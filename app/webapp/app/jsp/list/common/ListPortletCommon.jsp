<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>

<%
/** 
 *  Class Name  : ListPortletCommon.jsp 
 *  Description : 리스트 portlet 공통 javascript 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2012. 03. 09 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
response.setHeader("pragma","no-cache");

String userId	= (String) session.getAttribute("USER_ID");	// 사용자 ID
String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID

String resultLobCode = CommonUtil.nullTrim((String)request.getAttribute("lobCode"));

%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--
var appDoc = null;

var curCompId = "<%=compId%>";
var userId = "<%=userId%>";


var curDocId = "";
var curElecYn = "";
var curLobCode = "<%=resultLobCode%>";

var curTransferYn = "";
var curPreNextYn = "";
var curPublishId = "";

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
	if(result == true && type == "1"){//대기함
		selectAppDoc(curDocId, curLobCode, curTransferYn, curElecYn, curPreNextYn);
	}	
}

//보안문서 열기
function selectAppDocSec(docid, lobcode, transferyn, elecyn, preNextyn, securityYn, securityPass, isDuration) {
	var opinionWin = "";
	curDocId = docid;
	curLobCode = lobcode;
	curTransferYn = transferyn;
	curElecYn = elecyn;
	curPreNextYn = preNextyn;
	var type="1";
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

		var width = "";
		width = 830;
		
		
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
		if(lobCode == "LOB005"){
			linkUrl = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}else{
			linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}

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
		
		var width = "";		
		width = 830;
	
		
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

function selectCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn) {	
	var isPop = false;
		
	if(preNextYn == "N"){	
		isPop = isPopOpen();
		openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, isPop);
	}else{
		closeAppDoc();
		setTimeout(function() { openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, true); }, 100);
	}

}

//접수대기함 문서호출 20120613 add
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

function openCompAppDoc(compId, docId, lobCode, transferYn, elecYn, preNextYn, isPop) {
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

		appDoc = openWindow("selectSenderAppDocWin", linkUrl , width, height); 
		
	}

}

//접수대기함 문서호출 20120613 add
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
