<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.CategoryVO" %>
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
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListRegistSearch.jsp 
 *  Description : 리스트 공통 검색  
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 06 
 *  @version 1.0 
 *  @see
 */ 
%>

<%
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

String deptAllYn = CommonUtil.nullTrim(request.getParameter("deptAll"));

//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String cPageStr = request.getParameter("cPage");
int CPAGE = 1;
if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);

String resultSearchType 		= CommonUtil.nullTrim(resultSearchVO.getSearchType());
String resultSearchWord 		= CommonUtil.nullTrim(resultSearchVO.getSearchWord());
String resultStartDate			= CommonUtil.nullTrim(resultSearchVO.getStartDate());
String resultEndDate			= CommonUtil.nullTrim(resultSearchVO.getEndDate());
String resultCurRange 			= CommonUtil.nullTrim(resultSearchVO.getRegistCurRange());
String resultSearchTypeYn		= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeYn());
String resultSearchTypeValue	= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeValue());
String resultLobCode			=  CommonUtil.nullTrim(resultSearchVO.getLobCode());

String easyApprSearch			= CommonUtil.nullTrim(resultSearchVO.getEasyApprSearch());
String easyEnfSearch			= CommonUtil.nullTrim(resultSearchVO.getEasyEnfSearch());

String resultSearchLobCode		= CommonUtil.nullTrim(resultSearchVO.getSearchLobCode());
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
String resultSearchApprTypeEtc		= CommonUtil.nullTrim(resultSearchVO.getSearchApprTypeEtc());
String resultSearchAppDocYn			= CommonUtil.nullTrim(resultSearchVO.getAppDocYn());
String resultSearchEnfDocYn			= CommonUtil.nullTrim(resultSearchVO.getEnfDocYn());
String resultDocType				= CommonUtil.nullTrim(resultSearchVO.getDocType());
String searchAuthDeptId				= CommonUtil.nullTrim(resultSearchVO.getSearchAuthDeptId());
String searchAuthDeptName			= CommonUtil.nullTrim(resultSearchVO.getSearchAuthDeptName());

// 검색조건 - 감사
String resultSearchAuditOpt009		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt009());
String resultSearchAuditOpt021		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt021());
String resultSearchAuditOpt022		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt022());
String resultSearchAuditOpt023		= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt023());

String det001 = appCode.getProperty("DET001", "DET001", "DET");
String det002 = appCode.getProperty("DET002", "DET002", "DET");
String det003 = appCode.getProperty("DET003", "DET003", "DET");
String det004 = appCode.getProperty("DET004", "DET004", "DET");
String det005 = appCode.getProperty("DET005", "DET005", "DET");
String det006 = appCode.getProperty("DET006", "DET006", "DET");
String det007 = appCode.getProperty("DET007", "DET007", "DET");
//상세조건 끝

String optionMsg				= "";
String searchCalendarYn			= "Y";

if("Y".equals(resultSearchTypeYn)){
    if("Y".equals(resultSearchTypeValue)) {
		optionMsg = messageSource.getMessage("list.list.msg.year" , null, langType);
    }else if("P".equals(resultSearchTypeValue)) {
		optionMsg = messageSource.getMessage("list.list.msg.period" , null, langType);
    }
}

if("LOL098".equals(resultLobCode)) {
    String rowRankYn = (String) request.getAttribute("rowRankYn");
    
    if("1".equals(rowRankYn)){
		//searchCalendarYn = "N";
    }
}
pageContext.setAttribute("resultDocType",resultDocType);
pageContext.setAttribute("resultCurRange",resultCurRange);
pageContext.setAttribute("optionMsg",optionMsg);

String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);

String startDateId = DateUtil.getFormattedDate(resultStartDate+" 00:00:00", "yyyyMMdd");
String endDateId = DateUtil.getFormattedDate(resultEndDate+" 00:00:00", "yyyyMMdd");

String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
String buttonReset = messageSource.getMessage("list.list.button.reset" , null, langType);
String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

int searchTypeSize = 0;
int searchTypeRerultSize = 0;
String searchTypeOpt = "";
String searchTypeOptName = "";
Map<String,String[]> searchTypeMap	= new HashMap<String,String[]>();

String[] lol098;

String[] lol001 = {"searchTitle","list.list.button.searchTypeTitle","searchRecvDeptName","list.list.title.headerRecieveSend","searchSenderDocNumber","list.list.button.searchTypeSenderDocNumber"};
String[] lol002 = {"searchTitle","list.list.button.searchTypeTitle","searchRecvName","list.list.title.headerReceiveDistributeName"};
String[] lol003 = {"searchTitle","list.list.button.searchTypeTitle","searchRecvDeptName","list.list.title.headerRecieveSend"};
String[] lol004 = {"searchTitle","list.list.button.searchTypeTitle","searchRequestDeptName","list.list.button.searchTypeRequestDeptName","searchRequestName","list.list.button.searchTypeRequestName","searchDocNumber","list.list.button.searchTypeDocNum"};
String[] lol005 = {"searchTitle","list.list.button.searchTypeTitle","searchRequestDeptName","list.list.button.searchTypeRequestDeptName","searchRequestName","list.list.button.searchTypeRequestName","searchDocNumber","list.list.button.searchTypeDocNum"};
String[] lol006 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lol007 = {"searchTitle","list.list.button.searchTypeTitle","searchApproverType","list.list.button.searchTypeApproverType","searchChargeDeptName","list.list.button.searchTypeChargeDeptName","searchAuditKind","list.list.button.searchTypeAuditKind"};
String[] lol008 = {"searchTitle","list.list.button.searchTypeTitle","searchSender","list.list.title.headerSender","searchReceiver","list.list.title.headerReceiver","searchRequester","list.list.title.headerRequester","searchDocNumber","list.list.button.searchTypeDocNum"};
String[] lol097 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter"};
if("Y".equals(deptAllYn)) { 
    lol098 = new String[]{"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDocNumber","list.list.button.searchTypeDocNum","searchDeptName","list.list.button.searchTypeDeptName"};
}else{
    lol098 = new String[]{"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDocNumber","list.list.button.searchTypeDocNum"};
}
String[] lol099 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter"};


searchTypeMap.put("LOL001",lol001);
searchTypeMap.put("LOL002",lol002);
searchTypeMap.put("LOL003",lol003);
searchTypeMap.put("LOL004",lol004);
searchTypeMap.put("LOL005",lol005);
searchTypeMap.put("LOL006",lol006);
searchTypeMap.put("LOL007",lol007);
searchTypeMap.put("LOL008",lol008);
searchTypeMap.put("LOL097",lol097);
searchTypeMap.put("LOL098",lol098);
searchTypeMap.put("LOL099",lol099);

searchTypeSize = searchTypeMap.get(resultLobCode).length;
searchTypeRerultSize = searchTypeSize / 2;

//상세검색 중 결재자롤 사용여부 확인
Map<String,String> appRoleMap = (HashMap<String,String>) request.getAttribute("appRoleMap");

String opt003GYn = CommonUtil.nullTrim(appRoleMap.get("opt003G"));
String opt009GYn = CommonUtil.nullTrim(appRoleMap.get("opt009G"));
String opt019GYn = CommonUtil.nullTrim(appRoleMap.get("opt019G"));
// 상세검색 중 결재자롤 사용여부 확인 끝

// 검색 조건 감사 사용 여부 조회
String opt009AuditYn = "";
String opt010AuditYn = ""; 
String opt021AuditYn = "";
String opt022AuditYn = "";
String opt023AuditYn = "";

if(request.getAttribute("appAuditRoleMap") != null){
    Map<String,String> appAuditRoleMap = (HashMap<String,String>) request.getAttribute("appAuditRoleMap");   
	
    opt009AuditYn = CommonUtil.nullTrim(appAuditRoleMap.get("OPT009"));
    opt010AuditYn = CommonUtil.nullTrim(appAuditRoleMap.get("OPT010"));
    opt021AuditYn = CommonUtil.nullTrim(appAuditRoleMap.get("OPT021"));
    opt022AuditYn = CommonUtil.nullTrim(appAuditRoleMap.get("OPT022"));
    opt023AuditYn = CommonUtil.nullTrim(appAuditRoleMap.get("OPT023"));
}
// 검색 조건 감사 사용 여부 조회 끝

String searchWordStyle = "block";
String searchAuditKindStyle = "none";
if("searchAuditKind".equals(resultSearchType)){
    searchWordStyle = "none";
    searchAuditKindStyle = "block";
}

IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

String opt370 = appCode.getProperty("OPT370","OPT370","OPT"); // 관련문서 필터링 사용여부
opt370 = envOptionAPIService.selectOptionValue(compId, opt370);
%>
		
		<tr>
			<acube:space between="button_search" />
		</tr>
		<tr>
			<td>
			<form name="listSearch" id="listSearch" method="post" style="margin:0px">
			<table class="tablesearchnp" cellspacing="0" cellpadding="0" width="100%">
			  <tr>
			    <td width="10"></td>
			    <td style="padding:5px 7px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <%
			            
			            int frontWidth = 0;
			            if(!"LOL097".equals(resultLobCode) && !"LOL098".equals(resultLobCode) && ("LOL099".equals(resultLobCode) && "N".equals(opt370)) ) { 
			            	frontWidth = 455;
			            }else{ 
			            	frontWidth = 405;
			            } 
			            %>
			            
			            <td width="<%=frontWidth%>" height="24"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <%if(!"LOL097".equals(resultLobCode) && !"LOL098".equals(resultLobCode) && !"LOL099".equals(resultLobCode)  ) { %>
			                <td width="45"><select name="selRange" id="selRange" onChange="javascript:chRange(this.value);">
			                    <option value=""><spring:message code="list.list.button.searchTypeDefault"/></option>
								<c:forEach var="registList" items="${registList}" >
								<option value="<c:out value="${registList.registRange}" />" <c:if test="${registList.registRange == resultCurRange}"> selected</c:if> >${registList.registRange}${optionMsg}</option>
								</c:forEach>
			                    </select></td>
			                <td width="5"></td>
			                <% } %>
			                <% if("LOL099".equals(resultLobCode) && "Y".equals(opt370) ){			                    
			                    
			                    String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT"); // 공람문서함
			                    String opt116 = appCode.getProperty("OPT116", "OPT116", "OPT"); // 주관부서문서함
			                    String opt117 = appCode.getProperty("OPT117", "OPT117", "OPT"); // 본부문서함
			                    String opt118 = appCode.getProperty("OPT118", "OPT118", "OPT"); // 회사문서함
			                    
			                    opt112 = envOptionAPIService.selectOptionText(compId, opt112);
			                    opt116 = envOptionAPIService.selectOptionText(compId, opt116);
			                    opt117 = envOptionAPIService.selectOptionText(compId, opt117);
			                    opt118 = envOptionAPIService.selectOptionText(compId, opt118);
			                    
			                %>
			                <td width="45">
			                	<select name="selLobCode" id="selLobCode" onChange="javascript:chLobCode(this.value);">
			                    	<option value="<%=appCode.getProperty("LOL099", "LOL099","LOL") %>" <%if(appCode.getProperty("LOL099", "LOL099","LOL").equals(resultSearchLobCode)){ %> selected <% } %>><spring:message code="list.list.title.listRelationDocRegist"/></option>
			                    	<%if(!"".equals(opt117)){ %>
			                    		<option value="<%=appCode.getProperty("LOB017", "LOB017", "LOB") %>" <%if(appCode.getProperty("LOB017", "LOB017", "LOB").equals(resultSearchLobCode)){ %> selected <% } %>><%=opt117 %></option>
			                    	<% } %>
			                    	<%if(!"".equals(opt118)){ %>
			                    		<option value="<%=appCode.getProperty("LOB018", "LOB018", "LOB") %>" <%if(appCode.getProperty("LOB018", "LOB018", "LOB").equals(resultSearchLobCode)){ %> selected <% } %>><%=opt118 %></option>
			                    	<%} %>
			                    	<%if(!"".equals(opt112)){ %>
			                    		<option value="<%=appCode.getProperty("LOB012", "LOB012", "LOB") %>" <%if(appCode.getProperty("LOB012", "LOB012", "LOB").equals(resultSearchLobCode)){ %> selected <% } %>><%=opt112 %></option>
			                    	<% } %>
			                    	<%if(!"".equals(opt116)){ %>
			                    		<option value="<%=appCode.getProperty("LOB016", "LOB016", "LOB") %>" <%if(appCode.getProperty("LOB016", "LOB016", "LOB").equals(resultSearchLobCode)){ %> selected <% } %>><%=opt116 %></option>
			                    	<% } %>
			                    </select>
			                </td>
			                <td width="5"></td>
			                <% } %>
			                <td width="50"><select name="searchType" class="select" <% if("LOL007".equals(resultLobCode)){ %> onChange="chThisValue(this.value);"<% } %>>
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
			                </select></td>
			                <td width="5"></td>
			                <td width="300"  class="search">
			                <div id="searchWordTd" style="display:<%=searchWordStyle%>">
			                <input name="searchWord" type="text" class="input" style="width:100%" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearch();}" >
			                </div>
			                <div id="searchAuditKindTd" style="display:<%=searchAuditKindStyle%>">
			                <% if("Y".equals(opt009AuditYn) || "Y".equals(opt010AuditYn)){ %><input type="checkbox" id="searchAuditOpt009" name="searchAuditOpt009" value="Y" <%if("Y".equals(resultSearchAuditOpt009)) { %> checked <% } %>><spring:message code="list.list.button.searchTypeAuditKind1"/></input><% } %>
			                <% if("Y".equals(opt021AuditYn)){ %><input type="checkbox" id="searchAuditOpt021" name="searchAuditOpt021" value="Y" <%if("Y".equals(resultSearchAuditOpt021)) { %> checked <% } %>><spring:message code="list.list.button.searchTypeAuditKind2"/></input><% } %>
			                <% if("Y".equals(opt022AuditYn)){ %><input type="checkbox" id="searchAuditOpt022" name="searchAuditOpt022" value="Y" <%if("Y".equals(resultSearchAuditOpt022)) { %> checked <% } %>><spring:message code="list.list.button.searchTypeAuditKind3"/></input><% } %>
			                <% if("Y".equals(opt023AuditYn)){ %><input type="checkbox" id="searchAuditOpt023" name="searchAuditOpt023" value="Y" <%if("Y".equals(resultSearchAuditOpt023)) { %> checked <% } %>><spring:message code="list.list.button.searchTypeAuditKind4"/></input><% } %>
			                </div>
			                </td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td width="280"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="list.list.button.period"/></td>
			                <td width="220"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td><input type="text" class="input_read" name="startDate" id="startDate" readonly  style="width:100%" value="<%= resultStartDate %>"></td>
			                    <td width="5"></td>
			                    <%if("Y".equals(searchCalendarYn) ) { %>
			                    <td width="18"><img id="calendarBTN1" name="calendarBTN1" 
															src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
															align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
															onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 
															'<%= dateFormat %>');"></td>
			                    <% } %>
			                    <input type="hidden" name="startDateId" id="startDateId" value="<%= startDateId %>">
			                    <td width="5">&nbsp;</td>
			                    <td width="18" align="center">~</td>
			                    <td><input type="text" class="input_read" name="endDate" id="endDate" readonly style="width:100%" value="<%= resultEndDate %>"></td>
			                    <td width="5">&nbsp;</td>
			                    <%if("Y".equals(searchCalendarYn) ) { %>
			                    <td width="18"><img id="calendarBTN2" name="calendarBTN2" 
															src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
															align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
															onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN2', 
															'<%= dateFormat %>');">
														</td>
								<% }  %>
								<input type="hidden" name="endDateId" id="endDateId" value="<%= endDateId %>">
			                  </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td width="10">&nbsp;</td>
			            <td width="120">
							<a href="#" onclick="javascript:goSearch();"><img src="<%=webUri%>/app/ref/image/bt_search.gif" alt="검색" /></a>
							<a href="#" onclick="reset();changeSearchType('searchTitle');"><img src="<%=webUri%>/app/ref/image/bt_search_reset.gif" alt="초기화" /></a>
			            </td>
			          </tr>
			          <tr name="detailSearchDiv" id="detailSearchDiv" style="display:none;">
			            <td width="<%=frontWidth%>" height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDocNum"/></td> <!-- 문서번호 -->
			                <td width="<%=frontWidth-70%>"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><input name="startDocNum" id="startDocNum" type="text" class="input" style="width:100%" value="<%=resultStartDocNum %>" style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);" onkeydown="if(event.keyCode==13){goSearch();}"></td>
			                      <td width="5"></td>
			                      <td width="18" align="center">~</td>
			                      <td width="5"></td>
			                      <td><input name="endDocNum" id="endDocNum" type="text" class="input" style="width:100%" value="<%=resultEndDocNum%>" style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);" onkeydown="if(event.keyCode==13){goSearch();}"></td>
			                    </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td width="%">&nbsp;</td>
			            <td width="280"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="list.list.button.searchTypeBindingInfo"/></td> <!-- 편철 -->
			                <td width="250"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td>
			                    <input type="hidden" id="bindingId" name="bindingId" value="<%=resultBindingId%>" />
			                    <input id="bindingName" name="bindingName" value="<%=resultBindingName%>" readonly type="text" class="input_read" style="width:100%">
			                    </td>
			                    <td width="5"></td>
			                    <td width="19"><img src="<%=webUri%>/app/ref/image/bu5_search.gif" width="19" height="18" onclick="selectBind();return(false);" alt="<%=bindBtn%>" style="cursor:pointer;"></td>
			                  </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td>&nbsp;</td>
			          </tr>
			          <%if("LOL001".equals(resultLobCode) || "LOL003".equals(resultLobCode)  ) { %>
				          <tr name="detailSearchDiv2" id="detailSearchDiv2" style="display:none;">
				            <td height="24">            
				            <table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDocDefault"/></td> <!-- 문서분류 -->
				                <td><select name="searchDocType" id="searchDocType">
				                    <option value=""><spring:message code="list.list.button.searchTypeDocDefault"/></option>
									<c:forEach var="DocTypeVo" items="${DocTypeVO}" >
									<option value="<c:out value="${DocTypeVo.categoryId}" />" <c:if test="${DocTypeVo.categoryId == resultDocType}"> selected</c:if> >${DocTypeVo.categoryName}</option>
									</c:forEach>
				                </select></td>
				              </tr>
				            </table>           
				            </td>
				            <td>&nbsp;</td>
				            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="60" class="search_title"><spring:message code="list.list.button.searchTypeAppEnfType"/></td> <!-- 문서유형 -->
				                <td class="search_text">
				                <input type="checkbox" name="chkAppDocYn" id="chkAppDocYn" value="Y" onclick="javascript:setSearchAppDocYn(this.value)" <%if("Y".equals(resultSearchAppDocYn)) { %> checked <% } %>/><spring:message code="list.list.msg.docTypeAppDoc"/>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="chkEnfDocYn" id="chkEnfDocYn"  value="Y" onclick="javascript:setSearchEnfDocYn(this.value)" <%if("Y".equals(resultSearchEnfDocYn)) { %> checked <% } %>/><spring:message code="list.list.msg.docTypeEnfDoc"/>
				                <input type="hidden" name="searchAppDocYn" id="searchAppDocYn" value="<%=resultSearchAppDocYn%>"> 
				                <input type="hidden" name="searchEnfDocYn" id="searchEnfDocYn" value="<%=resultSearchEnfDocYn%>"> 
				                </td>
				              </tr>
				            </table></td>
				            <td>&nbsp;</td>
				            <td>&nbsp;</td>
				          </tr>
			          <% }else{  %>
			      		<tr name="detailSearchDiv2" id="detailSearchDiv2" style="display:none;">
			            <td height="24">            
			            <table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeAppEnfType"/></td> <!-- 문서유형 -->
			                <td class="search_text">
			                <input type="checkbox" name="chkAppDocYn" id="chkAppDocYn" value="Y" onclick="javascript:setSearchAppDocYn(this.value)" <%if("Y".equals(resultSearchAppDocYn)) { %> checked <% } %>/><spring:message code="list.list.msg.docTypeAppDoc"/>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="chkEnfDocYn" id="chkEnfDocYn"  value="Y" onclick="javascript:setSearchEnfDocYn(this.value)" <%if("Y".equals(resultSearchEnfDocYn)) { %> checked <% } %>/><spring:message code="list.list.msg.docTypeEnfDoc"/>
			                <input type="hidden" name="searchAppDocYn" id="searchAppDocYn" value="<%=resultSearchAppDocYn%>"> 
			                <input type="hidden" name="searchEnfDocYn" id="searchEnfDocYn" value="<%=resultSearchEnfDocYn%>"> 
			                </td>
			              </tr>
			            </table>           
			            </td>
			            <td>&nbsp;</td>
			            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="60" class="search_title"></td> 
			                <td class="search_text"><input type="hidden" name="selDocType" id="selDocType" value=""></td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td>&nbsp;</td>
			          </tr>
			     	  <% } %>
			          <tr name="detailSearchDiv3" id="detailSearchDiv3" style="display:none;">
			            <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDocType"/></td> <!-- 문서구분 -->
			                <td class="search_text"><input type="checkbox" name="searchElecYn" id="searchElecYn" value="Y" <%if("Y".equals(resultSearchElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeElecDocType"/>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="searchNonElecYn" id="searchNonElecYn"  value="Y" <%if("Y".equals(resultSearchNonElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeNonElecDocType"/></td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="60" class="search_title"><spring:message code="list.list.button.searchTypeDet"/></td> <!-- 시행구분 -->
			                <td class="search_text">
			                <select class="select" name="searchDetType" id="searchDetType">
							<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
							<option value="<%=det001%>" <% if(det001.equals(resultSearchDetType)) { %> selected <% } %>><spring:message code="list.list.button.searchTypeDet001"/></option>
							<option value="<%=det002%>" <% if(det002.equals(resultSearchDetType)) { %> selected <% } %>><spring:message code="list.list.button.searchTypeDet002"/></option>
							<option value="<%=det003%>" <% if(det003.equals(resultSearchDetType)) { %> selected <% } %>><spring:message code="list.list.button.searchTypeDet003"/></option>
							<option value="<%=det004%>" <% if(det004.equals(resultSearchDetType)) { %> selected <% } %>><spring:message code="list.list.button.searchTypeDet004"/></option>							
							</select>
							</td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td>&nbsp;</td>
			          </tr>
			        </table></td>
			      </tr>      
			      <tr name="detailSearchDiv4" id="detailSearchDiv4" style="display:none;">
			        <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeApproval"/></td><!-- 결재자 -->
			            <td class="search_text"><input type="text" class="input" name="searchApprovalName" id="searchApprovalName" size="13" value="<%=resultSearchApprovalName%>" onkeydown="if(event.keyCode==13){goSearch();}">
										&nbsp;( <input type="checkbox" name="searchApprTypeDraft" id="searchApprTypeDraft" value="Y" <%if("Y".equals(resultSearchApprTypeDraft)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprDraft"/> <input type="checkbox" name="searchApprTypeExam" id="searchApprTypeExam" value="Y" <%if("Y".equals(resultSearchApprTypeExam)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprExam"/> <% if("Y".equals(opt003GYn)){ %><input type="checkbox" name="searchApprTypeCoop" id="searchApprTypeCoop" value="Y" <%if("Y".equals(resultSearchApprTypeCoop)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprCoop"/> <% } %> <% if("Y".equals(opt009GYn)){ %><input type="checkbox" name="searchApprTypeAudit" id="searchApprTypeAudit" value="Y" <%if("Y".equals(resultSearchApprTypeAudit)) { %> checked <% } %>/><spring:message code="list.list.button.searchApprTypeAudit"/> <% } %><input type="checkbox" name="searchApprTypeApproval" id="searchApprTypeApproval" value="Y" <%if("Y".equals(resultSearchApprTypeApproval)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprApproval"/>  <% if("Y".equals(opt019GYn)){ %><input type="checkbox" name="searchApprTypePreDis" id="searchApprTypePreDis" value="Y" <%if("Y".equals(resultSearchApprTypePreDis)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprPreDis"/><% } %> <input type="checkbox" name="searchApprTypeResponse" id="searchApprTypeResponse" value="Y" <%if("Y".equals(resultSearchApprTypeResponse)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprResponse"/>  
										)
										</td>
			          </tr>
			        </table></td>
			      </tr>      
			    </table>    
			    </td>
			    <td width="10"></td>
			  </tr>
  			</table>

			<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">
			<input type="hidden" name="searchDocType" id="searchDocType" value="<%=resultDocType%>">
			<input type="hidden" name="searchCurRange" id="searchCurRange" value="<%=resultCurRange%>">
			<input type="hidden" name="searchTypeYn" id="searchTypeYn" value="<%=resultSearchTypeYn%>">
			<input type="hidden" name="searchTypeValue" id="searchTypeValue" value="<%=resultSearchTypeValue%>">
			<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N">
			<input type="hidden" name="excelExportYn" id="excelExportYn" value="N">	
			<input type="hidden" name="deptAll" id="deptAll" value="<%=deptAllYn%>">
			<input type="hidden" name="easyApprSearch" id="easyApprSearch" value="<%=easyApprSearch %>"/>
			<input type="hidden" name="easyEnfSearch" id="easyEnfSearch" value="<%=easyEnfSearch %>"/>
			<input type="hidden" name="searchLobCode" id="searchLobCode" value="<%=resultSearchLobCode%>" />
			<input type="hidden" name="searchAuthDeptId" id="searchAuthDeptId" value="<%=searchAuthDeptId%>">
			<input type="hidden" name="searchAuthDeptName" id="searchAuthDeptName" value="<%=searchAuthDeptName%>">
			</form>
			</td>
		</tr>