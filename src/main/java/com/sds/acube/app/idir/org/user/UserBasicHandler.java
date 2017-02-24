package com.sds.acube.app.idir.org.user;

/**
 * UserBasicHandler.java
 * 2002-11-18
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

public class UserBasicHandler extends DataHandler
{
	private String m_strUserBasicTable = "";
	private String m_strUserBasicColumns = "";
	
	public UserBasicHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserBasicTable = TableDefinition.getTableName(TableDefinition.USER_BASIC);
		m_strUserBasicColumns = UserBasicTableMap.getColumnName(UserBasicTableMap.USER_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.USER_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.USER_OTHER_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.USER_UID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.PART_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.PART_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.ORG_DISPLAY_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.GRADE_CODE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.TITLE_CODE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.POSITION_CODE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.USER_ORDER) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.SECURITY_LEVEL) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.ROLE_CODE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.RESIDENT_NO) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.EMPLOYEE_ID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.SYSMAIL) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.SERVERS) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.IS_CONCURRENT) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.IS_PROXY) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.IS_DELEGATE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.DESCRIPTION) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.IS_EXISTENCE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.USER_RID) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.IS_DELETED) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED1) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED2) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED3) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.OPTIONAL_GTP_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.DISPLAY_ORDER) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.DEFAULT_USER) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.CERTIFICATION_ID) + "," + 
								UserBasicTableMap.getColumnName(UserBasicTableMap.DUTY_CODE) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_OTHER_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_OTHER_NAME) + "," + 
								UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_OTHER_NAME) + "," +
								UserBasicTableMap.getColumnName(UserBasicTableMap.PART_OTHER_NAME) + "," + 
								UserBasicTableMap.getColumnName(UserBasicTableMap.ORG_DISPLAY_OTHER_NAME);
	}	
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Employees
	 */
	private UserBasics processData(ResultSet resultSet)
	{
		UserBasics  	userBasics = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserBasicHandler.processData",
								   "");
			
			return null;
		}
		
		userBasics = new UserBasics();
		
		try
		{
			while(resultSet.next())
			{
				UserBasic userBasic = new UserBasic();
									
				// set UserBasic information
				userBasic.setUserID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_ID)));
				userBasic.setUserName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_NAME)));
				userBasic.setUserOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_OTHER_NAME)));
				userBasic.setUserUID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_UID)));
				userBasic.setGroupID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_ID)));
				userBasic.setGroupName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_NAME)));
				userBasic.setCompID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_ID)));
				userBasic.setCompName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_NAME)));
				userBasic.setDeptID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID)));
				userBasic.setDeptName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_NAME)));
				userBasic.setPartID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.PART_ID)));
				userBasic.setPartName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.PART_NAME)));
				userBasic.setOrgDisplayName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.ORG_DISPLAY_NAME)));
				userBasic.setGradeCode(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.GRADE_CODE)));
				userBasic.setTitleCode(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.TITLE_CODE)));
				userBasic.setPositionCode(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.POSITION_CODE)));
				userBasic.setUserOrder(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_ORDER)));
				userBasic.setSecurityLevel(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.SECURITY_LEVEL)));
				userBasic.setRoleCodes(MVController.getRoleCodes(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.ROLE_CODE))));
				userBasic.setResidentNo(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.RESIDENT_NO)));
				userBasic.setEmployeeID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.EMPLOYEE_ID)));
				userBasic.setSysMail(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.SYSMAIL)));
				userBasic.setServers(MVController.getServers(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.SERVERS))));
				userBasic.setIsConcurrent(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.IS_CONCURRENT)));
				userBasic.setIsProxy(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.IS_PROXY)));
				userBasic.setIsDelegate(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.IS_DELEGATE)));
				userBasic.setDescription(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DESCRIPTION)));
				userBasic.setIsExistence(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.IS_EXISTENCE)));
				userBasic.setUserRID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_RID)));
				userBasic.setIsDeleted(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.IS_DELETED)));
				userBasic.setReserved1(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED1)));
				userBasic.setReserved2(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED2)));
				userBasic.setReserved3(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.RESERVED3)));
				userBasic.setOptionGTPName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.OPTIONAL_GTP_NAME)));
				userBasic.setDisplayOrder(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DISPLAY_ORDER)));
				userBasic.setDefaultUser(getInt(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEFAULT_USER)));
				userBasic.setCertificationID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.CERTIFICATION_ID))); 
				userBasic.setDutyCode(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DUTY_CODE)));
				userBasic.setGroupOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.GROUP_OTHER_NAME)));
				userBasic.setCompOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_OTHER_NAME)));
				userBasic.setDeptOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_OTHER_NAME)));
				userBasic.setPartOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.PART_OTHER_NAME))); 
				userBasic.setOrgDisplayOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.ORG_DISPLAY_OTHER_NAME)));
							
				userBasics.add(userBasic);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserBasic classList.",
								   "UserBasicHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return userBasics;				
	} 
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @return UserBasic
	 */
	public UserBasic getUserBasicByID(String strUserID)
	{
		UserBasics 		userBasics = null;
		UserBasic 		userBasic = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_ID = ?";
				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userBasics = processData(m_connectionBroker.getResultSet());
		if (userBasics == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = userBasics.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "UserBasicHandler.getUserBasic.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		userBasic = userBasics.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return userBasic;
	}
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @param connectionBroker Connection 정보 
	 * @return UserBasic
	 */
	public UserBasic getUserBasicByID(String strUserID, ConnectionBroker connectionBroker)
	{
		UserBasics 		userBasics = null;
		UserBasic 		userBasic = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		if (!connectionBroker.IsConnectionClosed())
		{
			strQuery = "SELECT " + m_strUserBasicColumns +
					   " FROM " + m_strUserBasicTable +
					   " WHERE USER_ID = ?";
					   				   
			bResult = connectionBroker.prepareStatement(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			connectionBroker.setString(1, strUserID);
			
			bResult = connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
							
			userBasics = processData(connectionBroker.getResultSet());
			if (userBasics == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			nSize = userBasics.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct user infomation.", 
									   "UserBasicHandler.getUserBasicByID.LinkedList.size(not unique)", 
									   "");
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			userBasic = userBasics.get(0);
			
			connectionBroker.clearPreparedQuery();
		}	 
		
		return userBasic;
	}
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserUID 사용자 UID
	 * @return UserBasic
	 */
	public UserBasic getUserBasic(String strUserUID)
	{
		UserBasics 		userBasics = null;
		UserBasic 		userBasic = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_UID = ?";
				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userBasics = processData(m_connectionBroker.getResultSet());
		if (userBasics == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = userBasics.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "UserBasicHandler.getUserBasic.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		userBasic = userBasics.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return userBasic;
	}
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserUID 사용자 UID
	 * @param connectionBroker Connection 정보 
	 * @return UserBasic
	 */
	public UserBasic getUserBasic(String strUserUID, ConnectionBroker connectionBroker)
	{
		UserBasics 		userBasics = null;
		UserBasic 		userBasic = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT " + m_strUserBasicColumns +
					   " FROM " + m_strUserBasicTable +
					   " WHERE USER_UID = ?";
					   				   
			bResult = connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			connectionBroker.setString(1, strUserUID);
			
			bResult = connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
							
			userBasics = processData(connectionBroker.getResultSet());
			if (userBasics == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			nSize = userBasics.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct user infomation.", 
									   "UserBasicHandler.getUserBasic.LinkedList.size(not unique)", 
									   "");
				connectionBroker.clearPreparedQuery();
				return null;
			}
			
			userBasic = userBasics.get(0);
			
			connectionBroker.clearPreparedQuery();
		}	 
		
		return userBasic;
	}
	
	/**
	 * 새로운 사용자 UserID 확인. 
	 * @param strUserNewID 새로운 User ID
	 * @return boolean
	 */
	public boolean isAvailableUserID(String strUserNewID)
	{
		UserBasics		userBasics = null;
		boolean 		bResult = false;
		boolean        bReturn = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_ID = ?";
				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strUserNewID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
			
		userBasics = processData(m_connectionBroker.getResultSet());
		if (userBasics == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		nSize = userBasics.size();
		if (nSize == 0)
		{			
			bReturn = true;
		}
		else
		{
			bReturn = false;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return bReturn;		
	}
	
	/**
	 * User ID 변경.
	 * @param userBasic UserBasic 정보 
	 * @return boolean
	 */
	public boolean registerUserID(UserBasic userBasic)
	{
		boolean 		bReturn = false;
		boolean 		bResult = false;
		String 	 		strQuery = "";
		int 			nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strUserBasicTable +
				   " SET USER_ID = ?" +
				   " WHERE USER_UID = ?";
	
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, userBasic.getUserID());
		m_connectionBroker.setString(2, userBasic.getUserUID());
				   
		nResult = m_connectionBroker.executePreparedUpdate();
		if (nResult != 1)
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
	 * Certification ID 변경.
	 * @param strUserUID 사용자 UID 
	 * @param strCertificationID Certification ID
	 * @return boolean
	 */
	public boolean registerCertificationID(String strUserUID, String strCertificationID)
	{
		boolean 		bReturn = false;
		boolean 		bResult = false;
		String 	 		strQuery = "";
		int 			nResult = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user UID.", 
								   "UserBasicHandler.registerCertificationID.Empty UserUID", 
								   "");
			return bReturn;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strUserBasicTable +
				   "   SET CERTIFICATION_ID = ?" +
				   " WHERE USER_UID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strCertificationID);
		m_connectionBroker.setString(2, strUserUID);
			   
		nResult = m_connectionBroker.executePreparedUpdate();
		if (nResult != 1)
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
	 * Diplay Order 변경.
	 * @param strUserUID 사용자 UID
	 * @param nDisplayOrder Display Order
	 * @return boolean
	 */
	public boolean registerDisplayOrder(String strUserUID, int nDisplayOrder)
	{
		boolean 		bReturn = false;
		boolean 		bResult = false;
		String 	 		strQuery = "";
		int 			nResult = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user UID.", 
								   "UserBasicHandler.registerDisplayOrder.Empty UserUID", 
								   "");
			return bReturn;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		// Control Transaction
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strUserBasicTable +
				   " SET DISPLAY_ORDER = ?" + 
				   " WHERE USER_UID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setInt(1, nDisplayOrder);
		m_connectionBroker.setString(2, strUserUID);
				   
		nResult = m_connectionBroker.executePreparedUpdate();
		if (nResult != 1)
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
	 * 트리에서 펼쳐지는 사용자 변경
	 * @param strRealUserUID	원사용자 
	 * @param strDefaultUserUID Default 사용자UID
	 * @return boolean
	 */
	public boolean registerDefaultUser(String strRealUserUID, String strDefaultUserUID)
	{
		UserBasics		userBasics = null;
		boolean 		bReturn = false;
		boolean 		bResult = false;
		String 	 		strQuery = "";
		int 			nResult = -1;
		int				nDisplayOrder = 0;
		
		if (strRealUserUID == null || strRealUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get real user UID.", 
								   "UserBasicHandler.registerDefaultUser.Empty RealUserUID", 
								   "");
			return bReturn;
		}
		
		if (strDefaultUserUID == null || strDefaultUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get default user UID.",
								   "UserBasicHandler.registerDefaultUser.Empty DefaultUserUID",
								   "");
			return bReturn;
		}
		
		// get user related user information
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
			
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_UID = ?" +
				   "    OR USER_RID = ?";
				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strRealUserUID);
		m_connectionBroker.setString(2, strRealUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
			
		userBasics = processData(m_connectionBroker.getResultSet());
		if (userBasics == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		// update default user data
		m_connectionBroker.setAutoCommit(false);
		
		for (int i = 0 ; i < userBasics.size() ; i++)
		{
			UserBasic userBasic = userBasics.get(i);
			String 	  strUserUID = userBasic.getUserUID();
				
			if (strUserUID.compareTo(strDefaultUserUID) == 0)
			{
				nDisplayOrder = 1;
			}
			else
			{
				nDisplayOrder = 0;
			}
			
			strQuery = "UPDATE " + m_strUserBasicTable +
				   		" SET DEFAULT_USER = ? " +  
				   		" WHERE USER_UID = ? ";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;	
			}
			
			m_connectionBroker.setInt(1, nDisplayOrder);
			m_connectionBroker.setString(2, strUserUID);
			
			nResult = m_connectionBroker.executePreparedUpdate();
			if (nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;			
			}
			
			m_connectionBroker.clearPreparedQuery();
		}
		
		bReturn = true;
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();	
				
		return bReturn;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 데이타의 주어진 칼럼의 사용자 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strUserUID 	 사용자 UID
	 * @param int  			 User Basic 테이블 컬럼 Type
	 * @param strColumnValue User Basic 테이블 컬럼의 값 
	 */
	public boolean updateUserBasicInfoByUID(String strUserUID, 
											int    nColumnType, 
	                             			String strColumnValue)
	{
		boolean 	bReturn = false;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strColumnName = "";
		int 		nResult = -1;
		int 		nDataType = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "UserBasicHandler.updateUserBasicInfoByUID.Empty User ID",
								   "");
			return bReturn;
		}
		
		// get data type of column
		nDataType = UserBasicTableMap.getDataType(nColumnType);
		strColumnName = UserBasicTableMap.getColumnName(nColumnType);
		
		if (strColumnName != null && strColumnName.length() > 0)
		{
			if ((nDataType == UserBasicTableMap.STRING) || (nDataType == UserBasicTableMap.INTEGER))
			{
				strQuery = "UPDATE " + m_strUserBasicTable +
						   "   SET " + strColumnName + "= ?" +
						   " WHERE USER_UID = ? ";
			}
			else 
			{
				m_lastError.setMessage("Fail to get invalid column type.",
						   			   "UserBasicHandler.updateUserBasicInfoByUID.Invalid column type",
						   			   "");
				return bReturn;			
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get column name.",
								   "UserBasicHandler.updateUserBasicInfoByUID.Empty column name",
								   "");
			return bReturn;		
		}
		
		if (strQuery != null && strQuery.length() > 0)
		{
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
			
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			bResult = m_connectionBroker.prepareStatement(strQuery); 
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
			
			if (nDataType == UserBasicTableMap.STRING) 
				m_connectionBroker.setString(1, strColumnValue);
			else if (nDataType == UserBasicTableMap.INTEGER)
				m_connectionBroker.setInt(1, Integer.parseInt(strColumnValue));
			
			m_connectionBroker.setString(2, strUserUID);
				
			nResult = m_connectionBroker.executePreparedUpdate();
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
				
			m_connectionBroker.commit();	
			m_connectionBroker.clearConnectionBroker();	
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Fail to make sql query statement.",
								   "UserBasicHandler.updateUserBasicInfoByUID.special data type.",
								   "");			
		}
				
		return bReturn;		
	}		
			
}
