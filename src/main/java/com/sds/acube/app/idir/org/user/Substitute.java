package com.sds.acube.app.idir.org.user;

/**
 * Substitute.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Substitute extends Employee
{
	private String m_strStartDate = "";
	private String m_strEndDate = "";
	
	
	/**
	 * Sets 대결 종료 일자. 
	 * @param m_strEndData The m_strEndData to set
	 */
	public void setEndDate(String strEndDate) 
	{
		m_strEndDate = strEndDate;
	}

	/**
	 * Sets 대결 시작 일자.
	 * @param m_strStartData The m_strStartData to set
	 */
	public void setStartDate(String strStartDate) 
	{
		m_strStartDate = strStartDate;
	}
	
	/**
	 * Returns 대결 종료 일자.
	 * @return String
	 */
	public String getEndDate() 
	{
		return m_strEndDate;
	}

	/**
	 * Returns 대결 시작 일자.
	 * @return String
	 */
	public String getStartDate() 
	{
		return m_strStartDate;
	}
}
