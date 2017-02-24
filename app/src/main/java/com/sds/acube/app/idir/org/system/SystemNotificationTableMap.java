package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.org.common.*;

/**
 * SystemNotificationTableMap.java
 * 2003-08-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemNotificationTableMap extends TableMap
{
	// System Notification Table Map
	public static final int SYSTEM_ID = 0;
	public static final int SERVER_NAME = 1;
	public static final int SERVER_IP = 2;
	public static final int PORT_NO = 3;
	public static final int STORE_TYPE = 4;
	public static final int STORE_INFO = 5;
	public static final int OTHER_INFO = 6;
	public static final int WRITE_LOG = 7;
	public static final int NOTI_POINT = 8;
	public static final int NOTI_TITLE = 9;
	public static final int DESCRIPTION = 10;
	public static final int NOTI_TYPE = 11;

	
	public static final String m_strColumn[] =
	{
		"SYSTEM_ID",
		"SERVER_NAME",
		"SERVER_IP",
		"PORT_NO",
		"STORE_TYPE",
		"STORE_INFO",
		"OTHER_INFO",
		"WRITE_LOG",
		"NOTI_POINT",
		"NOTI_TITLE",
		"DESCRIPTION",
		"NOTI_TYPE"
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
		BOOLEAN,
		STRING,
		STRING,
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
