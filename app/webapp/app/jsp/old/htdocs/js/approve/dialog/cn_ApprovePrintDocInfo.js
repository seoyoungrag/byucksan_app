function getHeaderInfo(strDisplayPos)
{
	var strBodyCount = opener.getBodyCount();
	var nBodyCount = parseInt(strBodyCount);

	var strHeaderInfo = "";
	for (var i = 1; i <= nBodyCount; ++i)
	{
		if (g_strVersion == "3.5")
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
		else
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
		strHeaderInfo = strHeaderInfo + "<TR><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='70'>";
		if (i == 1)
			strHeaderInfo = strHeaderInfo + LABEL_TITLE + "&nbsp;:&nbsp;";
		else
			strHeaderInfo = strHeaderInfo + "&nbsp;";

		strHeaderInfo = strHeaderInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='490'>";
		if (nBodyCount > 1)
			strHeaderInfo = strHeaderInfo + "(" + i + PROPOSAL + ") " + opener.getTitle(i);
		else
			strHeaderInfo = strHeaderInfo + opener.getTitle(i);

		strHeaderInfo = strHeaderInfo + "</TR></TABLE>";
	}


	var objApprovalLine = opener.getActiveApprovalLine();
	var objApprover = null;
	if (objApprovalLine != null)
	{
		objApprover = opener.getApproverByRole(objApprovalLine, "1");
		if (objApprover == null)
			objApprover = opener.getApproverByRole(objApprovalLine, "0");

		var strApproverDeptName = opener.getApproverDeptName(objApprover);
		var strUserPosition = opener.getUserPosition(objApprover, strDisplayPos);
		var strApproverUserName = opener.getApproverUserName(objApprover);
		var strApproverSignDate = opener.getApproverSignDate(objApprover);

		if (g_strVersion == "3.5")
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
		else
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
		strHeaderInfo = strHeaderInfo + "<TR><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='70'>";
		strHeaderInfo = strHeaderInfo + LABEL_APPROVER_ROLE_1 + "&nbsp;:&nbsp;";
		strHeaderInfo = strHeaderInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='490'>";
		strHeaderInfo = strHeaderInfo + strApproverDeptName + "&nbsp;" + strApproverUserName + "&nbsp;" + strUserPosition;
		strHeaderInfo = strHeaderInfo + "</TR></TABLE>";

		if (g_strVersion == "3.5")
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
		else
			strHeaderInfo = strHeaderInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
		strHeaderInfo = strHeaderInfo + "<TR><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='70'>";
		strHeaderInfo = strHeaderInfo + LABEL_APPROVER_ROLE_1_DATE + "&nbsp;:&nbsp;";
		strHeaderInfo = strHeaderInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='490'>";
		strHeaderInfo = strHeaderInfo + strApproverSignDate;
		strHeaderInfo = strHeaderInfo + "</TR></TABLE>";
	}
	return strHeaderInfo;
}

function getApprovalInfo(strDisplayPos)
{
	var strApprovalInfo = "";
	var strOpinionInfo = "";
	if (g_strVersion == "3.5")
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_head' style='border-bottom:none;' width='130'>";
	strApprovalInfo = strApprovalInfo + LABEL_DEPT_NAME;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='60'>";

	if ((strDisplayPos == "GRD") || (strDisplayPos == "SGRD"))
		strApprovalInfo = strApprovalInfo + LABEL_LEVEL;
	else if ((strDisplayPos == "POS") || (strDisplayPos == "SPOS"))
		strApprovalInfo = strApprovalInfo + LABEL_POSITION;
	else if (strDisplayPos == "TLT")
		strApprovalInfo = strApprovalInfo + LABEL_TITLENAME;

	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='120'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVER;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='90'>";
	strApprovalInfo = strApprovalInfo + LABEL_ROLE;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='110'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVE_DATE;
	strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";

	var objApprovalLine = null;
	var objApprover = null;

	var strApprovalLineName = "";
	var strCurrentApprovalLine = "";
	var strApproverDeptName = "";
	var strUserPosition = "";
	var strApproverUserName = "";
	var strApproverRepName = "";
	var strApproverRole = "";
	var strApproverAction = "";
	var strApproverActionString = "";
	var strApproverType = "";
	var strApproverSerialOrder = "";
	var strApproverRoleString = "";
	var strApproverSignDate = "";
	var strOpinion = "";
	var strIsDocModified = "";
	var strIsLineModified = "";
	var strIsAttachModified = "";

	objApprovalLine = opener.getActiveApprovalLine();
	if (objApprovalLine != null)
	{
		strApprovalLineName = opener.getLineName(objApprovalLine);
		objApprover = opener.getFirstApprover(objApprovalLine);
	}

	while (objApprover != null)
	{
		strApproverDeptName = opener.getApproverDeptName(objApprover);
		strUserPosition = opener.getUserPosition(objApprover, strDisplayPos);
		strApproverUserName = opener.getApproverUserName(objApprover);
		strApproverRepName = opener.getApproverRepName(objApprover);
		strApproverSerialOrder = opener.getApproverSerialOrder(objApprover);
		strApproverRole = opener.getApproverRole(objApprover);
		strApproverRoleString = opener.getApproverRoleString(objApprover);
		strApproverAction = opener.getApproverAction(objApprover);
		strApproverActionString = opener.getApproverActionString(objApprover);
		strApproverType = opener.getApproverType(objApprover);
		strApproverSignDate = opener.getApproverSignDate(objApprover);
		strOpinion = opener.getApproverOpinion(objApprover);
		strIsDocModified = opener.getApproverIsDocModified(objApprover);
		strIsLineModified = opener.getApproverIsLineModified(objApprover);
		strIsAttachModified = opener.getApproverIsAttachModified(objApprover);

		if (strApproverRepName != "")
		{
			strApproverUserName = strApproverUserName + "(" + strApproverRepName + ")";
			strApproverRoleString = strApproverRoleString + "(" + ROLE_PROXY + ")";
		}

		if (strApproverType == "1")
			strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_1 + ROLE_DEPART;

		if (strApproverActionString != "")
			strApproverRoleString = strApproverRoleString + "(" + strApproverActionString + ")";

		else if (strApproverType == "2")
		{
			if (strApproverRole != "34")
				strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_2 + ROLE_DEPART;
		}

		strApproverSignDate = getDateDisplay(strApproverSignDate, "YYYY-MM-DD");

		if (g_strVersion == "3.5")
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
		else
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
		strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_center' style='border-bottom:none;' width='130'>";
		if ((strApproverRole == "32") || (strApproverRole == "33"))
			strCurrentApprovalLine = strApprovalLineName + "-" + strApproverSerialOrder;

		strApprovalInfo = strApprovalInfo + strApproverDeptName + "</TD>";
		strApprovalInfo = strApprovalInfo + "<TD class='ltb_center' style='border-bottom:none;' width='60'>";
		strApprovalInfo = strApprovalInfo + strUserPosition;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='120'>";
		strApprovalInfo = strApprovalInfo + strApproverUserName;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='90'>";
		strApprovalInfo = strApprovalInfo + strApproverRoleString;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='110'>";
		strApprovalInfo = strApprovalInfo + strApproverSignDate;
		strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";
		if ((strApproverRole == "32") || (strApproverRole == "33"))
			strApprovalInfo = strApprovalInfo + getApprovalLineHtml(strCurrentApprovalLine, strDisplayPos);

		if (strOpinion != "")
		{
			if (g_strVersion == "3.5")
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'>";
			else
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'>";
			strOpinionInfo = strOpinionInfo + "<TR><TD class='ltb_center' style='border-bottom:none;' width='110'>";
			strOpinionInfo = strOpinionInfo + strApproverUserName;
			strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='440'>";
			strOpinionInfo = strOpinionInfo + strOpinion;
			strOpinionInfo = strOpinionInfo + "</TD></TR></TABLE>";
		}

		objApprover = opener.getNextApprover(objApprover);
	}
	strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";

	strApprovalInfo = strApprovalInfo + "<BR>";
	if (g_strVersion == "3.5")
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='1' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='1' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_head' style='border-bottom:none;' width='120' nowrap='1'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVER;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='440'>";
	strApprovalInfo = strApprovalInfo + LABEL_OPINION;
	strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";
	strApprovalInfo = strApprovalInfo + strOpinionInfo;

	return strApprovalInfo;
}

function getApprovalLineHtml(strApprovalLineName, strDisplayPos)
{
	var objApprovalLine = opener.getApprovalLineByLineName(strApprovalLineName);
	if (objApprovalLine == null)
		return "";

	var objApprover = "";
	var strApprovalLineName = "";
	var strCurrentApprovalLine = "";
	var strApproverDeptName = "";
	var strUserPosition = "";
	var strApproverUserName = "";
	var strApproverRepName = "";
	var strApproverRole = "";
	var strApproverRoleString = "";
	var strApproverAction = "";
	var strApproverActionString = "";
	var strApproverType = "";
	var strApproverSerialOrder = "";
	var strApproverSignDate = "";
	var strBgColor = "";

	strApprovalLineName = opener.getLineName(objApprovalLine);
	objApprover = opener.getFirstApprover(objApprovalLine);

	if (((strApprovalLineName.split("-").length) % 2) == 0)
		strBgColor = "background-color:#ffffff;";
	else
		strBgColor = "";

	var strApprovalInfo = "";
	while (objApprover != null)
	{
		strApproverDeptName = opener.getApproverDeptName(objApprover);
		strUserPosition = opener.getUserPosition(objApprover, strDisplayPos);
		strApproverUserName = opener.getApproverUserName(objApprover);
		strApproverRepName = opener.getApproverRepName(objApprover);
		strApproverSerialOrder = opener.getApproverSerialOrder(objApprover);
		strApproverRole = opener.getApproverRole(objApprover);
		strApproverRoleString = opener.getApproverRoleString(objApprover);
		strApproverAction = opener.getApproverAction(objApprover);
		strApproverActionString = opener.getApproverActionString(objApprover);
		strApproverType = opener.getApproverType(objApprover);
		strApproverSignDate = opener.getApproverSignDate(objApprover);

		if (strApproverRepName != "")
		{
			strApproverUserName = strApproverUserName + "(" + strApproverRepName + ")";
			strApproverRoleString = strApproverRoleString + "(" + ROLE_PROXY + ")";
		}

		if (strApproverType == "1")
			strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_1 + ROLE_DEPART;

		if (strApproverActionString != "")
			strApproverRoleString = strApproverRoleString + "(" + strApproverActionString + ")";

		else if (strApproverType == "2")
		{
			if (strApproverRole != "34")
				strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_2 + ROLE_DEPART;
		}

		strApproverSignDate = getDateDisplay(strApproverSignDate, "YYYY-MM-DD");

		if (g_strVersion == "3.5")
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
		else
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
		strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='130'>";
		if ((strApproverRole == "32") || (strApproverRole == "33"))
			strCurrentApprovalLine = strApprovalLineName + "-" + strApproverSerialOrder;
		strApprovalInfo = strApprovalInfo + strApproverDeptName + "</TD>";

		strApprovalInfo = strApprovalInfo + "<TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='60'>";
		strApprovalInfo = strApprovalInfo + strUserPosition;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='120'>";
		strApprovalInfo = strApprovalInfo + strApproverUserName;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='90'>";
		strApprovalInfo = strApprovalInfo + strApproverRoleString;
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='110'>";
		strApprovalInfo = strApprovalInfo + strApproverSignDate;
		strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";
		if ((strApproverRole == "32") || (strApproverRole == "33"))
			strApprovalInfo = strApprovalInfo + getApprovalLineHtml(strCurrentApprovalLine, strDisplayPos);

		objApprover = opener.getNextApprover(objApprover);
	}
	return strApprovalInfo;
}

function getRecipientInfo()
{
	var strBodyCount = opener.getBodyCount();

	var strRecipientInfo = "";
	var strRecipientInfoByProposal = "";
	if (g_strVersion == "3.5")
		strRecipientInfo = strRecipientInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strRecipientInfo = strRecipientInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strRecipientInfo = strRecipientInfo + "<TR><TD class='ltb_head' style='border-bottom:none;' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_NAME;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_SYMBOL;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_REFNAME;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_REFSYMBOL;
	strRecipientInfo = strRecipientInfo + "</TD></TR></TABLE>";

	var objRecipientGroup = "";
	var objRecipient = "";
	var strRecipientDeptName = "";
	var strRecipientDeptSymbol = "";
	var strRecipientRefDeptName = "";
	var strRecipientRefDeptSymbol = "";
	for (var i = 1; i <= parseInt(strBodyCount); ++i)
	{
		strRecipientInfoByProposal = "";
		objRecipientGroup = opener.getRecipGroup(i);

		if (objRecipientGroup != null)
		{
			objRecipient = opener.getFirstRecipient(objRecipientGroup);

			while (objRecipient != null)
			{
				strRecipientDeptName = opener.getRecipientDeptName(objRecipient);
				strRecipientDeptSymbol = opener.getRecipientDeptSymbol(objRecipient);
				strRecipientRefDeptName = opener.getRecipientRefDeptName(objRecipient);
				strRecipientRefDeptSymbol = opener.getRecipientRefDeptSymbol(objRecipient);

				if (g_strVersion == "3.5")
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
				else
					strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + "<TR><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientDeptName;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientDeptSymbol;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientRefDeptName;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientRefDeptSymbol;
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD></TR></TABLE>";

				objRecipient = opener.getNextRecipient(objRecipient);
			}
		}
		if (strRecipientInfoByProposal != "")
		{
			if (parseInt(strBodyCount) > 1)
			{
				if (g_strVersion == "3.5")
					strRecipientInfo = strRecipientInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #cfe5fe'>";
				else
					strRecipientInfo = strRecipientInfo + "<TABLE cellspacing='0' width='560' border='0' style='border-bottom:1pt solid #e0e0e0'>";
				strRecipientInfo = strRecipientInfo + "<TR><TD class='ltb_center' style='Text-align:left;border-bottom:none;' width='560'>";
				strRecipientInfo = strRecipientInfo + i + PROPOSAL;
				strRecipientInfo = strRecipientInfo + "</TD></TR></TABLE>";
				strRecipientInfo = strRecipientInfo + strRecipientInfoByProposal;
			}
			else
			{
				strRecipientInfo = strRecipientInfo + strRecipientInfoByProposal;
			}
		}
	}
	strRecipientInfo = strRecipientInfo + "</DIV></TD></TR></TABLE>";

	return strRecipientInfo;
}

function getAttachInfo()
{
	var strAttachInfo = "";

//	var strIsAttached = opener.getIsAttached();
//	if (strIsAttached != "Y")
//		return strAttachInfo;

	var strBodyCount = opener.getBodyCount();
	var nBodyCount = parseInt(strBodyCount);

	if (g_strVersion == "3.5")
		strAttachInfo = strAttachInfo + "<TABLE width='410' cellpadding='1' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strAttachInfo = strAttachInfo + "<TABLE width='410' cellpadding='1' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strAttachInfo = strAttachInfo + "<TR><TD class='ltb_head' style='border-bottom:none;' width='50' nowrap='1'>&nbsp;";
	strAttachInfo = strAttachInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='260'>";
	strAttachInfo = strAttachInfo + LABEL_ATTACH_NAME;
	strAttachInfo = strAttachInfo + "</TD><TD class='ltb_head' style='border-bottom:none;' width='100'>";
	strAttachInfo = strAttachInfo + LABEL_ATTACH_SIZE;
	strAttachInfo = strAttachInfo + "</TD></TR></TABLE>";
	for (var iBodyCount = 1; iBodyCount <= nBodyCount; ++iBodyCount)
	{
		var objAttachList = opener.getGeneralAttachListByCaseNumber(iBodyCount);
		if ((nBodyCount > 1) && (objAttachList.length > 0))
		{
			if (g_strVersion == "3.5")
				strAttachInfo = strAttachInfo + "<TABLE cellspacing='0' width='410' border='0' style='border-bottom:1pt solid #cfe5fe'>";
			else
				strAttachInfo = strAttachInfo + "<TABLE cellspacing='0' width='410' border='0' style='border-bottom:1pt solid #e0e0e0'>";
			strAttachInfo = strAttachInfo + "<TR><TD class='ltb_center' style='border-bottom:none;' width='40'>";
			strAttachInfo = strAttachInfo + iBodyCount + PROPOSAL;
			strAttachInfo = strAttachInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='260'>";
			strAttachInfo = strAttachInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='100'>";
			strAttachInfo = strAttachInfo + "</TD></TR></TABLE>";
		}
		for (var iAttachCount = 0; iAttachCount < objAttachList.length; ++iAttachCount)
		{
			var objAttach = objAttachList[iAttachCount];
		    	var strDisplayName = opener.getAttachDisplayName(objAttach);
		    	var strFileName = opener.getAttachFileName(objAttach);
		    	var strExt = getExtention(strFileName);
		    	var strFileSize = calcFileSize(opener.getAttachFileSize(objAttach));

			if (g_strVersion == "3.5")
				strAttachInfo = strAttachInfo + "<TABLE cellspacing='0' width='410' border='0' style='border-bottom:1pt solid #cfe5fe'>";
			else
				strAttachInfo = strAttachInfo + "<TABLE cellspacing='0' width='410' border='0' style='border-bottom:1pt solid #e0e0e0'>";
			strAttachInfo = strAttachInfo + "<TR><TD class='ltb_center' style='border-bottom:none;' width='40'>";
			strAttachInfo = strAttachInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='260'>";
			strAttachInfo = strAttachInfo + strDisplayName + strExt;
			strAttachInfo = strAttachInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='100'>";
			strAttachInfo = strAttachInfo + strFileSize;
			strAttachInfo = strAttachInfo + "</TD></TR></TABLE>";
		}
	}
	
	return strAttachInfo;
}

function calcFileSize(size)
{
    var strFileSize = size;
    var nFileSize = parseInt(strFileSize);
    var strUnit = "B";

    while (parseInt(nFileSize / 1000) > 0)
    {
        if (strUnit == "B")
            strUnit = "KB";
        else if (strUnit == "KB")
            strUnit = "MB";
        else if (strUnit == "MB")
            strUnit = "GB";
        else if (strUnit == "GB")
            break;

        nFileSize = nFileSize / 1000;
    }

    strFileSize = parseInt(nFileSize) + strUnit;

    return strFileSize;
}

function getExtention(strFileName)
{
	var arrFileName = strFileName.split(".");
	return "." + arrFileName[arrFileName.length - 1];
}
