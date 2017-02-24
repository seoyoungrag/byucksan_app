package com.sds.acube.app.idir.org.user;

/**
 * UserViewTableMap.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class UserViewTableMap extends TableMap
{	
	// VIEW Table Map
	public static final int USER_ID = 0;
	public static final int USER_NAME = 1;
	public static final int USER_OTHER_NAME = 2;
	public static final int USER_UID = 3;
	public static final int GROUP_ID = 4;
	public static final int GROUP_NAME = 5;
	public static final int GROUP_OTHER_NAME = 6;
	public static final int COMP_ID = 7;
	public static final int COMP_NAME = 8;
	public static final int COMP_OTHER_NAME = 9;
	public static final int DEPT_ID = 10;
	public static final int DEPT_NAME = 11;
	public static final int DEPT_OTHER_NAME = 12;
	public static final int PART_ID = 13;
	public static final int PART_NAME = 14;
	public static final int PART_OTHER_NAME = 15;
	public static final int ORG_DISPLAY_NAME = 16;
	public static final int ORG_DISPLAY_OTHER_NAME = 17;
	public static final int GRADE_CODE = 18;
	public static final int GRADE_NAME = 19;
	public static final int GRADE_ABBR_NAME = 20;
	public static final int GRADE_OTHER_NAME = 21;
	public static final int GRADE_ORDER = 22;
	public static final int POSITION_CODE = 23;
	public static final int POSITION_NAME = 24;
	public static final int POSITION_ABBR_NAME = 25;
	public static final int POSITION_OTHER_NAME = 26;
	public static final int POSITION_ORDER = 27;
	public static final int TITLE_CODE = 28;
	public static final int TITLE_NAME = 29;
	public static final int TITLE_OTHER_NAME = 30;
	public static final int TITLE_ORDER = 31;
	public static final int DUTY_CODE = 32;
	public static final int DUTY_NAME = 33;
	public static final int DUTY_OTHER_NAME = 34;
	public static final int DUTY_ORDER = 35;
	public static final int OPTIONAL_GTP_NAME = 36;
	public static final int USER_ORDER = 37;
	public static final int SECURITY_LEVEL = 38;
	public static final int ROLE_CODE = 39;
	public static final int RESIDENT_NO = 40;
	public static final int EMPLOYEE_ID = 41;
	public static final int SYSMAIL = 42;
	public static final int SERVERS = 43;
	public static final int IS_CONCURRENT = 44;
	public static final int IS_PROXY = 45;
	public static final int IS_DELEGATE = 46;
	public static final int IS_EXISTENCE = 47;
	public static final int USER_RID = 48;
	public static final int IS_DELETED = 49;
	public static final int DESCRIPTION = 50;
	public static final int RESERVED1 = 51;
	public static final int RESERVED2 = 52;
	public static final int RESERVED3 = 53;
	public static final int DISPLAY_ORDER = 54;
	public static final int DEFAULT_USER = 55;
	public static final int CERTIFICATION_ID = 56;
    
	public static final String m_strColumn[] =
	{
		"USER_ID",
		"USER_NAME",
		"USER_OTHER_NAME",
		"USER_UID",
		"GROUP_ID",
		"GROUP_NAME",
		"GROUP_OTHER_NAME",
		"COMP_ID",
		"COMP_NAME",
		"COMP_OTHER_NAME",
		"DEPT_ID",
		"DEPT_NAME",
		"DEPT_OTHER_NAME",
		"PART_ID",
		"PART_NAME",
		"PART_OTHER_NAME",
		"ORG_DISPLAY_NAME",
		"ORG_DISPLAY_OTHER_NAME",
		"GRADE_CODE",
		"GRADE_NAME",
		"GRADE_ABBR_NAME",
		"GRADE_OTHER_NAME",
		"GRADE_ORDER",
		"POSITION_CODE",
		"POSITION_NAME",
		"POSITION_ABBR_NAME",
		"POSITION_OTHER_NAME",
		"POSITION_ORDER",
		"TITLE_CODE",
		"TITLE_NAME",
		"TITLE_OTHER_NAME",
		"TITLE_ORDER",
		"DUTY_CODE",
		"DUTY_NAME",
		"DUTY_OTHER_NAME",
		"DUTY_ORDER",
		"OPTIONAL_GTP_NAME",
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
		"IS_EXISTENCE",
		"USER_RID",
		"IS_DELETED",
		"DESCRIPTION",
		"RESERVED1",
		"RESERVED2",
		"RESERVED3",
		"DISPLAY_ORDER",
		"DEFAULT_USER",
		"CERTIFICATION_ID"
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
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		STRING,
		STRING,
		STRING,
		INTEGER,
		STRING,
		STRING,
		STRING,
		INTEGER,
		STRING,
		INTEGER,
		INTEGER,
		LIST,
		STRING,
		STRING,
		STRING,
		STRING,
		BOOLEAN,
		BOOLEAN,
		BOOLEAN,
		BOOLEAN,
		STRING,
		BOOLEAN,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		BOOLEAN,
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
