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
	var strDeptId = "";
	var strSignDate = "";
	if (strType == "3")
	{
		var strTargetId = "idReceiver";
		var objTarget = document.getElementById(strTargetId);
		strUserId = objTarget.userid;
		strUserName = objTarget.username;
		strUserPosition = objTarget.position;
		strUserPositionAbbr = objTarget.positionabbr;
		strCompany = objTarget.compname;
		strDeptName = objTarget.deptname;
		strDeptId = objTarget.deptid;
		strSignDate = getValueById("idReceiveDate") + " 00:00:00";	// + getValueById("regiHour") + ":" + getValueById("regiMinute") + ":00";

		setRecvDate(strSignDate);
	}
	else if (strType == "1")
		strDeptName = getValueById("idSendDept");

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
	setDelivererDeptCode(objDeliverer, strDeptId);
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

function setApproverInfo(strType)
{
	var objApprovalLine = getActiveApprovalLine();

	var strRole = "";
	var strTargetId = "";
	var strApproverAction = "";
	var strSignDate = "";
	if (strType == "20")
	{
		strRole = "20";
		strTargetId = "idCharger";
		strApproverAction = "2";
	}
	var objTarget = document.getElementById(strTargetId);
	var objApprover = getApproverByRole(objApprovalLine, strRole);
	if (objApprover != null)
		removeApprover(objApprover);
	objApprover = addApprover(objApprovalLine);

	setApproverIsActive(objApprover, "N");
	setApproverSerialOrder(objApprover, "0");
	setApproverParallelOrder(objApprover, "0");
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
	setApproverIsSigned(objApprover, "");
	setApproverSignDate(objApprover, "");
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

function onBeforeSend()
{
	var strTargetId = "idReceiver";
	var objTarget = document.getElementById(strTargetId);

	if (objTarget.value == "")
	{
		alertMsg(ALERT_SELECT_RECEIVER2);
		return false;
	}
	else
		setDelivererInfo("3");

	var strSendDept = getValueById("idSendDept");
	if (strSendDept == "")
	{
		alertMsg(ALERT_INPUT_SENDDEPT);
		return false;
	}
	else
		setDelivererInfo("1");

	var strDocNo = getValueById("idDocNo");
	if (strDocNo == "")
	{
//		alertMsg(ALERT_INPUT_DOCNO);
//		return false;
	}

	setClassInfo(strDocNo);

	var strTitle = getValueById("idDocTitle");
	if (strTitle == "")
	{
		alertMsg(ALERT_INPUT_TITLE);
		return false;
	}
	else
		setTitle(strTitle, "1");

	var strTargetId = "idCharger";
	var objTarget = document.getElementById(strTargetId);

	if (objTarget.value == "")
	{
		alertMsg(ALERT_SELECT_CHARGER);
		return false;
	}
	else
	{
		setApproverInfo("20");
	}

	setDocCategory("O", "1");
	setFlowStatus("12");
	setFileInfo();
}