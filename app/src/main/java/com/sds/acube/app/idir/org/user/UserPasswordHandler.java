package com.sds.acube.app.idir.org.user;

/**
 * UserPasswordHandler.java
 * 2002-10-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.io.*;

import com.sds.acube.app.login.security.EnDecode;

public class UserPasswordHandler extends DataHandler
{
	protected final static int REGISTER_SYSPWD_SUCCESS = 0;
	protected final static int REGISTER_INVALID_SYSPWD = 1;
	protected final static int REGISTER_SYSPWD_FAIL = 2;
	
	protected final static int PASSWORD_APPROVAL = 1;
	protected final static int PASSWORD_FINGERPRINT = 2;
	
	private String m_strUserPWDColumnes = "";
	private String m_strUserPWDTable = "";
	
	private final static int m_nApprovalPassword = 1;
	private final static int m_nFingerInfoPassword = 2;
	
	public final static String ENCRYPT_DELIMITER = ":";					// sutil package를 사용하였을 경우 delimiter
	public final static String ENCRYPT_POSTFIX = ":sisenc";				// sutil package를 사용하였을 경우 뒤에 붙는 꼬리말
	
	// Ref Type
	private final int MINE = 0;					// 자기 결재함
	private final int CONCURRENT = 1;			// 겸직 결재함 
	private final int PROXY = 2;				// 직무 대리 결재함 
	private final int DELEGATE = 3;				// 파견 결재함
	private final int SUBSTITUTE = 4;			// 대결 결재함
	
	public UserPasswordHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserPWDTable = TableDefinition.getTableName(TableDefinition.USER_PASSWORD);
		m_strUserPWDColumnes = UserPasswordTableMap.getColumnName(UserPasswordTableMap.USER_ID) + "," +
							   UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD) + "," +
							   UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE) + "," +
							   UserPasswordTableMap.getColumnName(UserPasswordTableMap.APPROVAL_PASSWORD) + "," +
							   UserPasswordTableMap.getColumnName(UserPasswordTableMap.FINGERPRINT_INFO);
	}
		
	/**
	 * ResultSet을 UserPassword Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserPassword
	 */
	private UserPassword processData(ResultSet resultSet)
	{
		UserPassword	userPassword = null;
		int				nCount = 0;
		int				nPasswordType = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserPasswordHandler.processData",
								   "");
			
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{
				userPassword = new UserPassword();
				
				nCount++;
							
				// set user password information			
				userPassword.setUserUID(getString(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.USER_ID)));
				userPassword.setSystemPassword(getString(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD)));
				
				nPasswordType = getInt(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE));
				userPassword.setPasswordType(nPasswordType);
				userPassword.setFingerPrintInfo(getBlob(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.FINGERPRINT_INFO)));	
				userPassword.setApprovalPassword(getString(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.APPROVAL_PASSWORD)));	
				
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserPassword class.",
								   "UserPasswordHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user password.",
								   "UserPasswordHandler.processData.unique UserImage",
								   "");
			return null;
		}
			
		return userPassword;				
	} 
	
	/**
	 * 사용자 password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strPassword 사용자 결재 password
	 * @return boolean
	 */
	public boolean registerApprovalPassword(String strUserUID,
											 String strPassword)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		int 		nPasswordType = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		} 
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
				nPasswordType = getInt(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.registerApprovalType.next",
								   e.getMessage());
			m_connectionBroker.clearPreparedQuery();
			
		}
		m_connectionBroker.clearPreparedQuery();
		
		if (!bFound)
		{
			// insert password information
			bReturn = insertApprovalPassword(strUserUID, strPassword);	
		}
		else
		{
			// update password information
			bReturn = updateApprovalPassword(strUserUID, strPassword, nPasswordType);
		}
		m_connectionBroker.clearConnectionBroker();				
		return bReturn;	
	}
	
	
	/**
	 * 사용자 password 정보 insert
	 * @param strUserUID  사용자 UID
	 * @param strPassword 결재 Password
	 * @return boolean
	 */
	private boolean insertApprovalPassword(String strUserUID,
											 String strPassword)
	{
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int			nResult = -1;

		if (!m_connectionBroker.IsConnectionClosed())
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{
				strQuery = "INSERT INTO " + m_strUserPWDTable + 
									"(" + m_strUserPWDColumnes + ")" +
					   	   " VALUES (?, '', 1, ?, EMPTY_BLOB())";
			}
			else
			{
				strQuery = "INSERT INTO " + m_strUserPWDTable + 
									"(" + m_strUserPWDColumnes + ")" +
					   	   " VALUES (?, '', 1, ? , NULL)";				
			}
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setString(1, strUserUID);
			m_connectionBroker.setString(2, strPassword);
																					  					  
			nResult = m_connectionBroker.executePreparedUpdate();
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearPreparedQuery();
			
			// 변경시간 recording
			bResult = recordChangedTime(strUserUID);
			if (bResult == false)
			{
				m_connectionBroker.rollback();	
				return bReturn;
			}	
												  
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;		
	}	
	
	/** 
	 * 사용자 Password 정보 update
	 * @param strUserUID  사용자 UID
	 * @param strPassword 결재 Password
	 * @param nPasswordType 결재 Password Type
	 * @return boolean
	 */
	private boolean updateApprovalPassword(String strUserUID,
											 String strPassword,
											 int    nPasswordType)
	{
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 		strQuery = "";
		int 		nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (nPasswordType > 0)
			{
				// 결재 Password Print Password Check가 안되어 있는 경우 사용 
				if ((nPasswordType & PASSWORD_APPROVAL) == 0)
				{
					nPasswordType += PASSWORD_APPROVAL;	
				} 	
			}
			else
			{
				nPasswordType = PASSWORD_APPROVAL;
			}
			
			strQuery = "UPDATE " + m_strUserPWDTable +
					   " SET PASSWORD_TYPE = ?," + 
					   		"APPROVAL_PASSWORD = ?" +
					   " WHERE USER_ID = ?";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setInt(1, nPasswordType);
			m_connectionBroker.setString(2, strPassword);
			m_connectionBroker.setString(3, strUserUID);
			
			nResult = m_connectionBroker.executePreparedUpdate();
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearPreparedQuery();	
			
			// 변경시간 recording
			bResult = recordChangedTime(strUserUID);
			if (bResult == false)
			{
				m_connectionBroker.rollback();	
				return bReturn;
			}	
			
			m_connectionBroker.commit();	
				
			bReturn = true;
		}
	
		return bReturn;		
				
	}
	
	/**
	 * 사용자 Password 정보.
	 * @param strUserUID 사용자 UID
	 * @return UserPassword
	 */
	public UserPassword getUserPassword(String strUserUID)
	{
		UserPassword 	userPassword = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
			
		userPassword = processData(m_connectionBroker.getResultSet());
		if (userPassword == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return userPassword;		
	}
	
	/**
	 * 사용자 Password 정보 by ID
	 * @param strUserID 사용자 Login ID
	 * @return UserPassword
	 */
	public UserPassword getUserPasswordByID(String strUserID)
	{
		UserPassword 	userPassword = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + 	m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.USER_ID) + "," +
							 	m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD) + "," +
							 	m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE) + "," + 
								m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.APPROVAL_PASSWORD) + "," +
							 	m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.FINGERPRINT_INFO) + 
				   " FROM " + m_strUserPWDTable + "," + TableDefinition.getTableName(TableDefinition.USER_BASIC) +
				   " WHERE " + TableDefinition.getTableName(TableDefinition.USER_BASIC) +".USER_ID = ?" +
				   "   AND " + TableDefinition.getTableName(TableDefinition.USER_BASIC) +".USER_UID = " + m_strUserPWDTable + ".USER_ID" ;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
			
		userPassword = processData(m_connectionBroker.getResultSet());
		if (userPassword == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return userPassword;		
	}
		
	/**
	 * 마지막 패스워드 변경 정보 기록
	 * @param strUserUID 	사용자 UID
	 * @return boolean
	 */
	private boolean recordChangedPassword(String strUserUID)
	{
		boolean 	bReturn = false;
		String 	 	strUserTimeTable = TableDefinition.getTableName(TableDefinition.USER_TIME);
		String   	strToday = getToday("yyyy-MM-dd HH:mm:ss");
		String 	 	strQuery = "";
		int 		nResult = -1;
		
		if (strToday.length() == 0)
		{
			m_lastError.setMessage("Fail to get Today.",
								   "UserPasswordHandler.recordChangedPassword.getToday",
								   "");
								   
			return bReturn;
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{	
			strQuery = "UPDATE " + strUserTimeTable +
					   " SET WHEN_CHANGED_PASSWORD = " + getDateFormat(strToday)  +
					   " WHERE USER_ID = '" + strUserUID + "'";
							   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();	
										
			bReturn = true;
		}
	
		return bReturn;	
	}
		
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strSystemPassword 사용자 로그온 password
	 * @return boolean
	 */
	public boolean registerLogonPassword(String strUserUID,
										   String strSystemPassword)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.registerLogonPassword.next",
								   e.getMessage());
			m_connectionBroker.clearPreparedQuery();
			
		}
		m_connectionBroker.clearPreparedQuery();
		
		if (bFound == false)
		{
			// insert system password information
			bReturn = insertLogonPassword(strUserUID, strSystemPassword);
			
		}
		else
		{
			// update system password information
			bReturn = updateLogonPassword(strUserUID, strSystemPassword);
		}
		
		
		m_connectionBroker.clearConnectionBroker();				
		return bReturn;	
	}
	
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strOldSysPassword 기존 사용자 로그온 password
	 * @param strNewSysPassword 새로운 사용자 로그온 password
	 * @return int 		
	 */
	public int registerLogonPassword(String strUserUID,
									  String strOldSysPassword,
									  String strNewSysPassword)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		boolean     bFound = false;
		boolean     bReturn = false;
		String 	 	strQuery = "";
		String 		strDBPWD = "";
		int 		nReturn = REGISTER_SYSPWD_FAIL;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
				strDBPWD = getString(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.registerLogonPassword.next",
								   e.getMessage());
			m_connectionBroker.clearPreparedQuery();
			
		}
		m_connectionBroker.clearPreparedQuery();
		

		if (bFound == false)
		{
			// insert system password information
			bReturn = insertLogonPassword(strUserUID, strNewSysPassword);
			
		}
		else
		{
			if (strDBPWD.compareTo(strOldSysPassword) != 0)
			{
				nReturn = REGISTER_INVALID_SYSPWD;
				m_connectionBroker.clearConnectionBroker();
				return nReturn;		
			}
			
			// update system password information
			bReturn = updateLogonPassword(strUserUID, strNewSysPassword);
		}
		
		if (bReturn == true)
		{
			nReturn = REGISTER_SYSPWD_SUCCESS;
		}
		else
		{
			nReturn = REGISTER_SYSPWD_FAIL;
		}
		
		m_connectionBroker.clearConnectionBroker();				
		return nReturn;	
	}
	
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strOldSysPassword 기존 사용자 로그온 password
	 * @param strNewSysPassword 새로운 사용자 로그온 password
	 * @param nType 로그인 Data 비교 방법
	 * 			    0 : DB Data 값 그대로 비교 
	 *              1 : sutil에서 제공하는 디코딩 모듈 사용
	 * @return int 		
	 */
	public int registerLogonPassword(String strUserUID, String strOldSysPassword,
									 String strNewSysPassword, int nType)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		boolean     bFound = false;
		boolean     bReturn = false;
		String 	 	strQuery = "";
		String 		strDBPWD = "";
		int 		nReturn = REGISTER_SYSPWD_FAIL;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return nReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
				strDBPWD = getString(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.registerLogonPassword.next",
								   e.getMessage());
			m_connectionBroker.clearPreparedQuery();
			
		}
		m_connectionBroker.clearPreparedQuery();
		

		if (bFound == false)
		{
			// insert system password information
			bReturn = insertLogonPassword(strUserUID, strNewSysPassword);
			
		}
		else
		{
			if (nType == 1) 
			{
				// 디코딩 모듈을 사용하는 경우
				String strDecodedInputPW = "";
				String strDecodedStorePW = "";
				
				strDecodedInputPW = decodeBySType(strOldSysPassword);
				strDecodedStorePW = decodeBySType(strDBPWD);
													
				if (strDecodedStorePW.compareTo(strDecodedInputPW) != 0)
				{
					nReturn = REGISTER_INVALID_SYSPWD;
					m_connectionBroker.clearConnectionBroker();
					return nReturn;		
				}	
			} 
			else
			{
				// DB 값을 그대로 비교하는 경우
				if (strDBPWD.compareTo(strOldSysPassword) != 0)
				{
					nReturn = REGISTER_INVALID_SYSPWD;
					m_connectionBroker.clearConnectionBroker();
					return nReturn;		
				}
			}
			
			// update system password information
			bReturn = updateLogonPassword(strUserUID, strNewSysPassword);
		}
		
		if (bReturn == true)
		{
			nReturn = REGISTER_SYSPWD_SUCCESS;
		}
		else
		{
			nReturn = REGISTER_SYSPWD_FAIL;
		}
		
		m_connectionBroker.clearConnectionBroker();				
		return nReturn;	
	}
	
	/**
	 * 사용자 system password 정보 insert
	 * @param strUserUID  사용자 UID
	 * @param strSystemPassword System Password
	 * @return boolean
	 */
	private boolean insertLogonPassword(String strUserUID,
										  String strSystemPassword)
	{
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int 		nResult = -1;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
		
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{	
				strQuery = "INSERT INTO " + m_strUserPWDTable + 
									"(" + m_strUserPWDColumnes + ")" +
					   	   " VALUES (?, ?, 1, '', EMPTY_BLOB())";
			}
			else
			{
				strQuery = "INSERT INTO " + m_strUserPWDTable + 
									"(" + m_strUserPWDColumnes + ")" +
					   	   " VALUES (?, ?, 1, '', NULL)";
			}
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false) 
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setString(1, strUserUID);
			m_connectionBroker.setString(2, strSystemPassword);
																					  					  
			nResult = m_connectionBroker.executePreparedUpdate();
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearPreparedQuery();
			
			// 변경시간 recording
			bResult = recordChangedPassword(strUserUID);
			if (bResult == false)
			{
				m_connectionBroker.rollback();	
				return bReturn;
			}	
												  
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;		
	}	
	
	/** 
	 * 사용자 System Password 정보 update
	 * @param strUserUID  사용자 UID
	 * @param strSystemPassword System Password
	 * @return boolean
	 */
	private boolean updateLogonPassword(String strUserUID,
										  String strSystemPassword)
	{
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 		strQuery = "";
		int		nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strUserPWDTable +
					   " SET SYSTEM_PASSWORD = ?" +
					   " WHERE USER_ID = ?";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setString(1, strSystemPassword);
			m_connectionBroker.setString(2, strUserUID);
							   	   	   								
			nResult = m_connectionBroker.executePreparedUpdate();
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearPreparedQuery();	
			
			// 변경시간 recording
			bResult = recordChangedPassword(strUserUID);
			if (bResult == false)
			{
				m_connectionBroker.rollback();	
				return bReturn;
			}	
			
			m_connectionBroker.commit();	
				
			bReturn = true;
		}
	
		return bReturn;		
				
	}
	
	/**
	 * 사용자 지문 정보 입력 
	 * @param UserPassword 패스 워드 관련 정보 
	 * @return boolean
	 */
	public boolean registerFingerPrintInfo(UserPassword userPassword)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean 	bFound = false;
		String   	strQuery = "";
		int 		nPasswordType = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
		   		   " FROM " + m_strUserPWDTable +
		   		   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, userPassword.getUserUID());
		   		   
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;
				nPasswordType = getInt(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE));
				userPassword.setPasswordType(nPasswordType);
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.registerFingerPrintInfo.next",
								   e.getMessage());			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		if (bFound == false)
		{
			// insert finger print information
			bReturn = insertFingerPrint(userPassword);
		}
		else
		{
			// update finger print information
			bReturn = updateFingerPrint(userPassword);
		}
	
		m_connectionBroker.clearConnectionBroker();
			   		   
		return bReturn;	
	}
	
	/**
	 * 지문 정보 Column Update
	 * @param resultSet 지문 정보를 update할 resultSet
	 * @param userPassword 사용자 Password 정보
	 * @return boolean
	 */
	private boolean updateFingerprintInfo(ResultSet resultSet,
										    UserPassword userPassword)
	{
		ConnectionParam connectionParam = null;
		OutputStream 	outstream = null; 
		Blob			fingerprintBlob = null;
		int 			nMethod = 0;
		
		connectionParam = m_connectionBroker.getConnectionParam();
		nMethod = connectionParam.getMethod();
		
		try
		{
			while (resultSet.next())
			{
				fingerprintBlob = resultSet.getBlob(UserPasswordTableMap.getColumnName(UserPasswordTableMap.FINGERPRINT_INFO));	
				
				if (fingerprintBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS)     // Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outstream = ((oracle.sql.BLOB)fingerprintBlob).getBinaryOutputStream();	
				//		}
				//		else
				//		{
				//			outstream = ((weblogic.jdbc.common.OracleBlob)fingerprintBlob).getBinaryOutputStream();	
				//		}
				//	}
				//	else													// Using DriverManager
					{
//						if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outstream = ((weblogic.jdbc.common.OracleBlob)fingerprintBlob).getBinaryOutputStream();	
//						}
//						else
//						{
//							//outstream = ((oracle.sql.BLOB)fingerprintBlob).getBinaryOutputStream();
//							//20110730 JDBC blob 으로 변환
//							outstream = fingerprintBlob.setBinaryStream(1L);
//						}
					    
					    outstream = fingerprintBlob.setBinaryStream(1L);
					}
					
					if (outstream != null)
					{
						outstream.write(userPassword.getFingerPrintInfo());
						outstream.close();
					}					
				}
			}		
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to update finger print image information.",
								   "UserPasswordHandler.updateUserPasswordInfo(SQLException)",
								   e.getMessage());
								
			return false;
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to update finger print image information.",
								   "UserPasswordHandler.updateUserPasswordInfo(IOException)",
								   e.getMessage());			
								   
			return false;
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to update finger print image information.",
								   "UserPasswordHandler.updateUserPasswordInfo(Exception)",
								   e.getMessage());
		
			return false;
		}
		
		return true;
	}
	
	/**
	 * 지문 정보 이미지 생성
	 * @param userPassword 사용자 Password 정보
	 * @return boolean
	 */
	private boolean insertFingerPrint(UserPassword userPassword)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int 	 	nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			// insert user password column
			strQuery = "INSERT INTO " + m_strUserPWDTable + 
								"(" + m_strUserPWDColumnes + ")" +
				   	   " VALUES (?, '', 2, '', EMPTY_BLOB())";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setString(1, userPassword.getUserUID());
								
			nResult = m_connectionBroker.executePreparedUpdate();
			if (nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearPreparedQuery();
			
			// update password blob data
			strQuery = "SELECT " + m_strUserPWDColumnes +
					   " FROM " + m_strUserPWDTable +
					   " WHERE USER_ID = ?";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setString(1, userPassword.getUserUID());
			
			bResult = m_connectionBroker.executePreparedQuery();
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;	
			}
			
			resultSet = m_connectionBroker.getResultSet();
			bResult = updateFingerprintInfo(resultSet, userPassword);
			if (bResult == false)
			{
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.commit();
			m_connectionBroker.clearPreparedQuery();
			
			bReturn = true;			
			
		}
		else
		{
			m_lastError.setMessage("Fail to open database connection.",
								   "UserPasswordHandler.insertFingerPrint",
								   "");
			return bReturn;
		}
		
		return bReturn;
	} 
	
	/**
	 * 지문 정보 이미지 Update
	 * @param userPassword 사용자 Password 정보
	 * @return boolean
	 */
	private boolean updateFingerPrint(UserPassword userPassword)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bModify = false;
		String 	 	strQuery = "";
		int 	 	nResult = -1;
		int		nPasswordType = 1;
		
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			nPasswordType = userPassword.getPasswordType();
			if (nPasswordType > 0)
			{
				// Finger Print Password Check가 안되어 있는 경우 사용 
				if ((nPasswordType & PASSWORD_FINGERPRINT) == 0)
				{
					nPasswordType += PASSWORD_FINGERPRINT;	
				} 	
			}
			else
			{
				nPasswordType = PASSWORD_FINGERPRINT;
			}
			
			if (userPassword.getFingerPrintInfo() != null && 
				userPassword.getFingerPrintInfo().length > 0)
			{
				bModify = true;
			}
			
			if (bModify == true)
			{
				// insert user password column (with finger print information)
				strQuery = "UPDATE " + m_strUserPWDTable +
						   " SET FINGERPRINT_INFO = EMPTY_BLOB(), " + 
						   "     PASSWORD_TYPE = ?" + 
						   " WHERE USER_ID = ?";
			}
			else
			{
				// insert user password column
				strQuery = "UPDATE " + m_strUserPWDTable +
						   " SET PASSWORD_TYPE = ?" + 
						   " WHERE USER_ID = ?";
			}
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			m_connectionBroker.setInt(1, nPasswordType);
			m_connectionBroker.setString(2, userPassword.getUserUID());
								
			nResult = m_connectionBroker.executePreparedUpdate();
			if (nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearPreparedQuery();
				return bReturn;
			}
			
			if (bModify == true)
			{
				m_connectionBroker.clearPreparedQuery();
				
				// update password blob data
				strQuery = "SELECT " + m_strUserPWDColumnes +
						   " FROM " + m_strUserPWDTable +
						   " WHERE USER_ID = ?";
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearPreparedQuery();
					return bReturn;	
				}
				
				m_connectionBroker.setString(1, userPassword.getUserUID());
				
				bResult = m_connectionBroker.executePreparedQuery();
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearPreparedQuery();
					return bReturn;	
				}
				
				resultSet = m_connectionBroker.getResultSet();
				bResult = updateFingerprintInfo(resultSet, userPassword);
				if (bResult == false)
				{
					m_connectionBroker.rollback();
					m_connectionBroker.clearPreparedQuery();
					return bReturn;
				}
			}
			
			m_connectionBroker.commit();
			m_connectionBroker.clearPreparedQuery();
			
			bReturn = true;			
			
		}
		else
		{
			m_lastError.setMessage("Fail to open database connection.",
								   "UserPasswordHandler.updateFingerPrint",
								   "");
			return bReturn;
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 지문 인식 정보 Delete
	 * @param strUserUID 지문 인식 정보를 Delete 할 사용자
	 * @return boolean
	 */
	public boolean deleteFingerPrintInfo(String strUserUID)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean    bResult = false;
		String 	 	strQuery = "";
		int 		nPasswordType = 1;
		int 		nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserPWDColumnes +
				   " FROM " + m_strUserPWDTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				nPasswordType = getInt(resultSet, UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.deleteFingerPrintInfo(SQLException)",
								   e.getMessage());			
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserPasswordHandler.deleteFingerPrintInfo(Exception)",
								   e.getMessage());					
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		// Password Type 정리
		if (nPasswordType > 0)
		{
			// Finger Print Password Check가 안되어 있는 경우 사용 
			if ((nPasswordType & PASSWORD_FINGERPRINT) == PASSWORD_FINGERPRINT)
			{
				nPasswordType -= PASSWORD_FINGERPRINT;	
			} 	
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strUserPWDTable +
				   " SET PASSWORD_TYPE = ?" +  
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		m_connectionBroker.setInt(1, nPasswordType);
		m_connectionBroker.setString(2, strUserUID);
				   				   
		nResult = m_connectionBroker.executePreparedUpdate();
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		bReturn = true;
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();	
					
		return bReturn;		
	}
	
	/**
	 * 사용자 Password정보를 추출하는 함수.
	 * @param strUserUID 사용자 UID
	 * @param nRefType   사용자 Type (	0 : 원직 사용자
	 *									1 :	겸직 사용자
	 *									2 :	직무 대리 사용자 
	 *									3 :	파견 사용자
	 *									4 :	대결 사용자 )
	 * @return UserPassword  
	 */	
	public UserPassword getUserPassword(String strUserUID, int nRefType)
	{
		UserPassword userPassword = null;
		boolean 	 bResult = false;
		String 		 strQuery = "";
		Substitute	 substitute = null;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user uid.",
								   "UserPasswordHandler.getUserPassword.Empty User UID",
								   "");
			return userPassword;
		}
		
		if (nRefType == CONCURRENT ||     // 겸직자
			nRefType == PROXY ||          // 파견
			nRefType == DELEGATE )    	  // 직무대리
		{
			strQuery = "SELECT " + 	m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.USER_ID) + "," +
							 	    m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.SYSTEM_PASSWORD) + "," +
							 	    m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.PASSWORD_TYPE) + "," + 
								    m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.APPROVAL_PASSWORD) + "," +
							 	    m_strUserPWDTable + "." + UserPasswordTableMap.getColumnName(UserPasswordTableMap.FINGERPRINT_INFO) + 
				      " FROM "   + m_strUserPWDTable + "," + TableDefinition.getTableName(TableDefinition.USER_BASIC) +
				      " WHERE "  + TableDefinition.getTableName(TableDefinition.USER_BASIC) +".USER_UID = ?" +
				      "   AND "  + TableDefinition.getTableName(TableDefinition.USER_BASIC) +".USER_RID = " + m_strUserPWDTable + ".USER_ID" ;		
		}
		else if (nRefType == SUBSTITUTE)  // 대결자
		{
			SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
			substitute = substituteHandler.getValidSubstitute(strUserUID);
			
			if (substitute != null)
			{
				strQuery = "SELECT " + m_strUserPWDColumnes +
				   	   		" FROM " + m_strUserPWDTable +
				       		" WHERE USER_ID = ?";		
			}	
		}
		else							  // 원결재자
		{
			strQuery = "SELECT " + m_strUserPWDColumnes +
				   	   " FROM " + m_strUserPWDTable +
				       " WHERE USER_ID = ?";	
		}
		
		if (strQuery != null && strQuery.length() > 0)
		{
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();	
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return null;
			}
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();	
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return null;
			}
			
			if (nRefType == SUBSTITUTE)  // 대결자
				m_connectionBroker.setString(1, substitute.getUserUID());
			else
				m_connectionBroker.setString(1, strUserUID);
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();	
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return null;
			}
			
			userPassword = processData(m_connectionBroker.getResultSet());
			if (userPassword == null)
			{
				m_connectionBroker.clearConnectionBroker();	
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return null;
			}
					
			m_connectionBroker.clearConnectionBroker();	 		
		}
	
		return userPassword;
	}
	
	/**
	 * sutil 모듈로 디코딩하는 함수.
	 * @param strEncodedData 인코딩되어 있는 데이터
	 * @return String
	 */
	private String decodeBySType(String strEncodedData)
	{
		String strDecodedData = EnDecode.DecodeBySType(strEncodedData);
		if ((strDecodedData == null) || (strDecodedData.length() == 0))
			return strDecodedData;
			
		int nStartIndex = strDecodedData.indexOf(ENCRYPT_DELIMITER);
		int nEndIndex = strDecodedData.indexOf(ENCRYPT_POSTFIX);
		
		if ((nStartIndex > -1) && (nEndIndex > nStartIndex))
			return strDecodedData.substring(nStartIndex + 1, nEndIndex);
		else if ((nStartIndex > -1) && (nStartIndex == nEndIndex))
			return "";
		else
			return strDecodedData;
	}
}
