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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : ListReceiveWaitBox.jsp 
 *  Description : 접수대기함 리스트 
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

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// 검색 결과 값
	List<EnfDocVO> results = (List<EnfDocVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();
	
	String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultLobCode	= CommonUtil.nullTrim(resultSearchVO.getLobCode());
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
	String msgHeaderDocNumber 		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderReceiveDate 	= messageSource.getMessage("list.list.title.headerReceiveDate" , null, langType);
	String msgHeaderSendDeptName 	= messageSource.getMessage("list.list.title.headerSendDeptName" , null, langType);
	String msgHeaderAttach 			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);
	String msgHeaderEnfType 		= messageSource.getMessage("list.list.title.headerEnfType" , null, langType); 
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String det001 = appCode.getProperty("DET001","DET001","DET"); // 내부
	String det002 = appCode.getProperty("DET002","DET002","DET"); // 대내
	String det003 = appCode.getProperty("DET003","DET003","DET"); // 대외
	String det011 = appCode.getProperty("DET011","DET011","DET"); // 행정기관
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
			<td>
				<table width="50%" border="0" cellpadding="0" cellspacing="0" align="left">
				<tr>
					<td><acube:titleBar>접수함</acube:titleBar></td>
				</tr>
				</table>
				<table width="50%" border="0" cellpadding="0" cellspacing="0" align="right">
					<jsp:include page="/app/jsp/list/common/ListButtonGroup.jsp" flush="true" />
				</table>
			</td>
		</tr>

		<jsp:include page="/app/jsp/list/common/ListSearch.jsp" flush="true" />
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
							acubeList.setColumnWidth("*,40,100,,100,80,150");
							acubeList.setColumnAlign("left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderAttach);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderEnfType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderReceiveDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderSendDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
		
							AcubeListRow row = null;
							
						
			 				
			 				String tempAttachYn = "N";
			 				
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    EnfDocVO result = (EnfDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId			= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(result.getDocNumber());
					            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(result.getSenderDeptName());
					            String rsEnfType		= CommonUtil.nullTrim(result.getEnfType());
					            int rsAttach 			= result.getAttachCount();
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsOriginCompId	= CommonUtil.nullTrim(result.getOriginCompId());
					            String rsReceiveDate 	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
					            String titleMsg			= "";
					            String senderInfo 		= "";
					            String msgEnfType		= "";
					            String titleDate		= "";
					            String linkScriptName	= "";
					            String otherCompId		= "";
					            
					            int rsReceiverOrder		= result.getReceiverOrder();
					            
					            if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {

					        		if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
						        		if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderCompName;
						        		}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderDeptName;
						        		}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
						        		}
						            }
					            }else{
					        		if(!"".equals(rsSenderDeptName)){
					        		    senderInfo = rsSenderDeptName; 
					        		}
					            }
					            
					            
					            if(det002.equals(rsEnfType)) {
					        		msgEnfType = messageSource.getMessage("list.list.msg.enfType002" , null, langType);
					            }else if (det003.equals(rsEnfType)) {
					        		msgEnfType = messageSource.getMessage("list.list.msg.enfType003" , null, langType);
					        	}else if (det011.equals(rsEnfType)) {
					        		msgEnfType = messageSource.getMessage("list.list.msg.enfType011" , null, langType);
					            }else{
					        		msgEnfType = "";
					            }
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if("Y".equals(electronDocYn)) {
					        		titleDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
					        		otherCompId = rsOriginCompId;
					            }else{
					        		titleDate	= rsReceiveDate;
					            }
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								
								
								if("Y".equals(electronDocYn)) {
									row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:selectCompAppDoc('"+rsOriginCompId+"','"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N', '"+rsReceiverOrder+"');\">"+rsTitle+"</A>");
								}else{								    
									row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:selectNonEnfDoc('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N');\">"+rsTitle+"</A>");								    
								}		
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", titleMsg);
								
								if(rsAttach > 0){
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','"+tempAttachYn+"','"+otherCompId+"');fncMoveAttachDiv(event);\" ><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'></a>");
								}else{
								    row.setData(++rowIndex, "");
								}
								
								row.setData(++rowIndex, rsDocNumber);
								row.setAttribute(rowIndex, "title",rsDocNumber);
								
								row.setData(++rowIndex, msgEnfType);
								row.setAttribute(rowIndex, "title",msgEnfType);
								
								if("Y".equals(electronDocYn)) {
									row.setData(++rowIndex, rsReceiveDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectCompAppDoc('"+rsOriginCompId+"','"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','Y', '"+rsReceiverOrder+"');\"> </A>");
								}else{
								    row.setData(++rowIndex, rsReceiveDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectNonEnfDoc('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, senderInfo);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",senderInfo);								
					
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
     <jsp:include page="/app/jsp/list/common/ListPagingForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
       
     <!-- 첨부파일 div -->
     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
     <!-- 첨부파일 div 끝-->
</acube:outerFrame>

</Body>
</Html>