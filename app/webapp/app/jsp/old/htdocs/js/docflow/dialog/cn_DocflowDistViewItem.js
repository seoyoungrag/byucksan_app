function setItemInfo(strType)
{
	var strView = "View";
	if (strType == "MOD")
		strView = "";

	var objDeliverer = getDelivererByDelivererType("2");
	var strReceiver = getDelivererUserName(objDeliverer);
	setValueById(strReceiver, "idReceiver" + strView);
	setUserInfo(objDeliverer, "idReceiver" + strView, "DELI");

	var objDeliverer = getDelivererByDelivererType("1");
	var strSendDept = getDelivererDeptName(objDeliverer);
	setValueById(strSendDept, "idSendDept" + strView);
	var strReceiveDate = getDelivererSignDate(objDeliverer);
	if (strType == "MOD")
		setDateNTime("idReceiveDate" + strView, "regiHour", "regiMinute", strReceiveDate);
	else
	{
		var arrDate = strReceiveDate.split(" ");
		setValueById(arrDate[0], "idReceiveDate" + strView);
	}


	var strDocNo = 	getClassInfo();
	setValueById(strDocNo, "idDocNo" + strView);

	var strTitle = getTitle("1");
	setValueById(strTitle, "idDocTitle" + strView);

	var objDeliverer = getDelivererByDelivererType("3");
	var strCharger = getDelivererUserName(objDeliverer);
	setValueById(strCharger, "idCharger" + strView);
	setUserInfo(objDeliverer, "idCharger" + strView, "APPR");
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