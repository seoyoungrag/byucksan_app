<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
/** 
 *  Class Name  : ListOfficerPop.jsp 
 *  Description : 임원 목록(새창)
 *  Modification Information 
 * 
 *   수 정 일 : 2011.07.26 
 *   수 정 자 : redcomet 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  redcomet
 *  @since 2011. 7. 26 
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
<title><spring:message code="env.option.subtitle.officer.list"/></title>
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
		if(!confirm("<spring:message code='env.user.msg.confirm.add.officer' />")){
			return;
		}
		
		var trUser = gSaveLineObject.first();
		if (trUser == null) {
			alert("<spring:message code='env.user.msg.select.officer.foradd.dept' />");
			return false;
		}
		var user = new Object();
		user.userId 		= trUser.attr('Id');
		user.userName 		= trUser.attr('userName');
		user.deptId	 		= trUser.attr('deptId');
		user.deptName 		= trUser.attr('deptName');
		user.compId 		= trUser.attr('compId');
		user.compName 		= trUser.attr('compName');
		user.positionName 	= trUser.attr('positionName');
		if (opener != null && opener.setUserInfo) {
			var msg = opener.setUserInfo(user);
			if(typeof(msg) !== "undefined"){
				if(msg !== ""){
					alert(msg);
				}else{
					window.close();
				}
			}else{
				window.close();
			}
		}
	}

	function closeWin() {
		window.close();
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.subtitle.officer.list"/></acube:titleBar>
<acube:outerFrame popup="true">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
			<!-- 임원 목록 -->
			<div style="height:223px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
			<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
				<tr>
					<td width="45%" class="ltb_head"><spring:message code="env.option.form.07"/></td>
					<td width="25%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
					<td width="30%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
				</tr>
			</table>	
			<table cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:absolute;left:0px;top:30px;z-index:1;">
				<c:forEach var="vo" items="${officerList}">
					<tr id="${vo.userUID}"  name="${vo.userUID}" userName="${vo.userName}" deptId="${vo.deptID}" 
						deptName="${vo.deptName}" compId="${vo.compID}" compName="${vo.compName}" positionName="${vo.displayPosition}" 
						onClick='onLineClick($("#${vo.userUID}"));' ondblclick='sendOk();' style="background-color:#ffffff;cursor:pointer">
						<td width="45%" class="ltb_center">${vo.deptName}</td>
						<td width="25%" class="ltb_center">${vo.displayPosition}</td>
						<td width="30%" class="ltb_center">${vo.userName}</td>
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
				<acube:menuButton onclick="sendOk();" value="<%=buttonSave%>" />
				<acube:space between="button" />
				<acube:menuButton onclick="closeWin();" value="<%=buttonClose%>" />
			</acube:buttonGroup>
		</td>
	</tr>	
</table>

</acube:outerFrame>

</body>
</html>