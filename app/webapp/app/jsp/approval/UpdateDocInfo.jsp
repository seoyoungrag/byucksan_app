<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO"%>
<%@ page import="com.sds.acube.app.env.vo.SenderTitleVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%
/** 
 *  Class Name  : InsertDocInfo.jsp 
 *  Description : 문서정보 
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

	String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인 - Y : 사용, N : 사용안함
	opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
	String dispHeaderCamp = "";
	if (!"Y".equals(opt323)) dispHeaderCamp = "style='display:none;'";
	
	String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인 - Y : 사용, N : 사용안함
	opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
	String dispFooterCamp = "";
	if (!"Y".equals(opt324)) dispFooterCamp = "style='display:none;'";
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외

	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
	String wkt002 = appCode.getProperty("WKT002", "WKT002", "WKT"); // 일반
	
	String rtt001 = AppConfig.getProperty("RTT001", "RTT001", "RTT"); // 워터마크 인쇄제외

	// 버튼명
	String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
	String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화	
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	
	String docId = request.getParameter("docId");
	String autoSendYn = (String) request.getAttribute("autosendyn");
	String auditReadYn = (String) request.getAttribute("auditReadYn");
	String auditYn = (String) request.getAttribute("audityn");
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	String publicPost = (String) request.getAttribute("publicpost");
	List<SenderTitleVO> sendertitle = (ArrayList) request.getAttribute("senderTitleList");
	List<FormEnvVO> campaignHeaderList = (ArrayList) request.getAttribute("campaignHeaderList");
	List<FormEnvVO> campaignFooterList = (ArrayList) request.getAttribute("campaignFooterList");
	
	AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");
	String docType = appDocVO.getDocType();
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	String displayValue = "";
	String classNumber = appDocVO.getClassNumber();
	String docnumName = appDocVO.getDocnumName();
	if (classNumber != null && !"".equals(classNumber)) {
	    displayValue = classNumber + " [" + docnumName +"]";
	}
	    
	int rangesize = readrange.size();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var docKindDoc = null;

function initialize() {
<%if(appDocVO.getBindingId().equals("")){%>
	$("#retentionPeriod").val("${default}");
<%}%>
}

function changeChoice() {
	if ($("#publicPostYn").attr("checked")) {
		$("#publicPostBound").show();
	} else {
		$("#publicPostBound").hide();
	}
}

function clickAuditRead(disabled) {
	if (disabled) {
		$("#auditReadReason").val("");
	}
	$("#auditReadReason").attr("disabled", disabled);
}

function saveDocInfo() {
	var docInfo = new Object();
	
	// 열람범위 
	docInfo.readRange = $("#readRange").val();
	// 긴급여부
	if ($("#urgencyYn").attr("checked")) {
		docInfo.urgencyYn = "Y";
	} else {
		docInfo.urgencyYn = "N";
	}
	// 공람게시
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
	if ($("#publicPostYn").attr("checked")) {
		docInfo.publicPost = $("#publicPost").val();
	} else {
		docInfo.publicPost = "";
	}
<% } %>

	var docinfo = "readRange" + String.fromCharCode(2) + docInfo.readRange;
	docinfo += String.fromCharCode(4) + "urgencyYn" + String.fromCharCode(2) + docInfo.urgencyYn;
	docinfo += String.fromCharCode(4) + "publicPost" + String.fromCharCode(2) + docInfo.publicPost;

	$("#docInfo", "#managerForm").val(docinfo);
	insertReason("docinfo");
}

function insertReason(asktype) {
	var width = 500;
	<% if ("1".equals(opt301)) { %>		
	var height = 260;
	<% } else { %>
	var height = 220;
	<% } %>
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createReason.do?askType=" + asktype, width, height);
}

function setReason(reason, asktype, password, roundkey) {
	$("#docId", "#managerForm").val("<%=docId%>");
	$("#reason", "#managerForm").val(reason);
	$("#password", "#managerForm").val(password);
	$("#roundkey", "#managerForm").val(roundkey);

	$.post("<%=webUri%>/app/approval/modifyDocInfo.do", $("#managerForm").serialize(), function(data) {
		alert(data.message);
		if (data.result == "success") {
			var docInfo = new Object();
			
			// 열람범위 
			docInfo.readRange = $("#readRange").val();
			// 긴급여부
			if ($("#urgencyYn").attr("checked")) {
				docInfo.urgencyYn = "Y";
			} else {
				docInfo.urgencyYn = "N";
			}
			// 공람게시
		<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
			if ($("#publicPostYn").attr("checked")) {
				docInfo.publicPost = $("#publicPost").val();
			} else {
				docInfo.publicPost = "";
			}
		<% } %>
				
			if (opener != null && opener.setDocInfoByManager != null) {
				opener.setDocInfoByManager(docInfo);
				document.location.href = "<%=webUri%>/app/approval/selectDocInfo.do?docId=<%=docId%>";
			}
		}
	}, 'json').error(function(data) {
		alert("<spring:message code='approval.msg.fail.modifydocinfo'/>");
	});
}

function closeDocInfo() {
	window.close();
}

//문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//문서분류 셋팅
function setDocKind(docKind){

	$("#classNumber").val(docKind.classificationCode);
	$("#docnumName").val(escapeJavaScript(docKind.unitName));

	var divValue = $("#classNumber").val() +" [" + $("#docnumName").val()  +"]";
	$("#divDocKind").html(divValue);
}

//문서분류 초기화
function docKindInit(){	
	$("#classNumber").val("<%=EscapeUtil.escapeHtmlTag(classNumber)%>");
	$("#docnumName").val("<%=EscapeUtil.escapeHtmlTag(docnumName)%>");
	$("#divDocKind").html("<%=EscapeUtil.escapeHtmlTag(displayValue)%>");
}
</script>
</head>
<body style="margin: 0">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.insert.docinfo'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<!-------정보등록 Table S ---------> 
				<acube:tableFrame>
					<tr style="display:none;"><td width="20%"></td><td width="30%"></td><td width="20%"></td><td width="30%"></td></tr><!-- 틀 고정용 -->
					<tr bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.title'/></td>
						<td width="80%" class="tb_left" colspan="3"><%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%></td>
					</tr>
<%
	String docNumber = (appDocVO.getSerialNumber() == 0) ? "" : EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory()) + "-" + appDocVO.getSerialNumber();
	if (appDocVO.getSubserialNumber() > 0) {
		docNumber += "-" + appDocVO.getSubserialNumber();
	}
%>
					<tr bgcolor="#ffffff"><!-- 문서번호 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.docnumber'/></td>
						<td class="tb_left" id="divDeptCategory" colspan="3"><%=docNumber%></td>
					</tr>
					<%if(!appDocVO.getBindingId().equals("")){%>
					<tr bgcolor="#ffffff"><!-- 편철 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.bind'/><input type="hidden" id="bindingId" name="bindingId" value="<%=appDocVO.getBindingId()%>" /></td>
						<td class="tb_left" colspan="3" width="100%">
							<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>
						</td>
					</tr>
					<%}else{%>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="bind.obj.retention.period" /></td><!-- 보존기간 -->
						<td class="tb_left_bg" colspan="3" >
							<table border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" disabled="true"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<%}%>

					<% if (!classNumber.equals("")){ %>
					<tr bgcolor="#ffffff"><!-- 문서분류 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /></td>
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"><%=EscapeUtil.escapeHtmlTag(displayValue)%></div>
										<input type="hidden" name="classNumber" id="classNumber" value="<%=EscapeUtil.escapeHtmlTag(classNumber)%>"/>
										<input type="hidden" name="docnumName" id="docnumName" value="<%=EscapeUtil.escapeHtmlTag(docnumName)%>" />
									</td>
								</tr>
							</table>
						</td>
					</tr>

<% } %>

					<tr bgcolor="#ffffff"><!-- 열람범위 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.readrange'/></td>
<% 	if (rangesize > 1) { %>	    
						<td class="tb_left_bg" width="30%">	
							<select id="readRange" name="readRange" class="select_9pt" style="width:100%;">
<%		
			for (int loop = 0; loop < rangesize; loop++) {
	    		String range = (String)readrange.get(loop);
	    		if (range.equals(appDocVO.getReadRange())) {
%>		    		
								<option value="<%=range%>" selected><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%		
	    		} else {
%>
								<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%		    	    
	    		}
			} 
%>
							</select>
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td class="tb_left" width="30%"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%>
							<input type="hidden" id="readRange" name="readRange" value="<%=range%>"/>
						</td>
<% 	} %>
<%
		SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
		if (sendInfoVO == null) {
	    	sendInfoVO = new SendInfoVO();
		}
%>		
						<td width="20%" class="tb_tit"><spring:message code='approval.form.sendertitle'/></td><!-- 발신명의 -->
						<td class="tb_left" width="30%"><%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%></td>
					</tr>
<%
	sendInfoVO = appDocVO.getSendInfoVO();
	if (sendInfoVO == null) {
	    	sendInfoVO = new SendInfoVO();
	}
%>
					<tr bgcolor="#ffffff" <%=dispHeaderCamp%>><!-- 상부캠페인 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.campaign.up'/></td>
						<td class="tb_left" colspan="3"><%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%></td>
					</tr>
					<tr bgcolor="#ffffff" <%=dispFooterCamp%>><!-- 하부캠페인 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.campaign.down'/></td>
						<td class="tb_left" colspan="3"><%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%></td>
					</tr>
					</tr>
<% if ("N".equals(autoSendYn)) { %>
					<tr bgcolor="#ffffff"><!-- 긴급여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
						<td class="tb_left_bg" colspan="3"><input type="checkbox" id="urgencyYn" name="urgencyYn"<%="Y".equals(appDocVO.getUrgencyYn()) ? " checked" : ""%>><input type="hidden" id="autoSendYn" name="autoSendYn" value=<%=appDocVO.getAutoSendYn()%>></td>
					</tr>	
<% } else { %>
					<tr bgcolor="#ffffff"><!-- 긴급여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
						<td class="tb_left_bg" colspan="3"><input type="checkbox" id="urgencyYn" name="urgencyYn"<%="Y".equals(appDocVO.getUrgencyYn()) ? " checked" : ""%>></td>
					</tr>
					<tr bgcolor="#ffffff"><!-- 자동발송 -->						
						<td width="20%" class="tb_tit"><spring:message code='approval.form.autosendyn'/></td>
						<td class="tb_left" colspan="3"><%=messageSource.getMessage("Y".equals(appDocVO.getAutoSendYn()) ? "approval.form.use.autosend" : "approval.form.notuse.autosend" , null, langType)%></td>
					</tr>
<% } %>
					<tr bgcolor="#ffffff"><!-- 공람게시 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
<% 	if (rangesize > 1) { %>	    
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);"<%="".equals(appDocVO.getPublicPost()) ? "" : " checked"%>>
									</td>
									<td>
										<div id="publicPostBound">
											<select id="publicPost" name="publicPost" class="select_9pt" style="width:115">
<%
			for (int loop = 1; loop < rangesize; loop++) {
		    	String range = (String)readrange.get(loop);
		    	if (range.equals(appDocVO.getPublicPost())) {
%>		    	    
												<option value="<%=range%>" selected><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%			} else { %>
												<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%
				}
			}	
%>
											</select>
										</div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);">
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
									</td>
									<td>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;">	<%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} %>					
<% } %>
					</tr>
					<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditReadYn) ? "''" : "none"%>"><!-- 감사부서열람여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.audit.readyn'/></td>
						<td class="tb_left_bg" colspan="3">
							<nobr>
<% if ("Y".equals(appDocVO.getAuditReadYn())) { %>						
							<input type="radio" id="auditReadY" name="auditReadYn" onclick="clickAuditRead(true);return(true);" checked/><spring:message code='approval.form.open'/>&nbsp;
							<input type="radio" id="auditReadN" name="auditReadYn" onclick="clickAuditRead(false);return(true);"/><spring:message code='approval.form.notopen'/>
							&nbsp;&nbsp;<b><spring:message code='approval.form.notopen.reason'/></b>&nbsp;<input type="text" id="auditReadReason" name="auditReadReason" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>" class="input" style="width:50%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',1024)" disabled/>
<% } else { %>
							<input type="radio" id="auditReadY" name="auditReadYn" onclick="clickAuditRead(true);return(true);"/><spring:message code='approval.form.open'/>&nbsp;
							<input type="radio" id="auditReadN" name="auditReadYn" onclick="clickAuditRead(false);return(true);" checked/><spring:message code='approval.form.notopen'/>
							&nbsp;&nbsp;<b><spring:message code='approval.form.notopen.reason'/></b>&nbsp;<input type="text" id="auditReadReason" name="auditReadReason" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>" class="input" style="width:50%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',1024)"/>
<% } %>
							</nobr>
						</td>
					</tr>
					<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditYn) ? "''" : "none"%>"><!-- 일상감사 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.audityn'/></td>
						<td class="tb_left" colspan="3">
<% 	if ("Y".equals(appDocVO.getAuditYn())) { %>						
							<spring:message code='approval.form.use.audit'/>
<% 	} else if ("N".equals(appDocVO.getAuditYn())) { %>
							<spring:message code='approval.form.notuse.audit'/>
<% 	} %>
						</td>
					</tr>
				</acube:tableFrame>
				<!-------정보등록 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="saveDocInfo();return(false);" value="<%=saveBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeDocInfo();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<jsp:include page="/app/jsp/common/managerform.jsp" />
</body>
</html>