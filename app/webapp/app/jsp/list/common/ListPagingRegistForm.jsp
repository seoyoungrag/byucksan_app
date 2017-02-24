<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListPagingRegistForm.jsp 
 *  Description : paging 관련 form 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 13 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String deptAllYn = CommonUtil.nullTrim(request.getParameter("deptAll"));

String resultSearchType 		= CommonUtil.nullTrim(resultSearchVO.getSearchType());
String resultSearchWord 		= CommonUtil.nullTrim(resultSearchVO.getSearchWord());
String resultStartDate			= CommonUtil.nullTrim(resultSearchVO.getStartDate());
String resultEndDate			= CommonUtil.nullTrim(resultSearchVO.getEndDate());
String resultDocType			= CommonUtil.nullTrim(resultSearchVO.getDocType());
String resultCurRange 			= CommonUtil.nullTrim(resultSearchVO.getRegistCurRange());
String resultSearchTypeYn		= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeYn());
String resultSearchTypeValue	= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeValue());

resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);

//상세조건
String resultStartDocNum 			= CommonUtil.nullTrim(resultSearchVO.getStartDocNum());
String resultEndDocNum 				= CommonUtil.nullTrim(resultSearchVO.getEndDocNum());
String resultBindingId 				= CommonUtil.nullTrim(resultSearchVO.getBindingId());
String resultBindingName 			= CommonUtil.nullTrim(resultSearchVO.getBindingName());
String resultSearchElecYn 			= CommonUtil.nullTrim(resultSearchVO.getSearchElecYn());
String resultSearchNonElecYn 		= CommonUtil.nullTrim(resultSearchVO.getSearchNonElecYn());
String resultSearchDetType 			= CommonUtil.nullTrim(resultSearchVO.getSearchDetType());
String resultSearchApprovalName 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprovalName());
String resultSearchApprTypeApproval = CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeApproval());
String resultSearchApprTypeExam 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeExam());
String resultSearchApprTypeCoop 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeCoop());
String resultSearchApprTypeDraft 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeDraft());
String resultSearchApprTypePreDis 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypePreDis());
String resultSearchApprTypeResponse = CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeResponse());
String resultSearchApprTypeAudit	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeAudit());
String resultSearchAppDocYn			= CommonUtil.nullTrim(resultSearchVO.getAppDocYn());
String resultSearchEnfDocYn			= CommonUtil.nullTrim(resultSearchVO.getEnfDocYn());
//상세조건 끝

String resultSearchAuditOpt009		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt009());
String resultSearchAuditOpt021		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt021());
String resultSearchAuditOpt022		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt022());
String resultSearchAuditOpt023		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt023());

String easyApprSearch				= CommonUtil.nullTrim(resultSearchVO.getEasyApprSearch());
String easyEnfSearch				= CommonUtil.nullTrim(resultSearchVO.getEasyEnfSearch());

String resultSearchLobCode			= CommonUtil.nullTrim(resultSearchVO.getSearchLobCode());

String searchAuthDeptId				= CommonUtil.nullTrim(resultSearchVO.getSearchAuthDeptId());
String searchAuthDeptName			= CommonUtil.nullTrim(resultSearchVO.getSearchAuthDeptName());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
</head>
<body>
<form name="pagingList" id="pagingList" method="post" style="margin:0px">
        <input name="cPage" id="cPage" type="hidden">
        <input name="searchWord" id="searchWord" type="hidden" value="<%=resultSearchWord%>">
        <input name="searchType" id="searchType"  type="hidden" value="<%=resultSearchType%>">
        <input name="startDate" id="startDate"  type="hidden" value="<%=resultStartDate%>">
        <input name="endDate" id="endDate"  type="hidden" value="<%=resultEndDate%>">
        <input name="searchDocType" id="searchDocType"  type="hidden" value="<%=resultDocType%>">
        <input type="hidden" name="searchCurRange" id="searchCurRange" value="<%=resultCurRange%>">
		<input type="hidden" name="searchTypeYn" id="searchTypeYn" value="<%=resultSearchTypeYn%>">
		<input type="hidden" name="searchTypeValue" id="searchTypeValue" value="<%=resultSearchTypeValue%>">
		
		<input name="startDocNum" id="startDocNum"  type="hidden" value="<%=resultStartDocNum%>">
        <input name="endDocNum" id="endDocNum"  type="hidden" value="<%=resultEndDocNum%>">
        <input name="bindingId" id="bindingId"  type="hidden" value="<%=resultBindingId%>">
        <input name="bindingName" id="bindingName"  type="hidden" value="<%=resultBindingName%>">
        <input name="searchElecYn" id="searchElecYn"  type="hidden" value="<%=resultSearchElecYn%>">
        <input name="searchNonElecYn" id="searchNonElecYn"  type="hidden" value="<%=resultSearchNonElecYn%>">
        <input name="searchDetType" id="searchDetType"  type="hidden" value="<%=resultSearchDetType%>">
        <input name="searchApprovalName" id="searchApprovalName"  type="hidden" value="<%=resultSearchApprovalName%>">
        <input name="searchApprTypeApproval" id="searchApprTypeApproval"  type="hidden" value="<%=resultSearchApprTypeApproval%>">
        <input name="searchApprTypeExam" id="searchApprTypeExam"  type="hidden" value="<%=resultSearchApprTypeExam%>">
        <input name="searchApprTypeCoop" id="searchApprTypeCoop"  type="hidden" value="<%=resultSearchApprTypeCoop%>">
        <input name="searchApprTypeDraft" id="searchApprTypeDraft"  type="hidden" value="<%=resultSearchApprTypeDraft%>">
        <input name="searchApprTypePreDis" id="searchApprTypePreDis"  type="hidden" value="<%=resultSearchApprTypePreDis%>">
        <input name="searchApprTypeResponse" id="searchApprTypeResponse"  type="hidden" value="<%=resultSearchApprTypeResponse%>">
        <input name="searchApprTypeAudit" id="searchApprTypeAudit"  type="hidden" value="<%=resultSearchApprTypeAudit%>">
        <input name="searchAppDocYn" type="hidden" value="<%=resultSearchAppDocYn %>">
        <input name="searchEnfDocYn" type="hidden" value="<%=resultSearchEnfDocYn %>">
        <input type="hidden" name="deptAll" id="deptAll" value="<%=deptAllYn%>">
        
        <input type="hidden" id="searchAuditOpt009" name="searchAuditOpt009" value="<%=resultSearchAuditOpt009 %>"></input>
        <input type="hidden" id="searchAuditOpt021" name="searchAuditOpt021" value="<%=resultSearchAuditOpt021 %>"></input>
        <input type="hidden" id="searchAuditOpt022" name="searchAuditOpt022" value="<%=resultSearchAuditOpt022 %>"></input>
        <input type="hidden" id="searchAuditOpt023" name="searchAuditOpt023" value="<%=resultSearchAuditOpt023 %>"></input>
        <input type="hidden" name="easyApprSearch" id="easyApprSearch" value="<%=easyApprSearch %>"/>
		<input type="hidden" name="easyEnfSearch" id="easyEnfSearch" value="<%=easyEnfSearch %>"/>
		<input type="hidden" name="searchLobCode" id="searchLobCode" value="<%=resultSearchLobCode%>" />
		<input type="hidden" name="searchAuthDeptId" id="searchAuthDeptId" value="<%=searchAuthDeptId%>" />
		<input type="hidden" name="searchAuthDeptName" id="searchAuthDeptName" value="<%=searchAuthDeptName%>" />
</form>
<iframe id="myframe" name="myframe"  frameborder="0" width="0" height="0"></iframe>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>