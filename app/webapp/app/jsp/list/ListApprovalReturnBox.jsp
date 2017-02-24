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

<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListApprovalReturnBox.jsp 
 *  Description : 반려함 리스트 
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
	String msgHeaderLastUpdateDate	= messageSource.getMessage("list.list.title.headerLastUpdateDate" , null, langType);
	String msgHeaderSenderName 		= messageSource.getMessage("list.list.title.headerSenderName" , null, langType);
	String msgHeaderDocSate	 		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
																														// 컬럼 수정 20150603_jskim			
	String msgHeaderCheck			= messageSource.getMessage("list.relay.title.headerCheck" , null, langType);        //확인 
	String msgHeaderAttach 			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);        //첨부
	String msgHeaderSender	 		= messageSource.getMessage("list.list.title.headerSender" , null, langType);        //전달
	String msgHeaderReturnDate 		= messageSource.getMessage("list.list.title.headerReturnDate" , null, langType);    //반려일자
	//==============================================================================

	int curPage=CPAGE;	//현재페이지
	
	//	반려 의견		20150905_csh
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
		<jsp:include page="/app/jsp/list/common/ListSearch.jsp" flush="true" />
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
							//S		체크박스 추가	20150311_csh		S
							acubeList = new AcubeList(sLine, 6);			// 컬럼 수정 20150603_jskim		
							acubeList.setColumnWidth("20,*,80,80,40,60");
							acubeList.setColumnAlign("center,left,center,center,center,center");
							//E		체크박스 추가	20150311_csh		E
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							//S		체크박스 추가	20150311_csh		S
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							//E		체크박스 추가	20150311_csh		E
							
							//	문서제목
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
		
							//	상신일자
							titleRow.setData(++rowIndex,msgHeaderLastUpdateDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							//	반려일자
							titleRow.setData(++rowIndex,msgHeaderReturnDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							//	첨부							
							titleRow.setData(++rowIndex,msgHeaderAttach);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	전달
							titleRow.setData(++rowIndex,msgHeaderSender);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
							String tempAttachYn = "N";
						
			 				String apt004 = appCode.getProperty("APT004","APT004","APT");
			 				String app100 = appCode.getProperty("APP100","APP100","APP");			 				
			 				String enf500 = appCode.getProperty("ENF500","ENF500","ENF");
			 				
			 				String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
			 				String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
			 				String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
			 					
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId 		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDeptName 		= EscapeUtil.escapeHtmlDisp(result.getDrafterDeptName());
					            String rsDrafterName 	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
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
					            int rsAttach				= result.getAttachCount();
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
					            
								//	반려의견	20150905_csh
					            String returnOpinion	= "";
					            String currentAskType	= "";
								boolean returnCheck		= false;
					            List<AppLineVO> lineVO = approvalService.listAppLine(rsCompId, rsDocId);	//	결재경로
					            
					            for ( int loop=0; loop < lineVO.size(); loop++ ) {
					            	String procType = lineVO.get(loop).getProcType();
					            	String askType = lineVO.get(loop).getAskType();
					            	
					            	if ( procType.equals(apt002) && 
					            			(askType.contains("ART04") || askType.contains("ART08") || askType.contains("ART09")) ) {
					            		returnOpinion = messageSource.getMessage("list.list.msg.return", null, langType) + " " + lineVO.get(loop).getProcOpinion();
					            		returnCheck = true;
					            		currentAskType = askType;
					            	}
					            }
					            
					            if ( CommonUtil.isNullOrEmpty(returnOpinion) ) {
					            	titleMsg = rsTitle;
					            } else {
					            	titleMsg = returnOpinion;					            	
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
						        	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
					            }else{
					        		if("APP".equals(docGubun)){
					        		    linkScriptName = "selectNonAppDoc";  
					        		}else{
					        		    linkScriptName = "selectNonEnfDoc";
					        		}
					        		titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
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
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+rsReturnDocYn+"\"  >");								
								
								//S		 상세정보 버튼	20150311_csh		S
								buff.append("<input type=\"hidden\" name=\"lobCode\" id=\"lobCode\" value=\""+resultLobCode+"\">");
								buff.append("<input type=\"hidden\" name=\"transferYn\" id=\"transferYn\" value=\""+rsTransferYn+"\">");
								buff.append("<input type=\"hidden\" name=\"electronDocYn\" id=\"electronDocYn\" value=\""+electronDocYn+"\">");
								//E		 상세정보 버튼	20150311_csh		E
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:center;");
								
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
								
								//	문서제목
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;");
								row.setAttribute(rowIndex, "rowDocNum",i);
								row.setAttribute(rowIndex, "title", titleMsg);
								
								//	상신일자
								row.setData(++rowIndex, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(titleDate)));
								row.setAttribute(rowIndex, "title",titleDate);			// 컬럼 수정 20150603_jskim
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								//	반려일자
								row.setData(++rowIndex, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate())));
								row.setAttribute(rowIndex, "title", result.getLastUpdateDate());
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
								//	첨부
								if(rsAttach > 0){   //리스트 함 컬럼 수정 20150601_jskim
// 								    if("Y".equals(rsUnRegistYn)) {
// 										row.setData(++rowIndex, "<img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'>");
// 								    }else{										
										row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','"+tempAttachYn+"');fncMoveAttachDiv(event);\" ><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'></a>");
// 								    }
								}else{
								    row.setData(++rowIndex, "");
								}

								//	전달(3종세트인 문서만 해당)		20150909_csh
								if ( returnCheck ) {
									row.setData(++rowIndex, "<a href=\"#\" id=\"sendApprove\" onclick=\"javascript:returnView('"+rsDocId+"', '"+currentAskType+"');return(false);\" ><img src=\"" + webUri + "/app/ref/image/LH/button/btn_app_icon04.jpg\" border='0'></a>");
								} else {
									row.setData(++rowIndex, "");	
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
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
</acube:outerFrame>

</Body>
</Html>