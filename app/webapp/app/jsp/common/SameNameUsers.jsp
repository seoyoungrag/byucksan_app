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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.approval.samenameusers" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript"><!--
var isCtrl = false, isShift = false; //keyChecked
var gSaveObject = g_selector();
var sColor = "#F2F2F4";

$(document).ready(function() { 
	//동명이인검색 추가
	$('#userName').live('keydown', function(event){
		var keyCode = event.which;
		if(keyCode === 13){
			goSameNameUser();
		}
	});	
	
	if(opener != null){
		if(opener.getSameUsers != null)
			insertUser(opener.getSameUsers());
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


//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	for(var i = 0; i < results.length; i++){
		var user = results[i];
		//var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;		
		//row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
		//row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
		var row = "<tr id='"+user.userUID;
		row += "' certificationID='"+ user.certificationID;
		row += "' changedPWDDate='"+ user.changedPWDDate;
		row += "' compID='"+ user.compID;
		row += "' compName='"+ user.compName;
		row += "' compOtherName='"+ user.compOtherName;
		row += "' deptID='"+ user.deptID;
		row += "' deptName='"+ user.deptName;
		row += "' deptOtherName='"+ user.deptOtherName;
		row += "' description='"+ user.description;
		row += "' displayPosition='"+ user.displayPosition;
		row += "' duty='"+ user.duty;
		row += "' dutyCode='"+ user.dutyCode;
		row += "' dutyName='"+ user.dutyName;
		row += "' dutyOrder='"+ user.dutyOrder;
		row += "' dutyOtherName='"+ user.dutyOtherName;
		row += "' email='"+ user.email;
		row += "' employeeID='"+ user.employeeID;
		row += "' gradeAbbrName='"+ user.gradeAbbrName;
		row += "' gradeCode='"+ user.gradeCode;
		row += "' gradeName='"+ user.gradeName;
		row += "' gradeOrder='"+ user.gradeOrder;
		row += "' gradeOtherName='"+ user.gradeOtherName;
		row += "' groupID='"+ user.groupID;
		row += "' groupName='"+ user.groupName;
		row += "' groupOtherName='"+ user.groupOtherName;
		row += "' homeAddr='"+ user.homeAddr;
		row += "' homeDetailAddr='"+ user.homeDetailAddr;
		row += "' homeFax='"+ user.homeFax;
		row += "' homePage='"+ user.homePage;
		row += "' homeTel='"+ user.homeTel;
		row += "' homeTel2='"+ user.homeTel2;
		row += "' homeZipCode='"+ user.homeZipCode;
		row += "' loginResult='"+ user.loginResult;
		row += "' mailServer='"+ user.mailServer;
		row += "' mobile='"+ user.mobile;
		row += "' mobile2='"+ user.mobile2;
		row += "' officeAddr='"+ user.officeAddr;
		row += "' officeDetailAddr='"+ user.officeDetailAddr;
		row += "' officeFax='"+ user.officeFax;
		row += "' officeTel='"+ user.officeTel;
		row += "' officeTel2='"+ user.officeTel2;
		row += "' officeZipCode='"+ user.officeZipCode;
		row += "' optionalGTPName='"+ user.optionalGTPName;
		row += "' orgDisplayName='"+ user.orgDisplayName;
		row += "' orgDisplayOtherName='"+ user.orgDisplayOtherName;
		row += "' pager='"+ user.pager;
		row += "' partID='"+ user.partID;
		row += "' partName='"+ user.partName;
		row += "' partOtherName='"+ user.partOtherName;
		row += "' pcOnlineID='"+ user.pcOnlineID;
		row += "' positionAbbrName='"+ user.positionAbbrName;
		row += "' positionCode='"+ user.positionCode;
		row += "' positionName='"+ user.positionName;
		row += "' positionOrder='"+ user.positionOrder;
		row += "' positionOtherName='"+ user.positionOtherName;
		row += "' reserved1='"+ user.reserved1;
		row += "' reserved2='"+ user.reserved2;
		row += "' reserved3='"+ user.reserved3;
		row += "' residentNo='"+ user.residentNo;
		row += "' roleCodes='"+ user.roleCodes;
		row += "' securityLevel='"+ user.securityLevel;
		row += "' sysMail='"+ user.sysMail;
		row += "' titleCode='"+ user.titleCode;
		row += "' titleName='"+ user.titleName;
		row += "' titleOrder='"+ user.titleOrder;
		row += "' titleOtherName='"+ user.titleOtherName;
		row += "' userID='"+ user.userID;
		row += "' userName='"+ user.userName;
		row += "' userOrder='"+ user.userOrder;
		row += "' userOtherName='"+ user.userOtherName;
		row += "' userRID='"+ user.userRID;
		row += "' userStatus='"+ user.userStatus;
		row += "' userUID='"+ user.userUID;
		
		row += "' rowOrd='"+ i;
		row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";
		var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
		var deptName = (user.deptName == "" ? "&nbsp;" : user.deptName);
		row += "<td class='ltb_center' width='113' style='border-bottom: #ADBED7 1px solid;'>"+deptName+"</td>";
		row += "<td class='ltb_center' width='93' style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;'>"+userPosition+"</td>";
		row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;border-right:1pt solid #ADBED7;'>"+user.userName+"</td></tr>";
		tbl.append(row);
	}		
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
	var trUser = gSaveObject.first();
	var user = new Object();
	user.certificationID = trUser.attr('certificationID');
	user.changedPWDDate = trUser.attr('changedPWDDate');
	user.compID = trUser.attr('compID');
	user.compName = trUser.attr('compName');
	user.compOtherName = trUser.attr('compOtherName');
	user.deptID = trUser.attr('deptID');
	user.deptName = trUser.attr('deptName');
	user.deptOtherName = trUser.attr('deptOtherName');
	user.description = trUser.attr('description');
	user.displayPosition = trUser.attr('displayPosition');
	user.duty = trUser.attr('duty');
	user.dutyCode = trUser.attr('dutyCode');
	user.dutyName = trUser.attr('dutyName');
	user.dutyOrder = trUser.attr('dutyOrder');
	user.dutyOtherName = trUser.attr('dutyOtherName');
	user.email = trUser.attr('email');
	user.employeeID = trUser.attr('employeeID');
	user.gradeAbbrName = trUser.attr('gradeAbbrName');
	user.gradeCode = trUser.attr('gradeCode');
	user.gradeName = trUser.attr('gradeName');
	user.gradeOrder = trUser.attr('gradeOrder');
	user.gradeOtherName = trUser.attr('gradeOtherName');
	user.groupID = trUser.attr('groupID');
	user.groupName = trUser.attr('groupName');
	user.groupOtherName = trUser.attr('groupOtherName');
	user.homeAddr = trUser.attr('homeAddr');
	user.homeDetailAddr = trUser.attr('homeDetailAddr');
	user.homeFax = trUser.attr('homeFax');
	user.homePage = trUser.attr('homePage');
	user.homeTel = trUser.attr('homeTel');
	user.homeTel2 = trUser.attr('homeTel2');
	user.homeZipCode = trUser.attr('homeZipCode');
	user.loginResult = trUser.attr('loginResult');
	user.mailServer = trUser.attr('mailServer');
	user.mobile = trUser.attr('mobile');
	user.mobile2 = trUser.attr('mobile2');
	user.officeAddr = trUser.attr('officeAddr');
	user.officeDetailAddr = trUser.attr('officeDetailAddr');
	user.officeFax = trUser.attr('officeFax');
	user.officeTel = trUser.attr('officeTel');
	user.officeTel2 = trUser.attr('officeTel2');
	user.officeZipCode = trUser.attr('officeZipCode');
	user.optionalGTPName = trUser.attr('optionalGTPName');
	user.orgDisplayName = trUser.attr('orgDisplayName');
	user.orgDisplayOtherName = trUser.attr('orgDisplayOtherName');
	user.pager = trUser.attr('pager');
	user.partID = trUser.attr('partID');
	user.partName = trUser.attr('partName');
	user.partOtherName = trUser.attr('partOtherName');
	user.pcOnlineID = trUser.attr('pcOnlineID');
	user.positionAbbrName = trUser.attr('positionAbbrName');
	user.positionCode = trUser.attr('positionCode');
	user.positionName = trUser.attr('positionName');
	user.positionOrder = trUser.attr('positionOrder');
	user.positionOtherName = trUser.attr('positionOtherName');
	user.reserved1 = trUser.attr('reserved1');
	user.reserved2 = trUser.attr('reserved2');
	user.reserved3 = trUser.attr('reserved3');
	user.residentNo = trUser.attr('residentNo');
	user.roleCodes = trUser.attr('roleCodes');
	user.securityLevel = trUser.attr('securityLevel');
	user.sysMail = trUser.attr('sysMail');
	user.titleCode = trUser.attr('titleCode');
	user.titleName = trUser.attr('titleName');
	user.titleOrder = trUser.attr('titleOrder');
	user.titleOtherName = trUser.attr('titleOtherName');
	user.userID = trUser.attr('userID');
	user.userName = trUser.attr('userName');
	user.userOrder = trUser.attr('userOrder');
	user.userOtherName = trUser.attr('userOtherName');
	user.userRID = trUser.attr('userRID');
	user.userStatus = trUser.attr('userStatus');
	user.userUID = trUser.attr('userUID');
	
	if (opener != null && opener.setSameUsers) {		
		var strMsg = opener.setSameUsers(user);
		if(strMsg !== ""){
			alert(strMsg);
		}
		window.close();
	}
}

function closePopup(){
	window.close();
}
//동명이인 검색
function goSameNameUser(){
	var userName = $('#userName');

	if(userName.val() === ""){
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		userName.focus();
		return;
	}
	
	var results = "";
	var url = "<%=webUri%>/app/common/sameNameUsers.do";
	$.ajaxSetup({async:false});
	$.getJSON(url,userName.serialize() ,function(data){
		results = data;
	});

	if(results !== ""){
		insertUser(results);
	}		
}
--></script>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
		<acube:titleBar><spring:message code="approval.title.approval.samenameusers" /></acube:titleBar>
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
						<td width="15%" class="search_title">사용자명</td>				
						<td width="60%">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="96%">
								<input id="userName" name="userName" type="text" class="input" style="width: 100%;ime-mode:active;" /></td>
								<td>&nbsp;</td><td>
								<a href="javascript:goSameNameUser();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a>
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
								<TD width="120" class="ltb_head" style="border-top:none;border-bottom: #ADBED7 1px solid;"><spring:message code="approval.table.title.dept" /></TD>
								<TD width="100" class="ltb_head" style="border-left:1pt solid #ADBED7;border-bottom: #ADBED7 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
								<TD class="ltb_head"  style='border-left:1pt solid #ADBED7;border-bottom: #ADBED7 1px solid;'><spring:message code="approval.table.title.name" /></TD>
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