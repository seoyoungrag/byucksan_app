package com.sds.acube.app.idir.org.system;

/**
 * RelatedSystems.java
 * 2002-11-28
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class RelatedSystems 
{
	private LinkedList m_lSystemList = null;
	
	public RelatedSystems()
	{
		m_lSystemList = new LinkedList();	
	}
		
	/**
	 * List에 System을 더함.
	 * @param system System 정보 
	 * @return boolean
	 */
	public boolean add(RelatedSystem system)
	{
		return m_lSystemList.add(system);
	}
	
	/**
	 * System 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lSystemList.size();
	}
	
	/**
	 * System 정보 
	 * @param  nIndex System List Index
	 * @return Business
	 */
	public RelatedSystem get(int nIndex)
	{
		return (RelatedSystem)m_lSystemList.get(nIndex);
	}
	
	/**
	 * 연동정보 TYPE 반환.
	 * @param  nIndex System List Index
	 * @return int
	 */
	public int getLinkageType(int nIndex) 
	{
		RelatedSystem system = (RelatedSystem)m_lSystemList.get(nIndex);
		return system.getLinkageType();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex System List Index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		RelatedSystem system = (RelatedSystem)m_lSystemList.get(nIndex);
		return system.getCompID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex System List Index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		RelatedSystem system = (RelatedSystem)m_lSystemList.get(nIndex);
		return system.getDescription();
	}

	/**
	 * System ID 반환.
	 * @param  nIndex System List Index
	 * @return String
	 */
	public String getSystemID(int nIndex) 
	{
		RelatedSystem system = (RelatedSystem)m_lSystemList.get(nIndex);
		return system.getSystemID();
	}

	/**
	 * System 정보명 반환.
	 * @param  nIndex System List Index
	 * @return String
	 */
	public String getSystemName(int nIndex) 
	{
		RelatedSystem system = (RelatedSystem)m_lSystemList.get(nIndex);
		return system.getSystemName();
	}
	
}
