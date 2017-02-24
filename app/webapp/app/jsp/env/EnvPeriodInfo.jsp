<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>

<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
/** 
 *  Class Name  : EnvDeptInfo.jsp 
 *  Description : 회기 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.08.02 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  skh0204
 *  @since 2011. 8. 2 
 *  @version 1.0 
 */ 
%>
<% 
	String imagePath = webUri + "/app/ref/image";
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String compId = (String)session.getAttribute("COMP_ID");
	String periodType = AppConfig.getProperty("periodType", "Y", "etc");
	String periodMenu = messageSource.getMessage("env.option.menu.sub.46."+periodType, null, langType); // 회기/연도 메뉴
	String buttonAdd = messageSource.getMessage("env.period.button.add."+periodType, null, langType); // 회기추가
	String formPeriod = messageSource.getMessage("env.period.form.01."+periodType, null, langType); // 회기/연도
	String buttonModify = messageSource.getMessage("env.option.button.modify", null, langType); // 수정
	
	String msgConfirmDelete = messageSource.getMessage("env.period.msg.confirm.delete."+periodType, null, langType);
	String msgConfirmModifyPre = messageSource.getMessage("env.period.msg.confirm.modify.pre."+periodType, null, langType);
	String msgConfirmModifyNext = messageSource.getMessage("env.period.msg.confirm.modify.next."+periodType, null, langType);
	String msgSuccessModify = messageSource.getMessage("env.option.msg.success.modifyPeriod."+periodType, null, langType);
	String msgAddPeriod = messageSource.getMessage("env.option.msg.success.addPeriod."+periodType, null, langType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.menu.sub.24" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#656565;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var currentPeriod = '${currentPeriod}';
var latestPeriod = ${latestPeriod.periodId};
var confirmMsg = "<spring:message code='env.option.msg.confirm.modify'/>";

function initialize() {
}

function insertPeriod() {
	var result = null;
	$.ajaxSetup({async:false});
	$.getJSON("<%=webUri%>/app/env/admin/insertPeriod.do", function(data){
		result = data;
	});
	if (result == "success") {
		alert("<%=msgAddPeriod%>");
		self.location.href = "<%=webUri%>/app/env/admin/selectPeriodInfo.do";
	} else {
		alert("<spring:message code='env.option.msg.error'/>");
	}
}

function deletePeriod(periodId) {
	var result = null;	

	if (confirm(periodId+"<%=msgConfirmDelete%>")) {
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/admin/deletePeriod.do?periodId="+periodId, function(data){
			result = data;
		});
		if (result == "success") {
			alert("<spring:message code='env.option.msg.success.delete'/>");
			self.location.href = "<%=webUri%>/app/env/admin/selectPeriodInfo.do";
		} else {
			alert("<spring:message code='env.option.msg.error'/>");
		}
	}
}

function checkEndDate(periodId) {
	var nextPeriod = (Number(periodId)+1).toString();
	if ($('#startDate'+nextPeriod).val() != null) {
		var endDate = $('#endDate'+periodId).val();
		var nextStartDate = getPreNextDate(endDate, "/", "1", 1);		
		$('#startDate'+nextPeriod).val(nextStartDate);	
		confirmMsg = "<%=msgConfirmModifyNext%>";	
	}	
}

function checkStartDate(periodId) {
	var prePeriod = (Number(periodId)-1).toString();
	var startDate = $('#startDate'+periodId).val();
	var preEndDate = getPreNextDate(startDate, "/", "0", 1);		
	$('#endDate'+prePeriod).val(preEndDate);
	confirmMsg = "<%=msgConfirmModifyPre%>";
}

function checkValidation(periodId) {

	var prePeriod = (Number(periodId)-1).toString();
	var nextPeriod = (Number(periodId)+1).toString();
	
	var startDate = $('#startDate'+periodId).val();
	var endDate = $('#endDate'+periodId).val();
	var nextStartDate = $('#startDate'+nextPeriod).val();
	var preEndDate = $('#endDate'+prePeriod).val();
	
	if (periodId == currentPeriod) {
		if (compareDate(nextStartDate, endDate) != 1) {
			checkEndDate(periodId);
			updatePeriod(nextPeriod);
		}
	} else if (periodId == latestPeriod) {
		if (compareDate(startDate, preEndDate) != 1) {
			checkStartDate(periodId);
			updatePeriod(prePeriod);
		}
	} else {
		if (compareDate(startDate, preEndDate) != 1) {
			checkStartDate(periodId);
			updatePeriod(prePeriod);
		}
		if (compareDate(nextStartDate, endDate) != 1) {
			checkEndDate(periodId);
			updatePeriod(nextPeriod);
		}
	}
}

function compareDate(start, end) {
	var startDate = strToDate(start, "/");
	var endDate = strToDate(end, "/");
	var defferentDate = (startDate-endDate)/24/60/60/1000;
	return defferentDate;
}

function strToDate(str, delim) {
	var ds = str.split(delim);
	var dt = new Date(ds[0], ds[1]-1, ds[2]);
	return dt;
}

function dateToStr(date, delim) {
	var year = date.getFullYear().toString();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	month = ( month<10 ? "0" : "" ) + month;
	day = ( day<10 ? "0" : "" ) + day;
	return year + delim + month + delim + day;
}

function getPreNextDate(date, delim, type, n) { // 0:pre, 1:next
	var dt = strToDate(date, delim);
	var temp;
	if (type=="0") {
		temp = dt.getTime() - n*(24*60*60*1000);
	} else if (type=="1") {
		temp = dt.getTime() + n*(24*60*60*1000);
	}
	var sumDt = new Date(temp);	
	return dateToStr(sumDt, delim);
}


function updatePeriod(periodId) {
	//alert("${currentPeriod}");
	
	var result = null;
	var checkVal = bCheckFromToInputValue($('#startDate'+periodId), $('#endDate'+periodId), "<spring:message code='list.list.msg.searchDateError'/>");

	if (checkVal) {
		
		if (confirm(confirmMsg)) {
			$.ajaxSetup({async:false});
			$.getJSON("<%=webUri%>/app/env/admin/updatePeriod.do?periodId="+periodId, $('#periodInfoForm').serialize(), function(data){
				result = data;
			});
			if (result == "success") {
				alert("<%=msgSuccessModify%>");
				checkValidation(periodId);
				self.location.href = "<%=webUri%>/app/env/admin/selectPeriodInfo.do";
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}
	}
}

</script>
</head> 
<body>
<acube:outerFrame>

	<acube:titleBar><%=periodMenu%></acube:titleBar>
	<acube:space between="button_content" table="y"/>
	<acube:buttonGroup>
		<acube:menuButton onclick="insertPeriod();" value="<%=buttonAdd%>" />
	</acube:buttonGroup>
	<acube:space between="button_content"/>
	<acube:space between="button_content" table="y"/>
	
	<acube:tableFrame class="table_grow">
	<form:form modelAttribute="" method="post" name="periodInfoForm" id="periodInfoForm">	
		<tr bgcolor="#ffffff">
			<td width="12%" height="25" class="ltb_head">
				<nobr><%=formPeriod%></nobr><!-- 회기/연도 -->
			</td>
			<td width="18%" class="ltb_head">
				<nobr><spring:message code="env.period.form.02"/></nobr><!-- 시작일자 -->
			</td>
			<td width="18%" class="ltb_head">
				<nobr><spring:message code="env.period.form.03"/></nobr><!-- 종료일자 -->
			</td>
			<td width="18%" class="ltb_head">
				<nobr><spring:message code="env.period.form.04"/></nobr><!-- 등록일자 -->
			</td>
			<td width="18%" class="ltb_head">
				<nobr><spring:message code="env.period.form.05"/></nobr><!-- 최종수정일자 -->
			</td>
			<td width="8%" class="ltb_head">
				<nobr><spring:message code="env.option.button.modify"/></nobr><!-- 수정 -->
			</td>
			<td width="8%" class="ltb_head">
				<nobr><spring:message code="env.option.button.delete"/></nobr><!-- 삭제 -->
			</td>
		</tr>
		<c:forEach var="vo" items="${voList}">
		<tr bgcolor="#ffffff">
			<td height="25" class="ltb_center" <c:if test="${vo.periodId eq currentPeriod}">style="color:#3333ff"</c:if>>${vo.periodId}</td>
			<td class="ltb_center" <c:if test="${vo.periodId eq currentPeriod}">style="color:#3333ff"</c:if>>
				<c:if test="${vo.periodId < currentPeriod}">
					${vo.startDate}
				</c:if>				
				<c:if test="${vo.periodId >= currentPeriod}">
					<input type="text" id="startDate${vo.periodId}" name="startDate${vo.periodId}" value="${vo.startDate}" class="input" style="width:70px;<c:if test="${vo.periodId eq currentPeriod}">color:#3333ff;</c:if>" readOnly />
					<c:if test="${vo.periodId != currentPeriod}">
					<input type="hidden" name="startDateId${vo.periodId}" id="startDateId${vo.periodId}" value="" />
					<img id="calendarBTN${vo.periodId}" name="calendarBTN${vo.periodId}" 						
						        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 						
						        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"						
						        onclick="cal.select(event, document.getElementById('startDateId${vo.periodId}'), document.getElementById('startDate${vo.periodId}'), 'calendarBTN${vo.periodId}', 						
						        '<%= dateFormat %>');">
					</c:if>
				</c:if>				
			</td>
			<td class="ltb_center" <c:if test="${vo.periodId eq currentPeriod}">style="color:#3333ff"</c:if>>
				<nobr>
				<c:if test="${vo.periodId < currentPeriod}">
					${vo.endDate}
				</c:if>
				<c:if test="${vo.periodId >= currentPeriod}">
					<input type="text" id="endDate${vo.periodId}" name="endDate${vo.periodId}" value="${vo.endDate}" class="input" style="width:70px;<c:if test="${vo.periodId eq currentPeriod}">color:#3333ff;</c:if>" readOnly />
					<input type="hidden" name="endDateId${vo.periodId}" id="endDateId${vo.periodId}" value="" />
					<img id="calendarBTN${vo.periodId}" name="calendarBTN${vo.periodId}" 						
						        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 						
						        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"						
						        onclick="cal.select(event, document.getElementById('endDateId${vo.periodId}'), document.getElementById('endDate${vo.periodId}'), 'calendarBTN${vo.periodId}', 						
						        '<%= dateFormat %>');">
				</c:if>
				</nobr>			
			<td class="ltb_center" <c:if test="${vo.periodId eq currentPeriod}">style="color:#3333ff"</c:if>>${vo.registDate}</td>
			<td class="ltb_center" <c:if test="${vo.periodId eq currentPeriod}">style="color:#3333ff"</c:if>>${vo.lastUpdateDate}</td>
			<td class="ltb_center">
				<c:if test="${vo.periodId >= currentPeriod && currentPeriod != ''}">
						<acube:button type="4" onclick="updatePeriod('${vo.periodId}');" value="<%=buttonModify%>" />
				</c:if>
			</td>
			<td valign="bottom" class="ltb_center">
				<c:if test="${vo.periodId eq latestPeriod.periodId}">
					<a href="#" onClick="deletePeriod('${vo.periodId}');"><img src="<%=imagePath%>/button_x.gif" border="0"></a>	
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</form:form>
	</acube:tableFrame>
	
</acube:outerFrame>
</body>	
</html>