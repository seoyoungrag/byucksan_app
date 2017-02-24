<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="java.util.List"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title><spring:message code='notice.title.edit' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">

	function add() {
		var form = document.getElementById('bindForm');
	
		var subjectTitle = form.subjectTitle.value.trim();
		if(subjectTitle == null || subjectTitle == '') {
			alert('제목을 입력해 주세요.');
			form.subjectTitle.focus();
			return;
		}
	
		var contentsEtc = form.contentsEtc.value.trim();
		if(contentsEtc == null || contentsEtc == '') {
			alert('내용을 입력해 주세요.');
			form.contentsEtc.focus();
			return;
		}
	
		var reportNo = form.reportNo.value.trim();;
		
		$.ajax({
			url : '<%=webUri%>/app/notice/modify.do',
			type : 'POST',
			dataType : 'json',
			data : {
				subjectTitle : subjectTitle,
				contentsEtc : contentsEtc,
				reportNo : reportNo
			},
			success : function(data) {
				if(data.success) {
					var f = opener.document.listForm;
					f.searchValue.value = '';
					f.target = '_self';
					f.action = '<%=webUri%>/app/notice/list.do';
					f.submit();

					alert("<spring:message code='notice.msg.edit.completed'/>");
					
					window.close();
				} else {
					if(data.msgId) {
						alert(data.msg);
					} else {
						alert("<spring:message code='notice.msg.edit.error'/>");
					}
				}
			},
			error : function(data) {
				alert("<spring:message code='notice.msg.edit.error'/>");
			}
		});
	}


	var webUri = '<%=webUri%>';
	
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='notice.title.edit' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/notice/modify.do" onsubmit="return false;">
	<input type="hidden" name="reportNo" value="${reportNo}" />
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.subjectTitle' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="subjectTitle"  size="60" value="${subjectTitle}"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.contentsEtc' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><textarea tabindex="6" name="contentsEtc" class="input" style="overflow:auto;width:370px;height:80px;">${contentsEtc}</textarea></td>
							</tr>
						</table>
						</td>
					</tr>
				</acube:tableFrame>
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
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:add();"
						value='<%= m.getMessage("bind.button.add", null, langType) %>'
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