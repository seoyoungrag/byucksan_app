package com.sds.acube.app.idir.common.vo;

/**
 * ConnectionParam.java
 * 2002-10-09
 *
 *
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.io.Serializable;

public class ConnectionParam implements Serializable
{
	public static final int DB_TYPE_ORACLE = 0;
	public static final int DB_TYPE_MSSQL = 1;
	public static final int DB_TYPE_DB2 = 2;
	public static final int DB_TYPE_ALTIBASE = 3;
	public static final int DB_TYPE_SYBASE = 4;
	
	public static final int WAS_TYPE_WEBLOGIC = 0;
	public static final int WAS_TYPE_WEBLOGIC_8_1 = 1;
	public static final int WAS_TYPE_JEUS = 10;

	public static final int METHOD_CREATE = 0;
	public static final int METHOD_GET_USING_DM = 1;
	public static final int METHOD_GET_USING_DS = 2;

	private String m_strClassName;
	private String m_strURL;
	private String m_strDSName;
	private String m_strUser;
	private String m_strPassword;
	private int m_nLoginTimeout;
	private int m_nMethod;
    private int m_nDBType;
    private int m_nWASType;

	/**
	 * Constructor
	 */
	public ConnectionParam()
	{
		m_strClassName = "";
		m_strURL = "";
		m_strDSName = "";
		m_strUser = "";
		m_strPassword = "";
		m_nLoginTimeout = 0;
		m_nMethod = METHOD_CREATE;
		m_nDBType = DB_TYPE_ORACLE;
		m_nWASType = WAS_TYPE_WEBLOGIC;
	}

	/**
	 * ClassName 설정
	 * @param strClassName The fully qualified name of the desired class.
	 */
	public void setClassName(String strClassName)
	{
		m_strClassName = strClassName;
	}

	/**
	 * URL 설정
	 * @param strURL A database url of the form jdbc:subprotocol:subname.
	 */
	public void setURL(String strURL)
	{
		m_strURL = strURL;
	}

	/**
	 * DataSource-Name 설정
	 * @param strDSName The name of the object to look up.
	 */
	public void setDSName(String strDSName)
	{
		m_strDSName = strDSName;
	}

	/**
	 * DB User 설정.
	 * @param strUser DB User
	 */
	public void setUser(String strUser)
	{
		m_strUser = strUser;
	}

	/**
	 * DB User Password 설정.
	 * @param strPassword DB User password
	 */
	public void setPassword(String strPassword)
	{
		m_strPassword = strPassword;
	}

	/**
	 * LoginTimeout 설정.
	 * @param nLoginTimeout The login time limit in seconds.
	 */
	public void setLoginTimeout(int nLoginTimeout)
	{
		m_nLoginTimeout = nLoginTimeout;
	}

	/**
	 * Method 설정.
	 * @param nMethod Connection 설정 방법
	 */
	public void setMethod(int nMethod)
	{
		m_nMethod = nMethod;
	}

	/**
	 * DB Type 설정.
	 * @param nDBType Database 종류
	 */
	public void setDBType(int nDBType)
	{
		m_nDBType = nDBType;
	}

	/**
	 * WAS Type 설정.
	 * @param nWASType WAS 종류
	 */
	public void setWASType(int nWASType)
	{
		m_nWASType = nWASType;
	}

	/**
	 * ClassName을 얻음
	 * @return String
	 */
	public String getClassName()
	{
		return m_strClassName;
	}

	/**
	 * URL을 얻음.
	 * @return String
	 */
	public String getURL()
	{
		return m_strURL;
	}

	/**
	 * DataSource-Name을 얻음.
	 * @return String
	 */
	public String getDSName()
	{
		return m_strDSName;
	}

	/**
	 * DB User 얻음.
	 * @return String
	 */
	public String getUser()
	{
		return m_strUser;
	}

	/**
	 * DB User Password 얻음.
	 * @return String
	 */
	public String getPassword()
	{
		return m_strPassword;
	}

	/**
	 * LoginTimeout 얻음.
	 * @return int
	 */
	public int getLoginTimeout()
	{
		return m_nLoginTimeout;
	}

	/**
	 * Method 얻음.
	 * @return int
	 */
	public int getMethod()
	{
		return m_nMethod;
	}

	/**
	 * Database 종류 얻음.
	 * @return int
	 */
	public int getDBType()
	{
		return m_nDBType;
	}

	/**
	 * WAS 종류 얻음.
	 * @return int
	 */
	public int getWASType()
	{
		return m_nWASType;
	}
}
