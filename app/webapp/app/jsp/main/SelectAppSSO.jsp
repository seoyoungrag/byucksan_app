<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

<%@ include file="/app/jsp/common/header.jsp"%>
<%
// 	String strUrl 	= AppConfig.getProperty("web_url", "", "path");
	String strUrl 	= "http://172.18.1.11:8080";
	String docId	= CommonUtil.nullTrim(request.getParameter("docId"));
	String lobCode	= CommonUtil.nullTrim(request.getParameter("lobCode"));	
	String compId = CommonUtil.nullTrim(request.getParameter("compId"));
	String appUrl = "";
	String url = CommonUtil.nullTrim(request.getParameter("url"));	//20140309 kj.yang
	
	strUrl = strUrl + url;	
	
	appUrl = strUrl + "?docId=" + docId + "&lobCode=" + lobCode;
	if("LOB008".equals(lobCode)) {
		compId = CommonUtil.nullTrim(request.getParameter("originCompId"));		// �ܺαⰣ �ڵ�	20140317_csh
		String receiverOrder = CommonUtil.nullTrim(request.getParameter("receiverOrder"));		// 20140319 ����ó ���� ���� kj.yang
		appUrl = appUrl + "&compId=" + compId + "&receiverOrder=" + receiverOrder;
	}

	if("LOB031".equals(lobCode)) {	//�����Խ�
		appUrl = appUrl + "&publishId=" + CommonUtil.nullTrim(request.getParameter("publishId"));
	}

%>
<html>
<head>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() { 
		document.location.href="<%=appUrl%>";
	});
</script>
</html>
