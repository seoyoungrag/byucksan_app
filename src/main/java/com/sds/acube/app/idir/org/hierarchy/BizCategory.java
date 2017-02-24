package com.sds.acube.app.idir.org.hierarchy;

/**
 * BizCategory.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BizCategory 
{
	private String m_strCategoryID = "";
	private String m_strCategoryName = "";
	private String m_strParentCategoryID = "";
	private String m_strCompanyID = "";
	private int    m_nCategoryOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * Category 순서 설정.
	 * @param nCategoryOrder Category 순서
	 */
	public void setCategoryOrder(int nCategoryOrder) 
	{
		m_nCategoryOrder = nCategoryOrder;
	}

	/**
	 * Category ID 설정.
	 * @param strCategoryID Category ID
	 */
	public void setCategoryID(String strCategoryID) 
	{
		m_strCategoryID = strCategoryID;
	}

	/**
	 * Category 명 설정.
	 * @param strCategoryName Category 명
	 */
	public void setCategoryName(String strCategoryName) 
	{
		m_strCategoryName = strCategoryName;
	}

	/**
	 * Company ID 설정.
	 * @param strCompanyID Company ID
	 */
	public void setCompanyID(String strCompanyID) 
	{
		m_strCompanyID = strCompanyID;
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
	 * 상위 CategoryID 설정.
	 * @param strParentCategoryID 상위 Category ID
	 */
	public void setParentCategoryID(String strParentCategoryID) 
	{
		m_strParentCategoryID = strParentCategoryID;
	}
	
	/**
	 * Category 순서 반환.
	 * @return int
	 */
	public int getCategoryOrder() 
	{
		return m_nCategoryOrder;
	}

	/**
	 * Category ID 반환.
	 * @return String
	 */
	public String getCategoryID() 
	{
		return m_strCategoryID;
	}

	/**
	 * Category Name 반환.
	 * @return String
	 */
	public String getCategoryName() 
	{
		return m_strCategoryName;
	}

	/**
	 * Company ID 반환.
	 * @return String
	 */
	public String getCompanyID() 
	{
		return m_strCompanyID;
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
	 * 상위 Category ID 반환.
	 * @return String
	 */
	public String getParentCategoryID() 
	{
		return m_strParentCategoryID;
	}
}
