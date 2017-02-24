<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertProNoneElecDoc.jsp 
 *  Description : 생산용 비전자문서 등록
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.22 
 *   수 정 자 : 장진홍
 *   수정내용 : KDB 요건반영
 * 
 *  @author  장진홍
 *  @since 2011. 04. 22 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

//버튼 
String insertBtn = messageSource.getMessage("appcom.button.insert", null, langType); //등록
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
String apppublicBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//공람

String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화
String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.button", null, langType); // 공개범위설정   // jth8172  신결재 TF 2012
String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력  // jth8172  신결재 TF 2012
String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);  // jth8172  신결재 TF 2012

String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401
String currentYY = DateUtil.getCurrentDate("yyyy");

String lobCode = (String) request.getParameter("lobCode");
String receivers = CommonUtil.nullTrim(request.getParameter("receivers"));

String LOL001 = appCode.getProperty("LOL001","LOL001","LOL");

pageContext.setAttribute("compId", compId);
pageContext.setAttribute("lobCode", lobCode);

pageContext.setAttribute("currentYY", currentYY);

pageContext.setAttribute("isExtWeb", isExtWeb);


// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
String institutionId = (String) userProfileVO.getInstitution();
String headOfficeId = (String) userProfileVO.getHeadOffice();
// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF


String closeYn = CommonUtil.nullTrim(request.getParameter("closeYn"));
String title = CommonUtil.nullTrim(request.getParameter("title"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.approval.noeledoc.insert" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
//시행범위
var DET003 = '<%=appCode.getProperty("DET003", "DET003", "DET") %>';
var DET004 = '<%=appCode.getProperty("DET004", "DET004", "DET") %>';

//등록구분(문서형태)
var DTY003 = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';//사진, 필름류 시청각기록물
var DTY004 = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';//녹음, 동영상류 시청각기록물

var DPI001 = '<%=appCode.getProperty("DPI001", "DPI001", "DPI") %>'; //생산문서
var DPI002 = '<%=appCode.getProperty("DPI002", "DPI002", "DPI") %>'; //접수문서

var index = 0;
var callType = 1;

var bOrgAbbrName = false;
var openLevelWin = null;

$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$(window).unload(function() { closeChildWindow(); });

function screenBlock() {
    var top = ($(window).height() - 120) / 2;
    var left = ($(window).width() - 340) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

function init_page(){
	//편철 미사용시 보조기한 설정(defalut 값 설정),jd.park, 20120511
	<c:if test='${OPT423 == "N"}'>
	$("#retentionPeriod").val("${default}");
	</c:if>
	
	// 화면블럭지정
	screenBlock();
	
	initializeFileManager();
	
	<c:if test='${OrgAbbrName != ""}'>
	bOrgAbbrName = true;
	</c:if>
	
//시행범위
//	$('#enfType').bind('change',function(){
//		enfoce_change($(this));
//	});

	//시행범위 기타
//	$('input:checkbox[group=det]').bind('click', function(){
//		enfTarget_click($(this));
//	});

	//등록구분(문서형태)
	$('#apDty').bind('change',function(){
		apDty_change($(this));
	});
	
	$('input:checkbox[group=rectype]').bind('click', function(){
		rectype_click($(this));
	});
	
	//특수 기록물	
	$('input:checkbox[group=specialRec]').bind('click', function(){
		specialRec_click($(this));
	});

	//채번사용
	$('#noSerialYn').bind('click', function(){
		if($(this).attr("checked") === true){
			$('#divSerialNum').show();
			$("#divProSerial").show();
		}else{
			$('#divSerialNum').hide();	
			$("#divProSerial").hide();
		}
		
		initBind();
	});
	//기안자
	$("input.#drafterNm").bind("change",function(){textChange($(this));});
	$("input.#approverNm").bind("change",function(){textChange($(this));});

	$('input[name=auditReadYn]').bind("click", function(){
		if($(this).attr('value') === "Y"){
			$('#divAuditReadN1').hide()
			$('#divAuditReadN2').hide()
		}else{
			$('#divAuditReadN1').show()
			$('#divAuditReadN2').show()
		}
		$('#auditReadReason').val("");
	});

	$('#publicPostYn').bind("click",function(){
		if($(this).attr('checked')){
			$('#divPublicPost').show();
		}else{
			$('#divPublicPost').hide();
		}
	});

	//숫자등록
	$('#pageCount').bind('change',function(){
		var result = new Number($(this).val());
		if(isNaN(result)){
			$(this).val('0');
		}
	});
	
	// 화면블럭해제
	screenUnblock();
}

//편철 및 생산등록번호 초기화
function initBind(){
	$('#bindId').val("");
	$('#bindNm').val("");
	$('#conserveType').val("");
	$('#divBind').show();

	$('#DeptCategory').val("${OrgAbbrName}");
	$('#divSerialValue').html("${OrgAbbrName}");
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

function textChange(obj){
	$("input[group="+obj.attr('name')+"]").val("");
}
//특수 기록물
function specialRec_click(theObj){
	var specialRec = $('#specialRec');
	var rdspecialRec = $('input:checkbox[group='+theObj.attr('group')+']');
	var specialRecCnt = rdspecialRec.length;
	var specialRecValue = "";
	
	for(var i = 0; i < specialRecCnt; i++){
		if(rdspecialRec[i].checked){
			specialRecValue+= "Y";
		}else{
			specialRecValue+= "N";
		}
	}

	specialRec.val(specialRecValue)	
}

//---------------팝업 관련------------------------------------

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

//관련문서 목록
function setRe1DocList(infolist){
	var tbRelDoc = $('#tbRelDoc tbody');
		
	for(var i = 0; i < infolist.length; i++){
		var info = infolist[i];
		var existDoc = $('input[group=related][docId='+info.docId+']');

		if (existDoc.length == 0){		
			var row = "";
			var img = "";
			row += "<tr docId='"+info.docId+"' title='"+escapeJavaScript(info.title)+"' usingType='"+info.usingType+"' electronYnValue='"+info.electronYnValue+"'>";			
			row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			if(DPI001 === info.usingType){
				img = "[<spring:message code="bind.code.DPI001" />]";
			}else{
				img = "[<spring:message code="bind.code.DPI002" />]";
			}
			row += ("<td width='36' class='ltb_left'>" + img + "</td>");
			//row += ("<td width='5'>&nbsp;</td>");
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
			//row += ("<td width='5'>&nbsp;</td>");
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

//체크박스 선택 수 구하기
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

//편철업무
var bindDoc = null
function goBind(){
	var width = 430;
	var height = 455;
	var appDoc = null;
	var serialNumber = "Y";
	if (!$("#noSerialYn").attr("checked")) {
		serialNumber = "N";
	}

	var url = "<%=webUri%>/app/bind/select.do?serialNumber=" + serialNumber;

	bindDoc = openWindow("bind", url, width, height); 
}

//편철정보
function setBind(bind){
	$('#bindId').val(bind.bindingId);
	$('#bindNm').val(escapeJavaScript(bind.bindingName));
	$('#conserveType').val(bind.retentionPeriod);

	// 편철 다국어 추가
	 $("#bindingResourceId").val(bind.bindingResourceId);       	
}

var docKindDoc = null;

// 문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//문서번호 셋팅(문서분류 사용시), jd.park, 20120511
function setDeptCategory(deptCategory){
	$('#DeptCategory').val(deptCategory);
	$('#divSerialValue').html(deptCategory);
}

// 문서분류 셋팅
function setDocKind(docKind){

	$('#classNumber').val(docKind.classificationCode);
	$('#docnumName').val(escapeJavaScript(docKind.unitName));

	var divValue = $('#classNumber').val()+" ["+$('#docnumName').val()+"]";
	
	$('#divDocKind').html(divValue);
}

// 문서분류 초기화
function docKindInit(){
	$('#classNumber').val("");
	$('#docnumName').val("");

	$('#divDocKind').html("");

	$('#DeptCategory').val("${OrgAbbrName}");
	$('#divSerialValue').html("${OrgAbbrName}");
}


//부분공개범위 창 오픈  // jth8172  신결재 TF 2012
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel;
	var width = 350;
	var height = 210;
	openLevelWin = openWindow("openLevelWin", url, width, height); 
}


//공개범위 설정  // jth8172  신결재 TF 2012
function setOpenLevelValue(strValue,strReason)
{
	$("#openLevel").val(strValue);
	$("#openReason").val(strReason);

	var strPLOpen = strValue.charAt(0);
	var strOpenLevel = "";
	if (strPLOpen == "1"){
		strOpenLevel = "<spring:message code='approval.form.publiclevel.open'/>";
	} else if (strPLOpen == "2") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.partialopen'/>";
		var lstLevel ="";
		for (var i = 1 ; i < strValue.length; i++)
		{
			if (strValue.charAt(i) == "Y") {
				lstLevel +="," + i;
			}	
		}
		strOpenLevel += "(" + lstLevel.substring(1) + ")";
	} else if (strPLOpen == "3") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.closed'/>";
		if("<%=ReasonUseYN%>" == "Y") {
			if(strReason !="") strOpenLevel += " (" + strReason +")";
		} else {
			var lstLevel ="";
			for (var i = 1 ; i < strValue.length; i++)
			{
				if (strValue.charAt(i) == "Y") {
					lstLevel +="," + i;
				}	
			}
			strOpenLevel += "(" + lstLevel.substring(1) + ")";
		}	
	}

	$("#divOpenLevel").html(strOpenLevel);
}

//공람관련//-----------------------------------------------
// 공람자
function selectPubReader() {
	var width = 650;
	var height = 570;
	var appDoc = null;
	var url = "<%=webUri%>/app/approval/ApprovalPubReader.do";
	appDoc = openWindow("pubreaderWin", url, width, height); 
}

function getPubReader() {
	return $("#pubReader").val();
}

function setPubReader(pubreader) {
	$("#pubReader").val(pubreader);
}


//등록
function setRegist(){ 

	//${OPT358.useYn}
	<c:if test='${OPT358.useYn == "1"}' >
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	</c:if>
	<c:if test='${OPT358.useYn == "2"}' >
	if($('#noSerialYn').attr('checked')){
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	}
	</c:if>
	
	if("" === $.trim($('#title').val())){
		alert('<spring:message code="approval.msg.notitle" />');
		$('#title').focus();
		return;
	}

	// 편철 사용시, jd.park, 20120511
	<c:choose>
		<c:when test='${OPT423 == "Y"}'>
			if("" === $.trim($('#bindId').val())){	
				alert("<spring:message code='approval.msg.nobind'/>");
				goBind();
				return;
			}
		</c:when>
		<c:otherwise>
			$('#conserveType').val($("#retentionPeriod").val());
		</c:otherwise>
	</c:choose>

	//문서분류 사용시, jd.park, 20120511	
	<c:if test='${OPT422 == "Y"}'>
		if("" === $.trim($('#classNumber').val())){	
			alert("<spring:message code='approval.msg.nomanage.number'/>");
			goDocKind();
			return;
		}
	</c:if>

	if("" === $.trim($('#drafterNm').val())){
		alert('<spring:message code="approval.msg.nodrafter" />');
		return;
	}
	
	if("" === $.trim($('#approverNm').val())){
		alert('<spring:message code="approval.msg.noapprover" />');
		return;
	}

	if("" === $.trim($('#draftDate').val())){
		alert('<spring:message code="approval.msg.nodraftedate" />');
		return;
	}

	if("" === $.trim($('#approvalDate').val())){
		alert('<spring:message code="approval.msg.noapprovaldate" />');
		return;
	}


	if($('#draftDateId').val() >  $('#approvalDateId').val()){
		alert('<spring:message code="approval.msg.draftedateApprovaldate" />');
		return;
	}


	<c:if test='${OPT359.useYn == "Y"}' >
	if("" === $('#attachFile').val()){ 
		alert('<spring:message code="approval.file.selectonlyoneattachfile" />');
		return;
	}
	</c:if>
	arrangeAttach();

	<c:if test='${OPT312.useYn == "Y"}'>
	if($('#auditReadN').attr('checked')){
		var auditReadReason = $('#auditReadReason');
		if(auditReadReason.val() === ""){
			alert('<spring:message code="approval.msg.input.reason" />');
			auditReadReason.focus();		
			return;
		}
	}	
	</c:if>
	
	//시행 범위가 내부가 아니면 수신자 입력 체크
	if($("#enfType").val() != "<%=appCode.getProperty("DET001", "DET001", "DET") %>" && $("#receivers").val() == ""){
		alert('<spring:message code="approval.msg.notexist.receiverNonElec" />');
		$("#receivers").focus();
		return;
	}

	// 시행범위가 내부면 수신자 입력 체크(내부일때는 수신자 등록 불가)
	if($("#enfType").val() == "<%=appCode.getProperty("DET001", "DET001", "DET") %>" && $("#receivers").val() != ""){
		alert('<spring:message code="approval.msg.needDetType.nonElec" />');
		$("#enfType").focus();
		return;
	}
	
	<c:choose>
	<c:when test='${OPT301 == "1"}'> //결재패스워드
	popOpinion("goAjax","<%=insertBtn%>", "N" );
	</c:when>
	<c:when test='${OPT301 == "2"}'> //인증서
	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			goAjax();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		} else {
			if (certificate()) {
				goAjax();
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
			goAjax();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		}
	});
	</c:when>
	<c:otherwise> //인증안함
		goAjax();//비인증시 오류로 인해 추가함 by jkkim 2012.03.27
	</c:otherwise>
	</c:choose>
}

function goAjax(){
	// 편철 다국어 추가
	var saveBindName =$('#bindNm').val();
	$('#bindNm').val(escapeJavaScript($("#bindingResourceId").val()));
	
	var url = "<%=webUri%>/app/approval/insertProNonElecDoc.do?method=1";
	$.ajaxSetup({async:false});
	
	var result = "", Status = "";	
	$.ajaxSetup({async:false});
	$.post(url,$('form').serialize(),function(data, textStatus){
		result = data
		Status = textStatus;
	},'json');	

	$('#bindNm').val(saveBindName);
	if (Status !== "success" || result.resultCode === "fail") {//approval.msg.success.savenonele
		setTimeout(function(){ afterSubmit(false, '<spring:message code="approval.msg.fail.savenonele" />'); }, 100);
	} else {
		$('#docId').val(result.resultMessageKey);
		setTimeout(function(){ afterSubmit(true, '<spring:message code="approval.msg.success.savenonele" />'); }, 100);
	}
}

function afterSubmit(result, message) {
	alert(message);
	
	if (result) {
		if (typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined") {
			if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
				opener.listRefreshCompulsion();	
			}
		}

		<c:choose>
		<c:when test='${OPT357.useYn == "Y"}'>
		<%if(!"N".equals(closeYn)){%>
		window.close();
		<%} else {%>
		$('#frm').attr("target","_self");
		$('#frm').attr("action","<%=webUri%>/app/approval/selectProNonElecDoc.do");
		$('#frm').submit();
		<%} %>	
		</c:when>
		<c:otherwise>
		$('#frm').attr("target","_self");
		$('#frm').attr("action","<%=webUri%>/app/approval/selectProNonElecDoc.do");
		$('#frm').submit();
		</c:otherwise>
		</c:choose>
	}
}

function closePopup(){
	if (openLevelWin != null) {
		openLevelWin.close();
	}
	window.close();
}

//현재 안건번호
function getCurrentItem() {
	return 1;
}
//--------검색이벤트-------------------
//기안자 정보 팝업
function goDrafter(){
	callType = 1;
	goUser();
}

//결재자 정보 팝업
function goApprover(){
	callType = 2;
	goUser();
} 
var goUserDoc = null;
function goUser(){
	var width = 500;
	var height = 310;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do";
	goUserDoc = openWindow("User", url, width, height); 
}

//기안자, 결재자 정보
function setUser(type, user){
	if(type === 1){ //기안자
		$('#drafterNm').val(user.userName);
		$('#drafterId').val(user.userId);
		$('#drafterPos').val(user.positionName);
		$('#drafterDeptId').val(user.deptId);
		$('#drafterDeptNm').val(user.deptName);
	}else{
		
		$('#approverNm').val(user.userName);
		$('#approverId').val(user.userId);
		$('#approverPos').val(user.positionName);
		$('#approverDeptId').val(user.deptId);
		$('#approverDeptNm').val(user.deptName);
	}
}

function setUserInfo(user){
	setUser(callType, user);
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

			$('#bindNm').val(escapeJavaScript(info[6]));
			$('#bindId').val(info[5]);
			$('#btnBind').hide();

			$('#divBind').hide();

			bOrgAbbrName = true;
		}
	}
}

//편철 및 생산번호 초기화
function delProSerial(){
	initBind();
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {
	var width = 400;

	var height = 250;
	if(opinionYn=="N") {
		height = 140;
	}	
	
	var appDoc = null;
	var url = "";
	appDoc = openWindow("popupWin", url, width, height); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frm").attr("target", "popupWin");
	$("#frm").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#frm").submit();

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
	if (openLevelWin != null) {
		openLevelWin.close();
	}    
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>


	
<acube:outerFrame>
<form id="frm" name="frm" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
    <tr>
        <td><span class="pop_title77"><spring:message code="approval.title.approval.noeledoc.insert" /></span></td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				</td>
				<td align="right">
					<acube:buttonGroup>
<c:if test="${!isExtWeb}">
						<acube:button onclick="setRegist();return(false);" value="<%=insertBtn %>" type="2" />
						<acube:space between="button" />
	<c:if test='${OPT334.useYn == "Y"}'>
						<acube:button onclick="selectPubReader();return(false);" value="<%=apppublicBtn %>" type="2" />
						<acube:space between="button" />
	</c:if>
</c:if>
						<acube:button onclick="closePopup();return(false);" value="<%=closeBtn %>" type="2" />
					</acube:buttonGroup>
				</td>
			</tr>
		</table>
		<!-- 문서정보 -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width=65%>
					<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.docinfo" /></acube:titleBar>
				</td>
				<c:if test='${OPT316.useYn == "1" || OPT316.useYn == "3"}'>
				<td align="right"><!-- 공람게시 -->				
				<table border="0" cellpadding="0" cellspacing="0">
					<tr bgcolor="#ffffff" >
						<td style="font-size: 9pt;" align="right" width="5">
						<input type="checkbox" id="publicPostYn" name="publicPostYn" value="Y" />
						</td>
						<td style="font-size: 9pt;" width="50">
						<spring:message code='approval.form.publicpost'/>
						</td>
					<c:choose>
						<c:when test='${OPT314.useYn == "1"}'>
						<td style="width: 130;" align="right">
						<div id="divPublicPost" style="display: none;">
									<select id="publicPost" name="publicPost" class="select_9pt" style="width:115">										
										<option value='<%=appCode.getProperty("DRS002", "	", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
										<% if(!"".equals(headOfficeId)) { // jth8172 2012 신결재 TF%>
										<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
										<% } %>
										<% if(!"".equals(institutionId)) { // jth8172 2012 신결재 TF%>
										<option value='<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'><spring:message code="approval.form.readrange.drs004" /></option>
										<% } %>
										<option value='<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>'><spring:message code="approval.form.readrange.drs005" /></option>
									</select>
						</div>
						</td>
						</c:when>
						<c:otherwise>
						<input id="publicPost" name="publicPost" type="hidden" value="<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>" />
						</c:otherwise>
						</c:choose>
					</tr>
				</table>
				</td>
				</c:if>
			</tr>
		</table>
		
		<acube:tableFrame class="td_table borderBottom borderRight">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /><spring:message code='common.title.essentiality'/></td><!-- 제목 -->
				<td class="tb_left_bg" colspan="3" ><input id="title" name="title" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value='<%=title %>' /></td>
			</tr>
			<c:choose>
				<c:when test='${OPT423 == "Y"}'>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="approval.form.records" /><spring:message code='common.title.essentiality'/></td><!-- 편철 -->
						<td class="tb_left_bg" colspan="3" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%"><input id="bindNm"  name="bindNm" type="text" class="input_read" readonly="readonly" style="width: 100%;ime-mode:active;" /></td>
									<td>&nbsp;</td><td align="left"><div id="divBind" ><acube:button onclick="goBind();return(false);" value="<%=bindBtn%>" type="4" class="gr" /></div></td>
								</tr>
							</table>
						</td>					
					</tr>
				</c:when>
				<c:otherwise>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="bind.obj.retention.period" /></td><!-- 보존기간 -->
						<td class="tb_left_bg" colspan="3" >							
							<table border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td>
										<input id="bindNm"  name="bindNm" type="hidden" value=""/>
										<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.proregnum" /></td><!-- 생산등록번호 -->
				<td  class="tb_left_bg" colspan="3" style="margin: 0px;padding: 0px">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="tb_left tb_last tr_last">
								<div id="divSerialNum" style="float: left; width:100%;height:100%;margin: 0px;padding: 0px;">
									<div id="divSerialValue" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;">
									
									${OrgAbbrName}
									
									</div>
								</div>
							</td>
							<td class="tb_left tb_last tr_last" width="50" style="font-size: 9pt;">								
							</td>
							<td class="tb_left tb_last tr_last" width="60">
								<div id="divProSerial" >
									<c:if test='${OPT310.useYn == "Y"}'><a href="javascript:goProSerial();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></c:if>
									<a href="javascript:delProSerial();"><img src='<%=webUri%>/app/ref/image/bu5_close.gif' border="0"></a>
								</div>
							</td>
							<c:choose>
							<c:when test='${OPT358.useYn == "1"}' >
							<input id="noSerialYn" name="noSerialYn" type="checkbox"  value="Y" style="display:none;position: absolute;" checked />
							</c:when>
							<c:otherwise>
							<td class="tb_left_bg" width="70" style="font-size: 9pt;text-align: right">								
								<input id="noSerialYn" name="noSerialYn" type="checkbox"  value="Y" checked /><spring:message code="approval.form.nosericalnum" />
							</td>
							</c:otherwise>
							</c:choose>
							
						</tr>
					</table>					
					<input id="DeptCategory" name="DeptCategory" type="hidden" value="${OrgAbbrName}" /><input id="SerialNum" name="SerialNum" type="hidden" value="0" /><input id="SubserialNumber" name="SubserialNumber" type="hidden" value="0" />
				</td>
			</tr>

			<c:if test='${OPT422 == "Y"}'>
				<tr bgcolor="#ffffff" >
					<td class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.docKind" /><spring:message code='common.title.essentiality'/></td><!-- 문서분류 -->
					<td class="tb_left_bg" colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="96%">
									<div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
								</td>
								<td align="left"><acube:button onclick="goDocKind();return(false);" value="<%=docKindBtn%>" type="4" class="gr" /></td>
								<td width="5">&nbsp;</td>
								<td align="left"><acube:button onclick="docKindInit();return(false);" value="<%=docKindInitBtn%>" type="4" class="gr" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
		
<c:if test='${OPT321.useYn == "Y"}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.reldoc" /></td><!-- 관련문서 -->
				<td class="tb_left_bg" colspan="3" height="60">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
						<td width='97%'>
							<div style="width: 100%; height:50px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF; float: left;" >
								<table id="tbRelDoc" width="95%" border="0" cellpadding="0" cellspacing="0">
								<tbody />
								</table>
							</div>
						</td>
						<td valign="top">						
							<table cellpadding="0" cellspacing="0" border="0" width="2%">
								<tr>
									<td style="background:#ffffff" align="center" valign="top">
										<img src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:moveUpRelateDoc();return(false);" style="cursor: hand">
									</td>
									<td width="5">&nbsp;</td>
									<td style="background:#ffffff" align="center" valign="top">
										<img src="<%=webUri%>/app/ref/image/bu_pp.gif" onclick="javascript:goRecords();return(false);" style="cursor: hand">
									</td>
								</tr>
								<tr>
									<td colspan="3" style="height:5px; "></td>
								</tr>
								<tr>
									<td style="background:#ffffff" align="center" valign="bottom">
										<img src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:moveDownrelateDoc();return(false);" style="cursor: hand">
									</td>
									<td></td>
									<td style="background:#ffffff" align="center" valign="bottom">
										<img src="<%=webUri%>/app/ref/image/bu_mm.gif" onclick="javascript:removeRecods();return(false);" style="cursor: hand">
									</td>
								</tr>
							</table>							
						</td>
						</tr>
					</table>
				</td>
			</tr>
</c:if>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.title.summary2" /></td><!-- 요약전 -->
				<td class="tb_left_bg" colspan="3" height="50">
				<textarea id="summary" name="summary"  rows="3" cols="10" style="width: 100%;ime-mode:active;"></textarea>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.attach" /><c:if test='${OPT359.useYn == "Y"}' ><spring:message code='common.title.essentiality'/></c:if></td><!-- 첨부파일 -->
				<td class="tb_left_bg" colspan="3" height="60">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="100%">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:50px;width:100%;overflow:auto;">
							</div>
						</td>
						<td>
						<table cellpadding="0" cellspacing="0" border="0" width="2%">
							<tr>
								<td style="background:#ffffff" align="center" valign="top">
									<img src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:moveUpAttach();return(false);">
								</td>
								<td width="5">&nbsp;</td>
								<td style="background:#ffffff" align="center" valign="top">
									<img src="<%=webUri%>/app/ref/image/bu_pp.gif" onclick="javascript:appendAttach();return(false);">
								</td>
							</tr>
							<tr>
								<td colspan="3" style="height:5px; "></td>
							</tr>
							<tr>
								<td style="background:#ffffff" align="center" valign="bottom">
									<img src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:removeAttach();return(false);">
								</td>
								<td></td>
								<td style="background:#ffffff" align="center" valign="bottom">
									<img src="<%=webUri%>/app/ref/image/bu_mm.gif" onclick="javascript:removeAttach();return(false);">
								</td>
							</tr>
						</table>
						</td>
				</tr>
				</table>
				</td>
			</tr>
		</acube:tableFrame>
	
		<!-- 경로정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.lineinfo" /></acube:titleBar>
		<acube:tableFrame class="td_table borderBottom borderRight">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.drafter" /><spring:message code='common.title.essentiality'/></td>
				<td  class="tb_left_bg" width="32%">
					<table width="99%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="90%"><input id="drafterNm" name="drafterNm" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" />
							<input id="drafterId" name="drafterId" type="hidden"   		 group="drafterNm" />
							<input id="drafterPos" name="drafterPos" type="hidden" 		 group="drafterNm" />
							<input id="drafterDeptId" name="drafterDeptId" type="hidden" group="drafterNm" />
							<input id="drafterDeptNm" name="drafterDeptNm" type="hidden" group="drafterNm" /></td>
							<td>&nbsp;</td><td><a href="javascript:goDrafter();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
						</tr>
					</table>
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.approver" /><spring:message code='common.title.essentiality'/></td>
				<td class="tb_left_bg" >
					<table width="99%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="90%">
								<input id="approverNm" name="approverNm" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" />
								<input id="approverId" name="approverId" type="hidden" group="approverNm" />
								<input id="approverPos" name="approverPos" type="hidden" group="approverNm" />
								<input id="approverDeptId" name="approverDeptId" type="hidden" group="approverNm" />
								<input id="approverDeptNm" name="approverDeptNm" type="hidden" group="approverNm" />
							</td>
							<td>&nbsp;</td><td><a href="javascript:goApprover();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" ><spring:message code="approval.form.draftdate" /><spring:message code='common.title.essentiality'/></td>
				<td class="tb_left_bg" >
					<input id="draftDate" name="draftDate" type="text" class="input_read" maxlength="256" style="width: 89%" value="<%=startDate %>" readonly="readonly" />
					<img id="calDraft" name="calDraft"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('draftDateId'), document.getElementById('draftDate'), 'calDraft','<%= dateFormat %>');">
				</td>
				<td  class="tb_tit"><spring:message code="approval.form.approvaldate" /><spring:message code='common.title.essentiality'/>
				</td>
				<td class="tb_left_bg" >
					<input id="approvalDate" name="approvalDate" type="text" class="input_read" maxlength="256" style="width:89%"  value="<%=startDate %>"  readonly="readonly" />
					<img id="calApproval" name="calApproval"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('approvalDateId'), document.getElementById('approvalDate'), 'calApproval','<%= dateFormat %>');">
				</td>
			</tr>
		</acube:tableFrame>
			
		<acube:space between="button_content" table="y"/>
		<!-- 시행정보 -->
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.procinfo" /></acube:titleBar>
		<acube:tableFrame class="td_table borderBottom borderRight">
<!-- 등록일자				
					<tr bgcolor="#ffffff" >
						<td  class="tb_tit" ><spring:message code="approval.form.regdate" /></td>
						<td class="tb_left_bg"  colspan="3"><div id="divRegDate"></div></td>
					</tr>
 -->
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforceange" /></td><!-- 시행범위 -->
				<td class="tb_left_bg" style="vertical-align: middle;" width="32%">
				<!-- div style="float: left;" -->
				<select id="enfType" name="enfType"  style="width:99%">
					<option value="<%=appCode.getProperty("DET001", "DET001", "DET") %>"><spring:message code="approval.form.det001" /></option><!-- 내부 -->
					<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
					<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					<option value="<%=appCode.getProperty("DET004", "DET004", "DET") %>"><spring:message code="approval.form.det004" /></option><!-- 대내외 -->
				</select>
			<!-- /div -->
<!-- 
						<div id="divEnforce" style="display: none;"> 
							&nbsp;<input id="det005" name="det005" group="det" type="checkbox" value="<%=appCode.getProperty("DET005", "DET005", "DET") %>" /><spring:message code="approval.form.det005" />
					&nbsp;<input id="det006" name="det006" group="det" type="checkbox" value="<%=appCode.getProperty("DET006", "DET006", "DET") %>" /><spring:message code="approval.form.det006" />
					&nbsp;<input id="det007" name="det007" group="det" type="checkbox" value="<%=appCode.getProperty("DET007", "DET007", "DET") %>" /><spring:message code="approval.form.det007" />
				</div>
-->
					</td>
					<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforcedate" /></td>
				<td class="tb_left_bg" >
					<input id="enforceDate" name="enforceDate" type="text" class="input_read" maxlength="256" style="width: 90%"  value=""  readonly="readonly" />
					<img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');"></td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" ><spring:message code="approval.title.apprecip" /></td><!-- 수신자 -->
				<td  class="tb_left_bg" colspan="3"><input id="receivers" name="receivers" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  onkeyup="checkInputMaxLength(this,'',2000)" value="<%=receivers%>" /></td>
			</acube:tableFrame>
				
			<!-- 관리정보 -->
			<acube:space between="button_content" table="y"/>
			<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.mgrinfo" /></acube:titleBar>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td>
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="td_table borderBottom borderRight">
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" width="18%"><spring:message code="approval.from.docclsdiv" /></td><!-- 문서분류구분 -->
						<td class="tb_left_bg" width="32%">
						<select id="Categoris" name="Categoris" style="width:99%">
						<c:forEach items='${Categoris}' var='category'>
							<option value="${category.categoryId}">${category.categoryName}</option>
						</c:forEach>
						</select>
						</td>
						<td  class="tb_tit" width="18%"><spring:message code="approval.form.readrange" /></td><!-- 열람범위 -->
						<td  class="tb_left_bg">
						<c:choose>
							<c:when test='${OPT314.useYn == "1"}'>
							<select id="readRange" name="readRange" style="width: 100%">
								<option value='<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>'><spring:message code="approval.form.readrange.drs001" /></option>
								<option value='<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
								<% if(!"".equals(headOfficeId)) { // jth8172 2012 신결재 TF%>
								<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
								<% } %>
								<% if(!"".equals(institutionId)) { // jth8172 2012 신결재 TF%>
								<option value='<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'><spring:message code="approval.form.readrange.drs004" /></option>
								<% } %>
								<option value='<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>'><spring:message code="approval.form.readrange.drs005" /></option>
							</select>
							</c:when>
							<c:otherwise>
							<spring:message code="approval.form.drs002" /><input id="readRange" name="readRange" type="hidden" value="<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>" />
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr bgcolor="#ffffff"><!-- 공개범위 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.publiclevel" /></td>
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td width="96%"><div id="divOpenLevel" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div></td>
									<td align="right">
									<acube:button onclick="goOpenLevel();return(false);" value="<%=openLevelBtn%>" type="4" class="gr" />
									<input type="hidden" name="openLevel" id="openLevel" value="" />
									<input type="hidden" name="openReason" id="openReason" value=""/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<c:if test='${OPT312.useYn == "Y"}'>
					<tr>
					<td class="tb_tit" ><spring:message code="approval.form.audit.readyn" /></td><!-- 감사부서열람 -->
					<td class="tb_left_bg" colspan="3" style="margin: 0px">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 9pt;">
						<tr>
						<td width="30%" style="background-color: #FFFFFF;">
						<input id="auditReadY" name="auditReadYn" type="radio" value="Y" checked="checked" /><spring:message code="approval.form.open" />
						<input id="auditReadN" name="auditReadYn" type="radio" value="N" /><spring:message code="approval.form.notopen" />
						</td>
						<td  width="15%" style="background-color: #FFFFFF;"><span id="divAuditReadN1" style="display:none"><spring:message code="approval.form.notopen.reason" /> : </span></td>
						<td><span  id="divAuditReadN2" style="display:none"><input id="auditReadReason" name="auditReadReason" class="input" type="text" style="width:100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',1024)" /></span></td>
						</tr>
						</table>
					</td>
					</tr>
					</c:if>
					<c:if test='${OPT312.useYn == "N"}'>
						<input id="auditReadYn" name="auditReadYn" type="hidden" value="Y" />
					</c:if>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="approval.from.regdiv" /></td><!-- 등록구분 -->
						<td class="tb_left_bg">
							<select id="apDty" name="apDty" style="width:99%">
								<option value='<%=appCode.getProperty("DTY001", "DTY001", "DTY") %>' selected="selected"><spring:message code="approval.form.dty001" /></option>
								<option value='<%=appCode.getProperty("DTY002", "DTY002", "DTY") %>'><spring:message code="approval.form.dty002" /></option>
								<option value='<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>'><spring:message code="approval.form.dty003" /></option>
								<option value='<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>'><spring:message code="approval.form.dty004" /></option>
								<option value='<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>'><spring:message code="approval.form.dty005" /></option>
							</select>
						</td>
						<td  class="tb_tit" width="18%"><spring:message code="approval.from.pagecount" /></td><!-- 쪽수 -->
						<td  class="tb_left_bg"  >
							<input id="pageCount" name="pageCount" type="text" value="0" class="input" maxlength="4" style="width:100%" />
						</td>
					</tr>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="approval.from.spcrec" /></td><!-- 특수기록물 -->
						<td class="tb_left_bg"  colspan="3">
						<input id="specialReca" name="specialReca" group="specialRec" type="checkbox" value="1" /><spring:message code="approval.from.spcreca" />&nbsp;
						<input id="specialRecb" name="specialRecb" group="specialRec" type="checkbox" value="2" /><spring:message code="approval.from.spcrecb" />&nbsp;
						<input id="specialRecc" name="specialRecc" group="specialRec" type="checkbox" value="3" /><spring:message code="approval.from.spcrecc" />&nbsp;
						<input id="specialRecd" name="specialRecd" group="specialRec" type="checkbox" value="4" /><spring:message code="approval.from.spcrecd" />&nbsp;<br />
						<input id="specialRece" name="specialRece" group="specialRec" type="checkbox" value="5" /><spring:message code="approval.from.spcrece" />
						</td>
					</tr>
				</acube:tableFrame>
				</td>
			</tr>			
			</acube:tableFrame>
		
			<div id="div_rectype" style="display: none;">
			<acube:space between="button_content" table="y"/>
			<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.addregrec" /></acube:titleBar>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1" class="td_table">
				<tr bgcolor="#ffffff" >
					<td  class="tb_tit" width="18%"><spring:message code="approval.form.dty.summary" /></td><!-- 내용요약 -->
					<td  class="tb_left_bg" height="60">
					<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',2000)"></textarea>
					</td>
				</tr>
				
				<tr bgcolor="#ffffff" >
					<td  width="150" class="tb_tit" ><spring:message code="approval.form.dty.rectype" /></td><!-- 기록물형태 -->
					<td  class="tb_left_bg" height="60" style="font-size: 9pt;">
					<div id="div_rectype_c" style="display: none;"><!-- 사진, 필름류 시청각 기록물 -->
						<input id="rectypeCr" name="rectypeCr" group="rectype" type="checkbox" value="<%=appCode.getProperty("CR", "CR", "RTS") %>" /><spring:message code="approval.form.rts.cr" />&nbsp;
						<input id="rectypeCs" name="rectypeCs" group="rectype" type="checkbox" value="<%=appCode.getProperty("CS", "CS", "RTS") %>" /><spring:message code="approval.form.rts.cs" />&nbsp;
						<input id="rectypeCt" name="rectypeCt" group="rectype" type="checkbox" value="<%=appCode.getProperty("CT", "CT", "RTS") %>" /><spring:message code="approval.form.rts.ct" />&nbsp;
						<input id="rectypeCu" name="rectypeCu" group="rectype" type="checkbox" value="<%=appCode.getProperty("CU", "CU", "RTS") %>" /><spring:message code="approval.form.rts.cu" />&nbsp;
						<input id="rectypeCv" name="rectypeCv" group="rectype" type="checkbox" value="<%=appCode.getProperty("CV", "CV", "RTS") %>" /><spring:message code="approval.form.rts.cv" />&nbsp;<br />
						<input id="rectypeCy" name="rectypeCy" group="rectype" type="checkbox" value="<%=appCode.getProperty("CY", "CY", "RTS") %>" /><spring:message code="approval.form.rts.cy" />&nbsp;
					</div>
					<div id="div_rectype_d" style="display: none;"><!-- 녹음, 동영상류 시청각 기록물 -->
						<input id="rectypeDa" name="rectypeDa" group="rectype" type="checkbox" value="<%=appCode.getProperty("DA", "DA", "RTS") %>" /><spring:message code="approval.form.rts.da" />&nbsp;
						<input id="rectypeDb" name="rectypeDb" group="rectype" type="checkbox" value="<%=appCode.getProperty("DB", "DB", "RTS") %>" /><spring:message code="approval.form.rts.db" />&nbsp;
						<input id="rectypeDc" name="rectypeDc" group="rectype" type="checkbox" value="<%=appCode.getProperty("DC", "DC", "RTS") %>" /><spring:message code="approval.form.rts.dc" />&nbsp;
						<input id="rectypeDd" name="rectypeDd" group="rectype" type="checkbox" value="<%=appCode.getProperty("DD", "DD", "RTS") %>" /><spring:message code="approval.form.rts.dd" />&nbsp;
						<input id="rectypeDf" name="rectypeDf" group="rectype" type="checkbox" value="<%=appCode.getProperty("DF", "DF", "RTS") %>" /><spring:message code="approval.form.rts.df" />&nbsp;
						<input id="rectypeDg" name="rectypeDg" group="rectype" type="checkbox" value="<%=appCode.getProperty("DG", "DG", "RTS") %>" /><spring:message code="approval.form.rts.dg" />&nbsp;<br />
						<input id="rectypeDh" name="rectypeDh" group="rectype" type="checkbox" value="<%=appCode.getProperty("DH", "DH", "RTS") %>" /><spring:message code="approval.form.rts.dh" />&nbsp;
						<input id="rectypeDj" name="rectypeDj" group="rectype" type="checkbox" value="<%=appCode.getProperty("DJ", "DJ", "RTS") %>" /><spring:message code="approval.form.rts.dj" />&nbsp;
						<input id="rectypeDk" name="rectypeDk" group="rectype" type="checkbox" value="<%=appCode.getProperty("DK", "DK", "RTS") %>" /><spring:message code="approval.form.rts.dk" />&nbsp;
						<input id="rectypeDn" name="rectypeDn" group="rectype" type="checkbox" value="<%=appCode.getProperty("DN", "DN", "RTS") %>" /><spring:message code="approval.form.rts.dn" />&nbsp;
						<input id="rectypeDo" name="rectypeDo" group="rectype" type="checkbox" value="<%=appCode.getProperty("DO", "DO", "RTS") %>" /><spring:message code="approval.form.rts.do" />&nbsp;
						<input id="rectypeDp" name="rectypeDp" group="rectype" type="checkbox" value="<%=appCode.getProperty("DP", "DP", "RTS") %>" /><spring:message code="approval.form.rts.dp" />&nbsp;
						<input id="rectypeDq" name="rectypeDq" group="rectype" type="checkbox" value="<%=appCode.getProperty("DQ", "DQ", "RTS") %>" /><spring:message code="approval.form.rts.dq" />&nbsp;
					</div>
					</td>
				</tr>
			</acube:tableFrame>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			</div>	
			<div id="approvalitem1" name="approvalitem" style="dispaly:none">
			<!-- hidden -->
			<input id="bindId" name="bindId" type="hidden"  /><!-- 편철 -->
			<input id="docId" name="docId" type="hidden" />
			<input id="lobCode" name="lobCode" type="hidden" value="<%=LOL001 %>"/>
			<input id="relDoc" name="relDoc" type="hidden"  /><!-- 관련문서 -->
			<input id="draftDateId" name="draftDateId" type="hidden" value="<%=startDateId %>" /><!-- 기안일자 -->
			<input id="approvalDateId" name="approvalDateId" type="hidden" value="<%=startDateId %>"  /><!-- 결재일자 -->
			<input id="enfTarget" name="enfTarget" type="hidden" value="" /><!-- 시행범위 기타 -->
			<input id="enforceDateId" name="enforceDateId" type="hidden" value=""  /><!-- 수신일자 -->
			<input id="openLevel" name="openLevel" type="hidden" value="1YYYYYYYY" /><!-- 정보공개 --> 
			<input id="openReason" name="openReason" type="hidden" value="" /><!-- 정보비공개사유 --> 
			<input id="specialRec" name="specialRec" type="hidden" value="NNNNN" /><!-- 특수기록물 --> 
			<input id="rectype" name="rectype" type="hidden" value="" /><!-- 기록물형태 --> 
			<input id="pubReader" name="pubReader" type="hidden" value="" /><!-- 공람자 -->
			<input id="attachFile" name="attachFile" type="hidden" value=""></input>
			<input id="conserveType" name="conserveType" type="hidden" value="" /><!-- 사용연한 -->
			<input id="auditYn" name="auditYn" type="hidden" value="U" />
			<!-- 비밀번호 입력 팝업 -->
			<input type="hidden" id="returnFunction" name="returnFunction" value="" />
			<input type="hidden" id="btnName" name="btnName" value="" />
	    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
	    	
	    	<!--  문서분류 -->
	    	<input type="hidden" name="classNumber" id="classNumber" value=""/>
			<input type="hidden" name="docnumName" id="docnumName" value="" />
			</div>
			
			<!-- 편철 다국어 추가 -->
			<input type="hidden" name="bindingResourceId" id="bindingResourceId" value=""/>			
		</form>
		<!-- 여백 끝 -->
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>