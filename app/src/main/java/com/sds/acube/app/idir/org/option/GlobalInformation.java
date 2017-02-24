package com.sds.acube.app.idir.org.option;

/**
 * GlobalInformation.java
 * 2003-07-24
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class GlobalInformation 
{
	private String m_strInfoID = "";
	private String m_strInfoName = "";
	private String m_strInfoValue = "";
	private String m_strDescription = "";
	
	/**
	 * Description 설정.
	 * @param strDescription Global Information Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * Information ID 설정.
	 * @param strInfoID Information ID
	 */
	public void setInfoID(String strInfoID) 
	{
		m_strInfoID = strInfoID;
	}

	/**
	 * Information Name 설정.
	 * @param strInfoName Information Name
	 */
	public void setInfoName(String strInfoName) 
	{
		m_strInfoName = strInfoName;
	}

	/**
	 * Information Value 설정.
	 * @param strInfoValue Information Value
	 */
	public void setInfoValue(String strInfoValue) 
	{
		m_strInfoValue = strInfoValue;
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
	 * Information ID 반환.
	 * @return String
	 */
	public String getInfoID() 
	{
		return m_strInfoID;
	}

	/**
	 * Information Name 반환.
	 * @return String
	 */
	public String getInfoName() 
	{
		return m_strInfoName;
	}

	/**
	 * Information Value 반환.
	 * @return String
	 */
	public String getInfoValue()
	{
		return m_strInfoValue;
	}
}
