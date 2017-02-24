<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : excelDown.jsp 
 *  Description : 엑셀다운  
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 09. 29 
 *  @version 1.0 
 *  @see
 */ 
%>

<%

// 파일관련
String filePath 		= (String) request.getAttribute("filePath");
String fileName 		= (String) request.getAttribute("fileName");
String downloadFileName = (String) request.getAttribute("downloadFileName");

//out.println("filePath : " + filePath+"<br>");
//out.println("fileName : " + fileName+"<br>");
//out.println("downloadFileName : " + downloadFileName);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script language="javascript">
<!--
<!--
$(document).ready(function(){ 
	// 파일 ActiveX 초기화
	initializeFileManager();

	excelFileDown();
});

function excelFileDown(){
	
	<% if(!"".equals(filePath) && !"".equals(fileName) && !"".equals(downloadFileName)){ %>
	
		var file = new Object();
		file.filename = "<%=fileName%>";
		file.displayname = "<%=downloadFileName%>";
		FileManager.setExtension("xls");
		FileManager.savefile(file);
	
	<% }else{ %>
		alert("<spring:message code='list.fileDownLoadWindowFail'/>");
		return false;
	<% } %>
}

//-->
</script>

</head>

<body>
	
</body>
</html>
