<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

<%@ page import="com.sds.acube.app.mig.vo.RegDocVO" %>
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
<%@ include file="/app/jsp/common/header.jsp" %>
<%

/** 
 *  Class Name  : ListApprovalCompleteBox.jsp 
 *  Description : 구버전 등록문서 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 15 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID

	//==============================================================================
	String listTitle = "구문서 등록대장";
	// 검색 결과 값
	List<RegDocVO> results = (List<RegDocVO>) request.getAttribute("ListVo");
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
	
	String msgHeaderType				= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 				= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderAttach				= messageSource.getMessage("list.list.title.headerAttach" , null, langType);	
	String msgHeaderDocNumber			= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderDrafterDept 		= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDrafterName			= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderDraftReceiveDate	= messageSource.getMessage("list.list.title.headerDraftReceiveDate" , null, langType);
	String msgHeaderDocState	 		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	
	String msgNoData 					= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<%
String roleCodes = (String) session.getAttribute("ROLE_CODES"); // 역할코드
String roleId40 = AppConfig.getProperty("role_old_doc_reader", "", "role");
%>
<script>

function goSearchDoc(){
	
/* 	var target = document.getElementById("searchType");
	var searchType= target.options[target.selectedIndex].text;
	
	if(searchType == "제목"){
		$('#searchTitle').val($('#searchWord').val());
	}else{
		$('#searchDrafter').val($('#searchWord').val());
	}
	$('#searchStartDate').val($('#startDate').val());
	$('#searchEndDate').val($('#endDate').val()); */
	
	$('#listSearch')
	.attr('action', '<%=webUri%>/app/list/mig/ListMigRegDoc.do')
	.attr('method', 'post')
	.submit();

}
<%
if (roleCodes.indexOf(roleId40)>=0) {
%>
$(document).delegate("#calYearSelect", "change",  function(){
	$('#calYearSelect option').each(function(index,value){
		if(this.value!='2014' && this.value!='2015'){
			$(this).remove();	
		}
			
	})
});

$(window).load(function(){
	cal.addDisabledDates(null, "2013-12-31");
	cal.addDisabledDates("2016-01-01", null);
	
	$('#calendarBTN1,#calendarBTN2').click(function(){
	
		$('#calYearSelect option').each(function(index,value){
			if(this.value!='2014' && this.value!='2015'){
				$(this).remove();	
			}  
				
		})
	
	});
	
});
<%}%>

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<input type="hidden" id="startDt" value="2001/01/01">
<input type="hidden" id="endDt" value="2013/12/31">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="50%" border="0" cellpadding="0" cellspacing="0" align="left">
				<tr>
					<td><acube:titleBar>구문서 등록대장</acube:titleBar></td>
				</tr>
				</table>
				<table width="50%" border="0" cellpadding="0" cellspacing="0" align="right">
					<%-- <jsp:include page="/app/jsp/list/common/ListButtonGroup.jsp" flush="true" /> --%>
				</table>
			</td>
		</tr>
			<jsp:include page="/app/jsp/list/common/ListDateSearchForMig.jsp" flush="true" />
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" id="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
							
						 	AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 9);
							// 체크박스, 문서제목, 부서, 기안자, 기안/접수일자, 완결자, 완결일자, 문서 상태, 첨부
							acubeList.setColumnWidth("30,70,150,120,*,120,150,120,120");
							acubeList.setColumnAlign("center,center,center,center,left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							 
							/* titleRow.setData(rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;"); */
							
						 	titleRow.setData(++rowIndex,"등록 번호");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"결재 일자");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"분류번호");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"문서 제목");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"기안자");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,"시행일자");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
						 	titleRow.setData(++rowIndex,"문서구분");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	 	
							
							titleRow.setData(++rowIndex,"첨부");
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							String enf500 = appCode.getProperty("ENF500","ENF500","ENF"); 
							
							// 데이타 개수만큼 돈다...(행출력)
						 	for(int i = 0; i < nSize; i++) {
							    
						 		RegDocVO result = (RegDocVO) results.get(i);
								
								String rsDocId 			= EscapeUtil.escapeHtmlDisp(result.getDocId());
					        	String rsSerialNumber	= EscapeUtil.escapeHtmlDisp(result.getSerialNumber());
					        	String rsCompletedDate  = EscapeUtil.escapeHtmlDisp(result.getCompletedDate());
					        	String rsClassNumber	= EscapeUtil.escapeHtmlDisp(result.getClassNumber());
					        	String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					        	String rsDraftName		= EscapeUtil.escapeHtmlDisp(result.getDraftName());
					        	String rsEnforceDate	= EscapeUtil.escapeHtmlDisp(result.getEnforceDate());
					        	String rsDocCategory	= EscapeUtil.escapeHtmlDisp(result.getDocCategory());
					        	String rsIsAttached		= EscapeUtil.escapeHtmlDisp(result.getIsAttached());
	
					         	
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
					
								rowIndex = 0;
								
								StringBuffer buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"   >");								
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");
								
								row.setData(++rowIndex, rsSerialNumber);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsSerialNumber);	
								
								row.setData(++rowIndex, rsCompletedDate);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsCompletedDate);	
								
								row.setData(++rowIndex, rsClassNumber);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsClassNumber);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:selectMigRegDoc('"+rsDocId+"');\">"+rsTitle+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsTitle);		
								
								row.setData(++rowIndex, rsDraftName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsDraftName);	
								
								row.setData(++rowIndex, rsEnforceDate);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsEnforceDate);	
								
								if("I".equals(rsDocCategory)){
									rsDocCategory = "내부";
								}else if("W".equals(rsDocCategory)){
									rsDocCategory = "업무연락";
								}else if("E".equals(rsDocCategory)){
									rsDocCategory = "시행";
								}
								
								row.setData(++rowIndex, rsDocCategory);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsDocCategory);	
					
								if("Y".equals(rsIsAttached)) {
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','N','useformiguseformigg');fncMoveAttachDiv(event);\"><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'>");
								}else{
								    row.setData(++rowIndex, "");
								}
								
								row.setAttribute(rowIndex, "title",rsIsAttached); 
					
						    }  // for(~)
			
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
    <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
     
     <!-- 첨부파일 div -->
     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
     <!-- 첨부파일 div 끝--> 
</acube:outerFrame>

</Body>
</Html>