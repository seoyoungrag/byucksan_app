package com.sds.acube.app.idir.org.option;

/**
 * Code.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Code 
{
	private String m_strCodeID = "";
	private String m_strCodeName = "";
	private int 	m_nCodeType = 0;
	private int 	m_nReserved1 = 0;
	private String m_strReserved2 = "";
	private String m_strDescription = "";
	private String m_strCompID = "";
	private int m_nCodeOrder = 0;
	
	/**
	 * Code Type 설정.
	 * @param nCodeType The m_nCodeType to set
	 */
	public void setCodeType(int nCodeType) 
	{
		m_nCodeType = nCodeType;
	}

	/**
	 * Reserved1 설정.
	 * @param nReserved1 The m_nReserved1 to set
	 */
	public void setReserved1(int nReserved1) 
	{
		m_nReserved1 = nReserved1;
	}

	/**
	 * Code ID 설정.
	 * @param strCodeID The m_strCodeID to set
	 */
	public void setCodeID(String strCodeID) 
	{
		m_strCodeID = strCodeID;
	}

	/**
	 * Code Name 설정.
	 * @param strCodeName The m_strCodeName to set
	 */
	public void setCodeName(String strCodeName) 
	{
		m_strCodeName = strCodeName;
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
	 * Reserved2 설정.
	 * @param strReserved2 The m_strReserved2 to set
	 */
	public void setReserved2(String strReserved2) 
	{
		m_strReserved2 = strReserved2;
	}
		
	/**
	 * Code Order 설정.
	 * @param nCodeOrder The m_nCodeOrder to set
	 */
	public void setCodeOrder(int nCodeOrder) 
	{
		m_nCodeOrder = nCodeOrder;
	}

	/**
	 * Comp ID 설정.
	 * @param strCompID The m_strCompID to set
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * Code Type 반환.
	 * @return int
	 */
	public int getCodeType() 
	{
		return m_nCodeType;
	}

	/**
	 * Reserved1 반환.
	 * @return int
	 */
	public int getReserved1() 
	{
		return m_nReserved1;
	}

	/**
	 * Code ID 반환.
	 * @return String
	 */
	public String getCodeID() 
	{
		return m_strCodeID;
	}

	/**
	 * Code Name 반환.
	 * @return String
	 */
	public String getCodeName() 
	{
		return m_strCodeName;
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
	 * Reserved2 반환.
	 * @return String
	 */
	public String getReserved2() 
	{
		return m_strReserved2;
	}

	/**
	 * COMP_ID 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
	}

	/**
	 * CODE_ORDER 반환.
	 * @return integer
	 */
	public int getCodeOrder() 
	{
		return m_nCodeOrder;
	}
}
