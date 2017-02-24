<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.board.vo.AppBoardVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="java.util.HashMap,
				 java.util.Map" 
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListSearch.jsp 
 *  Description : 리스트 공통 검색  
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 05 
 *  @version 1.0 
 *  @see
 */ 
%>

<%

String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String curPageStr = request.getAttribute("curPage").toString();
String cPageStr = request.getParameter("cPage");
if(cPageStr != null && !cPageStr.equals(curPageStr)){
    cPageStr = curPageStr;
}	
int CPAGE = 1;
if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);

String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
String resultStartDate	= CommonUtil.nullTrim(resultSearchVO.getStartDate());
String resultEndDate	= CommonUtil.nullTrim(resultSearchVO.getEndDate());
String startDateId 		= "";
String endDateId 		= "";

String searchWordStyle = "block";
if("searchSaveDate".equals(resultSearchType) || "searchBizDate".equals(resultSearchType) || "searchDraftDate".equals(resultSearchType) || "searchCompleteDate".equals(resultSearchType)){
	searchWordStyle = "none";
}

if(!"".equals(resultStartDate) || !"".equals(resultEndDate)) {
	resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
	resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);
	
	startDateId = DateUtil.getFormattedDate(resultStartDate+" 00:00:00", "yyyyMMdd");
	endDateId = DateUtil.getFormattedDate(resultEndDate+" 00:00:00", "yyyyMMdd");
}

String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
String buttonReset = messageSource.getMessage("list.list.button.reset" , null, langType);

//상세조건
String resultStartDocNum 			= CommonUtil.nullTrim(resultSearchVO.getStartDocNum());
String resultEndDocNum 				= CommonUtil.nullTrim(resultSearchVO.getEndDocNum());
String resultBindingId 				= CommonUtil.nullTrim(resultSearchVO.getBindingId());
String resultBindingName 			= CommonUtil.nullTrim(resultSearchVO.getBindingName());
String resultSearchElecYn 			= CommonUtil.nullTrim(resultSearchVO.getSearchElecYn());
String resultSearchNonElecYn 		= CommonUtil.nullTrim(resultSearchVO.getSearchNonElecYn());
String resultSearchDetType 			= CommonUtil.nullTrim(resultSearchVO.getSearchDetType());
String resultSearchApprovalName 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprovalName());
String resultSearchApprTypeApproval = CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeApproval());
String resultSearchApprTypeExam 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeExam());
String resultSearchApprTypeCoop 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeCoop());
String resultSearchApprTypeDraft 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeDraft());
String resultSearchApprTypePreDis 	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypePreDis());
String resultSearchApprTypeResponse = CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeResponse());
String resultSearchApprTypeAudit	= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeAudit());
//상세조건 끝


int searchTypeSize = 0;
int searchTypeRerultSize = 0;
String searchTypeOpt = "";
String searchTypeOptName = "";
Map<String,String[]> searchTypeMap	= new HashMap<String,String[]>();
String[] lob001 = {"searchTitle","list.list.button.searchTypeTitle","searchSaveDate","list.list.button.searchTypeSaveDate"};
String[] lob002 = {"searchTitle","list.list.button.searchTypeTitle","searchBizDate","list.list.button.searchTypeBizDraftDate"};
String[] lob003 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDraftDate","list.list.button.searchTypeDraftDate"};
String[] lob005 = {"searchTitle","list.list.button.searchTypeTitle","searchRecvDeptName","list.list.button.searchTypeReceiveDeptName","searchCompleteDate","list.list.button.searchTypeCompleteDate"};
String[] lob006 = {"searchTitle","list.list.button.searchTypeTitle","searchRecvDeptName","list.list.button.searchTypeReceiveDeptName","searchCompleteDate","list.list.button.searchTypeCompleteDate"};
String[] lob007 = {"searchTitle","list.list.button.searchTypeTitle","searchSendDeptName","list.list.button.searchTypeSendDeptName"};
String[] lob008 = {"searchTitle","list.list.button.searchTypeTitle","searchSendDeptName","list.list.button.searchTypeSendDeptName"};
String[] lob019 = {"searchTitle","list.list.button.searchTypeTitle","searchSendDeptName","list.list.button.searchTypeSendDeptName"};
String[] lob110 = {"searchTitle","list.list.button.searchTypeTitle","searchTypeUserName","list.list.button.searchTypeUserName"};
String[] lob026 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDraftDate","list.list.button.searchTypeDraftDate"};
String[] lob027 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDraftDate","list.list.button.searchTypeDraftDate"};

searchTypeMap.put("LOB001",lob001);
searchTypeMap.put("LOB002",lob002);
searchTypeMap.put("LOB003",lob003);
searchTypeMap.put("LOB005",lob005);
searchTypeMap.put("LOB006",lob006);
searchTypeMap.put("LOB007",lob007);
searchTypeMap.put("LOB008",lob008);
searchTypeMap.put("LOB019",lob019);
searchTypeMap.put("LOB110",lob110);
searchTypeMap.put("LOB026",lob026);
searchTypeMap.put("LOB027",lob027);

searchTypeSize = searchTypeMap.get(resultSearchVO.getLobCode()).length;
searchTypeRerultSize = searchTypeSize / 2;

//out.println("searchTypeRerultSize : " + searchTypeRerultSize);

//out.println(searchTypeMap.get("LOB001")[2]);

%>
	<tr>
			<acube:space between="button_search" />
	</tr>
	<tr>
		<td>
			<table class="tablesearch" cellspacing="0" cellpadding="0" width="100%">

				<tr>

					<td>
					<form name="listSearch" id="listSearch" method="post" style="margin:0px">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50">
								<select name="searchType" class="select" onchange="changeSearchType(this.value);">
									<%
									if(searchTypeRerultSize > 0){
										for(int i = 0; i < searchTypeRerultSize; i++){ 
										    
											searchTypeOpt = searchTypeMap.get(resultSearchVO.getLobCode())[i *2] ;
											searchTypeOptName = searchTypeMap.get(resultSearchVO.getLobCode())[(i*2)+1]; 
											
										%>
										<option value="<%=searchTypeOpt%>" <% if(searchTypeOpt.equals(resultSearchType)) { %>selected <% } %>><spring:message code="<%=searchTypeOptName%>"/></option>
									<%
										}
									}
									%>
								</select>
							</td>
							<td width="10"></td>
							<td width="400" id="searchWordTd" style="display:<%=searchWordStyle%>">
								<input type="text" class="input" name="searchWord" id="searchWord" maxlength="25" style="width: 100%;display:<%=searchWordStyle%>" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearch();}" >
							</td>	
							<td width="20"></td>
							<td>
							<%if("searchSaveDate".equals(resultSearchType) || "searchBizDate".equals(resultSearchType) || "searchDraftDate".equals(resultSearchType) || "searchCompleteDate".equals(resultSearchType)){%>
							<td id="searchTypeDateFromTd" style="display:block">								
								<div id="searchTypeDateDiv" style="display:block">
							<% }else{ %>
							<td id="searchTypeDateFromTd" style="display:none">
								<div id="searchTypeDateDiv" style="display:none">
							<% } %>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr class="search">
										<td>
											<input type="text" class="input_read" name="startDate" id="startDate" readonly size="11" value="<%= resultStartDate %>">
											<img id="calendarBTN1" name="calendarBTN1" 
												src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
												align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
												onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 
												'<%= dateFormat %>');">
											<input type="hidden" name="startDateId" id="startDateId" value="<%= startDateId %>">
												&nbsp;~&nbsp;
											<input type="text" class="input_read" name="endDate" id="endDate" readonly size="11" value="<%= resultEndDate %>">
											<img id="calendarBTN2" name="calendarBTN2" 
												src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
												align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
												onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN2', 
												'<%= dateFormat %>');">
											<input type="hidden" name="endDateId" id="endDateId" value="<%= endDateId %>">
										</td>
									</tr>
								</table>
								</div>    
							</td>
							<%if("searchSaveDate".equals(resultSearchType) || "searchBizDate".equals(resultSearchType) || "searchDraftDate".equals(resultSearchType) || "searchCompleteDate".equals(resultSearchType)){%>
							<td width="20" id="searchTypeDateTailTd" style="display:block"></td>
							<%}else{ %>
							<td width="20" id="searchTypeDateTailTd" style="display:none"></td>
							<% } %>
							
							</td>
							<td>
								<a href="#" onclick="javascript:goSearch();"><img src="<%=webUri%>/app/ref/image/bt_search.gif" alt="검색" /></a>
								<%--<acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" />--%>
								<%-- <acube:button onclick="reset();changeSearchType('searchTitle');" value='<%=buttonReset%>' type="reset" align="left" disable="" /> --%>
							</td>
						</tr>					
					</table>
					<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">
					<input name="curRowDocNum" id="curRowDocNum" type="hidden">
					<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N">
					<input type="hidden" name="excelExportYn" id="excelExportYn" value="N">	
					
					<input name="startDocNum" id="startDocNum"  type="hidden" value="<%=resultStartDocNum%>">
			        <input name="endDocNum" id="endDocNum"  type="hidden" value="<%=resultEndDocNum%>">
			        <input name="BindingId" id="BindingId"  type="hidden" value="<%=resultBindingId%>">
			        <input name="BindingName" id="BindingName"  type="hidden" value="<%=resultBindingName%>">
			        <input name="searchElecYn" id="searchElecYn"  type="hidden" value="<%=resultSearchElecYn%>">
			        <input name="searchNonElecYn" id="searchNonElecYn"  type="hidden" value="<%=resultSearchNonElecYn%>">
			        <input name="searchDetType" id="searchDetType"  type="hidden" value="<%=resultSearchDetType%>">
			        <input name="searchApprovalName" id="searchApprovalName"  type="hidden" value="<%=resultSearchApprovalName%>">
			        <input name="searchApprTypeApproval" id="searchApprTypeApproval"  type="hidden" value="<%=resultSearchApprTypeApproval%>">
			        <input name="searchApprTypeExam" id="searchApprTypeExam"  type="hidden" value="<%=resultSearchApprTypeExam%>">
			        <input name="searchApprTypeCoop" id="searchApprTypeCoop"  type="hidden" value="<%=resultSearchApprTypeCoop%>">
			        <input name="searchApprTypeDraft" id="searchApprTypeDraft"  type="hidden" value="<%=resultSearchApprTypeDraft%>">
			        <input name="searchApprTypePreDis" id="searchApprTypePreDis"  type="hidden" value="<%=resultSearchApprTypePreDis%>">
			        <input name="searchApprTypeResponse" id="searchApprTypeResponse"  type="hidden" value="<%=resultSearchApprTypeResponse%>">
			        <input name="searchApprTypeAudit" id="searchApprTypeAudit"  type="hidden" value="<%=resultSearchApprTypeAudit%>">
					</form>
					</td>					

				</tr>
			</table>
		</td>					

	</tr>
