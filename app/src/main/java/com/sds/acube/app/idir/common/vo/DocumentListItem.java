package com.sds.acube.app.idir.common.vo;

import com.sds.acube.app.idir.common.constants.ApprovalConstants;

/**
 * DocumentListItem.java
 * 2002-09-17
 *
 *
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DocumentListItem
{
	private String	m_strDocID;
	private String	m_strOwnLineName;
	private int		m_nOwnSerialOrder;
	private String	m_strTitle;
	private String	m_strDrafter;
	private String	m_strDraftDepartment;
	private String	m_strDraftDate;
	private String	m_strCurrentApprover;
	private String	m_strCurrentDepartment;
	private int		m_nCurrentRole;
	private String	m_strChief;
	private String	m_strCompletedDate;
	private int		m_nCompletedRole;
	private int		m_nDocumentStatus;
	private int		m_nFlowStatus;
	private String	m_strIsAttached;

	//추가사항
	private String 	m_strSecurityPass;			// 보안문서	비밀번호
	private String 	m_strDraftDeptCode;			// 기안부서코드
	private String 	m_strCreatedDate;			// 생성일자
	private String 	m_strDeletedDate;			// 삭제일자
	private String 	m_strOriginPos;				// 원래위치
	private int 	m_nApproverRole;			// 현결재자의 개인행위
	private String 	m_strIsOpen;				// 결재 진행중 외의 단계에서 문서를 오픈했는지 여부(Y/N)
	private String	m_strOwnIsOpen;				// 자신의 문서 open 여부
	private String 	m_strUrgency;				// 문서 긴급도
	private String 	m_strDrafterUID;			// 기안자의 userID
	private String	m_strCurrentApproverUID;	// 현결재자의 userID
	private String 	m_strChiefUID;				// 완결자의 userID
	private int 	m_nTotalCount;				// 각 함의 총 문서개수
	private String 	m_strDocCategory;			// 문서구분(내부:I/시행:E/업무연락:W/게시:M/민원:C/외부:O)
	private String 	m_strClassNumber;			// 분류번호
	private String 	m_strOrgSymbol;				// 기관기호
	private String 	m_strEnforceBound;			// 시행범위
	private int 	m_nSerialNumber;			// 문서번호
	private String 	m_strEnforceDate;			// 시행일자
	private String 	m_strSenderDeptName;		// 보낸기관
	private int 	m_nDelivererType;			// 전달자 유형
	private String 	m_strEnforceMethod;			// 시행방법
	private String 	m_strTransferName;			// 인수자
	private String 	m_strTransferUID;			// 인수자의 userID
	private String 	m_strRecipient;				// 수신처
	private int 	m_nRecvNumber;				// 접수번호
	private String 	m_strRecvDate;				// 접수일자
	private String 	m_strCharger;				// 담당자
	private String 	m_strChargerUID;			// 담당자의 userID
	private String 	m_strDrafterPosition;		// 기안자 직위
	private int 	m_nLineType;				// 라인유형
	private String 	m_strIsBatch;				// 일괄기안여부(Y/N)
	private String 	m_strBodyType;				// 본문 작성기 종류(gul/hwp/han/doc/htm/txt)
	private String 	m_strChargeDeptName;		// 처리과
	private String	m_strChargeDeptCode;		// 처리과 ID
	private String 	m_strTransferChargerName;	// 이송 처리자
	private String 	m_strTransferChargerUID;	// 이송 처리자 UID
	private int 	m_nAnnouncementStatus;		// 공표상태
	private String 	m_strApprBizID;				// 업무ID
	private String 	m_strApprBizName;			// 업무명
	private String 	m_strOrginDrafterUID;		// 원기안자 UID
	private String  m_strOrginDrafterName;		// 원기안자명
	private String 	m_strOrginDraftDeptCode;	// 원기안부서코드
	private String  m_strOrginDraftDeptName;	// 원기안부서명
	private String  m_strIsModified;			// 문서수정여부
	private String  m_strHasOpinion;			// 의견작성여부

	// 공람게시를 위한 추가인자
	private String m_strPostDeptName;			// 게시부서
	private String m_strPostDeptCode;			// 게시부서 ID
	private String m_strPostName;				// 게시자
	private String m_strPostUID;				// 게시자의 userID
	private String m_strPostDate;				// 게시일자
	private String m_strIsPost;					// 공람게시된 문서인지 여부(Y/N/D)
	private String m_strPostPosition;			// 공람게시 처리된 위치
	private String m_strPostPeriod;				// 게시종료일자

	// 등록대장을 위한 수신처수 추가
	private int m_nRecipientCount;				// 수신처 수

	// PubDoc 문서를 위한 추가인자
	private String m_strIsPubDoc;				// PubDoc인 문서인지 여부(Y/N)

	// 연계문서를 위한 추가 인자
	private String m_strIsAdminMIS;				// 연계문서인지 여부(Y/N)


	private String m_strSenderCompName;			// 보낸 회사명

	// 공람게시 큐블릿을 위한 추가인자
	private String m_strPostFolderID;
	private String m_strPostFolderName;

	// 대기함 큐블릿에서 겸직사용자 세션을 위한 정보
 	private String m_strOwnUserUID;

 	// 관심 문서함을 위한 정보
 	private String m_strConcernKeepDate;         // 관심문서함 보관일자
 	private String m_strConcernDocStatus;		 // 관심문서함 문서상태

	////////////////////////////////////////////////////////////////////////////
	// Constructor
	public DocumentListItem()
	{
		m_strDocID				= "";
		m_strOwnLineName		= "0";
		m_nOwnSerialOrder		= -1;
		m_strTitle				= "";
		m_strDrafter			= "";
		m_strDraftDepartment	= "";
		m_strDraftDate			= "";
		m_strCurrentApprover	= "";
		m_strCurrentDepartment	= "";
		m_nCurrentRole			= -1;
		m_strChief				= "";
		m_strCompletedDate		= "";
		m_nCompletedRole		= -1;
		m_nDocumentStatus		= 0;
		m_nFlowStatus			= -1;
		m_strIsAttached			= "";

// 추가사항
		m_strSecurityPass		= "";
		m_strDraftDeptCode		= "";
		m_strCreatedDate		= "";
		m_strDeletedDate		= "";
		m_strOriginPos			= "";
		m_nApproverRole		= -1;
		m_strIsOpen			= "";
		m_strOwnIsOpen			= "";
		m_strUrgency			= "";
		m_strDrafterUID			= "";
		m_strCurrentApproverUID = "";
		m_strChiefUID			= "";
		m_strDocCategory		= "";
		m_strClassNumber 		= "";
		m_strOrgSymbol			= "";
		m_strEnforceBound		= "";
		m_nSerialNumber			= -1;
		m_strEnforceDate		= "";
		m_strSenderDeptName		= "";
		m_nDelivererType		= -1;
		m_strEnforceMethod 		= "";
		m_strTransferName       = "";
		m_strTransferUID		= "";
		m_strRecipient			= "";
		m_nRecvNumber			= -1;
		m_strRecvDate			= "";
		m_strCharger			= "";
		m_strChargerUID			= "";
		m_strDrafterPosition    = "";
		m_nLineType				= -1;
		m_strIsBatch			= "";
		m_strBodyType 			= "";
		m_strChargeDeptName	    = "";
		m_strChargeDeptCode		= "";
		m_nAnnouncementStatus   = -1;
		m_strApprBizID			= "";
		m_strApprBizName        = "";
		m_strOrginDrafterUID	= "";
		m_strOrginDrafterName	= "";
		m_strOrginDraftDeptCode = "";
		m_strOrginDraftDeptName = "";
		m_strIsModified         = "N";
		m_strHasOpinion         = "N";

// 공람게시를 위한 추가인자
		m_strPostDeptName		= "";
		m_strPostDeptCode		= "";
		m_strPostName			= "";
		m_strPostUID			= "";
		m_strPostDate			= "";
		m_strIsPost				= "";
		m_strPostPosition		= "";
		m_strPostPeriod         = "";

		m_nRecipientCount	    = -1;
		m_strIsPubDoc			= "";
		m_strIsAdminMIS			= "";
		m_strSenderCompName		= "";

		m_strTransferChargerName= "";
		m_strTransferChargerUID = "";

		m_strOwnUserUID = "";

		m_strConcernKeepDate = "";
 		m_strConcernDocStatus = "";
	}

	/**
	 * 의견작성 여부 반환.
	 * @return String
	 */
	public String getHasOpinion()
	{
		return m_strHasOpinion;
	}

	/**
	 * 관심문서함 보관일자 반환.
	 * @return String
	 */
	public String getConcernKeepDate()
	{
		return m_strConcernKeepDate;
	}

	/**
	 * 관심문서함 문서상태정보(원래 문서가 있었던 함 정보) 반환.
	 * @return String
	 */
	public String getConcernDocStatus()
	{
		return m_strConcernDocStatus;
	}

	/**
	 * 문서를 오픈하는 사용자 UID 반환.
	 * @return String
	 */
	public String getOwnUserUID()
	{
		return m_strOwnUserUID;
	}

	/**
	 * 문서 수정 여부 반환.
	 * @return String
	 */
	public String getIsModified()
	{
		return m_strIsModified;
	}

	/**
	 * 공람 게시기간 반환.
	 * @return String
	 */
	public String getPostPeriod()
	{
		return m_strPostPeriod;
	}

	/**
	 * 기안부서 기안자 UID 반환.
	 * @return String
	 */
	public String getOrginDrafterUID()
	{
		return m_strOrginDrafterUID;
	}

	/**
	 * 기안부서 기안자 명 반환.
	 * @return String
	 */
	public String getOrginDrafterName()
	{
		return m_strOrginDrafterName;
	}

	/**
	 * 기안부서 부서 코드 반환.
	 * @return String
	 */
	public String getOrginDraftDeptCode()
	{
		return m_strOrginDraftDeptCode;
	}

	/**
	 * 기안부서 부서명 반환.
	 * @return String
	 */
	public String getOrginDraftDeptName()
	{
		return m_strOrginDraftDeptName;
	}

	/**
	 * 업무 ID 반환.
	 * @return String
	 */
	public String getApprBizID()
	{
		return m_strApprBizID;
	}

	/**
	 * 업무 명 반환.
	 * @return String
	 */
	public String getApprBizName()
	{
		return m_strApprBizName;
	}

	/**
	 * 공표 여부 반환.
	 * @return int
	 */
	public int getAnnouncementStatus()
	{
		return m_nAnnouncementStatus;
	}

	/**
	 * 이송자 명 반환.
	 * @return String
	 */
	public String getTransferChargerName()
	{
		return m_strTransferChargerName;
	}

	/**
	 * 이송자 UID 반환.
	 * @return String
	 */
	public String getTransferChargerUID()
	{
		return m_strTransferChargerUID;
	}

	/**
	 * 현재 결재자의 Role 반환.
	 * @return int
	 */
	public int getCurrentRole()
	{
		return m_nCurrentRole;
	}

	/**
	 * Document Status 반환.
	 * @return int
	 */
	public int getDocumentStatus()
	{
		return m_nDocumentStatus;
	}

	/**
	 * 현재 결재자의 Serial Order 반환.
	 * @return int
	 */
	public int getOwnSerialOrder()
	{
		return m_nOwnSerialOrder;
	}

	/**
	 * 현재 결재자 이름 반환.
	 * @return String
	 */
	public String getCurrentApprover()
	{
		return m_strCurrentApprover;
	}

	/**
	 * 현재 결재자 UID 반환.
	 * @return String
	 */
	public String getCurrentApproverUID()
	{
		return m_strCurrentApproverUID;
	}

	/**
	 * 현재 결재자 부서명 반환.
	 * @return String
	 */
	public String getCurrentDepartment()
	{
		return m_strCurrentDepartment;
	}

	/**
	 * Document ID 반환.
	 * @return String
	 */
	public String getDocID()
	{
		return m_strDocID;
	}

	/**
	 * 기안 일자 반환.
	 * @return String
	 */
	public String getDraftDate()
	{
		return m_strDraftDate;
	}

	/**
	 * 기안 부서명 반환.
	 * @return String
	 */
	public String getDraftDepartment()
	{
		return m_strDraftDepartment;
	}

	/**
	 * 기안자 이름 반환.
	 * @return String
	 */
	public String getDrafter()
	{
		return m_strDrafter;
	}

	/**
	 * 기안자 UID 반환.
	 * @return String
	 */
	public String getDrafterUID()
	{
		return m_strDrafterUID;
	}

	/**
	 * 기안자 부서 코드 반환.
	 * @return String
	 */
	public String getDraftDepartmentCode()
	{
		return m_strDraftDeptCode;
	}

	/**
	 * 첨부 여부 반환.
	 * @return String
	 */
	public String getIsAttached()
	{
		return m_strIsAttached;
	}

	/**
	 * 현재 결재자의 Line Name 반환.
	 * @return String
	 */
	public String getOwnLineName()
	{
		return m_strOwnLineName;
	}

	/**
	 * 문서 제목 반환.
	 * @return String
	 */
	public String getTitle()
	{
		return m_strTitle;
	}

	/**
	 * 완결자 Role 반환.
	 * @return int
	 */
	public int getCompletedRole()
	{
		return m_nCompletedRole;
	}

	/**
	 * 완결자 이름 반환.
	 * @return String
	 */
	public String getChief()
	{
		return m_strChief;
	}

	/**
	 * 완결자 UID 반환.
	 * @return String
	 */
	public String getChiefUID()
	{
		return m_strChiefUID;
	}

	/**
	 * 완결일자 반환.
	 * @return String
	 */
	public String getCompletedDate()
	{
		return m_strCompletedDate;
	}

	/**
	 * Flow Status 반환.
	 * @return int
	 */
	public int getFlowStatus()
	{
		return m_nFlowStatus;
	}

	/**
	 * Classification 반환.
	 * @return int
	 */
	public int getClassification(int nCab)
	{
		if (nCab == 1)
		{
			if ((m_nCompletedRole >= ApprovalConstants.APPROVER_ROLE_SELF) &&
				(m_nCompletedRole <= ApprovalConstants.APPROVER_ROLE_LAST_INDEX))
			{
				return m_nCompletedRole;
			}
			else
			{
				return -1;
			}

		}

		else
		{
			if ((m_nCurrentRole >= ApprovalConstants.APPROVER_ROLE_SELF) &&
				(m_nCurrentRole <= ApprovalConstants.APPROVER_ROLE_LAST_INDEX))
			{
				return m_nCurrentRole;
			}
			else
			{
				return -1;
			}
		}

	}

	/**
	 * 문서 Status 반환.
	 * @return int
	 */
	public int getStatus()
	{
		int nStatus = -1;

		switch (m_nFlowStatus)
		{
			case ApprovalConstants.FLOW_STATUS_BEFORE_COMPLETED:
			case ApprovalConstants.FLOW_STATUS_CIRCULAR:
				if ((m_nDocumentStatus >= ApprovalConstants.DOC_STATUS_DRAFTING) &&
					(m_nDocumentStatus < ApprovalConstants.DOC_STATUS_LAST_INDEX))
				{
					nStatus = m_nDocumentStatus;
				}
				else
				{
					nStatus = -1;
				}

				break;
			default :
				if ((m_nFlowStatus >= ApprovalConstants.FLOW_STATUS_BEFORE_COMPLETED) &&
					(m_nFlowStatus <= ApprovalConstants.FLOW_STATUS_LAST_INDEX))
				{
					nStatus = m_nFlowStatus + 100;
				}

				else
				{
					nStatus = -1;
				}
		}

		return nStatus;
	}

	/**
	 * 현재 결재자의 Role 반환.
	 * @return int
	 */
	public int getApproverRole()
	{
		return m_nApproverRole;
	}

	/**
	 * 현재 결재자의 문서 오픈 여부
	 * @param nCabinet 함 Type
	 * @return String
	 */
	public String getIsOpen(int nCabinet)
	{
		String strIsOpen = "N";

		if (nCabinet == 0)
			strIsOpen = m_strOwnIsOpen;
		else
			strIsOpen = m_strIsOpen;

		return strIsOpen;
	}

	/**
	 * 보안 결재 비밀번호 반환.
	 * @return String
	 */
	public String getSecurityPass()
	{
		return m_strSecurityPass;
	}

	/**
	 * 보안 결재문서 여부 반환.
	 * @return String
	 */
	public String getIsSecurityDoc()
	{
		return m_strSecurityPass;
	}

	/**
	 * 긴급도 반환.
	 * @return String
	 */
	public String getUrgency()
	{
		return m_strUrgency;
	}

	/**
	 * 문서 생성일자 반환.
	 * @return String
	 */
	public String getCreatedDate()
	{
		return m_strCreatedDate;
	}

	/**
	 * 문서 삭제일자 반환.
	 * @return String
	 */
	public String getDeletedDate()
	{
		return m_strDeletedDate;
	}

	/**
	 * 삭제된 함 반환.
	 * @return int
	 */
	public int getOriginalPosition()
	{
		int nPosition = -1;

		if (m_nDocumentStatus == ApprovalConstants.DOC_STATUS_DRAFT_RESERVED)
		{
			nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_PRIVATE;
		}
		else if (m_nDocumentStatus == ApprovalConstants.DOC_STATUS_REJECTED)
		{
			if (m_nOwnSerialOrder != 0)
			{
				nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_COMPLETE;
			}
			else
			{
				nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_REJECT;
			}
		}
		else
		{
			if (m_nApproverRole == ApprovalConstants.APPROVER_ROLE_SELF ||
				m_nApproverRole == ApprovalConstants.APPROVER_ROLE_DRAFT)
			{
				nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_SUBMIT;
			}
			else if (m_nApproverRole == ApprovalConstants.APPROVER_ROLE_POST ||
					 m_nApproverRole == ApprovalConstants.APPROVER_ROLE_REPORTED)
			{
				nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_AFTERAPPROVE;
			}
			else
			{
				nPosition = ApprovalConstants.APPROVE_CABINET_TYPE_COMPLETE;
			}
		}

		return nPosition;
	}

	/**
	 * 합계 반환.
	 * @return int
	 */
	public int getTotalCount()
	{
		return m_nTotalCount;
	}

	/**
	 * 문서 구분 반환.
	 * @return String
	 */
	public String getDocCategory()
	{
		return m_strDocCategory;
	}

	/**
	 * 분류번호 반환.
	 * @return String
	 */
	public String getClassNumber()
	{
		return m_strClassNumber;
	}

	/**
	 * 기관기호 반환.
	 * @return String
	 */
	public String getOrgSymbol()
	{
		return m_strOrgSymbol;
	}

	/**
	 * 시행범위 반환.
	 * @return String
	 */
	public String getEnforceBound()
	{
		return m_strEnforceBound;
	}

	/**
	 * 문서 등록 번호 반환.
	 * @return int
	 */
	public int getSerialNumber()
	{
		return m_nSerialNumber;
	}

	/**
	 * 시행 일자 반환.
	 * @return String
	 */
	public String getEnforceDate()
	{
		return m_strEnforceDate;
	}

	/**
	 * 발신 부서명 반환.
	 * @return String
	 */
	public String getSenderDeptName()
	{
		return m_strSenderDeptName;
	}

	/**
	 * 전달자 유형 반환.
	 * @return int
	 */
	public int getDelivererType()
	{
		return m_nDelivererType;
	}

	/**
	 * 시행 방법 반환.
	 * @return String
	 */
	public String getEnforceMethod()
	{
		return m_strEnforceMethod;
	}

	/**
	 * 인수자 이름 반환.
	 * @return String
	 */
	public String getTransferName()
	{
		return m_strTransferName;
	}

	/**
	 * 인수자 UID 반환.
	 * @return String
	 */
	public String getTransferUID()
	{
		return m_strTransferUID;
	}

	/**
	 * 수신자 명 반환.
	 * @return String
	 */
	public String getRecipient()
	{
		return m_strRecipient;
	}

	/**
	 * 접수 번호 반환.
	 * @return int
	 */
	public int getRecvNumber()
	{
		return m_nRecvNumber;
	}

	/**
	 * 접수 일자 반환.
	 * @return String
	 */
	public String getRecvDate()
	{
		return m_strRecvDate;
	}

	/**
	 * 담당자 이름 반환.
	 * @return String
	 */
	public String getCharger()
	{
		return m_strCharger;
	}

	/**
	 * 담당자 UID 반환.
	 * @return String
	 */
	public String getChargerUID()
	{
		return m_strChargerUID;
	}

	/**
	 * 기안자 직위 반환.
	 * @return String
	 */
	public String getDrafterPosition()
	{
		return m_strDrafterPosition;
	}

	/**
	 * 라인 유형 반환.
	 * @return int
	 */
	public int getLineType()
	{
		return m_nLineType;
	}

	/**
	 * Dept Classification 반환.
	 * @return int
	 */
	public int getDeptClassification()
	{
		if ((m_nLineType >= ApprovalConstants.LINE_TYPE_GENERAL) &&
			(m_nLineType <= ApprovalConstants.LINE_TYPE_LAST_INDEX))
		{
			return m_nLineType;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * 일괄 기안문 인지 여부 반환.
	 * @return String
	 */
	public String getIsBatch()
	{
		return m_strIsBatch;
	}

	/**
	 * 본문 작성기 종류 반환.
	 * @return String
	 */
	public String getBodyType()
	{
		return m_strBodyType;
	}

	/**
	 * 담당자 부서명 반환.
	 * @return String
	 */
	public String getChargeDeptName()
	{
		return m_strChargeDeptName;
	}

	/**
	 * 담당자 부서 코드 반환.
	 * @return String
	 */
	public String getChargeDeptCode()
	{
		return m_strChargeDeptCode;
	}

	/**
	 * 공람 게시 부서 명 반환.
	 * @return String
	 */
	public String getPostDeptName()
	{
		return m_strPostDeptName;
	}

	/**
	 * 공람 게시 부서 코드 반환.
	 * @return String
	 */
	public String getPostDeptCode()
	{
		return m_strPostDeptCode;
	}

	/**
	 * 공람 게시자 명 반환.
	 * @return String
	 */
	public String getPostName()
	{
		return m_strPostName;
	}

	/**
	 * 공람 게시자 UID 반환.
	 * @return String
	 */
	public String getPostUID()
	{
		return m_strPostUID;
	}

	/**
	 * 공람 게시 일자 반환.
	 * @return String
	 */
	public String getPostDate()
	{
		return m_strPostDate;
	}

	/**
	 * 공람 게시 여부 반환.
	 * @return String
	 */
	public String getIsPost()
	{
		return m_strIsPost;
	}

	/**
	 * 게시한 위치(등록대장/접수대장) 반환.
	 * @return String
	 */
	public String getPostPosition()
	{
		return m_strPostPosition;
	}

	/**
	 * 수신처 개수 반환.
	 * @return int
	 */
	public int getRecipientCount()
	{
		return m_nRecipientCount;
	}

	/**
	 * 행정 기관간 유통 문서 여부 반환.
	 * @return String
	 */
	public String getIsPubDoc()
	{
		return m_strIsPubDoc;
	}

	/**
	 * 행정 정보 시스템에서 상신된 문서 여부 반환.
	 * @return String
	 */
	public String getIsAdminMIS()
	{
		return m_strIsAdminMIS;
	}

	/**
	 * 발신자 회사명 반환.
	 * @return String
	 */
	public String getSenderCompName()
	{
		return m_strSenderCompName;
	}

	/**
	 * 공람게시 부서 코드 반환.
	 * @return String
	 */
	public String getPostFolderID()
	{
		return m_strPostFolderID;
	}

	/**
	 * 공람게시 부서명 반환.
	 * @return String
	 */
	public String getPostFolderName()
	{
		return m_strPostFolderName;
	}

	/**
	 * 현재 결재자 이름 설정.
	 * @param strCurrentApprover 현재 결재자 이름
	 */
	public void setCurrentApprover(String strCurrentApprover)
	{
		m_strCurrentApprover = strCurrentApprover;
	}

	/**
	 * 현재 결재자 UID 설정.
	 * @param strCurrentApproverUID 현재 결재자 UID
	 */
	public void setCurrentApproverUID(String strCurrentApproverUID)
	{
		m_strCurrentApproverUID = strCurrentApproverUID;
	}

	/**
	 * 현재 결재자 부서명 설정.
	 * @param strCurrentDepartment 현재 결재자 부서명
	 */
	public void setCurrentDepartment(String strCurrentDepartment)
	{
		m_strCurrentDepartment = strCurrentDepartment;
	}

	/**
	 * 현재 결재자 Role 설정.
	 * @param nCurrentRole 현재 결재자 Role
	 */
	public void setCurrentRole(int nCurrentRole)
	{
		m_nCurrentRole = nCurrentRole;
	}

	/**
	 * 문서 DOC_ID 설정.
	 * @param strDocID 문서 DOC_ID
	 */
	public void setDocID(String strDocID)
	{
		m_strDocID = strDocID;
	}

	/**
	 * 문서 상태 설정.
	 * @param documentStatus 문서 상태
	 */
	public void setDocumentStatus(int nDocumentStatus)
	{
		m_nDocumentStatus = nDocumentStatus;
	}

	/**
	 * 기안 일자 설정.
	 * @param strDraftDate 기안 일자
	 */
	public void setDraftDate(String strDraftDate)
	{
		m_strDraftDate = strDraftDate;
	}

	/**
	 * 기안 부서명 설정.
	 * @param strDraftDepartment 기안 부서명
	 */
	public void setDraftDepartment(String strDraftDepartment)
	{
		m_strDraftDepartment = strDraftDepartment;
	}

	/**
	 * 기안자 이름 설정.
	 * @param strDrafter 기안자 이름
	 */
	public void setDrafter(String strDrafter)
	{
		m_strDrafter = strDrafter;
	}

	/**
	 * 기안자 UID 설정.
	 * @param strDrafterUID 기안자 UID
	 */
	public void setDrafterUID(String strDrafterUID)
	{
		m_strDrafterUID = strDrafterUID;
	}

	/**
	 * 기안자 부서 코드 설정.
	 * @param strDraftDepartmentCode 기안자 부서 코드
	 */
	public void setDraftDepartmentCode(String strDraftDepartmentCode)
	{
		m_strDraftDeptCode = strDraftDepartmentCode;
	}

	/**
	 * 첨부 여부 설정.
	 * @param strIsAttached 첨부 여부
	 */
	public void setIsAttached(String strIsAttached)
	{
		m_strIsAttached = strIsAttached;
	}

	/**
	 * 현재 결재자 결재 라인명 설정.
	 * @param strOwnLineName 현재 결재자 결재 라인명
	 */
	public void setOwnLineName(String strOwnLineName)
	{
		m_strOwnLineName = strOwnLineName;
	}

	/**
	 * 현재 결재자 Serial Order 설정.
	 * @param nOwnSerialOrder 현재 결재자 Serial Order
	 */
	public void setOwnSerialOrder(int nOwnSerialOrder)
	{
		m_nOwnSerialOrder = nOwnSerialOrder;
	}

	/**
	 * 문서 제목 설정.
	 * @param strTitle 문서제목
	 */
	public void setTitle(String strTitle)
	{
		m_strTitle = strTitle;
	}

	/**
	 * 완결자 Role 설정.
	 * @param nCompletedRole 완결자 Role
	 */
	public void setCompletedRole(int nCompletedRole)
	{
		m_nCompletedRole = nCompletedRole;
	}

	/**
	 * 완결자 이름 설정.
	 * @param strChief 완결자 이름
	 */
	public void setChief(String strChief)
	{
		m_strChief = strChief;
	}

	/**
	 * 완결자 UID 설정.
	 * @param strChiefUID 완결자 UID
	 */
	public void setChiefUID(String strChiefUID)
	{
		m_strChiefUID = strChiefUID;
	}

	/**
	 * 완결 일자 설정.
	 * @param strCompletedDate 완결 일자
	 */
	public void setCompletedDate(String strCompletedDate)
	{
		m_strCompletedDate = strCompletedDate;
	}

	/**
	 * 유통 상태 설정.
	 * @param nFlowStatus 유통 상태
	 */
	public void setFlowStatus(int nFlowStatus)
	{
		m_nFlowStatus = nFlowStatus;
	}

	/**
	 * 현 결재자 Role 설정.
	 * @param nApproverRole 현재 결재자 Role
	 */
	public void setApproverRole(int nApproverRole)
	{
		m_nApproverRole = nApproverRole;
	}

	/**
	 * 문서 오픈 여부 설정.
	 * @param strIsOpened 문서 오픈 여부
	 */
	public void setIsOpen(String strIsOpened)
	{
		m_strIsOpen = strIsOpened;
	}

	/**
	 * 보안 결재 비밀 번호 설정.
	 * @param strSecurityPass 보안결재 비밀번호
	 */
	public void setSecurityPass(String strSecurityPass)
	{
		m_strSecurityPass = strSecurityPass;
	}

	/**
	 * 긴급도 설정.
	 * @param strUrgency 긴급도
	 */
	public void setUrgency(String strUrgency)
	{
		m_strUrgency = strUrgency;
	}

	/**
	 * 문서 생성일자 설정.
	 * @param strCreatedDate 문서 생성 일자
	 */
	public void setCreatedDate(String strCreatedDate)
	{
		m_strCreatedDate = strCreatedDate;
	}

	/**
	 * 문서 삭제일자 설정.
	 * @param strDeletedDate 문서 삭제 일자
	 */
	public void setDeletedDate(String strDeletedDate)
	{
		m_strDeletedDate = strDeletedDate;
	}

	/**
	 * 문서가 삭제된 함 위치 설정.
	 * @param strOriginalPosition 문서가 삭제된 함 위치
	 */
	public void setOriginalPosition(String strOriginalPosition)
	{
		m_strOriginPos = strOriginalPosition;
	}

	/**
	 * 현 결재자의 문서 오픈 여부 설정.
	 * @param strOwnIsOpen 현결재자의 문서 오픈 여부
	 */
	public void setOwnIsOpen(String strOwnIsOpen)
	{
		m_strOwnIsOpen = strOwnIsOpen;
	}

	/**
	 * 합계 설정.
	 * @param nTotalCount 합계
	 */
	public void setTotalCount(int nTotalCount)
	{
		m_nTotalCount = nTotalCount;
	}

	/**
	 * 문서 구분 설정.
	 * @param strDocCategory 문서 구분
	 */
	public void setDocCategory(String strDocCategory)
	{
		m_strDocCategory = strDocCategory;
	}

	/**
	 * 분류 번호 설정.
	 * @param strClassNumber 분류 번호
	 */
	public void setClassNumber(String strClassNumber)
	{
		m_strClassNumber = strClassNumber;
	}

	/**
	 * 기관 기호 설정.
	 * @param strOrgSymbol 기관 기호
	 */
	public void setOrgSymbol(String strOrgSymbol)
	{
		m_strOrgSymbol = strOrgSymbol;
	}

	/**
	 * 시행 범위 설정.
	 * @param strEnforceBound 시행 범위
	 */
	public void setEnforceBound(String strEnforceBound)
	{
		m_strEnforceBound = strEnforceBound;
	}

	/**
	 * 문서 등록 번호 설정.
	 * @param nSerialNumber 문서 등록 번호
	 */
	public void setSerialNumber(int nSerialNumber)
	{
		m_nSerialNumber = nSerialNumber;
	}

	/**
	 * 시행 일자 설정.
	 * @param strEnforceDate 시행 일자
	 */
	public void setEnforceDate(String strEnforceDate)
	{
		m_strEnforceDate = strEnforceDate;
	}

	/**
	 * 발신 부서명 설정.
	 * @param strSenderDeptName 발신부서명
	 */
	public void setSenderDeptName(String strSenderDeptName)
	{
		m_strSenderDeptName = strSenderDeptName;
	}

	/**
	 * 전달자 유형 설정.
	 * @param nDelivererType 전달자 유형
	 */
	public void setDelivererType(int nDelivererType)
	{
		m_nDelivererType = nDelivererType;
	}

	/**
	 * 시행 방법 설정.
	 * @param strEnforceMethod 시행 방법
	 */
	public void setEnforceMethod(String strEnforceMethod)
	{
		m_strEnforceMethod = strEnforceMethod;
	}

	/**
	 * 인수자 이름 설정.
	 * @param strTransferName 인수자 이름
	 */
	public void setTransferName(String strTransferName)
	{
		m_strTransferName = strTransferName;
	}

	/**
	 * 인수자 UID 설정.
	 * @param strTransferUID 인수자 UID
	 */
	public void setTransferUID(String strTransferUID)
	{
		m_strTransferUID = strTransferUID;
	}

	/**
	 * 수신자 명 설정.
	 * @param strRecipient 수신자 명
	 */
	public void setRecipient(String strRecipient)
	{
		m_strRecipient = strRecipient;
	}

	/**
	 * 접수 번호 설정.
	 * @param nRecvNumber 접수 번호
	 */
	public void setRecvNumber(int nRecvNumber)
	{
		m_nRecvNumber = nRecvNumber;
	}

	/**
	 * 접수 일자 설정.
	 * @param strRecvDate 접수 일자
	 */
	public void setRecvDate(String strRecvDate)
	{
		m_strRecvDate = strRecvDate;
	}

	/**
	 * 담당자 이름 설정.
	 * @param strCharger 담당자 이름
	 */
	public void setCharger(String strCharger)
	{
		m_strCharger = strCharger;
	}

	/**
	 * 담당자 UID 설정.
	 * @param strChargerUID 담당자 UID
	 */
	public void setChargerUID(String strChargerUID)
	{
		m_strChargerUID = strChargerUID;
	}

	/**
	 * 기안자 직위명 설정.
	 * @param strDrafterPosition 기안자 직위명
	 */
	public void setDrafterPosition(String strDrafterPosition)
	{
		m_strDrafterPosition = strDrafterPosition;
	}

	/**
	 * 현 결재 라인의 라인 유형 설정.
	 * @param nLineType 현 결재 라인의 라인 유형
	 */
	public void setLineType(int nLineType)
	{
		m_nLineType = nLineType;
	}

	/**
	 * 일괄 결재 여부 설정.
	 * @param strIsBatch 일괄 결재 여부
	 */
	public void setIsBatch(String strIsBatch)
	{
		m_strIsBatch = strIsBatch;
	}

	/**
	 * 본문 작성기 종류 설정.
	 * @param strBodyType 본문 작성기 종류
	 */
	public void setBodyType(String strBodyType)
	{
		m_strBodyType = strBodyType;
	}

	/**
	 * 담당자 부서명 설정.
	 * @param strChargeDeptName 담당자 부서명
	 */
	public void setChargeDeptName(String strChargeDeptName)
	{
		m_strChargeDeptName = strChargeDeptName;
	}

	/**
	 * 담당자 부서 코드 설정.
	 * @param strChargeDeptCode 담당자 부서 코드
	 */
	public void setChargeDeptCode(String strChargeDeptCode)
	{
		m_strChargeDeptCode = strChargeDeptCode;
	}

	/**
	 * 공람 게시 부서명 설정.
	 * @param strPostDeptName 공람 게시 부서명
	 */
	public void setPostDeptName(String strPostDeptName)
	{
		m_strPostDeptName = strPostDeptName;
	}

	/**
	 * 공람 게시 부서 코드 설정.
	 * @param strPostDeptCode 공람 게시 부서 코드
	 */
	public void setPostDeptCode(String strPostDeptCode)
	{
		m_strPostDeptCode = strPostDeptCode;
	}

	/**
	 * 공람 게시자 이름 설정.
	 * @param strPostName 공람 게시자 이름
	 */
	public void setPostName(String strPostName)
	{
		m_strPostName = strPostName;
	}

	/**
	 * 공람 게시자 UID 설정.
	 * @param strPostUID 공람 게시자 UID
	 */
	public void setPostUID(String strPostUID)
	{
		m_strPostUID = strPostUID;
	}

	/**
	 * 공람 게시 일자 설정.
	 * @param strPostDate 공람 게시 일자
	 */
	public void setPostDate(String strPostDate)
	{
		m_strPostDate = strPostDate;
	}

	/**
	 * 공람 게시 여부 설정.
	 * @param strIsPost 공람 게시 여부
	 */
	public void setIsPost(String isPost)
	{
		m_strIsPost = isPost;
	}

	/**
	 * 수신처의 개수 설정.
	 * @param nRecipientCount 수신처의 개수
	 */
	public void setRecipientCount(int nRecipientCount)
	{
		m_nRecipientCount = nRecipientCount;
	}

	/**
	 * 행정 기관 유통 문서 여부 설정.
	 * @param strIsPubdoc 행정 기관 유통 문서 여부
	 */
	public void setIsPubDoc(String strIsPubdoc)
	{
		m_strIsPubDoc = strIsPubdoc;
	}

	/**
	 * 공람 게시가 된 대장 설정.
	 * @param strPostPosition 공람 게시가 된 대장
	 */
	public void setPostPosition(String strPostPosition)
	{
		m_strPostPosition = strPostPosition;
	}

	/**
	 * 행정정보 시스템에서 상신된 문서 여부
	 * @param strIsAdminMIS 행정정보 시스템에서 상신된 문서 여부
	 */
	public void setIsAdminMIS(String strIsAdminMIS)
	{
		m_strIsAdminMIS = strIsAdminMIS;
	}

	/**
	 * 발신 회사명 설정.
	 * @param strSenderCompName 발신 회사명
	 */
	public void setSenderCompName(String strSenderCompName)
	{
		m_strSenderCompName = strSenderCompName;
	}

	/**
	 * 이송자 이름 설정.
	 * @param strTransferChargerName 이송자 이름
	 */
	public void setTransferChargerName(String strTransferChargerName)
	{
		m_strTransferChargerName = strTransferChargerName;
	}

	/**
	 * 이송자 UID 설정.
	 * @param strTransferChargerUID 이송자 UID
	 */
	public void setTransferChargerUID(String strTransferChargerUID)
	{
		m_strTransferChargerUID = strTransferChargerUID;
	}

	/**
	 * 공람 게시 부서 코드 설정.
	 * @param strPostFolderID 공람게시 부서 코드
	 */
	public void setPostFolderID(String strPostFolderID)
	{
		m_strPostFolderID = strPostFolderID;
	}

	/**
	 * 공람 게시 부서명 설정.
	 * @param strPostFolderName 공람 게시 부서명
	 */
	public void setPostFolderName(String strPostFolderName)
	{
		m_strPostFolderName = strPostFolderName;
	}

	/**
	 * 공표 여부 설정.
	 * @param nAnnouncementStatus 공표 여부
	 */
	public void setAnnouncementStatus(int nAnnouncementStatus)
	{
		m_nAnnouncementStatus = nAnnouncementStatus;
	}

	/**
	 * 업무 ID 설정.
	 * @param strApprBizID 업무 ID
	 */
	public void setApprBizID(String strApprBizID)
	{
		m_strApprBizID = strApprBizID;
	}

	/**
	 * 업무명 설정.
	 * @param strApprBizName 업무 명
	 */
	public void setApprBizName(String strApprBizName)
	{
		m_strApprBizName = strApprBizName;
	}

	/**
	 * 기안부서 기안자 UID 설정.
	 * @param strOriginalDrafterUID 기안부서 기안자 UID
	 */
	public void setOrginDrafterUID(String strOriginDrafterUID)
	{
		m_strOrginDrafterUID = strOriginDrafterUID;
	}

	/**
	 * 기안부서 기안자 이름 설정.
	 * @param strOrigianlDrafterName 기안부서 기안자 이름
	 */
	public void setOrginDrafterName(String strOriginDrafterName)
	{
		m_strOrginDrafterName = strOriginDrafterName;
	}

	/**
	 * 기안부서 부서 코드 설정.
	 * @param strOriginDraftDeptCode 기안부서 부서 코드
	 */
	public void setOrginDraftDeptCode(String strOrginDraftDeptCode)
	{
		m_strOrginDraftDeptCode = strOrginDraftDeptCode;
	}

	/**
	 * 기안부서 부서명 설정.
	 * @param strOrginDraftDeptName 기안부서 부서명
	 */
	public void setOrginDraftDeptName(String strOrginDraftDeptName)
	{
		m_strOrginDraftDeptName = strOrginDraftDeptName;
	}

	/**
	 * 게시 기간 설정.
	 * @param strPostPeriod 게시기간
	 */
	public void setPostPeriod(String strPostPeriod)
	{
		m_strPostPeriod = strPostPeriod;
	}

	/**
	 * 수정 여부 설정.
	 * @param strIsModified 수정 여부
	 */
	public void setIsModified(String strIsModified)
	{
		m_strIsModified = strIsModified;
	}

	/**
	 * 문서를 오픈하는 사용자 UID 설정.
	 * @param strOwnUserUID 문서를 오픈하는 사용자 UID
	 */
	public void setOwnUserUID(String strOwnUserUID)
	{
		m_strOwnUserUID = strOwnUserUID;
	}

	/**
	 * 관심문서함 보관일자 설정.
	 * @param strConcernKeepDate 관심문서함 보관일자
	 */
	public void setConcernKeepDate(String strConcernKeepDate)
	{
		m_strConcernKeepDate = strConcernKeepDate;
	}

	/**
	 * 관심문서함 문서상태정보(원래 문서가 있었던 함 정보) 설정.
	 *@param strConcernDocStatus 관심문서함 문서상태정보
	 */
	public void setConcernDocStatus(String strConcernDocStatus)
	{
		m_strConcernDocStatus = strConcernDocStatus;
	}

	/**
	 * 의견작성 여부 설정.
	 * @param strHasOpinion 의견작성여부
	 */
	public void setHasOpinion(String strHasOpinion)
	{
		m_strHasOpinion = strHasOpinion;
	}
}

