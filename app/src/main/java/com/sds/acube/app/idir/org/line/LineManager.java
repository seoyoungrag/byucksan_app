package com.sds.acube.app.idir.org.line;

/**
 * LineManager.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : LineManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.line.LineManager.java
 */
public class LineManager 
{
	/**
	 */
	private ConnectionParam 	m_connectionParam = null;
	private String 			m_strLastError = "";
		
	public LineManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;		
	}
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}
	
	/**
	 * 결재 그룹 등록 
	 * @param approvalLine 결재 라인 정보 
	 * @param approvers     결재 라인에 속한 결재자 정보 
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean registerApprovalLine(ApprovalLine approvalLine,
								  Approvers    approvers,
								  int		   nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		boolean 		bReturn = false;
		
		bReturn = approverHandler.registerApprovalLine(approvalLine,
													   approvers,
													   nType);
		if (!bReturn)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return bReturn; 		
	}	
	
	/**
	 * 결재 그룹에 속한 결재자 정보 삭제 
	 * @param strLineID    삭제할 결재 그룹 ID
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean deleteApprovalLine(String strLineID, int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		boolean 		bReturn = false;
		
		bReturn = approverHandler.deleteApprovalLine(strLineID, nType);
		
		if (!bReturn)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return bReturn; 		
	}
	
	/** 
	 * 주어진 owner의 결재 경로 그룹들.
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param nType		공용(0)/개인(1)
	 * @return ApprovalLines
	 */
	public ApprovalLines getApprovalLines(String strOwnerID, int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		ApprovalLines	approvalLines = null;
		
		approvalLines = approverHandler.getApprovalLines(strOwnerID, nType);
		 
		if (approvalLines == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvalLines;	
	}
	
	/** 
	 * 주어진 owner의 결재 경로 그룹들.
	 * @param strOwnerID 		부서 ID/ User ID
	 * @param nType			공용(0)/개인(1)
	 * @param nLineCategory    일반(0)/담당,선람(1)
	 * @return ApprovalLines
	 */
	public ApprovalLines getApprovalLines(String strOwnerID, 
										  int nType,
										  int nLineCategory)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		ApprovalLines	approvalLines = null;
		
		approvalLines = approverHandler.getApprovalLines(strOwnerID,
														 nType, nLineCategory);
		if (approvalLines == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvalLines;
	}
	
	/** 
	 * 주어진 Line ID를 가지는 결재 경로 그룹.
	 * @param strLineID 		부서 ID/ User ID
	 * @param nType			    공용(0)/개인(1)
	 * @return ApprovalLine
	 */
	public ApprovalLine getApprovalLineByLineID(String strLineID, 
										  		int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		ApprovalLine	approvalLine = null;
		
		approvalLine = approverHandler.getApprovalLineByLineID(strLineID, nType);
		
		if (approvalLine == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvalLine;
	}
	
	/**
	 * 주어진 결재 그룹의 결재자들을 얻음.
	 * @param strLineID 	결재 라인 ID
	 * @param nType 		공용(0) / 개인(1)
	 * @return Approvers
	 */	
	public Approvers getApprovers(String strLineID, int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		Approvers		approvers = null;
		
		approvers = approverHandler.getApprovers(strLineID, nType);
		 
		if (approvers == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvers;			
	}
	
	/**
	 * 즐겨쓰는 결재 경로를 가져오는 함수 
	 * @param 	strOwnerID	결재 그룹 소유자 ID
	 * @param	nType 		공용(0) / 개인(1)
	 * @return Approvers	결재 그룹에 속한 사용자
	 */
	public Approvers getFavoriteApprovers(String strOwnerID, int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		Approvers		approvers = null;
		
		approvers = approverHandler.getFavoriteApprovers(strOwnerID, nType);
		
		if (approvers == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvers;
	}
	
	/**
	 * 즐겨쓰는 결재 Line 정보를 가져오는 함수 
	 * @param 	strOwnerID		결재 그룹 소유자 ID
	 * @param	nType 			공용(0) / 개인(1)
	 * @return ApprovalLine	결재 그룹에 속한 사용자
	 */
	public ApprovalLine getFavoriteLine(String strOwnerID, int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		ApprovalLine	approvalLine = null;
		
		approvalLine = approverHandler.getFavoriteLine(strOwnerID, nType);
		if(approvalLine == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvalLine;
	}
	
	/**
	 * Favorite 결재 경로 정보 수정 
	 * @param strOwnerID 	결재 경로 그룹 소유자 ID
	 * @param strLineID 	결재 그룹 ID
	 * @param strFavorite 	Favorite 결재 그룹 설정 여부 (Y/N)
	 * @param nType 		공용(0) / 개인(1)
	 * @return boolean
	 */
	public boolean registerFavoriteLine(String strOwnerID,
										  String strLineID,
										  String strFavorite,
										  int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		boolean		bReturn = false;
		
		bReturn = approverHandler.registerFavoriteLine(strOwnerID, strLineID,
													   strFavorite, nType);
		if (!bReturn)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/** 
	 * 주어진 owner의 주어진 APPR_BIZ_ID를 가지는 결재 경로 그룹들.
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param nType			공용(0)/개인(1)
	 * @return ApprovalLine
	 */
	public ApprovalLine getApprovalLinesByBizID(String strOwnerID, 
												String strApprBizID,
												int nType)
	{
		ApproverHandler approverHandler = new ApproverHandler(m_connectionParam);
		ApprovalLine	approvalLine = null;
		
		approvalLine = approverHandler.getApprovalLinesByBizID(strOwnerID, strApprBizID, nType);
		if (approvalLine == null)
		{
			m_strLastError = approverHandler.getLastError();
		}
		
		return approvalLine;	
	}
		
	/** 
	 * 수신그룹 조회
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param strEnforceBound 시행범위
	 * @param nType		공용(0)/개인(1)
	 * @return RecipLines
	 */
	public RecipLines getRecipLines(String strOwnerID, String strEnforceBound, int nType)
	{
		RecipientHandler recipientHandler = new RecipientHandler(m_connectionParam);
		RecipLines	recipLines = null;
		
		recipLines = recipientHandler.getRecipLines(strOwnerID, strEnforceBound, nType);	
			 
		if (recipLines == null)
		{
			m_strLastError = recipientHandler.getLastError();
		}
		
		return recipLines;	
	}
	
	/**
	 * 수신그룹의 수신처들 조회.
	 * @param strLineID 	수신그룹 ID
	 * @param nType 		공용(0) / 개인(1)
	 * @return Recipients
	 */	
	public Recipients getRecipients(String strLineID, int nType)
	{
		RecipientHandler recipientHandler = new RecipientHandler(m_connectionParam);
		Recipients		recipients = null;
		
		recipients = recipientHandler.getRecipients(strLineID, nType);
		 
		if (recipients == null)
		{
			m_strLastError = recipientHandler.getLastError();
		}
		
		return recipients;			
	}
	
	/**
	 * 수신그룹 등록 
	 * @param approvalLine 수신그룹 정보 
	 * @param approvers     수신그룹에 속한 수신처 정보 
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean registerRecipLine(RecipLine RecipLine,
								  Recipients    recipients,
								  int		   nType)
	{
		RecipientHandler recipientHandler = new RecipientHandler(m_connectionParam);
		boolean 		bReturn = false;
		
		bReturn = recipientHandler.registerRecipLine(RecipLine,
													   recipients,
													   nType);
		if (!bReturn)
		{
			m_strLastError = recipientHandler.getLastError();
		}
		
		return bReturn; 		
	}	
	
	/**
	 * 수신그룹에 속한 수신처 정보 삭제 
	 * @param strLineID    삭제할 수신그룹 ID
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean deleteRecipLine(String strLineID, int nType)
	{
		RecipientHandler recipientHandler = new RecipientHandler(m_connectionParam);
		boolean 		bReturn = false;
		
		bReturn = recipientHandler.deleteRecipLine(strLineID, nType);
		
		if (!bReturn)
		{
			m_strLastError = recipientHandler.getLastError();
		}
		
		return bReturn; 		
	}
}
