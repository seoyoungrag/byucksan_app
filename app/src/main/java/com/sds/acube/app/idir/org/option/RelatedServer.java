package com.sds.acube.app.idir.org.option;

/**
 * RelatedServer.java
 * 2002-11-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class RelatedServer 
{
	public static String SYSTEM_TYPE_GWDB = "GWDB";
	public static String SYSTEM_TYPE_GWSTORE = "GWSTORE";
		
	private String 	m_strServerID = "";
	private String 	m_strSystemName = "";
	private String 	m_strServerName = "";
	private String 	m_strServerIP = "";
	private String 	m_strConnectionInfo = "";
	private int		m_nServerType = 0;
	private String 	m_strDescription = "";
	
	/**
	 * Server Type 설정.
	 * @param nServerType The m_nServerType to set
	 */
	public void setServerType(int nServerType) 
	{
		m_nServerType = nServerType;
	}

	/**
	 * Connection Informaiton 설정.
	 * @param strConnectionInfo The m_strConnectionInfo to set
	 */
	public void setConnectionInfo(String strConnectionInfo) 
	{
		m_strConnectionInfo = strConnectionInfo;
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
	 * Server ID 설정.
	 * @param strServerID The m_strServerID to set
	 */
	public void setServerID(String strServerID) 
	{
		m_strServerID = strServerID;
	}

	/**
	 * Server IP 설정.
	 * @param strServerIP The m_strServerIP to set
	 */
	public void setServerIP(String strServerIP) 
	{
		m_strServerIP = strServerIP;
	}

	/**
	 * Server Name 설정.
	 * @param strServerName The m_strServerName to set
	 */
	public void setServerName(String strServerName) 
	{
		m_strServerName = strServerName;
	}

	/**
	 * System Name 설정.
	 * @param strSystemName The m_strSystemName to set
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
	 * Connection Information 반환.
	 * @return String
	 */
	public String getConnectionInfo() 
	{
		return m_strConnectionInfo;
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
	 * Server ID 반환.
	 * @return String
	 */
	public String getServerID() 
	{
		return m_strServerID;
	}

	/**
	 * Server IP 반환.
	 * @return String
	 */
	public String getServerIP() 
	{
		return m_strServerIP;
	}

	/**
	 * Server Name 반환.
	 * @return String
	 */
	public String getServerName() 
	{
		return m_strServerName;
	}

	/**
	 * System Name 반환.
	 * @return String
	 */
	public String getSystemName() 
	{
		return m_strSystemName;
	}
}
