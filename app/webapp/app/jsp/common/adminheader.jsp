
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="com.sds.acube.app.common.util.MemoryUtil" %>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil" %>
     //	Log logger = LogFactory.getLog(request.getRequestURI());
     Log logger = LogFactory.getLog(this.getClass().getName());
     AppCode appCode = MemoryUtil.getCodeInstance();

     WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
     MessageSource messageSource = (MessageSource) ctx.getBean("messageSource");
     Locale langType = (Locale) session.getAttribute("LANG_TYPE");
     String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
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

     String roleCode = (String) session.getAttribute("ROLE_CODES");
	 String adminCode = AppConfig.getProperty("role_admin", "", "role");
	 String appAdminCode = AppConfig.getProperty("role_appadmin", "", "role");
	 String iamCode = AppConfig.getProperty("role_iam", "", "role");
     String adminIp = AppConfig.getProperty("admin", "", "etc"); 
     if ( !( roleCode.indexOf(adminCode) >= 0 || roleCode.indexOf(appAdminCode) >= 0 || roleCode.indexOf(iamCode) >= 0 ) || (!"".equals(adminIp) && adminIp.indexOf(userIp) == -1) || isExtWeb) {
 		response.sendRedirect(webUri + "/app/jsp/error/ErrorLimited.jsp");
     }
 %>