<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.vo.OptionVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionBox.jsp 
 *  Description : 관리자 환경설정 - 함구분 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.30 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 3. 30 
 *  @version 1.0 
 */ 
%>
<%
	String opt120 = appCode.getProperty("OPT120", "", "OPT");
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType); 
	String compId = (String)session.getAttribute("COMP_ID");
	
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

<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	.range_box {display:inline-block;width:245px;height:25px; !important;}
	.range {display:inline-block;width:175px;height:25px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });
function initialize() {
	chainCheck();
	
	//여신문서함 숨김 jd.park, 20120426 
	$('#<%=opt120%>').hide();
}

	function updateOptionYn() {
		if ($('input[name=OPT342]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt342"/>');
			return;
		}
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionInspection.do", $("#optBoxForm").serialize(), function(data){
				// 다국어 추가
				registOptionResource();
				
				alert(data.msg);
				document.location.href = data.url;
			}, 'json');
	    }
	}

	function readOnlyCheck(obj) {
		if (obj.checked == false) {
			$('#'+obj.name+'Value').attr('disabled', true);
		} else {
			$('#'+obj.name+'Value').attr('disabled', false);
		}
	}

	function chainCheck() {
		var optVal = $('input[name=OPT342]:checked').val();
		if (optVal == "1") {
			$('#OPT347').attr('disabled', false);
		} else if (optVal == "2") {
			$('#OPT347').attr('disabled', true);
			$('#OPT347').attr('checked', false);
		}
	}

	function readOnlyCheck(obj) {
		if (obj.checked == false) {
			$('#'+obj.name+'Value').attr('disabled', true);
		} else {
			$('#'+obj.name+'Value').attr('disabled', false);
		}
	}

	function setBoxName(obj, nm) {
		if (obj.checked == true && $.trim($('#'+obj.name+'Value').val()) == "") {
			$('#'+obj.name+'Value').val(nm);
		}
	}

	function boxCheck(obj, nm) {
		readOnlyCheck(obj);
		setBoxName(obj, nm);
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.014"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optBoxForm" id="optBoxForm">
		<c:set var="map100" value="${VOMap100}" />
		<c:set var="map200" value="${VOMap200}" />
		<c:set var="map300" value="${VOMap300}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 감사문서 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.inspection.section.name"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 일상감사대장 사용 -->
							<span class="range_box" id="${map200.OPT207.optionId}">
								<input type="checkbox" value="Y" name="${map200.OPT207.optionId}" id="${map200.OPT207.optionId}" <c:if test="${map200.OPT207.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${map200.OPT207.description}')" />
								<span style="width:80px;">
									<font color="#3f3f3f"><spring:message code="tgw_app_option.${map200.OPT207.optionId}" /></font>
								</span>
								<input type="text" value="${map200.OPT207.optionValue}" name="${map200.OPT207.optionId}Value" id="${map200.OPT207.optionId}Value" onclick="showOptionResource(this, '${map200.OPT207.optionId}', '${map200.OPT207.resourceId}', '${map200.OPT207.optionValue}')" <c:if test="${map200.OPT207.useYn eq 'N'}">disabled</c:if> style="width:100px;" class="input" />
							</span>
							<!-- 감사문서함 사용 -->
							<span class="range_box" id="${map200.OPT206.optionId}">
								<input type="checkbox" value="Y" name="${map200.OPT206.optionId}" id="${map200.OPT206.optionId}" <c:if test="${map200.OPT206.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${map200.OPT206.description}')" />
								<span style="width:80px;">
									<font color="#3f3f3f"><spring:message code="tgw_app_option.${map200.OPT206.optionId}" /></font>
								</span>
								<input type="text" value="${map200.OPT206.optionValue}" name="${map200.OPT206.optionId}Value" id="${map200.OPT206.optionId}Value" onclick="showOptionResource(this, '${map200.OPT206.optionId}', '${map200.OPT206.resourceId}', '${map200.OPT206.optionValue}')" <c:if test="${map200.OPT206.useYn eq 'N'}">disabled</c:if> style="width:100px;" class="input" />
							</span>
							<!-- 감사열람함 사용 -->
							<span class="range_box" id="${map100.OPT114.optionId}">
								<input type="checkbox" value="Y" name="${map100.OPT114.optionId}" id="${map100.OPT114.optionId}" <c:if test="${map100.OPT114.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${map100.OPT114.description}')" />
								<span style="width:80px;">
									<font color="#3f3f3f"><spring:message code="tgw_app_option.${map100.OPT114.optionId}" /></font>
								</span>
								<input type="text" value="${map100.OPT114.optionValue}" name="${map100.OPT114.optionId}Value" id="${map100.OPT114.optionId}Value" onclick="showOptionResource(this, '${map100.OPT114.optionId}', '${map100.OPT114.resourceId}', '${map100.OPT114.optionValue}')" <c:if test="${map100.OPT114.useYn eq 'N'}">disabled</c:if> style="width:100px;" class="input" />
							</span>
							<!-- 감사직인날인대장 사용 -->
							<span class="range_box" id="${map200.OPT208.optionId}">
								<input type="checkbox" value="Y" name="${map200.OPT208.optionId}" id="${map200.OPT208.optionId}" <c:if test="${map200.OPT208.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${map200.OPT208.description}')" />
								<span style="width:100px;">
									<font color="#3f3f3f"><spring:message code="tgw_app_option.${map200.OPT208.optionId}" /></font>
								</span>
								<input type="text" value="${map200.OPT208.optionValue}" name="${map200.OPT208.optionId}Value" id="${map200.OPT208.optionId}Value" onclick="showOptionResource(this, '${map200.OPT208.optionId}', '${map200.OPT208.resourceId}', '${map200.OPT208.optionValue}')" <c:if test="${map200.OPT208.useYn eq 'N'}">disabled</c:if> style="width:110px;" class="input" />
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.inspection.05"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 일상감사 대상 사용 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt346.inspection.1"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 일상감사 대상 사용 -->
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map300.OPT346.optionId}" id="${map300.OPT346.optionId}" <c:if test="${map300.OPT346.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt346"/>
							</span>
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map300.OPT379.optionId}" id="${map300.OPT379.optionId}" <c:if test="${map300.OPT379.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt379"/>
							</span>
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map300.OPT380.optionId}" id="${map300.OPT380.optionId}" <c:if test="${map300.OPT380.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt380"/>
							</span>
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map300.OPT381.optionId}" id="${map300.OPT381.optionId}" <c:if test="${map300.OPT381.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt381"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.inspection.01"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 감사부서 열람 옵션 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt346.inspection.2"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 감사부서 열람 사용 -->
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map300.OPT312.optionId}" id="${map300.OPT312.optionId}" <c:if test="${map300.OPT312.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt312"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.inspection.02"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>

		<!-- 검사부열람함 열람범위 -->
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="radio" name="OPT342" id="OPT342" value="1" onClick="chainCheck();" <c:if test="${map300.OPT342.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt342.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT342" id="OPT342" value="2" onClick="chainCheck();" <c:if test="${map300.OPT342.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt342.2"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 감사열람함 문서 유형 및 구분 -->
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="checkbox" name="OPT999" id="OPT999" value="N" checked disabled />
							<spring:message code="env.option.subtitle.opt374.1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT375" id="OPT375" value="Y" <c:if test="${map300.OPT375.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt375"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT888" id="OPT888" value="N" checked disabled />
							<spring:message code="env.option.subtitle.opt376"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT377" id="OPT377" value="Y" <c:if test="${map300.OPT377.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt377"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.inspection.03"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 감사열람함 열람자 -->
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="checkbox" name="OPT000" id="OPT000" value="N" checked disabled />
							<spring:message code="env.option.subtitle.opt347.1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT347" id="OPT347" value="Y" <c:if test="${map300.OPT347.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt347.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.inspection.04"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="updateOptionYn();" value="<%=buttonSave%>" 
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