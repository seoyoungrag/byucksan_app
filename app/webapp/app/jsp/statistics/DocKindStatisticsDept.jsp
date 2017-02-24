<%@ page import="com.sds.acube.app.statistics.vo.StatisticsVO" %>
<%@ page import="com.sds.acube.app.list.vo.ListVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ApprovalRoleStatisticsDept.jsp 
 *  Description : 문서구분별통계(부서별) 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 12 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

	//==============================================================================
	// 검색 결과 값
	List<StatisticsVO> results = (List<StatisticsVO>) request.getAttribute("ListVo");
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
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String headerDeptId 	= messageSource.getMessage("statistics.header.deptId" , null, langType);
	String headerDeptName 	= messageSource.getMessage("statistics.header.deptName" , null, langType);
	String headerDet001		= messageSource.getMessage("statistics.header.det001" , null, langType);
	String headerDet002 	= messageSource.getMessage("statistics.header.det002" , null, langType);
	String headerDet003 	= messageSource.getMessage("statistics.header.det003" , null, langType);
	String headerTotal		= messageSource.getMessage("statistics.header.total" , null, langType);
	
	String msgNoData 		= messageSource.getMessage("statistics.msg.noDate" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='statistics.title.docKindStatisticsDept'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/statistics/common/Common.jsp" flush="true" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='statistics.title.docKindStatisticsDept'/></acube:titleBar></td>
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
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("80,*,70,70,70,80");
							acubeList.setColumnAlign("center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,headerDeptId);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerDet001);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerDet002);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,headerDet003);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,headerTotal);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    StatisticsVO result = (StatisticsVO) results.get(i);
								
							    String rsDeptId			= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptId());
								String rsDeptName		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            int det001				= Integer.parseInt(CommonUtil.nullTrim(result.getDet001()));					            
					            int det002				= Integer.parseInt(CommonUtil.nullTrim(result.getDet002()));
					            int det003				= Integer.parseInt(CommonUtil.nullTrim(result.getDet003()));
								String titleDeptName	= "";
								String titleDeptId		= "";
					            
					            if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){							    
					        		titleDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
					        		rsDeptName = "<font color='red'>"+rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"")+"</font>";
								}else{
								    titleDeptName = rsDeptName;
								}
					            
					            if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){							    
								    titleDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
								    rsDeptId = "<font color='red'>"+rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"")+"</font>";
								}else{
								    titleDeptId = rsDeptId;
								}
					            
					            
					            long totalDept		= 1L;
					            totalDept = det001 + det002 + det003;
					            
					            
								
								row = acubeList.createRow();					
																						
								rowIndex = 0;
								
								row.setData(rowIndex, rsDeptId);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleDeptId);	
								
								row.setData(++rowIndex, rsDeptName);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleDeptName);	
								
								row.setData(++rowIndex, CommonUtil.currency(det001));
								row.setAttribute(rowIndex, "title", CommonUtil.currency(det001));
								
								row.setData(++rowIndex, CommonUtil.currency(det002));
								row.setAttribute(rowIndex, "title", CommonUtil.currency(det002));
								
								row.setData(++rowIndex, CommonUtil.currency(det003));
								row.setAttribute(rowIndex, "title", CommonUtil.currency(det003));
								
								row.setData(++rowIndex, CommonUtil.currency(totalDept));
								row.setAttribute(rowIndex, "title", CommonUtil.currency(totalDept));
								
					
						    } // for(~)
			
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
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
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>  
</acube:outerFrame>
<jsp:include page="/app/jsp/statistics/common/screen.jsp" flush="true" />
</Body>
</Html>