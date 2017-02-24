<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindDocVO"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%><html>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	MessageSource m = messageSource;
	
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI");
%>


<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.titla.unit.each.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery-ui-1.8.4.custom.min.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" />

<script LANGUAGE="JavaScript">

	function init() {
		$('#searchTitle').val('${type}'||'title');
		$('#searchValue').val('${value}');
		$('#searchYear').val('${year}');
		
		$('#btLeft').click(function() {
			$("[name='unitZone']").each(function () {
				$(this).hide();
			});
			$('#leftTd').hide();
			$('#rightTd').show();
		});
		
		$('#btRight').click(function() {
			$("[name='unitZone']").each(function () {
				$(this).show();
			});
			$('#rightTd').hide();
			$('#leftTd').show();
		});
		
		$('#btLeft').css("cursor", "hand");
		$('#btRight').css("cursor", "hand");
		
	}

	function goAllMovePage() {
		$("#frmDocList")[0].contentWindow.goAllMovePage();
	}

	function goMovePage() {
		$("#frmDocList")[0].contentWindow.goMovePage();
	}
	
	function goList(bindId) {
		$("#frmDocList").attr("src","<%=webUri%>/app/bind/document/listEachUnit.do?bindId=" + bindId);
	}

	function goExcelPage() {
		$("#frmDocList")[0].contentWindow.goExcelPage();
	}

	function goPrintPage() {
		$("#frmDocList")[0].contentWindow.goPrintPage();
	}
	
	function onSubmit() {
		var searchType = $("#searchType").val();
		var searchValue = $("#searchValue").val();
		$("#frmDocList")[0].contentWindow.$("input[name='searchType']").val(searchType);
		$("#frmDocList")[0].contentWindow.$("input[name='searchValue']").val(searchValue);
		$("#frmDocList")[0].contentWindow.onSubmit();
	}
	
	function showButton(btn) {
	    $(btn).show();
	}

	function hideButton(btn) {
		$(btn).hide();
	}
	
	// 년도검색 추가 - 2014.12.02 - ronnie
	function changeSearchYear(value) {		
		
		var form = document.listForm;
		form.bindId.value = "";
		form.action = '<%=webUri%>/app/bind/document/eachUnitMain.do';
		form.target = '_self';
		form.submit();
	}

	var webUri = '<%=webUri%>';

	window.onload = init;
	
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/eachUnitMain.do'>
		<input type="hidden" name="bindId" value="${bindId}" />
		<input type="hidden" name="deptId" value="${deptId}" />
		<input type="hidden" name="targetId" value="${deptId}" />
		<input type="hidden" name="docId" />
		<input type="hidden" name="pageNo" value="${pageNo}" />		
		<input type="hidden" name="year" />
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><spring:message code='bind.titla.unit.each.list' /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td>
			<td><acube:buttonGroup align="right">
				<% if(roleCode.indexOf(roleId10) > -1 || roleCode.indexOf(roleId11) > -1 || roleCode.indexOf(roleId12) > -1) { %>
				<acube:menuButton id="all_move" disabledid="" onclick="javascript:goAllMovePage();" value='<%= m.getMessage("bind.button.all.movement", null, langType) %>' />
				<acube:space between="button" />
				<acube:menuButton id="movement" disabledid="" onclick="javascript:goMovePage();" value='<%= m.getMessage("bind.button.movement", null, langType) %>' />
				<acube:space between="button" />
				<% } %>
				<% if(!isExtWeb) { %>
				<acube:menuButton id="print" disabledid="" onclick="javascript:goPrintPage();" value='<%= m.getMessage("bind.button.print", null, langType) %>' />
				<acube:space between="button" />
				<acube:menuButton id="excel" disabledid="" onclick="javascript:goExcelPage();" value='<%= m.getMessage("bind.button.save.excel", null, langType) %>' />
				<% } %>
			</acube:buttonGroup></td>
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			
			<td><acube:tableFrame type="search">
				<tr>
					<acube:space between="search" />
					<td>
					<table border="0" cellpadding="0" cellspacing="0"> 
						<tr>
						<!-- 년도검색 추가 - 2014.12.02 - ronnie -->
							<td width="50">
								<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
							</td>
							<td width="5"></td>
							<td width="50"><form:select id="searchType" name="searchType" path="searchType" items="${searchType}" /></td>
							<td width="5"></td>
							<td width="200"><input type="text" class="input" id="searchValue" name="searchValue" onkeydown="if(event.keyCode==13){onSubmit();}" maxlength="25" style="width: 100%"></td>
							<td width="5"></td>
							<td><acube:button onclick="javascript:onSubmit();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" /></td>
						</tr>
					</table>
					</td>
					<acube:space between="search" />
				</tr>
			</acube:tableFrame></td>
		</tr>

		<tr>
			<acube:space between="menu_list" />
		</tr>
		
		<tr>
            <td height="100%" class="communi_text">
            <table height="420" width="100%"  cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td name="unitZone" class="communi_text"></td>
                    <td name="unitZone" height="20" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text" width="150">
                                 <acube:titleBar type="sub">단위업무</acube:titleBar>
                                 </td>
                            </tr>
                         </table>
                     </td>
                     <td class="communi_text"></td>
                     <td colspan="2" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub">문서목록</acube:titleBar>
                                 </td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td name="unitZone" width="10" class="communi_text"></td>
                    <td name="unitZone" width="320" height="100%">
                    <!------------------------------------단위업무 트리--------------------------------------------->
                    <iframe id='frmUnit' name='frmUnit' src="<%=webUri%>/app/bind/document/eachUnitTree.do?searchYear=${year}" scrolling='yes' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    </td>
                    
                    <td width="30" class="text_center" id='leftTd'><img id="btLeft" src="<%=webUri%>/app/ref/image/bu_left2.gif"/></td>
                    <td width="30" class="text_center" id='rightTd' style="display:none"><img id="btRight" src="<%=webUri%>/app/ref/image/bu_right2.gif"/></td>
                    
                   
                    <!-- --------------------------------문서목록------------------------------------------ -->
                    <td width="*" height="100%">
                    <!------------------------------------단위업무 트리--------------------------------------------->
                    <iframe id='frmDocList' name='frmDocList' src="<%=webUri%>/app/bind/document/listEachUnit.do" scrolling='no' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    </td>
                    <td width= "10" class="communi_text"></td>
                </tr>
                
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
            </table>
            </td>
        </tr>
	</table>
	</form>
</acube:outerFrame>
</Body>
</Html>
