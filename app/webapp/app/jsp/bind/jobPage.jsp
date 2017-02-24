<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList" %>
<%@ page import="com.sds.acube.app.design.AcubeListRow" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLDecoder"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
	response.setHeader("pragma","no-cache");
	MessageSource m = messageSource;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.job' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<SCRIPT LANGUAGE="javascript">

	function init() {
		var arrange = '${arrange}';
		var binding = '${binding}';

		var f = document.actionForm;
		if(arrange == 'Y') {
			document.getElementById('arrange_ok').checked = true;
			document.getElementById('binding_ok').disabled = true;
			document.getElementById('binding_no').disabled = true;
		} else if(binding == 'Y') {
			document.getElementById('arrange_ok').disabled = true;
			document.getElementById('arrange_no').disabled = true;
			document.getElementById('binding_ok').checked = true;
		} else {
			document.getElementById('arrange_no').disabled = true;
			document.getElementById('binding_no').disabled = true;
		}
 	}
 
	function actionJob() {
		var form = document.actionForm;
		
		var rtnval = '';
		for(var i = 0; i < form.length; i++) {
			if(form.elements[i].checked) {
				rtnval = form.elements[i].value;
				break;
			}
		}

		var msgCode = '';
		var arrange = '';
		var binding = '';
		
		if(rtnval == 'arrange_ok') {
			msgCode = "<spring:message code='bind.obj.ok.arrange' />";
			arrange = 'Y';
			binding = '';			
		} else if(rtnval == 'arrange_no') {
			msgCode = "<spring:message code='bind.obj.cancel.arrange' />";
			arrange = 'N';
			binding = '';
		} else if(rtnval == 'binding_ok') {
			msgCode = "<spring:message code='bind.obj.ok.binding' />";
			arrange = 'Y';
			binding = 'Y';
		} else if(rtnval == 'binding_no') {
			msgCode = "<spring:message code='bind.obj.cancel.binding' />";
			arrange = 'N';
			binding = 'N';
		}

		if(arrange == '' && binding == '') {
			alert("<spring:message code='bind.msg.select.job' />");
			return;
		}
		
		$.ajax({
			url : '<%=webUri%>/app/bind/actionJob.do',
			type : 'POST',
			dataType : 'json',
			data : {
				bindId : '${bindId}',
				deptId : '${deptId}',
				arrange : arrange,
				binding : binding
			},
			success : function(data) {
				if(data.success) {
					alert("<spring:message code='bind.msg.job.completed' arguments='" + msgCode + "' />");

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

	window.onload = init;

</SCRIPT>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame popup="true">
<!-------팝업 타이틀 S --------->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<tr>
    <td>
	<span class="pop_title77"><spring:message code='bind.title.job' />
	</span>
			</td>
		</tr>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->

<form name="actionForm" method="POST" action="<%=webUri%>/app/bind/actionJob.do">
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="td_table table_border">
                    <tr bgcolor="#ffffff">
						<td width="20" style="padding : 2px" class="tb_tit">
							<input type="radio" name="bindJob" id="arrange_ok" value="arrange_ok">
						</td>  
						<td style="padding : 5px" class="tb_tit">
							<spring:message code='bind.obj.ok.arrange' /> (<spring:message code='bind.msg.arrange.description' />)
						</td>
					</tr>
                    <tr bgcolor="#ffffff">
						<td width="20" style="padding :2px" class="tb_tit">
							<input type="radio" name="bindJob" id="arrange_no" value="arrange_no">
						</td>
						<td style="padding : 5px" class="tb_tit">
							<spring:message code='bind.obj.cancel.arrange' />
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="20" style="padding : 2px" class="tb_tit" >
							<input type="radio" name="bindJob" id="binding_ok" value="binding_ok">
						</td>
						<td style="padding : 5px" class="tb_tit">
							<spring:message code='bind.obj.ok.binding' /> (<spring:message code='bind.msg.binding.description' />)
						</td>
						<td></td>
					</tr>
                    <tr bgcolor="#ffffff">
						<td width="20" style="padding : 2px" class="tb_tit" >
							<input type="radio" name="bindJob" id="binding_no" value="binding_no">
						</td>
						<td style="padding : 5px" class="tb_tit">
							<spring:message code='bind.obj.cancel.binding' />
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<!-------내용조회  E --------->

		<!-------여백 H5  S --------->
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S ---------> 
				<acube:buttonGroup align = "center">
					<acube:button onclick="javascript:actionJob();" value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("bind.button.cancel", null, langType) %>' type="2" class="gr" />
				</acube:buttonGroup> 
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	<!--------------------------------------------본문 Table E -------------------------------------->
</form>
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>