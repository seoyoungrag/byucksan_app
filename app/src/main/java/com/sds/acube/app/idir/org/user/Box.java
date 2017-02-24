package com.sds.acube.app.idir.org.user;

/**
 * Box.java
 * 2002-10-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Box 
{
	private String 	m_strBoxID = "";
	private String 	m_strDisplayName = "";
	private String  m_strParentBoxID = "";
	private String 	m_strDataURL = "";
	private String 	m_strUserUID = "";
	private String 	m_strDeptID = "";
	private int 	m_nIsExpand = 0;
	private int 	m_nIsSelected = 0;
	private int 	m_nIsBizCategory = 0;
	private String 	m_strBusinessID = "";
	
	/**
	 * 대장 업무 관련 Category 인지 여부 설정.
	 * @param nIsBizCategory 대장 업무 관련 Category 인지 여부 
	 */
	public void setIsBizCategory(int nIsBizCategory)
	{
		m_nIsBizCategory = nIsBizCategory;	
	} 
	
	/**
	 * 대장에 소속된 하위 업무 ID 설정.
	 * @param strBusinessID	대장 소속 하위 업무 ID
	 */
	public void setBusinessID(String strBusinessID)
	{
		m_strBusinessID = strBusinessID;
	}
	
	/**
	 * Sets 트리를 Expand상태로 Display할지 여부.
	 * @param nIsExpand The m_nIsExpand to set
	 */
	public void setIsExpand(int nIsExpand) 
	{
		m_nIsExpand = nIsExpand;
	}

	/**
	 * Sets 기본 Display함으로 선택되었는지 여부.
	 * @param nIsSelected The m_nIsSelected to set
	 */
	public void setIsSelected(int nIsSelected) 
	{
		m_nIsSelected = nIsSelected;
	}

	/**
	 * Sets 함 ID.
	 * @param strBoxID The m_strBoxID to set
	 */
	public void setBoxID(String strBoxID) 
	{
		m_strBoxID = strBoxID;
	}

	/**
	 * Sets DataURL.
	 * @param strDataURL The m_strDataURL to set
	 */
	public void setDataURL(String strDataURL) 
	{
		m_strDataURL = strDataURL;
	}
	
	/**
	 * 상위 BoxID 설정.
	 * @param parentBoxID 상위 BoxID
	 */
	public void setParentBoxID(String strParentBoxID) {
		
		m_strParentBoxID = strParentBoxID;
	}

	/**
	 * Sets DeptID.
	 * @param strDeptCode The m_strDeptCode to set
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * Sets 함 Diplay 명 .
	 * @param strDisplayName The m_strDisplayName to set
	 */
	public void setDisplayName(String strDisplayName) 
	{
		m_strDisplayName = strDisplayName;
	}

	/**
	 * Sets 사용자 UID.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}
		
	/**
	 * Returns 함 ID.
	 * @return String
	 */
	public String getBoxID() 
	{
		return m_strBoxID;
	}

	/**
	 * Returns Data URL.
	 * @return String
	 */
	public String getDataURL() 
	{
		return m_strDataURL;
	}

	/**
	 * Returns Dept ID.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * Returns 함 Display 명 .
	 * @return String
	 */
	public String getDisplayName() 
	{
		return m_strDisplayName;
	}

	/**
	 * Returns 사용자 UID.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
	
	/**
	 * Returns 트리를 Expand상태로 Display할지 여부.
	 * @return int
	 */
	public int getIsExpand() 
	{
		return m_nIsExpand;
	}

	/**
	 * Returns 기본 Display함으로 선택되었는지 여부.
	 * @return int
	 */
	public int getIsSelected() 
	{
		return m_nIsSelected;
	}
	
	/**
	 * 대장 업무 관련 Category 인지 여부 반환.
	 * @return int
	 */
	public int getIsBizCategory()
	{
		return m_nIsBizCategory;	
	} 
	
	/**
	 * 대장에 소속된 하위 업무 ID 반환.
	 * @param String
	 */
	public String getBusinessID()
	{
		return m_strBusinessID;
	}
	
	/**
	 * 상위 Box ID 반환.
	 * @param String
	 */
	public String getParentBoxID() {
		
		return m_strParentBoxID;
	}
}
