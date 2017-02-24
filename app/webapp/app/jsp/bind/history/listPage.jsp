<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.time.FastDateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");

	MessageSource m = messageSource;
	
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
%>


<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script LANGUAGE="JavaScript">

	function init() {
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

	var toggleCount = 0;
	
	function toggleSelect(obj) {
		toggleCount += (obj.checked ? 1 : -1);
	}

	function changePage(pageNo) {
		window.location.href = '<%=webUri%>/app/bind/history/list.do?deptId=${deptId}&bindId=${bindId}&pageNo=' + pageNo;
	}

	function goViewPage(modified) {
		window.location.href = '<%=webUri%>/app/bind/history/view.do?deptId=${deptId}&bindId=${bindId}&pageNo=${pageNo}&modified=' + modified;
	}

	function goBindViewPage() {
		window.location.href = '<%=webUri%>/app/bind/view.do?deptId=${deptId}&bindId=${bindId}';
	}
	
	window.onload = init;


</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
<form name="listForm" method='post' action='<%=webUri%>/app/bind/list.do'>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><spring:message code="bind.title.history"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>
	<table width="100%" height="342" border="0" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<acube:space between="menu_list" />
		</tr>
		<tr valign="top">
			<td>
			<table width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
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
			// 변경일, 편철명, 보존기간, 변경자
 		    AcubeList acubeList = null;
 		    acubeList = new AcubeList(sLine, 4);
 		    acubeList.setColumnWidth("120,*,100,100");
 		    acubeList.setColumnAlign("center,left,center,center");

 		    AcubeListRow titleRow = acubeList.createTitleRow();
 		    int rowIndex = 0;
 		    titleRow.setData(rowIndex, m.getMessage("bind.obj.modified", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.name", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.retention.period", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.modifiedBy", null, langType));
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
	 			    
	 			    if(totalCount == 0) {
	 					totalCount = vo.getTotalCount();
	 			    }
	 			    
	 			    row = acubeList.createRow();
	 			    rowIndex = 0;
	 			    row.setData(rowIndex, FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getModified())));
					row.setData(++rowIndex, "<a href=\"javascript:goViewPage('" + vo.getModified() + "');\">" + vo.getBindName() + "</a>");
	 			    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
	 			    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
	 			    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
	 			  	row.setData(++rowIndex, vo.getModifiedName());
	 			    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
	 			}
 		    }

			acubeList.setNavigationType("normal");
			acubeList.generatePageNavigator(true); 
			acubeList.setPageDisplay(false);
			acubeList.setTotalCount(totalCount);
			acubeList.setCurrentPage(curPage);
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
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr height="30">
			<td align="center">
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
</form>
</acube:outerFrame>

</Body>
</Html>
