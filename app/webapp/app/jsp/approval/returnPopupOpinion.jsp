<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

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
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	
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

	String processBtn = messageSource.getMessage("approval.enforce.button.return" , null, langType);
	String cancelBtn = messageSource.getMessage("approval.enforce.button.close" , null, langType);
	String passBtn = messageSource.getMessage("approval.enforceinfo.msg.notapplicable" , null, langType);
	
	String OpinionSubTitle =  messageSource.getMessage("approval.enforce.opinion" , null, langType);
	String OpinionTitle = processBtn;
	
	if("Y".equals(opinionYn)){ // 의견입력란 있으면 제목에 의견을 표시함
	    OpinionTitle += " " + OpinionSubTitle;
	}
	
	String alertMsg = btnName + " " + messageSource.getMessage("approval.opinion.msg.inputopinion" , null, langType);
	String acceptMsg = messageSource.getMessage("approval.opinion.msg.accept" , null, langType);
	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
	int docCount = appDocVOs.size();

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<title><%=OpinionTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">


// 의견필수입력유무 체크
function checkOpinion() {
	var opinion = $.trim($("#popComment").val());
	// 아래 경우에만 의견 필수입력 체크를 한다. 
	if (opinion == $.trim("")) {
		alert("<%=alertMsg%>");
		$("#popComment").focus();
		return false;
	} else {
		setOpinion(opinion);		
	}
}

function setOpinion(opinion) {
	var currentLineNum = 1;
	var itemnum = getCurrentItem();
	var itemCount = getItemCount();

	for (var loop = 0; loop < itemCount; loop++) {

		var appline = $("#appLine", "#approvalitem" + (loop + 1)).val();
		var approvers = appline.split(String.fromCharCode(4));
		var approverslength = approvers.length;
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				var approver = approvers[pos].split(String.fromCharCode(2));
				if ((approver[13] == "<%=apt003%>" || approver[13] == "<%=apt004%>")) {
					approver[19] = opinion;
					approver[22] = getCurrentDate();
					currentLineNum = approver[27];
		
					var appinfo = "";
					var applength = approver.length; 
					for (var subpos = 0; subpos < applength; subpos++) {
						if (subpos == applength - 1) {
							appinfo += approver[subpos];
						} else {
							appinfo += approver[subpos] + String.fromCharCode(2);
						}
					}
					approvers[pos] = appinfo;
					break;
				} 
			}
		}

		appline = "";
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				appline += approvers[pos] + String.fromCharCode(4);
			}
		}
		$("#appLine", "#approvalitem" + (loop + 1)).val(appline);

	}

	setTimeout(function(){returnApproval();}, 100);
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

function getCurrentDate() {
	return "<%=DateUtil.getCurrentDate()%>";
}

function checkPass(){
	$.post("<%=webUri%>/app/approval/returnPassAppDoc.do", $("#appDocForm").serialize(), function(data){
		alert(data.message);
		opener.location.reload();
		closePopup();
	}, 'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.return'/>");
		}
	});
}

function returnApproval() {
	
	$.post("<%=webUri%>/app/approval/returnDeptAppDocList.do", $("#appDocForm").serialize(), function(data){
		alert(data.message);
		opener.location.reload();
		closePopup();
	}, 'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.return'/>");
		}
	});
}
 
function closePopup() {
	//창을 닫는다.
	self.close();
	
}
 
</script>
</head>
<body onunload="closePopup();" topmargin="0" leftmargin="0" >
<acube:outerFrame>
<table width="99%" border="0" cellpadding="0" cellspacing="0">
<tr><td colspan="2">
<acube:titleBar><%=processBtn%> </acube:titleBar>
</td>
</tr>
<!-- 여백 시작 -->
<tr><td colspan=2> 
<acube:space between="button_content" />
</tr>
<!-- 여백 끝 -->
<tr>
<td colspan=2>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" height="112">
		<table class="table_grow" width="100%" border="0" cellspacing="1"> 
			<tr bgcolor="#ffffff">
				<td width="30%" class="tb_tit" ><spring:message code='approval.enforceinfo.msg.notapplicable'/></td> 
				<td width="70%" class="tb_left_bg" valign="top" ><spring:message code='approval.enforceinfo.msg.notapplicabledata'/></td>
			</tr>  
			<tr bgcolor="#ffffff">
				<td width="30%" class="tb_tit" ><spring:message code='approval.enforce.button.return'/></td> 
				<td width="70%" class="tb_left_bg" valign="top" ><spring:message code='approval.enforceinfo.msg.returnreason'/></td>
			</tr>
			<tr bgcolor="#ffffff">
				<td width="100%" class="tb_left_bg" valign="top" colspan="2"> 
					<textarea id="popComment" name="popComment" cols="60" rows="9" style="width:100%;height:100px;overflow:auto;ime-mode:active;" ><%=popComment%></textarea>
				</td>
			</tr> 
		</table>
		</td>  
	</tr>
</table>
</td>
</tr>
    <tr>
        <td height="10"></td>
    </tr>
	<tr><td colspan=2> 
	<acube:buttonGroup align="right">
	<acube:button id="btnPass" disabledid="" onclick="checkPass();" value="<%=passBtn%>" />
	<acube:space between="button" />
	<acube:button id="btnOk" disabledid="" onclick="checkOpinion();" value="<%=processBtn%>" />
	<acube:space between="button" />
	<acube:button id="btnCalcel" disabledid="" onclick="closePopup();" value="<%=cancelBtn%>" />
	</acube:buttonGroup>
	</tr>
</table>
</acube:outerFrame>
<form id="appDocForm" name="appDocForm" method="post">
<%
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
%>
	<div id="approvalitem<%=(loop+1)%>" name="approvalitem">
		<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- 문서ID -->
		<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input><!-- 보고경로 -->
	</div>
<%
	}
%>	
</form>

<input type="hidden" id="SEC_IS_FOREIGN_USER" name="SEC_IS_FOREIGN_USER" value="false"/>
<jsp:include page="/app/jsp/common/seed.jsp" />
</body>
</html>