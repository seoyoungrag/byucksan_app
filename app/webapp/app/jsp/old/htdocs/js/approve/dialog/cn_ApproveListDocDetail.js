function drawDocumentInfo(nBodyCount)
{
	var objDocumentInfo = document.getElementById("idDocumentInfo");

	var strTitle = "";
	var strDocCategory = "";
	var strEnforceBound = "";
	for (var i = 1; i <= nBodyCount; ++i)
	{
		if (nBodyCount > 1)
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
	var strDocNo = getClassInfo();
	var strDraftConv = getDraftConserve();
	var strEnforceConv = getEnforceConserve();
	var strSubmiter = getSubmiterName();
	var strSubmitDate = getSubmitDate();
	var strCurrent = getCurrentName();
	var strCurrentRole = getCurrentRoleString();
	var strCompleteName = getCompleteName();

	if (strCompleteName != "")
		strCurrent = "-";
	var strCompleteDate = getCompleteDate();
	var strStatus = getApprovalStatus();

	var strAccessLevel = getAccessLevel();
	var strPublicLevel = getPublicLevel()
	var strUrgency = getUrgency();
	var strEnforceDate = getEnforceDate();

	var strDocumentInfo = "";

	strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='0' cellspacing='0' width='560' style='border-bottom:1pt solid #a3a3a3'><TR><TD height='5'></TD></TR></TABLE>";
	strDocumentInfo = strDocumentInfo + "<TABLE cellpadding='2' cellspacing='1' width='560' bgcolor='#ffffff' style='border-bottom:1pt solid #a3a3a3'>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_TITLE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' colspan='3'>" + strTitle + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DOC_NUMBER + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocNo + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_DRAFT_CONSERVE + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDraftConv + YEAR + "</TD></TR>";

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
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceConv + YEAR + "</TD></TR>";

	strDocumentInfo = strDocumentInfo + "<TR><TD class='tb_tit_center' width='20%' align='center'>" + LABEL_CATEGORY + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strDocCategory + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_tit_center' width='20%' align='center'>" + LABEL_ENFORCE_BOUND + "</TD>";
	strDocumentInfo = strDocumentInfo + "<TD class='tb_left' width='30%'>" + strEnforceBound + "</TD></TR></TABLE>";

	objDocumentInfo.setAttribute("view", "Y");
	objDocumentInfo.innerHTML = strDocumentInfo;

}

function drawApprovalInfo()
{
	var objApprovalInfo = document.getElementById("idApprovalInfo");
	var strDisplayPos = objApprovalInfo.getAttribute("pos");

	var strApprovalInfo = "";
	var strOpinionInfo = "";
	strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";
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

	var objApprovalLine = getActiveApprovalLine();
	var objApprover = getFirstApprover(objApprovalLine);
	while (objApprover != null)
	{
		strApprovalInfo = strApprovalInfo + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='130'>";
		strApprovalInfo = strApprovalInfo + getApproverDeptName(objApprover);
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='60'>";
		strApprovalInfo = strApprovalInfo + getUserPosition(objApprover, strDisplayPos);
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='120'>";
		strApprovalInfo = strApprovalInfo + getApproverUserName(objApprover);
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='90'>";
		strApprovalInfo = strApprovalInfo + getApproverRoleString(objApprover);
		strApprovalInfo = strApprovalInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='110'>";
		strApprovalInfo = strApprovalInfo + getApproverSignDate(objApprover);
		strApprovalInfo = strApprovalInfo + "</TD></TR></TABLE>";

		var strOpinion = getApproverOpinion(objApprover);
		var strIsDocModified = getApproverIsDocModified(objApprover);
		var strIsLineModified = getApproverIsLineModified(objApprover);
		var strIsAttachModified = getApproverIsAttachModified(objApprover);
		if ((strOpinion != "") || (strIsDocModified == "Y") || (strIsLineModified == "Y") || (strIsAttachModified == "Y"))
		{
			strOpinionInfo = strOpinionInfo + "<TABLE cellspacing='0' width='100%' border='0'><TR><TD class='ltb_center' style='border-bottom:none;' width='110'>";
			strOpinionInfo = strOpinionInfo + getApproverUserName(objApprover);
			strOpinionInfo = strOpinionInfo + "</TD><TD class='ltb_center' style='border-bottom:none;' width='300'><SPAN style='overflow-y:visible;overflow-x:auto;width:300;'><PRE>";
			strOpinionInfo = strOpinionInfo + strOpinion;
			strOpinionInfo = strOpinionInfo + "</PRE></SPAN></TD><TD class='ltb_center' style='border-bottom:none;' width='150'>";

			strOpinionInfo = strOpinionInfo + "<TABLE width='150'><TR><TD width='50'>";
			if (strIsDocModified == "Y")
				strOpinionInfo = strOpinionInfo + "&nbsp;문서";
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsLineModified == "Y")
				strOpinionInfo = strOpinionInfo + "<A href='#' onclick='javascript:onClickModLine();return(false);'><IMG src='" + g_strBaseUrl + "image/" + g_strVersion + "/mod_line.gif'/></A>" + "&nbsp;경로";
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD><TD width='50'>";

			if (strIsAttachModified == "Y")
				strOpinionInfo = strOpinionInfo + "<A href='#' onclick='javascript:onClickModAttach();return(false);'><IMG src='" + g_strBaseUrl + "image/" + g_strVersion + "/mod_attach.gif'/></A>" + "&nbsp;첨부";
			else
				strOpinionInfo = strOpinionInfo + "&nbsp;";
			strOpinionInfo = strOpinionInfo + "</TD></TR></TABLE></TD></TR></TABLE>";
		}

		objApprover = getNextApprover(objApprover);
	}
	strApprovalInfo = strApprovalInfo + "</DIV></TD></TR></TABLE>";

	strApprovalInfo = strApprovalInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";
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

function drawRecipientInfo(nBodyCount)
{
	var objRecipientInfo = document.getElementById("idRecipientInfo");

	var strRecipientInfo = "";
	var strRecipientInfoByProposal = "";
	strRecipientInfo = strRecipientInfo + "<TABLE cellpadding='0' cellspacing='0' width='560'><TR><TD height='5'></TD></TR></TABLE>";
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

	for (var i = 1; i <= nBodyCount; ++i)
	{
		strRecipientInfoByProposal = "";
		var objRecipientGroup = getRecipGroup(i);
		if (objRecipientGroup != null)
		{
			var objRecipient = getFirstRecipient(objRecipientGroup);
			while (objRecipient != null)
			{
				strRecipientInfoByProposal = strRecipientInfoByProposal + "<TABLE cellspacing='0' width='100%' border='0' style='border-bottom:1pt solid #e0e0e0'><TR><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + getRecipientDeptName(objRecipient);
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + getRecipientDeptSymbol(objRecipient);
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + getRecipientRefDeptName(objRecipient);
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD><TD class='ltb_center' style='border-bottom:none;' width='140'>";
				strRecipientInfoByProposal = strRecipientInfoByProposal + getRecipientRefDeptSymbol(objRecipient);
				strRecipientInfoByProposal = strRecipientInfoByProposal + "</TD></TR></TABLE>";

				objRecipient = getNextRecipient(objRecipient);
			}
		}
		if (strRecipientInfoByProposal != "")
		{
			if (nBodyCount > 1)
			{
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

function onClickModDoc()
{
	alert("수정전 문서입니다.");
}

function onClickModLine()
{
	alert("수정전 경로입니다.");
}

function onClickModAttach()
{
	alert("수정전 첨부입니다.");
}