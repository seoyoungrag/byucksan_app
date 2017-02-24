package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgImageTableMap.java
 * 2002-10-17
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class OrgImageTableMap extends TableMap
{
	// Organization Image Table Map
	public static final int IMAGE_ID = 0;
	public static final int IMAGE_NAME = 1;
	public static final int ORG_ID = 2;
	public static final int IMAGE_TYPE = 3;
	public static final int IMAGE_CLASSIFICATION = 4;
	public static final int REGISTRATION_DATE = 5;
	public static final int ISSUE_REASON = 6;
	public static final int MANAGED_ORG = 7;
	public static final int REGISTRATION_REMARKS = 8;
	public static final int DISUSE_YN = 9;
	public static final int DISUSE_DATE = 10;
	public static final int DISUSE_REASON = 11;
	public static final int DISUSE_REMARKS = 12;
	public static final int IMAGE_FILE_TYPE = 13;
	public static final int IMAGE = 14;
	public static final int SIZE_WIDTH = 15;
	public static final int SIZE_HEIGHT = 16;
	public static final int IMAGE_ORDER = 17;
	
	public static final String m_strColumn[] =
	{
		"IMAGE_ID",
		"IMAGE_NAME",
		"ORG_ID",
		"IMAGE_TYPE",
		"IMAGE_CLASSIFICATION",
		"REGISTRATION_DATE",
		"ISSUE_REASON",
		"MANAGED_ORG",
		"REGISTRATION_REMARKS",
		"DISUSE_YN",
		"DISUSE_DATE",
		"DISUSE_REASON",
		"DISUSE_REMARKS",
		"IMAGE_FILE_TYPE",
		"IMAGE",
		"SIZE_WIDTH",
		"SIZE_HEIGHT",
		"IMAGE_ORDER"
	};
	
	public static final int m_nDataType[] =
	{
		STRING,
		STRING,
		STRING,
		INTEGER,
		INTEGER,
		DATE,
		STRING,
		STRING,
		STRING,
		BOOLEAN,
		DATE,
		STRING,
		STRING,
		STRING,
		BLOB,
		INTEGER,
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
