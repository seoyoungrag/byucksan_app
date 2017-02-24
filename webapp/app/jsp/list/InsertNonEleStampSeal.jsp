<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="com.sds.acube.app.common.vo.StampListVO"%>
<%
/** 
 *  Class Name  : InsertNonEleStempSeal.jsp 
 *  Description : 직인 날인 등록 
 *  Modification Information 
 * 
 *   수 정 일 :  2011-05-18
 *   수 정 자 : 장진홍
 *   수정내용 : 
 * 
 *  @author  
 *  @since 2011. 04. 15 
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
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	
	//이전다음
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

	String SPT001 = appCode.getProperty("SPT001","SPT001","SPT"); //직인(관인)
	String SPT002 = appCode.getProperty("SPT002","SPT002","SPT"); //서명인(인장)
	String LOL004 = appCode.getProperty("LOL004","LOL004","LOL"); //서명인날인대장
	String LOL005 = appCode.getProperty("LOL005","LOL005","LOL"); //직인날인대장
	
	String stampId = request.getParameter("stampId");
	stampId = (stampId == null? "" : stampId);
	
	String lobCode = request.getParameter("lobCode");
	lobCode = (lobCode == null? LOL004 : lobCode);
	
	StampListVO  stampList = (StampListVO) request.getAttribute("stampList");
	
	String sealType = SPT002;
	
	if(LOL004.equals(lobCode)){
	    sealType = SPT002;
	}else{
	    sealType = SPT001;
	}
	
	pageContext.setAttribute("stampId",stampId);
	pageContext.setAttribute("SPT001",SPT001);
	pageContext.setAttribute("SPT002",SPT002);
	pageContext.setAttribute("sealType",sealType);
	
	//calendar 관련
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
	String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401
	

	pageContext.setAttribute("isExtWeb", isExtWeb);
	
	String spt002Title = messageSource.getMessage("list.list.title.insertStampSealRegist", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<c:if test="${sealType==SPT001}">
	<c:choose>
		<c:when test='${stampId == ""}'>
			<spring:message code="list.list.title.insertSealRegist" />	
		</c:when>
		<c:otherwise> 
			<spring:message code="list.list.title.selectSealRegist" />	
		</c:otherwise> 
	</c:choose>
	</c:if>
	<c:if test="${sealType == SPT002}">
		<c:choose>
		<c:when test='${stampId == ""}'>
			<%=spt002Title %>
		</c:when>
		<c:otherwise> 
			<spring:message code="list.list.title.selectStampSealRegist" />	
		</c:otherwise> 
		</c:choose>
	</c:if>
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

	<c:if test='${stampId != ""}'>
	var enforceDate = "${stampList.enforceDate}";
	enforceDate = enforceDate.substring(0,10);
	enforceDate = enforceDate.replace(/-/g,"/");
	enforceDate = (enforceDate.indexOf("9999") !== -1? "":enforceDate);
	$('#enforceDate').html(enforceDate);

	var sealDate = "${stampList.sealDate}";
	sealDate = sealDate.replace(/-/g,"/");
	sealDate = sealDate.substring(0,10);
	sealDate = (sealDate.indexOf("9999") !== -1? "":sealDate);
	
	$('#sealDate').html(sealDate);
	
	var requestDate = "${stampList.requestDate}";
	requestDate = requestDate.substring(0,10);
	requestDate = requestDate.replace(/-/g,"/");
	requestDate = (requestDate.indexOf("9999") !== -1? "":requestDate);
	$('#requestDate').html(requestDate);

	var remark = $('#remark').val();
	var pattern = /\n/g;
	remark = escapeJavaScript(remark);
	remark = remark.replace(pattern,"<br />");
	//$('#div_remark').html(remark);
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

//생산문서 팝업----------------------------------------------------
function goProSerial(){
	var width = 800;
	var height = 450;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRowRankDocRegist.do";
	<c:if test="${sealType==SPT001}">
	url += "?deptAll=Y";
	</c:if>
	appDoc = openWindow("serial", url, width, height); 
}

function setProSerial(serialInfo){
	var infos = serialInfo.split(String.fromCharCode(4))
	var infolength = infos.length;
	var docNumber = "";
	var docId = "";
	var docTitle = "";
	if(infolength > 0){
		if (infos[0].indexOf(String.fromCharCode(2)) != -1) {
			var info = infos[0].split(String.fromCharCode(2));
			docNumber += info[0];
			docNumber += "-";
			docNumber += info[1];

			if(info[2] != "" && info[2] != "0"){
				docNumber += "-";
				docNumber += info[2];
			}

			docId = info[3];
			docTitle = info[4];
		}
	}

	$("input.#docNumber").val(docNumber);
	$("input.#docId").val(docId);
	$("input.#title").val(docTitle);
	
}
//버튼 이벤트---------------------------------------------------
function setRegist(){
	//test();
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

	var enforceDate = $('#enforceDateId').val();
	var requestDate = $('#requestDateId').val();

	if(enforceDate < requestDate){		
		alert('<spring:message code="list.list.msg.enforceDateRequestDate" />');
		return;
	}

	<c:choose>
	<c:when test='${OPT301 == "1"}'> //결재패스워드
		popOpinion("goAjax","<%=insertBtn %>", "N" );
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
	var url = "<%=webUri%>/app/list/seal/insertNonEleStampSeal.do?method=1";	
	var result = "", Status;
	$.ajaxSetup({async:false});
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
	
	if(typeof(opener) !== "undefined" && typeof(opener.goSearch) !== "undefined"){
		opener.goSearch();
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
        var stampId = $('#stampId').val();

        var message = opener.getPreNextDoc(stampId, "next");

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
			<c:if test="${sealType==SPT001}">
			<c:choose>
				<c:when test='${stampId == ""}'>
					<spring:message code="list.list.title.insertSealRegist" />	
				</c:when>
				<c:otherwise> 
					<spring:message code="list.list.title.selectSealRegist" />	
				</c:otherwise> 
			</c:choose>
			</c:if>
			<c:if test="${sealType == SPT002}">
				<c:choose>
				<c:when test='${stampId == ""}'>
					<%=spt002Title %>
				</c:when>
				<c:otherwise> 
					<spring:message code="list.list.title.selectStampSealRegist" />	
				</c:otherwise> 
				</c:choose>
			</c:if>
		</acube:titleBar>
		</td>
		<td align="right">
		<!-- 
		<c:if test='${stampId != ""}'>
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
							<td width="18%" class="tb_tit" ><spring:message code="approval.form.title" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 제목 -->
							<td class="tb_left_bg" colspan="3">
							<c:if test='${stampId == ""}'>
							<input id="title" name="title" msg='<spring:message code="list.msg.title.doctitle.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" required="Y" />
							</c:if>
							<c:if test='${stampId != ""}'>
							${stampList.title}
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="18%" class="tb_tit" ><spring:message code="list.form.docnumber" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 문서번호 --><!-- 수행일자 -->
							<td width="32%" class="tb_left_bg">
							<c:if test='${stampId == ""}'>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="90%">
								<input id="docNumber" name="docNumber" msg='<spring:message code="list.msg.docnumber.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  onkeyup="checkInputMaxLength(this,'',128)" required="Y"  />
								</td>
								<td>&nbsp;</td><td><a href="javascript:goProSerial();"><img id="imgDocNumber" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
							</tr>
							</table>
							</c:if>
							<c:if test='${stampId != ""}'>
							${stampList.docNumber}
							</c:if>
							</td>
							<c:if test='${stampId == ""}'>
							<td width="18%" class="tb_tit"><spring:message code="approval.form.enforcedate" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
								<input id="enforceDate" name="enforceDate" type="text" class="input_read" maxlength="256" style="width: 88%;ime-mode:active;"  value="<%=startDate %>"  readonly="readonly" />
								<img id="calEnforce" name="calEnforce"
							        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
							        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
							        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calEnforce','<%= dateFormat %>');">		
							</td>
							</c:if>
							<c:if test='${stampId != ""}'><!-- 날인번호 -->
							<td width="18%" class="tb_tit"><spring:message code="list.list.title.headerStampNumber" /></td>
							<td class="tb_left_bg">
							<c:if test='${stampList.sealNumber != 999999}'>
							${stampList.sealNumber }
							</c:if>
							</td>
							</c:if>
						</tr>
						<tr bgcolor="#ffffff"><!-- 날인자 --><!-- 요청자 -->
							<td class="tb_tit" ><spring:message code="list.form.sealer" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
							<c:if test='${stampId == ""}'>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="90%">
								<input id="sealerName" 		name="sealerName"  msg='<spring:message code="list.msg.sealer.no" />'	type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="Y" />
								<input id="sealerId" 		name="sealerId" 		type="hidden" group="sealerName" />
								<input id="sealerPos" 		name="sealerPos" 		type="hidden" group="sealerName" />
								<input id="sealDeptId" 		name="sealDeptId" 		type="hidden" group="sealerName" />
								<input id="sealDeptName" 	name="sealDeptName" 	type="hidden" group="sealerName" />
								</td>
								<td>&nbsp;</td><td><a href="javascript:goSealer();"><img id="imgSealer" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
							</tr>
							</table>
								
							</c:if>
							<c:if test='${stampId != ""}'>
							 ${stampList.sealerPos}&nbsp;${stampList.sealerName}
							</c:if>
							</td>
							<td class="tb_tit"><spring:message code="list.form.requester" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
							<c:if test='${stampId == ""}'>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="90%">
								<input id="requesterName" 	name="requesterName" msg='<spring:message code="list.msg.requester.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="Y"  />
								<input id="requesterId" 	name="requesterId" 			type="hidden" group="requesterName"  />
								<input id="requesterPos" 	name="requesterPos" 		type="hidden" group="requesterName"  />
								<input id="requesterDeptId" name="requesterDeptId" 		type="hidden" group="requesterName"  />
								<input id="requesterDeptName" name="requesterDeptName" 	type="hidden" group="requesterName"  />
								</td>
								<td>&nbsp;</td><td><a href="javascript:goRequester();"><img id="imgRequester" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
							</tr>
							</table>			

							</c:if>
							<c:if test='${stampId != ""}'>
							${stampList.requesterPos}&nbsp;${stampList.requesterName}
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 날인일자 --><!-- 요청일자 -->
							<td class="tb_tit" ><spring:message code="list.form.sealdate" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
							<c:if test='${stampId == ""}'>
								<input id="sealDate" name="sealDate" type="text" class="input_read" maxlength="256" style="width: 88%;ime-mode:active;" value="<%=startDate %>" readonly="readonly" />
								<img id="calSeal" name="calSeal"
							        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
							        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
							        onclick="javascript:cal.select(event, document.getElementById('sealDateId'), document.getElementById('sealDate'), 'calSeal','<%= dateFormat %>');">
							</c:if>
							<c:if test='${stampId != ""}'>
							<span id="sealDate" />
							</c:if>
							</td>
							<td class="tb_tit"><spring:message code="list.form.requestdate" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if>
							</td>
							<td class="tb_left_bg">
							<c:if test='${stampId == ""}'>
								<input id="requestDate" name="requestDate" type="text" class="input_read" maxlength="256" style="width: 88%;ime-mode:active;"  value="<%=startDate %>"  readonly="readonly" />
								<img id="calRequest" name="calRequest"
							        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
							        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
							        onclick="javascript:cal.select(event, document.getElementById('requestDateId'), document.getElementById('requestDate'), 'calRequest','<%= dateFormat %>');">
							</c:if>
							<c:if test='${stampId != ""}'>
							<span id="requestDate" />
							</c:if>
							</td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 발신명의 -->
							<td class="tb_tit" ><spring:message code="approval.form.sendertitle" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
							<c:if test='${stampId == ""}'>
								<input id="sender" 		name="sender"  msg='<spring:message code="list.msg.sendertitle.no" />' type="text" class="input" maxlength="256" style="width: 88%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',128)" required="Y"  />
							</c:if>
							<c:if test='${stampId != ""}'>
							${stampList.sender}
							</c:if>
							</td>
							<c:if test='${stampId != ""}'><!-- 시행일자 -->
							<td width="18%" class="tb_tit"><spring:message code="approval.form.enforcedate" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
								<span id="enforceDate" />	
							</td>
							</c:if>	
							<c:if test='${stampId == ""}'><!-- 수신자 -->						
							<td class="tb_tit"><spring:message code="approval.title.apprecip" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg">
								<input id="receiver" 	name="receiver"  msg='<spring:message code="list.msg.apprecip.no" />'	type="text" class="input" maxlength="256" style="width: 88%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',2000)" required="Y"  />
							<c:if test='${stampId != ""}'>
							${stampList.receiver}
							</c:if>
							</td>
							</c:if>
						</tr>
						<c:if test='${stampId != ""}'><!-- 수신자 -->	
						<tr>
						<td class="tb_tit"><spring:message code="approval.title.apprecip" /><c:if test='${stampId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
							<td class="tb_left_bg" colspan="3">								
							${stampList.receiver}
							</td>
						</tr>
						</c:if>
						<tr bgcolor="#ffffff">
							<td class="tb_tit" ><spring:message code="list.form.remark" /></td><!-- 사유 -->
							<td class="tb_left_bg" colspan="3" style="height: 100px">
							<c:if test='${stampId == ""}'>
							<textarea id="remark" name="remark" cols="10" style="width:100%;height:100%;" onkeyup="checkInputMaxLength(this,'',2000)"></textarea>
							</c:if>
							<c:if test='${stampId != ""}'>
							<span id="div_remark" style="width:100%;height:100px;overflow-y:auto;word-break:break-all;"><%= (stampList.getRemark() == null) ? "" : EscapeUtil.escapeHtmlDisp(stampList.getRemark()) %></span>
							<input id="remark" name="remark" type="hidden" value='${stampList.remark}' />
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
				<acube:button id="insertBtn" onclick="setRegist();" value="<%=insertBtn %>" type="2" class="gr" />
				<acube:space between="button" />
			</c:if>
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
				<acube:space between="button" />
				</acube:buttonGroup>
				</c:if>
				<c:if test='${stampId != ""}'>
				<acube:buttonGroup align="right">
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
				<acube:space between="button" />
				</acube:buttonGroup>
				</c:if>
				</td>
			</tr>
		</acube:tableFrame>
		<input id="docId" 	 		 name="docId" 	 			type="hidden" 	value=""  />
		<input id="stampId" 	 	 name="stampId" 	 		type="hidden" 	value="${stampId}"  />
		<input id="electronDocYn" 	 name="electronDocYn" 	 	type="hidden" 	value="N" />
		<input id="enforceDateId" 	 name="enforceDateId" 		type="hidden" 	value="<%=startDateId %>" />
		<input id="sealDateId" 		 name="sealDateId" 			type="hidden" 	value="<%=startDateId %>" />
		<input id="requestDateId" 	 name="requestDateId" 		type="hidden" 	value="<%=startDateId %>" />
		<input id="sealType" 		 name="sealType" 			type="hidden" 	value="${sealType}" /><!-- 서명인타입 -->
		
		<!-- 비밀번호 입력 팝업 -->
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
		<input type="hidden" id="btnName" name="btnName" value="" />
    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
		</form>
	</acube:outerFrame>
</body>
</html>