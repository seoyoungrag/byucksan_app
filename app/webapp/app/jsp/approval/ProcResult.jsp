<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기
String app611 = appCode.getProperty("APP611", "APP611", "APP"); // 반려후대장등록  // jth8172 2012 신결재 TF

String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
String count = CommonUtil.nullTrim((String)request.getAttribute("count"));
String dupAccept = CommonUtil.nullTrim((String)request.getAttribute("dupAccept"));
List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
String insertedDocId = CommonUtil.nullTrim((String)request.getAttribute("insertedDocId"));

JSONObject json = new JSONObject();
// 처리 결과
json.put("count", count);
json.put("result", result);
json.put("dupAccept", dupAccept);
json.put("docId", insertedDocId);
if (!"".equals(message)) {
    if (!"".equals(count)) {
		json.put("message", (messageSource.getMessage(message, null, langType)).replace("%s", count));
    } else {
		json.put("message", messageSource.getMessage(message, null, langType));
    }
}

// 처리 데이터
if (appDocVOs != null) {
    int docCount = appDocVOs.size();
	JSONArray jsonArray = new JSONArray();		
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    String state = appDocVO.getDocState();
		if (app600.equals(state) || app610.equals(state)|| app611.equals(state)) { // jth8172 2012 신결재 TF
			json.put("state", state);
		    List<FileVO> fileVOs = appDocVO.getFileInfo();
			JSONObject jsonDoc = new JSONObject();
			jsonDoc.put("docid", appDocVO.getDocId());
			jsonDoc.put("serial", appDocVO.getSerialNumber());
			jsonDoc.put("itemnum", appDocVO.getBatchDraftNumber());
	
			int fileCount = fileVOs.size();
		    for (int pos = 0; pos < fileCount; pos++) {
			    FileVO fileVO = (FileVO) fileVOs.get(pos);
			    if (aft001.equals(fileVO.getFileType())) {
					jsonDoc.put("hwpfile", fileVO.getFileId());
			    } else if (aft002.equals(fileVO.getFileType())) {
					jsonDoc.put("htmlfile", fileVO.getFileId());
			    }
			}
		    jsonArray.put(jsonDoc);
		}
		json.put("bodyfile", jsonArray);
    }
}

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>