<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : Logout.jsp
 *  Description : 로그아웃 페이지 
 *  Modification Information 
 * 
 *   수 정 일 :  2011-06-20
 *   수 정 자 : redcomet
 *   수정내용 : 
 * 
 *  @author  
 *  @since 2011. 06. 20 
 *  @version 1.0 
 *  @see
 */ 
%>
<%@ page import="java.util.Locale" %>
<%@ page import="org.jconfig.Configuration" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %> 
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.MessageSource" %> 
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%
	String logout_redirect_page = request.getParameter("page");

	//if(logout_redirect_page == null || "".equals(logout_redirect_page)) {
	    logout_redirect_page = AppConfig.getProperty("logout_redirect_page", "", "login") + "?mode=xygate";
	//}
	

	String message = (String) request.getAttribute("message");
		
	if(message != null && !"".equals(message)) {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		MessageSource messageSource = (MessageSource)ctx.getBean("messageSource");
		Locale langType = (Locale)session.getAttribute("LANG_TYPE");
		message = messageSource.getMessage(message, null, langType);
	} else {
	    message = "";
	}
	
	session.invalidate();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
function initialize() {
	if("<%=message%>" != "") {
		alert("<%=message%>");
	}
	document.location.href = "<%= logout_redirect_page %>";
}
</script>
</head>
<body onload="initialize();return(false);">
</body>
</html>