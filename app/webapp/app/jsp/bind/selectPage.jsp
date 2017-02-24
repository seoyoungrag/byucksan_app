<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.select' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<style>
	.scrollbox {width:100%; height:100%; overflow:auto; padding:2px; border:1px; border-style:solid; border-color:#DDDDDD;
		scrollbar-face-color: #EEEEEE;
		scrollbar-highlight-color: #EEEEEE;
		scrollbar-3dlight-color: #AAAAAA;
		scrollbar-shadow-color: #AAAAAA;
		scrollbar-darkshadow-color: #EEEEEE;
		scrollbar-track-color: #DDDDDD;
		scrollbar-arrow-color: #666666;
	}
	.scrollboxNB {width:100%; height:100%; overflow:auto; padding:2px; border:0px; border-style:solid; border-color:gray}
</style>

<script language="javascript">

	function init() {
		// document.listForm.searchYear.value = '${year}';
	}

	function selectTarget() {
		var form = document.listForm;
		var bindId = form.bindId.value;

		if(bindId == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else {
			var bind = {};
			bind['bindingId'] = form.bindId.value;
			bind['bindingName'] = form.bindName.value;
			bind['unitId'] = form.unitId.value;
			bind['unitName'] = form.unitName.value;
			bind['retentionPeriod'] = form.retentionPeriod.value;

			// 편철 다국어 추가
			bind['bindingResourceId'] = form.bindingResourceId.value;
			
			if(opener && opener.setBind) {
				opener.setBind(bind);
				window.close();
			}
		}
	}

	function changeSearchYear(searchYear) {
		var f = document.listForm;
		f.searchYear.value = searchYear;
		document.listForm.submit();
	}

	function select(bindId, bindName, unitId, unitName, retentionPeriod, bindingResourceId) {
		var f = document.listForm;
		f.bindId.value = bindId;
		f.bindName.value = bindName;
		f.unitId.value = unitId;
		f.unitName.value = unitName;
		f.retentionPeriod.value = retentionPeriod;

		// 편철 다국어 추가
		f.bindingResourceId.value = bindingResourceId;
	} 
	
	function selectDbl(bindId, bindName, unitId, unitName, retentionPeriod, bindingResourceId) {
		var f = document.listForm;
		f.bindId.value = bindId;
		f.bindName.value = bindName;
		f.unitId.value = unitId;
		f.unitName.value = unitName;
		f.retentionPeriod.value = retentionPeriod;

		// 편철 다국어 추가
		f.bindingResourceId.value = bindingResourceId;

		selectTarget();
	}
	
	var webUri = '<%=webUri%>';
	
	window.onload = init;
	
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/select.do'>
	<input type="hidden" name="bindId" />
	<input type="hidden" name="unitId" />
	<input type="hidden" name="unitName" />
	<input type="hidden" name="retentionPeriod" />
	<input type="hidden" name="serialNumber" value="${serialNumber}" />
	<input type="hidden" name="bindingResourceId" />
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.select" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<!--
		<tr>
			<td>
			<table border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td class="basic_text">
	                  <c:choose>
	                  	<c:when test="${searchOption eq 'Y'}">
	                  	<spring:message code='bind.obj.year.point' />
	                  	</c:when>
	                  	<c:otherwise>
	                  	<spring:message code='bind.obj.period' />
	                  	</c:otherwise>
	                  </c:choose>
					</td>
					<td width="10" align="center">:</td>
					<td><form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		-->
		<tr>
			<td colspan="2">
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%;height:350;">
			
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
									    acubeList = new AcubeList(sLine, 2);
									    acubeList.setColumnWidth("160,*");
									    acubeList.setColumnAlign("left,left");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.name", null, langType));
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
											    row.setData(rowIndex, EscapeUtil.escapeHtmlDisp(vo.getUnitName()));
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getUnitName()));
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:selectDbl('" + vo.getBindId() + "', '" + vo.getBindName() + "', '" + vo.getUnitId() + "', '" + vo.getUnitName() + "', '" + vo.getRetentionPeriod() + "', '" + vo.getResourceId() + "');\" onDblClick=\"javascript:selectDbl('" + vo.getBindId() + "', '" + vo.getBindName() + "', '" + vo.getUnitId() + "', '" + vo.getUnitName() + "', '" + vo.getRetentionPeriod() + "', '" + vo.getResourceId() + "');\" style=\"" + style + "\">" + EscapeUtil.escapeHtmlDisp(vo.getBindName()) + "</a>");
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getBindName()));
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
			</DIV>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td class="basic_text" nowrap="nowrap"><spring:message code='bind.obj.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="bindName" size="42" class="input_read" readonly /></td>
				<td width="3"></td>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:selectTarget();"
							value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
						<acube:space between="button" />
						<acube:button onclick="javascript:window.close();"
							value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
					</acube:buttonGroup>
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
