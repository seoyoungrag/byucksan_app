<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.anyframe.pagination.Page"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.common.vo.UserVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List"%>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : ListProcessStatus.jsp 
 *  Description :  개인별 문서미처리 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 13 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID

	// 검색 결과 값
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("ListVO");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	UserVO userVO = (UserVO) request.getAttribute("UserVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = appDocVOs.size();
	
	String resultSearchDocType = CommonUtil.nullTrim(resultSearchVO.getSearchDocType());
	String resultSearchDeptId = CommonUtil.nullTrim(resultSearchVO.getSearchDeptId());
	String resultSearchDeptName = CommonUtil.nullTrim(resultSearchVO.getSearchDeptName());
	String resultSearchUserId = CommonUtil.nullTrim(resultSearchVO.getSearchUserId());
	String resultSearchUserName = CommonUtil.nullTrim(resultSearchVO.getSearchUserName());
	
	// Page Navigation variables
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderType = messageSource.getMessage("statistics.header.docType", null, langType);
	String msgHeaderTitle = messageSource.getMessage("list.list.title.headerTitle", null, langType);
	String msgHeaderDraftReceive = messageSource.getMessage("statistics.header.draftReceive", null, langType);
	String msgHeaderLastUpdateDate = messageSource.getMessage("statistics.header.lastUpdateDate", null, langType);
	String msgHeaderDocState = messageSource.getMessage("statistics.header.docState", null, langType); 
	String msgNoData = messageSource.getMessage("list.list.msg.noData", null, langType);
	String msgTypeApp = messageSource.getMessage("statistics.header.typeApp", null, langType);
	String msgTypeEnf = messageSource.getMessage("statistics.header.typeEnf", null, langType);
	String msgHeaderDocKind = messageSource.getMessage("statistics.header.docKind", null, langType);
	String msgElectronDoc = messageSource.getMessage("statistics.header.electronDoc", null, langType);
	String msgNonElectronDoc = messageSource.getMessage("statistics.header.nonElectronDoc", null, langType);
	
	int curPage = CPAGE;	//현재페이지
	
	String listTitle = messageSource.getMessage("statistics.title.processStatusStatistics", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=listTitle%> - [<%=userVO.getDeptName()%>/<%=userVO.getUserName()%>]</title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/ref/css/main.css" />
<jsp:include page="/app/jsp/statistics/common/Common.jsp" flush="true" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%> - [<%=userVO.getDeptName()%>/<%=userVO.getUserName()%>]</acube:titleBar></td>
		</tr>
		<jsp:include page="/app/jsp/statistics/common/ButtonGroup.jsp" flush="true" />
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
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("60,*,100,100,80,80");
							acubeList.setColumnAlign("center,left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderLastUpdateDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDocKind);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
			 				
							// 데이타 개수만큼 돈다...(행출력)
					        if (totalCount == 0) {
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					        } else {
								for (int i = 0; i < nSize; i++) {
								    
								    AppDocVO appDocVO = (AppDocVO) appDocVOs.get(i);
									
									String rsDocId = CommonUtil.nullTrim(appDocVO.getDocId());
									String rsCompId = CommonUtil.nullTrim(appDocVO.getCompId());
						            String rsTitle	= EscapeUtil.escapeHtmlDisp(appDocVO.getTitle());
						            String urgencyYn = CommonUtil.nullTrim(appDocVO.getUrgencyYn());
						            String rsDrafterName = EscapeUtil.escapeHtmlDisp(appDocVO.getDrafterName());
						            String rsLastUpdateDate = EscapeUtil.escapeDate(appDocVO.getLastUpdateDate());
						            String rsDocState = CommonUtil.nullTrim(appDocVO.getDocState());
						            String electronDocYn = CommonUtil.nullTrim(appDocVO.getElectronDocYn());
						            									
									row = acubeList.createRow();
									row.setAttribute("id", rsDocId);
									row.setAttribute("elecYn", electronDocYn);
						
									rowIndex = 0;
				
									row.setData(rowIndex, (rsDocId.startsWith("APP")) ? msgTypeApp : msgTypeEnf);
									row.setAttribute(rowIndex, "title", (rsDocId.startsWith("APP")) ? msgTypeApp : msgTypeEnf);
									
									row.setData(++rowIndex, rsTitle);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", "Y".equals(urgencyYn) ? "<font color='red'>"+rsTitle+"</font>" : rsTitle);
																		
									row.setData(++rowIndex, rsDrafterName);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", rsDrafterName);								

									row.setData(++rowIndex, DateUtil.getFormattedShortDate(rsLastUpdateDate));
									row.setAttribute(rowIndex, "title", rsLastUpdateDate);
						
									String msgDocState = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
									row.setData(++rowIndex, msgDocState);
									row.setAttribute(rowIndex, "title", msgDocState);
									
									row.setData(++rowIndex, "Y".equals(electronDocYn) ? msgElectronDoc : msgNonElectronDoc);
									row.setAttribute(rowIndex, "title", "Y".equals(electronDocYn) ? msgElectronDoc : msgNonElectronDoc);									
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
			</table>
			</td>
		</tr>
	</table>
	 <!-- 페이징관련 form -->
     <jsp:include page="/app/jsp/statistics/common/ListPagingForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
</acube:outerFrame>

</Body>
</Html>