<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
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
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String resultListType = CommonUtil.nullTrim(resultSearchVO.getListType());
String resultSearchDeptId = CommonUtil.nullTrim(resultSearchVO.getSearchDeptId());
String resultSearchDeptName = CommonUtil.nullTrim(resultSearchVO.getSearchDeptName());
String resultSearchUserId = CommonUtil.nullTrim(resultSearchVO.getSearchUserId());
String resultSearchUserName = CommonUtil.nullTrim(resultSearchVO.getSearchUserName());
String resultDocType = CommonUtil.nullTrim(resultSearchVO.getSearchDocType());
String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
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
        <input name="listType" id="listType" type="hidden" value="<%=resultListType%>">
        <input name="searchDeptId" id="searchDeptId"  type="hidden" value="<%=resultSearchDeptId%>">
        <input name="searchDeptName" id="searchDeptName"  type="hidden" value="<%=resultSearchDeptName%>">        
        <input name="searchUserId" id="searchUserId"  type="hidden" value="<%=resultSearchUserId%>">
        <input name="searchUserName" id="searchUserName"  type="hidden" value="<%=resultSearchUserName%>">
        <input name="searchDocType" id="searchDocType"  type="hidden" value="<%=resultDocType%>">
        <input name="searchWord" id="searchWord"  type="hidden" value="<%=resultSearchWord%>">
</form>
<iframe id="myframe" name="myframe" frameborder="0" width="0" height="0"></iframe>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>