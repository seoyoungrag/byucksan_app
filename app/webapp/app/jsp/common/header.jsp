
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://www.anyframejava.org/tags" prefix="anyframe" %><%@ taglib uri="/WEB-INF/tlds/acube_design.tld" prefix="acube" %>
<%@ page import="org.apache.commons.logging.Log" %><%@ page import="org.apache.commons.logging.LogFactory" %><%@ page import="org.jconfig.Configuration" %><%@ page import="com.sds.acube.app.common.util.AppConfig" %><%@ page import="com.sds.acube.app.common.util.MemoryUtil" %>
<%@ page import="com.sds.acube.app.common.util.AppCode" %>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil" %><%@ page import="com.sds.acube.app.common.util.CommonUtil" %><%@ page import="java.util.Locale" %><%@ page import="org.springframework.web.context.WebApplicationContext" %> <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %><%@ page import="org.springframework.context.MessageSource" %> <%//	Log logger = LogFactory.getLog(request.getRequestURI());	Log logger = LogFactory.getLog(this.getClass().getName());	AppCode appCode = MemoryUtil.getCodeInstance();
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());	MessageSource messageSource = (MessageSource)ctx.getBean("messageSource");	Locale langType = (Locale)session.getAttribute("LANG_TYPE");
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
