<%@ page import="com.sds.acube.app.common.vo.StampListVO" %>

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
 *  Class Name  : PrintAuditSealRegist.jsp 
 *  Description : 감사직인날인대장 리스트 인쇄
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 06. 14 
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
	List<StampListVO> results = (List<StampListVO>) request.getAttribute("ListVo");
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
	
	String msgHeaderStampNumber		= messageSource.getMessage("list.list.title.headerStampNumber" , null, langType);
	String msgHeaderEnforceDate 	= messageSource.getMessage("list.list.title.headerEnforceDate" , null, langType);
	String msgHeaderDocNumber		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderSender			= messageSource.getMessage("list.list.title.headerSender" , null, langType);
	String msgHeaderReceiver		= messageSource.getMessage("list.list.title.headerReceiver" , null, langType);
	String msgHeaderRequester		= messageSource.getMessage("list.list.title.headerRequester" , null, langType);
	String msgHeaderElecType		= messageSource.getMessage("list.list.title.headerElecType" , null, langType);
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
							acubeList = new AcubeList(sLine, 9);
							acubeList.setColumnWidth("60,80,100,*,80,80,80,80,80");
							acubeList.setColumnAlign("center,center,center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,msgHeaderStampNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderEnforceDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
							
							titleRow.setData(++rowIndex,msgHeaderSender);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiver);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderRequester);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderElecType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    StampListVO result = (StampListVO) results.get(i);
								
							    String rsStampId			= CommonUtil.nullTrim(result.getStampId());
							    String rsDocId 				= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 			= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 				= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDate 				= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getSealDate()));
					            String rsSealNumber			= CommonUtil.nullTrim(Integer.toString(result.getSealNumber()));					            				            
					            String rsDocNumber			= EscapeUtil.escapeHtmlDisp(result.getDocNumber());
					            String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsSender				= EscapeUtil.escapeHtmlDisp(result.getSender());
					            String rsReceiver			= EscapeUtil.escapeHtmlDisp(result.getReceiver());
					            String rsRequesterName		= EscapeUtil.escapeHtmlDisp(result.getRequesterName());
					            String deleteYn				= CommonUtil.nullTrim(result.getDeleteYn());
					            String electronDocMsg 		= "";
					            String titleDate			= "";
					            String msgDocState			= "";
					            String titleMsg				= "";
					            String titleRequesterName	= "";
					            
					            
					            if("Y".equals(electronDocYn)) {
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSealDate()));
					            }else{
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		titleDate	   = rsDate;
					            }
					            titleMsg = rsTitle;
					            titleRequesterName = rsRequesterName;
					            
					            if("N".equals(deleteYn)) {
					        		msgDocState = messageSource.getMessage("list.list.msg.auditRegistDocStateY" , null, langType);
					            }else{
					        		msgDocState = messageSource.getMessage("list.list.msg.auditRegistDocStateN" , null, langType);
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					        		rsRequesterName = "<font color='red'>"+rsRequesterName+"</font>";
					            }

					            if("0".equals(rsSealNumber)) {
					        		rsSealNumber = messageSource.getMessage("list.list.msg.notApplicable" , null, langType);
					            }
					            
					            
							
								row = acubeList.createRow(false);
					
								rowIndex = 0;
								
								if("Y".equals(deleteYn)) {
									row.setData(rowIndex, "<font color='red'>"+rsSealNumber+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(rowIndex, rsSealNumber);
								}
								row.setAttribute(rowIndex, "title",rsSealNumber);
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsDate+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsDate);
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsDocNumber+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsDocNumber);
								}
								row.setAttribute(rowIndex, "title",rsDocNumber);
								
								row.setData(++rowIndex, rsTitle);	
								if("Y".equals(deleteYn)) {
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);
								
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsSender+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsSender);
								}
								row.setAttribute(rowIndex, "title",rsSender);
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsReceiver+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsReceiver);
								}
								row.setAttribute(rowIndex, "title",rsReceiver);
								
								row.setData(++rowIndex, rsRequesterName);
								if("Y".equals(deleteYn)) {
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title",titleRequesterName);
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+electronDocMsg+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, electronDocMsg);
								}
								row.setAttribute(rowIndex, "title",electronDocMsg);
								
								if("Y".equals(deleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+msgDocState+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, msgDocState);
								}
								row.setAttribute(rowIndex, "title",msgDocState);
								
								
								
					
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