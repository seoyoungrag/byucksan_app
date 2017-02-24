<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.HashMap,
				 java.util.Map" 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>

<%
/** 
 *  Class Name  : ListAdminCommon.jsp 
 *  Description : 리스트 메인 공통 javascript 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
response.setHeader("pragma","no-cache");
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");


String portalUrl = AppConfig.getProperty("portal_url", "", "portal");

String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String compId = (String) session.getAttribute("COMP_ID");	// 회사 ID
String useTrayYn = CommonUtil.nullTrim((String) session.getAttribute("USE_TRAY"));	// 트레이 사용여부

String resultDetailSearchYn = CommonUtil.nullTrim(resultSearchVO.getDetailSearchYn());
String resultSearchTypeYn		= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeYn());
String resultSearchTypeValue	= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeValue());
String resultLobCode			= CommonUtil.nullTrim(resultSearchVO.getLobCode());

String noSearchTypeRangeMsg 	= "";
if("Y".equals(resultSearchTypeYn)){
    if("1".equals(resultSearchTypeValue)) {
		noSearchTypeRangeMsg = messageSource.getMessage("list.list.msg.noSearchTypeYearRangeSelected" , null, langType);
    }else{
		noSearchTypeRangeMsg = messageSource.getMessage("list.list.msg.noSearchTypePeriodRangeSelected" , null, langType);
    }
}

IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
OPT426 = envOptionAPIService.selectOptionText(compId, OPT426);
String maxPageSize = OPT426;
String excelSaveMsg = messageSource.getMessage("list.list.msg.alertSaveMaxPageSize",null, langType).replace("%s",maxPageSize);
String printMsg = messageSource.getMessage("list.list.msg.alertPrintMaxPageSize",null, langType).replace("%s",maxPageSize);

//상세검색 중 결재자롤 사용여부 확인
Map<String,String> appRoleMap = (HashMap<String,String>) request.getAttribute("appRoleMap");
String opt003GYn = CommonUtil.nullTrim(appRoleMap.get("opt003G"));
String opt009GYn = CommonUtil.nullTrim(appRoleMap.get("opt009G"));
String opt019GYn = CommonUtil.nullTrim(appRoleMap.get("opt019G"));
// 상세검색 중 결재자롤 사용여부 확인 끝

String lob091 = appCode.getProperty("LOB091","LOB091","LOB");
String lob092 = appCode.getProperty("LOB092","LOB092","LOB");
String lob095 = appCode.getProperty("LOB095","LOB095","LOB");
String lob096 = appCode.getProperty("LOB096","LOB096","LOB");
String lob097 = appCode.getProperty("LOB097","LOB097","LOB");
String lob099 = appCode.getProperty("LOB099","LOB099","LOB");

String adm001 = appCode.getProperty("ADM001","ADM001","ADM");
String adm002 = appCode.getProperty("ADM002","ADM002","ADM");

String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

String unRegistDocBtn 		= messageSource.getMessage("list.list.button.unRegistDoc", null, langType);
String reRegistDocBtn 		= messageSource.getMessage("list.list.button.reRegistDoc", null, langType);
String sendImpossibleBtn 	= messageSource.getMessage("list.list.button.sendImpossible", null, langType);

String rangeSearch = "N";
if(!lob099.equals(resultLobCode) && !lob097.equals(resultLobCode) && !lob096.equals(resultLobCode) && !lob095.equals(resultLobCode) && !lob092.equals(resultLobCode) && !lob091.equals(resultLobCode) && !adm001.equals(resultLobCode) && !adm002.equals(resultLobCode)){
    rangeSearch = "Y";
}
String refreshYn = "N";

if(lob091.equals(resultLobCode)){
    refreshYn = "Y";
}
%>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--
$(document).ready(function(){ 	

	<% if("Y".equals(resultDetailSearchYn)) { %>
		//상세검색일 경우 검색창 보여줌.				
	    $("#detailSearchYn").val("Y");
		$("#detailSearchDiv").show();
		$("#detailSearchDiv2").show();
		$("#detailSearchDiv3").show();
		$("#detailSearchDiv4").show();
	<% }else{ %>
		$("#detailSearchYn").val("N");
		$("#detailSearchDiv").hide();
		$("#detailSearchDiv2").hide();
		$("#detailSearchDiv3").hide();
		$("#detailSearchDiv4").hide();
	<% } %>
	
	
	
});

$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });

var appDoc = null;
var NonEleAudit = null;
var Org_ViewUserInfo = null;
var bindWin = null;
var selDept = null;
var printDoc = null;
var bizResultDoc = null;
var docmgrResultDoc = null;
var relayResultDetail = null;
var relayAckResultDetail = null;
var enfRecvList = null;

var curCompId = "<%=compId%>";
var userId = "<%=userId%>";

var curLobCode = "<%=resultLobCode%>";

var curDocId = "";
var curElecYn = "";

var chkAll = 0;

function check_AllDoc() {
    if(!document.formList.docId) return;
    if(chkAll==0){
        chkAll = 1;
        if(document.formList.docId.length > 1){
	        for (var i=0; i < document.formList.docId.length; i++) document.formList.docId[i].checked = true;
		}else{
			document.formList.docId.checked = true;
		}
    }else{
        chkAll = 0;
        if(document.formList.docId.length > 1){
	        for (var i=0; i < document.formList.docId.length; i++) document.formList.docId[i].checked = false;      
		}else{
			document.formList.docId.checked = false;
		}
    }
}

//전체선택
function check_All() {
    if(!document.formList.stdId) return;
    if(chkAll==0){
        chkAll = 1;
        if(document.formList.stdId.length > 1){
	        for (var i=0; i < document.formList.stdId.length; i++) document.formList.stdId[i].checked = true;
		}else{
			document.formList.stdId.checked = true;
		}
    }else{
        chkAll = 0;
        if(document.formList.stdId.length > 1){
	        for (var i=0; i < document.formList.stdId.length; i++) document.formList.stdId[i].checked = false;      
		}else{
			document.formList.stdId.checked = false;
		}
    }
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
		strUrl = "<%=portalUrl%>/EP/web/user/app/jsp/UM_UsrInfo.jsp?id="+strUserID+"&compid=" + curCompId;
		height = "400"; 
	<%} else { %>
		strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=" + curCompId;
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


//문서창 확인
function isPopOpen(){

	// 문서창이 열려 있으면 확인 후 닫는다.
	if(appDoc != null && appDoc.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			appDoc.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}
//문서창 확인
function isPopNonEleAudit(){
	
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(NonEleAudit != null && NonEleAudit.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			NonEleAudit.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}

//문서창 확인
function isPrintOpen(){

	// 문서창이 열려 있으면 확인 후 닫는다.
	if(printDoc != null && printDoc.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			printDoc.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}

//문서창 확인
function isBizResultDocOpen(){

	// 문서창이 열려 있으면 확인 후 닫는다.
	if(bizResultDoc != null && bizResultDoc.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			bizResultDoc.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}

//문서창 확인
function isDocmgrResultDocOpen(){

	// 문서창이 열려 있으면 확인 후 닫는다.
	if(docmgrResultDoc != null && docmgrResultDoc.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			docmgrResultDoc.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
}

//유통문서 오류이력창 확인
function isRelayResultDetailOpen(){

	// 유통 문서창이 열려 있으면 확인 후 닫는다.
	if(relayResultDetail != null && relayResultDetail.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relayResultDetail.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
}

//유통문서 응답이력창 확인
function isRelayAckResultDetailOpen(){

	// 유통 문서창이 열려 있으면 확인 후 닫는다.
	if(relayResultDetail != null && relayAckResultDetail.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relayAckResultDetail.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
}

//문서창 닫기
function closeAppDoc(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(appDoc != null && appDoc.closed == false) {
		appDoc.close();		
		
	}

}

//문서 전체보기
function selectAppDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}
}

function openAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop) {
	
	if(isPop){
		
		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 830;
		if(transferYn == "Y"){
			if(curCompId == "003") {
				width = 730;
			} else if(curCompId == "004") {
				width = 1000;
			}
		}
		
		if (screen.availWidth < width) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var linkUrl;		
		linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
			
		appDoc = openWindow("selectAppDocWin", linkUrl , width, height,"no","get"); 
	}

}

function selectOnlyAppDoc(docId, lobCode, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openOnlyAppDoc(docId, lobCode, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openOnlyAppDoc(docId, lobCode,  elecYn, preNextYn, true); }, 100);
	}
}

function openOnlyAppDoc(docId, lobCode, elecYn, preNextYn, isPop) {

	if(isPop){
		
		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 830;
		
		if (screen.availWidth < width) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var linkUrl;		
		linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
			
		appDoc = openWindow("selectAppDocWin", linkUrl , width, height,"no","get"); 
	}

}

function selectEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}
}

function openEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop) {

	if(isPop){
		
		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 830;
		if(transferYn == "Y"){
			if(curCompId == "003") {
				width = 730;
			} else if(curCompId == "004") {
				width = 1000;
			}
		}
		
		if (screen.availWidth < width) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var linkUrl;
		
		linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode="+lobCode;
			
			
		appDoc = openWindow("selectEnfDocWin", linkUrl , width, height);
	}

}

function selectNonAppDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}
}

function openNonAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop) {

	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
		
	    var url = "<%=webUri%>/app/approval/selectProNonElecDoc.do";
	    url += "?docId="+docId+"&lobCode="+lobCode;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }
	
	    var height = 650;
	    if (screen.availHeight < 650) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    appDoc = openWindow("selectNonAppDocWin", url , width, height, "yes");
	}

}

function selectNonEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn) {

	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}
}

function openNonEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop) {

	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
			
		var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
	    url += "?docId="+docId+"&lobCode="+lobCode;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }
	
	    var height = 650;
	    if (screen.availHeight < 650) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    
	    appDoc = openWindow("selectNonEnfDocWin", url , width, height, "yes");
	}
  
}

function selectCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, receiverOrder) {	
	var isPop = false;
		
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, isPop, receiverOrder);
	}else{
		closeAppDoc();
		setTimeout(function() { openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, true, receiverOrder); }, 100);
	}

}

function openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, isPop, receiverOrder) {
	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 830;
		if(transferYn == "Y"){
			if(curCompId == "003") {
				width = 730;
			} else if(curCompId == "004") {
				width = 1000;
			}
		}
		
		if (screen.availWidth < width) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var linkUrl;
		linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?compId="+compId+"&docId="+docId+"&lobCode="+lobCode;	
		if(receiverOrder != null && receiverOrder != "") linkUrl += "&receiverOrder="+receiverOrder;
		appDoc = openWindow("selectSenderAppDocWin", linkUrl , width, height); 
		
	}

}

//연계 목록 상세
function selectBizResultDoc(docId) {
	var isPop = isBizResultDocOpen();

	if(isPop){
		
	    var url = "<%=webUri%>/app/list/admin/ListAdminBizResultDoc.do";
	    url += "?docId="+docId;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }
	
	    var height = 610;
	    if (screen.availHeight < 610) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    bizResultDoc = openWindow("bizResultDoc", url , width, height, "yes");
	}

}

//문서관리연계이력 상세
function selectDocmgrResultDoc(docId){
	var isPop = isDocmgrResultDocOpen();

	if(isPop){
		
	    var url = "<%=webUri%>/app/list/admin/ListAdminDocmgrResultDoc.do";
	    url += "?docId="+docId;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }
	
	    var height = 610;
	    if (screen.availHeight < 610) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    docmgrResultDoc = openWindow("docmgrResultDoc", url , width, height, "yes");
	}
	
}

//문서유통연계이력 상세
function selectRelayResultDetail(errorId){
	var isPop = isRelayResultDetailOpen();

	if(isPop){
	    var url = "<%=webUri%>/app/list/admin/ListAdminRelayResultDetail.do";
	    url += "?errorId="+errorId;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }

	    var height = 275;
	    if (screen.availHeight < 275) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    relayResultDetail = openWindow("relayResultDetail", url , width, height, "yes");

	}
	
}

//문서유통응답이력 상세
function selectRelayAckResultDetail(docId){
	var isPop = isRelayAckResultDetailOpen();

	if(isPop){
	    var url = "<%=webUri%>/app/list/admin/ListAdminRelayAckResultDetail.do";
	    url += "?docId="+docId;
	    
	    var width = 830;
	    if (screen.availWidth < 830) {
	        width = screen.availWidth;
	    }

	    var height = 275;
	    if (screen.availHeight < 275) {
	        height = screen.availHeight;
	    }
	    
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	    relayAckResultDetail = openWindow("relayAckResultDetail", url , width, height, "yes");

	}
	
}

function changePage(p) {
	$("#cPage").val(p);
	$("#pagingList").submit();
}

function init(){
	parent.rightWin = 1;
}

// 기본 검색
function goSearch(){
	// 화면블럭지정
	screenBlock();
	
	var sForm = document.listSearch;

	if(sForm.searchType.value == "searchSaveDate" || sForm.searchType.value == "searchBizDate" || sForm.searchType.value == "searchDraftDate" || sForm.searchType.value == "searchCompleteDate"){
		// 날짜검색
		 if(bCheckFromToInputValue($('#startDateId'),$('#endDateId'),"<spring:message code='list.list.msg.searchDateError'/>")){
			 
         }else {
        	 screenUnblock();
             return;
         }
		
		
	}else{
		
		if(bCheckFromToInputValue($('#startDateId'),$('#endDateId'),"<spring:message code='list.list.msg.searchDateError'/>")){
			 
        }else {
        	screenUnblock();
            return;
        }
        
		if(sForm.searchType.value == "" && sForm.searchWord.value != ""){
			alert("<spring:message code='list.list.msg.noSearchTypeSelected'/>");
			screenUnblock();
			sForm.searchType.focus();
			return;
		}
		/*
		if(sForm.searchType.value != "" && sForm.searchWord.value == "" ){
			alert("<spring:message code='list.list.msg.noSearchWord'/>");
			sForm.searchWord.focus();
			return;
		}*/
	}

	if(sForm.startDocNum.value != "" || sForm.endDocNum.value != "" ){
		if( sForm.startDocNum.value == ""){
			alert("<spring:message code='list.list.msg.noStartDocNum'/>");
			screenUnblock();
			sForm.startDocNum.focus();
			return;
		}
		if(sForm.endDocNum.value == ""){
			alert("<spring:message code='list.list.msg.noEndDocNum'/>");
			screenUnblock();
			sForm.endDocNum.focus();
			return;
		}
		
		if(isNaN(sForm.startDocNum.value)){
			alert("<spring:message code='list.list.msg.insertOnlyNumber'/>");
			screenUnblock();
			sForm.startDocNum.focus();
			return;
		}

		if(isNaN(sForm.endDocNum.value)){
			alert("<spring:message code='list.list.msg.insertOnlyNumber'/>");
			screenUnblock();
			sForm.endDocNum.focus();
			return;
		}
		
		
	}
	
	if(sForm.searchApprovalName.value != "" || 
			(sForm.searchApprTypeApproval.checked == true 
			 || sForm.searchApprTypeExam.checked == true 
			<% if("Y".equals(opt003GYn)) { %>
			 || sForm.searchApprTypeCoop.checked == true
			<% } %>
			<% if("Y".equals(opt009GYn)) { %>
			|| sForm.searchApprTypeAudit.checked == true
			<% } %>
			 || sForm.searchApprTypeDraft.checked == true
			<%  if("Y".equals(opt019GYn)) { %>
			 || sForm.searchApprTypePreDis.checked == true
			<% } %>	
			 || sForm.searchApprTypeResponse.checked == true 
			)
		){
			if(sForm.searchApprovalName.value == ""){
				alert("<spring:message code='list.list.msg.noSearhTypeApprovalName'/>");
				screenUnblock();
				sForm.searchApprovalName.focus();
				return;
			}

			if(sForm.searchApprTypeApproval.checked == false 
					 && sForm.searchApprTypeExam.checked == false 
					 <% if("Y".equals(opt003GYn)) { %>
					 && sForm.searchApprTypeCoop.checked == false
					 <% } %>
					 <% if("Y".equals(opt009GYn)) { %>
					 && sForm.searchApprTypeAudit.checked == false
					 <% } %>
					 && sForm.searchApprTypeDraft.checked == false 
					 <%  if("Y".equals(opt019GYn)) { %>
					 && sForm.searchApprTypePreDis.checked == false
					 <% } %>	
					 && sForm.searchApprTypeResponse.checked == false 
			){
				alert("<spring:message code='list.list.msg.noSearchTypeAppr'/>");
				screenUnblock();
				return;
				
			}
		}

	<%if("Y".equals(rangeSearch) ) { %>
		//검색 시 저장된 날짜와 지정한 날짜가 다를 경우 년도/회기별 검색이 되어 있으면 해당값 초기화한다.
		if( ((sForm.startDate.value != document.pagingList.startDate.value) || (sForm.endDate.value != document.pagingList.endDate.value))
		  	&& sForm.selRange.value != "" ){
	
			$("#selRange").val("");
			$("#searchCurRange").val("");			
		}
	<% } %>

	//문서번호 검색
	if(sForm.searchType.value == "searchDocNum" && sForm.searchWord.value != ""){
		var searchDocNum = sForm.searchWord.value;
		
		if(searchDocNum.indexOf("-") > -1){
			if(isNaN(searchDocNum.substring(searchDocNum.lastIndexOf("-")+1,searchDocNum.length))){
				alert("<spring:message code='list.list.msg.validate.isDocNumber.onlyNum'/>");
				screenUnblock();
				return;
			}

			if(searchDocNum.substring(searchDocNum.lastIndexOf("-")+1,searchDocNum.length) == ""){
				alert("<spring:message code='list.list.msg.validate.isDocNumber.formatDocNum'/>");
				screenUnblock();
				return;
			}
		
			$("#searchDeptCategory").val(searchDocNum.substring(0,searchDocNum.lastIndexOf("-")));
			$("#searchSerialNumber").val(searchDocNum.substring(searchDocNum.lastIndexOf("-")+1,searchDocNum.length));
		}else{
			alert("<spring:message code='list.list.msg.validate.isDocNumber.formatDocNum'/>");
			screenUnblock();
			return;
		}

		
	}else{
		$("#searchDeptCategory").val("");
		$("#searchSerialNumber").val("");
	}

	
	$("#ListFormcPage").val("1");
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
}

// 목록 refresh
function listRefresh(){
/*
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
*/
}

//목록 refresh - 강제 호출
function listRefreshCompulsion(){
/*
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
*/
}
// 목록 refresh - 목록에서 바로 호출
function listRefreshFromList(){	
	// 화면블럭지정
	screenBlock();
	
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
}

//비전자 직인 상세보기
function selectNonSealDoc(stampId,lobCode, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonSealDoc(stampId, lobCode, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonSealDoc(stampId, lobCode, elecYn, preNextYn, true); }, 100);
	}
}

function openNonSealDoc(stampId,lobCode, elecYn, preNextYn, isPop) {

	if(isPop){

		curElecYn = elecYn;
		curDocId = stampId;
		
		var url = "<%=webUri%>/app/list/seal/insertNonEleStampSeal.do";
	    url += "?stampId="+stampId+"&lobCode="+lobCode;
	    
	    var width = 700;   
	    if (screen.availWidth < 700) {
	        width = screen.availWidth;
	    }   
	    var height = 360;
		if (screen.availHeight < 360) {
			height = screen.availHeight;
		}
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "top="+top+",left="+left+",width="+width+",height="+height+",menubar=no,scrollbars=yes,status=yes";

	    appDoc = openWindow("selectNonSealDocWin", url , width, height, "yes");
	}
    
}

//날인대장 삭제(비전자만 가능)
function deleteSealDoc() {
	
	if($("input#stampId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noDeleteAuthDoc'/>");
			return;	
		}else{
			if(confirm("<spring:message code='list.list.msg.delDocConfirm'/>")){
				var param = $("[listFormChk='Y']:checked").serialize();

				var url = "<%=webUri%>/app/list/seal/deleteStampList.do";
				
				<% if("LOL094".equals(resultLobCode)){ %>
					url = "<%=webUri%>/app/list/seal/deleteStampSealList.do";
				<% } %>
			
				$.post(url, param , function(data){
			        alert(data.message);
	
			        listRefreshCompulsion();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.docDelete'/>");
					
				});
			}
			
			
			
		}
	}
}

//날인대장 수정
function modifySealDoc(){
	
	if($("input#stampId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noAuthModifySealDoc'/>");
			return;	
			
		}else{ 

			if($("[listFormChk='Y']:checked").length > 1){
				alert("<spring:message code='list.list.msg.selDocOnlyOne'/>");
				return;
			}else{
				var param = $("[listFormChk='Y']:checked").serialize();
				
				var url = "<%=webUri%>/app/list/seal/updateNonEleStampSeal.do";
			    url += "?lobCode=<%=resultLobCode%>";
				url += "&"+param;
			    
			    var width = 700;   
			    if (screen.availWidth < 700) {
			        width = screen.availWidth;
			    }
			    var height = 390;
				if (screen.availHeight < 390) {
					height = screen.availHeight;
				}
			    var top = (screen.availHeight - height) / 2;
			    var left = (screen.availWidth - width) / 2; 
			    var option = "top="+top+",left="+left+",width="+width+",height="+height+",menubar=no,scrollbars=1,status=yes";
	
			    NonEleStampSeal = openWindow("modifyStampSealDocWin", url , width, height, "yes");
			}
		}
	}
}

//비전자 일상감사일지 상세보기
function selectDailyAuditDoc(docId, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openDailyAuditDoc(docId, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openDailyAuditDoc(docId, elecYn, preNextYn, true); }, 100);
	}
}

function openDailyAuditDoc(docId, elecYn, preNextYn, isPop) {
	
	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
		
		var url = "<%=webUri%>/app/list/audit/insertNonEleAudit.do";
		url += "?docId="+docId;
	    
	    var width = 600;   
	    if (screen.availWidth < 600) {
	        width = screen.availWidth;
	    }   
	    var height = 430;
	    if (screen.availHeight < 430) {	
		    height = screen.availHeight;	
		}
	    var top = (screen.availHeight - height) / 2;
	    var left = (screen.availWidth - width) / 2; 
	    var option = "top="+top+",left="+left+",width="+width+",height="+height+",menubar=no,scrollbars=no,status=yes";

	    appDoc = openWindow("selectDailyAuditDocWin", url , width, height, "yes");
	}
}

//상세검색
function detailSearch(){
	if($("#detailSearchYn").val() == "N"){
		$("#detailSearchYn").val("Y");
		$("#detailSearchDiv").show();
		$("#detailSearchDiv2").show();
		$("#detailSearchDiv3").show();
		$("#detailSearchDiv4").show();
	}else{
		$("#detailSearchYn").val("N");
		$("#detailSearchDiv").hide();
		$("#detailSearchDiv2").hide();
		$("#detailSearchDiv3").hide();
		$("#detailSearchDiv4").hide();
		$("#startDocNum").val("");
		$("#endDocNum").val("");
		$("#bindingId").val("");
		$("#bindingName").val("");
		$("#searchDetType").val("");
		$("#searchApprovalName").val("");
		$("#searchAppDocYn").val("");
		$("#searchEnfDocYn").val("");
		$(":checkbox").attr("checked",false);
		
	}
		
}

//편철 선택
function selectBind() {
	var top = (screen.availHeight - 450) / 2;
	var left = (screen.availWidth - 430) / 2;
	var option = "width=430,height=450,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";

	bindWin = openWindow("selectBindWin", "<%=webUri%>/app/bind/select.do" , "430", "450");
}

//편철 셋팅
function setBind(bind) {
	if (typeof(bind) == "object") {
		$("#bindingId").val(bind.bindingId);
		$("#bindingName").val(bind.bindingName);
	}
}


//년도/회기 필터
function chRange(range){
	if($("#selRange").val() == ""){
		/*alert("<%=noSearchTypeRangeMsg%>");
		$("#selRange").focus();
		return;
		*/
		$("#ListFormcPage").val("1");
		$("#searchCurRange").val("");
		$("#excelExportYn").val("N");
		$("#pageSizeYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
	}else{
		$("#ListFormcPage").val("1");
		$("#searchCurRange").val(range);
		$("#excelExportYn").val("N");
		$("#pageSizeYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();	
	}
	
}

//부서선택
function selectDept(){
	var width = 500;
    var height = 400;
	var url = "<%=webUri%>/app/common/deptAll.do";

	selDept = openWindow("deptInfoWin", url , width, height);
		
}

//기관선택
function selectInstitution(){
	var width = 500;
    var height = 400;
	var url = "<%=webUri%>/app/common/Institution.do";

	selDept = openWindow("deptInfoWin", url , width, height);
		
}

//부서id 셋팅
function setDeptInfo(obj){
	if (typeof(obj) == "object") {
		
		screenBlock(); 
		
		$("#ListFormcPage").val("1");
		$("#ListFormDeptId").val(obj.orgId);
		$("#ListFormDeptName").val(obj.orgName);
		$("#pageSizeYn").val("N");
		$("#excelExportYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();
	}
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

function setSearchAppDocYn(appDocYn){
	
	if($("#chkAppDocYn:checked").length > 0){
		$("#searchAppDocYn").val("Y");
	}else{
		$("#searchAppDocYn").val("N");
	}
	
	if($("#chkEnfDocYn:checked").length == 0) {
		$("#searchEnfDocYn").val("N");
	}	

	if($("#chkEnfDocYn:checked").length == 0 && $("#chkAppDocYn:checked").length == 0) {
		$("#searchAppDocYn").val("");
		$("#searchEnfDocYn").val("");
	}
}

function setSearchEnfDocYn(enfDocYn){
	
	if($("#chkEnfDocYn:checked").length > 0){
		$("#searchEnfDocYn").val("Y");
	}else{
		$("#searchEnfDocYn").val("N");
	}

	if($("#chkAppDocYn:checked").length == 0) {
		$("#searchAppDocYn").val("N");
	}	

	if($("#chkEnfDocYn:checked").length == 0 && $("#chkAppDocYn:checked").length == 0) {
		$("#searchAppDocYn").val("");
		$("#searchEnfDocYn").val("");
	}
}

//인쇄
function printBox() {

	var linkUrl = "";
	
	if(confirm("<%=printMsg%>")){
		var isPop = isPrintOpen();
		var linkUrl = "";
		
		if(isPop){
			
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
			//var option = 	"width="+width+",height="+height+",top="+top+",left="+left+",menubar=yes,scrollbars=1,status=yes";		
				
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

//엑셀저장
function excelSaveBox() {
	var linkUrl = "";
	
	if(confirm("<%=excelSaveMsg%>")){		

		$("#pageSizeYn").val("N");
		$("#excelExportYn").val("Y");
		$("#ListFormcPage").val("1");	
		$("#listSearch").attr("target", "myframe");
		$("#listSearch").attr("action", "");	
		$("#listSearch").submit();
		
	}
	
}

//이전/다음 문서 호출
function getPreNextDoc(docId,gubun){

	if(docId == "" || gubun == ""){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}
	
	var currentTr = null;
	var preTr  = null;
	var nextTr = null;
	var aElement = null;
	var docInfoYn = true;
	
	currentTr = document.getElementById(docId);

	if(gubun == "pre"){
		if( currentTr != null && typeof(currentTr.previousSibling) === "object" &&  typeof(currentTr.previousSibling) !== "undefined" ){
			preTr = currentTr.previousSibling;
		}else{
			docInfoYn = false;
		}
		
		if(preTr != null && typeof(preTr) === "object" &&  typeof(preTr) !== "undefined" ){
			aElement = document.getElementById("a_" + preTr.id);
			docInfoYn = true;
		}
	}else{
		if( currentTr != null && typeof(currentTr.nextSibling) === "object" &&  typeof(currentTr.nextSibling) !== "undefined" ){
			nextTr = currentTr.nextSibling;
		}else{
			docInfoYn = false;
		}
		
		if(nextTr != null && typeof(nextTr) === "object" &&  typeof(nextTr) !== "undefined" ){			
			aElement = document.getElementById("a_" + nextTr.id);
			docInfoYn = true;
		}
	}
	 
	if(aElement == null) {
		
		if(gubun == "pre" && docInfoYn == true){
			return "<spring:message code='list.list.msg.preNextFirst'/>";
		}else if(gubun == "pre" && docInfoYn ==  false){
			return "<spring:message code='list.list.msg.noDocPreInfo'/>";
		}else if(gubun == "next" && docInfoYn == true){
			return "<spring:message code='list.list.msg.preNextlast'/>";
		}else if(gubun == "next" && docInfoYn == false){
			return "<spring:message code='list.list.msg.noDocNextInfo'/>";
		}
			
	}else{
		aElement.click();
		curDocId = docId;
		curElecYn = aElement.getAttribute("elecYn");
	}
}

// 이전/다음을 위한 정보 초기화
function clearPreNextDoc(){
	curDocId = "";
	curElecYn = "";
}

//숫자체크
function onlyNumber(e, decimal) {
	var key;
	var keychar;

	if (window.event) {
		key = window.event.keyCode;
	} else if (e) {
		key = e.which;
	} else {
		return true;
	}

	keychar = String.fromCharCode(key);

	if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
			|| (key == 27)) {
		return true;
	} else if ((('0123456789').indexOf(keychar) > -1)) {
		return true;
	} else if (decimal && (keychar == '.')) {
		return true;
	} else {
		alert("<spring:message code='list.list.msg.insertOnlyNumber'/>");
		return false;
	}
}

// 이력 삭제
function deleteHistory(){
	var delType = "";

	if(curLobCode == "<%=lob097%>"){
		delType = "1";
	}else if(curLobCode == "<%=lob096%>"){
		delType = "2";
	}else if(curLobCode == "<%=lob095%>"){
		delType = "3";
	}
	
	if($("input#stdId").size() == 0){
		alert("<spring:message code='list.list.msg.noAccessHis'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectHistory'/>");
		return;
	}else{
		
		
		if(confirm("<spring:message code='list.list.msg.delHistoryConfirm'/>")){
			var param = $("[listFormChk]:checked").serialize();
			
			$.post("<%=webUri%>/app/list/etc/deleteAccHis.do?delType="+delType, param , function(data){
		        alert(data.message);

		        listRefreshCompulsion();
		        
		    }, 'json').error(function(data) {
				alert("<spring:message code='list.list.msg.fail.historyDelete'/>");
				
			});
		}
			
		
	}
}

//년도/회기 필터
function chProcType(procType){
	if($("#selProcType").val() == ""){
		
		$("#ListFormcPage").val("1");
		$("#selProcType").val("");
		$("#excelExportYn").val("N");
		$("#pageSizeYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();	
	}else{
		$("#ListFormcPage").val("1");
		$("#selProcType").val(procType);
		$("#excelExportYn").val("N");
		$("#pageSizeYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();	
	}
	
}


//일상감사일지 검색조건 변경
function chThisValue(value){
	var n = $("input:checked").length;
	if(n > 0){
		$("input:checked").attr("checked","");
	}
	if(value == "searchAuditKind"){
		
		$('#searchWordTd').hide();
		$('#searchAuditKindTd').show();
		
	}else{
		$('#searchWordTd').show();
		$('#searchAuditKindTd').hide();
	}
}

// 문서 등록 취소(문서 전체 목록)
function unRegistDoc(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[input#docId]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[listFormChk ='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noUnRegistDoc'/>");
			return;	
		}else{
			var param = $("[listFormChk ='Y']:checked").serialize();
			
			$("#returnDocId").val(param);
			popOpinion("unReigstDocOk","<%=unRegistDocBtn%>","Y");
		}
	}
}


//문서  재등록(문서 전체 목록)
function reRegistDoc(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[input#docId]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[reRegistYn ='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noReRegistDoc'/>");
			return;	
		}else{
			var param = $("[reRegistYn ='Y']:checked").serialize();
			$("#returnDocId").val(param);
			
			popOpinion("reReigstDocOk","<%=reRegistDocBtn%>","Y");				
			
		}
	}
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var top = (screen.availHeight - 250) / 2;
	var left = (screen.availWidth - 500) / 2;
	var height = "height=240,";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=200,";
	<% } %>  
	if(opinionYn=="N") {
		height = "height=140,";
		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=170,";
		<% } %>  
	}	
	popupWin = window.open("", "popupWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
	        "scrollbars=no,resizable=no"); 
	
	$("#comment").val("");
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#appDocForm").attr("target", "popupWin");
	$("#appDocForm").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#appDocForm").submit();

} 

// 등록 취소 완료
function unReigstDocOk(popComment){

	var param = $("#returnDocId").val()+"&reason="+popComment;
	
	$.post("<%=webUri%>/app/approval/admin/unregistAppDoc.do", param , function(data){
        alert(data.message);

        listRefresh();
        
    }, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.unRegistDoc'/>");
		
	});
}

//재등록 완료
function reReigstDocOk(popComment){

	var param = $("#returnDocId").val()+"&reason="+popComment;
	
	$.post("<%=webUri%>/app/approval/admin/registAppDoc.do", param , function(data){
        alert(data.message);

        listRefresh();
        
    }, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.reRegistDoc'/>");
		
	});
}

//첨부파일 위치 변경
function fncMoveAttachDiv(event){
     var evt=window.event || event;

     document.getElementById("attachDiv").style.position="absolute";
     $('#attachDiv').show();
     document.getElementById("attachDiv").style.left=evt.clientX - 100;    //evt.clientX는 이벤트가 발생한 x좌료
     document.getElementById("attachDiv").style.top=evt.clientY + document.body.scrollTop;   //y좌표
	
}

function fncHiddenAttachDiv(){

	$('#attachDiv').hide();

}


// 첨부파일 div
function fncShowAttach(docId, tempYn){
	if(docId == "" || docId == null){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}
	

	$('#attachDiv').show();

	var oRow, oCell1, oCell2, curRow;

	curRow = attachTb.childNodes[0].childNodes.length;

	if(curRow > 1){
		for(var i=0; i < curRow-1; i++){
			attachTb.deleteRow(attachTb.childNodes[0].childNodes.length-1);
		}
	}
	
	$.post("<%=webUri%>/app/appcom/attach/listAttach4Ajax.do", "docId="+docId+"&tempYn="+tempYn, function(data){
		var datalength = data.length;
		for (var loop = 0; loop < datalength; loop ++) {

			
			oRow = attachTb.insertRow();
			

			oCell1 = oRow.insertCell();
			oCell1.bgColor= "#FFFFFF";
			oCell1.className = "ltb_center";			
			oCell1.innerHTML = "<img src='<%=webUri%>/app/ref/image/attach/attach_" + fncGetAttachIcon(data[loop].displayname) + ".gif'>";

			oCell2 = oRow.insertCell();
			oCell2.bgColor= "#FFFFFF";
			oCell2.className = "ltb_left";
<% if (!isExtWeb) { %>	
			oCell2.innerHTML = "<a href=\"#\" onclick=\"javascript:fncGetAttachFile('"+data[loop].fileid+"','"+data[loop].filename+"','"+escapeJavaScript(data[loop].displayname)+"','"+data[loop].docid+"');\">"+data[loop].displayname+"</a>";
<% } else { %>
			oCell2.innerHTML = data[loop].displayname;				
<% } %>			
		    
		}
	}, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.listAttachDivList'/>");
		$("#attachDivUse").val("N");
		$('#attachDiv').hide();
		
	});
	
	$("#attachDivUse").val("Y");
}

//파일 다운로드
function fncGetAttachFile(fileId, fileName, fileDisplayName, docId){
	if(fileId == "" || fileId == null){
		alert("<spring:message code='list.list.msg.noFileId'/>");
		return;
	}
	$("#attachDivUse").val("Y");

	var attach = new Object();
	attach.fileid = fileId;
	attach.filename = fileName;
	attach.displayname = fileDisplayName;
	attach.type = "save";
	attach.gubun = "";
	attach.docid = docId;

	FileManager.download(attach);

	$("#attachDivUse").val("Y");
}

// 파일 확장자 체크
function fncGetAttachIcon(filename){
	var extension = filename.substring(filename.lastIndexOf(".") + 1);
	if (extension != "bc" && extension != "bmp" && extension != "dl" && extension != "doc" && extension != "docx" && extension != "exe" 
		&& extension != "gif" && extension != "gul" && extension != "htm" && extension != "html" && extension != "hwp" 
		&& extension != "ini" && extension != "jpg"	&& extension != "mgr" && extension != "mpg" && extension != "pdf" 
		&& extension != "ppt" && extension != "pptx" && extension != "print" && extension != "report" && extension != "tif" && extension != "txt" 
		&& extension != "wav" && extension != "xls" && extension != "xlsx" && extension != "xls02" && extension != "xml" && extension != "gif") {
		extension = "etc";
	}

	return extension;
}

//발송불가 
function sendImpossibleDoc(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[input#docId]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{	
		if($("#searchDeptId").val() != ""){				
			var param = $("[input#docId]:checked").serialize();
			$("#returnDocId").val(param);
			
			popOpinion("sendImpossibleOk","<%=sendImpossibleBtn%>","Y");
		}else{
			alert("<spring:message code='list.list.msg.validate.isNotDeptInfo'/>");
			return;
		}		
	}
}

// 발송 불가 처리
function sendImpossibleOk(popComment){
	var param = $("#returnDocId").val()+"&reason="+popComment;
	
	$.post("<%=webUri%>/app/approval/admin/sendImpossibleDoc.do", param , function(data){
        alert(data.message);

        listRefreshFromList();
        
    }, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.sendImpossibleDoc'/>");
		
	});
}

//미접수 부서 목록
function selectEnfRecvList(docId){

	var strUrl 	= "<%=webUri%>/app/approval/admin/selectEnfRecvList.do?docId="+docId;
	var width	= "400"; 
	var height 	= "450";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;

	if(enfRecvList != null && enfRecvList.closed == false) {
		enfRecvList.close();
	}
	
	enfRecvList = openWindow("enfRecvList", strUrl , width, height, "yes", "post", "no");
	
}
//-->
</script>
