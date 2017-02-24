<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : EnvDeptInfo.jsp 
 *  Description : 부서정보 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.24 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 24 
 *  @version 1.0 
 */ 
%>
<% 
	String uri = request.getRequestURI();
	String confirmBtn = messageSource.getMessage("env.option.button.save", null, langType); // 확인
	String buttonZipcode = messageSource.getMessage("env.option.button.zipcode" , null, langType); // 우편번호
	String buttonOrgTree = messageSource.getMessage("env.option.subtitle.05" , null, langType); // 조직도
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String mode = (String) request.getAttribute("mode");
	
	String compId = (String)session.getAttribute("COMP_ID"); // 회사아이디

	
	String classInfo = " readonly class=\"input\"";
	String addressStyleInfo = "95";
	String addressButtonViewYn = "Y";			
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.menu.sub.24" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#656565;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {

	$("INPUT:text[group='readOnly']").bind('keydown', function(event){
		var keyCode = event.which;
		if (keyCode == 8) {
			return false;
		}
	});
	
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		formReset();
	<% } else { %>
		var orgVo = new Object();
		orgVo.zipCode = "${orgVo.zipCode}";
		if (orgVo.zipCode != "") {
			//var displayZipCode = (orgVo.zipCode).substring(0,3) + "-" + (orgVo.zipCode).substring(3,6);
			$('#displayZipCode').val(orgVo.zipCode);
			$('#zipCode').val(orgVo.zipCode);
		}		
	<% } %>
}

<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>

	function winOrgTreePop() {
		var winOrgTreePop = openWindow("winOrgTreePop", "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=3", 400, 280);		
	}

	function setDeptInfo(obj) {
		//alert(obj.orgId+", "+obj.orgName);
		$('#spanDeptName').text(obj.orgName);
		$('#orgId').val(obj.orgId);

		var objDeptInfo = null;
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/admin/selectEnvDeptInfoAjax.do?orgId="+obj.orgId, function(data){
			objEmpty = data;
		});
		getOrgInfo(objEmpty);
	}

	function getOrgInfo(obj) {
		var displayZipCode = (obj.zipCode).substring(0, 3) + "-" + (obj.zipCode).substring(3, 6);
		$('#orgAbbrName').val(obj.orgAbbrName);
		$('#addrSymbol').val(obj.addrSymbol);
		$('#chiefPosition').val(obj.chiefPosition);
		$('#zipCode').val(obj.zipCode);
		$('#displayZipCode').val(displayZipCode);
		$('#address').val(obj.address);
		$('#addressDetail').val(obj.addressDetail);
		$('#telephone').val(obj.telephone);
		$('#fax').val(obj.fax);
		$('#email').val(obj.email);
		$('#homepage').val(obj.homepage);
		$('#outgoingName').val(obj.outgoingName);
		
	}

	function formReset() {
		$('#orgAbbrName').val("");
		$('#addrSymbol').val("");
		$('#chiefPosition').val("");
		$('#zipCode').val("");
		$('#address').val("");
		$('#addressDetail').val("");
		$('#telephone').val("");
		$('#fax').val("");
		$('#email').val("");
		$('#homepage').val("");
		$('#outgoingName').val("");
	}
<% } %>

function setZipCode(obj) {	
	var displayZipCode = (obj.first().children().first().text());
	var zipCode = displayZipCode.replace('-','');
	$('#zipCode').val(zipCode);
	$('#displayZipCode').val(displayZipCode);
	$('#address').val(obj.first().attr('displayAddr'));
	$('#addressDetail').val(obj.first().attr('bungi'));	
}

function updateDeptInfo(obj){
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		if ($('#orgId') == null || $('#orgId').val() == "") {
			alert("<spring:message code='env.deptInfo.msg.noSelectDept'/>");
			return;
		}
	<% } %>
	var result = null;
	$.ajaxSetup({async:false});
	$.getJSON("<%=webUri%>/app/env/charge/updateEnvDeptInfo.do", $('form').serialize(), function(data){
		result = data;
	});
	if (result == "success") {
		alert("<spring:message code='env.option.msg.success.modify'/>");
	} else {
		alert("<spring:message code='env.option.msg.error'/>");
	}		
}

function winZipCode() {
	var envDeptInfoZipCode = openWindow("envDeptInfoZipCode", "<%=webUri%>/app/env/selectZipcodePop.do", 500, 450);
}
</script>
</head> 
<body>
<acube:outerFrame>

		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td width="50%" align="left">
					<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.24" /></acube:titleBar>
				</td>
				<td width="50%" align="right">
					<acube:buttonGroup>
						<acube:menuButton onclick="updateDeptInfo();" value="<%=confirmBtn%>" />
					</acube:buttonGroup>
				</td>
			</tr>
		</table>
	<acube:space between="button_content"/>
	<acube:space between="button_content" table="y"/>
	
	<acube:tableFrame class="td_table">
	<c:set var="vo" value="${orgVo}" />
	<form:form modelAttribute="" method="post" name="deptInfoForm" id="deptInfoForm">	
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		<input type="hidden" id="orgId" name="orgId" value="" />
		<tr bgcolor="#ffffff">
			<td width="15%" class="tb_tit_left">
				<nobr><spring:message code="env.user.table.title.deptname"/></nobr><!-- 부서명 -->
			</td>
			<td width="35%" class="tb_left_bg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="70%">
							<nobr><span id="spanDeptName"></span></nobr>
						</td>
						<td width="*" align="right">
							<nobr>
							<acube:buttonGroup>
								<acube:button type="4" onclick="winOrgTreePop();" value="<%=buttonOrgTree%>" />
							</acube:buttonGroup>
							</nobr>
						</td>
					</tr>
				</table>
			</td>
			<td width="15%" class="tb_tit_left">
			</td>
			<td width="35" class="tb_left_bg">
			</td>
		</tr>
	<% } %>	
		<tr bgcolor="#ffffff">
			<td width="15%" class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.01"/></nobr><!-- 조직약어 -->
			</td>
			<td width="35%" class="tb_left_bg">
				<nobr><input type="text" id="orgAbbrName" name="orgAbbrName" value="${vo.orgAbbrName}" onkeyup="checkInputMaxLength(this,'',128)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
			<td width="15%" class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.02"/></nobr><!-- 수신처기호 -->
			</td>
			<td width="35%" class="tb_left_bg">
				<nobr><input type="text" id="addrSymbol" name="addrSymbol" value="${vo.addrSymbol}" onkeyup="checkInputMaxLength(this,'',128)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td class="tb_tit_left" style="display:none;">
				<nobr><spring:message code="env.deptinfo.form.03"/></nobr><!-- 부서장직위 -->
			</td>
			<td class="tb_left_bg" style="display:none;">
				<nobr><input type="text" id="chiefPosition" name="chiefPosition" value="${vo.chiefPosition}" onkeyup="checkInputMaxLength(this,'',64)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.10"/></nobr><!-- 수신명의 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="outgoingName" name="outgoingName" value="${vo.outgoingName}" onkeyup="checkInputMaxLength(this,'',64)" class="input" style="width:100%;"/></nobr>
			</td>
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.04"/></nobr><!-- 우편번호 -->				
			</td>
			<td class="tb_left_bg">
				<nobr>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="40%">
							<input type="text" id="displayZipCode" name="displayZipCode" value="${vo.zipCode}" group="readOnly" readOnly class="input_read" style="width:<%=addressStyleInfo%>%;"/>
							<input type="hidden" id="zipCode" name="zipCode" value="${vo.zipCode}" />
						</td>
						<td width="*">
							<%if ("Y".equals(addressButtonViewYn)) { %>
								<acube:buttonGroup align="left">
									<acube:button id="sendOk" disabledid="" onclick="winZipCode();" value="<%=buttonZipcode%>" type="4" class="gr"/>
								</acube:buttonGroup>
							<%} %>
						</td>
					</tr>
				</table>				
				</nobr>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.05"/></nobr><!-- 주소 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="address" name="address" value="${vo.address}" group="readOnly" readOnly class="input_read" style="width:100%;"/></nobr>
			</td>
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.06"/></nobr><!-- 상세주소 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="addressDetail" name="addressDetail" value="${vo.addressDetail}" onkeyup="checkInputMaxLength(this,'',100)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.07"/></nobr><!-- 전화번호 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="telephone" name="telephone" value="${vo.telephone}" onkeyup="checkInputMaxLength(this,'',20)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.08"/></nobr><!-- 팩스번호 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="fax" name="fax" value="${vo.fax}" onkeyup="checkInputMaxLength(this,'',20)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td class="tb_tit_left">
				<nobr>E-Mail</nobr><!-- E-Mail -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="email" name="email" value="${vo.email}" onkeyup="checkInputMaxLength(this,'',150)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
			<td class="tb_tit_left">
				<nobr><spring:message code="env.deptinfo.form.09"/></nobr><!-- 홈페이지 -->
			</td>
			<td class="tb_left_bg">
				<nobr><input type="text" id="homepage" name="homepage" value="${vo.homepage}" onkeyup="checkInputMaxLength(this,'',256)" <%=classInfo %> style="width:100%;"/></nobr>
			</td>
		</tr>
		<input type="hidden" id="mode" name="mode" value="<%=mode%>" />
	</form:form>
	</acube:tableFrame>

	<acube:space between="button_content" table="y"/>
	
</acube:outerFrame>
</body>	
</html>