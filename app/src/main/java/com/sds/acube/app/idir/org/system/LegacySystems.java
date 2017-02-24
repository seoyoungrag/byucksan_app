package com.sds.acube.app.idir.org.system;

/**
 * LegacySystems.java
 * 2003-02-28
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class LegacySystems 
{
	private LinkedList m_lLegacySystemList = null;
	
	public LegacySystems()
	{
		m_lLegacySystemList = new LinkedList();	
	}
		
	/**
	 * List에 Legacy System 정보를 더함.
	 * @param legacySystem LegacySystem 정보 
	 * @return boolean
	 */
	public boolean add(LegacySystem legacySystem)
	{
		return m_lLegacySystemList.add(legacySystem);
	}
	
	/**
	 * Legacy System 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lLegacySystemList.size();
	}
	
	/**
	 * List Index로 Legacy System 정보 반환 
	 * @param  nIndex Legacy System List Index
	 * @return LegacySystem
	 */
	public LegacySystem get(int nIndex)
	{
		return (LegacySystem)m_lLegacySystemList.get(nIndex);
	}
	
	/**
	 * Architecture 정보 반환.
	 * @param  nIndex Legacy System List Index
	 * @return Architecture
	 */
	public Architecture getArchitecture(int nIndex) 
	{
		LegacySystem legacySystem = (LegacySystem)m_lLegacySystemList.get(nIndex);
		return legacySystem.getArchitecture();
	}

	/**
	 * BusinessInfo List 정보 반환.
	 * @param  nIndex Legacy System List Index
	 * @return BusinessInfos
	 */
	public BusinessInfos getBusinessInfos(int nIndex) 
	{
		LegacySystem legacySystem = (LegacySystem)m_lLegacySystemList.get(nIndex);
		return legacySystem.getBusinessInfos();
	}

	/**
	 * LegacyKey List 정보 반환.
	 * @param  nIndex Legacy System List Index
	 * @return LegacyKeys
	 */
	public LegacyKeys getLegacyKeys(int nIndex) 
	{
		LegacySystem legacySystem = (LegacySystem)m_lLegacySystemList.get(nIndex);
		return legacySystem.getLegacyKeys();
	}

	/**
	 * Approval Event 반환.
	 * @param  nIndex Legacy System List Index
	 * @return int
	 */
	public int getApprovalEvent(int nIndex) 
	{
		LegacySystem legacySystem = (LegacySystem)m_lLegacySystemList.get(nIndex);
		return legacySystem.getApprovalEvent();
	}

	/**
	 * System ID 반환.
	 * @param  nIndex Legacy System List Index
	 * @return String
	 */
	public String getSystemID(int nIndex) 
	{
		LegacySystem legacySystem = (LegacySystem)m_lLegacySystemList.get(nIndex);
		return legacySystem.getSystemID();
	}
	
}
