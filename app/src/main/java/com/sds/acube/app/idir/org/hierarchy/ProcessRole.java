package com.sds.acube.app.idir.org.hierarchy;

/**
 * ProcessRole.java
 * 2006-09-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class ProcessRole {

	private String  m_strProleID = "";
	private String  m_strProleName = "";
	private String  m_strPcost = "";
	private String  m_strCompID = "";
	private String  m_strDescription = "";	
	private String  m_strProleParentID = "";
	private String  m_strProleOtherName = "";
	private int 	m_nProleOrder = -1;
	
	/**
	 * 회사 코드 설정.
	 * @param strCompID 회사 코드 
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
	 * 비용 설정.
	 * @param strPcost 비용
	 */
	public void setPcost(String strPcost) 
	{
		m_strPcost = strPcost;
	}

	/**
	 * 프로세스 역할 ID 설정.
	 * @param strProleID 프로세스 역할 ID
	 */
	public void setProleID(String strProleID) 
	{
		m_strProleID = strProleID;
	}

	/**
	 * 프로세스 역할 이름 설정.
	 * @param strProleName 프로세스 역할 이름
	 */
	public void setProleName(String strProleName) 
	{
		m_strProleName = strProleName;
	}
	
	/**
	 * 부모 프로세스 역할 ID 설정.
	 * @param strProleParentID 부모 프로세스 역할 ID
	 */
	public void setProleParentID(String strProleParentID)
	{
		m_strProleParentID = strProleParentID;	
	}
	
	/**
	 * 타언어권 프로세스 역할 이름 설정.
	 * @param strProleOtherName 타언어권 프로세스 역할 이름
	 */
	public void setProleOtherName(String strProleOtherName)
	{
		m_strProleOtherName = strProleOtherName;	
	}
	
	/**
	 * 프로세스 역할 표시 순서 설정.
	 * @param strProleOrder 프로세스 역할 표시 순서
	 */
	public void setProleOrder(int nProleOrder)
	{
		m_nProleOrder = nProleOrder;
	}
	
	/**
	 * 부모 프로세스 역할 ID 반환.
	 * @return String
	 */
	public String getProleParentID()
	{
		return m_strProleParentID;	
	}
	
	/**
	 * 타언어권 프로세스 역할 이름 반환.
	 * @return String
	 */
	public String getProleOtherName()
	{
		return m_strProleOtherName;	
	}
	
	/**
	 * 프로세스 역할 표시 순서 반환.
	 * @return int
	 */
	public int getProleOrder()
	{
		return m_nProleOrder;
	}
		
	/**
	 * 회사 코드 반환.
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
	 * 비용 반환.
	 * @return String
	 */
	public String getPcost() 
	{
		return m_strPcost;
	}

	/**
	 * 프로세스 역할 ID 반환.
	 * @return String
	 */
	public String getProleID() 
	{
		return m_strProleID;
	}

	/**
	 * 프로세스 역할 이름 반환.
	 * @return String
	 */
	public String getProleName() 
	{
		return m_strProleName;
	}
}
