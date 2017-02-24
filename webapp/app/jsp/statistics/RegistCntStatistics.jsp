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
 *  Class Name  : RegistCntStatistics.jsp 
 *  Description : 대장문서건수(부서별) 
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
	
	String headerDeptName 			= messageSource.getMessage("statistics.header.deptName" , null, langType);
	String headerDocRegist			= messageSource.getMessage("statistics.header.docRegist" , null, langType);
	String headerDistributionRegist	= messageSource.getMessage("statistics.header.distributionRegist" , null, langType);
	String headerNoDocRegist		= messageSource.getMessage("statistics.header.noDocRegist" , null, langType);
	String headerStampSealRegist	= messageSource.getMessage("statistics.header.stampSealRegist" , null, langType);
	String headerSealRegist			= messageSource.getMessage("statistics.header.sealRegist" , null, langType);
	String headerAuditSealRegist	= messageSource.getMessage("statistics.header.auditSealRegist" , null, langType);
	String headerTotal				= messageSource.getMessage("statistics.header.total" , null, langType);
	
	String msgNoData 		= messageSource.getMessage("statistics.msg.noDate" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	
	String opt201Yn = CommonUtil.nullTrim(resultSearchVO.getOpt201Yn());
	String opt202Yn = CommonUtil.nullTrim(resultSearchVO.getOpt202Yn());
	String opt203Yn = CommonUtil.nullTrim(resultSearchVO.getOpt203Yn());
	String opt204Yn = CommonUtil.nullTrim(resultSearchVO.getOpt204Yn());
	String opt205Yn = CommonUtil.nullTrim(resultSearchVO.getOpt205Yn());
	String opt208Yn = CommonUtil.nullTrim(resultSearchVO.getOpt208Yn());
	
	int defaultRowSize = 2;
	String opt201Size = "";
	String opt202Size = "";
	String opt203Size = "";
	String opt204Size = "";
	String opt205Size = "";
	String opt208Size = "";
	String opt201Align = "";
	String opt202Align = "";
	String opt203Align = "";
	String opt204Align = "";
	String opt205Align = "";
	String opt208Align = "";
	
	if("Y".equals(opt201Yn)){
	    defaultRowSize ++;
	    opt201Size = ",90";
	    opt201Align = ",center";
	}
	
	if("Y".equals(opt202Yn)){
	    defaultRowSize ++;
	    opt202Size = ",70";
	    opt202Align = ",center";
	}
	
	if("Y".equals(opt203Yn)){
	    defaultRowSize ++;
	    opt203Size = ",100";
	    opt203Align = ",center";
	}
	
	if("Y".equals(opt204Yn)){
	    defaultRowSize ++;
	    opt204Size = ",100";
	    opt204Align = ",center";
	}
	
	if("Y".equals(opt205Yn)){
	    defaultRowSize ++;
	    opt205Size = ",90";
	    opt205Align = ",center";
	}
	
	if("Y".equals(opt208Yn)){
	    defaultRowSize ++;
	    opt208Size = ",110";
	    opt208Align = ",center";
	}
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='statistics.title.registCntStatistics'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/statistics/common/Common.jsp" flush="true" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='statistics.title.registCntStatistics'/></acube:titleBar></td>
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
							acubeList = new AcubeList(sLine, defaultRowSize);
							acubeList.setColumnWidth("*"+opt201Size+opt202Size+opt203Size+opt204Size+opt205Size+opt208Size+",80");
							acubeList.setColumnAlign("center"+opt201Align+opt202Align+opt203Align+opt204Align+opt205Align+opt208Align+",center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,headerDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							if("Y".equals(opt201Yn)){
								titleRow.setData(++rowIndex,headerDocRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt202Yn)){
								titleRow.setData(++rowIndex,headerDistributionRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt203Yn)){
								titleRow.setData(++rowIndex,headerNoDocRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt204Yn)){
								titleRow.setData(++rowIndex,headerStampSealRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt205Yn)){
								titleRow.setData(++rowIndex,headerSealRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt208Yn)){
								titleRow.setData(++rowIndex,headerAuditSealRegist);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,headerTotal);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    
							    StatisticsVO result = (StatisticsVO) results.get(i);							    
								
								String rsDeptName		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
								String defaultDeptName	= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            int lol001				= Integer.parseInt(CommonUtil.nullTrim(result.getLol001()));					            
					            int lol002				= Integer.parseInt(CommonUtil.nullTrim(result.getLol002()));
					            int lol003				= Integer.parseInt(CommonUtil.nullTrim(result.getLol003()));
					            int lol004				= Integer.parseInt(CommonUtil.nullTrim(result.getLol004()));
					            int lol005				= Integer.parseInt(CommonUtil.nullTrim(result.getLol005()));
					            int lol008				= Integer.parseInt(CommonUtil.nullTrim(result.getLol008()));
					            String titleDeptName	= "";
					            String mChDept			= messageSource.getMessage("statistics.msg.db.replaceDept" , null, langType);
					            
					            if(rsDeptName.indexOf(mChDept) != -1){							    
					        		titleDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
					        		rsDeptName = "<font color='red'>"+rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"")+"</font>";
								}else{
								    titleDeptName = rsDeptName;
								}
					            
					            long totalDept		= 1L;
					            totalDept = lol001 + lol002 + lol003 + lol004 + lol005 + lol008;
					            
					            if(totalCount == 1 && defaultDeptName.equals(mChDept) && lol001 == 0 && lol002 == 0 && lol003 == 0 && lol004 == 0 && lol005 == 0 && lol008 == 0){
					        		totalCount = 0;
					            }else{
								
									row = acubeList.createRow();					
																							
									rowIndex = 0;
									
									row.setData(rowIndex, rsDeptName);	
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", titleDeptName);	
									
									if("Y".equals(opt201Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol001));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol001));
									}
									
									if("Y".equals(opt202Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol002));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol002));
									}
									
									if("Y".equals(opt203Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol003));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol003));
									}
									
									if("Y".equals(opt204Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol004));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol004));
									}
									
									if("Y".equals(opt205Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol005));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol005));
									}
									
									if("Y".equals(opt208Yn)){
										row.setData(++rowIndex, CommonUtil.currency(lol008));
										row.setAttribute(rowIndex, "title", CommonUtil.currency(lol008));
									}
									
									row.setData(++rowIndex, CommonUtil.currency(totalDept));
									row.setAttribute(rowIndex, "title", CommonUtil.currency(totalDept));
					            }
								
					
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