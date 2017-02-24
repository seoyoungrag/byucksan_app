package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.org.common.*;

/**
 * SystemServerTableMap.java
 * 2002-12-10
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemServerTableMap extends TableMap
{
	// System Server Information Table Map
	public static final int SYSTEM_ID = 0;
	public static final int SERVER_NAME = 1;
	public static final int SERVER_IP = 2;
	public static final int IS_HOME_SERVER = 3;
	public static final int DB_INFO = 4;
	public static final int DB_PORT = 5;
	public static final int DB_LOGIN_ID = 6;
	public static final int DB_LOGIN_PWD = 7;
	public static final int DB_TYPE = 8;
	
	public static final String m_strColumn[] =
	{
		"SYSTEM_ID",
		"SERVER_NAME",
		"SERVER_IP",
		"IS_HOME_SERVER",
		"DB_INFO",
		"DB_PORT",
		"DB_LOGIN_ID",
		"DB_LOGIN_PWD",
		"DB_TYPE"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		BOOLEAN,
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
