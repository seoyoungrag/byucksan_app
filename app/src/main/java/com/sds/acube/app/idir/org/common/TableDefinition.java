package com.sds.acube.app.idir.org.common;

/**
 * TableDefinition.java
 * 2002-10-09
 * 
 * 조직에서 사용되는 테이블 정의서 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class TableDefinition 
{
	public static final int ORGANIZATION = 0;
	public static final int USER_BASIC = 1;
	public static final int USER_IMAGE = 2;
	public static final int USER_STATUS = 3; 
	public static final int USER_ASSOCIATED = 4;
	public static final int USER_ADDR = 5;
	public static final int USER_TIME = 6;
	public static final int USER_PASSWORD = 7;
	public static final int USER_OPTION = 8;
	public static final int GROUP_COMMONINFO = 9;
	public static final int GROUP_COMMONMEMBER = 10; 
	public static final int SYSTEM_BASIC = 11;
	public static final int OPTION = 12;
	public static final int ORG_IMAGE = 13;
	public static final int SERVER = 14;
	public static final int GRADE = 15;
	public static final int TITLE= 16;
	public static final int POSITION = 17;
	public static final int ROLE = 18;
	public static final int CLASSIFICATION = 19;
	public static final int COMP = 20;
	public static final int STORED_LINE_PUBLIC =21;
	public static final int STORED_LINE_PRIVATE = 22;
	public static final int APPROVER_PUBLIC = 23;
	public static final int APPROVER_PRIVATE = 24;
	public static final int STORED_RECIPIENTS_PUBLIC = 25;
	public static final int STORED_RECIPIENTS_PRIVATE = 26;
	public static final int RECIPIENT_PUBLIC = 27;
	public static final int RECIPIENT_PRIVATE = 28;
	public static final int CODE = 29;
	public static final int SYSTEM_ARCHITECTURE = 30;
	public static final int SYSTEM_BUSINESS = 31;
	public static final int SYSTEM_LEGACY = 32;
	public static final int SYSTEM_NOTIFICATION = 33;
	public static final int ZIPCODE = 34;
	public static final int GLOBALINFO = 35;
	public static final int FAVORITE_CLASSIFICATION = 36;
	public static final int BUSINESS = 37;
	public static final int SYSTEMINFO_SERVER = 38;
	public static final int BUSINESS_CATEGORY = 39;
	public static final int LEDGER_CATEGORY = 40;
	public static final int USER_LIST_VIEW = 41;
	public static final int USERINFO_DETAIL_VIEW = 42;
	public static final int USER_RELATION = 43;
	public static final int USER_PROLE = 44;
	public static final int PORTAL_GROUP = 45;
	public static final int DUTY = 46;
	public static final int PROCESS_ROLE = 47;
	public static final int ORG_ROLE = 48;
	
	// Table Name	
	public static final String[] m_strTableName =
	{
		"TCN_ORGANIZATIONINFORMATION",		
		"TCN_USERINFORMATION_BASIC",
		"TCN_USERINFORMATION_IMAGE",
		"TCN_USERINFORMATION_STATUS",
		"TCN_USERINFORMATION_ASSOCIATED",
		"TCN_USERINFORMATION_ADDR",
		"TCN_USERINFORMATION_TIME",
		"TCN_USERINFORMATION_PASSWORD",
		"TCN_USERINFORMATION_OPTION",
		"TCN_COMMOMGROUPINFORMATION",
		"TCN_COMMONGROUPMEMBERS",
		"TCN_SYSTEMINFORMATION_BASIC",
		"TCN_OPTIONINFORMATION",
		"TCN_ORGINFORMATION_IMAGE",
		"TCN_SERVERINFORMATION",
		"TCN_GRADEINFORMATION",
		"TCN_TITLEINFORMATION",
		"TCN_POSITIONINFORMATION",
		"TCN_ROLEINFORMATION",
		"TCN_CLASSIFICATIONINFORMATION",
		"TCN_COMPANYINFORMATION",
		"TCN_STORED_LINE_PUBLIC",
		"TCN_STORED_LINE_PRIVATE",
		"TCN_APPROVAL_LINE_PUBLIC",
		"TCN_APPROVAL_LINE_PRIVATE",
		"TCN_STORED_RECIPIENTS_PUBLIC",
		"TCN_STORED_RECIPIENTS_PRIVATE",
		"TCN_RECIPIENT_PUBLIC",
		"TCN_RECIPIENT_PRIVATE",
		"TCN_CODEINFORMATION",
		"TCN_SYSTEMINFO_ARCHITECTURE",
		"TCN_SYSTEMINFO_BUSINESS",
		"TCN_SYSTEMINFO_LEGACY",
		"TCN_SYSTEMINFO_NOTIFICATION",
		"TCN_ZIPCODE",
		"TCN_GLOBAL_INFORMATION",
		"TCN_CLASSIFICATION_FAVORITES",
		"TCN_APPROVAL_BUSINESS",
		"TCN_SYSTEMINFO_SERVER",
		"TCN_BUSINESS_CATEGORY",
		"TCN_LEDGER_CATEGORY",
		"VCN_USERLIST",
		"VCN_USERINFO_DETAIL",
		"TCN_USERINFORMATION_RELATION",
		"TCN_USERINFORMATION_PROLE",
		"TCN_PORTAL_GROUP_INFORMATION",
		"TCN_DUTYINFORMATION",
		"TCN_PROCESSROLEINFORMATION",
		"TCN_ORGROLEINFORMATION"
	};
	
	/**
	 * 테이블명을 얻음.
	 * @param nTableType 이름을 얻고자 하는 Table Type
	 * @return String 	테이블명
	 */	
	public static String getTableName(int nTableType)
	{
		return m_strTableName[nTableType];
	}
}
