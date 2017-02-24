<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ReplyInsert
 *  Description : 댓글입력저장
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.03 
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
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 org.anyframe.pagination.Page,
				 java.util.Locale,
				 java.util.List"
%>
<%
	response.setHeader("pragma","no-cache");	
	String useTrayYn = CommonUtil.nullTrim((String) session.getAttribute("USE_TRAY"));	// 트레이 사용여부
	String idirUrl = AppConfig.getProperty("idir_url", "", "path");  //idir 웹
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	AppBoardVO boardVO = (AppBoardVO) request.getAttribute("boardVO");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">


<title>::저장 완료페이지::</title>
</head>
<body>
<acube:outerFrame>
<acube:titleBar><spring:message code="board.title.reply"/></acube:titleBar>
</acube:outerFrame>
	<table  border="0" cellpadding="0" cellspacing="0" width="100%" height="60%">
			<tr height="60">
			
			<td class="tb_tit">
			<textarea id="recontents" name="recontents"  rows="3" cols="70" style="width: 98.9%;ime-mode:active;"></textarea>
			</td>
		<td width="1%">
						
				</td>
				<td>
<table cellpadding="0" cellspacing="0" border="0" width="2%">
	<tr height="" >
				<td style="background:#ffffff" ></td>
				<td width="10"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
				<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);">등록</a></td>
				<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
				
				</table>
			</tr>
			
	</table>
	
	<p></p>
	<p></p>
	
		<table  border="0" cellpadding="0" cellspacing="0" width="40%" height="60%">
			<tr height="60">
			
			<td class="" width=80%>
			<b>곽경종</b>   &nbsp; 10:45<br/>
			<p/>
			댓글입니다.
			
			</td>
			
			
		<td width="1%">
						
				</td>
				<td>
<table cellpadding="0" cellspacing="0" border="0" width="2%">
	<tr height="" >
				<td style="background:#ffffff" ></td>
				<td width="10"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
				<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);">등록</a></td>
				<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
				
				</table>
			</tr>
			</td>
	</table>

</body>
</html>