package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.common.vo.StoreServerParam;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.option.*;
import com.sds.acube.app.idir.org.db.*;
import java.util.*;
import java.sql.*;

/**
 * SystemServerHandler.java
 * 2002-12-10
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemServerHandler extends DataHandler
{
	private String m_strSystemServerColumns = "";
	private String m_strSystemServerTable = "";
			
	public SystemServerHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);	
		
		m_strSystemServerTable = TableDefinition.getTableName(TableDefinition.SYSTEMINFO_SERVER);
		m_strSystemServerColumns = SystemServerTableMap.getColumnName(SystemServerTableMap.SYSTEM_ID) + "," +
								   SystemServerTableMap.getColumnName(SystemServerTableMap.SERVER_NAME) + "," +
								   SystemServerTableMap.getColumnName(SystemServerTableMap.SERVER_IP) + "," +
								   SystemServerTableMap.getColumnName(SystemServerTableMap.IS_HOME_SERVER) + "," +
						           SystemServerTableMap.getColumnName(SystemServerTableMap.DB_INFO) + "," +
                                   SystemServerTableMap.getColumnName(SystemServerTableMap.DB_PORT) + "," +
                                   SystemServerTableMap.getColumnName(SystemServerTableMap.DB_LOGIN_ID) + "," +
                                   SystemServerTableMap.getColumnName(SystemServerTableMap.DB_LOGIN_PWD) + "," +
                                   SystemServerTableMap.getColumnName(SystemServerTableMap.DB_TYPE);
	}
	
	/**
	 * ResultSet을 DataLinkedList로 변환
	 * @param resultSet Query 실행 결
	 * @return SystemServers
	 */
	private SystemServers processData(ResultSet resultSet)
	{
		SystemServers  		systemServers = null;
		boolean				bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "SystemServerHandler.processData",
								   "");
			
			return null;
		}
		
		systemServers = new SystemServers();
		
		try
		{
			while(resultSet.next())
			{
				SystemServer systemServer = new SystemServer();
									
				// set SystemServer information
				systemServer.setSystemID(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.SYSTEM_ID)));
				systemServer.setServerName(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.SERVER_NAME)));
				systemServer.setServerIP(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.SERVER_IP)));
				systemServer.setIsHomeServer(getBoolean(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.IS_HOME_SERVER)));
				systemServer.setDBInfo(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.DB_INFO)));
				systemServer.setDBPort(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.DB_PORT)));
				systemServer.setDBLoginID(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.DB_LOGIN_ID)));
				systemServer.setDBLoginPassword(getString(resultSet, SystemServerTableMap.getColumnName(SystemServerTableMap.DB_LOGIN_PWD)));
				systemServer.setDBType(getInt(resultSet,SystemServerTableMap.getColumnName(SystemServerTableMap.DB_TYPE)));
							    								
				systemServers.add(systemServer);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make SystemServer classList.",
								   "SystemServerHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return systemServers;		
	}
		
	/**
	 * System Server Information을 구성하는 함수 
	 * @param arrApprovalServerName 결재 서버명
	 * @param arrStoreServerName	저장 서버명 
	 * @return SystemServerInfo
	 */
	public SystemServerInfo getSystemServerInfo(String[] arrApprovalServerName,
											    String[] arrStoreServerName)
	{
		SystemServerInfo systemServerInfo = null;
		RelatedServers 	 storeServers = null;
		SystemServers	 approvalServers = null;
		boolean	    	 bResult = false;
		String		     strQuery = "";
		String 			 strStoreServers = "";
		String 			 strApprovalServers = "";
		
		if (arrApprovalServerName != null)
		{
			for (int i = 0 ; i < arrApprovalServerName.length ; i++)
			{
				strApprovalServers += "'" + arrApprovalServerName[i] + "',";	
			}
			
			if (strApprovalServers != null && strApprovalServers.length() > 0)
			{
				if (strApprovalServers.substring(strApprovalServers.length()-1).compareTo(",") == 0)
				{
					strApprovalServers = strApprovalServers.substring(0, strApprovalServers.length()-1);	
				}
			}
		}
		
		if (arrStoreServerName != null)
		{
			for (int i = 0 ; i < arrStoreServerName.length ; i++)
			{
				strStoreServers += arrStoreServerName[i] + "^";		
			}
		}
										
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return systemServerInfo;
		}
		
		// 결재 DB 서버 정보 저장
		strQuery = " SELECT " + m_strSystemServerColumns +
				   "   FROM " + m_strSystemServerTable +
				   "  WHERE SYSTEM_ID = 'MULTISTORE'" +
				   "    AND SERVER_NAME IN (" + strApprovalServers + ")";
				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return systemServerInfo;
		}
		
		approvalServers = processData(m_connectionBroker.getResultSet());
		if (approvalServers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return systemServerInfo;
		}
				
		// 결재 저장 서버 정보 저장
		RelatedServerHandler relatedServerHandler = new RelatedServerHandler(m_connectionBroker.getConnectionParam());
		
		storeServers = relatedServerHandler.getRelatedServersBySystemName(RelatedServer.SYSTEM_TYPE_GWSTORE, strStoreServers, m_connectionBroker);
		if (storeServers == null)
		{
			m_lastError.setMessage(relatedServerHandler.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return systemServerInfo;
		}
		
		// 정보 셋팅
		systemServerInfo = new SystemServerInfo();
		
		systemServerInfo.setApprServers(approvalServers);	// 결재 서버 정보
		systemServerInfo.setStoreServers(storeServers);		// 저장 서버 정보
		
		m_connectionBroker.clearConnectionBroker();	 		
		return systemServerInfo;		
	} 
	
	/**
	 * System Server Information을 구성하는 함수 
	 * @param approvalServerNameList 결재 서버명 List
	 * @param arrStoreServerName	저장 서버명 
	 * @return SystemServerInfo
	 */
	public SystemServerInfo getSystemServerInfo(LinkedList approvalServerNameList,
											    LinkedList storeServerNameList)
	{
		SystemServerInfo systemServerInfo = null;
		RelatedServers 	 storeServers = null;
		SystemServers	 approvalServers = null;
		boolean	    	 bResult = false;
		String		     strQuery = "";
		String 			 strStoreServers = "";
		String 			 strApprovalServers = "";
		
		if (approvalServerNameList != null)
		{
			for (int i = 0 ; i < approvalServerNameList.size() ; i++)
			{
				strApprovalServers += "'" + (String)approvalServerNameList.get(i) + "',";	
			}
			
			if (strApprovalServers != null && strApprovalServers.length() > 0)
			{
				if (strApprovalServers.substring(strApprovalServers.length()-1).compareTo(",") == 0)
				{
					strApprovalServers = strApprovalServers.substring(0, strApprovalServers.length()-1);	
				}
			}
		}
		
		if (storeServerNameList != null)
		{
			for (int i = 0 ; i < storeServerNameList.size() ; i++)
			{
				strStoreServers += (String)storeServerNameList.get(i) + "^";		
			}
		}
										
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return systemServerInfo;
		}
		
		// 결재 DB 서버 정보 저장
		strQuery = " SELECT " + m_strSystemServerColumns +
				   "   FROM " + m_strSystemServerTable +
				   "  WHERE SYSTEM_ID = 'MULTISTORE'" +
				   "    AND SERVER_NAME IN (" + strApprovalServers + ")";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return systemServerInfo;
		}
		
		approvalServers = processData(m_connectionBroker.getResultSet());
		if (approvalServers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return systemServerInfo;
		}
				
		// 결재 저장 서버 정보 저장
		RelatedServerHandler relatedServerHandler = new RelatedServerHandler(m_connectionBroker.getConnectionParam());
		
		storeServers = relatedServerHandler.getRelatedServersBySystemName(RelatedServer.SYSTEM_TYPE_GWSTORE, strStoreServers, m_connectionBroker);
		if (storeServers == null)
		{
			m_lastError.setMessage(relatedServerHandler.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return systemServerInfo;
		}
		
		// 정보 셋팅
		systemServerInfo = new SystemServerInfo();
		
		systemServerInfo.setApprServers(approvalServers);	// 결재 서버 정보 
		systemServerInfo.setStoreServers(storeServers);		// 저장 서버 정보
		
		m_connectionBroker.clearConnectionBroker();	 		
		return systemServerInfo;		
	} 
	
	/**
	 * 서버명을 입력받아 해당하는 DB Connection 정보를 추출하는 함수 
	 * @param strServerName
	 * @return ConnectionParam
	 */
	public ConnectionParam getApprovalConnectionByName(String strServerName)
	{
		SystemServerInfo	systemServerInfo = null;
		ConnectionParam 	connectionParam = null;
		SystemServers		servers = null;
		SystemServer		server = null;
		boolean				bResult = false;
		String 				strQuery = "";
		int					nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return connectionParam;
		}
		
		strQuery = " SELECT " + m_strSystemServerColumns +
				   "   FROM " + m_strSystemServerTable +
				   "  WHERE SYSTEM_ID = 'MULTISTORE'" +
				   "    AND SERVER_NAME = '" + strServerName + "'";
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return connectionParam;
		}
		
		servers = processData(m_connectionBroker.getResultSet());
		if (servers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return connectionParam;
		}
		
		nSize = servers.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get correct server infomation.", 
								   "SystemServerHandler.getApprovalConnectionByName.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return connectionParam;	
		}	
		
		server = servers.get(0);
			   
		m_connectionBroker.clearConnectionBroker();
		
		// transfer server information to connectionparam
		systemServerInfo = new SystemServerInfo();	
		connectionParam = systemServerInfo.transferConnectionParam(server);
		
		return connectionParam;
	}
	
	/**
	 * 서버명을 입력받아 해당하는 Store Connection 정보를 추출하는 함수 
	 * @param strServerName
	 * @return StoreServerParam
	 */
	public StoreServerParam getStoreConnectionByName(String strServerName)
	{
		RelatedServerHandler 	relatedServerHandler = null;
		SystemServerInfo		systemServerInfo = null;
		StoreServerParam 		storeServerParam = null;
		RelatedServer 	 		storeServer = null;
		
		relatedServerHandler = new RelatedServerHandler(m_connectionBroker.getConnectionParam());
		storeServer = relatedServerHandler.getRelatedServerBySystemName(RelatedServer.SYSTEM_TYPE_GWSTORE, strServerName);
		
		if (storeServer == null)
		{
			m_lastError.setMessage(relatedServerHandler.getLastError());
			return storeServerParam;		
		}
		
		// transfer server information to storeServerParam
		systemServerInfo = new SystemServerInfo();
		storeServerParam = systemServerInfo.transferStoreServerParam(storeServer);
		
		return storeServerParam;
	}	
}
