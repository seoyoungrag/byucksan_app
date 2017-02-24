<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil,
				 java.util.ArrayList"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminAll.jsp 
 *  Description : 전체 문서 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String app010 = appCode.getProperty("APP010", "APP010", "APP");	// 반려후 대장등록
 	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	String unRegistButtonAuthYn = (String) request.getAttribute("unRegistButtonAuthYn");
	
	// 검색 결과 값
	List<AppDocVO> results = new ArrayList<AppDocVO>();
	if(request.getAttribute("ListVo") != null){
		results = (List<AppDocVO>) request.getAttribute("ListVo");
	}
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
	
	String msgHeaderType 			= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDraftReceive	= messageSource.getMessage("list.list.title.headerDraftReceive" , null, langType);
	String msgHeaderProgressor		= messageSource.getMessage("list.list.title.headerProgressor" , null, langType);
	String msgHeaderEnfDocNumber	= messageSource.getMessage("list.list.title.headerEnfDocNumber" , null, langType);
	String msgHeaderLastUpdateDate	= messageSource.getMessage("list.list.title.headerLastUpdateDate" , null, langType);
	String msgHeaderDocState		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminAll"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListAdminCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="list.list.title.listAdminAll"/></acube:titleBar></td>
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
							int col = 7;
							String colSize = "";
							String colWidth = "";
							
							if("Y".equals(unRegistButtonAuthYn)){
							    col = 8;
							    colSize = "20,";
							    colWidth = "center,";
							}
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, col);
							acubeList.setColumnWidth(colSize+"40,*,130,90,90,80,90");
							acubeList.setColumnAlign(colWidth+"center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							if("Y".equals(unRegistButtonAuthYn)){
								titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
								titleRow.setAttribute(rowIndex,"onclick","javascript:check_AllDoc();");
								titleRow.setAttribute(rowIndex,"style","padding-left:2px");
			
								titleRow.setData(++rowIndex,msgHeaderType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}else{
							    titleRow.setData(rowIndex,msgHeaderType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderEnfDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
		
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderLastUpdateDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							String apt004 = appCode.getProperty("APT004","APT004","APT");
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsDocType 		= CommonUtil.nullTrim(result.getDocType());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDeptCategory	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
					            int rsSerialNumber		= result.getSerialNumber();
					            int rsSubSerialNumber	= result.getSubserialNumber();
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDrafterName	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            String rsProcessorId	= CommonUtil.nullTrim(result.getProcessorId());
					            String rsProcessorName 	= EscapeUtil.escapeHtmlDisp(result.getProcessorName());
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));	
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsDeleteYn		= CommonUtil.nullTrim(result.getDeleteYn());
					            String rsProcType		= CommonUtil.nullTrim(result.getProcType());
					            String rsTempYn			= CommonUtil.nullTrim(result.getTempYn());
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					            String rsDocCompleteYn	= CommonUtil.nullTrim(result.getDocCompleteYn());
					            String rsSealType		= CommonUtil.nullTrim(result.getSealType());
	 
					            String titleMsg			= "";
					            String docTypeMsg		= "";
					            String docStateMsg		= "";
					            String docNumber		= "";
					            String linkUrl			= "";
					            String titleDate		= "";
					            String deleteTr			= "";
					            String unRegistAuthYn	= "N";
					            String reRegistAuthYn	= "N";
					            
					            if("APP".equals(docGubun)) {
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
					            }else{
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
					            }
				            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            docNumber = rsDeptCategory;
					            if(rsSerialNumber > 0){
					        		docNumber = docNumber+"-"+rsSerialNumber;
					            }
					            if(rsSubSerialNumber > 0){
					        		docNumber = docNumber+"-"+rsSubSerialNumber;
					            }
					            if(rsSerialNumber == 0 && rsSubSerialNumber == 0){
					        		docNumber = "";
					            }
					            
					            if(!"".equals(rsDocState) ){
					            	docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
					            	// 심사완료건을 구분하여 목록에 출력  // jth8172 2012 신결재 TF
					            	if(!("".equals(rsSealType)) && ("APP620".equals(rsDocState) || "APP625".equals(rsDocState) ) ){
 					            		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase()+"Signed" , null, langType);  // jth8172 2012 신결재 TF
					            	}
									//반려후 재기안삭제문서구분일 경우 문서번호가 있으면 반려후 대장등록 문서임    // jth8172 2012 신결재 TF
					            	if(app010.equals(rsDocState) && rsSerialNumber >0) {
 					            		docStateMsg = messageSource.getMessage("list.list.msg.app110" , null, langType);  // jth8172 2012 신결재 TF
					            	}	
					            }
					            
					            if("Y".equals(electronDocYn)) {
								    if("APP".equals(docGubun)){
								    	linkUrl = "selectAppDoc";
								    }else{
										linkUrl = "selectEnfDoc";
								    }
								    
								    titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
								    
								}else{
								    if("APP".equals(docGubun)){
								    	linkUrl = "selectNonAppDoc";
								    }else{
										linkUrl = "selectNonEnfDoc";
								    }
								    
								    titleDate = rsDate;
								}						            
					            
					           
					            if("Y".equals(rsTempYn)) {
					        		docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
					            }
					            
					            if(("".equals(rsUnRegistYn) || "N".equals(rsUnRegistYn)) && "complete".equals(rsDocCompleteYn)){
					        		unRegistAuthYn = "Y";
					            }
					            
					            if("Y".equals(rsUnRegistYn)){
					        		reRegistAuthYn = "Y";
					            }
					            
					           
					            StringBuffer buff;
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								if("Y".equals(unRegistButtonAuthYn)){
									
									
									buff = new StringBuffer();
									buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+unRegistAuthYn+"\"   reRegistYn=\""+reRegistAuthYn+"\" >");								
									
									row.setData(rowIndex, buff.toString());
									row.setAttribute(rowIndex, "class", "ltb_check");
									row.setAttribute(rowIndex,"style","vertical-align:top;");
									
									row.setData(++rowIndex, docTypeMsg);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									if("Y".equals(rsUnRegistYn)) {
								        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								    }
									row.setAttribute(rowIndex, "title", docTypeMsg);
								}else{
								    row.setData(rowIndex, docTypeMsg);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									if("Y".equals(rsUnRegistYn)) {
								        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								    }
									row.setAttribute(rowIndex, "title", docTypeMsg);
								}
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkUrl+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N');\">"+rsTitle+"</A>");								
								if("Y".equals(rsUnRegistYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);								
								
								row.setData(++rowIndex, docNumber);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn) && !"".equals(docNumber)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",docNumber);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDeptName);
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
								if("Y".equals(rsUnRegistYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkUrl+"('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, docStateMsg);
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",docStateMsg);
								
								
					
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
<form id="appDocForm" method="post" name="appDocForm" style="margin:0px">
<!-- 의견팝업 -->
<input type="hidden" id="returnDocId" name="returnDocId" value="" />
<input type="hidden" id="returnFunction" name="returnFunction" value="" />
<input type="hidden" id="btnName" name="btnName" value="" />
<input type="hidden" id="opinionYn" name="opinionYn" value="" />
<input type="hidden" id="comment" name="comment" value=""/>
</form>
</Body>
</Html>