<%@ page import="com.sds.acube.app.relay.vo.RelayAckHisVO" %>
<%@ page import="com.sds.acube.app.common.vo.OrganizationVO" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminRelayResult.jsp 
 *  Description : 문서유통 연계이력
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김상태
 *  @since 2012. 05. 04 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	
	IOrgService orgService = (IOrgService)ctx.getBean("orgService");

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// 검색 결과 값
	List<RelayAckHisVO> results = (List<RelayAckHisVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();

	String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());

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
	
	String msgHeaderKind 			= messageSource.getMessage("list.relayAck.title.headerKind" , null, langType);
	String msgHeaderDocId 			= messageSource.getMessage("list.relayAck.title.headerDocId" , null, langType);
	String msgHeaderCompId			= messageSource.getMessage("list.relayAck.title.headerCompId" , null, langType);
	String msgHeaderReceiveId		= messageSource.getMessage("list.relayAck.title.headerReceiveId" , null, langType);
	String msgHeaderAckType			= messageSource.getMessage("list.relayAck.title.headerAckType" , null, langType);
	String msgheaderAckDate			= messageSource.getMessage("list.relayAck.title.headerAckDate" , null, langType);
	String msgheaderAckXml			= messageSource.getMessage("list.relayAck.title.headerAckXml" , null, langType);
	String msgheaderRegistDate		= messageSource.getMessage("list.relayAck.title.headerRegistDate" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	String msgKindSend				= messageSource.getMessage("list.relay.title.headerKind.send" , null, langType);
	String msgKindReceive			= messageSource.getMessage("list.relay.title.headerKind.receive" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
		<title><spring:message code="list.list.title.listAdminRelayResult"/></title>
		<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
		<jsp:include page="/app/jsp/list/common/ListAdminCommon.jsp" flush="true" />
		<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
	</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<acube:outerFrame>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><acube:titleBar><spring:message code="list.list.title.listAdminRelayAckResult"/></acube:titleBar></td>
				</tr>
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
												AcubeList acubeList = null;
												acubeList = new AcubeList(sLine, 6);
												acubeList.setColumnWidth("80, 100, 100, *, 100, 130");
												acubeList.setColumnAlign("center, center, center, center, center, center");	 
							
												AcubeListRow titleRow = acubeList.createTitleRow();
												int rowIndex=0;
												
												titleRow.setData(rowIndex,msgHeaderKind);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												
												titleRow.setData(++rowIndex,msgHeaderCompId);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												
												titleRow.setData(++rowIndex,msgHeaderReceiveId);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												
												titleRow.setData(++rowIndex,msgHeaderDocId);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

												titleRow.setData(++rowIndex,msgHeaderAckType);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												
												titleRow.setData(++rowIndex,msgheaderRegistDate);
												titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
												AcubeListRow row = null;
												
												// 데이타 개수만큼 돈다...(행출력)
												for(int i = 0; i < nSize; i++) {
												    
													RelayAckHisVO result = (RelayAckHisVO) results.get(i);
													
													String hisId 		= CommonUtil.nullTrim(result.getHisId());
													String docId 		= CommonUtil.nullTrim(result.getDocId());
													String sendId		= CommonUtil.nullTrim(result.getCompId());
										            String receiveId 	= CommonUtil.nullTrim(result.getReceiveId());
													String ackType		= CommonUtil.nullTrim(result.getAckType());
										            String ackDate		= CommonUtil.nullTrim(result.getAckDate());
										            String ackDept		= CommonUtil.nullTrim(result.getAckDept());
										            String ackName		= CommonUtil.nullTrim(result.getAckName());					            
										            String ackXml		= CommonUtil.nullTrim(result.getAckXml());
										            String registDate	= CommonUtil.nullTrim(result.getRegistDate());
										            
													row = acubeList.createRow();
										
													rowIndex = 0;
													
													String relayKind = "";
													OrganizationVO orgInfo = orgService.selectOrganization(result.getCompId());
													if(orgInfo != null) {
														relayKind = msgKindSend;
													} else {
														relayKind = msgKindReceive;
													}
													
													row.setData(rowIndex, relayKind);
													row.setAttribute(rowIndex, "title", relayKind);
													
													row.setData(++rowIndex, sendId);
													row.setAttribute(rowIndex, "title", sendId);
													
													row.setData(++rowIndex, receiveId);
													row.setAttribute(rowIndex, "title", receiveId);
													
													row.setData(++rowIndex, "<a href=\"#\"  onclick=\"javascript:selectRelayAckResultDetail('"+docId+"');\">"+docId+"</A>");
													row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
													row.setAttribute(rowIndex, "title", docId);
													
													row.setData(++rowIndex, ackType);
													row.setAttribute(rowIndex, "title",ackType);
													
													row.setData(++rowIndex, registDate);
													row.setAttribute(rowIndex, "title",registDate);
					
										
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
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- 페이징관련 form -->
			<jsp:include page="/app/jsp/list/common/ListPagingAdminForm.jsp" flush="true" /> 
			<!-- 페이징관련 form  끝-->
		</acube:outerFrame>
	</body>
</html>