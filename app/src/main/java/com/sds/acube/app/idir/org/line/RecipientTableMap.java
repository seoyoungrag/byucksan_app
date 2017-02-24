package com.sds.acube.app.idir.org.line;

/**
 * RecipientTableMap.java
 * 2002-10-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class RecipientTableMap extends TableMap 
{  
	// Recipient Table Map
	public static final int RECIP_GROUP_ID = 0;
	public static final int ORG_ID = 1;
	public static final int ORG_NAME = 2;
	public static final int ADDR_SYMBOL = 3;
	public static final int CHIEF_POSITION = 4;
	public static final int REF_ORG_ID = 5;
	public static final int REF_ORG_NAME = 6;
	public static final int REF_ADDR_SYMBOL = 7;
	public static final int REF_CHIEF_POSITION = 8;
	public static final int ENFORCE_BOUND = 9;
	public static final int ACTUAL_ORG_ID = 10;
	public static final int RECIP_TYPE = 11;
	public static final int RECIP_ORDER = 12;
	
	public static final String m_strColumn[] =
	{
		"RECIP_GROUP_ID",
		"ORG_ID",
		"ORG_NAME",
		"ADDR_SYMBOL",   
		"CHIEF_POSITION",
		"REF_ORG_ID",
		"REF_ORG_NAME",  
		"REF_ADDR_SYMBOL",
		"REF_CHIEF_POSITION",
		"ENFORCE_BOUND",
		"ACTUAL_ORG_ID",
		"RECIP_TYPE",
		"RECIP_ORDER"
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
		STRING,
		STRING,
		STRING,
		INTEGER,
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
