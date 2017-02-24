<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertOpinion.jsp 
 *  Description : 결재의견
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	
	String opt305 = appCode.getProperty("OPT305", "OPT305", "OPT"); // 반려문서결재 - 1 : 모든결재자 결재, 2 : 기 결재자 결재여부 선택
	opt305 = envOptionAPIService.selectOptionValue(compId, opt305);

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)

	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	String art060 = appCode.getProperty("ART060", "ART060", "ART"); //선람
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); //담당

	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	
	String opt317 = appCode.getProperty("OPT317","OPT317", "OPT");	// 알림설정
	HashMap<String, String> mapNoticeMode = envOptionAPIService.selectOptionTextMap(compId, opt317);

	String askType = CommonUtil.nullTrim(request.getParameter("askType"));
	String actType = CommonUtil.nullTrim(request.getParameter("actType"));
	String alarmYn = CommonUtil.nullTrim(request.getParameter("alarmYn"));
	String opinion = CommonUtil.nullTrim(request.getParameter("opinion"));
	
	boolean smsFlag = false;	//sms사용시 조건에 따라 smsflag true로 설정(mapNoticeMode.get("I3") : 알림설정, actType : 결재처리유형)
	
	// 버튼명
	String processBtn = "";
	if (apt002.equals(actType)) {
	    processBtn = messageSource.getMessage("approval.button.return", null, langType);
	} else if (art010.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.submit", null, langType);
	} else if (art020.equals(askType) || art021.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.consider", null, langType);
	} else if (art030.equals(askType) || art031.equals(askType) || art033.equals(askType) || art034.equals(askType) || art035.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.assistant", null, langType);	    
	} else if (apt051.equals(actType)) {
	    //processBtn = messageSource.getMessage("approval.button.agree", null, langType);	    김해성 팀장 요청. 찬성은 결재로 텍스트 변경
	    processBtn = messageSource.getMessage("approval.apptype.art050", null, langType);	    
	} else if (apt052.equals(actType)) {
	    processBtn = messageSource.getMessage("approval.button.disagree", null, langType);	    
	} else if (art130.equals(askType) || art131.equals(askType) || art133.equals(askType) || art134.equals(askType) || art135.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.agreesubmit", null, langType);	    
	} else if (art040.equals(askType) || art042.equals(askType) || art043.equals(askType) || art044.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.audit", null, langType);
	} else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType) || art053.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.approval", null, langType);
	} else if (art060.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.art60", null, langType);
	} else if (art070.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.art70", null, langType);
	}
	
	String cancelBtn = messageSource.getMessage("approval.button.cancel", null, langType); 
	
	boolean opinionFlag = true;
	if (art060.equals(askType) || art070.equals(askType)) {	//비전자문서 선람, 담당인경우 비활성화
	    opinionFlag = false;
	}
	
	// 본문문서 타입 
	String formBodyType = (String) request.getParameter("formBodyType");
	if (formBodyType == null || "".equals(formBodyType)) {
		opinionFlag = false;		
	} else if ("html".equals(formBodyType)) {
		opinionFlag = false;		
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.opinion'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<% if ("2".equals(opt305)) { %>		
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<% } %>
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$.ajaxSetup({async:false});

function initialize() {
	if (opener != null && opener.getAppLine != null) {
		var draftType = $("#draftType", opener.document).val();
		
		if(draftType == 'redraft-skip') {
			var opinion = $("#drafterOpinion", opener.document).val();
				
			//본문내 의견표시 특수문자가 있는 경우 제거 
			var opinionChk = opinion.indexOf(String.fromCharCode(15));
			
			if(opinionChk != -1) {
				opinion = opinion.substr(1);				
			}
			
			$("#opinion").val(opinion);
		}
	}
}

function chkKeyPress() {
	var keyCode = window.event.keyCode;
	switch (keyCode)
	{
		case 13:
		{
			checkOpinion();
		}
		default:
			break;
	}
}

function checkOpinion() {
	var opinion = $.trim($("#opinion").val());
<% if (apt002.equals(actType)) { %>
	if (opinion == $.trim("")) {
		alert("<spring:message code='approval.msg.need.returnopinion'/>");
		$("#opinion").focus();
		return false;
	} 
	var message = "<spring:message code='approval.msg.returnapproval'/>";
<% } else if (apt051.equals(actType)) { %>
	var message = "<spring:message code='approval.msg.agreeapproval'/>";
<% } else if (apt052.equals(actType)) { %>
	if (opinion == $.trim("")) {
		alert("<spring:message code='approval.msg.need.disagreeopinion'/>");
		$("#opinion").focus();
		return false;
	} 
	var message = "<spring:message code='approval.msg.disagreeapproval'/>";
<% } else { 
		if (art010.equals(askType)) { %>
	var message = "<spring:message code='approval.msg.submitapproval'/>";
<%	} else { %>
	var message = "<spring:message code='approval.msg.processapproval'/>";
<%	}
	} %>	

<% if ("1".equals(opt301)) { %>
	var password = $.trim($("#password").val());
	if (password == $.trim("")) {
		alert("<spring:message code='approval.msg.need.approvalpassword'/>");
		$("#password").focus();
		return false;
	} else {
		prepareSeed();
		$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
		
		$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#opinionForm").serialize(), function(data){
			if (data.result == "success") {
				submitOpinion(opinion, message);
			} else {
				alert(data.message);
				$("#password").focus();
				return false;
			}
		}, 'json');
	}
<% } else { %>
	submitOpinion(opinion, message);
<% } %>
}

function submitOpinion(opinion, message) {
	 window.moveBy(0,0 );   
	//의견 본문내 표시 체크 시 의견내용앞에 구분자 추가, kj.yang, 20120510 S
	if ($("#opinionYn").attr("checked")) {
		if(opinion != "") {
			opinion = String.fromCharCode(15) + opinion;
		}
	}
	//의견 본문내 표시 체크 시 의견내용앞에 구분자 추가, kj.yang, 20120510 E
	
<% if (smsFlag) {	// SMS %>
	if (opener.setSms) {
		var toNextApprover = "N";
		var toDrafter = "N";
		if ($("#alarm1").attr("checked")) {
			toNextApprover = "Y";
		}
		if ($("#alarm2").attr("checked")) {
			toDrafter = "Y";
		}
		opener.setSms(toNextApprover, toDrafter);
	}	
<% } %>	
<% if ("2".equals(opt301)) { %>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			if (opener != null && opener.setOpinion1) {
				opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>");
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				window.close();
			}
		} else {
			if (certificate()) {
				if (opener != null && opener.setOpinion1) {
					opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>");
					$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
					window.close();
				}
			} else {
				return false;
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
			if (opener != null && opener.setOpinion1) {
				opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>");
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				window.close();
			}
		} else {
			return false;
		}
	});
<% } else { %>
<% 	if ("0".equals(opt301)) { %>
	if (opener != null && opener.setOpinion1) {
		opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>");
		window.close();
	}
<% 	} else { %>
	if (opener != null && opener.setOpinion1) {
		<% if ("1".equals(opt301)) { %>	
		opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>", $("#encryptedpassword").val(), $("#roundkey").val()); 
		<% } else { %>
		opener.setOpinion1(opinion, "<%=askType%>", "<%=actType%>");
		<% } %>
		window.close();
	}
<% 	} %>
<% } %>
}

function cancelOpinion() {
	if (confirm("<spring:message code='approval.msg.cancelopinion'/>")) {
		window.close();
	}	
/*
	if(opener != null && ( opener.bReqAcc != null || opener.bApprovalok != null || opener.bPrereadok != null ) ){
		if(opener.bReqAcc != null){
			opener.bReqAcc = false;
		}
		if(opener.bApprovalok != null){
			opener.bApprovalok = false;
		}
		if(opener.bPrereadok != null){
			opener.bPrereadok = false;
		}
	}
	*/
}

function prepareSeed() {

    var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();

    if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
        var userkey = document.getElementById("seedOCX").GetUserKey();
        currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
    }

    var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);

    $('#roundkey').val(roundkey_c);
}

function closeNewOpinion(){
	if(opener != null && ( opener.bReqAcc != null || opener.bApprovalok != null || opener.bPrereadok != null ) ){
		if(opener.bReqAcc != null){
			opener.bReqAcc = false;
		}
		if(opener.bApprovalok != null){
			opener.bApprovalok = false;
		}
		if(opener.bPrereadok != null){
			opener.bPrereadok = false;
		}
	}
}
</script>
</head>

<body onunload="closeNewOpinion();" style="margin: 0">


<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
<% if (apt002.equals(actType)) { %>		
			<td><span class="pop_title77"><spring:message code='approval.title.returnopinion'/></span></td>
<% } else { %>
			<td><span class="pop_title77"><%=processBtn%><spring:message code='approval.title.popup.opinion'/></span></td>
<% } %>			
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
<% if (apt002.equals(actType)) { %>		
						<td width="25%" class="tb_tit"><span class="pop_title77">
							<spring:message code='approval.title.returnopinion'/><spring:message code='common.title.essentiality'/></span><br>
<% if (opinionFlag) { %>	
							<spring:message code='approval.title.show.opinion'/>&nbsp;<input type="checkbox" id="opinionYn" name="opinionYn"> <!-- 의견 본문내 표시 체크 여부, kj.yang, 20120510 -->
<% } %>	
						</td>
<% } else { %>
						<td width="25%" class="tb_tit">
							<%=processBtn%><spring:message code='approval.title.popup.opinion'/><br>
<% if (opinionFlag) { %>	
							<spring:message code='approval.title.show.opinion'/>&nbsp;<input type="checkbox" id="opinionYn" name="opinionYn"> <!-- 의견 본문내 표시 체크 여부, kj.yang, 20120510 -->
<% } %>	
						</td>
<% } %>			
						<td width="75%" class="tb_left_bg">
							<textarea id="opinion" name="opinion" style="width:100%;height:330px;overflow:auto;ime-mode:active;"><%=opinion%></textarea> 
						</td>
				</tr>
			</acube:tableFrame>
			</td>
		</tr>
<% if ("1".equals(opt301)) { %>		
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
						<td width="25%" class="tb_tit"><spring:message code='approval.title.approval.password'/><spring:message code='common.title.essentiality'/></td>
						<td width="75%" class="tb_left_bg">
							<input type="password" id="password" name="password" style=" width:100%" class="input" onkeydown="chkKeyPress();return(true);"></input>
						</td>
				</tr>
			</acube:tableFrame>
			</td>
		</tr>
<% } %>		
<% if (smsFlag) {	// SMS %>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
						<td width="25%" class="tb_tit"><spring:message code='approval.title.alarm.sms'/></td>
						<td width="35%" class="tb_left_bg">
							<input type="checkbox" id="alarm1" name="alarm1"/>
							<spring:message code='approval.form.alarm.nextapprover'/>
						</td>
						<td width="40%" class="tb_left_bg">
							<input type="checkbox" id="alarm2" name="alarm2" <%=("Y".equals(alarmYn) ? "checked" : "")%>/>
							<spring:message code='approval.form.alarm.drafter'/>
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
<% } %>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="checkOpinion();return(false);" value="확인" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="cancelOpinion();return(false);" value="<%=cancelBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<% if ("1".equals(opt301)) { %>		
<form id="opinionForm" name="opinionForm" method="post" onsubmit="return(false);">
	<input type="hidden" id="roundkey" name="roundkey"></input>
	<input type="hidden" id="encryptedpassword" name="encryptedpassword"></input>
</form>
<% } %>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>