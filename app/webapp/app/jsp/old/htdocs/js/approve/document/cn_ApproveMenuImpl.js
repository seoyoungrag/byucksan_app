// icaha, document switching
var g_strDirection = "";
var g_objPubdocElement = null;
var g_objAdminElement = null;

var g_bAttachModifiedDoc = false;

function onOpenDocument()
{
	openFile();
}

function onAddDocument()
{
//	if (editFrame.isFormData() == false)
//	{
//		showAlert(20);
//		return;
//	}

	if (addDocument() == true)
	{
		;
	}
}

function onSaveFile()
{
	var bIncludeSign = false;	// 옵션사항

	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML != "")
	{
		var strIsBatch = getIsBatch();
		var nEnforceProposal = getEnforceProposal();

		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDownNPrint.jsp?batch=" + strIsBatch + "&proposal=" + nEnforceProposal + "&type=DOWN";
		window.open(strUrl, "Approve_Print", "toolbar=no, resizable=no, status=yes, width=200, height=150");
	}
	else
		saveApproveDocument(bIncludeSign);
}

function onPrint()
{
	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML != "")
	{
		var strIsBatch = getIsBatch();
		var nEnforceProposal = getEnforceProposal();

		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDownNPrint.jsp?batch=" + strIsBatch + "&proposal=" + nEnforceProposal + "&type=PRINT";
		window.open(strUrl, "Approve_Print", "toolbar=no, resizable=no, status=yes, width=200, height=150");
	}
	else
	{
		printDocument();
	}
}

function onModifyDocument()
{
	if (g_strEditType == "14")
		g_bAttachModifiedDoc = false;
	else
		g_bAttachModifiedDoc = true;

	changeMenu(1);
	editDocument(true);
}

function onSetOpinion()
{
	var strBodyType = getBodyType();
	var strFormUsage = getFormUsage();
	var strFlowStatus = getFlowStatus();

	if (getBodyType() == "gul" || getBodyType() == "txt" || getBodyType() == "html" || getFormUsage() == "0" || strFlowStatus != "0" || parseInt(g_strEditType) >= 18)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=W&bodytype="+getBodyType();
		window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
	}
	else
	{
// 20031008 동두천 시청
		if (getBodyType() == "hwp" || getBodyType() == "han" || getBodyType() == "doc")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=W&bodytype="+getBodyType();
			window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
		}
		else
		{
			if (getBodyFileObj() == null)
			{
				setBodyFileInfo();
				saveBodyFile();
			}

			g_bAttachModifiedDoc = false;

			changeMenu(1);
			editDocument(true);
		}
	}
}

function onApplyModify()
{
	if (getBodyType() == "han" || getBodyType() == "hwp")
	{
		if (g_strSubject != getFieldText(FIELD_TITLE))
		{
//			if (confirm(ALERT_CAN_NOT_MODIFY_TITLE))
//				putFieldText(FIELD_TITLE, g_strSubject);
//			else
//				return;
		}
	}

	if (g_bAttachModifiedDoc == true)
	{
		g_bAttachModifiedDoc == false;

		var objExtendAttach = findExtendAttachInfo("ModifiedDoc", g_strLineName, g_strSerialOrder);
		if (objExtendAttach == null)
		{
			var objFileBody = getBodyFileObj();

			var strDisplayName = "ModifiedDoc";
			var strFileName = getAttachFileName(objFileBody);
			var strFileSize = getAttachFileSize(objFileBody);
			var strLocation = getAttachLocation(objFileBody);

			objExtendAttach = addExtendAttachInfo("0", strDisplayName, strFileName, strFileSize, strLocation);

			setChildNodeData(objExtendAttach, "CLASSIFY", "ModifiedDoc");
			setChildNodeData(objExtendAttach, "CHARGER_LINE_NAME", g_strLineName);
			setChildNodeData(objExtendAttach, "CHARGER_SERIAL_ORDER", g_strSerialOrder);
			setAttachAttribute(objExtendAttach, "METHOD", "");

			strFileName = getGUID() + "." + getFileExtension();
			setAttachFileName(objFileBody, strFileName);
			setAttachFileSize(objFileBody, getLocalFileSize(g_strDownloadPath + strFileName));
			setAttachLocation(objFileBody, "");
			setAttachAttribute(objFileBody, "METHOD", "add");

			var objApprovalLine = getApprovalLineByLineName(g_strLineName);
			var objApprover = getApproverBySerialOrder(objApprovalLine, g_strSerialOrder);
			setApproverIsDocModified(objApprover, "Y");
		}
		else
		{
			var objFileBody = getBodyFileObj();
			var strFileName = getAttachFileName(objFileBody);
			setAttachFileSize(objFileBody, getLocalFileSize(g_strDownloadPath + strFileName));
		}

		setFormDataToXml();
		setInfoChange(true);
	}
	else
	{
		setOpinion("의견(첨부)");

		var objFileBody = getBodyFileObj();
		var strFileName = getAttachFileName(objFileBody);
		setAttachFileSize(objFileBody, getLocalFileSize(g_strDownloadPath + strFileName));
	}

	saveBodyFile();
	setModified(true);

	changeMenu(0);

	if (getBodyType() == "html")
		uploadBodyFile();

	if (g_bAttachModifiedDoc == false && g_strEditType == "0")
		editDocument(true);
	else
		editDocument(false);
}

function applyModifiedAttach(strOriginFileName, strNewFileName)
{
/*
	if (strOriginFileName == strNewFileName)
	{
		alert("이름이 같아서 오류");
		return;
	}
*/
	var objExtendAttach = findModifiedOriginAttach(strOriginFileName, g_strLineName, g_strSerialOrder);
	if (objExtendAttach == null && strNewFileName != "")
	{
		var objGeneralAttach = findGeneralAttachInfo(strOriginFileName);
		if (objGeneralAttach == null)
			objGeneralAttach = findRelatedAttachInfoByFileName(strOriginFileName);

		var strMethod = getAttachMethod(objGeneralAttach);
		if (strMethod == "add")
		{
			setAttachFileName(objGeneralAttach, strNewFileName);
			setAttachFileSize(objGeneralAttach, getLocalFileSize(g_strDownloadPath + strNewFileName));
		}
		else
		{
			var strCaseNumber = getAttachCaseNumber(objGeneralAttach);
			var strDisplayName = getAttachDisplayName(objGeneralAttach);
			var strFileName = getAttachFileName(objGeneralAttach);
			var strFileSize = getAttachFileSize(objGeneralAttach);
			var strLocation = getAttachLocation(objGeneralAttach);
	
			var nFind = strNewFileName.indexOf(".");
			if (nFind > 32)
				nFind = 32;
			var strSubDiv = strNewFileName.substring(0, nFind);
	
			objExtendAttach = addExtendAttachInfo(strCaseNumber, strDisplayName, strFileName, strFileSize, strLocation);
	
			setChildNodeData(objExtendAttach, "CLASSIFY", "ModifiedAttach");
			setChildNodeData(objExtendAttach, "SUBDIV", strSubDiv);
			setChildNodeData(objExtendAttach, "CHARGER_LINE_NAME", g_strLineName);
			setChildNodeData(objExtendAttach, "CHARGER_SERIAL_ORDER", g_strSerialOrder);
			setAttachAttribute(objExtendAttach, "METHOD", "");
	
			setAttachFileName(objGeneralAttach, strNewFileName);
			setAttachFileSize(objGeneralAttach, getLocalFileSize(g_strDownloadPath + strNewFileName));
			setAttachLocation(objGeneralAttach, "");
			setAttachAttribute(objGeneralAttach, "METHOD", "add");
	
			var objApprovalLine = getApprovalLineByLineName(g_strLineName);
			if (objApprovalLine != null)
			{
				var objApprover = getApproverBySerialOrder(objApprovalLine, g_strSerialOrder);
				if (objApprover != null)
					setApproverIsAttachModified(objApprover, "Y");
			}
		}
	}
	else
	{
		var objGeneralAttach = findGeneralAttachInfo(strOriginFileName);
		if (objGeneralAttach == null)
			objGeneralAttach = findRelatedAttachInfoByFileName(strOriginFileName);

		setAttachFileSize(objGeneralAttach, getLocalFileSize(g_strDownloadPath + strOriginFileName));

		if (getAttachMethod(objGeneralAttach) == "")
			setAttachMethod(objGeneralAttach, "replace");
	}

	setInfoChange(true);
}

function applyMisModifiedAttach(strFileName)
{
	var objGeneralAttach = findGeneralAttachInfo(strFileName);
	if (objGeneralAttach == null)
		objGeneralAttach = findRelatedAttachInfoByFileName(strFileName);

	if (objGeneralAttach == null)
	{
		alert("Not Found");
		return;
	}
/// 상우대리수정
	if (getAttachMethod(objGeneralAttach) == "")
		setAttachMethod(objGeneralAttach, "replace");
///
	setAttachModify(objGeneralAttach, "1");

	var objApprovalLine = getApprovalLineByLineName(g_strLineName);
	if (objApprovalLine != null)
	{
		var objApprover = getApproverBySerialOrder(objApprovalLine, g_strSerialOrder);
		if (objApprover != null)
			setApproverIsAttachModified(objApprover, "Y");
	}

	setInfoChange(true);
}

function onCancelModify()
{
	changeMenu(0);

	if (g_bAttachModifiedDoc == false && g_strEditType == "0")
		editDocument(true);
	else
		editDocument(false);
}

function applyModifiedLine()
{
	var objApprovalLine = getActiveApprovalLine();
	if (objApprovalLine == null)
		return;

	var objApprover = getActiveApprover();
	if (objApprover == null)
		return;

	if (getApproverIsLineModified(objApprover) == "Y")
		return;

	var objApprovalLines = ApprovalDoc.selectSingleNode("//APPROVAL_LINES");
	var strApprovalLines = "<?xml version='1.0' encoding='euc-kr'?><APPROVAL_DOC>" + objApprovalLines.xml + "</APPROVAL_DOC>";

	var strFileName = getGUID() + ".xml";

	if (saveToLocalFile(g_strDownloadPath + strFileName, strApprovalLines) == false)
		return;

	if (uploadFile(g_strDownloadPath + strFileName, g_strUploadUrl + strFileName) == false)
		return;

	var objExtendAttach = findExtendAttachInfo("ModifiedFlow", getLineName(objApprovalLine), getApproverSerialOrder(objApprover));
	if (objExtendAttach != null)
		objExtendAttach.parentNode.removeChild(objExtendAttach);

	objExtendAttach = addExtendAttachInfo("", "ModifiedFlow", strFileName, getLocalFileSize(g_strDownloadPath + strFileName), "");

	setChildNodeData(objExtendAttach, "CLASSIFY", "ModifiedFlow");
	setChildNodeData(objExtendAttach, "CHARGER_LINE_NAME", g_strLineName);
	setChildNodeData(objExtendAttach, "CHARGER_SERIAL_ORDER", g_strSerialOrder);

	setInfoChange(true);
}

function onModifyEnforce()
{
	changeMenu(3);

	if (g_strIsDirect == "N")
	{
		editEnforceDocument(true);
		setFrameScale(1);
	}
	else
		editDocument(true);

	setInfoChange(true);
}

function onApplyEnforceModify()
{
	if (g_strIsDirect == "N")
	{
		var nCaseNumber = getEnforceProposal();
		saveEnforceFile(nCaseNumber)
		setEnforceModified(true);

		changeMenu(1);
		editEnforceDocument(false);
		setFrameScale(2);
	}
	else
	{
		saveBodyFile()
		setModified(true);

		changeMenu(0);
		editDocument(false);
	}

	setInfoChange(true);
}

function onCancelEnforceModify()
{
	if (g_strIsDirect == "N")
	{
		changeMenu(1);
		editEnforceDocument(false);
		setFrameScale(2);
	}
	else
	{
		changeMenu(0);
		editDocument(false);
	}
}

function onApplyRejectEnforceModify()
{
	if (g_strIsDirect == "N")
	{
		var nCaseNumber = getEnforceProposal();
		saveEnforceFile(nCaseNumber)
		setEnforceModified(true);

		changeMenu(1);
		editEnforceDocument(false);
		setFrameScale(2);
	}
	else
	{
		saveBodyFile()
		setModified(true);

		changeMenu(0);
		editDocument(false);
	}

	setInfoChange(true);
}

function onCancelRejectEnforceModify()
{
	if (g_strIsDirect == "N")
	{
		changeMenu(1);
		editEnforceDocument(false);
		setFrameScale(2);
	}
	else
	{
		changeMenu(0);
		editDocument(false);
	}
}

function onViewBodyDoc()
{
	if (g_strEditType == "5" || g_strEditType == "13")
		changeMenu(1);
	else
		changeMenu(0);
	setFrameScale(0);

//	if (getBodyType() == "gul")
		onSetViewScale(true);

	if (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13")
		editDocument(true);
		
	if (g_strBodyType == "doc")
	{
		unprotectSection();
	}
}

function onViewEnforceDoc()
{
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
		return;

	if (getEnforceFileObj(nProposal) == null)
	{
		alert("시행문이 없거나 시행변환 전의 문서입니다.");
		return;
	}

	var strFileName = getAttachFileName(getEnforceFileObj(nProposal));
	var strServerUrl = g_strUploadUrl + strFileName;
	var strLocalPath = g_strDownloadPath + strFileName;

	if (getLocalFileSize(strLocalPath) == 0)
	{
		if (downloadFile(strServerUrl, strLocalPath) == 0)
			return false;
	}

	createEnforceSide();

	changeMenu(1);

	setFrameScale(2);

	onLoadEnforceDocument();
}

function onOpenDraftForm(strFormUrl, strDocType, strFormName, strEnforceFormID, bIsLocal)
{
	if (strFormUrl == "")
		return false;

	if (bIsLocal == true)
	{
		// local file already exists in temporary download folder
	}
	else
	{
		if (downloadFile(g_strUploadUrl + strFormUrl, g_strDownloadPath + strFormUrl) == false)
		{
			alert("Download Error");
			return false;
		}
	}

	changeFormUrl(strFormUrl);

	return true;
}

// icaha 20030117
function onOpenEnforceForm(strFormUrl, strDocType, strFormName, strLogoName, strSymbolName, strEnforceFormID, bIsLocal)
{
	if (strFormUrl == "")
		return false;
//alert(g_strUploadUrl + strFormUrl+ " : "+  g_strDownloadPath + strFormUrl);

// icaha 20030117
	if (bIsLocal == true)
	{
		// local file already exists in temporary download folder
	}
	else
	{
		if (getLocalFileSize(g_strDownloadPath + strFormUrl) == 0)
		{
			if (downloadFile(g_strUploadUrl + strFormUrl, g_strDownloadPath + strFormUrl) == false)
			{
				alert("Download Error");
				return false;
			}
		}
		if (strLogoName != "")
			downloadFile(g_strUploadUrl + strLogoName, g_strDownloadPath + strLogoName);
		if (strSymbolName != "")
			downloadFile(g_strUploadUrl + strSymbolName, g_strDownloadPath + strSymbolName);
	}

	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML == "")
	{
		createEnforceSide();

		// icaha, 20030825
		if (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "16")
			changeMenu(4);
		else if (g_strEditType == "13")
		{
			if (g_bPreviewConvert == true)
				changeMenu(4);
		}
		else
			changeMenu(1);
		setFrameScale(2);
		g_strEnforceFormUrl = strFormUrl;
		g_strEnforceFormName = strFormName;
		if (getBodyType() == "han")
		{
			g_strLogoName = strLogoName;
			g_strSymbolName = strSymbolName;
		}
		onLoadEnforceDocument();
	}
	else
	{
		// icaha, 20030825
		if (g_strEditType == "0" || g_strEditType == "5")
			changeMenu(4);
		else if (g_strEditType == "13")
		{
			if (g_bPreviewConvert == true)
				changeMenu(4);
		}
		else
			changeMenu(1);
		setFrameScale(2);
		g_strEnforceFormUrl = strFormUrl;
		g_strEnforceFormName = strFormName;
		var objEnforceFile = getEnforceFileObj(g_nEnforceProposal);
		if (objEnforceFile != null)
			objEnforceFile.parentNode.removeChild(objEnforceFile);

		if (getBodyType() == "han")
		{
			g_strLogoName = strLogoName;
			g_strSymbolName = strSymbolName;
		}
		loadEnforceDocument();
	}

	return true;
}

function changeMenu(nType)
{
	var eleDefaultMenu = document.getElementById("defaultMenu");
	var eleExtendMenu = document.getElementById("extendMenu");

	var eleDefaultSubMenu = document.getElementById("defaultSubMenu");
	var eleExtendSubMenu = document.getElementById("extendSubMenu");

	var eleExtraSubMenu = document.getElementById("extraSubMenu");

	if (getBodyType() == "hwp")
	{
		var eleHwpMenuSet = document.getElementById("hwpMenuSet");
		if (nType == 0)
		{
			if (eleDefaultMenu != null)
				eleHwpMenuSet.value = eleDefaultMenu.value;
		}
		else if (nType == 1)
		{
			if (eleExtendMenu != null)
				eleHwpMenuSet.value = eleExtendMenu.value;
		}
		else if (nType == 2)
		{
			if (eleDefaultSubMenu != null)
				eleHwpMenuSet.value = eleDefaultSubMenu.value;
		}
		else if (nType == 3)
		{
			if (eleExtendSubMenu != null)
				eleHwpMenuSet.value = eleExtendSubMenu.value;
		}
		else if (nType == 4)
		{
			if (eleExtraSubMenu != null)
				eleHwpMenuSet.value = eleExtraSubMenu.value;
		}
		if (g_bHwpDocumentOpen == true)
			changeHwpDocumentMenu();
		if (typeof(g_bHwpEnforceOpen) != "undefined" && g_bHwpEnforceOpen == true)
			changeHwpEnforceMenu();
	}
	else
	{
		if (nType == 0)
		{
			if (eleDefaultMenu != null)
				eleDefaultMenu.style.display = "block";
			if (eleExtendMenu != null)
				eleExtendMenu.style.display = "none";

			if (eleDefaultSubMenu != null)
				eleDefaultSubMenu.style.display = "none";
			if (eleExtendSubMenu != null)
				eleExtendSubMenu.style.display = "none";

			if (eleExtraSubMenu != null)
				eleExtraSubMenu.style.display = "none";
		}
		else if (nType == 1)
		{
			if (eleDefaultMenu != null)
				eleDefaultMenu.style.display = "none";
			if (eleExtendMenu != null)
				eleExtendMenu.style.display = "block";

			if (eleDefaultSubMenu != null)
				eleDefaultSubMenu.style.display = "none";
			if (eleExtendSubMenu != null)
				eleExtendSubMenu.style.display = "none";

			if (eleExtraSubMenu != null)
				eleExtraSubMenu.style.display = "none";
		}
		else if (nType == 2)
		{
			if (eleDefaultMenu != null)
				eleDefaultMenu.style.display = "none";
			if (eleExtendMenu != null)
				eleExtendMenu.style.display = "none";

			if (eleDefaultSubMenu != null)
				eleDefaultSubMenu.style.display = "block";
			if (eleExtendSubMenu != null)
				eleExtendSubMenu.style.display = "none";

			if (eleExtraSubMenu != null)
				eleExtraSubMenu.style.display = "none";
		}
		else if (nType == 3)
		{
			if (eleDefaultMenu != null)
				eleDefaultMenu.style.display = "none";
			if (eleExtendMenu != null)
				eleExtendMenu.style.display = "none";

			if (eleDefaultSubMenu != null)
				eleDefaultSubMenu.style.display = "none";
			if (eleExtendSubMenu != null)
				eleExtendSubMenu.style.display = "block";

			if (eleExtraSubMenu != null)
				eleExtraSubMenu.style.display = "none";
		}
		else if (nType == 4)
		{
			if (eleDefaultMenu != null)
				eleDefaultMenu.style.display = "none";
			if (eleExtendMenu != null)
				eleExtendMenu.style.display = "none";

			if (eleDefaultSubMenu != null)
				eleDefaultSubMenu.style.display = "none";
			if (eleExtendSubMenu != null)
				eleExtendSubMenu.style.display = "none";

			if (eleExtraSubMenu != null)
				eleExtraSubMenu.style.display = "block";
		}
	}
}

function onClickStamp(nStampType)
{
	var strTitle = "";
	var nTreeType = 0;
	var nSignType = 0;

	if (nStampType == 0 || nStampType == 2)
	{
		nTreeType = 0;
		nSignType = 0;
	}
	else if (nStampType == 1)
	{
		nTreeType = 3;
		nSignType = 1;
	}
	else
	{
		nTreeType = 1;
		nSignType = 1;
	}

	if (nStampType == 0 || nStampType == 1)
	{
		var strUrl = g_strBaseUrl + "signature/dialog/CN_SignatureSelect.jsp?deptid=" + g_strApproverDeptCode + "&treetype=" + nTreeType + "&signtype=" + nSignType;
		window.open(strUrl, "Org_SetDept", "toolbar=no, resizable=no, status=yes, width=500, height=350");
	}
	else
	{
		dataTransform.location.href = g_strBaseUrl + "signature/dialog/CN_SignatureGetOmitImage.jsp?signtype=" + nStampType;
	}
}

function setEnforceStamp(nStampType, strFileName, strWidth, strHeight)
{
	if (strFileName == "" || downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == false)
	{
		if (nStampType == 0 || nStampType == 1)
		{
//			alert("파일 전송 오류");
			return;
		}
	}

	if (typeof(strWidth) == "undefined")
		strWidth = "30";
	if (typeof(strHeight) == "undefined")
		strHeight = "30";

	var strSubDiv;
	if (nStampType == 0)
		strSubDiv = "DeptStamp";
	else if (nStampType == 1)
		strSubDiv = "CompanyStamp";
	else if (nStampType == 2)
		strSubDiv = "DeptOmitStamp";
	else if (nStampType == 3)
		strSubDiv = "CompanyOmitStamp";

	var nCaseNumber;
	var strEnforceBound;
	var strMethod = "";
	var strFileSize = "0";
	if (strFileName != "")
		getLocalFileSize(g_strDownloadPath + strFileName);

	var objStampAttach = null;
	if (g_strIsDirect == "Y")
	{
		strEnforceBound = getEnforceBound(1);
		objStampAttach = findRelatedSealInfo("DocStamp", strSubDiv, strMethod);
		if (objStampAttach == null)
		{
			strMethod = "replace";
			objStampAttach = findRelatedSealInfo("DocStamp", strSubDiv, strMethod);
		}
		if (objStampAttach == null)
		{
			strMethod = "add";
			objStampAttach = findRelatedSealInfo("DocStamp", strSubDiv, strMethod);
		}
	}
	else
	{
		nCaseNumber = getEnforceProposal();
		strEnforceBound = getEnforceBound(nCaseNumber);
		objStampAttach = findExtendedSealInfo(nCaseNumber, "EnforceRelated", strSubDiv, strMethod);
		if (objStampAttach == null)
		{
			strMethod = "replace";
			objStampAttach = findExtendedSealInfo(nCaseNumber, "EnforceRelated", strSubDiv, strMethod);
		}
		if (objStampAttach == null)
		{
			strMethod = "add";
			objStampAttach = findExtendedSealInfo(nCaseNumber, "EnforceRelated", strSubDiv, strMethod);
		}
	}

	if (objStampAttach != null)
	{
		var strStampMethod = getAttachMethod(objStampAttach);
		if (strStampMethod == "")
			setAttachMethod(objStampAttach, "replace");
		setAttachDisplayName(objStampAttach, strFileName);
		setAttachFileName(objStampAttach, strFileName);
		setAttachFileSize(objStampAttach, strFileSize);

		if (g_strIsDirect == "Y")
			clearApprovalSeal();
		else
			clearEnforceApprovalSeal();
	}
	else
	{
		if (g_strIsDirect == "Y")
		{
			clearApprovalSeal();
			removeSealRelatedAttaches();
			objStampAttach = addRelatedAttachInfo(strFileName, strFileName, strFileSize, "");

			setAttachClassify(objStampAttach, "DocStamp");
			setAttachSubDiv(objStampAttach, strSubDiv);
		}
		else
		{
			clearEnforceApprovalSeal();
			removeSealExtendAttaches(nCaseNumber);
			objStampAttach = addExtendAttachInfo(nCaseNumber, strFileName, strFileName, strFileSize, "");

			setAttachClassify(objStampAttach, "EnforceRelated");
			setAttachSubDiv(objStampAttach, strSubDiv);
		}
	}

	setAttachImageWidth(objStampAttach, strWidth);
	setAttachImageHeight(objStampAttach, strHeight);

	if (g_strIsDirect == "Y")
		setApprovalSeal();
	else
		setEnforceApprovalSeal();
}

function onInvestSign(strTitle)
{
	alert("향후 개발예정입니다.");
}

function onProposalChanged(nCaseNumber)
{
	var strDocCategory = getDocCategory(nCaseNumber);
	if (strDocCategory == "I")
	{
		alert(ALERT_NONE_ENFORCE_DOCUMENT);
		return;
	}

	setEnforceProposal(nCaseNumber);
	loadEnforceDocument();
}

function onRetrySubmit()
{
	changeMenu(1);
	setFrameScale(0);

	if (g_strBodyType == "han" || g_strBodyType == "hwp6" || g_strBodyType == "doc")
		clearOpinion("");

	var strPrevFlowStatus = getFlowStatus();

	if (strPrevFlowStatus != "18")
	{
		clearApprovalSign();
		editDocument(true);
		setModified(true);
	}

	resetSubmitInfo();

	if (strPrevFlowStatus != "18")
	{
		setApprovalFlow();
	}

	if (g_strBodyType == "doc")
	{
		saveDocument();
		openRetrySubmit();
	}
}

function onRetrySubmitRejectEnforce()
{
	changeMenu(1);
	setFrameScale(0);
	clearApprovalSign();
	editDocument(true);
	setModified(true);
	resetSubmitInfo();
	setApprovalFlow();
}

function onModifySubmit()
{
	g_strEditType = "0";

	changeMenu(1);
	setFrameScale(0);

	if (g_strBodyType == "han" || g_strBodyType == "hwp6")
		clearOpinion("");

	var strFlowStatus = getFlowStatus();

	editDocument(true);
	clearApproveFlow();
	clearApprovalDocInfo();

	if (parseInt(strFlowStatus) > 7)
	{
		setRegistryType("T");
		setIsDirect("Y");
		g_strIsDirect = "Y";
		setDocCategory("E", 1);
		setEnforceBound("I", 1);
		setFormUsage("1");
		g_strFormUsage = "1";
	}

	var strSenderAs = "";
	var nBodyCount = getBodyCount();
	for (var i = 1 ; i <= nBodyCount ; i++)
	{
		strSenderAs += g_strSenderAs;
		if (i != nBodyCount)
			strSenderAs += "\u0002";

		setSenderAs(g_strSenderAs, i);
		setSenderAsCode(g_strApproverDeptCode, i);
	}

	restoreApproveFlow();
	setDrafterField();
	setXmlDataToForm();
	setSenderAsToForm(strSenderAs);
	setOrganImage();
	setModified(true);
}

function resetSubmitInfo()
{
	var strPrevFlowStatus = getFlowStatus();

	if (strPrevFlowStatus == "18")
		setFlowStatus("14");
	else
		setFlowStatus("0");

	var objApprovalLine = getActiveApprovalLine();

	if (getDocStatus(objApprovalLine) != "6")
	{
		setDocID("");
		setDocStatus(objApprovalLine, "0");
	}

	setCurrentSerial(objApprovalLine, "0");
	setCurrentParallel(objApprovalLine, "0");

	var objResetApprovalLine = getLastApprovalLine();
	while (objResetApprovalLine != null)
	{
		var objPrevApprovalLine = getPrevApprovalLine(objResetApprovalLine);

		if (getLineName(objResetApprovalLine) == getLineName(objApprovalLine))
		{
			var objApprover = getFirstApprover(objResetApprovalLine);
			while (objApprover != null)
			{
				setApproverIsSigned(objApprover, "");
				setApproverSignDate(objApprover, "");
				setApproverSignFileName(objApprover, "");

				setApproverIsOpen(objApprover, "N");
				setApproverAction(objApprover, "0");
				setApproverKeepStatus(objApprover, "0");

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

				objApprover = getNextApprover(objApprover);
			}
		}
		else
		{
			var strActiveLineName = getLineName(objApprovalLine);
			var strResetLineName = getLineName(objResetApprovalLine);
			if (strResetLineName.indexOf(strActiveLineName + "-") != -1)
			{
				removeApprovalLine(objResetApprovalLine);
			}
		}

		objResetApprovalLine = objPrevApprovalLine;
	}

	if (strPrevFlowStatus != "18")
	{
		var objDeliverers = getDeliverers();
		if (objDeliverers != null)
			removeAllChildNode(objDeliverers);

		if (getBodyType() != "html")
			clearFileBodyInfo();

		var objAttach = getFirstGeneralAttach();
		while(objAttach != null)
		{
			setAttachMethod(objAttach, "add");
			setAttachLocation(objAttach, "");
			objAttach = getNextGeneralAttach(objAttach);
		}

		clearRelatedAttachInfo();
		clearExtendAttachInfo();	// 왜 막아놨었는지 기억이 안나서 풀었음

		var objExtendAttach = getFirstExtendAttach();
		while (objExtendAttach != null)
		{
			var objNextExtendAttach = getNextExtendAttach(objExtendAttach);

			var strClassify = getAttachClassify(objExtendAttach)
			if (strClassify == "Synopsis")
				setAttachMethod(objExtendAttach, "replace");
			else
				objExtendAttach.parentNode.removeChild(objExtendAttach);

    		objExtendAttach = objNextExtendAttach;
		}
	}
}

function setRecipientsToForm()
{
	setDocumentRecipientsToForm();
	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML != "")
	{
		setEnforceRecipientsToForm();
	}
}

function appendRecipientsToForm(nCaseNumber)
{
	appendDocumentRecipientsToForm(nCaseNumber);
	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML != "")
	{
		appendEnforceRecipientsToForm(nCaseNumber);
	}
}

function updatePostSendField()
{
/*
	updateDocumentPostSendField();
	var eleEnforce = document.getElementById("enforceSide");
	if (eleEnforce.innerHTML != "")
	{
		updateEnforcePostSendField();

*/
}

function onViewModifiedDocument(strLineName, strSerialOrder)
{
	var objExtendedAttach = findExtendAttachInfo("ModifiedDoc", strLineName, strSerialOrder);
	if (objExtendedAttach == null)
	{
		alert(ALERT_NOTFOUND_PREMODIFIED_DOCUMENT);
		return;
	}

	var strFileName = getAttachFileName(objExtendedAttach);
	var strLocation = getAttachLocation(objExtendedAttach);

	var nFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
	if (nFileSize == 0)
		extractStoreFile(strFileName, strLocation);
	else
		openLocalFile(g_strDownloadPath + strFileName);
}

//Modified Attach
function onViewModifiedAttach(strLineName, strSerialOrder)
{
	var strClassify = "ModifiedAttach";
	var objExtendAttachList = findExtendAttachList(strClassify, strLineName, strSerialOrder);
    if (objExtendAttachList.length > 0)
    {
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveModifiedAttach.jsp?linename=" + strLineName + "&serialorder=" + strSerialOrder;
		window.open(strUrl,"Approve_Attach","toolbar=no,resizable=no, status=yes, width=420,height=360");
	}
	else
	{
		alert(ALERT_NOTFOUND_PREMODIFIED_ATTACH);
	}
}

//Modified Line
function onViewModifiedLine(strLineName, strSerialOrder)
{
	var objExtendedAttach = findExtendAttachInfo("ModifiedFlow", strLineName, strSerialOrder);
	if (objExtendedAttach != null)
	{
		var strFileName = getAttachFileName(objExtendedAttach);
		var strLocation = getAttachLocation(objExtendedAttach);
		getStoreFile(strFileName, strLocation);
	}
	else
	{
		alert(ALERT_NOTFOUND_PREMODIFIED_LINE);
	}
}

function doViewModifiedLine(strFileName)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveModifiedLine.jsp?filename=" + strFileName;
	window.open(strUrl,"Approve_ModifiedLine","toolbar=no,resizable=no, status=yes, width=500,height=300");
}

//Public Level
function setPLValue(strValue)
{
	setPublicLevel(strValue);
	setXmlDataToForm();
}

function getPublication(strCode)
{
	var strReturn = "";

	if (g_strOption148 == "0")
		strReturn = strCode;
	else
	{
		var strSubString = strCode.substr(0, 1);
		var strPublic = "";
		if (strSubString == "1")
		{
			strReturn = "공개";
		}
		else if (strSubString == "2")
		{
			strReturn = "부분공개";
		}
		if (strSubString == "3")
		{
			strReturn = "비공개";
		}

		if (strSubString != "1")
		{
			var strLevel = "";
			for (var iLoop = 1; iLoop < strCode.length; ++iLoop)
			{
				strSubString = strCode.substr(iLoop, 1);
				if (strSubString.toUpperCase() == "Y")
				{
					var strTempLevel = iLoop;
					if (strLevel != "")
					{
						strTempLevel = ", " + strTempLevel
					}
					strLevel = strLevel + strTempLevel
				}
			}
			if (strLevel != "")
			{
				strReturn = strReturn + "(" + strLevel + ")";
			}
		}
	}

	return strReturn;
}

function setReceiverDisplayName()
{
	var nBodyCount = getBodyCount();
	var objRecipientGroup = "";
	var objRecipient = "";
	var nRecipientCount = 0;
	var strRecipientDeptChief = "";
	var strRecipientDeptName = "";
	var strReturn = "";

	for (var i = 1; i <= nBodyCount; ++i)
	{
		objRecipientGroup = getRecipGroup(i);
		if (objRecipientGroup != null)
		{
			objRecipient = getFirstRecipient(objRecipientGroup);
			while (objRecipient != null)
			{
				var strDeptChief = getRecipientDeptChief(objRecipient);
				var strDeptName = getRecipientDeptName(objRecipient);
				if (strRecipientDeptChief == "" && strDeptChief != "")
					strRecipientDeptChief = strDeptChief;
				if (strRecipientDeptName == "" && strDeptName != "")
					strRecipientDeptName = strDeptName;

				nRecipientCount++;
				objRecipient = getNextRecipient(objRecipient);
			}
		}
	}
	if (nRecipientCount == 1)
	{
		if (strRecipientDeptChief != "")
			strReturn = strRecipientDeptChief;
		else
			strReturn = strRecipientDeptName;
	}
	else if (nRecipientCount > 1)
	{
		if (strRecipientDeptChief != "")
			strReturn = strRecipientDeptChief + "외 " + (nRecipientCount - 1);
		else
			strReturn = strRecipientDeptName + "외 " + (nRecipientCount - 1);
	}
	else
		strReturn = "";

	setReceiver(strReturn);
}

function setWrkValue(clpid, clpname, savid, savname)
{
	var strFlowStatus = getFlowStatus();
	if (strFlowStatus < 8)
	{
		setDraftArchiveID(clpid);
		setDraftArchiveName(clpname);
		setDraftConserveCode(savid);
		setDraftConserve(savname);
//		setXmlDataToForm();
	}
	else
	{
		setEnforceArchiveID(clpid);
		setEnforceArchiveName(clpname);
		setEnforceConserveCode(savid);
		setEnforceConserve(savname);
//		setXmlDataToForm();
	}
}

//Post
function onSendPost()
{
	getStoreFileAll("onSendPostCompleted");
}

function onSendPostCompleted(strReturn)
{
	var strFolderType = "";
	var strFolderUrl = "";
	var strSubmitFlag = "";

	var strTitle = getTitle(1);
	var strValidTitle = "";

	var strFileName = "";
	var strDisplayName = "";
	var strFileSize = "";

	var strDocExtention = getBodyType();

//	if (strDocExtention == "whwp")
//		strDocExtention = "hwp";

	var objBodyFile = getBodyFileObj();
	if (objBodyFile != null)
	{
		var strUploadUrl = g_strUploadUrl;
		var strUploadPath = dataForm.uploadpath.value;
		var strDownloadPath = g_strDownloadPath;

		var strBodyFile = getAttachFileName(objBodyFile);

//		if (saveApproveDocumentAs(strDownloadPath + "Approve_" + strBodyFile, true) == true)
//			strBodyFile = "Approve_" + strBodyFile;

		saveApproveDocumentAs(strDownloadPath + "Approve_" + strBodyFile, true);
		strBodyFile = "Approve_" + strBodyFile;

		strLocalPath = strDownloadPath + strBodyFile;
		strServerUrl = strUploadUrl + strBodyFile;

		if (uploadFile(strLocalPath, strServerUrl) == false)
			strBodyFile = getAttachFileName(objBodyFile);

		strFileName += strBodyFile + "|";
		var strExt = "";
		var nFind = strFileName.lastIndexOf(".");
		if (nFind != -1)
			strExt = strFileName.substring(nFind, strFileName.length);

		strValidTitle = strTitle.replace(/[\\\/\:\*\?\"\<\>\|]/g, "-");

		strDisplayName += strValidTitle + strExt;
		if (strExt == "")
			strDisplayName += "|";

		strFileSize += getAttachFileSize(objBodyFile) + "|";
	}

	var objGeneralAttach = getFirstGeneralAttach();
	while (objGeneralAttach != null)
	{
		strFileName += getAttachFileName(objGeneralAttach) + "|";
		nFind = getAttachFileName(objGeneralAttach).lastIndexOf(".");
		if (nFind != -1)
			strExt = getAttachFileName(objGeneralAttach).substring(nFind, strFileName.length);
		strDisplayName += getAttachDisplayName(objGeneralAttach) + strExt + "|";
		strFileSize += getAttachFileSize(objGeneralAttach) + "|";
		objGeneralAttach = getNextGeneralAttach(objGeneralAttach);
	}

	var strUrl = "http://" + g_strServer + "/uni2k/Post/Document/Post_NewDocument.asp?BaseUrl=http://" + g_strServer + "/uni2k/&DataUrl=" + escape(strFolderUrl);
	strUrl +=  "&sSubject=" + escape(strTitle) + "&Flag=New" + "&FolderType=" + escape(strFolderType) + "&SubmitFlag=" + escape(strSubmitFlag) + "&strType=APPR";
	strUrl += "&sAttachFull=" + escape(strFileName) + "&sAttachDisplay=" + escape(strDisplayName) + "&sAttachSize=" + strFileSize + "&deletefolder=N";
	strUrl += "&jsppath=" + strUploadPath;

	window.open(strUrl, "Approve_SendPost", "toolbar=no, status=yes, width=790, height=525");
}

// 20030824, icaha
function onPrevApprove()
{
	var element = opener.getPrevElement();
	var bOther = false;
	while (element != null)
	{
		var strDocCategory = element.getAttribute("doccategory");

		if (strDocCategory != null && (strDocCategory.indexOf("C") != -1 || strDocCategory.indexOf("O") != -1))
		{
			bOther = true;
			opener.moveCurDocIndexPrev();
			element = opener.getPrevElement();
		}
		else
			break;
	}
	if (bOther)
		alert("민원 문서나 외부 등록 문서는 '이전문서/다음문서' 메뉴로 열람할 수 없습니다.");

	if (element != null)
	{
		if (g_strBodyType == "doc")
			saveDocument();

		g_strDirection = "prev";
		onMoveApprove(element);
	}
	else
	{
		if (opener.getSelectedCount() == 0)
			alert(ALERT_NO_PREVIOUS_DOCUMENT);
		else
			alert(ALERT_NO_PREVIOUS_SELECTED_DOCUMENT);
	}
}

// 20030824, icaha
function onNextApprove()
{
	var element = opener.getNextElement();
	var bOther = false;
	while (element != null)
	{
		var strDocCategory = element.getAttribute("doccategory");
		if (strDocCategory != null && (strDocCategory.indexOf("C") != -1 || strDocCategory.indexOf("O") != -1))
		{
			bOther = true;
			opener.moveCurDocIndexNext();
			element = opener.getNextElement();
		}
		else
			break;
	}
	if (bOther)
		alert("민원 문서나 외부 등록 문서는 '이전문서/다음문서' 메뉴로 열람할 수 없습니다.");
	if (element != null)
	{
		if (g_strBodyType == "doc")
			saveDocument();

		g_strDirection = "next";
		onMoveApprove(element);
	}
	else
	{
		if (opener.getSelectedCount() == 0)
			alert(ALERT_NO_NEXT_DOCUMENT);
		else
			alert(ALERT_NO_NEXT_SELECTED_DOCUMENT);
	}
}

function onMoveApprove(element)
{
	g_strInputOpinion = "";

	var listIndex = element.getAttribute("dataurl");
	var strFolderList = element.getAttribute("folderlist");
	var nDocStatus = element.getAttribute("docstatus");
	var strLineName = element.getAttribute("openlinename");
	var nSerialOrder = element.getAttribute("openserialorder");
	var strSecurity = element.getAttribute("security");
	var strBodyType = element.getAttribute("bodytype");
// 20030824, icaha
	var strDocCategory = element.getAttribute("doccategory");
	var strIsPubDoc = element.getAttribute("ispubdoc");
	var strIsAdminMIS = element.getAttribute("isadminmis");

	if (strFolderList == CABINET_WAIT_REAL ||
		strFolderList == CABINET_REJECT_REAL ||
		strFolderList == CABINET_BACK_REAL ||
		strFolderList == CABINET_PRIVATE_REAL ||
		strFolderList == CABINET_DISCARD_REAL ||
		strFolderList == CABINET_SUBMIT_REAL ||
		strFolderList == CABINET_SUBMITEDAPPROVAL_REAL ||
		strFolderList == CABINET_SUBMITEDDOCFLOW_REAL ||
		strFolderList == CABINET_COMPLETE_REAL ||
		strFolderList == CABINET_PROCESS_REAL ||
		strFolderList == CABINET_DEPTWAIT_REAL ||
		strFolderList == CABINET_INVESTIGATION_REAL ||
		strFolderList == CABINET_SEND_REAL ||
		strFolderList == LEDGER_REGI_REAL)
	{
		if (strSecurity == "Y")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType;
			window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
		}
		else
		{
			if (strFolderList == CABINET_INVESTIGATION_REAL || strFolderList == CABINET_SEND_REAL || strFolderList == LEDGER_REGI_REAL)
			{
				if (strIsAdminMIS == "Y")
				{
					g_objAdminElement = element;

					var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=adminmis";
					window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=yes, width=600,height=370");
				}
				else
					onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
			}
			else
				onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
		}
	}
	else
	{
		// icaha, 20030824 redundancy ... shit...
		if (strFolderList == LEDGER_RECV_REAL || strFolderList == LEDGER_DIST_REAL)
			strLineName = "1";
		else if (strFolderList == CABINET_RECV_REAL || strFolderList == CABINET_DIST_REAL)
			strLineName = "1";
		else if (strFolderList == CABINET_PUBLICPOST_REAL)
			strLineName = "1";

		if (strFolderList == TRANSFER_LEDGER_REGI_REAL ||
			strFolderList == TRANSFER_LEDGER_RECV_REAL ||
			strFolderList == TRANSFER_LEDGER_DIST_REAL ||
			strFolderList == TRANSFER_LEDGER_TRANSFER_REAL ||
			strFolderList == TRANSFER_LEDGER_INSPECTION_REAL)
		{
			strFolderList = strFolderList.substr(1);
			onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory, opener.g_strSearchYear);
		}
		else
		{
			onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
		}
	}
}

// 20030824, icaha
function onOpenPubDoc(strBodyType, strFormUrl)
{
	if (g_objPubdocElement != null)
	{
		var listIndex = g_objPubdocElement.getAttribute("dataurl");
		var strFolderList = g_objPubdocElement.getAttribute("folderlist");
		var nDocStatus = g_objPubdocElement.getAttribute("docstatus");
		var strLineName = g_objPubdocElement.getAttribute("openlinename");
		var nSerialOrder = g_objPubdocElement.getAttribute("openserialorder");
		var strSecurity = g_objPubdocElement.getAttribute("security");
		var strBodyType = g_objPubdocElement.getAttribute("bodytype");
		var strDocCategory = g_objPubdocElement.getAttribute("doccategory");

		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
	else
	{
		alert("Failed to find PubDoc");
	}
}

// 20030824, icaha
function onOpenAdminMIS(strBodyType, strFileName, nEditType, strFormName, strFormUsage, strLogoName, strSymbolName)
{
	if (g_objAdminElement != null)
	{
		var listIndex = g_objAdminElement.getAttribute("dataurl");
		var strFolderList = g_objAdminElement.getAttribute("folderlist");
		var nDocStatus = g_objAdminElement.getAttribute("docstatus");
		var strLineName = g_objAdminElement.getAttribute("openlinename");
		var nSerialOrder = g_objAdminElement.getAttribute("openserialorder");
		var strSecurity = g_objAdminElement.getAttribute("security");
		var strBodyType = g_objAdminElement.getAttribute("bodytype");
		var strDocCategory = element.getAttribute("doccategory");

		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
	else
	{
		alert("Failed to find AdminMIS Document");
	}
}

function onOpenMessage(strDataUrl, strCabinet, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory)
{
	if (g_strDirection == "prev")
		opener.moveCurDocIndexPrev();
	else
		opener.moveCurDocIndexNext();

//	dataForm.xmlData.value = strApprovalDoc;
//	dataForm.extendData.value = strExtendData;
	dataForm.returnFunction.value = "onGetDocDataCompleted";
	dataForm.action = "./approve/document/CN_ApproveGetDocData.jsp?dataurl=" + strDataUrl + "&cabinet=" + strCabinet + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType + "&transyear=" + g_strTransYear;
	dataForm.target = "dataTransform";
	dataForm.submit();
}

function onGetDocDataCompleted()
{
	g_bIsDocSwitching = true;
	var strOldBodyType = g_strBodyType;

	// 서버 사이드에서 결정되어 작성기 액세스, 메뉴, 작성기 등의 재로딩의 파라미터가 되는 각종 글로벌 재설정
	g_strDataUrl = dataTransform.g_strDataUrl;
	g_strFormUrl = dataTransform.g_strFormUrl;
	g_strFormName = dataTransform.g_strFormName;
	g_strCabinet = dataTransform.g_strCabinet;
	g_strDocStatus = dataTransform.g_strDocStatus;
	g_strLineName = dataTransform.g_strLineName;
	g_strSerialOrder = dataTransform.g_strSerialOrder;
	g_strEditType = dataTransform.g_strEditType;
	g_strVersion = dataTransform.g_strVersion;
	g_strSubject = dataTransform.g_strSubject;
	g_strIsAttached = dataTransform.g_strIsAttached;
	g_strIsDirect = dataTransform.g_strIsDirect;
	g_strIsBatch = dataTransform.g_strIsBatch;
	g_strIsOpinion = dataTransform.g_strIsOpinion;
	g_strIsPost = dataTransform.g_strIsPost;
	g_strDocCategory = dataTransform.g_strDocCategory;
	g_strEnforceBound = dataTransform.g_strEnforceBound;
	g_strRegistryType = dataTransform.g_strRegistryType;
	g_strBodyType = dataTransform.g_strBodyType;

	// 타이틀 재로딩
	title.innerHTML = getTitleHtml();
	var strXML = dataTransform.ApprovalDoc.documentElement.xml;
	ApprovalDoc.loadXML(strXML);

	// 메뉴 재로딩...
	menu.innerHTML = getMenuHtml();
/*
	var eleEnforce = document.getElementById("enforceSide");
	eleEnforce.innerHTML = "";
*/
	if (g_strBodyType == "hwp")
	{
		document.getElementById("hwpMenuSet").value = document.getElementById("defaultMenu").value;
	}

	if (strOldBodyType != g_strBodyType)
	{
		if (strOldBodyType == "hwp")
		{
			Document_HWPProxy2.Disconnect();
			var eleEnforce = document.getElementById("enforceSide");
			if (eleEnforce.innerHTML != "")
			{
				Enforce_HWPProxy2.Disconnect();
				eleEnforce.innerHTML = "";
			}

			window.moveTo(0, 0);
			window.resizeTo(790, 545);
			Document_HWPProxy2.SetWindowNormal();
		}

		// 작성기 액세스 변경 - 작성기 액세스 로딩이 완료될 때 작성기 html 변경시킴
		changeEditorAccess();

// synchronization of script loading...
//		editor.innerHTML = getEditorHtml();
	}
	else
	{
/*
		var eleEnforce = document.getElementById("enforceSide");
		eleEnforce.innerHTML = "";

		enforceAccess.src = "";
		changeEnforceAccess();
*/
		if (g_strBodyType == "txt")
		{
			changeEditorAccess();
		}

		// 이전문서, 다음문서에서 중계문서일 경우 양식 선택창을 띄워야 함.
		if (g_strDataUrl != "" && getIsPubDoc() == "Y")
		{
			var bReturn = unPack();

			if (bReturn == false)
			{
				if (g_strBodyType == "hwp")
					g_bHwpNeedClose = true;
				else
					window.close();
			}
		}
		else
		{
			// 시행문 액세스 없을 경우 추가됨
			if (enforceAccess.src == "")
				changeEnforceAccess();
			else
			{
				if (g_strBodyType == "hwp")
				{
					// 결재 문서 초기화
					g_bLegacyFormSelected = false;
					g_bIsModified = false;
					g_strSenderAs = "";
					g_strHwpDocumentTitle = "";
	
					// 시행문 초기화
					g_bEnforceHwpProxyCreated = false;
					g_bEnforceStart = false;
					g_nEnforceProposal = 1;
					g_strEnforceFormUrl = "";
					g_bIsEnforceModified = false;
					g_bHwpEnforceOpen = false;
					g_strHwpEnforceTitle = "";
					g_strEnforceFormName = "";
	
					var eleEnforce = document.getElementById("enforceSide");
					if (eleEnforce.innerHTML != "")
					{
						Enforce_HWPProxy2.Disconnect();
						eleEnforce.innerHTML = "";
					}
				}
	
				if (getLegacySystem() != null && getFlowStatus() == "0")
				{
					var objApprovalLine = getActiveApprovalLine();
					if (objApprovalLine != null)
					{
						var strStatus = getDocStatus(objApprovalLine);
						if (strStatus == "1")		// 기안자 지정됨
						{
							onOpenLegacyForm();
						}
						else
						{
							loadDocument();
						}
					}
					else
						loadDocument();
				}
				else
				{
					loadDocument();
				}
			}
		}
	}
}

function onBodyAccessLoadingCompleted()
{
	if (typeof(g_bIsDocSwitching) != "undefined" && g_bIsDocSwitching == true)
	{
		editor.innerHTML = getEditorHtml();
		if (g_strBodyType == "hwp")
		{
			g_bDocumentHwpProxyCreated = true;
		}

		if (getLegacySystem() != null && getFlowStatus() == "0")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strStatus = getDocStatus(objApprovalLine);
				if (strStatus == "1")		// 기안자 지정됨
				{
					onOpenLegacyForm();
				}
				else
				{
					onLoadDocument();
				}
			}
			else
				onLoadDocument();
		}
		else if (g_strDataUrl != "" && getIsPubDoc() == "Y")
		{
			var bReturn = unPack();

			if (bReturn == false)
			{
				if (g_strBodyType == "hwp")
					g_bHwpNeedClose = true;
				else
					window.close();
			}
		}
		else if (g_strDataUrl != "" && getIsAdminMis() == "Y")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strDocStatus = getDocStatus(objApprovalLine);
				if (strDocStatus == "1")
				{
					downloadAndUnpackAdminMisExchPackFile();
					onConvertAdminXmlToDocXml();
				}
				else
					onLoadDocument();
			}
			else
				onLoadDocument();
		}
		else
		{
			onLoadDocument();
		}
	}
}

function onEnforceAccessLoadingCompleted()
{
	if (typeof(g_bIsDocSwitching) != "undefined" && g_bIsDocSwitching == true)
	{
		if (getLegacySystem() != null && getFlowStatus() == "0")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strStatus = getDocStatus(objApprovalLine);
				if (strStatus == "1")		// 기안자 지정됨
				{
					onOpenLegacyForm();
				}
				else
				{
					onLoadDocument();
				}
			}
			else
			{
				onLoadDocument();
			}
		}
		else
		{
			onLoadDocument();
		}
	}
}
