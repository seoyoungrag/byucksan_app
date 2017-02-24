function drawDocumentInfo()
{
	if (g_strOpenLocale == "DOCUMENT")
		g_nBodyCount = opener.getBodyCount();
	else if (g_strOpenLocale == "LIST")
		g_nBodyCount = getBodyCount();

	var objDocumentInfo = document.getElementById("idDocumentInfo");

	var strTitle = "";
	var strDocCategory = "";
	var strEnforceBound = "";
	if (g_strOpenLocale == "DOCUMENT")
	{
		for (var i = 1; i <= g_nBodyCount; ++i)
		{
			if (g_nBodyCount > 1)
			{
				strTitle = strTitle + "(" + i + PROPOSAL + ") " + opener.getTitle(i) + "<BR>";
				strDocCategory = strDocCategory + "(" + i + PROPOSAL + ") " + opener.getDocCategoryString(i) + "<BR>";
				strEnforceBound = strEnforceBound + "(" + i + PROPOSAL + ") " + opener.getEnforceBoundString(i) + "<BR>";
			}
			else
			{
				strTitle = opener.getTitle(i);
				strDocCategory = opener.getDocCategoryString(i);
				strEnforceBound = opener.getEnforceBoundString(i);
			}
		}
	}
	else if (g_strOpenLocale == "LIST")
	{
		for (var i = 1; i <= g_nBodyCount; ++i)
		{
			if (g_nBodyCount > 1)
			{
				strTitle = strTitle + "(" + i + PROPOSAL + ") " + getTitle(i) + "<BR>";
				strDocCategory = strDocCategory + "(" + i + PROPOSAL + ") " + getDocCategoryString(i) + "<BR>";
				strEnforceBound = strEnforceBound + "(" + i + PROPOSAL + ") " + getEnforceBoundString(i) + "<BR>";
			}
			else
			{
				strTitle = getTitle(i);
				strDocCategory = getDocCategoryString(i);
				strEnforceBound = getEnforceBoundString(i);
			}
		}
	}

	var strDocNo = "";
	var strDraftProcDeptName = "";
	var strSerialNumber = "";
	var strDraftClipName = "";
	var strDraftConv = "";
	var strEnforceConv = "";
	var strSubmiter = "";
	var strSubmitDate = "";
	var strCurrent = "";
	var strCurrentRole = "";
	var strCompleteName = "";
	var strCompleteDate = "";
	var strStatus = "";
	var strAccessLevel = "";
	var strPublicLevel = "";
	var strUrgency = "";
	var strEnforceDate = "";
	var strAnnouncementStatus = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		strDraftProcDeptName = opener.getDraftProcDeptName();
		strSerialNumber = opener.getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
		{
			strDocNo = opener.getClassInfo();
			if (strDocNo.indexOf("-") == 0)
				strDocNo = strDocNo.substring(1, strDocNo.length);
		}
		strDraftClipName = opener.getDraftArchiveName();
		strDraftConv = opener.getDraftConserve();
		strEnforceConv = opener.getEnforceConserve();
		strSubmiter = opener.getSubmiterName();
		strSubmitDate = opener.getSubmitDate();
		strCurrent = opener.getCurrentName();
		strCurrentRole = opener.getCurrentRoleString();
		strCompleteName = opener.getCompleteName();
		if (strCompleteName != "")
			strCurrent = "-";
		strCompleteDate = opener.getCompleteDate();
		strStatus = opener.getApprovalStatus();
		strStatus = strStatus + getDocStatusInApprovalLine();
		strAccessLevel = opener.getAccessLevel();
		if (g_strOpt148 == "1")
			strPublicLevel = getPublication(opener.getPublicLevel());
		else
			strPublicLevel = opener.getPublicLevel();
		strUrgency = opener.getUrgency();
		strEnforceDate = opener.getEnforceDate();
		if (g_nAnnounceStatus == "1")
			strAnnouncementStatus = opener.getAnnouncementStatus();
	}
	else if (g_strOpenLocale == "LIST")
	{
		strDraftProcDeptName = getDraftProcDeptName();
		strSerialNumber = getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
		{
			strDocNo = getClassInfo();
			if (strDocNo.indexOf("-") == 0)
				strDocNo = strDocNo.substring(1, strDocNo.length);
		}
		strDraftClipName = getDraftArchiveName();
		strDraftConv = getDraftConserve();
		strEnforceConv = getEnforceConserve();
		strSubmiter = getSubmiterName();
		strSubmitDate = getSubmitDate();
		strCurrent = getCurrentName();
		strCurrentRole = getCurrentRoleString();
		strCompleteName = getCompleteName();
		if (strCompleteName != "")
			strCurrent = "-";
		strCompleteDate = getCompleteDate();
		strStatus = getApprovalStatus();
		strStatus = strStatus + getDocStatusInApprovalLine();
		strAccessLevel = getAccessLevel();
		if (g_strOpt148 == "1")
			strPublicLevel = getPublication(getPublicLevel());
		else
			strPublicLevel = getPublicLevel()
		strUrgency = getUrgency();
		strEnforceDate = getEnforceDate();
		if (g_nAnnounceStatus == "1")
			strAnnouncementStatus = getAnnouncementStatus();
	}

	if (isString(strDraftConv))
		strDraftConv = strDraftConv + YEAR;
	if (isString(strEnforceConv))
		strEnforceConv = strEnforceConv + YEAR;

	strSubmitDate = getDateDisplay(strSubmitDate, "YYYY-MM-DD");
	strCompleteDate = getDateDisplay(strCompleteDate, "YYYY-MM-DD");
	strEnforceDate = getDateDisplay(strEnforceDate, "YYYY-MM-DD");
	if (g_nAnnounceStatus == "1")
		strAnnouncementStatus = getAnnouncementStatusName(strAnnouncementStatus);

	var strDocumentInfo = "";
	if (g_strVersion == "3.5")
	{
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a5c9f9'><TR><TD height='5'></TD></TR></TABLE>";
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	}
	else
	{
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a3a3a3'><TR><TD height='5'></TD></TR></TABLE>";
		strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#ffffff' style='border-bottom:1pt solid #a3a3a3'>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_TITLE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strTitle + "</TD></TR>";

	if (g_strOpt148 == "1")
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_NUMBER + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strDocNo + "</TD></TR>";
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_CLIPNAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDraftClipName + "</TD>";
	}
	else
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_NUMBER + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocNo + "</TD>";
	}
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DRAFT_CONSERVE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDraftConv + "</TD></TR>";
// related
	if (g_strSystemID == "SDSDM")
	{
		var strCabName = getRelatedDataByElement("CAB_NAME");
		var strFldName = getRelatedDataByElement("FLD_NAME");

		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CABINET_NAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCabName + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_FOLDER_NAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strFldName + "</TD></TR>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_SUBMITER + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strSubmiter + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_SUBMIT_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strSubmitDate + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CURRENT + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCurrent + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CURRENT_ROLE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCurrentRole + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_COMPLETE_NAME + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCompleteName + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_COMPLETE_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCompleteDate + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_STATUS + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strStatus + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ACCESS_LEVEL + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strAccessLevel + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_PUBLIC_LEVEL + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strPublicLevel + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_URGENCY + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strUrgency + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceDate + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_CONSERVE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceConv + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CATEGORY + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocCategory + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_BOUND + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceBound + "</TD>";

	if (g_nAnnounceStatus == 1)
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ANNOUNCEMENT_STATUS + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strAnnouncementStatus + "</TD>";
	}

	strDocumentInfo = strDocumentInfo + "</TR></TABLE>";

	objDocumentInfo.setAttribute("view", "Y");
	objDocumentInfo.innerHTML = strDocumentInfo;
}

function drawDocumentInfo4RECV()
{
	g_nBodyCount = 1;

	var objDocumentInfo = document.getElementById("idDocumentInfo");

	var objApprovalLine = null;
	var objApprover = null;

	var strTitle = "";
	var strReceiveNumber = "";
	var strEnforceProcDeptName = "";
	var strDraftNo = "";
	var strDocNo = "";
	var strDraftProcDeptName = "";
	var strSerialNumber = "";
	var strEnforceClipName = "";
	var strSendDeptName = "";
	var strEnforceDate = "";
	var strRecieveUserName = "";
	var strRecieveDate = "";
	var strPriorName = "";
	var strPriorDate = "";
	var strCharger = "";
	var strStatus = "";
	var strConserveTerm = "";
	var strSecurityLevel = "";
	var strEnforceBound = "";
	var strIsPost = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		strTitle = opener.getTitle(1);
		strReceiveNumber = opener.getReceiveNumber();
		strEnforceProcDeptName = opener.getEnforceProcDeptName();
		strDraftNo = strEnforceProcDeptName + "-" + strReceiveNumber;
		strDraftProcDeptName = opener.getDraftProcDeptName();
		strSerialNumber = opener.getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
		{
			strDocNo = opener.getClassInfo();
			if (strDocNo.indexOf("-") == 0)
				strDocNo = strDocNo.substring(1, strDocNo.length);
		}
		strEnforceClipName = opener.getEnforceArchiveName();
		strSendDeptName = opener.getDelivererDeptNameByType("1");
		strEnforceDate = opener.getDelivererSignDateByType("1");
		strRecieveUserName = opener.getDelivererUserNameByType("3");
		strRecieveDate = opener.getDelivererSignDateByType("3");
		objApprovalLine = opener.getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			objApprover = opener.getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				strPriorName = opener.getApproverUserName(objApprover);
				strPriorDate = opener.getApproverSignDate(objApprover);
			}

			objApprover = opener.getApproverByRole(objApprovalLine, "20");
			if (objApprover != null)
				strCharger = opener.getApproverUserName(objApprover);
		}
		strStatus = opener.getApprovalStatus();
		strConserveTerm = opener.getEnforceConserve();
		strSecurityLevel = opener.getSecurityLevel();
		strEnforceBound = opener.getEnforceBoundString(1);
		strIsPost = opener.getIsPostString();
	}
	else if (g_strOpenLocale == "LIST")
	{
		strTitle = getTitle(1);
		strReceiveNumber = getReceiveNumber();
		strEnforceProcDeptName = getEnforceProcDeptName();
		strDraftNo = strEnforceProcDeptName + "-" + strReceiveNumber;
		strDraftProcDeptName = getDraftProcDeptName();
		strSerialNumber = getSerialNumber();
		if (g_strOpt148 == "1")
			strDocNo = strDraftProcDeptName + "-" + strSerialNumber;
		else
		{
			strDocNo = getClassInfo();
			if (strDocNo.indexOf("-") == 0)
				strDocNo = strDocNo.substring(1, strDocNo.length);
		}
		strEnforceClipName = getEnforceArchiveName();
		strSendDeptName = getDelivererDeptNameByType("1");
		strEnforceDate = getDelivererSignDateByType("1");
		strRecieveUserName = getDelivererUserNameByType("3");
		strRecieveDate = getDelivererSignDateByType("3");
		objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			objApprover = getApproverByRole(objApprovalLine, "21");
			if (objApprover != null)
			{
				strPriorName = getApproverUserName(objApprover);
				strPriorDate = getApproverSignDate(objApprover);
			}
			objApprover = getApproverByRole(objApprovalLine, "20");
			if (objApprover != null)
			{
				strCharger = getApproverUserName(objApprover);
			}
		}
		strStatus = getApprovalStatus();
		strConserveTerm = getEnforceConserve();
		strSecurityLevel = getSecurityLevel();
		strEnforceBound = getEnforceBoundString(1);
		strIsPost = getIsPostString();
	}

	strEnforceDate = getDateDisplay(strEnforceDate, "YYYY-MM-DD");
	strRecieveDate = getDateDisplay(strRecieveDate, "YYYY-MM-DD");
	strPriorDate = getDateDisplay(strPriorDate, "YYYY-MM-DD");

	var strDocumentInfo = "";
	strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a3a3a3'><TR><TD height='5'></TD></TR></TABLE>";
	strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#ffffff' style='border-bottom:1pt solid #a3a3a3'>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_TITLE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strTitle + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_RECEIVE_NUMBER + "</TD>";
	if (g_strOpt148 == "1")
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDraftNo + "</TD>";
	else
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strReceiveNumber + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_NUMBER + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocNo + "</TD></TR>";
// related
	if(g_strSystemID == "SDSDM")
	{
		var strCabName = getRelatedDataByElement("CAB_NAME");
		var strFldName = getRelatedDataByElement("FLD_NAME");

		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CABINET_NAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCabName + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_FOLDER_NAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strFldName + "</TD></TR>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_SEND_DEPT_NAME + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strSendDeptName + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceDate + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_RECIEVE_USER_NAME + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strRecieveUserName + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_RECIEVE_DATE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strRecieveDate + "</TD></TR>";

	if (g_strOpt148 == "0")
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_PRIOR_NAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strPriorName + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_PRIOR_DATE + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strPriorDate + "</TD></TR>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CHARGER + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strCharger + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_STATUS + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strStatus + "</TD></TR>";

	if (g_strOpt148 == "1")
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_CLIPNAME + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceClipName + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CONSERVE_TERM + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strConserveTerm + "</TD></TR>";
	}
	else
	{
		strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CONSERVE_TERM + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strConserveTerm + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_SECURITY_LEVEL + "</TD>";
		strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strSecurityLevel + "</TD></TR>";
	}

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_BOUND + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceBound + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_IS_POST + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strIsPost + "</TD></TR>";

	objDocumentInfo.setAttribute("view", "Y");
	objDocumentInfo.innerHTML = strDocumentInfo;
}

function drawApprovalInfo(strLineName)
{
	var objApprovalInfo = document.getElementById("idApprovalInfo");
	var strDisplayPos = objApprovalInfo.getAttribute("pos");

	var strApprovalInfo = "";
	var strOpinionInfo = "";
	strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";
	if (g_strVersion == "3.5")
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_head' width='130'>";
	strApprovalInfo = strApprovalInfo + LABEL_DEPT_NAME;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='60'>";

	if ((strDisplayPos == "GRD") || (strDisplayPos == "SGRD"))
		strApprovalInfo = strApprovalInfo + LABEL_LEVEL;
	else if ((strDisplayPos == "POS") || (strDisplayPos == "SPOS"))
		strApprovalInfo = strApprovalInfo + LABEL_POSITION;
	else if (strDisplayPos == "TLT")
		strApprovalInfo = strApprovalInfo + LABEL_TITLENAME;

	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='120'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVER;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='90'>";
	strApprovalInfo = strApprovalInfo + LABEL_ROLE;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='110'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVE_DATE;
	strApprovalInfo = strApprovalInfo + "</TD></TR><TR><TD colspan='9'>";
	strApprovalInfo = strApprovalInfo + "<DIV id='idFlowContent' name='divFlowContent' style='overflow:auto;width:100%;height:140;'>";

	var objApprovalLine = null;
	var objApprover = null;

	var strApprovalLineName = "";
	var strCurrentApprovalLine = "";
	var strApprovalDocStatus = "";
	var strApproverDeptName = "";
	var strUserPosition = "";
	var strApproverUserName = "";
	var strApproverRepName = "";
	var strApproverRole = "";
	var strApproverAction = "";
	var strApproverActionString = "";
	var strApproverType = "";
	var strApproverAdditionalRole = "";
	var strApproverSerialOrder = "";
	var strApproverRoleString = "";
	var strApproverSignDate = "";
	var strOpinion = "";
	var strIsDocModified = "";
	var strIsLineModified = "";
	var strIsAttachModified = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		if (strLineName == "activeLine")
			objApprovalLine = opener.getActiveApprovalLine();
		else
			objApprovalLine = opener.getApprovalLineByLineName(strLineName);
		if (objApprovalLine != null)
		{
			strApprovalLineName = opener.getLineName(objApprovalLine);
			strApprovalDocStatus = opener.getDocStatus(objApprovalLine);
			objApprover = opener.getFirstApprover(objApprovalLine);
		}
	}
	else if (g_strOpenLocale == "LIST")
	{
		if (strLineName == "activeLine")
			objApprovalLine = getActiveApprovalLine();
		else
			objApprovalLine = getApprovalLineByLineName(strLineName);
		if (objApprovalLine != null)
		{
			strApprovalLineName = getLineName(objApprovalLine);
			strApprovalDocStatus = getDocStatus(objApprovalLine);
			objApprover = getFirstApprover(objApprovalLine);
		}
	}

	if (strApprovalLineName.indexOf("-") != -1)
	{
		strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD>";
		strApprovalInfo = strApprovalInfo + "<A href='#' onclick='javascript:onClickFullLine(&quot;" + strApprovalLineName + "&quot;);return(false);'><B>";
		strApprovalInfo = strApprovalInfo + "&#160;&#24;...";
		strApprovalInfo = strApprovalInfo + "</B></A></TD></TR></TABLE>";
	}
	if (g_strCabinet.indexOf("INSPECTION") != -1)
	{
		if (g_strOpenLocale == "DOCUMENT")
			var strSenderDocID = opener.getSenderDocID();
		else
			var strSenderDocID = getSenderDocID();
		strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD>";
		strApprovalInfo = strApprovalInfo + "<A href='#' onclick='javascript:onClickSenderLine(&quot;" + strSenderDocID + "&quot;);return(false);'><B>";
		strApprovalInfo = strApprovalInfo + "&#160;&#24;...";
		strApprovalInfo = strApprovalInfo + "</B></A></TD></TR></TABLE>";
	}

	while (objApprover != null)// && strApprovalDocStatus != "20")
	{
		if (g_strOpenLocale == "DOCUMENT")
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
			strApproverAdditionalRole = opener.getApproverAdditionalRole(objApprover);
			strApproverSignDate = opener.getApproverSignDate(objApprover);
			strOpinion = opener.getApproverOpinion(objApprover);
			strIsDocModified = opener.getApproverIsDocModified(objApprover);
			strIsLineModified = opener.getApproverIsLineModified(objApprover);
			strIsAttachModified = opener.getApproverIsAttachModified(objApprover);
		}
		else if (g_strOpenLocale == "LIST")
		{
			strApproverDeptName = getApproverDeptName(objApprover);
			strUserPosition = getUserPosition(objApprover, strDisplayPos);
			strApproverUserName = getApproverUserName(objApprover);
			strApproverRepName = getApproverRepName(objApprover);
			strApproverSerialOrder = getApproverSerialOrder(objApprover);
			strApproverRole = getApproverRole(objApprover);
			strApproverRoleString = getApproverRoleString(objApprover);
			strApproverAction = getApproverAction(objApprover);
			strApproverActionString = getApproverActionString(objApprover);
			strApproverType = getApproverType(objApprover);
			strApproverAdditionalRole = getApproverAdditionalRole(objApprover);
			strApproverSignDate = getApproverSignDate(objApprover);
			strOpinion = getApproverOpinion(objApprover);
			strIsDocModified = getApproverIsDocModified(objApprover);
			strIsLineModified = getApproverIsLineModified(objApprover);
			strIsAttachModified = getApproverIsAttachModified(objApprover);
		}

		if (strApproverRepName != "")
		{
			strApproverUserName = strApproverUserName + "(" + strApproverRepName + ")";
			strApproverRoleString = strApproverRoleString + "(" + ROLE_PROXY + ")";
		}

		if (strApproverType == "1")
			strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_1 + ROLE_DEPART;
		else if (strApproverType == "2")
		{
			if (strApproverRole != "34")
				strApproverRoleString = strApproverRoleString + "/" + ROLE_DOUBLE_2 + ROLE_DEPART;
		}

		if (strApproverActionString != "")
			strApproverRoleString = strApproverRoleString + "(" + strApproverActionString + ")";

		if ((parseInt(strApproverAdditionalRole,10) & 1) > 0)
			strApproverRoleString = strApproverRoleString + ",발의";
		if ((parseInt(strApproverAdditionalRole,10) & 2) > 0)
			strApproverRoleString = strApproverRoleString + ",보고";

		if (strApproverSignDate != "")
			strApproverSignDate = getDateDisplay(strApproverSignDate, "YYYY-MM-DD");
		else
		{
			var strDocStatus = "";
			var strIsOpen = "";
			if (g_strOpenLocale == "DOCUMENT")
			{
				strDocStatus = opener.getDocStatus(objApprovalLine);
				strIsOpen = opener.getApproverIsOpen(objApprover);
			}
			else if (g_strOpenLocale == "LIST")
			{
				strDocStatus = getDocStatus(objApprovalLine);
				strIsOpen = getApproverIsOpen(objApprover);
			}

			if (strDocStatus != "2")
			{
				if (strIsOpen == "Y")
					strApproverSignDate = APPROVE_IS_OPEN;
				else
					strApproverSignDate = APPROVE_ARRIVED;
			}
		}

		if (g_strVersion == "3.5")
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'><TR><TD class='ltb_center' style='border-bottom:none;' width='130'>";
		else
			strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='130'>";
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
		{
			strCurrentApprovalLine = strApprovalLineName + "-" + strApproverSerialOrder;
			strApprovalInfo = strApprovalInfo + "<A href='#' onclick='javascript:onClickLineName(&quot;" + strCurrentApprovalLine + "&quot;);return(false);'><B>";
			strApprovalInfo = strApprovalInfo + strApproverDeptName;
			strApprovalInfo = strApprovalInfo + "</B></A></TD>";
		}
		else
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
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
		{
			if (g_strVersion == "3.5")
				strApprovalInfo = strApprovalInfo + "<DIV view='N' id='idApprovalLine" + strCurrentApprovalLine + "' width='100%' style='border-bottom:1pt solid #cfe5fe'></DIV>";
			else
				strApprovalInfo = strApprovalInfo + "<DIV view='N' id='idApprovalLine" + strCurrentApprovalLine + "' width='100%' style='border-bottom:1pt solid #e0e0e0'></DIV>";
		}

		if ((strOpinion != "") || (strIsDocModified == "Y") || (strIsLineModified == "Y") || (strIsAttachModified == "Y"))
		{
			while(true)
			{
				if (strOpinion.indexOf("\r\n") != -1)
					strOpinion = strOpinion.replace("\r\n", "<BR>");
				else if (strOpinion.indexOf("\r") != -1)
					strOpinion = strOpinion.replace("\r", "<BR>");
				else if (strOpinion.indexOf("\n") != -1)
					strOpinion = strOpinion.replace("\n", "<BR>");

				if (strOpinion.indexOf("\r") == -1 && strOpinion.indexOf("\n") == -1)
					break;
			}
			if (g_strVersion == "3.5")
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'><TR><TD class='ltb_center' style='border-bottom:none;' width='110'>";
			else
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='110'>";
			strOpinionInfo = strOpinionInfo + strApproverUserName;
			strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='300'><SPAN style='overflow:auto;width:300;'>";
			strOpinionInfo = strOpinionInfo + strOpinion;
			strOpinionInfo = strOpinionInfo + "</SPAN></TD><TD class='ltb_center' style='border-bottom:none;' width='150'>";

			strOpinionInfo = strOpinionInfo + "<TABLE width='150'><TR><TD width='50'>";
			if (strIsDocModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "&nbsp;문서";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_doc.gif'/>" + "&nbsp;문서";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsLineModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "<A href='#' onclick=\"javascript:onClickModLine('";
					strOpinionInfo = strOpinionInfo + strApprovalLineName;
					strOpinionInfo = strOpinionInfo + "', '";
					strOpinionInfo = strOpinionInfo + strApproverSerialOrder;
					strOpinionInfo = strOpinionInfo + "');return(false);\"><IMG src='" + g_strBaseUrl + "app/ref/image/mod_line.gif'/>" + "&nbsp;경로</A>";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_line.gif'/>" + "&nbsp;경로";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsAttachModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "<A href='#' onclick=\"javascript:onClickModAttach('";
					strOpinionInfo = strOpinionInfo + strApprovalLineName;
					strOpinionInfo = strOpinionInfo + "', '";
					strOpinionInfo = strOpinionInfo + strApproverSerialOrder;
					strOpinionInfo = strOpinionInfo + "');return(false);\"><IMG src='" + g_strBaseUrl + "app/ref/image/mod_attach.gif'/>" + "&nbsp;첨부</A>";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_attach.gif'/>" + "&nbsp;첨부";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD></TR></TABLE></TD></TR></TABLE>";
		}
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
		{
			if (g_strVersion == "3.5")
				strOpinionInfo = strOpinionInfo + "<DIV view='N' id='idApprovalOpinion" + strCurrentApprovalLine + "' width='100%' style='border-bottom:1pt solid #cfe5fe'></DIV>";
			else
				strOpinionInfo = strOpinionInfo + "<DIV view='N' id='idApprovalOpinion" + strCurrentApprovalLine + "' width='100%' style='border-bottom:1pt solid #e0e0e0'></DIV>";
		}

		if (g_strOpenLocale == "DOCUMENT")
			objApprover = opener.getNextApprover(objApprover);
		else if (g_strOpenLocale == "LIST")
			objApprover = getNextApprover(objApprover);
	}
	strApprovalInfo = strApprovalInfo + "</DIV></TD></TR></TABLE>";

	strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";

	if (g_strVersion == "3.5")
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='1' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strApprovalInfo = strApprovalInfo + "<TABLE width='560' cellpadding='1' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";

	strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_head' width='120' nowrap='1'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVER;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='300'>";
	strApprovalInfo = strApprovalInfo + LABEL_OPINION;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='140'>";
	strApprovalInfo = strApprovalInfo + LABEL_MODIFIED;
	strApprovalInfo = strApprovalInfo + "</TD></TR><TR><TD colspan='3'>";
	strApprovalInfo = strApprovalInfo + "<DIV id='idOpinion' name='divOpinion' style='overflow:auto;width:100%;height:110;'>" + strOpinionInfo + "</DIV>";
	strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";

	objApprovalInfo.setAttribute("view", "Y");
	objApprovalInfo.innerHTML = strApprovalInfo;
}

function drawRecipientInfo()
{
	if (g_strOpenLocale == "DOCUMENT")
		g_nBodyCount = opener.getBodyCount();
	else if (g_strOpenLocale == "LIST")
		g_nBodyCount = getBodyCount();

	var objRecipientInfo = document.getElementById("idRecipientInfo");

	var strRecipientInfo = "";
	var strRecipientInfoByProposal = "";
	strRecipientInfo = strRecipientInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";
	if (g_strVersion == "3.5")
		strRecipientInfo = strRecipientInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f2f8ff' style='border-bottom:1pt solid #a5c9f9'>";
	else
		strRecipientInfo = strRecipientInfo + "<TABLE width='560' cellpadding='0' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strRecipientInfo = strRecipientInfo + "<TR><TD class='ltb_head' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_NAME;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_SYMBOL;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_REFNAME;
	strRecipientInfo = strRecipientInfo + "</TD><TD class='ltb_head' width='140'>";
	strRecipientInfo = strRecipientInfo + LABEL_VAR_DEPT_REFSYMBOL;
	strRecipientInfo = strRecipientInfo + "</TD></TR><TR><TD colspan='4'>";
	strRecipientInfo = strRecipientInfo + "<DIV id='idRecipientContent' name='divRecipientContent' style='overflow:auto;width:100%;height:280;'>";

	var objRecipientGroup = "";
	var objRecipient = "";
	var strRecipientDeptName = "";
	var strRecipientDeptSymbol = "";
	var strRecipientRefDeptName = "";
	var strRecipientRefDeptSymbol = "";
	for (var i = 1; i <= g_nBodyCount; ++i)
	{
		strRecipientInfoByProposal = "";
		var bPassRecip = false;
		var nGroupCnt = 1;
		if (g_nPassThrough == 1)
		{
			if (g_strOpenLocale == "DOCUMENT")
			{
				objRecipientGroup = opener.getRecipGroupByGroupType(i, "0");
				objRecipientGroup2 = opener.getRecipGroupByGroupType(i, "2");
			}
			else if (g_strOpenLocale == "LIST")
			{
				objRecipientGroup = getRecipGroupByGroupType(i, "0");
				objRecipientGroup2 = getRecipGroupByGroupType(i, "2");
			}
			if (objRecipientGroup2 != null)
			{
				bPassRecip = true;
				nGroupCnt = 2;
			}
		}
		else
		{
			if (g_strOpenLocale == "DOCUMENT")
				objRecipientGroup = opener.getRecipGroup(i);
			else if (g_strOpenLocale == "LIST")
				objRecipientGroup = getRecipGroup(i);
		}

		if (objRecipientGroup != null)
		{
			for (var i = 0; i < nGroupCnt; i++)
			{
				var nRecipType = 0;
				if (i == 0 && bPassRecip == true)
					var nRecipType = 1;
				if (i == 0)
				{
					if (g_strOpenLocale == "DOCUMENT")
						objRecipient = opener.getFirstRecipient(objRecipientGroup);
					else if (g_strOpenLocale == "LIST")
						objRecipient = getFirstRecipient(objRecipientGroup);
				}
				else
				{
					if (g_strOpenLocale == "DOCUMENT")
						objRecipient = opener.getFirstRecipient(objRecipientGroup2);
					else if (g_strOpenLocale == "LIST")
						objRecipient = getFirstRecipient(objRecipientGroup2);
				}

				while (objRecipient != null)
				{
					if (g_strOpenLocale == "DOCUMENT")
					{
						strRecipientDeptName = opener.getRecipientDeptName(objRecipient);
						strRecipientDeptSymbol = opener.getRecipientDeptSymbol(objRecipient);
						strRecipientRefDeptName = opener.getRecipientRefDeptName(objRecipient);
						strRecipientRefDeptSymbol = opener.getRecipientRefDeptSymbol(objRecipient);
					}
					else if (g_strOpenLocale == "LIST")
					{
						strRecipientDeptName = getRecipientDeptName(objRecipient);
						strRecipientDeptSymbol = getRecipientDeptSymbol(objRecipient);
						strRecipientRefDeptName = getRecipientRefDeptName(objRecipient);
						strRecipientRefDeptSymbol = getRecipientRefDeptSymbol(objRecipient);
					}

					if (g_strVersion == "3.5")
						strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'><TR><TD class='ltb_center' style='border-bottom:none;' width='140'>";
					else
						strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='140'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientDeptName;
					strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientDeptSymbol;
					strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientRefDeptName;
					strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
					strRecipientInfoByProposal = strRecipientInfoByProposal + strRecipientRefDeptSymbol;
					if (nRecipType == 1)
						strRecipientInfoByProposal = strRecipientInfoByProposal + "(경유) ";
					strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD></TR></TABLE>";

					if (g_strOpenLocale == "DOCUMENT")
						objRecipient = opener.getNextRecipient(objRecipient);
					else if (g_strOpenLocale == "LIST")
						objRecipient = getNextRecipient(objRecipient);
				}
			}
		}
		if (strRecipientInfoByProposal != "")
		{
			if (g_nBodyCount > 1)
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
	strRecipientInfo = strRecipientInfo + "</DIV></TD></TR></TABLE>";

	objRecipientInfo.setAttribute("view", "Y");
	objRecipientInfo.innerHTML = strRecipientInfo;
}

function onClickLineName(strApprovalLineName)
{
	var objApprovalLine = "";
	var strApprovalLineHtml = "";
	var strApprovalInfoHtml = "";
	var strOpinionHtml = "";
	var objDivApprovalLine = document.getElementById("idApprovalLine" + strApprovalLineName);
	var objDivApprovalOpinion = document.getElementById("idApprovalOpinion" + strApprovalLineName);
	var strView = objDivApprovalLine.getAttribute("view");
	if (strView == "N")
	{
		objDivApprovalLine.setAttribute("view", "Y");
		if (g_strOpenLocale == "DOCUMENT")
			objApprovalLine = opener.getApprovalLineByLineName(strApprovalLineName);
		else if (g_strOpenLocale == "LIST")
			objApprovalLine = getApprovalLineByLineName(strApprovalLineName);
		if (objApprovalLine != null)
		{
			strApprovalLineHtml = getApprovalLineHtml(objApprovalLine);
			var arrApprovalLineHtml = strApprovalLineHtml.split("\u0002");
			strApprovalInfoHtml = arrApprovalLineHtml[0];
			strOpinionHtml = arrApprovalLineHtml[1];
			objDivApprovalLine.innerHTML = strApprovalInfoHtml;
			objDivApprovalOpinion.innerHTML = strOpinionHtml;
		}

	}
	else
	{
		if (objDivApprovalLine.style.display == "none")
			objDivApprovalLine.style.display = "block";
		else
			objDivApprovalLine.style.display = "none";

		if (objDivApprovalOpinion.style.display == "none")
			objDivApprovalOpinion.style.display = "block";
		else
			objDivApprovalOpinion.style.display = "none";
	}
}

function getApprovalLineHtml(objApprovalLine)
{
	var objApprovalInfo = document.getElementById("idApprovalInfo");
	var strDisplayPos = objApprovalInfo.getAttribute("pos");

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
	var strApproverAdditionalRole = "";
	var strApproverSerialOrder = "";
	var strApproverSignDate = "";
	var strOpinion = "";
	var strIsDocModified = "";
	var strIsLineModified = "";
	var strIsAttachModified = "";
	var strBgColor = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		strApprovalLineName = opener.getLineName(objApprovalLine);
		objApprover = opener.getFirstApprover(objApprovalLine);
	}
	else if (g_strOpenLocale == "LIST")
	{
		strApprovalLineName = getLineName(objApprovalLine);
		objApprover = getFirstApprover(objApprovalLine);
	}

	if (((strApprovalLineName.split("-").length) % 2) == 0)
		strBgColor = "background-color:#ffffff;";
	else
		strBgColor = "";

	var strApprovalInfo = "";
	var strOpinionInfo = "";
	while (objApprover != null)
	{
		if (g_strOpenLocale == "DOCUMENT")
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
			strApproverAdditionalRole = opener.getApproverAdditionalRole(objApprover);
			strApproverSignDate = opener.getApproverSignDate(objApprover);
			strOpinion = opener.getApproverOpinion(objApprover);
			strIsDocModified = opener.getApproverIsDocModified(objApprover);
			strIsLineModified = opener.getApproverIsLineModified(objApprover);
			strIsAttachModified = opener.getApproverIsAttachModified(objApprover);
		}
		else if (g_strOpenLocale == "LIST")
		{
			strApproverDeptName = getApproverDeptName(objApprover);
			strUserPosition = getUserPosition(objApprover, strDisplayPos);
			strApproverUserName = getApproverUserName(objApprover);
			strApproverRepName = getApproverRepName(objApprover);
			strApproverSerialOrder = getApproverSerialOrder(objApprover);
			strApproverRole = getApproverRole(objApprover);
			strApproverRoleString = getApproverRoleString(objApprover);
			strApproverAction = getApproverAction(objApprover);
			strApproverActionString = getApproverActionString(objApprover);
			strApproverType = getApproverType(objApprover);
			strApproverAdditionalRole = getApproverAdditionalRole(objApprover);
			strApproverSignDate = getApproverSignDate(objApprover);
			strOpinion = getApproverOpinion(objApprover);
			strIsDocModified = getApproverIsDocModified(objApprover);
			strIsLineModified = getApproverIsLineModified(objApprover);
			strIsAttachModified = getApproverIsAttachModified(objApprover);
		}

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

		if ((parseInt(strApproverAdditionalRole,10) & 1) > 0)
			strApproverRoleString = strApproverRoleString + ",발의";
		if ((parseInt(strApproverAdditionalRole,10) & 2) > 0)
			strApproverRoleString = strApproverRoleString + ",보고";

		if (strApproverSignDate != "")
			strApproverSignDate = getDateDisplay(strApproverSignDate, "YYYY-MM-DD");
		else
		{
			var strDocStatus = "";
			var strIsOpen = "";
			if (g_strOpenLocale == "DOCUMENT")
			{
				strDocStatus = opener.getDocStatus(objApprovalLine);
				strIsOpen = opener.getApproverIsOpen(objApprover);
			}
			else if (g_strOpenLocale == "LIST")
			{
				strDocStatus = getDocStatus(objApprovalLine);
				strIsOpen = getApproverIsOpen(objApprover);
			}

			if (strDocStatus != "2")
			{
				if (strIsOpen == "Y")
					strApproverSignDate = APPROVE_IS_OPEN;
				else
					strApproverSignDate = APPROVE_ARRIVED;
			}
		}

		strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='100%' border='0'><TR><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='130'>";
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
		{
			strCurrentApprovalLine = strApprovalLineName + "-" + strApproverSerialOrder;
			strApprovalInfo = strApprovalInfo + "<A href='#' onclick='javascript:onClickLineName(&quot;" + strCurrentApprovalLine + "&quot;);return(false);'><B>";
			strApprovalInfo = strApprovalInfo + strApproverDeptName;
			strApprovalInfo = strApprovalInfo + "</B></A></TD>";
		}
		else
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
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
			strApprovalInfo = strApprovalInfo + "<DIV view='N' id='idApprovalLine" + strCurrentApprovalLine + "' width='100%'></DIV>";

		if ((strOpinion != "") || (strIsDocModified == "Y") || (strIsLineModified == "Y") || (strIsAttachModified == "Y"))
		{
			while(true)
			{
				if (strOpinion.indexOf("\r\n") != -1)
					strOpinion = strOpinion.replace("\r\n", "<BR>");
				else if (strOpinion.indexOf("\r") != -1)
					strOpinion = strOpinion.replace("\r", "<BR>");
				else if (strOpinion.indexOf("\n") != -1)
					strOpinion = strOpinion.replace("\n", "<BR>");

				if (strOpinion.indexOf("\r") == -1 && strOpinion.indexOf("\n") == -1)
					break;
			}
			if (g_strVersion == "3.5")
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe;" + strBgColor + "'><TR><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='110'>";
			else
				strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0;" + strBgColor + "'><TR><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='110'>";
			strOpinionInfo = strOpinionInfo + strApproverUserName;
			strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;" + strBgColor + "' width='300'><SPAN style='overflow:auto;width:300;'>";
			strOpinionInfo = strOpinionInfo + strOpinion;
			strOpinionInfo = strOpinionInfo + "</SPAN></TD><TD class='ltb_center' style='border-bottom:none;" + strBgColor + "' width='150'>";

			strOpinionInfo = strOpinionInfo + "<TABLE width='150'><TR><TD width='50'>";
			if (strIsDocModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "&nbsp;문서";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_doc.gif'/>" + "&nbsp;문서";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsLineModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "<A href='#' onclick=\"javascript:onClickModLine('";
					strOpinionInfo = strOpinionInfo + strApprovalLineName;
					strOpinionInfo = strOpinionInfo + "', '";
					strOpinionInfo = strOpinionInfo + strApproverSerialOrder;
					strOpinionInfo = strOpinionInfo + "');return(false);\"><IMG src='" + g_strBaseUrl + "app/ref/image/mod_line.gif'/>" + "&nbsp;경로</A>";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_line.gif'/>" + "&nbsp;경로";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsAttachModified == "Y")
			{
				if (g_strOpenLocale == "DOCUMENT")
				{
					strOpinionInfo = strOpinionInfo + "<A href='#' onclick=\"javascript:onClickModAttach('";
					strOpinionInfo = strOpinionInfo + strApprovalLineName;
					strOpinionInfo = strOpinionInfo + "', '";
					strOpinionInfo = strOpinionInfo + strApproverSerialOrder;
					strOpinionInfo = strOpinionInfo + "');return(false);\"><IMG src='" + g_strBaseUrl + "app/ref/image/mod_attach.gif'/>" + "&nbsp;첨부</A>";
				}
				else if (g_strOpenLocale == "LIST")
					strOpinionInfo = strOpinionInfo + "<IMG src='" + g_strBaseUrl + "app/ref/image/mod_attach.gif'/>" + "&nbsp;첨부";
			}
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD></TR></TABLE></TD></TR></TABLE>";
		}
		if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
			strOpinionInfo = strOpinionInfo + "<DIV view='N' id='idApprovalOpinion" + strCurrentApprovalLine + "' width='100%'></DIV>";

		if (g_strOpenLocale == "DOCUMENT")
			objApprover = opener.getNextApprover(objApprover);
		else if (g_strOpenLocale == "LIST")
			objApprover = getNextApprover(objApprover);
	}
	return strApprovalInfo + "\u0002" + strOpinionInfo;
}

function isString(strText)
{
	for (var i = 0; i < strText.length; i++)
	{
		var bYear = false;
		var strSub = strText.substring(i, i+1);
		if (strSub.charCodeAt(0) < 59)
			bYear = true;
		else
			bYear = false;
	}
	return bYear;
}

function getOpinion()
{
	var objOpinionInfo = document.getElementById("idOpinionInfo");
	var strDisplayPos = objOpinionInfo.getAttribute("pos");

	var strApprovalInfo = "";
	var strOpinionInfo = "";
	var objApprovalLine = null;
	var objApprover = null;
	var bActive = true;
	var strActiveLineName = "";
	var strApprovalLineName = "";
	var strApprovalFullLineName = "";

	objApprovalLine = opener.getActiveApprovalLine();
	while (objApprovalLine != null)
	{
		strApprovalLineName = opener.getLineName(objApprovalLine);
		if (bActive == true)
		{
			strActiveLineName = strApprovalLineName;
			if (strActiveLineName.indexOf("-") != -1)
			{
				var arrActiveLineName = strActiveLineName.split("-");
				strApprovalFullLineName = arrActiveLineName[0];
				objApprovalLine = opener.getApprovalLineByLineName(strApprovalFullLineName);
				if (objApprovalLine == null)
					break;
			}
			else
				strApprovalFullLineName = strActiveLineName;

			bActive = false;
		}
		else
		{
			if (strApprovalLineName.indexOf(strApprovalFullLineName + "-") == -1 ||
				strApprovalLineName != strActiveLineName)
			{
				objApprovalLine = opener.getNextApprovalLine(objApprovalLine);
				continue;
			}
		}

		objApprover = opener.getFirstApprover(objApprovalLine);
		while (objApprover != null)
		{
			var strApproverRole = opener.getApproverRole(objApprover);
			if ((strApproverRole == "30") || (strApproverRole == "31") || (strApproverRole == "32") || (strApproverRole == "33") || (strApproverRole == "34"))
			{
				objApprover = opener.getNextApprover(objApprover);
				continue;
			}
			var strApproverPosition = opener.getUserPosition(objApprover, strDisplayPos);
			var strApproverUserName = opener.getApproverUserName(objApprover);
			var strApproverRepName = opener.getApproverRepName(objApprover);
			if (strApproverRepName != "")
				strApproverUserName = strApproverUserName + "(" + strApproverRepName + ")";

			var strOpinion = opener.getApproverOpinion(objApprover);
			if (strOpinion != "")
			{
				while(true)
				{
					if (strOpinion.indexOf("\r\n") != -1)
						strOpinion = strOpinion.replace("\r\n", "<BR>");
					else if (strOpinion.indexOf("\r") != -1)
						strOpinion = strOpinion.replace("\r", "<BR>");
					else if (strOpinion.indexOf("\n") != -1)
						strOpinion = strOpinion.replace("\n", "<BR>");

					if (strOpinion.indexOf("\r") == -1 && strOpinion.indexOf("\n") == -1)
						break;
				}
				if (g_strVersion == "3.5")
					strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #cfe5fe'><TR><TD class='ltb_center' style='border-bottom:none;' width='50'>";
				else
					strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='50'>";
				strOpinionInfo = strOpinionInfo + strApproverPosition;
				strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='80'>";
				strOpinionInfo = strOpinionInfo + strApproverUserName;
				strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;text-align:left;' width='*'><SPAN style='overflow:auto;width:100%;'>";
				strOpinionInfo = strOpinionInfo + strOpinion;
				strOpinionInfo = strOpinionInfo + "</SPAN></TD></TR></TABLE>";
			}
			objApprover = opener.getNextApprover(objApprover);
		}
		objApprovalLine = opener.getNextApprovalLine(objApprovalLine);
	}

	strApprovalInfo = strApprovalInfo + "<TABLE width='100%' cellpadding='1' cellspacing='0' bgcolor='#f7f7f7' style='border-bottom:1pt solid #a3a3a3'>";
	strApprovalInfo = strApprovalInfo + "<TR><TD class='ltb_head' width='50' nowrap='1'>";

	if ((strDisplayPos == "GRD") || (strDisplayPos == "SGRD"))
		strApprovalInfo = strApprovalInfo + LABEL_LEVEL;
	else if ((strDisplayPos == "POS") || (strDisplayPos == "SPOS"))
		strApprovalInfo = strApprovalInfo + LABEL_POSITION;
	else if (strDisplayPos == "TLT")
		strApprovalInfo = strApprovalInfo + LABEL_TITLENAME;

	strApprovalInfo = strApprovalInfo + "<TD class='ltb_head' width='80' nowrap='1'>";
	strApprovalInfo = strApprovalInfo + LABEL_APPROVER;
	strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_head' width='80%'>";
	strApprovalInfo = strApprovalInfo + LABEL_OPINION;
	strApprovalInfo = strApprovalInfo + "</TD></TR><TR><TD colspan='3'>";
	strApprovalInfo = strApprovalInfo + "<DIV id='idOpinion' name='divOpinion' style='overflow:auto;width:100%;height:240;'>" + strOpinionInfo + "</DIV>";
	strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";

	objOpinionInfo.setAttribute("view", "Y");
	objOpinionInfo.innerHTML = strApprovalInfo;
}

function getPublication(strCode)
{
	var strReturn = "";
	var strSubString = strCode.substr(0, 1);
	var strPublic = "";
	if (strSubString == "1")
	{
		strReturn = "공개";
	}
	else if (strSubString == "2")
	{
		strReturn = "부분공개";
	}
	if (strSubString == "3")
	{
		strReturn = "비공개";
	}

	if (strSubString != "1")
	{
		var strLevel = "";
		for (var iLoop = 1; iLoop < strCode.length; ++iLoop)
		{
			strSubString = strCode.substr(iLoop, 1);
			if (strSubString.toUpperCase() == "Y")
			{
				var strTempLevel = iLoop;
				if (strLevel != "")
				{
					strTempLevel = ", " + strTempLevel
				}
				strLevel = strLevel + strTempLevel
			}
		}
		if (strLevel != "")
		{
			strReturn = strReturn + "(" + strLevel + ")";
		}
	}
	return strReturn;
}

function onClickModDoc(strApprovalLineName, strApproverSerialOrder)
{
//g_strOpenLocale == "DOCUMENT"
	opener.onViewModifiedDocument(strApprovalLineName, strApproverSerialOrder);
}

function onClickModLine(strApprovalLineName, strApproverSerialOrder)
{
//g_strOpenLocale == "DOCUMENT"
	opener.onViewModifiedLine(strApprovalLineName, strApproverSerialOrder);
}

function onClickModAttach(strApprovalLineName, strApproverSerialOrder)
{
//g_strOpenLocale == "DOCUMENT"
	opener.onViewModifiedAttach(strApprovalLineName, strApproverSerialOrder);
}

function getDocStatusInApprovalLine()
{
	var objApprovalLine = null;
	var strDocStatus = "";
	var strReturn = "";

	if (g_strOpenLocale == "DOCUMENT")
	{
		objApprovalLine = opener.getFirstApprovalLine();
		while (objApprovalLine != null)
		{
			strDocStatus = opener.getDocStatus(objApprovalLine);
			if (strDocStatus == 4)
			{
				strReturn = DOC_STATUS_4;
				break;
			}
			else if (strDocStatus == 9)
			{
				strReturn = DOC_STATUS_9;
				break;
			}
			objApprovalLine = opener.getNextApprovalLine(objApprovalLine);
		}
	}
	else if (g_strOpenLocale == "LIST")
	{
		objApprovalLine = getFirstApprovalLine();
		while (objApprovalLine != null)
		{
			strDocStatus = getDocStatus(objApprovalLine);
			if (strDocStatus == 4)
			{
				strReturn = DOC_STATUS_4;
				break;
			}
			else if (strDocStatus == 9)
			{
				strReturn = DOC_STATUS_9;
				break;
			}
			objApprovalLine = getNextApprovalLine(objApprovalLine);
		}
	}

	return strReturn;
}

function getRelatedDataByElement(strEleName)
{
	var strEleValue = "";

	var objRelatedSystem = null;
	var strRelatedSystemData = "";
	if (g_strOpenLocale == "DOCUMENT")
	{
		objRelatedSystem = opener.getRelatedSystemBySystemID(g_strSystemID);
		if (objRelatedSystem != null)
			strRelatedSystemData = opener.getReleatedSystemData(objRelatedSystem);
	}
	else if (g_strOpenLocale == "LIST")
	{
		objRelatedSystem = getRelatedSystemBySystemID(g_strSystemID);
		if (objRelatedSystem != null)
			strRelatedSystemData = getReleatedSystemData(objRelatedSystem);
	}

	if (strRelatedSystemData != "")
	{
		var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
		objXMLDom.async = false;
		if (objXMLDom.loadXML(strRelatedSystemData))
		{
			var objElement = objXMLDom.selectSingleNode("*//" + strEleName);
			if (objElement != null)
				strEleValue = objElement.text;
		}
	}

	return strEleValue;
}

function onClickFullLine(strApprovalLineName)
{
	var arrApprovalLineName = strApprovalLineName.split("-");
	var strApprovalFullLineName = arrApprovalLineName[0];
/*
	var objApprovalFullLine = null;
	var objApprovalLine = null;
	if (g_strOpenLocale == "DOCUMENT")
	{
		objApprovalFullLine = opener.getApprovalLineByLineName(strApprovalFullLineName);
		objApprovalLine = opener.getApprovalLineByLineName(strApprovalLineName);
	}
	else if (g_strOpenLocale == "LIST")
	{
		objApprovalFullLine = getApprovalLineByLineName(strApprovalFullLineName);
		objApprovalLine = getApprovalLineByLineName(strApprovalLineName);
	}

	if (objApprovalFullLine != null && objApprovalLine != null)
	{
		objApprovalFullLine.setAttribute("IS_ACTIVE", "Y");
		objApprovalLine.setAttribute("IS_ACTIVE", "N");
	}
*/
	drawApprovalInfo(strApprovalFullLineName);
}

function onClickSenderLine(strSenderDocID)
{
	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveGetSenderLine.jsp?returnFunction=addLine&docid=" + strSenderDocID;

	var objHiddenFrm = document.getElementById("hidDataFrame");
	objHiddenFrm.src = strUrl;
}

function addLine(strHTML)
{
	var objFlowContent = document.getElementById("idFlowContent");
	var objTables = objFlowContent.getElementsByTagName("TABLE");
	for (var i = 1; i < objTables.length; i++)
	{
		strHTML += objTables.item(i).outerHTML;
	}
	objFlowContent.innerHTML = strHTML;
}

function getAnnouncementStatusName(strValue)
{
	var strNameValue = "";
	if (strValue == "0")
		strNameValue = ANNOUNCEMENT_STATUS_NO;

	else if (strValue == "1")
		strNameValue = ANNOUNCEMENT_STATUS_WAITING;

	else if (strValue == "2")
		strNameValue = ANNOUNCEMENT_STATUS_DONE;

	return strNameValue;
}
