package com.sds.acube.app.idir.org.user;

/**
 * UserAddressTableMap.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;

public class UserAddressTableMap extends TableMap
{
	// User Address Table Map
	public static final int USER_ID = 0;
	public static final int EMAIL = 1;
	public static final int DUTY = 2;
	public static final int PCONLINE_ID = 3;
	public static final int HOMEPAGE = 4;
	public static final int OFFICE_TEL = 5;
	public static final int OFFICE_TEL2 = 6;
	public static final int OFFICE_ADDR = 7;
	public static final int OFFICE_DETAIL_ADDR = 8;
	public static final int OFFICE_ZIPCODE = 9;
	public static final int OFFICE_FAX = 10;
	public static final int MOBILE = 11;
	public static final int MOBILE2 = 12;
	public static final int PAGER = 13;
	public static final int HOME_ADDR = 14;
	public static final int HOME_DETAIL_ADDR = 15;
	public static final int HOME_ZIPCODE = 16;
	public static final int HOME_TEL = 17;
	public static final int HOME_TEL2 = 18;
	public static final int HOME_FAX = 19;
	
	public static final String m_strColumn[] =
	{
		"USER_ID",
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
		"HOME_FAX"
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
