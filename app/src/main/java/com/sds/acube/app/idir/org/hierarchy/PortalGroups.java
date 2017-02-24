package com.sds.acube.app.idir.org.hierarchy;

import java.io.Serializable;
import java.util.*;

/**
 * PortalGroupTableMap.java
 * 2005-09-26
 * 
 * 포탈 그룹 정보 Value Object
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PortalGroups implements Serializable
{
	private LinkedList m_lPortalGroupList = null;
	
	public PortalGroups() 
	{
		m_lPortalGroupList = new LinkedList();	
	}
	
	/**
	 * PortalGroups를 LinkedList로 얻음.
	 * @return LinkedList
	 */
	public LinkedList toLinkedList() 
	{
		return m_lPortalGroupList;
	}
	
	/**
	 * List에 PortalGroup을 더함.
	 * @param PortalGroup Portal Group 정보 
	 * @return boolean
	 */	
	public boolean add(PortalGroup portalGroup) 
	{
		return m_lPortalGroupList.add(portalGroup);
	}
	
	/**
	 * PortalGroup List의 사이즈.
	 * @return int
	 */	
	public int size() 
	{
		return m_lPortalGroupList.size();
	}	
	
	/**
	 * Portal Group 정보 반환.
	 * @param nIndex Portal Group Index
	 * @return PortalGroup
	 */
	public PortalGroup get(int nIndex)
	{
		return (PortalGroup) m_lPortalGroupList.get(nIndex);
	}
	
	/**
	 * 조직 Type 반환.
	 * @param nIndex Portal Group Index
	 * @return int
	 */
	public int getOrgType(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getOrgType();
	}

	/**
	 * Description 반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{	
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getDescription();
	}

	/**
	 * 조직 ID 반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getOrgID();
	}

	/**
	 * 상위 조직 ID 반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getParentOrgID(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getParentOrgID();
	}

	/**
	 * 상위 포탈 ID 반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getParentPortalID(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getParentPortalID();
	}

	/**
	 * 포탈 ID 반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getPortalID(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getPortalID();
	}

	/**
	 * 포탈 Type반환.
	 * @param nIndex Portal Group Index
	 * @return String
	 */
	public String getPortalType(int nIndex) 
	{
		PortalGroup portalGroup = (PortalGroup) m_lPortalGroupList.get(nIndex);
		return portalGroup.getPortalType();
	}
}
