<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
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
	
    String adminSelectDept = request.getParameter("adminSelectDept");
    
    List<BindUnitVO> rows = (List<BindUnitVO>) request.getAttribute("ListVo");
    int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
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
	var allDocOrForm = '${allDocOrForm}'; // 채번사용여부(1: 모든문서(안보이게), 2: 양식에서 선택(보이게)) 
	var unitType = '${unitType}';
	
	function init() {
		var form = document.listForm;
		
		if ( '${adminSelectDept}' == 'Y' ) {
			$("#select_comp").closest("table").show();
			$("#select_dept").closest("table").show();
		}
		else {
			$("#select_comp").closest("table").show();
			$("#select_dept").closest("table").show();
		}
		
		$("input[name='searchValue']").focus();
		$("input[name='searchValue']").val('${value}');
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

	function goAddPage() 
	{
		var deptId = '${deptId}';
		var params = 'unitType='+unitType+'&deptId='+deptId+'&allDocOrForm='+allDocOrForm;
		openWindow('bind_unit_add_win', '<%=webUri%>/app/bind/unit/ex/add.do?'+params , 720, 650, 'no'); 
	}

	function goEditPage() 
	{
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.edit'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.edit'/>");
		} 
		else {
			var compId = items[0].attributes.compId.value;
			var deptId = items[0].attributes.deptId.value;
			var unitId = items[0].attributes.unitId.value;
			var unitType = items[0].attributes.unitType.value;
			
			var params = 'compId='+compId+'&deptId='+deptId+'&unitId='+unitId
							+'&unitType='+unitType+'&allDocOrForm='+allDocOrForm;

			openWindow('bind_unit_edit_win', '<%=webUri%>/app/bind/unit/ex/edit.do?'+params , 720, 650, 'no');
		}
	}

	function goInfoPage(_unitId) 
	{
		var items = [];
		
		if ( _unitId != null )
		{
			var result = [];
			var form = document.listForm;
			for ( var i = 0; i < form.length; i++) {
				if (form.elements[i].name == 'bindChk') {
					if (form.elements[i].attributes.unitId.value == _unitId )
					{
						result.push(form.elements[i]);
						break;
					}
				}
			}

			items = result;
		}
		else
		{
			items = getCheckedList();
		}
			
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.info'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.info'/>");
		} else {
			var compId = items[0].attributes.compId.value;
			var deptId = items[0].attributes.deptId.value;
			var unitId = items[0].attributes.unitId.value;
			var unitType = items[0].attributes.unitType.value;
			var bindCount = items[0].attributes.bindCount.value;
			
			var params = 'compId='+compId+'&deptId='+deptId+'&unitId='+unitId
							+'&unitType='+unitType+'&bindCount='+bindCount+'&allDocOrForm='+allDocOrForm;

			openWindow('bind_unit_info_win', '<%=webUri%>/app/bind/unit/ex/info.do?'+params , 720, 650, 'no');
		}
	}

	function goDel() {

		if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{
			var items = [];
			var result = [];
			var form = document.listForm;
			for ( var i = 0; i < form.length; i++) {
				if (form.elements[i].name == 'bindChk' && form.elements[i].checked) {
					if (form.elements[i].attributes.bindCount.value != '0' )
					{
						alert("<spring:message code='bind.obj.unit.name'/>: "+form.elements[i].attributes.unitName.value+"\r\n<spring:message code='bind.msg.bind.use.not.delete'/>");
						return;
					}
					result.push(form.elements[i]);
				}
			}

			items = result;
			var bRet = false;

			var itemCnt = items.length;
			for ( var i = 0; i < itemCnt; i++) {
				if (items[i].name == 'bindChk' && items[i].checked) {
					var compId = items[i].attributes.compId.value;
					var deptId = items[i].attributes.deptId.value;
					var unitId = items[i].attributes.unitId.value;
					//var resourceId = items[i].attributes.resourceId.value; 

					$.ajax({
						url : '<%=webUri%>/app/bind/unit/ex/remove.do',
						async: false,
						type : 'POST',
						dataType : 'json',
						data : {
							compId : compId,
							deptId : deptId,
							unitId : unitId
						},
						success : function(data) { 
							if(data.success) {
								bRet = true;
							} else {
								bRet = false;
							}
						},
						error : function(data) {
							bRet = false;
						}
					});
				}
			}
			// 다국어 추가
			//deleteResource(resourceId);
			if (bRet) {
				alert("<spring:message code='bind.msg.delete.completed'/>");
				onSubmit('DEL', itemCnt);
			} else {
				alert("<spring:message code='bind.msg.error'/>");
			}
			
		}
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
	
<% if(roleCode.indexOf(systemAdminCode) > -1 || roleCode.indexOf(appSystemAdminCode) > -1) { %>	
	function goSelectDept() {
		openType = 'ChangeDept';
		unitType = 'UTT002';
		
		var width = 500;
	    var height = 400;
	    	    
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
	}
	
	function goSelectComp() {
		unitType = 'UTT001';
		var form = document.listForm;
		$('#adminSelectDept').val('N');
		form.cPage.value = 1;
		form.searchType.value = "";
		form.searchValue.value = "";
		form.action = '<%=webUri%>/app/bind/unit/ex/list.do?deptId=' + '${compId}' + '&adminSelectDept=N';
		form.target = '_self';
		form.submit();
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				var form = document.listForm;
				$('#adminSelectDept').val('Y');
				form.cPage.value = 1;
				form.searchType.value = "";
				form.searchValue.value = "";
				form.action = '<%=webUri%>/app/bind/unit/ex/list.do?deptId=' + obj.orgId + '&adminSelectDept=Y';
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
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";
	
	window.onload = init;

--></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">


<acube:outerFrame>
	<form id="listForm" name="listForm" method='post' action='<%=webUri%>/app/bind/unit/ex/list.do'>
	<input name="cPage" id="cPage" type="hidden" value="${cPage}">
	<input name="deptId" id="deptId" type="hidden" value="${deptId}">
	<input name="totalCount" id="totalCount" type="hidden" value="${totalCount}">
	<input type="hidden" id="adminSelectDept" name="adminSelectDept" value=${adminSelectDept} />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.unit.list.ex" /></acube:titleBar>
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
					<% if(roleCode.indexOf(systemAdminCode) > -1 || roleCode.indexOf(appSystemAdminCode) > -1) { %>	
					<acube:menuButton id="select_comp" disabledid="" onclick="javascript:goSelectComp();" value='<%= m.getMessage("bind.code.UTT001", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="select_dept" disabledid="" onclick="javascript:goSelectDept();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<% } %>
					<acube:menuButton id="add" disabledid="" onclick="javascript:goAddPage();" value='<%= m.getMessage("bind.button.add", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="edit" disabledid="" onclick="javascript:goEditPage();" value='<%= m.getMessage("bind.button.edit", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="info" disabledid="" onclick="javascript:goInfoPage();" value='<%= m.getMessage("bind.button.info", null, langType) %>' />
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
		<!------ 검색 Table S -------->
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
		<!------ 검색 Table E -------->
		
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
									    acubeList.setColumnWidth("30,*,100,120,100,100");
									    acubeList.setColumnAlign("center,left,center,center,center,center");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.retention.period", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.applied", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.bind.count", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.doc.type", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;
									    
									    String style = "text-overflow : ellipsis; overflow : hidden;";
									    
									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } 
									    else {
											for (int i = 0; i < rows.size(); i++) {
											    BindUnitVO vo = rows.get(i);

											    row = acubeList.createRow();
											    rowIndex = 0;
											    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + vo.getUnitId()
											    		+ "\" compId=\"" + vo.getCompId() 
											    		+ "\" deptId=\"" + vo.getDeptId() 
											    		+ "\" unitId=\"" + vo.getUnitId() 
											    		+ "\" unitName=\"" + vo.getUnitName() 
											    		+ "\" unitType=\"" + vo.getUnitType() 
											    		+ "\" bindCount=\"" + vo.getBindCount() 
											    		+ "\" >");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
											    
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goInfoPage('" + vo.getUnitId() + "');\" style=\"" + style + "\">" + EscapeUtil.escapeHtmlDisp(vo.getUnitName()) + "</a>");
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, BindUtil.getDateFormat(StringUtils.rightPad(vo.getApplied(),14,'0'), dateFormat));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, vo.getBindCount());
											    row.setAttribute(rowIndex, "style", style);
												row.setData(++rowIndex, m.getMessage("bind.code." + vo.getUnitType(), null, langType));
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
