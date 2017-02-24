<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
	String attrDocId =  CommonUtil.nullTrim((String) request.getAttribute("docId")); // 문서 아이디가 있다면 수신함으로 이동한 뒤 해당 문서팝업을 열어줘야 한다.


	String sessionCompId = (String) session.getAttribute("COMP_ID");	// 사용자 회사ID
	String type = CommonUtil.nullTrim((String) request.getParameter("type")); // 페이지타입
	String readRange = CommonUtil.nullTrim((String) request.getParameter("readRange")); // 열람범위
	String D1 = request.getParameter("D1") != null ? request.getParameter("D1") : "";
	session.setAttribute("D1", D1);
	
	String toggleMenu 	= CommonUtil.nullTrim(request.getParameter("toggleMenu"));
    String boxCode		= CommonUtil.nullTrim(request.getParameter("boxCode"));
    String boxUrl		= CommonUtil.nullTrim(request.getParameter("boxUrl"));
    String docId		= CommonUtil.nullTrim(request.getParameter("docId"));
    String userUid		= CommonUtil.nullTrim(request.getParameter("userUid"));
    String listBoxUrl	= "";
    String mainUrl		= "";
    
    if(!attrDocId.equals("")){// 문서 아이디가 있다면 수신함으로 이동한 뒤 해당 문서팝업을 열어줘야 한다.
    	toggleMenu 	= "Approval";
        boxCode		= "linkMenu_OPT103";
        boxUrl		= "/ep/app/list/approval/ListApprovalWaitBox.do";
        
        listBoxUrl	= webUri+"/app/list/box/listBox.do?toggleMenu="+toggleMenu+"&boxUrl="+boxUrl+"&boxCode="+boxCode+"&docId="+attrDocId;
        mainUrl		= "";			
    }else{
    	 if(!"".equals(toggleMenu) && !"".equals(boxCode) && !"".equals(boxUrl) ) {
    	    	if(!"".equals(docId)){
    	    		if(!"".equals(userUid)){
    	    			listBoxUrl	= webUri+"/app/list/box/listBox.do?toggleMenu="+toggleMenu+"&boxUrl="+boxUrl+"&boxCode="+boxCode+"&docId="+docId+"&userUid="+userUid;
    	    		}
    	    	}else{
    	    		if(!"".equals(userUid)){
    	    			listBoxUrl	= webUri+"/app/list/box/listBox.do?toggleMenu="+toggleMenu+"&boxUrl="+boxUrl+"&boxCode="+boxCode+"&userUid="+userUid;
    	    		}else{
    	    			listBoxUrl	= webUri+"/app/list/box/listBox.do?toggleMenu="+toggleMenu+"&boxUrl="+boxUrl+"&boxCode="+boxCode;
    	    		}
    	    	}
    			mainUrl		= "";	
    	    }else{
    			listBoxUrl 	= webUri+"/app/list/box/listBox.do";
    			mainUrl		= webUri+"/app/list/main/mainList.do";
    	    }
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

