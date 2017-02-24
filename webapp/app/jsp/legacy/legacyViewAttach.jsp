<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
String docId = (String)request.getAttribute("docId");
String fileId = (String)request.getAttribute("fileId");
String fileName = (String)request.getAttribute("fileName");
String fileDisplayName = (String)request.getAttribute("displayName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	alert("<%=fileDisplayName%>");
	initializeFileManager();
	AttachManager.viewAttach("attach_0");
});

function getAttachFile(){
	var attach = new Object();
	attach.fileid = "<%=fileId%>";
	attach.filename = "<%=fileName%>";
	attach.displayname = "<%=fileDisplayName%>";
	attach.type = "save";
	attach.gubun = "";
	attach.docid = "<%=docId%>";
	FileManager.download(attach);
}
</script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<!-- <input type="button" name="attach" value="첨부" onclick="javascript:getAttachFile();" /> -->
<input type="hidden" id="attach_0" fileid="<%=fileId%>" fileName="<%=fileName%>" displayName="<%=fileDisplayName%>" docId="<%=docId%>" localpath="" />
</Body>
</Html>