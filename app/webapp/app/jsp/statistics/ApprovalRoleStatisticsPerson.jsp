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
 *  Class Name  : ApprovalRoleStatisticsPerson.jsp 
 *  Description : 결재역할별통계(개인별) 
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
	
	String resultStartDate		= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate		= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	String resultSearchDeptId	= CommonUtil.nullTrim(resultSearchVO.getSearchDeptId());
	String resultSearchDeptName	= CommonUtil.nullTrim(resultSearchVO.getSearchDeptName());
	
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
	
	String headerUserName 	= messageSource.getMessage("statistics.header.userName" , null, langType);
	String headerUserPos 	= messageSource.getMessage("statistics.header.userPos" , null, langType);
	String headerDeptId 	= messageSource.getMessage("statistics.header.deptId" , null, langType);
	String headerDeptName 	= messageSource.getMessage("statistics.header.deptName" , null, langType);
	String headerArt010		= messageSource.getMessage("statistics.header.art010" , null, langType);
	String headerArt020 	= messageSource.getMessage("statistics.header.art020" , null, langType);
	String headerArt030 	= messageSource.getMessage("statistics.header.art030" , null, langType);
	String headerArt130 	= messageSource.getMessage("statistics.header.art130" , null, langType);  // jth8172 2012 신결재 TF
	String headerArt040 	= messageSource.getMessage("statistics.header.art040" , null, langType);
	String headerArt050 	= messageSource.getMessage("statistics.header.art050" , null, langType);
	String headerArt060 	= messageSource.getMessage("statistics.header.art060" , null, langType);
	String headerArt070 	= messageSource.getMessage("statistics.header.art070" , null, langType);
	String headerTotal		= messageSource.getMessage("statistics.header.total" , null, langType);
	
	String msgNoData 		= "";
	if("".equals(resultSearchDeptId)) {
	    msgNoData	= messageSource.getMessage("statistics.msg.noSearchDeptId" , null, langType);
	}else{
		msgNoData	= messageSource.getMessage("statistics.msg.noDate" , null, langType);
	}
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	int totalRow = 9;
	String colWidth = "80,*,70,80,60,60,60,60,60";
	String colAlign = "center,center,center,center,center,center,center,center,center";
	
	String opt003GYn = CommonUtil.nullTrim(resultSearchVO.getOpt003GYn());
	String opt053GYn = CommonUtil.nullTrim(resultSearchVO.getOpt053GYn());  // jth8172 2012 신결재 TF
	String opt009GYn = CommonUtil.nullTrim(resultSearchVO.getOpt009GYn());
	String opt019GYn = CommonUtil.nullTrim(resultSearchVO.getOpt019GYn());
	
	if("Y".equals(opt003GYn)){
	    totalRow++;
	    colWidth += ",60";
	    colAlign += ",center";
	}
	 // jth8172 2012 신결재 TF
	if("Y".equals(opt053GYn)){
	    totalRow++;
	    colWidth += ",60";
	    colAlign += ",center";
	}
	if("Y".equals(opt009GYn)) {
	    totalRow++;
	    colWidth += ",60";
	    colAlign += ",center";
	}
	if("Y".equals(opt019GYn)) {
	    totalRow++;
	    colWidth += ",60";
	    colAlign += ",center";
	}
		
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='statistics.title.approvalRoleStatisticsPerson'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/statistics/common/Common.jsp" flush="true" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='statistics.title.approvalRoleStatisticsPerson'/></acube:titleBar></td>
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
							acubeList = new AcubeList(sLine, totalRow);
							acubeList.setColumnWidth(colWidth);
							acubeList.setColumnAlign(colAlign);	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,headerDeptId);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,headerUserPos);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,headerUserName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerArt010);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerArt020);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							if("Y".equals(opt003GYn)){
								titleRow.setData(++rowIndex,headerArt030);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							 // jth8172 2012 신결재 TF
							if("Y".equals(opt053GYn)){
								titleRow.setData(++rowIndex,headerArt130);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							if("Y".equals(opt009GYn)){
								titleRow.setData(++rowIndex,headerArt040);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,headerArt050);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							if("Y".equals(opt019GYn)){
								titleRow.setData(++rowIndex,headerArt060);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,headerArt070);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,headerTotal);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
							
							String curDeptName 	= "";
							String curDeptId	= "";
							
							int art030 = 0;
							int art130 = 0;  // jth8172 2012 신결재 TF
							int art040 = 0;
							int art060 = 0;
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    StatisticsVO result = (StatisticsVO) results.get(i);
							    
							    String rsDeptId			= EscapeUtil.escapeHtmlDisp(result.getApproverDeptId());
								String rsDeptName		= EscapeUtil.escapeHtmlDisp(result.getApproverDeptName());
								String rsUserPos		= EscapeUtil.escapeHtmlDisp(result.getApproverPos());
								String rsUserName		= EscapeUtil.escapeHtmlDisp(result.getApproverName());								
								int art010				= Integer.parseInt(CommonUtil.nullTrim(result.getArt010()));					            
					            int art020				= Integer.parseInt(CommonUtil.nullTrim(result.getArt020()));

					            if("Y".equals(opt003GYn)){
					        		art030				= Integer.parseInt(CommonUtil.nullTrim(result.getArt030()));
					            }
					            // jth8172 2012 신결재 TF
					            if("Y".equals(opt053GYn)){
					        		art130				= Integer.parseInt(CommonUtil.nullTrim(result.getArt130()));
					            }
					            
					            if("Y".equals(opt009GYn)){
					            	art040				= Integer.parseInt(CommonUtil.nullTrim(result.getArt040()));
					            }
					            
					            int art050				= Integer.parseInt(CommonUtil.nullTrim(result.getArt050()));
					            
					            if("Y".equals(opt019GYn)){
					            	art060				= Integer.parseInt(CommonUtil.nullTrim(result.getArt060()));
					            }
					            
					            int art070				= Integer.parseInt(CommonUtil.nullTrim(result.getArt070()));					            
								String titleDeptName	= "";
								String titleDeptId		= "";
								String titleUserName	= "";
					            
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
					            
					            if(curDeptName.equals(rsDeptName)){
								    rsDeptName = "";								     
								}else{
								    curDeptName = rsDeptName;
								}
					            
					            if(curDeptId.equals(rsDeptId)){
					        		rsDeptId = "";								     
								}else{
								    curDeptId = rsDeptId;
								}
					            
					            if(rsUserName.indexOf(messageSource.getMessage("statistics.msg.replacePerson" , null, langType)) != -1){
					        		titleUserName = rsUserName.replaceAll(messageSource.getMessage("statistics.msg.replacePerson" , null, langType),"");
								    rsUserName = "<font color='red'>"+rsUserName.replaceAll(messageSource.getMessage("statistics.msg.replacePerson" , null, langType),"")+"</font>";
								}else{
								    titleUserName = rsUserName;
								}
					            
					            long totalPserson		= 1L;
					            totalPserson = art010 + art020 + art050  + art070;
					            
					            if("Y".equals(opt003GYn)){
					        		totalPserson += art030;
					            }
					            // jth8172 2012 신결재 TF
					            if("Y".equals(opt053GYn)){
					        		totalPserson += art130;
					            }
					            
					            if("Y".equals(opt009GYn)){
					        		totalPserson += art040;
					            }
					            
					            if("Y".equals(opt019GYn)){
					        		totalPserson += art060;
					            }   
								
								row = acubeList.createRow();					
																						
								rowIndex = 0;
								
								row.setData(rowIndex, rsDeptId);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleDeptId);
								
								row.setData(++rowIndex, rsDeptName);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleDeptName);
								
								row.setData(++rowIndex, rsUserPos);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", rsUserPos);
								
								row.setData(++rowIndex, rsUserName);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleUserName);
								
								row.setData(++rowIndex, CommonUtil.currency(art010));
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", CommonUtil.currency(art010));
								
								row.setData(++rowIndex, CommonUtil.currency(art020));
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", CommonUtil.currency(art020));
								
								if("Y".equals(opt003GYn)){
									row.setData(++rowIndex, CommonUtil.currency(art030));
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(art030));
								}
								 // jth8172 2012 신결재 TF
								if("Y".equals(opt053GYn)){
									row.setData(++rowIndex, CommonUtil.currency(art130));
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(art130));
								}
								
								if("Y".equals(opt009GYn)){
									row.setData(++rowIndex, CommonUtil.currency(art040));
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(art040));
								}
								
								row.setData(++rowIndex, CommonUtil.currency(art050));
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", CommonUtil.currency(art050));
								
								if("Y".equals(opt019GYn)){
									row.setData(++rowIndex, CommonUtil.currency(art060));
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", CommonUtil.currency(art060));
								}
								
								row.setData(++rowIndex, CommonUtil.currency(art070));
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", CommonUtil.currency(art070));
								
								row.setData(++rowIndex, CommonUtil.currency(totalPserson));
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", CommonUtil.currency(totalPserson));
								
					
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