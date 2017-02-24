package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.org.common.*;

/**
 * UserDetailViewTableMap.java
 * 2003-04-30
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserDetailViewTableMap extends TableMap
{
	// User View Detail Table Map
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
	public static final int GRADE_OTHER_NAME = 20;
	public static final int GRADE_ABBR_NAME = 21;
	public static final int GRADE_ORDER = 22;
	public static final int POSITION_CODE = 23;
	public static final int POSITION_NAME = 24;
	public static final int POSITION_OTHER_NAME = 25;
	public static final int POSITION_ABBR_NAME = 26;
	public static final int POSITION_ORDER = 27;
	public static final int TITLE_CODE = 28;
	public static final int TITLE_NAME = 29;
	public static final int TITLE_OTHER_NAME = 30;
	public static final int TITLE_ORDER = 31;
	public static final int DUTY_CODE = 32;
	public static final int DUTY_NAME = 33;
	public static final int DUTY_OTHER_NAME = 34;
	public static final int DUTY_ORDER = 35;
	public static final int USER_ORDER = 36;
	public static final int SECURITY_LEVEL = 37;
	public static final int ROLE_CODE = 38;
	public static final int RESIDENT_NO = 39;
	public static final int EMPLOYEE_ID = 40;
	public static final int SYSMAIL = 41;
	public static final int SERVERS = 42;
	public static final int IS_CONCURRENT = 43;
	public static final int IS_PROXY = 44;
	public static final int IS_DELEGATE = 45;
	public static final int IS_EXISTENCE = 46;
	public static final int USER_RID = 47;
	public static final int IS_DELETED = 48;
	public static final int DESCRIPTION = 49;
	public static final int RESERVED1 = 50;
	public static final int RESERVED2 = 51;
	public static final int RESERVED3 = 52;
	public static final int EMAIL = 53;
	public static final int DUTY = 54;
	public static final int PCONLINE_ID = 55;
	public static final int HOMEPAGE = 56;
	public static final int OFFICE_TEL = 57;
	public static final int OFFICE_TEL2 = 58;
	public static final int OFFICE_ADDR = 59;
	public static final int OFFICE_DETAIL_ADDR = 60;
	public static final int OFFICE_ZIPCODE = 61;
	public static final int OFFICE_FAX = 62;
	public static final int MOBILE = 63;
	public static final int MOBILE2 = 64;
	public static final int PAGER = 65;
	public static final int HOME_ADDR = 66;
	public static final int HOME_DETAIL_ADDR = 67;
	public static final int HOME_ZIPCODE = 68;
	public static final int HOME_TEL = 69;
	public static final int HOME_TEL2 = 70;
	public static final int HOME_FAX = 71;
	public static final int USER_STATUS = 72;
	public static final int LAST_LOGON = 73;
	public static final int LAST_LOGOUT = 74;
	public static final int LAST_LOGOUT_IP = 75;
	public static final int WHEN_CHANGED_PASSWORD = 76;
	public static final int DISPLAY_ORDER = 77;
	public static final int DEFAULT_USER = 78;
	public static final int CERTIFICATION_ID = 79;
	public static final int OPTIONAL_GTP_NAME = 80;
	   	  
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
		"GRADE_OTHER_NAME",
		"GRADE_ABBR_NAME",
		"GRADE_ORDER",
		"POSITION_CODE",
		"POSITION_NAME",
		"POSITION_OTHER_NAME",
		"POSITION_ABBR_NAME",
		"POSITION_ORDER",
		"TITLE_CODE",
		"TITLE_NAME",
		"TITLE_OTHER_NAME",
		"TITLE_ORDER",
		"DUTY_CODE",
		"DUTY_NAME",
		"DUTY_OTHER_NAME",
		"DUTY_ORDER",
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
		"EMAIL",
		"DUTY",
		"PCONLINE_ID",
		"HOMEPAGE",
		"OFFICE_TEL",
		"OFFICE_TEL2",
		"OFFICE_ADDR",
		"OFFICE_DETAIL_ADDR",
		"OFFICE_ZIPCODE",
		"OFFICE_FAX",
		"MOBILE",
		"MOBILE2",
		"PAGER",
		"HOME_ADDR",
		"HOME_DETAIL_ADDR",
		"HOME_ZIPCODE",
		"HOME_TEL",
		"HOME_TEL2",
		"HOME_FAX",
		"USER_STATUS",
		"LAST_LOGON",
		"LAST_LOGOUT",
		"LAST_LOGOUT_IP",
		"WHEN_CHANGED_PASSWORD",
		"DISPLAY_ORDER",
		"DEFAULT_USER",
		"CERTIFICATION_ID",
		"OPTIONAL_GTP_NAME"
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
		INTEGER,
		INTEGER,
		STRING,
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
		DATE,
		DATE,
		STRING,
		DATE,
		INTEGER,
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
