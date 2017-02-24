package com.sds.acube.app.idir.org.system;

/**
 * LegacyKey.java
 * 2002-11-26
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LegacyKey 
{
	private int 	m_nLegacyKeyType = 0;
	private String m_strLegacyKey = "";
	
	/**
	 * Legacy Key Type 설정.
	 * @param nLegacyKeyType Legacy Key Type
	 */
	public void setLegacyKeyType(int nLegacyKeyType) 
	{
		m_nLegacyKeyType = nLegacyKeyType;
	}

	/**
	 * Legacy Key 설정.
	 * @param strLegacyKey Legacy Key
	 */
	public void setLegacyKey(String strLegacyKey) 
	{
		m_strLegacyKey = strLegacyKey;
	}
	
	
	/**
	 * Legacy Key Type 반환.
	 * @return int
	 */
	public int getLegacyKeyType() 
	{
		return m_nLegacyKeyType;
	}

	/**
	 * Legacy Key 반환.
	 * @return String
	 */
	public String getLegacyKey() 
	{
		return m_strLegacyKey;
	}
}
