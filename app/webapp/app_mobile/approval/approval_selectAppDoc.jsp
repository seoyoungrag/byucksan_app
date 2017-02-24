<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.common.vo.DocHisVO,
				com.sds.acube.app.appcom.vo.SendProcVO,
				 com.sds.acube.app.common.util.DateUtil "%>
<%
/** 
 *  Class Name  : SelectDocInfo.jsp 
 *  Description : 문서정보 조회 
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
	String iam_url = AppConfig.getProperty("iam_url", "", "organization");

	String D1 = (String) request.getParameter("D1");
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서사용여부 - Y : 사용, N : 사용안함
	//관련문서 비활성화(본문하단에 표시 됨) - 추후 사용을 위해 처리
	//opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	opt321 = "N";
	String opt344 = appCode.getProperty("OPT344", "OPT344", "OPT"); // 관련규정사용여부 - Y : 사용, N : 사용안함
	opt344 = envOptionAPIService.selectOptionValue(compId, opt344);
	String opt348 = appCode.getProperty("OPT348", "OPT348", "OPT"); // 거래처사용여부 - Y : 사용, N : 사용안함
	opt348 = envOptionAPIService.selectOptionValue(compId, opt348);

	String OPT051 = appCode.getProperty("OPT051", "OPT051", "OPT"); // 발의
	OPT051 = envOptionAPIService.selectOptionText(compId, OPT051);
	String OPT052 = appCode.getProperty("OPT052", "OPT052", "OPT"); // 보고
	OPT052 = envOptionAPIService.selectOptionText(compId, OPT052);
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사 
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재) 
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	
	String art099 = appCode.getProperty("ART099", "ART099", "ART"); // 행정기관 : 발송실패
	String art100 = appCode.getProperty("ART100", "ART100", "ART"); // 행정기관 : 재발송성공

	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // 첨부수정
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // 문서정보수정(결재완료전)
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)
	String dhu021 = appCode.getProperty("DHU021", "DHU021", "DHU"); // 관리자삭제
	String dhu022 = appCode.getProperty("DHU022", "DHU022", "DHU"); // 등록취소
	String dhu023 = appCode.getProperty("DHU023", "DHU023", "DHU"); // 관리자등록취소
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // 결재경로수정
	String dhu026 = appCode.getProperty("DHU026", "DHU026", "DHU"); // 첨부날인수정
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록

	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
	       
	// 버튼명
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
	String modifyBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType);
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.detailbutton", null, langType); // 상세보기
	
	String autoSendYn = CommonUtil.nullTrim((String) request.getAttribute("autosendyn"));
	String auditReadYn = CommonUtil.nullTrim((String) request.getAttribute("auditReadYn"));
	String auditYn = CommonUtil.nullTrim((String) request.getAttribute("audityn"));
	String publicPost = CommonUtil.nullTrim((String) request.getAttribute("publicpost"));
	String closeBind = CommonUtil.nullTrim((String) request.getAttribute("closeBind"));
	String transferYn = CommonUtil.nullTrim((String) request.getAttribute("transferYn"));
	String securityYn = CommonUtil.nullTrim((String) request.getParameter("securityYn"));//jkkim added 2012.04.18
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	List<DocHisVO> docHisVOs = (ArrayList) request.getAttribute("docHisVOs");
	List<SendProcVO> ProcVOs = (List<SendProcVO>) request.getAttribute("ProcVOs");
	
	// 본문문서 파일 종류(HWP, DOC, HTML)
	String bodyType = (String)request.getAttribute("bodyType");
	
	AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");
	List<AppRecvVO> appRecvVOs = (List<AppRecvVO>) appDocVO.getReceiverInfo();	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<%-- <link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" /> --%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<link rel="shortcut icon" href="favicon.ico">
<link rel="apple-touch-icon-precomposed" href="icon57.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="icon114.png">

<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/libs/jquery-2.0.2.min.js"></script><!-- jQuery -->
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/iscroll.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/appCodeMessage.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/button.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/font_icon.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/community/style.css">

<script type="text/javascript">
$(document).ready(function() { 
		initialize();
		displayfunc(0);
		
});

var applineHisWin = null;
var bodyHisWin = null;
var attachHisWin = null;
var docLinkedWin = null;
var relatedDocWin =  null;
var openLevelWin = null;
var passwordWin = null;
var docType = "";
var bodyType = "<%= bodyType %>"; 
var d1 = "<%=D1%>";
function initialize() {
	// by 서영락, 수신자정보 2016-01-13
	<%
	if(appDocVO!=null){
		%>
		var content2Html = "";
		content2Html += "<li class='group' style='padding:12px 0px 12px 16px'>";
		content2Html += "<img src='<%=iam_url%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId=<%=appDocVO.getDrafterId()%>' class='circleImg'/>";
		content2Html += "<div class='rf'>";
		content2Html += "<p class='title' id='content2Title'><%=appDocVO.getTitle()%></p>";
		content2Html += "<p class='status'><span id='content2Name'><%=appDocVO.getDrafterName()%></span><span id='content2Pos'><%=appDocVO.getDrafterPos()%></span><span id='content2DeptName'>(<%=appDocVO.getDrafterDeptName()%>)</span><span id='content2DraftTime'><%=appDocVO.getDraftDate()%></span></p>" ;
		content2Html += "</div><div class='rsf'></div>";
		$('#content2 .sub_content ul').html(content2Html);
		<%
	}
	%>
	<%
	if(appRecvVOs != null){
		if(appRecvVOs.size()!=0){
			for(int i =0; i <appRecvVOs.size(); i++){
			%>
			$('#receiverinfoTable tbody').append('<tr><td><%=appRecvVOs.get(i).getRecvDeptName()%></td><td><%=appRecvVOs.get(i).getRefDeptName()%></td></tr>');
			<%
			}
		}else{
			%>
			$('#receiverinfoTable tbody').append('<tr><td colspan=2>수신자 이력이 없습니다.</td></tr>');
			<%
		}
	}
	%>
	////////////////////////////

	
<% if (!"Y".equals(closeBind)) { %>
	// 수정권한
	if (opener != null && opener.getEditAuthority != null) {
		var hasAuthority = opener.getEditAuthority();
		if (hasAuthority) {
			$("#editButton").show();
		}
	}
<% } %>
setOpenLevelValue("<%=appDocVO.getOpenLevel()%>","<%=appDocVO.getOpenReason()%>");
	// 문서정보
	
		var docInfo = getDocInfo();
		$("#docId").val(docInfo.docId);
		
		<% if(securityYn.equals("Y")){%>
		if(docInfo.securityStartDate != null&&docInfo.securityEndDate != null){
			var securityDate = docInfo.securityStartDate.substring(0,10) +" ~ "+ docInfo.securityEndDate.substring(0,10);
			$("#securityDate").text(securityDate);
		}

		<%}%>
		
		if (docInfo.classNumber != "undefined" && docInfo.classNumber != "" && docInfo.classNumber != null) {
			$('#classNumberTR').show();
			$("#divDocKind").html(docInfo.classNumber + " [" + docInfo.docnumName + "]");
		}else{
			$('#classNumberTR').hide();
		}

		$("#title").text(docInfo.title);		
		if (docInfo.transferYn == "Y") {
			if (docInfo.serialNumber > 0) {
				if (docInfo.subserialNumber > 0) {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
				} else {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
				}
			} else {
				$("#docNumber").text(docInfo.deptCategory);
			}
		} else {
			if (docInfo.serialNumber > 0) {
				if (docInfo.subserialNumber > 0) {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
				} else {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
				}
			}else{
				$("#docNumber").text(docInfo.deptCategory);
			}
		}
		//편철 정보가 존재 할때 정보 보여주기, jd.park, 20120509
		if (docInfo.bindingName != "") {
			$('#bindTR').show();
			$("#bindingId").val(docInfo.bindingId);
			$("#bindingName").text(docInfo.bindingName);	
		}else{
			$('#bindTR').hide();
			$("#bindingId").val("");
		}
		
		setOpenLevelValue("<%=appDocVO.getOpenLevel()%>","<%=appDocVO.getOpenReason()%>");
			
		$("#readRange").text(typeOfReadRange(docInfo.readRange));
		$("#senderTitle").text(docInfo.senderTitle);
		if (docInfo.auditReadYn == "Y") {
			$("#auditReadY").attr("checked", true);
			$("#auditReadN").attr("checked", false);
			$("#auditReadN").attr("disabled", true);
		} else {
			$("#auditReadY").attr("checked", false);
			$("#auditReadN").attr("checked", true);
		}
		$("#auditReadReason").val(docInfo.auditReadReason);
<% if ("Y".equals(auditYn)) { %>
		if (docInfo.auditYn == "Y") {
			$("#auditY").attr("checked", true);
			$("#auditN").attr("checked", false);
		} else {
			$("#auditY").attr("checked", false);
			$("#auditN").attr("checked", true);
		}
<% } %>
		$("#bindingName").text(docInfo.bindingName);
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		}
<% if ("Y".equals(autoSendYn)) { %>
		if (docInfo.autoSendYn == "Y") {
			$("#autoSendYn").attr("checked", true);
		} else {
			$("#autoSendYn").attr("checked", false);
		}
<% } %>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
		if (docInfo.publicPost != "") {
			$("#publicPost").text(typeOfReadRange(docInfo.publicPost));
		}
<% } %>	
	 

	// 결재경로정보
		var appline = getAppLine();
		var approver = getApproverList(appline);
		var approversize = approver.length;
		var tbAppLines = $('#tbAppLines');
		for (var loop = 0; loop < approversize; loop++) {
			var dept = approver[loop].approverDeptName;
			var pos = approver[loop].approverPos;
			var uid = approver[loop].approverId;
			var name = approver[loop].approverName;
			var reppos = approver[loop].representativePos;			
			var repname = approver[loop].representativeName;
			var role = approver[loop].approverRole;			
			var bodyHisId = approver[loop].bodyHisId;
			if (docInfo.transferYn == "Y") {
				bodyHisId = "";
			}
			var fileHisId = approver[loop].fileHisId;
			var lineHisId = approver[loop].lineHisId;
			var asktype = typeOfApp(approver[loop].askType);

			if (role!="") {  //발의 및 보고 체크// jth8172 2012 신결재 TF
			 	var strAddTxt ="";
			
				if( role.indexOf("OPT051")> -1  ) {  //발의
						strAddTxt = "<%=OPT051%>" ;
						if( role.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( role.indexOf("OPT052")> -1  ) {  //보고
						strAddTxt += "<%=OPT052%>" ;
				}  
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]";
					asktype = asktype + strAddTxt;
				}	
			}	
			
			var proctype = approver[loop].procType;
			var absent = approver[loop].absentReason;
			var date = approver[loop].processDate;
			var opinion = approver[loop].procOpinion;
			var readDate = approver[loop].readDate;

			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang S
			var opinionChk = opinion.indexOf(String.fromCharCode(15));
			if(opinionChk != -1) {
				opinion = opinion.substr(1);				
			}
			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang E			

			var row = makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readDate,uid,loop);
			tbAppLines.append(row);
		}
	
	
	// 수신자정보
	if (opener != null && opener.getAppRecv != null) {
		var recv = opener.getAppRecv();
		var receiver = getReceiverList(recv.appRecv);
		var recvsize = receiver.length;
		var tbRecvLines = $('#tbRecvLines tbody');
		if (recvsize > 0) {
			for (var loop = 0; loop < recvsize; loop++) {
				var sName = receiver[loop].recvDeptName;
				var sRef = typeOfStr(receiver[loop].refDeptName);
				var sSymbol = typeOfStr(receiver[loop].recvSymbol);
				if (receiver[loop].recvUserName != "") {
					sRef = receiver[loop].recvUserName;
				}
				if (receiver[loop].enfType == "<%=appCode.getProperty("DET006", "DET006", "DET")%>") {
					sSymbol = typeOfStr(receiver[loop].fax);
				}
				else if (receiver[loop].enfType == "<%=appCode.getProperty("DET007", "DET007", "DET")%>") {
					var brTag = "";
					if(receiver[loop].postNumber != null && receiver[loop].postNumber != ""
						&& receiver[loop].address != null && receiver[loop].address != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sRef = typeOfStr(receiver[loop].postNumber+brTag+receiver[loop].address);
					if(receiver[loop].telephone != null && receiver[loop].telephone != ""
						&& receiver[loop].fax != null && receiver[loop].fax != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sSymbol = typeOfStr(receiver[loop].telephone+brTag+receiver[loop].fax);
				}
				var row = makeReciver(sName, sRef, sSymbol);
				tbRecvLines.append(row);
			}
		} else {
			var row = makeNoReciver();
			tbRecvLines.append(row);
		}
		if (recv.displayNameYn == "Y") {
			$('#chkDisplayAs').attr("checked", true);
			$('#displayAs').text(recv.receivers);
		} else {
			$('#chkDisplayAs').attr("checked", false);
		}
	}

<% if ("Y".equals(opt321)) { %>
	// 관련문서
	if (opener != null && opener.getRelatedDoc != null) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');
		var relateddoc = opener.getRelatedDoc();
		if (relateddoc == "") {
			var row = makeNonRelatedDoc();
			tbRelatedDocs.append(row);
		} else {
			var related = getRelatedDocList(relateddoc);
			var relateddocsize = related.length;

			for (var loop = 0; loop < relateddocsize; loop++) {
				var docId = related[loop].docId;
				var title = related[loop].title;
				var usingType = related[loop].usingType;
				var electronDocYn = related[loop].electronDocYn;
				var row = makeRelatedDoc(docId, title, usingType, electronDocYn);
				tbRelatedDocs.append(row);
			}
		}
	}
<% } %>	

<% if ("Y".equals(opt344)) { %>
	// 관련규정
	if (opener != null && opener.getRelatedRule != null) {
		var tbRelatedRules = $('#tbRelatedRules tbody');
		var relatedrule = opener.getRelatedRule();

		if (relatedrule == "") {
			var row = makeNonRelatedRule();
			tbRelatedRules.append(row);
		} else {
			var related = getRelatedRuleList(relatedrule);
			var relatedrulesize = related.length;

			for (var loop = 0; loop < relatedrulesize; loop++) {
				var ruleId = related[loop].ruleId;
				var ruleLink = related[loop].ruleLink;
				var ruleName = related[loop].ruleName;
				var row = makeRelatedRule(ruleId, ruleLink, ruleName);
				tbRelatedRules.append(row);
			}
		}
	}
<% } %>	

<% if ("Y".equals(opt348)) { %>							
// 거래처
if (opener != null && opener.getCustomer != null) {
	var tbCustomers = $('#tbCustomers tbody');
	var customer = opener.getCustomer();

	if (customer == "") {
		var row = makeNonCustomer();
		tbCustomers.append(row);
	} else {
		var customers = getCustomerList(customer);
		var customersize = customers.length;

		for (var loop = 0; loop < customersize; loop++) {
			var customerId = customers[loop].customerId;
			var customerName = customers[loop].customerName;
			var row = makeCustomer(customerId, customerName);
			tbCustomers.append(row);
		}
	}
}
<% } %>

<%
// 수발신이력
if (ProcVOs != null && ProcVOs.size() > 0) { 
%>
    var tbProc = $('#tbProc tbody');
<%
    int procCount = ProcVOs.size();
    if (procCount == 0) {
%>	
	var row = makeNonProc();
	tbProc.append(row);
<%			
    } else {
    	for (int loop = 0; loop < procCount; loop++) {
    		SendProcVO sendProcVO = ProcVOs.get(loop);
    		String procType = messageSource.getMessage("approval.procinfo.form.proctype."+sendProcVO.getProcType().toLowerCase(), null, langType);
%>	
			var row<%=loop%> = makeProc("<%=sendProcVO.getProcOrder()%>", "<%=procType%>", 
					"<%=EscapeUtil.escapeJavaScript(sendProcVO.getProcessorName())%>", 
					"<%=EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(sendProcVO.getProcessDate() ) ) %>", 
					"<%=EscapeUtil.escapeDate(DateUtil.getFormattedDate(sendProcVO.getProcessDate()))%>", 
					"<%=EscapeUtil.escapeJavaScript(sendProcVO.getProcOpinion())%>");
			tbProc.append(row<%=loop%>);
<%		
    	} //for
    }
}
%>

<%
	// 문서이력
	if (docHisVOs != null) { 
%>
	    var tbDocHis = $('#tbDocHis tbody');
<%
	    int hisCount = docHisVOs.size();
	    if (hisCount == 0) {
%>	
	var row = makeNonDocHis();
	tbDocHis.append(row);
<%			
	    } else {
	    	for (int loop = 0; loop < hisCount; loop++) {
	    	    DocHisVO docHisVO = docHisVOs.get(loop);
	    		String usedType = docHisVO.getUsedType();
	    		String hisType = messageSource.getMessage("approval.dhutype."+usedType.toLowerCase(), null, langType);
	    		if (dhu021.equals(usedType) || dhu022.equals(usedType) || dhu023.equals(usedType)) {
	    		    hisType = "<font color='red'><b>" + hisType + "</b></font>";
	    		}
	    		
%>	
	var row<%=loop%> = makeDocHis("<%=docHisVO.getDocId()%>", "<%=docHisVO.getHisId()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getUserName())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getPos())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getDeptName())%>", "<%=docHisVO.getUsedType()%>", "<%=hisType%>", "<%=docHisVO.getUseDate()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getRemark())%>", "<%=docHisVO.getUserIp()%>");
	tbDocHis.append(row<%=loop%>);
<%		
	    	}
	    }
	}
%>


}


function getDocInfo() {

	var docInfo = new Object();
	
	docInfo.docId = "<%=request.getAttribute("docId")%>";
	docInfo.title = "<%=request.getAttribute("title")%>";	
	docInfo.bindingId = "<%=request.getAttribute("bindingId")%>";
	docInfo.bindingName = "<%=request.getAttribute("bindingName")%>";
	docInfo.bindingResourceId = "<%=request.getAttribute("bindingResourceId")%>";
	docInfo.conserveType = "<%=request.getAttribute("conserveType")%>";
	docInfo.readRange = "<%=request.getAttribute("readRange")%>";
	docInfo.auditReadYn = "<%=request.getAttribute("auditReadYn")%>";
	docInfo.auditReadReason = "<%=request.getAttribute("auditReadReason")%>";
	docInfo.auditYn = "<%=request.getAttribute("auditYn")%>";
	docInfo.deptCategory = "<%=request.getAttribute("deptCategory")%>";
	docInfo.serialNumber = "<%=request.getAttribute("serialNumber")%>";	
	docInfo.subserialNumber = "<%=request.getAttribute("subserialNumber")%>";
	docInfo.sendOrgName = "<%=request.getAttribute("sendOrgName")%>";
	docInfo.logoPath = "<%=request.getAttribute("logoPath")%>";
	docInfo.symbolPath = "<%=request.getAttribute("symbolPath")%>";
	docInfo.senderTitle = "<%=request.getAttribute("senderTitle")%>";
	docInfo.headerCamp = "<%=request.getAttribute("headerCamp")%>";
	docInfo.footerCamp = "<%=request.getAttribute("footerCamp")%>";
	docInfo.urgencyYn = "<%=request.getAttribute("urgencyYn")%>";
	docInfo.autoSendYn = "<%=request.getAttribute("autoSendYn")%>";
	docInfo.enfType = "<%=request.getAttribute("enfType")%>";
	docInfo.publicPost = "<%=request.getAttribute("publicPost")%>";
	docInfo.openLevel = "<%=request.getAttribute("openLevel")%>";
	docInfo.openReason = "<%=request.getAttribute("openReason")%>";
	docInfo.enfBound = "<%=request.getAttribute("enfBound")%>";
	docInfo.classNumber = "<%=request.getAttribute("classNumber")%>";
	docInfo.docnumName = "<%=request.getAttribute("docnumName")%>";
	docInfo.transferYn = "<%=request.getAttribute("transferYn")%>";
	docInfo.drafterDeptId = "<%=request.getAttribute("drafterDeptId")%>";
	docInfo.securityYn = "<%=request.getAttribute("securityYn")%>";
	docInfo.securityStartDate = "<%=request.getAttribute("securityStartDate")%>";
	docInfo.securityEndDate = "<%=request.getAttribute("securityEndDate")%>";

	return docInfo; 
	
}

function getAppLine(){
	var appline = "<%=request.getAttribute("appLine")%>";
	return appline;
}


//부분공개범위 창 오픈
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel+ "&readOnly=Y";
	var width = 350;
	var height = 210;
	openLevelWin = openWindow("openLevelWin", url, width, height); 
}



//공개범위 설정
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


function makeDrafter(uid){
	var row = "";
	row = "<li class='group'><a style='height:auto;'>";
	row += "<img src='<%=iam_url%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId="+uid+"' alt='이미지' class='circleImg'/>";
	row += "<div class='rf'>";
	row += "<p class='title'><%=request.getAttribute("title")%></p>";
	row += "<p class='status'>";
	row += "<span id='content1Name'><%=appDocVO.getDrafterName()%></span><span id='content1Pos'><%=appDocVO.getDrafterPos()%></span><span id='content1DeptName'><%=appDocVO.getDrafterDeptName()%></span></span>";
	row += "<span id='content1DraftTime'><%=appDocVO.getDraftDate()%></span>";
	row += "</p></div><div class='rsf'></div></a></li>";

	$("#tbAppLines").append(row);
	
}


// 결재경로
function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readDate,uid,loop) {
	var row = "";
		if(loop == 0){
			makeDrafter(uid);
		}
	
		var processStatus = '';
		if(proctype != null && proctype != ""){
			if(date == '9999-12-31 23:59:59' && readDate == '9999-12-31 23:59:59'){
				processStatus = '도착';
			}else if(date == '9999-12-31 23:59:59' && readDate != '9999-12-31 23:59:59'){
				processStatus = '개봉';
			}else if(date != '9999-12-31 23:59:59' && readDate != '9999-12-31 23:59:59'){
				processStatus = '처리';
			}	
		}
	
		row = "<li class='group'><a style='height:auto;'>";
		row += "<div class='rf'>";
		row += "<p class='title'><span>"+name+"</span><span>"+pos+"</span><span>("+dept+")</span></p>";
		row += "<p class='status'>";
		row += "<span>"+asktype+"</span>"
		
		 if(proctype=="APT003" && readDate.substring(0,4) != "9999"){  //결재 대기중이고 읽은 경우 읽은 날짜를 표시한다. 160113 한동구 수정
			 row += "<span id='content1DraftTime'>"+readDate+"</span><span>"+processStatus+"</span>";
		}else{
			if(date.substring(0,4) == "9999" && readDate.substring(0,4) != "9999"){		//문서를 읽었으나 결재를 하지 않은 경우 읽은 날짜를 표시하고 결재를 한 경우에는 결재일을 표시한다.
			 	row += "<span id='content1DraftTime'>"+readDate+"</span><span>"+processStatus+"</span>";
			}else{
				if(date.substring(0,4) != "9999"){
					row += "<span id='content1DraftTime'>"+date+"</span><span>"+processStatus+"</span>";					
				}else{
					row += "<span id='content1DraftTime'></span><span>"+processStatus+"</span>";
				}
			}
		}
		row += "</p>";
		if(opinion != null && opinion != ""){
			row += "<p class='comment_blue'>"+opinion+"</p>";
		}
		row += "</div>";
		row += "<div class='rsf'></div></a></li>";
	
	return row; 

}

// 수신자
function makeReciver(sName, sRef, sSymbol) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='40%' class='tb_center'>" +sName + "</td>"; 
	row += "<td width='40%' class='tb_center'>" +sRef + "</td>"; 
	row += "<td width='20%' class='tb_center'>" +sSymbol + "</td>"; 
	row += "</tr>";

	return row;
}

function makeNoReciver() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><spring:message code='approval.msg.notexist.receiver' /></td></tr>";
}

<% if ("Y".equals(opt321)) { %>
// 관련문서
function makeRelatedDoc(docId, title, usingType, electronDocYn) {
	var securityYn = opener.getSecurityYn();
	var isDuration = opener.getIsDuration();
	var row = "<tr bgcolor='#ffffff'>";
	if (usingType == "<%=dpi001%>") {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		} else {
			row += "<td width='10%' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		}
	} else {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		} else {
			row += "<td width='10%' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		}
	}
	row += "<td width='90%' class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\", \"" + securityYn + "\", \"" + isDuration + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedDoc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";
}
<% } %>	

<% if ("Y".equals(opt344)) { %>
// 관련규정
function makeRelatedRule(ruleId, ruleLink, ruleName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='25%' class='tb_center'>" + escapeJavaScript(ruleId) + "</td>";
	row += "<td width='75%' class='tb_left'><a href='#' onclick='selectRelatedRule(\"" + ruleId + "\", \"" + ruleLink + "\");return(false);'>" + escapeJavaScript(ruleName) + "</a></td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedRule() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noRelatedRule'/></nobr></td></tr>";
}

function selectRelatedRule(ruleId, ruleLink) {
	var ruleWin = openWindow("ruleWin", ruleLink, 820, 500, "yes");
}
<% } %>	

<% if ("Y".equals(opt348)) { %>							
// 거래처
function makeCustomer(customerId, customerName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='30%' class='tb_center'>" + customerId + "</td>";
	row += "<td width='70%' class='tb_center'>" + escapeJavaScript(customerName) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonCustomer() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='2'><nobr><spring:message code='list.list.msg.noCustomer'/></nobr></td></tr>";
}
<% } %>

<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
// 수발신이력

function makeProc(no, procType, processorName, processShortDate, processDate, procOpinion) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='tb_center' >" + no + "</td>";
	row += "<td width='20%' class='tb_center'>" + procType + "</td>";
	row += "<td width='30%' class='tb_center' >" + processorName + "</td>";
	row += "<td width='30%' class='tb_center' title='" + processDate + "'>" + processShortDate + "</td>";
	row += "</tr>";
	row += "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='ltb_head'><spring:message code="approval.procinfo.form.procopinion" /></td>";
	row += "<td width='80%' class='tb_left' colspan='3'>" + escapeHtmlCarriageReturn(procOpinion) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonProc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>

<% if (docHisVOs != null) { %>
// 문서이력
function makeDocHis(docId, hisId, userName, pos, deptName, usedType, hisType, useDate, remark, userIp) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='27%' class='tb_center' title='" + deptName + " " + pos + " " + userName + "'>" + userName + "</td>";
	row += "<td width='25%' class='tb_center' title='" + typeOfAppDate(useDate, "<%=format%>") + "'>" + typeOfAppDate(useDate, "<%=dateFormat%>") + "</td>";
	row += "<td width='18%' class='tb_center'>" + hisType + "</td>";
	row += "<td width='16%' class='tb_center'>" + userIp + "</td>";
	row += "<td width='14%' class='tb_center'>";
	if (usedType == "<%=dhu025%>") {
		row += "<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAppLineHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		if (bodyType == "hwp") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "doc") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "html") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		}
	} else if (usedType == "<%=dhu002%>" || usedType == "<%=dhu010%>" || usedType == "<%=dhu015%>" || usedType == "<%=dhu017%>") {
		if (bodyType == "hwp") {	
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "doc") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "html") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		}
	} else 	if (usedType == "<%=dhu009%>" || usedType == "<%=dhu026%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAttachHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else {
		row += "&nbsp;";
	}
	row += "</td>";
	row += "</tr>";
	if (remark != "") {
		row += "<tr bgcolor='#ffffff'>";
		row += "<td width='27%' class='ltb_head'><spring:message code="approval.title.modifyreason" /></td>";
		row += "<td width='73%' class='tb_left' colspan='4'>" + escapeHtmlCarriageReturn(remark) + "</td>";
		row += "</tr>";
	}

	return row;
}

function makeNonDocHis() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='4'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";	
}
<% } %>

function selectAppLineHis(hisId) {
	applineHisWin = openWindow("applineHisWin", "<%=webUri%>/app/approval/listAppLineHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 500, 450);
}

function selectBodyHis(hisId) {
	/*
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}
	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}
	*/
	var width = 800;
	var height = 700;

	bodyHisWin = openWindow("bodyHisWin", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?bodyType=" + bodyType + "&docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

function selectAttachHis(hisId) {
	attachHisWin = openWindow("attachHisWin", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 450, 450);
}

//문서정보 수정
function modifyDocInfo() {
	document.location.href = "<%=webUri%>/app/approval/updateDocInfo.do?docId=" + $("#docId").val();
}

function closeDocInfo(isall) {
	if (applineHisWin != null && !applineHisWin.closed)
		applineHisWin.close();
	if (bodyHisWin != null && !bodyHisWin.closed)
		bodyHisWin.close();
	if (attachHisWin != null && !attachHisWin.closed)
		attachHisWin.close();
	if (relatedDocWin != null && !relatedDocWin.closed)
		relatedDocWin.close();
	if (openLevelWin != null && !openLevelWin.closed)
		openLevelWin.close();
	
	if (isall == "Y" && docLinkedWin != null && !docLinkedWin.closed)
		docLinkedWin.close();

	window.close();
}

function hideAllDiv() {
	$("#docinfo").hide();
	$("#applineinfo").hide();
	$("#receiverinfo").hide();
<% if ("Y".equals(opt321)) { %>
	$("#relateddocinfo").hide();
<% } %>	
<% if ("Y".equals(opt344)) { %>
	$("#relatedruleinfo").hide();
<% } %>	
<% if ("Y".equals(opt348)) { %>							
	$("#customerinfo").hide();
<% } %>	
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
	$("#divProcInfo").hide();
<% } %>
<% if (docHisVOs != null) { %>
	$("#dochisinfo").hide();
<% } %>

}

function changeTab(divid) {
	hideAllDiv();
	$("#" + divid).show();
}

function selectRelatedAppDoc(docId, usingType, electronDocYn, securityYn, isDuration) {
	if ((arguments.length == 5) && (securityYn == "Y") && (isDuration == "true")) {
		var orginDocId = opener.getDocId();
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
				linkUrl = "<%=webUri%>/app/approval/selectProNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		} else {
			if (electronDocYn == "N") {
				linkUrl = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lob093%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		}		
			
		closeDocInfo("N");
	}
}

function isPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relatedDocWin.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
	
}

function insertDocPassword4RelatedDoc(docId, relatedDocId, usingType, electronDocYn)
{
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=relateddoc&docId="+docId+"&relatedDocId="+relatedDocId+"&usingType="+usingType+"&electronDocYn="+electronDocYn, 350, 160);
}

<%
int index = 3;
if ("Y".equals(opt321)) {
    index++;
}
if ("Y".equals(opt344)) {
    index++;
}
if ("Y".equals(opt348)) {
    index++;
}
if (ProcVOs != null && ProcVOs.size() > 0) {
	index++;
}
if (docHisVOs != null) {
	index++;
}

String strIndex = index +"";
int tabIndex = 3;
%>

<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(index) %>

function checkCheckBox(){

	
	
}

function displayfunc(index){
	
	var len = $("#tabMenu > li").length;

	 
	for(var i = 0; i < len ; i++){
		if(i == index){
			$("#content"+i).show();
			$("#tabMenu > li > a").eq(i).addClass("active");
		}else{
			$("#content"+i).hide();	
			$("#tabMenu > li > a").eq(i).removeClass();
		}
	}
}


function setdocInfo(){
	
	<%if(appDocVO.getReadRange().equals("DRS001")){%> 
			$("#readRange").html("보고경로");
	<%	}else if(appDocVO.getReadRange().equals("DRS002")){%>
			$("#readRange").html("부서");
	<%	}else if(appDocVO.getReadRange().equals("DRS003")){%>
			$("#readRange").html("본부");
	<%	}else if(appDocVO.getReadRange().equals("DRS004")){%>
			$("#readRange").html("기관");
	<%	}else if(appDocVO.getReadRange().equals("DRS005")){%>
			$("#readRange").html("회사");
	<%}%>
	
	<%if(appDocVO.getUrgencyYn().equals("Y")){%>
		$("#emerYn").addClass("active");
	<%}%>
	
	<%if(appDocVO.getPublicPost().equals("1")){%>
		$("#pubPostYn").addClass("active");
	<%}%>
	
	
	
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
<body>
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
		     <p class="subTitle">문서정보 조회</p>
		     </div>
		     
			<ul class="public_top_menu_b group" id="tabMenu">
				<li><a style="cursor:pointer;" title="문서정보" class="active" onClick="displayfunc(0)">문서정보</a></li>
				<li><a style="cursor:pointer;" title="결재정보" onClick="displayfunc(1)">결재정보</a></li>
				<li><a style="cursor:pointer;" title="수신자정보" onClick="displayfunc(2)">수신자정보</a></li>
			</ul>
			
			<div class="sub_content" id="content0">
		        <div class="list_write">
		         <div class="table_app_dcm">
		          <table align="center" cellpadding="0"  cellspacing="0">
		         <caption></caption>
		                    <colgroup>
		                        <col style="width:30%">
		                        <col style="width:70%">
		                    </colgroup>
		                <tbody>
		                  <tr>
		           
		                    <th>제목</th>
		                    <td id="title"></td>
		                  </tr>
		                  <tr>
		                    <th>문서번호</th>
		                    <td id="docNumber">${appDocVO.serialNumber}</td>
		                  </tr>
		                  <tr>
		                   <th>공개범위</th>
		                    <td id="readRange"></td>
		                  </tr>
		                  <tr>
		                    <th>열람범위</th>
		                    <td id="divOpenLevel"></td>
		                  </tr>
		                  <tr>
		                    <th>긴급여부</th>
		                    <td><label for="chk3" class="checkbox" id="emerYn"><input type="checkbox"></label></td>
		                  </tr>
		                   <tr>
		                    <th>공람게시</th>
		                    <td><label for="chk3" class="checkbox" id="pubPostYn" ><input type="checkbox"></label></td>
		                  </tr>
		                  </tbody>
		                </table>
		                </div>    
		     	 </div>      
		      </div>
			<div class="sub_content" id="content1">
				<ul class="list V_Limg_list Rdiv group" id="tbAppLines">
					<!-- <li class="group" id="drafter">
						<a><img src="" alt="이미지"/>
							<div class="rf">
								<p class="title" id="title"></p>
								<p class="status">
		                        <span id="content1Name"></span><span id="content1Pos"></span><span id="content1DeptName"></span></span>
		                        <span id="content1DraftTime"></span>
		                        </p>
							</div>
							<div class="rsf"></div>
						</a>
					</li>
					 <li class="group">
						<a href="#none">
							<div class="rf">
								<p class="title">현재수 과장(경영정보팀)</p>
								<p class="status">
									<span>기안(상신)</span>
									<span>2015.09.20 13:20</span>
								</p>
							</div>
							<div class="rsf"></div>
						</a>
					</li> -->
								
				  <!--  <li class="group">
		            <a href="#none">
						<div class="rf">
								<p class="title">현재수 과장(경영정보팀)</p>
								<p class="status">
		                        <span>기안(상신)</span>
		                        <span>2015.09.20 13:20</span>
		                        </p>
							</div>
							<div class="rsf"></div>
						</a>
					</li> --> 
				</ul>
			</div>
			<!------- 수신자정보 수신자 Table S--------->
			<div id="content2">
				<div class="sub_content">
					<ul class="list V_Limg_list Rdiv group">
					</ul>
				</div>
			     <div class="table_app">
			     <table id="receiverinfoTable" style="border-collapse: separate;" align="center" cellpadding="0"  cellspacing="0">
			     <caption></caption>
							<colgroup>
								<col style="width:50%">
								<col style="width:50%">
							</colgroup>
						<tbody>
			              <tr>
			                <th>수신</th>
			                <th>참조</th>
			              </tr>
			              </tbody>
			            </table>
			       </div>
		       </div> 
			<!------- 수신자 Table E --------->
		</div>
	</div>
<%@ include file="/app_mobile/approval/footer.jsp" %>
</body>
</html>