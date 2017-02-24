<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList" %>
<%@ page import="com.sds.acube.app.design.AcubeListRow" %>
<%@ page import="com.sds.acube.app.statistics.vo.StatisticsVO" %>
<%@ page import="com.sds.acube.app.list.vo.ListVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ProcessStatusStatistics.jsp 
 *  Description : 개인별 문서미처리 현황을 조회
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 22 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

	//==============================================================================
	// 검색 결과 값
	List<StatisticsVO> results = (List<StatisticsVO>) request.getAttribute("ListVO");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	String resultDocType = CommonUtil.nullTrim(resultSearchVO.getSearchDocType());
	String resultStartDate = CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate = CommonUtil.nullTrim(resultSearchVO.getEndDate());
	resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
	resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);
	
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());	
	int nSize = results.size();		

	// Page Navigation variables
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String headerDeptId 	= messageSource.getMessage("statistics.header.deptId" , null, langType);
	String headerDeptName 	= messageSource.getMessage("statistics.header.deptName" , null, langType);
	String headerUserPos 	= messageSource.getMessage("statistics.header.userPos" , null, langType);
	String headerUserName 	= messageSource.getMessage("statistics.header.userName" , null, langType);
	String headerCount		= messageSource.getMessage("statistics.header.doc.count" , null, langType);
	
	String msgNoData 		= messageSource.getMessage("statistics.msg.noDate" , null, langType);
	//==============================================================================
	
	int curPage=CPAGE;	//현재페이지
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='statistics.title.processStatusStatistics'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/statistics/common/Common.jsp" flush="true" />
<script type="text/javascript">
var statisticsWin = null;

function listProcessStatus(userId, deptId, userPos) {
	var url = "<%=webUri%>/app/list/admin/listProcessStatus.do?searchDocType=<%=resultDocType%>&searchUserId="+userId+"&searchDeptId="+deptId+"&searchUserPos="+userPos;
	statisticsWin = openWindow("statisticsWin", url, 800, 480);
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='statistics.title.processStatusStatistics'/></acube:titleBar></td>
		</tr>
		<jsp:include page="/app/jsp/statistics/common/ButtonGroup.jsp" flush="true" />
		<tr>
			<td style="font:9pt;"><spring:message code='statistics.msg.process.status.notice'/></td>
		</tr>
		<jsp:include page="/app/jsp/statistics/common/Search.jsp" flush="true" />
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList"  style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">			
<%			
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 5);
							acubeList.setColumnWidth("150,*,200,150,130");
							acubeList.setColumnAlign("center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex = 0;
							
							titleRow.setData(rowIndex,headerDeptId);		
							titleRow.setData(++rowIndex,headerDeptName);
							titleRow.setData(++rowIndex,headerUserPos);		
							titleRow.setData(++rowIndex,headerUserName);		
							titleRow.setData(++rowIndex,headerCount);
		
							AcubeListRow row = null;
							
						    if (totalCount == 0) {
						        row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
						    } else {
								// 데이타 개수만큼 돈다...(행출력)
								for (int i = 0; i < nSize; i++) {
								    
								    StatisticsVO result = (StatisticsVO) results.get(i);
									
								    String rsDeptId			= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptId());
									String rsDeptName		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
								    String rsUserId			= EscapeUtil.escapeHtmlDisp(result.getDrafterId());
								    String rsUserPos			= EscapeUtil.escapeHtmlDisp(result.getDrafterPos());
									String rsUserName		= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
						            int docCount 			= Integer.parseInt(result.getAppIng());
						            
									row = acubeList.createRow();					
																							
									rowIndex = 0;
									
									row.setData(rowIndex, rsDeptId);	
									row.setAttribute(rowIndex, "title", rsDeptId);	

									row.setData(++rowIndex, "<a href=\"#\" onclick=\"listProcessStatus('"+rsUserId+"','"+rsDeptId+"','"+rsUserPos+"');return(false);\">"+rsDeptName+"</a>");
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", rsDeptName);	
									
									row.setData(++rowIndex, rsUserPos);	
									row.setAttribute(rowIndex, "title", rsUserPos);	
									
									row.setData(++rowIndex, "<a href=\"#\" onclick=\"onFindUserInfo('"+rsUserId+"');return(false);\">"+rsUserName+"</a>");
									row.setAttribute(rowIndex, "title", rsUserName);	
									
									row.setData(++rowIndex, "<a href=\"#\" onclick=\"listProcessStatus('"+rsUserId+"','"+rsDeptId+"','"+rsUserPos+"');return(false);\">"+CommonUtil.currency(docCount)+"</a>");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(docCount));
									if (docCount > 50) {
										row.setAttribute(rowIndex,"style","color : red;");
									}
							    }
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
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>  
	 <!-- 페이징관련 form -->
     <jsp:include page="/app/jsp/statistics/common/ListPagingForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝--></acube:outerFrame>
<jsp:include page="/app/jsp/statistics/common/screen.jsp" flush="true" />
</Body>
</Html>