<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : SelectEnfLine.jsp 
 *  Description : 결재경로 조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.24 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  김경훈
 *  @since 2012.05.24 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String opentype = request.getParameter("opentype"); 
	String linetype = request.getParameter("linetype");
	String docId	= CommonUtil.nullTrim(request.getParameter("docId"));

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");

	// 버튼명
	String modifyBtn = messageSource.getMessage("approval.button.modifyappline", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
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

var enflineinfo = null;
var bodyinfo = null;
var attachinfo = null;
var docType = "";

function initialize() {
	// 결재경로정보
<% if("".equals(docId)) { %>	
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();
		$("#docId").val(docInfo.docId);
		docType = docInfo.docType;
	}
<% } else { %>
	$("#docId").val("<%=docId%>");
<% } %>
	
	if (opener != null && opener.getAppLine != null) {

		var tbEnfLines = $('#tbEnfLines tbody');
		var enfline = opener.getAppLine();

		if (enfline == "") {
			var row = makeNonApprover();
			tbEnfLines.append(row);
		} else {
			var approver = getEnfList(enfline);
			var approversize = approver.length;
			for (var loop = 0; loop < approversize; loop++) {
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
				var row = makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId, procOpinion, absentReason, reppos, repname);
				tbEnfLines.prepend(row);
			}
		}
	}

}



//결재경로
function makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId, procOpinion, absentReason, reppos, repname) {

	var row = "<tr bgcolor='#ffffff'>";
	if (procOpinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='14%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='15%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='14%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='15%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='14%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='15%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='14%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='15%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='20%' class='ltb_center' >" + escapeJavaScript(dept) + "</td>"; 
	row += "<td width='18%' class='ltb_center' >" + asktype + "</td>"; 
    
	//부재표시 
    if(proctype ==null || proctype =="<%=apt014%>"){
    	row += "<td width='16%' class='ltb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'> <font color='blue'>" + absentReason + "</font></td>"; 
    }else{
	    row += "<td width='16%' class='ltb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=dateFormat%>") + "</td>"; 
    }
	row += "<td class='ltb_center' >";
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


//결재경로 이력
function selectEnfLineHis(hisId) {
    var url = "<%=webUri%>/app/enforce/enflineHis/getLineHisList.do?docId=" + $("#docId").val() + "&lineHisId="+ hisId;
    enflineinfo = openWindow("enflineinfowin", url , 500, 450);
}


function selectAttachHis(hisId) {
	attachinfo = openWindow("attachinfo", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 350, 450);
}

<% if ("U".equals(opentype)) { %>				
function selectEnfLine() {
	document.location.href = "<%=webUri%>/app/approval/ApprovalPreReader.do?groupYn=N";
}
<% } %>					

function closeDocInfo() {
	if (enflineinfo != null)
		enflineinfo.close();
	if (attachinfo != null)
		attachinfo.close();

	window.close();
}

</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="approval.title.applines" /></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
<% if ("U".equals(opentype)) { %>				
					<acube:button onclick="selectEnfLine();return(false);" value="<%=modifyBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>					
					<acube:button onclick="closeDocInfo();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
            </td>
         </tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<!------- 결재정보 Table S--------->
				<div style="height:500px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="14%" class="ltb_head"><spring:message code="approval.form.position" /></td>
							<td width="15%" class="ltb_head"><spring:message code="approval.form.name" /></td>
							<td width="20%" class="ltb_head"><spring:message code='approval.form.dept'/></td>
							<td width="18%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
							<td width="16%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
							<td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
						</tr>
					</table>
					<table id="tbEnfLines" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody />
					</table>
				</div>
				<!-------결재정보 Table E --------->
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input id="docId" name="docId" type="hidden" value=""></input><!-- 문서ID --> 
</body>
</html>