<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : SecuTokenDiscard.jsp
 *  Description : 인증서 폐기 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @seepadding: 10px;padding: 10px;padding: 10px;padding: 10px;
 */ 
%>
<% 
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String roleCodes = (String) session.getAttribute("ROLE_CODES"); // 역할 CODE
	
	String listTitle = (String) request.getAttribute("listTitle");
 	String msgEmployeeId = messageSource.getMessage("list.list.title.employeeId" , null, langType);
	String msgEmployeeName = messageSource.getMessage("list.list.title.employeeName" , null, langType);
	String msgConfirm = messageSource.getMessage("list.relay.title.headerCheck" , null, langType);
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); //처리과 문서책임자
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String userName = userProfileVO.getUserName();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
/* 코드 구현 부분 */
	// 사원명 입력 부분 클릭시
	function clearHint() {
		$('#employeeId').val('');
		$('#userName').val('');
	}
	
	// 사원명 입력 후 엔터 키 처리
	$('#userName').live('keydown', function(event) {
		var keyCode = event.which;
		if(keyCode == 13)
			goSearchUserName();			
	});
	
	var sameUsers = '';	// 동명이인 검색 결과 값
	
	// 사원명 입력 후 검색 수행
	function goSearchUserName() {
		var results = "";
		var url = "<%=webUri%>/app/common/sameNameUsers.do";
		$.ajaxSetup({async:false});
		$.getJSON(url, $('#userName').serialize() , function(data){
			results = data;
		});
		
		if(results != '') {
			sameUsers = results;
			if(results.length == 0) {
				alert("<spring:message code='app.alert.msg.56'/>");
				$('#userName').val("");				
			} else if (results.length == 1){
				$('#employeeId').val(results[0].employeeID);
				$('#employeeId').focus();				
			} else {
				var width = 400;
				var height = 360;
				var url = "<%=webUri%>/app/common/sameNameUsers.do?method=2";
				var appDoc = null;
				appDoc = openWindow("sameUserWin", url, width, height); 				
			}
		}else {
			alert("<spring:message code='list.list.msg.noSearchUser'/>");
			$('#userName').val('');
			$('#userName').focus();
			return;
		}
	}
	
	// 동명이인 검색 팝업 호출시 - 사용자 정보 보내기
	function getSameUsers(){
		return sameUsers;
	}
	
	// 동명이인 검색 팝업 - 해당 사용자 선택시 정보 가져오기
	var bPop = false;
	var popMsg = "";
	function setSameUsers(user){
		bPop = true;
		$('#employeeId').val(user.employeeID);
		$('#employeeId').focus();
		bPop = false;
		return popMsg;
	}
	
	// 확인 버튼 선택시
	function onCertDiscard() {
		
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<!-- 절차 단계 view -->
		<tr>
			<td>
				<div style="width: 100%; min-height: 100px;" align="center">
					<div style="width: 100%; border: 5px solid #6ab2da;">
						<div style="width: 150px; float: left; padding-left: 50px;" align="left">
							<img src="<%=webUri%>/app/ref/image/sso/insu_img1.gif">
						</div>
						<div style="float: left; padding-top: 30px; text-align: left;">
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.certDiscardStep1'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.certDiscardStep2'/></div>
						</div>
					</div>
				</div>
			<td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<div id="docinfo" style="padding-top: 30px;">
				<acube:tableFrame>
					<!-- 사원번호 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgEmployeeId%></td>
						<td width="*" class="tb_left">
							<input type="text" id="employeeId" style="width:60%; margin-bottom: 5px; background-color: #fbfbfb" readonly="readonly">
						</td>
					</tr>
					<!-- 사원명 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgEmployeeName%></td>
						<td width="*" class="tb_left">
							<%if(roleCodes.indexOf(roleId11) >= 0) {%>
								<input type="text" id="userName" name="userName" title="<spring:message code = 'list.list.title.inputNameHint'/>" value="<spring:message code = 'list.list.title.inputNameHint'/>" style="width:60%; margin-bottom: 5px;" onclick="clearHint()">
							<%}else {%>
								<input type="text" id="userName" name="userName" title="<spring:message code = 'list.list.title.inputNameHint'/>" value="<%=userName%>" style="width:60%; margin-bottom: 5px; background-color: #fbfbfb" readonly="readonly">
							<%}%>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</td>
		</tr>
	</table>
	<div width="100%" border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top: 20px;">
		<acube:menuButton id="confirmBtn" onclick="javascript:onCertDiscard();" value='<%=msgConfirm%>'/>
	</div>
</acube:outerFrame>
</body>
</html>