<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectLogoMngAdmin.jsp 
 *  Description : 관리자 환경설정 - 로고관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.8 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 8 
 *  @version 1.0 
 */ 
%>
<%
	String msgSave = messageSource.getMessage("env.option.button.save" , null, (Locale)session.getAttribute("LANG_TYPE")); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<style type="text/css">
	.range {display:inline-block;width:170px;height:25px; !important;}
</style>
<script type="text/javascript">
	function updateOptionYn() {
	    document.optEtcForm.action="<%=webUri%>/app/env/updateOptionEtc.do";
	    document.optEtcForm.submit();
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.title.admin"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optEtcForm">
		<c:set var="map" value="${VOMap}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.etc"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="100%" class="tb_tit">
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT306.useYn}" />" name="<c:out value="${map.OPT306.optionId}" />" id="<c:out value="${map.OPT306.optionId}" />" <c:if test="${map.OPT306.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt306"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT307.useYn}" />" name="<c:out value="${map.OPT307.optionId}" />" id="<c:out value="${map.OPT307.optionId}" />" <c:if test="${map.OPT307.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt307"/>
							</span>
							<span class="range">	
							<input type="checkbox" value="<c:out value="${map.OPT308.useYn}" />" name="<c:out value="${map.OPT308.optionId}" />" id="<c:out value="${map.OPT308.optionId}" />" <c:if test="${map.OPT308.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt308"/>
							</span>
							<span class="range">	
							<input type="checkbox" value="<c:out value="${map.OPT309.useYn}" />" name="<c:out value="${map.OPT309.optionId}" />" id="<c:out value="${map.OPT309.optionId}" />" <c:if test="${map.OPT309.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt309"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT312.useYn}" />" name="<c:out value="${map.OPT312.optionId}" />" id="<c:out value="${map.OPT312.optionId}" />" <c:if test="${map.OPT312.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt312"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT315.useYn}" />" name="<c:out value="${map.OPT315.optionId}" />" id="<c:out value="${map.OPT315.optionId}" />" <c:if test="${map.OPT315.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt315"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT320.useYn}" />" name="<c:out value="${map.OPT320.optionId}" />" id="<c:out value="${map.OPT320.optionId}" />" <c:if test="${map.OPT320.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt320"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT302.useYn}" />" name="<c:out value="${map.OPT302.optionId}" />" id="<c:out value="${map.OPT302.optionId}" />" <c:if test="${map.OPT302.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt302"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT325.useYn}" />" name="<c:out value="${map.OPT325.optionId}" />" id="<c:out value="${map.OPT325.optionId}" />" <c:if test="${map.OPT325.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt325"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT326.useYn}" />" name="<c:out value="${map.OPT326.optionId}" />" id="<c:out value="${map.OPT326.optionId}" />" <c:if test="${map.OPT326.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt326"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:out value="${map.OPT327.useYn}" />" name="<c:out value="${map.OPT327.optionId}" />" id="<c:out value="${map.OPT327.optionId}" />" <c:if test="${map.OPT327.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt327"/>	
							</span>						
						</td>
					</tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="javascript:updateOptionYn();" value="<%=msgSave%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>