<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionEtc.jsp 
 *  Description : 관리자 환경설정 - 기타 결재옵션
 *  Modification Information 
 * 
 *   수 정 일 : 2012.03.28
 *   수 정 자 : 최봉곤
 *   수정내용 : 신전자결재 TF
 * 
 *  @author  신경훈
 *  @since 2011. 4. 1 
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

<!-- 다국어 추가, app-common.js 파일에 openWindow 메소드 있음, common.jsp 파일에 app-common.js 파일이 포함됨 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/app-common.js"></script>
<!-- 다국어 추가 종료 -->

<style type="text/css">
	.range {display:inline-block;width:170px;height:25px; !important;}
	.range2 {display:inline-block;width:270px;height:25px; !important;}
	.range200 {display:inline-block;width:220px;height:25px; !important;}
	.h25 {display:inline-block;height:25px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		checkChain($('#OPT310')[0]);
		checkChain($('#OPT413')[0]);
	}

	function changeYn(obj){
		if (obj.checked == true) {
			obj.value = "Y";
		} else if (obj.checked == false) {
			obj.value = "N";
		}
	}

	function updateOptionYn() {
		if ($('input[name=OPT303]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt303"/>');
			return;
		}
		if ($('input[name=OPT305]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt305"/>');
			return;
		}
		if ($('input[name=OPT322]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt322"/>');
			return;
		}
		if ($('input[name=OPT333]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt333"/>');
			return;
		}
		if ( $('input[name=OPT310]:checked').val() == "Y" && $('input[name=OPT310value]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt311"/>');
			return;
		}
		if ( $('input[name=OPT413]:checked').val() == "Y" && $('input[name=OPT413value]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt413"/>');
			return;
		}
		$('#OPT354optionValue').val("I1:"+$('#OPT354_1').val()+"^I2:"+$('#OPT354_2').val());
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionDisabled.do", $("#optEtcForm").serialize(), function(data){
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

	function checkChainEtc() {
		if ($('#OPT321').attr('checked') == false) { // 관련문서 사용안함이면
			$('#OPT370').attr('disabled', true);	 // 관련문서 필터링 사용여부 옵션 비활성화
			$('#OPT370').attr('checked', false);			
		} else {
			$('#OPT370').attr('disabled', false);
		}
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.017"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optEtcForm" id="optEtcForm">
		<c:set var="map000" value="${VOMap000}" />
		<c:set var="map300" value="${VOMap300}" />
		<c:set var="map400" value="${VOMap400}" />
		<c:set var="map354" value="${map354}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 결재구분 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.01"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">	
							<!-- 일상감사 -->
							<span class="range200">
								<input type="checkbox" value="Y" name="${map000.OPT021.optionId}" id="${map000.OPT021.optionId}" <c:if test="${map000.OPT021.useYn eq 'Y'}">checked</c:if> />
								<span style="width:70px"><font color="#3f3f3f">${map000.OPT021.description}</font></span>
								<input style="width:100" type="text" value="${map000.OPT021.optionValue}" name="${map000.OPT021.optionId}.Text" id="${map000.OPT021.optionId}.Text" onclick="showOptionResource(this, '${map000.OPT021.optionId}', '${map000.OPT021.resourceId}', '${map000.OPT021.optionValue}')" class="input" />
							</span>
							<!-- 준법감시 -->
							<span class="range200">	
								<input type="checkbox" value="Y" name="${map000.OPT022.optionId}" id="${map000.OPT022.optionId}" <c:if test="${map000.OPT022.useYn eq 'Y'}">checked</c:if> />
								<span style="width:70px"><font color="#3f3f3f">${map000.OPT022.description}</font></span>
								<input style="width:100" type="text" value="${map000.OPT022.optionValue}" name="${map000.OPT022.optionId}.Text" id="${map000.OPT022.optionId}.Text" onclick="showOptionResource(this, '${map000.OPT022.optionId}', '${map000.OPT022.resourceId}', '${map000.OPT022.optionValue}')" class="input" />
							</span>
							<!-- 감사위원 -->
							<span class="range200">	
								<input type="checkbox" value="Y" name="${map000.OPT023.optionId}" id="${map000.OPT023.optionId}" <c:if test="${map000.OPT023.useYn eq 'Y'}">checked</c:if> />
								<span style="width:70px"><font color="#3f3f3f">${map000.OPT023.description}</font></span>
								<input style="width:100" type="text" value="${map000.OPT023.optionValue}" name="${map000.OPT023.optionId}.Text" id="${map000.OPT023.optionId}.Text" onclick="showOptionResource(this, '${map000.OPT023.optionId}', '${map000.OPT023.resourceId}', '${map000.OPT023.optionValue}')" class="input" />
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.01"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 결재 후 부서협조 사용 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT337.optionId}" id="${map300.OPT337.optionId}" <c:if test="${map300.OPT337.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt337"/>
							</span>
							<!-- 결재 후 병렬협조 사용 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT360.optionId}" id="${map300.OPT360.optionId}" <c:if test="${map300.OPT360.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt360"/>
							</span>
							<!-- 결재 후 협조 사용 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT336.optionId}" id="${map300.OPT336.optionId}" <c:if test="${map300.OPT336.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt336"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>
				<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.10"/></td></tr>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 부서협조 표시 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt303"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range2">				
							<input type="radio" name="OPT303" id="OPT303" value="1" <c:if test="${map300.OPT303.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt303.1"/>
							</span>
							<span class="range2">
							<input type="radio" name="OPT303" id="OPT303" value="2" <c:if test="${map300.OPT303.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt303.2"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>
				<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.02"/></td></tr>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 감사 표시 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt304"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range2">
							<input type="radio" name="OPT304" id="OPT304" value="3" <c:if test="${map300.OPT304.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt304.3"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>
				<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.03"/></td></tr>
			</td>
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt333"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 문서수신대상 -->
							<span class="range">						
							<input type="radio" name="OPT333" id="OPT333" value="1" <c:if test="${map300.OPT333.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt333.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT333" id="OPT333" value="2" <c:if test="${map300.OPT333.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt333.2"/>
							</span>												
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.16"/></td></tr>
				</acube:tableFrame>				 
				
			</td>
		</tr>	
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 채번 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt358"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="OPT358" id="OPT358" value="1" <c:if test="${map300.OPT358.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt358.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT358" id="OPT358" value="2" <c:if test="${map300.OPT358.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt358.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.33"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 하위번호 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt310"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 하위번호 -->
							<span class="range">
							<input type="checkbox" value="Y" name="OPT310" id="OPT310" <c:if test="${map300.OPT310.useYn eq 'Y'}">checked</c:if> onClick="checkChain(this);"/>
							<spring:message code="env.option.subtitle.opt310"/><br />
							<input type="radio" name="OPT310value" id="OPT310value" value="1" <c:if test="${map300.OPT311.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt311.1"/>
							</span>
							<input type="radio" name="OPT310value" id="OPT310value" value="2" <c:if test="${map300.OPT311.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt311.2"/>					
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.18"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 반려문서결재 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt305"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">							
							<input type="radio" name="OPT305" id="OPT305" value="1" <c:if test="${map300.OPT305.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt305.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT305" id="OPT305" value="2" <c:if test="${map300.OPT305.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt305.2"/>	
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.19"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 부서별 양식등록 사용 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt354"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="checkbox" name="OPT354_1" id="OPT354_1" value="<c:if test="${map354.I1 eq 'Y'}">Y</c:if><c:if test="${map354.I1 eq 'N'}">N</c:if>" <c:if test="${map354.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt354.1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT354_2" id="OPT354_2" value="<c:if test="${map354.I2 eq 'Y'}">Y</c:if><c:if test="${map354.I2 eq 'N'}">N</c:if>" <c:if test="${map354.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt354.2"/>
							</span>
							<input type="hidden" name="OPT354optionValue" id="OPT354optionValue" value="" />
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.32"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 기타 결재옵션 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.10"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">								
							<!-- 그룹내(계열사)자동발송 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT307.optionId}" id="${map300.OPT307.optionId}" <c:if test="${map300.OPT307.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt307"/>
							</span>
							<!-- 서명인 그룹내(계열사)발송 -->
							<span class="range2">	
							<input type="checkbox" value="Y" name="${map300.OPT308.optionId}" id="${map300.OPT308.optionId}" <c:if test="${map300.OPT308.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt308"/>
							</span>
							<!-- 채번문서 담당접수 사용불가 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT355.optionId}" id="${map300.OPT355.optionId}" <c:if test="${map300.OPT355.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt355"/>
							</span>
							<!-- 미채번문서 접수경로 사용불가 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT356.optionId}" id="${map300.OPT356.optionId}" <c:if test="${map300.OPT356.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt356"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.21"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.gen.etc"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 관련문서 사용 -->
							<!--
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT321.optionId}" id="${map300.OPT321.optionId}" <c:if test="${map300.OPT321.useYn eq 'Y'}">checked</c:if> onClick="checkChainEtc();" />
							<spring:message code="env.option.subtitle.opt321"/>
							</span>
							-->
							<!-- 관련문서 필터링 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT370.optionId}" id="${map300.OPT370.optionId}" <c:if test="${map300.OPT370.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt370"/>
							</span>
							<!-- DRM 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT319.optionId}" id="${map300.OPT319.optionId}" <c:if test="${map300.OPT319.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt319"/>
							</span>
							<!-- 모바일 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT343.optionId}" id="${map300.OPT343.optionId}" <c:if test="${map300.OPT343.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt343"/>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.28"/></td></tr>
				</acube:tableFrame> 				
			</td>
		</tr>		
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT413"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 대내문서 자동발송 -->
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 대내문서 자동발송 및 자동발송 날인방법 -->
							<input type="checkbox" value="Y" name="OPT413" id="OPT413" <c:if test="${map400.OPT413.useYn eq 'Y'}">checked</c:if> onClick="checkChain(this);" />
							<spring:message code="env.option.subtitle.OPT413.0"/><br />	
							<span class="range">					
								<input type="radio" name="OPT413value" id="OPT413value" value="1" <c:if test="${map400.OPT414.useYn eq '1'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT413.1"/>
							</span>
							<span class="range">
								<input type="radio" name="OPT413value" id="OPT413value" value="2" <c:if test="${map400.OPT414.useYn eq '2'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT413.2"/>
							</span>
							<span class="range">
								<input type="radio" name="OPT413value" id="OPT413value" value="3" <c:if test="${map400.OPT414.useYn eq '3'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT413.3"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.43"/></td></tr>
				</acube:tableFrame>			 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- PDF파일 저장권한 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt322"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="${map300.OPT322.optionId}" id="${map300.OPT322.optionId}" value="1" <c:if test="${map300.OPT322.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt322.1"/>
							</span>
							<span class="range">
							<input type="radio" name="${map300.OPT322.optionId}" id="${map300.OPT322.optionId}" value="2" <c:if test="${map300.OPT322.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt322.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.25"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
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