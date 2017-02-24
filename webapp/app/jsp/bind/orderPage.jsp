<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.change.order' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<script LANGUAGE="JavaScript">
	
	function init() {
	}

	function move_step(dir){
		var selectObj = document.listForm.targetOpt;

		el = selectObj.selectedIndex
		if (el != -1 && el != null) {
			o_text = selectObj.options[el].text;
			o_value = selectObj.options[el].value;
			if (el > 0 && dir == -1) {
				selectObj.options[el].text = selectObj.options[el-1].text;
				selectObj.options[el].value = selectObj.options[el-1].value;
				selectObj.options[el-1].text = o_text;
				selectObj.options[el-1].value = o_value;
				selectObj.selectedIndex--;
			} else if (el > 0 && dir == -2) {
				for ( kk = el-1 ; kk >= 0 ; kk-- ) {
					temp_text  = selectObj.options[kk].text
					temp_value = selectObj.options[kk].value
					selectObj.options[kk+1].text = temp_text;
					selectObj.options[kk+1].value = temp_value;
				}
				selectObj.options[0].text = o_text;
				selectObj.options[0].value = o_value;
				selectObj.selectedIndex=0

			} else if (el < selectObj.length-1 && dir == 1) {
				selectObj.options[el].text = selectObj.options[el+1].text;
				selectObj.options[el].value = selectObj.options[el+1].value;
				selectObj.options[el+1].text = o_text;
				selectObj.options[el+1].value = o_value;
				selectObj.selectedIndex++;
			} else if (el < selectObj.length-1 && dir == 2) {
				for ( kk=el ; kk < selectObj.length-1 ; kk++ ) {
					temp_text  = selectObj.options[kk+1].text
					temp_value = selectObj.options[kk+1].value
					selectObj.options[kk].text = temp_text;
					selectObj.options[kk].value = temp_value;
				}
				selectObj.options[selectObj.length-1].text = o_text;
				selectObj.options[selectObj.length-1].value = o_value;
				selectObj.selectedIndex=selectObj.length-1
			}
		}
	}

	function actionObj() {
		var f = document.listForm;
		
		var sourceOpt = f.targetOpt;
		var bindIds = '';
		for(var i = 0; i < sourceOpt.length; i++) {
			bindIds += (bindIds == '' ? '' : ',') + sourceOpt.options[i].value;
		}

		f.bindIds.value = bindIds;

		if(confirm("<spring:message code='bind.msg.confirm.save'/>")) {
			$.ajax({
				url : '<%=webUri%>/app/bind/actionOrder.do',
				type : 'POST',
				dataType : 'json',
				data : {
					deptId : '${deptId}',
					bindIds : bindIds
				},
				success : function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.completed'/>");
						
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
	
	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/actionOrder.do'>
	<input type="hidden" name="bindIds" value="" />
	<input type="hidden" name="deptId" value="${deptId}" />

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.change.order" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td align="center">
			<table height="250" width="470" border='0' cellspacing='0' cellpadding='0'>
				<tr valign="top">
					<td width="100%">
						<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="1" class="table" >
							<tr valign="top">
								<td>
									<select name="targetOpt" size="25" style="width:100%;">
									<c:forEach items="${rows}" var="row" varStatus="seq">
										<option value="${row.bindId}"${seq.count == '1' ? " selected" : ""}>[${row.unitName}] ${row.bindName}</option>
									</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td height="300">
						<table height="100%" style='padding: 1px;' border='0' cellspacing='0' cellpadding='0'>
							<tr valign="top" height="22"><td><img src="<%=webUri%>/app/ref/image/up_icon.gif" style="cursor:pointer" onclick="javascript:move_step(-2);"></td></tr>
							<tr valign="top"><td><img src="<%=webUri%>/app/ref/image/bu_up.gif" style="cursor:pointer" onclick="javascript:move_step(-1);"></td></tr>
							<tr valign="bottom"><td><img src="<%=webUri%>/app/ref/image/bu_down.gif" style="cursor:pointer" onclick="javascript:move_step(1);"></td></tr>
							<tr valign="bottom" height="22"><td><img src="<%=webUri%>/app/ref/image/dn_icon.gif" style="cursor:pointer" onclick="javascript:move_step(2);"></td></tr>
						</table>
					</td>
				</tr>
			</table>
			</td>
			
		</tr>
		
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>

		<tr>
			<td>
				<!-------기능버튼 Table S ---------> 
				<acube:buttonGroup align = "center">
					<acube:button onclick="javascript:actionObj();" value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("bind.button.cancel", null, langType) %>' type="2" class="gr" />
				</acube:buttonGroup> 
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
		
	</table>
	</form>
</acube:outerFrame>

</body>
</html>
