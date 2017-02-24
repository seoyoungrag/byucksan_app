<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionCLS.jsp 
 *  Description : 관리자 환경설정 - 캠페인/로고/심볼 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.1 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 1 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType);
	String buttonRegister = messageSource.getMessage("env.option.button.register" , null, langType);
	String buttonRegisterCLS = messageSource.getMessage("env.option.button.register.cls" , null, langType);
	String campaignUp = messageSource.getMessage("env.option.subtitle.opt323" , null, langType);
	String campaignDown = messageSource.getMessage("env.option.subtitle.opt324" , null, langType);
	String logo = messageSource.getMessage("env.option.subtitle.opt328" , null, langType);
	String symbol = messageSource.getMessage("env.option.subtitle.opt329" , null, langType);
	
	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	String registerDeptId = institutionId;
	
	String admin ="Y";  //관리자만 사용유무 셋팅
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String adminCode = AppConfig.getProperty("role_admin", "", "role");
	String appAdminCode = AppConfig.getProperty("role_appadmin", "", "role");
	if ( !( roleCode.indexOf(adminCode) >= 0 || roleCode.indexOf(appAdminCode) >= 0 ) ) {
		admin ="N";
	}	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<style type="text/css">
	.range {display:inline-block;width:125px;height:25px; !important;}
</style>
<script type="text/javascript">
	function updateOptionYn(flag, obj, optionValue) {
		var procUrl = "";
		var msg = "";
		if (flag=="useYnSet") {
			procUrl = "<%=webUri%>/app/env/admincharge/updateOptionCLS.do";
			msg = '<spring:message code="env.option.msg.confirm.save"/>';
		} else if (flag=="defaultYnSet") {
			$("#envType").val(obj.name);
			$("#formEnvId").val(obj.value);
			$("#optionValue").val(optionValue);
			procUrl = "<%=webUri%>/app/env/admincharge/updateFormEnvDefaultSet.do";
			msg = '<spring:message code="env.option.msg.confirm.setDefault"/>';
		}
		if (confirm(msg)) {
			$.post(procUrl, $("#optCLSForm").serialize(), function(data){
					alert(data.msg);
					document.location.href = data.url;
			}, 'json');			
	    } else {
		    self.location.href = "<%=webUri%>/app/env/admincharge/selectOptionCLS.do";
	    }
	}
	
	function winInsertFormEnv() {
		var top = (screen.availHeight - 310) / 2;
		var left = (screen.availWidth - 420) / 2;
		var option = "width=420,height=310,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
		summary = window.open("<%=webUri%>/app/env/admincharge/insertFormEnv.do", "register", option);
	}
	
	function deleteFormEnv(formEnvId) {
		$('#formEnvId').val(formEnvId);
		if (confirm('<spring:message code="env.option.msg.confirm.delete"/>')) {
			$.post("<%=webUri%>/app/env/admincharge/deleteFormEnv.do", $("#optCLSForm").serialize(), function(data){
					alert(data.msg);
					document.location.href = data.url;
			}, 'json');			
	    }
	}

	// 다국어 추가
	function deleteFormEnvResource(formEnvId, resourceId) {
		$('#formEnvId').val(formEnvId);
		if (confirm('<spring:message code="env.option.msg.confirm.delete"/>')) {
			$.post("<%=webUri%>/app/env/admincharge/deleteFormEnv.do", $("#optCLSForm").serialize(), function(data){
				// 다국어 삭제
				deleteResource(resourceId);

				alert(data.msg);
				document.location.href = data.url;
			}, 'json');			
	    }
	}
	
	function codeToName(cd) {
		if (cd=="FET001") {
			return document.write('<spring:message code="env.option.subtitle.opt328"/>');
		} else if (cd=="FET002") {
			return document.write('<spring:message code="env.option.subtitle.opt329"/>');
		} else if (cd=="FET003") {
			return document.write('<spring:message code="env.option.subtitle.opt323"/>');
		} else if (cd=="FET004") {
			return document.write('<spring:message code="env.option.subtitle.opt324"/>');
		}
	}	
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.12"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optCLSForm" id="optCLSForm">
		<c:set var="map" value="${VOMap}" />
<% if("Y".equals(admin)) { %>		
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td class="title_mini">
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.03"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<!-- 캠페인/로고/심볼 사용여부 -->
				<acube:tableFrame class="">
					<tr>
						<td width="*" class="g_box">
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT323.optionId}" id="${map.OPT323.optionId}" <c:if test="${map.OPT323.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt323"/>
							</span>
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT324.optionId}" id="${map.OPT324.optionId}" <c:if test="${map.OPT324.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt324"/>
							</span>
							<span class="range">	
							<input type="checkbox" value="Y" name="${map.OPT328.optionId}" id="${map.OPT328.optionId}" <c:if test="${map.OPT328.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt328"/>
							</span>
							<span class="range">		
							<input type="checkbox" value="Y" name="${map.OPT329.optionId}" id="${map.OPT329.optionId}" <c:if test="${map.OPT329.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt329"/>
							</span>								
						</td>
					</tr>					
				</acube:tableFrame>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<acube:space between="button_content" />
					</tr>
				</table>				
				<acube:buttonGroup>
					<acube:button onclick="updateOptionYn('useYnSet', '');" value="<%=buttonSave%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% } %>
		<tr>
			<td>		
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="50%" align="left">
<% if("Y".equals(admin)) { %>		
							<acube:titleBar type="sub"><span class="title_mini"><spring:message code="env.option.subtitle.04"/></span></acube:titleBar>	
<% } %>				
						</td>
						<td width="50%" align="right">
							<span style="float:right"><acube:buttonGroup>		
								<acube:button onclick="winInsertFormEnv();" value='<%=buttonRegisterCLS%>' type="2" class="gr" />
								<acube:space between="button" />
							</acube:buttonGroup></span><span style="float:right">
							<acube:buttonGroup>
								<acube:button onclick="updateOptionYn('useYnSet', '');" value="<%=buttonSave%>" 
									type="2" class="gr" />
								<acube:space between="button" />
							</acube:buttonGroup></span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<%-- <tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td style="float:right;padding-bottom:5px;">
				<span style="float:left"><acube:buttonGroup>		
					<acube:button onclick="winInsertFormEnv();" value='<%=buttonRegisterCLS%>' type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup></span><span style="float:left">
				<acube:buttonGroup>
					<acube:button onclick="updateOptionYn('useYnSet', '');" value="<%=buttonSave%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup></span>
			</td>
		</tr> --%>
		<tr>
			<td>
				<!-- 캠페인 -->
				<acube:tableFrame class="table_grow">
					<tr>
						<td width="5%" class="ltb_head" style="height:26px;"><nobr><spring:message code="env.option.form.01"/></nobr></td>
						<td width="12%" class="ltb_head"><nobr><spring:message code="env.option.form.10"/></nobr></td>
						<td width="*" class="ltb_head"><nobr><spring:message code="env.option.form.11"/></nobr></td>
						<td width="14%" class="ltb_head"><nobr><spring:message code="env.option.form.12"/></nobr></td>
						<td width="14%" class="ltb_head"><nobr><spring:message code="env.option.form.13"/></nobr></td>
						<td width="7%" class="ltb_head"><nobr><spring:message code="env.option.button.delete"/></nobr></td>
					</tr>
					<c:forEach var="vo1" items="${VOCampaignUpList}">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td height="25" class="ltb_check">
							<nobr><input type="radio"  name="${vo1.envType}" id="${vo1.envType}" 
							value="${vo1.formEnvId}" <c:if test="${vo1.defaultYn eq 'N'}">onclick="updateOptionYn('defaultYnSet', this, '${vo1.envInfo}');"</c:if> 
							<c:if test="${vo1.defaultYn eq 'Y'}">checked</c:if> /></nobr>							
						</td>
						<td class="ltb_center"><nobr><spring:message code="env.code.name.${vo1.envType}"/></nobr></td>
						<td class="ltb_center"><nobr>${vo1.envInfo}</nobr></td>
						<td class="ltb_center"><nobr>${vo1.registDate}</nobr></td>
						<td class="ltb_center"><nobr>${vo1.registerName}</nobr></td>
						<td class="ltb_check" valign="bottom">
							<a href="#" onClick="deleteFormEnvResource('${vo1.formEnvId}', '${vo1.resourceId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>				
						</td>
					</tr>
					</c:forEach>
					<tr bgcolor='#ffffff'><td colspan="6" height="1"></td></tr>
					<c:forEach var="vo2" items="${VOCampaignDownList}">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td  height="25" class="ltb_check">
							<nobr><input type="radio"  name="${vo2.envType}" id="${vo2.envType}" 
							value="${vo2.formEnvId}" <c:if test="${vo2.defaultYn eq 'N'}">onclick="updateOptionYn('defaultYnSet', this, '${vo2.envInfo}');"</c:if> 
							<c:if test="${vo2.defaultYn eq 'Y'}">checked</c:if> /></nobr>
						</td>
						<td class="ltb_center"><nobr><spring:message code="env.code.name.${vo2.envType}"/></nobr></td>
						<td class="ltb_center"><nobr>${vo2.envInfo}</nobr></td>
						<td class="ltb_center"><nobr>${vo2.registDate}</nobr></td>
						<td class="ltb_center"><nobr>${vo2.registerName}</nobr></td>
						<td class="ltb_check" valign="bottom">
							<a href="#" onClick="deleteFormEnvResource('${vo2.formEnvId}', '${vo2.resourceId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>						
						</td>
					</tr>
					</c:forEach>
				</acube:tableFrame>
				
				<br />
				<!-- 로고/심볼 -->
				<acube:tableFrame class="table_grow">
					<tr>
						<td width="5%" class="ltb_head" style="height:26px;"><nobr><spring:message code="env.option.form.01"/></nobr></td>
						<td width="12%" class="ltb_head"><nobr><spring:message code="env.option.form.10"/></nobr></td>
						<td width="20%" class="ltb_head"><nobr><spring:message code="env.option.form.14"/></nobr></td>
						<td width="*" class="ltb_head"><nobr><spring:message code="env.option.form.15"/></nobr></td>
						<td width="14%" class="ltb_head"><nobr><spring:message code="env.option.form.12"/></nobr></td>
						<td width="14%" class="ltb_head"><nobr><spring:message code="env.option.form.13"/></nobr></td>
						<td width="7%" class="ltb_head"><nobr><spring:message code="env.option.button.delete"/></nobr></td>
					</tr>
					<c:forEach var="vo3" items="${VOLogoList}">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td class="ltb_check">
							<nobr><input type="radio"  name="${vo3.envType}" id="${vo3.envType}" 
							value="${vo3.formEnvId}" <c:if test="${vo3.defaultYn eq 'N'}">onclick="updateOptionYn('defaultYnSet', this, '${vo3.envInfo}');" </c:if>
							<c:if test="${vo3.defaultYn eq 'Y'}">checked</c:if> /></nobr>							
						</td>
						<td class="ltb_center"><nobr><spring:message code="env.code.name.${vo3.envType}"/></nobr></td>
						<td class="ltb_center"><nobr>${vo3.envName}</nobr></td>
						<td class="ltb_center">
							<img src="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=${vo3.formEnvId}" border="1" width="100" height="100" />
						</td>
						<td class="ltb_center"><nobr>${vo3.registDate}</nobr></td>
						<td class="ltb_center"><nobr>${vo3.registerName}</nobr></td>
						<td class="ltb_check">
							<a href="#" onClick="deleteFormEnv('${vo3.formEnvId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>
						</td>
					</tr>
					</c:forEach>
					<tr bgcolor='#ffffff'><td colspan="7" height="1"></td></tr>
					<c:forEach var="vo4" items="${VOSymbolList}">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td class="ltb_check" style="vertical-align:middle;">
							<nobr><input type="radio"  name="${vo4.envType}" id="${vo4.envType}" 
							value="${vo4.formEnvId}" <c:if test="${vo4.defaultYn eq 'N'}">onclick="updateOptionYn('defaultYnSet', this, '${vo4.envInfo}');"</c:if> 
							<c:if test="${vo4.defaultYn eq 'Y'}">checked</c:if> /></nobr>
						</td>
						<td class="ltb_center"><nobr><spring:message code="env.code.name.${vo4.envType}"/></nobr></td>
						<td class="ltb_center"><nobr>${vo4.envName}</nobr></td>
						<td class="ltb_center">
							<img src="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=${vo4.formEnvId}" border="1" width="100" height="100" />
						</td>
						<td class="ltb_center"><nobr>${vo4.registDate}</nobr></td>
						<td class="ltb_center"><nobr>${vo4.registerName}</nobr></td>
						<td class="ltb_check">
							<a href="#" onClick="deleteFormEnv('${vo4.formEnvId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>
						</td>
					</tr>
					</c:forEach>
					<input type="hidden" name="envType" id="envType" value="" />
					<input type="hidden" name="formEnvId" id="formEnvId" value="" />
					<input type="hidden" name="optionValue" id="optionValue" value="" />
					<input type="hidden" name="registerDeptId" id="registerDeptId" value="<%=registerDeptId%>" />
				</acube:tableFrame>
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>