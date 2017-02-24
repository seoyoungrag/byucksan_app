<%@ page import="com.sds.acube.app.common.vo.BizProcVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminBizResult.jsp 
 *  Description : 연계처리결과 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 20 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// 검색 결과 값
	List<BizProcVO> results = (List<BizProcVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();
	
	String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultStartDate	= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate	= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	String resultLobCode	= CommonUtil.nullTrim(resultSearchVO.getLobCode());

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
	int trSu = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderBizSystemName	= messageSource.getMessage("list.list.title.headerBizSystemName" , null, langType);
	String msgHeaderBizTypeName		= messageSource.getMessage("list.list.title.headerBizTypeName" , null, langType);
	String msgHeaderBizProc			= messageSource.getMessage("list.list.title.headerBizProc" , null, langType);
	String msgHeaderBizDocState		= messageSource.getMessage("list.list.title.headerBizDocState" , null, langType);
	String msgHeaderProcState		= messageSource.getMessage("list.list.title.headerProcState" , null, langType);
	String msgHeaderProcDate		= messageSource.getMessage("list.list.title.headerProcDate" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminBizResult"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListAdminCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="list.list.title.listAdminBizResult"/></acube:titleBar></td>
		</tr>
		<jsp:include page="/app/jsp/list/common/ListAdminButtonGroup.jsp" flush="true" />
		<jsp:include page="/app/jsp/list/common/ListAdminSearch.jsp" flush="true" />
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
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 8);
							acubeList.setColumnWidth("20,*,80,100,80,90,90,90");
							acubeList.setColumnAlign("center,left,center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
		
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderBizDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderBizTypeName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
							
							titleRow.setData(++rowIndex,msgHeaderBizSystemName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderBizProc);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderProcState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderProcDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    BizProcVO result = (BizProcVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsBizSystemName	= EscapeUtil.escapeHtmlDisp(result.getBizSystemName());
					            String rsBizTypeName	= EscapeUtil.escapeHtmlDisp(result.getBizTypeName());
					            String rsBizProc		= CommonUtil.nullTrim(result.getExProcType());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String rsExProcState	= CommonUtil.nullTrim(result.getExProcState());
					            String rsExProcDate		= EscapeUtil.escapeDate(result.getExProcDate());
					            String titleDate		= EscapeUtil.escapeDate(result.getExProcDate());
					            
					            String bizProcMsg		= "";
					            String docStateMsg		= "";
					            String procStateMsg		= "";
					            
					            if(!"".equals(rsBizProc)) {
					        		bizProcMsg = messageSource.getMessage("list.code.msg."+rsBizProc.toLowerCase() , null, langType);
					            }
					            
					            if(!"".equals(rsDocState)) {
					        		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
					            }
					            
					            if(!"".equals(rsExProcState)) {
					        		procStateMsg = messageSource.getMessage("list.code.msg."+rsExProcState.toLowerCase() , null, langType);
					            }
					            
					            if(!"".equals(rsExProcDate)){
					        		rsExProcDate = DateUtil.getFormattedShortDate(rsExProcDate);
					            }
					            
					            if(!"".equals(titleDate)){
					        		titleDate = DateUtil.getFormattedDate(titleDate);
					            }
					            
								
					            StringBuffer buff;
					            
								row = acubeList.createRow();
					
								rowIndex = 0;
								
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"stdId\" id=\"stdId\" value=\""+rsDocId+"\"  listFormChk=listFormChk  >");								
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
								row.setData(++rowIndex, "<a href=\"#\"  onclick=\"javascript:selectBizResultDoc('"+rsDocId+"');\">"+rsTitle+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", rsTitle);
								
								row.setData(++rowIndex, docStateMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",docStateMsg);
								
								row.setData(++rowIndex, rsBizSystemName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsBizSystemName);								
								
								row.setData(++rowIndex, rsBizTypeName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");		
								row.setAttribute(rowIndex, "title",rsBizTypeName);
								
								row.setData(++rowIndex, bizProcMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");		
								row.setAttribute(rowIndex, "title",bizProcMsg);								
								
								row.setData(++rowIndex, procStateMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",procStateMsg);
								
								row.setData(++rowIndex, rsExProcDate);
								row.setAttribute(rowIndex, "title",titleDate);

					
						    } // for(~)
			
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
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
	</table>
	 <!-- 페이징관련 form -->
     <jsp:include page="/app/jsp/list/common/ListPagingAdminForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
</acube:outerFrame>

</Body>
</Html>