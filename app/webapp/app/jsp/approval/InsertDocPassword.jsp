<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
    String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt411 = appCode.getProperty("OPT411", "OPT411", "OPT"); //보안 - 1 : 로그인패스워드, 2 : 비밀번호
	opt411 = envOptionAPIService.selectOptionValue(compId, opt411);
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType);	
	String cancelBtn = messageSource.getMessage("approval.button.cancel", null, langType); 
	String securityPass = request.getParameter("securitypass");
	String viewType = CommonUtil.nullTrim(request.getParameter("viewtype"));
	if ("".equals(viewType))
	    viewType = "doc";
	
	String type = request.getParameter("type");
	String docId = request.getParameter("docId");
	
	// 첨부파일 ID
	String attachId = request.getParameter("attachId");
	
	// 관련문서 ID
	String relatedDocId = request.getParameter("relatedDocId");
	String usingType = request.getParameter("usingType");
	String electronDocYn = request.getParameter("electronDocYn");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><% 
				if(opt411.equals("2"))
				{
			%>
					<spring:message code='approval.title.security.password'/>
			<% 
				}else if(opt411.equals("1"))
				{
			%>
					<spring:message code='approval.title.login.password'/>
			<%} %>
</title>
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
		alert("<spring:message code='approval.msg.fail.securitylpassword'/>");
		$("#password").focus();
		return false;
	} else {
		if("<%=opt411%>" == "2")
		{
			/*if("<%=securityPass%>" == password)
				submitCompareResult(true);
			else 
			{
				alert("<spring:message code='approval.msg.fail.securitylpassword'/>");
				$("#password").val("");
				$("#password").focus();
				return false;
			}*/
			prepareSeed();
			$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
			$.ajaxSetup({async:false});
			$.post("<%=webUri%>/app/appcom/compareAppDocPwd.do", $("#passwordForm").serialize(), function(data){
				if (data.result == "success") {
					submitCompareResult(true);
				} else {
					alert(data.message);
					$("#password").focus();
					return false;
				}
			}, 'json');
		}
		else if("<%=opt411%>" == "1")
		{
				prepareSeed();
				$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
				$.ajaxSetup({async:false});
				$.post("<%=webUri%>/app/appcom/compareAppLoginPwd.do", $("#passwordForm").serialize(), function(data){
					if (data.result == "success") {
						submitCompareResult(true);
					} else {
						alert(data.message);
						$("#password").focus();
						return false;
					}
				}, 'json');
		}
	}
}

function submitCompareResult(result) {
<%	if ("doc".equals(viewType)) {	%>
		if (opener != null && opener.selectAppDocSecResut) {
			opener.selectAppDocSecResut(result,"<%=type%>");
		}
<%	} else if ("attach".equals(viewType)) {	%>
		if (opener != null && opener.viewAttach) {
			opener.viewAttach("<%=attachId%>");
		}
<%	} else if ("relateddoc".equals(viewType)) {	%>
		if (opener != null && opener.selectRelatedAppDoc) {
			opener.selectRelatedAppDoc("<%=relatedDocId%>", "<%=usingType%>", "<%=electronDocYn%>");
		}
<%	} else {	%>
		if (opener != null && opener.selectAppDocSecResut) {
			opener.selectAppDocSecResut(result,"<%=type%>");
		}
<%	}	%>
	window.close();
}

function cancelPassword() {
		window.close();
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
			<td><acube:titleBar>
			<% 
				if(opt411.equals("2"))
				{
			%>
					<spring:message code='approval.title.security.password'/>
			<% 
				}else if(opt411.equals("1"))
				{
			%>
					<spring:message code='approval.title.login.password'/>
			<%} %>
			</acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
						<td width="25%" class="tb_tit"><spring:message code='approval.security.password'/><spring:message code='common.title.essentiality'/></td>
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
	<input type="hidden" id="docId" name="docId" value="<%=docId%>"></input>
	<input type="hidden" id="type" name="type" value="<%=type%>"></input>
</form>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>