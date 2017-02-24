package com.sds.acube.app.idir.org.line;

/**
 * ApprovalLineTableMap.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class ApprovalLineTableMap extends TableMap
{
	// Approval Line Table Map
	public static final int LINE_ID = 0;
	public static final int LINE_NAME = 1;
	public static final int LINE_CATEGORY = 2;
	public static final int IS_FAVORITE = 3;
	public static final int ORG_ID = 4;
	public static final int USER_ID = 5;
	public static final int WHEN_CREATED = 6;
	public static final int DESCRIPTION = 7;
	
	public static final String m_strColumn[] =
	{
		"LINE_ID",
		"LINE_NAME",
		"LINE_CATEGORY",
		"IS_FAVORITE",
		"ORG_ID",
		"USER_ID",
		"WHED_CREATED",
		"DESCRIPTION",
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		INTEGER,
		STRING,
		STRING,
		STRING,
		DATE,
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
