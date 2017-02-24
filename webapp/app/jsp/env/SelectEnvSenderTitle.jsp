<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : SelectEnvSenderTitle.jsp 
 *  Description : 발신명의 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.20 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 20 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType);
	String buttonRegister = messageSource.getMessage("env.option.button.register" , null, langType);
	String buttonOrgTree = messageSource.getMessage("env.option.subtitle.05", null, langType);
	String mode = (String) request.getAttribute("mode");
	String deptId = (String)session.getAttribute("DEPT_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String groupType = (String)request.getAttribute("groupType");
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재관리자
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자
	
	String GUT001 = appCode.getProperty("GUT001","GUT001", "GUT"); // 회사
	String GUT002 = appCode.getProperty("GUT002","GUT002", "GUT"); // 기관
	String GUT003 = appCode.getProperty("GUT003","GUT003", "GUT");; // 부서

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.senderTitle"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var GUT001 = "<%=GUT001%>";
var GUT002 = "<%=GUT002%>";
var GUT003 = "<%=GUT003%>";

	function initialize() {
		$('#senderTitle').bind('keydown', function(event){
			var keyCode = event.which;
			if (keyCode == 13) {
				procSenderTitle('insert', '', '');
				return false;
			}
		});
	} 

	function duplicationCheck(senderTitle) {
		var checkVal = false;
		//var tlbST = $('#tblSenderTitle tbody').children(); 
		var tlbST = $('#tbodySenderTitle').children();
		var loop = tlbST.length;
		for (var i=0; i<loop; i++) {
			var st = tlbST[i].children[1].innerText;
			if (st == senderTitle) {
				checkVal = true;
			}
		}
		return checkVal;
	}

	function selectSenderTitle() {
		var groupType = $("#groupType").val();

		<% if ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0) { %>
			if (groupType == GUT001) {
				$("#divDeptSetting").hide();
				getSenderTitle();
				//self.location.href = "<%=webUri%>/app/env/<%=mode%>/selectEnvSenderTitle.do?groupType="+$('#groupType').val();
			} else {
				var tbl = $('#tbodySenderTitle');
				tbl.empty();
				$('#spanDeptName').text('');
				$('#deptId').val('');
				$("#divDeptSetting").show();
			}
		<% } else { %>
			self.location.href = "<%=webUri%>/app/env/charge/selectEnvSenderTitle.do?groupType="+$('#groupType').val();
		<% } %>
	}

	function procSenderTitle(type, sId, resourceId) {
		var procUrl = "";
		var msg = "";
		if (type == "update") {
			//$('#senderTitleVal').val(val);
			msg = '<spring:message code="env.option.msg.confirm.setDefault"/>';
			procUrl = "<%=webUri%>/app/env/admincharge/updateEnvSenderTitleDefaultSet.do?senderTitleId="+sId;
		} else if (type == "insert") {
			var senderTitle = $.trim($("#senderTitle").val());
			if (!duplicationCheck(senderTitle)) {
				if (senderTitle == "") {
					alert('<spring:message code="env.option.msg.validate.sendertitle"/>');
					$('#senderTitle').focus();
					return;
				}
				if ($("#groupType").val() != GUT001 && $("#deptId").val() == "") {
					alert('<spring:message code="env.deptInfo.msg.noSelectDept"/>');
					return;
				}
				msg = '<spring:message code="env.option.msg.confirm.register"/>';
				procUrl = "<%=webUri%>/app/env/admincharge/insertEnvSenderTitle.do";
			} else {
				alert('<spring:message code="env.group.msg.sendertitle.duplication"/>');
				return;
			}
		} else if (type == "delete") {
			msg = '<spring:message code="env.option.msg.confirm.delete"/>';
			//$('#senderTitleVal').val(val);
			procUrl = "<%=webUri%>/app/env/admincharge/deleteEnvSenderTitle.do?senderTitleId="+sId;
		}
		if (confirm(msg)) {
			$.post(procUrl, $("#senderTitleForm").serialize(), function(data){
				if (type == "insert") {
					// 다국어 저장 
        			registResource(data.conditionValue);
				} else if (type == "delete") {
					// 다국어 삭제 
					deleteResource(resourceId);
				}
				
				alert(data.msg);
				<% if ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0) { %>					
					getSenderTitle();
					resetRegistForm();
				<% } else { %>
					document.location.href = data.url;
				<% } %>
			}, 'json');
	    } else {
	    	if (type == "update") {
				getSenderTitle();
				resetRegistForm();
	    	}
	    }
	}

	function popOrgTree() {
		var groupType = $("#groupType").val();
		if((groupType == GUT003)) {
		var url = "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=3";
		var winOrgTree = openWindow("winOrgTree", url, 400, 280);

		}else if((groupType == GUT002)) {
			var selDept = null;
			var width = 500;
		    var height = 400;
			var url = "<%=webUri%>/app/common/Institution.do";

			selDept = openWindow("deptInfoWin", url , width, height);
			}

	}

	function setDeptInfo(obj) {
		$('#spanDeptName').text(obj.orgName);
		$('#deptId').val(obj.orgId);		
		getSenderTitle();
	}

		function getSenderTitle() {
			var results = null;
			var procUrl = "<%=webUri%>/app/env/<%=mode%>/selectEnvSenderTitleAjax.do";
			$.ajaxSetup({async:false});
			$.post(procUrl, $("#senderTitleForm").serialize(), function(data){
				results = data;
			}, 'json');
			senderTitleDraw(results);
		}
	
		function senderTitleDraw(obj) {
			var tbl = $('#tbodySenderTitle');
			tbl.empty();
	
			if (obj != null) {
			
				var rowLength = obj.length;
				for (var i=0; i<rowLength; i++) {
					var rowInfo = obj[i];
					var row = "<tr bgcolor='#ffffff' onMouseOut=\"this.style.backgroundColor=''\" onMouseOver=\"this.style.backgroundColor='#F2F2F4'\">";
					row += "<td height='25' class='ltb_check' style='vertical-align:middle;'>";
					row += "<nobr><input type='radio' name='"+rowInfo.deptId+"' id='"+rowInfo.deptId+"' value='"+rowInfo.senderTitle+"'";
					if (rowInfo.defaultYn == "N") {
						row += " onclick=\"procSenderTitle('update', '" + rowInfo.senderTitleId + "', '" + rowInfo.resourceId + "');\"";
					} else {
						row += " checked";
					}
					row += "/></nobr></td>";
					row += "<td class='ltb_center'><nobr>"+rowInfo.senderTitle+"</nobr></td>";
					row += "<td class='ltb_check' valign='bottom'>";
					row += "<a href='#' onClick=\"procSenderTitle('delete', '" + rowInfo.senderTitleId + "', '" + rowInfo.resourceId + "');\"><img src='<%=imagePath%>/button_x.gif' border='0'></a>";
					row += "</td></tr>";
					tbl.append(row);
				}
			}
		}

		function resetRegistForm() {
			$('#senderTitle').val('');
			$('#defaultYn').attr('checked', false);
		}
	
</script>
</head>
<body>

<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.06"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="senderTitleForm" id="senderTitleForm">
		<c:set var="voList" value="${voList}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.01"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box" style="padding:10px 10px 10px 10px;">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="75">
										<nobr><strong><spring:message code="env.option.form.02"/></strong><spring:message code='common.title.essentiality'/></nobr>
									</td>
									<td width="*">
										<input type="text" name="senderTitle" id="senderTitle" onclick="showResource(this, 'SENDER_TITLE', 'SENDER_TITLE', '', '')" class="input" style="width:100%;" readOnly />
									</td>
									<td width="30" class="ltb_check">
										<input type="checkbox" name="defaultYn" id="defaultYn" value="Y" />
									</td>
									<td width="80">
										<nobr><spring:message code="env.option.txt.01"/></nobr>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</acube:tableFrame>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<acube:space between="button_content" />
					</tr>
				</table>
				<acube:buttonGroup>
					<acube:button onclick="procSenderTitle('insert', '', '');" value="<%=buttonRegister%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.02"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" height="30" border="0">
					<tr>
						<td width="160">
							<select name="groupType" id="groupType" onChange="selectSenderTitle();" style="width:150px;">
							<% if ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0) {  // 시스템 관리자   2012 jth8172 신결재 TF %> 
								<option value="GUT001" <% if ("GUT001".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.06"/></option>
							<% } %>													
							<% if (("charge".equals(mode) && roleCodes.indexOf(roleId12)>=0 || ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0)) ) { // 기관 문서과 문서담당자   2012 jth8172 신결재 TF %>
								<option value="GUT002" <% if ("GUT002".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.10"/></option>
							<% } %>	
							<% if (("charge".equals(mode) && roleCodes.indexOf(roleId11)>=0) || ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0)) { %>
								<option value="GUT003" <% if ("GUT003".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.05"/></option>
							<% } %>	
							</select>
						</td>
						<td width="*">
							
							<div id="divDeptSetting" style="display:none">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="50">
										<acube:buttonGroup>
											<acube:button onclick="popOrgTree();" value="<%=buttonOrgTree%>" type="2" class="gr" />
											<acube:space between="button" />
										</acube:buttonGroup>
									</td>
									<td width="*" style="padding-left:5px;">
										<strong><spring:message code="env.grouptype.GUT003"/> : </strong> 
										<span id="spanDeptName"></span>
									</td>
								</tr>
							</table>
							</div>
							
						</td>
					</tr>							
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table id="tblSenderTitle" width="100%" class='table_grow' border='0' cellspacing='1' cellpadding='0'>					
					<tr>
						<td width="5%" class="ltb_head" style="height:26px;"><nobr><spring:message code="env.option.form.01"/></nobr></td>
						<td width="*" class="ltb_head"><nobr><spring:message code="env.option.form.02"/></nobr></td>
						<td width="7%" class="ltb_head"><nobr><spring:message code="env.option.button.delete"/></nobr></td>
					</tr>
					<tbody id="tbodySenderTitle">
					<c:forEach var="vo" items="${voList}">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td height="25" class="ltb_check" style="vertical-align:middle;">
							<nobr><input type="radio"  name="${vo.deptId}" id="${vo.deptId}" 
							value="${vo.senderTitle}" <c:if test="${vo.defaultYn eq 'N'}">onclick="procSenderTitle('update', '${vo.senderTitleId}', '${vo.resourceId}');"</c:if> 
							<c:if test="${vo.defaultYn eq 'Y'}">checked</c:if> /></nobr>							
						</td>
						<td class="ltb_center"><nobr>${vo.senderTitle}</nobr></td>
						<td class="ltb_check" valign="bottom">
							<a href="#" onClick="procSenderTitle('delete', '${vo.senderTitleId}', '${vo.resourceId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>					
						</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
		<input type="hidden" name="deptId" id="deptId" value="<%=deptId%>" />
		<input type="hidden" name="senderTitleVal" id="senderTitleVal" value="" />
		<input type="hidden" name="mode" id="mode" value="<%=mode%>" />
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>