<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.batch.edit' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">

	function init() {
		document.bindForm.retentionPeriod.value = '${row.retentionPeriod}';
	}

	function edit() {
		var form = document.getElementById('bindForm');
	
		var unitId = form.unitId.value.trim();
		if(unitId == null || unitId == '') {
			alert(bind_unit_code_required);
			form.unitId.focus();
			return;
		}
	
		var unitName = form.unitName.value.trim();
		if(unitName == null || unitName == '') {
			alert(bind_unit_name_required);
			form.unitName.focus();
			return;
		}

		var bindName = form.bindName.value.trim();
		if(bindName == null || bindName == '') {
			alert(bind_name_required);
			form.bindName.focus();
			return;
		}
		
		var retentionPeriod = form.retentionPeriod.value.trim();
		if(retentionPeriod == null || retentionPeriod == '' || retentionPeriod == '_') {
			alert(bind_retentionPeriod_required);
			form.retentionPeriod.focus();
			return;
		}

		var prefix = form.prefix.value.trim();

		$.ajax({
			url : '<%=webUri%>/app/bind/bindBatchUpdate.do',
			type : 'POST',
			dataType : 'json',
			data : {
				unitId : unitId,
				unitName : unitName,
				bindName : bindName,
				prefix : prefix,
				retentionPeriod : retentionPeriod
			},
			success : function(data) {
				if(data.success) {
					var f = opener.document.listForm;
					f.submit();

					alert("<spring:message code='bind.msg.add.completed'/>");
					
					window.close();
				} else {
					if(data.msgId) {
						alert(data.msg);
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				}
			},
			error : function(data) {
				alert("<spring:message code='bind.msg.error'/>");
			}
		});
	}

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_name = "<spring:message code='bind.obj.name'/>";
	var bind_create_year = '${option}' == 'year' ? "<spring:message code='bind.obj.create.year'/>" : "<spring:message code='bind.obj.create.period'/>";
	var bind_expire_year = '${option}' == 'year' ? "<spring:message code='bind.obj.expire.year'/>" : "<spring:message code='bind.obj.expire.period'/>";
	var bind_retentionPeriod = "<spring:message code='bind.obj.retention.period'/>";
	var rootText = "<spring:message code='bind.obj.document.division'/>";
	var select_unit = "<spring:message code='bind.msg.select.unit'/>";
		
	var bind_unit_code_required = bind_unit_code + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_name_required = bind_unit_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_name_required = bind_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_create_year_required = bind_create_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_expire_year_required = bind_expire_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_retentionPeriod_required = bind_retentionPeriod + " <spring:message code='bind.msg.mandantory'/>";
	
	var webUri = '<%=webUri%>';

	window.onload = init;

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='bind.title.batch.edit' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/bind/bindBatchUpdate.do">
	<input type="hidden" name="compId" value="${compId}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit" nowrap="nowrap">
							<spring:message code='bind.obj.unit.code' /><spring:message code='common.title.essentiality'/>
						</td>
						<td height="200">
						<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
							<tr><td colspan="2"><div id="select-tree"></div></td></tr>
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input_read" name="unitId" value="${row.unitId}" readonly size="12"></td>
								<td align="left" style="padding-left:-1px; width: 100%"><input type="text" class="input_read" name="unitName" value="${row.unitName}" readonly size="35"></td>
							</tr>
						</table>
						</td>
					</tr>
	
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='bind.obj.name' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="bindName" value="${row.displayName}" onkeyup="checkInputMaxLength(this, '', 60)" size="49"></td>
							</tr>
						</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='bind.obj.prefix.code' />
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="prefix" value="${row.prefix}" onkeyup="checkInputMaxLength(this, '', 36)" size="12"></td>
							</tr>
						</table>
						</td>
					</tr>
	
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
						<spring:message code='bind.obj.retention.period' /> <spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" /></td>
							</tr>
						</table>
						</td>
					</tr>
				</acube:tableFrame>
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
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:edit();"
						value='<%= m.getMessage("bind.button.edit", null, langType) %>'
						type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>'
						type="2" class="gr" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>