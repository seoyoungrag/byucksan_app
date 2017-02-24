var g_strInputOpinion = "";
var g_bInfoChange = false;

function clearApprovalDocInfo()
{
	setDocID("");
	setIsBatch("N");
	setIsOpen("N");
	setKeepStatus("0");
	setOrgSymbol("");
	setClassNumber("");
	setClassNumberID("");
	setSerialNumber("");
	setRegistryType("Y");
	setSecurityPass("");
	setSecurityLevel("");
	setUrgency("보통");

	if (g_strOption148 == "1")
		setPublicLevel("1YYYYYYYY");
	else
		setPublicLevel("공개");

//	setIsDirect("Y");
//	setIsAttached("Y");
	setDraftConserve("");
	setDraftConserveCode("");
	setEnforceConserve("");
	setEnforceConserveCode("");
	setEnforceMethod("");
	setFlowStatus("0");
	setIsConverted("N");
	setIsPost("N");
	setEnforceFormID("");
	setSenderDocID("");
	setReceiveNumber("");
	setRecvDate("");

	setTitle("", 1);
//	setDocCategory("I", 1);
//	setEnforceBound("N", 1);

//GOV
	setDraftOrgCode(g_strUserOrgCode);
	setDraftOrgName(g_strUserOrgName);
	setEnforceOrgCode("");
	setEnforceOrgName("");
	setSender("");
	setReceiver("");
	setDraftProcDeptName(g_strApproverDeptName);
	setDraftProcDeptCode(g_strApproverDeptCode);
	setEnforceProcDeptName("");
	setEnforceProcDeptCode("");
	setDraftArchiveName("");
	setDraftArchiveID("");
	setEnforceArchiveName("");
	setEnforceArchiveID("");
//	setAccessLevel(g_strApproverCompName);	// 기상청
//	setAccessLevelCode(g_strApproverCompCode);	// 기상청
	setAccessLevel(g_strApproverDeptName);
	setAccessLevelCode(g_strApproverDeptCode);

	var objApprovalLine = getFirstApprovalLine();
	if (objApprovalLine != null)
		removeAllChildNode(objApprovalLine.parentNode);

	g_strLineName = "0";
	g_strSerialOrder = "0";

	var objDeliverers = getDeliverers();
	if (objDeliverers != null)
		removeAllChildNode(objDeliverers);

	var objRecipGroup = getFirstRecipGroup();
	if (objRecipGroup != null)
		removeAllChildNode(objRecipGroup.parentNode);

	var nBodyCount = getBodyCount();
	for (var n = 1; n <= nBodyCount; n++)
	{
		setSenderAs(g_strSenderAs, n);
		setSenderAsCode(g_strApproverDeptCode, n);
	}
/*
// 해경
	var objServers = getServers();
	if (objServers != null)
		removeAllChildNode(objServers);
*/
	clearFileBodyInfo();
	clearSignAttachInfo();
//	clearRelatedAttachInfo();
//	clearGeneralAttachInfo();
	clearExtendAttachInfo();
}

function clearAttachMethod()
{
	var objAttach = getBodyFileObj();
	if (objAttach != null)
		setAttachMethod(objAttach, "");

	var objAttach = getFirstRelatedAttach();
	while(objAttach != null)
	{
		setAttachMethod(objAttach, "");
		objAttach = getNextRelatedAttach(objAttach);
	}

	var objAttach = getFirstGeneralAttach();
	if (objAttach != null)
		setIsAttached("Y");
	else
		setIsAttached("N");

	while(objAttach != null)
	{
		setAttachMethod(objAttach, "");
		objAttach = getNextGeneralAttach(objAttach);
	}

	var objAttach = getFirstExtendAttach();
	while(objAttach != null)
	{
		setAttachMethod(objAttach, "");
		objAttach = getNextExtendAttach(objAttach);
	}
}

function clearSignAttachInfo()
{
	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var objNextRelatedAttach = getNextRelatedAttach(objRelatedAttach);

		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify != "AttachBody")
			objRelatedAttach.parentNode.removeChild(objRelatedAttach);

		objRelatedAttach = objNextRelatedAttach;
	}
}

function setRelatedAttachToGeneralAttach()
{
	var objRelatedAttach = getFirstRelatedAttach();
	while (objRelatedAttach != null)
	{
		var objNextRelatedAttach = getNextRelatedAttach(objRelatedAttach);

		var strClassify = getAttachClassify(objRelatedAttach);
		var strSubDiv = getAttachSubDiv(objRelatedAttach);

		if (strClassify == "AttachBody" && (strSubDiv == "BodyXML" || strSubDiv == "BodyXSL"))
		{
			var strDisplayName = getAttachDisplayName(objRelatedAttach);
			var strFileName = getAttachFileName(objRelatedAttach);
			var strFileSize = getAttachFileSize(objRelatedAttach);
			var strLocation = getAttachLocation(objRelatedAttach);

			var objGeneralAttach = addGeneralAttachInfo("1", strDisplayName, strFileName, strFileSize, strLocation)
			objGeneralAttach.setAttribute("METHOD", "");

			objRelatedAttach.parentNode.removeChild(objRelatedAttach);
		}

		objRelatedAttach = objNextRelatedAttach;
	}
}

// XML Control Method

function removeAllChildNode(objNode)
{
	var nodeChild = objNode.firstChild;

	while (nodeChild != null)
	{
		var nodeNext = nodeChild.nextSibling;

		if (nodeChild.childNodes.length > 0)
			removeAllChildNode(nodeChild);

		objNode.removeChild(nodeChild);
		nodeChild = nodeNext;
	}

}

function copyXMLNode(nodeSrc, nodeDest)
{
	var nodeChild = nodeSrc.firstChild;
	while (nodeChild != null)
	{
		var nodeNew = null;

		if (nodeChild.nodeTypeString == "element")
		{
			nodeNew = nodeDest.ownerDocument.createNode(1, nodeChild.nodeName, "");

			var nLength = nodeChild.attributes.length;
			var i = 0;
			while (i < nLength)
			{
				var objAttribute = nodeChild.attributes(i);
				nodeNew.setAttribute(objAttribute.nodeName, objAttribute.text);
				i++;
			}
		}
		else if (nodeChild.nodeTypeString == "text" || nodeChild.nodeTypeString == "cdatasection")
		{
			nodeNew = nodeDest.ownerDocument.createTextNode(nodeChild.nodeValue);
		}

		if (nodeNew != null)
		{
			nodeDest.appendChild(nodeNew);

			if (nodeChild.childNodes.length > 0)
				copyXMLNode(nodeChild, nodeNew);
		}

		nodeChild = nodeChild.nextSibling;
	}
}

function createDataNode(strParentNode, strAddNode)
{
	var objParentNode = ApprovalDoc.selectSingleNode("//" + strParentNode);
	var objNewNode = objParentNode.ownerDocument.createNode(1, strAddNode, "");
	objParentNode.appendChild(objNewNode);

	return objNewNode;
}

function addDataNode(objParentNode, strAddNode)
{
	var objNewNode = objParentNode.ownerDocument.createNode(1, strAddNode, "");
	objParentNode.appendChild(objNewNode);

	return objNewNode;
}

function getChildNodeData(objParentNode, strChildElement)
{
	var strValue = "";

	if (objParentNode != null)
	{
		var objChildNode = objParentNode.selectSingleNode(strChildElement);
		if (objChildNode != null)
			strValue = objChildNode.text;
	}

	return strValue;
}

function setChildNodeData(objParentNode, strChildElement, strValue)
{
	var objChildNode = objParentNode.selectSingleNode(strChildElement);
	if (objChildNode == null)
		objChildNode = addDataNode(objParentNode, strChildElement);

	objChildNode.text = strValue;
}

function getDataAttribute(objNode, strAttribute)
{
	return objNode.getAttribute(strAttribute);
}

function setDataAttribute(objNode, strAttribute, strValue)
{
	objNode.setAttribute(strAttribute, strValue);
}

function getAttachAttribute(objAttach, strAttribute)
{
	return objAttach.getAttribute(strAttribute);
}

function setAttachAttribute(objAttach, strAttribute, strValue)
{
	objAttach.setAttribute(strAttribute, strValue);
}

// DOC_ID
function setDocID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DOC_ID").text = strValue;
}

function getDocID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DOC_ID").text;
	return strValue;
}

// IS_FORM
function setIsForm(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_FORM").text = strValue;
}

function getIsForm()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_FORM").text;
	return strValue;
}

// IS_BATCH
function setIsBatch(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_BATCH").text = strValue;
}

function getIsBatch()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_BATCH").text;
	return strValue;
}

// IS_OPEN
function setIsOpen(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_OPEN").text = strValue;
}

function getIsOpen()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_OPEN").text;
	return strValue;
}

// KEEP_STATUS
function setKeepStatus(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/KEEP_STATUS").text = strValue;
}

function getKeepStatus()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/KEEP_STATUS").text;
	return strValue;
}

// BODY_TYPE
function setBodyType(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/BODY_TYPE").text = strValue;
}

function getBodyType()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/BODY_TYPE").text;
	return strValue;
}

// ORG_SYMBOL
function setOrgSymbol(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ORG_SYMBOL").text = strValue;
}

function getOrgSymbol()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ORG_SYMBOL").text;
	return strValue;
}

// CLASS_NUMBER
function setClassNumber(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/CLASS_NUMBER").text = strValue;
}

function getClassNumber()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/CLASS_NUMBER").text;
	return strValue;
}

// CLASS_NUMBER_ID
function setClassNumberID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/CLASS_NUMBER_ID").text = strValue;
}

function getClassNumberID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/CLASS_NUMBER_ID").text;
	return strValue;
}

// SERIAL_SEED
function setSerialSeed(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SERIAL_SEED").text = strValue;
}

function getSerialSeed()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SERIAL_SEED").text;
	return strValue;
}

// SERIAL_NUMBER
function setSerialNumber(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SERIAL_NUMBER").text = strValue;
}

function getSerialNumber()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SERIAL_NUMBER").text;
	return strValue;
}

// REGISTRY_TYPE
function setRegistryType(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/REGISTRY_TYPE").text = strValue;
}

function getRegistryType()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/REGISTRY_TYPE").text;
	return strValue;
}

// SECURITY_PASS
function setSecurityPass(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SECURITY_PASS").text = strValue;
}

function getSecurityPass()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SECURITY_PASS").text;
	return strValue;
}

// SECURITY_LEVEL
function setSecurityLevel(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SECURITY_LEVEL").text = strValue;
}

function getSecurityLevel()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SECURITY_LEVEL").text;
	return strValue;
}

// ACCESS_LEVEL_CODE
function setAccessLevelCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ACCESS_LEVEL_CODE").text = strValue;
}

function getAccessLevelCode()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ACCESS_LEVEL_CODE").text;
	return strValue;
}

// ACCESS_LEVEL
function setAccessLevel(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ACCESS_LEVEL").text = strValue;
}

function getAccessLevel()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ACCESS_LEVEL").text;
	return strValue;
}

// URGENCY
function setUrgency(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/URGENCY").text = strValue;
}

function getUrgency()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/URGENCY").text;
	return strValue;
}

// PUBLIC_LEVEL
function setPublicLevel(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/PUBLIC_LEVEL").text = strValue;
}

function getPublicLevel()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/PUBLIC_LEVEL").text;
	return strValue;
}

// RESTRICTED_PAGE
function setRestricedPage(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/RESTRICTED_PAGE").text = strValue;
}

function getRestricedPage()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/RESTRICTED_PAGE").text;
	return strValue;
}

// IS_DIRECT
function setIsDirect(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_DIRECT").text = strValue;
}

function getIsDirect()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_DIRECT").text;
	return strValue;
}

// HAS_ATTACH
function setIsAttached(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_ATTACHED").text = strValue;
}

function getIsAttached()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_ATTACHED").text;
	return strValue;
}

// DRAFT_CONSERVE_CODE
function setDraftConserveCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_CONSERVE_CODE").text = strValue;
}

function getDraftConserveCode()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_CONSERVE_CODE").text;
	return strValue;
}

// DRAFT_CONSERVE
function setDraftConserve(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_CONSERVE").text = strValue;
}

function getDraftConserve()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_CONSERVE").text;
	return strValue;
}

// DRAFT_CONSERVE_CODE
function setEnforceConserveCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_CONSERVE_CODE").text = strValue;
}

function getEnforceConserveCode()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_CONSERVE_CODE").text;
	return strValue;
}

// ENFORCE_CONSERVE
function setEnforceConserve(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_CONSERVE").text = strValue;
}

function getEnforceConserve()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_CONSERVE").text;
	return strValue;
}

// ENFORCE_METHOD
function setEnforceMethod(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_METHOD").text = strValue;
}

function getEnforceMethod()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_METHOD").text;
	return strValue;
}

// FLOW_STATUS
function setFlowStatus(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/FLOW_STATUS").text = strValue;
}

function getFlowStatus()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/FLOW_STATUS").text;
	return strValue;
}

// IS_CONVERTED
function setIsConverted(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_CONVERTED").text = strValue;
}

function getIsConverted()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_CONVERTED").text;
	return strValue;
}

// IS_POST
function setIsPost(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_POST").text = strValue;
}

function getIsPost()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_POST").text;
	return strValue;
}

// ENFORCE_FORM_ID
function setEnforceFormID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_FORM_ID").text = strValue;
}

function getEnforceFormID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_FORM_ID").text;
	return strValue;
}

// SENDER_DOC_ID
function setSenderDocID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SENDER_DOC_ID").text = strValue;
}

function getSenderDocID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SENDER_DOC_ID").text;
	return strValue;
}

// DIST_DOC_ID
function setDistDocID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DIST_DOC_ID").text = strValue;
}

function getDistDocID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DIST_DOC_ID").text;
	return strValue;
}

// DISTRIBUTE_SEED
function setDistributeSeed(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DISTRIBUTE_SEED").text = strValue;
}

function getDistributeSeed()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DISTRIBUTE_SEED").text;
	return strValue;
}

// DISTRIBUTE_NUMBER
function setDistributeNumber(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DISTRIBUTE_NUMBER").text = strValue;
}

function getDistributeNumber()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DISTRIBUTE_NUMBER").text;
	return strValue;
}

// RECEIVE_SEED
function setReceiveSeed(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVE_SEED").text = strValue;
}

function getReceiveSeed()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVE_SEED").text;
	return strValue;
}

// RECEIVE_NUMBER
function setReceiveNumber(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVE_NUMBER").text = strValue;
}

function getReceiveNumber()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVE_NUMBER").text;
	return strValue;
}

// ANNOUNCEMENT_STATUS
function setAnnouncementStatus(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ANNOUNCEMENT_STATUS").text = strValue;
}

function getAnnouncementStatus()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ANNOUNCEMENT_STATUS").text;
	return strValue;
}

// SCHEMA_VERSION
function setSchemaVersion(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SCHEMA_VERSION").text = strValue;
}

function getSchemaVersion()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SCHEMA_VERSION").text;
	return strValue;
}

// DRAFT_ORG_CODE
function setDraftOrgCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ORG_CODE").text = strValue;
}

function getDraftOrgCode()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ORG_CODE").text;
	return strValue;
}

// DRAFT_ORG_NAME
function setDraftOrgName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ORG_NAME").text = strValue;
}

function getDraftOrgName()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ORG_NAME").text;
	return strValue;
}

// ENFORCE_ORG_CODE
function setEnforceOrgCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ORG_CODE").text = strValue;
}

function getEnforceOrgCode()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ORG_CODE").text;
	return strValue;
}

// ENFORCE_ORG_NAME
function setEnforceOrgName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ORG_NAME").text = strValue;
}

function getEnforceOrgName()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ORG_NAME").text;
	return strValue;
}

// SENDER
function setSender(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SENDER").text = strValue;
}

function getSender()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SENDER").text;
	return strValue;
}

// RECEIVER
function setReceiver(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVER").text = strValue;
}

function getReceiver()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/RECEIVER").text;
	return strValue;
}

// SEND_DATE
function setSendDate(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/SEND_DATE").text = strValue;
}

function getSendDate()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/SEND_DATE").text;
	return strValue;
}

// RECV_DATE
function setRecvDate(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/RECV_DATE").text = strValue;
}

function getRecvDate()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/RECV_DATE").text;
	return strValue;
}

// ACCEPTOR_ID
function setAcceptorID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ACCEPTOR_ID").text = strValue;
}

function getAcceptorID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ACCEPTOR_ID").text;
	return strValue;
}

// ACCEPTOR_NAME
function setAcceptorName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ACCEPTOR_NAME").text = strValue;
}

function getAcceptorName()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ACCEPTOR_NAME").text;
	return strValue;
}

// CHARGER_ID
function setChargerID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/CHARGER_ID").text = strValue;
}

function getChargerID()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/CHARGER_ID").text;
	return strValue;
}

// CHARGER_NAME
function setChargerName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/CHARGER_NAME").text = strValue;
}

function getChargerName()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/CHARGER_NAME").text;
	return strValue;
}

// IS_PUBDOC
function setIsPubDoc(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_PUBDOC").text = strValue;
}

function getIsPubDoc()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_PUBDOC").text;
	return strValue;
}

// IS_ADMIN_MIS
function setIsAdminMis(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_ADMIN_MIS").text = strValue;
}

function getIsAdminMis()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_ADMIN_MIS").text;
	return strValue;
}

// DRAFT_PROC_DEPT_CODE
function setDraftProcDeptCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_CODE").text = strValue;
}

function getDraftProcDeptCode()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_CODE").text;
	var strValue = "";
	var objDraftProcDeptCode = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_CODE");
	if (objDraftProcDeptCode != null)
		strValue = objDraftProcDeptCode.text;
	return strValue;
}

// DRAFT_PROC_DEPT_NAME
function setDraftProcDeptName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_NAME").text = strValue;
}

function getDraftProcDeptName()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_NAME").text;
	var strValue = "";
	var objDraftProcDeptName = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_PROC_DEPT_NAME");
	if (objDraftProcDeptName != null)
		strValue = objDraftProcDeptName.text;
	return strValue;
}

// ENFORCE_PROC_DEPT_CODE
function setEnforceProcDeptCode(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_CODE").text = strValue;
}

function getEnforceProcDeptCode()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_CODE").text;
	var strValue = "";
	var objEnforceProcDeptCode = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_CODE");
	if (objEnforceProcDeptCode != null)
		strValue = objEnforceProcDeptCode.text;
	return strValue;
}

// ENFORCE_PROC_DEPT_NAME
function setEnforceProcDeptName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_NAME").text = strValue;
}

function getEnforceProcDeptName()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_NAME").text;
	var strValue = "";
	var objEnforceProcDeptName = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_PROC_DEPT_NAME");
	if (objEnforceProcDeptName != null)
		strValue = objEnforceProcDeptName.text;
	return strValue;
}

// DRAFT_ARCHIVE_ID
function setDraftArchiveID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_ID").text = strValue;
}

function getDraftArchiveID()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_ID").text;
	var strValue = "";
	var objDraftArchiveID = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_ID");
	if (objDraftArchiveID != null)
		strValue = objDraftArchiveID.text;
	return strValue;
}

// DRAFT_ARCHIVE_NAME
function setDraftArchiveName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_NAME").text = strValue;
}

function getDraftArchiveName()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_NAME").text;
	var strValue = "";
	var objDraftArchiveName = ApprovalDoc.selectSingleNode("//DOC_INFO/DRAFT_ARCHIVE_NAME");
	if (objDraftArchiveName != null)
		strValue = objDraftArchiveName.text;
	return strValue;
}

// ENFORCE_ARCHIVE_ID
function setEnforceArchiveID(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_ID").text = strValue;
}

function getEnforceArchiveID()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_ID").text;
	var strValue = "";
	var objEnforceArchiveID = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_ID");
	if (objEnforceArchiveID != null)
		strValue = objEnforceArchiveID.text;
	return strValue;
}

// ENFORCE_ARCHIVE_NAME
function setEnforceArchiveName(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_NAME").text = strValue;
}

function getEnforceArchiveName()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_NAME").text;
	var strValue = "";
	var objEnforceArchiveName = ApprovalDoc.selectSingleNode("//DOC_INFO/ENFORCE_ARCHIVE_NAME");
	if (objEnforceArchiveName != null)
		strValue = objEnforceArchiveName.text;
	return strValue;
}

// FORM_USAGE
function setFormUsage(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/FORM_USAGE").text = strValue;
}

function getFormUsage()
{
//	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/FORM_USAGE").text;
	var strValue = "";
	var objFormUsage = ApprovalDoc.selectSingleNode("//DOC_INFO/FORM_USAGE");
	if (objFormUsage != null)
		strValue = objFormUsage.text;
	return strValue;
}

// IS_MODIFIABLE
function setIsModifiable(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/IS_MODIFIABLE").text = strValue;
}

function getIsModifiable()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/IS_MODIFIABLE").text;
	return strValue;
}

// MODIFY_DATE
function setModifyDate(strValue)
{
	ApprovalDoc.selectSingleNode("//DOC_INFO/MODIFY_DATE").text = strValue;
}

function getModifyDate()
{
	var strValue = ApprovalDoc.selectSingleNode("//DOC_INFO/MODIFY_DATE").text;
	return strValue;
}

// DRAFT_INFOS method

function getDraftInfo(nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	return objNode;
}

function addDraftInfos(nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS ", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
		setChildNodeData(objNode, "TITLE", "");
		setChildNodeData(objNode, "DOC_CATEGORY", "");
		setChildNodeData(objNode, "ENFORCE_BOUND", "");
		setChildNodeData(objNode, "SENDER_AS", "");
		setChildNodeData(objNode, "SENDER_AS_CODE", "");
		setChildNodeData(objNode, "TREATMENT", "");

		if (nCaseNumber > 1)
		{
			var strPrevData = getDocCategory(nCaseNumber - 1);
			setDocCategory(strPrevData, nCaseNumber);
			strPrevData = getEnforceBound(nCaseNumber - 1);
			setEnforceBound(strPrevData, nCaseNumber);
			strPrevData = getSenderAs(nCaseNumber - 1);
			setSenderAs(strPrevData, nCaseNumber);
			strPrevData = getSenderAsCode(nCaseNumber - 1);
			setSenderAsCode(strPrevData, nCaseNumber);
			strPrevData = getTreatment(nCaseNumber - 1);
			setTreatment(strPrevData, nCaseNumber);
		}
	}

	return objNode;
}

function removeDraftInfos(nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode != null)
		objNode.parentNode.removeChild(objNode);
}

// TITLE
function setTitle(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("TITLE").text = strValue;
}

function getTitle(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode != null)
		strValue = objNode.selectSingleNode("TITLE").text;

	return strValue;
}

// DOC_CATEGORY
function setDocCategory(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("DOC_CATEGORY").text = strValue;
}

function getDocCategory(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");

	if (objNode != null)
		strValue = objNode.selectSingleNode("DOC_CATEGORY").text;

	return strValue;
}

// ENFORCE_BOUND
function setEnforceBound(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("ENFORCE_BOUND").text = strValue;
}

function getEnforceBound(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");

	if (objNode != null)
		strValue = objNode.selectSingleNode("ENFORCE_BOUND").text;

	return strValue;
}

// SENDER_AS
function setSenderAs(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("SENDER_AS").text = strValue;
}

function getSenderAs(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");

	if (objNode != null)
		strValue = objNode.selectSingleNode("SENDER_AS").text;

	return strValue;
}

// SENDER_AS_CODE
function setSenderAsCode(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("SENDER_AS_CODE").text = strValue;
}

function getSenderAsCode(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");

	if (objNode != null)
		strValue = objNode.selectSingleNode("SENDER_AS_CODE").text;

	return strValue;
}

// TREATMENT
function setTreatment(strValue, nCaseNumber)
{
	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");
	if (objNode == null)
	{
		objNode = createDataNode("DRAFT_INFOS", "DRAFT_INFO");
		setChildNodeData(objNode, "CASE_NUMBER", "" + nCaseNumber);
	}

	objNode.selectSingleNode("TREATMENT").text = strValue;
}

function getTreatment(nCaseNumber)
{
	var strValue = "";

	var objNode = ApprovalDoc.selectSingleNode("//DRAFT_INFOS/DRAFT_INFO[CASE_NUMBER='" + nCaseNumber + "']");

	if (objNode != null)
	{
		var objTreatment = objNode.selectSingleNode("TREATMENT");
		if (objTreatment != null)
			strValue = objTreatment.text;
	}

	return strValue;
}

// APPROVAL_LINE Method

function getActiveApprovalLine()
{
	return ApprovalDoc.selectSingleNode("//APPROVAL_LINES/APPROVAL_LINE[@IS_ACTIVE='Y']");
}

function getApprovalLineByLineName(strLineName)
{
	return ApprovalDoc.selectSingleNode("//APPROVAL_LINES/APPROVAL_LINE[LINE_NAME='" + strLineName + "']");
}

function removeApprovalLine(objApprovalLine)
{
	objApprovalLine.parentNode.removeChild(objApprovalLine);
}

function removeAllApprover(objApprovalLine)
{
	var objApprovers = objApprovalLine.selectNodes("APPROVER");

	var nIndex = 0;
	var nLength = objApprovers.length;
	while (nIndex < nLength)
	{
		var objApprover = objApprovers.item(nIndex);
		removeApprover(objApprover);

		nIndex++;
	}
}

function addApprovalLine(strIsActive, strLineName, strDocStatus, strLineType, strCurrentSerial, strCurrentParallel)
{
	var objApprovalLines = ApprovalDoc.selectSingleNode("//APPROVAL_LINES");
	if (objApprovalLines == null)
		objApprovalLines = createDataNode("APPROVAL_DOC ", "APPROVAL_LINES");

	var objApprovalLine = addDataNode(objApprovalLines, "APPROVAL_LINE");
	objApprovalLine.setAttribute("IS_ACTIVE", strIsActive);

	setLineName(objApprovalLine, strLineName);
	setDocStatus(objApprovalLine, strDocStatus);
	setLineType(objApprovalLine, strLineType);
	setCurrentSerial(objApprovalLine, strCurrentSerial);
	setCurrentParallel(objApprovalLine, strCurrentParallel);

	return objApprovalLine;
}

function getFirstApprovalLine()
{
	var objApprovalLine = null;

	var objApprovalLines = ApprovalDoc.selectSingleNode("//APPROVAL_LINES");
	if (objApprovalLines != null)
		objApprovalLine = objApprovalLines.firstChild;

	return objApprovalLine;
}

function getNextApprovalLine(objApprovalLine)
{
	return objApprovalLine.nextSibling;
}

function getLastApprovalLine()
{
	var objApprovalLine = null;

	var objApprovalLines = ApprovalDoc.selectSingleNode("//APPROVAL_LINES");
	if (objApprovalLines != null)
		objApprovalLine = objApprovalLines.lastChild;

	return objApprovalLine;
}

function getPrevApprovalLine(objApprovalLine)
{
	return objApprovalLine.previousSibling;
}

function getCurrentLineName()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
		return getLineName(objApprovalLine);

	return g_strLineName;
}

function getCurrentSerialOrder()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
		return getCurrentSerial(objApprovalLine);

	return g_strSerialOrder;
}

function getCurrentLineType()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine != null)
		return getLineType(objApprovalLine);

	var strLineType = "0";

	var strLineName = getCurrentLineName();
	var nFind = strLineName.lastIndexOf("-");
	if (nFind == -1)
		return strLineType;

	var strParentLineName = strLineName.substring(0, nFind);
	var strParentSerial = strLineName.substring(nFind + 1, strLineName.length);

	var objParentLine = getApprovalLineByLineName(strParentLineName);
	if (objParentLine == null)
		return strLineType;

	var objParentApprover = getApproverBySerialOrder(objParentLine, strParentSerial);
	if (objParentApprover == null)
		return strLineType;

	var strApproverType = getApproverType(objParentApprover);
	if (strApproverType == "1" || strApproverType == "2")
		strLineType = "3";
	else if (strApproverType == "5")
		strLineType = "1";
	else if (strApproverType == "6")
		strLineType = "2";
	else if (strApproverType == "7")
		strLineType = "4";
	else if (strApproverType == "8")
		strLineType = "5";

	return strLineType;
}

function getLineIsActive(objApprovalLine)
{
	return objApprovalLine.getAttribute("IS_ACTIVE");
}

function setLineIsActive(objApprovalLine, strValue)
{
	objApprovalLine.setAttribute("IS_ACTIVE", strValue);
}

function getLineName(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "LINE_NAME");
}

function setLineName(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "LINE_NAME", strValue);
}

function getDocStatus(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DOC_STATUS");
}

function setDocStatus(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DOC_STATUS", strValue);
}

function getLineType(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "LINE_TYPE");
}

function setLineType(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "LINE_TYPE", strValue);
}

function getCurrentSerial(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CURRENT_SERIAL");
}

function setCurrentSerial(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CURRENT_SERIAL", strValue);
}

function getCurrentParallel(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CURRENT_PARALLEL");
}

function setCurrentParallel(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CURRENT_PARALLEL", strValue);
}

function getCurrentID(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CURRENT_ID");
}

function setCurrentID(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CURRENT_ID", strValue);
}

function getCurrentName(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CURRENT_NAME");
}

function setCurrentName(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CURRENT_NAME", strValue);
}

function getCurrentRole(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CURRENT_ROLE");
}

function setCurrentRole(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CURRENT_ROLE", strValue);
}

function getDrafterID(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_ID");
}

function setDrafterID(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_ID", strValue);
}

function getDrafterName(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_NAME");
}

function setDrafterName(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_NAME", strValue);
}

function getDrafterPosition(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_POSITION");
}

function setDrafterPosition(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_POSITION", strValue);
}

function getDrafterPositionAbbr(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_POSITION_ABBR");
}

function setDrafterPositionAbbr(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_POSITION_ABBR", strValue);
}

function getDrafterLevel(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_LEVEL");
}

function setDrafterLevel(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_LEVEL", strValue);
}

function getDrafterLevelAbbr(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_LEVEL_ABBR");
}

function setDrafterLevelAbbr(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_LEVEL_ABBR", strValue);
}

function getDrafterDuty(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_DUTY");
}

function setDrafterDuty(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_DUTY", strValue);
}

function getDrafterTitle(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFTER_TITLE");
}

function setDrafterTitle(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFTER_TITLE", strValue);
}

function getDraftDeptName(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFT_DEPT_NAME");
}

function setDraftDeptName(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFT_DEPT_NAME", strValue);
}

function getDraftDeptCode(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFT_DEPT_CODE");
}

function setDraftDeptCode(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFT_DEPT_CODE", strValue);
}

function getDraftDate(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "DRAFT_DATE");
}

function setDraftDate(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "DRAFT_DATE", strValue);
}

function getChiefID(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CHIEF_ID");
}

function setChiefID(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CHIEF_ID", strValue);
}

function getChiefName(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CHIEF_NAME");
}

function setChiefName(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CHIEF_NAME", strValue);
}

function getChiefRole(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "CHIEF_ROLE");
}

function setChiefRole(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "CHIEF_ROLE", strValue);
}

function getCompDate(objApprovalLine)
{
	return getChildNodeData(objApprovalLine, "COMP_DATE");
}

function setCompDate(objApprovalLine, strValue)
{
	setChildNodeData(objApprovalLine, "COMP_DATE", strValue);
}

function getFirstApprover(objApprovalLine)
{
	var objApprover = getApproverBySerialOrder(objApprovalLine, "0");
	return objApprover;
}

function getLastApprover(objApprovalLine)
{
	var strSerialOrder = "" + (getApproverCount(objApprovalLine) - 1);

	var objApprover = getApproverBySerialOrder(objApprovalLine, strSerialOrder);
	return objApprover;
}

function getNextApprover(objApprover)
{
	var strSerialOrder = getApproverSerialOrder(objApprover);
	strSerialOrder = "" + (parseInt(strSerialOrder) + 1);

	var objApprovalLine = objApprover.parentNode;
	var objApprover = getApproverBySerialOrder(objApprovalLine, strSerialOrder);
	return objApprover;
}

function getPrevApprover(objApprover)
{
	var strSerialOrder = getApproverSerialOrder(objApprover);
	if (strSerialOrder == "0")
		return null;

	strSerialOrder = "" + (parseInt(strSerialOrder) - 1);

	var objApprovalLine = objApprover.parentNode;
	var objApprover = getApproverBySerialOrder(objApprovalLine, strSerialOrder);
	return objApprover;
}

function getApproverCount(objApprovalLine)
{
	return objApprovalLine.selectNodes("APPROVER").length;
}

function getApproverByUserID(objApprovalLine, strUserID)
{
	return objApprovalLine.selectSingleNode("APPROVER[USER_ID='" + strUserID + "']");
}

function getApproverBySerialOrder(objApprovalLine, strSerialOrder)
{
	return objApprovalLine.selectSingleNode("APPROVER[SERIAL_ORDER='" + strSerialOrder + "']");
}

function getApproverByParallelOrder(objApprovalLine, strParallelOrder)
{
	return objApprovalLine.selectSingleNode("APPROVER[PARALLEL_ORDER='" + strParallelOrder + "']");
}

function getApproverByOrder(objApprovalLine, strSerialOrder, strParallelOrder)
{
	return objApprovalLine.selectSingleNode("APPROVER[SERIAL_ORDER='" + strSerialOrder + "' && PARALLEL_ORDER='" + strParallelOrder + "']");
}

function getApproverByRole(objApprovalLine, strApproverRole)
{
	return objApprovalLine.selectSingleNode("APPROVER[APPROVER_ROLE='" + strApproverRole + "']");
}

function getApproverByAction(objApprovalLine, strApproverAction)
{
	return objApprovalLine.selectSingleNode("APPROVER[APPROVER_ACTION='" + strApproverAction + "']");
}

function getApproverByType(objApprovalLine, strApproverType)
{
	return objApprovalLine.selectSingleNode("APPROVER[APPROVER_TYPE='" + strApproverType + "']");
}

function getApproverByRepID(objApprovalLine, strRepID)
{
	return objApprovalLine.selectSingleNode("APPROVER[REP_ID='" + strRepID + "']");
}

// APPROVER Method

function getActiveApprover()
{
	var objApprover = null;

	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return objApprover;

	objApprover = objApprovalLine.selectSingleNode("APPROVER[@IS_ACTIVE='Y']");
	if (objApprover == null)
	{
		var strCurrentSerial = getCurrentSerial(objApprovalLine);
		objApprover = getApproverBySerialOrder(objApprovalLine, strCurrentSerial);
	}

	return objApprover;
}

function addApprover(objApprovalLine)
{
	var objApprover = addDataNode(objApprovalLine, "APPROVER");
	return objApprover;
}

function removeApprover(objApprover)
{
	objApprover.parentNode.removeChild(objApprover);
}

function getApproverIsActive(objApprover)
{
	return objApprover.getAttribute("IS_ACTIVE");
}

function setApproverIsActive(objApprover, strValue)
{
	objApprover.setAttribute("IS_ACTIVE", strValue);
}

function getApproverSerialOrder(objApprover)
{
	return getChildNodeData(objApprover, "SERIAL_ORDER");
}

function setApproverSerialOrder(objApprover, strValue)
{
	setChildNodeData(objApprover, "SERIAL_ORDER", strValue);
}

function getApproverParallelOrder(objApprover)
{
	return getChildNodeData(objApprover, "PARALLEL_ORDER");
}

function setApproverParallelOrder(objApprover, strValue)
{
	setChildNodeData(objApprover, "PARALLEL_ORDER", strValue);
}

function getApproverUserID(objApprover)
{
	return getChildNodeData(objApprover, "USER_ID");
}

function setApproverUserID(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_ID", strValue);
}

function getApproverUserName(objApprover)
{
	return getChildNodeData(objApprover, "USER_NAME");
}

function setApproverUserName(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_NAME", strValue);
}

function getApproverUserPosition(objApprover)
{
	return getChildNodeData(objApprover, "USER_POSITION");
}

function setApproverUserPosition(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_POSITION", strValue);
}

function getApproverUserPositionAbbr(objApprover)
{
	return getChildNodeData(objApprover, "USER_POSITION_ABBR");
}

function setApproverUserPositionAbbr(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_POSITION_ABBR", strValue);
}

function getApproverUserLevel(objApprover)
{
	return getChildNodeData(objApprover, "USER_LEVEL");
}

function setApproverUserLevel(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_LEVEL", strValue);
}

function getApproverUserLevelAbbr(objApprover)
{
	return getChildNodeData(objApprover, "USER_LEVEL_ABBR");
}

function setApproverUserLevelAbbr(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_LEVEL_ABBR", strValue);
}

function getApproverUserDuty(objApprover)
{
	return getChildNodeData(objApprover, "USER_DUTY");
}

function setApproverUserDuty(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_DUTY", strValue);
}

function getApproverUserTitle(objApprover)
{
	return getChildNodeData(objApprover, "USER_TITLE");
}

function setApproverUserTitle(objApprover, strValue)
{
	setChildNodeData(objApprover, "USER_TITLE", strValue);
}

function getApproverCompany(objApprover)
{
	return getChildNodeData(objApprover, "COMPANY");
}

function setApproverCompany(objApprover, strValue)
{
	setChildNodeData(objApprover, "COMPANY", strValue);
}

function getApproverDeptName(objApprover)
{
	return getChildNodeData(objApprover, "DEPT_NAME");
}

function setApproverDeptName(objApprover, strValue)
{
	setChildNodeData(objApprover, "DEPT_NAME", strValue);
}

function getApproverDeptCode(objApprover)
{
	return getChildNodeData(objApprover, "DEPT_CODE");
}

function setApproverDeptCode(objApprover, strValue)
{
	setChildNodeData(objApprover, "DEPT_CODE", strValue);
}

function getApproverIsSigned(objApprover)
{
	return getChildNodeData(objApprover, "IS_SIGNED");
}

function setApproverIsSigned(objApprover, strValue)
{
	setChildNodeData(objApprover, "IS_SIGNED", strValue);
}

function getApproverSignDate(objApprover)
{
	return getChildNodeData(objApprover, "SIGN_DATE");
}

function setApproverSignDate(objApprover, strValue)
{
	setChildNodeData(objApprover, "SIGN_DATE", strValue);
}

function getApproverSignFileName(objApprover)
{
	return getChildNodeData(objApprover, "SIGN_FILE_NAME");
}

function setApproverSignFileName(objApprover, strValue)
{
	setChildNodeData(objApprover, "SIGN_FILE_NAME", strValue);
}

function getApproverRole(objApprover)
{
	return getChildNodeData(objApprover, "APPROVER_ROLE");
}

function setApproverRole(objApprover, strValue)
{
	setChildNodeData(objApprover, "APPROVER_ROLE", strValue);
}

function getApproverIsOpen(objApprover)
{
	return getChildNodeData(objApprover, "IS_OPEN");
}

function setApproverIsOpen(objApprover, strValue)
{
	setChildNodeData(objApprover, "IS_OPEN", strValue);
}

function getApproverAction(objApprover)
{
	return getChildNodeData(objApprover, "APPROVER_ACTION");
}

function setApproverAction(objApprover, strValue)
{
	setChildNodeData(objApprover, "APPROVER_ACTION", strValue);
}

function getApproverType(objApprover)
{
	return getChildNodeData(objApprover, "APPROVER_TYPE");
}

function setApproverType(objApprover, strValue)
{
	setChildNodeData(objApprover, "APPROVER_TYPE", strValue);
}

function getApproverAdditionalRole(objApprover)
{
	return getChildNodeData(objApprover, "ADDITIONAL_ROLE");
}

function setApproverAdditionalRole(objApprover, strValue)
{
	setChildNodeData(objApprover, "ADDITIONAL_ROLE", strValue);
}

function getApproverKeepStatus(objApprover)
{
	return getChildNodeData(objApprover, "KEEP_STATUS");
}

function setApproverKeepStatus(objApprover, strValue)
{
	setChildNodeData(objApprover, "KEEP_STATUS", strValue);
}

function getApproverKeepDate(objApprover)
{
	return getChildNodeData(objApprover, "KEEP_DATE");
}

function setApproverKeepDate(objApprover, strValue)
{
	setChildNodeData(objApprover, "KEEP_DATE", strValue);
}

function getApproverEmptyReason(objApprover)
{
	return getChildNodeData(objApprover, "EMPTY_REASON");
}

function setApproverEmptyReason(objApprover, strValue)
{
	setChildNodeData(objApprover, "EMPTY_REASON", strValue);
}

function getApproverIsDocModified(objApprover)
{
	return getChildNodeData(objApprover, "IS_DOC_MODIFIED");
}

function setApproverIsDocModified(objApprover, strValue)
{
	setChildNodeData(objApprover, "IS_DOC_MODIFIED", strValue);
}

function getApproverIsLineModified(objApprover)
{
	return getChildNodeData(objApprover, "IS_LINE_MODIFIED");
}

function setApproverIsLineModified(objApprover, strValue)
{
	setChildNodeData(objApprover, "IS_LINE_MODIFIED", strValue);
}

function getApproverIsAttachModified(objApprover)
{
	return getChildNodeData(objApprover, "IS_ATTACH_MODIFIED");
}

function setApproverIsAttachModified(objApprover, strValue)
{
	setChildNodeData(objApprover, "IS_ATTACH_MODIFIED", strValue);
}

function getApproverOpinion(objApprover)
{
	return getChildNodeData(objApprover, "OPINION");
}

function setApproverOpinion(objApprover, strValue)
{
	setChildNodeData(objApprover, "OPINION", strValue);
}

function getApproverRepID(objApprover)
{
	return getChildNodeData(objApprover, "REP_ID");
}

function setApproverRepID(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_ID", strValue);
}

function getApproverRepName(objApprover)
{
	return getChildNodeData(objApprover, "REP_NAME");
}

function setApproverRepName(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_NAME", strValue);
}

function getApproverRepPosition(objApprover)
{
	return getChildNodeData(objApprover, "REP_POSITION");
}

function setApproverRepPosition(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_POSITION", strValue);
}

function getApproverRepPositionAbbr(objApprover)
{
	return getChildNodeData(objApprover, "REP_POSITION_ABBR");
}

function setApproverRepPositionAbbr(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_POSITION_ABBR", strValue);
}

function getApproverRepLevel(objApprover)
{
	return getChildNodeData(objApprover, "REP_LEVEL");
}

function setApproverRepLevel(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_LEVEL", strValue);
}

function getApproverRepLevelAbbr(objApprover)
{
	return getChildNodeData(objApprover, "REP_LEVEL_ABBR");
}

function setApproverRepLevelAbbr(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_LEVEL_ABBR", strValue);
}

function getApproverRepDuty(objApprover)
{
	return getChildNodeData(objApprover, "REP_DUTY");
}

function setApproverRepDuty(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_DUTY", strValue);
}

function getApproverRepTitle(objApprover)
{
	return getChildNodeData(objApprover, "REP_TITLE");
}

function setApproverRepTitle(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_TITLE", strValue);
}

function getApproverRepCompany(objApprover)
{
	return getChildNodeData(objApprover, "REP_COMPANY");
}

function setApproverRepCompany(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_COMPANY", strValue);
}

function getApproverRepDeptName(objApprover)
{
	return getChildNodeData(objApprover, "REP_DEPT_NAME");
}

function setApproverRepDeptName(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_DEPT_NAME", strValue);
}

function getApproverRepDeptCode(objApprover)
{
	return getChildNodeData(objApprover, "REP_DEPT_CODE");
}

function setApproverRepDeptCode(objApprover, strValue)
{
	setChildNodeData(objApprover, "REP_DEPT_CODE", strValue);
}

// DELIVERERS Method

function getDeliverers()
{
	var objDeliverers = ApprovalDoc.selectSingleNode("//DELIVERERS");
	if (objDeliverers == null)
		objDeliverers = createDataNode("APPROVAL_DOC", "DELIVERERS");

	return objDeliverers;
}

// DELIVERER Method

function getDelivererByDelivererType(strDelivererType)
{
	var objDeliverers = getDeliverers();
	return objDeliverers.selectSingleNode("DELIVERER[DELIVERER_TYPE='" + strDelivererType + "']");
}

function addDeliverer()
{
	var objDeliverers = getDeliverers();
	return addDataNode(objDeliverers, "DELIVERER");
}

function removeDeliverer(objDeliverer)
{
	objDeliverer.parentNode.removeChild(objDeliverer);
}

function getDelivererType(objDeliverer)
{
	return getChildNodeData(objDeliverer, "DELIVERER_TYPE");
}

function setDelivererType(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "DELIVERER_TYPE", strValue);
}

function getDelivererUserID(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_ID");
}

function setDelivererUserID(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_ID", strValue);
}

function getDelivererUserName(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_NAME");
}

function setDelivererUserName(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_NAME", strValue);
}

function getDelivererUserPosition(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_POSITION");
}

function setDelivererUserPosition(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_POSITION", strValue);
}

function getDelivererUserPositionAbbr(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_POSITION_ABBR");
}

function setDelivererUserPositionAbbr(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_POSITION_ABBR", strValue);
}

function getDelivererUserLevel(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_LEVEL");
}

function setDelivererUserLevel(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_LEVEL", strValue);
}

function getDelivererUserLevelAbbr(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_LEVEL_ABBR");
}

function setDelivererUserLevelAbbr(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_LEVEL_ABBR", strValue);
}

function getDelivererUserDuty(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_DUTY");
}

function setDelivererUserDuty(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_DUTY", strValue);
}

function getDelivererUserTitle(objDeliverer)
{
	return getChildNodeData(objDeliverer, "USER_TITLE");
}

function setDelivererUserTitle(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "USER_TITLE", strValue);
}

function getDelivererCompany(objDeliverer)
{
	return getChildNodeData(objDeliverer, "COMPANY");
}

function setDelivererCompany(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "COMPANY", strValue);
}

function getDelivererDeptName(objDeliverer)
{
	return getChildNodeData(objDeliverer, "DEPT_NAME");
}

function setDelivererDeptName(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "DEPT_NAME", strValue);
}

function getDelivererDeptCode(objDeliverer)
{
	return getChildNodeData(objDeliverer, "DEPT_CODE");
}

function setDelivererDeptCode(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "DEPT_CODE", strValue);
}

function getDelivererSignDate(objDeliverer)
{
	return getChildNodeData(objDeliverer, "SIGN_DATE");
}

function setDelivererSignDate(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "SIGN_DATE", strValue);
}

function getDelivererSignFilename(objDeliverer)
{
	return getChildNodeData(objDeliverer, "SIGN_FILE_NAME");
}

function setDelivererSignFilename(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "SIGN_FILE_NAME", strValue);
}

function getDelivererOpinion(objDeliverer)
{
	return getChildNodeData(objDeliverer, "OPINION");
}

function setDelivererOpinion(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "OPINION", strValue);
}

function getDelivererRepID(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_ID");
}

function setDelivererRepID(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_ID", strValue);
}

function getDelivererRepName(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_NAME");
}

function setDelivererRepName(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_NAME", strValue);
}

function getDelivererRepPosition(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_POSITION");
}

function setDelivererRepPosition(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_POSITION", strValue);
}

function getDelivererRepPositionAbbr(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_POSITION_ABBR");
}

function setDelivererRepPositionAbbr(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_POSITION_ABBR", strValue);
}

function getDelivererRepLevel(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_LEVEL");
}

function setDelivererRepLevel(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_LEVEL", strValue);
}

function getDelivererRepLevelAbbr(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_LEVEL_ABBR");
}

function setDelivererRepLevelAbbr(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_LEVEL_ABBR", strValue);
}

function getDelivererRepDuty(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_DUTY");
}

function setDelivererRepDuty(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_DUTY", strValue);
}

function getDelivererRepTitle(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_TITLE");
}

function setDelivererRepTitle(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_TITLE", strValue);
}

function getDelivererRepCompany(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_COMPANY");
}

function setDelivererRepCompany(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_COMPANY", strValue);
}

function getDelivererRepDeptName(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_DEPT_NAME");
}

function setDelivererRepDeptName(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_DEPT_NAME", strValue);
}

function getDelivererRepDeptCode(objDeliverer)
{
	return getChildNodeData(objDeliverer, "REP_DEPT_CODE");
}

function setDelivererRepDeptCode(objDeliverer, strValue)
{
	setChildNodeData(objDeliverer, "REP_DEPT_CODE", strValue);
}


// RECIP_GROUP Method

function getRecipGroup(strCaseNumber)
{
	return ApprovalDoc.selectSingleNode("//RECIPIENTS/RECIP_GROUP[CASE_NUMBER='" + strCaseNumber + "']");
}

function getRecipGroupByGroupType(strCaseNumber, strGroupType)
{
	return ApprovalDoc.selectSingleNode("//RECIPIENTS/RECIP_GROUP[CASE_NUMBER='" + strCaseNumber + "' && RECIP_GROUP_TYPE='" + strGroupType + "']");
}

function removeRecipGroup(strCaseNumber)
{
	var objRecipGroup = getRecipGroup(strCaseNumber);
	if (objRecipGroup != null)
		objRecipGroup.parentNode.removeChild(objRecipGroup);
}

function addRecipGroup(strCaseNumber, strDisplayAs)
{
	var objRecipients = ApprovalDoc.selectSingleNode("//RECIPIENTS");
	if (objRecipients == null)
		objRecipients = createDataNode("APPROVAL_DOC ", "RECIPIENTS");

	var objRecipGroup = addDataNode(objRecipients, "RECIP_GROUP");

	setRecipGroupCaseNumber(objRecipGroup, strCaseNumber);
	setRecipGroupDisplayAs(objRecipGroup, strDisplayAs);

	return objRecipGroup;
}

function getFirstRecipGroup()
{
	return ApprovalDoc.selectSingleNode("//RECIPIENTS/RECIP_GROUP");
}

function getNextRecipGroup(objRecipGroup)
{
	return objRecipGroup.nextSibling;
}

function getRecipGroupType(objRecipGroup)
{
	return getChildNodeData(objRecipGroup, "RECIP_GROUP_TYPE");
}

function setRecipGroupType(objRecipGroup, strValue)
{
	setChildNodeData(objRecipGroup, "RECIP_GROUP_TYPE", strValue);
}

function getRecipGroupCaseNumber(objRecipGroup)
{
	return getChildNodeData(objRecipGroup, "CASE_NUMBER");
}

function setRecipGroupCaseNumber(objRecipGroup, strValue)
{
	setChildNodeData(objRecipGroup, "CASE_NUMBER", strValue);
}

function getRecipGroupDisplayAs(objRecipGroup)
{
	return getChildNodeData(objRecipGroup, "DISPLAY_AS");
}

function setRecipGroupDisplayAs(objRecipGroup, strValue)
{
	setChildNodeData(objRecipGroup, "DISPLAY_AS", strValue);
}

// RECIPIENT Method
function getRecipientCount(objRecipGroup)
{
	return objRecipGroup.selectNodes("RECIPIENT").length;
}

function getRefRecipientCount(objRecipGroup)
{
	var nRefRecipientCount = 0;
	if (objRecipGroup != null)
	{
		var objRecipient = getFirstRecipient(objRecipGroup);
		while (objRecipient != null)
		{
			if (getRecipientRefDeptName(objRecipient) != "")
				nRefRecipientCount ++;
			objRecipient = getNextRecipient(objRecipient);
		}
	}
	return nRefRecipientCount;
}

function getRecipientByActualDeptCode(strActualDeptCode)
{
	return ApprovalDoc.selectSingleNode("//RECIPIENT[ACTUAL_DEPT_CODE='" + strActualDeptCode + "']");
}

function getRecipientByMethod(strMethod)
{
	return ApprovalDoc.selectSingleNode("//RECIPIENT[@METHOD='" + strMethod + "']");
}

function getFirstRecipient(objRecipGroup)
{
	var objRecipient = objRecipGroup.firstChild;
	while (objRecipient != null)
	{
		if (objRecipient.nodeName == "RECIPIENT")
			break;

		objRecipient = objRecipient.nextSibling;
	}

	return objRecipient;
}

function getLastRecipient(objRecipGroup)
{
	var objRecipient = objRecipGroup.lastChild;
	while (objRecipient != null)
	{
		if (objRecipient.nodeName == "RECIPIENT")
			break;

		objRecipient = objRecipient.previousSibling;
	}

	return objRecipient;
}

function getNextRecipient(objRecipient)
{
	var objRecipient = objRecipient.nextSibling;
	while (objRecipient != null)
	{
		if (objRecipient.nodeName == "RECIPIENT")
			break;

		objRecipient = objRecipient.nextSibling;
	}

	return objRecipient;
}

function getPrevRecipient(objRecipient)
{
	var objRecipient = objRecipient.previousSibling;
	while (objRecipient != null)
	{
		if (objRecipient.nodeName == "RECIPIENT")
			break;

		objRecipient = objRecipient.previousSibling;
	}

	return objRecipient;
}

function addRecipient(objRecipGroup)
{
	var objRecipient = addDataNode(objRecipGroup, "RECIPIENT");
	return objRecipient;
}

function removeRecipient(objRecipient)
{
	objRecipient.parentNode.removeChild(objRecipient);
}

function getRecipientMethod(objRecipient)
{
	return objRecipient.getAttribute("METHOD");
}

function setRecipientMethod(objRecipient, strMethod)
{
	objRecipient.setAttribute("METHOD", strMethod);
}

function getRecipientIsNew(objRecipient)
{
	return objRecipient.getAttribute("ISNEW");
}

function setRecipientIsNew(objRecipient, strIsNew)
{
	objRecipient.setAttribute("ISNEW", strIsNew);
}

function getRecipients()
{
	return ApprovalDoc.selectSingleNode("//RECIPIENTS");
}

function getRecipGroupFromOrigin(objRecipients, strCaseNumber)
{
	return objRecipients.selectSingleNode("RECIP_GROUP[CASE_NUMBER='" + strCaseNumber + "']");
}

function getRecipientCountWithNew(objRecipGroup)
{
	return objRecipGroup.selectNodes("RECIPIENT[@ISNEW='Y']").length;
}

function getRecipientEnforceBound(objRecipient)
{
	return getChildNodeData(objRecipient, "ENFORCE_BOUND");
}

function setRecipientEnforceBound(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "ENFORCE_BOUND", strValue);
}

function getRecipientDeptName(objRecipient)
{
	return getChildNodeData(objRecipient, "DEPT_NAME");
}

function setRecipientDeptName(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "DEPT_NAME", strValue);
}

function getRecipientDeptCode(objRecipient)
{
	return getChildNodeData(objRecipient, "DEPT_CODE");
}

function setRecipientDeptCode(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "DEPT_CODE", strValue);
}

function getRecipientDeptSymbol(objRecipient)
{
	return getChildNodeData(objRecipient, "DEPT_SYMBOL");
}

function setRecipientDeptSymbol(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "DEPT_SYMBOL", strValue);
}

function getRecipientDeptChief(objRecipient)
{
	return getChildNodeData(objRecipient, "DEPT_CHIEF");
}

function setRecipientDeptChief(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "DEPT_CHIEF", strValue);
}

function getRecipientRefDeptName(objRecipient)
{
	return getChildNodeData(objRecipient, "REF_DEPT_NAME");
}

function setRecipientRefDeptName(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "REF_DEPT_NAME", strValue);
}

function getRecipientRefDeptCode(objRecipient)
{
	return getChildNodeData(objRecipient, "REF_DEPT_CODE");
}

function setRecipientRefDeptCode(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "REF_DEPT_CODE", strValue);
}

function getRecipientRefDeptSymbol(objRecipient)
{
	return getChildNodeData(objRecipient, "REF_DEPT_SYMBOL");
}

function setRecipientRefDeptSymbol(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "REF_DEPT_SYMBOL", strValue);
}

function getRecipientRefDeptChief(objRecipient)
{
	return getChildNodeData(objRecipient, "REF_DEPT_CHIEF");
}

function setRecipientRefDeptChief(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "REF_DEPT_CHIEF", strValue);
}

function getRecipientActualDeptCode(objRecipient)
{
	return getChildNodeData(objRecipient, "ACTUAL_DEPT_CODE");
}

function setRecipientActualDeptCode(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "ACTUAL_DEPT_CODE", strValue);
}

function getRecipientIsPubdocRecip(objRecipient)
{
	return getChildNodeData(objRecipient, "IS_PUBDOC_RECIP");
}

function setRecipientIsPubdocRecip(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "IS_PUBDOC_RECIP", strValue);
}

function getRecipientIsCertExist(objRecipient)
{
	return getChildNodeData(objRecipient, "IS_CERT_EXIST");
}

function setRecipientIsCertExist(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "IS_CERT_EXIST", strValue);
}

function getRecipientSendingType(objRecipient)
{
	return getChildNodeData(objRecipient, "SENDING_TYPE");
}

function setRecipientSendingType(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "SENDING_TYPE", strValue);
}

function getRecipientAcceptor(objRecipient)
{
	return getChildNodeData(objRecipient, "ACCEPTOR");
}

function setRecipientAcceptor(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "ACCEPTOR", strValue);
}

function getRecipientAcceptDate(objRecipient)
{
	return getChildNodeData(objRecipient, "ACCEPT_DATE");
}

function setRecipientAcceptDate(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "ACCEPT_DATE", strValue);
}

function getRecipientReceiptStatus(objRecipient)
{
	return getChildNodeData(objRecipient, "RECEIPT_STATUS");
}

function setRecipientReceiptStatus(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "RECEIPT_STATUS", strValue);
}

function getRecipientDescription(objRecipient)
{
	return getChildNodeData(objRecipient, "DESCRIPTION");
}

function setRecipientDescription(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "DESCRIPTION", strValue);
}

function getRecipientIsPubDocRecip(objRecipient)
{
	return getChildNodeData(objRecipient, "IS_PUBDOC_RECIP");
}

function setRecipientIsPubDocRecip(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "IS_PUBDOC_RECIP", strValue);
}

function getRecipientIsCertExist(objRecipient)
{
	return getChildNodeData(objRecipient, "IS_CERT_EXIST");
}

function setRecipientIsCertExist(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "IS_CERT_EXIST", strValue);
}

function getRecipientSendingType(objRecipient)
{
	return getChildNodeData(objRecipient, "SENDING_TYPE");
}

function setRecipientSendingType(objRecipient, strValue)
{
	setChildNodeData(objRecipient, "SENDING_TYPE", strValue);
}

// FILE_BODY Method

function clearFileBodyInfo()
{
	var objFileBodies = ApprovalDoc.selectSingleNode("//FILE_INFO/FILE_BODIES");

	if (objFileBodies != null)
		removeAllChildNode(objFileBodies);
}

function addBodyFileInfo(strDisplayName, strFileName, strFileSize, strLocation)
{
	var objFileBodies = ApprovalDoc.selectSingleNode("//FILE_INFO/FILE_BODIES");
	if (objFileBodies == null)
		objFileBodies = createDataNode("FILE_INFO", "FILE_BODIES");

	var objFileBody = addDataNode(objFileBodies, "FILE_BODY");
	objFileBody.setAttribute("METHOD", "add");

	setAttachInfo(objFileBody, strDisplayName, strFileName, strFileSize, strLocation);

	return objFileBody;
}

function getBodyFileObj()
{
	var objBodyFile = ApprovalDoc.selectSingleNode("//FILE_INFO/FILE_BODIES/FILE_BODY");

	if (getCurrentLineType() == "5")
	{
		var strLineName = getCurrentLineName();
		var objInspBodyFile = findExtendAttachInfo("InspBody", strLineName, "0");
		if (objInspBodyFile != null)
			objBodyFile = objInspBodyFile;
	}

	return objBodyFile;
}

// ENFORCE_BODY Method

function addEnforceFileInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation)
{
	objExtendAttach = addExtendAttachInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation);

	setChildNodeData(objExtendAttach, "CLASSIFY", "EnforceDoc");

	return objExtendAttach;
}

function getEnforceFileObj(strCaseNumber)
{
	return ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && CLASSIFY='EnforceDoc']");
}

// RELATED_ATTACH Method

function clearRelatedAttachInfo()
{
	var objRelatedAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES");

	if (objRelatedAttaches != null)
		removeAllChildNode(objRelatedAttaches);
}

function removeRelatedAttachInfo(strFileName)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[FILE_NAME='" + strFileName + "']");
	if (objRelatedAttach != null)
		objRelatedAttach.parentNode.removeChild(objRelatedAttach);
}

function getRelatedAttachByClassify(strClassify)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[CLASSIFY='" + strClassify + "']");
	return objRelatedAttach;
}

// 공문서유통
function getRelatedAttachListByClassify(strClassify)
{
	var objRelatedAttach = ApprovalDoc.selectNodes("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[CLASSIFY='" + strClassify + "']");
	return objRelatedAttach;
}

function findRelatedAttachInfo(strFileName, strClassify, strSubDiv)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[FILE_NAME='" + strFileName + "' && CLASSIFY='" + strClassify + "' && SUBDIV='" + strSubDiv + "']");
	return objRelatedAttach;
}

function findRelatedSealInfo(strClassify, strSubDiv, strMethod)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[CLASSIFY='" + strClassify + "' && SUBDIV='" + strSubDiv + "' && @METHOD='" + strMethod + "']");
	return objRelatedAttach;
}

// 공문서유통
function getRelatedSealInfo()
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[CLASSIFY='DocStamp' && (@METHOD='add' || @METHOD='') && (@SUBDIV='DeptStamp' || @SUBDIV='DeptOmitStamp' || @SUBDIV='CompanyStamp' || @SUBDIV='CompanyOmitStamp' || @SUBDIV='EnforceOmitStamp')]");
	return objRelatedAttach;
}

function addRelatedAttachInfo(strDisplayName, strFileName, strFileSize, strLocation)
{
	var objRelatedAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES");
	if (objRelatedAttaches == null)
		objRelatedAttaches = createDataNode("FILE_INFO", "RELATED_ATTACHES");

	var objRelatedAttach = addDataNode(objRelatedAttaches, "RELATED_ATTACH");
	objRelatedAttach.setAttribute("METHOD", "add");

	setAttachInfo(objRelatedAttach, strDisplayName, strFileName, strFileSize, strLocation);

	return objRelatedAttach;
}

function getFirstRelatedAttach()
{
	var objRelatedAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES");
	if (objRelatedAttaches != null)
		return objRelatedAttaches.firstChild;

	return null;
}

function getLastRelatedAttach()
{
	var objRelatedAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES");
	if (objRelatedAttaches != null)
		return objRelatedAttaches.lastChild;

	return null;
}

function getNextRelatedAttach(objRelatedAttach)
{
	return objRelatedAttach.nextSibling;
}

function getPrevRelatedAttach(objRelatedAttach)
{
	return objRelatedAttach.previousSibling;
}

function getRelatedAttachBySubDiv(strSubDiv)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[SUBDIV='" + strSubDiv + "']");
	return objRelatedAttach;
}

function findRelatedAttachInfoByFileName(strFileName)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[FILE_NAME='" + strFileName + "']");
	return objExtendAttach;
}

function removeRelatedAttachBySubDiv(strSubDiv)
{
	var objRelatedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH[SUBDIV='" + strSubDiv + "']");

	if (objRelatedAttach != null)
		objRelatedAttaches.parentNode.removeChild(objRelatedAttach);
}

function removeSealRelatedAttaches()
{
	var objRelatedAttaches = ApprovalDoc.selectNodes("//FILE_INFO/RELATED_ATTACHES/RELATED_ATTACH");
	for (var i = 0; i < objRelatedAttaches.length; i++)
	{
		var objRelatedAttach = objRelatedAttaches.item(i);
		var strClassify = getAttachClassify(objRelatedAttach);
		if (strClassify == "DocStamp")
		{
			var strSubDiv = getAttachSubDiv(objRelatedAttach);
			if (strSubDiv == "DeptStamp" || strSubDiv == "DeptOmitStamp" ||
				strSubDiv == "CompanyStamp" || strSubDiv == "CompanyOmitStamp")
			{
				objRelatedAttach.parentNode.removeChild(objRelatedAttach);
			}
		}
	}
}

// GENERAL_ATTACH Method

function clearGeneralAttachInfo()
{
	var objGeneralAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES");

	if (objGeneralAttaches != null)
		removeAllChildNode(objGeneralAttaches);

	setIsAttached("N");
}

function removeGeneralAttachInfo(strFileName)
{
	var objGeneralAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES/GENERAL_ATTACH[FILE_NAME='" + strFileName + "']");
	if (objGeneralAttach != null)
		objGeneralAttach.parentNode.removeChild(objGeneralAttach);

	if (getFirstGeneralAttach() == null)
		setIsAttached("N");
}

function findGeneralAttachInfo(strFileName)
{
	var objGeneralAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES/GENERAL_ATTACH[FILE_NAME='" + strFileName + "']");
	return objGeneralAttach;
}

function addGeneralAttachInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation)
{
	var objGeneralAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES");
	if (objGeneralAttaches == null)
		objGeneralAttaches = createDataNode("FILE_INFO", "GENERAL_ATTACHES");

	var objGeneralAttach = addDataNode(objGeneralAttaches, "GENERAL_ATTACH");
	objGeneralAttach.setAttribute("METHOD", "add");

	setChildNodeData(objGeneralAttach, "CASE_NUMBER", strCaseNumber);
	setAttachInfo(objGeneralAttach, strDisplayName, strFileName, strFileSize, strLocation);

	setIsAttached("Y");

	return objGeneralAttach;
}

function getFirstGeneralAttach()
{
	var objGeneralAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES");
	if (objGeneralAttaches != null)
		return objGeneralAttaches.firstChild;

	return null;
}

function getLastGeneralAttach()
{
	var objGeneralAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES");
	if (objGeneralAttaches != null)
		return objGeneralAttaches.lastChild;

	return null;
}

function getNextGeneralAttach(objGeneralAttach)
{
	return objGeneralAttach.nextSibling;
}

function getPrevGeneralAttach(objGeneralAttach)
{
	return objGeneralAttach.previousSibling;
}

function getGeneralAttachListByCaseNumber(strCaseNumber)
{
	return ApprovalDoc.selectNodes("//FILE_INFO/GENERAL_ATTACHES/GENERAL_ATTACH[CASE_NUMBER='" + strCaseNumber + "']");
}

function getGeneralAttachListCount(objGeneralAttachList)
{
	return objGeneralAttachList.length;
}

function getGeneralAttachInList(objGeneralAttachList, nIndex)
{
	return objGeneralAttachList.item(nIndex);
}

function removeGeneralAttachesByCaseNumber(nCaseNumber)
{
	var objGeneralAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/GENERAL_ATTACHES");
	if (objGeneralAttaches != null)
	{
		var objGeneralAttachList = objGeneralAttaches.selectNodes("GENERAL_ATTACH[CASE_NUMBER='" + nCaseNumber + "']");
		for (var i = 0; i < objGeneralAttachList.length; i++)
			objGeneralAttaches.removeChild(objGeneralAttachList.item(i));
	}

	if (getFirstGeneralAttach() == null)
		setIsAttached("N");
}

// EXTENDED_ATTACH Method

function clearExtendAttachInfo()
{
	var objExtendAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES");

	if (objExtendAttaches != null)
		removeAllChildNode(objExtendAttaches);
}

function removeExtendAttachInfo(strFileName)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[FILE_NAME='" + strFileName + "']");
	if (objExtendAttach != null)
		objExtendAttach.parentNode.removeChild(objExtendAttach);
}

// 20030829, 시행문 미리보기시 추가된 attach 제거
function clearEnforceAttachInfo()
{
	var objExtendAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES");
	if (objExtendAttaches != null)
	{
		var objExtendAttachList = objExtendAttaches.selectNodes("EXTENDED_ATTACH[CLASSIFY='EnforceDoc' || CLASSIFY='EnforceRelated']");
		for (var i = 0; i < objExtendAttachList.length; i++)
			objExtendAttaches.removeChild(objExtendAttachList.item(i));
	}
}

function findExtendAttachInfoByFileName(strFileName)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[FILE_NAME='" + strFileName + "']");
	return objExtendAttach;
}

function findExtendAttachInfo(strClassify, strLineName, strSerialOrder)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CLASSIFY='" + strClassify + "' && CHARGER_LINE_NAME='" + strLineName + "' && CHARGER_SERIAL_ORDER='" + strSerialOrder + "']");
	return objExtendAttach;
}

function findExtendAttachList(strClassify, strLineName, strSerialOrder)
{
	var objExtendAttachList = ApprovalDoc.selectNodes("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CLASSIFY='" + strClassify + "' && CHARGER_LINE_NAME='" + strLineName + "' && CHARGER_SERIAL_ORDER='" + strSerialOrder + "']");
	return objExtendAttachList;
}

function findExtendedSealInfo(strCaseNumber, strClassify, strSubDiv, strMethod)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && CLASSIFY='" + strClassify + "' && SUBDIV='" + strSubDiv + "' && @METHOD='" + strMethod + "']");
	return objExtendAttach;
}

// 공문서유통
function getExtendedSealInfo(strCaseNumber)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && CLASSIFY='EnforceRelated' && (@METHOD='add' || @METHOD='') && (@SUBDIV='DeptStamp' || @SUBDIV='DeptOmitStamp' || @SUBDIV='CompanyStamp' || @SUBDIV='CompanyOmitStamp' || @SUBDIV='EnforceOmitStamp')]");
	return objExtendAttach;
}

function findModifiedAttach(strFileName, strLineName, strSerialOrder)
{
	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CLASSIFY='ModifiedAttach' && FILE_NAME='" + strFileName + "' && CHARGER_LINE_NAME='" + strLineName + "' && CHARGER_SERIAL_ORDER='" + strSerialOrder + "']");
	return objExtendAttach;
}

function findModifiedOriginAttach(strOriginFileName, strLineName, strSerialOrder)
{
	var nFind = strOriginFileName.indexOf(".");
	if (nFind > 32)
		nFind = 32;
	var strSubDiv = strOriginFileName.substring(0, nFind);

	var objExtendAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CLASSIFY='ModifiedAttach' && SUBDIV='" + strSubDiv + "' && CHARGER_LINE_NAME='" + strLineName + "' && CHARGER_SERIAL_ORDER='" + strSerialOrder + "']");
	return objExtendAttach;
}

function addExtendAttachInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation)
{
	var objExtendAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES");
	if (objExtendAttaches == null)
		objExtendAttaches = createDataNode("FILE_INFO", "EXTENDED_ATTACHES");

	var objExtendAttach = addDataNode(objExtendAttaches, "EXTENDED_ATTACH");
	objExtendAttach.setAttribute("METHOD", "add");

	setChildNodeData(objExtendAttach, "CASE_NUMBER", strCaseNumber);
	setAttachInfo(objExtendAttach, strDisplayName, strFileName, strFileSize, strLocation);

	return objExtendAttach;
}

function getFirstExtendAttach()
{
	var objExtendAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES");
	if (objExtendAttaches != null)
		return objExtendAttaches.firstChild;

	return null;
}

function getLastExtendAttach()
{
	var objExtendAttaches = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES");
	if (objExtendAttaches != null)
		return objExtendAttaches.lastChild;

	return null;
}

function getNextExtendAttach(objExtendAttach)
{
	return objExtendAttach.nextSibling;
}

function getPrevExtendAttach(objExtendAttach)
{
	return objExtendAttach.previousSibling;
}

function getExtendAttachBySubDiv(strCaseNumber, strSubDiv)
{
	var objExtendedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && SUBDIV='" + strSubDiv + "']");
	return objExtendedAttach;
}

function getExtendAttachByClassify(strCaseNumber, strClassify)
{
	var objExtendedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && CLASSIFY='" + strClassify + "']");
	return objExtendedAttach;
}

function removeExtendAttachBySubDiv(strCaseNumber, strSubDiv)
{
	var objExtendedAttach = ApprovalDoc.selectSingleNode("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "' && SUBDIV='" + strSubDiv + "']");

	if (objExtendedAttach != null)
		objExtendedAttach.parentNode.removeChild(objExtendedAttach);
}

function removeSealExtendAttaches(strCaseNumber)
{
	var objExtendAttaches = ApprovalDoc.selectNodes("//FILE_INFO/EXTENDED_ATTACHES/EXTENDED_ATTACH[CASE_NUMBER='" + strCaseNumber + "']");
	for (var i = 0; i < objExtendAttaches.length; i++)
	{
		var objExtendAttach = objExtendAttaches.item(i);
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			var strSubDiv = getAttachSubDiv(objExtendAttach);
			if (strSubDiv == "DeptStamp" || strSubDiv == "DeptOmitStamp" ||
				strSubDiv == "CompanyStamp" || strSubDiv == "CompanyOmitStamp")
			{
				objExtendAttach.parentNode.removeChild(objExtendAttach);
			}
		}
	}
}

// RELATED_SYSTEM Method

function addRelatedSystem(strSystemID, strProcessType, strSettingType, strSendingType, strRelatedType, strAgentID, strSystemData)
{
	var objRelatedSystems = ApprovalDoc.selectSingleNode("//RELATED_SYSTEMS");
	if (objRelatedSystems == null)
		objRelatedSystems = createDataNode("APPROVAL_DOC ", "RELATED_SYSTEMS");

	var objRelatedSystem = getRelatedSystemBySystemID(strSystemID);
	if (objRelatedSystem == null)
		objRelatedSystem = addDataNode(objRelatedSystems, "RELATED_SYSTEM");

	setRelatedSystemID(objRelatedSystem, strSystemID);
	setRelatedSystemProcessType(objRelatedSystem, strProcessType);
	setRelatedSystemSettingType(objRelatedSystem, strSettingType);
	setRelatedSystemSendingType(objRelatedSystem, strSendingType);
	setRelatedSystemRelatedType(objRelatedSystem, strRelatedType);
	setRelatedSystemAgentID(objRelatedSystem, strAgentID);
	setRelatedSystemData(objRelatedSystem, strSystemData);

	return objRelatedSystem;
}

function getRelatedSystemBySystemID(strSystemID)
{
	return ApprovalDoc.selectSingleNode("//RELATED_SYSTEMS/RELATED_SYSTEM[SYSTEM_ID='" + strSystemID + "']");
}

function removeRelatedSystemBySystemID(strSystemID)
{
	var objRelatedSystem = getRelatedSystemBySystemID(strSystemID);
	if (objRelatedSystem != null)
		removeAllChildNode(objRelatedSystem.parentNode);
}

function setRelatedSystemID(objRelatedSystem, strSystemID)
{
	setChildNodeData(objRelatedSystem, "SYSTEM_ID", strSystemID);
}

function getReleatedSystemID(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "SYSTEM_ID");
}

function setRelatedSystemProcessType(objRelatedSystem, strProcessType)
{
	setChildNodeData(objRelatedSystem, "PROCESS_TYPE", strProcessType);
}

function getReleatedSystemProcessType(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "PROCESS_TYPE	");
}

function setRelatedSystemSettingType(objRelatedSystem, strSettingType)
{
	setChildNodeData(objRelatedSystem, "SETTING_TYPE", strSettingType);
}

function getReleatedSystemSettingType(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "SETTING_TYPE	");
}

function setRelatedSystemSendingType(objRelatedSystem, strSendingType)
{
	setChildNodeData(objRelatedSystem, "SENDING_TYPE", strSendingType);
}

function getReleatedSystemSendingType(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "SENDING_TYPE	");
}

function setRelatedSystemRelatedType(objRelatedSystem, strRelatedType)
{
	setChildNodeData(objRelatedSystem, "RELATED_TYPE", strRelatedType);
}

function getReleatedSystemRelatedType(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "RELATED_TYPE	");
}

function setRelatedSystemAgentID(objRelatedSystem, strAgentID)
{
	setChildNodeData(objRelatedSystem, "AGENT_ID", strAgentID);
}

function getReleatedSystemRelatedType(objRelatedSystem)
{
	return getChildNodeData(objRelatedSystem, "AGENT_ID");
}

function setRelatedSystemData(objRelatedSystem, strSystemData)
{
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	if (objXMLDom.loadXML(strSystemData) == false)
		return;

	var objSystemData = objRelatedSystem.selectSingleNode("SYSTEM_DATA");
	if (objSystemData == null)
		objSystemData = addDataNode(objRelatedSystem, "SYSTEM_DATA");
	else
	{
//		var objDocument = objSystemData.selectSingleNode("DOCUMENT");
//		if (objDocument != null)
//			objDocument.parentNode.removeChild(objDocument);
//		else
			removeAllChildNode(objSystemData);
	}

	copyXMLNode(objXMLDom.documentElement, objSystemData);
}

function getReleatedSystemData(objRelatedSystem)
{
	var strSystemData = "";

	var objSystemData = objRelatedSystem.selectSingleNode("SYSTEM_DATA");
	if (objSystemData != null)
		strSystemData = objSystemData.xml;

	return strSystemData;
}

function getReleatedSystemDataObj(objRelatedSystem)
{
	return objRelatedSystem.selectSingleNode("SYSTEM_DATA");
}

// Common Attach Method
function setAttachInfo(objAttach, strDisplayName, strFileName, strFileSize, strLocation)
{
	setChildNodeData(objAttach, "DISPLAY_NAME", strDisplayName);
	setChildNodeData(objAttach, "FILE_NAME", strFileName);
	setChildNodeData(objAttach, "FILE_SIZE", strFileSize);
	setChildNodeData(objAttach, "LOCATION", strLocation);
}

function getAttachCaseNumber(objAttach)
{
	return getChildNodeData(objAttach, "CASE_NUMBER");
}

function setAttachCaseNumber(objAttach, strCaseNumber)
{
	setChildNodeData(objAttach, "CASE_NUMBER", strCaseNumber);
}

function getAttachDisplayName(objAttach)
{
	return getChildNodeData(objAttach, "DISPLAY_NAME");
}

function setAttachDisplayName(objAttach, strDisplayName)
{
	setChildNodeData(objAttach, "DISPLAY_NAME", strDisplayName);
}

function getAttachFileName(objAttach)
{
	return getChildNodeData(objAttach, "FILE_NAME");
}

function setAttachFileName(objAttach, strFileName)
{
	setChildNodeData(objAttach, "FILE_NAME", strFileName);
}

function getAttachFileSize(objAttach)
{
	return getChildNodeData(objAttach, "FILE_SIZE");
}

function setAttachFileSize(objAttach, strFileSize)
{
	setChildNodeData(objAttach, "FILE_SIZE", strFileSize);
}

function getAttachPageCount(objAttach)
{
	return getChildNodeData(objAttach, "PAGE_COUNT");
}

function setAttachPageCount(objAttach, strPageCount)
{
	setChildNodeData(objAttach, "PAGE_COUNT", strPageCount);
}

function setAttachImageWidth(objAttach, strWidth)
{
	setChildNodeData(objAttach, "IMAGE_WIDTH", strWidth);
}

function getAttachImageWidth(objAttach)
{
	return getChildNodeData(objAttach, "IMAGE_WIDTH");
}

function setAttachImageHeight(objAttach, strHeight)
{
	setChildNodeData(objAttach, "IMAGE_HEIGHT", strHeight);
}

function getAttachImageHeight(objAttach)
{
	return getChildNodeData(objAttach, "IMAGE_HEIGHT");
}

function getAttachLocation(objAttach)
{
	return getChildNodeData(objAttach, "LOCATION");
}

function setAttachLocation(objAttach, strLocation)
{
	setChildNodeData(objAttach, "LOCATION", strLocation);
}

function getAttachMethod(objAttach)
{
	return objAttach.getAttribute("METHOD");
}

function setAttachMethod(objAttach, strMethod)
{
	objAttach.setAttribute("METHOD", strMethod);
}

function getAttachModify(objAttach)
{
	return objAttach.getAttribute("MODIFY");
}

function setAttachModify(objAttach, strModify)
{
	objAttach.setAttribute("MODIFY", strModify);
}

// Use only RelatedAttach

function getAttachClassify(objAttach)
{
	return getChildNodeData(objAttach, "CLASSIFY");
}

function setAttachClassify(objAttach, strClassify)
{
	setChildNodeData(objAttach, "CLASSIFY", strClassify);
}

function getAttachSubDiv(objAttach)
{
	return getChildNodeData(objAttach, "SUBDIV");
}

function setAttachSubDiv(objAttach, strSubDiv)
{
	setChildNodeData(objAttach, "SUBDIV", strSubDiv);
}

// Current User Input Opinion
function setOpinion(strOpinion)
{
	g_strInputOpinion = strOpinion;

	var strFlowStatus = getFlowStatus();
	if (strFlowStatus == "0" || strFlowStatus == "14")
	{
		var objApprover = getActiveApprover();
		if (objApprover != null)
		{
			setApproverOpinion(objApprover, strOpinion);

			if (getApproverRole(objApprover) == "21")
				setXmlDataToForm();
		}

		setInfoChange(true);
	}
	else if (strFlowStatus == "4" || strFlowStatus == "6" ||strFlowStatus == "15")
	{
		var strDelivererType = "";

		if (strFlowStatus == "4")
			strDelivererType = "0";
		else if (strFlowStatus == "6" || strFlowStatus == "15")
			strDelivererType = "5";

		var objDeliverer = getDelivererByDelivererType("0");
		if (objDeliverer != null)
			setDelivererOpinion(objDeliverer, strOpinion);
	}
}

function getOpinion()
{
	if (g_strInputOpinion == "")
	{
		var strFlowStatus = getFlowStatus();
		if (strFlowStatus == "0" || strFlowStatus == "14")
		{
			var objApprover = getActiveApprover();
			if (objApprover != null)
				g_strInputOpinion = getApproverOpinion(objApprover);
		}
		else if (strFlowStatus == "4" || strFlowStatus == "6" ||strFlowStatus == "15")
		{
			var strDelivererType = "";

			if (strFlowStatus == "4")
				strDelivererType = "0";
			else if (strFlowStatus == "6" || strFlowStatus == "15")
				strDelivererType = "5";

			var objDeliverer = getDelivererByDelivererType("0");
			if (objDeliverer != null)
				g_strInputOpinion = getDelivererOpinion(objDeliverer);
		}
	}

	return g_strInputOpinion;
}

function setInfoChange(bInfoChange)
{
	g_bInfoChange = bInfoChange;
}

function getInfoChange()
{
	return g_bInfoChange;
}

// Class Info
function getClassInfo()
{
	var strClassName = getOrgSymbol();										// ORG_SYMBOL
	var strNumber = getClassNumber();										// CLASS_NUMBER
	var strSerialNumber = getSerialNumber();								// SERIAL_NUMBER
	var strDocNo = strClassName + strNumber;
	if ((strSerialNumber != "") && (strSerialNumber != "-1") && (strSerialNumber != "0"))
		strDocNo = strDocNo + "-" + strSerialNumber;
	return strDocNo;
}

function setClassInfo(strDocNumber)
{
	var strNumber = "";
	var strClassName = "";
	var strSerialNumber = "";
	var nNumberType = 0;

	var nLength = strDocNumber.length;
	var i = 0;
	while (i < nLength)
	{
		var ch = strDocNumber.charAt(i);
		if (ch == "-")
		{
			nNumberType = 2;
		}
		else if (ch >= "0" && ch <= "9")
		{
			if (nNumberType == 0)
				nNumberType = 1;
		}
		else
		{
			if (nNumberType == 1)
			{
				strClassName += strNumber;
				strNumber = "";
				nNumberType = 0;
			}
		}

		if (ch != "-")
		{
			if (nNumberType == 0)
				strClassName += ch.toString();
			else if (nNumberType == 1)
				strNumber += ch.toString();
			else if (nNumberType == 2)
				strSerialNumber += ch.toString();
		}

		i++;
	}

	if (strClassName.length > 16)
			strClassName = strClassName.substring(0, 16);

	if (strNumber.length > 32)
		strNumber = strNumber.substring(0, 32);

	setOrgSymbol(strClassName);
	setClassNumber(strNumber);
	setSerialNumber(strSerialNumber);
	setXmlDataToForm();
}

// Body Count - 1 base

function getBodyCount()
{
	var objNodeList = ApprovalDoc.selectNodes("//DRAFT_INFOS/DRAFT_INFO");
	return objNodeList.length;
}

function increaseBodyCount()
{
	var nBodyCount = getBodyCount();
	nBodyCount++;

	addDraftInfos(nBodyCount);
	if (nBodyCount > 1)
		setIsBatch("Y");
}

function decreaseBodyCount(nCaseNumber)
{
	if (nCaseNumber == 1)
		return;

	var nBodyCount = getBodyCount();
	if (nBodyCount > 1)
	{
		removeDraftInfos(nCaseNumber);
		removeRecipGroup(nCaseNumber);
		removeGeneralAttachesByCaseNumber(nCaseNumber);

		if (nCaseNumber < nBodyCount)
		{
			var nIndex = nCaseNumber + 1;
			while(nIndex <= nBodyCount)
			{
				var objDraftInfo = getDraftInfo(nIndex);
				setChildNodeData(objDraftInfo, "CASE_NUMBER", "" + (nIndex - 1));

				var objReciptGroup = getRecipGroup(nIndex);
				if (objReciptGroup != null)
					setChildNodeData(objReciptGroup, "CASE_NUMBER", "" + (nIndex - 1));

				var objGeneralAttachList = getGeneralAttachListByCaseNumber(nIndex);
				for (var i = 0; i < objGeneralAttachList.length; i++)
					setChildNodeData(objGeneralAttachList.item(i), "CASE_NUMBER", "" + (nIndex - 1));

				nIndex++;
			}
		}

		checkRegistryType();
	}
}

function getEditType()
{
	return g_strEditType;
}

// LEGACY_SYSTEM method
function getLegacySystem()
{
	return ApprovalDoc.selectSingleNode("//LEGACY_SYSTEMS/LEGACY_SYSTEM");
}

function getLegacySystemID(objLegacySystem)
{
	var objSystemID = objLegacySystem.selectSingleNode("SYSTEM_ID");
	if (objSystemID != null)
		return objSystemID.text;
	return null;
}

function getBusinessInfo(objLegacySystem)
{
	return objLegacySystem.selectSingleNode("//BUSINESS_INFO");
}

function getBusinessID(objBusinessInfo)
{
	var objBusinessID = objBusinessInfo.selectSingleNode("BUSINESS_ID");
	if (objBusinessID != null)
		return objBusinessID.text;
	return null;
}

function getBusinessAuxInfoElement(objBusinessInfo)
{
	return objBusinessInfo.selectSingleNode("AUX_INFO");
}

function getBusinessAuxInfoText(objBusinessInfo)
{
	var objBusinessAuxInfo = objBusinessInfo.selectSingleNode("AUX_INFO");
	if (objBusinessAuxInfo != null)
	{
		if (objBusinessAuxInfo.childNodes.length == 0)
			return "";
		else if (objBusinessAuxInfo.childNodes.length == 1)
		{
			if (objBusinessAuxInfo.childNodes.item(0).nodeType == 1)
			{
				var strText = objBusinessAuxInfo.xml;
				strText = strText.substring(strText.indexOf(">") + 1, strText.lastIndexOf("<"));
				return strText;
			}
			else
				return objBusinessAuxInfo.text;
		}
		else
		{
			var strText = objBusinessAuxInfo.xml;
			strText = strText.substring(strText.indexOf(">") + 1, strText.lastIndexOf("<"));
			return strText;
		}
	}
	return null;
}

function getLegacyDataElement(objBusinessInfo)
{
	return objBusinessInfo.selectSingleNode("LEGACY_DATA");
}

function getLegacyDataText(objBusinessInfo)
{
	var objLegacyData = objBusinessInfo.selectSingleNode("LEGACY_DATA");
	if (objLegacyData != null)
	{
		if (objLegacyData.childNodes.length == 0)
			return null;
		else if (objLegacyData.childNodes.length == 1)
		{
			if (objLegacyData.childNodes.item(0).nodeType == 1)
			{
				var strText = objLegacyData.xml;
				strText = strText.substring(strText.indexOf(">") + 1, strText.lastIndexOf("<"));
				return strText;
			}
			else
				return objLegacyData.text;
		}
		else
		{
			var strText = objLegacyData.xml;
			strText = strText.substring(strText.indexOf(">") + 1, strText.lastIndexOf("<"));
			return strText;
		}
	}
	return null;
}

// INPUT_CONFIG
function getInputConfigFormVersion()
{
	if (typeof(InputConfig) == "undefined")
		return null;
	var objFormVersion = InputConfig.selectSingleNode("//FORM_VERSION");
	if (objFormVersion != null)
		return objFormVersion.text;
	return null;
}

function getInputConfigLegacyFile()
{
	if (typeof(InputConfig) == "undefined")
		return null;
	var objLegacyFile = InputConfig.selectSingleNode("//LEGACY_FILE");
	if (objLegacyFile != null)
		return objLegacyFile.text;
	return null;
}

function getInputConfigInputMethod()
{
	if (typeof(InputConfig) == "undefined")
		return null;
	var objInputMethod = InputConfig.selectSingleNode("//INPUT_METHOD");
	if (objInputMethod != null)
		return objInputMethod.text;
	return null;
}

function getInputConfigInputFormat()
{
	if (typeof(InputConfig) == "undefined")
		return null;
	var objInputFormat = InputConfig.selectSingleNode("//INPUT_FORMAT");
	if (objInputFormat != null)
		return objInputFormat.text;
	return null;
}

function getInputConfigLegacyKeys()
{
	if (typeof(InputConfig) == "undefined")
		return null;
	var objLegacyKeyList = InputConfig.selectNodes("//LEGACY_KEY");
	if (objLegacyKeyList.length > 0)
	{
		var arrLegacyKey = new Array();
		for (var i = 0; i < objLegacyKeyList.length; i++)
		{
			arrLegacyKey[i][0] = objLegacyKeyList.item(i).getAttribute("SOURCE");
			arrLegacyKey[i][1] = objLegacyKeyList.item(i).text;
		}
	}
	return null;
}

// OUTPUT_CONFIG
function getOutputConfigApprovalEvent()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objApprovalEvent = OutputConfig.selectSingleNode("//APPROVAL_EVENT");
	if (objApprovalEvent != null)
		return objApprovalEvent.text;
	return null;
}

function getOutputConfigOutputType()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objOutputType = OutputConfig.selectSingleNode("//OUTPUT_TYPE");
	if (objOutputType != null)
		return objOutputType.text;
	return null;
}

function getOutputConfigOutputMethod()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objOutputMethod = OutputConfig.selectSingleNode("//OUTPUT_METHOD");
	if (objOutputMethod != null)
		return objOutputMethod.text;
	return null;
}

function getOutputConfigOutputFormat()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objOutputFormat = OutputConfig.selectSingleNode("//OUTPUT_FORMAT");
	if (objOutputFormat != null)
		return objOutputFormat.text;
	return null;
}

function getOutputConfigOutputURL()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objOutputURL = OutputConfig.selectSingleNode("//OUTPUT_URL");
	if (objOutputURL != null)
		return objOutputURL.text;
	return null;
}

function getOutputConfigAuxInfo()
{
	if (typeof(OutputConfig) == "undefined")
		return null;
	var objAuxInfo = OutputConfig.selectSingleNode("//AUX_INFO");
	if (objAuxInfo != null)
		return objAuxInfo.text;
	return null;
}

/*
// 해경
// SERVERS
function getServers()
{
	return ApprovalDoc.selectSingleNode("//SERVERS");
}

function isMultiServer()
{
	var objServers = getServers();
	if (objServers == null)
		return false;

	var objForeignServerList = objServers.selectNodes("//APPROVAL_SERVERS/FOREIGN_SERVER");
	if (objForeignServerList.length > 0)
		return true;

	return false;
}
*/

// etc
function getNode(objNode, strXPath)
{
	return objNode.selectSingleNode(strXPath);
}
