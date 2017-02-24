<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : PrintDistributionRegist.jsp 
 *  Description : 문서배부대장 리스트 인쇄
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 06. 20 
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
	List<EnfDocVO> results = (List<EnfDocVO>) request.getAttribute("ListVo");
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
	int trSu = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle 				= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDistributeNumber	= messageSource.getMessage("list.list.title.headerDistributeNumber" , null, langType);
	String msgHeaderSenderName			= messageSource.getMessage("list.list.title.headerSenderName" , null, langType);
	String msgHeaderReceiverName		= messageSource.getMessage("list.list.title.headerReceiverName" , null, langType);
	String msgHeaderReceiveDeptName		= messageSource.getMessage("list.list.title.headerReceiveDistributeName" , null, langType);
	String msgHeaderDistributionDate	= messageSource.getMessage("list.list.title.headerReceiveDistributeDeptName" , null, langType);
	String msgHeaderElecType			= messageSource.getMessage("list.list.title.headerElecType" , null, langType);
	
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
							acubeList = new AcubeList(sLine, 7);
							acubeList.setColumnWidth("60,*,150,80,90,80,80");
							acubeList.setColumnAlign("center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,msgHeaderDistributeNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderSenderName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiverName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDistributionDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderElecType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    EnfDocVO result = (EnfDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            int rsDistributeNumber	= result.getDistributeNumber();
					            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(result.getSenderDeptName());
					            String rsAccepterName	= EscapeUtil.escapeHtmlDisp(result.getAccepterName());
					            String rsAcceptDeptName	= EscapeUtil.escapeHtmlDisp(result.getAcceptDeptName());
					            String rsDistributeDate	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDistributeDate())); 
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn()); 
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String titleMsg			= "";
								String senderInfo 		= "";
								String electronDocMsg	= "";
								String scriptLinkName 	= "";
								String ttleDate			= "";
					            
					            if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
					        		senderInfo = rsSenderCompName + "/" + rsSenderDeptName;
					            }
					            
					            titleMsg = rsTitle;
					            

					            if("Y".equals(electronDocYn)) {
					        		scriptLinkName = "selectAppDoc";
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					        		ttleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDistributeDate()));
					        		
					            }else{
					        		if("APP".equals(docGubun)){
					        		    scriptLinkName = "selectNonAppDoc"; 
					        		}else{
					        		    scriptLinkName = "selectNonEnfDoc"; 
					        		}
					        		
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		ttleDate	   = rsDistributeDate;
					            }
					            
								row = acubeList.createRow(false);
					
								rowIndex = 0;
								
								row.setData(rowIndex, rsDistributeNumber);
								row.setAttribute(rowIndex, "title",rsDistributeNumber);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+scriptLinkName+"('"+rsDocId+"','"+resultLobCode+"');\">"+rsTitle+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleMsg);	
								
								row.setData(++rowIndex, senderInfo);
								row.setAttribute(rowIndex, "title",senderInfo);	
								
								row.setData(++rowIndex, rsAccepterName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsAccepterName);
								
								row.setData(++rowIndex, rsAcceptDeptName);
								row.setAttribute(rowIndex, "title",rsAcceptDeptName);
								
								row.setData(++rowIndex, rsDistributeDate);
								row.setAttribute(rowIndex, "title",ttleDate);
								
								row.setData(++rowIndex, electronDocMsg);
								row.setAttribute(rowIndex, "title",electronDocMsg);
								
					
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