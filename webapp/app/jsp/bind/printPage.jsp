<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
    String closeBtn = m.getMessage("list.list.button.close", null, langType);
%>
<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

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

	function closeWin(){
		if(opener.printDoc != null || opener.printDoc.closed == false){
			opener.printDoc.close();
		}
	}

	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="ltb_left" ><b><spring:message code='list.list.msg.noticePrint1'/></b></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint2'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint3'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint4'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint5'/></td>
</tr>
<tr>
	<td class="ltb_right"><acube:button onclick="closeWin();return(false);" value="<%=closeBtn%>" type="4" class="gr" /></td>
</tr>
</table>
</acube:outerFrame>

<div id="printDiv">
<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/list.do'>
	<input type="hidden" name="bindId" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><spring:message code="bind.title.list" /></acube:titleBar>
			</td>
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
									    acubeList.setColumnWidth("40, 150,*,60,100,60,100");
									    acubeList.setColumnAlign("center,left,left,center,center,center,center");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, m.getMessage("bind.obj.num", null, langType));
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.count", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.type", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.retention.period", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.remark", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;

									    //==============================================================================
									    // 데이타 가져오는 부분
									    List<BindVO> rows = (List<BindVO>) request.getAttribute("rows");

									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } else {
											for (int i = 0; i < rows.size(); i++) {
											    BindVO vo = rows.get(i);
											    
											    if(i == 0) totalCount = vo.getTotalCount();
	
											    String style = "text-overflow : ellipsis; overflow : hidden;";
											    if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
													style = style + " text-decoration : line-through;";
											    }
	
											    row = acubeList.createRow();
											    rowIndex = 0;
											    row.setData(rowIndex, i + 1);
											    row.setAttribute(rowIndex, "title", i + 1);
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goBindInfoPage('" + vo.getDeptId() + "', '" + vo.getUnitId() + "');\" style=\"" + style + "\">" + vo.getUnitName() + "</a>");
											    row.setAttribute(rowIndex, "title", vo.getUnitName());
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goDocumentPage('" + vo.getDeptId() + "', '" + vo.getBindId() + "');\" style=\"" + style + "\">" + vo.getBindName() + "</a>");
											    row.setAttribute(rowIndex, "title", vo.getBindName());
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, m.getMessage("bind.obj.doc.docunt", new Integer[] { vo.getDocCount() }, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.obj.doc.docunt", new Integer[] { vo.getDocCount() }, langType));
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getDocType(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getDocType(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
	
											    String arrange = vo.getArrange();
											    String binding = vo.getBinding();
											    
											    String remark = "";
											    if (BindConstants.Y.equals(binding)) {
													remark = m.getMessage("bind.obj.confirmation", null, langType);
											    } else if (BindConstants.Y.equals(arrange)) {
													remark = m.getMessage("bind.obj.ok", null, langType);
											    }
	
											    if (BindConstants.SEND_TYPES.DST001.name().equals(vo.getSendType())) {
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else if (BindConstants.SEND_TYPES.DST002.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST002", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST002", null, langType);
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST003", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST003", null, langType);
		
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden; color : #FF0000;");
											    } else if (BindConstants.SEND_TYPES.DST004.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST004", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST004", null, langType);
		
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else {
													row.setData(++rowIndex, "");
											    }
											    
											    row.setAttribute(rowIndex, "title", remark);
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

</body>
</html>
