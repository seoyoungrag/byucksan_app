<%@ page import="java.net.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.sds.acube.app.login.vo.LoginHistoryVO"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.GuidUtil"%>
<%@ page import="com.sds.acube.app.common.util.AppCode" %>
<%@ page import="com.sds.acube.app.common.util.MemoryUtil" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.login.service.ILoginService" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %> 
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%
    /** 
     *  Class Name  : SessionClear.jsp
     *  Description : 세션 종료 
     *  Modification Information 
     * 
     *   수 정 일 :  2011-07-15
     *   수 정 자 : redcomet
     *   수정내용 : 
     * 
     *  @author  
     *  @since 2011. 07. 15 
     *  @version 1.0 
     *  @see
     */
%>
<%
    if ((String) session.getAttribute("USER_ID") != null && !"".equals((String) session.getAttribute("USER_ID"))) {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		ILoginService loginService = (ILoginService) ctx.getBean("loginService");
		AppCode appCode = MemoryUtil.getCodeInstance();
		String dhu006 = appCode.getProperty("DHU006", "DHU006", "DHU");

		String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
		}
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
		}

		LoginHistoryVO loginHistoryVO = new LoginHistoryVO();
		loginHistoryVO.setCompId((String) session.getAttribute("COMP_ID"));
		loginHistoryVO.setHisId(GuidUtil.getGUID());
		loginHistoryVO.setUserId((String) session.getAttribute("USER_ID"));
		loginHistoryVO.setUserName((String) session.getAttribute("USER_NAME"));
		loginHistoryVO.setPos((String) session.getAttribute("DISPLAY_POSITION"));
		loginHistoryVO.setUserIp(userIp);
		loginHistoryVO.setDeptId((String) session.getAttribute("DEPT_ID"));
		loginHistoryVO.setDeptName((String) session.getAttribute("DEPT_NAME"));
		loginHistoryVO.setUseDate(DateUtil.getCurrentDate());
		loginHistoryVO.setUsedType(dhu006);
		loginService.insertLoginHistory(loginHistoryVO);
    }

    session.invalidate();
%>
<html>
<head>
<script type="text/javascript">
	window.self.close();
</script>
</head>
<body>
<marquee>...........</marquee>
</body>
</html>
