package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.io.*;

/**
 * UserRelationHandler.java
 * 2004-12-21
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class UserRelationHandler extends DataHandler
{
	private String m_strUserRelationTable = "";
	private String m_strUserRelationColumnes = "";
	
	public UserRelationHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserRelationTable = TableDefinition.getTableName(TableDefinition.USER_RELATION);
		m_strUserRelationColumnes = UserRelationTableMap.getColumnName(UserRelationTableMap.USER_ID) + "," +
									UserRelationTableMap.getColumnName(UserRelationTableMap.RELATION_ID) + "," +
									UserRelationTableMap.getColumnName(UserRelationTableMap.RELATED_ID) + "," +
									UserRelationTableMap.getColumnName(UserRelationTableMap.RELATION_ORDER) + "," +
									UserRelationTableMap.getColumnName(UserRelationTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserRelations
	 */
	private UserRelations processData(ResultSet resultSet)
	{
		UserRelations  	userRelations = null;
		boolean			bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserRelationHandler.processData",
								   "");
			
			return null;
		}
		
		userRelations = new UserRelations();
		
		try
		{
			while(resultSet.next())
			{
				UserRelation userRelation = new UserRelation();
									
				// set Relation information
				userRelation.setUserUID(getString(resultSet, UserRelationTableMap.getColumnName(UserRelationTableMap.USER_ID)));
				userRelation.setRelationID(getString(resultSet, UserRelationTableMap.getColumnName(UserRelationTableMap.RELATION_ID)));
				userRelation.setRelatedUID(getString(resultSet, UserRelationTableMap.getColumnName(UserRelationTableMap.RELATED_ID)));
				userRelation.setRelationOrder(getInt(resultSet, UserRelationTableMap.getColumnName(UserRelationTableMap.RELATION_ORDER)));
				userRelation.setDescription(getString(resultSet, UserRelationTableMap.getColumnName(UserRelationTableMap.DESCRIPTION)));
								
				userRelations.add(userRelation);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserRelation classList.",
								   "UserRelationHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return userRelations;				
	} 
	
	/**
	 * 주어진 사용자 UID와 관계있는 모든 관계 정보. 
	 * @param strUserUID 사용자 UID
	 * @return UserRelations
	 */
	public UserRelations getUserRelations(String strUserUID)
	{
		UserRelations 	userRelations = null;
		boolean   		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQuery = "SELECT " + m_strUserRelationColumnes +
				   " FROM " + m_strUserRelationTable + 
				   " WHERE USER_ID = '" + strUserUID + "'" +
				   " ORDER BY RELATION_ORDER ";
				    
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userRelations = processData(m_connectionBroker.getResultSet());
		if (userRelations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
	
		m_connectionBroker.clearConnectionBroker();	
		
		return userRelations;
	}
	
	/**
	 * 주어진 사용자 UID와 관계 ID에 해당하는 관계 정보.
	 * @param strUserUID 사용자 UID
	 * @param strRelationID 관계 ID
	 * @return UserRelations
	 */
	public UserRelations getUserRelations(String strUserUID, String strRelationID)
	{
		UserRelations 	userRelations = null;
		boolean   		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQuery = "SELECT " + m_strUserRelationColumnes +
				   " FROM " + m_strUserRelationTable + 
				   " WHERE USER_ID = '" + strUserUID + "'" +
				   "   AND RELATION_ID = '" + strRelationID + "'" +
				   " ORDER BY RELATION_ORDER ";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		userRelations = processData(m_connectionBroker.getResultSet());
		if (userRelations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
	
		m_connectionBroker.clearConnectionBroker();	
		
		return userRelations;
	}
}
