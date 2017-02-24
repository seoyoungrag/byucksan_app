<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%-- Ç¥ÁöÀÎ¼â . 2014.10.01 - ronnie --%>
<%!
	static String makeCover(String[] arrHdr) {
    	StringBuffer sb = new StringBuffer("");

	    if (arrHdr == null) return "";
    	sb.append("<CENTER>\n");
    	sb.append("<TABLE class='hdr4'>\n");
	    sb.append(" <TR>\n");
	    sb.append("  <TD class='hdrtitlenounder1' colspan='2' nowrap>");
    	sb.append(arrHdr[0] + "</TD></TR>");
    	sb.append("  <TR><TD class='hdrtitlenounder2' colspan='2'>" + arrHdr[1] + "</TD></TR>\n");
    	sb.append("   <TR><TD colspan='2'>&nbsp</TD></TR>");
    	sb.append("  <TR><TD class='hdrtitlenounder3' colspan='2'>" + arrHdr[2]+"</TD></TR>\n");
    	sb.append("  <TR><TD class='hdrtitlenounder4' colspan='2'>" + arrHdr[3] + "</TD></TR>\n");
    	sb.append("</TABLE>\n");
    	sb.append("</CENTER>\n");
    	sb.append(" <TR class='bdfix'><TD colspan='8'></TD></TR>\n");
    	
    	return sb.toString();
 	}

%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
    String closeBtn = m.getMessage("list.list.button.close", null, langType);
    
    String compId = (String) session.getAttribute("COMP_ID");
    String compName = (String) session.getAttribute("COMP_NAME");
    String deptId = (request.getParameter("deptId") == null) ? "" : request.getParameter("deptId");
    String deptName = (String)session.getAttribute("DEPT_NAME");
    String createYear = (request.getParameter("createYear") == null) ? "" : request.getParameter("createYear");
    String expireYear = (request.getParameter("expireYear") == null) ? "" : request.getParameter("expireYear");
    String bindName = (request.getParameter("bindName") == null) ? "" : request.getParameter("bindName");
    String bindVol = "000001";
    String unitId = (request.getParameter("unitId") == null) ? "" : request.getParameter("unitId");
    
     String[] arrInput = new String[4];
    
    String title = deptId+"-"+unitId+"-"+createYear+"-"+bindVol;
    
    arrInput[0] = title;
    arrInput[1] = bindName;
    arrInput[2] = createYear+"~"+expireYear;
    arrInput[3] = compName;
    
%>
<html>
	<head>
		<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
		<META HTTP-EQUIV=Expires CONTENT="0">
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">
		
		<TITLE><spring:message code='bind.title.list' /></TITLE>
		
		<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
		
		<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
		
		<script LANGUAGE="JavaScript">
		
			var initBody;
			
			function beforePrint(){
				initBody = document.body.innerHTML;
				document.body.innerHTML = printDiv.innerHTML; 
			}
			
			function afterPrint(){
				document.body.innerHTML = initBody;
			}
			
			function init(){
				window.onbeforeprint = beforePrint;
				window.onafterprint = afterPrint;
				window.print();
			}
		
			function closeWin(){
				if(opener.printDoc != null || opener.printDoc.closed == false){
					opener.printDoc.close();
				}
			}
		
			window.onload = init;
		
		</script>
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
		<acube:outerFrame>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="ltb_left" ><b><spring:message code='list.list.msg.noticePrint1'/></b></td>
			</tr>
			<tr>
				<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint2'/></td>
			</tr>
			<tr>
				<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint3'/></td>
			</tr>
			<tr>
				<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint4'/></td>
			</tr>
			<tr>
				<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint5'/></td>
			</tr>
			<tr>
				<td class="ltb_right"><acube:button onclick="closeWin();return(false);" value="<%=closeBtn%>" type="4" class="gr" /></td>
			</tr>
		</table>
		</acube:outerFrame>
		<div id="printDiv">
			<form name="listForm" method='post' action='<%=webUri%>/app/bind/list.do'>	
			<input type="hidden" name="bindId" />	
			<%
			out.println(makeCover(arrInput));
			%>
			</form>
		</div>
	</body>
</html>
