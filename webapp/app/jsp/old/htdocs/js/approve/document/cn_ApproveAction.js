
var g_nActionType = 0;	// 0 : Default, 1 : 기안, 2 : 결재, 3:반려, 4 : 심사, 5 : 발송, 6 : 배부, 7 : 배부반송, 8 : 접수, 9 : 접수반송,
						// 10 : 기안회수, 11 : 재발송, 12 : 심사반송, 13 : 반대, 14: 기안보류, 15: 결재보류
						// 21 : 배부이송, 22 : 배부경유, 23 : 접수이송, 24 : 접수경유, 25 : 접수후 반송
var g_bDoSendApproval = false;

var g_strReturnFunction = "";
var g_strActionUrl = "";
var g_arrMessage = new Array();

var g_strApprovalDocXml = "";
var g_nPrevActionType = 0;

function onSubmitApprove(bNewDocID)
{
// 재기안시 기존 문서 Update로 처리 (향후 옵션사항)
	if (bNewDocID)
	{
//		clearModifiedAttach();

		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var strLineName = getLineName(objApprovalLine);
			var strDocStatus = getDocStatus(objApprovalLine);

//			if (strLineName == "0" && strDocStatus != "2" && strDocStatus != "4" && strDocStatus != "6")	// 법무부
			if (strLineName == "0" && strDocStatus != "4" && strDocStatus != "6")
				setDocID("");
		}
		else
			setDocID("");
	}
	else
		checkReportApproval();

	clearApproverSignInfo();
	checkAttachMethod();

	setFormDataToXml();
	checkRegistryType();

	setReceiverDisplayName();

	var nCaseNum = isAllTitleSpecified();
	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_INPUT_TITLE);
		else
			alert(nCaseNum + ALERT_NOT_INPUT_TITLE);

		onSetDocInfo();
		return;
	}

	var bPreviewer = false;
	if (getActiveApprovalLine() == null)
	{
		alert(ALERT_INPUT_APPROVE_LINE);
		onSetApproveLine();
		return;
	}
	else
	{
		var objApprovalLine = getActiveApprovalLine();
		var objApprover = getActiveApprover();
		if (getApproverUserID(objApprover) != g_strApproverID)
		{
			removeApprovalLine(objApprovalLine);
			alert(ALERT_RESET_APPROVE_LINE);
			onSetApproveLine();

			return;
		}

		if (getApproverRole(objApprover) == "20")
		{
			if (g_strOption148 == "1") //check archive
			{
				var bRet = checkArchiveInfo(2);
				if (bRet == false)
				{
					onEditArchiveInfo();
					return;
				}
			}
		}
		else if (getApproverRole(objApprover) == "21")
		{
			if (getApproverSerialOrder(objApprover) == "0")
				bPreviewer = true;
		}

		if (getApproverCount(objApprovalLine) == 1)
		{
			if (g_strOption90 == "1" && confirm(CONFIRM_APPROVE_SELF) == false)
			{
				onSetApproveLine();
				return;
			}
		}
	}

// 한양대 기안 체크 정보 시작
/*
	if (getLineName(getActiveApprovalLine()) == "0")
	{
// 기상청 기안 체크 정보 시작
		var nCaseNum = isAllSenderAsSpecified();
		if (nCaseNum != 0)
		{
			if (getBodyCount() == 1)
				alert("발신명의가 지정되지 않았습니다.");
			else
				alert(nCaseNum + " 안의 발신명의가 지정되지 않았습니다.");
			onSetDocInfo();
			return;
		}

		var nCaseNum = isAllRecipSpecified();
		if (nCaseNum != 0)
		{
			if (getBodyCount() == 1)
				alert(ALERT_NO_RECIPIENT);
			else
				alert(nCaseNum + ALERT_NO_RECIPIENT_IN_DOC);
			onSetRecipPart();
			return;
		}

		var bInspector = isInspectorSpecified();
		if (bInspector == false)
		{
			alert(ALERT_NO_INSPECTOR);
			g_bSendEnforceHold = false;
			onSetDealerInfo(1);
			return;
		}
	}
*/
// 한양대 기안 체크 정보 끝

	if (checkRelatedSystemInfo() == -1)
	{
		alert(ALERT_INPUT_DM_DETAILINFO);
		onSetDocInfo();
		return;
	}
	else if (checkRelatedSystemInfo() == 0)
	{
		alert(ALERT_INPUT_DM_INFO);
		onSelectRelatedSystem(0);
		return;
	}

	if (g_strOption148 == "1") //check archive, publiclevel
	{
		if (parseInt(getFlowStatus()) < 8)
		{
			var bRet = checkArchiveInfo(0);
			if (bRet == false)
			{
				onEditArchiveInfo();
				return;
			}
		}
		bRet = checkPublicLevel();
		if (bRet == false)
		{
			onEditPublicLevel();
			return;
		}
	}

	if (getCurrentLineType() == "5")
	{
		var strLineName = getCurrentLineName();
		var objInspBodyFile = findExtendAttachInfo("InspBody", strLineName, "0");
		if (objInspBodyFile == null)
			setBodyFileInfo();

		saveBodyFile();
		setModified(true);
	}

	g_nActionType = 1;

	if (g_nPrevActionType != 0 && g_strApprovalDocXml != "")
	{
		makeReturnSubmitData();
	}
	else
	{
		g_strActionUrl = "./approve/action/CN_ApproveProcess.jsp";
		g_strReturnFunction = "onSubmitCompleted";

		if (g_strOption88 == "1")
		{
			if (bPreviewer == false)
				g_arrMessage[0] = CONFIRM_APPROVE_SEND;
			else
				g_arrMessage[0] = CONFIRM_CIRCULAR_PRIOR;
		}
		else
			g_arrMessage[0] = "";
		g_arrMessage[1] = MESSAGE_APPROVE;
		g_arrMessage[2] = MESSAGE_APPROVE_SEND;
		if (bPreviewer == false)
			g_arrMessage[3] = ALERT_APPROVE_SEND;
		else
			g_arrMessage[3] = ALERT_APPROVE_AGREE;
		g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;
	}

	setSignInfo(true);
}

function onUpdateFile()
{
	g_nActionType = 0;

	// XML 파일 수정
	var objBodyFile = getBodyFileObj();
	if (objBodyFile != null)
		setAttachMethod(objBodyFile, "replace");

	// 서버로 본문 업로드
	var strFileName = getAttachFileName(objBodyFile);
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;
	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	g_strActionUrl = "./approve/action/CN_ApproveUpdateFile.jsp";
	g_strReturnFunction = "onUpdateFileCompleted";

	g_arrMessage[0] = CONFIRM_MODIFY_SEAL_AGREE;
	g_arrMessage[1] = MESSAGE_MODIFY_SEAL;
	g_arrMessage[2] = MESSAGE_MODIFY_SEAL_AGREE;
	g_arrMessage[3] = ALERT_MODIFY_SEAL_AGREE;
	g_arrMessage[4] = ALERT_MODIFY_SEAL_PROCESSING_ERROR;

	sendApproval(0);
}

function doAttachSubmitApprove(strFileName, strExt, strFormName, strFormUsage)
{
	if (strFileName == "")
		return;

	var strAttachBodyFile = getGUID() + "." + getFileExtension();
	var strTitle = getTitle(1);

	if (getIsDirect() == "N" && getIsConverted() == "Y" && parseInt(getFlowStatus()) < 7)
	{
		var strEnforceProposal = getEnforceProposal();
		saveEnforceDocumentAs(g_strDownloadPath + strAttachBodyFile, true);
		strTitle = getTitle(strEnforceProposal);

		var nBodyCount = getBodyCount();
		if (nBodyCount > 1)
		{
			var nIndex = nBodyCount;
			while (nIndex > 1)
			{
				removeDraftInfos(nIndex);

				if (nIndex != parseInt(strEnforceProposal))
					removeGeneralAttachesByCaseNumber(nIndex);

				nIndex --;
			}

			if (parseInt(strEnforceProposal) != 1)
			{
				var objGeneralAttach = getFirstGeneralAttach();
				while (objGeneralAttach != null)
				{
					setAttachCaseNumber(objGeneralAttach, "1");
					objGeneralAttach = getNextGeneralAttach(objGeneralAttach);
				}
			}
		}
	}
	else
		saveApproveDocumentAs(g_strDownloadPath + strAttachBodyFile, true);

	var nFileSize = getLocalFileSize(g_strDownloadPath + strAttachBodyFile);

	if (uploadFile(g_strDownloadPath + strAttachBodyFile, g_strUploadUrl + strAttachBodyFile) == true)
		addGeneralAttachInfo("1", strTitle, strAttachBodyFile, nFileSize, "");

	clearApprovalDocInfo();
	g_strEditType = "0";

	if (strFormUsage == "0")
	{
		setRegistryType("T");
		setIsDirect("N");
		g_strIsDirect = "N";
		setDocCategory("E", 1);
		setEnforceBound("I", 1);
	}
	else if (strFormUsage == "1" || strFormUsage == "2")
	{
		setRegistryType("T");
		setIsDirect("Y");
		g_strIsDirect = "Y";
		setDocCategory("E", 1);
		setEnforceBound("I", 1);
	}
	else
	{
		setRegistryType("Y");
		setIsDirect("T");
		g_strIsDirect = "T";
		setDocCategory("I", 1);
		setEnforceBound("N", 1);
	}

	setFormUsage(strFormUsage);
	g_strFormUsage = strFormUsage;
	setIsAttached("Y");

	g_strLineName = "0";
	g_strSerialOrder = "0";

	g_strEditType = "0";
	g_strDataUrl = "";
	g_strFormUrl = strFileName;
	loadDocument();

	changeMenu(1);
}

function doReturnSubmitApprove(strFileName, strExt, strFormName, strFormUsage)
{
	if (strFileName == "")
		return;

	sendBody();
	g_strApprovalDocXml = toXMLString(ApprovalDoc);

	var strAttachBodyFile = getGUID() + "." + getFileExtension();

	saveApproveDocumentAs(g_strDownloadPath + strAttachBodyFile, true);
	var nFileSize = getLocalFileSize(g_strDownloadPath + strAttachBodyFile);

	if (uploadFile(g_strDownloadPath + strAttachBodyFile, g_strUploadUrl + strAttachBodyFile) == true)
		addGeneralAttachInfo("1", "접수문서", strAttachBodyFile, nFileSize, "");

	var strDocCategory = getDocCategory(1);
	var strEnforceBound = getEnforceBound(1);

	clearApprovalDocInfo();
	setIsPubDoc("N");


	if (g_nPrevActionType == 22 || g_nPrevActionType == 24)
		setTitle("경유문서의 이송", 1);

//	setDocCategory(strDocCategory, 1);
//	setDocCategory(strEnforceBound, 1);
	setFormUsage(strFormUsage);
	setIsAttached("Y");

	if (strFormUsage == "0")
	{
		setRegistryType("T");
		setIsDirect("N");
		g_strIsDirect = "N";
		setDocCategory("E", 1);
		setEnforceBound("I", 1);
	}
	else if (strFormUsage == "1" || strFormUsage == "2")
	{
		setRegistryType("T");
		setIsDirect("Y");
		g_strIsDirect = "Y";
		setDocCategory("E", 1);
		setEnforceBound("I", 1);
	}
	else
	{
		setRegistryType("Y");
		setIsDirect("T");
		g_strIsDirect = "T";
		setDocCategory("I", 1);
		setEnforceBound("N", 1);
	}

	g_strLineName = "0";
	g_strSerialOrder = "0";

	g_strEditType = "0";
	g_strDataUrl = "";
	g_strFormUrl = strFileName;
	loadDocument();

	changeMenu(3);
}

function makeReturnSubmitData()
{
	dataForm.extendData.value = g_strApprovalDocXml;

	if (g_nPrevActionType == 7)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitRejectDistribute.jsp";
		g_strReturnFunction = "onRejectDistributeCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_REJECT;
		g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;
	}
	else if (g_nPrevActionType == 9)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitRejectReceive.jsp";
		g_strReturnFunction = "onRejectReceiveCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_REJECT;
		g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;
	}
	else if (g_nPrevActionType == 21)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitMoveDistribute.jsp";
		g_strReturnFunction = "onMoveDistributeCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_MOVE;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_MOVE;
		g_arrMessage[4] = ALERT_DOC_MOVE_ERROR;
	}
	else if (g_nPrevActionType == 22)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitPassDistribute.jsp";
		g_strReturnFunction = "onPassDistributeCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_PASS;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_PASS;
		g_arrMessage[4] = ALERT_DOC_PASS_ERROR;
	}
	else if (g_nPrevActionType == 23)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitMoveEnforce.jsp";
		g_strReturnFunction = "onMoveEnforceCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_MOVE;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_MOVE;
		g_arrMessage[4] = ALERT_DOC_MOVE_ERROR;
	}
	else if (g_nPrevActionType == 24)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitPassEnforce.jsp";
		g_strReturnFunction = "onPassEnforceCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_PASS;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_PASS;
		g_arrMessage[4] = ALERT_DOC_PASS_ERROR;
	}
	else if (g_nPrevActionType == 25)
	{
		g_strActionUrl = "./approve/action/CN_ApproveSubmitRejectAfterReceive.jsp";
		g_strReturnFunction = "onRejectAfterReceiveCompleted";

		g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_DOC_REJECT;
		g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;
	}
}

function onAgreeApprove()
{
	if (g_strOption148 == "1") //check publiclevel
	{
		var bRet = checkPublicLevel();
		if (bRet == false)
		{
			onEditPublicLevel();
			return;
		}
	}

	setReceiverDisplayName();

	g_nActionType = 2;

	g_strActionUrl = "./approve/action/CN_ApproveProcess.jsp";
	g_strReturnFunction = "onAgreeCompleted";

	if (g_strOption88 == "1")
		g_arrMessage[0] = CONFIRM_APPROVE_AGREE;
	else
		g_arrMessage[0] = "";
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_AGREE;
	g_arrMessage[3] = ALERT_APPROVE_AGREE;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(true);
}

function onOpposeApprove()
{
	var strOpinion = getOpinion();
	if (strOpinion == "")
	{
		alert(ALERT_INPUT_OPPOSE_OPINION);
		onSetCallOpinion("onOpposeApprove()");
//		onSetCallOpinion("");
		return;
	}

	g_nActionType = 13;

	g_strActionUrl = "./approve/action/CN_ApproveOppose.jsp";
	g_strReturnFunction = "onOpposeCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_OPPOSE;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_OPPOSE;
	g_arrMessage[3] = ALERT_APPROVE_AGREE;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(true);
}

function onSuspendApprove(nType)
{
	g_nActionType = 0;

	setFormDataToXml();
	checkRegistryType();

	var nCaseNum = isAllTitleSpecified();
	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_INPUT_TITLE);
		else
			alert(nCaseNum + ALERT_NOT_INPUT_TITLE);

		onSetDocInfo();
		return;
	}

	if (getActiveApprovalLine() == null)
	{
		alert(ALERT_INPUT_APPROVE_LINE);
		onSetApproveLine();
		return;
	}

	checkRelatedSystemInfo();

	if (nType == 0)
		g_nActionType = 14;	// g_nActionType = 1;

	g_strActionUrl = "./approve/action/CN_ApproveSuspend.jsp";
	g_strReturnFunction = "onSuspendCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_SUSPEND;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_SUSPEND;
	g_arrMessage[3] = ALERT_APPROVE_SUSPEND;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(false);
}

function onAutoSuspendApprove(nType)
{
	g_nActionType = 0;

	setFormDataToXml();
	checkRegistryType();

	var nCaseNum = isAllTitleSpecified();
	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			setTitle("제목없음", 1);
	}

	if (getActiveApprovalLine() == null)
		return false;

	checkRelatedSystemInfo();

	if (nType == 0)
		g_nActionType = 1;

	if (nType == 2)
		g_strActionUrl = "./approve/action/CN_ApproveSuspendEnforce.jsp";
	else
		g_strActionUrl = "./approve/action/CN_ApproveSuspend.jsp";

	g_strReturnFunction = "onAutoSuspendCompleted";

	g_arrMessage[0] = "";
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_SUSPEND;
	g_arrMessage[3] = ALERT_APPROVE_SUSPEND;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	if (nType == 2)
	{
		if (g_strIsDirect == "N")
			sendApproval(1);
		else
			sendApproval(0);
	}
	else
		setSignInfo(false);
}

function onReverseApprove()
{
	if (getCurrentLineName() == "1")
	{
		onCircularReverseApprove();
		return;
	}

	var strOpinion = getOpinion();
	if (strOpinion == "")
	{
		alert(ALERT_INPUT_REJECT_OPINION);
		onSetCallOpinion("onReverseApprove()");
//		onSetCallOpinion("");
		return;
	}

	g_nActionType = 3;

	g_strActionUrl = "./approve/action/CN_ApproveReject.jsp";
	g_strReturnFunction = "onReverseCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_REJECT;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_REJECT;
	g_arrMessage[3] = ALERT_APPROVE_REJECT;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(false);
}

function onDepartInspectReverseApprove()
{
	var strOpinion = getOpinion();
	if (strOpinion == "")
	{
		alert(ALERT_INPUT_REJECT_OPINION);
		onSetCallOpinion("onDepartInspectReverseApprove()");
//		onSetCallOpinion("");
		return;
	}

	g_nActionType = 3;

	g_strActionUrl = "./approve/action/CN_ApproveDepartInspectReject.jsp";
	g_strReturnFunction = "onDepartInspectReverseCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_REJECT;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_REJECT;
	g_arrMessage[3] = ALERT_APPROVE_REJECT;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(false);
}

function onCircularReverseApprove()
{
	var strOpinion = getOpinion();
	if (strOpinion == "")
	{
		alert(ALERT_INPUT_REJECT_OPINION);
		onSetCallOpinion("onCircularReverseApprove()");
//		onSetCallOpinion("");
		return;
	}

	g_nActionType = 3;

	g_strActionUrl = "./approve/action/CN_ApproveCircularReject.jsp";
	g_strReturnFunction = "onCircularReverseCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_REJECT;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_REJECT;
	g_arrMessage[3] = ALERT_APPROVE_REJECT;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	setSignInfo(false);
}

function onApproveCancel(nType)
{
	if (nType == 0)
		g_nActionType = 0;
	else
		g_nActionType = 10;

	g_strActionUrl = "./approve/action/CN_ApproveCancel.jsp";
	g_strReturnFunction = "onCancelCompleted";

	if (nType == 0)
	{
		g_arrMessage[0] = CONFIRM_SUBMIT_CANCEL;
		g_arrMessage[1] = MESSAGE_APPROVE;
		g_arrMessage[2] = MESSAGE_SUBMIT_CANCEL;
		g_arrMessage[3] = ALERT_SUBMIT_CANCEL;
		g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;
	}
	else if (nType == 1)
	{
		g_arrMessage[0] = CONFIRM_AGREE_CANCEL;
		g_arrMessage[1] = MESSAGE_APPROVE;
		g_arrMessage[2] = MESSAGE_AGREE_CANCEL;
		g_arrMessage[3] = ALERT_AGREE_CANCEL;
		g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;
	}

	setSignInfo(false);
}

function onRegistRejectApproval()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveRegist.jsp";
	g_strReturnFunction = "onRegistRejectApprovalCompleted";

	g_arrMessage[0] = "반려문서를 등록하시겠습니까?";
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = "결재문서를 등록 중입니다.";
	g_arrMessage[3] = "반려문서를 등록하였습니다.";
	g_arrMessage[4] = "반려문서 등록에 실패하였습니다.";

	sendApproval(0);
}

function onEnforceApprove()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveEnforce.jsp";
	g_strReturnFunction = "onEnforceCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_ENFORCE;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_ENFORCE;
	g_arrMessage[3] = ALERT_APPROVE_ENFORCE;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	if (g_strIsDirect == "N")
		sendApproval(1);
	else
		sendApproval(0);
}

function onDirectEnforceApprove(bNewDocID)
{
	if (bNewDocID)
		setDocID("");

	setFormDataToXml();
	checkRegistryType();

	var nCaseNum = isAllTitleSpecified();
	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_INPUT_TITLE);
		else
			alert(nCaseNum + ALERT_NOT_INPUT_TITLE);

		onSetDocInfo();
		return;
	}

	if (getActiveApprovalLine() == null)
	{
		alert(ALERT_INPUT_APPROVE_LINE);
		onSetApproveInfo();
		return;
	}

	if (checkRelatedSystemInfo() == -1)
	{
		alert(ALERT_INPUT_DM_DETAILINFO);
		onSetDocInfo();
		return;
	}
	else if (checkRelatedSystemInfo() == 0)
	{
		alert(ALERT_INPUT_DM_INFO);
		onSelectRelatedSystem(0);
		return;
	}

	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveDirectEnforce.jsp";
	g_strReturnFunction = "onEnforceCompleted";

	g_arrMessage[0] = CONFIRM_APPROVE_ENFORCE;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_APPROVE_ENFORCE;
	g_arrMessage[3] = ALERT_APPROVE_ENFORCE;
	g_arrMessage[4] = ALERT_APPROVE_PROCESSING_ERROR;

	sendApproval(0);
}

function onAgreeExamination()
{
// 공공버전에서는 심사에서 관인을 찍지 않음
/*
	var nCaseNum = isCompanyStampInExamination();
	if (nCaseNum != 0)
	{
		if (nCaseNum == 1)
			alert(ALERT_NO_COMPANY_STAMP);
		else
			alert(nCaseNum + ALERT_NO_COMPANY_STAMP_IN_DOC);
		return;
	}
*/
	g_nActionType = 4;

	g_strActionUrl = "./approve/action/CN_ApproveAgreeExam.jsp";
	g_strReturnFunction = "onAgreeExaminationCompleted";

	g_arrMessage[0] = CONFIRM_AGREE_EXAMINATION;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_INSPECTOR_SIGNED;
	g_arrMessage[4] = ALERT_INSPECTOR_SIGNED_ERROR;

	setDelivererSignInfo(true, "0");
}

function onRejectExamination()
{
	var objDeliverer = getDelivererByDelivererType("0");
	if (objDeliverer == null)
		return;

	var strOpinion = getDelivererOpinion(objDeliverer);
	if (strOpinion == "")
	{
		alert(ALERT_INPUT_RETURN_OPINION);
		onSetCallOpinion("onRejectExamination()");
//		onSetCallOpinion("");
		return;
	}

	g_nActionType = 12;

	g_strActionUrl = "./approve/action/CN_ApproveRejectExam.jsp";
	g_strReturnFunction = "onRejectExaminationCompleted";

	g_arrMessage[0] = CONFIRM_REJECT_EXAMINATION;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_INSPECTOR_REJECT;
	g_arrMessage[4] = ALERT_INSPECTOR_REJECT_ERROR;

	if (g_strIsDirect == "N")
		sendApproval(1);
	else
		sendApproval(0);
}

function onSendToApprover(nType)
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return;

	if (nType == 0)
	{
		g_nActionType = 0;

		checkReportApproval();

		g_strActionUrl = "./approve/action/CN_ApproveSendToSubmiter.jsp";
		g_strReturnFunction = "onSendToApproverCompleted";

		g_arrMessage[0] = CONFIRM_SEND_TO_SUBMITER;
		g_arrMessage[1] = MESSAGE_APPROVE;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_SEND_TO_SUBMITER;
		g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

		sendApproval(0);
	}
	else if (nType == 2)
	{
		g_nActionType = 0;

		g_strActionUrl = "./approve/action/CN_ApproveSendToPrior.jsp";
		g_strReturnFunction = "onSendToApproverCompleted";

		g_arrMessage[0] = CONFIRM_SEND_TO_PRIOR;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_SEND_TO_PRIOR;
		g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

		sendApproval(0);
	}
	else if (nType == 3)
	{
		g_nActionType = 0;

		g_strActionUrl = "./approve/action/CN_ApproveSendToCharger.jsp";
		g_strReturnFunction = "onSendToApproverCompleted";

		g_arrMessage[0] = CONFIRM_SEND_TO_CHARGER;
		g_arrMessage[1] = MESSAGE_DOCFLOW;
		g_arrMessage[2] = MESSAGE_DOC_SENDING;
		g_arrMessage[3] = ALERT_SEND_TO_CHARGER;
		g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

		var objApprover = getApproverBySerialOrder(objApprovalLine, "0");
		if (getApproverRole(objApprover) == "21")
		{
			g_nActionType = 1;
			g_strActionUrl = "./approve/action/CN_ApproveProcess.jsp";

			setSignInfo(true);
		}
		else
			sendApproval(0);
	}
}

function onCallBackDocument()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveCallBackDocument.jsp";
	g_strReturnFunction = "onCallBackDocumentCompleted";

	g_arrMessage[0] = CONFIRM_CALLBACK;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_CALLED_BACK;
	g_arrMessage[4] = ALERT_CALLBACK_ERROR;

	if (g_strOpenLocale == "DOCUMENT")
		var strApprovalDoc = toXMLString(opener.ApprovalDoc);
	else
		var strApprovalDoc = toXMLString(ApprovalDoc);

	dataForm.xmlData.value = strApprovalDoc;
	dataForm.returnFunction.value = g_strReturnFunction;
	dataForm.action = g_strActionUrl;
	dataForm.target = "dataTransform";
	dataForm.submit();
}

///////////////////////////////////////////////////////////////////////////////////////////////

function onSubmitCompleted(nType)
{
	g_bDoSendApproval = false;
	var g_strOldEditType = g_strEditType;
	g_strEditType = "2";

// 2003.11.28
//	editDocument(false);

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsAdminMis() == "Y")			// 상신은 IS_ADMIN_MIS flag에 상관없이 MIS시스템이면 submit notification
		{
			if (!onSendDocToAdminSys("SUBMIT"))	// 상신 Notification to MIS
				alert("통보 파일 전송에 실패하였습니다.");
		}

		setInfoChange(false);

		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		if (g_strDataUrl == "")		// 새 문서 상신
		{
	//		window.close();
		}
		else if (g_strOldEditType == "6" || g_strOldEditType == "46")		// 부서대기함 문서 상신
		{
			opener.invalidateCurDoc();
			if (g_strOption87 == "1")
			{
				onNextApprove();
				return;
			}
			else
			{
			}
		}
		else		// 반려함, 개인함, 지정된 기안자, 담당자, 선람자의 기안함 문서 상신
		{
		}
	}

	onClose();
}

function onUpdateFileCompleted(nType)
{
	g_bDoSendApproval = false;

//	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
		alert(g_arrMessage[3]);
	else
		alert(g_arrMessage[4]);
}

function onAgreeCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsAdminMis() == "Y")
		{
			if (needNotiToMisEveryStep() || needNotiToMisLastStep())
			{
				if (!onSendDocToAdminSys(""))	// 승인 Notification to MIS
					alert("통보 파일 전송에 실패하였습니다.");
			}
		}

		setInfoChange(false);

		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
//		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
/*
			// 마지막 결재문서 결재시 자동 Close
			if (opener.getNextElement() == null)
				window.close();
			else
*/
				onNextApprove();

			return;
		}
		else
		{
		}
	}

	onClose();
}

function onOpposeCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onSuspendCompleted(nType)
{
	g_bDoSendApproval = false;

//	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		var strXML = dataTransform.ApprovalDoc.documentElement.xml;
		ApprovalDoc.loadXML(strXML);

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		if (g_strDataUrl == "")			// 새 문서 보류
		{
//			window.close();
		}
		else if (g_nActionType == 14)	// else if (g_nActionType == 0)	// 개인함 문서 보류
		{
		}
	}

	onClose();
}

function onAutoSuspendCompleted(nType)
{
	g_bDoSendApproval = false;

//	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		var strXML = dataTransform.ApprovalDoc.documentElement.xml;
		ApprovalDoc.loadXML(strXML);
	}
	else
		alert(g_arrMessage[4]);

	window.close();
}

function onReverseCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsAdminMis() == "Y")
		{
			if (needNotiToMisEveryStep())
			{
				if (!onSendDocToAdminSys(""))	// 반려 Notification to MIS
					alert("통보 파일 전송에 실패하였습니다.");
			}
		}

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onDepartInspectReverseCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onCircularReverseCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onCancelCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsAdminMis() == "Y")			// 기안회수는 IS_ADMIN_MIS flag에 상관없이 MIS시스템이면 반송
		{
			if (!onSendDocToAdminSys(""))
				alert("통보 파일 전송에 실패하였습니다.");
		}

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onRegistRejectApprovalCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onSendEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	updatePostSendField();
	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (isPubdocRecipExist() == true)
			uploadPackFiles();

		var strXML = dataTransform.ApprovalDoc.documentElement.xml;
		ApprovalDoc.loadXML(strXML);
		setXmlDataToForm();

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
	g_bSendEnforceHold = false;

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		if (g_strCabinet == CABINET_SUBMIT_REAL || g_strCabinet == CABINET_SUBMITEDAPPROVAL_REAL || g_strCabinet == CABINET_SUBMITEDDOCFLOW_REAL)
		{
		}
		else if (g_strCabinet == CABINET_SEND_REAL || g_strCabinet == CABINET_INVESTIGATION_REAL)
		{
			opener.invalidateCurDoc();
			if (g_strOption87 == "1")
			{
				onNextApprove();
				return;
			}
			else
			{
			}
		}
	}

	onClose();
}

function onResendEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	updatePostSendField();
	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (isPubdocRecipExist() == true)
			uploadPackFiles();

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
	{
		setInfoChange(false);
		alert(g_arrMessage[4]);
	}
	g_bSendEnforceHold = false;
}

function onSendExaminationCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		if (g_strCabinet == CABINET_SUBMIT_REAL || g_strCabinet == CABINET_SUBMITEDAPPROVAL_REAL || g_strCabinet == CABINET_SUBMITEDDOCFLOW_REAL)
		{
		}
		else if (g_strCabinet == CABINET_SEND_REAL)
		{
			opener.invalidateCurDoc();
			if (g_strOption87 == "1")
			{
				onNextApprove();
				return;
			}
			else
			{
			}
		}
	}

	onClose();
}

function onAgreeExaminationCompleted(nType)
{
	g_bDoSendApproval = false;

	if ((parseInt(g_strOption46) & 4) == 4)
		changeMenu(1);
	else
		changeMenu(2);

	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1" && (parseInt(g_strOption46) & 4) != 4)
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onRejectExaminationCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onDistributeEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(1);

		var strXML = dataTransform.ApprovalDoc.documentElement.xml;
		ApprovalDoc.loadXML(strXML);
		setXmlDataToForm();

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onRejectDistributeCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(2);

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onMoveDistributeCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onPassDistributeCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onReceiveEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

//	changeMenu(2);

	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(1);

		var strXML = dataTransform.ApprovalDoc.documentElement.xml;
		ApprovalDoc.loadXML(strXML);
		setXmlDataToForm();
		clearAttachMethod();

		g_strLineName = "1";	// 접수후 결재선은 LineName 1
/*
// 해경
		if (isMultiServer() == true)
			changeMenu(2);		// 멀티서버 문서는 기본 메뉴만 보여줌.
		else
			changeMenu(1);		// 접수후 자동으로 접수대장의 메뉴를 보여줌.
*/
		changeMenu(1);		// 접수후 자동으로 접수대장의 메뉴를 보여줌.

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
	{
		changeMenu(2);
		alert(g_arrMessage[4]);
	}

// 20030625 icaha document switching
// 접수시에서는 다음문서로 이동하지 않음
/*
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}
*/

	onClose();
}

function onRejectReceiveCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(2);

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onRejectAfterReceiveCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onMoveEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(1);

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onPassEnforceCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		if (getIsPubDoc() == "Y")
			setAck(1);

		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onPostPublicViewCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);
}

function onModifyPublicViewCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

	// unknown
	if (g_strCaller == "")
	{
	}

	onClose();
}

function onDeletePublicViewCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

	// unknown
	if (g_strCaller == "")
	{
	}

	onClose();
}

function onRejectChargerCompleted()
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onSendToApproverCompleted(nType)
{
	g_bDoSendApproval = false;

	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
	{
		setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching
	if (g_strCaller == "")
	{
		opener.invalidateCurDoc();
		if (g_strOption87 == "1")
		{
			onNextApprove();
			return;
		}
		else
		{
		}
	}

	onClose();
}

function onCallBackDocumentCompleted(nType)
{
	g_bDoSendApproval = false;

	if (g_strOpenLocale == "DOCUMENT")
		opener.changeMenu(2);

	if (nType == true)
	{
		if (g_strOpenLocale == "DOCUMENT")
			opener.setInfoChange(false);
		alert(g_arrMessage[3]);
	}
	else
		alert(g_arrMessage[4]);

// 20030625 icaha document switching ??
	window.close();
}

///////////////////////////////////////////////////////////////////////////////////////////////

function setSignInfo(bSign)
{
	var objApprover = getActiveApprover();
	setApproverIsActive(objApprover, "Y");

	var strOpinion = getOpinion();
	setApproverOpinion(objApprover, strOpinion);

	if (bSign == true)
	{
		setApproverIsSigned(objApprover, "Y");
		if (g_strSignType != "2")
			setApproverSignFileName(objApprover, g_strSignUrl);

		setSignFileInfo();
	}
	else
	{
		setApproverIsSigned(objApprover, "N");
		setApproverSignFileName(objApprover, "");

		if (g_strSignUrl != "")
			removeRelatedAttachInfo(g_strSignUrl);
	}

	dataForm.xmlData.value = "";
	dataForm.returnFunction.value = "onSetSignDate";

	dataForm.action = "./common/jsp/CN_GetServerTime.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onSetSignDate(strSignDate)
{
//	var objApprovalLine = getActiveApprovalLine();
//	var objApprover = getApproverByUserID(objApprovalLine, g_strApproverID);
//	var objApprover = getApproverBySerialOrder(objApprovalLine, g_strSerialOrder);
	var objApprover = getActiveApprover();

	setApproverSignDate(objApprover, strSignDate);

	if (g_strApproverType == "4")
	{
		var arrRepUserInfo = g_strRepUserInfo.split("");
		setApproverRepID(objApprover, arrRepUserInfo[0]);
		setApproverRepName(objApprover, arrRepUserInfo[1]);
		setApproverRepPosition(objApprover, arrRepUserInfo[2]);
		setApproverRepPositionAbbr(objApprover, arrRepUserInfo[3]);
		setApproverRepLevel(objApprover, arrRepUserInfo[4]);
		setApproverRepLevelAbbr(objApprover, arrRepUserInfo[5]);
		setApproverRepDuty(objApprover, arrRepUserInfo[6]);
		setApproverRepTitle(objApprover, arrRepUserInfo[7]);
		setApproverRepCompany(objApprover, arrRepUserInfo[8]);
		setApproverRepDeptName(objApprover, arrRepUserInfo[9]);
		setApproverRepDeptCode(objApprover, arrRepUserInfo[10]);
	}

	if (sendApproval(0) == false)
	{
		;	// Error 처리
	}
}

function setDelivererSignInfo(bSign, strType)
{
	var objDeliverer = getDelivererByDelivererType(strType);

	if (objDeliverer == null)
		return;

	if (bSign == true)
	{
		setDelivererSignFilename(objDeliverer, g_strSignUrl);
		setExaminationSignFileInfo(g_strSignUrl);
	}
	else
	{
		setDelivererSignFilename(objDeliverer, "");

		if (g_strSignUrl != "")
			removeRelatedAttachInfo(g_strSignUrl);
	}

	dataForm.xmlData.value = strType;
	dataForm.returnFunction.value = "onSetDelivererSignDate";

	dataForm.action = "./common/jsp/CN_GetServerTime.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onSetDelivererSignDate(strSignDate, strType)
{
	var objDeliverer = getDelivererByDelivererType(strType);
	setDelivererSignDate(objDeliverer, strSignDate);

	if (strType == "0")
	{
		if (g_strOption46 == "4")
		{
			setExaminationSign();
			onAgreeExaminationCompleted(true);
			return;
		}
	}

	if (g_strIsDirect == "N")
	{
		if (sendApproval(1) == false)
		{
			;	// Error 처리
		}
	}
	else
	{
		if (sendApproval(0) == false)
		{
			;	// Error 처리
		}
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////

function onSendExamination()
{
	if (g_strEditType == "0" || g_strEditType == "5")
		return;

	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveSendExam.jsp";
	g_strReturnFunction = "onSendExaminationCompleted";

	g_arrMessage[0] = CONFIRM_SEND_TO_INSPECTOR;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_SEND_TO_INSPECTOR;
	g_arrMessage[4] = ALERT_SEND_TO_INSPECTOR_ERROR;

	if (g_strIsDirect == "N")
	{
		if (sendApproval(1) == false)
		{
			;	// Error 처리
		}
	}
	else
	{
		if (sendApproval(0) == false)
		{
			;	// Error 처리
		}
	}
}

function onSendEnforce()
{
	if (checkAutoEnforce() == true)
	{
		getLinkedEnforceForm();
		return;
	}

	var bInspector = isInspectorSpecified()

	if (bInspector == false)
	{
		alert(ALERT_NO_INSPECTOR);
		g_bSendEnforceHold = false;
		onSetDealerInfo(1);
		return;
	}

	var nCaseNum = isAllRecipSpecified();

	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_NO_RECIPIENT);
		else
			alert(nCaseNum + ALERT_NO_RECIPIENT_IN_DOC);
		g_bSendEnforceHold = false;
		onSetRecipPart();
		return;
	}

	if (checkAutoEnforceStamp() == true)
	{
		g_nPrevActionType = 5;
		getAutoEnforceStamp();
		return;
	}

	nCaseNum = isAllEnforceStamped();

	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_NO_ENFORCE_STAMP);
		else
			alert(nCaseNum + ALERT_NO_ENFORCE_STAMP_IN_DOC);
		g_bSendEnforceHold = false;
		return;
	}

	g_nActionType = 5;

	g_strActionUrl = "./approve/action/CN_ApproveSendEnforce.jsp";
	g_strReturnFunction = "onSendEnforceCompleted";

	g_arrMessage[0] = CONFIRM_ENFORCE_WILL_SEND;
	g_arrMessage[1] = MESSAGE_SEND;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_ENFORCE_SEND;
	g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetSenderInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onResendEnforce()
{
/*
	var nBodyCount = getBodyCount();

	if (nBodyCount > 1)
	{
		var nHeight = 180 + (nBodyCount - 3) * 24;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectProposal.jsp?start=1&end=" + nBodyCount + "&returnFunction=doResendEnforceProposal&type=select&multiselect=true";
		window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=300,height="+nHeight);
	}
	else
		doResendEnforce();
*/
	doResendEnforce();
//	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveResendRecipient.jsp?returnFunction=doResendEnforce";
//	window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=500,height=400");
}

function doResendEnforceProposal(strProposal)
{
	strProposal = "^" + strProposal + "^";

	var nBodyCount = getBodyCount();
	for (var i = 1 ; i <= nBodyCount ; i++)
	{
		var strFind = "^" + i + "^";
		if (strProposal.indexOf(strFind) == -1)
			removeRecipGroup("" + i);
	}

	doResendEnforce();
}

function doResendEnforce()
{
// 수신처 적합성 검사(접수한 부서에는 보낼수 없음)
	var bResend = false;

	var objRecipGroup = getFirstRecipGroup();
	while (objRecipGroup != null)
	{
		var objRecipient = getFirstRecipient(objRecipGroup);
		while (objRecipient != null)
		{
//			if (getRecipientReceiptStatus(objRecipient) != "3")
			if (getRecipientMethod(objRecipient) == "keep")
			{
				bResend = true;
				break;
			}

			objRecipient = getNextRecipient(objRecipient);
		}

		if (bResend == true)
			break;

		objRecipGroup = getNextRecipGroup(objRecipGroup);
	}

	if (bResend == false)
	{
		alert(ALERT_NO_RESEND_RECIPIENT);
		onSetRecipPart();
		return;
	}

	g_nActionType = 11;

	g_strActionUrl = "./approve/action/CN_ApproveResendEnforce.jsp";
	g_strReturnFunction = "onResendEnforceCompleted";

	g_arrMessage[0] = CONFIRM_ENFORCE_WILL_SEND;
	g_arrMessage[1] = MESSAGE_SEND;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_ENFORCE_SEND;
	g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

	if (isPubdocRecipExist() == true)
	{
		makePubDocXML();
	}
	else
	{
		if (g_strIsDirect == "N")
		{
			if (sendApproval(1) == false)
			{
				;	// Error 처리
			}
		}
		else
		{
			if (sendApproval(0) == false)
			{
				;	// Error 처리
			}
		}
	}
}

function onAutoSendEnforce()
{
	var bInspector = isInspectorSpecified()

	if (bInspector == false)
	{
		alert(ALERT_NO_INSPECTOR);
		g_bSendEnforceHold = false;
		onSetDealerInfo(1);
		return;
	}

	var nCaseNum = isAllRecipSpecified();

	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_NO_RECIPIENT);
		else
			alert(nCaseNum + ALERT_NO_RECIPIENT_IN_DOC);
		g_bSendEnforceHold = false;
		onSetRecipPart();
		return;
	}

	nCaseNum = isAllEnforceStamped();

	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_NO_ENFORCE_STAMP);
		else
			alert(nCaseNum + ALERT_NO_ENFORCE_STAMP_IN_DOC);
		g_bSendEnforceHold = false;
		return;
	}

	g_nActionType = 5;

	if (getFlowStatus() == "0")
		g_strActionUrl = "./approve/action/CN_ApproveProcess.jsp";
	else
		g_strActionUrl = "./approve/action/CN_ApproveSendEnforce.jsp";

	g_strReturnFunction = "onSendEnforceCompleted";

	g_arrMessage[0] = CONFIRM_ENFORCE_WILL_SEND;
	g_arrMessage[1] = MESSAGE_SEND;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_ENFORCE_SEND;
	g_arrMessage[4] = ALERT_DOC_SEND_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetSenderInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function isAllTitleSpecified()
{
	var nBodyCount = getBodyCount();
	var nCaseNum = 1;

	for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
	{
		var strTitle = getTitle(nCaseNum);
		if (strTitle == "")
			return nCaseNum;
	}

	return 0;
}

function isAllRecipSpecified()
{
	var nBodyCount = getBodyCount();
	var nCaseNum = 1;

	for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
	{
		var strDocCategory = getDocCategory(nCaseNum);
		if (strDocCategory != "I")
		{
			var objRecipGroup = getRecipGroup(nCaseNum);
			if (objRecipGroup == null)
				return nCaseNum;
			else
			{
				if (getRecipientCount(objRecipGroup) == 0)
					return nCaseNum;
			}
		}
	}

	return 0;
}

function isAllSenderAsSpecified()
{
	var nBodyCount = getBodyCount();
	var nCaseNum = 1;

	for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
	{
		var strDocCategory = getDocCategory(nCaseNum);
		if (strDocCategory != "I")
		{
			var strSenderAs = getSenderAs(nCaseNum);
			if (strSenderAs == "")
				return nCaseNum;
		}
	}

	return 0;
}

function isAllEnforceStamped()
{
	if (g_strOption83 == "0")
		return 0;

	if (g_strIsDirect == "Y")
	{
		var nCaseNum = 1;
		var objStamp = getRelatedAttachByClassify("DocStamp");
		if (objStamp == null)
		{
			return nCaseNum;
		}
	}
	else
	{
		var nBodyCount = getBodyCount();
		for (var nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
		{
			var strDocCategory = getDocCategory(nCaseNum);
			if (strDocCategory != "I")
			{
				var strDocCategory = getDocCategory(nCaseNum);
				if (strDocCategory == "E")
				{
					var strEnforceBound = getEnforceBound(nCaseNum);
					if (strEnforceBound == "I" || strEnforceBound == "O" || strEnforceBound == "C")
					{
						var objStamp = getExtendAttachByClassify(nCaseNum, "EnforceRelated");
						if (objStamp == null)
						{
							return nCaseNum;
						}
					}
				}
			}
		}
	}

	return 0;
}

function isCompanyStampInExamination()
{
	var nBodyCount = getBodyCount();
	var nCaseNum = 1;
	var objStamp = null;

	if (g_strIsDirect == "Y")
	{
		objStamp = getRelatedAttachBySubDiv("CompanyStamp");

		if (objStamp == null)
			objStamp = getRelatedAttachBySubDiv("CompanyOmitStamp");

		if (objStamp == null)
			return 1;
	}
	else
	{
		for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
		{
			if (getDocCategory(nCaseNum) != "I" && getEnforceBound(nCaseNum) != "I")
			{
				objStamp = getExtendAttachBySubDiv(nCaseNum, "CompanyStamp");
				if (objStamp == null)
				{
					if (objStamp == null)
						objStamp = getExtendAttachBySubDiv(nCaseNum, "CompanyOmitStamp");

					if (objStamp == null)
					{
						return nCaseNum;
					}
				}
			}
		}
	}

	return 0;
}

function isInspectorSpecified()
{
	if (g_strOption42 == "0" || g_strOption148 == "1")
		return true;

	var nBodyCount = getBodyCount();
	var nCaseNum = 1;
	var bInspectorCheck = false;

	for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
	{
		var strDocCategory = getDocCategory(nCaseNum);
		if (strDocCategory == "E")
		{
			var strEnforceBound = getEnforceBound(nCaseNum);
			if (strEnforceBound == "O" || strEnforceBound == "C")
			{
				bInspectorCheck = true;
				break;
			}

// 한양대는 무조건 심사자 지정
//			bInspectorCheck = true;
//			break;
		}
	}

	if (bInspectorCheck)								//심사자를 지정해야 하는 조건이면 심사자 찾음.
	{
		var objDeliverer = getDelivererByDelivererType(0);
		if (objDeliverer == null)
			return false;
		else
			return true;
	}
	else
	{
		var objDeliverer = getDelivererByDelivererType(0);
		if (objDeliverer != null)
			removeDeliverer(objDeliverer)
	}

	return true;
}

function onDistributeEnforce()
{
	g_nActionType = 6;
	g_nPrevActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveDistributeEnforce.jsp";
	g_strReturnFunction = "onDistributeEnforceCompleted";

	g_arrMessage[0] = CONFIRM_DELIVERY_ENFORCE;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DELIVERED_ENFORCE;
	g_arrMessage[4] = ALERT_DELIVERED_ENFORCE_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetDistributerInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onRejectDistribute()
{
	if (g_strOption148 == "0" || getIsPubDoc() == "Y")
	{
		var strOpinion = getOpinion();
		if (strOpinion == "")
		{
			alert(ALERT_INPUT_RETURN_OPINION);
			onSetCallOpinion("onRejectDistribute()");
//			onSetCallOpinion("");
			return;
		}

		g_nPrevActionType = 0;
		doRejectDistribute();
	}
	else
	{
		if (confirm(CONFIRM_DOC_WILL_REJECT) == false)
			return;

		g_nPrevActionType = 7;
		doRejectDistribute();
	}
}

function doRejectDistribute()
{
	g_nActionType = 7;

	g_strActionUrl = "./approve/action/CN_ApproveRejectDistribute.jsp";
	g_strReturnFunction = "onRejectDistributeCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_REJECT;
	g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetDistributerInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onMoveDistribute()
{
	if (confirm(CONFIRM_DOC_WILL_MOVE) == false)
		return;

	g_nPrevActionType = 21;
	g_nActionType = 21;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetDistributerInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onPassDistribute()
{
	if (confirm(CONFIRM_DOC_WILL_PASS) == false)
		return;

	g_nPrevActionType = 22;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetDistributerInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onReceiveEnforce()
{
	if (g_strOption148 == "1") //check archive
	{
		var bRet = checkArchiveInfo(1);
		if (bRet == false)
		{
			onEditArchiveInfo();
			return;
		}
	}

	g_nActionType = 8;
	g_nPrevActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveReceiveEnforce.jsp";
	g_strReturnFunction = "onReceiveEnforceCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_RECEIVE;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_RECEIVE;
	g_arrMessage[4] = ALERT_DOC_RECEIVE_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetReceiverInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onRejectReceive()
{
	if (g_strOption148 == "0" || getDelivererByDelivererType("2") != null || getIsPubDoc() == "Y")
	{
		var strOpinion = getOpinion();
		if (strOpinion == "")
		{
			alert(ALERT_INPUT_RETURN_OPINION);
			onSetCallOpinion("onRejectReceive()");
//			onSetCallOpinion("");
			return;
		}

		g_nPrevActionType = 0;
		doRejectReceive();
	}
	else
	{
		if (confirm(CONFIRM_DOC_WILL_REJECT) == false)
			return;

		var bRet = checkArchiveInfo(1);
		if (bRet == false)
		{
			onEditArchiveInfo();
			return;
		}

		g_nPrevActionType = 9;
		doRejectReceive();
	}
}

function doRejectReceive()
{
	g_nActionType = 9;

	g_strActionUrl = "./approve/action/CN_ApproveRejectReceive.jsp";
	g_strReturnFunction = "onRejectReceiveCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_REJECT;
	g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetReceiverInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onRejectAfterReceive()
{
	if (g_strOption148 == "0" || getDelivererByDelivererType("2") != null || getIsPubDoc() == "Y")
	{
		var strOpinion = getOpinion();
		if (strOpinion == "")
		{
			alert(ALERT_INPUT_RETURN_OPINION);
			onSetCallOpinion("onRejectAfterReceive()");
//			onSetCallOpinion("");
			return;
		}

		g_nPrevActionType = 0;
		doRejectAfterReceive();
	}
	else
	{
		if (confirm(CONFIRM_DOC_WILL_REJECT) == false)
			return;

		var bRet = checkArchiveInfo(1);
		if (bRet == false)
		{
			onEditArchiveInfo();
			return;
		}

		g_nPrevActionType = 25;
		doRejectAfterReceive();
	}
}

function doRejectAfterReceive()
{
	g_nActionType = 25;

	g_strActionUrl = "./approve/action/CN_ApproveRejectAfterReceive.jsp";
	g_strReturnFunction = "onRejectAfterReceiveCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_REJECT;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_REJECT;
	g_arrMessage[4] = ALERT_DOC_REJECT_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetReceiverInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onMoveEnforce()
{
	if (g_strOption148 == "0")
	{
		var strOpinion = getOpinion();
		if (strOpinion == "")
		{
			alert(ALERT_INPUT_MOVE_OPINION);
			onSetCallOpinion("onMoveEnforce()");
//			onSetCallOpinion("");
			return;
		}

		g_nPrevActionType = 0;
		doMoveEnforce();
	}
	else
	{
		if (confirm(CONFIRM_DOC_WILL_MOVE) == false)
			return;

		if (getFlowStatus() == "8")
		{
//check archive
			var bRet = checkArchiveInfo(1);
			if (bRet == false)
			{
				onEditArchiveInfo();
				return;
			}

			g_nPrevActionType = 23;
			doMoveEnforce();
		}
		else
		{
			g_nPrevActionType = 23;
			g_nActionType = 23;

			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
			window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
		}
	}
}

function doMoveEnforce()
{
	g_nActionType = 23;

	g_strActionUrl = "./approve/action/CN_ApproveMoveEnforce.jsp";
	g_strReturnFunction = "onMoveEnforceCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_MOVE;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_MOVE;
	g_arrMessage[4] = ALERT_DOC_MOVE_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetReceiverInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onPassEnforce()
{
	if (g_strOption148 == "0")
	{
		var strOpinion = getOpinion();
		if (strOpinion == "")
		{
			alert(ALERT_INPUT_PASS_OPINION);
			onSetCallOpinion("onPassEnforce()");
//			onSetCallOpinion("");
			return;
		}

		g_nPrevActionType = 0;
		g_nActionType = 24;
		doMoveEnforce();
	}
	else
	{
		if (confirm(CONFIRM_DOC_WILL_PASS) == false)
			return;

		if (getFlowStatus() == "8")
		{
//check archive
			var bRet = checkArchiveInfo(1);
			if (bRet == false)
			{
				onEditArchiveInfo();
				return;
			}

			g_nPrevActionType = 24;
			g_nActionType = 24;

			dataForm.xmlData.value = g_strApproverID;
			dataForm.returnFunction.value = "onSetReceiverInfo";

			dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
			dataForm.target = "dataTransform";
			dataForm.submit();
		}
		else
		{
			g_nPrevActionType = 24;
			g_nActionType = 24;

			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
			window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
		}
	}
}

function onPostPublicView()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApprovePostPublicView.jsp";
	g_strReturnFunction = "onPostPublicViewCompleted";

	g_arrMessage[0] = CONFIRM_DOC_WILL_POST;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_POSTED;
	g_arrMessage[4] = ALERT_DOC_POST_ERROR;

	dataForm.xmlData.value = g_strApproverID;
	dataForm.returnFunction.value = "onSetPublicViewerInfo";

	dataForm.action = "./common/jsp/CN_GetDelivererInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onModifyPublicView()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveModifyPublicView.jsp";
	g_strReturnFunction = "onModifyPublicViewCompleted";

	g_arrMessage[0] = CONFIRM_DOC_MODIFYPOST;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_MODIFYPOST;
	g_arrMessage[4] = ALERT_DOC_MODIFYPOST_ERROR;

	if (sendApproval(0) == false)
	{
		;	// Error 처리
	}
}

function onDeletePublicView()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveDeletePublicView.jsp";
	g_strReturnFunction = "onDeletePublicViewCompleted";

	g_arrMessage[0] = CONFIRM_DOC_DELETEPOST;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_DOC_DELETEPOST;
	g_arrMessage[4] = ALERT_DOC_DELETEPOST_ERROR;

	if (sendApproval(0) == false)
	{
		;	// Error 처리
	}
}

// onRejectCharger 대신 onCircularReverseApprove() 사용
function onRejectCharger()
{
	g_nActionType = 0;

	g_strActionUrl = "./approve/action/CN_ApproveRejectCharger.jsp";
	g_strReturnFunction = "onRejectChargerCompleted";

	g_arrMessage[0] = CONFIRM_REJECT_CHARGER;
	g_arrMessage[1] = MESSAGE_APPROVE;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_REJECT_CHARGER;
	g_arrMessage[4] = ALERT_REJECT_CHARGER_ERROR;

	sendApproval(0);
}

///////////////////////////////////////////////////////////////////////////////////////////////

function onSetExaminerInfo(strDelivererInfo)	// 없어져야 할 함수
{
	setDelivererInfo("0", strDelivererInfo);

	if (g_strIsDirect == "N")
	{
		if (sendApproval(1) == false)
		{
			;	// Error 처리
		}
	}
	else
	{
		if (sendApproval(0) == false)
		{
			;	// Error 처리
		}
	}
}

function onSetSenderInfo(strDelivererInfo)
{
	setDelivererInfo("1", strDelivererInfo);

	if (isPubdocRecipExist() == true)
	{
		makePubDocXML();
	}
	else
	{
		if (g_strIsDirect == "N")
		{
			if (sendApproval(1) == false)
			{
				;	// Error 처리
			}
		}
		else
		{
			if (sendApproval(0) == false)
			{
				;	// Error 처리
			}
		}
	}
}

function onSetDistributerInfo(strDelivererInfo)
{
	setDelivererInfo("2", strDelivererInfo);

	var arrDelivererInfo = strDelivererInfo.split("");
	var strDeptCode = arrDelivererInfo[10];

	var objRecipient = getRecipientByActualDeptCode(strDeptCode);
	if (objRecipient == null)
		return;

	var strReceiptStatus = "3";
	var strReceiptStatus = "";
	if (g_nActionType == 7)
		strReceiptStatus = "4";
	else if (g_nActionType == 21)
		strReceiptStatus = "7";
	else if (g_nActionType == 22)
		strReceiptStatus = "12";

	setDataAttribute(objRecipient, "METHOD", "keep");

	if (g_nActionType == 7 || g_nActionType == 21 || g_nActionType == 22)
		setRecipientReceiptStatus(objRecipient, strReceiptStatus);

	if (g_nActionType == 6)
	{
		objRecipient = getRecipientByMethod("replace");
		if (objRecipient != null)
		{
			// 처리과 정보를 Deliverer에 Setting
			strDelivererInfo = "";
			strDelivererInfo += getRecipientDeptName(objRecipient) + "";
			strDelivererInfo += getRecipientDeptCode(objRecipient) + "";
			strDelivererInfo += "";

			setDelivererInfo("3", strDelivererInfo);
		}
	}

	if (g_nPrevActionType == 0)
	{
		if (sendApproval(0) == false)
		{
			;	// Error 처리
		}
	}
	else
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
		window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
	}
}

function onSetReceiverInfo(strDelivererInfo)
{
	setDelivererInfo("3", strDelivererInfo);

	var arrDelivererInfo = strDelivererInfo.split("");
	var strDeptCode = arrDelivererInfo[10];

	var objRecipient = getRecipientByActualDeptCode(strDeptCode);
	if (objRecipient == null)
		return;

	var strReceiptStatus = "3";
	if (g_nActionType == 9 || g_nActionType == 25)
	{
		strReceiptStatus = "4";

		setRecipientDescription(objRecipient, getOpinion());
	}
	else if (g_nActionType == 23)
	{
		strReceiptStatus = "7";

		var objMoveRecipient = getRecipientByMethod("replace");
		if (objMoveRecipient != null)
			setRecipientDescription(objRecipient, "이송부서 : " + getRecipientDeptName(objMoveRecipient));
	}
	else if (g_nActionType == 24)
		strReceiptStatus = "12";

	setDataAttribute(objRecipient, "METHOD", "keep");
	setRecipientReceiptStatus(objRecipient, strReceiptStatus);

	if (g_nPrevActionType == 0)
	{
		if (sendApproval(0) == false)
		{
			;	// Error 처리
		}
	}
	else
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
		window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
	}
}

function onSetPublicViewerInfo(strDelivererInfo)
{
	setDelivererInfo("5", strDelivererInfo);

	if (sendApproval(0) == false)
	{
		;	// Error 처리
	}
}

// strDelivererType : 0=심사자, 1=발송자, 2=배부자, 3=접수자, 5=공람게시자, 6=이송자
function setDelivererInfo(strDelivererType, strDelivererInfo)
{
	var chrSepa = '';
	var arrDelivererInfo = strDelivererInfo.split(chrSepa);

	var strUserID = arrDelivererInfo[0];
	var strUserName = arrDelivererInfo[1];
	var strUserPosition = arrDelivererInfo[2];
	var strUserPositionAbbr = arrDelivererInfo[3];
	var strUserLevel = arrDelivererInfo[4];
	var strUserLevelAbbr = arrDelivererInfo[5];
	var strUserDuty = arrDelivererInfo[6];
	var strUserTitle = arrDelivererInfo[7];
	var strCompany = arrDelivererInfo[8];
	var strDeptName = arrDelivererInfo[9];
	var strDeptCode = arrDelivererInfo[10];
	var strSignDate = arrDelivererInfo[11];
	var strSignFileName = "";
	var strRepID = "";
	var strRepName = "";
	var strRepPosition = "";
	var strRepPositionAbbr = "";
	var strRepLevel = "";
	var strRepLevelAbbr = "";
	var strRepDuty = "";
	var strRepUserTitle = "";
	var strRepCompany = "";
	var strRepDeptName = "";
	var strRepDeptCode = "";


	if (strDelivererType != "5")
	{
		var objDeliverer = getDelivererByDelivererType(strDelivererType);

		if (objDeliverer == null)
			objDeliverer = addDeliverer();

		setDelivererType(objDeliverer, strDelivererType);
		setDelivererUserID(objDeliverer, strUserID);
		setDelivererUserName(objDeliverer, strUserName);
		setDelivererUserPosition(objDeliverer, strUserPosition);
		setDelivererUserPositionAbbr(objDeliverer, strUserPositionAbbr);
		setDelivererUserLevel(objDeliverer, strUserLevel);
		setDelivererUserLevelAbbr(objDeliverer, strUserLevelAbbr);
		setDelivererUserDuty(objDeliverer, strUserDuty);
		setDelivererUserTitle(objDeliverer, strUserTitle);
		setDelivererCompany(objDeliverer, strCompany);
		setDelivererDeptName(objDeliverer, strDeptName);
		setDelivererDeptCode(objDeliverer, strDeptCode);
		setDelivererSignDate(objDeliverer, strSignDate);
		setDelivererSignFilename(objDeliverer, strSignFileName);

		setDelivererRepID(objDeliverer, strRepID);
		setDelivererRepName(objDeliverer, strRepName);
		setDelivererRepPosition(objDeliverer, strRepPosition);
		setDelivererRepPositionAbbr(objDeliverer, strRepPositionAbbr);
		setDelivererRepLevel(objDeliverer, strRepLevel);
		setDelivererRepLevelAbbr(objDeliverer, strRepLevelAbbr);
		setDelivererRepDuty(objDeliverer, strRepDuty);
		setDelivererRepTitle(objDeliverer, strRepUserTitle);
		setDelivererRepCompany(objDeliverer, strRepCompany);
		setDelivererRepDeptName(objDeliverer, strRepDeptName);
		setDelivererRepDeptCode(objDeliverer, strRepDeptCode);

		setDelivererOpinion(objDeliverer, getOpinion());
	}
	else
	{
		var objDeliverer = getDelivererByDelivererType(strDelivererType);

		setDelivererType(objDeliverer, strDelivererType);
		setDelivererUserID(objDeliverer, strUserID);
		setDelivererUserName(objDeliverer, strUserName);
		setDelivererUserPosition(objDeliverer, strUserPosition);
		setDelivererUserPositionAbbr(objDeliverer, strUserPositionAbbr);
		setDelivererUserLevel(objDeliverer, strUserLevel);
		setDelivererUserLevelAbbr(objDeliverer, strUserLevelAbbr);
		setDelivererUserDuty(objDeliverer, strUserDuty);
		setDelivererUserTitle(objDeliverer, strUserTitle);
		setDelivererCompany(objDeliverer, strCompany);
		setDelivererDeptName(objDeliverer, strDeptName);
		setDelivererDeptCode(objDeliverer, strDeptCode);
		setDelivererSignDate(objDeliverer, strSignDate);
		setDelivererSignFilename(objDeliverer, strSignFileName);

		setDelivererOpinion(objDeliverer, getOpinion());
	}

	if (strDelivererType == "1")
	{
		setSendDate(strSignDate);
	}
	else if (strDelivererType == "2" || strDelivererType == "3" || strDelivererType == "6" || strDelivererType == "7")
	{
		if (strDelivererType == "2")
		{
			setDistributeSeed(g_strUserOrgCode);
		}
		else if (strDelivererType == "3")
		{
			setAcceptorID(strUserID);
			setAcceptorName(strUserName);
		}

		if (!(strDelivererType == "3" && getFlowStatus() == 9))
		{
			setRecvDate(getReceiveDate());

			setEnforceOrgCode(g_strUserOrgCode);
			setEnforceOrgName(g_strUserOrgName);
			setEnforceProcDeptCode(strDeptCode);
			setEnforceProcDeptName(strDeptName);
			setAccessLevelCode(strDeptCode);
			setAccessLevel(strDeptName);
		}
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////

function sendApproval(nType)
{
	var bRet = false;

	if (g_arrMessage[0] != "" && confirm(g_arrMessage[0]) == false)
	{
		if (g_nActionType == 1 || g_nActionType == 2 || g_nActionType == 13 || g_nActionType == 14)
		{
// Hj
//			clearUserSign(g_strApproverID);
		}
		else if (g_nActionType == 4)
		{
// Hj
//			clearExaminationSign();
		}
		else if (g_nActionType == 3)
		{
			if (getBodyType() == "han")
			{
				if (confirm(CONFIRM_DELETE_REJECT_OPINION))
				{
					insertOpinionToBody(g_strInputOpinion, "");
					setOpinion("");											// Document 에  의견을 셋팅.
					saveBodyFile();
					setModified(true);
					Document_HwpCtrl.MovePos(2);
				}
			}
		}

/*
		// 20030430, 다시 심사서명 안됨
		var objDeliverer = getDelivererByDelivererType(0);
		if (objDeliverer != null)
			removeDeliverer(objDeliverer);
*/

		g_bSendEnforceHold = false;
		return false;
	}
	else
	{
		if (g_nActionType == 12)
		{
			var objExtendAttach = getFirstExtendAttach();
			while (objExtendAttach != null)
			{
				var objNextExtendAttach = getNextExtendAttach(objExtendAttach);

				var strClassify = getAttachClassify(objExtendAttach)
				if (strClassify == "EnforceRelated")
				{
					var strSubDiv = getAttachSubDiv(objExtendAttach);
					if (strSubDiv == "CompanyStamp" || strSubDiv == "CompanyOmitStamp")
						objExtendAttach.parentNode.removeChild(objExtendAttach);
				}

    			objExtendAttach = objNextExtendAttach;
			}
		}
	}

	if ((g_nActionType == 2 || g_nActionType == 13 || g_nActionType == 3) && (g_strUseFP == "Y"))
	{
		var strReturnFunction = "prepareSendApproval(" + nType + ")";
//		onCheckFPPassword(strReturnFunction, nType);
		onCheckFPPassword(strReturnFunction);
	}
	else if ((g_nActionType == 2 || g_nActionType == 13 || g_nActionType == 3) && g_strUsePassword == "Y")
	{
		var strReturnFunction = "prepareSendApproval(" + nType + ")";
		onCheckPassword(strReturnFunction);
	}
	else
		prepareSendApproval(nType);
}

function checkOptionFPUse()
{
	var strOptionFPUse = "N";
	var strOption = "";
	var arrOption76 = new Array;
	var strOption76 = g_strOption76;
	if (strOption76 != "")
	{
		arrOption76 = strOption76.split("^");
		for (i = 0 ; i < arrOption76.length ; i++)
		{

			strOption = arrOption76[i];
			if (strOption == "FP")
			{
				strOptionFPUse = "Y";
				break;
			}

		}
	}
	return strOptionFPUse;
}

//function onCheckFPPassword(strFunction, nType)
function onCheckFPPassword(strFunction)
{
//	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveFPCheckDialog.jsp?function=" + strFunction + "&type=" + nType;
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveFPCheckDialog.jsp?function=" + strFunction + "&location=view";
	dataTransform.location.href = strUrl;
}

//function setExistDevice(bDevice, nType)	// 지문인식 장치 존재 유무
function setExistDevice(bDevice, strFunction)	// 지문인식 장치 존재 유무
{
	if (bDevice == false)			//지문사용 설정, 지문장치 없는 경우 결재비밀번호 입력창
	{
//		var strReturnFunction = "prepareSendApproval(" + nType + ")";
//		onCheckPassword(strReturnFunction);
		onCheckPassword(strFunction);
	}
}

function prepareSendApproval(nType)
{
	if (g_nActionType == 1 || g_nActionType == 2 || g_nActionType == 13 || g_nActionType == 14)
	{
		if (g_nActionType == 13)
		{
			var objApprover = getActiveApprover();
			setApproverAction(objApprover, "8");
		}

		setApprovalSign();		// 임시

		if (checkAutoEnforce() == true)
		{
			getLinkedEnforceForm();
			return;
		}
	}
	else if (g_nActionType == 3)
	{
		var objApprover = getActiveApprover();
		setApproverAction(objApprover, "9");

		setApprovalSign();
	}
	else if (g_nActionType == 4)
		setExaminationSign();

	doSendApproval(nType);
}

function getAutoEnforceStamp()
{
	g_strOption171 = "0";

	if (isAllEnforceStamped() == 0)
	{
		if (g_nPrevActionType == 5)
			onSendEnforce();
		return;
	}

	var strDeptCodeList = "";

	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
			strDeptCodeList += getSenderAsCode(nProposal);
		else
			strDeptCodeList += " ";	// StringTokenizer 클래스가 붙어있는 구분자는 Split하지 않아 공백이라도 추가

		strDeptCodeList += "^";
		nProposal++;
	}

	dataForm.xmlData.value = strDeptCodeList;
	dataForm.returnFunction.value = "doAutoEnforceStamp";

	dataForm.action = "./signature/action/CN_SignatureGetAutoEnforceStamp.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function doAutoEnforceStamp(strStampType, strEnforceStampList, strWidthList, strHeightList)
{
	var arrStampType = strStampType.split("^");
	var arrStampList = strEnforceStampList.split("^");
	var arrWidthList = strWidthList.split("^");
	var arrHeightList = strHeightList.split("^");

	var nCurEnforceProposal = 1;
	if (g_strIsDirect == "N")
		nCurEnforceProposal = getEnforceProposal();

	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			var strStampType = arrStampType[nProposal - 1];
			var strStamp = arrStampList[nProposal - 1];
			var strWidth = arrWidthList[nProposal - 1];
			var strHeight = arrHeightList[nProposal - 1];

			if (strStamp != "" && getExtendedSealInfo(nProposal) == null)
			{
				if (g_strIsDirect == "N")
					setEnforceProposal(nProposal);

				setEnforceStamp(strStampType, strStamp, strWidth, strHeight);
			}
		}

		nProposal++;
	}

	if (g_strIsDirect == "N")
	{
		setEnforceProposal(nCurEnforceProposal);
		setEnforceApprovalSeal();
	}

	if (g_nPrevActionType == 5)
		onSendEnforce();
}

function getLinkedEnforceForm()
{
	var strFormUsage = getFormUsage();

	if (strFormUsage == "0")
	{
		var strEnforceFormID = getEnforceFormID();

		dataForm.xmlData.value = strEnforceFormID;
		dataForm.returnFunction.value = "onPrepareAutoEnforce";

		dataForm.action = "./approve/dialog/CN_ApproveFormLinkedEnforce.jsp";
		dataForm.target = "dataTransform";
		dataForm.submit();
	}
	else if (strFormUsage == "1")
	{
		setRegistryType("I");
		setEnforceFormID("");

		if (getFlowStatus() == "0")
			changeMenu(3);

		dataForm.xmlData.value = "";
		dataForm.returnFunction.value = "onAutoSendEnforce";

		dataForm.action = "./signature/action/CN_SignatureGetAutoDeptImage.jsp";
		dataForm.target = "dataTransform";
		dataForm.submit();
	}
}

function onPrepareAutoEnforce(nType)
{
	if (nType == true)
	{
		var strEnforceFormID = getEnforceFormID();
		var strBodyType = getBodyType();

		if (onOpenEnforceForm(strEnforceFormID + "." + strBodyType, strBodyType, "", "", "", "", false) == true)
		{
			setRegistryType("I");
			setEnforceFormID("");
			changeMenu(3);

			dataForm.xmlData.value = "";
			dataForm.returnFunction.value = "onAutoSendEnforce";

			dataForm.action = "./signature/action/CN_SignatureGetAutoDeptImage.jsp";
			dataForm.target = "dataTransform";
			dataForm.submit();

			return;
		}
	}

	if (confirm(CONFIRM_ERROR_AUTO_ENFORCE) == true)
		doSendApproval(0);
	else
		changeMenu(2);
}

function doSendApproval(nType)
{
	if (g_bDoSendApproval == true)
		return false;

	showWaitDlg(g_arrMessage[1], g_arrMessage[2]);

// 연계문서 제목 표시
/*
	if (getIsAdminMis() == "Y")
	{
		var strTitle = getTitle(1);
		if (strTitle.substring(0,6) != "[연계문서]")
			strTitle = "[연계문서]" + strTitle;
		setTitle(strTitle, 1);
	}
*/
	if (nType == 0)
	{
		// 20030827, icaha
		clearEnforceAttachInfo();
		bRet = sendBody();
	}
	else if (nType == 1)
	{
		sendBody();
		bRet = sendEnforce();
	}

	if (bRet == true)
	{
		var strApprovalDoc = toXMLString(ApprovalDoc);
		dataForm.xmlData.value = strApprovalDoc;
		dataForm.returnFunction.value = g_strReturnFunction;
		dataForm.action = g_strActionUrl;
		dataForm.target = "dataTransform";
		dataForm.submit();

		g_bDoSendApproval = true;
	}

	return bRet;
}

function toXMLString(objXML)
{
	var strResult = "";
	var strXML = objXML.xml;

	var strPrevElement = "";
	var nFind = strXML.indexOf("><");
	while (nFind != -1)
	{
		var bSeparate = false;

		if (strXML.charAt(nFind + 2) == '/')
		{
			var nNextFind = strXML.indexOf(">", nFind + 2);
			if (nNextFind != -1)
			{
				var strNextElement = strXML.substring(nFind + 3, nNextFind);
				var strPrevElement = strXML.substring(nFind - strNextElement.length, nFind);
				if (strNextElement != strPrevElement)
				{
					bSeparate = true;
				}
				else
				{
					nFind = strXML.indexOf("><", nFind + 2);
				}
			}
		}
		else
		{
			bSeparate = true;
		}

		if (bSeparate)
		{
			strResult += strXML.substring(0, nFind + 1);
			strResult += "\r\n";
			strXML = strXML.substring(nFind + 1, strXML.length);

			nFind = strXML.indexOf("><");
		}
	}

	strResult += strXML;

	return strResult;
}

function sendBody()
{
	if (isModified())
	{
		var bSaveFile = false;

		var objBodyFile = getBodyFileObj();
		if (objBodyFile == null)
		{
			bSaveFile = true;
			objBodyFile = setBodyFileInfo();
		}
		else
		{
// 20030429, 수동 날인시 저장할 타이밍이 없음(이벤트 방식으로 하려 하나, OCX 가 또 수정되어야 함)
			if (g_nActionType == 1 || g_nActionType == 14 || getBodyType() == "hwp" || getBodyType() == "han")
				bSaveFile = true;
		}

		if (bSaveFile == true)
		{
			if (saveBodyFile() == false)
			{
				hideWaitDlg();
				alert(ALERT_PREPARE_SEND_APPROVE);
				return false;
			}
		}

		var strMethod = getAttachMethod(objBodyFile);
		if (strMethod == "")
			setAttachMethod(objBodyFile, "replace");

		// 20030904
//		if (getLegacySystem() != null )
//			setFormDataToLegacyXML(ApprovalDoc.selectSingleNode("//LEGACY_DATA").firstChild);

		if (uploadBodyFile() == false)
		{
			hideWaitDlg();
			alert(ALERT_FILE_SEND_ERROR);
			return false;
		}
	}

	return true;
}

function sendEnforce()
{
	if (isPubdocRecipExist())
		setEnforceModified(true);

	if (isEnforceModified())
	{
		// 20030613, 수동 날인시 저장할 타이밍이 없음(이벤트 방식으로 하려 하나, OCX 가 또 수정되어야 함)
		if (getBodyType() == "hwp" || getBodyType() == "han")
			saveEnforceFile(g_nEnforceProposal);

		var nCurrentProposal = getEnforceProposal();

		var nProposal = 1;
		var nBodyCount = getBodyCount();
		while (nProposal <= nBodyCount)
		{
			var strDocCategory = getDocCategory(nProposal);
			if (strDocCategory == "E" || strDocCategory == "W")
			{
				var objEnforceFile = getEnforceFileObj(nProposal);
				if (objEnforceFile == null)
				{
					setEnforceProposal(nProposal);
					loadEnforceDocument();

					objEnforceFile = getEnforceFileObj(nProposal);
				}

				var nEnforceFileSize = getLocalFileSize(g_strDownloadPath + getAttachFileName(objEnforceFile));

				var strMethod = getAttachMethod(objEnforceFile);
				if (strMethod == "")
				{
//					if (g_nActionType == 11)
//					{
//						if (nEnforceFileSize > 0)
//							setAttachMethod(objEnforceFile, "add");
//					}
//					else
						setAttachMethod(objEnforceFile, "replace");
				}

				if (nEnforceFileSize > 0)
				{
					if (uploadEnforceFile(nProposal) == false)
					{
//						hideWaitDlg();
//						alert(ALERT_FILE_SEND_ERROR);
//						return false;
					}
				}
			}

			nProposal++;
		}

		setEnforceProposal(nCurrentProposal);
		loadEnforceDocument();

		setIsConverted("Y");
	}

	return true;
}

function setBodyFileInfo()
{
	var strGUID = getGUID();
	var strDisplayName = "Body";
	var strFileName = strGUID + "." + getFileExtension();
	var strFileSize = "0";
	var strLocation = "";

	var objFileBody = null;

	if (getCurrentLineType() == "5")
	{
		strDisplayName = "InspBody";

		objFileBody = addExtendAttachInfo("1", strDisplayName, strFileName, strFileSize, strLocation);

		setChildNodeData(objFileBody, "CLASSIFY", "InspBody");
		setChildNodeData(objFileBody, "CHARGER_LINE_NAME", g_strLineName);
		setChildNodeData(objFileBody, "CHARGER_SERIAL_ORDER", "0");
	}
	else
	{
		objFileBody = addBodyFileInfo(strDisplayName, strFileName, strFileSize, strLocation);
	}

	return objFileBody;
}

function setEnforceFileInfo(strCaseNumber)
{
	var strGUID = getGUID();
	var strDisplayName = "Enforce";
	var strFileName = strGUID + "." + getFileExtension();
	var strFileSize = "0";
	var strLocation = "";

	return addEnforceFileInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation);
}

function setSignFileInfo()
{
	if (g_strSignUrl != "")
	{
		var strDisplayName = g_strSignUrl;
		var strFileName = g_strSignUrl;
		var strFileSize = getLocalFileSize(g_strDownloadPath + g_strSignUrl);
		var strLocation = "";

		removeRelatedAttachInfo(strFileName);
		addRelatedAttachInfo(strDisplayName, strFileName, strFileSize, strLocation);
	}
}

function setExaminationSignFileInfo(strFileName)
{
	if (strFileName != "")
	{
		var strDisplayName = strFileName;
		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		var strLocation = "";

		removeRelatedAttachInfo(strFileName);
		var objRelatedAttach = addRelatedAttachInfo(strDisplayName, strFileName, strFileSize, strLocation);

		setAttachClassify(objRelatedAttach, "InspStamp");
		setAttachSubDiv(objRelatedAttach, "");
	}
}

function doSendToSystem(strSystemID)
{
	var strFlowStatus = getFlowStatus();
//alert(strFlowStatus);
//	if ((g_strEditType != "0" &&  g_strEditType != "5" && g_strEditType != "10") && (strFlowStatus != "10" && strFlowStatus != "12" && strFlowStatus != "14"))
	if (strFlowStatus == "1" || strFlowStatus == "6" || strFlowStatus == "7" || strFlowStatus == "15")
	{
/*
		var strClassNumberID = getClassNumberID();
		if (strSystemID == "SDSDM" && strClassNumberID == "")
		{
			alert(ALERT_NOT_SET_CLASSNUM_DOCUMENT);
			return;
		}
		var strSecurityPass = getSecurityPass();
		if (strSystemID == "SDSDM" && strSecurityPass != "")
		{
			alert(ALERT_SECURITY_DOCUMENT);
			return;
		}
*/
		g_strActionUrl = "./related/action/CN_RelatedSendDoc.jsp";
		g_strReturnFunction = "onSendCompleted";

		var strApprovalDoc = toXMLString(ApprovalDoc);
		var strTableType = "";
		if(parseInt(strFlowStatus,10) < 8)
			strTableType = "1";
		else
			strTableType = "3";
		var strExtendData = strTableType + "^" + strSystemID;

		g_arrMessage[1] = MESSAGE_WAIT;
		g_arrMessage[2] = MESSAGE_WAIT_MOMENT;
		showWaitDlg(g_arrMessage[1], g_arrMessage[2]);

		dataForm.xmlData.value = strApprovalDoc;
		dataForm.extendData.value = strExtendData;
		dataForm.returnFunction.value = g_strReturnFunction;
		dataForm.action = g_strActionUrl;
		dataForm.target = "dataTransform";
		dataForm.submit();
	}
	else if (strFlowStatus == "14")
	{
		var strDocStatus = "";
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
			var strDocStatus = getDocStatus(objApprovalLine);

		if (strDocStatus != "" && strDocStatus != "1" && strDocStatus != "11" && strDocStatus != "12")
			alert(ALERT_CANNOT_SEND_TO);
	}
}

function onSendCompleted(result)
{
	hideWaitDlg();
	g_bDoSendApproval = false;

	if (result == "")
		alert(ALERT_DOC_SEND);
	else
		alert(ALERT_DOC_SEND_ERROR);
}

function onCertificateRecipient()
{
	var nCaseNum = isAllRecipSpecified();
	if (nCaseNum != 0)
	{
		if (getBodyCount() == 1)
			alert(ALERT_NO_RECIPIENT);
		else
			alert(nCaseNum + ALERT_NO_RECIPIENT_IN_DOC);

		onSetRecipPart();
		return;
	}

	var Converter = new ActiveXObject("PubDocConverter.Converter");
	if (Converter != null)
	{
		var bHas = Converter.HasCertificate();
		if (bHas == true)
		{
			var nBodyCount = getBodyCount();
			var objRecipientGroup = "";
			var objRecipient = "";
			var strRecipientActualDeptCode = "";
			var strRecipientIsPubdocRecip = "";

			for (var i = 1; i <= nBodyCount; ++i)
			{
				objRecipientGroup = getRecipGroup(i);
				if (objRecipientGroup != null)
				{
					objRecipient = getFirstRecipient(objRecipientGroup);
					while (objRecipient != null)
					{
						strRecipientIsPubdocRecip = getRecipientIsPubdocRecip(objRecipient);
						if (strRecipientIsPubdocRecip == "Y")
						{
							if (strRecipientActualDeptCode == "")
								strRecipientActualDeptCode += getRecipientActualDeptCode(objRecipient);
							else
								strRecipientActualDeptCode += "^" + getRecipientActualDeptCode(objRecipient);
						}
						objRecipient = getNextRecipient(objRecipient);
					}
				}
			}
//alert(strRecipientActualDeptCode);

			if (strRecipientActualDeptCode != "")
			{
				g_strActionUrl = "./approve/action/CN_CertificateRecipient.jsp";
				g_strReturnFunction = "onCertificateCompleted";

				dataForm.xmlData.value = strRecipientActualDeptCode;
				dataForm.extendData.value = "Y";
				dataForm.returnFunction.value = g_strReturnFunction;
				dataForm.action = g_strActionUrl;
				dataForm.target = "dataTransform";
				dataForm.submit();
			}
			else
			{
				onCertificateCompleted("");
			}
		}
		else
		{
			onCertificateCompleted("");
		}
	}
	else
	{
		onCertificateCompleted("");
	}
}

function onCertificateCompleted(result)
{
//alert("2:" + result);
	if (result == "")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveEncryptRecipient.jsp?HasCertificate=N";
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
	else
	{
		var bRet = doCertificateRecipient(result);
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveEncryptRecipient.jsp?HasCertificate=Y";
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
}

function doCertificateRecipient(result)
{
//alert(result);
	var nBodyCount = getBodyCount();
	var objRecipientGroup = "";
	var objRecipient = "";
	var strRecipientActualDeptCode = "";
	var strRecipientIsPubdocRecip = "";

	for (var i = 1; i <= nBodyCount; ++i)
	{
		objRecipientGroup = getRecipGroup(i);
		if (objRecipientGroup != null)
		{
			objRecipient = getFirstRecipient(objRecipientGroup);
			while (objRecipient != null)
			{
				strRecipientIsPubdocRecip = getRecipientIsPubdocRecip(objRecipient);
				if (strRecipientIsPubdocRecip == "Y")
				{
					strRecipientActualDeptCode = getRecipientActualDeptCode(objRecipient);
					var arrInfo = result.split("$.$");
					for (var i = 0; i < arrInfo.length; i++)
					{
						var arrCode = arrInfo[i].split("^");
						if (arrCode[0] == strRecipientActualDeptCode && arrCode[1] == "true")
							setRecipientIsCertExist(objRecipient, "Y");
					}
				}
				objRecipient = getNextRecipient(objRecipient);
			}
		}
	}
	return true;
}

function doSaveBody4RelatedDoc()
{
	var strBodyName = getAttachFileName(getBodyFileObj());

	if (strBodyName != "")
	{
		var strBodyPath = g_strDownloadPath + strBodyName;
		saveApproveDocumentAs(strBodyPath, true);

		var bRet = uploadBodyFile();

		return bRet;
	}
	else
	{
		doReleaseBody4RelatedDoc();

		return false;
	}
}

function doReleaseBody4RelatedDoc()
{
	saveBodyFile();
	setModified(true);
}

// for 법무부
function setSenderAs4Recipients(strRecipientCodeList)
{
	g_strActionUrl = "./approve/action/CN_ApproveComputeSenderAs.jsp";
	g_strReturnFunction = "onSetSenderAsInfoCompleted";

	dataForm.xmlData.value = strRecipientCodeList;
	dataForm.returnFunction.value = g_strReturnFunction;
	dataForm.action = g_strActionUrl;
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onSetSenderAsInfoCompleted(strSenderAsCaseNumberList, strSenderAsDeptCodeList, strSenderAsList)
{
//alert(strSenderAsCaseNumberList);
//alert(strSenderAsDeptCodeList);
//alert(strSenderAsList);
	var arrSenderAsCaseNumberList = strSenderAsCaseNumberList.split("^");
	var arrSenderAsDeptCodeList = strSenderAsDeptCodeList.split("^");
	var arrSenderAsList = strSenderAsList.split("^");
	for (var i = 0; i < arrSenderAsCaseNumberList.length; i++)
	{
		setSenderAs(arrSenderAsList[i], arrSenderAsCaseNumberList[i]);
		setSenderAsCode(arrSenderAsDeptCodeList[i], arrSenderAsCaseNumberList[i]);
		setSenderAsToForm(arrSenderAsList[i]);
	}
}
