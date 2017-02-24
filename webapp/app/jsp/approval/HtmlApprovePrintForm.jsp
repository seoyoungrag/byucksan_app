<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="java.io.*" %>

<!DCOTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type"/>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript">
<!--  

	// 처음 시작할때 호출됨 (htmlmanager.jsp 파일에 있는 메소드 호출) 
	function pageLoad() {
		document.getElementById("printFooter").style.display = "none";

		// 결재경로
		if (opener.frm.appLineHTML.value != "") {
			document.getElementById("appLineArea").innerHTML = opener.frm.appLineHTML.value;
		}

		// HTML 본문
		document.getElementById("printArea").innerHTML = opener.frm.printData.value;
		
		window.print();
		document.getElementById("printFooter").style.display = "block";
	}

	// 인쇄
	function printHTML() {
		document.getElementById("printFooter").style.display = "none";
		window.print();
		document.getElementById("printFooter").style.display = "block";
	}

	// 닫기
	function closeWindow() {
		self.close();
	}

-->
</script> 

</head>
<body onLoad="pageLoad();">

<div id="printFooter">
	<table border='0' cellspacing='0' cellpadding='0' align='right'>
		<tr>
			<td>
				<table border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td><img src='/ep/app/ref/image/btn_top1.gif' width='6' height='20'/></td>
						<td background='/ep/app/ref/image/btn_topbg.gif' class='text_left' nowrap><a href='#' onclick='javascript:printHTML();return(false);'><spring:message code='approval.button.print'/></a></td>
						<td><img src='/ep/app/ref/image/btn_top2.gif' width='6' height='20'/></td>
					</tr>
				</table>
			</td>
			<td width="5">&nbsp;</td>
			<td>
				<table border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td><img src='/ep/app/ref/image/btn_top1.gif' width='6' height='20'/></td>
						<td background='/ep/app/ref/image/btn_topbg.gif' class='text_left' nowrap><a href='#' onclick='javascript:closeWindow();'><spring:message code='approval.button.close'/></a></td>
						<td><img src='/ep/app/ref/image/btn_top2.gif' width='6' height='20'/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<br/>
<br/>
<br/>

<div id="appLineArea"></div>

<div id="printArea"></div>

</body>
</html>