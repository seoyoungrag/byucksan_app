var g_bDocumentHwpProxyCreated = false;
// 20031025 pubdoc
var g_bHeadBodySelected = false;
var g_bLegacyFormSelected = false;
var g_bIsModified = false;
var g_bHwpDocumentModify;
var g_bHwpDocumentOpen = false;
var g_strHwpDocumentTitle = "";
var g_bPageLoad = false;
var g_bHwpNeedClose = false;

function getDocumentMenuSet()
{
	var strMenuSet = document.getElementById("hwpMenuSet").value;

	// document switching
	if (g_strCaller == "" && g_strBodyType == "hwp")
	{
		if (g_strEditType == "0" && g_strDataUrl != "" || g_strEditType != "0" && g_strEditType != "5" && g_strEditType != "13" || g_bPreviewConvert == false)
			strMenuSet = "D/77,D/78," + strMenuSet;
	}

	if (strMenuSet.charAt(strMenuSet.length - 1) == ',')
		strMenuSet = strMenuSet.substring(0, strMenuSet.length - 1);

	var strDocumentMenuSet = "";
	var arrMenu  = strMenuSet.split(",");
	for (var i = 0; i < arrMenu.length; i++)
	{
		var arrMenuItem = arrMenu[i].split("/");
		var strMenuCategory = arrMenuItem[0];
		var strMenuID = arrMenuItem[1];
		if (strMenuCategory == "D" || strMenuCategory == "A" ||
			(strMenuCategory == "E" && getIsDirect() == "Y"))
		{
			if (strDocumentMenuSet != "")
				strDocumentMenuSet += ",";
			strDocumentMenuSet += strMenuID;
		}
	}
	return strDocumentMenuSet;
}

function onLoadDocument()
{
		alert('cn_ApproveHwpDocument.js');
/*
	Document_HWPProxy2.attachEvent("OnCreated", onDocumentHwpProxyCreated);
	Document_HWPProxy2.attachEvent("OnHWPNotFound", onDocumentHwpNotFound);
	Document_HWPProxy2.attachEvent("OnHWPCommand", onDocumentHwpProxyCommand);
*/
	g_bPageLoad = true;
	loadDocument();
}

function onUnloadDocument()
{
//	closeDocument();
}

function onDocumentHwpProxyCreated()
{
	if (g_bHwpNeedClose == true)
		onCloseHwp(true);

	if (g_bDocumentHwpProxyCreated == true)
		return;
	g_bDocumentHwpProxyCreated = true;

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

// 20031025 pubdoc
	if (getIsPubDoc() == "Y")
	{
		if (g_bHeadBodySelected == false)
			return;
	}

	loadDocument();
}

function onDocumentHwpNotFound()
{
}

function loadDocument()
{
	if (g_bHwpNeedClose == true)
		onCloseHwp(true);

	if (g_bDocumentHwpProxyCreated == false || g_bPageLoad == false)
		return;
//	Document_HWPProxy2.attachEvent("OnHWPCommand", onDocumentHwpProxyCommand);

	var bReplace = g_bHwpDocumentOpen;
	g_bHwpDocumentModify = (g_strEditType == "0" || g_strEditType == "10");
/*
	// 삼립
	if (g_strEditType == "0")
	{
		if (getLegacySystem() != null)
		{
			g_bHwpDocumentModify = false;
		}
	}
*/
	var nEditMode = (g_bHwpDocumentModify ? 2 : 1);
	var nMenuSetID = 0;
	var nWindowPos = 0;

	g_bIsModified = (g_strEditType == "0" || g_strEditType == "10");
	if (getLegacySystem() != null && getBodyFileObj() == null)
		g_bIsModified = true;

	downloadDefaultFile();

	if (g_strEditType == "10" || g_strEditType == "18" || g_strEditType == "19" || g_strEditType == "20" ||
		g_strEditType == "21" || g_strEditType == "21" || g_strEditType == "22")
		g_strHwpDocumentTitle = ENFORCE_DOCUMENT;
	else if ((g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" ||
		g_strEditType == "16" || g_strEditType == "17") && g_strIsDirect == "Y")
		g_strHwpDocumentTitle = ENFORCE_DOCUMENT;
	else
		g_strHwpDocumentTitle = DRAFT_DOCUMENT;

	if (g_strFormUrl != "")
	{
		if (g_strFormName != "")
			g_strHwpDocumentTitle += " - [" + g_strFormName + "]";
		g_strHwpDocumentTitle = g_strHwpDocumentTitle.replace(/,/g, " ");

		if (getLocalFileSize(g_strDownloadPath + g_strFormUrl) > 0)
		{
			Document_HWPProxy2.OpenDocumentEx(g_strHwpDocumentTitle, g_strDownloadPath + g_strFormUrl, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
		}
		else
		{
			alert(ALERT_FIND_DOCUMENT_ERROR);
			return;
		}

		var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
		setIsForm((bIsForm ? "Y" : "N"));

//		setFormDataToXml();

		setDrafterField();
		setXmlDataToForm();
		setSenderAsToForm(g_strSenderAs);
		if (getIsPubDoc() != "Y")
			setDocumentRecipientsToForm();
		setOrganImage();

		if (g_strDataUrl != "")
		{
//			unPack();
			if (getIsPubDoc() == "Y")
			{
				setDocumentBody();
				setDocumentField();
			}
			if (getIsAdminMis() == "Y")
				setDocumentBody();
		}

		if (bIsForm)
		{
			var nBodyCount = 2;
			var strField = FIELD_TITLE + nBodyCount;
			while (Document_HWPProxy2.IsExistField(strField))
			{
				increaseBodyCount();
				strField = FIELD_TITLE + (++ nBodyCount);
			}
		}

		if (g_strDataUrl != "" && getIsAdminMis() == "Y")
		{
			var strFileName = "";
			var objBodyFile = getBodyFileObj();
			if (objBodyFile != null)
				strFileName = getAttachFileName(objBodyFile);

//			본문이 2번 들어가는 원인이 됩니다.
//			if (strFileName != "")
//				Document_HWPProxy2.InsertAux(g_strDownloadPath + strFileName, 1);

			if (Document_HWPProxy2.IsExistField(FIELD_SENDER_AS + "#1"))
				Document_HWPProxy2.PutFieldText(FIELD_SENDER_AS + "#1", getSender());

			if (Document_HWPProxy2.IsExistField(FIELD_ORGAN))
				Document_HWPProxy2.PutFieldText(FIELD_ORGAN, getDraftOrgName());

			var nFind = strFileName.lastIndexOf(".");
			if (nFind != -1)
			{
				strFileName = strFileName.substring(0, nFind) + "." + getFileExtension();
				setAttachFileName(objBodyFile, strFileName);
			}

			saveBodyFile();
			setModified(true);
			setDrafterField();
			setXmlDataToForm();
			restoreApproveFlow();

//			setRelatedAttachToGeneralAttach();
		}
	}
	else if (g_strDataUrl != "")
	{
		if (getTitle(1) != "")
			g_strHwpDocumentTitle += " - [" + getTitle(1) + "]";
		g_strHwpDocumentTitle = g_strHwpDocumentTitle.replace(/,/g, " ");

		var strFileName = "";
		var objBodyFile = getBodyFileObj();
		if (objBodyFile != null)
			strFileName = getAttachFileName(objBodyFile);

		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			if (getLocalFileSize(strLocalPath) > 0)
			{
				Document_HWPProxy2.OpenDocumentEx(g_strHwpDocumentTitle, strLocalPath, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
			}
			else
			{
				alert(ALERT_FIND_DOCUMENT_ERROR);
				return;
			}

//			if (Document_HWPProxy2.IsExistField(FIELD_SENDER_AS + "#1"))
//				g_strSenderAs = Document_HWPProxy2.getFieldText(FIELD_SENDER_AS + "#1");

			var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
			setIsForm((bIsForm ? "Y" : "N"));

			setDrafterField();
			setXmlDataToForm();
			restoreApproveFlow();
		}
		else
		{
			setIsForm("N");
			// no body file
//			Document_HWPProxy2.NewDocumentEx(g_strHwpDocumentTitle, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
		}
	}
	else
	{
		Document_HWPProxy2.NewDocumentEx(g_strHwpDocumentTitle, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
		setIsForm("N");
	}

//	일단 필요없어서 막음. 추후 수정기안 시 필요...
//	Document_HWPProxy2.PasteProposal();

	Document_HWPProxy2.SetModified(0);
	Document_HWPProxy2.SetVisible(true);
	Document_HWPProxy2.SetDocumentViewRatio("width","");
	Document_HWPProxy2.Focus();
	g_bHwpDocumentOpen = true;
	onDocumentOpen();
}

function closeDocument()
{
	Document_HWPProxy2.CloseDocument();
}

function changeFormUrl(strFormUrl)
{
	var bReplace = true;
	var nEditMode = (g_bHwpDocumentModify ? 2 : 1);
	var nMenuSetID = 0;
	var nWindowPos = 0;

	g_strFormUrl = strFormUrl;

	Document_HWPProxy2.CopyProposal(1);

	Document_HWPProxy2.OpenDocumentEx(g_strHwpDocumentTitle, g_strDownloadPath + strFormUrl, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nMenuSetID, g_bHideParent);

	Document_HWPProxy2.PasteProposal();

	setXmlDataToForm();

	var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
	setIsForm((bIsForm ? "Y" : "N"));

	if (getCurrentLineType() == "5")
	{
		var strLineName = getCurrentLineName();
		var objInspBodyFile = findExtendAttachInfo("InspBody", strLineName, "0");
		if (objInspBodyFile == null)
			setBodyFileInfo();
	}

	saveBodyFile();
	setModified(true);

	Document_HWPProxy2.SetVisible(true);
	Document_HWPProxy2.SetDocumentViewRatio("width", "");
	Document_HWPProxy2.Focus();
//	g_bIsModified = true;
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

	var bArrangeMode = false;
	if (getFlowStatus() == "0")
		bArrangeMode = true;
	if (Document_HWPProxy2.SaveDocument(g_strDownloadPath + strFileName, bArrangeMode) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objBodyFile, strFileSize);
	}
	restoreApproveFlow();
	Document_HWPProxy2.SetModified(0);

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
		if (g_strOption148 == "0")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName("0");

		if (objApprovalLine == null)
			return;

		// 편철 사용여부에 따라 신사무관리 적용유무 판단
		if (g_strOption148 == "0")
			setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
		else
			setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}


function setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
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

				if (strApprovalField.indexOf(FIELD_CONSIDER) >= 0 && bIsForm == true)
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
						var arrOption37 = g_strOption37.split("^");
						strPosition = getUserPosition(objApprover, arrOption37[0]);
						if (getApproverRepName(objApprover) != "")
							strPosition = getUserRepPosition(objApprover, arrOption37[0]);
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
						var strSignUrl = "";
						if (g_strSignType != "2")
							strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Document_HWPProxy2.PutStampFromFile(strApprovalField, g_strDownloadPath + strSignUrl, true);
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
					Document_HWPProxy2.ClearStamp(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Document_HWPProxy2.ClearStamp(strApprovalField);
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
			Document_HWPProxy2.MakeApprovalFields(strFieldSlots);

		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(false);

		if (bIsForm == true)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
				Document_HWPProxy2.SetCellShape(arrSlashField[i], "diagonalslashon");
		}

		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(true);

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

		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}
/*

function setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
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
		else if (strRole == "4")                        // 열람 필드 추가
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

				if (strApprovalField.indexOf(FIELD_CONSIDER) >= 0 && bIsForm == true)
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
// *
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
// *
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
						var arrOption37 = g_strOption37.split("^");
						strPosition = getUserPosition(objApprover, arrOption37[0]);
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
								Document_HWPProxy2.PutStampFromFile(strApprovalField, g_strDownloadPath + strSignUrl, true);
						}
					}
				}
				else
				{
					Document_HWPProxy2.ClearStamp(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Document_HWPProxy2.ClearStamp(strApprovalField);
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
			Document_HWPProxy2.MakeApprovalFields(strFieldSlots);

		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(false);

		if (bIsForm == true)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
				Document_HWPProxy2.SetCellShape(arrSlashField[i], "diagonalslashon");
		}

		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(true);

		if (g_strEditType == "0")               // drafter
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

		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}
*/
function setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
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
						if (getApproverRepName(objApprover) != "")
							strPosition = getUserRepPosition(objApprover, arrOption37[0]);

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
				var strExtraField = "";
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
					// 신사무관리 규정은 전대결의 표시방법은 결재일자와 함께 상단에 표시
					// 대결 후 전결의 경우는 하단에 표시
					var objPrevApprover = getPrevApprover(objApprover);
					var strPrevRole = getApproverRole(objPrevApprover);
					if (strPrevRole == "7")
						strExtraField = "@";

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

				// 날짜 필드에 먼저 세팅함 - 공석사유가 있을경우, 대결후 전결일 경우에는 도장필드
				if (strEmptyReason != "" || strExtraField != "")
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
						var strSignUrl = strSignUrl = getApproverSignFileName(objApprover);
						if (strSignUrl != "" && strApprovalField != "")
						{
							if (getLocalFileSize(g_strDownloadPath + strSignUrl) > 0)
								Document_HWPProxy2.PutStampFromFile(strApprovalField, g_strDownloadPath + strSignUrl, true);
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
					Document_HWPProxy2.ClearStamp(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					Document_HWPProxy2.ClearStamp(strApprovalField);
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
			Document_HWPProxy2.MakeApprovalFields(strFieldSlots);
/*
		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(false);

		if (bIsForm == true)
			markUnusedSlots(strFieldSlots);

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
				Document_HWPProxy2.SetCellShape(arrSlashField[i], "diagonalslashon");
		}

		if (g_bHwpDocumentModify == false)
			Document_HWPProxy2.SetReadOnly(true);
*/
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
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

	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var nFieldCount = arrField.length;
	var strField;
	var strFieldList = "", strValueList = "";
	for (i = 0; i < nFieldCount; i++)
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
					Document_HWPProxy2.SetCellShape(strField, "diagonalslashon");
				else
					Document_HWPProxy2.SetCellShape(strField, "diagonalslashoff");
				strFieldList += strField + "\u0002#" + strField + "\u0002$" + strField + "\u0002*" + strField + "\u0002";
				strValueList += "\u0002\u0002\u0002\u0002";
			}
			else
			{
				Document_HWPProxy2.SetCellShape(strField, "diagonalslashoff");
			}
		}
	}
	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
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
		if (Document_HWPProxy2.IsExistField(FIELD_INSPECTOR))
		{
			var strSignFileName = getDelivererSignFilename(objDeliverer);
			if (strSignFileName != "")
			{
				var strSignFilePath = g_strDownloadPath + strSignFileName;

				if (getLocalFileSize(strSignFilePath) > 0)
					Document_HWPProxy2.PutStampFromFile(FIELD_INSPECTOR, strSignFilePath, true);
			}
		}

		if (Document_HWPProxy2.IsExistField(FIELD_INSPECT_DATE))
		{
			var strSignDate = getDelivererSignDate(objDeliverer);
			if (strSignDate != "")
			{
//				strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));
				var strDate = "";
				strDate = getDateDisplay(strSignDate, g_strOption36);
				Document_HWPProxy2.PutFieldText(FIELD_INSPECT_DATE, strDate);
			}
		}
	}
}

// 수동 날인시 저장 요구됨
function setApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();

	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

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
				var nImageWidth = parseInt(getAttachImageWidth(objRelatedAttach));
				var nImageHeight = parseInt(getAttachImageHeight(objRelatedAttach));
				var strFilePath = g_strDownloadPath + strFileName;

				if (getLocalFileSize(strFilePath) > 0)
				{
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
									Document_HWPProxy2.RenameField(strReplaceField, strFieldName);
								else if (isExistKey(objDic, FIELD_STAMP))
									Document_HWPProxy2.RenameField(FIELD_STAMP, strFieldName);
								else if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
									Document_HWPProxy2.InsertSealHwp(strFieldName);

								if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
									Document_HWPProxy2.AdjustSealPos(strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);
								if (strFormUsage == "0" || strFormUsage == "")
									Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								else
									Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);
							}
							else
							{
								alert(ALERT_CLICK_POSITION_TO_STAMP);
								if (strFormUsage == "0" || strFormUsage == "")
									Document_HWPProxy2.PutSealFromFileEx(strFieldName, strFilePath);
								else
									Document_HWPProxy2.PutSealFromFileExWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);

								setModified(true);
							}
						}
						else
						{
							alert(ALERT_CLICK_POSITION_TO_STAMP);
							if (strFormUsage == "0" || strFormUsage == "")
								Document_HWPProxy2.PutSealFromFileEx(strFieldName, strFilePath);
							else
								Document_HWPProxy2.PutSealFromFileExWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);

							setModified(true);
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

						if (isExistKey(objDic, strFieldName) || isExistKey(objDic, strReplaceField) ||
							isExistKey(objDic, FIELD_OMIT_STAMP))
						{
							if (isExistKey(objDic, strReplaceField))
								Document_HWPProxy2.RenameField(strReplaceField, strFieldName);
							else if (isExistKey(objDic, FIELD_OMIT_STAMP))
								Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);

							if (strFileName == "")
								Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
							else
							{
								if (strFormUsage == "0" || strFormUsage == "")
									Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								else
									Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);
							}
						}
						else
						{
							alert(ALERT_CLICK_POSITION_TO_STAMP);
							if (strFormUsage == "0" || strFormUsage == "")
								Document_HWPProxy2.PutOmitSealFromFileEx(strFieldName, strFilePath);
							else
								Document_HWPProxy2.PutOmitSealFromFileExWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);

							setModified(true);
						}
					}
				}
				else
				{
					if (strFieldName == FIELD_DEPT_OMIT_STAMP)
					{
						if (Document_HWPProxy2.IsExistField(FIELD_DEPT_OMIT_STAMP))
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						else if (Document_HWPProxy2.IsExistField(FIELD_COMPANY_OMIT_STAMP))
						{
							Document_HWPProxy2.RenameField(FIELD_COMPANY_OMIT_STAMP, strFieldName);
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
						else if (Document_HWPProxy2.IsExistField(FIELD_OMIT_STAMP))
						{
						    Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
						    Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
					}
					else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
					{
						if (Document_HWPProxy2.IsExistField(FIELD_COMPANY_OMIT_STAMP))
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						else if (Document_HWPProxy2.IsExistField(FIELD_DEPT_OMIT_STAMP))
						{
							Document_HWPProxy2.RenameField(FIELD_DEPT_OMIT_STAMP, strFieldName);
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
						else if (Document_HWPProxy2.IsExistField(FIELD_OMIT_STAMP))
						{
						    Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
						    Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
					}
				}
			}
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function restoreApprovalSeal()
{
	var objRelatedAttach = getFirstRelatedAttach();

	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

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
				var nImageWidth = parseInt(getAttachImageWidth(objRelatedAttach));
				var nImageHeight = parseInt(getAttachImageHeight(objRelatedAttach));
				var strFilePath = g_strDownloadPath + strFileName;

				if (getLocalFileSize(strFilePath) > 0)
				{
					// 수동으로 날인된 경우
					if (isExistKey(objDic, strFieldName))
					{
						if (strFormUsage == "0" || strFormUsage == "")
							Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
						else
							Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);
					}
					// 자동으로 날인된 경우
					else
					{
						if (strFieldName == FIELD_DEPT_STAMP || strFieldName == FIELD_COMPANY_STAMP)
						{
							var strUnionField = (strFieldName == FIELD_DEPT_STAMP ? FIELD_COMPANY_STAMP : FIELD_DEPT_STAMP);
							if (isExistKey(objDic, strUnionField) || isExistKey(objDic, FIELD_STAMP)
								|| isExistKey(objDic, FIELD_SENDER_AS + "#1"))
							{
								if (isExistKey(objDic, strUnionField))
									Document_HWPProxy2.RenameField(strReplaceField, strFieldName);
								else if (isExistKey(objDic, FIELD_STAMP))
									Document_HWPProxy2.RenameField(FIELD_STAMP, strFieldName);
								else if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
									Document_HWPProxy2.InsertSealHwp(strFieldName);

								if (isExistKey(objDic, FIELD_SENDER_AS + "#1"))
									Document_HWPProxy2.AdjustSealPos(strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

								if (strFormUsage == "0" || strFormUsage == "")
									Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
								else
									Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);
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

							if (isExistKey(objDic, strReplaceField) || isExistKey(objDic, FIELD_OMIT_STAMP))
							{
								if (isExistKey(objDic, strReplaceField))
									Document_HWPProxy2.RenameField(strReplaceField, strFieldName);
								else if (isExistKey(objDic, FIELD_OMIT_STAMP))
									Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);

								if (strFileName == "")
									Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
								else
								{
									if (strFormUsage == "0" || strFormUsage == "")
										Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
									else
										Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, nImageWidth, nImageHeight);
								}
							}
						}
					}
				}
				else
				{
					if (strFieldName == FIELD_DEPT_OMIT_STAMP)
					{
						if (Document_HWPProxy2.IsExistField(FIELD_DEPT_OMIT_STAMP))
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						else if (Document_HWPProxy2.IsExistField(FIELD_COMPANY_OMIT_STAMP))
						{
							Document_HWPProxy2.RenameField(FIELD_COMPANY_OMIT_STAMP, strFieldName);
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
						else if (Document_HWPProxy2.IsExistField(FIELD_OMIT_STAMP))
						{
						    Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
						    Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
					}
					else if (strFieldName == FIELD_COMPANY_OMIT_STAMP)
					{
						if (Document_HWPProxy2.IsExistField(FIELD_COMPANY_OMIT_STAMP))
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						else if (Document_HWPProxy2.IsExistField(FIELD_DEPT_OMIT_STAMP))
						{
							Document_HWPProxy2.RenameField(FIELD_DEPT_OMIT_STAMP, strFieldName);
							Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
						}
						else if (Document_HWPProxy2.IsExistField(FIELD_OMIT_STAMP))
						{
						    Document_HWPProxy2.RenameField(FIELD_OMIT_STAMP, strFieldName);
						    Document_HWPProxy2.PutFieldText(strFieldName, strFieldName);
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
	var strFormUsage = getFormUsage();
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

				if (Document_HWPProxy2.IsExistField(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
					{
						if (strFormUsage == "0" || strFormUsage == "")
							Document_HWPProxy2.PutStampFromFile(strFieldName, strFilePath, true);
						else
							Document_HWPProxy2.PutStampFromFileWithSize(strFieldName, strFilePath, 20, 20);
					}
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
	if (Document_HWPProxy2.IsExistField(FIELD_INSPECTOR))
		Document_HWPProxy2.ClearStamp(FIELD_INSPECTOR);

	if (Document_HWPProxy2.IsExistField(FIELD_INSPECT_DATE))
		Document_HWPProxy2.PutFieldText(FIELD_INSPECT_DATE, "");
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

			Document_HWPProxy2.ClearStamp(strFieldName);
		}
		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function clearUserSign(strUserID)
{
	setApprovalField(2, false, strUserID);
}

function setDocumentRecipientsToForm()
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

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

			if (isExistKey(objDic, strDeptField))
			{
				strFieldList += strDeptField + "\u0002";
				if (nRecipientCount == 0)
					strValueList += strDisplayAs + "\u0002";
				else if (nRecipientCount == 1)
				{
					if (strRefName != "")
						strValueList += strDeptName + "(" + strRefName + ")" + "\u0002";
					else
						strValueList += strDeptName + "\u0002";

//					strValueList += strDeptName + "\u0002";
				}
				else
					strValueList += RECIPIENT_REFER + "\u0002";
			}

			if (isExistKey(objDic, strRefField))
			{
				strFieldList += strRefField + "\u0002";
				if (nRecipientCount == 0)
					strValueList += "\u0002";
				else if (nRecipientCount == 1)
					strValueList += "\u0002";
//					strValueList += strRefName + "\u0002";
				else
				{
					strValueList += "\u0002";
/*
					if (getRefRecipientCount(objRecipGroup) > 0)
						strValueList += RECIPIENT_REFER + "\u0002";
					else
						strValueList += "\u0002";
*/
				}
			}

			if (isExistKey(objDic, strDeptRefField))
			{
				strFieldList += strDeptRefField + "\u0002";
				if (nRecipientCount < 2)
				{
					strValueList += "\u0002";
					if (isExistKey(objDic, RECIPIENT_TITLE))
					{
						strFieldList += RECIPIENT_TITLE + "\u0002";
						strValueList += "\u0002";
					}
				}
				else
				{
					// 옵션화...
					strValueList += strDeptRefName + "\u0002";
//					strValueList += LABEL_RECIPIENT + ": " + strDeptRefName + "\u0002";
					if (isExistKey(objDic, RECIPIENT_TITLE))
					{
						strFieldList += RECIPIENT_TITLE + "\u0002";
						strValueList += RECIPIENT_TITLE + "\u0002";
					}
				}
			}
		}
		else		// no RecipGroup
		{
			if (isExistKey(objDic, strDeptField))
			{
				strFieldList += strDeptField + "\u0002";
				var strDocCategory = getDocCategory(nCaseNumber);
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
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}

	saveBodyFile();
	setModified(true);
}

function appendDocumentRecipientsToForm(nCaseNumber)
{
	var strFieldList = "", strValueList = "";
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

	// 기존 수신처 값을 가져온다.
	var nOriginRecipCount = 0;
	var arrRecipients = g_strRecipients.split("\u0002");	
	if (arrRecipients[(nCaseNumber - 1) * 2] == "N")
	{
		if (Document_HWPProxy2.IsExistField(strDeptRefField))
		{
			strDeptRefName = getFieldText(strDeptRefField);
			arrRecipients[(nCaseNumber - 1) * 2] = strDeptRefName;
			nOriginRecipCount = arrRecipients[(nCaseNumber * 2) - 1];

			for (var nLoop = 0; nLoop < arrRecipients.length; nLoop++)
			{	
				if (nLoop == 0)
					g_strRecipients = arrRecipients[nLoop] + "\u0002";
				else
					g_strRecipients += arrRecipients[nLoop] + "\u0002";
			}
		}
	}
	else
	{
		strDeptRefName = arrRecipients[(nCaseNumber - 1) * 2];
		nOriginRecipCount = arrRecipients[(nCaseNumber * 2) - 1];
	}
		
	var objRecipGroup = getRecipGroup(nCaseNumber);
	if (objRecipGroup != null)
	{
		var nRecipientCount = parseInt(getRecipientCountWithNew(objRecipGroup));
		if (parseInt(nOriginRecipCount) == 1)
		{
			var objRecipOriginGroup = getRecipGroupFromOrigin(g_objOriginRecipients, nCaseNumber);
			var objRecipient = getFirstRecipient(objRecipOriginGroup);
			if (objRecipient != null)
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
				
				strDeptRefName = strDeptName;
				if (strRefName != "")
					strDeptRefName += "(" + strRefName + ")";
			}
		}
		nRecipientCount += parseInt(nOriginRecipCount);

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
				if (getRecipientIsNew(objRecipient) == "Y")
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
				}
				
				objRecipient = getNextRecipient(objRecipient);
			}
		}
		
		if (Document_HWPProxy2.IsExistField(strDeptField) && Document_HWPProxy2.IsExistField(strRefField))
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
		else if (Document_HWPProxy2.IsExistField(strDeptField))
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
		else if (Document_HWPProxy2.IsExistField(strRefField))
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

		if (Document_HWPProxy2.IsExistField(strDeptRefField))
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
		if (Document_HWPProxy2.IsExistField(strDeptField) && Document_HWPProxy2.IsExistField(strRefField))
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
		else if (Document_HWPProxy2.IsExistField(strDeptField))
		{
			// 수신필드만 있는 경우
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nCaseNumber);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";
		}
		else if (Document_HWPProxy2.IsExistField(strRefField))
		{
			// 참조필드만 있는 경우
			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}

		if (Document_HWPProxy2.IsExistField(strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			strValueList += "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
	saveBodyFile();
	setModified(true);
}

function getFieldText(strFieldName)
{
	return Document_HWPProxy2.GetFieldText(strFieldName);
}

function addDocument()
{
	var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
	if (bIsForm == false)
		return false;

	var nMode;
	if (g_strOption81 == "0")
		nMode = 1;
	else if (g_strOption81 == "1")
		nMode = 2;
	else
		nMode = 1;

	increaseBodyCount();

	var bReturn;

	if (nMode == 1)
		bReturn = Document_HWPProxy2.AddProposal();
	else
		bReturn = Document_HWPProxy2.AddProposal2();

	var nBodyCount = getBodyCount();
	if (getDocCategory(nBodyCount) == "I")
		Document_HWPProxy2.PutFieldText(FIELD_RECIPIENT + nBodyCount, INNER_APPROVAL);
	else
		Document_HWPProxy2.PutFieldText(FIELD_SENDER_AS + "#" + nBodyCount, g_strSenderAs);
	if (nBodyCount == 2)
		Document_HWPProxy2.PutFieldText(FIELD_BATCH, "(" + FIRST_DRAFT + ")");
}

function removeDocument(nIndex)			// nIndex: zero base
{
	var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
	if (bIsForm == false)
		return false;

	var nMode;
	if (g_strOption81 == "0")
		nMode = 1;
	else if (g_strOption81 == "1")
		nMode = 2;
	else
		nMode = 1;

	decreaseBodyCount(nIndex);

	if (getBodyCount() == 1)
		Document_HWPProxy2.PutFieldText(FIELD_BATCH, "");

	if (nMode == 1)
		return Document_HWPProxy2.DeleteProposal(nIndex - 1);
	else
		return Document_HWPProxy2.DeleteProposal2(nIndex - 1);
//	return Document_HWPProxy2.DeleteProposalByMode(nIndex - 1, nMode);
}

function editDocument(bModify)
{
	var bReplace = true;
	var nEditMode = (bModify ? 2 : 1);
	var nMenuSetID = 0;
//	var nWindowPos = 0;
	var nWindowPos = -1;

	if (g_bHwpDocumentModify == bModify)
	{
		Document_HWPProxy2.ModifyCommandUI(nMenuSetID, "", getDocumentMenuSet());
//		Document_HWPProxy2.MoveDocumentWindow(nWindowPos);
	}
	else
	{
		var objBodyFile = getBodyFileObj();
		if (objBodyFile == null)
			objBodyFile = setBodyFileInfo();

		var strFileName = getAttachFileName(objBodyFile);

		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			if (bModify)				// save to preserve links to images
				Document_HWPProxy2.SaveDocument(strLocalPath, false);
			if (getLocalFileSize(strLocalPath) > 0)
			{
				Document_HWPProxy2.OpenDocumentEx(g_strHwpDocumentTitle, strLocalPath, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
			}
			else
			{
				alert(ALERT_FIND_DOCUMENT_ERROR);
				return false;
			}
		}
		else
		{
			Document_HWPProxy2.NewDocumentEx(g_strHwpDocumentTitle, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
		}

		if (!bModify)
			restoreApproveFlow();

		Document_HWPProxy2.SetVisible(true);
		Document_HWPProxy2.SetDocumentViewRatio("width","");
		Document_HWPProxy2.Focus();
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

	var strFilePath = Document_HWPProxy2.OpenHwpFileDialog(false, strTitle);
	if (strFilePath == "")
		return;
	if (!bIncludeSign)
		clearApproveFlow();
	Document_HWPProxy2.SaveDocument(strFilePath, false);
	if (!bIncludeSign)
		restoreApproveFlow();
}

function saveApproveDocumentAs(strFilePath, bIncludeSign)
{
	if (!bIncludeSign)
		clearApproveFlow();
	Document_HWPProxy2.SaveDocument(strFilePath, false);
	if (!bIncludeSign)
		restoreApproveFlow();
}

function setFormDataToXml()
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
	var arrValue = strValues.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = arrValue[i];

//	var bIsForm = isExistKey(objDic, FIELD_TITLE);
//	if (bIsForm == false)
//		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (isExistKey(objDic, strFieldName) == true)
		{
			var strSubject = getValue(objDic, strFieldName);
			if (strSubject.length > 128)
				strSubject = strSubject.substring(0, 128)

			setTitle(strSubject, nIndex);
		}

		if (isExistKey(objDic, FIELD_SENDER_AS + "#" + nIndex) == true)
		{
			var strSenderAs = getValue(objDic, FIELD_SENDER_AS + "#" + nIndex);
			setSenderAs(strSenderAs, nIndex);
		}

		nIndex++;
	}
/*
	if (isExistKey(objDic, FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getValue(objDic, FIELD_DOC_NUMBER);

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

	if (isExistKey(objDic, FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = getValue(objDic, FIELD_CONSERVE_TERM);

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

	if (isExistKey(objDic, FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = getValue(objDic, FIELD_PUBLIC_BOUND);

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
/*
	// 20030904
	if (getLegacySystem() != null )
		setFormDataToLegacyXML();
*/
}

function setXmlDataToForm()
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	var i;
	for (i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	var strFieldList = "", strValueList = "";

//	var bIsForm = isExistKey(objDic, FIELD_TITLE);
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

		if (isExistKey(objDic, strFieldName) == true)
		{
			strSubject = getTitle(nIndex);

			if (strSubject != Document_HWPProxy2.GetFieldText(strSubject))
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

		if (isExistKey(objDic, strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nIndex);
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
*/
		nIndex++;
	}

	if (isExistKey(objDic, FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
//		var strDocNumber = getDraftProcDeptCode();
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

	if (isExistKey(objDic, FIELD_ENFORCE_NUMBER) == true)
	{
		var strDraftDeptName = getDraftProcDeptName();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDraftDeptName += "-" + strSerialNumber;

		strFieldList += FIELD_ENFORCE_NUMBER + "\u0002";
		strValueList += strDraftDeptName + "\u0002";
	}

	if (isExistKey(objDic, FIELD_ENFORCE_DATE) == true)
	{
		var strEnforceDate = getEnforceDate();
		if (strEnforceDate == "")
		{
/*
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
*/

			var strDocCategory = getDocCategory(nIndex);
			if (strDocCategory == "I")
			{
				strEnforceDate = getCompleteDate();
			}
		}
		var strDate = "";
		if (strEnforceDate != "")
// 20030324, 행자부 spec
			strDate = "(" + getDateDisplay(strEnforceDate, "YYYY.MM.DD.") + ")";
//			strDate = getDateDisplay(strEnforceDate, g_strOption36);

		strFieldList += FIELD_ENFORCE_DATE + "\u0002";
		strValueList += strDate + "\u0002";
	}
	if (isExistKey(objDic, FIELD_RECEIVE_DATE) == true)
	{
		var strReceiveDate = getReceiveDate();
		var strDate = "", strTime = "";
		if (strReceiveDate != "")
		{
// 20030324, 행자부 spec
			strDate = "(" + getDateDisplay(strReceiveDate, "YYYY.MM.DD.") + ")";
//			strDate = getDateDisplay(strReceiveDate, g_strOption36);
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

	if (isExistKey(objDic, FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (isExistKey(objDic, FIELD_INSTRUCTION) == true)
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

	if (isExistKey(objDic, FIELD_INSTRUCTION_ITEM) == true)
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

	if (isExistKey(objDic, FIELD_TREATMENT) == true)
	{
		var strDocCategory = getDocCategory(1);
		strFieldList += FIELD_TREATMENT + "\u0002";
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			var strTreatment = getTreatment(1);
			strValueList += strTreatment + "\u0002";
			try
			{
				Document_HWPProxy2.SetCellLine(FIELD_TREATMENT, "allon", "single");
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
				Document_HWPProxy2.SetCellLine(FIELD_TREATMENT, "allon", "noline");
			}
			catch (e)
			{
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}

//	setDocumentRecipientsToForm();
//	setSenderAsToForm(g_strSenderAs);
}

function getFormInfo()
{
	var strFormInfo = "";

	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	if (getIsForm() == "N")
		return strFormInfo;

	if (isExistKey(objDic, FIELD_DRAFTER + "1") == true || isExistKey(objDic, "#" + FIELD_DRAFTER + "1") == true)
	{
		var nSubmit = 0;
		while (true)
		{
			var strField = FIELD_DRAFTER + (nSubmit + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nSubmit++;
		}

		var nApprove = 0;
		while (true)
		{
			var strField = FIELD_CONSIDER + (nApprove + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nApprove++;
		}

		var nAssist = 0;
		while (true)
		{
			var strField = FIELD_COOPERATE + (nAssist + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nAssist++;
		}

		var nComplete = 0;
		while (true)
		{
			var strField = FIELD_LAST + (nComplete + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nComplete++;
		}
/*
		// 신사무 양식은 결재필드가 없음
		if (nComplete == 0)
		{
			nApprove--;
			nComplete++;
		}
*/
		strFormInfo = nSubmit + "-" + nApprove + "-" + nAssist + "-" + nComplete;
	}
	else if (isExistKey(objDic, FIELD_DRAFTER + "1-1") == true ||
		isExistKey(objDic, "#" + FIELD_DRAFTER + "1-1") == true)
	{
		var nType = 1;
		while (true)
		{
			var nSubmit = 0;
			while (true)
			{
				var strField = FIELD_DRAFTER + nType + "-" + (nSubmit + 1);
				if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
					break;

				nSubmit++;
			}

			var nApprove = 0;
			while(true)
			{
				var strField = FIELD_CONSIDER + nType + "-" + (nApprove + 1);
				if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
					break;

				nApprove++;
			}

			var nAssist = 0;
			while (true)
			{
				var strField = FIELD_COOPERATE + nType + "-" + (nAssist + 1);
				if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
					break;

				nAssist++;
			}

			var nComplete = 0;
			while (true)
			{
				var strField = FIELD_LAST + nType + "-" + (nComplete + 1);
				if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
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
	else if (isExistKey(objDic, FIELD_CHARGER) == true || isExistKey(objDic, "#" + FIELD_CHARGER) == true ||
		isExistKey(objDic, FIELD_PRIOR) == true || isExistKey(objDic, FIELD_PUBLIC) == true)
	{
		var nCharger = 0;
		{
			var strField = FIELD_CHARGER;
			if (isExistKey(objDic, strField) == true || isExistKey(objDic, "#" + strField) == true)
				nCharger++;
		}

		var nPrior = 0;
		while (true)
		{
			var strField = FIELD_PRIOR + (nPrior + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nPrior++;
		}

		var nCircular = 0;
		while (true)
		{
			var strField = FIELD_PUBLIC + (nCircular + 1);
			if (isExistKey(objDic, strField) == false && isExistKey(objDic, "#" + strField) == false)
				break;

			nCircular++;
		}

		strFormInfo = nCharger + "-" + nPrior + "-" + nCircular;
	}

	return strFormInfo;
}

function clearApproveFlow()
{
	if (getIsForm() == "N")
		Document_HWPProxy2.RemoveApprovalFields();
	else
	{
		clearApprovalFlow();
		clearApprovalSign();
		clearExaminationSign();
		clearApprovalSeal();
	}
}

function restoreApproveFlow()
{
	setApprovalFlow();
	setApprovalSign();
	setExaminationSign();
	// 20030429, manual seal...
	restoreApprovalSeal();
//	setApprovalSeal();
	setOrganImage();
}

function setPriorOpinion(strOpinion)
{
	if (Document_HWPProxy2.IsExistField(FIELD_INSTRUCTION) == true)
		Document_HWPProxy2.PutFieldText(FIELD_INSTRUCTION, strOpinion);
	else if (Document_HWPProxy2.IsExistField(FIELD_INSTRUCTION_ITEM) == true)
		Document_HWPProxy2.PutFieldText(FIELD_INSTRUCTION_ITEM, strOpinion);
}

function printDocument()
{
	Document_HWPProxy2.PrintDocument();
}

function getFieldData(strFieldList)
{
	var strValueList = Document_HWPProxy2.GetFieldText(strFieldList);
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

	Document_HWPProxy2.PutFieldText(strFieldList, strValueList);

	saveBodyFile();
	setModified(true);
}

function openFile()
{
	var nRet = Document_HWPProxy2.OpenDocumentWithDialog();
	if (nRet != 0)
	{
		var bIsForm = Document_HWPProxy2.IsExistField(FIELD_TITLE);
		if (bIsForm == true)
		{
			setIsForm("Y");

			var nIndex = 2;
			var strField = FIELD_TITLE + nIndex;
			while (Document_HWPProxy2.IsExistField(strField) == true)
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
}

function isModified()
{
	return g_bIsModified;
}

function setModified(bModified)
{
	g_bIsModified = bModified;
}

function copyProposal(nProposal)
{
	Document_HWPProxy2.CopyProposal(nProposal);
}

function setOrganToForm(strOrganName)
{
	var strFieldList = "", strValueList = "";
	if (Document_HWPProxy2.IsExistField(FIELD_ORGAN) && strOrganName != "")
	{
		strFieldList += FIELD_ORGAN + "\u0002";
		strValueList += strOrganName + "\u0002";
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}

function setSenderAsToForm(strSenderAs)
{
	if (getFlowStatus() != "0")
		return;

	if (typeof(strSenderAs) == "undefined")
		strSenderAs = g_strSenderAs;
	g_strSenderAs = strSenderAs;
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");

	var objDic = new Array();
	var nFieldCount = arrField.length;
	for (var i = 0; i < nFieldCount; i++)
		objDic[arrField[i]] = "";

	var arrSenderAs = strSenderAs.split("\u0002");
	g_strSenderAs = arrSenderAs[0];

	var strFieldList = "", strValueList = "";
	var nBodyCount = getBodyCount();
	for (var n = 1; n <= nBodyCount; n++)
	{
		var strSenderAsField = FIELD_SENDER_AS + "#" + n;
		if (isExistKey(objDic, strSenderAsField))
		{
			strFieldList += strSenderAsField + "\u0002";
			if (getDocCategory(n) == "I")
			{
				strValueList += "\u0002";
				setSenderAs("", n);
				setSenderAsCode("", n);
			}
			else
			{
				strValueList += arrSenderAs[n-1] + "\u0002";
				setSenderAs(arrSenderAs[n-1], n);
			}
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);

		saveBodyFile();
		setModified(true);
	}
}

// 20031008 의견을 본문내용 끝에 삽입
function insertOpinionToBody(strOldOpinion, strOpinion)
{
	var strBodyType = getBodyType();
	var strFormUsage = getFormUsage();
	var strFlowStatus = getFlowStatus();

/*
	if (getBodyType() == "txt" || getBodyType() == "html" || getFormUsage() == "0" || strFlowStatus != "0" || parseInt(g_strEditType) >= 18)
	{
	}
	else
	{
*/
		if (g_strLineName == "0" && strFlowStatus == "0" && strOpinion != "")
		{
			Document_HWPProxy2.MoveToBody(1);
			strOldOpinion = g_strUserName + " : " + strOldOpinion;
//			if (strFieldBody.lastIndexOf(strOldOpinion) != -1)
//				strFieldBody = strFieldBody.replace(strOldOpinion, g_strUserName+" : "+strOpinion);
//			else
//			{
				Document_HWPProxy2.PutText(false, "\r\n" + g_strUserName + " : " + strOpinion);
//			}
			saveBodyFile();
			setModified(true);
		}
//	}
}

function setLegacyXMLToForm(objRoot)
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
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
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}
}

function setFormDataToLegacyXML(objRoot)
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
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
	if (g_strEditType == "0" && g_strCabinet != "PRIVATE" && g_strCabinet != "SUBMITEDAPPROVAL" && g_strCabinet != "SUBMITEDDOCFLOW")
	{
		var strFieldList = FIELD_ENFORCE_NUMBER + "\u0002" + FIELD_ENFORCE_DATE + "\u0002" + FIELD_RECEIVE_NUMBER + "\u0002" + FIELD_RECEIVE_DATE + "\u0002";
		var strValueList = "\u0002\u0002\u0002\u0002";
		if (Document_HWPProxy2.IsExistField(FIELD_ORGAN) == true)
		{
			strFieldList += FIELD_ORGAN + "\u0002";
// 건보 기관표시명
//			strValueList += g_strUserOrgName + "\u0002";
			strValueList += g_strUserOrgDisplayName + "\u0002";
		}
		if (Document_HWPProxy2.IsExistField(FIELD_EMAIL) == true)
		{
			strFieldList += FIELD_EMAIL + "\u0002";
//			strValueList += g_strEmail + "\u0002";
			strValueList += g_strDeptEmail + "\u0002";	// 부서 Email
		}
		if (Document_HWPProxy2.IsExistField(FIELD_HOMEPAGE) == true)
		{
			strFieldList += FIELD_HOMEPAGE + "\u0002";
//			strValueList += g_strHomePage + "\u0002";
			strValueList += g_strDeptHomepage + "\u0002";	// 부서 Homepage
		}
		if (Document_HWPProxy2.IsExistField(FIELD_TELEPHONE) == true)
		{
			strFieldList += FIELD_TELEPHONE + "\u0002";
//			strValueList += g_strTelephone + "\u0002";
			strValueList += g_strDeptTelephone + "\u0002";	// 부서 Telephone
		}
		if (Document_HWPProxy2.IsExistField(FIELD_FAX) == true)
		{
			strFieldList += FIELD_FAX + "\u0002";
//			strValueList += g_strFax + "\u0002";
			strValueList += g_strDeptFax + "\u0002";	// 부서 Fax Number
		}
		if (Document_HWPProxy2.IsExistField(FIELD_ZIPCODE) == true)
		{
			strFieldList += FIELD_ZIPCODE + "\u0002";
//			strValueList += g_strZipCode + "\u0002";
			strValueList += g_strDeptZipCode + "\u0002";	// 부서 ZipCode
		}
// 20030818 건보
		if (Document_HWPProxy2.IsExistField(FIELD_ADDRESS) == true)
		{
			strFieldList += FIELD_ADDRESS + "\u0002";
//			strValueList += g_strAddress + "\u0002";
			strValueList += g_strDeptAddress + " " + g_strDeptAddressDetail + "\u0002";	// 부서 Full Address
		}

		if (strFieldList.length > 0)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);
			Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
		}
	}
}

function updateDocumentPostSendField()
{
	// 문서번호, 시행일자
	var strFieldList = "";
	var strValueList = "";

	if (Document_HWPProxy2.IsExistField(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (Document_HWPProxy2.IsExistField(FIELD_ENFORCE_DATE) == true)
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
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
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
			var strFields = Document_HWPProxy2.GetFieldList("\u0002");
			var arrField = strFields.split("\u0002");

			var objDic = new Array();
			var nFieldCount = arrField.length;
			for (var i = 0; i < nFieldCount; i++)
				objDic[arrField[i]] = "";

			var strFieldName = "(";

			if (strRole == "6" && isExistKey(objDic, FIELD_REAL_NAME_ARBIT))
				strFieldName = FIELD_REAL_NAME_ARBIT;
			else if (strRole == "7" && isExistKey(objDic, FIELD_REAL_NAME_PROXY))
				strFieldName = FIELD_REAL_NAME_PROXY;
			else if ((strRole == "6" || strRole == "7") && isExistKey(objDic, FIELD_REAL_NAME))
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
				Document_HWPProxy2.PutFieldText(strFieldName, strValue);
			}
		}
	}
}
*/

/********************************************/
/* 기관간 공문서 유통, 행정정보 시스템 연계 */
/********************************************/

function saveBodyToXML(strBodyXMLPath)
{
	var bReturn = Document_HWPProxy2.SaveBodyAsXML(strBodyXMLPath);

	if (bReturn == true)
	{
		var strXhtml = readLocalFile(strBodyXMLPath);
		var nContentStartTagEnd = strXhtml.indexOf("<content") + 8;
		nContentStartTagEnd = strXhtml.indexOf(">", nContentStartTagEnd) + 1;
		var nContentEndTagStart = strXhtml.lastIndexOf("</content>");
		strXhtml = strXhtml.substring(nContentStartTagEnd, nContentEndTagStart);
		bReturn = saveToLocalFile(strBodyXMLPath, strXhtml);

		return bReturn;
	}
	else
		return false;
}

function readBodyFormXML(strBodyXMLPath)
{
	var strHmlPath = g_strDownloadPath + "pubdoc\\pubdoc.hml";
//	createPubDocFiles();
	var bReturn = Document_HWPProxy2.ImportFromXML(strBodyXMLPath, strHmlPath);
	if (bReturn == false)
		return false;

	var nEditMode = (g_bHwpDocumentModify ? 2 : 1);
	var nMenuSetID = 0;
	var nWindowPos = 0;

	Document_HWPProxy2.SetVisible(false);
	Document_HWPProxy2.InsertAux(strHmlPath, 2);

/*
	Document_HWPProxy2.CopyProposal(1);
	Document_HWPProxy2.OpenDocumentEx(g_strHwpDocumentTitle, g_strDownloadPath + g_strFormUrl, true, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
	Document_HWPProxy2.PasteProposal();
*/
	return true
}

function getHashTable()
{
	var strFields = Document_HWPProxy2.GetFieldList("\u0002");
	var arrField = strFields.split("\u0002");
	var strValues = Document_HWPProxy2.GetFieldText(strFields);
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
				"<!DOCTYPE pubdoc SYSTEM \"pubdoc_content.dtd\">\n" +
				"<pubdoc><body>" +
				strData +
				"</body></pubdoc>";
	return strData;
}

function putFieldText(strField, strValue)
{
	Document_HWPProxy2.PutFieldText(strField, strValue);
}

function setFieldsText(objDic)
{
	var bReturn = true;
	var strFieldList = Document_HWPProxy2.GetFieldList("\u0002");
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
		strValueList = strValueList.substring(0, strValueList.length - 1);
		Document_HWPProxy2.PutFieldText(strFieldList, strValueList);
	}

	return bReturn;
}

function setZipcode(strZipcode, strAddress)
{
	if (Document_HWPProxy2.IsExistField(FIELD_ZIPCODE) == true)
		Document_HWPProxy2.PutFieldText(FIELD_ZIPCODE, strZipcode);
	if (Document_HWPProxy2.IsExistField(FIELD_ADDRESS) == true)
		Document_HWPProxy2.PutFieldText(FIELD_ADDRESS, strAddress);
}

function onSetViewScale(bExpand)
{
}

/************************/
/* 한글97 관련 function */
/************************/
function changeHwpDocumentMenu()
{
	Document_HWPProxy2.ModifyCommandUI(0, "0", getDocumentMenuSet());
}

function setHwpDocumentVisible()
{
	var bVisible = (g_nFrameScale == 0 || g_nFrameScale == 2);
	Document_HWPProxy2.SetVisible(bVisible);
	if (bVisible = true)
		Document_HWPProxy2.Focus();
}

function moveHwpDocumentWindow()
{
	var nWindowPos;
	if (g_nFrameScale == 0 || g_nFrameScale == 1)
		nWindowPos = 0;
	else if (g_nFrameScale == 2)
		nWindowPos = 1;
	Document_HWPProxy2.MoveDocumentWindow(nWindowPos);
}

function onDocumentHwpManualSeal(strStampName)
{
//	saveBodyFile(g_nEnforceProposal)
//	setDocumentModified(true);
}

function onDocumentHwpProxyCommand(nCommandID)
{
// * EnableSession(<enable/disable/hourglass>#)
//	Document_HWPProxy2.EnableSession(hourglass);
	switch (nCommandID)
	{
	case 0:		// 사용못함 - 한글97 menu 버그, 0 번을 사용하면 메뉴가 생기지 않거나 오작동함.
		break;
	case 1:		// 승인취소
		onApproveCancel(1);
		break;
	case 2:		// 경로수정
		onSetApproveLine();
		break;
	case 3:		// 경로지정
		onSetApproveLine();
		break;
	case 4:		// 감사양식
		break;
	case 5:		// 공람게시
		onSetPublicViewInfo();
		break;
	case 6:		// 공람
		onAgreeApprove();
		break;
	case 7:		// 관인
		onClickStamp(1);
		break;
	case 8:		// 관인생략
		onClickStamp(3);
		break;
	case 9:		// 기안회수
		onApproveCancel(0);
		break;
	case 10:	// 문서회수
		onCallbackEnforce();
		break;
	case 11:	// 우편번호
		onOpenZipcodeInput();
		break;
	case 12:	// 담당지정
		onSetDealerInfo(3);
		break;
	case 13:	// 암호화
		onCertificateRecipient();
		break;
	case 14:	// 문서수정(결재문서)
		onModifyDocument();
		break;
	case 15:	// 결재보류
		onSuspendApprove(1);
		break;
	case 16:	// 문서정보
		onSetDocInfo();
		break;
	case 17:	// 원문보기
		break;
	case 18:	// 시행문서보기
		if (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13")
			onPreviewConvert();
		else
			onViewEnforceDoc();
		break;
	case 19:	// 반대
		onOpposeApprove();
		break;
	case 20:	// 반려
		if (g_strEditType == "1")
			onReverseApprove();
		else if (g_strEditType == "30" || g_strEditType == "31" || g_strEditType == "32" || g_strEditType == "33" || g_strEditType == "34" || g_strEditType == "35")
			onCircularReverseApprove();
		else if (g_strEditType == "9")
//			onDepartInspectReverseApprove();
			onReverseApprove();
		break;
	case 21:	// 반송
		if (g_strEditType == 0 || g_strEditType == 5)
		{
			if (getIsAdminMis() == "Y")
				onSendReturnNotiToAdminMis();
		}
		else if (g_strEditType == "18")
			onRejectDistribute();
		else if (g_strEditType == "20")
			onRejectReceive();
		else if (g_strEditType == "21")
			onRejectAfterReceive();
		break;
	case 22:	// 발송(기안자)
		if (g_bSendEnforceHold == true)
			return;
		g_bSendEnforceHold = true;
		onSendEnforce();
		break;
	case 23:	// 배부
		onSelectDelivery();
		break;
	case 24:	// 상세정보
		onViewDocDetail();
		break;
	case 25:	// 상신
		if (getIsAdminMis() == "Y")
			onSubmitApprove(false);
		else
			onSubmitApprove(g_strEditType == "0");
		break;
	case 26:	// (상신)보류
		onSuspendApprove(0);
		break;
	case 27:	// 서명 or 승인
		// 해경
		if (g_wndApproveAttach != null)
		{
			if (g_wndApproveAttach.closed == false)
			{
				window.focus();
				g_wndApproveAttach.focus();
				if (g_wndApproveAttach.onConfirm() == 0)
					return false;
			}
		}

		if (g_bAttachModifiedDoc == true)
		{
			if (confirm(CONFIRM_APPROVE_MODIFIED_DOCUMENT))
				onApplyModify();
			else
				return false;
		}

		onAgreeApprove();
		break;
	case 28:	// 서명생략
		onClickStamp(2);
		break;
	case 29:	// 서명인
		onClickStamp(0);
		break;
	case 30:	// 선람
		if (g_strEditType == "31")
			onSubmitApprove(false);
		else if (g_strEditType == "33")
			onAgreeApprove();
		break;
	case 31:	// 선람지정
		onSetDealerInfo(2);
		break;
	case 32:	// 수신지정
		onSetRecipPart();
		break;
	case 33:	// 수신수정
		onSetRecipPart();
		break;
	case 34:	// 문서분류
		onSetClassInfo();
		break;
	case 35:	// 시행변환
		onConvertEnforce();
		break;
	case 36:	// 심사반송
		onRejectExamination();
		break;
	case 37:	// 담당재지정

		break;
	case 38:	// 심사서명
		onAgreeExamination();
		break;
	case 39:	// 보내기
		onSelectRelatedSystem(1);
		break;
	case 40:	// 심사자지정
		onSetDealerInfo(1);
		break;
	case 41:	// 안추가
		onAddDocument();
		break;
	case 42:	// 안삭제
		onDeleteDocument();
		break;
	case 43:	// 요약전
		onEditSynopsis();
		break;
	case 44:	// 문서관리
		onSelectRelatedSystem(0);
		break;
	case 45:	// 의견작성
		onSetOpinion();
		break;
	case 46:	// 의견조회
		if (g_strEditType == "16")
			onViewExamOpinion();	// 심사자 의견조회
		else
			onViewOpinion();
		break;
	case 47:	// 이송
		if (g_strOption148 == "0")
			onSelectMovePart();
		else
		{
			if (g_strEditType == "18")
				onMoveDistribute();
			else
				onMoveEnforce();
		}
		break;
	case 48:	// 재발송
		onResendEnforce();
		break;
	case 49:	// 경유
		if (g_strEditType == "18")
			onPassDistribute();
		else
			onPassEnforce();
		break;
	case 50:	// 전대결표시
		break;
	case 51:	// 접수
		onReceiveEnforce();
		break;
	case 52:	// 지시사항
		onSetOpinion();
		break;
	case 53:	// 첨부
		onAttachFiles();
		break;
	case 54:	// 공개여부
		onEditPublicLevel();
		break;
	case 55:	// 결재문서보기
		onViewBodyDoc();
		break;
	case 56:	// 대장등록
		onRegistRejectApproval();
		break;
	case 57:	// 기록물철
		onEditArchiveInfo();
		break;
	case 58:	// 내려받기
		saveApproveDocument(false);
		break;
	case 59:	// 인쇄
		printDocument();
		break;
	case 60:	// 닫기
		onCloseHwp(true);
		break;
	case 61:	// 수정완료
		onApplyModify();
		break;
	case 62:	// 수정취소
		onCancelModify();
		break;
	case 63:	// 재기안
		if (g_strEditType == "5")
			onRetrySubmit();
		else if (g_strEditType == "13")
			onRetrySubmitRejectEnforce();
		break;
	case 64:	// 시행처리(시행작성시)
		if (g_strEditType == "10")
			onDirectEnforceApprove(true);
		else if (g_strEditType == "11" || g_strEditType == "13")
			onEnforceApprove();
		break;
	case 65:	// 열기
		onOpenDocument();
		break;
	case 66:	// 양식변경
		if (g_strEditType == "46")
			onChangeInspectForm();
		else
			onChangeForm();
		break;
	case 67:	// 시행범위변경
		onChangeDocInfo();
		break;
	case 68:	// 안건변경
		onChangeProposal();
		break;
	case 69:	// 결재정보
		onSetApproveInfo();
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
		Document_HWPProxy2.AdjustForm();
		break;
	case 75:	// 심사발송
		break;
	case 76:	// 재배부
		break;
	case 77:	// 이전문서
		onPrevApprove();
		break;
	case 78:	// 다음문서
		onNextApprove();
		break;
	case 79:	// 기안자지정
		onSetDealerInfo(0);
		break;
	case 80:	// 게시삭제
		onDeletePublicView();
		break;
	case 81:	// (게시)정보수정
		onModPublicViewInfo();
		break;
	case 82:	// 감사의견
		break;
	case 83:	// 게시로
		break;
	case 84:	// 필드수정
		onSetInspectEtcData();
		break;
	case 85:	// 첨부기안
		onChangeAttachSubmitForm();
		break;
	case 86:
		onChangeStampLocation();
		break;
	}
}

/// Hangul 97 dedicated common methods
var g_bHideParent = true;
var g_nSenderAsFontSize = 18;
var g_bSendEnforceHold = false;

function onHwpDocumentOpen()
{
	if (g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17")
	{
		createEnforceSide();

		setFrameScale(2);
		onLoadEnforceDocument();
	}
}

function setHwpWindowScale(nType)
{
	moveHwpDocumentWindow();
	moveHwpEnforceWindow();
	if (nType == 0)
	{
		setHwpDocumentVisible(true);
		setHwpEnforceVisible(false);
	}
	else if (nType == 1)
	{
		setHwpDocumentVisible(false);
		setHwpEnforceVisible(true);
	}
	else if (nType == 2)
	{
		setHwpDocumentVisible(true);
		setHwpEnforceVisible(true);
	}
}

function onCloseHwp(bBody)
{
	if (bBody != true)
		bBody = false;
	if (typeof(parent) == "object" && parent.location.href != window.location.href)
	{
		window.location.href = "about:blank";
//		onUnload();
	}
	else
	{
		if (!bBody)
//			Document_HWPProxy2.CloseDocument();
			Document_HWPProxy2.Disconnect();
		window.close();
	}
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

// icaha, document switching
onBodyAccessLoadingCompleted();

// coded by jiniabba
function setLinkedApprovalBody(strBody)
{
	var bRet = false;
	Document_HWPProxy2.MoveToBody(0);
	if (Document_HWPProxy2.IsExistField(FIELD_TITLE))
		Document_HWPProxy2.PutText(false, strBody);
}

function onChangeStampLocation()
{
	var strFileName = "";
	var nImageWidth = 0;
	var nImageHeight = 0;
	var strFilePath = "";

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
				strFileName = getAttachFileName(objRelatedAttach);
				nImageWidth = parseInt(getAttachImageWidth(objRelatedAttach));
				nImageHeight = parseInt(getAttachImageHeight(objRelatedAttach));
				strFilePath = g_strDownloadPath + strFileName;
				break;
			}
		}
		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}

	if (getLocalFileSize(strFilePath) > 0)
	{
		if (Document_HWPProxy2.IsExistField(FIELD_SENDER_AS + "#1"))
		{
			Document_HWPProxy2.AdjustSealPos(strFieldName, FIELD_SENDER_AS + "#1", g_nSenderAsFontSize);

			saveBodyFile();
			Document_HWPProxy2.MoveToField(strFieldName, true, true, true);
			onUpdateFile();
		}
	}
	else
	{
			alert(ALERT_CAN_NOT_MOVE_TEXT_SIGNATURE);
	}
}