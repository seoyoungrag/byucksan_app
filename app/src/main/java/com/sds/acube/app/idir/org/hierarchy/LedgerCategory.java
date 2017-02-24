package com.sds.acube.app.idir.org.hierarchy;

/**
 * LedgerCategory.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LedgerCategory 
{
	private String m_strCategoryID = "";
	private String m_strCategoryName = "";
	private String m_strCompanyID = "";
	private String m_strFolderType = "";
	private String m_strBusinessID = "";
	private int    m_nCategoryOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * 순서 정보 설정.
	 * @param nCategoryOrder 순서 정보
	 */
	public void setCategoryOrder(int nCategoryOrder) 
	{
		m_nCategoryOrder = nCategoryOrder;
	}

	/**
	 * 소속업무 정보 설정.
	 * @param strBusinessID 소속업무 정보
	 */
	public void setBusinessID(String strBusinessID) 
	{
		m_strBusinessID = strBusinessID;
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
	 * 소속 회사 ID 설정.
	 * @param strCompanyID 소속 회사 ID
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
	 * 소속함 정보 설정.
	 * @param strFolderType 소속함 정보
	 */
	public void setFolderType(String strFolderType) 
	{
		m_strFolderType = strFolderType;
	}

	
	/**
	 * 순서 정보 반환.
	 * @return int
	 */
	public int getCategoryOrder() 
	{
		return m_nCategoryOrder;
	}

	/**
	 * 소속 업무 정보 반환.
	 * @return String
	 */
	public String getBusinessID() 
	{
		return m_strBusinessID;
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
	 * 소속함 정보 반환.
	 * @return String
	 */
	public String getFolderType() 
	{
		return m_strFolderType;
	}
}
