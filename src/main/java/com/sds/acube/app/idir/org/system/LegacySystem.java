package com.sds.acube.app.idir.org.system;

/**
 * LegacySystem.java 2003-02-27
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LegacySystem 
{
	private String 		m_strSystemID = "";
	private int 			m_nApprovalEvent = 0;
	/**
	 */
	private Architecture 	m_architecture = null;
	/**
	 */
	private LegacyKeys 	m_legacyKeys = null;
	/**
	 */
	private BusinessInfos	m_businessInfos = null;
	
	/**
	 * Architecture 정보 설정.
	 * @param architecture Architecture 정보 
	 */
	public void setArchitecture(Architecture architecture) 
	{
		m_architecture = architecture;
	}

	/**
	 * Business Info List 정보 설정.
	 * @param businessInfos Business Info List 정보
	 */
	public void setBusinessInfos(BusinessInfos businessInfos) 
	{
		m_businessInfos = businessInfos;
	}

	/**
	 * Legacy Key List 정보 설정.
	 * @param legacyKeys Legacy Key List 정보 
	 */
	public void setLegacyKeys(LegacyKeys legacyKeys) 
	{
		m_legacyKeys = legacyKeys;
	}

	/**
	 * Approval Event 설정.
	 * @param nApprovalEvent Approval Event
	 */
	public void setApprovalEvent(int nApprovalEvent) 
	{
		m_nApprovalEvent = nApprovalEvent;
	}

	/**
	 * System ID 설정.
	 * @param strSystemID System ID
	 */
	public void setSystemID(String strSystemID) 
	{
		m_strSystemID = strSystemID;
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
	 * BusinessInfo List 정보 반환.
	 * @return BusinessInfos
	 */
	public BusinessInfos getBusinessInfos() 
	{
		return m_businessInfos;
	}

	/**
	 * LegacyKey List 정보 반환.
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
	 * System ID 반환.
	 * @return String
	 */
	public String getSystemID() 
	{
		return m_strSystemID;
	}
	
	/**
	 * 특정 Business ID를 가지는 BusinessInfo Class 반환
	 * @param strBusinessID Business ID
	 * @return BusinessInfo
	 */
	public BusinessInfo getBusinessInfoByID(String strBusinessID)
	{
		BusinessInfo businessInfo = null;
		
		if (m_businessInfos.size() > 0)
		{
			for (int i = 0; i < m_businessInfos.size() ; i++)
			{
				BusinessInfo elementBusinessInfo = (BusinessInfo)m_businessInfos.get(i);
				
				String strObjectBusinessID = elementBusinessInfo.getBusinessID();
				
				if (strObjectBusinessID.compareTo(strBusinessID) == 0)
				{
					businessInfo = elementBusinessInfo;
				}
			}
		}
		
		return businessInfo;
	}
}
