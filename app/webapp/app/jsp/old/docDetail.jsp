<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<HTML>



<HEAD>
<!--
	<META http-equiv="Content-type" content="text/html; charset=euc-kr"/>
-->
	<TITLE>상세정보</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=euc-kr">
<BASE href="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/">
<LINK rel="stylesheet" type="text/css"
	href="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/css/2.0/main.css" />


</HEAD>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/js/jquery.js"/></script>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/js/uuid.js"/></script>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/dialog/cn_DocumentDialog.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveDocDetail.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveRole.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/dialog/cn_ApproveDocDetail.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_DateNTimeCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveEditor.js"></SCRIPT>


<SCRIPT language="javascript">
var g_nOrder = 0;
var g_strBaseUrl = "<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/";
var g_strVersion = "2.0";
var g_nBodyCount = 1;
var g_strOpenLocale = "DOCUMENT";
var g_strRegiRecv = opener.g_strCabinet.substring(0,4);//g_strCabinet
var g_strOption55 = "0:1인결재^1:기안^2:검토^3:협조^4:열람^5:결재^6:전결^7:대결^8:결재안함(위임)^9:사후보고(후열)^10:개인통보^11:개인감사^12:개인 순차합의^13:개인 병렬합의^20:담당^21:선람^22:공람^23:동시공람^24:공람열람^30:부서통보^31:부서감사^32:부서순차합의^33:부서병렬합의^34:주관부서^35:신청부서^";
var g_strOpt148 = "0";
var g_strCaller = "";
var g_strSystemID = "";
var g_nAnnounceStatus = 0;
var g_nPassThrough = 0;
var g_strCabinet = opener.g_strCabinet;
</SCRIPT>

<SCRIPT language="javascript">
function onLoad()
{
	getFocusForHwp97();
	if (g_strCaller != "")
	{
		drawApprovalInfo("activeLine");
		displayTab(0);
	}
	else
	{
		if (g_strRegiRecv == "REGI")
			drawDocumentInfo();
		else
			drawDocumentInfo4RECV();
	}
	displayRecipientTab();
	
	//loadAttach($("#attachFile", "#approvalitem").val());
}

function onClickTab(nOrder)
{
	if (g_nOrder == nOrder)
		return;

	selectTab(nOrder);
	initializeTab();

	displayTab(nOrder)
	g_nOrder = nOrder;
}

function selectTab(nOrder)
{
	if ((g_strVersion.indexOf("2.0") != -1) || (g_strVersion.indexOf("3.5") != -1))
	{
		var strId = "tap" + nOrder + "left";
		var element = document.getElementById(strId);
		element.src = g_strBaseUrl + "app/ref/image/tap01_left.gif";
		strId = "tap" + nOrder + "bg";
		element = document.getElementById(strId);
		element.background = g_strBaseUrl + "app/ref/image/tap01_bg.gif";
		element.style.fontWeight = "bold";
		strId = "tap" + nOrder + "right";
		element = document.getElementById(strId);
		element.src = g_strBaseUrl + "app/ref/image/tap01_right.gif";
	}
	else
	{
		var strId = "Tab" + nOrder;
		var element = document.getElementById(strId);

		element.style.fontWeight = "bold";
		element.style.background = "#f1f1f1";
		element.style.borderBottom = "none";
	}
}

function initializeTab()
{
	if ((g_strVersion.indexOf("2.0") != -1) || (g_strVersion.indexOf("3.5") != -1))
	{
		strId = "tap" + g_nOrder + "left";
		var oldElement = document.getElementById(strId);
		oldElement.src = g_strBaseUrl + "app/ref/image/tap02_left.gif";
		strId = "tap" + g_nOrder + "bg";
		oldElement = document.getElementById(strId);
		oldElement.background = g_strBaseUrl + "app/ref/image/tap02_bg.gif";
		oldElement.style.fontWeight = "normal";
		strId = "tap" + g_nOrder + "right";
		oldElement = document.getElementById(strId);
		oldElement.src = g_strBaseUrl + "app/ref/image/tap02_right.gif";
	}
	else
	{
		strId = "Tab" + g_nOrder;
		var oldElement = document.getElementById(strId);

		oldElement.style.fontWeight = "normal";
		oldElement.style.background = "#b1cfd1";
		oldElement.style.borderBottom = "solid 1px";
	}
}

function displayTab(nOrder)
{
	var objDocumentSpan = document.getElementById("idDocumentInfo");
	objDocumentSpan.style.display = "none";
	var objApprovalSpan = document.getElementById("idApprovalInfo");
	objApprovalSpan.style.display = "none";
	if (g_strRegiRecv != "RECV")
	{
		var objRecipientSpan = document.getElementById("idRecipientInfo");
		objRecipientSpan.style.display = "none";
	}

	if (nOrder == 0)
	{
		if (g_strCaller != "")
		{
			var strView = objApprovalSpan.getAttribute("view");
			if (strView.toUpperCase() == "N")
				drawApprovalInfo("activeLine");
			objApprovalSpan.style.display = "block";
		}
		else
		{
			var strView = objDocumentSpan.getAttribute("view");
			if (strView.toUpperCase() == "N")
				if (g_strRegiRecv != "RECV")
					drawDocumentInfo();
				else
					drawDocumentInfo4RECV();

			objDocumentSpan.style.display = "block";
		}
	}
	else if (nOrder == 1)
	{
		var strView = objApprovalSpan.getAttribute("view");
		if (strView.toUpperCase() == "N")
			drawApprovalInfo("activeLine");
		objApprovalSpan.style.display = "block";
	}
	else if (nOrder == 2)
	{
		var strView = objRecipientSpan.getAttribute("view");
		if (strView.toUpperCase() == "N")
			drawRecipientInfo();
		objRecipientSpan.style.display = "block";
	}
}

function viewRecipient(nProposal)
{
	var objProposal = document.getElementById("idProposal" + nProposal);
	if (objProposal.style.display == "none")
		objProposal.style.display = "block";
	else
		objProposal.style.display = "none";
}

function displayRecipientTab()
{
	var objRecipientTab = document.getElementById("Tab2");
	var objRecipientGroup = null;
	for (var i = 1; i <= g_nBodyCount; ++i)
	{
		if (g_strOpenLocale == "DOCUMENT")
			objRecipientGroup = opener.getRecipGroupByGroupType(i, "0");
		else if (g_strOpenLocale == "LIST")
			objRecipientGroup = getRecipGroupByGroupType(i, "0");
	}

	if (objRecipientTab != null && g_strRegiRecv == "RECV" && objRecipientGroup == null)
		objRecipientTab.style.display = "none";
}

function onClose()
{
	window.close();
}
</SCRIPT>

<BODY onload="javascript:onLoad();return(false);">
	<TABLE width="100%" height="33" border="0" cellpadding="0"
		cellspacing="0" background="./app/ref/image/pop_topbg.gif">
		<TR>
			<TD width="10"></TD>
			<TD width="9"><IMG src="./app/ref/image/tit_icon_pop.gif" width="9"
				height="9"></TD>
			<TD width="5"></TD>
			<TD class="title_wh" nowrap>상세정보</TD>
			<TD width="19"><IMG src="./app/ref/image/bu5_close.gif" width="19"
				height="19" onclick="javascript:window.close();return(false);"></TD>
			<TD width="10"></TD>
		</TR>
	</TABLE>



	<!-- Body Begin -->
<CENTER>
		<TABLE cellspacing='0' cellpadding='0' border='0' width='560'
			height='38'>
			<TR>
				<TD valign='bottom'><TABLE cellspacing='0' cellpadding='0'
						border='0' align='left'>
						<TR>
							<TD><TABLE id='Tab0' border='0' cellpadding='0'
									cellspacing='0'>
									<TR>
										<TD><IMG id='tap0left' src='./app/ref/image/tap01_left.gif'
											width='12' height='24'></TD>
										<TD id='tap0bg' background='./app/ref/image/tap01_bg.gif'
											style='font-weight: bold;'><A href='#'
											onclick='javascript:onClickTab(0);return(false);'><NOBR>문서정보</NOBR></A></TD>
										<TD><IMG id='tap0right' src='./app/ref/image/tap01_right.gif'
											width='12' height='24'></TD>
									</TR>
								</TABLE></TD>
							<TD width='0'><BR /></TD>
							<TD><TABLE id='Tab1' border='0' cellpadding='0'
									cellspacing='0'>
									<TR>
										<TD><IMG id='tap1left' src='./app/ref/image/tap02_left.gif'
											width='12' height='24'></TD>
										<TD id='tap1bg' background='./app/ref/image/tap02_bg.gif'
											style='font-weight: normal;'><A href='#'
											onclick='javascript:onClickTab(1);return(false);'><NOBR>결재정보</NOBR></A></TD>
										<TD><IMG id='tap1right' src='./app/ref/image/tap02_right.gif'
											width='12' height='24'></TD>
									</TR>
								</TABLE></TD>
							<TD width='0'><BR /></TD>
							<TD><TABLE id='Tab2' border='0' cellpadding='0'
									cellspacing='0'>
									<TR>
										<TD><IMG id='tap2left' src='./app/ref/image/tap02_left.gif'
											width='12' height='24'></TD>
										<TD id='tap2bg' background='./app/ref/image/tap02_bg.gif'
											style='font-weight: normal;'><A href='#'
											onclick='javascript:onClickTab(2);return(false);'><NOBR>수신처정보</NOBR></A></TD>
										<TD><IMG id='tap2right' src='./app/ref/image/tap02_right.gif'
											width='12' height='24'></TD>
									</TR>
								</TABLE></TD>
							</TD>
						</TR>
					</TABLE></TD>
				<TD valign='bottom'><TABLE width='100%' border='0'
						cellpadding='0' cellspacing='0'>
						<TR>
							<TD height='13'></TD>
						</TR>
						<TR>
							<TD><TABLE align='right' border='0' cellpadding='0'
									cellspacing='0'>
									<TR>
										<TD><IMG src='./app/ref/image/bu2_left.gif' width='8'
											height='20'></TD>
										<TD background='./app/ref/image/bu2_bg.gif' class='text_left'
											nowrap><A href='#'
											onclick="javascript:onClose();return(false);">확인</A></TD>
										<TD><IMG src='./app/ref/image/bu2_right.gif' width='8'
											height='20'></TD>
										<TD width='3'></TD>
										<TD width='7'></TD>
									</TR>
								</TABLE></TD>
						</TR>
						<TR>
							<TD height='5'></TD>
						</TR>
					</TABLE></TD>
			</TR>
			<TR>
				<TD height='2' colspan='2' class='tab_bg'></TD>
			</TR>
		</TABLE>

	</CENTER>
<CENTER>
<SPAN id="idDocumentInfo" view="N" style='overflow:auto;width:560;height:340;text-align:center;border:0pt solid #b1cfd1;border-top:none;'></SPAN>
<SPAN id="idApprovalInfo" view="N" pos="GRD" style='overflow:auto;width:560;height:320;text-align:center;border:0pt solid #b1cfd1;border-top:none;display:none;'></SPAN>

<SPAN id="idRecipientInfo" view="N" style='overflow:auto;width:560;height:320;text-align:center;border:0pt solid #b1cfd1;border-top:none;display:none;'></SPAN>

<!-- Hidden Frame Begin -->
	<IFRAME id="hidDataFrame" name="hidDataFrame" src="" width="100%" height="0" ></IFRAME>
<!-- Hidden Frame End -->
</CENTER>
<!-- Body End -->
</BODY>
</HTML>