<%@ page language="java" errorPage="/sample/common/error.jsp"
     contentType="text/html; charset=UTF-8" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%@ page import="java.util.List" %>

<%@ page import="java.util.Locale" %>

<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>









<% 
    //String msg = messageSource.getMessage("메세지ID" , null, (Locale)session.getAttribute("LANG_TYPE"));
%>



<head>
<title>jhgjhgjhgjhgjh </title>
<meta name="heading" content="" />
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/blockUI.js"></script>
<script type="text/javascript">


    //연계기안
    function lfn_reqExchange(){
        document.updateForm.action="<c:url value='/ws/server/insertAppDoc.do'/>";
        document.updateForm.submit();
    }
    
    function lfn_listBox(){
        document.updateForm.action="<c:url value='/ws/server/listBox.do'/>";
        document.updateForm.submit();
    }
	</script>
</head>
<!--************************** begin of contents *****************************-->


<!--begin of title-->
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td>
<form name="updateForm">
  문서아이디:<input type="hidden" name="docId" value="ENFT0000000000F612E7404TTTTT0009" style="width:50%" /><br>
  회사코드:  <input type="hidden" name="compId" value="002" /><br>
  의견: <input type="hidden" name="opinion" value="" style="width:100%"/><br>
 <input type="hidden" name="opinionYn" value="" style="width:100%"/>
 <input type="hidden" name="returnFunction" value="" style="width:100%"/>
 <input type="hidden" name="btnName" value="" style="width:100%"/>

    <table><tr><td><a id="updatelink" href="javascript:lfn_reqExchange();"> 연계기안요청 </a></td></tr></table>
     <table><tr><td><a id="updatelink" href="javascript:lfn_listBox();"> 연계(함목록) </a></td></tr></table> 
</form>
</td>
</tr>
</table>