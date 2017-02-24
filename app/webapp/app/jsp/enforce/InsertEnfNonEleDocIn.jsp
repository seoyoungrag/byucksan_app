<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

String lobCode = (String) request.getParameter("lobCode");
%>
<script type="text/javascript">
var opinionWin; // 사유입력창
var linePopupWin; // 경로창 

//시행범위
var DET003 = '<%=appCode.getProperty("DET003", "DET003", "DET") %>';
var DET004 = '<%=appCode.getProperty("DET004", "DET004", "DET") %>';

//등록구분(문서형태)
var DTY001 = '<%=appCode.getProperty("DTY001", "DTY003", "DTY") %>';
var DTY002 = '<%=appCode.getProperty("DTY002", "DTY003", "DTY") %>';
var DTY003 = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';//사진, 필름류 시청각기록물
var DTY004 = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';//녹음, 동영상류 시청각기록물
var DTY005 = '<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>';

var DPI001 = '<%=appCode.getProperty("DPI001", "DPI001", "DPI") %>'; //생산문서
var DPI002 = '<%=appCode.getProperty("DPI002", "DPI002", "DPI") %>'; //접수문서

//열람범위
var DRS001 = '<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>';
var DRS002 = '<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>';
var DRS003 = '<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>';
var DRS004 = '<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>';   // jth8172 2012 신결재 TF
var DRS005 = '<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>';   // jth8172 2012 신결재 TF

var APT001 = '<%=appCode.getProperty("APT001", "APT001", "APT") %>'; //승인
var APT002 = '<%=appCode.getProperty("APT002", "APT002", "APT") %>'; //반려
var APT003 = '<%=appCode.getProperty("APT003", "APT003", "APT") %>'; // 대기 (처리유형)
var APT014 = '<%=appCode.getProperty("APT014", "APT014", "APT") %>'; // 부재미처리
var APT017 = '<%=appCode.getProperty("APT017", "APT017", "APT") %>'; // 담당자재지정요청
var APT018 = '<%=appCode.getProperty("APT018", "APT018", "APT") %>'; // 문서책임자재지정
var APT019 = '<%=appCode.getProperty("APT019", "APT019", "APT") %>'; // 관리자재지정

var ART060 = '<%=appCode.getProperty("ART060", "ART060", "ART") %>'; //선람
var ART070 = '<%=appCode.getProperty("ART070", "ART070", "ART") %>'; //담당

var ENF310 = '<%=appCode.getProperty("ENF310", "ENF310", "ENF") %>';

var RTS = new Array(); //문서종류 기타



var index = 0;

var reDistMoveYn = "N"; //이송, 재배부요청, 재배부 

var svrType = "0" //1 접수, 2 : 결재라인 설정 후 처리 요청

var gBtnNm = 0;

var bOrgAbbrName = false;

var closeNo = false; //닫기 허용여부

var messageType = 0; //2 : 접수

var insertType = 1;//1등록, 2접수

var bReqAcc = false; //담당접수여부

var bApprovalok = false; //담당
var bPrereadok = false; // 선람

$(window).unload(function() { closeChildWindow(); });

function setValiables(){
	RTS[0 ] = new Object();
	RTS[1 ] = new Object();
	RTS[2 ] = new Object();
	RTS[3 ] = new Object();
	RTS[4 ] = new Object();
	RTS[5 ] = new Object();
	RTS[6 ] = new Object();
	RTS[7 ] = new Object();
	RTS[8 ] = new Object();
	RTS[9 ] = new Object();
	RTS[10] = new Object();
	RTS[11] = new Object();
	RTS[12] = new Object();
	RTS[13] = new Object();
	RTS[14] = new Object();
	RTS[15] = new Object();
	RTS[16] = new Object();
	RTS[17] = new Object();
	RTS[18] = new Object();


	RTS[0 ].code = '<%=appCode.getProperty("CR", "CR", "RTS") %>';
	RTS[1 ].code = '<%=appCode.getProperty("CS", "CS", "RTS") %>';
	RTS[2 ].code = '<%=appCode.getProperty("CT", "CT", "RTS") %>';
	RTS[3 ].code = '<%=appCode.getProperty("CU", "CU", "RTS") %>';
	RTS[4 ].code = '<%=appCode.getProperty("CV", "CV", "RTS") %>';
	RTS[5 ].code = '<%=appCode.getProperty("CY", "CY", "RTS") %>';
	RTS[6 ].code = '<%=appCode.getProperty("DA", "DA", "RTS") %>';
	RTS[7 ].code = '<%=appCode.getProperty("DB", "DB", "RTS") %>';
	RTS[8 ].code = '<%=appCode.getProperty("DC", "DC", "RTS") %>';
	RTS[9 ].code = '<%=appCode.getProperty("DD", "DD", "RTS") %>';
	RTS[10].code = '<%=appCode.getProperty("DF", "DF", "RTS") %>';
	RTS[11].code = '<%=appCode.getProperty("DG", "DG", "RTS") %>';
	RTS[12].code = '<%=appCode.getProperty("DH", "DH", "RTS") %>';
	RTS[13].code = '<%=appCode.getProperty("DJ", "DJ", "RTS") %>';
	RTS[14].code = '<%=appCode.getProperty("DK", "DK", "RTS") %>';
	RTS[15].code = '<%=appCode.getProperty("DN", "DN", "RTS") %>';
	RTS[16].code = '<%=appCode.getProperty("DO", "DO", "RTS") %>';
	RTS[17].code = '<%=appCode.getProperty("DP", "DP", "RTS") %>';
	RTS[18].code = '<%=appCode.getProperty("DQ", "DQ", "RTS") %>';

	RTS[0 ].name = '<spring:message code="approval.form.rts.cr" />';
	RTS[1 ].name = '<spring:message code="approval.form.rts.cs" />';
	RTS[2 ].name = '<spring:message code="approval.form.rts.ct" />';
	RTS[3 ].name = '<spring:message code="approval.form.rts.cu" />';
	RTS[4 ].name = '<spring:message code="approval.form.rts.cv" />';
	RTS[5 ].name = '<spring:message code="approval.form.rts.cy" />';
	RTS[6 ].name = '<spring:message code="approval.form.rts.da" />';
	RTS[7 ].name = '<spring:message code="approval.form.rts.db" />';
	RTS[8 ].name = '<spring:message code="approval.form.rts.dc" />';
	RTS[9 ].name = '<spring:message code="approval.form.rts.dd" />';
	RTS[10].name = '<spring:message code="approval.form.rts.df" />';
	RTS[11].name = '<spring:message code="approval.form.rts.dg" />';
	RTS[12].name = '<spring:message code="approval.form.rts.dh" />';
	RTS[13].name = '<spring:message code="approval.form.rts.dj" />';
	RTS[14].name = '<spring:message code="approval.form.rts.dk" />';
	RTS[15].name = '<spring:message code="approval.form.rts.dn" />';
	RTS[16].name = '<spring:message code="approval.form.rts.do" />';
	RTS[17].name = '<spring:message code="approval.form.rts.dp" />';
	RTS[18].name = '<spring:message code="approval.form.rts.dq" />';


	
}

//현재 안건번호
function getCurrentItem() {
	return 1;
}

<%-- 접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
//관련문서 리턴
function setRelatedDoc(docInfo){
	var infolist = getRelatedDocList(docInfo)
	setRe1DocList(infolist);	
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 리턴(이동시 사용)
function setRelatedDoc_move(docInfo,checkId){	
	var infolist = getRelatedDocList(docInfo)

	setRe1DocList_move(infolist,checkId);	
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 데이터 추출
function getRelatedDocList(docInfo) {
	var infolist = new Array();
	var infos = docInfo.split(String.fromCharCode(4));
	var infolength = infos.length;
	for (var loop = 0; loop < infolength; loop++) {
		if (infos[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = infos[loop].split(String.fromCharCode(2));
			var infoObj = new Object();
			infoObj.docId = info[0];
			infoObj.title = info[1];
			infoObj.usingType = info[2];
			infoObj.electronYnValue = info[3];
			infolist[loop] = infoObj;
		}
	}

	return infolist;
}

//관련문서정보리턴
function getRelateDocinfo(){
	var docInfos = $('#tbRelDoc tbody tr');
	var rtnStr = "";
	
	for(var i = 0; i < docInfos.length; i++){
		var docInfo = docInfos[i];
		rtnStr += docInfo.getAttribute('docId');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('title');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('usingType');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('electronYnValue');
		rtnStr += String.fromCharCode(4);
	}

	return rtnStr;
}

--%>
/**
 * 선람 담당 결재라인 넘기기
 */
function getEnfLine(){
	return $('#enfLines').val();
	
}

/**
 * 선람 담당 결재라인 받기
 */
function setEnfLine(enfLine){
	$('#enfLines').val(enfLine);
	drawEnfLines();

	if(svrType === "2"){
		svrType = "3";
		setTimeout(function(){ setProcessAfter();},50);
	}
}


//재배부 대상 부서 선택
function reDistribute() {
	reDistMoveYn = "Y";
	goRecvDept();
}

//이송
function move() {
	// 이송대상부서 선택(대외문서는 ref, 대내문서는 recv로 지정됨)
	reDistMoveYn = "M";
	goRecvDept();
	
}

//의견조회팝업
function viewOpinion(title, opinion) {
	var width = 400;
	var height = 260;
	var appDoc = null;
	var url = "";
	appDoc = openWindow("popupWin", url, width, height); 
	
		
	$("#popupTitle").val(title);
	$("#popupOpinion").val(opinion);
	$('#frm').attr("target","popupWin");
	$('#frm').attr("action","<%=webUri%>/app/enforce/viewOpinion.do");
	$('#frm').submit();
}

//문서정보조회창용 스크립트
function getAppLine() {
	return $("#enfLines", "#frm").val();
}

//문서정보조회창용 스크립트
function getAppRecv() {
	var recv = new Object();
	recv.appRecv = $("#appRecv", "#frm").val();
	recv.displayNameYn = $("#displayNameYn", "#frm").val();
	recv.receivers = $("#receivers", "#frm").val();

	return recv;
}

function modifyPopup(){
	var frm = $('#frm');
	$('#method').val("0");
	var url = "<%=webUri%>/app/enforce/updateEnfNonElecDoc.do"
	frm.attr("target","_self");
	frm.attr("action",url);
	frm.submit();
}

//이전문서/다음문서
var prevnext = false;

function moveToPrevious(chkPrenext) {
	if (typeof(chkPrenext) == "undefined") {
		chkPrenext = "N";
	}
	
    prevnext = true;
    
	try {
    	if (opener != null && opener.getPreNextDoc != null) {
	        var docId = $('#docId').val();   
	
	        var message = opener.getPreNextDoc(docId, "pre");
	
	        if (message != null && message != "") {
	        	if(chkPrenext == "Y"){
					prevnext = false;
				}
	            alert(message);
	        }
	    }
	} catch(e) {}
}

function moveToNext(chkPrenext) {
	if (typeof(chkPrenext) == "undefined") {
		chkPrenext = "N";
	}
    prevnext = true;

	try {
	    if (opener != null && opener.getPreNextDoc != null) {
	        var docId = $('#docId').val();
	
	        var message = opener.getPreNextDoc(docId, "next");
	
	        if (message != null && message != "") {
	        	if(chkPrenext == "Y"){
					prevnext = false;
				}
	            alert(message);
	        }
	    }
	} catch(e) {}
}

//반려처리
function procRejectDoc(opinion) {
	$('#opinion').val(opinion);
    $.post("<%=webUri%>/app/enforce/processEnfDocReject.do", $('#frm').serialize(), function(data){
        if (data.result =="OK") {
        	afterButton();
        }else{
            alert("<spring:message code='env.form.msg.fail'/>"+data.result);
        }
    },'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='env.form.msg.fail'/>");
		}
	});		 
}

//문서회수
function procRetrieveDoc() {
    $.post("<%=webUri%>/app/enforce/processDocRetrieve.do", $("#frm").serialize(), function(data){
        if (data.result=="OK") {
   //     	alert("<spring:message code='enforce.msg.retrievedocok'/>");
        	afterButton();
        }else{
            alert("<spring:message code='env.form.msg.fail'/>"+data.result);
        }
    },'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='env.form.msg.fail'/>");
		}
	});		
}

//공람처리
function pubreadAppDoc() {
	pubreadAppDocProc();
}

function setCancel(){
	$('#method').val("0");
	$('#frm').attr("target","_self");
	$('#frm').attr("action","<%=webUri%>/app/enforce/insertEnfNonElecDoc.do");
	$('#frm').submit();
}

//문서를 열때마다 열람일자를 set한다. 결재대기함에서 결재자가 문서를 열때 호출한다.
function setReadDate(){
    $.post("<%=webUri%>/app/enforce/updateReadDate.do", $("#frm").serialize(), function(data){
        
    }, 'json');
}

//담당자 재지정
var bReEnfLine = false;
function processorRefix(){
	var enfLines = $('#enfLines').val();
	var rex = /<%=appCode.getProperty("APT003", "APT003", "APT") %>/g;
	enfLines = enfLines.replace(rex,'');
	$('#enfLines').val(enfLines);
	bReEnfLine = true;
	svrType = "2"
	messageType = 3; 
	setProcess(30);
}

<%--  접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
//관련문서 팝업
function goRecords(){
	var width = 800;
	var height = 480;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRelationDocRegist.do";
	appDoc = openWindow("relDocs", url, width, height); 
}

function removeRecods(){
	var existDoc = $('input[group=related]:checked');
	existDoc.parent().parent().remove();
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 목록
function setRe1DocList(infolist){
	var tbRelDoc = $('#tbRelDoc tbody');
		
	for(var i = 0; i < infolist.length; i++){
		var info = infolist[i];
		var existDoc = $('input[group=related][docId='+info.docId+']');
		if (existDoc.length == 0){		
			var row = "";
			var img = "";
			var docId = $('#docId').val(); 
			row += "<tr docId='"+info.docId+"' title='"+escapeJavaScript(info.title)+"' usingType='"+info.usingType+"' electronYnValue='"+info.electronYnValue+"'>";
			if(docId ==""){				
				row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			}
			if(DPI001 === info.usingType){
				img = "[<spring:message code="bind.code.DPI001" />]";
			}else{
				img = "[<spring:message code="bind.code.DPI002" />]";
			}
			row += ("<td width='36' class='ltb_left'>" + img + "</td>");
			if(DPI001 === info.usingType){
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}else{
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}
			
			row += "</tr>";
	
			tbRelDoc.append(row);
		}
	}
}

//관련문서 목록(이동시 사용)
function setRe1DocList_move(infolist,checkId){	
	var tbRelDoc = $('#tbRelDoc tbody');
	tbRelDoc.children().remove();	
	for(var i = 0; i < infolist.length; i++){
		var info = infolist[i];
		var existDoc = $('input[group=related][docId='+info.docId+']');
		if (existDoc.length == 0){		
			var row = "";
			var img = "";
			
			row += "<tr docId='"+info.docId+"' title='"+escapeJavaScript(info.title)+"' usingType='"+info.usingType+"' electronYnValue='"+info.electronYnValue+"'>";
				
			if(checkId == info.docId){
				row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"' checked/></td>");
			}else{			
				row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			}
			
			if(DPI001 === info.usingType){
				img = "[<spring:message code="bind.code.DPI001" />]";
			}else{
				img = "[<spring:message code="bind.code.DPI002" />]";
			}
			row += ("<td width='36' class='ltb_left'>" + img + "</td>");
			if(DPI001 === info.usingType){
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}else{
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}
			
			row += "</tr>";
	
			tbRelDoc.append(row);
		}
	}
}

//관련문서 이동 실행
function moveRelateDocResult(id,opt){	
	var docInfos = $('#tbRelDoc tbody tr');	
	var docId = id.replace("relateDoc_cid_", "");	
	var rtnStr = "";	
	var rtnArray = new Array(docInfos.length);
	if(opt=="down"){		
		for(var i = docInfos.length-1; i > -1; i--){		
			var docInfo = docInfos[i];
			var tempStr ="";
			tempStr += docInfo.getAttribute('docId');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('title');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('usingType');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('electronYnValue');
			tempStr += String.fromCharCode(4);
					
			if(docId == docInfo.getAttribute('docId')){
				if(i != docInfos.length - 1){
					rtnArray[i] = rtnArray[i+1]; 				
					rtnArray[i+1] = tempStr;
				}else{
					alert(FileConst.Error.NoPlaceToMove);
					return false;
				}
			}else{			
				rtnArray[i] = tempStr;
			}		
		}
	}else{		
		for(var i = 0; i < docInfos.length; i++){
			var docInfo = docInfos[i];
			var tempStr ="";
			tempStr += docInfo.getAttribute('docId');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('title');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('usingType');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('electronYnValue');
			tempStr += String.fromCharCode(4);
					
			if(docId == docInfo.getAttribute('docId')){
				if(i !=0){
					rtnArray[i] = rtnArray[i-1]; 				
					rtnArray[i-1] = tempStr;
				}else{
					alert(FileConst.Error.NoPlaceToMove);
					return false;
				}
			}else{			
				rtnArray[i] = tempStr;
			}		
		}
	}	
	for(var j = 0; j < rtnArray.length; j++){		
		rtnStr +=rtnArray[j];
	}	
	setRelatedDoc_move(rtnStr,docId);
}

//관련문서 위로 이동
function moveUpRelateDoc(){
	var count = selectedCheckDoc("relateDoc_cname");

	if (count == 0) {
		alert("<spring:message code='approval.msg.noRelatedDocselected'/>");		
		return;
	} else if (count > 1) {
		alert("<spring:message code='approval.msg.selectonlyoneRelatedDoc'/>");		
		return;
	}

	moveRelateDocResult(selectedid,"up");
}

//관련문서 아래로 이동
function moveDownrelateDoc(){
	var count = selectedCheckDoc("relateDoc_cname");

	if (count == 0) {
		alert("<spring:message code='approval.msg.noRelatedDocselected'/>");		
		return;
	} else if (count > 1) {
		alert("<spring:message code='approval.msg.selectonlyoneRelatedDoc'/>");		
		return;
	}
	
	moveRelateDocResult(selectedid,"down");
}

function selectedCheckDoc(check_name) {
	var count = 0;
	var checkboxes = document.getElementsByName(check_name);
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = 0; loop < checkboxes.length; loop++) {
			if (checkboxes[loop].checked) {
				selectedid = checkboxes[loop].id;
				count++;
			}
		}
	}

	return count;
}
--%>
function openAppDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonAppDoc(docId){
	var url = "<%=webUri%>/app/approval/selectProNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}

function openEnfDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonEnfDoc(docId){
	var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}

//생산문서 팝업
var proSerialDoc = null;
function goProSerial(){
	var width = 800;
	var height = 450;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRowRankDocRegist.do?searchDocType=ALL";

	if(!confirm('<spring:message code="approval.msg.addsubserialnumber" />')) return;
	
	proSerialDoc = openWindow("serial", url, width, height); 
}

//생산문서 번호
function setProSerial(serialInfo){
	var infos = serialInfo.split(String.fromCharCode(4))
	var infolength = infos.length;
	if(infolength > 0){
		if (infos[0].indexOf(String.fromCharCode(2)) != -1) {
			var info = infos[0].split(String.fromCharCode(2));
			$('#DeptCategory').val(info[0]);
			$('#SerialNum').val(info[1]);
			$('#divSerialValue').html(info[0]+"-"+info[1]);

			$('#bindNm').val(info[6]);
			$('#bindId').val(info[5]);
			$('#btnBind').hide();

			$('#divBind').hide();
		}
	}
}

//편철 및 생산번호 초기화
function delProSerial(){
	initBind();
}

//접수부서
var goUserDoc = null;
function goRecvDept(){
	var width = 500;
	var height = 310;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=3";
	goUserDoc = openWindow("dept", url, width, height); 
}

//접수처결과
function setDeptInfo(deptInfo){
	if(deptInfo.isProcess){//처리과여부
		$('#refDeptId').val(deptInfo.orgId);
		$('#refDeptName').val(deptInfo.orgName);
	}else{//처리과가 아님
		
		$('#refDeptId').val("");
		$('#refDeptName').val("");
		return '<spring:message code="approval.result.msg.noisprocess" />';		
	}

	if("Y" === reDistMoveYn){		
		setTimeout(function(){ reDistributeOk(); }, 100);
	}else if("M" === reDistMoveYn){
		moveOk();
	}

	return "";
}

function screenBlock() {
    var top = ($(window).height() - 120) / 2;
    var left = ($(window).width() - 340) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

function closeChildWindow(){
    if(bindDoc != null){
    	bindDoc.close();
    }

    if(proSerialDoc != null){
    	proSerialDoc.close();
    }

    if(goUserDoc != null){
    	goUserDoc.close();
    }
}

//------------event-------------------------------------------
//시행범위 onchange event
function enfoce_change(theObj){
	if(theObj.val() === DET003 || theObj.val() === DET004){
		$('#divEnforce').show();
	}else{
		$('#divEnforce').hide();
	}

	$('input:checkbox[group=det]').attr('checked',false);
	$('#enfTarget').val("");
}

//시행범위 기타
function enfTarget_click(theObj){
	var enfTarget = $('#enfTarget');

	var rdDets = $('input:checkbox[group='+theObj.attr('group')+']');
	var enValue = "";
	var enCnt = rdDets.length;
	for(var i = 0; i < enCnt; i++){
		if(rdDets[i].checked){
			enValue += "Y";
		}else{
			enValue += "N";
		}
	}
	enfTarget.val(enValue);
}

//문서형태(등록구분) 이벤트
function apDty_change(theObj){
	var div_rectype = $('#div_rectype');
	var div_rectype_c = $('#div_rectype_c');
	var div_rectype_d = $('#div_rectype_d');
	if(theObj.val() === DTY003){
		div_rectype.show();
		div_rectype_c.show();
		div_rectype_d.hide();
		$('document').height(300);
	}else if(theObj.val() === DTY004){
		div_rectype.show();
		div_rectype_c.hide();
		div_rectype_d.show();
	}else{
		div_rectype.hide();
		div_rectype_c.hide();
		div_rectype_d.hide();
	}
	
	$('input:checkbox[group=rectype]').attr('checked',false);
	$('#rectype').val("");
}

function rectype_click(theObj){
	var rectype = $('#rectype');

	var rdRectype = $('input:checkbox[group='+theObj.attr('group')+']');
	var rectypeValue = "";
	var rectypeCnt = rdRectype.length;
	for(var i = 0; i < rectypeCnt; i++){
		if(rdRectype[i].checked){
			rectypeValue += rdRectype[i].value;
			if(i < (rectypeCnt-1)){
				rectypeValue += ",";
			}
		}
	}
	rectype.val(rectypeValue);
}

function callRefreshPortlet(){
	//각 사이트에 맞게 구성
}

var docKindDoc = null;
//문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//문서번호 셋팅(문서분류 사용시), jd.park, 20120509
function setDeptCategory(deptCategory){
	$('#DeptCategory').val(deptCategory);
	$('#divSerialValue').html(deptCategory);
}


//문서분류 셋팅
function setDocKind(docKind){

	$('#classNumber').val(docKind.classificationCode);
	$('#docnumName').val(escapeJavaScript(docKind.unitName));

	var divValue = $('#classNumber').val()+" ["+$('#docnumName').val()+"]";
	
	$('#divDocKind').html(divValue);
}

//문서분류 초기화
function docKindInit(){
	$('#classNumber').val("");
	$('#docnumName').val("");

	$('#divDocKind').html("");

	$('#DeptCategory').val("${OrgAbbrName}");
	$('#divSerialValue').html("${OrgAbbrName}");
}

// 기본사항 체크
function chkDefaultValues(){
	
	if("" === $.trim($('#title').val())){
		alert('<spring:message code="approval.msg.notitle" />');
		$('#title').focus();
		return false;
	}
	
	if("" === $('#draftId').val()){
		alert('<spring:message code="approval.msg.nodrafter" />');
		return false;
	}

	if("" === $.trim($('#sendOrgName').val())){ //발신기관명
		alert('<spring:message code="approval.msg.nosendorgname" />');
		$('#sendOrgName').focus();
		return false;
	}

	if("" === $.trim($('#docNumber').val())){ //생산등록번호
		alert('<spring:message code="approval.msg.nopronum" />');
		$('#docNumber').focus();
		return false;
	}

	return true;
}

function goCertificateProcess(goFunction, btnNm, opinionYn){
<% if ("1".equals(opt301)) { %>		
		popOpinion(goFunction, btnNm, opinionYn);
<% } else if ("2".equals(opt301)) { %>
		$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
			if (data.validation == "Y") {
				eval(goFunction+"();")
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			} else {
				if (certificate()) {
					eval(goFunction+"();")
					$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				}
			}
		}, 'json').error(function(data) {
			if (certificate()) {
				eval(goFunction+"();")
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			}
		});
<% } else { %>
	eval(goFunction+"();")
<% } %>

}

function closeBeforePop(){
	try{
		if (!prevnext) { 
			if (opener != null && opener.listRefresh != null 
					&& opener.curLobCode != null && opener.curLobCode == '<%=lobCode%>') {
			    opener.listRefresh();
			}
		}

	} catch(error) { }	
}

function closePopup(){	
	closeBeforePop();
	window.close();
}
</script>