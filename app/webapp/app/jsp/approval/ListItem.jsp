<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListItem.jsp 
 *  Description : 안건 선택
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.listitem'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	if (opener != null && opener.getItemCount != null) {
		var itemcount = opener.getItemCount();
		for (var loop = 0; loop < itemcount; loop++) {
			$("#listitem").append("<option value='" + (loop + 1) + "'>" + (loop + 1) + "<spring:message code= 'approval.form.item'/></option>");
		}
	}		
}

function confirmItem() {
	if (opener != null && opener.setItem) {
		opener.setItem($("#listitem option:selected").val());
		window.close();
	}
}

function closeItem() {
	if (confirm("<spring:message code='approval.msg.close.itemlist'/>")) {
		window.close();
	}	
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.listitem'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="40%" class="tb_tit"><spring:message code='approval.title.listitem'/></td>
						<td width="60%" class="tb_left_bg">
							<select id="listitem" name="listitem" class="select_9pt" style="width:100%"></select> 
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="confirmItem();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeItem();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>