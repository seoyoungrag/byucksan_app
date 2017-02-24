<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList"%>
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
 *  Class Name  : ListSecuTokenInfo.jsp
 *  Description : 보안토큰정보 리스트 
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
	response.setHeader("pragma","no-cache");	

	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String roleCodes = (String)session.getAttribute("ROLE_CODES"); // 역할 CODE
	String listTitle = (String) request.getAttribute("listTitle");
	String cPageStr = request.getParameter("cPage");
	String sLineStr = request.getParameter("sline");
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); //처리과 문서책임자
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

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
	
	String msgManageNum	= messageSource.getMessage("list.list.title.manageNum" , null, langType);
	String msgProductName = messageSource.getMessage("list.list.title.productName" , null, langType);
	String msgRegisterDate = messageSource.getMessage("list.list.title.registerDate" , null, langType);	
	String msgHandle = messageSource.getMessage("list.list.title.handle" , null, langType);
	String msgHandleDept = messageSource.getMessage("list.list.title.handleDept" , null, langType);
	String msgUse = messageSource.getMessage("list.list.title.use" , null, langType);
	String msgCertificationIssue = messageSource.getMessage("list.list.title.certificationIssue" , null, langType);
	String msgDiscardYN = messageSource.getMessage("list.list.title.discardYN" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	// 버튼 텍스트 설정
	String msgTokenRegBtn = messageSource.getMessage("list.list.button.tokenRegBtn" , null, langType);
	String msgTokenDisBtn = messageSource.getMessage("list.list.button.tokenDisBtn" , null, langType);
	String msgTokenDetailBtn = messageSource.getMessage("list.list.button.tokenDetailBtn" , null, langType);
	String msgSearchBtn = messageSource.getMessage("list.list.button.search" , null, langType);
	String msgPrintBtn = messageSource.getMessage("list.list.button.print" , null, langType);
	
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
	// 조직 select
	function changeSelOrg(orgId) {
	
	}

	// 부서 select
	function changeSelDept(deptId) {
		
	}
	
	// 발급 및 미발급 여부 select
	function changeSelIssue(issueYN) {
		
	}
	
	// 보안토큰 등록 버튼 선택시
	function onTokenReg() {
		document.location.href = '<%=webUri%>/app/sso/security/secuTokenReg.do';
	}
	
	// 폐기 버튼 선택시
	function onTokenDiscard() {
		var chkCnt = 0;
		chkCnt  = $(".communi_text input:checked").length;
		
		// 체크 항목이 없는 경우
		if(chkCnt == 0){
			alert("<spring:message code = 'list.list.msg.noSelectToken'/>");
			return false;
		}else if (chkCnt > 1){
			alert("<spring:message code = 'list.list.msg.SelectTokens'/>");
			return false;
		}else {
			document.location.href = '<%=webUri%>/app/sso/security/discardToken.do';
		}
	}
	
	// 상세정보보기 버튼 선택시
	function onTokenDetailInfo() {
		var chkCnt = 0;
		chkCnt  = $(".communi_text input:checked").length;
		
		// 체크 항목이 없는 경우
		if(chkCnt == 0){
			alert("<spring:message code = 'list.list.msg.noSelectToken'/>");
			return false;
		}else if(chkCnt > 1){
			alert("<spring:message code = 'list.list.msg.SelectTokens'/>");
			
			return false;
		}else {
			document.location.href = '<%=webUri%>/app/sso/security/detailTokenInfo.do';
		}
	}
	
	// 검색 버튼 선택시
	function onTokenSearch() {
		document.location.href = '<%=webUri%>/app/sso/security/secuTokenSearch.do';
	}
	
	// 인쇄 버튼 선택시
	function onListPrint() {
		
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
		<!-- 절차 단계 view -->
		<tr>
			<td>
				<div style="width: 100%; min-height: 100px;" align="center">
					<div style="width: 100%; border: 5px solid #6ab2da;">
						<div style="width: 150px; float: left; padding-left: 50px;" align="left">
							<img src="<%=webUri%>/app/ref/image/sso/insu_img1.gif">
						</div>
						<div style="float: left; padding-top: 10px; text-align: left;">
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold;"><spring:message code = 'list.list.title.reqInfoStep1'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold;"><spring:message code = 'list.list.title.reqInfoStep2'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold;"><spring:message code = 'list.list.title.reqInfoStep3'/></div>
							<div style="color: #666666; line-height: 18px; font-family: Dotum, Verdana, Arial; font-size: 13px; font-weight: bold;"><spring:message code = 'list.list.title.reqInfoStep4'/></div>
						</div>
					</div>
				</div>
			<td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<!-- select 및 button 구성 시작 -->
 		<tr>
			<td>
				<acube:buttonGroup align="right">
				<%if(roleCodes.indexOf(roleId11) >= 0) {%>
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
					<td>
					<!-- 부서 select -->
					<select name="selDept" id="selDept" onchange="javascript:changeSelDept(this.value);">
						<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
					</select>
					</td>
					<acube:space between="button" />
				<%}%>
					<td>
					<!-- 발급 및 미발급 select -->
					<select name="selIssue" id="selIssue" onchange="javascript:changeSelIssue(this.value);">
						<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
						<option value="Y"><spring:message code="list.list.select.tokenInfoIssueY"/></option>
						<option value="N"><spring:message code="list.list.select.tokenInfoIssueN"/></option>
					</select>
					</td>
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="tokenRegBtn" disabledid="" onclick="javascript:onTokenReg();"  value='<%=msgTokenRegBtn%>'/>
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="tokenDisBtn" disabledid="" onclick="javascript:onTokenDiscard();"  value='<%=msgTokenDisBtn%>'/>
					<acube:space between="button" />
					<%if(roleCodes.indexOf(roleId11) >= 0) {%>
						<acube:space between="button" />
						<acube:menuButton id="tokenDetailBtn" disabledid="" onclick="javascript:onTokenDetailInfo();"  value='<%=msgTokenDetailBtn%>'/>
						<acube:space between="button" />
						<acube:space between="button" />
						<acube:menuButton id="tokenSearchBtn" disabledid="" onclick="javascript:onTokenSearch();"  value='<%=msgSearchBtn%>'/>
						<acube:space between="button" />
						<acube:space between="button" />
						<acube:menuButton id="tokenPrintBtn" disabledid="" onclick="javascript:onListPrint();"  value='<%=msgPrintBtn%>'/>
						<acube:space between="button" />
					<%}%>
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
						<!-- 보안토큰정보 목록 Table -->
						<td height="100%" valign="top" class="communi_text">
						<% 
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 9);
							acubeList.setColumnWidth("20,180,*,180,180,180,100,100,100");
							acubeList.setColumnAlign("center,center,center,center,center,center,center,center,center");
							
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							// 체크박스 생성
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All_Progress();");
							titleRow.setAttribute(rowIndex,"style","");
							
							titleRow.setData(++rowIndex, msgManageNum);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");

							titleRow.setData(++rowIndex, msgProductName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgRegisterDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgHandle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgHandleDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgUse);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgCertificationIssue);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgDiscardYN);
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