<%@ page contentType="text/html; charset=UTF-8" %>
<%
    /** 
     *  Class Name  : leftDisplayNotice.jsp
     *  Description : 공람게시판 왼쪽 메뉴 
     *  Modification Information 
     * 
     *   수 정 일 :  
     *   수 정 자 : 
     *   수정내용 : 
     * 
     *  @author  김경훈
     *  @since 2011. 06. 09 
     *  @version 1.0 
     *  @see
     */
%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.anyframe.util.StringUtil" %>
<%@page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>

<%
    String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS");
    String imagePath = webUri + "/app/ref/image";
    String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디    
    String readRange = StringUtil.null2str(request.getParameter("readRange"), drs002);
    
    String opt314 = (String) request.getAttribute("opt314");

	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = (String) userProfileVO.getInstitution();
	String headOfficeId = (String) userProfileVO.getHeadOffice();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="list.etc.left.title"/></title>
<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<script language="javascript">
<!--
$(document).ready(function(){ 
	init(); 

});

function init(){
	linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=<%=readRange%>','<%=readRange%>');	
	
	

}

function linkUrl(url,id){
	var selMenu = $('#'+id);
	var menus 	= $('a[group=leftMenu]');
	
	menus.css("color","#888888");
	menus.css("font-weight","");
	
	selMenu.css("color","#e87e0e");
	//selMenu.css("font-weight","bold");

	parent.frames[parent.frames.length - 1].location.href = url;
}

function goDisplayNoticeMain(){
	parent.location.href = "<%=webUri%>/app/index.do?type=pubPost";
}
</script>
</head>
<BODY  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width=100% height=100% border="0" cellpadding="0" cellspacing="0" style="border-right:solid 1px #dfdfdf;">
  
  
  <tr>
    <td width="230" align="center" valign="top"><table width="180" height="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="1" colspan="3" bgcolor="E3E3E3"></td></tr>
      <tr onClick="javascript:goDisplayNoticeMain();" style="cursor:pointer;">
	      <td height="50" valign="top"><img src="<%=imagePath%>/left_menu/title_approval_01.jpg"></td>
      </tr>
      
      <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
           
            
	        
			<tr>
	              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td class="menu_open"><spring:message code="list.etc.left.title"/></td>
	                  <td width="20" style="background-color:e0e0e0"><img src="<%=imagePath%>/left_menu/left_icon.gif"></td>
	                </tr>
	              </table></td>
	         </tr>
			
			
			 <tr>
	              <td class="menu_bg_01">
	              <table width="100%" border="0" cellspacing="0" cellpadding="0">	                
	                <tr>
	                  <td width="22" align="right"><img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
	                  <td class="menu_body" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS002','DRS002');"><a id="DRS002" href="#" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS002','DRS002');" group="leftMenu"><spring:message code="list.etc.left.subDept"/></a></td>
	                </tr>
	                <%
	                    if ("1".equals(opt314)) {
	                %>
					<% if(!"".equals(headOfficeId)) {   // jth8172 2012 신결재 TF%>
	                <tr>
	                  <td width="22" align="right"><img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
	                  <td class="menu_body" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS003','DRS003');"><a id="DRS003" href='#' onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS003','DRS003');" group="leftMenu"><spring:message code="list.etc.left.subHQ"/></a></td>
	                </tr>
					<% } %>
					<% if(!"".equals(institutionId)) {   // jth8172 2012 신결재 TF%>
	                <tr>
	                  <td width="22" align="right"><img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
	                  <td class="menu_body" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS004','DRS004');"><a id="DRS004" href='#' onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS004','DRS004');" group="leftMenu"><spring:message code="list.etc.left.subInstitution"/></a></td>
	                </tr>
					<% } %>
	                <tr>
	                  <td width="22" align="right"><img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
	                  <td class="menu_body" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS005','DRS005');"><a id="DRS005" href='#' onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS005','DRS005');" group="leftMenu"><spring:message code="list.etc.left.subCompany"/></a></td>
	                </tr>
	                <%
	                    }
	                %>               
	              </table>
	              </td>
	          </tr>
	          <tr>
	              <td class="menu_line_01"></td>
	          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>  
</table> 
</body>
</html>