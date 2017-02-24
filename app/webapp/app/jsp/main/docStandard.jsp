<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
	String sessionCompId = (String) session.getAttribute("COMP_ID");	// 사용자 회사ID
	String type = CommonUtil.nullTrim((String) request.getParameter("type")); // 페이지타입
	String readRange = CommonUtil.nullTrim((String) request.getParameter("readRange")); // 열람범위
	String D1 = request.getParameter("D1") != null ? request.getParameter("D1") : "";
	session.setAttribute("D1", D1);
	
	String toggleMenu 	= CommonUtil.nullTrim(request.getParameter("toggleMenu"));
    String boxCode		= CommonUtil.nullTrim(request.getParameter("boxCode"));
    String boxUrl		= CommonUtil.nullTrim(request.getParameter("boxUrl"));
    String listBoxUrl	= "";
    String mainUrl		= "";
    
    if(!"".equals(toggleMenu) && !"".equals(boxCode) && !"".equals(boxUrl) ) {
		listBoxUrl	= webUri+"/app/list/box/docListBox.do?toggleMenu="+toggleMenu+"&boxUrl="+boxUrl+"&boxCode="+boxCode;
		mainUrl		= "";	
    }else{
		listBoxUrl 	= webUri+"/app/list/box/docListBox.do";
		mainUrl		= webUri+"/app/board/BullShare.do?boardId=BOARD_COMMON_SHARE";
		/* mainUrl		= webUri+"/app/list/main/mainList.do"; */
    }
    
    String pubPostUrl = webUri+"/app/list/etc/leftDisplayNotice.do";
    if(!"".equals(readRange)){
		pubPostUrl += "?readRange="+readRange;
    }
%>
	<html>
		<%--<frameset cols=190,* frameborder=0> --%>
		<frameset cols=270,* frameborder=0>
<% if("GROUP".equals(sessionCompId)){ %>
	<frame name="left" src="<%=webUri%>/app/env/admin/MenuOptionGroupAdmin.do" scrolling="auto" noresize/>
	<frame name="body" src="" noresize/>
<%}else{ %>
<%
	if("".equals(type)) {
%>
			
			<frame name="left" src="<%=listBoxUrl%>" scrolling="auto" noresize/>			
			<frame name="body" src="<%=mainUrl%>" noresize/>
<%
	}
	else if("admin".equals(type)) {
%>
			<frame name="left" src="<%=webUri%>/app/env/admin/MenuOptionAdmin.do" scrolling="auto" noresize/>
			<frame name="body" src="" noresize/>
<%
	}
	else if("pubPost".equals(type)) {
%>
			<frame name="left" src="<%=pubPostUrl %>" scrolling="auto" noresize/>
			<frame name="body" src="" noresize/>
<%
	}
	else if("bind".equals(type)) {
%>
			<frame name="left" src="<%=webUri%>/app/bind/menu.do" scrolling="auto" noresize/>
			<frame name="body" src="" noresize/>

<%
	}
	else if("notice".equals(type)) {
%>
			<frame name="left" src="<%=webUri%>/app/notice/menu.do" scrolling="auto" noresize/>
			<frame name="body" src="<%=webUri%>/app/notice/list.do" noresize/>
			
<%
	}
	else if("receiveMemo".equals(type)) {
%>
			<frame name="left" src="<%=webUri%>/app/memo/menu.do" scrolling="auto" noresize/>
			<frame name="body" src="<%=webUri%>/app/memo/list.do?pageType=receive" noresize/>
<%
	}
	else if("sendMemo".equals(type)) {
%>
			<frame name="left" src="<%=webUri%>/app/memo/menu.do" scrolling="auto" noresize/>
			<frame name="body" src="<%=webUri%>/app/memo/list.do?pageType=send" noresize/>			
			
<%
	}
%>
<% } %>
		</frameset>
	</html>

