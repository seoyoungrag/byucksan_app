var g_wndDocument = null;
var g_bAgreeApprove = false;
// 20030528, icaha document switching
var g_nCurDocIndex;

function initialize()
{
	var element = document.getElementById("pageinfo");
	if (element != null)
	{
		var nCurPage = element.curpage;
		var nMessagePerPage = element.docsperpage;
		var nCount = element.docitemcount;
		g_nValidTotal = element.validcount;

		g_firstIndex = 0;
//		g_lastIndex = Math.min(g_nValidTotal - 1, parseInt(g_docsPerPage) - 1);
		g_lastIndex = Math.min(nCount - 1, parseInt(g_docsPerPage) - 1);

		if (g_lastIndex < 0)
			g_lastIndex = 0;
	}
	else
	{
		g_lastIndex = Math.min(g_nDocItemCount - 1, parseInt(g_docsPerPage) - 1);
	}

	if (g_strVersion == "2.0" || g_strVersion == "3.5")
	{
		var objListBound = document.getElementById("listbound");
		var nListBound = 0;
		if (g_strListBound == "TOTAL")
			nListBound = 0;
		else if (g_strListBound == "OPENED")
			nListBound = 1;
		else if (g_strListBound == "UNOPENED")
			nListBound = 2;

		if (objListBound != null)
			objListBound.selectedIndex = nListBound;
	}
}

function onNewMemoApprove()
{
//	var strWordList = "hwp^";
	var strWordList = g_strWordList;
	var arrWordList = strWordList.split("^");

	if (arrWordList.length >= 3)
	{
		var nHeight = 160 + (arrWordList.length - 3) * 22;

		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectWord.jsp?wordlist=" + strWordList;
		window.open(strUrl,"CN_ApproveSelectWord","toolbar=no,resizable=no, status=yes, width=300,height=" + nHeight);
	}
	else
	{
		var strBodyType = arrWordList[0];
		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?bodytype=" + strBodyType;
		openNewDocument(strBodyType, "", 0, "", "", "", "", "", "", "", "", "");
	}
}

function openNewDocument(strBodyType, strFileName, nEditType, strFormName, strEnforceFormId, strFormUsage, strLogoName, strSymbolName, strApprBizID, strApprBizName, strFormID, strFormClass)
{
//	if (strFileName != "")
	{
//		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?bodytype=" + strBodyType + "&formurl=" + strFileName + "&edittype=" + nEditType + "&formname=" + strFormName + "&enforceformid=" + strEnforceFormId;
		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?bodytype=" + strBodyType + "&formurl=" + strFileName + "&edittype=" + nEditType + "&formname=" + strFormName + "&enforceformid=" + strEnforceFormId + "&formusage=" +strFormUsage + "&logoname=" + strLogoName + "&symbolname=" + strSymbolName + "&apprbizid=" + strApprBizID + "&apprbizname=" + strApprBizName + "&formid=" + strFormID + "&formclass=" + strFormClass;

		//var strPageLoader = g_strBaseUrl + "Common/Asp/PageLoader.asp?pageurl=" + escape(strUrl);
		//g_wndDocument = openWindow(strPageLoader,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545,top=0,left=0");
		if (g_bEditorPopup == true)
		{
			if (g_wndDocument != null)
				g_wndDocument.close();

			g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545, top=0,left=0");
/*
			var nWidth = 790;
			var nHeight = 545;

			if (strBodyType == "hwp")
			{
				nWidth = 1;
				nHeight = 1;
			}

			g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=" + nWidth + ",height=" + nHeight + ", top=0,left=0");

			if (strBodyType == "hwp")
				g_wndDocument.moveTo(-5000, 0);
*/
//			showModalDialog(strUrl, null, "edge:sunken;resizable:yes;center:yes;scroll:no;status:yes;dialogWidth:800px;dialogHeight:600px;help:no");
		}
		else
		{
		}
	}
}

function onOpenMessage(strDataUrl, strCabinet, nDocStatus, strLineName, nSerialOrder, strBodyType)
{
	var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);

	if (g_bEditorPopup == true)
	{
		if (g_wndDocument != null)
				g_wndDocument.close();

		g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545, top=0,left=0");

/*
		var nWidth = 790;
		var nHeight = 545;

		if (strBodyType == 	"hwp")
		{
			nWidth = 1;
			nHeight = 1;
		}

		g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=" + nWidth + ",height=" + nHeight + ", top=0,left=0");

		if (strBodyType == 	"hwp")
			g_wndDocument.moveTo(-5000, 0);
*/
	}
	else
	{
	}
}

function onViewDocDetailInfo()
{
	var arrSelectedIndex = getSelectedIndexArray();
	if (arrSelectedIndex.length < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return;
	}
	else if (arrSelectedIndex.length > 1)
	{
		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
		return;
	}
	else
	{
		var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
		var strCabinet = document.getElementById("index" + arrSelectedIndex[0]).folderlist;
		var nDocStatus = document.getElementById("index" + arrSelectedIndex[0]).docstatus;
		var strLineName = document.getElementById("index" + arrSelectedIndex[0]).linename;
		var nSerialOrder = document.getElementById("index" + arrSelectedIndex[0]).serialorder;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
}

function onSearchDocument()
{
	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");

	if (strFolderList == CABINET_DEPTWAIT_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_DeptRecvCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&reftype=" + g_nRefType;
		window.open(strUrl, "CN_SendCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=200,top=0,left=0");
	}

	else
	{
		var strUrl = g_strBaseUrl + "list/search/CN_ApproveSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&reftype=" + g_nRefType;
		window.open(strUrl, "CN_ApproveSearch", "toolbar=no, resizable=no, status=yes, width=620, height=" + /*g_arrSrchPageHeight[g_nCabinetType]*/215 + ", top=0, left=0");
	}
}

function onSelectForm()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=draft&edittype=0";
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=yes, status=yes, width=800,height=470");
}

function onDeleteListItem()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	if (confirm(CONFIRM_DELETE_DOCUMENT) == false)
		return;

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	var strFunction = "confirmDelete";
	var nDoc = 0;
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);

		if (element.getAttribute("folderlist") != "SUBMITED" && element.getAttribute("folderlist") != "SUBMITEDAPPROVAL" && element.getAttribute("folderlist") != "SUBMITEDDOCFLOW")
		{
/*			if (i == g_nCurSelectedCount -1)
			{
				strDocId += element.getAttribute("dataurl");
				strCabinet += element.getAttribute("folderlist");
				nDocStatus += element.getAttribute("docstatus");
				strLineName += element.getAttribute("linename");
				nSerialOrder += element.getAttribute("serialorder");
			}
			else
*/
			{
				strDocId += element.getAttribute("dataurl") + "^";
				strCabinet += element.getAttribute("folderlist") + "^";
				nDocStatus += element.getAttribute("docstatus") + "^";
				strLineName += element.getAttribute("linename") + "^";
				nSerialOrder += element.getAttribute("serialorder") + "^";
			}
		}
		else if (element.getAttribute("flowstatus") == "1" || element.getAttribute("flowstatus") == "6" ||
				element.getAttribute("docstatus") == "4" || element.getAttribute("docstatus") == "6" ||
				element.getAttribute("flowstatus") == "5" || element.getAttribute("flowstatus") == "15")
		{
/*
			if (i == g_nCurSelectedCount -1)
			{
				strDocId += element.getAttribute("dataurl");
				strCabinet += element.getAttribute("folderlist");
				nDocStatus += element.getAttribute("docstatus");
				strLineName += element.getAttribute("linename");
				nSerialOrder += element.getAttribute("serialorder");
			}
			else
*/
			{
				strDocId += element.getAttribute("dataurl") + "^";
				strCabinet += element.getAttribute("folderlist") + "^";
				nDocStatus += element.getAttribute("docstatus") + "^";
				strLineName += element.getAttribute("linename") + "^";
				nSerialOrder += element.getAttribute("serialorder") + "^";
			}
		}
		else
		{
			nDoc++;
		}
	}
	if (nDoc != 0)
		alert(ALERT_AMONG_SELECTED + nDoc + ALERT_CANNOT_DELETE);

	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveDeleteDocument.jsp?dataurl=" + strDocId + "&cabinet=" + strCabinet + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&returnFunction=" + strFunction;

	deleteDocument.location.href = strUrl;
}

function onRestoreListItem()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	if (confirm(CONFIRM_RESTORE_DOCUMENT) == false)
		return;

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	var strFunction = "confirmRestore";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveRestoreDocument.jsp?dataurl=" + strDocId + "&cabinet=" + strCabinet + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&returnFunction=" + strFunction;
	restoreDocument.location.href = strUrl;
}

function onPrint(strIsSearchMode)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectPrint1.jsp?baseurl=" + g_strBaseUrl + "&issearch=" + escape(strIsSearchMode);
	window.open(strUrl,"CN_ApproveSelectPrint","toolbar=no,resizable=no, status=yes, width=300,height=210");
}

function printList(strPrintType, strIsSearchMode)
{
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strListing = eleDataInfo.getAttribute("listing");
	var strFolderList = eleDataInfo.getAttribute("folderlist");

	var elePageInfo = document.getElementById("pageinfo");
	var nTotalCount = elePageInfo.getAttribute("validcount");

	var strUrl = g_strBaseUrl + "list/print/CN_ApprovePrintList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&cabinet=" + escape(strFolderList) + "&title=" + strTitle + "&listing=" + escape(strListing) + "&printtype=" + strPrintType + "&totalcount=" + nTotalCount + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&reftype=" + g_nRefType;
	window.open(strUrl, "CN_ApprovePrintList","toolbar=no, scrollbars=yes, resizable=yes, status=yes, width=800,height=600");
}

function changePage(pageIndex, strIsSearchMode)
{
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");

	var elePageInfo = document.getElementById("pageinfo");
	var nPageCount = parseInt(elePageInfo.getAttribute("pagecount"));
	var nBasePage = parseInt(elePageInfo.getAttribute("basepage"));
	var strDocsPerPage = parseInt(elePageInfo.getAttribute("docsperpage"));
	var strListing = eleDataInfo.getAttribute("listing");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strCurPage = "" + pageIndex;
	var strRefType = elePageInfo.getAttribute("reftype");

	var strUrl = "CN_ApproveList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&reftype=" + strRefType;

	document.location.href = strUrl;
}

function clearAllCheckbox()
{
	if (g_nValidTotal > 0)
	{
		for (var i = g_firstIndex; i <= g_lastIndex; i++)
		{
			var str = "check" + i;
			var element = document.getElementById(str);
			element.checked = false;
		}
	}
}

function checkAll()
{
//	var nCount = (g_nValidTotal > 0) ? g_lastIndex - g_firstIndex + 1 : 0;
	var elePageInfo = document.getElementById("pageinfo");
	var nCount = parseInt(elePageInfo.getAttribute("docitemcount"));

	if (nCount > 0)
	{
		var arrSelectedIndex = getSelectedIndexArray();

		if (arrSelectedIndex.length < nCount)
		{
			for (var i = g_firstIndex; i <= g_lastIndex; i++)
			{
				var str = "check" + i;

				var element = document.getElementById(str);
				element.checked = true;
			}
		}
		else	// arrSelectedIndex.length == nCount
		{
			for (var i = g_firstIndex; i <= g_lastIndex; i++)
			{
				var str = "check" + i;
				var element = document.getElementById(str);
				element.checked = false;
			}
		}
	}
}

function getSelectedIndexArray()
{
	var arrSelectedIndex = new Array();
	var nCount = 0;
	if (g_nValidTotal > 0)
	{
		for (var i = g_firstIndex; i <= g_lastIndex; i++)
		{
			var str = "check" + i;
			var element = document.getElementById(str);

			if (element != null)
			{
				if (element.checked)
				{
					arrSelectedIndex[nCount++] = i;
				}
			}
		}
	}
	return arrSelectedIndex;
}

function changeListing(strListing)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strRefType = elePageInfo.getAttribute("reftype");

	var strUrl = "CN_ApproveList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + "0" + "&listing=" + strListing + "&reftype=" + strRefType;

	document.location.href = strUrl;
}

function sortTable(strColSort, strIsSearchMode)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strListing = eleDataInfo.getAttribute("listing");
	var strRefType = elePageInfo.getAttribute("reftype");

	if (g_strSorting == strColSort)
	{
		if (g_strSortingFlag == g_strDesc)
			g_strSortingFlag = g_strAsc;
		else
			g_strSortingFlag = g_strDesc;
	}

	g_strSorting = strColSort;

	var strUrl = "CN_ApproveList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + strColSort + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&reftype=" + strRefType;
	document.location.href = strUrl;
}

// 20030528, icaha document switching
//function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc)
function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, nItemIndex)
{
	g_nCurDocIndex = nItemIndex;

	if (strIsSecurityDoc == "Y")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType;
		window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
	}
	else
	{
		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType);
	}
}

function onClickAttach(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, nItemIndex)
{
	g_nCurDocIndex = nItemIndex;

	if (strIsSecurityDoc == "Y")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType + "&returnfunction=onOpenAttach";
		window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
	}
	else
	{//CN_ApproveAttachDocument.jsp
		onOpenAttach(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType);
	}
}

function onOpenAttach(strDataUrl, strCabinet, nDocStatus, strLineName, nSerialOrder, strBodyType)
{
	var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
	frmAutoProc.location.href = strUrl;
}

function onOpenSelectedDocument()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;
/*
	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}
*/
	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return;
	}
//	else if (g_nCurSelectedCount > 1)
//	{
//		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
//		return;
//	}

	g_nCurSelectedArrIndex = 0;

	var element = document.getElementById("index" + arrSelectedIndex[g_nCurSelectedArrIndex]);

	var listIndex = element.getAttribute("dataurl");
	var strFolderList = element.getAttribute("folderlist");
	var nDocStatus = element.getAttribute("docstatus");
	var strLineName = element.getAttribute("linename");
	var nSerialOrder = element.getAttribute("serialorder");
	var strSecurity = element.getAttribute("security");
	var strBodyType = element.getAttribute("bodytype");

	g_nCurDocIndex = arrSelectedIndex[g_nCurSelectedArrIndex];

	if (strSecurity == "Y")
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType;
		window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
	}
	else
	{
// 2003.10.07 수정
		if (g_strFolderList == CABINET_DEPTWAIT_REAL)
		{
			strLineName = strLineName + "-" + nSerialOrder;
			nSerialOrder = 0;
		}
// 2003.10.07 수정

		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType);
	}
}

function onAgreeApprove()
{
	if (g_bAgreeApprove == true)
	{
		return;
	}

	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	if (confirm(CONFIRM_APPROVE_AGREE) == false)
		return;


	if (g_strUseFP == "Y")
	{
		var strReturnFunction = "doAgreeApprove()";
		onCheckFPPassword(strReturnFunction);
	}

	else if (g_strUsePassword == "Y")
	{
		var strReturnFunction = "doAgreeApprove()";
		onCheckPassword(strReturnFunction);
	}

	else
		doAgreeApprove();
}

function doAgreeApprove()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	var strFunction = "confirmApprove";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	g_bAgreeApprove = true;
	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoProcess.jsp?dataurl=" + strDocId + "&cabinet=" + strCabinet + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&returnFunction=" + strFunction;
	autoProcess.location.href = strUrl;
}

function onCheckPassword(strFunction)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePWDialog.jsp?function=" + strFunction + "&location=list";
	window.open(strUrl,"Approve_ConfirmPW","toolbar=no,resizable=no, status=yes, width=220,height=110");
}

function onCheckFPPassword(strFunction)
{
//	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveFPCheckDialog.jsp?function=" + strFunction + "&type=" + nType;
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveFPCheckDialog.jsp?function=" + strFunction + "&location=list";;
	frmTransform.location.href = strUrl;
}

function setExistDevice(bDevice, strFunction)	// 지문인식 장치 존재 유무
{
	if (bDevice == false)			//지문사용 설정, 지문장치 없는 경우 결재비밀번호 입력창
	{
//		var strReturnFunction = "prepareSendApproval(" + nType + ")";
//		onCheckPassword(strReturnFunction);
		onCheckPassword(strFunction);
	}
}

function confirmApprove(nProcessCount)
{
	alert(nProcessCount + ALERT_PROCESS_AGREEAPPROVAL);
	g_bAgreeApprove = false;
	document.location.reload();
}

function confirmDelete(nProcessCount, bResult)
{
	if (bResult == true)
		alert(nProcessCount + ALERT_PROCESS_DELETEAPPROVAL);
	else if (bResult == false && nProcessCount == 0)
	{
	}
	else
		alert(ALERT_PROCESS_FAILURE);

	document.location.reload();
}

function confirmRestore(nProcessCount, bResult)
{
	if (bResult == true)
		alert(nProcessCount + ALERT_PROCESS_RESTOREAPPROVAL);
	else
		alert(ALERT_PROCESS_FAILURE);

	document.location.reload();
}

function onFindUserInfo(strUserID)
{
	if (strUserID == "")
	{
		alert(ALERT_NOT_FOUNT_USERINFO);
		return;
	}

	var strUrl = g_strBaseUrl + "org/CN_ViewUserInfo.jsp?userid=" + g_strUserId + "&searchuserid=" + strUserID;
	window.open(strUrl,"Org_ViewUserInfo","toolbar=no,resizable=no, status=yes, width=600,height=470");
}

function onFindDeptInfo(strDeptCode)
{
/*
	if (strDeptCode == "")
	{
		alert(ALERT_NOT_FOUNT_DEPTINFO);
		return;
	}
	else
		alert(strDeptCode);
*/
}

function onDirectEnforceDocument(nEdittype)
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=enforce&edittype=10";
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=yes, width=600,height=370");
}

//related
function onSendToRelatedSystem()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return;
	}
//for MOJHOMEPAGE
	else if (arrSelectedIndex.length > 1)
	{
		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
		return;
	}

	var strDocId = "", strCabinet = "", nDocStatus = "", strLineName = "", nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (element.getAttribute("docstatus") != "10" && element.getAttribute("docstatus") != "20")
		{
//alert(g_strFolderList);
			alert(ALERT_CAN_SEND_TO_ONLY_COMPLETED);
			return;
		}
//for MOJHOMEPAGE
//		if (element.getAttribute("annstatus") != "1")
//		{
//			alert(ALERT_CAN_SEND_TO_ONLY_ANNOUNCE_WAIT);
//			return;
//		}

		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	var strCabinetType = g_strFolderList;
	var strUrl = g_strBaseUrl + "related/dialog/CN_RelatedSelectSystem.jsp?relatedtype=APPR&setmode=1&dataurl=" + strDocId + "&cabinettype=" + strCabinetType;
	window.open(strUrl, "Related_SelectSystem", "toolbar=no, resizable=no, status=yes, width=265, height=270");
}

function addRelatedSystem(strSystemID, strProcessType, strSettingType, strSendingType, strRelatedType, strAgentID, strSystemData)
{
	var strTableType = "1"; //approve
	var strExtendedData = strSystemID + "^" + strProcessType + "^" + strSettingType + "^" + strSendingType + "^" + strRelatedType + "^" + strAgentID + "^" + strTableType;
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	if (objXMLDom.loadXML(strSystemData))
	{
		if (strSystemID == "SDSDM")
		{
			var objDocument = objXMLDom.selectSingleNode("*//DOCUMENT");
			strSystemData = objDocument.xml;
		}
	}
	else
		return null;

	document.getElementById("hidSystemData").value = strSystemData;
	document.getElementById("hidExtendedData").value = strExtendedData;

	return "true";
}

function getReleatedSystemDataObj(objRelatedSystem)
{
	return "true";
}

function doSendToSystem(strSystemID)
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	document.getElementById("hidDataUrl").value = strDocId;
	var strUrl = g_strBaseUrl + "related/action/CN_RelatedSendToSystem.jsp";
	submitForm(strUrl);
}

function submitForm(strUrl)
{
	var objForm = document.getElementById("formData");
	objForm.target = "frmAutoProc";
	objForm.action = strUrl;
	objForm.submit();
}

function completedSendTo(strResult, nTotal)
{
	var strMsg = "";
	if (strResult == "")
	{
		strMsg = nTotal + ALERT_SEND_TO;
		var nFailedCnt = g_nCurSelectedCount - nTotal;
		if (nFailedCnt > 0)
			strMsg += "/" + nFailedCnt + ALERT_CANNOT_SEND_TO;
		alert(strMsg);
	}
	else
	{
		strMsg = g_nCurSelectedCount - nTotal + ALERT_FAILED_TO_SEND;
		alert(strMsg);
	}

	document.location.reload();
}

function getDocID()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	var strDocId = "", strCabinet = "", nDocStatus = "", strLineName = "", nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);

		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}
	return strDocId;
}

function getFlowStatus()
{
	return 1;
}

function doSaveBody4RelatedDoc()
{
	return true;
}

function getOpenLocale()
{
	return "LIST";
}

/// document switching ...
function isPrevDocumentAvailable()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
				return true;
		}
	}
	return false;
}

function isNextDocumentAvailable()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
			return true;
		}
	}
	return false;
}

function getPrevElement()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
				return element;
		}

	}
	return null;
}

function getNextElement()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
				return element;
		}
	}
	return null;
}

function moveCurDocIndexPrev()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
				break;
		}
	}
	g_nCurDocIndex = nIndex;
}

function moveCurDocIndexNext()
{
	var nIndex = g_nCurDocIndex;
	var nCurSelectedCount = getSelectedCount();

	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
		{
			if (nCurSelectedCount == 0 || document.getElementById("check" + nIndex).checked == true)
				break;
		}
	}
	g_nCurDocIndex = nIndex;
}

function getSelectedCount()
{
	var arrSelectedIndex = getSelectedIndexArray();
	return arrSelectedIndex.length;
}

function invalidateCurDoc()
{
	var element = document.getElementById("index" + g_nCurDocIndex);
	if (element != null)
		element.setAttribute("isexist", "false");
}

//for 법무부
function onSetListLine()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return;
	}
	var strDocId = "", strCabinet = "", nDocStatus = "", strLineName = "", nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (element.getAttribute("docstatus") != "11")
		{
			alert(ALERT_CAN_SEND_TO_ONLY_CHARGER);
			return;
		}

		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	var strCabinetType = g_strFolderList;
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSetLine.jsp?setmode=LIST&cabinet=" + strCabinetType + "&dataurl=" + strDocId;
	window.open(strUrl,"Approve_SetApproveLine","toolbar=no,resizable=no, status=yes, width=600,height=540");
}

// 기록물철
function onEditArchiveInfo()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		if (element.getAttribute("docstatus") != "11")
		{
			alert(ALERT_CAN_SEND_TO_ONLY_CHARGER);
			return;
		}
	}

	var strUrl = g_strBaseUrl + "dm/govjsp/DM_GovWrkSelectFrame.jsp";
	var strRetUrl = g_strBaseUrl + "approve/action/CN_ApproveGetWrkValue.jsp";

	strUrl += "?PAGE=APR&OPTION=CLP&MODAL=N&TARGET=frmTransform&WRKALL=Y&RETURL=" + strRetUrl +"&DPTID=" + g_strDeptID;
	window.open(strUrl,"Approve_ArchiveInfo","toolbar=no,resizable=no, status=yes, width=350,height=420");
//setWrkValue("A1", "구라", "1", "1");
}

function setWrkValue(clpid, clpname, savid, savname)
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

//	if (g_nCurSelectedCount < 1)
//	{
//		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
//		return;
//	}

	var strDocId = "", strCabinet = "", nDocStatus = "", strLineName = "", nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
//		if (element.getAttribute("docstatus") != "11")
//		{
//			alert(ALERT_CAN_SEND_TO_ONLY_CHARGER);
//			return;
//		}

		if (i == g_nCurSelectedCount -1)
		{
			strDocId += element.getAttribute("dataurl");
			strCabinet += element.getAttribute("folderlist");
			nDocStatus += element.getAttribute("docstatus");
			strLineName += element.getAttribute("linename");
			nSerialOrder += element.getAttribute("serialorder");
		}
		else
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
	}

	document.getElementById("hidExtendedData").value = clpid + "^" + clpname + "^" + savid + "^" + savname;
	document.getElementById("hidDataUrl").value = strDocId;
	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveSetArchive.jsp";
	submitForm(strUrl);
}

function completedSetArchive(strResult, nTotal)
{
	var strMsg = "";
	if (strResult == "")
	{
		strMsg = nTotal + ALERT_EXECUTE_ORDER;
		var nFailedCnt = g_nCurSelectedCount - nTotal;
		if (nFailedCnt > 0)
			strMsg += "/" + nFailedCnt + ALERT_CANNOT_EXECUTE;
		alert(strMsg);
	}
	else
	{
		strMsg = g_nCurSelectedCount - nTotal + ALERT_FAILED_TO_EXECUTE;
		alert(strMsg);
	}

	document.location.reload();
}
