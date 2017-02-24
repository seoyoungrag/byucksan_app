package com.sds.acube.app.idir.org.line;

/**
 * ApproverTableMap.java
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

public class ApproverTableMap extends TableMap
{
	// Approver Table Map
	public static final int LINE_ID = 0;
	public static final int APPROVER_ID = 1;
	public static final int APPROVER_CLASS = 2;
	public static final int APPROVER_ROLE = 3;
	public static final int APPROVER_TYPE = 4;
	public static final int SERIAL_ORDER = 5;
	public static final int PARALLEL_ORDER = 6;
	public static final int EMPTY_REASON = 7;
	public static final int ADDITIONAL_ROLE = 8;
	public static final int ADDITIONAL_INFO = 9;
	
	public static final String m_strColumn[] =
	{
		"LINE_ID",
		"APPROVER_ID",
		"APPROVER_CLASS",
		"APPROVER_ROLE",
		"APPROVER_TYPE",
		"SERIAL_ORDER",
		"PARALLEL_ORDER",
		"EMPTY_REASON",
		"ADDITIONAL_ROLE",
		"ADDITIONAL_INFO"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		INTEGER,
		INTEGER,
		INTEGER,
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
