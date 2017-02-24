<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
</head>   
<body margin="5 5 5 5" style="background-color:transparent;">
<center>
<table width="300" height="80" bgcolor="#aaaaaa">
	<tr>
		<td>
			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tb_left" width="20%">&nbsp;&nbsp;&nbsp;<image src="<%=webUri%>/app/ref/image/bigWaiting.gif" /></td>
					<td class="tb_left" width="80%"><spring:message code='common.msg.wait.moment'/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
</body>
</html>