<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
/** 
 *  Class Name  : EnvDeptMemberPop.jsp 
 *  Description : 부서원 목록(새창)
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.09 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 9 
 *  @version 1.0 
 */ 
%>
<%
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType); 
	String buttonClose = messageSource.getMessage("env.option.button.close" , null, langType);
	String msgEmpty = messageSource.getMessage("env.empty.text.true" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<style type="text/css">
	
</style>
<script type="text/javascript">
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
	
	function sendOk() {
		var trUser = gSaveLineObject.first();	 
		var user = new Object();
		user.userId 		= trUser.attr('Id');
		user.userName 		= trUser.attr('userName');
		user.deptId	 		= trUser.attr('deptId');
		user.deptName 		= trUser.attr('deptName');
		user.compId 		= trUser.attr('compId');
		user.compName 		= trUser.attr('compName');
		user.positionName 	= trUser.attr('positionName');
		if (opener != null && opener.setUserInfo) {
			opener.setUserInfo(user);
			window.close();
		}	
	}

	function closeWin() {
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
		<tr>
			<td>
			<span class="pop_title77"><spring:message code="env.option.subtitle.06"/></span>
			</td>
		</tr>
		<tr>
			<td>


		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="position:relative;">
			<tr>
				<td>
		
			<!-- 부서원 목록 -->
			<div style="height:223px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
			<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
				<tr>
					<td width="25%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
					<td width="45%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
					<td width="30%" class="ltb_head"><spring:message code="env.option.form.23"/></td>
				</tr>
			</table>	
			<table cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:absolute;left:0px;top:30px;z-index:1;">
				<c:forEach var="vo" items="${listUserVO}">
					<tr id="${vo.userUID}"  name="${vo.userUID}" userName="${vo.userName}" deptId="${vo.deptID}" 
						deptName="${vo.deptName}" compId="${vo.compID}" compName="${vo.compName}" positionName="${vo.displayPosition}" 
						onClick='onLineClick($("#${vo.userUID}"));' ondblclick='sendOk();' style="background-color:#ffffff;">
						<td width="25%" class="ltb_center">${vo.displayPosition}</td>
						<td width="45%" class="ltb_center">${vo.userName}</td>
						<td width="30%" class="ltb_center"><c:if test="${vo.existence eq false}"><%=msgEmpty%></c:if></td>
					</tr>
				</c:forEach>
			</table>
			
			</div>
			
		</td>
	</tr>
	<tr><td height="5"></td></tr>
	<tr>
		<td>
			<acube:buttonGroup>
				<acube:button onclick="sendOk();" value="<%=buttonSave%>" />
				<acube:space between="button" />
				<acube:button onclick="closeWin();" value="<%=buttonClose%>" />
			</acube:buttonGroup>
		</td>
	</tr>	
</table>
</td>
</tr>
</table>
</acube:outerFrame>

</body>
</html>