<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.anyframe.pagination.Page"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List"%>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : PrintListReceiptStatus.jsp 
 *  Description : 부서별 문서미처리 목록 인쇄
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
	List<EnfDocVO> enfDocVOs = (List<EnfDocVO>) request.getAttribute("ListVO");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = enfDocVOs.size();
	
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
	String msgHeaderReceiveDate 	= messageSource.getMessage("list.list.title.headerReceiveDate" , null, langType);
	String msgHeaderSendDeptName 	= messageSource.getMessage("list.list.title.headerSendDeptName" , null, langType);
	String msgHeaderEnfType 		= messageSource.getMessage("list.list.title.headerEnfType" , null, langType); 
	String msgHeaderAccepter = messageSource.getMessage("statistics.header.accepter", null, langType);
	String msgHeaderLastUpdateDate = messageSource.getMessage("statistics.header.lastUpdateDate", null, langType);
	String msgHeaderDocKind = messageSource.getMessage("statistics.header.docKind", null, langType);
	String msgElectronDoc = messageSource.getMessage("statistics.header.electronDoc", null, langType);
	String msgNonElectronDoc = messageSource.getMessage("statistics.header.nonElectronDoc", null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	int curPage=CPAGE;	//현재페이지
	
	String det001 = appCode.getProperty("DET001","DET001","DET"); // 내부
	String det002 = appCode.getProperty("DET002","DET002","DET"); // 대내
	String det003 = appCode.getProperty("DET003","DET003","DET"); // 대외
	
	String listTitle = messageSource.getMessage("statistics.title.receiptStatusStatistics", null, langType);
	if ("RECV".equals(resultSearchDocType)) {
	    listTitle += " - [" + resultSearchDeptName + "/" + messageSource.getMessage("statistics.header.searchTypeRecv", null, langType) + "]";
	} else if ("LINE".equals(resultSearchDocType)) {
	    listTitle += " - [" + resultSearchDeptName + "/" + messageSource.getMessage("statistics.header.searchTypeLine", null, langType) + "]";
	}
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
							acubeList = new AcubeList(sLine, 5);
							if ("RECV".equals(resultSearchDocType)) {
								acubeList.setColumnWidth("*,140,80,80,160");
							} else if ("LINE".equals(resultSearchDocType)) {
								acubeList.setColumnWidth("*,140,80,80,80");
							}
							acubeList.setColumnAlign("left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							if ("RECV".equals(resultSearchDocType)) {
								titleRow.setData(++rowIndex,msgHeaderEnfType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			
								titleRow.setData(++rowIndex,msgHeaderReceiveDate);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderSendDeptName);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							} else if ("LINE".equals(resultSearchDocType)) {
								titleRow.setData(++rowIndex,msgHeaderAccepter);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			
								titleRow.setData(++rowIndex,msgHeaderLastUpdateDate);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderDocKind);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
	
							AcubeListRow row = null;
			 				
							// 데이타 개수만큼 돈다...(행출력)
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        } else {
								for(int i = 0; i < nSize; i++) {
								    
								    EnfDocVO enfDocVO = (EnfDocVO) enfDocVOs.get(i);
									
									String rsDocId 			= CommonUtil.nullTrim(enfDocVO.getDocId());
						            String rsTitle			= EscapeUtil.escapeHtmlDisp(enfDocVO.getTitle());
						            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(enfDocVO.getDocNumber());
									if ("LINE".equals(resultSearchDocType)) {
									    rsDocNumber = CommonUtil.nullTrim(enfDocVO.getDeptCategory()) + "-" + rsDocNumber;
									}
						            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(enfDocVO.getSenderCompName());
						            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(enfDocVO.getSenderDeptName());
						            String rsEnfType		= CommonUtil.nullTrim(enfDocVO.getEnfType());
						            String urgencyYn		= CommonUtil.nullTrim(enfDocVO.getUrgencyYn());
						            String electronDocYn	= CommonUtil.nullTrim(enfDocVO.getElectronDocYn());
						            String rsOriginCompId	= CommonUtil.nullTrim(enfDocVO.getOriginCompId());
						            String rsReceiveDate 	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(enfDocVO.getReceiveDate()));
						            
						            String rsAcceperName = EscapeUtil.escapeHtmlDisp(enfDocVO.getAccepterName());
						            String rsLastUpdateDate = EscapeUtil.escapeDate(enfDocVO.getLastUpdateDate());
						            
						            String titleMsg			= "";
						            String senderInfo 		= "";
						            String msgEnfType		= "";
						            String titleDate		= "";
						            String linkScriptName	= "";
						            String otherCompId		= "";
						            
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
						            }else{
						        		msgEnfType = "";
						            }
						            
						            titleMsg = rsTitle;
						            if("Y".equals(urgencyYn)) {
						        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
						            }
						            
						            if("Y".equals(electronDocYn)) {
						        		titleDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(enfDocVO.getReceiveDate()));
						        		otherCompId = rsOriginCompId;
						            }else{
						        		titleDate	= rsReceiveDate;
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
									
									if ("RECV".equals(resultSearchDocType)) {
										row.setData(++rowIndex, msgEnfType);
										row.setAttribute(rowIndex, "title",msgEnfType);
										
										row.setData(++rowIndex, rsReceiveDate);
										row.setAttribute(rowIndex, "title",titleDate);
										
										row.setData(++rowIndex, senderInfo);
										row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										row.setAttribute(rowIndex, "title",senderInfo);								
									} else if ("LINE".equals(resultSearchDocType)) {
										row.setData(++rowIndex, rsAcceperName);
										row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										row.setAttribute(rowIndex, "title", rsAcceperName);								

										row.setData(++rowIndex, DateUtil.getFormattedShortDate(rsLastUpdateDate));
										row.setAttribute(rowIndex, "title", rsLastUpdateDate);
							
										row.setData(++rowIndex, "Y".equals(electronDocYn) ? msgElectronDoc : msgNonElectronDoc);
										row.setAttribute(rowIndex, "title", "Y".equals(electronDocYn) ? msgElectronDoc : msgNonElectronDoc);									
									}
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