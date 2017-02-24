
var g_bIsModified = false;

function onLoadDocument()
{
	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function loadDocument()
{/*
<<<<<<< .mine
	alert('loadDocument');
	//downloadDefaultFile();
=======
	//downloadDefaultFile();
>>>>>>> .r791*/

	// 20030428, 삼립
	if (g_strEditType == "0" && getLegacySystem() == null || g_strEditType == "10")
	{
		Document_Hun2KLa.SetEdit(1);
		Document_Hun2KLa.SetToolbar(1);
		Document_Hun2KLa.SetCopy(1);
		Document_Hun2KLa.SetPrint(1);

		g_bIsModified = true;
	}
	else
	{
		Document_Hun2KLa.SetEdit(0);
		Document_Hun2KLa.SetToolbar(0);
		Document_Hun2KLa.SetCopy(1);
		Document_Hun2KLa.SetPrint(1);

		g_bIsModified = (g_strEditType == "0" && getLegacySystem() != null) ? true : false;
	}

	if (g_strDataUrl != "")
	{
		var strFileName = getAttachFileName(getBodyFileObj());
		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			Document_Hun2KLa.OpenDocument(strLocalPath, 0);

			var bIsForm = Document_Hun2KLa.IsFormData();
			if (bIsForm == true)
				setIsForm("Y");
			else
				setIsForm("N");

			Document_Hun2KLa.PasteEnforceDoc(true);
			Document_Hun2KLa.ClearEnforceClipBoard();
		}
		else
		{
			Document_Hun2KLa.NewDocument();
			setIsForm("N");
		}

		setXmlDataToForm();
		restoreApproveFlow();
	}
	else
	{
		if (g_strFormUrl != "")
		{
			var bRet = Document_Hun2KLa.OpenDocument(g_strDownloadPath + g_strFormUrl, 0);

			var bIsForm = Document_Hun2KLa.IsFormData();
			if (bIsForm == true)
				setIsForm("Y");
			else
				setIsForm("N");

			Document_Hun2KLa.PasteEnforceDoc(true);
			Document_Hun2KLa.ClearEnforceClipBoard();

			if (g_strDataUrl == "")
				setFormDataToXml();
		}
		else
		{
			Document_Hun2KLa.NewDocument();
			setIsForm("N");
		}

//		if (Document_Hun2KLa.IsExistRecord(FIELD_SENDER_AS + "#1") == true)
//			g_strSenderAs = Document_Hun2KLa.GetRecordTextByNameEx(FIELD_SENDER_AS + "#1", true);

		setDrafterField();
		setXmlDataToForm();
		setSenderAsToForm(g_strSenderAs);
		setOrganImage();
	}

	if (g_strEditType == "0" && Document_Hun2KLa.IsExistRecord(FIELD_ORGAN) == true)
		Document_Hun2KLa.SetRecordTextByName(FIELD_ORGAN, -1, g_strUserOrgDisplayName);

//	if (getIsForm() == "Y")
//		Document_Hun2KLa.SetFormPlayMode(true);

	Document_Hun2KLa.SetZoomFactor(4);
	Document_Hun2KLa.focus();
	onDocumentOpen();
}

function closeDocument()
{
	Document_Hun2KLa.CloseDocument();
}

function reloadDocument()
{
	var strLocalPath = Document_Hun2KLa.GetDocPath();

	Document_Hun2KLa.SetEdit(0);
	Document_Hun2KLa.OpenDocument(strLocalPath, 0);

	setXmlDataToForm();
	restoreApproveFlow();
}

function changeFormUrl(strFormUrl)
{
	g_strFormUrl = strFormUrl;

	Document_Hun2KLa.CopyEnforceDocEx(1, true);
	Document_Hun2KLa.CloseDocument();

	Document_Hun2KLa.OpenDocument(g_strDownloadPath + strFormUrl, 0);
	var bIsForm = Document_Hun2KLa.IsFormData();
	if (bIsForm == true)
		setIsForm("Y");
	else
		setIsForm("N");

	Document_Hun2KLa.PasteEnforceDoc(true);
	Document_Hun2KLa.ClearEnforceClipBoard();

	setXmlDataToForm();

	if (getCurrentLineType() == "5")
	{
		var strLineName = getCurrentLineName();
		var objInspBodyFile = findExtendAttachInfo("InspBody", strLineName, "0");
		if (objInspBodyFile == null)
			setBodyFileInfo();
	}

	saveBodyFile();
	setModified(true);
}

function getFileExtension()
{
	return "gul";
}

function saveBodyFile()
{
	clearApproveFlow();

	var bRet = false;

	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();

	var strFileName = getAttachFileName(objBodyFile);

	if (Document_Hun2KLa.SaveAsDocument(g_strDownloadPath + strFileName, 0) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objBodyFile, strFileSize);
	}

	restoreApproveFlow();

	return bRet;
}

function uploadBodyFile()
{
	var strFileName = getAttachFileName(getBodyFileObj());
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
}

function clearApproveFlow()
{
	if (getIsForm() == "N")
		Document_Hun2KLa.RemoveApproveFieldAll();
	else
	{
		clearApprovalFlow();
		clearApprovalSign();
		clearExaminationSign();
		clearApprovalSeal();
		clearOrganImage();
	}
}

function restoreApproveFlow()
{
	setApprovalFlow();
	setApprovalSign();
	setExaminationSign();
	setApprovalSeal();
	setOrganImage();
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
//		if (strFormUsage == "0")
			objApprovalLine = getActiveApprovalLine();
//		else
//			objApprovalLine = getApprovalLineByLineName("0");

		if (objApprovalLine == null)
			return;

//		if (strFormUsage == "0")
			setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
//		else
//			setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}

function setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
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
//		else if (strPrevType != strType)
//		{
//			nAssist = 0;
//			nInspect = 0;
//		}

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
				if (Document_Hun2KLa.IsExistRecord(strApprovalField) == false)
				{
					if (getIsForm() == "N")
						Document_Hun2KLa.AddApproveField(strApprovalField, nSerialOrder);
				}

				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
					var strDeptName = "";
					if (bSetData)
						strDeptName = getApproverDeptName(objApprover);

					Document_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strDeptName);
				}
				else
				{
					var strPosition = "";
					if (bSetData)
					{
						var arrOption37 = g_strOption37.split("^");
						strPosition = getUserPosition(objApprover, arrOption37[0]);
					}

					Document_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strPosition);
				}

				var strFieldData = "";

				if (strRole == "6")
					strFieldData = FIELD_ARBITRARY;
				else if (strRole == "7")
					strFieldData = FIELD_PROXY;
				else if (strRole == "9")
					strFieldData = FIELD_REAR;

				Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strFieldData);

				var strApproverName = "#" + strApprovalField;
				var strUserName = "";
				if (bSetData)
					strUserName = getApproverUserName(objApprover);

				Document_Hun2KLa.SetRecordTextByName(strApproverName, -1, strUserName);

				var strApproverDept = "$" + strApprovalField;
				var strDeptName = "";
				if (bSetData)
					strDeptName = getApproverDeptName(objApprover);

				Document_Hun2KLa.SetRecordTextByName(strApproverDept, -1, strDeptName);

				if (strRole == "0" || strRole == "1")
				{
					if (Document_Hun2KLa.IsExistRecord(FIELD_DRAFTER_NAME))
						Document_Hun2KLa.SetRecordTextByName(FIELD_DRAFTER_NAME, -1, strUserName);
				}
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
						Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, STATUS_OPPOSE);
					else if (strAction == "9")
						Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, STATUS_REJECT);

					if (getApproverIsSigned(objApprover) == "Y")
					{
						var strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Document_Hun2KLa.SetRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
						}
					}
				}
				else
				{
					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
					Document_Hun2KLa.ClearRecordImage(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Document_Hun2KLa.ClearRecordImage(strApprovalField);
					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
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
							Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);

							if (strRole == "0" || strRole == "1")
							{
								var strDateField = FIELD_DRAFTER + "일자";
								if (strType == "1" || strType == "2")
									strDateField += strType + "-1";
								else
									strDateField += "1";

								Document_Hun2KLa.SetRecordTextByName(strDateField, -1, strMMDD);
							}
						}
					}
				}
				else
				{
					var strDateField = "@" + strApprovalField;
					Document_Hun2KLa.SetRecordTextByName(strDateField, -1, "");

					if (strRole == "0" || strRole == "1")
					{
						strDateField = FIELD_DRAFTER + "일자";
						if (strType == "1" || strType == "2")
							strDateField += strType + "-1";
						else
							strDateField += "1";

						Document_Hun2KLa.SetRecordTextByName(strDateField, -1, "");
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

					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);
					break;
				}
			}
		}

		nSerialOrder++;
		objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
	}
}

function setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
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
		var strType = getApproverType(objApprover);

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
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
				if (Document_Hun2KLa.IsExistRecord(strApprovalField) == false)
				{
//					if (getIsForm() == "N")
//						Document_Hun2KLa.AddApproveField(strApprovalField, nSerialOrder);
				}

				var strApprovalType = "*" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
					var strDeptName = "";
					if (bSetData)
						strDeptName = getApproverDeptName(objApprover);

					Document_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strDeptName);
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
					var strApproverOpinion = getApproverOpinion(objApprover);
					if (strApproverOpinion != "")
						strPosition = strPosition + OPINION_ATTACHED;

					Document_Hun2KLa.SetRecordTextByName(strApprovalType, -1, strPosition);
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
				Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strFieldData);

				var strApproverName = "#" + strApprovalField;
				var strUserName = "";
				if (bSetData)
					strUserName = getApproverUserName(objApprover);

				Document_Hun2KLa.SetRecordTextByName(strApproverName, -1, strUserName);

				var strApproverDept = "$" + strApprovalField;
				var strDeptName = "";
				if (bSetData)
					strDeptName = getApproverDeptName(objApprover);

				Document_Hun2KLa.SetRecordTextByName(strApproverDept, -1, strDeptName);

				if (strRole == "0" || strRole == "1")
				{
					if (Document_Hun2KLa.IsExistRecord(FIELD_DRAFTER_NAME))
						Document_Hun2KLa.SetRecordTextByName(FIELD_DRAFTER_NAME, -1, strUserName);
				}
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
								Document_Hun2KLa.SetRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
						}
						else
						{
							var strApproverName = getApproverUserName(objApprover);
							if (getApproverRepName(objApprover) != "")
								strApproverName = getApproverRepName(objApprover);

							Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strApproverName);
						}
					}
				}
				else
				{
					Document_Hun2KLa.ClearRecordImage(strApprovalField);
					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Document_Hun2KLa.ClearRecordImage(strApprovalField);
					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, "");
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

							var strDateField = "@" + strApprovalField;
							Document_Hun2KLa.SetRecordTextByName(strDateField, -1, strDate);

							strDateField = FIELD_REGIST_DATE;
							Document_Hun2KLa.SetRecordTextByName(strDateField, -1, strDate);

							strDateField = FIELD_COMPLETE_DATE;
							Document_Hun2KLa.SetRecordTextByName(strDateField, -1, strDate);
						}
					}
				}
				else
				{
					var strDateField = "@" + strApprovalField;
					Document_Hun2KLa.SetRecordTextByName(strDateField, -1, "");
				}

				if (strRole == "0" || strRole == "1")
				{
					var strSignDate = getApproverSignDate(objApprover);
					if (strSignDate != "")
					{
						var strDate = "";
						if (bSetData)
							strDate = getDateDisplay(strSignDate, g_strOption36);

						var strDateField = FIELD_DRAFTER + "일자";
						if (strType == "1" || strType == "2")
							strDateField += strType + "-1";
						else
							strDateField += "1";

						Document_Hun2KLa.SetRecordTextByName(strDateField, -1, strMMDD);
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

					Document_Hun2KLa.SetRecordTextByName(strApprovalField, -1, strMMDD);
					break;
				}
			}
		}

		objApprover = objNextApprover;
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
		var strSignFileName = getDelivererSignFilename(objDeliverer);
		if (strSignFileName != "")
		{
			var strSignFilePath = g_strDownloadPath + strSignFileName;

			if (getLocalFileSize(strSignFilePath) > 0)
				Document_Hun2KLa.SetRecordImageFromFile(FIELD_INSPECTOR, strSignFilePath);
		}

		var strSignDate = getDelivererSignDate(objDeliverer);
		if (strSignDate != "")
		{
			strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));
			if (strSignDate != "")
			{
				var arrDate = strSignDate.split("-");
				if (g_strOption36.indexOf("YYYY.MM.DD") >= 0)
					strSignDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
				else if (g_strOption36.indexOf("YYYY/MM/DD") >= 0)
					strSignDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];
				else if (g_strOption36.indexOf("YYYY-MM-DD") >= 0)
					strSignDate = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];
				else
					strSignDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
			}

			if (Document_Hun2KLa.IsExistRecord(FIELD_INSPECT_DATE))
				Document_Hun2KLa.SetRecordTextByName(FIELD_INSPECT_DATE, -1, strSignDate);
		}
	}
}

function setApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();

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

				if (Document_Hun2KLa.IsExistRecord(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
					{
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
							Document_Hun2KLa.RearrangeEndStampRecord(-1);
							Document_Hun2KLa.RearrangeStampRecord(-1, FIELD_SENDER_AS + "#1", strFieldName);
						}

						Document_Hun2KLa.SetRecordImageFromFile(strFieldName, strFilePath);
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

				if (Document_Hun2KLa.IsExistRecord(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
						Document_Hun2KLa.SetRecordImageFromFile(strFieldName, strFilePath);
				}
			}
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
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
	if (Document_Hun2KLa.IsExistRecord(FIELD_INSPECTOR))
		Document_Hun2KLa.ClearRecordImage(FIELD_INSPECTOR);

	if (Document_Hun2KLa.IsExistRecord(FIELD_INSPECT_DATE))
		Document_Hun2KLa.SetRecordTextByName(FIELD_INSPECT_DATE, -1, "");
}

function clearApprovalSeal()
{
	if (Document_Hun2KLa.IsExistRecord(FIELD_DEPT_STAMP))
		Document_Hun2KLa.ClearRecordImage(FIELD_DEPT_STAMP);

	if (Document_Hun2KLa.IsExistRecord(FIELD_DEPT_OMIT_STAMP))
		Document_Hun2KLa.SetRecordTextByName(FIELD_DEPT_OMIT_STAMP, -1, "");

	if (Document_Hun2KLa.IsExistRecord(FIELD_COMPANY_STAMP))
		Document_Hun2KLa.ClearRecordImage(FIELD_COMPANY_STAMP);

	if (Document_Hun2KLa.IsExistRecord(FIELD_COMPANY_OMIT_STAMP))
		Document_Hun2KLa.SetRecordTextByName(FIELD_COMPANY_OMIT_STAMP, -1, "");
}

function clearOrganImage()
{
	if (Document_Hun2KLa.IsExistRecord(FIELD_LOGO_IMAGE))
		Document_Hun2KLa.ClearRecordImage(FIELD_LOGO_IMAGE);

	if (Document_Hun2KLa.IsExistRecord(FIELD_SYMBOL_IMAGE))
		Document_Hun2KLa.SetRecordTextByName(FIELD_SYMBOL_IMAGE, -1, "");
}

function clearUserSign(strUserID)
{
	setApprovalField(2, false, strUserID);
}

function setDocumentRecipientsToForm()
{
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

			if (Document_Hun2KLa.IsExistRecord(strDeptField) == true)
			{
				var strValue = "";
				if (nRecipientCount == 0)
					strValue = strDisplayAs;
				else if (nRecipientCount == 1)
					strValue = strDeptName;
				else
					strValue = RECIPIENT_REFER;

				Document_Hun2KLa.SetRecordTextByName(strDeptField, -1, strValue);
			}

			if (Document_Hun2KLa.IsExistRecord(strRefField) == true)
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

				Document_Hun2KLa.SetRecordTextByName(strRefField, -1, strValue);
			}

			if (Document_Hun2KLa.IsExistRecord(strDeptRefField) == true)
			{
				var strValue = "";
				if (nRecipientCount < 2)
					strValue = "";
				else
					strValue = strDeptRefName;
//					strValue = LABEL_RECIPIENT + ": " + strDeptRefName;

				Document_Hun2KLa.SetRecordTextByName(strDeptRefField, -1, strValue);
			}
		}
		else		// no RecipGroup
		{
			if (Document_Hun2KLa.IsExistRecord(strDeptField) == true)
			{
				var strDocCategory = getDocCategory(nCaseNumber);
				var strValue = "";
				if (strDocCategory == "I")
					strValue = INNER_APPROVAL;

				Document_Hun2KLa.SetRecordTextByName(strDeptField, -1, strValue);
			}

			if (Document_Hun2KLa.IsExistRecord(strRefField) == true)
				Document_Hun2KLa.SetRecordTextByName(strRefField, -1, "");

			if (Document_Hun2KLa.IsExistRecord(strDeptRefField) == true)
				Document_Hun2KLa.SetRecordTextByName(strDeptRefField, -1, "");
		}
	}
}

function addDocument()
{
	var bIsForm = Document_Hun2KLa.IsFormData();
	if (bIsForm == false)
		return false;

	if (Document_Hun2KLa.MakeNextDraftTitle() != 0)
	{
		increaseBodyCount();

		var nBodyCount = getBodyCount();
		if (getDocCategory(nBodyCount) == "I")
			Document_Hun2KLa.SetRecordTextByName(FIELD_RECIPIENT + nBodyCount, -1, INNER_APPROVAL);
		else
			Document_Hun2KLa.SetRecordTextByName(FIELD_SENDER_AS + "#" + nBodyCount, -1, g_strSenderAs);

		if (nBodyCount == 2)
			Document_Hun2KLa.SetRecordTextByName(FIELD_BATCH, -1, "(" + FIRST_DRAFT + ")");

		return true;
	}

	return false;
}

function removeDocument(nIndex)
{
	var bIsForm = Document_Hun2KLa.IsFormData();
	if (bIsForm == false)
		return false;

	var bRet = Document_Hun2KLa.DeleteNthKian(nIndex - 1);
	decreaseBodyCount(nIndex);

	Document_Hun2KLa.focus();

	return bRet;
}

function editDocument(bModify)
{
	Document_Hun2KLa.CloseDocument();

	// 20030425, 삼립
	if (bModify == true && getLegacySystem() == null)
	{
		Document_Hun2KLa.SetEdit(1);
		Document_Hun2KLa.SetToolbar(1);
		Document_Hun2KLa.SetCopy(1);
		Document_Hun2KLa.SetPrint(1);
	}
	else
	{
		Document_Hun2KLa.SetEdit(0);
		Document_Hun2KLa.SetToolbar(0);
		Document_Hun2KLa.SetCopy(1);
		Document_Hun2KLa.SetPrint(1);
	}

	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();

	var strFileName = getAttachFileName(objBodyFile);

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		Document_Hun2KLa.OpenDocument(strLocalPath, 0);
	}
	else
		Document_Hun2KLa.NewDocument();

	if (bModify == false)
		restoreApproveFlow();

//	if (getIsForm() == "Y")
//		Document_Hun2KLa.SetFormPlayMode(true);

	return true;
}

function saveApproveDocument(bIncludeSign)
{
	var strTitle = getTitle(1);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	Document_Hun2KLa.SaveDocumentWithDialogEx(strTitle, "Hunmin");
}

function saveApproveDocumentAs(strFilePath, bIncludeSign)
{
	if (!bIncludeSign)
		clearApproveFlow();

	Document_Hun2KLa.SaveAsDocument(strFilePath, 0);

	if (!bIncludeSign)
		restoreApproveFlow();
}

function setFormDataToXml()
{
	var bIsForm = Document_Hun2KLa.IsFormData();
	if (bIsForm == false)
		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (Document_Hun2KLa.IsExistRecord(strFieldName) == true)
		{
			var strSubject = Document_Hun2KLa.GetRecordTextByNameEx(strFieldName, true);
			if (strSubject.length > 128)
				strSubject = strSubject.substring(0, 128)

			setTitle(strSubject, nIndex);
		}

		if (Document_Hun2KLa.IsExistRecord(FIELD_SENDER_AS + "#" + nIndex) == true)
		{
			var strSenderAs = Document_Hun2KLa.GetRecordTextByNameEx(FIELD_SENDER_AS + "#" + nIndex, true);
			setSenderAs(strSenderAs, nIndex);
		}

		nIndex++;
	}
/*
	if (Document_Hun2KLa.IsExistRecord(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = Document_Hun2KLa.GetRecordTextByNameEx(FIELD_DOC_NUMBER, true);

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
*/
/*
	if (Document_Hun2KLa.IsExistRecord(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = Document_Hun2KLa.GetRecordTextByNameEx(FIELD_CONSERVE_TERM, true);

		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0, 4);

		setDraftConserve(strConserveTerm);
	}
	else
	{
		var strConserveTerm = getDraftConserve();

		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0, 4);

		setDraftConserve(strConserveTerm);
	}
*/
/*
	if (Document_Hun2KLa.IsExistRecord(FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = Document_Hun2KLa.GetRecordTextByNameEx(FIELD_PUBLIC_BOUND, true);

		if (strPublicBound.length > 4)
			strPublicBound = strPublicBound.substring(0, 4);

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
	var bIsForm = Document_Hun2KLa.IsFormData();
	if (bIsForm == false)
		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (Document_Hun2KLa.IsExistRecord(strFieldName) == true)
		{
			strSubject = getTitle(nIndex);
			Document_Hun2KLa.SetRecordTextByName(strFieldName, -1, strSubject);
		}

		nIndex++;
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		Document_Hun2KLa.SetRecordTextByName(FIELD_DOC_NUMBER, -1, strDocNumber);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_CONSERVE_TERM) == true)
	{
		strConserveTerm = getDraftConserve();
		Document_Hun2KLa.SetRecordTextByName(FIELD_CONSERVE_TERM, -1, strConserveTerm);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_PUBLIC_BOUND) == true)
	{
//		var strPublicBound = getPublicLevel();
		var strPublicBound = getPublication(getPublicLevel());
		Document_Hun2KLa.SetRecordTextByName(FIELD_PUBLIC_BOUND, -1, strPublicBound);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_ENFORCE_NUMBER) == true)
	{
		var strDraftDeptName = getDraftProcDeptName();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDraftDeptName += "-" + strSerialNumber;

		Document_Hun2KLa.SetRecordTextByName(FIELD_ENFORCE_NUMBER, -1, strDraftDeptName);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_ENFORCE_DATE) == true)
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
		Document_Hun2KLa.SetRecordTextByName(FIELD_ENFORCE_DATE, -1, strEnforceDate);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "")
		{
			var strEnforceDeptName = getEnforceProcDeptName();
			if (strReceiveNumber != "" && parseInt(strReceiveNumber) > 0)
				strEnforceDeptName += "-" + strReceiveNumber;

			Document_Hun2KLa.SetRecordTextByName(FIELD_RECEIVE_NUMBER, -1, strEnforceDeptName);
		}
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_RECEIVE_DATE) == true)
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

		Document_Hun2KLa.SetRecordTextByName(FIELD_RECEIVE_DATE, -1, strReceiveDate);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getEnforceProcDeptName();
		Document_Hun2KLa.SetRecordTextByName(FIELD_PROCESS_DEPT, -1, strProcessDept);
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Document_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION, -1, strInstruction);
			}
		}
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION_ITEM) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Document_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION_ITEM, -1, strInstruction);
			}
		}
	}

	if (Document_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION_ITEM) == true)
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				var strInstruction = getApproverOpinion(objApprover);
				Document_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION_ITEM, -1, strInstruction);
			}
		}
	}
}

function getFormInfo()
{
	var strFormInfo = "";

	if (getIsForm() == "N")
		return strFormInfo;

	if (Document_Hun2KLa.IsExistRecord(FIELD_DRAFTER + "1") == true ||
		Document_Hun2KLa.IsExistRecord("$" + FIELD_DRAFTER + "1") == true ||
		Document_Hun2KLa.IsExistRecord("#" + FIELD_DRAFTER + "1") == true)
	{
		var nSubmit = 0;
		while(1)
		{
			var strField = FIELD_DRAFTER + (nSubmit + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nSubmit++;
		}

		var nApprove = 0;
		while(1)
		{
			var strField = FIELD_CONSIDER + (nApprove + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nApprove++;
		}

		var nAssist = 0;
		while(1)
		{
			var strField = FIELD_COOPERATE + (nAssist + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nAssist++;
		}

		var nComplete = 0;
		while(1)
		{
			var strField = FIELD_LAST + (nComplete + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nComplete++;
		}

		strFormInfo = nSubmit + "-" + nApprove + "-" + nAssist + "-" + nComplete;
	}
	else if (Document_Hun2KLa.IsExistRecord(FIELD_DRAFTER + "1-1") == true ||
			Document_Hun2KLa.IsExistRecord("$" + FIELD_DRAFTER + "1-1") == true ||
			Document_Hun2KLa.IsExistRecord("#" + FIELD_DRAFTER + "1-1") == true)
	{
		var nType = 1;
		while(1)
		{
			var nSubmit = 0;
			while(1)
			{
				var strField = FIELD_DRAFTER + nType + "-" + (nSubmit + 1);
				if (Document_Hun2KLa.IsExistRecord(strField) == false &&
					Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
					Document_Hun2KLa.IsExistRecord("#" + strField) == false)
				{
					break;
				}

				nSubmit++;
			}

			var nApprove = 0;
			while(1)
			{
				var strField = FIELD_CONSIDER + nType + "-" + (nApprove + 1);
				if (Document_Hun2KLa.IsExistRecord(strField) == false &&
					Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
					Document_Hun2KLa.IsExistRecord("#" + strField) == false)
				{
					break;
				}

				nApprove++;
			}

			var nAssist = 0;
			while(1)
			{
				var strField = FIELD_COOPERATE + nType + "-" + (nAssist + 1);
				if (Document_Hun2KLa.IsExistRecord(strField) == false &&
					Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
					Document_Hun2KLa.IsExistRecord("#" + strField) == false)
				{
					break;
				}

				nAssist++;
			}

			var nComplete = 0;
			while(1)
			{
				var strField = FIELD_LAST + nType + "-" + (nComplete + 1);
				if (Document_Hun2KLa.IsExistRecord(strField) == false &&
					Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
					Document_Hun2KLa.IsExistRecord("#" + strField) == false)
				{
					break;
				}

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
	else if (Document_Hun2KLa.IsExistRecord(FIELD_CHARGER) == true ||
			Document_Hun2KLa.IsExistRecord("$" + FIELD_CHARGER) == true ||
			Document_Hun2KLa.IsExistRecord("#" + FIELD_CHARGER) == true)
	{
		var nCharger = 0;
		{
			var strField = FIELD_CHARGER;
			if (Document_Hun2KLa.IsExistRecord(strField) == true)
				nCharger++;
		}

		var nPrior = 0;
		while(1)
		{
			var strField = FIELD_PRIOR + (nPrior + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nPrior++;
		}

		var nCircular = 0;
		while(1)
		{
			var strField = FIELD_PUBLIC + (nCircular + 1);
			if (Document_Hun2KLa.IsExistRecord(strField) == false &&
				Document_Hun2KLa.IsExistRecord("$" + strField) == false &&
				Document_Hun2KLa.IsExistRecord("#" + strField) == false)
			{
				break;
			}

			nCircular++;
		}

		strFormInfo = nCharger + "-" + nPrior + "-" + nCircular;
	}

	return strFormInfo;
}

function printDocument()
{
	Document_Hun2KLa.PrintDocWithDialog();

	if (confirm(CONFIRM_PRINT_DOCINFO) == true)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePrintDocInfo.jsp";
		window.open(strUrl,"Approve_ViewDocDetail","scrollbars=yes, toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
}

function openFile()
{
	if (Document_Hun2KLa.OpenDocumentWithDialog() == true)
	{
		var bIsForm = Document_Hun2KLa.IsFormData();
		if (bIsForm == true)
		{
			setIsForm("Y");

			var nIndex = 2;
			var strField = "&FIELD_TITLE;" + nIndex;
			while (Document_Hun2KLa.IsExistRecord(strField) == true)
			{
//				parent.increaseBodyCount();
				nIndex++;
				strField = "&FIELD_TITLE;" + nIndex;
			}
		}
		else
			setIsForm("N");

		setFormDataToXml();
		restoreApproveFlow();
		setDrafterField();
	}
}

function isModified()
{
	return g_bIsModified;
}

function setModified(bModified)
{
	g_bIsModified = bModified;
}

function isFormData()
{
//	return g_bIsFormData;
}

function copyProposal(nProposal)
{
	Document_Hun2KLa.CopyEnforceDocEx(1, true);
}

function setSenderAsToForm(strSenderAs)
{
	if (getFlowStatus() != "0")
		return;

	if (typeof(strSenderAs) == "undefined")
		strSenderAs = g_strSenderAs;
	g_strSenderAs = strSenderAs;

	var nBodyCount = getBodyCount();
	for (var n = 1; n <= nBodyCount; n++)
	{
		var strSenderAsField = FIELD_SENDER_AS + "#" + n;
		if (Document_Hun2KLa.IsExistRecord(strSenderAsField) == true)
		{
			if (getDocCategory(n) == "I")
			{
				Document_Hun2KLa.SetRecordTextByName(strSenderAsField, -1, "");
				setSenderAs("", n);
				setSenderAsCode("", n);
			}
			else
			{
				Document_Hun2KLa.SetRecordTextByName(strSenderAsField, -1, strSenderAs);
				setSenderAs(strSenderAs, n);
			}
		}
	}
}

function insertOpinionToBody(strOpinion)
{
	var strBodyType = getBodyType();
	var strFormUsage = getFormUsage();
	var strFlowStatus = getFlowStatus();

	if (getBodyType() == "txt" || getBodyType() == "html" || getFormUsage() == "0" || strFlowStatus != "0" || parseInt(g_strEditType) >= 18)
	{
	}
	else
	{
		if (g_strLineName == "0")
		{
		}
	}
}

function setLegacyXMLToForm(objRoot)
{
	// 삼립산업: map record name to XPath from mapping xml file
	var strFieldMapUrl = g_strBaseUrl + "legacy/fieldmap/FieldMap.xml";
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	if (objXMLDom.load(strFieldMapUrl) == false)
	{
		alert("매핑 파일 '" + strFieldMapUrl + "'이 존재하지 않거나 로드하는데 실패하였습니다.");
		return;
	}
	var strSystemID = getLegacySystemID(getLegacySystem());
	var strBusinessID = getBusinessID(getBusinessInfo(getLegacySystem()));
	var objSystem = objXMLDom.selectSingleNode("//System[@SystemID='" + strSystemID + "']");
	if (objSystem == null)
	{
		alert("시스템 '" + strSystemID + "' 의 매핑 정보가 존재하지 않습니다.");
		return;
	}

	var objBusiness = objSystem.selectSingleNode("Business[@BusinessID='" + strBusinessID + "']");
	if (objSystem == null)
	{
		alert("시스템 '" + strSystemID + "' 에 업무 '" + strBusinessID + "' 의 매핑 정보가 존재하지 않습니다.");
		return;
	}

	var strBaseXPath = objBusiness.selectSingleNode("BaseXPath").text;
	var objBaseElement = objRoot.selectSingleNode(strBaseXPath);

	if (objBaseElement == null)
	{
		alert("기간데이터 XML에 '" + strBaseXPath + "' 엘리먼트가 존재하지 않습니다.");
		return;
	}

	var objFieldList = objBusiness.selectNodes("Field");
	var nFieldCount = objFieldList.length;
	for (var i = 0; i < nFieldCount; i++)
	{
		var objField = objFieldList.item(i);
		var strFieldName = objField.selectSingleNode("FieldName").text;
		var strXPath = objField.selectSingleNode("XPath").text;
		var objNode = objBaseElement.selectSingleNode(strXPath);
		if (objNode != null)
		{
			var strFieldValue = objNode.text;
			Document_Hun2KLa.SetRecordTextByName(strFieldName, -1, strFieldValue);
		}
	}

/*
	objRoot = objRoot.selectSingleNode("//legacy_data");
	var strBusinessID = getBusinessID(getBusinessInfo(getLegacySystem()));
	var strFields = Document_Hun2KLa.GetAllRecordNames("\u0002", 1);
	var arrField = strFields.split("\u0002");
	var nFieldCount = arrField.length;
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
			Document_Hun2KLa.SetRecordTextByName(arrField[i], -1, objElement.text);
		}
	}
*/
}

function displayArbitProxy(objManInfo)
{
	var strFieldName;
	var strRole = objManInfo.selectSingleNode("var_man_role").text;
	if (strRole == "6" && Document_Hun2KLa.IsExistRecord(FIELD_REAL_NAME_ARBIT))
		strFieldName = FIELD_REAL_NAME_ARBIT;
	else if (strRole == "7" && Document_Hun2KLa.IsExistRecord(FIELD_REAL_NAME_PROXY))
		strFieldName = FIELD_REAL_NAME_PROXY;
	else if ((strRole == "6" || strRole == "7") && Document_Hun2KLa.IsExistRecord(FIELD_REAL_NAME))
		strFieldName = FIELD_REAL_NAME;
	else
		return;

	var strValue = "";
	if (strRole == "6")
	{
		strValue += FIELD_ARBITRARY + " ";
	}
	else if (strRole == "7")
	{
		strValue += FIELD_PROXY + " ";
	}
	strValue += objManInfo.selectSingleNode("var_man_position").text

	if (g_strOption8 == "0")
	{
	}
	else if (g_strOption8 == "1")
	{
		strValue += " " + objManInfo.selectSingleNode("var_man_name").text;
	}
	else
	{
		strValue += " " + objManInfo.selectSingleNode("var_man_name").text;
	}

	Document_Hun2KLa.SetRecordTextByName(strFieldName, -1, strValue);
}

function setPriorOpinion(strOpinion)
{
	if (Document_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION) == true)
		Document_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION, -1, strOpinion);
	else if (Document_Hun2KLa.IsExistRecord(FIELD_INSTRUCTION_ITEM) == true)
		Document_Hun2KLa.SetRecordTextByName(FIELD_INSTRUCTION_ITEM, -1, strOpinion);
}

function setZipcode(strZipcode, strAddress)
{
	if (Document_Hun2KLa.IsExistRecord(FIELD_ZIPCODE) == true)
		Document_Hun2KLa.SetRecordTextByName(FIELD_ZIPCODE, -1, strZipcode);
	if (Document_Hun2KLa.IsExistRecord(FIELD_ADDRESS) == true)
		Document_Hun2KLa.SetRecordTextByName(FIELD_ADDRESS, -1, strAddress);
}

function saveBodyToXML(strBodyXMLPath)
{
//	var bReturn = true;
	return Document_Hun2KLa.SaveAsDocument(strBodyXMLPath, 0);
/*
	if (bReturn != false)
	{
		var strBody = readLocalFile(strBodyXMLPath);
		strBody = "<?xml version=\"1.0\" encoding=\"euc-kr\"?>\r\n<content>" + strBody;
		strBody = strBody + "</content>";
		bReturn = saveToLocalFile(strBodyXMLPath, strBody);
	}
	return bReturn;
*/
}

function getFieldText(strFieldName)
{
	return Document_Hun2KLa.GetRecordTextByNameEx(strFieldName, true);
}

function getHashTable()
{
	var strFields = Document_Hun2KLa.GetAllRecordNames("^", 0);
	var arrFields = strFields.split("^");
	var nLength = arrFields.length;
	var objDic = new Array();
	for (var i = 0; i < nLength-1; ++i)
	{
		var strValue = Document_Hun2KLa.GetRecordTextByNameEx(arrFields[i], true);
		objDic[arrFields[i]] = strValue;
	}
	return objDic;
}

function readBodyFormXML(strFilePath)
{
	return Document_Hun2KLa.OpenDocument(strFilePath, 2);
}

function convContentString(strData)
{
	strData = strData.replace("<content>", "");
	strData = strData.replace("</content>", "");
	return strData;
}

function setFieldsText(objDic)
{
	var bReturn = true;
	var strFields = Document_Hun2KLa.GetAllRecordNames("^", 0);
	var arrFields = strFields.split("^");
	var nLength = arrFields.length;
	for (var i = 0; i < nLength-1; ++i)
	{
		if (isExistKey(objDic, arrFields[i]))
		{
			var strValue = getValue(objDic, arrFields[i]);
			bReturn = Document_Hun2KLa.SetRecordTextByName(arrFields[i], -1, strValue);
			if (bReturn == false)
				break;
		}
	}
	return bReturn;
}

function setFieldsImage(objDic)
{
	var bReturn = true;
	var strFields = Document_Hun2KLa.GetAllRecordNames("^", 0);
	var arrFields = strFields.split("^");
	var nLength = arrFields.length;
	for (var i = 0; i < nLength-1; ++i)
	{
		if (isExistKey(objDic, arrFields[i]))
		{
			var strValue = getValue(objDic, arrFields[i]);
			bReturn = Document_Hun2KLa.SetRecordImageFromFile(arrFields[i], strValue);
			if (bReturn == false)
				break;
		}
	}
	return bReturn;
}

function onSetViewScale(bExpand)
{
	var nFactor = 2;
	if (bExpand)
		nFactor = 4;

	Document_Hun2KLa.SetZoomFactor(nFactor);

	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce != null)
		Enforce_Hun2KLa.SetZoomFactor(nFactor);
}

function appendDocumentRecipientsToForm(nCaseNumber)
{
}

function setDrafterField()
{
	// 새로 문서를 작성할 때만 셋팅(기안 && 보류가 아닌 경우)
	if ((g_strEditType == "0" && g_strCabinet != "PRIVATE" && g_strCabinet != "SUBMITED" && g_strCabinet != "SUBMITEDAPPROVAL" && g_strCabinet != "SUBMITEDDOCFLOW")
		|| (g_strEditType == "10" && g_strCabinet == ""))
	{
		var strDocCategory = getDocCategory(1);
		if (strDocCategory == "I" && Document_Hun2KLa.IsExistRecord(FIELD_RECIPIENT) == true)
			Document_Hun2KLa.SetRecordTextByName(FIELD_RECIPIENT, -1, INNER_APPROVAL);

		if (Document_Hun2KLa.IsExistRecord(FIELD_ORGAN) && g_strUserOrgDisplayName != "")
			Document_Hun2KLa.SetRecordTextByName(FIELD_ORGAN, -1, g_strUserOrgDisplayName);

		if (Document_Hun2KLa.IsExistRecord(FIELD_SENDER_AS + "#1") && g_strSenderAs != "")
			Document_Hun2KLa.SetRecordTextByName(FIELD_SENDER_AS, -1, g_strSenderAs);

		if (Document_Hun2KLa.IsExistRecord(FIELD_EMAIL))
			Document_Hun2KLa.SetRecordTextByName(FIELD_EMAIL, -1, g_strDeptEmail);

		if (Document_Hun2KLa.IsExistRecord(FIELD_HOMEPAGE))
			Document_Hun2KLa.SetRecordTextByName(FIELD_HOMEPAGE, -1, g_strDeptHomepage);

		if (Document_Hun2KLa.IsExistRecord(FIELD_TELEPHONE))
			Document_Hun2KLa.SetRecordTextByName(FIELD_TELEPHONE, -1, g_strDeptTelephone);

		if (Document_Hun2KLa.IsExistRecord(FIELD_FAX))
			Document_Hun2KLa.SetRecordTextByName(FIELD_FAX, -1, g_strDeptFax);

		if (Document_Hun2KLa.IsExistRecord(FIELD_ZIPCODE))
			Document_Hun2KLa.SetRecordTextByName(FIELD_ZIPCODE, -1, g_strDeptZipCode);

		if (Document_Hun2KLa.IsExistRecord(FIELD_ADDRESS))
			Document_Hun2KLa.SetRecordTextByName(FIELD_ADDRESS, -1, g_strDeptAddress + " " + g_strDeptAddressDetail);
	}
}

// icaha, document switching
onBodyAccessLoadingCompleted();
