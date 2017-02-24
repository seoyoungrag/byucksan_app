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
<%@ page import="com.sds.acube.app.list.util.ListUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListDateSearch.jsp 
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
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

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
String resultDocType			= CommonUtil.nullTrim(resultSearchVO.getDocType());
String resultReadingRange		= CommonUtil.nullTrim(resultSearchVO.getReadingRange());
String resultDeptId				= ListUtil.TransReplace(CommonUtil.nullTrim(resultSearchVO.getDeptId()),"'","");
String resultDeptName			= CommonUtil.nullTrim(resultSearchVO.getDeptName());
String resultLobCode			= CommonUtil.nullTrim(resultSearchVO.getLobCode());
String resultDisplayYn			= CommonUtil.nullTrim(resultSearchVO.getDisplayYn());
String resultSearchAuditReadY	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditReadY());
String resultSearchAuditReadN	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditReadN());

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
String resultSearchWordDeptName		= CommonUtil.nullTrim(resultSearchVO.getSearchWordDeptName());

String det001 = appCode.getProperty("DET001", "DET001", "DET");
String det002 = appCode.getProperty("DET002", "DET002", "DET");
String det003 = appCode.getProperty("DET003", "DET003", "DET");
String det004 = appCode.getProperty("DET004", "DET004", "DET");
String det005 = appCode.getProperty("DET005", "DET005", "DET");
String det006 = appCode.getProperty("DET006", "DET006", "DET");
String det007 = appCode.getProperty("DET007", "DET007", "DET");
//상세조건 끝

pageContext.setAttribute("resultDocType",resultDocType);

String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);

String startDateId = DateUtil.getFormattedDate(beforeStartDate+" 00:00:00", "yyyyMMdd");
String endDateId = DateUtil.getFormattedDate(beforeEndDate+" 00:00:00", "yyyyMMdd");

String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
String buttonReset = messageSource.getMessage("list.list.button.reset" , null, langType);
String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

int searchTypeSize = 0;
int searchTypeRerultSize = 0;
String searchTypeOpt = "";
String searchTypeOptName = "";
Map<String,String[]> searchTypeMap	= new HashMap<String,String[]>();
String[] lob004 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.title.headerCustomer"};
String[] lob009 = {"searchTitle","list.list.button.searchTypeTitle","searchApproval","list.list.button.searchTypeApproval"};
String[] lob010 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter"};
String[] lob011 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeReceiver","searchDeptName","list.list.button.searchTypeSenderDeptName","searchSenderDocNumber","list.list.button.searchTypeSenderDocNumber"};
String[] lob012 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDeptName","list.list.button.searchTypeDeptName","searchSenderDocNumber","list.list.button.searchTypeSenderDocNumber"};
String[] lob013 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob014 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName","searchTypeAuditReadYn","list.list.title.headerAuditReadYn"};
String[] lob015 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName","searchDeptName","list.list.button.searchTypeDeptName"};
String[] lob016 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob017 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob018 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob020 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob021 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lob022 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDeptName","list.list.button.searchTypeDeptName"};
String[] lob023 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchDeptName","list.list.button.searchTypeDeptName"};
String[] lob024 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName","searchTypeAuditReadYn","list.list.title.headerAuditReadYn"};  // jth8172 2012 신결재 TF 
String[] lob025 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};  // jth8172 2012 신결재 TF
String[] lob031 = {"searchTitle","list.list.button.searchTypeTitle","searchPublisher","list.list.button.searchTypePublisher","searchPublisherDept","list.list.button.searchTypePublisherDept"};
String[] lob094 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter"};
String[] lob098 = {"searchCustomName","list.list.button.searchTypeCustomerName","searchCustomCode","list.list.button.searchTypeCustomerCode","searchTitle","list.list.button.searchTypeTitle"};
String[] lob130 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter"};

searchTypeMap.put("LOB004",lob004);
searchTypeMap.put("LOB009",lob009);
searchTypeMap.put("LOB010",lob010);
searchTypeMap.put("LOB011",lob011);
searchTypeMap.put("LOB012",lob012);
searchTypeMap.put("LOB013",lob013);
searchTypeMap.put("LOB014",lob014);
searchTypeMap.put("LOB015",lob015);
searchTypeMap.put("LOB016",lob016);
searchTypeMap.put("LOB017",lob017);
searchTypeMap.put("LOB018",lob018);
searchTypeMap.put("LOB020",lob020);
searchTypeMap.put("LOB021",lob021);
searchTypeMap.put("LOB022",lob022);
searchTypeMap.put("LOB023",lob023);
searchTypeMap.put("LOB024",lob024);  // jth8172 2012 신결재 TF
searchTypeMap.put("LOB025",lob025); // jth8172 2012 신결재 TF
searchTypeMap.put("LOB031",lob031);
searchTypeMap.put("LOB094",lob094);
searchTypeMap.put("LOB098",lob098);
searchTypeMap.put("LOB130",lob130);

searchTypeSize = searchTypeMap.get(resultSearchVO.getLobCode()).length;
searchTypeRerultSize = searchTypeSize / 2;
 

 // 상세검색 중 결재자롤 사용여부 확인
Map<String,String> appRoleMap = (HashMap<String,String>) request.getAttribute("appRoleMap");

/* String opt003GYn = CommonUtil.nullTrim(appRoleMap.get("opt003G"));
String opt009GYn = CommonUtil.nullTrim(appRoleMap.get("opt009G"));
String opt019GYn = CommonUtil.nullTrim(appRoleMap.get("opt019G")); */
// 상세검색 중 결재자롤 사용여부 확인 끝
 
String searchWordStyle = "block";
String searchTypeAuditReadYnStyle = "none";
if("LOB014".equals(resultLobCode)) {
	if("searchTypeAuditReadYn".equals(resultSearchType) && ("Y".equals(resultSearchAuditReadY) || "Y".equals(resultSearchAuditReadN)) ){
	    searchWordStyle = "none";
	    searchTypeAuditReadYnStyle = "block";
	}else{
	    resultSearchType = "";
	}
}

IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String opt361 = appCode.getProperty("OPT361","OPT361","OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
opt361 = envOptionAPIService.selectOptionValue(compId, opt361);
String opt342 = appCode.getProperty("OPT342","OPT342","OPT"); // 검사부열람함 열람범위 - 1:모든문서, 2:담당부서문서
opt342 = envOptionAPIService.selectOptionValue(compId, opt342);
String opt375 = appCode.getProperty("OPT375","OPT375","OPT"); // 감사부서열람함 접수문서 사용여부(Y:사용, N:미사용)
opt375 = envOptionAPIService.selectOptionValue(compId, opt375);
String opt377 = appCode.getProperty("OPT377","OPT377","OPT"); // 감사부서열람함 비전자문서 사용여부(Y:사용, N:미사용)
opt377 = envOptionAPIService.selectOptionValue(compId, opt377);

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
			            <td width="455" height="24"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <%
			                if("LOB012".equals(resultLobCode)){
			                %>
			                <td width="50">
			                <select name="selDisplayYn" id="selDisplayYn" onchange="javascript:chDisplay(this.value);">
			                <option value="ALL" <%if("ALL".equals(resultDisplayYn)){ %> selected <%} %>><spring:message code="list.list.button.searchTypeAll"/></option>
			                <option value="Y" <% if("Y".equals(resultDisplayYn)){ %> selected <%} %>><spring:message code="list.list.button.searchTypeDisplayY"/></option>
			                <option value="N" <% if("N".equals(resultDisplayYn)){ %> selected <%} %>><spring:message code="list.list.button.searchTypeDisplayN"/></option>
			                </select>
			                </td>
			                <td width="5"></td>
			                <%
			                }
			                %>
			                <td width="50"><select id="searchType" name="searchType" class="select" <%if("LOB014".equals(resultLobCode)){ %>onchange="javascript:chSearchAudit(this.value);" <% } %>>
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
			                <td width="400" class="search">
			                <div id="searchWordTd" style="display:<%=searchWordStyle%>">
			                <input name="searchWord" id="searchWord" type="text" class="input" style="width:100%" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearchDoc();}" >
			                </div>
			                <div id="searchTypeAuditReadYnTd" style="display:<%=searchTypeAuditReadYnStyle%>">
			                <input type="checkbox" name="searchAuditReadY" id="searchAuditReadY" value="Y" <%if("Y".equals(resultSearchAuditReadY)){ %> checked<% }%>></input><spring:message code="list.list.msg.headerAuditReadY"/>
			                <input type="checkbox" name="searchAuditReadN" id="searchAuditReadN" value="Y" <%if("Y".equals(resultSearchAuditReadN)){ %> checked<% }%>></input><spring:message code="list.list.msg.headerAuditReadN"/>
			                </div>
			                </td>
			              </tr>
			            </table></td>
			            <td width="%">&nbsp;</td>
			            <td width="280"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="60" class="search_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="list.list.button.period"/></td>
			                <td width="220"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td><input type="text" class="input_read" name="startDate" id="startDate" readonly  style="width:100%" value="<%= resultStartDate %>"></td>
			                    <td width="5"></td>
			                    <td width="18"><img id="calendarBTN1" name="calendarBTN1" 
															src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
															align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
															onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 
															'<%= dateFormat %>');"></td>
			                    <input type="hidden" name="startDateId" id="startDateId" value="<%= startDateId %>">
			                    <td width="5">&nbsp;</td>
			                    <td width="18" align="center">~</td>
			                    <td><input type="text" class="input_read" name="endDate" id="endDate" readonly style="width:100%" value="<%= resultEndDate %>"></td>
			                    <td width="5">&nbsp;</td>
			                    <td width="18"><img id="calendarBTN2" name="calendarBTN2" 
															src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
															align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
															onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN2', 
															'<%= dateFormat %>');">
														</td>
								<input type="hidden" name="endDateId" id="endDateId" value="<%= endDateId %>">
			                  </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td width="10">&nbsp;</td>
			            <td width="58" align="right">
							<a href="#" onclick="javascript:goSearchDoc();"><img src="<%=webUri%>/app/ref/image/bt_search.gif" alt="검색" /></a>
			            	<%-- <acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /> --%>
			            	<%-- <acube:button onclick="reset();changeSearchType('searchTitle');" value='<%=buttonReset%>' type="reset" align="left" disable="" /> --%>
			            </td>
			          </tr>
			          <tr name="detailSearchDiv" id="detailSearchDiv" style="display:none;">
			            <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDocNum"/></td> <!-- 문서번호 -->
			                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                    <tr>
			                      <td><input name="startDocNum" id="startDocNum" type="text" class="input" style="width:100%" value="<%=resultStartDocNum %>" style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);" onkeydown="if(event.keyCode==13){goSearchDoc();}"></td>
			                      <td width="5"></td>
			                      <td width="18" align="center">~</td>
			                      <td width="5"></td>
			                      <td><input name="endDocNum" id="endDocNum" type="text" class="input" style="width:100%" value="<%=resultEndDocNum%>" style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);" onkeydown="if(event.keyCode==13){goSearchDoc();}"></td>
			                    </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="60" class="search_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="list.list.button.searchTypeBindingInfo"/></td> <!-- 편철 -->
			                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td>
			                    <input type="hidden" id="bindingId" name="bindingId" value="<%=resultBindingId%>" />
			                    <input id="bindingName" name="bindingName" value="<%=resultBindingName%>" readonly type="text" class="input_read" style="width:100%"></td>
			                    <td width="5"></td>
			                    <td width="19"><img src="<%=webUri%>/app/ref/image/bu5_search.gif" width="19" height="18" onclick="selectBind();return(false);" alt="<%=bindBtn%>" style="cursor:pointer;"></td>
			                  </tr>
			                </table></td>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td>&nbsp;</td>
			          </tr>			          
			          <% if("LOB010".equals(resultLobCode) || "LOB012".equals(resultLobCode) || "LOB015".equals(resultLobCode) ) {  %>
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
			            <%if("LOB010".equals(resultLobCode)){ %>
				            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="60" class="search_title"><spring:message code="list.list.button.searchTypeDocDefault"/></td> <!-- 문서분류 -->
				                <td class="search_text">
				                <select name="searchDocType" id="searchDocType">
				                    <option value=""><spring:message code="list.list.button.searchTypeDocDefault"/></option>
									<c:forEach var="DocTypeVo" items="${DocTypeVO}" >
									<option value="<c:out value="${DocTypeVo.categoryId}" />" <c:if test="${DocTypeVo.categoryId == resultDocType}"> selected</c:if> >${DocTypeVo.categoryName}</option>
									</c:forEach>
				                </select>
				                <input type="hidden" name="selDocType" id="selDocType" value="">
				                </td>
				              </tr>
				            </table></td>
				            <td>&nbsp;</td>
			            <%}else{ %>			            
			            	<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>				              	
				                <td width="60" class="search_title"></td> 
				                <td class="search_text"><input type="hidden" name="selDocType" id="selDocType" value=""></td>
				              </tr>
				            </table></td>
				            <td>&nbsp;</td>
			            <% } %>
			            <td>&nbsp;</td>
			          </tr>	
			          <% } %>	
			          <%
			          // 열람범위 확인 후 생산/접수 선택 기능 view
			          if( "LOB017".equals(resultLobCode) || "LOB018".equals(resultLobCode) ){ 
			          	if("1".equals(opt361)){
			          %>
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
				            </table>
				        </td>
				        <td>&nbsp;</td>
				        <td>&nbsp;</td>
			          </tr>	
			          <%    
			          	}
			          } 
			          %>
			          <% 
			          // 검사부 열람함의 경우 opt375, opt377 확인
			          if( "LOB014".equals(resultLobCode) && ("Y".equals(opt375) || "Y".equals(opt377)  ) ){ %>
			          <tr name="detailSearchDiv2" id="detailSearchDiv2" style="display:none;">
			            <td height="24">            
			            <%if("Y".equals(opt375)){ %>
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
			            <%}else{ %>
			            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="70" class="search_title">&nbsp;</td> 
				                <td class="search_text">&nbsp;</td>
				              </tr>
				            </table>
			            <%} %>           
			            </td>
			            <td>&nbsp;</td>	
			            <td>
			            	<%if("Y".equals(opt377)){ %>
				            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					              <tr>
					                <td width="60" class="search_title"><spring:message code="list.list.button.searchTypeDocType"/></td> <!-- 문서구분 -->
					                <td class="search_text">
					                	<input type="checkbox" name="searchElecYn" id="searchElecYn" value="Y" <%if("Y".equals(resultSearchElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeElecDocType"/>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="searchNonElecYn" id="searchNonElecYn"  value="Y" <%if("Y".equals(resultSearchNonElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeNonElecDocType"/>
					                </td>
					              </tr>
					            </table>
				            <%}else{ %>
				            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					              <tr>
					                <td width="60" class="search_title">&nbsp;</td> 
					                <td class="search_text">&nbsp;</td>
					              </tr>
					            </table>
				            <% } %>
				        </td>
				        <td>&nbsp;</td>
				        <td>&nbsp;</td>
			          </tr>	 
			          </tr>
			          <% } %>         
			          <tr name="detailSearchDiv3" id="detailSearchDiv3" style="display:none;">
			            <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>			              	
		                	<% if("LOB014".equals(resultLobCode) ) {  %>
				                <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDeptName"/></td> <!-- 부서명 -->
				                <td class="search_text">
				                	<input type="text" class="input<% if("1".equals(opt342)){ %>_read<% } %>" name="searchWordDeptName" id="searchWordDeptName" style="width:100%" value="<%=resultSearchWordDeptName%>" onkeydown="if(event.keyCode==13){goSearchDoc();}" <% if("1".equals(opt342)){ %> disabled<% } %>>
				                </td>
		                	<% }else{ %>
			                	<td width="70" class="search_title"><spring:message code="list.list.button.searchTypeDocType"/></td> <!-- 문서구분 -->
				                <td class="search_text">
				                	<input type="checkbox" name="searchElecYn" id="searchElecYn" value="Y" <%if("Y".equals(resultSearchElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeElecDocType"/>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="searchNonElecYn" id="searchNonElecYn"  value="Y" <%if("Y".equals(resultSearchNonElecYn)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeNonElecDocType"/>
				                </td>	
			                <% }  %>		                
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <% if(!"LOB011".equals(resultLobCode) && !"LOB022".equals(resultLobCode)){  %>
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
							<%}else{ %>
								<td width="60" class="search_title">&nbsp;</td>
								<td class="search_text"><input type="hidden" name="searchDetType" id="searchDetType" value="<%=resultSearchDetType %>"></input>
							<% } %>
			              </tr>
			            </table></td>
			            <td>&nbsp;</td>
			            <td>&nbsp;</td>
			          </tr>
			        </table></td>
			      </tr>      
			      <% if(!"LOB011".equals(resultLobCode) && !"LOB022".equals(resultLobCode)){  %>
			      <tr name="detailSearchDiv4" id="detailSearchDiv4" style="display:none;">
			        <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeApproval"/></td><!-- 결재자 -->
			            <td class="search_text"><input type="text" class="input" name="searchApprovalName" id="searchApprovalName" size="13" value="<%=resultSearchApprovalName%>" onkeydown="if(event.keyCode==13){goSearchDoc();}">										
										&nbsp;
										</td> 
			          </tr>
			        </table></td>
			      </tr>
			      <% } %>      
			    </table>    
			    </td>
			    <td width="10"></td>
			  </tr>
  			</table>
  			<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">
			<% if(!"LOB010".equals(resultLobCode)) {  %>
			<input type="hidden" name="searchDocType" id="searchDocType" value="<%=resultDocType%>">
			<% } %>
			<input type="hidden" name="readRange" id="readRange" value="<%=resultReadingRange%>">
			<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N">
			<input type="hidden" name="excelExportYn" id="excelExportYn" value="N">		
			<input type="hidden" name="searchDeptId" id="ListFormDeptId" value="<%=resultDeptId %>">
			<input type="hidden" name="searchDeptName" id="ListFormDeptName" value="<%=resultDeptName %>">
			</form>
			</td>
		</tr>