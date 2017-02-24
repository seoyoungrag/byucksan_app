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
 *  Class Name  : ListDisplayBox.jsp 
 *  Description : 공람문서함 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 20 
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
	String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDrafterName		= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderDraftDate 		= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
	
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
							acubeList = new AcubeList(sLine, 7);
							acubeList.setColumnWidth("20,40,*,40,120,100,90");
							acubeList.setColumnAlign("center,center,left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
		
							titleRow.setData(++rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderAttach);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							String det001 = appCode.getProperty("DET001","DET001","DET");
							String det002 = appCode.getProperty("DET002","DET002","DET");
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDrafterName	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));					            
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            int rsAttach			= result.getAttachCount();
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String rsPubReadDate 	= EscapeUtil.escapeDate(result.getPubReadDate());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String rsEnfType		= CommonUtil.nullTrim(result.getEnfType());
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					          	//jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            String rsReadDate	= CommonUtil.nullTrim(result.getReadDate());
					            //end
					            String titleMsg			= "";
					            String docTypeMsg		= "";
					            String displayYn		= "N";
					            String titleDate		= "";
					            String scriptLinkName	= "";
					            String deptInfo			= "";
					            
					            if("APP".equals(rsDocId.substring(0,3))) {
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
					            }else{
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
					            }
					            
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
					            
					            if("".equals(rsPubReadDate)) {
					        		displayYn = "Y";
					            }
					            
					            if("Y".equals(electronDocYn)) {
								    if("APP".equals(docGubun)){
										scriptLinkName = "selectAppDoc";
								    }else{
										scriptLinkName = "selectEnfDoc";
								    }
								    titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
								}else{
								    if("APP".equals(docGubun)){
										scriptLinkName = "selectNonAppDoc";
								    }else{
										scriptLinkName = "selectNonEnfDoc";
								    }
								    titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
								}
					            
					            if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {

					        		if(!"".equals(rsSenderCompName) || !"".equals(rsDeptName)){
							        		if(!"".equals(rsSenderCompName) && "".equals(rsDeptName)){
							        		    deptInfo = rsSenderCompName;
							        		}else if("".equals(rsSenderCompName) && !"".equals(rsDeptName)){
							        		    deptInfo = rsDeptName;
							        		}else if(!"".equals(rsSenderCompName) && !"".equals(rsDeptName)){
							        		    deptInfo = rsSenderCompName + "/" + rsDeptName; 
							        		}
							            }
						            }else{
						        		if(!"".equals(rsDeptName)){
						        		    deptInfo = rsDeptName; 
						        		}
					            }


								StringBuffer buff;
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\" listFormChk=\""+displayYn+"\" >");								
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
								row.setData(++rowIndex, docTypeMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", docTypeMsg);
								
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
								    scriptLinkName = "selectAppDocSec";
								}
								//end
								
								
								if("Y".equals(rsUnRegistYn)) {
									row.setData(++rowIndex, rsTitle);
								}else{
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+scriptLinkName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								}
								if("".equals(rsPubReadDate)){
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; font-weight:bold;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
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
								
								row.setData(++rowIndex, deptInfo);
								if("".equals(rsPubReadDate)){
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; font-weight:bold;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",deptInfo);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
								if("".equals(rsPubReadDate)){
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; font-weight:bold;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+scriptLinkName+"('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("".equals(rsPubReadDate)){
									row.setAttribute(rowIndex,"style","font-weight:bold;");
								}
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
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
     <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
     
     <!-- 첨부파일 div -->
     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
     <!-- 첨부파일 div 끝--> 
</acube:outerFrame>

</Body>
</Html>