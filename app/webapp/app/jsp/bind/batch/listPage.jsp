<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BatchVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.lang.time.FastDateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/adminheader.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
	String year = (String) request.getAttribute("year");
	String option = (String) request.getAttribute("option");
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.batch.create' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

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
	}

	function create() {
		var msg = "<spring:message code='bind.msg.confirm.batch.create' arguments='${year}' />";
		if('${option}' != 'year') msg = "<spring:message code='bind.msg.confirm.batch.create.period' arguments='${year}' />";
		
		if(confirm(msg)) {
			$.ajax({
				url : '<%=webUri%>/app/bind/batch/execute.do',
				type : 'POST',
				data : {
					year : '${year}'
				},
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.completed'/>");
						
						document.listForm.submit();
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}
	
	function remove() {
		var msg = "<spring:message code='bind.msg.confirm.delete.batch' arguments='${year}' />";
		if('${option}' != 'year') msg = "<spring:message code='bind.msg.confirm.batch.create.period' arguments='${year}' />";
		
		if(confirm(msg)) {
			$.ajax({
				url : '<%=webUri%>/app/bind/batch/remove.do',
				type : 'POST',
				data : {
					year : '${year}'
				},
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.delete.completed'/>");
						
						document.listForm.submit();
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	var webUri = '<%=webUri%>';
	
	window.onload = init;
	

</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/batch/list.do'>
	
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<tr>
			<td>
	<span class="pop_title77"><spring:message code="bind.title.batch.create" /></span>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td colspan="2" class="ltb_left" nowrap="nowrap">
				* <spring:message code="bind.msg.batch.description" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%;height:325;">
			
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
									    acubeList = new AcubeList(sLine, 4);
									    acubeList.setColumnWidth("60,120,120,*");
									    acubeList.setColumnAlign("center,center,center,right");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, m.getMessage("year".equals(option) ? "bind.obj.year.point" : "bind.obj.period", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.start.date", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.end.date", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.execute.time", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;

									    //==============================================================================
									    // 데이타 가져오는 부분
									    List<BatchVO> rows = (List<BatchVO>) request.getAttribute("rows");

									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } else {
											for (int i = 0; i < rows.size(); i++) {
											    BatchVO vo = rows.get(i);
											    
											    String style = "text-overflow : ellipsis; overflow : hidden; height : 25;";
											    
											    row = acubeList.createRow();
											    rowIndex = 0;
											    
											    row.setData(rowIndex, vo.getYear());
											    row.setAttribute(rowIndex, "title", vo.getYear());
											    row.setAttribute(rowIndex, "style", style);
											    
											    String startDate = FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getStartDate()));
											    row.setData(++rowIndex, startDate);
											    row.setAttribute(rowIndex, "title", startDate);
											    row.setAttribute(rowIndex, "style", style);
											    
											    String endDate = FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getEndDate()));
											    row.setData(++rowIndex, endDate);
											    row.setAttribute(rowIndex, "title", endDate);
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, vo.getExecuteTime() + " ms");
											    row.setAttribute(rowIndex, "title", vo.getExecuteTime() + " ms");
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
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<c:choose>
						<c:when test="${target > period}">
							<acube:button onclick="javascript:remove();"
							value='<%= m.getMessage("year".equals(option) ? "bind.button.remove" : "bind.button.remove.period", new String[] {year}, langType) %>'
							type="2" class="gr" />
						</c:when>
						<c:otherwise>
							<acube:button onclick="javascript:create();"
							value='<%= m.getMessage("year".equals(option) ? "bind.button.create" : "bind.button.create.period", new String[] {year}, langType) %>'
							type="2" class="gr" />
						</c:otherwise>
					</c:choose>
					
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
				</acube:buttonGroup>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</Body>
</Html>
