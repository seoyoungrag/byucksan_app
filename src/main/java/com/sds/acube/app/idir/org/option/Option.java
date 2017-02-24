package com.sds.acube.app.idir.org.option;

/**
 * Option.java
 * 2002-12-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Option 
{
	private String m_strOptionID = "";
	private String m_strOwnerID = "";
	private String m_strOptionType = "";
	private int 	m_nValueType = 0;
	private int  	m_nIntegerValue = 0;
	private String	m_strStringValue = "";
	private String m_strMStringValue = "";
	private String m_strDescription = "";
	
	/**
	 * Option Integer Value 설정.
	 * @param nIntegerValue Option Integer Value
	 */
	public void setIntegerValue(int nIntegerValue) 
	{
		m_nIntegerValue = nIntegerValue;
	}

	/**
	 * Option Value Type 설정.
	 * @param nValueType Option Value Type (0:Integer / 1:String / 2:Mstring)
	 */
	public void setValueType(int nValueType) 
	{
		m_nValueType = nValueType;
	}

	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * Option Multi Value String 설정.
	 * @param strMStringValue Multi Value String
	 */
	public void setMStringValue(String strMStringValue) 
	{
		m_strMStringValue = strMStringValue;
	}

	/**
	 * Option ID 설정.
	 * @param strOptionID Option ID
	 */
	public void setOptionID(String strOptionID) 
	{
		m_strOptionID = strOptionID;
	}

	/**
	 * Option Type 설정.
	 * @param strOptionType Option Type
	 */
	public void setOptionType(String strOptionType) 
	{
		m_strOptionType = strOptionType;
	}

	/**
	 * Option 소유자 ID (COMP_ID / USER_ID) 설정.
	 * @param m_strOwnerID The m_strOwnerID to set
	 */
	public void setOwnerID(String strOwnerID) 
	{
		m_strOwnerID = strOwnerID;
	}

	/**
	 * Option String Value 설정.
	 * @param strStringValue Option String Value
	 */
	public void setStringValue(String strStringValue) 
	{
		m_strStringValue = strStringValue;
	}
	
	/**
	 * Option Integer Value 반환.
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
	 * Option Multi Value String 반환.
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
	 * Option 소유자 ID 반환.
	 * @return String
	 */
	public String getOwnerID() 
	{
		return m_strOwnerID;
	}

	/**
	 * Option String Value 반환.
	 * @return String
	 */
	public String getStringValue() 
	{
		return m_strStringValue;
	}
}
