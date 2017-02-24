package com.sds.acube.app.idir.common.constants;

/**
 * DBConstants.java
 * 2002-09-27
 *
 * DB 관련 Constant 정의
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public final class DBConstants
{
	public static final String DB_NAME			= "GWAPPR";
	public static final String DB_SERVICE_NAME	= "GWAPPR";
	public static final String DB_USER			= "GW_USER";
	public static final String DB_PASSWORD		= "GW000";

	public static final int DB_ORACLE			= 0;
	public static final int DB_ORACLE_9I		= 1;
	public static final int DB_PROVIDER_COUNT	= 2;

	public static final String[] DB_DRIVER_LIST =
	{
		"oracle.jdbc.OracleDriver",
		"oracle.jdbc.driver.OracleDriver"
	};

	public static final String[] DB_SOURCE_HEAD_LIST =
	{
		"jdbc:oracle:thin:@//",
		"jdbc:oracle:thin:@"
	};

	public static final String[] DB_PORT_LIST =
	{
		"1521",
		"1521"
	};

	public static final String[] DB_SOURCE_DELIMITER_LIST =
	{
		"/",
		":"
	};

	public static final String DB_TABLE_PREFIX = "TGW";

	public static final int DB_SOURCE_IN_PROCESS	= 0;
	public static final int DB_SOURCE_REGISTRATION	= 1;
	public static final int DB_SOURCE_DISTRIBUTION	= 2;
	public static final int DB_SOURCE_RECEPTION		= 3;
	public static final int DB_SOURCE_INSPECTION	= 4;
	public static final int DB_SOURCE_REJECTION		= 5;
	public static final int DB_SOURCE_DOCR			= 6;
	public static final int DB_SOURCE_DISTN			= 7;
	public static final int DB_SOURCE_RECVT			= 8;
	public static final int DB_SOURCE_SENT			= 9;
	public static final int DB_SOURCE_COUNT			= 10;

	public static final String[] DB_SOURCE_LIST =
	{
		"PROC",
		"REGI",
		"DIST",
		"RECV",
		"INSP",
		"REJECT",
		"DOCR",
		"DISTN",
		"RECVT",
		"SENT"
	};

	public static final int DB_CATEGORY_DOC			= 0;
	public static final int DB_CATEGORY_DRAFT		= 1;
	public static final int DB_CATEGORY_ATTACH		= 2;
	public static final int DB_CATEGORY_APPRLINE	= 3;
	public static final int DB_CATEGORY_APPROVER	= 4;
	public static final int DB_CATEGORY_DELIVERER	= 5;
	public static final int DB_CATEGORY_RECIPIENT	= 6;
	public static final int DB_CATEGORY_RELATED		= 7;
	public static final int DB_CATEGORY_LEGACY		= 8;
	public static final int DB_CATEGORY_GETNUM		= 9;
	public static final int DB_CATEGORY_POST		= 10;
	public static final int DB_CATEGORY_READER		= 11;
	public static final int DB_CATEGORY_COUNT		= 12;

	public static final String[] DB_CATEGORY_LIST =
	{
		"DOC",
		"DRAFT",
		"ATTACH",
		"APPRLINE",
		"APPROVER",
		"DELIVERER",
		"RECIPIENT",
		"RELATED",
		"LEGACY",
		"GETNUM",
		"POST",
		"READER"
	};

	public static final int DB_DATE_FORMAT_BAR		= 0;
	public static final int DB_DATE_FORMAT_DOT		= 1;
	public static final int DB_DATE_FORMAT_COUNT	= 2;

	public static final String[] DB_SIMPLE_DATE_FORMAT_LIST =
	{
		"yyyy-MM-dd HH:mm:ss",
		"yyyy.MM.dd HH:mm:ss"
	};

	public static final String[] DB_NLS_DATE_FORMAT_LIST =
	{
		"YYYY-MM-DD HH24:MI:SS",
		"YYYY.MM.DD HH24:MI:SS"
	};

	public static final String DB_SORT_TITLE				= "TITLE";													// 문서제목
	public static final String DB_SORT_DRAFT_NAME			= "DRAFT_NAME";												// 기안자
	public static final String DB_SORT_DRAFT_DEPT			= "DRAFT_DEPT";												// 기안부서
	public static final String DB_SORT_DRAFT_DATE			= "DRAFT_DATE";												// 상신일자
	public static final String DB_SORT_CURRENT_ROLE			= "CURRENT_ROLE";											// 구분
	public static final String DB_SORT_FLW_STAT_DOC_STAT	= "FLOW_STATUS || DOC_STATUS";								// 문서상태
	public static final String DB_SORT_IS_ATTACHED			= "IS_ATTACHED";											// 첨부
	public static final String DB_SORT_CURRENT_APPROVER		= "CURRENT_APPROVER";										// 결재자(결재플로우)
	public static final String DB_SORT_CHIEF_NAME			= "CHIEF_NAME";												// 결재자(유통), 완결자
	public static final String DB_SORT_COMPLETED_DATE		= "COMPLETED_DATE";											// 결재일자,완결일자
	public static final String DB_SORT_REJECT_NAME			= "REJECT_NAME";											// 반려자
	public static final String DB_SORT_REJECT_DATE			= "REJECT_DATE";											// 반려일자
	public static final String DB_SORT_SENDER_DEPT_NAME		= "SENDER_DEPT_NAME";										// 보낸기관명
	public static final String DB_SORT_ORG_SYM_CLS_NUM_SRL_NUM	= "ORG_SYMBOL || CLASS_NUMBER || SERIAL_NUMBER";		// 문서번호 (접수함, 배부함)
	public static final String DB_SORT_ORG_SYM_CLS_NUM		= "ORG_SYMBOL || CLASS_NUMBER";								// 문서번호, 분류번호
	public static final String DB_SORT_IS_BAT_ENF_BND		= "IS_BATCH || ENFORCE_BOUND";								// 시행범위
	public static final String DB_SORT_IS_BAT_ENF_BND_FLW_STAT		= "FLOW_STATUS || IS_BATCH || ENFORCE_BOUND";		// 시행범위 (발송함용),
	public static final String DB_SORT_SERIAL_NUMBER		= "SERIAL_NUMBER";											// 등록번호
	public static final String DB_SORT_RECEIVE_NUMBER		= "RECEIVE_NUMBER";											// 접수번호
	public static final String DB_SORT_RECEIVE_DATE			= "RECEIVE_DATE";											// 접수일자
	public static final String DB_SORT_CHARGER_NAME			= "CHARGER_NAME";											// 담당자
	public static final String DB_SORT_CHARGE_DEPT			= "CHARGE_DEPT";											// 담당과, 담당부서, 처리과
	public static final String DB_SORT_DOC_CATEGORY			= "DOC_CATEGORY";											// 문서구분
	public static final String DB_SORT_IS_BAT_DOC_CAT		= "IS_BATCH || DOC_CATEGORY";								// 문서구분(등록대장용)
	public static final String DB_SORT_DOC_CAT_FLW_STAT		= "DOC_CATEGORY || FLOW_STATUS";							// 문서구분(접수대장, 배부대장용)
	public static final String DB_SORT_ENFORCE_DATE			= "ENFORCE_DATE";											// 시행일자
	public static final String DB_SORT_CREATE_DATE			= "CREATE_DATE";											// 생성일자
	public static final String DB_SORT_DELETE_DATE			= "DELETE_DATE";											// 삭제일자
	public static final String DB_SORT_DOC_STAT_APPR_ROLE	= "DOC_STATUS || APPROVER_ROLE";							// 원래위치
	public static final String DB_SORT_DRAFT_POSITION		= "DRAFT_POSITION";											// 기안자직위
	public static final String DB_SORT_TRANSFER_NAME		= "TRANSFER_NAME";											// 인수자
	public static final String DB_SORT_DOC_STATUS			= "DOC_STATUS";												// 구분 (부서대기함)
	public static final String DB_SORT_POST_NAME			= "POST_NAME";												// 게시자
	public static final String DB_SORT_POST_DEPT_NAME		= "POST_DEPT_NAME";											// 게시자
	public static final String DB_SORT_POST_DATE			= "POST_DATE";												// 게시자
	public static final String DB_SORT_RECIPIENT            = "RECIPIENT";												// 수신기관
	public static final String DB_SORT_TRANSFERCHARGER_NAME = "TRANS_NAME";												// 이송처리자명
	public static final String DB_SORT_POST_FOLDER_NAME		= "POST_FOLDER_NAME";										// 공람게시부서
	public static final String DB_SORT_IS_POST				= "IS_POST";												// 공람여부
	public static final String DB_SORT_POST_POS				= "POST_POS";												// 공람게시된 대장 위치
	public static final String DB_SORT_ANNOUNCEMENT_STATUS  = "ANNOUNCEMENT_STATUS"; 									// 홈페이지 공람 여부
	public static final String DB_SORT_ORGIN_DRAFT_NAME		= "ORGIN_DRAFT_NAME";
	public static final String DB_SORT_ORGIN_DRAFT_DEPT     = "ORGIN_DRAFT_DEPT_NAME";
	public static final String DB_SORT_POST_PERIOD			= "POST_PERIOD";
	public static final String DB_SORT_IS_MODIFIED			= "IS_MODIFIED";
	public static final String DB_SORT_CONCERN_KEEP_DATE	= "CONCERN_KEEP_DATE";
	public static final String DB_SORT_CONCERN_STATUS		= "CONCERN_STATUS";
	public static final String DB_SORT_HAS_OPINION			= "HAS_OPINION";

	public static final String DB_SORT_ASCENDING			= "ASC";													// 오름차순
	public static final String DB_SORT_DESCENDING			= "DESC";													// 내림차순

	// 등록대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_REGISTRYLEDGER =
	{
		"TITLE", 				//제목
		"SERIAL_NUMBER", 		//등록번호
		"DOC_CATEGORY", 		//문서구분
		"DRAFTER_ID",			//기안자
		"CHIEF_ID", 			//결재자
		"COMP_DATE", 			//결재일자
		"SEND_DATE",				//시행일자
		"DRAFTER_NAME",			//기안자명
		"CHIEF_NAME"			//결재자명
	};

	public static final String[] DB_SEARCH_REGISTRYLEDGER =
	{
		"TITLE", 				//제목
		"SERIAL_NUMBER", 		//등록번호
		"DOC_CATEGORY", 		//문서구분
		"DRAFT_UID",			//기안자
		"CHIEF_UID", 			//결재자
		"COMPLETED_DATE", 		//결재일자
		"SIGN_DATE",			//시행일자
		"DRAFTER_NAME",			//기안자명
		"CHIEF_NAME"			//결재자명
	};

	public static final String[] DB_SEARCH_TYPE_REGISTRYLEDGER =
	{
		"STRING", 				//제목
		"RANGE",				//등록번호
		"STRING", 				//문서구분
		"STRING",				//기안자
		"STRING", 				//결재자
		"DATE", 				//결재일자
		"DATE",					//시행일자
		"STRING",				//기안자명
		"STRING"				//결재자명
	};

	// 구기록물철 등록대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_TREGISTRYLEDGER =
	{
		"TITLE", 				//제목
		"SERIAL_NUMBER", 		//등록번호
		"DOC_CATEGORY", 		//문서구분
		"DRAFTER_ID",			//기안자
		"CHIEF_ID", 			//결재자
		"tDOC.COMP_DATE", 		//결재일자
		"SEND_DATE",			//시행일자
		"DRAFTER_NAME",			//기안자명
		"CHIEF_NAME"			//결재자명
	};

	public static final String[] DB_SEARCH_TREGISTRYLEDGER =
	{
		"TITLE", 				//제목
		"SERIAL_NUMBER", 		//등록번호
		"DOC_CATEGORY", 		//문서구분
		"DRAFT_UID",			//기안자
		"CHIEF_UID", 			//결재자
		"COMPLETED_DATE", 		//결재일자
		"SIGN_DATE",			//시행일자
		"DRAFTER_NAME",			//기안자명
		"CHIEF_NAME"			//결재자명
	};

	public static final String[] DB_SEARCH_TYPE_TREGISTRYLEDGER =
	{
		"STRING", 				//제목
		"RANGE",				//등록번호
		"STRING", 				//문서구분
		"STRING",				//기안자
		"STRING", 				//결재자
		"DATE", 				//결재일자
		"DATE",					//시행일자
		"STRING",				//기안자명
		"STRING"				//결재자명
	};

	// 감사대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_INSPLEDGER =
	{
		"TITLE", 						//제목
		"DRAFT_PROC_DEPT_CODE",			//기안부서
		"DRAFTER_ID",					//기안자
		"CHIEF_ID",						//결재자
		"COMP_DATE",					//완결일자
		"DRAFTER_NAME",					//기안자명
		"CHIEF_NAME",					//결재자명
		"ORGIN_DRAFT_DEPT_CODE",		//원기안부서
		"ORGIN_DRAFT_UID",				//원기안자
		"ORGIN_DRAFT_NAME"				//원기안자 이름
	};

	public static final String[] DB_SEARCH_INSPLEDGER =
	{
		"TITLE", 							//제목
		"DRAFT_DEPT_CODE",					//기안부서
		"DRAFT_UID",						//기안자
		"CHIEF_UID",						//결재자
		"COMPLETED_DATE",					//완결일자
		"DRAFTER_NAME",						//기안자명
		"CHIEF_NAME",						//결재자명
		"ORGIN_DRAFT_DEPT_CODE",			//원기안부서
		"ORGIN_DRAFT_UID",					//원기안자
		"ORGIN_DRAFT_NAME"					//원기안자 이름
	};

	public static final String[] DB_SEARCH_TYPE_INSPLEDGER =
	{
		"STRING", 				//제목
		"STRING",				//기안부서
		"STRING",				//기안자
		"STRING",				//결재자
		"DATE",					//완결일자
		"STRING",				//기안자명
		"STRING",				//결재자명
		"STRING",				//원기안부서
		"STRING",				//원기안자
		"STRING"				//원기안자명
	};

	// 접수대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_RECEPTIONLEDGER =
	{
		"TITLE", 				//제목
		"RECEIVE_NUMBER", 		//접수번호
		"ENFORCE_CONSERVE",		//보존기간
		"DOC_CATEGORY", 		//문서구분
		"CHARGER_ID", 			//담당자
		"DRAFT_PROC_DEPT_CODE",	//보낸기관
		"RECV_DATE",			//접수일자
		"CHARGER_NAME"			//담당자명
	};

	public static final String[] DB_SEARCH_RECEPTIONLEDGER =
	{
		"TITLE", 				//제목
		"RECEIVE_NUMBER", 		//접수번호
		"ENFORCE_CONSERVE",		//보존기간
		"DOC_CATEGORY", 		//문서구분
		"CHARGER_ID", 			//담당자
		"DEPT_CODE",			//보낸기관
		"RECEIVE_DATE",			//접수일자
		"CHARGER_NAME"			//담당자명
	};

	public static final String[] DB_SEARCH_TYPE_RECEPTIONLEDGER =
	{
		"STRING",	 			//제목
		"RANGE", 				//접수번호
		"STRING", 				//보존기간
		"STRING", 				//문서구분
		"STRING", 				//담당자
		"STRING", 				//보낸기관
		"DATE",					//접수일자
		"STRING", 				//보낸기관
	};

	// 구기록물철 접수대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_TRECEPTIONLEDGER =
	{
		"TITLE", 				//제목
		"RECEIVE_NUMBER", 		//접수번호
		"ENFORCE_CONSERVE",		//보존기간
		"DOC_CATEGORY", 		//문서구분
		"CHARGER_ID", 			//담당자
		"DRAFT_PROC_DEPT_CODE",	//보낸기관
		"RECV_DATE",			//접수일자
		"CHARGER_NAME"			//담당자명
	};

	public static final String[] DB_SEARCH_TRECEPTIONLEDGER =
	{
		"TITLE", 				//제목
		"RECEIVE_NUMBER", 		//접수번호
		"ENFORCE_CONSERVE",		//보존기간
		"DOC_CATEGORY", 		//문서구분
		"CHARGER_ID", 			//담당자
		"DEPT_CODE",			//보낸기관
		"RECEIVE_DATE",			//접수일자
		"CHARGER_NAME"			//담당자명
	};

	public static final String[] DB_SEARCH_TYPE_TRECEPTIONLEDGER =
	{
		"STRING",	 			//제목
		"RANGE", 				//접수번호
		"STRING", 				//보존기간
		"STRING", 				//문서구분
		"STRING", 				//담당자
		"STRING", 				//보낸기관
		"DATE",					//접수일자
		"STRING" 				//담당자명
	};

	// 이송대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_TRANSFERLEDGER =
	{
		"TITLE", 				//제목
		"tDLVR.USER_ID",		//이송처리자
		"SEND_DATE",			//이송일자
		"tDLVR.USER_NAME"		//이송처리자 이름
	};

	public static final String[] DB_SEARCH_TRANSFERLEDGER =
	{
		"TITLE", 				//제목
		"TRANS_UID",			//이송처리자
		"ENFORCE_DATE",			//이송일자
		"TRANS_NAME"			//이송처리자 이름
	};

	public static final String[] DB_SEARCH_TYPE_TRANSFERLEDGER =
	{
		"STRING", 				//제목
		"STRING",				//이송처리자
		"DATE",					//이송일자
		"STRING"			    //이송처리자 이름
	};

	// 배부대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_DISTRIBUTIONLEDGER =
	{
		"TITLE", 					//제목
		"tDLVR.DEPT_CODE",			//처리과
		"DRAFT_PROC_DEPT_CODE",		//보낸기관
		"RECV_DATE"					//접수일자
	};

	public static final String[] DB_SEARCH_DISTRIBUTIONLEDGER =
	{
		"TITLE", 				//제목
		"CHARGE_DEPT_CODE",		//처리과
		"DEPT_CODE",			//보낸기관
		"SIGN_DATE"				//접수일자
	};

	public static final String[] DB_SEARCH_TYPE_DISTRIBUTIONLEDGER =
	{
		"STRING", 				//제목
		"STRING",				//처리과
		"STRING",				//보낸기관
		"DATE"					//접수일자
	};

	// 구기록물철 배부대장 검색 정보
	public static final String[] DB_SEARCH_COLUMN_TDISTRIBUTIONLEDGER =
	{
		"TITLE", 					//제목
		"tDLVR.DEPT_CODE",			//처리과
		"DRAFT_PROC_DEPT_CODE",		//보낸기관
		"RECV_DATE"					//접수일자
	};

	public static final String[] DB_SEARCH_TDISTRIBUTIONLEDGER =
	{
		"TITLE", 				//제목
		"CHARGE_DEPT_CODE",		//처리과
		"DEPT_CODE",			//보낸기관
		"SIGN_DATE"				//접수일자
	};

	public static final String[] DB_SEARCH_TYPE_TDISTRIBUTIONLEDGER =
	{
		"STRING", 				//제목
		"STRING",				//처리과
		"STRING",				//보낸기관
		"DATE"					//접수일자
	};

	// 부서대기함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_DEPTRECEIVEDCABINET =
	{
		"TITLE", 				//제목
		"DRAFTER_ID",			//기안자
		"DRAFT_DEPT_CODE",		//기안부서
		"DRAFT_DATE",			//기안일자
		"DRAFTER_NAME",			//기안자명
		"DRAFT_DEPT_NAME"		//기안부서명
	};

	public static final String[] DB_SEARCH_DEPTRECEIVEDCABINET =
	{
		"TITLE", 				//제목
		"USER_ID",				//기안자
		"DEPT_CODE",			//기안부서
		"SIGN_DATE",			//기안일자
		"USER_NAME",			//기안자명
		"DEPT_NAME"				//기안부서명
	};

	public static final String[] DB_SEARCH_TYPE_DEPTRECEIVEDCABINET =
	{
		"STRING", 				//제목
		"STRING",				//기안자
		"STRING",				//기안부서
		"DATE",					//기안일자
		"STRING",				//기안자명
		"STRING"				//기안부서명
	};

	// 기안함 검색 정보
	public static final String[] DB_SEARCH_DRAFTCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DOC_STATUS",			//문서상태
		"CURRENT_ROLE",			//결재구분
		"CURRENT_APPROVER_UID",	//결재자(결재대기자)
		"CURRENT_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMPLETED_DATE",		//완결일자
		"DRAFT_UID",			//기안자
		"DRAFT_DEPT_CODE"		//기안부서
	};

	public static final String[] DB_SEARCH_COLUMN_DRAFTCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DOC_STATUS",			//문서상태
		"CURRENT_ROLE",			//결재구분
		"CURRENT_ID",			//결재자(결재대기자)
		"CURRENT_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMP_DATE",			//완결일자
		"tORGINAPPRLINE.DRAFTER_ID",  //기안자
		"tORGINAPPRLINE.DRAFT_DEPT_CODE"	  //기안부서
	};

	public static final String[] DB_SEARCH_TYPE_DRAFTCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 			//보존연한
		"MULTIINT",				//문서상태
		"MULTIINT",				//결재구분
		"MULTISTR",				//결재자(결재대기자)
		"MULTISTR",				//결재부서
		"DATE",					//상신일자
		"DATE",					//완결일자
		"MULTISTR",				//기안자
		"MULTISTR"				//기안부서
	};

	// 업무연관함 검색 정보
	public static final String[] DB_SEARCH_RELATEDAPPROVALCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DOC_STATUS",			//문서상태
		"CURRENT_ROLE",			//결재구분
		"CURRENT_APPROVER_UID",	//결재자(결재대기자)
		"CURRENT_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMPLETED_DATE"		//완결일자
	};

	public static final String[] DB_SEARCH_COLUMN_RELATEDAPPROVALCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DOC_STATUS",			//문서상태
		"CURRENT_ROLE",			//결재구분
		"CURRENT_ID",			//결재자(결재대기자)
		"CURRENT_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMP_DATE",			//완결일자
		"tORGINAPPRLINE.DRAFTER_ID",  //기안자
		"tORGINAPPRLINE.DRAFT_DEPT_CODE"	  //기안부서
	};

	public static final String[] DB_SEARCH_TYPE_RELATEDAPPROVALCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 			//보존연한
		"MULTIINT",				//문서상태
		"MULTIINT",				//결재구분
		"MULTISTR",				//결재자(결재대기자)
		"MULTISTR",				//결재부서
		"DATE",					//상신일자
		"DATE",					//완결일자
		"MULTISTR",				//기안자
		"MULTISTR"				//기안부서
	};

	// 대기함 검색 정보
	public static final String[] DB_SEARCH_WAITCABINET =
	{
		"TITLE", 					//제목
		"ACCESS_LEVEL",				//열람범위
		"URGENCY",					//긴급도
		"PUBLIC_LEVEL",				//공개수준
		"DRAFT_CONSERVE", 			//보존연한
		"DOC_STATUS",				//문서상태
		"CURRENT_ROLE",				//결재구분
		"DRAFT_UID",				//기안자
		"DRAFT_DEPT_CODE",			//기안부서
		"DRAFT_DATE"				//상신일자
	};

	public static final String[] DB_SEARCH_COLUMN_WAITCABINET =
	{
		"TITLE", 						//제목
		"ACCESS_LEVEL",					//열람범위
		"URGENCY",						//긴급도
		"PUBLIC_LEVEL",					//공개수준
		"DRAFT_CONSERVE", 				//보존연한
		"tAPPRLINE.DOC_STATUS",			//문서상태
		"tAPPRLINE.CURRENT_ROLE",		//결재구분
		"tAPPRLINE.DRAFTER_ID",			//기안자
		"tAPPRLINE.DRAFT_DEPT_CODE",	//기안부서
		"tAPPRLINE.DRAFT_DATE"			//상신일자
	};

	public static final String[] DB_SEARCH_TYPE_WAITCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"MULTIINT",				//문서상태
		"MULTIINT",				//결재구분
		"MULTISTR",				//기안자
		"MULTISTR",				//기안부서
		"DATE"					//상신일자
	};

	// 진행함 검색 정보
	public static final String[] DB_SEARCH_PROCESSCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DOC_STATUS",			//문서상태
		"CURRENT_ROLE",			//결재구분
		"DRAFT_UID",			//기안자
		"DRAFT_DEPT_CODE",		//기안부서
		"CURRENT_APPROVER_UID",	//결재자
		"CURRENT_DEPT_CODE",	//결재부서
		"DRAFT_DATE"			//상신일자
	};

	public static final String[] DB_SEARCH_COLUMN_PROCESSCABINET =
	{
		"TITLE", 					//제목
		"ACCESS_LEVEL",				//열람범위
		"URGENCY",					//긴급도
		"PUBLIC_LEVEL",				//공개수준
		"DRAFT_CONSERVE", 			//보존연한
		"tAPPRLINE.DOC_STATUS",	 	//문서상태
		"tAPPRLINE.CURRENT_ROLE",	//결재구분
		"tAPPRLINE.DRAFTER_ID",		//기안자
		"tAPPRLINE.DRAFT_DEPT_CODE",//기안부서
		"tAPPRLINE.CURRENT_ID",		//결재자
		"CURRENT_DEPT_CODE",		//결재부서
		"tAPPRLINE.DRAFT_DATE"		//상신일자
	};

	public static final String[] DB_SEARCH_TYPE_PROCESSCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"MULTIINT",				//문서상태
		"MULTIINT",				//결재구분
		"MULTISTR",				//기안자
		"MULTISTR",				//기안부서
		"MULTISTR",				//결재자
		"MULTISTR",				//결재부서
		"DATE"					//상신일자
	};

	// 완료함 검색 정보
	public static final String[] DB_SEARCH_COMPLETEDCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CURRENT_ROLE",			//결재구분
		"DRAFT_UID",			//기안자
		"DRAFT_DEPT_CODE",		//기안부서
		"CHIEF_UID",			//완결자
		"COMPLETED_DEPT_CODE",	//완결부서
		"DRAFT_DATE",			//상신일자
		"COMPLETED_DATE",		//완결일자
		"DOC_STATUS"			//문서상태
	};

	public static final String[] DB_SEARCH_COLUMN_COMPLETEDCABINET =
	{
		"TITLE", 						//제목
		"ACCESS_LEVEL",					//열람범위
		"URGENCY",						//긴급도
		"PUBLIC_LEVEL",					//공개수준
		"DRAFT_CONSERVE", 				//보존연한
		"tAPPRLINE.CURRENT_ROLE",		//결재구분
		"tAPPRLINE.DRAFTER_ID",			//기안자
		"tAPPRLINE.DRAFT_DEPT_CODE",  	//기안부서
		"tAPPRLINE.CHIEF_ID",			//완결자
		"COMPLETED_DEPT_CODE",			//완결부서
		"tAPPRLINE.DRAFT_DATE",			//상신일자
		"tAPPRLINE.COMP_DATE",			//완결일자
		"tAPPRLINE.DOC_STATUS"			//문서상태
	};

	public static final String[] DB_SEARCH_TYPE_COMPLETEDCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"MULTIINT",				//결재구분
		"MULTISTR",				//기안자
		"MULTISTR",				//기안부서
		"MULTISTR",				//완결자
		"MULTISTR",				//완결부서
		"DATE",					//상신일자
		"DATE",					//완결일자
		"MULTIINT"				//결재구분
	};

	// 후열함 검색 정보
	public static final String[] DB_SEARCH_AFTERAPPROVALCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CURRENT_ROLE",			//결재구분
		"DRAFT_UID",			//기안자
		"DRAFT_DEPT_CODE",		//기안부서
		"CHIEF_UID",			//결재자
		"COMPLETED_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMPLETED_DATE"		//완결일자
	};

	public static final String[] DB_SEARCH_COLUMN_AFTERAPPROVALCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CURRENT_ROLE",			//결재구분
		"DRAFTER_ID",			//기안자
		"DRAFT_DEPT_CODE",		//기안부서
		"CHIEF_ID",				//결재자
		"COMPLETED_DEPT_CODE",	//결재부서
		"DRAFT_DATE",			//상신일자
		"COMP_DATE"		//완결일자
	};

	public static final String[] DB_SEARCH_TYPE_AFTERAPPROVALCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"MULTIINT",				//결재구분
		"MULTISTR",				//기안자
		"MULTISTR",				//기안부서
		"MULTISTR",				//결재자
		"MULTISTR",				//결재부서
		"DATE",					//상신일자
		"DATE"					//완결일자
	};

	// 반려함 검색 정보
	public static final String[] DB_SEARCH_REJECTCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CURRENT_ROLE",			//결재구분
		"REJECT_UID",			//반려자
		"REJECT_DEPT_CODE",		//반려부서
		"DRAFT_DATE",			//상신일자
		"REJECT_DATE"			//반려일자
	};

	public static final String[] DB_SEARCH_COLUMN_REJECTCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CURRENT_ROLE",			//결재구분
		"CURRENT_ID",			//반려자
		"REJECT_DEPT_CODE",		//반려부서
		"DRAFT_DATE",			//상신일자
		"COMP_DATE"				//반려일자
	};

	public static final String[] DB_SEARCH_TYPE_REJECTCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"MULTIINT",				//결재구분
		"MULTISTR",				//반려자
		"MULTISTR",				//반려부서
		"DATE",					//상신일자
		"DATE"					//완결일자
	};

	// 개인함 검색 정보
	public static final String[] DB_SEARCH_PRIVATECABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"CREATE_DATE"			//생성일자
	};

	public static final String[] DB_SEARCH_COLUMN_PRIVATECABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"tAPPROVER.SIGN_DATE"	//생성일자
	};

	public static final String[] DB_SEARCH_TYPE_PRIVATECABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"DATE"					//생성일자
	};

	// 폐기함 검색 정보
	public static final String[] DB_SEARCH_DISCARDEDCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"DELETE_DATE"			//삭제일자
	};

	public static final String[] DB_SEARCH_COLUMN_DISCARDEDCABINET =
	{
		"TITLE", 				//제목
		"ACCESS_LEVEL",			//열람범위
		"URGENCY",				//긴급도
		"PUBLIC_LEVEL",			//공개수준
		"DRAFT_CONSERVE", 		//보존연한
		"tAPPROVER.KEEP_DATE"	//삭제일자
	};

	public static final String[] DB_SEARCH_TYPE_DISCARDEDCABINET =
	{
		"STRING", 				//제목
		"MULTISTR",				//열람범위
		"MULTISTR",				//긴급도
		"MULTISTR",				//공개수준
		"MULTISTR", 				//보존연한
		"DATE"					//삭일자
	};

	// 심사함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_INVESTCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_DEPT_CODE",		//기안부서
		"DRAFTER_ID",			//기안자
		"CHIEF_ID",				//결재자
		"DRAFT_DATE", 			//기안일자
		"COMP_DATE"				//완결일자
	};

	public static final String[] DB_SEARCH_INVESTCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_DEPT_CODE",		//기안부서
		"DRAFT_UID",			//기안자
		"USER_ID",				//결재자
		"DRAFT_DATE", 			//기안일자
		"SIGN_DATE"				//완결일자
	};

	public static final String[] DB_SEARCH_TYPE_INVESTCABINET =
	{
		"STRING", 				//제목
		"STRING",				//기안부서
		"STRING",				//기안자
		"STRING",				//결재자
		"DATE", 				//기안일자
		"DATE"					//완결일자
	};

	// 접수함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_RECVCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_PROC_DEPT_CODE",	//보낸기관
		"tDOC.ENFORCE_BOUND",	 //시행범위
		"SEND_DATE"				//시행일자
	};

	public static final String[] DB_SEARCH_RECVCABINET =
	{
		"TITLE", 				//제목
		"tDLVR.DEPT_CODE",		//보낸기관
		"tDRAFT.ENFORCE_BOUND",	//시행범위
		"SIGN_DATE"				//시행일자
	};

	public static final String[] DB_SEARCH_TYPE_RECVCABINET =
	{
		"STRING", 				//제목
		"STRING",				//보낸기관
		"STRING",				//시행범위
		"DATE"					//시행일자
	};

	// 배부함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_DISTCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_PROC_DEPT_CODE",	//보낸기관
		"SEND_DATE"				//시행일자
	};

	public static final String[] DB_SEARCH_DISTCABINET =
	{
		"TITLE", 				//제목
		"DEPT_CODE",			//보낸기관
		"SIGN_DATE"				//시행일자
	};

	public static final String[] DB_SEARCH_TYPE_DISTCABINET =
	{
		"STRING", 				//제목
		"STRING",				//보낸기관
		"DATE"					//시행일자
	};

	// 발송함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_SENDCABINET =
	{
		"TITLE", 				//제목
		"DRAFTER_ID",			//기안자
		"CHIEF_ID",				//결재자
		"DOC_CATEGORY",			//문서구분
		"ENFORCE_BOUND", 		//시행범위
		"DRAFT_DATE", 			//기안일자
		"COMP_DATE"				//완결일자
	};

	public static final String[] DB_SEARCH_SENDCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_UID",			//기안자
		"CHIEF_UID",			//결재자
		"DOC_CATEGORY",			//문서구분
		"ENFORCE_BOUND", 		//시행범위
		"DRAFT_DATE", 			//기안일자
		"COMPLETED_DATE"		//완결일자
	};

	public static final String[] DB_SEARCH_TYPE_SENDCABINET =
	{
		"STRING", 				//제목
		"STRING",				//기안자
		"STRING",				//결재자
		"STRING",				//문서구분
		"STRING", 				//시행범위
		"DATE", 				//기안일자
		"DATE"					//완결일자
	};

	// 구기록물철 발송함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_TSENDCABINET =
	{
		"TITLE", 				//제목
		"DRAFTER_ID",			//기안자
		"CHIEF_ID",				//결재자
		"DOC_CATEGORY",			//문서구분
		"ENFORCE_BOUND", 		//시행범위
		"DRAFT_DATE", 			//기안일자
		"tAPPRLINE.COMP_DATE"				//완결일자
	};

	public static final String[] DB_SEARCH_TSENDCABINET =
	{
		"TITLE", 				//제목
		"DRAFT_UID",			//기안자
		"CHIEF_UID",			//결재자
		"DOC_CATEGORY",			//문서구분
		"ENFORCE_BOUND", 		//시행범위
		"DRAFT_DATE", 			//기안일자
		"COMPLETED_DATE"		//완결일자
	};

	public static final String[] DB_SEARCH_TYPE_TSENDCABINET =
	{
		"STRING", 				//제목
		"STRING",				//기안자
		"STRING",				//결재자
		"STRING",				//문서구분
		"STRING", 				//시행범위
		"DATE", 				//기안일자
		"DATE"					//완결일자
	};

	// 공람게시 검색 정보
	public static final String[] DB_SEARCH_PUBLICPOST =
	{
		"TITLE", 				//제목
		"USER_ID",				//게시자
		"DEPT_CODE",			//게시부서
		"SIGN_DATE"				//게시일자
	};

	public static final String[] DB_SEARCH_TYPE_PUBLICPOST =
	{
		"STRING", 				//제목
		"STRING",				//게시자
		"STRING",				//게시부서
		"DATE"					//게시일자
	};

	// 관심문서함 검색 정보
	public static final String[] DB_SEARCH_COLUMN_CONCERNCABINET =
	{
		"TITLE", 				// 제목
		"tCONCERN.KEEP_DATE"	// 보관일자
	};

	public static final String[] DB_SEARCH_CONCERNCABINET =
	{
		"TITLE", 				// 제목
		"CONCERN_KEEP_DATE"		// 보관일자
	};

	public static final String[] DB_SEARCH_TYPE_CONCERNCABINET =
	{
		"STRING", 				// 제목
		"DATE"					// 보관일자
	};

	// 인쇄 파라미터
	public static final String[] DB_PRINT_PARAMETER =
	{
		"tDOC.SERIAL_NUMBER", 	//등록번호
		"RECEIVE_NUMBER",		//접수번호
		"RECV_DATE",			//접수일자
		"SEND_DATE",			//이송일자
		"tAPPRLINE.COMP_DATE",	//완결일자
		"SERIAL_NUMBER", 	//등록번호
	};

	public static final String[] DB_PRINT_PARAMETER_TYPE =
	{
		"RANGE", 				//등록번호
		"RANGE",				//접수번호
		"DATE",					//접수일자
		"DATE",					//이송일자
		"DATE",					//완결일자
		"RANGE" 				//등록번호
	};

	public static final int 	DB_LOGIN_TIMEOUT = 3;
	public static final String 	USER_UID_DELIMITER = "$.$";
}
