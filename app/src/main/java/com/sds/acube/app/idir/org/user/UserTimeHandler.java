package com.sds.acube.app.idir.org.user;

/**
 * UserTimeHandler.java
 * 2002-11-06
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

public class UserTimeHandler extends DataHandler
{
	private String m_strTimeTable = "";
	private String m_strTimeColumns = "";
	
	public UserTimeHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strTimeTable = TableDefinition.getTableName(TableDefinition.USER_TIME);
		m_strTimeColumns = 	UserTimeTableMap.getColumnName(UserTimeTableMap.USER_ID) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_CREATED) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_CHANGED) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_CONCURRENT) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_PROXY) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_DELEGATE) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGON) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGOUT) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGOUT_IP) + "," +
							UserTimeTableMap.getColumnName(UserTimeTableMap.ASSIGNED_DATE);
	
	}
		
	/**
	 * ResultSet을 UserTime Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserTime
	 */
	private UserTime processData(ResultSet resultSet)
	{
		UserTime		userTime = null;
		boolean			bResult = false;
		int				nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserTimeHandler.processData",
								   "");
			
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{
				userTime = new UserTime();
				
				nCount++;
							
				// set user time information
				userTime.setUserUID(getString(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.USER_ID)));
				userTime.setWhenCreated(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_CREATED), TIMESTAMP_SECOND));
				userTime.setWhenChanged(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_CHANGED), TIMESTAMP_SECOND));
				userTime.setWhenDeleted(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED), TIMESTAMP_SECOND));
				userTime.setWhenDeletedConcurrent(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_CONCURRENT), TIMESTAMP_SECOND));
				userTime.setWhenDeletedProxy(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_PROXY), TIMESTAMP_SECOND));
				userTime.setWhenDeletedDelegate(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.WHEN_DELETED_DELEGATE), TIMESTAMP_SECOND));
				userTime.setLastLogon(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGON), TIMESTAMP_SECOND));
				userTime.setLastLogout(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGOUT), TIMESTAMP_SECOND));
				userTime.setLastLogoutIP(getString(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.LAST_LOGOUT_IP)));
				userTime.setAssignedDate(getDate(resultSet, UserTimeTableMap.getColumnName(UserTimeTableMap.ASSIGNED_DATE), TIMESTAMP_SECOND));
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserTime class.",
								   "UserTimeHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user time.",
								   "UserTimeHandler.processData.unique UserTime",
								   "");
			return null;
		}
			
		return userTime;				
	} 
	
	/**
	 * 사용자의 Time 정보 추출 
	 * @param strUserUID 	사용자 UID
	 * @return UserTime
	 */
	public UserTime getUserTime(String strUserUID)
	{
		UserTime 		userTime = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strTimeColumns +
				   " FROM " + m_strTimeTable +
				   " WHERE USER_ID = ?";
				   	 				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}

		m_connectionBroker.setString(1, strUserUID);
				   		   				 
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		userTime = processData(m_connectionBroker.getResultSet());
		if (userTime == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	
		
		return userTime;			
	}
	
	/**
	 * 사용자의 login 시간 등록
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	public boolean setUserLogOnTime(String strUserUID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		boolean bFound = false;
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		String 	 strQuery = "";
		int	 nResult = -1;
		
		if ((strUserUID == null) || (strUserUID.length() == 0)) 
		{
			m_lastError.setMessage("Fail to make UserTime class.",
								   "UserTimeHandler.processData",
								   "");
			return false;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return bReturn;
		}
		
		bFound = checkUserTime(strUserUID);
		
		m_connectionBroker.setAutoCommit(false);
		if (!bFound)
		{
			bResult = createUserTime(strUserUID);
			if (bResult == false) 
			{
				m_connectionBroker.clearConnectionBroker();	 
				return bReturn;	
			}
		}
		
		strQuery = "UPDATE " + m_strTimeTable +
				   "   SET LAST_LOGON = " + getDateFormat() +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearQuery();	
			return bReturn;
		}

		m_connectionBroker.setString(1, strToday);
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
	 * 사용자의 logout 시간 등록
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	public boolean setUserLogOutTime(String strUserUID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		boolean bFound = false;
		int 	nResult = 0;
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		String 	 strQuery = "";
		
		if ((strUserUID == null) || (strUserUID.length() == 0)) 
		{
			m_lastError.setMessage("Fail to make UserTime class.",
								   "UserTimeHandler.processData",
								   "");
			return false;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return bReturn;
		}
		
		bFound = checkUserTime(strUserUID);
		
		m_connectionBroker.setAutoCommit(false);
		if (bFound == false)
		{
			bResult = createUserTime(strUserUID);
			if (bResult == false) 
			{
				m_connectionBroker.clearConnectionBroker();	 
				return bReturn;	
			}
		}
		
		strQuery = "UPDATE " + m_strTimeTable +
				   "   SET LAST_LOGOUT = " + getDateFormat() +
				   " WHERE USER_ID = ?";
				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setString(1, strToday);
		m_connectionBroker.setString(2, strUserUID);
				   		   				   
		nResult = m_connectionBroker.executePreparedUpdate();
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		bReturn = true;
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();
		
		return bReturn;
	}
	
	/**
	 * 사용자의 Time 정보 Row 생성
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	private boolean createUserTime(String strUserUID)
	{
		boolean 	bResult = false;
		String   	strToday = getToday("yyyy-MM-dd HH:mm:ss");
		String 		strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "INSERT INTO " + m_strTimeTable + 
					   "(USER_ID, WHEN_CREATED)" +
					   " VALUES (?, " + getDateFormat() + ")";
					   
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false) 
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	
				return bResult;
			}

			m_connectionBroker.setString(1, strUserUID);
			m_connectionBroker.setString(2, strToday);
					   		   				 
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return bResult;
			}
			
			m_connectionBroker.clearQuery();
			return true;
		}
		
		return false;
	} 
	
	/**
	 * 사용자 UserTime Row 생성 Check
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	private boolean checkUserTime(String strUserUID)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{		
			strQuery = "SELECT " + m_strTimeColumns +
					   " FROM " + m_strTimeTable +
					   " WHERE USER_ID = ?";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false) 
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	
				return bFound;
			}

			m_connectionBroker.setString(1, strUserUID);
					   		   				 
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return bFound;
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
				m_lastError.setMessage("Fail to get next resultset",
									   "UserTimeHandler.checkUserTime.next",
									   e.getMessage());
				m_connectionBroker.clearQuery();
				
			}
			m_connectionBroker.clearQuery();
			
		}
		
		return bFound;	
	}
	
	/**
	 * 사용자 Login Data를 기록하는 함수 
	 * @param strUserUID 사용자 UID
	 * @param strLoginIP Login IP
	 * @param connectionBroker DB Connection이 Open되어 있는 Connection Broker Object
	 * @return boolean
	 */
	public boolean setUserLogOnData(String strUserUID, String strLoginIP, 
									  ConnectionBroker connectionBroker)
	{
		boolean	 bResult = false;
		int 	 nReturn = 0;
		String   strQuery = "";
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strTimeTable +
					   " SET LAST_LOGON = " + getDateFormat(strToday) + "," +
					   "     LAST_LOGOUT_IP = ?" +   
					   " WHERE USER_ID = ?";
			
			bResult = connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
			
			connectionBroker.setString(1, strLoginIP);
			connectionBroker.setString(2, strUserUID);
			
			nReturn = connectionBroker.executePreparedUpdate();
			if (nReturn != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
		
			connectionBroker.commit();
			connectionBroker.clearPreparedQuery();					
		}
		
		return true;		
	}
	
	/**
	 * 사용자 Logout Data를 기록하는 함수 
	 * @param strUserUID 사용자 UID
	 * @param strLoginIP Login IP
	 * @param connectionBroker DB Connection이 Open되어 있는 Connection Broker Object
	 * @return boolean
	 */
	public boolean setUserLogOutData(String strUserUID, String strLoginIP, 
									 ConnectionBroker connectionBroker)
	{
		boolean	 bResult = false;
		int 	 nReturn = 0;
		String   strQuery = "";
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strTimeTable +
					   " SET LAST_LOGOUT = " + getDateFormat(strToday) + "," +
					   "     LAST_LOGOUT_IP = ?" +   
					   " WHERE USER_ID = ?";
			
			bResult = connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
			
			connectionBroker.setString(1, strLoginIP);
			connectionBroker.setString(2, strUserUID);
			
			nReturn = connectionBroker.executePreparedUpdate();
			if (nReturn != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
		
			connectionBroker.commit();
			connectionBroker.clearPreparedQuery();					
		}
		
		return true;		
	}
	
	/**
	 * 사용자 Login Time 기록하는 함수
	 * @param strUserUID		사용자 UID
	 * @param connectionBroker DB Connection이 Open되어 있는 Connection Broker Object
	 * @return boolean
	 */
	public boolean setUserLogOnTime(String strUserUID, ConnectionBroker connectionBroker)
	{
		boolean bResult = false;
		int nReturn = 0;
		String   strQuery = "";
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strTimeTable +
					   " SET LAST_LOGON = " + getDateFormat() +
					   " WHERE USER_ID = ?";
			
			bResult = connectionBroker.prepareStatement(strQuery);
			if (bResult == false) 
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;
			}
		   
			connectionBroker.setString(1, strToday);
			connectionBroker.setString(2, strUserUID);
			
			nReturn = connectionBroker.executePreparedUpdate();
			if (nReturn != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
		
			connectionBroker.commit();
			connectionBroker.clearPreparedQuery();					
		}
		
		return true;
	}
	
	/**
	 * 사용자 Logout Time 기록하는 함수
	 * @param strUserUID		사용자 UID
	 * @param connectionBroker DB Connection이 Open되어 있는 Connection Broker Object
	 * @return boolean
	 */
	public boolean setUserLogOutTime(String strUserUID, ConnectionBroker connectionBroker)
	{
		boolean bResult = false;
		int nReturn = 0;
		String   strQuery = "";
		String   strToday = getToday("yyyy-MM-dd HH:mm:ss");
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strTimeTable +
					   " SET LAST_LOGOUT = " + getDateFormat() +
					   " WHERE USER_ID = ?";
			
			bResult = connectionBroker.prepareStatement(strQuery);
			if (bResult == false) 
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;
			}
		   
			connectionBroker.setString(1, strToday);
			connectionBroker.setString(2, strUserUID);
			
			nReturn = connectionBroker.executePreparedUpdate();
			if (nReturn != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.rollback();
				connectionBroker.clearPreparedQuery();	
				return false;				
			}
		
			connectionBroker.commit();
			connectionBroker.clearPreparedQuery();					
		}
		
		return true;
	}
}
