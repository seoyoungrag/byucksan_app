<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.sds.acube.app.notice.vo.NoticeVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;
    String closeBtn = m.getMessage("list.list.button.close", null, langType);
    
    List<NoticeVO> rows = (List<NoticeVO>) request.getAttribute("rows");
%>
<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.notice' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<script LANGUAGE="JavaScript">

	var initBody;
	
	function beforePrint(){
		initBody = document.body.innerHTML;
		document.body.innerHTML = printDiv.innerHTML; 
	}
	
	function afterPrint(){
		document.body.innerHTML = initBody;
	}
	
	function init(){
		window.onbeforeprint = beforePrint;
		window.onafterprint = afterPrint;
		window.print();
	}

	function closeWin(){
		if(opener.printDoc != null || opener.printDoc.closed == false){
			opener.printDoc.close();
		}
	}

	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="ltb_left" ><b><spring:message code='list.list.msg.noticePrint1'/></b></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint2'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint3'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint4'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint5'/></td>
</tr>
<tr>
	<td class="ltb_right"><acube:button onclick="closeWin();return(false);" value="<%=closeBtn%>" type="4" class="gr" /></td>
</tr>
</table>
</acube:outerFrame>

<div id="printDiv">
<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/notice/list.do'>
	<input type="hidden" name="bindId" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><spring:message code="bind.title.notice" /></acube:titleBar>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">
							<!------ 리스트 Table S --------->
							<%
							    try {
									    //==============================================================================
									    // Page Navigation variables
									    String cPageStr = request.getParameter("pageNo");
									    String sLineStr = request.getParameter("sline");
									    int CPAGE = 1;
									    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
										String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
										String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
										OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
										int sLine = Integer.parseInt(OPT424);
									    int trSu = 1;
									    int RecordSu = 0;
									    if (cPageStr != null && !cPageStr.equals(""))
										CPAGE = Integer.parseInt(cPageStr);
									    if (sLineStr != null && !sLineStr.equals(""))
										sLine = Integer.parseInt(sLineStr);

									    //==============================================================================

									    int totalCount = 0; //총글수
									    int curPage = CPAGE; //현재페이지

									    AcubeList acubeList = null;
										acubeList = new AcubeList(sLine, 6);
										acubeList.setColumnWidth("30, 100, 100,*,200,200");
										acubeList.setColumnAlign("center, center,center,left,center,center");	 

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    
									    String titleReportNo 					= messageSource.getMessage("list.notice.title.reportNo" , null, langType);
										String titleSubjectTitle 					= messageSource.getMessage("list.notice.title.subjectTitle" , null, langType);
										String titleContentsEtc 					= messageSource.getMessage("list.notice.title.contentsEtc" , null, langType);
										String titleClassCode 					= messageSource.getMessage("list.notice.title.classCode" , null, langType);
										String titleInputDate 					= messageSource.getMessage("list.notice.title.inputDate" , null, langType);
										String titleInputer 					= messageSource.getMessage("list.notice.title.inputer" , null, langType);
									    
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
									    
									    titleRow.setData(++rowIndex,titleReportNo);
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,titleClassCode);
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,titleSubjectTitle);
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,titleInputDate);
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,titleInputer);
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									    
									    AcubeListRow row = null;
									    
									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } else {
											for (int i = 0; i < rows.size(); i++) {
												NoticeVO result = (NoticeVO) rows.get(i);
											    
											    if(i == 0) totalCount = result.getTotalCount();
	
											    String reportNo 		= CommonUtil.nullTrim(result.getReportNo());	
												String subjectTitle 	= EscapeUtil.escapeHtmlDisp(CommonUtil.nullTrim(result.getSubjectTitle()));	
												String contentsEtc 	= EscapeUtil.escapeHtmlDisp(CommonUtil.nullTrim(result.getContentsEtc()));	
												String classCode 		= CommonUtil.nullTrim(result.getClassCode());	
												String inputDate 		= CommonUtil.nullTrim(result.getInputDate());	
												String inputer 		= "관리자";	
												
												if(classCode.equals("03")){
													classCode = "일반 공지";
												}else if(classCode.equals("04")){
													classCode = "관리소 공지";
												}else if(classCode.equals("93")){
													classCode = "국외 출장 공지";
												}else if(classCode.equals("99")){
													classCode = "주요 보도";
												}
												
												row = acubeList.createRow();
												rowIndex = 0;
												
												row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + reportNo + "\">");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
												
												row.setData(++rowIndex, reportNo);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",reportNo);		
												
												row.setData(++rowIndex, classCode);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",classCode);				
												
												row.setData(++rowIndex, subjectTitle);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",subjectTitle);				
												
												row.setData(++rowIndex, inputDate);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",inputDate);				
												
												row.setData(++rowIndex, inputer);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",inputer);
											}
									    }
									    
										acubeList.generatePageNavigator(false); 
										acubeList.generate(out);
										
									} catch (Exception e) {
									    logger.error(e.getMessage());
									}
							%>
							<!------ 리스트 Table E --------->
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>
</div>

</body>
</html>
