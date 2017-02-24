<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.env.vo.EmptyInfoVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>

<%
/** 
 *  Class Name  : ListMainCommon.jsp 
 *  Description : 리스트 메인 공통 javascript 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 24 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
response.setHeader("pragma","no-cache");


String portalUrl = AppConfig.getProperty("portal_url", "", "portal");

String userId	= (String) session.getAttribute("USER_ID");	// 사용자 ID
String userName = (String) session.getAttribute("USER_NAME");	// 사용자 이름
String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
String useTrayYn = CommonUtil.nullTrim((String) session.getAttribute("USE_TRAY"));	// 트레이 사용여부


String boxCode103 = appCode.getProperty("OPT103", "OPT103", "OPT");
String boxCode104 = appCode.getProperty("OPT104", "OPT104", "OPT");
String boxCode105 = appCode.getProperty("OPT105", "OPT105", "OPT");
String boxCode106 = appCode.getProperty("OPT106", "OPT106", "OPT");
String boxCode107 = appCode.getProperty("OPT107", "OPT107", "OPT");
String boxCode108 = appCode.getProperty("OPT108", "OPT108", "OPT");
String boxCode110 = appCode.getProperty("OPT110", "OPT110", "OPT");
String boxCode112 = appCode.getProperty("OPT112", "OPT112", "OPT");
String boxCode115 = appCode.getProperty("OPT115", "OPT115", "OPT");

// 대결 및 부재 설정
String sessionRepresentativeYn = (String) session.getAttribute("sessionRepresentativeYn");

List<EmptyInfoVO> emptyInfos = (List<EmptyInfoVO>) request.getAttribute("emptyInfos");
EmptyInfoVO substituteVO = (EmptyInfoVO) request.getAttribute("substituteVO");

int emptyInfosSize = emptyInfos.size(); 
// 대결 및 부재 설정 끝




%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--
$(document).ready(function(){ 
	
	<% 
	if("Y".equals(sessionRepresentativeYn)) {
	    
	    StringBuffer buff = new StringBuffer();
	    String substituteMsg = "";
	    String emptyMsg = "";
	    
	    if(emptyInfosSize > 0 ){
			
		    for(int i = 0; i < emptyInfosSize; i++){
				EmptyInfoVO emptyInfo = emptyInfos.get(i);
				String substituteName 		= CommonUtil.nullTrim(emptyInfo.getSubstituteName());
				String substituteStartDate 	= DateUtil.getFormattedShortDate(EscapeUtil.escapeDate(emptyInfo.getSubstituteStartDate()));
				String substituteEndDate 	= DateUtil.getFormattedShortDate(EscapeUtil.escapeDate(emptyInfo.getSubstituteEndDate()));
				substituteMsg = messageSource.getMessage("list.list.msg.representative", null, langType).replace("%userName",substituteName).replace("%startDate",substituteStartDate).replace("%endDate",substituteEndDate);
				
				if(emptyInfosSize - 1 != i){
				    substituteMsg += "\\n\\n";
				}
				buff.append(substituteMsg);
		    }
		    
	    }
	    
	    if(substituteVO.getIsEmpty()){
			if(emptyInfosSize > 0 ){
			    emptyMsg += "\\n\\n";
			}
			String emptyStartDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(substituteVO.getEmptyStartDate()));
			String emptyEndDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(substituteVO.getEmptyEndDate()));
			emptyMsg += messageSource.getMessage("list.list.msg.emptyInfo", null, langType).replace("%userName",userName).replace("%startDate",emptyStartDate).replace("%endDate",emptyEndDate);
			
			buff.append(emptyMsg);
			
	    }
	%>
		alert("<%=buff.toString()%>");
	<% 
		session.setAttribute("sessionRepresentativeYn","N");
	}
	%>
	
	// 파일 ActiveX 초기화
	initializeFileManager();
	$("#attachDiv").mouseleave(function(){
		
		if( $("#attachDivUse").val() == "Y"){
			$("#attachDivUse").val("N");
		}else{
			$('#attachDiv').hide();
		}
		
	});
});

var appDoc = null;
var Org_ViewUserInfo = null;

var curCompId = "<%=compId%>";
var userId = "<%=userId%>";


var curDocId = "";
var curElecYn = "";
var curLobCode = "";

//첨부파일 위치 변경
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

//결재대기함
function goApprovalWait(){
	parent.frames["left"].toggleMenu('Approval');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/approval/ListApprovalWaitBox.do','linkMenu_<%=boxCode103%>');
}

//결재진행함
function goApprovalProgress(){
	parent.frames["left"].toggleMenu('Approval');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/approval/ListProgressDocBox.do','linkMenu_<%=boxCode104%>');
}

//발송대기함
function goSendWait(){
	parent.frames["left"].toggleMenu('Send');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/send/ListSendWaitBox.do','linkMenu_<%=boxCode105%>');
}

//발송심사함
function goSendJudge(){
	parent.frames["left"].toggleMenu('Send');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/send/ListSendJudgeBox.do','linkMenu_<%=boxCode106%>');
}

//접수대기함
function goReceiveWait(){
	parent.frames["left"].toggleMenu('Receive');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/receive/ListReceiveWaitBox.do','linkMenu_<%=boxCode108%>');
	
}

//배부대기함
function goDistributionWait(){
	parent.frames["left"].toggleMenu('Receive');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/receive/ListDistributionWaitBox.do','linkMenu_<%=boxCode107%>');
	
}
//공람함
function goDisplay(){
	parent.frames["left"].toggleMenu('Complete');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/complete/ListDisplayBox.do','linkMenu_<%=boxCode112%>');
}
//완료함 by 서영락, 2016-01-14
function goComplete(){
	parent.frames["left"].toggleMenu('Complete');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/complete/ListApprovalCompleteBox.do','linkMenu_<%=boxCode112%>');
}
function fnc_openDoc(row){					//클릭 할 때와 더블 클릭 할 때를 구분하여 문서 선택을 진행한다. 160114 한동구 수정
	clearTimeout(timer);
	/* $("input:checkbox").eq(row).attr("checked",true); */
	gstrFormId = $("input:checkbox")[row];
	parent.fnc_openDoc();
}   
//결재완료함
function goApprovalComplete(){
	parent.frames["left"].toggleMenu('Complete');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/complete/ListApprovalCompleteBox.do','linkMenu_<%=boxCode110%>');
}

//임원문서함
function goOfficerBox(){
	parent.frames["left"].toggleMenu('Complete');
	parent.frames["left"].linkUrl('<%=webUri%>/app/list/complete/ListOfficerBox.do','linkMenu_<%=boxCode115%>');
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

//양식함  팝업
function lfn_formPop(){
	parent.frames["left"].toggleMenu('Draft');
	parent.frames["left"].linkUrl('draftPop','linkMenu_boxCode100');
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

//문서창 닫기
function closeAppDoc(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(appDoc != null && appDoc.closed == false) {
		appDoc.close();		
		
	}

}

//보안문서 열기 결과
function selectAppDocSecResut(result,type)
{
	if(result == true && type == "1")
		selectAppDoc(docId, lobCode, transferYn, elecYn, preNextYn);
}

//보안문서 열기
function selectAppDocSec(docid, lobcode, transferyn, elecyn, preNextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	docId = docid;
	lobCode = lobcode;
	transferYn = transferyn;
	elecYn = elecyn;
	preNextYn = preNextyn;
	var type = "1";
	if(securityYn == "Y" && isDuration == "true")
		opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?securitypass="+securityPass+"&type="+type+"&docId="+docId, 350, 160);
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
		curLobCode = lobCode;

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
			
		appDoc = openWindow("selectAppDocWin", linkUrl , width, height); 
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
		curLobCode = lobCode;
		
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
		curLobCode = lobCode;
		
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
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=yes,status=yes";

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
		curLobCode = lobCode;
			
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
	    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=yes,status=yes";

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

//목록 refresh
function listRefresh(){	
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

function screenBlock() {
    var top = ($(window).height() - 150) / 2;
    var left = ($(window).width() - 350) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}
//-->
</script>
