//var g_objPubDoc = new ActiveXObject("MSXML.DOMDocument");
//g_objPubDoc.async = false;

var g_objPubDoc = null;

function resetPubDoc()
{
//	g_objPubDoc.load(g_strBaseUrl + "htdocs/js/pubdoc/pubdoc.xml");
	g_objPubDoc = new ActiveXObject("MSXML.DOMDocument");
	g_objPubDoc.async = false;
}

function setPudDocElementData(strXPath, strText)
{
	var objNode = g_objPubDoc.selectSingleNode("pubdoc/" + strXPath);
	if (objNode != null)
		objNode.text = strText;
}

function createPubDocElement(strName)
{
	return g_objPubDoc.createElement(strName);
}

function getPubDocElement(strXPath)
{
	return g_objPubDoc.selectSingleNode("pubdoc/" + strXPath);
}

function savePubDocXMLWithBody(strPubDocXMLPath, strBodyXMLPath)
{
	var strPubDocXML = "<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n" + 
					"<!DOCTYPE pubdoc SYSTEM \"pubdoc.dtd\">\n" +
					"<?xml-stylesheet type=\"text/xsl\" href=\"siheng.xsl\"?>\n" +
					g_objPubDoc.xml;
	var nPubDocBodyStartPos = strPubDocXML.indexOf("<body");
	var nPubDocBodyEndPos = strPubDocXML.lastIndexOf("body>") + 5;

	var strBodyXML = readLocalFile(strBodyXMLPath);
	var nBodyStartPos = strBodyXML.indexOf("<body");
	var nBodyEndPos = strBodyXML.lastIndexOf("body>") + 5;
	var strBodyElementXML = strBodyXML.substring(nBodyStartPos, nBodyEndPos);

	strPubDocXML = strPubDocXML.substring(0, nPubDocBodyStartPos) +
				strBodyElementXML +
				strPubDocXML.substring(nPubDocBodyEndPos, strPubDocXML.length);
	saveToLocalFile(strPubDocXMLPath, strPubDocXML);
	return true;
}

function convertFromPubDoc(strPubDocXMLPath)
{

}

function extractPubPack()
{
	var objPubPack = getExtendAttachBySubDiv(1, "PubPack");
	var strFileName = getAttachFileName(objPubPack);
	var strPubDocDir = createDownloadPath(g_strDownloadPath + "pubdoc\\");
	unpack(strFileName, strPubDocDir);
}

function removeAllChildNode(objNode)
{
	var nodeChild = objNode.firstChild;

	while (nodeChild != null)
	{
		var nodeNext = nodeChild.nextSibling;

		if (nodeChild.hasChildNodes == true)
//		if (nodeChild.childNodes.length > 0)
			removeAllChildNode(nodeChild);

		objNode.removeChild(nodeChild);
		nodeChild = nodeNext;
	}
}

function copyXMLNode(nodeOrigin, nodeDest)
{
	var nodeChild = nodeOrigin.firstChild;
	while (nodeChild != null)
	{
		var nodeNew = null;

		if (nodeChild.nodeTypeString == "element")
		{
			nodeNew = nodeDest.ownerDocument.createNode(1, nodeChild.nodeName, "");

			var nLength = nodeChild.attributes.length;
			var i = 0;
			while (i < nLength)
			{
				var objAttribute = nodeChild.attributes(i);
				nodeNew.setAttribute(objAttribute.nodeName, objAttribute.text);
				i++;
			}
		}
		else if (nodeChild.nodeTypeString == "text" || nodeChild.nodeTypeString == "cdatasection")
		{
			nodeNew = nodeDest.ownerDocument.createTextNode(nodeChild.nodeValue);
		}

		if (nodeNew != null)
		{
			nodeDest.appendChild(nodeNew);

			if (nodeChild.hasChildNodes == true)
				copyXMLNode(nodeChild, nodeNew);
		}

		nodeChild = nodeChild.nextSibling;
	}
}
