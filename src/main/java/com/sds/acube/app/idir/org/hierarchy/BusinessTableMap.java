package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.org.common.*;

/**
 * BusinessTableMap.java
 * 2003-10-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BusinessTableMap extends TableMap
{
	// Business Table Map
	public static final int APPR_BIZ_ID = 0;
	public static final int APPR_BIZ_NAME = 1;
	public static final int APPR_BIZ_POSITION = 2;
	public static final int APPR_BIZ_POSITION_NAME = 3;
	public static final int CREATOR_NAME = 4;
	public static final int CREATOR_ID = 5;
	public static final int CREATOR_DEPT_NAME = 6;
	public static final int CREATOR_DEPT_ID = 7;
	public static final int CREATION_DATE = 8;
	public static final int APPR_BIZ_ORDER = 9;
	public static final int DESCRIPTION = 10;
	public static final int CATEGORY_ID = 11;
	
	public static final String m_strColumn[] =
	{
		"APPR_BIZ_ID",
		"APPR_BIZ_NAME",
		"APPR_BIZ_POSITION",
		"APPR_BIZ_POSITION_NAME",
		"CREATOR_NAME",
		"CREATOR_ID",
		"CREATOR_DEPT_NAME",
		"CREATOR_DEPT_ID",
		"CREATION_DATE",
		"APPR_BIZ_ORDER",
		"DESCRIPTION",
		"CATEGORY_ID"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		DATE,
		INTEGER,
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
