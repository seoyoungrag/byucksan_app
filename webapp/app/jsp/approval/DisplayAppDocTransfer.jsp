<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO" %>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : DisplayAppDoc.jsp 
 *  Description : 완료생산문서조회
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
	// 본문문서 타입 설정
	String strBodyType = "hwp";
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	    
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
	
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
	String result = CommonUtil.nullTrim((String) request.getAttribute("result"));

	if ("success".equals(result)) {
	    AppDocVO appDocVO = null;
		List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
		int docCount = appDocVOs.size();

		FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
		strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
		
		String itemNum = (String) request.getAttribute("itemnum");
		int itemnum = Integer.parseInt(itemNum);
		if (itemnum <= docCount) {
		    appDocVO = appDocVOs.get(itemnum - 1);
		} else {
		    appDocVO = appDocVOs.get(0);
		}
		int serialNumber = appDocVO.getSerialNumber();
		String docType = appDocVO.getDocType();
		
		// 버튼명
		String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
		String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

		String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); // 문서정보
		String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 저장
		String printBtn = messageSource.getMessage("approval.button.print", null, langType); // 인쇄
		String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<%if(strBodyType.equals("hwp")){ %>
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<%}else if(strBodyType.equals("doc")){ %>
<jsp:include page="/app/jsp/common/wordmanager.jsp" />
<%}else if(strBodyType.equals("html")){ %>
<jsp:include page="/app/jsp/common/htmlmanager.jsp" />
<%}%>

<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<jsp:include page="/app/jsp/common/adminmanager.jsp">
	<jsp:param name="formBodyType" value="<%= strBodyType %>" />
</jsp:include>

<jsp:include page="/app/jsp/approval/approval.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$(window).unload(function() { uninitialize(); });
function uninitialize() { closeChildWindow(); }

var index = 0;
var docinfoWin = null;
var applineWin = null;
var receiverWin = null;
var pubreaderWin = null;
var relatedDocWin = null;
var relatedRuleWin = null;
var customerWin = null;
var summaryWin = null;
var opinionWin = null;

function initialize() {
	
	document.getElementById("iframeContent").style.height = (document.body.offsetHeight - 230);

	// 파일 ActiveX 초기화
	initializeFileManager();
	
	// 첨부파일
	loadAttach($("#attachFile", "#approvalitem1").val(), false);

	$("#changedoc").attr("disabled", true);
	
	// 관련문서, 관련규정, 거래처 알림
	var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem1").val());
	var relatedDocCount = relateddoc.length;
	var relatedRuleCount = (getRelatedRuleList($("#relatedRule", "#approvalitem1").val())).length;
	var customerCount = (getCustomerList($("#customer", "#approvalitem1").val())).length;
	var message = "";

<% if("Y".equals(opt321)) { //관련문서사용시 관련문서 창호출%>
	if (relatedDocCount > 0) {
		loadRelatedDoc(relateddoc);
	}
<% } else { %>	
	if (relatedDocCount > 0) {
		message += "<spring:message code='approval.msg.relateddoc'/> " + relatedDocCount + "<spring:message code='approval.msg.unit'/>";
	}
<% } %>

	if (relatedRuleCount > 0) {
		if (message != "") {
			message += ", ";
		}
		message += "<spring:message code='approval.msg.relatedrule'/> " + relatedRuleCount + "<spring:message code='approval.msg.unit'/>";
	}
	
	if (message != "") {
		message += "<spring:message code='approval.msg.registered'/>";
//		alert(message);
	}	
}


//문서정보조회
function selectDocInfo() {
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/selectDocInfo.do?bodyType=<%= strBodyType %>&docId=" + $("#docId", "#approvalitem" + getCurrentItem()).val(), <%=(adminstratorFlag ? 700 : 650)%>, 450);
}

// 문서정보
function getDocInfo() {
	var docInfo = new Object();
	docInfo.docId = $("#docId", "#approvalitem1").val();
	docInfo.title = $("#title", "#approvalitem1").val();
	docInfo.bindingId = $("#bindingId", "#approvalitem1").val();
	docInfo.bindingName = $("#bindingName", "#approvalitem1").val();
	docInfo.conserveType = $("#conserveType", "#approvalitem1").val();
	docInfo.readRange = $("#readRange", "#approvalitem1").val();
	docInfo.auditReadYn = $("#auditReadYn", "#approvalitem1").val();
	docInfo.auditReadReason = $("#auditReadReason", "#approvalitem1").val();
	docInfo.auditYn = $("#auditYn", "#approvalitem1").val();
	docInfo.deptCategory = $("#deptCategory", "#approvalitem1").val();
	docInfo.serialNumber = $("#serialNumber", "#approvalitem1").val();
	docInfo.subserialNumber = $("#subserialNumber", "#approvalitem1").val();
	docInfo.bindingId = $("#bindingId", "#approvalitem1").val();
	docInfo.bindingName = $("#bindingName", "#approvalitem1").val();
	docInfo.senderTitle = $("#senderTitle", "#approvalitem1").val();
	docInfo.headerCamp = $("#headerCamp", "#approvalitem1").val();
	docInfo.footerCamp = $("#footerCamp", "#approvalitem1").val();
	docInfo.urgencyYn = $("#urgencyYn", "#approvalitem1").val();
	docInfo.autoSendYn = $("#autoSendYn", "#approvalitem1").val();
	docInfo.enfType = $("#enfType", "#approvalitem1").val();
	docInfo.publicPost = $("#publicPost", "#approvalitem1").val();
	docInfo.transferYn = $("#transferYn", "#approvalitem1").val();
	
	return docInfo;
}

// 결재정보
function getAppLine() {
	var itemnum = getCurrentItem();
	return $("#appLine", "#approvalitem1").val();
}

// 수신자정보
function getAppRecv() {
	var recv = new Object();
	recv.appRecv = $("#appRecv", "#approvalitem1").val();
	recv.displayNameYn = $("#displayNameYn", "#approvalitem1").val();
	recv.receivers = $("#receivers", "#approvalitem1").val();

	return recv;
}

// 인쇄
function printAppDoc() {
	document.getElementById("iframeContent").contentWindow.print();	
}

// 본문 구문 체크
var checkCount = 0;
function checkContent() {
	if(checkCount < 10 && (document.getElementById("iframeContent").contentWindow.document.body.innerHTML).substring(0,5) != '<FORM' 
		&& (document.getElementById("iframeContent").contentWindow.document.body.innerHTML).substring(0,5) != '<!DOC') {
		checkCount++;
		document.getElementById("iframeContent").contentWindow.document.body.innerHTML = "";
		window.document.frames["iframeContent"].location.reload();
	}
}

</script>
</head>
<body style="margin: 5 5 5 5">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="50%">
							<acube:titleBar><spring:message code='approval.title.select.approval'/></acube:titleBar>
						</td>
<!-- 						
						<td width="50%" align="right">
							<acube:buttonGroup align="right">
								<acube:button onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" type="2" />
								<acube:space between="button" />
								<acube:button onclick="moveToNext();return(false);" value="<%=nextBtn%>" type="2" />
							</acube:buttonGroup>
						</td>
-->						
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>	
							<acube:buttonGroup align="right">
<%
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// 관리자전체목록
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	if (lob099.equals(lobCode) && adminstratorFlag) { 
		if ((app600.compareTo(appDocVO.getDocState()) <= 0) && !isExtWeb) { 
		String adminSendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
%>										
								<acube:button onclick="sendToDocByAdmin();return(false);" value="<%=adminSendToDocBtn%>" type="2" />
								<acube:space between="button" />	
<%
		}
	}
%>												
							
								<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
								<acube:space between="button" />
<% 	if (!isExtWeb) { %>
								<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
								<acube:space between="button" />
<%	} %>												
								<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="message_box">
				<iframe id="iframeContent" name="iframeContent" width="100%" height="600" src="<%=webUri%>/app/jsp/approval/SelectTransferContent.jsp?fileName=<%=bodyVO.getFileName()%>" onload="checkContent()"></iframe>
			</td>
		</tr>
		<tr>
			<td height="6"></td>
		</tr>
 		<tr>
<% if("Y".equals(opt321)) { //관련문서 사용할 경우 %>
		    <td>
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<%-- <td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="15%;" height="60px" class="ltb_head"><spring:message code='approval.title.relateddoc'/></td>
					        		<td width="85%;" height="60px" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;">
						        		<div style="height:60px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">
											<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3">
												<tbody/>
											</table>
										</div>	
									</td>
								</tr>
							</table>
						</td>
						<td>&nbsp;</td> --%>
		      			<td width="100%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
								    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
					        		<td width="80%;" height="15px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
					        		</td>
<% 	if (!isExtWeb) { %>	
					        		<td width="10">&nbsp;</td>
									<td>
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
												<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
												<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
											</tr>
										</table>
					        		</td>
<% 	} %>
					      		</tr>
					    	</table>
					    </td>
					</tr>
		    	</table>
		    </td>	
<% } else { %>
		    <td class="approval_box">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="15px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
		        		</td>
<% 	if (!isExtWeb) { %>	
		        		<td width="10">&nbsp;</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
									<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
									<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
								</tr>
							</table>
		        		</td>
<% 	} %>
		      		</tr>
		    	</table>
		    </td>
<% } %>		    	
		</tr> 
	</table>	
</acube:outerFrame>

<form id="appDocForm" name="appDocForm" method="post">
	<input id="draftType" name="draftType" type="hidden" value=""/>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
	<!-- 생산문서 -->
<%
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		List<FileVO> fileVOs = appDocVO.getFileInfo();
		List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
		SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
		if (sendInfoVO == null)
		    sendInfoVO = new SendInfoVO();
		List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
		List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
		List<CustomerVO> customerVOs = appDocVO.getCustomer();
		List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
%>		    
	<div id="approvalitem1" name="approvalitem">
		<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- 문서ID --> 
		<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"></input><!-- 회사ID --> 
		<input id="title" name="title" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"></input><!-- 문서제목 --> 
		<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
		<input id="enfType" name="enfType" type="hidden" value=<%=appDocVO.getEnfType()%>></input><!-- 시행유형 --> 
		<input id="docState" name="docState" type="hidden" value="<%=appDocVO.getDocState()%>"></input><!-- 문서상태 --> 
		<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"></input><!-- 문서부서분류 --> 
		<input id="serialNumber" name="serialNumber" type="hidden" value="<%=appDocVO.getSerialNumber()%>"></input><!-- 문서일련번호 --> 
		<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=appDocVO.getSubserialNumber()%>"></input><!-- 문서하위번호 --> 
		<input id="readRange" name="readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"></input><!-- 열람범위 --> 
		<input id="openLevel" name="openLevel" type="hidden" value="<%=appDocVO.getOpenLevel()%>"></input><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"></input><!-- 정보공개사유 --> 
		<input id="conserveType" name="conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"></input><!-- 보존년한 --> 
		<input id="deleteYn" name="deleteYn" type="hidden" value="<%=appDocVO.getDeleteYn()%>"></input><!-- 삭제여부 --> 
		<input id="tempYn" name="tempYn" type="hidden" value="<%=appDocVO.getTempYn()%>"></input><!-- 임시여부 --> 
		<input id="docSource" name="docSource" type="hidden" value="<%=appDocVO.getDocSource()%>"></input><!-- 문서출처 --> 
		<input id="originDocId" name="originDocId" type="hidden" value="<%=appDocVO.getOriginDocId()%>"></input><!-- 원문서ID --> 
		<input id="originDocNumber" name="originDocNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOriginDocNumber())%>"></input><!-- 원문서번호 --> 
		<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="<%=appDocVO.getBatchDraftYn()%>"></input><!-- 일괄기안여부 --> 
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="<%=appDocVO.getBatchDraftNumber()%>"></input><!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden" value="<%=appDocVO.getElectronDocYn()%>"></input><!-- 전자문서여부 --> 
		<input id="attachCount" name="attachCount" type="hidden" value="<%=appDocVO.getAttachCount()%>"></input><!-- 첨부개수 --> 
		<input id="urgencyYn" name="urgencyYn" type="hidden" value="<%=appDocVO.getUrgencyYn()%>"></input><!-- 긴급여부 --> 
		<input id="publicPost" name="publicPost" type="hidden" value="<%=appDocVO.getPublicPost()%>"></input><!-- 공람게시 --> 
		<input id="auditReadYn" name="auditReadYn" type="hidden" value="<%=appDocVO.getAuditReadYn()%>"></input><!-- 감사열람여부 --> 
		<input id="auditReadReason" name="auditReadReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>"></input><!-- 감사여부 -->
		<input id="auditYn" name="auditYn" type="hidden" value="<%=appDocVO.getAuditYn()%>"></input><!-- 감사여부 --> 
		<input id="bindingId" name="bindingId" type="hidden" value="<%=appDocVO.getBindingId()%>"></input><!-- 편철ID --> 
		<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"></input><!-- 편철명 --> 
		<input id="handoverYn" name="handoverYn" type="hidden" value="<%=appDocVO.getHandoverYn()%>"></input><!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden" value="<%=appDocVO.getAutoSendYn()%>"></input><!-- 자동발송여부 --> 
		<input id="bizSystemCode" name="bizSystemCode" type="hidden" value="<%=appDocVO.getBizSystemCode()%>"></input><!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden" value="<%=appDocVO.getBizTypeCode()%>"></input><!-- 업무유형코드 --> 
		<input id="mobileYn" name="mobileYn" type="hidden" value="<%=appDocVO.getMobileYn()%>"></input><!-- 모바일결재여부 --> 
		<input id="transferYn" name="transferYn" type="hidden" value="<%=appDocVO.getTransferYn()%>"></input><!-- 문서이관여부 --> 
		<input id="doubleYn" name="doubleYn" type="hidden" value="<%=appDocVO.getDoubleYn()%>"></input><!-- 이중결재여부 --> 
		<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=appDocVO.getEditbodyYn()%>"></input><!-- 본문수정가능여부 --> 
		<input id="editfileYn" name="editfileYn" type="hidden" value="<%=appDocVO.getEditfileYn()%>"></input><!-- 첨부수정가능여부 --> 
		<input id="execDeptId" name="execDeptId" type="hidden" value="<%=appDocVO.getExecDeptId()%>"></input><!-- 주관부서ID --> 
		<input id="execDeptName" name="execDeptName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"></input><!-- 주관부서명 --> 
		<input id="summary" name="summary" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"></input><!-- 요약 --> 
		<!-- 보고경로 --> 
		<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input>
		<!-- 수신자 --> 
		<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appRecvVOs))%>"></input>
		<!-- 본문 --> 
		<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
		<!-- 첨부 --> 
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
		<!-- 발송정보 -->
		<input id="sendOrgName" name="sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- 발신기관명 -->
		<input id="senderTitle" name="senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- 발신명의 -->
		<input id="headerCamp" name="headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- 상부캠페인 -->
		<input id="footerCamp" name="footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- 하부캠페인 -->
		<input id="postNumber" name="postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- 우편번호 -->
		<input id="address" name="address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- 주소 -->
		<input id="telephone" name="telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- 전화 -->
		<input id="fax" name="fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
		<input id="via" name="via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- 경유 -->
		<input id="sealType" name="sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- 날인유형 -->
		<input id="homepage" name="homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- 홈페이지 -->
		<input id="email" name="email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- 이메일 -->
		<input id="receivers" name="receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- 수신 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- 수신 -->
		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- 관련규정 --> 
		<input id="relatedRule" name="relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 --> 
		<input id="customer" name="customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<!-- 공람자 --> 
		<input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>"></input>

		<!-- 본문변경여부 -->
		<input id="bodyEdited" name="bodyEdited" type="hidden" value="N"></input><!-- 본문변경여부 -->
	</div>
</form>
<jsp:include page="/app/jsp/common/adminform.jsp" />
</body>
</html>
<% } else { %>
<%
	String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
	message = messageSource.getMessage(message, null, langType);
%>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	alert("<%=message%>");
	window.close();
}
</script>
</head>
<body></body>
</html>
<% } %>