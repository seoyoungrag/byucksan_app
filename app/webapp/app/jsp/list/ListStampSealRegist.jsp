<%@ page import="com.sds.acube.app.common.vo.StampListVO" %>

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
 *  Class Name  : ListStampSealRegist.jsp 
 *  Description : 서명인날인대장 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 03 
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
	List<StampListVO> results = (List<StampListVO>) request.getAttribute("ListVo");
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
	
	String msgHeaderStampNumber		= messageSource.getMessage("list.list.title.headerStampNumber" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDocNumber		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderReceiver		= messageSource.getMessage("list.list.title.headerReceiver" , null, langType);
	String msgHeaderRequestName		= messageSource.getMessage("list.list.title.headerRequestName" , null, langType);
	String msgHeaderStampDate		= messageSource.getMessage("list.list.title.headerStampDate" , null, langType);
	String msgHeaderElecType		= messageSource.getMessage("list.list.title.headerElecType" , null, langType);
	
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	String msgTheRest 				= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	String msgCnt 					= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String deptAdminYn = (String) request.getAttribute("deptAdminYn");
	int totalCol = 7;
	String colWidth = "";
	String colAlign = "";
	
	if("Y".equals(deptAdminYn)){
	    totalCol = 8;
	    colWidth = "20,";
	    colAlign = "center,";
	}
	
	String opt363 = appCode.getProperty("OPT363", "OPT363", "OPT"); // 문서등록취소기능 사용여부
	opt363 = envOptionAPIService.selectOptionValue(compId, opt363);
	
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
		<jsp:include page="/app/jsp/list/common/ListRegistSearch.jsp" flush="true" />
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
							acubeList = new AcubeList(sLine, totalCol);
							acubeList.setColumnWidth(colWidth+"60,*,130,100,100,80,80");
							acubeList.setColumnAlign(colAlign+"center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							if("Y".equals(deptAdminYn)){
								titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
								titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
								titleRow.setAttribute(rowIndex,"style","padding-left:2px");
								
								titleRow.setData(++rowIndex,msgHeaderStampNumber);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}else{
							    titleRow.setData(rowIndex,msgHeaderStampNumber);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							}
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiver);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderRequestName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderStampDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderElecType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    StampListVO result = (StampListVO) results.get(i);
								
								String rsStampId			= CommonUtil.nullTrim(result.getStampId());
							    String rsDocId 				= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 			= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 				= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsRequesterId		= CommonUtil.nullTrim(result.getRequesterId());
					            String rsRequesterName		= EscapeUtil.escapeHtmlDisp(result.getRequesterName());
					            String rsRequesterDeptId	= CommonUtil.nullTrim(result.getRequesterDeptId());
					            String rsRequesterDeptName	= EscapeUtil.escapeHtmlDisp(result.getRequesterDeptName());
					            String rsDate 				= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getSealDate()));
					            String rsSealNumber			= CommonUtil.nullTrim(Integer.toString(result.getSealNumber()));
					            String rsDocNumber			= EscapeUtil.escapeHtmlDisp(result.getDocNumber());
					            String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsReceiverFront		= EscapeUtil.escapeHtmlDisp(result.getReceiverFront());
					            String rsReceiverCnt		= CommonUtil.nullTrim(result.getReceiverCnt());
					            String rsDeleteYn			= CommonUtil.nullTrim(result.getDeleteYn());
					          //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            //end
					            String electronDocMsg 		= "";
					            String titleDate			= "";
					            String receiverMsg			= "";
					            String deleteYn				= "N";
					            String titleMsg				= "";
					            String titleRequesterName	= "";
					            String confirmYn 			= "Y";
					            
					            titleMsg 			= rsTitle;
					            titleRequesterName	= rsRequesterName;
					            
					            if("Y".equals(electronDocYn)) {
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSealDate()));
					            }else{
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		titleDate	   = rsDate;
					            }
					            
					            if(!"".equals(rsReceiverFront) && !"0".equals(rsReceiverCnt)) {
					        		receiverMsg = rsReceiverFront+" "+msgTheRest+" "+rsReceiverCnt+msgCnt;
					            }else if(!"".equals(rsReceiverFront) &&  "0".equals(rsReceiverCnt)) {
					        		receiverMsg = rsReceiverFront;
					            }
					            
					            if("0".equals(rsSealNumber)) {
					        		rsSealNumber = messageSource.getMessage("list.list.msg.notApplicable" , null, langType);
					            }
					            
					            if("N".equals(electronDocYn) && "N".equals(rsDeleteYn) ){
					        		deleteYn = "Y";
					            }
					            
					            if("Y".equals(deleteYn) && !"999999".equals(rsSealNumber) && "N".equals(opt363) ){
					        		deleteYn = "N";
					            }
					            
					            if("Y".equals(rsDeleteYn)){
					        		titleMsg = "<font color='red'>"+rsTitle+"</font>";
				        			titleRequesterName = "<font color='red'>"+rsRequesterName+"</font>";
					            }
					            
					            if("999999".equals(rsSealNumber)) {
					        		rsSealNumber = messageSource.getMessage("list.msg.stampSeal.nonConfirm" , null, langType);
					        		confirmYn = "N";
					            }
					            
					            StringBuffer buff;

								
								row = acubeList.createRow();
								if("Y".equals(electronDocYn)) {
									row.setAttribute("id", rsDocId);
								}else{
								    row.setAttribute("id", rsStampId);
								}
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								if("Y".equals(deptAdminYn)){
								    
								
									buff = new StringBuffer();
									buff.append("<input type=\"checkbox\"  name=\"stampId\" id=\"stampId\" value=\""+rsStampId+"\"  listFormChk=\""+deleteYn+"\" confirmAuthYn=\""+confirmYn+"\" >");								
									
									row.setData(rowIndex, buff.toString());
									row.setAttribute(rowIndex, "class", "ltb_check");
									row.setAttribute(rowIndex,"style","vertical-align:top;");
									
									row.setData(++rowIndex, rsSealNumber);
									if("Y".equals(rsDeleteYn)) {
										row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
									}
									row.setAttribute(rowIndex, "title",rsSealNumber);
									
								}else{
								    
								    row.setData(rowIndex, rsSealNumber);
									if("Y".equals(rsDeleteYn)) {
										row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
									}
									row.setAttribute(rowIndex, "title",rsSealNumber);								    
								}
								//jkkim added 보안문서 아이콘 표시 start 
								boolean isDuration = false;
								String linkScriptName = "selectOnlyAppDoc";
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
								    titleMsg = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + titleMsg;
								    linkScriptName = "selectOnlyAppDocSec";
								}
								//end
								if("Y".equals(rsDeleteYn)){
								    row.setData(++rowIndex, titleMsg);
								}else{
									if("Y".equals(electronDocYn)) {
										row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+titleMsg+"</A>");
									}else{
									    row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:selectNonSealDoc('"+rsStampId+"','"+resultLobCode+"', '"+electronDocYn+"','N');\">"+titleMsg+"</A>");
									}	
								}
								
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", rsTitle);								
								
								
								row.setData(++rowIndex, rsDocNumber);
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title",rsDocNumber);
								
								row.setData(++rowIndex, receiverMsg);								
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title",receiverMsg);
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsRequesterId+"');return(false);\">"+titleRequesterName+"</A>");
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title",rsRequesterName);								
								
								if("Y".equals(electronDocYn)) {
									row.setData(++rowIndex, rsDate+"<a href=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\"  onclick=\"javascript:selectOnlyAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','Y');\"> </A>");
								}else{
								    row.setData(++rowIndex, rsDate+"<a href=\"#\" id=\"a_"+rsStampId+"\" elecYn=\""+electronDocYn+"\"  onclick=\"javascript:selectNonSealDoc('"+rsStampId+"','"+resultLobCode+"', '"+electronDocYn+"','Y');\"> </A>");
								}
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, electronDocMsg);
								if("Y".equals(rsDeleteYn)) {
								    row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}
								row.setAttribute(rowIndex, "title",electronDocMsg);
								
					
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
     <jsp:include page="/app/jsp/list/common/ListPagingRegistForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
</acube:outerFrame>

</Body>
</Html>