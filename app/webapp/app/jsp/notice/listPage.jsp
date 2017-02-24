<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.sds.acube.app.notice.vo.NoticeVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;

	String roleCode = (String) session.getAttribute("ROLE_CODES");
    String sysRoleCode = AppConfig.getProperty("role_appadmin", "011", "role");
    String docMngRoleCode = AppConfig.getProperty("role_doccharger", "", "role");
    
    List<NoticeVO> rows = (List<NoticeVO>) request.getAttribute("rows");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<script LANGUAGE="JavaScript">

	var webUri = '<%=webUri%>';
	
	function toggleAllSelect(checkall, element) {
		var form = document.listForm;
		var isChecked = form[checkall].checked;
	
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == element) {
				form.elements[i].checked = isChecked;
			}
		}
	}

	function addNotice() {
		openWindow('notice_add_win', '<%=webUri%>/app/notice/add.do', 520, 350, 'no');
	}
	
	function editNotice() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.edit'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.edit'/>");
		} else {
			var classCode = items[0].attributes.classCode.value;
			if(classCode == '03'){
				alert('연계 공지사항은 수정할 수 없습니다. ');
			}else{
				var reportNo = items[0].value;
				openWindow('notice_edit_win', '<%=webUri%>/app/notice/edit.do?reportNo=' + reportNo , 520, 350, 'no');
			}
		}
	}

	
	function getCheckedList() {
		var result = [];
		var form = document.listForm;
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == 'bindChk' && form.elements[i].checked) {
				result.push(form.elements[i]);
			}
		}
		return result;
	}
	
	function deleteNotice() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='notice.msg.select.item.delete'/>");
		} else {
			var reportNos = '';
			var canDelete = true;
			
			for(var i = 0; i < items.length; i++) {
				reportNos += "'"+items[i].value+"'"+","
				
				if(items[i].attributes.classCode.value == '03'){
					canDelete = false;
					break;
				}
			}
			
			if(!canDelete){
				alert('연계 공지사항은 삭제할 수 없습니다. ');
			}else{
				reportNos = reportNos.substring(0, reportNos.length-1)

				if(confirm("<spring:message code='notice.msg.select.delete.confirm'/>")){
					$.ajax({
						url : '<%=webUri%>/app/notice/delete.do',
						dataType : 'json',
						data: { 
							reportNos : reportNos						
						},
						success: function(data) {
							if(data.success) {
								var f = document.listForm;
								f.searchValue.value = '';
								f.target = '_self';
								f.action = '<%=webUri%>/app/notice/list.do';
								f.submit();

								
								alert("<spring:message code='notice.msg.delete.completed'/>");
							} else {
								alert("<spring:message code='notice.msg.delete.error'/>");
							}
						},
						error: function(data) {
							alert("<spring:message code='notice.msg.delete.error'/>");
						}
					});				
				}
			}
		}	
	}

	function changePage(pageNo) {
		var form = document.listForm;
		form.pageNo.value = pageNo;
		form.action = '<%=webUri%>/app/notice/list.do';
		form.target = '_self';
		form.submit();
	}
	
	function onSubmit() {
		var form = document.listForm;
		form.pageNo.value = 1;
		form.action = '<%=webUri%>/app/notice/list.do';
		form.target = '_self';
		form.submit();
	}
	
	function searchKeyup(){
		if(event.keyCode === 13){
			onSubmit();
		}
		return;		
	}
	
	function goPrintPage() {
		var form = document.listForm;
		var searchValue = form.searchValue.value;
		var deptId = form.deptId.value;
		
		var param = 'deptId=' + deptId + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue));

		var url = webUri + "/app/notice/print.do";
		printBox(url, param);
	}
	
	function goExcelPage() {
		var form = document.listForm;
		form.action = '<%=webUri%>/app/common/excel/notice.do';
		form.target = '_self';
		form.submit();
	}
	
	function viewNotice(reportNo) {
		openWindow('notice_edit_win', '<%=webUri%>/app/notice/view.do?reportNo=' + reportNo , 520, 350, 'no');
	}

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/notice/list.do'>
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="deptId" value="${deptId}" />
	<input type="hidden" name="deptName" value="${deptName}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.notice" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td>
			<td nowrap="nowrap">
				<acube:buttonGroup align="right">
					<acube:menuButton id="add" disabledid="" onclick="javascript:addNotice();" value='<%= m.getMessage("bind.button.add", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="edit" disabledid="" onclick="javascript:editNotice();" value='<%= m.getMessage("bind.button.edit", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="info" disabledid="" onclick="javascript:deleteNotice();" value='<%= m.getMessage("bind.button.del", null, langType) %>' />
					<acube:space between="button" />
					
					<% if(!isExtWeb) { %>
					<acube:space between="button" />
					<acube:menuButton id="print" disabledid="" onclick="javascript:goPrintPage();" value='<%= m.getMessage("bind.button.print", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="excel" disabledid="" onclick="javascript:goExcelPage();" value='<%= m.getMessage("bind.button.save.excel", null, langType) %>' />
					<% } %>
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame type="search">
					<tr>
						<acube:space between="search" />
							<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="5"></td>
										<td width="50">제목 : </td>
										<td width="5"></td>
										<td width="200">
											<div id="search-value" style="display: block; width: 100%;">
												<input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%" onkeypress="searchKeyup();" value="${searchValue}">
											</div>
										</td>
										<td width="5"></td>
										<td>
											<acube:button onclick="javascript:onSubmit();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" />
											<acube:button onclick="reset();" value='<%=m.getMessage("list.list.button.reset" , null, langType)%>' type="reset" align="left" disable="" />
										</td>
									</tr>
								</table>
							</td>
						<acube:space between="search" />
					</tr>
				</acube:tableFrame>
			</td>
		</tr>

		<tr>
			<acube:space between="menu_list" />
		</tr>
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
									    
									    titleRow.setData(++rowIndex,"순번");
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
												String inputer 		= CommonUtil.nullTrim(result.getInputerName());	
												
												row = acubeList.createRow();
												rowIndex = 0;
												
												/* row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + reportNo + "\">"); */
												row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + reportNo + "\" classCode=\"" + classCode + "\" >");
												    
												 
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
												
												row.setData(++rowIndex, rowIndex);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",rowIndex);		
												
												if(classCode.equals("03")){
													classCode = "연계 공지";
												}else if(classCode.equals("04")){
													classCode = "관리소 공지";
												}else if(classCode.equals("93")){
													classCode = "국외 출장 공지";
												}else if(classCode.equals("99")){
													classCode = "주요 보도";
												}
												
												row.setData(++rowIndex, classCode);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",classCode);				
												
												row.setData(++rowIndex, "<a href='#' onClick=\"javascript:viewNotice('" + reportNo + "');\">" + subjectTitle + "</a>");
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
									    
										acubeList.setNavigationType("normal");
										acubeList.generatePageNavigator(true); 
										acubeList.setPageDisplay(true);
										acubeList.setTotalCount(totalCount);
										acubeList.setCurrentPage(curPage);
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

</body>
</html>
