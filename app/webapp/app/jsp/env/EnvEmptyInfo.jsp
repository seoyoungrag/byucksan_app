<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.EmptyInfoVO"%>
<%//@ page import="com.sds.acube.app.idir.org.user.Substitute"%>
<%//@ page import="com.sds.acube.app.idir.org.user.UserStatus"%>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
/** 
 *  Class Name  : EnvEmptyInfo.jsp 
 *  Description : 환경설정 - 부재정보 관리 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.08 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 8 
 *  @version 1.0 
 */ 
%>
<%
	String compId = (String)session.getAttribute("COMP_ID");
	String userName = (String)session.getAttribute("USER_NAME");
	String userId = (String)session.getAttribute("USER_ID");
	String deptName = (String)session.getAttribute("DEPT_NAME");
	String deptId = (String)session.getAttribute("DEPT_ID");
	String posName = (String)session.getAttribute("DISPLAY_POSITION");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String mode = (String) request.getAttribute("mode");

		
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	
	String proxyUserId = "";
	String proxyUserName = "";
	String proxyDeptName = "";
	String proxyPosName = "";
	
	if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
	    userName = "";
		userId = "";
		deptName = "";
		posName = "";
	}
	
	String buttonSave = messageSource.getMessage("env.empty.button.save" , null, langType); 
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType); 
	String buttonWithdrow = messageSource.getMessage("env.empty.button.delete" , null, langType); 
	String buttonEmptySelect = messageSource.getMessage("env.empty.button.emptyselect" , null, langType); 
	String buttonProxySelect = messageSource.getMessage("env.empty.button.proxyselect" , null, langType); 	
	String msgEmpty = messageSource.getMessage("env.empty.text.true" , null, langType); 
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String dateFormat2 = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
	String currentDate = DateUtil.getCurrentDate(dateFormat2);
	String startDate = DateUtil.getCurrentDate(dateFormat);
	String startDateId = DateUtil.getCurrentDate("yyyyMMdd");
	String endDate = DateUtil.getCurrentDate(dateFormat);
	String endDateId = DateUtil.getCurrentDate("yyyyMMdd");
	String emptyReason = "";
	String startHour = "00";
	String endHour = "23";
	String strEmptyYn = "";
	
	//UserStatus userStatus = null;
	//Substitute substitute = null;
	EmptyInfoVO emptyInfo = null;
	if ("charge".equals(mode)) {
		//userStatus = (UserStatus)request.getAttribute("objUserStatus");
		//substitute = (Substitute)request.getAttribute("objSubstitute");
	    emptyInfo = (EmptyInfoVO)request.getAttribute("emptyInfo");
	}
	if (emptyInfo != null && emptyInfo.getIsEmpty()) {
	    strEmptyYn = msgEmpty;
	    startDate = DateUtil.getFormattedDate(emptyInfo.getEmptyStartDate(), dateFormat);
	    startDateId = DateUtil.getFormattedDate(emptyInfo.getEmptyStartDate(), "yyyyMMdd");
	    endDate = DateUtil.getFormattedDate(emptyInfo.getEmptyEndDate(), dateFormat);
	    endDateId = DateUtil.getFormattedDate(emptyInfo.getEmptyEndDate(), "yyyyMMdd");
	    emptyReason = emptyInfo.getEmptyReason();
	    startHour = DateUtil.getFormattedDate(emptyInfo.getEmptyStartDate(), "HH");
		endHour = DateUtil.getFormattedDate(emptyInfo.getEmptyEndDate(), "HH");
	}
	if (emptyInfo != null && emptyInfo.getIsSubstitute()) {
	    proxyUserId = emptyInfo.getSubstituteId();
	    proxyUserName = emptyInfo.getSubstituteName();
	    proxyDeptName = emptyInfo.getSubstituteDeptName();
	    proxyPosName = emptyInfo.getSubstituteDisplayPosition();
	}
	
	pageContext.setAttribute("reason",emptyReason);
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	$('INPUT').bind('keydown', function(event){
		var keyCode = event.which;
		if (keyCode == 8) {
			return false;
		}
	});
} 

	var callType;
	var cnt = ${fn:length(emptyReason)};
	var opt313 = "${opt313}";		
	
	function setCallType(type) {
		callType = type;
	}
	function getCallType() {
		return callType;
	}
	
	function setUserInfo(obj) {
		if (getCallType() == "e") {	
			setEmptyInfo(obj);
		} else if (getCallType() == "p") {
			setProxyInfo(obj);
		}
	}
	
	function setEmptyInfo(obj) {
		$('#emptyUserName').val(obj.userName);
		$('#emptyUserId').val(obj.userId);
		$('#emptyDeptId').val(obj.deptId);
		$('#emptyDeptName').val(obj.deptName);
		$('#emptyPosName').val(obj.positionName);	

		var objEmpty = null;
		var objProxy = null;
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/selectEnvEmptyStatus.do?userId="+obj.userId, function(data){
			objEmpty = data;
		});
		if (objEmpty != null && objEmpty.isEmpty) {

			setEmpty(objEmpty);
			$.getJSON("<%=webUri%>/app/env/selectEnvProxyStatus.do?userId="+obj.userId, function(data){
				objProxy = data;
			});	

			if (objProxy != null) {
				setSubstituteInfo(objProxy);
			}			
		} else {
			$('#spanEmptyStatus').text("");
			emptyReset();
			proxyReset();
		}
	}

	function setEmpty(objEmpty) {
		var startDate = (objEmpty.emptyStartDate).substring(0,10);
		var endDate = (objEmpty.emptyEndDate).substring(0,10);
		var startAMPM = (objEmpty.emptyStartDate).substring(11,19);
		var endAMPM = (objEmpty.emptyEndDate).substring(11,19);	

		$('#spanEmptyStatus').text("<%=msgEmpty%>");
		$('#startDate').val(startDate.replace(/-/g,"/"));
		$('#startDateId').val(startDate.replace(/-/g,""));
		$('#endDate').val(endDate.replace(/-/g,"/"));
		$('#endDateId').val(endDate.replace(/-/g,""));
		$('select.#userStatus').val(objEmpty.emptyReason);
		$('select.#selAMPM1').val(startAMPM);
		$('select.#selAMPM2').val(endAMPM);	
	}
	
	function setProxyInfo(obj) {
		if (obj.userId == $('#emptyUserId').val()) {
			alert("<spring:message code='env.empty.msg.validate.duplication'/>");
		} else {
			$('#proxyUserName').val(obj.userName);
			$('#proxyUserId').val(obj.userId);
			$('#proxyDeptName').val(obj.deptName);
			$('#proxyPosName').val(obj.positionName);
		}	
	}

	function setSubstituteInfo(obj) {
		$('#proxyUserName').val(obj.substituteName);
		$('#proxyUserId').val(obj.substituteId);
		$('#proxyDeptName').val(obj.substituteDeptName);
		$('#proxyPosName').val(obj.substituteDisplayPosition);
	}

	<% if (roleCodes.indexOf(roleId11)>=0 && "charge".equals(mode)) { %>
	
			function popDeptMember(type) {
				setCallType(type);
				var top = (screen.availHeight - 320) / 2;
				var left = (screen.availWidth - 400) / 2;
				var url = "<%=webUri%>/app/env/envDeptMember.do";
				var option = "width=400,height=330,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
				recvLine = window.open(url, "envRecv", option);
			}
	
	<% } %>

	function popOrgTree(type) {
		setCallType(type);
		if (type == "p" && $('#emptyUserName').val() == "") {
			alert("<spring:message code='env.option.msg.validate.noempty'/>");
			return;
		}
		var url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3";
		if (type == "p") {
			url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3&deptId="+$('#emptyDeptId').val();
		}
		if (type == "p" && opt313 == "2") {
			alert("<spring:message code='env.empty.msg.validate.setEmpty'/>");
		} else {
			var winRecvLine = openWindow("winRecvLine", url, 600, 310);
		}
	}	
	
	function proxyReset() {
		$('#proxyUserName').val("");
		$('#proxyUserId').val("");
		$('#proxyDeptName').val("");
		$('#proxyPosName').val("");
	}

	function emptyReset() {
		if ($('select.#userStatus option').length > 0) { 
			var firstOptVal = $('select.#userStatus option')[0].value;
			$('select.#userStatus').val(firstOptVal);
		}
		$('#startDate').val('<%=DateUtil.getCurrentDate(dateFormat)%>');
		$('#startDateId').val('<%=DateUtil.getCurrentDate("yyyyMMdd")%>');
		$('#endDate').val('<%=DateUtil.getCurrentDate(dateFormat)%>');
		$('#endDateId').val('<%=DateUtil.getCurrentDate("yyyyMMdd")%>');
		$('select.#selAMPM1').val("00:00:00");
		$('select.#selAMPM2').val("23:59:59");
	}
	
	function setPramDate() {
		var start = $('#startDateId').val()+($('#selAMPM1').val()).replace( /:/g, "" );
		var end = $('#endDateId').val()+($('#selAMPM2').val()).replace( /:/g, "" );
		$('#paramStartDate').val(start);
		$('#paramEndDate').val(end);
	}
	
	function checkDate() {
		setPramDate();
		var result = "1";
		var currentDate = "<%=currentDate%>".substring(0,8);
		var startDate = ($('#paramStartDate').val()).substring(0,8);
		var endDate = ($('#paramEndDate').val()).substring(0,8);
		//alert(currentDate+" "+startDate+" "+endDate);
		if (currentDate > startDate || currentDate > endDate) {
			result = "2"; // 시작일과 종료일은 현재보다 과거일 수 없습니다.
		}
		if (startDate > endDate) {
			result = "3"; // 부재기간이 올바르지 않습니다.
		}
		return result;
	}

	function checkProxy() {
		var objEmpty = null;
		var userId = $('#proxyUserId').val();
		var result = true;
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/selectEnvEmptyStatus.do?userId="+userId, function(data){
			objEmpty = data;
		});
		if (objEmpty.isEmpty) {
			var emptyEnd = $('#endDateId').val()+($('#selAMPM2').val()).replace( /:/g, "" );
			var proxyStart = (objEmpty.emptyStartDate).replace( /:/g, "" );
			proxyStart = proxyStart.replace( /-/g, "" );
			proxyStart = proxyStart.replace( / /g, "" );
			if (emptyEnd > proxyStart) {
				result = false;
			}
		}
		return result;
	}

	function insertEmptyInfo() {
		//대결자에게 발송 옵션일 경우 대결자가 없는 경우 경고메시지 호출, 나머진 경고창 없음, jd.park, 20120426		
		if (opt313=="1" && ($('#proxyUserId').val() == null || $('#proxyUserId').val() == "")) {
			alert("<spring:message code='env.option.msg.validate.noproxy'/>");
			return;
		}		
	
		
		if ($('#emptyUserId').val() == null || $('#emptyUserId').val() == "") {
			alert("<spring:message code='env.option.msg.validate.noempty'/>");
			return;
		}		
		if (cnt == 0) {
			alert("<spring:message code='env.option.msg.validate.noreason'/>");
			return;
		}
		
		if (checkDate()=="1") {
			if ($('#proxyUserId').val() != null && $('#proxyUserId').val() != "") {
				if (!checkProxy()) {
					alert("<spring:message code='env.empty.msg.validate.empty'/>");
					return;
				}
			}
			var result = null;
			$.ajaxSetup({async:false});
			$.getJSON("<%=webUri%>/app/env/insertEnvEmptyStatus.do", $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {	
				alert("<spring:message code='env.empty.msg.success.register'/>");
				$('#spanEmptyStatus').text("<%=msgEmpty%>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
			//self.location.reload();
		} else if (checkDate()=="2") {
			alert("<spring:message code='env.option.msg.validate.wrongperiod2'/>");
		} else {
			alert("<spring:message code='env.option.msg.validate.wrongperiod1'/>");
		}
	}

	function deleteEmptyInfo() {
		if ($('#emptyUserId').val() == null || $('#emptyUserId').val() == "") {
			alert("<spring:message code='env.option.msg.validate.noempty'/>");
			return;
		}
		//if ( ($('#emptyUserId').val() != null && $('#emptyUserId').val() != "") && ($('#proxyUserId').val() == null || $('#proxyUserId').val() == "") ) {
		//	alert("<spring:message code='env.option.msg.validate.noinfo'/>");
		//	return;
		//}
		var result = null;

		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/deleteEnvEmptyStatus.do", $('form').serialize(), function(data){
			result = data;
		});
		
		if (result=="success") {	
			alert("<spring:message code='env.empty.msg.success.delete'/>");
		} else {
			alert("<spring:message code='env.option.msg.error'/>");
		}
		self.location.reload();
		//proxyReset();
	}

</script>
</head>
<body>
<%-- <acube:titleBar popup="true"><spring:message code="env.option.menu.sub.19"/></acube:titleBar> --%>
<acube:outerFrame popup="true">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<form:form modelAttribute="" method="post" name="emptyInfoForm" id="emptyInfoForm">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr>
					<td width="50%" align="left">
						<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.19"/></acube:titleBar>
					</td>
					<td width="50%" align="right">
						<acube:buttonGroup>
							<acube:menuButton onclick="insertEmptyInfo();" value="<%=buttonSave%>" />
							<acube:space between="button" />
							<acube:menuButton onclick="deleteEmptyInfo();" value="<%=buttonWithdrow%>" />
						</acube:buttonGroup>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td height="3"></td></tr>
	<tr>
		<td class="g_box">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="49%" valign="top">
						<!-- 부재설정 -->
						<acube:titleBar type="sub"><spring:message code="env.option.subtitle.07"/></acube:titleBar>
						<acube:tableFrame class="td_table">
							<tr bgcolor="#ffffff">
								<td width="15%" class="tb_tit_left"><nobr><spring:message code="env.option.form.09"/></nobr><!-- 이름 --></td>
								<td width="35%" class="tb_left_bg">
									
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td width="60%">
												<nobr>
												<input type="text" id="emptyUserName" name="emptyUserName" class="input_read" value="<%=userName%>" style="width:70%;" readonly/>
												<font color='#3333ff'><span id="spanEmptyStatus"><%=strEmptyYn%></span></font>
												</nobr>
												<input type="hidden" id="emptyUserId" name="emptyUserId" value="<%=userId%>" />
												<input type="hidden" id="emptyDeptId" name="emptyDeptId" value="<%=deptId%>" />
											</td>
											<td width="5"></td>
											<td width="*">
												<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
													<acube:buttonGroup>
														<acube:button type="4" onclick="popOrgTree('e');" value="<%=buttonEmptySelect%>" />
													</acube:buttonGroup>
												<% } else if (roleCodes.indexOf(roleId11)>=0 && "charge".equals(mode)) { %>
													<acube:buttonGroup>
														<acube:button type="4" onclick="popDeptMember('e');" value="<%=buttonEmptySelect%>" />
													</acube:buttonGroup>
												<% } %>
											</td>
										</tr>									
									</table>
									
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.option.form.07"/></nobr><!-- 소속 --></td>
								<td class="tb_left_bg">
									<input type="text" id="emptyDeptName" name="emptyDeptName" value="<%=deptName%>" class="input_read" style="width:100%;" readonly/>									
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.option.form.08"/></nobr><!-- 직위 --></td>
								<td class="tb_left_bg">
									<input type="text" id="emptyPosName" name="emptyPosName" value="<%=posName%>" class="input_read" style="width:100%;" readonly/>
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.empty.form.02"/></nobr><!-- 사유 --></td>
								<td class="tb_left_bg">
									<nobr>
									<select name="userStatus" id="userStatus">
										<c:forEach var="vo" items="${emptyReason}">
											<option value="${vo.emptyReason}" <c:if test="${vo.emptyReason eq reason}">selected</c:if>>${vo.emptyReason}</option>
										</c:forEach>
									</select>
									</nobr>
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.empty.form.03"/></nobr><!-- 시작일 --></td>
								<td class="tb_left_bg">
									<nobr>
									<input type="text" name="startDate" id="startDate" value="<%=startDate%>" class="input_read" style="width:60%;" readonly/>
									<img id="calendarBTN1" name="calendarBTN1" 						
								        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 						
								        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"						
								        onclick="cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 						
								        '<%= dateFormat %>');">
								    <input type="hidden" name="startDateId" id="startDateId" value="<%=startDateId%>" />
								    <input type="hidden" name="paramStartDate" id="paramStartDate" value="" />
									<select name="selAMPM1" id="selAMPM1" style="width:50px;">
										<option value="00:00:00" <% if ("00".equals(startHour)) { %>selected<% } %>><spring:message code="env.empty.form.05"/></option>
										<option value="12:00:00" <% if ("12".equals(startHour)) { %>selected<% } %>><spring:message code="env.empty.form.06"/></option>
									</select>
									</nobr>
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.empty.form.04"/></nobr><!-- 종료일 --></td>
								<td class="tb_left_bg">
									<nobr>
									<input type="text" name="endDate" id="endDate" value="<%=endDate%>" class="input_read" style="width:60%;" readonly/>
									<img id="calendarBTN2" name="calendarBTN2" 						
								        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 						
								        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"						
								        onclick="cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN1', 						
								        '<%= dateFormat %>');">
								    <input type="hidden" name="endDateId" id="endDateId" value="<%=endDateId%>" />
								    <input type="hidden" name="paramEndDate" id="paramEndDate" value="" />
									<select name="selAMPM2" id="selAMPM2" style="width:50px;">
										<option value="11:59:59" <% if ("11".equals(endHour)) { %>selected<% } %>><spring:message code="env.empty.form.05"/></option>
										<option value="23:59:59" <% if ("23".equals(endHour)) { %>selected<% } %>><spring:message code="env.empty.form.06"/></option>
									</select>
									</nobr>
								</td>
							</tr>
						</acube:tableFrame>
					</td>
					<td width="2%"></td>
					<td width="49%" valign="top">
						<!-- 대결지정 -->
						<acube:titleBar type="sub"><spring:message code="env.option.subtitle.08"/></acube:titleBar>
						<acube:tableFrame class="td_table">
							<tr bgcolor="#ffffff">
								<td width="15%" class="tb_tit_left"><nobr><spring:message code="env.option.form.09"/></nobr><!-- 이름 --></td>
								<td width="35%" class="tb_left_bg">
									
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td width="50%">
												<input type="text" id="proxyUserName" name="proxyUserName" value="<%=proxyUserName%>" class="input_read" readOnly style="width:100%;" />
												<input type="hidden" id="proxyUserId" name="proxyUserId" value="<%=proxyUserId%>" />
											</td>
											<td width="5"></td>
											<td width="*">
												<acube:buttonGroup>
													<acube:button type="4" onclick="popOrgTree('p');" value="<%=buttonProxySelect%>" />
													<acube:space between="button" />
													<acube:button type="4" onclick="proxyReset();" value="<%=buttonDelete%>" />
												</acube:buttonGroup>
											</td>	
										</tr>																			
									</table>
									
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.option.form.07"/></nobr><!-- 소속 --></td>
								<td class="tb_left_bg">
									<input type="text" id="proxyDeptName" name="proxyDeptName" value="<%=proxyDeptName%>" class="input_read" readOnly style="width:100%;" />
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td class="tb_tit_left"><nobr><spring:message code="env.option.form.08"/></nobr><!-- 직위 --></td>
								<td class="tb_left_bg">
									<input type="text" id="proxyPosName" name="proxyPosName" value="<%=proxyPosName%>" class="input_read" readOnly style="width:100%;" />
								</td>
							</tr>
						</acube:tableFrame>
					</td>
				</tr>
			</table>			
		</td>
	</tr>
	</form:form>
</table>

</acube:outerFrame>

</body>
</html>