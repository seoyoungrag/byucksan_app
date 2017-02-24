// EXCHANGE.XML[TO_DOC] --> APPROVALDOC
var g_strLocalToMakeTestPack = "c:\\Exchange_ToDocSys\\";
function onMakePackStart()
{
	try
	{
		var strLocalFolder = g_strLocalToMakeTestPack;

		var objXml = new ActiveXObject("MSXML.DOMDocument");
		objXml.async = false;
		objXml.load(strLocalFolder + "Exchange_Input.xml");

		var objXml_copy = new ActiveXObject("MSXML.DOMDocument");
		objXml_copy.async = false;
		objXml_copy.load(strLocalFolder + "Exchange_Input.xml");

		var objXsl = new ActiveXObject("MSXML.DOMDocument");
		var strUrl = g_strBaseUrl + "mis/xsl/CN_MisMakeStartPack.xsl";
		objXsl.async = false;
		objXsl.load(strUrl);

		var strResult = objXml.transformNode(objXsl);

		if(strResult != "")
		{
			objXml.loadXML(strResult);

			// insert DOC_ID
		
			objXml.selectSingleNode("//DOC_INFO/DOC_ID").text = g_strDataUrl;
		
			// insert RELATED_ATTACHES file size
			var objRelatedAttaches = objXml.selectSingleNode("//RELATED_ATTACHES");
			if (objRelatedAttaches != null)
			{
				var objRelatedAttach = objRelatedAttaches.selectNodes("RELATED_ATTACH");

				for (nIndex = 0; nIndex < objRelatedAttach.length; nIndex++)
				{
					var strFileName = objRelatedAttach(nIndex).selectSingleNode("FILE_NAME").text;
					var strLocalPath = strLocalFolder + strFileName;
					objRelatedAttach(nIndex).selectSingleNode("FILE_SIZE").text = getLocalFileSize(strLocalPath);
				}
			}


			// insert GENERAL_ATTACHES file size
			var objGeneralAttaches = objXml.selectSingleNode("//GENERAL_ATTACHES");
			if (objGeneralAttaches != null)
			{
				var objGeneralAttach = objGeneralAttaches.selectNodes("GENERAL_ATTACH");

				for (nIndex = 0; nIndex < objGeneralAttach.length; nIndex++)
				{
					objGeneralAttach(nIndex).setAttribute("MODIFY","1");
					var strFileName = objGeneralAttach(nIndex).selectSingleNode("FILE_NAME").text;
					var strLocalPath = strLocalFolder + strFileName;
					objGeneralAttach(nIndex).selectSingleNode("FILE_SIZE").text = getLocalFileSize(strLocalPath);
				}
			}

			// Set Current UserID into original EXCHANGE (틀린 정보가 들어갈수 있음)

			var objReceiver = objXml_copy.documentElement.selectSingleNode("HEADER/COMMON/RECEIVER");
			var objUserId = objReceiver.selectSingleNode("USERID");
			var objEmail = objReceiver.selectSingleNode("EMAIL");
		
			if (objUserId != null)
				objUserId.text = g_strUserID;
			if (objEmail != null)
				objEmail.text = "";

			// insert Related System.

			var objOrigin = objXml_copy.documentElement;
			var objTarget = objXml.selectSingleNode("//RELATED_SYSTEM/SYSTEM_DATA/EXCHANGE");
			copyXMLNode(objOrigin, objTarget);

			var strXml = toXMLString(objXml);
alert(strXml);
			// fill User info.
			onMakePackFillPersonInfo(strXml);
		}
		else
			alert(ALERT_ERROR_CONVERT_TO_DOCXML);
	}
	catch(e)
	{
		alert(e.description);
	}

}

function onMakePackFillPersonInfo(strXml)
{

	dataForm.xmlData.value = strXml;
	dataForm.returnFunction.value = "onMakePackReceiveUserInfo"

	dataForm.action = "./mis/org/CN_MisFillPersonInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onMakePackReceiveUserInfo(bSuccess)
{
	if (bSuccess)
	{
		// update ApprovalDoc

		var strApprovalDoc = dataTransform.ApprovalDoc.documentElement.xml;
		var strLocalFolder = g_strLocalToMakeTestPack;

		var objXml = new ActiveXObject("MSXML.DOMDocument");
		objXml.async = false;
		objXml.load(strLocalFolder + "Exchange_Input.xml");

		var strExchageXml = objXml.documentElement.xml;

		onMakePackInsertBodyToTextBody(strApprovalDoc);

		var strExchPackFileName = onMakePackFile(strApprovalDoc, strExchageXml, "send");

		hideWaitDlg();

		alert(strExchPackFileName);
	}
	else
	{
		alert(ALERT_ERROR_RECEIVING_USER_INFO);
		hideWaitDlg();
	}
}
function onMakePackInsertBodyToTextBody(strApprovalDoc)
{
	var objTextBox = document.getElementById("TextBody");
	objTextBox.value = strApprovalDoc;
	objTextBox.readOnly = false;
}

function onMakePackFile(strApprovalDocXml, strExchangeDocXml, strType)
{
	// strType
	// 상신:submit, 반송:return, 결재:approval

	if (strType == "")
	{
		alert("Couldn't get 'transmit type'");
		return;
	}

	try
	{
		var objPubDocConverter = new ActiveXObject("PubDocConverter.Converter");
		var strWorkingFolder = g_strLocalToMakeTestPack;

		objPubDocConverter.FileFolder = strWorkingFolder;
		objPubDocConverter.GetExchDocAdjuncts(strWorkingFolder);
		objPubDocConverter.GetExchPackAdjuncts(strWorkingFolder);

		var objPubExdoc = new ActiveXObject("PubDocConverter.ExchDocument");
		objPubExdoc.FileFolder = strWorkingFolder;
		objPubExdoc.LoadFromXML(strExchangeDocXml);
		objPubExdoc.SaveToXMLFile("Exchange.xml");

		objPubDocConverter.LoadApprovalDocXML(strApprovalDocXml);
		objPubDocConverter.LoadExchDocFile("Exchange.xml");

		var strExchPackFileName = objPubDocConverter.DoPackExchPack("1", strType);

		return strExchPackFileName;
	}
	catch(e)
	{
		alert(e.description);
	}
}
