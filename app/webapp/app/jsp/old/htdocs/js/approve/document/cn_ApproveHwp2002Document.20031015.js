var g_bLegacyFormSelected = false;
var g_bIsModified = false;
var g_bHwpDocumentModify;
var g_bHwpDocumentOpen = false;
var g_strChiefGrade = "";

var g_strApprovalFieldNames = "";
var g_bSeal = true;
var g_strSealName = "";
var g_strSealPath = "";

function onLoadDocument()
{
		alert('cn_ApproveHwp2002Document.20031015.js');
	if (Document_HwpCtrl.object == null)
	{
		alert(MSG_HWPCTRL_NOT_INSTALLED);
		return;
	}

	if (!verifyHwpCtrlVersion(Document_HwpCtrl))
		return;

	initToolbar(Document_HwpCtrl);

	if (g_strCaller == "legacy" && getLegacySystem() != null)
	{
		if (g_bLegacyFormSelected == false)
			return;
	}
	else if (getLegacySystem() != null && getBodyFileObj() == null)
	{
		if (g_bLegacyFormSelected == false)
			return;
	}

	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function loadDocument()
{
	var bReplace = g_bHwpDocumentOpen;
	g_bHwpDocumentModify = (g_strEditType == "0" || g_strEditType == "10");
	var nEditMode = (g_bHwpDocumentModify ? 0x01 : 0);

	g_bIsModified = (g_strEditType == "0" || g_strEditType == "10");
	if (getLegacySystem() != null && getBodyFileObj() == null)
		g_bIsModified = true;

	downloadDefaultFile();

	if (g_strFormUrl != "")
	{
		if (g_strDataUrl != "" && getIsPubDoc() == "Y")
		{
//			unPack();
			setDocumentBody();
		}

		if (getLocalFileSize(g_strDownloadPath + g_strFormUrl) > 0)
		{
			Document_HwpCtrl.Open(g_strDownloadPath + g_strFormUrl, "", "lock:FALSE");
		}
		else
		{
			alert(ALERT_FIND_DOCUMENT_ERROR);
			return;
		}

		if (g_strDataUrl != "" && getIsPubDoc() == "Y")
			pasteProposal();

		var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
		setIsForm((bIsForm ? "Y" : "N"));

		if (bIsForm)
		{
			var nBodyCount = 2;
			var strField = FIELD_TITLE + nBodyCount;
			while (Document_HwpCtrl.FieldExist(strField))
			{
				increaseBodyCount();
				strField = FIELD_TITLE + (++ nBodyCount);
			}
		}

		if (g_strDataUrl == "")
			setFormDataToXml();

//		setXmlDataToForm();
		setDocumentRecipientsToForm();
		setOrganImage();

		if (g_strDataUrl != "" && getIsAdminMis() == "Y")
		{
			var strFileName = "";
			var objBodyFile = getBodyFileObj();
			if (objBodyFile != null)
				strFileName = getAttachFileName(objBodyFile);
			if (strFileName != "")
				insertAux(Document_HwpCtrl, g_strDownloadPath + strFileName, 1);

			if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
				Document_HwpCtrl.PutFieldText(FIELD_SENDER_AS + "#1", getSender());

			if (Document_HwpCtrl.FieldExist(FIELD_ORGAN))
				Document_HwpCtrl.PutFieldText(FIELD_ORGAN, getDraftOrgName());

			var nFind = strFileName.lastIndexOf(".");
			if (nFind != -1)
			{
				strFileName = strFileName.substring(0, nFind) + "." + getFileExtension();
				setAttachFileName(objBodyFile, strFileName);
			}

			saveBodyFile();
			setModified(true);

			setXmlDataToForm();
			restoreApproveFlow();

//			setRelatedAttachToGeneralAttach();
		}
	}
	else if (g_strDataUrl != "")
	{
		var strFileName = "";
		var objBodyFile = getBodyFileObj();
		if (objBodyFile != null)
			strFileName = getAttachFileName(objBodyFile);

		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			if (getLocalFileSize(strLocalPath) > 0)
			{
				Document_HwpCtrl.Open(strLocalPath, "", "lock:FALSE");
			}
			else
			{
				alert(ALERT_FIND_DOCUMENT_ERROR);
				return;
			}

			var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
			setIsForm((bIsForm ? "Y" : "N"));

			if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
				g_strChiefGrade = Document_HwpCtrl.GetFieldText(FIELD_SENDER_AS + "#1");

			setXmlDataToForm();
			restoreApproveFlow();
		}
		else
		{
			setIsForm("N");
			// no body file
		}
	}
	else
	{
		Document_HwpCtrl.Clear(1);
		Document_HwpCtrl.CreateField(MSG_INSERT_BODY_HERE, FIELD_BODY, FIELD_BODY);
		setIsForm("N");
	}

//	�ϴ� �ʿ�� ����. ���� ������� �� �ʿ�...
//	pasteProposal(Document_HwpCtrl);

	setDrafterField();

//	Document_HwpCtrl.IsModified = 0;

	changeEditMode(Document_HwpCtrl, nEditMode);
	showToolbar(Document_HwpCtrl, nEditMode);
	Document_HwpCtrl.focus();
	onDocumentOpen();
}

function closeDocument()
{
}

function changeFormUrl(strFormUrl)
{
	var bReplace = true;
	var nEditMode = (g_bHwpDocumentModify ? 0x01 : 0);

	g_strFormUrl = strFormUrl;

	copyProposal(Document_HwpCtrl, 1);

	Document_HwpCtrl.Open(strFormUrl, "", "lock:FALSE");

	pasteProposal(Document_HwpCtrl);

	setXmlDataToForm();

	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
	setIsForm((bIsForm ? "Y" : "N"));

	changeEditMode(Document_HwpCtrl, nEditMode);

	Document_HwpCtrl.focus();
}

function getFileExtension()
{
	return "hwp";
}

function saveBodyFile()
{
	clearApproveFlow();
	var bRet = false;

	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();

	var strFileName = getAttachFileName(objBodyFile);

	if (saveHwpDocument(Document_HwpCtrl, g_strDownloadPath + strFileName, true) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objBodyFile, strFileSize);
	}
	restoreApproveFlow();
//	Document_HwpCtrl.IsModified = 0;

	return bRet;
}

function uploadBodyFile(strFileName)
{
	var strFileName = getAttachFileName(getBodyFileObj());
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
}

// nSetType : 0=setApprovalFlow, 1=setApprovalSign, 2=setUserSign
// bSetData : true=set, false=clear
// strUserID : break on match
function setApprovalField(nSetType, bSetData, strUserID)
{
	var strFormUsage = getFormUsage();

	var strLineType = getCurrentLineType();
	if (strLineType == "3")
	{
		var objApprovalLine = getFirstApprovalLine();
		while (objApprovalLine != null)
		{
			if (getLineType(objApprovalLine) == "3")
				setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);

			objApprovalLine = getNextApprovalLine(objApprovalLine);
		}
	}
	else
	{
		var objApprovalLine = null;
		if (strFormUsage == "0" || strFormUsage == "")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName("0");
			
		if (objApprovalLine == null)
			return;

		if (strFormUsage == "0" || strFormUsage == "")
			setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
		else
			setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}

function setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);

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
		else if (strRole == "4")			// ���� �ʵ� �߰�
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
			// strRole != "4"(����) �� ������
			if (strApprovalField != "" && strRole != "10" && strRole != "30")
			{
//				if (Document_HwpCtrl.FieldExist(strApprovalField) == true)
					strFieldSlots += strApprovalField + "^";

				// �̸�
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

				// ���κμ���
				var strApproverDept = "$" + strApprovalField;
				strFieldList += strApproverDept + "\u0002";
				strValueList += strDeptName + "\u0002";

				// ���� or �μ���(�μ�����)
				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Document_HwpCtrl.FieldExist(strApproverDept) == false)
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

				// ���� or �μ���(�μ�����)
				var strApproverTitle = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Document_HwpCtrl.FieldExist(strApproverDept) == false)
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
								putStampFromFile(Document_HwpCtrl, strApprovalField, g_strDownloadPath + strSignUrl, true);
							}
						}
					}
				}
				else
				{
					clearStamp(Document_HwpCtrl, strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearStamp(Document_HwpCtrl, strApprovalField);
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
		if (Document_HwpCtrl.FieldExist(FIELD_REPORT_DEPT) == true)
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
//					removeApprovalFields(Document_HwpCtrl);
					makeApprovalFields(Document_HwpCtrl, strFieldSlots);
				}
			}
		}

		if (g_bHwpDocumentModify == false)
			Document_HwpCtrl.EditMode = 0x01;

		if (bIsForm == true/* && g_strOption77 == "1"*/)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (var i = arrSlashField.length - 1; i >= 0; i--)
				slashCell(Document_HwpCtrl, arrSlashField[i], true);
		}

		if (g_bHwpDocumentModify == false)
			Document_HwpCtrl.EditMode = 0;

		if (g_strEditType == "0")		// drafter
		{
			if (Document_HwpCtrl.FieldExist(FIELD_DRAFTER_NAME) == true)
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

		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);

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
			// strRole != "4"(����) �� ������
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
//				if (Document_HwpCtrl.FieldExist(strApprovalField) == true)
					strFieldSlots += strApprovalField + "^";

				// �̸�
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

				// ���κμ���
				var strApproverDept = "$" + strApprovalField;
				strFieldList += strApproverDept + "\u0002";
				strValueList += strDeptName + "\u0002";

				// ���� or �μ���(�μ�����)
				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Document_HwpCtrl.FieldExist(strApproverDept) == false)
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
						if (arrOption37[0] == "GRD")
							strPosition = getApproverUserLevel(objApprover);
						if (arrOption37[0] == "SGRD")
							strPosition = getApproverUserLevelAbbr(objApprover);
						if (arrOption37[0] == "POS")
							strPosition = getApproverUserPosition(objApprover);
						if (arrOption37[0] == "SPOS")
							strPosition = getApproverUserPositionAbbr(objApprover);
						if (arrOption37[0] == "TLT")
							strPosition = getApproverUserPosition(objApprover);

						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x02) > 0)
							strPosition = "��" + strPosition;
						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x01) > 0)
							strPosition = "��" + strPosition;
					}

					// �ǰߺ���
					var strExtraValue = "";
					var strApproverOpinion = getApproverOpinion(objApprover);
					if (strApproverOpinion != "")
						strExtraValue = OPINION_ATTACHED;

					strFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + strExtraValue + "\u0002";
				}

				// ������� or �μ���(�μ�����)
				var strApprovalTypeAbbr = "!" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (Document_HwpCtrl.FieldExist(strApproverDept) == false)
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

				// ����� ǥ��
				var strExtraValue = "";
				if (strRole == "6")
				{
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
					var strEmptyReason = getApproverEmptyReason(objApprover);
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

				// ��¥ �ʵ忡 ���� ������
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
							{
								putStampFromFile(Document_HwpCtrl, strApprovalField, g_strDownloadPath + strSignUrl, true);
							}
						}
						else
						{
							strFieldList += strApprovalField + "\u0002";
							var strApproverName = getApproverUserName(objApprover);
							strValueList += strApproverName + "\u0002";
						}
					}
				}
				else
				{
					clearStamp(Document_HwpCtrl, strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearStamp(Document_HwpCtrl, strApprovalField);
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
							if (bSetData)
								strDate = getDateDisplay(strSignDate, g_strOption36);
	
							strApprovalField = "@" + strApprovalField;
	
							strFieldList += strApprovalField + "\u0002";
							strValueList += Document_HwpCtrl.GetFieldText(strApprovalField) + " " + strDate + "\u0002";
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
//					removeApprovalFields(Document_HwpCtrl);
					makeApprovalFields(Document_HwpCtrl, strFieldSlots);
				}
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);

		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function isExistSlot(arrSlotList, strField)
{
	var nSlotCount = arrSlotList.length;
	for (var i = 0; i < nSlotCount; i++)
	{
		if (arrSlotList[i] == strField)
			return true;
	}
	return false;
}

function markUnusedSlots(strFieldSlots)
{
	var arrSlotList = strFieldSlots.split("^");

	var strFields = Document_HwpCtrl.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var nFieldCount = arrField.length;
	var strField;
	var strFieldList = "", strValueList = "";
	for (var i = 0; i < nFieldCount; i++)
	{
		strField = arrField[i];
		var bMatch = false;
		if (strField.indexOf(FIELD_DRAFTER) == 0)
		{
			var strPostPart = strField.substring(FIELD_DRAFTER.length, strField.length);
			var re = /[1-9](-[1-9])?/;
			if (strPostPart.match(re) != null)
				bMatch = true;
		}
		else if (strField.indexOf(FIELD_CONSIDER) == 0)
		{
			var strPostPart = strField.substring(FIELD_CONSIDER.length, strField.length);
			var re = /[1-9](-[1-9])?/;
			if (strPostPart.match(re) != null)
				bMatch = true;
		}
		if (bMatch)
		{
			if (!isExistSlot(arrSlotList, strField))
			{
				if (g_strOption77 == "1")
					slashCell(Document_HwpCtrl, strField, true);
				else
					slashCell(Document_HwpCtrl, strField, false);
				strFieldList += strField + "\u0002#" + strField + "\u0002$" + strField + "\u0002*" + strField + "\u0002";
				strValueList += "\u0002\u0002\u0002\u0002";
			}
			else
			{
				slashCell(Document_HwpCtrl, strField, false);
			}
		}
	}
	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function setApprovalFlow()
{
	setApprovalField(0, true, "");
}

function setApprovalSign()
{
	setApprovalField(1, true, "");
}

function setExaminationSign()
{
	var objDeliverer = getDelivererByDelivererType("0");
	if (objDeliverer != null)
	{
		if (Document_HwpCtrl.FieldExist(FIELD_INSPECTOR))
		{
			var strSignFileName = getDelivererSignFilename(objDeliverer);
			if (strSignFileName != "")
			{
				var strSignFilePath = g_strDownloadPath + strSignFileName;

				if (getLocalFileSize(strSignFilePath) > 0)
					putStampFromFile(Document_HwpCtrl, FIELD_INSPECTOR, strSignFilePath, true);
			}
		}

		if (Document_HwpCtrl.FieldExist(FIELD_INSPECT_DATE))
		{
			var strSignDate = getDelivererSignDate(objDeliverer);
			if (strSignDate != "")
			{
//				strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));
				var strDate = "";
				strDate = getDateDisplay(strSignDate, g_strOption36);
				Document_HwpCtrl.PutFieldText(FIELD_INSPECT_DATE, strDate);
			}
		}
	}
}

function setApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();

	var strFormUsage = getFormUsage();

	while (objRelatedAttach != null)
	{
		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify == "DocStamp")
		{
			var strCaseNumber = getAttachCaseNumber(objRelatedAttach);
			var strSubDiv = getAttachSubDiv(objRelatedAttach);
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
				var strFileName = getAttachFileName(objRelatedAttach);
				var strFilePath = g_strDownloadPath + strFileName;
				var nImageWidth = parseInt(getAttachImageWidth(objRelatedAttach));
				var nImageHeight = parseInt(getAttachImageHeight(objRelatedAttach));

//				if (getLocalFileSize(strFilePath) > 0)
//				{
					if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
					{
/*
						// 20030429, manual seal...
						if (confirm(CONFIRM_AUTO_SEAL) == true)
						{
*/
							var strUnionField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
							var bManualStamp;
							if (Document_HwpCtrl.FieldExist(strFieldName) || Document_HwpCtrl.FieldExist(strUnionField) ||
								Document_HwpCtrl.FieldExist(FIELD_STAMP) || Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
							{
								if (Document_HwpCtrl.FieldExist(strFieldName))
								{
								}
								else if (Document_HwpCtrl.FieldExist(strUnionField))
									Document_HwpCtrl.RenameField(strReplaceField, strFieldName);
								else if (Document_HwpCtrl.FieldExist(FIELD_STAMP))
									Document_HwpCtrl.RenameField(FIELD_STAMP, strFieldName);
								else if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
									insertSealHwp(Document_HwpCtrl, strFieldName);
	
								if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
									adjustSealPos(Document_HwpCtrl, strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

								if (strFormUsage == "0" || strFormUsage == "")
									putStampFromFile(Document_HwpCtrl, strFieldName, strFilePath, true);
								else
									putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, nImageWidth, nImageHeight);
							}
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								if (strFormUsage == "0" || strFormUsage == "")
									putSealFromFileEx(Document_HwpCtrl, strFieldName, strFilePath);
								else
									putSealFromFileExWithSize(Document_HwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight);
								setModified(true);
							}
/*
						}
						else
						{
							alert(ALERT_CLICK_POSITION_TO_STAMP);
							if (strFormUsage == "0" || strFormUsage == "")
								putSealFromFileEx(Document_HwpCtrl, strFieldName, strFilePath);
							else
								putSealFromFileExWithSize(Document_HwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight);
							setModified(true);
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

						if (Document_HwpCtrl.FieldExist(strFieldName) || Document_HwpCtrl.FieldExist(strReplaceField) ||
							Document_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
						{
							if (Document_HwpCtrl.FieldExist(strReplaceField))
								Document_HwpCtrl.RenameField(strReplaceField, strFieldName);
							else if (Document_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
								Document_HwpCtrl.RenameField(FIELD_OMIT_STAMP, strFieldName);

							if (strFileName == "")
								Document_HwpCtrl.PutFieldText(strFieldName, strFieldName);
							else
							{
								if (strFormUsage == "0" || strFormUsage == "")
									putStampFromFile(Document_HwpCtrl, strFieldName, strFilePath, true);
								else
									putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, nImageWidth, nImageHeight);
							}
						}
/*
						else
						{
							alert(ALERT_CLICK_POSITION_TO_STAMP);
							if (strFormUsage == "0" || strFormUsage == "")
								putOmitSealFromFileEx(Document_HwpCtrl, strFieldName, strFilePath);
							else
								putOmitSealFromFileExWithSize(Document_HwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight);
							setModified(true);
						}
*/
					}
//				}
			}
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function restoreApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();

	var strFormUsage = getFormUsage();

	while (objRelatedAttach != null)
	{
		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify == "DocStamp")
		{
			var strCaseNumber = getAttachCaseNumber(objRelatedAttach);
			var strSubDiv = getAttachSubDiv(objRelatedAttach);
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
				var strFileName = getAttachFileName(objRelatedAttach);
				var strFilePath = g_strDownloadPath + strFileName;
				var nImageWidth = parseInt(getAttachImageWidth(objRelatedAttach));
				var nImageHeight = parseInt(getAttachImageHeight(objRelatedAttach));

				if (getLocalFileSize(strFilePath) > 0)
				{
					// �������� ���ε� ���
					if (Document_HwpCtrl.FieldExist(strFieldName))
					{
						if (strFormUsage == "0" || strFormUsage == "")
							putStampFromFile(Document_HwpCtrl, strFieldName, strFilePath, true);
						else
							putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, nImageWidth, nImageHeight);
					}
					// �ڵ����� ���ε� ���
					else
					{
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
							var strUnionField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
							if (Document_HwpCtrl.FieldExist(strUnionField) ||
								Document_HwpCtrl.FieldExist(FIELD_STAMP) || Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
							{
								if (Document_HwpCtrl.FieldExist(strUnionField))
									Document_HwpCtrl.RenameField(strReplaceField, strFieldName);
								else if (Document_HwpCtrl.FieldExist(FIELD_STAMP))
									Document_HwpCtrl.RenameField(FIELD_STAMP, strFieldName);
								else if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
									insertSealHwp(Document_HwpCtrl, strFieldName);
	
								if (Document_HwpCtrl.FieldExist(FIELD_SENDER_AS + "#1"))
									adjustSealPos(Document_HwpCtrl, strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

								if (strFormUsage == "0" || strFormUsage == "")
									putStampFromFile(Document_HwpCtrl, strFieldName, strFilePath, true);
								else
									putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, nImageWidth, nImageHeight);
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
	
							if (Document_HwpCtrl.FieldExist(strReplaceField) ||	Document_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
							{
								if (Document_HwpCtrl.FieldExist(strReplaceField))
									Document_HwpCtrl.RenameField(strReplaceField, strFieldName);
								else if (Document_HwpCtrl.FieldExist(FIELD_OMIT_STAMP))
									Document_HwpCtrl.RenameField(FIELD_OMIT_STAMP, strFieldName);

								if (strFormUsage == "0" || strFormUsage == "")
									putStampFromFile(Document_HwpCtrl, strFieldName, strFilePath, true);
								else
									putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, nImageWidth, nImageHeight);
							}
						}
					}
				}
			}
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function setOrganImage()
{
	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify == "OrganImage")
		{
			var strCaseNumber = getAttachCaseNumber(objRelatedAttach);
			var strSubDiv = getAttachSubDiv(objRelatedAttach);
			var strFieldName = "";

			if (strSubDiv == "Logo")
				strFieldName = FIELD_LOGO_IMAGE;
			else if (strSubDiv == "Symbol")
				strFieldName = FIELD_SYMBOL_IMAGE;

			if (strFieldName != "")
			{
				var strFileName = getAttachFileName(objRelatedAttach);
				var strFilePath = g_strDownloadPath + strFileName;

				if (Document_HwpCtrl.FieldExist(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
						putStampFromFileWithSize(Document_HwpCtrl, strFieldName, strFilePath, true, 20, 20);
				}
			}
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function removeApprovalSeal(strSubDiv)
{
}

function clearApprovalFlow()
{
	setApprovalField(0, false, "");
}

function clearApprovalSign()
{
	setApprovalField(1, false, "");
}

function clearExaminationSign()
{
	if (Document_HwpCtrl.FieldExist(FIELD_INSPECTOR))
		clearStamp(Document_HwpCtrl, FIELD_INSPECTOR);

	if (Document_HwpCtrl.FieldExist(FIELD_INSPECT_DATE))
		Document_HwpCtrl.PutFieldText(FIELD_INSPECT_DATE, "");
}

function clearApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify == "DocStamp")
		{
			var strSubDiv = getAttachSubDiv(objRelatedAttach);
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

			clearStamp(Document_HwpCtrl, strFieldName);
		}
		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function clearOrganImage()
{
	if (Document_HwpCtrl.FieldExist(FIELD_LOGO_IMAGE))
		clearStamp(Document_HwpCtrl, FIELD_LOGO_IMAGE);

	if (Document_HwpCtrl.FieldExist(FIELD_LOGO_IMAGE))
		Document_HwpCtrl.PutFieldText(FIELD_LOGO_IMAGE, "");

	if (Document_HwpCtrl.FieldExist(FIELD_SYMBOL_IMAGE))
		clearStamp(Document_HwpCtrl, FIELD_SYMBOL_IMAGE);

	if (Document_HwpCtrl.FieldExist(FIELD_SYMBOL_IMAGE))
		Document_HwpCtrl.PutFieldText(FIELD_SYMBOL_IMAGE, "");
}

function clearUserSign(strUserID)
{
	setApprovalField(2, false, strUserID);
}

function setDocumentRecipientsToForm()
{
	var strFieldList = "", strValueList = "";

	var nBodyCount = getBodyCount();
	for (var nCaseNumber = 1; nCaseNumber <= nBodyCount; nCaseNumber++)
	{
		var strDeptField = "", strRefField = "", strDeptRefField = "";
		var strDeptName = "", strRefName = "", strDeptRefName = "";

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

		var objRecipGroup = getRecipGroup(nCaseNumber);
		if (objRecipGroup != null)
		{
			var nRecipientCount = getRecipientCount(objRecipGroup);

			var strDisplayAs = getRecipGroupDisplayAs(objRecipGroup);
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

			if (Document_HwpCtrl.FieldExist(strDeptField))
			{
				strFieldList += strDeptField + "\u0002";
				if (nRecipientCount == 0)
					strValueList += strDisplayAs + "\u0002";
				else if (nRecipientCount == 1)
					strValueList += strDeptName + "\u0002";
				else
					strValueList += RECIPIENT_REFER + "\u0002";
			}

			if (Document_HwpCtrl.FieldExist(strRefField))
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

			if (Document_HwpCtrl.FieldExist(strDeptRefField))
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
			if (Document_HwpCtrl.FieldExist(strDeptField))
			{
				strFieldList += strDeptField + "\u0002";
				var strDocCategory = getDocCategory(nCaseNumber);
				if (strDocCategory == "I")
					strValueList += INNER_APPROVAL + "\u0002";
				else
					strValueList += "\u0002";
			}

			if (Document_HwpCtrl.FieldExist(strRefField))
			{
				strFieldList += strRefField + "\u0002";
				strValueList += "\u0002";
			}

			if (Document_HwpCtrl.FieldExist(strDeptRefField))
			{
				strFieldList += strDeptRefField + "\u0002";
				strValueList += "\u0002";
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}

	saveBodyFile();
	setModified(true);
}

function getFieldText(strFieldName)
{
	return Document_HwpCtrl.GetFieldText(strFieldName);
}

function addDocument()
{
	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
	if (bIsForm == false)
		return false;

	var nMode;
	if (g_strOption81 == "0")
		nMode = 1;
	else if (g_strOption81 == "1")
		nMode = 2;
	else
		nMode = 1;

	var bReturn;
	if (nMode == 1)
		bReturn = addProposal(Document_HwpCtrl);
	else
		bReturn = addProposal2(Document_HwpCtrl);

//	bReturn = addProposalByMode(Document_HwpCtrl, nMode);

	var nBodyCount = getBodyCount();
	if (getDocCategory(nBodyCount) == "I")
		Document_HwpCtrl.PutFieldText(FIELD_RECIPIENT + nBodyCount, INNER_APPROVAL);
	else
		Document_HwpCtrl.PutFieldText(FIELD_SENDER_AS + "#" + nBodyCount, g_strChiefGrade);
	if (nBodyCount == 2)
		Document_HwpCtrl.PutFieldText(FIELD_BATCH, "(" + FIRST_DRAFT + ")");

	increaseBodyCount();
}

function removeDocument(nIndex)			// nIndex: zero base
{
	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
	if (bIsForm == false)
		return false;

	var nMode;
	if (g_strOption81 == "0")
		nMode = 1;
	else if (g_strOption81 == "1")
		nMode = 2;
	else
		nMode = 1;

	if (nMode == 1)
		deleteProposal(Document_HwpCtrl, nIndex - 1);
	else
		deleteProposal(Document_HwpCtrl, nIndex - 1);

//	deleteProposalByMode(Document_HwpCtrl, nIndex - 1, nMode);

	decreaseBodyCount(nIndex);

	if (getBodyCount() == 1)
		Document_HwpCtrl.PutFieldText(FIELD_BATCH, "");
}

function editDocument(bModify)
{
	var bReplace = true;
	var nEditMode = (bModify ? 0x01 : 0);

	if (g_bHwpDocumentModify == bModify)
	{
	}
	else
	{
		var objBodyFile = getBodyFileObj();
		if (objBodyFile == null)
			objBodyFile = setBodyFileInfo();

		strFileName = getAttachFileName(objBodyFile);

		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;

//			if (bModify)				// save to preserve links to images
				saveHwpDocument(Document_HwpCtrl, strLocalPath, false);

			if (getLocalFileSize(strLocalPath) > 0)
			{
				Document_HwpCtrl.Open(strLocalPath, "", "lock:FALSE");
			}
			else
			{
				alert(ALERT_FIND_DOCUMENT_ERROR);
				return false;
			}
		}
		else
		{
			Document_HwpCtrl.Clear(1);
			Document_HwpCtrl.CreateField(MSG_INSERT_BODY_HERE, FIELD_BODY, FIELD_BODY);
		}

		var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
		setIsForm((bIsForm ? "Y" : "N"));

		if (!bModify)
			restoreApproveFlow();

		changeEditMode(Document_HwpCtrl, nEditMode);

		showToolbar(Document_HwpCtrl, nEditMode);

		Document_HwpCtrl.focus();
	}

	g_bHwpDocumentModify = bModify;
	return true;
}

function saveApproveDocument(bIncludeSign)
{
	var strTitle = getTitle(1);

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
		clearApproveFlow();
	Document_HwpCtrl.SaveAs(strFilePath, strFormat, "lock:FALSE");
	if (!bIncludeSign)
		restoreApproveFlow();
}

function saveApproveDocumentAs(strFilePath, bIncludeSign)
{
	if (!bIncludeSign)
		clearApproveFlow();
	Document_HwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
	if (!bIncludeSign)
		restoreApproveFlow();
}

function setFormDataToXml()
{
//	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
//	if (bIsForm == false)
//		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (Document_HwpCtrl.FieldExist(strFieldName) == true)
		{
			var strSubject = Document_HwpCtrl.GetFieldText(strFieldName);
			if (strSubject.length > 128)
				strSubject = strSubject.substring(0, 128)

			setTitle(strSubject, nIndex);
		}

		nIndex++;
	}
/*
	if (Document_HwpCtrl.FieldExist(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = Document_HwpCtrl.GetFieldText(FIELD_DOC_NUMBER);

		var strNumber = "";
		var strClassName = "";
		var strSerialNumber = "";
		var nNumberType = 0;

		var nLength = strDocNumber.length;
		var i = 0;
		while (i < nLength)
		{
			var ch = strDocNumber.charAt(i);
			if (ch == "-")
			{
				nNumberType = 2;
			}
			else if (ch >= "0" && ch <= "9")
			{
				if (nNumberType == 0)
					nNumberType = 1;
			}
			else
			{
				if (nNumberType == 1)
				{
					strClassName += strNumber;
					strNumber = "";
					nNumberType = 0;
				}
			}

			if (ch != "-")
			{
				if (nNumberType == 0)
					strClassName += ch.toString();
				else if (nNumberType == 1)
					strNumber += ch.toString();
				else if (nNumberType == 2)
					strSerialNumber += ch.toString();
			}

			i++;
		}

		if (strClassName.length > 16)
			strClassName = strClassName.substring(0, 16);

		if (strNumber.length > 32)
			strNumber = strNumber.substring(0, 32);

		setOrgSymbol(strClassName);
		setClassNumber(strNumber);
		setSerialNumber(strSerialNumber);
	}
	else
	{
		var strNumber = getClassNumber();
		var strClassName = getOrgSymbol();
		var strSerialNumber = getSerialNumber();

		if (strClassName.length > 16)
			strClassName = strClassName.substring(0, 16);

		if (strNumber.length > 32)
			strNumber = strNumber.substring(0, 32);

		setOrgSymbol(strClassName);
		setClassNumber(strNumber);
		setSerialNumber(strSerialNumber);
	}

	if (Document_HwpCtrl.FieldExist(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = Document_HwpCtrl.GetFieldText(FIELD_CONSERVE_TERM);

		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0, 4);

		if (strConserveTerm != "")
			setDraftConserve(strConserveTerm);
	}
	else
	{
		var strConserveTerm = getDraftConserve();

		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0, 4);

		setDraftConserve(strConserveTerm);
	}

	if (Document_HwpCtrl.FieldExist(FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = Document_HwpCtrl.GetFieldText(FIELD_PUBLIC_BOUND);

		if (strPublicBound.length > 4)
			strPublicBound = strPublicBound.substring(0, 4);

		if (strPublicBound != "")
			setPublicLevel(strPublicBound);
	}
	else
	{
		var strPublicBound = getPublicLevel();

		if (strPublicBound.length > 4)
			strPublicBound = strPublicBound.substring(0, 4)

		setPublicLevel(strPublicBound);
	}
*/
}

function setXmlDataToForm()
{
	var strFieldList = "", strValueList = "";

//	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
//	if (bIsForm == false)
//		return;

	var strFormUsage = getFormUsage();

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (Document_HwpCtrl.FieldExist(strFieldName) == true)
		{
			strSubject = getTitle(nIndex);

			if (strSubject != Document_HwpCtrl.GetFieldText(strSubject))
			{
				strFieldList += strFieldName + "\u0002";
				strValueList += strSubject + "\u0002";
			}
		}
/*
		var strDeptField = "", strRefField = "", strDeptRefField = "";
		if (nIndex == 1)
		{
			strDeptField = FIELD_RECIPIENT;
			strRefField = FIELD_REFERER;
			strDeptRefField = FIELD_RECIPIENT_REFER + "#1";
		}
		else
		{
			strDeptField = FIELD_RECIPIENT + nIndex;
			strRefField = FIELD_REFERER + nIndex;
			strDeptRefField = FIELD_RECIPIENT_REFER + "#" + nIndex;
		}

		if (Document_HwpCtrl.FieldExist(strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nIndex);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";
		}

		if (Document_HwpCtrl.FieldExist(strRefField))
		{
			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}

		if (Document_HwpCtrl.FieldExist(strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			strValueList += "\u0002";
		}
*/
		nIndex++;
	}

	if (Document_HwpCtrl.FieldExist(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = getDraftConserve();
		strFieldList += FIELD_CONSERVE_TERM + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_RECP_CONSERVE) == true)
	{
		var strConserveTerm = getEnforceConserve();
		strFieldList += FIELD_RECP_CONSERVE + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_PUBLIC_BOUND) == true)
	{
//		var strPublicBound = getPublicLevel();
		var strPublicBound = getPublication(getPublicLevel());
		strFieldList += FIELD_PUBLIC_BOUND + "\u0002";
		strValueList += strPublicBound + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_ENFORCE_NUMBER) == true)
	{
		var strDraftDeptName = getDraftProcDeptName();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDraftDeptName += "-" + strSerialNumber;

		strFieldList += FIELD_ENFORCE_NUMBER + "\u0002";
		strValueList += strDraftDeptName + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_ENFORCE_DATE) == true)
	{
		var strEnforceDate = getEnforceDate();
		if (strEnforceDate == "")
		{
			var nBodyCount = getBodyCount();
			var nCaseNum = 1;
			var bInbound = true;

			while (nCaseNum < nBodyCount)
			{
				var strEnforceBound = getEnforceBound(nCaseNum);
				if (strEnforceBound == "O" || strEnforceBound == "C")
				{
					bInbound = false;
					break;
				}

				nCaseNum++;
			}

			if (bInbound)
			{
				var strFlowStatus = getFlowStatus();
				if (strFlowStatus != "0" && strFlowStatus != "12")
				{
					var objApprover = getCompleteApprover();
					if (objApprover != null)
						strEnforceDate = getApproverSignDate(objApprover);
				}
			}
/*
			var strDocCategory = getDocCategory(nIndex);
			if (strDocCategory == "I")
			{
				strEnforceDate = getCompleteDate();
			}
*/
		}
		var strDate = "";
// 20030324, ���ں� ����
		if (strEnforceDate != "")
			strDate = getDateDisplay(strEnforceDate, "YYYY.MM.DD.");

		strFieldList += FIELD_ENFORCE_DATE + "\u0002";
		if (strDate != "")
		{	
			strDate = "(" + strDate + ")";
		}
		strValueList += strDate + "\u0002";
	}
	if (Document_HwpCtrl.FieldExist(FIELD_RECEIVE_DATE) == true)
	{
		var strReceiveDate = getReceiveDate();
		var strDate = "", strTime = "";
		if (strReceiveDate != "")
		{
// 20030324, ���ں� ����
			strDate = getDateDisplay(strReceiveDate, "YYYY.MM.DD.");
			strTime = getTimeDisplay(strReceiveDate);
		}

		strFieldList += FIELD_RECEIVE_DATE + "\u0002";
		if (strDate != "")
			strDate = "(" + strDate + ")";
		strValueList += strDate + "\u0002";

		if (Document_HwpCtrl.FieldExist(FIELD_RECEIVE_TIME) == true)
		{
			strFieldList += FIELD_RECEIVE_TIME + "\u0002";
			strValueList += strTime + "\u0002";
		}
	}

	if (Document_HwpCtrl.FieldExist(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "")
		{
			var strEnforceDeptName = getEnforceProcDeptName();
			if (strReceiveNumber != "" && parseInt(strReceiveNumber) > 0)
				strEnforceDeptName += "-" + strReceiveNumber;

			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strEnforceDeptName + "\u0002";
		}
/*
		if (strReceiveNumber != "0")
		{
			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strReceiveNumber + "\u0002";
		}
*/
	}

	if (Document_HwpCtrl.FieldExist(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (Document_HwpCtrl.FieldExist(FIELD_INSTRUCTION) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				strFieldList += FIELD_INSTRUCTION + "\u0002";
				strValueList += strInstruction + "\u0002";
			}
		}
	}

	if (Document_HwpCtrl.FieldExist(FIELD_INSTRUCTION_ITEM) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				strFieldList += FIELD_INSTRUCTION_ITEM + "\u0002";
				strValueList += strInstruction + "\u0002";
			}
		}
	}

	if (Document_HwpCtrl.FieldExist(FIELD_TREATMENT) == true)
	{
		var strDocCategory = getDocCategory(1);
		strFieldList += FIELD_TREATMENT + "\u0002";
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			var strTreatment = getTreatment(1);
			strValueList += strTreatment + "\u0002";
// �Ǻ�, ��� �� ���
			setBorderLine(Document_HwpCtrl, FIELD_TREATMENT, true);
		}
		else
		{
			strValueList += "\u0002";
// �Ǻ�, ��� �� ���
			setBorderLine(Document_HwpCtrl, FIELD_TREATMENT, false);
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}

//	setDocumentRecipientsToForm();
//	setSenderAsToForm(g_strChiefGrade);
}

function getFormInfo()
{
	var strFormInfo = "";

	if (getIsForm() == "N")
		return strFormInfo;

	if (Document_HwpCtrl.FieldExist(FIELD_DRAFTER + "1") == true || Document_HwpCtrl.FieldExist("#" + FIELD_DRAFTER + "1") == true)
	{
		var nSubmit = 0;
		while (true)
		{
			var strField = FIELD_DRAFTER + (nSubmit + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nSubmit++;
		}

		var nApprove = 0;
		while (true)
		{
			var strField = FIELD_CONSIDER + (nApprove + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nApprove++;
		}

		var nAssist = 0;
		while (true)
		{
			var strField = FIELD_COOPERATE + (nAssist + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nAssist++;
		}

		var nComplete = 0;
		while (true)
		{
			var strField = FIELD_LAST + (nComplete + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nComplete++;
		}

		strFormInfo = nSubmit + "-" + nApprove + "-" + nAssist + "-" + nComplete;
	}
	else if (Document_HwpCtrl.FieldExist(FIELD_DRAFTER + "1-1") == true ||
		Document_HwpCtrl.FieldExist("#" + FIELD_DRAFTER + "1-1") == true)
	{
		var nType = 1;
		while (true)
		{
			var nSubmit = 0;
			while (true)
			{
				var strField = FIELD_DRAFTER + nType + "-" + (nSubmit + 1);
				if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
					break;

				nSubmit++;
			}

			var nApprove = 0;
			while(true)
			{
				var strField = FIELD_CONSIDER + nType + "-" + (nApprove + 1);
				if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
					break;

				nApprove++;
			}

			var nAssist = 0;
			while (true)
			{
				var strField = FIELD_COOPERATE + nType + "-" + (nAssist + 1);
				if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
					break;

				nAssist++;
			}

			var nComplete = 0;
			while (true)
			{
				var strField = FIELD_LAST + nType + "-" + (nComplete + 1);
				if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
					break;

				nComplete++;
			}

			var nTotal = nSubmit + nApprove + nAssist + nComplete;
			if (nTotal == 0)
				break;

			if (strFormInfo != "")
				strFormInfo += "-";

			strFormInfo += nTotal;
			nType++;
		}
	}
	else if (Document_HwpCtrl.FieldExist(FIELD_CHARGER) == true || Document_HwpCtrl.FieldExist("#" + FIELD_CHARGER) == true ||
		Document_HwpCtrl.FieldExist(FIELD_PRIOR) == true || Document_HwpCtrl.FieldExist(FIELD_PUBLIC) == true)
	{
		var nCharger = 0;
		{
			var strField = FIELD_CHARGER;
			if (Document_HwpCtrl.FieldExist(strField) == true || Document_HwpCtrl.FieldExist("#" + strField) == true)
				nCharger++;
		}

		var nPrior = 0;
		while (true)
		{
			var strField = FIELD_PRIOR + (nPrior + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nPrior++;
		}

		var nCircular = 0;
		while (true)
		{
			var strField = FIELD_PUBLIC + (nCircular + 1);
			if (Document_HwpCtrl.FieldExist(strField) == false && Document_HwpCtrl.FieldExist("#" + strField) == false)
				break;

			nCircular++;
		}

		strFormInfo = nCharger + "-" + nPrior + "-" + nCircular;
	}

	return strFormInfo;
}

function clearApproveFlow()
{
//	if (getIsForm() == "N")
//		removeApprovalFields(Document_HwpCtrl);
//	else
//	{
		clearApprovalFlow();
		clearApprovalSign();
		clearExaminationSign();
		clearApprovalSeal();
		clearOrganImage();
//	}
}

function restoreApproveFlow()
{
	setApprovalFlow();
	setApprovalSign();
	setExaminationSign();
	// 20030520, manual seal...
	restoreApprovalSeal();
//	setApprovalSeal();
	setOrganImage();
}

function setPriorOpinion(strOpinion)
{
	if (Document_HwpCtrl.FieldExist(FIELD_INSTRUCTION) == true)
		Document_HwpCtrl.PutFieldText(FIELD_INSTRUCTION, strOpinion + "");
	else if (Document_HwpCtrl.FieldExist(FIELD_INSTRUCTION_ITEM) == true)
		Document_HwpCtrl.PutFieldText(FIELD_INSTRUCTION_ITEM, strOpinion + "");
}

function printDocument()
{
	Document_HwpCtrl.Run("Print");
}

function openFile()
{
	var strFilePath = HWPUtil.OpenHwpFileDialog(true, "");
	if (strFilePath == "")
		return;

	Document_HwpCtrl.Open(strFilePath, "", "lock:FALSE");

	var bIsForm = Document_HwpCtrl.FieldExist(FIELD_TITLE);
	if (bIsForm == true)
	{
		setIsForm("Y");

		var nIndex = 2;
		var strField = FIELD_TITLE + nIndex;
		while (Document_HwpCtrl.FieldExist(strField) == true)
		{
			nIndex++;
			strField = FIELD_TITLE + nIndex;
		}
	}
	else
		setIsForm("N");

	setFormDataToXml();
	restoreApproveFlow();

	setDrafterField();
}

function isModified()
{
	return g_bIsModified;
}

function setModified(bModified)
{
	g_bIsModified = bModified;
}

function setSenderAsToForm(strChiefGrade)
{
	if (getFlowStatus() != "0")
		return;

	if (typeof(strChiefGrade) == "undefined")
		strChiefGrade = g_strChiefGrade;
	g_strChiefGrade = strChiefGrade;

	var strFieldList = "", strValueList = "";
	var nBodyCount = getBodyCount();
	for (var n = 1; n <= nBodyCount; n++)
	{
		var strSenderAsField = FIELD_SENDER_AS + "#" + n;
		if (Document_HwpCtrl.FieldExist(strSenderAsField))
		{
			strFieldList += strSenderAsField + "\u0002";
			if (getDocCategory(n) == "I")
			{
				strValueList += "\u0002";
				setSenderAs("", n);
			}
			else
			{
				strValueList += strChiefGrade + "\u0002";
				setSenderAs(strChiefGrade, n);
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function setLegacyXMLToForm(objRoot)
{
	var strFields = Document_HwpCtrl.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var nFieldCount = arrField.length;
	var strFieldList = "", strValueList = "";
	for (var i = 0; i < nFieldCount; i++)
	{
		var objElement = null;
		try
		{
			objElement = getNode(objRoot, arrField[i]);
		}
		catch (e)
		{
			continue;
		}

		if (objElement != null)
		{
			strFieldList += arrField[i] + "\u0002";
			strValueList += objElement.text + "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

function setFormDataToLegacyXML(objRoot)
{
	var strFields = Document_HwpCtrl.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HwpCtrl.GetFieldText(strFields);
	var arrValue = strValues.split("\u0002");

	var nFieldCount = arrField.length;
	var strFieldList = "", strValueList = "";

	for (var i = 0; i < nFieldCount; i++)
	{
		var objElement = null;
		try
		{
			objElement = getNode(objRoot, arrField[i]);
		}
		catch (e)
		{
			continue;
		}

		if (objElement != null)
		{
			objElement.text = arrValue[i];
		}
	}
}

function setDrafterField()
{
	if (g_strEditType == "0")
	{
		var strFieldList = "", strValueList = "";
		if (Document_HwpCtrl.FieldExist(FIELD_ORGAN))
		{
			strFieldList += FIELD_ORGAN + "\u0002";
//			strValueList += g_strUserOrgName + "\u0002";
			strValueList += g_strUserOrgDisplayName + "\u0002";		// �Ǻ� ���ǥ�ø�
		}
		if (Document_HwpCtrl.FieldExist(FIELD_EMAIL))
		{
			strFieldList += FIELD_EMAIL + "\u0002";
			strValueList += g_strEmail + "\u0002";
		}
		if (Document_HwpCtrl.FieldExist(FIELD_HOMEPAGE))
		{
			strFieldList += FIELD_HOMEPAGE + "\u0002";
			strValueList += g_strHomePage + "\u0002";
		}
		if (Document_HwpCtrl.FieldExist(FIELD_TELEPHONE))
		{
			strFieldList += FIELD_TELEPHONE + "\u0002";
			strValueList += g_strTelephone + "\u0002";
		}
		if (Document_HwpCtrl.FieldExist(FIELD_FAX))
		{
			strFieldList += FIELD_FAX + "\u0002";
			strValueList += g_strFax + "\u0002";
		}
		if (Document_HwpCtrl.FieldExist(FIELD_ZIPCODE))
		{
			strFieldList += FIELD_ZIPCODE + "\u0002";
			strValueList += g_strZipCode + "\u0002";
		}
		if (Document_HwpCtrl.FieldExist(FIELD_ADDRESS))
		{
			strFieldList += FIELD_ADDRESS + "\u0002";
			strValueList += g_strAddress + "\u0002";
		}
		if (strFieldList.length > 0)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
	
			Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
		}
	}
}

function updateDocumentPostSendField()
{
	// ������ȣ, ��������
	var strFieldList = "";
	var strValueList = "";

	if (Document_HwpCtrl.FieldExist(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (Document_HwpCtrl.FieldExist(FIELD_ENFORCE_DATE) == true)
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
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
}

/*
function setArbitProxyApproverToForm()
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
			var strFields = Document_HwpCtrl.GetFieldList("\u0002");
			var arrField = strFields.split("\u0002");

			var objDic = new Array();
			var nFieldCount = arrField.length;
			for (var i = 0; i < nFieldCount; i++)
				objDic[arrField[i]] = "";

			var strFieldName = "(";

			if (strRole == "6" && Document_HwpCtrl.FieldExist(FIELD_REAL_NAME_ARBIT))
				strFieldName = FIELD_REAL_NAME_ARBIT;
			else if (strRole == "7" && Document_HwpCtrl.FieldExist(FIELD_REAL_NAME_PROXY))
				strFieldName = FIELD_REAL_NAME_PROXY;
			else if ((strRole == "6" || strRole == "7") && Document_HwpCtrl.FieldExist(FIELD_REAL_NAME))
				strFieldName = FIELD_REAL_NAME;

			if (strFieldName != "")
			{
				var strValue = "";
				strValue += getApproverUserPosition(objApprover);

				if (g_strOption8 == "0")
				{
				}
				else if (g_strOption8 == "1")
				{
					strValue += " " + getApproverUserName(objApprover);
				}
				else
				{
					strValue += " " + getApproverUserName(objApprover);
				}

				if (strRole == "6")
				{
					strValue += " " + ROLE_ARBITRARY;
				}
				else if (strRole == "7")
				{
					strValue += " " + ROLE_PROXY;
				}

				strValue += ")";
				Document_HwpCtrl.PutFieldText(strFieldName, strValue);
			}
		}
	}
}
*/

function setZipcode(strZipcode, strAddress)
{
	if (Document_HwpCtrl.FieldExist(FIELD_ZIPCODE) == true)
		Document_HwpCtrl.PutFieldText(FIELD_ZIPCODE, strZipcode);
	if (Document_HwpCtrl.FieldExist(FIELD_ADDRESS) == true)
		Document_HwpCtrl.PutFieldText(FIELD_ADDRESS, strAddress);
}

function onSetViewScale(bExpand)
{
	var vpSet1 = Document_HwpCtrl.ViewProperties;
	var vpSet2 = Enforce_HwpCtrl.ViewProperties;
	if (bExpand)
	{
		vpSet1.SetItem("ZoomType", 0);
		vpSet1.SetItem("ZoomRatio", 100);

		vpSet2.SetItem("ZoomType", 0);
		vpSet2.SetItem("ZoomRatio", 100);
	}
	else
	{
		vpSet1.SetItem("ZoomType", 2);
		vpSet2.SetItem("ZoomType", 2);
	}
	Document_HwpCtrl.ViewProperties = vpSet1;
	Enforce_HwpCtrl.ViewProperties = vpSet2;
}

function saveBodyToXML(strBodyXMLPath)
{
	if (Document_HwpCtrl.FieldExist(FIELD_BODY) == false)
	{
		alert("Field '" + FIELD_BODY + "' not found.");
		return false;
	}

	var strFieldType;
	Document_HwpCtrl.MoveToField(FIELD_BODY);
	if ((Document_HwpCtrl.CurFieldState & 0xf) == 1)
		strFieldType = "cell";
	else if ((Document_HwpCtrl.CurFieldState & 0xf) == 2)
		strFieldType = "clickhere";
	else
	{
		alert("Field '" + FIELD_BODY + "' is not cell or clikhere object.");
		return false;
	}
	var strFormat = "PUBDOCBODY";
	var strArguments = "fieldtype:" + strFieldType + ";fieldname:" + FIELD_BODY;
	var bReturn = Document_HwpCtrl.SaveAs(strBodyXMLPath, strFormat, strArguments);		// UTF-8
// bReturn �� �׻� false ��. �����Ҽ� ����
//	if (bReturn == true)
//	{
//		var strXhtml = readLocalFile(strBodyXMLPath);

		var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
		objXMLDom.async = false;
		objXMLDom.validateOnParse = false;
		bReturn = objXMLDom.load(strBodyXMLPath);
		if (bReturn == false)
		{
			alert(ALERT_ERROR_OCCUR_PACK + "\nError Type:Load XML From content\n" + objXMLDom.parseError.reason);
			changeMenu(2);
			return;
		}
		var strXhtml = objXMLDom.documentElement.xml;

		var nContentStartTagEnd = strXhtml.indexOf("<content") + 8;
		nContentStartTagEnd = strXhtml.indexOf(">", nContentStartTagEnd) + 1;
		var nContentEndTagStart = strXhtml.lastIndexOf("</content>");
		strXhtml = strXhtml.substring(nContentStartTagEnd, nContentEndTagStart);
		bReturn = saveToLocalFile(strBodyXMLPath, strXhtml);
		return bReturn;
//	}
//	else
//		return false;
}

function readBodyFormXML(strBodyXMLPath)
{
	var strFormat = "PUBDOCBODY";
	var strArguments = null;

	var bReturn = Document_HwpCtrl.Open(strBodyXMLPath, strFormat, strArguments);
	if (bReturn == false)
		return false;

	copyProposal();
	return true;
}

function getHashTable()
{
	var strFields = Document_HwpCtrl.GetFieldList();
	var arrField = strFields.split("\u0002");
	var strValues = Document_HwpCtrl.GetFieldText(strFields);
	var arrValue = strValues.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = arrValue[i];
	return objDic;
}

function convContentString(strData)
{
	strData = "<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n" +
				"<!DOCTYPE pubdoc [<!ENTITY nbsp \"&#160;\">]>\n" +
				"<pubdoc><body>" +
				strData +
				"</body></pubdoc>";
	return strData;
}

function setFieldsText(objDic)
{
	var bReturn = true;
	var strFieldList = Document_HwpCtrl.GetFieldList();
	var arrField = strFieldList.split("\u0002");
	var nLength = arrField.length;
	var strValueList = "";
	strFieldList = "";
	for (var i = 0; i < nLength; ++i)
	{
		if (isExistKey(objDic, arrField[i]))
		{
			var strValue = getValue(objDic, arrField[i]);
			strFieldList += arrField[i] + "\u0002";
			strValueList += strValue + "\u0002";
		}
	}
	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HwpCtrl.PutFieldText(strFieldList, strValueList);
	}
	return bReturn;
}


/*
function setFieldsImage(objDic)
{
	var bReturn = true;
	var strFieldList = Document_HwpCtrl.GetFieldList();
	var arrField = strFieldList.split("\u0002");
	var nLength = arrField.length;
	for (var i = 0; i < nLength; ++i)
	{
		if (isExistKey(objDic, arrField[i]))
		{
			var strValue = getValue(objDic, arrField[i]);
			putStampFromFile(Document_HwpCtrl, arrField[i], strValue);
		}
	}
	return bReturn;
}
*/

/// Hangul 2002 SE dedicated common methods
var g_nSenderAsFontSize = 18;
var g_objTargetHwpCtrl = null;

var g_strApprovalFieldNames = "";

var g_bSeal = true;
var g_bWithSize = false;
var g_strSealName = "";
var g_strSealPath = "";
var g_nImageWidth = 30;
var g_nImageHeight = 30;

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

function verifyHwpCtrlVersion(objHwpCtrl)
{
	if (objHwpCtrl.getAttribute("Version") == null)
	{
		alert(MSG_HWPCTRL_NOT_INSTALLED);
		return false;
	}

	var nMinVersion =  0x0507011F;	// Hangul 2002 SE update (build 2881, 2002/10/10) for Hangul 2002
	var nCurVersion = objHwpCtrl.Version;

	if (nCurVersion < nMinVersion)
	{
		var strMsg = ALERT_UPDATE_RECOMMENDED + "\n\n" + CURRENT_VERSION + ": 0x" +
			nCurVersion.toString(16) + "\n" + RECOMMENDED_VERSION + ": 0x" +
			nMinVersion.toString(16) + " " + AND_HIGHER;

		alert(strMsg);
	}
	return true;
}

function initToolbar(objHwpCtrl, nType)
{
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_MENU");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_STANDARD");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_FORMAT");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_DRAW");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_TABLE");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_IMAGE");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_HEADERFOOTER");
}

function changeEditMode(objHwpCtrl, nEditMode)
{
	if ((nEditMode & 0x01) != 0 && objHwpCtrl.FieldExist(FIELD_BODY))
	{
		objHwpCtrl.MoveToField(FIELD_BODY);
		if ((objHwpCtrl.CurFieldState & 0x0f) == 0x02)
			nEditMode = 0x02;
	}

	var vpSet = objHwpCtrl.ViewProperties;
	if ((nEditMode & 0x01) != 0 || (nEditMode & 0x02) != 0)
	{
		vpSet.SetItem("OptionFlag", 0x04 + 0x08);
		objHwpCtrl.ViewProperties = vpSet;
	}
	else
	{
		vpSet.SetItem("OptionFlag", 0);
		objHwpCtrl.ViewProperties = vpSet;
	}

	objHwpCtrl.EditMode = nEditMode;
}

function showToolbar(objHwpCtrl, nEditMode)
{
	if (nEditMode == 0)
		objHwpCtrl.ShowToolbar(false);
	else if ((nEditMode & 0x01) != 0 || (nEditMode & 0x02) != 0)
		objHwpCtrl.ShowToolBar(true);
}

function addProposal(objHwpCtrl)
{
	var diAction, diSet;
	var tableAction, tableSet;
	var nCurPara, nCurPos;
	var b2002 = false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var strField = FIELD_SENDER_AS + "#" + getBodyCount();
	if (objHwpCtrl.FieldExist(strField))
	{
		objHwpCtrl.MoveToField(strField);

		if (getBodyCount() == 1)
		{
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
			else
				objHwpCtrl.MoveToField(strField);

			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);

			objHwpCtrl.MovePos(26);
			diAction = objHwpCtrl.CreateAction("DocumentInfo");
			diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

			// copy
			var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
			var saveBlockSet = saveBlockAction.CreateSet();
			saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
			saveBlockSet.SetItem("Format", "HWP");
			saveBlockAction.Execute(saveBlockSet);
			objHwpCtrl.Run("Delete");

			objHwpCtrl.MovePos(3);			

			// paste
			objHwpCtrl.Insert(getDownloadPath() + "clipboard.hwp");

			if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
			{
//				objHwpCtrl.MoveToField(strField);
//				objHwpCtrl.MovePos(26);

				// ���๮��...
				objHwpCtrl.MoveToField(strField);
				if (b2002)
					objHwpCtrl.MovePos(24);
				
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				tableSet.SetItem("TreatAsChar", 1);
				tableAction.Execute(tableSet);
			}
		}

		objHwpCtrl.MovePos(26);

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nCurPara = diSet.Item("CurPara");
		nCurPos = diSet.Item("CurPos");

		objHwpCtrl.MovePos(0, nCurPara, nCurPos + 8);
	}
	else
	{
		objHwpCtrl.MovePos(3);
	}

	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/Proposal.hwp");

	var nProposalCount = getBodyCount() + 1;

	objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (nProposalCount - 2) + "}}", "" + nProposalCount + "\u0002");

	objHwpCtrl.RenameField(FIELD_RECIPIENT + "{{1}}\u0002" + FIELD_REFERER + "{{1}}\u0002" + FIELD_TITLE + "{{1}}\u0002" + FIELD_SENDER_AS + "\u0002" + FIELD_RECIPIENT_REFER,
						FIELD_RECIPIENT + nProposalCount + "\u0002" + FIELD_REFERER + nProposalCount + "\u0002" + FIELD_TITLE + nProposalCount + "\u0002" + FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount);

//	enableAdjustTable(false);

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function addProposal2(objHwpCtrl)
{
	var diAction, diSet;
	var tableAction, tableSet;
	var nCurPara, nCurPos;
	var b2002 = false;
	var bBottomTableExist = false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var strField = FIELD_SENDER_AS + "#" + getBodyCount();
	bBottomTableExist = objHwpCtrl.FieldExist(strField);
	if (!bBottomTableExist)
	{
		strField = FIELD_RECIPIENT_REFER + "#" + getBodyCount();
		bBottomTableExist = objHwpCtrl.FieldExist(strField);
	}
	
	if (bBottomTableExist)
	{
		objHwpCtrl.MoveToField(strField);
		
		if (getBodyCount() == 1)
		{
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
			else
				objHwpCtrl.MoveToField(strField);

			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);

			objHwpCtrl.MovePos(26);
			diAction = objHwpCtrl.CreateAction("DocumentInfo");
			diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

			// copy
			var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
			var saveBlockSet = saveBlockAction.CreateSet();
			saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
			saveBlockSet.SetItem("Format", "HWP");
			saveBlockAction.Execute(saveBlockSet);
			objHwpCtrl.Run("Delete");

			objHwpCtrl.MovePos(3);			

			// paste
			objHwpCtrl.Insert(getDownloadPath() + "clipboard.hwp");

			if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
			{
//				objHwpCtrl.MoveToField(strField);
//				objHwpCtrl.MovePos(26);

				// ���๮��...
				objHwpCtrl.MoveToField(strField);
				if (b2002)
					objHwpCtrl.MovePos(24);
				
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				tableSet.SetItem("TreatAsChar", 1);
				tableAction.Execute(tableSet);
			}			
		}

		objHwpCtrl.MovePos(26);
	}
	else
	{
		objHwpCtrl.MovePos(3);
	}

	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/Proposal2.hwp");

	var nProposalCount = getBodyCount() + 1;

	objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (nProposalCount - 2) + "}}", "" + nProposalCount + "\u0002");

	objHwpCtrl.RenameField(FIELD_RECIPIENT_REFER + "\u0002" + FIELD_RECIPIENT + "{{1}}\u0002" + FIELD_REFERER + "{{1}}\u0002" + FIELD_TITLE + "{{1}}\u0002" + FIELD_SENDER_AS + "#" + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1),
						FIELD_RECIPIENT_REFER + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT + nProposalCount + "\u0002" + FIELD_REFERER + nProposalCount + "\u0002" + FIELD_TITLE + nProposalCount + "\u0002" + FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount);

	var strReceiptBackup = objHwpCtrl.GetFieldText(FIELD_RECIPIENT_REFER + "#" + nProposalCount);
	
	objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1), strReceiptBackup);
	
	objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + nProposalCount, "");

//	enableAdjustTable(false);

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

// nIndex: zero base
function deleteProposal(objHwpCtrl, nIndex)
{
	var nProposalCount = getBodyCount();

	if (nIndex < 1 || nIndex >= nProposalCount)
		return false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var diAction, diSet;

	var strField = FIELD_TITLE + (nIndex + 1);			
	objHwpCtrl.MoveToField(strField);		
	objHwpCtrl.MovePos(26);		
	diAction = objHwpCtrl.CreateAction("DocumentInfo");
	diSet = diAction.CreateSet();
	diAction.Execute(diSet);

	nStartPara = diSet.Item("CurPara");
	nStartPos = diSet.Item("CurPos");

	if (nIndex == nProposalCount - 1)
	{
		objHwpCtrl.MovePos(3);
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");					
	}
	else
	{
		strField = FIELD_TITLE + (nIndex + 2);
		objHwpCtrl.MoveToField(strField);
		objHwpCtrl.MovePos("26");
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");
	}

	objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	objHwpCtrl.Run("Delete");

	for (var i = nIndex; i < nProposalCount; i++)
	{
		objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (i - 1) + "}}", "" + (i + 1) + "\u0002");
		objHwpCtrl.RenameField(FIELD_RECIPIENT + (i + 1) + "\u0002" + FIELD_REFERER + (i + 1) + "\u0002" + FIELD_TITLE + (i + 1) + "\u0002" + FIELD_SENDER_AS + "#" + (i + 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (i + 1),
							FIELD_RECIPIENT + i + "\u0002" + FIELD_REFERER + i + "\u0002" + FIELD_TITLE + i + "\u0002" + FIELD_SENDER_AS + "#" + i + "\u0002" + FIELD_RECIPIENT_REFER + "#" + i);
	}

/*
	if (getBodyCount() == 1)
	 	enableAdjustTable(true);
*/
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function deleteProposal2(objHwpCtrl, nIndex)
{
	var nProposalCount = getBodyCount();

	if (nIndex < 1 || nIndex >= nProposalCount)
		return false;

//	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var diAction, diSet;
	var bChangeLastTable = false;
	var strReceiptBackup;

	if (nIndex == nProposalCount - 1)
	{
		strField = FIELD_RECIPIENT_REFER + "#" + nIndex;
		if (objHwpCtrl.FieldExist(strField))
		{
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}
		else
		{
			strField = FIELD_TITLE + (nIndex + 1);
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nStartPara = diSet.Item("CurPara");
		nStartPos = diSet.Item("CurPos");

		var bLastTableExist = false;
		
		strField = FIELD_SENDER_AS + "#" + (nIndex + 1);
		bLastTableExist = objHwpCtrl.FieldExist(strField);
		if (!bLastTableExist)
		{
			strField = FIELD_RECIPIENT_REFER + "#" + (nIndex + 1);
			bLastTableExist = objHwpCtrl.FieldExist(strField);
		}

		if (bLastTableExist)
		{
			bChangeLastTable = true;
			strReceiptBackup = objHwpCtrl.GetFieldText(FIELD_RECIPIENT_REFER + "#" + nIndex);
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}
		else
		{
			objHwpCtrl.MovePos(3);
		}

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");		
	}
	else
	{
		strField = FIELD_TITLE + (nIndex + 1);
		objHwpCtrl.MoveToField(strField);
		
		objHwpCtrl.MovePos(26);
		
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nStartPara = diSet.Item("CurPara");
		nStartPos = diSet.Item("CurPos");

		strField = FIELD_TITLE + (nIndex + 2);
		objHwpCtrl.MoveToField(strField);
		
		objHwpCtrl.MovePos(26);
		
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");
	}

	objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	objHwpCtrl.Run("Delete");

	for (var i = nIndex; i < nProposalCount; i++)
	{
		objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (i - 1) + "}}", "" + (i + 1) + "\u0002");
		objHwpCtrl.RenameField(FIELD_RECIPIENT + (i + 1) + "\u0002" + FIELD_REFERER + (i + 1) + "\u0002" + FIELD_TITLE + (i + 1) + "\u0002" + FIELD_SENDER_AS + "#" + (i + 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (i + 1),
							FIELD_RECIPIENT + i + "\u0002" + FIELD_REFERER + i + "\u0002" + FIELD_TITLE + i + "\u0002" + FIELD_SENDER_AS + "#" + i + "\u0002" + FIELD_RECIPIENT_REFER + "#" + i);
	}

	if (bChangeLastTable)
	{
		objHwpCtrl.RenameField(FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount,
							FIELD_SENDER_AS + "#" + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1));
		objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1), strReceiptBackup);
	}

/*
	if (getBodyCount() == 1)
	 	enableAdjustTable(true);
*/

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function saveHwpDocument(objHwpCtrl, strFilePath, bAdjust)
{
	var tableAction, diAction;
	var tableSet, diSet;
	var nCurPara, nCurPos, nBottomPara, nBottomPos;
	var b2002 = false;

	objHwpCtrl.MovePos(2);

	if (bAdjust)
	{
		var bBodyExist = objHwpCtrl.FieldExist(FIELD_BODY);
		var bSenderAsExist = objHwpCtrl.FieldExist(FIELD_SENDER_AS + "#1");
		if (bSenderAsExist)
		{
			objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
		}

		// no body field - adjust
		if (!bBodyExist)
		{
			// title field - refer to as form
			if (objHwpCtrl.FieldExist(FIELD_TITLE))
			{
				// move top table
				objHwpCtrl.MoveToField(FIELD_TITLE);
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
				{
					objHwpCtrl.MovePos(26);
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diAction.Execute(diSet);

					nCurPara = diSet.Item("CurPara");
					nCurPos = diSet.Item("CurPos");
					if (nCurPara != 0 && nCurPos != 16)
					{
						objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

						objHwpCtrl.MovePos(2);
						objHwpCtrl.focus();
					}
				}

				// move bottom table
				if (getBodyCount() == 1 && bSenderAsExist)
				{
					objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
					if (b2002)
						objHwpCtrl.MoveToField(24);
					tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
					tableSet = tableAction.CreateSet();
					tableAction.GetDefault(tableSet);
					if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
					{
						objHwpCtrl.MovePos(26);
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nCurPara = diSet.Item("CurPara");
						nCurPos = diSet.Item("CurPos");
					
						objHwpCtrl.MovePos(3);
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nBottomPara = diSet.Item("CurPara");
						nBottomPos = diSet.Item("CurPos");
						
						if (nCurPara != nBottomPara || nCurPos != nBottomPos - 8)
						{
							objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

							// copy
							var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
							var saveBlockSet = saveBlockAction.CreateSet();
							saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
							saveBlockSet.SetItem("Format", "HWP");
							saveBlockAction.Execute(saveBlockSet);
							objHwpCtrl.Run("Delete");

							objHwpCtrl.MovePos(3);
							objHwpCtrl.focus();

							// paste
							objHwpCtrl.Insert(getDownloadPath() + "clipboard.hwp");
						}

						objHwpCtrl.focus();
/*
						if (!b2002)
						{
							// remove white space after bottom table
							var nBottomPara, nBottomPos, nCurPara, nCurPos, nPrevPara, nPrevPos;
							objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
							var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
							var tableSet = tableAction.CreateSet();
							tableAction.GetDefault(tableSet);
							if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
							{
								objHwpCtrl.MovePos(26);
								var diAction = objHwpCtrl.CreateAction("DocumentInfo");
								var diSet = diAction.CreateSet();
								diAction.Execute(diSet);

								nCurPara = diSet.Item("CurPara");
								nCurPos = diSet.Item("CurPos");

								nBottomPara = nCurPara;
								nBottomPos = nCurPos;

								do {
									// move to previous character
									objHwpCtrl.MovePos(15);

									var diAction = objHwpCtrl.CreateAction("DocumentInfo");
									var diSet = diAction.CreateSet();
									diAction.Execute(diSet);
									
									nPrevPara = diSet.Item("CurPara");
									nPrevPos = diSet.Item("CurPos");
									
									if (nCurPara == (nPrevPara + 1) && nCurPos == 0)
									{
										// carriage return;
									}
									else if (nCurPara == nPrevPara && nCurPos == (nPrevPos + 1))
									{
										var textSet = objHwpCtrl.CreateSet("GetText");
										objHwpCtrl.InitScan(0, 0);
										objHwpCtrl.GetTextBySet(textSet);
										objHwpCtrl.ReleaseScan();
										var strText = textSet.Item("Text");
			
										if (strText != "")
										{
											var nCharCode = strText.charCodeAt(0);
//											if (nCharCode == 0x0010 || nCharCode == 0x0013 || nCharCode == 0x0020 || nCharCode == 0x0009)
											if (nCharCode > 0x0020)
												break;
										}
									}
									nCurPara = nPrevPara;
									nCurPos = nPrevPos;
								} while (nCurPara > 0);
		
								// delete
								if (nCurPara != nBottomPara || nCurPos != nBottomPos)
								{
									objHwpCtrl.SelectText(nCurPara, nCurPos, nBottomPara, nBottomPos);
									objHwpCtrl.Run("Delete");
								}
							}	// remove white space after bottom table end
						}	// b2002 false end
*/
					}
				}	// move bottom table end

				// change table property into treat as char
				if (bSenderAsExist)
				{
					objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diSet.SetItem("DetailInfo", 1);
					diAction.Execute(diSet);
					if (diSet.Item("DetailCurPage") > 0)
					{
						objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
						if (b2002)
							objHwpCtrl.MovePos(24);
						tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
						tableSet = tableAction.CreateSet();
						tableAction.GetDefault(tableSet);
						tableSet.SetItem("TreatAsChar", 1);
						tableAction.Execute(tableSet);
					}
				}

			}	// title field exist end
			else	// no title field - memo
			{
				var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
				var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
				
				if (bDraftSign || bFinalSign)
				{
					if (bDraftSign)
						objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
					else if (bFinalSign)
						objHwpCtrl.MoveToField(FIELD_LAST + "1");
		
					objHwpCtrl.MovePos(26);
					
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diAction.Execute(diSet);

					nCurPara = diSet.Item("CurPara");
					nCurPos = diSet.Item("CurPos");

					if (nCurPara != 0 || nCurPos != 16)
					{
						objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

						// copy
						var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
						var saveBlockSet = saveBlockAction.CreateSet();
						saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
						saveBlockSet.SetItem("Format", "HWP");
						saveBlockAction.Execute(saveBlockSet);
						objHwpCtrl.Run("Delete");

						objHwpCtrl.MovePos(2);

						// paste
						objHwpCtrl.Insert(getDownloadPath() + "clipboard.hwp");
					}
					
					var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");
					var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
					if (bDraftDate || bFinalDate)
					{
						if (bDraftDate)
							objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
						else
							objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
						
						objHwpCtrl.MovePos(26);
						
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nCurPara = diSet.Item("CurPara");
						nCurPos = diSet.Item("CurPos");

						if (nCurPara != 0 || nCurPos != (16 + 8))
						{
							objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

							// copy
							var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
							var saveBlockSet = saveBlockAction.CreateSet();
							saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
							saveBlockSet.SetItem("Format", "HWP");
							saveBlockAction.Execute(saveBlockSet);
							objHwpCtrl.Run("Delete");

							objHwpCtrl.MovePos(0, 0, 8);

							// paste
							objHwpCtrl.Insert(getDownloadPath() + "clipboard.hwp");
						}
					}
				}	// draft sign slot or final sign slot exist end
			}	// no title field - memo end
		}	// bBodyExist false end
	}	// bAdjust end
/*
	if (strFilePath == "")
	{
		var strTitle = "Body";
		strFilePath = getDownloadPath() + strTitle + ".hwp";
	}
*/

	return objHwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
}

function createApprovalHWPML(strFieldList, bDate, strHmlPath)
{
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	objXMLDom.load(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/HmlHead.xml");
	var strHml = objXMLDom.documentElement.text;

	var arrFieldList = strFieldList.split("^");
	var nApprovalCount = arrFieldList.length;
	if (nApprovalCount > 0)
	{
		var nCellWidth = 4300, nCellHeight1 = 1700, nCellHeight2 = 5400;
		if (nApprovalCount > 10)
			nCellWidth = 43000 / nApprovalCount;
		
		strHml += "<TABLE PageBreak=\"Cell\" RepeatHeader=\"true\" RowCount=\"2\" ColCount=\""
				+ nApprovalCount
				+ "\" CellSpacing=\"0\" borderFill=\"1\">\r\n"
				+ "<SHAPEOBJECT ZOrder=\"4\" NumberingType=\"Table\" TextWrap=\"TopAndBottom\" Lock=\"false\">\r\n"
				+ "<POSITION TreatAsChar=\"false\" VertRelTo=\"Page\" HorzRelTo=\"Page\" VertAlign=\"Top\" HorzAlign=\"Right\" VertOffset=\"0\" HorzOffset=\"0\" FlowWithText=\"true\" AllowOverlap=\"false\"/>\r\n"
				+ "<SIZE Width=\""
				+ nCellWidth * nApprovalCount
				+ "\" Height=\""
				+ (nCellHeight1 + nCellHeight2)
				+ "\" WidthRelTo=\"Absolute\" HeightRelTo=\"Absolute\" Protect=\"false\"/>"
				+ "<OUTSIDEMARGIN Left=\"283\" Right=\"283\" Top=\"283\" Bottom=\"283\"/>\r\n"
				+ "</SHAPEOBJECT>\r\n"
				+ "<INSIDEMARGIN Left=\"141\" Right=\"141\" Top=\"141\" Bottom=\"141\"/>\r\n"
				+ "<ROW>\r\n";

		var nIndex;
		for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
		{
			strHml += "<CELL Name=\"*"
					+ arrFieldList[nIndex]
					+ "\" ColAddr=\""
					+ nIndex
					+ "\" RowAddr=\"0\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
					+ nCellWidth
					+ "\" Height=\""
					+ nCellHeight1
					+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
					+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
					+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
					+ "</PARALIST>\r\n"
					+ "</CELL>\r\n";
		}
		strHml += "</ROW>\r\n<ROW>\r\n";
// CharShape 0: 10pt, 2: 8pt
		for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
		{
			strHml += "<CELL Name=\""
					+ arrFieldList[nIndex]
					+ "\" ColAddr=\""
					+ nIndex
					+ "\" RowAddr=\"1\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
					+ nCellWidth
					+ "\" Height=\""
					+ nCellHeight2
					+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
					+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
					+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
					+ "</PARALIST>\r\n"
					+ "</CELL>\r\n";
		}
		strHml += "</ROW>\r\n</TABLE>\r\n";

		if (bDate)
		{
			strHml += "<TABLE PageBreak=\"Cell\" RepeatHeader=\"true\" RowCount=\"1\" ColCount=\""
					+ nApprovalCount
					+ "\" CellSpacing=\"0\" borderFill=\"1\">\r\n"
					+ "<SHAPEOBJECT Id=\"1982440985\" ZOrder=\"4\" NumberingType=\"Table\" TextWrap=\"BehindText\" Lock=\"false\">\r\n"
					+ "<POSITION TreatAsChar=\"false\" VertRelTo=\"Page\" HorzRelTo=\"Page\" VertAlign=\"Top\" HorzAlign=\"Right\" VertOffset=\""
					+ (nCellHeight1 + nCellHeight2)
					+ "\" HorzOffset=\"0\" FlowWithText=\"true\" AllowOverlap=\"false\"/>\r\n"
					+ "<SIZE Width=\""
					+ nCellWidth * nApprovalCount
					+ "\" Height=\""
					+ nCellHeight2
					+ "\" WidthRelTo=\"Absolute\" HeightRelTo=\"Absolute\" Protect=\"true\"/>"
					+ "<OUTSIDEMARGIN Left=\"283\" Right=\"283\" Top=\"283\" Bottom=\"283\"/>\r\n"
					+ "</SHAPEOBJECT>\r\n"
					+ "<INSIDEMARGIN Left=\"141\" Right=\"141\" Top=\"141\" Bottom=\"141\"/>\r\n"
					+ "<ROW>\r\n";

			for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
			{
				strHml += "<CELL Name=\"@"
						+ arrFieldList[nIndex]
						+ "\" ColAddr=\""
						+ nIndex
						+ "\" RowAddr=\"0\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
						+ nCellWidth
						+ "\" Height=\""
						+ nCellHeight1
						+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
						+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
						+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
						+ "</PARALIST>\r\n"
						+ "</CELL>\r\n";
			}
			strHml += "</ROW>\r\n</TABLE>\r\n";
		}
	}
	
	strHml += "<CHAR></CHAR>\n</TEXT>\n</P>\n</SECTION>\n</BODY>\n</HWPML>";
	FileTransfer.SaveToLocalFile(strHmlPath, strHml);
}

function makeApprovalFields(objHwpCtrl, strFieldNames)
{
//	if (g_strApprovalFieldNames == strFieldNames)
//		return;

	var strHmlPath = getDownloadPath() + "fieldtable.hml";
	createApprovalHWPML(strFieldNames, false, strHmlPath);

	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;

	var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
	var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
	if (bDraftSign || bFinalSign)
	{
		if (bDraftSign)
			objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField(FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");		
	var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");		
	if (bDraftDate || bFinalDate)
	{
		if (bDraftDate)
			objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	objHwpCtrl.MovePos(2);	// moveTopOfFile

	objHwpCtrl.Insert(strHmlPath, "HML/KS");

	objHwpCtrl.EditMode = nEditMode;

	g_strApprovalFieldNames = strFieldNames;
}

function removeApprovalFields(objHwpCtrl)
{
	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;

	var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
	var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
	if (bDraftSign || bFinalSign)
	{
		if (bDraftSign)
			objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField(FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");		
	var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
	if (bDraftDate || bFinalDate)
	{
		if (bDraftDate)
			objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	objHwpCtrl.EditMode = nEditMode;
}

function copyProposal(objHwpCtrl, nProposal)
{
	if (nProposal < 1)
		return;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var strField = FIELD_BODY + "{{" + (nProposal - 1) + "}}";
	if (objHwpCtrl.FieldExist(strField))
	{
		objHwpCtrl.MoveToField(strField, true, true, true);
	}
	else if (objHwpCtrl.FieldExist("pubdoc/content/body"))
	{
		objHwpCtrl.MoveToField("pubdoc/content/body", true, true, true);
	}
	else
	{
		if (objHwpCtrl.FieldExist(FIELD_TITLE))
		{
			if (nProposal == 1)
				objHwpCtrl.MoveToField(FIELD_TITLE);
			else
				objHwpCtrl.MoveToField(FIELD_TITLE + nProposal);					
			objHwpCtrl.MovePos(26);
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nStartPara = diSet.Item("CurPara");
			nStartPos = diSet.Item("CurPos") + 8;
			
			strField = FIELD_SENDER_AS + "#" + nProposal;
			if (objHwpCtrl.FieldExist(strField))
			{
				objHwpCtrl.MoveToField(strField);
				objHwpCtrl.MovePos(26);
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				nEndPara = diSet.Item("CurPara");
				nEndPos = diSet.Item("CurPos");
			}
			else
			{
				strField = FIELD_TITLE + (nProposal + 1);
				if (objHwpCtrl.FieldExist(strField))
				{
					objHwpCtrl.MoveToField(strField);
					objHwpCtrl.MovePos(26);
				}
				else
				{
					objHwpCtrl.MovePos(3);
				}		
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				nEndPara = diSet.Item("CurPara");
				nEndPos = diSet.Item("CurPos");
			}
		}
		else
		{
			var bDraftSign, bFinalSign, bDraftDate, bFinalDate;
			bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
			bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
			bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");
			bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
			
			nStartPara = 0;
			nStartPos = 16;
			if (bDraftSign || bFinalSign)
			{
				if (bDraftDate || bFinalDate)
					nStartPos += 16;
				else
					nStartPos += 8;
			}

			objHwpCtrl.MovePos(3);

			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nEndPara = diSet.Item("CurPara");
			nEndPos = diSet.Item("CurPos");
		}
		
		objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	}	

	// copy or save ...
	var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
	var saveBlockSet = saveBlockAction.CreateSet();
	saveBlockSet.SetItem("FileName", getDownloadPath() + "clipboard.hwp");
	saveBlockSet.SetItem("Format", "HWP");
	saveBlockAction.Execute(saveBlockSet);

	objHwpCtrl.MovePos(0, nEndPara, nEndPos);
}

function pasteProposal(objHwpCtrl)
{
	insertAux(objHwpCtrl, getDownloadPath() + "clipboard.hwp", 1);
}
	
// nPos: 0 = before body, 1 = replace body, 2 = after body
function insertAux(objHwpCtrl, strFilePath, nPos)
{
	var b2002 = false;
	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;
	var bSenderAsExist = objHwpCtrl.FieldExist(FIELD_SENDER_AS + "#1");
	if (bSenderAsExist)
	{
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		objHwpCtrl.MovePos(24);
		var parentCtrl = objHwpCtrl.ParentCtrl;
		if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
			b2002 = true;
	}
	var bBodyExist = objHwpCtrl.FieldExist(FIELD_BODY);
	if (bBodyExist)
	{
		if (nPos == 0)
			objHwpCtrl.MoveToField(FIELD_BODY);
		else if (nPos == 1)
			objHwpCtrl.MoveToField(FIELD_BODY, true, true, true);
		else if (nPos == 2)
			objHwpCtrl.MoveToField(FIELD_BODY, true, false);
	}
	else
	{
		var nStartPara, nStartPos, nEndPara, nEndPos;
		if (nPos == 0 || nPos == 1)
		{
			objHwpCtrl.MoveToField(FIELD_TITLE);
			objHwpCtrl.MovePos(26);

			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nStartPara = diSet.Item("CurPara");
			nStartPos = diSet.Item("CurPos") + 8;
		}
		
		if (nPos == 1 || nPos == 2)
		{
			var strField = FIELD_SENDER_AS + "#1";
			if (bSenderAsExist)
			{
				objHwpCtrl.MoveToField(strField);
				objHwpCtrl.MovePos(26);
			}
			else
			{
				objHwpCtrl.MovePos(3);
			}
			
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nEndPara = diSet.Item("CurPara");
			nEndPos = diSet.Item("CurPos") + 8;
		}
		
		if (nPos == 0)
		{
			objHwpCtrl.MovePos(0, nStartPara, nStartPos);
		}
		else if (nPos == 1)
		{
			objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
			objHwpCtrl.Run("Delete");
		}
		else if (nPos == 2)
		{
			objHwpCtrl.MovePos(0, nEndPara, nEndPos);
		}
	}
	
	objHwpCtrl.Insert(strFilePath);

	// ���� ����	
	if (bBodyExist && (nPos == 1 || nPos == 2))
	{
		objHwpCtrl.MoveToField(FIELD_BODY, true, false);
		objHwpCtrl.Run("BreakPara");
	}
/*
	// remove whitespace before bottom table
	if (!bBodyExist && getBodyCount() == 1 && bSenderAsExist)
	{
		var nBottomPara, nBottomPos, nCurPara, nCurPos, nPrevPara, nPrevPos;
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		if (b2002)
			objHwpCtrl.MovePos(24);
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
		var tableSet = tableAction.CreateSet();
		tableAction.GetDefault(tableSet);
		if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
		{
			objHwpCtrl.MovePos(26);
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			nBottomPara = nCurPara;
			nBottomPos = nCurPos;
			
			do {
				objHwpCtrl.MovePos(15);
				
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				
				nPrevPara = diSet.Item("CurPara");
				nPrevPos = diSet.Item("CurPos");
				
				if (nCurPara == (nPrevPara + 1) && nCurPos == 0)
				{
					// carriage return;
				}
				else if (nCurPara == nPrevPara && nCurPos == (nPrevPos + 1))
				{
					var textSet = objHwpCtrl.CreateSet("GetText");
					objHwpCtrl.InitScan(0, 0);
					objHwpCtrl.GetTextBySet(textSet);
					objHwpCtrl.ReleaseScan();
					var strText = textSet.Item("Text");

					if (strText != "")
					{
						var nCharCode = strText.charCodeAt(0);
//						if (nCharCode == 0x0010 || nCharCode == 0x0013 || nCharCode == 0x0020 || nCharCode == 0x0009)
						if (nCharCode > 0x0020)
							break;
					}
				}
				nCurPara = nPrevPara;
				nCurPos = nPrevPos;
			} while (nCurPara > 0);

			// delete
			if (nCurPara != nBottomPara || nCurPos != nBottomPos)
			{
				objHwpCtrl.SelectText(nCurPara, nCurPos, nBottomPara, nBottomPos);
				objHwpCtrl.Run("Delete");
			}
		}
	}	// remove whitespace before bottom table end
*/

	if (bSenderAsExist)
	{
//					objHwpCtrl.MovePos(3);
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diSet.SetItem("DetailInfo", 1);
		diAction.Execute(diSet);

		if (diSet.Item("DetailCurPage") > 0)
		{
			objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
			if (b2002)
				objHwpCtrl.MovePos(24);
			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);
			tableSet.SetItem("TreatAsChar", 1);
			tableAction.Execute(tableSet);
		}
	}

	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0;
}			

function putStampFromFile(objHwpCtrl, strFieldName, strFilePath, bReplace)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;

	objHwpCtrl.MoveToField(strFieldName, true, true, false);

//	objHwpCtrl.InsertBackgroundPicture("SelectedCell", strFilePath);

/*
InsertPicture(BSTR path, [boolean embeded], [short sizeoption], [boolean reverse], [boolean watermark], [short effect], [long width], [long height])
sizeoption :
	// �̹��� ���� ũ��� �����Ѵ�. width�� height�� ������ �ʿ���.
	0,
	// width�� height�� ������ ũ��� �׸��� �����Ѵ�.
	1,
	// ���� ĳ���� ǥ�� ���ȿ� ���� ���, ���� ũ�⿡ �°� �ڵ� �����Ͽ� �����Ѵ�.
	// width�� ���� width��ŭ, height�� ���� height��ŭ Ȯ��/��ҵȴ�.
	// ĳ���� ���ȿ� ���� ������ �̹����� �� ũ���� ���Եȴ�.
	cellSize = 2,
	// ���� ĳ���� ǥ�� ���ȿ� ���� ���, ���� ũ�⿡ ���߾� �� �̹����� ���� ������ ������ �����ϰ� Ȯ��/����Ͽ� �����Ѵ�. 
	cellSizeWithSameRatio = 3

*/

	objHwpCtrl.Run("SelectAll");
	objHwpCtrl.Run("Delete");
	objHwpCtrl.InsertPicture(strFilePath, true, 3);
}

function putStampFromFileWithSize(objHwpCtrl, strFieldName, strFilePath, bReplace, nImageWidth, nImageHeight)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;

	objHwpCtrl.MoveToField(strFieldName, true, true, false);

//	objHwpCtrl.InsertBackgroundPicture("SelectedCell", strFilePath);

	objHwpCtrl.Run("SelectAll");
	objHwpCtrl.Run("Delete");
	objHwpCtrl.InsertPicture(strFilePath, true, 1, false, false, 0, nImageWidth, nImageHeight);
}


function insertSealHwp(objHwpCtrl, strSealName)
{
	objHwpCtrl.MovePos(3);
	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/stamp.hwp");
	objHwpCtrl.RenameField(FIELD_STAMP, strSealName);
}

function insertOmitSealHwp(objHwpCtrl, strSealName)
{
	objHwpCtrl.MovePos(3);
	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/omitstamp.hwp");
	objHwpCtrl.RenameField(FIELD_OMIT_STAMP, strSealName);				
}

function adjustSealPos(objHwpCtrl, strSealName, strTextName, nFontSize) 
{
	var b2002 = false;
	objHwpCtrl.MoveToField(strTextName);
	objHwpCtrl.MovePos(24);
	var parentCtrl = objHwpCtrl.ParentCtrl;
	if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
		b2002 = true;
	
	if (!b2002)
		return;

	objHwpCtrl.MoveToField(strTextName);
	var strText = objHwpCtrl.GetFieldText(strTextName);
	strText.replace(/[ \t]*$/, "");
	var nTextSize = 0;
	for (var i = 0; i < strText.length; i++)
	{
		if ((strText.charCodeAt(i) & 0xff00) != 0)
			nTextSize += 2;
		else
			nTextSize += 1;
	}
	var nCharOffset = nTextSize / 2 - 1;
	var nFontWidth = nFontSize * 50;
	var nHorizontalAdjust = nCharOffset * nFontWidth;

	var nVerticalAdjust = (8500 - nFontWidth) / 2 - 840;

	objHwpCtrl.MoveToField(strSealName);
	var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
	var tableSet = tableAction.CreateSet();
	tableAction.GetDefault(tableSet);

	// horizontal align relative to enclosing table
	if (tableSet.Item("HorzRelTo") != 3)
		tableSet.SetItem("HorzRelTo", 3);
	// center alignment
	tableSet.SetItem("HorzAlign", 1);
	tableSet.SetItem("HorzOffset", nHorizontalAdjust);
	tableAction.Execute(tableSet);
}

function onClickForSealOnHwp(x, y)
{
	var objHwpCtrl = g_objTargetHwpCtrl;

	var mousePosSet = objHwpCtrl.GetMousePos(0, 0);
	var xrelto = mousePosSet.Item("XRelto");
	var yrelto = mousePosSet.Item("YRelTo");
	var page = mousePosSet.Item("Page");
	var pagex = mousePosSet.Item("X");
	var pagey = mousePosSet.Item("Y");
	mousePosSet = objHwpCtrl.GetMousePos(1, 1);
	var paperx = mousePosSet.Item("X");
	var papery = mousePosSet.Item("Y");

	if (g_bSeal)
	{
		insertSealHwp(objHwpCtrl, g_strSealName);
		paperx -= 29900;
		papery -= 9200;
	}
	else
	{
		insertOmitSealHwp(objHwpCtrl, g_strSealName);
		paperx -= 29900;
		papery -= 3100;
	}

	objHwpCtrl.MoveToField(g_strSealName);
	var tableCtrl = objHwpCtrl.ParentCtrl;
	if (tableCtrl != null && tableCtrl.CtrlID == "tbl")
	{
		var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
		var tableSet = tableAction.CreateSet();
		tableAction.GetDefault(tableSet);
		tableSet.SetItem("VertRelTo", 1); // 0 : Paper, 1: Page
		tableSet.SetItem("HorzRelTo", 1);
		tableSet.SetItem("HorzOffset", paperx);
		tableSet.SetItem("VertOffset", papery);
		tableCtrl.Properties = tableSet;
	}

	if (g_bWithSize)
		putStampFromFileWithSize(objHwpCtrl, g_strSealName, g_strSealPath, true, g_nImageWidth, g_nImageHeight);
	else
		putStampFromFile(objHwpCtrl, g_strSealName, g_strSealPath, true);

	objHwpCtrl.detachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putSealFromFileEx(objHwpCtrl, strFieldName, strFilePath)
{
	g_bSeal = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_objTargetHwpCtrl = objHwpCtrl;
	
	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putSealFromFileExWithSize(objHwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight)
{
	g_bSeal = true;
	g_bWithSize = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_nImageWidth = nImageWidth;
	g_nImageHeight = nImageHeight
	g_objTargetHwpCtrl = objHwpCtrl;
	
	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putOmitSealFromFileEx(objHwpCtrl, strFieldName, strFilePath)
{
	g_bSeal = false;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;

	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putOmitSealFromFileExWithSize(objHwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight)
{
	g_bSeal = false;
	g_bWithSize = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_nImageWidth = nImageWidth;
	g_nImageHeight = nImageHeight;

	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function slashCell(objHwpCtrl, strFieldName, bDraw)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;
	objHwpCtrl.MoveToField(strFieldName);
	
	var borderAction = objHwpCtrl.CreateAction("CellBorderFill");
	var borderSet = borderAction.CreateSet();
	borderAction.GetDefault(borderSet);
	borderSet.SetItem("ApplyTo", 0);
	borderSet.SetItem("SlashFlag", (bDraw ? 2 : 0));
	borderSet.SetItem("DiagonalType", 1);
	borderAction.Execute(borderSet);
}

function clearStamp(objHwpCtrl, strFieldName)
{
	if (objHwpCtrl.FieldExist(strFieldName))
	{
		objHwpCtrl.MoveToField(strFieldName, true, true, false);

		objHwpCtrl.Run("SelectAll");
		objHwpCtrl.Run("Delete");
/*
		objHwpCtrl.Run("TableCellBlock");
		objHwpCtrl.InsertBackgroundPicture("SelectedCellDelete", "");
*/
	}
}

function setBorderLine(objHwpCtrl, strFieldName, bDraw)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;
	objHwpCtrl.MoveToField(strFieldName);
	
	var borderAction = objHwpCtrl.CreateAction("CellBorderFill");
	var borderSet = borderAction.CreateSet();
	borderAction.GetDefault(borderSet);
	borderSet.SetItem("ApplyTo", 0);

	var nBorderType = (bDraw ? 1 : 0);

	borderSet.SetItem("BorderTypeLeft", nBorderType);
	borderSet.SetItem("BorderTypeRight", nBorderType);
	borderSet.SetItem("BorderTypeTop", nBorderType);
	borderSet.SetItem("BorderTypeBottom", nBorderType);
/*
	var nBorderWidth = 0;
	borderSet.SetItem("BorderWidthLeft", nBorderWidth);
	borderSet.SetItem("BorderWidthRight", nBorderWidth);
	borderSet.SetItem("BorderWidthTop", nBorderWidth);
	borderSet.SetItem("BorderWidthBottom", nBorderWidth);
*/
	borderAction.Execute(borderSet);
}

// icaha, document switching
onBodyAccessLoadingCompleted();
