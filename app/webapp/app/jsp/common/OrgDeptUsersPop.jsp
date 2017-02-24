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
	
	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("results");	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	
	String linetype = request.getParameter("linetype");
	linetype = (linetype == null ? "1" : linetype);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="common.title.userlist" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
var isCtrl = false, isShift = false; //keyChecked
var gSaveObject = g_selector();
var sColor = "#F2F2F4";

$(document).ready(function() { 
	$("#org_tree").tree({
		rules : {
		valid_children : [ "root" ]
		},
		types : {
			// all node types inherit the "default" node type
			"default" : {
				deletable : false,
				renameable : false,
				draggable : false
			},
			"root" : {
				valid_children : [ "comp" ],
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
				}
			},
	
			"comp" : {
				icon: {
				valid_children : [ "dept" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"
	
				}
			},
	
			"dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/dept.gif"	
				}
			},
			"part" : {
				icon: {
				valid_children : [ "none" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/part.gif"	
				}
			}			
		},
		callback : {
			//노드가 OPEN 될때
			onopen: function(node, tree_obj) {
				openNode(node, tree_obj);
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				selectNode(node, tree_obj);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
				//alert("aaaaaaa");
			},
			ondblclk : function(node, tree_obj) { 
				dbclickNode(node, tree_obj);
			}	
		}
	});
	
	$.tree.focused().select_branch('#${userProfile.compId}');
	
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

//동명이인검색 추가
$('#userName').live('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 13){
		goSameNameUser();
	}
});

//------------------------트리 이벤트 시작--------------------------------------------------------
//노트가 열렸을때 발생하는 이벤트를 처리하는 함수
function openNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/OrgTreeAjax.do";
	url += ("?orgID="+node.id+"&treeType=1");
	
	if(nSize < 1){
		var results = null;
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});	
		drawSubTree(node, results);
		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');	
		//$.tree.focused().open_branch("#+node.id+");	
	}
}

//하위 노드를 그리는 함수
function drawSubTree(node, nodeObj){
	var t = $.tree.focused();

	for(var i=0; i < nodeObj.length; i++) {
		try {
			var rel = "";
			
			if(nodeObj[i].orgType === 0){
				rel="root";
			}else if(nodeObj[i].orgType === 1){
				rel="comp";
			}else if(nodeObj[i].orgType === 2){
				rel="dept";
			}else{
				rel="part";
			}
			
			t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, "rel":rel},
				data:{title:nodeObj[i].orgName}}, '#'+nodeObj[i].orgParentID);	
		} catch(e) {}
	}
}
//노드 더블클릭시 리턴
var gDeptObj = null;
var gUserObj = null;
function dbclickNode(node, tree_obj){
	sendOk();
}

//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	url += ("?orgID="+node.id+"&orgType="+node.getAttribute("orgType"));
	
	var results = null;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	
	insertUser(results);

	url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	url += "?deptID="+node.id;
	$.getJSON(url,function(data){
		results = data;
	});
	addAttrOrg(results, node);

	gDeptObj = getDeptInfo(node, tree_obj);
}

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	for(var i = 0; i < results.length; i++){
		var user = results[i];
		var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
		row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
		row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
		row += "' rowOrd='"+ i;
		row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";
		var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
		row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;' width='100'>"+userPosition+"</td>";
		row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;border-right:1pt solid #ADBED7;'>"+user.userName+"</td></tr>";
		tbl.append(row);
	}		
}

//부서 정보에 해당 조직정보를 추가 한다.
function addAttrOrg(result, node){
	if(result!==null){
		node.zipCode =  result.zipCode;
		node.telephone =  result.telephone;
		node.roleCodes =  result.roleCodes;
		node.reserved3 =  result.reserved3;
		node.reserved2 =  result.reserved2;
		node.reserved1 =  result.reserved1;
		node.proxyDocHandleDeptCode =  result.proxyDocHandleDeptCode;
		node.outgoingName =  result.outgoingName;
		node.orgType =  result.orgType;
		node.orgParentID =  result.orgParentID;
		node.orgOtherName =  result.orgOtherName;
		node.orgOrder =  result.orgOrder;
		node.orgName =  result.orgName;
		node.orgID =  result.orgID;
		node.orgCode =  result.orgCode;
		node.orgAbbrName =  result.orgAbbrName;
		node.oDCDCode =  result.oDCDCode;
		node.isProxyDocHandleDept =  result.isProxyDocHandleDept;
		node.isProcess =  result.isProcess;
		node.isODCD =  result.isODCD;
		node.isInstitution =  result.isInstitution;
		node.isInspection =  result.isInspection;
		node.isDeleted =  result.isDeleted;
		node.institutionDisplayName =  result.institutionDisplayName;
		node.homepage =  result.homepage;
		node.formBoxInfo =  result.formBoxInfo;
		node.fax =  result.fax;
		node.email =  result.email;
		node.description =  result.description;
		node.companyID =  result.companyID;
		node.chiefPosition =  result.chiefPosition;
		node.addrSymbol =  result.addrSymbol;
		node.addressDetail =  result.addressDetail;
		node.address =  result.address;
	}
}

//로그인 사용자 정보 초기화 시킨다.
function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
	if(! bUse){
		usrRow.trigger('dblclick');
		usrRow.children()[0].focus();
	}
}
//사원 선택시 발생하는 이벤트 
// parameter obj : 선택된 tr 의 jquery 객체이다.
function onListClick(obj){
		selectOneElement(obj)
		gUserObj = getUserInfo();
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
	//alert(user.userId + ":"	+ user.userName + ":"	+ user.deptId	+ ":" + user.deptName + ":"	+ user.compId + ":"	+ user.compName + ":"	+ user.positionCode + ":" + user.positionName + ":"	+ user.titleName + ":" + user.gradeCode + ":" + user.gradeName );
	var rtnObj = null;
	
	if(gUserObj != null){
		rtnObj = gUserObj;
	}else{
		rtnObj = gDeptObj;
	}
	
	if (opener != null && opener.setUserInfo) {
		opener.setUserInfo(rtnObj);
		window.close();
	}	
}
function getDeptInfo(node, tree_obj)
{
	var obj = new Object();

	if(node !== null){
		obj.zipCode =  node.zipCode;
		obj.telephone =  node.telephone;
		obj.roleCodes =  node.roleCodes;
		obj.reserved3 =  node.reserved3;
		obj.reserved2 =  node.reserved2;
		obj.reserved1 =  node.reserved1;
		obj.proxyDocHandleDeptCode =  node.proxyDocHandleDeptCode;
		obj.outgoingName =  node.outgoingName;
		obj.orgType =  node.orgType;
		obj.orgParentId =  node.orgParentID;
		obj.orgOtherName =  node.orgOtherName;
		obj.orgOrder =  node.orgOrder;
		obj.orgName =  node.orgName;
		obj.orgId =  node.orgID;
		obj.orgCode =  node.orgCode;
		obj.orgAbbrName =  node.orgAbbrName;
		obj.oDCDCode =  node.oDCDCode;
		obj.isProxyDocHandleDept =  node.isProxyDocHandleDept;
		obj.isProcess =  node.isProcess;
		obj.isODCD =  node.isODCD;
		obj.isInstitution =  node.isInstitution;
		obj.isInspection =  node.isInspection;
		obj.isDeleted =  node.isDeleted;
		obj.institutionDisplayName =  node.institutionDisplayName;
		obj.homepage =  node.homepage;
		obj.formBoxInfo =  node.formBoxInfo;
		obj.fax =  node.fax;
		obj.email =  node.email;
		obj.description =  node.description;
		obj.companyId =  node.companyID;
		obj.chiefPosition =  node.chiefPosition;
		obj.addrSymbol =  node.addrSymbol;
		obj.addressDetail =  node.addressDetail;
		obj.address =  node.address;

		obj.userId 		= "";
		obj.userName 		= "";
		obj.deptId 		= "";
		obj.deptName 		= "";
		obj.compId 		= "";
		obj.compName 		= "";
		obj.positionCode 	= "";
		obj.positionName 	= "";
		obj.titleName 		= "";
		obj.gradeCode 		= "";
		obj.gradeName 		= "";	
	}else{
		obj.zipCode = '';
		obj.telephone = '';
		obj.roleCodes = '';
		obj.reserved3 = '';
		obj.reserved2 = '';
		obj.reserved1 = '';
		obj.proxyDocHandleDeptCode = '';
		obj.outgoingName = '';
		obj.orgType = '';
		obj.orgParentId = '';
		obj.orgOtherName = '';
		obj.orgOrder = '';
		obj.orgName = '';
		obj.orgId = '';
		obj.orgCode = '';
		obj.orgAbbrName = '';
		obj.oDCDCode = '';
		obj.isProxyDocHandleDept = '';
		obj.isProcess = '';
		obj.isODCD = '';
		obj.isInstitution = '';
		obj.isInspection = '';
		obj.isDeleted = '';
		obj.institutionDisplayName = '';
		obj.homepage = '';
		obj.formBoxInfo = '';
		obj.fax = '';
		obj.email = '';
		obj.description = '';
		obj.companyId = '';
		obj.chiefPosition = '';
		obj.addrSymbol = '';
		obj.addressDetail = '';
		obj.address = '';

		obj.userId 		= "";
		obj.userName 		= "";
		obj.deptId 		= "";
		obj.deptName 		= "";
		obj.compId 		= "";
		obj.compName 		= "";
		obj.positionCode 	= "";
		obj.positionName 	= "";
		obj.titleName 		= "";
		obj.gradeCode 		= "";
		obj.gradeName 		= "";
	}
	gUserObj = null;
	return obj;
}

function getUserInfo(){
	var trUser = gSaveObject.first();

	var obj = new Object();

	if(trUser !== null){
		obj.userId 		= trUser.attr('Id');
		obj.userName 		= trUser.attr('userName');
		obj.deptId	 		= trUser.attr('deptID');
		obj.deptName 		= trUser.attr('deptName');
		obj.compId 		= trUser.attr('compID');
		obj.compName 		= trUser.attr('compName');
		obj.positionCode 	= trUser.attr('positionCode');
		obj.positionName 	= trUser.attr('positionName');
		obj.titleName 		= trUser.attr('titleName');
		obj.gradeCode 		= trUser.attr('gradeCode');
		obj.gradeName 		= trUser.attr('gradeName');

		obj.zipCode = '';
		obj.telephone = '';
		obj.roleCodes = '';
		obj.reserved3 = '';
		obj.reserved2 = '';
		obj.reserved1 = '';
		obj.proxyDocHandleDeptCode = '';
		obj.outgoingName = '';
		obj.orgType = '';
		obj.orgParentId = '';
		obj.orgOtherName = '';
		obj.orgOrder = '';
		obj.orgName = '';
		obj.orgId = '';
		obj.orgCode = '';
		obj.orgAbbrName = '';
		obj.oDCDCode = '';
		obj.isProxyDocHandleDept = '';
		obj.isProcess = '';
		obj.isODCD = '';
		obj.isInstitution = '';
		obj.isInspection = '';
		obj.isDeleted = '';
		obj.institutionDisplayName = '';
		obj.homepage = '';
		obj.formBoxInfo = '';
		obj.fax = '';
		obj.email = '';
		obj.description = '';
		obj.companyId = '';
		obj.chiefPosition = '';
		obj.addrSymbol = '';
		obj.addressDetail = '';
		obj.address = '';
	}else{
		obj.userId 		= "";
		obj.userName 		= "";
		obj.deptId 		= "";
		obj.deptName 		= "";
		obj.compId 		= "";
		obj.compName 		= "";
		obj.positionCode 	= "";
		obj.positionName 	= "";
		obj.titleName 		= "";
		obj.gradeCode 		= "";
		obj.gradeName 		= "";

		obj.zipCode = '';
		obj.telephone = '';
		obj.roleCodes = '';
		obj.reserved3 = '';
		obj.reserved2 = '';
		obj.reserved1 = '';
		obj.proxyDocHandleDeptCode = '';
		obj.outgoingName = '';
		obj.orgType = '';
		obj.orgParentId = '';
		obj.orgOtherName = '';
		obj.orgOrder = '';
		obj.orgName = '';
		obj.orgId = '';
		obj.orgCode = '';
		obj.orgAbbrName = '';
		obj.oDCDCode = '';
		obj.isProxyDocHandleDept = '';
		obj.isProcess = '';
		obj.isODCD = '';
		obj.isInstitution = '';
		obj.isInspection = '';
		obj.isDeleted = '';
		obj.institutionDisplayName = '';
		obj.homepage = '';
		obj.formBoxInfo = '';
		obj.fax = '';
		obj.email = '';
		obj.description = '';
		obj.companyId = '';
		obj.chiefPosition = '';
		obj.addrSymbol = '';
		obj.addressDetail = '';
		obj.address = '';
	}

	return obj;
}

function closePopup(){
	window.close();
}

//동명이인 검색
var sameUsers = "";
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
		sameUsers = results
		if(results.length == 0) {
			alert("<spring:message code='app.alert.msg.56'/>");
			$('#userName').val("");
		}else if(results.length == 1){
			makeSameUser(results[0]);
		}else{
			var width = 400;
			var height = 330;
			var url = "<%=webUri%>/app/common/sameNameUsers.do?method=2";
			var appDoc = null;
			appDoc = openWindow("sameUserWin", url, width, height); 
		}
	}		
}

//동명이인 검색
function getSameUsers(){
	return sameUsers;
}
//동명이인 검색
var popMsg = "";
function setSameUsers(user){
	makeSameUser(user);
	return popMsg;
}

function makeSameUser(user){
	var tbl = $('#sameUser tbody');
	tbl.empty();
	
	var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
	row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
	row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
	row += "' >";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: #ADBED7 1px solid;border-left:1pt solid #ADBED7;border-right:1pt solid #ADBED7;'>"+user.userName+"</td></tr>";
	tbl.append(row);

	var tRow = $('#sameUser tbody tr[id='+user.userUID+']');

	gSaveObject.restore();
	gSaveObject.add(tRow, sColor);

	gUserObj = getUserInfo();
	sendOk();

	$('#userName').val("");
}
--></script>
<style>
	.tdSelect{
		background-color : #F9E5DF;
	}
</style>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
	<acube:outerFrame>

	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code="common.title.userlist" /></span></td>
		</tr>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 검색  -->	
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td class="tb_tit" width="50">사용자명</td>				
			<td class="tb_tit" width="28%"><input id="userName" name="userName" type="text" style="width: 100%;ime-mode:active;FONT-SIZE:9pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#777777; background-color:#FFFFFF; height:16px; padding-left:3px; padding-right:3px; border:1px #C3C2C2 solid;" /></td>
			<td class="tb_tit" width="10%"><a href="javascript:goSameNameUser();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
			<td class="tb_tit">&nbsp;</td>
		</tr>
		</acube:tableFrame>
	
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td width="48%">
			 <!-- 트리용 테이블  -->
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="170" class="basic_box">
				<div id="org_tree" style="height:170px; overflow:auto; background-color : #FFFFFF;">
			    <ul>
			    	<%
			    	int nSize = results.size();
			    	for(int i = 0; i < nSize; i++){
			    		DepartmentVO result = results.get(i);
			    		String _class = "class='closed'";
			    		String rel = "";
			    		boolean hasChild = false;
			    		
			    		if(i < (nSize-1)){
			    		    //자식존재여부
				    		hasChild = result.getOrgID().equals(results.get(i+1).getOrgParentID());	    		
						}

			    		if(result.getOrgType()== 0 ) {
							_class = "class='open'";
							rel = " rel='root' ";
			    		}else if(result.getOrgType() == 1){
						    _class = "class='open'";
						    rel = " rel='comp' ";
						}else  if(result.getOrgType() == 2){
						    _class = "class='closed'";
						    rel = " rel='dept' ";
						}else{
						    _class = "class='closed'";
						    rel = " rel='part' ";
						}
			    		
			    		out.println("<li id='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() +"' "+_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
			    		
			    		if(hasChild){//자식이있으면
			    		    out.println("<ul>");
			    		}
			    		
			    		
			    		if(i <= (nSize -2)){
			    		   
			    		   if(!hasChild && !result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
			    		   		//out.println("</li>\n</ul>\n</li>");
							   int deptSize = results.get(i+1).getDepth() - result.getDepth();
			    			   out.println("</li>");
			    			   for(int nLi = 0; nLi < deptSize; nLi++){
			    				   out.println("\n</ul>\n</li>");
			    			   }
			    		   }
			    		   
			    		   if(!hasChild && result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
			    		   		out.println("</li>");
			    		   }
			    		}
			    		
			    		if(i == (nSize-1)){
				    		if(!results.get(0).getOrgParentID().equals(results.get(i).getOrgParentID())){
				    			out.println("</li>\n</ul>\n</li>");
				    		}else{
				    			out.println("</li>");
				    		}
			    		}
			    	}
			    	%>
			    </ul>
				</div>				
				</td>
			</tr>
			</acube:tableFrame><!-- 트리용 테이블 -->
			</td>
			<td width="3%" style="background-color : #FFFFFF;"></td>
			<td>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
			<tr>
				<td height="190" class="basic_box" style="padding:0px; margin:0px;">
				<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<TR><TD width="100" class="ltb_head" style="border-top:none;border-bottom: #ADBED7 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
							<TD class="ltb_head"  style='border-left:1pt solid #ADBED7;border-bottom: #ADBED7 1px solid;'><spring:message code="approval.table.title.name" /></TD>
						</TR>
					</thead>
				</table>
				</div>
				<div style="height:162px; overflow-x:hidden; overflow-y:scroll; background-color : #FFFFFF;">
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
	<div id="divPop" style="display: none;position: absolute;">
	<table id="sameUser">
		<tbody></tbody>
	</table>
	</div>
</body>	
</html>