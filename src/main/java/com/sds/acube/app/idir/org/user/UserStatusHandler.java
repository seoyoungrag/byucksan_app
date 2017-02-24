package com.sds.acube.app.idir.org.user;

/**
 * UserStatusHandler.java
 * 2002-10-30
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
import java.text.SimpleDateFormat;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class UserStatusHandler extends DataHandler
{
	private String m_strStatusColumns = "";
	private String m_strAssociatedTable = "";
	private String m_strAssociatedColumns = "";
	private String m_strStatusTable = "";
	private String m_strInsertColumns = "";
	
	public UserStatusHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strStatusTable = TableDefinition.getTableName(TableDefinition.USER_STATUS);
		m_strStatusColumns = UserStatusTableMap.getColumnName(UserStatusTableMap.USER_ID) +","+
						   	 UserStatusTableMap.getColumnName(UserStatusTableMap.USER_STATUS) +","+
						   	 UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_SET) +","+
						   	 UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_REASON) +","+
						   	 UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_START_DATE) +","+
						     UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_END_DATE);
						     						     
		m_strAssociatedTable = TableDefinition.getTableName(TableDefinition.USER_ASSOCIATED);
		m_strAssociatedColumns = UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.USER_ID) + "," +
								 UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE) + "," +
								 UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE) + "," +
								 UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_ID);
	}
	

	/**
	 * ResultSet을 UserImage Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserStatus
	 */
	private UserStatus processData(ResultSet resultSet)
	{
		UserStatus		userStatus = null;
		boolean		bResult = false;
		int			nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserStatusHandler.processData",
								   "");
			
			return null;
		}
			
		try
		{
			while(resultSet.next())
			{
				userStatus = new UserStatus();
				
				nCount++;
								
				// set user status information
				userStatus.setUserUID(getString(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.USER_ID)));
				userStatus.setUserStatus(getString(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.USER_STATUS)));
				userStatus.setEmptySet(getBoolean(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_SET)));
				userStatus.setEmptyReason(getString(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_REASON)));
				userStatus.setStartDate(getDate(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_START_DATE), TIMESTAMP_SECOND));
				userStatus.setEndDate(getDate(resultSet, UserStatusTableMap.getColumnName(UserStatusTableMap.EMPTY_END_DATE), TIMESTAMP_SECOND));				
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserStatus class.",
								   "UserStatusHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user status.",
								   "UserStatusHandler.processData.unique UserImage",
								   "");
			return null;
		}
			
		return userStatus;				
	} 
	
	/**
	 * 사용자 Status 정보 등록
	 * @param UserStatus
	 * @return boolean
	 */
	public boolean registerUserStatus(UserStatus userStatus)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strStatusColumns +
				   " FROM " + m_strStatusTable +
				   " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
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
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "UserStatusHandler.registerUserStatus.next",
								   e.getMessage());
			
		}
		
		m_connectionBroker.clearQuery();
		if (!bFound)
		{
			// insert
			bReturn = insertUserStatus(userStatus);
		}	
		else
		{
			// update
			bReturn = updateUserStatus(userStatus);
		}
		
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;
	}

	/**
	 * 사용자 status 정보 등록(insert)
	 * @param userStatus 
	 * @return boolean
	 */
	private boolean insertUserStatus(UserStatus userStatus)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		String 	 	strQuery = "";
		int		nResult = -1;

		if (!m_connectionBroker.IsConnectionClosed())
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			

			if (userStatus.getEmptySet())
			{
				strQuery = "INSERT INTO " + m_strStatusTable + 
									"(" + m_strInsertColumns + ")" +
					   	   " VALUES ('" + userStatus.getUserUID() + "'," +
					   	   				  DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   			"'" + userStatus.getEmptyReason() + "'," +
					   	   				  getDateFormat(userStatus.getStartDate()) + "," +
					   	   				  getDateFormat(userStatus.getEndDate()) + ")"; 
			}
			else
			{
				strQuery = "INSERT INTO " + m_strStatusTable + 
									"(" + m_strInsertColumns + ")" +
					   	   " VALUES ('" + userStatus.getUserUID() + "'," +
					   	   				  DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   			 "''," +
					   	   				  getDateFormat("") + "," +
					   	   				  getDateFormat("") + ")"; 			
			}

				
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 status 정보 등록(update)
 	 * @param UserStatus
	 * @return boolean
	 */
	private boolean updateUserStatus(UserStatus userStatus)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		String 	 	strQuery = "";
		int	    nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
		
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{	
				if (userStatus.getEmptySet() == true)
				{
					strQuery = "UPDATE " + m_strStatusTable + 
						   	   " SET EMPTY_SET = " + DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   	    	"EMPTY_REASON = '" + userStatus.getEmptyReason() + "'," +
					   	   	    	"EMPTY_START_DATE = " + getDateFormat(userStatus.getStartDate()) +"," +
					   	   	    	"EMPTY_END_DATE = " + getDateFormat(userStatus.getEndDate()) +
					   	       " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				}
				else
				{
					strQuery = "UPDATE " + m_strStatusTable + 
						   	   " SET EMPTY_SET = " + DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   	    	"EMPTY_REASON = ''," +
				   	   	    		"EMPTY_START_DATE = " + getDateFormat("") +"," +
					   	   	    	"EMPTY_END_DATE = " + getDateFormat("") +
					   	       " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				}
			}
			else
			{
				if (userStatus.getEmptySet() == true)
				{
					strQuery = "UPDATE " + m_strStatusTable + 
						   	   " SET EMPTY_SET = " + DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   	    	"EMPTY_REASON = '" + userStatus.getEmptyReason() + "'," +
					   	   	    	"EMPTY_START_DATE = '" +  userStatus.getStartDate() +"', " +
					   	   	    	"EMPTY_END_DATE = '" + userStatus.getEndDate() +"'" +
					   	       " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				}
				else
				{
					strQuery = "UPDATE " + m_strStatusTable + 
						   	   " SET EMPTY_SET = " + DataConverter.toInt(userStatus.getEmptySet()) + "," +
					   	   	    	"EMPTY_REASON = ''," +
					   	   	    	"EMPTY_START_DATE = NULL," + 
					   	   	        "EMPTY_END_DATE = NULL" +
					   	       " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				}				
			}
						   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();	
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;		
	}
	
	/**
	 * 주어진 결재자 상태 정보 
	 * @param strUserUID 사용자 UID
	 * @return UserStatus
	 */
	public UserStatus getUserStatus(String strUserUID)
	{
		UserStatus 		userStatus = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strStatusColumns +
				   " FROM " + m_strStatusTable +
				   " WHERE USER_ID = '" + strUserUID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		userStatus = processData(m_connectionBroker.getResultSet());
		if (userStatus == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return userStatus;
	}
	
	/**
	 * 사용자 부재 정보 및 대결자 정보 등록
	 * @param userStaus 	사용자 상태 정보 
	 * @param substitutes 	대결자 정보 
	 * @return boolean  
	 */
	public boolean registerUserStatus(UserStatus  userStatus,
									   Substitutes substitutes)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean	bFound = false;
		String 	 	strQuery = "";
		
		if (userStatus.getEmptySet() == false)
		{
			m_lastError.setMessage("Fail to correct user status.",
								   "UserStatusHandler.registerUserStatus.UserStatus.getEmptySet",
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
		
		strQuery = "SELECT " + m_strStatusColumns +
		   		   " FROM " + m_strStatusTable +
		           " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
		           
		bResult = m_connectionBroker.excuteQuery(strQuery);
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
								   "UserStatusHandler.registerUserStatus.next",
								   e.getMessage());
			
		}
		
		if (bFound == false)
		{
			// Insert 대결자 정보  
			bReturn = insertUserStatus(userStatus, substitutes);
		}	
		else
		{
			// update 대결자 정보  
			bReturn = updateUserStatus(userStatus, substitutes);
		}
		
		m_connectionBroker.clearConnectionBroker();
			
		return bReturn;								
	}
	
	/**
	 * 사용자 status 정보 등록(insert)
	 * @param userStatus  사용자 상태 정보 
	 * @param substitutes 대결자 정보 
	 * @return boolean
	 */
	private boolean insertUserStatus(UserStatus userStatus, 
									   Substitutes substitutes)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int 	 nResult = -1;
								
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strStatusTable + 
								"(" + m_strStatusColumns + ")" +
				   	   " VALUES ('" + userStatus.getUserUID() + "'," +
				   	   			"'0'," +
				   	   				  DataConverter.toInt(userStatus.getEmptySet()) + "," +
				   	   			"'" + userStatus.getEmptyReason() + "'," +
				   	   				  getDateFormat(userStatus.getStartDate()) + "," +
				   	   				  getDateFormat(userStatus.getEndDate()) + ")"; 
					
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			bResult = registerSubstitutes(userStatus, substitutes);
			if (bResult == false)
			{
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "UserStatusHandler.insertUserStatus.IsConnectionClosed.",
								   "");
		}	
		return bReturn;		
	}
	
	/**
	 *  사용자 status 정보 등록(update) 결재 라인 정보 
	 * @param userStatus  사용자 상태 정보 
	 * @param substitutes 대결자 정보 
	 * @return boolean
	 */
	private boolean updateUserStatus(UserStatus userStatus, 
									   Substitutes substitutes)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int	 nResult = -1;
						
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strStatusTable + 
				   	   " SET EMPTY_SET = " + DataConverter.toInt(userStatus.getEmptySet()) + "," +
			   	   	    	"EMPTY_REASON = '" + userStatus.getEmptyReason() + "'," +
			   	   	    	"EMPTY_START_DATE = " + getDateFormat(userStatus.getStartDate()) + "," +
			   	   	    	"EMPTY_END_DATE = " + getDateFormat(userStatus.getEndDate()) +
			   	       " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			bResult = registerSubstitutes(userStatus, substitutes);
			if (bResult == false)
			{
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "UserStatusHandler.updateUserStatus.IsConnectionClosed.",
								   "");
		}	
		return bReturn;
	}
	
	/**
	 * 사용자 대결자 정보 등록  
	 * @param userStatus  사용자 상태 정보 
	 * @param substitutes 대결자 정보 
	 * @return boolean
	 */
	private boolean registerSubstitutes(UserStatus userStatus,
									   	  Substitutes substitutes)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int	 nResult = -1;
					
		if (m_connectionBroker.IsConnectionClosed() == false
			&& substitutes != null && substitutes.size() > 0)
		{
			strQuery = "DELETE " + 
				   	   " FROM " + m_strAssociatedTable +
				   	   " WHERE USER_ID = '" + userStatus.getUserUID() + "'";
				   	   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			for (int i = 0; i < substitutes.size() ; i ++)
			{
				Substitute substitute = (Substitute)substitutes.get(i);
				strQuery = "INSERT INTO " + m_strAssociatedTable + 
									" (" + m_strAssociatedColumns + ")" +
					   	   " VALUES ('" + userStatus.getUserUID() + "'," +
					   	   				  getDateFormat(substitute.getStartDate()) + "," +
					   	   				  getDateFormat(substitute.getEndDate()) + "," +
					   	   			"'" + substitute.getUserUID() + "')";
					   	   			 				
				nResult = m_connectionBroker.executeUpdate(strQuery);
				if(nResult != 1)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearQuery();
					return bReturn;
				}
				
				m_connectionBroker.clearQuery();
			}
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "UserStatusHandler.registerSubstitutes.IsConnectionClosed.",
								   "");
		}	
	 
		return bReturn;
	}
	
	/**
	 * 부재 정보 및 대결자 해제.
	 * @param strUserUID  부재정보를 해제할 사용자 UID
	 * @return boolean
	 */
	public boolean deleteUserStatus(String strUserUID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int	 nResult = -1;
				
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();		
			return bReturn;
		}
				
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		// 대결자 정보 삭제 
		strQuery = "DELETE " + 
			   	   " FROM " + m_strAssociatedTable +
			   	   " WHERE USER_ID = '" + strUserUID + "'";
				
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult == -1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();		
			return bReturn;
		}
		
		m_connectionBroker.clearQuery();
		
		strQuery = "UPDATE " + m_strStatusTable + 
				   " SET EMPTY_SET = 0," + 
				   	   	"EMPTY_REASON = ''," +
				   	   	"EMPTY_START_DATE = " + getDateFormat("") + "," +
				   	   	"EMPTY_END_DATE = " + getDateFormat("") + 
				   " WHERE USER_ID = '" + strUserUID + "'";

		nResult = m_connectionBroker.executeUpdate(strQuery);
		if (nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();		
			return bReturn;	
		}		
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();				
		bReturn = true;
	
		return bReturn;
	}
	
}
