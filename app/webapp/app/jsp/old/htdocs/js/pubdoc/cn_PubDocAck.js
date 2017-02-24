function setAck(nAckType, strMsg)
{
	var objPubPackFile = getExtendAttachBySubDiv("", "PubPack");
	var strPackFile = getAttachFileName(objPubPackFile);
	var strSendId = strPackFile.substr(7, 7);

	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;
	var strFileName = strSendId + getDraftProcDeptCode() + getCurrentDate("") + getCurrentTime("") + "01.xml";
//	var strFileName = strSendId + strPackFile.substr(0, 7) + getCurrentDate("") + getCurrentTime("") + "01.xml";

	var objPubPackDocument = getPubPackDocument(nAckType);
	objPubPackDocument.FileName = strFileName;

	var objDeliverer = null;
	if (g_strEditType == 18)
		objDeliverer = getDelivererByDelivererType(2);					// 배부함 2
	else if (g_strEditType == 20)
		objDeliverer = getDelivererByDelivererType(3);					// 접수함 3

	if (nAckType == ACK_ACCEPT)
		objPubPackDocument.DocType = ACK_ACCEPT_STRING;
	else if (nAckType == ACK_RETURN)
	{
		objPubPackDocument.DocType = ACK_RETURN_STRING;
		objPubPackDocument.setReturn(getDelivererOpinion(objDeliverer));
	}
//	else if (nAckType == ACK_RESEND)
//		objPubPackDocument.DocType = ACK_RESEND_STRING;
	else if (nAckType == ACK_REQ_RESEND)
	{
		objPubPackDocument.DocType = ACK_REQ_RESEND_STRING;
		objPubPackDocument.SetReturn(strMsg);
	}

	objPubPackDocument.SaveToXMLFile(strPubDocPath + strFileName);

	var strServerUrl = g_strPubDocUploadUrl + strFileName;
	bReturn = uploadFile(strPubDocPath + strFileName, strServerUrl);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR_ACK + "\nError Type:Upload Ack Message to PubDocUploadDir\nuploadFile(strLocalPath, strServerUrl) Fail");
		return;
	}

	objDeliverer = null;
}

function getPubPackDocument(nAckType)
{
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;

	var objPubPackDocument = new ActiveXObject("PubDocConverter.PubPackDocument");
	objPubPackDocument.FileFolder = strPubDocPath;

	var strSendOrgCode = "";
	var strSendID = "";
	var strSendName = "";
	var strReceiveID = "";
	var strDate = "";
	var strTitle = "";
	var strDocID = "";
	var strDept = "";
	var strName = "";
	var strDTDVersion = "";
	var strXSLVersion = "";

	var objPubPackFile = getExtendAttachBySubDiv("", "PubPack");
	var strPackFile = getAttachFileName(objPubPackFile);
	var strSendId = strPackFile.substr(7, 7);

	var objDeliverer = null;
	if (g_strEditType == 18)
		objDeliverer = getDelivererByDelivererType(2);					// 배부함 2
	else if (g_strEditType == 20)
		objDeliverer = getDelivererByDelivererType(3);					// 접수함 3
	var objRecipGroup = getRecipGroup(1);
	if (objRecipGroup != null)
		var objRecipient = getFirstRecipient(objRecipGroup);

	if (nAckType != 4)
	{
		objPubPackDocument.SendOrgCode = getEnforceOrgCode();				// ENFORCE_ORG_CODE, g_strUserOrgCode
//		objPubPackDocument.SendID = getEnforceProcDeptCode();				// ENFORCE_PROC_DEPT_CODE, g_strUserDeptCode
		objPubPackDocument.SendID = strSendId;
		objPubPackDocument.SendName = getEnforceOrgName();				// ENFORCE_ORG_NAME, g_strUserOrgName
		objPubPackDocument.ReceiveID = getDraftProcDeptCode();				// DRAFT_PROC_DEPT_CODE
//		objPubPackDocument.ReceiveID = strPackFile.substr(0, 7);				// DRAFT_PROC_DEPT_CODE
		objPubPackDocument.Title = getTitle(1);
		objPubPackDocument.DocID = getSenderDocID();
		objPubPackDocument.Date = getDelivererSignDate(objDeliverer);
		objPubPackDocument.Dept = getDelivererDeptName(objDeliverer);
		objPubPackDocument.Name = getDelivererUserName(objDeliverer);
		objPubPackDocument.SendGW = "ACUBE Communication for Gov 5.0";
	}
	else
	{
		objPubPackDocument.SendOrgCode = g_strUserOrgCode;				// ENFORCE_ORG_CODE, g_strUserOrgCode
//		objPubPackDocument.SendID = g_strUserDeptCode;					// ENFORCE_PROC_DEPT_CODE, g_strUserDeptCode
		objPubPackDocument.SendID = strSendId;
		objPubPackDocument.SendName = g_strUserOrgName;					// ENFORCE_ORG_NAME, g_strUserOrgName
		objPubPackDocument.ReceiveID = getDraftProcDeptCode();				// DRAFT_PROC_DEPT_CODE
//		objPubPackDocument.ReceiveID = strPackFile.substr(0, 7);				// DRAFT_PROC_DEPT_CODE
		objPubPackDocument.Title = getTitle(1);
		objPubPackDocument.DocID = getSenderDocID();
		var arrUserInfo = g_strRepUserInfo.split("");					// ytaekwon윤택원과장2급갑과장삼성1과장삼성SDS삼성1과S905001";
		objPubPackDocument.Date = getCurrentDate("-") + " " + getCurrentTime(":");
		objPubPackDocument.Dept = g_strRepUserInfo[9];
		objPubPackDocument.Name = g_strRepUserInfo[1];
		objPubPackDocument.SendGW = "ACUBE Communication for Gov 5.0";
	}

	objPubPackDocument.DTDVersion = ACK_DTD_VERSION;
	objPubPackDocument.XSLVersion = ACK_XSL_VERSION;

	objDeliverer = null;
	objRecipGroup = null;
	objRecipient = null;

	return objPubPackDocument;
}
