package com.sds.acube.app.idir.org.hierarchy;

import java.sql.ResultSet;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.TableDefinition;
import com.sds.acube.app.idir.org.db.DataHandler;

/**
 * RoleHandler.java
 * 2007-01-13
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class RoleHandler  extends DataHandler
{
	private String m_strRoleColumns = "";
	private String m_strRoleTable = "";
	
	public RoleHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strRoleTable = TableDefinition.getTableName(TableDefinition.ROLE);
		m_strRoleColumns = RoleTableMap.getColumnName(RoleTableMap.ROLE_ID) + "," +
						   RoleTableMap.getColumnName(RoleTableMap.ROLE_NAME) + "," +
						   RoleTableMap.getColumnName(RoleTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Roles
	 */
	private Roles processData(ResultSet resultSet)
	{
		Roles  roles = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "RoleHandler.processData",
								   "");
			
			return null;
		}
		
		roles = new Roles();
		
		try
		{
			while(resultSet.next())
			{
				Role role = new Role();
									
				// set Role information
				role.setRoleID(getString(resultSet, RoleTableMap.getColumnName(RoleTableMap.ROLE_ID)));
				role.setRoleName(getString(resultSet, RoleTableMap.getColumnName(RoleTableMap.ROLE_NAME)));
				role.setDescription(getString(resultSet, RoleTableMap.getColumnName(RoleTableMap.DESCRIPTION)));
				
				roles.add(role);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make roles.",
								   "RoleHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return roles;				
	}
	
	/**
	 * 선택한 Role ID를 가지는 롤  정보 얻음
	 * @param strRoleID Role ID
	 * @return Role
	 */	
	public Role getRole(String strRoleID)
	{
		Roles 			roles = null;
		Role  			role = null;
		boolean	    	bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		// 입력 조건 검사
		if ((strRoleID == null) || (strRoleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get role ID.", 
								   "RoleHandler.getRole.Empty Role ID", 
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strRoleColumns +
				   " FROM " + m_strRoleTable +
				   " WHERE ROLE_ID = '" + strRoleID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		roles = processData(m_connectionBroker.getResultSet());
		if (roles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = roles.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct role information.", 
								   "RoleHandler.getRole.Linkedist.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		role = roles.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return role;
	} 
		
	/**
	 * 모든 역할 정보를 반환하는 함수.
	 * @return Roles
	 */
	public Roles getAllRoles()
	{
		Roles 			roles = null;
		boolean	    	bResult = false;
		String		    strQuery = "";

		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strRoleColumns +
				   " FROM " + m_strRoleTable +
				   " ORDER BY ROLE_NAME ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		roles = processData(m_connectionBroker.getResultSet());
		if (roles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return roles;	
	}
}
