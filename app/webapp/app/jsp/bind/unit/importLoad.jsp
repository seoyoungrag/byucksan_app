<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.common.util.BindUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.unit.import.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<script LANGUAGE="JavaScript"><!--

	var webUri = '<%=webUri%>';
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";
	
	window.onload = init;
	
	function init() {
		initializeFileManager();
	}
	
	// 찾기 (파일첨부)
	function attachFormFile() {
		
		var filelist = FileManager.uploadfile();

        if(filelist.length > 0) {
    		$("#filePath").val(filelist[0].localpath);
    		$("#fileName").val(filelist[0].filename);
        }		
	}
	
	function sendOk() {
		var form = document.getElementById('form');
		
		var fileName =$.trim($('#fileName').val());
		if(fileName == null || fileName == '') {
			alert("<spring:message code='bind.msg.unit.file.select'/>");
			return;
		}
		
		var formData = $('#form').serializeArray();
		$.ajax({
			url : '<%=webUri%>/app/bind/unit/import/load.do',
			type : 'POST',
			dataType : 'json',
			data : {
				fileName : fileName
			},
			success : function(data) {
				if(data.success) {
					var f = opener.document.listForm;
					f.cPage.value = 1;
					f.searchValue.value = "";
					f.method = 'POST';
					f.submit();
					
					alert("<spring:message code='bind.msg.add.completed'/>");
					window.close();
				} else {
					alert("<spring:message code='bind.msg.error'/>");
				}
			},
			error : function(data) {
				alert("<spring:message code='bind.msg.error'/>");
			}
		});	
	}

--></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.unit.import.list" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b><spring:message code="bind.obj.unit" /></b>
			</td>
			
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<table width="100%" border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" valign="top">

								<!--------------------------------------------본문 Table S -------------------------------------->
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
										<form id="form" method="POST" action="<%=webUri%>/app/bind/unit/create.do">
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
											<!-- 단위업무명 --> 
												<td width="20%" class="tb_tit" nowrap="nowrap">수동전송 <spring:message code='common.title.essentiality'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td width="90%"><input type="text" class="input_read" id="filePath" name="filePath" readOnly style="width:100%"></td>
															<input type="hidden"  name="fileName" id="fileName" value="" /> 
															<td class="tb_left_bg">
											                    <acube:buttonGroup>
											                    <acube:button  type="4" onclick="javascript:attachFormFile();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
											                    </acube:buttonGroup>
												            </td>
														</tr>
													</table>
												</td>
											</tr>
											</acube:tableFrame>
										</form>
										</td>
									</tr>
								
									<tr>
										<acube:space between="title_button" />
									</tr>
									<!-------내용조회  E --------->
							
									<!-------여백 H5  S --------->
									<tr>
										<acube:space between="button_content" />
									</tr>
									<!-------여백 H5  E --------->
									<tr>
										<td>
											<!-------기능버튼 Table S --------->
											<div id="bind_unit_add" style="display:block">
											<acube:buttonGroup>
												<acube:button onclick="javascript:sendOk();return false;" value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
												<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("appcom.button.close", null, langType) %>' type="2" class="gr" />
											</acube:buttonGroup>
											</div>
											<!-------기능버튼 Table E --------->
										</td>
									</tr>
								</table>
								<!--------------------------------------------본문 Table E -------------------------------------->
							
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>

</body>
</html>
