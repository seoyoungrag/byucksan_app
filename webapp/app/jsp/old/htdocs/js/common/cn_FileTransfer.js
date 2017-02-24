
function getDownloadPath()
{/*
<<<<<<< .mine
	alert('cn_FileTransfer.strDownloadPath : ' + strDownloadPath);
	
	var strDownloadPath = FileTransfer.GetModulePath();
	
	if (strDownloadPath != "")	// 아래는 데모를 위한 임시 코딩
//	if (strDownloadPath != "" && strDownloadPath.indexOf("WebApp") != -1)
		strDownloadPath += "Temp\\";
	else
		strDownloadPath = "C:\\Program Files\\WebApp\\Temp\\";

=======*/
	var strDownloadPath = "C:\\Users\\jyd\\AppData\\Local\\Temp\\APPTEMP\\";
//>>>>>>> .r791
	return strDownloadPath;
}

function setConnectInfo(strUserID, strPassword)
{
	FileTransfer.SetConnectInfo(strUserID, strPassword);
}

function createDownloadPath(strDownloadPath)
{
	return FileTransfer.CreateLocalDirectory(strDownloadPath);
}

function deleteDownloadPath(strDownloadPath)
{
	return FileTransfer.DeleteLocalDirectory(strDownloadPath);
}

function deleteServerDirectory(strUploadUrl)
{
	return FileTransfer.DeleteServerDirectory(strUploadUrl);
}

function openFolderDialog(strDefaultFolder)
{
	return FileTransfer.OpenFolderDialog(strDefaultFolder);
}

function uploadFile(strLocalPath, strServerUrl)
{
	return FileTransfer.UploadFile(strLocalPath, strServerUrl);
}

function downloadFile(strServerUrl, strLocalPath)
{
	return FileTransfer.DownloadFile(strServerUrl, strLocalPath);
}

function showWaitDlg(strTitle, strMessage)
{
	var bRet = FileTransfer.ShowWaitDlg(strTitle, strMessage);
	return bRet;
}

function hideWaitDlg()
{
	return FileTransfer.HideWaitDlg();
}

function showProgressDlg()
{
	return FileTransfer.ShowProgressDlg();
}

function hideProgressDlg()
{
	return FileTransfer.HideProgressDlg();
}

function clearFileInfo()
{
	return FileTransfer.ClearFileInfo();
}

function addTransFileSize(strFileSize)
{
	return FileTransfer.AddTransFileSize(parseInt(strFileSize));
}

function addLocalFileInfo(strLocalPath)
{
	return FileTransfer.AddLocalFileInfo(strLocalPath);
}

function addServerFileInfo(strServerUrl)
{
	return FileTransfer.AddServerFileInfo(strServerUrl);
}

function getLocalFileSize(strLocalPath)
{
	return FileTransfer.GetLocalFileSize(strLocalPath);
}

function openLocalFile(strLocalPath)
{
	return FileTransfer.OpenLocalFile(strLocalPath);
}

function editLocalFile(strLocalPath)
{
	return FileTransfer.EditLocalFile(strLocalPath);
}

function getGUID()
{
	return FileTransfer.GetGUID();
}

function openFileDialog(strDelimiter)
{
	return FileTransfer.OpenFileDialog(strDelimiter);
}

function saveFileDialog(strFileName, strFilter)
{
	return FileTransfer.SaveFileDialog(strFileName, strFilter);
}

function checkModifyAttach()
{
	return FileTransfer.CheckModifyAttach();
}

function saveToLocalFile(strFilePath, strData)
{
	return FileTransfer.SaveToLocalFile(strFilePath, strData);
}

function saveToLocalUTF8(strFilePath, strData)
{
	return FileTransfer.SaveToLocalUTF8(strFilePath, strData);
}

function createLocalFile(strFilePath)
{
	return FileTransfer.CreateLocalFile(strFilePath);
}

function readLocalFile(strFilePath)
{
	return FileTransfer.ReadLocalFile(strFilePath);
}

function copyLocalFile(strLocalPath, strDownloadPath)
{
	return FileTransfer.CopyLocalFile(strLocalPath, strDownloadPath);
}

function setKeepConnect(bKeepConnect)
{
	FileTransfer.KeepConnect = bKeepConnect;
}

function getLocalFileSizeInKB(strLocalPath)
{
	var nByteSize = getLocalFileSize(strLocalPath);
	var nFileSize = 0;

	if (nByteSize == 0)
		nFileSize = 0;
	else if (nByteSize < 512)
		nFileSize = 1;
	else
		nFileSize = Math.round(nByteSize/1024).toString();

	return nFileSize;
}

function renameServerFile(strOldUrl, strNewUrl)
{
	return FileTransfer.RenameServerFile(strOldUrl, strNewUrl);
}
