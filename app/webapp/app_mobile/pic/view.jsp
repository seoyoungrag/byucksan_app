<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.sds.acube.app.common.util.AppConfig"%>
<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
MessageSource messageSource = (MessageSource)ctx.getBean("messageSource");
String webUri = AppConfig.getProperty("web_uri", "/ep", "path");

List results = (ArrayList)request.getAttribute("list");
if(results == null){
	results = new ArrayList();
}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Mini Ajax File Upload Form</title>

		<!-- Google web fonts -->
		<link href="http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700" rel='stylesheet' />

		<!-- The main CSS file -->
		<link href="<%=webUri%>/app/ref/assets/css/style.css" rel="stylesheet" />
	</head>

	<body>
		<%
		for(int i=0; i<results.size(); i++){
		%>
			<div id="drop">
				<img style="width:597px; height:325px; margin-bottom:1px;" src="<%=webUri%>/tempPic/<%=(String)results.get(i)%>" />
			</div>
		<%} %>
			



		<footer>
            <div id="tzine-actions">
            	<a id="tzine-download" href="/ep/app_mobile/pic/upload.jsp" title="사진업로드">사진업로드</a>
            </div>
        </footer>
        
		<!-- JavaScript Includes -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script src="<%=webUri%>/app/ref/assets/js/jquery.knob.js"></script>

		<!-- jQuery File Upload Dependencies -->
		<script src="<%=webUri%>/app/ref/assets/js/jquery.ui.widget.js"></script>
		<script src="<%=webUri%>/app/ref/assets/js/jquery.iframe-transport.js"></script>
		<script src="<%=webUri%>/app/ref/assets/js/jquery.fileupload.js"></script>
		
		<!-- Our main JS file -->
		<script src="<%=webUri%>/app/ref/assets/js/script.js"></script>


	</body>
</html>