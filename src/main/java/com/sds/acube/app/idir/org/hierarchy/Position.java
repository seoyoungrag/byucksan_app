package com.sds.acube.app.idir.org.hierarchy;

/**
 * Position.java
 * 2002-10-31
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Position 
{
	private String m_strPositionID = "";
	private String m_strPositionName = "";
	private String m_strPositionAbbrName = "";
	private String m_strPositionOtherName = "";
	private String m_strPositionParentID = "";
	private String m_strCompID = "";
	private int	m_nPositionOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * 직위 순서 설정.
	 * @param nPositionOrder The m_nPositionOrder to set
	 */
	public void setPositionOrder(int nPositionOrder) 
	{
		m_nPositionOrder = nPositionOrder;
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
	 * 직위 약어 설정.
	 * @param strPositionAbbrName The m_strPositionAbbrName to set
	 */
	public void setPositionAbbrName(String strPositionAbbrName) 
	{
		m_strPositionAbbrName = strPositionAbbrName;
	}

	/**
	 * 직위 ID 설정.
	 * @param strPositionID The m_strPositionID to set
	 */
	public void setPositionID(String strPositionID) 
	{
		m_strPositionID = strPositionID;
	}

	/**
	 * 직위명 설정.
	 * @param strPositionName The m_strPositionName to set
	 */
	public void setPositionName(String strPositionName) 
	{
		m_strPositionName = strPositionName;
	}

	/**
	 * 타언어 직위명 설정.
	 * @param strPositionOtherName The m_strPositionOtherName to set
	 */
	public void setPositionOtherName(String strPositionOtherName) 
	{
		m_strPositionOtherName = strPositionOtherName;
	}

	/**
	 * 상위 직위 ID 설정.
	 * @param strPositionParentID The m_strPositionParentID to set
	 */
	public void setPositionParentID(String strPositionParentID) 
	{
		m_strPositionParentID = strPositionParentID;
	}
	
	/**
	 * 직위 순서 반환.
	 * @return int
	 */
	public int getPositionOrder() 
	{
		return m_nPositionOrder;
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
	 * 직위 약어 반환.
	 * @return String
	 */
	public String getPositionAbbrName() 
	{
		return m_strPositionAbbrName;
	}

	/**
	 * 직위 ID 반환.
	 * @return String
	 */
	public String getPositionID() 
	{
		return m_strPositionID;
	}

	/**
	 * 직위명 반환.
	 * @return String
	 */
	public String getPositionName() 
	{
		return m_strPositionName;
	}

	/**
	 * 타언어 직위명 반환.
	 * @return String
	 */
	public String getPositionOtherName() 
	{
		return m_strPositionOtherName;
	}

	/**
	 * 상위 직위 ID 반환.
	 * @return String
	 */
	public String getPositionParentID() 
	{
		return m_strPositionParentID;
	}
}
