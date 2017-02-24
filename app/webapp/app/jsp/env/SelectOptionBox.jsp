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
	List<OptionVO> boxList = (List<OptionVO>)request.getAttribute("VOLists");
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
}

	function updateOptionYn() {
		var temp;
		var boxSize = <%=boxList.size()%>;
		for (i=1; i<=boxSize; i++) {
			if (i<10) {
				temp = "OPT10"+i;
			} else {
				temp = "OPT1"+i;
			}
			if ($('input[id='+temp+']').attr('checked') && $.trim($('#'+temp+'Value').val()) == "") {
				alert('<spring:message code="env.option.msg.validate.optg100"/>');
				$('#'+temp+'Value').focus();
				return;
			}
		}
		if ($('input[name=OPT339]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt339"/>');
			return;
		}
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionBox.do", $("#optBoxForm").serialize(), function(data){
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

	function chainCheck() {
	}

	
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.012"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optBoxForm" id="optBoxForm">
		<c:set var="map" value="${VOMap}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.menu.sub.02"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 함 목록 -->							
							<c:forEach var="vo" items="${VOLists}">
								<span class="range_box" id="${vo.optionId}">
								<input type="checkbox" value="Y" name="${vo.optionId}" id="${vo.optionId}" <c:if test="${vo.useYn eq 'Y'}">checked</c:if> onClick="boxCheck(this, '${vo.description}')" />
								<span style="width:80px;">
								<font color="#3f3f3f"><spring:message code="tgw_app_option.${vo.optionId}" /></font>
								</span>
								<input type="text" value="${vo.optionValue}" name="${vo.optionId}Value" id="${vo.optionId}Value" onclick="showOptionResource(this, '${vo.optionId}', '${vo.resourceId}', '${vo.optionValue}')" <c:if test="${vo.useYn eq 'N'}">disabled</c:if> style="width:100px;" class="input" />
								
								</span>
							</c:forEach>
							<input type="hidden" name="voSize" id="voSize" value="${fn:length(VOLists)}" />													
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.08"/></td></tr>
				</acube:tableFrame> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 임시저장함 상신후 삭제 -->
							<span class="range_box">
							<input type="checkbox" value="Y" name="${map.OPT302.optionId}" id="${map.OPT302.optionId}" <c:if test="${map.OPT302.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt302"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.09"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-- 임원문서함 열람범위 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt339"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="radio" name="OPT339" id="OPT339" value="1" <c:if test="${map.OPT339.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt339.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT339" id="OPT339" value="2" <c:if test="${map.OPT339.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt339.2"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT339" id="OPT339" value="3" <c:if test="${map.OPT339.useYn eq '3'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt339.3"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.05"/></td></tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		
		
		<!-- 공람문서함 기본검색조건 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt365"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="radio" name="OPT365" id="OPT365" value="1" <c:if test="${map.OPT365.useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt365.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT365" id="OPT365" value="2" <c:if test="${map.OPT365.useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt365.2"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.36"/></td></tr>
				</acube:tableFrame>				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		
		<!-- 결재 경로상의 사용자 중 결재 전 사용자가 결재진행함에서 결재문서를 조회 여부 -->
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt383"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<span class="range">					
							<input type="radio" name="OPT383" id="OPT383" value="Y" <c:if test="${map.OPT383.useYn eq 'Y'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt383.Y"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT383" id="OPT383" value="N" <c:if test="${map.OPT383.useYn eq 'N'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt383.N"/>
							</span>
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;"></td></tr>
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