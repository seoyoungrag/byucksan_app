<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.list.util.ListUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListPagingDateForm.jsp 
 *  Description : paging 관련 form 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 27 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String resultSearchType 			= CommonUtil.nullTrim(resultSearchVO.getSearchType());
String resultSearchWord 			= CommonUtil.nullTrim(resultSearchVO.getSearchWord());
String resultStartDate				= CommonUtil.nullTrim(resultSearchVO.getStartDate());
String resultEndDate				= CommonUtil.nullTrim(resultSearchVO.getEndDate());
String resultDocType				= CommonUtil.nullTrim(resultSearchVO.getDocType());
String resultReadingRange			= CommonUtil.nullTrim(resultSearchVO.getReadingRange());
String resultDeptId					= ListUtil.TransReplace(CommonUtil.nullTrim(resultSearchVO.getDeptId()),"'","");
String resultDeptName				= CommonUtil.nullTrim(resultSearchVO.getDeptName());
String resultDisplayYn				= CommonUtil.nullTrim(resultSearchVO.getDisplayYn());
String resultSearchWordDeptName		= CommonUtil.nullTrim(resultSearchVO.getSearchWordDeptName());
String resultSearchAuditReadY		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditReadY());
String resultSearchAuditReadN		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditReadN());

String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);

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
        <input type="hidden" name="readRange" id="readRange" value="<%=resultReadingRange%>">
        <input name="searchDeptId" id="searchDeptId"  type="hidden" value="<%=resultDeptId%>">
        <input name="searchDeptName" id="searchDeptName"  type="hidden" value="<%=resultDeptName%>">
        <input name="searchWordDeptName" id="searchWordDeptName"  type="hidden" value="<%=resultSearchWordDeptName%>">
        
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
        <input type="hidden" name="selDisplayYn" id="selDisplayYn" value="<%=resultDisplayYn %>"></input>
        <input type="hidden" name="searchAuditReadY" id="searchAuditReadY" value="<%=resultSearchAuditReadY %>"></input>
        <input type="hidden" name="searchAuditReadN" id="searchAuditReadN" value="<%=resultSearchAuditReadN %>"></input>
        
</form>
<iframe id="myframe" name="myframe" frameborder="0" width="0" height="0"></iframe>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>