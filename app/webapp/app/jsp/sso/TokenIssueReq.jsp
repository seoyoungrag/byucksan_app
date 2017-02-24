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
 *  Class Name  : TokenIssueReq.jsp
 *  Description : 보안토큰 신청 - 발급신청 
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

 	String msgReqDate = messageSource.getMessage("list.list.title.reqDate" , null, langType);
	String msgReqReason = messageSource.getMessage("list.list.title.reqReason" , null, langType);
	String msgDocuFileAdd = messageSource.getMessage("list.list.button.docuFileAdd" , null, langType);
	String msgReqBtn = messageSource.getMessage("list.list.subTitle.stampSealRegist.002.08" , null, langType);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
	$(document).ready(function() {
		var now = new Date();
		var nowMonth = now.getMonth() + 1;
		if(nowMonth.toString().length == 1)
			nowMonth = '0' + nowMonth;
		else if(nowMonth == '13')
			nowMonth = '01';
		
		var nowTime = now.getFullYear() + '-' + nowMonth + '-' + now.getDate();
		
		$('#reqDate_txt').html(nowTime);
		$('#reqDate').val(nowTime);
	});
	
	// 신청버튼 선택시
	function tokenRequest() {
		if($('#docuPath').val() == ''){
			alert("<spring:message code = 'list.list.alert.noDocuPath'/>");
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
		<tr align="right">
			<td style="padding-right: 5px;"><acube:menuButton id="tokenReqBtn" onclick="javascript:tokenRequest();" value='<%=msgReqBtn%>'/><td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<form name="formReq" id="formReq" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<!-- 신청일자 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgReqDate%></td>
						<td width="*" class="tb_left">
							<div id="reqDate_txt" style="text-align: left;"></div>
							<input type="hidden" id="reqDate" name="reqDate">
						</td>
					</tr>
					<!-- 신청사유 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgReqReason%></td>
						<td width="*" class="tb_left">
							<div style="width: 250px; float: left;">
								<input type="radio" id="radio1" name="reqType" value="1" style="float: left;" checked="checked"><spring:message code="list.list.title.reqReasonRadio1"/>
								<input type="radio" id="radio2" name="reqType" value="2"><spring:message code="list.list.title.reqReasonRadio2"/>
								<input type="radio" id="radio3" name="reqType" value="3"><spring:message code="list.list.title.reqReasonRadio3"/>
							</div>
							<div style="width: 550px;">
								<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt1"/></span><br />
								<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt2"/></span> <span style="color: red; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt3"/></span><span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt4"/></span><br />
								<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt5"/></span><br />
								&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt6"/></span><br />
								&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt7"/></span>
							</div>
						</td>
					</tr>
					<!-- 양식첨부 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><acube:menuButton id="searchEasyEnf" onclick="javascript:TokenRequest();" value='<%=msgDocuFileAdd%>'/>
						<td width="*" class="tb_left">
							<input type="text" id="docuPath" name="docuPath" style="width: 60%;" readonly="readonly"><br />
							<span style="color: blue; font-weight: bold;"><spring:message code="list.list.title.reqReasontxt8"/></span> <br />
							<span><img src="<%=webUri%>/app/ref/image/sso/att_hwp.gif"><a href="<%=webUri%>/app/ref/image/sso/HSM발급신청서.zip" target="_blank"><spring:message code="list.list.title.reqReasontxt9"/></a></span>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</form>
			</td>
		</tr>
	</table>
	<div style="width: 100%; min-height: 100px; margin-top: 20px;" align="center">
		<!-- 발급절차 이미지 view -->
		<img alt="<spring:message code="list.list.title.issueStep"/>" src="<%=webUri%>/app/ref/image/sso/stepView.jpg"/>
	</div>
</acube:outerFrame>
</body>
</html>