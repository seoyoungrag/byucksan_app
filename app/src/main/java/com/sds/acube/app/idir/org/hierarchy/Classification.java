package com.sds.acube.app.idir.org.hierarchy;

/**
 * Classification.java
 * 2002-10-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Classification 
{
	private String m_strClassificationID = "";
	private String m_strClassificationName = "";
	private String m_strRetentionDate = "";
	private String m_strClassificationParentID = "";
	private String m_strCompID = "";
	private String m_strDescription = "";
	private String m_strClassificationCode = "";
	private int	m_nDepth = 0;
	private String m_strHasChild = "U";
	
	/**
	 * ClassificationCode 설정.
	 * @param strClassificationCode The m_strClassificationCode to set
	 */
	public void setClassificationCode(String strClassificationCode)
	{
		m_strClassificationCode = strClassificationCode;
	}
	
	/**
	 * Display Depth 설정.
	 * @param nDepth The m_nDepth to set
	 */
	public void setDepth(int nDepth)
	{
		m_nDepth = nDepth;
	}
	
	/**
	 * Tree 구성시 하위 트리 구성 여부 설정. 
	 * @param strHasChild The m_strHasChild to set
	 */
	public void setHasChild(String strHasChild)
	{
		m_strHasChild = strHasChild;
	}
	
	/**
	 * 분류 체계 ID 설정.
	 * @param strClassificationID The m_strClassificationID to set
	 */
	public void setClassificationID(String strClassificationID) 
	{
		m_strClassificationID = strClassificationID;
	}

	/**
	 * 분류 체계명 설정.
	 * @param strClassificationName The m_strClassificationName to set
	 */
	public void setClassificationName(String strClassificationName) 
	{
		m_strClassificationName = strClassificationName;
	}

	/**
	 * 상위 분류 체계 ID 설정.
	 * @param strClassificationParentID The m_strClassificationParentID to set
	 */
	public void setClassificationParentID(String strClassificationParentID) 
	{
		m_strClassificationParentID = strClassificationParentID;
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
	 * 보존 연한 설정.
	 * @param strRetentionDate The m_strRetentionDate to set
	 */
	public void setRetentionDate(String strRetentionDate) 
	{
		m_strRetentionDate = strRetentionDate;
	}
	
	/**
	 * Classification Code 반환.
	 * @return String
	 */
	public String getClassificationCode()
	{
		return m_strClassificationCode;
	}
	
	/**
	 * Display Depth 반환.
	 * @return int
	 */
	public int getDepth()
	{
		return m_nDepth;
	}
	
	/**
	 * Tree 구성시 하위 트리 구성 여부 반환.
	 * @return String
	 */
	public String getHasChild()
	{
		return m_strHasChild;
	}
	
	/**
	 * 분류 체계 ID 반환.
	 * @return String
	 */
	public String getClassificationID() 
	{
		return m_strClassificationID;
	}

	/**
	 * 분류체계명 반환.
	 * @return String
	 */
	public String getClassificationName() 
	{
		return m_strClassificationName;
	}

	/**
	 * 상위 분류 체계 ID 반환.
	 * @return String
	 */
	public String getClassificationParentID() 
	{
		return m_strClassificationParentID;
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
	 * 보존 연한 반환.
	 * @return String
	 */
	public String getRetentionDate() 
	{
		return m_strRetentionDate;
	}
}
