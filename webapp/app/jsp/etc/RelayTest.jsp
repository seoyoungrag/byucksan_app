<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.relay.vo.PackInfoVO"%>
<%@ page import="com.sds.acube.app.relay.vo.LineInfoVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%
	PackInfoVO packInfoVO = (PackInfoVO) request.getAttribute("packInfoVO");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
���������<br/>
==============================================================================================================================<br/>
<%
	out.println("�߽ű�� : " + packInfoVO.getOrgan() + "<br/>");
	out.println("����ó : " + packInfoVO.getRec() + "<br/>");
	out.println("���� : " + packInfoVO.getVia() + "<br/>");
	out.println("�������� : " + packInfoVO.getTitle() + "<br/>");
	out.println("�߽Ÿ��� : " + packInfoVO.getSenderTitle() + "<br/>");

	for(LineInfoVO lineInfoVO : packInfoVO.getApproval()) {
		out.println("������ : " + lineInfoVO.getLineOrder() + ", ");
		out.println("��å : " + lineInfoVO.getSignposition() + ", ");
		out.println("�������� : " + lineInfoVO.getType() + ", ");
		out.println("�����ڸ� : " + lineInfoVO.getName());
		out.println("�����̹��� : " + lineInfoVO.getSignimage() + ", ");
		out.println("��¥ : " + lineInfoVO.getPdate() + ", ");
		out.println("�ð� : " + lineInfoVO.getPtime() + "<br/>");
	}
	
	for(LineInfoVO lineInfoVO : packInfoVO.getAssist()) {
		out.println("������� : " + lineInfoVO.getLineOrder() + ", ");
		out.println("��å : " + lineInfoVO.getSignposition() + ", ");
		out.println("�������� : " + lineInfoVO.getType() + ", ");
		out.println("�����ڸ� : " + lineInfoVO.getName());
		out.println("�����̹��� : " + lineInfoVO.getSignimage() + ", ");
		out.println("��¥ : " + lineInfoVO.getPdate() + ", ");
		out.println("�ð� : " + lineInfoVO.getPtime() + "<br/>");
	}
	
	out.println("���������Ϲ�ȣ : " + packInfoVO.getRegNumber() + "<br/>");
	out.println("�����������ڵ� : " + packInfoVO.getRegNumberCode() + "<br/>");
	out.println("�������� : " + packInfoVO.getSendDate() + "<br/>");
	out.println("�����ȣ : " + packInfoVO.getZipcode() + "<br/>");
	out.println("�ּ� : " + packInfoVO.getAddress() + "<br/>");
	out.println("Ȩ������ : " + packInfoVO.getHomeUrl() + "<br/>");
	out.println("��ȭ��ȣ : " + packInfoVO.getTelephone() + "<br/>");
	out.println("�ѽ� : " + packInfoVO.getFax() + "<br/>");
	out.println("�����ڵ� : " + packInfoVO.getPublication() + "<br/>");

	out.println("÷������<br/>");
	for(FileVO fileVO : packInfoVO.getAttach()) {
		out.println(fileVO.getFileName() + "<br/>");
	}
%>
==============================================================================================================================<br/>
</body>
</html>