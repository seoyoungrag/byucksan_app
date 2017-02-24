package com.sds.acube.app.idir.org.hierarchy;

/**
 * Duty.java
 * 2005-10-17
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class Duty 
{
	private String m_strDutyID = "";
	private String m_strDutyName = "";
	private String m_strDutyOtherName = "";
	private String m_strDutyParentID = "";
	private String m_strCompID = "";
	private int	   m_nDutyOrder = 0;
	private String m_strDescription = "";
	
	/**
	 * 직무 순서 설정.
	 * @param nDutyOrder 직무 순서
	 */
	public void setDutyOrder(int nDutyOrder) 
	{	
		m_nDutyOrder = nDutyOrder;
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
	 * 설명 설정.
	 * @param strDescription 설명
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * 직무 ID 설정.
	 * @param strDutyID 직무 ID
	 */
	public void setDutyID(String strDutyID) 
	{
		m_strDutyID = strDutyID;
	}

	/**
	 * 직무 명 설정.
	 * @param strDutyName 직무 명
	 */
	public void setDutyName(String strDutyName) 
	{	
		m_strDutyName = strDutyName;
	}

	/**
	 * 타언어 직무 명 설정.
	 * @param strDutyOtherName 타 언어 직무명
	 */
	public void setDutyOtherName(String strDutyOtherName) 
	{	
		m_strDutyOtherName = strDutyOtherName;
	}

	/**
	 * 상위 직무 ID 설정.
	 * @param strDutyParentID 상위 직무 ID
	 */
	public void setDutyParentID(String strDutyParentID) 
	{
		m_strDutyParentID = strDutyParentID;
	}
	
	/**
	 * 직무 순서 반환.
	 * @return int
	 */
	public int getDutyOrder() 
	{
		return m_nDutyOrder;
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
	 * 설명 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
	}

	/**
	 * 직무 ID 반환.
	 * @return String
	 */
	public String getDutyID() 
	{
		return m_strDutyID;
	}

	/**
	 * 직무 명 반환.
	 * @return String
	 */
	public String getDutyName() 
	{
		return m_strDutyName;
	}

	/**
	 * 타언어 직무명 반환.
	 * @return String
	 */
	public String getDutyOtherName() 
	{	
		return m_strDutyOtherName;
	}

	/**
	 * 상위 직무 ID 반환.
	 * @return String
	 */
	public String getDutyParentID() 
	{
		return m_strDutyParentID;
	}
}
