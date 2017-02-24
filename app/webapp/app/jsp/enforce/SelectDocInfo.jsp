<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.vo.DocHisVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.EnfProcVO,
				 com.sds.acube.app.common.util.DateUtil,
                 org.anyframe.util.StringUtil"
            %>
<%
/** 
 *  Class Name  : PopupDocInfo.jsp 
 *  Description : 문서정보 조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.23 
 *   수 정 자 : jth8172
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  jth8172
 *  @since 2011. 05. 23
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결

	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // 첨부수정
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // 문서정보수정(결재완료전)
	String dhu013 = appCode.getProperty("DHU013", "DHU013", "DHU"); // 접수완료
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // 결재경로 수정
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장
	
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수 대기(부서)
	String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // 접수 대기(사람)

	String docState = StringUtil.null2str((String)request.getParameter("docState"));//문서상태코드
	String lobCode = StringUtil.null2str((String)request.getParameter("lobCode"));//문서함코드

	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서사용여부 - Y : 사용, N : 사용안함
	//관련문서 비활성화(본문하단에 표시 됨) - 추후 사용을 위해 처리
	//opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	opt321 = "N";
	
    String apt014 = appCode.getProperty("APT014", "APT014", "APT"); //부재
    
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서

	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/	
	
	// 버튼명
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
    String modBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType); 
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
	
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	String publicPost = (String) request.getAttribute("publicpost");
	List<DocHisVO> docHisVOs = (ArrayList) request.getAttribute("docHisVOs");
	List<EnfProcVO> ProcVOs = (List<EnfProcVO>) request.getAttribute("ProcVOs");
	String transferYn = (String) request.getAttribute("transferYn");
	
		
    String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId11 = AppConfig.getProperty("role_doccharger","","role"); //처리과 문서 책임자
	boolean docManagerFlag = (roleCode.indexOf(roleId11) == -1) ? false : true; 
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;    

	String disabled;

	//처리과 문서담당자, 접수이후
   if(docManagerFlag && !(enf200.equals(docState) || enf250.equals(docState))){
	   disabled ="";
    }else{
        disabled="disabled";   
    }
    
	// Title
	String title = messageSource.getMessage("approval.title.select.docinfo", null, langType); // Title
    String tabDisplay = ""; // 탭 표시 여부
    String initTab = "docinfo"; // 초기 표시 탭
    if(lobCode.equals(lol002)){
		disabled="disabled";   
		title = messageSource.getMessage("approval.title.enforce.procinfo.select", null, langType);
		tabDisplay = "none";
		initTab = "divProcInfo";
    }
    
	int rangesize = readrange.size();
    
    
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= title %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
var bindWin =null;

$(document).ready(function() { initialize(); });

var enflineinfo = null;
var bodyinfo = null;
var attachinfo = null;
var popOpinionWin;
var relatedDocWin =  null;
var docLinkedWin = null;

function initialize() {
	var docType = "";
	
	// 문서정보
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();
		docType = docInfo.docType;
		$("#docId").val(docInfo.docId);
		$("#txtitle").text(docInfo.title);
        $("#title").val(docInfo.title);
		if (docInfo.serialNumber > 0) {
			if (docInfo.subserialNumber > 0) {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
			} else {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
			}
		}
        
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		} else {
			$("#urgencyYn").attr("checked", false);
		}

		//편철 정보가 존재 할때 정보 보여주기, jd.park, 20120509
		if (docInfo.bindingName != "") {
			$('#bindTR').show();
			$("#bindingName").text(docInfo.bindingName);
		}else{
			$('#bindTR').hide();
		}
<% if(!docManagerFlag){ %>
		$("#readRange").text(typeOfReadRange(docInfo.readRange));
<% } else{%>
        $("#readRange").val(docInfo.readRange);
<%}%>

<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
        if (docInfo.publicPost != "") {
            $("#publicPostYn").attr("checked", true);
            $("#publicPostBound").show();
            $("#publicPost").val(docInfo.publicPost);
        }
<% } %> 

		$("#classNumber").val(docInfo.classNumber);
		$("#docnumName").val(docInfo.docnumName);
		
		//문서 분류 체계 정보가 존재 할때 정보 보여주기, jd.park, 20120509
		if (docInfo.classNumber != "") {	
			$('#classNumberTR').show();
			$("#divDocKind").html($("#classNumber").val() + " [" + $("#docnumName").val() + "]");
		}else{
			$('#classNumberTR').hide();
		}

		changeTab('<%= initTab %>');

	}
    
	// 결재경로정보
	if (opener != null && opener.getAppLine != null) {
		var tbEnfLines = $('#tbEnfLines tbody');
		var enfline = opener.getAppLine();

		if (enfline == "") {
			var row = makeNonApprover();
			tbEnfLines.append(row);
		} else {
			var approver = getEnfList(enfline);
			var approversize = approver.length-1;
			for (var loop = approversize; loop >= 0; loop--) {
				
				var dept = approver[loop].processorDeptName;
				var pos = approver[loop].processorPos;
				var name = approver[loop].processorName;
				var bodyHisId = "";
				var fileHisId = "";
				var lineHisId = approver[loop].lineHisId; 
				var asktype = typeOfApp(approver[loop].askType);
				var proctype = approver[loop].procType;
	            var procOpinion = approver[loop].procOpinion;
	            var absentReason = approver[loop].absentReason;
				var reppos = approver[loop].representativePos;//부재자직위			
				var repname = approver[loop].representativeName;//부재자이름
				var date = approver[loop].processDate;
				if (date.indexOf("9999")>=0) {
					date = "";
				}
				if (procOpinion.indexOf("null") >= 0) {
					procOpinion = "";
				}
				var row = makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname);
				tbEnfLines.append(row);
			}
		}
	}
	

	<% if ("Y".equals(opt321)) { %>
	// 관련문서
	if (opener != null && opener.getRelatedDoc != null) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');
		var relateddoc = opener.getRelatedDoc();
;
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
	    		String hisType = messageSource.getMessage("approval.dhutype."+docHisVO.getUsedType().toLowerCase(), null, langType);
%>	
	var row<%=loop%> = makeDocHis("<%=docHisVO.getDocId()%>", "<%=docHisVO.getHisId()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getUserName())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getPos())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getDeptName())%>", "<%=docHisVO.getUsedType()%>", "<%=hisType%>", "<%=docHisVO.getUseDate()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getRemark())%>", "<%=docHisVO.getUserIp()%>");
	tbDocHis.append(row<%=loop%>);
<%		
	    	}
	    }
	}
%>
 
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
	    	int receiverOrder = -1;
	    	for (int loop = 0; loop < procCount; loop++) {
	    		EnfProcVO enfProcVO = ProcVOs.get(loop);
	    		String procType = messageSource.getMessage("approval.procinfo.form.proctype."+enfProcVO.getProcType().toLowerCase(), null, langType);
	    		if(enfProcVO.getReceiverOrder() != receiverOrder) {
	    			if(receiverOrder != -1) {
%>
						var rowDept = makeBlankLine(); 
						tbProc.append(rowDept);
<%
	    			}
	    			if(enfProcVO.getRefDeptName() != null && !"".equals(enfProcVO.getRefDeptName())) {
%>
						var rowDept = makeProcRefDept("<%=enfProcVO.getRefDeptName()%>"); 
						tbProc.append(rowDept);
<%
	    			}					
					receiverOrder = enfProcVO.getReceiverOrder();
	    		}
%>	

				var row<%=loop%> = makeProc("<%=enfProcVO.getProcOrder()%>", "<%=procType%>", 
						"<%=EscapeUtil.escapeJavaScript(enfProcVO.getProcessorName())%>", 
						"<%=EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(enfProcVO.getProcessDate() ) ) %>", 
						"<%=EscapeUtil.escapeDate(DateUtil.getFormattedDate(enfProcVO.getProcessDate()))%>", 
						"<%=EscapeUtil.escapeJavaScript(CommonUtil.nullTrim(enfProcVO.getProcOpinion()))%>");
				tbProc.append(row<%=loop%>);
<%		
	    	} //for
	    }
	}
%>
}


//결재경로 이력
function selectEnfLineHis(hisId) {
    var url = "<%=webUri%>/app/enforce/enflineHis/getLineHisList.do?docId=" + $("#docId").val() + "&lineHisId="+ hisId;
    enflineinfo = openWindow("enflineinfowin", url , 500, 450);
}


//결재경로
function makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname) {

	var row = "<tr bgcolor='#ffffff'>";
	if (procOpinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='17%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='17%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='20%' class='tb_center' >" + escapeJavaScript(dept) + "</td>"; 
	row += "<td width='12%' class='tb_center' >" + asktype + "</td>"; 
    
	//부재표시 
    if(proctype ==null || proctype =="<%=apt014%>"){
    	//row += "<td width='16%' class='tb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'> <font color='blue'>" + absentReason + "</font></td>"; 
    	row += "<td width='16%' class='tb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'> <font color='blue'>" + absentReason + "</font></td>";
    }else{
	    row += "<td width='16%' class='tb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>"; 
    }
	row += "<td class='tb_center' >";
	if (proctype == "<%=apt001%>" && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' onclick='selectEnfLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
    row += "</tr>";
    if (procOpinion !="") {
       // alert(procOpinion);
        row += "<tr bgcolor='#ffffff'>";
        row += "<td width='100%' class='ltb_left' colspan='4' >" + unescapeCarriageReturn(unescapeJavaScript(procOpinion)) + "</td>";
    } 

	return row;
}

function makeNonApprover() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='6'><nobr><spring:message code='approval.msg.appline.nodata'/></nobr></td></tr>";	
}

<% if ("Y".equals(opt321)) { %>
// 관련문서
function makeRelatedDoc(docId, title, usingType, electronDocYn) {
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
	row += "<td width='90%' class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedDoc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";
}

<% } %>	



<% if (docHisVOs != null) { %>
// 문서이력
function makeDocHis(docId, hisId, userName, pos, deptName, usedType, hisType, useDate, remark, userIp) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='27%' class='ltb_center' title='" + deptName + " " + pos + " " + userName + "'>" + userName + "</td>";
	row += "<td width='25%' class='ltb_center'>" + useDate + "</td>";
	row += "<td width='18%' class='ltb_center'>" + hisType + "</td>";
	row += "<td width='16%' class='ltb_center'>" + userIp + "</td>";
	row += "<td width='14%' class='ltb_center'>";
	if (usedType == "<%=dhu002%>" || usedType == "<%=dhu010%>" || usedType == "<%=dhu013%>"|| usedType == "<%=dhu015%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else 	if (usedType == "<%=dhu009%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else {
		row += "&nbsp;";
	}
	row += "</td>";
	row += "</tr>";
	row += "<tr bgcolor='#ffffff'>";
	row += "<td width='27%' class='ltb_head'><spring:message code="approval.title.modifyreason" /></td>";
	row += "<td width='73%' class='ltb_left' colspan='4'>" + escapeHtmlCarriageReturn(remark) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonDocHis() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>


<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
// 수발신이력

function makeProc(no, procType, processorName, processShortDate, processDate, procOpinion) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='ltb_center' >" + no + "</td>";
	row += "<td width='20%' class='ltb_center'>" + procType + "</td>";
	row += "<td width='30%' class='ltb_center' >" + processorName + "</td>";
	row += "<td width='30%' class='ltb_center' title='" + processDate + "'>" + processShortDate + "</td>";
	row += "</tr>";
	if(procOpinion!=null && procOpinion!="") {
		row += "<tr bgcolor='#ffffff'>";
		row += "<td width='20%' class='ltb_head'><spring:message code="approval.procinfo.form.procopinion" /></td>";
		row += "<td width='80%' class='ltb_left' colspan='3'>" + escapeHtmlCarriageReturn(procOpinion) + "</td>";
		row += "</tr>";
	}

	return row;
}

function makeNonProc() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>

function makeProcRefDept(deptName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td class='ltb_head' colspan='4'>" + deptName + "</td>";
	row += "</tr>";

	return row;
}

function makeBlankLine() {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td colspan='4'>&nbsp;</td>";
	row += "</tr>";

	return row;
}

function selectBodyHis(hisId) {
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}
	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}

	bodyinfo = openWindow("bodyinfo", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

function selectAttachHis(hisId) {
	attachinfo = openWindow("attachinfo", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 350, 450);
}

function isPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relatedDoc.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
}

function selectRelatedAppDoc(docId, usingType, electronDocYn) {
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


function closeDocInfo(isall) {
	if (enflineinfo != null && !enflineinfo.closed)
		enflineinfo.close();
	if (bodyinfo != null && !bodyinfo.closed)
		bodyinfo.close();
	if (attachinfo != null && !attachinfo.closed)
		attachinfo.close();
	if (isall == "Y" && docLinkedWin != null && !docLinkedWin.closed)
		docLinkedWin.close();
	
	window.close();
}

function hideAllDiv() {
	$("#docinfo").hide();
	$("#enflineinfo").hide();
	

<% if ("Y".equals(opt321)) { %>
	$("#relateddocinfo").hide();
<% } %>		
<% if (docHisVOs != null) { %>
	$("#dochisinfo").hide();
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
	$("#divProcInfo").hide();
<% } %>
}

function changeTab(divid) {
	hideAllDiv();
	$("#" + divid).show();
}


function changeChoice() {
    if ($("#publicPostYn").attr("checked")) {
        $("#publicPostBound").show();
    } else {
        $("#publicPostBound").hide();
    }
}


function close_win(docInfo) {
    if (opener != null && opener.setChargeeDocInfo) {

        //메인페이지에 값 전달
    	opener.setChargeeDocInfo(docInfo);

    	if (relatedDocWin != null && relatedDocWin.closed == false) {
    			relatedDoc.close();		
    	}		
        if (popOpinionWin != null) {
            popOpinionWin.close();
        }
        window.close();
    }
}

//저장 클릭시-문서정보수정
function onOk() {
    popOpinion("saveDocInfo","<%=modBtn%>","Y");                                        
}


// 문서정보 저장
function saveDocInfo(comment) {
    $("#comment", "#frmDocInfo").val(comment);

    var docInfo = new Object();

    if ($("#urgencyYn").attr("checked")) {
        docInfo.urgencyYn = "Y";
    } else {
        docInfo.urgencyYn = "N";
    } 
    
    docInfo.title = $("#txtitle").text();
    
    docInfo.readRange = $("#readRange").val();

    <% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>               
    if ($("#publicPostYn").attr("checked")) {
        docInfo.publicPost = $("#publicPost").val();
    } else {
        docInfo.publicPost = "";
    }
<% } %>

    procSaveDocInfo(docInfo);
}


// 문서수정
function procSaveDocInfo(docInfo) { 
    
    $("#readRange", "#frmDocInfo").val(docInfo.readRange);
	$("#publicPost").val(docInfo.publicPost);
    $("#urgencyYn", "#frmDocInfo").val(docInfo.urgencyYn);
    $("#title", "#frmDocInfo").val(docInfo.title);

    frmDocInfo.publicPost.value = docInfo.publicPost;
    $.post("<%=webUri%>/app/enforce/charge/saveEnfDocInfo.do", $("#frmDocInfo").serialize(), function(data){
        //결과 페이지의 값을 받아 메세지 처리한다.
            if("1" == data.result ) {  
                alert("<spring:message code='approval.msg.success.modifydocinfo'/>");
                //opener 문서정보 set
                setDocInfoOpener(docInfo);
                //close_win(docInfo);
                window.close();
            } else {
                alert("<spring:message code='approval.msg.fail.modifydocinfo'/>");
            }   
    },'json').error(function(data) {
        var context = data.responseText;
        if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
            alert("<spring:message code='common.msg.include.badinformation'/>");
        } else {
            alert("<spring:message code='approval.msg.fail.modifydocinfo'/>");
        }
    });     
}

// 문저정보 수정시 오프너에서 값set
function setDocInfoOpener(docInfo){

    var oDocInfo = opener.getDocInfo();
    oDocInfo.urgencyYn = docInfo.urgencyYn;
    oDocInfo.publicPost = docInfo.publicPost;
    oDocInfo.readRange = docInfo.readRange;
    if(opener !=null){
        opener.setDocInfo(oDocInfo);
    }    
}


// 의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

    var top = (screen.availHeight - 250) / 2;
    var left = (screen.availWidth - 500) / 2;
    var height = "height=230,";
    <% if (!"1".equals(opt301)) { %> // 암호입력아니면
        height = "height=280,";
    <% } %>  
    if(opinionYn=="N") {
        height = "height=120,";
        <% if (!"1".equals(opt301)) { %> // 암호입력아니면
        height = "height=150,";
        <% } %>  
    }   
    popOpinionWin = window.open("", "popOpinionWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
            "scrollbars=no,resizable=no"); 
    $("#returnFunction").val(returnFunction);
    $("#btnName").val(btnName);
    $("#opinionYn").val(opinionYn);
    $("#frmDocInfo").attr("target", "popOpinionWin");
    $("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
    $("#frmDocInfo").submit();
}

<%
int index = 2;
if ("Y".equals(opt321)) {
    index++;
}
if (docHisVOs != null) {
	index++;
}
if (ProcVOs != null && ProcVOs.size() > 0) {
	index++;
}


String strIndex = index + "";
int tabIndex = 1;
%>
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(index) %>
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

 <form id="frmDocInfo" name="frmDocInfo">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
		<span class="pop_title77"><%= title %></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr style="display:<%= tabDisplay %>"> 
			<td>
				<acube:tabGroup>
<% String selectTab = "selectTab(" + tabIndex + ");changeTab('docinfo');"; %>
<% 
  
	String tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>" selected="true"><spring:message code='approval.title.docinfo'/></acube:tab>
						<acube:space between="tab"/>
						
						
<% selectTab = "selectTab(" + tabIndex + ");changeTab('enflineinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.title.approverinfo'/></acube:tab>

<% if ("Y".equals(opt321)) { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('relateddocinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onclick="<%=selectTab%>"><spring:message code='approval.title.relateddoc'/></acube:tab>
<% } %>	
<% if (docHisVOs != null) { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('dochisinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
						<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.title.dochis'/></acube:tab>
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0)  { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('divProcInfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
						<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.enforce.button.procinfo'/></acube:tab>
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
				<div id="docinfo" style="display:none">
                
				<acube:tableFrame>
					<tr bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.title'/></td>
						<td width="80%" class="tb_left_bg" id="txtitle" name="txtitle">
                        
                        </td><input type="hidden" id="title" name="title" />
					</tr>
					<tr bgcolor="#ffffff"><!-- 문서번호 -->
						<td width="20%" class="tb_tit" id="docNumber"><spring:message code='approval.form.docnumber'/></td>
						<td class="tb_left_bg" id="divDeptCategory" >
                        <input type="hidden" id="deptCategory" name="deptCategory" value=""/></td>
					</tr>
					
					<tr bgcolor="#ffffff" id="bindTR" Style="display: none"><!-- 편철 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.bind'/></td>
						<td class="tb_left_bg" id="bindingName">
						</td>
					</tr>					
					
					<tr bgcolor="#ffffff" id="classNumberTR" Style="display: none"><!-- 문서분류 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /></td>
						<td class="tb_left_bg" colspan="2">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
									<input type="hidden" name="classNumber" id="classNumber" value=""/>
									<input type="hidden" name="docnumName" id="docnumName" value="" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff"><!-- 열람범위 -->
                    
<% 
//처리과문서책임자
if(docManagerFlag){ %>
                        <td width="20%" class="tb_tit" ><spring:message code='approval.form.readrange'/></td>
    <% if (rangesize > 1) { %>      
                        <td class="tb_left_bg" width="100%" >    
                            <select id="readRange" name="readRange" class="select_9pt" >
    <%  for (int i = 0; i < rangesize; i++) {
            String range = (String)readrange.get(i); %>                         
                                <option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
    <%  } %>
                            </select>
                        </td>
    <% } else if (rangesize == 1) {
        String range = (String)readrange.get(0); %>                         
                        <td class="tb_left_bg" width="100%" ><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%>
                            <input type="hidden" id="readRange" name="readRange" value="<%=range%>"/>
                        </td>
<% 
        }
}else{
%>  

                    <td width="20%" class="tb_tit" ><spring:message code='approval.form.readrange'/></td>
                    <td class="tb_left_bg" width="30%" id="readRange"></td> 

<%} %>						
                    <tr bgcolor="#ffffff"><!-- 긴급여부 -->
                        <td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
                        <td class="tb_left_bg" ><input type="checkbox" id="urgencyYn" name="urgencyYn" <%=disabled%>>
                    </tr>   

					<tr bgcolor="#ffffff"><!-- 공람게시 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
<% 	  if (rangesize > 1) { %>	    
						<td class="tb_left_bg" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);" <%=disabled%>>
									</td>
									<td class="tb_left_bg">
										<div id="publicPostBound" style="display:none;">
											<select id="publicPost"  name="publicPost" class="select_9pt" style="width:50" <%=disabled%>>
<%		for (int loop = 1; loop < rangesize; loop++) {
		    	String range = (String)readrange.get(loop); %>							
												<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%		} %>
											</select>
										</div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30"  class="tb_left_bg">
										<input type="checkbox" class=input_read" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);" <%=disabled%>>
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
									</td>
									<td class="tb_left">
										<div  id="publicPostBound" style="display:none;">	<%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} %>					
<% } %>
                        </tr>
				</acube:tableFrame>
               
				</div>
				<!-------문서정보 Table E --------->
				<!------- 결재정보 Table S--------->
				<div id="enflineinfo" style="display:none;position:relative;">
						<div style="height:162px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
						<table class="table_body" width="100%" border="0" cellpadding="0" cellspacing="0" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr bgcolor="#ffffff">
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.position" /></td>
                                <td width="18%" class="ltb_head"><spring:message code="approval.form.name" /></td>
                                <td width="20%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
                                <td width="12%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
                                <td width="16%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
							</tr>
						</table>
						<table id="tbEnfLines" bgcolor="#adbed7" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
						</div>
 				</div>
				<!-------결재정보 Table E --------->
				
<% if ("Y".equals(opt321)) { %>
				<!------- 관련문서 Table S--------->
				<div id="relateddocinfo" style="display:none;position:relative;">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table_body" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="10%" class="ltb_head"><spring:message code="list.list.title.headerType" /></td>
								<td width="90%" class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
							</tr>
						</table>
						<table id="tbRelatedDocs" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>
				</div>
				<!------- 관련문서 Table E --------->
<% } %>				
<% if (docHisVOs != null) { %>
				<!------- 문서이력 Table S--------->  
				<div id="dochisinfo" style="display:none;">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="2" cellspacing="1" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="27%" class="ltb_head"><spring:message code="approval.title.modifyuser" /></td>
 								<td width="25%" class="ltb_head"><spring:message code="approval.title.modifydate" /></td>
								<td width="18%" class="ltb_head"><spring:message code="approval.title.modifytype" /></td>
								<td width="16%" class="ltb_head"><spring:message code="approval.title.modifyip" /></td>
								<td width="14%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
							</tr>
						</table>
						<table id="tbDocHis" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody/>
						</table>
					</div>	
				</div>
				<!------- 문서이력 Table E --------->
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
				<!------- 수발신이력 Table S--------->  
				<div id="divProcInfo" style="display:none;position:relative">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="0" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- 제목 -->
								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.no" /></td>
 								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.proctype" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processorname" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processdate" /></td>
							</tr>
						</table>
						<table id="tbProc" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;" class="table_body">
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
                <% if(docManagerFlag){ //처리과문서담당자%>
                    <% if(!enf200.equals(docState) && !enf250.equals(docState)){ //접수이후 %>
                    <acube:button onclick="onOk();return(false);" value="<%=saveBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <% } %>
                <%} %>
					<acube:button onclick="closeDocInfo('Y');return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table> 
</acube:outerFrame>
<input id="docId" name="docId" type="hidden" value=""></input><!-- 문서ID --> 
<input id="comment" name="comment" type="hidden" value=""></input><!-- 문서의견--> 
<input id="returnFunction"  name="returnFunction"   type="hidden" />
<input id="btnName"         name="btnName"          type="hidden" />
<input id="opinionYn"       name="opinionYn"        type="hidden" />
<input id="transferYn"       name="transferYn"        type="hidden" value="<%=transferYn%>"/>

</form>
</body>
</html>