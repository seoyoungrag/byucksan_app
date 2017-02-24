
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="com.sds.acube.app.common.util.AppCode" %>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil" %>

	String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
	String sessionUserId = (String) session.getAttribute("USER_ID");
	if (sessionUserId == null || "".equals(sessionUserId)) {
		response.sendRedirect(webUri + "/app/jsp/error/ErrorNoSession.jsp");
	}

	String externalWeb = AppConfig.getProperty("external_web", "192.168.115.101,192.168.115.111,192.168.115.112", "path");
	String serverName = request.getServerName();

    //String webUrl = AppConfig.getProperty("web_url", "", "path");
    String webUrl = "http://" + serverName + ":" + request.getServerPort();
	
	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
    }
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getRemoteAddr());
    }
	boolean isExtWeb = (externalWeb.indexOf(userIp) == -1 && externalWeb.indexOf(serverName) == -1) ? false : true;
	session.setAttribute("IS_EXTWEB", isExtWeb);
%>