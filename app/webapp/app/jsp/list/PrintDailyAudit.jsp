<%@ page import="com.sds.acube.app.common.vo.AuditListVO" %>

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
 *  Class Name  : PrintDailyAudit.jsp 
 *  Description : 일상감사일지 리스트 인쇄
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 06. 23 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// 검색 결과 값
	List<AuditListVO> results = (List<AuditListVO>) request.getAttribute("ListVo");
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
	String cPageStr = request.getParameter("cPage");
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
	
	String msgHeaderReceiveNumber	= messageSource.getMessage("list.list.title.headerReceiveNumber" , null, langType);
	String msgHeaderReceiveDate		= messageSource.getMessage("list.list.title.headerAcceptDate" , null, langType);
	String msgHeadeChargeDeptName	= messageSource.getMessage("list.list.title.headerChargeDeptName" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderApproverType	= messageSource.getMessage("list.list.title.headerApproverType" , null, langType);
	String msgHeaderReceiveType		= messageSource.getMessage("list.list.title.headerReceiveType" , null, langType);	
	String msgHeaderApproverPos		= messageSource.getMessage("list.list.title.headerApproverPos" , null, langType);
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
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 8);
							acubeList.setColumnWidth("60,80,100,*,80,100,80,50");
							acubeList.setColumnAlign("center,center,center,left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,msgHeaderReceiveNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeadeChargeDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApproverType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApproverPos);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							String art040 = appCode.getProperty("ART040", "ART040", "ART"); 
							String art042 = appCode.getProperty("ART042", "ART042", "ART");
							String art043 = appCode.getProperty("ART043", "ART043", "ART");
							String art044 = appCode.getProperty("ART044", "ART044", "ART");
							String apt001 = appCode.getProperty("APT001", "APT001", "APT");
							String apt002 = appCode.getProperty("APT002", "APT002", "APT");
							     
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AuditListVO result = (AuditListVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());					            
					            int rsReceiveNumber		= result.getAuditNumber();
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
					            String rsChargeDeptName	= EscapeUtil.escapeHtmlDisp(result.getChargeDeptName());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsApproverType	= CommonUtil.nullTrim(result.getApproverType());
					            String rsReceiveType 	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsApproverPos	= CommonUtil.nullTrim(result.getApproverPos());
					            String rsAskType		= CommonUtil.nullTrim(result.getAskType());
					            String rsProcType		= CommonUtil.nullTrim(result.getProcType());
					            String rsDeleteYn		= CommonUtil.nullTrim(result.getDeleteYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String electronDocYn 	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String titleMsg			= "";
					            String docState			= "";
					            String electronDocMsg	= "";
					            String deleteAuthYn		= "N";
					            String titleDate		= "";
					            
					            
					            titleMsg = rsTitle;
						        if("Y".equals(rsDeleteYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					             
						        if("N".equals(electronDocYn)){
					        		docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
					            }else{
					        		if(!"".equals(rsDocState) && rsDocState.length() == 6){
					        		   if(Integer.parseInt(rsDocState.substring(3,6)) >= 600 ){
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
					        		   }else if(Integer.parseInt(rsDocState.substring(3,6)) == 110 ){
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocReturnState" , null, langType);
					        		   }else{
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocIngState" , null, langType);
					        		   }
					        		}
					            }

/*
					            if( (art040.equals(rsAskType) || art044.equals(rsAskType) ) && apt001.equals(rsProcType) ) {
					        		docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
					            }else if( (art042.equals(rsAskType) || art043.equals(rsAskType) ) && apt001.equals(rsProcType) ) {
					        		docState = messageSource.getMessage("list.list.msg.daliyAuditDocIngState" , null, langType);
					            }else if( apt002.equals(rsProcType) ) {
					        		docState = messageSource.getMessage("list.list.msg.daliyAuditDocReturnState" , null, langType);
					            }
*/
					            
					            if("Y".equals(rsReceiveType)) {
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
					            }else{
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		titleDate	   = rsDate;
					            }
					            
					            if("N".equals(rsReceiveType) &&  "N".equals(rsDeleteYn)) {
					        		deleteAuthYn = "Y";
					            }
					            

					            
								row = acubeList.createRow(false);
					
								rowIndex = 0;								
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(rowIndex, "<font color='red'>"+rsReceiveNumber+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(rowIndex, rsReceiveNumber);
								}
								row.setAttribute(rowIndex, "title",rsReceiveNumber);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsDate+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsDate);
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsChargeDeptName+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsChargeDeptName);
								}
								row.setAttribute(rowIndex, "title",rsChargeDeptName);
								
								if("Y".equals(rsReceiveType)) {
									row.setData(++rowIndex, rsTitle);
								}else{
								    row.setData(++rowIndex, rsTitle);
								}
								if("Y".equals(rsDeleteYn)) {
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsApproverType+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsApproverType);
								}
								row.setAttribute(rowIndex, "title",rsApproverType);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+electronDocMsg+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, electronDocMsg);
								}
								row.setAttribute(rowIndex, "title",electronDocMsg);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsApproverPos+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsApproverPos);
								}
								row.setAttribute(rowIndex, "title",rsApproverPos);
								
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+docState+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, docState);
								}
								row.setAttribute(rowIndex, "title",docState);
								
					
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