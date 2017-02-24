<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@page import="com.sds.acube.app.common.vo.UserVO"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectAuditorForDept.jsp 
 *  Description : 감사담당부서지정
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.17 
 *   수 정 자 : redcomet 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  redcomet
 *  @since 2011. 6. 17 
 *  @version 1.0 
 */ 
%>

<%
	boolean permission = (Boolean) request.getAttribute("permission");

	if (!permission) {
		response.sendRedirect(webUri + "/app/jsp/error/ErrorLimited.jsp");
	}

	List<UserVO> auditorList = (List<UserVO>) request.getAttribute("auditorList");
	int auditorListSize = auditorList.size();
	
	String auditorType = (String) request.getAttribute("auditorType");
	
	String addScript = "popOrgTree(1)";

	String buttonAddAuditor = messageSource.getMessage("env.user.button.add.auditor" , null, langType);
	String buttonAddAuditDept = messageSource.getMessage("env.user.button.add.auditdept" , null, langType);
	String buttonDeleteAuditor = messageSource.getMessage("env.user.button.delete.auditor" , null, langType);
	String buttonDeleteAuditDept = messageSource.getMessage("env.user.button.delete.auditdept" , null, langType);

	String msgTitleFordept = messageSource.getMessage("env.user.title.auditor.fordept" , null, langType);
	String msgAlreadyAdded = messageSource.getMessage("env.user.msg.already.added.auditor" , null, langType);
	String msgSelectFordelete = messageSource.getMessage("env.user.msg.select.auditor.fordelete" , null, langType);
	String msgConfirmDelete = messageSource.getMessage("env.user.msg.confirm.delete.auditor" , null, langType);
	String msgSelectForaddDept = messageSource.getMessage("env.user.msg.select.auditor.foradd.dept" , null, langType);
	String msgNo = messageSource.getMessage("env.user.msg.no.auditor" , null, langType);
	String msgSubtitle = messageSource.getMessage("env.user.subtitle.auditor" , null, langType);
	
	if ("O".equals(auditorType)) {
	    addScript = "popOfficerList()";

		buttonAddAuditor = messageSource.getMessage("env.user.button.add.officer" , null, langType);
		buttonDeleteAuditor = messageSource.getMessage("env.user.button.delete.officer" , null, langType);
	    
		msgTitleFordept = messageSource.getMessage("env.user.title.officer.fordept" , null, langType);
	    msgAlreadyAdded = messageSource.getMessage("env.user.msg.already.added.officer" , null, langType);
		msgSelectFordelete = messageSource.getMessage("env.user.msg.select.officer.fordelete" , null, langType);
		msgConfirmDelete = messageSource.getMessage("env.user.msg.confirm.delete.officer" , null, langType);
		msgSelectForaddDept = messageSource.getMessage("env.user.msg.select.officer.foradd.dept" , null, langType);
		msgNo = messageSource.getMessage("env.user.msg.no.officer" , null, langType);
		msgSubtitle = messageSource.getMessage("env.user.subtitle.officer" , null, langType);
	} else if ("C".equals(auditorType)) {
	    
	    buttonAddAuditor = messageSource.getMessage("env.user.button.add.charger" , null, langType);
		buttonDeleteAuditor = messageSource.getMessage("env.user.button.delete.charger" , null, langType);
	    
		msgTitleFordept = messageSource.getMessage("env.user.title.charger" , null, langType);
	    msgAlreadyAdded = messageSource.getMessage("env.user.msg.already.added.charger" , null, langType);
		msgSelectFordelete = messageSource.getMessage("env.user.msg.select.charger.fordelete" , null, langType);
		msgConfirmDelete = messageSource.getMessage("env.user.msg.confirm.delete.charger" , null, langType);
		msgSelectForaddDept = messageSource.getMessage("env.user.msg.select.officer.foradd.dept" , null, langType);
		msgNo = messageSource.getMessage("env.user.msg.no.charger" , null, langType);
		msgSubtitle = messageSource.getMessage("env.user.sustitle.charger" , null, langType);
	}
	
	
	
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= msgTitleFordept %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var gSaveAuditorObject = g_selector();
var gSaveAuditDeptObject = g_selector();
var sColor = "#F2F2F4";

function onClickAuditor(obj){
	$('#auditorId').val(obj.attr('id'));
	selectAuditorElement(obj);
	selectAuditDeptList(obj);
}

function onClickAuditDept(obj){
	$('#targetId').val(obj.attr('id'));
	selectAuditDeptElement(obj);
}

function selectAuditorElement(obj){
	$('document').empty();
	gSaveAuditorObject.restore();
	gSaveAuditorObject.add(obj, sColor);
}

function selectAuditDeptElement(obj){
	$('document').empty();
	gSaveAuditDeptObject.restore();
	gSaveAuditDeptObject.add(obj, sColor);
}

function selectAuditDeptList(obj){
	var procUrl = "<%=webUri%>/app/env/user/selectAuditDeptList.do";
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, $('form').serialize(), function(data){
		results = data;
	});
	drawAuditDeptList(results);			
}

function insertAuditor(auditor){
	var cObj = document.getElementById(auditor.userId);
	if(cObj != null) {
		//alert("<%= msgAlreadyAdded %>");
		//return false;
		return "<%= msgAlreadyAdded %>";
		
	}

	$('#auditorId').val(auditor.userId);
	var procUrl = "<%=webUri%>/app/env/user/insertAuditor.do";
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, $('form').serialize(), function(data){
		results = data;
	});
	drawAuditorList(results);
	onClickAuditor($('#'+auditor.userId));
}

function deleteAuditor(){

	if("" == $('#auditorId').val()) {
		alert("<%= msgSelectFordelete %>");
		return false;
	}

	if (!confirm("<%= msgConfirmDelete %>")) {
		return false;
	}	
	
	var procUrl = "<%=webUri%>/app/env/user/deleteAuditor.do";
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, $('form').serialize(), function(data){
		results = data;
	});
	drawAuditorList(results);
	initialize();
}

function insertAuditDept(auditDept){
	if("" == $('#auditorId').val()) {
		//alert("<%= msgSelectForaddDept %>");
		//return false;
		return "<%= msgSelectForaddDept %>";
	}
	
	var cObj = document.getElementById(auditDept.orgId);
	if(cObj != null) {
		//alert("<spring:message code='env.user.msg.already.added.auditdept'/>");
		//return false;
		return "<spring:message code='env.user.msg.already.added.auditdept'/>";
	}
	
	$('#targetId').val(auditDept.orgId);
	$('#targetName').val(auditDept.orgName);
	
	var procUrl = "<%=webUri%>/app/env/user/insertAuditDept.do";

	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, $('form').serialize(), function(data){
		results = data;
	});
	drawAuditDeptList(results);
	onClickAuditDept($('#'+auditDept.orgId));
}

function deleteAuditDept(){

	if("" == $('#targetId').val()) {
		alert("<spring:message code='env.user.msg.select.auditdept.fordelete'/>");
		return false;
	}

	if (!confirm("<spring:message code='env.user.msg.confirm.delete.auditdept'/>")) {
		return false;
	}	
	
	var procUrl = "<%=webUri%>/app/env/user/deleteAuditDept.do";
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, $('form').serialize(), function(data){
		results = data;
	});
	drawAuditDeptList(results);
	initializeDept();
}

function drawAuditorList(auditorList) {
	$('#auditorId').val("");
	$('#targetId').val("");
	$('#targetName').val("");
	var tbl = $('#tblAuditor');
	var auditorListLength = auditorList.length;
	tbl.empty();

	if(auditorListLength > 0) {
		for (var i=0; i<auditorListLength; i++) {
			var auditor = auditorList[i];
			var userId = auditor.userUID;
			var userName = auditor.userName;
			var displayPosition = auditor.displayPosition;
			var deptName = auditor.deptName;
			var row = "";
			row = "<tr id='"+userId+"' name='"+userId+"' onClick='onClickAuditor($(\"#"+userId+"\"));' bgcolor='#ffffff' style='cursor:pointer;'>";
			row += "<td width='35%' class='ltb_center'>"+userName+"</td>";
			row += "<td width='35%' class='ltb_center'>"+displayPosition+"</td>";
			row += "<td width='30%' class='ltb_center'>"+deptName+"</td>";
			row += "</tr>";
			tbl.append(row);	
		}
	}
	else {
		var row = "";
		row = "<tr style='background-color:#ffffff'>";
		row += "<td height='23' align='center'><%= msgNo %></td>";
		row += "</tr>";
		tbl.append(row);	
	}

}

function drawAuditDeptList(auditDeptList) {
	$('#targetId').val("");
	$('#targetName').val("");
	var tbl = $('#tblAuditDept');
	var auditDeptListLength = auditDeptList.length;
	tbl.empty();

	if(auditDeptListLength > 0) {
		for (var i=0; i<auditDeptListLength; i++) {
			var auditDept = auditDeptList[i];
			var targetId = auditDept.targetId;
			var targetName = auditDept.targetName;
			var row = "";
			row = "<tr id='"+targetId+"' name='"+targetId+"' onClick='onClickAuditDept($(\"#"+targetId+"\"));' style='background-color:#ffffff;cursor:pointer;'>";
			row += "<td class='ltb_center'>"+targetName+"</td>";
			row += "</tr>";
			tbl.append(row);	
		}
	}
	else {
		var row = "";
		row = "<tr style='background-color:#ffffff'>";
		row += "<td height='23' align='center'><spring:message code='env.user.msg.no.auditdept'/></td>";
		row += "</tr>";
		tbl.append(row);	
	}

}

var auditorWin; //감사자선택창

function popOrgTree(type) {
	if(type == 2 && "" == $('#auditorId').val()) {
		alert("<%= msgSelectForaddDept %>");
		return false;
	}
	
	var url = "<%=webUri%>/app/common/OrgTree.do?type=" + type + "&treetype=3&confirmYn=Y";
	auditorWin = openWindow("auditorWin", url, 600, 330);
}	

function popOfficerList() {
	var url = "<%=webUri%>/app/env/user/ListOfficerPop.do";
	auditorWin = openWindow("auditorWin", url, 600, 330);
}	

function setUserInfo(obj) {
	return insertAuditor(obj);
}

function setDeptInfo(obj) {
	return insertAuditDept(obj);
}

function clearPopup() {
	if (auditorWin != null && !auditorWin.closed) {
		auditorWin.close();
	}
}

function initialize() {
	var auditorGroup = $('#tblAuditor tbody').children();
	if(auditorGroup.length > 0 && auditorGroup[0].id != null && auditorGroup[0].id != ""){
		onClickAuditor($('#'+auditorGroup[0].id));
		initializeDept();
	} else {
		drawAuditDeptList("");
	}
}

function initializeDept() {
	var auditDeptGroup = $('#tblAuditDept tbody').children();
	if(auditDeptGroup.length > 0 && auditDeptGroup[0].id != null && auditDeptGroup[0].id != ""){
		onClickAuditDept($('#'+auditDeptGroup[0].id));
	} else {
		drawAuditDeptList("");
	}		
}

</script>
</head>
<body onunload="clearPopup();return(false);">
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2"><acube:titleBar><%= msgTitleFordept %></acube:titleBar></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td colspan="2">
				<acube:buttonGroup>
					<acube:menuButton onclick="<%= addScript %>" value="<%= buttonAddAuditor %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="deleteAuditor();" value="<%= buttonDeleteAuditor %>" />
<%                    
                    if ("O".equals(auditorType) || "A".equals(auditorType)) {
%>                	    
					<acube:space between="button" />
					<acube:menuButton onclick="popOrgTree(2);" value="<%= buttonAddAuditDept %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="deleteAuditDept();" value="<%= buttonDeleteAuditDept %>" />
<%
                    }
%>                    
				</acube:buttonGroup>
            </td>
        </tr>
        <tr>
            <acube:space between="button_search" />
        </tr>
        <tr>
            <td height="100%" class="communi_text">
            <table height="300" width="100%"  cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td colspan="2" height="20" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><%= msgSubtitle %></acube:titleBar>
                            </tr>
                         </table>
                     </td>
<%                    
                    if ("O".equals(auditorType) || "A".equals(auditorType)) {
%>                	    
                     <td colspan="2" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><spring:message code='env.user.subtitle.auditdept'/></acube:titleBar>
                                 </td>
                             </tr>
                         </table>
                    </td>
<%
                    }
%>                    
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td width="44%" height="100%" valign="top">
                    <!------------------------------------감사자--------------------------------------------->
							<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="1" width="100%" class="table" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="35%" class="ltb_head">
										<spring:message code="env.user.table.title.sosok" />
									</td>
									<td width="35%" class="ltb_head">
										<spring:message code="env.user.table.title.gikwei" />
									</td>
									<td width="30%" class="ltb_head">
										<spring:message code="env.user.table.title.name" />
									</td>
								</tr>
							</table>
							<% if (auditorListSize==0) { %>
							<table id="tblAuditor" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
								<tr bgcolor="#ffffff"><td height="23" align="center"><%= msgNo %></td></tr>
							</table>
							<% } else { %>
							<table id="tblAuditor" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
								<tbody>
								
								<c:forEach var="vo" items="${auditorList}">
									<tr id="${vo.userUID}" name="${vo.userUID}" onClick='onClickAuditor($("#${vo.userUID}"));' bgcolor="#ffffff" style="cursor:pointer;">
										<td width="35%" class="ltb_center">${vo.deptName}</td>
										<td width="35%" class="ltb_center">${vo.displayPosition}</td>
										<td width="30%" class="ltb_center">${vo.userName}</td>
									</tr>
								</c:forEach>
								
								</tbody>
							</table>
							<% } %>

							</div>
                    </td>
<%                    
                    if ("O".equals(auditorType) || "A".equals(auditorType)) {
%>                	    
                    <td width="4%" class="communi_text"></td>
                    <td width="50%" height="100%" valign="top">
                    <!-- --------------------------------감사담당부서------------------------------------------ -->
						<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
						<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:relative;left:0px;top:0px;z-index:10;">
							<tr>
								<td class="ltb_head"><spring:message code="env.user.table.title.deptname" /></td>
							</tr>
						</table>
						<table id="tblAuditDept" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
							<tbody/>
						</table>
                    </td>
                    <td width= "1%" class="communi_text"></td>
<%
                    }
%>                    
                  </tr>
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</acube:outerFrame>
<form name="frmForm" id="frmForm">
<input type="hidden" name="auditorId" id="auditorId"></input>
<input type="hidden" name="targetId" id="targetId"></input>
<input type="hidden" name="targetName" id="targetName"></input>
<input type="hidden" name="auditorType" id="auditorType" value="<%= auditorType %>"></input>
</form>
</body>
</html>