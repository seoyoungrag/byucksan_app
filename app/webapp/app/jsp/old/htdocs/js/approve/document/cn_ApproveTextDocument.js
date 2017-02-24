var g_bIsModified = false;
var g_bIsFormData = false;

function onLoadDocument()
{
	alert('cn_ApproveTextDocument.js');
	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function closeDocument(){}

function onClose(){}

function loadDocument()
{
	downloadDefaultFile();

	if (g_strEditType == "0")
	{
		g_bIsModified = true;
	}
	else
	{
		g_bIsModified = false;
	}

	if (g_strDataUrl != "")
	{
		var strFileName = getAttachFileName(getBodyFileObj());

		if (strFileName == "")
		{
			hideWaitDlg();
			showAlert(1);
			return false;
		}

		var strLocalPath = g_strDownloadPath + strFileName;

		if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == true)
		{
			drawDocInfo();

			var strLocalPath = g_strDownloadPath + strFileName;
			if (getLocalFileSize(strLocalPath) <= 0)
			{
				hideWaitDlg();

				showAlert(2);
				return false;
			}

			var strBodyText = readLocalFile(strLocalPath);
			TextBody.value = strBodyText;
			if (g_bIsModified == false)
				TextBody.readOnly = true;
			else
				TextBody.readOnly = false;
		}
		else
		{
			hideWaitDlg();
			showAlert(2);
			return false;
		}
	}
	else
	{
		var objTextBox = document.getElementById("TextBody");
		objTextBox.value = "";
		objTextBox.readOnly = false;
	}

	onDocumentOpen();
	return true;
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

function setApprovalFlow(){}

function setApprovalSign(){}

function putFieldText(strApprovalField, strValue){}

function setRecordImageFromFile(strApprovalField, strSignUrl){}

function setSpecialSign(){}

function removeOfficialSeal(nType){}

function makeOfficialSeal(nType, strFileName)
{
	return 0;
}

function clearApprovalFlow(){}

function clearApprovalSign(){}

function clearSpecialSign(){}

function clearUserSign(strEmail){}

function setDocumentRecipientsToForm(){}

function addDocument(){}

function removeDocument(nIndex)
{
	return false;
}

function editDocument(bModify)
{
	if (bModify)
	{
		TextBody.readOnly = false;
	}
	else
	{
		TextBody.readOnly = true;
	}

	return true;
}

function OpenDocument(strFileUrl)
{
	var strBodyText = readLocalFile(strFileUrl);
	TextBody.value = strBodyText;

	if (g_strEditType == 0)
		TextBody.readOnly = false;
	else
		TextBody.readOnly = true;
}

function saveApproveDocument(strFilePath)
{
	var strBodyText = TextBody.value;
	if (saveToLocalFile(strFilePath, strBodyText) == false)
		return false;
}

function setFormDataToXml(){}

function setXmlDataToForm(){}

function getFileExtension()
{
	return "txt";
}

function saveBodyFile()
{
	var objBodyFile = getBodyFileObj();
	var strFileName = getAttachFileName(objBodyFile);

	var strBodyText = TextBody.value;
	var strFilePath = getDownloadPath() + strFileName;

	if (saveToLocalFile(strFilePath, strBodyText) == false)
		return false;
}

function getDocumentTitle(nIndex)
{
	var strTitle = "";
	var strField = FIELD_TITLE;

	if (nIndex > 1)
	 strField += nIndex;

	return strTitle;
}

function setDocumentTitle(nIndex, strTitle)
{
	var strField = FIELD_TITLE + nIndex;
}

function getFormInfo()
{
}

function clearApproveFlow(){}

function clearApprovalFlow(){}

function restoreApproveFlow(){}

function setPriorOpinion(strOpinion)
{

}

function getWord(strType)
{
	switch (strType)
	{
		case 1:
			strWord = FIELD_TITLE;
			break;
		case 2:
			strWord = SUBMIT_DATE;
			break;
		case 3:
			strWord = SUBMITER_NAME;
			break;
		case 5:
			strWord = LABEL_DEPT_NAME;
			break;
		case 6:
			strWord = LABEL_LEVEL;
			break;
		case 7:
			strWord = LABEL_APPROVER;
			break;
		case 8:
			strWord = LABEL_ROLE;
			break;
		case 9:
			strWord = LABEL_COMPLETE_DATE;
			break;
		case 10:
			strWord = LABEL_EXIST_OPINION;
			break;
		case 11:
			strWord = LABEL_DOC_MODIFIED;
			break;
		case 12:
			strWord = LABEL_FLOW_MODIFIED;
			break;
		case 13:
			strWord = LABEL_ATTACH_MODIFIED;
			break;
		case 14:
			strWord = ROLE_SELF;
			break;
		case 15:
			strWord = ROLE_DRAFT;
			break;
		case 16:
			strWord = ROLE_CONSIDER;
			break;
		case 17:
			strWord = ROLE_COOPERATE;
			break;
		case 18:
			strWord = ROLE_LAST;
			break;
		case 19:
			strWord = ROLE_ARBITRARY;
			break;
		case 20:
			strWord = ROLE_PROXY;
			break;
		case 21:
			strWord = ROLE_MANDATOR;
			break;
		case 22:
			strWord = ROLE_POST;
			break;
		case 23:
			strWord = ROLE_NOT;
			break;
		case 24:
			strWord = ROLE_REAR;
			break;
		case 25:
			strWord = ROLE_PRIOR;
			break;
		case 26:
			strWord = ROLE_CHARGER;
			break;
		case 27:
			strWord = ROLE_PUBLIC;
			break;
		case 28:
			strWord = ROLE_SINGLE_SERIAL;
			break;
		case 29:
			strWord = ROLE_SINGLE_PARALLEL;
			break;
		case 30:
			strWord = ROLE_GROUP_SERIAL;
			break;
		case 31:
			strWord = ROLE_GROUP_PARALLEL;
			break;
		case 32:
			strWord = ROLE_MANAGE_GROUP;
			break;
		case 33:
			strWord = ROLE_REPORT;
			break;
		case 34:
			strWord = ROLE_READ;
			break;

		case 41:
			strWord = STATUS_SUBMIT;
			break;
		case 42:
			strWord = STATUS_PROCESS;
			break;
		case 43:
			strWord = STATUS_SUSPEND;
			break;
		case 44:
			strWord = STATUS_REJECT;
			break;
		case 45:
			strWord = STATUS_OPPOSE;
			break;
		case 46:
			strWord = STATUS_CANCEL;
			break;
		case 47:
			strWord = STATUS_COMPLETE;
			break;
		case 48:
			strWord = STATUS_PREENFORCE;
			break;
		case 49:
			strWord = STATUS_WAIT_SEND;
			break;
		case 50:
			strWord = STATUS_WAIT_INVESTIGATION;
			break;

		default:
			strWord = "";
			break;

	}

	return strWord;
}

function setApprovalField(nSetType, bSetData, strUserID){}

function openFile()
{
	var strTargetFile = openFileDialog("");
	if (strTargetFile != "")
	{
		var strTextBody = readLocalFile(strTargetFile);
		TextBody.value = strTextBody;
	}
}

function saveApproveDocument(bIncludeSign)
{
	var strTargetFile = saveFileDialog(getTitle(1), "Text Files (*.txt)|*.txt||");
	var strFilePath = getDownloadPath();
	if (strTargetFile != "")
	{
		var nPos = strTargetFile.lastIndexOf("\\");
		var strFile = strTargetFile.substring(nPos+1, strTargetFile.length);
		var nPos = strFile.lastIndexOf(".");
		if (nPos != -1)
		{
			var strExt = strFile.substring(nPos+1, strFile.length);
			var strExt = strExt.toUpperCase();
			if (strExt != "TXT")
				strTargetFile = strTargetFile + ".txt";
		}
		else
			strTargetFile = strTargetFile + ".txt";

		var strBodyText = TextBody.value;
		saveToLocalFile(strTargetFile, strBodyText);
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
	return g_bIsFormData;
}

function copyProposal(nProposal){}

function displayArbitProxy(objManInfo)
{
	var strFieldName;
	var strRole = objManInfo.selectSingleNode("var_man_role").text;

	return;

	var strValue = "";
	if (strRole == "6")
	{
		strValue += FIELD_ARBITRARY;
	}
	else if (strRole == "7")
	{
		strValue += FIELD_PROXY;
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

	saveDocument();
}

function showAlert(nIndex)
{
	switch (nIndex)
	{
		case 1:
			alert(ALERT_FIND_DOCUMENT_ERROR);
			break;
		default:
			break;
	}
}

function showWaitDlg(nIndex)
{
	switch (nIndex)
	{
		case 1:
			showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
			break;
		default:
			break;
	}
}

function drawDocInfo()
{
	var str = "<TABLE width='100%' id='DocInfoTable' border='0' cellpadding='0' cellspacing='1' bgcolor='ffffff'>";
	str += "<TR height='20'><TD width='18%' class='tb_tit_center'>" + FIELD_TITLE + "</TD>";
	str += "<TD class='tb_left' id='DocTitle'></TD></TR>";
	str += "<TR height='20'><TD class='tb_tit_center'>" + SUBMITER_NAME + "</TD><TD class='tb_left' id='Submiter'></TD></TR>";
	str += "<TR height='20'><TD class='tb_tit_center'>" + EXTRA_INFO + "</TD><TD class='tb_left' id='DocStatus'></TR>";
	str += "</TABLE>";

	DocInfoSpan.innerHTML = str;

	setDocInfo();
}

function setDocInfo()
{
	var strTitle = getTitle(1);
	var strSubmiter = getSubmiterName();
	var strStatus = getApprovalStatus();

	if (strTitle != "")
	{
		DocInfoSpan.style.display = "block";
		var DocTitle = document.getElementById("DocTitle");
		DocTitle.innerText = strTitle;
	}

	if (strSubmiter != "")
	{
		var Submiter = document.getElementById("Submiter");
		Submiter.innerText = strSubmiter;
	}

	if (strStatus != "")
	{
		var DocStatus = document.getElementById("DocStatus");
		DocStatus.innerText = strStatus;
	}
}

function printDocument()
{
	var strDocumentTitle = getTitle(1);
//	var strOption9 = document.XMLDocument.documentElement.getAttribute("option9");
	var strOption9 = "";

	var strTitle = getWord(1);
	var strDraftDate = getWord(2);
	var strDrafter = getWord(3);
	var strPrinter = getWord(4);
	var strDeptName = getWord(5);
	var strDeptLevel = getWord(6);
	var strApprover = getWord(7);
	var strRole = getWord(8);
	var strCompleteDate = getWord(9);
	var strExistOpinion = getWord(10);
	var strDocModified  = getWord(11);
	var strFlowModified = getWord(12);
	var strAttachModified = getWord(13);

	var strHtmlBody = "";
	strHtmlBody = strHtmlBody + "<BODY onload='javascript:onLoadFunc();return(false);'>";

	strHtmlBody = strHtmlBody + "<P />" + strTitle + " : " + "<B><U>" + strDocumentTitle + "</U></B><P /><BR />";

	strHtmlBody = strHtmlBody + "<TABLE border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse; border:none;mso-border-alt:solid windowtext .5pt;mso-padding-alt:0cm 4.95pt 0cm 4.95pt'>";

	strHtmlBody = strHtmlBody + "<TR>";

	strHtmlBody = strHtmlBody + "<TD align='center' width='100' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strDeptName + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strDeptLevel + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strApprover + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strRole + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='150' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strCompleteDate + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strExistOpinion + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strDocModified + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strFlowModified + "</B></TD>";
	strHtmlBody = strHtmlBody + "<TD align='center' width='70' style='font-family:" + FONT_FAMILY + "; font-size:9pt'><B>" + strAttachModified + "</B></TD>";
	strHtmlBody = strHtmlBody + "</TR>";

	var objApprovalLine = getActiveApprovalLine();

	if (objApprovalLine != null)
	{
		var strLineName = getLineName(objApprovalLine);

		var objApprovalLine = getApprovalLineByLineName(strLineName);
		var objApprover = getFirstApprover(objApprovalLine);

		while(objApprover != null)
		{
			strHtmlBody = strHtmlBody + "<TR style='border-bottom:none;'>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + getApproverDeptName(objApprover) + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + getApproverUserLevel(objApprover) + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + getApproverUserName(objApprover) + "</TD>";

			var strVarmanRole = getApproverRole(objApprover);
			var strVarRole = "";
			switch (strVarmanRole)
			{
				case "0" :
					strVarRole = getWord(14);
					break;
				case "1" :
					strVarRole = getWord(15);
					break;
				case "2" :
					strVarRole = getWord(16);
					break;
				case "3" :
					strVarRole = getWord(17);
					break;
				case "5" :
					strVarRole = getWord(18);
					break;
				case "6" :
					strVarRole = getWord(19);
					break;
				case "7" :
					strVarRole = getWord(20);
					break;
				case "8" :
				{
					if (strOption9 == 0)
						strVarRole = getWord(21);
					else
						strVarRole = getWord(23);

					break;
				}

				case "9" :
				{
					if (strOption9 == 0)
						strVarRole = getWord(22);
					else
						strVarRole = getWord(24);

					break;
				}

				case "10" :
					strVarRole = getWord(25);
					break;
				case "11" :
					strVarRole = getWord(26);
					break;
				case "12" :
					strVarRole = getWord(27);
					break;
				case "13" :
					strVarRole = getWord(28);
					break;
				case "14" :
					strVarRole = getWord(29);
					break;
				case "15" :
					strVarRole = getWord(30);
					break;
				case "16" :
					strVarRole = getWord(31);
					break;
				case "17" :
					strVarRole = getWord(32);
					break;
				case "18" :
					strVarRole = getWord(33);
					break;
				case "19" :
					strVarRole = getWord(34);
					break;
				default:
					strVarRole = "";
					break;
			}

			var strVarmanStatus = getApproverKeepStatus(objApprover);
			var strStatus;

			switch (strVarmanStatus)
			{
				case "3":
					strStatus = getWord(43);
					break;
				case "4":
					strStatus = getWord(44);
					break;
				case "5":
					strStatus = getWord(45);
					break;
				case "6":
					strStatus = getWord(46);
					break;
				default:
					strStatus = "";
					break;
			}

			if (strStatus != "")
				strStatus = "(" + strStatus + ")";

			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + strVarRole + strStatus + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + getApproverSignDate(objApprover) + "</TD>";

			var strExistOpinion = getApproverOpinion(objApprover);
			if (strExistOpinion != "")
				strExistOpinion = "O";

			var strModifiedDoc = getApproverIsDocModified(objApprover);
			if (strModifiedDoc == "Y")
				strModifiedDoc = "O";

			var strModifiedLine = getApproverIsLineModified(objApprover);
			if (strModifiedLine == "Y")
				strModifiedLine = "O";

			var strModifiedAtt = getApproverIsAttachModified(objApprover);
			if (strModifiedAtt == "Y")
				strModifiedAtt = "O";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + strExistOpinion + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + strModifiedDoc + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + strModifiedLine + "</TD>";
			strHtmlBody = strHtmlBody + "<TD align='center' style='font-family:" + FONT_FAMILY + "; font-size:9pt'>" + strModifiedAtt + "</TD>";
			strHtmlBody = strHtmlBody + "</TR>";

			objApprover = getNextApprover(objApprover);
		}
	}

	strHtmlBody = strHtmlBody + "</TABLE><BR/><HR width='100%' align='left'/>";

	var strHead = "<HTML><HEAD><STYLE>P {margin-top:2px;margin-bottom:2px;}</STYLE><SCRIPT LANGUAGE='JAVASCRIPT'>function onLoadFunc(){window.print();}</SCRIPT></HEAD>";
	var strTail = "</BODY></HTML>";

	var strInnerText = TextBody.value;

	while (strInnerText.indexOf("\r\n") != -1)
	{
		strInnerText = strInnerText.replace("\r\n", "<BR>");
	}

	var strHTML = strHead + strHtmlBody + strInnerText + strTail;
	var strOpenFileName = g_strDownloadPath + "Print.htm";

	createLocalFile(strOpenFileName);
	saveToLocalFile(strOpenFileName, strHTML);

	var strServerUrl = g_strUploadUrl + "Print.htm";
	if (uploadFile(strOpenFileName, strServerUrl) == 0)
		return false;

	var strPrintFileName = g_strHttpUploadUrl + "Print.htm";

	window.open(strPrintFileName,"Body_Print","toolbar=no,resizable=yes, status=yes, width=790,height=525, top=0,left=0");
}

function setExaminationSign(){}
function clearExaminationSign(){}
function isEnforceModified()
{
	return false;
}
function setSenderAsToForm(strChiefGrade)
{
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

// icaha, document switching
onBodyAccessLoadingCompleted();
