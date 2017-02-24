<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : EnvOptionGenEtc.jsp
 *  Description : 관리자 환경설정 - 기타 일반옵션
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.7 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 7
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
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	.range {display:inline-block;width:180px;height:30px; !important;}
	.range2 {display:inline-block;width:110px;height:30px; !important;}
	.h25 {display:inline-block;height:30px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	var gSaveLineObject = g_selector();
	var sColor = "#e0e0e0";
	
	function updateOptionYn() {
		if ( $('input[name=OPT330]:checked').val() == "Y" && $.trim($('#OPT330optionValue').val()) == "") {
			alert('<spring:message code="env.option.msg.validate.opt330"/>');
			$('#OPT330optionValue').focus();
			return;
		}
		if ($('input[name=OPT322]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt322"/>');
			return;
		}
		if ($('input[name=OPT331]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt331"/>');
			return;
		}
		/*
		if ($('input[name=OPT340]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt340"/>');
			return;
		}
		*/
		var positions = $('#tblPosition tbody').children().first().children();
		var posPriority = $('#'+positions[0].id).attr('id')
						  +"^"+ $('#'+positions[1].id).attr('id')
						  +"^"+ $('#'+positions[2].id).attr('id')
						  +"^"+ $('#'+positions[3].id).attr('id')
						  +"^"+ $('#'+positions[4].id).attr('id');
		$('#posPriority').val(posPriority);
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionGenEtc.do", $("#optGenEtcForm").serialize(), function(data){
					alert(data.msg);
					document.location.href = data.url;
			}, 'json');
	    }
	}
	
	function checkChain(obj) {		
		if (obj.checked == false) {
			$('#OPT330optionValue').attr('disabled', true);
		} else {
			$('#OPT330optionValue').attr('disabled', false);
		}
	}

	function onLineClick(obj) {
		selectOneLineElement(obj);
	}

	function selectOneLineElement(obj) {
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}

	function onMoveLeft() {
		if(gSaveLineObject.collection().length === 1){
			var obj = gSaveLineObject.first();
			var prev = obj.prev();
			
			prev.before($('#'+obj.attr('id')));
		}
	}

	function onMoveRight() {
		if(gSaveLineObject.collection().length === 1){
			var obj = gSaveLineObject.first();
			var prev = obj.next();
			
			prev.after($('#'+obj.attr('id')));
		}
	}

	function checkChain() {
		if ($('#OPT321').attr('checked') == false) { // 관련문서 사용안함이면
			$('#OPT370').attr('disabled', true);	 // 관련문서 필터링 사용여부 옵션 비활성화
			$('#OPT370').attr('checked', false);			
		} else {
			$('#OPT370').attr('disabled', false);
		}
	}

	function initialize() {
		checkChain();
		var opsitions = $('#tblPosition tbody').children().first().children();
		onLineClick($('#'+opsitions[0].id));
		if ($('#OPT330').attr('checked') == false) {
			$('#OPT330optionValue').attr('disabled', true);
		} else {
			$('#OPT330optionValue').attr('disabled', false);
		}	
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.13"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optGenEtcForm" id="optGenEtcForm">
		<c:set var="map" value="${VOMap}" />
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
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT321.optionId}" id="${map.OPT321.optionId}" <c:if test="${map.OPT321.useYn eq 'Y'}">checked</c:if> onClick="checkChain();" />
							<spring:message code="env.option.subtitle.opt321"/>
							</span>
							<!-- 관련문서 필터링 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT370.optionId}" id="${map.OPT370.optionId}" <c:if test="${map.OPT370.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt370"/>
							</span>
							<!-- 관련규정 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT344.optionId}" id="${map.OPT344.optionId}" <c:if test="${map.OPT344.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt344"/>
							</span>
							<!-- 거래처 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT348.optionId}" id="${map.OPT348.optionId}" <c:if test="${map.OPT348.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt348"/>
							</span>
							<!-- DRM 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT319.optionId}" id="${map.OPT319.optionId}" <c:if test="${map.OPT319.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt319"/>
							</span>
							<!-- 모바일 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT343.optionId}" id="${map.OPT343.optionId}" <c:if test="${map.OPT343.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt343"/>
							</span><br />
							<!-- 첨부사이즈 제한 -->
							<span class="h25">	
							<input type="checkbox" value="Y" name="${map.OPT330.optionId}" id="${map.OPT330.optionId}" <c:if test="${map.OPT330.useYn eq 'Y'}">checked</c:if> onClick="checkChain(this);" />
							<spring:message code="env.option.subtitle.opt330"/>&nbsp;
							<input type="text" class="input_read" style="width:40px;"  value="${map.OPT330.optionValue}" name="OPT330optionValue" id="OPT330optionValue" style="ime-mode: disabled;" onkeypress="return onlyNumber(event, false);" onkeyup="checkInputMaxLength(this,'',3)"/>&nbsp;MB
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.28"/></td></tr>
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
							<input type="radio" name="${map.OPT322.optionId}" id="${map.OPT322.optionId}" value="1" <c:if test="${map.OPT322.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt322.1"/>
							</span>
							<span class="range">
							<input type="radio" name="${map.OPT322.optionId}" id="${map.OPT322.optionId}" value="2" <c:if test="${map.OPT322.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt322.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.25"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 조회기본기간 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt331"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range2">
							<input type="radio" name="${map.OPT331.optionId}" id="${map.OPT331.optionId}" value="1" <c:if test="${map.OPT331.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.1"/>
							</span>
							<span class="range2">
							<input type="radio" name="${map.OPT331.optionId}" id="${map.OPT331.optionId}" value="2" <c:if test="${map.OPT331.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.2"/>
							</span>
							<span class="range2">
							<input type="radio" name="${map.OPT331.optionId}" id="${map.OPT331.optionId}" value="3" <c:if test="${map.OPT331.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.3"/>
							</span>
							<span class="range2">
							<input type="radio" name="${map.OPT331.optionId}" id="${map.OPT331.optionId}" value="4" <c:if test="${map.OPT331.useYn eq '4'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.4"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.26"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 직위표시 우선순위 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt340"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="g_box">
					<tr>
						<td>
							<table cellpadding="0" cellpadding="0">
								<tr>
									<td style="font-family:Dotum,Gulim,Arial;font-size:9pt;font-weight:bold;padding-top:3px;padding-right:10px;">
										<spring:message code="env.option.form.22"/>
									</td>
									<td class="ltb_center">
										<table id="tblPosition" cellpadding="2" cellspacing="1" width="400" bgcolor="#aaaaaa">	
											<tbody>				
												<tr>
													<c:set var="attrPos" value="${attrPos}" />
													<td id="${attrPos[0]}" onClick='onLineClick($("#${attrPos[0]}"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.position.priority.${attrPos[0]}"/></td>
													<td id="${attrPos[1]}" onClick='onLineClick($("#${attrPos[1]}"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.position.priority.${attrPos[1]}"/></td>
													<td id="${attrPos[2]}" onClick='onLineClick($("#${attrPos[2]}"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.position.priority.${attrPos[2]}"/></td>
													<td id="${attrPos[3]}" onClick='onLineClick($("#${attrPos[3]}"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.position.priority.${attrPos[3]}"/></td>
													<td id="${attrPos[4]}" onClick='onLineClick($("#${attrPos[4]}"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.position.priority.${attrPos[4]}"/></td>
												</tr>
											</tbody>	
										</table>
									</td>
									<td style="padding-left:10px;padding-top:3px;">
										<img src="<%=webUri%>/app/ref/image/bu_left.gif" onclick="javascript:onMoveLeft();return(false);">&nbsp;
										<img src="<%=webUri%>/app/ref/image/bu_right.gif" onclick="javascript:onMoveRight();return(false);">
									</td>
								</tr>
							</table>
							
						</td>
					</tr>					
				</acube:tableFrame>
				
				
			</td>
		</tr>
		<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.27"/></td></tr>
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
		<input type="hidden" id="posPriority" name="posPriority" value="" />
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>