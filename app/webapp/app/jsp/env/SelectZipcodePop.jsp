<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectZipcodePop.jsp 
 *  Description : 우편번호 팝업 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.19 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 5. 19 
 *  @version 1.0 
 */ 
%>
<%
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType); 
	String buttonCancel = messageSource.getMessage("env.option.button.cancel" , null, langType);
	String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
	String noData = messageSource.getMessage("env.deptInfo.msg.nodata" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.zipcode.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		$('#searchKey').bind('keydown', function(event){
			var keyCode = event.which;
			if (keyCode == 13) {
				searchZipcode();
				return false;
			}
		});
	} 

	var gSaveLineObject = g_selector();
	var sColor = "#F2F2F4";		

	function onLineClick(obj){
		selectOneLineElement(obj);
	}

	function selectOneLineElement(obj){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}

	function drawLine(zipcodeList) {
		var tbl = $('#tblzipcode');
		var listLength = zipcodeList.length;
		tbl.empty();
		
		if (listLength < 1) {
			var row = "<tr><td class='ltb_center' style='background-color:#ffffff;'><%=noData%></td></tr>";
			tbl.append(row);
		} else {
			for (var i=0; i<listLength; i++) {
				var id = "zip"+i;
				var zipcodeLine = zipcodeList[i];
				var address = zipcodeLine.sido+" "+zipcodeLine.gugun+" "+zipcodeLine.dong+" "+zipcodeLine.bungi;
				var displayAddr = zipcodeLine.sido+" "+zipcodeLine.gugun+" "+zipcodeLine.dong;
				var row = "<tr id='"+id+"' displayAddr='"+displayAddr+"' bungi='"+zipcodeLine.bungi+"' onclick='onLineClick($(\"#"+id+"\"));' ondblclick='onLineClick($(\"#"+id+"\"));sendOk();' style=\"background-color:#ffffff;\">";
//				row += "<td width='100' class='ltb_center'>"+(zipcodeLine.zipCode).substring(0,3)+"-"+(zipcodeLine.zipCode).substring(3,6)+"</td>";
				row += "<td width='100' class='ltb_center'>"+(zipcodeLine.zipCode)+"</td>";  // 우편번호 형식 수정 jth8172 20120316
				row += "<td width='*' class='ltb_center'>"+address+"</td>";
				row += "</tr>";
				tbl.append(row);	
			}			
		}
	}	

	function searchZipcode(){
		if ($.trim($("#searchKey").val()) == "") {
			alert('<spring:message code="env.zipcode.msg.validate"/>');
			$('#searchKey').focus();
			return;
		}
		var results = null;

		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/listZipcode.do", $('form').serialize(), function(data){
			results = data;
		});
		drawLine(results);			
	}

	function sendOk(){
		if (gSaveLineObject.first() == null) {
			alert('<spring:message code="env.zipcode.msg.validate.zipcode"/>');
			return;
		}
		if (opener != null && opener.setZipCode) {
			opener.setZipCode(gSaveLineObject);
		}
		window.close();
	}

	function closePopup(){
		window.close();
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<td><span class="pop_title77"><spring:message code="env.zipcode.title"/></span></td>
	<form:form modelAttribute="" method="post" name="zipcodeForm" id="zipcodeForm">
	<acube:space between="button_content" table="y"/>
	<acube:tableFrame class="">
	<tr bgcolor="#ffffff">		
		<td width="36" height="25"><nobr><strong><spring:message code="env.zipcode.txt.01"/></strong></nobr></td>
		<td width="90">
			<nobr>&nbsp;<input type="text" name="searchKey" id="searchKey" class="input" style="width:80px;ime-mode:active;"/></nobr>
		</td>
		<td width="65">
			<acube:button onclick="javascript:searchZipcode();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" />
		</td>
		<td width="*"><spring:message code="env.zipcode.txt.02"/></td>		
	</tr>
	</acube:tableFrame>
	<acube:space between="button_content" table="y"/>
	<acube:space between="button_content" table="y"/>
	<acube:tableFrame class="table_grow" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100" class="ltb_head"><spring:message code="env.zipcode.table.01"/></td>
			<td width="*" class="ltb_head"><spring:message code="env.zipcode.table.02"/></td>
		</tr>
	</acube:tableFrame>
	<div style="height:290px; overflow-x:hidden; overflow-y:auto; background-color:#FFFFFF; margin-bottom:10px;">
	<table id="tblzipcode" cellpadding="0" cellspacing="0" width="100%" style="table-layout:fixed;">
		<tbody>
		<tr><td class='ltb_center' style='background-color:#ffffff;'><%=noData%></td></tr>
		</tbody>
	</table>
	</div>
	<acube:buttonGroup align="right">
	<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=buttonSave%>" />
	<acube:space between="button" />
	<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=buttonCancel%>" />
	</acube:buttonGroup>
	</form:form>
</acube:outerFrame>

</body>
</html>