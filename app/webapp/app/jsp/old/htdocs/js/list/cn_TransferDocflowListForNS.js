var g_wndDocument = null;
// 20030528, icaha document switching
var g_nCurDocIndex;

function initialize(strValidYear)
{
	var element = document.getElementById("pageinfo");
	if (element != null)
	{
		var nCurPage = element.curpage;
		var nMessagePerPage = element.docsperpage;
		g_nValidTotal = element.validcount;

		g_firstIndex = 0;
		g_lastIndex = Math.min(g_nValidTotal - 1, parseInt(g_docsPerPage) - 1);

		if (g_lastIndex < 0)
			g_lastIndex = 0;
	}

	var objListBound = document.getElementById("listbound");
	if (objListBound != null)
	{
		var arrValidYear = strValidYear.split("^");
		var strStartYear = arrValidYear[0];
		var strEndYear = arrValidYear[1];
		var nCount = parseInt(strEndYear) - parseInt(strStartYear) + 1;
		var strYear = "";

		for (var i = 0; i < nCount + 1 ; i++)
		{
			if (objListBound[i].value == g_strSearchYear)
			{
				{
					objListBound.selectedIndex = i;
					break;
				}
			}
		}
	}
}

function openNewDocument(strBodyType, strFileName)
{
	if (strFileName != "")
	{
		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?bodytype=" + strBodyType + "&formurl=" + strFileName + "&edittype=10";

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
}

function onNewEnforceDocument()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=enforce";
	window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=no, width=600,height=370");
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
	else if (g_nCurSelectedCount > 1)
	{
		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
		return;
	}

	g_nCurSelectedArrIndex = 0;

	var element = document.getElementById("index" + arrSelectedIndex[g_nCurSelectedArrIndex]);

	var listIndex = element.getAttribute("dataurl");
	var strFolderList = element.getAttribute("folderlist");
	var nDocStatus = element.getAttribute("docstatus");
	var strLineName = element.getAttribute("linename");
	var nSerialOrder = element.getAttribute("serialorder");
	var strSecurity = element.getAttribute("security");
	var strDocCategory = element.getAttribute("doccategory");
	var strBodyType = element.getAttribute("bodytype");

	g_nCurDocIndex = arrSelectedIndex[g_nCurSelectedArrIndex];

	if (strFolderList == TRANSFER_LEDGER_REGI_REAL)
	{
		if (strSecurity == "Y")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType;
			window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
		}
		else
			onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
	else
	{
		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
}

function onOpenMessage(strDataUrl, strCabinet, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory)
{
	if (strDocCategory.indexOf("C") != -1)
	{
//		var strFormUrl = g_strBaseUrl + "Docflow/CivilTask/CivilTask_ReadDoc.asp";
//		var strUrl = g_strBaseUrl + "Docflow/CivilTask/CivilTask_ReadDoc.asp?dataurl=" + strDataUrl + "&amp;formurl=" + strFormUrl;
//		window.open(strUrl,'Docflow_RegiDoc','toolbar=no,resizable=no, status=yes,width=620,height=620,top=0,left=0');

		alert("추후 제공 예정입니다.");
	}

	else if (strDocCategory.indexOf("O") != -1)
	{
		if (strCabinet == TRANSFER_LEDGER_REGI_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
		}
		else if (strCabinet == TRANSFER_LEDGER_RECV_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
		}

		else if (strCabinet == TRANSFER_LEDGER_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowDistViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_DistDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
		}
	}

	else if (strBodyType.indexOf("pdf") != -1)
	{
		onOpenPDF(strDataUrl, strCabinet, strBodyType);
	}

	else
	{
		if (strBodyType == null || strBodyType == "")
		{
			alert(ALERT_NOT_FOUND_BODYTYPE);
			return;
		}

		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);

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
}

function onViewDocDetailInfo()
{
	if (checkSelectedItems())
	{
		var arrSelectedIndex = getSelectedIndexArray();

		var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
		var strCabinet = document.getElementById("index" + arrSelectedIndex[0]).folderlist;
		var nDocStatus = document.getElementById("index" + arrSelectedIndex[0]).docstatus;
		var strLineName = document.getElementById("index" + arrSelectedIndex[0]).linename;
		var nSerialOrder = document.getElementById("index" + arrSelectedIndex[0]).serialorder;

		if (strCabinet == TRANSFER_LEDGER_RECV_REAL || strCabinet == TRANSFER_LEDGER_DIST_REAL)
			strLineName = "1";

		if (strCabinet == TRANSFER_LEDGER_REGI_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
		}

		else if (strCabinet == TRANSFER_LEDGER_RECV_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
		}

		else if (strCabinet == TRANSFER_LEDGER_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=DIST&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_DocflowIngDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
		}

		else
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
		}
	}
}

function changePage(pageIndex, strIsSearchMode)
{
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");

	var elePageInfo = document.getElementById("pageinfo");
	var nPageCount = parseInt(elePageInfo.getAttribute("pagecount"));
	var nBasePage = parseInt(elePageInfo.getAttribute("basepage"));
	var strMsgPerPage = parseInt(elePageInfo.getAttribute("msgperpage"));
	var strListing = eleDataInfo.getAttribute("listing");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strCurPage = "" + pageIndex;
	var strRefType = elePageInfo.getAttribute("reftype");

	var strUrl = "CN_TransferDocflowListForNS.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&searchyear=" + g_strSearchYear + "&reftype=" + strRefType;

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

function onAddListItem()
{
	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");

	if (strFolderList == TRANSFER_LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowDistAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}

	else
	{
	alert("추후 제공 예정입니다.");
	}
}

function onReceiveDocument()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}
}

function onAppointCharger()
{
	if (!checkSelectedItems())
		return;
/*
	var strTargetId = "";
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=8&targetid=" + strTargetId;
	window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
*/
}

function onSendToApprover(nType)
{
alert("추후 제공 예정입니다.");
}

function onCallbackDocument()
{
	if (!checkSelectedItems())
		return;
	else
	{
		var arrSelectedIndex = getSelectedIndexArray();

		var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
		var strCabinet = document.getElementById("index" + arrSelectedIndex[0]).folderlist;
		var nDocStatus = document.getElementById("index" + arrSelectedIndex[0]).docstatus;
		var strLineName = document.getElementById("index" + arrSelectedIndex[0]).linename;
		var nSerialOrder = document.getElementById("index" + arrSelectedIndex[0]).serialorder;

		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiConfirmRecipient.jsp?type=CALLBACK&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_ApproveSelectPrint2", "toolbar=no,resizable=no, status=yes,width=600,height=390,top=0,left=0");
	}
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
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
		}
		else if (element.getAttribute("flowstatus") == "1" || element.getAttribute("flowstatus") == "6" || element.getAttribute("docstatus") == "4")
		{
			strDocId += element.getAttribute("dataurl") + "^";
			strCabinet += element.getAttribute("folderlist") + "^";
			nDocStatus += element.getAttribute("docstatus") + "^";
			strLineName += element.getAttribute("linename") + "^";
			nSerialOrder += element.getAttribute("serialorder") + "^";
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

function onConfirmPublicView()
{
	if (!checkSelectedItems())
		return;

	else
	{
		var arrSelectedIndex = getSelectedIndexArray();
		var strIsPost = document.getElementById("index" + arrSelectedIndex[0]).ispost;

		if (strIsPost != "Y")
			alert(ALERT_ISNOT_POSTED);
		else
		{
			var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvConfirmPublic.jsp?docid=" + strDataUrl;
			window.open(strUrl,"CN_DocflowPublic", "toolbar=no,resizable=no, status=yes,width=400,height=350,top=0,left=0");
		}
	}
}

function onRejectMovement()
{
	if (!checkSelectedItems())
		return;
}

function onModifyListItem()
{
//	if (!checkSelectedItems())
//		return;

	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");

	if (strFolderList == TRANSFER_LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiViewItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvViewItem.jsp";
		window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowDistViewItem.jsp";
		window.open(strUrl,"Docflow_DistDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
}

function checkSelectedItems()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return false;
	}
	else if (g_nCurSelectedCount > 1)
	{
		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
		return false;
	}

	return true;
}

function onConfirmReception()
{
	if (!checkSelectedItems())
		return;
	else
	{
		var arrSelectedIndex = getSelectedIndexArray();

		var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
		var strCabinet = document.getElementById("index" + arrSelectedIndex[0]).folderlist;
		var nDocStatus = document.getElementById("index" + arrSelectedIndex[0]).docstatus;
		var strLineName = document.getElementById("index" + arrSelectedIndex[0]).linename;
		var nSerialOrder = document.getElementById("index" + arrSelectedIndex[0]).serialorder;

		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiConfirmRecipient.jsp?type=CONFIRM&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_DocflowRegiConfirmRecipient", "toolbar=no,resizable=no, status=yes,width=600,height=390,top=0,left=0");
	}
}

function onPrint(strIsSearchMode)
{

	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectPrint3.jsp?cabinet=" + escape(strFolderList) + "&title=" + strTitle + "&issearch=" + escape(strIsSearchMode);
	window.open(strUrl,"CN_ApproveSelectPrint3", "toolbar=no,resizable=no, status=yes,width=400,height=300,top=0,left=0");

//	printList("prt", 0,0, false);
}

function printList(strPrintType, strSearchItem, strSearchValue, strIsSearchMode)
{
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strListing = eleDataInfo.getAttribute("listing");
	var strFolderList = eleDataInfo.getAttribute("folderlist");

	var elePageInfo = document.getElementById("pageinfo");
	var nTotalCount = elePageInfo.getAttribute("validcount");

	var strUrl = g_strBaseUrl + "list/print/CN_TransferDocflowPrintListForNS.jsp?userid=" + escape(g_strUserID)
				 + "&deptid=" + escape(g_strDeptID) + "&cabinet=" + escape(strFolderList)
				 + "&title=" + strTitle + "&printtype=" + strPrintType + "&totalcount=" + nTotalCount
				 + "&searchitem=" + strSearchItem + "&searchvalue=" + strSearchValue
				 + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList
				 + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems
				 + "&searchyear=" + g_strSearchYear + "&reftype=" + g_nRefType;

	window.open(strUrl, "CN_TransferDocflowPrintListForNS","toolbar=no, scrollbars=yes, resizable=yes, status=yes, width=800,height=600");
}

function onSearch()
{
	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");
	var strTargetPage = "CN_TransferDocflowListForNS.jsp";

	if (strFolderList == TRANSFER_LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_TRegiLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&targetpage=" + strTargetPage;
		window.open(strUrl, "CN_TRegiLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=260,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_TRecvLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&targetpage=" + strTargetPage;
		window.open(strUrl, "CN_TRecvLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=230,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_TDistLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&targetpage=" + strTargetPage;
		window.open(strUrl, "CN_TDistLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=200,top=0,left=0");
	}
	else if (strFolderList == TRANSFER_CABINET_SEND_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_TSendCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&targetpage=" + strTargetPage;
		window.open(strUrl, "CN_SendCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=260,top=0,left=0");
	}

}

function changeListing(strListing)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	var strFolderList = eleDataInfo.getAttribute("folderlist");

	var strUrl = "CN_TransferDocflowListForNS.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing;
	document.location.href = strUrl;
}

function changeSearchYear(strYear)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");

	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	strCurPage = 0;

	var strUrl = "CN_TransferDocflowListForNS.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=TOTAL" + "&searchyear=" + strYear;
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
	var strUrl = "CN_TransferDocflowListForNS.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + strColSort + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&searchyear=" + g_strSearchYear + "&reftype=" + strRefType;
	document.location.href = strUrl;
}

// 20030528, icaha document switching
//function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, strDocCategory, strIsPubDoc, strIsAdminMIS)
function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, strDocCategory, strIsPubDoc, strIsAdminMIS, nItemIndex)
{
	// 20030824, icaha
	g_nCurDocIndex = nItemIndex;

	if (strFolderList == TRANSFER_LEDGER_REGI_REAL)
	{
		if (strIsSecurityDoc == "Y")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType + "&doccategory=" + strDocCategory;
			window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
		}
		else
		{
			if (strIsAdminMIS == "Y")
			{
				var srcElement;
				srcElement = window.event.srcElement;

				while(!(srcElement.tagName == "TR"))
				{
					srcElement = srcElement.parentElement;
				}
				if (srcElement.id.indexOf("index") != -1)
				{
					g_strAdminMIS = srcElement.id;
				}

				else
				{
					g_strAdminMIS = "";
				}

				var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveForm.jsp?formlisttype=adminmis";
				window.open(strUrl,"Approve_Form","toolbar=no,resizable=no, status=yes, width=600,height=370");
			}

			else
				onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
		}
	}
	else
	{
		if (strFolderList == TRANSFER_LEDGER_RECV_REAL || strFolderList == TRANSFER_LEDGER_DIST_REAL)
			strLineName = "1";

		onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}

}

function onFindUserInfo(strUserID)
{
	if (strUserID == "")
	{
		alert(ALERT_NOT_FOUNT_USERINFO);
		return;
	}

	else
		alert(strUserID);
}

function onOpenPubDoc(strBodyType, strFormUrl)
{
	var objTR = document.getElementById(g_strPubDocID);

	if (objTR != null)
	{
		var strDataUrl = objTR.dataurl;
		var strFolderList = objTR.folderlist;
		var nDocStatus = objTR.docstatus;
		var strLineName = objTR.linename;
		var nSerialOrder = objTR.serialorder;
		var strDocCategory = objTR.doccategory;

		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strFolderList) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType) + "&formurl=" + escape(strFormUrl);

		if (g_wndDocument != null)
			g_wndDocument.close();

/*
		if (strBodyType == "hwp")
			g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=50,height=50, top=0,left=0");
		else
*/
			g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545, top=0,left=0");
	}
	else
	{
		alert("Failed to find PubDoc");
	}
}

function onOpenAdminMIS(strBodyType, strFileName, nEditType, strFormName, strFormUsage, strLogoName, strSymbolName)
{

	var objTR = document.getElementById(g_strAdminMIS);

	if (objTR != null)
	{
		var strDataUrl = objTR.dataurl;
		var strFolderList = objTR.folderlist;
		var nDocStatus = objTR.docstatus;
		var strLineName = objTR.linename;
		var nSerialOrder = objTR.serialorder;
		var strDocCategory = objTR.doccategory;

		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strFolderList) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType) + "&formurl=" + strFileName + "&edittype=" + nEditType + "&formname=" + strFormName + "&formusage=" +strFormUsage + "&logoname=" + strLogoName + "&symbolname=" + strSymbolName;

		if (g_wndDocument != null)
			g_wndDocument.close();

/*
			if (strBodyType == "hwp")
				g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=50,height=50, top=0,left=0");
			else
*/
				g_wndDocument = window.open(strUrl,"CN_ApproveDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545, top=0,left=0");

	}
	else
	{
		alert("Failed to find AdminMIS Document");
	}
}

/*
function onFindDeptInfo(strDeptCode)
{
	if (strDeptCode == "")
	{
		alert(ALERT_NOT_FOUNT_DEPTINFO);
		return;
	}
	else
		alert(strDeptCode);
}
*/

function onModifyClassNum()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_ONE_DOCUMENT);
		return;
	}
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
		var eleTD = element.getElementsByTagName("TD");
		if (eleTD[3].innerText != "")
		{
			alert(ALERT_CANNOT_SET_DOCNUM);
			return;
		}
	}

	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveClassInfo.jsp";
	window.open(strUrl,"Approve_ClassInfo","toolbar=no,resizable=no, status=yes, width=350,height=400");
}

function getClassInfo()
{
	var strValue = "";
	return strValue;
}

function getClassNumberID()
{
	var strValue = "";
	return strValue;
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

	if (strNumber.length > 8)
		strNumber = strNumber.substring(0, 8);

	if (strClassName.length > 16)
		strClassName = strClassName.substring(0, 16);

//	setOrgSymbol(strClassName);
//	setClassNumber(strNumber);
//	setSerialNumber(strSerialNumber);
	document.getElementById("hidOrgSymbol").value = strClassName;
	document.getElementById("hidClassNumber").value = strNumber;
}

function setConserve(strRetentionDate)
{
}

function setClassNumberID(strID)
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

	document.getElementById("hidClassNumberID").value = strID;
	document.getElementById("hidDataUrl").value = strDocId;
	submitForm();
}

function submitForm()
{
//	document.formData.target = "frmHidden";
//	document.formData.action = g_strBaseUrl + "related/action/CN_RelatedSendTo.jsp";
//	document.formData.submit();

	var objForm = document.getElementById("formData");
	objForm.target = "frmHidden";
	objForm.action = g_strBaseUrl + "related/action/CN_RelatedSendTo.jsp";
	objForm.submit();
}


function completedSendTo(strResult, nTotal)
{
	if (strResult == "")
		alert(nTotal + ALERT_SET_DOCINFO);
	else
		alert(ALERT_NOT_SET_DOCINFO);

	document.location.reload();
}

/// document switching ...
function isPrevDocumentAvailable()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			return true;

	}
	return false;
}

function isNextDocumentAvailable()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			return true;

	}
	return false;
}

function getPrevElement()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			return element;

	}
	return null;
}

function getNextElement()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			return element;

	}
	return null;
}

function moveCurDocIndexPrev()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex > g_firstIndex)
	{
		nIndex --;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			break;

	}
	g_nCurDocIndex = nIndex;
}

function moveCurDocIndexNext()
{
	var nIndex = g_nCurDocIndex;
	while (nIndex < g_lastIndex)
	{
		nIndex ++;
		var element = document.getElementById("index" + nIndex);
		if (element != null && element.getAttribute("isexist") == "true")
			break;
	}
	g_nCurDocIndex = nIndex;
}

function invalidateCurDoc()
{
	var element = document.getElementById("index" + g_nCurDocIndex);
	if (element != null)
		element.setAttribute("isexist", "false");
}

function onOpenPDF(strDataUrl, strCabinet, strBodyType)
{
	var strUrl = g_strBaseUrl + "transfer/document/CN_TransferDocument.jsp?dataurl=" + escape(strDataUrl) +  "&cabinet=" + escape(strCabinet) + "&bodytype=" + strBodyType;
//	var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
	if (g_wndDocument != null)
		g_wndDocument.close();

	g_wndDocument = window.open(strUrl,"CN_TransferDocument","toolbar=no,resizable=yes, status=yes, width=790,height=545, top=0,left=0");
}