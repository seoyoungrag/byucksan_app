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
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
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
String[] lol020 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lol021 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lol022 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lol023 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
String[] lol024 = {"searchTitle","list.list.button.searchTypeTitle","searchDrafter","list.list.button.searchTypeDrafter","searchApproval","list.list.title.headerApprovalName"};
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

searchTypeMap.put("LOL020",lol020);
searchTypeMap.put("LOL021",lol021);
searchTypeMap.put("LOL022",lol022);
searchTypeMap.put("LOL023",lol023);
searchTypeMap.put("LOL024",lol024);

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
			<acube:tableFrame type="search">
			  <tr class="search">
			    <td width="10"></td>
                <td width="45"><select name="selRange" id="selRange" onChange="javascript:chRange(this.value);">
                    <option value=""><spring:message code="list.list.button.searchTypeDefault"/></option>
					<c:forEach var="registList" items="${registList}" >
						<option value="<c:out value="${registList.registRange}" />" <c:if test="${registList.registRange == resultCurRange}"> selected</c:if> >${registList.registRange}${optionMsg}</option>
					</c:forEach>
                    </select>
                </td>
			  </tr>
  			</acube:tableFrame>

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