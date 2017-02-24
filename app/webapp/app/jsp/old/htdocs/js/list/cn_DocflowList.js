var g_wndDocument = null;
var g_bAgreeReceive = false;
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

	if (g_strOption159 == "1")
	{
		var objListBound = document.getElementById("listbound");
		var nInvestType = 0;
		if (g_strInvestType == 0)
			nInvestType = 0;
		else if (g_strInvestType == 1)
			nInvestType = 1;
		else if (g_strInvestType == 2)
			nInvestType = 2;
		if (objListBound != null)
			objListBound.selectedIndex = nInvestType;
	}

}

/*
function onNewMemoApprove()
{
	var strWordList = "<%=strWordList%>";
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
		openNewDocument(strBodyType, strUrl);
	}
}
*/
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

function onViewSubDeptDocList()
{
	var strUrl = g_strBaseUrl + "org/dialog/CN_OrgTree.jsp?deptid=" + g_strDeptSelfID + "&orgtype=3&treetype=0";
	window.open(strUrl, "Org_SetDept", "toolbar=no, resizable=no, status=yes, width=200, height=250");
}

function setSubDeptInfo(strDeptID, strDeptName)
{
	g_strDeptID = strDeptID;
	document.location.href = g_strBaseUrl + "list/CN_DocflowList.jsp?depttype=sub&folderlist=" + g_strFolderList + "&title=" + g_strTitle + "&userid=" + g_strUserID + "&deptid=" + g_strDeptID + "&deptname=" + strDeptName + "&reftype=" + g_nRefType + "&businessid=" + g_strBusinessID + "&rolecode=";
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
	var strDocCategory = element.getAttribute("doccategory");
	var strBodyType = element.getAttribute("bodytype");
	var strPostPosition = element.getAttribute("postposition");

	g_nCurDocIndex = arrSelectedIndex[g_nCurSelectedArrIndex];

	if (strFolderList == CABINET_INVESTIGATION_REAL || strFolderList == CABINET_SEND_REAL || strFolderList == LEDGER_REGI_REAL)
	{
		if (strSecurity == "Y")
		{
	//		strDataUrl = element.dataurl;
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType; //dataurl=" + escape(strDataUrl);
			window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
		}
		else
			onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
	else
	{
		if (strFolderList == CABINET_PUBLICPOST_REAL)
		{
			if (strPostPosition == LEDGER_REGI_REAL)
				strFolderList = "REGIPUBLICPOST";
			else if (strPostPosition == LEDGER_RECV_REAL)
				strFolderList = "RECVPUBLICPOST";
		}

		if (strFolderList == LEDGER_DEREGILEDGER_REAL)
			strFolderList = LEDGER_RECV_REAL;

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
		if (strCabinet == LEDGER_REGI_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
		}
		else if (strCabinet == LEDGER_RECV_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
		}

		else if (strCabinet == LEDGER_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowDistViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			window.open(strUrl,"Docflow_DistDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
		}
		else if (strCabinet == CABINET_RECV_REAL)
		{
			if (g_strOption148 == "0")
			{
				var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvViewItem.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
				window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
			}
			else
			{
				var strUrl = g_strBaseUrl + "dm/govjsp/DM_GovRecRegister.jsp?PAGE=DSCREC&CLOSEWIN=Y&RECID=" + escape(strDataUrl) + "&DPTID=" + escape(g_strDeptID);
				window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=600,height=650,top=0,left=0");
			}
		}
	}
	else
	{
		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);

		if (g_strDeptID != g_strDeptSelfID)
			strUrl += "&edittype=51";

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
		var strPostPostion = document.getElementById("index" + arrSelectedIndex[0]).postposition;

		if (strCabinet == LEDGER_RECV_REAL || strCabinet == LEDGER_DIST_REAL || strCabinet == LEDGER_DEREGILEDGER_REAL)
			strLineName = "1";
		else if (strCabinet == CABINET_RECV_REAL || strCabinet == CABINET_DIST_REAL)
			strLineName = "1";
		else if (strCabinet == CABINET_PUBLICPOST_REAL)
			strLineName = "1";

		if (strCabinet == CABINET_SEND_REAL || strCabinet == CABINET_INVESTIGATION_REAL || strCabinet == LEDGER_REGI_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
		}
		else if (strCabinet == LEDGER_RECV_REAL || strCabinet == LEDGER_DEREGILEDGER_REAL)
		{
			strCabinet = LEDGER_RECV_REAL;
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
		}
		else if (strCabinet == CABINET_RECV_REAL || strCabinet == CABINET_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=ING&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_DocflowIngDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
		}
		else if (strCabinet == LEDGER_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=DIST&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
			window.open(strUrl,"CN_DocflowIngDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
		}
		else if (strCabinet == CABINET_PUBLICPOST_REAL)
		{
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowPostDetail.jsp?dataurl=" + escape(strDataUrl) + "&postposition=" + escape(strPostPostion);
			window.open(strUrl,"CN_DocflowPostDetail","toolbar=no,resizable=no, status=yes, width=500,height=240");
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
	var strInvestType = eleDataInfo.getAttribute("investtype");
	var strSubDeptName = document.getElementById("hidSubDeptName").value;
	var strDeptType = "";
	if (strSubDeptName != "")
		strDeptType = "sub";
	var strUrl = "CN_DocflowList.jsp?depttype=" + strDeptType + "&userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&reftype=" + strRefType + "&investtype=" + strInvestType + "&deptname=" + strSubDeptName;

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

	if (strFolderList == LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
	}
	else if (strFolderList == LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
	else if (strFolderList == LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowDistAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
	else if (strFolderList.indexOf("CIVIL") != -1)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowCivilAddItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=620,height=620,top=0,left=0");
	}
	else
	{
	alert("추후 제공 예정입니다.");
	}
}

function onReceiveDocument()
{
	if (g_bAgreeReceive == true)
		return;

	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	var strFunction = "confirmReceive";

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

	g_bAgreeReceive = true;
	var strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoReceiveEnforce.jsp?dataurl=" + strDocId + "&cabinet=" + strCabinet + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&returnFunction=" + strFunction;
	autoProcess.location.href = strUrl;
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

//	alert("추후 제공 예정입니다.");
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
		else if (element.getAttribute("flowstatus") == "1" || element.getAttribute("flowstatus") == "6" || element.getAttribute("docstatus") == "4")
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

function confirmReceive(nProcessCount)
{
	alert(nProcessCount + ALERT_PROCESS_AGREEAPPROVAL);
	g_bAgreeReceive = false;
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

function onConfirmPublicView()
{
	if (!checkSelectedItems())
	{
		return;
	}
	else
	{
		var arrSelectedIndex = getSelectedIndexArray();
		var strIsPost = document.getElementById("index" + arrSelectedIndex[0]).ispost;

		if (strIsPost != "Y")
			alert(ALERT_ISNOT_POSTED);
		else
		{
			var eleDataInfo = document.getElementById("datainfo");
			var strFolderList = eleDataInfo.getAttribute("folderlist");
			var strPostPosition = "";

			if (strFolderList == LEDGER_REGI_REAL)
			{
				strPostPosition = "REGILEDGER";
			}
			else if (strFolderList == LEDGER_RECV_REAL)
			{
				strPostPosition = "RECVLEDGER";
			}
			else
			{
				strPostPosition = document.getElementById("index" + arrSelectedIndex[0]).postposition;
			}

			var strDataUrl = document.getElementById("index" + arrSelectedIndex[0]).dataurl;
			var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvConfirmPublic.jsp?docid=" + strDataUrl + "&postposition=" + strPostPosition;
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

	if (strFolderList == LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRegiViewItem.jsp";
		window.open(strUrl,"Docflow_RegiDoc","toolbar=no,resizable=no, status=yes,width=450,height=600,top=0,left=0");
	}
	else if (strFolderList == LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowRecvViewItem.jsp";
		window.open(strUrl,"Docflow_RecvDoc","toolbar=no,resizable=no, status=yes,width=450,height=380,top=0,left=0");
	}
	else if (strFolderList == LEDGER_DIST_REAL)
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

	if (strFolderList == CABINET_PUBLICPOST_REAL)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectPrint1.jsp?baseurl=" + g_strBaseUrl + "&cabinet=" + escape(strFolderList);
		window.open(strUrl,"CN_ApproveSelectPrint","toolbar=no,resizable=no, status=yes, width=300,height=210");
	}

	else
	{
//		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectPrint2.jsp?cabinet=" + escape(strFolderList) + "&title=" + strTitle;
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveSelectPrint2.jsp?cabinet=" + escape(strFolderList) + "&title=" + strTitle + "&issearch=" + escape(strIsSearchMode);
		window.open(strUrl,"CN_ApproveSelectPrint2", "toolbar=no,resizable=no, status=yes,width=400,height=300,top=0,left=0");
	}

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

//	var strUrl = g_strBaseUrl + "list/print/CN_DocflowPrintList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&cabinet=" + escape(strFolderList) + "&title=" + strTitle + "&listing=" + escape(strListing) + "&printtype=" + strPrintType + "&start=" + nStart + "&end=" + nEnd + "&totalcount=" + nTotalCount + "&datecondition=" + bDateCondition + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag;
	var strUrl = g_strBaseUrl + "list/print/CN_DocflowPrintList.jsp?userid=" + escape(g_strUserID)
				 + "&deptid=" + escape(g_strDeptID) + "&cabinet=" + escape(strFolderList)
				 + "&title=" + strTitle + "&printtype=" + strPrintType + "&totalcount=" + nTotalCount
				 + "&searchitem=" + strSearchItem + "&searchvalue=" + strSearchValue
				 + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList
				 + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems
				 + "&reftype=" + g_nRefType + "&businessid=" + g_strBusinessID;

	window.open(strUrl, "CN_DocflowPrintList","toolbar=no, scrollbars=yes, resizable=yes, status=yes, width=800,height=600");
}

function onSearch()
{
	var eleDataInfo = document.getElementById("datainfo");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");

	if (strFolderList == CABINET_INVESTIGATION_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_InvestCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_InvestCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=230,top=0,left=0");
	}
	else if (strFolderList == CABINET_SEND_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_SendCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_SendCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=240,top=0,left=0");
	}
	else if (strFolderList == CABINET_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_RecvCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_RecvCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=160,top=0,left=0");
	}
	else if (strFolderList == CABINET_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_DistCabinetSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_DistCabinetSearch", "toolbar=no, resizable=no, status=yes, width=420,height=160,top=0,left=0");
	}
	else if (strFolderList == LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_RegiLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList + "&businessid=" + g_strBusinessID;
		window.open(strUrl, "CN_RegiLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=240,top=0,left=0");
	}
	else if (strFolderList == LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_RecvLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_RecvLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=210,top=0,left=0");
	}
	else if (strFolderList == LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_DistLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_DistLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=180,top=0,left=0");
	}
	else if (strFolderList == CABINET_INSPECTION_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_InspLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_InspLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=210,top=0,left=0");
	}
	else if (strFolderList == LEDGER_TRANSFER_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_TransferLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_TransferLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=160,top=0,left=0");
	}
	else if (strFolderList.indexOf("CIVIL") != -1)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_CivilDeptSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_CivilDeptSearch", "toolbar=no, resizable=no, status=yes, width=420,height=220,top=0,left=0");
	}
	else if (strFolderList == LEDGER_DEREGILEDGER_REAL)
	{
		var strUrl = g_strBaseUrl + "list/search/CN_RecvLedgerSearch.jsp?title=" + strTitle + "&cabinet=" + strFolderList;
		window.open(strUrl, "CN_DeRegiLedgerSearch", "toolbar=no, resizable=no, status=yes, width=420,height=210,top=0,left=0");
	}
	else
	{
		alert("추후 제공 예정입니다.");
	}
}

function changeListing(strListing)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strInvestType = eleDataInfo.getAttribute("investtype");

	var strUrl = "CN_DocflowList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing;
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
	var strInvestType = eleDataInfo.getAttribute("investtype");

	if (g_strSorting == strColSort)
	{
		if (g_strSortingFlag == g_strDesc)
			g_strSortingFlag = g_strAsc;
		else
			g_strSortingFlag = g_strDesc;
	}

	g_strSorting = strColSort;
	var strUrl = "CN_DocflowList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + strColSort + "&sortingflag=" + g_strSortingFlag + "&issearch=" + strIsSearchMode + "&searchitemlist=" + g_strSearchItemList + "&searchvaluelist=" + g_strSearchValueList + "&searchitems=" + g_nSearchItems + "&reftype=" + strRefType + "&investtype=" + strInvestType;
	document.location.href = strUrl;
}

// 20030528, icaha document switching
//function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, strDocCategory, strIsPubDoc, strIsAdminMIS)
function onClickSubject(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, strDocCategory, strIsPubDoc, strIsAdminMIS, strPostPosition, nItemIndex)
{
	// 20030824, icaha
	g_nCurDocIndex = nItemIndex;

	if (strFolderList == CABINET_INVESTIGATION_REAL || strFolderList == CABINET_SEND_REAL || strFolderList == LEDGER_REGI_REAL)
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
		// modify by kkang - 20040609 check security number
		if (strIsSecurityDoc == "Y")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType + "&doccategory=" + strDocCategory;
			window.open(strUrl,"CN_ApprovePassword","toolbar=no,resizable=no, status=yes, width=210, height=160");
		}
		else
		{
			if (strFolderList == LEDGER_RECV_REAL || strFolderList == LEDGER_DIST_REAL || strFolderList == LEDGER_DEREGILEDGER_REAL)
				strLineName = "1";
			else if (strFolderList == CABINET_RECV_REAL || strFolderList == CABINET_DIST_REAL)
				strLineName = "1";
			else if (strFolderList == CABINET_PUBLICPOST_REAL && strPostPosition == "RECVLEDGER")
				strLineName = "1";

			if (strFolderList == CABINET_PUBLICPOST_REAL)
			{
				if (strPostPosition == LEDGER_REGI_REAL)
					strFolderList = "REGIPUBLICPOST";
				else if (strPostPosition == LEDGER_RECV_REAL)
					strFolderList = "RECVPUBLICPOST";
			}

			if (strFolderList == LEDGER_DEREGILEDGER_REAL)
				strFolderList = LEDGER_RECV_REAL;

			onOpenMessage(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
		}
	}

}

function onClickAttach(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strIsSecurityDoc, strDocCategory, strIsPubDoc, strIsAdminMIS, strPostPosition, nItemIndex)
{
	// 20030824, icaha
	g_nCurDocIndex = nItemIndex;

	if (strFolderList == CABINET_INVESTIGATION_REAL || strFolderList == CABINET_SEND_REAL || strFolderList == LEDGER_REGI_REAL)
	{
		if (strIsSecurityDoc == "Y")
		{
			var strUrl = g_strBaseUrl + "approve/dialog/CN_ApprovePassword.jsp?listindex=" + listIndex + "&folderlist=" + strFolderList + "&docstatus=" + nDocStatus + "&linename=" + strLineName + "&serialorder=" + nSerialOrder + "&bodytype=" + strBodyType + "&doccategory=" + strDocCategory + "&returnfunction=onOpenAttach";;
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
				onOpenAttach(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
		}
	}
	else
	{
		if (strFolderList == LEDGER_RECV_REAL || strFolderList == LEDGER_DIST_REAL || strFolderList == LEDGER_DEREGILEDGER_REAL)
			strLineName = "1";
		else if (strFolderList == CABINET_RECV_REAL || strFolderList == CABINET_DIST_REAL)
			strLineName = "1";
		else if (strFolderList == CABINET_PUBLICPOST_REAL && strPostPosition == "RECVLEDGER")
			strLineName = "1";

		if (strFolderList == CABINET_PUBLICPOST_REAL)
		{
			if (strPostPosition == LEDGER_REGI_REAL)
				strFolderList = "REGIPUBLICPOST";
			else if (strPostPosition == LEDGER_RECV_REAL)
				strFolderList = "RECVPUBLICPOST";
		}

		if (strFolderList == LEDGER_DEREGILEDGER_REAL)
			strFolderList = LEDGER_RECV_REAL;

		onOpenAttach(listIndex, strFolderList, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory);
	}
}

function onOpenAttach(strDataUrl, strCabinet, nDocStatus, strLineName, nSerialOrder, strBodyType, strDocCategory)
{
	if (strDocCategory.indexOf("C") != -1)
	{
		alert("추후 제공 예정입니다.");
	}
	else if (strDocCategory.indexOf("O") != -1)
	{
		if (strCabinet == LEDGER_REGI_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			frmAutoProc.location.href = strUrl;
		}
		else if (strCabinet == LEDGER_RECV_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			frmAutoProc.location.href = strUrl;
		}

		else if (strCabinet == LEDGER_DIST_REAL)
		{
			var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
			frmAutoProc.location.href = strUrl;
		}
		else if (strCabinet == CABINET_RECV_REAL)
		{
			if (g_strOption148 == "0")
			{
				var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);
				frmAutoProc.location.href = strUrl;
			}
			else
			{
				var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?PAGE=DSCREC&CLOSEWIN=Y&RECID=" + escape(strDataUrl) + "&DPTID=" + escape(g_strDeptID);
				frmAutoProc.location.href = strUrl;
			}
		}
	}
	else
	{
		var strUrl = g_strBaseUrl + "approve/document/CN_ApproveAttachDocument.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + nSerialOrder + "&bodytype=" + escape(strBodyType);

		if (g_strDeptID != g_strDeptSelfID)
			strUrl += "&edittype=51";

		if (g_wndDocument != null)
			g_wndDocument.close();

		frmAutoProc.location.href = strUrl;
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
	else if (arrSelectedIndex.length > 1)
	{
		alert(ALERT_SELECT_ONLY_ONE_DOCUMENT);
		return;
	}

	var strDocId = "", strCabinet = "", nDocStatus = "", strLineName = "", nSerialOrder = "";
	for (var i = 0; i < g_nCurSelectedCount; i++)
	{
		var element = document.getElementById("index" + arrSelectedIndex[i]);
//alert(element.outerHTML);
//		if (element.getAttribute("docstatus") != "10" && element.getAttribute("docstatus") != "20")
//		{
//			alert(ALERT_CAN_SEND_TO_ONLY_COMPLETED);
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
		strMsg = nTotal + ALERT_EXECUTE_ORDER;
		var nFailedCnt = g_nCurSelectedCount - nTotal;
		if (nFailedCnt > 0)
			strMsg += "/" + nFailedCnt + ALERT_CANNOT_SEND_TO;
		alert(strMsg);
	}
	else
	{
		strMsg = g_nCurSelectedCount - nTotal + ALERT_FAILED_TO_EXECUTE;
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
	if (g_strFolderList == "REGILEDGER")
		return 1;
	else
		return 9;
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


function onClickListTitle(strDocID, strFolder, nDocStatus, strDocLineName, nDocSerialOrder)
{
	var strDataUrl = strDocID;//document.getElementById("index" + arrSelectedIndex[0]).dataurl;
	var strCabinet = strFolder;//document.getElementById("index" + arrSelectedIndex[0]).folderlist;
	var nDocStatus = nDocStatus;//document.getElementById("index" + arrSelectedIndex[0]).docstatus;
	var strLineName = strDocLineName;//document.getElementById("index" + arrSelectedIndex[0]).linename;
	var nSerialOrder = nDocSerialOrder;//document.getElementById("index" + arrSelectedIndex[0]).serialorder;

	if (strCabinet == LEDGER_RECV_REAL || strCabinet == LEDGER_DIST_REAL)
		strLineName = "1";
	else if (strCabinet == CABINET_RECV_REAL || strCabinet == CABINET_DIST_REAL)
		strLineName = "1";
	else if (strCabinet == CABINET_PUBLICPOST_REAL)
		strLineName = "1";

	if (strCabinet == CABINET_SEND_REAL || strCabinet == CABINET_INVESTIGATION_REAL || strCabinet == LEDGER_REGI_REAL)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}

	else if (strCabinet == LEDGER_RECV_REAL)
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?recv=RECV&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}

	else if (strCabinet == CABINET_RECV_REAL || strCabinet == CABINET_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=ING&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_DocflowIngDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
	}
	else if (strCabinet == LEDGER_DIST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowIngDocDetail.jsp?ing=DIST&dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_DocflowIngDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=210");
	}
	else if (strCabinet == CABINET_PUBLICPOST_REAL)
	{
		var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowPostDetail.jsp?dataurl=" + escape(strDataUrl);
		window.open(strUrl,"CN_DocflowPostDetail","toolbar=no,resizable=no, status=yes, width=500,height=240");
	}
	else
	{
		var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveDocDetail.jsp?dataurl=" + escape(strDataUrl) + "&cabinet=" + escape(strCabinet) + "&docstatus=" + escape(nDocStatus) + "&linename=" + escape(strLineName) + "&serialorder=" + escape(nSerialOrder);
		window.open(strUrl,"CN_ApproveDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
}

function changeListingBound(nType)
{
	var elePageInfo = document.getElementById("pageinfo");
	var eleDataInfo = document.getElementById("datainfo");

	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strTitle = eleDataInfo.getAttribute("title");
	var strCurPage = elePageInfo.getAttribute("curpage");
	var strFolderList = eleDataInfo.getAttribute("folderlist");
	var strListing = eleDataInfo.getAttribute("listing");
	var strRefType = elePageInfo.getAttribute("reftype");

	var strUrl = "CN_DocflowList.jsp?userid=" + escape(g_strUserID) + "&deptid=" + escape(g_strDeptID) + "&folderlist=" + strFolderList + "&title=" + strTitle + "&curpage=" + strCurPage + "&listing=" + strListing + "&sorting=" + g_strSorting + "&sortingflag=" + g_strSortingFlag + "&reftype=" + strRefType + "&investtype=" + nType;
	document.location.href = strUrl;
}

function onSetCharger(nType)
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType;
	window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
}

function setApproverInfo(objUserInfo, strType)
{
	var strUserInfo = "";
	strUserInfo += "0" + "";										//SERIAL_ORDER
	strUserInfo += "0" + "";										//PARALLEL_ORDER
	strUserInfo += objUserInfo.getAttribute("userid") + "";		//USER_ID
	strUserInfo += objUserInfo.getAttribute("username") + "";		//USER_NAME
	strUserInfo += objUserInfo.getAttribute("position") + "";		//USER_POSITION
	strUserInfo += objUserInfo.getAttribute("positionabbr") + "";	//USER_POSITION_ABBR
	strUserInfo += objUserInfo.getAttribute("grade") + "";			//USER_LEVEL
	strUserInfo += objUserInfo.getAttribute("gradeabbr") + "";		//USER_LEVEL_ABBR
	strUserInfo += objUserInfo.getAttribute("titlename") + "";		//USER_DUTY
	strUserInfo += objUserInfo.getAttribute("gtpname") + "";		//USER_TITLE
	strUserInfo += objUserInfo.getAttribute("compname") + "";		//COMPANY
	strUserInfo += objUserInfo.getAttribute("deptname") + "";		//DEPT_NAME
	strUserInfo += objUserInfo.getAttribute("deptid") + "";		//DEPT_CODE
	strUserInfo += "N" + "";										//IS_SIGNED
	strUserInfo += "" + "";										//SIGN_DATE
	strUserInfo += "" + "";										//SIGN_FILE_NAME
	if (strType == "60" || strType == "61")
		strUserInfo += "20" + "";									//APPROVER_ROLE
	else
		strUserInfo += "1" + "";									//APPROVER_ROLE
	strUserInfo += "N" + "";										//IS_OPEN
	strUserInfo += "0" + "";										//APPROVER_ACTION
	strUserInfo += "0" + "";										//APPROVER_TYPE
	strUserInfo += "0" + "";										//ADDITIONAL_ROLE
	strUserInfo += "0" + "";										//KEEP_STATUS
	strUserInfo += "" + "";										//KEEP_DATE
	strUserInfo += "" + "";										//EMPTY_REASON
	strUserInfo += "N" + "";										//IS_DOC_MODIFIED
	strUserInfo += "N" + "";										//IS_LINE_MODIFIED
	strUserInfo += "N" + "";										//IS_ATTACH_MODIFIED
	strUserInfo += "" + "";										//OPINION
	strUserInfo += "" + "";										//REP_ID
	strUserInfo += "" + "";										//REP_NAME
	strUserInfo += "" + "";										//REP_POSITION
	strUserInfo += "" + "";										//REP_POSITION_ABBR
	strUserInfo += "" + "";										//REP_LEVEL
	strUserInfo += "" + "";										//REP_LEVEL_ABBR
	strUserInfo += "" + "";										//REP_DUTY
	strUserInfo += "" + "";										//REP_TITLE
	strUserInfo += "" + "";										//REP_COMPANY
	strUserInfo += "" + "";										//REP_DEPT_NAME
	strUserInfo += "" + "";										//REP_DEPT_CODE

	document.getElementById("hidExtendedData").value = strUserInfo;
	var strUrl = "";
	if (strType == "60")
		strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoReceiveCharger.jsp";
	else if (strType == "61")
		strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoSendToCharger.jsp";
	else if (strType == "62")
		strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoReceiveCharger.jsp";
	else if (strType == "63")
		strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoSendToCharger.jsp";

	doBatchDocument(strUrl, 1);
}

function onDistributeDocument()
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	if (g_nCurSelectedCount < 1)
	{
		alert(ALERT_SELECT_AT_LEAST_ONE_DOCUMENT);
		return;
	}

	var strTreeType = "1";
	var strSetType = "2";	//일괄처리과
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetDepartment.jsp?deptid=" + g_strDeptID + "&treetype=" + strTreeType + "&settype=" + strSetType;
	window.open(strUrl, "Docflow_Department", "toolbar=no, resizable=no, status=yes, width=300, height=250");
}

function setDeptInfo(objDeptInfo, strSetType)
{
	var strDeptInfo = "";
	strDeptInfo += "O" + "";										//ENFORCE_BOUND
	strDeptInfo += objDeptInfo.getAttribute("deptname") + "";		//DEPT_NAME
	strDeptInfo += objDeptInfo.getAttribute("deptcode") + "";		//DEPT_CODE
	strDeptInfo += objDeptInfo.getAttribute("deptsymbol") + "";	//DEPT_SYMBOL
	strDeptInfo += objDeptInfo.getAttribute("deptchief") + "";		//DEPT_CHIEF
	strDeptInfo += "" + "";										//REF_DEPT_NAME
	strDeptInfo += "" + "";										//REF_DEPT_CODE
	strDeptInfo += "" + "";										//REF_DEPT_SYMBOL
	strDeptInfo += "" + "";										//REF_DEPT_CHIEF
	strDeptInfo += objDeptInfo.getAttribute("deptcode") + "";		//ACTUAL_DEPT_CODE
	strDeptInfo += "" + "";										//ACCEPTOR
	strDeptInfo += "" + "";										//ACCEPT_DATE
	strDeptInfo += "2" + "";										//RECEIPT_STATUS
	strDeptInfo += "" + "";										//DESCRIPTION
	strDeptInfo += "N" + "";										//IS_PUBDOC_RECIP
	strDeptInfo += "N" + "";										//IS_CERT_EXIST
	strDeptInfo += "0" + "";										//SENDING_TYPE

	document.getElementById("hidExtendedData").value = strDeptInfo;
	var strUrl = "";
	strUrl = g_strBaseUrl + "approve/action/CN_ApproveAutoDistributeEnforce.jsp";

	doBatchDocument(strUrl, 0);
}

function doBatchDocument(strUrl, nType)
{
	var arrSelectedIndex = getSelectedIndexArray();
	g_nCurSelectedCount = arrSelectedIndex.length;

	var strDocId = "";
	var strCabinet = "";
	var nDocStatus = "";
	var strLineName = "";
	var nSerialOrder = "";
	var strFunction = "confirmBatch";
	if (nType == 0)
		strFunction = "confirmDistribution";
	else if (nType == 1)
		strFunction = "confirmReceiveCharger";

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
	document.getElementById("hidCabinet").value = strCabinet;
	document.getElementById("hidDocStatus").value = nDocStatus;
	document.getElementById("hidLineName").value = strLineName;
	document.getElementById("hidSerialOrder").value = nSerialOrder;
	document.getElementById("hidReturnFunction").value = strFunction;

	submitForm(strUrl);
}

function confirmBatch(nProcessCount)
{
	alert(nProcessCount + ALERT_PROCESS_AGREEAPPROVAL);
	document.location.reload();
}

function confirmDistribution(nProcessCount)
{
	alert(nProcessCount + ALERT_DELIVERED_ENFORCE);
	document.location.reload();
}

function confirmReceiveCharger(nProcessCount)
{
	alert(nProcessCount + ALERT_SEND_TO_CHARGER);
	document.location.reload();
}
