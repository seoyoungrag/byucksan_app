<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.sds.acube.app.common.util.AppConfig"%>
<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
MessageSource messageSource = (MessageSource)ctx.getBean("messageSource");
String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery-1.6.2.min.js"></script> 
<script>


function login() {
	var loginIdVal = $("#loginId").val();
	var passwordVal = $("#password").val();
	
	 $.ajax({
			type:'post',
			timeout: 5000,
			async:false,
			dataType:'json',
			url:'<%=webUri%>/app/webService/login/loginProcess.do?loginId='+$("#loginId").val()+'&password='+$('#password').val(),
			success:function(data) {

				if(data.webServiceStatus == 200){
					var D1 = data.D1;
					document.location.href = '<%=webUri%>/app/list/webservice/approval/receive.do?D1='+D1;
				}
				
			},
			error:function(data,sataus,err) {						
				alert("로그인 실패 했습니다.");
			}
		}); 
	
	
	
	
	
}

</script>
</head>

<body>


<form id="listForm">
<input type="text" style="width:100px; height:30px;" id="loginId" name="loginId" value=""/>
<input type="password" style="width:100px; height:30px;" id="password" name="password" value=""/>
<input type="button" style="width:100px; height:30px;" id="loginBtn" name="loginBtn" onClick = "login()"/>
</form>



</body>
</html>