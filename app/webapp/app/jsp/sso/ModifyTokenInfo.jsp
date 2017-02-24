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
 *  Class Name  : ModifyTokenInfo.jsp
 *  Description : 보안토큰 관리자 - 관리대장(정보수정)
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
 	String msgProductName = messageSource.getMessage("list.list.title.productName" , null, langType);
	String msgHandle = messageSource.getMessage("list.list.title.handle" , null, langType);
	String msgHandleDept = messageSource.getMessage("list.list.title.handleDept" , null, langType);
	String msgUse = messageSource.getMessage("list.list.title.use" , null, langType);
	
	// 버튼 설정
	String msgModifyBtn = messageSource.getMessage("list.list.button.modify" , null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList" , null, langType);
	
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
	
	// 제품명 select 변경시
	function changeProduct(productName) {

	}
	
	// 취급자 입력 부분 클릭시
	function searchHandler() {
		window.open('<%=webUri%>/app/sso/security/tokenHandlerSearch.do', '', "width=450px , height=250px, menubar=no, scrollbars=no, status=no, directories=no, toolbar=no, resizable=no, location=no");
	}
	
	// 수정 버튼 선택시
	function onCertModify() {
		alert("<spring:message code = 'list.list.alert.tokenMod'/>");
		history.back();
	}
	
	// 목록 버튼 선택시
	function onCertList() {
		history.back();
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
					<acube:menuButton id="modifyBtn" onclick="javascript:onCertModify();" value='<%=msgModifyBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="ListBtn" onclick="javascript:onCertList();" value='<%=msgListBtn%>'/>
					<acube:space between="button" />
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
					<!-- 제품명 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgProductName%></td>
						<td width="*" class="tb_left">
							<select name="productName" id="productName" onchange="javascript:changeProduct(this.value);">
								<option value="ikey2032"><spring:message code="list.list.select.ikey2032"/></option>
								<option value="<spring:message code='list.form.etc'/>"><spring:message code="list.form.etc"/></option>
							</select>
						</td>
					</tr>
					<!-- 취급자 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandle%></td>
						<td width="*" class="tb_left">
							<input type="text" id="handleUser" name="handleUser" value="" style="width:60%; margin-bottom: 5px;" onclick="searchHandler()">
						</td>
					</tr>
					<!-- 취급부서 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandleDept%></td>
						<td width="*" class="tb_left">
							<input type="text" id="employeeId" style="width:40%; margin-bottom: 5px;" readonly="readonly">
						</td>
					</tr>
					<!-- 용도 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgUse%></td>
						<td width="*" class="tb_left">
							<input type="radio" id="useType" value="<spring:message code = 'list.list.title.inputNameHint'/>" checked="checked"/><spring:message code="list.list.title.useWork"/>
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