<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : Common.jsp 
 *  Description : 공통 javascript 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 05 
 *  @version 1.0 
 *  @see
 */ 
%>

<%
response.setHeader("pragma","no-cache");
String opt189 = AppConfig.getProperty("OPT189", "OPT189", "OPT");
String opt190 = AppConfig.getProperty("OPT190", "OPT190", "OPT");

String compId = CommonUtil.nullTrim((String) session.getAttribute("COMP_ID"));	// 회사 ID
String useTrayYn = CommonUtil.nullTrim((String) session.getAttribute("USE_TRAY"));	// 트레이 사용여부

SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
if (resultSearchVO == null) {
    resultSearchVO = new SearchVO();
}
String resultListType = CommonUtil.nullTrim(resultSearchVO.getListType());
String portalUrl = AppConfig.getProperty("portal_url", "", "portal");

String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
OPT426 = envOptionAPIService.selectOptionText(compId, OPT426);
String maxPageSize = OPT426;
String printMsg = messageSource.getMessage("list.list.msg.alertPrintMaxPageSize",null, langType).replace("%s",maxPageSize);
%>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--
$(document).ready(function(){ init(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });

var selDept = null;
var printDoc = null;
var Org_ViewUserInfo = null;

function init(){
	parent.rightWin = 1;
	screenUnblock();
}

function changePage(p) {
	$("#cPage").val(p);
	$("#pagingList").submit();
}

// 기본 검색
function goSearch(){
	// 화면블럭지정
	screenBlock();
	
	var sForm = document.listSearch;
<%			              
	if (opt189.equals(resultListType) || opt190.equals(resultListType)) {
%> 
		if (sForm.searchType.value == "searchDeptName") {
			sForm.searchDeptId.value = "";
			sForm.searchUserId.value = "";
			sForm.searchDeptName.value = sForm.searchWord.value;
			sForm.searchUserName.value = "";
		} else if (sForm.searchType.value == "searchUserName") {
			sForm.searchDeptId.value = "";
			sForm.searchUserId.value = "";
			sForm.searchDeptName.value = "";
			sForm.searchUserName.value = sForm.searchWord.value;
		}
<%
	} else {
%>
	if (!bCheckFromToInputValue($('#startDateId'),$('#endDateId'),"<spring:message code='list.list.msg.searchDateError'/>")) {
    	screenUnblock();
        return;
    }
<%
	}
%>

	$("#ListFormcPage").val("1");
	$("#excelExportYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
}

// 목록 refresh
function listRefresh(){
	$("#excelExportYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
}

function screenBlock() {
    var top = ($(window).height() - 150) / 2;
    var left = ($(window).width() - 350) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

//부서선택
function selectDept(){
	var width = 500;
    var height = 400;
	var url = "<%=webUri%>/app/common/deptAll.do";

	selDept = openWindow("deptInfoWin", url , width, height);
}

//부서id 셋팅
function setDeptInfo(obj){
	if (typeof(obj) == "object") {	
		$("#ListFormcPage").val("1");
		$("#ListFormDeptId").val(obj.orgId);
		$("#ListFormDeptName").val(obj.orgName);
		$("#excelExportYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();
	}
}

function printBox() {
	var linkUrl = "";
	
	if (confirm("<%=printMsg%>")) {
		var isPop = isPrintOpen();
		var linkUrl = "";
		
		if (isPop) {
			var width = 900;
			if (screen.availWidth < 900) {	
			    width = screen.availWidth;
			}
			
			var height = 600;	
			if (screen.availHeight < 600) {	
			    height = screen.availHeight;	
			}
			
			var top = (screen.availHeight - height) / 2;	
			var left = (screen.availWidth - width) / 2; 
			var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=1,status=yes";
				
			printDoc = window.open(linkUrl, "printDoc", option);
			
			$("#pageSizeYn").val("Y");
			$("#excelExportYn").val("N");
			$("#ListFormcPage").val("1");
			$("#listSearch").attr("target", "printDoc");
			$("#listSearch").attr("action", "");
			$("#listSearch").submit();
		}
	}
}

// 문서창 확인
function isPrintOpen() {
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (printDoc != null && printDoc.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			printDoc.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
}

//결재역할별 통계 엑셀 저장
 function excelSave(){
	$("#excelExportYn").val("Y");
	$("#ListFormcPage").val("1");	
	$("#listSearch").attr("target", "myframe");
	$("#listSearch").attr("action", "");	
	$("#listSearch").submit();
}

//사용자 팝업
 function onFindUserInfo(strUserID) {

 	if (strUserID == "" || strUserID == null) {
 		alert("<spring:message code='list.list.msg.noSearchUser'/>");
 		return;
 	}

 	var strUrl = "";
 	var height = "";
 	<%if("Y".equals(useTrayYn)) { %>
 		strUrl = "<%=portalUrl%>/EP/web/user/app/jsp/UM_UsrInfo.jsp?id="+strUserID+"&compid=<%=compId%>";
 		height = "400"; 
 	<%} else { %>
 		strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
 		height = "450";
 	<% } %>

 	var top = (screen.availHeight - 560) / 2;
 	var left = (screen.availWidth - 800) / 2;
 	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

 	if(Org_ViewUserInfo != null && Org_ViewUserInfo.closed == false) {
 		Org_ViewUserInfo.close();
 	}
 	
 	Org_ViewUserInfo = openWindow("Org_ViewUserInfoWin", strUrl , "500", height, "no", "post", "no");
 }
//-->
</script>
