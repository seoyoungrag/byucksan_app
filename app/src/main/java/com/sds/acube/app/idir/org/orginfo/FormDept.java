package com.sds.acube.app.idir.org.orginfo;

/**
 * FormDept.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormDept 
{
	private String 		m_strOrgName = "";
	private String 		m_strOrgOtherName = "";
	private String 		m_strOrgID = "";
	private String 		m_strOrgParentID = "";
	private boolean 	m_bFormBoxInfo = false;
	private int 		m_nDepth = 0;
	private int			m_nIsSelected = 0;

	/**
	 * 기본으로 선택되는 함인지 여부 설정.
	 * @param nIsSelected 기본선택여부
	 */
	public void setIsSelected(int nIsSelected)
	{
		m_nIsSelected = nIsSelected;	
	}
	
	/**
	 * Sets the m_nDepth
	 * @param nDepth The m_nDepth to set
	 */
	public void setDepth(int nDepth)
	{
		m_nDepth = nDepth;
	}	
	
	/**
	 * Sets the m_strOrgParentID
	 * @param strOrgParentID The m_strOrgParentID to set
	 */
	public void setOrgParentID(String strOrgParentID)
	{
		m_strOrgParentID = strOrgParentID;
	}
	
	/**
	 * Sets the m_bFormBoxInfo 
	 * @param bFormBoxInfo The m_bFormBoxInfo to set
	 */
	public void setFormBoxInfo(boolean bFormBoxInfo)
	{
		m_bFormBoxInfo = bFormBoxInfo;
	}
	
	/**
	 * Sets the m_strOrgDeptID.
	 * @param strOrgID The m_strOrgDeptID to set
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * Sets the m_strOrgName.
	 * @param strOrgName The m_strOrgName to set
	 */
	public void setOrgName(String strOrgName) 
	{
		m_strOrgName = strOrgName;
	}
	
	/**
	 * Sets the m_strOrgOtherName
	 * @param strOrgOtherName The m_strOrgOtherName to set
	 */
	public void setOrgOtherName(String strOrgOtherName)
	{
		m_strOrgOtherName = strOrgOtherName;
	}
	
	/**
	 * Returns the m_strOrgOtherName
	 * @return String
	 */
	public String getOrgOtherName() 
	{
		return m_strOrgOtherName;
	}
	
	/**
	 * Returns the m_nDepth
	 * @return int
	 */
	public int getDepth()
	{
		return m_nDepth;
	}
	
	/**
	 * Returns the m_strOrgDeptCode.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}

	/**
	 * Returns the m_strOrgName.
	 * @return String
	 */
	public String getOrgName() 
	{
		return m_strOrgName;
	}
	
	/**
	 * Returns the m_strOrgParentID
	 * @return String
	 */
	public String getOrgParentID()
	{
		return m_strOrgParentID;
	}
	
	/**
	 * Returns the m_bFormBoxInfo
	 * @return boolean
	 */
	public boolean getFormBoxInfo()
	{
		return m_bFormBoxInfo;
	}
	
	/**
	 * 기본으로 선택되는 함인지 여부 반환.
	 * @return int
	 */
	public int getIsSelected()
	{
		return m_nIsSelected;	
	}
}
