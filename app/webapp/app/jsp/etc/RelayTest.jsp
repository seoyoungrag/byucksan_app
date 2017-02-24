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
출력페이지<br/>
==============================================================================================================================<br/>
<%
	out.println("발신기관 : " + packInfoVO.getOrgan() + "<br/>");
	out.println("수신처 : " + packInfoVO.getRec() + "<br/>");
	out.println("경유 : " + packInfoVO.getVia() + "<br/>");
	out.println("문서제목 : " + packInfoVO.getTitle() + "<br/>");
	out.println("발신명의 : " + packInfoVO.getSenderTitle() + "<br/>");

	for(LineInfoVO lineInfoVO : packInfoVO.getApproval()) {
		out.println("결재경로 : " + lineInfoVO.getLineOrder() + ", ");
		out.println("직책 : " + lineInfoVO.getSignposition() + ", ");
		out.println("결재유형 : " + lineInfoVO.getType() + ", ");
		out.println("결재자명 : " + lineInfoVO.getName());
		out.println("사인이미지 : " + lineInfoVO.getSignimage() + ", ");
		out.println("날짜 : " + lineInfoVO.getPdate() + ", ");
		out.println("시간 : " + lineInfoVO.getPtime() + "<br/>");
	}
	
	for(LineInfoVO lineInfoVO : packInfoVO.getAssist()) {
		out.println("협조경로 : " + lineInfoVO.getLineOrder() + ", ");
		out.println("직책 : " + lineInfoVO.getSignposition() + ", ");
		out.println("결재유형 : " + lineInfoVO.getType() + ", ");
		out.println("결재자명 : " + lineInfoVO.getName());
		out.println("사인이미지 : " + lineInfoVO.getSignimage() + ", ");
		out.println("날짜 : " + lineInfoVO.getPdate() + ", ");
		out.println("시간 : " + lineInfoVO.getPtime() + "<br/>");
	}
	
	out.println("문서생산등록번호 : " + packInfoVO.getRegNumber() + "<br/>");
	out.println("문서생산등록코드 : " + packInfoVO.getRegNumberCode() + "<br/>");
	out.println("시행일자 : " + packInfoVO.getSendDate() + "<br/>");
	out.println("우편번호 : " + packInfoVO.getZipcode() + "<br/>");
	out.println("주소 : " + packInfoVO.getAddress() + "<br/>");
	out.println("홈페이지 : " + packInfoVO.getHomeUrl() + "<br/>");
	out.println("전화번호 : " + packInfoVO.getTelephone() + "<br/>");
	out.println("팩스 : " + packInfoVO.getFax() + "<br/>");
	out.println("공개코드 : " + packInfoVO.getPublication() + "<br/>");

	out.println("첨부파일<br/>");
	for(FileVO fileVO : packInfoVO.getAttach()) {
		out.println(fileVO.getFileName() + "<br/>");
	}
%>
==============================================================================================================================<br/>
</body>
</html>