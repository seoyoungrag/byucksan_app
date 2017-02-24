
var g_strExchangeBodyText = "";

function onConvertAdminXmlToDocXml()
{
	showWaitDlg();
	
	var objXml = new ActiveXObject("MSXML.DOMDocument");
	objXml.async = false;

//	var strAdminXmlPath = g_strDownloadPath + "Exchange.xml";
	var strAdminXmlPath = g_strDownloadPath + g_strExchDocFileName;
	objXml.load(strAdminXmlPath);
	
	var objXml_copy = new ActiveXObject("MSXML.DOMDocument");
	objXml_copy.async = false;
	objXml_copy.load(strAdminXmlPath);

	// insert body to text editor.
//	insertBodyToTextBody(objXml);
//	setModified(true);

	var objBody = objXml.selectSingleNode("EXCHANGE/BODY");
	if (objBody != null)
		g_strExchangeBodyText = objBody.text;

	var objXsl = new ActiveXObject("MSXML.DOMDocument");
	var strUrl = g_strBaseUrl + "mis/xsl/CN_MisConvertAdminXmlToDocXml.xsl";
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
				var strLocalPath = g_strDownloadPath + strFileName;
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
				var strFileName = objGeneralAttach(nIndex).selectSingleNode("FILE_NAME").text;
				var strLocalPath = g_strDownloadPath + strFileName;
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

		// fill User info.
		onFillPersonInfo(strXml);
	}
	else
		alert(ALERT_ERROR_CONVERT_TO_DOCXML);

}

function onFillPersonInfo(strXml)
{
	dataForm.xmlData.value = strXml;
	dataForm.returnFunction.value = "onReceiveUserInfo"

	dataForm.action = "./mis/org/CN_MisFillPersonInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onReceiveUserInfo(bSuccess)
{
//	alert(ApprovalDoc.xml);

	if (bSuccess)
	{
		// update ApprovalDoc

		var objConvertedXml = dataTransform.ApprovalDoc;
		ApprovalDoc.loadXML(objConvertedXml.documentElement.xml);

		setDraftOrgCode(g_strUserOrgCode);
		setDraftOrgName(g_strUserOrgName);
		setDraftProcDeptCode(g_strUserDeptCode);
		setDraftProcDeptName(g_strUserDeptName);

		setAccessLevel(g_strUserDeptName);
		setAccessLevelCode(g_strUserDeptCode);

		uploadAttachFilesInPack();
		
		hideWaitDlg();
	}
	else
	{
		var objConvertedXml = dataTransform.ApprovalDoc;
		ApprovalDoc.loadXML(objConvertedXml.documentElement.xml);

		setDraftOrgCode(g_strUserOrgCode);
		setDraftOrgName(g_strUserOrgName);
		setDraftProcDeptCode(g_strUserDeptCode);
		setDraftProcDeptName(g_strUserDeptName);

		uploadAttachFilesInPack();

		hideWaitDlg();
		alert(ALERT_ERROR_RECEIVING_USER_INFO);
		onSetApproveLine();
	}

	onChangeReceiveForm();
//	alert(ApprovalDoc.xml);
}

// insert body to text editor
function insertBodyToTextBody(objExchangeXml)
{
	var objBody = objExchangeXml.selectSingleNode("EXCHANGE/BODY")
	if (objBody != null)
	{
		var strBody = objBody.text;
		var objTextBox = document.getElementById("TextBody");
		objTextBox.value = strBody;
		objTextBox.readOnly = false;
	}
}

function setDocumentBody()
{
	setLinkedApprovalBody(g_strExchangeBodyText);
}


function onTestConvertAdminXmlToDocXml()
{
	try
	{
	var objXml = new ActiveXObject("MSXML.DOMDocument");
	objXml.async = false;
	objXml.load("c:\\exchange\\Exchange(샘플2).xml");
	
	var objXml_copy = new ActiveXObject("MSXML.DOMDocument");
	objXml_copy.async = false;
	objXml_copy.load("c:\\exchange\\Exchange(샘플2).xml");

	var objXsl = new ActiveXObject("MSXML.DOMDocument");
	var strUrl = g_strBaseUrl + "mis/xsl/CN_MisConvertAdminXmlToDocXml.xsl";
	objXsl.async = false;
	objXsl.load(strUrl);

	var strResult = objXml.transformNode(objXsl);

	if(strResult != "")
	{
		objXml.loadXML(strResult);

		// insert RELATED_ATTACHES file size
		var objRelatedAttaches = objXml.selectSingleNode("//RELATED_ATTACHES");
		if (objRelatedAttaches != null)
		{
			var objRelatedAttach = objRelatedAttaches.selectNodes("RELATED_ATTACH");

			for (nIndex = 0; nIndex < objRelatedAttach.length; nIndex++)
			{
				var strFileName = objRelatedAttach(nIndex).selectSingleNode("FILE_NAME").text;
//				var strLocalPath = g_strDownloadPath + strFileName;
				var strLocalPath = "c:\\exchange\\" + strFileName;
				objRelatedAttach(nIndex).selectSingleNode("FILE_SIZE").text = getLocalFileSize(strLocalPath);
//				objRelatedAttach(nIndex).setAttribute("METHOD", "1");
			}
		}


		// insert GENERAL_ATTACHES file size
		var objGeneralAttaches = objXml.selectSingleNode("//GENERAL_ATTACHES");
		if (objGeneralAttaches != null)
		{
			var objGeneralAttach = objGeneralAttaches.selectNodes("GENERAL_ATTACH");

			for (nIndex = 0; nIndex < objGeneralAttach.length; nIndex++)
			{
				var strFileName = objGeneralAttach(nIndex).selectSingleNode("FILE_NAME").text;
//				var strLocalPath = g_strDownloadPath + strFileName;
				var strLocalPath = "c:\\exchange\\" + strFileName;
				objGeneralAttach(nIndex).selectSingleNode("FILE_SIZE").text = getLocalFileSize(strLocalPath);
//				objGeneralAttach(nIndex).setAttribute("METHOD", "2");
			}
		}

		// insert Related System.

		var objOrigin = objXml_copy.documentElement;
		var objTarget = objXml.selectSingleNode("//RELATED_SYSTEM/SYSTEM_DATA/EXCHANGE");
		copyXMLNode(objOrigin, objTarget);

		var strXml = toXMLString(objXml);
		// fill User info.
		onTestFillPersonInfo(strXml);
	}
	else
		alert(ALERT_ERROR_CONVERT_TO_DOCXML);
	}
	catch(e)
	{
		alert(e.description);
	}

}

function onTestFillPersonInfo(strXml)
{

	dataForm.xmlData.value = strXml;
	dataForm.returnFunction.value = "onTestReceiveUserInfo"

	dataForm.action = "./mis/org/CN_MisFillPersonInfo.jsp";
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onTestReceiveUserInfo(bSuccess)
{
	if (bSuccess)
	{
		// update ApprovalDoc

		var objConvertedXml = dataTransform.ApprovalDoc;
		var strApprovalDoc = objConvertedXml.documentElement.xml;

		testInsertBodyToTextBody(objConvertedXml.documentElement);
		// alert(isApproverCloseLine());

		hideWaitDlg();
	}
	else
	{
		alert(ALERT_ERROR_RECEIVING_USER_INFO);
		hideWaitDlg();
	}
}
function testInsertBodyToTextBody(objExchangeXml)
{
	var strBody = objExchangeXml.xml
	var objTextBox = document.getElementById("TextBody");
	objTextBox.value = strBody;
	objTextBox.readOnly = false;
}

