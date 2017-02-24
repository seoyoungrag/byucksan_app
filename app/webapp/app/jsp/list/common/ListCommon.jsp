<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.HashMap,
				 java.util.Map" 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
%>
<%
/** 
 *  Class Name  : ListCommon.jsp 
 *  Description : 리스트 공통 javascript 
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
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
if(resultSearchVO == null){
    resultSearchVO = new SearchVO();
}

String portalUrl = AppConfig.getProperty("portal_url", "", "portal");

String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String compId = (String) session.getAttribute("COMP_ID");	// 회사 ID
String searchElectronDocYn = (String) request.getAttribute("searchElectronDocYn");
String useTrayYn = CommonUtil.nullTrim((String) session.getAttribute("USE_TRAY"));	// 트레이 사용여부

String resultLobCode			= CommonUtil.nullTrim(resultSearchVO.getLobCode());
String resultDetailSearchYn 	= CommonUtil.nullTrim(resultSearchVO.getDetailSearchYn());
String resultSearchTypeYn		= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeYn());
String resultSearchTypeValue	= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeValue());

String noSearchTypeRangeMsg 	= "";
if("Y".equals(resultSearchTypeYn)){
    if("1".equals(resultSearchTypeValue)) {
		noSearchTypeRangeMsg = messageSource.getMessage("list.list.msg.noSearchTypeYearRangeSelected" , null, langType);
    }else{
		noSearchTypeRangeMsg = messageSource.getMessage("list.list.msg.noSearchTypePeriodRangeSelected" , null, langType);
    }
}

String lob001 = appCode.getProperty("LOB001","LOB001","LOB");
String lob002 = appCode.getProperty("LOB002","LOB002","LOB");
String lob003 = appCode.getProperty("LOB003","LOB003","LOB");
String lob004 = appCode.getProperty("LOB004","LOB004","LOB");
String lob005 = appCode.getProperty("LOB005","LOB005","LOB");
String lob006 = appCode.getProperty("LOB006","LOB006","LOB");
String lob007 = appCode.getProperty("LOB007","LOB007","LOB");
String lob008 = appCode.getProperty("LOB008","LOB008","LOB");
String lob009 = appCode.getProperty("LOB009","LOB009","LOB");
String lob010 = appCode.getProperty("LOB010","LOB010","LOB");
String lob012 = appCode.getProperty("LOB012","LOB012","LOB");
String lob013 = appCode.getProperty("LOB013","LOB013","LOB");
String lob024 = appCode.getProperty("LOB024","LOB024","LOB");  // 통보함  // jth8172 2012 신결재 TF
String lob019 = appCode.getProperty("LOB019","LOB019","LOB");
String lob022 = appCode.getProperty("LOB022","LOB022","LOB");
String lol001 = appCode.getProperty("LOL001","LOL001","LOL");
String lol002 = appCode.getProperty("LOL002","LOL002","LOL");
String lol003 = appCode.getProperty("LOL003","LOL003","LOL");
String lol008 = appCode.getProperty("LOL008","LOL008","LOB");
String lol007 = appCode.getProperty("LOL007","LOL007","LOB");

String refreshAuthYn = "N";

if(lob001.equals(resultLobCode) || lob002.equals(resultLobCode) || lob003.equals(resultLobCode) || lob004.equals(resultLobCode) 
		|| lob005.equals(resultLobCode) || lob006.equals(resultLobCode) || lob007.equals(resultLobCode) || lob008.equals(resultLobCode) 
		|| lob012.equals(resultLobCode) || lob013.equals(resultLobCode) || lob024.equals(resultLobCode) || lob019.equals(resultLobCode) 
		|| lol002.equals(resultLobCode) || lol008.equals(resultLobCode) ) {
    refreshAuthYn = "Y";
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
String opt003GYn = appRoleMap == null ? "" : CommonUtil.nullTrim(appRoleMap.get("opt003G"));
String opt009GYn = appRoleMap == null ? "" : CommonUtil.nullTrim(appRoleMap.get("opt009G"));
String opt019GYn = appRoleMap == null ? "" : CommonUtil.nullTrim(appRoleMap.get("opt019G"));
// 상세검색 중 결재자롤 사용여부 확인 끝

String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

String unRegistDocBtn = messageSource.getMessage("list.list.button.unRegistDoc", null, langType);

%>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--
$(document).ready(function(){ 
	// 파일 ActiveX 초기화
	initializeFileManager();

	init(); 
	
	
	$("#attachDiv").mouseleave(function(){
		
		if( $("#attachDivUse").val() == "Y"){
			$("#attachDivUse").val("N");
		}else{
			$('#attachDiv').hide();
		}
		
	});

	$("#nonElecDocRegistDiv").mouseleave(function(){
				
		$('#nonElecDocRegistDiv').hide();		
		
	});

	<% if("Y".equals(resultDetailSearchYn)) { %>
		//상세검색일 경우 검색창 보여줌.				
	    $("#detailSearchYn").val("Y");
		$("#detailSearchDiv").show();
		$("#detailSearchDiv2").show();
		<%if(!lob022.equals(resultLobCode)){ %>
		$("#detailSearchDiv3").show();
		<% } %>
		$("#detailSearchDiv4").show();
	<% }else{ %>
		$("#detailSearchYn").val("N");
		$("#detailSearchDiv").hide();
		$("#detailSearchDiv2").hide();
		<%if(!lob022.equals(resultLobCode)){ %>
		$("#detailSearchDiv3").hide();
		<% } %>
		$("#detailSearchDiv4").hide();
	<% } %>
	
	
});

$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });

var appDoc = null;
var relationDoc = null;
var noElecDoc = null;
var NonEleStampSeal = null;
var NonEleAudit = null;
var bindWin = null;
var printDoc = null;
var readerWin = null;
var selDept = null;


var chkAll = 0;
var curCompId = "<%=compId%>";
var userId = "<%=userId%>";

var Org_ViewUserInfo = null;
var curLobCode = "<%=resultLobCode%>";

var curDocId = "";
var curElecYn = "";
// jkkim added secruty related
var docId = "";
var lobCode = "";
var transferYn = "";
var elecYn = "";
var preNextYn = "";
var publishId = "";

//양식함  팝업
function lfn_formPop(){
	parent.frames["left"].toggleMenu('Draft');
	parent.frames["left"].linkUrl('draftPop','linkMenu_boxCode100');
}

//문서정보조회
function viewDocDetail() {
	var items = getCheckedList();

	if(items.length == 0) {
		alert("상세정보를 조회할 문서를 선택해주세요.");
	} else if(items.length > 1) {
		alert("하나의 문서만 선택해주세요.");
	}else{
		docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/selectDocInfo.do?bodyType=hwp&docId=" + items[0].value+"&securityYn=N", <%=(adminstratorFlag ? 700 : 650)%>, 450);
	}
}

function getCheckedList() {
	if(!document.formList.docId) return;
	
	var result = [];
	var form = document.formList;
	for ( var i = 0; i < form.length; i++) {
		if (form.elements[i].name == 'docId' && form.elements[i].checked) {
			result.push(form.elements[i]);
		}
	}
	return result;
}

function check_All() {
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

	<% if(!"LOB011".equals(resultLobCode) && !"LOB022".equals(resultLobCode)){ %> 
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
	<% } %>
	
	<% if(resultLobCode.indexOf("LOL00") != -1){ %> 
		//등록대장 검색 시 저장된 날짜와 지정한 날짜가 다를 경우 년도/회기별 검색이 되어 있으면 해당값 초기화한다.
		if( ((sForm.startDate.value != document.pagingList.startDate.value) || (sForm.endDate.value != document.pagingList.endDate.value))
		  	&& sForm.selRange.value != "" ){
	
			$("#selRange").val("");
			$("#searchCurRange").val("");			
		}
	<% } %>

	<% // 생산부서 문서 검색시 강제로 접수문서만 검색 하게 수정 KKH
	if("LOB012".equals(resultLobCode) || "LOL001".equals(resultLobCode)){ %>
		if(sForm.searchType.value == "searchSenderDocNumber" && sForm.searchWord.value != ""){
			$("#easyApprSearch").val("N");
			$("#easyEnfSearch").val("N");
			
			$("#searchAppDocYn").val("N");
			$("#searchEnfDocYn").val("Y");
		}
	<% } %> 

	
	
	$("#ListFormcPage").val("1");
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
}

// 목록 refresh
function listRefresh(){	
	<% if("Y".equals(refreshAuthYn)) { %>
		// 화면블럭지정
		screenBlock();

		var str = document.location+'';
    var n = str.indexOf("waitDocId");

    if(str.indexOf("waitDocId") > -1){
      $("#listSearch").attr("action", str.split('?')[0]);
    }else{
      $("#listSearch").attr("action", "");
    }
		
		$("#excelExportYn").val("N");
		$("#pageSizeYn").val("N");
		$("#listSearch").attr("target", "");
		
		$("#listSearch").submit(); 
	<% } %>
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

//목록 refresh - 강제 호출
function listRefreshCompulsion(){
	// 화면블럭지정
	screenBlock();
	
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
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



// 첨부파일 위치 변경
function fncMoveAttachDiv(event){
     var evt=window.event || event;

     document.getElementById("attachDiv").style.position="absolute";
     $('#attachDiv').show();
     document.getElementById("attachDiv").style.left=evt.clientX - 250;    //evt.clientX는 이벤트가 발생한 x좌료
     document.getElementById("attachDiv").style.top=evt.clientY + document.body.scrollTop;   //y좌표
	
}

function fncHiddenAttachDiv(){

	$('#attachDiv').hide();

}


// 첨부파일 div
function fncShowAttach(docId, tempYn, compId){
	if(docId == "" || docId == null){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}
	

	$('#attachDiv').show();

	var oRow, oCell1, oCell2, curRow;

	curRow = attachTb.children[0].children.length;	//리스트에서 첨부파일이 중복되어 보이는 현상으로 childNodes ->children 변경

	if(curRow > 1){
		for(var i=0; i < curRow-1; i++){
			attachTb.deleteRow(attachTb.childNodes[0].childNodes.length-1);
		}
	}

	var param = "";
	if(compId == null){
		param = "docId="+docId+"&tempYn="+tempYn;
	}else{
		param = "compId="+compId+"&docId="+docId+"&tempYn="+tempYn;
	}
	
	$.post("<%=webUri%>/app/appcom/attach/listAttach4Ajax.do", param, function(data){
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

function isRelationPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(relationDoc != null && relationDoc.closed == false) {
		if(confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relationDoc.close();			
			return true;			
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}

// 문서창 닫기
function closeAppDoc(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(appDoc != null && appDoc.closed == false) {
		appDoc.close();		
		
	}
	//20140724 관련문서 닫기 kj.yang
	if(relationDoc != null && relationDoc.closed == false) {
		relationDoc.close();			
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

//보안문서 열기 결과
function selectAppDocSecResut(result,type)
{
	if(result == true && type == "1")//대기함
		selectAppDoc(docId, lobCode, transferYn, elecYn, preNextYn);
	else if(result == true && type == "2")//임시저장
		selectTemporary(docId,lobCode, elecYn, preNextYn);
	else if(result == true && (type=="3" || type=="4"))//서명인 대장 또는 일일감사일지
		selectOnlyAppDoc(docId, lobCode, elecYn, preNextYn);
	else
		selectPubPost(publishId, docId, lobCode, preNextYn);
		
}

//보안문서 열기
function selectAppDocSec(docid, lobcode, transferyn, elecyn, preNextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	docId = docid;
	lobCode = lobcode;
	transferYn = transferyn;
	elecYn = elecyn;
	preNextYn = preNextyn;
	var type="1";
	if(securityYn == "Y" && isDuration == "true")
	  opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?securitypass="+securityPass+"&type="+type+"&docId="+docId, 350, 160);
}

function selectMigRegDoc(docId) {
	//관련문서 열기 기능이 필요하다면 추후에 추가.
	openMigRegDoc(docId);
}
function openMigRegDoc(docId) {
	curDocId = docId;
	var width = 830;
		if(curCompId == "003") {
			width = 730;
		} else if(curCompId == "004") {
			width = 1000;
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
	var linkUrl = "<%=webUri%>/app/mig/oldOpener.do?dataurl=" + docId + "&cabinet=REGILEDGER&docstatus=0&linename=0&serialorder=-1&bodytype=gul";
	
	appDoc = openWindow("selectAppDocWin", linkUrl , width, height);
}

function selectMigRecvDoc(docId) {
	//관련문서 열기 기능이 필요하다면 추후에 추가.
	openMigRecvDoc(docId);
}
function openMigRecvDoc(docId) {
	curDocId = docId;
	var width = 830;
		if(curCompId == "003") {
			width = 730;
		} else if(curCompId == "004") {
			width = 1000;
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
	var linkUrl = "<%=webUri%>/app/mig/oldOpener.do?dataurl=" + docId + "&cabinet=RECVLEDGER&docstatus=0&linename=1&serialorder=-1&bodytype=gul";
	
	appDoc = openWindow("selectAppDocWin", linkUrl , width, height);
}

//문서 전체보기
function selectAppDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	var isPop = false;

	if(preNextYn == "N"){	
		if(lobCode != "LOL099" ) {
			isPop = isPopOpen();
		}else{
			isPop = isRelationPopOpen();
		}
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
		if(lobCode == "LOB005" || lobCode == "LOB006"){
			linkUrl = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}else if(lobCode == "LOB007" || lobCode == "LOB011" || lobCode == "LOB019" || lobCode == "LOL002"){
				linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode="+lobCode;
		}else{
			linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}
		if(lobCode != "LOL099" ) {
			appDoc = openWindow("selectAppDocWin", linkUrl , width, height);
		}else{
			relationDoc = openWindow("relationAppDocWin", linkUrl , width, height);	//20140724 명칭변경 kj.yang
		}

	}

}
//보안문서 열기
function selectOnlyAppDocSec(docid,lobcode, elecyn, preNextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	docId = docid;
	lobCode = lobcode;
	//transferYn = transferyn;
	elecYn = elecyn;
	preNextYn = preNextyn;
	var type="3";//서명날인대장
	if(lobcode == "<%=lol007%>")
		type="4";//일일감사일지
	if(securityYn == "Y" && isDuration == "true")
		 opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?securitypass="+securityPass+"&type="+type+"&docId="+docId, 350, 160);
}

//문서 전체보기
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
		if(lobCode == "LOB005" || lobCode == "LOB006"){
			linkUrl = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}else if(lobCode == "LOB007" || lobCode == "LOB011" || lobCode == "LOB019" || lobCode == "LOL002"){
				linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode="+lobCode;
		}else{
			linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}

		appDoc = openWindow("selectAppDocWin", linkUrl , width, height); 
	}

}

<% if("LOB007".equals(resultLobCode) || "LOB008".equals(resultLobCode) || "LOB019".equals(resultLobCode)) { %>
//문서 전체보기
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
<% } %>

//문서 전체보기
function selectSenderDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openSenderAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openSenderAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}
}

function openSenderAppDoc(docId, lobCode, transferYn, elecYn, preNextYn, isPop) {
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
		linkUrl = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;	
			
		appDoc = openWindow("senderAppDocWin", linkUrl , width, height);
	}

}


//접수문서 보기(전자문서)
function selectEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		if(lobCode != "LOL099" ) {
			isPop = isPopOpen();
		}else{
			isPop = isRelationPopOpen();
		}
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
		if(lobCode != "LOL099" ) {			
			appDoc = openWindow("selectEnfDocWin", linkUrl , width, height);
		}else{
			relationDoc = openWindow("relationEnfDocWin", linkUrl , width, height); //20140724 명칭변경 kj.yang
		}
	}

}


// 문서 본문 보기
function openAppBody(docId) {
	var isPop = isPopOpen();

	if(isPop){
		var width = 830;
		
		if (screen.availWidth < 830) {
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

		appDoc = openWindow("openAppBodyWin", "<%=webUri%>/app/approval/openAppBody.do?docId="+docId , width, height);
	}

}


// 검색 조건 변경
function changeSearchType(searchType){

	if(searchType == "searchSaveDate" || searchType == "searchBizDate" || searchType == "searchDraftDate" || searchType == "searchCompleteDate"){
		
		$('#searchTypeDateFromTd').show();
		$('#searchTypeDateDiv').show();
		$('#searchTypeDateTailTd').show();
		$('#searchWordTd').hide();
		$('#searchWord').hide();
		$('#searchWord').val("");
		
	}else{
		
		$('#searchTypeDateFromTd').hide();
		$('#searchTypeDateDiv').hide();
		$('#searchTypeDateTailTd').hide();
		$('#searchWordTd').show();
		$('#searchWord').show();
		
	}

}

//카테고리 필터(문서분류)
function chDocType(docType){
	/*if($("#selDocType").val() == ""){
		alert("<spring:message code='list.list.msg.noSearchTypeDocTypeSelected'/>");
		$("#selDocType").focus();
		return;
	}
	*/
	
	$("#ListFormcPage").val("1");
	$("#searchDocType").val(docType);
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();	
	

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

function closeWindow(){
	window.close();
}

<% if("LOB003".equals(resultLobCode) || "LOB026".equals(resultLobCode)) { %>
// 반려문서 삭제
function deleteAppDoc() {
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noDeleteDoc'/>");
			return;	
		}else{
			if(confirm("<spring:message code='list.list.msg.delDocConfirm'/>")){
				var param = $("[listFormChk='Y']:checked").serialize();

				$.post("<%=webUri%>/app/approval/deleteAppDoc.do", param , function(data){
			        alert(data.message);
	
			        listRefresh();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.docDelete'/>");
					
				});
			}
			
		}
	}
}
<% } %>

<% if("LOB027".equals(resultLobCode)) { %>
 //완전삭제
function deleteComplate() {
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if(confirm("폐기함에서 문서를 완전히 삭제합니다. 해당 문서는 복구될 수 없습니다. 계속 진행하시겠습니까?")){
			var param = $("[listFormChk='Y']:checked").serialize();

			$.post("<%=webUri%>/app/memo/completelyDeleteAppDoc.do", param , function(data){
		        alert("문서가 삭제 되었습니다.");
		        listRefreshFromList();
		        //listRefresh();
		        
		    }, 'json').error(function(data) {
				alert("<spring:message code='list.list.msg.fail.docDelete'/>");
				
			});
		}
	}
}

//문서 복구
function restoreDoc() {
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noDeleteDoc'/>");
			return;	
		}else{
			if(confirm("<spring:message code='list.list.msg.delDocConfirm'/>")){
				var param = $("[listFormChk='Y']:checked").serialize();

				$.post("<%=webUri%>/app/approval/deleteAppDoc.do", param , function(data){
			        alert(data.message);
	
			        listRefresh();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.docDelete'/>");
					
				});
			}
			
		}
	}
} 
<% } %>
 
<% if("LOB001".equals(resultLobCode)) { %>
// 임시문서 삭제
function deleteTemporary() {
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if(confirm("<spring:message code='list.list.msg.delDocConfirm'/>")){
			var param = $("[listFormChk]:checked").serialize();
			
			$.post("<%=webUri%>/app/approval/deleteTemporary.do", param , function(data){
		        alert(data.message);

		        	        
		        listRefresh();
		        
		    }, 'json').error(function(data) {
				alert("<spring:message code='list.list.msg.fail.temporaryDocDelete'/>");
				
			});
		}

	   	
	}

}

//보안문서 열기
function selectTemporarySec(docid,lobcode, elecyn, preNextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	docId = docid;
	lobCode = lobcode;
	//transferYn = transferyn;
	elecYn = elecyn;
	preNextYn = preNextyn;
		var type="2";
	if(securityYn == "Y" && isDuration == "true")
		 opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?securitypass="+securityPass+"&type="+type+"&docId="+docId, 350, 160);
}

function selectTemporary(docId,lobCode, elecYn, preNextYn) {
	
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openTempDoc(docId, lobCode, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openTempDoc(docId, lobCode, elecYn, preNextYn, true); }, 100);
	}
}


function openTempDoc(docId,lobCode, elecYn, preNextYn, isPop) {
	
	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 1200;
		
		if (screen.availWidth < 1200) {	
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
		var linkUrl = "<%=webUri%>/app/approval/selectTemporary.do?docId="+docId+"&lobCode="+lobCode;
			

		appDoc = openWindow("selectTemporaryWin", linkUrl , width, height);
	}
}
<% } %>

<% if("LOB009".equals(resultLobCode) || "LOB010".equals(resultLobCode) ) { %>
//공람자 팝업
function selectPubReader() {
	var top = (screen.availHeight - 650) / 2;
	var left = (screen.availWidth - 460) / 2;
	var option = "width=650,height=460,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";	

	pubreader = openWindow("selectPubReaderWin", "<%=webUri%>/app/approval/ApprovalPubReader.do" , "650", "570");
}


//공람지정(버튼)
function displayAppoint() {
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;		
	}else{		

		selectPubReader();	   	
	}
}

// 공람자 선택 후 리턴 값 확인
function setPubReader(pubreader) {
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}	

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{		
		
		if(pubreader){

			//alert(pubreader);

            document.getElementById("pubReader").value=pubreader;

			insertPubReader();
  
		}else{
			alert("<spring:message code='list.list.msg.noPubReaderInfo'/>");
			return;
		}
	}
	
}

//공람자 등록
function insertPubReader(){

	
    $.post("<%=webUri%>/app/appcom/insertPubReader.do", $("#formList").serialize() , function(data){

        if(data.result =="success"){
            alert("<spring:message code='appcom.result.msg.pubreaderok'/>");
        }else{
            alert("<spring:message code='list.list.msg.fail.insertPubReader'/>");
        }
    }, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.insertPubReader'/>");
		
	});  
}
<% } %>

<% if("LOB012".equals(resultLobCode)) { %>


//공람처리
function displayProgress(){
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.alreadyDisplayProgress'/>");
			return;	
		}else{
			var param = $("[listFormChk='Y']:checked").serialize();
			
			$.post("<%=webUri%>/app/appcom/processPubReader.do", param , function(data){
		        alert(data.message);

		        listRefresh();
		        
		    }, 'json').error(function(data) {
		    	alert(data.message);
		    	
		    	listRefresh();
				
			});
			
			
		}
	}	
	
}

//공람/미공람 선택
function chDisplay(dislplayYn){
	
	$("#ListFormcPage").val("1");
	$("#selDisplayYn").val(dislplayYn);
	$("#excelExportYn").val("N");
	$("#pageSizeYn").val("N");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();	
}
<% } %>

<% if("LOB002".equals(resultLobCode)) { %>
// 연계기안문서
function selectBizAppDoc(docId,lobCode, elecYn, preNextYn) {
var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openBizDoc(docId, lobCode, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openBizDoc(docId, lobCode, elecYn, preNextYn, true); }, 100);
	}

}

function openBizDoc(docId,lobCode, elecYn, preNextYn, isPop) {
	
	if(isPop){

		curElecYn = elecYn;
		curDocId = docId;
		
		var width = 1200;
		
		if (screen.availWidth < 1200) {	
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
		var linkUrl = "<%=webUri%>/app/approval/selectBizAppDoc.do?docId="+docId+"&lobCode="+lobCode;
			
		appDoc = openWindow("selectBizAppDocWin", linkUrl , width, height);
	}
}

//반송
function returnBizDoc(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
	}else{
		if(confirm("<spring:message code='list.list.msg.returnBizDocConfirm'/>")){
			
			var param = $("[listFormChk]:checked").serialize();
			
			$.post("<%=webUri%>/app/exchange/processReject.do", param , function(data){
		        alert(data.message);
	
		        	        
		        listRefresh();
		        
		    }, 'json').error(function(data) {
				alert("<spring:message code='list.list.msg.fail.returnBizDoc'/>");
				
			});
		}
	   	
	}
	
}
<% } %>


<% if("LOB013".equals(resultLobCode)) { %>
//후열처리
function rearProgress(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noRearDoc'/>");
			return;	
		}else{
			var param = $("[listFormChk='Y']:checked").serialize();
			
			$.post("<%=webUri%>/app/approval/readafterAppDoc.do", param, function(data) {
	
		        alert(data.message);
		        
		        listRefresh();
	
		    }, 'json').error(function(data) {
	
		        alert("<spring:message code='approval.msg.fail.readafter'/>");
	
		    });
		}


		
	   	
	}

}
<% } %>


<% if("LOB024".equals(resultLobCode)) { %>
//통보처리  // jth8172 2012 신결재 TF
function informProgress(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noInformDoc'/>");
			return;	
		}else{
			var param = $("[listFormChk='Y']:checked").serialize();
			
			$.post("<%=webUri%>/app/approval/informAppDoc.do", param, function(data) {
	
		        alert(data.message);
		        
		        listRefresh();
	
		    }, 'json').error(function(data) {
	
		        alert("<spring:message code='approval.msg.fail.inform'/>");
	
		    });
		}


		
	   	
	}

}
<% } %>


<% if("LOL001".equals(resultLobCode) ){ %>
// 비전자 문서 등록 div (등록대장)
function showNoElecDocRegist(){
	var evt=window.event || event;

    document.getElementById("nonElecDocRegistDiv").style.position="absolute";
    $('#nonElecDocRegistDiv').show();
    document.getElementById("nonElecDocRegistDiv").style.left=evt.clientX - 50;    //evt.clientX는 이벤트가 발생한 x좌료
    document.getElementById("nonElecDocRegistDiv").style.top=evt.clientY + document.body.scrollTop;   //y좌표
	
}
<% } %>

<% if("LOL001".equals(resultLobCode) || "LOL002".equals(resultLobCode) ) { %>
//비전자 문서 등록(배부대장 - 생산문서)
function noElecAppDocRegist(lobCode){	
	var width = 830;

	if (screen.availWidth < 830) {
		width = screen.availWidth;
	}
	var height = 670;
	if (screen.availHeight < 670) {
		height = screen.availHeight;
	}

	var top = (screen.availHeight - height) / 2;
	var left = (screen.availWidth - width) / 2;		
	var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	noElecDoc = openWindow("noElecAppDocRegistWin", "<%=webUri%>/app/approval/insertProNonElecDoc.do?lobCode="+lobCode , width, height, "yes");
	
}

//비전자 문서 등록(배부대장 - 접수문서)
function noElecEnfDocRegist(lobCode){
	if(lobCode == "" || lobCode == null){
		alert("<spring:message code='list.list.msg.noLobCode'/>");
		return;
	}
	var width = 830;

	if (screen.availWidth < 830) {
		width = screen.availWidth;
	}
	var height = 660;
	if (screen.availHeight < 660) {
		height = screen.availHeight;
	}

	var top = (screen.availHeight - height) / 2;
	var left = (screen.availWidth - width) / 2;		
	var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";

	noElecDoc = openWindow("noElecEnfDocRegistWin", "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?lobCode="+lobCode , width, height, "yes");
	
}
<% } %>


<% if("LOL098".equals(resultLobCode)) { %>
//생산문서 하위번호 선택
function setProDocSerial(deptCategory, serialNumber, subSerialNumber, docId, approvalTitle, bindingId, bindingName){
	if(deptCategory == "" || serialNumber == "" || docId == ""){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}
	
	var serialNum = deptCategory + String.fromCharCode(2) + serialNumber + String.fromCharCode(2) + subSerialNumber + String.fromCharCode(2) + docId + String.fromCharCode(2) + approvalTitle + String.fromCharCode(2) + bindingId + String.fromCharCode(2)+ bindingName +  String.fromCharCode(4);
	if (opener != null && opener.setProSerial != null) {
		opener.setProSerial(serialNum);
	}

	closeWindow();
}

<% } %>

<% if("LOL099".equals(resultLobCode)) { %>
//관련문서 선택 버튼 클릭 이벤트
function selRelationDocRegist(){

	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
	}else{
		var docIdValue 		= [];
		var titleValue 		= [];
		var usingValue 		= [];
		var electronYnValue	= [];
		
		$("[listFormChk]:checked").each(function(){
			
			docIdValue.push(this.value);
			titleValue.push(this.getAttribute("listFormChk"));
			usingValue.push(this.getAttribute("listUsingType"));
			electronYnValue.push(this.getAttribute("listElectronYn"));
			
		});

		var docLen = docIdValue.length;
		var returnString = "";
		
		for(i=0; i < docLen; i++){
			
			returnString += docIdValue[i] + String.fromCharCode(2) + titleValue[i]+ String.fromCharCode(2) + usingValue[i]+ String.fromCharCode(2) + electronYnValue[i]+ String.fromCharCode(4);
			
		}
		<% if ("Y".equals(searchElectronDocYn)) { %>
		if (parent != null && parent.setRelatedDoc != null) {
			parent.setRelatedDoc(returnString);
		}
		<% } else { %>
		if (opener != null && opener.setRelatedDoc != null) {
			opener.setRelatedDoc(returnString);
		}

		closeWindow();
	   	<% } %>
	}
	
}
<% } %>


<% if("LOB003".equals(resultLobCode) || "LOB004".equals(resultLobCode) || "LOB007".equals(resultLobCode) || "LOB008".equals(resultLobCode) || "LOB009".equals(resultLobCode) || "LOB010".equals(resultLobCode) || "LOB011".equals(resultLobCode) || "LOB012".equals(resultLobCode) || "LOB014".equals(resultLobCode) || "LOB015".equals(resultLobCode) || "LOB019".equals(resultLobCode) || "LOB017".equals(resultLobCode) || "LOB018".equals(resultLobCode) || "LOB100".equals(resultLobCode)  || "LOL001".equals(resultLobCode) || "LOL002".equals(resultLobCode) || "LOL003".equals(resultLobCode) || "LOL099".equals(resultLobCode) ) { %>
// 비전자문서 상세보기
function selectNonAppDoc(docId, lobCode, transferYn, elecYn, preNextYn) {
	var isPop = false;
	
	if(preNextYn == "N"){
		if(lobCode != "LOL099" ) {
			isPop = isPopOpen();
		}else{
			isPop = isRelationPopOpen();
		}
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
	    
	    if(lobCode != "LOL099" ) {
	    	appDoc = openWindow("selectNonAppDocWin", url , width, height, "yes");
	    }else{
	    	relationDoc = openWindow("relationNonAppDocWin", url , width, height, "yes");	//20140724 명칭변경 kj.yang
	    }
	}

}

function selectNonEnfDoc(docId, lobCode, transferYn, elecYn, preNextYn) {

	var isPop = false;
	
	if(preNextYn == "N"){	
		if(lobCode != "LOL099" ) {
			isPop = isPopOpen();
		}else{
			isPop = isRelationPopOpen();
		}
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

	    if(lobCode != "LOL099" ) {
	    	appDoc = openWindow("selectNonEnfDocWin", url , width, height, "yes");
	    }else{
	    	relationDoc = openWindow("relationNonEnfDocWin", url , width, height, "yes");	//20140724 명칭변경 kj.yang
	    }
	}
    
}

<% } %>

<% if("LOL004".equals(resultLobCode)) { %>
// 서명인날인 등록 팝업
function stampRegist(){
	var url = "<%=webUri%>/app/list/seal/insertNonEleStampSeal.do";
    url += "?lobCode=<%=resultLobCode%>";
    
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

    NonEleStampSeal = openWindow("stampRegistWin", url , width, height, "yes");
}

<% } %>

<% if("LOL005".equals(resultLobCode)) { %>
//직인날인 등록 팝업
function sealRegist(){
	var url = "<%=webUri%>/app/list/seal/insertNonEleStampSeal.do";
    url += "?lobCode=<%=resultLobCode%>";
    
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
    
    NonEleStampSeal = openWindow("sealRegistWin", url , width, height, "yes");
}
<% } %>

<% if("LOL004".equals(resultLobCode) || "LOL005".equals(resultLobCode)  ) { %>
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
	    var option = "top="+top+",left="+left+",width="+width+",height="+height+",menubar=no,scrollbars=1,status=yes";

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

				var url = "<%=webUri%>/app/list/seal/deleteStampSealList.do";
				
				$.post(url, param , function(data){
			        alert(data.message);
	
			        listRefreshFromList();
			        
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

// 서명인 날인 승인
function stampSealConfirm(){
	if($("input#stampId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}
	if($("[confirmAuthYn]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[confirmAuthYn='N']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noAuthStampSealConfirmDoc'/>");
			return;	
			
		}else{
			
			var param = $("[confirmAuthYn='N']:checked").serialize();
			param += "&lobCode=<%=resultLobCode%>";
			
			

			$.post("<%=webUri%>/app/list/seal/confirmStampSealList.do", param , function(data){
		        alert(data.message);

		        listRefreshFromList();
		        
		    }, 'json').error(function(data) {
		    	alert(data.message);
				
			});

		}
	}
}
<%} %>

<% if("LOB031".equals(resultLobCode)) { %>
//공람게시 종료
function publishEnd(){
	if($("input#publishId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noAuth'/>");
			return;	
		}else{
			if(confirm("<spring:message code='list.list.msg.endPubConfirm'/>")){
				var param = $("[listFormChk='Y']:checked").serialize();

				$.post("<%=webUri%>/app/etc/closePublicPost.do", param , function(data){
			        alert(data.message);
	
			        listRefreshFromList();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.endPubPost'/>");
					
				});
			}
			
		}
	}
}

//보안문서 열기
function selectPubPostSec(publishid, docid, lobcode, prenextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	publishId = publishid;
	docId = docid;
	lobCode = lobcode;
	//transferYn = transferyn;
	//elecYn = elecyn;
	preNextYn = prenextyn;
	var type="5";
	if(securityYn == "Y" && isDuration == "true")
	  opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?securitypass="+securityPass+"&type="+type+"&docId="+docId, 350, 160);
}

function selectPubPost(publishId, docId, lobCode, preNextYn){
	if(publishId == "" || docId == ""){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}

	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openPubPost(publishId, docId, lobCode, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openPubPost(publishId, docId, lobCode, preNextYn, true); }, 100);
	}
}

function openPubPost(publishId, docId, lobCode, preNextYn, isPop){
	
	if(isPop){
		var width = 1200;
		
		if (screen.availWidth < 1200) {	
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
		
		linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?publishId="+publishId+"&docId="+docId+"&lobCode="+lobCode;
			
		appDoc = openWindow("selectPubPostWin", linkUrl , width, height);	
		
	}
	
	
}


function selectEnfPubPost(publishId, docId, lobCode, preNextYn){
	if(publishId == "" || docId == ""){
		alert("<spring:message code='list.list.msg.noDocId'/>");
		return;
	}

	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openEnfPubPost(publishId, docId, lobCode, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openEnfPubPost(publishId, docId, lobCode, preNextYn, true); }, 100);
	}
}

function openEnfPubPost(publishId, docId, lobCode, preNextYn, isPop){
	
	if(isPop){
		var width = 1200;
		
		if (screen.availWidth < 1200) {	
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
		
		linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?publishId="+publishId+"&docId="+docId+"&lobCode="+lobCode;
			
		appDoc = openWindow("selectEnfPubPostWin", linkUrl , width, height);	
		
	}
	
	
}

//비전자문서 상세보기
function selectNonAppPubPost(publishId, docId, lobCode, preNextYn) {
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonAppPubPost(publishId, docId, lobCode, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonAppPubPost(publishId, docId, lobCode, preNextYn, true); }, 100);
	}
}

function openNonAppPubPost(publishId, docId, lobCode, preNextYn, isPop) {
	
	if(isPop){

		curElecYn = 'N';
		curDocId = docId;
		
	    var url = "<%=webUri%>/app/approval/selectProNonElecDoc.do";
	    url += "?publishId="+publishId+"&docId="+docId+"&lobCode="+lobCode;
	    
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
	    
	    appDoc = openWindow("selectNonAppPubPostWin", url , width, height, "yes");
	}

}

//비전자문서 상세보기
function selectNonEnfPubPost(publishId, docId, lobCode, preNextYn) {
	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonEnfPubPost(publishId, docId, lobCode, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonEnfPubPost(publishId, docId, lobCode, preNextYn, true); }, 100);
	}
}

function openNonEnfPubPost(publishId, docId, lobCode, preNextYn, isPop) {
	
	if(isPop){

		curElecYn = 'N';
		curDocId = docId;
		
		var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
	    url += "?publishId="+publishId+"&docId="+docId+"&lobCode="+lobCode;
	    
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
	    
	    appDoc = openWindow("selectNonAppPubPostWin", url , width, height, "yes");
	}

}
<% } %>

<% if("LOL007".equals(resultLobCode)) { %>
function deleteDailyAuditDoc(){
	if($("input#docId").size() == 0){
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

				
				$.post("<%=webUri%>/app/appcom/deleteAuditList.do", param , function(data){
			        alert(data.message);
	
			        listRefreshFromList();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.auditDocDelete'/>");
					
				});				
				
			}
			
		}
	}
}

function registDailyAuditDoc(lobCode){
	var isPop = isPopNonEleAudit();
	
	if(isPop){
		
		var url = "<%=webUri%>/app/list/audit/insertNonEleAudit.do?lobCode="+lobCode;
	    
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

	    NonEleAudit = openWindow("registDailyAuditDocWin", url , width, height, "yes");
	    
	}
}

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

// 일상감사 일지 수정(전자,비전자)
function modifyDailyAudit(){
	
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[modifyChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[modifyChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noModifyDoc'/>");
			return;	
		}else{
			if($("[modifyChk='Y']:checked").length > 1){
				alert("<spring:message code='list.list.msg.selDocOnlyOne'/>");
				return;	
			}else{
				var param = $("[modifyChk='Y']:checked").serialize();
				var curId = param.split("=");
				curId = curId[1];
				
				var aElement = document.getElementById(curId);
				var elecYn = aElement.getAttribute("elecYn");

				var isPop = isPopNonEleAudit();
				
				if(isPop){
					
					var url = "<%=webUri%>/app/list/audit/updateNonEleAudit.do?lobCode=<%=resultLobCode%>&"+param;
				    
				    var width = 600;   
				    if (screen.availWidth < 600) {
				        width = screen.availWidth;
				    }   
				    var height = 430;
				    if(elecYn == "Y"){
					    height = 350;
				    }
				    
				    if (screen.availHeight < height) {	
					    height = screen.availHeight;	
					}
				    var top = (screen.availHeight - height) / 2;
				    var left = (screen.availWidth - width) / 2; 
				    var option = "top="+top+",left="+left+",width="+width+",height="+height+",menubar=no,scrollbars=no,status=yes";
	
				    NonEleAudit = openWindow("registDailyAuditDocWin", url , width, height, "yes");
				    
				}
			
			}
			
		}
	}
	
}


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
<% } %>

//상세검색
function detailSearch(){
	if($("#detailSearchYn").val() == "N"){
		$("#detailSearchYn").val("Y");
		$("#detailSearchDiv").show();
		$("#detailSearchDiv2").show();
		<%if(!lob022.equals(resultLobCode)){ %>
		$("#detailSearchDiv3").show();
		<% } %>
		$("#detailSearchDiv4").show();
	}else{
		$("#detailSearchYn").val("N");
		$("#detailSearchDiv").hide();
		$("#detailSearchDiv2").hide();
		<%if(!lob022.equals(resultLobCode)){ %>
		$("#detailSearchDiv3").hide();
		<% } %>
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

//공람게시판 열람자 팝업
function displayNoticeReadPersonPop(pubId){
	if(pubId == ""){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}	


    var top = (screen.availHeight - 400) / 2;
    var left = (screen.availWidth - 500) / 2;
    var option = "width=500,height=400,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";

    readerWin = openWindow("displayNoticeReadPersonPopWin", "<%=webUri%>/app/appcom/listPostReader.do?publishId=" + pubId , "500", "400");

	

		
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

function screenBlock() {
    var top = ($(window).height() - 150) / 2;
    var left = ($(window).width() - 350) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

<% if("LOL008".equals(resultLobCode)) { %>
//감사직인날인대장 등록
function registAuditSealDoc(){
	var url = "<%=webUri%>/app/list/seal/insertAuditSeal.do";
    url += "?lobCode=<%=resultLobCode%>";
    
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

    NonEleStampSeal = openWindow("registAuditSealDocWin", url , width, height, "yes");
}

//감사직인날인대장 선택
function selectNonSealDoc(stampId, lobCode, elecYn, preNextYn) {

	var isPop = false;
	
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openNonSealDoc(stampId, lobCode, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openNonSealDoc(stampId, lobCode, elecYn, preNextYn, true); }, 100);
	}
}

function openNonSealDoc(stampId, lobCode, elecYn, preNextYn, isPop) {
	
	if(isPop){
		
		curElecYn = elecYn;
		curDocId = stampId;
		
		var url = "<%=webUri%>/app/list/seal/insertAuditSeal.do";
	    url += "?lobCode="+lobCode+"&stampId="+stampId;
	    url += "&modYn=N";
	    
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
	
	    appDoc = openWindow("selectNonSealDocWin", url , width, height, "yes");
	}
}

//감사직인날인대장 수정
function modifyAuditSealDoc(){
	if($("input#stampId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[listFormChk]:checked").length > 1){
			alert("<spring:message code='list.list.msg.selDocOnlyOne'/>");
			return;
		}else{
			var param = $("[listFormChk]:checked").serialize();
			param += "&modYn=Y";
			
			var url = "<%=webUri%>/app/list/seal/insertAuditSeal.do";
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

		    NonEleStampSeal = openWindow("modifyAuditSealDocWin", url , width, height, "yes");
		}
	}
}

//감사직인날인대장 취소
function cancelDoc() {
	
	if($("input#stampId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		
		if($("[listFormChk='N']:checked").length == 0){
			alert("<spring:message code='list.list.msg.nocancelDoc'/>");
			return;	
		}else{
			if(confirm("<spring:message code='list.list.msg.cancelauditSealDocConfirm'/>")){
				var param = $("[listFormChk='N']:checked").serialize();
				
				
				$.post("<%=webUri%>/app/list/seal/deleteStampList.do", param , function(data){
			        alert(data.message);
	
			        listRefreshFromList();
			        
			    }, 'json').error(function(data) {
					alert("<spring:message code='list.list.msg.fail.auditSealDocDelete'/>");
					
				});
			}
			
			
			
		}
	}
}
<% } %>
<% if("LOL097".equals(resultLobCode)) { %>
function setDetailSearch(docNumber, docId, title, drafterId, drafterName, senderTitle, recvDeptNames, compId){
	var obj = new Object();

	obj.docNumber 		= docNumber;
	obj.docId 			= docId;
	obj.title 			= title;
	obj.drafterId 		= drafterId;
	obj.drafterName 	= drafterName;
	obj.senderTitle 	= senderTitle;
	obj.recvDeptNames 	= recvDeptNames;
	obj.compId 			= compId;

	if (opener != null && opener.setAuditSerial) {
		opener.setAuditSerial(obj);
		window.close();
	}
	

}

<% } %>

<% if("LOB020".equals(resultLobCode) || "LOB014".equals(resultLobCode) || "LOL001".equals(resultLobCode)) { %>
//부서선택
function selectDept(){
	var width = 500;
    var height = 300;
    
    var top = (screen.availHeight - 560) / 2;
    var left = (screen.availWidth - 800) / 2;

    var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

    selDept = openWindow("deptInfoWin", "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2" , width, height);
    
		
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

//부서선택(열람권한관련)
function selectDeptAuth(){
	var width = 500;
    var height = 300;
    
    var top = (screen.availHeight - 560) / 2;
    var left = (screen.availWidth - 800) / 2;
	
    var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

    selDept = openWindow("deptInfoWin", "<%=webUri%>/app/common/OrgAuthDept.do?type=2&treetype=3&confirmYn=Y" , width, height);
}

//부서id 셋팅(열람권한관련)
function setDeptAuthInfo(obj){
	if (typeof(obj) == "object") {
		
		screenBlock();
		 
		$("#ListFormcPage").val("1");
		$("#searchAuthDeptId").val(obj.orgId);
		$("#searchAuthDeptName").val(obj.orgName);
		$("#pageSizeYn").val("N");
		$("#excelExportYn").val("N");
		$("#listSearch").attr("target", "");
		$("#listSearch").attr("action", "");
		$("#listSearch").submit();
	}
}
<% } %>

function setSearchAppDocYn(appDocYn){
	
	if($("#chkAppDocYn:checked").length > 0){
		$("#easyApprSearch").val("N");
		$("#easyEnfSearch").val("N");
		
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

		$("#easyApprSearch").val("D");
		$("#easyEnfSearch").val("D");
	}
}

function setSearchEnfDocYn(enfDocYn){
	
	if($("#chkEnfDocYn:checked").length > 0){
		$("#easyApprSearch").val("N");
		$("#easyEnfSearch").val("N");
		
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

		$("#easyApprSearch").val("D");
		$("#easyEnfSearch").val("D");
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

// 숫자체크
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



<% if("LOB098".equals(resultLobCode)) { %>
//거래처 등록을 위한 이벤트
function selGwPibsv(){

	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.customListNoData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.selCustomListNoData'/>");
	}else{
		var docIdValue = [];
		
		$("[listFormChk]:checked").each(function(){
			
			docIdValue.push(this.value);
			
		});

		var docLen = docIdValue.length;
		var returnString = "";
		
		for(i=0; i < docLen; i++){
			
			returnString += docIdValue[i]+ String.fromCharCode(4);
		}
		
		if (parent != null && parent.setGwPibsv != null) {
			parent.setGwPibsv(returnString);
		}
		
	}
	
}
<% } %>

<% if("LOB014".equals(resultLobCode)) { %>
function chSearchAudit(typeValue){
	
	if(typeValue == "searchTypeAuditReadYn"){

		$('#searchWord').val('');
		$('#searchWordTd').hide();
		$('#searchTypeAuditReadYnTd').show();
		
	}else{
		var n = $("input:checked").length;
		if(n > 0){
			$("input:checked").attr("checked","");
		}
		
		$('#searchWordTd').show();
		$('#searchTypeAuditReadYnTd').hide();
	}
	
}
<% } %>

<% if("LOL001".equals(resultLobCode) || "LOL003".equals(resultLobCode)) { %>

//문서 등록 취소(등록 대장, 미등록 대장)
function unRegistDoc(){
	if($("input#docId").size() == 0){
		alert("<spring:message code='list.list.msg.noData'/>");
		return;
	}

	if($("[input#docId]:checked").length == 0){
		alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		return;
	}else{
		if($("[listFormChk='Y']:checked").length == 0){
			alert("<spring:message code='list.list.msg.noUnRegistDocByDeptAdmin'/>");
			return;	
		}else{
			var param = $("[listFormChk='Y']:checked").serialize();
			
			$("#returnDocId").val(param);
			popOpinion("unReigstDocOk","<%=unRegistDocBtn%>","Y");				
			
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
	
	$.post("<%=webUri%>/app/approval/unregistAppDoc.do", param , function(data){
        alert(data.message);

        listRefreshFromList();
        
    }, 'json').error(function(data) {
		alert("<spring:message code='list.list.msg.fail.unRegistDoc'/>");
		
	});
}
<% } %>

<% if("LOL001".equals(resultLobCode)){ %>
function searchEasyApprDoc(){
	
	$("#easyApprSearch").val("Y");
	$("#easyEnfSearch").val("D");
	$("#searchAppDocYn").val("");
	$("#searchEnfDocYn").val("");
	
	goSearch();
}

function searchEasyEnfDoc(){
	
	$("#easyApprSearch").val("D");
	$("#easyEnfSearch").val("Y");
	$("#searchAppDocYn").val("");
	$("#searchEnfDocYn").val("");
	
	goSearch();
}
<% } %>

<% if("LOL099".equals(resultLobCode)){ %>
function chLobCode(searchLobCode){
	$("#searchLobCode").val(searchLobCode);

	goSearch();
}
<% }%>

//-->
</script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />