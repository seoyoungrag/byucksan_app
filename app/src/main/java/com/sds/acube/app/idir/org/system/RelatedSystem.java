package com.sds.acube.app.idir.org.system;

/**
 * RelatedSystem.java
 * 2002-11-26
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class RelatedSystem 
{
	private String m_strSystemID = "";
	private String m_strSystemName = "";
	private String m_strCompID = "";
	private int    m_nLinkageType = 0;			// 1: 조직/2:기간시스템/4:일반결재연동
	private String m_strDescription = "";
	
	// Linkage Type
	public static final int ORG_SYSTEM = 1;				// 조직 연동
	public static final int LEGACY_SYSTEM = 2;			// 기간 시스템
	public static final int LINKAGE_SYSTEM = 4;   		// 일반 결재 연동
	public static final int MAIL_SYSTEM = 8;			// 메일 시스템 연동
	public static final int NOTIFICATION_SYSTEM = 16;	// 알림 시스템 연동
	
	/**
	 * 연동정보 TYPE 설정.
	 * @param nLinkageType 연동정보 TYPE
	 */
	public void setLinkageType(int nLinkageType) 
	{
		m_nLinkageType = nLinkageType;
	}

	/**
	 * 회사 ID 설정.
	 * @param strCompID 회사 ID
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
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
	 * 시스템 정보명.
	 * @param strSystemName 시스템 정보명 
	 */
	public void setSystemName(String strSystemName) 
	{
		m_strSystemName = strSystemName;
	}

	/**
	 * 연동정보 TYPE 반환.
	 * @return int
	 */
	public int getLinkageType() 
	{
		return m_nLinkageType;
	}

	/**
	 * 회사 ID 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
	}

	/**
	 * Description 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
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
	 * System 정보명 반환.
	 * @return String
	 */
	public String getSystemName() 
	{
		return m_strSystemName;
	}
}
