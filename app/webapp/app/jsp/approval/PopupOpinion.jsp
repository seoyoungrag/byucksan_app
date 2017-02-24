<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.Locale" %>
<% 
/** 
 *  Class Name  : PopupOpinion.jsp 
 *  Description : 의견 및 결재암호입력 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.19
 *   수 정 자 : jth8172 
 *   수정내용 : 요구반영 
 * 
 *  @author  jth8172
 *  @since 2011. 5. 19 
 *  @version 1.0 
 */ 
%> 				 

<% 
	response.setHeader("pragma","no-cache");	

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	//호출대상함수
	String returnFunction = CommonUtil.nullTrim(request.getParameter("returnFunction"));
	String mustOpinionYn = "N";
	//버튼명
	String btnName = CommonUtil.nullTrim(request.getParameter("btnName"));
	//의견여부
	String opinionYn = CommonUtil.nullTrim(request.getParameter("opinionYn"));
	//기 입력한 의견
	String popComment = CommonUtil.nullTrim(request.getParameter("comment"));

	String processBtn = btnName;
	String cancelBtn = messageSource.getMessage("approval.enforce.button.close" , null, langType);  
	
	String OpinionSubTitle =  messageSource.getMessage("approval.enforce.opinion" , null, langType);
	String OpinionTitle = btnName;
	
	if("Y".equals(opinionYn)){ // 의견입력란 있으면 제목에 의견을 표시함
	    OpinionTitle += " " + OpinionSubTitle;
	}
	
	String alertMsg = btnName + " " + messageSource.getMessage("approval.opinion.msg.inputopinion" , null, langType);
	String acceptMsg = messageSource.getMessage("approval.opinion.msg.accept" , null, langType);  
	
	String strDisplayYn = " style='display:none' ";
	// 의견 없이 암호만 체크하는 경우
	if("Y".equals(opinionYn)) {
	    strDisplayYn = "";
	}
	
	// 의견필수
 	if( "returnDocOk".equals(returnFunction) || // 반송
 	   	"moveOk".equals(returnFunction) || // 이송
 	   	"reDistRequestOk".equals(returnFunction) || // 재배부요청 
 	   	"rejectStampOk".equals(returnFunction) || // 심사반려 
 	   	"reSendOk".equals(returnFunction) || //재발송요청
 	   	"openReDefineLine".equals(returnFunction) || // 담당재지정
 	   	"unReigstDocOk".equals(returnFunction) || // 등록취소 
 	   	"reReigstDocOk".equals(returnFunction) || // 재등록
 	   	"updateNonAppDocOk".equals(returnFunction) || //비전자 생산문서 수정
 	   	"updateNonEnfDocOk".equals(returnFunction) || //비전자 접수문서 수정
 	   	"sendImpossibleOk".equals(returnFunction) || // 발송불가
 	   	"returnEnfDocOk".equals(returnFunction) || // 접수문서 결재경로재지정으로 변경
 	   	"retrieveDocByAdminOk".equals(returnFunction) // 관리자 비전자 접수문서 회수 
 	){ 
 	   mustOpinionYn = "Y";
 	}
	
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<title><%=OpinionTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>     
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script language="javascript">
var returnFunction = "<%=returnFunction%>";

function returnOk() {
	//의견을 리턴한다.
	if (opener != null) {
		opener.window.moveBy(0,0 );                   
		opener.focus();
	}
	var opinion = $('#popComment').val();
<% if ("returnDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.returnDocOk != null) {
		opener.returnDocOk(opinion);
	}
<% } else if ("moveOk".equals(returnFunction)) { %>
	if (opener != null && opener.moveOk != null) {
		opener.moveOk(opinion);
	}
<% } else if ("reDistRequestOk".equals(returnFunction)) { %>
	if (opener != null && opener.reDistRequestOk != null) {
		opener.reDistRequestOk(opinion);
	}
<% } else if ("rejectStampOk".equals(returnFunction)) { %>
	if (opener != null && opener.rejectStampOk != null) {
		opener.rejectStampOk(opinion);
	}
<% } else if ("reSendOk".equals(returnFunction)) { %>
	if (opener != null && opener.reSendOk != null) {
		opener.reSendOk(opinion);
	}
<% } else if ("openReDefineLine".equals(returnFunction)) { %>
	if (opener != null && opener.openReDefineLine != null) {
		opener.openReDefineLine(opinion);
	}
<% } else if ("unReigstDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.unReigstDocOk != null) {
		opener.unReigstDocOk(opinion);
	}
<% } else if ("reReigstDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.reReigstDocOk != null) {
		opener.reReigstDocOk(opinion);
	}
<% } else if("sendImpossibleOk".equals(returnFunction)) { %>
	if (opener != null && opener.sendImpossibleOk != null) {
		opener.sendImpossibleOk(opinion);
	}
<% } else if ("stopSendOk".equals(returnFunction)) { %>
	if (opener != null && opener.stopSendOk != null) {
		opener.stopSendOk(opinion);
	}
<% } else if ("sendCancelOk".equals(returnFunction)) { %>
	if (opener != null && opener.sendCancelOk != null) {
		opener.sendCancelOk(opinion);
	}
<% } else if ("sendOk".equals(returnFunction)) { %>
	if (opener != null && opener.sendOk != null) {
		opener.sendOk(opinion);
	}
<% } else if ("noSendOk".equals(returnFunction)) { %>
	if (opener != null && opener.noSendOk != null) {
		opener.noSendOk(opinion);
	}
<% } else if ("goUpdate".equals(returnFunction)) { %>
	if (opener != null && opener.goUpdate != null) {
		opener.goUpdate(opinion);
	}
<% } else if ("saveDocInfo".equals(returnFunction)) { %>
	if (opener != null && opener.saveDocInfo != null) {
		opener.saveDocInfo(opinion);
	}
<% } else if ("procRetrieveDoc".equals(returnFunction)) { %>
	if (opener != null && opener.procRetrieveDoc != null) {
		opener.procRetrieveDoc(opinion);
	}
<% } else if ("procRejectDoc".equals(returnFunction)) { %>
	if (opener != null && opener.procRejectDoc != null) {
		opener.procRejectDoc(opinion);
	}
<% } else if ("goOpinion".equals(returnFunction)) { %>
	if (opener != null && opener.goOpinion != null) {
		opener.goOpinion(opinion);
	}
<% } else if ("goEnf".equals(returnFunction)) { %>
	if (opener != null && opener.goEnf != null) {
		opener.goEnf(opinion);
	}
<% } else if ("procApprovalDoc".equals(returnFunction)) { %>
	if (opener != null && opener.procApprovalDoc != null) {
		opener.procApprovalDoc(opinion);
	}
<% } else if ("procPreOpen".equals(returnFunction)) { %>
	if (opener != null && opener.procPreOpen != null) {
		opener.procPreOpen(opinion);
	}
<% } else if ("acceptApprovalProcess".equals(returnFunction)) { %>
	if (opener != null && opener.acceptApprovalProcess != null) {
		opener.acceptApprovalProcess(opinion);
	}
<% } else if ("acceptProcess".equals(returnFunction)) { %>
	if (opener != null && opener.acceptProcess != null) {
		opener.acceptProcess(opinion);
	}
<% } else if ("reDistributeProcess".equals(returnFunction)) { %>
	if (opener != null && opener.reDistributeProcess != null) {
		opener.reDistributeProcess(opinion);
	}
<% } else if ("distributeProcess".equals(returnFunction)) { %>
	if (opener != null && opener.distributeProcess != null) {
		opener.distributeProcess(opinion);
	}
<% } else if ("moveProcess".equals(returnFunction)) { %>
	if (opener != null && opener.moveProcess != null) {
		opener.moveProcess(opinion);
	}
<% } else if ("enableSendOk".equals(returnFunction)) { %>
	if (opener != null && opener.enableSendOk != null) {
		opener.enableSendOk(opinion);
	}
<% } else if ("transferCallOk".equals(returnFunction)) { %>
	if (opener != null && opener.transferCallOk != null) {
		opener.transferCallOk(opinion);
	}
<% } else if ("saveHisProcess".equals(returnFunction)) { %>
	if (opener != null && opener.saveHisProcess != null) {
		opener.saveHisProcess(opinion);
	}
<% } else if ("goAjax".equals(returnFunction)) { %>
	if (opener != null && opener.goAjax != null) {
		opener.goAjax(opinion);
	}
<% } else if ("updateNonAppDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.updateNonAppDocOk != null) {
		opener.updateNonAppDocOk(opinion);
	}
<% } else if ("updateNonEnfDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.updateNonEnfDocOk != null) {
		opener.updateNonEnfDocOk(opinion);
	}
<% } else if ("returnEnfDocOk".equals(returnFunction)) { %>
	if (opener != null && opener.returnEnfDocOk != null) {
		opener.returnEnfDocOk(opinion);
	}
<% } else if ("retrieveDocByAdminOk".equals(returnFunction)) { %>
	if (opener != null && opener.retrieveDocByAdminOk != null) {
		opener.retrieveDocByAdminOk(opinion);
	}
<% } else { %>
	if (opener != null) {
		eval("opener." + returnFunction + "('" + escapeCarriageReturn(escapeJavaScript(opinion)) + "');");
	}
<% } %>
	closePopup();
}

//결재암호 체크
function chkPassword() {
	<% if ("1".equals(opt301)) { %>
		var password = $.trim($("#password").val());
		if (password == $.trim("")) {
			alert("<spring:message code='approval.msg.need.approvalpassword'/>");
			$("#password").focus();
			return;
		} else {
			prepareSeed();
			$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
			
			$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#opinionForm").serialize(), function(data){
				if (data.result != "success") {
					alert(data.message);
					$("#password").focus();
					return;
				} else {
					returnOk();	
				}	
			}, 'json');
		}
	<% } else if ("2".equals(opt301)) {%>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			returnOk();	
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		} else {
		    if (certificate()) {
		    	returnOk();	
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		    } else {
		        return false;
		    }
		}
	}, 'json').error(function(data) {
	    if (certificate()) {
	    	returnOk();	
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
	    } else {
	        return false;
	    }
	});
	<% } else {%>
		returnOk();	
	<% } %>

	
	
}

// 의견필수입력유무 체크
function checkOpinion() {
	var opinion = $.trim($("#popComment").val());
	// 아래 경우에만 의견 필수입력 체크를 한다. 
	if (opinion == $.trim("")) {
	 	if( "Y" == "<%=mustOpinionYn%>" ) { // 의견필수
			alert("<%=alertMsg%>");
			$("#popComment").focus();
			return false;
	 	}	
	} 
	
	chkPassword();

}
 
// 암호에서 엔터키 치면 버튼을 누른것으로 처리함
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


function prepareSeed() {
    var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();
    if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
        var userkey = document.getElementById("seedOCX").GetUserKey();
        currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
    }
    var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);
    $('#roundkey').val(roundkey_c);
}

function closePopup() {
	//창을 닫는다.
	self.close();
	
}
 
</script>
</head>
<body onunload="closePopup();" topmargin="0" leftmargin="0" style="margin: 0" >

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><%=OpinionTitle%></span></td>
</td>
</tr>
<% if ("returnEnfDocOk".equals(returnFunction) ) {  %>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td style="font-size:9pt"><spring:message code='approval.opinion.msg.returnEnfDoc'/>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% } %>
<!-- 여백 시작 -->
<tr><td colspan=2> 
<acube:space between="button_content" />
</tr>
<!-- 여백 끝 -->
<tr>
<td colspan=2>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
	<tr <%=strDisplayYn%> ><!-- 의견 -->
		<td width="100%" height="112">
		<table class="table_grow" width="100%" border="0" cellspacing="1"> 
			<tr bgcolor="#ffffff">
				<td width="30%" class="tb_tit" >
<% /* //접수의견을 발송부서에 전달하는 부분 제거 %>
<% if( "acceptProcess".equals(returnFunction) ) {  %>
<p/>&nbsp;<p/><p/>
<% } %>				
<% */ %>
				<%=OpinionTitle%>
				<% if ("Y".equals(mustOpinionYn)) { %>
				<spring:message code='common.title.essentiality'/>
				<% } %>
<% /* //접수의견을 발송부서에 전달하는 부분 제거 %>
<% if( "acceptProcess".equals(returnFunction) ) {  %>
<p><font style='font-weight:normal'><%=acceptMsg%></font></p>
<% } %>				
<% */ %>
				</td> 
				<td width="70%" class="tb_left_bg" valign="top" > 
					<textarea id="popComment" name="popComment" cols="60" rows="9" style="width:100%;height:100px;overflow:auto;ime-mode:active;" ><%=popComment%></textarea>
				</td>
			</tr>  
		</table>
		</td>  
	</tr>
<% if ("1".equals(opt301)) { %>
		<tr bgcolor="#ffffff"><!-- 암호 -->
			<acube:space between="button_content" />   
		</tr>   
		<tr style="height:10px" bgcolor="#ffffff">
			<td width="100%" style="height:10px">
				<table width="100%" style="height:10px" class='table_grow' border="0" bgcolor="#ffffff" cellpadding="0" cellspacing="1" marginheight="0">
					<tr bgcolor="#ffffff">
						<td width="30%" class="tb_tit" style="height:10px" ><spring:message code='approval.title.approval.password'/><spring:message code='common.title.essentiality'/></font></td>
						<td width="70%" class="tb_left_bg" style="height:10px" >
								<input type="password" id="password" name="password" style=" width:100%" class="input" onkeydown="chkKeyPress();return(true);"/>
							<form id="opinionForm" name="opinionForm" method="post" onsubmit="return(false);" style="margin:0;">
								<input type="hidden" id="roundkey" name="roundkey"/>
								<input type="hidden" id="encryptedpassword" name="encryptedpassword"/>
							</form>
						</td>
					</tr></table></td> 
		</tr>
 <% } %>		

</table>
</td>
</tr>
    <tr>
        <td height="10"></td>
    </tr>
	<tr><td colspan=2> 
	<acube:buttonGroup align="right">
	<%if("발송".equals(processBtn)){ %>
		<acube:button id="btnOk" disabledid="" onclick="checkOpinion();" value="저장" />
	<%}else{ %>
		<acube:button id="btnOk" disabledid="" onclick="checkOpinion();" value="<%=processBtn%>" />
	<% }%>
	<acube:space between="button" />
	<acube:button id="btnCalcel" disabledid="" onclick="closePopup();" value="<%=cancelBtn%>" />
	</acube:buttonGroup>
	</tr>
</table>
</acube:outerFrame>
<input type="hidden" id="SEC_IS_FOREIGN_USER" name="SEC_IS_FOREIGN_USER" value="false"/>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>