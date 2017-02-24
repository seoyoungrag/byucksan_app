var g_strEditType = "0";

function onLoadDocument()
{
		alert('cn_ApproveHwp2002Synopsis.js');
	if (Document_HwpCtrl.object == null)
	{
		alert(MSG_HWPCTRL_NOT_INSTALLED);
		return;
	}

	if (!verifyHwpCtrlVersion(Document_HwpCtrl))
		return;

	initToolbar(Document_HwpCtrl);
	showToolbar(Document_HwpCtrl, 1);

	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function loadDocument()
{
	// 한글 OCX 보안 다이얼로그가 나타나지 않도록 합니다.
	Document_HwpCtrl.RegisterModule("FilePathCheckDLL", "HwpLocalFileAccess");

	if (g_strDataUrl != "")
	{
		var strFileName = g_strDataUrl;
		var strLocalPath = g_strDownloadPath + strFileName;
		Document_HwpCtrl.Open(strLocalPath, "", "lock:FALSE;versionwarning:TRUE");
	}
	else
	{
		Document_HwpCtrl.Clear(1);
		Document_HwpCtrl.CreateField(MSG_INSERT_SYNOPSIS_HERE, TITLE_SYNOPSIS, TITLE_SYNOPSIS);
	}

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
	var strFilePath = HWPUtil.OpenHwpFileDialog(true, "");
	if (strFilePath == "")
		return;

	Document_HwpCtrl.Open(strFilePath, "", "lock:FALSE;versionwarning:TRUE");
}

function onSaveFile()
{
	var strTitle = TITLE_SYNOPSIS;

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	var strFilePath = HWPUtil.OpenHwpFileDialog(false, strTitle);
	if (strFilePath == "")
		return;

	Document_HwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
}

function onPrint()
{
	Document_HwpCtrl.Run("Print");
}

function saveSynopsis(strFilePath)
{
	return Document_HwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
}
