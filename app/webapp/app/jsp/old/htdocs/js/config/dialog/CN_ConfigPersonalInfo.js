function onClickModifyImage(strType)
{
	var strUrl = g_strBaseUrl + "config/dialog/CN_ConfigChangeImage.jsp?type=" + strType;
	window.open(strUrl, "Config_ChangeImage", "toolbar=no, resizable=no, status=yes, width=300, height=200");
}

function onClickModifySignType()
{
	var objRadio = document.getElementsByName("rdSignType");
	var nSignType = 2;
	for (var i = 0; i < objRadio.length; ++i)
	{
		if (objRadio[i].checked == true)
		{
			nSignType = i;
			break;
		}
	}
	var strUrl = g_strBaseUrl + "config/document/CN_ConfigModifySignType.jsp?signtype=" + nSignType;
	loadHiddenPage(strUrl);
}

function onClickModifyPassword()
{
	var objOldPW = document.getElementById("idOldPW");
	var objNewPW = document.getElementById("idNewPW");
	var objConfirmPW = document.getElementById("idConfirmPW");
	if (objNewPW.value != objConfirmPW.value)
	{
		msgAgent(ALERT_CONFRIM_NEW_PASSWORD);
		return;
	}
	var strOldPW = objOldPW.value;
	if (strOldPW != "")
		strOldPW = encodeValue(strOldPW);
	var strNewPW = objNewPW.value;
	if (strNewPW != "")
		strNewPW = encodeValue(strNewPW);
	var strUrl = g_strBaseUrl + "config/document/CN_ConfigModifyPassword.jsp?oldpw=" + strOldPW + "&newpw=" + strNewPW;
	loadHiddenPage(strUrl);
	objOldPW.value = "";
	objNewPW.value = "";
	objConfirmPW.value = "";
}

function setUserImage(strFilePath, strType)
{
	if (checkFileSize(strFilePath, strType))
	{
		var strFileName = "";
		var strFileExt = "";
		var nFind = strFilePath.lastIndexOf("\\");
		if (nFind != -1)
			strFileName = strFilePath.substring(nFind + 1);

		var nFind = strFileName.lastIndexOf(".");
		if (nFind != -1)
			strFileExt = strFileName.substring(nFind);

		strFileExt = strFileExt.toLowerCase();
		if ((strFileExt == ".bmp") || (strFileExt == ".jpg") || (strFileExt == "jpeg"))
		{
			var strGUID = getGUID();
			var strGUIDFileName = getFileName(strGUID, strFileExt)
			var strUploadUrl = g_strUploadUrl + strGUIDFileName;
			if (!uploadFile(strFilePath, strUploadUrl))
			{
				msgAgent(ALERT_FAIL_TO_UPLOAD_FILE);
				return;
			}

			var strUrl = g_strBaseUrl + "config/document/CN_ConfigModifyImage.jsp?type=" + strType + "&filename=" + strGUIDFileName + "&keyurl=" + g_strKeyUrl;
			loadHiddenPage(strUrl);
		}
		else
		{
			msgAgent(ALERT_NOT_SUPPORT_FILE_FORMAT);
			return;
		}
	}
}

function checkFileSize(strFilePath, strType)
{
	var nLocalFileSize = 0;
	var nLimitedFileSize = 0;
	nLocalFileSize = parseInt(getLocalFileSizeInKB(strFilePath));
	if (strType == "IMAGE")
		nLimitedFileSize = LIMITED_IMAGE_SIZE;
	else
		nLimitedFileSize = LIMITED_SEAL_SIZE;

	if (nLocalFileSize > nLimitedFileSize)
	{
		var strMsg = "";
		strMsg = ALERT_OVERFLOW_SIZE + "\n\n" + ALERT_IMAGE_SIZE + LIMITED_IMAGE_SIZE + FILE_SIZE;
		strMsg = strMsg + "\n\n" + ALERT_SEAL_SIZE + LIMITED_SEAL_SIZE + FILE_SIZE;
		msgAgent(strMsg);
		return false;
	}
	else
		return true;
}

function getFileName(strFileName, strFileExt)
{
	var strGUIDFileName = strFileName + strFileExt;
	return strGUIDFileName;
}

function changeImage(strType, strFileName)
{
	var strID = "id" + strType;
	var objImage = document.getElementById(strID);
	objImage.src = g_strHttpUploadUrl + strFileName;
}

function loadHiddenPage(strUrl)
{
	var objHidden = document.getElementById("idHidden");
	objHidden.src = strUrl;
}

function msgAgent(strMsg)
{
	alert(strMsg);
}

function onVerifyFingerPrint()
{
	var ret = FPAuthorizer.CheckDevice();

	if (ret == -1)
	{
		alert(ALERT_CANNOT_FIND_FPAUTHORIZER_DEVICE);
		return;
	}
	var strFileName = document.getElementById("idUseFP").getAttribute("filename");
	var strServerUrl = g_strUploadUrl + strFileName;
	var strLocalPath = getDownloadPath() + strFileName;
//alert(strFileName);
	if (strFileName == "")
	{
		alert(ALERT_NOT_FIND_REGIST_FINGERPRINT);
//		enrollFingerPrint();
		return;
	}
	if (downloadFile(strServerUrl, strLocalPath) == false)
	{
		alert(ALERT_CANNOT_CONFIRM_FINGERPRINT);
		return;
	}
	var nResult = FPAuthorizer.ReadStreamFromFile(strLocalPath);
	if (nResult != 1)
	{
		alert(ALERT_CANNOT_CONFIRM_FINGERPRINT);
		return;
	}
	var ret = FPAuthorizer.VerifyFingerPrint();
	if (ret != 0)
	{
		alert(ALERT_CANNOT_CONFIRM_FINGERPRINT);
		return;
	}

//	alert(ALERT_CONFIRM_FINGERPRINT);
	if (document.getElementById("idUseFP").checked == false)
	{
		setFPInfo("0");
	}
	else
	{
//		if (confirm(CONFIRM_MODIFY_FINGERPRINT))
//			enrollFingerPrint();
//		else
			setFPInfo("1");
	}
}

function enrollFingerPrint()
{
	var strFileName = document.getElementById("idUseFP").getAttribute("filename");
	var strLocalPath = getDownloadPath() + strFileName;

	var ret = FPAuthorizer.EnrollFingerPrint(g_strUserID);
	if (ret == -1)
		return;
	FPAuthorizer.SaveStreamToFile(strLocalPath);

	var strServerUrl = g_strUploadUrl + strFileName;

	if (HttpTransfer1.UploadFile(strLocalPath, strServerUrl) == false)
		return;

	var strUrl = g_strBaseUrl + "config/document/CN_ConfigModifyFingerPrint.jsp?type=register&filename=" + strFileName;
	loadHiddenPage(strUrl);
}

function setFPInfo(strValue)
{
	var strFPIsUsed = document.getElementById("idUseFP").getAttribute("isUsed");
alert('setFPInfo: ' + strFPIsUsed);
	if (strFPIsUsed == strValue)
		return;

	var strFileName = document.getElementById("idUseFP").getAttribute("filename");
	var strType = "";
	if (strValue == "0")
		strType = "delete";
	else
		strType = "update";
	var strUrl = g_strBaseUrl + "config/document/CN_ConfigModifyFingerPrint.jsp?type=" + strType + "&filename=" + strFileName;
	loadHiddenPage(strUrl);
}

function msgAgentAndReload(strMsg)
{
	alert(strMsg);
	document.location.reload();
}
