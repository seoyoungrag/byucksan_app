var g_bHideParent = true;
var g_nSenderAsFontSize = 18;
var g_bSendEnforceHold = false;

function onHwpDocumentOpen()
{
	if (g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17")
	{
		createEnforceSide();

		setFrameScale(2);
		onLoadEnforceDocument();
	}
}

function setHwpWindowScale(nType)
{
	moveHwpDocumentWindow();
	moveHwpEnforceWindow();
	if (nType == 0)
	{
		setHwpDocumentVisible(true);
		setHwpEnforceVisible(false);
	}
	else if (nType == 1)
	{
		setHwpDocumentVisible(false);
		setHwpEnforceVisible(true);
	}
	else if (nType == 2)
	{
		setHwpDocumentVisible(true);
		setHwpEnforceVisible(true);
	}
}

function onCloseHwp(bBody)
{
	if (bBody != true)
		bBody = false;
	if (typeof(parent) == "object" && parent.location.href != window.location.href)
	{
		window.location.href = "about:blank";
//		onUnload();
	}
	else
	{
		if (!bBody)
			Document_HWPProxy2.CloseDocument();
		window.close();
	}
}

function getValue(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return "";
	else
		return arr[key];
}

function isExistKey(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return false;
	else
		return true;
}
/*
function getDateDisplay(strDateTime)
{
	var nFind = strDateTime.indexOf(" ");
	var strDate;
	if (nFind != -1)
		strDate = strDateTime.substring(0, nFind);
	else
		strDate = strDateTime;

	var arrDate = strDate.split("-");
	if (g_strOption36 == "YYYY.MM.DD")
		strDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
	else if (g_strOption36 == "YYYY/MM/DD")
		strDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];
	else if (g_strOption36 == "YYYY-MM-DD")
		strDate = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];
	else if (g_strOption36 == "MM.DD")
		strDate = arrDate[1] + "." + arrDate[2];
	else if (g_strOption36 == "MM/DD")
		strDate = arrDate[1] + "/" + arrDate[2];
	else if (g_strOption36 == "MM-DD")
		strDate = arrDate[1] + "-" + arrDate[2];
	else
		strDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
	return strDate;
}

function getTimeDisplay(strDateTime)
{
	var nFind = strDateTime.indexOf(" ");
	var strTime;
	if (nFind != -1)
		strTime = strDateTime.substring(nFind + 1, strDateTime.length);
	else
		strTime = strDateTime;

//	var arrDate = strDate.split(":");
	return strTime;
}
*/