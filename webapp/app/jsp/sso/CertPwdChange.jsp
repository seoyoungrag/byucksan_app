<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

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
 *  Class Name  : CertPwdChange.jsp
 *  Description : 인증서 비밀번호 변경
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<% 
	String listTitle = (String) request.getAttribute("listTitle");

 	String msgCertPwdChange1 = messageSource.getMessage("list.list.title.certPwdChange1" , null, langType);
	String msgCertPwdChange2 = messageSource.getMessage("list.list.title.certPwdChange2" , null, langType);
	String msgCertPwdChange3 = messageSource.getMessage("list.list.title.certPwdChange3" , null, langType);
	String msgConfirm = messageSource.getMessage("list.relay.title.headerCheck" , null, langType);
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

	// 확인 버튼 선택시
	function certPwdChange() {
		if($('#certPwdChange1').val() == '') {
			alert("<spring:message code = 'list.list.alert.existPwd'/>");
			return false;
		}else if($('#certPwdChange2').val() == '') {
			alert("<spring:message code = 'list.list.alert.inputNewPwd'/>");
			return false;
		}else if($('#certPwdChange2').val() != $('#certPwdChange3').val()) {
			alert("<spring:message code = 'list.list.alert.inputReNewPwd'/>");
			return false;
		}else {
			
		}
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
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.certPwdChangeStep1'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.certPwdChangeStep2'/></div>
						</div>
					</div>
				</div>
			<td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td style="padding-top: 30px;">
			<form name="formChangePwd" id="formChangePwd" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<!-- 기존의 보안토큰 비밀번호를 입력하세요 -->
					<tr bgcolor="#ffffff">
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgCertPwdChange1%></td>
						<td width="*" class="tb_left">
							<input type="password" id="certPwdChange1" style="width:60%; margin-bottom: 5px;" maxlength="20">
						</td>
					</tr>
					<!-- 새로운 보안토큰 비밀번호를 입력하세요 -->
					<tr bgcolor="#ffffff">
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgCertPwdChange2%></td>
						<td width="*" class="tb_left">
							<input type="password" id="certPwdChange2" style="width:60%; margin-bottom: 5px;" maxlength="20">
						</td>
					</tr>
					<!-- 새로운 보안토큰 비밀번호를 다시 입력하세요 -->
					<tr bgcolor="#ffffff">
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgCertPwdChange3%></td>
						<td width="*" class="tb_left">
							<input type="password" id="certPwdChange3" style="width:60%; margin-bottom: 5px;" maxlength="20">
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</form>
			</td>
		</tr>
	</table>
	<div width="100%" border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top: 20px;">
		<acube:menuButton id="confirmBtn" onclick="javascript:certPwdChange();" value='<%=msgConfirm%>'/>
	</div>
</acube:outerFrame>
</body>
</html>