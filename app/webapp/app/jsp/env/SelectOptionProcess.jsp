<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionProcess.jsp 
 *  Description : 관리자 환경설정 - 결재구분 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.25 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 3. 25 
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
	.range {display:inline-block;width:244px;height:25px; !important;}
	.range_indent {text-indent:20px;display:inline-block;width:244px;height:25px; !important;}
	.range2 {display:inline-block;width:180px;height:25px; !important;}
</style>
<script type="text/javascript">
	function updateOptionYn() {
		if ($('input[name=OPT304]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt304"/>');
			return;
		}
		
		$('#OPT417optionValue').val("I1:"+$('#OPT417_1').val()+"^I2:"+$('#OPT417_2').val());
		$('#OPT418optionValue').val("I1:"+$('#OPT418_1').val()+"^I2:"+$('#OPT418_2').val());//jkkim added for MS-Word adaption 2013.03.20

		// 문서편집기 선택 사용 
		$('#OPT428optionValue').val("I1:" + $('#OPT428_1').val() + "^I2:" + $('#OPT428_2').val() + "^I3:" + $('#OPT428_3').val());
		if ($('#OPT428_1').val() == "N" && $('#OPT428_2').val() == "N" && $('#OPT428_3').val() == "N") {
			// alert("문서편집기를 선택해주세요.");
			alert('<spring:message code="env.option.editor.type.select"/>');
			return; 
		}
				
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionProcess.do", $("#optProcessForm").serialize(), function(data){
				// 다국어 추가
				registOptionResource();
				
				alert(data.msg);
				document.location.href = data.url;
			}, 'json');
	    }
	}

	function checkChain(obj) {
		if (obj.attr('id') == "OPT005" && obj.attr('checked')) {
			$("#OPT006").attr('checked', true);
			$("#OPT007").attr('checked', true);
			$("#OPT008").attr('checked', true);
			$("#OPT006Val").val("Y");
			$("#OPT007Val").val("Y");
			$("#OPT008Val").val("Y");
		} else if (obj.attr('id') == "OPT005" && !obj.attr('checked')) {
			$("#OPT006").attr('checked', false);
			$("#OPT007").attr('checked', false);
			$("#OPT008").attr('checked', false);
			$("#OPT006Val").val("N");
			$("#OPT007Val").val("N");
			$("#OPT008Val").val("N");
		} else if (obj.attr('id') == "OPT055" && obj.attr('checked')) {
			$("#OPT056").attr('checked', true);
			$("#OPT057").attr('checked', true);
			$("#OPT058").attr('checked', true);
			$("#OPT056Val").val("Y");
			$("#OPT057Val").val("Y");
			$("#OPT058Val").val("Y");
		} else if (obj.attr('id') == "OPT055" && !obj.attr('checked')) {
			$("#OPT056").attr('checked', false);
			$("#OPT057").attr('checked', false);
			$("#OPT058").attr('checked', false);
			$("#OPT056Val").val("N");
			$("#OPT057Val").val("N");
			$("#OPT058Val").val("N");
		} else if (obj.attr('id') == "OPT010" && obj.attr('checked')) {
			$("#OPT011").attr('checked', true);
			$("#OPT012").attr('checked', true);
			$("#OPT013").attr('checked', true);
			$("#OPT011Val").val("Y");
			$("#OPT012Val").val("Y");
			$("#OPT013Val").val("Y");
		} else if (obj.attr('id') == "OPT010" && !obj.attr('checked')) {
			$("#OPT011").attr('checked', false);
			$("#OPT012").attr('checked', false);
			$("#OPT013").attr('checked', false);
			$("#OPT011Val").val("N");
			$("#OPT012Val").val("N");
			$("#OPT013Val").val("N");
		} 
	}

	function checkChainDisabled(obj) {
		if (obj.checked == false) {
			$('input.disabledGroup').attr('disabled', true);
		} else {
			$('input.disabledGroup').removeAttr('disabled');
		}
	}

	function readOnlyCheckBox() {
		return false;
	}

	function changeYn(obj){
		if (obj.checked == true) {
			obj.value = "Y";
		} else if (obj.checked == false) {
			obj.value = "N";
		}
	}

</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.011"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" id="optProcessForm" name="optProcessForm">
		<c:set var="map300" value="${VOMap300}" />
		<c:set var="map400" value="${VOMap400}" />
		<c:set var="map417" value="${map417}" />
		<c:set var="map428" value="${map428}" />
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
					<tr bgcolor="#ffffff">
						<td width="100%" class="g_box">
							<!-- 요청유형 -->
							<c:forEach var="vo" items="${VOLists}">
								<span class="range">
								<input type="checkbox" value="Y" name="${vo.optionId}" id="${vo.optionId}" onClick="checkChain($('#${vo.optionId}'));" 
									<c:if test="${vo.useYn eq 'Y'}">checked</c:if> 
									<c:if test="${vo.optionId eq 'OPT001' or vo.optionId eq 'OPT002' or vo.optionId eq 'OPT014' or vo.optionId eq 'OPT020'}">checked</c:if> 
									<c:if test="${vo.optionId eq 'OPT001' or vo.optionId eq 'OPT002' or vo.optionId eq 'OPT014' or vo.optionId eq 'OPT020' or vo.optionId eq 'OPT006' or vo.optionId eq 'OPT007' or vo.optionId eq 'OPT008' or vo.optionId eq 'OPT011' or vo.optionId eq 'OPT012' or vo.optionId eq 'OPT013' or vo.optionId eq 'OPT056' or vo.optionId eq 'OPT057' or vo.optionId eq 'OPT058' }">disabled</c:if>
								/> <span style="width:80px"><font color="#3f3f3f"><spring:message code="tgw_app_option.${vo.optionId}" /></font></span>
								<input style="width:100" type="text" value="${vo.optionValue}" name="${vo.optionId}.Text" id="${vo.optionId}.Text" onclick="showOptionResource(this, '${vo.optionId}', '${vo.resourceId}', '${vo.optionValue}')" class="input" />
								
								</span>		
							</c:forEach>
							<c:forEach var="vo" items="${VOLists}">
								<c:if test="${vo.optionId eq 'OPT006' or vo.optionId eq 'OPT007' or vo.optionId eq 'OPT008' or vo.optionId eq 'OPT011' or vo.optionId eq 'OPT012' or vo.optionId eq 'OPT013' or vo.optionId eq 'OPT056' or vo.optionId eq 'OPT057' or vo.optionId eq 'OPT058' }">
									<input type="hidden" name="${vo.optionId}Val" id="${vo.optionId}Val" value="${vo.useYn}" />
								</c:if>
							</c:forEach>
							<input type="hidden" name="voSize" id="voSize" value="${fn:length(VOLists)}" />
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
							<!-- 일괄기안 -->
							<span class="range2">							
							<input type="checkbox" value="Y" name="${map300.OPT350.optionId}" id="${map300.OPT350.optionId}" <c:if test="${map300.OPT350.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt350"/>
							</span>
							<!-- 이중결재 -->
							<span class="range2">							
							<input type="checkbox" value="Y" name="${map300.OPT325.optionId}" id="${map300.OPT325.optionId}" <c:if test="${map300.OPT325.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt325"/>
							</span>
							<!-- 처리과만 결재요청유형 선택 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT378.optionId}" id="${map300.OPT378.optionId}" <c:if test="${map300.OPT378.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt378"/>
							</span>
							<!-- CEO결재문서 감사필수 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT320.optionId}" id="${map300.OPT320.optionId}" <c:if test="${map300.OPT320.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt320"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>
				<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.10"/></td></tr>
			</td>
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT345.section.name"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 심사기능 -->
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 심사기능 사용 -->
							<input type="checkbox" value="Y" name="OPT345" id="OPT345" <c:if test="${map300.OPT345.useYn eq 'Y'}">checked</c:if> onClick="checkChainDisabled(this);" />
							<spring:message code="env.option.subtitle.OPT345.section.main"/><br />	
							<!-- 기안/발송담당자 발송:1, 심사자 발송:2 -->
							<span class="range_indent">					
								<input type="checkbox" class="disabledGroup" name="OPT415" id="OPT415" <c:if test="${map400.OPT415.useYn eq 'Y'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT345.section.option1"/>
							</span>
							<span class="range">
								<input type="checkbox" class="disabledGroup" name="OPT415Disabled" id="OPT415Disabled" disabled checked />
								<spring:message code="env.option.subtitle.OPT345.section.option2"/>
							</span><br />	
							<!-- 직인날인 신청 사용 -->
							<span class="range_indent">
								<input type="checkbox" class="disabledGroup" name="OPT373" id="OPT373" <c:if test="${map300.OPT373.useYn eq 'Y'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT345.section.check2"/>
							</span>
							<!-- 서명인날인 신청 사용 -->
							<span class="range">
								<input type="checkbox" class="disabledGroup" name="OPT367" id="OPT367" <c:if test="${map300.OPT367.useYn eq 'Y'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT345.section.check3"/>
							</span>
							<!-- 기안/발송담당자 날인 허용 -->				
							<span class="range">
								<input type="checkbox" value="Y" class="disabledGroup" name="${map400.OPT416.optionId}" id="${map400.OPT416.optionId}" <c:if test="${map400.OPT416.useYn eq 'Y'}">checked</c:if> />
								<spring:message code="env.option.subtitle.OPT345.section.check1"/>	
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.44"/></td></tr>
				</acube:tableFrame>			 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 에디터 사용선택 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT428"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="checkbox" name="OPT428_1" id="OPT428_1" value="<c:if test="${map428.I1 eq 'Y'}">Y</c:if><c:if test="${map428.I1 eq 'N'}">N</c:if>" <c:if test="${map428.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.OPT428_1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT428_2" id="OPT428_2" value="<c:if test="${map428.I2 eq 'Y'}">Y</c:if><c:if test="${map428.I2 eq 'N'}">N</c:if>" <c:if test="${map428.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.OPT428_2"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT428_3" id="OPT428_3" value="<c:if test="${map428.I3 eq 'Y'}">Y</c:if><c:if test="${map428.I3 eq 'N'}">N</c:if>" <c:if test="${map428.I3 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.OPT428_3"/>
							</span>
							<input type="hidden" name="OPT428optionValue" id="OPT428optionValue" value="" />
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		
		<!-- 생략인 사용 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT417"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="checkbox" name="OPT417_1" id="OPT417_1" value="<c:if test="${map417.I1 eq 'Y'}">Y</c:if><c:if test="${map417.I1 eq 'N'}">N</c:if>" <c:if test="${map417.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.OPT417_1"/>
							</span>
							<span class="range">
							<input type="checkbox" name="OPT417_2" id="OPT417_2" value="<c:if test="${map417.I2 eq 'Y'}">Y</c:if><c:if test="${map417.I2 eq 'N'}">N</c:if>" <c:if test="${map417.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.OPT417_2"/>
							</span>
							<input type="hidden" name="OPT417optionValue" id="OPT417optionValue" value="" />
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.45"/></td></tr>
				</acube:tableFrame>
			</td>
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
							<input type="radio" name="OPT304" id="OPT304" value="1" <c:if test="${map300.OPT304.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt304.1"/>
							</span>
							<span class="range2">
							<input type="radio" name="OPT304" id="OPT304" value="2" <c:if test="${map300.OPT304.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt304.2"/>
							</span>
						</td>
					</tr>
				</acube:tableFrame>
				<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.03"/></td></tr>
			</td>
		</tr>	
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.56"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">	
							<!-- 수신자 재발송 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT351.optionId}" id="${map300.OPT351.optionId}" <c:if test="${map300.OPT351.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt351"/>
							</span>	
							<!-- 수신자 추가발송 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT315.optionId}" id="${map300.OPT315.optionId}" <c:if test="${map300.OPT315.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt315"/>
							</span>	
							<!-- 기본 발신명의(부서명+장) 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT349.optionId}" id="${map300.OPT349.optionId}" <c:if test="${map300.OPT349.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt349"/>
							</span>		
							<!-- 상위부서 발신명의 사용 -->						
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT327.optionId}" id="${map300.OPT327.optionId}" <c:if test="${map300.OPT327.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt327"/>	
							</span>	
							<!-- 부서발신명의 관리기능 사용 -->				
							<span class="range">
							<input type="checkbox" value="Y" name="${map300.OPT364.optionId}" id="${map300.OPT364.optionId}" <c:if test="${map300.OPT364.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt364"/>	
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.41"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>	
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.56.1"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">	
							<!-- 다중문서배부 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map400.OPT427.optionId}" id="${map400.OPT427.optionId}" <c:if test="${map400.OPT427.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT427"/>
							</span>	
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.41.1"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>	
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 문서접수 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.57"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="radio" name="OPT341" id="OPT341" value="1" <c:if test="${map300.OPT341.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt341.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT341" id="OPT341" value="2" <c:if test="${map300.OPT341.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt341.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.04"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 이송/경유 옵션 설정 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.54"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<!-- 접수전 이송 기능 사용 added by bonggon.choi. -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map400.OPT401.optionId}" id="${map400.OPT401.optionId}" <c:if test="${map400.OPT401.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt401"/>
							</span>
							<!-- 접수후 이송 기능 사용 -->
							<span class="range">	
							<input type="checkbox" value="Y" name="${map400.OPT402.optionId}" id="${map400.OPT402.optionId}" <c:if test="${map400.OPT402.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt402"/>
							</span>
							<!-- 경유 기능 사용 -->
							<span class="range">	
							<input type="checkbox" value="Y" name="${map400.OPT403.optionId}" id="${map400.OPT403.optionId}" <c:if test="${map400.OPT403.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt403"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.39"/></td></tr>
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