package com.sds.acube.app.idir.org.user;

/**
 * UserListMap.java
 * 2002-10-23
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
import com.sds.acube.app.idir.common.constants.DBConstants;

public class UserListMap 
{
	// User Container
	private static final int PROCESSING = 0;				// 진행함
	private static final int SUBMITED = 1;					// 기안함
	private static final int COMPLETED = 2;					// 완료함
	private static final int RECEIVED = 3;					// 대기함
	private static final int PRIVATE = 4;					// 개인함
	private static final int REJECTED = 5;					// 반려함
	private static final int DISCARDED = 6;					// 폐기함 
	private static final int DEPTRECEIVED = 7;				// 부서대기함 
	private static final int AFTERAPPROVAL = 8;				// 후열함 
    private static final int SUBMITEDAPPROVAL = 9;			// 결재기안함
    private static final int SUBMITEDDOCFLOW = 10;			// 접수기안함 
	private static final int INSPECTION = 11;				// 감사대장
	private static final int INVESTIGATION = 12;			// 심사함
	private static final int SENDING = 13;					// 발송함
	private static final int RECEIVING = 14;				// 접수함 
	private static final int DISTRIBUTION = 15;				// 배부함 
	private static final int REGILEDGER = 16;				// 등록대장				
	private static final int RECVLEDGER = 17;				// 접수대장 
	private static final int DISTLEDGER = 18;				// 배부대장 
	private static final int TRANSFERLEDGER = 19;			// 이송대장
	private static final int CIVILMANAGEDEPT = 20;			// 민원 사무 처리부 	
	private static final int CIVILRECV = 21;				// 민원사무처리/접수대장
	private static final int CIVILDIST = 22;				// 민원사무처리/배부대장
	private static final int CIVILMOVE1 = 23;				// 대비실 이첩 
	private static final int CIVILMOVE2 = 24;				// 감사원 이첩 
	private static final int CIVILMOVE3 = 25;				// 다수인 이첩
	private static final int PUBLICPOST = 26;               // 공람게시
	private static final int DEREGILEDGER = 27;				// 미편철함
	private static final int DOCREGILEDGER = 28;			// 편철 등록대장
	private static final int DOCDISTLEDGER = 29;			// 편철 배부대장
	private static final int PREINSPECTION = 30;			// 사전감사대장
	private static final int POSTINSPECTION = 31;			// 사후감사대장
	private static final int PROCINSPECTION = 32;			// 감사접수함
	private static final int CONCERN = 33;					// 관심문서함
	private static final int EXCHANGESUBMITED = 34;			// 연계기안함
	private static final int RELATEDAPPROVAL = 35;			// 업무관련함
	
	private static final int CONTAINER_COUNT = 36;			// 함의 개수 
	
	private static final String m_strContainerName[] =
	{
		"PROCESSING",
		"SUBMITED",
		"COMPLETED",
		"RECEIVED",
		"PRIVATE",
		"REJECTED",
		"DISCARDED",
		"DEPTRECEIVED",
		"AFTERAPPROVAL",
		"SUBMITEDAPPROVAL",		
		"SUBMITEDDOCFLOW",		
		"INSPECTION",
		"INVESTIGATION",
		"SENDING",
		"RECEIVING",
		"DISTRIBUTION",
		"REGILEDGER",
		"RECVLEDGER",
		"DISTLEDGER",
		"TRANSFERLEDGER",
		"CIVILMANAGEDEPT",
		"CIVILRECV",
		"CIVILDIST",	
		"CIVILMOVE1",
		"CIVILMOVE2",
		"CIVILMOVE3",
		"PUBLICPOST",
		"DEREGILEDGER",
		"DOCREGILEDGER",
		"DOCDISTLEDGER",
		"PREINSPECTION",
		"POSTINSPECTION",
		"PROCINSPECTION",
		"CONCERN",
		"EXCHANGESUBMITED",
		"RELATEDAPPROVAL"
	};
	
	// 대기함 Default 정보
	public static final int RECEIVED_LABEL_TITLE			= 0;		// 문서제목
	public static final int RECEIVED_LABEL_SUBMITER			= 1;		// 기안자
	public static final int RECEIVED_LABEL_SUBMIT_DATE		= 2;		// 상신일자
	public static final int RECEIVED_LABEL_CURRENT_ROLE		= 3;		// 구분
	public static final int RECEIVED_LABEL_STATUS			= 4;		// 문서상태
	public static final int RECEIVED_LABEL_ATTACH			= 5;		// 첨부
	public static final int RECEIVED_LABEL_ORGIN_DRAFTER    = 6;		// 원기안자
	
	public static final String[][] m_strReceivedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"기안자", 	 "20",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"구분",	 "8", 	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", "8", 	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자", "20",  DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};
	
	// 진행함 Default 정보 
	public static final int PROCESSING_LABEL_TITLE			= 0;		// 문서제목
	public static final int PROCESSING_LABEL_SUBMITER		= 1;		// 기안자
	public static final int PROCESSING_LABEL_SUBMIT_DATE	= 2;		//상신일자
	public static final int PROCESSING_LABEL_APPROVER		= 3;		// 결재자	
	public static final int PROCESSING_LABEL_CURRENT_ROLE	= 4;		// 구분
	public static final int PROCESSING_LABEL_STATUS			= 5;		// 문서상태
	public static final int PROCESSING_LABEL_ATTACH			= 6;		// 첨부
	public static final int PROCESSING_LABEL_ORGIN_DRAFTER  = 7;		// 원기안자

	public static final String[][] m_strProcessingCabColumns =
	{
		{"문서제목",  	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"20",	DBConstants.DB_SORT_DRAFT_NAME + "^" + DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자",  	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재자",	  	"8", 	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"구분",	  	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태",  	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",      	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자",    "20",   DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};
	
	// 완료함 Default 정보 
	public static final int COMPLETED_LABEL_TITLE			= 0;		// 문서제목
	public static final int COMPLETED_LABEL_SUBMITER		= 1;		// 기안자
	public static final int COMPLETED_LABEL_SUBMIT_DATE		= 2;		// 상신일자
	public static final int COMPLETED_LABEL_COMPLETER		= 3;		// 완결자	
	public static final int COMPLETED_LABEL_COMPLETE_DATE	= 4;		// 완결일자
	public static final int COMPLETED_LABEL_CURRENT_ROLE	= 5;		// 구분
	public static final int COMPLETED_LABEL_ATTACH			= 6;		// 첨부
	public static final int COMPLETED_LABEL_ORGIN_DRAFTER  	= 7;		// 원기안자

	public static final String[][] m_strCompletedCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"20",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"완결자",	 	"8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자",    "20",   DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};	
	
	// 기안함 Default 정보 
	public static final int SUBMITED_LABEL_TITLE				= 0;		// 문서제목
	public static final int SUBMITED_LABEL_SUBMIT_DATE			= 1;		// 상신일자
	public static final int SUBMITED_LABEL_APPROVER				= 2;		// 결재자
	public static final int SUBMITED_LABEL_COMPLETER			= 3;		// 완결자	
	public static final int SUBMITED_LABEL_COMPLETE_DATE		= 4;		// 완결일자
	public static final int SUBMITED_LABEL_CURRENT_ROLE			= 5;		// 구분
	public static final int SUBMITED_LABEL_STATUS				= 6;		// 문서상태
	public static final int SUBMITED_LABEL_ATTACH				= 7;		// 첨부
	public static final int SUBMITED_LABEL_ANNOUNCEMENT_STATUS 	= 8;		// 홈페이지 공표 여부 

	public static final String[][] m_strSubmitedCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재대기자",	"8",	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"완결자",	 	"8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공표", 		"6",    DBConstants.DB_SORT_ANNOUNCEMENT_STATUS}
	};	
	
	// 결재 기안함 Default 정보 
	public static final int SUBMITED_APPROVAL_LABEL_TITLE				= 0;		// 문서제목
	public static final int SUBMITED_APPROVAL_LABEL_SUBMIT_DATE			= 1;		// 상신일자
	public static final int SUBMITED_APPROVAL_LABEL_APPROVER			= 2;		// 결재자
	public static final int SUBMITED_APPROVAL_LABEL_COMPLETER			= 3;		// 완결자	
	public static final int SUBMITED_APPROVAL_LABEL_COMPLETE_DATE		= 4;		// 완결일자
	public static final int SUBMITED_APPROVAL_LABEL_CURRENT_ROLE		= 5;		// 구분
	public static final int SUBMITED_APPROVAL_LABEL_STATUS				= 6;		// 문서상태
	public static final int SUBMITED_APPROVAL_LABEL_ATTACH				= 7;		// 첨부
	public static final int SUBMITED_APPROVAL_LABEL_ANNOUNCEMENT_STATUS = 8;		// 홈페이지 공표 여부 
	public static final int SUBMITED_APPROVAL_LABEL_IS_MODIFIED			= 9;		// 결재문서 수정 여부 

	public static final String[][] m_strSubmitedApprovalCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재대기자",	"8",	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"완결자",	 	"8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공표", 		"6",    DBConstants.DB_SORT_ANNOUNCEMENT_STATUS},
		{"수정",		"6",    DBConstants.DB_SORT_IS_MODIFIED}
	};
	
	// 접수 기안함 Default 정보 
	public static final int SUBMITED_DOCFLOW_LABEL_TITLE			= 0;		// 문서제목
	public static final int SUBMITED_DOCFLOW_LABEL_SUBMIT_DATE		= 1;		// 상신일자
	public static final int SUBMITED_DOCFLOW_LABEL_APPROVER			= 2;		// 결재자
	public static final int SUBMITED_DOCFLOW_LABEL_COMPLETER		= 3;		// 완결자	
	public static final int SUBMITED_DOCFLOW_LABEL_COMPLETE_DATE	= 4;		// 완결일자
	public static final int SUBMITED_DOCFLOW_LABEL_CURRENT_ROLE		= 5;		// 구분
	public static final int SUBMITED_DOCFLOW_LABEL_STATUS			= 6;		// 문서상태
	public static final int SUBMITED_DOCFLOW_LABEL_ATTACH			= 7;		// 첨부
	public static final int SUBMITED_DOCFLOW_LABEL_ANNOUNCEMENT_STATUS = 8;		// 홈페이지 공표 여부 
	public static final int SUBMITED_DOCFLOW_LABEL_ORGIN_DRAFTER  	= 9;		// 원기안자
	public static final int SUBMITED_DOCFLOW_LABEL_SEND_DEPT_NAME	= 10;		// 보낸기관명

	public static final String[][] m_strSubmitedDocflowCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재대기자",	"8",	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"완결자",	 	"8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공표", 		"6",    DBConstants.DB_SORT_ANNOUNCEMENT_STATUS},
		{"원기안자",    "20",   DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT},
		{"보낸기관명",	"10",   DBConstants.DB_SORT_SENDER_DEPT_NAME}
	};
	
	// 연계 기안함 Default 정보 
	public static final int EXCHANGE_SUBMITED_LABEL_TITLE				= 0;		// 문서제목
	public static final int EXCHANGE_SUBMITED_LABEL_CURRENT_ROLE		= 1;		// 구분
	public static final int EXCHANGE_SUBMITED_LABEL_STATUS			= 2;		// 문서상태
	public static final int EXCHANGE_SUBMITED_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strExchageSubmitedCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 반려함 Default 정보 
	public static final int REJECTED_LABEL_TITLE				= 0;		// 문서제목
	public static final int REJECTED_LABEL_SUBMIT_DATE		= 1;		//상신일자
	public static final int REJECTED_LABEL_REJECTER			= 2;		//반려자
	public static final int REJECTED_LABEL_REJECTE_DATE		= 3;		// 반려일자
	public static final int REJECTED_LABEL_CURRENT_ROLE		= 4;		// 구분
	public static final int REJECTED_LABEL_ATTACH			= 5;		// 첨부

	public static final String[][] m_strRejectedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"반려자",	 "8",	DBConstants.DB_SORT_REJECT_NAME},
		{"반려일자", "12",	DBConstants.DB_SORT_REJECT_DATE},	
		{"구분",	 "8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	

	// 사후 보고함 default 정보 
	public static final int AFTERAPPROVAL_LABEL_TITLE			= 0;		// 문서제목
	public static final int AFTERAPPROVAL_LABEL_SUBMITER		= 1;	
	public static final int AFTERAPPROVAL_LABEL_SUBMIT_DATE		= 2;		//상신일자
	public static final int AFTERAPPROVAL_LABEL_APPROVER		= 3;		//결재자
	public static final int AFTERAPPROVAL_LABEL_COMPLETER		= 4;		// 완결자	
	public static final int AFTERAPPROVAL_LABEL_COMPLETE_DATE	= 5;		// 완결일자
	public static final int AFTERAPPROVAL_LABEL_CURRENT_ROLE	= 6;		// 구분
	public static final int AFTERAPPROVAL_LABEL_ATTACH			= 7;		// 첨부

	public static final String[][] m_strAfterapprovalCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"기안자",	 "20",  DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재자",	 "8",	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"완결자",	 "8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", "12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 "8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"첨부",     "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	
	
	// 개인함 default 정보 
	public static final int PRIVATE_LABEL_TITLE			= 0;		// 문서제목
	public static final int PRIVATE_LABEL_CREATE_DATE	= 1;		//생성일자
	public static final int PRIVATE_LABEL_ATTACH			= 2;		// 첨부

	public static final String[][] m_strPrivateCabColumns =
	{
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"생성일자",  "12",	DBConstants.DB_SORT_CREATE_DATE},
		{"첨부",      "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	

	// 페기함 default 정보 
	public static final int DISCARDED_LABEL_TITLE			 = 0;		// 문서제목
	public static final int DISCARDED_LABEL_DELETE_DATE		 = 1;		//삭제일자
	public static final int DISCARDED_LABEL_ORIGINAL_POSITION  = 2;	//원래위치
	public static final int DISCARDED_LABEL_ATTACH			 = 3;		// 첨부

	public static final String[][] m_strDiscardedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"삭제일자", "12",	DBConstants.DB_SORT_DELETE_DATE},
		{"원래위치", "12",	DBConstants.DB_SORT_DOC_STAT_APPR_ROLE},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 부서 대기함 Default 정보 
	public static final int DEPTRECEIVED_LABEL_TITLE			= 0;		//문서제목	
	public static final int DEPTRECEIVED_LABEL_DEPT_NAME		= 1;		//부서명
	public static final int DEPTRECEIVED_LABEL_RANK			= 2;		//직위
	public static final int DEPTRECEIVED_LABEL_SUBMITER		= 3;		// 기안	
	public static final int DEPTRECEIVED_LABEL_SUBMIT_DATE	= 4;		// 상신일자
	public static final int DEPTRECEIVED_LABEL_CURRENT_ROLE	= 5;		// 문서구분
	public static final int DEPTRECEIVED_LABEL_ATTACH		= 6;		// 첨부
		
	public static final String[][] m_strDeptreceivedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"부서명",	 "8",	DBConstants.DB_SORT_DRAFT_DEPT},
		{"직위",	 "8",	DBConstants.DB_SORT_DRAFT_POSITION},
		{"기안자",	 "8",	DBConstants.DB_SORT_DRAFT_NAME},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"문서구분", "8",	DBConstants.DB_SORT_DOC_STATUS},
		{"첨부",	 "6", 	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 개인문서함 Default 정보 
	public static final int CONCERN_LABEL_TITLE			= 0;	// 문서제목
	public static final int CONCERN_LABEL_GUBOON		= 1;	// 문서구분
	public static final int CONCERN_LABEL_STATUS   		= 2;    // 문서상태
	public static final int CONCERN_LABEL_ATTACH		= 3;	// 첨부
	public static final int CONCERN_LABEL_KEEP_DATE		= 4;	// 보관일자
	
	public static final String[][] m_strConcernCabColumns =
	{
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CAT_FLW_STAT},
		{"문서상태",	"10",	DBConstants.DB_SORT_CONCERN_STATUS},
		{"보관일자",	"12",	DBConstants.DB_SORT_CONCERN_KEEP_DATE},
		{"첨부",		"7",    DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 발송함 Default 정보 
	public static final int SENDING_LABEL_COMPLETE_DATE 	= 0;	//결재일자
	public static final int SENDING_LABEL_CLASS_NUMBER	= 1;		//분류번호
	public static final int SENDING_LABEL_SUBMITER 		= 2;		//기안자
	public static final int SENDING_LABEL_COMPLETER		= 3;		//결재자
	public static final int SENDING_LABEL_TITLE			= 4;		//문서제목
	public static final int SENDING_LABEL_ENFORCE_BOUND	= 5;		//시행범위
	public static final int SENDING_LABEL_ATTACH			= 6;	//첨부	

	public static final String[][] m_strSendingCabColumns =
	{
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"기안자",	  "10",	DBConstants.DB_SORT_DRAFT_NAME},
		{"결재자",	  "10",	DBConstants.DB_SORT_CHIEF_NAME},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"시행범위",  "10",	DBConstants.DB_SORT_IS_BAT_ENF_BND_FLW_STAT},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED},
	};
	
	// 심사함 Default 정보 
	public static final int INVESTIGATION_LABEL_COMPLETE_DATE	= 0;	 	//결재일자
	public static final int INVESTIGATION_LABEL_CLASS_NUMBER   	= 1;		//분류번호
	public static final int INVESTIGATION_LABEL_SUBMITER		 	= 2;	//기안자
	public static final int INVESTIGATION_LABEL_COMPLETER	 	= 3;		//결재자
	public static final int INVESTIGATION_LABEL_TITLE			= 4;		//문서제목
	public static final int INVESTIGATION_LABEL_ENFORCE_BOUND	= 5;		//시행범위
	public static final int INVESTIGATION_LABEL_ATTACH			= 6;		//첨부
	
	public static final String[][] m_strInvestigationCabColumns =
	{
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"기안자",	  "10",	DBConstants.DB_SORT_DRAFT_NAME},
		{"결재자",	  "10",	DBConstants.DB_SORT_CHIEF_NAME},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"시행범위",  "10",	DBConstants.DB_SORT_IS_BAT_ENF_BND},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 감사접수함 Default 정보 
	public static final int PROCINSPECTION_LABEL_TITLE			= 0;		// 문서제목
	public static final int PROCINSPECTION_LABEL_SUBMITER		= 1;		// 기안자
	public static final int PROCINSPECTION_LABEL_SUBMIT_DATE	= 2;		//상신일자
	public static final int PROCINSPECTION_LABEL_APPROVER		= 3;		// 결재자	
	public static final int PROCINSPECTION_LABEL_CURRENT_ROLE	= 4;		// 구분
	public static final int PROCINSPECTION_LABEL_STATUS			= 5;		// 문서상태
	public static final int PROCINSPECTION_LABEL_ATTACH			= 6;		// 첨부
	public static final int PROCINSPECTION_LABEL_ORGIN_DRAFTER  = 7;		// 원기안자

	public static final String[][] m_strProcInspectionCabColumns =
	{
		{"문서제목",  	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"20",	DBConstants.DB_SORT_DRAFT_NAME + "^" + DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자",  	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재자",	  	"8", 	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"구분",	  	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태",  	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",      	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자",    "20",   DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};	
	
	// 감사대장 Default 정보 
	public static final int INSPECTION_LABEL_REGI_NUMBER 	= 0;	//등록번호
	public static final int INSPECTION_LABEL_COMPLETE_DATE  = 1;	//결재일자
	public static final int INSPECTION_LABEL_CLASS_NUMBER	= 2;	//분류번호
	public static final int INSPECTION_LABEL_TITLE			= 3;	//문서제목
	public static final int INSPECTION_LABEL_SUBMITER		= 4;    //기안자/기안부서
	public static final int INSPECTION_LABEL_ATTACH			= 5;	//첨부
	public static final int INSPECTION_LABEL_ORGIN_DRAFTER  = 6;	// 원기안자
	
	public static final String[][] m_strInspectionCabColumns =
	{
		{"등록번호",  "10",	DBConstants.DB_SORT_SERIAL_NUMBER},
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"기안자",    "15",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자", "20",  DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};
	
	// 사전감사대장 Default 정보 
	public static final int PREINSPECTION_LABEL_REGI_NUMBER 	= 0;	//등록번호
	public static final int PREINSPECTION_LABEL_COMPLETE_DATE  	= 1;	//결재일자
	public static final int PREINSPECTION_LABEL_CLASS_NUMBER	= 2;	//분류번호
	public static final int PREINSPECTION_LABEL_TITLE			= 3;	//문서제목
	public static final int PREINSPECTION_LABEL_SUBMITER		= 4;    //기안자/기안부서
	public static final int PREINSPECTION_LABEL_ATTACH			= 5;	//첨부
	public static final int PREINSPECTION_LABEL_ORGIN_DRAFTER  = 6;		// 원기안자
	
	public static final String[][] m_strPreInspectionCabColumns =
	{
		{"등록번호",  "10",	DBConstants.DB_SORT_SERIAL_NUMBER},
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"기안자",    "15",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자", "20",  DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};
	
	// 사후감사대장 Default 정보 
	public static final int POSTINSPECTION_LABEL_REGI_NUMBER 	= 0;		//등록번호
	public static final int POSTINSPECTION_LABEL_COMPLETE_DATE  	= 1;	//결재일자
	public static final int POSTINSPECTION_LABEL_CLASS_NUMBER	= 2;		//분류번호
	public static final int POSTINSPECTION_LABEL_TITLE			= 3;		//문서제목
	public static final int POSTINSPECTION_LABEL_SUBMITER		= 4;    	//기안자/기안부서
	public static final int POSTINSPECTION_LABEL_ATTACH			= 5;		//첨부
	public static final int POSTINSPECTION_LABEL_ORGIN_DRAFTER  = 6;		// 원기안자
	
	public static final String[][] m_strPostInspectionCabColumns =
	{
		{"등록번호",  "10",	DBConstants.DB_SORT_SERIAL_NUMBER},
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"기안자",    "15",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자", "20",  DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT}
	};
	
	// 접수함 Default 정보 
	public static final int RECEIVING_LABEL_ENFORCE_DATE	= 0;		//시행일자
	public static final int RECEIVING_LABEL_SEND_DEPT_NAME 	= 1;		//보낸기관명
	public static final int RECEIVING_LABEL_DOCUMENT_NUMBER	= 2;		//문서번호
	public static final int RECEIVING_LABEL_TITLE			= 3;		//문서제목
	public static final int RECEIVING_LABEL_GUBOON			= 4;		//문서구분
	public static final int RECEIVING_LABEL_ATTACH			= 5;		//첨부
	public static final int RECEIVING_LABEL_ORGIN_DRAFTER  	= 6;		//원기안자
	public static final int RECEIVING_LABEL_HAS_OPINION 	= 7;		//의견작성유무
	
	public static final String[][] m_strReceivingCabColumns =
	{
		{"시행일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM_SRL_NUM},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CATEGORY},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"원기안자",    "20",   DBConstants.DB_SORT_ORGIN_DRAFT_NAME + "^" + DBConstants.DB_SORT_ORGIN_DRAFT_DEPT},
		{"의견",		"7",	DBConstants.DB_SORT_HAS_OPINION}
	};
	
	// 배부함 Default 정보 
	public static final int DISTRIBUTION_LABEL_ENFORCE_DATE		= 0;		//시행일자
	public static final int DISTRIBUTION_LABEL_SEND_DEPT_NAME 	= 1;		//보낸기관명
	public static final int DISTRIBUTION_LABEL_DOCUMENT_NUMBER	= 2;		//문서번호
	public static final int DISTRIBUTION_LABEL_TITLE				= 3;				//문서제목
	public static final int DISTRIBUTION_LABEL_GUBOON			= 4;		//문서구분
	public static final int DISTRIBUTION_LABEL_ATTACH			= 5;		//첨부
	
	public static final String[][] m_strDistributionCabColumns =
	{
		{"시행일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM_SRL_NUM},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CATEGORY},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 등록 대장 Default 정보 
	public static final int REGILEDGER_LABEL_REGI_NUMBER	= 0;			//등록번호
	public static final int REGILEDGER_LABEL_COMPLETE_DATE 	= 1;	 		//결재일자
	public static final int REGILEDGER_LABEL_CLASS_NUMBER	= 2;			//분류번호
	public static final int REGILEDGER_LABEL_TITLE			= 3;			//문서제목
	public static final int REGILEDGER_LABEL_SUBMITER		= 4;			//기안자
	public static final int REGILEDGER_LABEL_ENFORCE_DATE	= 5;			//시행일자
	public static final int REGILEDGER_LABEL_GUBOON			= 6;			//문서구분			
	public static final int REGILEDGER_LABEL_ATTACH			= 7;			//첨부	
	public static final int REGILEDGER_LABEL_IS_POST		= 8;			//공람여부
	
	public static final String[][] m_strRegiLedgerColumns =
	{
		{"등록번호",	"10",	DBConstants.DB_SORT_SERIAL_NUMBER},
		{"결재일자",	"15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"8", 	DBConstants.DB_SORT_DRAFT_NAME},
		{"시행일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"문서구분",	"10",	DBConstants.DB_SORT_IS_BAT_DOC_CAT},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공람여부",	"10",	DBConstants.DB_SORT_IS_POST}
	};
	
	// 접수 대장 Default 정보 
	public static final int RECVLEDGER_LABEL_RECV_NUMBER	= 0;	//접수번호
	public static final int RECVLEDGER_LABEL_RECV_DATE		= 1;	//접수일자
	public static final int RECVLEDGER_LABEL_CLASS_NUMBER   = 2;    //분류번호
	public static final int RECVLEDGER_LABEL_SEND_DEPT_NAME = 3;	//보낸기관명
	public static final int RECVLEDGER_LABEL_TITLE			= 4;	//문서제목
	public static final int RECVLEDGER_LABEL_CHARGER		= 5;	//담당자
	public static final int RECVLEDGER_LABEL_GUBOON			= 6;	//문서구분			
	public static final int RECVLEDGER_LABEL_ATTACH			= 7;	//첨부
	public static final int RECVLEDGER_LABEL_IS_POST		= 8;	//공람여부	
	
	public static final String[][] m_strRecvLedgerColumns =
	{
		{"접수번호",	"10",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"분류번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"처리담당자",	"10",	DBConstants.DB_SORT_CHARGER_NAME},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CAT_FLW_STAT},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공람여부",	"10",	DBConstants.DB_SORT_IS_POST}
	};

	// 배부 대장 Default 정보 	
	public static final int DISTLEDGER_LABEL_RECV_DATE		= 0;		//접수일자
	public static final int DISTLEDGER_LABEL_CLASS_NUMBER	= 1;		// 분류번호
	public static final int DISTLEDGER_LABEL_SEND_DEPT_NAME 	= 2;		//보낸기관명
	public static final int DISTLEDGER_LABEL_TITLE			= 3;		//문서제목
	public static final int DISTLEDGER_LABEL_PROC_DEPT		= 4;		//처리과
	public static final int DISTLEDGER_LABEL_TRANSFER		= 5;		//인수자			
	public static final int DISTLEDGER_LABEL_GUBOON			= 6;		//문서구분			
	public static final int DISTLEDGER_LABEL_ATTACH			= 7;		//첨부
	
	public static final String[][] m_strDistLedgerColumns =
	{
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"분류번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"처리과",		"10",	DBConstants.DB_SORT_CHARGE_DEPT},
		{"인수자",		"10",	DBConstants.DB_SORT_TRANSFER_NAME},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CAT_FLW_STAT},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 민원 사무 처리부 Default 정보 
	public static final int CIVILMANAGEDEPT_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int CIVILMANAGEDEPT_LABEL_RECV_DATE			= 1;	//접수일자	
	public static final int CIVILMANAGEDEPT_LABEL_TITLE				= 2;	//문서제목
	public static final int CIVILMANAGEDEPT_LABEL_MANAGE_DEPT		= 3;	//담당부서
	public static final int CIVILMANAGEDEPT_LABEL_PROC_END_DATE		= 4;	//처리만료일
	public static final int CIVILMANAGEDEPT_LABEL_GUBOON				= 5;	//문서구분			

	public static final String[][] m_strCivilManageCabColumns =
	{
		{"접수번호",	"10",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"담당부서",	"10",	""},
		{"처리만료일",	"15",	""},
		{"문서구분",	"10",	""}
	};
	
	// 민원사무처리/접수대장 Default 정보 
	public static final int CIVILRECV_LABEL_RECV_NUMBER			= 0;		//접수번호
	public static final int CIVILRECV_LABEL_RECV_DATE			= 1;		//접수일자
	public static final int CIVILRECV_LABEL_TITLE				= 2;		//문서제목
	public static final int CIVILRECV_LABEL_CIVIL_PERSON			= 3;		//민원인
	public static final int CIVILRECV_LABEL_CHARGER				= 4;		//담당자
	public static final int CIVILRECV_LABEL_PROC_END_DATE		= 5;		//처리만료일

	public static final String[][] m_strCivilRecvLedgerColumns =
	{
		{"접수번호",	"10",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"민원인",		"10",	""},
		{"담당자",		"10",	""},
		{"처리만료일",	"15",	""}	
	};
	
	// 대비실 이첩 Default 정보 
	public static final int CIVILMOVE1_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int CIVILMOVE1_LABEL_RECV_DATE		= 1;		//접수일자	
	public static final int CIVILMOVE1_LABEL_TITLE			= 2;	//문서제목
	public static final int CIVILMOVE1_LABEL_MANAGE_DEPT		= 3;	//담당부서
	public static final int CIVILMOVE1_LABEL_PROC_END_DATE	= 4;	//처리만료일
	public static final int CIVILMOVE1_LABEL_GUBOON			= 5;		//문서구분			

	public static final String[][] m_strCivilMove1CabColumns =
	{
		{"접수번호",	"10",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"10",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"10",	""}
	};	
	
	// 감사원 이첩 Default 정보 
	public static final int CIVILMOVE2_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int CIVILMOVE2_LABEL_RECV_DATE		= 1;		//접수일자	
	public static final int CIVILMOVE2_LABEL_TITLE			= 2;	//문서제목
	public static final int CIVILMOVE2_LABEL_MANAGE_DEPT		= 3;	//담당부서
	public static final int CIVILMOVE2_LABEL_PROC_END_DATE	= 4;	//처리만료일
	public static final int CIVILMOVE2_LABEL_GUBOON			= 5;		//문서구분			

	public static final String[][] m_strCivilMove2CabColumns =
	{
		{"접수번호",	"10",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"10",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"10",	""}
	};	
	
	// 다수인 이첩 Default 정보 
	public static final int CIVILMOVE3_LABEL_RECV_NUMBER	= 0;	//접수번호
	public static final int CIVILMOVE3_LABEL_RECV_DATE		= 1;	//접수일자	
	public static final int CIVILMOVE3_LABEL_TITLE			= 2;	//문서제목
	public static final int CIVILMOVE3_LABEL_MANAGE_DEPT	= 3;	//담당부서
	public static final int CIVILMOVE3_LABEL_PROC_END_DATE	= 4;	//처리만료일
	public static final int CIVILMOVE3_LABEL_GUBOON			= 5;	//문서구분			

	public static final String[][] m_strCivilMove3CabColumns =
	{
		{"접수번호",	"10",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"10",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"10",	""}
	};
	
	// 공람 게시 Default 정보 
	public static final int PUBLICPOST_LABEL_TITLE			= 0;	// 문서제목
	public static final int PUBLICPOST_LABEL_POST_PERSON	= 1;	// 게시자
	public static final int PUBLICPOST_LABEL_POST_DEPT		= 2;	// 게시부서
	public static final int PUBLICPOST_LABEL_POST_DATE		= 3;	// 게시일자
	public static final int PUBLICPOST_LABEL_GUBOON			= 4;	// 문서구분
	public static final int PUBLICPOST_LABEL_ATTACH			= 5;	// 첨부
	public static final int PUBLICPOST_LABEL_POST_POS		= 6;	// 게시된 위치 
	public static final int PUBLICPOST_LABEL_POST_END_DATE  = 7;	// 게시 종료 일자
	public static final int PUBLICPOST_LABEL_SEND_DEPT_NAME = 8;	// 보낸기관명
	
	public static final String[][] m_strPublicPostCabColumns = 
	{
		{"문서제목", 	"0", 	DBConstants.DB_SORT_TITLE},
		{"게시자",		"10",   DBConstants.DB_SORT_POST_NAME},
		{"게시부서", 	"10",   DBConstants.DB_SORT_POST_DEPT_NAME},
		{"게시일자",    "15",   DBConstants.DB_SORT_POST_DATE},
		{"문서구분", 	"10", 	DBConstants.DB_SORT_DOC_CAT_FLW_STAT},
		{"첨부", 		"7", 	DBConstants.DB_SORT_IS_ATTACHED},
		{"게시된 위치",	"15",	DBConstants.DB_SORT_POST_POS},
		{"게시종료일자","12",   DBConstants.DB_SORT_POST_PERIOD},
		{"보낸기관명",  "10", 	DBConstants.DB_SORT_SENDER_DEPT_NAME}
	};
	
	// 이송대장 Default 정보
	public static final int TRANSFERLEDGER_LABEL_TITLE       = 0;
	public static final int TRANSFERLEDGER_LABEL_CHARGER     = 1;
	public static final int TRANSFERLEDGER_LABEL_RECIPIENT   = 2;
	public static final int TRANSFERLEDGER_LABEL_TRANS_DATE  = 3;
	public static final int TRANSFERLEDGER_LABEL_ATTACH      = 4;
	
	public static final String[][] m_strTransferCabColumns = 
	{
		{"문서제목", 	"0", 	DBConstants.DB_SORT_TITLE},
		{"이송처리자",  "10",   DBConstants.DB_SORT_TRANSFERCHARGER_NAME},
		{"수신기관",    "15",   DBConstants.DB_SORT_RECIPIENT},
		{"이송일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"첨부", 		"7", 	DBConstants.DB_SORT_IS_ATTACHED}	
	};
	
	// 미편철함 Default 정보 
	public static final int DEREGILEDGER_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int DEREGILEDGER_LABEL_RECV_DATE		= 1;	//접수일자
	public static final int DEREGILEDGER_LABEL_CLASS_NUMBER   	= 2;    //분류번호
	public static final int DEREGILEDGER_LABEL_SEND_DEPT_NAME 	= 3;	//보낸기관명
	public static final int DEREGILEDGER_LABEL_TITLE			= 4;	//문서제목
	public static final int DEREGILEDGER_LABEL_CHARGER			= 5;	//담당자
	public static final int DEREGILEDGER_LABEL_GUBOON			= 6;	//문서구분			
	public static final int DEREGILEDGER_LABEL_ATTACH			= 7;	//첨부
	public static final int DEREGILEDGER_LABEL_IS_POST			= 8;	//공람여부	
	
	public static final String[][] m_strDeregiLedgerColumns =
	{
		{"접수번호",	"10",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"분류번호",	"10",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"처리담당자",	"10",	DBConstants.DB_SORT_CHARGER_NAME},
		{"문서구분",	"10",	DBConstants.DB_SORT_DOC_CAT_FLW_STAT},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공람여부",	"10",	DBConstants.DB_SORT_IS_POST}
	};
	
	// 편철 등록대장 Default 정보
	public static final int DOCREGILEDGER_LABEL_NUMBER 			= 0;	// 번호
	public static final int DOCREGILEDGER_LABEL_DATE 			= 1;	// 일자
	public static final int DOCREGILEDGER_LABEL_TITLE 			= 2;	// 문서제목
	public static final int DOCREGILEDGER_LABEL_USER 			= 3;	// 수(발)신자
	public static final int DOCREGILEDGER_LABEL_GUBOON 			= 4;	// 구분
	public static final int DOCREGILEDGER_LABEL_STATUS			= 5; 	// 상태
	public static final int DOCREGILEDGER_LABEL_ELECTRIC		= 6; 	// 전자문서
	public static final int DOCREGILEDGER_LABEL_DRAFTER			= 7;	// 기안자
	
	public static final String[][] m_strDocRegiLedgerColumns =
	{
		{"번호",		"8",	"SEQ"},
		{"일자",		"9",	"DATE1"},
		{"문서제목",	"0",	"TITLE"},
		{"수(발)신자",	"12",	"USER1"},
		{"구분",		"6",	"TYPE"},
		{"상태",		"11",	"STATUS"},
		{"전자",		"6",	"BOOL2"},
		{"기안자",		"10",	"USER2"}
	};
	
	// 편철 배부대장 Default 정보
	public static final int DOCDISTLEDGER_LABEL_NUMBER			= 0;	// 배부번호
	public static final int DOCDISTLEDGER_LABEL_RECV_DATE		= 1;	// 접수일자
	public static final int DOCDISTLEDGER_LABEL_SEND_ORG		= 2;	// 보낸기관
	public static final int DOCDISTLEDGER_LABEL_TITLE			= 3; 	// 문서제목
	public static final int DOCDISTLEDGER_LABEL_DIST_DATE		= 4;    // 배부일자
	public static final int DOCDISTLEDGER_LABEL_PROC_DEPT		= 5; 	// 처리과
	public static final int DOCDISTLEDGER_LABEL_ACCEPTOR		= 6;	// 인수자
	public static final int DOCDISTLEDGER_LABEL_STAUTS			= 7;	// 상태
	
	public static final String[][] m_strDocDistLedgerColumns =
	{
		{"배부번호",	"8",	"SEQ"},
		{"접수일자",	"8",	"DATE1"},
		{"보낸기관",	"15",	"INFO2"},
		{"문서제목",	"0",	"TITLE"},
		{"배부일자",	"8",	"DATE2"},
		{"처리과",		"10",	"DPT1"},
		{"인수자",		"10",	"USER1"},
		{"상태",		"8",	"BOOL2"}
	};
	
	// 연관업무함 Default 정보 
	public static final int RELATEDAPPROVAL_LABEL_TITLE				= 0;		// 문서제목
	public static final int RELATEDAPPROVAL_LABEL_SUBMIT_DATE		= 1;		// 상신일자
	public static final int RELATEDAPPROVAL_LABEL_APPROVER			= 2;		// 결재자
	public static final int RELATEDAPPROVAL_LABEL_COMPLETER			= 3;		// 완결자	
	public static final int RELATEDAPPROVAL_LABEL_COMPLETE_DATE		= 4;		// 완결일자
	public static final int RELATEDAPPROVAL_LABEL_CURRENT_ROLE		= 5;		// 구분
	public static final int RELATEDAPPROVAL_LABEL_STATUS			= 6;		// 문서상태
	public static final int RELATEDAPPROVAL_LABEL_ATTACH			= 7;		// 첨부
	public static final int RELATEDAPPROVAL_LABEL_ANNOUNCEMENT_STATUS = 8;		// 홈페이지 공표 여부 

	public static final String[][] m_strRelatedApprovalCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"결재대기자",	"8",	DBConstants.DB_SORT_CURRENT_APPROVER},
		{"완결자",	 	"8",	DBConstants.DB_SORT_CHIEF_NAME},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"구분",	 	"8",	DBConstants.DB_SORT_CURRENT_ROLE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED},
		{"공표", 		"6",    DBConstants.DB_SORT_ANNOUNCEMENT_STATUS}
	};
	      		
	// Option Definition
	private static final int OPTION_PROCESSING = 0;				// 진행함
	private static final int OPTION_SUBMITED = 1;				// 기안함
	private static final int OPTION_COMPLETED = 2;				// 완료함
	private static final int OPTION_RECEIVED = 3;				// 대기함
	private static final int OPTION_PRIVATE = 4;				// 개인함
	private static final int OPTION_REJECTED = 5;				// 반려함
	private static final int OPTION_DISCARDED = 6;				// 폐기함 
	private static final int OPTION_DEPTRECEIVED = 7;			// 부서대기함 
	private static final int OPTION_AFTERAPPROVAL = 8;			// 후열함 
	private static final int OPTION_SUBMITED_APPROVAL = 9;		// 결재기안함
	private static final int OPTION_SUBMITED_DOCFLOW = 10;		// 접수기안함
	private static final int OPTION_INSPECTION = 11;			// 감사함
	private static final int OPTION_INVESTIGATION = 12;			// 심사함
	private static final int OPTION_SENDING = 13;				// 발송함
	private static final int OPTION_RECEIVING = 14;				// 접수함 
	private static final int OPTION_DISTRIBUTION = 15;			// 배부함 
	private static final int OPTION_REGILEDGER = 16;			// 등록대장				
	private static final int OPTION_RECVLEDGER = 17;			// 접수대장 
	private static final int OPTION_DISTLEDGER = 18;			// 배부대장 
	private static final int OPTION_TRANSLEDGER = 19;			// 이송대장
	private static final int OPTION_CIVILMANAGEDEPT = 20;		// 민원 사무 처리부 	
	private static final int OPTION_CIVILRECV = 21;				// 민원사무처리/접수대장
	private static final int OPTION_CIVILDIST = 22;				// 민원사무처리/배부대장
	private static final int OPTION_CIVILMOVE1 = 23;			// 대비실 이첩 
	private static final int OPTION_CIVILMOVE2 = 24;			// 감사원 이첩 
	private static final int OPTION_CIVILMOVE3 = 25;			// 다수인 이첩
	private static final int OPTION_PUBLICPOST = 26;			// 공람 게시
	private static final int OPTION_DEREGILEDGER = 27;			// 미편철함
	private static final int OPTION_DOCREGILEDGER = 28;			// 편철 등록대장
	private static final int OPTION_DOCDISTLEDGER = 29;			// 편철 배부대장
	private static final int OPTION_PREINSPECTION = 30;			// 사전감사대장
	private static final int OPTION_POSTINSPECTION = 31;		// 사후감사대장
	private static final int OPTION_PROCINSPECTION = 32;		// 감사접수함
	private static final int OPTION_CONCERN = 33;				// 개인문서함
	private static final int OPTION_EXCHANGESUBMITED = 34;		// 연계기안함
	private static final int OPTION_RELATEDAPPROVAL = 35;		// 연관업무함
		
	public static final String m_strOptionIDArray[] =
	{
		"AIOPT97",												// 진행함
		"AIOPT99",												// 기안함
		"AIOPT101",												// 완료함
		"AIOPT103",												// 대기함
		"AIOPT105",												// 개인함
		"AIOPT107",												// 반려함
		"AIOPT109",												// 폐기함
		"AIOPT111",												// 부서대기함
		"AIOPT113",												// 후열함
		"AIOPT166",												// 결재기안함
		"AIOPT168",												// 접수기안함		
		"AIOPT115",												// 감사함
		"AIOPT117",												// 심사함
		"AIOPT119",												// 발송함
		"AIOPT121",												// 접수함
		"AIOPT123",												// 배부함
		"AIOPT125",												// 등록대장
		"AIOPT127",												// 접수대장
		"AIOPT129",												// 배부대장
		"AIOPT151",												// 이송대장
		"AIOPT131",												// 민원 사무 처리부
		"AIOPT133",												// 민원 사무 처리 / 접수대장
		"AIOPT135",												// 민원 사무 처리 / 배부대장
		"AIOPT137",												// 대비실 이첩
		"AIOPT139",												// 감사원 이첩
		"AIOPT141",												// 다수인 이첩
		"AIOPT143",												// 공람게시
		"AIOPT177",												// 미편철함
		"AIOPT181",												// 편철 등록대장
		"AIOPT183",												// 편철 배부대장
		"AIOPT192",												// 사전감사대장
		"AIOPT194",												// 사후감사대장
		"AIOPT198",												// 접수감사함
		"AIOPT201",												// 개인문서함
		"AIOPT209",												// 연계기안함
		"AIOPT211"												// 업무관련함
	};
	
	// Option Sort Definition		
	public static final String m_strSortOptionIDArray[] =
	{
		"AIOPT98",												// 진행함
		"AIOPT100",												// 기안함
		"AIOPT102",												// 완료함
		"AIOPT104",												// 대기함
		"AIOPT106",												// 개인함
		"AIOPT108",												// 반려함
		"AIOPT110",												// 폐기함
		"AIOPT112",												// 부서대기함
		"AIOPT114",												// 후열함
		"AIOPT167",												// 결재기안함
		"AIOPT169",												// 접수기안함		
		"AIOPT116",												// 감사함
		"AIOPT118",												// 심사함
		"AIOPT120",												// 발송함
		"AIOPT122",												// 접수함
		"AIOPT124",												// 배부함
		"AIOPT126",												// 등록대장
		"AIOPT128",												// 접수대장
		"AIOPT130",												// 배부대장
		"AIOPT152",												// 이송대장
		"AIOPT132",												// 민원 사무 처리부
		"AIOPT134",												// 민원 사무 처리 / 접수대장
		"AIOPT136",												// 민원 사무 처리 / 배부대장
		"AIOPT138",												// 대비실 이첩
		"AIOPT140",												// 감사원 이첩
		"AIOPT142",												// 다수인 이첩
		"AIOPT144",												// 공람게시 
		"AIOPT178",												// 미편철함
		"AIOPT182",												// 편철 등록대장
		"AIOPT184",												// 편철 배부대장
		"AIOPT193",												// 사전감사대장
		"AIOPT195",												// 사후감사대장
		"AIOPT199",												// 접수감사함
		"AIOPT202",												// 개인문서함
		"AIOPT210",												// 연계기안함
		"AIOPT212"												// 업무관련함
	};
		
	/**
	 * 선택된 함에 대한 정보를 가져오는 함수 
	 * @param strContainerName 함 이름 
	 * @return String[][] 함 정보 
	 */
	public static String[][] getDefaultCabListInfo(String strContainerName)
	{
		String[][] 	strListInformation = null;
		int 		nType = -1;
		
		nType = getContainerType(strContainerName);
		if (nType == -1)
			return null;
							
		switch(nType)
		{
			case PROCESSING:  		// 진행함
					strListInformation = m_strProcessingCabColumns;
					break;
			case SUBMITED: 		// 기안함
					strListInformation = m_strSubmitedCabColumns;
					break;
			case COMPLETED: 		// 완료함
					strListInformation = m_strCompletedCabColumns;
					break;
			case RECEIVED: 		// 대기함
					strListInformation = m_strReceivedCabColumns;
					break;
			case PRIVATE: 			// 개인함
					strListInformation = m_strPrivateCabColumns;
					break;
			case REJECTED: 		// 반려함
					strListInformation = m_strRejectedCabColumns;
					break;
			case DISCARDED: 		// 폐기함 
					strListInformation = m_strDiscardedCabColumns;
					break;
			case DEPTRECEIVED: 	// 부서대기함 
					strListInformation = m_strDeptreceivedCabColumns;
					break;
			case AFTERAPPROVAL: 	// 후열함 
					strListInformation = m_strAfterapprovalCabColumns;
					break;
			case SUBMITEDAPPROVAL:	// 결재기안함
					strListInformation =  m_strSubmitedApprovalCabColumns;
					break;
			case SUBMITEDDOCFLOW:   // 접수기안함
    				strListInformation =  m_strSubmitedDocflowCabColumns;
    				break;
			case INSPECTION: 		// 감사함
					strListInformation = m_strInspectionCabColumns;
					break;
			case INVESTIGATION: 	// 심사함
					strListInformation = m_strInvestigationCabColumns;
					break;
			case SENDING: 			// 발송함
					strListInformation = m_strSendingCabColumns;
					break;
			case RECEIVING: 		// 접수함 
					strListInformation = m_strReceivingCabColumns;
					break;
			case DISTRIBUTION: 		// 배부함 
					strListInformation = m_strDistributionCabColumns;
					break;
			case REGILEDGER: 		// 등록대장	
					strListInformation = m_strRegiLedgerColumns;
					break;			
			case RECVLEDGER: 		// 접수대장 
					strListInformation = m_strRecvLedgerColumns;
					break;
			case DISTLEDGER: 		// 배부대장 
					strListInformation = m_strDistLedgerColumns;
					break;
			case CIVILMANAGEDEPT: 	// 민원 사무 처리부 
					strListInformation = m_strCivilManageCabColumns;
					break;	
			case CIVILRECV: 		// 민원사무처리/접수대장
					strListInformation = m_strCivilRecvLedgerColumns;
					break;
			case CIVILDIST: 		// 민원사무처리/배부대장
					strListInformation = null;
					break;
			case CIVILMOVE1: 		// 대비실 이첩 
					strListInformation = m_strCivilMove1CabColumns;
					break;
			case CIVILMOVE2: 		// 감사원 이첩 
					strListInformation = m_strCivilMove2CabColumns;
					break;
			case CIVILMOVE3: 		// 다수인 이첩	
					strListInformation = m_strCivilMove3CabColumns;
					break;
			case PUBLICPOST:
					strListInformation = m_strPublicPostCabColumns;
					break;
			case TRANSFERLEDGER:
					strListInformation = m_strTransferCabColumns;
					break;
			case DEREGILEDGER:
					strListInformation = m_strDeregiLedgerColumns;
					break;
			case DOCREGILEDGER:		// 편철 등록대장
					strListInformation = m_strDocRegiLedgerColumns;
					break;
			case DOCDISTLEDGER:		// 편철 배부대장
					strListInformation = m_strDocDistLedgerColumns;
					break;	
			case PREINSPECTION:		// 사전감사대장
					strListInformation = m_strPreInspectionCabColumns;
					break;
			case POSTINSPECTION:	// 사후감사대장
			 		strListInformation = m_strPostInspectionCabColumns;
			 		break;
			case PROCINSPECTION:	// 감사접수함
					strListInformation = m_strProcInspectionCabColumns;
					break;
			case CONCERN:			// 개인문서함
					strListInformation = m_strConcernCabColumns;
					break;
			case EXCHANGESUBMITED:	// 연계기안함
					strListInformation = m_strExchageSubmitedCabColumns;
					break;
			case RELATEDAPPROVAL:	// 업무연관함
					strListInformation = m_strRelatedApprovalCabColumns;
					break;
			default:
					strListInformation = null;
					break;			
		}
		
		return strListInformation;		
	}
	
	/**
	 * 함의 Container Type을 얻어오는 함수 
	 * @param strContainerName 함 이름
	 * @return int
	 */
	private static int getContainerType(String strContainerName)
	{	
		for (int i = 0 ; i < m_strContainerName.length ; i++)
		{
			String strName = m_strContainerName[i];
	
			if (strName.compareTo(strContainerName) == 0)
			{
				return i;
			}	
		}
			
		return -1;
	}	
	
	/**
	 * 주어진 Container의 주어진 Column의 Default정보를 가져오는 함수 
	 * @param strContainerName 함 이름
	 * @param strColumnName    컬럼명
	 * @return UserListItem
	 */
	public static UserListItem getDefaultListItem(String strContainerName,
												  String strColumnName)
	{
		UserListItem 	userListItem = null;
		String[][] 		strDefaultItemArray = null;
		int			nColumnName = 0;
		int			nColumnSize = 1;
		int			nColumnSort = 2;
		
		strDefaultItemArray = getDefaultCabListInfo(strContainerName);
		if (strDefaultItemArray == null)
			return userListItem;
			
		for (int i = 0 ; i < strDefaultItemArray.length ; i++)
		{
			String strDefaultName = strDefaultItemArray[i][nColumnName];	
			
			if (strDefaultName.compareTo(strColumnName) == 0)
			{
				userListItem = new UserListItem();
				
				userListItem.setListLabel(strDefaultItemArray[i][nColumnName]);
				userListItem.setListSize(strDefaultItemArray[i][nColumnSize]);
				userListItem.setListSortType(strDefaultItemArray[i][nColumnSort]);
			}	
		}
		
		return userListItem;
	}
	
	/**
	 * 선택된 함의 OptionID를 가져오는 함수 
	 * @param strContainerName 함 이름 
	 * @return String			Option ID 
	 */
	public static String getOptionID(String strContainerName)
	{
		String 	strOptionID = "";
		int 	nType = -1;
		
		nType = getContainerType(strContainerName);
		if (nType == -1)
			return null;
			
		switch(nType)
		{
			case PROCESSING:  		// 진행함
					strOptionID = m_strOptionIDArray[OPTION_PROCESSING];
					break;
			case SUBMITED: 			// 기안함
					strOptionID = m_strOptionIDArray[OPTION_SUBMITED];
					break;
			case COMPLETED: 		// 완료함
					strOptionID =  m_strOptionIDArray[OPTION_COMPLETED];
					break;
			case RECEIVED: 			// 대기함
					strOptionID =  m_strOptionIDArray[OPTION_RECEIVED];
					break;
			case PRIVATE: 			// 개인함
					strOptionID =  m_strOptionIDArray[OPTION_PRIVATE];
					break;
			case REJECTED: 			// 반려함
					strOptionID =  m_strOptionIDArray[OPTION_REJECTED];
					break;
			case DISCARDED: 		// 폐기함 
					strOptionID =  m_strOptionIDArray[OPTION_DISCARDED];
					break;
			case DEPTRECEIVED: 		// 부서대기함 
					strOptionID =  m_strOptionIDArray[OPTION_DEPTRECEIVED];
					break;
			case AFTERAPPROVAL: 	// 후열함 
					strOptionID =  m_strOptionIDArray[OPTION_AFTERAPPROVAL];
					break;
			case SUBMITEDAPPROVAL:	// 결재기안함
					strOptionID =  m_strOptionIDArray[OPTION_SUBMITED_APPROVAL];
					break;
			case SUBMITEDDOCFLOW:   // 접수기안함
    				strOptionID =  m_strOptionIDArray[OPTION_SUBMITED_DOCFLOW];
    				break;
			case INSPECTION: 		// 감사함
					strOptionID =  m_strOptionIDArray[OPTION_INSPECTION];
					break;
			case INVESTIGATION: 	// 심사함
					strOptionID =  m_strOptionIDArray[OPTION_INVESTIGATION];
					break;
			case SENDING: 			// 발송함
					strOptionID =  m_strOptionIDArray[OPTION_SENDING];
					break;
			case RECEIVING: 		// 접수함 
					strOptionID =  m_strOptionIDArray[OPTION_RECEIVING];
					break;
			case DISTRIBUTION: 		// 배부함 
					strOptionID =  m_strOptionIDArray[OPTION_DISTRIBUTION];
					break;
			case REGILEDGER: 		// 등록대장	
					strOptionID =  m_strOptionIDArray[OPTION_REGILEDGER];
					break;			
			case RECVLEDGER: 		// 접수대장 
					strOptionID =  m_strOptionIDArray[OPTION_RECVLEDGER];
					break;
			case DISTLEDGER: 		// 배부대장 
					strOptionID =  m_strOptionIDArray[OPTION_DISTLEDGER];
					break;
			case CIVILMANAGEDEPT: 	// 민원 사무 처리부 
					strOptionID =  m_strOptionIDArray[OPTION_CIVILMANAGEDEPT];
					break;	
			case CIVILRECV: 		// 민원사무처리/접수대장
					strOptionID =  m_strOptionIDArray[OPTION_CIVILRECV];
					break;
			case CIVILDIST: 		// 민원사무처리/배부대장
					strOptionID = m_strOptionIDArray[OPTION_CIVILDIST];
					break;
			case CIVILMOVE1: 		// 대비실 이첩 
					strOptionID =  m_strOptionIDArray[OPTION_CIVILMOVE1];
					break;
			case CIVILMOVE2: 		// 감사원 이첩 
					strOptionID =  m_strOptionIDArray[OPTION_CIVILMOVE2];
					break;
			case CIVILMOVE3: 		// 다수인 이첩	
					strOptionID =  m_strOptionIDArray[OPTION_CIVILMOVE3];
					break;
			case PUBLICPOST:		// 공람게시
					strOptionID =  m_strOptionIDArray[OPTION_PUBLICPOST];
					break;
			case TRANSFERLEDGER:	// 이송대장
					strOptionID =  m_strOptionIDArray[OPTION_TRANSLEDGER];
					break;
			case DEREGILEDGER:		// 미편철함
					strOptionID =  m_strOptionIDArray[OPTION_DEREGILEDGER];
					break;
			case DOCREGILEDGER:		// 편철 등록대장
					strOptionID =  m_strOptionIDArray[OPTION_DOCREGILEDGER];
					break;
			case DOCDISTLEDGER:		// 편철 배부대장
					strOptionID =  m_strOptionIDArray[OPTION_DOCDISTLEDGER];	
					break;
			case PREINSPECTION:		// 사전 감사대장
					strOptionID =  m_strOptionIDArray[OPTION_PREINSPECTION];
					break;
			case POSTINSPECTION:	// 사후 감사대장
					strOptionID =  m_strOptionIDArray[OPTION_POSTINSPECTION];
					break;
			case PROCINSPECTION:	// 감사접수함
			        strOptionID =  m_strOptionIDArray[OPTION_PROCINSPECTION];
			        break;
			case CONCERN:			// 개인 문서함
			        strOptionID =  m_strOptionIDArray[OPTION_CONCERN];
			        break;
			case EXCHANGESUBMITED:	// 연계 기안함 
					strOptionID =  m_strOptionIDArray[OPTION_EXCHANGESUBMITED];
					break;
			case RELATEDAPPROVAL:	// 업무 연관함 
					strOptionID =  m_strOptionIDArray[OPTION_RELATEDAPPROVAL];
					break;
			default:
					strOptionID = "";
					break;			
		}
		
		return strOptionID;		
	}
	
	/**
	 * 선택된 함의 Default Sort OptionID를 가져오는 함수 
	 * @param strContainerName 함 이름 
	 * @return String			Option ID 
	 */
	public static String getDefaultSortOptionID(String strContainerName)
	{
		String 	strOptionID = "";
		int 	nType = -1;
		
		nType = getContainerType(strContainerName);
		if (nType == -1)
			return null;
			
		switch(nType)
		{
			case PROCESSING:  		// 진행함
					strOptionID = m_strSortOptionIDArray[OPTION_PROCESSING];
					break;
			case SUBMITED: 			// 기안함
					strOptionID = m_strSortOptionIDArray[OPTION_SUBMITED];
					break;
			case COMPLETED: 		// 완료함
					strOptionID =  m_strSortOptionIDArray[OPTION_COMPLETED];
					break;
			case RECEIVED: 			// 대기함
					strOptionID =  m_strSortOptionIDArray[OPTION_RECEIVED];
					break;
			case PRIVATE: 			// 개인함
					strOptionID =  m_strSortOptionIDArray[OPTION_PRIVATE];
					break;
			case REJECTED: 			// 반려함
					strOptionID =  m_strSortOptionIDArray[OPTION_REJECTED];
					break;
			case DISCARDED: 		// 폐기함 
					strOptionID =  m_strSortOptionIDArray[OPTION_DISCARDED];
					break;
			case DEPTRECEIVED: 		// 부서대기함 
					strOptionID =  m_strSortOptionIDArray[OPTION_DEPTRECEIVED];
					break;
			case AFTERAPPROVAL: 	// 후열함 
					strOptionID =  m_strSortOptionIDArray[OPTION_AFTERAPPROVAL];
					break;
			case SUBMITEDAPPROVAL:	// 결재기안함
					strOptionID =  m_strSortOptionIDArray[OPTION_SUBMITED_APPROVAL];
					break;
			case SUBMITEDDOCFLOW:   // 접수기안함
    				strOptionID =  m_strSortOptionIDArray[OPTION_SUBMITED_DOCFLOW];
    				break;
			case INSPECTION: 		// 감사함
					strOptionID =  m_strSortOptionIDArray[OPTION_INSPECTION];
					break;
			case INVESTIGATION: 	// 심사함
					strOptionID =  m_strSortOptionIDArray[OPTION_INVESTIGATION];
					break;
			case SENDING: 			// 발송함
					strOptionID =  m_strSortOptionIDArray[OPTION_SENDING];
					break;
			case RECEIVING: 		// 접수함 
					strOptionID =  m_strSortOptionIDArray[OPTION_RECEIVING];
					break;
			case DISTRIBUTION: 		// 배부함 
					strOptionID =  m_strSortOptionIDArray[OPTION_DISTRIBUTION];
					break;
			case REGILEDGER: 		// 등록대장	
					strOptionID =  m_strSortOptionIDArray[OPTION_REGILEDGER];
					break;			
			case RECVLEDGER: 		// 접수대장 
					strOptionID =  m_strSortOptionIDArray[OPTION_RECVLEDGER];
					break;
			case DISTLEDGER: 		// 배부대장 
					strOptionID =  m_strSortOptionIDArray[OPTION_DISTLEDGER];
					break;
			case CIVILMANAGEDEPT: 	// 민원 사무 처리부 
					strOptionID =  m_strSortOptionIDArray[OPTION_CIVILMANAGEDEPT];
					break;	
			case CIVILRECV: 		// 민원사무처리/접수대장
					strOptionID =  m_strSortOptionIDArray[OPTION_CIVILRECV];
					break;
			case CIVILDIST: 		// 민원사무처리/배부대장
					strOptionID = m_strSortOptionIDArray[OPTION_CIVILDIST];
					break;
			case CIVILMOVE1: 		// 대비실 이첩 
					strOptionID =  m_strSortOptionIDArray[OPTION_CIVILMOVE1];
					break;
			case CIVILMOVE2: 		// 감사원 이첩 
					strOptionID =  m_strSortOptionIDArray[OPTION_CIVILMOVE2];
					break;
			case CIVILMOVE3: 		// 다수인 이첩	
					strOptionID =  m_strSortOptionIDArray[OPTION_CIVILMOVE3];
					break;
			case PUBLICPOST:		// 공람게시
					strOptionID =  m_strSortOptionIDArray[OPTION_PUBLICPOST];
					break;
			case TRANSFERLEDGER:	// 이송대장
					strOptionID =  m_strSortOptionIDArray[OPTION_TRANSLEDGER];
					break;
			case DEREGILEDGER:		// 미편철함
					strOptionID =  m_strSortOptionIDArray[OPTION_DEREGILEDGER];
					break;
			case DOCREGILEDGER:		// 편철 등록대장
					strOptionID =  m_strSortOptionIDArray[OPTION_DOCREGILEDGER];
					break;
			case DOCDISTLEDGER:		// 편철 배부대장
					strOptionID =  m_strSortOptionIDArray[OPTION_DOCDISTLEDGER];
					break;
			case PREINSPECTION:		// 사전 감사대장
					strOptionID =  m_strSortOptionIDArray[OPTION_PREINSPECTION];
					break;
			case POSTINSPECTION:	// 사후 감사대장
					strOptionID =  m_strSortOptionIDArray[OPTION_POSTINSPECTION];
					break;
			case PROCINSPECTION:	// 감사접수함
			   	 	strOptionID =  m_strSortOptionIDArray[OPTION_PROCINSPECTION];
			   	 	break;
			case CONCERN:			// 개인문서함
			        strOptionID =  m_strSortOptionIDArray[OPTION_CONCERN];
			        break;
			case EXCHANGESUBMITED:	// 연계기안함
					strOptionID =  m_strSortOptionIDArray[OPTION_EXCHANGESUBMITED];
					break;
			case RELATEDAPPROVAL:	// 연관업무함
					strOptionID =  m_strSortOptionIDArray[OPTION_RELATEDAPPROVAL];
					break;
			default:
					strOptionID = "";
					break;			
		}
		
		return strOptionID;		
	}											
	
}
