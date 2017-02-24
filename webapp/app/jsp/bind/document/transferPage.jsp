<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindDocVO"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<html>
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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.document' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" />

<script LANGUAGE="JavaScript">

	function init() {
		var form = document.listForm;
		form.searchType.value = '${type}'||'title';
		form.searchValue.value = '${value}';
	}

	function toggleAllSelect(checkall, element) {
		var form = document.listForm;
		var isChecked = form[checkall].checked;

		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == element) {
				form.elements[i].checked = isChecked;
			}
		}
	}

	function toggleSelect() {
		document.listForm['checkall'].checked = false;
	}
	
	function getCheckedList() {
		var result = [];
		var form = document.listForm;
		
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == 'bindChk' && form.elements[i].checked) {
				result.push(form.elements[i]);
			}
		}
		return result;
	}

	function goAllMovePage() {
		openWindow('doc_move_win', '<%=webUri%>/app/bind/document/move.do?deptId=${deptId}&bindId=${bindId}&docId=all', 430, 455, 'no');
	}

	function goMovePage() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.move'/>");
		} else {
			var docId = new Array();
			for(var i = 0; i < items.length; i++) {
				docId.push(items[i].attributes.value.value);
			}

			openWindow('doc_move_win', '<%=webUri%>/app/bind/document/move.do?deptId=${deptId}&bindId=${bindId}&docId=' + docId.join(','), 430, 455, 'no');
		}
	}

	function goExcelPage() {
		if(confirm("<spring:message code='bind.msg.confirm.excel.download' />")) {
			var form = document.listForm;
			form.action = '<%=webUri%>/app/common/excel/transferDocument.do'
			form.submit();
		}
	}

	function onSubmit() {
		var form = document.listForm;
		form.action = '<%=webUri%>/app/bind/document/transfer.do';
		form.submit();
	}
	
	function changePage(pageNo) {
		document.listForm.action = '<%=webUri%>/app/bind/document/transfer.do';
		document.listForm.pageNo.value = pageNo;
		document.listForm.submit();
	}

	function goPrintPage() {
		var form = document.listForm;
		var searchType = form.searchType.value;
		var searchValue = form.searchValue.value;
		
		var param = 'deptId=${deptId}&bindId=${bindId}&searchType=' + searchType + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue));
		
		if(confirm("<spring:message code='list.list.msg.alertPrintMaxPageSize'/>")) {
			var url = webUri + "/app/bind/document/transferPrint.do";
			commonPrintBox(url, param);
		}
	}

	var webUri = '<%=webUri%>';

	window.onload = init;
	
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/transfer.do'>
		<input type="hidden" name="bindId" value="${bindId}" />
		<input type="hidden" name="deptId" value="${deptId}" />
		<input type="hidden" name="targetId" value="${deptId}" />
		<input type="hidden" name="docId" />
		<input type="hidden" name="orgBindId" value="${orgBindId}" />
		<input type="hidden" name="pageNo" value="${pageNo}" />
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar>
					<c:choose>
						<c:when test="${empty bindName}">
							<spring:message code='bind.title.document' />
						</c:when>
						<c:otherwise>[${unitName}] ${bindName}</c:otherwise>
					</c:choose>
				</acube:titleBar>
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
							<td width="50"><form:select id="searchType" name="searchType" path="searchType" items="${searchType}" /></td>
							<td width="5"></td>
							<td width="200"><input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%"></td>
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
			<td colspan="2">
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							<%
    try {
 		    //==============================================================================
 		    // Page Navigation variables
 		    String cPageStr = request.getParameter("pageNo");
 		    String sLineStr = request.getParameter("sline");
 		    int CPAGE = 1;
 		    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
 			compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
 			String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
 			OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
 			int sLine = Integer.parseInt(OPT424);
 		    int trSu = 1;
 		    int RecordSu = 0;
 		    if (cPageStr != null && !cPageStr.equals(""))
 			CPAGE = Integer.parseInt(cPageStr);
 		    if (sLineStr != null && !sLineStr.equals(""))
 			sLine = Integer.parseInt(sLineStr);

 		    //==============================================================================

 		    int totalCount = 0; //총글수
 		    int curPage = CPAGE; //현재페이지

 		    AcubeList acubeList = null;
 		    acubeList = new AcubeList(sLine, 7);
 		    acubeList.setColumnWidth("30,40,*,80,80,120,80"); // 900
 		    acubeList.setColumnAlign("center,center,left,center,center,center,center");

 		    AcubeListRow titleRow = acubeList.createTitleRow();
 		    int rowIndex = 0;

 		    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
 		    titleRow.setAttribute(rowIndex, "style", "padding-left:1px");

 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.doc.type", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.doc.title", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.doc.num", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.create.date", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.send.recv", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.division", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		     		    
 		    AcubeListRow row = null;

 		    //==============================================================================
 		    // 데이타 가져오는 부분
 		    List<BindDocVO> rows = (List<BindDocVO>) request.getAttribute("rows");

 		    if (rows == null || rows.size() == 0) {
	 			row = acubeList.createDataNotFoundRow();
	 			row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
 		    } else {
	 			for (int i = 0; i < rows.size(); i++) {
	 			   BindDocVO vo = rows.get(i);
	 			   
	 			   boolean isDocYn = BindConstants.Y.equals(vo.getElectronDocYn());
	 			   boolean isTransferYn = BindConstants.Y.equals(vo.getTransferYn());

	 			   if(totalCount == 0) {
						totalCount = vo.getTotalCount();
				    }

	 			    String style = "height : 25px; text-overflow : ellipsis; overflow : hidden;";
	 			   
	 			    row = acubeList.createRow();
	 			    rowIndex = 0;
	 			    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + vo.getDocId() + "\" onclick=\"javascript:toggleSelect();\">");
	 			    row.setAttribute(rowIndex, "class", "ltb_check");
	 			    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
	 			    
	 			    String docType = m.getMessage("bind.code." + vo.getDocType(), null, langType);
	 			    row.setData(++rowIndex, docType);
	 			    row.setAttribute(rowIndex, "title", docType);
	 			   	row.setAttribute(rowIndex, "style", style);

	 			   	row.setData(++rowIndex, vo.getTitle());
					row.setAttribute(rowIndex, "title", vo.getTitle());
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			   	row.setData(++rowIndex, vo.getDocNumber());
					row.setAttribute(rowIndex, "title", vo.getDocNumber());
	 			   	row.setAttribute(rowIndex, "style", style);

	 			    String approvalTitle = null;
	 			   	if(isDocYn) {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedDate(vo.getApprovalDate()));
	 			   	} else {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate()));
	 			   	}

	 			    row.setData(++rowIndex, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));
	 			    row.setAttribute(rowIndex, "title", approvalTitle);
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			   	String displayName = "";
	 			   	if(dpi001.equals(vo.getDocType())) {
		 			   	String[] receivers = vo.getReceivers().split(",");
		 			   	if(receivers.length == 0) {
		 			   	} else if(receivers.length == 1) {
		 			   	    displayName = receivers[0];
		 			   	} else {
		 			   		displayName = receivers[0] + " " + m.getMessage("bind.obj.receive.count", new Integer[] {receivers.length - 1}, langType);
		 			   	}
	 			   	} else if(dpi002.equals(vo.getDocType())) {
	 			   	    displayName = StringUtils.defaultIfEmpty(vo.getSenderName(), "");
	 			   	}

	 			   	row.setData(++rowIndex, displayName);
	 			    row.setAttribute(rowIndex, "title", vo.getReceivers());
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			    String electronDocYn = m.getMessage(isDocYn ? "bind.obj.doc.electronic" : "bind.obj.doc.none.electronic", null, langType);
	 			    row.setData(++rowIndex, electronDocYn);
	 			    row.setAttribute(rowIndex, "title", electronDocYn);
	 			   	row.setAttribute(rowIndex, "style", style);
				}
 		    }

 		    acubeList.generatePageNavigator(true);
 		    acubeList.setPageDisplay(true);
 		    acubeList.setTotalCount(totalCount);
			acubeList.setCurrentPage(curPage);
 		    acubeList.generate(out);
 		} catch (Exception e) {
		    logger.error(e.getMessage());
 		}
%> <!------ 리스트 Table E ---------></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</Body>
</Html>
