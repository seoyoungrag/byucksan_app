
var g_strOpenFormType = "";
var g_bPreviewConvert = false;
var g_wndApproveAttach = null;

function onCheckPassword(strFunction)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePWDialog.jsp?function=" + strFunction;
	window.open(strUrl,"Approve_ConfirmPW","toolbar=no,resizable=no, status=yes, width=220,height=270");
}

function onSetDocInfo()
{
/*
	var nBodyCount = getBodyCount();
	if (nBodyCount > 1)
	{
		var nHeight = 180 + (nBodyCount - 3) * 24;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectProposal.jsp?start=1&end=" + nBodyCount + "&returnFunction=openSetDocInfo";
		window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=300,height="+nHeight);
	}
	else
*/
		openSetDocInfo(1);
}

function openSetDocInfo(nCaseNumber)
{
	setFormDataToXml();
	var nBodyCount = getBodyCount();
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocInfo.jsp?bodycount=" + nBodyCount + "&casenumber=" + nCaseNumber  + "&isdirect=" + g_strIsDirect + "&formusage=" + g_strFormUsage;
	window.open(strUrl,"Approve_DocInfo","toolbar=no,resizable=no, status=yes, width=450,height=340");
}

function onSetClassInfo()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveClassInfo.jsp";
	window.open(strUrl,"Approve_ClassInfo","toolbar=no,resizable=no, status=yes, width=350,height=400");
}

function onSetApproveLine()
{
	if (getFlowStatus() == "18")
	{
		var objApprovalLine = getActiveApprovalLine();
		if (objApprovalLine != null)
		{
			var objApprover = getActiveApprover();
			if (getApproverUserID(objApprover) != g_strApproverID)
			{
				removeApprovalLine(objApprovalLine);
				alert(ALERT_RESET_APPROVE_LINE);
			}
		}
	}

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSetLine.jsp?setmode=org";
	var objWindow = window.open(strUrl,"Approve_SetApproveLine","toolbar=no,resizable=no, status=yes, width=600,height=540");
}

function onSetApproveInfo()		//결재정보
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowSetApproveInfo.jsp?";
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=yes, width=450,height=190");
}

function onSetRecipPart()
{
	var strRegistryType = getRegistryType();
	if (strRegistryType == "N")
	{
		alert(ALERT_RECIP_ENABLE);
		onSetDocInfo();
		return;
	}
/*
	var nBodyCount = getBodyCount();
	if (nBodyCount > 1)
	{
		var nHeight = 180 + (nBodyCount - 3) * 24;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectProposal.jsp?start=1&end=" + nBodyCount + "&returnFunction=openSetRecipPart";
		window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=300,height="+nHeight);
	}
	else
*/
		openSetRecipPart(1);
}

function openSetRecipPart(nCaseNumber)
{
//	var strDocCategory = getDocCategory(nCaseNumber);
//	if (strDocCategory == "I")
/*
	var strRegistryType = getRegistryType();
	if (strRegistryType != "T")
	{
		alert(ALERT_NONE_RECIPIENT_DOCUMENT);
		return;
	}
*/
	var bEnable = false;
	var nProposal = 1;
	var nBodyCount = getBodyCount();
	while (nProposal <= nBodyCount)
	{
		var strDocCategory = getDocCategory(nProposal);
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			bEnable = true;
			break;
		}

		nProposal++;
	}

	if (bEnable == false)
	{
		alert(ALERT_NONE_RECIPIENT_DOCUMENT);
		return;
	}

	var nBodyCount = getBodyCount();
	var strBound = getEnforceBound(nCaseNumber);
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSetRecipient.jsp?casenumber=" + nCaseNumber + "&bound=" + strBound + "&bodycount=" + nBodyCount;

	window.open(strUrl,"Approve_SetRecipient","toolbar=no,resizable=no, status=yes, width=700,height=450");
}
/*
function onSetOpinion()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=W";
	window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
//	showModalDialog(strUrl, null, "center:yes;scroll:no;status:yes;dialogWidth:306px;dialogHeight:310px;help:no");
}
*/
function onSetCallOpinion(strReturnFunction)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=W&returnFunction=" + strReturnFunction + "&bodytype="+getBodyType();
	window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
//	showModalDialog(strUrl, null, "center:yes;scroll:no;status:yes;dialogWidth:306px;dialogHeight:310px;help:no");
}

function onAttachFiles()
{
//	var strUrl = "http://70.7.22.129/pattern_html/page_pattern/set/attach_n_sb01.html";
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveAttach.jsp?edittype=" + g_strEditType + "&formusage=" + getFormUsage() + "&isadminmis=" + getIsAdminMis();

	g_wndApproveAttach = window.open(strUrl,"Approve_Attach","toolbar=no,resizable=no, status=yes, width=420,height=360");
}

function onDeleteDocument()
{
	var nBodyCount = getBodyCount();
	if (nBodyCount > 2)
	{
		var nHeight = 180 + (nBodyCount - 3) * 36;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectProposal.jsp?title=" + BTN_REMOVE_DOCUMENT + "&start=2&end=" + nBodyCount + "&returnFunction=removeDocument&type=delete";
		window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=300,height="+nHeight);
	}
	else if (nBodyCount == 2)
		removeDocument(nBodyCount);
	else
		alert(ALERT_CANNOT_DELETE_ONLY_ONE_PROPOSAL);
}

function onViewDocDetail()
{
	if (g_strEditType == "21" || g_strEditType == "22" || g_strEditType == "24")	// 접수대장
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&cabinet=" + g_strCabinet;
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
	else if (g_strEditType == "23" || g_strEditType == "52") // 공람게시
	{
		if (g_strCabinet == "RECVPUBLICPOST" || g_strCabinet == "SUBMITEDDOCFLOW")
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&cabinet=" + g_strCabinet;
		else // "REGIPUBLICPOST" "SUBMITEDAPPROVAL"
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?cabinet=" + g_strCabinet;

		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
	else if (g_strEditType == "30" || g_strEditType == "31" || g_strEditType == "32" ||
			 g_strEditType == "33" || g_strEditType == "34" || g_strEditType == "35" ||
			 g_strEditType == "40")	// 접수이후 결재
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&cabinet=" + g_strCabinet;
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
	else if (g_strEditType == "18" || g_strEditType == "20")	// 접수함, 배부함
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=ING";
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
	}
	else if (g_strEditType == "19")	// 배부대장
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=DIST";
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
	}
/*	else if ()
	{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowPostDetail.jsp?dataurl=" + escape(strDataUrl);
			window.open(strUrl,"CN_DocflowPostDetail","toolbar=no,resizable=no, status=yes, width=500,height=240");
	}
*/	else
	{
		if (getFlowStatus() < 8)
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?cabinet=" + g_strCabinet;
		else
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&cabinet=" + g_strCabinet;
		window.open(strUrl,"Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
}

function onPreviewConvert()
{
	saveBodyFile();
	setModified(true);

	g_bPreviewConvert = true;
	setFormDataToXml();
	onConvertEnforce();
}

function onConvertEnforce()
{
	if (checkAutoEnforce() == true)
	{
		var strEnforceFormID = getEnforceFormID();

		dataForm.xmlData.value = strEnforceFormID;
		dataForm.returnFunction.value = "onConvertAutoEnforce"

		dataForm.action = "./approve/dialog/CN_ApproveFormLinkedEnforce.jsp";
		dataForm.target = "dataTransform";
		dataForm.submit();

		return;
	}
	onSelectEnforceForm();
}

function onConvertAutoEnforce(nType)
{
	if (nType == true)
	{
		var strEnforceFormID = getEnforceFormID();
		var strBodyType = getBodyType();

		if (onOpenEnforceForm(strEnforceFormID + "." + strBodyType, strBodyType, "", "", "", "", false) == true)
		{
			setEnforceFormID("");
			changeMenu(1);

			dataForm.xmlData.value = "";
			dataForm.returnFunction.value = "";

			dataForm.action = "./signature/action/CN_SignatureGetAutoDeptImage.jsp";
			dataForm.target = "dataTransform";
			dataForm.submit();

			return;
		}
	}

	onSelectEnforceForm();
}

function onSelectEnforceForm()
{
	var nBodyCount = getBodyCount();
	var bEnforce = false;
	for (var i = 1; i <= nBodyCount; i++)
	{
		var strDocCategory = getDocCategory(i);
		if (strDocCategory == "E" || strDocCategory == "W")
		{
			bEnforce = true;
			break;
		}
	}
	if (!bEnforce)
	{
		alert(ALERT_NO_ENFORCE_DRAFT);
		return;
	}

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=enforce&doctype=" + getBodyType();
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=yes, status=no, width=800,height=470");
/*
// 양식함 호출
// 양식함에서 양식 선택히 cn_ApproveMenuImpl.js 의 onOpenForm(strFormUrl, strDocType) 함수 호출
	var strFormUrl;
	var strDocType;
	if (getBodyType() == "gul")
	{
		strFormUrl = "공문서 유통 시행문.gul";
		strDocType = "gul";
	}
	else if (getBodyType() == "hwp")
	{
		strFormUrl = "공문서 유통 시행문.hwp";
		strDocType = "hwp";
	}

	onOpenEnforceForm(strFormUrl, strDocType);
*/
}

// 임시 함수 : 향후 함수명을 변경하고 리스트와 동조 필요
// icaha 20030117
//function openNewDocument(strExt, strFileName, nEditType, strFormName, bIsLocal)
// jimi 20030822 formid추가
function openNewDocument(strBodyType, strFileName, nEditType, strFormName, strEnforceFormID, strFormUsage, strLogoName, strSymbolName, strFomrID, bIsLocal)
{
	if (g_nPrevActionType != 0)
	{
		g_strLogoName = strLogoName;
		g_strSymbolName = strSymbolName;

		doReturnSubmitApprove(strFileName, strBodyType, strFormName, strFormUsage);
	}
	else
	{
		if (g_strOpenFormType == "Inspect")
			onOpenDraftForm(strFileName, strBodyType, strFormName, strEnforceFormID, bIsLocal);
		else if (g_strOpenFormType == "Receive")
		{
			g_strFormUrl = strFileName;
			setBodyType(strBodyType);
			setFormUsage(strFormUsage);

			if (downloadFile(g_strUploadUrl + g_strFormUrl, g_strDownloadPath + g_strFormUrl) == false)
			{
				alert("Download Error");
				return false;
			}

// 아래아한글 타이밍에 필요.
			g_bHeadBodySelected = true;
			onLoadDocument();
		}
		else if (g_strOpenFormType == "Submit")
		{
			g_strLogoName = strLogoName;
			g_strSymbolName = strSymbolName;

			doAttachSubmitApprove(strFileName, strBodyType, strFormName, strFormUsage);
		}
		else
		{
			onOpenEnforceForm(strFileName, strBodyType, strFormName, strLogoName, strSymbolName, strEnforceFormID, bIsLocal);
		}
	}
}

function onChangeForm()
{
	g_strOpenFormType = "Enforce";

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=enforce&doctype=" + getBodyType();
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
}

function onChangeInspectForm()
{
	g_strOpenFormType = "Inspect";

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
}

function onChangeReceiveForm()
{
	g_strOpenFormType = "Receive";
	setBodyType(g_strBodyType);

	if (g_strOption170 == "0")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + g_strBodyType;
		window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
	}
	else
	{
		var objApprovalLine = getApprovalLineByLineName("0");
		if (objApprovalLine == null)
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + g_strBodyType;
			window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");

			return;
		}

		var nApprover = 0;
		var nCooperator = 0;

		var nCount = getApproverCount(objApprovalLine);
		var i = 0;

		while (i < nCount)
		{
			var objApprover = getApproverBySerialOrder(objApprovalLine, i);
			if (objApprover != null)
			{
				var strApproverRole = getApproverRole(objApprover);
				if (strApproverRole == "4")
					nCooperator++;
				else
					nApprover++;
			}

			i++;
		}

		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDownloadDefaultForm.jsp?approver=" + nApprover + "&cooperator=" + nCooperator + "&type=" + g_strOption170 + "&returnFunction=onDownloadDefaultForm";
		dataTransform.location.href = strUrl;
	}
}

function onChangeAttachSubmitForm()
{
	g_strOpenFormType = "Submit";
	setBodyType(g_strBodyType);

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + g_strBodyType;
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
}

function onDownloadDefaultForm(strFormName)
{
	if (strFormName == "" || typeof(strFormName) == "undefined")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&doctype=" + getBodyType();
		window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
	}
	else
	{
		openNewDocument(getBodyType(), strFormName, 0, strFormName, "", "1", "", "", "", false);
	}
}

function onChangeDocInfo(nCaseNumber)
{
	var nBodyCount = getBodyCount();
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveModEnforceBound.jsp?casenumber=" + nCaseNumber + "&bodycount=" + nBodyCount + "&isdirect=" + g_strIsDirect;
	window.open(strUrl,"Approve_EnforceBound","toolbar=no,resizable=no, status=no, width=410,height=210");
}

function onConfirmAccept()
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiConfirmRecipient.jsp?type=CONFIRM";
	window.open(strUrl,"CN_ApproveConfirmAccept", "toolbar=no,resizable=no, status=yes,width=600,height=390,top=0,left=0");
}

function onCallbackEnforce()
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiConfirmRecipient.jsp?type=CALLBACK";
	window.open(strUrl,"CN_ApproveCallbackEnforce", "toolbar=no,resizable=no, status=yes,width=600,height=390,top=0,left=0");
}

function onChangeProposal()
{
	var nBodyCount = getBodyCount();
	if (nBodyCount > 1)
	{
		// 20030613, 수동 날인시 저장할 타이밍이 없음(이벤트 방식으로 하려 하나, OCX 가 또 수정되어야 함)
		if (isEnforceModified() == true)
			if (getBodyType() == "hwp" || getBodyType() == "han")
				saveEnforceFile(g_nEnforceProposal)

		var nSelect = getEnforceProposal();
		var nHeight = 180 + (nBodyCount - 3) * 36;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectProposal.jsp?title=" + CHANGE_PROPOSAL + "&start=1&end=" + nBodyCount + "&select=" + nSelect + "&returnFunction=onProposalChanged&type=select";
		window.open(strUrl,"CN_ApproveSelectProposal","toolbar=no,resizable=no, status=yes, width=300,height="+nHeight);
	}
}

function onSetDealerInfo(nType)
{
	if (nType == 0)
	{
		nType = 5;
		var strTargetId = "";
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType + "&targetid=" + strTargetId;
		window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
	}
	else if (nType == 1)
	{
		nType = 6;
		var strTargetId = "";
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType + "&targetid=" + strTargetId;
		window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
	}
	else if (nType == 2)
	{
		nType = 7;
		var strTargetId = "";
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType + "&targetid=" + strTargetId;
		window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
	}
	else if (nType == 3)
	{
		nType = 8;
		var strTargetId = "";
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType + "&targetid=" + strTargetId;
		window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
	}
}

function onSelectDelivery()
{
	var strTreeType = "1";
	var strSetType = "0";	//처리과
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetDepartment.jsp?deptid=" + g_strUserDeptCode + "&treetype=" + strTreeType + "&settype=" + strSetType;
	window.open(strUrl, "Docflow_Department", "toolbar=no, resizable=no, status=yes, width=300, height=250");
}

function onSelectMovePart()
{
	var strTreeType = "0";	// 대내 수신처
	var strSetType = "1";	// 이송 처리

	if (getFlowStatus() == "9" || getFlowStatus() == "11" || getFlowStatus() == "13")
		strTreeType = "0";	// 대외 수신처

	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetDepartment.jsp?deptid=" + g_strUserDeptCode + "&treetype=" + strTreeType + "&settype=" + strSetType;
	window.open(strUrl, "Docflow_Department", "toolbar=no, resizable=no, status=yes, width=300, height=250");
}

function onSelectRelatedSystem(setmode)
{
//	var strSecurityPass = getSecurityPass();
	var strFlowStatus = getFlowStatus();
/*
	if (strSecurityPass != "")
	{
		alert(ALERT_SECURITY_DOCUMENT);
		return;
	}
*/
	if (setmode == 1)
	{
		if (strFlowStatus == "1" || strFlowStatus == "6" || strFlowStatus == "7" || strFlowStatus == "15")
		{
		}
		else if (strFlowStatus == "14")
		{
			var strDocStatus = "";
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
				var strDocStatus = getDocStatus(objApprovalLine);

			if (strDocStatus != "" && strDocStatus != "1" && strDocStatus != "11" && strDocStatus != "12")
			{
				alert(ALERT_CANNOT_SEND_TO);
				return;
			}
		}
		else
		{
			alert(ALERT_CANNOT_SEND_TO);
			return;
		}
	}
	var strCabinetType = g_strCabinet;
	var strUrl = g_strBaseUrl + "related/dialog/CN_RelatedSelectSystem.jsp?relatedtype=APPR&setmode=" + setmode + "&cabinettype=" + strCabinetType;
	window.open(strUrl, "Related_SelectSystem", "toolbar=no, resizable=yes, scrollbars=no, status=yes, width=265, height=270");
}

function onViewOpinion()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveViewOpinion.jsp";
	window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=yes, status=yes, width=700,height=350");
}

function onViewExamOpinion()
{
	var objDeliverer = getDelivererByDelivererType("0");
	if (objDeliverer != null)
	{
		var strOpinion = getDelivererOpinion(objDeliverer);
		if (strOpinion != "")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=R&returnFunction=&opinion=" + strOpinion + "&bodytype=" + getBodyType();
			window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
		}
	}
}

// 연동양식도 시행가능토록
function openLegacyForm(strExt, strFileName, nEditType, strFormName, strFormUsage, strLogoName, strSymbolName)
{
	if (strFileName == "")
		return;

	if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) == false)
	{
		alert("Download Error");
		return;
	}

	if (strLogoName != "")
		downloadFile(g_strUploadUrl + strLogoName, g_strDownloadPath + strLogoName);
	if (strSymbolName != "")
		downloadFile(g_strUploadUrl + strSymbolName, g_strDownloadPath + strSymbolName);

	g_strFormUrl = strFileName;
	g_strFormName = strFormName;
	g_strLogoName = strLogoName;
	g_strSymbolName = strSymbolName;

	if (strFormUsage == "" || strFormUsage == "undefined")
		strFormUsage = "3";
	if (strFormUsage == "0")
	{
		g_strRegistryType = "T";
		g_strIsDirect = "N";
		g_strDocCategory = "E";
		g_strEnforceBound = "I";
	}
	else if (strFormUsage == "1" || strFormUsage == "2")
	{
		g_strRegistryType = "T";
		g_strIsDirect = "Y";
		g_strDocCategory = "E";
		g_strEnforceBound = "I";
	}
	g_strFormUsage = strFormUsage;

	setFormUsage(strFormUsage);
	setRegistryType(g_strRegistryType);
	setIsDirect(g_strIsDirect);
	setDocCategory(g_strDocCategory, 1);
	setEnforceBound(g_strEnforceBound, 1);

	if (strExt == "hwp")
	{
/*
		Document_HWPProxy2.attachEvent("OnCreated", onDocumentHwpProxyCreated);
		Document_HWPProxy2.attachEvent("OnHWPNotFound", onDocumentHwpNotFound);
		Document_HWPProxy2.attachEvent("OnHWPCommand", onDocumentHwpProxyCommand);
*/
	}
	g_bLegacyFormSelected = true;
	// 메뉴 재로딩...
	menu.innerHTML = getMenuHtml();
	onLoadDocument();
}

function onOpenLegacyForm()
{
	alert('onOpenLegacyForm');
	var objLegacySystem = getLegacySystem();
	var strSystemID = getLegacySystemID(objLegacySystem);
	var strBusinessID = getBusinessID(getBusinessInfo(objLegacySystem));
	var strFormVersion = getInputConfigFormVersion();

//	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveLegacyFormList.jsp?systemid=" + strSystemID + "&businessid=" + strBusinessID;
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpenLegacyForm.jsp?systemid=" + strSystemID + "&businessid=" + strBusinessID;

	if (strFormVersion != null)
		strUrl += "&formversion=" + strFormVersion;

	// 20030416, roadshow
	if (strFormVersion == null)
		strUrl += "&formversion=" + "1.0";

	strUrl += "&doctype=" + getBodyType();
	strUrl += "&folderid=" + g_strUserDeptCode + "&edittype=" + g_strEditType;

//	window.open(strUrl,"","toolbar=no,resizable=no, status=no, width=600,height=370");

	dataForm.returnFunction.value = "openLegacyForm";
	dataForm.action = strUrl;
	dataForm.target = "legacyTransform";
	dataForm.submit();
}

function onEditSynopsis()
{
	var strBodyType = getBodyType();
	var strDataUrl = "";

	var objExtendAttach = getFirstExtendAttach();
	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "Synopsis")
		{
			strFileName = getAttachFileName(objExtendAttach);
			if (strFileName != "")
				strDataUrl = strFileName;

			var strLocalPath = getDownloadPath() + strFileName;
            var nFileSize = getLocalFileSize(strLocalPath);
            if(nFileSize == "0")
            {
                var strServerUrl = getUploadUrl() + strFileName;
                downloadFile(strServerUrl, strLocalPath);
            }

			break;
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}

	var strUrl = g_strBaseUrl + "approve/document/CN_ApproveSynopsis.jsp?bodytype=" + strBodyType + "&dataurl=" + strDataUrl;
	window.open(strUrl, "CN_ApproveSynopsis", "toolbar=no,resizable=yes, status=yes, width=790,height=545");
}

function onOpenZipcodeInput()
{
	var strUrl = g_strBaseUrl + "zipcode/CN_ZipcodeInput.jsp";
	var objWindow = window.open(strUrl,"ZipcodeInput","toolbar=no,resizable=no, status=yes, width=500,height=440");
}

function onSetPublicViewInfo()
{
	var strPost = getIsPost();

	if (strPost == "Y")
	{
		alert(ALERT_ALREADY_POSTED);
		return;
	}

	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowSetPublicViewInfo.jsp?cabinet=" + g_strCabinet;
	window.open(strUrl, "CN_DocflowPublicView", "toolbar=no,resizable=yes, status=yes, width=450,height=190");
}

function onModPublicViewInfo()
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowSetPublicViewInfo.jsp?type=MOD&dataurl=" + escape(g_strDataUrl) + "&cabinet=" + g_strCabinet;
	window.open(strUrl, "CN_DocflowPublicView", "toolbar=no,resizable=yes, status=yes, width=450,height=190");
}

function onDelPublicViewInfo()
{
	onDeletePublicView();
}

function onSetEtcData()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveEtcData.jsp?type=0";
	window.open(strUrl,"Approve_ClassInfo","toolbar=no,resizable=no, status=yes, width=350,height=330");
}

function onSetInspectEtcData()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveEtcData.jsp?type=1";
	window.open(strUrl,"Approve_ClassInfo","toolbar=no,resizable=no, status=yes, width=350,height=300");
}

//Public Level
function onEditPublicLevel()
{
	var strPublicLevel = getPublicLevel();
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePublicLevel.jsp?publiclevel=" + strPublicLevel;
	window.open(strUrl,"Approve_PublicLevel","toolbar=no,resizable=no, status=yes, width=350,height=200");
}

// 기록물철
function onEditArchiveInfo()
{
	var strArchiveDeptCode = g_strApproverDeptCode;
	if (getFlowStatus() < 8)
		strArchiveDeptCode = getDraftProcDeptCode();
	else if (getFlowStatus() > 8)
		strArchiveDeptCode = getEnforceProcDeptCode();

	var strUrl = g_strBaseUrl + "dm/govjsp/DM_GovWrkSelectFrame.jsp";
	var strRetUrl = g_strBaseUrl + "approve/action/CN_ApproveGetWrkValue.jsp";

	strUrl += "?PAGE=APR&OPTION=CLP&MODAL=N&TARGET=dataTransform&WRKALL=Y&RETURL=" + strRetUrl +"&DPTID=" + strArchiveDeptCode;
	window.open(strUrl,"Approve_ArchiveInfo","toolbar=no,resizable=no, status=yes, width=350,height=420");
}

function onConfirmPublicView()
{
	var strPostPosition = "RECVLEDGER";
	if (g_strCabinet == "REGILEDGER" || g_strCabinet == "SUBMITED" || g_strCabinet == "SUBMITEDAPPROVAL")
		strPostPosition = "REGILEDGER";

	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvConfirmPublic.jsp?docid=" + getDocID() + "&subject=" + getTitle(1) + "&postposition=" + strPostPosition;
	window.open(strUrl,"CN_DocflowPublic", "toolbar=no,resizable=no, status=yes,width=400,height=370,top=0,left=0");
}
