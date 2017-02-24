<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindBatchVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/adminheader.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.batch.category' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<script language="javascript">

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

	function goAddPage() {
		openWindow('bind_batch_add_win', '<%=webUri%>/app/bind/bindBatchAdd.do', 480, 380, 'no');
	}
	
	function goEditPage() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.edit'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.edit'/>");
		} else {
			var unitId = items[0].value;
			var bindName = items[0].attributes.bindName.value;
			
			openWindow('bind_batch_edit_win', '<%=webUri%>/app/bind/bindBatchEdit.do?unitId=' + unitId + '&bindName=' + bindName, 480, 380, 'no');
		}
	}

	function goDelPage() {
		var items = getCheckedList();
		var bindName = '';
		var unitType = '';
		
		for(var i = 0; i < items.length; i++) {
			bindName += ',' + items[i].attributes.bindName.value;
			unitType += ',' + items[i].attributes.unitType.value;
		}
		
		if(confirm("<spring:message code='bind.msg.confirm.delete' />")) {
			$.ajax({
				url : '<%=webUri%>/app/bind/bindBatchDelete.do',
				type : 'POST',
				dataType : 'json',
				data : {
					bindName : bindName.substring(1),
					unitType : unitType.substring(1)
				},
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

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/bindBatchList.do'>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.batch.category" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td nowrap="nowrap">
				<acube:buttonGroup align="right">
					<acube:menuButton id="add" disabledid="" onclick="javascript:goAddPage();" value='<%= m.getMessage("bind.button.add", null, langType) %>' />
					<acube:space between="button" />
					<!--
					<acube:menuButton id="edit" disabledid="" onclick="javascript:goEditPage();" value='<%= m.getMessage("bind.button.edit", null, langType) %>' />
					<acube:space between="button" />
					-->
					<acube:menuButton id="del" disabledid="" onclick="javascript:goDelPage();" value='<%= m.getMessage("bind.button.del", null, langType) %>' />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
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
									    acubeList.setColumnWidth("30,100,100,200,*,100,80");
									    acubeList.setColumnAlign("center,center,left,left,center,center,center");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.type", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.code", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.prefix.code", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.retention.period", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;

									    //==============================================================================
									    // 데이타 가져오는 부분
									    List<BindBatchVO> rows = (List<BindBatchVO>) request.getAttribute("rows");
									    
									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    }else {
											for (int i = 0; i < rows.size(); i++) {
											    BindBatchVO vo = rows.get(i);

											    row = acubeList.createRow();
											    rowIndex = 0;
											    
											    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + vo.getUnitId() + "\" bindName=\"" + vo.getDisplayName()+ "\" unitType=\"" + vo.getUnitType() + "\">");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
											    
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getUnitType(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getUnitType(), null, langType));
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    
											    row.setData(++rowIndex, EscapeUtil.escapeHtmlDisp(vo.getUnitId()));
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getUnitId()));
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    
											    row.setData(++rowIndex, EscapeUtil.escapeHtmlDisp(vo.getUnitName()));
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getUnitName()));
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    
											    row.setData(++rowIndex, EscapeUtil.escapeHtmlDisp(vo.getDisplayName()));
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getDisplayName()));
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    
											    row.setData(++rowIndex, EscapeUtil.escapeHtmlDisp(vo.getPrefix()));
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getPrefix()));
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    
											    String retentionPeriod = m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType);
											    row.setData(++rowIndex, retentionPeriod);
											    row.setAttribute(rowIndex, "title", retentionPeriod);
											    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
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

</Body>
</Html>
