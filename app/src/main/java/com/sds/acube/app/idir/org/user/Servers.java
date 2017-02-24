package com.sds.acube.app.idir.org.user;

/**
 * Servers.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Servers 
{
	private LinkedList m_lServerList = null;
	
	public Servers()
	{
		m_lServerList = new LinkedList();	
	}
	
	/**
	 * List에 서버정보를 더함.
	 * @param Server 서버 정보  
	 * @return boolean 성공 여부 
	 */
	public boolean add(Server server)
	{
		return m_lServerList.add(server);
	}
	
	/**
	 * 서버 리스트의 size
	 * @return int 서버정보의 수
	 */ 
	public int size()
	{
		return m_lServerList.size();
	}
	
	/**
	 * 서버 정보 
	 * @param  서버 index
	 * @return Server
	 */
	public Server get(int index)
	{
		return (Server)m_lServerList.get(index);
	}
	
	/**
	 * Returns 서버명.
	 * @param  서버 index
	 * @return String
	 */
	public String getServerName(int index)
	{
		Server server = (Server)m_lServerList.get(index);
		return server.getServerName();
	}
	
	/**
	 * Returns 서버 Type.
	 * @param  서버 index
	 * @return String
	 */
	public String getServerType(int index)
	{
		Server server = (Server)m_lServerList.get(index);
		return server.getServerType();
	}
	
	/**
	 * 주어진 Type의 서버명 
	 * @param strServerType 서버 Type
	 * @return String
	 */
	public String getServerName(String strServerType)
	{
		String strServerName = "";
		
		for (int i = 0 ; i < size() ; i++)
		{
			String serverType = getServerType(i);
			if (serverType.compareTo(strServerType) == 0)
				strServerName = getServerName(i);	
		}
		
		return strServerName;				
	}		
}
