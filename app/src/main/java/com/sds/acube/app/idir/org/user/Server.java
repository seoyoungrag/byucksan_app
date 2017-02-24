package com.sds.acube.app.idir.org.user;

/**
 * Server.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Server 
{
	private String m_strServerName = "";
	private String m_strServerType = "";
	
	/**
	 * Sets 서버명. 
	 * @param m_strServerName The m_strServerName to set
	 */
	public void setServerName(String strServerName)
	{
		m_strServerName = strServerName;
	} 
	
	/**
	 * Sets 서버 Type. 
	 * @param m_strServerType The m_strServerType to set
	 */
	public void setServerType(String strServerType)
	{
		m_strServerType = strServerType;
	}
	
	/**
	 * Returns 서버명.
	 * @return String
	 */
	public String getServerName()
	{
		return m_strServerName;
	}
	
	/**
	 * Returns 서버 Type.
	 * @return String
	 */
	public String getServerType()
	{
		return m_strServerType;
	}
}
