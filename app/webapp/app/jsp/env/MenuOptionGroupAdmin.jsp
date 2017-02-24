<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.env.service.impl.EnvOptionAPIService" %>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : MenuOptionGroupAdmin.jsp 
 *  Description : 관리자 환경설정 좌측메뉴 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.28 
 *   수 정 자 : 김경훈
 *   수정내용 : KDB 요건반영
 * 
 *  @author  김경훈
 *  @since 2011. 10. 04 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
	String defaultUrl = "about:blank";
	
	if (roleCode.indexOf(systemAdminCode) != -1) {
	    defaultUrl = webUri+"/app/approval/admin/selectAppConfig.do";
	}
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="JavaScript">
$(document).ready(function(){ 
	init(); 
 
});
 
function init(){
	toggleMenu('GenOption', '<%=defaultUrl%>');
}

function linkUrl(url){
	parent.frames[parent.frames.length - 1].location.href = url;
}

function toggleMenu(menuId, url) {
	var imgId = 'Img'+menuId;
	var subId = menuId+"_Sub";
	var tdId = menuId+"_Td";
	var leftIconId = "leftIcon"+menuId;
	if (document.getElementById(menuId).style.display == 'block') {
		document.getElementById(menuId).style.display = 'none';
		document.getElementById(subId).style.backgroundColor  = "#ffffff";
		document.getElementById(tdId).className = "menu_end";
		$('#'+leftIconId).show();			
	} else {
		document.getElementById(menuId).style.display = 'block';
		document.getElementById(imgId).src = "<%=imagePath%>/left_menu/left_icon.gif";
		document.getElementById(subId).style.backgroundColor  = "#e0e0e0";
		document.getElementById(tdId).className = "menu_open";
		parent.frames["body"].location.href = url;
		$('#'+leftIconId).hide();			
	}
}

function goMain(){
	parent.location.href = "<%=webUri%>/app/index.do";
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="border-right:solid 1px #dfdfdf;">
	<tr>
		<td height="1" colspan="3" bgcolor="e3e3e3"></td>
	</tr>
	<tr onClick="javascript:goMain();" style="cursor:pointer;">
		<td height="50" valign="top" background="<%=imagePath%>/left_menu/title_approval.gif">
		</td>
	</tr>
	
	<tr>
		<td valign="top">
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="GenOption_Td">
									<!-- 일반옵션 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconGenOption'>
									<a href="javascript:toggleMenu('GenOption', '<%=defaultUrl%>');"><spring:message code="env.option.menu.main.02"/></a>
								</td>
								<td width="20" valign="top" id="GenOption_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgGenOption'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>	
				<tr>
					<td class="menu_bg_01">
						<div id='GenOption' style="display:none;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								
<% 
	 if (roleCode.indexOf(systemAdminCode) != -1) {
%>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 서버환경설정 조회 -->
										<a href='<%=webUri%>/app/approval/admin/selectAppConfig.do' target="body"><spring:message code="env.option.menu.sub.41"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 서버환경설정 조회 -->
										<a href='<%=webUri%>/app/schedule/listSchedule.do' target="body"><spring:message code="env.option.menu.sub.47"/></a>
									</td>
								</tr>
<%
	 }
%>								
							</table>
						</div>	
					</td>
				</tr>
				
				<tr>
					<td class="menu_line_01"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>