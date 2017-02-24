<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : SelectAppLine.jsp 
 *  Description : 결재경로 조회 
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

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사 
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재) 
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결

	String OPT051 = appCode.getProperty("OPT051", "OPT051", "OPT"); // 발의
	OPT051 = envOptionAPIService.selectOptionText(compId, OPT051);
	String OPT052 = appCode.getProperty("OPT052", "OPT052", "OPT"); // 보고
	OPT052 = envOptionAPIService.selectOptionText(compId, OPT052);

	String opentype = request.getParameter("opentype"); 
	String linetype = request.getParameter("linetype"); 
	String formBodyType = request.getParameter("formBodyType");

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");

	// 버튼명
	String modifyBtn = messageSource.getMessage("approval.button.modifyappline", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType);
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); // 인쇄
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

function printAppDoc(){
    print(); //윈도우 인쇄 창 띄우고
 }
var applineinfo = null;
var bodyinfo = null;
var attachinfo = null;
var docType = "";
var bodyType = "<%= formBodyType %>";

function initialize() {
	// 결재경로정보
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();
		$("#docId").val(docInfo.docId);
		docType = docInfo.docType;
	}
	if (opener != null && opener.getAppLine != null) {
		var appline = opener.getAppLine();
		var approver = getApproverList(appline);
		var approversize = approver.length;
		var appline = $("#tbAppLines tbody");
		for (var loop = 0; loop < approversize; loop++) {
			var dept = approver[loop].approverDeptName;
			var pos = approver[loop].approverPos;
			var name = approver[loop].approverName;
			var reppos = approver[loop].representativePos;			
			var repname = approver[loop].representativeName;
			var role = approver[loop].approverRole;			
			var bodyHisId = approver[loop].bodyHisId;
			var fileHisId = approver[loop].fileHisId;
			var lineHisId = approver[loop].lineHisId;
			var asktype = typeOfApp(approver[loop].askType);

			if (role!="") {  //발의 및 보고 체크
			 	var strAddTxt ="";

				if( role.indexOf("OPT051")> -1  ) {  //발의
 					strAddTxt = "<%=OPT051%>" ;
 					if( role.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( role.indexOf("OPT052")> -1  ) {  //보고
 					strAddTxt += "<%=OPT052%>" ;
				}  
				if(strAddTxt !="") strAddTxt = "[" + strAddTxt +"]"
				asktype = asktype + strAddTxt;
			}	
								
			var proctype = approver[loop].procType;
			var absent = approver[loop].absentReason;
			var date = approver[loop].processDate;
			var opinion = approver[loop].procOpinion;
			
			var row = makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion);
			appline.append(row);
		}
	}
}

function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion) {
	var row = "<tr bgcolor='#ffffff'>";
	if (opinion != "") {
		if (repname == "") {
			row += "<td class='ltb_center' width='14%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='ltb_center' width='15%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='ltb_center' width='14%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='ltb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='ltb_center' width='100%'>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</td></tr></table></td>"; 
			row += "<td class='ltb_center' width='15%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='ltb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='ltb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		}
	} else {
		if (repname == "") {
			row += "<td class='ltb_center' width='14%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='ltb_center' width='15%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='ltb_center' width='14%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='ltb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='ltb_center' width='100%'>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</td></tr></table></td>"; 
			row += "<td class='ltb_center' width='15%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='ltb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='ltb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td class='ltb_center' width='20%'>" + escapeJavaScript(dept) + "</td>"; 
	if (proctype == "<%=apt002%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>"; 
	} else if (proctype == "<%=apt004%>") {
		row += "<td width='18%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt004" />)</b>" + "</td>"; 
	} else {
		row += "<td width='18%' class='tb_center'>" + asktype + "</td>"; 
	}
	if (proctype == "<%=apt014%>") {
		row += "<td width='16%' class='ltb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + absent + "</td>";
	} else {
		row += "<td class='ltb_center' width='16%' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=dateFormat%>") + "</td>";
	}
	row += "<td class='ltb_center' width='17%'>";
	// 합의자도 검토role 에 포함하여 체크 // jth8172 2012 신결재 TF
	if ((proctype == "<%=apt001%>" || proctype == "<%=apt051%>" || proctype == "<%=apt052%>") && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' onclick='selectAppLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		}
		if (bodyHisId != "") {
			if (bodyType == "hwp") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "doc") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "html") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			}
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
	row += "</tr>";
	if (opinion != "") {
		row += "<tr bgcolor='#ffffff'><td class='ltb_left' colspan='4'><img src='<%=webUri%>/app/ref/img/icon_commentplus.gif' />" + escapeHtmlCarriageReturn(opinion) + "</td></tr>";
	}

	return row;
}

function selectAppLineHis(hisId) {
	applineinfo = openWindow("applineinfo", "<%=webUri%>/app/approval/listAppLineHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 500, 450);
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

	bodyinfo = openWindow("bodyinfo", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?bodyType=" + bodyType + "&docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

function selectAttachHis(hisId) {
	attachinfo = openWindow("attachinfo", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 350, 450);
}

<% if ("U".equals(opentype)) { %>				
function selectAppLine() {
	document.location.href = "<%=webUri%>/app/approval/ApprovalLine.do?groupYn=N&linetype=<%=linetype%>&formBodyType=" + bodyType;
}
<% } %>					

function closeDocInfo() {
	if (applineinfo != null)
		applineinfo.close();
	if (bodyinfo != null)
		bodyinfo.close();
	if (attachinfo != null)
		attachinfo.close();

	window.close();
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
			<td><span class="pop_title77"><spring:message code="approval.title.applines" /></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
<% if ("U".equals(opentype)) { %>				
					<acube:button onclick="selectAppLine();return(false);" value="<%=modifyBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>					
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
					<acube:space between="button" />
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
				<div style="height:500px; overflow-y:auto; background-color:#FFFFFF; position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="14%" class="ltb_head"><spring:message code="approval.form.position" /></td>
							<td width="15%" class="ltb_head"><spring:message code="approval.form.name" /></td>
							<td width="20%" class="ltb_head"><spring:message code='approval.form.dept'/></td>
							<td width="18%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
							<td width="16%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
							<td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
						</tr>
					</table>
					<table id="tbAppLines" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;" class="table_body">
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