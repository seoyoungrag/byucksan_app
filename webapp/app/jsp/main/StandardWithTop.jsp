<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
//UserProfileVO userProfile = (UserProfileVO) request.getAttribute("jsonResult");
UserProfileVO userProfile = (UserProfileVO) session.getAttribute("userProfile");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${userProfile.userName}(${userProfile.compName}/${userProfile.deptName})</title>
</head>
<frameset rows=120,* frameborder=0> 
	<frame name="top" src="<%=webUri%>/app/top.do" scrolling="no" noresize/>
	<frame name="content" src="<%=webUri%>/app/index.do" noresize/>
</frameset>
</html>
