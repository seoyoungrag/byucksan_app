<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<!-- <%@ include file="/app/jsp/common/calendarPopup.jsp"%> -->

<%
    response.setHeader("pragma", "no-cache");

	MessageSource m = messageSource;
    
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String current = DateUtil.getCurrentDate(dateFormat);
	String applied = current.replaceAll("-", "");
	String unitType = request.getParameter("unitType");
	String deptId = request.getParameter("deptId");
	String unitId  = request.getParameter("unitId");
	String unitDepth  = request.getParameter("unitDepth");
	String allDocOrForm = request.getParameter("allDocOrForm");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.unit.add' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<script language="javascript">
	function init() {
		
		var unitDepth = "<%=unitDepth%>";
		$('#retentionPeriod').val('${default}');
		<% if (allDocOrForm.equals("1")) { // 모든문서 선택시 안보이게 처리. %>
			$('#allDocOrForm').hide();
		<% } %>
	}

	function add() {
		var form = document.getElementById('form');

		var unitName =$.trim($('#unitName').val());
		if(unitName == null || unitName == '') {
			alert(bind_unit_name_required);
			$('#unitName').focus();
			return;
		}
		var unitOrder =$.trim($('#unitOrder').val());
		
		var unitType = "<%=unitType%>";
		var deptId = "<%=deptId%>";
		var parentUnitId = "<%=unitId%>";
		var unitDepth = "<%=unitDepth%>";

		var description =$.trim($('#description').val());
		if(description.length > 0 && description.length > 400) {
			alert(msg_description);
			$('#description').focus();
			return;
		}

		var retentionPeriod =$.trim($('#retentionPeriod').val());
		if(retentionPeriod == null || retentionPeriod == '' || retentionPeriod == '_') {
			alert(bind_retentionPeriod_required);
			$('#retentionPeriod').focus();
			return;
		}

		var applied = $('#applied').val();
		
		var serialNumber;
		if(form.serialNumber_Y.checked) {
			serialNumber = 'Y';
		} else {
			serialNumber = 'N';
		}

		$.ajax({
			url : '<%=webUri%>/app/bind/unit/simple/create.do',
			type : 'POST',
			dataType : 'json',
			data : {
				unitName : unitName,
				unitType : unitType,
				deptId : deptId,
				description : description,
				serialNumber : serialNumber,
				retentionPeriod : retentionPeriod,
				applied : applied,
				parentUnitId : parentUnitId,
				unitDepth : unitDepth,
				unitOrder : unitOrder
			},
			success : function(data) {
				if(data.success) {
					fncUnitAddCallback(data.unitId, unitName) 
				} else {
					alert("<spring:message code='bind.msg.error'/>");
				}
			},
			error : function(data) {
				alert("<spring:message code='bind.msg.error'/>");
			}
		});
	}

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_retentionPeriod = "<spring:message code='bind.obj.retention.period'/>";
	var bind_description = "<spring:message code='bind.obj.unit.description'/>";

	var bind_unit_code_required = bind_unit_code + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_name_required = bind_unit_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_retentionPeriod_required = bind_retentionPeriod + " <spring:message code='bind.msg.select'/>";
	var msg_description = "<spring:message code='bind.msg.unit.description'/>";
	
	var textAdd = "<spring:message code='bind.button.add'/>";
	var textEdit = "<spring:message code='bind.button.edit'/>";
	var textDel = "<spring:message code='bind.button.del'/>";
	var textRefresh = "<spring:message code='bind.obj.refresh'/>";
	var confirm_delete = "<spring:message code='bind.msg.confirm.delete'/>";
	var msg_delete = "<spring:message code='bind.msg.delete.completed'/>";
	var currentTime = '<%= current %>';
	var appliedTime = '<%= applied %>';
	var webUri = '<%=webUri%>';
	  
	window.onload = init;
	
</script>


</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<%-- <tr>
			<td>
				<acube:titleBar><spring:message code='bind.title.unit.add' /></acube:titleBar>
			</td>
		</tr> --%>
		<tr>
			<acube:space between="title_button" />
		</tr>
		
		<tr>
			<td>
			<table width="100%" border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" valign="top">

								<!--------------------------------------------본문 Table S -------------------------------------->
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
										<form id="form" method="POST" action="<%=webUri%>/app/bind/unit/create.do" onsubmit="return false;">
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name'/> <spring:message code='common.title.essentiality'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unitName" name="unitName" style="width:100%"></td>
														</tr>
													</table>
												</td>
											</tr>    
												
											<tr bgcolor="#ffffff"> 
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/></td>
												<td height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="description" name="description" style="width:100%;overflow:auto" rows="5" onkeyup="checkInputMaxLength(this, '', 400)"></textarea></td>
														</tr>
													</table>
												</td>
											</tr>

											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.order'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="number" class="input" id="unitOrder" name="unitOrder" size="20" value="1"></td>
														</tr>
													</table>
												</td>
											</tr> 											
					
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.retention.period'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff" id="allDocOrForm">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.use.serial.number'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><input type="radio" id="serialNumber_Y" name="serialNumber" value="Y" checked="checked"/> <spring:message code='bind.obj.use'/></td>
														<td class="basic_text"><input type="radio" id="serialNumber_N" name="serialNumber" value="N" /> <spring:message code='bind.obj.not.use'/></td>
													</tr>
													</table>
												</td>
											</tr>


											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.applied'/> <spring:message code='common.title.essentiality'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td>
																<input type="text" class="input_read" name="calc" id="calc" readonly size="11" value="<%= current %>">
																<img id="calendarBTN1" name="calendarBTN1" 
																	src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
																	align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
																	onclick="javascript:cal.select(event, document.getElementById('applied'), document.getElementById('calc'), 'calendarBTN1', '<%= dateFormat %>');">
																<input type="hidden" name="applied" id="applied" value="<%= applied %>">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.created'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input_read" id="created" name="created" size="20" readonly></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.modified'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input_read" id="modified" name="modified" size="20" readonly></td>
														</tr>
													</table>
												</td>
											</tr>
											
											</acube:tableFrame>
											</form>
										</td>
									</tr>
									<tr>
										<acube:space between="title_button" />
									</tr>
									<!-------내용조회  E --------->
							
									<!-------여백 H5  S --------->
									<tr>
										<acube:space between="button_content" />
									</tr>
									<!-------여백 H5  E --------->
									<tr>
										<td>
											<!-------기능버튼 Table S --------->
											<div id="bind_unit_add" style="display:block">
											<acube:buttonGroup>
												<acube:button onclick="javascript:add();" value='<%= m.getMessage("bind.button.save", null, langType) %>' type="2" class="gr" />
											</acube:buttonGroup>
											</div>
											<!-------기능버튼 Table E --------->
										</td>
									</tr>
								</table>
								<!--------------------------------------------본문 Table E -------------------------------------->


<!-------컨텐츠 Table S --------->
							</td>
						</tr>
					</table>
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>

</Body>
</Html>
