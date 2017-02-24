package com.sds.acube.app.idir.org.system;

import java.util.*;

/**
 * SystemServers.java
 * 2003-12-10
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemServers 
{
	private LinkedList m_lSystemServerList = null;
	
	public SystemServers()
	{
		m_lSystemServerList = new LinkedList();	
	}
		
	/**
	 * List에 SystemServer를 더함.
	 * @param systemServer SystemServer 정보 
	 * @return boolean
	 */
	public boolean add(SystemServer systemServer)
	{
		return m_lSystemServerList.add(systemServer);
	}
	
	/**
	 * SystemServer 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lSystemServerList.size();
	}
	
	/**
	 * SystemServer 정보 
	 * @param  nIndex SystemServer List Index
	 * @return SystemServer
	 */
	public SystemServer get(int nIndex)
	{
		return (SystemServer)m_lSystemServerList.get(nIndex);
	}
	
	/**
	 * Home Server 여부 반환.
	 * @param  nIndex SystemServer List Index
	 * @return boolean
	 */
	public boolean IsHomeServer(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.IsHomeServer();
	}

	/**
	 * DB 정보 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getDBInfo(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getDBInfo();
	}

	/**
	 * DB LoginID 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getDBLoginID(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getDBLoginID();
	}

	/**
	 * DB LoginPassword 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getDBLoginPassword(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getDBLoginPassword();
	}

	/**
	 * DB Port 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getDBPort(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getDBPort();
	}

	/**
	 * DB Type 반환.
	 * @param  nIndex SystemServer List Index
	 * @return int
	 */
	public int getDBType(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getDBType();
	}

	/**
	 * Server IP 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getServerIP(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getServerIP();
	}

	/**
	 * Server Name 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getServerName(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getServerName();
	}

	/**
	 * System ID 반환.
	 * @param  nIndex SystemServer List Index
	 * @return String
	 */
	public String getSystemID(int nIndex) 
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getSystemID();
	}
	
	/**
	 * 결재 분산 서버 Type 반환.
	 * @param  nIndex SystemServer List Index
	 * @return int 
	 */
	public int getServerType(int nIndex)
	{
		SystemServer systemServer = (SystemServer)m_lSystemServerList.get(nIndex);
		return systemServer.getServerType();
	}
}
