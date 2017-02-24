<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
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
		document.listForm.searchYear.value = '${year}';
	}

	function move() {
		var form = document.listForm;
		var targetId = form.targetId.value;

		if(targetId == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else if (targetId == '${bindId}') {
			alert("<spring:message code='bind.msg.move.not.same' />");
		} else {
			if(confirm("<spring:message code='bind.msg.confirm.bind.move.document' />")) {
				var f = opener.document.listForm;

				$.ajax({
					url : '<%=webUri%>/app/bind/document/movement.do',
					type : 'POST',
					dataType : 'json',
					data : {
						orgBindId : '${bindId}',
						bindId : targetId,
						docId : '${docId}',
						deptId : '${deptId}',
						searchType : f.searchType.value,
						searchValue : f.searchValue.value
					},
					success : function(data) {
						if(data.success) {
							alert("<spring:message code='bind.msg.completed'/>");

							f.action = '<%=webUri%>/app/bind/document/list.do';
							f.target = '_self';
							f.submit();

							if(opener.opener) {
								var _f = opener.opener.document.listForm;
								f.action = '<%=webUri%>/app/bind/list.do';
								f.target = '_self';
								_f.submit();
							}
							
							window.close();
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
	}

	function changeSearchYear(searchYear) {
		var f = document.listForm;
		f.searchYear.value = searchYear;
		document.listForm.submit();
	}

	function select(bindId, bindName) {
		var f = document.listForm;
		f.targetId.value = bindId;
		f.targetName.value = bindName;
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
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/move.do'>
	<input type="hidden" name="bindId" value="${bindId}"/>
	<input type="hidden" name="docId" value="${docId}" />
	<input type="hidden" name="targetId" />	
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
		<span class="pop_title77"><spring:message code="bind.title.select" /></span>
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
									    acubeList = new AcubeList(sLine, 2);
									    acubeList.setColumnWidth("120,*");
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
											    row.setData(rowIndex, vo.getUnitName());
											    row.setAttribute(rowIndex, "title", vo.getUnitName());
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:select('" + vo.getBindId() + "', '" + vo.getBindName() + "');\" style=\"" + style + "\">" + vo.getBindName() + "</a>");
											    row.setAttribute(rowIndex, "title", vo.getBindName());
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
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td class="basic_text"><spring:message code='bind.obj.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="targetName" size="43" class="input_read" readonly /></td>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:move();"
							value='<%= m.getMessage("bind.button.movement", null, langType) %>' type="2" class="gr" />
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
