<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : EnvOptionReadShow.jsp 
 *  Description : 관리자 환경설정 - 공람/열람범위
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
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
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
	.range {display:inline-block;width:180px;height:25px; !important;}
	.h25 {display:inline-block;height:25px; !important;}
</style>

<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		checkChain($('#OPT334')[0]);
		checkChain($('#OPT335')[0]);
		checkOption($('#OPT316UseYn'));
	}
	function updateOptionYn() {
		if ( $('input[name=OPT334]:checked').val() == "Y" && $('input[name=OPT334value]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt334"/>');
			return;
		}
		if ( $('input[name=OPT335]:checked').val() == "Y" && $('input[name=OPT335value]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt335"/>');
			return;
		}
		if ($('#OPT316UseYn').attr('checked') && !$('#OPT316_1').attr('checked') && !$('#OPT316_2').attr('checked')) {
			alert('<spring:message code="env.option.msg.validate.opt316"/>');
			return;
		}
		if ($('#OPT316UseYn').attr('checked') && $.trim($('#OPT316Value').val()) == "") {
			alert('<spring:message code="env.option.msg.validate.opt316value"/>');
			$('#OPT316Value').focus();
			return;
		}		
		if ($('input[name=OPT314]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt314"/>');
			return;
		}
		setOPT316();
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionReadShow.do", $("#optReadShowForm").serialize(), function(data){
				// 다국어 추가
				registOptionResource();
				
				alert(data.msg);
				document.location.href = data.url;
			}, 'json');
	    }
	}
	
	function checkChain(obj) {
		var objRadio = obj.name+"value";
		var radios = $('input:radio[name="'+objRadio+'"]');
		
		if (obj.checked == false) {
			radios.attr('disabled', true);
		} else {
			radios.attr('disabled', false);
		}
	}

	function checkOption(obj) {
		if (obj.attr('checked') == true) {
			$('#divOPT316item').show();
		} else {
			$('#divOPT316item').hide();
		}
	}

	function setOPT316() {
		var opt316Val = "";
		if (!$('#OPT316UseYn').attr('checked')) {
			opt316Val = "0";
		} else if ($('#OPT316UseYn').attr('checked') && $('#OPT316_1').attr('checked') && !$('#OPT316_2').attr('checked')) {
			opt316Val = "1";
		} else if ($('#OPT316UseYn').attr('checked') && !$('#OPT316_1').attr('checked') && $('#OPT316_2').attr('checked')) {
			opt316Val = "2";
		} else if ($('#OPT316UseYn').attr('checked') && $('#OPT316_1').attr('checked') && $('#OPT316_2').attr('checked')) {
			opt316Val = "3";
		}
		$('#OPT316').val(opt316Val);
	}
	
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.015"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optReadShowForm" id="optReadShowForm">
		<c:set var="map" value="${VOMap}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.show"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 공람 -->
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 생산문서 공람사용 -->
							<span class="h25" style="padding-bottom:12px;">
								<input type="checkbox" value="Y" name="OPT334" id="OPT334" <c:if test="${map.OPT334.useYn eq 'Y'}">checked</c:if> onClick="checkChain(this);" />
								<spring:message code="env.option.subtitle.opt334"/><br />	
								<span class="range">					
									<input type="radio" name="OPT334value" id="OPT334value" value="B" <c:if test="${map.OPT334.optionValue eq 'B'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt334.1"/>
								</span>
								<span class="range">
									<input type="radio" name="OPT334value" id="OPT334value" value="A" <c:if test="${map.OPT334.optionValue eq 'A'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt334.2"/>
								</span>
							</span><br />
							<!-- 접수문서 공람사용 -->
							<span class="h25" style="padding-bottom:12px;">
								<input type="checkbox" value="Y" name="OPT335" id="OPT335" <c:if test="${map.OPT335.useYn eq 'Y'}">checked</c:if>  onClick="checkChain(this);" />
								<spring:message code="env.option.subtitle.opt335"/><br />	
								<span class="range">				
									<input type="radio" name="OPT335value" id="OPT335value" value="B" <c:if test="${map.OPT335.optionValue eq 'B'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt335.1"/>
								</span>
								<span class="range">
									<input type="radio" name="OPT335value" id="OPT335value" value="A" <c:if test="${map.OPT335.optionValue eq 'A'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt335.2"/>
								</span>
							</span><br />
							<!-- 공람문서함에서 공람자 추가 -->
							<span class="h25" style="padding-bottom:12px;">
								<span class="range">
									<input type="checkbox" value="Y" name="${map.OPT366.optionId}" id="${map.OPT366.optionId}" <c:if test="${map.OPT366.useYn eq 'Y'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt366"/>
								</span>
								<!-- 공람자목록 소속부서/타부서 분리 -->				
								<span class="range">
									<input type="checkbox" value="Y" name="${map.OPT372.optionId}" id="${map.OPT372.optionId}" <c:if test="${map.OPT372.useYn eq 'Y'}">checked</c:if> />
									<spring:message code="env.option.subtitle.opt372"/>	
								</span>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.13"/></td></tr>
				</acube:tableFrame>			 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt316"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
			
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 공람게시 -->							
							<input type="checkbox" name="OPT316UseYn" id="OPT316UseYn" value="0" onClick="checkOption($('#OPT316UseYn'))" <c:if test="${map.OPT316.useYn > 0}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt316.0"/><br />
							
							<div id="divOPT316item" style="padding:10px 0 0 3px;display:block;">
							<span class="range">
							<spring:message code="env.option.text.opt316.name"/> : 
							<input type="text" name="OPT316Value" id="OPT316Value" value="${map.OPT316.optionValue}" class="input" style="width:100px;" onclick="showOptionResource(this, '${map.OPT316.optionId}', '${map.OPT316.resourceId}', '${map.OPT316.optionValue}')" <c:if test="${map.OPT316.useYn eq '0'}">readOnly</c:if> />
							</span>							
							<span class="range">
							<input type="checkbox" name="OPT316_1" id="OPT316_1" value="1" <c:if test="${map.OPT316.useYn eq '1' or map.OPT316.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt316.1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT316_2" id="OPT316_2" value="2" <c:if test="${map.OPT316.useYn eq '2' or map.OPT316.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt316.2"/>
							</span>
							</div>
							<input type="hidden" id="OPT316" name="OPT316" value="${map.OPT316.useYn}" />								
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.14"/></td></tr>
				</acube:tableFrame>				 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt314"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
			
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 생산문서 열람범위 -->
							<span class="range">						
							<input type="radio" name="OPT314" id="OPT314" value="1" <c:if test="${map.OPT314.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt314.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT314" id="OPT314" value="2" <c:if test="${map.OPT314.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt314.2"/>
							</span>												
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.15"/></td></tr>
				</acube:tableFrame>				 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt361"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
			
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 접수문서 열람범위 -->
							<span class="range">						
							<input type="radio" name="OPT361" id="OPT361" value="1" <c:if test="${map.OPT361.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt314.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT361" id="OPT361" value="2" <c:if test="${map.OPT361.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt314.2"/>
							</span>												
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.34"/></td></tr>
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