<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="com.sds.acube.app.schedule.vo.ScheduleVO" %>
<%@ page import="org.quartz.impl.StdScheduler"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : ListSchedule.jsp 
 *  Description : 결재 서비스 일정 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String instanceName = "";
	try {
		java.net.InetAddress address = java.net.InetAddress.getLocalHost();
		instanceName = address.getHostAddress() + ":" + Integer.toString(request.getServerPort());
	} catch(Exception ex) {}
	
	List<ScheduleVO> scheduleVOs = (List<ScheduleVO>) request.getAttribute("scheduleVOs");		
	
	// 버튼명
	String startBtn = messageSource.getMessage("appcom.button.start", null, langType); 
	String standbyBtn = messageSource.getMessage("appcom.button.standby", null, langType); 
	String shutdownBtn = messageSource.getMessage("appcom.button.shutdown", null, langType); 
	String restartBtn = messageSource.getMessage("appcom.button.restart", null, langType); 
	String executeBtn = messageSource.getMessage("appcom.button.execute", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='appcom.title.servicemanage'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$.ajaxSetup({async:false});

function initialize() {
}

function startSchedule(schedule) {
	$("#schedule").val(schedule);
	$("#actionType").val("start");
	$.post("<%=webUri%>/app/schedule/startSchedule.do", $("#scheduleForm").serialize(), function(data) {
		alert(data.message);
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.service'/>");
	});

	document.location.href = "<%=webUri%>/app/schedule/listSchedule.do";
}

function standbySchedule(schedule) {
	$("#schedule").val(schedule);
	$("#actionType").val("standby");
	$.post("<%=webUri%>/app/schedule/stopSchedule.do", $("#scheduleForm").serialize(), function(data) {
		alert(data.message);
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.service'/>");
	});

	document.location.href = "<%=webUri%>/app/schedule/listSchedule.do";
}

function shutdownSchedule(schedule) {
	$("#schedule").val(schedule);
	$("#actionType").val("shutdown");
	$.post("<%=webUri%>/app/schedule/stopSchedule.do", $("#scheduleForm").serialize(), function(data) {
		alert(data.message);
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.service'/>");
	});

	document.location.href = "<%=webUri%>/app/schedule/listSchedule.do";
}

function restartSchedule(schedule) {
	$("#schedule").val(schedule);
	$("#actionType").val("restart");
	$.post("<%=webUri%>/app/schedule/stopSchedule.do", $("#scheduleForm").serialize(), function(data) {
		alert(data.message);
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.service'/>");
	});
	
	document.location.href = "<%=webUri%>/app/schedule/listSchedule.do";
}

function executeSchedule(schedule) {
	$("#schedule").val(schedule);
	$("#actionType").val("execute");
	$.post("<%=webUri%>/app/schedule/executeSchedule.do", $("#scheduleForm").serialize(), function(data) {
		alert(data.message);
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.service'/>");
	});

	document.location.href = "<%=webUri%>/app/schedule/listSchedule.do";
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='appcom.title.servicemanage'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><%=instanceName%></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<table cellpadding="2" cellspacing="1" width="100%" class="table">
			 		<tr bgcolor="#ffffff">
						<td class="ltb_head" width="20%" style="height: 28px;"><spring:message code='appcom.title.servicename'/></td>
						<td class="ltb_head" width="40%" style="height: 28px;"><spring:message code='appcom.title.serviceclass'/></td>
						<td class="ltb_head" width="15%" style="height: 28px;"><spring:message code='appcom.title.serviceperiod'/></td>
						<td class="ltb_head" width="15%" style="height: 28px;"><spring:message code='appcom.title.serviceexecute'/></td>
						<td class="ltb_head" width="10%" style="height: 28px;"><spring:message code='appcom.title.servicemanage'/></td>
					</tr>
<%
	int scheduleCount = scheduleVOs.size();
	if (scheduleCount > 0) {
	    for (int loop = 0; loop < scheduleCount; loop++) {
			ScheduleVO scheduleVO = scheduleVOs.get(loop);
			String sfbName = scheduleVO.getScheduleFactoryBean();
			String startMethod = "startSchedule('" + sfbName + "');return(false);";
			String standbyMethod = "standbySchedule('" + sfbName + "');return(false);";
			String shutdownMethod = "shutdownSchedule('" + sfbName + "');return(false);";
			String restartMethod = "restartSchedule('" + sfbName + "');return(false);";
			String executeMethod = "executeSchedule('" + sfbName + "');return(false);";
			StdScheduler scheduler = (StdScheduler) ctx.getBean(sfbName);
%>
					<tr bgcolor="#ffffff">
						<td rowspan="2" class="ltb_center" style="height: 28px;"><%=scheduleVO.getJobDetailFactoryBean()%></td>
						<td class="ltb_left" style="height: 28px;">&nbsp;<%=scheduleVO.getTarget()%></td>
						<td class="ltb_left" style="height: 28px;">&nbsp;<%=scheduleVO.getCronExpression()%></td>
						<td rowspan="2" class="ltb_center" style="height: 28px;">
<% 		if (scheduler.isStarted()) {
    			if (scheduler.isInStandbyMode()) { %>						
<%			} else { %>
							<acube:buttonGroup align="center">
								<acube:button onclick="<%=executeMethod%>" value="<%=executeBtn%>" type="2" class="gr" />
							</acube:buttonGroup> 
<%			} 
			} else { %>							
<%		} %>							
						</td>
						<td rowspan="2" class="ltb_center" style="height: 28px;">
<% 		if (scheduler.isStarted()) {
    			if (scheduler.isInStandbyMode()) { %>						
							<acube:buttonGroup align="center">
								<acube:button onclick="<%=startMethod%>" value="<%=startBtn%>" type="2" class="gr" />
							</acube:buttonGroup> 
<%			} else { %>
							<acube:buttonGroup align="center">
								<acube:button onclick="<%=standbyMethod%>" value="<%=standbyBtn%>" type="2" class="gr" />
<% /* %>								
								<acube:space between="button" />
								<acube:button onclick="<%=shutdownMethod%>" value="<%=shutdownBtn%>" type="2" class="gr" />
<% */ %>								
							</acube:buttonGroup> 
<%			} 
			} else { %>							
							<acube:buttonGroup align="center">
								<acube:button onclick="<%=restartMethod%>" value="<%=restartBtn%>" type="2" class="gr" />
							</acube:buttonGroup> 
<%		} %>							
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td colspan="2" class="ltb_left" style="height: 28px;">&nbsp;<%=scheduleVO.getDescription()%></td>
					</tr>						
<%		    
	    }
	} else {
%>
			 		<tr bgcolor="#ffffff">
						<td class="ltb_center" style="height: 28px;" colspan="5"><spring:message code='appcom.msg.notexist.scheduleservice'/></td>
					</tr>
<%		    
	}
%>	
				</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>
<form id="scheduleForm" name="scheduleForm" method="post" onsubmit="return false;">
	<input id="schedule" name="schedule" type="hidden" value=""/>
	<input id="actionType" name="actionType" type="hidden" value=""/>
</form>
</body>
</html>