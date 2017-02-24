<%@ page import="com.sds.acube.app.common.vo.OpinionListVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : ListDailyControlOpinion.jsp 
 *  Description : 기획통제 의견서 리스트 
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
	List<OpinionListVO> results = (List<OpinionListVO>) request.getAttribute("ListVo");
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
	
	String msgHeaderReceiveNumber	= messageSource.getMessage("env.document.number.rule.serialnum" , null, langType);
	String msgHeaderReceiveDate		= messageSource.getMessage("list.list.title.headerRegistDate" , null, langType);
	String msgHeadeChargeDeptName	= messageSource.getMessage("list.list.title.headerDraftDeptName" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerOpinionTitle" , null, langType);
	String msgHeaderCustomer		= messageSource.getMessage("list.list.title.headerCustomer" , null, langType);
	String msgHeaderActionDate		= messageSource.getMessage("list.list.title.headerActionDate" , null, langType);	
	String msgHeaderDocNumber		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderRemark			= messageSource.getMessage("list.list.title.remark" , null, langType);
	
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
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
							acubeList = new AcubeList(sLine, 9);
							acubeList.setColumnWidth("20,60,80,70,80,*,100,120,120");
							acubeList.setColumnAlign("center,center,center,center,center,left,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;							
							
							titleRow.setData(rowIndex,"<img src=\""+webUri+"/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							//	담당자
							titleRow.setData(++rowIndex,msgHeaderCustomer);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	기안부서
							titleRow.setData(++rowIndex,msgHeadeChargeDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	제목
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	조치일자
							titleRow.setData(++rowIndex,msgHeaderActionDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	문서번호
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	비고
							titleRow.setData(++rowIndex,msgHeaderRemark);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							/* 
							String art040 = appCode.getProperty("ART040", "ART040", "ART"); 
							String art042 = appCode.getProperty("ART042", "ART042", "ART");
							String art043 = appCode.getProperty("ART043", "ART043", "ART");
							String art044 = appCode.getProperty("ART044", "ART044", "ART");
							String apt001 = appCode.getProperty("APT001", "APT001", "APT");
							String apt002 = appCode.getProperty("APT002", "APT002", "APT");
							 */     
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
								OpinionListVO result = (OpinionListVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());					//	DOC_ID
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());					//	COM_ID            
					            int rsReceiveNumber		= result.getAuditNumber();									//	AUDIT_NUMBER
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
					            String rsChargeDeptName	= EscapeUtil.escapeHtmlDisp(result.getChargeDeptName());	//	CHARGE_DEPT_NAME
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());				//	TITLE
					            String rsRegisterName	= CommonUtil.nullTrim(result.getRegisterName());			//	REGISTER_NAME
					            String rsReceiveType 	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsDocNumber		= CommonUtil.nullTrim(result.getDocNumber());				//	DOC_NUMBER
					            String rsAskType		= CommonUtil.nullTrim(result.getAskType());
					            String rsProcType		= CommonUtil.nullTrim(result.getProcType());
					            String rsDeleteYn		= "N";//CommonUtil.nullTrim(result.getDeleteYn());
					           	String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					           	String rsRemark			= CommonUtil.nullTrim(result.getRemark());					//	REMARK
					            String titleMsg			= "";
					            String docState			= "";
					            String rsActionDate		= EscapeUtil.escapeDate(result.getActionDate());	
					            String deleteAuthYn		= "N";
					            String titleDate		= "";
					            String modAuthYn		= "Y";
					            
					            
					            titleMsg = rsTitle;
						        if("Y".equals(rsDeleteYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            if("Y".equals(rsReceiveType)) {
					        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
					            }else{
					        		titleDate	   = rsDate;
					            }
					            
					            if("N".equals(rsReceiveType) &&  "N".equals(rsDeleteYn)) {
					        		deleteAuthYn = "Y";
					            }
					            
					            if("Y".equals(rsDeleteYn)) {
					        		modAuthYn = "N";
					            }

					            StringBuffer buff;
					            
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;

								//	체크박스
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+deleteAuthYn+"\" modifyChk=\""+modAuthYn+"\">");
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:center;");  // 체크박스 위치 변경  jskim_20150526
								
								//	일련번호
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsReceiveNumber+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsReceiveNumber);
								}
								row.setAttribute(rowIndex, "title",rsReceiveNumber);
								
								//	등록일자
								if("Y".equals(rsReceiveType)) {
									row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectOnlyAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','Y');\"> </A>");
								}else{
								    row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectDailyAuditDoc('"+rsDocId+"', '"+electronDocYn+"','Y');\"> </A>");
								}
								if("Y".equals(rsDeleteYn)) {									
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}
								row.setAttribute(rowIndex, "title",titleDate);

								//	담당자
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsRegisterName+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsRegisterName);
								}
								row.setAttribute(rowIndex, "title",rsRegisterName);
								
								//	기안부서
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsChargeDeptName+"</font>");
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsChargeDeptName);
								}
								row.setAttribute(rowIndex, "title",rsChargeDeptName);
								
								//	건명
								if("Y".equals(rsDeleteYn)) {
								    row.setData(++rowIndex, rsTitle);
								}else{
									row.setData(++rowIndex, "<a href=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\"  onclick=\"javascript:selectOnlyAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','N');\">"+rsTitle+"</A>");
								}
								if("Y".equals(rsDeleteYn)) {
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);
								
								//	조치일자
								buff = new StringBuffer();
								buff.append("<input type=\"text\" class=\"input_read_center\" name=\"actionDate"+i+"\" id=\"actionDate"+i+"\" readonly size=\"12\" value=\""+DateUtil.getFormattedDate(rsActionDate, dateFormat)+"\" onclick=\"javascript:cal.select(event, document.getElementById('actionDate'), document.getElementById('actionDate"+i+"'), 'actionDate"+i+"', '"+dateFormat+"');\">");
								buff.append("<input type=\"hidden\" name=\"actionDate\" id=\"actionDate\" size=\"12\" value=\"\">");
							    row.setData(++rowIndex, buff.toString());
								
							    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;");  //row 높이 변경

								
								//	문서번호
							    row.setData(++rowIndex, "<input type=\"text\" class=\"input_read\" id=\"docNumber\" name=\"docNumber\" size=\"16\" value=\""+rsDocNumber+"\" docNumChk=\""+modAuthYn+"\">");
								
								//	비고
							    row.setData(++rowIndex, "<input type=\"text\" class=\"input_read\" id=\"remark\" name=\"remark\" size=\"16\" value=\""+rsRemark+"\" remarkChk=\""+modAuthYn+"\">");
								
					
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