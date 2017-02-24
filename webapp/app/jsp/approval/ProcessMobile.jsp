<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : approval.jsp 
 *  Description : 결재공통처리 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String result = (String) request.getAttribute("result");
	String message = (String) request.getAttribute("message");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript">
function processMobile() {
	if (document.mobile.docId.value == "") {
		alert("문서ID가 입력되지 않았습니다.");
	}

	document.mobile.submit();
}
</script>
</head>
<body>
<table width="100%">
	<tr>
		<td width="25%" class="tb_tit">처리결과</td>
		<td width="75%" class="tb_left"><%=(result == null) ? "" : result%></td>
	</tr>
	<tr>
		<td width="25%" class="tb_tit">처리메세지</td>
		<td width="75%" class="tb_left"><%=(message == null) ? "" : message%></td>
	</tr>
</table>
<form id="mobile" name="mobile" method="post" action="<%=webUri%>/app/approval/processMobile.do"">
<table width="100%">
	<tr>
		<td width="25%" class="tb_tit">문서ID</td>
		<td width="75%" class="tb_left"><input type="text" id="docId" name="docId" size="100" class="input" style="width:100%;"></input></td>
	</tr>
	<tr>
		<td width="25%" class="tb_tit">처리코드</td>
		<td width="75%" class="tb_left">
			<select id="actioncode" name="actioncode" class="select_9pt" style="width:100%;">
				<option value="A1">승인</option>
				<option value="A2">반려</option>
				<option value="A3">대기</option>
				<option value="A4">보류</option>
				<option value="A5">합의</option>
				<option value="A6">검토</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="25%" class="tb_tit">처리의견</td>
		<td width="75%" class="tb_left"><textarea id="appopinion" name="appopinion" size="5" style="width:100%;"></textarea></td>
	</tr>
	<tr>
		<td width="25%" class="tb_tit">요청유형</td>
		<td width="75%" class="tb_left">
			<select id="reqtype" name="reqtype" class="select_9pt" style="width:100%;">
				<option value="updateApproval">문서처리</option>
			</select>
		</td>
	</tr>
</table>
</form>
<table width="100%">
	<tr>
		<td width="100%" align="right"><button onclick="processMobile();return(false);">처리</button></td>
	</tr>
</table>
</body>
</html>