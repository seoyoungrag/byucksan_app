<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.vo.OptionVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionRegister.jsp 
 *  Description : 관리자 환경설정 - 대장구분 
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
	List<OptionVO> registeList = new ArrayList<OptionVO>();
	registeList = (List<OptionVO>)request.getAttribute("VOLists");
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
	.range {display:inline-block;width:265px;height:25px !important;}
</style>
<script type="text/javascript">
	function updateOptionYn() {
		var temp;
		var registeSize = <%=registeList.size()%>;
		for (i=1; i<=registeSize; i++) {
			if (i<10) {
				temp = "OPT20"+i;
			} else {
				temp = "OPT2"+i;
			}
			if ($('input[id='+temp+']').attr('checked') && $.trim($('#'+temp+"Value").val()) == "") {
				alert('<spring:message code="env.option.msg.validate.optg200"/>');
				$('#'+temp+'Value').focus();
				return;
			}
		}
		
		$('#OPT382optionValue').val("I1:"+$('#OPT382_1').val()+"^I2:"+$('#OPT382_2').val());
		
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionRegister.do", $("#optRegisterForm").serialize(), function(data){
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

	function setBoxName(obj, nm) {
		if (obj.checked == true && $.trim($('#'+obj.name+'Value').val()) == "") {
			$('#'+obj.name+'Value').val(nm);
		}
	}

	function boxCheck(obj, nm) {
		readOnlyCheck(obj);
		setBoxName(obj, nm);
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
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.013"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optRegisterForm" id="optRegisterForm">
		<c:set var="map" value="${VOMap}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.03"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 대장 목록 -->
							<c:forEach var="vo" items="${VOLists}">
								<span class="range">
								<input type="checkbox" value="Y" name="${vo.optionId}" id="${vo.optionId}" <c:if test="${vo.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${vo.description}')" />
								<span style="width:92px;">
								<font color="#3f3f3f"><spring:message code="env.register.name.${vo.optionId}"/></font>
								</span>
								<input type="text" value="${vo.optionValue}" name="${vo.optionId}Value" id="${vo.optionId}Value" onclick="showOptionResource(this, '${vo.optionId}', '${vo.resourceId}', '${vo.optionValue}')" <c:if test="${vo.useYn eq 'N'}">disabled</c:if> style="width:120px;" class="input" />
								
								</span>
							</c:forEach>
							<input type="hidden" name="voSize" id="voSize" value="${fn:length(VOLists)}" />
														
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.11"/></td></tr>
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
							<!-- 연도회기별 문서목록조회 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT338.optionId}" id="${map.OPT338.optionId}" <c:if test="${map.OPT338.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt338"/>	
							</span>
							<!-- 등록대장 생산문서 간편조회 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT368.optionId}" id="${map.OPT368.optionId}" <c:if test="${map.OPT368.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt368"/>	
							</span>
							<!-- 등록대장 생산문서 간편조회 사용 -->
							<span class="range">
							<input type="checkbox" value="Y" name="${map.OPT369.optionId}" id="${map.OPT369.optionId}" <c:if test="${map.OPT369.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt369"/>	
							</span>								
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.12"/></td></tr>
				</acube:tableFrame> 				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-- 문서등록대장 열람 옵션 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt382"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 하위부서 문서등록대장 문서 열람 -->
							<span class="range">
							<input type="checkbox" name="OPT382_1" id="OPT382_1" value="<c:if test="${map382.I1 eq 'Y'}">Y</c:if><c:if test="${map382.I1 eq 'N'}">N</c:if>" <c:if test="${map382.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt382.1"/>
							</span>
							<!-- 부서 대 부서 문서등록대장 문서 열람 -->
							<span class="range">
							<input type="checkbox" name="OPT382_2" id="OPT382_2" value="<c:if test="${map382.I2 eq 'Y'}">Y</c:if><c:if test="${map382.I2 eq 'N'}">N</c:if>" <c:if test="${map382.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt382.2"/>
							</span>
							<input type="hidden" name="OPT382optionValue" id="OPT382optionValue" value="" />
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.46"/></td></tr>
				</acube:tableFrame>
			</td>
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