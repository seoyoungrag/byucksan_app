package com.sds.acube.app.idir.org.hierarchy;

import java.io.Serializable;

/**
 * Grade.java
 * 2002-10-31
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Grade implements Serializable  
{
	private String m_strGradeID = "";
	private String m_strGradeName = "";
	private String m_strGradeOtherName = "";
	private String m_strGradeParentID = "";
	private String m_strCompID = "";
	private String m_strGradeAbbrName = "";
	private int	m_nGradeOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * 직급 Order 설정.
	 * @param nGradeOrder The m_nGradeOrder to set
	 */
	public void setGradeOrder(int nGradeOrder) 
	{
		m_nGradeOrder = nGradeOrder;
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
	 * 직급 약어 설정.
	 * @param strGradeAbbrName The m_strGradeAbbrName to set
	 */
	public void setGradeAbbrName(String strGradeAbbrName) 
	{
		m_strGradeAbbrName = strGradeAbbrName;
	}

	/**
	 * 직급 ID 설정.
	 * @param strGradeID The m_strGradeID to set
	 */
	public void setGradeID(String strGradeID) 
	{
		m_strGradeID = strGradeID;
	}

	/**
	 * 직급 명 설정.
	 * @param strGradeName The m_strGradeName to set
	 */
	public void setGradeName(String strGradeName) 
	{
		m_strGradeName = strGradeName;
	}

	/**
	 * 타언어 직급 명 설정.
	 * @param strGradeOtherName The m_strGradeOtherName to set
	 */
	public void setGradeOtherName(String strGradeOtherName) 
	{
		m_strGradeOtherName = strGradeOtherName;
	}

	/**
	 * 상위 직급 ID 설정.
	 * @param strGradeParentID The m_strGradeParentID to set
	 */
	public void setGradeParentID(String strGradeParentID) 
	{
		m_strGradeParentID = strGradeParentID;
	}
	
	
	/**
	 * 직급 순서 반환.
	 * @return int
	 */
	public int getGradeOrder() 
	{
		return m_nGradeOrder;
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
	 * 직급 약어 반환.
	 * @return String
	 */
	public String getGradeAbbrName() 
	{
		return m_strGradeAbbrName;
	}

	/**
	 * 직급 ID 반환.
	 * @return String
	 */
	public String getGradeID() 
	{
		return m_strGradeID;
	}

	/**
	 * 직급명 반환.
	 * @return String
	 */
	public String getGradeName() 
	{
		return m_strGradeName;
	}

	/**
	 * 타언어 직급명 반환.
	 * @return String
	 */
	public String getGradeOtherName() 
	{
		return m_strGradeOtherName;
	}

	/**
	 * 상위 직급 ID 반환.
	 * @return String
	 */
	public String getGradeParentID() 
	{
		return m_strGradeParentID;
	}
}
