var g_AppWord = null
var g_DocWord = null;
var g_bIsFormData = true;
var g_ProcessCase = 1;
var g_strLocalPath = "";
var g_nType = 1;
var g_nRemove = 0;
var g_bSaved = false;
var g_strChiefGrade = "";
var g_bMadeRoom = false;

function onLoadDocument()
{
	alert('cn_ApproveWordDocument_origin.js');
	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function loadDocument()
{
	g_bIsModified = (g_strEditType == "0" || g_strEditType == "10");
	downloadDefaultFile();

	if (g_strDataUrl != "")
	{
		var strFileName = getAttachFileName(getBodyFileObj());
		if (strFileName != "")
		{
			g_nType = 0;
			g_ProcessCase = 0;

			var strLocalPath = g_strDownloadPath + strFileName;
			g_strLocalPath = strLocalPath;
			openDocument(strLocalPath);
		}
		else
		{
			NewDocument();
			setIsForm("N");
		}
	}
	else			// 문서 작성(g_ProcessCase = 1)
	{
//===========temp==========================
if (g_strFormUrl.indexOf(".doc") == -1)
	g_strFormUrl = "";
//==============================temp=======
		g_nType = 1;

		if (g_strFormUrl != "")
		{
			openDocument(g_strDownloadPath + g_strFormUrl);
		}
		else
		{
			NewDocument();
			setIsForm("N");
		}
	}

	onDocumentOpen();
}

function NewDocument()	// Word Application을 생성하고 빈 문서를 만든다.
{
	var strLocalPath = "C:\\Program Files\\WebApp\\Default.doc";
	openDocument(strLocalPath);
}

function closeDocument()
{

}

function openDocument(strFormUrl)
{
	wordObject.Navigate2(strFormUrl);
}

function onBeforeNavigate2(objDisp, strUrl, flags, targetframename, postdata, headers, bCancel)
{
	g_DocWord = wordObject.Document;
	if (g_DocWord == null)
	{
	}

	else
	{
		if (g_DocWord.Saved == false)
		{
			g_DocWord.Saved = true;
			g_AppWord.Options.SaveNormalPrompt = true;
		}
	}
}


function wordObject_NavigateComplete2(objDisp, strUrl)
{
	// objDisp : Application object, strUrl : Application 객체가 Open한 URL
	g_DocWord = wordObject.Document;
	g_AppWord = g_DocWord.Application;

	g_DocWord.ActiveWindow.View.Zoom.Percentage = 100;

	g_bIsFormData = isFormData();

	if (g_bIsFormData == true)
		setIsForm("Y");
	else
		setIsForm("N");

	if (g_bIsFormData == false)
	{
		var strTitle = "Default";
		var strDocName = g_DocWord.Name;
		if (strDocName.indexOf(".doc") == -1)
			strDocName = strDocName + ".doc";

		if (strDocName == strTitle + ".doc")
		{
			if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2 )
				g_DocWord.UnProtect();

			var myRange = g_DocWord.Content;
			var StartPos = myRange.Start;
			var EndPos = myRange.End;
			myRange.Select();

			myRange.Delete(1,1);
//			g_DocWord.SaveAs(parent.getDownloadPath() + strTitle + ".doc");
			g_DocWord.Save();
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_BODY + "1"))
	{
		g_DocWord.Bookmarks(FIELD_BODY + "1").Range.Select();
	}

	drawMenuBar(g_nType);

	restoreApproveFlow();

	if (g_strEditType == "0" && g_DocWord.Bookmarks.Exists(FIELD_ORGAN) == true)				//기관명
		putFieldText(FIELD_ORGAN, g_strUserOrgName);

	if (g_ProcessCase == 0 && g_strEditType != 1)
	{
		if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2 )
			g_DocWord.UnProtect();

// 발신자 명의를 필드에 표시
		if (g_DocWord.Bookmarks.Exists(FIELD_SENDER_AS + "_P1"))
				g_strChiefGrade = readTempRecordText(FIELD_SENDER_AS + "_P1");

//대장에서 접수정보 등을 필드에 표시
//		setFormDataToXml();
		setXmlDataToForm();

if (g_strEditType != 21 && g_strEditType != 22)
		setDocumentRecipientsToForm();

		if (g_DocWord.ProtectionType == -1)
		{
			if (g_bIsFormData == true)
				g_DocWord.Protect(1);
			else
			{
				if (g_DocWord.Sections.Count != 1)
				{
					g_DocWord.Sections(1).ProtectedForForms = true;
					g_DocWord.Sections(2).ProtectedForForms = true;
					g_DocWord.Protect(2);
				}
			}
		}
	}

	else if (g_ProcessCase == 0 && g_strEditType == 1)
	{
		if (g_bIsFormData == false)
		{
			if (g_DocWord.ProtectionType == 2)
			{
				g_DocWord.UnProtect();
				if (g_DocWord.Sections.Count != 1)
				{
					g_DocWord.Sections(1).ProtectedForForms = true;
					g_DocWord.Sections(2).ProtectedForForms = true;
					g_DocWord.Protect(2);
				}
			}
		}
	}

	if (g_ProcessCase == 0 && g_strEditType == 0)
	{
		if (g_bIsFormData == true)
		{
			if (g_DocWord.ProtectionType == 1)
				g_DocWord.UnProtect();
		}

		else if (g_bIsFormData == false)
		{
			if (g_DocWord.ProtectionType == -1 && g_DocWord.Sections.Count != 1)
				protectSection();
		}
		drawMenuBar(1);
	}

	else if (g_ProcessCase == 2 && g_strEditType == 1)
	{
		if (g_bIsFormData == true)
		{
			if (g_DocWord.ProtectionType == 1)
				g_DocWord.UnProtect();
		}

		else if (g_bIsFormData == false)
		{
			if (g_DocWord.ProtectionType == -1)
				protectSection();

		}
//		drawMenuBar(1);
	}

	else if (g_ProcessCase != 0 && g_ProcessCase != 1)
	{
		if (g_DocWord.ProtectionType == -1)
		{
			if (g_bIsFormData == true)
				g_DocWord.Protect(1);
			else
			{
				g_DocWord.Sections(1).ProtectedForForms = true;
				g_DocWord.Sections(2).ProtectedForForms = true;
				g_DocWord.Protect(2);
			}
		}
		else if (g_DocWord.ProtectionType == 2)
		{
			if (g_bIsFormData == false)
			{
				g_DocWord.UnProtect();
				g_DocWord.Sections(1).ProtectedForForms = true;
				g_DocWord.Sections(2).ProtectedForForms = true;
				g_DocWord.Protect(2);
			}
		}
	}
}

function isFormData()
{
	var bIsFormData = false;

	if (g_DocWord == null)
		bIsFormData = false;

	if (g_DocWord.Bookmarks.Exists(FIELD_TITLE))
	{
		bIsFormData = true;
	}

	return bIsFormData;
}

function drawMenuBar(nType)
{

	if (nType == 1)
	{
		if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2 )
			g_DocWord.UnProtect();

		var StandardBars = g_DocWord.CommandBars("Standard");
		StandardBars.Position = 1;
		StandardBars.RowIndex = 1;
		StandardBars.Left = 0;
		StandardBars.Controls(2).Enabled = false;
		StandardBars.Controls(3).Enabled = false;
		StandardBars.Controls(5).Enabled = false;
		StandardBars.Controls(6).Enabled = false;
		StandardBars.Visible = true;

		var FormatBars = g_DocWord.CommandBars("Formatting");
		FormatBars.Position = 1;
		FormatBars.RowIndex = 2;
		FormatBars.Left = 0;
		FormatBars.Visible = true;

/*		var FormatBars = g_DocWord.CommandBars("Tables and Borders");
		FormatBars.Position = 1;
		FormatBars.RowIndex = 3;
		FormatBars.Left = 0;
		FormatBars.Visible = true;
*/
		var DrawingBars = g_DocWord.CommandBars("Drawing");
		DrawingBars.Visible = true;

		if (g_bIsFormData == false)
		{
			if (g_DocWord.Sections.Count != 1)
			{
				protectSection();
			}
		}

	}

	else if (nType == 0)
	{
		if (g_DocWord.ProtectionType == -1)
		{
			if (g_bIsFormData == true)
				g_DocWord.Protect(1);
			else
			{
				if (g_DocWord.Sections.Count != 1)
				{
					g_DocWord.Sections(1).ProtectedForForms = true;
					g_DocWord.Sections(2).ProtectedForForms = true;
					g_DocWord.Protect(2);
				}
			}
		}
	}
}

function reloadDocument()
{
	g_ProcessCase = 0;
	openDocument(g_strLocalPath);
}

function changeFormUrl(strFormUrl)
{
	g_strFormUrl = strFormUrl;

	openDocument(strFormUrl);
/*
	var bIsForm = isFormData();
	if (bIsForm == true)
		setIsForm("Y");
	else
		setIsForm("N");
*/
	setXmlDataToForm();


	saveBodyFile();
	setModified(true);

	editDocument(true);
}

function getFileExtension()
{
	return "doc";
}

function saveBodyFile()
{
	var strDocName = g_DocWord.Name;
	if (strDocName.indexOf(".doc") == -1)
		strDocName = strDocName + ".doc";

	clearApproveFlow();

	var bRet = false;

	var objBodyFile = getBodyFileObj();
	if (objBodyFile == null)
		objBodyFile = setBodyFileInfo();

	var strFileName = getAttachFileName(objBodyFile);

	if (strFileName.indexOf(".doc") == -1)
	{
		strFileName = strFileName + ".doc";
	}


	if (g_strDataUrl == "")
		g_DocWord.SaveAs(g_strDownloadPath + strFileName);

	else
	{
		if (strDocName != strFileName)
			g_DocWord.SaveAs(g_strDownloadPath + strFileName);

		else
			g_DocWord.Save();
	}
//	if (g_DocWord.SaveAs(g_strDownloadPath + strFileName) == true)
	{
		bRet = true;

		var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
		setAttachFileSize(objBodyFile, strFileSize);
	}


	if (g_strEditType != 1)			// g_strEditType == 1 : 수정모드(문서수정, 수신수정)
	{
		restoreApproveFlow();
	}

	g_bSaved = true;
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
		removeApprovalRoom();
	else
	{
		clearApprovalFlow();
		clearApprovalSign();
		clearExaminationSign();
		clearApprovalSeal();
	}
}

function removeApprovalRoom()
{
	if (g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	if (g_DocWord.Sections.Count == 2 && g_DocWord.Sections(1).Range.Tables.Count == 1)
	{
		g_nRemove++;
		g_DocWord.Sections(1).Range.Tables(1).Select();
		g_AppWord.Selection.TypeBackspace();

		var nPara = g_DocWord.Sections(1).Range.Paragraphs.Count;
		if (nPara != 1);
		{
			g_DocWord.Sections(1).Range.Paragraphs(2).Range.Select();
			g_AppWord.Selection.TypeBackspace();
		}
	}
}

function putFieldText(strFieldList, strValueList)
{
	var nLoop = 0;
	var arrFieldList = strFieldList.split("\u0002");
	var arrValueList = strValueList.split("\u0002");
	var nLength = arrFieldList.length;

	if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	while (nLoop < nLength)
	{
		var strField = arrFieldList[nLoop];

		if (g_DocWord.Bookmarks.Exists(strField))
		{
			var myRange = g_DocWord.Bookmarks(strField).Range.Cells(1).Range;
			var strValue = arrValueList[nLoop];
			var strRecordText = readTempRecordText(strField);

			if (strRecordText != strValue)
			{
				myRange.Delete(1,1);
				myRange.Cells(1).Range.Text = strValue;
			}
		}

		nLoop++;
	}

	if (g_ProcessCase != 1 && g_DocWord.ProtectionType == -1)
//	if (g_strEditType != 0 && g_DocWord.ProtectionType == -1)
	{
		if (g_strEditType != 0)
			g_DocWord.Protect(1);
	}
}

function setRecordImageFromFile(strApprovalField, strSignUrl)
{
	clearRecordImage(strApprovalField);

	if (g_DocWord.Bookmarks.Exists(strApprovalField) == true)
	{
		g_DocWord.Activate();

		g_DocWord.Bookmarks(strApprovalField).Range.InlineShapes.AddPicture(strSignUrl,false,true);

		var mySelection = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range;
		var strRecordText = readTempRecordText(strApprovalField);

		if (g_bIsFormData == true)
		{

//			if (g_strEditType < 30 && g_strEditType != 22)// && strStatus != 22)
			if (strRecordText.length > 3)
			{
				mySelection.InlineShapes(1).ConvertToShape();
//				mySelection.ShapeRange.WrapFormat.AllowOverlap = true;
				mySelection.ShapeRange.WrapFormat.Type = 3;		//wdWrapSquare=0, wdWrapTight=1, wdWrapThrough=2, wdWrapNone=3, wdWrapTopBottom=4
				mySelection.ShapeRange.WrapFormat.Side = 3;		//wdWrapBoth=0, wdWrapLeft=1, wdWrapRight=2, wdWrapLargest=3
				mySelection.ShapeRange.ZOrder(5);
			}
		}
		else
		{
			if (strRecordText.length > 3)
			{
				mySelection.InlineShapes(1).ConvertToShape();
				mySelection.ShapeRange.WrapFormat.Type = 3;
				mySelection.ShapeRange.ZOrder(5);				//msoSendBehindText
//				mySelection.ShapeRange.ZOrder(4);				//msoBringInFrontOfText
			}
		}
	}
}

function setCellSlash(strSlashField)
{
	if (g_DocWord.Bookmarks.Exists(strSlashField) == true)
	{
		var mySelection = g_DocWord.Bookmarks(strSlashField).Range.Cells(1).Range;
		if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
			g_DocWord.UnProtect();

		if (mySelection.Borders(-8).LineStyle == 0)
			mySelection.Borders(-8).LineStyle = 1;
		else
			mySelection.Borders(-8).LineStyle = 0;
	}
}

function restoreApproveFlow()
{
	setApprovalFlow();
	setApprovalSign();
	setExaminationSign();
	setApprovalSeal();
	setOrganImage();

	if (g_ProcessCase != 1 && g_DocWord.ProtectionType == -1)
	{
		if (g_strEditType != 0)
		{
			if (g_bIsFormData == true)
				g_DocWord.Protect(1);
			else
			{
				g_DocWord.Protect(2);
			}
		}
	}
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
		if (strFormUsage == "0")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName("0");

		if (objApprovalLine == null)
			return;

		if (strFormUsage == "0")
			setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID);
		else
			setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID);
	}
}

function setApprovalFieldByApprovalLine(objApprovalLine, nSetType, bSetData, strUserID)
{
	var strFieldSlots = "", strFieldList = "", strValueList = "";
	var strApprovalFieldList = ""; strApprovalValueList = "";
	var strDateFieldList = ""; strDateValueList = "";
	var bIsForm = isFormData();
	var strSlashFields = "";

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
				strApprovalField += strType + "_1";
			else
				strApprovalField += "1";
		}
		else if (strRole == "1")
		{
			strApprovalField = FIELD_DRAFTER;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "_1";
			else
				strApprovalField += "1";
		}
		else if (strRole == "2")
		{
			nApprove++;
			strApprovalField = FIELD_CONSIDER;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "_" + nApprove;
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
					strApprovalField += strType + "_" + nApprove;
				else
					strApprovalField += nApprove;
			}
			else if (nSetType == 1)
			{
				strApprovalField = FIELD_LAST;
				if (strType == "1" || strType == "2")
					strApprovalField += strType + "_1";
				else
					strApprovalField += "1";
			}
		}
		else if (strRole == "3" || strRole == "12" || strRole == "13" || strRole == "32" || strRole == "33")
		{
			nAssist++;
			strApprovalField = FIELD_COOPERATE;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "_" + nAssist;
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
						strApprovalField += strType + "_1";
					else
						strApprovalField += "1";
				}

				else if (strType == "1")
				{
					var objNextApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder + 1));
					var strNextType = getApproverType(objNextApprover);
					strApprovalField = FIELD_LAST;
					if (strNextType != strType)
						strApprovalField += strType + "_1";
					else
						strApprovalField += "1";
				}
/*
				else
				{
					nApprove++;
					strApprovalField = FIELD_CONSIDER;
					if (strType == "1" || strType == "2")
						strApprovalField += strType + "_" + nApprove;
					else
						strApprovalField += nApprove;
				}
			}
		}
*/
				else
				{
					var objNextApprover = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder + 1));
					var strNextType = getApproverType(objNextApprover);
					var strNextRole = getApproverRole(objNextApprover);
					if (strType == "1")
					{
						if (strNextType != strType)
						{
							strApprovalField = FIELD_LAST + strType + "_1";
						}
						else
						{
							if (strNextRole == "10" || strNextRole == "30")
							{
								strApprovalField = FIELD_LAST + strType + "_1";
							}
							else
							{
								nApprove++;
								strApprovalField = FIELD_CONSIDER + strType + "_" + nApprove;
							}
						}
					}
					else
					{
						if (strNextRole == "10" || strNextRole == "30")
						{
							strApprovalField = FIELD_LAST;
							if (strType == "1" || strType == "2")
								strApprovalField += strType + "_1";
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
								strApprovalField += strType + "_" + nApprove;
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
		else if (strRole == "31" || strRole == "11")
		{
//			strApprovalField = FIELD_DEPART_INSPECT + "1";
// 2003.04.02 for 연합캐피탈
			nInspect++;
			strApprovalField = FIELD_DEPART_INSPECT;
			if (strType == "1" || strType == "2")
				strApprovalField += strType + "_" + nInspect;
			else
				strApprovalField += nInspect;
		}

		else if (strRole == "30")
		{
			if (bSetData)
			{
				var strDeptCode = getApproverDeptCode(objApprover);
			}
		}

		if (nSetType == 0)
		{
			// strRole != "4"(열람) 비교 제거함
//			if (strApprovalField != "" && strRole != "10" && strRole != "30")
			if (strApprovalField != "" && strRole != "4" && strRole != "10" && strRole != "30")
			{
				if (g_DocWord.Bookmarks.Exists(strApprovalField) == false)
					strFieldSlots += strApprovalField + "^";

				// 이름
				var strApproverName = "N_" + strApprovalField;
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
				var strApproverDept = "DN_" + strApprovalField;
				strFieldList += strApproverDept + "\u0002";
				strValueList += strDeptName + "\u0002";

				// 직위 or 부서명(부서단위)
				var strApprovalType = "P_" + strApprovalField;

				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (g_DocWord.Bookmarks.Exists(strApproverDept) == false)
					{
						strFieldList += strApprovalType + "\u0002";
						strApprovalFieldList += strApprovalType + "\u0002";
						strValueList += strDeptName + "\u0002";
						strApprovalValueList += strDeptName + "\u0002";
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
					strApprovalFieldList += strApprovalType + "\u0002";
					strValueList += strPosition + "\u0002";
					strApprovalValueList += strPosition + "\u0002";
				}

				// 상세직위 or 부서명(부서단위)
				var strApproverTitle = "SP_" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33" || strRole == "34")
				{
					if (g_DocWord.Bookmarks.Exists(strApproverDept) == false)
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
					{
						strProxyFieldValue += FIELD_PROXY;
					}

					strValueList += strProxyFieldValue + "\u0002";

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
					var strRearFieldValue = "";
					if (bSetData)
						strRearFieldValue += FIELD_REAR;
					strValueList += strRearFieldValue + "\u0002";
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
								setRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
							}
						}
					}
				}
				else
				{
					clearRecordImage(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearRecordImage(strApprovalField);
					if (g_DocWord.ProtectionType == -1)
					{
						if (g_bIsFormData == true)
							g_DocWord.Protect(1);
						else
						{
							if (g_DocWord.Sections.Count != 1)
							{
								g_DocWord.Sections(1).ProtectedForForms = true;
								g_DocWord.Sections(2).ProtectedForForms = true;
								g_DocWord.Protect(2);
							}
						}
					}
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
							strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));

							var strDate = "";
							if (bSetData)
								strDate = strSignDate;//getDateDisplay(strSignDate, g_strOption36);

							strApprovalField = "D_" + strApprovalField;

							strFieldList += strApprovalField + "\u0002";
							strValueList += strDate + "\u0002";

	// for memo start
							strDateFieldList += strApprovalField + "\u0002";
							strDateValueList += strDate + "\u0002";
	// for memo end

							if (strRole == "1")
							{
								var strDateField = FIELD_DRAFTER + "일자";
								if (strType == "1" || strType == "2")
									strDateField += strType + "-1";
								else
									strDateField += "1";

								strFieldList += strDateField + "\u0002";
								strValueList += strDate + "\u0002";

		// for memo start
								strDateFieldList += strDateField + "\u0002";
								strDateValueList += strDate + "\u0002";
		// for memo end
							}
						}
					}
				}
				else
				{
					var strDateField = "D_" + strApprovalField;

					strFieldList += strDateField + "\u0002";
					strValueList += "" + "\u0002";

// for memo start
					strDateFieldList += strDateField + "\u0002";
					strDateValueList += "" + "\u0002";
// for memo end

					if (strRole == "1")
					{
						strDateField = FIELD_DRAFTER + "일자";

						if (strType == "1" || strType == "2")
							strDateField += strType + "-1";
						else
							strDateField += "1";

						strFieldList += strDateField + "\u0002";
						strValueList += "" + "\u0002";

	// for memo start
						strDateFieldList += strDateField + "\u0002";
						strDateValueList += "" + "\u0002";
	// for memo end

					}
				}

			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					var strMMDD = "";
					strApprovalField = "D_" + strApprovalField;

					strFieldList += strApprovalField + "\u0002";
					strValueList += strMMDD + "\u0002";

// for memo start
					strDateFieldList += strApprovalField + "\u0002";
					strDateValueList += strMMDD + "\u0002";
// for memo end

					break;
				}
			}
		}

		nSerialOrder++;
		objApprover = getApproverBySerialOrder(objApprovalLine, "" + nSerialOrder);
	}

	if (nSetType == 0)
	{
		if (g_DocWord.Bookmarks.Exists(FIELD_REPORT_DEPT))
		{
			strFieldList += FIELD_REPORT_DEPT + "\u0002";
			strValueList += strReportDept + "\u0002";
		}

		if (strFieldSlots.length > 0 && strFieldSlots.charAt(strFieldSlots.length - 1) == "^")
			strFieldSlots = strFieldSlots.substring(0, strFieldSlots.length - 1);

		if (bIsForm == false)
		{
			insertSection();
			if (g_DocWord.Sections(1).Range.Tables.Count == 0)
			{
//				makeApprovalRooms(strFieldSlots, strValueList);
				makeApprovalRooms(strFieldSlots, strApprovalValueList);
				g_DocWord.Sections(1).Range.Tables(1).AllowAutoFit = false;
				g_DocWord.Sections(1).Range.Tables(1).Rows(2).HeightRule = 2;
			}

			if (g_DocWord.Sections.Count == 2)
			{
//				if (g_nRemove != 0) // 줄하나 늘어나는것 방지
				if (g_bMadeRoom == true)
				{
					var nPara = g_DocWord.Sections(2).Range.Paragraphs.Count;

					if (nPara != 1)
					{
						var nTable = g_DocWord.Sections(2).Range.Paragraphs(1).Range.Tables.Count;

						if (nTable != 1)
							g_DocWord.Sections(2).Range.Paragraphs(1).Range.Delete();
					}
				}
			}
		}

		if (strSlashFields != "")
		{
			strSlashFields = strSlashFields.substring(0, strSlashFields.length - 1);
			var arrSlashField = strSlashFields.split("^");
			for (i = arrSlashField.length - 1; i >= 0; i--)
			{
				setCellSlash(arrSlashField[i]);
			}
		}

	}
	if (strFieldList.length > 0)
	{
		if (g_bIsFormData == true)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);
//			putFieldText(strFieldList, strValueList);

			showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
			setApprovalText(strFieldList, strValueList);
			hideWaitDlg();
		}

		else
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);

//			putFieldText(strApprovalFieldList, strApprovalValueList);
			showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
			setApprovalText(strDateFieldList, strDateValueList);
			hideWaitDlg();
		}
	}

	if (g_bIsFormData == false)
	{
		protectSection();
	}

}

function setApprovalFieldByApprovalLineGov(objApprovalLine, nSetType, bSetData, strUserID)
{
	var bIsForm = isFormData();

	var strFieldSlots = "", strFieldList = "", strValueList = "";
	var strEmptyFields = "";
	var strSlashFields = "";
	var strDateFieldList = "", strDateValueList = "";

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
				strFieldSlots += strApprovalField + "^";

				// 이름
				var strApproverName = "N_" + strApprovalField;
				if (strRole == "31" || strRole == "32" || strRole == "33")
				{
				}
				else
				{
					var strUserName = "";
					if (bSetData)
					{
						strUserName = getApproverUserName(objApprover);
						if (getApproverRepName(objApprover) != "")
							strUserName = getApproverRepName(objApprover);
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
				var strApprovalType = "P_" + strApprovalField;
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
				var strApprovalTypeAbbr = "PA_" + strApprovalField;
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

				// 전대결등 표시
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

				// 날짜 필드에 먼저 세팅함
				strFieldList += "D_" + strApprovalField + "\u0002";
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
								setRecordImageFromFile(strApprovalField, g_strDownloadPath + strSignUrl);
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
					clearRecordImage(strApprovalField);
				}
			}
			else if (nSetType == 2)
			{
				strApprovalID = getApproverUserID(objApprover);
				if (strApprovalID == strUserID)
				{
					clearRecordImage(strApprovalField);
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
							strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));

							var strDate = "";
							if (bSetData)
								strDate = strSignDate;//getDateDisplay(strSignDate, g_strOption36);
/*
							var strDate = "";
							if (bSetData)
								strDate = getDateDisplay(strSignDate, g_strOption36);
*/
							strApprovalField = "D_" + strApprovalField;

							strFieldList += strApprovalField + "\u0002";
							strValueList += readTempRecordText(strApprovalField) + " " + strDate + "\u0002";

	// for memo start
							strDateFieldList += strApprovalField + "\u0002";
							strDateValueList += readTempRecordText(strApprovalField) + " " + strDate + "\u0002";
	// for memo end

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
					strApprovalField = "D_" + strApprovalField;

					strFieldList += strApprovalField + "\u0002";
					strValueList += strDate + "\u0002";

					strDateFieldList += strApprovalField + "\u0002";
					strDateValueList += strDate + "\u0002";

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
			insertSection();
			if (g_DocWord.Sections(1).Range.Tables.Count == 0)
			{
				makeApprovalRooms(strFieldSlots, strValueList);
				g_DocWord.Sections(1).Range.Tables(1).AllowAutoFit = false;
				g_DocWord.Sections(1).Range.Tables(1).Rows(2).HeightRule = 2;
			}

			if (g_DocWord.Sections.Count == 2)
			{
//				if (g_nRemove != 0) // 줄하나 늘어나는것 방지
				if (g_bMadeRoom == true)	//기안작성시가 아니면
				{
					var nPara = g_DocWord.Sections(2).Range.Paragraphs.Count;

					if (nPara != 1)
					{
						var nTable = g_DocWord.Sections(2).Range.Paragraphs(1).Range.Tables.Count;

						if (nTable != 1)
							g_DocWord.Sections(2).Range.Paragraphs(1).Range.Delete();
					}
				}
			}

		}

	}

	if (strFieldList.length > 0)
	{
		if (g_bIsFormData == true)
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);

			showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
			putFieldText(strFieldList, strValueList);
			hideWaitDlg();
		}
		else
		{
			strFieldList = strFieldList.substring(0, strFieldList.length - 1);
			strValueList = strValueList.substring(0, strValueList.length - 1);

//			putFieldText(strFieldList, strValueList);
			showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
			putFieldText(strApprovalFieldList, strApprovalValueList);
			setApprovalText(strDateFieldList, strDateValueList);
			hideWaitDlg();
		}
	}

	if (g_bIsFormData == false)
	{
		protectSection();
	}
}

function setApprovalText(strFieldList, strValueList)
{
	var nLoop = 0;
	var arrFieldList = strFieldList.split("\u0002");
	var arrValueList = strValueList.split("\u0002");
	var nLength = arrFieldList.length;

	if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	while (nLoop < nLength)
	{
		var strField = arrFieldList[nLoop];

// 2003.04.01 for 연합캐피탈
		if (g_bIsFormData == true)
		{
			if (strField.indexOf("P_") != -1 || strField.indexOf("D_") != -1 || strField.indexOf("N_") != -1 || strField.indexOf("기안일자1") != -1)
			{
				if (g_DocWord.Bookmarks.Exists(strField))
				{
					var myRange = g_DocWord.Bookmarks(strField).Range.Cells(1).Range;
					var strValue = arrValueList[nLoop];
					var strRecordText = readTempRecordText(strField);
					if (strRecordText != strValue)
					{
						myRange.Delete(1,1);
						myRange.Cells(1).Range.Text = strValue;
					}
				}
			}
		}
		else
		{
//			if (strField.indexOf("P_") != -1)
			{
				if (g_DocWord.Bookmarks.Exists(strField))
				{
					var myRange = g_DocWord.Bookmarks(strField).Range.Cells(1).Range;
					var strValue = arrValueList[nLoop];
					myRange.Cells(1).Range.Text = strValue;
				}
			}
		}
		nLoop++;
	}
}

function protectSection()
{
	if (g_DocWord.Sections.Count == 2 && g_DocWord.ProtectionType == -1)
	{
		g_DocWord.Sections(1).ProtectedForForms = true;
		g_DocWord.Sections(2).ProtectedForForms = false;
		g_DocWord.Protect(2);//, false, "");
	}
}

function insertSection()
{
	if (g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	if (g_DocWord.Sections.Count == 1)
		g_DocWord.Range(0,0).InsertBreak(3);
}


function makeApprovalRooms(strFieldSlots, strValueList)
{
	g_bMadeRoom = true;
	var strHtmlTableStartString = "";
	var strHtmlTableEndString = "";
	var strHtmlPositionString = "";
	var strHtmlUserSignString = "";
	var strHtmlSignDateString = "";
	var strFilePath = parent.getDownloadPath() + "tempHtmlField.doc";

	var nLoop = 0;
	var arrFieldList = strFieldSlots.split("^");
	var arrValueList = strValueList.split("\u0002");
	var nField = arrFieldList.length;

/*
	if (!objXMLDom.load(g_strBaseUrl + "approve/document/word/WordFieldRoomContent.xml"))
	{
		alert("load failure");
		return false;
	}
*/
//	var strHtmlString = objXMLDom.documentElement.text;

	strHtmlTableStartString	+= "<html><body lang=KO style='tab-interval:40.0pt'>";
	strHtmlTableStartString += "<div class=Section1 style='layout-grid:18.0pt'>";
	strHtmlTableStartString += "<table border=1 cellspacing=0 cellpadding=0 style='border-collapse:collapse;";
	strHtmlTableStartString += "border:none;";
	strHtmlTableStartString += "mso-border-alt:solid windowtext .5pt;";
	strHtmlTableStartString += "mso-table-overlap:never;mso-table-lspace:7.1pt;mso-table-rspace:7.1pt;";
	strHtmlTableStartString += "mso-table-anchor-vertical:paragraph;mso-table-anchor-horizontal:column;";
	strHtmlTableStartString += "mso-table-left:right;mso-table-top:.05pt;mso-padding-alt:0cm 0cm 0cm 0cm'>";


	strHtmlPositionString += "<tr>";
	strHtmlUserSignString += "<tr style='height:53.85pt'>";
	strHtmlSignDateString += "<tr>";

	while (nLoop < nField)
	{
		var strApprovalType = "P_" + arrFieldList[nLoop];
		var strSignDate = "D_" + arrFieldList[nLoop];

		strHtmlPositionString += "<td width=72 nowrap style='width:54.25pt;border:solid windowtext .5pt;";
		strHtmlPositionString += "border-left:none;mso-border-left-alt:solid windowtext .5pt;";
		strHtmlPositionString += "padding:0cm 0cm 0cm 0cm;text-fit:100%'>";
		strHtmlPositionString += "<p class=MsoNormal align=center style=";

//		strHtmlPositionString += "'mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;";
		strHtmlPositionString += "'text-align:center;";

		strHtmlPositionString += "mso-element:frame;";
		strHtmlPositionString += "mso-element-frame-hspace:7.1pt;mso-element-wrap:around;mso-element-anchor-vertical:paragraph;";
		strHtmlPositionString += "mso-element-anchor-horizontal:column;mso-element-left:right;";
		strHtmlPositionString += "mso-element-top:.05pt;mso-height-rule:exactly'>";
		strHtmlPositionString += "<a name=\""+strApprovalType+"\">"+arrValueList[nLoop]+"</a></p></td>";
//		strHtmlPositionString += "<a name=\""+strApprovalType+"\"></a></p></td>";
		strHtmlPositionString += "<span style='mso-bookmark:"+strApprovalType+"'></span>";

		strHtmlUserSignString += "<td width=72 nowrap style='width:54.25pt;border:solid windowtext .5pt;";
		strHtmlUserSignString += "border-top:none;mso-border-top-alt:solid windowtext .5pt;";
		strHtmlUserSignString += "mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;";
		strHtmlUserSignString += "padding:0cm 0cm 0cm 0cm;text-fit:100%;height:53.85pt'>";
		strHtmlUserSignString += "<p class=MsoNormal align=center style=";
		strHtmlUserSignString += "'text-align:center;";
		strHtmlUserSignString += "mso-element:frame;";
		strHtmlUserSignString += "mso-element-frame-hspace:7.1pt;mso-element-wrap:around;mso-element-anchor-vertical:paragraph;";
		strHtmlUserSignString += "mso-element-anchor-horizontal:column;mso-element-left:right;";
		strHtmlUserSignString += "mso-element-top:.05pt;mso-height-rule:exactly'>";
		strHtmlUserSignString += "<a name="+arrFieldList[nLoop]+"></a></p></td>";
		strHtmlUserSignString += "<span style='mso-bookmark:"+ arrFieldList[nLoop] +"'></span>";

		strHtmlSignDateString += "<td width=72 nowrap style='width:54.25pt;border:solid windowtext .5pt;";
		strHtmlSignDateString += "border-top:none;mso-border-top-alt:solid windowtext .5pt;";
		strHtmlSignDateString += "border-left:none;border-bottom:solid windowtext .5pt;border-right:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;";
		strHtmlSignDateString += "padding:0cm 0cm 0cm 0cm;text-fit:100%'>";
		strHtmlSignDateString += "<p class=MsoNormal align=center style=";
		strHtmlSignDateString += "'text-align:center;";
		strHtmlSignDateString += "mso-element:frame;";
		strHtmlSignDateString += "mso-element-frame-hspace:7.1pt;mso-element-wrap:around;mso-element-anchor-vertical:paragraph;";
		strHtmlSignDateString += "mso-element-anchor-horizontal:column;mso-element-left:right;";
		strHtmlSignDateString += "mso-element-top:.05pt;mso-height-rule:exactly'>";
		strHtmlSignDateString += "<a name="+strSignDate+"></a></p></td>";
		strHtmlSignDateString += "<span style='mso-bookmark:"+strSignDate+"'></span>";

		nLoop++;
	}

	strHtmlPositionString += "</tr>";
	strHtmlUserSignString += "</tr>";
	strHtmlSignDateString += "</tr>";
	strHtmlTableEndString += "</table></div></body></html>";

//	var strHtmlFieldString = strHtmlString + strHtmlTableStartString + strHtmlPositionString + strHtmlUserSignString + strHtmlSignDateString + strHtmlTableEndString;
	var strHtmlFieldString = strHtmlTableStartString + strHtmlPositionString + strHtmlUserSignString + strHtmlSignDateString + strHtmlTableEndString;
//	parent.HttpTransfer1.SaveToLocalFile(strFilePath, strHtmlFieldString);
	parent.FileTransfer.SaveToLocalFile(strFilePath, strHtmlFieldString);

	var myRange = g_DocWord.Content;
	myRange.Collapse(1);

	myRange.insertfile(strFilePath,"",false);
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
			{
				setRecordImageFromFile(FIELD_INSPECTOR, strSignFilePath);
			}
		}

		var strSignDate = getDelivererSignDate(objDeliverer);
		if (strSignDate != "")
		{
			strSignDate = strSignDate.substring(0, strSignDate.indexOf(" "));
/*
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
*/
			if (g_DocWord.Bookmarks.Exists(FIELD_INSPECT_DATE))
				putFieldText(FIELD_INSPECT_DATE, strSignDate);
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

				if (g_DocWord.Bookmarks.Exists(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
						setRecordImageFromFile(strFieldName, strFilePath);
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

				if (g_DocWord.Bookmarks.Exists(strFieldName))
				{
					if (getLocalFileSize(strFilePath) > 0)
						setRecordImageFromFile(strFieldName, strFilePath);
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
	clearRecordImage(FIELD_INSPECTOR);

	if (g_DocWord.Bookmarks.Exists(FIELD_INSPECT_DATE))
		putFieldText(FIELD_INSPECT_DATE, "");
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

			clearRecordImage(strFieldName);
		}
		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}
}

function clearRecordImage(strApprovalField)
{
	if (g_DocWord.ProtectionType  == 1 || g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(strApprovalField))
	{
		var nImage = g_DocWord.Bookmarks(strApprovalField).Range.InlineShapes.Count;
		var strRecordText = readTempRecordText(strApprovalField);

		if (g_bIsFormData == true)
		{
//	temp (0305)
			if ((strRecordText == "") || (strRecordText.length <= 1) || (nImage != 0))
			{
				g_DocWord.Activate();
				g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,1);
			}

			var mySelection = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range;

			if (mySelection.Borders(-8).LineStyle == 1)
			{
				mySelection.Borders(-8).LineStyle = 0;
			}
/*			else
			{
				g_DocWord.Bookmarks(strApprovalField).Range.Delete();
			}
*/		}
		else
		{
			if (strRecordText.length <= 1)
				g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,1);
		}
	}
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
			strDeptRefField = FIELD_RECIPIENT_REFER + "_P1";
		}

		else
		{
			strDeptField = FIELD_RECIPIENT + nCaseNumber;
			strRefField = FIELD_REFERER + nCaseNumber;
			strDeptRefField = FIELD_RECIPIENT_REFER + "_P" + nCaseNumber;
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
					strDeptName = getRecipientDeptName(objRecipient);

					strRefName = getRecipientRefDeptName(objRecipient);

					if (strDeptRefName != "")
						strDeptRefName += ", ";

					strDeptRefName += strDeptName;
					if (strRefName != "")
						strDeptRefName += "(" + strRefName + ")";

					objRecipient = getNextRecipient(objRecipient);
				}
			}

			if (g_DocWord.Bookmarks.Exists(strDeptField))
			{
				strFieldList += strDeptField + "\u0002";
				if (nRecipientCount == 0)
					strValueList += strDisplayAs + "\u0002";
				else if (nRecipientCount == 1)
					strValueList += strDeptName + "\u0002";
				else
					strValueList += RECIPIENT_REFER + "\u0002";
			}

			if (g_DocWord.Bookmarks.Exists(strRefField))
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

			if (g_DocWord.Bookmarks.Exists(strDeptRefField))
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
			if (g_DocWord.Bookmarks.Exists(strDeptRefField))
			{
				strFieldList += strDeptField + "\u0002";
				var strDocCategory = getDocCategory(nCaseNumber);
				if (strDocCategory == "I")
					strValueList += INNER_APPROVAL + "\u0002";
				else
					strValueList += "\u0002";
			}
			if (g_DocWord.Bookmarks.Exists(strRefField))
			{
				strFieldList += strRefField + "\u0002";
				strValueList += "\u0002";
			}

			if (g_DocWord.Bookmarks.Exists(strDeptRefField))
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

		showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
		putFieldText(strFieldList, strValueList);
		hideWaitDlg();
	}

//  temp(02019)
//	saveBodyFile();

	setModified(true);

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
		var strSenderAsField = FIELD_SENDER_AS + "_P" + n;
		if (g_DocWord.Bookmarks.Exists(strSenderAsField))
		{
			strFieldList += strSenderAsField + "\u0002";
			if (getDocCategory(n) == "I")
				strValueList += "\u0002";
			else
				strValueList += strChiefGrade + "\u0002";
		}
	}

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);

		showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
		putFieldText(strFieldList, strValueList);
		hideWaitDlg();
	}

}


function addDocument()
{
	var bIsForm = isFormData();
	if (bIsForm == false)
		return false;

	var nProposal = getBodyCount();
	nProposal = nProposal+1;

	var strProposal = PRE_PROPOSAL + nProposal + PROPOSAL;
	var strRecipient = FIELD_RECIPIENT + nProposal;
	var strReferer = FIELD_REFERER + nProposal;
	var strTitle = FIELD_TITLE + nProposal;
	var strBody = FIELD_BODY + nProposal;
	var strSenderAs = FIELD_SENDER_AS + "_P" + nProposal;
	var strRecipientRefer = FIELD_RECIPIENT_REFER + "_P" + nProposal;

	var strProposalString = "";
	strProposalString += "<html><body>";
	strProposalString += "<br></br>";
	strProposalString += "<table border=1 cellspacing=0 cellpadding=0 width=679 style='width:508.9pt;font-size:10.pt;";
	strProposalString += "border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;";
	strProposalString += "mso-padding-alt:0cm 0cm 0cm 0cm'>";

	strProposalString += "<tr>";
	strProposalString += "<td width=56 style='width:41.7pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>";
	strProposalString += "</p></td>";
	strProposalString += "<td width=623 valign=top style='width:467.2pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "<p class=MsoNormal>";
	strProposalString += "<span lang=EN-US>("+strProposal+")</span></p>";
	strProposalString += "</td>";
	strProposalString += "<a name="+strProposal+"></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr>";
	strProposalString += "<td width=56 style='width:41.7pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>"+FIELD_RECIPIENT;
	strProposalString += "</p></td>";
	strProposalString += "<td width=623 style='width:467.2pt; border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";

	strProposalString += "<p class=MsoNormal>";
	strProposalString += "<span lang=EN-US></span></p>";

	strProposalString += "</td>";
	strProposalString += "<a name=" + strRecipient + "></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr>";
	strProposalString += "<td width=56 style='width:41.7pt;border:none;";

	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>"+FIELD_REFERER;
	strProposalString += "</p></td>";
	strProposalString += "<td width=623  style='width:467.2pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "</td>";
	strProposalString += "<a name=" + strReferer + "></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr style='height:9.0pt'>";
	strProposalString += "<td width=56 style='width:41.7pt;border:none;border-bottom:solid windowtext 2.25pt;";
	strProposalString += "padding:0cm 0cm 0cm 0cm;height:9.0pt'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>"+ FIELD_TITLE;
	strProposalString += "</td>";
	strProposalString += "<td width=623 valign=top style='width:467.2pt;border-top:none;border-left:none;";
	strProposalString += "border-bottom:solid windowtext 2.25pt;border-right:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm'>";
	strProposalString += "</td>";
	strProposalString += "<a name=" + strTitle + "></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr style='height:53.75pt'>";
	strProposalString += "<td width=679 colspan=2 valign=top style='width:508.95pt;border:none;";
	strProposalString += "mso-border-top-alt:solid windowtext 2.25pt;";
	strProposalString += "padding:0cm 4.95pt 0cm 4.95pt;height:42.25pt'>";
	strProposalString += "<p class=MsoNormal>";

	strProposalString += "</p></td>";
	strProposalString += "<a name=" + strBody + "></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr>";
	strProposalString += "<td width=679 colspan=2 valign=top style='width:508.95pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm;height:30.0pt'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>";
	strProposalString += "</p></td>";
	strProposalString += "<a name=" + strSenderAs + "></a>";
	strProposalString += "</tr>";

	strProposalString += "<tr>";
	strProposalString += "<td width=679 colspan=2 valign=top style='width:508.95pt;border:none;";
	strProposalString += "padding:0cm 0cm 0cm 0cm;height:9.0pt'>";
	strProposalString += "<p class=MsoNormal align=center style='text-align:center'>";
	strProposalString += "</p></td>";
	strProposalString += "<a name=" + strRecipientRefer + "></a>";
	strProposalString += "</tr>";

	strProposalString += "</table>";
	strProposalString += "</body></html>";
//	strProposalString = strHtmlString + strProposalString;

	var strFilePath = parent.getDownloadPath() + "tempHtmlProposal.doc";

	parent.FileTransfer.SaveToLocalFile(strFilePath, strProposalString);
/*
	var strFileName = getAttachFileName(getBodyFileObj());
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
*/

	var myRange = g_DocWord.Content;
	myRange.Collapse(0);
	myRange.insertfile(strFilePath,"",false);

	increaseBodyCount();

	return true;
}


function removeDocument(nIndex)
{
	var bIsForm = isFormData();
	if (bIsForm == false)
		return false;

	var nBody = nIndex+1;
	var strTempTitle = FIELD_BODY + nBody;

	if (g_DocWord.Bookmarks.Exists(strTempTitle))
	{
		g_DocWord.Tables(nBody).Select();
		g_AppWord.Selection.TypeBackspace();
	}

	var nLoop = nIndex+2;
	var nProposal = parent.getBodyCount()+1;
	while(nLoop < nProposal)
	{
		var strTempProposal = PRE_PROPOSAL +(nLoop)+ PROPOSAL;
		var strTempRecipient = FIELD_RECIPIENT + nLoop;
		var strTempReferer = FIELD_REFERER + nLoop;
		var strTempTitle = FIELD_TITLE + nLoop;
		var strTempBody = FIELD_BODY + nLoop;

		var strProposal = PRE_PROPOSAL + (nLoop-1) + PROPOSAL;
		var strRecipient = FIELD_RECIPIENT + (nLoop-1);
		var strReferer = FIELD_REFERER + (nLoop-1);
		var strTitle = FIELD_TITLE + (nLoop-1)
		var strBody = FIELD_BODY + (nLoop-1);


		if (g_DocWord.Bookmarks.Exists(strTempProposal))
		{
			var myRange = g_DocWord.Bookmarks(strTempProposal).Range.Cells(1).Range;
			g_DocWord.Bookmarks(strTempProposal).Delete();
			g_DocWord.Bookmarks.Add(strProposal, myRange);
			myRange.Delete(1,1);
			myRange.Text = "("+strProposal+")";
		}

		if (g_DocWord.Bookmarks.Exists(strTempRecipient))
		{
			var myRange = g_DocWord.Bookmarks(strTempRecipient).Range.Cells(1).Range;
			g_DocWord.Bookmarks(strTempRecipient).Delete();
			g_DocWord.Bookmarks.Add(strRecipient, myRange);
		}

		if (g_DocWord.Bookmarks.Exists(strTempReferer))
		{
			var myRange = g_DocWord.Bookmarks(strTempReferer).Range.Cells(1).Range;
			g_DocWord.Bookmarks(strTempReferer).Delete();
			g_DocWord.Bookmarks.Add(strReferer, myRange);
		}

		if (g_DocWord.Bookmarks.Exists(strTempTitle))
		{
			var myRange = g_DocWord.Bookmarks(strTempTitle).Range.Cells(1).Range;
			g_DocWord.Bookmarks(strTempTitle).Delete();
			g_DocWord.Bookmarks.Add(strTitle, myRange);
		}

		if (g_DocWord.Bookmarks.Exists(strTempBody))
		{
			var myRange = g_DocWord.Bookmarks(strTempBody).Range.Cells(1).Range;
			g_DocWord.Bookmarks(strTempBody).Delete();
			g_DocWord.Bookmarks.Add(strBody, myRange);
		}

		nLoop++;

	}
	decreaseBodyCount(nIndex);

	return true;

}

function editDocument(bModify)
{
	var strFileName = getAttachFileName(getBodyFileObj());

	if (strFileName != "")
	{
		var strLocalPath = g_strDownloadPath + strFileName;

		if (g_strEditType == "1")
		{
			if (bModify == true) 		//문서 수정button을 눌렀을때
			{
				g_nType = 1;
				g_ProcessCase = 2;
				clearApproveFlow();
				g_DocWord.Save();
				g_bSaved = false;
			}
			else						//수정완료, 수정 취소 button을 눌렀을 때
			{
				g_nType = 0;
				g_ProcessCase = 3;

				if (g_bSaved == true)
				{
					clearApproveFlow();
					g_DocWord.Save();
				}
			}
		}

		//수정완료 또는 수정 취소 button을 눌렀을 때
		else if (g_strEditType == "0")
		{
			g_nType = 0;
			g_ProcessCase = 3;

			if (g_bIsModified == true)
			{
				clearApprovalFlow();
				g_DocWord.Save();
			}
		}

		openDocument(strLocalPath);
	}
	else
		NewDocument();

	return true;
}


function saveApproveDocument(bIncludeSign)
{
	var strTitle = getTitle(1) + ".doc";

//	g_DocWord.SaveAs(g_strDownloadPath + strTitle);

	g_DocWord.Activate();
	g_AppWord.Dialogs(84).Show();
}

function saveApproveDocumentAs(strFilePath, bIncludeSign)
{
}

function setFormDataToXml()
{
	var bIsForm = isFormData();
	if (bIsForm == false)
		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (g_DocWord.Bookmarks.Exists(strFieldName) == true)
		{
			var strSubject = readTempRecordText(strFieldName);
			if (strSubject.length > 128)
				strSubject = strSubject.substring(0, 128)

			setTitle(strSubject, nIndex);
		}

		nIndex++;
	}
/*
	if (g_DocWord.ProtectionType == 1)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = readTempRecordText(FIELD_DOC_NUMBER);

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
				nNumberType= 2;
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

	if (g_DocWord.Bookmarks.Exists(FIELD_CONSERVE_TERM) == true)
	{
		var strConserveTerm = readTempRecordText(FIELD_CONSERVE_TERM);
		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0,4)

		setDraftConserve(strConserveTerm);
	}
	else
	{
		var strConserveTerm = getDraftConserve();

		if (strConserveTerm.length > 4)
			strConserveTerm = strConserveTerm.substring(0, 4);

		setDraftConserve(strConserveTerm);
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_PUBLIC_BOUND) == true)
	{
		var strPublicBound = readTempRecordText(FIELD_PUBLIC_BOUND);

		if (strPublicBound.length > 4)
			strPublicBound = strPublicBound.substring(0, 4)

		setPublicLevel(strPublicBound);
	}
	else
	{
		var strPublicBound = getPublicLevel();

		if (strPublicBound.length > 4)
			strPublicBound = strPublicBound.substring(0, 4)

		setPublicLevel(strPublicBound);
	}

	if (g_ProcessCase == 0 && g_strEditType != 1)
	{
		if (g_DocWord.ProtectionType == -1)
			g_DocWord.Protect(1);
	}
*/
}

function setXmlDataToForm()
{
	var strFieldList = "", strValueList = "";

	var bIsForm = isFormData();
	if (bIsForm == false)
		return;

	var nIndex = 1;
	var nBodyCount = getBodyCount();

	while(nIndex <= nBodyCount)
	{
		var strFieldName = FIELD_TITLE;
		if (nIndex > 1)
			strFieldName += nIndex;

		if (g_DocWord.Bookmarks.Exists(strFieldName) == true)
		{
			strSubject = getTitle(nIndex);
			putFieldText(strFieldName, strSubject);
		}

		nIndex++;
	}

	if (g_DocWord.ProtectionType == 1)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && strSerialNumber != "0")
			strDocNumber += "-" + strSerialNumber;

		if (strDocNumber != readTempRecordText(FIELD_DOC_NUMBER))
		{
			strFieldList += FIELD_DOC_NUMBER + "\u0002";
			strValueList += strDocNumber + "\u0002";
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_CONSERVE_TERM) == true)
	{
		strConserveTerm = getDraftConserve();
		if (strConserveTerm != readTempRecordText(FIELD_CONSERVE_TERM))
		{
			strFieldList += FIELD_CONSERVE_TERM + "\u0002";
			strValueList += strConserveTerm + "\u0002";
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_PUBLIC_BOUND) == true)
	{
//		strPublicBound = getPublicLevel();
		var strPublicBound = getPublication(getPublicLevel());

		if (strPublicBound != readTempRecordText(FIELD_PUBLIC_BOUND))
		{
			strFieldList += FIELD_PUBLIC_BOUND + "\u0002";
			strValueList += strPublicBound + "\u0002";
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_ENFORCE_DATE) == true)
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
		var strDate = "";
		if (strEnforceDate != "")
		{
			strDate = getDateDisplay(strEnforceDate, "YYYY.MM.DD.");

/*
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
*/
		}

/*		if (strEnforceDate == "")
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
*/

		if (strEnforceDate != readTempRecordText(FIELD_ENFORCE_DATE))
		{
//			strFieldList += "&FIELD_ENFORCE_DATE;\u0002";
//			strValueList += strEnforceDate + "\u0002";
			if (strDate != "")
			{
				strDate = "(" + strDate + ")";
			}

			strFieldList += FIELD_ENFORCE_DATE + "\u0002";
			strValueList += strDate + "\u0002";
		}
	}


	if (g_DocWord.Bookmarks.Exists(FIELD_RECEIVE_DATE) == true)
	{
		var strReceiveDate = getReceiveDate(); //nodeDocFlowInfo.selectSingleNode("receive_date").text;
		var strDate = "", strTime = "";

		if (strReceiveDate != "")
		{
			strDate = getDateDisplay(strReceiveDate, "YYYY.MM.DD.");
			strTime = getTimeDisplay(strReceiveDate);
		}

		strFieldList += FIELD_RECEIVE_DATE + "\u0002";
		if (strDate != "")
			strDate = "(" + strDate + ")";
		strValueList += strDate + "\u0002";

/*		var nFind = strReceiveDate.indexOf(" ");
		if (nFind != -1)
		{
			strReceiveTime = strReceiveDate.substring(nFind);
			strReceiveDate = strReceiveDate.substring(0, nFind);
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


		strFieldList += FIELD_RECEIVE_DATE + "\u0002";
		strValueList += strReceiveDate + "\u0002";
*/
		if (g_DocWord.Bookmarks.Exists(FIELD_RECEIVE_TIME) == true)
		{
			strFieldList += FIELD_RECEIVE_TIME + "\u0002";
			strValueList += strTime + "\u0002";
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_RECEIVE_NUMBER) == true)
	{
		var strReceiveNumber = getReceiveNumber();
		if (strReceiveNumber != "0")
		{
			strFieldList += FIELD_RECEIVE_NUMBER + "\u0002";
			strValueList += strReceiveNumber + "\u0002";
		}
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_PROCESS_DEPT) == true)
	{
		var strProcessDept = getReceiveDeptName();
		strFieldList += FIELD_PROCESS_DEPT + "\u0002";
		strValueList += strProcessDept + "\u0002";
	}

	if (g_DocWord.Bookmarks.Exists(FIELD_INSTRUCTION) == true)
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

	if (g_DocWord.Bookmarks.Exists(FIELD_INSTRUCTION_ITEM) == true)
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

	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);

		showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
		putFieldText(strFieldList, strValueList);
		hideWaitDlg();
	}
}

function readTempRecordText(strApprovalField)
{
	var strRecordText = "";
	if (g_DocWord.ProtectionType == 1)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(strApprovalField) == true)
	{
		if (g_bIsFormData == true)
		{
			if (strApprovalField.indexOf(FIELD_BODY) != -1)
			{
				var nProposal = getBodyCount();
				var nTable = g_DocWord.Bookmarks(FIELD_BODY + nProposal).Range.Tables.Count;
				if (nTable == 0)
				{
					strRecordText = g_DocWord.Bookmarks(FIELD_BODY + g_CaseNumber).Range.Paragraphs(1).Range.Text;
				}
				else
				{
					var startloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
					var endloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
					strRecordText = g_DocWord.Range(startloc, endloc-1).Text;
				}
			}

			else
			{
				var startloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
				var endloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
				strRecordText = g_DocWord.Range(startloc, endloc-1).Text;
			}
		}

		else
			strRecordText = g_DocWord.Bookmarks(strApprovalField).Range.Text;
	}
	return strRecordText;
}

function getFormInfo()
{
	var strFormInfo = "";

	if (g_bIsFormData == false)
		return strFormInfo;

	if (g_DocWord.Bookmarks.Exists(FIELD_DRAFTER + "1")==true)
	{
		var nSubmit = 0;
		while(1)
		{
			var strField = FIELD_DRAFTER + (nSubmit+1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nSubmit++;
		}

		var nApprove = 0;
		while(1)
		{
			var strField = FIELD_CONSIDER + (nApprove + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nApprove++;
		}

		var nAssist = 0;
		while(1)
		{
			var strField = FIELD_COOPERATE + (nAssist + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nAssist++;
		}

		var nComplete = 0;
		while(1)
		{
			var strField = FIELD_LAST + (nComplete + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nComplete++;
		}

// 2003.04.02 for 연합캐피탈
		var nInspect = 0;
		while(1)
		{
			var strField = FIELD_DEPART_INSPECT + (nInspect + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nInspect++;
		}

		strFormInfo = nSubmit + "-" + nApprove + "-" + nAssist + "-" + nComplete;

	}
	else if (g_DocWord.Bookmarks.Exists(FIELD_DRAFTER + "1_1") == true)
	{
		var nType = 1;
		while(1)
		{
			var nSubmit = 0;
			while(1)
			{
				var strField = FIELD_DRAFTER + nType + "_" + (nSubmit + 1);
				if (g_DocWord.Bookmarks.Exists(strField) == false)
					break;

				nSubmit++;
			}

			var nApprove = 0;
			while(1)
			{
				var strField = FIELD_CONSIDER + nType + "_" + (nApprove + 1);
				if (g_DocWord.Bookmarks.Exists(strField) == false)
					break;

				nApprove++;
			}

			var nAssist = 0;
			while(1)
			{
				var strField = FIELD_COOPERATE + nType + "_" + (nAssist + 1);
				if (g_DocWord.Bookmarks.Exists(strField) == false)
					break;

				nAssist++;
			}

			var nComplete = 0;
			while(1)
			{
				var strField = FIELD_LAST + nType + "_" + (nComplete + 1);
				if (g_DocWord.Bookmarks.Exists(strField) == false)
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
	else if (g_DocWord.Bookmarks.Exists(FIELD_CHARGER) == true)
	{
		var nCharger = 0;
		{
			var strField = FIELD_CHARGER;
			if (g_DocWord.Bookmarks.Exists(strField) == true)
				nCharger++;
		}

		var nPrior = 0;
		while(1)
		{
			var strField = FIELD_PRIOR + (nPrior + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nPrior++;
		}

		var nCircular = 0;
		while(1)
		{
			var strField = FIELD_PUBLIC + (nCircular + 1);
			if (g_DocWord.Bookmarks.Exists(strField) == false)
				break;

			nCircular++;
		}

		strFormInfo = nCharger + "-" + nPrior + "-" + nCircular;
	}

	return strFormInfo;
}

function printDocument()
{
	var strDocName = g_DocWord.Name;
	var strTempUrl = parent.getDownloadPath() + "viewdoc1" + ".doc";

	if (confirm(CONFIRM_PRINT_DOCUMENT) == false)
		return;

	if (g_DocWord.SaveAs(strTempUrl) == false)
		return false;

	if (g_DocWord.Save() == false)
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

	if (confirm(CONFIRM_PRINT_DOCINFO) == true)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePrintDocInfo.jsp";
		window.open(strUrl,"Approve_ViewDocDetail","scrollbars=yes, toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
//	g_bPrinted = true;
}

function openFile()
{
	if (g_DocWord.ProtectionType != -1)
		g_DocWord.UnProtect();

	var myRange = g_DocWord.Content;
	var StartPos = myRange.Start;
	var EndPos = myRange.End;
	myRange.Select();


	if (g_AppWord.Dialogs(164).Show() == -1)
	{
		myRange.Delete(1,-1);

		g_bIsFormData = isFormData();

		if (g_bIsFormData == true)
		{
			g_DocWord.PageSetup.LineNumbering.Active = false;
			g_DocWord.PageSetup.Orientation = 0;
			g_DocWord.PageSetup.LeftMargin = g_AppWord.CentimetersToPoints(2);
			g_DocWord.PageSetup.TopMargin = g_AppWord.CentimetersToPoints(1.59);
			g_DocWord.PageSetup.RightMargin = g_AppWord.CentimetersToPoints(1.5);
			g_DocWord.PageSetup.BottomMargin = g_AppWord.CentimetersToPoints(1);
			var nIndex = 2;
			var strField = FIELD_TITLE + nIndex;
			while (g_DocWord.Bookmarks.Exists(strField) == true)
			{
//				parent.increaseBodyCount();
				nIndex++;
				strField = FIELD_TITLE + nIndex;
			}

			setFormDataToXml();

//			restoreApproveFlow();
			var nPara = g_DocWord.Paragraphs.Count;
			g_DocWord.Paragraphs(nPara).Range.Delete();
		}
	}
	else
	{
		var rcovRange = g_DocWord.Range(EndPos-1, EndPos-1);
		rcovRange.Select();
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
}

function displayArbitProxy(objManInfo)
{
	var strFieldName;
	var strRole = objManInfo.selectSingleNode("var_man_role").text;

	if (strRole == "6" && g_DocWord.Bookmarks.Exists(FIELD_REAL_NAME_ARBIT))
		strFieldName = FIELD_REAL_NAME_ARBIT;
	else if (strRole == "7" && g_DocWord.Bookmarks.Exists(FIELD_REAL_NAME_PROXY))
		strFieldName = FIELD_REAL_NAME_PROXY;
	else if ((strRole == "6" || strRole == "7") && g_DocWord.Bookmarks.Exists(FIELD_REAL_NAME))
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

	putFieldText(strFieldName, strValue);
}

function setPriorOpinion(strOpinion)
{
	if (g_DocWord.ProtectionType == 1)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(FIELD_INSTRUCTION) == true)
		putFieldText(FIELD_INSTRUCTION, strOpinion);

	else if (g_DocWord.Bookmarks.Exists(FIELD_INSTRUCTION_ITEM) == true)
		putFieldText(FIELD_INSTRUCTION_ITEM, strOpinion);

	if (g_DocWord.ProtectionType == -1)
		g_DocWord.Protect(1);
}

function updateDocumentPostSendField()
{
	// 문서번호, 시행일자
	var strFieldList = "";
	var strValueList = "";

	if (g_DocWord.Bookmarks.Exists(FIELD_DOC_NUMBER) == true)
	{
		var strDocNumber = getOrgSymbol() + getClassNumber();
		var strSerialNumber = getSerialNumber();
		if (strSerialNumber != "" && parseInt(strSerialNumber) > 0)
			strDocNumber += "-" + strSerialNumber;

		strFieldList += FIELD_DOC_NUMBER + "\u0002";
		strValueList += strDocNumber + "\u0002";
	}
	if (g_DocWord.Bookmarks.Exists(FIELD_ENFORCE_DATE) == true)
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

		showWaitDlg(MESSAGE_APPROVE, MESSAGE_WAIT_MOMENT);
		putFieldText(strFieldList, strValueList);
		hideWaitDlg();
	}
}

function onSetViewScale(bExpand)
{
	var nFactor = 50;
	if (bExpand)
		nFactor = 100;

	g_DocWord.ActiveWindow.View.Zoom.Percentage = nFactor;
	g_EnforceDoc.ActiveWindow.View.Zoom.Percentage = nFactor;
	//Document_Hun2KLa.SetZoomFactor(nFactor);
	//Enforce_Hun2KLa.SetZoomFactor(nFactor);
}
// icaha, document switching
onBodyAccessLoadingCompleted();
