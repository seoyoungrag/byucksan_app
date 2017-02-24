<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ include file="/app/jsp/common/adminheader.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
    int totalCount = 0;
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE>문서관리 컨버전</TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<script LANGUAGE="JavaScript">

	function init() {
		var f = document.listForm;
		f.company.value = '${compId}';
		f.searchYear.value = '${year}';
	}

	function changeCompany(compId) {
		var f = document.listForm;
		f.company.value = compId;
		f.submit();
	}
	
	function changeSearchYear(year) {
		var f = document.listForm;
		f.searchYear.value = year;
		f.submit();
	}
	
	function goDetailPage(year, month) {
		document.location.href = '<%=webUri%>/app/convert/detail.do?searchYear=' + year + '&searchMonth=' + month;
	}

	function execute(month) {
		var f = document.listForm;

		var msg = '${year}' + '년 ' + month + '월 컨버전을 실행하시겠습니까?';
		if(month == null) {
			msg = '${year}' + '년 컨버전을 실행하시겠습니까?';
		}
		
		$.confirm({
			'title'		: '컨버전',
			'message'	: msg,
			'buttons'	: {
				'Yes'	: {
					'action': function(){
						$.ajax({
							url : '<%=webUri%>/app/convert/execute.do',
							type : 'POST',
							dataType : 'json',
							data : {
								compId : '${compId}',
								year : '${year}',
								month : month
							},
							success : function(data) {
								if(data.success) {
									if(month == null) {
										alert('${year}' + '년 컨버전이 실행되었습니다.');
									} else {
										alert('${year}' + '년 ' + month + '월 컨버전이 실행되었습니다.');
									}
									
								} else {
									alert(data.reason);
								}
							},
							error : function(data) {
								alert("<spring:message code='bind.msg.error'/>");
							}
						});
					}
				},
				'No'	: {
					'action': function(){
						return;
					}	
				
				}
			}
		});
	}


	function convert() {
		var f = document.listForm;
		var docId = f.docId.value;
		if(docId.trim() == '') {
			alert('문서 아이디를 입력하세요.');
			return;
		}
		var usingType = 'DPI001';
		
		if(docId.substring(0, 3) == 'APP') {
			usingType = 'DPI001';
		} else if(docId.substring(0, 3) == 'ENF') {
			usingType = 'DPI002';
		}

		var msg = '컨버전을 실행하시겠습니까?';
		
		$.confirm({
			'title'		: '컨버전',
			'message'	: msg,
			'buttons'	: {
				'Yes'	: {
					'action': function(){
						$.ajax({
							url : '<%=webUri%>/app/convert/document.do',
							type : 'POST',
							dataType : 'json',
							data : {
								compId : '${compId}',
								docId : docId,
								usingType : usingType
							},
							success : function(data) {
								if(data.success) {
									alert('컨버전이 완료되었습니다.');
								} else {
									alert(data.reason);
								}
							},
							error : function(data) {
								alert("<spring:message code='bind.msg.error'/>");
							}
						});
					}
				},
				'No'	: {
					'action': function(){
						return;
					}	
				
				}
			}
		});
	}

	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/convert/list.do'>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar>문서관리 컨버전</acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<table width="400" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100" class="ltb_left">
							회사 : <form:select id="company" name="company" path="company" onchange="javascript:changeCompany(this.value);" items="${company}" />
						</td>
						<td class="ltb_left">
							연도 : <form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<table style='' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td>
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="communi_text">
										<table border="0" cellpadding="0" cellspacing="1" class="table">
											<tr bgcolor="#FFFFFF">
												<td class="ltb_head" style="text-overflow:ellipsis; overflow:hidden; width:150;"><nobr>Month</nobr></td>
												<td class="ltb_head" style="text-overflow:ellipsis; overflow:hidden; width:150;"><nobr>Count</nobr></td>
												<td class="ltb_head" style="text-overflow:ellipsis; overflow:hidden; width:100;"><nobr>&nbsp;</nobr></td>
											</tr>
											
											<c:forEach items="${rows}" var="row" varStatus="seq">
											<tr bgcolor="#FFFFFF" onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
												<td class="ltb_center" style="height:25px; text-overflow:ellipsis; overflow:hidden;">
													<a href="#" onClick="javascript:goDetailPage('${year}', '${row.month}');">${row.month}</a></td>
												<td class="ltb_right" style="height:25px; text-overflow:ellipsis; overflow:hidden;">
													<a href="#" onClick="javascript:goDetailPage('${year}', '${row.month}');"><fmt:formatNumber value="${row.count}" type="NUMBER" groupingUsed="true" /></a>
												</td>
												<td class="ltb_center" style="height:25px; text-overflow:ellipsis; overflow:hidden;">
												<!-- <a href='#' onClick="javascript:execute('${row.month}')">실행</a> -->
												</td>
											</tr>
											
											<c:if test="${fn:length(rows) == seq.count}">
											<tr bgcolor="#EFEFEF" onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
												<td class="ltb_center" style="height:25px; text-overflow:ellipsis; overflow:hidden;">합계</td>
												<td class="ltb_right" style="height:25px; text-overflow:ellipsis; overflow:hidden;">
													<fmt:formatNumber value="${row.totalCount}" type="NUMBER" groupingUsed="true" />
												</td>
												<td class="ltb_center" style="height:25px; text-overflow:ellipsis; overflow:hidden;">
													<!-- <a href='#' onClick="javascript:execute()">실행</a> -->
												</td>
											</tr>
											</c:if>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table style='' border='0' cellspacing='0' cellpadding='0'>
				<tr height="30">
					<td class="ltb_left">문서 아이디</td>
					<td width="10" class="ltb_left">:</td>
					<td><input type="text" class="input" name="docId" size="50"/>&nbsp;</td>
					<td><input type="button" onClick="javascript:convert();" value="실행"/></td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="30"></td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</body>
</html>