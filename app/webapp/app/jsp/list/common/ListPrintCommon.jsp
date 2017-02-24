<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>

<%
/** 
 *  Class Name  : ListPrintCommon.jsp 
 *  Description : 리스트 메인 공통 javascript 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
response.setHeader("pragma","no-cache");

%>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script language="javascript">
<!--
$(document).ready(function(){
	init(); 	
});


var initBody;
function beforePrint(){
	initBody = document.body.innerHTML;
	document.body.innerHTML = printDiv.innerHTML; 
}

function afterPrint(){
	document.body.innerHTML = initBody;
}

function init(){
	
	/*
	window.onbeforeprint = beforePrint;
	window.onafterprint = afterPrint;
	window.print();
	*/

}
function printWin(){
	window.onbeforeprint = beforePrint;
	window.onafterprint = afterPrint;
	window.print();	
}

function closeWin(){
	if(opener.printDoc != null || opener.printDoc.closed == false){
		opener.printDoc.close();
	}
}

//-->
</script>
