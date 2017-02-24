package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgRoleTableMap.java
 * 2011-04-29
 * 
 * 
 * 
 * @author geena
 * @version 1.0.0.0
 *
 * Copyright 2011 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class OrgRoleTableMap extends TableMap
{
	// User Relation Table Map
	public static final int ROLE_ID = 0;
	public static final int ROLE_NAME = 1;
	public static final int ROLE_OTHER_NAME = 2;
	public static final int ROLE_ORDER = 3;
	public static final int DESCRIPTION = 4;
	public static final int ORG_ID = 5;

	public static final String m_strColumn[] =
	{
		"ROLE_ID",
		"ROLE_NAME",
		"ROLE_OTHER_NAME",
		"ROLE_ORDER",
		"DESCRIPTION",
		"ORG_ID"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
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
