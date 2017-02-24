package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

/**
 * PortalGroupHandler.java
 * 2005-09-26
 * 
 * 포탈 그룹에 대한 Table Map
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PortalGroupHandler extends DataHandler
{
	private String m_strPortalGroupColumns = "";
	private String m_strPortalGroupTable = "";
	
	public PortalGroupHandler(ConnectionParam connectionParam) 
	{
		super(connectionParam);
		
		m_strPortalGroupTable = TableDefinition.getTableName(TableDefinition.PORTAL_GROUP);
		m_strPortalGroupColumns = PortalGroupTableMap.getColumnName(PortalGroupTableMap.PORTAL_ID) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.PARENT_PORTAL_ID) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.PORTAL_TYPE) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.ORG_ID) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.PARENT_ORG_ID) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.ORG_TYPE) + "," +
								  PortalGroupTableMap.getColumnName(PortalGroupTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return PortalGroups
	 */
	private PortalGroups processData(ResultSet resultSet)
	{
		PortalGroups  	portalGroups = null;
		boolean			bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "PortalGroupHandler.processData",
								   "");
			
			return null;
		}
		
		portalGroups = new PortalGroups();
		
		try
		{
			while(resultSet.next())
			{
				PortalGroup portalGroup = new PortalGroup();
									
				// set Portal Group information
				portalGroup.setPortalID(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.PORTAL_ID)));
				portalGroup.setParentPortalID(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.PARENT_PORTAL_ID)));
				portalGroup.setPortalType(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.PORTAL_TYPE)));
				portalGroup.setOrgID(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.ORG_ID)));
				portalGroup.setParentOrgID(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.PARENT_ORG_ID)));
				portalGroup.setOrgType(getInt(resultSet,PortalGroupTableMap.getColumnName(PortalGroupTableMap.ORG_TYPE)));
				portalGroup.setDescription(getString(resultSet, PortalGroupTableMap.getColumnName(PortalGroupTableMap.DESCRIPTION)));
												
				portalGroups.add(portalGroup);

			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to make portal groups.",
								   "PortalGroupHandler.processData.SQLException",
								   e.getMessage());
			
			return null;
		}	
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make portal groups.",
								   "PortalGroupHandler.processData.Exception",
								   e.getMessage());
			
			return null;
		}	
		
		return portalGroups;				
	}
	
	/**
	 * 주어진 Portal ID를 가지는 Group Portal정보를 가져오는 함수.
	 * @param strPortalID Portal ID
	 * @return GroupPortal
	 */
	public PortalGroup getPortalGroup(String strPortalID)
	{
		PortalGroups	portalGroups = null;
		PortalGroup		portalGroup = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		if (strPortalID == null || strPortalID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Portal Group ID.",
								   "PortalGroupHandler.getPortalGroup.Empty Portal Group ID",
								   "");
			return portalGroup;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroup;
		}
		
		strQuery = "SELECT " + m_strPortalGroupColumns +
				   " FROM  " + m_strPortalGroupTable +
				   " WHERE PORTAL_ID = '" + strPortalID + "'";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroup;
		}
		
		portalGroups = processData(m_connectionBroker.getResultSet());
		if (portalGroups == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (portalGroups.size() != 1)
		{
			m_lastError.setMessage("Fail to get portal group information (not unique)",
								   "PortalGroupHandler.getPortalGroup.not unique",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		portalGroup = portalGroups.get(0);

		return portalGroup;
	}
	
	/**
	 * 상위 Portal ID를 가지는 하위 Portal Group정보를 가져오는 함수 
	 * @param strParentPortalID Parent Portal ID
	 * @return PortalGroups
	 */
	public PortalGroups getSubPortalGroups(String strParentPortalID)
	{
		PortalGroups	portalGroups = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		if (strParentPortalID == null || strParentPortalID.length() == 0)
		{
			m_lastError.setMessage("Fail to get parent portal group ID.",
								   "PortalGroupHandler.getSubPortalGroups.Empty Parent Portal ID",
								   "");
			return portalGroups;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
		
		strQuery = "SELECT " + m_strPortalGroupColumns +
				   " FROM  " + m_strPortalGroupTable +
				   " WHERE PARENT_PORTAL_ID = '" + strParentPortalID + "'";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
		
		portalGroups = processData(m_connectionBroker.getResultSet());
		if (portalGroups == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
	
		m_connectionBroker.clearConnectionBroker();
		
		return portalGroups;
	}
	
	/**
	 * 모든 Portal Group 정보를 반환하는 함수.
	 * @return PortalGroups
	 */
	public PortalGroups getAllPortalGroups() 
	{
		PortalGroups	portalGroups = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
		
		strQuery = "SELECT " + m_strPortalGroupColumns +
				   " FROM  " + m_strPortalGroupTable  +
				   " ORDER BY PORTAL_TYPE ";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
		
		portalGroups = processData(m_connectionBroker.getResultSet());
		if (portalGroups == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return portalGroups;
		}
	
		m_connectionBroker.clearConnectionBroker();
		
		return portalGroups;
	}	
}
