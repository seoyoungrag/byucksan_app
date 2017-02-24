
var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";
var g_strEnforceFormName = "";
var g_bIsEnforceModified = false;

function getEnforceObjectHTML()
{
	var strHTML = "<OBJECT id='Enforce_Hun2KLa' width='100%' height='100%' classid='CLSID:3CCEE269-A6D2-11D2-BF35-00A0C98C39D8'>";
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
		if (strDocCategory == "E" || strDocCategory == "W" || strDocCategory == "T")
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
	var objEnforceFile = getEnforceFileObj(g_nEnforceProposal);

	if (objEnforceFile == null)
	{
		objEnforceFile = makeEnforceDocument(g_strEnforceFormUrl, g_nEnforceProposal);
		if (objEnforceFile == null)
			return;
	}
	else
	{
		downloadEnforceFile(g_nEnforceProposal);
	}

	Enforce_Hun2KLa.CloseDocument();

	if (g_strEditType == "0")
	{
		Enforce_Hun2KLa.SetEdit(1);
		Enforce_Hun2KLa.SetToolbar(1);
		Enforce_Hun2KLa.SetCopy(1);
		Enforce_Hun2KLa.SetPrint(1);

		g_bIsModified = true;
	}
	else
	{
		Enforce_Hun2KLa.SetEdit(0);
		Enforce_Hun2KLa.SetToolbar(0);
		Enforce_Hun2KLa.SetCopy(1);
		Enforce_Hun2KLa.SetPrint(1);

		g_bIsModified = false;
	}

	var strFileName = getAttachFileName(objEnforceFile);

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		Enforce_Hun2KLa.OpenDocument(strLocalPath, 0);
	}
	else
		Enforce_Hun2KLa.NewDocument();

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

	Document_Hun2KLa.SetZoomFactor(2);
	Enforce_Hun2KLa.SetZoomFactor(2);

//	Enforce_Hun2KLa.SetFormPlayMode(true);
	Enforce_Hun2KLa.focus();
}

function closeEnforceDocument()
{
	Enforce_Hun2KLa.CloseDocument();
}

function makeEnforceDocument(strEnforceFormUrl, nCaseNumber)
{
	var objEnforceFile = null;

	if (strEnforceFormUrl == "")
		return objEnforceFile;

	var strDocCategory = getDocCategory(nCaseNumber);

	if (strDocCategory != "E" && strDocCategory != "W")
		return objEnforceFile;

	objEnforceFile = getEnforceFileObj(nCaseNumber)
	if (objEnforceFile == null)
	{
		Enforce_Hun2KLa.CloseDocument();

		Enforce_Hun2KLa.SetEdit(1);
		Enforce_Hun2KLa.SetToolbar(1);
		Enforce_Hun2KLa.SetCopy(1);
		Enforce_Hun2KLa.SetPrint(1);

		if (strEnforceFormUrl != "")
		{
			var bRet = Enforce_Hun2KLa.OpenDocument(g_strDownloadPath + strEnforceFormUrl, 0);

			setEnforceXmlDataToForm();
			setEnforceRecipientsToForm();
		}

		Document_Hun2KLa.CopyEnforceDocEx(nCaseNumber, true);
		Enforce_Hun2KLa.PasteEnforceDoc(true);
		Enforce_Hun2KLa.ClearEnforceClipBoard();

		var strRecordNames = Document_Hun2KLa.GetAllRecordNames("^", 1);
		var arrRecordName = strRecordNames.split("^");
		if (arrRecordName.length != 0)
		{
			for (var i = 0 ; i < arrRecordName.length ; i++)
			{
				var strRecordName = arrRecordName[i];

				if (strRecordName.indexOf(FIELD_LAST) == -1 && strRecordName.indexOf(FIELD_DRAFTER) == -1 &&
					strRecordName.indexOf(FIELD_CONSIDER) == -1 && strRecordName.indexOf(FIELD_READER) == -1 &&
					strRecordName.indexOf(FIELD_COOPERATE) == -1 && strRecordName.indexOf(FIELD_ARBITRARY) == -1 &&
					strRecordName.indexOf(FIELD_PROXY) == -1 && strRecordName.indexOf(FIELD_DEPART_INSPECT) == -1)

				{
					if (Enforce_Hun2KLa.IsExistRecord(strRecordName))
					{
						var strData = Document_Hun2KLa.GetRecordTextByName(strRecordName);
						Enforce_Hun2KLa.SetRecordTextByName(strRecordName, -1, strData);
					}
				}
			}

			if (nCaseNumber > 1)
			{
				var strField = FIELD_TITLE;
				if (Enforce_Hun2KLa.IsExistRecord(strField))
				{
					var strData = Document_Hun2KLa.GetRecordTextByName(strField + nCaseNumber);
					Enforce_Hun2KLa.SetRecordTextByName(strField, -1, strData);
				}
			}
		}

		objEnforceFile = setEnforceFileInfo(nCaseNumber);

		saveEnforceFile(g_nEnforceProposal)
		setEnforceModified(true);
	}
//alert(ApprovalDoc.xml);
	return objEnforceFile;
}

function setEnforceRecipientsToForm()
{
	var nCaseNumber = g_nEnforceProposal;

	var strDeptField = "", strRefField = "", strDeptRefField = "";
	var strDeptName = "", strRefName = "", strDeptRefName = "";

	strDeptField = FIELD_RECIPIENT;
	strRefField = FIELD_REFERER;
	strDeptRefField = FIELD_RECIPIENT_REFER + "#1";

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

		if (Enforce_Hun2KLa.IsExistRecord(strDeptField) == true)
		{
			var strValue = "";
			if (nRecipientCount == 0)
				strValue = strDisplayAs;
			else if (nRecipientCount == 1)
				strValue = strDeptName;
			else
				strValue = RECIPIENT_REFER;

			Enforce_Hun2KLa.SetRecordTextByName(strDeptField, -1, strValue);
		}

		if (Enforce_Hun2KLa.IsExistRecord(strRefField) == true)
		{
			var strValue = "";
			if (nRecipientCount == 0)
				strValue = "";
			else if (nRecipientCount == 1)
				strValue = strRefName;
			else
			{
				if (getRefRecipientCount(objRecipGroup) > 0)
					strValue = RECIPIENT_REFER;
				else
					strValue = "";
			}

			Enforce_Hun2KLa.SetRecordTextByName(strRefField, -1, strValue);
		}

		if (Enforce_Hun2KLa.IsExistRecord(strDeptRefField) == true)
		{
			var strValue = "";
			if (nRecipientCount < 2)
				strValue = "";
			else
				strValue = strDeptRefName;
//					strValue = LABEL_RECIPIENT + ": " + strDeptRefName;

			Enforce_Hun2KLa.SetRecordTextByName(strDeptRefField, -1, strValue);
		}
	}
	else		// no RecipGroup
	{
		if (Enforce_Hun2KLa.IsExistRecord(strDeptField) == true)
		{
			var strDocCategory = getDocCategory(nCaseNumber);
			var strValue = "";
			if (strDocCategory == "I")
				strValue = INNER_APPROVAL;

			Enforce_Hun2KLa.SetRecordTextByName(strDeptField, -1, strValue);
		}

		if (Enforce_Hun2KLa.IsExistRecord(strRefField) == true)
			Enforce_Hun2KLa.SetRecordTextByName(strRefField, -1, "");

		if (Enforce_Hun2KLa.IsExistRecord(strDeptRefField) == true)
			Enforce_Hun2KLa.SetRecordTextByName(strDeptRefField, -1, "");
	}
}

function appendEnforceRecipientsToForm(nCaseNumber)
{
}

function saveEnforceFile(nCaseNumber)
{
	clearEnforceApproveFlow();

	var bRet = false;

	var objEnforceFile = getEnforceFileObj(nCaseNumber);
	var strFileName = getAttachFileName(objEnforceFile);

	if (Enforce_Hun2KLa.SaveAsDocument(g_strDownloadPath + strFileName, 0) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objEnforceFile, strFileSize);
	}

	restoreEnforceApproveFlow();

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

	clearEnforceApprovalFlow();
	clearEnforceApprovalSign();
}

function restoreEnforceApproveFlow()
{
	setEnforceApprovalSeal();

	setEnforceApprovalFlow();
	setEnforceApprovalSign();
}

function setEnforceApprovalSeal()
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

					if (Enforce_Hun2KLa.IsExistRecord(strFieldName))
					{
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
							Enforce_Hun2KLa.RearrangeEndStampRecord(-1);
							Enforce_Hun2KLa.RearrangeStampRecord(-1, FIELD_SENDER_AS + "#1", strFieldName);
						}

						if (getLocalFileSize(strFilePath) > 0)
							Enforce_Hun2KLa.SetRecordImageFromFile(strFieldName, strFilePath);
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function clearEnforceApprovalSeal()
{
}

// nSetType : 0=setApprovalFlow, 1=setApprovalSign, 2=setUserSign
// bSetData : true=set, false=clear
// strUserID : break on match
function setEnforceApprovalField(nSetType, bSetData, strUserID)
{
	return;

//	var strFormUsage = getFormUsage();
	var strFormUsage = "1";

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
		if (strFormUsage == "0")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName("0");

		if (objApprovalLine == null)
			return;

//		if (strFormUsage == "0")
			setEnforceApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
//		else
//			setEnforceApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}

function setEnforceApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var nApprove = 0;
	var nAssist = 0;
	var nInspect = 0;

	var nSerialOrder = 0;
	var nLength = getApproverCount(objApprovalLine);
	var objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);

	while(objApprover != null)
	{
		var strApprovalField = "";

		var strRole = getApproverRole(objApprover);
		var strType = getApproverType(objApprover);

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
				var strPrevRole = getApproverRole(objPrevApprover);
				var strPrevType = getApproverType(objPrevApprover);

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
				else if (strType == "1")
				{
					var objNextApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder + 1));
					var strNextType = getApproverType(objNextApprover);
					strApprovalField = FIELD_LAST;
					if (strNextType != strType)
						strApprovalField += strType + "-1";
					else
						strApprovalField += "1";
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

		if (nSetType == 0)
		{
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
				if (Enforce_Hun2KLa.IsExistRecord(strApprovalField) == false)
				{
					if (getIsForm() == "N")
						Enforce_Hun2KLa.AddApproveField(strApprovalField, nSerialOrder);
				}

				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
					var strDeptName = "";
					if (bSetData)
						strDeptName = getApproverDeptName(objApprover);

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strDeptName);
				}
				else
				{
					var strPosition = "";
					if (bSetData)
					{
						var arroption37 = g_strOption37.split("^");
						if (arroption37[0] == "GRD")
							strPosition = getApproverUserLevel(objApprover);
						if (arroption37[0] == "SGRD")
							strPosition = getApproverUserLevelAbbr(objApprover);
						if (arroption37[0] == "POS")
							strPosition = getApproverUserPosition(objApprover);
						if (arroption37[0] == "SPOS")
							strPosition = getApproverUserPositionAbbr(objApprover);
						if (arroption37[0] == "TLT")
							strPosition = getApproverUserPosition(objApprover);
					}

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strPosition);
				}

				var strFieldData = "";

				if (strRole == "6")
					strFieldData = FIELD_ARBITRARY;
				else if (strRole == "7")
					strFieldData = FIELD_PROXY;
				else if (strRole == "9")
					strFieldData = FIELD_REAR;

				Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strFieldData);

				var strApproverName = "#" + strApprovalField;
				var strUserName = "";
				if (bSetData)
					strUserName = getApproverUserName(objApprover);

				Enforce_Hun2KLa.SetRecordTextByName(strApproverName, -1, strUserName);

				var strApproverDept = "$" + strApprovalField;
				var strDeptName = "";
				if (bSetData)
					strDeptName = getApproverDeptName(objApprover);

				Enforce_Hun2KLa.SetRecordTextByName(strApproverDept, -1, strDeptName);
			}
		}
		else if (nSetType == 1 || nSetType == 2)
		{
			if (nSetType == 1)
			{
				if (bSetData)
				{
					var strAction = getApproverAction(objApprover);
					if (strAction == "8")
						Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, STATUS_OPPOSE);
					else if (strAction == "9")
						Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, STATUS_REJECT);

					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Enforce_Hun2KLa.SetRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
						}
					}
				}
				else
				{
					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
					Enforce_Hun2KLa.ClearRecordImage(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Enforce_Hun2KLa.ClearRecordImage(strApprovalField);
					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
					break;
				}
			}

			if (nSetType == 1)
			{
				if (bSetData)
				{
					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignDate = getApproverSignDate(objApprover);
						if (strSignDate != "")
						{
							var strMMDD = strSignDate;

							var nFind = strSignDate.indexOf(" ");
							if (nFind != -1)
								strSignDate = strSignDate.substring(0, nFind);

							var arrDate = strSignDate.split("-");
							var strMMDD = "";
							if (g_strOption36.indexOf("MM.DD") >= 0)
								strMMDD = arrDate[1] + "." + arrDate[2];
							else if (g_strOption36.indexOf("MM/DD") >= 0)
								strMMDD = arrDate[1] + "/" + arrDate[2];
							else if (g_strOption36.indexOf("MM-DD") >= 0)
								strMMDD = arrDate[1] + "-" + arrDate[2];
							else
								strMMDD = arrDate[1] + "." + arrDate[2];

							strApprovalField = "@" + strApprovalField;
							Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);

							if (strRole == "1")
							{
								var strDateField = FIELD_DRAFTER + "일자";
								if (strType == "1" || strType == "2")
									strDateField += strType + "-1";
								else
									strDateField += "1";

								Enforce_Hun2KLa.SetRecordTextByName(strDateField, -1, strMMDD);
							}
						}
					}
				}
				else
				{
					var strDateField = "@" + strApprovalField;
					Enforce_Hun2KLa.SetRecordTextByName(strDateField, -1, "");

					if (strRole == "1")
					{
						strDateField = FIELD_DRAFTER + "일자";
						if (strType == "1" || strType == "2")
							strDateField += strType + "-1";
						else
							strDateField += "1";

						Enforce_Hun2KLa.SetRecordTextByName(strDateField, -1, "");
					}
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					var strMMDD = "";
					strApprovalField = "@" + strApprovalField;

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);
					break;
				}
			}
		}

		nSerialOrder++;
		objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
	}
}

function setEnforceApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
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
//						strApprovalField = FIELD_DRAFTER + "1";
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
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
				if (Enforce_Hun2KLa.IsExistRecord(strApprovalField) == false)
				{
//					if (getIsForm() == "N")
//						Enforce_Hun2KLa.AddApproveField(strApprovalField, nSerialOrder);
				}

				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
					var strDeptName = "";
					if (bSetData)
						strDeptName = getApproverDeptName(objApprover);

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strDeptName);
				}
				else
				{
					var strPosition = "";
					if (bSetData)
					{
						var arroption37 = g_strOption37.split("^");
						if (arroption37[0] == "GRD")
							strPosition = getApproverUserLevel(objApprover);
						if (arroption37[0] == "SGRD")
							strPosition = getApproverUserLevelAbbr(objApprover);
						if (arroption37[0] == "POS")
							strPosition = getApproverUserPosition(objApprover);
						if (arroption37[0] == "SPOS")
							strPosition = getApproverUserPositionAbbr(objApprover);
						if (arroption37[0] == "TLT")
							strPosition = getApproverUserPosition(objApprover);

						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x02) > 0)
							strPosition = "⊙" + strPosition;
						if ((parseInt(getApproverAdditionalRole(objApprover)) & 0x01) > 0)
							strPosition = "★" + strPosition;
					}

					// 의견붙임
					var strApproverOpinion = getApproverOpinion(objApprover);
					if (strApproverOpinion != "")
						strPosition = strPosition + OPINION_ATTACHED;

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strPosition);
				}

				var strFieldData = "";

				if (strRole == "6")
					strFieldData = FIELD_ARBITRARY;
				else if (strRole == "7")
					strFieldData = FIELD_PROXY;
				else if (strRole == "9")
					strFieldData = FIELD_REAR;
/*
				var strOpinion = getApproverOpinion(objApprover)
				if (strOpinion != "")
					strFieldData += strOpinion;
*/
				Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strFieldData);

				var strApproverName = "#" + strApprovalField;
				var strUserName = "";
				if (bSetData)
					strUserName = getApproverUserName(objApprover);

				Enforce_Hun2KLa.SetRecordTextByName(strApproverName, -1, strUserName);
			}
		}
		else if (nSetType == 1 || nSetType == 2)
		{
			if (nSetType == 1)
			{
				if (bSetData)
				{
					if (getApproverIsSigned(objApprover) == "Y" && strApprovalField != "")
					{
						var strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Enforce_Hun2KLa.SetRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
						}
						else
						{
							var strApproverName = getApproverUserName(objApprover);
							Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strApproverName);
						}
					}
				}
				else
				{
					Enforce_Hun2KLa.ClearRecordImage(strApprovalField);
					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Enforce_Hun2KLa.ClearRecordImage(strApprovalField);
					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
					break;
				}
			}

			if (nSetType == 1)
			{
				if (bSetData)
				{
					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignDate = getApproverSignDate(objApprover);
						if (strSignDate != "")
						{
							var strMMDD = "";
							if (bSetData)
							{
								var nFind = strSignDate.indexOf(" ");
								if (nFind != -1)
									strSignDate = strSignDate.substring(0, nFind);

								var arrDate = strSignDate.split("-");
								var strMMDD = "";
								if (g_strOption36.indexOf("MM.DD") >= 0)
									strMMDD = arrDate[1] + "." + arrDate[2];
								else if (g_strOption36.indexOf("MM/DD") >= 0)
									strMMDD = arrDate[1] + "/" + arrDate[2];
								else if (g_strOption36.indexOf("MM-DD") >= 0)
									strMMDD = arrDate[1] + "-" + arrDate[2];
								else
									strMMDD = arrDate[1] + "." + arrDate[2];
							}

							var strDateField = "@" + strApprovalField;
							Enforce_Hun2KLa.SetRecordTextByName(strDateField, -1, strMMDD);
						}
					}
				}
				else
				{
					var strDateField = "@" + strApprovalField;
					Enforce_Hun2KLa.SetRecordTextByName(strDateField, -1, "");
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					var strMMDD = "";
					strApprovalField = "@" + strApprovalField;

					Enforce_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);
					break;
				}
			}
		}

		objApprover = objNextApprover;
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
	Enforce_Hun2KLa.CloseDocument();

	if (bModify == true)
	{
		Enforce_Hun2KLa.SetEdit(1);
		Enforce_Hun2KLa.SetToolbar(1);
		Enforce_Hun2KLa.SetCopy(1);
		Enforce_Hun2KLa.SetPrint(1);
	}
	else
	{
		Enforce_Hun2KLa.SetEdit(0);
		Enforce_Hun2KLa.SetToolbar(0);
		Enforce_Hun2KLa.SetCopy(1);
		Enforce_Hun2KLa.SetPrint(1);
	}

	var strFileName = getAttachFileName(getEnforceFileObj(g_nEnforceProposal));

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		Enforce_Hun2KLa.OpenDocument(strLocalPath, 0);
	}
	else
		Enforce_Hun2KLa.NewDocument();

	if (bModify == true)
		Enforce_Hun2KLa.SetZoomFactor(4);
	else
		Enforce_Hun2KLa.SetZoomFactor(2);

//	Enforce_Hun2KLa.SetFormPlayMode(true);

	return true;
}

function saveEnforceDocument(bIncludeSign)
{
	var strTitle = getTitle(1);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	Enforce_Hun2KLa.SaveDocumentWithDialogEx(strTitle, "Hunmin");
}

function saveEnforceDocumentAs(strFilePath, bIncludeSign)
{
}

function setEnforceXmlDataToForm()
{
	var bIsForm = Enforce_Hun2KLa.IsFormData();
	if (bIsForm == false)
		return;

	var nIndex = g_nEnforceProposal;
	var strFieldName = FIELD_TITLE;

	if (Enforce_Hun2KLa.IsExistRecord(strFieldName) == true)
	{
		strSubject = getTitle(nIndex);
		Enforce_Hun2KLa.SetRecordTextByName(strFieldName, -1, strSubject);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		Enforce_Hun2KLa.SetRecordTextByName(FIELD_DOC_NUMBER, -1, strDocNumber);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_CONSERVE_TERM) == true)
	{
		strConserveTerm = getDraftConserve();
		Enforce_Hun2KLa.SetRecordTextByName(FIELD_CONSERVE_TERM, -1, strConserveTerm);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_PUBLIC_BOUND) == true)
	{
//		var strPublicBound = getPublicLevel();
		var strPublicBound = getPublication(getPublicLevel());
		Enforce_Hun2KLa.SetRecordTextByName(FIELD_PUBLIC_BOUND, -1, strPublicBound);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_ENFORCE_NUMBER) == true)
	{
		var strDraftDeptName = getDraftProcDeptName();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDraftDeptName += "-" + strSerialNumber;

		Enforce_Hun2KLa.SetRecordTextByName(FIELD_ENFORCE_NUMBER, -1, strDraftDeptName);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_ENFORCE_DATE) == true)
	{
		var strEnforceDate = getEnforceDate();
		if (strEnforceDate == "")
		{
			var nBodyCount = getBodyCount();
			var nCaseNum = 1;
			var bInbound = true;

			while (nCaseNum <= nBodyCount)
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
		}

		if (strEnforceDate != "")
		{
			strEnforceDate = strEnforceDate.substring(0, strEnforceDate.indexOf(" "));
			if (strEnforceDate != "")
			{
				var arrDate = strEnforceDate.split("-");
				if (g_strOption36.indexOf("YYYY.MM.DD") >= 0)
					strEnforceDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
				else if (g_strOption36.indexOf("YYYY/MM/DD") >= 0)
					strEnforceDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];
				else if (g_strOption36.indexOf("YYYY-MM-DD") >= 0)
					strEnforceDate = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];
				else
					strEnforceDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
			}
		}
		Enforce_Hun2KLa.SetRecordTextByName(FIELD_ENFORCE_DATE, -1, strEnforceDate);
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "")
		{
//			var strEnforceDeptName = getEnforceProcDeptName();
//			if (strReceiveNumber != "" && parseInt(strReceiveNumber) > 0)
//				strEnforceDeptName += "-" + strReceiveNumber;

//			Enforce_Hun2KLa.SetRecordTextByName(FIELD_RECEIVE_NUMBER, -1, strEnforceDeptName);
			Enforce_Hun2KLa.SetRecordTextByName(FIELD_RECEIVE_NUMBER, -1, strReceiveNumber);
		}
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_RECEIVE_DATE) == true)
	{
		var strReceiveDate = getReceiveDate();
		if (strReceiveDate != "")
		{
			strReceiveDate = strReceiveDate.substring(0, strReceiveDate.indexOf(" "));
			if (strReceiveDate != "")
			{
				var arrDate = strReceiveDate.split("-");
				if (g_strOption36.indexOf("YYYY.MM.DD") >= 0)
					strReceiveDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
				else if (g_strOption36.indexOf("YYYY/MM/DD") >= 0)
					strReceiveDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];
				else if (g_strOption36.indexOf("YYYY-MM-DD") >= 0)
					strReceiveDate = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];
				else
					strReceiveDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
			}
		}

		Enforce_Hun2KLa.SetRecordTextByName(FIELD_RECEIVE_DATE, -1, strReceiveDate);
	}
/*
	if (Enforce_Hun2KLa.IsExistRecord(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = nodeDocFlowInfo.selectSingleNode("receive_deptname").text;
		Enforce_Hun2KLa.SetRecordTextByName(FIELD_PROCESS_DEPT, -1, strProcessDept);
	}
*/
	if (Enforce_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Enforce_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION, -1, strInstruction);
			}
		}
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION_ITEM) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Enforce_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION_ITEM, -1, strInstruction);
			}
		}
	}

	if (Enforce_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION_ITEM) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Enforce_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION_ITEM, -1, strInstruction);
			}
		}
	}
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
	Enforce_Hun2KLa.PrintDocWithDialog();
}

// icaha, document switching
onEnforceAccessLoadingCompleted();
