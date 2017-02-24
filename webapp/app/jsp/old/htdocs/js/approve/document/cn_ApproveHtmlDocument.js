var g_bIsFormData = false;
var g_bIsModified = false;
var g_nLoadStep = 0;
var g_bEnv4Edit = false;

function onLoadDocument()
{
	alert('cn_ApproveHtmlDocument.js 758  16. 1. 21 오후 5:12  jyd');
	if(g_strEditType == "0" || g_bEnv4Edit == true)
	{
		g_bEnv4Edit = true;
		loadDHTMLEditor();
		g_bIsModified = true;
		g_nLoadStep = 1;
	}
	else
	{
		loadDocument();
	}
}

function onUnloadDocument()
{
	closeDocument();
}

function closeDocument(){}

function onClose(){}

function documentComplete()
{
	if (g_nLoadStep == 1)
	{
		g_nLoadStep = 0;
		var str = "<HTML><HEAD><TITLE></TITLE><STYLE>P {margin-top:2px;margin-bottom:2px;}</STYLE></HEAD><BODY></BODY></HTML>";
		editFrame.tbContentElement.DOM.writeln(str);

		loadDocument();
	}
}

function loadDocument()
{
	g_nLoadStep = 0;
	downloadDefaultFile();

	if (g_strDataUrl != "")
	{
		var strFileName = getAttachFileName(getBodyFileObj());

		if (strFileName == "")
		{
			hideWaitDlg();
			alert(ALERT_FIND_DOCUMENT_ERROR);
			return false;
		}

		if(g_bEnv4Edit)
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			editFrame.tbContentElement.LoadDocument(strLocalPath, false);
		}
		else
		{
			drawDocInfo();

			editFrame.location.href = g_strHttpUploadUrl + strFileName;
//			editFrame.location.href = strLocalPath;
		}
	}
	else
	{
		if (g_strFormUrl != "")
		{
			g_bIsFormData = true;

			if(g_bEnv4Edit)
			{
				var strLocalPath = g_strDownloadPath + g_strFormUrl;
				editFrame.tbContentElement.LoadDocument(strLocalPath, false);
			}
			else
			{
				drawDocInfo();

				editFrame.location.href = g_strHttpUploadUrl + g_strFormUrl;
			}
		}
		else
		{
		}

		setXmlDataToForm();
	}

	onDocumentOpen();
	return true;
}

function prepareDocument(){}

function prepareSendApprove(){}

function uploadBodyFile()
{
//alert("uploadBodyFile");
	var strFileName = getAttachFileName(getBodyFileObj());
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
}

function setApprovalFlow()
{
//	setApprovalField(0, true, "");
}

function setApprovalSign()
{
//	setApprovalField(1, true, "");
}

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
		DocInfoSpan.style.display = "none";
		g_bEnv4Edit = true;
		onLoadDocument();
	}
	else
	{
		DocInfoSpan.style.display = "block";
		g_bEnv4Edit = false;

		var strFileName = getAttachFileName(getBodyFileObj());
		var strLocalPath = g_strDownloadPath + strFileName;

		editFrame.location.href = g_strHttpUploadUrl + strFileName;
//		editFrame.location.href = strLocalPath;
	}
	onResize();
	return true;
}

function editEnforceDocument(bModify)
{
	editDocument(bModify);
}

function applyModifiedDoc(){}

function saveApproveDocument(bIncludeSign)
{
	var strTitle = getTitle(1);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}
	else
		strTitle += ".htm";

	if(g_bEnv4Edit)
	{
		editFrame.tbContentElement.SaveDocument(strTitle, true);
		editFrame.tbContentElement.focus();
	}
	else
	{
		var strLocalPath = g_strDownloadPath + getAttachFileName(getBodyFileObj());
		var strDownloadPath = saveFileDialog(strTitle, "Html Files (*.htm)|*.htm|");

		copyLocalFile(strLocalPath, strDownloadPath);
	}
}

function saveApproveDocumentAs(strFilePath, bIncludeSign)
{
	var strTitle = getTitle(1);

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}
	else
		strTitle += ".htm";

	if(g_bEnv4Edit)
	{
		editFrame.tbContentElement.SaveDocument(strTitle, true);
		editFrame.tbContentElement.focus();
	}
	else
	{
		// couldn't get iframe 'editFrame' (IE bug maybe....), so get the bodypart from body file.

		var strLocalPath = g_strDownloadPath + getAttachFileName(getBodyFileObj());
//		var strDownloadPath = saveFileDialog(strTitle, "Html Files (*.htm)|*.htm|")

//		copyLocalFile(strLocalPath, strDownloadPath);

		if (strLocalPath != strFilePath)
			copyLocalFile(strLocalPath, strFilePath);
	}
}

function setFormDataToXml(){}

function setXmlDataToForm(){}

function getFormInfo(){ return ""; }

function clearApproveFlow()
{
//	clearApprovalFlow();
}

function clearApprovalFlow()
{
//	setApprovalField(0, false, "");
}


function restoreApproveFlow(){}

function setPriorOpinion(strOpinion){}

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

	strHtmlBody = strHtmlBody + "</TABLE><BR /><HR width='100%' align='left' />";

	var strHead = "<HTML><HEAD><STYLE>P {margin-top:2px;margin-bottom:2px;}</STYLE><SCRIPT LANGUAGE='JAVASCRIPT'>function onLoadFunc(){window.print();}</SCRIPT></HEAD>";
	var strTail = "</BODY></HTML>";

	if(g_bEnv4Edit)
	{
		var strInnerText = editFrame.tbContentElement.DOM.body.innerHTML;
	}
	else
	{
		// couldn't get iframe 'editFrame'(IE bug maybe....), so get the bodypart from body file.

		var strInnerText = readLocalFile(g_strDownloadPath + getAttachFileName(getBodyFileObj()));
	}

	var strHTML = strHead + strHtmlBody + strInnerText + strTail;
	var strOpenFileName = g_strDownloadPath + "Print.htm";

	createLocalFile(strOpenFileName);
	saveToLocalFile(strOpenFileName, strHTML);

	var strServerUrl = g_strUploadUrl + "Print.htm";
	if (uploadFile(strOpenFileName, strServerUrl) == 0)
		return false;

	var strPrintFileName = g_strHttpUploadUrl + "Print.htm";

	window.open(strPrintFileName,"Body_Print","toolbar=no, resizable=yes, scrollbars=yes, status=yes, width=790, height=525, top=0,left=0");
}

function openFile()
{
	editFrame.tbContentElement.LoadDocument("", true);
	editFrame.tbContentElement.focus();
}

function isModified()
{
	return g_bIsModified;
}

function setModified(bModified)
{
	g_bIsModified = bModified
}

function isFormData()
{
	return g_bIsFormData;
}

function saveHTMLToFile(strHTMLData, strSubject)
{
	var strOpenFileName = getDownloadPath() + strSubject;

	if (createLocalFile(strOpenFileName) == false)
		return false;

	var strInline = convImgSrc(strHTMLData);
	strInline = "<HTML><HEAD><STYLE>P {margin-top:2px;margin-bottom:2px;}</STYLE></HEAD><BODY>" + strInline + "</BODY></HTML>";

	if (saveToLocalFile(strOpenFileName, strInline) == false)
		return false;

	return true;
}

function convImgSrc(strHTMLData)
{
	var strConvHTMLData = "";
	var bFlag = true;
	var strHead, strImgTag, strTail;
	var nFind, nEndFind;

	strTail = strHTMLData;
	while (bFlag != false)
	{
		nFind = strTail.indexOf("<IMG", 0);

		if (nFind != -1)
		{
			nEndFind = strTail.indexOf(">", nFind+1);
			strHead = strTail.substring(0, nFind);
			strImgTag = strTail.substring(nFind, nEndFind+1);
			strTail = strTail.substring(nEndFind+1, strTail.length);

			strConvHTMLData = strConvHTMLData + strHead + replaceImgSrc(strImgTag);
		}
		else
		{
			strConvHTMLData = strConvHTMLData + strTail;
			bFlag = false;
		}
	}

	return strConvHTMLData;
}

function replaceImgSrc(strImgTag)
{
	var strReplaceImgTag = "";
	var nFind = strImgTag.indexOf("src=\"", 0);
	var nGifFind = strImgTag.indexOf(".", 0);
	if (nFind == -1 || nGifFind == -1)
		return strImgTag;

	var strFirst = strImgTag.substring(0, nFind+5);
	var strCenter = strImgTag.substring(nFind+5, nGifFind);
	var strLast = strImgTag.substring(nGifFind, strImgTag.length);

	if (strCenter.substring(0, 7).toUpperCase() == "HTTP://")
		return strImgTag;
	else if (strCenter.substring(0, 7).toUpperCase() == "FILE://")
		var chDeli = "/";
	else
		var chDeli = "\\";

	var nExtFind = strLast.indexOf("\"", 0);
	var strImgFile = strCenter + strLast.substring(0, nExtFind);

	var strUploadFileName = uploadImgFile(strImgFile, chDeli);
	var strNetImgFileName = strImgFile;

	nFind = strImgFile.lastIndexOf("\\");
	if (nFind != -1)
		strNetImgFileName = strImgFile.substring(nFind + 1);

	var strFileSize = getLocalFileSize(strImgFile);
	var strLocation = "";

//	addRelatedAttachInfo(strNetImgFileName, strNetImgFileName, strFileSize, strLocation);
	addRelatedAttachInfo(strNetImgFileName, strUploadFileName, strFileSize, strLocation);

//	var arrCenter = strCenter.split(chDeli);
//		strCenter = "" + arrCenter[arrCenter.length-1];

	if (strUploadFileName.indexOf(".") != -1)
	{
		var arrCenter = strUploadFileName.split(".");
		strCenter = arrCenter[0];
	}
	else
		strCenter = strUploadFileName;

	strReplaceImgTag = strFirst + strCenter + strLast;
	return strReplaceImgTag;
}

function uploadImgFile(strFilePath, chDeli)
{
	var arrFilePath = strFilePath.split(chDeli);
//	var strServerUrl = g_strUploadUrl + arrFilePath[arrFilePath.length-1];

	var strUploadFileName = arrFilePath[arrFilePath.length-1];
	var nFind = strUploadFileName.indexOf(".");
	if (nFind != -1)
		strUploadFileName = getGUID() + strUploadFileName.substring(nFind, strUploadFileName.length);
	else
		strUploadFileName = getGUID();

	var strServerUrl = g_strUploadUrl + strUploadFileName;

	if (g_bEnv4Edit)
	{
		// src에 파일명만 있는 경우 경로 보정.
		if(strFilePath.indexOf(":\\") < 1)
			strFilePath = g_strDownloadPath + strFilePath;

		// 새로 추가된 그림파일을 g_strDownloadPath에 복사해야 수정완료 후 보임.
		var nFind = strFilePath.lastIndexOf("\\");
		var strCmp = strFilePath.substr(0,nFind + 1);

		if (strCmp != g_strDownloadPath)
		{
			var arrFileName = strFilePath.split("\\");
			var strFileName = arrFileName[arrFileName.length - 1];
			copyLocalFile(strFilePath, g_strDownloadPath + strFileName);
		}
	}

	if (uploadFile(strFilePath, strServerUrl))
	{
		clearFileInfo();
	}
	else
	{
		alert(ALERT_FILE_SEND_ERROR);
//		return;
	}

	return strUploadFileName;
}

// nSetType : 0=setApprovalFlow, 1=setApprovalSign, 2=setUserSign
// bSetData : true=set, false=clear
// strUserID : break on match
function setApprovalField(nSetType, bSetData, strUserID)
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return;

	var nApprove = 0;
	var nAssist = 0;

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
		else if (strRole == "22")
		{
			nApprove++;
			strApprovalField = FIELD_PUBLIC + nApprove;
		}
		else if (strRole == "31")
		{
			strApprovalField = FIELD_DEPART_INSPECT + "1";
		}

		if (nSetType == 0)
		{

		}
		else if (nSetType == 1 || nSetType == 2)
		{
		}

		nSerialOrder++;
		objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
	}
}

function getFileExtension()
{
	return "html";
}

function saveBodyFile()
{
	var wordDocument = editFrame.tbContentElement.DOM;

	var objBodyFile = getBodyFileObj();
	var strFileName = getAttachFileName(objBodyFile);

	saveHTMLToFile(wordDocument.body.innerHTML, strFileName);
}

function loadDHTMLEditor()
{
	editFrame.location.href = g_strBaseUrl + "dhtmleditor/approve/CN_ApproveDHtmlEditor.jsp";
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

function appendDocumentRecipientsToForm(nCaseNumber)
{
}

// icaha, document switching
onBodyAccessLoadingCompleted();
