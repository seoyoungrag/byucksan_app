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
	
	String currentYear = (String)request.getAttribute("year");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.transfer' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<SCRIPT LANGUAGE="javascript">

	function init() {
	}

	function transfer() {
		var form = document.bindForm;

		var bindId = form.bindId.value;
		var bindName = form.bindName.value;
		var deptId = form.deptId.value;
		var targetId = form.targetId.value;
		var targetName = form.targetName.value;

		if(targetId == null || targetId == '') {
			alert(select_transfer_dept);
			form.targetId.focus();
			return;
		} else if (deptId == targetId) {
			alert("<spring:message code='bind.msg.not.transfer.same.dept' />");
			return;
		}

		if(confirm("<spring:message code='bind.msg.confirm.bind.transfer' arguments='" + bindName + "," + targetName + "'/>")) {
			$.ajax({
				url : '<%=webUri%>/app/bind/bindTranspose.do',
				type : 'POST',
				dataType : 'json',
				data : {
					bindId : bindId,
					deptId : deptId,
					targetId : targetId
				},
				success : function(data) {
					if(data.success) {
						alert(msg_completed_transfer);

						var f = opener.document.listForm;
						f.action = '<%=webUri%>/app/bind/list.do';
						f.target = '_self';
						f.submit();
						
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

	function goSelectDept() {
		openType = 'SelectDept';
		
		var width = 500;
	    var height = 400;
	    	    
		// openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2', width, height, 'no');
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		debugger;
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {

			} else if('SelectDept' == openType) {
				//obj.orgId
				bindForm.targetId.value = obj.orgId;
				indForm.targetName.value = obj.orgName;
				
			}
		}
	}
	
	var rootText = "${compName}";
	var select_transfer_dept = "<spring:message code='bind.msg.select.transfer.dept'/>";
	var msg_completed_transfer = "<spring:message code='bind.msg.completed.transfer'/>";

	var webUri = '<%=webUri%>';
	window.onload = init;

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='bind.title.transfer' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">

	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/bind/trasfer.do">
	<input type="hidden" name="bindId" value="${row.bindId}"/>
	<input type="hidden" name="bindName" value="${row.bindName}"/>
	<input type="hidden" name="deptId" value="${row.deptId}"/>
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.name' /></td>
						<td style="padding:3px">${row.bindName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.code' /></td>
						<td style="padding:3px">${row.unitId}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name' /></td>
						<td style="padding:3px">${row.unitName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.type'/></td>
						<td style="padding:3px"><spring:message code='bind.code.${row.docType}' /></td>
					</tr>
	
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.retention.period' /></td>
						<td style="padding:3px"><spring:message code='bind.code.${row.retentionPeriod}' /></td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit" nowrap="nowrap">
							<spring:message code='bind.obj.dept.select' /> <spring:message code='common.title.essentiality'/>
						</td>
						<td height="240">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr bgcolor="#ffffff">
								<td>
									<input type="text" class="input_read" name="targetId" readonly size="12">
									<input type="text" class="input_read" name="targetName" readonly size="26">
								</td>
							</tr>
							<tr>
								<td valign="top">
									<acube:button onclick="javascript:goSelectDept();"
										value='부서' />
									<acube:space between="button" />
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</acube:tableFrame>  
			</td>
		</tr>
		<!-------내용조회  E --------->

		<!-------여백 H5  S --------->
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:transfer();"
						value='<%= m.getMessage("bind.button.transfer", null, langType) %>'
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