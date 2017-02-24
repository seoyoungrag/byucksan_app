<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
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
 *  Class Name  : TokenCacheSetting.jsp
 *  Description : 캐시설정 
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

 	String msgTokenCacheSetting = messageSource.getMessage("list.list.title.tokenCacheSetting" , null, langType);
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

	// 확인 버튼 선택시
	function cacheConfirm() {
		
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
						<div style="float: left; padding-top: 10px; text-align: left;">
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.cacheSettingStep1'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.cacheSettingStep2'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.cacheSettingStep3'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold; padding-bottom: 3px;"><img src="<%=webUri%>/app/ref/image/sso/bull_pw.gif"> <spring:message code='list.list.title.cacheSettingStep4'/></div>
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
			<!-- 인증서 캐시 사용 -->
			<div id="docinfo">
				<acube:tableFrame>
					<!-- 라디오 버튼 : '예' -->
					<tr bgcolor="#ffffff">
						<td width="20%" class="tb_tit" style="text-align: center;" rowspan="2"><%=msgTokenCacheSetting%></td>
						<td width="80%" class="tb_left">
							<div style="width: 60px; float: left;">
								<input type="radio" id="radio1" name="cacheUse"><b><spring:message code="list.list.title.cacheRadioYes"/> </b>
							</div>
							<div style="width: 450px; float: left; padding-top: 4px;">
								<spring:message code="list.list.title.cacheRadiotxt1"/>
							</div>
						</td>
					</tr>
					<!-- 라디오 버튼 : '아니오' -->
					<tr bgcolor="#ffffff">
						<td width="80%" class="tb_left">
							<div style="width: 60px; float: left;">
								<input type="radio" id="employeeId" name="cacheUse" checked="checked"><b><spring:message code="list.list.title.cacheRadioNo"/></b>
							</div>
							<div style="width: 450px; float: left; padding-top: 4px;">
								<spring:message code="list.list.title.cacheRadiotxt2"/>
							</div>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</td>
		</tr>
	</table>
	<div width="100%" border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top: 20px;">
		<acube:menuButton id="confirmBtn" onclick="javascript:cacheConfirm();" value='<%=msgConfirm%>'/>
	</div>
</acube:outerFrame>
</body>
</html>