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
 *  Class Name  : ListTokenIssue.jsp
 *  Description : 보안토큰 관리자 - 발급대장
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
	
	String msgReqDate1	= messageSource.getMessage("list.list.title.reqDate1" , null, langType);
	String msgReqReason1 = messageSource.getMessage("list.list.title.reqReason1" , null, langType);
	String msgDocuFileAdd = messageSource.getMessage("list.list.button.docuFileAdd" , null, langType);	
	String msgReqUser = messageSource.getMessage("list.list.title.reqUser" , null, langType);
	String msgReqDept = messageSource.getMessage("list.list.title.reqDept" , null, langType);
	String msgCancelReason = messageSource.getMessage("list.list.title.cancelReason" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);

	
	// 버튼 설정
	String msgSearchBtn = messageSource.getMessage("list.list.button.search" , null, langType);
	String msgExcelSaveBtn = messageSource.getMessage("list.list.button.excelSaveBtn" , null, langType);
	
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
	// 엑셀저장 버튼 선택시
	function onExcelSave() {
	
	}

	// 검색 버튼 선택시
	function onSearch() {
		document.location.href = '<%=webUri%>/app/sso/security/tokenIssueSearch.do';
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
					<!-- 부서 select -->
					<select name="selDept" id="selDept" onchange="javascript:changeSelDept(this.value);">
						<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
						<option value="1"><spring:message code="list.list.select.selMiss"/></option>
						<option value="2"><spring:message code="list.list.select.selbreakDown"/></option>
						<option value="3"><spring:message code="list.list.select.selNew"/></option>
					</select>
					</td>
					<acube:space between="button" />
					<td>
					<!-- 조직 select -->
					<select name="selOrg" id="selOrg" onchange="javascript:changeSelOrg(this.value);">
						<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
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
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="excelSaveBtn" disabledid="" onclick="javascript:onExcelSave();" value='<%=msgExcelSaveBtn%>'/>
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="searchBtn" disabledid="" onclick="javascript:onSearch();" value='<%=msgSearchBtn%>'/>
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
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<!-- 발급대장 목록 Table -->
						<td height="100%" valign="top" class="communi_text">
						<% 
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("200,200,200,200,200,*");
							acubeList.setColumnAlign("center,center,center,center,center,center");
							
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex, msgReqDate1);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
							
							titleRow.setData(++rowIndex, msgReqReason1);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							titleRow.setData(++rowIndex, msgDocuFileAdd);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgReqUser);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgReqDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgCancelReason);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
					        if(totalCount == 0){
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(true); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	
						%>
						</td>
					</tr>
				</table>
				</form>
			</tr>
		</tr>
	</table>
</div>
</acube:outerFrame>
</body>
</html>