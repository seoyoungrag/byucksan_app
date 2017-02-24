var g_objTextDic = new Array();
var g_objImageDic = new Array();

function unPack()
{
	var strUploadUrl = getUploadUrl();
	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;

	deleteDownloadPath(strPubDocPath);
	createDownloadPath(strPubDocPath);

// Get PubPack File
	var objPubPackFile = getExtendAttachBySubDiv("", "PubPack");
	if (objPubPackFile == null)
	{
		alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Get PubPack Extend Attach\ngetExtendAttachBySubDiv('1', 'PubPack') Fail");
		changeMenu(2);
		return;
	}
	var strPackFile = getAttachFileName(objPubPackFile);
	var strServerUrl = strUploadUrl + strPackFile;
	var strLocalPath = strPubDocPath + strPackFile;
	var bReturn = downloadFile(strServerUrl, strLocalPath);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:PubPack File Download To PubDocDir\ndownloadFile(strServerUrl, strLocalPath) Fail");
		changeMenu(2);
		return;
	}
	objPubPackFile = null;
// Start PubDoc Converter
	var Converter = new ActiveXObject("PubDocConverter.Converter");
	Converter.FileFolder = strPubDocPath;
	Converter.GetPubPackAdjuncts(strPubDocPath);
	Converter.GetPubDocAdjuncts(strPubDocPath);
	Converter.LoadPubPackDocFile(strPackFile);
	var bReturn = Converter.DoUnpackPubPack();
	if (bReturn == "FAIL")
	{
//		alert("Converter.DoUnpackPubPack() Fail");
		changeMenu(2);
		return false;
	}
	else if (bReturn == "CANCEL")
	{
//		alert("Converter.DoUnpackPubPack() Fail");
		return false;
	}
	else if (bReturn.indexOf("CERTFAIL^") != -1)
	{
		var arrReturn = bReturn.split("^");
		bReturn = confirm("복호화에 실패하였습니다. 재전송을 요청하고 문서를 삭제하시겠습니까?");
		if (bReturn)
		{
			showWaitDlg(MESSAGE_WAIT, MESSAGE_WAIT_MOMENT);
			setAck(4, arrReturn[1]);
			deletePubDoc();
			changeMenu(2);
		}
		return;
	}

// Load PubDoc XML File
	var strPubDocFile = strPubDocPath + Converter.PubDocFileName;
//	var strPubDocFile = strPubDocPath + UNPACK_PUBDOC_FILE;
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	// Woongyber 2004.01.08 일부 필드 누락시에도 문서가 열리도록 수정
	objXMLDom.validateOnParse = false;
	bReturn = objXMLDom.load(strPubDocFile);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Load XML From PubDocFile\n" + objXMLDom.parseError.reason + "\nError Line:" + objXMLDom.parseError.line + "\nError Src:" + objXMLDom.parseError.srcText);
		changeMenu(2);
		bReturn = confirm("공문서 데이터의 오류가 치명적이어서 접수나 반송 처리가 불가능합니다. 재전송을 요청하고 문서를 삭제하시겠습니까?");
		if (bReturn == true)
		{
			showWaitDlg(MESSAGE_WAIT, MESSAGE_WAIT_MOMENT);
			setAck(4, objXMLDom.parseError.reason + "\nError Line:" + objXMLDom.parseError.line + "\nError Src:" + objXMLDom.parseError.srcText);
			deletePubDoc();
		}
		return;
	}

// Set XML/XSL Body File
	var strExchXMLFileName = Converter.ExchXMLFileName;
	var strExchXSLFileName = Converter.ExchXSLFileName;

	if ((strExchXMLFileName != "") && (strExchXSLFileName != ""))
	{
		var strDisplayName = getDisplayName(strExchXMLFileName);
		var strExtention = getExtention(strExchXMLFileName);
//		var strGuid = getGUID();
//		var strFileName = strGuid + strExtention;
		var strFileName = strDisplayName + strExtention;
		var strFileSize = getLocalFileSize(strPubDocPath + strExchXMLFileName);
		bReturn = copyLocalFile(strPubDocPath + strExchXMLFileName, strDownloadPath + strFileName);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Copy XML Body From PubDocDir To TempDir\ncopyLocalFile(strPubDocPath + strExchXMLFileName, strDownloadPath + strFileName) Fail");
//			changeMenu(2);
//			return false;
		}

		bReturn = setRelatedAttach(strDisplayName, strFileName, strFileSize, "AttachBody", "BodyXML");
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set XML Body Info To Related Attach\nsetRelatedAttach(strExchXMLFileName, strFileName, strFileSize, 'AttachBody', 'BodyXML') Fail");
//			changeMenu(2);
//			return;
		}

		bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Upload XML Body To UploadUrl\nuploadFile(strLocalPath, strServerUrl) Fail");
//			changeMenu(2);
//			return;
		}

		var strDisplayName = getDisplayName(strExchXSLFileName);
		var strExtention = getExtention(strExchXSLFileName);
//		var strGuid = getGUID();
//		var strFileName = strGuid + strExtention;
		var strFileName = strDisplayName + strExtention;
		var strFileSize = getLocalFileSize(strPubDocPath + strExchXSLFileName);
		bReturn = copyLocalFile(strPubDocPath + strExchXSLFileName, strDownloadPath + strFileName);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Copy XSL Body From PubDocDir To TempDir\ncopyLocalFile(strPubDocPath + strExchXSLFileName, strDownloadPath + strFileName) Fail");
//			changeMenu(2);
//			return false;
		}

		bReturn = setRelatedAttach(strDisplayName, strFileName, strFileSize, "AttachBody", "BodyXSL");
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set XSL Body Info To Related Attach\nsetRelatedAttach(strExchXSLFileName, strFileName, strFileSize, 'AttachBody', 'BodyXSL') Fail");
//			changeMenu(2);
//			return;
		}

		bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Upload XSL Body To UploadUrl\nuploadFile(strLocalPath, strServerUrl) Fail");
//			changeMenu(2);
//			return;
		}
	}

// Get body Node From pubdoc
	var objBody = getXMLNode(objXMLDom, "*//body");
	var strSeparate = getAttributeFromNode(objBody, "separate");
	if (strSeparate == "true")
		setFormUsage("2");
	else
		setFormUsage("1");

	var objPubPackDocument = Converter.PubPackDocument;
	if (strSeparate == "true")
	{
// Set RelatedAttachBody
		var strAttachBody = objPubPackDocument.AttachBodyFileName;
		if (strAttachBody != "")
		{
			var arrAttachBody = strAttachBody.split("^");
			for (var iAttachBody = 0; iAttachBody < arrAttachBody.length-1; ++iAttachBody)
			{
				var strDisplayName = getDisplayName(arrAttachBody[iAttachBody]);
				var strExtention = getExtention(arrAttachBody[iAttachBody]);
				var strGuid = getGUID();
				var strFileName = strGuid + strExtention;
				var strFileSize = getLocalFileSize(strPubDocPath + arrAttachBody[iAttachBody]);
				bReturn = copyLocalFile(strPubDocPath + arrAttachBody[iAttachBody], strDownloadPath + strFileName);
				if (bReturn == false)
				{
					alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Copy Attach Body From PubDocDir To TempDir\ncopyLocalFile(strPubDocPath + arrAttachBody[iAttachBody], strDownloadPath + strFileName) Fail");
//					changeMenu(2);
//					return false;
				}

				bReturn = setRelatedAttach(strDisplayName, strFileName, strFileSize, "AttachBody", "");
				if (bReturn == false)
				{
					alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Attach Body Info To Related Attach\nsetRelatedAttach(arrAttachBody[iAttachBody], strFileName, strFileSize, 'AttachBody', '') Fail");
//					changeMenu(2);
//					return;
				}

				bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
				if (bReturn == false)
				{
					alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Upload Attach Body To UploadUrl\nuploadFile(strLocalPath, strServerUrl) Fail");
//					changeMenu(2);
//					return;
				}
			}
		}
	}

//	if (strSeparate == "false")
//	{
// Create Body XML File
		var strBodyXMLPath = strPubDocPath + BODY_XML_FILE;
		bReturn = createLocalFile(strBodyXMLPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Create XML Body File To PubDocDir\ncreateLocalFile(strBodyXMLPath) Fail");
			changeMenu(2);
			return;
		}

// Read Body XML Data
		var objContentNode = getXMLNode(objXMLDom, "//content");
		var strContent = objContentNode.xml;

		strContent = convContentString(strContent);
		objContentNode = null;


// Save Body XML to Local File
		bReturn = saveToLocalFile(strBodyXMLPath, strContent);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Save Content To XML Body File\nsaveToLocalFile(strBodyXMLPath, strContent) Fail");
			changeMenu(2);
			return;
		}

/*
// Read Body From XML
		bReturn = readBodyFormXML(strBodyXMLPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Read Body Content From XML Body File\nreadBodyFormXML(strBodyXMLPath) Fail");
			changeMenu(2);
			return;
		}
*/
//	}

/*
	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();
	strLocalPath = g_strDownloadPath + getAttachFileName(objBodyFile);
	saveApproveDocumentAs(strLocalPath, false);
	setModified(true);
*/

//	var objTextDic = new Array();
//	var objImageDic = new Array();

// Get organ Node From pubdoc
	var objOrgan = getXMLNode(objXMLDom, "*//organ");
	var strOrgan = getTextFromNode(objOrgan);
	setDic(g_objTextDic, FORM_NAME_ORGAN, strOrgan);
	objOrgan = null;

// Get receiptinfo Node From pubdoc
	var objReceiptInfo = getXMLNode(objXMLDom, "*//receiptinfo");
// Get recipient Node From receiptinfo
	var objRecipient = getXMLNode(objReceiptInfo, "recipient");
	var strRefer = getAttributeFromNode(objRecipient, "refer");
// Get rec Node From recipient
	var objRec = getXMLNode(objRecipient, "rec");
	var strRec = getTextFromNode(objRec);
	if (strRefer == "true")
	{
		setDic(g_objTextDic, FORM_NAME_RECIPIENT, FORM_VALUE_RECIPIENT_REFER);
		setDic(g_objTextDic, FORM_NAME_RECIPIENT_REFER, strRec);
	}
	else
	{
		setDic(g_objTextDic, FORM_NAME_RECIPIENT, strRec);
	}
	objRec = null;
	objRecipient = null;

// Get via Node From receiptinfo
	var objVia = getXMLNode(objReceiptInfo, "via");
	if (objVia != null)
	{
		var strVia = getTextFromNode(objVia);
		setDic(g_objTextDic, FORM_NAME_VIA, strVia);
	}
	objVia = null;
	objReceiptInfo = null;

// Get title Node From pubdoc
	var objTitle = getXMLNode(objXMLDom, "*//title");
	var strTitle = getTextFromNode(objTitle);
	setTitle(strTitle, 1);
	setDic(g_objTextDic, FORM_NAME_TITLE, strTitle);
	objTitle = null;

// Get sendername Node From pubdoc
	var objSenderName = getXMLNode(objXMLDom, "*//sendername");
	var strSenderName = getTextFromNode(objSenderName);
	setSender(strSenderName);
	setDic(g_objTextDic, FORM_NAME_SENDERNAME, strSenderName);
	setSenderAs(strSenderName, 1);						// 20031021 SENDER_AS
	objSenderName = null;

// Get seal Node From pubdoc
	var objSeal = getXMLNode(objXMLDom, "*//seal");
	var strOmit = getAttributeFromNode(objSeal, "omit");
	if (strOmit == "false")
	{
// Get img Node From seal
		var objImg = getXMLNode(objSeal, "img");
		var strSrc = getAttributeFromNode(objImg, "src");
		var strImageWidth = getAttributeFromNode(objImg, "width");
		var strImageHeight = getAttributeFromNode(objImg, "height");
		var strExtention = getExtention(strSrc);
		var strGuid = getGUID();
		var strFileName = strGuid + strExtention;
		var strFileSize = getLocalFileSize(strPubDocPath + strSrc);
		bReturn = copyLocalFile(strPubDocPath + strSrc, strDownloadPath + strFileName);
//		if (bReturn == false)
//		{
//			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Copy Seal Image To Local TempDir From PubDocDir\ncopyLocalFile(strPubDocPath + strSrc, strDownloadPath + strFileName) Fail");
//			changeMenu(2);
//			return false;
//		}

		bReturn = setRelatedAttach(strFileName, strFileName, strFileSize, "DocStamp", "CompanyStamp");
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Related Attach Info by Classify DocStamp and SubDiv CompanyStamp\nsetRelatedAttach(strFileName, strFileName, strFileSize, 'DocStamp', 'CompanyStamp') Fail");
//			changeMenu(2);
//			return;
		}
		else
		{
			var objCompanyStamp = getRelatedAttachBySubDiv("CompanyStamp");
			setAttachImageWidth(objCompanyStamp, parseInt(strImageWidth));
			setAttachImageHeight(objCompanyStamp, parseInt(strImageHeight));
		}

		bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
//		if (bReturn == false)
//		{
//			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Upload Seal Image To UploadUrl\nuploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName) Fail");
//			changeMenu(2);
//			return;
//		}

		setDic(g_objImageDic, FORM_NAME_COMPANYSTAMP, strDownloadPath + strFileName);
		objImg = null;
	}
	else
	{
		var objStampAttach = addRelatedAttachInfo(STAMP_COMPANY_OMITSTAMP, "", "", "");
		if (objStampAttach == null)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Related Attach Info by Classify DocStamp and SubDiv CompanyOmitStamp\naddRelatedAttachInfo(STAMP_COMPANY_OMITSTAMP, '', '', '') Fail");
//			changeMenu(2);
//			return;
		}
		setAttachClassify(objStampAttach, "DocStamp");
		setAttachSubDiv(objStampAttach, "CompanyOmitStamp");

		setDic(g_objTextDic, FORM_NAME_OMITCOMPANYSTAMP, FORM_NAME_OMITCOMPANYSTAMP);
	}
	objSeal = null;

	var objActiveApprovalLine = getActiveApprovalLine();
	if (objActiveApprovalLine == null)
		objActiveApprovalLine = addApprovalLine("N", "0", "20", "0", "0", "0");	// DOC_STATUS "20"으로 수정
//		objActiveApprovalLine = addApprovalLine("N", "0", "0", "0", "0", "0");	// DOC_STATUS "20"으로 수정
//		objActiveApprovalLine = addApprovalLine("Y", "0", "0", "0", "0", "0");	// Active가 되면 안되기때문에 수정


// Get approvalinfo Node From pubdoc
	var objApprovalInfo = getXMLNode(objXMLDom, "*//approvalinfo");
	bReturn = setApprovalInfoToApprovalDoc(objApprovalInfo, objActiveApprovalLine);
	if (bReturn == false)
	{
//		changeMenu(2);
//		return;
	}

// Set ApprovalLine Info								// 20031021
	bReturn = setApprovalInfo4Integrated(objApprovalInfo, objActiveApprovalLine);
	if (bReturn == false)
	{
//		changeMenu(2);
//		return;
	}

// Get processinfo Node From pubdoc
	var objProcessInfo = getXMLNode(objXMLDom, "*//processinfo");

// Get regnumber Node From processinfo
	var objRegNumber = getXMLNode(objProcessInfo, "regnumber");
	var strRegNumber = getTextFromNode(objRegNumber);
	var arrRegNumber = strRegNumber.split("-");
	var strDraftProcDeptName = arrRegNumber[0];
	var strSerialNumber = arrRegNumber[1];
	setDraftProcDeptName(strDraftProcDeptName);
	setSerialNumber(strSerialNumber);
	setDic(g_objTextDic, FORM_NAME_REGNUMBER, strRegNumber);

	var strRegNumberCode = getAttributeFromNode(objRegNumber, "regnumbercode");
	var strDraftProcDeptCode = strRegNumberCode;
	if (strSerialNumber != "")
	{
		var nPos = strRegNumberCode.lastIndexOf(strSerialNumber);
		strDraftProcDeptCode = strRegNumberCode.substr(0, nPos);
	}
//	setDraftProcDeptCode(strDraftProcDeptCode);
	setSerialSeed(strDraftProcDeptCode);						// 20031021 SERIAL_SEED
	objRegNumber = null;


// Get enforcedate Node From processinfo
	var objEnforceDate = getXMLNode(objProcessInfo, "enforcedate");
	var strEnforceDate = getTextFromNode(objEnforceDate);
	strEnforceDate = strEnforceDate.replace(/\./g, "-");

	if (strEnforceDate != "")							// 20031021 SEND_DATE
		setSendDate(strEnforceDate);

	if (strEnforceDate != "")
		strEnforceDate = getDateDisplay(strEnforceDate, g_strOption36);

//	if (strEnforceDate != "")
//		strEnforceDate = "(" + strEnforceDate + ")";

	setDic(g_objTextDic, FORM_NAME_ENFORCEDATE, strEnforceDate);
	objEnforceDate = null;

// Get receipt Node From processinfo
	var objReceipt = getXMLNode(objProcessInfo, "receipt");
	if (objReceipt != null)
	{
// Get number Node From receipt
		var objNumber = getXMLNode(objReceipt, "number");
		var strNumber = getTextFromNode(objNumber);
		setDic(g_objTextDic, FORM_NAME_NUMBER, strNumber);

// Get date Node From receipt
		var objDate = getXMLNode(objReceipt, "date");
		var strDate = getTextFromNode(objDate);

// Get time Node From receipt
		var objTime = getXMLNode(objReceipt, "time");
		if (objTime != null)
		{
			var strTime = getTextFromNode(objTime);
			strDate = strDate + " " + strTime;
		}
		objTime = null;
		objDate = null;
		setDic(g_objTextDic, FORM_NAME_DATE, strDate);
	}
	objReceipt = null;
	objProcessInfo = null;

// Get sendinfo Node From pubdoc
	var objSendInfo = getXMLNode(objXMLDom, "*//sendinfo");

// Get zipcode Node From sendinfo
	var objZipcode = getXMLNode(objSendInfo, "zipcode");
	var strZipcode = getTextFromNode(objZipcode);
	setDic(g_objTextDic, FORM_NAME_ZIPCODE, strZipcode);
	objZipcode = null;

// Get address Node From sendinfo
	var objAddress = getXMLNode(objSendInfo, "address");
	var strAddress = getTextFromNode(objAddress);
	setDic(g_objTextDic, FORM_NAME_ADDRESS, strAddress);
	objAddress = null;

// Get homeurl Node From sendinfo
	var objHomeurl = getXMLNode(objSendInfo, "homeurl");
	if (objHomeurl != null)
	{
		var strHomeurl = getTextFromNode(objHomeurl);
		setDic(g_objTextDic, FORM_NAME_HOMEURL, strHomeurl);
	}
	objHomeurl = null;

// Get telephone Node From sendinfo
	var objTelephone = getXMLNode(objSendInfo, "telephone");
	var strTelephone = getTextFromNode(objTelephone);
/*
	var arrTelephone = strTelephone.split(")");
	var strTelePrefix = arrTelephone[0].substring(1);
	setDic(g_objTextDic, FORM_NAME_TELPREFIX, strTelePrefix);
	setDic(g_objTextDic, FORM_NAME_TELEPHONE, arrTelephone[1]);
*/
	setDic(g_objTextDic, FORM_NAME_TELEPHONE, strTelephone);
	objTelephone = null;

// Get fax Node From sendinfo
	var objFax = getXMLNode(objSendInfo, "fax");
	var strFax = getTextFromNode(objFax);
/*
	var arrFax = strFax.split(")");
	var strFaxPrefix = arrFax[0].substring(1);
	setDic(g_objTextDic, FORM_NAME_FAXPREFIX, strFaxPrefix);
	setDic(g_objTextDic, FORM_NAME_FAX, arrFax[1]);
*/
	setDic(g_objTextDic, FORM_NAME_FAX, strFax);
	objFax = null;

// Get email Node From sendinfo
	var objEmail = getXMLNode(objSendInfo, "email");
	if (objEmail != null)
	{
		var strEmail = getTextFromNode(objEmail);
		setDic(g_objTextDic, FORM_NAME_EMAIL, strEmail);
	}
	objEmail = null;

// Get publication Node From sendinfo
	var objPublication = getXMLNode(objSendInfo, "publication");
	var strPublication = getTextFromNode(objPublication);
	var strCode = getAttributeFromNode(objPublication, "code");
	setPublicLevel(strCode);
	setDic(g_objTextDic, FORM_NAME_PUBLICATION, getPublication(strCode));
	objPublication  = null;

// Get symbol Node From sendinfo
	var objSymbol = getXMLNode(objSendInfo, "symbol");
	if (objSymbol != null)
	{
// Get img Node From symbol
		var objImg = getXMLNode(objSymbol, "img");
		var strSrc = getAttributeFromNode(objImg, "src");
		var strFileSize = getLocalFileSize(strPubDocPath + strSrc);

		if (strFileSize != "0")
		{
			bReturn = copyLocalFile(strPubDocPath + strSrc, strDownloadPath + strSrc);
			if (bReturn == true)
			{
				bReturn = uploadFile(strDownloadPath + strSrc, strUploadUrl + strSrc);
				if (bReturn == false)
				{
					alert(ALERT_ERROR_OCCUR_NOT_EXIST_SYMBOL);
		//			changeMenu(2);
		//			return;
				}
			}
			else
			{
				alert(ALERT_ERROR_OCCUR_NOT_EXIST_SYMBOL);
	//			changeMenu(2);
	//			return false;
			}

			bReturn = setRelatedAttach(strSrc, strSrc, strFileSize, "OrganImage", "Symbol");
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Related Attach Info by Classify OrganImage and SubDiv Symbol\nsetRelatedAttach(strSrc, strSrc, strFileSize, 'OrganImage', 'Symbol') Fail");
	//			changeMenu(2);
	//			return;
			}


			setDic(g_objImageDic, FORM_NAME_SYMBOL, strDownloadPath + strSrc);
			objImg = null;
		}
	}
	objSymbol = null;

// Get logo Node From sendinfo
	var objLogo = getXMLNode(objSendInfo, "logo");
	if (objLogo != null)
	{
// Get img Node From logo
		var objImg = getXMLNode(objLogo, "img");
		var strSrc = getAttributeFromNode(objImg, "src");
		var strFileSize = getLocalFileSize(strPubDocPath + strSrc);

		if (strFileSize != "0")
		{
			bReturn = copyLocalFile(strPubDocPath + strSrc, strDownloadPath + strSrc);
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Copy Logo Image To Local TempDir From PubDocDir\ncopyLocalFile(strPubDocPath + strSrc, strDownloadPath + strSrc) Fail");
//				changeMenu(2);
//				return false;
			}

			bReturn = setRelatedAttach(strSrc, strSrc, strFileSize, "OrganImage", "Logo");
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Related Attach Info by Classify OrganImage and SubDiv Logo\nsetRelatedAttach(strSrc, strSrc, strFileSize, 'OrganImage', 'Logo') Fail");
//				changeMenu(2);
//				return;
			}

			bReturn = uploadFile(strDownloadPath + strSrc, strUploadUrl + strSrc);
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Upload Logo Image To UploadUrl\nuploadFile(strDownloadPath + strSrc, strUploadUrl + strSrc) Fail");
//				changeMenu(2);
//				return;
			}

			setDic(g_objImageDic, FORM_NAME_LOGO, strDownloadPath + strSrc);
			objImg = null;
		}
	}
	objLogo = null;

// Get campaign Node From pubdoc
	var objCampaign = getXMLNode(objXMLDom, "*//campaign");
	if (objCampaign != null)
	{
// Get headcampaign Node From campaign
		var objHeadCampaign = getXMLNode(objCampaign, "headcampaign");
		if (objHeadCampaign != null)
		{
			var strHeadCampaign = getTextFromNode(objHeadCampaign);
			setDic(g_objTextDic, FORM_NAME_HEADCAMPAIGN, strHeadCampaign);
		}
		objHeadCampaign = null;

// Get footcampaign Node From campaign
		var objFootCampaign = getXMLNode(objCampaign, "footcampaign");
		if (objFootCampaign != null)
		{
			var strFootCampaign = getTextFromNode(objFootCampaign);
			setDic(g_objTextDic, FORM_NAME_FOOTCAMPAIGN, strFootCampaign);
		}
		objFootCampaign = null;
	}

// Get attach Node From pubdoc
//	var objAttach = getXMLNode(objXMLDom, "*//attach");
//	if (objAttach != null)
//	{
//		bReturn = setAttachInfoToDoc(objAttach);
//		if (bReturn == false)
//		{
//			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set General Attach Info\nsetAttachInfoToDoc(objAttach) Fail");
//			changeMenu(2);
//			return;
//		}
//	}

	var strAttachFileList = objPubPackDocument.AttachFileNames;
	if (strAttachFileList != "")
	{
		bReturn = setAttachInfoToDoc(strAttachFileList);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set General Attach Info\nsetAttachInfoToDoc(strAttachFileList) Fail");
//			changeMenu(2);
//			return;
		}
	}
	onChangeReceiveForm();
}

function setDocumentBody()
{
	var strFormUsage = getFormUsage();
// Modified by Jack 2003/03/26 : 전자기안문 표제부 본문 추출
//	if (strFormUsage != "1")
//	{
// Read Body From XML
		var strDownloadPath = getDownloadPath();
		var strPubDocPath = strDownloadPath + PUBDOC_PATH;
		var strBodyXMLPath = strPubDocPath + BODY_XML_FILE;
		var bReturn = readBodyFormXML(strBodyXMLPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Read Body Content From XML Body File\nreadBodyFormXML(strBodyXMLPath) Fail");
			changeMenu(2);
			return false;
		}
//	}
	return true;
}

function setDocumentField()
{
// Document Setting
	bReturn = setFieldsText(g_objTextDic);
	if (bReturn == false)
	{
		alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Body Fields By Text\nsetBodyDocument(g_objTextDic) Fail");
//		changeMenu(2);
//		return;
	}

/*
	bReturn = setFieldsImage(g_objImageDic);
	if (bReturn == false)
	{
		alert("setBodyDocument(g_objTextDic) Fail");
		return;
	}
*/

	var strDownloadPath = getDownloadPath();
	var strPubDocPath = strDownloadPath + PUBDOC_PATH;
	var strLocalPath = strPubDocPath + "approvalDoc.xml";
	saveToLocalFile(strLocalPath, toXMLString(ApprovalDoc));

	restoreApproveFlow();
// resave
	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();
	var strLocalPath = g_strDownloadPath + getAttachFileName(objBodyFile);
	saveApproveDocumentAs(strLocalPath, false);
	setModified(true);
}

function setDic(objDic, strForm, strValue)
{
	objDic[strForm] = strValue;
}

function getXMLNode(objParentNode, strNodeName)
{
	return objParentNode.selectSingleNode(strNodeName);
}

function getTextFromNode(objNode)
{
	if (objNode != null)
		return objNode.text;
	else
		return "";
}

function getAttributeFromNode(objNode, strAttributeName)
{
	if (objNode != null)
		return objNode.getAttribute(strAttributeName);
	else
		return "";
}

function setApprovalInfoToApprovalDoc(objApprovalInfo, objActiveApprovalLine)
{
	var nOrder = 0;
	var bReturn = true;

// Get approval Node From approvalinfo
	var objApproval = objApprovalInfo.selectNodes("approval");
	for (var iApproval = 0; iApproval < objApproval.length - 1; ++iApproval)
	{
		var objApprover = addApprover(objActiveApprovalLine);
		if (objApprover != null)
		{
			bReturn = setApproverInfoToDoc(objApproval.item(iApproval), objApprover, nOrder);
			if (bReturn == false)
			{
				alert(ALERT_ERROR_OCCUR_FAIL_SET_APPROVER_INFO);
				return false;
			}
			nOrder += 1;
		}
	}

// Get assist Node From approvalinfo
	var objAssist = objApprovalInfo.selectNodes("assist");
	for (var iAssist = 0; iAssist < objAssist.length; ++iAssist)
	{
		var objApprover = addApprover(objActiveApprovalLine);
		bReturn = setApproverInfoToDoc(objAssist.item(iAssist), objApprover, nOrder);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_FAIL_SET_ASSIST_INFO);
			return false;
		}
		nOrder += 1;
	}

// Last Approver
	var objApprover = addApprover(objActiveApprovalLine);
	if (objApprover != null)
	{
		bReturn = setApproverInfoToDoc(objApproval.item(iApproval), objApprover, nOrder);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Set Last Approver Info To ApprovalDoc\nsetApproverInfoToDoc(objApproval.item(iApproval), objApprover, nOrder) Fail");
			return false;
		}
	}
}

function setApproverInfoToDoc(objApproval, objApprover, nOrder)
{
// Get opinion Attribute From approval
	var strOpinion = "";
	var strOpinionAttribute = getAttributeFromNode(objApproval, "opinion");
	if (strOpinionAttribute != null)
	{
		if (strOpinionAttribute == "yes")
		{
			strOpinion = HAVE_OPINION;
		}
	}

// Get signposition Node From approval
	var objSignPosition = getXMLNode(objApproval, "signposition");
	var strSignPosition = getTextFromNode(objSignPosition);
	objSignPosition = null;

// Get type Node From approval
	var objType = getXMLNode(objApproval, "type");
	var strType = getTextFromNode(objType);
	var strRole = getRoleByType(strType);
	var strAction = getAction(strType);
	objType = null;

// Get signimage Node From approval
	var objSignImage = getXMLNode(objApproval, "signimage");
	var strFileName = "";
	if (objSignImage != null)
	{
// Get img Node From signimage
		var objImg = getXMLNode(objSignImage, "img");
		var strSrc = getAttributeFromNode(objImg, "src");
		var strUploadUrl = getUploadUrl();
		var strDownloadPath = getDownloadPath();
		var strPubDocPath = strDownloadPath + PUBDOC_PATH;
		var strExtention = getExtention(strSrc);
		var strGuid = getGUID();
		strFileName = strGuid + strExtention;
		var strFileSize = getLocalFileSize(strPubDocPath + strSrc);

		var bReturn = copyLocalFile(strPubDocPath + strSrc, strDownloadPath + strFileName);
		if (bReturn == false)
			return false;

		bReturn = setRelatedAttach(strFileName, strFileName, strFileSize, "", "");
		if (bReturn == false)
			return false;

		bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
		if (bReturn == false)
			return false;

		objImg = null;
	}
	objSignImage = null;

// Get name Node From approval
	var objName = getXMLNode(objApproval, "name");
	var strName = "";
	if (objName != null)
		strName = getTextFromNode(objName);
	objName = null;

// Get date Node From approval
	var objDate = getXMLNode(objApproval, "date");
	var strDate = getTextFromNode(objDate);
	strDate = strDate.replace(".", "-");
	strDate = strDate.replace(".", "-");
	objDate = null;

// Get time Node From approval
	var objTime = getXMLNode(objApproval, "time");
	if (objTime != null)
	{
		var strTime = getTextFromNode(objTime);
		strDate = strDate + " " + strTime;
	}
	objTime = null;

	setApproverSerialOrder(objApprover, nOrder);
	setApproverParallelOrder(objApprover, nOrder);
	setApproverUserID(objApprover, "");
	setApproverUserName(objApprover, strName);
	setApproverUserPosition(objApprover, strSignPosition);
	setApproverUserPositionAbbr(objApprover, "");
	setApproverUserLevel(objApprover, strSignPosition);
	setApproverUserLevelAbbr(objApprover, "");
	setApproverUserDuty(objApprover, strSignPosition);
	setApproverUserTitle(objApprover, strSignPosition);				// 20031021 USER_TITLE
	setApproverCompany(objApprover, "");
	setApproverDeptName(objApprover, "");
	setApproverDeptCode(objApprover, "");
	setApproverIsSigned(objApprover, "Y");
	setApproverSignDate(objApprover, strDate);
	setApproverSignFileName(objApprover, strFileName);
	setApproverRole(objApprover, strRole);
	setApproverIsOpen(objApprover, "Y");
	setApproverAction(objApprover, strAction);					// "2" : 상신	"6" : 승인
	setApproverType(objApprover, "0");
	setApproverKeepStatus(objApprover, "0");
	setApproverEmptyReason(objApprover, "");
	setApproverIsDocModified(objApprover, "N");
	setApproverIsLineModified(objApprover, "N");
	setApproverIsAttachModified(objApprover, "N");
	setApproverOpinion(objApprover, strOpinion);
	setApproverRepID(objApprover, "");
	setApproverRepName(objApprover, "");
	setApproverRepPosition(objApprover, "");
	setApproverRepPositionAbbr(objApprover, "");
	setApproverRepLevel(objApprover, "");
	setApproverRepLevelAbbr(objApprover, "");
	setApproverRepDuty(objApprover, "");
	setApproverRepTitle(objApprover, "");						// 20031021 REP_TITLE
	setApproverRepCompany(objApprover, "");
	setApproverRepDeptName(objApprover, "");
	setApproverRepDeptCode(objApprover, "");
	return true;
}

function setApprovalInfo4Integrated(objApprovalInfo, objApprovalLine)		// 20031021 APPROVAL_LINE
{
	setCurrentID(objApprovalLine, "");
	setCurrentName(objApprovalLine, "");
	setCurrentRole(objApprovalLine, "");

// Drafter Info
	var objApprovals = objApprovalInfo.selectNodes("approval");
	var objApproval = objApprovals.item(0);

	var objSignPosition = getXMLNode(objApproval, "signposition");
	var strSignPosition = getTextFromNode(objSignPosition);
	objSignPosition = null;

	var objName = getXMLNode(objApproval, "name");
	var strName = "";
	if (objName != null)
		strName = getTextFromNode(objName);
	objName = null;

	var objDate = getXMLNode(objApproval, "date");
	var strDate = getTextFromNode(objDate);
	strDate = strDate.replace(".", "-");
	strDate = strDate.replace(".", "-");
	objDate = null;

	var objTime = getXMLNode(objApproval, "time");
	if (objTime != null)
	{
		var strTime = getTextFromNode(objTime);
		strDate = strDate + " " + strTime;
	}
	objTime = null;

	setDrafterID(objApprovalLine, "");
	setDrafterName(objApprovalLine, strName);
	setDrafterPosition(objApprovalLine, strSignPosition);
	setDrafterPositionAbbr(objApprovalLine, "");
	setDrafterLevel(objApprovalLine, "");
	setDrafterLevelAbbr(objApprovalLine, "");
	setDrafterDuty(objApprovalLine, "");
	setDrafterTitle(objApprovalLine, strSignPosition);
	setDraftDeptName(objApprovalLine, "");
	setDraftDeptCode(objApprovalLine, "");
	setDraftDate(objApprovalLine, strDate);

// Chief Info
	var nLastApprover = objApprovals.length - 1;
	objApproval = objApprovals.item(nLastApprover);
	objName = getXMLNode(objApproval, "name");
	strName = "";
	if (objName != null)
		strName = getTextFromNode(objName);
	objName = null;

	var objType = getXMLNode(objApproval, "type");
	var strType = getTextFromNode(objType);
	var strRole = getRoleByType(strType);
	objType = null;

	objDate = getXMLNode(objApproval, "date");
	strDate = getTextFromNode(objDate);
	strDate = strDate.replace(".", "-");
	strDate = strDate.replace(".", "-");
	objDate = null;

	objTime = getXMLNode(objApproval, "time");
	if (objTime != null)
	{
		strTime = getTextFromNode(objTime);
		strDate = strDate + " " + strTime;
	}
	objTime = null;

	setChiefID(objApprovalLine, "");
	setChiefName(objApprovalLine, strName);
	setChiefRole(objApprovalLine, strRole);
	setCompDate(objApprovalLine, strDate);

	return true;
}

function getRoleByType(strType)
{
	var strReturn = "";
	if (strType == APPROVER_TYPE_0)
		strReturn = APPROVER_ROLE_0;
	else if (strType == APPROVER_TYPE_1)
		strReturn = APPROVER_ROLE_1;
	else if (strType == APPROVER_TYPE_2)
		strReturn = APPROVER_ROLE_2;
	else if (strType == APPROVER_TYPE_3)
		strReturn = APPROVER_ROLE_3;
	else if (strType == APPROVER_TYPE_5)
		strReturn = APPROVER_ROLE_5;
	else if (strType == APPROVER_TYPE_6)
		strReturn = APPROVER_ROLE_6;
	else if (strType == APPROVER_TYPE_7)
		strReturn = APPROVER_ROLE_7;

	return strReturn;
}

function getAction(strType)
{
	var strReturn = "";
	if (strType == APPROVER_TYPE_1)
		strReturn = "2";
	else
		strReturn = "6";

	return strReturn;
}

function setAttachInfoToDoc(strAttachFileList)
{
/*
	var objTitle = objAttach.selectNodes("title");
	if (objTitle.length > 0)
	{
		for (var iAttach = 0; iAttach < objTitle.length; ++iAttach)
		{
			var strUploadUrl = getUploadUrl();
			var strDownloadPath = getDownloadPath();
			var strPubDocPath = strDownloadPath + PUBDOC_PATH;
			var strTitle = getTextFromNode(objTitle.item(iAttach));
			var strDisplayName = getDisplayName(strTitle);
			var strGuid = getGUID();
			var strExtention = getExtention(strTitle);
			var strFileName = strGuid + strExtention;
			var strFileSize = getLocalFileSize(strPubDocPath + strTitle);

			var bReturn = copyLocalFile(strPubDocPath + strTitle, strDownloadPath + strFileName);
			if (bReturn == false)
				return false;

			bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
			if (bReturn == false)
				return false;

			var objGeneralAttach = addGeneralAttachInfo("1", strDisplayName, strFileName, strFileSize, "");
			if (objGeneralAttach == null)
				return false;
		}
	}
	objTitle = null;
	return true;
*/

	if (strAttachFileList != "")
	{
		var arrAttachFile = strAttachFileList.split("^");
		for (var iAttach = 0; iAttach < arrAttachFile.length-1; ++iAttach)
		{
			var strUploadUrl = getUploadUrl();
			var strDownloadPath = getDownloadPath();
			var strPubDocPath = strDownloadPath + PUBDOC_PATH;
			var strTitle = arrAttachFile[iAttach];
			var strDisplayName = getDisplayName(strTitle);
			var strGuid = getGUID();
			var strExtention = getExtention(strTitle);
			var strFileName = strGuid + strExtention;
			var strFileSize = getLocalFileSize(strPubDocPath + strTitle);

			var bReturn = copyLocalFile(strPubDocPath + strTitle, strDownloadPath + strFileName);
			if (bReturn == false)
				return false;

			bReturn = uploadFile(strDownloadPath + strFileName, strUploadUrl + strFileName);
			if (bReturn == false)
				return false;

			var objGeneralAttach = addGeneralAttachInfo("1", strDisplayName, strFileName, strFileSize, "");
			if (objGeneralAttach == null)
				return false;
		}
	}
	return true;
}

function setRelatedAttach(strDisplayName, strFileName, strFileSize, strClassify, strSubDiv)
{
	var objRelatedAttach = addRelatedAttachInfo(strDisplayName, strFileName, strFileSize, "");
	if (objRelatedAttach == null)
		return false;

	setAttachClassify(objRelatedAttach, strClassify);
	setAttachSubDiv(objRelatedAttach, strSubDiv);
	objRelatedAttach = null;
	return true;
}

function getDisplayName(strFile)
{
	var nPos = strFile.lastIndexOf(".");
	if (nPos == -1)
		return strFile;

	return strFile.substr(0, nPos);
}

function getExtention(strFile)
{
	var nPos = strFile.lastIndexOf(".");
	if (nPos == -1)
		return "";

	return strFile.substring(nPos);
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

// 법무부... 비적합 유통문서 삭제
function deletePubDoc()
{
	var strFunction = "onDeleteCompleted";
	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveDeletePubDoc.jsp?dataurl=" + g_strDataUrl + "&cabinet=" + g_strCabinet + "&returnFunction=" + strFunction;
	dataTransform.location.href = strUrl;
}

function onDeleteCompleted(bReturn)
{
	hideWaitDlg();

	if (bReturn == true)
	{
		alert("문서를 삭제하였습니다.");
	}
	else
	{
		alert("문서 삭제에 실패하였습니다.");
	}

//	window.close();
}
