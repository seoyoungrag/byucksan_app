
var g_bIsModified = false;

function onLoadDocument()
{
	alert('cn_ApproveHunSynopsis.js');
	loadDocument();
}

function onUnloadDocument()
{
	closeDocument();
}

function loadDocument()
{
	Document_Hun2KLa.SetEdit(1);
	Document_Hun2KLa.SetToolbar(1);
	Document_Hun2KLa.SetCopy(1);
	Document_Hun2KLa.SetPrint(1);

	g_bIsModified = true;

	if (g_strDataUrl != "")
	{
		var strFileName = g_strDataUrl;

		var strLocalPath = g_strDownloadPath + strFileName;
		Document_Hun2KLa.OpenDocument(strLocalPath, 0);
	}
	else
	{
		Document_Hun2KLa.NewDocument();
	}

	Document_Hun2KLa.SetZoomFactor(4);
	Document_Hun2KLa.focus();
	onDocumentOpen();
}

function closeDocument()
{
	Document_Hun2KLa.CloseDocument();
}

function getFileExtension()
{
	return "gul";
}

function onOpenFile()
{
	Document_Hun2KLa.OpenDocumentWithDialog();
}

function onSaveFile()
{
	var strTitle = "요약전";

	if (strTitle.indexOf("\\") != -1 || strTitle.indexOf("/") != -1 || strTitle.indexOf(":") != -1 ||
		strTitle.indexOf("*") != -1 || strTitle.indexOf("?") != -1 || strTitle.indexOf("\"") != -1 ||
		strTitle.indexOf("&lt;") != -1 || strTitle.indexOf("&gt;") != -1 || strTitle.indexOf("|") != -1)
	{
		strTitle = "";
	}

	Document_Hun2KLa.SaveDocumentWithDialogEx(strTitle, "Hunmin");
}

function onPrint()
{
	Document_Hun2KLa.PrintDocWithDialog();
}

function saveSynopsis(strFilePath)
{
	return Document_Hun2KLa.SaveAsDocument(strFilePath, 0);
}
