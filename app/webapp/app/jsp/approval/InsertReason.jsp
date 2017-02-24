<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertReason.jsp 
 *  Description : 수정사유
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

	String askType = CommonUtil.nullTrim(request.getParameter("askType"));

	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType);
	String cancelBtn = messageSource.getMessage("approval.button.cancel", null, langType); 
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% if ("withdraw".equals(askType)) { %>
<title><spring:message code='approval.title.withdrawopinion'/></title>
<% } else { %>
<title><spring:message code='approval.title.modifyopinion'/></title>
<% } %>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
//암호에서 엔터키 치면 버튼을 누른것으로 처리함
function chkKeyPress() {
	var keyCode = window.event.keyCode;
	switch (keyCode)
	{
		case 13:
		{
			checkReason();
		}
		default:
			break;
	}
}

function checkReason() {
	var reason = $.trim($("#reason").val());
	if (reason == $.trim("")) {
		alert("<spring:message code='approval.msg.need.modifyreason'/>");
		$("#reason").focus();
		return false;
	} 

<% if ("1".equals(opt301)) { %>
	var password = $.trim($("#password").val());
	if (password == $.trim("")) {
		alert("<spring:message code='approval.msg.need.approvalpassword'/>");
		$("#password").focus();
		return false;
	} else {
		prepareSeed();
		$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
		
		$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#reasonForm").serialize(), function(data){
			if (data.result == "success") {
				submitReason(reason);
			} else {
				alert(data.message);
				$("#password").focus();
				return false;
			}
		}, 'json');
	}
<% } else { %>
	submitReason(reason);
<% } %>
}

function submitReason(reason) {
<% if ("2".equals(opt301)) { %>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			if (opener != null && opener.setReason) {
				opener.setReason(reason, "<%=askType%>");
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				window.close();
			}
		} else {
			if (certificate()) {
				opener.setReason(reason, "<%=askType%>");
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				window.close();
			} else {
				return false;
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
			opener.setReason(reason, "<%=askType%>");
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			window.close();
		} else {
			return false;
		}
	});
<% } else { %>
<% 	if ("0".equals(opt301)) { %>
<% 		if ("body".equals(askType)) { %>	
	var message = "<spring:message code='approval.msg.modifybody'/>";
<% 		} else if ("attach".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.modifyattach'/>";
<% 		} else if ("withdraw".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.modifywithdraw'/>";
<% 		} else if ("docinfo".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.modifydocinfo'/>";
<% 		} %>
	if (confirm(message)) {
		if (opener != null && opener.setReason) {
			opener.setReason(reason, "<%=askType%>");
			window.close();
		}
	}
<% 	} else { %>
	if (opener != null && opener.setReason) {
		<% if ("1".equals(opt301)) { %>		
		opener.setReason(reason, "<%=askType%>", $("#encryptedpassword").val(), $("#roundkey").val());
		<% } else { %>
		opener.setReason(reason, "<%=askType%>", "", "");
		<% } %>
		window.close();
	}
<% 	} %>
<% } %>
}

function cancelReason() {
<% if ("body".equals(askType)) { %>	
	var message = "<spring:message code='approval.msg.cancelmodifybody'/>";
<% } else if ("attach".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.cancelmodifyattach'/>";
<% } else if ("withdraw".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.cancelwithdrawl'/>";
<% } else if ("docinfo".equals(askType)) { %>
	var message = "<spring:message code='approval.msg.cancelmodifydocinfo'/>";
<% } %>

if (confirm(message)) {
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
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
<% if ("withdraw".equals(askType)) { %>
			<td><span class="pop_title77"><spring:message code='approval.title.withdrawopinion'/></span></td>
<% } else { %>
			<td><span class="pop_title77"><spring:message code='approval.title.modifyopinion'/></span></td>
<% } %>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
<% if ("withdraw".equals(askType)) { %>
						<td width="25%" class="tb_tit"><spring:message code='approval.title.withdrawreason'/><spring:message code='common.title.essentiality'/></td>
<% } else { %>
						<td width="25%" class="tb_tit"><spring:message code='approval.title.modifyreason'/><spring:message code='common.title.essentiality'/></td>
<% } %>
						<td width="75%" class="tb_left_bg">
							<textarea id="reason" name="reason" style="width:100%;height:100px;overflow:auto;ime-mode:active;"></textarea> 
						</td>
				</tr>
			</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% if ("1".equals(opt301)) { %>		
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
<% } %>		
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="checkReason();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="cancelReason();return(false);" value="<%=cancelBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<% if ("1".equals(opt301)) { %>		
<form id="reasonForm" name="reasonForm" method="post" onsubmit="return(false);">
	<input type="hidden" id="roundkey" name="roundkey"></input>
	<input type="hidden" id="encryptedpassword" name="encryptedpassword"></input>
</form>
<% } %>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>