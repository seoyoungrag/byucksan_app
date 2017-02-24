<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : approval.jsp 
 *  Description : 결재공통처리 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
%>
<script type="text/javascript">
// 이전문서/다음문서
var prevnext = false;
var docLinkedWin = null;
var mailWin = null;
var g_itemnum = 0;//안건별 보안 패스워드 적용을 위해 //jkkim 20120612

function moveToPrevious() {
	prevnext = true;
	if (opener != null && opener.getPreNextDoc != null) {
		var itemnum = getCurrentItem();
		var docId = $("#docId", "#approvalitem" + itemnum).val();	
		var message = opener.getPreNextDoc(docId, "pre");
		if (message != null && message != "") {
			alert(message);
		}
	}
}

function moveToNext() {
	prevnext = true;
	if (opener != null && opener.getPreNextDoc != null) {
		var itemnum = getCurrentItem();
		var docId = $("#docId", "#approvalitem" + itemnum).val();	
		var message = opener.getPreNextDoc(docId, "next");
		if (message != null && message != "") {
			alert(message);
		}
	}
}

// 관련문서
function selectRelatedDoc() {
	if ($("#draftType").val() == "reference" || $("#draftType").val() == "redraft-all") {
		relatedDocWin = openWindow("relatedDocWin", "<%=webUri%>/app/approval/selectRelatedDoc.do?lobCode=<%=lob000%>", 800, 680);
	} else {
		relatedDocWin = openWindow("relatedDocWin", "<%=webUri%>/app/approval/selectRelatedDoc.do?lobCode=" + $("#lobCode").val(), 800, 680);
	}
}

function getRelatedDoc() {
	return $("#relatedDoc", "#approvalitem" + getCurrentItem()).val();
}

//관련문서 설정
function setRelatedDoc(relateddoc) {
	$("#relatedDoc", "#approvalitem" + getCurrentItem()).val(relateddoc);
	putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedDoc, getDisplayRelatedDoc(relateddoc));

	loadRelatedDoc(relateddoc);	//관련문서가 있는경우 하단부분 새로고침
}

//관련문서 설정(checkbox 활용), jd.park, 201205018
function setRelatedDoc_check(relateddoc) {
	$("#relatedDoc", "#approvalitem" + getCurrentItem()).val(relateddoc);
	putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedDoc, getDisplayRelatedDoc(relateddoc));

	loadRelatedDoc_check(relateddoc,"");	//관련문서가 있는경우 하단부분 새로고침
}

//관련문서 설정(checkbox 활용 및 이동), jd.park, 201205023
function setRelatedDoc_move(relateddoc, docId) {
	$("#relatedDoc", "#approvalitem" + getCurrentItem()).val(relateddoc);
	putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedDoc, getDisplayRelatedDoc(relateddoc));

	loadRelatedDoc_check(relateddoc,docId);	//관련문서가 있는경우 하단부분 새로고침
}

//관련문서 새로고침
function loadRelatedDoc(relateddoc) {
	var tbRelatedDocs = $('#tbRelatedDocs tbody');

	var relatedlist = new Array();
	if (relateddoc instanceof Array) {
		relatedlist = relateddoc;
	} else {	
		relatedlist = getRelatedDocList(relateddoc);
	}
	var relatedlength = relatedlist.length;
	tbRelatedDocs.children().remove();
	for (var loop = 0; loop < relatedlength; loop++) {
		var row = makeRelatedDoc(relatedlist[loop].docId, relatedlist[loop].title, relatedlist[loop].usingType, relatedlist[loop].electronDocYn);
		tbRelatedDocs.append(row);
	}
}

//관련문서 새로고침(체크박스 활용시), jd.park, 201205018
function loadRelatedDoc_check(relateddoc,checkId) {
	var tbRelatedDocs = $('#tbRelatedDocs tbody');

	var relatedlist = new Array();
	if (relateddoc instanceof Array) {
		relatedlist = relateddoc;
	} else {	
		relatedlist = getRelatedDocList(relateddoc);
	}
	var relatedlength = relatedlist.length;
	tbRelatedDocs.children().remove();
	for (var loop = 0; loop < relatedlength; loop++) {
		var row ="";
		if(checkId == relatedlist[loop].docId){
			row = makeRelatedDoc_check(relatedlist[loop].docId, relatedlist[loop].title, relatedlist[loop].usingType, relatedlist[loop].electronDocYn, "checked");
		}else{
			row = makeRelatedDoc_check(relatedlist[loop].docId, relatedlist[loop].title, relatedlist[loop].usingType, relatedlist[loop].electronDocYn, "");
		}		
		tbRelatedDocs.append(row);
	}
}

//관련문서 목록 만들기 
function makeRelatedDoc(docId, title, usingType, electronDocYn) {	
	var row = "";
	row += "<tr bgcolor='#ffffff' docId='"+docId+"' title='"+escapeJavaScript(title)+"' usingType='"+usingType+"' electronYnValue='"+electronDocYn+"'>";
	var securityYn = getSecurityYn();
	var isDuration = getIsDuration();	
	if($("#lobCode").val()=="<%=lob000%>" || $("#lobCode").val()=="<%=lob003%>"){
		row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+docId+"' name='relateDoc_cname' group='related' docId='"+docId+"' /></td>");
	}	
	if (usingType == "<%=dpi001%>") {
		if (electronDocYn == "N") {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
		} else {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
		}
	} else {
		if (electronDocYn == "N") {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
		} else {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
		}
	}
	row += "<td class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\", \"" + securityYn + "\", \"" + isDuration + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "</tr>";	
	return row;
}

//관련문서 목록 만들기 수정(check box 추가), jd.park, 20120518;
function makeRelatedDoc_check(docId, title, usingType, electronDocYn, checkYn) {	
	var row = "";
	row += "<tr bgcolor='#ffffff' docId='"+docId+"' title='"+escapeJavaScript(title)+"' usingType='"+usingType+"' electronYnValue='"+electronDocYn+"'>";
	var securityYn = getSecurityYn();
	var isDuration = getIsDuration();	
	row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+docId+"' name='relateDoc_cname' group='related' docId='"+docId+"' "+checkYn+" /></td>");		
	if (usingType == "<%=dpi001%>") {
		if (electronDocYn == "N") {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
		} else {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
		}
	} else {
		if (electronDocYn == "N") {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
		} else {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
		}
	}
	row += "<td class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\", \"" + securityYn + "\", \"" + isDuration + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "</tr>";	
	return row;
}

//관련문서 체크 삭제, jd.park, 20120518;
function removeRelatedDoc(){	
	var existDoc = $('input[group=related]:checked');
	existDoc.parent().parent().remove();
	setRelatedDoc_check(getRelateDocinfo());	
}

//본문 관련문서 다시 가져오기, jd.park, 20120518;
function getRelateDocinfo(){
	var docInfos = $('#tbRelatedDocs tbody tr');
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

//관련문서 이동 실행
function moveRelateDocResult(id,opt){	
	var docInfos = $('#tbRelatedDocs tbody tr');
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
/**
 * 감사대상 원문서 오픈 메소드
 * added by jkkim
 * 2012.07.20
 */
function selectOriginAppDoc() {
	var docId = $("#docId").val();
	var usingType = "DPI001";
	var securityYn = "N";
	var isDuration = false;
	var electronDocYn = "Y";
	//alert(docId+","+usingType+","+electronDocYn+","+securityYn+","+isDuration);
	if ((arguments.length == 5) && (securityYn == "Y") && (isDuration == "true")) {
		var orginDocId = getDocId();
		insertDocPassword4RelatedDoc(orginDocId, docId, usingType, electronDocYn);
		return;
	}

	var isPop = isPopOpen();
	 
	if (isPop) {
		// 전자문서
		var width = 1200;	
		if (screen.availWidth < 1200) {	
		    width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		
		// 비전자문서
		if (electronDocYn == "N") {
		    width = 800;
		    if (screen.availWidth < 800) {
		        width = screen.availWidth;
		    }
		    height = 650;
			if (screen.availHeight < 650) {
				height = screen.availHeight;
			}
			option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
		}

		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var linkUrl;
		linkUrl = "<%=webUri%>"+"/app/approval/selectAppDoc.do?docId="+docId+"&call=auditorigindoc";
		docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
		
			
		closeChildWindow("N");
	}
}

function selectRelatedAppDoc(docId, usingType, electronDocYn, securityYn, isDuration) {
	//alert(docId+","+usingType+","+electronDocYn+","+securityYn+","+isDuration);
	if ((arguments.length == 5) && (securityYn == "Y") && (isDuration == "true")) {
		var orginDocId = getDocId();
		insertDocPassword4RelatedDoc(orginDocId, docId, usingType, electronDocYn);
		return;
	}

	var isPop = isPopOpen();
	 
	if (isPop) {
		// 전자문서
		var width = 1200;	
		if (screen.availWidth < 1200) {	
		    width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		
		// 비전자문서
		if (electronDocYn == "N") {
		    width = 800;
		    if (screen.availWidth < 800) {
		        width = screen.availWidth;
		    }
		    height = 650;
			if (screen.availHeight < 650) {
				height = screen.availHeight;
			}
			option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
		}

		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var linkUrl;
		if (usingType == "<%=dpi001%>") {
			if (electronDocYn == "N") {
				linkUrl = "<%=webUri%>"+"/app/approval/selectProNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>"+"/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		} else {
			if (electronDocYn == "N") {
				linkUrl = "<%=webUri%>"+"/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>"+"/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		}		
			
		closeChildWindow("N");
	}
}

function insertDocPassword4RelatedDoc(docId, relatedDocId, usingType, electronDocYn)
{
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=relateddoc&docId="+docId+"&relatedDocId="+relatedDocId+"&usingType="+usingType+"&electronDocYn="+electronDocYn, 350, 160);
}

//문서암호 확인
function insertDocPassword4Attach(attachId)
{
	var docId = $("#docId", "#approvalitem" + getCurrentItem()).val();
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=attach&docId="+docId+"&attachId="+attachId, 350, 160);
}

//문서암호 확인 - 안건별 본문
function insertDocPassword4Body(strDocId,itemnum)
{
	g_itemnum = itemnum;
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?docId="+strDocId, 350, 160);
}

//보안문서 열기 결과 -안건별 본문
function selectAppDocSecResut(result,type)
{
	if(result == true){
		selectTabDoc(g_itemnum);
	}
}

function isPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){			
			//20140724 관련문서 창 종료 변경 kj.yang
			//relatedDoc.close();
			if (relatedDocWin.ChildCloseAppDoc != null) {
				relatedDocWin.ChildCloseAppDoc();
			}
			
			relatedDocWin.close();	
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
}

// 관련규정
function selectRelatedRule() {
	if ($("#draftType").val() == "reference" || $("#draftType").val() == "redraft-all") {
		relatedRuleWin = openWindow("relatedRuleWin", "<%=webUri%>/app/approval/selectRelatedRule.do?lobCode=<%=lob000%>", 600, 600);
	} else {
		relatedRuleWin = openWindow("relatedRuleWin", "<%=webUri%>/app/approval/selectRelatedRule.do?lobCode=" + $("#lobCode").val(), 600, 600);
	}
}

function getRelatedRule() {
	return $("#relatedRule", "#approvalitem" + getCurrentItem()).val();
}

function setRelatedRule(relatedrule) {
	$("#relatedRule", "#approvalitem" + getCurrentItem()).val(relatedrule);
	putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedRule, getDisplayRelatedRule(relatedrule));
}

// 거래처
function selectCustomer() {
	if ($("#draftType").val() == "reference" || $("#draftType").val() == "redraft-all") {
		customerWin = openWindow("customerWin", "<%=webUri%>/app/approval/selectCustomer.do?lobCode=<%=lob000%>", 900, 680);
	} else {
		customerWin = openWindow("customerWin", "<%=webUri%>/app/approval/selectCustomer.do?lobCode=" + $("#lobCode").val(), 900, 680);
	}
}

function getCustomer() {
	return $("#customer", "#approvalitem" + getCurrentItem()).val();	
}

function setCustomer(customer) {
	$("#customer", "#approvalitem" + getCurrentItem()).val(customer);
	putFieldText(Document_HwpCtrl, HwpConst.Field.Customer, getDisplayCustomer(customer));
}

// 공람자
function selectPubReader() {
	pubreaderWin = openWindow("pubreaderWin", "<%=webUri%>/app/approval/ApprovalPubReader.do", 650, 570);
}

function getPubReader() {
	var itemnum = getCurrentItem();
	return $("#pubReader", "#approvalitem" + itemnum).val();
}

function setPubReader(pubreader) {
	var itemnum = getCurrentItem();
	$("#pubReader", "#approvalitem" + itemnum).val(pubreader);
}

function listPubReader() {
	var itemnum = getCurrentItem();
	var param = "docId=" + $("#docId", "#approvalitem" + itemnum).val() + "&lobCode=" + $("#lobCode").val();
	var listPubReaderWin = openWindow("listPubReaderWin", "<%=webUri%>/app/appcom/listPubReader.do?" + param, 650, 450, "auto"); 
}

// 요약전
function insertSummary() {
	summaryWin = openWindow("summaryWin", "<%=webUri%>/app/approval/createSummary.do", 600, 500);
}

function getSummary() {
	var itemnum = getCurrentItem();
	return $("#summary", "#approvalitem" + itemnum).val();	
}

function setSummary(summary) {
	var itemnum = getCurrentItem();
	$("#summary", "#approvalitem" + itemnum).val(summary);
}


// 저장
// modified jkkim bodyType 2013.04.25
function saveAppDoc(filepath) {
	var itemnum = getCurrentItem();
	if (bodyType == "hwp" || bodyType == "doc") {
		var filename = "";
		if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
			filename =  escapeFilename($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)) + "."+bodyType);
		}
		if (filename == "") {
			filename = escapeFilename($("#title", "#approvalitem" + itemnum).val() + "."+bodyType);
		}
		FileManager.setExtension(bodyType);
		// 본문저장 시 경로값이 없고 본문/첨부 저장 시 경로값이 셋팅되어 들어온다.
		if (typeof(filepath) == "undefined") {
			filepath = FileManager.selectdownloadpath(filename);
		} else if(filepath == "-1") {	//본문/첨부 저장 취소 시
			return false;
		} else {
			filepath += "\\" + filename;
		}
		
		if (filepath != "") {
			// 현재파일 백업
			arrangeBody(Document_HwpCtrl, itemnum, false);
			var transferYn = $("#transferYn", "#approvalitem" + itemnum).val();
			if (transferYn == "N") {
				// 서명정보를 제거한다. 서명 정보를 왜 제거하는지 모르겠지만 요청에 의해 제거하지 않도록 한다.
				//clearApprover(Document_HwpCtrl);
				// 관인(서명인)/생략인정보를 제거한다.
				//clearStamp(Document_HwpCtrl);
			} else { // 이관 후 문서가 어떻게 변경되는지 잘 모르겠네...
				clearApproverSNT(Document_HwpCtrl);
				clearStampSNT(Document_HwpCtrl);
			}
			// 문서 처음으로 이동하기
			moveToPos(Document_HwpCtrl, 2);
	
			if (saveHwpDocument(Document_HwpCtrl, filepath)) {
				if(bodyType == "hwp"){
					reloadBody(itemnum);
				}
				alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", filepath));

				//20160705_syr 문서저장할때 이력을 남김
				var docId = $("#docId", "#approvalitem" + itemnum).val();	
				$.ajaxSetup({async:false});
				$.post("<%=webUri%>/app/approval/saveAppDocHis.do", "docId=" + docId, function(data) {
					var rs = 0;
					if(data.result && data.result!=''){
						rs = data.result;
					}
					if (parseInt(rs) > 0) {
						//console.log("logging success");
					} else {
						//console.log("logging fail");
					}
				}, 'json').error(function(data) {
					//console.log("logging fail");
				});
				
			} else {
				alert("<spring:message code='approval.msg.fail.savebody'/>");
			}
		}
	} else if (bodyType == "html") {
		filename = escapeFilename($("#title", "#approvalitem" + itemnum).val() + ".html");
		downloadHtmlBodyContent(filename, $("#appLine", "#approvalitem" + itemnum).val());
	}
}

function clearApproverSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			// Consider
			var considercount = getApbCountSNT(hwpCtrl);
			for (var loop = 1; loop <= considercount; loop++) {
				clearImage(hwpCtrl, "apb" + loop + "_sign");
			}
			// Assistance
			var assistancecount = getAsCountSNT(hwpCtrl);
			for (var loop = 1; loop <= assistancecount; loop++) {
				clearImage(hwpCtrl, "as" + loop + "_sign");
			}
			// 구 기록물
			var considercount = getApCountSNT(hwpCtrl);
			for (var loop = 1; loop <= considercount; loop++) {
				clearImage(hwpCtrl, "ap" + loop + "_sign");
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearApproverSNT");
	}
}

function clearStampSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, "seal"))
				return;
			clearImage(hwpCtrl, "seal");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearStampSNT");
	}	
}

function getApbCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "apb" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getApbCountSNT");
	}
}

// get Assistance Count
// argument[0] : HwpCtrl Object
function getAsCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "as" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getAsCountSNT");
	}
}

function getApCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "ap" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getApCountSNT");
	}
}

<% if (false) { %>
// PDF저장
function savePdfAppDoc() {
	var editMode = getEditMode(Document_HwpCtrl);
	var targetpath = escapeFilename($("#title").val() + ".pdf");
	var filename = "HwpBody_" + UUID.generate() + ".hwp";
	var sourcepath = FileManager.getlocaltempdir() + filename;
	saveHwpDocument(Document_HwpCtrl, sourcepath);
	changeEditMode(Document_HwpCtrl, editMode, false);
	
	//20120604 PDF저장 후 리로드시 본문내 의견표시 초기화 kj.yang
	deleteOpinionTbl(Document_HwpCtrl);

	var hwpfile = new Object();
	hwpfile.type = "body";
	hwpfile.localpath = sourcepath;
	var result = FileManager.uploadfile(hwpfile);
	if (result == null) {
		alert("<spring:message code='approval.msg.fail.savepdf'/>");
		return false;
	}
	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/appcom/attach/generatePdf.do", "filename=" + result[0].filename, function(data) {
		if (typeof(data) == "object" && typeof(data.filepath) != "undefined" && data.filepath != "") {
			var pdfFile = new Object();
			pdfFile.filename = data.filepath;
			pdfFile.displayname = targetpath;
			FileManager.setExtension("pdf");
			FileManager.savefile(pdfFile);
		} else {
			alert("<spring:message code='approval.msg.fail.savepdf'/>");
		}
	}, 'json').error(function(data) {
		alert("<spring:message code='approval.msg.fail.savepdf'/>");
	});
}
<%	} %>

// 사내우편
function sendMail() {
	var itemnum = getCurrentItem();
	var title = escapeFilename($("#title", "#approvalitem" + itemnum).val());

	// 현재파일 백업
	arrangeBody(Document_HwpCtrl, itemnum, false);
	var transferYn = $("#transferYn", "#approvalitem" + itemnum).val();
	if (transferYn == "N") {
		// 서명정보를 제거한다.
		clearApprover(Document_HwpCtrl);
		// 관인(서명인)/생략인정보를 제거한다.
		clearStamp(Document_HwpCtrl);
	} else {
		clearApproverSNT(Document_HwpCtrl);
		clearStampSNT(Document_HwpCtrl);
	}
	// 문서 처음으로 이동하기
	moveToPos(Document_HwpCtrl, 2);

	var filepath = FileManager.getlocaltempdir() + "HwpBody_" + UUID.generate() + ".hwp";
	var bodyfile = "";
	if (saveHwpDocument(Document_HwpCtrl, filepath)) {
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		result = FileManager.uploadfile(hwpfile);
		if (result.length > 0) {
			bodyfile = result[0].filename;
		}

		reloadBody(itemnum);
	}
	
	var attachfile = replaceAll($("#attachFile", "#approvalitem" + itemnum).val(), "'", String.fromCharCode(3));
	mailWin = openWindow("mailWin", "<%=webUri%>/app/approval/writeInnerMail.do?title=" + title + "&bodyfile=" + bodyfile + "&attachfile=" + attachfile, 800, 550, "yes");
}

// 본문/첨부 저장
var saveType = "e";
function saveAllAppDoc(type) {
	saveType = type;
	var count = 0;
	var attachid = "";
	var checkname = "attach_cname";
	var checkboxes = document.getElementsByName(checkname);
	var filelist = new Array();
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
			attachid = checkboxes[loop].id;
			attachid = attachid.replace("attach_cid_", "attach_");
			var attach = $("#" + attachid);
			var file = new Object();
			file.fileid = attach.attr("fileid");
			file.filename = attach.attr("filename");
			file.displayname = attach.attr("displayname");
			file.gubun = "";
			file.docid = "";
			file.type = "save";
			filelist[count++] = file;
		}
	}
	if (count == 0) {
		var filename = escapeFilename($("#title").val() + ".hwp");
		if (saveType == "d") { 
			saveDistributeBody("", filename, false);
		} else {
			saveAppDoc();
		}
	} else {
		FileConst.Variable.Distribute = "Y";
		FileManager.download(filelist);
	}
}

function saveunified(filepath) {
	var filename = escapeFilename($("#title").val() + ".hwp");
	if (saveType == "d") { 	
		saveDistributeBody(filepath, filename, true);
	} else {
		saveAppDoc(filepath);
	}
}

function saveDistributeBody(filepath, filename, isall) {
	var fullpath = "";
	if (filepath == "") {
		fullpath = FileManager.selectdownloadpath(filename);
	} else {
		fullpath = filepath + "\\" + filename;
	}

	saveHwpDocument(Document_HwpCtrl, fullpath, true);
	makeDistributeDocument(Document_HwpCtrl, false, false);
	openHwpDocument(Document_HwpCtrl, bodyfilepath);
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		moveToPos(Document_HwpCtrl, HwpConst.Form.Title);
	} else {
		moveToPos(Document_HwpCtrl, 2);
	}
	
	if (isall) {
		alert("<spring:message code='approval.msg.success.saveall'/>".replace("%s", filepath));
	} else {
		alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", fullpath));
	}
}

//인쇄
function printAppDoc() {
	var itemnum = getCurrentItem();
	
	if (bodyType == "hwp" || bodyType == "doc") {	
		var omitFlag = false;
		var transferYn = $("#transferYn", "#approvalitem" + itemnum).val();
		if (transferYn == "N") {
			if (existField(Document_HwpCtrl, HwpConst.Form.Seal) && !isEmptyCell(Document_HwpCtrl, HwpConst.Form.Seal)) {
				if (document.getElementById("printhwp") != null) {
					if (!confirm("<spring:message code='approval.msg.print.hwp'/>")) {
						omitFlag = true;
					}
				}
			}
		}
	
		if (omitFlag) {
			initializeHwp("printhwp", "print");
			registerModule(Print_HwpCtrl);
			var bodylist = transferFileList($("#bodyFile", "#approvalitem" + itemnum).val());
			if (bodylist.length > 0) {
				if (bodylist[0].localpath == "") {
					var bodyFile = new Object();
					bodyFile.filename = bodylist[0].filename;
					bodyFile.displayname = bodylist[0].displayname;
					bodylist[0].localpath = FileManager.savebodyfile(bodyFile);
				}
				openHwpDocument(Print_HwpCtrl, bodylist[0].localpath);
			} else if (bodyfilepath != "") {
				openHwpDocument(Print_HwpCtrl, bodyfilepath);
			} else {
				openHwpDocument(Print_HwpCtrl, hwpFormFile);
			}
			changeEditMode(Print_HwpCtrl, 2, false);
			moveToPos(Print_HwpCtrl, 2);
			clearStamp(Print_HwpCtrl);	
			printHwpDocument(Print_HwpCtrl);
			$("#printhwp").html("");
		} else {
			moveToPos(Document_HwpCtrl, 2);
			printHwpDocument(Document_HwpCtrl);
		}
	} else if (bodyType == "html") {
		if (confirm("<spring:message code='approval.msg.print'/>")) {
			printHtmlDocument($("#appLine", "#approvalitem" + itemnum).val());
		}
	}	
}

//닫기
function closeAppDoc() {
	if (confirm("<spring:message code='approval.msg.closewindow'/>")) {
		closeChildWindow();
		window.close();
	}
}

function closeChildWindow(isall) {
	try {
		if (docinfoWin != null && !docinfoWin.closed)
			docinfoWin.close();
		if (applineWin != null && !applineWin.closed)
			applineWin.close();
		if (receiverWin != null && !receiverWin.closed)
			receiverWin.close();
		if (pubreaderWin != null && !pubreaderWin.closed)
			pubreaderWin.close();
		if (readerWin != null && !readerWin.closed)
			readerWin.close();
		if (relatedDocWin != null && !relatedDocWin.closed){
			if (relatedDocWin.ChildCloseAppDoc != null) {	//20140724 관련문서 창 종료 변경 kj.yang
				relatedDocWin.ChildCloseAppDoc();
			}
			relatedDocWin.close();
		}			
		if (relatedRuleWin != null && !relatedRuleWin.closed)
			relatedRuleWin.close();
		if (customerWin != null && !customerWin.closed)
			customerWin.close();
		if (summaryWin != null && !summaryWin.closed)
			summaryWin.close();
		if (typeof(isall) == "undefined" && docLinkedWin != null && !docLinkedWin.closed)			
			docLinkedWin.close();
		if (mailWin != null && !mailWin.closed)
			mailWin.close();

		//필요시사용, 포탈홈 포틀릿에서 호출후 닫을때 새로고침 기능 필요시 사용, kj.yang, 20120425 S
 		// 포탈 홈화면에서 문서 닫을때 결재대기함, 진행문서함 refresh
   		//callRefreshPortlet();
		//필요시사용, 포탈홈 포틀릿에서 호출후 닫을때 새로고침 기능 필요시 사용, kj.yang, 20120425 E

		if (!prevnext) { 
			if (opener != null && opener.listRefresh != null 
					&& opener.curLobCode != null && opener.curLobCode == $("#lobCode").val()) {
			    opener.listRefresh();
			}
		}

	} catch(error) { }	
}

function exitAppDoc() {
	closeChildWindow();
	window.close();
}

//총 안건 건수
function getItemCount() {
	return $("div[name=approvalitem]").length;
}

// 현재 안건번호
function getCurrentItem() {
	var itemcount = $("div[name=approvalitem]").length;
	for (var loop = 1; loop <= itemcount; loop++) {
		if ($("#id_tab_bg_" + loop).attr("class") == "tab") {
			return loop;
		}
	}

	return 1;
}

function setSms(toNextApprover, toDrafter) {
	var itemCount = getItemCount();

	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		$("#alarmNextYn", "#approvalitem" + itemnum).val(toNextApprover);
		$("#alarmYn", "#approvalitem" + itemnum).val(toDrafter);
	}	
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

function moveStartPosition() {
	moveToPos(Document_HwpCtrl, 2);
	setTimeout(function(){setDefaultView(Document_HwpCtrl, '009');}, 100);
	setTimeout(function(){setDefaultView(Document_HwpCtrl, '002');}, 200);
}

function callRefreshPortlet(){
	try {
		if(($("#B_O201111241614557282311", opener.parent.document).val() != null || $("#B_O201110111741096728791", opener.parent.document).val() != null)
				&& ( $("#B_O201107312148255571630", opener.parent.document).val() != null || $("#B_O201110122147391389421", opener.parent.document).val() != null)
				&& opener.parent != null && opener.parent.updatePortletBody != null){
			
			if($("#B_O201111241614557282311", opener.parent.document).val() != null){
				//개발 WAS
				opener.parent.updatePortletBody('B_O201111241614557282311','C201107311833056351603','O201111241614557282311' , 'win_O201111241614557282311','view');
				opener.parent.updatePortletBody('B_O201107312148255571630','C201107311936459642252','O201107312148255571630' , 'win_O201107312148255571630','view');
			}
			
			if($("#B_O201110111741096728791", opener.parent.document).val() != null){
				//운영 WAS
				opener.parent.updatePortletBody('B_O201110111741096728791','C201107311833056351603','O201110111741096728791' , 'win_O201110111741096728791','view');
				opener.parent.updatePortletBody('B_O201110122147391389421','C201110122146300349373','O201110122147391389421' , 'win_O201110122147391389421','view');
			}
			
		}
	} catch (error) {
	}
}

function removeBottomTable(option, itemCount, itemnum) {
	// 표준서식인 경우에 하단표 확인
	if (isStandardForm(Document_HwpCtrl) && (option == "submit" || option == "temporary")) {
		var modifyFlag = false;
		for (var loop = 0; loop < itemCount; loop++) {
			var loopnum = loop + 1;
			var hwpCtrl = Document_HwpCtrl;
			if (itemnum != loopnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + loopnum).val());
			}
			var checkcount = 0;
			var dupFlag = false;
			var transFlag = false;
			// 이관문서
			while (existField(hwpCtrl, "footcampaign") && checkcount < 10) {
				changeEditMode(hwpCtrl, 2, true);
				removeApprTable(hwpCtrl, "footcampaign");
				transFlag = true;
				checkcount++;
				modifyFlag = true;
			}
			if (transFlag) {
				alert("<spring:message code='approval.msg.nobottomtable'/>");
				return false;
			}
			// 신규문서
			while (isDuplicated(hwpCtrl, HwpConst.Field.StandardForm) > 1 && checkcount < 10) {
				changeEditMode(hwpCtrl, 2, true);
				removeApprTable(hwpCtrl, HwpConst.Field.StandardForm);
				dupFlag = true;
				checkcount++;
				modifyFlag = true;
			}
			if (dupFlag) {
				if (isDuplicated(hwpCtrl, HwpConst.Field.StandardForm) == 0) {
					runHwpAction(hwpCtrl, "Undo");
					if (isDuplicated(hwpCtrl, HwpConst.Field.StandardForm) == 0) {
						alert("<spring:message code='approval.msg.nobottomtable'/>");
						return false;
					}
				}
				initAppLineEnv(hwpCtrl, loopnum);
				arrangeBody(hwpCtrl, loopnum, false);
			}
		}
		if (modifyFlag) {
			// 하단표 리셋
			var appline = $("#appLine", "#approvalitem" + loopnum).val();
			setAppLine(appline, true);
		}
	}

	return true;
}

function getDocId() {
	return $("#docId", "#approvalitem" + getCurrentItem()).val();
}

function getSecurityYn() {
	return $("#securityYn", "#approvalitem" + getCurrentItem()).val();
}

function getIsDuration() {
	return $("#isDuration", "#approvalitem" + getCurrentItem()).val();
}
</script>
