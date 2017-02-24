package com.sds.acube.app.idir.org.relation;

/**
 * ApprOrgEnv.java
 * 2002-12-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApprOrgEnv 
{
	private String m_strClassName = "";
	private String m_strConnectionURL = "";
	private String m_strDSName = "";
	private int	m_nMethod = 0;
	
	/**
	 * DS Name 반환
	 * @return String
	 */
	public String getDSName()
	{
		return m_strDSName;
	}
	
	/**
	 * Connection URL 반환.
	 * @return String
	 */
	public String getConnectionURL() 
	{
		return m_strConnectionURL;
	}

	/**
	 * Class Name 반환
	 * @return String
	 */
	public String getClassName() 
	{
		return m_strClassName;
	}
	
	/**
	 * Connection 유형 반환
	 * @return ing
	 */
	public int getMethod()
	{
		return m_nMethod;
	}
	
	/**
	 * DS Name 설정
	 * @param strDSName DataSource Name
	 */
	public void setDSName(String strDSName)
	{
		m_strDSName = strDSName;
	}

	/**
	 * Connection URL 설정.
	 * @param strConnectionURL Connection URL
	 */
	public void setConnectionURL(String strConnectionURL) 
	{
		m_strConnectionURL = strConnectionURL;
	}

	/**
	 * Class Name 설정
	 * @param strClassName Class Name
	 */
	public void setClassName(String strClassName) 
	{
		m_strClassName = strClassName;
	}
	
	/**
	 * Connection 유형 설정
	 * @param nMethod Connection  유형
	 */
	public void setMethod(int nMethod)
	{
		m_nMethod = nMethod;
	}

}
