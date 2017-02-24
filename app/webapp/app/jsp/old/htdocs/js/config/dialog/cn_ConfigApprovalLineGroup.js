
	function getActiveApprovalLine()
	{
		var objApprovalLine = null;
		if (g_nType == 1)
			objApprovalLine = document.getElementById("divLineList");

		return objApprovalLine;
	}

	function getCurrentLineName(approvalline)
	{
		var strLineName = "0";
		return strLineName;
	}

	function getCurrentLineType(approvalline)
	{
		var strLineType = "0";
		return strLineType;
	}

	function getLineName(approvalline)
	{
		var strLineName = "0";
		return strLineName;
	}

	function getDocStatus(approvalline)
	{
		var strDocStatus = "";
		return strDocStatus;
	}

	function getLineType(approvalline)
	{
		var objApprover = approvalline.getElementsByTagName("TABLE");
		var strLineType = "0";
		strLineType = objApprover[0].getAttribute("approver_type");
		if (strLineType == "1" || strLineType == "2")
			strLineType = "3";
		return strLineType;
	}

	function setLineType(approvalline, value)
	{
		approvalline.setAttribute("linetype", value);
	}

	function getCurrentSerial(approvalline)
	{
		var strCurrentSerial = "0";
		return strCurrentSerial;
	}

	function getCurrentParallel(approvalline)
	{
		var strCurrentParallel = "0";
		return strCurrentParallel;
	}

	function getFirstApprover(approvalline)
	{
		var objApprover = approvalline.getElementsByTagName("TABLE");
		return objApprover[0];
	}

	function getNextApprover(approver)
	{
		var objApprover = approver.nextSibling;
		return objApprover;
	}

	function getActiveApprover()
	{
		var objApprovalLine = document.getElementById("divLineList");
		var objApprover = objApprovalLine.getElementsByTagName("TABLE");
		return objApprover[0];
	}

	function removeAllApprover(approvalline)
	{
		approvalline.innerHTML = "";
	}

	function addApprovalLine(isactive, linename, docstatus, linetype, currentserial, currentparallel)
	{
		var objApprovalLine = document.getElementById("divLineList");
		return objApprovalLine;
	}

	function addApprover(approvalline)
	{
		approvalline.insertAdjacentHTML("beforeEnd", "<TABLE></TABLE>");
		var objApprover = approvalline.getElementsByTagName("TABLE");
		return objApprover[objApprover.length-1];
	}

	function getApproverSerialOrder(approver)
	{
		var strSerialOrder = approver.getAttribute("serialorder");
		return strSerialOrder;
	}

	function setApproverSerialOrder(approver, serialorder)
	{
		approver.setAttribute("serialorder", serialorder);
	}

	function getApproverParallelOrder(approver)
	{
		var strParallelOrder = approver.getAttribute("parallelorder");
		return strParallelOrder;
	}

	function setApproverParallelOrder(approver, parallelorder)
	{
		approver.setAttribute("parallelorder", parallelorder);
	}

	function getApproverUserID(approver)
	{
		var strUserID = approver.getAttribute("userid");
		return strUserID;
	}

	function setApproverUserID(approver, userid)
	{
		approver.setAttribute("userid", userid);
	}

	function getApproverUserName(approver)
	{
		var strUserName = approver.getAttribute("username");
		return strUserName;
	}

	function setApproverUserName(approver, username)
	{
		approver.setAttribute("username", username);
	}

	function getApproverUserPosition(approver)
	{
		var strUserPosition = approver.getAttribute("position");
		return strUserPosition;
	}

	function setApproverUserPosition(approver, userposition)
	{
		approver.setAttribute("position", userposition);
	}

	function getApproverUserPositionAbbr(approver)
	{
		var strUserPositionAbbr = approver.getAttribute("positionabbr");
		return strUserPositionAbbr;
	}

	function setApproverUserPositionAbbr(approver, userpositionabbr)
	{
		approver.setAttribute("positionabbr", userpositionabbr);
	}

	function getApproverUserLevel(approver)
	{
		var strUserLevel = approver.getAttribute("grade");
		return strUserLevel;
	}

	function setApproverUserLevel(approver, userlevel)
	{
		approver.setAttribute("level", userlevel);
	}

	function getApproverUserLevelAbbr(approver)
	{
		var strUserLevelAbbr = approver.getAttribute("gradeabbr");
		return strUserLevelAbbr;
	}

	function setApproverUserLevelAbbr(approver, userlevelabbr)
	{
		approver.setAttribute("levelabbr", userlevelabbr);
	}

	function getApproverUserDuty(approver)
	{
		var strUserDuty = approver.getAttribute("title");
		return strUserDuty;
	}

	function setApproverUserDuty(approver, userduty)
	{
		approver.setAttribute("duty", userduty);
	}

	function getApproverUserTitle(approver)
	{
		var strUserTitle = approver.getAttribute("gtpname");
		return strUserTitle;
	}

	function setApproverUserTitle(approver, usertitle)
	{
		approver.setAttribute("usertitle", usertitle);
	}

	function getApproverCompany(approver)
	{
		var strCompany = approver.getAttribute("compname");
		return strCompany;
	}

	function setApproverCompany(approver, company)
	{
		approver.setAttribute("company", company);
	}

	function getApproverDeptName(approver)
	{
		var strDeptName = approver.getAttribute("deptname");
		return strDeptName;
	}

	function setApproverDeptName(approver, deptname)
	{
		approver.setAttribute("deptname", deptname);
	}

	function getApproverDeptCode(approver)
	{
		var strDeptCode = approver.getAttribute("deptid");
		return strDeptCode;
	}

	function setApproverDeptCode(approver, deptcode)
	{
		approver.setAttribute("deptcode", deptcode);
	}

	function getApproverIsSigned(approver)
	{
		var strIsSigned = "N";
		return strIsSigned;
	}

	function setApproverIsSigned(approver, issigned)
	{
		approver.setAttribute("issigned", issigned);
	}

	function getApproverSignDate(approver)
	{
		var strSignDate = "";
		return strSignDate;
	}

	function setApproverSignDate(approver, signdate)
	{
		approver.setAttribute("signdate", signdate);
	}

	function getApproverSignFileName(approver)
	{
		var strSignFileName = "";
		return strSignFileName;
	}

	function setApproverSignFileName(approver, signfilename)
	{
		approver.setAttribute("signfilename", signfilename);
	}

	function getApproverRole(approver)
	{
		var strApproverRole = approver.getAttribute("approver_role");
		return strApproverRole;
	}

	function setApproverRole(approver, approverrole)
	{
		approver.setAttribute("approverrole", approverrole);
	}

	function getApproverIsOpen(approver)
	{
		var strIsOpen = "N";
		return strIsOpen;
	}

	function setApproverIsOpen(approver, isopen)
	{
		approver.setAttribute("isopen", isopen);
	}

	function getApproverAction(approver)
	{
		var strApproverAction = "0";
		return strApproverAction;
	}

	function setApproverAction(approver, approveraction)
	{
		approver.setAttribute("approveraction", approveraction);
	}

	function getApproverType(approver)
	{
		var strApproverType = approver.getAttribute("approver_type");
		return strApproverType;
	}

	function setApproverType(approver, approvertype)
	{
		approver.setAttribute("approvertype", approvertype);
	}

	function getApproverAdditionalRole(approver)
	{
		var strApproverAdditionalRole = approver.getAttribute("additional_role");
		return strApproverAdditionalRole;
	}

	function setApproverAdditionalRole(approver, additionalrole)
	{
		approver.setAttribute("additionalrole", additionalrole);
	}

	function getApproverKeepStatus(approver)
	{
		var strKeepStatus = "0";
		return strKeepStatus;
	}

	function setApproverKeepStatus(approver, keepstatus)
	{
		approver.setAttribute("keepstatus", keepstatus);
	}

	function getApproverEmptyReason(approver)
	{
		var strEmptyReason = approver.getAttribute("empty_reason");
		return strEmptyReason;
	}

	function setApproverEmptyReason(approver, emptyreason)
	{
		approver.setAttribute("emptyreason", emptyreason);
	}

	function getApproverIsDocModified(approver)
	{
		var strIsDocModified = "N";
		return strIsDocModified;
	}

	function setApproverIsDocModified(approver, isdocmodified)
	{
		approver.setAttribute("isdocmodified", isdocmodified);
	}

	function getApproverIsLineModified(approver)
	{
		var strIsLineModified = "N";
		return strIsLineModified;
	}

	function setApproverIsLineModified(approver, islinemodified)
	{
		approver.setAttribute("islinemodified", "");
	}

	function getApproverIsAttachModified(approver)
	{
		var strIsAttachModified = "N";
		return strIsAttachModified;
	}

	function setApproverIsAttachModified(approver, isattachmodified)
	{
		approver.setAttribute("isattachmodified", isattachmodified);
	}

	function getApproverOpinion(approver)
	{
		var strOpinion = "";
		return strOpinion;
	}

	function setApproverOpinion(approver, opinion)
	{
		approver.setAttribute("opinion", opinion);
	}

	function getApproverRepID(approver)
	{
		var strRepID = "";
		return strRepID;
	}

	function setApproverRepID(approver, repid)
	{
		approver.setAttribute("repid", repid);
	}

	function getApproverRepName(approver)
	{
		var strRepName = "";
		return strRepName;
	}

	function setApproverRepName(approver, repname)
	{
		approver.setAttribute("repname", repname);
	}

	function getApproverRepPosition(approver)
	{
		var strRepPosition = "";
		return strRepPosition;
	}

	function setApproverRepPosition(approver, repposition)
	{
		approver.setAttribute("repposition", repposition);
	}

	function getApproverRepPositionAbbr(approver)
	{
		var strRepPositionAbbr = "";
		return strRepPositionAbbr;
	}

	function setApproverRepPositionAbbr(approver, reppositionabbr)
	{
		approver.setAttribute("reppositionabbr", reppositionabbr);
	}

	function getApproverRepLevel(approver)
	{
		var strRepLevel = "";
		return strRepLevel;
	}

	function setApproverRepLevel(approver, replevel)
	{
		approver.setAttribute("replevel", replevel);
	}

	function getApproverRepLevelAbbr(approver)
	{
		var strRepLevelAbbr = "";
		return strRepLevelAbbr;
	}

	function setApproverRepLevelAbbr(approver, replevelabbr)
	{
		approver.setAttribute("replevelabbr", replevelabbr);
	}

	function getApproverRepDuty(approver)
	{
		var strRepDuty = "";
		return strRepDuty;
	}

	function setApproverRepDuty(approver, repduty)
	{
		approver.setAttribute("repduty", repduty);
	}

	function getApproverRepTitle(approver)
	{
		var strRepTitle = "";
		return strRepTitle;
	}

	function setApproverRepTitle(approver, reptitle)
	{
		approver.setAttribute("reptitle", reptitle);
	}

	function getApproverRepCompany(approver)
	{
		var strRepCompany = "";
		return strRepCompany;
	}

	function setApproverRepCompany(approver, repcompany)
	{
		approver.setAttribute("repcompany", repcompany);
	}

	function getApproverRepDeptName(approver)
	{
		var strRepDeptName = "";
		return strRepDeptName;
	}

	function setApproverRepDeptName(approver, repdeptname)
	{
		approver.setAttribute("repdeptname", repdeptname);
	}

	function getApproverRepDeptCode(approver)
	{
		var strRepDeptCode = "";
		return strRepDeptCode;
	}

	function setApproverRepDeptCode(approver, repdeptcode)
	{
		approver.setAttribute("repdeptcode", repdeptcode);
	}

	function clearApproveFlow()
	{
		var objDiv = document.getElementById("divLineList");
		objDiv.innerHTML = "";
	}

	function restoreApproveFlow()
	{
		var objDiv = document.getElementById("divLineList");
		setApprovalLine(objDiv);
	}

	function getFormInfo()
	{
		var strFormInfo = "";
		return strFormInfo;
	}