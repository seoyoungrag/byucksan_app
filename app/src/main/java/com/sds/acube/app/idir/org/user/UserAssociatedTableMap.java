package com.sds.acube.app.idir.org.user;

/**
 * UserAssociatedTableMap.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
import com.sds.acube.app.idir.org.common.*;

public class UserAssociatedTableMap extends TableMap
{
	// User Status Table Map
	public static final int USER_ID = 0;
	public static final int SUBSTITUTE_START_DATE = 1;
	public static final int SUBSTITUTE_END_DATE = 2;
	public static final int SUBSTITUTE_ID = 3;
	
	public static final String m_strColumn[] =
	{
		"USER_ID",
		"SUBSTITUTE_START_DATE",
		"SUBSTITUTE_END_DATE",
		"SUBSTITUTE_ID"
	};
	
	public static final int m_nDataType[] =
	{
		STRING, 
		DATE,
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
