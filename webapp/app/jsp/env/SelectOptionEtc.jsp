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

	String compId = "comp" + (String)session.getAttribute("COMP_ID");
	String periodType = AppConfig.getProperty("periodType", "Y", "etc");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/app-common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<style type="text/css">
	.range {display:inline-block;width:170px;height:25px; !important;}
	.range200 {display:inline-block;width:200px;height:25px; !important;}
	.range220 {display:inline-block;width:220px;height:25px; !important;}
	.range1 {display:inline-block;width:100px;height:25px; !important;}
	.range2 {display:inline-block;width:270px;height:25px; !important;}
	.h25 {display:inline-block;height:25px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var gSaveLineObject = g_selector();
var gRuleLineObject = g_selector();
var gItemIndex = 1;
var sColor = "#e0e0e0";
var db_korean_byte = <%=AppConfig.getProperty("db_korean_byte", "2", "locale")%>;

	function initialize() {
		$('#OPT410_default').attr('disabled', true);
		
		var opsitions = $('#tblPosition tbody').children().first().children();
		onLineClick($('#'+opsitions[0].id));

		drawDocRule();

		var opt422useYn = '${VOMap400.OPT422.useYn}';
		if ( opt422useYn == 'N' )
		{
			$('#CLASSNUM').hide();
			if ( $('#_CLASSNUM').length > 0 ) {
				$('#_CLASSNUM').hide();
				if ( $('#_CLASSNUM').next().text() == '-' ) {
					$('#_CLASSNUM').next().hide();
				}
			}
		}
	}

	function drawDocRule()
	{
		var ruleLine = '${VOMap400.OPT418.optionValue}';
		var jRuleLine = $('#ruleLine');
		var ruleLineSize = ruleLine.split(':').length;
		
		$.each(ruleLine.split(':'), 
			function(index, lineItem) 
			{
				var id = '_' + lineItem; if ( lineItem == '-' ) id = '_00' + (gItemIndex++);

				var displayName = getDisplayName(lineItem);
				var width = "72"; if ( displayName == "-" ) width = "30";

				if ( ruleLineSize - 1 == index ) // 마지막 하이픈이면.. (삭제 및 이동 금지)
					jRuleLine.append('<td id="ruleLineEndHyphen" style="background-color:#ffffff;" width="' + width + '" class="ltb_center">'+displayName+'</td>');
				else
					jRuleLine.append('<td id="' + id + '" onclick="onRuleLineClick($(\'#' + id + '\'))" ondblclick="onLineDblClickForDelete($(\'#' + id + '\'))" style="background-color:#ffffff;" width="' + width + '" class="ltb_center">'+displayName+'</td>');
			});

		jRuleLine.append('<td id="ruleLineEndItem" style="background-color:#ffffff;" width="72" class="ltb_center"><spring:message code="env.document.number.rule.serialnum"/></td>');
	}
	
	function prepareDocRuleSave()
	{
		var ruleLine = $('#ruleLine');
		var saveRuleLine = '';
		
		$.each(ruleLine.children(), 
				function(index, boxItem) 
				{
					if ( boxItem.id.indexOf('_00') == -1 ) // hyphen 항목이 아니면..
					{
						if ( boxItem.id == 'ruleLineEndItem' ) // 일련번호 항목이면..
						{ // do nothing..
						} else if ( boxItem.id == 'ruleLineEndHyphen' ) { // 마지막 하이픈이면..
							saveRuleLine += '-';
							saveRuleLine += ':';
						}
						 else {
							saveRuleLine += boxItem.id.substr(1); // remove underbar('_');
							saveRuleLine += ':';
						}
					}
					else // hyphen 항목이면..
					{
						saveRuleLine += '-';
						saveRuleLine += ':';
					}
				});
		
		saveRuleLine = saveRuleLine.substr(0, saveRuleLine.lastIndexOf(':'));

		$('#saveRuleLine').val(saveRuleLine);
	}
	
	function getDisplayName(value){
		if (value == "DEPTNAME") return '<spring:message code="env.document.number.rule.deptname"/>';
		if (value == "DEPTACRO") return '<spring:message code="env.document.number.rule.deptacro"/>';
		if (value == "SESSION")
		{
			if ( "<%=periodType%>" == "Y")
				return 	'<spring:message code="env.document.number.rule.year"/>';
			if ( "<%=periodType%>" == "P")
				return 	'<spring:message code="env.document.number.rule.session"/>';
		}
		if (value == "CLASSNUM") return '<spring:message code="env.document.number.rule.classnum"/>';
		if (value == "-" || value == "HYPHEN") return '-';
	}

	function changeYn(obj){
		if (obj.checked == true) {
			obj.value = "Y";
		} else if (obj.checked == false) {
			obj.value = "N";
		}
	}

	function updateOptionYn() 
	{
		if ($('#OPT422').attr('checked') == false) { // 문서분류체계 사용안함이면..
			if ( $('#_CLASSNUM').length > 0 ) {
				if ( $('#_CLASSNUM').next().text() == '-' ) {
					$('#_CLASSNUM').next().remove();
				}
				$('#_CLASSNUM').remove();
			}
		}
		
		if ($('input[name=OPT313]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt313"/>');
			return;
		}
		if ($('input[name=OPT411]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt411"/>');
			return;
		}
		if ($('input[name=OPT400-567]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt406"/>');
			return;
		}
		
		$('#OPT410optionValue').val("I1:"+$('#OPT410_1').val()+"^I2:"+$('#OPT410_2').val()
				+"^I3:"+$('#OPT410_3').val()+"^I4:"+$('#OPT410_4').val());

		var positions = $('#tblPosition tbody').children().first().children();
		
		var posPriority = $('#'+positions[0].id).attr('id')
						  +"^"+ $('#'+positions[1].id).attr('id')
						  +"^"+ $('#'+positions[2].id).attr('id')
						  +"^"+ $('#'+positions[3].id).attr('id')
						  +"^"+ $('#'+positions[4].id).attr('id');

		$('#posPriority').val(posPriority);

		setOPT400_567();

		prepareDocRuleSave();
		
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionEtc.do", $("#optEtcForm").serialize(), function(data){
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
		
	function checkChainForClassNum(obj) {
		if (obj.checked == false) {
			if ( confirm('<spring:message code="env.option.msg.confirm.class.num"/>') ) {
				$('#CLASSNUM').hide();
				if ( $('#_CLASSNUM').length > 0 ) {
					$('#_CLASSNUM').hide();
					if ( $('#_CLASSNUM').next().text() == '-' ) {
						$('#_CLASSNUM').next().hide();
					}
				}
			} else
			{
				obj.checked = true;
			}
			
		} else {
			$('#CLASSNUM').show();
			if ( $('#_CLASSNUM').length > 0 ) {
				$('#_CLASSNUM').show();
				if ( $('#_CLASSNUM').next().text() == '-' ) {
					$('#_CLASSNUM').next().show();
				}
			}
		}
	}	

	function onLineClick(obj) {
		selectOneLineElement(obj);
	}
	
	function onRuleLineClick(obj) {
		selectOneRuleLineElement(obj);
	}
	
	function onLineDblClickForInsert(obj) {
		var _tr = $('#ruleLine');
		var displayName = getDisplayName(obj.attr('id'));
		var itemID = '_' + obj.attr('id'); if ( obj.attr('id') == 'HYPHEN' ) itemID = '_00' + gItemIndex++;
		var width = "72"; if ( displayName == "-" ) width = "30";

		var isExist = false;

		$.each(_tr.children(), 
				function(index, boxItem) 
				{
					if ( boxItem.id.indexOf('_00') == -1 ) // HYPHEN 이 아니면..
					{
						if ( itemID == boxItem.id )
						{
							alert('<spring:message code="env.option.msg.validate.docnum.rule.exist"/>');
							isExist = true;
							return false; // break;
						} 
					}
				});

		if ( !isExist )
		{
			_tr.append('<td id="' + itemID + '" onclick="onRuleLineClick($(\'#' + itemID + '\'))" ondblclick="onLineDblClickForDelete($(\'#' + itemID + '\'))" style="background-color:#ffffff;" width="'+ width + '" class="ltb_center">' + displayName + '</td>');

			// 일련번호 앞으로 이동.
			var lastItem = _tr.children().last();
			var prevItem = lastItem.prev();
			lastItem.after(prevItem);
			
			// 하이픈 앞으로 이동.
			var hyphenItem = _tr.children().last().prev().prev();
			var insertItem = _tr.children().last().prev();

			insertItem.after(hyphenItem);
		}
			
		
		selectOneRuleLineElement($('#'+itemID));
	}
	
	function onLineDblClickForDelete(obj) {
		obj.remove();
		gItemIndex--;
	}

	function selectOneLineElement(obj) {
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}
	
	function selectOneRuleLineElement(obj) {
		$('document').empty();
		gRuleLineObject.restore();
		gRuleLineObject.add(obj, sColor);
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
	
	function onRuleMoveLeft() {
		if(gRuleLineObject.collection().length === 1){
			var obj = gRuleLineObject.first();

			if ( obj.attr('id') == 'ruleLineEndItem' || obj.attr('id') == 'ruleLineEndHyphen' )
			{ // do nothing.. 
			} else {
				var prev = obj.prev();
				prev.before($('#'+obj.attr('id')));
			}
		}
	}

	function onRuleMoveRight() {
		if(gRuleLineObject.collection().length === 1){
			var obj = gRuleLineObject.first();
			var nextItem = obj.next();

			if ( nextItem.attr('id') == 'ruleLineEndItem' || nextItem.attr('id') == 'ruleLineEndHyphen' )
			{ // do nothing.. 
			} else {
				var prev = obj.next();
				prev.after($('#'+obj.attr('id')));
			}
		}
	}


	function setOPT400_567() 
	{
		var optVal = $('input[name=OPT400-567]:checked').val();
		
		if ( optVal == "1" )
		{
			$('#OPT405').val('Y'); $('#OPT407').val('N'); $('#OPT406').val('N'); 
		} 
		else if ( optVal == "2" )
		{ 
			$('#OPT405').val('N'); $('#OPT407').val('Y'); $('#OPT406').val('N'); 
		}
		else if ( optVal == "3" )
		{ 
			$('#OPT405').val('N'); $('#OPT407').val('N'); $('#OPT406').val('Y'); 
		}
	}
	
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.016"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optEtcForm" id="optEtcForm">
		<c:set var="map300" value="${VOMap300}" />
		<c:set var="map400" value="${VOMap400}" />
		<c:set var="map410" value="${map410}" />
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.58"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">								
							<!-- 개인 결재경로그룹 사용 -->
							<span class="range2">
							<input type="checkbox" value="Y" name="${map300.OPT353.optionId}" id="${map300.OPT353.optionId}" <c:if test="${map300.OPT353.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt353"/>
							</span>
							<!-- 부서장을 기본 결재자로 지정(생산문서) -->
							<span class="range2">	
							<input type="checkbox" value="Y" name="${map300.OPT352.optionId}" id="${map300.OPT352.optionId}" <c:if test="${map300.OPT352.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt352"/>
							</span>
							<!-- 부서장을 기본 선람자로 지정(접수문서) -->
							<span class="range2">	
							<input type="checkbox" value="Y" name="${map300.OPT362.optionId}" id="${map300.OPT362.optionId}" <c:if test="${map300.OPT362.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt362"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.etc.01"/></td></tr>
				</acube:tableFrame> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 문서보안 암호설정 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT411"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="OPT411" id="OPT411" value="1" <c:if test="${map400.OPT411.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT411.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT411" id="OPT411" value="2" <c:if test="${map400.OPT411.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT411.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.42"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.59"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 수신자 지정 -->
							<span class="range1">
								<input type="checkbox" value="Y" 
										name="OPT410_default" id="OPT410_default" checked />
								<spring:message code="env.option.subtitle.OPT410.1"/>
							</span>
							<span class="range1">
								<input type="checkbox" value="<c:if test="${map410.I1 eq 'Y'}">Y</c:if><c:if test="${map410.I1 eq 'N'}">N</c:if>" 
										name="OPT410_1" id="OPT410_1" <c:if test="${map410.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
								<spring:message code="env.option.subtitle.OPT410.2"/>
							</span>
							<span class="range1">
								<input type="checkbox" value="<c:if test="${map410.I2 eq 'Y'}">Y</c:if><c:if test="${map410.I2 eq 'N'}">N</c:if>" 
										name="OPT410_2" id="OPT410_2" <c:if test="${map410.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
								<spring:message code="env.option.subtitle.OPT410.3"/>
							</span>
							<span class="range1">
								<input type="checkbox" value="<c:if test="${map410.I3 eq 'Y'}">Y</c:if><c:if test="${map410.I3 eq 'N'}">N</c:if>" 
										name="OPT410_3" id="OPT410_3" <c:if test="${map410.I3 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
								<spring:message code="env.option.subtitle.OPT410.4"/>
							</span>
							<span class="range1">
								<input type="checkbox" value="<c:if test="${map410.I4 eq 'Y'}">Y</c:if><c:if test="${map410.I4 eq 'N'}">N</c:if>" 
										name="OPT410_4" id="OPT410_4" <c:if test="${map410.I4 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
								<spring:message code="env.option.subtitle.OPT410.5"/>
							</span>
							<input type="hidden" name="OPT410optionValue" id="OPT410optionValue" value="" />						
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.etc.02"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 수신자 표시 방법 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.60"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="OPT400-567" value="1" <c:if test="${map400.OPT405.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT406.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT400-567" value="2" <c:if test="${map400.OPT407.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT406.2"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT400-567" value="3" <c:if test="${map400.OPT406.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT406.3"/>
							</span>
							<input type="hidden" id="OPT405" name="OPT405" value="" />
							<input type="hidden" id="OPT406" name="OPT406" value="" />
							<input type="hidden" id="OPT407" name="OPT407" value="" />
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.etc.03"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 부재처리 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt313"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="OPT313" id="OPT313" value="1" <c:if test="${map300.OPT313.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt313.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT313" id="OPT313" value="2" <c:if test="${map300.OPT313.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt313.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.20"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
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
							<span class="range1">
							<input type="radio" name="${map300.OPT331.optionId}" id="${map300.OPT331.optionId}" value="1" <c:if test="${map300.OPT331.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.1"/>
							</span>
							<span class="range1">
							<input type="radio" name="${map300.OPT331.optionId}" id="${map300.OPT331.optionId}" value="2" <c:if test="${map300.OPT331.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.2"/>
							</span>
							<span class="range1">
							<input type="radio" name="${map300.OPT331.optionId}" id="${map300.OPT331.optionId}" value="3" <c:if test="${map300.OPT331.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.3"/>
							</span>
							<span class="range1">
							<input type="radio" name="${map300.OPT331.optionId}" id="${map300.OPT331.optionId}" value="4" <c:if test="${map300.OPT331.useYn eq '4'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt331.4"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.26"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 페이지 목록 건수 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.page.list"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">	
							<!-- 한 페이지당 목록건수 -->				
							<span class="range220">
							<input type="checkbox" value="Y" checked disabled />
							<spring:message code="env.option.subtitle.OPT424"/>&nbsp;
							<input style="width:50" type="text" value="${map400.OPT424.optionValue}" name="${map400.OPT424.optionId}" id="${map400.OPT424.optionId}"  class="input" />	
							</span>								
							<!-- 한 페이지당 최대 목록건수 -->				
							<span class="range220">
							<input type="checkbox" value="Y" checked disabled />
							<spring:message code="env.option.subtitle.OPT426"/>&nbsp;
							<input style="width:50" type="text" value="${map400.OPT426.optionValue}" name="${map400.OPT426.optionId}" id="${map400.OPT426.optionId}"  class="input" />
							</span>		
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.page.list"/></td></tr>
				</acube:tableFrame> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 직위표시 우선순위 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt340"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
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
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.position.priority"/></td></tr>	
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.62"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">	
							<!-- 편철 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT423.optionId}" id="${map400.OPT423.optionId}" <c:if test="${map400.OPT423.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT423"/>	
							</span>								
							<!-- 문서분류체계 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT422.optionId}" id="${map400.OPT422.optionId}" <c:if test="${map400.OPT422.useYn eq 'Y'}">checked</c:if> onClick="checkChainForClassNum(this);" />
							<spring:message code="env.option.subtitle.OPT422"/>	
							</span>		
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.OPT422"/></td></tr>
				</acube:tableFrame> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 문서번호 생성 규칙 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.docnumrule.section.name"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<table cellpadding="0" cellpadding="0">
								<tr>
									<td style="font-family:Dotum,Gulim,Arial;font-size:9pt;font-weight:bold;padding-top:3px;padding-right:10px;">
										<spring:message code="env.option.subtitle.docnumrule.section.item"/>
									</td>
									<td class="ltb_center">
										<table id="tblItem" cellpadding="2" cellspacing="1" width="400" bgcolor="#aaaaaa">	
											<tbody>				
												<tr>
													<td id="DEPTNAME" ondblclick='onLineDblClickForInsert($("#DEPTNAME"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.document.number.rule.deptname"/></td>
													<td id="DEPTACRO" ondblclick='onLineDblClickForInsert($("#DEPTACRO"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.document.number.rule.deptacro"/></td>
													<% if ( periodType.equals("Y") ) { %>
													<td id="SESSION" ondblclick='onLineDblClickForInsert($("#SESSION"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.document.number.rule.year"/></td>
													<% } %>
													<% if ( periodType.equals("P") ) { %>
													<td id="SESSION" ondblclick='onLineDblClickForInsert($("#SESSION"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.document.number.rule.session"/></td>
													<% } %>
													<td id="CLASSNUM" ondblclick='onLineDblClickForInsert($("#CLASSNUM"));' style="background-color:#ffffff;" width="80" class="ltb_center"><spring:message code="env.document.number.rule.classnum"/></td>
													<td id="HYPHEN" ondblclick='onLineDblClickForInsert($("#HYPHEN"));' style="background-color:#ffffff;" width="80" class="ltb_center">-</td>
												</tr>
											</tbody>	
										</table>
									</td>
								</tr>
								<tr>
									<td style="font-family:Dotum,Gulim,Arial;font-size:9pt;font-weight:bold;padding-top:3px;padding-right:10px;">
										<spring:message code="env.option.subtitle.docnumrule.section.result"/>
									</td>
									<td class="ltb_left">
										<table id="tblRule" cellpadding="2" cellspacing="1" bgcolor="#aaaaaa">	
											<tbody>				
												<tr id="ruleLine">
												</tr>
											</tbody>	
										</table>
									</td>
									<td style="padding-left:10px;padding-top:3px;">
										<img src="<%=webUri%>/app/ref/image/bu_left.gif" onclick="javascript:onRuleMoveLeft();return(false);">&nbsp;
										<img src="<%=webUri%>/app/ref/image/bu_right.gif" onclick="javascript:onRuleMoveRight();return(false);">
									</td>
								</tr>
							</table>
						</td>
					</tr>			
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.docnumrule.01"/></td></tr>		
				</acube:tableFrame>
			</td>
		</tr>	
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 결재진행문서 회수 기능 설정 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.OPT421.section.name"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">							
							<span class="range">
							<input type="radio" name="OPT421" id="OPT421" value="1" <c:if test="${map400.OPT421.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT421.section.01"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT421" id="OPT421" value="2" <c:if test="${map400.OPT421.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT421.section.02"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT421" id="OPT421" value="0" <c:if test="${map400.OPT421.useYn eq '0'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT421.section.00"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.OPT421"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
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
							<!-- 관련문서 사용 -->
							<span class="range200">
							<input type="checkbox" value="Y" name="${map300.OPT321.optionId}" id="${map300.OPT321.optionId}" <c:if test="${map300.OPT321.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt321"/>
							</span>							
							<!-- 결재 처리 후 문서 자동닫기 -->						
							<span class="range200">
							<input type="checkbox" value="Y" name="${map300.OPT357.optionId}" id="${map300.OPT357.optionId}" <c:if test="${map300.OPT357.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt357"/>	
							</span>
							<!-- 비전자문서 첨부 필수 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map300.OPT359.optionId}" id="${map300.OPT359.optionId}" <c:if test="${map300.OPT359.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt359"/>	
							</span>
							<!-- 문서등록취소기능 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map300.OPT363.optionId}" id="${map300.OPT363.optionId}" <c:if test="${map300.OPT363.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt363"/>	
							</span>
							<!-- 접수절차 간소화 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map300.OPT371.optionId}" id="${map300.OPT371.optionId}" <c:if test="${map300.OPT371.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt371"/>	
							</span>
							<!-- 비공개 사유입력 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT404.optionId}" id="${map400.OPT404.optionId}" <c:if test="${map400.OPT404.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT404"/>	
							</span>		
							<!-- 반려된 문서를 재기안시 등록대장에 등록 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT412.optionId}" id="${map400.OPT412.optionId}" <c:if test="${map400.OPT412.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT412"/>	
							</span>		
							<!-- 서명이미지 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT419.optionId}" id="${map400.OPT419.optionId}" <c:if test="${map400.OPT419.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT419"/>	
							</span>		
							<!-- 비전자문서 접수 프로세스 사용 -->				
							<span class="range200">
							<input type="checkbox" value="Y" name="${map400.OPT420.optionId}" id="${map400.OPT420.optionId}" <c:if test="${map400.OPT420.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.OPT420"/>	
							</span>		
							<!-- 첨부사이즈 제한 -->
							<span class="range200">	
							<input type="checkbox" value="Y" name="${map300.OPT330.optionId}" id="${map300.OPT330.optionId}" <c:if test="${map300.OPT330.useYn eq 'Y'}">checked</c:if> onClick="checkChain(this);" />
							<spring:message code="env.option.subtitle.opt330"/>&nbsp;
							<input type="text" class="input_read" style="width:40px;"  value="${map300.OPT330.optionValue}" name="OPT330optionValue" id="OPT330optionValue" style="ime-mode: disabled;" onkeypress="return onlyNumber(event, false);" onkeyup="checkInputMaxLength(this,'',3)"/>&nbsp;MB
							</span>	
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.21"/></td></tr>
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
		<input type="hidden" id="posPriority" name="posPriority" value="" />
		<input type="hidden" id="saveRuleLine" name="saveRuleLine" value="" />
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>