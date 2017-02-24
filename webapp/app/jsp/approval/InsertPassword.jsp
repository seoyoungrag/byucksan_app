<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertPassword.jsp 
 *  Description : 결재 비밀번호
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
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType);	
	String cancelBtn = messageSource.getMessage("approval.button.cancel", null, langType); 
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.opinion'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
function chkKeyPress() {
	var keyCode = window.event.keyCode;
	switch (keyCode)
	{
		case 13:
		{
			confirmPassword();
		}
		default:
			break;
	}
}

function confirmPassword() {
	var password = $.trim($("#password").val());
	if (password == $.trim("")) {
		alert("<spring:message code='approval.msg.need.approvalpassword'/>");
		$("#password").focus();
		return false;
	} else {
		prepareSeed();
		$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
		$.ajaxSetup({async:false});
		$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#passwordForm").serialize(), function(data){
			if (data.result == "success") {
				submitPassword();
			} else {
				alert(data.message);
				$("#password").focus();
				return false;
			}
		}, 'json');
	}
}

function submitPassword() {
	 window.moveBy(0,0 );   
	if (opener != null && opener.setPassword) {
		opener.setPassword($("#encryptedpassword").val(), $("#roundkey").val());
		window.close();
	}
}

function cancelPassword() {
	if (confirm("<spring:message code='approval.msg.cancelwithdrawl'/>")) {
		window.close();
	}	
}

function prepareSeed() {

    var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();

    if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
        var userkey = document.getElementById("seedOCX").GetUserKey();
        currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
    }

    var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);

    $('#roundkey').val(roundkey_c);
}

</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.approval.password'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
						<td width="25%" class="tb_tit"><spring:message code='approval.title.approval.password'/><spring:message code='common.title.essentiality'/></td>
						<td width="75%" class="tb_left_bg">
							<input type="password" id="password" name="password" style=" width:100%" class="input" onkeydown="chkKeyPress();return(true);"></input>
						</td>
				</tr>
			</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="confirmPassword();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="cancelPassword();return(false);" value="<%=cancelBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<form id="passwordForm" name="passwordForm" method="post" onsubmit="return(false);">
	<input type="hidden" id="roundkey" name="roundkey"></input>
	<input type="hidden" id="encryptedpassword" name="encryptedpassword"></input>
</form>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>