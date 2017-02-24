<%@ page import="com.sds.acube.app.common.vo.AccessHisVO" %>
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
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminAccHis.jsp 
 *  Description : 접속이력 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 08. 24 
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
	List<AccessHisVO> results = (List<AccessHisVO>) request.getAttribute("ListVo");
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
	
	String msgHeaderUserName 	= messageSource.getMessage("list.list.title.headerUserName" , null, langType);
	String msgHeaderPos			= messageSource.getMessage("list.list.title.headerPos" , null, langType);
	String msgHeaderDeptName	= messageSource.getMessage("list.list.title.headerDeptName" , null, langType);
	String msgHeaderUserIp		= messageSource.getMessage("list.list.title.headerUserIp" , null, langType);
	String msgHeaderUseDate		= messageSource.getMessage("list.list.title.headerUseDate" , null, langType);
	String msgHeaderUsedType	= messageSource.getMessage("list.list.title.headerUsedType" , null, langType);
	String msgNoData 			= messageSource.getMessage("list.list.msg.noAccessHis" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminAccHis"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListAdminCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="list.list.title.listAdminAccHis"/></acube:titleBar></td>
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
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 7);
							acubeList.setColumnWidth("20,*,120,200,110,100,90");
							acubeList.setColumnAlign("center,center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
		
							titleRow.setData(++rowIndex,msgHeaderUserName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderPos);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
		
							titleRow.setData(++rowIndex,msgHeaderUserIp);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderUseDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderUsedType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    AccessHisVO result = (AccessHisVO) results.get(i);
								   
								String rsHisId		= CommonUtil.nullTrim(result.getHisId());
							    String rsUserId 	= CommonUtil.nullTrim(result.getUserId());
					            String rsUserName 	= EscapeUtil.escapeHtmlDisp(result.getUserName());
					            String rsPos		= EscapeUtil.escapeHtmlDisp(result.getPos());
					            String rsUserIp		= CommonUtil.nullTrim(result.getUserIp());
					            String rsDeptName	= EscapeUtil.escapeHtmlDisp(result.getDeptName());					            
					            String rsUseDate	= EscapeUtil.escapeDate(result.getUseDate());
					            String titleDate	= EscapeUtil.escapeDate(result.getUseDate());
					            String rsUsedType	= CommonUtil.nullTrim(result.getUsedType());
					            
					            String usedTypeMsg		= "";
					            
					            if(!"".equals(rsUsedType)) {
					        		usedTypeMsg = messageSource.getMessage("list.code.msg."+rsUsedType.toLowerCase() , null, langType);
					            }
					            
					            if(!"".equals(rsUseDate)){
					        		rsUseDate = DateUtil.getFormattedShortDate(rsUseDate);
					            }
					            
					            if(!"".equals(titleDate)){
					        		titleDate = DateUtil.getFormattedDate(titleDate);
					            }
					            
					            StringBuffer buff;
					            
								row = acubeList.createRow();
					
								rowIndex = 0;
								
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"stdId\" id=\"stdId\" value=\""+rsHisId+"\"  listFormChk=listFormChk  >");								
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsUserId+"');return(false);\">"+rsUserName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", rsUserName);
								
								row.setData(++rowIndex, rsPos);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsPos);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsDeptName);								
								
								row.setData(++rowIndex, rsUserIp);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");		
								row.setAttribute(rowIndex, "title",rsUserIp);
								
								row.setData(++rowIndex, rsUseDate);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");		
								row.setAttribute(rowIndex, "title",titleDate);								
								
								row.setData(++rowIndex, usedTypeMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",usedTypeMsg);

					
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

</Body>
</Html>