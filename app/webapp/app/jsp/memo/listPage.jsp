<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.sds.acube.app.memo.vo.MemoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
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
    
    List<MemoVO> rows = (List<MemoVO>) request.getAttribute("rows");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE>${pageType == 'receive' ? '받은쪽지함':'보낸쪽지함'}</TITLE>

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
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";
	
	function init() {
		var form = document.listForm;
		var searchType = "${type}";
		if(searchType){
			form.searchType.value = "${type}";	
		}
	}
	
	function toggleAllSelect(checkall, element) {
		var form = document.listForm;
		var isChecked = form[checkall].checked;
	
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == element) {
				form.elements[i].checked = isChecked;
			}
		}
	}

	function sendMemo() {
		openWindow('memo_write_win', '<%=webUri%>/app/memo/write.do', 520, 450, 'no');
	}
	
	function getCheckedList() {
		var result = [];
		var form = document.listForm;
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == 'memoChk' && form.elements[i].checked) {
				result.push(form.elements[i]);
			}
		}
		return result;
	}
	
	function deleteMemo() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("삭제할 쪽지를 선택해주세요");
		} else {
			var memoIds = '';
			
			for(var i = 0; i < items.length; i++) {
				memoIds += "'"+items[i].value+"'"+","
			}

			memoIds = memoIds.substring(0, memoIds.length-1)

			if(confirm("선택된 모든 쪽지를 삭제합니다. 계속 진행하시겠습니까?")){
				$.ajax({
					url : '<%=webUri%>/app/memo/delete.do',
					dataType : 'json',
					data: { 
						memoIds : memoIds						
					},
					success: function(data) {
						if(data.success) {
							var f = document.listForm;
							f.searchValue.value = '';
							f.target = '_self';
							f.action = '<%=webUri%>/app/memo/list.do';
							f.submit();
							
							alert("선택한 모든 쪽지가 정상적으로 삭제되었습니다.");
						} else {
							alert("쪽지 삭제 중 에러가 발생했습니다. 관리자에게 문의하세요.");
						}
					},
					error: function(data) {
						alert("쪽지 삭제 중 에러가 발생했습니다. 관리자에게 문의하세요.");
					}
				});				
			}
		}
	}

	function changePage(pageNo) {
		var form = document.listForm;
		form.pageNo.value = pageNo;
		form.action = '<%=webUri%>/app/memo/list.do';
		form.target = '_self';
		form.submit();
	}
	
	function onSubmit() {
		var form = document.listForm;
		form.pageNo.value = 1;
		form.action = '<%=webUri%>/app/memo/list.do';
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
		var searchType = form.searchType.value;
		var pageType = form.pageType.value;
		var param = 'searchType=' + searchType + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue)) + '&pageType='+pageType;
		var url = webUri + "/app/memo/print.do";
		printBox(url, param);
	}
	
	function goExcelPage() {
		var form = document.listForm;
		form.action = '<%=webUri%>/app/common/excel/memo.do';
		form.target = '_self';
		form.submit();
	}
	
	function viewMemo(memoId) {
		openWindow('memo_view_win', '<%=webUri%>/app/memo/view.do?memoId=' + memoId , 520, 450, 'no');
	}
	
	window.onload = init;
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/memo/list.do'>
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="pageType" value="${pageType}" />
	<input type="hidden" name="deptId" value="${deptId}" />
	<input type="hidden" name="deptName" value="${deptName}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar>${pageType == 'receive' ? '받은쪽지함':'보낸쪽지함'}</acube:titleBar>
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
					<acube:menuButton id="add" disabledid="" onclick="javascript:sendMemo();" value='쪽지 보내기' />
					<acube:space between="button" />
					
					<c:if test="${pageType == 'receive'}">
						<acube:menuButton id="info" disabledid="" onclick="javascript:deleteMemo();" value='<%= m.getMessage("bind.button.del", null, langType) %>' />
						<acube:space between="button" />
					</c:if>
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
										<td width="50"><form:select id="searchType" name="searchType" path="searchType" items="${searchType}" /></td>
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
										acubeList = new AcubeList(sLine, 7);
										acubeList.setColumnWidth("30, 200, 200, *, 200,200,100");
										acubeList.setColumnAlign("center, center,center,left,center,center,center");	 

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'memoChk');\"/>");
									    
									    titleRow.setData(++rowIndex,"보낸사람");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,"받는사람");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,"제목");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,"작성일");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,"읽음여부");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
										
										titleRow.setData(++rowIndex,"첨부갯수");
										titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									    
									    AcubeListRow row = null;
									    
									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } else {
											for (int i = 0; i < rows.size(); i++) {
												MemoVO result = (MemoVO) rows.get(i);
											    
											    if(i == 0) totalCount = result.getTotalCount();
	
											    String memoId 		= CommonUtil.nullTrim(result.getMemoId());
											    String senderName 		= CommonUtil.nullTrim(result.getSenderName());
											    String receiverName 		= CommonUtil.nullTrim(result.getReceiverName());
											    String title 	= EscapeUtil.escapeHtmlDisp(CommonUtil.nullTrim(result.getTitle()));	
												String createDate 		= CommonUtil.nullTrim(result.getCreateDate());	
												String readDate 		= CommonUtil.nullTrim(result.getReadDate());	
												String attachCount 		= Integer.toString(result.getAttachCount());	
												
												row = acubeList.createRow();
												rowIndex = 0;
												
												row.setData(rowIndex, "<input type=\"checkbox\" name=\"memoChk\" value=\"" + memoId + "\">");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
												
												row.setData(++rowIndex, senderName);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",senderName);		
												
												row.setData(++rowIndex, receiverName);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",receiverName);				
												
												row.setData(++rowIndex, "<a href='#' onClick=\"javascript:viewMemo('" + memoId + "');\">" + title + "</a>");
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",title);				
												
												row.setData(++rowIndex, createDate);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",createDate);				
												
												row.setData(++rowIndex, readDate.equals("")?"읽지않음":"읽음");
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title",readDate.equals("")?"읽지않음":"읽음");
												
												row.setData(++rowIndex, attachCount);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title", attachCount);
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
