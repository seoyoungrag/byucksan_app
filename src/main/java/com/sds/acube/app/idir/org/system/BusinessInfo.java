package com.sds.acube.app.idir.org.system;

/**
 * BusinessInfo.java 2003-02-27
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BusinessInfo 
{
	private String			m_strBusinessID = "";
	private int 			m_nApprovalEvent = 0;
	/**
	 */
	private Architecture	m_architecture = null;
	/**
	 */
	private LegacyKeys		m_legacyKeys = null;
	
	/**
	 * Archiecture 정보 설정.
	 * @param architecture Archiecture 정보
	 */
	public void setArchitecture(Architecture architecture) 
	{
		m_architecture = architecture;
	}

	/**
	 * Legacy Key list 정보 설정.
	 * @param legacyKeys Legacy Key 정보 
	 */
	public void setLegacyKeys(LegacyKeys legacyKeys) 
	{
		m_legacyKeys = legacyKeys;
	}

	/**
	 * Approval Evnet 설정.
	 * @param nApprovalEvent Approval Event
	 */
	public void setApprovalEvent(int nApprovalEvent) 
	{
		m_nApprovalEvent = nApprovalEvent;
	}

	/**
	 * Business ID 설정.
	 * @param strBusinessID Business ID
	 */
	public void setBusinessID(String strBusinessID) 
	{
		m_strBusinessID = strBusinessID;
	}
	
	/**
	 * Architecture 정보 반환.
	 * @return Architecture
	 */
	public Architecture getArchitecture() 
	{
		return m_architecture;
	}

	/**
	 * Legacy Key List 정보 반환.
	 * @return LegacyKeys
	 */
	public LegacyKeys getLegacyKeys() 
	{
		return m_legacyKeys;
	}

	/**
	 * Approval Event 반환.
	 * @return int
	 */
	public int getApprovalEvent() 
	{
		return m_nApprovalEvent;
	}

	/**
	 * Business ID 반환.
	 * @return String
	 */
	public String getBusinessID() 
	{
		return m_strBusinessID;
	}
}
