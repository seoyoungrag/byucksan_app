package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgRoleHandler.java
 * 2011-04-29
 * 
 * 
 * 
 * @author geena
 * @version 1.0.0.0
 *
 * Copyright 2011 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.TableDefinition;
import com.sds.acube.app.idir.org.db.DataHandler;

public class OrgRoleHandler extends DataHandler
{
	private String m_strOrgRoleColumns = "";
	private String m_strOrgRoleTable = "";
	
	public OrgRoleHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		m_strOrgRoleTable = TableDefinition.getTableName(TableDefinition.ORG_ROLE);
		m_strOrgRoleColumns = OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_ID) + "," +
								 OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_NAME) + "," +
								 OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_OTHER_NAME) + "," +
								 OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_ORDER) + "," +
								 OrgRoleTableMap.getColumnName(OrgRoleTableMap.DESCRIPTION);
	}
	
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return OrgRoles
	 */
	private OrgRoles processData(ResultSet resultSet)
	{
		OrgRoles  	orgRoles = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "OrgRoleHandler.processData",
								   "");
			
			return null;
		}
		
		orgRoles = new OrgRoles();
		
		try
		{
			while(resultSet.next())
			{
				OrgRole orgRole = new OrgRole();
									
				// set OrgRole information
				orgRole.setRoleID(getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_ID)));
				orgRole.setRoleName(getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_NAME)));
				orgRole.setRoleOtherName(getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_OTHER_NAME)));
				orgRole.setRoleOrder(getInt(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_ORDER)));
				orgRole.setDescription(getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.DESCRIPTION)));

				orgRoles.add(orgRole);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process OrgRole list.",
								   "OrgRoleHandler.processData",
								   e.getMessage());
			
			return null;
		}
		
		return orgRoles;
	}
	

	/**
	 * 조직역할 정보 반환
	 * @param strRoleID
	 * @return OrgRole
	 */
	public OrgRole getOrgRole(String strRoleID)
	{
		OrgRoles 	orgRoles 	= null;
		OrgRole 	orgRole 	= null;
		boolean 	bResult 	= false;
		String 		strQuery 	= "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = " SELECT " + m_strOrgRoleColumns +
				   " FROM " + m_strOrgRoleTable +
				   " WHERE ROLE_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strRoleID);
				   		   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		orgRoles = processData(m_connectionBroker.getResultSet());
		if (orgRoles == null || orgRoles.size() == 0)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (orgRoles.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique department information.",
								   "DepartmentHandler.getDepartment.processData",
								   "");
			m_connectionBroker.clearConnectionBroker();
		}
		
		orgRole = orgRoles.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
				
		return orgRole;
	}
	
	/**
	 * 해당 조직에 부여된 조직역할 정보 가져오기
	 * @param strOrgID
	 * @return OrgRoles
	 */
	public OrgRoles getOrgRolesByOrgID(String strOrgID)
	{
		OrgRoles 	orgRoles = null;
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = " SELECT ROLE_ID " +
				" FROM TCN_ORGINFORMATION_ROLE " + 
				" WHERE ORG_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strOrgID);
				   		   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		List orgIdList = new ArrayList();
		resultSet = m_connectionBroker.getResultSet();
		
		try {
			while(resultSet.next())
			{
				String role_id = getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ROLE_ID));
				orgIdList.add(role_id);
			}
		} catch (SQLException e) {
			m_lastError.setMessage("Fail to get ROLE ID.",
					   "OrgRoleHandler.getOrgRolesByOrgID.SQLException",
					   e.getMessage());
			m_connectionBroker.clearConnectionBroker();			
			return null;
		}
		
		if(orgIdList.size() < 1){
			m_lastError.setMessage("No Org Role infomation by "+ strOrgID);
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		String strOrgIds = "";
		for (int i = 0; i < orgIdList.size(); i++) {
			strOrgIds += (strOrgIds.length() > 1) ? "," : "";
			strOrgIds += "'" + orgIdList.get(i) + "'";
		}
		
		strQuery = " SELECT " + m_strOrgRoleColumns +
				   " FROM " + m_strOrgRoleTable +
				   " WHERE ROLE_ID in ("+ strOrgIds + ")";

		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}

		orgRoles = processData(m_connectionBroker.getResultSet());
		if (orgRoles == null || orgRoles.size() < 1) {
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearQuery();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		return orgRoles;
	}
	
	/**
	 * 조직역할에 해당하는 조직정보들 가져오기
	 * @param strOrgID
	 * @return List
	 */
	public List getOrgIdListByOrgRoleID(String strRoleID)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		List orgIdList = new ArrayList();
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = " SELECT ORG_ID " + 
					" FROM TCN_ORGINFORMATION_ROLE " + 
					" WHERE ROLE_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strRoleID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		resultSet = m_connectionBroker.getResultSet();
		
		try {
			while(resultSet.next())
			{
				String org_id = getString(resultSet, OrgRoleTableMap.getColumnName(OrgRoleTableMap.ORG_ID));
				orgIdList.add(org_id);
			}
		} catch (SQLException e) {
			m_lastError.setMessage("Fail to get ORG ID.",
					   "OrgRoleHandler.getOrgIdListByOrgRoleID.SQLException",
					   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
		}
		
		m_connectionBroker.clearConnectionBroker();
		
		return orgIdList;
	}
}

