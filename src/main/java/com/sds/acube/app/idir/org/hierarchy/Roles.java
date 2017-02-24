package com.sds.acube.app.idir.org.hierarchy;

import java.util.LinkedList;

/**
 * Roles.java
 * 2009-01-13
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Roles 
{
	private LinkedList m_lRoleList = null;
	
	public Roles()
	{
		m_lRoleList = new LinkedList();	
	}
	
	/**
	 * Titles LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lRoleList;
	}
	
	/**
	 * List에 Role을 더함.
	 * @param role Role 정보 
	 * @return boolean
	 */
	public boolean add(Role role)
	{
		return m_lRoleList.add(role);
	}
	
	/**
	 * Role 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lRoleList.size();
	}
	
	/**
	 * Role 정보  
	 * @param  nIndex Role index
	 * @return Role   
	 */
	public Role get(int nIndex)
	{
		return (Role)m_lRoleList.get(nIndex);
	}
}
