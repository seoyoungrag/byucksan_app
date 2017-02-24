
function getSubmitApprover()
{
	var objApprover = null;

	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
		objApprover = getApproverBySerialOrder(objApprovalLine, "0");

	return objApprover;
}

function getCurrentApprover()
{
	var objApprover = null;

	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
	{
		var strCurrentSerial = getCurrentSerial(objApprovalLine);
		objApprover = getApproverBySerialOrder(objApprovalLine, strCurrentSerial);
	}

	return objApprover;
}

function getCompleteApprover()
{
	var objApprover = null;

	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
	{
		var objLast = getLastApprover(objApprovalLine);
		while (objLast != null)
		{
			var strEmptyReason = getApproverEmptyReason(objLast);
			if (strEmptyReason == "")
			{
				var strRole = getApproverRole(objLast);
				if (strRole != "8" && strRole != "9" && strRole != "10" && strRole != "30")
				{
					if (strRole == "6")
					{
						var nSerialOrder = parseInt(getApproverSerialOrder(objLast));
						if (nSerialOrder > 0)
						{
							var objPrev = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder - 1));
							if (objPrev != null)
							{
								var strPrevRole = getApproverRole(objPrev);
								if (strPrevRole == "7")
								{
									objApprover = objPrev;
									break;
								}
							}
						}
					}

					objApprover = objLast;
					break;
				}
			}

			objLast = getPrevApprover(objLast);
		}
	}

	return objApprover;
}

function getCompleteApproverInApprovalLine(objApprovalLine)
{
	var objApprover = null;

	var objLast = getLastApprover(objApprovalLine);
	while (objLast != null)
	{
		var strEmptyReason = getApproverEmptyReason(objLast);
		if (strEmptyReason == "")
		{
			var strRole = getApproverRole(objLast);
			if (strRole != "8" && strRole != "9" && strRole != "10" && strRole != "30")
			{
				if (strRole == "6")
				{
					var nSerialOrder = parseInt(getApproverSerialOrder(objLast));
					if (nSerialOrder > 0)
					{
						var objPrev = getApproverBySerialOrder(objApprovalLine, "" + (nSerialOrder - 1));
						if (objPrev != null)
						{
							var strPrevRole = getApproverRole(objPrev);
							if (strPrevRole == "7")
							{
								objApprover = objPrev;
								break;
							}
						}
					}
				}

				objApprover = objLast;
				break;
			}
		}

		objLast = getPrevApprover(objLast);
	}

	return objApprover;
}

function getSubmiterName()
{
	var strUserName = "";

	var objApprover = getSubmitApprover();
	if (objApprover != null)
		strUserName = getApproverUserName(objApprover);

	return strUserName;
}

function getSubmitDate()
{
	var strSignDate = "";

	var objApprover = getSubmitApprover();
	if (objApprover != null)
		strSignDate = getApproverSignDate(objApprover);

	return strSignDate;
}

function getCurrentName()
{
	var strUserName = "";

	var objApprover = getCurrentApprover();
	if (objApprover != null)
		strUserName = getApproverUserName(objApprover);

	return strUserName;
}

function getCompleteName()
{
	var strUserName = "";

	var strFlowStatus = getFlowStatus();
	if (strFlowStatus != "0" && strFlowStatus != "12")
	{
		var objApprover = getCompleteApprover();
		if (objApprover != null)
			strUserName = getApproverUserName(objApprover);
	}

	return strUserName;
}

function getCompleteDate()
{
	var strSignDate = "";

	var strFlowStatus = getFlowStatus();
	if (strFlowStatus != "0" && strFlowStatus != "12")
	{
		var objApprover = getCompleteApprover();
		if (objApprover != null)
			strSignDate = getApproverSignDate(objApprover);
	}

	return strSignDate;
}

function getApprovalStatus()
{
	var strApprovalStatus = "";

	var strFlowStatus = getFlowStatus();
	if (strFlowStatus == "0")
		strApprovalStatus = STATUS_PROCESS;
	else if (strFlowStatus == "1")
		strApprovalStatus = STATUS_COMPLETE;
	else if (strFlowStatus == "2")
		strApprovalStatus = STATUS_WAIT_ENFORCE;
	else if (strFlowStatus == "3")
		strApprovalStatus = STATUS_WAIT_SEND;
	else if (strFlowStatus == "4")
		strApprovalStatus = STATUS_WAIT_INVESTIGATION;
	else if (strFlowStatus == "5")
		strApprovalStatus = STATUS_INVESTIGATION_RETURN;
	else if (strFlowStatus == "6" || strFlowStatus == "7")
		strApprovalStatus = STATUS_POSTENFORCE;
	else if (strFlowStatus == "8")
		strApprovalStatus = STATUS_WAIT_RECEIVE;
	else if (strFlowStatus == "9")
		strApprovalStatus = STATUS_WAIT_DISTRIBUTE;
	else if (strFlowStatus == "10")
		strApprovalStatus = STATUS_RECEIVE_RETURN;
	else if (strFlowStatus == "11")
		strApprovalStatus = STATUS_DISTRIBUTE_RETURN;
	else if (strFlowStatus == "12")
		strApprovalStatus = STATUS_RECEIVE_COMPLETE;
	else if (strFlowStatus == "13")
		strApprovalStatus = STATUS_DISTRIBUTE_COMPLETE;
	else if (strFlowStatus == "14")
		strApprovalStatus = STATUS_PUBLIC_PROCESS;
	else if (strFlowStatus == "15")
		strApprovalStatus = STATUS_PUBLIC_COMPLETE;
	else if (strFlowStatus == "16")
		strApprovalStatus = STATUS_WAIT_TRANS;
	else if (strFlowStatus == "17")
		strApprovalStatus = STATUS_TRANS;
	else if (strFlowStatus == "18")
		strApprovalStatus = STATUS_WAIT_TRANS2;
	else
		strApprovalStaust = strFlowStatus;

	return strApprovalStatus;
}

function getCurrentRoleString()
{
	var strCurrentRole = "";

	var objApprover = getCurrentApprover();
	if (objApprover != null)
		strCurrentRole = getApproverRoleString(objApprover);

	return strCurrentRole;
}

function getApproverRoleString(objApprover)
{
	var strApproverRole = "";
	if (objApprover == null)
		return strApproverRole;

	strApproverRole = getApproverRole(objApprover);
// by june
	var strRole = strApproverRole + ":";
	var arrApproveRoleList = g_strOption55.split("^");

	for (var i = 0 ; i < arrApproveRoleList.length; i++)
	{
		strApproverRole = arrApproveRoleList[i];
		if (strApproverRole.indexOf(strRole) != -1)
		{
			var nPos = strApproverRole.indexOf(":");
			strApproverRole = strApproverRole.substring(nPos+1, strApproverRole.length);
			break;
		}
	}

	var strEmptyReason = "";
	var strEmptyReason = getApproverEmptyReason(objApprover);
	if (strEmptyReason != "" && strEmptyReason != ROLE_READ)
		strApproverRole = strApproverRole + "(" + strEmptyReason + ")";

/*
	var strActionType = "";
	var strActionType = getApproverAction(objApprover);
	if (strActionType == "8")
		strApproverRole = strApproverRole + "(" + ROLE_DISAGREE + ")";
	else if ((strActionType == "1") || (strActionType == "5"))
		strApproverRole = strApproverRole + "(" + ROLE_SUSPEND + ")";
*/

	return strApproverRole;
}

function getApproverActionString(objApprover)
{
	var strApproverAction = getApproverAction(objApprover);
	if (strApproverAction == "5")
		strApproverAction = ACTION_5;
	else if (strApproverAction == "8")
		strApproverAction = ACTION_8;
	else if (strApproverAction == "9")
		strApproverAction = ACTION_9;
	else
		strApproverAction = "";

	return strApproverAction;
}

function getAccessLevelString()
{
	var strAccessLevel = getAccessLevel();

	if (strAccessLevel == "D0")
		strAccessLevel = STATUS_ACCESS_LEVEL_D0;
	else if (strAccessLevel == "C0")
		strAccessLevel = STATUS_ACCESS_LEVEL_C0;
	else if (strAccessLevel == "B0")
		strAccessLevel = STATUS_ACCESS_LEVEL_B0;
	else if (strAccessLevel == "A0")
		strAccessLevel = STATUS_ACCESS_LEVEL_A0;

	return strAccessLevel;
}

function getUserPosition(objApprover, strDisplayPos)
{
	var strDisplay = "";
	if (objApprover == null)
		return strDisplay;

	var nFind = strDisplayPos.indexOf("^");
	if (nFind != -1)
		strDisplayPos = strDisplayPos.substring(0, nFind);
	if (strDisplayPos.toUpperCase() == "GRD")			// 직급
		strDisplay = getApproverUserLevel(objApprover);
	else if (strDisplayPos.toUpperCase() == "SGRD")			// 직급약어
		strDisplay = getApproverUserLevelAbbr(objApprover);
	else if (strDisplayPos.toUpperCase() == "POS")			// 직위
	{
		strDisplay = getApproverUserPosition(objApprover);
		if (strDisplay == "")
			strDisplay = getApproverUserLevel(objApprover);
	}
	else if (strDisplayPos.toUpperCase() == "SPOS")			// 직위약어
		strDisplay = getApproverUserPositionAbbr(objApprover);
	else if (strDisplayPos.toUpperCase() == "TLT")			// 직책
	{
		strDisplay = getApproverUserDuty(objApprover);
		// 한양대
/*
		if (strDisplay == "")
			strDisplay = getApproverUserLevel(objApprover);
*/
	}

	return strDisplay;
}

function getUserRepPosition(objApprover, strDisplayPos)
{
	var strDisplay = "";
	if (objApprover == null)
		return strDisplay;

	var nFind = strDisplayPos.indexOf("^");
	if (nFind != -1)
		strDisplayPos = strDisplayPos.substring(0, nFind);
	if (strDisplayPos.toUpperCase() == "GRD")			// 직급
		strDisplay = getApproverRepLevel(objApprover);
	else if (strDisplayPos.toUpperCase() == "SGRD")			// 직급약어
		strDisplay = getApproverRepLevelAbbr(objApprover);
	else if (strDisplayPos.toUpperCase() == "POS")			// 직위
	{
		strDisplay = getApproverRepPosition(objApprover);
		if (strDisplay == "")
			strDisplay = getApproverRepLevel(objApprover);
	}
	else if (strDisplayPos.toUpperCase() == "SPOS")			// 직위약어
		strDisplay = getApproverRepPositionAbbr(objApprover);
	else if (strDisplayPos.toUpperCase() == "TLT")			// 직책
	{
		strDisplay = getApproverRepDuty(objApprover);
	}

	return strDisplay;
}

function getEnforceDate()
{
	var strSignDate = "";
	var objSender = getDelivererByDelivererType("1");
	if (objSender != null)
		strSignDate = getDelivererSignDate(objSender);
	return strSignDate;
}

function getSenderDeptName()
{
	var strSenderDeptName = "";

	var objSender = getDelivererByDelivererType("1");
	if (objSender != null)
		strSenderDeptName = getSenderDeptName(objSender);

	return strSenderDeptName;
}

function getReceiveDate()
{
/*
	var strSignDate = "";
	var objReceiver = getDelivererByDelivererType("3");
	if (objReceiver != null)
		strSignDate = getDelivererSignDate(objReceiver);
	return strSignDate;
*/
	var strSignDate = "";

	var objReceiver = getDelivererByDelivererType("2");
	if (objReceiver != null)
	{
		strSignDate = getDelivererSignDate(objReceiver);
	}
	else
	{
		objReceiver = getDelivererByDelivererType("3");
		if (objReceiver != null)
			strSignDate = getDelivererSignDate(objReceiver);
		else
			strSignDate = getRecvDate();
	}

	return strSignDate;
}

function getReceiveDeptName()
{
	var strReceiveDeptName = "";
	var objReceiver = getDelivererByDelivererType("3");
	if (objReceiver != null)
		strReceiveDeptName = getDelivererDeptName(objReceiver);
	return strReceiveDeptName;
}

function getDelivererUserNameByType(strType)
{
	var strDelivererUserName = "";
	var objDeliverer = getDelivererByDelivererType(strType);
	if (objDeliverer != null)
		strDelivererUserName = getDelivererUserName(objDeliverer);
	return strDelivererUserName;
}

function getDelivererDeptNameByType(strType)
{
	var strDelivererDeptName = "";
	var objDeliverer = getDelivererByDelivererType(strType);
	if (objDeliverer != null)
		strDelivererDeptName = getDelivererDeptName(objDeliverer);
	return strDelivererDeptName;
}

function getDelivererSignDateByType(strType)
{
	var strDelivererSignDate = "";
	var objDeliverer = getDelivererByDelivererType(strType);
	if (objDeliverer != null)
		strDelivererSignDate = getDelivererSignDate(objDeliverer);
	return strDelivererSignDate;
}

function getDocCategoryString(nCaseNumber)
{
	var strDocCategory = "";
	strDocCategory = getDocCategory(nCaseNumber);
	if (strDocCategory == "I")
		strDocCategory = DOC_CATEGORY_I;
	else if (strDocCategory == "E")
		strDocCategory = DOC_CATEGORY_E;
	else if (strDocCategory == "W")
		strDocCategory = DOC_CATEGORY_W;
	else if (strDocCategory == "M")
		strDocCategory = DOC_CATEGORY_M;
	else if (strDocCategory == "C")
		strDocCategory = DOC_CATEGORY_C;
	else if (strDocCategory == "O")
		strDocCategory = DOC_CATEGORY_O;

	return strDocCategory;
}

function getEnforceBoundString(nCaseNumber)
{
	var strEnforceBound = "";
	strEnforceBound = getEnforceBound(nCaseNumber);
	if (strEnforceBound == "I")
		strEnforceBound = ENFORCE_BOUND_I;
	else if (strEnforceBound == "O")
		strEnforceBound = ENFORCE_BOUND_O;
	else if (strEnforceBound == "C")
		strEnforceBound = ENFORCE_BOUND_C;
	else if (strEnforceBound == "N")
		strEnforceBound = ENFORCE_BOUND_N;

	return strEnforceBound;
}

function getRecipientReceiptStatusString(objRecipient)
{
	var strReceiptStatus = "";
	strReceiptStatus = getRecipientReceiptStatus(objRecipient);
	if (strReceiptStatus == "0")
		strReceiptStatus = RECEIPT_STATUS_0;
	else if (strReceiptStatus == "1")
		strReceiptStatus = RECEIPT_STATUS_1;
	else if (strReceiptStatus == "2")
		strReceiptStatus = RECEIPT_STATUS_2;
	else if (strReceiptStatus == "3")
		strReceiptStatus = RECEIPT_STATUS_3;
	else if (strReceiptStatus == "4")
		strReceiptStatus = RECEIPT_STATUS_4;
	else if (strReceiptStatus == "5")
		strReceiptStatus = RECEIPT_STATUS_5;
	else if (strReceiptStatus == "6")
		strReceiptStatus = RECEIPT_STATUS_6;
	else if (strReceiptStatus == "7")
		strReceiptStatus = RECEIPT_STATUS_7;
	else
		strReceiptStatus = RECEIPT_STATUS_2;

	return strReceiptStatus;
}

function checkReportApproval()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
	{
		if (getLineType(objApprovalLine) == "4")
		{
			if (getSenderDocID() == "")
			{
				setSenderDocID(getDocID());
				setDocID("");

				setFlowStatus("0");

				objApprovalLine = getFirstApprovalLine();
				while (objApprovalLine != null)
				{
					if (getLineIsActive(objApprovalLine) != "Y")
						setDocStatus(objApprovalLine, "20");

					objApprovalLine = getNextApprovalLine(objApprovalLine);
				}
			}
		}
	}
}

function checkAttachMethod()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return;

	if (getDocStatus(objApprovalLine) != "2")
		return;

	var objAttach = getBodyFileObj();
	if (objAttach != null)
	{
		setAttachMethod(objAttach, "add");
		setAttachLocation(objAttach, "");
	}

	var objAttach = getFirstRelatedAttach();
	while(objAttach != null)
	{
		setAttachMethod(objAttach, "add");
		setAttachLocation(objAttach, "");
		objAttach = getNextRelatedAttach(objAttach);
	}

	var objAttach = getFirstGeneralAttach();
	if (objAttach != null)
		setIsAttached("Y");
	else
		setIsAttached("N");

	while(objAttach != null)
	{
		setAttachMethod(objAttach, "add");
		setAttachLocation(objAttach, "");
		objAttach = getNextGeneralAttach(objAttach);
	}

	var objAttach = getFirstExtendAttach();
	while(objAttach != null)
	{
		setAttachMethod(objAttach, "add");
		setAttachLocation(objAttach, "");
		objAttach = getNextExtendAttach(objAttach);
	}
}

function checkRegistryType()
{
	var strRegistryType = getRegistryType();
	if (strRegistryType == "T")
		strRegistryType = "Y";

	var nBodyCount = getBodyCount();
	var nIndex = 1;

	if (strRegistryType != "I" && strRegistryType != "S")
	{
		while (nIndex <= nBodyCount)
		{
			var strDocCategory = getDocCategory(nIndex);
			if (strDocCategory != "I")
			{
				strRegistryType = "T";
				break;
			}

			nIndex++;
		}
	}

	if (strRegistryType == "N")
	{
		var strClassNumber = getClassNumber();
		if (strClassNumber != "")
			strRegistryType = "Y";
	}

	setRegistryType(strRegistryType);

	if (nBodyCount > 1)
		setIsBatch("Y");
	else
		setIsBatch("N");
}

function checkRelatedSystemInfo()
{
// Demo
//	return 1;

	if (g_strOption148 == "1")
		return 1;

	var strFlowStatus = getFlowStatus();
	if (strFlowStatus == "0" || strFlowStatus == "8" || strFlowStatus == "12" || strFlowStatus == "14")
	{
		var objRelatedSystem = getRelatedSystemBySystemID("SDSDM");
		var strClassNumber = getClassNumber();
		var strSecurityPass = getSecurityPass();
		if (objRelatedSystem != null)
		{
/*
			if (strFlowStatus == "0")
			{
				var strClassNumberID = getClassNumberID();
				if (strClassNumber != "" && strClassNumberID == "")
					return -1;
				if (strClassNumber == "" || strSecurityPass != "")
				{
					objRelatedSystem.parentNode.removeChild(objRelatedSystem);
					return 1;
				}
			}
			else
			{
				if (strSecurityPass != "")
				{
					objRelatedSystem.parentNode.removeChild(objRelatedSystem);
					return 1;
				}
			}
*/
			var objSystemData = getReleatedSystemDataObj(objRelatedSystem);
			if (objSystemData != null)
			{
				var objDocument = objSystemData.selectSingleNode("DOCUMENT");
				if (objDocument != null && objDocument.childNodes.length == 0)
					return 0;
			}
			else
				return 0;
/*
			var strClassNumberID = getClassNumberID();
			if (strClassNumberID != "")
			{
				var objSystemData = getReleatedSystemDataObj(objRelatedSystem);
				setChildNodeData(objSystemData, "CLASS_NUMBER_ID", strClassNumberID);
			}
*/
			var strProcessType = getReleatedSystemProcessType(objRelatedSystem);
			var nProcessType = parseInt(strProcessType,10);
			if (nProcessType != 36)
			{
				nProcessType = 36;
				setRelatedSystemProcessType(objRelatedSystem, nProcessType);
			}
			if ((nProcessType & 4) > 0)
			{
				var bInbound = true;
				var nBodyCount = getBodyCount();
				var nCaseNum = 1;

				for (nCaseNum = 1; nCaseNum <= nBodyCount; nCaseNum++)
				{
					var strDocCategory = getDocCategory(nCaseNum);
					if (strDocCategory != "I")
					{
						bInbound = false;
						break;
					}
				}
				if (bInbound == true)
				{
					nProcessType = (nProcessType | 1);
					setRelatedSystemProcessType(objRelatedSystem, nProcessType);
				}
			}
		}
		else
		{
			if (strFlowStatus == "0")
			{
//				if (strSecurityPass == "" && strClassNumber != "")
					return 0;
			}
/*
			else
			{
				if (strSecurityPass == "")
					return 0;
			}
*/
		}
	}
	return 1;
}

function checkAutoEnforce()
{
	if ((parseInt(g_strOption46) & 8) != 8 || getCurrentLineName() != "0" || (getFormUsage() == "0" && getEnforceFormID() == ""))
		return false;

	var objComplete = getCompleteApprover();
	var objApprover = getActiveApprover();
	if (objComplete == null || objApprover == null)
		return false;

	if (g_strEditType != "11")
	{
		if (getLineName(objComplete.parentNode) != getLineName(objApprover.parentNode) ||
			getApproverSerialOrder(objComplete) != getApproverSerialOrder(objApprover))
			return false;
	}

	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
			break;

		nProposal++;
	}

	if (nProposal > nBodyCount)
		return false;

	var bRet = true;
	if (g_strEditType != "11")
		bRet = confirm(CONFIRM_AUTO_ENFORCE);

	return bRet;
}

function checkAutoEnforceStamp()
{
	if (g_strOption171 != "1")
		return false;

	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
			break;

		nProposal++;
	}

	if (nProposal > nBodyCount)
		return false;

	return true;
}

function checkAutoSuspend()
{
	if (g_strOption180 == "1")
	{
		if (g_strEditType == "0")
		{
			if (getInfoChange() == true)
			{
				onAutoSuspendApprove(0);
				return true;
			}
		}
		else
		{
			var strFlowStatus = getFlowStatus();
			if (strFlowStatus == "0" || strFlowStatus == "14")
			{
				if (getInfoChange() == true)
				{
					onAutoSuspendApprove(1);
					return true;
				}
			}
			else if (parseInt(strFlowStatus) >= 2 && parseInt(strFlowStatus) <= 7)
			{
				if (getInfoChange() == true)
				{
					onAutoSuspendApprove(2);
					return true;
				}
			}
		}
	}

	return false;
}

function getIsPostString()
{
	var strIsPost = "";
	strIsPost = getIsPost();
	if (strIsPost == "N")
		strIsPost = IS_POST_N;
	else if (strIsPost == "Y")
		strIsPost = IS_POST_Y;
	else
		strIsPost = IS_POST_N;

	return strIsPost;
}

function isParentLineCompleted(strLineName)
{
	if (strLineName == "0")		// root line
		return true;
	var objApprover = null;
	var nParentLineNameEnd = strLineName.lastIndexOf('-');
	var strParentLineName = strLineName.substring(0, nParentLineNameEnd);
	var nSerialOrder = parseInt(strLineName.substring(nParentLineNameEnd + 1, strLineName.length));
	var objApprovalLine = getApprovalLineByName(strParentLineName);
	objApprover = getApproverBySerialOrder(objApprovalLine, nSerialOrder);
	var strRole = getApproverRole(objApprover);
	if (strRole == "32" || strRole == "34")
		return isParentLineCompleted(strParentLineName);
	else
		return false;
}

function isPubdocRecipExist()
{
	if (ApprovalDoc.selectSingleNode("//RECIP_GROUP/RECIPIENT[IS_PUBDOC_RECIP='Y']") != null)
		return true;
	return false;
}

function isPubdocRecipExistInGroup(objRecipGroup)
{
	if (objRecipGroup.selectSingleNode("RECIPIENT[IS_PUBDOC_RECIP='Y']") != null)
		return true;
	return false;
}

function checkArchiveInfo(nType)
{
	if (nType == 0) //완결
	{
		var strDraftArchiveID = getDraftArchiveID();
		if (strDraftArchiveID == "")
		{
			alert("기록물철을 지정하십시오.");
			return false;
		}

	}
	else if (nType == 1) //접수
	{
		var strEnforceArchiveID = getEnforceArchiveID();
		if (strEnforceArchiveID == "")
		{
			var bCon = confirm("기록물철을 지정하시겠습니까?");
			return !bCon;
		}

	}
	else if (nType == 2) //담당
	{
		var strEnforceArchiveID = getEnforceArchiveID();
		if (strEnforceArchiveID == "")
		{
			alert("기록물철을 지정하십시오.");
			return false;
		}

	}
	return true;
}

function clearModifiedAttach()
{
	var objExtendAttach = getFirstExtendAttach();
	while (objExtendAttach != null)
	{
		var objNextExtendAttach = getNextExtendAttach(objExtendAttach);

		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "ModifiedDoc" || strClassify == "ModifiedAttach" || strClassify == "ModifiedFlow")
			objExtendAttach.parentNode.removeChild(objExtendAttach);

		objExtendAttach = objNextExtendAttach;
	}
}

function clearApproverSignInfo()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return;

	setCurrentSerial(objApprovalLine, "0");
	setCurrentParallel(objApprovalLine, "0");
	setCurrentID(objApprovalLine, "");
	setCurrentName(objApprovalLine, "");
	setCurrentRole(objApprovalLine, "");

	setDrafterID(objApprovalLine, "");
	setDrafterName(objApprovalLine, "");
	setDrafterPosition(objApprovalLine, "");
	setDrafterPositionAbbr(objApprovalLine, "");
	setDrafterLevel(objApprovalLine, "");
	setDrafterLevelAbbr(objApprovalLine, "");
	setDrafterDuty(objApprovalLine, "");
	setDrafterTitle(objApprovalLine, "");
	setDraftDeptName(objApprovalLine, "");
	setDraftDeptCode(objApprovalLine, "");
	setDraftDate(objApprovalLine, "");

	setChiefID(objApprovalLine, "");
	setChiefName(objApprovalLine, "");
	setChiefRole(objApprovalLine, "");
	setCompDate(objApprovalLine, "");

	var objApprover = getFirstApprover(objApprovalLine);
	while (objApprover != null)
	{
		var strFileName = getApproverSignFileName(objApprover);
		if (strFileName != "")
			removeRelatedAttachInfo(strFileName);

		setApproverIsSigned(objApprover, "N");
		setApproverSignDate(objApprover, "");
		setApproverSignFileName(objApprover, "");
		setApproverIsOpen(objApprover, "N");
		setApproverAction(objApprover, "0");
		setApproverKeepStatus(objApprover, "0");
		setApproverKeepDate(objApprover, "");
		setApproverIsDocModified(objApprover, "");
		setApproverIsLineModified(objApprover, "");
		setApproverIsAttachModified(objApprover, "");
		setApproverOpinion(objApprover, "");

		objApprover = getNextApprover(objApprover);
	}


}

function isOuterRecipExist(nCaseNumber)
{
	var nBodyCount = getBodyCount();
	if (nCaseNumber <= nBodyCount)
	{
		var objRecipGroup = getRecipGroup(nCaseNumber);
		if (objRecipGroup != null)
		{
			var objRecipient = getFirstRecipient(objRecipGroup);
			while (objRecipient != null)
			{
				if (getRecipientDeptCode(objRecipient) == "OUT")		// 외부
					return true;
				objRecipient = getNextRecipient(objRecipient);
			}
		}
	}
	return false;
}

function checkRelatedPass(strValue)
{
	var myDialog = new Object;
	myDialog.value = strValue;
	var strUrl = g_strBaseUrl + "related/approve/CN_RelatedApprovePassword.jsp";
	var result = showModalDialog(strUrl, myDialog, "resizable:no;status:yes;scroll:no;dialogWidth:220px;dialogHeight:210px;help:no");
//alert(myDialog.rtnval);
	if (myDialog.rtnval == true)
		return true;
	else
		return false;
}

function checkPublicLevel()
{
	var strPublicLevel = getPublicLevel();
	if (strPublicLevel == null || strPublicLevel.length != 9)
	{
		setPublicLevel("1YYYYYYYY");
		alert("공개여부 정보를 확인하십시오.");
		return false;
	}
	return true;
}

function getOpenLocale()
{
	return "DOCUMENT";
}

function validateDocumentInfo()
{
	var strDraftProcCode = getDraftProcDeptCode();
	if (strDraftProcCode != g_strApproverDeptCode)
	{
		//doc info
		if (g_strOption148 == "0")
		{
			setOrgSymbol("");	//ORG_SYMBOL
		}
		else
		{
			setAccessLevelCode(g_strApproverDeptCode);	//ACCESS_LEVEL_CODE
			setAccessLevel(g_strApproverDeptName);	//ACCESS_LEVEL
			setDraftArchiveID("");	//DRAFT_ARCHIVE_ID
			setDraftArchiveName("");	//DRAFT_ARCHIVE_NAME
			setDraftConserveCode("");	//DRAFT_CONSERVE_CODE
			setDraftConserve("");	//DRAFT_CONSERVE
		}
		setDraftOrgCode(g_strUserOrgCode);	//DRAFT_ORG_CODE
		setDraftOrgName(g_strUserOrgName);	//DRAFT_ORG_NAME
		setDraftProcDeptCode(g_strApproverDeptCode);	//DRAFT_PROC_DEPT_CODE
		setDraftProcDeptName(g_strApproverDeptName);	//DRAFT_PROC_DEPT_NAME
		var nBodyCount = getBodyCount();
		for (var i = 1 ; i <= nBodyCount ; i++)
		{
			setSenderAsCode(g_strApproverDeptCode, i);	//SENDER_AS_CODE
			setSenderAs(g_strSenderAs, i);	//SENDER_AS
		}
		//approvers
		var objApprovalLine = getFirstApprovalLine();
		if (objApprovalLine != null)
			removeAllChildNode(objApprovalLine.parentNode);

		//deliverers
		var objDeliverers = getDeliverers();
		if (objDeliverers != null)
			removeAllChildNode(objDeliverers);

		//recipients
		var objRecipGroup = getFirstRecipGroup();
		if (objRecipGroup != null)
			removeAllChildNode(objRecipGroup);
//alert(ApprovalDoc.xml);
	}
}
