package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgTableMap.java
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

public class OrgTableMap extends TableMap
{	
	// Organization Table Map
	public static final int ORG_NAME = 0;
	public static final int ORG_OTHER_NAME = 1;
	public static final int ORG_ABBR_NAME = 2;
	public static final int ORG_ID = 3;
	public static final int ORG_CODE = 4;
	public static final int ORG_PARENT_ID = 5;
	public static final int ORG_ORDER = 6;
	public static final int SERVERS = 7;
	public static final int ORG_TYPE = 8;
	public static final int DESCRIPTION = 9;
	public static final int ODCD_CODE = 10;
	public static final int IS_ODCD = 11;
	public static final int IS_INSTITUTION = 12;
	public static final int IS_INSPECTION = 13;
	public static final int ADDR_SYMBOL = 14;
	public static final int IS_PROXY_DOC_HANDLE_DEPT = 15;
	public static final int PROXY_DOC_HANDLE_DEPT_CODE = 16;
	public static final int CHIEF_POSITION = 17;
	public static final int FORMBOX_INFO = 18;
	public static final int RESERVED1 = 19;
	public static final int RESERVED2 = 20;
	public static final int RESERVED3 = 21;
	public static final int OUTGOING_NAME = 22;
	public static final int COMPANY_ID = 23;
	public static final int INSTITUTION_DISPLAY_NAME = 24;
	public static final int HOMEPAGE = 25;
	public static final int EMAIL = 26;
	public static final int ADDRESS = 27;
	public static final int ADDRESS_DETAIL = 28;
	public static final int ZIP_CODE = 29;
	public static final int TELEPHONE = 30;
	public static final int FAX = 31;
	public static final int IS_DELETED = 32;
	public static final int IS_PROCESS = 33;
	public static final int IS_HEAD_OFFICE = 34;
	
	public static final String m_strColumn[] =
	{
		"ORG_NAME",
		"ORG_OTHER_NAME",
		"ORG_ABBR_NAME",
		"ORG_ID",
		"ORG_CODE",
		"ORG_PARENT_ID",
		"ORG_ORDER",
		"SERVERS",
		"ORG_TYPE",
		"DESCRIPTION",
		"ODCD_CODE",
		"IS_ODCD",
		"IS_INSTITUTION",
		"IS_INSPECTION",
		"ADDR_SYMBOL",
		"IS_PROXY_DOC_HANDLE_DEPT",
		"PROXY_DOC_HANDLE_DEPT_CODE",
		"CHIEF_POSITION",
		"FORMBOX_INFO",
		"RESERVED1",
		"RESERVED2",
		"RESERVED3",
		"OUTGOING_NAME",
		"COMPANY_ID",
		"INSTITUTION_DISPLAY_NAME",
		"HOMEPAGE",
		"EMAIL",
		"ADDRESS",
		"ADDRESS_DETAIL",
		"ZIP_CODE",
		"TELEPHONE",
		"FAX",
		"IS_DELETED",
		"IS_PROCESS",
		"IS_HEAD_OFFICE"
	};

	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		STRING,
		INTEGER,
		LIST,
		INTEGER,
		STRING,
		STRING,
		BOOLEAN,
		BOOLEAN,
		BOOLEAN,
		STRING,
		BOOLEAN,
		STRING,
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
		BOOLEAN,
		BOOLEAN,
		BOOLEAN
	};

	
	/**
	 * Get Column Name
	 * @param nTableType Table Type
	 * @param nColName   Column Type
	 * @return String 	  Column 명 
	 */	
	public static String getColumnName(int nColIndex)
	{	
		return m_strColumn[nColIndex];	
	}
	
	/**
	 * Get Column Data Type
	 * @param nTableType Table Type
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
