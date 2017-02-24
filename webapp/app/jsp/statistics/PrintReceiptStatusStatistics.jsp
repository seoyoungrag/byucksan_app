<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.anyframe.pagination.Page" %>
<%@ page import="com.sds.acube.app.design.AcubeList" %>
<%@ page import="com.sds.acube.app.design.AcubeListRow" %>				 
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.StatisticsVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : PrintReceiptStatusStatistics.jsp 
 *  Description : 부서별 접수대기문서 현황 인쇄
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 03. 31 
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
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());	
	
	int nSize = results.size();
	
	String resultStartDate	= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate	= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	
	resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
	resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);
	//==============================================================================
	
	//==============================================================================
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
	String headerProcLevel 	= messageSource.getMessage("statistics.header.procLevel" , null, langType);
	String headerCount		= messageSource.getMessage("statistics.header.doc.count" , null, langType);
	
	String msgNoData 		= messageSource.getMessage("statistics.msg.noDate" , null, langType);
	//==============================================================================
	
	int curPage=CPAGE;	//현재페이지
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='statistics.title.receiptStatusStatistics'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListPrintCommon.jsp" flush="true" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="/app/jsp/list/common/ListPrintTop.jsp" flush="true" />
<div id="printDiv">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='statistics.title.receiptStatusStatistics'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td width="100%" height="100%">
							<form name="formList" style="margin:0px">
							<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="100%" valign="top" class="communi_text">
<%													
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 4);
							acubeList.setColumnWidth("250,*,200,200");
							acubeList.setColumnAlign("center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex = 0;
							
							titleRow.setData(rowIndex,headerDeptId);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									
							titleRow.setData(++rowIndex,headerProcLevel);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							titleRow.setData(++rowIndex,headerCount);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
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
									String docType			= EscapeUtil.escapeHtmlDisp(result.getAppComplete());
						            int docCount 			= Integer.parseInt(result.getAppIng());
						            
									row = acubeList.createRow();																											
									rowIndex = 0;
									
									row.setData(rowIndex, rsDeptId);	
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", rsDeptId);	
									
									row.setData(++rowIndex, rsDeptName);
									row.setData(rowIndex, "<a href=\"#\" onclick=\"listReceiptStatus('"+rsDeptId+"', '"+rsDeptName+"', '"+docType+"');return(false);\">"+rsDeptName+"</a>");
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", rsDeptName);	
									
									if ("RECV".equals(docType)) {
										row.setData(++rowIndex, messageSource.getMessage("statistics.header.searchTypeRecv", null, langType));	
									} else if ("LINE".equals(docType)) {
										row.setData(++rowIndex, messageSource.getMessage("statistics.header.searchTypeLine", null, langType));	
									}
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", docType);	
									
									row.setData(++rowIndex, "<a href=\"#\" onclick=\"listReceiptStatus('"+rsDeptId+"', '"+rsDeptName+"', '"+docType+"');return(false);\">"+CommonUtil.currency(docCount)+"</a>");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(docCount));
									if (docCount > 50) {
										row.setAttribute(rowIndex,"style","color : red;");
									}
							    }
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(false); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	
%>							
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>
</acube:outerFrame>
</div>
</body>
</html>