<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : OrgUserPop.jsp 
 *  Description : 부서별 사용자 선택
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.17 
 *   수 정 자 : 장진홍
 *   수정내용 : 신규개발 
 * 
 *  @author  장진홍
 *  @since 2011.05.17 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	
	List<DepartmentVO> orgTree = (List<DepartmentVO>)request.getAttribute("orgTree");
	
	String searchType = request.getParameter("searchType");
	
	String listType = CommonUtil.nullTrim(request.getParameter("listType"));
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.table.title.dept" /><spring:message code="list.list.button.search" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript"><!--
var isCtrl = false, isShift = false; //keyChecked
var gSaveObject = g_selector();
var sColor = "#F2F2F4";
var Comps = new Array();

$(document).ready(function() { 
	setValuable();
	//동명이인검색 추가
	$('#userName').live('keydown', function(event){
		var keyCode = event.which;
		if(keyCode === 13){
			goSameNameUser();
		}
	});	
	
	if(typeof(opener) != "undefined"){
		if(typeof(opener.getSearchDept) != "undefined"){
			insertDept(opener.getSearchDept());
		}
	}

	if("<%=listType%>" == "All"){
		// 수신자그룹 확인
		if(typeof(opener) != "undefined"){
			if(typeof(opener.getSearchRecvGroup) != "undefined"){
				insertRecvGroup(opener.getSearchRecvGroup());
			}
		}
	}
});

//$(document).ready 종료
$(document).bind('keyup', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=false;  
	if(keyCode === 16) isShift=false; 
	
});

$(document).bind('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=true;  
	if(keyCode === 16) isShift=true; 
	
});


function setValuable(){
	<%
		int tIndex = 0;
		for(int i = 0; i < orgTree.size(); i++){ 
			DepartmentVO deptInfo = orgTree.get(i);
			
			if(deptInfo.getOrgType() == 1){
	%>
	Comps[<%=tIndex%>] = new Object();
	Comps[<%=tIndex%>].compId = "<%=deptInfo.getOrgID()%>";
	Comps[<%=tIndex%>].compName = "<%=deptInfo.getOrgName()%>";
	<%
				tIndex++;
			}
		}
	%>
}

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertDept(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	var nSize = results.length;

	if(nSize > 10){
		nSize = 10;
	}
	
	for(var i = 0; i < nSize; i++){
		var comp = results[i];
		var row = "<tr id='"+comp.orgID;
		row += "' address='"+comp.address;
		row += "' addressDetail='"+comp.addressDetail;
		row += "' addrSymbol='"+comp.addrSymbol;
		row += "' chiefPosition='"+comp.chiefPosition;
		row += "' companyID='"+comp.companyID;
		row += "' description='"+comp.description;
		row += "' email='"+comp.email;
		row += "' fax='"+comp.fax;
		row += "' formBoxInfo='"+comp.formBoxInfo;
		row += "' homepage='"+comp.homepage;
		row += "' institutionDisplayName='"+comp.institutionDisplayName;
		row += "' isDeleted='"+comp.isDeleted;
		row += "' isInspection='"+comp.isInspection;
		row += "' isInstitution='"+comp.isInstitution;
		row += "' isODCD='"+comp.isODCD;
		row += "' isProcess='"+comp.isProcess;
		row += "' isProxyDocHandleDept='"+comp.isProxyDocHandleDept;
		row += "' oDCDCode='"+comp.oDCDCode;
		row += "' orgAbbrName='"+comp.orgAbbrName;
		row += "' orgCode='"+comp.orgCode;
		row += "' orgID='"+comp.orgID;
		row += "' orgName='"+comp.orgName;
		row += "' orgOrder='"+comp.orgOrder;
		row += "' orgOtherName='"+comp.orgOtherName;
		row += "' orgParentID='"+comp.orgParentID;
		row += "' orgType='"+comp.orgType;
		row += "' outgoingName='"+comp.outgoingName;
		row += "' proxyDocHandleDeptCode='"+comp.proxyDocHandleDeptCode;
		row += "' reserved1='"+comp.reserved1;
		row += "' reserved2='"+comp.reserved2;
		row += "' reserved3='"+comp.reserved3;
		row += "' roleCodes='"+comp.roleCodes;
		row += "' telephone='"+comp.telephone;
		row += "' zipCode='"+comp.zipCode;
		row += "' listType='recvDept";
		row += "' rowOrd='"+ i;
		row += "' onclick='onListClick($(\"#"+comp.orgID+"\"));' ondblclick='onListDblClick($(\"#"+comp.orgID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+comp.orgID+"\"));' >";

		var companyName = "";
		for(var j = 0; j < Comps.length; j++){
			var root = Comps[j];
			if(root.compId === comp.companyID){
				companyName = root.compName;
				break;
			}
		}

		row += "<td class='ltb_center' width='143' style='border-bottom: #ADBED7 1px solid;'>"+companyName+"</td>";
		row += "<td class='ltb_center'  style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;'>"+comp.orgName+"</td></tr>";
		tbl.append(row);
	}		
}


//선택된 수신자 그룹의 내용을 저장한다.
function insertRecvGroup(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	//tbl.empty();
	var nSize = results.length;

	if(nSize > 10){
		nSize = 10;
	}
	
	for(var i = 0; i < nSize; i++){
		var obj = results[i];
		var msgCode = "";
		
		var row = "<tr id='"+obj.recvGroupId;
		row += "' listType='recvGroup";
		row += "' rowOrd='"+ i;
		row += "' onclick='onListClick($(\"#"+obj.recvGroupId+"\"));' ondblclick='onListDblClick($(\"#"+obj.recvGroupId+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+obj.recvGroupId+"\"));' >";

		if(obj.groupType == "GUT001"){
			msgCode = "<spring:message code='env.grouptype.GUT001' />";
		}else if(obj.groupType == "GUT003"){
			msgCode = "<spring:message code='env.grouptype.GUT003' />";
		}else if(obj.groupType == "GUT004"){
			msgCode = "<spring:message code='env.grouptype.GUT004' />";
		}
		
		row += "<td class='ltb_center' width='143' style='border-bottom: #ADBED7 1px solid;'>"+msgCode+"</td>";
		row += "<td class='ltb_center'  style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;'>"+obj.recvGroupName+" ("+obj.recvCount+")</td></tr>";
		tbl.append(row);
	}		
}

function clearTable(){
	var tbl = $('#tbUsers tbody');
	tbl.empty();
	var row = "<tr>";
	row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;'colsapn='2'><spring:message code='app.alert.msg.19' /></td>";
	row += "</tr>";
	tbl.append(row);
}

//사원 선택시 발생하는 이벤트 
// parameter obj : 선택된 tr 의 jquery 객체이다.
function onListClick(obj){
		selectOneElement(obj)
}

//하나의 셀을 선택했을 수행하는 이벤트
//parameter obj : 선택된 tr 의 jquery 객체이다.
function selectOneElement(obj){
	gSaveObject.restore();
	gSaveObject.add(obj, sColor);
}
//마우스 이동시 발생하는 이벤트
function onListMouseMove(){
	document.selection.empty();
}

//키를 선택했을 때 발생하는 이벤트
function onListKeyDown(obj){
	var objTmp = null;
	var checkEvn = false;
		
	switch(event.keyCode){
		case 37:
		case 38:
		{
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40:
		{
			objTmp = obj.next();
			checkEvn = true;
			break;
		}
		case 13:
		{
			sendOk();
			break;
		}		
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//사원 더블클릭시 발생하는 이벤트
function onListDblClick(obj){
	document.selection.empty();
	sendOk();
}

//확인버튼 클릭
function sendOk(){
	var trComp = gSaveObject.first();
	var objDept = new Object();

	if(trComp != null){
		
		if(trComp.attr('listType') == null || trComp.attr('listType') == ""){
			trComp.attr('listType') = "recvDept";
		} 
		
		if(trComp.attr('listType') == "recvDept"){
			objDept.companyID = trComp.attr('companyID');
			objDept.orgID = trComp.attr('orgID');
			
			if("<%=searchType%>" == "1"){
				if(typeof(opener) != "undefined"){
					if(typeof(opener.selectOwnDept) != "undefined"){
						opener.selectOwnDept(objDept);
						opener.AddList();
					}
		
					if(typeof(opener.setDirectOwnDept) != "undefined"){
						//opener.setDirectOwnDept();
					}
				}
			}else{
				if(typeof(opener) != "undefined"){
					if(typeof(opener.selectOtherDept) != "undefined"){
						opener.selectOtherDept(objDept);
					}
		
					if(typeof(opener.setDirectOwnDept) != "undefined"){
						opener.setDirectOwnDept();
					}
				}
			}
			
		}else if(trComp.attr('listType') == "recvGroup"){
			
			if(typeof(opener.getRecvGroupAdd) != "undefined"){
				opener.getRecvGroupAdd(trComp.attr('id'));
			}
		}
		
	}else{
		alert("<spring:message code='env.deptInfo.msg.noSelectDept' />");
		return;
	}
	
	closePopup();
}

function closePopup(){
	window.close();
}
//동명이인 검색
function goSearchDept(){	
	var objDept = $('#searchDept');

	if(objDept.val() === ""){
		alert("<spring:message code='common.msg.deptnm.no' />"); //common.msg.deptnm.no
		return;
	}

	var url = "<%=webUri%>/app/common/searchDept.do?method=1";
	url += "&searchType=<%=searchType%>";

	if("<%=listType%>" == "All"){
		url += "&listType=All";
	}
	
	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,objDept.serialize() ,function(data){
		results = data;
	});

	if("<%=listType%>" == "All"){

		var results2 = "";
		var url2 = "<%=webUri%>/app/env/searchEnvAppLineGroup.do";
		if("<%=listType%>" == "All"){
			url2 += "?listType=All";
		}

		$.ajaxSetup({async:false});
		$.getJSON(url2,objDept.serialize() ,function(data2){
			results2 = data2;
		});

		if(results != "" || results2 != ""){
			emptyTable();
			
			if(results != ""){
				insertDept(results);
			}

			if(results2 != ""){
				insertRecvGroup(results2);
			}
			return;
		}else{
			clearTable();
			return;
		}
		
	}else{

		if(results != ""){
			insertDept(results);
			return;
		}else{
			clearTable();
			return;
		}
	}
	
}

function searchKeyup(){

	if(event.keyCode === 13){
		goSearchDept();
	}
	return;		
}

function emptyTable(){
	var tbl = $('#tbUsers tbody');
	tbl.empty();
}

-->
</script>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
		<acube:titleBar><spring:message code="approval.table.title.dept" /><spring:message code="list.list.button.search" /></acube:titleBar>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>
		<acube:tableFrame type="search" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3">&nbsp;</td>
						<td width="15%" class="search_title"><spring:message code="bind.obj.dept.name" /></td>				
						<td width="60%">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="96%">
								<input id="searchDept" name="searchDept" type="text" class="input" style="width: 100%;ime-mode:active;" onkeypress="searchKeyup();" /></td>
								<td>&nbsp;</td><td>
								<a href="javascript:goSearchDept();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a>
								</td>
							</tr>
						</table>
					</td>
					<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		</acube:tableFrame>
		</td>
		</tr>
		</acube:tableFrame>
		<acube:space between="button_content" table="y"/>
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
				<tr>
					<td height="190" class="basic_box" style="padding:0px; margin:0px;">
					<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<TR>
								<TD width="150" class="ltb_head" style="border-top:none;border-bottom: #ADBED7 1px solid;"><spring:message code="common.title.company" /></TD>
								<TD class="ltb_head" style="border-left:1pt solid #ADBED7;border-bottom: #ADBED7 1px solid;"><spring:message code="approval.table.title.dept" /></TD>
							</TR>
						</thead>
					</table>
					</div>
					<div style="height:162px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
					<table id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody />
					</table>
					</div>
				</td>
				</tr>
			</acube:tableFrame>
			</td>
		</tr>
		</acube:tableFrame> 
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		<acube:buttonGroup align="right">
		<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=confirmBtn %>" />
		<acube:space between="button" />
		<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" />
		</acube:buttonGroup>		
	</acube:outerFrame>
</body>	
</html>