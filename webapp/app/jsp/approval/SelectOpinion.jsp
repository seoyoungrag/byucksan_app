<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : SelectOpinion.jsp 
 *  Description : 결재의견조회
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

	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
	
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType);
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); // 인쇄
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.opinion'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });
var docType = "";

function initialize() {

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

			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang S
			var opinionChk = opinion.indexOf(String.fromCharCode(15));
			if(opinionChk != -1) {
				opinion = opinion.substr(1);				
			}
			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang E			
			
			var row = makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, opinion);
			tbAppLines.append(row);
		}
	}
	top.focus();	
}

//결재경로
function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, opinion) {
	var row = "<tr bgcolor='#ffffff'>";
	if (opinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='14%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='17%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='14%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='17%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='14%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='17%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='14%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='17%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='30%' class='tb_center'>" + escapeJavaScript(dept) + "</td>"; 
	if (proctype == "<%=apt002%>") {
		row += "<td width='24%' class='tb_center'>" + asktype + "<font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>"; 
	} else if (proctype == "<%=apt004%>") {
		row += "<td width='24%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt004" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt051%>") {
		row += "<td width='24%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.button.approval" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt052%>") {
		row += "<td width='24%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt052" />)</b>" + "</td>"; 
	} else {
		row += "<td width='24%' class='tb_center'>" + asktype + "</td>"; 
	}
	if (proctype == "<%=apt014%>") {
		row += "<td width='15%' class='tb_center' title='" + typeOfAppDate(date, "<%=dateFormat%>") + "'>" + absent + "</td>";
	} else {
		row += "<td width='15%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=dateFormat%>") + "</td>";
	} 
	row += "</tr>";
	if (opinion != "") {
		row += "<tr bgcolor='#ffffff'><td class='tb_left b_td2' colspan='3'><b>" + escapeHtmlCarriageReturn(opinion) + "</b></td></tr>";
	}

	return row;
}

function closeOpinion() {
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
			<td><span class="pop_title77"><spring:message code='approval.title.opinion'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="javascript:print();return(false);" value="<%=printBtn%>" type="2" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td style="position:relative;">
				<div style="height:290px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="0" cellspacing="0" width="100%" class="table" style="z-index:10;">
						<tr>
							<td width="14%" class="ltb_head"><spring:message code="approval.form.position" /></td>
							<td width="17%" class="ltb_head"><spring:message code="approval.form.name" /></td>
							<td width="30%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
							<td width="24%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
							<td width="15%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
						</tr>
					</table>
					<table id="tbAppLines" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="z-index:1;">
						<tbody />
					</table>
				</div>
				<!-------결재정보 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeOpinion();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>