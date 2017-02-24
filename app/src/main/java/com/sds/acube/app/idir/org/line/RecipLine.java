package com.sds.acube.app.idir.org.line;

/**
 * RecipLine.java
 * 2002-10-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class RecipLine {
	
	private String 	m_strRecipGroupID = "";
	private String 	m_strRecipGroupName = "";
	private String 	m_strOrgID = "";
	private String 	m_strUserUID = "";
	private String 	m_strWhenCreated = "";
	private String 	m_strEnforceBound = "";
	private String 	m_strIsFavorite = "";
	private String 	m_strDescription = "";	

	/**
	 * 수신그룹 ID 설정.
	 * @param strRecipGroupID The m_strRecipGroupID to set
	 */
	public void setRecipGroupID(String strRecipGroupID) 
	{
		m_strRecipGroupID = strRecipGroupID;
	}

	/**
	 * 수신그룹 이름 설정.
	 * @param strRecipGroupName The m_strRecipGroupName to set
	 */
	public void setRecipGroupName(String strRecipGroupName) 
	{
		m_strRecipGroupName = strRecipGroupName;
	}

	/**
	 * 소속조직 ID 설정.
	 * @param strOrgID The m_strOrgID to set
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * 등록자 UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	/**
	 * 등록 일시 설정.
	 * @param strWhenCreated The m_strWhenCreated to set
	 */
	public void setWhenCreated(String strWhenCreated) 
	{
		m_strWhenCreated = strWhenCreated;
	}

	/**
	 * 시행범위
	 * @param strEnforceBound The m_strEnforceBound to set
	 */
	public void setEnforceBound(String strEnforceBound) 
	{
		m_strEnforceBound = strEnforceBound;
	}

	/**
	 * 기본 경로로 사용 여부 설정.
	 * @param strIsFavorite The m_strIsFavorite to set
	 */
	public void setIsFavorite(String strIsFavorite) 
	{
		m_strIsFavorite = strIsFavorite;
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
	 * 수신그룹 ID 반환.
	 * @return String
	 */
	public String getRecipGroupID() 
	{
		return m_strRecipGroupID;
	}

	/**
	 * 수신그룹명 반환.
	 * @return String
	 */
	public String getRecipGroupName() 
	{
		return m_strRecipGroupName;
	}

	/**
	 * 소속 부서ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}

	/**
	 * 등록자 ID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}

	/**
	 * 등록 일시 반환.
	 * @return String
	 */
	public String getWhenCreated() 
	{
		return m_strWhenCreated;
	}

	/**
	 * 시행범위 반환.
	 * @return String
	 */
	public String getEnforceBound() 
	{
		return m_strEnforceBound;
	}

	/**
	 * 기본 경로로 설정 여부 반환.
	 * @return String
	 */
	public String getIsFavorite() 
	{
		return m_strIsFavorite;
	}
	
	/**
	 * Description 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
	}
}
