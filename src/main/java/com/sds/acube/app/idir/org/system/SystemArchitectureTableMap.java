package com.sds.acube.app.idir.org.system;

/**
 * SystemArchitectureTableMap.java
 * 2002-11-26
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class SystemArchitectureTableMap extends TableMap
{
	// System Basic Table Map
	public static final int SYSTEM_ID = 0;
	public static final int INPUT_TYPE = 1;
	public static final int INPUT_METHOD = 2;
	public static final int INPUT_FORMAT = 3;
	public static final int IS_SSO = 4;
	public static final int INPUT_URL = 5;
	public static final int OUTPUT_TYPE = 6;
	public static final int OUTPUT_METHOD = 7;
	public static final int OUTPUT_FORMAT = 8;
	public static final int OUTPUT_URL = 9;
	public static final int APPROVAL_EVENT = 10;
	public static final int AUX_INFO = 11;

	public static final String m_strColumn[] =
	{
		"SYSTEM_ID",
		"INPUT_TYPE",
		"INPUT_METHOD",
		"INPUT_FORMAT",
		"IS_SSO",
		"INPUT_URL",
		"OUTPUT_TYPE",
		"OUTPUT_METHOD",
		"OUTPUT_FORMAT",
		"OUTPUT_URL",
		"APPROVAL_EVENT",
		"AUX_INFO"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		INTEGER,
		INTEGER,
		INTEGER,
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		INTEGER,
		STRING,
		INTEGER,
		CLOB
	};
	
	/**
	 * Get Column Name
	 * @param nColName   Column Type
	 * @return String 	  Column 명 
	 */	
	public static String getColumnName(int nColIndex)
	{
		return m_strColumn[nColIndex];
	}
	
	/**
	 * Get Column Data Type
	 * @param nColName   Column Type
	 * @return int 	  Column Data Type 
	 */	
	public static int getDataType(int nColIndex)
	{
		return m_nDataType[nColIndex];
	}	
	
	/**
	 * Data Type 반환
	 * @param strColumnName 	Table Column명 
	 * @return int
	 */
	public static int getDataType(String strColumnName)
	{
		String 	strUColumnName = "";
		int 	nDataType = -1;
		int 	nIndex = -1;
		
		strUColumnName = strColumnName.toUpperCase();
		
		for (int i = 0 ; i < m_strColumn.length ; i++)
		{
			if (m_strColumn[i].compareTo(strUColumnName) == 0)
			{
				nIndex = i;
			}		
		}
		
		if (nIndex >= 0)
		{
			nDataType = m_nDataType[nIndex];
		}
		
		return nDataType;
	}	
}
