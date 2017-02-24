function drawDocumentInfo()
{
	var objDocumentInfo = document.getElementById("idDocumentInfo");

	var strTitle = "";
	var strDocNo = "";
	var strDraftProcDeptName = "";
	var strSerialNumber = "";
	var strEnforceDate = "";
	var strDeliverDept = "";
	var strEnforceConserve = "";
	var strDocCategory = "";
	var strEnforceBound = "";
	var strRecieveDate = "";
	var strRecievePerson = "";
	var strAcceptDept = "";
	var strAcceptor = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		strTitle = opener.getTitle(1);
		strDraftProcDeptName = opener.getDraftProcDeptName();
		strSerialNumber = opener.getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
			strDocNo = opener.getClassInfo();
		strEnforceDate = opener.getDelivererSignDateByType("1");
		strDeliverDept = opener.getDelivererDeptNameByType("1");
		strEnforceConserve = opener.getEnforceConserve();
		strDocCategory = opener.getDocCategoryString(1);
		strEnforceBound = opener.getEnforceBoundString(1);
		strRecieveDate = opener.getDelivererSignDateByType("2");
		strRecievePerson = opener.getDelivererUserNameByType("2");
		strAcceptDept = opener.getDelivererDeptNameByType("3");
		strAcceptor = opener.getDelivererUserNameByType("3");
	}
	else if (g_strOpenLocale == "LIST")
	{
		strTitle = getTitle(1);
		strDraftProcDeptName = getDraftProcDeptName();
		strSerialNumber = getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
			strDocNo = getClassInfo();
		strEnforceDate = getDelivererSignDateByType("1");
		strDeliverDept = getDelivererDeptNameByType("1");
		strEnforceConserve = getEnforceConserve();
		strDocCategory = getDocCategoryString(1);
		strEnforceBound = getEnforceBoundString(1);
		strRecieveDate = getDelivererSignDateByType("2");
		strRecievePerson = getDelivererUserNameByType("2");
		strAcceptDept = getDelivererDeptNameByType("3");
		strAcceptor = getDelivererUserNameByType("3");
	}

	if (isString(strEnforceConserve))
		strEnforceConserve = strEnforceConserve + YEAR;
	strEnforceDate = getDateDisplay(strEnforceDate, "YYYY-MM-DD");
	strRecieveDate = getDateDisplay(strRecieveDate, "YYYY-MM-DD");

	var strDocumentInfo = "";
	if (g_strVersion == "3.5")
	{
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a5c9f9'><TR><TD height='5'></TD></TR></TABLE>";
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#ffffff' style='border-bottom:1pt solid #a5c9f9'>";
	}
	else
	{
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a3a3a3'><TR><TD height='5'></TD></TR></TABLE>";
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#ffffff' style='border-bottom:1pt solid #a3a3a3'>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_DOC_TITLE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strTitle + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_DOC_NO + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocNo + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + ING_DELIVERER_DEPT + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDeliverDept + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_ENFORCE_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceDate + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + ING_ENFORCE_CONV + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceConserve + "</TD></TR>";

	if (g_strIng == "ING")
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_DOC_CATEGORY + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocCategory + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + ING_ENFORCE_BOUND + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceBound + "</TD></TR>";
	}
	else
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_RECIEVE_DATE + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strRecieveDate + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + ING_RECIEVE_PEROSN + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strRecievePerson + "</TD></TR>";

		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + ING_ACCEPT_DEPT + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strAcceptDept + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + ING_ACCEPTOR + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strAcceptor + "</TD></TR>";
	}

	strDocumentInfo = strDocumentInfo + "</TABLE>";
	objDocumentInfo.innerHTML = strDocumentInfo;
}

function isString(strText)
{
	var bYear = false;
	for(var i = 0; i < strText.length; i++)
	{
		var strSub = strText.substring(i, i+1);
		if (strSub.charCodeAt(0) < 59)
			bYear = true;
		else
			bYear = false;
	}
	return bYear;
}
