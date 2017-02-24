package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.org.common.*;

/**
 * CodeTableMap.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class CodeTableMap extends TableMap
{
	// Code Table Map
	public static final int CODE_ID = 0;
	public static final int CODE_NAME = 1;
	public static final int CODE_TYPE = 2;
	public static final int RESERVED1 = 3;
	public static final int RESERVED2 = 4;
	public static final int DESCRIPTION = 5;
	public static final int COMP_ID = 6;
	public static final int CODE_ORDER = 7;
	
	public static final String m_strColumn[] =
	{
		"CODE_ID",
		"CODE_NAME",
		"CODE_TYPE",
		"RESERVED1",
		"RESERVED2",
		"DESCRIPTION",
		"COMP_ID",
		"CODE_ORDER"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		STRING,
		STRING,
		STRING,
		INTEGER
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
