<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListPagingForm.jsp 
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
//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
String resultDocType	= CommonUtil.nullTrim(resultSearchVO.getDocType());

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
        <input name="searchDocType" id="searchDocType"  type="hidden" value="<%=resultDocType%>">
        
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
</form>
<iframe id="myframe" name="myframe" frameborder="0" width="0" height="0"></iframe>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>