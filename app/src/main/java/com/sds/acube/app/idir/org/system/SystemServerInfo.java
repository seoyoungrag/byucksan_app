package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.org.option.RelatedServers;
import com.sds.acube.app.idir.org.option.RelatedServer;
import com.sds.acube.app.idir.common.vo.*;
import java.util.*;

/**
 * SystemServerInfo.java 2003-12-10
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemServerInfo 
{		
	public static final String[] m_arrClassName =
	{
		"oracle.jdbc.OracleDriver",
		"com.inet.tds.TdsDriver"
	};

	public static final String[] m_arrURLHeader =
	{
		"jdbc:oracle:thin:@",
		"jdbc:inetdae7:"
	};
	
	/**
	 */
	private SystemServers 	m_apprServers = null;			// DB 서버 정보 
	/**
	 */
	private RelatedServers 	m_storeServers = null;			// 저장 서버 정보 
	
	/**
	 * 결재 서버정보 설정.
	 * @param apprServers 결재 서버정보
	 */
	public void setApprServers(SystemServers apprServers) 
	{
		m_apprServers = apprServers;
	}

	/**
	 * 저장 서버정보 설정.
	 * @param storeServers 저장 서버정보
	 */
	public void setStoreServers(RelatedServers storeServers) 
	{
		m_storeServers = storeServers;
	}
	
	/**
	 * 결재 서버정보 반환.
	 * @return SystemServers
	 */
	public SystemServers getApprServers() 
	{
		return m_apprServers;
	}

	/**
	 * 저장 서버 정보 반환.
	 * @return RelatedServers
	 */
	public RelatedServers getStoreServers() 
	{
		return m_storeServers;
	}
	
	/**
	 * Connection 정보 반환
	 * @param strServerName 서버명
	 * @return ConnectionParam
	 */
	public ConnectionParam getApprovalConnection(String strServerName)
	{
		ConnectionParam connectionParam = null;
		
	    if (m_apprServers != null)
	    {
	    	for (int i = 0 ; i < m_apprServers.size() ; i++)
	    	{
	    		if (strServerName.compareTo(m_apprServers.get(i).getServerName()) == 0)
	    		{
	    			connectionParam = transferConnectionParam(m_apprServers.get(i));
	    		}
	    	}
	    }
		
		return connectionParam;
	}
	
	
	/**
	 * ConnectionParam LinkedList 정보 반환
	 * @param arrServerName 서버명 배열 
	 * @return ConnectionParam
	 */
	public LinkedList getApprovalConnections(String[] arrServerName)
	{
		LinkedList connectionParamList = null;
		
	    if (m_apprServers != null && arrServerName != null)
	    {
	    	connectionParamList = new LinkedList();
	    	for (int i = 0 ; i < arrServerName.length ; i++)
	    	{
		    	for (int j = 0 ; j < m_apprServers.size() ; j++)
		    	{
		    		if (arrServerName[i].compareTo(m_apprServers.get(j).getServerName()) == 0)
		    		{
		    			ConnectionParam connectionParam = transferConnectionParam(m_apprServers.get(j));
		    			connectionParamList.add(connectionParam);
		    		}
		    	}
	    	}
	    }
	    
		return connectionParamList;
	}
	
	/**
	 * ConnectionParam LinkedList 정보 반환
	 * @param serverNameList 서버명 리스트 
	 * @return ConnectionParam
	 */
	public LinkedList getApprovalConnections(LinkedList serverNameList)
	{
		LinkedList connectionParamList = null;
		
	    if (m_apprServers != null && serverNameList != null)
	    {
	    	connectionParamList = new LinkedList();
	    	for (int i = 0 ; i < serverNameList.size() ; i++)
	    	{
	    		String strServerName = (String)serverNameList.get(i);
		    	for (int j = 0 ; j < m_apprServers.size() ; j++)
		    	{	
		    		if (strServerName.compareTo(m_apprServers.get(j).getServerName()) == 0)
		    		{
		    			ConnectionParam connectionParam = transferConnectionParam(m_apprServers.get(j));
		    			connectionParamList.add(connectionParam);
		    		}
		    	}
	    	}
	    }
	    
		return connectionParamList;
	}
	
	/**
	 * System Server 정보를 ConnectionParam으로 바꾸는 함수
	 * @param systemServer 시스템 서버 정보 
	 * @return ConnectionParam
	 */
	public ConnectionParam transferConnectionParam(SystemServer systemServer)
	{
		ConnectionParam connectionParam = null;
		
		if (systemServer != null)
		{				
			connectionParam = new ConnectionParam();
			
			int nDBType = systemServer.getDBType();
			
			String strURL = "";
			
			if (nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{
				strURL = m_arrURLHeader[ConnectionParam.DB_TYPE_ORACLE] +
						 systemServer.getServerIP() + ":" +
						 systemServer.getDBPort() + ":" +
						 systemServer.getDBInfo();			
			}
			else
			{
				strURL = m_arrURLHeader[ConnectionParam.DB_TYPE_MSSQL] +
						 systemServer.getServerIP() + ":" +
						 systemServer.getDBPort() + "?" +
						 "database=" + systemServer.getDBInfo() +
						 "&language=korean&charset=EUC_KR";
			}
			
			connectionParam.setMethod(ConnectionParam.METHOD_CREATE);
			connectionParam.setDBType(nDBType);
			connectionParam.setURL(strURL);
			connectionParam.setClassName(m_arrClassName[nDBType]);
			connectionParam.setUser(systemServer.getDBLoginID());
			connectionParam.setPassword(systemServer.getDBLoginPassword());			
		}
		
		return connectionParam;
	}
	
	/**
	 * StoreServerParam 정보 반환
	 * @param strServerName 서버명
	 * @return StoreServerParam
	 */
	public StoreServerParam getStoreConnection(String strServerName)
	{
		StoreServerParam storeServerParam = null;
		
	    if (m_storeServers != null)
	    {
	    	for (int i = 0 ; i < m_storeServers.size() ; i++)
	    	{
	    		if (strServerName.compareTo(m_storeServers.get(i).getServerName()) == 0)
	    		{
	    			storeServerParam = transferStoreServerParam(m_storeServers.get(i));
	    		}
	    	}
	    }
		
		return storeServerParam;
	}
	
	/**
	 * StoreServerParam LinkedList 정보 반환
	 * @param arrServerName 서버명 리스트 
	 * @return ConnectionParam
	 */
	public LinkedList getStoreConnections(String[] arrServerName)
	{
		LinkedList storeServerParamList = null;
		
	    if (m_storeServers != null && arrServerName != null)
	    {
	    	storeServerParamList = new LinkedList();
	    	for (int i = 0 ; i < arrServerName.length ; i++)
	    	{
		    	for (int j = 0 ; j < m_storeServers.size() ; j++)
		    	{
		    		if (arrServerName[i].compareTo(m_storeServers.get(j).getServerName()) == 0)
		    		{
		    			StoreServerParam storeServerParam = transferStoreServerParam(m_storeServers.get(j));
		    			storeServerParamList.add(storeServerParam);
		    		}
		    	}
	    	}
	    }
	    
		return storeServerParamList;
	}
	
	/**
	 * StoreServerParam LinkedList 정보 반환
	 * @param serverNameList 서버명 배열 
	 * @return ConnectionParam
	 */
	public LinkedList getStoreConnections(LinkedList serverNameList)
	{
		LinkedList storeServerParamList = null;
		
	    if (m_storeServers != null && serverNameList != null)
	    {
	    	storeServerParamList = new LinkedList();
	    	for (int i = 0 ; i < serverNameList.size() ; i++)
	    	{
	    		String strServerName = (String)serverNameList.get(i);
		    	for (int j = 0 ; j < m_storeServers.size() ; j++)
		    	{
		    		if (strServerName.compareTo(m_storeServers.get(j).getServerName()) == 0)
		    		{
		    			StoreServerParam storeServerParam = transferStoreServerParam(m_storeServers.get(j));
		    			storeServerParamList.add(storeServerParam);
		    		}
		    	}
	    	}
	    }
	    
		return storeServerParamList;
	}
			
	/**
	 * 저장서버 정보 반환
	 * @return LinkedList
	 */
	public LinkedList getStoreConnections()
	{
		LinkedList storeConnectionList = null;
		
		if (m_storeServers != null)
		{
			if (m_storeServers.size() > 0)
			{
				storeConnectionList = new LinkedList();
				
				for (int i = 0 ; i < m_storeServers.size() ; i++)
				{
					RelatedServer storeServer = m_storeServers.get(i);
					
					StoreServerParam storeServerParam = transferStoreServerParam(storeServer);
									
					storeConnectionList.add(storeServerParam);
				}					
			}
		}
		
		return storeConnectionList;
	}
		
		
	/**
	 * Related Server 정보를 Store Server Param 정보로 반환하는 함수.
	 * @param relatedServer 서버정보
	 * @return ConnectionParam
	 */
	public StoreServerParam transferStoreServerParam(RelatedServer relatedServer)
	{
		StoreServerParam storeServerParam = null;
		
		if (relatedServer != null)
		{				
			storeServerParam = new StoreServerParam();
					
			storeServerParam.setStoreServerIP(relatedServer.getServerIP());
			storeServerParam.setStoreServerName(relatedServer.getServerName());
			storeServerParam.setStoreServerPort(relatedServer.getConnectionInfo());
			storeServerParam.setStoreServerVolume(relatedServer.getDescription());				
		}
		
		return storeServerParam;
	}
}
