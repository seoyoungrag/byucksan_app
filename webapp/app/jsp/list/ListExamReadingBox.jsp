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
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : ListExamReadingBox.jsp 
 *  Description : 검사부열람함 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 21 
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
	String cPageStr = request.getParameter("cPage");
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
	
	String msgHeaderType			= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderAttach			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);	
	String msgHeaderDocNumber		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderDrafterName		= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderDrafterDeptName	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDraftDate 		= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
	String msgHeaderElecType		= messageSource.getMessage("list.list.title.headerElecType" , null, langType);
	String msgHeaderAuditReadYn		= messageSource.getMessage("list.list.title.headerAuditReadYn" , null, langType);
	
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String opt375 = appCode.getProperty("OPT375","OPT375","OPT"); // 감사부서열람함 접수문서 사용여부(Y:사용, N:미사용)
	opt375 = envOptionAPIService.selectOptionValue(compId, opt375);
	String opt377 = appCode.getProperty("OPT377","OPT377","OPT"); // 감사부서열람함 비전자문서 사용여부(Y:사용, N:미사용)
	opt377 = envOptionAPIService.selectOptionValue(compId, opt377);
	
	int listCnt = 7;
	String headerWidth = "";
	String tailWidth = "";
	String headerAlign = "";
	String tailAlign = "";
	
	if("Y".equals(opt375)){
	    listCnt++;
	    headerWidth = "40,";
	    headerAlign = "center,";
	}
	
	if("Y".equals(opt377)){
	    listCnt++;
	    tailWidth = "70,";
	    tailAlign = "center,";
	}
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
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<jsp:include page="/app/jsp/list/common/ListButtonGroup.jsp" flush="true" />
		<jsp:include page="/app/jsp/list/common/ListDateSearch.jsp" flush="true" />
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
							acubeList = new AcubeList(sLine, listCnt);
							acubeList.setColumnWidth(headerWidth+"*,40,130,80,100,80,"+tailWidth+"60");
							acubeList.setColumnAlign(headerAlign+"left,center,center,center,center,center,"+tailAlign+"center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;							
							
							if("Y".equals(opt375)){
								titleRow.setData(rowIndex,msgHeaderType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}else{
							    rowIndex--;
							}

							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							titleRow.setData(++rowIndex,msgHeaderAttach);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							if("Y".equals(opt377)){
								titleRow.setData(++rowIndex,msgHeaderElecType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,msgHeaderAuditReadYn);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 				= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 			= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 				= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId			= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDrafterName		= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            String rsDrafterDeptName	= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            String rsDate 				= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
					            String titleDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
					            String rsApprovalId			= CommonUtil.nullTrim(result.getApproverId());
					            String rsApprovalName		= EscapeUtil.escapeHtmlDisp(result.getApproverName());
					            String rsApprovalDate		= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
					            String titleApprDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
					            String rsDocNumber			= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
					            int rsSubSerialNumber		= result.getSubserialNumber();
					            int rsAttach				= result.getAttachCount();
					            String urgencyYn			= CommonUtil.nullTrim(result.getUrgencyYn());
					            String rsTransferYn			= CommonUtil.nullTrim(result.getTransferYn());
					            String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsAuditReadYn		= CommonUtil.nullTrim(result.getAuditReadYn());
					            String rsAuditReadReason	= EscapeUtil.escapeHtmlDisp(result.getAuditReadReason());
					            String rsUnRegistYn			= CommonUtil.nullTrim(result.getUnregistYn());
					            //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            //end
					            String titleMsg				= "";
					            String auditReadYnMsg		= "";
					            String docTypeMsg			= "";
					            String electronDocMsg 		= "";
					            String linkScriptName		= "";
					            
					            if(!"N".equals(rsAuditReadYn)){
					        		rsAuditReadYn = "Y";
					            }
					            
					            if(rsSubSerialNumber > 0){
			        				rsDocNumber = rsDocNumber+"-"+rsSubSerialNumber;
			            		}
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if("Y".equals(rsAuditReadYn)) {
					        		auditReadYnMsg = messageSource.getMessage("list.list.msg.headerAuditReadY" , null, langType);
					        		rsAuditReadReason = auditReadYnMsg;
					            }else{
					        		auditReadYnMsg = messageSource.getMessage("list.list.msg.headerAuditReadN" , null, langType);
					            }
					            
					            if("APP".equals(rsDocId.substring(0,3))) {						        	
				        			docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
					            }else{
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
					            }
					            
					            if("Y".equals(electronDocYn)) {
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					            }else{
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					            }
					            
					            if("Y".equals(electronDocYn)) {
					        		if("APP".equals(rsDocId.substring(0,3))) {			
						        	    linkScriptName = "selectAppDoc"; 
					        		}else{
					        		    linkScriptName = "selectEnfDoc";
					        		}
						        	
						        	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
						        	
					            }else{
					        		if("APP".equals(rsDocId.substring(0,3))) {	
					        		    linkScriptName = "selectNonAppDoc";  
					        		}else{
					        		    linkScriptName = "selectNonEnfDoc";
					        		}
					        		
					        		titleDate = rsDate;
					            }
					            
					            

							
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								if("Y".equals(opt375)){
									row.setData(rowIndex, docTypeMsg);								
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									if("Y".equals(rsUnRegistYn)) {
								        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								    }
									row.setAttribute(rowIndex, "title", docTypeMsg);
								}else{
								    rowIndex--;
								}
								
								//jkkim added 보안문서 아이콘 표시 start 
								boolean isDuration = false;
								if(!rsSecurityStartDate.equals("")&&!rsSecurityEndDate.equals(""))
								{
								    int nStartDate = Integer.parseInt(rsSecurityStartDate);
								    int nEndDate = Integer.parseInt(rsSecurityEndDate);
								    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
									if((nCurDate > nStartDate ||  nCurDate == nStartDate) && (nCurDate < nEndDate ||  nCurDate == nEndDate))
									    isDuration = true;
								}
								if("Y".equals(rsSecurityYn)&&(isDuration==true))
								{
								    rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
								    linkScriptName = "selectAppDocSec";
								}
								//end
								
								if("Y".equals(rsUnRegistYn)) {
									row.setData(++rowIndex, rsTitle);
								}else{
									if("Y".equals(rsAuditReadYn)) {
										row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
									}else{
									    row.setData(++rowIndex, rsTitle);
									}
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", titleMsg);
								
								if(rsAttach > 0){
								    if("Y".equals(rsUnRegistYn)) {
										row.setData(++rowIndex, "<img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'>");
								    }else{										
										row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','"+tempAttachYn+"');fncMoveAttachDiv(event);\" ><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'></a>");
								    }
								}else{
								    row.setData(++rowIndex, "");
								}
								
								row.setData(++rowIndex, rsDocNumber);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn) && !"".equals(rsDocNumber)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDocNumber);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsDrafterDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterDeptName);	
								
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);								
								
								if("Y".equals(opt377)){
									row.setData(++rowIndex, electronDocMsg);
									if("Y".equals(rsUnRegistYn)) {
								        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								    }
									row.setAttribute(rowIndex, "title",electronDocMsg);
								}
								
								row.setData(++rowIndex, auditReadYnMsg);
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsAuditReadReason);
								
					
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
     <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
     
     <!-- 첨부파일 div -->
     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
     <!-- 첨부파일 div 끝--> 
</acube:outerFrame>

</Body>
</Html>