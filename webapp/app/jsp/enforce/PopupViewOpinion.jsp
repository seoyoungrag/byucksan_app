<%@ page import="java.util.Locale" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/**  
 *  Class Name  : PopupSendOpinion.jsp 
 *  Description : 의견조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.18
 *   수 정 자 : 정태환 
 *   수정내용 : 요구사항 반영 
 * 
 *  @author  정태환
 *  @since 2011. 4. 18 
 *  @version 1.0 
 */ 
%> 				 
<% 
	response.setHeader("pragma","no-cache");	

	// 의견을 보여준다.
	String popTitle =  messageSource.getMessage("approval.enforce.opinion" , null, langType);

	String etcTitle =  CommonUtil.nullTrim((String) request.getParameter("popupTitle"));  //파라미터로 제목을 받아온다
	if(!"".equals(etcTitle)) {
		popTitle =  etcTitle;
	}	
		
	String popOpinion = CommonUtil.nl2br(request.getParameter("popupOpinion"));
	if (popOpinion == null) popOpinion="";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<title><%=popTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"> 
$(document).ready(function(){
	init();
});

function init() {
	$("#popOpinion").val(unescapeCarriageReturn(unescapeJavaScript(replaceAll("<%=popOpinion%>","<br />","\n"))));
}
function closePopup() {
	if(opener.enableButton != null ) {
		opener.enableButton();
	}
	//창을 닫는다.
	self.close();
	
}
</script>
</head>
<body onunload="closePopup();" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<acube:titleBar><%=popTitle%></acube:titleBar>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->
<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" height="100%">
		<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="170" valign="top" >
					<textarea id="popOpinion" name="popOpinion" cols="60" rows="14" style="width:360px; height:160px%" readonly></textarea>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</acube:tableFrame>

<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->

<acube:buttonGroup align="right">
<% String msg1 = messageSource.getMessage("approval.enforce.button.close" , null, langType);  %>
<acube:button onclick="closePopup();" value="<%=msg1%>" />
</acube:buttonGroup>
</acube:outerFrame>

</body>
</html>