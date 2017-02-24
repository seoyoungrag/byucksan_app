<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : SelectRelatedRule.jsp 
 *  Description : 관련규정 
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
	String portalUri = AppConfig.getProperty("portal_url", "", "portal");
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob001 = appCode.getProperty("LOB001", "LOB001", "LOB");	// 임시저장함 - SelectTemporary
	String lob002 = appCode.getProperty("LOB002", "LOB002", "LOB");	// 연계기안함 - SelectTemporary
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함
	
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	boolean modifyFlag = (lob000.equals(lobCode) || lob001.equals(lobCode) || lob002.equals(lobCode) || lob003.equals(lobCode)) ? true : false;

	// 버튼명
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.relatedrule'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var subRelatedruleWin = null;
var checked = false;

function initialize() {
	// 결재경로정보
	if (opener != null && opener.getRelatedRule != null) {
		var relatedList = $("#relatedList");
		var relatedrule = opener.getRelatedRule();
		if (relatedrule == "") {
			var row = makeNonRelatedRule();
			relatedList.append(row);
		} else {
			var related = getRelatedRuleList(relatedrule);
			var relatedrulesize = related.length;

			for (var loop = 0; loop < relatedrulesize; loop++) {
				var ruleId = related[loop].ruleId;
				var ruleLink = related[loop].ruleLink;
				var ruleName = related[loop].ruleName;
				var row = makeRelatedRule(ruleId, ruleLink, ruleName);
				relatedList.append(row);
			}

			$("#relatedRule").val(relatedrule);
		}
	}
}

function makeRelatedRule(ruleId, ruleLink, ruleName) {
	var row = "<tr id='" + ruleId + "' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td width='30' class='ltb_check' style='overflow:hidden;vertical-align:top;'><nobr><input type='checkbox' name='ruleList' value='" + escapeJavaScript(ruleLink) + "'></nobr></td>"; 
	row += "<td width='25%' class='tb_left'>" + ruleId + "</td>";
	row += "<td width='75%' class='tb_left'>" + escapeJavaScript(ruleName) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedRule() {
	var row = "<tr id='none' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noRelatedRule'/></nobr></td>"; 
	row += "</tr>";

	return row;
}

<% if (modifyFlag) { %>				
function selectRelatedRule() {	
	try {
		if (document.frameRelatedRule != null && document.frameRelatedRule.getWikiContents != null) {
			var wikicontent = document.frameRelatedRule.getWikiContents();
			var contents = wikicontent.split("|");
			if (contents.length == 4) {			
				var relatedruleId = contents[0] + "-" + contents[1];
				var relatedruleName = contents[3];
				var relatedrule = relatedruleId + String.fromCharCode(2) + "<%=portalUri%>/wiki/manual/contents/direct.do?workspaceKey="+contents[0]+"&divKey="+contents[1]+"&compId=" + contents[2] + String.fromCharCode(2) + relatedruleName;
				setRelatedRule(relatedrule);
			}
		}
	} catch (error) {
	}
}

function setRelatedRule(relatedrule) {
	var relatedRule = $("#relatedRule").val();
	var relatedList = $("#relatedList");
	if (relatedrule != "") {
		if ($("#none")) {
			$("#none").remove();
		}
		var related = getRelatedRuleList(relatedrule);
		var relatedrulesize = related.length;

		for (var loop = 0; loop < relatedrulesize; loop++) {
			var ruleId = related[loop].ruleId;
			var ruleLink = related[loop].ruleLink;
			var ruleName = related[loop].ruleName;
			if (relatedRule.indexOf(ruleId) != -1) {
				alert("[" + ruleName + "]<spring:message code='list.list.msg.selectedRule'/>");
			} else {
				var row = makeRelatedRule(ruleId, ruleLink, ruleName);
				relatedList.append(row);
				relatedRule += getRelatedRuleInfo(related[loop]);
			}
		}
		$("#relatedRule").val(relatedRule);
	}
}

function deleteRelatedRule() {	
	var relatedList = $("#relatedList");
	var checkboxes = document.getElementsByName("ruleList");
	if (checkboxes != null) {
		var length = checkboxes.length;
		if (length == 0) {
			alert("<spring:message code='list.list.msg.noSelectRule'/>");
		}
		var relatedRule = $("#relatedRule").val();
		var infos = relatedRule.split(String.fromCharCode(4));
		var infolength = infos.length;
		for (var loop = length - 1; loop >= 0; loop--) {
			if (checkboxes[loop].checked) {
				var docId = checkboxes[loop].value;
				$("#" + docId).remove();
				for (var pos = 0; pos < infolength; pos++) {
					if (infos[pos].indexOf(docId) != -1) {
						infos[pos] = "";
						break;
					}
				}
			}
		}
		relatedRule = "";
		for (var pos = 0; pos < infolength; pos++) {
			if (infos[pos] != "") {
				relatedRule += infos[pos] + String.fromCharCode(4);
			}
		}
		$("#relatedRule").val(relatedRule);
	}
	if (relatedList.children().length == 0) {
		var row = makeNonRelatedRule();
		relatedList.append(row);
	}
}

function confirmRelatedRule() {
	if (opener != null && opener.setRelatedRule != null) {
		opener.setRelatedRule($("#relatedRule").val());
	}
	
	window.close();
}
<% } %>				

function closeRelatedRule() {
<% if (modifyFlag) { %>				
	if (confirm("<spring:message code='approval.msg.closerelatedrule'/>")) {
<% } %>				
		if (subRelatedruleWin != null)
			subRelatedruleWin.close();
	
		window.close();
<% if (modifyFlag) { %>				
	}
<% } %>				
}

function check_All() {
	var checkboxes = document.getElementsByName("ruleList");
	var length = checkboxes.length;
	if (checked) {
		for (var loop = 0; loop < length; loop++) {
			checked = false;
			checkboxes[loop].checked = checked;
		}
	} else {
		for (var loop = 0; loop < length; loop++) {
			checked = true;
			checkboxes[loop].checked = checked;
		}
	}
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="approval.title.relatedrule" /></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td width="100%">
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="basic_box">
							<iframe id="frameRelatedRule" name="frameRelatedRule" width="100%" height="220" frameborder="0" src="<%=portalUri%>/wiki/popup/divisionPopList.do"></iframe>
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% if (modifyFlag) { %>				
		<tr>
			<td width="100%">
				<table border='0' cellpadding='0' cellspacing='0' width="100%">
					<tr>
						<td width="100%" height="23" align="right" valign="bottom">
							<acube:buttonGroup align="center">
								<acube:button id="appendBtn" type="right" onclick="selectRelatedRule();return(false);" value="<%=appendBtn%>" />
								<acube:space between="button" />
								<acube:button id="removeBtn" type="left" onclick="deleteRelatedRule();return(false);" value="<%=removeBtn%>" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% 	} %>					
		<tr>
			<td>
				<!------- 관련규정정보 Table S--------->
				<div style="height:200px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr bgcolor="#ffffff"><!-- 제목 -->
							<td width="30" class="ltb_head" style="overflow:hidden;padding-left:2px"><nobr><img src="<%=webUri%>/app/ref/image/icon_allcheck.gif" width="13" height="14" border="0" onclick="check_All();"></nobr></td>
							<td width="25%" class="ltb_head"><spring:message code="list.list.title.ruleName" /></td>
							<td width="75%" class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
						</tr>
					</table>
					<table cellpadding="0" width="100%" cellspacing="1" border="0" class="table" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody id="relatedList">
						</tbody>					
					</table>
				</div>
				<!-------관련규정정보 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
<% if (modifyFlag) { %>				
					<acube:button onclick="confirmRelatedRule();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>					
					<acube:button onclick="closeRelatedRule();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input type="hidden" id="relatedRule" name="relatedRule" />
</body>
</html>