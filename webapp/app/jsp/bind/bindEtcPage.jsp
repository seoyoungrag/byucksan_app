<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    
    List<BindVO> rows = (List<BindVO>) request.getAttribute("rows");
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

	var objectWin;
	var openType = 'ChangeDept';
	var allDocOrForm = '${allDocOrForm}'; // 채번사용여부(1: 모든문서(안보이게), 2: 양식에서 선택(보이게)) 
	
	function init() {
		document.getElementById('search-value').style.display = '${type}' == 'docType' ? 'none' : 'block';
		document.getElementById('search-doc-type').style.display = '${type}' == 'docType' ? 'block' : 'none';

		var form = document.listForm;
		var typeValue = '${type}'||'bindName';
		form.searchYear.value = '${year}';
		form.searchType.value = typeValue;

		if(typeValue == '') {
		} else if(typeValue == 'docType') {
			form.docType.value = '${dType}';
		} else {
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

	function goAddPage() {
		var deptId = document.listForm.deptId.value;

		if(deptId == '') {
			alert("<spring:message code='bind.msg.select.dept.add.bind' />");
			return;
		} else {
			openWindow('bind_add_win', '<%=webUri%>/app/bind/add.do?targetId=' + deptId, 520, 520, 'no');
		}
	}

	function goEditPage() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.edit'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.edit'/>");
		} else {
			var sendType = items[0].attributes.sendType.value;
			
			if(sendType == 'DST003') { // 인계
				alert("<spring:message code='bind.msg.not.edit.transfer'/>");
				return;
			} else if(sendType == 'DST004') { // 공유
				alert("<spring:message code='bind.msg.not.edit.share'/>");
				return;
			} else {
				var targetId = items[0].attributes.deptId.value;
				var unitType = items[0].attributes.unitType.value;
				var bindId = items[0].value;
				
				openWindow('bind_edit_win', '<%=webUri%>/app/bind/edit.do?targetId=' + targetId + '&unitType=' + unitType + '&bindId=' + bindId, 520, 520, 'no');
			}
		}
	}
	
	function changeSearchType(value) {
		document.getElementById('search-value').style.display = value == 'docType' ? 'none' : 'block';
		document.getElementById('search-doc-type').style.display = value == 'docType' ? 'block' : 'none';
	}

	function changeSearchYear(value) {			
		var form = document.listForm;
		form.pageNo.value = 1;
		form.action = '<%=webUri%>/app/bind/list.do';
		form.target = '_self';
		form.submit();
	}

	function onSubmit() {
		var form = document.listForm;
		form.pageNo.value = 1;
		form.action = '<%=webUri%>/app/bind/list.do';
		form.target = '_self';
		form.submit();
	}

	function goBindInfoPage(unitId, unitType) {
		var params = '?unitId=' + unitId + '&unitType=' + unitType+'&allDocOrForm='+allDocOrForm;
		openWindow('bind_bindinfo_win', '<%=webUri%>/app/bind/unit/simple/info.do'+params, 400, 330, 'no');
	}

	function goInfoPage() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.info'/>");
		} else if(items.length > 1) { 
			alert("<spring:message code='bind.msg.select.onlyone.info'/>");
		} else {
			var deptId = items[0].attributes.deptId.value;
			var bindId = items[0].attributes.bindId.value;
			var unitType = items[0].attributes.unitType.value;

			openWindow('bind_info_win', '<%=webUri%>/app/bind/view.do?targetId=' + deptId + '&unitType=' + unitType + '&bindId=' + bindId, 470, 440, 'yes');
		}
	}

	function goDocumentPage(deptId, bindId) {
		openWindow('bind_doc_win', '<%=webUri%>/app/bind/document/list.do?targetId=' + deptId + '&bindId=' + bindId, 800, 500, 'no');
	}
	
	function goTransferPage() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.transfer'/>");
		} else if(items.length > 1) {
			alert("<spring:message code='bind.msg.select.onlyone.transfer'/>");
		} else {
			var sendType = items[0].attributes.sendType.value;
			var shareCnt = items[0].attributes.shareCount.value;
			if(sendType == 'DST003') { // 인계한 편철
				alert("<spring:message code='bind.msg.already.transfer'/>");
				return;
			} else if(sendType == 'DST004') { // 공유받은 편철
				alert("<spring:message code='bind.msg.not.transfer.from.share'/>");
				return;
			} else if(shareCnt > 0) { // 공유한 편철
				alert("<spring:message code='bind.msg.not.transfer.to.share'/>");
				return;
			} else {
				var deptId = items[0].attributes.deptId.value;
				var bindId = items[0].value;
				var count = items[0].attributes.count.value;
				var binding = items[0].attributes.binding.value;
				var unitType = items[0].attributes.unitType.value;
				
				if(count == 0) {
					alert("<spring:message code='bind.msg.document.not.exist.not.transfer'/>");
					return;
				} else if(binding != 'Y') {
					alert("<spring:message code='bind.msg.not.transfer.binding'/>");
					return;
				}

				$.ajax({
					url : '<%=webUri%>/app/bind/getBindCount.do',
					dataType : 'json',
					data: { 
						bindId : bindId						
					},
					
					success: function(data) {
						if(data.success) {
							var count = data.bindCount;
							if(count > 0) {
								var msg = "<spring:message code='bind.msg.document.count.process' arguments='" + count + "'/>\n<spring:message code='bind.msg.confirm.document.view'/>";
								if(confirm(msg)) {
									openWindow('bind_doc_win', '<%=webUri%>/app/list/etc/ListAppIngBind.do?bindingId=' + bindId, 800, 480, 'yes');
								}								
							} else {
								openWindow('bind_trans_win', '<%=webUri%>/app/bind/transfer.do?targetId=' + deptId + '&unitType=' + unitType + '&bindId=' + bindId, 400, 490, 'yes');
							}
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					},
					
					error: function(data) {
						alert("<spring:message code='bind.msg.error'/>");
					}
				});
			}
		}
	}

	function goSharePage() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.share'/>");
		} else {
			var targetIds = '';
			var deptIds = '';
			
			for(var i = 0; i < items.length; i++) {
				targetIds += (i == 0 ? '' : ',') + items[i].value;
				deptIds += (i == 0 ? '' : ',') + items[i].attributes.deptId.value;
				
				var count = items[i].attributes.count.value;
				var sendType = items[i].attributes.sendType.value;
				var deptId = items[i].attributes.deptId.value;
				var unitType = items[i].attributes.unitType.value;
				
				if(count == 0) {
					alert("<spring:message code='bind.msg.document.not.exist.not.share'/>");
					return;
				} else if(sendType == 'DST003') { // 인계
					alert("<spring:message code='bind.msg.not.share.transfer'/>");
					return;
				} else if(sendType == 'DST004') { // 공유
					alert("<spring:message code='bind.msg.not.share.to.share'/>");
					return;
				} else if(unitType == 'UTT002') { // 부서고유
					alert("<spring:message code='bind.msg.document.not.share.UTT002'/>");
					return;
				}
			}

			openType = 'SelectDept';
			
			var width = 350;
		    var height = 300;
		    
		    var top = (screen.availHeight - 560) / 2;
		    var left = (screen.availWidth - 800) / 2;

			openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2&confirmYn=N&msgType=share', width, height, 'no');
		}
	}

	function goJobPage() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.bind'/>");
		} else if(items.length > 1) {
			alert("<spring:message code='bind.msg.select.onlyone.job'/>");			
		} else {
			var bindId = items[0].value;
			var deptId = items[0].attributes.deptId.value;
			var count = items[0].attributes.count.value;
			var sendType = items[0].attributes.sendType.value;
			
			if(count == 0) {
				alert("<spring:message code='bind.msg.document.not.exist.not.job'/>");
				return;
			} else if(sendType == 'DST002') { // 인수
				alert("<spring:message code='bind.msg.not.job.receive'/>");
				return;
			} else if(sendType == 'DST003') { // 인계
				alert("<spring:message code='bind.msg.not.job.transfer'/>");
				return;
			} else if(sendType == 'DST004') { // 공유
				alert("<spring:message code='bind.msg.not.job.share'/>");
				return;
			}

			openWindow('bind_job_win', '<%=webUri%>/app/bind/job.do?bindId=' + bindId + '&deptId=' + deptId, 400, 230, 'yes');
		}
	}
	
	function goObsoletePage() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.obsolete'/>");
		} else {
			var bindIds = '';
			var deptIds = '';
			var unitTypes = '';
			var resourceId = '';
			
			for(var i = 0; i < items.length; i++) {
				if(items[i].attributes.sendType.value == 'DST003') {
					alert("<spring:message code='bind.msg.transfer.not.remove' />\n\n- " + items[i].attributes.bindName.value);
					return;
				} else if(items[i].attributes.sendType.value == 'DST004') {
					alert("<spring:message code='bind.msg.not.obsolete.from.share' />\n\n- " + items[i].attributes.bindName.value);
					return;
				} else if(items[i].attributes.count.value > 0) {
					alert("<spring:message code='bind.msg.document.exist.not.obsolete' />\n\n- " + items[i].attributes.bindName.value);
					return;
				} else if(items[i].attributes.shareCount.value > 0) {
					alert("<spring:message code='bind.msg.not.obsolete.to.share' />\n\n- " + items[i].attributes.bindName.value);
					return;
				} else {
					bindIds += ',' + items[i].attributes.bindId.value;
					deptIds += ',' + items[i].attributes.deptId.value;
					unitTypes += ',' + items[i].attributes.unitType.value;
					
					resourceId = items[i].attributes.resourceId.value;
				}
			}

			if(confirm("<spring:message code='bind.msg.confirm.obsolete'/>")) {
				var form = document.listForm;
				
				$.ajax({
					url : '<%=webUri%>/app/bind/obsolete.do',
					dataType : 'json',
					data: { 
						bindIds : bindIds.substring(1),
						deptIds : deptIds.substring(1),						
						unitTypes : unitTypes.substring(1)						
					},
					
					success: function(data) {
						if(data.success) {
							// 다국어 추가
							deleteResource(resourceId);
							
							document.listForm.action = '<%=webUri%>/app/bind/list.do';
							document.listForm.submit();
							
							alert("<spring:message code='bind.msg.completed'/>");
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					},
					
					error: function(data) {
						alert("<spring:message code='bind.msg.error'/>");
					}
				});
			}
		}
	}

	function goPrintPage() {
		var form = document.listForm;
		var searchYear = form.searchYear.value;
		var searchType = form.searchType.value;
		var searchValue = form.searchValue.value;
		var docType = form.docType.value;
		var deptId = form.deptId.value;

		var param = 'deptId=' + deptId + '&searchYear=' + searchYear + '&searchType=' + searchType + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue)) + '&docType=' + docType;

		var url = webUri + "/app/bind/print.do";
		printBox(url, param);
	}
	
	function goExcelPage() {
		var form = document.listForm;
		form.action = '<%=webUri%>/app/common/excel/bind.do';
		form.target = '_self';
		form.submit();
	}
	
<% if(roleCode.indexOf(sysRoleCode) > -1 || roleCode.indexOf(docMngRoleCode) > -1) { %>	
	function goSelectDept() {
		openType = 'ChangeDept';
		
		var width = 500;
	    var height = 400;
	    	    
		// openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2', width, height, 'no');
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				var form = document.listForm;
				form.deptId.value = obj.orgId;
				form.deptName.value = obj.orgName;
				form.pageNo.value = 1;
				form.action = '<%=webUri%>/app/bind/list.do';
				form.target = '_self';
				form.submit();
			} else if('SelectDept' == openType) {
				var items = getCheckedList();
				var targetIds = '';
				var deptIds = '';
					
				for(var i = 0; i < items.length; i++) {
					targetIds += (i == 0 ? '' : ',') + items[i].value;
					deptIds += (i == 0 ? '' : ',') + items[i].attributes.deptId.value;

					var count = items[i].attributes.count.value;
					var sendType = items[i].attributes.sendType.value;
					var deptId = items[i].attributes.deptId.value;
					if(deptId == obj.orgId) {
						alert("<spring:message code='bind.msg.not.share.same.dept'/>");
						return;
					} else if(count == 0) {
						alert("<spring:message code='bind.msg.document.not.exist.not.share'/>");
						return;
					} else if(sendType == 'DST003') { // 인계
						alert("<spring:message code='bind.msg.not.share.transfer'/>");
						return;
					}
				}

				$.ajax({
					url : '<%=webUri%>/app/bind/share.do',
					dataType : 'json',
					data: {
						deptId : obj.orgId,
						targetIds : targetIds,
						deptIds : deptIds
					},						
					success: function(data) {
						if(data.success) {
							alert("<spring:message code='bind.msg.completed'/>");
							
							document.listForm.action = '<%=webUri%>/app/bind/list.do';
							document.listForm.submit();
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					},						
					error: function(data) {
						alert("<spring:message code='bind.msg.error'/>");
					}
				});
			}
		}
	}
<% } %>	

	function goBatchPage() {
		openWindow('bind_batch_win', '<%=webUri%>/app/bind/batch/list.do', 400, 450, 'no');
	}

	function goOrderPage() {
		openWindow('bind_order_win', '<%=webUri%>/app/bind/order.do?deptId=${deptId}', 500, 420, 'no');
	}

	function goAutoCreatePage() {
		if(confirm("<spring:message code='bind.msg.confirm.auto.create'/>")) {
			$.ajax({
				url : '<%=webUri%>/app/bind/autoCreate.do',
				dataType : 'json',
				data: {
					deptId : '${deptId}'
				},						
				success: function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.completed'/>");
						
						document.listForm.action = '<%=webUri%>/app/bind/list.do';
						document.listForm.submit();
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},						
				error: function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	function changePage(pageNo) {
		var form = document.listForm;
		form.pageNo.value = pageNo;
		form.action = '<%=webUri%>/app/bind/list.do';
		form.target = '_self';
		form.submit();
	}

	var webUri = '<%=webUri%>';
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";
	
	window.onload = init;

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/list.do'>
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="deptId" value="${deptId}" />
	<input type="hidden" name="deptName" value="${deptName}" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	<input type="hidden" name="unitId" />
	<input type="hidden" name="deptIds" />
	<input type="hidden" name="targetId" />
	<input type="hidden" name="targetIds" />
	<input type="hidden" name="type" />
	<input type="hidden" name="treetype" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.etc" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="title_mini" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td>
			<td nowrap="nowrap">
				<acube:buttonGroup align="right">
					<% if(roleCode.indexOf(sysRoleCode) > -1) { %>
					<acube:menuButton id="select_dept" disabledid="" onclick="javascript:goSelectDept();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
					<%--<acube:space between="button" />
					<acube:menuButton id="bind_create" disabledid="" onclick="javascript:goBatchPage();" value='<%= m.getMessage("bind.button.batch.create", null, langType) %>' />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />--%>
					<% } %>
					<% if(rows.size() == -1) { //편철없을때 생성할수 있게 수정 jth8172 20120404%>
					<% // if(rows.size() ==  %>
					<%-- <acube:menuButton id="auto_create" disabledid="" onclick="javascript:goAutoCreatePage();" value='<%= m.getMessage("bind.button.auto.create", null, langType) %>' /> --%>
					<% } else { %>
					<%-- <acube:menuButton id="transfer" disabledid="" onclick="javascript:goTransferPage();" value='<%= m.getMessage("bind.button.transfer", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="transfer" disabledid="" onclick="javascript:goSharePage();" value='<%= m.getMessage("bind.button.share", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="bind_job" disabledid="" onclick="javascript:goJobPage();" value='<%= m.getMessage("bind.button.job", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="obsolete" disabledid="" onclick="javascript:goObsoletePage();" value='<%= m.getMessage("bind.button.obsolete", null, langType) %>' /> --%>
					<% } %>
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
			<td>
				<table class="tablesearchnp" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<acube:space between="search" />
							<td style="padding:5px 7px;">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="50">
											<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
										</td>
										<td width="5"></td>
										<td width="50"><form:select id="searchType" name="searchType" path="searchType" onchange="javascript:changeSearchType(this.value);" items="${searchType}" /></td>
										<td width="5"></td>
										<td width="200">
											<div id="search-value" style="display: block; width: 100%;">
												<input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%">
											</div>
											<div id="search-doc-type" style="display: none; width: 100%;">
												<form:select id="docType" name="docType" path="docType" items="${docType}" style="width: 100%" />
											</div>
										</td>
										<td width="5"></td>
										<td>
											<!-- <acube:button onclick="javascript:onSubmit();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" /> -->
											<a href="#" onclick="javascript:onSubmit();"><img src="<%=webUri%>/app/ref/image/bt_search.gif" alt="검색" /></a>
										</td>
									</tr>
								</table>
							</td>
						<acube:space between="search" />
					</tr>
				</table>
			</td>
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
									    acubeList = new AcubeList(sLine, 8);
									    acubeList.setColumnWidth("30,90,200,*,60,90,70,100");
									    acubeList.setColumnAlign("center,center,left,left,center,center,center,center");

									    AcubeListRow titleRow = acubeList.createTitleRow();
									    int rowIndex = 0;
									    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.type", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.name", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.count", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.type", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.retention.period", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
									    titleRow.setData(++rowIndex, m.getMessage("bind.obj.remark", null, langType));
									    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");

									    AcubeListRow row = null;

									    
									    if (rows.size() == 0) {
											row = acubeList.createDataNotFoundRow();
											row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
									    } else {
											for (int i = 0; i < rows.size(); i++) {
											    BindVO vo = rows.get(i);
											    
											    if(i == 0) totalCount = vo.getTotalCount();
	
											    String style = "text-overflow : ellipsis; overflow : hidden;";
											    if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
													style = style + " text-decoration : line-through;";
											    }
	
											    row = acubeList.createRow();
											    rowIndex = 0;
											    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" value=\"" + vo.getBindId() + "\" sendType=\"" + vo.getSendType() + "\"  arrange=\"" + vo.getArrange() + "\" binding=\"" + vo.getBinding() + "\" count=\"" + vo.getDocCount() + "\" shareCount=\"" + vo.getShareCount() + "\" bindId=\"" + vo.getBindId() + "\" deptId=\"" + vo.getDeptId() + "\" unitType=\"" + vo.getUnitType() + "\" bindName=\"" + vo.getBindName() + "\" resourceId=\"" + vo.getResourceId() + "\" >");
											    row.setAttribute(rowIndex, "class", "ltb_check");
											    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
											    
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getUnitType(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getUnitType(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goBindInfoPage('" + vo.getUnitId() + "', '" + vo.getUnitType() + "');\" style=\"" + style + "\">" + EscapeUtil.escapeHtmlDisp(vo.getUnitName()) + "</a>");
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getUnitName()));
											    row.setAttribute(rowIndex, "style", style);
											    
											    if(BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType()) || vo.getDocCount() == 0) {
													row.setData(++rowIndex, EscapeUtil.escapeHtmlDisp(vo.getBindName()));
											    } else {
													row.setData(++rowIndex, "<a href='#' onClick=\"javascript:goDocumentPage('" + vo.getDeptId() + "', '" + vo.getBindId() + "');\" style=\"" + style + "\">" + EscapeUtil.escapeHtmlDisp(vo.getBindName()) + "</a>");
											    }											    
											    row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getBindName()));
											    row.setAttribute(rowIndex, "style", style);
											    
											    row.setData(++rowIndex, m.getMessage("bind.obj.doc.docunt", new Integer[] { vo.getDocCount() }, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.obj.doc.docunt", new Integer[] { vo.getDocCount() }, langType));
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getDocType(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getDocType(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
											    row.setData(++rowIndex, m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
											    row.setAttribute(rowIndex, "title", m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
											    row.setAttribute(rowIndex, "style", style);
	
											    String arrange = vo.getArrange();
											    String binding = vo.getBinding();
											    
											    String remark = "";
											    if (BindConstants.Y.equals(binding)) {
													remark = m.getMessage("bind.obj.confirmation", null, langType);
											    } else if (BindConstants.Y.equals(arrange)) {
													remark = m.getMessage("bind.obj.ok", null, langType);
											    }
	
											    if (BindConstants.SEND_TYPES.DST001.name().equals(vo.getSendType())) {
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else if (BindConstants.SEND_TYPES.DST002.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST002", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST002", null, langType);
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST003", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST003", null, langType);
		
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden; color : #FF0000;");
											    } else if (BindConstants.SEND_TYPES.DST004.name().equals(vo.getSendType())) {
													if (StringUtils.isNotEmpty(remark)) {
													    remark = m.getMessage("bind.code.DST004", null, langType) + " / " + remark;
													} else {
													    remark = m.getMessage("bind.code.DST004", null, langType);
		
													}
													row.setData(++rowIndex, remark);
													row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
											    } else {
													row.setData(++rowIndex, "");
											    }
											    
											    row.setAttribute(rowIndex, "title", remark);
											}
									    }

									    //acubeList.generatePageNavigator(false);
									    //acubeList.generate(out);
									    
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