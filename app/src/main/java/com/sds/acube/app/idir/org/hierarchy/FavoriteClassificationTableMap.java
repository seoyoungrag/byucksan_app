package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.org.common.*;

/**
 * FavoriteClassificationTableMap.java
 * 2002-10-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FavoriteClassificationTableMap extends TableMap
{
	// Favorite Classification Table Map
	public static final int ORG_ID = 0;
	public static final int CLASSIFICATION_ID = 1;
	public static final int HAS_SUB_CLASSIFICATION = 2;
	public static final int DISPLAY_ORDER = 3;
	
	public static final String m_strColumn[] =
	{
		"ORG_ID",
		"CLASSIFICATION_ID",
		"HAS_SUB_CLASSIFICATION",
		"DISPLAY_ORDER"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		BOOLEAN,
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
