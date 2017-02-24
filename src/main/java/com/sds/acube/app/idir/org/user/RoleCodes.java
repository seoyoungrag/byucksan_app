package com.sds.acube.app.idir.org.user;

/**
 * RoleCodes.java
 * 2002-10-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class RoleCodes 
{
	private LinkedList m_lRoleCodeList = null;
	
	public RoleCodes()
	{
		m_lRoleCodeList = new LinkedList();	
	}
	
	/**
	 * List에 Role Code 정보를 더함.
	 * @param Role Code 정보
	 * @return boolean 성공 여부 
	 */
	public boolean add(RoleCode roleCode)
	{
		return m_lRoleCodeList.add(roleCode);
	}
	
	/**
	 * Role Code 리스트의 size
	 * @return int Role Code의 수
	 */ 
	public int size()
	{
		return m_lRoleCodeList.size();
	}
	
	/**
	 * Role Code 정보 
	 * @param  nIndex Role index 
	 * @return RoleCode
	 */
	public RoleCode get(int nIndex)
	{
		return (RoleCode)m_lRoleCodeList.get(nIndex);
	}
	
	/**
	 * Set LinkedList
	 * @param lRoleCodeList
	 */
	public void setLinkedList(LinkedList lRoleCodeList)
	{
		m_lRoleCodeList = lRoleCodeList;
	}
	
	/**
	 * Returns Role Code.
	 * @param  nIndex Role index 
	 * @return String
	 */
	public String getRoleCode(int nIndex) 
	{
		RoleCode roleCode = (RoleCode)m_lRoleCodeList.get(nIndex);
		return roleCode.getRoleCode();
	}
}
