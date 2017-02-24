package com.sds.acube.app.idir.org.hierarchy;

import java.io.Serializable;

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

public class PortalGroup implements Serializable
{
	private String m_strPortalID = "";
	private String m_strParentPortalID = "";
	private String m_strPortalType = "";
	private String m_strOrgID = "";
	private String m_strParentOrgID = "";
	private int	   m_nOrgType = 0;
	private String m_strDescription = "";
	
	/**
	 * 조직 종류 설정.
	 * @param nOrgType 조직 종류
	 */
	public void setOrgType(int nOrgType) 
	{	
		m_nOrgType = nOrgType;
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
	 * 조직 ID 설정.
	 * @param strOrgID 조직 ID
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * 상위 조직 ID 설정.
	 * @param strParentOrgID 상위 조직 ID
	 */
	public void setParentOrgID(String strParentOrgID) 
	{
		m_strParentOrgID = strParentOrgID;
	}

	/**
	 * 상위 포탈 ID 설정.
	 * @param strParentPortalID 상위 포탈 ID 
	 */
	public void setParentPortalID(String strParentPortalID) 
	{
		m_strParentPortalID = strParentPortalID;
	}

	/**
	 * 포탈 ID 설정.
	 * @param strPortalID 포탈 ID 
	 */
	public void setPortalID(String strPortalID) 
	{
		m_strPortalID = strPortalID;
	}

	/**
	 * 포탈 Type 설정.
	 * @param strPortalType 포탈 Type
	 */
	public void setPortalType(String strPortalType) 
	{
		m_strPortalType = strPortalType;
	}	
	
	/**
	 * 조직 Type 반환.
	 * @return int
	 */
	public int getOrgType() 
	{
		return m_nOrgType;
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
	 * 조직 ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}

	/**
	 * 상위 조직 ID 반환.
	 * @return String
	 */
	public String getParentOrgID() 
	{
		return m_strParentOrgID;
	}

	/**
	 * 상위 포탈 ID 반환.
	 * @return String
	 */
	public String getParentPortalID() 
	{
		return m_strParentPortalID;
	}

	/**
	 * 포탈 ID 반환.
	 * @return String
	 */
	public String getPortalID() 
	{
		return m_strPortalID;
	}

	/**
	 * 포탈 Type반환.
	 * @return String
	 */
	public String getPortalType() 
	{
		return m_strPortalType;
	}
}
