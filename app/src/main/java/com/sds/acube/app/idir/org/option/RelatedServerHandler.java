package com.sds.acube.app.idir.org.option;

/**
 * RelatedServerHandler.java
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
import com.sds.acube.app.idir.common.vo.StoreServerParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class RelatedServerHandler extends DataHandler
{
	
	private String m_strServerColumns = "";
	private String m_strServerTable = "";
	
	public RelatedServerHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strServerTable = TableDefinition.getTableName(TableDefinition.SERVER);
		m_strServerColumns = RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_ID) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.SYSTEM_NAME) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_NAME) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_IP) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.CONNECTION_INFO) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_TYPE) + "," +
							 RelatedServerTableMap.getColumnName(RelatedServerTableMap.DESCRIPTION);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return RelatedServers
	 */
	private RelatedServers processData(ResultSet resultSet)
	{
		RelatedServers  relatedServers = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "RelatedServerHandler.processData",
								   "");
			
			return null;
		}
		
		relatedServers = new RelatedServers();
		
		try
		{
			while(resultSet.next())
			{
				RelatedServer relatedServer = new RelatedServer();
									
				// set RelatedServer information
				relatedServer.setServerID(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_ID)));
				relatedServer.setSystemName(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.SYSTEM_NAME)));
				relatedServer.setServerName(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_NAME)));
				relatedServer.setServerIP(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_IP)));
				relatedServer.setConnectionInfo(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.CONNECTION_INFO)));
				relatedServer.setServerType(getInt(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.SERVER_TYPE)));
				relatedServer.setDescription(getString(resultSet, RelatedServerTableMap.getColumnName(RelatedServerTableMap.DESCRIPTION)));
					
				relatedServers.add(relatedServer);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make member classList.",
								   "RelatedServerHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return relatedServers;				
	} 
	
	/**
	 * 서버 정보 리스트 반환
	 * @return RelatedServers
	 */
	public RelatedServers getRelatedServers()
	{
		RelatedServers 	relatedServers = null;
		boolean  		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strServerColumns +
				   " FROM " + m_strServerTable;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		relatedServers = processData(m_connectionBroker.getResultSet());
		if (relatedServers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return relatedServers;
	}
		
	/**
	 * 주어진 시스템명을 가진 서버 정보 반환
	 * @param strSystemName 	시스템명 
	 * @param connectionBroker 	DB Connection이 Open되어 있는 Connection Broker Object
	 * @return RelatedServers
	 */
	public RelatedServers getRelatedServersBySystemName(String strSystemName, ConnectionBroker connectionBroker)
	{
		RelatedServers 	relatedServers = null;
		boolean 		bReturn = false;
		String   		strQuery = "";
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strServerColumns +
				   	   " FROM " + m_strServerTable +
					   " WHERE SYSTEM_NAME = '" + strSystemName + "'";
					   
			bReturn = connectionBroker.excuteQuery(strQuery);
			if (!bReturn)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	
				return relatedServers;				
			}
			
			relatedServers = processData(connectionBroker.getResultSet());
			if (relatedServers == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	 
				return relatedServers;
			}
		
			connectionBroker.clearQuery();					
		}
		else
		{
			m_lastError.setMessage("Fail to get open DB Connection.",
								   "RelatedServerHandler.getRelatedServersBySystemName.closed connection",
								   "");
		}
		
		return relatedServers;
	}
	
	/**
	 * 주어진 시스템명과 주어진 서버명을 가진 서버 정보 반환
	 * @param strSystemName 	시스템명 
	 * @param strServerNames	서버명 
	 * @param connectionBroker 	DB Connection이 Open되어 있는 Connection Broker Object
	 * @return RelatedServers
	 */
	public RelatedServers getRelatedServersBySystemName(String strSystemName, String strServerNames, ConnectionBroker connectionBroker)
	{
		RelatedServers 	relatedServers = null;
		boolean 		bReturn = false;
		String   		strQuery = "";
		
		
		if (strSystemName == null || strSystemName.length() == 0)
		{
			m_lastError.setMessage("Fail to get system name.",
								   "RelatedServerHandler.getRelatedServersBySystemName.Empty System Name.",
								   "");	
			return relatedServers;
		}
		
		if (strServerNames == null || strServerNames.length() == 0)
		{
			m_lastError.setMessage("Fail to get server names.",
								   "RelatedServerHandler.getRelatedServersBySystemName.Empty Server Name.",
								   "");	
			return relatedServers;

		}
		
		if (strServerNames != null && strServerNames.length() > 0)
		{
			strServerNames = DataConverter.replace(strServerNames, "^", "','");
			
			if (strServerNames.substring(0, 1).compareTo("'") == 0)
			{
				strServerNames = "'" + strServerNames;	
			}
			
			if (strServerNames.substring(strServerNames.length() - 2).compareTo(",'") == 0)
			{
				strServerNames = strServerNames.substring(0, strServerNames.length() - 2);
			}
		}
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strServerColumns +
				   	   "  FROM " + m_strServerTable +
					   " WHERE SYSTEM_NAME = '" + strSystemName + "'" +
					   "   AND SERVER_NAME IN (" + strServerNames + ")"; 
					   
			bReturn = connectionBroker.excuteQuery(strQuery);
			if (bReturn == false)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	
				return relatedServers;				
			}
			
			relatedServers = processData(connectionBroker.getResultSet());
			if (relatedServers == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	 
				return relatedServers;
			}
		
			connectionBroker.clearQuery();					
		}
		else
		{
			m_lastError.setMessage("Fail to get open DB Connection.",
								   "RelatedServerHandler.getRelatedServersBySystemName.closed connection",
								   "");
		}
		
		return relatedServers;
	} 	
	
	/**
	 * 주어진 시스템명과 주어진 서버명을 가진 서버 정보 반환
	 * @param strSystemName 	시스템명 
	 * @param strServerName		서버명 
	 * @return RelatedServer
	 */
	public RelatedServer getRelatedServerBySystemName(String strSystemName, String strServerName)
	{
		RelatedServers 	relatedServers = null;
		RelatedServer	relatedServer = null;
		boolean 		bReturn = false;
		boolean			bResult = false;
		String   		strQuery = "";
		int				nSize = 0;
		
		if (strSystemName == null || strSystemName.length() == 0)
		{
			m_lastError.setMessage("Fail to get system name.",
								   "RelatedServerHandler.getRelatedServerBySystemName.Empty System Name.",
								   "");	
			return relatedServer;
		}
		
		if (strServerName == null || strServerName.length() == 0)
		{
			m_lastError.setMessage("Fail to get server name.",
								   "RelatedServerHandler.getRelatedServerBySystemName.Empty Server Name.",
								   "");	
			return relatedServer;

		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return relatedServer;
		}
		
		strQuery = "SELECT " + m_strServerColumns +
			   	   "  FROM " + m_strServerTable +
				   " WHERE SYSTEM_NAME = '" + strSystemName + "'" +
				   "   AND SERVER_NAME = '" + strServerName + "'"; 
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return relatedServer;
		}
		
		relatedServers = processData(m_connectionBroker.getResultSet());
		if (relatedServers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return relatedServer;
		}
		
		nSize = relatedServers.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get correct server infomation.", 
								   "RelatedServerHandler.getRelatedServerBySystemName.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return relatedServer;			
		}
		
		relatedServer = relatedServers.get(0);
		
		m_connectionBroker.clearConnectionBroker();
		
		return relatedServer;
	} 	
}
