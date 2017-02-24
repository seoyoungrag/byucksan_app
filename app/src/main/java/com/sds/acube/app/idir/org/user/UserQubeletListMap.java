package com.sds.acube.app.idir.org.user;

/**
 * UserQubeletListMap.java
 * 2003-03-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.constants.DBConstants;

public class UserQubeletListMap 
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
	private static final int INSPECTION = 11;				// 감사함
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
	
	private static final int CONTAINER_COUNT = 28;			// 함의 개수 
	
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
		"DEREGILEDGER"
	};
	
	// 대기함 Default 정보
	public static final int RECEIVED_LABEL_TITLE			= 0;		// 문서제목
	public static final int RECEIVED_LABEL_SUBMITER			= 1;		// 기안자
	public static final int RECEIVED_LABEL_SUBMIT_DATE		= 2;		// 상신일자
	public static final int RECEIVED_LABEL_ATTACH			= 3;		// 첨부
	
	public static final String[][] m_strReceivedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"기안자", 	 "20",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 진행함 Default 정보 
	public static final int PROCESSING_LABEL_TITLE			= 0;		// 문서제목
	public static final int PROCESSING_LABEL_SUBMITER		= 1;		// 기안자
	public static final int PROCESSING_LABEL_STATUS			= 2;		// 문서상태
	public static final int PROCESSING_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strProcessingCabColumns =
	{
		{"문서제목",  	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"20",	DBConstants.DB_SORT_DRAFT_NAME + "^" + DBConstants.DB_SORT_DRAFT_DEPT},
		{"문서상태",  	"12",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",      	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 완료함 Default 정보 
	public static final int COMPLETED_LABEL_TITLE			= 0;		// 문서제목
	public static final int COMPLETED_LABEL_SUBMITER		= 1;		// 기안자
	public static final int COMPLETED_LABEL_COMPLETE_DATE	= 2;		// 완결일자
	public static final int COMPLETED_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strCompletedCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"기안자",		"20",	DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"완결일자", 	"12",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	
	
	// 기안함 Default 정보 
	public static final int SUBMITED_LABEL_TITLE			= 0;		// 문서제목
	public static final int SUBMITED_LABEL_SUBMIT_DATE		= 1;		//상신일자
	public static final int SUBMITED_LABEL_STATUS			= 2;		// 문서상태
	public static final int SUBMITED_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strSubmitedCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"문서상태", 	"12",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	
	
	// 결재 기안함 Default 정보 
	public static final int SUBMITED_APPROVAL_LABEL_TITLE				= 0;		// 문서제목
	public static final int SUBMITED_APPROVAL_LABEL_SUBMIT_DATE			= 1;		// 상신일자
	public static final int SUBMITED_APPROVAL_LABEL_STATUS				= 2;		// 문서상태
	public static final int SUBMITED_APPROVAL_LABEL_ATTACH				= 3;		// 첨부

	public static final String[][] m_strSubmitedApprovalCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 접수 기안함 Default 정보 
	public static final int SUBMITED_DOCFLOW_LABEL_TITLE			= 0;		// 문서제목
	public static final int SUBMITED_DOCFLOW_LABEL_SUBMIT_DATE		= 1;		// 상신일자
	public static final int SUBMITED_DOCFLOW_LABEL_STATUS			= 2;		// 문서상태
	public static final int SUBMITED_DOCFLOW_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strSubmitedDocflowCabColumns =
	{
		{"문서제목", 	"0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", 	"12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"문서상태", 	"8",	DBConstants.DB_SORT_FLW_STAT_DOC_STAT},
		{"첨부",	 	"6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 반려함 Default 정보 
	public static final int REJECTED_LABEL_TITLE			= 0;		// 문서제목
	public static final int REJECTED_LABEL_SUBMIT_DATE		= 1;		// 상신일자
	public static final int REJECTED_LABEL_REJECTER			= 2;		// 반려자
	public static final int REJECTED_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strRejectedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"반려자",	 "8",	DBConstants.DB_SORT_REJECT_NAME},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	

	// 사후 보고함 default 정보 
	public static final int AFTERAPPROVAL_LABEL_TITLE			= 0;		// 문서제목
	public static final int AFTERAPPROVAL_LABEL_SUBMITER		= 1;		// 기안자
	public static final int AFTERAPPROVAL_LABEL_SUBMIT_DATE		= 2;		// 상신일자
	public static final int AFTERAPPROVAL_LABEL_ATTACH			= 3;		// 첨부

	public static final String[][] m_strAfterapprovalCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"기안자",	 "20",  DBConstants.DB_SORT_DRAFT_NAME + "^" +DBConstants.DB_SORT_DRAFT_DEPT},
		{"상신일자", "12",	DBConstants.DB_SORT_DRAFT_DATE},
		{"첨부",     "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	
	
	// 개인함 default 정보 
	public static final int PRIVATE_LABEL_TITLE			= 0;		// 문서제목
	public static final int PRIVATE_LABEL_CREATE_DATE	= 1;		// 생성일자
	public static final int PRIVATE_LABEL_ATTACH		= 2;		// 첨부

	public static final String[][] m_strPrivateCabColumns =
	{
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"생성일자",  "12",	DBConstants.DB_SORT_CREATE_DATE},
		{"첨부",      "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};	

	// 페기함 default 정보 
	public static final int DISCARDED_LABEL_TITLE			 = 0;		// 문서제목
	public static final int DISCARDED_LABEL_DELETE_DATE		 = 1;		// 삭제일자
	public static final int DISCARDED_LABEL_ORIGINAL_POSITION  = 2;	    // 원래위치
	public static final int DISCARDED_LABEL_ATTACH			 = 3;		// 첨부

	public static final String[][] m_strDiscardedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"삭제일자", "12",	DBConstants.DB_SORT_DELETE_DATE},
		{"원래위치", "12",	DBConstants.DB_SORT_DOC_STAT_APPR_ROLE},
		{"첨부",	 "6",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 부서 대기함 Default 정보 
	public static final int DEPTRECEIVED_LABEL_TITLE		= 0;		// 문서제목	
	public static final int DEPTRECEIVED_LABEL_DEPT_NAME	= 1;		// 부서명
	public static final int DEPTRECEIVED_LABEL_SUBMITER		= 2;		// 기안	
	public static final int DEPTRECEIVED_LABEL_ATTACH		= 3;		// 첨부
		
	public static final String[][] m_strDeptreceivedCabColumns =
	{
		{"문서제목", "0",	DBConstants.DB_SORT_TITLE},
		{"부서명",	 "8",	DBConstants.DB_SORT_DRAFT_DEPT},
		{"기안자",	 "8",	DBConstants.DB_SORT_DRAFT_NAME},
		{"첨부",	 "6", 	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 발송함 Default 정보 
	public static final int SENDING_LABEL_COMPLETE_DATE = 0;		// 결재일자
	public static final int SENDING_LABEL_CLASS_NUMBER	= 1;		// 분류번호
	public static final int SENDING_LABEL_TITLE			= 2;		// 문서제목
	public static final int SENDING_LABEL_ATTACH		= 3;		// 첨부	

	public static final String[][] m_strSendingCabColumns =
	{
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "12",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED},
	};
	
	// 심사함 Default 정보 
	public static final int INVESTIGATION_LABEL_COMPLETE_DATE	= 0;	// 결재일자
	public static final int INVESTIGATION_LABEL_CLASS_NUMBER   	= 1;	// 분류번호
	public static final int INVESTIGATION_LABEL_SUBMITER		= 2;	// 기안자
	public static final int INVESTIGATION_LABEL_COMPLETER	 	= 3;	// 결재자
	public static final int INVESTIGATION_LABEL_TITLE			= 4;	// 문서제목
	public static final int INVESTIGATION_LABEL_ENFORCE_BOUND	= 5;	// 시행범위
	public static final int INVESTIGATION_LABEL_ATTACH			= 6;	// 첨부
	
	public static final String[][] m_strInvestigationCabColumns =
	{
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "12",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"기안자",	  "10",	DBConstants.DB_SORT_DRAFT_NAME},
		{"결재자",	  "10",	DBConstants.DB_SORT_CHIEF_NAME},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"시행범위",  "12",	DBConstants.DB_SORT_IS_BAT_ENF_BND},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED}
	};	
	
	// 감사함 Default 정보 
	public static final int INSPECTION_LABEL_COMPLETE_DATE 	= 0;	// 결재일자
	public static final int INSPECTION_LABEL_CLASS_NUMBER  	= 1;	// 분류번호
	public static final int INSPECTION_LABEL_SUBMITER		= 2;	// 기안자
	public static final int INSPECTION_LABEL_COMPLETER		= 3;	// 결재자
	public static final int INSPECTION_LABEL_TITLE			= 4;	// 문서제목
	public static final int INSPECTION_LABEL_ENFORCE_BOUND	= 5;	// 시행범위
	public static final int INSPECTION_LABEL_ATTACH			= 6;	// 첨부
	
	public static final String[][] m_strInspectionCabColumns =
	{
		{"결재일자",  "15",	DBConstants.DB_SORT_COMPLETED_DATE},
		{"분류번호",  "12",	DBConstants.DB_SORT_ORG_SYM_CLS_NUM},
		{"기안자",	  "10",	DBConstants.DB_SORT_DRAFT_NAME},
		{"결재자",	  "10",	DBConstants.DB_SORT_CHIEF_NAME},
		{"문서제목",  "0",	DBConstants.DB_SORT_TITLE},
		{"시행범위",  "12",	DBConstants.DB_SORT_IS_BAT_ENF_BND},
		{"첨부",	  "7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 접수함 Default 정보 
	public static final int RECEIVING_LABEL_ENFORCE_DATE	= 0;		// 시행일자
	public static final int RECEIVING_LABEL_SEND_DEPT_NAME 	= 1;		// 보낸기관명
	public static final int RECEIVING_LABEL_TITLE			= 2;		// 문서제목
	public static final int RECEIVING_LABEL_ATTACH			= 3;		// 첨부
	
	public static final String[][] m_strReceivingCabColumns =
	{
		{"시행일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 배부함 Default 정보 
	public static final int DISTRIBUTION_LABEL_ENFORCE_DATE		= 0;		// 시행일자
	public static final int DISTRIBUTION_LABEL_SEND_DEPT_NAME 	= 1;		// 보낸기관명
	public static final int DISTRIBUTION_LABEL_TITLE			= 2;		// 문서제목
	public static final int DISTRIBUTION_LABEL_ATTACH			= 3;		// 첨부
	
	public static final String[][] m_strDistributionCabColumns =
	{
		{"시행일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 등록 대장 Default 정보 
	public static final int REGILEDGER_LABEL_REGI_NUMBER	= 0;		// 등록번호
	public static final int REGILEDGER_LABEL_TITLE			= 1;		// 문서제목
	public static final int REGILEDGER_LABEL_ENFORCE_DATE	= 2;		// 시행일자
	
	public static final String[][] m_strRegiLedgerColumns =
	{
		{"등록번호",	"12",	DBConstants.DB_SORT_SERIAL_NUMBER},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"시행일자",	"15",	DBConstants.DB_SORT_IS_BAT_ENF_BND}
	};
	
	// 이송대장 Default 정보
	public static final int TRANSFERLEDGER_LABEL_TITLE       = 0;		// 문서제목
	public static final int TRANSFERLEDGER_LABEL_TRANS_DATE  = 1;		// 이송일자
	public static final int TRANSFERLEDGER_LABEL_ATTACH      = 2;		// 첨부 
	
	public static final String[][] m_strTransferCabColumns = 
	{
		{"문서제목", 	"0", 	DBConstants.DB_SORT_TITLE},
		{"이송일자",	"15",	DBConstants.DB_SORT_ENFORCE_DATE},
		{"첨부", 		"7", 	DBConstants.DB_SORT_IS_ATTACHED}	
	};
	
	// 접수 대장 Default 정보 
	public static final int RECVLEDGER_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int RECVLEDGER_LABEL_SEND_DEPT_NAME 	= 1;	//보낸기관명
	public static final int RECVLEDGER_LABEL_TITLE			    = 2;	//문서제목			
	
	public static final String[][] m_strRecvLedgerColumns =
	{
		{"접수번호",	"12",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"보낸기관명",	"15",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE}
	};
	
	// 미편철함 Default 정보 
	public static final int DEREGILEDGER_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int DEREGILEDGER_LABEL_SEND_DEPT_NAME 	= 1;	//보낸기관명
	public static final int DEREGILEDGER_LABEL_TITLE			= 2;	//문서제목
		
	public static final String[][] m_strDeregiLedgerColumns =
	{
		{"접수번호",	"10",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE}
	};

	// 배부 대장 Default 정보 	
	public static final int DISTLEDGER_LABEL_RECV_DATE		= 0;		//접수일자
	public static final int DISTLEDGER_LABEL_SEND_DEPT_NAME = 1;		//보낸기관명
	public static final int DISTLEDGER_LABEL_TITLE			= 2;		//문서제목			
	public static final int DISTLEDGER_LABEL_ATTACH			= 3;		//첨부
	
	public static final String[][] m_strDistLedgerColumns =
	{
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"보낸기관명",	"10",	DBConstants.DB_SORT_SENDER_DEPT_NAME},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"첨부",		"7",	DBConstants.DB_SORT_IS_ATTACHED}
	};
	
	// 민원 사무 처리부 Default 정보 
	public static final int CIVILMANAGEDEPT_LABEL_RECV_NUMBER		= 0;	//접수번호
	public static final int CIVILMANAGEDEPT_LABEL_RECV_DATE			= 1;	//접수일자	
	public static final int CIVILMANAGEDEPT_LABEL_TITLE				= 2;	//문서제목
	public static final int CIVILMANAGEDEPT_LABEL_MANAGE_DEPT		= 3;	//담당부서
	public static final int CIVILMANAGEDEPT_LABEL_PROC_END_DATE		= 4;	//처리만료일
	public static final int CIVILMANAGEDEPT_LABEL_GUBOON			= 5;	//문서구분			

	public static final String[][] m_strCivilManageCabColumns =
	{
		{"접수번호",	"12",	DBConstants.DB_SORT_RECEIVE_NUMBER},
		{"접수일자",	"15",	DBConstants.DB_SORT_RECEIVE_DATE},
		{"문서제목",	"0",	DBConstants.DB_SORT_TITLE},
		{"담당부서",	"12",	""},
		{"처리만료일",	"15",	""},
		{"문서구분",	"12",	""}
	};
	
	// 민원사무처리/접수대장 Default 정보 
	public static final int CIVILRECV_LABEL_RECV_NUMBER			= 0;		//접수번호
	public static final int CIVILRECV_LABEL_RECV_DATE			= 1;		//접수일자
	public static final int CIVILRECV_LABEL_TITLE				= 2;		//문서제목
	public static final int CIVILRECV_LABEL_CIVIL_PERSON		= 3;		//민원인
	public static final int CIVILRECV_LABEL_CHARGER				= 4;		//담당자
	public static final int CIVILRECV_LABEL_PROC_END_DATE		= 5;		//처리만료일

	public static final String[][] m_strCivilRecvLedgerColumns =
	{
		{"접수번호",	"12",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"민원인",		"10",	""},
		{"담당자",		"10",	""},
		{"처리만료일",	"15",	""}	
	};
	
	// 대비실 이첩 Default 정보 
	public static final int CIVILMOVE1_LABEL_RECV_NUMBER	= 0;	//접수번호
	public static final int CIVILMOVE1_LABEL_RECV_DATE		= 1;	//접수일자	
	public static final int CIVILMOVE1_LABEL_TITLE			= 2;	//문서제목
	public static final int CIVILMOVE1_LABEL_MANAGE_DEPT	= 3;	//담당부서
	public static final int CIVILMOVE1_LABEL_PROC_END_DATE	= 4;	//처리만료일
	public static final int CIVILMOVE1_LABEL_GUBOON			= 5;	//문서구분			

	public static final String[][] m_strCivilMove1CabColumns =
	{
		{"접수번호",	"12",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"12",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"12",	""}
	};	
	
	// 감사원 이첩 Default 정보 
	public static final int CIVILMOVE2_LABEL_RECV_NUMBER	= 0;	//접수번호
	public static final int CIVILMOVE2_LABEL_RECV_DATE		= 1;	//접수일자	
	public static final int CIVILMOVE2_LABEL_TITLE			= 2;	//문서제목
	public static final int CIVILMOVE2_LABEL_MANAGE_DEPT	= 3;	//담당부서
	public static final int CIVILMOVE2_LABEL_PROC_END_DATE	= 4;	//처리만료일
	public static final int CIVILMOVE2_LABEL_GUBOON			= 5;	//문서구분			

	public static final String[][] m_strCivilMove2CabColumns =
	{
		{"접수번호",	"12",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"12",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"12",	""}
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
		{"접수번호",	"12",	""},
		{"접수일자",	"15",	""},
		{"문서제목",	"0",	""},
		{"담당부서",	"12",	""},
		{"처리만료일",	"15",	""},	
		{"문서구분",	"12",	""}
	};
	
	// 공람 게시 Default 정보 
	public static final int PUBLICPOST_LABEL_TITLE			= 0;
	public static final int PUBLICPOST_LABEL_POST_FOLDER_NAME = 1;
	
	public static final String[][] m_strPublicPostCabColumns = 
	{
		{"문서제목", 	"0", 	DBConstants.DB_SORT_TITLE},
		{"게시위치", 	"12",   DBConstants.DB_SORT_POST_FOLDER_NAME}
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
		
	public static final String m_strOptionIDArray[] =
	{
		"JIOPT97",												// 진행함
		"JIOPT99",												// 기안함
		"JIOPT101",												// 완료함
		"JIOPT103",												// 대기함
		"JIOPT105",												// 개인함
		"JIOPT107",												// 반려함
		"JIOPT109",												// 폐기함
		"JIOPT111",												// 부서대기함
		"JIOPT113",												// 후열함
		"JIOPT166",												// 결재기안함
		"JIOPT168",												// 접수기안함		
		"JIOPT115",												// 감사함
		"JIOPT117",												// 심사함
		"JIOPT119",												// 발송함
		"JIOPT121",												// 접수함
		"JIOPT123",												// 배부함
		"JIOPT125",												// 등록대장
		"JIOPT127",												// 접수대장
		"JIOPT129",												// 배부대장
		"JIOPT151",												// 이송대장
		"JIOPT131",												// 민원 사무 처리부
		"JIOPT133",												// 민원 사무 처리 / 접수대장
		"JIOPT135",												// 민원 사무 처리 / 배부대장
		"JIOPT137",												// 대비실 이첩
		"JIOPT139",												// 감사원 이첩
		"JIOPT141",												// 다수인 이첩
		"JIOPT143",												// 공람게시
		"JIOPT177",												// 미편철함
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
			case SUBMITED: 			// 기안함
					strListInformation = m_strSubmitedCabColumns;
					break;
			case COMPLETED: 		// 완료함
					strListInformation = m_strCompletedCabColumns;
					break;
			case RECEIVED: 			// 대기함
					strListInformation = m_strReceivedCabColumns;
					break;
			case PRIVATE: 			// 개인함
					strListInformation = m_strPrivateCabColumns;
					break;
			case REJECTED: 			// 반려함
					strListInformation = m_strRejectedCabColumns;
					break;
			case DISCARDED: 		// 폐기함 
					strListInformation = m_strDiscardedCabColumns;
					break;
			case DEPTRECEIVED: 		// 부서대기함 
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
			case PUBLICPOST:		// 공람게시
					strListInformation = m_strPublicPostCabColumns;
					break;	
			case TRANSFERLEDGER:	// 이송대장
					strListInformation = m_strTransferCabColumns;
					break;
			case DEREGILEDGER:		// 미편철함
					strListInformation = m_strDeregiLedgerColumns;
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
			case TRANSFERLEDGER:	// 이송대장
					strOptionID =  m_strOptionIDArray[OPTION_TRANSLEDGER];
					break;
			case DEREGILEDGER:		// 미편철함
					strOptionID =  m_strOptionIDArray[OPTION_DEREGILEDGER];
					break;
			default:
					strOptionID = "";
					break;			
		}
		
		return strOptionID;		
	}
}
