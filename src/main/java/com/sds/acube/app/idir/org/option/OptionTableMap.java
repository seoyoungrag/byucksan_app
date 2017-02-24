package com.sds.acube.app.idir.org.option;

/**
 * OptionTableMap.java
 * 2002-12-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class OptionTableMap extends TableMap
{
	// Option Table Map
	public static final int OPTION_ID = 0;
	public static final int COMP_ID = 1;		// Company Option Owner ID
	public static final int USER_ID = 2;		// User Option Owner ID
	public static final int OPTION_TYPE = 3;
	public static final int VALUE_TYPE = 4;
	public static final int INTEGER_VALUE = 5;
	public static final int STRING_VALUE = 6;
	public static final int MSTRING_VALUE = 7;
	public static final int DESCRIPTION = 8;
	
	public static final String m_strColumn[] =
	{
		"OPTION_ID",
		"COMP_ID",
		"USER_ID",
		"OPTION_TYPE",
		"VALUE_TYPE",
		"INTEGER_VALUE",
		"STRING_VALUE",
		"MSTRING_VALUE",
		"DESCRIPTION"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		STRING,
		STRING,
		STRING
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
