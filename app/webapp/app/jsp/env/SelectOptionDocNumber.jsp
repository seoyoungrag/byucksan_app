<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>

<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<%
/** 
 *  Class Name  : EnvOptionDocNumber.jsp 
 *  Description : 관리자 환경설정 - 채번/가지번호
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.5 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 5 
 *  @version 1.0 
 */ 
%>
<%
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String startDate = DateUtil.getCurrentDate(dateFormat);
	String startDateId = DateUtil.getCurrentDate(dateFormat);
	
	HashMap<String, String> map = new HashMap<String, String>();
	map = (HashMap<String, String>)request.getAttribute("VOMap");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<style type="text/css">
	.range {display:inline-block;width:170px; !important;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		var date = "<%= map.get("OPT318_value")%>";
		var displayDate = "";
		if (date != null && date != "" && date != "9999/01/01") {
			displayDate = date;
		}
		$('#startDate').val(displayDate);
		$('#startDateId').val(displayDate);
		checkChain($('#OPT310')[0]);
		sessionCheck($('#OPT318')[0]);
	}
	function checkChain(obj) {
		var radios = $('input:radio[name="OPT311"]');		
		if (obj.checked == false) {
			radios.attr('disabled', true);
		} else {
			radios.attr('disabled', false);
		}
	}

	function sessionCheck(obj) {
		var opt318 = $('input:radio[name="OPT318"]:checked').val();	
		if (opt318 == "1") {
			$('#session').hide();
		} else if (opt318 == "2") {
			$('#session').show();
		}
	}
	
	function updateOptionYn() {
		var opt318 = $('#startDate').val();
		var patern = /-/g;		
		if ($('input[name=OPT318]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt318.0"/>');
			return;
		}
		if ($('input:radio[name="OPT318"]:checked').val() == 1) {
			$('#startDateId').val("9999/01/01");
		} else {
			if ($('#startDateId').val()=="") {
				alert('<spring:message code="env.option.msg.validate.opt318"/>');
				return;
			}
			$('#startDateId').val(opt318.replace(patern, ""));
		}
		if ( $('input[name=OPT310]:checked').val() == "Y" && $('input[name=OPT311]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt311"/>');
			return;
		}
		if (confirm('<spring:message code="env.option.msg.confirm.save"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionDocNumber.do", $("#optDocNumberForm").serialize(), function(data){
					alert(data.msg);
					document.location.href = data.url;
			}, 'json');
	    }
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.05"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optDocNumberForm" id="optDocNumberForm">
		<c:set var="map" value="${VOMap}" />		
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt318"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 채번시작일자 -->
							<span class="range">
							<input type="radio" name="OPT318" id="OPT318" value="1" onClick="sessionCheck(this)" <c:if test="${map.OPT318_useYn eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt318.1"/>
							</span>							
							<input type="radio" name="OPT318" id="OPT318" value="2" onClick="sessionCheck(this)" <c:if test="${map.OPT318_useYn eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt318.2"/>&nbsp;					
							<span id="session">	
							( <input type="text" class="input_read" name="startDate" id="startDate" readonly size="10" value="">
							<spring:message code="env.option.subtitle.opt318.0"/>
						    <img id="calendarBTN1" name="calendarBTN1" 						
						        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 						
						        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"						
						        onclick="cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 						
						        '<%= dateFormat %>');">	)					
						    <input type="hidden" name="startDateId" id="startDateId" value="" />
						    <c:if test="${map.OPT318_useYn eq '2'}">(${map.term}<spring:message code="env.option.subtitle.opt318.2.0"/>)</c:if>
						    </span>			
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.17"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>		
		<tr>
			<acube:space between="title_button" />
		</tr>
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
							<input type="checkbox" value="Y" name="OPT310" id="OPT310" <c:if test="${map.OPT310 eq 'Y'}">checked</c:if> onClick="checkChain(this);"/>
							<spring:message code="env.option.subtitle.opt310"/><br />
							<input type="radio" name="OPT311" id="OPT311" value="1" <c:if test="${map.OPT311 eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt311.1"/>
							</span>
							<input type="radio" name="OPT311" id="OPT311" value="2" <c:if test="${map.OPT311 eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt311.2"/>					
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.18"/></td></tr>
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