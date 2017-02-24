package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.org.common.*;

/**
 * AddressTableMap.java
 * 2003-06-01
 * 
 * TCN_ZIPCODE Table Map
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class AddressTableMap extends TableMap
{
	// Address Table Map
	public static final int ZIPCODE = 0;
	public static final int SIDO = 1;
	public static final int GUGUN = 2;
	public static final int DONG = 3;
	public static final int BUNJI = 4;
	public static final int SEQ = 5;
	
	public static final String m_strColumn[] =
	{
		"ZIPCODE",
		"SIDO",
		"GUGUN",
		"DONG",
		"BUNJI",
		"SEQ"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
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
