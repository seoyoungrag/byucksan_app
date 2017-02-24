package com.sds.acube.app.idir.org.orginfo;

/**
 * DeptServer.java
 * 2002-12-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DeptServer 
{
	public static String SYSTEM_TYPE_GWDB = "GWDB";
	public static String SYSTEM_TYPE_GWSTORE = "GWSTORE";
	
	public static int SERVER_TYPE_DB_HOME = 0;
	public static int SERVER_TYPE_DB_REMOTE = 1;
	public static int SERVER_TYPE_STORE = 10;

	private String m_strSystemName = "";
	private String m_strServerName = "";
	private int    m_nServerType = 1;
	
	/**
	 * Server Type 설정.
	 * @param nServerType Server Type
	 */
	public void setServerType(int nServerType) 
	{
		m_nServerType = nServerType;
	}

	/**
	 * Server명 설정.
	 * @param strServerName Server명
	 */
	public void setServerName(String strServerName) 
	{
		m_strServerName = strServerName;
	}

	/**
	 * System명 설정.
	 * @param strSystemName 시스템명 
	 */
	public void setSystemName(String strSystemName) 
	{
		m_strSystemName = strSystemName;
	}
	
	/**
	 * Server Type 반환.
	 * @return int
	 */
	public int getServerType() 
	{
		return m_nServerType;
	}

	/**
	 * Server 명 반환.
	 * @return String
	 */
	public String getServerName() 
	{
		return m_strServerName;
	}

	/**
	 * System 명 반환.
	 * @return String
	 */
	public String getSystemName() 
	{
		return m_strSystemName;
	}
}
