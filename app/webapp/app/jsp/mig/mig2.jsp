<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%
	String webUri = AppConfig.getProperty("web_uri", "/ep", "path");

	String mode = request.getParameter("mode");
	if (mode == null || !AppConfig.getProperty("systemUser", "", "systemOperation").equals(mode)) {
		response.sendRedirect(webUri + "/app/jsp/error/ErrorNoPage.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script LANGUAGE="JavaScript">
var startMonth = "";
var year="";
var month="";
$(document).ready(function(){
    $("#migStartBtn").click(function(){
    	startYearMonth = $("#startMonth").val();
		year = startYearMonth.substring(0,4);
		month = startYearMonth.substring(4,6);
    	startMig();
    	$(this).hide();
    	$("#startMonth").hide();
    });
});

function isNotNull(str){
	if(str==""||str == null){
		return false;
	}else{
		return true;
	}
}
function calcNextDate(){
	if(isNotNull(startYearMonth) && isNotNull(year) && isNotNull(month)){
		if(month<12){
			month++; if(month<10) month="0"+month;
		}else{
			month="01";
			year++;
		}
	}
	startYearMonth = year+""+month;
}
function startMig(){
	if(year=="2016"){
		alert("2015년까지 완료되었습니다.");
	}else{
		$.ajax({
		    type : "POST" 
		    , url : "<%=webUri%>/app/mig/mig.do" 
		    , dataType : "json" 
		    , timeout : 3000000  //밀리세컨드단위 제한시간 1/1000초 현재는 1800초(30분)
		    , data : {
		    	start : startYearMonth	
		    }
		    , error : function(request, status, error) {
		        $("#resultDIV").append("<tr><td colspan=3>작업실패 커넥션이 끊김 입력일자: "+startYearMonth+" </td></tr>");
		     startMig();
		    }
		    , success : function(json, status, request) {
		    	var list = $.parseJSON(json.resultList);
		    	var time = json.durTime;
		        var listLen = list.length;
		        var contentStr = "";
		        for(var i=0; i<listLen; i++){
		    		if(list[i].status().equals("0")){
		            	contentStr += "<tr><td>"+ list[i].docId+"</td><td>"+ list[i].status + "</td><td>" + list[i].msg + "</td></tr>";
		    		}
		        }
		        $("#resultDIV").append("<tr><td colspan=3>입력일자: "+startYearMonth+" 입력시간: "+time+"</td></tr>");
		        $("#resultDIV").append(contentStr);
		        calcNextDate();
		        startMig();
		    }
		    , beforeSend: function() {
		    }
		    , complete: function() {
		     
		    }
		});
	}//else
}//function
</script>
</head>
<body>
<input type="text" id="startMonth">
<input type="button" value="입력" id="migStartBtn">
<table>
<th>
	<td>DOCID</td>
	<td>status</td>
	<td>msg</td>
	<td>time</td>
</th>
<tbody id="resultDIV">
</tbody>
</table>
</body>
</html>