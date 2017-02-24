package com.sds.acube.app.idir.org.orginfo;

import java.util.*;

/**
 * DeptServers.java
 * 2002-12-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DeptServers 
{
	private LinkedList m_lDeptServerList = null;
	
	public DeptServers()
	{
		m_lDeptServerList = new LinkedList();
	}
	
	/**
	 * DeparServers를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lDeptServerList;
	}
	
	/**
	 * List에 DeptServer를 더함.
	 * @param DeptServer DeptServer 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(DeptServer deptServer)
	{
		return m_lDeptServerList.add(deptServer);
	}
	
	/**
	 * List에서 특정 DeptServer정보를 제거.
	 * @param nIndex 서버 index
	 * @return Object
	 */
	public Object remove(int nIndex)
	{
		return m_lDeptServerList.remove(nIndex);
	}
	
	/**
	 * 서버 리스트의 size
	 * @return int 서버의 수 
	 */ 
	public int size()
	{
		return m_lDeptServerList.size();
	}
	
	/**
	 * 서버 정보 
	 * @param nIndex 서버 index
	 * @return DeptServer 서버 정보 
	 */
	public DeptServer get(int nIndex)
	{
		return (DeptServer)m_lDeptServerList.get(nIndex);
	}
	
	/**
	 * Server Type 반환.
	 * @param nIndex 서버 index
	 * @return int
	 */
	public int getServerType(int nIndex) 
	{
		DeptServer deptServer = (DeptServer)m_lDeptServerList.get(nIndex);
		return deptServer.getServerType();
	}

	/**
	 * Server 명 반환.
	 * @param nIndex 서버 index
	 * @return String
	 */
	public String getServerName(int nIndex) 
	{
		DeptServer deptServer = (DeptServer)m_lDeptServerList.get(nIndex);
		return deptServer.getServerName();
	}

	/**
	 * System 명 반환.
	 * @param nIndex 서버 index
	 * @return String
	 */
	public String getSystemName(int nIndex) 
	{
		DeptServer deptServer = (DeptServer)m_lDeptServerList.get(nIndex);
		return deptServer.getSystemName();
	}
}
