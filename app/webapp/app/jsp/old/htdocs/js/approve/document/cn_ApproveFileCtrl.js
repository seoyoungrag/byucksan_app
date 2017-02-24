
function downloadDefaultFile()
{
	showWaitDlg(MESSAGE_WAIT, MESSAGE_WAIT_MOMENT);
//	setKeepConnect(true);

	if (g_strDownloadPath != getDownloadPath())
		g_strDownloadPath = getDownloadPath();

	var objBodyFile = getBodyFileObj();
	if (objBodyFile != null)
	{
		var strFileName = getAttachFileName(objBodyFile);
		if (strFileName != "")
			downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName);
	}

	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var strFileName = getAttachFileName(objRelatedAttach);
		if (strFileName != "")
			downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName);

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}

	if (g_strFormUrl != "")
		downloadFile(g_strUploadUrl + g_strFormUrl, g_strDownloadPath + g_strFormUrl);

	if (g_strLogoName != "")
	{
		if (downloadFile(g_strUploadUrl + g_strLogoName, g_strDownloadPath + g_strLogoName))
		{
			var nFileSize = getLocalFileSize(g_strDownloadPath + g_strLogoName);
			var objRelatedAttach = addRelatedAttachInfo("Logo", g_strLogoName, nFileSize, "");

			setAttachClassify(objRelatedAttach, "OrganImage");
			setAttachSubDiv(objRelatedAttach, "Logo");
		}
	}

	if (g_strSymbolName != "")
	{
		if (downloadFile(g_strUploadUrl + g_strSymbolName, g_strDownloadPath + g_strSymbolName))
		{
			var nFileSize = getLocalFileSize(g_strDownloadPath + g_strSymbolName);
			var objRelatedAttach = addRelatedAttachInfo("Symbol", g_strSymbolName, nFileSize, "");

			setAttachClassify(objRelatedAttach, "OrganImage");
			setAttachSubDiv(objRelatedAttach, "Symbol");
		}
	}

	if (g_strSignUrl != "")
		downloadFile(g_strUploadUrl + g_strSignUrl, g_strDownloadPath + g_strSignUrl);

//	setKeepConnect(false);
	hideWaitDlg();
}

function getUploadUrl()
{
	return g_strUploadUrl;
}

function downloadEnforceFile(strCaseNumber)
{
	var objEnforceFile = getEnforceFileObj(strCaseNumber);
	if (objEnforceFile == null)
		return false;

	var strFileName = getAttachFileName(objEnforceFile);
	if (strFileName != "")
	{
		if (getLocalFileSize(g_strDownloadPath + strFileName) == 0)
		{
			if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == 0)
				return false;
		}
	}

	var objExtendAttach = getFirstExtendAttach();

	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			strFileName = getAttachFileName(objExtendAttach);
			if (strFileName != "")
			{
				if (getLocalFileSize(g_strDownloadPath + strFileName) == 0)
				{
					if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == 0)
						return false;
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function getStoreFile(strFileName, strLocation)
{
	dataForm.xmlData.value = strFileName;
	dataForm.extendData.value = strLocation;
	dataForm.returnFunction.value = "onGetStoreFileCompleted";
	dataForm.action = "./common/jsp/CN_GetStoreFile.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onGetStoreFileCompleted(nType, strFileName)
{
	if (nType == true)
	{
		doViewModifiedLine(strFileName);
	}
	else
		alert(ALERT_NOTFOUND_PREMODIFIED_LINE);
}

function extractStoreFile(strFileName, strLocation)
{
	dataForm.xmlData.value = strFileName;
	dataForm.extendData.value = strLocation;
	dataForm.returnFunction.value = "onExtractStoreFileCompleted";
	dataForm.action = "./common/jsp/CN_GetStoreFile.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onExtractStoreFileCompleted(nType, strFileName)
{
	if (nType == true)
	{
		if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == false)
		{
			alert(ALERT_NOTFOUND_PREMODIFIED_DOCUMENT);
			return;
		}

		openLocalFile(g_strDownloadPath + strFileName);
	}
	else
		alert(ALERT_NOTFOUND_PREMODIFIED_DOCUMENT);
}

function downloadStoreFile(strFileName, strLocation)
{
	dataForm.xmlData.value = strFileName;
	dataForm.extendData.value = strLocation;
	dataForm.returnFunction.value = "onDownloadStoreFileCompleted";
	dataForm.action = "./common/jsp/CN_GetStoreFile.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onDownloadStoreFileCompleted(nType, strFileName)
{
//	if (nType == true)
//	else
}
