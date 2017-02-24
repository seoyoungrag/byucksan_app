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
 *  Class Name  : TokenIssueProcess.jsp
 *  Description : 보안토큰 관리자 - 보안토큰수량관리 - 지역본부 배부
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

 	String msgTotalNum = messageSource.getMessage("list.list.title.tokenTotalNum" , null, langType);
	String msgIssueNum = messageSource.getMessage("list.list.title.tokenIssueNum" , null, langType);
	String msgIssueDept = messageSource.getMessage("list.list.title.tokenIssueDept" , null, langType);
	
	// 버튼 텍스트 설정
	String msgSaveBtn = messageSource.getMessage("list.list.button.excelSave" , null, langType);
	String msgCloseBtn = messageSource.getMessage("list.list.button.close", null, langType);
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

	// 배부부서 select 변경시
	function changeSelDept(deptId) {
		$('#selDept').val(deptId);
	}
	
	// 저장 버튼 선택시
	function onIssueSave() {
		if($('#issueNum').val() == '') {
			alert("<spring:message code = 'list.list.alert.noIssueNum'/>");
			return false;
		}else if($('#selDept').val() == 'none') {
			alert("<spring:message code = 'list.list.alert.noIssueDept'/>");
			return false;
		}else {
			if(confirm("<spring:message code = 'list.list.alert.tokenIssue'/>")){
				top.location.reload();
				window.close();
			}
			else
				return false;
		}
	}
	
	// 닫기 버튼 선택시
	function onPopClose() {
		window.close();
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
		<tr>
			<td>
				<acube:buttonGroup align="right">
					<acube:space between="button" />
					<acube:menuButton id="saveBtn" disabledid="" onclick="javascript:onIssueSave();" value='<%=msgSaveBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="closeBtn" disabledid="" onclick="javascript:onPopClose();" value='<%=msgCloseBtn%>'/>
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<div id="docinfo">
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<!-- 전체보유수량 -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgTotalNum%></td>
						<!-- 배부수량 -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgIssueNum%></td>
						<!-- 배부부서 -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgIssueDept%></td>
					</tr>
					<tr bgcolor="#ffffff">
						<!-- 전체보유수량 -->
						<td width="250px;" style="text-align: center;">
							<div id="tokenTotalNum"></div>
						</td>
						<!-- 배부수량 -->
						<td width="250px;" style="text-align: center;">
							<input type="text" id="issueNum" name="issueNum" style="width: 60%;"/>
						</td>
						<!-- 배부부서 select -->
						<td width="250px;" style="text-align: center;">
							<select name="selDept" id="selDept" onchange="javascript:changeSelDept(this.value);">
								<option value="none"><spring:message code="list.list.select.tokenInfoOrgnone"/></option>
								<option value="1"><spring:message code="list.list.select.tokenInfoOrg1"/></option>
								<option value="2"><spring:message code="list.list.select.tokenInfoOrg2"/></option>
								<option value="3"><spring:message code="list.list.select.tokenInfoOrg3"/></option>
								<option value="4"><spring:message code="list.list.select.tokenInfoOrg4"/></option>
								<option value="5"><spring:message code="list.list.select.tokenInfoOrg5"/></option>
								<option value="6"><spring:message code="list.list.select.tokenInfoOrg6"/></option>
								<option value="7"><spring:message code="list.list.select.tokenInfoOrg7"/></option>
								<option value="8"><spring:message code="list.list.select.tokenInfoOrg8"/></option>
								<option value="9"><spring:message code="list.list.select.tokenInfoOrg9"/></option>
								<option value="10"><spring:message code="list.list.select.tokenInfoOrg10"/></option>
								<option value="11"><spring:message code="list.list.select.tokenInfoOrg11"/></option>
								<option value="12"><spring:message code="list.list.select.tokenInfoOrg12"/></option>
								<option value="13"><spring:message code="list.list.select.tokenInfoOrg13"/></option>
								<option value="14"><spring:message code="list.list.select.tokenInfoOrg14"/></option>
								<option value="15"><spring:message code="list.list.select.tokenInfoOrg15"/></option>
							</select>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>