<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/** 
 *  Class Name  : sessionWindowCheck.jsp
 *  Description : 팝업, iframe 체크
 *  Modification Information 
 * 
 *   수 정 일 : 2016-01-11
 *   수 정 자 : 서영락
 *   수정내용 : 팝업이나 아이프레임이 있는지 확인한다. 뎁스가 깊어지면 현재창을 닫음.
 * 
 */ 
%>
<%@ page import="java.util.Locale" %>
<%@ page import="org.jconfig.Configuration" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %> 
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.MessageSource" %> 
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%
	String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
//현재 스크립트 작업 by 서영락, 2016-01-11
var servletUrl = "";
function openerCheck(){
	if(opener){
		//현재창을 닫는다.
		close();
		//opener가 있으면 opener가 닫혀있는지 확인한다.(예외처리, opener가 닫혀있는 상황에서 로직을 수행하게 되면 iframe여부를 체크하는 로직이 제대로 수행되지 않음.)
		if(!opener.closed){ // closed속성관련내용: http://www.w3schools.com/jsref/prop_win_closed.asp
			//opener가 닫혀있지 않으면, 현재페이지(sessionWindowCheck.jsp)를 목표주소변수로 할당한다.(opener에서 다시 로직을 수행하기 위함)
			servletUrl = "<%=webUri%>/app/jsp/login/sessionWindowCheck.jsp";
			//opener의 iframe 여부를 확인한다.
			parentCheck(opener);
		}
	}else{
		//opener가 없으면, 로그아웃페이지(logout.do)를 목표주소변수로 할당한다.
		servletUrl = "<%=AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal")%>/ep/bics_login.jsp";
		//현재 주소의 iframe 여부를 확인한다.
		parentCheck(window);
	}
}
function parentCheck(targetWindow){
	//iframe 여부를 확인한다.
	if(targetWindow.location !== targetWindow.parent.location){
		//현재 주소와 parent의 주소가 일치하지 않으면, iframe이 존재하는 것이다. 부모 iframe이 존재하면 부모의 iframe여부를 다시 체크한다.
		parentCheck(targetWindow.parent);
	}else{
		//부모 iframe이 존재하지 않으면 현재 주소를 이동시킨다.
		targetWindow.location.href=servletUrl;
	}
}
function initialize() {
	//openerCheck()를 수행하는 것으로 로직이 시작된다.
	openerCheck(); 
}
</script>
</head>
<body onload="initialize();return(false);">
</body>
</html>