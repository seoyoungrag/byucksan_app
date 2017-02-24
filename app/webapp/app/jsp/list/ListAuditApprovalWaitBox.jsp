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

<%@ page import="com.sds.acube.app.approval.vo.AppLineVO"%>
<%@ page import="com.sds.acube.app.approval.service.IApprovalService" %>
<%@ page import="com.sds.acube.app.approval.util.ApprovalUtil" %>

<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListAuditApprovalWaitBox.jsp 
 *  Description : 일상감사함 리스트 
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
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); // 한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderType 			= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.doctitle" , null, langType);
	String msgHeaderRegisterDept 	= messageSource.getMessage("list.list.title.headerRegisterDept" , null, langType);
	String msgHeaderDraftReceive	= messageSource.getMessage("list.list.title.headerReceiveDate" , null, langType);
	String msgHeaderAttach 			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);	//결재대기함 첨부 컬럼추가	20150323_jskim
	String msgHeaderReturn			= messageSource.getMessage("list.list.button.return" , null, langType);
	String msgHeaderSenderName 		= messageSource.getMessage("list.list.title.headerSenderName" , null, langType);
	String msgHeaderDocSate	 		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	String headerProcStateDocmgr	= messageSource.getMessage("list.list.title.headerProcStateDocmgr", null, langType); //처리상태
	String headerCustomerSel		= messageSource.getMessage("list.list.title.headerCustomerSel", null, langType);	//	담당지정	20150414_csh
	String headerReserch			= messageSource.getMessage("list.code.msg.dhu001", null, langType);	//	조회	20150414_csh
	
	//==============================================================================
	
	int curPage=CPAGE;	//현재페이지

	//	담당자반송		20150916_csh
	IApprovalService approvalService = (IApprovalService)ctx.getBean("approvalService");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
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
<%-- 		<jsp:include page="/app/jsp/list/common/ListSearch.jsp" flush="false" /> --%>
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
							//S		체크박스, 담당지정 추가  20150311_csh		S
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("40,*,100,100,60,100");
							acubeList.setColumnAlign("center,left,center,center,center,center");
							//E		체크박스, 담당지정 추가	20150311_csh	E
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							//S		체크박스 추가	20150311_csh	S
// 							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
// 							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
// 							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							//E		체크박스 추가	20150311_csh   E
		
							titleRow.setData(rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderRegisterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReturn);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,headerCustomerSel);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
							String tempAttachYn = "N";
			 				String apt004 = appCode.getProperty("APT004","APT004","APT");
			 				String app100 = appCode.getProperty("APP100","APP100","APP");			 				
			 					
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId 		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            String rsDrafterName 	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            int rsAttach 			= result.getAttachCount();	                         //결재대기함 첨부 컬럼추가	20150323_jskim	
					            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));
					            String rsSenderDeptId 	= CommonUtil.nullTrim(result.getSenderDeptId());
					            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(result.getSenderDeptName());
					            String rsSenderCompId 	= CommonUtil.nullTrim(result.getSenderCompId());
					            String rsSenderCompName = EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String rsReturnDocYn	= CommonUtil.nullTrim(result.getReturnDocYn());
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsProcType		= CommonUtil.nullTrim(result.getProcType());
					            String rsTempYn			= CommonUtil.nullTrim(result.getTempYn());
					            //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            //end
					            String titleDate		= "";
					            String linkScriptName	= "";
					            String titleMsg			= "";
					            String docTypeMsg		= "";
					            String docStateMsg		= "";
					            String sendInfo			= "";
					            
					            
					            if("APP".equals(docGubun)) {
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
					        		docGubun = "APP";
					        		
					            }else{
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
					        		docGubun = "ENF";
					            }

					            docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType); 
					            titleMsg = rsTitle;
					            
					            //	담당자반송		20150916_csh
					            List<AppLineVO> lineVO = approvalService.listAppLine(rsCompId, rsDocId);	//	결재경로
					            AppLineVO currentUser = ApprovalUtil.getCurrentApprover(lineVO);
					            if (!currentUser.getReadDate().contains("9999")) {
					            	rsTitle = "<font style='font-weight: bold; color: #2015e6;'>"+messageSource.getMessage("list.list.title.returnCharge" , null, langType)+"</font> "+rsTitle;
					            }
					            
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if("Y".equals(electronDocYn)) {
						        	if("APP".equals(docGubun)){
						        	    linkScriptName = "selectAppDoc"; 
					        		}else{
					        		    linkScriptName = "selectEnfDoc";
					        		}
						        	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
					            }else{
					        		if("APP".equals(docGubun)){
					        		    linkScriptName = "selectNonAppDoc";  
					        		}else{
					        		    linkScriptName = "selectNonEnfDoc";
					        		}
					        		titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));
					            }
					            
					            if("ENF".equals(docGubun)) {
									if(rsCompId.equals(rsSenderCompId)) {
									    sendInfo = rsSenderDeptName;
									}else{
									    if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
							        		if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
							        		    sendInfo = rsSenderCompName;
							        		}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
							        		    sendInfo = rsSenderDeptName;
							        		}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
							        		    sendInfo = rsSenderCompName + "/" + rsSenderDeptName; 
							        		}
							            }
									}
								}
					            
					            if("Y".equals(rsTempYn)) {
					        		docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
					            }
					            
					            if(app100.equals(rsDocState)) {
					        		rsReturnDocYn = "Y";
					            }
					            
								StringBuffer buff;
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
// 								buff = new StringBuffer();
// 								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+rsReturnDocYn+"\"  >");								
								
// 								//S		 상세정보 버튼	20150311_csh		S
// 								buff.append("<input type=\"hidden\" name=\"lobCode\" id=\"lobCode\" value=\""+resultLobCode+"\">");
// 								buff.append("<input type=\"hidden\" name=\"transferYn\" id=\"transferYn\" value=\""+rsTransferYn+"\">");
// 								buff.append("<input type=\"hidden\" name=\"electronDocYn\" id=\"electronDocYn\" value=\""+electronDocYn+"\">");
// 								//E		 상세정보 버튼	20150311_csh		E
								
// 								row.setData(rowIndex, buff.toString());
// 								row.setAttribute(rowIndex, "class", "ltb_check");
// 								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
								row.setData(rowIndex, docTypeMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px; ");  //row 높이 변경 jskim_20150526
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
								    linkScriptName = "selectAppDocSec";
								}
								//end
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								//row.setAttribute(rowIndex, "rowDocNum",i);
								row.setAttribute(rowIndex, "title", titleMsg);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsDeptName);								
																
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								row.setAttribute(rowIndex, "title",titleDate);	

								row.setData(++rowIndex, "<a href=\"#\" id=\"ReturnApprove\" onclick=\"javascript:openReturnRecv('"+rsDocId+"');return(false);\" ><img src=\"" + webUri + "/app/ref/image/LH/button/btn_app_icon04.jpg\" border='0'></a>");
								
								row.setData(++rowIndex, "<a href=\"#\" id=\"ReturnApprove\" onclick=\"javascript:openRecvLine('"+rsDocId+"');return(false);\" ><img src=\"" + webUri + "/app/ref/image/LH/button/btn_app_icon03.jpg\" border='0'></a>");
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