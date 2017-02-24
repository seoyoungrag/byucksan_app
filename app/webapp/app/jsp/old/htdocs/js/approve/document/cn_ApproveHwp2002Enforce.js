var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";
var g_bIsEnforceModified = false;
var g_bHwpEnforceModify;
var g_strLogoName = "";
var g_strSymbolName = "";

function getEnforceObjectHTML()
{
	var strHTML = "<OBJECT id='Enforce_HwpCtrl' width='100%' height='100%' classid='CLSID:BD9C32DE-3155-4691-8972-097D53B10052'>";
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
	loadEnforceDocument();
}

function onUnloadEnforceDocument()
{
	closeEnforceDocument();
}

function loadEnforceDocument()
{
	// 한글 OCX 보안 다이얼로그가 나타나지 않도록 합니다.
	//	Enforce_HwpCtrl.RegisterModule("FilePathCheckDLL", "HwpLocalFileAccess");

//	g_bHwpEnforceModify = (g_strEditType == "0");
	g_bHwpEnforceModify = false;
	var nEditMode = (g_bHwpEnforceModify ? 0x01 : 0);
//	g_bIsEnforceModified = (g_strEditType == "0");

	var objEnforceFile = getEnforceFileObj(g_nEnforceProposal);
	if (objEnforceFile != null && (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13"))
		removeExtendAttachInfo(strFileName);
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
			var strLocalPath = g_strDownloadPath + strFileName;
			if (getLocalFileSize(strLocalPath) > 0)
			{
				Enforce_HwpCtrl.Open(strLocalPath, "", "lock:FALSE;versionwarning:TRUE");
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
			// no enforce file
			return;
		}
	}

	if (g_strOption83 == "0")
	{
		var objApprover = getCompleteApprover();
		var strSignFileName = getApproverSignFileName(objApprover);
		setEnforceStamp(0, strSignFileName);
	}

	restoreEnforceApproveFlow();

	if (g_strEditType == "0")
		editDocument(false);

	if (Document_HwpCtrl.FieldExist(LABEL_EXIST_OPINION) && getFieldText(LABEL_EXIST_OPINION) != "")
	{
		// 의견작성 표가 없을경우 새로 생성
		if (!Enforce_HwpCtrl.FieldExist(LABEL_EXIST_OPINION))
		{
			if (Enforce_HwpCtrl.FieldExist(FIELD_BODY))
				Enforce_HwpCtrl.MoveToField(FIELD_BODY, false, false, false);
			else
				Enforce_HwpCtrl.MovePos(3);

			Enforce_HwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/OpinionTbl.hwp");
		}
		var strFieldOpinion = getFieldText(LABEL_EXIST_OPINION)
		Enforce_HwpCtrl.PutFieldText(LABEL_EXIST_OPINION + "\u0002", strFieldOpinion + "\u0002");
	}

	changeEditMode(Enforce_HwpCtrl, nEditMode);
	Enforce_HwpCtrl.focus();
}

function closeEnforceDocument()
{
}

function isAutoSetField(strFieldName) {
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
		strFieldName == FIELD_REAL_NAME ||
		strFieldName == FIELD_TREATMENT ||
		strFieldName.indexOf(FIELD_TITLE) == 0 ||
		strFieldName.indexOf(FIELD_SENDER_AS + "#") == 0 ||
		strFieldName.indexOf(FIELD_RECIPIENT_REFER + "#") == 0)
		return true;
	return false;
}

function makeEnforceDocument(strEnforceFormUrl, nCaseNumber)
{
	var nEditMode = (g_bHwpEnforceModify ? 0x01 : 0);

	var objEnforceFile = null;

	if (strEnforceFormUrl == "")
		return objEnforceFile;

	var strDocCategory = getDocCategory(nCaseNumber);

	if (strDocCategory != "E" && strDocCategory != "W")
		return objEnforceFile;

	objEnforceFile = getEnforceFileObj(nCaseNumber)
	if (objEnforceFile == null)
	{
		if (strEnforceFormUrl != "")
		{
			if (getLocalFileSize(g_strDownloadPath + strEnforceFormUrl) > 0)
			{
				Enforce_HwpCtrl.Open(g_strDownloadPath + strEnforceFormUrl, "", "lock:FALSE;versionwarning:TRUE");
				setMakeEnforceDataToXml(nCaseNumber);
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

//			setXmlDataToForm();
		}

		copyProposal(Document_HwpCtrl, nCaseNumber);
		pasteProposal(Enforce_HwpCtrl);

		var strSrcFields = Document_HwpCtrl.GetFieldList();
		var arrSrcField = strSrcFields.split("\u0002");
		var nSrcFieldCount = arrSrcField.length;

		var strFieldList = "", strValueList = "";
		for (i = 0; i < nSrcFieldCount; i++)
		{
			if (arrSrcField[i].indexOf(FIELD_LAST) == -1 &&
				Enforce_HwpCtrl.FieldExist(arrSrcField[i]) && !isAutoSetField(arrSrcField[i]))
			{
				strFieldList += arrSrcField[i] + "\u0002";
				strValueList += Document_HwpCtrl.GetFieldText(arrSrcField[i]) + "\u0002";
			}
		}

		strField = FIELD_RECIPIENT;
		if (Enforce_HwpCtrl.FieldExist(strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += Document_HwpCtrl.GetFieldText(strField + (nCaseNumber == 1 ? "" : nCaseNumber)) + "\u0002";
		}

		strField = FIELD_REFERER;
		if (Enforce_HwpCtrl.FieldExist(strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += Document_HwpCtrl.GetFieldText(strField + (nCaseNumber == 1 ? "" : nCaseNumber)) + "\u0002";
		}

		strField = FIELD_RECIPIENT_REFER + "#1";
		if (Enforce_HwpCtrl.FieldExist(strField))
		{
			strFieldList += strField + "\u0002";
			strValueList += Document_HwpCtrl.GetFieldText(FIELD_RECIPIENT_REFER + "#" + nCaseNumber) + "\u0002";
		}

		strField = FIELD_SENDER_AS + "#1";
		if (Enforce_HwpCtrl.FieldExist(strField))
		{
			var strSrcSenderAsField = "";
			if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#" + nCaseNumber))
				strSrcSenderAsField = FIELD_SENDER_AS + "#" + nCaseNumber;
/*
			else if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#" + getBodyCount()))
				strSrcSenderAsField = FIELD_SENDER_AS + "#" + getBodyCount();
*/
			strFieldList += strField + "\u0002";
			strValueList += Document_HwpCtrl.GetFieldText(strSrcSenderAsField) + "\u0002";
		}

		if (strFieldList.length > 0)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
		}

/*
		setEnforceXmlDataToForm();
		setEnforceRecipientsToForm();
		setArbitProxyApproverToForm();
*/
		if (g_strOption45 == "1")
		{
			clearEnforceApprovalFlow();
			clearEnforceApprovalSign();
		}

//		objEnforceFile = setEnforceFileInfo(nCaseNumber);
		setEnforceOrganImage(nCaseNumber);
		objEnforceFile = getEnforceFileObj(nCaseNumber);
		if (objEnforceFile == null)
			objEnforceFile = setEnforceFileInfo(nCaseNumber);

		saveEnforceFile(g_nEnforceProposal);
		setEnforceModified(true);
	}

	// 기안문과 시행문의 위치를 가장 위로 올린다.
	Document_HwpCtrl.MovePos(2);
	Enforce_HwpCtrl.MovePos(2);

	return objEnforceFile;
}

function setMakeEnforceDataToXml(nCaseNumber)
{
	var nFileSize = getLocalFileSize(g_strDownloadPath + g_strLogoName);
	if (nFileSize > 0)
	{
		var objExtendAttach = addExtendAttachInfo(nCaseNumber+"", "Logo", g_strLogoName, nFileSize+"", g_strDownloadPath + g_strLogoName);
		var objClassify = addDataNode(objExtendAttach, "CLASSIFY");
		setChildNodeData(objExtendAttach, "CLASSIFY", "OrganImage");
		var objSubDiv = addDataNode(objExtendAttach, "SUBDIV");
		setChildNodeData(objExtendAttach, "SUBDIV", "Logo");
	}

	var nFileSize = getLocalFileSize(g_strDownloadPath + g_strSymbolName);
	if (nFileSize > 0)
	{
		var objExtendAttach = addExtendAttachInfo(nCaseNumber+"", "Symbol", g_strSymbolName, nFileSize+"", g_strDownloadPath + g_strSymbolName);
		var objClassify = addDataNode(objExtendAttach, "CLASSIFY");
		setChildNodeData(objExtendAttach, "CLASSIFY", "OrganImage");
		var objSubDiv = addDataNode(objExtendAttach, "SUBDIV");
		setChildNodeData(objExtendAttach, "SUBDIV", "Symbol");
	}
}

function setEnforceOrganImage(nCaseNumber)
{
	var objExtendAttach = getFirstExtendAttach();
	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "OrganImage")
		{
			var strCaseNumber = getAttachCaseNumber(objExtendAttach);
			if (strCaseNumber == nCaseNumber + "")
			{
				var strSubDiv = getAttachSubDiv(objExtendAttach);
				var strFieldName = "";
				if (strSubDiv == "Logo")
					strFieldName = FIELD_LOGO_IMAGE;
				else if (strSubDiv == "Symbol")
					strFieldName = FIELD_SYMBOL_IMAGE;

				if (strFieldName != "")
				{
					var strFileName = getAttachFileName(objExtendAttach);
					var strFilePath = g_strDownloadPath + strFileName;

					if (Enforce_HwpCtrl.FieldExist(strFieldName))
					{
						if (getLocalFileSize(strFilePath) > 0)
							putStampFromFileWithSize(Enforce_HwpCtrl, strFieldName, strFilePath, true, 20, 20);
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function setEnforceXmlDataToForm()
{
	var strFieldList = "", strValueList = "";

	var nBodyCount = getBodyCount();

	if (Enforce_HwpCtrl.FieldExist(FIELD_TITLE) == true)
	{
		strFieldList += FIELD_TITLE + "\u0002";
		strValueList += getTitle(g_nEnforceProposal) + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = getDraftConserve();
		strFieldList += FIELD_CONSERVE_TERM + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_RECP_CONSERVE) == true)
	{
		var strConserveTerm = getEnforceConserve();
		strFieldList += FIELD_RECP_CONSERVE + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_PUBLIC_BOUND) == true)
	{
//		var strPublicBound = getPublicLevel();
		var strPublicBound = getPublication(getPublicLevel());
		strFieldList += FIELD_PUBLIC_BOUND + "\u0002";
		strValueList += strPublicBound + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_ENFORCE_DATE) == true)
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

	if (Enforce_HwpCtrl.FieldExist(FIELD_RECEIVE_DATE) == true)
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

		if (Enforce_HwpCtrl.FieldExist(FIELD_RECEIVE_TIME) == true)
		{
			strFieldList += FIELD_RECEIVE_TIME + "\u0002";
			strValueList += strTime + "\u0002";
		}
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "0")
		{
			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strReceiveNumber + "\u0002";
		}
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (Enforce_HwpCtrl.FieldExist(FIELD_TREATMENT) == true)
	{
		var strDocCategory = getDocCategory(g_nEnforceProposal);
		strFieldList += FIELD_TREATMENT + "\u0002";
		if (strDocCategory == "E")
		{
			var strTreatment = getTreatment(g_nEnforceProposal);
			strValueList += strTreatment + "\u0002";
			setBorderLine(Enforce_HwpCtrl, FIELD_TREATMENT, true);
		}
		else
		{
			strValueList += "\u0002";
			setBorderLine(Enforce_HwpCtrl, FIELD_TREATMENT, false);
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}

//	setEnforceRecipientsToForm();
}

function setEnforceRecipientsToForm()
{
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

		if (Enforce_HwpCtrl.FieldExist(strDeptField) && Enforce_HwpCtrl.FieldExist(strRefField))
		{
			// 수신필드, 참조필드가 각각 있는 경우
			strFieldList += strDeptField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += strDisplayAs + "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strDeptName + "\u0002";
			else
				strValueList += RECIPIENT_REFER + "\u0002";

			strFieldList += strRefField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strRefName + "\u0002";
			else
			{
				strValueList += "\u0002";
			}
		}
		else if (Enforce_HwpCtrl.FieldExist(strDeptField))
		{
			// 수신필드만 있는 경우
			strFieldList += strDeptField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += strDisplayAs + "\u0002";
			else if (nRecipientCount == 1)
			{
				if (strRefName != "")
					strValueList += strDeptName + "(" + strRefName + ")" + "\u0002";
				else
					strValueList += strDeptName + "\u0002";
			}
			else
				strValueList += RECIPIENT_REFER + "\u0002";
		}
		else if (Enforce_HwpCtrl.FieldExist(strRefField))
		{
			// 참조필드만 있는 경우
			strFieldList += strRefField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strRefName + "\u0002";
			else
			{
				strValueList += "\u0002";

//					if (getRefRecipientCount(objRecipGroup) > 0)
//						strValueList += RECIPIENT_REFER + "\u0002";
//					else
//						strValueList += "\u0002";
			}
		}

		if (Enforce_HwpCtrl.FieldExist(strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			if (nRecipientCount < 2)
				strValueList += "\u0002";
			else
				strValueList += /*LABEL_RECIPIENT + ": " + */strDeptRefName + "\u0002";
		}
	}
	else		// no RecipGroup
	{
		if (Enforce_HwpCtrl.FieldExist(strDeptField) && Enforce_HwpCtrl.FieldExist(strRefField))
		{
			// 수신필드, 참조필드가 각각 있는 경우
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nCaseNumber);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";

			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}
		else if (Enforce_HwpCtrl.FieldExist(strDeptField))
		{
			// 수신필드만 있는 경우
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nCaseNumber);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";
		}
		else if (Enforce_HwpCtrl.FieldExist(strRefField))
		{
			// 참조필드만 있는 경우
			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}

		if (Enforce_HwpCtrl.FieldExist(strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			strValueList += "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}

	saveEnforceFile(g_nEnforceProposal);
	setEnforceModified(true);
}

function appendEnforceRecipientsToForm(nCaseNumber)
{
	var strFieldList = "";
	var strValueList = "";

	if (nCaseNumber == 1)
	{
		strDeptField = FIELD_RECIPIENT;
		strRefField = FIELD_REFERER;
		strDeptRefField = FIELD_RECIPIENT_REFER + "#1";
	}
	else
	{
		strDeptField = FIELD_RECIPIENT + nCaseNumber;
		strRefField = FIELD_REFERER + nCaseNumber;
		strDeptRefField = FIELD_RECIPIENT_REFER + "#" + nCaseNumber;
	}

	if (Document_HwpCtrl.FieldExist(strDeptField) && Enforce_HwpCtrl.FieldExist(FIELD_RECIPIENT))
	{
		strFieldList += FIELD_RECIPIENT + "\u0002";
		strValueList += Document_HwpCtrl.GetFieldText(strDeptField) + "\u0002";
	}
	if (Document_HwpCtrl.FieldExist(strRefField) && Enforce_HwpCtrl.FieldExist(FIELD_REFERER))
	{
		strFieldList += FIELD_REFERER + "\u0002";
		strValueList += Document_HwpCtrl.GetFieldText(strRefField) + "\u0002";
	}
	if (Document_HwpCtrl.FieldExist(strDeptRefField) && Enforce_HwpCtrl.FieldExist(FIELD_RECIPIENT_REFER + "#1"))
	{
		strFieldList += FIELD_RECIPIENT_REFER + "#1" + "\u0002";
		strValueList += Document_HwpCtrl.GetFieldText(strDeptRefField) + "\u0002";
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}

	saveEnforceFile(nCaseNumber);
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
	if (saveHwpDocument(Enforce_HwpCtrl, g_strDownloadPath + strFileName, false) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objEnforceFile, strFileSize);
	}

	restoreEnforceApproveFlow();
//	Enforce_HwpCtrl.IsModified = 0;
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
	if (g_bHwpEnforceModify == false)
		Enforce_HwpCtrl.EditMode = 0x01;

	clearEnforceApprovalFlow();
	clearEnforceApprovalSign();
	clearEnforceExaminationSign();
	clearEnforceApprovalSeal();

	if (g_bHwpEnforceModify == false)
		Enforce_HwpCtrl.EditMode = 0;
}

function restoreEnforceApproveFlow()
{
	if (g_bHwpEnforceModify == false)
		Enforce_HwpCtrl.EditMode = 0x01;

	setEnforceApprovalFlow();
	setEnforceApprovalSign();
	setEnforceExaminationSign();
	// 20030520, manual seal...
	restoreEnforceApprovalSeal();
//	setEnforceApprovalSeal();

	if (g_bHwpEnforceModify == false)
		Enforce_HwpCtrl.EditMode = 0;
}

// 수동 날인시 저장 요구됨
function setEnforceApprovalSeal()
{
	var bModified = false;
	var objExtendAttach = getFirstExtendAttach();

	var strFormUsage = getFormUsage();

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

//					if (getLocalFileSize(strFilePath) > 0)
//					{
						bModified = true;
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
/*
							// 20030520, manual seal...
							if (confirm(CONFIRM_AUTO_SEAL) == true)
							{
*/
								var strReplaceField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
								if (Enforce_HwpCtrl.FieldExist(strFieldName) || Enforce_HwpCtrl.FieldExist(strReplaceField) ||
									Enforce_HwpCtrl.FieldExist(FIELD_STAMP) || Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
								{
									if (Enforce_HwpCtrl.FieldExist(strFieldName))
									{
									}
									else if (Enforce_HwpCtrl.FieldExist(strReplaceField))
										Enforce_HwpCtrl.RenameField(strReplaceField, strFieldName);
									else if (Enforce_HwpCtrl.FieldExist(FIELD_STAMP))
										Enforce_HwpCtrl.RenameField(FIELD_STAMP, strFieldName);
									else if (Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
										Enforce_HwpCtrl.InsertSealHwp(strFieldName);

									if (Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
										adjustSealPos(Enforce_HwpCtrl, strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

									putStampFromFile(Enforce_HwpCtrl, strFieldName, strFilePath, true);
								}
								else
								{
									alert(ALERT_CLICK_POSITION_TO_STAMP);
									putSealFromFileEx(Enforce_HwpCtrl, strFieldName, strFilePath);
									setEnforceModified(true);
								}
/*
							}
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								putSealFromFileEx(Enforce_HwpCtrl, strFieldName, strFilePath);
								setEnforceModified(true);
							}
*/
						}
						else if (strFieldName == FIELD_DEPT_OMIT_STAMP || strFieldName == FIELD_COMPANY_OMIT_STAMP ||
								strFieldName == FIELD_ENFORCE_OMIT_STAMP)
						{
							var strReplaceField = "";
							if (strFieldName == FIELD_DEPT_OMIT_STAMP)
								strReplaceField = FIELD_COMPANY_OMIT_STAMP;
							else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
								strReplaceField = FIELD_DEPT_OMIT_STAMP;
							strReplaceField = (strFieldName == FIELD_DEPT_OMIT_STAMP ? FIELD_COMPANY_OMIT_STAMP : FIELD_DEPT_OMIT_STAMP);
							if (Enforce_HwpCtrl.FieldExist(strFieldName) || Enforce_HwpCtrl.FieldExist(strReplaceField) ||
								Enforce_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
							{
								if (Enforce_HwpCtrl.FieldExist(strReplaceField))
									Enforce_HwpCtrl.RenameField(strReplaceField, strFieldName);
								else if (Enforce_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
									Enforce_HwpCtrl.RenameField(FIELD_OMIT_STAMP, strFieldName);

								if (strFileName == "")
									Enforce_HwpCtrl.PutFieldText(strFieldName, strFieldName + "\u0002");
								else
									putStampFromFile(Enforce_HwpCtrl, strFieldName, strFilePath, true);
							}
/*
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								putOmitSealFromFileEx(Enforce_HwpCtrl, strFieldName, strFilePath);
								setEnforceModified(true);
							}
*/
						}
//					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}

//	setArbitProxyApproverToForm();
}

// 20030520, manual seal...
function restoreEnforceApprovalSeal()
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
						if (Enforce_HwpCtrl.FieldExist(strFieldName))
						{
							putStampFromFile(Enforce_HwpCtrl, strFieldName, strFilePath, true);
						}
						// 자동으로 날인된 경우
						else
						{
							if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
							{
								var strReplaceField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
								if (Enforce_HwpCtrl.FieldExist(strReplaceField) ||
									Enforce_HwpCtrl.FieldExist(FIELD_STAMP) || Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
								{
									if (Enforce_HwpCtrl.FieldExist(strReplaceField))
										Enforce_HwpCtrl.RenameField(strReplaceField, strFieldName);
									else if (Enforce_HwpCtrl.FieldExist(FIELD_STAMP))
										Enforce_HwpCtrl.RenameField(FIELD_STAMP, strFieldName);
									else if (Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
										Enforce_HwpCtrl.InsertSealHwp(strFieldName);

									if (Enforce_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
										adjustSealPos(Enforce_HwpCtrl, strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);
									putStampFromFile(Enforce_HwpCtrl, strFieldName, strFilePath, true);
								}
							}
							else if (strFieldName == FIELD_DEPT_OMIT_STAMP || strFieldName == FIELD_COMPANY_OMIT_STAMP ||
									strFieldName == FIELD_ENFORCE_OMIT_STAMP)
							{
								var strReplaceField = "";
								if (strFieldName == FIELD_DEPT_OMIT_STAMP)
									strReplaceField = FIELD_COMPANY_OMIT_STAMP;
								else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
									strReplaceField = FIELD_DEPT_OMIT_STAMP;
								strReplaceField = (strFieldName == FIELD_DEPT_OMIT_STAMP ? FIELD_COMPANY_OMIT_STAMP : FIELD_DEPT_OMIT_STAMP);
								if (Enforce_HwpCtrl.FieldExist(strReplaceField) || Enforce_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
								{
									if (Enforce_HwpCtrl.FieldExist(strReplaceField))
										Enforce_HwpCtrl.RenameField(strReplaceField, strFieldName);
									else if (Enforce_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
										Enforce_HwpCtrl.RenameField(FIELD_OMIT_STAMP, strFieldName);
									putStampFromFile(Enforce_HwpCtrl, strFieldName, strFilePath, true);
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
		Enforce_HwpCtrl.ClearStamp(strFieldName);
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

				clearStamp(Enforce_HwpCtrl, strFieldName);
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
	strFormUsage = "1";

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
	var bIsForm = Enforce_HwpCtrl.FieldExist(FIELD_TITLE);

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
//				if (Enforce_HwpCtrl.FieldExist(strApprovalField) == true)
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
					if (Enforce_HwpCtrl.FieldExist(strApproverDept) == false)
					{
						strFieldList += strApprovalType + "\u0002";
						strValueList += strDeptName + "\u0002";
					}
				}
				else
				{
					var strPosition = "";
					if (bSetData)
						strPosition = getUserPosition(objApprover, g_strOption37);

					strFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + "\u0002";
				}

				// 직함 or 부서명(부서단위)
				var strApproverTitle = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Enforce_HwpCtrl.FieldExist(strApproverDept) == false)
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
							{
								putStampFromFile(Enforce_HwpCtrl, strApprovalField, g_strDownloadPath + strSignUrl, true);
							}
						}
					}
				}
				else
				{
					clearStamp(Enforce_HwpCtrl, strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearStamp(Enforce_HwpCtrl, strApprovalField);
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
		if (Enforce_HwpCtrl.FieldExist(FIELD_REPORT_DEPT) == true)
		{
			strFieldList += FIELD_REPORT_DEPT + "\u0002";
			strValueList += strReportDept + "\u0002";
		}

		if (strFieldSlots.length > 0 && strFieldSlots.charAt(strFieldSlots.length - 1) == '^')
			strFieldSlots = strFieldSlots.substring(0, strFieldSlots.length - 1);

		if (bIsForm == false)
		{
			if (bSetData)
			{
				if (g_strApprovalFieldNames != strFieldSlots)
				{
//					removeApprovalFields(Enforce_HwpCtrl);
					makeApprovalFields(Enforce_HwpCtrl, strFieldSlots);
				}
			}
		}

		if (g_bHwpDocumentModify == false)
			Enforce_HwpCtrl.EditMode = 0x01;

		if (bIsForm == true/* && g_strOption77 == "1"*/)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (var i = arrSlashField.length - 1; i >= 0; i--)
				slashCell(Enforce_HwpCtrl, arrSlashField[i], true);
		}

		if (g_bHwpDocumentModify == false)
			Enforce_HwpCtrl.EditMode = 0;

		if (g_strEditType == "0")		// drafter
		{
			if (Enforce_HwpCtrl.FieldExist(FIELD_DRAFTER_NAME) == true)
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

		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function setEnforceApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
	var bIsForm = Enforce_HwpCtrl.FieldExist(FIELD_TITLE);

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
//				if (Enforce_HwpCtrl.FieldExist(strApprovalField) == true)
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
					if (Enforce_HwpCtrl.FieldExist(strApproverDept) == false)
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
					if (strApproverOpinion != "" && bSetData)
						strExtraValue = OPINION_ATTACHED;

					strFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + strExtraValue + "\u0002";
				}

				// 직위약어 or 부서명(부서단위)
				var strApprovalTypeAbbr = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Enforce_HwpCtrl.FieldExist(strApproverDept) == false)
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
					// 신사무관리 규정은 전대결의 표시방법이 다름(결재일자와 함께 상단에 표시)
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
					if (bSetData)
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

				// 날짜 필드에 먼저 세팅함 - 공석사유가 있을경우에는 도장필드
				if (strEmptyReason != "")
					strFieldList += strApprovalField + "\u0002";
				else
					strFieldList += "@" + strApprovalField + "\u0002";

				strValueList += strExtraValue + "\u0002";
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
								putStampFromFile(Enforce_HwpCtrl, strApprovalField, g_strDownloadPath + strSignUrl, true);
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
					clearStamp(Enforce_HwpCtrl, strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearStamp(Enforce_HwpCtrl, strApprovalField);
					break;
				}
			}

			if (nSetType == 1)
			{
				if (getApproverSerialOrder(objApprover) >= getApproverSerialOrder(getCompleteApproverInApprovalLine(objApprovalLine)))
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
							if ((Enforce_HwpCtrl.GetFieldText(strApprovalField)).indexOf(strMonthNDay) == -1)
								strValueList += Enforce_HwpCtrl.GetFieldText(strApprovalField) + " " + strMonthNDay + "\u0002";
							else
								strValueList += Enforce_HwpCtrl.GetFieldText(strApprovalField) + "\u0002";


							// 내부결재 관련 결재일자(FIELD_COMPLETE_DATE)와 등록일자필드(FIELD_REGIST_DATE) 추가
							if (Enforce_HwpCtrl.FieldExist(FIELD_REGIST_DATE))
							{
								strFieldList += FIELD_REGIST_DATE + "\u0002";
								strValueList += strDate + "\u0002";
							}
							if (Enforce_HwpCtrl.FieldExist(FIELD_COMPLETE_DATE))
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
		{
			if (bSetData)
			{
				if (g_strApprovalFieldNames != strFieldSlots)
				{
//					removeApprovalFields(Enforce_HwpCtrl);
					makeApprovalFields(Enforce_HwpCtrl, strFieldSlots);
				}
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);

		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
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

function setEnforceExaminationSign()
{
	var objDeliverer = getDelivererByDelivererType("0");
	if (objDeliverer != null)
	{
		if (Enforce_HwpCtrl.FieldExist(FIELD_INSPECTOR))
		{
			var strSignFileName = getDelivererSignFilename(objDeliverer);
			if (strSignFileName != "")
			{
				var strSignFilePath = g_strDownloadPath + strSignFileName;

				if (getLocalFileSize(strSignFilePath) > 0)
					putStampFromFile(Enforce_HwpCtrl, FIELD_INSPECTOR, strSignFilePath, true);
			}
		}

		if (Enforce_HwpCtrl.FieldExist(FIELD_INSPECT_DATE))
		{
			var strSignDate = getDelivererSignDate(objDeliverer);
			if (strSignDate != "")
			{
//				strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));
				var strDate = "";
				strDate = getDateDisplay(strSignDate, g_strOption36);
				Enforce_HwpCtrl.PutFieldText(FIELD_INSPECT_DATE, strDate + "\u0002");
			}
		}
	}
}

function clearEnforceApprovalFlow()
{
	setEnforceApprovalField(0, false, "");
}

function clearEnforceApprovalSign()
{
	setEnforceApprovalField(1, false, "");
}

function clearEnforceExaminationSign()
{
	if (Enforce_HwpCtrl.FieldExist(FIELD_INSPECTOR))
		clearStamp(Enforce_HwpCtrl, FIELD_INSPECTOR);

	if (Enforce_HwpCtrl.FieldExist(FIELD_INSPECT_DATE))
		Enforce_HwpCtrl.PutFieldText(FIELD_INSPECT_DATE, "\u0002");
}

function editEnforceDocument(bModify)
{
	var bReplace = true;
	var nEditMode = (bModify ? 0x01 : 0);

	var strFileName = getAttachFileName(getEnforceFileObj(g_nEnforceProposal));

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		if (getLocalFileSize(strLocalPath) > 0)
		{
			Enforce_HwpCtrl.Open(strLocalPath, "", "lock:FALSE;versionwarning:TRUE");
		}
		else
		{
			alert(ALERT_FIND_ENFORCE_DOCUMENT_ERROR);
			return;
		}
	}
	else
	{
		Enforce_HwpCtrl.Clear(1);
	}

	changeEditMode(Enforce_HwpCtrl, nEditMode);

	Enforce_HwpCtrl.focus();

	return true;
}

function saveEnforceDocument(bIncludeSign)
{
	var strTitle = getTitle(g_nEnforceProposal);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("<") != -1 || strTitle.indexOf(">") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	var strFilePath = HWPUtil.OpenHwpFileDialog(false, strTitle);
	if (strFilePath == "")
		return;

	var strExtension = strFilePath.substring(strFilePath.lastIndexOf(".") + 1, strFilePath.length);
	strExtension = strExtension.toLowerCase();

	var strFormat = "";
	if (strExtension == "hwp")
	{
		strFormat = "HWP";
	}
	else if (strExtension == "hml")
	{
		strFormat = "HWPML2X";
	}
	else if (strExtension == "htm")
	{
		strFormat = "HTML";
	}
	else if (strExtension == "txt")
	{
		strFormat = "TEXT";
	}
	else
	{
		strFormat = "HWP";
	}

	if (!bIncludeSign)
		clearEnforceApproveFlow()

	Enforce_HwpCtrl.SaveAs(strFilePath, strFormat, "lock:FALSE");
	if (!bIncludeSign)
		restoreEnforceApproveFlow();
}

function saveEnforceDocumentAs(strFilePath, bIncludeSign)
{
	if (!bIncludeSign)
		clearEnforceApproveFlow();
	Enforce_HwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
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
	Enforce_HwpCtrl.Run("Print");
}

function setArbitProxyApproverToForm()
{
	var nOption8 = parseInt(g_strOption8);
	var strFieldName = "";
	var strStampName = "";
	var strValue = "";
	var strSignUrl = "";

	if (Enforce_HwpCtrl.FieldExist(FIELD_REAL_NAME))
		strFieldName = FIELD_REAL_NAME;
	if (Enforce_HwpCtrl.FieldExist(FIELD_REAL_STAMP))
		strStampName = FIELD_REAL_STAMP;

	if (strFieldName != "" || strStampName != "")
	{
		var strEnforceBound = getEnforceBound(g_nEnforceProposal);
		if ((strEnforceBound == "O" || strEnforceBound == "C") && !isOuterRecipExist())		// 건보: 대외 or 대내외, 모든 안건의 수신처에 외부기관 존재하지 않을 때
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
						strValue = "(";

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

						strValue += ")";
						strSignUrl = getApproverSignFileName(objApprover);
					}
				}
			}
		}

		if (strFieldName != "")
			Enforce_HwpCtrl.PutFieldText(strFieldName, strValue + "\u0002");

		if (strStampName != "")
		{
			if ((nOption8 & 0x01) != 0 && strSignUrl != "")		// 서명인 사용: 0x01
			{
				if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
					putStampFromFile(Enforce_HwpCtrl, strStampName, g_strDownloadPath + strSignUrl, true);
			}
			else
				clearStamp(Enforce_HwpCtrl, strStampName);
		}
	}
}

function updateEnforcePostSendField()
{
	// 문서번호, 시행일자
	var strFieldList = "";
	var strValueList = "";

	if (Enforce_HwpCtrl.FieldExist(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (Enforce_HwpCtrl.FieldExist(FIELD_ENFORCE_DATE) == true)
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
		Enforce_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

// icaha, document switching
onEnforceAccessLoadingCompleted();
