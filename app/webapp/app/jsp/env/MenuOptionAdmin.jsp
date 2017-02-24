<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ page import="com.sds.acube.app.env.service.impl.EnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.login.security.EnDecode" %>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : MenuOptionAdmin.jsp 
 *  Description : 관리자 환경설정 좌측메뉴 
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.21 
 *   수 정 자 : 최봉곤 
 *   수정내용 : IAM 관리 연계
 * 
 *  @author  신경훈
 *  @since 2011. 3. 28 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
	String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
	String iamAdminCode = AppConfig.getProperty("role_iam", "", "role"); //전자결재에서 IAM SSO 연계를 통해 조직/사용자/역할/분류체계 관리
	String compId = (String)session.getAttribute("COMP_ID");
	String periodType = AppConfig.getProperty("periodType", "Y", "etc");
	String periodMenu = messageSource.getMessage("env.option.menu.sub.46."+periodType, null, langType); // 회기/연도
	
	String opt204Yn = (String)request.getAttribute("opt204Yn"); //서명인날인대장 사용여부
	String opt205Yn = (String)request.getAttribute("opt205Yn"); //직인날인대장 사용여부
	String opt207Yn = (String)request.getAttribute("opt207Yn"); //일상감사일지 사용여부
	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt107 = appCode.getProperty("OPT107", "OPT107", "LOL"); // 배부대기함 사용여부
	opt107 = envOptionAPIService.selectOptionValue(compId, opt107);
	String opt108 = appCode.getProperty("OPT108", "OPT108", "LOL"); // 접수대기함  사용여부
	opt108 = envOptionAPIService.selectOptionValue(compId, opt108);
	
	String opt423 = envOptionAPIService.selectOptionValue(compId, "OPT423"); // 캐비닛 사용여부

	boolean devmode = AppConfig.getBooleanProperty("dev_mode", false, "general");
	
	String comp_id[] = null;
	String comp_name[] = null;
	int compCount = 0;
	
	comp_id = AppConfig.getConfigManager().getArray("compid", null, "companyinfo");
	comp_name = AppConfig.getConfigManager().getArray("compname", null, "companyinfo");
	
	if (comp_id != null) {
		compCount = comp_id.length;
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
	<%	if (roleCode.indexOf(appSystemAdminCode) != -1) {	%>
			toggleMenu('AdminList', '<%=webUri%>/app/list/admin/ListAdminAll.do');
	<% } else if (roleCode.indexOf(iamAdminCode) != -1) {	%>
			toggleMenu('IAM', 'org');
	<% } else if (roleCode.indexOf(systemAdminCode) != -1) { %>
			toggleMenu('System', '<%=webUri%>/app/approval/admin/selectAppConfig.do');
	<% } %>
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
			document.getElementById(imgId).style.display = 'none';
			document.getElementById(subId).style.backgroundColor  = "#ffffff";
			document.getElementById(tdId).className = "menu_end";
			$('#'+leftIconId).show();			
		} else {
			document.getElementById(menuId).style.display = 'block';
			document.getElementById(imgId).style.display = 'block';
			document.getElementById(imgId).src = "<%=imagePath%>/left_menu/left_icon.gif";
			document.getElementById(subId).style.backgroundColor  = "#e0e0e0";
			document.getElementById(tdId).className = "menu_open";
			if ( url == "org" )
				connectIAM('org');
			else
				parent.frames["body"].location.href = url;
			$('#'+leftIconId).hide();			
		}
	}
	
	function connectIAM(menu) {
		parent.frames["body"].location.href = "<%=webUri%>/app/common/iam/redirect.do?menu="+menu;
	}

	function goMain(){
		parent.location.href = "<%=webUri%>/app/index.do";
	}

	function confirmMenu(url, type){
		if (typeof(type) == "undefined") {
			type = "direct";
		}

		if(confirm("<spring:message code="env.msg.confirm.confirmMenu"/>")){
			if(type == "direct"){
				parent.frames[parent.frames.length - 1].location.href = url;
			}else{
				toggleMenu(type, url);
			}
		}else{
			return false;
		}

	}	
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<% 
	 if ((roleCode.indexOf(appSystemAdminCode) != -1) || (roleCode.indexOf(systemAdminCode) != -1) || (roleCode.indexOf(iamAdminCode) != -1)) {
%>
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
				<% if (roleCode.indexOf(appSystemAdminCode) != -1) {	%>	
				<!-- 관리자 목록 -->
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="AdminList_Td">	
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconAdminList'>								
									<a href="javascript:toggleMenu('AdminList', '<%=webUri%>/app/list/admin/ListAdminAll.do');"><spring:message code="env.option.menu.main.03"/></a>
								</td>
								<td width="20" valign="middle" id="AdminList_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgAdminList'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>	         
				<tr class="menu_bg_01">
					<td>
						<div id='AdminList' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서전체 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminAll.do' target="body"><spring:message code="env.option.menu.sub.16"/> </a>
									</td>
								</tr>
								<%if("Y".equals(opt107)){ %>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 배부대기함 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminDistributionWait.do' target="body"><spring:message code="env.option.menu.sub.52"/></a>
									</td>
								</tr>
								<% } %>
								<%if("Y".equals(opt108)){ %>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 접수대기함 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminReceiveWait.do' target="body"><spring:message code="env.option.menu.sub.53"/></a>
									</td>
								</tr>
								<% } %>
								<%if("Y".equals(opt204Yn)){ %>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 서명인날인 목록 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminStampSeal.do' target="body"><spring:message code="env.option.menu.sub.26"/></a>
									</td>
								</tr>	
								<% } %>	
								<%if("Y".equals(opt205Yn)){ %>						
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 직인날인 목록 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminSeal.do' target="body"><spring:message code="env.option.menu.sub.17"/></a>
									</td>
								</tr>
								<% } %>	
								<%if("Y".equals(opt207Yn)){ %>							
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 일상감사일지 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminAudit.do' target="body"><spring:message code="env.option.menu.sub.18"/></a>
									</td>
								</tr>
								<% } %>								
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 연계처리결과 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminBizResult.do' target="body"><spring:message code="env.option.menu.sub.38"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서관리연계결과 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminDocmgrResult.do' target="body"><spring:message code="env.option.menu.sub.40"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서유통 오류이력 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminRelayResult.do' target="body"><spring:message code="env.option.menu.sub.61"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서유통 응답이력 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminRelayAckResult.do' target="body"><spring:message code="env.option.menu.sub.61.01"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 접속이력 -->
										<a href='<%=webUri%>/app/list/admin/ListAdminAccHis.do' target="body"><spring:message code="env.option.menu.sub.48"/></a>
									</td>
								</tr>
								
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>			 
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="Statistics_Td">
									<!-- 관리자 통계 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconStatistics'>
									<a href="#" onclick="toggleMenu('Statistics', '<%=webUri%>/app/statistics/admin/sendStatusStatistics.do');"><spring:message code="env.option.menu.main.05"/></a>
								</td>
								<td width="20" valign="middle" id="Statistics_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgStatistics'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>	         
				<tr class="menu_bg_01">
					<td>
						<div id='Statistics' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 부서별 발송대기문서 현황 -->
										<a href='<%=webUri%>/app/statistics/admin/sendStatusStatistics.do' target="body"><spring:message code="env.option.menu.sub.statistics.03"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 부서별 문서미처리 현황 -->
										<a href='<%=webUri%>/app/statistics/admin/receiptStatusStatistics.do' target="body"><spring:message code="env.option.menu.sub.statistics.01"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 개인별 문서미처리 현황 -->
										<a href='<%=webUri%>/app/statistics/admin/processStatusStatistics.do' target="body"><spring:message code="env.option.menu.sub.statistics.02"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 역할별통계(부서) -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/approvalRoleStatistics.do');" ><spring:message code="env.option.menu.sub.27"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 역할별통계(개인) -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/approvalRoleStatistics.do?deptYn=N');"><spring:message code="env.option.menu.sub.28"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서구분별통계(부서) -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/docKindStatistics.do');"><spring:message code="env.option.menu.sub.32"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서구분별통계(개인) -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/docKindStatistics.do?deptYn=N');"><spring:message code="env.option.menu.sub.33"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 전자결재건수 -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/approvalCntStatistics.do');"><spring:message code="env.option.menu.sub.34"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 발송건수 -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/sendCntStatistics.do');"><spring:message code="env.option.menu.sub.35"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 접수건수 -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/receiveCntStatistics.do');"><spring:message code="env.option.menu.sub.36"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 대장문서건수 -->
										<a href="#" onclick="confirmMenu('<%=webUri%>/app/statistics/admin/registCntStatistics.do');"><spring:message code="env.option.menu.sub.37"/></a>
									</td>
								</tr>
								
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>			 
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="AppOption_Td">
									<!-- 결재옵션 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconAppOption'>
									<a href="javascript:toggleMenu('AppOption', '<%=webUri%>/app/env/admin/selectOptionProcess.do');"><spring:message code="env.option.menu.main.01"/></a>
								</td>
								<td width="20" valign="middle" id="AppOption_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgAppOption'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>	         
				<tr class="menu_bg_01">
					<td>
						<div id='AppOption' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 기본옵션 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionProcess.do' target="body"><spring:message code="env.option.menu.sub.011"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 결재함구분 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionBox.do' target="body"><spring:message code="env.option.menu.sub.012"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 문서대장 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionRegister.do' target="body"><spring:message code="env.option.menu.sub.013"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 일상감사/감사대장 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionInspection.do' target="body"><spring:message code="env.option.menu.sub.014"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 공람열람 옵션 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionReadShow.do' target="body"><spring:message code="env.option.menu.sub.015"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 기타 결재옵션 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionEtc.do' target="body"><spring:message code="env.option.menu.sub.016"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 알림/인증 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionNoticeCert.do' target="body"><spring:message code="env.option.menu.sub.11"/> </a>
									</td>
								</tr>
<%	if (devmode) {	%>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 비활성화 옵션 -->
										<a href='<%=webUri%>/app/env/admin/selectOptionDisabled.do' target="body"><spring:message code="env.option.menu.sub.017"/></a>
									</td>
								</tr>
<%	}	%>
								<!-- 채번/가지번호 -->
								<!--tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">										
										<a href='<%=webUri%>/app/env/admin/selectOptionDocNumber.do' target="body"><spring:message code="env.option.menu.sub.05"/></a>
									</td>
								</tr-->								
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="GenOption_Td">
									<!-- 관리옵션 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconGenOption'>
									<a href="javascript:toggleMenu('GenOption', '<%=webUri%>/app/env/admincharge/selectOptionCLS.do');"><spring:message code="env.option.menu.main.02"/></a>
								</td>
								<td width="20" valign="middle" id="GenOption_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgGenOption'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>	         
				<tr class="menu_bg_01">
					<td>
						<div id='GenOption' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 캠페인/로고/심볼 관리 -->
										<a href='<%=webUri%>/app/env/admincharge/selectOptionCLS.do' target="body"><spring:message code="env.option.menu.sub.12"/></a>
									</td>
								</tr>	
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 우편번호 일괄 등록 -->
										<a href='<%=webUri%>/app/env/admin/insertZipcode.do' target="body">우편번호 일괄 등록</a>
									</td>
								</tr>								
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 부서정보 관리 -->
										<a href='<%=webUri%>/app/env/admin/selectEnvDeptInfoAdmin.do' target="body"><spring:message code="env.option.menu.sub.45"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 개인정보 관리 -->
										<a href='<%=webUri%>/app/env/admin/envPersonalInfoAdmin.do' target="body"><spring:message code="env.option.menu.sub.25"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 발신명의 관리 -->
										<a href='<%=webUri%>/app/env/admin/selectEnvSenderTitle.do' target="body"><spring:message code="env.option.menu.sub.06"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 부서이미지 관리 -->
										<a href='<%=webUri%>/app/env/listOrgImage.do' target="body"><spring:message code="env.option.menu.sub.43"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 이미지 일괄 등록 -->
										<a href='<%=webUri%>/app/approval/admin/insertOrgImage.do' target="body"><spring:message code="env.option.menu.sub.63"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 수신자그룹 관리 -->
										<a href='<%=webUri%>/app/env/admin/listEnvRecvGroup.do' target="body"><spring:message code="env.option.menu.sub.08"/></a>
									</td>
								</tr>
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 부재정보 관리 -->
										<a href='<%=webUri%>/app/env/admin/envEmptyInfo.do' target="body"><spring:message code="env.option.menu.sub.19"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 회기/연도 관리 -->
										<a href='<%=webUri%>/app/env/admin/selectPeriodInfo.do' target="body">
											<%=periodMenu%>
										</a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 양식 관리 -->
										<a href='<%=webUri%>/app/env/form/admin/ListEnvFormMain.do' target="body"><spring:message code="env.option.menu.sub.09"/></a>
									</td>
								</tr>
                                <tr>
                                    <td align="right">
                                        <img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
                                    <td class="menu_body">
                                        <!-- 연계시스템 관리 -->
                                        <a href='<%=webUri%>/app/env/admincharge/listEnvBizSystem.do' target="body"><spring:message code="env.option.menu.sub.14"/></a>
                                    </td>
                                </tr>								
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 임원 담당부서지정 -->
										<a href='<%=webUri%>/app/env/user/selectOfficerForDept.do' target="body"><spring:message code="env.option.menu.sub.42"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 감사 담당부서지정 -->
										<a href='<%=webUri%>/app/env/user/selectAuditorForDept.do' target="body"><spring:message code="env.option.menu.sub.23"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 협조문서함 담당지정 -->
										<a href='<%=webUri%>/app/env/user/selectAssistant.do' target="body"><spring:message code="env.option.menu.sub.51"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 전자결재 코드관리 -->
										<a href='<%=webUri%>/app/env/admin/listEnvCodeMng.do' target="body"><spring:message code="env.option.menu.sub.20"/></a>
									</td>
								</tr>
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>
<% 	if ( opt423.equals("Y") ) { %>				
			
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="Bind_Td">
									<!-- 캐비닛 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconBind'>
									<a href="javascript:toggleMenu('Bind', '<%=webUri%>/app/bind/unit/simple/list.do');"><spring:message code="env.option.menu.main.04"/></a>
								</td>
								<td width="20" valign="middle" id="Bind_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgBind'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>
				<tr class="menu_bg_01">
					<td>
						<div id='Bind' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 단위업무관리 -->
										<%-- <a href='<%=webUri%>/app/bind/unit/simple/list.do' target="body"><spring:message code="env.option.menu.sub.21"/></a> --%>
										<a href='<%=webUri%>/app/bind/unit/simple/unitManager.do' target="body"><spring:message code="env.option.menu.sub.21"/></a>
									</td>
								</tr>								
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 캐비닛관리 -->
										<a href='<%=webUri%>/app/bind/list.do' target="body"><spring:message code="env.option.menu.sub.22"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 미캐비닛관리 -->
										<a href='<%=webUri%>/app/bind/document/nonBind.do' target="body"><spring:message code="env.option.menu.sub.30"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 공유캐비닛관리 -->
										<a href='<%=webUri%>/app/bind/sharedList.do' target="body"><spring:message code="env.option.menu.sub.31"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 일괄생성 캐비닛 관리 -->
										<a href='<%=webUri%>/app/bind/bindBatchList.do' target="body"><spring:message code="env.option.menu.sub.49"/></a>
									</td>
								</tr>
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>
<% 	} %>				
<% } %>							
<% if ( roleCode.indexOf(iamAdminCode) != -1 ) { %>				
				
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="IAM_Td">
									<!-- IAM 관리 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconIAM'>
									<a href="javascript:toggleMenu('IAM', 'org');"><spring:message code="env.option.menu.main.07"/></a>
								</td>
								<td width="20" valign="middle" id="IAM_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgIAM'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>
				<tr class="menu_bg_01">
					<td>
						<div id='IAM' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 조직관리 -->
										<a href="javascript:connectIAM('org');"><spring:message code="env.option.menu.sub.07.01"/></a>
									</td>
								</tr>								
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 사용자관리 -->
										<a href="javascript:connectIAM('user');"><spring:message code="env.option.menu.sub.07.02"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 역할관리 -->
										<a href="javascript:connectIAM('role');"><spring:message code="env.option.menu.sub.07.03"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 코드관리 -->
										<a href="javascript:connectIAM('code');"><spring:message code="env.option.menu.sub.07.04"/></a>
									</td>
								</tr>
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 분류체계관리 -->
										<a href="javascript:connectIAM('classification');"><spring:message code="env.option.menu.sub.07.05"/></a>
									</td>
								</tr>
							</table>
						</div>	
					</td>
				</tr>
				<tr>
					<td class="menu_line_01"></td>
				</tr>
<% } %>				
<% 
	 if (roleCode.indexOf(systemAdminCode) != -1) {
%>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">					
							<tr>
								<td class="menu_end" id="System_Td">
									<!-- 시스템 관리 -->
									<img src="<%=imagePath%>/left_menu/icon_01.gif" id='leftIconSystem'>
									<a href="javascript:toggleMenu('System', '<%=webUri%>/app/approval/admin/selectAppConfig.do');"><spring:message code="env.option.menu.main.06"/></a>
								</td>
								<td width="20" valign="middle" id="System_Sub" style="background-color:#ffffff">
									<img src="<%=imagePath%>/left_menu/left_icon.png" id='ImgSystem'>
								</td>
							</tr>
						</table>						
					</td>
				</tr>
				<tr class="menu_bg_01">
					<td>
						<div id='System' class="menu_bg_02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 서버 공통환경설정 조회 -->
										<a href='<%=webUri%>/app/approval/admin/selectAppConfig.do' target="body"><spring:message code="env.option.menu.sub.41"/></a>
									</td>
								</tr>
			<%
			for (int nLoop = 0; nLoop < compCount; nLoop++) 
		    {
				String _compId = comp_id[nLoop];
				String _compName = comp_name[nLoop];
			%>	
								<tr>
									<td width="22" align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 서버 회사고유 환경설정 조회 -->
										<a href='<%=webUri%>/app/approval/admin/selectCompanyConfig.do?compID=<%=_compId%>&compName=<%=_compName%>' target="body"><%=_compName%> <%=messageSource.getMessage("env.option.menu.sub.comp.id", null, langType)%></a>
									</td>
								</tr>
				
			<% } %>					    
								<tr>
									<td align="right">
										<img src="<%=imagePath%>/left_menu/left_body_icon.gif"></td>
									<td class="menu_body">
										<!-- 스케줄 관리 -->
										<a href='<%=webUri%>/app/schedule/listSchedule.do' target="body"><spring:message code="env.option.menu.sub.47"/></a>
									</td>
								</tr>
							</table>
						</div>	
					</td>
				</tr>
				
				<tr>
					<td class="menu_line_01"></td>
				</tr>
<% } %>				
			</table>
		</td>
	</tr>
</table>
<% } %>
</body>
</html>