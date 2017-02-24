var g_strExchDocFileName = "";

function onUnPack(strExchPackFileName)
{
	var objPubDocConverter = new ActiveXObject("PubDocConverter.Converter");
	var strWorkingFolder = g_strDownloadPath;

	objPubDocConverter.FileFolder = strWorkingFolder;
	objPubDocConverter.GetExchDocAdjuncts(strWorkingFolder);
	objPubDocConverter.GetExchPackAdjuncts(strWorkingFolder);
	objPubDocConverter.LoadExchPackDocFile(strExchPackFileName);
	objPubDocConverter.DoUnpackExchPack();
	g_strExchDocFileName = objPubDocConverter.ExchDocFileName;
}

function onPack(strApprovalDocXml, strExchangeDocXml, strType)
{
	// strType
	// 상신:submit, 반송:return, 결재:approval

	if (strType == "")
	{
		alert("Couldn't get 'transmit type'");
		return;
	}

	var objPubDocConverter = new ActiveXObject("PubDocConverter.Converter");
	var strWorkingFolder = g_strDownloadPath;

	objPubDocConverter.FileFolder = strWorkingFolder;
	objPubDocConverter.GetExchDocAdjuncts(strWorkingFolder);
	objPubDocConverter.GetExchPackAdjuncts(strWorkingFolder);

	var objPubExdoc = new ActiveXObject("PubDocConverter.ExchDocument");
	objPubExdoc.FileFolder = strWorkingFolder;
	objPubExdoc.LoadFromXML(strExchangeDocXml);
//	objPubExdoc.SaveToXMLFile("exchange.xml");
	objPubExdoc.SaveToXMLFile("notification.xml");

	objPubDocConverter.LoadApprovalDocXML(strApprovalDocXml);
//	objPubDocConverter.LoadExchDocFile("exchange.xml");
	objPubDocConverter.LoadExchDocFile("notification.xml");

	var strExchPackFileName = objPubDocConverter.DoPackExchPack("1", strType);

	return strExchPackFileName;
}

function onPackHeaderOnly(strType)
{
	// strType
	// 상신:submit, 반송:return, 결재:approval

	if (strType == "")
	{
		alert("Couldn't get 'transmit type'");
		return;
	}

	var strSystemID = "GovernmentMisSystemExchangeXml";
	var objRelatedSystem = getRelatedSystemBySystemID(strSystemID);

	// SENDER
	var objOriginSender = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/SENDER");
	var strReceiver = objOriginSender.selectSingleNode("SERVERID").text;
	var strReceiverUserID = objOriginSender.selectSingleNode("USERID").text;
		
	// RECEIVER
	var objOriginReceiver = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/RECEIVER");
	var strSender = objOriginReceiver.selectSingleNode("SERVERID").text;
	var strSenderUserID = objOriginReceiver.selectSingleNode("USERID").text;
	
	var strDate = getCurrentDate("-") + " " + getCurrentTime(":");
	var strSenderOrgName = g_strUserOrgName;
	var strSenderSystemName = "ACUBE Communication for Gov 5.0";
	var strAdministrativeNum = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/ADMINISTRATIVE_NUM").text;

	var objPubExdoc = new ActiveXObject("PubDocConverter.ExchPackDocument");

	objPubExdoc.Date = strDate;
	objPubExdoc.Sender = strSender;
	objPubExdoc.Receiver = strReceiver;
	objPubExdoc.SenderUserID = strSenderUserID;
	objPubExdoc.ReceiverUserID = strReceiverUserID;
	objPubExdoc.SenderOrgName = g_strUserOrgName;			//	DRAFT_ORG_NAME
	objPubExdoc.SenderSystemName = "ACUBE Communication for Gov 5.0";
	objPubExdoc.AdministrativeNum = strAdministrativeNum;
	
	var strExchPackFileName = strReceiverUserID + strSender + strReceiver + getCurrentDate("") + getCurrentTime("") + getRandNum() + ".xml";

	objPubExdoc.FileName = strExchPackFileName;
	objPubExdoc.DocType = strType;
	
	objPubExdoc.SaveToXMLFile(g_strDownloadPath + strExchPackFileName);

	return strExchPackFileName;
}

function getRandNum()
{

	var strResult = "";
	var strRandNum = "" + parseInt(Math.random() * 100000);
	var strZero = "00000";
	var nLen = 5 - strRandNum.length;
	strRandNum = strZero.substring(0, nLen) + strRandNum;
	return strRandNum;
}

function downloadAndUnpackAdminMisExchPackFile()
{
	showWaitDlg(MESSAGE_WAIT, MESSAGE_WAIT_MOMENT);

	var objExchPack = getExtendAttachBySubDiv("", "ExchPack");
	if (objExchPack != null)
	{
		var strExchPackFileName = getAttachFileName(objExchPack);

		if (strExchPackFileName != "")
			downloadFile(g_strUploadUrl + strExchPackFileName, g_strDownloadPath + strExchPackFileName);

		onUnPack(strExchPackFileName);
	}
	else
	{
		alert("No ExtendAttach - 'ExchPack' Node");
	}

	hideWaitDlg();
}

function uploadAttachFilesInPack()
{
	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var strFileName = getAttachFileName(objRelatedAttach);
		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			var strServerUrl = g_strUploadUrl + strFileName;
			uploadFile(strLocalPath, strServerUrl);
		}

		objRelatedAttach = getNextRelatedAttach(objRelatedAttach);
	}

	var objGeneralAttach = getFirstGeneralAttach();
	while (objGeneralAttach != null)
	{
		var strFileName = getAttachFileName(objGeneralAttach);
		if (strFileName != "")
		{
			var strLocalPath = g_strDownloadPath + strFileName;
			var strServerUrl = g_strUploadUrl + strFileName;
			uploadFile(strLocalPath, strServerUrl);
		}

		objGeneralAttach = getNextGeneralAttach(objGeneralAttach);
	}

}

function downloadGeneralAttaches()
{
	var objGeneralAttach = getFirstGeneralAttach();
	while (objGeneralAttach != null)
	{
		var strFileName = getAttachFileName(objGeneralAttach);
		var strFileDisplayName = getAttachDisplayName(objGeneralAttach);
		var strExtension = getExtention(strFileName);
		if (strFileName != "")
		downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileDisplayName + strExtension);

		objGeneralAttach = getNextGeneralAttach(objGeneralAttach);
	}

}

function getExtention(strFile)
{
	var nPos = strFile.lastIndexOf(".");
	return strFile.substring(nPos);
}

function onPack2(strApprovalDocXml, strExchangeDocXml, strType)
{
	// strType
	// 상신:submit, 반송:return, 결재:approval

	if (strType == "")
	{
		alert("Couldn't get 'transmit type'");
		return;
	}

//	try
//	{
		var objPubDocConverter = new ActiveXObject("PubDocConverter.Converter");
		var strWorkingFolder = "c:\\exchange\\";

		objPubDocConverter.FileFolder = strWorkingFolder;
		objPubDocConverter.GetExchDocAdjuncts(strWorkingFolder);
		objPubDocConverter.GetExchPackAdjuncts(strWorkingFolder);

		var objPubExdoc = new ActiveXObject("PubDocConverter.ExchDocument");
		objPubExdoc.FileFolder = strWorkingFolder;
		objPubExdoc.LoadFromXML(strExchangeDocXml);
		objPubExdoc.SaveToXMLFile("exchange.xml");

		objPubDocConverter.LoadApprovalDocXML(strApprovalDocXml);
		objPubDocConverter.LoadExchDocFile("exchange.xml");

		var strExchPackFileName = objPubDocConverter.DoPackExchPack("1", strType);

		return strExchPackFileName;
//	}
//	catch(e)
//	{
//		alert(e.description);
//	}
}



function onMakeTestPackFile1()
{
	onConstructExchangeToDocSys();
}

function onMakeTestPackFile2()
{
	var strExchangeXml = onTestConvertDocXmlToAdminXml();

	var strExchPackFileName = onPack2(ApprovalDoc.documentElement.xml, strExchangeXml, "send");
	alert(strExchPackFileName);
}
