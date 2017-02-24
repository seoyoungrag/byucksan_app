package com.sds.acube.app.idir.org.option;

/**
 * Address.java
 * 2003-06-01
 * 
 * 우편번호 Object
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Address 
{
	private String m_strZipCode = "";
	private String m_strSIDO = "";
	private String m_strGUGUN = "";
	private String m_strDONG = "";
	private String m_strBUNGI = "";
	private String m_strAddress = "";
	
	/**
	 * 번지 주소 설정.
	 * @param strBUNGI 번지 주소 
	 */
	public void setBUNGI(String strBUNGI) 
	{
		m_strBUNGI = strBUNGI;
	}

	/**
	 * 동 주소 설정.
	 * @param strDONG 동 주소 
	 */
	public void setDONG(String strDONG) 
	{
		m_strDONG = strDONG;
	}

	/**
	 * 구군 주소 설정.
	 * @param strGUGUN 구군 주소
	 */
	public void setGUGUN(String strGUGUN) 
	{
		m_strGUGUN = strGUGUN;
	}

	/**
	 * 시도 주소 설정.
	 * @param strSIDO 시도 주소 
	 */
	public void setSIDO(String strSIDO) 
	{
		m_strSIDO = strSIDO;
	}

	/**
	 * 우편 번호 설정.
	 * @param strZipCode 우편번호 설정
	 */
	public void setZipCode(String strZipCode) 
	{
		m_strZipCode = strZipCode;
	}
	
	/**
	 * 번지 주소 반환.
	 * @return String
	 */
	public String getBUNGI() 
	{
		return m_strBUNGI;
	}

	/**
	 * 동 주소 반환.
	 * @return String
	 */
	public String getDONG() 
	{
		return m_strDONG;
	}

	/**
	 * 구군 주소 반환.
	 * @return String
	 */
	public String getGUGUN() 
	{
		return m_strGUGUN;
	}

	/**
	 * 시도 주소 반환.
	 * @return String
	 */
	public String getSIDO() 
	{
		return m_strSIDO;
	}

	/**
	 * 우편번호 반환.
	 * @return String
	 */
	public String getZipCode() 
	{
		return m_strZipCode;
	}
	
	/**
	 * 주소 반환.
	 * @return String
	 */
	public String getAddress()
	{
		if (m_strSIDO != null && m_strSIDO.length() > 0)
			m_strAddress += m_strSIDO + " ";
		
		if (m_strGUGUN != null && m_strGUGUN.length() > 0)
			m_strAddress += m_strGUGUN + " ";
			
		if (m_strDONG != null && m_strDONG.length() > 0)
			m_strAddress += m_strDONG + " ";
			
		if (m_strBUNGI != null && m_strBUNGI.length() > 0)
			m_strAddress += m_strBUNGI + " ";

		return m_strAddress;
	}
}
