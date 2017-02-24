<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : BullResult.jsp 
 *  Description : 게시판 처리페이지
 *  Modification Information 
 * 
 *   수 정 일 : 2012.06.14 
 *   수 정 자 : 곽경종
 *   수정내용 :  
 * 
 *  @author  정태환
 *  @since 2012. 3. 23 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.board.vo.AppBoardVO" %>

<%
	AppBoardVO boardVO = (AppBoardVO) request.getAttribute("boardVO");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta http-equiv="Refresh" content="0.1;URL=<%=webUri%>/app/board/BullList.do?boardId=<%=boardVO.getBoardId()%>">
<title><spring:message code="board.list.result"/></title>
</head>
<body>
</body>
</html>