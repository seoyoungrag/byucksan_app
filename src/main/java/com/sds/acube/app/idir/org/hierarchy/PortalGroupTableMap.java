package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.org.common.*;

/**
 * PortalGroupTableMap.java
 * 2005-09-26
 * 
 * 포탈 그룹에 대한 Table Map
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PortalGroupTableMap extends TableMap
{
	public static final int PORTAL_ID = 0;
	public static final int PARENT_PORTAL_ID = 1;
	public static final int PORTAL_TYPE = 2;
	public static final int ORG_ID = 3;
	public static final int PARENT_ORG_ID = 4;
	public static final int ORG_TYPE = 5;
	public static final int DESCRIPTION = 6;
	
	public static final String m_strColumn[] =
	{
		"PORTAL_ID",
   		"PARENT_PORTAL_ID",
   		"PORTAL_TYPE",
   		"ORG_ID",
   		"PARENT_ORG_ID",
   		"ORG_TYPE",
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
