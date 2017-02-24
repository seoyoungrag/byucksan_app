package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.org.common.*;

/**
 * LedgerCategoryTableMap.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LedgerCategoryTableMap extends TableMap
{
	// LedgerCategory Table Map
	public static final int CATEGORY_ID = 0;
	public static final int CATEGORY_NAME = 1;
	public static final int COMPANY_ID = 2;
	public static final int FOLDER_TYPE = 3;
	public static final int BUSINESS_ID = 4;
	public static final int CATEGORY_ORDER = 5;
	public static final int DESCRIPTION = 6;
	
	public static final String m_strColumn[] =
	{
		"CATEGORY_ID",
		"CATEGORY_NAME",
		"COMPANY_ID",
		"FOLDER_TYPE",
		"BUSINESS_ID",
		"CATEGORY_ORDER",
		"DESCRIPTION"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
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
