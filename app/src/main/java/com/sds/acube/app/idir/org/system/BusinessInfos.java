package com.sds.acube.app.idir.org.system;

/**
 * BusinessInfos.java
 * 2003-02-27
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class BusinessInfos 
{
	private LinkedList m_lBusinessInfoList = null;
	
	public BusinessInfos()
	{
		m_lBusinessInfoList = new LinkedList();	
	}
		
	/**
	 * List에 BusinessInfo를 더함.
	 * @param business Business 정보 
	 * @return boolean
	 */
	public boolean add(BusinessInfo businessInfo)
	{
		return m_lBusinessInfoList.add(businessInfo);
	}
	
	/**
	 * BusinessInfo 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lBusinessInfoList.size();
	}
	
	/**
	 * BusinessInfo 정보 
	 * @param  nIndex BusinessInfo List Index
	 * @return BusinessInfo
	 */
	public BusinessInfo get(int nIndex)
	{
		return (BusinessInfo)m_lBusinessInfoList.get(nIndex);
	}
	
	/**
	 * Architecture 정보 반환.
	 * @param  nIndex BusinessInfo List Index
	 * @return Architecture
	 */
	public Architecture getArchitecture(int nIndex) 
	{
		BusinessInfo businessInfo = (BusinessInfo)m_lBusinessInfoList.get(nIndex);
		return businessInfo.getArchitecture();
	}

	/**
	 * Legacy Key List 정보 반환.
	 * @param  nIndex BusinessInfo List Index
	 * @return LegacyKeys
	 */
	public LegacyKeys getLegacyKeys(int nIndex) 
	{
		BusinessInfo businessInfo = (BusinessInfo)m_lBusinessInfoList.get(nIndex);
		return businessInfo.getLegacyKeys();
	}

	/**
	 * Approval Event 반환.
	 * @param  nIndex BusinessInfo List Index
	 * @return int
	 */
	public int getApprovalEvent(int nIndex) 
	{
		BusinessInfo businessInfo = (BusinessInfo)m_lBusinessInfoList.get(nIndex);
		return businessInfo.getApprovalEvent();
	}

	/**
	 * Business ID 반환.
	 * @param  nIndex BusinessInfo List Index
	 * @return String
	 */
	public String getBusinessID(int nIndex) 
	{
		BusinessInfo businessInfo = (BusinessInfo)m_lBusinessInfoList.get(nIndex);
		return businessInfo.getBusinessID();
	}
}
