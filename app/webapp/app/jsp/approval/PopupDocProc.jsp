<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : PopupDocProc.jsp 
 *  Description : 발송및 접수이력정보 조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.07 
 *   수 정 자 : jth8172 
 *   수정내용 : 요건반영
 * 
 *  @author  jth8172
 *  @since 2011. 6. 07 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.appcom.vo.SendProcVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 org.anyframe.pagination.Page,
				 java.util.Locale,
				 java.util.List"
%>
<%
	response.setHeader("pragma","no-cache");	
	List<SendProcVO> results = (List<SendProcVO>) request.getAttribute("ProcVOpage");
	int nSize = results.size();
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	String compId = UtilRequest.getString(request, "compId");
	String docId = UtilRequest.getString(request, "docId");
	String newDocId = UtilRequest.getString(request, "newDocId");

	String cPageStr = UtilRequest.getString(request,"cPage");
	if(cPageStr == "") cPageStr = "1";
	
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt005 = appCode.getProperty("APT005", "APT005", "APT"); // 접수
	String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
	String apt007 = appCode.getProperty("APT007", "APT007", "APT"); // 반송
	String apt008 = appCode.getProperty("APT008", "APT008", "APT"); // 심사요청
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); // 발송
	String apt010 = appCode.getProperty("APT010", "APT010", "APT"); // 자동발송
	String apt011 = appCode.getProperty("APT011", "APT011", "APT"); // 발송보류
	String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // 배부
	String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청
	String apt020 = appCode.getProperty("APT020", "APT020", "APT"); // 재발송요청
	String ect009 = appCode.getProperty("ECT009", "ECT009", "ECT"); // 발송회수

	String okBtn = messageSource.getMessage("approval.procinfo.button.ok" , null, langType); 

	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><spring:message code="approval.procinfo.title" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"> 

$(document).ready(function(){
	init();
});

//초기화 
function init() {

} 
//목록화면 페이지이동
function changePage(p) { 
	isClose = "N";
	$("#cPage").val(p);
	$("#CompId").val("<%=compId%>");
	$("#DocId").val("<%=docId%>");
	$("#filterList").submit();
}   

function closeWin(){
	self.close();
}

</script>
</head>
<body onunload="closeWin();"topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<acube:titleBar><spring:message code="approval.procinfo.title" /></acube:titleBar>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->
<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr bgcolor="#ffffff"><td style="height:5px;"></td></tr>
	<tr>
		<td width="100%" height="100%" >
			<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
					
<%


				//==============================================================================
				// Page Navigation variables
				int CPAGE = 1;
				IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
				compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
				String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
				OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
				int sLine = Integer.parseInt(OPT424);
				int trSu = 1;
				if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
				//==============================================================================
				
				//int totalCount=nSize;	//총글수
				int curPage=CPAGE;	//현재페이지
							
				AcubeList acubeList = null; 
				acubeList = new AcubeList(sLine, 5);
				acubeList.setColumnWidth("5%,15%,15%,10%,55%");
				acubeList.setColumnAlign("center,center,center,center,left");	 
				acubeList.setListWithScroll(330);
				AcubeListRow titleRow = acubeList.createTitleRow();

				StringBuffer buff = new StringBuffer();
				buff.append("<img src='");
				buff.append(webUri);
				buff.append("/app/ref/image/icon_allcheck.gif' width='13' height='14' border='0'>");

				
				titleRow.setData(0, messageSource.getMessage("approval.procinfo.form.no" , null, langType));
				titleRow.setData(1, messageSource.getMessage("approval.procinfo.form.proctype" , null, langType));
				titleRow.setData(2, messageSource.getMessage("approval.procinfo.form.processorname" , null, langType));
				titleRow.setData(3, messageSource.getMessage("approval.procinfo.form.processdate" , null, langType));
				titleRow.setData(4, messageSource.getMessage("approval.procinfo.form.procopinion" , null, langType));

				AcubeListRow row = null;
				
 				for(int i = 0; i < nSize; i++) {
				    SendProcVO result = (SendProcVO) results.get(i);
					String enfState = result.getProcType();

					row = acubeList.createRow();

					//순번을 표시
					row.setData(0, result.getProcOrder());

					String procType = result.getProcType();
					String procMsg = "";
					
					if (apt001.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt001" , null, langType);
					} else if (apt002.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt002" , null, langType);
					} else if (apt003.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt003" , null, langType);
					} else if (apt004.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt0042" , null, langType);
					} else if (apt005.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt005" , null, langType);
					} else if (apt006.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt006" , null, langType);
					} else if (apt007.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt007" , null, langType);
					} else if (apt008.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt008" , null, langType);
					} else if (apt009.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt009" , null, langType);
					} else if (apt010.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt010" , null, langType);
					} else if (apt011.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt011" , null, langType);
					} else if (apt012.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt012" , null, langType);
					} else if (apt013.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt013" , null, langType);
					} else if (apt020.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.apt020" , null, langType);
					} else if (ect009.equals(procType)) {
					    procMsg = messageSource.getMessage("approval.procinfo.form.proctype.ect009" , null, langType);
					} else {
					    procMsg = procType;
					}
					row.setData(1, procMsg);

					String recvName = result.getProcessorName();
					row.setData(2, recvName);
					row.setAttribute(3, "title",EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getProcessDate())));
					row.setData(3, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getProcessDate())));
					
					String opinion = result.getProcOpinion();
									
					if(opinion==null) {
					    opinion = "";
					}    
				    row.setData(4, opinion);

		
			    } // for(~)

		        if(totalCount == 0){
		
					row = acubeList.createDataNotFoundRow();
					row.setData(0, messageSource.getMessage("approval.procinfo.msg.nodata" , null, langType));
		
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
		</td>
	</tr>
</acube:tableFrame>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->

<acube:buttonGroup align="right">
<acube:button id="btnConfirm" onclick="closeWin();" value="<%=okBtn%>" />
</acube:buttonGroup>
 
</acube:outerFrame> 
<form name="filterList" id="filterList" action="<%=webUri%>/app/approval/sendInfo.do" action="post">
<input name="cPage" id="cPage" TYPE="hidden" value="1"/>
<input name="CompId" id="CompId" TYPE="hidden"/>
<input name="DocId" id="DocId" TYPE="hidden"/>
</form> 
</body>
</html>