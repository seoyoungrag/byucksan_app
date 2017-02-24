function setItemInfo(strType)
{
	var strView = "View";
	if (strType == "MOD")
		strView = "";

	var strTitle = getTitle("1");
	setValueById(strTitle, "idDocTitle" + strView);

	var strDocNo = 	getClassInfo();
	setValueById(strDocNo, "idDocNo" + strView);

	var objApprovalLine = getFirstApprovalLine();
	var objApprover = getApproverByRole(objApprovalLine, "5");
	var strUserName = getApproverUserName(objApprover);
	setValueById(strUserName, "idApprover" + strView);
	setUserInfo(objApprover, "idApprover" + strView, "APPR");
	var strCompleteDate = getApproverSignDate(objApprover);
	if (strType == "MOD")
	{
		if (strCompleteDate != "")
			setDateNTime("idCompleteDate" + strView, "regiHour", "regiMinute", strCompleteDate);
	}
	else
	{
		var arrDate = strCompleteDate.split(" ");
		setValueById(arrDate[0], "idCompleteDate" + strView);
	}

	var objDeliverer = getDelivererByDelivererType("1");
	var strEnforceDate = getDelivererSignDate(objDeliverer);
	if (strType == "MOD")
	{
		if (strEnforceDate != "")
			setDateNTime("idEnforceDate" + strView, "regiHour1", "regiMinute1", strEnforceDate);
	}
	else
	{
		var arrDate = strEnforceDate.split(" ");
		setValueById(arrDate[0], "idEnforceDate" + strView);
	}

	var strDocCategory = getDocCategory("1");
	var strEnforceBound = getEnforceBound("1");
	var objRdDocCategory = document.getElementsByName("rdCategory" + strView);
	if (strDocCategory == "OI")
	{
		objRdDocCategory[0].checked = true;
	}
	else if (strDocCategory == "OE")
	{
		objRdDocCategory[1].checked = true;
		var objRdEnforceBound = document.getElementsByName("rdEnforceBound" + strView);
		if (strEnforceBound == "I")
			objRdEnforceBound[0].checked = true;
		else if (strEnforceBound == "O")
			objRdEnforceBound[1].checked = true;
		else if (strEnforceBound == "C")
			objRdEnforceBound[2].checked = true;
		else
			objRdEnforceBound[2].checked = true;
		if (strType == "MOD")
		{
			for (var i = 0; i < objRdEnforceBound.length; ++i)
				objRdEnforceBound[i].disabled = false;
		}
	}
	else
	{
		objRdDocCategory[0].checked = true;
	}

	var objRecipGroup = getRecipGroup("1");
	if (objRecipGroup != null)
	{
		var objRecipient = getFirstRecipient(objRecipGroup);
		while (objRecipient != null)
		{
			var strRecipientDeptName = getRecipientDeptName(objRecipient);
			var strRecipientDeptId = getRecipientDeptCode(objRecipient);
			var strRecipientDeptSymbol = getRecipientDeptSymbol(objRecipient);
			var strRecipientDeptChief = getRecipientDeptChief(objRecipient);

			var objSelect = document.getElementById("idRecipient" + strView);
			objSelect.length += 1;
			objSelect.options[objSelect.length - 1].text = strRecipientDeptName;
			objSelect.options[objSelect.length - 1].value = strRecipientDeptId;

			objSelect.options[objSelect.length - 1].deptcode = strRecipientDeptId;
			objSelect.options[objSelect.length - 1].deptname = strRecipientDeptName;
			objSelect.options[objSelect.length - 1].deptsymbol = strRecipientDeptSymbol;
			objSelect.options[objSelect.length - 1].deptchief = strRecipientDeptChief;

			objRecipient = getNextRecipient(objRecipient);
		}
	}

	var strEnforceMethod = getEnforceMethod();
	setValueById(strEnforceMethod, "idMethod" + strView);

	var objApprover = getApproverByRole(objApprovalLine, "1");
	var strUserName = getApproverUserName(objApprover);
	setValueById(strUserName, "idSubmiter" + strView);
	setUserInfo(objApprover, "idSubmiter" + strView, "APPR");

	var objDeliverer = getDelivererByDelivererType("0");
	var strInspectDate = getDelivererSignDate(objDeliverer);
	if (strType == "MOD")
	{
		if (strInspectDate != "")
			setDateNTime("idInspectDate" + strView, "regiHour2", "regiMinute2", strInspectDate);
	}
	else
	{
		var arrDate = strInspectDate.split(" ");
		setValueById(arrDate[0], "idInspectDate" + strView);
	}

	var objDeliverer = getDelivererByDelivererType("4");
	var strReceiver = getDelivererUserName(objDeliverer);
	setValueById(strReceiver, "idReceiver" + strView);
	setUserInfo(objDeliverer, "idReceiver" + strView, "DELI");
}

function setDateNTime(strDateId, strHourId, strMinId, strDate)
{
	var arrDate = strDate.split(" ");
	initializeDate(strDateId, arrDate[0]);
//	var arrTime = arrDate[1].split(":");
//	initializeTime(strHourId, arrTime[0], strMinId, arrTime[1]);
}

function setUserInfo(objUser, strId, strType)
{
	var objTarget = document.getElementById(strId);
	if (strType == "APPR")
	{
		objTarget.setAttribute("deptname", getApproverDeptName(objUser));
		objTarget.setAttribute("deptid", getApproverDeptCode(objUser));
		objTarget.setAttribute("compname", getApproverCompany(objUser));
		objTarget.setAttribute("compid", "");
		objTarget.setAttribute("groupname","");
		objTarget.setAttribute("groupid", "");
		objTarget.setAttribute("gradeabbr", "");
		objTarget.setAttribute("grade", "");
		objTarget.setAttribute("positionabbr", getApproverUserPositionAbbr(objUser));
		objTarget.setAttribute("position", getApproverUserPosition(objUser));
		objTarget.setAttribute("username", getApproverUserName(objUser));
		objTarget.setAttribute("uid", "");
		objTarget.setAttribute("userid", getApproverUserID(objUser));
	}
	else if (strType == "DELI")
	{
		objTarget.setAttribute("deptname", getDelivererDeptName(objUser));
		objTarget.setAttribute("deptid", getDelivererDeptCode(objUser));
		objTarget.setAttribute("compname", getDelivererCompany(objUser));
		objTarget.setAttribute("compid", "");
		objTarget.setAttribute("groupname","");
		objTarget.setAttribute("groupid", "");
		objTarget.setAttribute("gradeabbr", "");
		objTarget.setAttribute("grade", "");
		objTarget.setAttribute("positionabbr", getDelivererUserPositionAbbr(objUser));
		objTarget.setAttribute("position", getDelivererUserPosition(objUser));
		objTarget.setAttribute("username", getDelivererUserName(objUser));
		objTarget.setAttribute("uid", "");
		objTarget.setAttribute("userid", getDelivererUserID(objUser));
	}
}