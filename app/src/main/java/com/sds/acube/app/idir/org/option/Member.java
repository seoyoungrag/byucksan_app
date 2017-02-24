package com.sds.acube.app.idir.org.option;

/**
 * Member.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Member 
{
	private String m_strGroupID = "";
	private String m_strMemberID = "";
	private int	m_nMemberType = 0;
	
	/**
	 * Member Type 설정.
	 * @param nMemberType The m_nMemberType to set
	 */
	public void setMemberType(int nMemberType) 
	{
		m_nMemberType = nMemberType;
	}

	/**
	 * Group ID 설정.
	 * @param strGroupID The m_strGroupID to set
	 */
	public void setGroupID(String strGroupID) 
	{
		m_strGroupID = strGroupID;
	}

	/**
	 * Member ID 설정.
	 * @param strMemberID The m_strMemberID to set
	 */
	public void setMemberID(String strMemberID) 
	{
		this.m_strMemberID = strMemberID;
	}
	
	
	/**
	 * Member Type 반환.
	 * @return int
	 */
	public int getMemberType() 
	{
		return m_nMemberType;
	}

	/**
	 * Group ID 반환.
	 * @return String
	 */
	public String getGroupID() 
	{
		return m_strGroupID;
	}

	/**
	 * Member ID 반환.
	 * @return String
	 */
	public String getMemberID() 
	{
		return m_strMemberID;
	}
}
