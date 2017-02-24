package com.sds.acube.app.idir.org.orginfo;

/**
 * DeptServerInfo.java 2002-12-11
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DeptServerInfo 
{
	/**
	 */
	DeptServer 	m_homeDBDeptServer = null;
	/**
	 */
	DeptServers m_remoteDBDeptServers = null;
	/**
	 */
	DeptServer  m_homeStoreDeptServer = null;
	/**
	 */
	DeptServers m_storeDeptServers = null;
	
	/**
	 * Home Server 정보 설정.
	 * @param homeDBDeptServer Home Server 정보 
	 */
	public void setHomeDBDeptServer(DeptServer homeDBDeptServer) 
	{
		m_homeDBDeptServer = homeDBDeptServer;
	}

	/**
	 * Remote Server 정보 설정.
	 * @param remoteDBDeptServers Remote Server 정보
	 */
	public void setRemoteDBDeptServers(DeptServers remoteDBDeptServers) 
	{
		m_remoteDBDeptServers = remoteDBDeptServers;
	}

	/**
	 * 저장 Server 정보 설정.
	 * @param storeDeptServers 저장 Server 정보
	 */
	public void setStoreDeptServers(DeptServers storeDeptServers) 
	{
		m_storeDeptServers = storeDeptServers;
	}
	
	/**
	 * 저장 Server 중 홈 서버 정보 설정.
	 * @param homeStoreDeptServer Home 저장 Server 정보 
	 */
	public void setHomeStoreDeptServer(DeptServer homeStoreDeptServer)
	{
		m_homeStoreDeptServer = homeStoreDeptServer;	
	}
	
	/**
	 * Home Server 정보 반환.
	 * @return DeptServer
	 */
	public DeptServer getHomeDBDeptServer() 
	{
		return m_homeDBDeptServer;
	}

	/**
	 * Remote Server 정보 반환.
	 * @return DeptServers
	 */
	public DeptServers getRemoteDBDeptServers() 
	{
		return m_remoteDBDeptServers;
	}

	/**
	 * Store Server 정보 반환.
	 * @return DeptServers
	 */
	public DeptServers getStoreDeptServers() 
	{
		return m_storeDeptServers;
	}
	
	/**
	 * 저장 Server 중 홈 서버정보 반환.
	 * @return DeptServer
	 */
	public DeptServer getHomeStoreDeptServer()
	{
		return m_homeStoreDeptServer;
	}
}
