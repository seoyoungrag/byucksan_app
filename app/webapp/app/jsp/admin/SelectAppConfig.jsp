<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Properties"%>
<%@ page import="org.jconfig.Category"%>
<%
/** 
 *  Class Name  : SelectAppConfig.jsp 
 *  Description : 결재 환경설정 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	// 버튼명
	String reloadBtn = messageSource.getMessage("approval.button.reload", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.server.env.config'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
}

function reloadAppConfig() {
	document.location.href = "<%=webUri%>/app/approval/admin/reloadAppConfig.do";
} 
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<acube:titleBar><spring:message code='approval.title.server.env.config'/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="reloadAppConfig();return(false);" value="<%=reloadBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
				<!-------정보등록 Table S ---------> 
<%
	    String[] categoryNames = AppConfig.getCategoryNames("common", "");
	    int categoryCount = categoryNames.length;
	    for (int loop = 0; loop < categoryCount; loop++) {
			Category category = AppConfig.getCategory("common", "", categoryNames[loop]);
			Properties properties = category.getProperties();
			int propertyCount = properties.size();
			if ( propertyCount > 0 ) {
%>
		<tr>
			<td>
				<acube:titleBar type="sub"><%=EscapeUtil.escapeHtmlDisp(categoryNames[loop])%>&nbsp;(<%=propertyCount%>)</acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
<%
	 	    Iterator<Object> it = properties.keySet().iterator();
	 	    while (it.hasNext()) {
		 		String key = (String) it.next();
		 		String value = properties.getProperty(key);
%>
			 		<tr bgcolor="#ffffff">
						<td width="25%" class="tb_tit" style="height:28px;"><%=EscapeUtil.escapeHtmlDisp(key)%></td>
						<td width="75%" class="tb_left" ><%=EscapeUtil.escapeHtmlDisp(value)%></td>
					</tr>
<%					
	 	    }
%>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<%			} // end of.. if ( propertyCount > 0 ) {
	    } // end of for.
%>
				<!-------정보등록 Table E --------->
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="reloadAppConfig();return(false);" value="<%=reloadBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>