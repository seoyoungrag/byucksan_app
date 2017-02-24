package com.sds.acube.app.idir.org.hierarchy;

/**
 * Title.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Title 
{
	private String m_strTitleID = "";
	private String m_strTitleName = "";
	private String m_strTitleOtherName = "";
	private String m_strTitleParentID = "";
	private String m_strCompID = "";
	private int	m_nTitleOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * 직책 순서 설정.
	 * @param nTitleOrder The m_nTitleOrder to set
	 */
	public void setTitleOrder(int nTitleOrder) 
	{
		m_nTitleOrder = nTitleOrder;
	}

	/**
	 * 회사 ID 설정.
	 * @param strCompID The m_strCompID to set
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * Description 설정.
	 * @param strDescription The m_strDescription to set
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * 직책 ID 설정.
	 * @param strTitleID The m_strTitleID to set
	 */
	public void setTitleID(String strTitleID) 
	{
		m_strTitleID = strTitleID;
	}

	/**
	 * 직책명 설정.
	 * @param strTitleName The m_strTitleName to set
	 */
	public void setTitleName(String strTitleName) 
	{
		m_strTitleName = strTitleName;
	}

	/**
	 * 타언어 직책명 설정.
	 * @param strTitleOtherName The m_strTitleOtherName to set
	 */
	public void setTitleOtherName(String strTitleOtherName) 
	{
		m_strTitleOtherName = strTitleOtherName;
	}

	/**
	 * 상위 직책 ID 설정.
	 * @param strTitleParentID The m_strTitleParentID to set
	 */
	public void setTitleParentID(String strTitleParentID) 
	{
		m_strTitleParentID = strTitleParentID;
	}
	
	/**
	 * 직책 순서 반환.
	 * @return int
	 */
	public int getTitleOrder() 
	{
		return m_nTitleOrder;
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
	 * 직책 ID 반환.
	 * @return String
	 */
	public String getTitleID() 
	{
		return m_strTitleID;
	}

	/**
	 * 직책명 반환.
	 * @return String
	 */
	public String getTitleName() 
	{
		return m_strTitleName;
	}

	/**
	 * 타언어 직책명 반환.
	 * @return String
	 */
	public String getTitleOtherName() 
	{
		return m_strTitleOtherName;
	}

	/**
	 * 상위 직책 ID 반환.
	 * @return String
	 */
	public String getTitleParentID() 
	{
		return m_strTitleParentID;
	}
}
