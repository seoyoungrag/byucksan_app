var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";
var g_bIsEnforceModified = false;
var g_nMenuType = 0;

var g_EnforceDoc = null;
var g_nCase = 0;
var g_wordApp = null
var g_CaseNumber = 0;

function getEnforceObjectHTML()
{
	var strObjectHTML = "<SCRIPT language=\"javascript\" for=\"enforceObject\" EVENT=\"NavigateComplete2(objDisp, strUrl)\">";
	strObjectHTML += "return enforceObject_NavigateComplete2(objDisp, strUrl)";
	strObjectHTML += "</SCRIPT>";

	var strHTML = "<OBJECT id='enforceObject' width='100%' height='100%' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'>";
	strHTML += "<param name='_Version' value='65536'/>";
	strHTML += "<param name='_ExtentX' value='2646'/>";
	strHTML += "<param name='_ExtentY' value='1323'/>";
	strHTML += "<param name='_StockProps' value='0'/>";
	strHTML += "</OBJECT>";

	return (strObjectHTML + strHTML);
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
//	closeEnforceDocument();
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

	if (g_strEditType == "0")
		g_bIsModified = true;

	else
		g_bIsModified = false;

	var strFileName = getAttachFileName(objEnforceFile);

	if (strFileName == "")
		return;

	else if (strFileName != "" && g_nCase != 1)
	{
		var strLocalPath = g_strDownloadPath + strFileName;
		g_nCase = 2;

		if (g_EnforceDoc != null)
		{
			var strEnforceDoc = g_EnforceDoc.Name;
			if (strEnforceDoc.indexOf(".doc") == -1)
				strEnforceDoc += "doc";

			if (strFileName == g_EnforceDoc.Name)
				g_EnforceDoc.Save();
		}
		openEnforceDocument(strLocalPath);
	}

// 20030314
	if (g_nCase == 1)
		restoreEnforceApproveFlow();
}

function openEnforceDocument(strFormUrl)
{
//이상한 코드...
	enforceObject.Navigate(strFormUrl);
}

function enforceObject_NavigateComplete2(objDisp, strUrl)
{
	// objDisp : Application object, strUrl : Application 객체가 Open한 URL

	g_EnforceDoc = enforceObject.Document;
//		g_AppWord = g_EnforceDoc.Application;


	if (g_nCase == 1)
	{
		var nBookmark = g_DocWord.Bookmarks.Count;
		var nLoop = 1;
		var strBookmarkList = "";
		while(nLoop < nBookmark +1)
		{
			strBookmarkList += g_DocWord.Bookmarks.Item(nLoop).Name + "^";
			nLoop++;
		}

		if (isFormData() == true)
		{
			if (g_EnforceDoc.Bookmarks.Exists(FIELD_BODY))
			{
				if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
					g_DocWord.UnProtect();

//				g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Paragraphs(1).Range.Copy();
//				var strBodyText = g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Paragraphs(1).Range.Copy(); //Copy();

//				g_AppWord.Selection.Copy();
//g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Tables(1).Select();

				var nTable = g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Tables.Count;

				if (nTable == 0)
				{
					g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Paragraphs(1).Range.Copy();

				}
				else
					g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Cells(1).Range.Copy(); // 본문부분이 cell일때(본문을 표로 가져가는 이유)

				g_EnforceDoc.Bookmarks(FIELD_BODY).Range.Cells(1).Range.Paste();
			}
		}

		else if (isFormData() == false)
		{
			if (g_EnforceDoc.Bookmarks.Exists(FIELD_BODY))
			{
				g_DocWord.Sections(2).Range.Copy();
				g_EnforceDoc.Bookmarks(FIELD_BODY).Range.Cells(1).Range.Paste();
			}
		}

		var arrBookmarkName = strBookmarkList.split("^");
		if (arrBookmarkName.length != 0)
		{
			for (var i = 0 ; i < arrBookmarkName.length ; i++)
			{
				if (g_EnforceDoc.Bookmarks.Exists(arrBookmarkName[i]))
				{
					var strData = readTempRecordText(arrBookmarkName[i]);
					setRecordText(arrBookmarkName[i], strData,g_EnforceDoc);
				}
			}

			if (g_CaseNumber > 1)
			{
				var strField = FIELD_TITLE;
				if (g_EnforceDoc.Bookmarks.Exists(strField))
				{
					var strData = readTempRecordText(strField + g_CaseNumber);
					setRecordText(strField, strData, g_EnforceDoc);
				}

				strField = FIELD_RECIPIENT;
				if (g_EnforceDoc.Bookmarks.Exists(strField))
				{
					var strData = readTempRecordText(strField + g_CaseNumber);
					setRecordText(strField, strData, g_EnforceDoc);
				}

				strField = FIELD_REFERER;
				if (g_EnforceDoc.Bookmarks.Exists(strField))
				{
					var strData = readTempRecordText(strField + g_CaseNumber);
					setRecordText(strField, strData, g_EnforceDoc);
				}

				strField = FIELD_SENDER_AS;

				if (g_EnforceDoc.Bookmarks.Exists(strField))
				{
					var strData = readTempRecordText(strField + g_CaseNumber);
					setRecordText(strField, strData, g_EnforceDoc);
				}

				strField = FIELD_RECIPIENT_REFER;
				if (g_EnforceDoc.Bookmarks.Exists(strField))
				{
					var strData = readTempRecordText(strField + g_nProposal);
					setRecordText(strField, strData, g_EnforceDoc);
				}
			}
		}
		saveEnforceFile(g_nEnforceProposal);
		g_EnforceDoc.ActiveWindow.View.Zoom.Percentage = 50;
		g_DocWord.ActiveWindow.View.Zoom.Percentage = 50;


		if (g_EnforceDoc.ProtectionType == -1)
			g_EnforceDoc.Protect(1);

		if (g_DocWord.ProtectionType == -1)
			g_DocWord.Protect(1);

		setEnforceXmlDataToForm();
		restoreEnforceApproveFlow();
	}

	else if (g_nCase == 2)
	{
		g_EnforceDoc.ActiveWindow.View.Zoom.Percentage = 50;
		g_DocWord.ActiveWindow.View.Zoom.Percentage = 50;

		if (g_EnforceDoc.ProtectionType == -1)
			g_EnforceDoc.Protect(1);

		if (g_DocWord.ProtectionType == -1)
			g_DocWord.Protect(1);

		setEnforceXmlDataToForm();
	}

	else if (g_nCase == 3)
	{
		if (g_bModify == true)
		{
			g_EnforceDoc.ActiveWindow.View.Zoom.Percentage = 100;
			drawMenu(1);
		}
		else
			g_EnforceDoc.ActiveWindow.View.Zoom.Percentage = 50;
	}
}

function setEnforceXmlDataToForm()
{
	var strFieldList = "", strValueList = "";

	var nBodyCount = getBodyCount();

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_TITLE) == true)
	{
		strFieldList += FIELD_TITLE + "\u0002";
		strValueList += getTitle(g_nEnforceProposal) + "\u0002";
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = getDraftConserve();
		strFieldList += FIELD_CONSERVE_TERM + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_RECP_CONSERVE) == true)
	{
		var strConserveTerm = getEnforceConserve();
		strFieldList += FIELD_RECP_CONSERVE + "\u0002";
		strValueList += strConserveTerm + "\u0002";
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = getPublicLevel();
		strFieldList += FIELD_PUBLIC_BOUND + "\u0002";
		strValueList += strPublicBound + "\u0002";
	}
/*
	if (g_EnforceDoc.Bookmarks.Exists(FIELD_ENFORCE_DATE) == true)
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
*/
	if (g_EnforceDoc.Bookmarks.Exists(FIELD_RECEIVE_DATE) == true)
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

		if (g_EnforceDoc.Bookmarks.Exists(FIELD_RECEIVE_TIME) == true)
		{
			strFieldList += FIELD_RECEIVE_TIME + "\u0002";
			strValueList += strTime + "\u0002";
		}
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "0")
		{
			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strReceiveNumber + "\u0002";
		}
	}

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		putFieldData(strFieldList, strValueList);
	}

	setEnforceRecipientsToForm();
}

function makeEnforceDocument(strEnforceFormUrl, nCaseNumber)
{
	if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

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
			g_nCase = 1;
			g_CaseNumber = nCaseNumber;
			var bRet = openEnforceDocument(g_strDownloadPath + strEnforceFormUrl);
//			setXmlDataToForm();
		}

		objEnforceFile = setEnforceFileInfo(nCaseNumber);
		setEnforceModified(true);
	}

	return objEnforceFile;
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
		strDeptRefField = FIELD_RECIPIENT_REFER + "_P1";

		if (g_EnforceDoc.Bookmarks.Exists(strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			if (nRecipientCount == 0)
				strValueList += strDisplayAs + "\u0002";
			else if (nRecipientCount == 1)
				strValueList += strDeptName + "\u0002";
			else
				strValueList += RECIPIENT_REFER + "\u0002";
		}

		if (g_EnforceDoc.Bookmarks.Exists(strRefField))
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

		if (g_EnforceDoc.Bookmarks.Exists(strDeptRefField))
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
		if (g_EnforceDoc.Bookmarks.Exists(strDeptField))
		{
			strFieldList += strDeptField + "\u0002";
			var strDocCategory = getDocCategory(nCaseNumber);
			if (strDocCategory == "I")
				strValueList += INNER_APPROVAL + "\u0002";
			else
				strValueList += "\u0002";
		}

		if (g_EnforceDoc.Bookmarks.Exists(strRefField))
		{
			strFieldList += strRefField + "\u0002";
			strValueList += "\u0002";
		}

		if (g_EnforceDoc.Bookmarks.Exists(strDeptRefField))
		{
			strFieldList += strDeptRefField + "\u0002";
			strValueList += "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		putFieldData(strFieldList, strValueList);
	}

	saveEnforceFile(g_nEnforceProposal);
	setEnforceModified(true);

}

function putFieldData(strFieldList, strValueList)
{
	var nLoop = 0;
	var arrFieldList = strFieldList.split("\u0002");
	var arrValueList = strValueList.split("\u0002");
	var nLength = arrFieldList.length;

	if (g_EnforceDoc.ProtectionType == 1 || g_EnforceDoc.ProtectionType == 2)
		g_EnforceDoc.UnProtect();

	while (nLoop < nLength)
	{
		var strField = arrFieldList[nLoop];

		if (g_EnforceDoc.Bookmarks.Exists(strField))
		{
			var myRange = g_EnforceDoc.Bookmarks(strField).Range.Cells(1).Range;
			var strValue = arrValueList[nLoop];
			var strRecordText = readEnforceRecordText(strField);

			if (strRecordText != strValue)
			{
				myRange.Delete(1,1);
				myRange.Cells(1).Range.Text = strValue;
			}
		}

		nLoop++;
	}

	if (g_ProcessCase != 1 && g_EnforceDoc.ProtectionType == -1)
//	if (g_strEditType != 0 && g_DocWord.ProtectionType == -1)
	{
		if (g_strEditType != 0)
			g_EnforceDoc.Protect(1);
	}
}

function readEnforceRecordText(strApprovalField)
{
	var strRecordText = "";
	if (g_EnforceDoc.ProtectionType == 1)
		g_EnforceDoc.UnProtect();

	if (g_EnforceDoc.Bookmarks.Exists(strApprovalField) == true)
	{
		if (g_bIsFormData == true)
		{
			if (strApprovalField.indexOf(FIELD_BODY) != -1)
			{
				var nProposal = getBodyCount();
				var nTable = g_EnforceDoc.Bookmarks(FIELD_BODY + nProposal).Range.Tables.Count;
				if (nTable == 0)
				{
					strRecordText = g_EnforceDoc.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Paragraphs(1).Range.Text;
				}
				else
				{
					var startloc = g_EnforceDoc.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
					var endloc = g_EnforceDoc.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
					strRecordText = g_EnforceDoc.Range(startloc, endloc-1).Text;
				}
			}

			else
			{
				var startloc = g_EnforceDoc.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
				var endloc = g_EnforceDoc.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
				strRecordText = g_EnforceDoc.Range(startloc, endloc-1).Text;
			}
		}

		else
			strRecordText = g_EnforceDoc.Bookmarks(strApprovalField).Range.Text;
	}
	return strRecordText;
}

function saveEnforceFile(nCaseNumber)
{
	clearEnforceApproveFlow();

	var bRet = false;

	var objEnforceFile = getEnforceFileObj(nCaseNumber);
	var strFileName = getAttachFileName(objEnforceFile);

	var strEnforceDocName = g_EnforceDoc.Name;
	if (strEnforceDocName.indexOf(".doc") == -1)
		strEnforceDocName += ".doc";

	if (strFileName != g_EnforceDoc.Name)
	{
		if (g_EnforceDoc.SaveAs(g_strDownloadPath + strFileName) == true)
		{
			bRet = true;

			var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
			setAttachFileSize(objEnforceFile, strFileSize);
		}
	}

	else
	{
		if (g_EnforceDoc.Save() == true)
		{
			bRet = true;

			var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
			setAttachFileSize(objEnforceFile, strFileSize);
		}
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
}

function clearEnforceApprovalSeal()
{
}

function restoreEnforceApproveFlow()
{
	setEnforceApprovalSeal();
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

					if (g_EnforceDoc.Bookmarks.Exists(strFieldName))
					{
						if (getLocalFileSize(strFilePath) > 0)
						{
							setImageFromFile(strFieldName, strFilePath, g_EnforceDoc);
//							setImageFromFile(strFieldName, strFilePath, g_DocWord);
						}
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function editEnforceDocument(bModify)
{
	if (bModify == true)
	{
		g_nMenuType = 1;
		g_nCase = 3;
	}
	else
	{
		g_nMenuType = 0;
		g_nCase = 2;
	}

	var strFileName = getAttachFileName(getEnforceFileObj(g_nEnforceProposal));

	if (strFileName != "")
	{
		g_bModify = bModify;
		var strLocalPath = g_strDownloadPath + strFileName;

		if (g_nCase == 3)
			g_EnforceDoc.Save();

		openEnforceDocument(strLocalPath);
	}

	return true;
}

function saveEnforceDocument(bIncludeSign)
{
	var strTitle = getTitle(1);
	g_EnforceDoc.Activate();
	g_AppWord.Dialogs(84).Show();
}

function saveEnforceDocumentAs(strFilePath, bIncludeSign)
{
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
	var strDocName = g_EnforceDoc.Name;
	var strTempUrl = parent.getDownloadPath() + "viewdoc1" + ".doc";

	if (confirm(CONFIRM_PRINT_DOCUMENT) == false)
		return;

	if (g_EnforceDoc.SaveAs(strTempUrl) == false)
		return false;

	if (g_EnforceDoc.Save() == false)
		return false;
	var objWordApp = new ActiveXObject("Word.Application");

	if (objWordApp.Documents.Open(strTempUrl, true, true) == false)
		return false;

	if (objWordApp.Documents(1).PrintOut(0) == false)
		return false;

	if (objWordApp.Documents(1).Close() == false)
		return false;

	if (objWordApp.Quit() == false)
		return false;

//	g_bPrinted = true;
}

function setRecordText(strApprovalField, strData, objDocType)
{
	if (objDocType.ProtectionType == 1)
		objDocType.UnProtect();

	if (objDocType.Bookmarks.Exists(strApprovalField))
	{
		var myRange = objDocType.Bookmarks(strApprovalField).Range;
		var strRecordText = readRecordText(strApprovalField,objDocType);

		if (strRecordText != strData)
		{
			var startloc = myRange.Cells(1).Range.Start;
			var endloc = myRange.Cells(1).Range.End;
			myRange.Delete(1,endloc-startloc);
			myRange.Text = strData;
		}
	}
}

function readRecordText(strApprovalField, objDocType)
{
	var startloc = objDocType.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
	var endloc = objDocType.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
	strRecordText = objDocType.Range(startloc, endloc-1).Text;

	return strRecordText;
}

function setImageFromFile(strApprovalField, strSignUrl, objDocType)
{
	clearImage(strApprovalField);
	objDocType.Bookmarks(strApprovalField).Range.InlineShapes.AddPicture(strSignUrl,false,true);

	var mySelection = objDocType.Bookmarks(strApprovalField).Range.Cells(1).Range;
	mySelection.InlineShapes(1).ConvertToShape();
	mySelection.ShapeRange.WrapFormat.Side = 3;
	mySelection.ShapeRange.WrapFormat.Type = 3;
	mySelection.ShapeRange.ZOrder(5);
}

function clearImage(strApprovalField)
{
	if (g_EnforceDoc.ProtectionType == 1)
		g_EnforceDoc.UnProtect();

	g_EnforceDoc.Bookmarks(strApprovalField).Range.Delete(1,1);
}

function drawMenu(nType)
{
	if (g_EnforceDoc.ProtectionType == 1 || g_EnforceDoc.ProtectionType == 2 )
		g_EnforceDoc.UnProtect();

	if (nType == 1)
	{
		var StandardBars = g_EnforceDoc.CommandBars("Standard");
		StandardBars.Position = 1;
		StandardBars.RowIndex = 1;
		StandardBars.Left = 0;
		StandardBars.Controls(2).Enabled = false;
		StandardBars.Controls(3).Enabled = false;
		StandardBars.Controls(5).Enabled = false;
		StandardBars.Controls(6).Enabled = false;
		StandardBars.Visible = true;

		var FormatBars = g_EnforceDoc.CommandBars("Formatting");
		FormatBars.Position = 1;
		FormatBars.RowIndex = 2;
		FormatBars.Left = 0;
		FormatBars.Visible = true;

		var DrawingBars = g_EnforceDoc.CommandBars("Drawing");
		DrawingBars.Visible = true;
	}

	else if (nType == 2)
	{
		if (g_EnforceDoc.ProtectionType == -1)
		{
			if (g_bIsFormData == true)
				g_EnforceDoc.Protect(1);
			else
			{
				if (g_EnforceDoc.Sections.Count != 1)
				{
					g_EnforceDoc.Sections(1).ProtectedForForms = true;
					g_EnforceDoc.Sections(2).ProtectedForForms = true;
					g_EnforceDoc.Protect(2);
				}
			}
		}
	}
}

function updateEnforcePostSendField()
{
	// 문서번호, 시행일자
	var strFieldList = "";
	var strValueList = "";

	if (g_EnforceDoc.Bookmarks.Exists(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (g_EnforceDoc.Bookmarks.Exists(FIELD_ENFORCE_DATE) == true)
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
		putFieldData(strFieldList, strValueList);
	}
}

// icaha, document switching
onEnforceAccessLoadingCompleted();