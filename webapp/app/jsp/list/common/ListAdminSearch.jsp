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
<%@ page import="com.sds.acube.app.list.util.ListUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListAdminSearch.jsp 
 *  Description : 리스트 공통 검색  
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 25 
 *  @version 1.0 
 *  @see
 */ 
%>

<%
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

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
String resultCurRange 			= CommonUtil.nullTrim(resultSearchVO.getRegistCurRange());
String resultSearchTypeYn		= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeYn());
String resultSearchTypeValue	= CommonUtil.nullTrim(resultSearchVO.getRegistSearchTypeValue());
String resultLobCode			= CommonUtil.nullTrim(resultSearchVO.getLobCode());
String resultDeptId				= ListUtil.TransReplace(CommonUtil.nullTrim(resultSearchVO.getDeptId()),"'","");
String resultDeptName			= CommonUtil.nullTrim(resultSearchVO.getDeptName());
String resultProcType			= CommonUtil.nullTrim(resultSearchVO.getProcType());
String resultSearchDeptCategory	= CommonUtil.nullTrim(resultSearchVO.getSearchDeptCategory());
String resultSearchSerialNumber	= CommonUtil.nullTrim(resultSearchVO.getSearchSerialNumber());

//검색조건 - 감사
String resultSearchAuditOpt009	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt009());
String resultSearchAuditOpt021	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt021());
String resultSearchAuditOpt022	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt022());
String resultSearchAuditOpt023	= CommonUtil.nullTrim(resultSearchVO.getSearchAuditOpt023());

String optionMsg				= "";

if("Y".equals(resultSearchTypeYn)){
    if("Y".equals(resultSearchTypeValue)) {
		optionMsg = messageSource.getMessage("list.list.msg.year" , null, langType);
    }else if("P".equals(resultSearchTypeValue)) {
		optionMsg = messageSource.getMessage("list.list.msg.period" , null, langType);
    }
}


pageContext.setAttribute("resultDocType",resultDocType);
pageContext.setAttribute("resultCurRange",resultCurRange);
pageContext.setAttribute("optionMsg",optionMsg);

resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);

String startDateId = DateUtil.getFormattedDate(resultStartDate+" 00:00:00", "yyyyMMdd");
String endDateId = DateUtil.getFormattedDate(resultEndDate+" 00:00:00", "yyyyMMdd");

String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

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
String resultSearchAppDocYn			= CommonUtil.nullTrim(resultSearchVO.getAppDocYn());
String resultSearchEnfDocYn			= CommonUtil.nullTrim(resultSearchVO.getEnfDocYn()); 

String det001 = appCode.getProperty("DET001", "DET001", "DET");
String det002 = appCode.getProperty("DET002", "DET002", "DET");
String det003 = appCode.getProperty("DET003", "DET003", "DET");
String det004 = appCode.getProperty("DET004", "DET004", "DET");
String det005 = appCode.getProperty("DET005", "DET005", "DET");
String det006 = appCode.getProperty("DET006", "DET006", "DET");
String det007 = appCode.getProperty("DET007", "DET007", "DET");
//상세조건 끝

int searchTypeSize = 0;
int searchTypeRerultSize = 0;
String searchTypeOpt = "";
String searchTypeOptName = "";
Map<String,String[]> searchTypeMap	= new HashMap<String,String[]>();

String[] lob091 = {"searchTitle","list.list.button.searchTypeTitle","searchSendDeptName","list.list.button.searchTypeSendDeptName"};
String[] lob092 = {"searchTitle","list.list.button.searchTypeTitle","searchSendDeptName","list.list.button.searchTypeSendDeptName"};
String[] lob095 = {"searchUserName","list.list.button.searchTypeUserName","searchDeptName","list.list.button.searchTypeDeptName","searchUserIp","list.list.button.searchTypeUserIp"};
String[] lob096 = {"searchTitle","list.list.button.searchTypeTitle"};
String[] lob097 = {"searchTitle","list.list.button.searchTypeTitle","searchBizSystem","list.list.button.searchTypeBizSystemName","searchBizProc","list.list.button.searchTypeBizProcName","searchOriginDocId","list.list.button.searchTypeOriginDocId"};
String[] lob099 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.title.headerDraftReceive","searchDocNum","list.list.button.searchTypeDocNum"};
String[] lol094 = {"searchTitle","list.list.button.searchTypeTitle","searchRequestDeptName","list.list.button.searchTypeRequestDeptName","searchRequestName","list.list.button.searchTypeRequestName","searchDocNumber","list.list.button.searchTypeDocNum"};
String[] lol095 = {"searchTitle","list.list.button.searchTypeTitle","searchRequestDeptName","list.list.button.searchTypeRequestDeptName","searchRequestName","list.list.button.searchTypeRequestName","searchDocNumber","list.list.button.searchTypeDocNum"};
String[] lol096 = {"searchTitle","list.list.button.searchTypeTitle","searchApproverType","list.list.button.searchTypeApproverType","searchChargeDeptName","list.list.button.searchTypeChargeDeptName","searchAuditKind","list.list.button.searchTypeAuditKind"};

String[] adm001 = {"searchTypeSendId","list.list.button.searchTypeSendId"};
String[] adm002 = {"searchTypeSendId","list.list.button.searchTypeSendId", "searchTypeReceiveId","list.list.button.searchTypeReceiveId"};

searchTypeMap.put("LOB091",lob091);
searchTypeMap.put("LOB092",lob092);
searchTypeMap.put("LOB095",lob095);
searchTypeMap.put("LOB096",lob096);
searchTypeMap.put("LOB097",lob097);
searchTypeMap.put("LOB099",lob099);
searchTypeMap.put("LOL094",lol094);
searchTypeMap.put("LOL095",lol095);
searchTypeMap.put("LOL096",lol096);

searchTypeMap.put("ADM001",adm001);
searchTypeMap.put("ADM002",adm002);

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

String rangeSearch = "N";
if(!"LOB099".equals(resultLobCode) && !"LOB097".equals(resultLobCode) && !"LOB096".equals(resultLobCode) && !"LOB095".equals(resultLobCode) && !"LOB091".equals(resultLobCode) && !"LOB092".equals(resultLobCode) && !"ADM001".equals(resultLobCode) && !"ADM002".equals(resultLobCode) ) { 
    rangeSearch = "Y";
}
%>
		
		<tr>
			<acube:space between="button_search" />
		</tr>
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			<td>
			<form name="listSearch" id="listSearch" method="post" style="margin:0px">
			
			<acube:tableFrame type="search">
			  <tr class="search">
			    <td width="10"></td>
			    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <%
			            int frontWidth = 0;
			            if(!"LOB099".equals(resultLobCode) && !"LOB092".equals(resultLobCode) && !"LOB091".equals(resultLobCode)) { 
			            	frontWidth = 455;
			            }else{ 
			            	frontWidth = 405;
			            } 
			            %>
			            <td width="<%=frontWidth%>" height="24"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <%if("Y".equals(rangeSearch)) { %>
			                <td width="45"><select name="selRange" id="selRange" onChange="javascript:chRange(this.value);">
			                    <option value=""><spring:message code="list.list.button.searchTypeDefault"/></option>
								<c:forEach var="registList" items="${registList}" >
								<option value="<c:out value="${registList.registRange}" />" <c:if test="${registList.registRange == resultCurRange}"> selected</c:if> >${registList.registRange}${optionMsg}</option>
								</c:forEach>
			                    </select></td>
			                <td width="5"></td>
			                <% } %>
			                <%if("LOB096".equals(resultLobCode) || "LOB097".equals(resultLobCode)){ %>
				                <%
				                String bps001 = appCode.getProperty("BPS001","BPS001","BPS");
				                String bps002 = appCode.getProperty("BPS002","BPS002","BPS");
				                String bps003 = appCode.getProperty("BPS003","BPS003","BPS");
				                String bps004 = appCode.getProperty("BPS004","BPS004","BPS");
				                %>
				                <td width="60">
				                <select name="selProcType" id="selProcType" onchange="javascript:chProcType(this.value);">
				                <option value=""><spring:message code="list.list.button.searchTypeDefault"/></option>
				                <option value="<%=bps001 %>" <%if(bps001.equals(resultProcType)){ %> selected <%} %>><spring:message code="list.code.msg.bps001"/></option>
				                <option value="<%=bps002 %>" <%if(bps002.equals(resultProcType)){ %> selected <%} %>><spring:message code="list.code.msg.bps002"/></option>
				                <option value="<%=bps003 %>" <%if(bps003.equals(resultProcType)){ %> selected <%} %>><spring:message code="list.code.msg.bps003"/></option>
				                <option value="<%=bps004 %>" <%if(bps004.equals(resultProcType)){ %> selected <%} %>><spring:message code="list.code.msg.bps004"/></option>
				                </select>
				                </td>
				                <td width="5"></td>
			                <% } %>
			                <td width="50"><select name="searchType" class="select" <% if("LOL096".equals(resultLobCode)){ %> onChange="chThisValue(this.value);"<% } %>>
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
			                <td width="300" class="search">
			                <div id="searchWordTd" style="display:<%=searchWordStyle%>">
			                <input name="searchWord" type="text" class="input" style="width:100%" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearch();}" >
			                </div>
			                <div id="searchAuditKindTd" style="display:<%=searchAuditKindStyle%>">
			                <% if("Y".equals(opt009AuditYn) || "Y".equals(opt010AuditYn)){ %><input type="checkbox" id="searchAuditOpt009" name="searchAuditOpt009" value="Y" <%if("Y".equals(resultSearchAuditOpt009)) { %> checked <% } %>></input><spring:message code="list.list.button.searchTypeAuditKind1"/><% } %>
			                <% if("Y".equals(opt021AuditYn)){ %><input type="checkbox" id="searchAuditOpt021" name="searchAuditOpt021" value="Y" <%if("Y".equals(resultSearchAuditOpt021)) { %> checked <% } %>></input><spring:message code="list.list.button.searchTypeAuditKind2"/><% } %>
			                <% if("Y".equals(opt022AuditYn)){ %><input type="checkbox" id="searchAuditOpt022" name="searchAuditOpt022" value="Y" <%if("Y".equals(resultSearchAuditOpt022)) { %> checked <% } %>></input><spring:message code="list.list.button.searchTypeAuditKind3"/><% } %>
			                <% if("Y".equals(opt023AuditYn)){ %><input type="checkbox" id="searchAuditOpt023" name="searchAuditOpt023" value="Y" <%if("Y".equals(resultSearchAuditOpt023)) { %> checked <% } %>></input><spring:message code="list.list.button.searchTypeAuditKind4"/><% } %>
			                </div>
			                </td>
			              </tr>
			            </table></td>
			           <%if(!"LOB091".equals(resultLobCode) && !"LOB092".equals(resultLobCode)){ %>
			            <td width="%">&nbsp;</td>
			            <td width="280"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="70" class="search_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="list.list.button.period"/></td>
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
			            <td width="58" align="right"><acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /></td>
			            <%}else{ %>
			            <td align="left"><acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /></td>
			            <% } %>
			          </tr>
			          <tr name="detailSearchDiv" id="detailSearchDiv" style="display:none">
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
			          <tr name="detailSearchDiv2" id="detailSearchDiv2" style="display:none">
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
			          <tr name="detailSearchDiv3" id="detailSearchDiv3" style="display:none">
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
			      <tr name="detailSearchDiv4" id="detailSearchDiv4" style="display:none">
			        <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="70" class="search_title"><spring:message code="list.list.button.searchTypeApproval"/></td><!-- 결재자 -->
			            <td class="search_text"><input type="text" class="input" name="searchApprovalName" id="searchApprovalName" size="13" value="<%=resultSearchApprovalName%>" onkeydown="if(event.keyCode==13){goSearch();}">
										&nbsp;( <input type="checkbox" name="searchApprTypeDraft" id="searchApprTypeDraft" value="Y" <%if("Y".equals(resultSearchApprTypeDraft)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprDraft"/> <input type="checkbox" name="searchApprTypeExam" id="searchApprTypeExam" value="Y" <%if("Y".equals(resultSearchApprTypeExam)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprExam"/> <% if("Y".equals(opt003GYn)){ %><input type="checkbox" name="searchApprTypeCoop" id="searchApprTypeCoop" value="Y" <%if("Y".equals(resultSearchApprTypeCoop)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprCoop"/> <% } %> <% if("Y".equals(opt009GYn)){ %><input type="checkbox" name="searchApprTypeAudit" id="searchApprTypeAudit" value="Y" <%if("Y".equals(resultSearchApprTypeAudit)) { %> checked <% } %>/><spring:message code="list.list.button.searchApprTypeAudit"/> <% } %><input type="checkbox" name="searchApprTypeApproval" id="searchApprTypeApproval" value="Y" <%if("Y".equals(resultSearchApprTypeApproval)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprApproval"/>  <% if("Y".equals(opt019GYn)){ %><input type="checkbox" name="searchApprTypePreDis" id="searchApprTypePreDis" value="Y" <%if("Y".equals(resultSearchApprTypePreDis)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprPreDis"/><% } %> <input type="checkbox" name="searchApprTypeResponse" id="searchApprTypeResponse" value="Y" <%if("Y".equals(resultSearchApprTypeResponse)) { %> checked <% } %>/><spring:message code="list.list.button.searchTypeApprResponse"/>  )
										</td>
			          </tr>
			        </table></td>
			      </tr>      
			    </table>    
			    </td>
			    <td width="10"></td>
			  </tr>
  			</acube:tableFrame>	
					
			<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">			
			<input type="hidden" name="searchDocType" id="searchDocType" value="<%=resultDocType%>">
			<input type="hidden" name="searchCurRange" id="searchCurRange" value="<%=resultCurRange%>">
			<input type="hidden" name="searchTypeYn" id="searchTypeYn" value="<%=resultSearchTypeYn%>">
			<input type="hidden" name="searchTypeValue" id="searchTypeValue" value="<%=resultSearchTypeValue%>">
			<input type="hidden" name="searchDeptId" id="ListFormDeptId" value="<%=resultDeptId %>">
			<input type="hidden" name="searchDeptName" id="ListFormDeptName" value="<%=resultDeptName %>">
			<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N">
			<input type="hidden" name="excelExportYn" id="excelExportYn" value="N">	
			<input type="hidden" name="searchDeptCategory" id="searchDeptCategory" value="<%=resultSearchDeptCategory %>" />
			<input type="hidden" name="searchSerialNumber" id="searchSerialNumber" value="<%=resultSearchSerialNumber %>" />
			</form>
			</td>
		</tr>