// AdminMis로 Notification("submit", "approval" type)
// submit   : 상신, 재상신
// approval : 결재, 반려, 기안회수

function onSendDocToAdminSys(strType)
{
	var bUploadResult = false;
	var strExchangeXml = onConvertDocXmlToAdminXml("");
	var strResultApprovalDocXml = dataTransform.ApprovalDoc.documentElement.xml;

	var objApprover = getActiveApprover();
	if (objApprover != null && strExchangeXml != "")
	{
		strApproverRole = getApproverRole(objApprover);
		if ((strApproverRole == "0" || strApproverRole == "1") && strType == "SUBMIT")
		{
			var strExchPackFileName = onPack(strResultApprovalDocXml, strExchangeXml, "submit");
		}
		else
		{
			var strExchPackFileName = onPack(strResultApprovalDocXml, strExchangeXml, "approval");
		}

		if (strExchPackFileName != "" && strExchPackFileName != null)
		{
			var strLocalPath = g_strDownloadPath + strExchPackFileName;
			var strServerUrl = g_strMisDocUploadUrl + strExchPackFileName;
			bUploadResult = uploadFile(strLocalPath, strServerUrl);
		}
	}

	return bUploadResult;
}


// 반송메뉴 Action함수
// Mis에 Noti & 폐기함으로...

function onSendReturnNotiToAdminMis()
{
	g_arrMessage[0] = CONFIRM_DOC_RETURN_TO_ADMIN_MIS;
	g_arrMessage[1] = MESSAGE_DOCFLOW;
	g_arrMessage[2] = MESSAGE_DOC_SENDING;
	g_arrMessage[3] = ALERT_RETURNED_DOC;
	g_arrMessage[4] = ALERT_ERROR_RETURN_DOC;

	if (confirm(g_arrMessage[0]) == false)
		return false;

/*
	if(getActiveApprovalLine() == null)
	{
		alert("반송하기 위해서는 결재선이 지정되어야 합니다.");
		onSetApproveLine();
		return;
	}
*/

	showWaitDlg(g_arrMessage[1], g_arrMessage[2]);

	var bResultNoti = onReturnDocToAdminSys();

	if (!bResultNoti)
	{
		changeMenu(2);
		hideWaitDlg();

		alert(g_arrMessage[4] + " [Notification]");
		return false;
	}
	
	onDiscardDocument();
}


// AdminMis로 Notification ("return" type)
// return : 반송 --> 반송메뉴 onSendReturnNotiToAdminMis()를 거쳐 noti.

function onReturnDocToAdminSys()
{
	var bUploadResult = false;
	var strExchPackFileName = onPackHeaderOnly("return");

	if (strExchPackFileName != "" && strExchPackFileName != null)
	{
		var strLocalPath = g_strDownloadPath + strExchPackFileName;
		var strServerUrl = g_strMisDocUploadUrl + strExchPackFileName;
		bUploadResult = uploadFile(strLocalPath, strServerUrl);
	}
	
	return bUploadResult;
}


// 완결자인지 검사
function isApproverCloseLine()
{
	var objLastApprover = getCompleteApprover();

	if (objLastApprover != null)
	{
		var strLastApproverId = getApproverUserID(objLastApprover);
		var strCurrentApproverId = g_strUserID;

		if (strLastApproverId == strCurrentApproverId)
			return true;
		else
			return false;
	}
	
	return false;
}

function needNotiToMisLastStep()
{
	var bNeedNoti = (getIsAdminMis() == "Y") && isApproverCloseLine();
	return bNeedNoti;
}

function needNotiToMisEveryStep()
{
	var bNeedNoti = false;
	var objRelatedSystem = getRelatedSystemBySystemID("GovernmentMisSystemExchangeXml");

	if (objRelatedSystem != null)
	{
		var strNotificationFlag = getReleatedSystemProcessType(objRelatedSystem);
		var bNeedNoti = (getIsAdminMis() == "Y") && (strNotificationFlag == "2");
	}

	return bNeedNoti;
}

function onDiscardDocument()
{
	var strDocId = getDocID();
	var strDocStatus = "1";
	var strLineName = "0";
	var nSerialOrder = "0";
	var strFunction = "onDiscardDocumentCompleted";
		
	dataForm.action = "approve/action/CN_ApproveDiscardDoc.jsp?dataurl=" + strDocId + "&docstatus=" + strDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&returnFunction=" + strFunction;
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onDiscardDocumentCompleted(nType)
{
	changeMenu(2);
	hideWaitDlg();

	if (nType == true)
		alert(g_arrMessage[3]);
	else
		alert(g_arrMessage[4]);
}


function onMakeInputData()
{
	onMakePackStart();
}