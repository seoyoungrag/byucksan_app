function onConstructExchangeToDocSys()
{
	try
	{
		var objXml = new ActiveXObject("MSXML.DOMDocument");
		objXml.async = false;

		g_strDownloadPath = "c:\\exchange\\";
		var strApprovalDocPath = g_strDownloadPath + "c:\\exchange\\ApprovalDoc.xml";
		objXml.load(strApprovalDocPath);

		var objXml_copy = new ActiveXObject("MSXML.DOMDocument");
		objXml_copy.async = false;
		objXml_copy.load(strApprovalDocPath);
	
		var objXsl = new ActiveXObject("MSXML.DOMDocument");
		var strUrl = g_strBaseUrl + "mis/xsl/CN_MisConstructExchangeToDocSys.xsl";
		objXsl.async = false;
		objXsl.load(strUrl);
	
		var strResult = objXml.transformNode(objXsl);
		alert(strResult);
	}
	catch(e)
	{
		alert(e.description);
	}
}