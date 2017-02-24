package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.io.*;

/**
 * UserProleHandler.java
 * 2004-12-22
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserProleHandler extends DataHandler
{
	private String m_strUserProleTable = "";
	private String m_strUserProleColumnes = "";
	
	public UserProleHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserProleTable = TableDefinition.getTableName(TableDefinition.USER_PROLE);
		m_strUserProleColumnes = UserProleTableMap.getColumnName(UserProleTableMap.USER_ID) + "," +
								 UserProleTableMap.getColumnName(UserProleTableMap.PROLE_ID) + "," +
								 UserProleTableMap.getColumnName(UserProleTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserProles
	 */
	private UserProles processData(ResultSet resultSet)
	{
		UserProles  userProles = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserProleHandler.processData",
								   "");
			
			return null;
		}
		
		userProles = new UserProles();
		
		try
		{
			while(resultSet.next())
			{
				UserProle userProle = new UserProle();
									
				// set Prole information
				userProle.setUserUID(getString(resultSet, UserProleTableMap.getColumnName(UserProleTableMap.USER_ID)));
				userProle.setProleID(getString(resultSet, UserProleTableMap.getColumnName(UserProleTableMap.PROLE_ID)));
				userProle.setDescription(getString(resultSet, UserProleTableMap.getColumnName(UserProleTableMap.DESCRIPTION)));
											
				userProles.add(userProle);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserProle classList.",
								   "UserProleHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return userProles;				
	} 
	
	/**
	 * 주어진 사용자 UID와 관계있는 모든 프로세스 역할 정보. 
	 * @param strUserUID 사용자 UID
	 * @return UserProles
	 */
	public UserProles getUserProles(String strUserUID)
	{
		UserProles 		userProles = null;
		boolean   		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQuery = "SELECT " + m_strUserProleColumnes +
				   " FROM " + m_strUserProleTable + 
				   " WHERE USER_ID = '" + strUserUID + "'";
				    
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userProles = processData(m_connectionBroker.getResultSet());
		if (userProles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
	
		m_connectionBroker.clearConnectionBroker();	
		
		return userProles;
	}
	
	/**
	 * 주어진 프로세스 역할 ID와 관계있는 모든 프로세스 역할 정보. 
	 * @param strProleID 프로세스 역할 ID
	 * @return UserProles
	 */
	public UserProles getUserProlesByProleID(String strProleID)
	{
		UserProles 		userProles = null;
		boolean   		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQuery = "SELECT " + m_strUserProleColumnes +
				   " FROM " + m_strUserProleTable + 
				   " WHERE PROLE_ID = '" + strProleID + "'";
				    
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userProles = processData(m_connectionBroker.getResultSet());
		if (userProles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
	
		m_connectionBroker.clearConnectionBroker();	
		
		return userProles;
	}
}
