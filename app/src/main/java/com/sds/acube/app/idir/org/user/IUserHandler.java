package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.orginfo.*;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.login.security.EnDecode;
import java.sql.*;
import java.util.*;

/**
 * IUsers.java
 * 2003-04-30
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class IUserHandler extends DataHandler 
{
	public static final int SEARCH_GROUP = 0;	  				// 전체 Group 내 검색
	public static final int SEARCH_COMPANY = 1;    				// 회사 내 검색
	public static final int SEARCH_DEPT = 2;       				// 부서 내 검색
	
	public static final int COMPARE_TYPE_PURE = 0;				// 저장되어 있는 데이터 형태 그대로 비교
	public static final int COMPARE_TYPE_STYPE = 1;				// sutil 모듈로 디코딩 후 비교
	public static final int COMPARE_TYPE_DTYPE = 2;				// sutil 모듈로 디코딩 후 비교(DB 데이타만)
	
	public static final int SEARCH_SCOPE_INOFFICE = 0;					// 재직 사용자만 검색
	public static final int SEARCH_SCOPE_SUSPENSION = 1;				// 정직 사용자만 검색
	public static final int SEARCH_SCOPE_RETIREMENT = 2;				// 퇴직 사용자만 검색
	public static final int SEARCH_SCOPE_INOFFICE_N__SUSPENSION = 3;	// 재직, 정직 사용자 검색
	public static final int SEARCH_SCOPE_ALL = 4;						// 모든 사용자 검색
	
	public static final int PAGE_SEARCH_TYPE_USER_UID = 0;				// 사용자 UID로 페이지 위치 검색
	public static final int PAGE_SEARCH_TYPE_USER_ID = 1;				// 사용자 ID로 페이지 위치 검색 
	public static final int PAGE_SEARCH_TYPE_USER_NAME = 2;				// 사용자 이름으로 페이지 위치 검색 
	
	public static final String ENCRYPT_DELIMITER = ":";					// sutil package를 사용하였을 경우 delimiter
	public static final String ENCRYPT_POSTFIX = ":sisenc";				// sutil package를 사용하였을 경우 뒤에 붙는 꼬리말
		
	public static final String SERVER_TYPE_MAIL = "MAIL";

	private String m_strDetailUserColumns = "";
	private String m_strDetailUserTable = "";
	private String m_strDetailSUserColumns = "";
    private String m_strIUserWithVirtualSQuery = "";	// Virtual 사용자까지 가지고 오는 Select Query
	private String m_strIUserWithVirtualFQuery = "";	// Virtual 사용자까지 가지고 오는 From Query
	
	//2009.07.08 
	//검색리스트 정렬시 로그인 사용자 회사가  먼저 나오도록 하기 위해 설정 ----start-----
	private String m_strLoginCompID = ""; //로그인한 사용자 회사ID
	private String m_strCompOrderColumnName = ""; //SELECT시 사용하기 위한 COLUMN_NAME
	private boolean isUseCompOrder = false; //회사순 정렬 사용여부
	
	public void setStrLoginCompID(String strLoginCompID) {
		this.m_strLoginCompID = strLoginCompID;
		this.isUseCompOrder = true;
		this.m_strCompOrderColumnName = ", COMP_ORDER ";
	}
	//검색리스트 정렬시 로그인 사용자 회사가  먼저 나오도록 하기 위해 설정 ----end-----
	
	public IUserHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strDetailUserTable = TableDefinition.getTableName(TableDefinition.USERINFO_DETAIL_VIEW);
		m_strDetailUserColumns = m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ID) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ORDER) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SECURITY_LEVEL) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ROLE_CODE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESIDENT_NO) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMPLOYEE_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SYSMAIL) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SERVERS) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_CONCURRENT) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_PROXY) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELEGATE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_EXISTENCE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_RID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DESCRIPTION) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED1) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED2) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED3) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_CODE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ABBR_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ORDER) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_CODE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ABBR_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ORDER) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_CODE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_OTHER_NAME) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_ORDER) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMAIL) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PCONLINE_ID) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOMEPAGE) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL) + "," +
							     m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ADDR) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_DETAIL_ADDR) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ZIPCODE) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_FAX) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PAGER) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ADDR) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_DETAIL_ADDR) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ZIPCODE) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_FAX) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_STATUS) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.WHEN_CHANGED_PASSWORD) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.CERTIFICATION_ID) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_CODE) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_NAME) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_OTHER_NAME) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_ORDER) + "," +
								 m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OPTIONAL_GTP_NAME);
								 
		m_strDetailSUserColumns = UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ID) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ORDER) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SECURITY_LEVEL) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ROLE_CODE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESIDENT_NO) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMPLOYEE_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SYSMAIL) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SERVERS) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_CONCURRENT) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_PROXY) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELEGATE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_EXISTENCE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_RID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DESCRIPTION) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED1) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED2) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED3) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_CODE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ABBR_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ORDER) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_CODE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ABBR_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ORDER) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_CODE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_OTHER_NAME) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_ORDER) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMAIL) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PCONLINE_ID) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOMEPAGE) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL) + "," +
							      UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ADDR) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_DETAIL_ADDR) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ZIPCODE) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_FAX) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PAGER) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ADDR) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_DETAIL_ADDR) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ZIPCODE) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_FAX) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_STATUS) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.WHEN_CHANGED_PASSWORD) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.CERTIFICATION_ID) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_CODE) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_NAME) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_OTHER_NAME) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_ORDER) + "," +
								  UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OPTIONAL_GTP_NAME);
								  
		m_strIUserWithVirtualSQuery = "SELECT  tUserBasic.USER_ID, " +
	   								  "		   tUserBasic.USER_NAME, " +
       							      "		   tUserBasic.USER_OTHER_NAME, " +
									  "	       tUserBasic.USER_UID, " +
									  "	       tUserBasic.GROUP_ID, " +
									  "	       tUserBasic.GROUP_NAME, " +
									  "	       tUserBasic.GROUP_OTHER_NAME, " +
									  "	       tUserBasic.COMP_ID, " +
									  "	       tUserBasic.COMP_NAME, " +
									  "	       tUserBasic.COMP_OTHER_NAME, " +
									  "	       tUserBasic.DEPT_ID, " +
									  "	       tUserBasic.DEPT_NAME, " +
									  "	       tUserBasic.DEPT_OTHER_NAME, " +
									  "	       tUserBasic.PART_ID, " +
									  "	       tUserBasic.PART_NAME, " +
									  "	       tUserBasic.PART_OTHER_NAME, " +
									  "	       tUserBasic.ORG_DISPLAY_NAME, " +
									  "	       tUserBasic.ORG_DISPLAY_OTHER_NAME, " +
									  "	       tUserBasic.GRADE_CODE, " +
									  "	       tGrade.GRADE_NAME, " +
									  "	       tGrade.GRADE_OTHER_NAME, " +
									  "	       tGrade.GRADE_ABBR_NAME, " +
									  "	       tGrade.GRADE_ORDER, " +
									  "	       tUserBasic.POSITION_CODE, " +
									  "	       tPosition.POSITION_NAME, " +
									  "	       tPosition.POSITION_OTHER_NAME, " +
									  "	       tPosition.POSITION_ABBR_NAME, " +
									  "	       tPosition.POSITION_ORDER, " +
									  "	       tUserBasic.TITLE_CODE, " +
									  "	       tTitle.TITLE_NAME, " +
									  "	       tTitle.TITLE_OTHER_NAME, " +
									  "	       tTitle.TITLE_ORDER, " +
									  "	       tUserBasic.USER_ORDER, " +
									  "	       tUserBasic.SECURITY_LEVEL, " +
									  "	       tUserBasic.ROLE_CODE, " +
									  "	       tUserBasic.RESIDENT_NO, " +
									  "	       tUserBasic.EMPLOYEE_ID, " +
									  "	       tUserBasic.SYSMAIL, " +
									  "	       tUserBasic.SERVERS, " +
									  "	       tUserBasic.IS_CONCURRENT, " +
									  "	       tUserBasic.IS_PROXY, " +
									  "	       tUserBasic.IS_DELEGATE, " +
									  "	       tUserBasic.IS_EXISTENCE, " +
									  "	       tUserBasic.USER_RID, " +
									  "	       tUserBasic.IS_DELETED, " +
									  "	       tUserBasic.DESCRIPTION, " +
									  "	       tUserBasic.RESERVED1, " +
									  "	       tUserBasic.RESERVED2, " +
									  "	       tUserBasic.RESERVED3, " +
									  "	       tUserAddr.EMAIL, " +
									  "	       tUserAddr.DUTY, " +
									  "	       tUserAddr.PCONLINE_ID, " +
									  "	       tUserAddr.HOMEPAGE, " +
									  "	       tUserAddr.OFFICE_TEL, " +
									  "	       tUserAddr.OFFICE_TEL2, " +
									  "		   tUserAddr.OFFICE_ADDR, " +
									  "		   tUserAddr.OFFICE_DETAIL_ADDR, " +
									  "		   tUserAddr.OFFICE_ZIPCODE, " +
									  "		   tUserAddr.OFFICE_FAX, " +
									  "		   tUserAddr.MOBILE, " +
									  "		   tUserAddr.MOBILE2, " +
									  "		   tUserAddr.PAGER, " +
									  "		   tUserAddr.HOME_ADDR, " +
									  "		   tUserAddr.HOME_DETAIL_ADDR, " +
									  "		   tUserAddr.HOME_ZIPCODE, " +
									  "		   tUserAddr.HOME_TEL, " +
									  "		   tUserAddr.HOME_TEL2, " +
									  "		   tUserAddr.HOME_FAX, " +
									  "		   tUserStatus.USER_STATUS, " +
									  "		   tUserTime.LAST_LOGON, " +
									  "		   tUserTime.LAST_LOGOUT, " +
									  "		   tUserTime.LAST_LOGOUT_IP, " +
									  "		   tUserTime.WHEN_CHANGED_PASSWORD, " +
									  "		   tUserBasic.DISPLAY_ORDER, " +
									  "		   tUserBasic.DEFAULT_USER, " +
									  "		   tUserBasic.CERTIFICATION_ID, " +
									  "		   tUserBasic.DUTY_CODE, " +
									  "		   tDuty.DUTY_NAME, " +
									  "		   tDuty.DUTY_OTHER_NAME, " +
									  "		   tDuty.DUTY_ORDER, " +
									  "        tUserBasic.OPTIONAL_GTP_NAME ";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{	
			m_strIUserWithVirtualFQuery	= "	FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " tUserBasic, " +
													   TableDefinition.getTableName(TableDefinition.USER_ADDR) + " tUserAddr, " +
													   TableDefinition.getTableName(TableDefinition.USER_STATUS) + " tUserStatus, " +
													   TableDefinition.getTableName(TableDefinition.USER_TIME) + " tUserTime, " +
													   TableDefinition.getTableName(TableDefinition.GRADE) + " tGrade, " +
													   TableDefinition.getTableName(TableDefinition.POSITION) + " tPosition, " +
													   TableDefinition.getTableName(TableDefinition.TITLE) + " tTitle, " +
													   TableDefinition.getTableName(TableDefinition.DUTY) + " tDuty " +
										  "	WHERE  tUserBasic.USER_UID = tUserAddr.USER_ID(+) " +
										  "	  AND  tUserBasic.USER_UID = tUserStatus.USER_ID(+) " +
										  "	  AND  tUserBasic.USER_UID = tUserTime.USER_ID(+) " +
										  "	  AND  tUserBasic.GRADE_CODE = tGrade.GRADE_ID(+) " +
										  "	  AND  tUserBasic.POSITION_CODE = tPosition.POSITION_ID(+) " +
										  "	  AND  tUserBasic.TITLE_CODE = tTitle.TITLE_ID(+)" +
										  "   AND  tUserBasic.DUTY_CODE = tDuty.DUTY_ID(+)";
		}
		else if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
		{
			m_strIUserWithVirtualFQuery	= "	FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " tUserBasic, " +
										        	   TableDefinition.getTableName(TableDefinition.USER_ADDR) + " tUserAddr, " +
										        	   TableDefinition.getTableName(TableDefinition.USER_STATUS) + " tUserStatus, " +
										        	   TableDefinition.getTableName(TableDefinition.USER_TIME) + " tUserTime, " +
													   TableDefinition.getTableName(TableDefinition.GRADE) + " tGrade, " +
													   TableDefinition.getTableName(TableDefinition.POSITION) + " tPosition, " +
													   TableDefinition.getTableName(TableDefinition.TITLE) + " tTitle, " +
													   TableDefinition.getTableName(TableDefinition.DUTY) + " tDuty " +	
						                  "	WHERE  tUserBasic.USER_UID *= tUserAddr.USER_ID " +
						                  "	  AND  tUserBasic.USER_UID *= tUserStatus.USER_ID " +
						                  "	  AND  tUserBasic.USER_UID *= tUserTime.USER_ID " +
						                  "	  AND  tUserBasic.GRADE_CODE *= tGrade.GRADE_ID " +
						                  "	  AND  tUserBasic.POSITION_CODE *= tPosition.POSITION_ID " +
						                  "	  AND  tUserBasic.TITLE_CODE *= tTitle.TITLE_ID" +
						                  "   AND  tUserBasic.DUTY_CODE *= tDuty.DUTY_ID";
		}
		else
		{
			m_strIUserWithVirtualFQuery	= "	FROM " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " tUserBasic" +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_ADDR) + " tUserAddr  " +
													               " ON tUserBasic.USER_UID = tUserAddr.USER_ID " +
												      " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_STATUS) + " tUserStatus " +
																   " ON tUserBasic.USER_UID = tUserStatus.USER_ID " +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_TIME) + " tUserTime " +
																   " ON tUserBasic.USER_UID = tUserTime.USER_ID " +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.GRADE) + " tGrade " +
																   " ON tUserBasic.GRADE_CODE = tGrade.GRADE_ID " +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.POSITION) + " tPosition " +
																       " ON tUserBasic.POSITION_CODE = tPosition.POSITION_ID " +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.TITLE) + " tTitle " +
													             " ON tUserBasic.TITLE_CODE = tTitle.TITLE_ID " +
													  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.DUTY) + " tDuty " +
													            " ON tUserBasic.DUTY_CODE = tDuty.DUTY_ID ";
		}
		
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return IUsers
	 */
	private IUsers processData(ResultSet resultSet)
	{
		IUsers iUsers = null;

		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "IUserHandler.processData",
								   "");
			
			return null;
		}
		
		iUsers = new IUsers();
		
		try
		{
			while(resultSet.next())
			{
				IUser iUser = new IUser();
									
				// set Employee information
				iUser.setUserID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ID)));
				iUser.setUserName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_NAME)));
		    	iUser.setUserOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_OTHER_NAME)));
		    	iUser.setUserUID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID)));
		        iUser.setGroupID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_ID)));
		        iUser.setGroupName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_NAME)));
		        iUser.setGroupOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_OTHER_NAME)));
		        iUser.setCompID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_ID)));
		        iUser.setCompName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_NAME)));
		        iUser.setCompOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_OTHER_NAME)));
		        iUser.setDeptID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_ID)));
		        iUser.setDeptName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_NAME)));
		        iUser.setDeptOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_OTHER_NAME)));
		        iUser.setPartID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_ID)));
		        iUser.setPartName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_NAME)));
		        iUser.setPartOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_OTHER_NAME)));
		        iUser.setOrgDisplayName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_NAME)));
		        iUser.setOrgDisplayOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_OTHER_NAME)));
		        iUser.setUserOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ORDER)));
		        iUser.setSecurityLevel(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SECURITY_LEVEL)));
		        iUser.setRoleCodes(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ROLE_CODE)));
		        iUser.setResidentNo(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESIDENT_NO)));
		        iUser.setEmployeeID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMPLOYEE_ID)));
		        iUser.setSysMail(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SYSMAIL)));
		        iUser.setConcurrent(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_CONCURRENT)));
		        iUser.setProxy(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_PROXY)));
		        iUser.setDelegate(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELEGATE)));
		        iUser.setExistence(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_EXISTENCE)));
		        iUser.setUserRID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_RID)));
		        iUser.setDeleted(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED)));
		        iUser.setDescription(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DESCRIPTION)));
		        iUser.setReserved1(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED1)));
		        iUser.setReserved2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED2)));
		        iUser.setReserved3(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED3)));
		        iUser.setGradeCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_CODE)));
		        iUser.setGradeName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_NAME)));
		        iUser.setGradeOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_OTHER_NAME)));
		        iUser.setGradeAbbrName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ABBR_NAME)));
		        iUser.setGradeOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ORDER)));
		       	iUser.setPositionCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_CODE)));
		       	iUser.setPositionName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_NAME)));
		       	iUser.setPositionOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_OTHER_NAME)));
		       	iUser.setPositionAbbrName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ABBR_NAME)));
		       	iUser.setPositionOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ORDER)));
		        iUser.setTitleCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_CODE)));
		        iUser.setTitleName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_NAME)));
		        iUser.setTitleOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_OTHER_NAME)));
		        iUser.setTitleOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_ORDER)));
		        iUser.setEmail(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMAIL)));
		        iUser.setDuty(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY)));
		        iUser.setPCOnlineID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PCONLINE_ID)));
		        iUser.setHomePage(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOMEPAGE)));
		        iUser.setOfficeTel(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL)));
		        iUser.setOfficeTel2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2)));
			    iUser.setOfficeAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ADDR)));
			    iUser.setOfficeDetailAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_DETAIL_ADDR)));
			    iUser.setOfficeZipCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ZIPCODE)));
			    iUser.setOfficeFax(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_FAX)));
			    iUser.setMobile(getString(resultSet,UserDetailViewTableMap.getColumnName( UserDetailViewTableMap.MOBILE)));
			    iUser.setMobile2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2)));
			    iUser.setPager(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PAGER)));
			    iUser.setHomeAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ADDR)));
			    iUser.setHomeDetailAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_DETAIL_ADDR)));
			    iUser.setHomeZipCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ZIPCODE)));
			    iUser.setHomeTel(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL)));
			    iUser.setHomeTel2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2)));
			    iUser.setHomeFax(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_FAX)));
			    iUser.setUserStatus(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_STATUS)));
			    iUser.setChangedPWDDate(getDate(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.WHEN_CHANGED_PASSWORD), TIMESTAMP_SECOND));
			    iUser.setCertificationID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.CERTIFICATION_ID)));
			    iUser.setDutyCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_CODE)));
			    iUser.setDutyName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_NAME)));
			    iUser.setDutyOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_OTHER_NAME)));
			    iUser.setDutyOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_ORDER)));
			    iUser.setOptionalGTPName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OPTIONAL_GTP_NAME)));
			    
			    // Mail Server Setting
			    String strServers = getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SERVERS));
			    if (strServers != null && strServers.length() > 0)
			    {
			    	String arrServers[] = DataConverter.splitString(strServers, "^");
			    	if (arrServers != null)
			    	{
			    		for (int i = 0; i < arrServers.length ; i++)
			    		{
			    			String arrServerValue[] = DataConverter.splitString(arrServers[i], "/");
			    			if (arrServerValue != null && arrServerValue.length == 2)
			    			{
			    				String strServerType = arrServerValue[0].toUpperCase();
			    				if (strServerType.compareTo(SERVER_TYPE_MAIL) == 0)
			    				{
			    					String strMailServer = arrServerValue[1];
			    					if (strMailServer != null && strMailServer.length() > 0)
			    					{
			    						iUser.setMailServer(strMailServer);	
			    					}
			    				}
			    			}
			    		}
			    	}
			    }
								
				iUsers.add(iUser);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make IUser classList.",
								   "IUserHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return iUsers;				
	} 
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		IUser 		iUser = null;
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
				   		   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;		
	}
	
	/**
	 * 주어진 주민등록번호를 가지는 사용자 정보 
	 * @param strResidentNo 사용자 주민등록 번호
	 * @return IUser
	 */
	public IUsers getUsersByResidentNo(String strResidentNo)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		
		// initial condition check
		if ((strResidentNo == null) || (strResidentNo.length() == 0)) 
		{
			m_lastError.setMessage("Fail to get resident number.", 
								   "IUserHandler.getUserByResidentNo.Empty resident number", 
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE RESIDENT_NO = '" + strResidentNo + "'" +
				   "   AND IS_DELETED = " + INOFFICE;
				   		   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 여러명의 사용자를 ID를 통해 검색하는 기능. 
	 * @param strUserIDs 사용자 ID들
	 * @return IUsers
	 */
	public IUsers getUsersByIDs(String strUserIDs)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchUserIDs = "";
		IUsers 		iUsers = null;
		
		if (strUserIDs == null || strUserIDs.length() == 0)
		{
			m_lastError.setMessage("Fail to get User IDs (Empty User ID list).",
								   "IUserHandler.getUsersByIDs.Empty User ID list.",
								   "");
			return iUsers;
		}
		
		strSearchUserIDs = DataConverter.replace(strUserIDs, "^", "','");
		if (strSearchUserIDs == null || strSearchUserIDs.length() == 0)
		{
			m_lastError.setMessage("Fail to replace user id delimiter.",
								   "IUserHandler.getUsersByIDs.replace user IDs delimiter.",
								   "");
			return iUsers;	
		}
		
		if (strSearchUserIDs.lastIndexOf("','") == strSearchUserIDs.length() - 3)
		{
			strSearchUserIDs = strSearchUserIDs.substring(0,strSearchUserIDs.length() - 3);
		}
		
		if (strSearchUserIDs.length() > 0)
		{
			strSearchUserIDs = "'" + strSearchUserIDs + "'";
		}
		else
		{
			m_lastError.setMessage("Fail to get user id search information.",
								   "IUserHandler.getUsersByIDs.get user ID search information.",
								   "");
			return iUsers;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID IN ( " + strSearchUserIDs + " )" +
				   "   AND IS_DELETED = " + INOFFICE +
				   " ORDER BY USER_NAME, USER_ORDER";

		bResult = m_connectionBroker.executeQuery(strQuery);

		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 여러명의 사용자를 UID를 통해 검색하는 기능. 
	 * @param strUserUIDs 사용자 UID들
	 * @return IUsers
	 */
	public IUsers getUsersByUIDs(String strUserUIDs)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchUserUIDs = "";
		IUsers 		iUsers = null;
		
		if (strUserUIDs == null || strUserUIDs.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UIDs (Empty User UID list).",
								   "IUserHandler.getUsersByUIDs.Empty User UID list.",
								   "");
			return iUsers;
		}
		
		strSearchUserUIDs = DataConverter.replace(strUserUIDs, "^", "','");
		if (strSearchUserUIDs == null || strSearchUserUIDs.length() == 0)
		{
			m_lastError.setMessage("Fail to replace user uid delimiter.",
								   "IUserHandler.getUsersByUIDs.replace user UIDs delimiter.",
								   "");
			return iUsers;	
		}
		
		if (strSearchUserUIDs.lastIndexOf("','") == strSearchUserUIDs.length() - 3)
		{
			strSearchUserUIDs = strSearchUserUIDs.substring(0,strSearchUserUIDs.length() - 3);
		}
		
		if (strSearchUserUIDs.length() > 0)
		{
			strSearchUserUIDs = "'" + strSearchUserUIDs + "'";
		}
		else
		{
			m_lastError.setMessage("Fail to get user uid search information.",
								   "IUserHandler.getUsersByUIDs.get user UID search information.",
								   "");
			return iUsers;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_UID IN ( " + strSearchUserUIDs + " )" +
				   "   AND IS_DELETED = " + INOFFICE +
				   " ORDER BY USER_NAME, USER_ORDER";
		
		bResult = m_connectionBroker.executeQuery(strQuery);

		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}

	/**
	 * 주어진 ID를 가지는 사용자 정보 (Like 검색) 
	 * @param strUserID
	 * @return IUsers
	 */
	public IUsers getUsersByID(String strUserID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchUserID = "";
		String 		strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		IUsers 		iUsers = null;
		
		if (strUserID == null)
		{
			strUserID = "";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSearchUserID = getSearchFormat(strUserID);
			
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + "," + strOrgTable +
				   " WHERE USER_ID LIKE '" + strSearchUserID + "'" +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   "   AND DEPT_ID = ORG_ID " +
				   " ORDER BY USER_NAME, ORG_ORDER, GRADE_ORDER, POSITION_ORDER ";
				 				   		   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직급명를 가지는 사용자 정보 
	 * @param strGradeName 직급명
	 * @return IUsers
	 */
	public IUsers getUsersByGradeName(String strGradeName)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchGradeName = "";
		String 		strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		IUsers 		iUsers = null;
		int			index = 1;
		
		if (strGradeName == null)
		{
			strGradeName = "";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSearchGradeName = getSearchFormat(strGradeName);
		//TODO is use comp_order
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + "," + strOrgTable +
				   " WHERE (GRADE_NAME LIKE ? OR " +
				   "        GRADE_OTHER_NAME LIKE ?) " +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   "   AND DEPT_ID = ORG_ID " +
				   " ORDER BY USER_NAME, ORG_ORDER, GRADE_ORDER, POSITION_ORDER ";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
                 
		m_connectionBroker.setString(index++, strSearchGradeName);
		m_connectionBroker.setString(index++, strSearchGradeName);
				 				   		   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직무명을 가지는 사용자 정보 
	 * @param strDutyName 직무명
	 * @return IUsers
	 */
	public IUsers getUsersByDutyName(String strDutyName)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchDutyName = "";
		String 		strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		IUsers 		iUsers = null;
		int			index = 1;
		
		if (strDutyName == null)
		{
			strDutyName = "";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSearchDutyName = getSearchFormat(strDutyName);

		//TODO is use COMP_ORDER
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + "," + strOrgTable +
				   " WHERE (DUTY_NAME LIKE ? OR DUTY_OTHER_NAME LIKE ?) "  +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   "   AND DEPT_ID = ORG_ID " +
				   " ORDER BY USER_NAME, ORG_ORDER, DUTY_ORDER ";
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}

		
		m_connectionBroker.setString(index++, strSearchDutyName);
		m_connectionBroker.setString(index++, strSearchDutyName);

		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직위명를 가지는 사용자 정보 
	 * @param strPositionName 직위명
	 * @return IUsers
	 */
	public IUsers getUsersByPositionName(String strPositionName)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchPositionName = "";
		String 		strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		IUsers 		iUsers = null;
		int			index = 1;
		
		if (strPositionName == null)
		{
			strPositionName = "";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSearchPositionName = getSearchFormat(strPositionName);
		
		//TODO is use COMP_ORDER 
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + "," + strOrgTable +
				   " WHERE (POSITION_NAME LIKE ? OR " +
				   "        POSITION_OTHER_NAME LIKE ?)" +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   "   AND DEPT_ID = ORG_ID " +
				   " ORDER BY USER_NAME, ORG_ORDER, GRADE_ORDER, POSITION_ORDER ";

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		m_connectionBroker.setString(index++, strSearchPositionName);
		m_connectionBroker.setString(index++, strSearchPositionName);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직책명를 가지는 사용자 정보 
	 * @param strTitleName 직책명
	 * @return IUsers
	 */
	public IUsers getUsersByTitleName(String strTitleName)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchTitleName = "";
		String 		strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		IUsers 		iUsers = null;
		int			index = 1;
		
		if (strTitleName == null)
		{
			strTitleName = "";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSearchTitleName = getSearchFormat(strTitleName);
		//TODO is use COMP_ORDER
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + "," + strOrgTable +
				   " WHERE (TITLE_NAME LIKE ? OR " +
				   "        TITLE_OTHER_NAME LIKE ?)" +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   "   AND DEPT_ID = ORG_ID " +
				   " ORDER BY USER_NAME, ORG_ORDER, GRADE_ORDER, POSITION_ORDER ";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		m_connectionBroker.setString(index++, strSearchTitleName);
		m_connectionBroker.setString(index++, strSearchTitleName);
				 				   		   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
				
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @param strUserPWD 사용자 PassWord
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD)
	{
		UserTimeHandler userTimeHandler = null;
		ResultSet 		resultSet = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		String 			strPassword = "";
		String 			strDeptName = "";
		String 			strDeptID = "";
		String 			strUserUID = "";
		IUsers 			iUsers = null;
		IUser 			iUser = null;
		int				nSize = 0;
		int				nCount = 0;
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_CONNECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		//strQuery = "SELECT " + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) +
		strQuery = "SELECT USER_UID" + 
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_SELECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_SELECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strUserUID = DataConverter.toString(resultSet.getString("USER_UID"));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();
			return iUser;		
		}
				   
		strQuery = " SELECT SYSTEM_PASSWORD " +
				   " FROM " + TableDefinition.getTableName(TableDefinition.USER_PASSWORD)+
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			iUser = new IUser();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserUID);

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			iUser = new IUser();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strPassword = DataConverter.toString(resultSet.getString("SYSTEM_PASSWORD"));
				nCount++;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		// Invalid Login ID
		if (resultSet == null || nCount == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();
			return iUser;		
		}
		
		// Invalid Password
		if (strPassword.compareTo(strUserPWD) != 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_PWD);
			m_connectionBroker.clearConnectionBroker();
			return iUser;	
		}
			   
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
			   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		iUser.setLoginResult(IUser.LOGIN_SUCCESS);
		
		m_connectionBroker.clearPreparedQuery();
		
        // Login Time 정보 저장 
        userTimeHandler = new UserTimeHandler(m_connectionBroker.getConnectionParam());
        bResult = userTimeHandler.setUserLogOnTime(strUserUID, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
        
        bResult = userTimeHandler.setUserLogOutTime(strUserUID, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
		
		m_connectionBroker.clearConnectionBroker();	 
		
		if (iUser != null)
		{
			strDeptName = iUser.getDeptName();
			strDeptID = iUser.getDeptID();
			if ((strDeptName == null || strDeptName.length() == 0) &&
			     strDeptID.compareTo(iUser.getCompID()) == 0)
			{
				iUser.setDeptName(iUser.getCompName());
			}	
		}
		
		return iUser;		
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 	사용자 ID
	 * @param strUserPWD 	사용자 PassWord
	 * @param nType 		로그인 Data 비교 방법
	 * 						0 : DB Data 값 그대로 비교 
	 * 						1 : sutil에서 제공하는 디코딩 모듈 사용
	 * 						2 : sutil 모듈로 DB 데이타만 디코딩 후 비교
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD, int nType)
	{
		UserTimeHandler userTimeHandler = null;
		ResultSet 		resultSet = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		String 			strPassword = "";
		String 			strDeptName = "";
		String 			strDeptID = "";
		String 			strUserUID = "";
		IUsers 			iUsers = null;
		IUser 			iUser = null;
		int				nSize = 0;
		int				nCount = 0;
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_CONNECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		//strQuery = " SELECT " + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) +
		strQuery = " SELECT USER_UID" + 
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_SELECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserID);
				   		
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.DB_SELECT_FAIL);
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strUserUID = DataConverter.toString(resultSet.getString("USER_UID"));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();
			return iUser;		
		}
			   
		strQuery = " SELECT SYSTEM_PASSWORD " +
		 		   " FROM " + TableDefinition.getTableName(TableDefinition.USER_PASSWORD) +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			iUser = new IUser();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			iUser = new IUser();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strPassword = DataConverter.toString(resultSet.getString("SYSTEM_PASSWORD"));
				nCount++;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		// Invalid Login ID
		if (resultSet == null || nCount == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();
			return iUser;		
		}

		// Invalid Password
		if (nType == COMPARE_TYPE_STYPE)
		{
			// sutil에서 제공하는 decoding 모듈로 비교 
			String strDecodedInputPW = "";
			String strDecodedStorePW = "";
									
			strDecodedInputPW = decodeBySType(strUserPWD);
			strDecodedStorePW = decodeBySType(strPassword);
												
			if ((strDecodedInputPW == null) ||
				(strDecodedStorePW == null) ||
				(strDecodedInputPW.compareTo(strDecodedStorePW) != 0))
			{
				iUser = new IUser();
				iUser.setLoginResult(IUser.INVALID_PWD);
				m_connectionBroker.clearConnectionBroker();
				return iUser;
			}
		}else if(nType == COMPARE_TYPE_DTYPE){
			
			// sutil 모듈로 디코딩 후 비교(DB 데이타만)
			String strDecodedStorePW = "";
			strDecodedStorePW = decodeBySType(strPassword);
												
			if ((strUserPWD == null) ||
				(strDecodedStorePW == null) ||
				(strUserPWD.compareTo(strDecodedStorePW) != 0))
			{
				iUser = new IUser();
				iUser.setLoginResult(IUser.INVALID_PWD);
				m_connectionBroker.clearConnectionBroker();
				return iUser;
			}
		}
		else
		{
			if (strPassword.compareTo(strUserPWD) != 0)
			{
				iUser = new IUser();
				iUser.setLoginResult(IUser.INVALID_PWD);
				m_connectionBroker.clearConnectionBroker();
				return iUser;	
			}
		}
			   
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		iUser.setLoginResult(IUser.LOGIN_SUCCESS);
		
		m_connectionBroker.clearPreparedQuery();
		
        // Login Time 정보 저장 
        userTimeHandler = new UserTimeHandler(m_connectionBroker.getConnectionParam());
        bResult = userTimeHandler.setUserLogOnTime(strUserUID, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
        
        bResult = userTimeHandler.setUserLogOutTime(strUserUID, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
		
		m_connectionBroker.clearConnectionBroker();	 
		
		if (iUser != null)
		{
			strDeptName = iUser.getDeptName();
			strDeptID = iUser.getDeptID();
			if ((strDeptName == null || strDeptName.length() == 0) &&
			     strDeptID.compareTo(iUser.getCompID()) == 0)
			{
				iUser.setDeptName(iUser.getCompName());
			}	
		}
		
		return iUser;		
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @param strUserPWD 사용자 PassWord
	 * @param strLoginID 사용자 Login IP
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD, String strLoginIP)
	{
		UserTimeHandler userTimeHandler = null;
		ResultSet 		resultSet = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		String 			strPassword = "";
		String 			strDeptID = "";
		String   		strDeptName = "";
		String		    strUserUID = "";
		IUsers 			iUsers = null;
		IUser 			iUser = null;
		int				nSize = 0;
		int				nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		//strQuery = "SELECT " + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) +
		strQuery = "SELECT USER_UID" + 
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strUserUID = DataConverter.toString(resultSet.getString("USER_UID"));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearPreparedQuery();
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();
			return iUser;		
		}
		
		strQuery = "SELECT SYSTEM_PASSWORD " +
		   		   " FROM " + TableDefinition.getTableName(TableDefinition.USER_PASSWORD) +		
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return iUser;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strPassword = DataConverter.toString(resultSet.getString("SYSTEM_PASSWORD"));
				nCount++;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next password",
								   "IUserHandler.getUserByID.next",
								   e.getMessage());
			
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		// Invalid Login ID
		if (resultSet == null || nCount == 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_LOGIN);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;		
		}
		
		// Invalid Password
		if (strPassword.compareTo(strUserPWD) != 0)
		{
			iUser = new IUser();
			iUser.setLoginResult(IUser.INVALID_PWD);
			m_connectionBroker.clearConnectionBroker();	
			return iUser;	
		}
			   
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_ID = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		iUser.setLoginResult(IUser.LOGIN_SUCCESS);
		
		m_connectionBroker.clearPreparedQuery();
		
        // Login Time 정보 저장 
        userTimeHandler = new UserTimeHandler(m_connectionBroker.getConnectionParam());
        bResult = userTimeHandler.setUserLogOnData(strUserUID, strLoginIP, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
        
        bResult = userTimeHandler.setUserLogOutData(strUserUID, strLoginIP, m_connectionBroker);
        if (bResult == false)
        {
        	m_lastError.setMessage(userTimeHandler.getLastError());
        }
		
		m_connectionBroker.clearConnectionBroker();	 
		
		if (iUser != null)
		{
			strDeptName = iUser.getDeptName();
			strDeptID = iUser.getDeptID();
			if ((strDeptName == null || strDeptName.length() == 0) &&
			     strDeptID.compareTo(iUser.getCompID()) == 0)
			{
				iUser.setDeptName(iUser.getCompName());
			}	
		}
		
		return iUser;		
	}

	/**
	 * 주어진 UID를 가지는 사용자 정보 
	 * @param strUserUID
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		IUser 		iUser = null;
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_UID = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByUID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;		
	}
	
	/**
	 * 주어진 UID를 가지는 사용자 정보 
	 * @param strUserUID
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUser getUserByUIDWithScope(String strUserUID, int nScope)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		IUser 		iUser = null;
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE USER_UID = ?" +
				   "  AND " + getScopeQuery(nScope);

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByUIDWithScope.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;		
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 사용자 정보 반환.
	 * @param strUserUID 사용자 UID
	 * @param nType      0 : 자신의 사용자 정보만 반환
	 * 					 1 : 파견, 겸직, 직무대리의 경우 원직의 주소 정보 지정
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID, int nType)
	{
		boolean bResult = false;
		String 	strQuery = "";
		String  strUserRID = "";
		IUsers 	iUsers = null;
		IUsers  relatedIUsers = null;
		IUser 	iUser = null;
		IUser 	relatedIUser = null;
		int 	nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		if (nType == 0)
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE USER_UID = ?" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else
		{
			//TODO 쿼리 정리 view로 처리하면 안되나?
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{
				strQuery = "SELECT * " +
						   " FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic, " +
							   TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr, " +
							   TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus, " +
							   TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime, " +
							   TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo, " +
							   TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo, " +
							   TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo, " +
							   TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +
						   " WHERE UserInfoBasic.USER_UID = UserInfoAddr.USER_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = UserInfoStatus.USER_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = UserInfoTime.USER_ID(+) " +
					       "   AND UserInfoBasic.GRADE_CODE = GrdInfo.GRADE_ID(+) " +
						   "   AND UserInfoBasic.POSITION_CODE = PosInfo.POSITION_ID(+) " +
						   "   AND UserInfoBasic.TITLE_CODE = TltInfo.TITLE_ID(+)  " +
						   "   AND UserInfoBasic.DUTY_CODE = DutyInfo.DUTY_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = ?" +
						   "   AND UserInfoBasic.IS_DELETED = " + INOFFICE;
			}	
			else if (m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery = "SELECT * " +
						   " FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic, " +
							   TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr, " +
							   TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus, " +
							   TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime, " +
							   TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo, " +
							   TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo, " +
							   TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo, " +
							   TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +
						   " WHERE UserInfoBasic.USER_UID *= UserInfoAddr.USER_ID " +
						   "   AND UserInfoBasic.USER_UID *= UserInfoStatus.USER_ID " +
						   "   AND UserInfoBasic.USER_UID *= UserInfoTime.USER_ID " +
					       "   AND UserInfoBasic.GRADE_CODE *= GrdInfo.GRADE_ID " +
						   "   AND UserInfoBasic.POSITION_CODE *= PosInfo.POSITION_ID " +
						   "   AND UserInfoBasic.TITLE_CODE *= TltInfo.TITLE_ID  " +
						   "   AND UserInfoBasic.DUTY_CODE *= DutyInfo.DUTY_ID " +
						   "   AND UserInfoBasic.USER_UID = ?" +
						   "   AND UserInfoBasic.IS_DELETED = " + INOFFICE;
			}
			else
			{
				strQuery = "SELECT * " +
						   " FROM " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic" +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr  " +
										               " ON tUserBasic.USER_UID = tUserAddr.USER_ID " +
									      " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus " +
													   " ON tUserBasic.USER_UID = tUserStatus.USER_ID " +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime " +
													   " ON tUserBasic.USER_UID = tUserTime.USER_ID " +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo " +
													   " ON tUserBasic.GRADE_CODE = tGrade.GRADE_ID " +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo " +
													       " ON tUserBasic.POSITION_CODE = tPosition.POSITION_ID " +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo " +
										             " ON tUserBasic.TITLE_CODE = tTitle.TITLE_ID " +
										  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +
										            " ON tUserBasic.DUTY_CODE = tDuty.DUTY_ID " +
							" WHERE UserInfoBasic.USER_UID = ?" +
							"   AND UserInfoBasic.IS_DELETED = " + INOFFICE;	
			}		
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByUID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		iUser = iUsers.get(0);	
		m_connectionBroker.clearPreparedQuery();
		
		strUserRID = iUser.getUserRID();
		
		// 겸직, 파견, 직무대리인 경우 원직자의 부서 정보까지 포함 
		if (iUser != null && nType == 1)
		{
			if ((iUser.isConcurrent() == true || iUser.isDelegate() == true ||
				 iUser.isProxy() == true) &&
				(strUserRID != null && strUserRID.length() > 0))
			{
				strQuery = "SELECT " + m_strDetailUserColumns +
				   		   " FROM " + m_strDetailUserTable +
				   		   " WHERE USER_UID = ?";
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
				 
				m_connectionBroker.setString(1, strUserRID);
				
				bResult = m_connectionBroker.executePreparedQuery();
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				relatedIUsers = processData(m_connectionBroker.getResultSet());
				if (iUsers == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				nSize = relatedIUsers.size();
				if (nSize != 1)
				{			
					m_lastError.setMessage("Fail to get correct related user infomation.", 
										   "IUserHandler.getUserByUID.RelatedUser.LinkedList.size(not unique)", 
										   "");
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				relatedIUser = relatedIUsers.get(0);
				
				if (relatedIUser != null)
				{
					// copy address information	
					iUser.setEmail(relatedIUser.getEmail());
					iUser.setDuty(relatedIUser.getDuty());
					iUser.setPCOnlineID(relatedIUser.getPCOnlineID());
					iUser.setHomePage(relatedIUser.getHomePage());
					iUser.setOfficeTel(relatedIUser.getOfficeTel());
					iUser.setOfficeTel2(relatedIUser.getOfficeTel2());
					iUser.setOfficeAddr(relatedIUser.getOfficeAddr());
					iUser.setOfficeDetailAddr(relatedIUser.getOfficeDetailAddr());
					iUser.setOfficeZipCode(relatedIUser.getOfficeZipCode());
					iUser.setOfficeFax(relatedIUser.getOfficeFax());
					iUser.setMobile(relatedIUser.getMobile());
					iUser.setMobile2(relatedIUser.getMobile2());
					iUser.setPager(relatedIUser.getPager());
					iUser.setHomeAddr(relatedIUser.getHomeAddr());
					iUser.setHomeDetailAddr(relatedIUser.getHomeDetailAddr());
					iUser.setHomeZipCode(relatedIUser.getHomeZipCode());
					iUser.setHomeTel(relatedIUser.getHomeTel());
					iUser.setHomeTel2(relatedIUser.getHomeTel2());
					iUser.setHomeFax(relatedIUser.getHomeFax());
				}				   
			}	
		}		   
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 사용자 정보 반환.
	 * @param strUserUID 사용자 UID
	 * @param nType      0 : 자신의 사용자 정보만 반환
	 * 					 1 : 파견, 겸직, 직무대리의 경우 원직의 주소 정보 지정
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID, int nType, int nScope)
	{
		boolean bResult = false;
		String 	strQuery = "";
		String  strUserRID = "";
		IUsers 	iUsers = null;
		IUsers  relatedIUsers = null;
		IUser 	iUser = null;
		IUser 	relatedIUser = null;
		int 	nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}

		if (nType == 0)
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE USER_UID = ?" +
					   "   AND " + getScopeQuery(nScope);
		}
		else
		{
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{
				strQuery = "SELECT * " +
						   " FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic, " +
										TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr, " +
										TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus, " +
										TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime, " +
										TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo, " +
										TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo, " +
										TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo, " +
										TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +
						   " WHERE UserInfoBasic.USER_UID = UserInfoAddr.USER_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = UserInfoStatus.USER_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = UserInfoTime.USER_ID(+) " +
					       "   AND UserInfoBasic.GRADE_CODE = GrdInfo.GRADE_ID(+) " +
						   "   AND UserInfoBasic.POSITION_CODE = PosInfo.POSITION_ID(+) " +
						   "   AND UserInfoBasic.TITLE_CODE = TltInfo.TITLE_ID(+)  " +
						   "   AND UserInfoBasic.DUTY_CODE = DutyInfo.DUTY_ID(+) " +
						   "   AND UserInfoBasic.USER_UID = ?" +
						   "   AND UserInfoBasic." + getScopeQuery(nScope);
			}	
			else if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery = "SELECT * " +
						   " FROM  " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic, " +
										TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr, " +
										TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus, " +
										TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime, " +
										TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo, " +
										TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo, " +
										TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo, " +
										TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +	
						   " WHERE UserInfoBasic.USER_UID *= UserInfoAddr.USER_ID " +
						   "   AND UserInfoBasic.USER_UID *= UserInfoStatus.USER_ID " +
						   "   AND UserInfoBasic.USER_UID *= UserInfoTime.USER_ID " +
					       "   AND UserInfoBasic.GRADE_CODE *= GrdInfo.GRADE_ID " +
						   "   AND UserInfoBasic.POSITION_CODE *= PosInfo.POSITION_ID " +
						   "   AND UserInfoBasic.TITLE_CODE *= TltInfo.TITLE_ID  " +
						   "   AND UserInfoBasic.DUTY_CODE *= DutyInfo.DUTY_ID " +
						   "   AND UserInfoBasic.USER_UID = ?" +
						   "   AND UserInfoBasic." + getScopeQuery(nScope);
			}
			else
			{
				strQuery = "SELECT * " +
						   " FROM " +  TableDefinition.getTableName(TableDefinition.USER_BASIC) + " UserInfoBasic" +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_ADDR) + " UserInfoAddr  " +
							               " ON tUserBasic.USER_UID = tUserAddr.USER_ID " +
						      " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_STATUS) + " UserInfoStatus " +
										   " ON tUserBasic.USER_UID = tUserStatus.USER_ID " +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.USER_TIME) + " UserInfoTime " +
										   " ON tUserBasic.USER_UID = tUserTime.USER_ID " +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.GRADE) + " GrdInfo " +
										   " ON tUserBasic.GRADE_CODE = tGrade.GRADE_ID " +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.POSITION) + " PosInfo " +
										       " ON tUserBasic.POSITION_CODE = tPosition.POSITION_ID " +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.TITLE) + " TltInfo " +
							             " ON tUserBasic.TITLE_CODE = tTitle.TITLE_ID " +
							  " LEFT OUTER JOIN " + TableDefinition.getTableName(TableDefinition.DUTY) + " DutyInfo " +
							            " ON tUserBasic.DUTY_CODE = tDuty.DUTY_ID "+
							" WHERE UserInfoBasic.USER_UID = ?" +
							"   AND UserInfoBasic." + getScopeQuery(nScope).trim();
			}		
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		m_connectionBroker.setString(1, strUserUID);
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByUID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		iUser = iUsers.get(0);	
		m_connectionBroker.clearQuery();
		
		strUserRID = iUser.getUserRID();
		
		// 겸직, 파견, 직무대리인 경우 원직자의 부서 정보까지 포함 
		if (iUser != null && nType == 1)
		{
			if ((iUser.isConcurrent() == true || iUser.isDelegate() == true ||
				 iUser.isProxy() == true) &&
				(strUserRID != null && strUserRID.length() > 0))
			{
				strQuery = "SELECT " + m_strDetailUserColumns +
				   		   " FROM " + m_strDetailUserTable +
				   		   " WHERE USER_UID = ?";
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
				
				m_connectionBroker.setString(1, strUserRID);
				   		   
				bResult = m_connectionBroker.executeQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				relatedIUsers = processData(m_connectionBroker.getResultSet());
				if (iUsers == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				nSize = relatedIUsers.size();
				if (nSize != 1)
				{			
					m_lastError.setMessage("Fail to get correct related user infomation.", 
										   "IUserHandler.getUserByUID.RelatedUser.LinkedList.size(not unique)", 
										   "");
					m_connectionBroker.clearConnectionBroker();
					return iUser;
				}
		
				relatedIUser = relatedIUsers.get(0);
				
				if (relatedIUser != null)
				{
					// copy address information	
					iUser.setHomeTel(relatedIUser.getHomeTel());
					iUser.setHomeTel2(relatedIUser.getHomeTel2());
					iUser.setHomeFax(relatedIUser.getHomeFax());
					iUser.setHomeZipCode(relatedIUser.getHomeZipCode());
					iUser.setHomeAddr(relatedIUser.getHomeAddr());
					iUser.setHomeDetailAddr(relatedIUser.getHomeDetailAddr());					
					iUser.setOfficeTel(relatedIUser.getOfficeTel());
					iUser.setOfficeTel2(relatedIUser.getOfficeTel2());
					iUser.setOfficeFax(relatedIUser.getOfficeFax());					
					iUser.setOfficeZipCode(relatedIUser.getOfficeZipCode());
					iUser.setOfficeAddr(relatedIUser.getOfficeAddr());
					iUser.setOfficeDetailAddr(relatedIUser.getOfficeDetailAddr());					
					iUser.setMobile(relatedIUser.getMobile());
					iUser.setMobile2(relatedIUser.getMobile2());
					iUser.setEmail(relatedIUser.getEmail());
					iUser.setDuty(relatedIUser.getDuty());
					iUser.setPCOnlineID(relatedIUser.getPCOnlineID());
					iUser.setHomePage(relatedIUser.getHomePage());
					iUser.setPager(relatedIUser.getPager());
				}				   
			}	
		}		   
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;
	}
	
	/**
	 * 주어진 System Email Address를 가지는 사용자 정보 
	 * @param strSysmail System Email Address
	 * @return IUser
	 */
	public IUser getUserBySysmail(String strSysmail)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		IUser 		iUser = null;
		int			nSize = 0;
		int			index = 1;
		
		if (strSysmail == null || strSysmail.length() == 0)
		{
			m_lastError.setMessage("Fail to get system mail address.",
								   "IUserHandler.getUserBySysmail.empty system mail address",
								   "");
			return iUser;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE SYSMAIL = ?" +
				   "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(index++, strSysmail);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByUID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;		
	}
	
	/**
	 * 주어진 System Email Address를 가지는 사용자 정보 
	 * @param strSysmails System Email Addresses
	 * @return IUsers
	 */
	public IUsers getUsersBySysmail(String strSysmails)
	{
		boolean 	bResult = false;
		char[] 		ch = {30};
		String 		strQuery = "";
		String 		arrSysMail[] = null;
		String 		strDeli = new String(ch);
		String 		strSysMail = "";
		IUsers 		iUsers = null;
		int			nLength = 0;
		int			nSearchType = 0;	// 0 : in 검색, 1 : equal 검색, 2 : like 검색, 3 : 전체 검색
		
		if (strSysmails == null || strSysmails.length() == 0)
		{
			m_lastError.setMessage("Fail to get system mail address.",
								   "IUserHandler.getUsersBySysmail.empty system mail address",
								   "");
			return iUsers;
		}
				
		arrSysMail = DataConverter.splitString(strSysmails, strDeli);
		
		if (arrSysMail == null)
		{
			m_lastError.setMessage("Fail to get system mail address array.",
								   "IUserHandler.getUsersBySysmail.empty system mail address",
								   "");
			return iUsers;			
		}
		
		// 검색 Type을 결정
		nLength = arrSysMail.length;
		if (nLength == 1)
		{
			String strSearchSysMail = arrSysMail[0];
			int    nCount = DataConverter.getFindCount(strSearchSysMail, SEARCH_LIKE);
			
			if (nCount == 0)
			{
				nSearchType = 1;
			}
			else if (nCount == strSearchSysMail.length())
			{
				nSearchType = 3;
			}
			else 
			{
				nSearchType = 2;
			}			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (nSearchType == 1)			// SearchType 1 : equal 검색을 하는 경우
		{
			strSysMail = arrSysMail[0];	
			
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE SYSMAIL = '" + strSysMail + "'" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)		// SearchType 2 : like 검색을 하는 경우
		{
			strSysMail = getSearchFormat(arrSysMail[0]);	
			
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE SYSMAIL LIKE '" + strSysMail + "'" +
					   "   AND IS_DELETED = " + INOFFICE;		
		}
		else if (nSearchType == 3)		// SearchType 3 : 전체검색을 하는 경우
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;		
		}
		else							// SearchType 0 : IN 검색을 하는 경우
		{
			for (int i = 0 ; i < arrSysMail.length ; i++)
			{
				strSysMail += "'" + arrSysMail[i] + "'";
				if (i != arrSysMail.length - 1)	
					strSysMail += " , ";
			}
			
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE SYSMAIL IN ( " + strSysMail + " )" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
//FIXME 조직순 정렬 추가
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름 
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName)
	{
		return getUsersByNameInCaseSensitive(strUserName, SEARCH_SCOPE_INOFFICE);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, int nScope)
	{
		return getUsersByNameInCaseSensitive(strUserName, nScope);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 	 사용자 이름
	 * @param bCaseSensative 대소문자 구분 여부 (true : 대소문자 구분, 
	 * 											 false : 대소문자 무시) 
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, boolean bCaseSensative)
	{
		if (bCaseSensative == true) 	// 대소문자 구분
		{
			return getUsersByNameInCaseSensitive(strUserName, SEARCH_SCOPE_INOFFICE);	
		}
		else							// 대소문자 무시
		{
			return getUsersByNameInCaseInsensitive(strUserName, SEARCH_SCOPE_INOFFICE);		
		}
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 	 사용자 이름
	 * @param bCaseSensative 대소문자 구분 여부 (true : 대소문자 구분, 
	 * 											 false : 대소문자 무시) 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, boolean bCaseSensative, int nScope)
	{
		if (bCaseSensative == true) 	// 대소문자 구분
		{
			return getUsersByNameInCaseSensitive(strUserName, nScope);	
		}
		else							// 대소문자 무시
		{
			return getUsersByNameInCaseInsensitive(strUserName, nScope);		
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보.(대소문자 구분) 
	 * @param strUserName 사용자 이름
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseSensitive(String strUserName, int nScope)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String		strSelectQuery = "";
		String		strFromQuery = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseSensitive.Empty User Name.",
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}

		strSelectQuery = "SELECT " + m_strDetailUserColumns;
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				
				strFromQuery = 
						   " FROM " + m_strDetailUserTable +
						   " WHERE (USER_NAME LIKE ? OR "+
						   "        USER_OTHER_NAME LIKE ?) " +
						   "  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUserName;
					
				strFromQuery =
						   " FROM " + m_strDetailUserTable +
						   " WHERE (USER_NAME = ? OR "+
						   "        USER_OTHER_NAME = ?) " +
						   "  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strFromQuery =
					   " FROM " + m_strDetailUserTable +
					   " WHERE " + getScopeQuery(nScope);
		}
		
		strQuery = queryCombine(strSelectQuery, strFromQuery, null);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 검색 범위에 대한 query를 만들어 주는 부분.
	 * @param nScope 검색 범위
	 * @return String 
	 */
	private String getScopeQuery(int nScope) 
	{
		String strQuery = "";
		
		switch (nScope) {
			case SEARCH_SCOPE_INOFFICE:
					strQuery = " IS_DELETED = " + INOFFICE;
					break;
			case SEARCH_SCOPE_SUSPENSION :
					strQuery = " IS_DELETED = " + SUSPENSION;
					break;
			case SEARCH_SCOPE_RETIREMENT :
					strQuery = " IS_DELETED = " + RETIREMENT;
					break;
			case SEARCH_SCOPE_INOFFICE_N__SUSPENSION :
					strQuery = " IS_DELETED < " + RETIREMENT;
					strQuery += " AND ";
			default:
					strQuery += " IS_DELETED > -1 ";
		}
		 
		return strQuery;
	}	
	
	/**
	 * 주어진 이름을 가진 사용자 정보.(대소문자 무시) 
	 * @param strUserName 사용자 이름 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseInsensitive(String strUserName, int nScope)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String 		strUCUserName = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.Empty User Name.",
								   "");
			return null;
		}
		
		strUCUserName = strUserName.toUpperCase();
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE (UPPER(USER_NAME) LIKE ? OR "+
						   "        UPPER(USER_OTHER_NAME) LIKE ?)" +
						   "  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUCUserName;
				
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE (UPPER(USER_NAME) = ? OR "+
						   "        UPPER(USER_OTHER_NAME) = ?) " +
						   "  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE " + getScopeQuery(nScope);
		}
		
		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, null);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
		}
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, int nCurrentPage)
	{
		return getUsersByNameInCaseSensitive(strUserName, nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue, int nDocsPerPage)
	{
		return getFirstDetectedPageByNameInCaseSensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, SEARCH_SCOPE_INOFFICE);	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, int nCurrentPage, int nScope)
	{
		return getUsersByNameInCaseSensitive(strUserName, nDocsPerPage, nCurrentPage, nScope);	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue, int nDocsPerPage, int nScope)
	{
		return getFirstDetectedPageByNameInCaseSensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, nScope);	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param nCurrentPage   현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, 
	                             int nCurrentPage, boolean bCaseSensitive)
	{
		if (bCaseSensitive == true)     // 대소문자 구분
		{
			return getUsersByNameInCaseSensitive(strUserName, nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);		
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);
		}
		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName    사용자 이름
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue,
	                             		  int nDocsPerPage, boolean bCaseSensitive)
	{
		if (bCaseSensitive == true)     // 대소문자 구분
		{
			return getFirstDetectedPageByNameInCaseSensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, SEARCH_SCOPE_INOFFICE);		
		}
		else
		{
			return getFirstDetectedPageByNameInCaseInsensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, SEARCH_SCOPE_INOFFICE);
		}
		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param nCurrentPage   현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, 
	                             int nCurrentPage, boolean bCaseSensitive, int nScope)
	{
		if (bCaseSensitive == true)     // 대소문자 구분
		{
			return getUsersByNameInCaseSensitive(strUserName, nDocsPerPage, nCurrentPage, nScope);		
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, nDocsPerPage, nCurrentPage, nScope);
		}
		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue,
											 int nDocsPerPage, boolean bCaseSensitive, int nScope)
	{
		if (bCaseSensitive == true)     // 대소문자 구분
		{
			return getFirstDetectedPageByNameInCaseSensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, nScope);		
		}
		else
		{
			return getFirstDetectedPageByNameInCaseInsensitive(strUserName, nSearchType, strSearchValue, nDocsPerPage, nScope);
		}
		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 구별)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseSensitive(String strUserName, 
												 int nDocsPerPage, 
												 int nCurrentPage,
												 int nScope)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSearchUserName = "";		
		String 		strQuery = "";
		String 		strSortData = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseSensitive.Empty User Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE (USER_NAME LIKE ? OR "+
						   			"        USER_OTHER_NAME LIKE ?)" +
						    		"  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUserName;
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE (USER_NAME = ? OR "+
						   			"        USER_OTHER_NAME = ?) " +
						    		"  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE " + getScopeQuery(nScope);
		}

		strSortData = "USER_ORDER, USER_NAME";
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);

		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{ 
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||(m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + 
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + m_strCompOrderColumnName +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByNameInCaseSensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strUserName.length() > 0 && nCount != strUserName.length())
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchUserName, strSearchUserName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	private int getFirstDetectedPageByNameInCaseSensitive(String strUserName, 
														  int nSearchType,
														  String strSearchValue,
														  int nDocsPerPage,
														  int nScope)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSearchUserName = "";
		String 		strQuery = "";
		String 		strSortData = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.Empty User Name.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE ( USER_NAME LIKE ? OR "+
						   			"         USER_OTHER_NAME LIKE ?) "+
						    		"  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUserName;
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE ( USER_NAME = ? OR "+
						   			"         USER_OTHER_NAME = ?) "+ 
						    		"  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE " + getScopeQuery(nScope);
		}
		strSortData = "USER_ORDER, USER_NAME";

		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE)) 
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
						   				strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
						   				strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}//TODO  
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}

		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));		
		}
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));	
		}
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 무시)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseInsensitive(String strUserName, 
												   int nDocsPerPage, 
												   int nCurrentPage,
												   int nScope)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSearchUserName = "";
		String 		strQuery = "";
		String 		strSortData = "";
		String 		strUCUserName = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.Empty User Name.",
								   "");
			return null;
		}
		
		strUCUserName = strUserName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE (UPPER(USER_NAME) LIKE ? OR "+
						   			"        UPPER(USER_OTHER_NAME) LIKE ?) " +
						    		"  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUCUserName; 
					
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE (UPPER(USER_NAME) = ? OR "+
						   			"        UPPER(USER_OTHER_NAME) = ?)" +
						    		"  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE " + getScopeQuery(nScope);	
		}
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{ 
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchUserName, strSearchUserName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 무시)
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	private int getFirstDetectedPageByNameInCaseInsensitive(String strUserName,
															int nSearchType,
															String strSearchValue, 
												   			int nDocsPerPage,
												   			int nScope)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSearchUserName = "";
		String 		strQuery = "";
		String 		strInnerQuery = "";
		String 		strSortData = "";
		String 		strUCUserName = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.Empty User Name.",
								   "");
			return -1;
		}
		
		strUCUserName = strUserName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE ( UPPER(USER_NAME) LIKE ? OR "+
						   			"         UPPER(USER_OTHER_NAME) LIKE ?) " +	
						    		"  AND " + getScopeQuery(nScope);
			}
			else
			{
				strSearchUserName = strUCUserName;
				
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE ( UPPER(USER_NAME) = ? OR " +
						   			"         UPPER(USER_OTHER_NAME) = ?) " +
						    		"  AND " + getScopeQuery(nScope);
			}
		}
		else
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE " + getScopeQuery(nScope);	
		}
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		} 
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}//TODO
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, strSearchUserName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		}
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 사용자 검색 Total Count 반환.
	 * @param strFromQuery 조직 정보
	 * @return String
	 */
	private int getTotalCount(String strFromQuery)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int			nTotalCount = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COUNT(1) TOTAL_COUNT " +
				            strFromQuery;
				           
				bResult = m_connectionBroker.executeQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return nTotalCount;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return nTotalCount;
				}
				
				while(resultSet.next())
				{
					nTotalCount = resultSet.getInt("TOTAL_COUNT");
				}			
		
				m_connectionBroker.clearQuery();		 
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get total count.",
									   "IUserHandler.getTotalCount.SQLException.",
									   e.getMessage());
				return nTotalCount;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get total count.",
			                       "IUserHandler.getTotalCount.Closed Connection",
			                       "");
			 
		}
		
		return nTotalCount;
	}
	
	/**
	 * 사용자 검색 Total Count 반환.
	 * @param strFromQuery 조직 정보
	 * @param strValue PreparedStatement 대입값
	 * @return String
	 */
	private int getTotalCountByPS(String strFromQuery, String strValue)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int			nTotalCount = -1;
		int			index = 1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COUNT(1) TOTAL_COUNT " +
				            strFromQuery;
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				m_connectionBroker.setString(index++, strValue);
				           
				bResult = m_connectionBroker.executePreparedQuery();
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				while(resultSet.next())
				{
					nTotalCount = resultSet.getInt("TOTAL_COUNT");
				}			
		
				m_connectionBroker.clearPreparedQuery();		 
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get total count.",
									   "IUserHandler.getTotalCount.SQLException.",
									   e.getMessage());
				return nTotalCount;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get total count.",
			                       "IUserHandler.getTotalCount.Closed Connection",
			                       "");
			 
		}
		
		return nTotalCount;
	}
	
	/**
	 * 사용자 검색 Total Count 반환.
	 * @param strFromQuery 조직 정보
	 * @param strValue PreparedStatement 대입값
	 * @return String
	 */
	private int getTotalCountByPS(String strFromQuery, String strValue1, String strValue2)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int			nTotalCount = -1;
		int			index = 1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COUNT(1) TOTAL_COUNT " +
				            strFromQuery;
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				m_connectionBroker.setString(index++, strValue1);
				m_connectionBroker.setString(index++, strValue2);
				           
				bResult = m_connectionBroker.executePreparedQuery();
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				while(resultSet.next())
				{
					nTotalCount = resultSet.getInt("TOTAL_COUNT");
				}			
		
				m_connectionBroker.clearPreparedQuery();		 
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get total count.",
									   "IUserHandler.getTotalCount.SQLException.",
									   e.getMessage());
				return nTotalCount;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get total count.",
			                       "IUserHandler.getTotalCount.Closed Connection",
			                       "");
			 
		}
		
		return nTotalCount;
	}
	
	/**
	 * 사용자 검색 Total Count 반환.
	 * @param strFromQuery 조직 정보
	 * @param strValue1 PreparedStatement 대입값
	 * @param strValue2 PreparedStatement 대입값
	 * @param strValue3 PreparedStatement 대입값
	 * @return String
	 */
	private int getTotalCountByPS(String strFromQuery, String strValue1, String strValue2, String strValue3)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int			nTotalCount = -1;
		int			index = 1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COUNT(1) TOTAL_COUNT " +
				            strFromQuery;
				
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				m_connectionBroker.setString(index++, strValue1);
				m_connectionBroker.setString(index++, strValue2);
				m_connectionBroker.setString(index++, strValue3);
				           
				bResult = m_connectionBroker.executePreparedQuery();
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return nTotalCount;
				}
				
				while(resultSet.next())
				{
					nTotalCount = resultSet.getInt("TOTAL_COUNT");
				}			
		
				m_connectionBroker.clearPreparedQuery();		 
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get total count.",
									   "IUserHandler.getTotalCount.SQLException.",
									   e.getMessage());
				return nTotalCount;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get total count.",
			                       "IUserHandler.getTotalCount.Closed Connection",
			                       "");
			 
		}
		
		return nTotalCount;
	}
	
	/**
	 * 여러명의 사용자를 이름을 통해 검색하는 기능. 
	 * @param strUserNames 사용자 이름 ( 여러 사용자의 이름을 ^로 연결 )
	 * @return IUser
	 */
	public IUsers getUsersByNames(String strUserNames)
	{
		boolean 	bResult = false;
		String 		strSearchUserNames = "";
		String 		strQuery = "";
		IUsers 		iUsers = null;
		
		if (strUserNames == null || strUserNames.length() == 0)
		{
			m_lastError.setMessage("Fail to get user names.",
								   "IUserHandler.getUsersByNames.Empty User Names.",
								   "");
			return iUsers;
		}
		
		strSearchUserNames = DataConverter.replace(strUserNames, "^", "','");
		if (strSearchUserNames == null || strSearchUserNames.length() == 0)
		{
			m_lastError.setMessage("Fail to replace user names delimiter.",
								   "UserHandler.getUsersByNames.replace user names delimiter.",
								   "");
			return iUsers; 
		}
		
		if (strSearchUserNames.lastIndexOf("','") == strSearchUserNames.length() - 3)
		{
			strSearchUserNames = strSearchUserNames.substring(0, strSearchUserNames.length() - 3);	
		}
		
		if (strSearchUserNames.length() > 0)
		{
			strSearchUserNames = "'" + strSearchUserNames + "'";
		}
		else
		{
			m_lastError.setMessage("Fail to get user name search information.",
								   "UserHandler.getUsersByNames.get user name search information.",
								   "");
			return iUsers; 		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		//TODO is use COMP_ORDER
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE (USER_NAME IN (" + strSearchUserNames +") OR "+
				   "        USER_OTHER_NAME IN (" + strSearchUserNames +"))" +
				   "  AND IS_DELETED = " + INOFFICE +
				   "  ORDER BY USER_NAME, USER_ORDER";
							   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
			
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, int nType)
	{
		return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType, SEARCH_SCOPE_INOFFICE);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, int nType, int nScope)
	{
		return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType, nScope);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param bCaseSensitive 대소문자 구분 여부 ( true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, int nType, boolean bCaseSensitive)
	{
		if (bCaseSensitive == true)
		{
			return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType, SEARCH_SCOPE_INOFFICE);	
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, strOrgID, nType, SEARCH_SCOPE_INOFFICE);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param bCaseSensitive 대소문자 구분 여부 ( true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, 
								 int nType, boolean bCaseSensitive, int nScope)
	{
		if (bCaseSensitive == true)
		{
			return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType, nScope);	
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, strOrgID, nType, nScope);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 구분)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseSensitive(String strUserName, String strOrgID, int nType, int nScope)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		String 		strSearchUserName = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByName.Empty User Name.",
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				if ( nType == SEARCH_COMPANY )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE COMP_ID = ? " +
							   "   AND (USER_NAME LIKE ? OR "+
							   "        USER_OTHER_NAME LIKE ?) " +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME";			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE DEPT_ID = ?" +
							   "   AND (USER_NAME LIKE ? OR "+
							   "        USER_OTHER_NAME LIKE ?) " +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
				else
				{		
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE (USER_NAME LIKE ? OR "+
							   "        USER_OTHER_NAME LIKE ?)" +
							   "   AND " + getScopeQuery(nScope);
							  // "   ORDER BY USER_ORDER, USER_NAME"; 
				}
			}
			else
			{
				strSearchUserName = strUserName;
					
				if ( nType == SEARCH_COMPANY )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE COMP_ID = ?" +
							   "   AND (USER_NAME = ? OR "+
							   "        USER_OTHER_NAME = ?) " +
							   "   AND " + getScopeQuery(nScope);
							  // "   ORDER BY USER_ORDER, USER_NAME";			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE DEPT_ID = ?" +
							   "   AND (USER_NAME = ? OR "+
							   "        USER_OTHER_NAME = ?) " +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
				else
				{		
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE (USER_NAME = ? OR "+
							   "        USER_OTHER_NAME = ?) " +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE COMP_ID = ?" +
						   "   AND " + getScopeQuery(nScope);
						  // "   ORDER BY USER_ORDER, USER_NAME";			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE DEPT_ID = ?" +
						   "   AND " + getScopeQuery(nScope);
						   //"   ORDER BY USER_ORDER, USER_NAME"; 
			}
			else
			{		
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE " + getScopeQuery(nScope);
						  // "   ORDER BY USER_ORDER, USER_NAME"; 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);			
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 무시)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	private IUsers getUsersByNameInCaseInsensitive(String strUserName, String strOrgID, int nType, int nScope)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		String 		strSearchUserName = "";
		String 		strUCUserName = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.Empty User Name.",
								   "");
			return null;
		}
		
		strUCUserName = strUserName.toUpperCase();
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE COMP_ID = ? " +
							   "   AND (UPPER(USER_NAME) LIKE ? OR "+
							   "        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME";			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE DEPT_ID = ? " +
							   "   AND (UPPER(USER_NAME) LIKE ? OR "+
							   "        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
				else
				{		
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE (UPPER(USER_NAME) LIKE ? OR "+
							   "        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
			}
			else
			{
				strSearchUserName = strUCUserName;
					
				if ( nType == SEARCH_COMPANY )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE COMP_ID = ?" +
							   "   AND (UPPER(USER_NAME) = ? OR "+
							   "        UPPER(USER_OTHER_NAME) = ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME";			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE DEPT_ID = ?" +
							   "   AND (UPPER(USER_NAME) = ? OR "+
							   "        UPPER(USER_OTHER_NAME) = ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
				else
				{		
					strQuery = //"SELECT " + m_strDetailUserColumns +
							   " FROM " + m_strDetailUserTable +
							   " WHERE (UPPER(USER_NAME) = ? OR "+
							   "        UPPER(USER_NAME) = ?)" +
							   "   AND " + getScopeQuery(nScope);
							   //"   ORDER BY USER_ORDER, USER_NAME"; 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE COMP_ID = ?" +
						   "   AND " + getScopeQuery(nScope);
						   //"   ORDER BY USER_ORDER, USER_NAME";			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE DEPT_ID = ?" +
						   "   AND " + getScopeQuery(nScope);
						   //"   ORDER BY USER_ORDER, USER_NAME"; 
			}
			else
			{		
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE " + getScopeQuery(nScope);
						   //"   ORDER BY USER_ORDER, USER_NAME"; 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);		
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);			
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
								 String strOrgID, 
								 int nType,
								 int nDocsPerPage, 
								 int nCurrentPage)
	{
		return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType,
											 nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
										  String strOrgID, 
										  int nType,
										  int nSearchType,
										  String strSearchValue,
										  int nDocsPerPage)
	{
		return getFirstDetectedPageByNameInCaseSensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 			 nDocsPerPage, SEARCH_SCOPE_INOFFICE);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
								 String strOrgID, 
								 int nType,
								 int nDocsPerPage, 
								 int nCurrentPage,
								 int nScope)
	{
		return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType,
											 nDocsPerPage, nCurrentPage, nScope);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
										  String strOrgID, 
										  int nType,
										  int nSearchType,
										  String strSearchValue,
										  int nDocsPerPage, 
										  int nScope)
	{
		return getFirstDetectedPageByNameInCaseSensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 			nDocsPerPage, nScope);
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
			                              String strOrgID, 
			                              int nType,
			                              int nSearchType,
			                              String strSearchValue,
			                              int nDocsPerPage, 
			                              boolean bCaseSensitive,
			                              int nScope)
	{
		if (bCaseSensitive == true)
		{
			return getFirstDetectedPageByNameInCaseSensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 	 			 nDocsPerPage, nScope);	
		}
		else
		{
			return getFirstDetectedPageByNameInCaseInsensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 	   			  nDocsPerPage, nScope);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
	                             String strOrgID, 
	                             int nType,
	                             int nDocsPerPage, 
	                             int nCurrentPage, 
	                             boolean bCaseSensitive,
	                             int nScope)
	{
		if (bCaseSensitive == true)
		{
			return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType,
											 	 nDocsPerPage, nCurrentPage, nScope);	
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, strOrgID, nType,
											 	   nDocsPerPage, nCurrentPage, nScope);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
	                             String strOrgID, 
	                             int nType,
	                             int nDocsPerPage, 
	                             int nCurrentPage, 
	                             boolean bCaseSensitive)
	{
		if (bCaseSensitive == true)
		{
			return getUsersByNameInCaseSensitive(strUserName, strOrgID, nType,
											 	 nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);	
		}
		else
		{
			return getUsersByNameInCaseInsensitive(strUserName, strOrgID, nType,
											 	   nDocsPerPage, nCurrentPage, SEARCH_SCOPE_INOFFICE);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
				                          String strOrgID, 
				                          int nType,
				                          int nSearchType,
				                          String strSearchValue,
				                          int nDocsPerPage, 
				                          boolean bCaseSensitive)
	{
		if (bCaseSensitive == true)
		{
			return getFirstDetectedPageByNameInCaseSensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 	 			 nDocsPerPage, SEARCH_SCOPE_INOFFICE);	
		}
		else
		{
			return getFirstDetectedPageByNameInCaseInsensitive(strUserName, strOrgID, nType, nSearchType, strSearchValue,
											 	   			  nDocsPerPage, SEARCH_SCOPE_INOFFICE);	
		}	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 구별)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	private IUsers getUsersByNameInCaseSensitive(String strUserName, 
												 String strOrgID, 
												 int nType,
								 				 int nDocsPerPage, 
								 				 int nCurrentPage,
								 				 int nScope)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String 		strSortData = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseSensitive.Empty User Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (USER_NAME LIKE ? OR "+
							   			"        USER_OTHER_NAME LIKE ?)" +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (USER_NAME LIKE ? OR "+
							   			"        USER_OTHER_NAME LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (USER_NAME LIKE ? OR "+
							   			"        USER_OTHER_NAME LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
			else
			{
				strSearchUserName = strUserName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (USER_NAME = ? OR "+
							   			"        USER_OTHER_NAME = ?)" +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (USER_NAME = ? OR "+
							   			"        USER_OTHER_NAME = ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (USER_NAME = ? OR "+
							   			"        USER_OTHER_NAME = ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE COMP_ID = ?" +
						   			"   AND " + getScopeQuery(nScope);			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE DEPT_ID = ?" +
						   			"   AND " + getScopeQuery(nScope); 
			}
			else
			{		
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE " + getScopeQuery(nScope); 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME"; 
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||(m_nDBType == ConnectionParam.DB_TYPE_SYBASE) )
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " + 
					   					strInnerQuery +					   						 
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID); 
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||(m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByNameInCaseSensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchUserName, strSearchUserName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchUserName, strSearchUserName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID); 
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(대소문자 구별)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	private int getFirstDetectedPageByNameInCaseSensitive(String strUserName, 
														  String strOrgID, 
														  int nType,
														  int nSearchType,
														  String strSearchValue,
										 				  int nDocsPerPage,
										 				  int nScope)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String 		strSortData = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		String 		strInnerQuery = "";
		
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.Empty User Name.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUserName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (USER_NAME LIKE ? OR "+
							   			"		 USER_OTHER_NAME LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (USER_NAME LIKE ? OR "+
							   			"		 USER_OTHER_NAME LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (USER_NAME LIKE ? OR "+
							   			"		 USER_OTHER_NAME LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
			else
			{
				strSearchUserName = strUserName;
					
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (USER_NAME = ? OR "+
							   			"		 USER_OTHER_NAME = ?)" +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (USER_NAME = ? OR "+
							   			"		 USER_OTHER_NAME = ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (USER_NAME = ? OR "+
							   			"		 USER_OTHER_NAME = ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE COMP_ID = ?" +
						   			"   AND " + getScopeQuery(nScope);			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE DEPT_ID = ?" +
						   			"   AND " + getScopeQuery(nScope); 
			}
			else
			{		
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE " + getScopeQuery(nScope); 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME"; 
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		//TODO 
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		 
		if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));			
			}
			else
			{		
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true)); 
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 무시)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	private IUsers getUsersByNameInCaseInsensitive(String strUserName, 
												   String strOrgID, 
												   int nType,
								 				   int nDocsPerPage, 
								 				   int nCurrentPage,
								 				   int nScope)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String 		strSortData = "";
		String 		strUCUserName = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.Empty User Name.",
								   "");
			return null;
		}
		
		strUCUserName = strUserName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (UPPER(USER_NAME) LIKE ? OR "+
							   			"        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (UPPER(USER_NAME) LIKE ? OR "+
							   			"        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (UPPER(USER_NAME) LIKE ? OR "+
							   			"        UPPER(USER_OTHER_NAME) LIKE ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
			else
			{
				strSearchUserName = strUCUserName;
					
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (UPPER(USER_NAME) = ? OR "+
							   			"        UPPER(USER_OTHER_NAME) = ?)" +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (UPPER(USER_NAME) = ? OR "+
							   			"        UPPER(USER_OTHER_NAME) = ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (UPPER(USER_NAME) = ? OR "+
							   			"        UPPER(USER_OTHER_NAME) = ?)" +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE COMP_ID = ?" +
						   			"   AND " + getScopeQuery(nScope);			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE DEPT_ID = ?" +
						   			"   AND " + getScopeQuery(nScope); 
			}
			else
			{		
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE " + getScopeQuery(nScope); 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME"; 
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);			
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchUserName, strSearchUserName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchUserName, strSearchUserName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (대소문자 무시)
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	private int getFirstDetectedPageByNameInCaseInsensitive(String strUserName, 
															String strOrgID, 
															int nType,
															int nSearchType,
			             									String strSearchValue,
											 				int nDocsPerPage,
											 				int nScope)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSearchUserName = "";
		String 		strSortData = "";
		String 		strUCUserName = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strUserName == null || strUserName.length() == 0)
		{
			m_lastError.setMessage("Fail to get user name.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.Empty User Name.",
								   "");
			return -1;
		}
		
		strUCUserName = strUserName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;

		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchUserName = getSearchFormat(strUCUserName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (UPPER(USER_NAME) LIKE ? OR "+
							   			"		 UPPER(USER_OTHER_NAME) LIKE ?) " +
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (UPPER(USER_NAME) LIKE ? OR "+
							   			"		 UPPER(USER_OTHER_NAME) LIKE ?) " +	
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (UPPER(USER_NAME) LIKE ? OR "+
							   			"		 UPPER(USER_OTHER_NAME) LIKE ?) " +
 							   			"   AND " + getScopeQuery(nScope); 
				}
			}
			else
			{
				strSearchUserName = strUCUserName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE COMP_ID = ?" +
							   			"   AND (UPPER(USER_NAME) = ? OR "+
							   			" 		 UPPER(USER_OTHER_NAME) = ? )" +	
							   			"   AND " + getScopeQuery(nScope);			
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE DEPT_ID = ?" +
							   			"   AND (UPPER(USER_NAME) = ? OR "+
							   			"        UPPER(USER_OTHER_NAME) = ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
				else
				{		
					strInnerFromQuery = " FROM " + m_strDetailUserTable +
							   			" WHERE (UPPER(USER_NAME) = ? OR "+
							   			"		 UPPER(USER_OTHER_NAME) = ?) " +
							   			"   AND " + getScopeQuery(nScope); 
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE COMP_ID = ?" +
						   			"   AND " + getScopeQuery(nScope);			
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE DEPT_ID = ?" +
						   			"   AND " + getScopeQuery(nScope); 
			}
			else
			{		
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE " + getScopeQuery(nScope); 
			}	
		}
		
		strSortData = "USER_ORDER, USER_NAME"; 
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) " +
					   "	) " +
					   " WHERE " + strSearchData;
		} 
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   					strInnerQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}//TODO	
				
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		
		if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			} 
			else
			{
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, strSearchUserName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			} 
			else
			{
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByNameInCaseInsensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 여러명의 사용자를 이름을 통해 검색하는 기능. 
	 * @param strUserNamess 사용자 이름 ( 여러 사용자의 이름을 ^로 연결 )
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser
	 */
	public IUsers getUsersByNames(String strUserNames, String strOrgID, int nType)
	{
		boolean 	bResult = false;
		String 		strSearchUserNames = "";
		String 		strQuery = "";
		IUsers 		iUsers = null;
		
		if (strUserNames == null || strUserNames.length() == 0)
		{
			m_lastError.setMessage("Fail to get user names.",
								   "IUserHandler.getUsersByNames.Empty User Names.",
								   "");
			return null;
		}
		
		strSearchUserNames = DataConverter.replace(strUserNames, "^", "','");
		if (strSearchUserNames == null || strSearchUserNames.length() == 0)
		{
			m_lastError.setMessage("Fail to replace user names delimiter.",
								   "UserHandler.getUsersByNames.replace user names delimiter.",
								   "");
			return iUsers; 
		}
		
		if (strSearchUserNames.lastIndexOf("','") == strSearchUserNames.length() - 3)
		{
			strSearchUserNames = strSearchUserNames.substring(0, strSearchUserNames.length() - 3);	
		}
		
		if (strSearchUserNames.length() > 0)
		{
			strSearchUserNames = "'" + strSearchUserNames + "'";
		}
		else
		{
			m_lastError.setMessage("Fail to get user name search information.",
								   "UserHandler.getUsersByNames.get user name search information.",
								   "");
			return iUsers; 		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if ( nType == SEARCH_COMPANY )
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE COMP_ID = '" + strOrgID + "'" +
					   "   AND (USER_NAME IN (" + strSearchUserNames +") OR "+
					   "        USER_OTHER_NAME IN (" + strSearchUserNames +"))" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else if ( nType == SEARCH_DEPT )
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE DEPT_ID = '" + strOrgID + "'" +
					   "   AND (USER_NAME IN (" + strSearchUserNames +") OR "+
					   "        USER_OTHER_NAME IN (" + strSearchUserNames +"))" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else
		{		
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE (USER_NAME IN (" + strSearchUserNames +") OR "+
					   "        USER_OTHER_NAME IN (" + strSearchUserNames +"))" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		
		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, null);

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		IUsers 		iUsers = null;
		int			index = 1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE DEPT_ID = ?" +
				   "  AND IS_DELETED = " + INOFFICE +
				   "  ORDER BY " + strSortData;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(index++, strDeptID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보.
	 * @param strDeptID 부서ID
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nDocsPerPage, int nCurrentPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		IUsers 		iUsers = null;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		// check initial condition
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getUsersByDeptID.Empty Department ID.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		strInnerFromQuery = " FROM " + m_strDetailUserTable +
				   	        " WHERE DEPT_ID = ?" +
				            "   AND IS_DELETED = " + INOFFICE;

		if (strSortData != null && strSortData.length() > 0)
			strOrderByQuery = " ORDER BY " + strSortData;
		
		strInnerQuery = strInnerSelectQuery + strInnerFromQuery;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
				       
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL ) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(index++, strDeptID);

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptID.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCountByPS(strInnerFromQuery, strDeptID);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptID 부서ID
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptID(String strDeptID, int nSearchType, String strSearchValue, int nDocsPerPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		String 		strSearchData = "";
		int			nPageNum = 0;
		int			index = 1;
		
		// check initial condition
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getFirstDetectedPageByDeptID.Empty Department ID.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptID.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strDeptID);
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " +
					   "	        SELECT " + m_strDetailSUserColumns +
					   "	        FROM " + m_strDetailUserTable +
					   "	        WHERE DEPT_ID = ?" +
					   "			  AND IS_DELETED = " + INOFFICE +
					   "	        ORDER BY " + strSortData +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " +
					   "	        SELECT " + m_strDetailSUserColumns +
					   "	        FROM " + m_strDetailUserTable +
					   "	        WHERE DEPT_ID = ?" +
					   "			  AND IS_DELETED = " + INOFFICE +
					   "	        ORDER BY " + strSortData +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		//TODO 
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		m_connectionBroker.setString(index++, strDeptID);
		m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptID.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 검색 조건 SQL 문을 만드는 부분.
	 * @param nSearchType 검색 조건 
	 * @param strSearchValue 검색 값
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return String
	 */
	private String getPageSearchCondition(int nSearchType, String strSearchValue, boolean bCaseSensitive)
	{
		String strCondition = "";
		
		switch (nSearchType)
		{
			case PAGE_SEARCH_TYPE_USER_UID: 
				strCondition = " USER_UID = '" + strSearchValue + "'";
				break;
			case PAGE_SEARCH_TYPE_USER_ID:
				strCondition = " USER_ID = '" + strSearchValue + "'";
				break;
			case PAGE_SEARCH_TYPE_USER_NAME:
				int nCount = 0;
				
				// count the number of '*'
				if (strSearchValue.indexOf(SEARCH_LIKE) != -1)
				{
					nCount = DataConverter.getFindCount(strSearchValue, SEARCH_LIKE);
				}
				
				if (bCaseSensitive == true) 
				{
					if (nCount> 0)
					{
						strCondition = "USER_NAME LIKE '" + getSearchFormat(strSearchValue) + "'";
					}
					else
					{
						strCondition = "USER_NAME = '" + getSearchFormat(strSearchValue) + "'";
					}
				} 
				else
				{
					if (nCount> 0)
					{
						strCondition = "UPPER(USER_NAME) LIKE '" + getSearchFormat(strSearchValue.toUpperCase()) + "'";
					}
					else
					{
						strCondition = "UPPER(USER_NAME)  = '" + getSearchFormat(strSearchValue.toUpperCase()) + "'";
					}	
				}
				
				break;
		}
		
		return strCondition;
	}
	
	/**
	 * 검색 조건 SQL 문을 만드는 부분.
	 * @param nSearchType 검색 조건 
	 * @param strSearchValue 검색 값
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return String
	 */
	private String getPageSearchConditionByPS(int nSearchType, String strSearchValue, boolean bCaseSensitive)
	{
		String strCondition = "";
		
		switch (nSearchType)
		{
			case PAGE_SEARCH_TYPE_USER_UID: 
				strCondition = " USER_UID = ?";
				break;
			case PAGE_SEARCH_TYPE_USER_ID:
				strCondition = " USER_ID = ?";
				break;
			case PAGE_SEARCH_TYPE_USER_NAME:
				int nCount = 0;
				
				// count the number of '*'
				if (strSearchValue.indexOf(SEARCH_LIKE) != -1)
				{
					nCount = DataConverter.getFindCount(strSearchValue, SEARCH_LIKE);
				}
				
				if (bCaseSensitive == true) 
				{
					if (nCount> 0)
					{
						strCondition = "USER_NAME LIKE ?";
					}
					else
					{
						strCondition = "USER_NAME = ?";
					}
				} 
				else
				{
					if (nCount> 0)
					{
						strCondition = "UPPER(USER_NAME) LIKE ?";
					}
					else
					{
						strCondition = "UPPER(USER_NAME)  = ?";
					}	
				}
				
				break;
		}
		
		return strCondition;
	}
	
	/**
	 * 검색 조건의 실제 검색값을 설정하는 부분.
	 * @param index Index
	 * @param nSearchType 검색 조건 
	 * @param strSearchValue 검색 값
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return String
	 */
	private String getPageSearchValueByPS(int nSearchType, String strSearchValue, boolean bCaseSensitive)
	{
		String strValue = "";
		
		switch (nSearchType)
		{
			case PAGE_SEARCH_TYPE_USER_UID:
				strValue = strSearchValue;
				break;
			case PAGE_SEARCH_TYPE_USER_ID:
				strValue = strSearchValue;
				break;
			case PAGE_SEARCH_TYPE_USER_NAME:
				if (bCaseSensitive == true) 
					strValue = getSearchFormat(strSearchValue);
				else
					strValue = getSearchFormat(strSearchValue.toUpperCase());	
				
				break;
		}
		
		return strValue;
	}

	/**
	 * 주어진 부서의 하위 부서 사용자 정보 모두 반환.
	 * @param strDeptID 부서 ID
	 * @return IUsers
	 */
	public IUsers getSubAllUsersByDeptID(String strDeptID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String	strOrgCodes = "";
		String  strSortData = "";
		String 	strQuery = "";
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getSubAllUsersByDeptID.Empty department ID",
								   "");
			return iUsers;
		}
		
		// Get sub departments id
		OrgManager 	  orgManager = new OrgManager(m_connectionBroker.getConnectionParam());
		Organizations organizations = orgManager.getSubAllOrganizations(strDeptID, 1);
	
		if (organizations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return iUsers;
		}
		
		for (int i = 0 ; i < organizations.size() ; i++)
		{
			Organization organization = organizations.get(i);
			if (organization != null)
			{
				String strOrgID = organization.getOrgID();
				if (strOrgID != null && strOrgID.length() > 0)
				{
					strOrgCodes += "'" + strOrgID + "',";
				}	
			}
		}
		
		if (strOrgCodes != null && strOrgCodes.length() > 0)
		{
			if (strOrgCodes.substring(strOrgCodes.length() - 1).compareTo(",") == 0)
			{
				strOrgCodes = strOrgCodes.substring(0, strOrgCodes.length() - 1);
			}
		}
		
		// Get users information
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		strSortData = getSortData(strDeptID);
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE DEPT_ID IN (" + strOrgCodes + ")" +
				   "  AND IS_DELETED = " + INOFFICE + 
				   "  ORDER BY " + strSortData;
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
		return iUsers;	
	}
	
	/**
	 * 주어진 ID를 가지는 사용자와 관련된 사용자 정보
	 * @param strUserID 사용자 ID
	 * @return IUsers
	 */
	public IUsers getRelatedUsersByID(String strUserID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
		Employees	    employees = null;
		IUsers 			iUsers = null;
		IUser			iRealUser = null;
		
		if (strUserID == null || strUserID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User ID",
								   "IUserHandler.getRelatedUserByID.Empty User ID.",
								   "");
			return iUsers;
		}
		
		iRealUser = getUserByID(strUserID);
		if (iRealUser == null)
		{
			m_lastError.setMessage("Fail to get real user by ID.",
								   "IUserHandler.getRelatedUsersByID.Empty Real User.",
								   "");
			return iUsers;	
		}
		
		iUsers = new IUsers();
		
		iUsers.add(iRealUser);	
			
		employees = employeeHandler.getRelatedEmployees(iRealUser.getUserUID());
		if (employees == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return iUsers;
		}
		
		for (int i = 0 ; i < employees.size() ; i++)
		{
			Employee employee = employees.get(i);
			IUser    iUser = new IUser();
			
			// set information		
			iUser.setUserID(employee.getUserID());
			iUser.setUserName(employee.getUserName());
			iUser.setUserUID(employee.getUserUID());
			iUser.setGroupID(employee.getGroupID());
			iUser.setGroupName(employee.getGroupName());
			iUser.setCompID(employee.getCompID());
			iUser.setCompName(employee.getCompName());
			iUser.setDeptID(employee.getDeptID());
			iUser.setDeptName(employee.getDeptName());
			iUser.setPartID(employee.getPartID());
			iUser.setPartName(employee.getPartName());
			iUser.setOrgDisplayName(employee.getOrgDisplayName());
			iUser.setGradeName(employee.getGradeName());
			iUser.setGradeAbbrName(employee.getGradeAbbrName());
			iUser.setGradeOrder(employee.getGradeOrder());
			iUser.setTitleName(employee.getTitleName());
			iUser.setTitleOrder(employee.getTitleOrder());
			iUser.setPositionName(employee.getPositionName());
			iUser.setPositionAbbrName(employee.getPositionAbbrName());
			iUser.setPositionOrder(employee.getPositionOrder());
			iUser.setUserOrder(employee.getUserOrder());
			iUser.setConcurrent(employee.getIsConcurrent());
			iUser.setProxy(employee.getIsProxy());
			iUser.setDelegate(employee.getIsDelegate());
			iUser.setExistence(employee.getIsExistence());
			iUser.setUserRID(employee.getUserRID());
			iUser.setRoleCodes(employee.getRoleCodes());
			
			iUsers.add(iUser);	
		}
		
		return iUsers;						
	}
	
	/**
	 * 주어진 사용자의 겸직 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @return IUsers
	 */
	public IUsers getConcurrentUsersByID(String strUserID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
		Employees	    employees = null;
		IUsers 			iUsers = null;
		
		if (strUserID == null || strUserID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User ID",
								   "IUserHandler.getRelatedUserByID.Empty User ID.",
								   "");
			return iUsers;
		}
							
		employees = employeeHandler.getRelatedEmployeesByID(strUserID);
		if (employees == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return iUsers;
		}
		
		iUsers = new IUsers();
		
		for (int i = 0 ; i < employees.size() ; i++)
		{
			Employee employee = employees.get(i);
			IUser    iUser = new IUser();
			
			// set information		
			iUser.setUserID(employee.getUserID());
			iUser.setUserName(employee.getUserName());
			iUser.setUserUID(employee.getUserUID());
			iUser.setGroupID(employee.getGroupID());
			iUser.setGroupName(employee.getGroupName());
			iUser.setCompID(employee.getCompID());
			iUser.setCompName(employee.getCompName());
			iUser.setDeptID(employee.getDeptID());
			iUser.setDeptName(employee.getDeptName());
			iUser.setPartID(employee.getPartID());
			iUser.setPartName(employee.getPartName());
			iUser.setOrgDisplayName(employee.getOrgDisplayName());
			iUser.setGradeCode(employee.getGradeCode());
			iUser.setGradeName(employee.getGradeName());
			iUser.setGradeAbbrName(employee.getGradeAbbrName());
			iUser.setGradeOrder(employee.getGradeOrder());
			iUser.setTitleCode(employee.getTitleCode());
			iUser.setTitleName(employee.getTitleName());
			iUser.setTitleOrder(employee.getTitleOrder());
			iUser.setPositionCode(employee.getPositionCode());
			iUser.setPositionName(employee.getPositionName());
			iUser.setPositionAbbrName(employee.getPositionAbbrName());
			iUser.setPositionOrder(employee.getPositionOrder());
			iUser.setUserOrder(employee.getUserOrder());
			iUser.setConcurrent(employee.getIsConcurrent());
			iUser.setProxy(employee.getIsProxy());
			iUser.setDelegate(employee.getIsDelegate());
			iUser.setExistence(employee.getIsExistence());
			iUser.setUserRID(employee.getUserRID());
			
			if (iUser.isConcurrent() == true)
			{
				iUsers.add(iUser);
			}	
		}
		
		return iUsers;								
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nOrgType)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		IUsers 		iUsers = null;
		int			index = 1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
		
		if (nOrgType != 3)
		{	
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE DEPT_ID = ?" +
					   "  AND IS_DELETED = " + INOFFICE +
					   "  AND (PART_ID IS NULL OR PART_ID = '') " +
					   "  ORDER BY " + strSortData;
		}
		else
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable + 
					   " WHERE PART_ID = ?" +
					     " AND IS_DELETED = " + INOFFICE +
					   	 " ORDER BY " + strSortData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(index++, strDeptID);
					   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nOrgType, int nDocsPerPage, int nCurrentPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSortData = "";
		String 		strQuery = "";
		String		strOrderByQuery = "";
		IUsers 		iUsers = null;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		// check initial condition
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getUsersByDeptID.Empty Department ID.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (nOrgType != 3)
		{	
			strInnerFromQuery = "  FROM " + m_strDetailUserTable +
					   	   " WHERE DEPT_ID = ?" +
					   	   "  AND IS_DELETED = " + INOFFICE +
					       "  AND (PART_ID IS NULL OR PART_ID = '') ";
		}
		else
		{
			strInnerFromQuery = "  FROM " + m_strDetailUserTable + 
					       " WHERE PART_ID = ?" +
					         " AND IS_DELETED = " + INOFFICE;
		}

		if (strSortData != null && strSortData.length() > 0)
			strOrderByQuery = " ORDER BY " + strSortData;
		
		strInnerQuery = strInnerSelectQuery + strInnerFromQuery;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;		
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);

		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(index++, strDeptID);

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptID.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCountByPS(strInnerFromQuery, strDeptID);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptID(String strDeptID, int nOrgType, int nSearchType, 
											String strSearchValue, int nDocsPerPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		String 		strSearchData = "";
		int			nPageNum = 0;
		int			index = 1;
		
		// check initial condition
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getFirstDetectedPageByDeptID.Empty Department ID.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptID.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strDeptID);
		
		strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
				   " FROM ( " +
				   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
				   "	    FROM ( " +
				   "	        SELECT " + m_strDetailSUserColumns ;
		if (nOrgType != 3)
		{	
			strQuery +=    "  		FROM " + m_strDetailUserTable +
					   	   " 		WHERE DEPT_ID = ? " +
					   	   "  		  AND IS_DELETED = " + INOFFICE +
					       "  		  AND (PART_ID IS NULL OR PART_ID = '') ";
		}
		else
		{
			strQuery +=    " 		FROM " + m_strDetailUserTable + 
					       " 		WHERE PART_ID = ? " +
					       " 		  AND IS_DELETED = " + INOFFICE;
		}
		
		strQuery +=	"	        ORDER BY " + strSortData +
				    "	    ) vINNERSEARCH " +
				    "	) vSEARCH " +
				    " WHERE " + strSearchData; 
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		m_connectionBroker.setString(index++, strDeptID);
		m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptID.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 주어진 회사 ID를 가지는 사용자 정보 
	 * @param strCompID 부서ID
	 * @return IUsers
	 */
	public IUsers getUsersByCompID(String strCompID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String		strSortData = "";
		IUsers 		iUsers = null;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}

		strSortData = getSortData(strCompID);
				
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE COMP_ID = '" + strCompID + "'" +
				   "  AND IS_DELETED = " + INOFFICE +
				   "  ORDER BY " + strSortData;
				   				   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
				
		return iUsers;
	}
		
	/**
	 * 회사별 입력받은 Column정보에 해당하는 Data를 반환하는 함수 
	 * @param arrCol 		Column정보 
	 * @param strCompID 	회사 ID
	 * @return String[][]  반환될 데이터
	 */
	public String[][] getUserInfo(String[] arrCol, String strCompID)
	{
		String[][] 	arrUserInfo = null;
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int		nTotalCount = 0;
		int		nColCount = 0;
		
		if (arrCol == null || arrCol.length == 0)
		{
			m_lastError.setMessage("Fail to get column data.",
						 		   "IUserHandler.getUserInfo.Empty Column Data",
								   "");	
			return null;		
		}
		
		nColCount = arrCol.length;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		
		// get total count
		strQuery = "SELECT COUNT(*) " +
				   " FROM " + m_strDetailUserTable +
				   " WHERE COMP_ID = '" + strCompID + "'";
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			while (resultSet.next())
			{
				nTotalCount	= resultSet.getInt(1);	
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to get User Information.",
								   "IUserHandler.getUserInfo",
								   e.getMessage());
								   
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		finally {
			m_connectionBroker.clearQuery();
		}
		
		if (nTotalCount == 0)
		{
			m_lastError.setMessage("Fail to get User Information.",
								   "IUserHandler.getUserInfo.Zero Total Count",
								   "");
								   
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		strQuery = "SELECT ";
		
		for (int i = 0 ; i < nColCount ; i++)
		{
			strQuery += arrCol[i];
			if ( i != nColCount - 1)
			{
				strQuery += ", ";
			}
		}
		
		strQuery += " FROM " + m_strDetailUserTable +
					" WHERE COMP_ID = '" + 	strCompID + "'";
					
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
	
		try
		{
			int nRow = 0;	
			arrUserInfo = new String[nTotalCount][nColCount];
			while (resultSet.next())
			{
				for(int i = 0 ; i < nColCount ; i++)
				{
					arrUserInfo[nRow][i] = resultSet.getString(i+1);
				}
					
				nRow++;							
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to get User Information.",
								   "IUserHandler.getUserInfo",
								   e.getMessage());
								   
			m_connectionBroker.clearConnectionBroker();
			
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
							
		return arrUserInfo;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자의 개인 정보를 수정하는 함수 
	 * @param iUser 사용자 정보
	 * @return boolean
	 */
	public boolean registerIUserAddressInfo(IUser iUser)
	{
		UserAddressHandler 	userAddressHandler = null;
		UserAddress 		userAddress = null;
		boolean 			bReturn = false;
		
		if (iUser == null || iUser.getUserUID() == null || iUser.getUserUID().length() == 0)
		{
			m_lastError.setMessage("Fail to get User ID.",
								   "IUserHandler.registerIUserAddressInfo.Empty User ID",
								   "");
			return bReturn;
		}
		
		userAddress = new UserAddress();
		
		// set user address information
		userAddress.setUserUID(iUser.getUserUID());
		userAddress.setHomeTel(iUser.getHomeTel());
		userAddress.setHomeTel2(iUser.getHomeTel2());
		userAddress.setHomeFax(iUser.getHomeFax());
		userAddress.setHomeZipCode(iUser.getHomeZipCode());
		userAddress.setHomeAddr(iUser.getHomeAddr());
		userAddress.setHomeDetailAddr(iUser.getHomeDetailAddr());
		userAddress.setOfficeTel(iUser.getOfficeTel());
		userAddress.setOfficeTel2(iUser.getOfficeTel2());
		userAddress.setOfficeFax(iUser.getOfficeFax());
		userAddress.setOfficeZipCode(iUser.getOfficeZipCode());
		userAddress.setOfficeAddr(iUser.getOfficeAddr());
		userAddress.setOfficeDetailAddr(iUser.getOfficeDetailAddr());
		userAddress.setMobile(iUser.getMobile());
		userAddress.setMobile2(iUser.getMobile2());
		userAddress.setEmail(iUser.getEmail());
		userAddress.setDuty(iUser.getDuty());
		userAddress.setHomePage(iUser.getHomePage());		
		userAddress.setPCOnlineID(iUser.getPCOnlineID());
		userAddress.setPager(iUser.getPager());
		
		// register user addresss information
		userAddressHandler = new UserAddressHandler(m_connectionBroker.getConnectionParam());
		bReturn = userAddressHandler.registerUserAddress(userAddress);
		
		if (bReturn == false)
		{
			m_lastError.setMessage(userAddressHandler.getLastError());
			return bReturn;
		}
		
		
		return bReturn;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자의 개인 정보를 수정하는 함수 
	 * @param iUser 		사용자 정보
	 * @param nType 		Update 종류 
	 * @return boolean
	 */
	public boolean updateIUserInfo(IUser iUser, int nType)
	{
		boolean 			bReturn = false;
		boolean				bResult = false;
		String 				strQuery = "";
		String				strUserUID = "";
		int					nResult = -1;
		
		if (iUser == null || iUser.getUserUID() == null || iUser.getUserUID().length() == 0)
		{
			m_lastError.setMessage("Fail to get User ID.",
								   "IUserHandler.updateIUserInfo.Empty User UID",
								   "");
			return bReturn;
		}
		
		strUserUID = iUser.getUserUID();
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		if (nType == 0) 
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + TableDefinition.getTableName(TableDefinition.USER_BASIC) +
					   " SET USER_OTHER_NAME = '" + iUser.getUserOtherName() + "'" + 
					   " WHERE USER_UID = '" + strUserUID + "'";
					   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();	
			
			strQuery = "UPDATE " + TableDefinition.getTableName(TableDefinition.USER_ADDR) +
				   	   " SET OFFICE_TEL = '" + iUser.getOfficeTel() + "'," + 
				   	   	   " OFFICE_TEL2 = '" + iUser.getOfficeTel2() + "'," +
					   	   " OFFICE_FAX = '" + iUser.getOfficeFax() + "'," +
					   	   " HOME_TEL = '" + iUser.getHomeTel() + "'," +
					   	   " HOME_TEL2 = '" + iUser.getHomeTel2() + "'," +
					       " HOME_FAX = '" + iUser.getHomeFax() + "'," +
					       " MOBILE = '" + iUser.getMobile() + "'," +
					       " MOBILE2 = '" + iUser.getMobile2() + "'," +
					       " OFFICE_ZIPCODE = '" + iUser.getOfficeZipCode() + "'," +
					       " OFFICE_ADDR = '" + iUser.getOfficeAddr() + "'," +
					       " OFFICE_DETAIL_ADDR = '" + iUser.getOfficeDetailAddr() + "'," +
					       " HOME_ZIPCODE = '" + iUser.getHomeZipCode() + "'," +
					       " HOME_ADDR = '" + iUser.getHomeAddr() + "'," +
					       " HOME_DETAIL_ADDR = '" + iUser.getHomeDetailAddr() + "'," +
					       " EMAIL = '" + iUser.getEmail() + "'," +
					       " DUTY = '" + iUser.getDuty() + "'," +
					       " HOMEPAGE = '" + iUser.getHomePage() + "'" +
					       " PCONLINE_ID = '" + iUser.getPCOnlineID() + "'" +
					       " PAGER = '" + iUser.getPager() + "'" +
				      " WHERE USER_ID = '" + strUserUID + "'";
			
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();
				return bReturn;
			}
		}
				
		m_connectionBroker.commit();					
		m_connectionBroker.clearConnectionBroker();	
				
		bReturn = true;
		return bReturn;
	}
	
	/**
	 * 주어진 Certification ID를 가지는 사용자 정보 
	 * @param strCertificationID 인증서 ID
	 * @return IUser
	 */
	public IUser getUserByCertificationID(String strCertificationID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		IUsers 		iUsers = null;
		IUser 		iUser = null;
		int			nSize = 0;
		
		if (strCertificationID == null || strCertificationID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Certification ID.",
								   "IUserHandler.getUserByCertificationID.Empty Certification ID",
								   "");
			return null;			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   " WHERE CERTIFICATION_ID = '" + strCertificationID + "'" +
				   "   AND IS_DELETED = " + INOFFICE;
				   		   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = iUsers.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "IUserHandler.getUserByCertificationID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUser = iUsers.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUser;		
	}
	
	/**
	 * 사용자 정렬 정보 반환.
	 * @param strOrgID 조직 정보
	 * @return String
	 */
	private String getSortData(String strOrgID)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strSortData = "";
		String 		strCompID = "";
		String 		strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COMPANY_ID " + 
				           " FROM " + TableDefinition.getTableName(TableDefinition.ORGANIZATION)+
				           " WHERE ORG_ID = '" + strOrgID + "'";
				           
				bResult = m_connectionBroker.executeQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strSortData;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strSortData;
				}
				
				while(resultSet.next())
				{
					strCompID = resultSet.getString("COMPANY_ID");
				}
				
		
				m_connectionBroker.clearQuery();		 
				
				if (strCompID != null && strCompID.length() > 0)
				{
					strQuery = "SELECT COMP_ID, STRING_VALUE " +
					           " FROM " + TableDefinition.getTableName(TableDefinition.OPTION)+
					           " WHERE OPTION_ID = 'AIOPT63'" +
					             " AND COMP_ID IN ('DEFAULT', '" + strCompID + "')";
					             
					bResult = m_connectionBroker.executeQuery(strQuery);
					if(bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return strSortData;
					}
					
					resultSet = m_connectionBroker.getResultSet();
					if (resultSet == null)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return strSortData;
					}		
					
					String strCompOption = "";
					String strDefaultOption = "";
					
					while (resultSet.next())
					{
						String strOwnerID = resultSet.getString("COMP_ID");
						String strOptionValue = resultSet.getString("STRING_VALUE");
						
						if (strOwnerID != null && strOwnerID.length() > 0 && 
							strOptionValue != null && strOptionValue.length() > 0)
						{
							if (strOwnerID.compareTo(strCompID) == 0)
							{
								strCompOption = strOptionValue;	
							}
							else if (strOwnerID.compareTo("DEFAULT") == 0)
							{
								strDefaultOption = strOptionValue;
							}
						}
					}
					
					if (strCompOption.length() > 0)
					{
						strSortData = strCompOption;	
					}
					else if (strDefaultOption.length() > 0)
					{
						strSortData = strDefaultOption;
					}
					else
					{
						strSortData = "USER_ORDER";
					}			 		
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get sort data.",
									   "IUserHandler.getSortData.SQLException.",
									   e.getMessage());
				return strSortData;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get sort data.",
			                       "IUserHandler.getSortData.Closed Connection",
			                       "");
			 
		}
		
		if (strSortData == null || strSortData.length() == 0)
			strSortData = "USER_ORDER";
			
		strSortData += ", USER_NAME ";

		return strSortData;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, String strOrgID, int nType,
								 	 int nDocsPerPage, int nCurrentPage)
	{
		return getUsersByDeptNameInCaseSensitive(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, String strOrgID, int nType,
											     int nSearchType, String strSearchValue,
								 	 			 int nDocsPerPage)
	{
		return getFirstDetectedPageByDeptNameInCaseSensitive(strDeptName, strOrgID, nType, nSearchType, strSearchValue,
															nDocsPerPage);				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 String strOrgID, 
									 int nType,
								 	 int nDocsPerPage, 
								 	 int nCurrentPage,
								 	 boolean bCaseSensitive,
								 	 boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getUsersByDeptNameInCaseSensitiveWithTrim(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDeptNameInCaseSensitive(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);
			}
   		} else {
			if (bTrim ==  true) {
				return getUsersByDeptNameInCaseInsensitiveWithTrim(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDeptNameInCaseInsensitive(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, 
											  String strOrgID, 
											  int nType,
											  int nSearchType,
											  String strSearchValue,
											  int nDocsPerPage,
											  boolean bCaseSensitive,
											  boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim(strDeptName, strOrgID, nType, nSearchType, strSearchValue, nDocsPerPage);
			} else {
				return getFirstDetectedPageByDeptNameInCaseSensitive(strDeptName, strOrgID, nType, nSearchType, strSearchValue, nDocsPerPage);
			}
   		} else {
			if (bTrim ==  true) {
				return getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim(strDeptName, strOrgID, nType, nSearchType, strSearchValue, nDocsPerPage);
			} else {
				return getFirstDetectedPageByDeptNameInCaseInsensitive(strDeptName, strOrgID, nType, nSearchType, strSearchValue, nDocsPerPage);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitive(String strDeptName, 
									 				String strOrgID, 
									 				int nType,
								 	 				int nDocsPerPage, 
								 	 				int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (tUserBasic.DEPT_NAME LIKE ? OR "+
							   			"       tUserBasic.DEPT_OTHER_NAME LIKE ?)" +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tUserBasic.DEPT_NAME LIKE ? OR "+
							   			"        tUserBasic.DEPT_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tUserBasic.DEPT_NAME LIKE ? OR "+
							   			                     "  tUserBasic.DEPT_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (tUserBasic.DEPT_NAME = ? OR "+
							   			"        tUserBasic.DEPT_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tUserBasic.DEPT_NAME = ? OR "+
							   			"        tUserBasic.DEPT_OTHER_NAME = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tUserBasic.DEPT_NAME = ? OR "+
							   			                     "  tUserBasic.DEPT_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT)) 
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchDeptName, strSearchDeptName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseSensitive(String strDeptName, 
													 		  String strOrgID, 
													 		  int nType,
													 		  int nSearchType,
				             								  String strSearchValue,
												 	 		  int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int			nPageNum = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (tUserBasic.DEPT_NAME LIKE ? OR "+
							   			      " tUserBasic.DEPT_OTHER_NAME LIKE ?)"+
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tUserBasic.DEPT_NAME LIKE ? OR " +
							   			       " tUserBasic.DEPT_OTHER_NAME LIKE ?) " +  
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " ( tUserBasic.DEPT_NAME LIKE ? OR "+
							   			                     "   tUserBasic.DEPT_OTHER_NAME LIKE ?) "+
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND ( tUserBasic.DEPT_NAME = ? OR "+
							   			"         tUserBasic.DEPT_OTHER_NAME = ?)"+
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND ( tUserBasic.DEPT_NAME = ? OR "+
							   			"         tUserBasic.DEPT_OTHER_NAME = ?) "+
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " ( tUserBasic.DEPT_NAME = ? OR "+
							   			                     "   tUserBasic.DEPT_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		} 
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
			else
			{		
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));	
			}	
		}
				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptID.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 구분, 공백문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitiveWithTrim(String strDeptName, 
									 						 String strOrgID, 
									 						 int nType,
								 	 						 int nDocsPerPage, 
								 	 						 int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitiveWithTrim.Empty Department Name.",
								   "");
			return null;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"  AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			"       RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)" +
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"  AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			"       TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)" +
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			"        RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)" +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			"        TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)" +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
				else
				{	
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)" +
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)" +
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"   AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			"        RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?)" +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"   AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			"        TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?)" +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;						
					}

				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			"        RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?) " +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			"        TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?) " +
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else
				{	
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?)" +
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;						
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?)" +
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT)) 
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL)|| (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitiveWithTrim.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchDeptName, strSearchDeptName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * (대소문자 구분, 공백문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim(String strDeptName, 
									 						 		  String strOrgID, 
									 						 		  int nType,
									 						 		  int nSearchType,
									 						 		  String strSearchValue,
								 	 						 		  int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String 		strSearchData = "";
		int 		nPageNum = 0;
		int 		nCount = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim.Empty Department Name.",
								   "");
			return -1;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
								   			"  AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			       "RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?) "+
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
								   			"  AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			       "TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?) "+
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
								   			"   AND (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			        "RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)"+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
								   			"   AND (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			        "TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)"+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
				else
				{	
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
								   			                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)"+
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
								   			                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)"+
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
								   			"   AND ( RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			"         RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
								   			"   AND ( TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			"         TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND ( RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			"         RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?) "+          
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND ( TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			"         TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?) "+          
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else
				{	
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + "( RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   			                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ? )"+ 
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + "( TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   			                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ? )"+ 
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}

		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
			}
			else
			{		
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));	
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitive(String strDeptName, 
				   					 				   String strOrgID, 
									 				   int nType,
								 	 				   int nDocsPerPage, 
								 	 				   int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
							   			"       UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?) " +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (UPPER(tUserBasic.DEPT_NAME LIKE) ? OR "+
							   			                     "  UPPER(tUserBasic.DEPT_OTHER_NAME LIKE) ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			                     "  UPPER(tUserBasic.DEPT_OTHER_NAME) = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||  (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT)) 
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchDeptName, strSearchDeptName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
		
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * (대소문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @return IUser
	 */
	private int getFirstDetectedPageByDeptNameInCaseInsensitive(String strDeptName, 
												 				String strOrgID, 
												 				int nType,
												 				int nSearchType,
												 				String strSearchValue,
											 	 				int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int			nPageNum = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return -1;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseInsensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
							   			"       UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?)" +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (UPPER(tUserBasic.DEPT_NAME LIKE) ? OR "+
							   			"                       UPPER(tUserBasic.DEPT_OTHER_NAME LIKE) ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			"        UPPER(tUserBasic.DEPT_OTHER_NAME) = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + "( UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   			"					    UPPER(tUserBasic.DEPT_OTHER_NAME) = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
			else
			{		
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));	
			}	
		}
										   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseInsensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;					
	}
	
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시, 공백문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitiveWithTrim(String strDeptName, 
									 				   		   String strOrgID, 
									 				   		   int nType,
								 	 				   		   int nDocsPerPage, 
								 	 				   		   int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitiveWithTrim.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
					
				if (strDeptName.indexOf(SEARCH_LIKE) != -1)
				{
					// replace search format
					strSearchDeptName = getSearchFormat(strDeptName);
					
					if ( nType == SEARCH_COMPANY )
					{
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
									   			"  AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
									   			"       UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?)" +
									   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
									   			"  AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
									   			"       UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?)" +
									   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}

					}
					else if ( nType == SEARCH_DEPT )
					{
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
									   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
									   			"        UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
									   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
									   			"        UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}

					}
					else
					{	
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
									   			                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?)" +
									   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
									   			                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?)" +
									   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}

					}
				}
				else
				{
					strSearchDeptName = strDeptName;
					
					if ( nType == SEARCH_COMPANY )
					{
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
									   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
									   			"        UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
									   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
									   			"        UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}

					}
					else if ( nType == SEARCH_DEPT )
					{
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
									   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
									   			"        UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
									   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
									   			"        UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?)" +
									   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
						}

					}
					else
					{	
						if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
									   			                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?) " +
									   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}
						else
						{
							strInnerFromQuery = m_strIUserWithVirtualFQuery +
									   			strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
									   			                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?) " +
									   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
						}

					}
				}

		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		if(strSortData != null || strSortData.length() > 0)
			strOrderByQuery = "   ORDER BY " + strSortData;	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   					strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					   " 		FROM ( " +
					   					m_strIUserWithVirtualSQuery +
					   					strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT)) 
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitiveWithTrim.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchDeptName, strSearchDeptName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * (대소문자 무시, 공백문자 무시)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim(String strDeptName, 
									 				   		   			String strOrgID, 
									 				   		   			int nType,
									 				   		   			int nSearchType,
									 				   		   			String strSearchValue,
								 	 				   		   			int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim.Empty Department Name.",
								   "");
			return -1;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchCondition(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"  AND ( UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
								   			"        UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?) "+
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"  AND ( UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
								   			"        UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?) "+
								   			"  AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
								   			"        UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
								   			"        UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
				else
				{		
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
								   			                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?) "+
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
								   			                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?) "+
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if ( nType == SEARCH_COMPANY )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   			        "UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
								   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   			        "UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;						
					}

				}
				else if ( nType == SEARCH_DEPT )
				{
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   			       " UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
								   			"   AND (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   			       " UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?) "+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;							
					}

				}
				else
				{		
					if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " ( UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   			                     "   UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?)"+
								   			                     "   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}
					else
					{
						strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   			strQueryConnector  + " ( UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   			                     "   UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?)"+
								   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;							
					}

				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ? " +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ? " +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE ;
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strOrderByQuery = "   ORDER BY " + strSortData;	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		} 
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, strSearchDeptName);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY ) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
			}
			else
			{		
				m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));	
			}	
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptID.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName)
	{
		return getUsersByDeptNameInCaseSensitive(strDeptName);
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 boolean bCaseSensitive,
									 boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getUsersByDeptNameInCaseSensitiveWithTrim(strDeptName);
			} else {
				return getUsersByDeptNameInCaseSensitive(strDeptName);
			}
   		} else {
			if (bTrim ==  true) {
				return getUsersByDeptNameInCaseInsensitiveWithTrim(strDeptName);
			} else {
				return getUsersByDeptNameInCaseInsensitive(strDeptName);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitive(String strDeptName)
	{
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return null;
		}
				
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " ( tUserBasic.DEPT_NAME LIKE ? OR "+
		   							                     "   tUserBasic.DEPT_OTHER_NAME LIKE ?) " +
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + " ( tUserBasic.DEPT_NAME = ? OR "+
							   		                     "   tUserBasic.DEPT_OTHER_NAME = ?)" +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		 
		strQuery = m_strIUserWithVirtualSQuery +
		           strInnerFromQuery +
		           strOrderByQuery;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));	
			}
			else
			{
				m_connectionBroker.setString(index++, strDeptName);
				m_connectionBroker.setString(index++, strDeptName);
			}
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();			
		return iUsers;				
	} 
	
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitive(String strDeptName)
	{
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 무시 
		strDeptName = strDeptName.toUpperCase();
				
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " ( UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
		   							                     "   UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?)" + 
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + " ( UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   		                     "   UPPER(tUserBasic.DEPT_OTHER_NAME) = ?)" +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		strQuery = m_strIUserWithVirtualSQuery +
						   strInnerFromQuery +
				   strOrderByQuery;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));
			}
			else
			{
				m_connectionBroker.setString(index++, strDeptName);
				m_connectionBroker.setString(index++, strDeptName);
			}
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
				
		return iUsers;				
	}
		
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시, Trim 기능 추가)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitiveWithTrim(String strDeptName)
	{
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
				
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
										                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?)" +   
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;							
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
										                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?)" +   
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
			else
			{
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   		                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   		                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;					
				}

			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		strQuery = m_strIUserWithVirtualSQuery +
				   strInnerFromQuery +
				   strOrderByQuery;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));	
			}
			else
			{
				m_connectionBroker.setString(index++, strDeptName);
				m_connectionBroker.setString(index++, strDeptName);
			}
		}
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
			
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitiveWithTrim(String strDeptName)
	{
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitiveWithTrim.Empty Department Name.",
								   "");
			return null;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
				
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
										                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)" +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;							
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
										                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)" +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
			else
			{
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   		                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   		                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;					
				}

			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		strQuery = m_strIUserWithVirtualSQuery +
				   strInnerFromQuery +
				   strOrderByQuery;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));
				m_connectionBroker.setString(index++, getSearchFormat(strDeptName));	
			}
			else
			{
				m_connectionBroker.setString(index++, strDeptName);
				m_connectionBroker.setString(index++, strDeptName);
			}
		}
				
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	
				
		return iUsers;				
	} 
		
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, int nDocsPerPage, int nCurrentPage)
	{
		return getUsersByDeptNameInCaseSensitive(strDeptName, nDocsPerPage, nCurrentPage);
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName  조직명
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, int nSearchType, String strSearchValue, int nDocsPerPage)
	{
		return getFirstDetectedPageByDeptNameInCaseSensitive(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 int nDocsPerPage, 
									 int nCurrentPage,
									 boolean bCaseSensitive,
									 boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getUsersByDeptNameInCaseSensitiveWithTrim(strDeptName, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDeptNameInCaseSensitive(strDeptName, nDocsPerPage, nCurrentPage);
			}
   		} else {
			if (bTrim ==  true) {
				return getUsersByDeptNameInCaseInsensitiveWithTrim(strDeptName, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDeptNameInCaseInsensitive(strDeptName, nDocsPerPage, nCurrentPage);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName  조직명
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, 
											  int nSearchType,
											  String strSearchValue,
									 		  int nDocsPerPage, 
									          boolean bCaseSensitive,
									          boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
			} else {
				return getFirstDetectedPageByDeptNameInCaseSensitive(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
			}
   		} else {
			if (bTrim ==  true) {
				return getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
			} else {
				return getFirstDetectedPageByDeptNameInCaseInsensitive(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitive(String strDeptName, 
									 				int nDocsPerPage, 
									 				int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " (tUserBasic.DEPT_NAME LIKE ? OR "+
		   							                     "  tUserBasic.DEPT_OTHER_NAME LIKE ?)" +
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + " (tUserBasic.DEPT_NAME = ? OR "+
							   							 "  tUserBasic.DEPT_OTHER_NAME = ?) " +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	} 
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지)
	 * @param strDeptName  조직명
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseSensitive(String strDeptName, 
												  			  int nSearchType,
												              String strSearchValue,
									 			              int nDocsPerPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchDeptName = "";
		String 		strInnerFromQuery = "";
		String 		strSearchData = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		int			nPageNum = 0;
		int			nCount = 0;
		int			index = 1;
		
		// check initial condition
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return -1;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " (tUserBasic.DEPT_NAME LIKE ? OR" +
		   							                     "  tUserBasic.DEPT_OTHER_NAME LIKE ?" +
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + " (tUserBasic.DEPT_NAME = ? OR "+
							   		                     "  tUserBasic.DEPT_OTHER_NAME = ?) " +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		if ((m_nDBType ==  ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
			   		   " FROM ( " +
			           "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
			           "	    FROM ( " + 
			   	        				m_strIUserWithVirtualSQuery +
			   	        				strInnerFromQuery +
			   	        				strOrderByQuery +
			           "	    ) vINNERSEARCH " +
			           "	) vSEARCH " +
			           " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
		} 
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
		}
		
				    				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	} 
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지)
	 * @param strDeptName  조직명
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseInsensitive(String strDeptName, 
												  			  	int nSearchType,
												              	String strSearchValue,
									 			                int nDocsPerPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strSearchDeptName = "";
		String 		strInnerFromQuery = "";
		String 		strSearchData = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		int			nPageNum = 0;
		int			nCount = 0;
		int			index = 1;
		
		// check initial condition
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.Empty Department Name.",
								   "");
			return -1;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " ( UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
		   							                     "   UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?) "+
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + "( UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   		                     "  UPPER(tUserBasic.DEPT_OTHER_NAME) = ?) " +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		} 
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		}
				    				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	} 
	
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitive(String strDeptName, 
									 				   int nDocsPerPage, 
									 				   int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
		   							strQueryConnector  + " (UPPER(tUserBasic.DEPT_NAME) LIKE ? OR "+
		   							                     "  UPPER(tUserBasic.DEPT_OTHER_NAME) LIKE ?) " + 
		    						"  AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   		strQueryConnector  + " (UPPER(tUserBasic.DEPT_NAME) = ? OR "+
							   		                     "  UPPER(tUserBasic.DEPT_OTHER_NAME) = ?) " +
							    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
		
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * (대소문자 무시, Trim 기능 추가)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseInsensitiveWithTrim(String strDeptName, 
									 				   		   int nDocsPerPage, 
									 				           int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
										                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?) " +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
										                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?) " +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
	
			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   		                     "  UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?) " +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   		                     "  UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?) " +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
	
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||(m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보  중 조건에 해당하는 사용자의 페이지.
	 * (대소문자 무시, Trim 기능 추가)
	 * @param strDeptName  조직명
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseInsensitiveWithTrim(String strDeptName, 
																		int nSearchType,
																		String strSearchValue,
									 				   		  			int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String 		strSearchData = "";
		int 		nCount = 0;
		int			nPageNum = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseInsensitive.Empty Department Name.",
								   "");
			return -1;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		strSearchData = getPageSearchCondition(nSearchType, strSearchValue, false);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.",
								   "");
			return -1;
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " ( UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) LIKE ? OR "+
										                     "   UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) LIKE ?) "+
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;							
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " ( UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) LIKE ? OR "+
										                     "   UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) LIKE ?) "+
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " ( UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL))) = ? OR "+
								   		                     "   UPPER(RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL))) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " ( UPPER(TRIM(REPLACE(tUserBasic.DEPT_NAME, ' '))) = ? OR "+
								   		                     "   UPPER(TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' '))) = ?)" +
								   		                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
		
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData; 
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		} 
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, false));
		}
				    				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;				
	}
	
	
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	private IUsers getUsersByDeptNameInCaseSensitiveWithTrim(String strDeptName, 
									 						 int nDocsPerPage, 
									 						 int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitiveWithTrim.Empty Department Name.",
								   "");
			return null;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR "+
										                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?)" +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR "+
										                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?)" +
										                     "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
			else
			{
				strSearchDeptName = strDeptName; 
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   		                     "  RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?)" +
								    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " (TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   		                     "  TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?)" +
								    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;					
				}

			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					 m_strIUserWithVirtualSQuery +
					   					 strInnerFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDeptNameInCaseSensitiveWithTrim.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchDeptName, strSearchDeptName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	} 
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName  조직명
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	private int getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim(String strDeptName, 
									 					  			  int nSearchType,
									 					  			  String strSearchValue,
									 					  			  int nDocsPerPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDeptName = "";
		String 		strQuery = "";
		String 		strOrderByQuery = "";
		String 		strQueryConnector = "";
		String		strSearchData = "";
		int 		nCount = 0;
		int 		nPageNum = 0;
		int			index = 1;
		
		if (strDeptName == null || strDeptName.length() == 0)
		{
			m_lastError.setMessage("Fail to get department name.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim.Empty Department Name.",
								   "");
			return -1;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
		
		strSearchData = getPageSearchConditionByPS(nSearchType, strSearchValue, true);
		if ((strSearchData == null) || (strSearchData.length() == 0))
		{
			m_lastError.setMessage("Fail to get invalid page search data.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitiveWithTrim.",
								   "");
			return -1;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
				
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
				
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{

			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " ( RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) LIKE ? OR " +
															 "   RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) LIKE ?) " +
															 "  AND tUserBasic.IS_DELETED = " + INOFFICE;							
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
										strQueryConnector  + " ( TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) LIKE ? OR " +
															 "   TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) LIKE ?) " +
															 "  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}

			}
			else
			{
				strSearchDeptName = strDeptName;
				
				if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " ( RTRIM(STR_REPLACE(tUserBasic.DEPT_NAME, ' ',NULL)) = ? OR "+
								   		                     "   RTRIM(STR_REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ',NULL)) = ?) "+
								    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;						
				}
				else
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
								   		strQueryConnector  + " ( TRIM(REPLACE(tUserBasic.DEPT_NAME, ' ')) = ? OR "+
								   		                     "   TRIM(REPLACE(tUserBasic.DEPT_OTHER_NAME, ' ')) = ?) "+
								    	"  AND tUserBasic.IS_DELETED = " + INOFFICE;					
				}

			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   		strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;
		}
		
		strOrderByQuery = "  ORDER BY USER_ORDER, USER_NAME";	
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", ROWNUM NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT FLOOR(MIN((NUM-1)/" + nDocsPerPage + ")) AS PAGE_NUM " +
					   " FROM ( " +
					   "	SELECT " + m_strDetailSUserColumns  + ", (ROW_NUMBER() OVER()) NUM " +
					   "	    FROM ( " + 
					   	        		m_strIUserWithVirtualSQuery +
						   				strInnerFromQuery +
						   				strOrderByQuery +
					   "	    ) vINNERSEARCH " +
					   "	) vSEARCH " +
					   " WHERE " + strSearchData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	
		{
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, strSearchDeptName);
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
		} 
		else
		{
			m_connectionBroker.setString(index++, getPageSearchValueByPS(nSearchType, strSearchValue, true));
		}

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return -1;
		}
		
		try
		{
			resultSet = m_connectionBroker.getResultSet();
			while(resultSet.next())
			{
				nPageNum = getInt(resultSet, "PAGE_NUM");	
			}	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get page number.",
								   "IUserHandler.getFirstDetectedPageByDeptNameInCaseSensitive.SQLException",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			
			return -1;			
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return nPageNum;						
	} 
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strGradeName  직급명
	 * @param strOrgID 		조직 ID
	 * @param nType  		검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 	페이지당 출력 리스트 개수 
	 * @param nCurrentPage 	현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByGradeName(String strGradeName, String strOrgID, int nType,
								 	  int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchGradeName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 0;
		String 		strInnerQuery = "";
		
		if (strGradeName == null || strGradeName.length() == 0)
		{
			m_lastError.setMessage("Fail to get grade name.",
								   "IUserHandler.getUsersByGradeName.Empty Grade Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strGradeName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strGradeName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strGradeName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchGradeName = getSearchFormat(strGradeName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (tGrade.GRADE_NAME LIKE ? OR " +
							   			"       tGrade.GRADE_OTHER_NAME LIKE ?)" +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tGrade.GRADE_NAME LIKE ? OR "+
							   			"        tGrade.GRADE_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tGrade.GRADE_NAME LIKE ? OR "+
							   								 "  tGrade.GRADE_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchGradeName = strGradeName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (tGrade.GRADE_NAME = ? OR "+
							   			"        tGrade.GRADE_OTHER_NAME = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tGrade.GRADE_NAME = ? OR "+
							   			"        tGrade.GRADE_OTHER_NAME = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tGrade.GRADE_NAME = ? OR "+
							   			                     "  tGrade.GRADE_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;		
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		
		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns;
			                   if(isUseCompOrder) strQuery += ", COMP_ORDER ";
			strQuery +=" FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchGradeName);
				m_connectionBroker.setString(index++, strSearchGradeName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchGradeName);
				m_connectionBroker.setString(index++, strSearchGradeName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByGradeName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우 
		{	
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchGradeName, strSearchGradeName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchGradeName, strSearchGradeName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 		페이지당 출력 리스트 개수 
	 * @param nCurrentPage 		현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, String strOrgID, int nType,
								 	  	 int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchPositionName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			index = 0;
		String 		strInnerQuery = "";
		
		if (strPositionName == null || strPositionName.length() == 0)
		{
			m_lastError.setMessage("Fail to get position name.",
								   "IUserHandler.getUsersByPositionName.Empty Position Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strPositionName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strPositionName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strPositionName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchPositionName = getSearchFormat(strPositionName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (tPosition.POSITION_NAME LIKE ? OR " +
							   			"       tPosition.POSITION_OTHER_NAME LIKE ?) " +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME LIKE ? OR "+
							   			"        tPosition.POSITION_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tPosition.POSITION_NAME LIKE ? OR "+
							   			                     "  tPosition.POSITION_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchPositionName = strPositionName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME = ? OR "+
							   			"        tPosition.POSITION_OTHER_NAME = ?) " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME = ? OR "+
							   			"        tPosition.POSITION_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tPosition.POSITION_NAME = ? OR "+
							   			                     "  tPosition.POSITION_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;		
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		

		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
								   	strInnerQuery + 					   					
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchPositionName);
				m_connectionBroker.setString(index++, strSearchPositionName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchPositionName);
				m_connectionBroker.setString(index++, strSearchPositionName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
								   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || ( m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByPositionName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID, strSearchPositionName, strSearchPositionName);
			else
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchPositionName, strSearchPositionName);
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
				nTotalCount = getTotalCountByPS(strInnerFromQuery, strOrgID);
			else
				nTotalCount = getTotalCount(strInnerFromQuery);
		}
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, String strOrgID, int nType)
	{
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchPositionName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			index = 0;
		
		if (strPositionName == null || strPositionName.length() == 0)
		{
			m_lastError.setMessage("Fail to get position name.",
								   "IUserHandler.getUsersByPositionName.Empty Position Name.",
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strPositionName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strPositionName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strPositionName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchPositionName = getSearchFormat(strPositionName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery =// m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"  AND (tPosition.POSITION_NAME LIKE ? OR " +
							   			"       tPosition.POSITION_OTHER_NAME LIKE ?)" +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery =// m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME LIKE ? OR "+
							   			"        tPosition.POSITION_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery =//m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + "( tPosition.POSITION_NAME LIKE ? OR "+
							   								 "  tPosition.POSITION_OTHER_NAME LIKE ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				strSearchPositionName = strPositionName;
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery =// m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME = ? OR "+
							   			"        tPosition.POSITION_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = //m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
							   			"   AND (tPosition.POSITION_NAME = ? OR "+
							   			"   	 tPosition.POSITION_OTHER_NAME = ?)"+
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else
				{		
					strInnerFromQuery =// m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tPosition.POSITION_NAME = ? OR "+
							   			                     "  tPosition.POSITION_OTHER_NAME = ?)" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = //m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;		
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = //m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = ?" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{		
				strInnerFromQuery =// m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strQuery = queryCombine(m_strIUserWithVirtualFQuery, strInnerFromQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
				m_connectionBroker.setString(index++, strSearchPositionName);
				m_connectionBroker.setString(index++, strSearchPositionName);
			}
			else
			{
				m_connectionBroker.setString(index++, strSearchPositionName);
				m_connectionBroker.setString(index++, strSearchPositionName);
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ((nType == SEARCH_COMPANY) || (nType == SEARCH_DEPT))
			{
				m_connectionBroker.setString(index++, strOrgID);
			}	
		}
								   	 			   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직무명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDutyName  직무명
	 * @param strOrgID 		조직 ID
	 * @param nType  		검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 	페이지당 출력 리스트 개수 
	 * @param nCurrentPage 	현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDutyName(String strDutyName, String strOrgID, int nType,
								 	  int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDutyName = "";
		String		strSortData = "";
		String 		strQuery = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int			nTotalCount = -1;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		String 		strInnerQuery = "";
		
		if (strDutyName == null || strDutyName.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty name.",
								   "IUserHandler.getUsersByDutyName.Empty Duty Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		// count the number of '*'
		if (strDutyName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDutyName, SEARCH_LIKE);
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}
				
		if (strDutyName.length() > 0 && nCount != strDutyName.length())	     // 모두 * 가 입력되지 않은 경우 
		{		
			if (strDutyName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDutyName = getSearchFormat(strDutyName);
				
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = '" + strOrgID + "'" +
							   			"  AND (tDuty.DUTY_NAME LIKE '" + strSearchDutyName + "' OR " +
							   			"       tDuty.DUTY_OTHER_NAME LIKE '" + strSearchDutyName + "') " +
							   			"  AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = '" + strOrgID + "'" +
							   			"   AND (tDuty.DUTY_NAME LIKE '" + strSearchDutyName +"' OR "+
							   			"        tDuty.DUTY_OTHER_NAME LIKE '" + strSearchDutyName +"') " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " (tDuty.DUTY_NAME LIKE '" + strSearchDutyName +"' OR "+
							   			                     "  tDuty.DUTY_OTHER_NAME LIKE '" + strSearchDutyName +"') " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;
				}
			}
			else
			{
				if ( nType == SEARCH_COMPANY )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.COMP_ID = '" + strOrgID + "'" +
							   			"   AND (tDuty.DUTY_NAME = '" + strDutyName +"' OR "+
							   			"        tDuty.DUTY_OTHER_NAME = '" + strDutyName +"')" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else if ( nType == SEARCH_DEPT )
				{
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " tUserBasic.DEPT_ID = '" + strOrgID + "'" +
							   			"   AND (tDuty.DUTY_NAME = '" + strDutyName +"' OR "+
							   			"        tDuty.DUTY_OTHER_NAME = '" + strDutyName +"')" +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
				else
				{		
					strInnerFromQuery = m_strIUserWithVirtualFQuery +
							   			strQueryConnector  + " ( tDuty.DUTY_NAME = '" + strDutyName +"' OR "+
							   			                     "   tDuty.DUTY_OTHER_NAME = '" + strDutyName +"') " +
							   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
				}
			}
		}
		else	// 모두 *가 입력된 경우
		{
			if ( nType == SEARCH_COMPANY )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.COMP_ID = '" + strOrgID + "'" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;		
			}
			else if ( nType == SEARCH_DEPT )
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.DEPT_ID = '" + strOrgID + "'" +
						   			"   AND tUserBasic.IS_DELETED = " + INOFFICE;	
			}
			else
			{		
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
			}	
		}
		
		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType ==  ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		bResult = m_connectionBroker.executePreparedQuery();		
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE)) 
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCount(strInnerFromQuery);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strGradeName  직급명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByGradeName(String strGradeName, int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchGradeName = "";
		String 		strQuery = "";
		String 		strSortData = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strGradeName == null || strGradeName.length() == 0)
		{
			m_lastError.setMessage("Fail to get grade name.",
								   "IUserHandler.getUsersByGradeName.Empty Grade Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strGradeName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strGradeName, SEARCH_LIKE);
		}
		
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strGradeName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchGradeName = getSearchFormat(strGradeName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tGrade.GRADE_NAME LIKE ? OR "+
						   			                     "  tGrade.GRADE_OTHER_NAME LIKE ?)" +   
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
			else
			{
				strSearchGradeName = strGradeName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tGrade.GRADE_NAME = ? OR "+
						   			                     "  tGrade.GRADE_OTHER_NAME = ?)" +
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
					   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
		}
		
		strSortData = "USER_ORDER, USER_NAME";	
		
		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
			                   m_strCompOrderColumnName +			        		   
		     		   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
										   m_strCompOrderColumnName +
		  			   " 		FROM ( " +
					   					strInnerQuery + 
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);	
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchGradeName);
			m_connectionBroker.setString(index++, strSearchGradeName);
		}
				   				   						   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByGradeName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strGradeName.length() > 0 && nCount != strGradeName.length())	     // 모두 * 가 입력되지 않은 경우
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchGradeName, strSearchGradeName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName  직위명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchPositionName = "";
		String 		strQuery = "";
		String 		strSortData = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strPositionName == null || strPositionName.length() == 0)
		{
			m_lastError.setMessage("Fail to get position name.",
								   "IUserHandler.getUsersByPositionName.Empty Position Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strPositionName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strPositionName, SEARCH_LIKE);
		}
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strPositionName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchPositionName = getSearchFormat(strPositionName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tPosition.POSITION_NAME LIKE '" + strSearchPositionName +"' OR "+
						   			                     "  tPosition.POSITION_OTHER_NAME LIKE '" + strSearchPositionName +"')" +
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
			else
			{
				strSearchPositionName = strPositionName;
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tPosition.POSITION_NAME = '" + strPositionName +"' OR "+
						   			                     "  tPosition.POSITION_OTHER_NAME = '" + strPositionName +"')" +
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
					   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
		}
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);	
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			m_connectionBroker.setString(index++, strSearchPositionName);
			m_connectionBroker.setString(index++, strSearchPositionName);
		}
				   				   						   				   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if (m_nDBType == ConnectionParam.DB_TYPE_MSSQL)
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByPositionName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (strPositionName.length() > 0 && nCount != strPositionName.length())	     // 모두 * 가 입력되지 않은 경우 
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strSearchPositionName, strSearchPositionName);
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직무명 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDutyName   직무명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDutyName(String strDutyName, int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strInnerFromQuery = "";
		String 		strSearchDutyName = "";
		String 		strQuery = "";
		String 		strSortData = "";
		String 		strQueryConnector = "";
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		String 		strInnerQuery = "";
		
		if (strDutyName == null || strDutyName.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty name.",
								   "IUserHandler.getUsersByDutyName.Empty Duty Name.",
								   "");
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQueryConnector = " AND ";	
		}
		else
		{
			strQueryConnector = " WHERE ";	
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// count the number of '*'
		if (strDutyName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDutyName, SEARCH_LIKE);
		}
		
		if (strDutyName.length() > 0 && nCount != strDutyName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDutyName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDutyName = getSearchFormat(strDutyName);
				
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tDuty.DUTY_NAME LIKE '" + strSearchDutyName +"' OR "+
						   			                     "  tDuty.DUTY_OTHER_NAME LIKE '" + strSearchDutyName +"')" +
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
			else
			{
				strInnerFromQuery = m_strIUserWithVirtualFQuery +
						   			strQueryConnector  + " (tDuty.DUTY_NAME = '" + strDutyName +"' OR "+
						   			                     "  tDuty.DUTY_OTHER_NAME = '" + strDutyName +"')" +
						    		"  AND tUserBasic.IS_DELETED = " + INOFFICE;
			}
		}
		else
		{
			strInnerFromQuery = m_strIUserWithVirtualFQuery +
					   			strQueryConnector  + " tUserBasic.IS_DELETED = " + INOFFICE;	
		}
		
		strSortData = "USER_ORDER, USER_NAME";

		strInnerQuery = queryCombine(m_strIUserWithVirtualSQuery, strInnerFromQuery, strSortData);;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +  
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					                       m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);	
		}

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyName.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCount(strInnerFromQuery);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;				
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID)
	{
		return getUsersByDutyInCaseSensitive(strDuty, strOrgID);
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음) 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, 
				                 String strOrgID, 
								 boolean bCaseSensitive,
								 boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getUsersByDutyInCaseSensitiveWithTrim(strDuty, strOrgID);
			} else {
				return getUsersByDutyInCaseSensitive(strDuty, strOrgID);
			}
   		} else {
			if (bTrim ==  true) {
				return getUsersByDutyInCaseInsensitiveWithTrim(strDuty, strOrgID);
			} else {
				return getUsersByDutyInCaseInsensitive(strDuty, strOrgID);
			}
		}
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID, int nDocsPerPage, int nCurrentPage)
	{
		return getUsersByDutyInCaseSensitive(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지  
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID, 
								 boolean bCaseSensitive, boolean bTrim,
								 int nDocsPerPage, int nCurrentPage)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getUsersByDutyInCaseSensitiveWithTrim(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDutyInCaseSensitive(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
			}
   		} else {
			if (bTrim ==  true) {
				return getUsersByDutyInCaseInsensitiveWithTrim(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
			} else {
				return getUsersByDutyInCaseInsensitive(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
			}
		}
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 (대소문자 무시, 공백 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseInsensitiveWithTrim(String strDuty, String strOrgID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String 	strQuery = "";
		String	strSelectQuery = "";
		String  strSortData = "";
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    	nCount  = 0;
		int		index = 1; 
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseInsensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// 대소문자 문시 
		strDuty = strDuty.toUpperCase();
		// Space 제거 
		strDuty = strDuty.trim();
		strDuty = DataConverter.replace(strDuty, " ", "");
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		strSelectQuery = "SELECT " + m_strDetailUserColumns;
					
		if (nSearchType == 0)
		{ 
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE UPPER(RTRIM(STR_REPLACE(DUTY, ' ',NULL))) = ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE UPPER(TRIM(REPLACE(DUTY, ' '))) = ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}

		}
		else if (nSearchType == 2)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}
		else 
		{
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE UPPER(RTRIM(STR_REPLACE(DUTY, ' ',NULL))) LIKE ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE UPPER(TRIM(REPLACE(DUTY, ' '))) LIKE ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}

					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}
		
		strQuery = queryCombine(strSelectQuery, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 (대소문자 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseInsensitive(String strDuty, String strOrgID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String 	strQuery = "";
		String  strSelectQuery = "";
		String  strSortData = "";
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    	nCount  = 0;
		int		index = 1;
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseInsensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// 대소문자 문시 
		strDuty = strDuty.toUpperCase();
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// query를 만드는 작업
		if (nSearchType == 0)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE UPPER(DUTY) = ?" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;
		}
		else 
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE UPPER(DUTY) LIKE ?" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		//2009.07.08
		strQuery = queryCombine(strSelectQuery, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보. (공백문자 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseSensitiveWithTrim(String strDuty, String strOrgID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String 	strQuery = "";
		String  strSelectQuery = "";
		String  strSortData = "";
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    	nCount  = 0;
		int		index = 1;
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseSensitiveWithTrim.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// Space 제거 
		strDuty = strDuty.trim();
		strDuty = DataConverter.replace(strDuty, " ", "");
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		strSelectQuery = "SELECT " + m_strDetailUserColumns;

		if (nSearchType == 0)
		{ 
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE RTRIM(STR_REPLACE(DUTY, ' ',NULL)) = ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strQuery = //"SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE TRIM(REPLACE(DUTY, ' ')) = ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}

		}
		else if (nSearchType == 2)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}
		else 
		{
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strQuery =// "SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE RTRIM(STR_REPLACE(DUTY, ' ',NULL)) LIKE ?" +
						   "   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strQuery =// "SELECT " + m_strDetailUserColumns +
						   " FROM " + m_strDetailUserTable +
						   " WHERE TRIM(REPLACE(DUTY, ' ')) LIKE ?" +
						   "   AND IS_DELETED = " + INOFFICE;				
			}

					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}

		strQuery = queryCombine(strSelectQuery, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseSensitive(String strDuty, String strOrgID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String 	strQuery = "";
		String  strSortData = "";
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    	nCount  = 0;
		int		index = 1;

		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseSensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		if (nSearchType == 0)
		{ 
			strQuery =// "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE DUTY = ?" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strQuery =// "SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}
		else 
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE DUTY LIKE ?" +
					   "   AND IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}

		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 (대소문자 무시, 공백 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseInsensitiveWithTrim(String strDuty, String strOrgID, 
														   int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		IUsers 		iUsers = null;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSortData = "";
		int 		nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    		nCount  = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseInsensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// 대소문자 문시 
		strDuty = strDuty.toUpperCase();
		// Space 제거 
		strDuty = strDuty.trim();
		strDuty = DataConverter.replace(strDuty, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		if (nSearchType == 0)
		{ 
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
								    " WHERE UPPER(RTRIM(STR_REPLACE(DUTY, ' ',NULL))) = ?" +
								    "   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
								    " WHERE UPPER(TRIM(REPLACE(DUTY, ' '))) = ?" +
								    "   AND IS_DELETED = " + INOFFICE;					
			}

		}
		else if (nSearchType == 2)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE IS_DELETED = " + INOFFICE;			
		}
		else 
		{
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE UPPER(RTRIM(STR_REPLACE(DUTY, ' ',NULL))) LIKE ?" +
						   			"   AND IS_DELETED = " + INOFFICE;						
			}
			else
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE UPPER(TRIM(REPLACE(DUTY, ' '))) LIKE ?" +
						   			"   AND IS_DELETED = " + INOFFICE;					
			}
	
		}

		strInnerQuery = queryCombine("SELECT " + m_strDetailUserColumns, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +  
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  
					                       m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyInCaseInsensitiveWithTrim.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (nSearchType == 0)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strDuty);
		else if (nSearchType == 1)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, getSearchFormat(strDuty));
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 (대소문자 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseInsensitive(String strDuty, String strOrgID,
												   int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		IUsers 		iUsers = null;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSortData = "";
		int 		nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    		nCount  = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseInsensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// 대소문자 문시 
		strDuty = strDuty.toUpperCase();
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		if (nSearchType == 0)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
							    " WHERE UPPER(DUTY) = ?" +
							    "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
							    " WHERE IS_DELETED = " + INOFFICE;
		}
		else 
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
							    " WHERE UPPER(DUTY) LIKE ?" +
							    "   AND IS_DELETED = " + INOFFICE;			
		}
		
		strInnerQuery = queryCombine("SELECT " + m_strDetailUserColumns, strInnerFromQuery, strSortData);

		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + 
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					                       m_strCompOrderColumnName + 
					   					   strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (nSearchType == 0)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strDuty);
		else if (nSearchType == 1)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, getSearchFormat(strDuty));
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보. (공백문자 무시)
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseSensitiveWithTrim(String strDuty, String strOrgID,
														 int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		IUsers 		iUsers = null;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSortData = "";
		int 		nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    		nCount  = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseSensitiveWithTrim.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		// Space 제거 
		strDuty = strDuty.trim();
		strDuty = DataConverter.replace(strDuty, " ", "");
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업

		if (nSearchType == 0)
		{ 
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
									" WHERE RTRIM(STR_REPLACE(DUTY, ' ')) = ?" +
									"   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
									" WHERE TRIM(REPLACE(DUTY, ' ')) = ?" +
									"   AND IS_DELETED = " + INOFFICE;					
			}

		}
		else if (nSearchType == 2)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE IS_DELETED = " + INOFFICE;
		}
		else 
		{
			if(m_nDBType == ConnectionParam.DB_TYPE_SYBASE)
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE RTRIM(STR_REPLACE(DUTY, ' ')) LIKE ?" +
						   			"   AND IS_DELETED = " + INOFFICE;					
			}
			else
			{
				strInnerFromQuery = " FROM " + m_strDetailUserTable +
						   			" WHERE TRIM(REPLACE(DUTY, ' ')) LIKE ?" +
						   			"   AND IS_DELETED = " + INOFFICE;				
			}
		}

		strInnerQuery = queryCombine("SELECT " + m_strDetailUserColumns, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					                       m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyInCaseSensitiveWithTrim.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (nSearchType == 0)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strDuty);
		else if (nSearchType == 1)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, getSearchFormat(strDuty));
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	private IUsers getUsersByDutyInCaseSensitive(String strDuty, String strOrgID,
												int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		IUsers 		iUsers = null;
		String 		strInnerFromQuery = "";
		String 		strQuery = "";
		String 		strSortData = "";
		int 		nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    		nCount  = 0;
		int			nMin = 0;
		int			nMax = 0;
		int			nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strDuty == null || strDuty.length() == 0)
		{
			m_lastError.setMessage("Fail to get duty information.",
								   "IUserHandler.getUsersByDutyInCaseSensitive.Empty duty information.",
								   "");
			return iUsers;				
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strDuty, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strDuty.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		
		if (nSearchType == 0)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
							    " WHERE DUTY = ?" +
							    "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE IS_DELETED = " + INOFFICE;			
		}
		else 
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE DUTY LIKE ?" +
					   			"   AND IS_DELETED = " + INOFFICE;			
		}
		
		strInnerQuery = queryCombine("SELECT " + m_strDetailUserColumns, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
									   strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
			
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + 
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					                       m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " 
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strDuty);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strDuty));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);				
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByDutyInCaseSensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		if (nSearchType == 0)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strDuty);
		else if (nSearchType == 1)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, getSearchFormat(strDuty));
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 핸드폰 번호를 가지는 사용자 정보. 
	 * @param strMobile 핸드폰 번호
	 * @param strOrgID 조직 코드 
	 * @return IUsers
	 */
	public IUsers getUsersByMobile(String strMobile, String strOrgID)
	{
		boolean bResult = false;
		IUsers 	iUsers = null;
		String 	strQuery = "";
		String  strSortData = "";
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    	nCount  = 0;
		int		index = 1;
		
		if (strMobile == null || strMobile.length() == 0)
		{
			m_lastError.setMessage("Fail to get mobile number information.",
								   "IUserHandler.getUsersByMobile.Empty mobile number information.",
								   "");
			return iUsers;				
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strMobile, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strMobile.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// query를 만드는 작업
		if (nSearchType == 0)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE MOBILE = ?" +
					   "   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}
		}
		else 
		{
			strQuery = //"SELECT " + m_strDetailUserColumns +
					   " FROM " + m_strDetailUserTable +
					   " WHERE MOBILE LIKE ?" +
					   "   AND IS_DELETED = " + INOFFICE;
					   
//			if (strSortData != null && strSortData.length() > 0)
//			{
//				strQuery += " ORDER BY " + strSortData;
//			}			
		}

		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, strSortData);
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}

				
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strMobile);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strMobile));

		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;
	}
	
	/**
	 * 주어진 핸드폰 번호를 소유한 사용자를 검색하는 함수.
	 * @param strMobile 핸드폰 번호
	 * @param strOrgID  조직코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nCurrentPage 현재 출력 페이지 
	 * @return IUsers
	 */
	public IUsers getUsersByMobile(String strMobile, String strOrgID,
	                               int nDocsPerPage, int nCurrentPage)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		IUsers 		iUsers = null;
		String 		strInnerSelectQuery = "";
		String 		strInnerFromQuery = "";
		String 		strSortData = "";
		String 		strQuery = "";
		int 		nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int    		nCount  = 0;
		int     	nMin = 0;
		int			nMax = 0;
		int 		nTotalCount = -1;
		int			index = 1;
		String 		strInnerQuery = "";
		
		if (strMobile == null || strMobile.length() == 0)
		{
			m_lastError.setMessage("Fail to get mobile number information.",
								   "IUserHandler.getUsersByMobile.Empty mobile number information.",
								   "");
			return iUsers;				
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strMobile, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strMobile.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// query를 만드는 작업		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		
		if (nSearchType == 0)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE MOBILE = ?" +
					   			"   AND IS_DELETED = " + INOFFICE;
		}
		else if (nSearchType == 2)
		{ 
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE IS_DELETED = " + INOFFICE;
		}
		else 
		{
			strInnerFromQuery = " FROM " + m_strDetailUserTable +
					   			" WHERE MOBILE LIKE ?" +
					   			"   AND IS_DELETED = " + INOFFICE;			
		}
		
		if (nSearchType != 0)
		{
			// 조직 Sorting 옵션을 가져오는 함수
			strSortData = getSortData(strOrgID);
		}
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;

		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +  m_strCompOrderColumnName + 
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		} 
		
		
		if (nSearchType == 0)
			m_connectionBroker.setString(index++, strMobile);
		else if (nSearchType == 1)
			m_connectionBroker.setString(index++, getSearchFormat(strMobile));
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet != null)
			{
				if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
				{
					if (nCurrentPage > 0 && resultSet != null)
						resultSet.absolute(nCurrentPage * nDocsPerPage);						
				}
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByMobile.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;			
		}
		
		iUsers = processData(resultSet);
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		m_connectionBroker.clearPreparedQuery();
		
		if (nSearchType == 0)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, strMobile);
		else if (nSearchType == 1)
			nTotalCount = getTotalCountByPS(strInnerFromQuery, getSearchFormat(strMobile));
		else
			nTotalCount = getTotalCount(strInnerFromQuery);
		
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수.
	 * @param strOrgID       	사용자 소속 부서 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUsers getUsersByCondition(String 	strOrgID,
									  boolean 	bIncludeVirtual,
	                                  int  		nColumnType, 
	                                  String 	strColumnValue,
	                                  int		nType)
	{
		boolean bResult = false;
		String 	strSortData = "";
		String 	strQuery = "";
		IUsers 	iUsers = null;
		int 	nCount = 0;
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int		nDataType = UserDetailViewTableMap.STRING;
		
		if (nColumnType <0)
		{
			m_lastError.setMessage("Fail to get column type.",
								   "IUserHandler.getUsersByCondition.Empty column type.",
								   "");
			return iUsers;				
		}
		
		if (strColumnValue == null || strColumnValue.length() == 0)
		{
			m_lastError.setMessage("Fail to get column value.",
								   "IUserHandler.getUsersByCondition.Empty column value.",
								   "");
			return iUsers;				
		}
			
		// DataType 검색
		nDataType = UserDetailViewTableMap.getDataType(nColumnType);
		if (nDataType != UserDetailViewTableMap.STRING && nDataType != UserDetailViewTableMap.INTEGER)
		{
			m_lastError.setMessage("Fail to get correct data type.",
								   "IUserHandler.getUsersByCondition.incorrect data column type.",
								   "");
			return iUsers;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strColumnValue, SEARCH_LIKE);
		if (nCount == 0)							// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strColumnValue.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else										// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		// 검색 조건 check
		if (nDataType == UserDetailViewTableMap.INTEGER && nSearchType == 1)
		{
			m_lastError.setMessage("Fail to get correct search condition.",
								   "IUserHandler.getUsersByCondition.incorrect search condition.",
								   "");
			return iUsers;		
		}

		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// 검색 query를 생성하는 작업
		if (bIncludeVirtual == true)		// 가상 사용자를 검색에 포함하는 경우
		{
			strQuery = m_strIUserWithVirtualSQuery +
					   m_strIUserWithVirtualFQuery +
					   " AND IS_DELETED = " + INOFFICE;	
		}
		else								// 가상 사용자를 검색에 포함하지 않는 경우
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   "  FROM " + m_strDetailUserTable+
					   " WHERE IS_DELETED = " + INOFFICE;			
		}
		
		if (nSearchType == 0)			// SearchType 0 : equal 검색을 하는 경우	
		{
			if (nDataType == UserDetailViewTableMap.STRING)
			{
				strQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      "='" + strColumnValue + "'";
			}
			else 
			{
				strQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      "=" + strColumnValue;	
			}	
		}
		else if (nSearchType == 1)	   // SearchType 1 : like 검색을 하는 경우
		{
			if (nDataType == UserDetailViewTableMap.STRING)
			{
				strQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      " LIKE '" + getSearchFormat(strColumnValue) + "'";
			}
		}
		
		// 검색 범위 설정
		if (bIncludeVirtual == false)
		{
			if (nType == SEARCH_COMPANY)
			{
				strQuery += " AND COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strQuery += " AND DEPT_ID = '" + strOrgID + "'";
			}
		}
		else
		{
			if (nType == SEARCH_COMPANY)
			{
				strQuery += " AND tUserBasic.COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strQuery += " AND tUserBasic.DEPT_ID = '" + strOrgID + "'";
			}	
		}
		
		if (strSortData != null && strSortData.length() > 0)
		{
			strQuery += " ORDER BY " + strSortData;
		}

		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수.
	 * @param strOrgID       	사용자 소속 부서 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 		페이지당 출력 리스트 개수 
	 * @param nCurrentPage 		현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByCondition(String 	strOrgID,
									  boolean 	bIncludeVirtual,
	                                  int  		nColumnType, 
	                                  String 	strColumnValue,
	                                  int 		nDocsPerPage,
	                                  int 		nCurrentPage,
	                                  int		nType)
	{
		boolean bResult = false;
		String 	strSortData = "";
		String 	strSelectQuery = "";
		String 	strFromQuery = "";
		String 	strOrderByQuery = "";
		String 	strQuery = "";
		IUsers 	iUsers = null;
		int 	nCount = 0;
		int 	nSearchType = 0;	// 0 : equal 검색, 1 : like 검색, 2 : 전체 검색
		int		nDataType = UserDetailViewTableMap.STRING;
		int 	nMin = 0;
		int     nMax = 0;
		int		nTotalCount = -1;
		
		if (nColumnType <0)
		{
			m_lastError.setMessage("Fail to get column type.",
								   "IUserHandler.getUsersByCondition.Empty column type.",
								   "");
			return iUsers;				
		}
		
		if (strColumnValue == null || strColumnValue.length() == 0)
		{
			m_lastError.setMessage("Fail to get column value.",
								   "IUserHandler.getUsersByCondition.Empty column value.",
								   "");
			return iUsers;				
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
			
		// DataType 검색
		nDataType = UserDetailViewTableMap.getDataType(nColumnType);
		if (nDataType != UserDetailViewTableMap.STRING && nDataType != UserDetailViewTableMap.INTEGER)
		{
			m_lastError.setMessage("Fail to get correct data type.",
								   "IUserHandler.getUsersByCondition.incorrect data column type.",
								   "");
			return iUsers;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strColumnValue, SEARCH_LIKE);
		if (nCount == 0)							// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strColumnValue.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else										// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		// 검색 조건 check
		if (nDataType == UserDetailViewTableMap.INTEGER && nSearchType == 1)
		{
			m_lastError.setMessage("Fail to get correct search condition.",
								   "IUserHandler.getUsersByCondition.incorrect search condition.",
								   "");
			return iUsers;		
		}

		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 조직 Sorting 옵션을 가져오는 함수
		strSortData = getSortData(strOrgID);
		
		// 검색 query를 생성하는 작업
		if (bIncludeVirtual == true)		// 가상 사용자를 검색에 포함하는 경우
		{
			strSelectQuery = m_strIUserWithVirtualSQuery;
			strFromQuery = m_strIUserWithVirtualFQuery +
					   	   " AND IS_DELETED = " + INOFFICE;	
		}
		else								// 가상 사용자를 검색에 포함하지 않는 경우
		{
			strSelectQuery = "SELECT " + m_strDetailUserColumns;
			strFromQuery = "  FROM " + m_strDetailUserTable+
					   		" WHERE IS_DELETED = " + INOFFICE;			
		}
		
		if (nSearchType == 0)			// SearchType 0 : equal 검색을 하는 경우	
		{
			if (nDataType == UserDetailViewTableMap.STRING)
			{
				strFromQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      "='" + strColumnValue + "'";
			}
			else 
			{
				strFromQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      "=" + strColumnValue;	
			}	
		}
		else if (nSearchType == 1)	   // SearchType 1 : like 검색을 하는 경우
		{
			if (nDataType == UserDetailViewTableMap.STRING)
			{
				strFromQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
				                      " LIKE '" + getSearchFormat(strColumnValue) + "'";
			}
		}
		
		// 검색 범위 설정
		if (bIncludeVirtual == false)
		{
			if (nType == SEARCH_COMPANY)
			{
				strFromQuery += " AND COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strFromQuery += " AND DEPT_ID = '" + strOrgID + "'";
			}
		}
		else
		{
			if (nType == SEARCH_COMPANY)
			{
				strFromQuery += " AND tUserBasic.COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strFromQuery += " AND tUserBasic.DEPT_ID = '" + strOrgID + "'";
			}	
		}
		
		if (strSortData != null && strSortData.length() > 0)
		{
			strOrderByQuery += " ORDER BY " + strSortData;
		}
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage; 
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
					   " 		FROM ( " +
					   					 strSelectQuery +
					   					 strFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
					   " 		FROM ( " +
					   					 strSelectQuery +
					   					 strFromQuery +
					   					 strOrderByQuery +
					   "			 ) vINNERSERACH " +
					   " 		FETCH FIRST " + nMax + " ROW ONLY" +
					   "      ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL)  || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns +
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns +
					   " 		FROM ( " +
					   					 strSelectQuery +
					   					 strFromQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH " +
					   strOrderByQuery;		
		}
		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCount(strFromQuery);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;	
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수(단 조건이 Key인 경우).
	 * @param strOrgID       	사용자 소속 조직 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUser getUserByKey(String 	strOrgID,
							  boolean 	bIncludeVirtual,
	                          int  		nColumnType, 
	                          String 	strColumnValue,
	                          int		nType)
	{
		boolean bResult = false;
		String 	strQuery = "";
		IUsers 	iUsers = null;
		IUser	iUser = null;
		int 	nCount = 0;
		int		nDataType = UserDetailViewTableMap.STRING;
		
		if (nColumnType <0)
		{
			m_lastError.setMessage("Fail to get column type.",
								   "IUserHandler.getUserByKey.Empty column type.",
								   "");
			return iUser;				
		}
		
		if (strColumnValue == null || strColumnValue.length() == 0)
		{
			m_lastError.setMessage("Fail to get column value.",
								   "IUserHandler.getUserByKey.Empty column value.",
								   "");
			return iUser;				
		}
			
		// DataType 검색
		nDataType = UserDetailViewTableMap.getDataType(nColumnType);
		if (nDataType != UserDetailViewTableMap.STRING && nDataType != UserDetailViewTableMap.INTEGER)
		{
			m_lastError.setMessage("Fail to get correct data type.",
								   "IUserHandler.getUserByKey.incorrect data column type.",
								   "");
			return iUser;	
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strColumnValue, SEARCH_LIKE);
		if (nCount > 0)								
		{
			m_lastError.setMessage("Fail to get correct search value.",
								   "IUserHandler.getUserByKey.incorrect search value.",
								   "");
			return iUser;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		// 검색 query를 생성하는 작업
		if (bIncludeVirtual == true)		// 가상 사용자를 검색에 포함하는 경우
		{
			strQuery = m_strIUserWithVirtualSQuery +
					   m_strIUserWithVirtualFQuery +
					   " AND IS_DELETED = " + INOFFICE;	
		}
		else								// 가상 사용자를 검색에 포함하지 않는 경우
		{
			strQuery = "SELECT " + m_strDetailUserColumns +
					   "  FROM " + m_strDetailUserTable+
					   " WHERE IS_DELETED = " + INOFFICE;			
		}

		if (nDataType == UserDetailViewTableMap.STRING)
		{
			strQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
			                      "='" + strColumnValue + "'";
		}
		else 
		{
			strQuery += " AND " + UserDetailViewTableMap.getColumnName(nColumnType) + 
			                      "=" + strColumnValue;	
		}
		
		// 검색 범위 설정
		if (bIncludeVirtual == false)
		{
			if (nType == SEARCH_COMPANY)
			{
				strQuery += " AND COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strQuery += " AND DEPT_ID = '" + strOrgID + "'";
			}
		}
		else
		{
			if (nType == SEARCH_COMPANY)
			{
				strQuery += " AND tUserBasic.COMP_ID = '" + strOrgID + "'";
			}
			else if (nType == SEARCH_DEPT)
			{
				strQuery += " AND tUserBasic.DEPT_ID = '" + strOrgID + "'";
			}	
		}	

		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return iUser;
		}
			
		m_connectionBroker.clearConnectionBroker();	
		
		if (iUsers.size() != 1)
		{
			m_lastError.setMessage("Fail to get key search value.",
								   "IUserHandler.getUserByKey.incorrect key search value.(not unique)",
								   "");
			return iUser;	
		}
		
		iUser = iUsers.get(0);
		return iUser;	
	}
	
	/**
	 * 모든 사용자 정보를 가져오는 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @return IUsers
	 */
	public IUsers getAllUsers()
	{
		boolean bResult = false;
		String strQuery = "";
		IUsers iUsers = null;
		
		bResult = m_connectionBroker.openConnection();
		if	(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDetailUserColumns + 
		           " FROM " + m_strDetailUserTable + 
		           " WHERE IS_DELETED = " + INOFFICE;

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if(iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		} 
		else
		{
			m_connectionBroker.clearConnectionBroker();
			return iUsers;
		}
	}
	
	/**
	 * 사용자 일부 정보를 수정.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strTable 테이블 명
	 * @param strColumn 수정하고자 하는 컬럼
	 * @param strUserUID 사용자 UID
	 * @param strValue 수정하고자 하는 컬럼 값
	 * @return boolean
	 */
	public boolean updateIUserSpecificInfo(String strTable, String strColumn, String strUserUID, String strValue)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String strQuery = "";
		int nResult = -1;
	
		bResult = m_connectionBroker.openConnection();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
	
		m_connectionBroker.setAutoCommit(false);
		strQuery = "UPDATE " + strTable + 
				   " SET " + strColumn + " = '" + strValue + "'" + 
				   " WHERE USER_UID = '" + strUserUID + "'";
				   
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
	
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();
		
		bReturn = true;
		return bReturn;
	}
	
	/**
	 * 문서관리 부서 맵핑 관련 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param arrCol[] 반환하고자 하는 속성명
	 * @param strCompID 회사 ID
	 * @return boolean
	 */
	public String[][] getEBHUserInfo(String arrCol[], String strCompID)
	{
		ResultSet resultSet = null;
		int nTotalCount = 0;
		int nColCount = 0;
		String arrUserInfo[][] = null;
		boolean bResult = false;
		String strQuery = "";
		
		if ((arrCol == null) || (arrCol.length == 0))
		{
			m_lastError.setMessage("Fail to get column data.", 
							       "IUserHandler.getEBHUserInfo.Empty Column Data", 
							       "");
			return null;
		}
		
		nColCount = arrCol.length;
		bResult = m_connectionBroker.openConnection();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT COUNT(*)  " +
		           "  FROM " + m_strDetailUserTable + 
		           " WHERE GROUP_ID = '" + strCompID + "'";
		           
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if(resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{ 
				nTotalCount = resultSet.getInt(1);
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get User Count.", 
								   "IUserHandler.getEBHUserInfo.SQLException", 
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		finally
		{
			m_connectionBroker.clearQuery();
		}
		
		if (nTotalCount == 0)
		{
			m_lastError.setMessage("Fail to get User Information.", 
			                       "IUserHandler.getUserInfo.Zero Total Count", 
			                       "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT ";
		
		for (int i = 0; i < nColCount; i++)
		{
			strQuery += arrCol[i];
			if(i != nColCount - 1)
				strQuery += ", ";
		}

		strQuery += " FROM " + m_strDetailUserTable + 
					" WHERE GROUP_ID = '" + strCompID + "'";
					
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if(resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		try
		{
			int nRow = 0;
			arrUserInfo = new String[nTotalCount][nColCount];
			while (resultSet.next()) 
			{
				for (int i = 0; i < nColCount; i++)
					arrUserInfo[nRow][i] = resultSet.getString(i + 1);

				nRow++;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get User Information.", 
			                       "IUserHandler.getEBHUserInfo", 
			                       e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		return arrUserInfo;
	}
	
	/**
	 * 온라인회의 참석자, 열람자 유효성 확인.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strDeptID 부서 ID
	 * @param strUserID 사용자 ID
	 * @return boolean
	 */
	public boolean isValidInfo(String strDeptID, String strUserID)
	{
		boolean bResult = false;
		String strQuery = "";
		ResultSet resultSet = null;
		int nTotalCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		strQuery = "SELECT COUNT(*) FROM " + m_strDetailUserTable + 
		           " WHERE USER_ID = '" + strUserID + "'" +
		           "   AND DEPT_ID = '" + strDeptID + "'" +
		           "   AND IS_DELETED = " + INOFFICE;
		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if(resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		try
		{
			while(resultSet.next())
			{ 
				nTotalCount = resultSet.getInt(1);
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get User Information.", 
								   "IUserHandler.isValidInfo.SQLException", 
								   e.getMessage());
			return false;
		}
		finally 
		{
			m_connectionBroker.clearConnectionBroker();	
		}
		
		if (nTotalCount == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * 온라인보고 보고경로그룹 유효성 확인 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strDeptID 부서 ID
	 * @param strUserID 사용자 ID
	 * @param strUserUID 사용자 UID
	 * @param strCode 직위 or 직급 코드
	 * @return boolean
	 */
	public boolean isValidInfo4Dac(String strDeptID, String strUserID, String strUserUID, String strCode)
	{
		boolean bResult = false;
		String strQuery = "";
		ResultSet resultSet = null;
		int nTotalCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		strQuery = "SELECT COUNT(*) " +
		           "  FROM " + m_strDetailUserTable +
		           " WHERE DEPT_ID = '" + strDeptID + "'" +
				   "   AND USER_ID = '" + strUserID + "'" +
				   "   AND USER_UID = '" + strUserUID + "'" +
				   "   AND IS_DELETED = " + INOFFICE +
				   "   AND ( POSITION_CODE = '" + strCode + "'  OR GRADE_CODE = '" + strCode +"')"; 
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if(resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return false;
		}
		
		try
		{
			while(resultSet.next())
			{ 
				nTotalCount = resultSet.getInt(1);
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get User Information.", 
			                       "IUserHandler.isValidInfo4Dac.SQLException", 
			                       e.getMessage());	
			return false;
		}
		finally 
		{
			m_connectionBroker.clearConnectionBroker();
		}

		if (nTotalCount == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * 주어진 Process ID를 가지는 사용자 정보
	 * @param strProleID 프로세스 역할 ID
	 * @return IUsers
	 */
	public IUsers getUsersByProleID(String strProleID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strProleTable = TableDefinition.getTableName(TableDefinition.USER_PROLE);
		IUsers 		iUsers = null;

		if ((strProleID == null) || (strProleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get process role ID.", 
							       "IUserHandler.getUsersByProleID.Empty ProcessRole ID", 
							       "");	
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
			
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + " ," + strProleTable + 
				   " WHERE " + m_strDetailUserTable + ".USER_UID = " + strProleTable + ".USER_ID " +
				   "   AND " + strProleTable + ".PROLE_ID = '" + strProleID + "'" +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   " ORDER BY " + m_strDetailUserTable + ".USER_NAME, " +
				   				  m_strDetailUserTable + ".GRADE_ORDER, " +
				   				  m_strDetailUserTable + ".POSITION_ORDER ";
				 				   		   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 Process ID를 가지는 사용자 정보
	 * @param strProleID 프로세스 역할 ID
	 * @param strDeptID 부서 ID  
	 * @return IUsers
	 */
	public IUsers getUsersByProleID(String strProleID, String strDeptID)
	{
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strProleTable = TableDefinition.getTableName(TableDefinition.USER_PROLE);
		IUsers 		iUsers = null;

		if ((strProleID == null) || (strProleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get process role ID.", 
							       "IUserHandler.getUsersByProleID.Empty ProcessRole ID", 
							       "");	
			return null;
		}
		
		if ((strDeptID == null) || (strDeptID.length() == 0))
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "IUserHandler.getUsersByProleID.Emtpy Department ID.",
								   "");
			return null;						
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		strQuery = "SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable + " ," + strProleTable + 
				   " WHERE " + m_strDetailUserTable + ".USER_UID = " + strProleTable + ".USER_ID " +
				   "   AND " + strProleTable + ".PROLE_ID = '" + strProleID + "'" +
				   "   AND " + m_strDetailUserTable + ".DEPT_ID = '" + strDeptID + "'" +
				   "   AND " + m_strDetailUserTable + ".IS_DELETED = " + INOFFICE +
				   " ORDER BY " + m_strDetailUserTable + ".USER_NAME, " +
				   				  m_strDetailUserTable + ".GRADE_ORDER, " +
				   				  m_strDetailUserTable + ".POSITION_ORDER ";
				 				   		   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;
	}
	
	/**
	 * 주어진 전화 번호와 관련된 사용자 정보 
	 * @param strPhoneNumber 전화 번호
	 * @param bIncludeOfficeTel 회사 전화 번호 검색 포함 여부 
	 * @param bIncludeMobile 핸드폰 번호 검색 포함 여부
	 * @param bIncludeHomeTel 집전화번호 검색 포함 여부
	 * @return IUsers
	 */
	public IUsers getUsersByRelatedPhone(String strPhoneNumber, boolean bIncludeOfficeTel,
									     boolean bIncludeMobile, boolean bIncludeHomeTel)
	{
		boolean 	bResult = false;
		boolean 	bExistCondition = false;
		String 		strQuery = "";
		String		strSortData = "";
		String 		strSearchPhoneNumber = "";
		String 		strWhereCondition = ""; 
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int 		nSearchType = 0;
		
		// check initial condition
		if ((strPhoneNumber == null) || (strPhoneNumber.length() == 0))
		{
			m_lastError.setMessage("Fail to get phone number.", 
							       "IUserHandler.getUsersByRelatedPhone.Empty Phone ID", 
							       "");	
			return null;
		}
		
		if ((bIncludeOfficeTel == false) && (bIncludeMobile == false) &&
		    (bIncludeHomeTel == false))
		{
			m_lastError.setMessage("Fail to get phone number.", 
							       "IUserHandler.getUsersByRelatedPhone.Empty Phone Condition", 
							       "");		
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strPhoneNumber, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strPhoneNumber.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		strSearchPhoneNumber = getSearchFormat(strPhoneNumber);
		
		LinkedList conditionList = new LinkedList();
		if (bIncludeOfficeTel == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2));
		}
		if (bIncludeMobile == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2));
		}
		if (bIncludeHomeTel == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2));	
		}	
		for (int i = 0; i < conditionList.size(); i++)
		{
			if (nSearchType == 2)		// 전체 검색인 경우 where 조건을 제거
				continue;
				
			String strColumn = (String) conditionList.get(i);
				
			if (bExistCondition == false) 
			{
				strWhereCondition += " WHERE ";
				bExistCondition = true;
			}
			else
			{
				strWhereCondition += " OR ";
			}
			
			if (nSearchType == 1)			
				strWhereCondition += strColumn  + " LIKE '" + strSearchPhoneNumber + "'";
			else
				strWhereCondition += strColumn  + " = '" + strSearchPhoneNumber + "'";
				
		}

		strQuery = //"SELECT " + m_strDetailUserColumns +
				   " FROM " + m_strDetailUserTable +
				   strWhereCondition;
				   //"  ORDER BY USER_ORDER, USER_NAME";	
		
		strSortData = "USER_ORDER, USER_NAME"; 
		
		strQuery = queryCombine("SELECT " + m_strDetailUserColumns, strQuery, strSortData);;

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
			
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * 주어진 전화 번호와 관련된 사용자 정보 
	 * @param strPhoneNumber 전화 번호
	 * @param bIncludeOfficeTel 회사 전화 번호 검색 포함 여부 
	 * @param bIncludeMobile 핸드폰 번호 검색 포함 여부
	 * @param bIncludeHomeTel 집전화번호 검색 포함 여부
	 * @param nDocsPerPage 페이지 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByRelatedPhone(String strPhoneNumber, boolean bIncludeOfficeTel,
									     boolean bIncludeMobile, boolean bIncludeHomeTel,
									     int nDocsPerPage, int nCurrentPage)
	{
		ResultSet	resultSet = null;
		boolean 	bResult = false;
		boolean 	bExistCondition = false;
		String 		strQuery = "";
		String		strInnerSelectQuery = "";
		String		strInnerFromQuery = "";
		String		strSortData = "";
		String		strInnerQuery = "";
		String 		strSearchPhoneNumber = "";
		String 		strWhereCondition = ""; 
		IUsers 		iUsers = null;
		int 		nCount = 0;
		int 		nSearchType = 0;
		int			nMin = 0;
		int			nMax = 0;
		int 		nTotalCount = 0;
		
		// check initial condition
		if ((strPhoneNumber == null) || (strPhoneNumber.length() == 0))
		{
			m_lastError.setMessage("Fail to get phone number.", 
							       "IUserHandler.getUsersByRelatedPhone.Empty Phone ID", 
							       "");	
			return null;
		}
		
		if (nDocsPerPage < 0)
		{
			nDocsPerPage = 10;	
		}
		
		if (nCurrentPage < 0)
		{
			nCurrentPage = 0;	
		}
		
		if ((bIncludeOfficeTel == false) && (bIncludeMobile == false) &&
		    (bIncludeHomeTel == false))
		{
			m_lastError.setMessage("Fail to get phone number.", 
							       "IUserHandler.getUsersByRelatedPhone.Empty Phone Condition", 
							       "");		
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// 검색 Type을 결정
		nCount = DataConverter.getFindCount(strPhoneNumber, SEARCH_LIKE);
		if (nCount == 0)						// SearchType 0 : equal 검색을 하는 경우
		{
			nSearchType = 0;
		}
		else if (nCount == strPhoneNumber.length())	// SearchType 2 : 전체 검색을 하는 경우
		{
			nSearchType = 2;	
		}
		else									// SearchType 1 : like 검색을 하는 경우
		{
			nSearchType = 1;		
		}
		
		strSearchPhoneNumber = getSearchFormat(strPhoneNumber);
		
		LinkedList conditionList = new LinkedList();
		if (bIncludeOfficeTel == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2));
		}
		if (bIncludeMobile == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2));
		}
		if (bIncludeHomeTel == true)
		{
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL));
			conditionList.add(UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2));	
		}	
		for (int i = 0; i < conditionList.size(); i++)
		{
			if (nSearchType == 2)		// 전체 검색인 경우 where 조건을 제거
				continue;
				
			String strColumn = (String) conditionList.get(i);
				
			if (bExistCondition == false) 
			{
				strWhereCondition += " WHERE ";
				bExistCondition = true;
			}
			else
			{
				strWhereCondition += " OR ";
			}
			
			if (nSearchType == 1)			
				strWhereCondition += strColumn  + " LIKE '" + strSearchPhoneNumber + "'";
			else
				strWhereCondition += strColumn  + " = '" + strSearchPhoneNumber + "'";
				
		}
		
		strInnerSelectQuery = "SELECT " + m_strDetailUserColumns;
		strInnerFromQuery = " FROM " + m_strDetailUserTable + strWhereCondition;
		
		strSortData = "USER_ORDER, USER_NAME";
		
		strInnerQuery = queryCombine(strInnerSelectQuery, strInnerFromQuery, strSortData);;
		
		nMin = nCurrentPage * nDocsPerPage + 1;
		nMax = (nCurrentPage + 1) * nDocsPerPage;

		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE))
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
			           "  FROM ( " +
			           "          SELECT " + m_strDetailSUserColumns + ", ROWNUM NUM " +
			           "            FROM ( " +
					   						strInnerQuery+
					   "                 ) vINNSERSEARCH " +
					   "        WHERE ROWNUM <= " + nMax +
					   "       ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			strQuery = "SELECT " + m_strDetailSUserColumns +
			           "  FROM ( " +
			           "          SELECT " + m_strDetailSUserColumns + ", (ROW_NUMBER() OVER()) NUM " +
			           "            FROM ( " +
					   						strInnerQuery+
					   "                 ) vINNSERSEARCH " +
					   " 		   FETCH FIRST " + nMax + " ROW ONLY" +
					   "       ) vSERACH " +
					   " WHERE NUM BETWEEN " + nMin + " AND " + nMax;
		}
		else if((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) ||(m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
		{
			int nTopCount = (nCurrentPage + 1) * nDocsPerPage;	
			strQuery = "SELECT TOP " + nTopCount + " " +
			                   m_strDetailSUserColumns + 
			                   m_strCompOrderColumnName + 
					   " FROM ( " +
					   "		SELECT " + m_strDetailSUserColumns + 
					                       m_strCompOrderColumnName +
					   " 		FROM ( " +
					   					strInnerQuery +
					   "			 ) vINNERSERACH " +
					   "      ) vSERACH "
					   + getOrderQuery(strSortData);
		}
		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		try
		{	
			resultSet = m_connectionBroker.getResultSet();
			if ((m_nDBType == ConnectionParam.DB_TYPE_MSSQL) || (m_nDBType == ConnectionParam.DB_TYPE_SYBASE))
			{
				if (nCurrentPage > 0 && resultSet != null)
					resultSet.absolute(nCurrentPage * nDocsPerPage);						
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set absolute cursor.",
								   "IUserHandler.getUsersByNameInCaseInsensitive.ResultSet.absolute",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		
		iUsers = processData(m_connectionBroker.getResultSet());
		if (iUsers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		nTotalCount = getTotalCount(strInnerFromQuery);
		if (nTotalCount < 0)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		iUsers.setSearchTotalCount(nTotalCount);
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return iUsers;		
	}
	
	/**
	 * sutil 모듈로 디코딩하는 함수.
	 * @param strEncodedData 인코딩되어 있는 데이터
	 * @return String
	 */
	private String decodeBySType(String strEncodedData)
	{
		String strDecodedData = EnDecode.DecodeBySType(strEncodedData);
		if ((strDecodedData == null) || (strDecodedData.length() == 0))
			return strDecodedData;
			
		int nStartIndex = strDecodedData.indexOf(ENCRYPT_DELIMITER);
		int nEndIndex = strDecodedData.indexOf(ENCRYPT_POSTFIX);
		
		if ((nStartIndex > -1) && (nEndIndex > nStartIndex))
			return strDecodedData.substring(nStartIndex + 1, nEndIndex);
		else if ((nStartIndex > -1) && (nStartIndex == nEndIndex))
			return "";
		else
			return strDecodedData;
	}
	
	/**
	 * 쿼리문 반환 (사용자 검색시 조직 정보순으로 정렬하기 위하여)
	 * @param strSelectQuery SELECT 문
	 * @param strFromQuery	FROM 문
	 * @param strOrderQuery	정렬 조건
	 * @return
	 */
	private String queryCombine(String strSelectQuery, String strFromQuery, String sortData){
		String strQuery = "";

		if(isUseCompOrder){			
			strQuery = "SELECT "+ m_strDetailSUserColumns + ", " +
					   " (CASE WHEN tORG.ORG_ID = '" + m_strLoginCompID +"' THEN -999999 ELSE tORG.COMP_ORDER END) COMP_ORDER" +
					   "  FROM (" + strSelectQuery +
					   				strFromQuery +
					   " ) tUSER, " +
					   " ( SELECT ORG_ID, ORG_ORDER AS COMP_ORDER" +
					   " FROM "+ TableDefinition.getTableName(TableDefinition.ORGANIZATION) + ") tORG" +
					   " WHERE tUSER.COMP_ID = tORG.ORG_ID";
			
		}else{
			strQuery = strSelectQuery + strFromQuery;
		}
		
		//order 정보
		if((m_nDBType != ConnectionParam.DB_TYPE_MSSQL) && (m_nDBType != ConnectionParam.DB_TYPE_SYBASE)){
			strQuery += getOrderQuery(sortData);
		}
		
		return strQuery;
	}
	
	
	/**
	 * 리스트 정렬정보(ORDER BY) 쿼리 반환
	 * @param sortData
	 * @return
	 */
	private String getOrderQuery(String sortData){
		String strOrderQuery = "";
		
		if(sortData != null && sortData.length() > 0)
			if(isUseCompOrder)
				strOrderQuery = "  ORDER BY COMP_ORDER, " + sortData;
			else
				strOrderQuery = "  ORDER BY " + sortData;
		else
			if(isUseCompOrder)
				strOrderQuery = "  ORDER BY COMP_ORDER";
				
		return strOrderQuery;
	}
	
}
