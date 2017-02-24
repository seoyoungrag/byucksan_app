<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.StampListVO"%>
<%
/** 
 *  Class Name  : InsertAuditSeal.jsp
 *  Description : 감사직인날인대장 
 *  Modification Information 
 * 
 *   수 정 일 :  2011-06-15
 *   수 정 자 : 장진홍
 *   수정내용 : 
 * 
 *  @author  
 *  @since 2011. 06. 15 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

	String insertBtn = messageSource.getMessage("appcom.button.insert", null, langType); //등록
	String updateBtn = messageSource.getMessage("appcom.button.update", null, langType); //수정	
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	//이전다음
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

	String SPT005 = appCode.getProperty("SPT005","SPT005","SPT"); //감사직인

	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	
	String stampId = request.getParameter("stampId");
	stampId = (stampId == null? "" : stampId);
	StampListVO stampList = (StampListVO) request.getAttribute("stampList");
	String sealType = SPT005;
	
	String modYn = request.getParameter("modYn");
	modYn = (modYn == null ? "N" : modYn);
	modYn = ("".equals(modYn) == true ? "N" : modYn);
	
	pageContext.setAttribute("stampId",stampId);
	pageContext.setAttribute("sealType",sealType);
	pageContext.setAttribute("userId",userId);
	pageContext.setAttribute("userName",userName);
	pageContext.setAttribute("modYn",modYn);
	
	//calendar 관련
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
	String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401
	
	String lobCode = (String) request.getParameter("lobCode");
	pageContext.setAttribute("lobCode", lobCode);
	
	pageContext.setAttribute("isExtWeb", isExtWeb);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<c:choose>
		<c:when test='${stampId == ""}'>
			<spring:message code="list.list.title.insertAuditSealRegist" />	
		</c:when>
		<c:otherwise> 
			<spring:message code="list.list.title.selectAuditSealRegist" />	
		</c:otherwise> 
	</c:choose>
</title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>     
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
var callType = 1;

$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });

function screenBlock() {
    var top = ($(window).height()) / 2;
    var left = ($(window).width()) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

//초기화 함수
function init_page(){
	// 화면블럭지정
	screenBlock();
	
	$("input.#sealerName").bind("change",function(){textChange($(this));});
	$("input.#requesterName").bind("change",function(){textChange($(this));});	
	$("input.#docNumber").bind("change",function(){ $("input.#docId").val(""); });

	$('input.#etcDoc').bind("click",function(){
		var title = $('#title');
		var docId = $('#docId');
		var docNumber = $('#docNumber');
		var requesterName = $('#requesterName');
		var requesterId = $('#requesterId');
		var receiver = $('#receiver');
		var sender = $('#sender');
		var divDocNumber = $('#divDocNumber');
		
		if($(this).attr('checked')){
			docNumber.val('<spring:message code="list.form.etc" />');
			requesterName.val('${userName}');
			requesterId.val('${userId}');
			docId.val("");
			title.val("");
			receiver.val("");
			title.attr("readonly", false);
			requesterName.attr("readonly", false);
			sender.attr("readonly", false);
			divDocNumber.hide();
		}else{
			docNumber.val("");
			title.attr("readonly", true);
			requesterName.attr("readonly", true);
			sender.attr("readonly", true);
			divDocNumber.show();
		}
	});

	
	<c:if test='${stampId != "" && modYn == "Y"}'> //수정이면

	
	$('#requesterName').val('${stampList.requesterName}');
	$('#requesterId').val('${stampList.requesterId}');
	$('#sender').val('${stampList.sender}');	

	var docNumber = '${stampList.docNumber}';

	if(docNumber === '<spring:message code="list.form.etc" />'){
		$('#etcDoc').attr('checked',true);
		$('#divDocNumber').hide();
		
		$('#title').attr("readonly", false);
		$('#requesterName').attr("readonly", false);
		$('#sender').attr("readonly", false);
	}
	
	var sealDate = "${stampList.sealDate}";
	sealDate = sealDate.replace(/-/g,"/");
	sealDate = sealDate.substring(0,10);
	$('#sealDate').val(sealDate);
	sealDate = sealDate.replace(/\//g,"");
	$('#sealDateId').val(sealDate);
	
	$('#sealerName').val("<%= EscapeUtil.escapeJavaScript(stampList.getSealerName())%>");
	$('#title').val("<%= EscapeUtil.escapeJavaScript(stampList.getTitle())%>");
	$('#docNumber').val("<%= EscapeUtil.escapeJavaScript(stampList.getDocNumber())%>");
	$('#sender').val("<%= EscapeUtil.escapeJavaScript(stampList.getSender())%>");
	$('#receiver').val("<%= EscapeUtil.escapeJavaScript(stampList.getReceiver())%>");
	</c:if>

	<c:if test='${stampId != "" && modYn == "N"}'> //상세내역
	var sealDate = "${stampList.sealDate}";
	sealDate = sealDate.replace(/-/g,"/");
	sealDate = sealDate.substring(0,10);
	
	$('#sealDate').html(sealDate);
	
	var remark = "<%=EscapeUtil.escapeJavaScript(stampList.getRemark()) %>"
	$('#remark').val(remark);
	var pattern = /\n/g;
	remark = escapeJavaScript(remark);
	remark = remark.replace(pattern,"<br />");
	$('#div_remark').html(remark);
	
	</c:if>

	// 화면블럭해제
	screenUnblock();
}


//----------사용자 팝업관련------------------
function textChange(obj){
	$("input[group="+obj.attr('name')+"]").val("");
}

function goSealer(){
	callType = 1;
	goUser();
}

function goRequester(){
	callType = 2;
	goUser();	
}

function goUser(){
	var width = 500;
	var height = 310;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do";
	appDoc = openWindow("user", url, width, height); 
}
function setUserInfo(user){
	setUser(callType, user);
}

function setUser(type, user){
	if(type === 1){ //기안자
		$('#sealerName').val(user.userName);
		$('#sealerId').val(user.userId);
		$('#sealerPos').val(user.positionName);
		$('#sealDeptId').val(user.deptId);
		$('#sealDeptName').val(user.deptName);
	}else{		
		$('#requesterName').val(user.userName);
		$('#requesterId').val(user.userId);
		$('#requesterPos').val(user.positionName);
		$('#requesterDeptId').val(user.deptId);
		$('#requesterDeptName').val(user.deptName);
	}
}

//문서정보 팝업----------------------------------------------------
function goProSerial(){
	var width = 800;
	var height = 450;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListAuditSealRegistDetailSearch.do";
	appDoc = openWindow("serial", url, width, height); 
}
//문서정보 결과받는 곳
function setAuditSerial(obj){
/*
	obj.docNumber       = docNumber;
    obj.docId           = docId;
    obj.title           = title;
    obj.drafterId       = drafterId;
    obj.drafterName     = drafterName;
    obj.senderTitle     = senderTitle;
    obj.recvDeptNames   = recvDeptNames;
    obj.compId          = compId;
*/	

	$('#docId').val(obj.docId);
	$('#docNumber').val(obj.docNumber);
	$('#title').val(obj.title);
	$('#requesterName').val(obj.drafterName);
	$('#requesterId').val(obj.drafterId);
	$('#receiver').val(obj.recvDeptNames);
	
	
}
//버튼 이벤트---------------------------------------------------
function setRegist(btn){
	//필수항목체크
	var required = $('input[required=Y]');
	for(var i = 0; i < required.length; i++){
		var objInput = required[i];

		if(objInput.value === ""){
			alert(objInput.getAttribute('msg'));
			$('#'+objInput.id).focus();
			return;
		}
	}

	var btnNm = "<%=insertBtn%>";
	if(btn === 2){
		btnNm = "<%=updateBtn%>";
	}
	
	<c:choose>
	<c:when test='${OPT301 == "1"}'> //결재패스워드
	popOpinion("goAjax",btnNm, "N" );
	</c:when>
	<c:when test='${OPT301 == "2"}'> //인증서 
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
	goAjax();
	</c:otherwise>
	</c:choose>
	
}

function goAjax(){
	var url = "<%=webUri%>/app/list/seal/insertAuditSeal.do?method=1";
	
	$.ajaxSetup({async:false});
	var result = "", Status;
	$.post(url,$('form').serialize(),function(data, textStatus){
		result = data
		Status = textStatus;
	});	


	if(Status !== "success"){
		alert('<spring:message code="approval.msg.fail.savendoc" />');
		return;
	}
	
	if(result.resultCode === "fail"){//approval.msg.success.savenonele
		alert('<spring:message code="approval.msg.fail.savendoc" />');
		return;
	}

	if(typeof(opener) !== "undefined"){
		if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
		    opener.listRefresh();	
		}
	}

	setTimeout(function(){closeSave();},100);
}

function closeSave(){
	alert('<spring:message code="app.alert.msg.13" />');	
	window.close();	
}

function closePopup(){
	window.close();
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
//이전문서/다음문서
var prevnext = false;

function moveToPrevious() {
    prevnext = true;

    if (opener != null && opener.getPreNextDoc != null) {
        
		var stampId = $('#stampId').val();
        var message = opener.getPreNextDoc(stampId, "pre");
        if (message != null && message != "") {
            alert(message);
        }
    }
}

function moveToNext() {
    prevnext = true;

    if (opener != null && opener.getPreNextDoc != null) {
        var docId = $('#stampId').val();

        var message = opener.getPreNextDoc(docId, "next");

        if (message != null && message != "") {
            alert(message);
        }
    }
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr><td>
		<acube:titleBar>
			<c:choose>
				<c:when test='${stampId == ""}'>
					<spring:message code="list.list.title.insertAuditSealRegist" />	
				</c:when>
				<c:when test='${stampId != "" && modYn == "Y"}'>
					<spring:message code="list.list.title.updateAuditSealRegist" />	
				</c:when>
				<c:otherwise> 
					<spring:message code="list.list.title.selectAuditSealRegist" />	
				</c:otherwise> 
			</c:choose>
		</acube:titleBar>
		</td>
		<td align="right">
		<!-- 
		<c:if test='${stampId != "" && modYn != "Y"}'> 
             <acube:buttonGroup align="right">
                 <acube:menuButton onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" />
                 <acube:space between="button" />
                 <acube:menuButton onclick="moveToNext();return(false);" value="<%=nextBtn%>" />
             </acube:buttonGroup>
         </c:if>
          -->
         </td>
		</tr>
		</table>
		<form id="frm" name="frm" method="post" onsubmit="return false;">
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td>
					<acube:tableFrame>
						<thead style="display:none">
						<tr>
							<th width="18%"></th><th width="32%"></th><th width="18%"></th><th></th>
						</tr>
						</thead>
						<tr bgcolor="#ffffff">
							<td width="18%" class="tb_tit" ><spring:message code="list.form.docnumber" /><c:if test='${stampId == "" || modYn == "Y"}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 문서번호 --><!-- 수행일자 -->
							<td width="32%" class="tb_left_bg" <c:if test='${stampId == "" || modYn == "Y"}'>colspan="3"</c:if>>
							<c:if test='${stampId == "" || modYn == "Y"}'>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="75%">
								<input id="docNumber" name="docNumber" msg='<spring:message code="list.msg.docnumber.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  onkeyup="checkInputMaxLength(this,'',128)" required="Y" readonly="readonly" value="" />
								</td>
								<td>&nbsp;</td><td><div style="width:20px;"><span id="divDocNumber"><a href="javascript:goProSerial();"><img id="imgDocNumber" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></span></div></td>
								<td>&nbsp;</td><td style="font-size:9pt"><input id="etcDoc" name="etcDoc" type="checkbox" /><spring:message code="list.form.etcdoc" /></td>
							</tr>
							</table>
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							${stampList.docNumber}
							</c:if>
							</td>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<td width="18%" class="tb_tit" ><spring:message code="list.list.title.headerStampNumber" /></td><!-- 문서번호 -->
							<td width="32%" class="tb_left_bg" >
							${stampList.sealNumber }
							</td>
							</c:if>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="18%" class="tb_tit" ><spring:message code="approval.form.title" /><c:if test='${stampId == "" || modYn == "Y"}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 제목 -->
							<td class="tb_left_bg" colspan="3">
							<c:if test='${stampId == "" || modYn == "Y"}'>
							<input id="title" name="title" msg='<spring:message code="list.msg.title.doctitle.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" required="Y" readonly="readonly" value=""/>
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<%=EscapeUtil.escapeHtmlDisp(stampList.getTitle()) %>
							</c:if>
							</td>
						</tr>
						<tr>
							<td class="tb_tit"><spring:message code="list.form.requester2" /><c:if test='${stampId == "" || modYn == "Y"}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg" colspan="3">
							<c:if test='${stampId == "" || modYn == "Y"}'>
								<input id="requesterName" 	name="requesterName" msg='<spring:message code="list.msg.requester2.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="Y" value='${userName}' readonly="readonly"/>
								<input id="requesterId" 	name="requesterId" 			type="hidden" group="requesterName"  value='${userId}' />
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<%=EscapeUtil.escapeHtmlDisp(stampList.getRequesterName()) %>
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 발신명의 --><!-- 수신자 -->
							<td class="tb_tit" ><spring:message code="approval.form.sendertitle" /><c:if test='${stampId == "" || modYn == "Y"}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg" colspan="3">
							<c:if test='${stampId == "" || modYn == "Y"}'>
								<input id="sender" 		name="sender"  msg='<spring:message code="list.msg.sendertitle.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',128)" required="Y"  readonly="readonly" value="${sender.orgName }" />
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<%=EscapeUtil.escapeHtmlDisp(stampList.getSender()) %>
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td class="tb_tit"><spring:message code="approval.title.apprecip" /><c:if test='${stampId == "" || modYn == "Y"}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg" colspan="3">
							<c:if test='${stampId == "" || modYn == "Y"}'>
								<input id="receiver" 	name="receiver"  msg='<spring:message code="list.msg.apprecip.no" />'	type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',2000)" required="Y" value="" />
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<%=EscapeUtil.escapeHtmlDisp(stampList.getReceiver()) %>
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 날인자 --><!-- 날인일자 -->
							<td class="tb_tit" width="18%" ><spring:message code="list.form.sealer" /></td>
							<td class="tb_left_bg" width="32%" >
							<c:if test='${stampId == "" || modYn == "Y"}'>
								<input id="sealerName" 		name="sealerName"  type="text" class="input" maxlength="256" style="width: 88%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="N" value="" />
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							 <%=EscapeUtil.escapeHtmlDisp(stampList.getSealerName()) %>
							</c:if>
							</td>
						<td class="tb_tit" width="18%" ><spring:message code="list.form.sealdate" /></td>
							<td class="tb_left_bg">
							<c:if test='${stampId == "" || modYn == "Y"}'>
								<input id="sealDate" name="sealDate" type="text" class="input_read" maxlength="256" style="width: 88%;ime-mode:active;" value="<%=startDate %>" readonly="readonly" />
								<img id="calSeal" name="calSeal"
							        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
							        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
							        onclick="javascript:cal.select(event, document.getElementById('sealDateId'), document.getElementById('sealDate'), 'calSeal','<%= dateFormat %>');">
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<span id="sealDate" />
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td class="tb_tit" ><spring:message code="list.list.title.remark" /></td><!-- 비고 -->
							<td class="tb_left_bg" colspan="3" style="height: 90px">
							<c:if test='${stampId == "" || modYn == "Y"}'>
							<textarea id="remark" name="remark"  rows="6" cols="10" style="width: 100%;word-break:break-all;" onkeyup="checkInputMaxLength(this,'',2000)">${stampList.remark}</textarea>
							</c:if>
							<c:if test='${stampId != "" && modYn == "N"}'>
							<span id="div_remark" style="width: 100%;height: 90px;	overflow-y : auto;word-break:break-all;"  />
							<%=EscapeUtil.escapeHtmlDisp(stampList.getRemark()) %>
							</c:if>
						</tr>
					</acube:tableFrame>
				</td>
			</tr>
			<tr>
				<td><acube:space between="title_button" /></td>
			</tr>
			<tr>
				<td>
				<!-- 처리버튼 -->	
				<c:if test='${stampId == ""}'>
				<acube:buttonGroup align="right">
			<c:if test="${!isExtWeb}">
				<acube:button id="insertBtn" onclick="setRegist(1);" value="<%=insertBtn %>" type="2" class="gr" />
				<acube:space between="button" />
			</c:if>
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
				<acube:space between="button" />
				</acube:buttonGroup>
				</c:if>
				<c:if test='${stampId != "" && modYn == "Y"}'>
				<acube:buttonGroup align="right">
			<c:if test="${!isExtWeb}">
				<acube:button id="updateBtn" onclick="setRegist(2);" value="<%=updateBtn %>" type="2" class="gr" />
				<acube:space between="button" />
			</c:if>
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
				<acube:space between="button" />
				</acube:buttonGroup>
				</c:if>
				<c:if test='${stampId != "" && modYn == "N"}'>
				<acube:buttonGroup align="right">
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
				<acube:space between="button" />
				</acube:buttonGroup>
				</c:if>
				</td>
			</tr>
		</acube:tableFrame>
		<input id="docId" 	 		 name="docId" 	 			type="hidden" 	value="${stampList.docId}"  />
		<input id="stampId" 	 	 name="stampId" 	 		type="hidden" 	value="${stampId}"  />
		<input id="electronDocYn" 	 name="electronDocYn" 	 	type="hidden" 	value="N" />
		<input id="sealDateId" 		 name="sealDateId" 			type="hidden" 	value="<%=startDateId %>" />
		<input id="sealType" 		 name="sealType" 			type="hidden" 	value="${sealType}" /><!-- 서명인타입 -->
		<input id="modYn" 	 		 name="modYn" 	 			type="hidden" 	value="${modYn}"  />
		<input id="enforceDate" 	 name="enforceDate" 	 	type="hidden" 	value="${stampList.enforceDate}"  />
		<input id="requestDate" 	 name="requestDate" 	 	type="hidden" 	value="${stampList.requestDate}"  />
		
		<!-- 비밀번호 입력 팝업 -->
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
		<input type="hidden" id="btnName" name="btnName" value="" />
    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
		</form>
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>