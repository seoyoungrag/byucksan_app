package com.sds.acube.app.idir.org.option;

import java.util.*;

/**
 * GlobalInformations.java
 * 2003-07-24
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class GlobalInformations 
{
	private LinkedList m_lGlobalInfoList = null;
	
	public GlobalInformations()
	{
		m_lGlobalInfoList = new LinkedList();	
	}
	
	/**
	 * Option Code를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lGlobalInfoList;
	}
	
	/**
	 * List에 Global Infomation Object를 더함.
	 * @param globalInformation GlobalInformation Object 
	 * @return boolean
	 */
	public boolean add(GlobalInformation globalInformation)
	{
		return m_lGlobalInfoList.add(globalInformation);
	}
	
	/**
	 * Global Information 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lGlobalInfoList.size();
	}
	
	/**
	 * Global Information 정보 
	 * @param  nIndex Global Information index
	 * @return GlobalInformation
	 */
	public GlobalInformation get(int nIndex)
	{
		return (GlobalInformation)m_lGlobalInfoList.get(nIndex);
	}
	
	/**
	 * Description 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		GlobalInformation globalInformation = (GlobalInformation)m_lGlobalInfoList.get(nIndex);
		return globalInformation.getDescription();
	}

	/**
	 * Information ID 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getInfoID(int nIndex) 
	{
		GlobalInformation globalInformation = (GlobalInformation)m_lGlobalInfoList.get(nIndex);
		return globalInformation.getInfoID();
	}

	/**
	 * Information Name 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getInfoName(int nIndex) 
	{
		GlobalInformation globalInformation = (GlobalInformation)m_lGlobalInfoList.get(nIndex);
		return globalInformation.getInfoName();
	}

	/**
	 * Information Value 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getInfoValue(int nIndex)
	{
		GlobalInformation globalInformation = (GlobalInformation)m_lGlobalInfoList.get(nIndex);
		return globalInformation.getInfoValue();
	}
}
