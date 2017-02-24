package com.sds.acube.app.idir.org.user;

/**
 * UserBasicTableMap.java
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

public class UserBasicTableMap extends TableMap
{
	// User Basic Table Map
	public static final int USER_ID = 0;
	public static final int USER_NAME = 1;
	public static final int USER_OTHER_NAME = 2;
	public static final int USER_UID = 3;
	public static final int GROUP_ID = 4;
	public static final int GROUP_NAME = 5;
	public static final int COMP_ID = 6;
	public static final int COMP_NAME = 7;
	public static final int DEPT_ID = 8;
	public static final int DEPT_NAME = 9;
	public static final int PART_ID = 10;
	public static final int PART_NAME = 11;
	public static final int ORG_DISPLAY_NAME = 12;
	public static final int GRADE_CODE = 13;
	public static final int TITLE_CODE = 14;
	public static final int POSITION_CODE = 15;
	public static final int USER_ORDER = 16;
	public static final int SECURITY_LEVEL = 17;
	public static final int ROLE_CODE = 18;
	public static final int RESIDENT_NO = 19;
	public static final int EMPLOYEE_ID = 20;
	public static final int SYSMAIL = 21;
	public static final int SERVERS = 22;
	public static final int IS_CONCURRENT = 23;
	public static final int IS_PROXY = 24;
	public static final int IS_DELEGATE = 25;
	public static final int DESCRIPTION = 26;
	public static final int IS_EXISTENCE = 27;
	public static final int USER_RID = 28;
	public static final int IS_DELETED = 29;
	public static final int RESERVED1 = 30;
	public static final int RESERVED2 = 31;
	public static final int RESERVED3 = 32;
	public static final int OPTIONAL_GTP_NAME = 33;
	public static final int DISPLAY_ORDER = 34;
	public static final int DEFAULT_USER = 35;
	public static final int CERTIFICATION_ID = 36;
	public static final int DUTY_CODE = 37;
	public static final int GROUP_OTHER_NAME = 38;
	public static final int COMP_OTHER_NAME = 39; 
	public static final int DEPT_OTHER_NAME = 40; 
	public static final int PART_OTHER_NAME = 41; 
	public static final int ORG_DISPLAY_OTHER_NAME = 42;
	
	public static final String m_strColumn[] =
	{
		"USER_ID",
		"USER_NAME",
		"USER_OTHER_NAME",
		"USER_UID",
		"GROUP_ID",
		"GROUP_NAME",
		"COMP_ID",
		"COMP_NAME",
		"DEPT_ID",
		"DEPT_NAME",
		"PART_ID",
		"PART_NAME",
		"ORG_DISPLAY_NAME",
		"GRADE_CODE",
		"TITLE_CODE",
		"POSITION_CODE",
		"USER_ORDER",
		"SECURITY_LEVEL",
		"ROLE_CODE",
		"RESIDENT_NO",
		"EMPLOYEE_ID",
		"SYSMAIL",
		"SERVERS",
		"IS_CONCURRENT",
		"IS_PROXY",
		"IS_DELEGATE",
		"DESCRIPTION",
		"IS_EXISTENCE",
		"USER_RID",
		"IS_DELETED",
		"RESERVED1",
		"RESERVED2",
		"RESERVED3",
		"OPTIONAL_GTP_NAME",
		"DISPLAY_ORDER",
		"DEFAULT_USER",
		"CERTIFICATION_ID",
		"DUTY_CODE", 
		"GROUP_OTHER_NAME", 
		"COMP_OTHER_NAME", 
		"DEPT_OTHER_NAME", 
		"PART_OTHER_NAME", 
		"ORG_DISPLAY_OTHER_NAME"
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
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		LIST,
		STRING,
		STRING,
		STRING,
		LIST,
		INTEGER,
		INTEGER,
		INTEGER,
		STRING,
		BOOLEAN,
		STRING,
		BOOLEAN,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		STRING,
		STRING, 
		STRING, 
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
