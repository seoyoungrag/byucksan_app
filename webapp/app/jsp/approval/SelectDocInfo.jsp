<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
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

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var applineHisWin = null;
var bodyHisWin = null;
var attachHisWin = null;
var docLinkedWin = null;
var relatedDocWin =  null;
var openLevelWin = null;
var passwordWin = null;
var docType = "";
var bodyType = "<%= bodyType %>"; 
	
function initialize() {
<% if (!"Y".equals(closeBind)) { %>
	// 수정권한
	if (opener != null && opener.getEditAuthority != null) {
		var hasAuthority = opener.getEditAuthority();
		if (hasAuthority) {
			$("#editButton").show();
		}
	}
<% } %>
	
	// 문서정보
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();
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
		
		setOpenLevelValue(docInfo.openLevel,docInfo.openReason);
			
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
	}

	// 결재경로정보
	if (opener != null && opener.getAppLine != null) {
		var appline = opener.getAppLine();

		var approver = getApproverList(appline);
		var approversize = approver.length;
		var tbAppLines = $('#tbAppLines tbody');
		for (var loop = 0; loop < approversize; loop++) {
			var dept = approver[loop].approverDeptName;
			var pos = approver[loop].approverPos;
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

			var row = makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readDate);
			tbAppLines.append(row);
		}
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



// 결재경로
function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readDate) {
	var row = "<tr bgcolor='#ffffff'>";
	if (opinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='10%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='10%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='10%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='10%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='10%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='10%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='10%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='10%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='18%' class='tb_center'>" + escapeJavaScript(dept) + "</td>"; 
	if (proctype == "<%=apt002%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>"; 
	} else if (proctype == "<%=apt004%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt004" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt051%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.button.approval" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt052%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt052" />)</b>" + "</td>"; 
	} else {
		row += "<td width='18%' class='tb_center'>" + asktype + "</td>"; 
	}

	// 아직 읽지도 않은 상태
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
	row += "<td width='8%' class='tb_center'>" + processStatus + "</td>"; 
	
	
	
	if (proctype == "<%=apt014%>") {
		//row += "<td width='12%' class='tb_center' title='" + typeOfAppDate(date, "<%=dateFormat%>") + "'>" + absent + "</td>";
		row += "<td width='12%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + absent + "</td>";
	} else {
		row += "<td width='12%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";
	} 
	
	row += "<td width='12%' class='tb_center'>" + "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' alt='<spring:message code='approval.form.linehistory'/>' onclick='selectAppLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;" + "</td>"; 
	
	
	row += "<td width='12%' class='tb_center'>";
	// 합의자도 검토role 에 포함하여 체크 // jth8172 2012 신결재 TF
	if ((proctype == "<%=apt001%>" || proctype == "<%=apt051%>" || proctype == "<%=apt052%>") && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			//row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' alt='<spring:message code='approval.form.linehistory'/>' onclick='selectAppLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		}
		if (bodyHisId != "") {
			if (bodyType == "hwp") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "doc") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "html") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			}
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
	row += "</tr>";
	if (opinion != "") {
		row += "<tr bgcolor='#ffffff'><td class='tb_left' colspan='6'><img src='<%=webUri%>/app/ref/img/icon_commentplus.gif' />" + escapeHtmlCarriageReturn(opinion) + "</td></tr>";
	}

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
		row += "<td width='27%' class='tb_tit2'><spring:message code="approval.title.modifyreason" /></td>";
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
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.select.docinfo'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tabGroup>
					<acube:tab index="1" onclick="selectTab(1);changeTab('docinfo');" selected="true"><spring:message code='approval.title.docinfo'/></acube:tab>
						<acube:space between="tab"/>
					<acube:tab index="2" onclick="selectTab(2);changeTab('applineinfo');"><spring:message code='approval.title.approverinfo'/></acube:tab>
						<acube:space between="tab"/>
					<acube:tab index="3" onclick="selectTab(3);changeTab('receiverinfo');"><spring:message code='approval.title.receiverinfo'/></acube:tab>
<% if ("Y".equals(opt321)) { %>
<% 	String selectTab = "selectTab(" + (++tabIndex) + ");changeTab('relateddocinfo');"; %>
<%	String tagIndex = "" + tabIndex; %>
						<acube:space between="tab"/>
					<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>"><spring:message code='approval.title.relateddoc'/></acube:tab>
<% } %>					
<% if ("Y".equals(opt344)) { %>
<% 	String selectTab = "selectTab(" + (++tabIndex) + ");changeTab('relatedruleinfo');"; %>
<%	String tagIndex = "" + tabIndex; %>
						<acube:space between="tab"/>
					<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>"><spring:message code='approval.title.relatedrule'/></acube:tab>
<% } %>					
<% if ("Y".equals(opt348)) { %>
<% 	String selectTab = "selectTab(" + (++tabIndex) + ");changeTab('customerinfo');"; %>
<%	String tagIndex = "" + tabIndex; %>
						<acube:space between="tab"/>
					<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>"><spring:message code='approval.title.customer'/></acube:tab>
<% } %>					
<% if (ProcVOs != null && ProcVOs.size() > 0) { %> 
<% 	String selectTab = "selectTab(" + (++tabIndex) + ");changeTab('divProcInfo');"; %>
<%	String tagIndex = "" + tabIndex; %>
						<acube:space between="tab"/>
					<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>"><spring:message code='approval.enforce.button.sendprocinfo'/></acube:tab>
<% } %>
<% if (docHisVOs != null) { %>
<% 	String selectTab = "selectTab(" + (++tabIndex) + ");changeTab('dochisinfo');"; %>
<%	String tagIndex = "" + tabIndex; %>
						<acube:space between="tab"/>
					<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>"><spring:message code='approval.title.dochis'/></acube:tab>
<% } %>
               </acube:tabGroup>
            </td>
         </tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<!------- 문서정보 Table S--------->
				<div id="docinfo">
					<acube:tableFrame class="td_table borderRB">
						<tr style="display:none;"><td width="20%"></td><td width="30%"></td><td width="20%"></td><td width="30%"></td></tr><!-- 틀 고정용 -->
						<tr bgcolor="#ffffff"><!-- 제목 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.title'/></td>
							<td width="80%" class="tb_left" id="title" colspan="3"></td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 문서번호 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.docnumber'/></td>
							<td class="tb_left" colspan="3" id="docNumber"></td>
						</tr>
						<tr bgcolor="#ffffff" id="bindTR" Style="display: none"><!-- 편철 -->
							<td width="20%" class="tb_tit">케비닛/폴더<input type="hidden" id="bindingId" name="bindingName"/></td>
							<td class="tb_left" id="bindingName" colspan="3"></td>
						</tr>
						<tr bgcolor="#ffffff"><!-- 공개범위 -->
							<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.publiclevel" /></td>
							<td  colspan="3" class="tb_left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td width="96%"><div id="divOpenLevel" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:1pt; vertical-align:bottom;" class="tb_left"></div></td>
										<td align="right">
										<acube:button onclick="goOpenLevel();return(false);" value="<%=openLevelBtn%>" type="4" class="gr" />
										<input type="hidden" name="openLevel" id="openLevel" value="" />
										<input type="hidden" name="openReason" id="openReason" value=""/>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr bgcolor="#ffffff" id="classNumberTR" Style="display: none"><!-- 문서분류 -->
							<td width="20%" class="tb_tit"><spring:message code="approval.form.docKind" /></td>
							<td class="tb_left" colspan="3"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div></td>
						</tr>

						<tr bgcolor="#ffffff"><!-- 열람범위 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.readrange'/></td>
							<td class="tb_left" id="readRange" width="32%"></td>				
							<td width="20%" class="tb_tit" ><spring:message code='approval.form.sendertitle'/></td><!-- 발신명의 -->
							<td class="tb_left" width="32%" id="senderTitle"></td>
						</tr>
	
<% if(securityYn.equals("Y")){%>
					<tr bgcolor="#ffffff"><!-- 보안문서여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.security.date'/></td>
						<td class="tb_left_bg" colspan="3">
						    <span id="securityDate"/>
						</td>
						
					</tr>
<% } %>					
						<tr bgcolor="#ffffff"><!-- 긴급여부 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
							<td class="tb_left_bg" colspan="3"><input type="checkbox" id="urgencyYn" name="urgencyYn" disabled></td>				
						</tr>					
<% if ("Y".equals(autoSendYn)) { %>
						<tr bgcolor="#ffffff"><!-- 자동발송 -->						
							<td width="20%" class="tb_tit"><spring:message code='approval.form.autosendyn'/></td>
							<td class="tb_left_bg" colspan="3"><input type="checkbox" id="autoSendYn" name="autoSendYn" disabled></td>
						</tr>
<% } %>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
						<tr bgcolor="#ffffff"><!-- 공람게시 -->						
							<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>
							<td class="tb_left" colspan="3" id="publicPost"></td>
						</tr>
<% } %>
						<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditReadYn) ? "''" : "none"%>"><!-- 감사열람여부 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.audit.readyn'/></td>
							<td class="tb_left_bg" colspan="3">
								<input type="radio" id="auditReadY" name="auditReadYn" disabled/><spring:message code='approval.form.open'/>&nbsp;
								<input type="radio" id="auditReadN" name="auditReadYn" disabled/><spring:message code='approval.form.notopen'/>
								&nbsp;&nbsp;<b><spring:message code='approval.form.notopen.reason'/></b>&nbsp;<input type="text" id="auditReadReason" name="auditReadReason" class="input" style="width:280" disabled/>
							</td>
						</tr>
						<tr bgcolor="#ffffff" style="display:<%=("Y".equals(auditYn) && !"Y".equals(transferYn)) ? "''" : "none"%>"><!-- 감사여부 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.audityn'/></td>
							<td class="tb_left_bg" colspan="3">
								<input type="radio" id="auditY" name="auditYn" disabled/><spring:message code='approval.form.use.audit'/>&nbsp;
								<input type="radio" id="auditN" name="auditYN" disabled/><spring:message code='approval.form.notuse.audit'/>
							</td>
						</tr>
					</acube:tableFrame>
				</div>
				<!-------문서정보 Table E --------->
				<!------- 결재정보 Table S--------->
				<div id="applineinfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr>
								<td width="10%" class="ltb_head"><spring:message code="approval.form.position" /></td>
								<td width="10%" class="ltb_head"><spring:message code="approval.form.name" /></td>
								<td width="18%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
								<td width="18%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
								<td width="8%" class="ltb_head">상태</td>
								<td width="12%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
								<td width="12%" class="ltb_head">결재경로이력</td>
								<td width="12%" class="ltb_head">본문이력</td>
							</tr>
						</table>
						<table id="tbAppLines" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody />
						</table>
					</div>
				</div>
				<!-------결재정보 Table E --------->
				<!------- 수신자 Table S--------->
				<div id="receiverinfo" style="display:none;">
					<div style="height:269px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr>
								<td width="40%" class="ltb_head"><spring:message code="approval.table.title.recieve" /></td>
								<td width="40%" class="ltb_head"><spring:message code="approval.table.title.ref" /></td>
								<td width="20%" class="ltb_head"><spring:message code="approval.table.title.recvsymbol" /></td>
							</tr>
						</table>
						<table id="tbRecvLines" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>
					<!-- 여백 시작 -->
					<acube:space between="button_content" table="y"/>
					<!-- 여백 끝 -->
					<table width='100%' height="18" cellpadding="0" cellspacing="0" border="0" class="td_table">
						<tr>
							<td width="20" class="tb_left_bg" bgcolor="#dce9f6"><input type="checkbox" name="chkDisplayAs" id="chkDisplayAs"" disabled/></td>
							<td width="20%" class="tb_tit b_td"><spring:message code="approval.form.markreciver" /></td>
							<td width="*" class="tb_left_bg" id="displayAs"></td>
						</tr>
					</table>
					<!-- 여백 시작 -->
					<acube:space between="button_content" table="y"/>
					<!-- 여백 끝 -->
				</div>
				<!------- 수신자 Table E --------->
<% if ("Y".equals(opt321)) { %>
				<!------- 관련문서 Table S--------->
				<div id="relateddocinfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="10%" class="ltb_head"><spring:message code="list.list.title.headerType" /></td>
								<td width="90%" class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
							</tr>
						</table>
						<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>
				</div>
				<!------- 관련문서 Table E --------->
<% } %>
<% if ("Y".equals(opt344)) { %>
				<!------- 관련규정 Table S--------->
				<div id="relatedruleinfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="25%" class="ltb_head"><spring:message code="list.list.title.ruleNumber" /></td>
								<td width="75%" class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
							</tr>
						</table>
						<table id="tbRelatedRules" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>	
				</div>
				<!------- 관련규정 Table E --------->
<% } %>
<% if ("Y".equals(opt348)) { %>
				<!------- 거래처 Table S--------->
				<div id="customerinfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="30%" class="ltb_head"><spring:message code="approval.title.customernum" /></td>
								<td width="70%" class="ltb_head"><spring:message code="approval.title.customer" /></td>
							</tr>
						</table>
						<table id="tbCustomers" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>	
				</div>
				<!------- 거래처 Table E --------->
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
				<!------- 수발신이력 Table S--------->  
				<div id="divProcInfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.no" /></td>
 								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.proctype" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processorname" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processdate" /></td>
							</tr>
						</table>
						<table id="tbProc" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody/>
						</table>
					</div>	
				</div>
				<!------- 문서이력 Table E --------->
<% } %>
<% if (docHisVOs != null) { %>
				<!------- 문서이력 Table S--------->
				<div id="dochisinfo" style="display:none;">
					<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="27%" class="ltb_head"><spring:message code="approval.title.modifyuser" /></td>
								<td width="25%" class="ltb_head"><spring:message code="approval.title.modifydate" /></td>
								<td width="18%" class="ltb_head"><spring:message code="approval.title.modifytype" /></td>
								<td width="16%" class="ltb_head"><spring:message code="approval.title.modifyip" /></td>
								<td width="14%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
							</tr>
						</table>
						<table id="tbDocHis" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>	
				</div>
				<!------- 문서이력 Table E --------->
<% } %>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<td>
						<table id="editButton" border='0' cellpadding='0' cellspacing='0' style='display:none;'>
							<tr>
								<acube:button onclick="modifyDocInfo();return(false);" value="<%=modifyBtn%>" type="2" class="gr" />
								<acube:space between="button" />
							</tr>
						</table>
					</td>	
					<acube:button onclick="closeDocInfo('Y');return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input id="docId" name="docId" type="hidden" value=""></input><!-- 문서ID --> 
</body>
</html>