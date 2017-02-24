package com.sds.acube.app.idir.org.user;

/**
 * UserProle.java
 * 2004-12-22
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserProle 
{
	private String 	m_strUserUID = "";
	private String 	m_strProleID = "";
	private String 	m_strDescription = "";
	
	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
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
	 * 사용자 UID 설정.
	 * @param strUserUID 사용자 UID
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
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
	 * 프로세스 역할 ID 반환.
	 * @return String
	 */
	public String getProleID() 
	{
		return m_strProleID;
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
}
