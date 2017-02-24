function onClickCategory(nSelect)
{
	var objRdEnforceBound = document.getElementsByName("rdEnforceBound");
	if (nSelect == 0)
	{
		for (var i=0; i < objRdEnforceBound.length; ++i)
		{
			objRdEnforceBound[i].disabled = true;
			objRdEnforceBound[i].checked = false;
		}
		disableUI();
		onClickRecipDelAll();
		setValueById("", "idEnforceDate");
		setValueById("", "idInspectDate");
		clearReceiverInfo("idReceiver");
		removeRecipGroup("1");
	}
	else if (nSelect == 1)
	{
		for (var i=0; i < objRdEnforceBound.length; ++i)
		{
			objRdEnforceBound[i].disabled = false;
			if (i == 0)
			{
				objRdEnforceBound[i].checked = true;
			}
		}
		enableUI();
		initializeDate("idEnforceDate", "");
		initializeDate("idInspectDate", "");
	}
}

function onClickInRecip(strId)
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetDepartment.jsp?deptid=" + g_strDeptCode + "&inputid=" + strId;
	window.open(strUrl, "Org_SetDept", "toolbar=no, resizable=no, status=yes, width=300, height=250");
//	var strUrl = g_strBaseUrl + "signature/dialog/CN_SignatureSelect.jsp?deptid=" + g_strDeptCode + "&treetype=0&signtype=1&keyurl=hkmi-2121";
//	window.open(strUrl, "Org_SetDept", "toolbar=no, resizable=no, status=yes, width=500, height=350");
}

function setDeptInfo(strDeptId, strDeptName, strDeptSymbol, strDeptChief, strId)
{
	var objRecipient = document.getElementById(strId);

	for (var i = 0 ; i < objRecipient.length; i++)
	{
		if ((objRecipient.options[i].text == strDeptName) && (objRecipient.options[i].value == strDeptId))
		{
			alert(ALERT_ALREADY_SELECTED_DEPT);
			return;
		}
	}
	objRecipient.length += 1;
	objRecipient.options[objRecipient.length - 1].text = strDeptName;
	objRecipient.options[objRecipient.length - 1].value = strDeptId;
	objRecipient.options[objRecipient.length - 1].setAttribute("deptcode", strDeptId);
	objRecipient.options[objRecipient.length - 1].setAttribute("deptname", strDeptName);
	objRecipient.options[objRecipient.length - 1].setAttribute("deptsymbol", strDeptSymbol);
	objRecipient.options[objRecipient.length - 1].setAttribute("deptchief", strDeptChief);
}

function onClickOutRecip(strInputId)
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiOutRecip.jsp?inputid=" + strInputId;
	window.open(strUrl, "Docflow_OutRecipi", "toolbar=no, resizable=no, status=yes, width=350, height=150");
}

function onClickRecipDel()
{
	var objRecipient = document.getElementById("idRecipient");
	for (var i = objRecipient.length; i > 0; --i)
	{
		if (objRecipient.options[i-1].selected)
		{
			objRecipient.options.remove(i-1);
		}
	}
}

function setRecipientInfo()
{
	removeRecipGroup("1");
	var objRecipGroup = addRecipGroup("1", "");

	var objSelect = document.getElementById("idRecipient");
	for (var i = 0; i < objSelect.length; ++i)
	{
		var strDeptCode = objSelect.options[i].deptcode;
		var strDeptName = objSelect.options[i].deptname;
		var strDeptSymbol = objSelect.options[i].deptsymbol;
		var strDeptChief = objSelect.options[i].deptchief;

		var objRecipient = addRecipient(objRecipGroup);

		setRecipientEnforceBound(objRecipient, "O");
		setRecipientDeptCode(objRecipient, strDeptCode);
		setRecipientDeptName(objRecipient, strDeptName);
		setRecipientDeptSymbol(objRecipient, strDeptSymbol);
		setRecipientDeptChief(objRecipient, strDeptChief);
		setRecipientRefDeptCode(objRecipient, "");
		setRecipientRefDeptName(objRecipient, "");
		setRecipientRefDeptSymbol(objRecipient, "");
		setRecipientRefDeptChief(objRecipient, "");
		setRecipientActualDeptCode(objRecipient, "");
		setRecipientAcceptor(objRecipient, "");
		setRecipientAcceptDate(objRecipient, "");
		setRecipientReceiptStatus(objRecipient, "");
	}
	return;
}

function setFileInfo()
{
	clearGeneralAttachInfo();
	var objCheckBoxs = getCheckBoxs("chkFile");
	if (objCheckBoxs.length > 0)
		setIsAttached("Y");
	else
		setIsAttached("N");
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		var strFileName = objCheckBoxs[i].getAttribute("filename");
		var strGUIDFileName = objCheckBoxs[i].getAttribute("guidfilename");
		var strFileSize = objCheckBoxs[i].getAttribute("filesize");
		var strLocation = "";
		addGeneralAttachInfo("1", strFileName, strGUIDFileName, strFileSize, strLocation);
	}
}

function setApproverInfo(strType)
{
	var objApprovalLine = getActiveApprovalLine();
	var strRole = "";
	var strTargetId = "";
	var strApproverAction = "";
	var strSignDate = "";
	var strSerialOrder = "0";
	var strParallelOrder = "0";
	if (strType == "1")
	{
		strRole = "1";
		strTargetId = "idSubmiter";
		strApproverAction = "2";
	}
	else if (strType == "5")
	{
		strSerialOrder = "1";
		strParallelOrder = "1";
		strRole = "5";
		strTargetId = "idApprover";
		strApproverAction = "6";
		if (getValueById("idCompleteDate") != "")
			strSignDate = getValueById("idCompleteDate") + " 00:00:00";		// + getValueById("regiHour") + ":" + getValueById("regiMinute") + ":00";
	}
	var objTarget = document.getElementById(strTargetId);

	var objApprover = getApproverByRole(objApprovalLine, strRole);
	if (objApprover != null)
		removeApprover(objApprover);
	objApprover = addApprover(objApprovalLine);

	setApproverIsActive(objApprover, "N");
	setApproverSerialOrder(objApprover, strSerialOrder);
	setApproverParallelOrder(objApprover, strParallelOrder);
	setApproverUserID(objApprover, objTarget.userid);
	setApproverUserName(objApprover, objTarget.username);
	setApproverUserPosition(objApprover, objTarget.position);
	setApproverUserPositionAbbr(objApprover, objTarget.positionabbr);
	setApproverUserLevel(objApprover, "");
	setApproverUserLevelAbbr(objApprover, "");
	setApproverUserDuty(objApprover, "");
	setApproverUserTitle(objApprover, "");
	setApproverCompany(objApprover, objTarget.compname);
	setApproverDeptName(objApprover, objTarget.deptname);
	setApproverDeptCode(objApprover, objTarget.deptid);
	setApproverIsSigned(objApprover, "Y");
	setApproverSignDate(objApprover, strSignDate);
	setApproverSignFileName(objApprover, "");
	setApproverRole(objApprover, strRole);
	setApproverIsOpen(objApprover, "");
	setApproverAction(objApprover, strApproverAction);
	setApproverType(objApprover, "0");
	setApproverKeepStatus(objApprover, "");
	setApproverEmptyReason(objApprover, "");
	setApproverIsDocModified(objApprover, "");
	setApproverIsLineModified(objApprover, "");
	setApproverIsAttachModified(objApprover, "");
	setApproverOpinion(objApprover, "");
	setApproverRepID(objApprover, "");
	setApproverRepName(objApprover, "");
	setApproverRepPosition(objApprover, "");
	setApproverRepPositionAbbr(objApprover, "");
	setApproverRepLevel(objApprover, "");
	setApproverRepLevelAbbr(objApprover, "");
	setApproverRepDuty(objApprover, "");
	setApproverRepTitle(objApprover, "");
	setApproverRepCompany(objApprover, "");
	setApproverRepDeptName(objApprover, "");
	setApproverRepDeptCode(objApprover, "");
}

function setDelivererInfo(strType)
{
	var objDeliverer = getDelivererByDelivererType(strType);
	if (objDeliverer != null)
		removeDeliverer(objDeliverer);

	objDeliverer = addDeliverer();
	var strUserId = "";
	var strUserName = "";
	var strUserPosition = "";
	var strUserPositionAbbr = "";
	var strCompany = "";
	var strDeptName = "";
	var strSignDate = "";
	if (strType == "4")
	{
		var strTargetId = "idReceiver";
		var objTarget = document.getElementById(strTargetId);
		strUserId = objTarget.userid;
		strUserName = objTarget.username;
		strUserPosition = objTarget.position;
		strUserPositionAbbr = objTarget.positionabbr;
		strCompany = objTarget.compname;
		strDeptName = objTarget.deptname;
	}
	else if (strType == "0")
	{
		if (getValueById("idInspectDate") != "")
			strSignDate = getValueById("idInspectDate") + " 00:00:00";	// + getValueById("regiHour2") + ":" + getValueById("regiMinute2") + ":00";
	}
	else if (strType == "1")
	{
		if (getValueById("idEnforceDate") != "")
			strSignDate = getValueById("idEnforceDate") + " 00:00:00";	// + getValueById("regiHour1") + ":" + getValueById("regiMinute1") + ":00";

		setSendDate(strSignDate);
	}

	setDelivererType(objDeliverer, strType);
	setDelivererUserID(objDeliverer, strUserId);
	setDelivererUserName(objDeliverer, strUserName);
	setDelivererUserPosition(objDeliverer, strUserPosition);
	setDelivererUserPositionAbbr(objDeliverer, strUserPositionAbbr);
	setDelivererUserLevel(objDeliverer, "");
	setDelivererUserLevelAbbr(objDeliverer, "");
	setDelivererUserDuty(objDeliverer, "");
	setDelivererUserTitle(objDeliverer, "");
	setDelivererCompany(objDeliverer, strCompany);
	setDelivererDeptName(objDeliverer, strDeptName);
	setDelivererDeptCode(objDeliverer, "");
	setDelivererSignDate(objDeliverer, strSignDate);
	setDelivererSignFilename(objDeliverer, "");
	setDelivererRepID(objDeliverer, "");
	setDelivererRepName(objDeliverer, "");
	setDelivererRepPosition(objDeliverer, "");
	setDelivererRepPositionAbbr(objDeliverer, "");
	setDelivererRepLevel(objDeliverer, "");
	setDelivererRepLevelAbbr(objDeliverer, "");
	setDelivererRepDuty(objDeliverer, "");
	setDelivererRepTitle(objDeliverer, "");
	setDelivererRepCompany(objDeliverer, "");
	setDelivererRepDeptName(objDeliverer, "");
	setDelivererRepDeptCode(objDeliverer, "");
}

function onBeforeSend()
{
	var strTitle = getValueById("idDocTitle");
	if (strTitle == "")
	{
		alertMsg(ALERT_INPUT_TITLE);
		return false;
	}
	else
		setTitle(strTitle, "1");

	var strDocNo = getValueById("idDocNo");
	if (strDocNo == "")
	{
//		alertMsg(ALERT_INPUT_DOCNO);
//		return false;
	}

	setClassInfo(strDocNo);

	var strCategory = getValueByName("rdCategory");
	setDocCategory(strCategory, "1");

	if (strCategory == "OI")
		setFlowStatus("1");
	else if (strCategory == "OE")
		setFlowStatus("6");

	var strCompleteDate = getValueById("idCompleteDate");		// + " " + getValueById("regiHour") + ":" + getValueById("regiMinute") + ":00";
	var strEnforceDate = getValueById("idEnforceDate");		// + " " + getValueById("regiHour1") + ":" + getValueById("regiMinute1") + ":00";
	var strInspectDate = getValueById("idInspectDate");		// + " " + getValueById("regiHour2") + ":" + getValueById("regiMinute2") + ":00";
	if (strCategory != "OI")
	{
		if (strCompleteDate > strEnforceDate)
		{
			alertMsg(ALERT_NOT_MATCH_COMPLETE_ENFORCE);
			return false;
		}
	}

	var strEnforceBound = getValueByName("rdEnforceBound");
	setEnforceBound(strEnforceBound, "1");

	if (strCategory != "OI")
	{
		var objSelect = document.getElementById("idRecipient");
		if (objSelect.length == 0)
		{
			alertMsg(ALERT_SELECT_RECIPIENT);
			return false;
		}
		else
			setRecipientInfo();
	}

	var strMethod = getValueById("idMethod");
	if (strCategory != "OI")
	{
		if (strMethod == "")
		{
			alertMsg(ALERT_INPUT_ENFORCE_METHOD);
			return false;
		}
	}
	setEnforceMethod(strMethod);

	var strTargetId = "idSubmiter";
	var objTarget = document.getElementById(strTargetId);
	if (objTarget.value == "")
	{
		alertMsg(ALERT_SELECT_SUBMITER);
		return false;
	}
	else
		setApproverInfo("1");

	var strTargetId = "idApprover";
	var objTarget = document.getElementById(strTargetId);
	if (objTarget.value == "")
	{
		alertMsg(ALERT_SELECT_APPROVER);
		return false;
	}
	else
		setApproverInfo("5");

	if (strCategory != "OI" && g_strOpt42 == "1")
	{
		if (strInspectDate > strEnforceDate)
		{
			alertMsg(ALERT_NOT_MATCH_ENFORCE_INSPECT);
			return false;
		}

		if (strCompleteDate > strInspectDate)
		{
			alertMsg(ALERT_NOT_MATCH_COMPLETE_INSPECT);
			return false;
		}
	}
	setDelivererInfo("1");
	if (g_strOpt42 == "1")
		setDelivererInfo("0");

	if (strCategory != "OI")
	{
		var strTargetId = "idReceiver";
		var objTarget = document.getElementById(strTargetId);
		if (objTarget.value == "")
		{
			alertMsg(ALERT_SELECT_RECEIVER);
			return false;
		}
	}
	setDelivererInfo("4");

	setFileInfo();
}

function disableUI()
{
	var objEnforceCal = document.getElementById("idEnforceCal");
	objEnforceCal.style.display = "none";

	var objRecipButton = document.getElementById("idRecipButton");
	objRecipButton.style.display = "none";

	var objRecip = document.getElementById("idRecipient");
	objRecip.size = "5";

	var objInspectCal = document.getElementById("idInspectCal");
	objInspectCal.style.display = "none";

	var objReceiverButton = document.getElementById("idReceiverButton");
	objReceiverButton.style.display = "none";
}

function enableUI()
{
	var objEnforceCal = document.getElementById("idEnforceCal");
	objEnforceCal.style.display = "block";

	var objRecipButton = document.getElementById("idRecipButton");
	objRecipButton.style.display = "block";

	var objRecip = document.getElementById("idRecipient");
	objRecip.size = "4";

	var objInspectCal = document.getElementById("idInspectCal");
	objInspectCal.style.display = "block";

	var objReceiverButton = document.getElementById("idReceiverButton");
	objReceiverButton.style.display = "block";
}

function onClickRecipDelAll()
{
	var objRecipient = document.getElementById("idRecipient");
	for (var i = objRecipient.length; i > 0; --i)
	{
		objRecipient.options.remove(i-1);
	}
}


function clearReceiverInfo(strTargetId)
{
	var objTarget = document.getElementById(strTargetId);
	objTarget.value = "";
	objTarget.userid = "";
	objTarget.username = "";
	objTarget.position = "";
	objTarget.positionabbr = "";
	objTarget.compname = "";
	objTarget.deptname = "";
}