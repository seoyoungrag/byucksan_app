<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.anyframe.pagination.Page"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List"%>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : PrintListSendStatus.jsp 
 *  Description : 부서별 발송대기문서 목록 인쇄
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
	
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	
	// 검색 결과 값
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("ListVO");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = appDocVOs.size();
	
	String resultSearchDocType = CommonUtil.nullTrim(resultSearchVO.getSearchDocType());
	String resultSearchDeptId = CommonUtil.nullTrim(resultSearchVO.getSearchDeptId());
	String resultSearchDeptName = CommonUtil.nullTrim(resultSearchVO.getSearchDeptName());
	
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
	
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDocNumber 		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderDrafterName 	= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderReceiveDeptName = messageSource.getMessage("list.list.title.headerReceiveDeptName" , null, langType);
	String msgHeaderCompleteDate 	= messageSource.getMessage("list.list.title.headerCompleteDate" , null, langType);
	String msgHeaderEnfType 		= messageSource.getMessage("list.list.title.headerEnfType" , null, langType);
	String msgHeaderDocSate	 		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgTheRest 				= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	String msgCnt 					= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	int curPage=CPAGE;	//현재페이지
	
	String listTitle = messageSource.getMessage("statistics.title.sendStatusStatistics", null, langType) + " - [" + resultSearchDeptName + "]";
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
									<td height="100%" valign="top" class="communi_text">
<%									
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 7);
							acubeList.setColumnWidth("*,120,80,160,80,70,70");
							acubeList.setColumnAlign("left,center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDrafterName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCompleteDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderEnfType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocSate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
	
							AcubeListRow row = null;
			 				
							// 데이타 개수만큼 돈다...(행출력)
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        } else {
								for(int i = 0; i < nSize; i++) {
								    
								    AppDocVO appDocVO = (AppDocVO) appDocVOs.get(i);
									
									String rsDocId 			= CommonUtil.nullTrim(appDocVO.getDocId());
						            String rsTitle			= EscapeUtil.escapeHtmlDisp(appDocVO.getTitle());
						            String rsDrafterId		= CommonUtil.nullTrim(appDocVO.getDrafterId());
						            String rsDrafterName	= EscapeUtil.escapeHtmlDisp(appDocVO.getDrafterName());
						            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(appDocVO.getApprovalDate()));
						            String titleDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(appDocVO.getApprovalDate()));
						            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(appDocVO.getDeptCategory());
						            int rsSerialNumber		= appDocVO.getSerialNumber();
						            int rsSubSerialNumber	= appDocVO.getSubserialNumber();
						            String rsRecvDeptNames	= EscapeUtil.escapeHtmlDisp(appDocVO.getRecvDeptNames());
						            int rsRecvDeptCnt		= appDocVO.getRecvDeptCnt();
						            String urgencyYn		= CommonUtil.nullTrim(appDocVO.getUrgencyYn());
						            String rsEnfType = CommonUtil.nullTrim(appDocVO.getEnfType());
						            String rsDocState		= CommonUtil.nullTrim(appDocVO.getDocState());
						            String rsTransferYn		= CommonUtil.nullTrim(appDocVO.getTransferYn());
						            String electronDocYn	= CommonUtil.nullTrim(appDocVO.getElectronDocYn());
						            String titleMsg			= "";
						            String docStateMsg		= "";
						            
						            if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
							            if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
						        			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
						            	}else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
						            		rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
						            	}
						            }else{
						        		rsDocNumber = "";
						            }
						            
						            if(rsRecvDeptCnt > 0){
						        		rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
						            }
						            
						            titleMsg = rsTitle;
						            if("Y".equals(urgencyYn)) {
						        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
						            }
						            
						            if(!"".equals(rsDocState)) {
						            	docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
						            }
									
									row = acubeList.createRow();
									row.setAttribute("id", rsDocId);
									row.setAttribute("elecYn", electronDocYn);
						
									rowIndex = 0;															
									
									row.setData(rowIndex, rsTitle);	
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", titleMsg);
									
									row.setData(++rowIndex, rsDocNumber);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title",rsDocNumber);
									
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title",rsDrafterName);
									
									row.setData(++rowIndex, rsRecvDeptNames);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title",rsRecvDeptNames);
									
									row.setData(++rowIndex, rsDate);
									row.setAttribute(rowIndex, "title",titleDate);
									
									String enfType = messageSource.getMessage("statistics.header."+rsEnfType.toLowerCase() , null, langType);
									row.setData(++rowIndex, enfType);
									row.setAttribute(rowIndex, "title", enfType);
									
									row.setData(++rowIndex, docStateMsg);
									row.setAttribute(rowIndex, "title", docStateMsg);
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