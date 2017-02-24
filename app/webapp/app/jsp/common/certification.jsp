<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
    String certiUrl = AppConfig.getProperty("web_url", "", "certification");
%>
<script type="text/javascript">
var certUrl = "<%=certiUrl%>";
var webUrl = "<%=webUrl%>";
var webUri = "<%=webUri%>";
</script>
<script type="text/javascript" src="<%=webUri%>/app/ref/initech/plugin/cert.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/initech/plugin/install.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/initech/plugin/INIplugin.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/initech/plugin/noframe.js"></script>
<script type="text/javascript">
function certificate() {
	InitCache();
	if (KDBCertPasswdCheck()) {
		return true;
	} else {
		return false;
	}
}
//-->
</script>