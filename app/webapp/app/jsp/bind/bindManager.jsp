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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<script LANGUAGE="JavaScript">

	var objectWin;
	var openType = 'ChangeDept';
	var compId = '${compId}';
	var deptId = '${deptId}';
	var allDocOrForm = '${allDocOrForm}'; // 채번사용여부(1: 모든문서(안보이게), 2: 양식에서 선택(보이게))
	var selectBindId = 'ROOT';
	var selectParentBindId = 'ROOT';
	var selectBindDepth;
	var selectBindOrder;
	var selectIsChildBind;
	var selectUnitType = 'UTT001';
	var selectSendType = '';
	var selectDocCount = 0;
	var isTransfer = 'N';
	var origYear = '${year}';
	var orgDeptId = '${deptId}';
	
	
	function init() {
		var form = document.listForm;
		var typeValue = '${type}'||'bindName';
		form.searchYear.value = '${year}';
	}

	function changeSearchYear(value) {
		if(origYear==value)
			buttonToggle('Y');
		else
			buttonToggle('N');
		selectBindId = 'ROOT';
		fncBindTreeRefresh();
	}

	function onSubmit() {
		var form = document.listForm;
		/* form.pageNo.value = 1; */
		form.action = '<%=webUri%>/app/bind/bindManager.do';
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

	function buttonToggle(isShow){
		if(isShow!='Y'){		
			$("#add").hide();
			$("#transfer").hide();
			$("#share").hide();
			$("#bind_job").hide();
			$("#obsolete").hide();
			$("#revocate").hide();
		}
		else {
			$("#add").hide();
			$("#transfer").show();
			$("#share").show();
			$("#bind_job").show();
			$("#obsolete").show();
			$("#revocate").show();
		}
	}
	
	//부서id 셋팅
	function setDeptInfo(obj) {
		
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				<%-- var form = document.listForm;
				form.deptId.value = obj.orgId;
				form.deptName.value = obj.orgName;
				form.action = '<%=webUri%>/app/bind/bindManager.do?deptId='+obj.orgId+'&orgDeptId='+orgDeptId;
				form.target = '_self';
				form.submit(); --%>
				var tag = "<img src='<%=webUri%>/app/ref/image/dot_search9.gif'/><b>" + obj.orgName + "</b>";
				$('#deptName').html(tag);
				
				if(orgDeptId==obj.orgId)
					buttonToggle('Y');
				else
					buttonToggle('N');
				
				deptId = obj.orgId;
				selectBindId = 'ROOT';
				fncBindTreeRefresh();
			} else if('SelectDept' == openType) {
				var targetIds = selectBindId;
				var deptIds = deptId;

				if(deptId == obj.orgId) {
					
					if(isTransfer=='Y'){
						alert("<spring:message code='bind.msg.not.transfer.same.dept'/>");
					}
					else{
						alert("<spring:message code='bind.msg.not.share.same.dept'/>");
					}
					return;
				}
				
				var url = '';
				if(isTransfer=='Y'){
					url = '<%=webUri%>/app/bind/bindTranspose.do';
				}
				else{
					url = '<%=webUri%>/app/bind/bindShare.do';
				}
				
				$.ajax({
					url : url,
					dataType : 'json',
					data: {
						deptId : obj.orgId,
						targetIds : targetIds,
						deptIds : deptIds
					},						
					success: function(data) {
						if(data.success) {
							alert("<spring:message code='bind.msg.completed'/>");
							selectBindId = selectParentBindId;
							fncBindTreeRefresh();
						} else {
							if(data.msg!=null&&data.msg!="")
								alert(data.msg);
							else
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
					deptId : deptId
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

	<%-- function changePage(pageNo) {
		var form = document.listForm;
		form.pageNo.value = pageNo;
		form.action = '<%=webUri%>/app/bind/list.do';
		form.target = '_self';
		form.submit();
	} --%>

	function fncBindTreeRefresh() {
		var param;
		var searchYear = $('#searchYear').val();
		params = 'deptId=' + deptId + '&bindType=ALL' + '&searchYear=' + searchYear;
		var url = '<%=webUri%>/app/bind/bindList.do?'+params;
		$.ajaxSetup ({
		    cache: false
		});
		
	    $("#bind_tree").load(url, "");
	    
	    if(selectBindId=="ROOT"||selectBindId=="DEPT"||selectBindId=="SHARE"){
	    	$("#bind_detail").html("");
	    }
	}
	
	fncSetBindInfo = function(bindId, bindOrder, bindDepth, parentId, isChildBind, unitType, sendType, docCount) {
		selectParentBindId = parentId;
		selectBindId = bindId;
		selectBindOrder = bindOrder;
		selectBindDepth = bindDepth;
		selectIsChildBind = isChildBind;
		selectUnitType = unitType;
		selectSendType = sendType;
		selectDocCount = docCount
	}
	
	fncBindTreeReload = function() {
		var param = 'bindType=ALL';
	    var url = '<%=webUri%>/app/bind/bindList.do'+param;
	    $("#bind_tree").load(url, "");
	}
	
	fncEditReload = function(bindId) {
		var params = 'compId='+compId+'&deptId='+deptId+'&bindId='+bindId
		+'&unitType='+selectUnitType+'&allDocOrForm='+allDocOrForm+'&selectSendType='+selectSendType;
	    var url = '<%=webUri%>/app/bind/edit.do?'+params;
	    $("#bind_detail").load(url, "");
	}
	
	fncAddReload = function() {
		var bindId = "";
		if(selectBindId=="ROOT"||selectBindId=="DEPT"){
			bindId = "ROOT";
		}
		else {
			bindId = selectBindId;
		}
			
		if(selectSendType=='DST004'||selectBindId=="SHARE"){
			alert('공유 캐비닛에서는 하위 캐비닛을 생성 할 수 없습니다.');
			return;
		}
		var bindDepth = parseInt(selectBindDepth)+1;
		var params = 'targetId='+deptId+'&bindDepth='+bindDepth+'&bindId='+bindId;;
	    var url = '<%=webUri%>/app/bind/add.do?'+params;
	    $("#bind_detail").load(url, "");
	}
	
	// 콜백함수 정의
	var selectBindCallback = function(bindId, bindOrder, bindDepth, parentId, isChildBind, unitType, sendType, docCount) {
		if(bindId!="ROOT"&&bindId!="DEPT"&&bindId!="SHARE"){
			fncSetBindInfo(bindId, bindOrder, bindDepth, parentId, isChildBind, unitType, sendType, docCount);
			try{
				fncEditReload(bindId)
	    	} catch(e) { }
		}
		else fncSetBindInfo(bindId, 0, 0, null, 'Y', "", "", 0);
	};
	
	fncBindAddCallback = function(bindId, bindName) {
		//fncUnitTreeAdd(unitId, unitName);
		selectBindId = bindId;
		alert("<spring:message code='bind.msg.add.completed'/>");
		fncBindTreeRefresh();
	}
	
	fncBindUpdateCallback = function(bindId, bindName) {
		//fncBindUpdate(bindId, bindName);
		alert("<spring:message code='bind.msg.edit.completed'/>");
		fncBindTreeRefresh();
	}
	
	var openCallback = function(openNodeId) {	
		//getSubTree(openNodeId);
	};

	function goTransferPage() {
		if(selectBindId=="ROOT"){
			alert("최 상위 캐비닛에서는 인계를 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="DEPT"){
			alert("부서 캐비닛에서는 인계를 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="SHARE"||selectSendType=="DST004"){
			alert("공유 캐비닛에서는 인계를 할 수 없습니다.");
			return;
		}
		if(selectParentBindId!="ROOT"){
			alert("하위 캐비닛에서는 인계를 할 수 없습니다. 상위 캐비닛에서 인계 하시길 바랍니다.");
			return;
		}
		openType = 'SelectDept';
		isTransfer = 'Y';
		
		var width = 350;
	    var height = 300;
	    
	    var top = (screen.availHeight - 560) / 2;
	    var left = (screen.availWidth - 800) / 2;

		openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2&confirmYn=N&msgType=transpose', width, height, 'no');
	}

	function goSharePage() {
		if(selectBindId=="ROOT"){
			alert("최 상위 캐비닛에서는 공유를 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="DEPT"){
			alert("부서 캐비닛에서는 공유를 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="SHARE" || selectSendType=="DST004"){
			alert("공유 캐비닛에서는 공유를 할 수 없습니다.");
			return;
		}
		
		if(selectParentBindId!="ROOT"){
			alert("하위 캐비닛에서는 공유를 할 수 없습니다. 상위 캐비닛에서 공유 하시길 바랍니다.");
			return;
		}
		
		openType = 'SelectDept';
		isTransfer = 'N';
		
		var width = 350;
	    var height = 300;
	    
	    var top = (screen.availHeight - 560) / 2;
	    var left = (screen.availWidth - 800) / 2;

		openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2&confirmYn=N&msgType=share', width, height, 'no');
	}
	
	function goJobPage() {
		if(selectBindId=="ROOT"){
			alert("최 상위 캐비닛에서는 캐비닛 업무를 진행 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="DEPT"){
			alert("부서 캐비닛에서는 캐비닛 업무를 진행 할 수 없습니다.");
			return;
		}
		else if(selectBindId=="SHARE"||selectSendType=="DST004"){
			alert("공유 캐비닛에서는 캐비닛 업무를 진행 할 수 없습니다.");
			return;
		}
		
		var bindId = selectBindId;
		var count = selectDocCount;
		var sendType = selectSendType;
		
		/* if(count == 0) {
			alert("<spring:message code='bind.msg.document.not.exist.not.job'/>");
			return;
		} else  if(sendType == 'DST002') { // 인수
			alert("<spring:message code='bind.msg.not.job.receive'/>");
			return;
		} else if(sendType == 'DST003') { // 인계
			alert("<spring:message code='bind.msg.not.job.transfer'/>");
			return;
		} else if(sendType == 'DST004') { // 공유
			alert("<spring:message code='bind.msg.not.job.share'/>");
			return;
		}
		*/
		openWindow('bind_job_win', '<%=webUri%>/app/bind/bindJob.do?bindId=' + bindId + '&deptId=' + deptId, 400, 230, 'yes');
	}
	
	function goRevocatePage() {
		if(selectBindId=="ROOT"||selectBindId=="DEPT"||selectBindId=="SHARE"){
			alert("최 상위 캐비닛에서는공유 해지 할 수 없습니다.");
			return;
		}
		else if(selectSendType!='DST004'){
			alert("공유 캐비닛 만 해지 할 수 있습니다.");
			return;	
		}
		
		var confirmMessage = "";

		if(selectIsChildBind!='Y'){
			confirmMessage = "<spring:message code='bind.msg.confirm.share.revocate'/>";
		}
		else{
			confirmMessage = "공유해지 시 하위 캐비닛까지 해지 됩니다. 해지 하시겠습니까?";
		}
		
		if(confirm(confirmMessage)) {
			var form = document.listForm;
			var url = '<%=webUri%>/app/bind/bindRevocate.do';

			$.ajax({
				url : url,
				dataType : 'json',
				data: { 
					bindIds : selectBindId,
					deptIds : deptId,						
					unitTypes : selectUnitType						
				},
				
				success: function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.completed'/>");
						selectBindId = selectParentBindId;
						fncBindTreeRefresh();
					} else {
						if(data.msg!=null&&data.msg!="")
							alert(data.msg);
						else
							alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error: function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	function goObsoletePage() {
		if(selectBindId=="ROOT"||selectBindId=="DEPT"||selectBindId=="SHARE"){
			alert("최 상위 캐비닛이라 폐기 할 수 없습니다.");
			return;
		}
		else if(selectSendType=='DST004'){
			alert("공유 캐비닛은 폐기 할 수 없습니다.");
			return;	
		}
		
		var confirmMessage = "";
		
		if(selectIsChildBind!='Y'){
			confirmMessage = "<spring:message code='bind.msg.confirm.obsolete'/>";
		}
		else{
			confirmMessage = "폐기 시 하위 캐비닛까지 폐기 됩니다. 폐기 하시겠습니까?";
		}
		
		if(confirm(confirmMessage)) {
			var form = document.listForm;
			var url = '<%=webUri%>/app/bind/bindObsolete.do';

			$.ajax({
				url : url,
				dataType : 'json',
				data: { 
					bindIds : selectBindId,
					deptIds : deptId,						
					unitTypes : selectUnitType						
				},
				
				success: function(data) {
					if(data.success) {
						selectBindId = selectParentBindId;
						fncBindTreeRefresh();
						alert("<spring:message code='bind.msg.completed'/>");
					} else {
						if(data.msg!=null&&data.msg!="")
							alert(data.msg);
						else
							alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error: function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}
	
	var webUri = '<%=webUri%>';
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";
	
	window.onload = init;
    $(document).ready(function() {
    	$('#deptName').html("<img src='<%=webUri%>/app/ref/image/dot_search9.gif'/><b>${deptName}</b>");
    	fncBindTreeRefresh();
    });

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/bindManager.do'>
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="deptId" value="${deptId}" />
	<input type="hidden" name="deptName" value="${deptName}" />
	<%-- <input type="hidden" name="pageNo" value="${pageNo}" /> --%>
	<input type="hidden" name="bindId" />
	<input type="hidden" name="deptIds" />
	<input type="hidden" name="targetId" />
	<input type="hidden" name="targetIds" />
	<input type="hidden" name="type" />
	<input type="hidden" name="treetype" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.manage" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="ltb_left" id="deptName" nowrap="nowrap">
			</td>
			<td nowrap="nowrap">
				<acube:buttonGroup align="right">
					<% if(roleCode.indexOf(sysRoleCode) > -1) { %>
						<acube:menuButton id="select_dept" disabledid="" onclick="javascript:goSelectDept();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
						<acube:space between="button" />
					<% } %>
					<acube:menuButton id="add" disabledid="" onclick="javascript:fncAddReload();" value='<%= m.getMessage("bind.button.add", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="transfer" disabledid="" onclick="javascript:goTransferPage();" value='<%= m.getMessage("bind.button.transfer", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="share" disabledid="" onclick="javascript:goSharePage();" value='<%= m.getMessage("bind.button.share", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="bind_job" disabledid="" onclick="javascript:goJobPage();" value='<%= m.getMessage("bind.button.job", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="obsolete" disabledid="" onclick="javascript:goObsoletePage();" value='<%= m.getMessage("bind.button.obsolete", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="revocate" disabledid="" onclick="javascript:goRevocatePage();"  value='<%= m.getMessage("bind.button.share.revocate", null, langType) %>' />
				</acube:buttonGroup>
			</td>
		</tr>
		<table class="tablesearchnp" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<acube:space between="search" />
					<td style="padding:5px 7px;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="50">
									<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
								</td>
							</tr>
						</table>
					</td>
				<acube:space between="search" />
			</tr>
		</table>
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
							<td width="25%" valign="top" style="height: 18px;">
								<table width="100%" border="0" cellSpacing="0" cellPadding="0">
									<td width="16" height="18" valign="top">
										<img src="<%=webUri%>/app/ref/image/title3.gif" width="16" height="16"></td>
									<td class="file_title"><spring:message code="bind.title.category"/></td>
								</table>
							</td>
							<td width="1%" valign="top"></td>
							<td width="74%" valign="top" style="height: 18px;">
								<table width="100%" border="0" cellSpacing="0" cellPadding="0">
									<td width="16" height="18" valign="top">
										<img src="<%=webUri%>/app/ref/image/title3.gif" width="16" height="16"></td>
									<td class="file_title"><spring:message code="bind.title.info" /></td>
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
							<td width="25%" valign="top">
								<div id="bind_tree" class="table_border" style="height: 620px;"></div>
							</td>
							<td width="1%" valign="top"></td>
							<td width="74%" valign="top">
								<div id="bind_detail" class="table_border"
									style="height: 620px;"></div>
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