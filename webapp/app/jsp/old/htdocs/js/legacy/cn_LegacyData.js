function loadLocalLegacyDataFile()
{
	// not implemented
}

function fillLegacyDataField()
{
	var strInputMethod = getInputConfigInputMethod();
	var strInputFormat = getInputConfigInputFormat();
	if (strInputMethod == 0 || strInputMethod == 1)
	{
		var strLegacyXML = getLegacyDataText(getBusinessInfo(getLegacySystem()));
		if (strLegacyXML.indexOf("<?xml") == 0)
			strLegacyXML = strLegacyXML.substring(strLegacyXML.indexOf("?>") + 2, strLegacyXML.length);
		var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
		objXMLDom.async = false;
		objXMLDom.loadXML(strLegacyXML);
		if (objXMLDom != null)
		{
			if (strInputFormat == "0" || strInputFormat == "1")
				setLegacyXMLToForm(objXMLDom.documentElement);
			else
				setLegacyXMLToForm(objXMLDom.selectSingleNode("Body").getFirstChild());
		}
	}
	else if (strInputMethod == "2")
	{
		if (strInputFormat == "0" || strInputFormat == "1" || strInputFormat == "2" || strInputFormat == "3")
		{
			var strLegacyFile = getInputConfigLegacyFile();
			var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
			objXMLDom.async = false;
			objXMLDom.load(strLegacyFile);
			if (objXMLDom != null)
			{
				if (strInputFormat == "0" || strInputFormat == "1")
					setLegacyXMLToForm(objXMLDom.documentElement);
				else
					setLegacyXMLToForm(objXMLDom.selectSingleNode("Body").getFirstChild());
			}
		}
		else		// 4:text, 5:text(parse)
		{
			// not implemented
		}
	}
}
