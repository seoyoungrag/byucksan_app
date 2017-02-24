<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");

	MessageSource m = messageSource;
	
	BindUnitVO vo = (BindUnitVO) request.getAttribute(BindConstants.ROW);
    
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String fullFormat = AppConfig.getProperty("format", "yyyy-MM-dd", "date");
	String current = DateUtil.getCurrentDate(dateFormat);
	
	String compId = vo.getCompId();
	String deptId = vo.getDeptId();
	String unitId = vo.getUnitId();
	String unitName = vo.getUnitName();
	String unitType = vo.getUnitType();;
	String retentionPeriod = vo.getRetentionPeriod();
	String applied = vo.getApplied();
	String resourceId = vo.getResourceId();
	
	String created = vo.getCreated();
	String modified = vo.getModified();
	String serialNumber = vo.getSerialNumber();
	String description = vo.getDescription();
	description = CommonUtil.nl2br(description);
	description = CommonUtil.escapeSpecialCharForJQuery(description);
	String allDocOrForm = request.getParameter("allDocOrForm");
	
	String bindCount = request.getParameter("bindCount");
	
	applied = applied.substring(0, 4) + "-" + applied.substring(4, 6) + "-" + applied.substring(6, 8) + " "
				+ applied.substring(8,10) + ":" + applied.substring(10, 12) + ":" + applied.substring(12, 14);
	applied = DateUtil.getFormattedDate(applied, dateFormat);
	
	created = created.substring(0, 4) + "-" + created.substring(4, 6) + "-" + created.substring(6, 8) + " "
				+ created.substring(8,10) + ":" + created.substring(10, 12) + ":" + created.substring(12, 14);
	created = DateUtil.getFormattedDate(created, fullFormat);
	
	modified = modified.substring(0, 4) + "-" + modified.substring(4, 6) + "-" + modified.substring(6, 8) + " "
				+ modified.substring(8,10) + ":" + modified.substring(10, 12) + ":" + modified.substring(12, 14);
	modified = DateUtil.getFormattedDate(modified, fullFormat);
	
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.unit.info' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<script language="javascript">
	function init() {
		$('#unitName').val('<%=unitName%>');
		$('#unitId').val('<%=unitId%>');
		var description = "<%=description%>";
		var description = replaceAll("<%=description%>","<br />","\n");
		description = replaceAll(description,"&quot;","\"");
		$('#description').val(description);
		$('#retentionPeriod').val('<%=retentionPeriod%>');
		$('#applied').val('<%=applied%>');
		$('#created').val('<%=created%>');
		$('#modified').val('<%=modified%>');
		
		<% if ("1".equals(allDocOrForm)) { // 모든문서 선택시 안보이게 처리. %>
			$('#allDocOrForm').hide();
		<% } else { %>
			var form = document.getElementById('form');
			var serialNumber = '<%=serialNumber%>';
			if(serialNumber == 'Y') {
				form.serialNumber_Y.checked = true;
				form.serialNumber_N.checked = false;
			} else {
				form.serialNumber_Y.checked = false;
				form.serialNumber_N.checked = true;
			}
		<% }%>
	}

	function del() {
		if ( '<%=bindCount%>' != '0' )
		{
			alert("<spring:message code='bind.msg.bind.use.not.delete'/>");
			return;
		}

		if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{
			var unitId = "<%=unitId%>";
			var compId = "<%=compId%>";
			var deptId = "<%=deptId%>";
			
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/simple/remove.do',
				type : 'POST',
				dataType : 'json',
				data : {
					compId : compId,
					deptId : deptId,
					unitId : unitId
				},
				success : function(data) {
					if(data.success) {
						// 다국어 추가
						deleteResource("<%= resourceId %>");
						
						var f = opener.document.listForm;
						f.method = 'POST';
						f.submit();
						alert("<spring:message code='bind.msg.delete.completed'/>");
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

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
			<span class="pop_title77"><spring:message code='bind.title.unit.info' /></span>
			</td>
		</tr>
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
										<form id="form" method="POST">
										<input type='hidden' id='unitId' name='unitId' />
											<acube:tableFrame class="td_table table_border borderBottom">
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name'/> <spring:message code='common.title.essentiality'/></td>
												<td class="b_td"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input_read" id="unitName" name="unitName" style="width:100%" readonly></td>
														</tr>
													</table>
												</td>
											</tr>    
												
											<tr bgcolor="#ffffff"> 
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/></td>
												<td height=68 class="b_td"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea class="input_read_01" id="description" name="description" style="width:100%;overflow:auto" rows="5"  readonly></textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.type'/></td>
												<td class="b_td"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input_read" id="unitType" name="unitType" value='<%= m.getMessage("bind.code."+unitType, null, langType) %>' style="width:100%" readonly></td>
														</tr>
													</table>
												</td>
											</tr>
							
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.retention.period'/> <spring:message code='common.title.essentiality'/></td>	
												<td class="b_td"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
													<td><input type="text" class="input_read" value='<%= m.getMessage("bind.code."+retentionPeriod, null, langType) %>' size="5" readonly></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff" id="allDocOrForm">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.use.serial.number'/></td>	
												<td class="b_td"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><input type="radio" id="serialNumber_Y" name="serialNumber" value="Y" checked="checked"/> <spring:message code='bind.obj.use'/></td>
														<td class="basic_text"><input type="radio" id="serialNumber_N" name="serialNumber" value="N" /> <spring:message code='bind.obj.not.use'/></td>
													</tr>
													</table>
												</td>
											</tr>


											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.applied'/> <spring:message code='common.title.essentiality'/></td>
												<td class="b_td"><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td>
																<input type="text" class="input_read" name="calc" id="calc" readonly size="11" value="<%= applied %>">
																<input type="hidden" name="applied" id="applied" value="<%= applied %>">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.created'/></td>
												<td class="b_td"><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input_read" id="created" name="created" size="20" readonly></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<td width="20%" class="tb_tit tb_last" nowrap="nowrap"><spring:message code='bind.obj.modified'/></td>
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
											<div id="bind_unit_edit" style="display:block">
											<acube:buttonGroup>
												<acube:button onclick="javascript:del();" value='<%= m.getMessage("bind.button.del", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
												<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("appcom.button.cancel", null, langType) %>' type="2" class="gr" />
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
