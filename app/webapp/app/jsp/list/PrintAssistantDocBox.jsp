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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/headerListPopAuth.jsp" %>
<%
/** 
 *  Class Name  : PrintAssistantDocBox.jsp 
 *  Description : 협조문서함 인쇄 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2012. 02. 20 
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
	List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("ListVo");
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDraftName		= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderApprovalName	= messageSource.getMessage("list.list.title.headerApprovalName" , null, langType);
	String msgHeaderDraftDate		= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
	String msgHeaderDocState		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListPrintCommon.jsp" flush="true" />

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="/app/jsp/list/common/ListPrintTop.jsp" flush="true" />
<div id="printDiv">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
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
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("*,100,90,70,80,100");
							acubeList.setColumnAlign("left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApprovalName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDraftDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							String apt004 = appCode.getProperty("APT004","APT004","APT");
							String enf500 = appCode.getProperty("ENF500","ENF500","ENF");
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsDocType 		= CommonUtil.nullTrim(result.getDocType());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            String rsDrafterName	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            String rsApproverName 	= EscapeUtil.escapeHtmlDisp(result.getApproverName());
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));	
					            String titleDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String titleMsg			= "";
					            String docTypeMsg		= "";
					            String docStateMsg		= "";
					            String completeYn		= "N";
					            
					            String app600 = appCode.getProperty("APP600", "APP600", "APP");
					            
					            if(app600.compareTo(rsDocState) <= 0){
					        		// 완료 문서
					        		completeYn = "Y";					        	
					            }
					          
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }					            
					            
					            if(!"".equals(rsDocState)) {
					            	docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
					            }
					            
					           
								
								row = acubeList.createRow(false);
					
								rowIndex = 0;
									
								row.setData(rowIndex, rsTitle);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title",rsDeptName);
								
								row.setData(++rowIndex, rsDrafterName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsApproverName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title",rsApproverName);
								
								row.setData(++rowIndex, rsDate);
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, docStateMsg);
								if("N".equals(completeYn)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								row.setAttribute(rowIndex, "title",docStateMsg);
								
								
					
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
</div>
</Body>
</Html>