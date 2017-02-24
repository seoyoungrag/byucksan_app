var g_bEnforceHwpProxyCreated = false;
var g_bEnforceStart = false;
var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";
var g_bIsEnforceModified = false;
var g_bHwpEnforceModify;
var g_strHwpEnforceTitle = "";
var g_strEnforceFormName = "";

function getEnforceMenuSet()
{
	var strMenuSet = document.getElementById("hwpMenuSet").value;
	if (strMenuSet.charAt(strMenuSet.length - 1) == ',')
		strMenuSet = strMenuSet.substring(0, strMenuSet.length - 1);

	var strEnforceMenuSet = "";
	var arrMenu  = strMenuSet.split(",");
	for (var i = 0; i < arrMenu.length; i++)
	{
		var arrMenuItem = arrMenu[i].split("/");
		var strMenuCategory = arrMenuItem[0];
		var strMenuID = arrMenuItem[1];
		if (strMenuCategory == "R" || strMenuCategory == "A" || (strMenuCategory == "E"))
		{
			if (strEnforceMenuSet != "")
				strEnforceMenuSet += ",";
			strEnforceMenuSet += strMenuID;
		}
	}

	return strEnforceMenuSet;
}

function getEnforceObjectHTML()
{
	var strHTML = "";

	strHTML += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Enforce_HWPProxy2\" EVENT=\"OnCreated()\">\n";
	strHTML += "	return onEnforceHwpProxyCreated();\n";
	strHTML += "</SCRIPT>\n";
	strHTML += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Enforce_HWPProxy2\" EVENT=\"OnHWPNotFound()\">\n";
	strHTML += "	return onEnforceHwpNotFound();\n";
	strHTML += "</SCRIPT>\n";
	strHTML += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Enforce_HWPProxy2\" EVENT=\"OnHWPCommand(nCommandID)\">\n";
	strHTML += "	return onEnforceHwpProxyCommand(nCommandID);\n";
	strHTML += "</SCRIPT>\n";
	strHTML += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Enforce_HWPProxy2\" EVENT=\"OnManualSeal(strStampName)\">\n";
	strHTML += "	return onEnforceHwpManualSeal(strStampName);\n";
	strHTML += "</SCRIPT>\n";

	strHTML += "<OBJECT id='Enforce_HWPProxy2' width='100%' height='1' classid='CLSID:ED50765A-B652-48DB-ACD0-2989386B6FF4'>";
	strHTML += "<param name='_Version' value='65536'/>";
	strHTML += "<param name='_ExtentX' value='2646'/>";
	strHTML += "<param name='_ExtentY' value='1323'/>";
	strHTML += "<param name='_StockProps' value='0'/>";
	strHTML += "</OBJECT>";

	return strHTML;
}

function onLoadEnforceDocument()
{
	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
			break;

		nProposal++;
	}

	if (nProposal > nBodyCount)
		return;

	g_nEnforceProposal = nProposal;

//	changeMenu(0);
/*
	Enforce_HWPProxy2.attachEvent("OnHWPCommand", onEnforceHwpProxyCommand);
	Enforce_HWPProxy2.attachEvent("OnCreated", onEnforceHwpProxyCreated);
	Enforce_HWPProxy2.attachEvent("OnHWPNotFound", onEnforceHwpNotFound);
	Enforce_HWPProxy2.attachEvent("OnManualSeal", onEnforceHwpManualSeal);
*/
	g_bEnforceStart = true;

	loadEnforceDocument();
}

function onUnloadEnforceDocument()
{
	closeEnforceDocument();
}

function onEnforceHwpProxyCreated()
{
	if (g_bEnforceHwpProxyCreated == false)
		g_bEnforceHwpProxyCreated = true;

	if (g_bEnforceStart == true)
		loadEnforceDocument();
}

function onEnforceHwpNotFound()
{
}

function loadEnforceDocument()
{
	if (g_bEnforceStart == false || g_bEnforceHwpProxyCreated == false)
		return;

	var bReplace = g_bHwpEnforceOpen;
//	g_bHwpEnforceModify = (g_strEditType == "0");
	g_bHwpEnforceModify = false;
	var nEditMode = (g_bHwpEnforceModify ? 2 : 1);
	var nMenuSetID = 1;
	var nWindowPos = 2;

//	g_bIsEnforceModified = (g_strEditType == "0");
	var objEnforceFile = getEnforceFileObj(g_nEnforceProposal);
	if (objEnforceFile != null && (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13" && g_bPreviewConvert == true))
	{
		removeExtendAttachInfo(getAttachFileName(objEnforceFile));
		objEnforceFile = null;
	}

	if (objEnforceFile == null)
	{
		objEnforceFile = makeEnforceDocument(g_strEnforceFormUrl, g_nEnforceProposal);
		if (objEnforceFile == null)
			return;
	}
	else
	{
		downloadEnforceFile(g_nEnforceProposal);
		var strFileName = getAttachFileName(objEnforceFile);
		if (strFileName != "")
		{
			g_strHwpEnforceTitle = ENFORCE_DOCUMENT;
			if (getTitle(g_nEnforceProposal) != "")
				g_strHwpEnforceTitle += " - [" + getTitle(g_nEnforceProposal) + "]";
			g_strHwpEnforceTitle = g_strHwpEnforceTitle.replace(/,/g, " ");

			if (getLocalFileSize(g_strDownloadPath + strFileName) > 0)
			{
				Enforce_HWPProxy2.OpenDocumentEx(g_strHwpEnforceTitle, g_strDownloadPath + strFileName, bReplace, nEditMode, nMenuSetID, getEnforceMenuSet(), nWindowPos, g_bHideParent);
			}
			else
			{
				alert(ALERT_FIND_ENFORCE_DOCUMENT_ERROR);
				return;
			}
			setEnforceXmlDataToForm();
		}
		else
		{
//			Enforce_HWPProxy2.OpenDocumentEx("", bReplace, nEditMode, nMenuSetID, getEnforceMenuSet(), nWindowPos, g_bHideParent);
			return;
		}
	}

	if (g_strOption83 == "0")
	{
		var objApprover = getCompleteApprover();
		if (objApprover != null)
		{
			strSignFileName = getApproverSignFileName(objApprover);
			setEnforceStamp(0, strSignFileName);
		}
	}
	restoreEnforceApproveFlow();

	if (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13" && g_bPreviewConvert == true)
		editDocument(false);

	moveHwpDocumentWindow();
	Enforce_HWPProxy2.SetVisible(true);
	Enforce_HWPProxy2.SetDocumentViewRatio("width","");
	Enforce_HWPProxy2.Focus();

	g_bHwpEnforceOpen = true;
	g_bPreviewConvert = false;
}

function closeEnforceDocument()
{
}

function isAutoSetField(strFieldName)
{
	if (strFieldName == FIELD_BODY ||
		strFieldName == FIELD_DOC_NUMBER ||
		strFieldName == FIELD_CONSERVE_TERM ||
		strFieldName == FIELD_RECP_CONSERVE ||
		strFieldName == FIELD_PUBLIC_BOUND ||
		strFieldName == FIELD_ENFORCE_DATE ||
		strFieldName == FIELD_RECEIVE_DATE ||
		strFieldName == FIELD_RECEIVE_TIME ||
		strFieldName == FIELD_RECIPIENT ||
		strFieldName == FIELD_REFERER ||
		strFieldName == FIELD_RECEIVE_NUMBER ||
		strFieldName == FIELD_PROCESS_DEPT ||
//		strFieldName == FIELD_REAL_NAME ||
		strFieldName == FIELD_TREATMENT ||
		strFieldName.indexOf(FIELD_TITLE) == 0 ||
		strFieldName.indexOf(FIELD_SENDER_AS + "#") == 0 ||
		strFieldName.indexOf(FIELD_RECIPIENT_REFER + "#") == 0)
		return true;
	return false;
}

function makeEnforceDocument(strEnforceFormUrl, nCaseNumber)
{
	var bReplace = g_bHwpEnforceOpen;
	var nEditMode = (g_bHwpEnforceModify ? 2 : 1);
	var nMenuSetID = 1;
	var nWindowPos = 2;

	var objEnforceFile = null;

	if (strEnforceFormUrl == "")
		return objEnforceFile;

	var strDocCategory = getDocCategory(nCaseNumber);

	if (strDocCategory != "E" && strDocCategory != "W")
		return objEnforceFile;

	objEnforceFile = getEnforceFileObj(nCaseNumber)
	if (objEnforceFile == null)
	{
		g_strHwpEnforceTitle = ENFORCE_DOCUMENT;
		if (strEnforceFormUrl != "")
		{
			if (g_strEnforceFormName != "")
				g_strHwpEnforceTitle += " - [" + g_strEnforceFormName + "]";
			g_strHwpEnforceTitle = g_strHwpEnforceTitle.replace(/,/g, " ");

			if (getLocalFileSize(g_strDownloadPath + strEnforceFormUrl) > 0)
			{
				Enforce_HWPProxy2.OpenDocumentEx(g_strHwpEnforceTitle, g_strDownloadPath + strEnforceFormUrl, bReplace, nEditMode, nMenuSetID, getEnforceMenuSet(), nWindowPos, g_bHideParent);
				Enforce_HWPProxy2.MoveDocumentWindow(nWindowPos);
// 20030801			
				setEnforceXmlDataToForm();
				setEnforceRecipientsToForm();
				setArbitProxyApproverToForm();
			}
			else
			{
				alert(ALERT_FIND_ENFORCE_DOCUMENT_ERROR);
				return null;
			}

//			setEnforceXmlDataToForm();
		}

		Document_HWPProxy2.CopyProposal(nCaseNumber);
		Enforce_HWPProxy2.PasteProposal();

		var strSrcFields = Document_HWPProxy2.GetFieldList("\u0002");
		var arrSrcField = strSrcFields.split("\u0002");
		var strSrcValues = Document_HWPProxy2.GetFieldText(strSrcFields);
		var arrSrcValue = strSrcValues.split("\u0002");

		var objSrcDic = new Array();
		var nSrcFieldCount = arrSrcField.length;
		var i;
		for (i = 0; i < nSrcFieldCount; i++)
		{
			objSrcDic[arrSrcField[i]] = arrSrcValue[i];
		}

		var strDstFields = Enforce_HWPProxy2.GetFieldList("\u0002");
		var arrDstField = strDstFields.split("\u0002");
		var strDstValues = Enforce_HWPProxy2.GetFieldText(strDstFields);
		var arrDstValue = strDstValues.split("\u0002");

		var objDstDic = new Array();
		var nDstFieldCount = arrDstField.length;

		for (i = 0; i < nDstFieldCount; i++)
			objDstDic[arrDstField[i]] = "";

		var strFieldList = "", strValueList = "";
		for (i = 0; i < nSrcFieldCount; i++)
		{
			if (isExistKey(objDstDic, arrSrcField[i]) && !isAutoSetField(arrSrcField[i]))
			{
				strFieldList += arrSrcField[i] + "\u0002";
				strValueList += arrSrcValue[i] + "\u0002";
			}
		}

		strField = FIELD_RECIPIENT;
		if (isExistKey(objDstDic, strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += getValue(objSrcDic, strField + (nCaseNumber == 1 ? "" : nCaseNumber)) + "\u0002";
		}

		strField = FIELD_REFERER;
		if (isExistKey(objDstDic, strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += getValue(objSrcDic, strField + (nCaseNumber == 1 ? "" : nCaseNumber)) + "\u0002";
		}

		strField = FIELD_RECIPIENT_REFER + "#1";
		if (isExistKey(objDstDic, strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += getValue(objSrcDic, FIELD_RECIPIENT_REFER + "#" + nCaseNumber) + "\u0002";
		}

		strField = FIELD_SENDER_AS + "#1";
		if (isExistKey(objDstDic, strField))
		{
			var strSrcSenderAsField = "";
			if (isExistKey(objSrcDic, FIELD_SENDER_AS + "#" + nCaseNumber))
				strSrcSenderAsField = FIELD_SENDER_AS + "#" + nCaseNumber;
/*
			else if (isExistKey(objSrcDic, FIELD_SENDER_AS + "#" + getBodyCount()))
				strSrcSenderAsField = FIELD_SENDER_AS + "#" + getBodyCount();
*/
			strFieldList += strField + "\u0002";
			strValueList += getValue(objSrcDic, strSrcSenderAsField) + "\u0002";
		}

		if (strFieldList.length > 0)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);
			Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
		}
/*
		setEnforceXmlDataToForm();
		setEnforceRecipientsToForm();
		setArbitProxyApproverToForm();
*/

//		objEnforceFile = setEnforceFileInfo(nCaseNumber);
		objEnforceFile = getEnforceFileObj(nCaseNumber);
		if (objEnforceFile == null)
			objEnforceFile = setEnforceFileInfo(nCaseNumber);

		saveEnforceFile(g_nEnforceProposal)
		setEnforceModified(true);
	}

	return objEnforceFile;
}

function setEnforceXmlDataToForm()
{
	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	var i;
	for (i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	var strFieldList = "", strValueList = "";

	var nBodyCount = getBodyCount();

	if (isExistKey(objDic, FIELD_TITLE) == true)
	{
		strFieldList += FIELD_TITLE + "\u0002";
		strValueList += getTitle(g_nEnforceProposal) + "\u0002";
	}

	if (isExistKey(objDic, FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}

	if (isExistKey(objDic, FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = getDraftConserve();
		strFieldList += FIELD_CONSERVE_TERM + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (isExistKey(objDic, FIELD_RECP_CONSERVE) == true)
	{
		var strConserveTerm = getEnforceConserve();
		strFieldList += FIELD_RECP_CONSERVE + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (isExistKey(objDic, FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = getPublication(getPublicLevel());
		strFieldList += FIELD_PUBLIC_BOUND + "\u0002";
		strValueList += strPublicBound + "\u0002";
	}

	if (isExistKey(objDic, FIELD_ENFORCE_DATE) == true)
	{
		var strEnforceDate = getEnforceDate();
		if (strEnforceDate == "")
		{
			var strDocCategory = getDocCategory();
			if (strDocCategory == "I")
			{
				strEnforceDate = getCompleteDate();
			}
		}
		var strDate = "";
		if (strEnforceDate != "")
			strDate = getDateDisplay(strEnforceDate, g_strOption36);

		strFieldList += FIELD_ENFORCE_DATE + "\u0002";
		strValueList += strDate + "\u0002";
	}

	if (isExistKey(objDic, FIELD_RECEIVE_DATE) == true)
	{
		var strReceiveDate = getReceiveDate();
		var strDate = "", strTime = "";
		if (strReceiveDate != "")
		{
			strDate = getDateDisplay(strReceiveDate, g_strOption36);
			strTime = getTimeDisplay(strReceiveDate);
		}

		strFieldList += FIELD_RECEIVE_DATE + "\u0002";
		strValueList += strDate + "\u0002";

		if (isExistKey(objDic, FIELD_RECEIVE_TIME) == true)
		{
			strFieldList += FIELD_RECEIVE_TIME + "\u0002";
			strValueList += strTime + "\u0002";
		}
	}

	if (isExistKey(objDic, FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "0")
		{
			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strReceiveNumber + "\u0002";
		}
	}

	if (isExistKey(objDic, FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (isExistKey(objDic, FIELD_TREATMENT) == true)
	{
		var strDocCategory = getDocCategory(g_nEnforceProposal);
		strFieldList += FIELD_TREATMENT + "\u0002";
		Enforce_HWPProxy2.SetReadOnly(false);
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			var strTreatment = getTreatment(g_nEnforceProposal);
			strValueList += strTreatment + "\u0002";
			try
			{
				Enforce_HWPProxy2.SetCellLine(FIELD_TREATMENT, "allon", "single");
			}
			catch (e)
			{
			}
		}
		else
		{
			strValueList += "\u0002";
			try
			{
				Enforce_HWPProxy2.SetCellLine(FIELD_TREATMENT, "allon", "noline");
			}
			catch (e)
			{
			}
		}
		Enforce_HWPProxy2.SetReadOnly(true);
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
//	setEnforceRecipientsToForm();
}

function setEnforceRecipientsToForm()
{
	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	var strFieldList = "", strValueList = "";

	var strDeptName = "", strRefName = "", strDeptRefName = "";
	var strDeptField = "", strRefField = "", strDeptRefField = "";

	var objRecipGroup = getRecipGroup(g_nEnforceProposal);
	if (objRecipGroup != null)
	{
		var strDisplayAs = getRecipGroupDisplayAs(objRecipGroup);
		var nRecipientCount = getRecipientCount(objRecipGroup);
		if (strDisplayAs != "")
		{
			if (nRecipientCount == 1)
				strDeptName = strDisplayAs;
			else if (nRecipientCount > 1)
				strDeptRefName = strDisplayAs;
		}
		else
		{
			var objRecipient = getFirstRecipient(objRecipGroup);
			while (objRecipient != null)
			{
				if (g_strOption15 == "0")
				{
					strDeptName = getRecipientDeptName(objRecipient);
				}
				else if (g_strOption15 == "1")
				{
					strDeptName = getRecipientDeptSymbol(objRecipient);
				}
				else if (g_strOption15 == "2")
				{
					strDeptName = getRecipientDeptChief(objRecipient);
				}
				else
				{
					strDeptName = getRecipientDeptName(objRecipient);
				}

				if (g_strOption16 == "0")
				{
					strRefName = getRecipientRefDeptName(objRecipient);
				}
				else if (g_strOption16 == "1")
				{
					strRefName = getRecipientRefDeptSymbol(objRecipient);
				}
				else if (g_strOption16 == "2")
				{
					strRefName = getRecipientRefDeptChief(objRecipient);
				}
				else
				{
					strRefName = getRecipientRefDeptName(objRecipient);
				}

				if (strDeptRefName != "")
					strDeptRefName += ", ";

				strDeptRefName += strDeptName;
				if (strRefName != "")
					strDeptRefName += "(" + strRefName + ")";

				objRecipient = getNextRecipient(objRecipient);
			}
		}

		strDeptField = FIELD_RECIPIENT;
		strRefField = FIELD_REFERER;
		strDeptRefField = FIELD_RECIPIENT_REFER + "#1";

		if (isExistKey(objDic, strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += strDisplayAs + "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strDeptName + "\u0002";
			else
				strValueList += RECIPIENT_REFER + "\u0002";
		}

		if (isExistKey(objDic, strRefField))
		{
			strFieldList += strRefField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strRefName + "\u0002";
			else
			{
				if (getRefRecipientCount(objRecipGroup) > 0)
					strValueList += RECIPIENT_REFER + "\u0002";
				else
					strValueList += "\u0002";
			}
		}

		if (isExistKey(objDic, strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			if (nRecipientCount < 2)
				strValueList += "\u0002";
			else
				strValueList += LABEL_RECIPIENT + ": " + strDeptRefName + "\u0002";
		}
	}
	else		// no RecipGroup
	{
		if (isExistKey(objDic, strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(g_nEnforceProposal);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";
		}

		if (isExistKey(objDic, strRefField))
		{
			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}

		if (isExistKey(objDic, strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			strValueList += "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}

	saveEnforceFile(g_nEnforceProposal);
	setEnforceModified(true);
}

function saveEnforceFile(nCaseNumber)
{
	clearEnforceApproveFlow();

	var bRet = false;

	var objEnforceFile = getEnforceFileObj(nCaseNumber);
	if (objEnforceFile == null)
		objEnforceFile = setEnforceFileInfo(nCaseNumber);

	var strFileName = getAttachFileName(objEnforceFile);

	if (Enforce_HWPProxy2.SaveDocument(g_strDownloadPath + strFileName, false) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objEnforceFile, strFileSize);
	}

	restoreEnforceApproveFlow();
	Enforce_HWPProxy2.SetModified(0);
	return bRet;
}

function uploadEnforceFile(nCaseNumber)
{
	var strFileName = getAttachFileName(getEnforceFileObj(nCaseNumber));
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
}

function clearEnforceApproveFlow()
{
	clearEnforceApprovalSeal();
}

function restoreEnforceApproveFlow()
{
	// 20030429, manual seal...
	restoreEnforceApprovalSeal();
//	setEnforceApprovalSeal();

	setEnforceApprovalFlow();
	setEnforceApprovalSign();
}

// 수동 날인시 저장 요구됨
function setEnforceApprovalSeal()
{
	var bModified = false;
	var objExtendAttach = getFirstExtendAttach();

	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			var strCaseNumber = getAttachCaseNumber(objExtendAttach);
			if (parseInt(strCaseNumber) == g_nEnforceProposal)
			{
				var strSubDiv = getAttachSubDiv(objExtendAttach);
				var strFieldName = "";

				if (strSubDiv == "DeptStamp")
					strFieldName = FIELD_DEPT_STAMP;
				else if (strSubDiv == "DeptOmitStamp")
					strFieldName = FIELD_DEPT_OMIT_STAMP;
				else if (strSubDiv == "CompanyStamp")
					strFieldName = FIELD_COMPANY_STAMP;
				else if (strSubDiv == "CompanyOmitStamp")
					strFieldName = FIELD_COMPANY_OMIT_STAMP;

				if (strFieldName != "")
				{
					var strFileName = getAttachFileName(objExtendAttach);
					var strFilePath = g_strDownloadPath + strFileName;

					if (getLocalFileSize(strFilePath) > 0)
					{
						bModified = true;
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
							// 20030429, manual seal...
							if (confirm(CONFIRM_AUTO_SEAL) == true)
							{
								var strUnionField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
								if (isExistKey(objDic, strFieldName) || isExistKey(objDic, strUnionField) ||
									isExistKey(objDic, FIELD_STAMP) || isExistKey(objDic, FIELD_SENDER_AS + "#1"))
								{
									if (isExistKey(objDic, strFieldName))
									{
									}
									else if (isExistKey(objDic, strUnionField))
										Enforce_HWPProxy2.RenameField(strUnionField, strFieldName);
									else if (isExistKey(objDic, FIELD_STAMP))
										Enforce_HWPProxy2.RenameField(FIELD_STAMP, strFieldName);
									else if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
										Enforce_HWPProxy2.InsertSealHwp(strFieldName);

									if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
										Enforce_HWPProxy2.AdjustSealPos(strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

									Enforce_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								}
								else
								{
									alert(ALERT_CLICK_POSITION_TO_STAMP);
									Enforce_HWPProxy2.PutSealFromFileEx(strFieldName, strFilePath);
									setEnforceModified(true);
								}
							}
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								Enforce_HWPProxy2.PutSealFromFileEx(strFieldName, strFilePath);
								setEnforceModified(true);
							}

						}
						else if (strFieldName == FIELD_DEPT_OMIT_STAMP || strFieldName == FIELD_COMPANY_OMIT_STAMP ||
								strFieldName == FIELD_ENFORCE_OMIT_STAMP)
						{
							var strUnionField = "";
							if (strFieldName == FIELD_DEPT_OMIT_STAMP)
								strUnionField = FIELD_COMPANY_OMIT_STAMP;
							else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
								strUnionField = FIELD_DEPT_OMIT_STAMP;
							strUnionField = (strFieldName == FIELD_DEPT_OMIT_STAMP ? FIELD_COMPANY_OMIT_STAMP : FIELD_DEPT_OMIT_STAMP);
							if (isExistKey(objDic, strFieldName) || isExistKey(objDic, strUnionField) ||
								isExistKey(objDic, FIELD_OMIT_STAMP))
							{
								if (isExistKey(objDic, strUnionField))
									Enforce_HWPProxy2.RenameField(strUnionField, strFieldName);
								else if (isExistKey(objDic, FIELD_OMIT_STAMP))
									Enforce_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
								Enforce_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
							}
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								Enforce_HWPProxy2.PutOmitSealFromFileEx(strFieldName, strFilePath);
								setEnforceModified(true);
							}
						}
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}

//	setArbitProxyApproverToForm();
}

// 20030429, manual seal...
function restoreEnforceApprovalSeal()
{
	var objExtendAttach = getFirstExtendAttach();

	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			var strCaseNumber = getAttachCaseNumber(objExtendAttach);
			if (parseInt(strCaseNumber) == g_nEnforceProposal)
			{
				var strSubDiv = getAttachSubDiv(objExtendAttach);
				var strFieldName = "";

				if (strSubDiv == "DeptStamp")
					strFieldName = FIELD_DEPT_STAMP;
				else if (strSubDiv == "DeptOmitStamp")
					strFieldName = FIELD_DEPT_OMIT_STAMP;
				else if (strSubDiv == "CompanyStamp")
					strFieldName = FIELD_COMPANY_STAMP;
				else if (strSubDiv == "CompanyOmitStamp")
					strFieldName = FIELD_COMPANY_OMIT_STAMP;

				if (strFieldName != "")
				{
					var strFileName = getAttachFileName(objExtendAttach);
					var strFilePath = g_strDownloadPath + strFileName;

					if (getLocalFileSize(strFilePath) > 0)
					{
						// 수동으로 날인된 경우
						if (isExistKey(objDic, strFieldName))
						{
							Enforce_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
						}
						// 자동으로 날인된 경우
						else
						{
							if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
							{
								var strUnionField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
								if (isExistKey(objDic, strUnionField) ||
									isExistKey(objDic, FIELD_STAMP) || isExistKey(objDic, FIELD_SENDER_AS + "#1"))
								{
									if (isExistKey(objDic, strUnionField))
										Enforce_HWPProxy2.RenameField(strUnionField, strFieldName);
									else if (isExistKey(objDic, FIELD_STAMP))
										Enforce_HWPProxy2.RenameField(FIELD_STAMP, strFieldName);
									else if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
										Enforce_HWPProxy2.InsertSealHwp(strFieldName);

									if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
										Enforce_HWPProxy2.AdjustSealPos(strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);
									Enforce_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								}
							}
							else if (strFieldName == FIELD_DEPT_OMIT_STAMP || strFieldName == FIELD_COMPANY_OMIT_STAMP ||
									strFieldName == FIELD_ENFORCE_OMIT_STAMP)
							{
								var strUnionField = "";
								if (strFieldName == FIELD_DEPT_OMIT_STAMP)
									strUnionField = FIELD_COMPANY_OMIT_STAMP;
								else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
									strUnionField = FIELD_DEPT_OMIT_STAMP;
								strUnionField = (strFieldName == FIELD_DEPT_OMIT_STAMP ? FIELD_COMPANY_OMIT_STAMP : FIELD_DEPT_OMIT_STAMP);
								if (isExistKey(objDic, strUnionField) || isExistKey(objDic, FIELD_OMIT_STAMP))
								{
									if (isExistKey(objDic, strUnionField))
										Enforce_HWPProxy2.RenameField(strUnionField, strFieldName);
									else if (isExistKey(objDic, FIELD_OMIT_STAMP))
										Enforce_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
									Enforce_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								}
							}
						}
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}

//	setArbitProxyApproverToForm();
}

/*
function removeEnforceApprovalSeal(strCaseNumber, strSubDiv)
{
	var objExtendedAttach = getExtendAttachBySubDiv(strCaseNumber, strSubDiv);
	if (objExtendedAttach != null)
	{
		var strFieldName;
		if (strSubDiv == "DeptStamp")
			strFieldName = FIELD_DEPT_STAMP;
		else if (strSubDiv == "DeptOmitStamp")
			strFieldName = FIELD_DEPT_OMIT_STAMP;
		else if (strSubDiv == "CompanyStamp")
			strFieldName = FIELD_COMPANY_STAMP;
		else if (strSubDiv == "CompanyOmitStamp")
			strFieldName = FIELD_COMPANY_OMIT_STAMP;
		Enforce_HWPProxy2.ClearStamp(strFieldName);
	}
}
*/

function clearEnforceApprovalSeal()
{
	var objExtendAttach = getFirstExtendAttach();
	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			var strCaseNumber = getAttachCaseNumber(objExtendAttach);
			if (parseInt(strCaseNumber) == g_nEnforceProposal)
			{
				var strSubDiv = getAttachSubDiv(objExtendAttach);

				if (strSubDiv == "DeptStamp")
					strFieldName = FIELD_DEPT_STAMP;
				else if (strSubDiv == "DeptOmitStamp")
					strFieldName = FIELD_DEPT_OMIT_STAMP;
				else if (strSubDiv == "CompanyStamp")
					strFieldName = FIELD_COMPANY_STAMP;
				else if (strSubDiv == "CompanyOmitStamp")
					strFieldName = FIELD_COMPANY_OMIT_STAMP;

				if (strFieldName == "")
					continue;

				Enforce_HWPProxy2.ClearStamp(strFieldName);
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

// nSetType : 0=setApprovalFlow, 1=setApprovalSign, 2=setUserSign
// bSetData : true=set, false=clear
// strUserID : break on match
function setEnforceApprovalField(nSetType, bSetData, strUserID)
{
	var strFormUsage = getFormUsage();

	var strLineType = getCurrentLineType();
	if (strLineType == "3")
	{
		var objApprovalLine = getFirstApprovalLine();
		while (objApprovalLine != null)
		{
			if (getLineType(objApprovalLine) == "3")
				setEnforceApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);

			objApprovalLine = getNextApprovalLine(objApprovalLine);
		}
	}
	else
	{
		var objApprovalLine = null;
		if (g_strOption148 == "0")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName("0");

		if (objApprovalLine == null)
			return;


		if (g_strOption148 == "0")
			setEnforceApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
		else

			setEnforceApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}

function setEnforceApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Enforce_HWPProxy2.GetFieldText(strFields);
	var arrValue = strValues.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = arrValue[i];

	var bIsForm = isExistKey(objDic, FIELD_TITLE);

	var strFieldSlots = "", strFieldList = "", strValueList = "";
	var strEmptyFields = "";
	var strSlashFields = "";
	var strReportDept = "";

	var nApprove = 0;
	var nRead = 0;
	var nAssist = 0;
	var nInspect = 0;

	var nSerialOrder = 0;
	var nLength = getApproverCount(objApprovalLine);
	var objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);

	var strApprovalField;
	var strRole = "";
	var strType = "";
	var strPrevRole = "";
	var strPrevType = "";

	while (objApprover != null)
	{
		strApprovalField = "";

		strRole = getApproverRole(objApprover);
		strType = getApproverType(objApprover);

		if (strType == "1" && strRole == "2")
		{
			var objNextApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder + 1));
			if (objNextApprover != null)
			{
				var strNextType = getApproverType(objNextApprover);
				if (strNextType != strType)
					strRole = 5;
			}
		}
		else if (strType == "2" && strRole == "2")
		{
			var objPrevApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder - 1));
			if (objPrevApprover != null)
			{
				strPrevRole = getApproverRole(objPrevApprover);
				strPrevType = getApproverType(objPrevApprover);

				if (strPrevRole == "34" || strPrevType != strType)
				{
					strRole = 1;
					nApprove = 0;
				}
			}
		}
		else if (strPrevType != strType)
		{
			nAssist = 0;
			nInspect = 0;
		}

		if (strRole == "0" || strRole == "5")
		{
			strApprovalField = FIELD_LAST;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-1";
			else
				strApprovalField += "1";
		}
		else if (strRole == "1")
		{
			strApprovalField = FIELD_DRAFTER;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-1";
			else
				strApprovalField += "1";
		}
		else if (strRole == "2")
		{
			nApprove++;
			strApprovalField = FIELD_CONSIDER;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-" + nApprove;
			else
				strApprovalField += nApprove;
		}
		else if (strRole == "4")			// 열람 필드 추가
		{
			nRead++;
			strApprovalField = FIELD_READER;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-" + nRead;
			else
				strApprovalField += nRead;
		}
		else if (strRole == "6" || strRole == "7")
		{
			if (nSetType == 0)
			{
				nApprove++;
				strApprovalField = FIELD_CONSIDER;
				if (strType == "1" || strType == "2")
					strApprovalField += strType + "-" + nApprove;
				else
					strApprovalField += nApprove;
			}
			else if (nSetType == 1)
			{
				strApprovalField = FIELD_LAST;
				if (strType == "1" || strType == "2")
					strApprovalField += strType + "-1";
				else
					strApprovalField += "1";
			}
		}
		else if (strRole == "3" || strRole == "12" || strRole == "13" || strRole == "32" || strRole == "33")
		{
			nAssist++;
			strApprovalField = FIELD_COOPERATE;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-" + nAssist;
			else
				strApprovalField += nAssist;
		}
		else if (strRole == "8" || strRole == "9")
		{
			if (nSetType == 0)
			{
				if (nSerialOrder == nLength - 1)
				{
					strApprovalField = FIELD_LAST;
					if (strType == "1" || strType == "2")
						strApprovalField += strType + "-1";
					else
						strApprovalField += "1";
				}
				else
				{
					var objNextApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder + 1));
					var strNextType = getApproverType(objNextApprover);
					var strNextRole = getApproverRole(objNextApprover);
					if (strType == "1")
					{
						if (strNextType != strType)
						{
							strApprovalField = FIELD_LAST + strType + "-1";
						}
						else
						{
							if (strNextRole == "10" || strNextRole == "30")
							{
								strApprovalField = FIELD_LAST + strType + "-1";
							}
							else
							{
								nApprove++;
								strApprovalField = FIELD_CONSIDER + strType + "-" + nApprove;
							}
						}
					}
					else
					{
						if (strNextRole == "10" || strNextRole == "30")
						{
							strApprovalField = FIELD_LAST;
							if (strType == "1" || strType == "2")
								strApprovalField += strType + "-1";
							else
							{
								strApprovalField += "1";
							}
						}
						else
						{
							nApprove++;
							strApprovalField = FIELD_CONSIDER;
							if (strType == "1" || strType == "2")
								strApprovalField += strType + "-" + nApprove;
							else
								strApprovalField += nApprove;
						}
					}
				}

				if (strApprovalField.indexOf(FIELD_CONSIDER) >= 0 && bIsForm == true/* && g_strOption77 == "1"*/)
					strSlashFields += strApprovalField + "^";
			}
		}
		else if (strRole == "21")
		{
			strApprovalField = FIELD_PRIOR + "1";
		}
		else if (strRole == "20")
		{
			strApprovalField = FIELD_CHARGER;
		}
		else if (strRole == "22" || strRole == "23")
		{
			nApprove++;
			strApprovalField = FIELD_PUBLIC + nApprove;
		}
		else if (strRole == "11" || strRole == "31")
		{
			nInspect++;
			strApprovalField = FIELD_DEPART_INSPECT + nInspect;

			nInspect++;
			strApprovalField = FIELD_DEPART_INSPECT;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "-" + nInspect;
			else
				strApprovalField += nInspect;
		}
		else if (strRole == "30")
		{
			if (bSetData)
			{
				var strDeptCode = getApproverDeptCode(objApprover);
/*
				dataForm.xmlData.value = "";
				dataForm.returnFunction.value = "onSetDeptCode"

				dataForm.action = "./org/jsp/CN_GetDeptInfo.jsp?deptCode=" + strDeptCode;
				dataForm.target = "dataTransform";
				dataForm.submit();

				var objXMLDoc = objXMLHttp.responseXML;
				if (objXMLDoc != null)
				{
					var strDeptName = "";
					if (g_strOption15 == "0")
						strDeptName = objXMLDoc.selectSingleNode("//ou").text;
					// receiving site symbol
					else if (g_strOption15 == "1")
						strDeptName = objXMLDoc.selectSingleNode("//sDSCom2000-Uni-AddressSymbol").text;
					// as of dept cheif
					else if (g_strOption15 == "2")
						strDeptName = objXMLDoc.selectSingleNode("//sDSCom2000-Uni-ChiefTitle").text;
					else
						strDeptName = objXMLDoc.selectSingleNode("//ou").text;

					if (strReportDept != "")
						strReportDept += ", ";
					strReportDept += strDeptName;
				}
*/
			}
		}

		if (nSetType == 0)
		{
			// strRole != "4"(열람) 비교 제거함
			if (strApprovalField != "" && strRole != "10" && strRole != "30")
			{
//				if (isExistKey(objDic, strApprovalField) == true)
					strFieldSlots += strApprovalField + "^";

				// 이름
				var strApproverName = "#" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
				}
				else
				{
					var strUserName = "";
					if (bSetData)
						strUserName = getApproverUserName(objApprover);

					strFieldList += strApproverName + "\u0002";
					strValueList += strUserName + "\u0002";
				}

				var strDeptName = "";
				if (bSetData)
					strDeptName = getApproverDeptName(objApprover);

				// 개인부서명
				var strApproverDept = "$" + strApprovalField;
				strFieldList += strApproverDept + "\u0002";
				strValueList += strDeptName + "\u0002";

				// 직위 or 부서명(부서단위)
				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (isExistKey(objDic, strApproverDept) == false)
					{
						strFieldList += strApprovalType + "\u0002";
						strValueList += strDeptName + "\u0002";
					}
				}
				else
				{
					var strPosition = "";
					if (bSetData)
					{
						var arroption37 = g_strOption37.split("^");
						strPosition = getUserPosition(objApprover, arroption37[0]);
					}

					strFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + "\u0002";
				}

				// 상세직위 or 부서명(부서단위)
				var strApproverTitle = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (isExistKey(objDic, strApproverDept) == false)
					{
						strFieldList += strApproverTitle + "\u0002";
						strValueList += strDeptName + "\u0002";
					}
				}
				else
				{
					var strUserTitle = "";
					if (bSetData)
						strUserTitle = getApproverUserTitle(objApprover);

					strFieldList += strApproverTitle + "\u0002";
					strValueList += strUserTitle + "\u0002";
				}

				if (strRole == "6")
				{
					if (g_strOption4 == "0")
					{
						strSlashFields += strApprovalField + "^";
					}
					else if (g_strOption4 == "1")
					{
						strFieldList += strApprovalField + "\u0002";
						var strArbitFieldValue = "";
						if (bSetData)
							strArbitFieldValue = FIELD_ARBITRARY;
						strValueList += strArbitFieldValue + "\u0002";
					}
				}
				else if (strRole == "7")
				{
					strFieldList += strApprovalField + "\u0002";
					var strProxyFieldValue = "";
					if (bSetData)
						strProxyFIeldValue = FIELD_PROXY;
					strValueList += strProxyFIeldValue + "\u0002";
					if (g_strOption5 == "0")
					{
						// do nothing
					}
					else if (g_strOption5 == "1")
					{
						strSlashFields += strApprovalField + "^";
					}
				}
				else if (strRole == "8")
				{
					strFieldList += strApprovalField + "\u0002";
					strValueList += "\u0002";
				}
				else if (strRole == "9")
				{
					strFieldList += strApprovalField + "\u0002";
					strValueList += FIELD_REAR + "\u0002";
				}
				else if (strRole == "2" || strRole == "3")
				{
					var strEmptyReason = getApproverEmptyReason(objApprover);
					if (strEmptyReason != "")
					{
						if (g_strOption78 == "1")
						{
//							strEmptyFields += strApprovalField + "^";
							strSlashFields += strApprovalField + "^";
						}
						else
						{
							strFieldList += strApprovalField + "\u0002";
							strValueList += strEmptyReason + "\u0002";
						}
					}
				}
				else
				{
					strFieldList += strApprovalField + "\u0002";
					strValueList += "\u0002";
				}
			}
		}
		else if (nSetType == 1 || nSetType == 2)
		{
			if (nSetType == 1)
			{
				if (bSetData)
				{
					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Enforce_HWPProxy2.PutStampFromFile(strApprovalField, g_strDownloadPath + strSignUrl, true);
						}
					}
				}
				else
				{
					Enforce_HWPProxy2.ClearStamp(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Enforce_HWPProxy2.ClearStamp(strApprovalField);
					break;
				}
			}

			if (nSetType == 1)
			{
				if (getApproverIsSigned(objApprover) == "Y")
				{
					var strSignDate = getApproverSignDate(objApprover);
					if (strSignDate != "")
					{
						var strDate = "";
						if (bSetData)
							strDate = getDateDisplay(strSignDate, g_strOption36);

						strApprovalField = "@" + strApprovalField;

						strFieldList += strApprovalField + "\u0002";
						strValueList += strDate + "\u0002";
					}
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					var strDate = "";
					strApprovalField = "@" + strApprovalField;

					strFieldList += strApprovalField + "\u0002";
					strValueList += strDate + "\u0002";

					break;
				}
			}
		}

		nSerialOrder++;
		objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
		strPrevRole = strRole;
		strPrevType = strType;
	}

	if (nSetType == 0)
	{
		if (isExistKey(objDic, FIELD_REPORT_DEPT) == true)
		{
			strFieldList += FIELD_REPORT_DEPT + "\u0002";
			strValueList += strReportDept + "\u0002";
		}

		if (strFieldSlots.length > 0 && strFieldSlots.charAt(strFieldSlots.length - 1) == '^')
			strFieldSlots = strFieldSlots.substring(0, strFieldSlots.length - 1);

		if (bIsForm == false)
			Enforce_HWPProxy2.MakeApprovalFields(strFieldSlots);

		if (g_bHwpDocumentModify == false)
			Enforce_HWPProxy2.SetReadOnly(false);

		if (bIsForm == true/* && g_strOption77 == "1"*/)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
				Enforce_HWPProxy2.SetCellShape(arrSlashField[i], "diagonalslashon");
		}

		if (g_bHwpDocumentModify == false)
			Enforce_HWPProxy2.SetReadOnly(true);

		if (g_strEditType == "0")		// drafter
		{
			if (isExistKey(objDic, FIELD_DRAFTER_NAME) == true)
			{
				var strDrafter = "";
				var objApprovalLine = getActiveApprovalLine();
				var nSerialOrder = 0;
				var objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
				while (objApprover != null)
				{
					var strRole = getApproverRole(objApprover);
					if (strRole == "0" || strRole == "1")
					{
						strDrafter = getApproverUserName(objApprover);
						break;
					}
					nSerialOrder++;
					objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
				}

				strFieldList += FIELD_DRAFTER_NAME + "\u0002";
				strValueList += strDrafter + "\u0002";
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);

		Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}

function setEnforceApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFields = Enforce_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Enforce_HWPProxy2.GetFieldText(strFields);
	var arrValue = strValues.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = arrValue[i];

	var bIsForm = isExistKey(objDic, FIELD_TITLE);

	var strFieldSlots = "", strFieldList = "", strValueList = "";
	var strEmptyFields = "";
	var strSlashFields = "";

	var nApprove = 0;
	var nAssist = 0;
	var bFirst = true;

	var objApprover = getFirstApprover(objApprovalLine);
	while (objApprover != null)
	{
		var strApprovalField = "";
		var objNextApprover = getNextApprover(objApprover);

		var strRole = getApproverRole(objApprover);
		if (strRole == "3" || strRole == "12" || strRole == "13" || strRole == "32" || strRole == "33")
		{
			nAssist++;
			strApprovalField = FIELD_COOPERATE;
			strApprovalField += nAssist;
		}
		else if (strRole == "21")
		{
			strApprovalField = FIELD_PRIOR + "1";
		}
		else if (strRole == "20")
		{
			strApprovalField = FIELD_CHARGER;
		}
		else if (strRole == "22" || strRole == "23")
		{
			nApprove++;
			strApprovalField = FIELD_PUBLIC + nApprove;
		}
		else if (strRole == "31")
		{
			strApprovalField = FIELD_DEPART_INSPECT + "1";
		}
		else
		{
			if (strRole != "8")
			{
				if (bFirst)
				{
					bFirst = false;
//					if (objNextApprover == null || getApproverRole(objNextApprover) == "8")
//						strApprovalField = FIELD_LAST + "1";
//					else
						strApprovalField = FIELD_DRAFTER + "1";
				}
				else
				{
//					if (objNextApprover == null || getApproverRole(objNextApprover) == "8")
//					{
//						strApprovalField = FIELD_LAST + "1";
//					}
//					else
//					{
						nApprove++;
						strApprovalField = FIELD_CONSIDER;
						strApprovalField += nApprove;
//					}
				}
			}
		}

		if (nSetType == 0)
		{
			// strRole != "4"(열람) 비교 제거함
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
//				if (isExistKey(objDic, strApprovalField) == true)
					strFieldSlots += strApprovalField + "^";

				// 이름
				var strApproverName = "#" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
				}
				else
				{
					var strUserName = "";
					if (bSetData)
					{
						strUserName = getApproverUserName(objApprover);
//						if (getApproverRepName(objApprover) != "")
//							strUserName = getApproverRepName(objApprover);
					}
					strFieldList += strApproverName + "\u0002";
					strValueList += strUserName + "\u0002";
				}

				var strDeptName = "";
				if (bSetData)
					strDeptName = getApproverDeptName(objApprover);

				// 개인부서명
				var strApproverDept = "$" + strApprovalField;
				strFieldList += strApproverDept + "\u0002";
				strValueList += strDeptName + "\u0002";

				// 직위 or 부서명(부서단위)
				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (isExistKey(objDic, strApproverDept) == false)
					{
						strFieldList += strApprovalType + "\u0002";
						strValueList += strDeptName + "\u0002";
					}
				}
				else
				{
					var strPosition = "";
					if (bSetData)
					{
						var arrOption37 = g_strOption37.split("^");
						strPosition = getUserPosition(objApprover, arrOption37[0]);

						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x02) > 0)
							strPosition = "⊙" + strPosition;
						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x01) > 0)
							strPosition = "★" + strPosition;
					}

					// 의견붙임
					var strExtraValue = "";
					var strApproverOpinion = getApproverOpinion(objApprover);
					if (strApproverOpinion != "")
						strExtraValue = OPINION_ATTACHED;

					strFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + strExtraValue + "\u0002";
				}

				// 직위약어 or 부서명(부서단위)
				var strApprovalTypeAbbr = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (isExistKey(objDic, strApproverDept) == false)
					{
						strFieldList += strApprovalTypeAbbr + "\u0002";
						strValueList += strDeptName + "\u0002";
					}
				}
				else
				{
					var strPositionAbbr = "";
					if (bSetData)
						strPositionAbbr = getApproverUserPositionAbbr(objApprover);

					strFieldList += strApprovalTypeAbbr + "\u0002";
					strValueList += strPositionAbbr + "\u0002";
				}

				// 공석사유
				var strEmptyReason = ""
				// 전대결등 표시
				var strExtraValue = "";
				if (strRole == "6")
				{
/*				
					if (g_strOption4 == "0")
					{
						strSlashFields += strApprovalField + "^";
					}
					else if (g_strOption4 == "1")
					{
						var strArbitFieldValue = "";
						if (bSetData)
							strArbitFieldValue = FIELD_ARBITRARY;
						strExtraValue += strArbitFieldValue;
					}
*/
					// 신사무관리 규정은 전대결의 표시방법이 다름
					var strArbitFieldValue = "";
					if (bSetData)
						strArbitFieldValue = FIELD_ARBITRARY;
					strExtraValue += strArbitFieldValue;
				}
				else if (strRole == "7")
				{
					var strProxyFieldValue = "";
					if (bSetData)
						strProxyFieldValue = FIELD_PROXY;
					strExtraValue += strProxyFieldValue;
					if (g_strOption5 == "0")
					{
						// do nothing
					}
					else if (g_strOption5 == "1")
					{
						strSlashFields += strApprovalField + "^";
					}
				}
				else if (strRole == "8")
				{
				}
				else if (strRole == "9")
				{
					strExtraValue += FIELD_REAR;
				}
				else if (strRole == "2" || strRole == "3")
				{
					strEmptyReason = getApproverEmptyReason(objApprover);
					if (strEmptyReason != "")
					{
						if (g_strOption78 == "1")
						{
							strSlashFields += strApprovalField + "^";
						}
						else
						{
							strExtraValue += strEmptyReason;
						}
					}
				}
				else
				{
				}

				// 날짜 필드에 먼저 세팅함
				if (strEmptyReason != "")
					strFieldList += strApprovalField + "\u0002";
				else
					strFieldList += "@" + strApprovalField + "\u0002";
				strValueList += strExtraValue + "\u0002";

/*
				// 의견붙임
				var strApproverOpinion = getApproverOpinion(objApprover);
				if (strApproverOpinion != "")
				{
					if (strExtraValue != "")
						strExtraValue += " ";
					strExtraValue += strApproverOpinion;
				}

				strFieldList += "%" + strApprovalField + "\u0002";
				strValueList += strExtraValue + "\u0002";
*/
			}
		}
		else if (nSetType == 1 || nSetType == 2)
		{
			if (nSetType == 1)
			{
				if (bSetData)
				{
					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Enforce_HWPProxy2.PutStampFromFile(strApprovalField, g_strDownloadPath + strSignUrl, true);
						}
						else
						{
							strFieldList += strApprovalField + "\u0002";
							var strApproverName = getApproverUserName(objApprover);
							if (getApproverRepName(objApprover) != "")
								strApproverName = getApproverRepName(objApprover);

							strValueList += strApproverName + "\u0002";
						}
					}
				}
				else
				{
					Enforce_HWPProxy2.ClearStamp(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Enforce_HWPProxy2.ClearStamp(strApprovalField);
					break;
				}
			}

			if (nSetType == 1)
			{
//				if (getApproverSerialOrder(objApprover) >= getApproverSerialOrder(getCompleteApproverInApprovalLine(objApprovalLine)))
				{
					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignDate = getApproverSignDate(objApprover);
						if (strSignDate != "")
						{
							var strDate = "";
							var strMonthNDay = "";
							if (bSetData)
							{
								strDate = getDateDisplay(strSignDate, g_strOption36);
								if (strSignDate.length > 0)
									strMonthNDay = strSignDate.substr(5,2) + "/" + strSignDate.substr(8,2);
							}

							strApprovalField = "@" + strApprovalField;
							strFieldList += strApprovalField + "\u0002";
							if ((getValue(objDic, strApprovalField)).indexOf(strMonthNDay) == -1)
								strValueList += getValue(objDic, strApprovalField) + " " + strMonthNDay + "\u0002";
							else
								strValueList += getValue(objDic, strApprovalField) + "\u0002";
							// 내부결재 관련 결재일자(FIELD_COMPLETE_DATE)와 등록일자필드(FIELD_REGIST_DATE) 추가
							if (isExistKey(objDic, FIELD_REGIST_DATE))
							{
								strFieldList += FIELD_REGIST_DATE + "\u0002";
								strValueList += strDate + "\u0002";
							}
							if (isExistKey(objDic, FIELD_COMPLETE_DATE))
							{
								strFieldList += FIELD_COMPLETE_DATE + "\u0002";
								strValueList += strDate + "\u0002";
							}
						}
					}
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					var strDate = "";
					strApprovalField = "@" + strApprovalField;

					strFieldList += strApprovalField + "\u0002";
					strValueList += strDate + "\u0002";

					break;
				}
			}
		}

		objApprover = objNextApprover;
	}

	if (nSetType == 0)
	{
		if (strFieldSlots.length > 0 && strFieldSlots.charAt(strFieldSlots.length - 1) == '^')
			strFieldSlots = strFieldSlots.substring(0, strFieldSlots.length - 1);

		if (bIsForm == false)
			Enforce_HWPProxy2.MakeApprovalFields(strFieldSlots);
/*
		if (g_bHwpDocumentModify == false)
			Enforce_HWPProxy2.SetReadOnly(false);

		if (bIsForm == true)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
				Enforce_HWPProxy2.SetCellShape(arrSlashField[i], "diagonalslashon");
		}

		if (g_bHwpDocumentModify == false)
			Enforce_HWPProxy2.SetReadOnly(true);
*/
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);

		Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}

function setEnforceApprovalFlow()
{
	setEnforceApprovalField(0, true, "");
}

function setEnforceApprovalSign()
{
	setEnforceApprovalField(1, true, "");
}

function clearEnforceApprovalFlow()
{
	setEnforceApprovalField(0, false, "");
}

function clearEnforceApprovalSign()
{
	setEnforceApprovalField(1, false, "");
}

function editEnforceDocument(bModify)
{
	var bReplace = true;
	var nEditMode = (bModify ? 2 : 1);
	var nMenuSetID = 1;
//	var nWindowPos = (bModify ? 0 : 2);
	var nWindowPos = -1;

	var strFileName = getAttachFileName(getEnforceFileObj(g_nEnforceProposal));

	g_strHwpEnforceTitle = "시행문서";

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		if (getLocalFileSize(strLocalPath) > 0)
		{
			Enforce_HWPProxy2.OpenDocumentEx(g_strHwpEnforceTitle, strLocalPath, bReplace, nEditMode, nMenuSetID, getEnforceMenuSet(), nWindowPos, g_bHideParent);
		}
		else
		{
			alert(ALERT_FIND_ENFORCE_DOCUMENT_ERROR);
			return;
		}
	}
	else
	{
		Enforce_HWPProxy2.NewDocumentEx(g_strHwpEnforceTitle, bReplace, nEditMode, nMenuSetID, getEnforceMenuSet(), nWindowPos, g_bHideParent);
	}

	Enforce_HWPProxy2.SetVisible(true);
	Enforce_HWPProxy2.SetDocumentViewRatio("width","");
	Enforce_HWPProxy2.Focus();

	return true;
}

function saveEnforceDocument(bIncludeSign)
{
	var strTitle = getTitle(1);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("<") != -1 || strTitle.indexOf(">") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	var strFilePath = Enforce_HWPProxy2.OpenHwpFileDialog(false, strTitle);
	if (strFilePath == "")
		return;

	if (!bIncludeSign)
		clearEnforceApproveFlow()
	Enforce_HWPProxy2.SaveDocument(strFilePath, false);
	if (!bIncludeSign)
		restoreEnforceApproveFlow();
}

function saveEnforceDocumentAs(strFilePath, bIncludeSign)
{
	if (!bIncludeSign)
		clearEnforceApproveFlow();
	Enforce_HWPProxy2.SaveDocument(strFilePath, false);
	if (!bIncludeSign)
		restoreEnforceApproveFlow();
}

function isEnforceModified()
{
	return g_bIsEnforceModified;
}

function setEnforceModified(bEnforceModified)
{
	g_bIsEnforceModified = bEnforceModified;
}

function setEnforceProposal(nCaseNumber)
{
	g_nEnforceProposal = nCaseNumber;
}

function getEnforceProposal()
{
	return g_nEnforceProposal;
}

function printEnforce()
{
	Enforce_HWPProxy2.PrintDocument();
}

function setArbitProxyApproverToForm()
{
	var nOption8 = parseInt(g_strOption8);
	var strFieldName = "";
	var strStampName = "";
	var strValue = "";
	var strSignUrl = "";

	if (Enforce_HWPProxy2.IsExistField(FIELD_REAL_NAME))
		strFieldName = FIELD_REAL_NAME;
	if (Enforce_HWPProxy2.IsExistField(FIELD_REAL_STAMP))
		strStampName = FIELD_REAL_STAMP;

	if (strFieldName != "" || strStampName != "")
	{
		var strEnforceBound = getEnforceBound(g_nEnforceProposal);

		// 건보: 대내, 대외, 대내외 시행문이면서, 수신처에 외부기관 존재하지 않을 때
		if ((strEnforceBound == "I" || strEnforceBound == "O" || strEnforceBound == "C") && !isOuterRecipExist(g_nEnforceProposal))
		{
			var objApprovalLine = getActiveApprovalLine();
			
			if (objApprovalLine != null)
			{
				var strRole = "6";
				var objApprover = getApproverByRole(objApprovalLine, strRole);
				if (objApprover == null)
				{
					strRole = "7";
					objApprover = getApproverByRole(objApprovalLine, strRole);
				}

				if (objApprover != null)
				{
					if (strRole == "6" || strRole == "7")
					{
//						strValue = "(";

						if (strRole == "6")
						{
							strValue += ROLE_ARBITRARY;
						}
						else if (strRole == "7")
						{
							strValue += ROLE_PROXY;
						}

						var strPosition = "";
						if ((nOption8 & 0x10) != 0)		// 직급
							strPosition = getApproverUserLevel(objApprover);
						if ((nOption8 & 0x20) != 0)		// 직위
							strPosition = getApproverUserPosition(objApprover);
						if ((nOption8 & 0x40) != 0)		// 직책
							strPosition = getApproverUserDuty(objApprover);
						if ((nOption8 & 0x80) != 0)		// 통합직함
							strPosition = getApproverUserTitle(objApprover);
						strValue += " " + strPosition;

						if (nOption8 & 0x02 != 0)		// 성명
						{
							strValue += " " + getApproverUserName(objApprover);
						}
						else
						{
							// don't use name
						}

//						strValue += ")";
						strSignUrl = getApproverSignFileName(objApprover);
					}
				}
			}
		}

		if (strFieldName != "")
			Enforce_HWPProxy2.PutFieldText(strFieldName, strValue);

		if (strStampName != "")
		{
			if ((nOption8 & 0x01) != 0 && strSignUrl != "")		// 서명인 사용: 0x01
			{
				if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
					Enforce_HWPProxy2.PutStampFromFile(strStampName, g_strDownloadPath + strSignUrl, true);
			}
			else
				Enforce_HWPProxy2.ClearStamp(strStampName);
		}
	}
}

function getFieldData(strFieldList)
{
	var strValueList = Enforce_HWPProxy2.GetFieldText(strFieldList);
	return strValueList;
}

function setFieldData(strFieldList, strValueList)
{
	var arrField = strFieldList.split("\u0002");
	var arrValue = strValueList.split("\u0002");
	for (var i = 0; i < arrField.length; i++)
	{
		if (arrField[i] == FIELD_TREATMENT)
		{
			var strTreatment = arrValue[i];
			setTreatment(strTreatment, g_nEnforceProposal);
		}
		else if (arrField[i] == FIELD_SENDER_AS + "#1")
		{
			var strSenderAs = arrValue[i];
			setSenderAs(strSenderAs, g_nEnforceProposal);
		}
	}

	Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);

	saveEnforceFile(g_nEnforceProposal)
	setEnforceModified(true);
}

function updateEnforcePostSendField()
{
	// 문서번호, 시행일자
	var strFieldList = "";
	var strValueList = "";

	if (Enforce_HWPProxy2.IsExistField(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (Enforce_HWPProxy2.IsExistField(FIELD_ENFORCE_DATE) == true)
	{
		var strEnforceDate = getEnforceDate();
		if (strEnforceDate == "")
		{
			var strDocCategory = getDocCategory(nIndex);
			if (strDocCategory == "I")
			{
				strEnforceDate = getCompleteDate();
			}
		}
		var strDate = "";
		if (strEnforceDate != "")
			strDate = getDateDisplay(strEnforceDate, g_strOption36);

		strFieldList += FIELD_ENFORCE_DATE + "\u0002";
		strValueList += strDate + "\u0002";
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Enforce_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}

function changeHwpEnforceMenu()
{
	Enforce_HWPProxy2.ModifyCommandUI(1, "1", getEnforceMenuSet());
}

function setHwpEnforceVisible()
{
	var bVisible = (g_nFrameScale == 1 || g_nFrameScale == 2);
	Enforce_HWPProxy2.SetVisible(bVisible);
	if (bVisible = true)
		Enforce_HWPProxy2.Focus();
}

function moveHwpEnforceWindow() {
	var nPos;
	if (g_nFrameScale == 0 || g_nFrameScale == 1)
		nPos = 0;
	else if (g_nFrameScale == 2)
		nPos = 2;
	Enforce_HWPProxy2.MoveDocumentWindow(nPos);
}

function onEnforceHwpManualSeal(strStampName)
{
//	saveEnforceFile(g_nEnforceProposal)
//	setEnforceModified(true);
}

function onEnforceHwpProxyCommand(nCommandID)
{
	switch (nCommandID)
	{
	case 0:		// 사용못함 - 한글97 menu 버그, 0 번을 사용하면 메뉴가 생기지 않거나 오작동함.
		break;
	case 1:		// 승인취소
		break;
	case 2:		// 경로수정
		break;
	case 3:		// 경로지정
		break;
	case 4:		// 감사양식
		break;
	case 5:		// 공람게시
		break;
	case 6:		// 공람
		break;
	case 7:		// 관인
		onClickStamp(1);
		break;
	case 8:		// 관인생략
		onClickStamp(3);
		break;
	case 9:		// 기안회수
		break;
	case 10:	// 문서회수
		onCallbackEnforce();
		break;
	case 11:	// 우편번호
		break;
	case 12:	// 담당지정
		break;
	case 13:	// 암호화
		onCertificateRecipient();
		break;
	case 14:	// 문서수정(시행문서)
		onModifyEnforce();
		break;
	case 15:	// 결재보류
		break;
	case 16:	// 문서정보
		break;
	case 17:	// 원문보기
		break;
	case 18:	// 시행문서보기
		break;
	case 19:	// 반대
		break;
	case 20:	// 반려
		break;
	case 21:	// 반송
		break;
	case 22:	// 발송
		if (g_bSendEnforceHold == true)
			return;
		g_bSendEnforceHold = true;
		onSendEnforce();
		break;
	case 23:	// 배부
		break;
	case 24:	// 상세정보
		onViewDocDetail();
		break;
	case 25:	// 상신
		break;
	case 26:	// 보류
		break;
	case 27:	// 서명
		break;
	case 28:	// 서명생략
		onClickStamp(2);
		break;
	case 29:	// 서명인
		onClickStamp(0);
		break;
	case 30:	// 선람
		break;
	case 31:	// 선람지정
		break;
	case 32:	// 수신지정
		onSetRecipPart();
		break;
	case 33:	// 수신수정
		break;
	case 34:	// 문서분류
		break;
	case 35:	// 시행변환
		break;
	case 36:	// 심사반송
		break;
	case 37:	// 담당재지정
		break;
	case 38:	// 심사서명
		break;
	case 39:	// 보내기
		break;
	case 40:	// 심사자지정
		break;
	case 41:	// 안추가
		break;
	case 42:	// 안삭제
		break;
	case 43:	// 요약작성
		break;
	case 44:	// 요약첨부
		break;
	case 45:	// 의견작성
		break;
	case 46:	// 의견조회
		break;
	case 47:	// 이송
		break;
	case 48:	// 재발송
		onResendEnforce();
		break;
	case 49:	// 경유
		break;
	case 50:	// 전대결표시
		break;
	case 51:	// 접수
		break;
	case 52:	// 지시사항
		break;
	case 53:	// 첨부
		onAttachFiles();
		break;
	case 54:	// 공개여부
		break;
	case 55:	// 결재문서보기
		onViewBodyDoc();
		break;
	case 56:	// 대장등록
		break;
	case 57:	// 기록물철
		break;
	case 58:	// 내려받기
		saveEnforceDocument(false);
		break;
	case 59:	// 인쇄
		printEnforce();
		break;
	case 60:	// 닫기
		onCloseHwp(false);
		break;
	case 61:	// 수정완료
		if (g_strEditType == "11")
			onApplyEnforceModify();
		else if (g_strEditType == "13" || g_strEditType == "16")
			onApplyRejectEnforceModify();
		break;
	case 62:	// 수정취소
		if (g_strEditType == "11")
			onCancelEnforceModify();
		else if (g_strEditType == "13" || g_strEditType == "16")
			onCancelRejectEnforceModify();
		break;
	case 63:	// 재기안
		break;
	case 64:	// 시행처리
		onEnforceApprove();
		break;
	case 65:	// 열기
		break;
	case 66:	// 양식변경
		onChangeForm();
		break;
	case 67:	// 시행범위변경
		onChangeDocInfo();
		break;
	case 68:	// 안건변경
		onChangeProposal();
		break;
	case 69:	// 결재정보
		break;
	case 70:	// 공람확인
		break;
	case 71:	// 시행생략
		break;
	case 72:	// 수정기안
		break;
	case 73:	// 접수확인
		onConfirmAccept();
		break;
	case 74:	// 하단정리
		break;
	case 75:	// 심사발송
		break;
	case 76:	// 재배부
		break;
	case 77:	// 이전문서
		break;
	case 78:	// 다음문서
		break;
	case 79:	// 기안자지정
		break;
	case 80:	// 게시삭제
		break;
	case 81:	// (게시)정보수정
		break;
	case 82:	// 감사의견
		break;
	case 83:	// 게시로
		break;
	case 84:	// 필드수정(시행문서)
		onSetEtcData();
		break;
	}
}

// icaha, document switching
onEnforceAccessLoadingCompleted();
