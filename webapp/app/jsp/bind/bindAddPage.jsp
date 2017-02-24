<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="com.sds.acube.app.common.vo.AppCodeVO"%>
<%@ page import="java.util.List"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.add' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<%-- <link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/common.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/bind.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<%
	List<AppCodeVO> appCodeList =(List<AppCodeVO>)request.getAttribute("cabAuthList");
%>

<SCRIPT LANGUAGE="javascript">
	var adminInfo='${adminInfo}';
	var authInfo='${authInfo}';
	var chkAll = 0;
	var isBindAdmin = 'Y';
	var authorityArr = null;
	
	isNotEmpty = function(_str){
		obj = String(_str);
		if(obj == null || obj == undefined || obj == 'null' || obj == 'undefined' || obj == '' ) return false;
		else return true;
	};
	
	//----------------------------분류체계 팝업 start-------------------------------------------//
	var selectUnitCallback = function(unitId, unitName, unitType, unitOrder, unitDepth, parentId, isChildUnit) {
		$("#unitId").val(unitId);
		$("#unitName").val(unitName);
		$("#unitType").val(unitType);
	};
	
	function goUnitTreePage() {
		var params = 'deptId=' + "${compId}" + '&adminSelectDept=N';
		var url = '<%=webUri%>/app/bind/unit/simple/unitListTree.do?'+params;
		openWindow('bind_unit_tree_win', url, 430, 500, 'no');
	}
	//----------------------------분류체계 팝업 end-------------------------------------------//
	//체크박스 전부체크
	function checkAll() {
	    if($(":checkbox").length <= 0) return;
	    if(chkAll==0){
	        chkAll = 1;
	        $(":checkbox").attr("checked",true);
	
	    }else{
	        chkAll = 0;
	        $(":checkbox").attr("checked",false);
	    }
	}
	
	function checkDisable() {
	    if($(":checkbox").length <= 0) return;
        chkAll = 0;
        $(":checkbox").attr("checked",false);
	}
	
	function adminDel(){
		var adminId = ""; 
		var form = document.bindForm;

		for(var i = 1 ; i < document.getElementsByName("checkbox").length ; i++){
			if(document.getElementsByName("checkbox")[i].checked){
				adminId = document.getElementsByName("checkbox")[i].value;
				
				//화면에 삭제후 출력할 데이타
				var temp = adminInfo;
				adminInfo = "";
				var admins = temp.split(String.fromCharCode(4));
				for (var j=0;j<admins.length;j++) {
				    if(admins[j].indexOf(adminId)==-1 ){
				    	if(adminInfo!="")
				    		adminInfo += String.fromCharCode(4);
				    	adminInfo += admins[j];
			        }
				}
			}
			
		}

		if(adminId ==""){
			alert("삭제할 대상을 선택 하지 않았습니다.");
		}
		//else if(confirm("<spring:message code='board.msg.confirm'/>")==true)	
			dispAdminInfo(adminInfo);
		//}
	}
	
	function dispEmptyAdminInfo(){
		var tbl = $('#tblOrgViewLine');;
		tbl.empty();
		
		var row = "";
		row = "<tr><th scope='col' width='10%'>";
		row += "<input type='checkbox' name='checkbox' id='checkbox' onclick='checkAll(0)'/>";
		row += "</th>";
		row += "<th width='90%' scope='col'>관리자</th></tr>";
		row += "<tr><td colspan=2 class='tablelistcenter'>";
		row += "관리자를 추가해 주세요.";
		row += "</td></tr>";

		tbl.append(row);	
	}
	
	function dispAdminInfo(info){
		$('#adminInfo').val(info);
		var admins = info.split(String.fromCharCode(4));
		
		var tbl = $('#tblOrgViewLine');;
		var adminsLength = admins.length;
		tbl.empty();
		
		var row = "";
		row = "<tr><th scope='col' width='10%'>";
		row += "<input type='checkbox' name='checkbox' id='checkbox' onclick='checkAll(0)'/>";
		row += "</th>";
		row += "<th width='90%' scope='col'>관리자</th></tr>";
		
		tbl.append(row);
		for (var i=0; i<adminsLength; i++) {
			if(admins[i]){
				var admin = admins[i].split(String.fromCharCode(2));
				row = "";
				var data = admin[4] + ' / ' + admin[2] + ' / ' + admin[1];

				row = "<tr><td width='10%' class='tablelistleft' style='text-align: center;'>";
				row += "<input type='checkbox'  name='checkbox' id='checkbox' value='"+admin[0]+"'/>";
				row += "</td>";
				row += "<td width='90%' class='tablelistcenter'>"+ data + "</td></tr>";
				tbl.append(row);
			}
		}
	}
	
	function setOrgInfo(info) {
		//alert("setAdminInfo info= "+info);
		adminInfo = info;
		dispAdminInfo(info);
	}
	
	function getAdminInfo() {
		return $('#adminInfo').val();	
	}
	
	function winSelectAdminPop() {
		openWindow("winSelectAdminPop", "<%=webUri%>/app/bind/selectOrgPop.do", 650, 550);
	}
	
	function authorityDel(){
		var authId = ""; 
		var form = document.bindForm;

		for(var i = 1 ; i < document.getElementsByName("checkbox").length ; i++){
			if(document.getElementsByName("checkbox")[i].checked){
				authId = document.getElementsByName("checkbox")[i].value;
				
				//화면에 삭제후 출력할 데이타
				var temp = authInfo;
				authInfo = "";
				var auths = temp.split(String.fromCharCode(4));
				for (var j=0;j<auths.length;j++) {
				    if(auths[j].indexOf(authId)==-1 ){
				    	if(authInfo!="")
				    		authInfo += String.fromCharCode(4);
				    	authInfo += auths[j];
			        }
				}
			}
			
		}

		if(authId ==""){
			alert("삭제할 대상을 선택 하지 않았습니다.");
		}
		//else if(confirm("<spring:message code='board.msg.confirm'/>")==true)	
			dispAuthInfo(authInfo);
		//}
	}
	
	function dispEmptyAuthInfo(){
		var tbl = $('#tblAuthViewLine');;
		tbl.empty();
		
		var row = "";
		row = "<tr><th scope='col' width='10%'>";
		row += "<input type='checkbox' name='checkbox' id='checkbox' onclick='checkAll(0)'/>";
		row += "</th>";
		row += "<th width='90%' scope='col'>문서 권한</th></tr>";
		row += "<tr><td colspan=2 class='tablelistcenter'>";
		row += "문서 권한을 추가해 주세요.";
		row += "</td></tr>";

		tbl.append(row);	
	}
	
	function dispAuthInfo(info){
		$('#authInfo').val(info);
		var auths = info.split(String.fromCharCode(4));
		
		var tbl = $('#tblAuthViewLine');;
		var authsLength = auths.length;
		tbl.empty();
		
		var row = "";
		row = "<tr><th scope='col' width='10%'>";
		row += "<input type='checkbox' name='checkbox' id='checkbox' onclick='checkAll(0)'/>";
		row += "</th>";
		row += "<th width='90%' scope='col'>문서 권한</th></tr>";
		
		tbl.append(row);
		for (var i=0; i<authsLength; i++) {
			if(auths[i]){
				var auth = auths[i].split(String.fromCharCode(2));
				row = "";
				var data = getAuthorityStr(auth[3], auth[1]);
				
				row = "<tr><td width='10%' class='tablelistleft' style='text-align: center;'>";
				row += "<input type='checkbox'  name='checkbox' id='checkbox' value='"+auth[0]+"'/>";
				row += "</td>";
				row += "<td width='90%' class='tablelistcenter'>"+ data + "</td></tr>";
				tbl.append(row);
			}
		}
	}
	
	function setAuthInfo(info) {
		//alert("setAuthInfo info= "+info);
		authInfo = info;
		dispAuthInfo(info);
	}
	
	function getAuthInfo() {
		return $('#authInfo').val();	
	}
	
	function winSelectAuthorityPop() {
		openWindow("selectAuthorityPop", "<%=webUri%>/app/bind/selectAuthorityPop.do", 650, 550);
	}
	
	function getAuthorityStr(authDeptName, authorityValue) {
		var authorityStr = authDeptName + " / ";
		for(var i = 0; i < authorityArr.length; i++){
			var authority = authorityArr[i];
			if(authority.value == authorityValue){
				authorityStr += authority.name;
				break;
			}
		}
		return authorityStr;
	}
	
	function getAuthority(){
		return authorityArr;
	}
	
	function initAuthInfo(){
		Authority = function(value, name){
		    this.value = value;
		    this.name = name;
		}
		
		if(authorityArr == null)
			authorityArr = new Array();
		
		while (authorityArr.length) //권한 배열 비우기
			authorityArr.pop();
		
		<% 
		for(int i = 0; i < appCodeList.size();i++){ 
		%>
			var authValue = '<%= appCodeList.get(i).getCodeValue()%>';
			var authName = '<%= appCodeList.get(i).getCodeName()%>';
			authorityArr.push(new Authority(authValue, authName));
		<% } %>
	}
	
	function winSelectTabPopup(){
		if(isBindAdmin == 'Y'){
			winSelectAdminPop();
		}
		else {
			winSelectAuthorityPop();
		}
	}
	
	function delTab(){
		if(isBindAdmin == 'Y'){
			adminDel();
		}
		else {
			authorityDel();
		}
	}
	
	function add() {
		var form = document.getElementById('bindForm');
		
		var adminInfo = getAdminInfo();
		if(adminInfo == null || adminInfo == '') {
			alert(bind_admin_required);
			return;
		}
		
		var authInfo = getAuthInfo();
		if(authInfo == null || authInfo == '') {
			alert(bind_auth_required);
			return;
		}
		
		var unitId = form.unitId.value.trim();
		if(unitId == null || unitId == '') {
			alert(bind_unit_code_required);
			form.unitId.focus();
			return;
		}
	
		var unitName = form.unitName.value.trim();
		if(unitName == null || unitName == '') {
			alert(bind_unit_name_required);
			form.unitName.focus();
			return;
		}

		var bindName = form.bindName.value.trim();
		if(bindName == null || bindName == '') {
			alert(bind_name_required);
			form.bindName.focus();
			return;
		}
		
		var createYear = form.createYear.value.trim();
		if(createYear == null || createYear == '') {
			alert(bind_create_year_required);
			form.createYear.focus();
			return;
		}

		var expireYear = form.expireYear.value.trim();
		if(expireYear == null || expireYear == '') {
			alert(bind_expire_year_required);
			form.expireYear.focus();
			return;
		}
		
		var description =$.trim($('#description').val());
		if(description.length > 0 && description.length > 400) {
			alert(msg_description);
			$('#description').focus();
			return;
		}

		/* var prefix = form.prefix.value.trim(); */

		if(this.year > createYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.create.greater.than.current' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.create.greater.than.current.period' arguments='${year}'/>");
			}
			form.createYear.focus();
			return;
		} else if(this.year> expireYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.expire.greater.than.current' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.expire.greater.than.current.period' arguments='${year}'/>");
			}
			form.expireYear.focus();
			return;
		} else if(createYear > expireYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.expire.greater.than.create' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.expire.greater.than.create.period' arguments='${year}'/>");
			}
			form.createYear.focus();
			return;
		}
		
		var retentionPeriod = form.retentionPeriod.value.trim();
		if(retentionPeriod == null || retentionPeriod == '' || retentionPeriod == '_') {
			alert(bind_retentionPeriod_required);
			form.retentionPeriod.focus();
			return;
		}
		
		var bindOrder = form.bindOrder.value.trim();
		if(bindOrder == null || bindOrder == '' || bindOrder == '_') {
			alert(bind_order_required);
			form.bindOrder.focus();
			return;
		}
		var docType = form.docType.value;
		var unitType = form.unitType.value;
		var deptId = form.deptId.value;
		var parentBindId = '${bindId}';
		var bindDepth = '${bindDepth}';
		$.ajax({
			url : '<%=webUri%>/app/bind/create.do',
			type : 'POST',
			dataType : 'json',
			data : {
				deptId : '${deptId}',
				unitId : unitId,
				unitName : unitName,
				unitType : unitType,
				bindName : bindName,
				createYear : createYear,
				expireYear : expireYear,
				retentionPeriod : retentionPeriod,
				docType : docType,
				/* prefix : prefix, */
				description : description,
				adminInfo : adminInfo,
				authInfo : authInfo,
				parentBindId : parentBindId,
				bindDepth : bindDepth,
				bindOrder : bindOrder
			},
			success : function(data) {
				if(data.success) {
					fncBindAddCallback(data.bindId, bindName);
					<%-- var f = document.listForm;
					f.target = '_self';
					f.action = '<%=webUri%>/app/bind/bindManager.do';
					f.submit();
					selectBindId = selectParentBindId;
					alert("<spring:message code='bind.msg.add.completed'/>"); --%>
					
					//window.close();
				} else {
					if(data.msgId) {
						alert(data.msg);
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

	function selectNode(node, tree_obj, treeNum)
	{
		var unitId = node.getAttribute('unitId');
		var unitName = node.getAttribute('unitName');
		var unitType = node.getAttribute('unitType');
		$("#unitId").val(unitId);
		$("#unitName").val(unitName);
		$("#unitType").val(unitType);
	}

	//----------------------------초기 OnLoad 이벤트 시작-------------------------------------------//
	$(document).ready(function() { 
		document.bindForm.retentionPeriod.value = '${default}';

		initAuthInfo();
		if(isNotEmpty(adminInfo))
			dispAdminInfo(adminInfo);
		else dispEmptyAdminInfo();
		
		if(isNotEmpty(authInfo))
			dispAuthInfo(authInfo);
		else dispEmptyAuthInfo();
		
		$('#tab1').click(function() {
			checkDisable(); //tab 이동시 check 비 활성화
			$('#tab1').attr('class', 'tab_sch_on');
			$('#tab2').attr('class', 'tab_sch_off');
			$('.adminList').show();
			$(".authorityList").hide();
			isBindAdmin = 'Y';
		});
		
		//회의록
		$('#tab2').click(function() {
			checkDisable(); //tab 이동시 check 비 활성화
			$('#tab1').attr('class', 'tab_sch_off');
			$('#tab2').attr('class', 'tab_sch_on');
			$('.adminList').hide();
			$(".authorityList").show();
			isBindAdmin = 'N';
		});
	});

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_name = "<spring:message code='bind.obj.name'/>";
	var bind_create_year = '${option}' == 'year' ? "<spring:message code='bind.obj.create.year'/>" : "<spring:message code='bind.obj.create.period'/>";
	var bind_expire_year = '${option}' == 'year' ? "<spring:message code='bind.obj.expire.year'/>" : "<spring:message code='bind.obj.expire.period'/>";
	var bind_retentionPeriod = "<spring:message code='bind.obj.retention.period'/>";
	var bind_order = "<spring:message code='bind.obj.order'/>";
	var bind_auth = "<spring:message code='bind.tab.authority'/>";
	var bind_admin = "<spring:message code='bind.tab.admin'/>";
	var rootText = "<spring:message code='bind.obj.document.division'/>";
	var select_unit = "<spring:message code='bind.msg.select.unit'/>";
		
	var bind_unit_code_required = bind_unit_code + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_name_required = bind_unit_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_name_required = bind_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_create_year_required = bind_create_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_expire_year_required = bind_expire_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_retentionPeriod_required = bind_retentionPeriod + " <spring:message code='bind.msg.mandantory'/>";
	var bind_order_required = bind_order + " <spring:message code='bind.msg.mandantory'/>";
	var bind_auth_required = bind_auth + " <spring:message code='bind.msg.mandantory'/>";
	var bind_admin_required = bind_admin + " <spring:message code='bind.msg.mandantory'/>";
	var webUri = '<%=webUri%>';
	var year = ${year};
	
	//window.onload = init;

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/bind/create.do" onsubmit="return false;">
	<input type="hidden" name="deptId" value="${deptId}"/>
	<input type="hidden" name="compId" value="${compId}" />
	<input type="hidden" name="adminInfo" id="adminInfo" value="${adminInfo}"/>
	<input type="hidden" name="authInfo" id="authInfo" value="${authInfo}"/>
	
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class"td_table">
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="130" class="tb_tit">
							<spring:message code='bind.obj.name' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input style="width:100%" type="text" class="input" name="bindName" size="49"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="130" class="tb_tit">
							<spring:message code='bind.obj.dept' />
						</td>
						<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input style="width:100%" type="text" class="input_read" name="deptName" value="${deptName}" readonly size="49"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff"> 
						<td width="130" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/></td>
						<td height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr bgcolor="#ffffff">
									<td><textarea id="description" name="description" style="width:100%;overflow:auto" rows="5" onkeyup="checkInputMaxLength(this, '', 400)"></textarea></td>
								</tr>
							</table>
						</td>
					</tr> 
					<tr bgcolor="#ffffff">
						<td width="130" class="tb_tit">
							<spring:message code='bind.obj.type' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td><form:select id="docType" name="docType" path="docType" items="${docType}" /></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="130" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.order'/></td>
						<td class="b_td"><table border="0" cellspacing="1" cellpadding="1">
								<tr bgcolor="#ffffff">
									<td><input type="number" class="input" id="bindOrder" name="bindOrder" size="20" value="1"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="130" class="tb_tit" nowrap="nowrap">
							<spring:message code='bind.obj.unit.code' /> <spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td style="padding-left:-1px;">
									<input type="text" class="input_read" id="unitName" name="unitName" readonly size="35">
								</td>
								<input type="hidden" id="unitId" name="unitId" />
								<input type="hidden" id="unitType" />
								<td><acube:button onclick="javascript:goUnitTreePage();"
										value='<%=m.getMessage("bind.button.search", null, langType)%>' />
									<acube:space between="button" />
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<%-- <tr bgcolor="#ffffff">
						<td width="130" class="tb_tit">
							<spring:message code='bind.obj.prefix.code' />
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="prefix" onkeyup="checkInputMaxLength(this, '', 12)" size="29"></td>
							</tr>
						</table>
						</td>
					</tr> --%>
					
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub">
					<spring:message code='bind.title.classification' />
				</acube:titleBar>
			</td>
		</tr>
		<tr>
			<td><acube:tableFrame>
				<tr bgcolor="#ffffff">
					<td width="130" class="tb_tit">
						<c:choose>
						<c:when test="${option != 'year'}">
							<spring:message code='bind.obj.create.period' />
						</c:when>
						<c:otherwise>
							<spring:message code='bind.obj.create.year' />
						</c:otherwise>
						</c:choose>
						<spring:message code='common.title.essentiality'/>
					</td>
					<td>
					<table border="0" cellspacing="1" cellpadding="1">
						<tr bgcolor="#ffffff">
							<td class="communi_text_list">
								<input type="text" class="input" name="createYear" id="createYear"
									style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);"
									maxlength="4" size="6" value="${year}">
									
								<c:choose>
								<c:when test="${option != 'year'}">
									<spring:message code='bind.obj.period' />
								</c:when>
								<c:otherwise>
									<spring:message code='bind.obj.year'/>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr bgcolor="#ffffff">
					<td width="130" class="tb_tit">
						<c:choose>
						<c:when test="${option != 'year'}">
							<spring:message code='bind.obj.expire.period' />
						</c:when>
						<c:otherwise>
							<spring:message code='bind.obj.expire.year' />
						</c:otherwise>
						</c:choose>
						<spring:message code='common.title.essentiality'/>
					</td>
					<td>
					<table border="0" cellspacing="1" cellpadding="1">
						<tr bgcolor="#ffffff">
							<td class="communi_text_list">
								<input type="text" class="input" name="expireYear" id="expireYear"
									style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);"
									maxlength="4" size="6" value="${year}"> 
									
								<c:choose>
								<c:when test="${option != 'year'}">
									<spring:message code='bind.obj.period' />
								</c:when>
								<c:otherwise>
									<spring:message code='bind.obj.year'/>
								</c:otherwise>
								</c:choose> 
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr bgcolor="#ffffff">
					<td width="130" class="tb_tit">
						<spring:message code='bind.obj.retention.period' /> <spring:message code='common.title.essentiality'/>
					</td>
					<td>
					<table border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" /></td>
						</tr>
					</table>
					</td>
				</tr>
			</acube:tableFrame> <!-------정보등록 Table E ---------></td>
		</tr>
		<!-------내용조회  E --------->

		<tr>
			<td>
			
			<table  width="100%">
			<tr bgcolor="#ffffff" >
				<acube:space between="title_button" />
				</tr>
				<tr bgcolor="#ffffff" style="height:20px;">
					<td style="line-height:20px;">
						<div class="tab_sch_wrap" style="line-height:20px;">
							<li class="tab_sch_on" id="tab1"><span><spring:message code='bind.tab.admin' /></span></li>
							<li class="tab_sch_off" id="tab2"><span><spring:message code='bind.tab.authority' /></span></li>
							<li class="tab_sch_btn">
								<acube:buttonGroup>
									<acube:button onclick="javascript:delTab();" value='<%= m.getMessage("bind.button.adminDel", null, langType) %>' type="2" class="gr" />
									<td>&nbsp;</td>
									<acube:button onclick="javascript:winSelectTabPopup();" value='<%= m.getMessage("bind.button.adminAdd", null, langType) %>' type="2" class="gr" />
								</acube:buttonGroup>
							</li>
						</div>
					</td>
				</tr>

				<tr bgcolor="#ffffff" class="adminList" style="display: block;">
					<td colspan="2" nowrap="nowrap">
					<div style="height: 120px; overflow: auto;">
						<table id="tblOrgViewLine" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
						</table>
					</div>
					</td>
				</tr>
				<tr bgcolor="#ffffff" class="authorityList" style="display: none;">
					<td colspan="2" nowrap="nowrap">
					<div style="height: 120px; overflow: auto;">
						<table id="tblAuthViewLine" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
						</table>
					</div>
					</td>
				</tr>
			</table> <!-------정보등록 Table E ---------></td>
		</tr>

		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:add();"
						value='<%= m.getMessage("bind.button.save", null, langType) %>'
						type="2" class="gr" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>