<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : EnvAppCodeMng.jsp 
 *  Description : 전자결재 코드관리 - 코드설명 수정(새창)
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.24 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 24 
 *  @version 1.0 
 */ 
%>
<% 
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.applines" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	.range {display:inline-block;width:98px;height:25px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		if (opener != null && opener.getCodeId && opener.getCodeName && opener.getDescription) {
			$('#codeId').val(opener.getCodeId());
			$('#codeName').val(opener.getCodeName());
			$('textarea').text(opener.getDescription());
			$('#discription').focus();
		}
	}

	function updateAppCode(obj){
		var result = null;
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/admin/updateEnvAppCode.do", $('form').serialize(), function(data){
			result = data;
		});
		if (result == "success") {
			alert("<spring:message code='env.option.msg.success.modify'/>");
		} else {
			alert("<spring:message code='env.option.msg.error'/>");
		}
		if (opener != null && opener.onLineClick) {
			opener.mReload();
		}
		closePopup();		
	}
	
	function closePopup() {
		window.close();
	}
</script>
</head> 
<body>
<acube:outerFrame>

	<acube:titleBar><spring:message code="env.option.menu.sub.20" /></acube:titleBar>
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	
	<acube:tableFrame class="td_table">
	<form:form modelAttribute="" method="post" name="appCodeForm" id="appCodeForm">
		<tr bgcolor="#ffffff">
			<td width="25%" class="tb_tit_left">
				<nobr><spring:message code="env.codemng.form.03"/></nobr><!-- 코드 -->
			</td>
			<td width="75%" class="tb_left_bg">
				<input type="text" id="codeId" name="codeId" readOnly style="border:0" />
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td width="25%" class="tb_tit_left">
				<nobr><spring:message code="env.codemng.form.05"/></nobr><!-- 코드명 -->
			</td>
			<td width="75%" class="tb_left_bg">
				<input type="text" id="codeName" name="codeName" readOnly style="border:0" />
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td width="25%" class="tb_tit_left">
				<nobr><spring:message code="env.codemng.form.02"/></nobr><!-- 코드명 -->
			</td>
			<td width="75%" class="tb_left_bg">
				<textarea id="discription" name="discription" style="width:97%;height:70px;"></textarea>
			</td>
		</tr>
	</form:form>
	</acube:tableFrame>

	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	
	<acube:buttonGroup align="right">
	<acube:button id="sendOk" disabledid="" onclick="updateAppCode();" value="<%=confirmBtn%>" />
	<acube:space between="button" />
	<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn%>" />
	</acube:buttonGroup>	
</acube:outerFrame>
</body>	
</html>