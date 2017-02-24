<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;

	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
	String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
	
    String dateFormat = AppConfig.getProperty("std_date_format", "yyyy-MM-dd", "date");
	
    String adminSelectDept = request.getParameter("adminSelectDept");
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
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<script LANGUAGE="JavaScript">

	var openType = 'ChangeDept';
	var allDocOrForm = '${allDocOrForm}'; // 채번사용여부(1: 모든문서(안보이게), 2: 양식에서 선택(보이게)) 
	var unitType = '${unitType}';
	var compId = '${compId}';
	var deptId = '${deptId}';
	var selectUnitId = 'ROOT';
	var selectParentUnitId = 'ROOT';
	var selectUnitDepth = 0;
	var selectUnitOrder;
	var selectIsChildUnit;

	function goDel() {
		if(selectIsChildUnit=='Y'){
			alert("<spring:message code='bind.msg.error.child'/>");
		}
		else if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{
			var unitId = selectUnitId;

			$.ajax({
				url : '<%=webUri%>/app/bind/unit/simple/remove.do',
				type : 'POST',
				dataType : 'json',
				data : {
					compId : compId,
					deptId : deptId,
					unitId : unitId
				},
				success : function(data) { 
					if(data.success) {
						// continue
						selectUnitId = selectParentUnitId;
						fncUnitTreeRefresh();
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
			
			alert("<spring:message code='bind.msg.delete.completed'/>");
			/* var f = document.listForm;
			f.method = 'POST';
			f.submit(); */
		}
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
		form.action = '<%=webUri%>/app/bind/unit/simple/unitManager.do?deptId=' + '${compId}' + '&adminSelectDept=N';
		form.target = '_self';
		form.submit();
		selectUnitId = 'ROOT';
		
		<%-- unitType = 'UTT001';
		var params = 'deptId=' + '${compId}' + '&adminSelectDept=N';

		var url = '<%=webUri%>/app/bind/unit/simple/unitList.do?'+params;
	    $("#unit_tree").load(url, "");
	    selectUnitId = 'ROOT'; --%>
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				var form = document.listForm;
				$('#adminSelectDept').val('Y');
				form.action = '<%=webUri%>/app/bind/unit/simple/unitManager.do?deptId=' + obj.orgId + '&adminSelectDept=Y';
				form.target = '_self';
				form.submit();
				selectUnitId = 'ROOT';
				<%-- var params = 'deptId=' + obj.orgId + '&adminSelectDept=Y';

				var url = '<%=webUri%>/app/bind/unit/simple/unitList.do?'+params;
			    $("#unit_tree").load(url, "");
			    selectUnitId = 'ROOT'; --%>
			} 
		}
	}
<% } %>	
	function fncUnitTreeRefresh() {
		var param;
		if(unitType == 'UTT001') {
			params = 'deptId=' + '${compId}' + '&adminSelectDept=N';
		}
		else {
			params = 'deptId=' + '${deptId}' + '&adminSelectDept=Y';
		}
		
		var url = '<%=webUri%>/app/bind/unit/simple/unitList.do?'+params;
		$.ajaxSetup ({
		    cache: false
		});
		
	    $("#unit_tree").load(url, "");
	    /* selectUnitId = 'ROOT'; */
	}

	fncSetUnitInfo = function(unitId, unitOrder, unitDepth, parentId, isChildUnit) {
		selectParentUnitId = parentId;
		selectUnitId = unitId;
		selectUnitOrder = unitOrder;
		selectUnitDepth = unitDepth;
		selectIsChildUnit = isChildUnit;
	}
	
	fncEditReload = function(unitId) {
		
		var params = 'compId='+compId+'&deptId='+deptId+'&unitId='+unitId
		+'&unitType='+unitType+'&allDocOrForm='+allDocOrForm;
	    var url = '<%=webUri%>/app/bind/unit/simple/edit.do?'+params;
	    
		$.ajaxSetup ({
		    cache: false
		});
		
	    $("#unit_detail").load(url, "");
	}
	
	fncAddReload = function() { 
		var unitDepth = parseInt(selectUnitDepth)+1; 
		var params = 'unitType='+unitType+'&deptId='+deptId+'&allDocOrForm='+allDocOrForm
						+'&unitDepth='+unitDepth+'&unitId='+selectUnitId;
	    var url = '<%=webUri%>/app/bind/unit/simple/add.do?'+params;
		$.ajaxSetup ({
		    cache: false
		});
		
	    $("#unit_detail").load(url, "");
	}

	fncUnitTreeReload = function() {
	    <%-- var url = '<%=webUri%>/app/bind/unit/simple/unitList.do';
	    $("#unit_tree").load(url, ""); --%>
	    fncUnitTreeRefresh();
	}

	fncUnitAddCallback = function(unitId, unitName) {
		//fncUnitTreeAdd(unitId, unitName);
		selectUnitId = unitId;
		alert("<spring:message code='bind.msg.add.completed'/>");
		fncUnitTreeRefresh();
	}
	
	fncUnitUpdateCallback = function(unitId, unitName) {
		//fncUnitUpdate(unitId, unitName);
		alert("<spring:message code='bind.msg.edit.completed'/>");
		fncUnitTreeRefresh();
	}
	
	var openCallback = function(openNodeId) {	
		//getSubTree(openNodeId);
	};
	
	<%-- function getSubTree(nodeId) {
		var result = null;
		var compId = "<%=compId%>";

		var depth = $('#depth').val();
		$.ajax({
			url : '<%=webUri%>/app/bind/unit/simple/create.do',
			type : 'POST',
			dataType : 'json',
			data : {
				unitName : unitName,
				unitType : unitType,
				deptId : deptId,
				description : description,
				serialNumber : serialNumber,
				retentionPeriod : retentionPeriod,
				applied : applied,
				parentUnitId : parentUnitId,
				unitDepth : unitDepth,
				unitOrder : unitOrder
			},
			success : function(data) {
				if(data.success) {
					// 다국어 저장
					drawSubTree(openNodeId, openNodeObj);

					$('#'+openNodeId+' li').removeClass('leaf');
					$('#'+openNodeId+' li').addClass('closed');
				} else {
					alert("<spring:message code='bind.msg.error'/>");
				}
			},
			error : function(data) {
				alert("<spring:message code='bind.msg.error'/>");
			}
		});
		return result;
	} --%>

	// 콜백함수 정의
	var selectUnitCallback = function(unitId, unitName, unitType, unitOrder, unitDepth, parentId, isChildUnit) {
		if(unitId!="ROOT"){
			fncSetUnitInfo(unitId, unitOrder, unitDepth, parentId, isChildUnit);
			try{
				fncEditReload(unitId)
	    	} catch(e) { }
		}
		else fncSetUnitInfo(unitId, 0, 0, null, 'Y');
	};
	
	var webUri = '<%=webUri%>';
	
	//window.onload = init;
	
    $(document).ready(function() {
		fncUnitTreeReload();
    });
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/unit/simple/unitManager.do'>
	<input type="hidden" id="adminSelectDept" name="adminSelectDept" value=${adminSelectDept} />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.unit.manage" /></acube:titleBar>
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
					<%-- <acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:space between="button" /> --%>
					<% } %>
					<acube:menuButton id="add" disabledid="" onclick="javascript:fncAddReload();" value='<%= m.getMessage("bind.button.add", null, langType) %>' />
					<acube:space between="button" />
					<%-- <acube:menuButton id="edit" disabledid="" onclick="javascript:goEditPage();" value='<%= m.getMessage("bind.button.edit", null, langType) %>' />
					<acube:space between="button" /> --%>
					<%-- <acube:menuButton id="info" disabledid="" onclick="javascript:goInfoPage();" value='<%= m.getMessage("bind.button.info", null, langType) %>' />
					<acube:space between="button" /> --%>
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
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td colspan="2">
				<div>
					<!-- part -->
					<table width="100%" border="0" cellSpacing="0" cellPadding="0">
						<tr>
							<td width="35%" valign="top" style="height: 18px;">
								<table width="100%" border="0" cellSpacing="0" cellPadding="0">
									<td width="16" height="18" valign="top">
										<img src="<%=webUri%>/app/ref/image/title3.gif" width="16" height="16"></td>
									<td class="file_title"><spring:message code="bind.obj.unit"/></td>
								</table>
							</td>
							<td width="1%" valign="top"></td>
							<td width="64%" valign="top" style="height: 18px;">
								<table width="100%" border="0" cellSpacing="0" cellPadding="0">
									<td width="16" height="18" valign="top">
										<img src="<%=webUri%>/app/ref/image/title3.gif" width="16" height="16"></td>
									<td class="file_title"><spring:message code="bind.obj.unit.info" /></td>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div>
					<!-- part -->
					<table width="100%" border="0" cellSpacing="0" cellPadding="0">
						<tr>
							<td width="35%" valign="top">
								<div id="unit_tree" class="table_border"
									style="height: 500px;"></div>
							</td>
							<td width="1%" valign="top"></td>
							<td width="64%" valign="top">
								<div id="unit_detail" class="table_border"
									style="height: 500px;"></div>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</body>
</html>