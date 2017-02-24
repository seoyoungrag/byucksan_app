<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.schedule.service.IScheduleService" %>
<%@ page import="com.sds.acube.app.exchange.service.IDocumentService" %>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>
<%@ page import="org.quartz.impl.StdScheduler"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
String schedule = CommonUtil.nullTrim(request.getParameter("schedule"));
String actionType = CommonUtil.nullTrim(request.getParameter("actionType"));

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(schedule);
logger.debug(actionType);
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

JSONObject json = new JSONObject();

if ("start".equals(actionType)) {
	StdScheduler scheduler = (StdScheduler) ctx.getBean(schedule);
	scheduler.start();
	json.put("result", "success");
	json.put("message", messageSource.getMessage("appcom.msg.start.service", null, langType));
} else if ("standby".equals(actionType)) {
	StdScheduler scheduler = (StdScheduler) ctx.getBean(schedule);
	scheduler.standby();
	json.put("result", "success");
	json.put("message", messageSource.getMessage("appcom.msg.standby.service", null, langType));
} else if ("shutdown".equals(actionType)) {
	// StdScheduler scheduler = (StdScheduler) ctx.getBean(schedule);
	// scheduler.shutdown();
	json.put("result", "success");
	json.put("message", messageSource.getMessage("appcom.msg.shutdown.service", null, langType));
} else if ("restart".equals(actionType)) {
	// StdScheduler scheduler = (StdScheduler) ctx.getBean(schedule);
	// scheduler.scheduleJob(new JobDetail(jname, "DEFAULT", cname), new CronTrigger(jname, "DEFAULT", expr));
	// scheduler.standby();
	json.put("result", "success");
	json.put("message", messageSource.getMessage("appcom.msg.restart.service", null, langType));
} else if ("execute".equals(actionType)) {
	StdScheduler scheduler = (StdScheduler) ctx.getBean(schedule);
	if ("manageAnyGroupSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
	    scheduleService.manageAnyGroup();
	} else if ("sendLegacyServiceSFBean".equals(schedule)) {
	    IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
	    scheduleService.sendLegacy();
	} else if ("receiveLegacyServiceSFBean".equals(schedule)) {
	    IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
	    scheduleService.receiveLegacy();
	} else if ("legayDeleteServiceSFBean".equals(schedule)) {
	    IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
	    scheduleService.legacyDelete();
	} else if ("toDocmgrSFBean".equals(schedule)) {
	    IDocumentService documentService = (IDocumentService)ctx.getBean("documentService");
	    documentService.createImmediately();
	} else if ("removeDocQueueSFBean".equals(schedule)) {
	    IDocumentService documentService = (IDocumentService)ctx.getBean("documentService");
	    documentService.removeQueue();
	} else if ("removeAccessHistorySFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.removeAccessHistory();
	} else if ("removeExchangeHistorySFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.removeExchangeHistory();
	} else if ("makeBindBatchSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.makeBindBatch();
	} else if ("deleteAPPTempBatchSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.deleteAPPTempFolder();
	} else if ("syncAnyGroupSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.syncAnyGroup();
	} else if ("sendRelayServiceSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.relaySend();
	} else if ("recvRelayServiceSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.relayRecv();
	} else if ("workingDeleteServiceSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.workingDelete();
	} else if ("processMobileAppBatchSFBean".equals(schedule)) {
		IScheduleService scheduleService = (IScheduleService)ctx.getBean("scheduleService");
		scheduleService.processMobileApp();
	}
	
	json.put("result", "success");
	json.put("message", messageSource.getMessage("appcom.msg.execute.service", null, langType));
} else {
    json.put("result", "false");
    json.put("message", messageSource.getMessage("appcom.msg.notexist.service", null, langType));
}

// 처리 결과
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>