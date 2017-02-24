<%@ page contentType="text/html; charset=UTF-8" %>
<%
/**
 *  Class Name  : listBox.jsp
 *  Description : 왼쪽 함 리스트
 *  Modification Information
 *
 *   수 정 일 :
 *   수 정 자 :
 *   수정내용 :
 *
 *  @author  김경훈
 *  @since 2011. 03. 21
 *  @version 1.0
 *  @see
 */
%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="/app/jsp/common/header.jsp"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ page import="com.sds.acube.app.common.vo.UserVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="com.sds.acube.app.login.vo.UserProfileVO"%>

<%
	String imagePath = webUri + "/app/ref/image";
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String roleCodes = (String)session.getAttribute("ROLE_CODES");

    String boxCode101 = appCode.getProperty("OPT101", "OPT101", "OPT"); // 임시저장함
    String boxCode102 = appCode.getProperty("OPT102", "OPT102", "OPT"); // 연계기안함
    String boxCode103 = appCode.getProperty("OPT103", "OPT103", "OPT"); // 결재대기함
    String boxCode104 = appCode.getProperty("OPT104", "OPT104", "OPT"); // 진행문서함

    String boxCode105 = appCode.getProperty("OPT105", "OPT105", "OPT"); // 발송대기함
    String boxCode106 = appCode.getProperty("OPT106", "OPT106", "OPT"); // 발송심사함
    String boxCode107 = appCode.getProperty("OPT107", "OPT107", "OPT"); // 배부대기함
    String boxCode108 = appCode.getProperty("OPT108", "OPT108", "OPT"); // 접수대기함

    String boxCode109 = appCode.getProperty("OPT109", "OPT109", "OPT"); // 기안문서함
    String boxCode110 = appCode.getProperty("OPT110", "OPT110", "OPT"); // 결재완료함
    String boxCode111 = appCode.getProperty("OPT111", "OPT111", "OPT"); // 접수완료함
    String boxCode112 = appCode.getProperty("OPT112", "OPT112", "OPT"); // 공람문서함
    String boxCode113 = appCode.getProperty("OPT113", "OPT113", "OPT"); // 후열문서함
    String boxCode114 = appCode.getProperty("OPT114", "OPT114", "OPT"); // 감사열람함 @@@
    String boxCode115 = appCode.getProperty("OPT115", "OPT115", "OPT"); // 임원열람함
    String boxCode116 = appCode.getProperty("OPT116", "OPT116", "OPT"); // 주관부서문서함
    String boxCode117 = appCode.getProperty("OPT117", "OPT117", "OPT"); // 본부문서함
    String boxCode118 = appCode.getProperty("OPT118", "OPT118", "OPT"); // 기관문서함   // jth8172 2012 신결재 TF
    String boxCode125 = appCode.getProperty("OPT125", "OPT125", "OPT"); // 회사문서함
    String boxCode130 = appCode.getProperty("OPT130", "OPT130", "OPT"); // 대결문서함

    String boxCode119 = appCode.getProperty("OPT119", "OPT119", "OPT"); // 재배부요청함
//    String boxCode120 = appCode.getProperty("OPT120", "OPT120", "OPT"); // 여신문서함
    String boxCode121 = appCode.getProperty("OPT121", "OPT121", "OPT"); // 임원결재함
    String boxCode122 = appCode.getProperty("OPT122", "OPT122", "OPT"); // 부서협조열람함
    String boxCode123 = appCode.getProperty("OPT123", "OPT123", "OPT"); // 협조문서함

    String boxCode201 = appCode.getProperty("OPT201", "OPT201", "OPT"); // 문서등록대장
    String boxCode202 = appCode.getProperty("OPT202", "OPT202", "OPT"); // 문서배부대장
    String boxCode203 = appCode.getProperty("OPT203", "OPT203", "OPT"); // 미등록문서대장
    String boxCode204 = appCode.getProperty("OPT204", "OPT204", "OPT"); // 서명인날인대장
    String boxCode205 = appCode.getProperty("OPT205", "OPT205", "OPT"); // 직인날인대장
    String boxCode206 = appCode.getProperty("OPT206", "OPT206", "OPT"); // 감사문서함 @@@
    String boxCode207 = appCode.getProperty("OPT207", "OPT207", "OPT"); // 일상감사대장 @@@
    String boxCode208 = appCode.getProperty("OPT208", "OPT208", "OPT"); // 감사직인날인대장 @@@

    String boxCode124 = appCode.getProperty("OPT124", "OPT124", "OPT");  //통보문서함 // jth8172 2012 신결재 TF

    String roleId00 = AppConfig.getProperty("role_admin", "", "role");
    String roleId01 = AppConfig.getProperty("role_iam", "", "role");
    String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");
    String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");    //처리과 문서책임자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); //문서과 문서책임자
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role");
	String roleId16 = AppConfig.getProperty("role_signatoryadmin", "", "role");
//	String roleId22 = AppConfig.getProperty("role_creditassessor", "", "role");
	String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role");
	String roleId30 = AppConfig.getProperty("role_officecoordinationreader", "", "role");
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role");
	String roleId32 = AppConfig.getProperty("role_officer", "", "role");

	String optAppDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	String optEnfDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");

	String draftYn = AppConfig.getProperty("draftYn", "Y", "systemOperation");
    Map<String,String[]> pMap = (Map<String,String[]>)request.getAttribute("results");

    String toggleMenu 	= CommonUtil.nullTrim(request.getParameter("toggleMenu"));
    String boxCode		= CommonUtil.nullTrim(request.getParameter("boxCode"));
    String boxUrl		= CommonUtil.nullTrim(request.getParameter("boxUrl"));
    String authMsg		= "";

    if("Receive".equals(toggleMenu) && !"Y".equals((String)pMap.get(roleId11)[0])) {
		toggleMenu	= "Approval";
		boxCode		= "linkMenu_"+boxCode103;
		boxUrl 		= webUri+"/app/list/approval/ListApprovalWaitBox.do";
		authMsg		= messageSource.getMessage("list.list.msg.noAuth" , null, langType);
    }

    String defualtMenu = "Draft";
    String defualtBodyUrl = webUri+"/app/list/main/mainList.do";

	if(isExtWeb){
	    defualtMenu = "Approval";
	}

	String userId = (String) session.getAttribute("USER_ID");
	String loginId = (String) session.getAttribute("LOGIN_ID");
	IOrgService orgService = (IOrgService)ctx.getBean("orgService");
	List<UserVO> concurrentList = orgService.selectConcurrentUserListByLoginId(loginId);

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt364 = appCode.getProperty("OPT364", "OPT364", "OPT");
	opt364 = envOptionAPIService.selectOptionValue(compId, opt364); // 부서문서관리책임자 발신명의 관리여부

	String opt373 = appCode.getProperty("OPT373", "OPT373", "OPT");
	opt373 = envOptionAPIService.selectOptionValue(compId, opt373); // 직인날인신청 사용여부

	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	opt314 = envOptionAPIService.selectOptionValue(compId, opt314); // 열람범위

	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	opt316 = envOptionAPIService.selectOptionValue(compId, opt316); // 공람게시 - 0:공람게시 사용안함, 1:생산문서만 공람게시 사용, 2:접수문서만 공람게시, 3:생산문서/접수문서 공람게시 사용

	boolean devmode = AppConfig.getBooleanProperty("dev_mode", false, "general");

	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = (String) userProfileVO.getInstitution();
	String headOfficeId = (String) userProfileVO.getHeadOffice();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF

	String opt423 = envOptionAPIService.selectOptionValue(compId, "OPT423"); // 편철 사용여부

    // 문서편집기 사용 값 조회
    String opt428 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT428", "OPT428", "OPT"));
	boolean isHTMLEditorSet = false;
	if (opt428 != null && (opt428.indexOf("I3:Y") >= 0)) {
		isHTMLEditorSet = true;
	}

    // 문서등록대장 열람옵션
    String opt382 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT382", "OPT382", "OPT"));

	boolean bOption382 = false;
	if (opt382 != null && (opt382.indexOf("I2:Y") >= 0)) {
		bOption382 = true;
	}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="list.listBox.title.mainTitle"/></title>
<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.slidemod.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript">

$(document).ready(function(){
	init();

	initializeFileManager();
<%	if (!devmode) {	%>
	FileManager.deletefolder(0);
<%	}	%>
});

<%-- var curMenuId = ""; --%>


function init(){


		/* // 메뉴 일단 다 닫기
		var $elBefore = $('ul',this);
		$('#mainMenu > ul ').not($elBefore).slideUp();

		// 클릭 이벤트 정의
		$(function() {
			$('#mainMenu > li').click(function(e) {
				// 다른것들 원상복구
				$('[name=leftMenuFirst]').each(function(index, element) {
					$(element).attr('class','');
					$(element).attr('class','submenu1step02');
				});
				
				
				$(this).removeClass('submenu1step02');
				$(this).addClass('submenu1step04');

				e.stopPropagation();
				
				var $el = $(this).next();
				$('#mainMenu > ul ').slideUp();
				$('#mainMenu  > li > ul ').not($el).slideUpMod(10);
				
				$el.stop(true, true).slideToggle(400);
			});
			$('#mainMenu > ul > li').click(function(e) {
				e.stopImmediatePropagation();
			});
		}); */



	}

<%-- 
function init(){

	$("#ImgDraft").hide();
	$("#ImgApproval").hide();
	$("#ImgSend").hide();
	$("#ImgReceive").hide();
	$("#ImgComplete").hide();
	$("#ImgRegist").hide();
	$("#ImgPubRead").hide();
	$("#ImgInspect").hide();
	$("#ImgEnv").hide();

	<% if(!"".equals(toggleMenu) && !"".equals(boxCode) && !"".equals(boxUrl) ) { %>
		<%if(!"".equals(authMsg)){ %>
			alert("<%=authMsg%>");
		<% } %>
		toggleMenu("<%=toggleMenu%>");
		linkUrl("<%=boxUrl%>","<%=boxCode%>");
	<% }else{ %>
		toggleMenu("<%=defualtMenu%>");
		//goBodyUrl("<%=defualtBodyUrl%>");
	<% } %>


	// 메뉴 1depth default class setting 
	// var menuLeftMenuFirst = $('[group=leftMenuFirst]');
	// $('#'+menuLeftMenuFirst).removeClass("submenu1step02");
	// $('#'+menuLeftMenuFirst).addClass("submenu1step02");


	// 메뉴 일단 다 닫기
	$('#mainMenu > li > ul ').slideUp();
		
	$(function() {
		$('#mainMenu > li').click(function(e) {
			$('[name=leftMenuFirst]').each(function(index, element) {
				$(element).attr('class','');
				$(element).attr('class','submenu1step02');
			});
			
			$(this).removeClass('submenu1step02');
			$(this).addClass('submenu1step04');
			var childUl = $(this).find('ul')
			
			// slideUp, slideDown을 써도 되나, 화면이 흔들린다.
			childUl.show();
			$('#mainMenu  > li > ul ').not(childUl).hide();
		});
	});
	
	<% if(!"".equals(toggleMenu) && !"".equals(boxCode) && !"".equals(boxUrl) ) { %>
		<%if(!"".equals(authMsg)){ %>
			alert("<%=authMsg%>");
		<% } %>
		toggleMenu("<%=toggleMenu%>");
		linkUrl("<%=boxUrl%>","<%=boxCode%>");
	<% }else{ %>
		toggleMenu("<%=defualtMenu%>");
	<% } %>
/* 
	// 클릭 이벤트 정의
	$(function() {
		$('#mainMenu > li').click(function(e) {
			// 다른것들 원상복구
			$('[name=leftMenuFirst]').each(function(index, element) {
				$(element).attr('class','');
				$(element).attr('class','submenu1step02');
			});
			
			
			$(this).removeClass('submenu1step02');
			$(this).addClass('submenu1step04');
				
			/*
				
				$(this).next().show();


			e.stopPropagation();
			
			var $el = $(this).next();
			$('#mainMenu > ul ').slideUp();
			$('#mainMenu  > li > ul ').not($el).slideUpMod(10);
			
			$el.stop(true, true).slideToggle(400);
		});
		$('#mainMenu > ul > li').click(function(e) {
			e.stopImmediatePropagation();
		});
	});
 */


} --%>


function toggleMenu(menuId) {
<%--
	var imgId = 'Img'+menuId;
	var subId = menuId+"_Sub";
	var tdId = menuId+"_Td";
	var leftIconId = "leftIcon"+menuId;

	var menus 		= $('a[group=leftMenuGroup]');
	var menusTd		= $('[group=leftMenuTd]');
	var menusSub	= $('[group=leftMenuSub]');
	var menusImg	= $('[group=leftMenuImg]');
	var menusLink	= $('[group=leftMenu]');


	menusTd.css("color","#666666");
	menusTd.css("font-weight","");

	menusLink.css("color","#777777");

	if (document.getElementById(menuId).style.display == 'block') {

		$('#'+menuId).hide();
		$('#'+imgId).hide();
		$('#'+subId).css("backgroundColor","#FFFFFF");
		$('#'+tdId).css("color","#666666");
		$('#'+tdId).removeClass("menu_open");
		$('#'+tdId).addClass("menu_end");
		$('#'+leftIconId).show();
		$('#'+tdId).css("padding-left","5px");
		$('#'+menuId).css("display","none");

	} else {

		$('#'+menuId).show();
		$('#'+imgId).show();
		$('#'+imgId).src = "<%=imagePath%>/left_menu/left_icon.gif";
		$('#'+subId).css("backgroundColor","#e0e0e0");
		if(curMenuId != ""){
			$('#'+tdId).css("color","#7997df");
		}
		$('#'+tdId).removeClass("menu_end");
		$('#'+tdId).addClass("menu_open");
		$('#'+leftIconId).hide();
		$('#'+tdId).css("padding-left","14px");
		$('#'+menuId).css("display","block");

	}


	curMenuId = menuId;
--%>
}

function linkUrl(url, id, targetObj){
<%--
	var selMenu = $('#'+id);
	var menus 	= $('[group=leftMenu]');
	var menuTd	= $('[group=leftMenuTd]');

	menus.css("color","#888888");
	menus.css("font-weight","");

	menuTd.css("color","#666666");
	menuTd.css("font-weight","");

	selMenu.css("color","#e87e0e");
	//selMenu.css("font-weight","bold");
--%>
	// 다른것들 원상복구
	var siblingsChild = $(targetObj).siblings();
	$(siblingsChild).removeClass('submenu2step01');
	$(siblingsChild).addClass('submenu2step02');
	
	// alert("3 전 "+  $(targetObj).attr('class') );
	if( $(targetObj).attr('class') == 'submenu2step02' ){
		$(targetObj).removeClass('submenu2step02');
		$(targetObj).addClass('submenu2step01');
	
	}else if( $(targetObj).attr('class') == 'submenu2step01' ){
		$(targetObj).removeClass('submenu2step01');
		$(targetObj).addClass('submenu2step02');
	
	}
	// alert("4 전 "+  $(targetObj).attr('class') );

	if (url == "draftPop") {
		// 양식기안
		lfn_formPop();
	} else if (url == "memoDraftPop") {
		// 메모기안
		lfn_memoPop();
	} else {
		parent.frames[parent.frames.length - 1].location.href = url;
	}
}

// 메모기안 오픈
function lfn_memoPop() {
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}

	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}
	height = height - 80;

    var url = "/ep/app/approval/createAppDoc.do?formId=memoDraft";
    var doc = openWindow("popupWin", url, width, height);
}

//양식함  팝업
function lfn_formPop(){

    var top = (screen.availHeight - 250) / 2;
    var left = (screen.availWidth - 400) / 2;

    popupWin = openWindow("lfn_formPopWin", "<%=webUri%>/app/env/ListEnvFormPop.do" , "700", "420" );

}

function goBodyUrl(url){
	parent.frames[parent.frames.length - 1].location.href = url;
}

function goMain(){
	parent.location.href = "<%=webUri%>/app/index.do";
}

//겸직 변경
function changeConcurrent(){
	parent.location.href = "<%=webUri%>/app/login/loginProcessByConcurrent.do?concurrentUserId=" + document.getElementById("Concurrent").value;
}

function goAdmin(){
	parent.location.href = "<%=webUri%>/app/index.do?type=admin";
}

function goManual() {
	var url = "<%=webUri%>/app/ref/manual/start.swf";
	var winGoMenual = openWindow("winGoMenual", url, 1024, 685);
}

//공유게시판
function bbsUrl(boardId) {
	parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/board/BullList.do?boardId=" + boardId;
}

</script>
</head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="wrap" class="luxor-main">
	<div id="body" class="luxor-body" >
		<%--<div class="luxor-inner-body" > --%>
			<div id="menuTree" type="zone" class="luxor-left">
				<div class="subh2_01">전자결재</div>
				<ul id="mainMenu" class="submenu1step">
	<% if(!isExtWeb){
		if (roleCodes.indexOf(roleId00)>=0 || roleCodes.indexOf(roleId01)>=0 || roleCodes.indexOf(roleId10)>=0) {
	%>
      				<table border='0' cellpadding='0' cellspacing='0' style="position:absolute;z-index:10;top:45;left:180;cursor:pointer;">
						<tr>
						<td><img src='<%=imagePath%>/bu2_left.gif' alt=''></td>
						<td background='<%=imagePath%>/bu2_bg.gif' class='text_center' style='white-space:nowrap;padding:0 0 1 0;'>
							<a href="<%=webUri%>/app/index.do?type=admin" style='width:100%;' target="_parent" class="gr" title="<spring:message code='app.top.menu.10'/>">
								<span style="FONT-FAMILY:Dotum,Gulim,Arial;font-size:8pt;color:#888888;"><spring:message code='app.top.menu.10'/></span>
							</a>
						</td>
							<td><img src='<%=imagePath%>/bu2_right.gif' alt='' width='8' height='20'></td>
						</tr>
					</table>
	<%		}
		}
	%>


	<% if(!isExtWeb){  %>
		<% if(pMap.get(boxCode101) != null || pMap.get(boxCode102) != null){%>

					<li class="submenu1step02" name="leftMenuFirst">
						<spring:message code="list.listBox.title.menuDraft"/>
					</li>
						<ul class="submenu2step">
			<% if("Y".equals(draftYn)) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('draftPop','linkMenu_boxCode100', this);">
								<spring:message code="list.list.title.newFormDraft"/>
							</li>
			<% } %>
			<% // 메모기안 시작 %>
			<% if("Y".equals(draftYn) && isHTMLEditorSet) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('memoDraftPop','linkMenu_boxCode099', this);">
								<spring:message code="list.list.title.newMemoDraft"/>
							</li>
			<% } %>
			<% // 메모기안 종료 %>
			
			<% if("Y".equals((String)pMap.get(boxCode101)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/draft/ListTempApprovalBox.do','linkMenu_<%=boxCode101%>', this);">
								<%=pMap.get(boxCode101)[2]%>
							</li>
			<% } %>
	                <%--
	                <% if("Y".equals((String)pMap.get(boxCode102)[1])) {  %>
						<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/draft/ListBizDraftBox.do','linkMenu_<%=boxCode102%>', this);">
							5<%=pMap.get(boxCode102)[2]%>
						</li>
	                <% } %>
					 --%>
						</ul>
		<% } %>
		<% if(pMap.get(boxCode103) != null || pMap.get(boxCode104) != null || (pMap.get(boxCode123) != null && "Y".equals((String)pMap.get("isAssistantChargerYn")[0]) )){%>
					<li class="submenu1step02" onclick="javascript:toggleMenu('Approval');" name="leftMenuFirst">
						전자결재
					</li>
						<ul class="submenu2step">
			<% if("Y".equals((String)pMap.get(boxCode103)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/approval/ListApprovalWaitBox.do','linkMenu_<%=boxCode103%>', this);">
								<%=pMap.get(boxCode103)[2]%>
							</li>
			<% } %>
			<% if("Y".equals((String)pMap.get(boxCode104)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/approval/ListProgressDocBox.do','linkMenu_<%=boxCode104%>', this);">
								<%=pMap.get(boxCode104)[2]%>
							</li>
			<% } %>
			<%
			if(pMap.get(boxCode123) != null){
				if("Y".equals((String)pMap.get(boxCode123)[1]) && "Y".equals((String)pMap.get("isAssistantChargerYn")[0]) ) {
			%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/approval/ListAssistantDocBox.do','linkMenu_<%=boxCode123%>', this);">
								<%=pMap.get(boxCode123)[2]%>
							</li>
			<%
				}
			}
			%>
			<% if(pMap.get(boxCode109) != null || pMap.get(boxCode110) != null || pMap.get(boxCode111) != null || pMap.get(boxCode112) != null ||
			pMap.get(boxCode113) != null || pMap.get(boxCode124) != null || pMap.get(boxCode114) != null || pMap.get(boxCode115) != null ||
			pMap.get(boxCode116) != null || pMap.get(boxCode117) != null || pMap.get(boxCode118) != null){%>
				<% if(!isExtWeb){  %>
					<% if("Y".equals((String)pMap.get(boxCode110)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/complete/ListApprovalCompleteBox.do','linkMenu_<%=boxCode110%>', this);">
								<%=pMap.get(boxCode110)[2]%>
							</li>
					<% } %>
				<% } %>
				<% if("Y".equals((String)pMap.get(boxCode109)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/complete/ListDraftBox.do','linkMenu_<%=boxCode109%>', this);">
								<%=pMap.get(boxCode109)[2]%>
							</li>
				<% } %>
				<% if(!isExtWeb){  %>
					<% if("Y".equals((String)pMap.get(boxCode113)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/complete/ListRearBox.do','linkMenu_<%=boxCode113%>', this);">
								<%=pMap.get(boxCode113)[2]%>
							</li>
					<% } %>
				<% } %>
			<% } %>
						</ul>
		<% } %>
	<% } %>
			
	<% if(!isExtWeb){  %>
		<% if(pMap.get(boxCode105) != null || pMap.get(boxCode106) != null){%>
					<li class="submenu1step02" onclick="javascript:toggleMenu('Send');" name="leftMenuFirst">
						문서유통
					</li>
						<ul class="submenu2step">
			<% if("Y".equals((String)pMap.get(boxCode105)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/send/ListSendWaitBox.do','linkMenu_<%=boxCode105%>', this);">
								<%=pMap.get(boxCode105)[2]%>
							</li>
			<% } %>
		                
			<% if((pMap.get(boxCode107) != null || pMap.get(boxCode108) != null || pMap.get(boxCode119) != null) && ( "Y".equals((String)pMap.get(roleId12)[0]) ||
			 "Y".equals((String)pMap.get(roleId11)[0]) || "2".equals((String)pMap.get("receiveAuthYn")[0]) ) ){%>
				<% if("Y".equals((String)pMap.get(boxCode108)[1]) && ("Y".equals((String)pMap.get(roleId11)[0]) || "2".equals((String)pMap.get("receiveAuthYn")[0]) )  ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/receive/ListReceiveWaitBox.do','linkMenu_<%=boxCode108%>', this);">
								<%=pMap.get(boxCode108)[2]%>
							</li>
				<% } %>
			<% } %> 
			<% if(pMap.get(boxCode109) != null || pMap.get(boxCode110) != null || pMap.get(boxCode111) != null || pMap.get(boxCode112) != null ||
			pMap.get(boxCode113) != null || pMap.get(boxCode124) != null || pMap.get(boxCode114) != null || pMap.get(boxCode115) != null ||
			pMap.get(boxCode116) != null || pMap.get(boxCode117) != null || pMap.get(boxCode118) != null){%>
				<% if(!isExtWeb){  %>
					<% if("Y".equals((String)pMap.get(boxCode111)[1])) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/complete/ListReceiveCompleteBox.do','linkMenu_<%=boxCode111%>', this);">
								<%=pMap.get(boxCode111)[2]%>
							</li>
					<% } %>
				<% } %>  
			<% } %>             
			<% if("Y".equals((String)pMap.get(boxCode106)[1]) && ( ("Y".equals((String)pMap.get(roleId15)[0]) && "Y".equals((String)pMap.get(boxCode205)[1])) ||
			("Y".equals((String)pMap.get(roleId16)[0]) && "Y".equals((String)pMap.get(boxCode204)[1])) ) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/send/ListSendJudgeBox.do','linkMenu_<%=boxCode106%>', this);">
								<%=pMap.get(boxCode106)[2]%>
							</li>
			<% } %>
		                
			<% if(!isExtWeb){  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/regist/ListDocRegist.do','linkMenu_<%=boxCode201%>', this);">
								등록대장
							</li>
			<% } %>
		                </ul>
		<% } %>
	<% } %>
			
	<% if(!isExtWeb){ // 감사문서 %>
		<% if(pMap.get(boxCode114) != null || pMap.get(boxCode206) != null || pMap.get(boxCode207) != null || pMap.get(boxCode208) != null ){%>
			<% if ( ("Y".equals((String)pMap.get(boxCode114)[1]) && "Y".equals((String)pMap.get("isExamAuditDeptYn")[0]) )
			|| ("Y".equals((String)pMap.get(boxCode206)[1]) && "Y".equals((String)pMap.get(roleId25)[0]) )
			|| ("Y".equals((String)pMap.get(boxCode207)[1]) && ("Y".equals((String)pMap.get("isExamDept")[0]) || "Y".equals((String)pMap.get(roleId25)[0]) ) )
			|| ("Y".equals((String)pMap.get(boxCode208)[1]) && "Y".equals((String)pMap.get(roleId15)[0]) ) ) {
			%>
					<li class="submenu1step02" onclick="javascript:toggleMenu('Inspect');" name="leftMenuFirst">
						<spring:message code="list.listBox.title.menuInspect"/>
					</li>
						<ul class="submenu2step">
				<% if("Y".equals((String)pMap.get(boxCode114)[1]) && "Y".equals((String)pMap.get("isExamAuditDeptYn")[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/complete/ListExamReadingBox.do','linkMenu_<%=boxCode114%>', this);">
								<%=pMap.get(boxCode114)[2]%>
							</li>
				<% } %>
				<% if("Y".equals((String)pMap.get(boxCode206)[1]) && "Y".equals((String)pMap.get(roleId25)[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/regist/ListDailyAuditRegist.do','linkMenu_<%=boxCode206%>', this);">
								<%=pMap.get(boxCode206)[2]%>
							</li>
				<% } %>
				<% if("Y".equals((String)pMap.get(boxCode207)[1]) && ("Y".equals((String)pMap.get("isExamDept")[0]) || "Y".equals((String)pMap.get(roleId25)[0]) ) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/regist/ListDailyAudit.do','linkMenu_<%=boxCode207%>', this);">
								<%=pMap.get(boxCode207)[2]%>
							</li>
				<% } %>
				<% if("Y".equals((String)pMap.get(boxCode208)[1]) && "Y".equals((String)pMap.get(roleId15)[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/regist/ListAuditSealRegist.do','linkMenu_<%=boxCode208%>', this);">
								<%=pMap.get(boxCode208)[2]%>
							</li>
				<% } %>
		                </ul>
			<% } %>
		<% } %>
	<% } %>
	<% if(!isExtWeb){ // 공람게시 %>
		<%
		if ( !"0".equals(opt316) ) { // 공람게시 사용안함이 아니면.
		%>
					<li class="submenu1step02" onclick="javascript:toggleMenu('PubRead');" name="leftMenuFirst">
						<spring:message code="common.mainmenu.publicpost"/>
					</li>
						<ul class="submenu2step">
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS002','DRS002', this);">
								<spring:message code="list.etc.left.subDept"/>
							</li>
			<%
			if ("1".equals(opt314)) {
			%>
				<% if(!"".equals(headOfficeId)) {   // jth8172 2012 신결재 TF%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS003','DRS003', this);">
								<spring:message code="list.etc.left.subHQ"/>
							</li>
				<% } %>
				<% if(!"".equals(institutionId)) {   // jth8172 2012 신결재 TF%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS004','DRS004', this);">
								<spring:message code="list.etc.left.subInstitution"/>
							</li>
				<% } %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/list/etc/ListDisplayNotice.do?readRange=DRS005','DRS005', this);">
								<spring:message code="list.etc.left.subCompany"/>
							</li>
			<%
			}
			%>
						</ul>
		<% } %>
	<% } %>
	<% if(!isExtWeb){ // 환결설정. %>
					<li class="submenu1step02" onclick="javascript:toggleMenu('Env');" name="leftMenuFirst">
						<spring:message code="list.listBox.title.menuEnv"/>
					</li>
						<ul class="submenu2step">
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/listEnvLineGroup.do','linkMenu_boxCode997', this);">
								<spring:message code="list.list.title.listEnvLineGroup"/>
							</li>
		                 <% //if("Y".equals((String)pMap.get(roleId11)[0]) || "Y".equals((String)pMap.get(roleId12)[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/charge/listEnvRecvGroup.do','linkMenu_boxCode998', this);">
								<spring:message code="list.list.title.listEnvRecvGroup"/>
							</li>
		                <% //} %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/listEnvPubViewGroup.do','linkMenu_boxCode990', this);">
								<spring:message code="env.option.menu.sub.39"/>
							</li>
		<% if( "Y".equals(opt364) && ("Y".equals((String)pMap.get(roleId11)[0]) || "Y".equals((String)pMap.get(roleId12)[0])) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/charge/selectEnvSenderTitle.do','linkMenu_boxCode996', this);">
								<spring:message code="list.list.title.sendNameManage"/>
							</li>
		<% } %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/envEmptyInfo.do','linkMenu_boxCode999', this);">
								<spring:message code="env.option.menu.sub.19"/>
							</li>
		<% if("Y".equals((String)pMap.get("envFormRegistYn")[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/form/charge/ListEnvFormMain.do','linkMenu_boxCode992', this);">
								<spring:message code="list.list.title.formManage"/>
							</li>
		<% } %>
		<% if("Y".equals((String)pMap.get(roleId12)[0]) ) {
			//문서과문서책임자는 기관의 상하부 캠페인,로고,심볼을 관리한다.   // jth8172 2012 신결재 TF
			//문서과문서책임자는 문서등록대장 공유 부서를 관리한다.
		%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/admincharge/selectOptionCLS.do','linkMenu_boxCode992_1', this);">
								<spring:message code="env.option.menu.sub.12"/>
							</li>
			<% if(bOption382) {%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/shareDeptList.do','linkMenu_boxCode992_2', this);">
								<spring:message code="env.option.menu.sub.64"/>
							</li>
			<% } // bOption382 end%>
		<% } // roleId12 end%>
		<% if("Y".equals((String)pMap.get(roleId11)[0]) || "Y".equals((String)pMap.get(roleId12)[0]) ) {  %>
			<% if("Y".equals(opt423)) { // 편철 사용 여부. %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/bind/unit/simple/list.do','linkMenu_boxCode1994', this);">
								<spring:message code="env.option.menu.sub.21"/>
							</li>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/bind/list.do','linkMenu_boxCode995', this);">
								<spring:message code="list.list.title.bindManage"/>
							</li>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/bind/sharedList.do','linkMenu_boxCode995_1', this);">
								<spring:message code="list.list.title.shareBindManage"/>
							</li>
			<% } %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/charge/selectEnvDeptInfo.do','linkMenu_boxCode994', this);">
								<spring:message code="list.list.title.listEnvDeptInfo"/>
							</li>
		<% } %>
		<% if("Y".equals((String)pMap.get("isOfficerDeptAddYn")[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/user/selectOfficerForDept.do','linkMenu_boxCode989', this);">
								<spring:message code="env.option.menu.sub.42"/>
							</li>
		<% } %>
		<% if("Y".equals((String)pMap.get("isAuditDeptAddYn")[0]) ) {  %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/user/selectAuditorForDept.do','linkMenu_boxCode991', this);">
								<spring:message code="env.option.menu.sub.23"/>
							</li>
		<% } %>
		<% if("Y".equals((String)pMap.get(roleId15)[0]) || "Y".equals((String)pMap.get(roleId16)[0]) ) {  %>
			<%
				String OrgImageMangeMsg = "";
			
				if("Y".equals((String)pMap.get(roleId15)[0])) { // 기관날인관리자
					OrgImageMangeMsg = messageSource.getMessage("env.option.menu.sub.43" , null, langType);;
				}else if("Y".equals((String)pMap.get(roleId16)[0])) { // 서명인날인관리자
					OrgImageMangeMsg = messageSource.getMessage("env.option.menu.sub.44" , null, langType);;
				}
			%>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/listOrgImage.do','linkMenu_boxCode988', this);">
								<%=OrgImageMangeMsg%>
							</li>
		<% } %>
							<li class="submenu2step02" onclick="javascript:linkUrl('<%=webUri%>/app/env/selectEnvPersonalInfo.do','linkMenu_boxCode993', this);">
								<spring:message code="list.list.title.listEnvPersonalInfo"/>
							</li>
						</ul>
	<% } %>

		      	<%-- <tr>
		              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                  <td class="menu_end" id="Env_Td" onclick="javascript:goManual();" group="leftMenuTd"><img src="<%=imagePath%>/left_menu/icon_01.gif" id="leftIconEnv"> <spring:message code="list.list.title.appManual"/></td>
		                  <td width="20" id="Env_Sub" style="background-color:#FFFFFF" group="leftMenuSub"></td>
		                </tr>
		              </table></td>
		        </tr>
		        <tr>
		              <td class="menu_line_01"></td>
		        </tr>
		      	<tr>
		              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                  <td class="menu_end" id="Env_Td" onclick="javascript:bbsUrl('BOARD_COMMON_SHARE');" group="leftMenuTd"><img src="<%=imagePath%>/left_menu/icon_01.gif" id="leftIconEnv"> <spring:message code="board.msg.menu"/></td>
		                  <td width="20" id="linkMenu_bbs1" style="background-color:#FFFFFF" group="leftMenuSub"></td>
		                </tr>
		              </table></td>
		        <tr>
		              <td class="menu_line_01"></td>
		        </tr>	 --%>


		      <% if(concurrentList.size()>1) { %>
					<li class="submenu1step02">
						<spring:message code="app.top.menu.12"/><br />
									<select id="Concurrent" name="Concurrent" class="select_9pt" style="width:100%;" onChange="javascript:changeConcurrent();">
							<%
									for(int i=0; i<concurrentList.size(); i++) {
									    UserVO userVO = concurrentList.get(i);
									    String displayPosition = CommonUtil.nullTrim(userVO.getDisplayPosition());
							%>
										<option value="<%= userVO.getUserUID() %>" <%if(userId.equals(userVO.getUserUID())) {%>selected<%}%> ><%=userVO.getDeptName()%>[<%=userVO.getDisplayPosition()%>]</option>
							<%
									 }
							%>
									</select>
					</li>
	         <% } %>

				</ul>

			</div>
		<%--</div> --%>
	</div>
</div>


</body>
</html>