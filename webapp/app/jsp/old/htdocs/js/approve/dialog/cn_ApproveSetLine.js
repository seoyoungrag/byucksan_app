
	function getFlowStatus()
	{
		var strFlowStatus = opener.getFlowStatus();
		return strFlowStatus;
	}

	function getActiveApprovalLine()
	{
		var objApprovalLine = opener.getActiveApprovalLine();
		return objApprovalLine;
	}

	function getCurrentLineName()
	{
		var strLineName = opener.getCurrentLineName();
		return strLineName;
	}

	function getCurrentLineType()
	{
		var strLineType = opener.getCurrentLineType();
		return strLineType;
	}

	function getLineName(approvalline)
	{
		var strLineName = opener.getLineName(approvalline);
		return strLineName;
	}

	function getDocStatus(approvalline)
	{
		var strDocStatus = opener.getDocStatus(approvalline);
		return strDocStatus;
	}

	function getLineType(approvalline)
	{
		var strLineType = opener.getLineType(approvalline);
		return strLineType;
	}

	function setLineType(objApprovalLine, strValue)
	{
		opener.setLineType(objApprovalLine, strValue);
	}

	function getCurrentSerial(approvalline)
	{
		var strCurrentSerial = opener.getCurrentSerial(approvalline);
		return strCurrentSerial;
	}

	function getCurrentParallel(approvalline)
	{
		var strCurrentParallel = opener.getCurrentParallel(approvalline);
		return strCurrentParallel;
	}

	function getFirstApprover(approvalline)
	{
		var objApprover = opener.getFirstApprover(approvalline);
		return objApprover;
	}

	function getNextApprover(approver)
	{
		var objApprover = opener.getNextApprover(approver);
		return objApprover;
	}

	function getActiveApprover()
	{
		var objApprover = opener.getActiveApprover();
		return objApprover;
	}

	function removeAllApprover(approvalline)
	{
		opener.removeAllApprover(approvalline);
	}

	function addApprovalLine(isactive, linename, docstatus, linetype, currentserial, currentparallel)
	{
		var objApprovalLine = opener.addApprovalLine(isactive, linename, docstatus, linetype, currentserial, currentparallel);
		return objApprovalLine;
	}

	function addApprover(approvalline)
	{
		var objApprover = opener.addApprover(approvalline);
		return objApprover;
	}

	function getApproverSerialOrder(isnew, approver)
	{
		var strSerialOrder = "";
		if (isnew == "N"){strSerialOrder = opener.getApproverSerialOrder(approver);}
		else{strSerialOrder = g_totalIndex;}
		return strSerialOrder;
	}

	function setApproverSerialOrder(approver, serialorder)
	{
		opener.setApproverSerialOrder(approver, serialorder);
	}

	function getApproverParallelOrder(isnew, approver)
	{
		var strParallelOrder = "";
		if (isnew == "N"){strParallelOrder = opener.getApproverParallelOrder(approver);}
		else{strParallelOrder = g_totalIndex;}
		return strParallelOrder;
	}

	function setApproverParallelOrder(approver, parallelorder)
	{
		opener.setApproverParallelOrder(approver, parallelorder);
	}

	function getApproverUserID(isnew, approver)
	{
		var strUserID = "";
		if (isnew == "N"){strUserID = opener.getApproverUserID(approver);}
		else{strUserID = approver.getAttribute("userid");}
		return strUserID;
	}

	function setApproverUserID(approver, userid)
	{
		opener.setApproverUserID(approver, userid);
	}

	function getApproverUserName(isnew, approver)
	{
		var strUserName = "";
		if (isnew == "N"){strUserName = opener.getApproverUserName(approver);}
		else{strUserName = approver.getAttribute("username");}
		return strUserName;
	}

	function setApproverUserName(approver, username)
	{
		opener.setApproverUserName(approver, username);
	}

	function getApproverUserPosition(isnew, approver)
	{
		var strUserPosition = "";
		if (isnew == "N"){strUserPosition = opener.getApproverUserPosition(approver);}
		else{strUserPosition = approver.getAttribute("position");}
		return strUserPosition;
	}

	function setApproverUserPosition(approver, userposition)
	{
		opener.setApproverUserPosition(approver, userposition);
	}

	function getApproverUserPositionAbbr(isnew, approver)
	{
		var strUserPositionAbbr = "";
		if (isnew == "N"){strUserPositionAbbr = opener.getApproverUserPositionAbbr(approver);}
		else{strUserPositionAbbr = approver.getAttribute("positionabbr");}
		return strUserPositionAbbr;
	}

	function setApproverUserPositionAbbr(approver, userpositionabbr)
	{
		opener.setApproverUserPositionAbbr(approver, userpositionabbr);
	}

	function getApproverUserLevel(isnew, approver)
	{
		var strUserLevel = "";
		if (isnew == "N"){strUserLevel = opener.getApproverUserLevel(approver);}
		else{strUserLevel = approver.getAttribute("grade");}
		return strUserLevel;
	}

	function setApproverUserLevel(approver, userlevel)
	{
		opener.setApproverUserLevel(approver, userlevel);
	}

	function getApproverUserLevelAbbr(isnew, approver)
	{
		var strUserLevelAbbr = "";
		if (isnew == "N"){strUserLevelAbbr = opener.getApproverUserLevelAbbr(approver);}
		else{strUserLevelAbbr = approver.getAttribute("gradeabbr");}
		return strUserLevelAbbr;
	}

	function setApproverUserLevelAbbr(approver, userlevelabbr)
	{
		opener.setApproverUserLevelAbbr(approver, userlevelabbr);
	}

	function getApproverUserDuty(isnew, approver)
	{
		var strUserDuty = "";
		if (isnew == "N"){strUserDuty = opener.getApproverUserDuty(approver);}
		else{strUserDuty = approver.getAttribute("titlename");}
		return strUserDuty;
	}

	function setApproverUserDuty(approver, userduty)
	{
		opener.setApproverUserDuty(approver, userduty);
	}

	function getApproverUserTitle(isnew, approver)
	{
		var strUserTitle = "";
		if (isnew == "N"){strUserTitle = opener.getApproverUserTitle(approver);}
		else{strUserTitle = approver.getAttribute("gtpname");}
		return strUserTitle;
	}

	function setApproverUserTitle(approver, usertitle)
	{
		opener.setApproverUserTitle(approver, usertitle);
	}

	function getApproverCompany(isnew, approver)
	{
		var strCompany = "";
		if (isnew == "N"){strCompany = opener.getApproverCompany(approver);}
		else{strCompany = approver.getAttribute("compname");}
		return strCompany;
	}

	function setApproverCompany(approver, company)
	{
		opener.setApproverCompany(approver, company);
	}

	function getApproverDeptName(isnew, approver)
	{
		var strDeptName = "";
		if (isnew == "N"){strDeptName = opener.getApproverDeptName(approver);}
		else{strDeptName = approver.getAttribute("deptname");}
		return strDeptName;
	}

	function setApproverDeptName(approver, deptname)
	{
		opener.setApproverDeptName(approver, deptname);
	}

	function getApproverDeptCode(isnew, approver)
	{
		var strDeptCode = "";
		if (isnew == "N"){strDeptCode = opener.getApproverDeptCode(approver);}
		else{strDeptCode = approver.getAttribute("deptid");}
		return strDeptCode;
	}

	function setApproverDeptCode(approver, deptcode)
	{
		opener.setApproverDeptCode(approver, deptcode);
	}

	function getApproverIsSigned(isnew, approver)
	{
		var strIsSigned = "";
		if (isnew == "N"){strIsSigned = opener.getApproverIsSigned(approver);}
		else{strIsSigned = "N";}
		return strIsSigned;
	}

	function setApproverIsSigned(approver, issigned)
	{
		opener.setApproverIsSigned(approver, issigned);
	}

	function getApproverSignDate(isnew, approver)
	{
		var strSignDate = "";
		if (isnew == "N"){strSignDate = opener.getApproverSignDate(approver);}
		else{strSignDate = "";}
		return strSignDate;
	}

	function setApproverSignDate(approver, signdate)
	{
		opener.setApproverSignDate(approver, signdate);
	}

	function getApproverSignFileName(isnew, approver)
	{
		var strSignFileName = "";
		if (isnew == "N"){strSignFileName = opener.getApproverSignFileName(approver);}
		else{strSignFileName = "";}
		return strSignFileName;
	}

	function setApproverSignFileName(approver, signfilename)
	{
		opener.setApproverSignFileName(approver, signfilename);
	}

	function getApproverRole(isnew, approver, apprrole)
	{
		var strApproverRole = "";
		if (isnew == "N"){strApproverRole = opener.getApproverRole(approver);}
		else{strApproverRole = apprrole;}
		return strApproverRole;
	}

	function setApproverRole(approver, approverrole)
	{
		opener.setApproverRole(approver, approverrole);
	}

	function getApproverIsOpen(isnew, approver)
	{
		var strIsOpen = "";
		if (isnew == "N"){strIsOpen = opener.getApproverIsOpen(approver);}
		else{strIsOpen = "N";}
		return strIsOpen;
	}

	function setApproverIsOpen(approver, isopen)
	{
		opener.setApproverIsOpen(approver, isopen);
	}

	function getApproverAction(isnew, approver)
	{
		var strApproverAction = "";
		if (isnew == "N"){strApproverAction = opener.getApproverAction(approver);}
		else{strApproverAction = "0";}
		return strApproverAction;
	}

	function setApproverAction(approver, approveraction)
	{
		opener.setApproverAction(approver, approveraction);
	}

	function getApproverType(isnew, approver, apprtype)
	{
		var strApproverType = "";
		if (isnew == "N"){strApproverType = opener.getApproverType(approver);}
		else{strApproverType = apprtype;}
		return strApproverType;
	}

	function setApproverType(approver, approvertype)
	{
		opener.setApproverType(approver, approvertype);
	}

	function getApproverAdditionalRole(isnew, approver)
	{
		var strApproverAdditionalRole = "";
		if (isnew == "N"){strApproverAdditionalRole = opener.getApproverAdditionalRole(approver);}
		else{strApproverAdditionalRole = "0";}
		return strApproverAdditionalRole;
	}

	function setApproverAdditionalRole(approver, additionalrole)
	{
		opener.setApproverAdditionalRole(approver, additionalrole);
	}

	function getApproverKeepStatus(isnew, approver)
	{
		var strKeepStatus = "";
		if (isnew == "N"){strKeepStatus = opener.getApproverKeepStatus(approver);}
		else{strKeepStatus = "0";}
		return strKeepStatus;
	}

	function setApproverKeepStatus(approver, keepstatus)
	{
		opener.setApproverKeepStatus(approver, keepstatus);
	}

	function getApproverEmptyReason(isnew, approver, emptyreason)
	{
		var strEmptyReason = "";
		if (isnew == "N"){strEmptyReason = opener.getApproverEmptyReason(approver);}
		else{strEmptyReason = emptyreason;}
		return strEmptyReason;
	}

	function setApproverEmptyReason(approver, emptyreason)
	{
		opener.setApproverEmptyReason(approver, emptyreason);
	}

	function getApproverIsDocModified(isnew, approver)
	{
		var strIsDocModified = "";
		if (isnew == "N"){strIsDocModified = opener.getApproverIsDocModified(approver);}
		else{strIsDocModified = "N";}
		return strIsDocModified;
	}

	function setApproverIsDocModified(approver, isdocmodified)
	{
		opener.setApproverIsDocModified(approver, isdocmodified);
	}

	function getApproverIsLineModified(isnew, approver)
	{
		var strIsLineModified = "";
		if (isnew == "N"){strIsLineModified = opener.getApproverIsLineModified(approver);}
		else{strIsLineModified = "N";}
		return strIsLineModified;
	}

	function setApproverIsLineModified(approver, islinemodified)
	{
		opener.setApproverIsLineModified(approver, islinemodified);
	}

	function getApproverIsAttachModified(isnew, approver)
	{
		var strIsAttachModified = "";
		if (isnew == "N"){strIsAttachModified = opener.getApproverIsAttachModified(approver);}
		else{strIsAttachModified = "N";}
		return strIsAttachModified;
	}

	function setApproverIsAttachModified(approver, isattachmodified)
	{
		opener.setApproverIsAttachModified(approver, isattachmodified);
	}

	function getApproverOpinion(isnew, approver)
	{
		var strOpinion = "";
		if (isnew == "N"){strOpinion = opener.getApproverOpinion(approver);}
		else{strOpinion = "";}
		return strOpinion;
	}

	function setApproverOpinion(approver, opinion)
	{
		opener.setApproverOpinion(approver, opinion);
	}

	function getApproverRepID(isnew, approver)
	{
		var strRepID = "";
		if (isnew == "N"){strRepID = opener.getApproverRepID(approver);}
		else{strRepID = "";}
		return strRepID;
	}

	function setApproverRepID(approver, repid)
	{
		opener.setApproverRepID(approver, repid);
	}

	function getApproverRepName(isnew, approver)
	{
		var strRepName = "";
		if (isnew == "N"){strRepName = opener.getApproverRepName(approver);}
		else{strRepName = "";}
		return strRepName;
	}

	function setApproverRepName(approver, repname)
	{
		opener.setApproverRepName(approver, repname);
	}

	function getApproverRepPosition(isnew, approver)
	{
		var strRepPosition = "";
		if (isnew == "N"){strRepPosition = opener.getApproverRepPosition(approver);}
		else{strRepPosition = "";}
		return strRepPosition;
	}

	function setApproverRepPosition(approver, repposition)
	{
		opener.setApproverRepPosition(approver, repposition);
	}

	function getApproverRepPositionAbbr(isnew, approver)
	{
		var strRepPositionAbbr = "";
		if (isnew == "N"){strRepPositionAbbr = opener.getApproverRepPositionAbbr(approver);}
		else{strRepPositionAbbr = "";}
		return strRepPositionAbbr;
	}

	function setApproverRepPositionAbbr(approver, reppositionabbr)
	{
		opener.setApproverRepPositionAbbr(approver, reppositionabbr);
	}

	function getApproverRepLevel(isnew, approver)
	{
		var strRepLevel = "";
		if (isnew == "N"){strRepLevel = opener.getApproverRepLevel(approver);}
		else{strRepLevel = "";}
		return strRepLevel;
	}

	function setApproverRepLevel(approver, replevel)
	{
		opener.setApproverRepLevel(approver, replevel);
	}

	function getApproverRepLevelAbbr(isnew, approver)
	{
		var strRepLevelAbbr = "";
		if (isnew == "N"){strRepLevelAbbr = opener.getApproverRepLevelAbbr(approver);}
		else{strRepLevelAbbr = "";}
		return strRepLevelAbbr;
	}

	function setApproverRepLevelAbbr(approver, replevelabbr)
	{
		opener.setApproverRepLevelAbbr(approver, replevelabbr);
	}

	function getApproverRepDuty(isnew, approver)
	{
		var strRepDuty = "";
		if (isnew == "N"){strRepDuty = opener.getApproverRepDuty(approver);}
		else{strRepDuty = "";}
		return strRepDuty;
	}

	function setApproverRepDuty(approver, repduty)
	{
		opener.setApproverRepDuty(approver, repduty);
	}

	function getApproverRepTitle(isnew, approver)
	{
		var strRepTitle = "";
		if (isnew == "N"){strRepTitle = opener.getApproverRepTitle(approver);}
		else{strRepTitle = "";}
		return strRepTitle;
	}

	function setApproverRepTitle(approver, reptitle)
	{
		opener.setApproverRepDuty(approver, reptitle);
	}

	function getApproverRepCompany(isnew, approver)
	{
		var strRepCompany = "";
			if (isnew == "N"){strRepCompany = opener.getApproverRepCompany(approver);}
		else{strRepCompany = "";}
		return strRepCompany;
	}

	function setApproverRepCompany(approver, repcompany)
	{
		opener.setApproverRepCompany(approver, repcompany);
	}

	function getApproverRepDeptName(isnew, approver)
	{
		var strRepDeptName = "";
		if (isnew == "N"){strRepDeptName = opener.getApproverRepDeptName(approver);}
		else{strRepDeptName = "";}
		return strRepDeptName;
	}

	function setApproverRepDeptName(approver, repdeptname)
	{
		opener.setApproverRepDeptName(approver, repdeptname);
	}

	function getApproverRepDeptCode(isnew, approver)
	{
		var strRepDeptCode = "";
		if (isnew == "N"){strRepDeptCode = opener.getApproverRepDeptCode(approver);}
		else{strRepDeptCode = "";}
		return strRepDeptCode;
	}

	function setApproverRepDeptCode(approver, repdeptcode)
	{
		opener.setApproverRepDeptCode(approver, repdeptcode);
	}

	function clearApproveFlow()
	{
		opener.clearApproveFlow();
	}

	function restoreApproveFlow()
	{
		opener.restoreApproveFlow();
	}

	function setApproverIsActive(approver, isactive)
	{
		opener.setApproverIsActive(approver, isactive);
	}

	function getApproverByRole(approvalline, role)
	{
		return opener.getApproverByRole(approvalline, role);
	}

	function getPrevApprover(approver)
	{
		return opener.getPrevApprover(approver);
	}

	function getApproverCount(objApprovalLine)
	{
		return opener.getApproverCount(objApprovalLine);
	}
