<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : InsertFormEnv.jsp 
 *  Description : 개인정보 관리 - 사진, 서명 이미지 수정 팝업 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.30 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 30 
 *  @version 1.0 
 */ 
%>
<%
	String buttonFile = messageSource.getMessage("env.option.button.file" , null, langType);
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
	String buttonClose = messageSource.getMessage("env.option.button.close" , null, langType);
	String imagePath = webUri + "/app/ref/image";
	String compId = (String) session.getAttribute("COMP_ID");
	int limitSize = 32*1024;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<style type="text/css">
	.range {display:inline-block;width:140px;height:25px; !important;}
</style>
<script type="text/javascript">	
$(document).ready(function() { initialize(); });
	function initialize() {
		initializeFileManager();
	}

	function appendAttachEnv() {
		var filelist = FileManager.uploadfile("image");

		if (filelist.length > 0) {

			if (<%=limitSize%> > filelist[0].size) {
				$('#'+envType+'LocalFilePath').val(filelist[0].localpath);
				$('#fileName').val(filelist[0].filename);
				$('#fileDisplayName').val(filelist[0].displayname);
				preViewLogo.innerHTML = "<img src='<%=webUri%>/app/env/admin/imgPreView.do?fileName="+filelist[0].filename+"' width='100' height='100' border='1' />";
			
			} else {	
				alert('<spring:message code="env.option.msg.validate.limitsize"/>');
				return false;			
			}
		}
	}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.25" /></acube:titleBar>

<acube:outerFrame popup="true">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="insertCLSForm" id="insertCLSForm">
		<tr>
			<acube:space between="title_button" />
		</tr>

		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
			
				<table width="100%" class='td_table' border='0' cellpadding='0' cellspacing='1'>
					<tr bgcolor="#ffffff">
						<td width="85" class="tb_tit">
							<spring:message code="env.option.form.19"/><spring:message code='common.title.essentiality'/>
						</td>
						<td width="*" class="tb_left_bg">
							<input type="text" name="FET001LocalFilePath" id="FET001LocalFilePath" class="input" style="width:100%;" readOnly />
						</td>
						<td width="80" align="center">
							<acube:button onclick="appendAttachEnv();return(false);" value="<%=buttonFile%>" type="4" class="gr" />
						</td>
					</tr>				
				</table>
				<div align="right" style="width:100%;align:right;padding:5px 0 10px 0;font-size:12px;color:blue;">
					<spring:message code='common.title.essentiality'/>
					<font color="#999999"><spring:message code="env.option.txt.06"/></font>
				</div>
				<div style="width:100%;height:100px;border:solid 1px #e3e2e3;background-color:#f1f1f2;padding:10px 10px 10px 10px;">
					<span id="preViewLogo" style="width:100px;height:100px;border:solid 1px #e7e7e7;">
						<img src="<%=imagePath%>/common/n_no_img.gif" border="0" />						
					</span>
				</div>
				
				<div style="width:100%;padding-top:5px;">
					<acube:buttonGroup>
						<acube:button onclick="javascript:insertFormEnv('FET001')" value='<%=buttonSave%>' type="2" class="gr" />
						<acube:space between="button" />
						<acube:button onclick="javascript:window.close();" value='<%=buttonClose%>' type="2" class="gr" />
					</acube:buttonGroup>
				</div>
				
			</td>
		</tr>
		<input type="hidden" name="fileName" id="fileName" value="" />
		<input type="hidden" name="fileDisplayName" id="fileDisplayName" value="" />
		</form:form>
	</table>

</acube:outerFrame>

</body>
</html>