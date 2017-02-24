<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitTempVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.common.util.BindUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;

	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
	String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
	
    String dateFormat = AppConfig.getProperty("std_date_format", "yyyy-MM-dd", "date");
	
    List<BindUnitTempVO> rows = (List<BindUnitTempVO>) request.getAttribute("ListVo");
    int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());  	
    int nSize = rows.size();
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<script LANGUAGE="JavaScript"><!--
	
	var openType = 'ChangeDept';
	
	function init() {
		var form = document.listForm;
		document.getElementById('search-value').style.display = '${type}' == 'tunitProcBool' ? 'none' : 'block';
		document.getElementById('search-proc-bool').style.display = '${type}' == 'tunitProcBool' ? 'block' : 'none';
		
		var typeValue = '${type}'||'tunitName';
		form.searchType.value = typeValue;
		if(typeValue == '') {
		} else if(typeValue == 'tunitProcBool') {
			form.tunitProcBool.value = '${dType}';
		} else {
			form.searchValue.focus();
			form.searchValue.value = '${value}';
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

	function toggleSelect() {
		document.listForm['checkall'].checked = false;
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

	function goInfoPage(tunitProcId) 
	{			
		var params = 'tunitProcId='+tunitProcId;
		openWindow('tunit_info_win', '<%=webUri%>/app/bind/unit/import/info.do?'+params , 720, 650, 'no'); 
	}
	
	function goLoad() {
		var width = 500;
	    var height = 200;
	    	    
		openWindow('unitLoad', '<%=webUri%>/app/bind/unit/import/loadPage.do', width, height, 'no'); 
	}
	
	function goApply() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.unit'/>");
			return;
		}
		
		if ( confirm("<spring:message code='bind.msg.confirm.import.apply'/>") )
		{
			var tunitProcIds = "";
			for ( var i = 0; i < items.length; i++) {
				tunitProcIds += ',' + items[i].value;
				if (items[i].getAttribute("tunitProcBool") != "0") {
					alert("<spring:message code='bind.msg.apply.not.work'/>");
					return;
				}
			}
				
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/apply.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : tunitProcIds.substring(1)
				},
				success : function(data) { 
					if(data.success) {
						alert("<spring:message code='bind.msg.apply.completed'/>");
						
						var f = document.listForm;
						f.method = 'POST';
						f.submit();
						
					} else {
						if (data.resultCode == 3) {
							alert("<spring:message code='bind.msg.apply.duplicate.id'/>");
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					}
				},
				error : function(data) {
					alert(data.resultCode);
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
			
		}
	}
	
	function goRevoke() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.unit'/>");
			return;
		}
		
		if ( confirm("<spring:message code='bind.msg.confirm.import.revoke'/>") )
		{
			var tunitProcIds = "";
			for ( var i = 0; i < items.length; i++) {
				tunitProcIds += ',' + items[i].value;
				if (items[i].getAttribute("tunitProcBool") != "4") {
					alert("<spring:message code='bind.msg.revoke.not.work'/>");
					return;
				}
			}
			
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/revoke.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : tunitProcIds.substring(1)
				},
				success : function(data) { 
					if(data.success) {
						
						alert("<spring:message code='bind.msg.revoke.completed'/>");
						
						var f = document.listForm;
						f.method = 'POST';
						f.submit();
						
					} else {
						if (data.resultCode == 3) {
							alert("<spring:message code='bind.msg.revoke.bind.exist.not.work'/>");
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	function goDel() {
		
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.delete'/>");
			return;
		}

		if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{
			var tunitProcIds = "";
			var itemCnt = items.length;
			for ( var i = 0; i < itemCnt; i++) {
				tunitProcIds += ',' + items[i].value;
			}
				
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/remove.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : tunitProcIds.substring(1)
				},
				success : function(data) { 
					if(data.success) {
						alert("<spring:message code='bind.msg.delete.completed'/>");
						
						onSubmit('DEL', itemCnt);
						
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
			
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
	
	function onSubmit(mode, itemCnt) {
		var f = document.listForm;
		f.method = 'POST';
		if (mode == 'DEL') {
			f.totalCount.value = ((${totalCount}*1)-itemCnt);
		}
		f.submit();
	}
	
	function goSearch() {
		var form = document.listForm;
		form.cPage.value = 1;
		form.target = '_self';
		form.submit();
	}
	
	function changeSearchType(value) {
		document.getElementById('search-value').style.display = value == 'tunitProcBool' ? 'none' : 'block';
		document.getElementById('search-proc-bool').style.display = value == 'tunitProcBool' ? 'block' : 'none';
	}
	
<% if(roleCode.indexOf(systemAdminCode) > -1 || roleCode.indexOf(appSystemAdminCode) > -1) { %>	
	function goSelectDept() {
		openType = 'ChangeDept';
		
		var width = 500;
	    var height = 400;
	    	    
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
	}
	
	function goSelectAll() {
		var form = document.listForm;
		form.cPage.value = 1;
		form.searchType.value = "";
		form.searchValue.value = "";
		form.action = '<%=webUri%>/app/bind/unit/import/list.do?deptId=all';
		form.target = '_self';
		form.submit();
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				var form = document.listForm;
				form.cPage.value = 1;
				form.searchType.value = "";
				form.searchValue.value = "";
				form.action = '<%=webUri%>/app/bind/unit/import/list.do?deptId=' + obj.orgId;
				form.target = '_self';
				form.submit();
			} 
		}
	}
<% } %>	
	
	function changePage(p) {
		$("#cPage").val(p);
		$("#listForm").submit();
	}

	var webUri = '<%=webUri%>';
	
	window.onload = init;

--></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form id="listForm" name="listForm" method='post' action='<%=webUri%>/app/bind/unit/import/list.do'>
	<input name="cPage" id="cPage" type="hidden" value="${cPage}">
	<input name="deptId" id="deptId" type="hidden" value="${deptId}">
	<input name="totalCount" id="totalCount" type="hidden" value="${totalCount}">
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.unit.import.list" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<c:if test="${deptId !='all'}">
			<td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td>
			</c:if>
			<td nowrap="nowrap">
				<acube:buttonGroup align="right">
					<% if(roleCode.indexOf(systemAdminCode) > -1 || roleCode.indexOf(appSystemAdminCode) > -1) { %>	
					<acube:menuButton id="select_comp" disabledid="" onclick="javascript:goSelectAll();" value='<%= m.getMessage("bind.obj.all", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="select_dept" disabledid="" onclick="javascript:goSelectDept();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<% } %>
					
					<acube:menuButton id="save" disabledid="" onclick="javascript:goApply();" value='<%= m.getMessage("bind.button.import.apply", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="revoke" disabledid="" onclick="javascript:goRevoke();" value='<%= m.getMessage("bind.button.import.revoke", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="load" disabledid="" onclick="javascript:goLoad();" value='<%= m.getMessage("bind.button.import.load", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="del" disabledid="" onclick="javascript:goDel();" value='<%= m.getMessage("bind.button.del", null, langType) %>' />
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
										<td width="50"><form:select id="searchType" name="searchType" path="searchType" onchange="javascript:changeSearchType(this.value);" items="${searchType}" /></td>
										<td width="5"></td>
										<td width="200">
											<div id="search-value" style="display: block; width: 100%;">
												<input type="text" class="input" name="searchValue" onkeydown="if(event.keyCode==13){goSearch();}" maxlength="25" style="width: 100%">
											</div>
											<div id="search-proc-bool" style="display: none; width: 100%;">
												<form:select id="tunitProcBool" name="tunitProcBool" path="tunitProcBool" items="${tunitProcBool}" style="width: 100%" />
											</div>
										</td>
										<td width="5"></td>  
										<td>
											<acube:button onclick="javascript:goSearch();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" />
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
									    //String cPageStr = request.getParameter("cPage");
									    String cPageStr = (String)request.getAttribute("cPage");
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

									    int curPage = CPAGE; //현재페이지

									    AcubeList acubeList = null;
									    acubeList = new AcubeList(sLine, 6);
									    acubeList.setColumnWidth("30,*,130,120,120,100");
									    acubeList.setColumnAlign("center,left,center,center,center,center");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.dept.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.proc.date", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.division", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.status", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;
									    
									    String style = "text-overflow : ellipsis; overflow : hidden;";
									    
									    if (nSize == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } 
									    else {
									        
											for (int i = 0; i < nSize; i++) {
											    BindUnitTempVO vo = rows.get(i);
											    
											    row = acubeList.createRow();
											    rowIndex = 0;
											    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + vo.getTunitProcId()
											    		+ "\" tunitId=\"" + vo.getTunitId() 
											    		+ "\" tunitProcBool=\"" + vo.getTunitProcBool() 
											    		+ "\" >");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
											    
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goInfoPage('" + vo.getTunitProcId() + "');\" style=\"" + style + "\">" + EscapeUtil.escapeHtmlDisp(vo.getTunitName()) + "</a>");
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, vo.getTunitDptName());
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, BindUtil.getDateFormat(vo.getTunitProcDate(), dateFormat));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, m.getMessage("bind.code.tunit.proc.type." + vo.getTunitProcType(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, m.getMessage("bind.code.tunit.proc.bool." + vo.getTunitProcBool(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
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
