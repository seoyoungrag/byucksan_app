<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.jconfig.Configuration" %>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%
	String webUri = AppConfig.getProperty("web_uri", "/ep", "path");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><spring:message code="common.title.groupware"/></title>
<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
</head>
<script type="text/javascript">
</script>
<body bgcolor="#ffffff" text="#000000">

<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
				<td width="50%">
				
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="80" valign="top" background="<%=webUri%>/app/ref/image/common/message_04.gif">
								<img src="<%=webUri%>/app/ref/image/common/message_01.gif" width="80" height="80"></td>
							<td valign="top">
							
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="12" background="<%=webUri%>/app/ref/image/common/message_02.gif"></td>
										<td background="<%=webUri%>/app/ref/image/common/message_02.gif"></td>
									</tr>
									<tr>
										<td height="30"></td>
										<td width="40" rowspan="4"></td>
									</tr>
									<tr>
										<td><img src="<%=webUri%>/app/ref/image/common/error.gif" width="46" height="17"></td>
									</tr>
									<tr>
										<td height="15"></td>
									</tr>
									<tr>
										<td style="font:9pt;">
											<strong><spring:message code="common.msg.sorry.touse.system"/></strong>
										</td>
									</tr>
								</table> 
								         
							</td>
							<td width="12" align="right" valign="top" background="<%=webUri%>/app/ref/image/common/message_05.gif"><img src="<%=webUri%>/app/ref/image/common/message_03.gif" width="12" height="12"></td>
						</tr>
						<tr>
							<td background="<%=webUri%>/app/ref/image/common/message_04.gif">&nbsp;</td>
							<td height="35">&nbsp;</td>
							<td background="<%=webUri%>/app/ref/image/common/message_05.gif">&nbsp;</td>
						</tr>
						<tr>
							<td height="13" valign="top"><img src="<%=webUri%>/app/ref/image/common/message_08.gif" width="80" height="13"></td>
							<td height="13" background="<%=webUri%>/app/ref/image/common/message_07.gif"></td>
							<td height="13" valign="top"><img src="<%=webUri%>/app/ref/image/common/message_06.gif" width="12" height="13"></td>
						</tr>
					</table> 
			   
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>

	</td></tr>
</table>

</body>
</html>