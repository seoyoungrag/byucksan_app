var g_bHwpViewCreated = false;
var g_bModified = false;
var g_bHwpViewModify;
var g_bHwpViewOpen = false;
var g_strChiefGrade = "";
var g_strHwpViewTitle = "";
var g_bPageLoad = false;

function getDocumentMenuSet()
{
	var strMenuSet = document.getElementById("hwpMenuSet").value;
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
	alert('cn_ApproveHwpSynopsis.js');
/*
	HWPView2.attachEvent("OnCreated", onHwpViewCreated);
	HWPView2.attachEvent("OnHWPNotFound", onHwpNotFound);
	HWPView2.attachEvent("OnHWPCommand", onHwpViewCommand);
*/
	g_bPageLoad = true;
	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function onHwpViewCreated()
{
	if (g_bHwpViewCreated == true)
		return;
	g_bHwpViewCreated = true;

	loadDocument();
}

function onHwpNotFound()
{
}

function loadDocument()
{
	if (g_bHwpViewCreated == false || g_bPageLoad == false)
		return;
/*
	if (g_bHwpViewCreated == false)
		return;
*/
	var bReplace = g_bHwpViewOpen;
	g_bHwpViewModify = true;
	var nEditMode = (g_bHwpViewModify ? 2 : 1);
	var nMenuSetID = 2;
	var nWindowPos = 0;

	g_bIsModified = true;

	g_strHwpViewTitle = TITLE_EDIT_SYNOPSIS;

	if (g_strDataUrl != "")
	{
		var strFileName = g_strDataUrl;

		var strLocalPath = g_strDownloadPath + strFileName;
		if (getLocalFileSize(strLocalPath) > 0)
		{
			HWPView2.OpenDocumentEx(g_strHwpViewTitle, strLocalPath, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
		}
		else
		{
			alert(ALERT_FIND_DOCUMENT_ERROR);
			return;
		}
	}
	else
	{
		HWPView2.NewDocumentEx(g_strHwpViewTitle, bReplace, nEditMode, nMenuSetID, getDocumentMenuSet(), nWindowPos, g_bHideParent);
	}

//	HWPView2.SetModified(0);
	HWPView2.SetVisible(true);
	HWPView2.SetDocumentViewRatio("width","");
	HWPView2.Focus();
	g_bHwpViewOpen = true;
	onDocumentOpen();
}

function closeDocument()
{
}

function getFileExtension()
{
	return "hwp";
}

function onOpenFile()
{
	HWPView2.OpenDocumentWithDialog();
}

function onSaveFile()
{
	var strTitle = TITLE_SYNOPSIS;

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("<") != -1 || strTitle.indexOf(">") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	var strFilePath = HWPView2.OpenHwpFileDialog(false, strTitle);
	if (strFilePath == "")
		return;

	HWPView2.SaveDocument(strFilePath, false);
}

function onPrint()
{
	HWPView2.PrintDocument();
}

function saveSynopsis(strFilePath)
{
	return HWPView2.SaveDocument(strFilePath, false);
}

function onHwpViewCommand(nCommandID)
{
	switch (nCommandID)
	{
	case 24:	// 상세정보
		onViewDocDetail();
		break;
	case 46:	// 의견조회
		if (g_strEditType == "16")
			onViewExamOpinion();	// 심사자 의견조회
		else
			onViewOpinion();
		break;
	case 53:	// 첨부
		onAttachFiles();
		break;
	case 58:	// 내려받기
		onSaveFile();
		break;
	case 59:	// 인쇄
		onPrint();
		break;
	case 60:	// 닫기
		HWPView2.Disconnect();
		window.close();
		break;
	case 61:	// 수정완료
		onSynopsisComplete(true);
		window.close();
		break;
	case 62:	// 수정취소
		break;
	case 65:	// 열기
		onOpenFile();
		break;
	}
}
