<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>


<%
String imagePath = webUri + "/app/ref/image";
String edmsSvr = AppConfig.getProperty("server_url", "", "docmgr");

String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String loginId = (String) session.getAttribute("LOGIN_ID");
String userName = (String) session.getAttribute("USER_NAME");
String userId = (String) session.getAttribute("USER_ID");


String orgType = (String)session.getAttribute("ORG_TYPE");
String userList = (String)session.getAttribute("USER_ID");

String portal_url = AppConfig.getProperty("iam_url", "http://localhost:8080", "organization");

%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
<!-- style type="text/css">
	TD {FONT-SIZE:13px; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style-->
<style type="text/css">
<!--
.topmenu           { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#888888; PADDING-TOP:1px; letter-spacing:-1px;}
.topmenu A:link    { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#888888; text-decoration:none; }
.topmenu A:visited { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#888888; text-decoration:none; }
.topmenu A:active  { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#888888; text-decoration:none; }
.topmenu A:hover   { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#888888 ;text-decoration:none; }

.menu              { FONT-FAMILY:Gulim,Dotum,Arial; font-size:12px; color:#ffffff; PADDING-TOP:4px; font-weight:bold; letter-spacing:-1px;}
.menu A:link       { FONT-FAMILY:Gulim,Dotum,Arial; font-size:12px; color:#ffffff; text-decoration:none; }
.menu A:visited    { FONT-FAMILY:Gulim,Dotum,Arial; font-size:12px; color:#ffffff; text-decoration:none; }
.menu A:active     { FONT-FAMILY:Gulim,Dotum,Arial; font-size:12px; color:#FFCE7A; text-decoration:none; }
.menu A:hover      { FONT-FAMILY:Gulim,Dotum,Arial; font-size:12px; color:#FFCE7A; text-decoration:none; }

.submenu           { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#666666; PADDING-TOP:1px; letter-spacing:-1px;}
.submenu A:link    { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#666666; text-decoration:none; }
.submenu A:visited { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#666666; text-decoration:none; }
.submenu A:active  { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#666666; text-decoration:none; }
.submenu A:hover   { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#666666; text-decoration:none; }

.input    { height:18px; color:#001B4B; background-color:#ffffff; border:1px #2F91D2 solid; font-size: 9pt; padding-left:3px; padding-top:2px;  }
.select   { FONT-FAMILY:Gulim,Dotum,Arial; font-size:8pt; color:#001B4B; background-color:#ffffff;  }

.logout  { FONT-FAMILY:Dotum,Gulim,Arial; font-size:11px; color:#777777; font-weight:bold; letter-spacing:-1px; PADDING-TOP:3px;}
.top_search   { FONT-SIZE:8pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#555555; background-color:#EDEDED; border:1px solid #CAC9C9;}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style1 {
	font-size: 10px;
	font-weight: bold;
	color: #006699;
}
--> 
</style> 
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
<!--
$(document).ready(function() { initialize(); });

function initialize() {
}
var isUTF = true;

function goMenu(type) {
	var url = "<%=webUri%>/app/index.do";
	if(type == "admin") {
		url = "<%=webUri%>/app/index.do?type=admin";
	}else if(type == "pubPost") {
		url = "<%=webUri%>/app/index.do?type=pubPost";
	}else if(type == "bind") {
		url = "<%=webUri%>/app/index.do?type=bind";
	}else if(type == "notice") {
		url = "<%=webUri%>/app/index.do?type=notice";
	}else if(type == "receiveMemo") {
		url = "<%=webUri%>/app/index.do?type=receiveMemo";
	}		
	
	parent.frames["content"].location.href = url;
}

function goPasswordChange(){
		
	var compId = "<%=compId%>";
	var orgType = "<%=orgType%>";
	var userList = "<%=userList%>";
	
	if(orgType = null){
		orgType = "";
	}
	
	var win = window.open("<%=portal_url%>/acube/jsp/DisplayPasswordChange.jsp?orgId="+compId+"&orgType="+orgType+"&userList="+userList,"pop","width=350,height=250,scrollbars=yes,resizable=yes");
	win.focus();
	
}

function exportExcel() {
	document.getElementById("myframe").src = "<%=webUri%>/app/common/excel/exportExcel.do";
}

function logout(){
	
	parent.location.href = "<%=webUri%>/app/login/logout.do?page=" + encodeURI("<%=(AppConfig.getProperty("logout_redirect_page", "", "login") + "?mode=" + AppConfig.getProperty("systemUser", "", "systemOperation"))%>");
}

/*
 * 연계기안테스트
 */
function lfn_reqExchange(){

   
    //팝업
    var top = (screen.availHeight - 250) / 2;
    var left = (screen.availWidth - 400) / 2;
    popupWin = window.open("<%=webUri%>/app/jsp/enforce/EnfTestPage.jsp", "popupWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=1000,height=700," +
            "scrollbars=no,resizable=no"); 
}

function searchAppDoc() {
	var width = 1200;
	
	if (screen.availWidth < 1200) {	
	    width = screen.availWidth;
	}
	
	var height = 768;
	
	if (screen.availHeight > 768) {	
	    height = screen.availHeight;	
	}
	
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	var linkUrl;		
	linkUrl = "<%=webUri%>/app/approval/searchAppDoc.do?docNum=" + "전산-45";
		
	appDoc = window.open(linkUrl, "appDoc", option);

}

function processMobile() {
	var processMobileWin = openWindow("processMobileWin", "<%=webUri%>/app/approval/createMobileDoc.do", "400", "300");	
}

function jsEncodeURIComponent(sValue) {
    var sEncValue = isUTF ? encodeURIComponent(sValue) : sValue;
    return sEncValue;
}

function userSearch()
{
    var searchName = document.myhome.textfield4User.value;
    //alert(searchName);
    if (searchName.indexOf("%") != -1 || searchName.indexOf("#") != -1 ||
        searchName.indexOf("@") != -1 || searchName.indexOf("&") != -1)
    {
        alert("일부 특수문자의 사용을 제한합니다.(@#%&)");
        return;
    }
    if (searchName == "*" || searchName == "**" || searchName == "***")
    {
        alert("'*' 은 한글자 이상의 다른 문자와 함께 사용하세요");
        return;
    }
 
    var returl = "http://10.1.20.123/EP/web/user/jsp/UM_SrchUsr.jsp?EPFROM=TOP&CMD=F&SRCHNAME=" + jsEncodeURIComponent(searchName);
 
    var w = "800";
    var h = "450";
    var sw = screen.width;
    var sh = screen.height;
    var l = (eval(sw)-eval(w))/2;
    var t = (eval(sh)-eval(h))/2;
 
    myUserSearch = window.open("about:blank","userSearch","resizable=no,scrollbars=auto,status=no,width="+w+",height="+h+",left="+l+",top="+t);
 
    document.all.frmUserSrch.SRCHNAME.value = searchName;
    document.all.frmUserSrch.target = 'userSearch';
    document.all.frmUserSrch.submit();
}

function selectAppConfig() {
	var configWin = openWindow("configWin", "<%=webUri%>/app/approval/admin/selectAppConfig.do", "600", "600", "yes");		
}

function listSchedule() {														
	var configWin = openWindow("configWin", "<%=webUri%>/app/schedule/listSchedule.do", "800", "600", "yes");		
}

//겸직 변경
function changeConcurrent(){
	parent.content.location.href = "<%=webUri%>/app/login/loginProcessByConcurrent.do?concurrentUserId=" + document.getElementById("Concurrent").value;
}

function makeThumbNail(ext) {
	var thumbWin = openWindow("thumbWin", "<%=webUri%>/app/approval/admin/makeThumbName.do?ext=" + ext, "600", "600");		
}
//-->
</script>
</head>
<body>
<form name="myhome" id="myhome" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" >	
  <tr>
    <td height="3" background="<%=imagePath%>/top/top_bg.gif" ></td>
  </tr>
  <tr>
    <td height="53" background="<%=imagePath%>/top/apptopw.jpg" style="background-repeat:no-repeat"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15" height="53">&nbsp;</td>
        <td width="145" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10"></td>
          </tr>
          <tr>
            <td><img src="<%=imagePath%>/top/top_logo.jpg"></td>
          </tr>
        </table></td>
        <td align="right" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="5" align="right"></td>
          </tr>
          <tr>
            <td align="right"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td>&nbsp;</td>
                <td width="18"><span class="logout"><img src="<%=imagePath%>/top/top_logout_icon.gif" align="absmiddle"></span></td>
                <td align="right"><span class="logout"><%=userName%></span></td>
<%
     String roleCode = (String) session.getAttribute("ROLE_CODES");
	 String adminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	 if (roleCode.indexOf(adminCode) != -1 && !isExtWeb) {
/*	     
%>                
                <td width="21" align="center"><img src="<%=imagePath%>/top/top_global_sun.gif" width="1" height="7"></td>
                <td><span class="topmenu"><a href="javascript:goMenu('admin');"><spring:message code='common.title.administrator' /></a></span></td>
<%
*/
	}
%>
				<td align="right"><span class="logout">&nbsp;&nbsp;<a class="logout" href="javascript:goPasswordChange();" style="text-decoration:none;">암호변경</a></span></td>
				<td align="right"><span class="logout">&nbsp;&nbsp;</span></td>
                <td width="55" align="right"><a href="javascript:logout();"><img src="<%=imagePath%>/top/top_logout.gif" border="0" align="absmiddle"></a></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>&nbsp;</td>
                <%-- <td width="60" align="right" class="logout"><spring:message code='common.title.find.employee' /></td>
                <td width="1" align="right">&nbsp;</td>
                <td width="130" align="right"><input name="textfield4User" id="textfield4User" type="text" onKeyPress="javascript:var keycode = event.keyCode || event.which; if (keycode==13) {userSearch();return false;}" class="top_search" size="20"></td>
                <td width="1" align="right">&nbsp;</td>
                <td width="22" align="right"><a href="#"><img src="<%=imagePath%>/top/seach_icon.gif" border="0"></a></td>
                <td width="55" align="right"><label class="logout">Search</label></td>
                <td width="1" align="right">&nbsp;</td>
                <td width="130" align="right"><input name="textfield32" type="text" class="top_search" size="20"></td>
                <td width="1" align="right">&nbsp;</td>
                <td width="22" align="right"><a href="#"><img src="<%=imagePath%>/top/seach_icon.gif" border="0"></a></td> --%>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="15">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="40"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="26"><img src="<%=imagePath%>/top/top_menu_bg.gif"></td>
        <td width="100%" background="<%=imagePath%>/top/top_menu_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('')"><spring:message code='common.mainmenu.approval' /></a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('bind');"><spring:message code='common.mainmenu.bind' /></a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <%-- <td width="10%" align="center" class="menu"><a href="javascript:goMenu('receiveMemo')"><spring:message code='common.mainmenu.memo' /></a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('notice')"><spring:message code='common.mainmenu.notice' /></a></td> --%>
             <%--  <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('test');"><spring:message code='common.mainmenu.test' /></a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('m_test');"><spring:message code='common.mainmenu.m_test' /></a></td> --%>
<!--		  <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('pubPost');"><spring:message code='common.mainmenu.publicpost' /></a></td>
	          <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:goMenu('edms');"><spring:message code='common.mainmenu.edms' /></a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:makeThumbNail('jpg');">JPG</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:makeThumbNail('gif');">GIF</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:makeThumbNail('bmp');">BMP</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:makeThumbNail('bmp2');">BMP2</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:makeThumbNail('png');">PNG</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:listSchedule();">스케줄</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:selectAppConfig();">서버환경설정</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:processMobile();">모바일결재</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:searchAppDoc();">문서조회</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:lfn_reqExchange();">연계</a></td>
              <td width="1"><img src="<%=imagePath%>/top/top_menu_sun.gif" width="1" height="11"></td>
              <td width="10%" align="center" class="menu"><a href="javascript:exportExcel();">엑셀저장</a></td>
-->              
              <td width="70%">&nbsp;</td>
              </tr>
          </table>
            <!--//가로길이고정용시작//-->
            <table border=0 width=100%>
              <tr>
                <td><table border=0 width=930 align=center>
                </table></td>
              </tr>
            </table>
          <!--//가로길이고정용끝//-->
        </td>
        <td width="26"><img src="<%=imagePath%>/top/top_menu_bg.gif"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="22" bgcolor="#EFEFEF">&nbsp</td>
  </tr>
</table>
</form>
<form name='frmUserSrch' id='frmUserSrch' method='get' action="http://10.1.20.123/EP/web/user/jsp/UM_SrchUsr.jsp">
   <input type="hidden" name="SRCHNAME" id="SRCHNAME" value="">
   <input type="hidden" name="EPFROM" id="EPFROM" value='TOP'>
   <input type="hidden" name="CMD" id="CMD" value='F'>
</form>
<form name='frm_uni' id='frm_uni' method='post' action="http://10.1.20.123/EP/web/search/SR_Search.jsp">
   <input type="hidden" name="query" id="query" value="">
   <input type="hidden" name="popup" id="popup" value='Y'>
   <input type="hidden" name="execute" id="execute" value='Y'>
</form>
<iframe id="myframe" frameborder="0" width="0" height="0"></iframe>
</body>
</html>
