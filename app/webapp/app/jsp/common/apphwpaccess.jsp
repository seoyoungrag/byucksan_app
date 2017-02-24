<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.jconfig.Configuration" %>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%
	String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
%>
<script type="text/javascript">
// Define AppHwpAccess Control

// ///////////////////////////////////////
// Initialize AppHwpAccess Control
// ///////////////////////////////////////

document.writeln("<object id='AppHwpAccess' width='0' height='0' classid='CLSID:E896E7EF-C94B-44DB-AFB0-B33F918D0E28'");
document.writeln(" codeBase='<%=webUri%>/app/ref/cabfile/AppHwpAccess.cab#version=1,0,0,1'>");
document.writeln("</object>");
</script>