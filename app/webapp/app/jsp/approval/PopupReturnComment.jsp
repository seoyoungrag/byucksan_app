<% 
/** 
 *  Class Name  : PopupRejectComment.jsp 
 *  Description : 반송및접수의견조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.31
 *   수 정 자 : 정태환 
 *   수정내용 : 신규 
 * 
 *  @author  정태환
 *  @since 2011. 3. 31 
 *  @version 1.0 
 */ 
%> 				 
<%@ page import="java.util.Locale" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<% 
	response.setHeader("pragma","no-cache");	

	//반송의 경우 기존 반송의견을 가져와서 보여준다.
	String popComment = CommonUtil.nullTrim((String) request.getAttribute("popComment"));
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<title><spring:message code="approval.enforcereturnpopup.title" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
function closePopup() {
	//창을 닫는다.
	self.close();
	
}
</script>
</head>
<body topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>

<acube:titleBar><spring:message code="approval.enforcereturnpopup.title" /></acube:titleBar>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->

<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" height="100%">
		<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="170" valign="top" ><!------ 리스트 Table S --------->
					<textarea id="popComment" name="popComment" cols="60" rows="14" style="width:380px; height:160px" readonly><%=popComment %></textarea>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</acube:tableFrame>

<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->

<acube:buttonGroup align="right">
<% String msg = messageSource.getMessage("approval.enforceinfo.button.ok" , null, langType);  %>
<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=msg%>" />
</acube:buttonGroup>

</acube:outerFrame>

 
</body>
</html>