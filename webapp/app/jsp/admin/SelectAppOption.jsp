<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.sds.acube.app.common.util.AppEnvOption"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.sds.acube.app.env.vo.OptionVO"%>
<%@ page import="com.sds.acube.app.common.util.Transform"%>
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
<%!
	String getOptionList(Map<String, OptionVO> map) {
		StringBuffer sb = new StringBuffer();
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    String key = (String) entry.getKey();
		    OptionVO optionVO = (OptionVO) map.get(key);
		    try {
			sb.append(toString(optionVO));
		    } catch (Exception e) {
		    }
		}
		return sb.toString();
    }


    String toString(OptionVO optionVO) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' class='td_table' border='0' cellspacing='1' cellpadding='0'>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("COMP_ID").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getCompId())).append("</td>");
		sb.append("	</tr>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("OPTION_ID").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getOptionId())).append("</td>");
		sb.append("	</tr>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("USE_YN").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getUseYn())).append("</td>");
		sb.append("	</tr>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("OPTION_VALUE").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getOptionValue())).append("</td>");
		sb.append("	</tr>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("OPTION_TYPE").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getOptionType())).append("</td>");
		sb.append("	</tr>");
		sb.append("	<tr bgcolor='#ffffff'>");
		sb.append("		<td width='25%' class='tb_tit' style='height:28px;'>").append("DESCRIPTION").append("</td>");
		sb.append("		<td width='75%' class='tb_left' >").append(EscapeUtil.escapeHtmlDisp(optionVO.getDescription())).append("</td>");
		sb.append("	</tr>");
		sb.append("</table>");
	
		return sb.toString();
    }
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
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.server.env.config'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%
    	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
		out.println(getOptionList(appEnvOption.getOptions("001")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		out.println(getOptionList(appEnvOption.getOptions("002")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		out.println(getOptionList(appEnvOption.getOptions("003")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		out.println(getOptionList(appEnvOption.getOptions("004")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		out.println(getOptionList(appEnvOption.getOptions("005")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		out.println(getOptionList(appEnvOption.getOptions("006")));
%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<%		
		/*	
		 out.println(toString(appEnvOption.getOption("001", "OPT301")));
		 out.println(toString(appEnvOption.getOption("002", "OPT301")));
		 out.println(toString(appEnvOption.getOption("003", "OPT301")));
		 out.println(toString(appEnvOption.getOption("004", "OPT301")));
		 out.println(toString(appEnvOption.getOption("005", "OPT301")));
		 out.println(toString(appEnvOption.getOption("006", "OPT301")));
		 out.println("<br/><br/>");
		 out.println(toString(appEnvOption.getOption("001", "OPT332")));
		 out.println(toString(appEnvOption.getOption("002", "OPT332")));
		 out.println(toString(appEnvOption.getOption("003", "OPT332")));
		 out.println(toString(appEnvOption.getOption("004", "OPT332")));
		 out.println(toString(appEnvOption.getOption("005", "OPT332")));
		 out.println(toString(appEnvOption.getOption("006", "OPT332")));
		 */
%>	
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>