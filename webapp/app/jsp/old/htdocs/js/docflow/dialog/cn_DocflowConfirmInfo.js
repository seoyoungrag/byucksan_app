function drawRecipientInfo(strType)
{
	var objRecipientInfo = document.getElementById("idRecipientInfo");

	var nBodyCount = 1;
	if (g_strOpenLocale == "DOCUMENT")
		nBodyCount = opener.getBodyCount();
	else
		nBodyCount = getBodyCount();

	var strRecipientInfo = "";
	var strRecipientInfoByProposal = "";

	var strRecipientDeptName = "";
	var strRecipientDeptCode = "";
	var strRecipientRefDeptName = "";
	var strRecipientRefDeptCode = "";
	var strRecipientReceiptStatus = "";
	var strRecipientReceiptStatusString = "";
	var strRecipientAcceptor = "";
	var strRecipientAcceptDate = "";

	var objRecipientGroup = null;
	var objRecipient = null;

	var nRecipientCount = 0;

	for (var i = 1; i <= nBodyCount; ++i)
	{
		strRecipientInfoByProposal = "";
		if (g_strOpenLocale == "DOCUMENT")
			objRecipientGroup = opener.getRecipGroup(i);
		else
			objRecipientGroup = getRecipGroup(i);

		if (objRecipientGroup != null)
		{
			if (g_strOpenLocale == "DOCUMENT")
				objRecipient = opener.getFirstRecipient(objRecipientGroup);
			else
				objRecipient = getFirstRecipient(objRecipientGroup);

			while (objRecipient != null)
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strRecipientDeptName = opener.getRecipientDeptName(objRecipient);
					strRecipientDeptCode = opener.getRecipientDeptCode(objRecipient);
					strRecipientRefDeptName = opener.getRecipientRefDeptName(objRecipient);
					strRecipientRefDeptCode = opener.getRecipientRefDeptCode(objRecipient);
					strRecipientReceiptStatus = opener.getRecipientReceiptStatus(objRecipient);
					strRecipientReceiptStatusString = opener.getRecipientReceiptStatusString(objRecipient);
					strRecipientAcceptor = opener.getRecipientAcceptor(objRecipient);
					strRecipientAcceptDate = opener.getRecipientAcceptDate(objRecipient);
					objRecipient = opener.getNextRecipient(objRecipient);
				}
				else
				{
					strRecipientDeptName = getRecipientDeptName(objRecipient);
					strRecipientDeptCode = getRecipientDeptCode(objRecipient);
					strRecipientRefDeptName = getRecipientRefDeptName(objRecipient);
					strRecipientRefDeptCode = getRecipientRefDeptCode(objRecipient);
					strRecipientReceiptStatus = getRecipientReceiptStatus(objRecipient);
					strRecipientReceiptStatusString = getRecipientReceiptStatusString(objRecipient);
					strRecipientAcceptor = getRecipientAcceptor(objRecipient);
					strRecipientAcceptDate = getRecipientAcceptDate(objRecipient);
					objRecipient = getNextRecipient(objRecipient);
				}

				strRecipientAcceptDate = getDateDisplay(strRecipientAcceptDate, "YYYY-MM-DD");

				if (g_strVersion == "3.5")
				{
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TR style='cursor:default;background:#f2f8ff;' class='ltb_center";
				}
				else
				{
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TR style='cursor:default;background:#f7f7f7;' class='ltb_center";
				}
				if (strType == "CALLBACK")
				{
					if ((strRecipientReceiptStatus == "") || (strRecipientReceiptStatus == "2"))
					{
						strRecipientInfoByProposal = strRecipientInfoByProposal + "' name='selected' onclick='onListClick();' onmousemove='onListMouseMove();' onkeydown='onListKeyDown();' id='Index" + nRecipientCount;
						strRecipientInfoByProposal = strRecipientInfoByProposal + "' casenum='" + i + "' deptcode='" + strRecipientDeptCode + "' refdeptcode='" + strRecipientRefDeptCode;
						nRecipientCount += 1;
					}
				}
				strRecipientInfoByProposal = strRecipientInfoByProposal + "'>";

				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD style='border-bottom:none;' width='20%'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientDeptName;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD style='border-bottom:none;' width='20%'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientRefDeptName;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD style='border-bottom:none;' width='15%'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientReceiptStatusString;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD style='border-bottom:none;' width='15%'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientAcceptor;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD style='border-bottom:none;' width='30%'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientAcceptDate;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD></TR></TABLE>";
			}
		}
		if (strRecipientInfoByProposal != "")
		{
			if (nBodyCount > 1)
			{
				if (g_strVersion == "3.5")
					strRecipientInfo = strRecipientInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'><TR><TD class='ltb_center' style='Text-align:left;border-bottom:none;' width='100%'><A href='#' onclick='javascript:viewRecipient(&quot;" + i + "&quot;);return(false);'><B>";
				else
					strRecipientInfo = strRecipientInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='Text-align:left;border-bottom:none;' width='100%'><A href='#' onclick='javascript:viewRecipient(&quot;" + i + "&quot;);return(false);'><B>";
				strRecipientInfo = strRecipientInfo + i + PROPOSAL;
				strRecipientInfo = strRecipientInfo + "</B></A></TD></TR></TABLE><DIV id='idProposal" + i + "'>";
				strRecipientInfo = strRecipientInfo + strRecipientInfoByProposal + "</DIV>";
			}
			else
			{
				strRecipientInfo = strRecipientInfo + strRecipientInfoByProposal;
			}
		}
	}

	objRecipientInfo.setAttribute("view", "Y");
	objRecipientInfo.innerHTML = strRecipientInfo;
}

function drawPublicInfo()
{

	var objPublicInfo = document.getElementById("idPublicInfo");
	var strPublicInfo = "";
	var objPublicGroup = getPublicGroup();
	var objPublic = getFirstPublic(objPublicGroup);
	while (objPublic != null)
	{
		strPublicInfo = strPublicInfo + "<TABLE cellspacing='0' width='580' border='0' style='border-bottom:1pt solid #e0e0e0'><TR>";
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='25%'>";
		strPublicInfo = strPublicInfo + getPublicUserName(objPublic);
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='35%'>";
		strPublicInfo = strPublicInfo + getPublicDeptName(objPublic);
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='40%'>";
		strPublicInfo = strPublicInfo + getPublicDate(objPublic);
		strPublicInfo = strPublicInfo + "</TD></TR></TABLE>";

		objPublic = getNextPublic(objPublic);
	}
	objPublicInfo.innerHTML = strPublicInfo;

/*
	var objPublicInfo = document.getElementById("idPublicInfo");
	var strPublicInfo = "";
	for (var i = 0; i < 3; ++i)
	{
		strPublicInfo = strPublicInfo + "<TABLE cellspacing='0' width='380' border='0' style='border-bottom:1pt solid #e0e0e0'><TR>";
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='25%'>";
		strPublicInfo = strPublicInfo + "하경미";
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='35%'>";
		strPublicInfo = strPublicInfo + "전략팀";
		strPublicInfo = strPublicInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='40%'>";
		strPublicInfo = strPublicInfo + "2002-10-23 12:34:53";
		strPublicInfo = strPublicInfo + "</TD></TR></TABLE>";
	}
	objPublicInfo.innerHTML = strPublicInfo;
*/
}

function drawRecipientCount()
{
	var strValue = "";
	var nBodyCount = 1;
	if (g_strOpenLocale == "DOCUMENT")
		nBodyCount = opener.getBodyCount();
	else
		nBodyCount = getBodyCount();

	for (var i = 1; i <= nBodyCount; ++i)
	{
		var objRecipientGroup = null;
		if (g_strOpenLocale == "DOCUMENT")
			objRecipientGroup = opener.getRecipGroup(i);
		else
			objRecipientGroup = getRecipGroup(i);

		if (g_strVersion == "3.5")
		{
			strValue = strValue + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'>";
			strValue = strValue + "<TR style='cursor:default;background:#f2f8ff;' class='ltb_left'>";
		}
		else
		{
			strValue = strValue + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'>";
			strValue = strValue + "<TR style='cursor:default;background:#f7f7f7;' class='ltb_left'>";
		}
		strValue = strValue + "<TD style='border-bottom:none;' width='100%'>";
		if (nBodyCount > 1)
			strValue = strValue + i + PROPOSAL + ":" + getRecipStatusCount(objRecipientGroup);
		else
			strValue = strValue + getRecipStatusCount(objRecipientGroup);
		strValue = strValue + "</TD></TR></TABLE>";
	}
	var objRecipientCount = document.getElementById("idRecipientCount");
	objRecipientCount.innerHTML = strValue;
}

function getRecipStatusCount(objRecipientGroup)
{
	var strReturn = "";
	var objRecipient = null;

	var RECEIPT_TOTAL_COUNT = 0;
	var RECEIPT_STATUS_COUNT_0 = 0;
	var RECEIPT_STATUS_COUNT_1 = 0;
	var RECEIPT_STATUS_COUNT_2 = 0;
	var RECEIPT_STATUS_COUNT_3 = 0;
	var RECEIPT_STATUS_COUNT_4 = 0;
	var RECEIPT_STATUS_COUNT_5 = 0;
	var RECEIPT_STATUS_COUNT_6 = 0;
	var RECEIPT_STATUS_COUNT_7 = 0;

	if (objRecipientGroup != null)
	{
		if (g_strOpenLocale == "DOCUMENT")
			objRecipient = opener.getFirstRecipient(objRecipientGroup);
		else
			objRecipient = getFirstRecipient(objRecipientGroup);

		while (objRecipient != null)
		{
			if (g_strOpenLocale == "DOCUMENT")
			{
				strRecipientReceiptStatus = opener.getRecipientReceiptStatus(objRecipient);
				objRecipient = opener.getNextRecipient(objRecipient);
			}
			else
			{
				strRecipientReceiptStatus = getRecipientReceiptStatus(objRecipient);
				objRecipient = getNextRecipient(objRecipient);
			}

			if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_0)
				RECEIPT_STATUS_COUNT_0 = RECEIPT_STATUS_COUNT_0 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_1)
				RECEIPT_STATUS_COUNT_1 = RECEIPT_STATUS_COUNT_1 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_2)
				RECEIPT_STATUS_COUNT_2 = RECEIPT_STATUS_COUNT_2 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_3)
				RECEIPT_STATUS_COUNT_3 = RECEIPT_STATUS_COUNT_3 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_4)
				RECEIPT_STATUS_COUNT_4 = RECEIPT_STATUS_COUNT_4 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_5)
				RECEIPT_STATUS_COUNT_5 = RECEIPT_STATUS_COUNT_5 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_6)
				RECEIPT_STATUS_COUNT_6 = RECEIPT_STATUS_COUNT_6 + 1;
			else if (strRecipientReceiptStatus == RECEIPT_STATUS_VALUE_7)
				RECEIPT_STATUS_COUNT_7 = RECEIPT_STATUS_COUNT_7 + 1;
			else
				RECEIPT_STATUS_COUNT_2 = RECEIPT_STATUS_COUNT_2 + 1;
		}

		RECEIPT_TOTAL_COUNT = RECEIPT_STATUS_COUNT_0 + RECEIPT_STATUS_COUNT_1 + RECEIPT_STATUS_COUNT_2 + RECEIPT_STATUS_COUNT_3;
		RECEIPT_TOTAL_COUNT = RECEIPT_TOTAL_COUNT + RECEIPT_STATUS_COUNT_4 + RECEIPT_STATUS_COUNT_5 + RECEIPT_STATUS_COUNT_6 + RECEIPT_STATUS_COUNT_7;

		strReturn = strReturn + RECEIPT + ":" + RECEIPT_TOTAL_COUNT + " -> ";

		if (RECEIPT_STATUS_COUNT_0 > 0)
			strReturn = strReturn + RECEIPT_STATUS_0 + ":" + RECEIPT_STATUS_COUNT_0 + ", ";
		if (RECEIPT_STATUS_COUNT_1 > 0)
			strReturn = strReturn + RECEIPT_STATUS_1 + ":" + RECEIPT_STATUS_COUNT_1 + ", ";
		if (RECEIPT_STATUS_COUNT_2 > 0)
			strReturn = strReturn + RECEIPT_STATUS_2 + ":" + RECEIPT_STATUS_COUNT_2 + ", ";
		if (RECEIPT_STATUS_COUNT_3 > 0)
			strReturn = strReturn + RECEIPT_STATUS_3 + ":" + RECEIPT_STATUS_COUNT_3 + ", ";
		if (RECEIPT_STATUS_COUNT_4 > 0)
			strReturn = strReturn + RECEIPT_STATUS_4 + ":" + RECEIPT_STATUS_COUNT_4 + ", ";
		if (RECEIPT_STATUS_COUNT_5 > 0)
			strReturn = strReturn + RECEIPT_STATUS_5 + ":" + RECEIPT_STATUS_COUNT_5 + ", ";
		if (RECEIPT_STATUS_COUNT_6 > 0)
			strReturn = strReturn + RECEIPT_STATUS_6 + ":" + RECEIPT_STATUS_COUNT_6 + ", ";
		if (RECEIPT_STATUS_COUNT_7 > 0)
			strReturn = strReturn + RECEIPT_STATUS_7 + ":" + RECEIPT_STATUS_COUNT_7 + ", ";

		strReturn = strReturn.substring(0, strReturn.length-2);
	}
	return strReturn;
}


