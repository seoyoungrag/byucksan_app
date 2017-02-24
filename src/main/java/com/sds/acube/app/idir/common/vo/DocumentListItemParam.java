package com.sds.acube.app.idir.common.vo;

/**
 * DocumentListItemParam.java
 * 2002-09-24
 * 
 * 
 * 
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DocumentListItemParam
{
	public static final int APPROVER_ROLE_SELF_APPRVR				= 0;		// 1인결재 기안자
	public static final int APPROVER_ROLE_DRAFTER					= 1;		// 기안자
	public static final int APPROVER_ROLE_CONSIDER_APPRVR			= 2;		// 검토자
	public static final int APPROVER_ROLE_COOPERATE_APPRVR			= 3;		// 협조자
	public static final int APPROVER_ROLE_COOPERATE_AGREE			= 4;		// 열람-추가 기능
	public static final int APPROVER_ROLE_LAST_APPRVR				= 5;		// 결재자
	public static final int APPROVER_ROLE_ARBITRARY_APPRVR			= 6;		// 전결자
	public static final int APPROVER_ROLE_PROXY_APPRVR				= 7;		// 대결자
	public static final int APPROVER_ROLE_NOT_APPRVR				= 8;		// 결재안함자(위임자)
	public static final int APPROVER_ROLE_REAR_APPRVR				= 9;		// 후열자(사후보고)
	public static final int APPROVER_ROLE_REPORT_APPRVR				= 10;		// 통보-추가 기능
	public static final int APPROVER_ROLE_SINGLE_INSPECT_APPRVR		= 11;		// 개인 감사
	public static final int APPROVER_ROLE_SINGLE_SERIAL_APPRVR		= 12;		// 개인 순차합의자
	public static final int APPROVER_ROLE_SINGLE_PARALLEL_APPRVR	= 13;		// 개인 병렬합의자
	public static final int APPROVER_ROLE_CHARGER					= 20;		// 담당자
	public static final int APPROVER_ROLE_PRIOR_APPRVR				= 21;		// 선람자
	public static final int APPROVER_ROLE_PUBLIC_APPRVR				= 22;		// 공람자
	public static final int APPROVER_ROLE_PARALLEL_PUBLIC_APPRVR		= 23;		// 병렬 공람
	public static final int APPROVER_ROLE_GROUP_REPORT_APPRVR		= 30;		// 부서 통보
	public static final int APPROVER_ROLE_GROUP_INSPECT_APPRVR		= 31;		// 부서 감사
	public static final int APPROVER_ROLE_GROUP_SERIAL_APPRVR		= 32;		// 부서 순차합의
	public static final int APPROVER_ROLE_GROUP_PARALLEL_APPRVR		= 33;		// 부서 병렬합의
	public static final int APPROVER_ROLE_MANAGE_GROUP				= 34;		// 결재 주관부서
	public static final int APPROVER_ROLE_COUNT						= 35;
	
	public static final int DOC_STATUS_DRAFT				= 0;		// 기안작성중
	public static final int DOC_STATUS_SET_DRAFTER			= 1;		// 기안자지정됨
	public static final int DOC_STATUS_SUSPENDED_DRAFT		= 2;		// 기안보류됨
	public static final int DOC_STATUS_IN_PROCESS			= 3;		// 결재진행중(상신이나 승인됨)
	public static final int DOC_STATUS_CANCELED				= 4;		// 기안회수됨
	public static final int DOC_STATUS_SUSPENDED_APPROVE	= 5;		// 결재보류됨
	public static final int DOC_STATUS_REJECTED				= 6;		// 반려됨
	public static final int DOC_STATUS_OPPOSED				= 7;		// 반대됨
	public static final int DOC_STATUS_REJECTED_INSPECT		= 8;		// 감사반송됨
	public static final int DOC_STATUS_ROLLBACK				= 9;		// 승인취소됨
	public static final int DOC_STATUS_COMPLETED			= 10;		// 결재완료됨
	public static final int DOC_STATUS_APPROVED_CHARGER		= 11;		// 담당자 지정됨
	public static final int DOC_STATUS_APPROVED_PRIOR		= 12;		// 선람자 지정됨
	public static final int DOC_STATUS_COUNT				= 13;
						
	public static final int FLOW_STATUS_IN_PROCESS			= 0;		// 완료이전
	public static final int FLOW_STATUS_COMPLETED_APPRVR		= 1;		//결재완료됨
	public static final int FLOW_STATUS_WAIT_ENFORCE		= 2;		// 시행대기
	public static final int FLOW_STATUS_WAIT_SEND			= 3;		// 발송대기
	public static final int FLOW_STATUS_WAIT_INSPECT		= 4;		// 심사대기
	public static final int FLOW_STATUS_REJECTED_INSPECT	= 5;		// 심사반송됨
	public static final int FLOW_STATUS_SENT				= 6;		// 발송됨, 시행완료
	public static final int FLOW_STATUS_RESENT				= 7;		// 재발송됨
	public static final int FLOW_STATUS_WAIT_RECEIVE		= 8;		// 접수대기
	public static final int FLOW_STATUS_WAIT_DISTRIBUTE		= 9;		// 배부대기
	public static final int FLOW_STATUS_REJECTED_RECEIVE	= 10;		// 접수반송됨
	public static final int FLOW_STATUS_REJECTED_DISTRIBUTE	= 11;		// 배부반송됨
	public static final int FLOW_STATUS_RECEIVED			= 12;		// 접수됨
	public static final int FLOW_STATUS_DISTRIBUTED		= 13;		// 배부됨
	public static final int FLOW_STATUS_FLOW_APPROVE		= 14;		// 선람,담당,공람
	public static final int FLOW_STATUS_FLOW_DONE			= 15;		// 유통완료
	public static final int FLOW_STATUS_WAIT_TRANSFER		= 16;		// 이송대기
	public static final int FLOW_STATUS_TRANSFERED			= 17;		// 이송
	public static final int FLOW_STATUS_WAIT_REFER			= 18;		// 이첩대기
	public static final int FLOW_STATUS_COUNT				= 19;
	
	public static final int LINE_TYPE_BASIC_APPRVR 	= 0;	//일반결재 라인
	public static final int LINE_TYPE_GROUP_SERIAL_APPRVR 	= 1;	//순차합의부 라인
	public static final int LINE_TYPE_GROUP_PARALLEL_APPRVR 	= 2;	//병렬합의부 라인
	public static final int LINE_TYPE_DOUBLE_APPRVR			= 3;	//이중결재 라인
	public static final int LINE_TYPE_GROUP_REPORT_APPRVR	= 4;	//통보 부서 라인	
	public static final int LINE_TYPE_GROUP_INSPECT_APPRVR	= 5;	//감사부서 라인
	public static final int LINE_TYPE_COUNT				= 6;
	
	public static final int APPROVE_CABINET_NAME_WAIT = 0;
	public static final int APPROVE_CABINET_NAME_PROCESS = 1;
	public static final int APPROVE_CABINET_NAME_COMPLETE = 2;
	public static final int APPROVE_CABINET_NAME_SUBMIT = 3;
	public static final int APPROVE_CABINET_NAME_REJECT = 4;
	public static final int APPROVE_CABINET_NAME_PRIVATE = 5;
	public static final int APPROVE_CABINET_NAME_AFTERAPPROVE = 6;
	public static final int APPROVE_CABINET_NAME_DISCARD = 7;
	public static final int APPROVE_CABINET_NAME_DEPTWAIT = 8;
}
