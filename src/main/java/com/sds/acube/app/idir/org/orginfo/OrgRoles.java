package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgRoles.java
 * 2011-04-29
 * 
 * 
 * 
 * @author geena
 * @version 1.0.0.0
 *
 * Copyright 2011 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class OrgRoles 
{
	private LinkedList m_lOrgRoleList = null;
	
	public OrgRoles()
	{
		m_lOrgRoleList = new LinkedList();
	}
	
	/**
	 * OrgRoles를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lOrgRoleList;
	}
	
	/**
	 * List에 OrgRole를 더함.
	 * @param OrgRole OrgRole 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(OrgRole orgRole)
	{
		return m_lOrgRoleList.add(orgRole);
	}
	
	/**
	 * List에 OrgRole를 앞에 추가.
	 * @param OrgRole OrgRole 정보
	 * @return void
	 */
	public void addFirst(OrgRole orgRole)
	{
		m_lOrgRoleList.addFirst(orgRole);
	}
	
	/**
	 * List에 여러 OrgRole 정보 append
	 * @param Departmens
	 * @return boolean
	 */
	public boolean addAll(OrgRoles orgRoles)
	{
		return m_lOrgRoleList.addAll(orgRoles.toLinkedList());
	}
	
	/**
	 * List에 여러 OrgRole 정보 insert
	 * @param Departmens
	 * @return boolean
	 */
	public boolean addAll(int index, OrgRoles orgRoles)
	{
		return m_lOrgRoleList.addAll(index, orgRoles.toLinkedList());
	}
	
	/**
	 * 부서 리스트의 size
	 * @return int 부서의 수 
	 */ 
	public int size()
	{
		return m_lOrgRoleList.size();
	}
	
	/**
	 * 한 부서의 정보 
	 * @param nIndex 부서 index
	 * @return OrgRole 부서 정보 
	 */
	public OrgRole get(int nIndex)
	{
		return (OrgRole)m_lOrgRoleList.get(nIndex);
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public String getRoleID(int nIndex)
	{
		OrgRole orgRole = (OrgRole)m_lOrgRoleList.get(nIndex);
		return orgRole.getRoleID();
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public String getRoleName(int nIndex)
	{
		OrgRole orgRole = (OrgRole)m_lOrgRoleList.get(nIndex);
		return orgRole.getRoleName();
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public String getRoleOtherName(int nIndex)
	{
		OrgRole orgRole = (OrgRole)m_lOrgRoleList.get(nIndex);
		return orgRole.getRoleOtherName();
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public int getRoleOrder(int nIndex)
	{
		OrgRole orgRole = (OrgRole)m_lOrgRoleList.get(nIndex);
		return orgRole.getRoleOrder();
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public String getDescription(int nIndex)
	{
		OrgRole orgRole = (OrgRole)m_lOrgRoleList.get(nIndex);
		return orgRole.getDescription();
	}
		
}
