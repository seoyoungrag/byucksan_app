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
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListDraftBox.jsp 
 *  Description : 기안문서함 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 14 
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
	
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDraftDate 		= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
	String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderApprovalName	= messageSource.getMessage("list.list.title.headerApprovalName" , null, langType);
	String msgHeaderApprovalDate	= messageSource.getMessage("list.list.title.headerApprovalDate" , null, langType);
	String msgHeaderAttach			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);
	String msgHeaderDocState	 	= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
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
					<td><acube:titleBar>기안함</acube:titleBar></td>
				</tr>
				</table>
				<table width="50%" border="0" cellpadding="0" cellspacing="0" align="right">
					<jsp:include page="/app/jsp/list/common/ListButtonGroup.jsp" flush="true" />
				</table>
			</td>
		</tr>
		<jsp:include page="/app/jsp/list/common/ListDateSearch.jsp" flush="true" />
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form id="formList" name="formList" style="margin:0px">
					<input name="pubReader" id="pubReader"  type="hidden" value="">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							// 체크박스, 제목, 기안일자, 부서, 결재대기자, 결재자, 결재일자, 문서상태, 첨부
							acubeList = new AcubeList(sLine, 9);
							acubeList.setColumnWidth("30,*,120,120,120,120,120,120,80");
							acubeList.setColumnAlign("center,left,center,center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"결재대기자");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApprovalName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderApprovalDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocState);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderAttach);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
					            String titleDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getApproverDeptName());
					            String rsApprovalId 	= CommonUtil.nullTrim(result.getApproverId());
					            String rsApprovalName 	= EscapeUtil.escapeHtmlDisp(result.getApproverName());
					            String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
					            String titleApprDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
					            int rsAttach			= result.getAttachCount();
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					            
					          //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            String rsReadDate	= CommonUtil.nullTrim(result.getReadDate());
					            //end
					            
						    	String docStateMsg		= "";
					            String titleMsg			= "";
								String linkScriptName	= "";
								String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }else{//by 서영락, 2016-01-14
					            	if(!rsReadDate.equals("")){
										if("9999".equals(rsReadDate.substring(0,4))){
							        		rsTitle = "<b>"+rsTitle+"</b>";
						            	}						
									}
					            }
					            
					            if(!"".equals(rsDocState)) {
									docStateMsg = messageSource.getMessage("list.list.msg." + rsDocState.toLowerCase(), null, langType);
							    }
					            
					            if("Y".equals(electronDocYn)) {
					        		if("APP".equals(docGubun)){
					        		    linkScriptName =  "selectAppDoc";
					        		}else{
					        		    linkScriptName =  "selectEnfDoc"; 
					        		}
					        		
					        		titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
					            }else{
						        	if("APP".equals(docGubun)){
					        		    linkScriptName =  "selectNonAppDoc";
					        		}else{
					        		    linkScriptName =  "selectNonEnfDoc"; 
					        		}
						        	
						        	titleDate = rsDate;
					            }
					            
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								StringBuffer buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"   >");								
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
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
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", titleMsg);
								
								row.setData(++rowIndex, titleDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDeptName);		
								
								//결재대기자. 종결된 문서가 아니라면 결재일은 고정되어있어서 해당 값으로 결재대기자 표시여부를 결정한다.
								//결재 상태는 너무 많아서 귀찮다... 허나 이렇게 해도 되는지는 모르겠다.
								if(titleApprDate.equals("")){
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+CommonUtil.nullTrim(result.getProcessorId())+"');return(false);\">"+EscapeUtil.escapeHtmlDisp(result.getProcessorName())+"</A>");
									if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							   	 	}
								}else{
									row.setData(++rowIndex, "");
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsApprovalId+"');return(false);\">"+rsApprovalName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsApprovalName);
								
								row.setData(++rowIndex, titleApprDate);
								if("Y".equals(rsUnRegistYn)) {
						        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleApprDate);
								
								row.setData(++rowIndex, docStateMsg);
								if("Y".equals(rsUnRegistYn)) {
							        row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",docStateMsg);
								
								if(rsAttach > 0){
								    if("Y".equals(rsUnRegistYn)) {
										row.setData(++rowIndex, "<img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'>");
								    }else{										
										row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','"+tempAttachYn+"');fncMoveAttachDiv(event);\" ><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'></a>");
								    }
								}else{
								    row.setData(++rowIndex, "");
								}
					
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