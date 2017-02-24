<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sds.acube.app.mig.vo.MigVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
//임시 코드(commutil을 반영하지 않았음.. 서버를 재기동하지 않고 jsp 수정하여 반영하고, 추후에 commutil 변경반영할 시에 이 jsp도 변경해서 반영하겠음)
int nPage = 0;
if(request.getParameter("page")!=null){
	nPage = Integer.parseInt(request.getParameter("page"));
}
//임시 코드 끝
//nPage = Integer.parseInt(CommonUtil.nullTrim(request.getParameter("page"),"0"));
String start = CommonUtil.nullTrim(request.getParameter("start"));
String isSequencial = CommonUtil.nullTrim(request.getParameter("go"));
String cabinet = CommonUtil.nullTrim(request.getParameter("cabinet"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script>
$(document).ready(function() { initialize(); });

var page = '<%=nPage%>';
var start = '<%=start%>';
var webUri = '<%=webUri%>';
var isSequencial = '<%=isSequencial%>';
var cabinet = '<%=cabinet%>';
var end = '';
var startYear = '';
var endYear ='';
var startMonth ='';
var endMonth ='';
if(start!=""){
	startYear = start.substring(0,4)-0;
	startMonth = start.substring(4,6)-0;
	endYear = startYear;
	endMonth = startMonth+6;
	if(endMonth>12){
		endMonth -= 12;
		endYear++;
	}
	if(endMonth<10){
		endMonth = "0"+endMonth;
	}
	end = endYear+""+endMonth;

}
function doNextMig(start){

	startMonth++;
	if(startMonth>12){
		startMonth -= 12;
		startYear++;
	}
	if(startMonth<10){
		startMonth = "0"+startMonth;
	}
	start = startYear+""+startMonth;
	if(startYear<endYear){
		if(startMonth<=((endMonth-0)+12)){
			doMig(start, 1);
		}
	}else{
		if((startMonth-0)<=(endMonth-0)){
			doMig(start, 1);
		}
	}
}
function initialize(){
	if(page > 0 && start != '') {
		doMig(start, page);
	}
}

function doMig(start, page){
	
	$.post(webUri+'/app/mig/mig.do?start='+start+'&page='+page+'&cabinet='+cabinet, function(data) {
		var content = '<tr>' + 
			'<td colspan=4> 현재 페이지: '+page+' 현재 진행월: '+start+'</td>' + 
		'</tr>';

		for(var i=0;i<data.resultList.length;i++){
			content += '<tr>' + 
				'<td>'+data.resultList[i].docid+'</td>' + 
				'<td>'+page+'</td>' + 
				'<td>'+data.resultList[i].status+'</td>' + 
				'<td>'+data.resultList[i].msg+'</td>' + 
			'</tr>';
		}
		$('#migLog').append(content);
		page++;
		
		if(page <= parseInt(data.endPage)){
			doMig(start, page);	
		}else{
			if(isSequencial.toLowerCase()=="y"){
				doNextMig(start);
				}
		}
	}, 'json').error(function(data) {
		
		});
}
</script>
</head>






<body>
<div style="width:300px; height:100px; border: 1px solid red;" id="durTime">
	
</div>

<table>
	<tr>
	<th>DOCID</th>
	<th>page</th>
	<th>status</th>
	<th>msg</th>
	</tr>
<tbody id="migLog">
</tbody>
</table>
</body>
</html>