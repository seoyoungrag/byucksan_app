function onClickDept(strId)
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetDepartment.jsp?deptid=" + g_strDeptCode + "&inputid=" + strId;
	window.open(strUrl, "Org_SetDept", "toolbar=no, resizable=no, status=yes, width=300, height=250");
}

function setDeptInfo(strDeptId, strDeptName, strId)
{
//	var objElement = document.getElementById(strId);
//	objElement.value = strDeptName;
	if (g_strDivKind != "TRANSFER")
		setValueById(strDeptName, strId);
	else
	{
		var strDivListInfo = "";
		strDivListInfo = strDeptName + "$.$" +strDeptId;
		setDivListInfo(strDivListInfo);
	}
}

function onClickAddDivList(strDivKind)
{
	g_strDivKind = strDivKind;
	if (strDivKind == "PASSBY")
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowCivilPassBy.jsp";
		window.open(strUrl, "Docflow_CivilPassBy", "toolbar=no, resizable=no, status=yes, width=350, height=170");
	}
	else if (strDivKind == "PROCESS")
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowCivilProcess.jsp";
		window.open(strUrl, "Docflow_CivilPassBy", "toolbar=no, resizable=no, status=yes, width=350, height=190");
	}
	else if (strDivKind == "COOPERATION")
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowCivilCooperation.jsp";
		window.open(strUrl, "Docflow_CivilPassBy", "toolbar=no, resizable=no, status=yes, width=350, height=170");
	}
	else if (strDivKind == "TRANSFER")
	{
		var strDisposalContent = getValueById("idDisposalContent");
		if (strDisposalContent != VARIABLE_TRANSFER)
			alert(ALERT_SELECT_TRANSFERDEPT_ONLY_TRANS);
		else
			onClickDept('idTransfer');
	}
}

function onClickDelByName(strCheckName, strDivId, strDivKind)
{
	g_strDivKind = strDivKind;
	var objCheckBoxs = getCheckBoxs(strCheckName);
	if (objCheckBoxs.length > 0)
	{
		var bCheckFlag = getCheckFlag(objCheckBoxs);
		if (!bCheckFlag)
		{
			var nCheckCount = getCheckCount(objCheckBoxs);
			if (nCheckCount > 0)
			{
				var strDivTag = getRemainDiv(objCheckBoxs);
				drawDivTag(strDivId, strDivTag);
			}
			else
			{
				if (strDivKind == "PASSBY")
					alert(ALERT_SELECT_PASSBY4DEL);
				else if (strDivKind == "PROCESS")
					alert(ALERT_SELECT_PROCESS4DEL);
				else if (strDivKind == "COOPERATION")
					alert(ALERT_SELECT_COOPERATION4DEL);
				else if (strDivKind == "TRANSFER")
					alert(ALERT_SELECT_TRANSFER4DEL);
			}
		}
		else
		{
			drawAttachTag(strDivId, "<TABLE><TR><TD></TD></TR></TABLE>");
		}
	}
}

function onClickAllCheckByName(strCheckName)
{
	var objCheckBoxs = getCheckBoxs(strCheckName);
	var bCheckFlag = getCheckFlag(objCheckBoxs);
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		objCheckBoxs[i].checked = (!bCheckFlag);
	}
}

function setDivListInfo(strDivListInfo)
{
	var strDivTag = "";
	strDivTag = strDivTag + getOpenTBLTag4Civil();
	var objCheckBoxs = null;
	var strDivId = "";
	if (g_strDivKind == "PASSBY")
	{
		objCheckBoxs = getCheckBoxs("chkPassBy");
		strDivId = "idPassBy";
	}
	else if (g_strDivKind == "PROCESS")
	{
		objCheckBoxs = getCheckBoxs("chkProcess");
		strDivId = "idProcessSituation";
	}
	else if (g_strDivKind == "COOPERATION")
	{
		objCheckBoxs = getCheckBoxs("chkCooperation");
		strDivId = "idCooperation";
	}
	else if (g_strDivKind == "TRANSFER")
	{
		objCheckBoxs = getCheckBoxs("chkTransfer");
		strDivId = "idTransfer";
	}

	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		var strPrevDivListInfo = "";
		if (g_strDivKind == "PASSBY")
		{
			var strPrevPassByName = objCheckBoxs[i].getAttribute("passbyname");
			var strPrevPassByDate = objCheckBoxs[i].getAttribute("passbydate");
			var strPrevReceiveNo = objCheckBoxs[i].getAttribute("passbyno");
			strPrevDivListInfo = strPrevPassByName + "$.$" + strPrevPassByDate + "$.$" + strPrevReceiveNo;
		}
		else if (g_strDivKind == "PROCESS")
		{
			var strPrevProcessSituation = objCheckBoxs[i].getAttribute("processsituation");
			var strPrevDocNo = objCheckBoxs[i].getAttribute("docno");
			var strPrevDocTitle = objCheckBoxs[i].getAttribute("doctitle");
			var strPrevEnforceDate = objCheckBoxs[i].getAttribute("enforcedate");
			strPrevDivListInfo = strPrevProcessSituation + "$.$" + strPrevDocNo + "$.$" + strPrevDocTitle + "$.$" + strPrevEnforceDate;
		}
		else if (g_strDivKind == "COOPERATION")
		{
			var strPrevCooperationDept = objCheckBoxs[i].getAttribute("cooperationdept");
			var strPrevCooperationDate = objCheckBoxs[i].getAttribute("cooperationdate");
			var strPrevReplyDate = objCheckBoxs[i].getAttribute("replydate");
			strPrevDivListInfo = strPrevCooperationDept + "$.$" + strPrevCooperationDate + "$.$" + strPrevReplyDate;
		}
		else if (g_strDivKind == "TRANSFER")
		{
			var strPrevTransferDept = objCheckBoxs[i].getAttribute("transferdept");
			var strPrevTransferId = objCheckBoxs[i].getAttribute("transferid");
			strPrevDivListInfo = strPrevTransferDept + "$.$" + strPrevTransferId;
			if (strPrevDivListInfo == strDivListInfo)
			{
				alert(ALERT_ALREADY_SELECTED_TRANSFER);
				return;
			}
		}
		strDivTag = strDivTag + getDivTDTag(strPrevDivListInfo);
	}
	strDivTag = strDivTag + getDivTDTag(strDivListInfo);
	strDivTag = strDivTag + getCloseTBLTag4Civil();
	drawDivTag(strDivId, strDivTag);
}

function getRemainDiv(objCheckBoxs)
{
	var strDivTag = "";
	var nUnCheckCount = 0;
	strDivTag = strDivTag + getOpenTBLTag4Civil();
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		if (!objCheckBoxs[i].checked)
		{
			var strDivListInfo = "";
			if (g_strDivKind == "PASSBY")
			{
				var strPassByName = objCheckBoxs[i].getAttribute("passbyname");
				var strPassByDate = objCheckBoxs[i].getAttribute("passbydate");
				var strReceiveNo = objCheckBoxs[i].getAttribute("passbyno");
				strDivListInfo = strPassByName + "$.$" + strPassByDate + "$.$" + strReceiveNo;
			}
			else if (g_strDivKind == "PROCESS")
			{
				var strProcessSituation = objCheckBoxs[i].getAttribute("processsituation");
				var strDocNo = objCheckBoxs[i].getAttribute("docno");
				var strDocTitle = objCheckBoxs[i].getAttribute("doctitle");
				var strEnforceDate = objCheckBoxs[i].getAttribute("enforcedate");
				strDivListInfo = strProcessSituation + "$.$" + strDocNo + "$.$" + strDocTitle + "$.$" + strEnforceDate;
			}
			else if (g_strDivKind == "COOPERATION")
			{
				var strCooperationDept = objCheckBoxs[i].getAttribute("cooperationdept");
				var strCooperationDate = objCheckBoxs[i].getAttribute("cooperationdate");
				var strReplyDate = objCheckBoxs[i].getAttribute("replydate");
				strDivListInfo = strCooperationDept + "$.$" + strCooperationDate + "$.$" + strReplyDate;
			}
			else if (g_strDivKind == "TRANSFER")
			{
				var strTransferDept = objCheckBoxs[i].getAttribute("transferdept");
				var strTransferId = objCheckBoxs[i].getAttribute("transferid");
				strDivListInfo = strTransferDept + "$.$" + strTransferId;
			}
			strDivTag = strDivTag + getDivTDTag(strDivListInfo);
		}
	}
	strDivTag = strDivTag + getCloseTBLTag4Civil();
	return strDivTag;
}

function drawDivTag(strID, strTag)
{
	var objElement = document.getElementById(strID);
	objElement.innerHTML = strTag;
}

function getOpenTBLTag4Civil()
{
	var strOpenTBLTag = "<TABLE width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>";
	return strOpenTBLTag;
}

function getCloseTBLTag4Civil()
{
	var strCloseTBLTag = "</TABLE>";
	return strCloseTBLTag;
}

function getDivTDTag(strTDList)
{
	var strDivTDTag = "";
	var arrTDList = strTDList.split("$.$");
	if (g_strDivKind == "PASSBY")
	{
		strDivTDTag = strDivTDTag + "<TR><TD width='3%' align='center'><INPUT type='checkbox' id='idCheck' name='chkPassBy' passbyname='" + arrTDList[0] + "' passbydate='" + arrTDList[1] + "' passbyno='" + arrTDList[2] + "'></INPUT></TD>";
		strDivTDTag = strDivTDTag + "<TD width='42%' align='center'>" + arrTDList[0] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='30%' align='center'>" + arrTDList[1] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='25%' align='center'>" + arrTDList[2] + "</TD></TR>";
	}
	else if (g_strDivKind == "PROCESS")
	{
		strDivTDTag = strDivTDTag + "<TR><TD width='3%' align='center'><INPUT type='checkbox' id='idCheck' name='chkProcess' processsituation='" + arrTDList[0] + "' docno='" + arrTDList[1] + "' doctitle='" + arrTDList[2] + "' enforcedate='" + arrTDList[3] + "'></INPUT></TD>";
		strDivTDTag = strDivTDTag + "<TD width='17%' align='center'>" + arrTDList[0] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='20%' align='center'>" + arrTDList[1] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='40%' align='center'>" + arrTDList[2] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='20%' align='center'>" + arrTDList[3] + "</TD></TR>";
	}
	else if (g_strDivKind == "COOPERATION")
	{
		strDivTDTag = strDivTDTag + "<TR><TD width='3%' align='center'><INPUT type='checkbox' id='idCheck' name='chkCooperation' cooperationdept='" + arrTDList[0] + "' cooperationdate='" + arrTDList[1] + "' replydate='" + arrTDList[2] + "'></INPUT></TD>";
		strDivTDTag = strDivTDTag + "<TD width='27%' align='center'>" + arrTDList[0] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='35%' align='center'>" + arrTDList[1] + "</TD>";
		strDivTDTag = strDivTDTag + "<TD width='35%' align='center'>" + arrTDList[2] + "</TD>";
	}
	else if (g_strDivKind == "TRANSFER")
	{
		strDivTDTag = strDivTDTag + "<TR><TD width='3%' align='center'><INPUT type='checkbox' id='idCheck' name='chkTransfer' transferdept='" + arrTDList[0] + "' transferid='" + arrTDList[1] + "'></INPUT></TD>";
		strDivTDTag = strDivTDTag + "<TD width='97%' align='center'>" + arrTDList[0] + "</TD>";
	}

	return strDivTDTag;
}

function onClickDocNo()
{
	alert("등록대장 목록화면");
}