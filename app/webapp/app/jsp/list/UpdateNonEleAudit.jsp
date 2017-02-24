<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="com.sds.acube.app.common.vo.AuditListVO"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : UpdateNonEleAudit.jsp.jsp 
 *  Description : 일상감사일지등록 
 *  Modification Information 
 * 
 *   수 정 일 :  2011-09-20
 *   수 정 자 : 장진홍
 *   수정내용 : 
 * 
 *  @author  
 *  @since 2011. 09. 20 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

	String insertBtn = messageSource.getMessage("appcom.button.update", null, langType); //등록
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	
	//이전다음
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

	String docId = request.getParameter("docId");
	docId = (docId == null? "" : docId);

	pageContext.setAttribute("docId",docId);
	
	
	//calendar 관련
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
	String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401
	
	String sAttach = "";
	
	if(!"".equals(docId)){
	    String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	    AuditListVO result = (AuditListVO)request.getAttribute("auditList");
		List<FileVO> fileVOs = result.getFileInfo();
		sAttach = EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft004));
	}

	String lobCode = (String) request.getParameter("lobCode");
	pageContext.setAttribute("lobCode", lobCode);
	pageContext.setAttribute("isExtWeb", isExtWeb);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="list.list.title.editaudit" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
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
	
	initializeFileManager();
	
	$("input.#approverName").bind("change",function(){textChange($(this));});
	$("input.#chargeDeptName").bind("change",function(){textChange($(this));});	

	//파일첨부
	loadAttach($("#attachFile").val());

	<c:if test='${docId != ""}'>

	$('#imgAppend').hide();
	$('#imgRemove').hide();	
	$('#imgUp').hide();
	$('#imgDown').hide();
	
	var receiveDate = "${auditList.receiveDate}";
	receiveDate = receiveDate.substring(0,10);
	receiveDate = receiveDate.replace(/-/g,"/");
	$('#receiveDate').html(receiveDate);

	var remark = $('#remark').val();
	var pattern = /\n/g;
	remark = escapeJavaScript(remark);
	remark = remark.replace(pattern,"<br />");
	$('#div_remark').html(remark);
	</c:if>

	//첨부파일 체크파일 없음
	$('input[name=attach_cname]:checkbox').hide();
	
	// 화면블럭해제
	screenUnblock();
}

//----------사용자 팝업관련------------------
function textChange(obj){
	$("input[group="+obj.attr('name')+"]").val("");
}

function goDept(){
	var width = 500;
	var height = 300;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2";
	appDoc = openWindow("dept", url, width, height); 
}

function setDeptInfo(dept){
	$('#chargeDeptId').val(dept.orgId);
	$('#chargeDeptName').val(dept.orgName );
}

function goUser(){
	var width = 500;
	var height = 310;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do";
	appDoc = openWindow("user", url, width, height); 
}
function setUserInfo(user){
	$('#approverName').val(user.userName);
	$('#approverId').val(user.userId);
	$('#approverPos').val(user.positionName);
	$('#approverDeptId').val(user.deptId);
	$('#approverDeptName').val(user.deptName);
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
	arrangeAttach();	

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
	var url = "<%=webUri%>/app/list/audit/updateNonEleAudit.do?method=1";	
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

	if(typeof(opener) !== "undefined"){
		if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
		    opener.listRefresh();	
		}
	}

	setTimeout(function(){closeSave();},100);
}

function closeSave(){
	alert('<spring:message code="app.alert.msg.13" />');	
	if (opener != null && opener.listRefreshCompulsion != null) {
	    opener.listRefreshCompulsion();
	}
	window.close();	
}

function closePopup(){
	window.close();
}


function test(){
	var arr = $('form').serializeArray();
	var str = "";
	for(var i = 0; i < arr.length; i++){
		var item = arr[i];
		str += item.name
		str += "<br />"
	}
	
	$('#divtest').html(str);
}

//현재 안건번호
function getCurrentItem() {
	return 1;
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
        
		var docId = $('#docId').val();
		
        var message = opener.getPreNextDoc(docId, "pre");
        if (message != null && message != "") {
            alert(message);
        }
    }
}

function moveToNext() {
    prevnext = true;

    if (opener != null && opener.getPreNextDoc != null) {
        var docId = $('#docId').val();

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
		<acube:titleBar><spring:message code="list.list.title.editaudit" /></acube:titleBar>
		</td>
		<td align="right">
		<!-- 
		<c:if test='${docId != ""}'>
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
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1" class="td_table">
			<tr bgcolor="#ffffff"><!-- 제목 -->
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.doctitle" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td class="tb_left_bg">
				<c:if test='${docId == ""}'>
					<input id="title" name="title" msg='<spring:message code="list.msg.title.doctitle.no" />' type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" required="Y" />
				</c:if>
				<c:if test='${docId != ""}'>
				${auditList.title}
				</c:if>
				</td>
			</tr>
			<tr bgcolor="#ffffff"><!-- 접수일자 -->
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.headerAcceptDate" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td class="tb_left_bg">
				<c:if test='${docId == ""}'>
				<input id="receiveDate" name="receiveDate" type="text" class="input_read" maxlength="256" style="width: 90;ime-mode:active;" value="<%=startDate %>" readonly="readonly" />
					<img id="calReceiveDate" name="calReceiveDate"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('receiveDateId'), document.getElementById('receiveDate'), 'calReceiveDate','<%= dateFormat %>');">
				</c:if>
				<c:if test='${docId != ""}'>
				<span id="receiveDate"></span>
				</c:if>
				</td>
			</tr>
			<tr bgcolor="#ffffff"><!-- 담당부서 -->
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.chargedept" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td  width="30%" class="tb_left_bg">
				<c:if test='${docId == ""}'>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="96%">
					<input id="chargeDeptName" 		name="chargeDeptName"  msg='<spring:message code="list.msg.title.chargedept.no" />'	type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="Y" />
					<input id="chargeDeptId" 		name="chargeDeptId" 		type="hidden" group="chargeDeptName" />
					</td>
					<td>&nbsp;</td><td><a href="javascript:goDept();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
				</tr>
				</table>
				</c:if>
				<c:if test='${docId != ""}'>
				${auditList.chargeDeptName}
				</c:if>
				</td>
			</tr>
			<c:if test='${auditList.electronDocYn == "N"}'>
			<tr bgcolor="#ffffff"><!-- 결재구분 -->
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.approvediv" /></td>
				<td class="tb_left_bg">
				<c:if test='${docId == ""}'>
					<input id="approverType" name="approverType" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" />
					<input id="approveType" name="approveType"  type="hidden"/>
				</c:if>
				<c:if test='${docId != ""}'>
				${auditList.approverType}
				</c:if>
				</td>
			</tr>
			</c:if>
			<tr bgcolor="#ffffff">
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.approver" /><spring:message code='common.title.essentiality'/></td>
				<td  class="tb_left_bg">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="96%">
					<input id="approverName" 		name="approverName"  msg='<spring:message code="list.msg.title.approver.no" />'	type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',64)" required="Y" value='<c:out value="${auditList.approverName}" />' />
					<input id="approverId" 			name="approverId" 			type="hidden" group="approverName" value='<c:out value="${auditList.approverId}"/>' />
					<input id="approverPos" 		name="approverPos" 			type="hidden" group="approverName" value='<c:out value="${auditList.approverPos}"/>' />
					<input id="approverDeptId" 		name="approverDeptId" 		type="hidden" group="approverName" value='<c:out value="${auditList.approverDeptId}"/>' />
					<input id="approverDeptName" 	name="approverDeptName" 	type="hidden" group="approverName" value='<c:out value="${auditList.approverDeptName}"/>' />
					</td>
					<td>&nbsp;</td><td><a href="javascript:goUser();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
				</tr>
				</table>
				</td>
			</tr>
			<c:if test='${auditList.electronDocYn == "N"}'>
			<tr bgcolor="#ffffff">
					<td class="tb_tit"><spring:message code="approval.form.attach" /></td><!-- 첨부파일 -->
					<td class="tb_left_bg" height="60">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						<tr>
							<td width="100%">
								<div id="divattach" style="background-color:#ffffff;border:0px solid;height:30px;width:100%;overflow:auto;">
								</div>
							</td>
							<td>
							<c:if test='${docId == ""}'>
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
							</c:if>
							</td>
					</tr>
					</table>
					</td>
				</tr>
	</c:if>
			<tr bgcolor="#ffffff">
				<td  width="10%" class="tb_tit" ><spring:message code="list.list.title.remark" /></td><!-- 사유 -->
				<td class="tb_left_bg">
				<textarea id="remark" name="remark"  rows="10" cols="10" style="width: 100%;word-break:break-all;" onkeyup="checkInputMaxLength(this,'',2000)"><c:out value='${auditList.remark}' /></textarea>
				</td>
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
				<acube:buttonGroup align="right">
			<c:if test="${!isExtWeb}">
				<acube:button id="insertBtn" onclick="setRegist();" value="<%=insertBtn %>" />
				<acube:space between="button" />
			</c:if>
				<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" />
				</acube:buttonGroup>
			</td>
		</tr>
		</acube:tableFrame>
		<div id="approvalitem1" name="approvalitem">
		<input id="docId" 	 		 name="docId" 	 			type="hidden" 	value="${docId }"  />
		<input id="electronDocYn" 	 name="electronDocYn" 	 	type="hidden" 	value="N" />
		<input id="receiveDateId" 	 name="receiveDateId" 		type="hidden" 	value="<%=startDateId %>" />
		<input id="attachFile" 		 name="attachFile" 			type="hidden" 	value="<%=sAttach %>" />
		
		<!-- 비밀번호 입력 팝업 -->
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
		<input type="hidden" id="btnName" name="btnName" value="" />
    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
		</div>
		</form>
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>