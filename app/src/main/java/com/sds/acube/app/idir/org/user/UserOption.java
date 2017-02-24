package com.sds.acube.app.idir.org.user;

/**
 * UserOption.java
 * 2002-11-01
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserOption 
{
	private String m_strUserUID = "";
	private String m_strOptionID = "";
	private int  	m_nIntegerValue = 0;
	private String	m_strStringValue = "";
	private String m_strMStringValue = "";
	private String m_strOptionType = "";
	private int	m_nValueType = 0;
	private String m_strDescription = "";
	
	/**
	 * Option Integer Value 설정.
	 * @param nIntegerValue The m_nIntegerValue to set
	 */
	public void setIntegerValue(int nIntegerValue) 
	{
		m_nIntegerValue = nIntegerValue;
	}

	/**
	 * Option Value Type 설정.
	 * @param nValueType The m_nValueType to set
	 */
	public void setValueType(int nValueType) 
	{
		m_nValueType = nValueType;
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
	 * Option Multi Value String 설정.
	 * @param strMStringValue The m_strMStringValue to set
	 */
	public void setMStringValue(String strMStringValue) 
	{
		m_strMStringValue = strMStringValue;
	}

	/**
	 * Option ID 설정.
	 * @param strOptionID The m_strOptionID to set
	 */
	public void setOptionID(String strOptionID) 
	{
		m_strOptionID = strOptionID;
	}

	/**
	 * Option Type 설정.
	 * @param strOptionType The m_strOptionType to set
	 */
	public void setOptionType(String strOptionType) 
	{
		m_strOptionType = strOptionType;
	}

	/**
	 * String Value설정.
	 * @param strStringValue The m_strStringValue to set
	 */
	public void setStringValue(String strStringValue) 
	{
		m_strStringValue = strStringValue;
	}

	/**
	 * Sets the m_strUserUID.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	 
	/**
	 * Integer Value 반환.
	 * @return int
	 */
	public int getIntegerValue() 
	{
		return m_nIntegerValue;
	}

	/**
	 * Option Value Type 반환.
	 * @return int
	 */
	public int getValueType() 
	{
		return m_nValueType;
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
	 * Multi Value String 반환.
	 * @return String
	 */
	public String getMStringValue() 
	{
		return m_strMStringValue;
	}

	/**
	 * Option ID 반환.
	 * @return String
	 */
	public String getOptionID() 
	{
		return m_strOptionID;
	}

	/**
	 * Option Type 반환.
	 * @return String
	 */
	public String getOptionType() 
	{
		return m_strOptionType;
	}

	/**
	 * String Value 반환.
	 * @return String
	 */
	public String getStringValue() 
	{
		return m_strStringValue;
	}

	/**
	 * User UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
}
