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
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
	String docTitle = (String)request.getParameter("title");
	String drafterName = (String)request.getParameter("drafterName");
	String drafterPos = (String)request.getParameter("drafterPos");
	String drafterDeptName = (String)request.getParameter("drafterDeptName");
	String draftdate = (String)request.getParameter("draftdate");
	String referer = (String) request.getHeader("REFERER");

	String D1 = (String) request.getParameter("D1");
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
	    processBtn = messageSource.getMessage("approval.button.agree", null, langType);	    
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
<title>벽산 전자결재</title>
<%-- <link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" /> --%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<link rel="shortcut icon" href="favicon.ico">
<link rel="apple-touch-icon-precomposed" href="icon57.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="icon114.png">

<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app_mobile/js/libs/jquery-2.0.2.min.js"></script><!-- jQuery -->
<script type="text/javascript" src="<%=webUri%>/app_mobile/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app_mobile/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app_mobile/js/iscroll.js"></script>
<script type="text/javascript" src="<%=webUri%>/app_mobile/js/jquery.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/button.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/font_icon.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/community/style.css">

<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<% if ("2".equals(opt305)) { %>		
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<% } %>
<script type="text/javascript">
var d1 = "<%=D1%>";
$(document).ready(function() { 
	initialize(); 
	$("#searcharea").hide();
});
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
	if (confirm("<spring:message code='approval.msg.cancelapproval'/>")) {
		/* window.close(); */
		document.location.href = "<%=referer%>";
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

function goApprovalBox(index){
	
	switch(index){
	case 0:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;				//수신함
		break;
	case 1:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_processBox.jsp?D1='+d1;				//진행함
		break;                    
	case 2:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_completeBox.jsp?D1='+d1;				//완료함
		break;                    
	case 3:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_draftBox.jsp?D1='+d1;				//기안함
		break;                    
	case 4:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_rearBox.jsp?D1='+d1;					//후열함
		break;                    
	case 5:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_rejectBox.jsp?D1='+d1;				//반려함
		break;                    
	case 6:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS002&D1='+d1;				//공람게시 (부서)
		break;                    
	case 7:                       
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS005D1='+d1;				//공람게시 (회사)
		break;
	}
}
</script>
</head>

<body onunload="closeNewOpinion();" style="margin: 0">
	<header>
		<%@ include file="/app_mobile/approval/toggleMenu.jsp" %> 
	</header>
	
	<div class="sub_top msn_top">
		<a href="#none" class="navbtn"></a>
		<div class="title">전자결재</div>
		<!-- <div class="search"><a><img src="../img/community/top_search_white_btn.png" alt="검색"></a></div> -->
	</div> 
    <div id="wrapper" style="overflow:hidden;">
		<div id="scroller">
		     <div class="titleBar">
		     <p class="subTitle">반려하기</p>
		     </div>
		    <div class="sub_content">
				<ul class="list V_Limg_list Rdiv group">
				<!-- <ul> -->
					<li class="group">
						<a style="height:60px;"><img src="/ep/app_mobile/img/community/sample.jpg" alt="이미지"/>
							<div class="rf">
								<p class="title" id="title"><%=docTitle%></p>
								<p class="status">
		                        <span><%=drafterName%></span> <%=drafterPos%>&nbsp;(<%=drafterDeptName%>)</span>
		                        <span><%=draftdate%></span>
		                        </p>
							</div>
							<div class="rsf"></div>
						</a>
					</li>
		            
					
				</ul>
		       </div>
		         
		        <div class="list_write">
		        <textarea placeholder="의견을 작성해 주십시오." class="input_content"></textarea>
		             <div class="btnset_r">
						<a href="javascript:cancelOpinion();" class="cancel_btn">취소</a>&nbsp;
						<a href="javascript:checkOpinion();" class="confirm_btn">반려</a>
					</div>
		            </div>
		            
		            </div>
		            
		            </div>
		
		<div class="moreNtop">
				<a href="#none" class="more_btn"><span class="txt">20개 더보기</span>&nbsp;&nbsp;<span class="info">20 / 55</span></a>
				<a href="#none" class="top_btn"><img src="../img/community/top_btn.png"></a>
			</div>
		
		<% if ("1".equals(opt301)) { %>		
		<form id="opinionForm" name="opinionForm" method="post" onsubmit="return(false);">
			<input type="hidden" id="roundkey" name="roundkey"></input>
			<input type="hidden" id="encryptedpassword" name="encryptedpassword"></input>
		</form>
		<% } %>
		<jsp:include page="/app/jsp/common/seed.jsp" />
		</div>
	</div>
<%@ include file="/app_mobile/approval/footer.jsp" %>
</body>
</html>