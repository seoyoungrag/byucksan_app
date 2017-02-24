<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.NameValuePair" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%
    /** 
     *  Class Name  : testSendFSP.jsp 
     *  Description : 산은캐피탈 FSP 테스트 모듈 
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
boolean result = false;
try {
    String currentDate = DateUtil.getCurrentDate();
//    UserVO drafter = orgService.selectUserByUserId(userId);
//    UserVO approver = orgService.selectUserByUserId(appId);
	  String requestTime = "SRT020";

    // "http://fspserver.kdbc.co.kr:9080/drmone/req_printex.jsp";
    String callURL = AppConfig.getProperty("fspUrl", "", "comp004");
    callURL = "http://192.168.221.104:9080/drmone/req_printex.jsp";
    out.println("# callURL : " + callURL);

    NameValuePair nameValuePairs[] = new NameValuePair[8];
    nameValuePairs[0] = new NameValuePair("StartDate", DateUtil.getFormattedDate(currentDate, "yyyy-MM-dd"));
    nameValuePairs[1] = new NameValuePair("StartTime", DateUtil.getFormattedDate(currentDate, "HH:mm:ss"));
    String drafterId = "C2007089";
    if ("C".equals(drafterId.substring(0, 1).toUpperCase())) {
		drafterId = drafterId.substring(1);
    }
    nameValuePairs[2] = new NameValuePair("UserId", drafterId);
//    nameValuePairs[2] = new NameValuePair("UserId", "2007089");
    nameValuePairs[3] = new NameValuePair("EndDate", DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "yyyy-MM-dd"));
    nameValuePairs[4] = new NameValuePair("EndTime", DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "HH:mm:ss"));
    nameValuePairs[5] = new NameValuePair("ReqComment", "TEST");
    String approverId = "C1990135";
    if ("C".equals(approverId.substring(0, 1).toUpperCase())) {
	approverId = approverId.substring(1);
    }
    nameValuePairs[6] = new NameValuePair("AppId", approverId);
//    nameValuePairs[6] = new NameValuePair("AppId", "1990135");
    nameValuePairs[7] = new NameValuePair("AppComment", " ");

    out.println("# StartDate : " + DateUtil.getFormattedDate(currentDate, "yyyy-MM-dd"));
    out.println("# StartTime : " + DateUtil.getFormattedDate(currentDate, "HH:mm:ss"));
    out.println("# UserId : " + drafterId);
    out.println("# EndDate : " + DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "yyyy-MM-dd"));
    out.println("# EndTime : " + DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "HH:mm:ss"));
    out.println("# ReqComment : " + "TEST");
    out.println("# AppId : " + approverId);
    out.println("# AppComment : " + " ");
    
    HttpClient httpClient = new HttpClient();
//    httpClient.setConnectionTimeout(5000);
    httpClient.getParams().setParameter("http.connection.timeout", 5000);
    PostMethod postMethod = new PostMethod(callURL);
    postMethod.setRequestBody(nameValuePairs);
    int responseCode = httpClient.executeMethod(postMethod);
    if (responseCode == 200) {
		String responseMsg = postMethod.getResponseBodyAsString();
		responseMsg = responseMsg.trim();
		if (responseMsg.startsWith("SUCCESS")) {
		    out.println("# Call FSP : " + responseMsg);
		    result = true;
		} else {
		    out.println("# Call FSP : " + responseMsg);
		}
    } else {
	    out.println("responseCode : " + responseCode);
    }
    postMethod.releaseConnection();
    

} catch(Exception e) {
    out.println("Error Message : " + e.getMessage());
} 

out.println("# Result : " + result);
%>