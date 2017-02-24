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
 *  Class Name  : ListTokenNumManage.jsp
 *  Description : 보안토큰 관리자 - 보안토큰 수량관리
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
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String listTitle = (String) request.getAttribute("listTitle");
	String cPageStr = request.getParameter("cPage");
	String sLineStr = request.getParameter("sline");

	int nSize = 0;
	int totalCount = 0;
	
	// Page Navigation variables
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) 
		CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) 
		sLine = Integer.parseInt(sLineStr);
	
	String msgTokenDate	= messageSource.getMessage("list.list.title.tokenDate" , null, langType);
	String msgTokenStore = messageSource.getMessage("list.list.title.tokenStore" , null, langType);
	String msgTokenRelease = messageSource.getMessage("list.list.title.tokenRelease" , null, langType);	
	String msgType = messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgTokenNum = messageSource.getMessage("list.list.title.tokenNum" , null, langType);
 	String msgDistributeNum = messageSource.getMessage("list.list.title.tokenDistributeNum" , null, langType);
	String msgIssueNum = messageSource.getMessage("list.list.title.tokenIssueNum" , null, langType);
	String msgStockNum = messageSource.getMessage("list.list.title.tokenStockNum" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	// 버튼 설정
	String msgLocalIssueBtn = messageSource.getMessage("list.list.button.localIssueBtn" , null, langType);
	String msgStoreBtn = messageSource.getMessage("list.list.button.storeBtn" , null, langType);

	int curPage = CPAGE;	//현재페이지
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript">
/* 코드 구현 부분 */
	
	// 현황 select 변경시
	function changeTokenStatus(statusToken) {
		$('#selTokenStatus').val(statusToken);
	}
	
	// 지역본부 배부 버튼 선택시
	function onLocalIssue() {
		window.open('<%=webUri%>/app/sso/security/tokenIssueProcess.do', '', "width=650px , height=250px, menubar=no, scrollbars=no, status=no, directories=no, toolbar=no, resizable=no, location=no");
	}
	
	// 입고 버튼 선택시
	function onTokenStore() {
		window.open('<%=webUri%>/app/sso/security/tokenStore.do', '', "width=650px , height=350px, menubar=no, scrollbars=no, status=no, directories=no, toolbar=no, resizable=no, location=no");
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<div style="width: 100%; padding-right: 10px;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<!-- select 및 button 구성 시작 -->
 		<tr>
			<td style="padding-right: 5px;">
				<acube:buttonGroup align="right">
					<td>
					<select name="selTokenStatus" id="selTokenStatus" onchange="javascript:changeTokenStatus(this.value);">
						<option value="1"><spring:message code="list.list.select.bothTokenStatus"/></option>
						<option value="2"><spring:message code="list.list.select.issueTokenStatus"/></option>
						<option value="3"><spring:message code="list.list.select.deptTokenStatus"/></option>
					</select>
					</td>
					<acube:space between="button" />
					<acube:menuButton id="localIssueBtn" disabledid="" onclick="javascript:onLocalIssue();" value='<%=msgLocalIssueBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="storeBtn" disabledid="" onclick="javascript:onTokenStore();" value='<%=msgStoreBtn%>'/>
				</acube:buttonGroup>
			</td>
		</tr>
		<!-- select 및 button 구성 종료 -->
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<tr>
				<form name="formList" id="formList" style="margin:0px">
				<table class="table" height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td class="ltb_head" style="width: 250px; height: 36px;" rowspan="2"><%=msgTokenDate%></td>
						<td class="ltb_head" style="width: 350px;" colspan="2"><%=msgTokenStore%></td>
						<td class="ltb_head" style="width: 350px;" colspan="2"><%=msgTokenRelease%></td>
						<td class="ltb_head" style="width: *;" rowspan="2"><%=msgStockNum%></td>
					</tr>
					<tr>
						<td class="ltb_head" style="width: 175px;"><%=msgType%></td>
						<td class="ltb_head" style="width: 175px;"><%=msgTokenNum%></td>
						<td class="ltb_head" style="width: 175px;"><%=msgDistributeNum%></td>
						<td class="ltb_head" style="width: 175px;"><%=msgIssueNum%></td>
					</tr>
				</thead>
				<tbody>
				<%if(totalCount == 0){%>
					<tr>
						<td style="height: 10px; background-color: #ffffff;" colspan="6">
					</tr>
					<tr onmouseover="this.style.backgroundColor='#F2F2F4'" onmouseout="this.style.backgroundColor=''" bgColor="#ffffff">
						<td height="30px" align="center" class="communi_text" style="overflow: hidden; border: 1px solid #c7c7c7;" colSpan="6">
							<%=msgNoData%>				
						</td>
					</tr>
				<%} %>
				</tbody>
				</table>
				</form>
			</tr>
		</tr>
	</table>
</div>
</acube:outerFrame>
</body>
</html>