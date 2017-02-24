<%@page import="com.sds.acube.app.notice.vo.NoticeVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO" %>
<%@page import="com.sds.acube.app.memo.vo.MemoVO"%>

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
 *  Class Name  : mainList.jsp 
 *  Description : 메인 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 24 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	//==============================================================================
	List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("ListVoApprovalWait");
	int totalCount = Integer.parseInt(request.getAttribute("totalCountApprovalWait").toString());	
	int nSize = results.size();
	String lobCode = appCode.getProperty("LOB003","LOB003","LOB");
	
	// 결재완료함
	List<AppDocVO> resultsApprovalComplete = (List<AppDocVO>) request.getAttribute("ListVoApprovalComplete");
	int totalCountComplete = Integer.parseInt(request.getAttribute("totalCountApprovalComplete").toString());
	int nSizeComplete = 0;
	if(resultsApprovalComplete != null ){
	    nSizeComplete = resultsApprovalComplete.size();
	}
	String lobCompleteCode = appCode.getProperty("LOB010","LOB010","LOB");
	
	// 임원문서함
	List<AppDocVO> resultsOfficerBox = (List<AppDocVO>) request.getAttribute("ListVoOfficerBox");
	int totalCountOfficerBox = Integer.parseInt(request.getAttribute("totalCountOfficerBox").toString());
	int nSizeOfficerBox = 0;
	if(resultsOfficerBox != null ){
	    nSizeOfficerBox = resultsOfficerBox.size();
	}
	String lobOfficerCode = appCode.getProperty("LOB015","LOB015","LOB");
	
	// 배부대기함
	List<EnfDocVO> resultsDistributions = (List<EnfDocVO>) request.getAttribute("ListVoDistributions");
	int totalCountDistributions = Integer.parseInt(request.getAttribute("totalCountDistributions").toString());
	int nSizeDistributions = 0;
	if(resultsDistributions != null){
	    nSizeDistributions = totalCountDistributions;
	}
	String lobDistributionsCode = appCode.getProperty("LOB007","LOB007","LOB");
	
	String deptAdminReceiveYn 	= (String) request.getAttribute("deptAdminReceiveYn");
	String officerDocYn 		= (String) request.getAttribute("officerDocYn");
	String sendJudgeAuthYn 		= (String) request.getAttribute("sendJudgeAuthYn");
	String compDocMgrYn			= (String) request.getAttribute("compDocMgrYn");
	
	int totalCountProgress 	= (Integer) request.getAttribute("totalCountProgress");
	int totalCountReceive 	= (Integer) request.getAttribute("totalCountApprovalReceive");
	int totalCountDisplay 	= (Integer) request.getAttribute("totalCountApprovalDisplay");
	int totalCountSendWait 	= (Integer) request.getAttribute("totalCountSendWait");
	int totalCountSendJudge = (Integer) request.getAttribute("totalCountSendJudge");
	int approvalCompleteCount = (Integer) request.getAttribute("approvalCompleteCount");
	
	String opt103Title = (String) request.getAttribute("opt103Title");
	String opt104Title = (String) request.getAttribute("opt104Title");
	String opt105Title = (String) request.getAttribute("opt105Title");
	String opt106Title = (String) request.getAttribute("opt106Title");
	String opt107Title = (String) request.getAttribute("opt107Title");
	String opt108Title = (String) request.getAttribute("opt108Title");
	String opt110Title = (String) request.getAttribute("opt110Title");
	String opt112Title = (String) request.getAttribute("opt112Title");
	String opt115Title = (String) request.getAttribute("opt115Title");
	
	String opt103Yn = (String) request.getAttribute("opt103Yn");
	String opt104Yn = (String) request.getAttribute("opt104Yn");
	String opt105Yn = (String) request.getAttribute("opt105Yn");
	String opt106Yn = (String) request.getAttribute("opt106Yn");
	String opt108Yn = (String) request.getAttribute("opt108Yn");
	String opt110Yn = (String) request.getAttribute("opt110Yn");
	String opt112Yn = (String) request.getAttribute("opt112Yn");
	String opt115Yn = (String) request.getAttribute("opt115Yn");
	
	String searchStartDate = DateUtil.getFormattedShortDate(EscapeUtil.escapeDate((String) request.getAttribute("searchStartDate")));
	String searchEndDate = DateUtil.getFormattedShortDate(EscapeUtil.escapeDate((String) request.getAttribute("searchEndDate")));
	
	//==============================================================================
	
	//==============================================================================
	// Page Navigation variables	
	String cPageStr = request.getParameter("cPage");	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderType 				= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 				= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDrafterDept 		= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDraftReceive		= messageSource.getMessage("list.list.title.headerDraftReceive" , null, langType);
	String msgHeaderLastUpdateDate		= messageSource.getMessage("list.list.title.headerLastUpdateDate" , null, langType);
	String msgHeaderDraftReceiveDate	= messageSource.getMessage("list.list.title.headerDraftReceiveDate" , null, langType);
	String msgHeaderSenderName 			= messageSource.getMessage("list.list.title.headerSenderName" , null, langType);
	String msgHeaderDocSate	 			= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgHeaderDrafterName			= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderDraftDate 			= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
	String msgHeaderApprovalName		= messageSource.getMessage("list.list.title.headerApprovalName" , null, langType);
	String msgHeaderApprovalDate		= messageSource.getMessage("list.list.title.headerApprovalDate" , null, langType);
	String msgHeaderDocNumber			= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderSendDate 			= messageSource.getMessage("list.list.title.headerSendDate" , null, langType);
	String msgHeaderSendDeptName 		= messageSource.getMessage("list.list.title.headerSendDeptName" , null, langType);
	String msgHeaderRequestDeptName		= messageSource.getMessage("list.list.title.headerRequestDistributeDeptName" , null, langType);
	String msgNoData 					= messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	
	
	
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String searchStartEndDate = "";
	searchStartEndDate =  messageSource.getMessage("list.list.msg.searchDate" , null, langType)+" : "+ searchStartDate + " ~ " + searchEndDate;
	String draftYn = AppConfig.getProperty("draftYn", "Y", "systemOperation");	
	
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/list/common/ListMainCommon.jsp" flush="true" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	try {	
		if (!HwpCtrl.RegisterModule("FilePathCheckDLL", "HwpLocalFileAccess")) {
			document.getElementById("appHwpAccess").click();
			alert("<spring:message code='hwpconst.msg.not_installed_hwpfileaccess'/>");
		}
	} catch (error) {
	}
}

function viewNotice(reportNo) {
	openWindow('notice_edit_win', '<%=webUri%>/app/notice/view.do?reportNo=' + reportNo , 520, 350, 'no');
}

function viewMemo(memoId) {
	openWindow('memo_view_win', '<%=webUri%>/app/memo/view.do?isMainpage=yes&memoId=' + memoId , 520, 450, 'no');
}

function goNoticeList(){
	var url = "<%=webUri%>/app/index.do?type=notice";
	parent.parent.frames["content"].location.href = url;
}

function goMemoList(){
	var url = "<%=webUri%>/app/index.do?type=receiveMemo";
	parent.parent.frames["content"].location.href = url;
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="<%=webUri%>/app/ref/image/common/approval_bg.gif" class="approval_main"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> </tr>
          <tr>
            <%if("Y".equals(opt103Yn)){ %>
            <!-- 결재대기함 -->
            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="center" valign="top"><img ondblclick="javascript:goApprovalWait();" src="<%=webUri%>/app/ref/image/common/approval_icon01_0809.gif"></td>
                </tr>
                <tr>
                  <td align="center" class="approval_tit0809"><a href="#" onclick="javascript:goApprovalWait();"><%=opt103Title %></a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goApprovalWait();"><%=totalCount%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
                </tr>
            </table></td>
            <td width="2"><img src="<%=webUri%>/app/ref/image/common/approval_jul.gif"></td>
            <% } %>
            <%if("Y".equals(opt104Yn)){ %>
            <!--  결재진행함 -->
            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="center" valign="top"><img ondblclick="javascript:goApprovalProgress();" src="<%=webUri%>/app/ref/image/common/approval_icon05_0811.gif"></td>
                </tr>
                <tr>
                  <td align="center" class="approval_tit0809" title="<%=searchStartEndDate%>"><a href="#" onclick="javascript:goApprovalProgress();"><%=opt104Title %></a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goApprovalProgress();"><%=totalCountProgress%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
                </tr>
            </table></td>
            <td width="2"><img src="<%=webUri%>/app/ref/image/common/approval_jul.gif" width="2" height="55"></td>
            <% } %>
           <% if(!isExtWeb){  %>
           	   <%if("Y".equals(opt105Yn)){ %>
	           <!--  발송대기함 -->
	            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="2015-12-290">
	                <tr>
	                  <td align="center" valign="top"><img ondblclick="javascript:goSendWait();" src="<%=webUri%>/app/ref/image/common/approval_icon03_0809.gif"></td>
	                </tr>
	                <tr>
	                  <td align="center" class="approval_tit0809"><a href="#" onclick="javascript:goSendWait();"><%=opt105Title %></a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goSendWait();"><%=totalCountSendWait%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
	                </tr>
	            </table></td>
	            <td width="2"><img src="<%=webUri%>/app/ref/image/common/approval_jul.gif" width="2" height="55"></td>
	            <% } %>
	            <%if("Y".equals(sendJudgeAuthYn) && "Y".equals(opt106Yn) ) { %>
		            <!--  발송심사함 -->
		            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                  <td align="center" valign="top"><img ondblclick="javascript:goSendJudge();" src="<%=webUri%>/app/ref/image/common/approval_icon05_0809.gif"></td>
		                </tr>
		                <tr>
		                  <td align="center" class="approval_tit0809"><a href="#" onclick="javascript:goSendJudge();"><%=opt106Title %></a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goSendJudge();"><%=totalCountSendJudge%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
		                </tr>
		            </table></td>
		            <td width="2"><img src="<%=webUri%>/app/ref/image/common/approval_jul.gif" width="2" height="55"></td>
	            <%} %>           
	            <%if("Y".equals(deptAdminReceiveYn) && "Y".equals(opt108Yn) ) { %>
		            <!--  접수대기함 -->
		            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                  <td align="center" valign="top"><img ondblclick="javascript:goReceiveWait();" src="<%=webUri%>/app/ref/image/common/approval_icon02_0809.gif"></td>
		                </tr>
		                <tr>
		                  <td align="center" class="approval_tit0809"><a href="#" onclick="javascript:goReceiveWait();"><%=opt108Title %></a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goReceiveWait();"><%=totalCountReceive%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
		                </tr>
		            </table></td>
		            <td width="2"><img src="<%=webUri%>/app/ref/image/common/approval_jul.gif" width="2" height="55"></td>
	            <% } %>
            <% } %>
            <%if("Y".equals(opt112Yn)){ %>
            <!--  공람문서함 --> <!-- 문서함으로 변경 by 서영락, 2016-01-14 -->
            <td align="center" class="approval_padding"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="center" valign="top"><img ondblclick="javascript:goComplete();" src="<%=webUri%>/app/ref/image/common/approval_icon04_0809.gif"></td>
                </tr>
                <tr>
                  <td align="center" class="approval_tit0809" title="<%=searchStartEndDate%>"><a href="#" onclick="javascript:goComplete();">완료함</a> <span class="approval_tit0810">&nbsp;<a href="#" onclick="javascript:goComplete();"><%=approvalCompleteCount%></a>&nbsp;</span><spring:message code='list.list.msg.cnt'/></td>
                </tr>
            </table></td>
            <% } %>
          </tr>
        </table></td>
        <td width="8">&nbsp;</td>
        <% if(!isExtWeb && "Y".equals(draftYn)){  %>
        <td width="143" align="right"><a href="#" onclick="javascript:lfn_formPop();"><img src="<%=webUri%>/app/ref/image/common/approval_mon.gif" width="143" height="99" border="0"></a></td>
        <% } else { %>
        <td width="143" align="right"><img src="<%=webUri%>/app/ref/image/common/approval_mon.gif" width="143" height="99" border="0"></td>
        <% } %>
      </tr>
    </table></td>
   </tr>
  </table>
</acube:outerFrame>
<table>
	<tr>
		<acube:space between="title_button" />
	</tr>
</table>
<%if("Y".equals(opt103Yn)){ %>
<acube:outerFrame>
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_left.gif" /></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg.gif" class="janja_boxtit"><span class="janja_boxtitl"><%=opt103Title %></span><span class="janja_boxtitr"><a href="#" onclick="javascript:goApprovalWait();"><img src="<%=webUri%>/app/ref/image/common/approval_plus.gif" border="0"></a></span></td>
        <td align="right"><img src="<%=webUri%>/app/ref/image/common/ml_right.gif" /></td>
      </tr>
      <tr>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg2.gif"></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
            <!-- <tr>
              <td width="10"><img src="<%=webUri%>/app/ref/image/common/approval_bullet_0809 .gif"></td>
              <td width="98%" class="approval_tit"></td>
              <td width="100" align="right"><a href="#" onclick="javascript:goApprovalWait();"><img src="<%=webUri%>/app/ref/image/common/approval_plus.gif" width="41" height="11" border="0"></a></td>
            </tr>
            <tr>
              <td height="15" colspan="3"></td>
            </tr> -->
            <tr>
			<!------ 결재대기함 시작--------->
              <td colspan="3">
              	<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
						<%		
						
						AcubeList acubeList = null;
						// 제목,부서,기안/접수자,상신일자,문서상태,첨부
						acubeList = new AcubeList(sLine, 7);
					
						acubeList.setColumnWidth("*,100,100,200,150,100,100");
						acubeList.setColumnAlign("left,center,center,center,center,center,center");	 
						
						AcubeListRow titleRow = acubeList.createTitleRow();
						int rowIndex=0;
						
						titleRow.setData(rowIndex,msgHeaderTitle);
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						titleRow.setData(++rowIndex,msgHeaderDrafterDept);
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						titleRow.setData(++rowIndex,msgHeaderDraftReceive);
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						titleRow.setData(++rowIndex,"상신일자");
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						titleRow.setData(++rowIndex,"문서상태");
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						titleRow.setData(++rowIndex,"첨부");
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

						titleRow.setData(++rowIndex,"수신경과일");
						titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						
						AcubeListRow row = null;
						
						String apt004 		= appCode.getProperty("APT004","APT004","APT");

						String enf500		= appCode.getProperty("ENF500","ENF500","ENF");
						
						// 데이타 개수만큼 돈다...(행출력)
						for(int i = 0; i < nSize; i++) {
						
						AppDocVO result = (AppDocVO) results.get(i);
						
						String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
						String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
						String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
						String rsDrafterId 		= CommonUtil.nullTrim(result.getDrafterId());
						String rsDeptName 		= CommonUtil.nullTrim(result.getDrafterDeptName());
						String rsDrafterName 	= CommonUtil.nullTrim(result.getDrafterName());
						String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));					            
						String rsSenderDeptId 	= CommonUtil.nullTrim(result.getSenderDeptId());
						String rsSenderDeptName	= CommonUtil.nullTrim(result.getSenderDeptName());
						String rsSenderCompId 	= CommonUtil.nullTrim(result.getSenderCompId());
						String rsSenderCompName = CommonUtil.nullTrim(result.getSenderCompName());
						String rsReturnDocYn	= CommonUtil.nullTrim(result.getReturnDocYn());
						String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
						String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
						String rsDocState		= CommonUtil.nullTrim(result.getDocState());
						String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
						String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
						String rsProcType		= CommonUtil.nullTrim(result.getProcType());
						String rsTempYn			= CommonUtil.nullTrim(result.getTempYn()); 
			          //jkkim added security 관련 추가 start
			            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
			            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
			            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
			            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
			            String rsReadDate	= CommonUtil.nullTrim(result.getReadDate());
			            //end
						String linkScriptName	= "";
						String titleMsg			= "";
						String docTypeMsg		= "";
						String docStateMsg		= "";
						String sendInfo			= "";
						String titleDate		= "";
						
						int rsAttach 			= result.getAttachCount();
						
						
						if("APP".equals(docGubun)) {
							docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
							docGubun = "APP";
							
						}else{
							docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
							docGubun = "ENF";
						}
						
						docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType); 
						
						titleMsg = rsTitle;
						if("Y".equals(urgencyYn)) {
							rsTitle = "<font color='red'>"+rsTitle+"</font>";
						}else{//by 서영락, 2016-01-14
							if(!rsReadDate.equals("")){
								if("9999".equals(rsReadDate.substring(0,4))){
					        		rsTitle = "<b>"+rsTitle+"</b>";
				            	}						
							}
			            }
						
						if("Y".equals(electronDocYn)) {
							if("APP".equals(docGubun)){
								linkScriptName = "selectAppDoc"; 
							}else{
								linkScriptName = "selectEnfDoc";
						}
						
							titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
						}else{
							if("APP".equals(docGubun)){
								linkScriptName = "selectNonAppDoc";  
							}else{
								linkScriptName = "selectNonEnfDoc";
							}
						
							titleDate = rsDate;
						}
						
						if("ENF".equals(docGubun)) {
							if(rsCompId.equals(rsSenderCompId)) {
								sendInfo = rsSenderDeptName;
							}else{
								if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
									if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
										sendInfo = rsSenderCompName;
									}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
										sendInfo = rsSenderDeptName;
									}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
										sendInfo = rsSenderCompName + "/" + rsSenderDeptName; 
									}
								}
							}
						}
						
						if("Y".equals(rsTempYn)) {
				        	docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
				        }		
						
						StringBuffer buff;
						
						row = acubeList.createRow();
						row.setAttribute("id", rsDocId);
						row.setAttribute("elecYn", electronDocYn);
						
						rowIndex = 0;
						
						//jkkim added 보안문서 아이콘 표시 start 
						boolean isDuration = false;
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
						    rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
						    linkScriptName = "selectAppDocSec";
						}
						//end
						

						//20161120 서영락
						long titleDateDuration = 5;
						String standard = AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date");
						try{
							if(titleDate!=null && !titleDate.equals("")){
								titleDateDuration = DateUtil.diffOfDate(DateUtil.getFormattedDate(titleDate,standard), DateUtil.getCurrentDate(standard));
							}
							if(titleDateDuration>7){
								row.setData(rowIndex, "<a href=\"#\" style=\"color:red;\"  onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
							}else{
								row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
							}
						}catch(Exception e){
							row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
						}
						//20161120 서영락
						//row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						//row.setAttribute(rowIndex, "title", titleMsg);
						
						row.setData(++rowIndex, rsDeptName);
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",rsDeptName);								
						
						row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",rsDrafterName);
						
						row.setData(++rowIndex, titleDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
						row.setAttribute(rowIndex, "title",titleDate);
						
						row.setData(++rowIndex, docStateMsg);
						row.setAttribute(rowIndex, "title",docStateMsg);
						
						if(rsAttach > 0){
						    row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:fncShowAttach('"+rsDocId+"','N');fncMoveAttachDiv(event);\"><img src=\"" + webUri + "/app/ref/image/icon_clip.gif\" border='0'></a>");
						}else{
						    row.setData(++rowIndex, "");
						}
							//20161106 서영락
							try{
								if(titleDate!=null && !titleDate.equals("")){
									titleDateDuration = DateUtil.diffOfDate(DateUtil.getFormattedDate(titleDate,standard), DateUtil.getCurrentDate(standard));
								}
								if(titleDateDuration>7){
									row.setData(++rowIndex, "<font color='red'>"+titleDateDuration+"일</font>");
								}else{
							    	row.setData(++rowIndex, titleDateDuration+"일");
								}
							}catch(Exception e){
								row.setData(++rowIndex, "");
							}
						} // for(~)
						
						if(totalCount == 0){
						
						row = acubeList.createDataNotFoundRow();
						row.setData(0, msgNoData);
						
						}
						
						acubeList.setNavigationType("normal");
						acubeList.generatePageNavigator(false); 
						acubeList.setTotalCount(totalCount);
						acubeList.setCurrentPage(curPage);
						acubeList.generate(out);	    
						
						%>
						</td>
					</tr>
				</table>
              
              									
              </td>
			  <!------ 결재대기함 끝--------->
            </tr>
        </table></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg3.gif"></td>
      </tr>
      <tr>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_left_01.gif" /></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg1.gif"></td>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_right_01.gif" /></td>
      </tr>
    </table>
</acube:outerFrame>
<% } %>
  <% if(!isExtWeb){  %>
  
  <%-- <table>
	<tr>
		<acube:space between="title_button" />
	</tr>
  </table> --%>
  <%-- <acube:outerFrame>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_left.gif"/></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg.gif"></td>
        <td align="right"><img src="<%=webUri%>/app/ref/image/common/ml_right.gif" /></td>
      </tr>
      <tr>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg2.gif"></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
          <tr>
            <td width="10"><img src="<%=webUri%>/app/ref/image/common/approval_bullet_0809 .gif"></td>
            <%if("N".equals(officerDocYn)) { %>
	            <% if("Y".equals(compDocMgrYn)){ %>
		            <td width="98%" class="approval_tit"><%=opt107Title %></td>
		            <td width="100" align="right"><a href="#" onclick="javascript:goDistributionWait();"><img src="<%=webUri%>/app/ref/image/common/approval_plus.gif" width="41" height="11" border="0"></a></td>
	            <%}else{ %>
	            	<td width="98%" class="approval_tit" title="<%=searchStartEndDate%>"><%=opt110Title %></td>
		            <td width="100" align="right"><a href="#" onclick="javascript:goApprovalComplete();"><img src="<%=webUri%>/app/ref/image/common/approval_plus.gif" width="41" height="11" border="0"></a></td>
	            <%} %>
            <% }else{ %>
            	<td width="98%"  class="approval_tit" title="<%=searchStartEndDate%>"><%=opt115Title %></td>
	            <td width="100" align="right"><a href="#" onclick="javascript:goOfficerBox();"><img src="<%=webUri%>/app/ref/image/common/approval_plus.gif" width="41" height="11" border="0"></a></td>
            <% } %>
          </tr>
          <tr>
            <td height="15" colspan="3"></td>
          </tr>
          <tr>
            <% //임원 열람함 권한이 있으면 결재완료함 대신 임원 열람함을 display %>
			<%if("N".equals(officerDocYn)) { %>
				<% if("Y".equals(compDocMgrYn)){ %>
					<!-- 배부대기함 시작 -->
					<td colspan="3">
						<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->	
								<%		
								
								// 재배부요청함 사용여부
								String opt119 = appCode.getProperty("OPT119", "OPT119", "OPT");
								String opt119Yn = envOptionAPIService.selectOptionValue(compId, opt119);
								
								AcubeList acubeList = null;
								// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 함께 표시하기 때문에 재배부요청부서 필드가 추가됨.
								if("N".equals(opt119Yn)) {
									acubeList = new AcubeList(sLine, 5);
									acubeList.setColumnWidth("*,100,100,80,180");
									acubeList.setColumnAlign("left,center,center,center,center");
								} else {
									acubeList = new AcubeList(sLine, 4);
									acubeList.setColumnWidth("*,100,80,180");
									acubeList.setColumnAlign("left,center,center,center");
								}
								
								AcubeListRow titleRow = acubeList.createTitleRow();
								int rowIndex=0;
								
								titleRow.setData(rowIndex,msgHeaderTitle);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 함께 표시하기 때문에 재배부요청부서 필드가 추가됨.
								if("N".equals(opt119Yn)) {
									titleRow.setData(++rowIndex,msgHeaderRequestDeptName);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}

								titleRow.setData(++rowIndex,msgHeaderDocNumber);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderSendDate);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderSendDeptName);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
																
								AcubeListRow row = null;
								
								
								String tempAttachYn = "N";
								
								// 데이타 개수만큼 돈다...(행출력)
								for(int i = 0; i < nSizeDistributions; i++) {
								    
								    EnfDocVO result = (EnfDocVO) resultsDistributions.get(i);
								    
								    String rsCompId			= CommonUtil.nullTrim(result.getCompId());
								    String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
						            String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
						            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(result.getDocNumber());
						            String rsReceiveDate 	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getSendDate()));
						            String titleDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSendDate()));
						            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
						            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(result.getSenderDeptName());
						            int rsAttach			= result.getAttachCount();
						            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
						            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
						            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
						            String rsOriginCompId	= CommonUtil.nullTrim(result.getOriginCompId());
						            String titleMsg			= "";
						            String senderInfo 		= "";
						            
						            String rsDeptId			= CommonUtil.nullTrim(result.getAcceptDeptId());
						            String rsDeptName		= CommonUtil.nullTrim(result.getAcceptDeptName());
						            
						            int rsReceiverOrder		= result.getReceiverOrder();

						            if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
						        		if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderCompName;
						        		}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderDeptName;
						        		}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
						        		    senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
						        		}
						            }
						            
						            titleMsg = rsTitle;
						            if("Y".equals(urgencyYn)) {
						        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
						            }
						            
						            row = acubeList.createRow();
									row.setAttribute("id", rsDocId);
									row.setAttribute("elecYn", electronDocYn);
						
									rowIndex = 0;
								
								
									row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:selectCompAppDoc('"+rsOriginCompId+"', '"+rsDocId+"','"+lobDistributionsCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N', '"+rsReceiverOrder+"');\">"+rsTitle+"</A>");	
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title", titleMsg);
									
									// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 함께 표시하기 때문에 재배부요청부서 필드가 추가됨.
									if("N".equals(opt119Yn)) {
										row.setData(++rowIndex, rsDeptName);
										row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										row.setAttribute(rowIndex, "title",rsDeptName);
									}
									
									row.setData(++rowIndex, rsDocNumber);
									row.setAttribute(rowIndex, "title",rsDocNumber);
									
									row.setData(++rowIndex, rsReceiveDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectCompAppDoc('"+rsOriginCompId+"','"+rsDocId+"','"+lobDistributionsCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y', '"+rsReceiverOrder+"');\"> </A>");
									row.setAttribute(rowIndex, "title",titleDate);
									
									row.setData(++rowIndex, senderInfo);
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex, "title",senderInfo);
						
							    } // for(~)
				
						        if(totalCountDistributions == 0){
						
						            row = acubeList.createDataNotFoundRow();
									row.setData(0, msgNoData);
						
						        }
							    
						        acubeList.setNavigationType("normal");
								acubeList.generatePageNavigator(false); 
								acubeList.setTotalCount(totalCountDistributions);
								acubeList.setCurrentPage(curPage);
								acubeList.generate(out);
								
								    
								%>
								</td>
							</tr>
						</table>
					</td>
					<!-- 배부대기함 끝 -->
				<% }else{ %>
					<!------ 결재완료함 시작--------->
					<td colspan="3">
						<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
								<%		
								
								AcubeList acubeList = null;
								acubeList = new AcubeList(sLine, 6);
								acubeList.setColumnWidth("40,*,100,100,100,90");
								acubeList.setColumnAlign("center,left,center,center,center,center");	 
								
								AcubeListRow titleRow = acubeList.createTitleRow();
								int rowIndex=0;
								
								titleRow.setData(rowIndex,msgHeaderType);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderTitle);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderDocNumber);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderDrafterDept);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderDrafterName);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								titleRow.setData(++rowIndex,msgHeaderDraftReceiveDate);
								titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								AcubeListRow row = null;
								
								
								String tempAttachYn = "N";
								
								// 데이타 개수만큼 돈다...(행출력)
								for(int i = 0; i < nSizeComplete; i++) {
								
								AppDocVO result = (AppDocVO) resultsApprovalComplete.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
								String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
								String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
								String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
								String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
								String rsDeptName 		= CommonUtil.nullTrim(result.getDrafterDeptName());
								int rsAttach			= result.getAttachCount();
								String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
								String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
								String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
								String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
								String rsDocNumber		= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
					            int rsSerialNumber		= result.getSerialNumber();
					            int rsSubSerialNumber	= result.getSubserialNumber();	
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					            
					          //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
							  System.out.println("SecurityYn : "+rsSecurityYn);
							  System.out.println("rsSecurityPass : "+rsSecurityPass);
							  System.out.println("rsSecurityStartDate : "+rsSecurityStartDate);
							  System.out.println("rsSecurityEndDate : "+rsSecurityEndDate);
					            //end
			
								String titleMsg			= "";
								String docTypeMsg		= "";
								String linkScriptName	= "";
								String titleDate		= "";
								
								if("APP".equals(docGubun)) {
									docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
								}else{
									docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
								}
								
								titleMsg = rsTitle;
								if("Y".equals(urgencyYn)) {
									rsTitle = "<font color='red'>"+rsTitle+"</font>";
								}
								
								if("Y".equals(electronDocYn)) {
									if("APP".equals(docGubun)){
										linkScriptName = "selectAppDoc"; 
									}else{
										linkScriptName = "selectEnfDoc";
									}
									
									titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
								
								}else{
									if("APP".equals(docGubun)){
										linkScriptName = "selectNonAppDoc";  
									}else{
										linkScriptName = "selectNonEnfDoc";
									}
									
									titleDate = rsDate;
								}
								
								 if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
						            if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
					        			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
					            	}else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
					            		rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
					            	}
					            }else{
					        		rsDocNumber = "";
					            }
								
								
								StringBuffer buff;
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
								
								rowIndex = 0;
								
								
								row.setData(rowIndex, docTypeMsg);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", docTypeMsg);
								
								//jkkim added 보안문서 아이콘 표시 start 
								boolean isDuration = false;
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
								    rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
								    linkScriptName = "selectAppDocSec";
								}
								//end
								
								
								
								if("Y".equals(rsUnRegistYn)) {
									row.setData(++rowIndex, rsTitle);
								}else{
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCompleteCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", titleMsg);
								
								row.setData(++rowIndex, rsDocNumber);
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDocNumber);	
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDeptName);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobCompleteCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);
								
								
								} // for(~)
								
								if(totalCountComplete == 0){
								
								row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
								
								}
								
								acubeList.setNavigationType("normal");
								acubeList.generatePageNavigator(false); 
								acubeList.setTotalCount(totalCountComplete);
								acubeList.setCurrentPage(curPage);
								acubeList.generate(out);	    
								
								%>
								</td>
							</tr>
						</table>
					</td>
				<!------ 결재완료함 끝--------->
			<% } %>
		<% }else{ %>
			<!------ 임원열람함 시작--------->
				<td colspan="3">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
				
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 7);
							acubeList.setColumnWidth("40,*,100,100,80,100,80");
							acubeList.setColumnAlign("center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDrafterName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApprovalName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApprovalDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
							String tempAttachYn = "N";
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSizeOfficerBox; i++) {
							    
							    AppDocVO result = (AppDocVO) resultsOfficerBox.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
					            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
					            String rsDeptName 		= CommonUtil.nullTrim(result.getDrafterDeptName());
					            String rsApprovalId		= CommonUtil.nullTrim(result.getApproverId());
					            String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
					            String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
					            int rsAttach			= result.getAttachCount();
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					          //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());

					            //end
					            String titleMsg			= "";
					            String titleDate		= "";
					            String titleApprDate	= "";
					            String linkScriptName	= "";
					            String docTypeMsg		= "";
					            
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if("APP".equals(rsDocId.substring(0,3))) {
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);					        		
					            }else{
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
					            }
					            
						        	
					            
								if("Y".equals(electronDocYn)) {
								    if("APP".equals(rsDocId.substring(0,3))) {
						        	    linkScriptName = "selectAppDoc"; 
					        		}else{
					        		    linkScriptName = "selectEnfDoc";
					        		}
								    
								    titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
								    titleApprDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
								}else{
								    if("APP".equals(rsDocId.substring(0,3))) {
						        	    linkScriptName = "selectNonAppDoc"; 
					        		}else{
					        		    linkScriptName = "selectNonEnfDoc";
					        		}
								    
								    titleDate = rsDate;
								    titleApprDate = rsApprovalDate;
								}
	
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								row.setData(rowIndex, docTypeMsg);								
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", docTypeMsg);
								
								//jkkim added 보안문서 아이콘 표시 start 
								boolean isDuration = false;
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
								    rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
								    linkScriptName = "selectAppDocSec";
								}
								//end
								
								if("Y".equals(rsUnRegistYn)) {
									row.setData(++rowIndex, rsTitle);
								}else{
									row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobOfficerCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", titleMsg);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDeptName);	
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
								row.setData(++rowIndex, rsDate);
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsApprovalId+"');return(false);\">"+rsApprovalName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsApprovalName);
								
								row.setData(++rowIndex, rsApprovalDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+lobOfficerCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleApprDate);
								
					
						    } // for(~)
			
					        if(totalCountOfficerBox == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(false); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	    
						    
							%>
							</td>
						</tr>
					</table>
				</td>
			<!------ 임원열람함 끝--------->
		<% } %>
          </tr>
        </table></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg3.gif"></td>
      </tr>
      <tr>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_left_01.gif" /></td>
        <td background="<%=webUri%>/app/ref/image/common/ml_bg1.gif"></td>
        <td><img src="<%=webUri%>/app/ref/image/common/ml_right_01.gif" /></td>
      </tr>
    </table>
    
    
    </acube:outerFrame> --%>
    <form name="listSearch" id="listSearch" margin="0">
	<input type="hidden" name="excelExportYn" id="excelExportYn" value="">
	<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="">
	</form>
  <% } %>

<table>
	<tr>
		<acube:space between="title_button" />
	</tr>
</table>

<script type="text/javascript">
	var objectTag = "<OBJECT id='HwpCtrl' name='HwpCtrl' width='1' height='1' classid='CLSID:BD9C32DE-3155-4691-8972-097D53B10052'>"
		+ "	<PARAM name='_Version' value='65536'/>"
		+ "	<PARAM name='_ExtentX' value='20902'/>"
		+ "	<PARAM name='_ExtentY' value='12435'/>"
		+ "	<PARAM name='_StockProps' value='0'/>"
		+ "	<PARAM name='FILENAME' value=''/>'"
		+ "</OBJECT>";
	document.write(objectTag);
</script>
<a id="appHwpAccess" href="<%=webUri%>/app/ref/cabfile/AppHwpAccess.exe"></a>
<jsp:include page="/app/jsp/common/seed.jsp" />

<!-- 첨부파일 div -->
<jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
<!-- 첨부파일 div 끝-->

<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>