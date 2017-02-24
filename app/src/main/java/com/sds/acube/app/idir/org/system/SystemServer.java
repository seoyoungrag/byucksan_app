package com.sds.acube.app.idir.org.system;

/**
 * SystemServer.java
 * 2003-12-10
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemServer 
{
	private String 	m_strSystemID = "";
	private String 	m_strServerName = "";
	private String 	m_strServerIP = "";
	private boolean m_bIsHomeServer = false;
	private String 	m_strDBInfo = "";
	private String	m_strDBPort = "";
	private String 	m_strDBLoginID = "";
	private String 	m_strDBLoginPassword = "";
	private int 	m_nDBType = 0;	  		// 0 : Oracle  1 : MSSQL
	private int		m_nServerType = 0;    	// 0 : Home Server	1 : Remote Server  2 : Discard Server
	
	/**
	 * 결재 분산 서버 Type 설정.
	 * @param nServerType 결재 분산 서버 Type
	 */
	public void setServerType(int nServerType)
	{
		m_nServerType = nServerType;
	}
	
	/**
	 * Home 서버 여부 설정.
	 * @param bIsHomeServer Home 서버 여부
	 */
	public void setIsHomeServer(boolean bIsHomeServer) 
	{
		m_bIsHomeServer = bIsHomeServer;
	}

	/**
	 * DB 정보 설정.
	 * @param strDBInfo DB 정보
	 */
	public void setDBInfo(String strDBInfo) 
	{
		m_strDBInfo = strDBInfo;
	}

	/**
	 * DB LoginID 설정.
	 * @param strDBLoginID DB LoginID
	 */
	public void setDBLoginID(String strDBLoginID) 
	{
		m_strDBLoginID = strDBLoginID;
	}

	/**
	 * DB LoginPassword 설정.
	 * @param strDBLoginPassword DB LoginPassword
	 */
	public void setDBLoginPassword(String strDBLoginPassword) 
	{
		m_strDBLoginPassword = strDBLoginPassword;
	}

	/**
	 * DB Port 설정.
	 * @param strDBPort DB Port
	 */
	public void setDBPort(String strDBPort) 
	{
		m_strDBPort = strDBPort;
	}

	/**
	 * DB Type 설정.
	 * @param nDBType DB Type
	 */
	public void setDBType(int nDBType) 
	{
		m_nDBType = nDBType;
	}

	/**
	 * Server IP 설정.
	 * @param strServerIP Server IP
	 */
	public void setServerIP(String strServerIP) 
	{
		m_strServerIP = strServerIP;
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
	 * System ID 설정.
	 * @param strSystemID System ID
	 */
	public void setSystemID(String strSystemID) 
	{
		m_strSystemID = strSystemID;
	}
	
	/**
	 * Home Server 여부 반환.
	 * @return boolean
	 */
	public boolean IsHomeServer() 
	{
		return m_bIsHomeServer;
	}

	/**
	 * DB 정보 반환.
	 * @return String
	 */
	public String getDBInfo() 
	{
		return m_strDBInfo;
	}

	/**
	 * DB LoginID 반환.
	 * @return String
	 */
	public String getDBLoginID() 
	{
		return m_strDBLoginID;
	}

	/**
	 * DB LoginPassword 반환.
	 * @return String
	 */
	public String getDBLoginPassword() 
	{
		return m_strDBLoginPassword;
	}

	/**
	 * DB Port 반환.
	 * @return String
	 */
	public String getDBPort() 
	{
		return m_strDBPort;
	}

	/**
	 * DB Type 반환.
	 * @return int
	 */
	public int getDBType() 
	{
		return m_nDBType;
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
	 * System ID 반환.
	 * @return String
	 */
	public String getSystemID() 
	{
		return m_strSystemID;
	}
	
	/**
	 * 결재 분산 서버 Type 반환.
	 * @return int 
	 */
	public int getServerType()
	{
		return m_nServerType;
	}
}
