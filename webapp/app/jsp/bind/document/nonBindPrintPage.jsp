<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindDocVO"%>
<%@ page import="java.net.URLEncoder"%><html>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<html>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
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

<script LANGUAGE="JavaScript">

	var initBody;
	
	function beforePrint(){
		initBody = document.body.innerHTML;
		document.body.innerHTML = printDiv.innerHTML; 
	}
	
	function afterPrint(){
		document.body.innerHTML = initBody;
	}
	
	function init(){
		window.onbeforeprint = beforePrint;
		window.onafterprint = afterPrint;
		window.print();
	}
	
	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="ltb_left"><b>※ 원할한 프린트를 위해서는 아래와 같이 프린트 설정을 변경하셔야합니다.</b></td>
		</tr>
		<tr>
			<td class="ltb_left">1. 배경색 및 이미지 인쇄 활성화.</td>
		</tr>
		<tr>
			<td class="ltb_left">2. 프린트 설정에서 왼쪽,오른쪽 여백 설정을 4밀리미터로 변경.</td>
		</tr>
	</table>
</acube:outerFrame>

<div id="printDiv">
<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/list.do'>
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><spring:message code="bind.title.document"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">
							<!------ 리스트 Table S --------->
<%
    try {
 		    //==============================================================================
 		    // Page Navigation variables
 		    String cPageStr = request.getParameter("pageNo");
 		    String sLineStr = request.getParameter("sline");
 		    int CPAGE = 1;
 		   	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
 			String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
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
 		    acubeList.setColumnWidth("40,40,*,80,80,120,80"); // 900
 		    acubeList.setColumnAlign("center,center,left,center,center,center,center");

 		    AcubeListRow titleRow = acubeList.createTitleRow();
 		    int rowIndex = 0;

 		    titleRow.setData(rowIndex, m.getMessage("bind.obj.num", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

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

 		    if (rows.size() == 0) {
	 			row = acubeList.createDataNotFoundRow();
	 			row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
 		    } else {
	 			for (int i = 0; i < rows.size(); i++) {
	 			   BindDocVO vo = rows.get(i);
	 			   boolean isDocYn = BindConstants.Y.equals(vo.getElectronDocYn());

	 			   if(totalCount == 0) {
						totalCount = vo.getTotalCount();
				    }

	 			    String style = "height : 25px; text-overflow : ellipsis; overflow : hidden;";
	 			   
	 			    row = acubeList.createRow();
	 			    rowIndex = 0;
	 			    row.setData(rowIndex, vo.getIdx());
	 			    row.setAttribute(rowIndex, "title", vo.getIdx());
	 			    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
	 			    
	 			    String docType = m.getMessage("bind.code." + vo.getDocType(), null, langType);
	 			    row.setData(++rowIndex, docType);
	 			    row.setAttribute(rowIndex, "title", docType);
	 			   	row.setAttribute(rowIndex, "style", style);

	 			   	row.setData(++rowIndex, "<a href=\"#\">" + vo.getTitle() + "</a>");
					row.setAttribute(rowIndex, "title", vo.getTitle());
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			    String approvalTitle = null;
	 			   	if(isDocYn) {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedDate(vo.getApprovalDate()));
	 			   	} else {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate()));
	 			   	}
	 			    
	 			   	row.setData(++rowIndex, vo.getDocNumber());
					row.setAttribute(rowIndex, "title", vo.getDocNumber());
	 			   	row.setAttribute(rowIndex, "style", style);

	 			    row.setData(++rowIndex, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));
	 			    row.setAttribute(rowIndex, "title", approvalTitle);
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			   	
	 			   	String displayName = "";
	 			   	if(dpi001.equals(vo.getDocType())) {
		 			   	String[] receivers = vo.getReceivers().split(",");
		 			   	
		 			   	if(receivers.length == 1) {
		 			   	    displayName = receivers[0];
		 			   	} else {
		 			   		displayName = receivers[0] + " " + m.getMessage("bind.obj.receive.count", new Integer[] {receivers.length - 1}, langType);
		 			   	}
	 			   	} else if(dpi002.equals(vo.getDocType())) {
	 			   	    displayName = vo.getSenderName();
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

 		   acubeList.generatePageNavigator(false);
		   acubeList.generate(out);
		   
 		} catch (Exception e) {
		    logger.error(e.getMessage());
 		}
%>
							<!------ 리스트 Table E --------->
							</td>
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
</div>

</Body>
</Html>
