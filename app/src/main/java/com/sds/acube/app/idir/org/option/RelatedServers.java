package com.sds.acube.app.idir.org.option;

/**
 * RelatedServers.java
 * 2002-11-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class RelatedServers 
{
	private LinkedList m_lServerList = null;
	
	public RelatedServers()
	{
		m_lServerList = new LinkedList();	
	}
	
	/**
	 * Server 정보를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lServerList;
	}
	
	/**
	 * List에 Server를 더함.
	 * @param relatedServer Related Server 정보 
	 * @return boolean
	 */
	public boolean add(RelatedServer relatedServer)
	{
		return m_lServerList.add(relatedServer);
	}
	
	/**
	 * Member 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lServerList.size();
	}
	
	/**
	 * 코드 정보
	 * @param  nIndex Related Server index
	 * @return RelatedServer
	 */
	public RelatedServer get(int nIndex)
	{
		return (RelatedServer)m_lServerList.get(nIndex);
	}
	
	/**
	 * Server Type 반환.
	 * @param  nIndex Related Server index
	 * @return int
	 */
	public int getServerType(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getServerType();
	}

	/**
	 * Connection Information 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getConnectionInfo(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getConnectionInfo();
	}

	/**
	 * Description 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getDescription();
	}

	/**
	 * Server ID 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getServerID(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getServerID();
	}

	/**
	 * Server IP 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getServerIP(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getServerIP();
	}

	/**
	 * Server Name 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getServerName(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getServerName();
	}

	/**
	 * System Name 반환.
	 * @param  nIndex Related Server index
	 * @return String
	 */
	public String getSystemName(int nIndex) 
	{
		RelatedServer relatedServer = (RelatedServer)m_lServerList.get(nIndex);
		return relatedServer.getSystemName();
	}
}
