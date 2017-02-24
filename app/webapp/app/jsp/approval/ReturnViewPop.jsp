<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.style.*" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%
/** 
 *  Class Name  : ReturnViewPop.jsp
 *  Description : 반려함 전달 팝업창 
 *  Modification Information 
 * 
 *   수 정 일 : 2015.09.11 
 *   수 정 자 : CSH
 *   수정내용 : 
 * 
 *  @author  CSH
 *  @since 2015. 09. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
// 	response.setHeader("pragma","no-cache");

	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	
	AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
	String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");
	
	String docId = (String) request.getParameter("docId");
	String askType = (String) request.getParameter("askType");
	String lobCode = appCode.getProperty("LOB051", "LOB051", "LOB");
	
	if ( askType.contains("ART04") ) {
		askType = messageSource.getMessage("tgw_app_option.OPT133", null, langType);
	} else if ( askType.contains("ART08") ) {
		askType = messageSource.getMessage("tgw_app_option.OPT134", null, langType);
	} else if ( askType.contains("ART09") ) {
		askType = messageSource.getMessage("tgw_app_option.OPT135", null, langType);
	}
	
	String returnApproveMsg = messageSource.getMessage("list.list.alert.returnApprove", null, langType).replace("%s", askType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.invest.union'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
	}
	
	function nextApprove() {
		$.post("<%=webUri%>/app/approval/nextAppDoc.do", $("#appDocForm").serialize(), function(data){
			alert(data.message);
			closeReturn();
			opener.location.reload();
		}, 'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.msg.fail.return'/>");
			}
		});
	}
	
	function returnApprove() {
		$.post("<%=webUri%>/app/approval/resendDeptAppDoc.do", $("#appDocForm").serialize(), function(data){
			alert(data.message);
			closeReturn();
			opener.location.reload();
		}, 'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.msg.fail.return'/>");
			}
		});
	}
	
	function closeReturn() {
		window.close();
	}
</script>
</head>
<body onunload="closeReturn();">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
<%-- 			<td><acube:titleBar><spring:message code='list.list.title.headerSenderChoice'/></acube:titleBar></td> --%>
			<td>
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td width='39'><img src='<%=imageHome%>/title1.jpg' width='39' height='29'></td>
						<td background='<%=imageHome%>/titlebg.gif'>
							<table border='0' cellspacing='0' cellpadding='0'>
								<tr>
									<td class='title'><spring:message code='list.list.title.headerSenderChoice'/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeReturn();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table cellpadding="2" cellspacing="1" width="100%" class="table_body">
					<tr>
						<td class="ltb_head_normal">
							<span style="cursor: pointer;" onclick="nextApprove();return(false);" title="<spring:message code='list.list.alert.nextApprove' />">
								<spring:message code="list.list.select.nextApprove" />
							</span>
						</td>
					</tr>
					<tr>
						<td class=ltb_head_normal>
							<span style="cursor: pointer;" onclick="returnApprove();return(false);" title="<%=returnApproveMsg%>">
								<spring:message code="list.list.select.returnApprove" />
							</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<form id="appDocForm" method="post" name="appDocForm">
		<input id="docId" name="docId" type="hidden" value="<%=docId%>"/><!-- 문서ID -->
		<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
	</form>
</acube:outerFrame>
</body>
</html>