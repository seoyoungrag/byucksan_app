var g_objPubDocDom = null;
var g_strPackFile = "";

function makePubDocXML()
{
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;
	deleteDownloadPath(strPubDocPath);
	createDownloadPath(strPubDocPath);
	saveGeneralAttachFiles2Server();
}

function saveGeneralAttachFiles2Server()
{
	var objAttach = getFirstGeneralAttach();
	var objAttachBody = getRelatedAttachListByClassify("AttachBody");
	if ((objAttach != null) || (objAttachBody.length > 0))
	{
		var strActionUrl = "./common/jsp/CN_GetStoreFileAll.jsp";
		var strReturnFunction = "downloadGenFiles";
		var strApprovalDoc = toXMLString(ApprovalDoc);

		dataForm.xmlData.value = strApprovalDoc;
		dataForm.returnFunction.value = strReturnFunction;
		dataForm.action = strActionUrl;
		dataForm.target = "dataTransform";
		dataForm.submit();
	}
	else
	{
		processPubDoc();
	}
}

function downloadGenFiles(bReturn)
{
//	if (bReturn == false)
//	{
//		alert(ALERT_ERROR_OCCUR + "\nError Type:\nsaveGeneralAttachFiles2Server() Fail");
//		return;
//	}

	var strUploadUrl = getUploadUrl();
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;

	var bReturn = true;
/*
	bReturn = createDownloadPath(strPubDocPath);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR + "\nError Type:\ncreateDownloadPath(strPubDocPath) Fail");
		return;
	}
*/

	var objAttach = getFirstGeneralAttach();
	while (objAttach != null)
	{
		var strDisplayName = getAttachDisplayName(objAttach);
		var strFileName = getAttachFileName(objAttach);
		var strServerUrl = strUploadUrl + strFileName;
	        var strLocalPath = strPubDocPath + strDisplayName + getExtention(strFileName);
	        bReturn = downloadFile(strServerUrl, strLocalPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:General Attach Download\ndownloadFile(strServerUrl, strLocalPath) Fail");
			return;
		}
		objAttach = getNextGeneralAttach(objAttach);
	}

	var objAttachBody = getRelatedAttachListByClassify("AttachBody");
	if (objAttachBody != null)
	{
		for (var iAttachBody = 0; iAttachBody < objAttachBody.length; ++iAttachBody)
		{
			var strDisplayName = getAttachDisplayName(objAttachBody(iAttachBody));
			var strFileName = getAttachFileName(objAttachBody(iAttachBody));
			var strServerUrl = strUploadUrl + strFileName;
	                var strLocalPath = strPubDocPath + strDisplayName + getExtention(strFileName);
	                bReturn = downloadFile(strServerUrl, strLocalPath);
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR + "\nError Type:AttachBody Download\ndownloadFile(strServerUrl, strLocalPath) Fail");
				return;
			}
		}
	}
/*
	var strFormUsage = getFormUsage();
	if (strFormUsage == "2")
	{
		var objAttachBody = getRelatedAttachListByClassify("AttachBody");
		if (objAttachBody != null)
		{
			for (var iAttachBody = 0; iAttachBody < objAttachBody.length; ++iAttachBody)
			{
				var strDisplayName = getAttachDisplayName(objAttachBody(iAttachBody));
				var strFileName = getAttachFileName(objAttachBody(iAttachBody));
				var strServerUrl = strUploadUrl + strFileName;
		                var strLocalPath = strPubDocPath + strDisplayName + getExtention(strFileName);
		                bReturn = downloadFile(strServerUrl, strLocalPath);
				if (bReturn == false)
				{
					alert(ALERT_ERROR_OCCUR + "\nError Type:AttachBody Download\ndownloadFile(strServerUrl, strLocalPath) Fail");
					return;
				}
			}
		}
	}
*/
	processPubDoc();
}

function processPubDoc()
{
	var strUploadUrl = getUploadUrl();
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;

	var bReturn = true;
/*
	bReturn = createDownloadPath(strPubDocPath);
//	if (bReturn == false)
//	{
//		alert(ALERT_ERROR_OCCUR + "\nError Type:\ncreateDownloadPath(strPubDocPath) Fail");
//		return;
//	}

	var objAttach = getFirstGeneralAttach();
	while (objAttach != null)
	{
		var strDisplayName = getAttachDisplayName(objAttach);
		var strFileName = getAttachFileName(objAttach);
		var strServerUrl = strUploadUrl + strFileName;
                var strLocalPath = strPubDocPath + strDisplayName + getExtention(strFileName);
                bReturn = downloadFile(strServerUrl, strLocalPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:\ndownloadFile(strServerUrl, strLocalPath) Fail");
			return;
		}
		objAttach = getNextGeneralAttach(objAttach);
	}
*/

	var strBodyXMLPath = strPubDocPath + BODY_XML_FILE;
	var strFormUsage = getFormUsage();
//	if (strFormUsage == "2")
//		bReturn = saveBlankBodyToXML(strBodyXMLPath);
//	else
		bReturn = saveBodyToXML(strBodyXMLPath);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR + "\nError Type:Save Body Content to XML File\nsaveBodyToXML(strBodyXMLPath) Fail");
		return;
	}

	g_objPubDocDom = getPubDocDom();
	var objDic = getHashTable();

// Add organ Node To head
	var strOrgan = "";
	if (isExistKey(objDic, FORM_NAME_ORGAN))
	{
		strOrgan = getValue(objDic, FORM_NAME_ORGAN);
	}
	createNsetNode("head", "organ", strOrgan);

// Add receiptinfo Node to head
	createNsetNode("head", "receiptinfo", "");

// Add recipient Node to receiptinfo
	var strRefer = getRecipientRefAttribute();
	createNsetNode("receiptinfo", "recipient", "");
	setAttributeValue2Node("recipient", "refer", strRefer);

// Add rec Node to recipient
	var strRecipientList = getRecipientList();
	createNsetNode("recipient", "rec", strRecipientList);

// Add via Node to receiptinfo
	var strVia = "";
	if (isExistKey(objDic, FORM_NAME_VIA))
	{
		strVia = getValue(objDic, FORM_NAME_VIA);
	}
	createNsetNode("receiptinfo", "via", strVia);

// Add title Node To body
	var strTitle = getTitle(1);
	createNsetNode("body", "title", strTitle);
	if (strFormUsage == "2")
		setAttributeValue2Node("body", "separate", "true");
	else
		setAttributeValue2Node("body", "separate", "false");

/*
	var objAttachBody = getRelatedAttachListByClassify("AttachBody");
	if (objAttachBody.length > 0)
		setAttributeValue2Node("body", "separate", "true");
	else
		setAttributeValue2Node("body", "separate", "false");
	objAttachBody = null;
*/

// Add content Node To body
	createNsetNode("body", "content", "");

// Add sendername Node To foot
	var strSenderName = "";
	if (isExistKey(objDic, FORM_NAME_SENDERNAME))
	{
		strSenderName = getValue(objDic, FORM_NAME_SENDERNAME);
	}
	createNsetNode("foot", "sendername", strSenderName);

// Add seal Node To foot
	var objCompanyStamp = getRelatedAttachBySubDiv("CompanyStamp");
	if (objCompanyStamp != null)
	{
		var strFileName = getAttachFileName(objCompanyStamp);
		var strImageWidth = getAttachImageWidth(objCompanyStamp);
		var strImageHeight = getAttachImageHeight(objCompanyStamp);
		var strLocalFile = strDownloadPath + strFileName;
		var strPubDocFile = strPubDocPath + strFileName;
		bReturn = copyLocalFile(strLocalFile, strPubDocFile);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:Copy CompanyStamp from tempDir to PubDocDir\ncopyLocalFile(strLocalPath, strPubDocFile) Fail");
			return;
		}
// Add seal Node To foot
		createNsetNode("foot", "seal", "");
		setAttributeValue2Node("seal", "omit", "false");

// Add img Node To seal
		createNsetNode("seal", "img", "");
		setAttributeValue2Node("img", "src", strFileName);
		setAttributeValue2Node("img", "alt", strOrgan);
		setAttributeValue2Node("img", "width", strImageWidth);
		setAttributeValue2Node("img", "height", strImageHeight);
	}
	else
	{
		createNsetNode("foot", "seal", "");
		setAttributeValue2Node("seal", "omit", "true");
	}

// Add approvalinfo Node To foot
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
	{
		createNsetNode("foot", "approvalinfo", "");
		var objApprover = getFirstApprover(objApprovalLine);
		var nApproverSerialOrder = 0;
		while (objApprover != null)
		{
			var nApproverRole = parseInt(getApproverRole(objApprover));
			if (isApprover(nApproverRole))
			{
//				var nApproverSerialOrder = parseInt(getApproverSerialOrder(objApprover)) + 1;
				nApproverSerialOrder += 1;
				var strIsSigned = getApproverIsSigned(objApprover);
				var arrOption37 = g_strOption37.split("^");
				var strUserPosition = getUserPosition(objApprover, arrOption37[0]);
//				var strUserPosition = getApproverUserPosition(objApprover);
				var strApproverAdditionalRole = getApproverAdditionalRole(objApprover);
				if (strApproverAdditionalRole == "1")
					strUserPosition = "★" + strUserPosition;
				else if (strApproverAdditionalRole == "2")
					strUserPosition = "⊙" + strUserPosition;
				var strApproverRoleString = "";
//				if (nApproverRole == 0)
//					strApproverRoleString = ROLE_APPROVER;
//				else
					strApproverRoleString = getApproverRoleString(objApprover);
				var strApproverSignFileName = getApproverSignFileName(objApprover);
				var strApproverUserName = getApproverUserName(objApprover);
				var strApproverSignDate = getDateDisplay(getApproverSignDate(objApprover), "YYYY.MM.DD");
				var arrApproverSignTime = getApproverSignDate(objApprover).split(" ");
				var strApproverSignTime = arrApproverSignTime[1];
				var strOpinion = getApproverOpinion(objApprover);
				if (strIsSigned != "Y")
				{
					strApproverSignFileName = "";
					strApproverUserName = "";
					strApproverSignDate = "";
					arrApproverSignTime = "";
					strApproverSignTime = "";
					strOpinion = "";
				}

// Add approval Node To approvalinfo
				createNsetNode("approvalinfo", "approval", "");
				setAttributeValue2Node("approval", "order", nApproverSerialOrder);
//				if (strOpinion != "")
//					setAttributeValue2Node("approval", "opinion", "yes");

// Add signposition Node To approval
				if (strOpinion != "")
					strUserPosition = strUserPosition + "(의견있음)";
				createNsetNode("approval", "signposition", strUserPosition);
// Add type Node To approval

// Add EmptyReason substring 2003.10.31
				var strApproverEmptyReason = "";
				var nFind = strApproverRoleString.indexOf("(");
				if (nFind != -1)
				{
					strApproverEmptyReason = strApproverRoleString.substring(nFind+1, strApproverRoleString.length-1);
					strApproverRoleString = strApproverRoleString.substring(0, nFind);
				}
				createNsetNode("approval", "type", strApproverRoleString);
				if (strIsSigned == "Y")
				{
// Add signimage or name Node To approval
					if (strApproverSignFileName != "")
					{
						var strLocalFile = strDownloadPath + strApproverSignFileName;
						var strPubDocFile = strPubDocPath + strApproverSignFileName;
						bReturn = copyLocalFile(strLocalFile, strPubDocFile);
						if (bReturn == false)
						{
							alert(ALERT_ERROR_OCCUR + "\nError Type:Copy Approver SignImage from tempDir to PubDocDir\ncopyLocalFile(strLocalFile, strPubDocFile) Fail");
							return;
						}
						createNsetNode("approval", "signimage", "");
// Add img or name Node To signimage
						createNsetNode("signimage", "img", "");
						setAttributeValue2Node("img", "src", strApproverSignFileName);
						setAttributeValue2Node("img", "alt", strApproverUserName);
						setAttributeValue2Node("img", "width", "6mm");
						setAttributeValue2Node("img", "height", "6mm");
					}
// Woongyber 2003.10.29 DTD변경으로 <name> Tag가 REQUIRED되었다.
//					else
//					{
// 						if (strApproverUserName != "")
//						if (strApproverEmptyReason != "")
//							strApproverUserName = strApproverEmptyReason;
//						createNsetNode("approval", "name", strApproverUserName);
//					}
				}

// Woongyber 2003.10.31 <name> Tag를 REQUIRED로 항상 만든다.
				if (strApproverEmptyReason != "")
					strApproverUserName = strApproverEmptyReason;
				createNsetNode("approval", "name", strApproverUserName);

// Add date Node To approval
				createNsetNode("approval", "date", strApproverSignDate);
// Add time Node To approval
				if (strApproverSignTime != "")
					createNsetNode("approval", "time", strApproverSignTime);
			}
			objApprover = getNextApprover(objApprover);
		}
// set order value for final to final approval
		setAttributeValue2Node("approval", "order", "final");

		var objApprover = getFirstApprover(objApprovalLine);
		var bIsAssist = false;
		var nApproverSerialOrder = 0;
		while (objApprover != null)
		{
			var nApproverRole = parseInt(getApproverRole(objApprover));
			if (nApproverRole == 3)
			{
				bIsAssist = true;
//				var nApproverSerialOrder = parseInt(getApproverSerialOrder(objApprover)) + 1;
				nApproverSerialOrder += 1;
				var strIsSigned = getApproverIsSigned(objApprover);
				var arrOption37 = g_strOption37.split("^");
				var strUserPosition = getUserPosition(objApprover, arrOption37[0]);
//				var strUserPosition = getApproverUserPosition(objApprover);
				var strApproverAdditionalRole = getApproverAdditionalRole(objApprover);
				if (strApproverAdditionalRole == "1")
					strUserPosition = "★" + strUserPosition;
				var strApproverRoleString = getApproverRoleString(objApprover);
				var strApproverSignFileName = getApproverSignFileName(objApprover);
				var strApproverUserName = getApproverUserName(objApprover);
				var strApproverSignDate = getDateDisplay(getApproverSignDate(objApprover), "YYYY.MM.DD");
				var arrApproverSignTime = getApproverSignDate(objApprover).split(" ");
				var strApproverSignTime = arrApproverSignTime[1];
				var strOpinion = getApproverOpinion(objApprover);
				if (strIsSigned != "Y")
				{
					strApproverSignFileName = "";
					strApproverUserName = "";
					strApproverSignDate = "";
					arrApproverSignTime = "";
					strApproverSignTime = "";
					strOpinion = "";
				}

// Add assist Node To approvalinfo
				createNsetNode("approvalinfo", "assist", "");
				setAttributeValue2Node("assist", "order", nApproverSerialOrder);

// Add signposition Node To assist
				if (strOpinion != "")
					strUserPosition = strUserPosition + "(의견있음)";
				createNsetNode("assist", "signposition", strUserPosition);
// Add type Node To assist

// Add EmptyReason substring 2003.10.31
				var strApproverEmptyReason = "";
				var nFind = strApproverRoleString.indexOf("(");
				if (nFind != -1)
				{
					strApproverEmptyReason = strApproverRoleString.substring(nFind+1, strApproverRoleString.length-1);
					strApproverRoleString = strApproverRoleString.substring(0, nFind);
				}

				createNsetNode("assist", "type", strApproverRoleString);
				if (strIsSigned == "Y")
				{
// Add signimage or name Node To assist
					if (strApproverSignFileName != "")
					{
						var strLocalFile = strDownloadPath + strApproverSignFileName;
						var strPubDocFile = strPubDocPath + strApproverSignFileName;
						bReturn = copyLocalFile(strLocalFile, strPubDocFile);
						if (bReturn == false)
						{
							alert(ALERT_ERROR_OCCUR + "\nError Type:Copy Assisst SignImage from tempDir to PubDocDir\ncopyLocalFile(strLocalFile, strPubDocFile) Fail");
							return;
						}
						createNsetNode("assist", "signimage", "");
// Add img or name Node To signimage
						createNsetNode("signimage", "img", "");
						setAttributeValue2Node("img", "src", strApproverSignFileName);
						setAttributeValue2Node("img", "alt", strApproverUserName);
						setAttributeValue2Node("img", "width", "6mm");
						setAttributeValue2Node("img", "height", "6mm");
					}
					else
					{
						if (strApproverEmptyReason != "")
							strApproverUserName = strApproverEmptyReason;

						if (strApproverUserName != "")
							createNsetNode("assist", "name", strApproverUserName);
					}
				}
// Add date Node To assist
				createNsetNode("assist", "date", strApproverSignDate);
// Add time Node To assist
				if (strApproverSignTime != "")
					createNsetNode("assist", "time", strApproverSignTime);
			}
			objApprover = getNextApprover(objApprover);
		}
// set order value for final to final assist
		if (bIsAssist == true)
			setAttributeValue2Node("assist", "order", "final");
	}

// Add processinfo Node To foot
	createNsetNode("foot", "processinfo", "");

	var strDraftProcDeptName = getDraftProcDeptName();
	var strDraftProcDeptCode = getDraftProcDeptCode();
	var strSerialNumber = getSerialNumber();
	var strEnforceDate = getDateDisplay(getDelivererSignDateByType("1"), "YYYY.MM.DD");

// Add regnumber Node To processinfo
	createNsetNode("processinfo", "regnumber", strDraftProcDeptName + "-" + strSerialNumber);
	if (strSerialNumber.length < SERIAL_NUMBER_LENGTH)
	{
		var strTemp = strSerialNumber;
		for (var iLoop = 0; iLoop < SERIAL_NUMBER_LENGTH - strSerialNumber.length; ++iLoop)
		{
			strTemp = "0" + strTemp;
		}
		strSerialNumber = strTemp;
	}
	setAttributeValue2Node("regnumber", "regnumbercode", strDraftProcDeptCode + strSerialNumber);

// Add enforcedate Node To processinfo
	createNsetNode("processinfo", "enforcedate", strEnforceDate);

// Add sendinfo Node To foot
	createNsetNode("foot", "sendinfo", "");

// Add zipcode Node To sendinfo
	var strZipCode = "";
	if (isExistKey(objDic, FORM_NAME_ZIPCODE))
	{
		strZipCode = getValue(objDic, FORM_NAME_ZIPCODE);
	}
	createNsetNode("sendinfo", "zipcode", strZipCode);

// Add address Node To sendinfo
	var strAddress = "";
	if (isExistKey(objDic, FORM_NAME_ADDRESS))
	{
		strAddress = getValue(objDic, FORM_NAME_ADDRESS);
	}
	createNsetNode("sendinfo", "address", strAddress);

// Add homeurl Node To sendinfo
	if (isExistKey(objDic, FORM_NAME_HOMEURL))
	{
		var strHomeUrl = getValue(objDic, FORM_NAME_HOMEURL);
		if (strHomeUrl != "")
		{
// Modified by Woongyber 2003.10.30
			var strLowerCaseUrl = strHomeUrl;
			var strHTTP = "http://";

			strLowerCaseUrl = strLowerCaseUrl.toLowerCase();
			var nIndex = strLowerCaseUrl.indexOf(strHTTP);
			if (nIndex != -1)
			{
				nIndex += strHTTP.length;
				strHomeUrl = strHomeUrl.substring(nIndex, strHomeUrl.length);
			}

			createNsetNode("sendinfo", "homeurl", strHomeUrl);
		}
	}

// Add telephone Node To sendinfo
	var strTelephone = "";
/*
	if (isExistKey(objDic, FORM_NAME_TELPREFIX))
	{
		strTelephone = getValue(objDic, FORM_NAME_TELPREFIX);
		if (strTelephone != "")
		{
			strTelephone = "(" + strTelephone + ")";
		}
	}
*/
	if (isExistKey(objDic, FORM_NAME_TELEPHONE))
	{
		strTelephone = getValue(objDic, FORM_NAME_TELEPHONE);
	}
	createNsetNode("sendinfo", "telephone", strTelephone);

// Add fax Node To sendinfo
	var strFax = "";
/*
	if (isExistKey(objDic, FORM_NAME_FAXPREFIX))
	{
		strFax = getValue(objDic, FORM_NAME_FAXPREFIX);
		if (strFax != "")
		{
			strFax = "(" + strFax + ")";
		}
	}
*/
	if (isExistKey(objDic, FORM_NAME_FAX))
	{
		strFax = getValue(objDic, FORM_NAME_FAX);
	}
	createNsetNode("sendinfo", "fax", strFax);

// Add email Node To sendinfo
	if (isExistKey(objDic, FORM_NAME_EMAIL))
	{
		var strEmail = getValue(objDic, FORM_NAME_EMAIL);
		if (strEmail != "")
		{
			createNsetNode("sendinfo", "email", strEmail);
		}
	}

// Add publication Node To sendinfo
	var strCode = getPublicLevel();
	var strPublication = getPublication(strCode);
	createNsetNode("sendinfo", "publication", strPublication);
	setAttributeValue2Node("publication", "code", strCode);

// Add symbol Node To sendinfo
	var objSymbol = getRelatedAttachBySubDiv("Symbol");
	if (objSymbol != null)
	{
		var strFileName = getAttachFileName(objSymbol);
		var strLocalFile = strDownloadPath + strFileName;
		var strPubDocFile = strPubDocPath + strFileName;
		bReturn = copyLocalFile(strLocalFile, strPubDocFile);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:Copy Symbol from tempDir to PubDocDir\ncopyLocalFile(strLocalFile, strPubDocFile) Fail");
			return;
		}

// Add symbol Node To sendinfo
		createNsetNode("sendinfo", "symbol", "");

// Add img Node To symbol
		createNsetNode("symbol", "img", "");
		setAttributeValue2Node("img", "src", strFileName);
		setAttributeValue2Node("img", "alt", ALT_SYMBOL);
		setAttributeValue2Node("img", "width", "20");
		setAttributeValue2Node("img", "height", "20");
	}

// Add logo Node To sendinfo
	var objLogo = getRelatedAttachBySubDiv("Logo");
	if (objLogo != null)
	{
		var strFileName = getAttachFileName(objLogo);
		var strLocalFile = strDownloadPath + strFileName;
		var strPubDocFile = strPubDocPath + strFileName;
		bReturn = copyLocalFile(strLocalFile, strPubDocFile);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:Copy Logo from tempDir to PubDocDir\ncopyLocalFile(strLocalFile, strPubDocFile) Fail");
			return;
		}

// Add logo Node To sendinfo
		createNsetNode("sendinfo", "logo", "");

// Add img Node To symbol
		createNsetNode("logo", "img", "");
		setAttributeValue2Node("img", "src", strFileName);
		setAttributeValue2Node("img", "alt", ALT_LOGO);
		setAttributeValue2Node("img", "width", "20");
		setAttributeValue2Node("img", "height", "20");
	}

/*
		createNsetNode("sendinfo", "symbol", "");
		var strFileName = "symbol.bmp";
		var strLocalFile = strDownloadPath + strFileName;
		var strPubDocFile = strPubDocPath + strFileName;
		copyLocalFile(strLocalFile, strPubDocFile)
		createNsetNode("symbol", "img", "");
		setAttributeValue2Node("img", "src", strFileName);
		setAttributeValue2Node("img", "alt", ALT_SYMBOL);
*/

// Add campaign Node To foot
	var strHeadCampaign = "";
	if (isExistKey(objDic, FORM_NAME_HEADCAMPAIGN))
	{
		strHeadCampaign = getValue(objDic, FORM_NAME_HEADCAMPAIGN);
	}
	var strFootCampaign = "";
	if (isExistKey(objDic, FORM_NAME_FOOTCAMPAIGN))
	{
		strFootCampaign = getValue(objDic, FORM_NAME_FOOTCAMPAIGN);
	}
	if ((strHeadCampaign != "") || (strFootCampaign != ""))
	{
// Add campaign Node To foot
		createNsetNode("foot", "campaign", "");
		if (strHeadCampaign != "")
		{
// Add headcampaign Node To campaign
			createNsetNode("campaign", "headcampaign", strHeadCampaign);
		}
		if (strFootCampaign != "")
		{
// Add footcampaign Node To campaign
			createNsetNode("campaign", "footcampaign", strFootCampaign);
		}
	}

// Add Or Remove attach Node To pubdoc
	var objAttach = getFirstGeneralAttach();
	if (objAttach != null)
	{
// Add title Node To attach
		while (objAttach != null)
		{
			var strDisplayName = getAttachDisplayName(objAttach);
			var strFileName = getAttachFileName(objAttach);
	                strDisplayName = strDisplayName + getExtention(strFileName);
			createNsetNode("attach", "title", strDisplayName);
			objAttach = getNextGeneralAttach(objAttach);
		}
	}
	else
	{
// Remove attach Node From pubdoc
		removeNode("attach");
	}

	var strPubDocXMLPath = strPubDocPath + PUBDOC_XML_FILE;
	bReturn = savePubDocXML(strPubDocXMLPath, g_objPubDocDom.xml);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR + "\nError Type:Save PubDoc XML File\nsavePubDocXML(strPubDocXMLPath, g_objPubDocDom.xml) Fail");
		return;
	}

	var strOriginXML = "";

//	var strApprovalDoc = toXMLString(ApprovalDoc);
	var strSendType = "send";
	if (g_strEditType == "17")
	{
		strSendType = "resend";

		// 재발송시에 METHOD 가 keep인 대외 수신처만 남기는 Logic 추가
		strOriginXML = ApprovalDoc.documentElement.xml;

		var objRecipGroup = getFirstRecipGroup();
		while (objRecipGroup != null)
		{
			var objNextRecipGroup = getNextRecipGroup(objRecipGroup);

			var objRecipient = getFirstRecipient(objRecipGroup);
			while (objRecipient != null)
			{
				var objNextRecipient = getNextRecipient(objRecipient);

				if (getRecipientIsPubdocRecip(objRecipient) == "Y")
				{
//					if (getRecipientReceiptStatus(objRecipient) != "3")
					if (getRecipientMethod(objRecipient) != "keep")
						removeRecipient(objRecipient);
				}

				objRecipient = objNextRecipient;
			}

			if (getFirstRecipient(objRecipGroup) == null)
			{
				objRecipGroup.parentNode.removeChild(objRecipGroup)
			}

			objRecipGroup = objNextRecipGroup;
		}
		// 재발송시에 METHOD 가 keep인 대외 수신처만 남기는 Logic 끝
	}

	var strApprovalDoc = toXMLString(ApprovalDoc);

// Start PubDoc Converter
	var Converter = new ActiveXObject("PubDocConverter.Converter");
	Converter.FileFolder = strPubDocPath;
	Converter.GetPubPackAdjuncts(strPubDocPath);
	Converter.LoadApprovalDocXML(strApprovalDoc);
	g_strPackFile = Converter.DoPackPubPack(1, strSendType, PUBDOC_XML_FILE, BODY_XML_FILE);

	if (g_strEditType == "17" && strOriginXML != "")
	{
		ApprovalDoc.loadXML(strOriginXML);
	}

	if (g_strPackFile == "FAIL")
	{
//		alert(ALERT_ERROR_OCCUR + "\nError Type:\nConverter.DoPackPubPack(1, strSendType, PUBDOC_XML_FILE, BODY_XML_FILE) Fail");
		return;
	}
	else if (g_strPackFile == "CANCEL")
	{
//		alert(ALERT_ERROR_OCCUR + "\nError Type:\nConverter.DoPackPubPack(1, strSendType, PUBDOC_XML_FILE, BODY_XML_FILE) Fail");
		return;
	}

/*
	var arrPackFileList = strPackFileList.split("^");
	for (var iLoop = 0; iLoop < arrPackFileList.length-1; ++iLoop)
	{
		var strFileName = arrPackFileList[iLoop];
		var strLocalPath = strPubDocPath + strFileName;
		var strServerUrl = g_strPubDocUploadUrl + strFileName;
		bReturn = uploadFile(strLocalPath, strServerUrl);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:\nuploadFile(strLocalPath, strServerUrl) Fail");
			return;
		}
	}
*/
	callFlow();
}

// 공문서 전송방식 수정전
function uploadPackFiles()
{
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;
	var arrPackFileList = g_strPackFile.split("^");
	for (var iLoop = 0; iLoop < arrPackFileList.length-1; ++iLoop)
	{
		var strFileName = arrPackFileList[iLoop];
		var strLocalPath = strPubDocPath + strFileName;
		var strServerUrl = g_strPubDocUploadUrl + strFileName;
		var bReturn = uploadFile(strLocalPath, strServerUrl);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:Upload Pack Files\nuploadFile(strLocalPath, strServerUrl) Fail");
			return;
		}
	}
}
/*
// 공문서 전송방식 수정후 (관련파일 - 3.0.0.5용 OCX, cn_FileTransfer.js)
function uploadPackFiles()
{
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;
	var arrPackFileList = g_strPackFile.split("^");
	for (var iLoop = 0; iLoop < arrPackFileList.length-1; ++iLoop)
	{
		var strFileName = arrPackFileList[iLoop];
		var strLocalPath = strPubDocPath + strFileName;
		var strServerUrl = g_strPubDocUploadUrl + strFileName;
//		var bReturn = uploadFile(strLocalPath, strServerUrl);
		var bReturn = uploadFile(strLocalPath, strServerUrl + ".tmp");
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:Upload Pack Files\nuploadFile(strLocalPath, strServerUrl) Fail");
			return;
		}

		bReturn = renameServerFile(strServerUrl + ".tmp", strServerUrl);
		if (bReturn == false)
		{
			alert("중계서버 전송파일 이름 변경에 실패하였습니다.");
			return;
		}
	}
}
*/
function isApprover(nApproverRole)
{
//0:1인결재/1:기안자/2:검토자/3:협조자//5:결재자/6:전결자/7:대결자

	if (nApproverRole == APPROVER_ROLE_0)
		return true;
	else if (nApproverRole == APPROVER_ROLE_1)
		return true;
	else if (nApproverRole == APPROVER_ROLE_2)
		return true;
//	else if (nApproverRole == APPROVER_ROLE_3)
//		return true;
	else if (nApproverRole == APPROVER_ROLE_5)
		return true;
	else if (nApproverRole == APPROVER_ROLE_6)
		return true;
	else if (nApproverRole == APPROVER_ROLE_7)
		return true;
	else
		return false;
}

function callFlow()
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

function getRecipientList()
{
	var strReturn = "";
	var objRecipientGroup = getRecipGroup(1);
//	var objRecipient = getFirstRecipient(objRecipientGroup);
	if (objRecipientGroup != null)
	{
		var nRecipCount = getRecipientCount(objRecipientGroup);
		if (nRecipCount == 1)
		{
			strReturn = getFieldText(FORM_NAME_RECIPIENT);
			var strTemp = getFieldText(FORM_NAME_RECIPIENT_REF);
			if (strTemp != "")
				strReturn = strReturn + "(" + strTemp + ")";
		}
		else
		{
			strReturn = getFieldText(FORM_NAME_RECIPIENT_REFER);
		}
	}
	return strReturn;

/*
	while (objRecipient != null)
	{
		var strRecipientDeptName = getRecipientDeptChief(objRecipient);
		var strRecipientRefDeptChief = getRecipientRefDeptChief(objRecipient);
		var strTemp = strRecipientDeptName;
		if (strRecipientRefDeptChief != "")
		{
			strTemp = strTemp + "(" + strRecipientRefDeptChief + ")";
		}
		if (strReturn != "")
		{
			strTemp = ", " + strTemp;
		}
		strReturn = strReturn + strTemp;
		objRecipient = getNextRecipient(objRecipient);
	}
	return strReturn;
*/
}

function getRecipientRefAttribute()
{
	var strReturn = "false";
	var objRecipientGroup = getRecipGroup(1);
	if (objRecipientGroup != null)
	{
		var objRecipient = getFirstRecipient(objRecipientGroup);
		var nCount = 0;
		while (objRecipient != null)
		{
			++nCount;
			objRecipient = getNextRecipient(objRecipient);
		}
		if (nCount > 1)
			strReturn = "true";
	}
	return strReturn;
}
/*
function getPublication(strCode)
{
	var strReturn = "";
	var strSubString = strCode.substr(0, 1);
	var strPublic = "";
	if (strSubString == "1")
	{
		strReturn = PUBLICATION_1;
	}
	else if (strSubString == "2")
	{
		strReturn = PUBLICATION_2;
	}
	if (strSubString == "3")
	{
		strReturn = PUBLICATION_3;
	}

	if (strSubString != "1")
	{
		var strLevel = "";
		for (var iLoop = 1; iLoop < strCode.length; ++iLoop)
		{
			strSubString = strCode.substr(iLoop, 1);
			if (strSubString.toUpperCase() == "Y")
			{
				var strTempLevel = iLoop;
				if (strLevel != "")
				{
					strTempLevel = ", " + strTempLevel
				}
				strLevel = strLevel + strTempLevel
			}
		}
		if (strLevel != "")
		{
			strReturn = strReturn + "(" + strLevel + ")";
		}
	}
	return strReturn;
}
*/
function createNsetNode(strParentNodeName, strNodeName, strText)
{
	var objChildNode = createChildNode(strParentNodeName, strNodeName);
	setText2Node(objChildNode, strText);
	objChildNode= null;
}

function getNodeByName(strNodeName)
{
	var objNodes = g_objPubDocDom.selectNodes("*//" + strNodeName);
	var objNode = objNodes.item(objNodes.length-1);
	return objNode;
}

function createChildNode(strParentNodeName, strNodeName)
{
	var objParetnNode = getNodeByName(strParentNodeName);
	var objNewNode = objParetnNode.ownerDocument.createNode(1, strNodeName, "");
	objParetnNode.appendChild(objNewNode);
	objParentNode = null;
	return objNewNode;
}

function removeNode(strNodeName)
{
	var objNode = getNodeByName(strNodeName);
	if (objNode != null)
		objNode.parentNode.removeChild(objNode);
}

function setText2Node(objNode, strText)
{
	objNode.text = strText;
}

function setAttributeValue2Node(strNodeName, strAttributeName, strValue)
{
	var objNode = getNodeByName(strNodeName);
	objNode.setAttribute(strAttributeName, strValue);
	objNode = null;
}

function getExtention(strFile)
{
	var nPos = strFile.lastIndexOf(".");
	return strFile.substring(nPos);
}

function getPubDocDom()
{
	var strXML = "";
	strXML = strXML + "<pubdoc>\r\n";
	strXML = strXML + "<head>\r\n";
	strXML = strXML + "</head>\r\n";
	strXML = strXML + "<body>\r\n";
	strXML = strXML + "</body>\r\n";
	strXML = strXML + "<foot>\r\n";
	strXML = strXML + "</foot>\r\n";
	strXML = strXML + "<attach>\r\n";
	strXML = strXML + "</attach>\r\n";
	strXML = strXML + "</pubdoc>\r\n";

	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	objXMLDom.loadXML(strXML);

	return objXMLDom;
}

function savePubDocXML(strLocalPath, strXML)
{
	var bReturn = createLocalFile(strLocalPath);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR + "\nError Type:\ncreateLocalFile(strLocalPath) Fail");
	}
	else
	{
		bReturn = saveToLocalFile(strLocalPath, strXML);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR + "\nError Type:\nsaveToLocalFile(strLocalPath, strXML) Fail");
		}
	}
	return bReturn;
}

function saveBlankBodyToXML(strBodyXMLPath)
{
	var bReturn = createLocalFile(strBodyXMLPath);
	if (bReturn)
		bReturn = saveToLocalFile(strBodyXMLPath, "");
	return bReturn;
}

function getValue(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return "";
	else
		return arr[key];
}

function isExistKey(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return false;
	else
		return true;
}
