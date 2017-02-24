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
		<meta charset="utf-8"/>
		<title>Mini Ajax File Upload Form</title>

		<!-- Google web fonts -->
		<link href="http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700" rel='stylesheet' />

		<!-- The main CSS file -->
		<link href="<%=webUri%>/app/ref/assets/css/style.css" rel="stylesheet" />
	</head>

	<body>

		<form id="upload" method="post" action="/ep/app/list/webservice/approval/uploadPic.do" enctype="multipart/form-data">
			<div id="drop">
				Drop Here

				<a>Browse</a>
				<input type="file" name="upl" multiple />
			</div>

			<ul>
				<!-- The file uploads will be shown here -->
			</ul>

		</form>

		<footer>
            <div id="tzine-actions">
	            <a id="tzine-download" href="/ep/app/list/webservice/approval/viewPic.do" title="이미지보기">이미지보기</a>
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