package com.sds.acube.app.idir.org.option;

/**
 * ApprovalOptio.java
 * 2003-02-27
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.io.Serializable;

public class ApprovalOption implements Serializable 
{
	private String m_strOptionID = "";
	private String m_strOptionValue = "";
	
	/**
	 * Option ID 설정 .
	 * @param strOptionID Option ID
	 */
	public void setOptionID(String strOptionID) 
	{
		m_strOptionID = strOptionID;
	}

	/**
	 * Option Value 설정.
	 * @param strOptionValue Option Value 설정 
	 */
	public void setOptionValue(String strOptionValue) 
	{
		m_strOptionValue = strOptionValue;
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
	 * Returns the strOptionValue.
	 * @return String
	 */
	public String getOptionValue() 
	{
		return m_strOptionValue;
	}
}
