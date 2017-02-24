<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : selectAuthorityPop.jsp 
 *  Description : 부서 권한 등록(팝업)
 *  Modification Information 
 * 
 *   수 정 일 : 2011.07.22 
 *   수 정 자 : 신경훈
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  skh0204
 *  @since 2011. 07. 22 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType);//부서추가
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType);//추가
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType);//변경
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType);//삭제
	//String selectAutorityBtn = messageSource.getMessage("bind.button.select.authority", null, langType);//권한 선택

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("result");
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	String usingType = (String)request.getAttribute("usingType");
	
	String COMP_ID  = (String) session.getAttribute("COMP_ID");
	
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));
	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
	
	String tbColor = "#E3E3E3";
	
	String imagePath = webUri + "/app/ref/image";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="bind.title.authority" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
var gSaveObject = g_selector();
var gSaveLineObject = g_selector();
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";

var usingType = '<%=usingType%>'; //사용대상유형
var isCtrl = false, isShift = false; //keyChecked

var role_ceo = '<%=AppConfig.getProperty("role_ceo","","role")%>'; //CEO 코드
var chkAll = 0;

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
				icon: {
					valid_children : [ "comp" ],
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
			}
		}
	});

	<c:choose>
		<c:when test='${PART_ID != ""}'>
			$.tree.focused().select_branch('#${DEPT_ID}');
			$.tree.focused().open_branch('#${DEPT_ID}');
			$.tree.focused().select_branch('#${PART_ID}');
		</c:when>
		<c:otherwise>
			$.tree.focused().select_branch('#${DEPT_ID}');
		</c:otherwise>	
	</c:choose>
	//lineInit(true);

	//if (opener != null && opener.getMode() && opener.getMode() == "U") {
		setPubReader();
	//}
	dispAuthority();
});

/* function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
	if(! bUse){
		usrRow.trigger('dblclick');
		usrRow.children()[0].focus();
	}
} */

//----------------------------부서 트리 관련 이벤트  시작-------------------------------------------//
//노트가 열렸을때 발생하는 이벤트를 처리하는 함수
var openNodeId = "";
function openNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/OrgTreeAjax.do";
	url += ("?orgID="+node.id+"&treeType=1");
	
	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});
		drawSubTree(node, results);
		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');		
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


//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	selectedDeptObject  = node;
}

function dispAuthority(){

	var authorityArr = opener.getAuthority()
	
	var tbl = $('#tbAuthority tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	
	if(authorityArr.length > 0){
		/* for(var i = 0; i < authorityArr.length; i++){
			var authority = authorityArr[i];
			var row = "<tr bgcolor='#ffffff'>";
			row += "<td width='10%' class='ltb_center'>";
			row += "<input type='checkbox'  name='checkbox' id='checkbox' value='"+authority.value
					+"' authName='"+authority.name+"'/>";
			row += "</td>";
			row += "<td class='ltb_center'>"+authority.name+"</td></tr>";
			tbl.append(row);
		} */
		for(var i = 0; i < authorityArr.length; i++){
			var authority = authorityArr[i];
			var row = "<tr bgcolor='#ffffff' id='"+authority.value+"' value='"+ authority.value+"' name='"+ authority.name;
			row += "' onclick='onListClick($(\"#"+authority.value+"\"));' ondblclick='onListDblClick($(\"#"+authority.value+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+authority.value+"\"));' >";
			row += "<td class='ltb_center'>"+authority.name+"</td></tr>";
			tbl.append(row);
		}
	} else{
		var row = "<tr bgcolor='#ffffff'>";
		row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;'colsapn='2'><spring:message code='app.alert.msg.19' /></td>";
		row += "</tr>";
		tbl.append(row);
	}		
}

//부서 정보에 해당 조직정보를 추가 한다.
function addAttrOrg(result, node){
	if(result!==null){
		node.orgCode = result.orgCode; 	    //조직 코드
		node.isDeleted = result.isDeleted; //삭제여부
		node.isInspection = result.isInspection; //감사과 여부
		node.isODCD = result.isODCD;	//문서과 여부
		node.isProxyDocHandleDept = result.isProxyDocHandleDept //대리 문서 처리과 여부
		node.orgAbbrName = result.orgAbbrName; //조직 약어명
		node.isProcess = result.isProcess; //처리과 여부
	}
}

//----------------------------부서 트리 관련 이벤트 끝-------------------------------------------//
//----------------------------사용자 테이블 이벤트 시작 -----------------------------------------//
//사원 선택시 발생하는 이벤트 
//parameter obj : 선택된 tr 의 jquery 객체이다.
function onListClick(obj){
		selectOneElement(obj)
}

//하나의 셀을 선택했을 수행하는 이벤트
//parameter obj : 선택된 tr 의 jquery 객체이다.
function selectOneElement(obj){
	$('document').empty();
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
	AddList();
}


function AddList(){
	if(gSaveObject.count() === 0){
		if(!bPop)
			alert('<spring:message code="bind.msg.not.bind.doc.authority" />');
		else
			popMsg = '<spring:message code="bind.msg.not.bind.doc.authority" />';
		return;
	}
	
	document.selection.empty();
	var pubObj = new Object();
	var treeNode = $.tree.focused().selected;
	var now=new Date();
	 	
	if(!treeNode){
		return;
	}
	
	var tbAuthorityLines = $('#tbAuthorityLines tbody');
	
	var authorityStr = "";
	var id = "";
	var deptId = "";
	var deptName = "";
	var authorityValue = "";
	
	if(selectedDeptObject != null){
		id = selectedDeptObject.getAttribute('id');
		deptId  = selectedDeptObject.getAttribute('orgId');
		deptName = selectedDeptObject.getAttribute('orgName');
	}

	var authorityValue;
	if(gSaveObject != null){
		var authority = gSaveObject.collection()[0];
		authorityValue = authority[0].getAttribute('id');
	}
	
	authorityStr += opener.getAuthorityStr(selectedDeptObject.getAttribute('orgName'), authorityValue);
							
	pubObj.pubReaderId 			= id;
	pubObj.pubReaderAuthValue	= authorityValue;
	pubObj.pubReaderAuthStr	= authorityStr;
	pubObj.pubReaderDeptId 		= deptId;
	pubObj.pubReaderDeptName 	= deptName;

	var id = pubObj.pubReaderId;

	var row = MakePubReader(id, pubObj);

	var tmpTr = $('#tbAuthorityLines tbody tr[id="tbAuthorityLines_'+id+'"]');

	if(tmpTr.length === 0){	
		tbAuthorityLines.append(row);
	}else{				
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.ckchoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.ckchoice" />';
	}
				
	document.getElementById("divPubView").scrollTop = document.getElementById("divPubView").scrollHeight;
}

// 권한 정보에 대한 Row 정보를 생성한다.
function MakePubReader(idn, recvObj){
	var id = "tbAuthorityLines_" +idn;
	var row = "<tr bgcolor='#ffffff'";
	row += "id='"					+ id						+ "' ";
	row += "pubReaderId='"			+ recvObj.pubReaderId 		+ "' ";
	row += "pubReaderAuthValue='" 		+ recvObj.pubReaderAuthValue 	+ "' "; 
	row += "pubReaderDeptId='" 		+ recvObj.pubReaderDeptId  	+ "' ";
	row += "pubReaderDeptName='" 	+ recvObj.pubReaderDeptName + "' ";
	row += " onclick='onLineClick($(\"#"+id+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+id +"\"));' >";
	row += "<td class='ltb_center'>" + recvObj.pubReaderAuthStr+ "</td>"; 
	row += "</tr>";

	return row;
}

//----------------------------사용자 테이블 이벤트 끝 -------------------------------------------//
//----------------------------권한 테이블 이벤트 시작 -----------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
}

//키를 선택했을 때 발생하는 이벤트
function onLineKeyDown(obj){
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
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneLineElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//결재라인 선택시 처리하는 함수
function selectOneLineElement(obj){

	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}else{
		if(isCtrl){
			$('document').empty();
			gSaveLineObject.add(obj, sColor);
		}

		if(isShift){
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;
			var objs = $('#tbAuthorityLines tbody').children();
			
			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === obj.attr("id")){
					num2 = i;
					break;
				}
			}

			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === gSaveLineObject.first().attr("id")){
					num1 = i;
					break;
				}
			}
			
			if(num1 >= num2){
				ktype = 1;
				for(var i = 0; i < objs.length; i++){
					if(objs[i].id === gSaveLineObject.last().attr("id")){
						num1 = i;
						break;
					}
				}
			}

			sNum = (num2 > num1? num1 : num2);
			eNum = (num2 > num1? num2 : num1);
			
			gSaveLineObject.restore();
			
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveLineObject.add(nextObj, sColor);
			}
		}
	}
}

//----------------------------수신자 테이블 이벤트 끝 -----------------------------------------//

//마우스 이동시 발생하는 이벤트
function onLineMouseMove(){
	document.selection.empty();
}

//추가(+) 버튼 클릭시
function onAddLine(){
	/* var authValue = 0;
	if(selectedDeptObject == null){
		$(":checkbox").attr("checked",false);
		alert("부서를 먼저 선택하여 주십시오!");
		return;
	}
	
	for(var i = 1 ; i < document.getElementsByName("checkbox").length ; i++){
		if(document.getElementsByName("checkbox")[i].checked){
			authValue += Number(document.getElementsByName("checkbox")[i].value);
		}	
	}
	$(":checkbox").attr("checked",false);
	
	if(authValue == 0){
		alert("권한을 선택해 주십시오! ");
		return;
	} */
	
	AddList();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	
	for(var i = 0; i < lines.length; i++){
		lines[i].remove();
	}
}

//----------------------------추가삭제위 아래이미지 버튼 이벤트 끝--------------------------------------//
//----------------------------최 하위 버튼 이벤트 시작 --------------------------------------------//

function sendOk(){
	var pubViewerline = "";
	var lines = $('#tbAuthorityLines tbody');
	//var recvMark = $('#chkDisplayAs');
	//var txtMark = $('#txtDisplayAs'); 
	
	if(lines.length === 0){
		alert('<spring:message code="approval.msg.applines.noOrgviewers" />');
		return;
	}
	
	pubViewerline = makeRecvLine(lines.children());
	if(pubViewerline === ""){
		alert('<spring:message code="approval.msg.applines.noOrgviewers" />');
		return;
	}
	//alert(pubViewerline);
	if (opener != null && opener.setOrgInfo) {
		opener.setAuthInfo(pubViewerline);
	
		window.close();
	}
}

function closePopup(){
	window.close();
}

function makeRecvLine(lines){
	
	var strRt = "";	
	for(var i = 0; i < lines.length; i++){
		var line = lines[i];
		strRt += (line.getAttribute('pubReaderId') 	+ String.fromCharCode(2) + line.getAttribute('pubReaderAuthValue') 	+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('pubReaderDeptId') + String.fromCharCode(2) + line.getAttribute('pubReaderDeptName'));
		if(i<(lines.length-1))
			strRt += String.fromCharCode(4);
	}
	return strRt;
}

function getAuthReaderList(readersinfo) {
	var readers = new Array();
	var readerinfo = readersinfo.split(String.fromCharCode(4));
	var readerlength = readerinfo.length;

	for (var loop = 0; loop < readerlength; loop++) {
		if (readerinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = readerinfo[loop].split(String.fromCharCode(2));
			var reader = new Object();
			reader.pubReaderId = info[0];
			reader.pubReaderAuthValue = info[1];
			reader.pubReaderAuthStr = opener.getAuthorityStr(info[3], info[1]);
			reader.pubReaderDeptId = info[2];
			reader.pubReaderDeptName = info[3];
			
			readers[loop] = reader;
		}
	}

	return readers;
}

//넘어온 경로를 세팅한다.
function setPubReader(){
	var tbAuthorityLines = $('#tbAuthorityLines tbody');
	
	if (opener != null && opener.getAuthInfo) {
		var readersinfo = opener.getAuthInfo();

		var objPubReaders = getAuthReaderList(readersinfo)
		var loop = objPubReaders.length;
			
		for(var i = 0; i < loop; i++){
			var objPubReader = objPubReaders[i];
			var id = objPubReader.pubReaderId;
			var row = MakePubReader(id, objPubReader);
			tbAuthorityLines.append(row);
		}
		
	}
	
}
//----------------------------최 하위 버튼 이벤트 끝 --------------------------------------------//

var bPop = false;
var popMsg = "";


var searchDepts = "";

function goSearchDept(){
	var objDept = $('#searchDept');	
	objDept.val($('#userName').val());

	if(objDept.val() === ""){
		alert("<spring:message code='common.msg.deptnm.no' />"); //common.msg.deptnm.no
		return;
	}

	var url = "<%=webUri%>/app/common/searchDept.do?method=1"
	url += "&searchType=1";
	
	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,objDept.serialize() ,function(data){
		results = data;
	});

	if(results != ""){
		if(results.length === 1){
			selectOwnDept(results[0]);
			return;
		}else{
			searchDepts = results;
			goSearchList();
			return;
		}
	}else{
		alert("<spring:message code='app.alert.msg.56'/>");
		objDept.val("");
		$('#userName').val("")
		return;
	}
}

function getSearchDept(){
	return searchDepts;
}

var ownDeptId = "";
function selectOwnDept(objDept){
	var url = "<%=webUri%>/app/common/searchDept.do?method=2";
	url += "&compId=" + objDept.companyID;
	url += "&deptId=" + objDept.orgID;

	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,"",function(data){
		results = data;
	});

	if(results != ""){
		var rsCnt = results.length;
		
		for(var i = 0; i < rsCnt; i++){
			var node = $('#'+results[i].orgID);
			openNode(node[0], $.tree.focused());

			if(i === (rsCnt -1)){
				ownDeptId = '#' + results[i].orgID;
				$.tree.focused().scroll_into_view(ownDeptId);
				$.tree.focused().select_branch(ownDeptId);			
			}
		}
	}
}

function goSearchList(){
	var width = 400;
	var height = 330;
	var url = "<%=webUri%>/app/common/searchDept.do?method=3";
	url += "&searchType=1";
	
	var appDoc = null;
	appDoc = openWindow("searchDeptName", url, width, height); 
}

function goSearch(){
	var searchType = $('#searchType');
	if(searchType.val() === "1"){
		goSearchDept();
	}
}

//체크박스 전부체크
/* function checkAll() {
    if($(":checkbox").length <= 0) return;
    if(chkAll==0){
        chkAll = 1;
        $(":checkbox").attr("checked",true);

    }else{
        chkAll = 0;
        $(":checkbox").attr("checked",false);
    }
} */

--></script>
<style>
	.tdSelect{
		background-color : #F9E5DF;
	}
	
	.scrollbox {
		width:99%; height:99%; overflow:auto; padding:2px; /* border:1px; border-style:solid; border-color:#DDDDDD; */
		scrollbar-face-color: #EEEEEE;
		scrollbar-highlight-color: #EEEEEE;
		scrollbar-3dlight-color: #AAAAAA;
		scrollbar-shadow-color: #AAAAAA;
		scrollbar-darkshadow-color: #EEEEEE;
		scrollbar-track-color: #DDDDDD;
		scrollbar-arrow-color: #666666;
	}
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
		<acube:titleBar><spring:message code="bind.title.authority" /></acube:titleBar>
	         <!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<!-- 그룹명/검색  -->	
			<acube:tableFrame border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
		    	<td height="3" background="<%=imagePath%>/dot_search1.gif"></td>
		    </tr>
			<tr>		
				<td width="49%">
					<acube:tableFrame cellspacing="0" class="">
					<tr class="search" height="36">
						<td class="search_t" width="70">
						<select id="searchType" name="searchType">
							<option value="1"><spring:message code="bind.obj.dept.name" /></option>
						</select>
						</td>			
						<td width="30%"><input id="userName" name="userName" type="text" class="input" style="width:85%;height:15px;ime-mode:active;" />
						<input id="searchDept" name="searchDept" type="hidden" />
						</td>
						<td width="*" align="left"><a href="javascript:goSearch();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
					</tr>
					</acube:tableFrame>
				</td>
			</tr>
			<tr>
		    	<td height="3" background="<%=imagePath%>/dot_search1.gif"></td>
		    </tr>	
			</acube:tableFrame>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 대내 -->
		<div id="divInternal" style="height:200px;">
			<!-- 부서 트리 시작 -->
			<div  id="divDept"  style="float:left; width:49%; ">
			 <!-- 트리용 테이블  -->
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="200" class="basic_box">
				<div id="scrollbox" class="scrollbox">
				<div id="org_tree" style="height:200; /* overflow:auto; */ background-color : #FFFFFF;">
			    <ul>
			    	<%
			    	int nSize = results.size();
			    	String rootOrgId = "";
			    	String rootOrgNm = "";
			    	for(int i = 0; i < nSize; i++){
			    		DepartmentVO result = results.get(i);
			    		String _class = "class='closed'";
			    		String rel = "";
			    		StringBuffer Li = new StringBuffer();
			    		
			    		boolean hasChild = false;
			    		
			    		if(result.getOrgType() == 1){
				    		rootOrgId = result.getOrgID();
				    		rootOrgNm = result.getOrgName();
			    		}
			    		
			    		if(i < (nSize-1)){
			    		    //자식존재여부
				    		hasChild = result.getOrgID().equals(results.get(i+1).getOrgParentID());	    		
						}
			    		
			    		if(result.getOrgType()== 0 ) {
							_class = "class='open'";
							rel = " rel='root' ";
			    		}else if(result.getOrgType() == 1){
						    _class = "class='closed'";
						    rel = " rel='comp' ";
						}else if(result.getOrgType() == 2){
						    _class = "class='closed'";
						    rel = " rel='dept' ";
						}else{
						    _class = "class='closed'";
						    rel = " rel='part' ";
						}	
			    		
			    		Li.append("<li id='"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID());
			    		Li.append("' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() );
			    		Li.append("' rootOrgId='"+rootOrgId+"' rootOrgName='"+rootOrgNm+"' addrSymbol='"+result.getAddrSymbol()+"' ");
			    		Li.append("' orgCode='' isDeleted='' isInspection='' isODCD='' isProxyDocHandleDept='' orgAbbrName='' isProcess='' ");
			    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
			    		//out.println("<li id='"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() +"' rootOrgId='"+rootOrgId+"' rootOrgName='"+rootOrgNm+"' addrSymbol='"+result.getAddrSymbol()+"' "+_class+"><a href='#'><ins></ins>"+result.getOrgName()+"</a>");
			    		out.println(Li.toString());
			    		
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
				</div>
				</td>
			</tr>
			</acube:tableFrame><!-- 트리용 테이블 -->
			</div><!-- 부서 트리 끝-->
			<!-- 권한 목록 시작 -->
			<div id="divAutority" style="float:right; width:49%; ">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="167" class="basic_box" style="padding:3 3 3 3;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr><td>
				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="1">
					<thead>
						<TR colsapn='2'>
							<TD class="ltb_head"><spring:message code="bind.tab.authority" /></TD>
						</TR>
					</thead>
				</table>
				<div style="height:156px; overflow-x:hidden; overflow-y:auto; background-color:#FFFFFF;">
				<table id="tbAuthority" width="100%" border="0" cellpadding="0" cellspacing="1" style="table-layout:fixed;background-color:#cccccc;">
					<tbody />
				</table>
				</div>
				</td>
				</tr>
				</table>
			</td>
			</tr>
			</acube:tableFrame>
			</div><!-- 권한 목록 시작 -->
		</div>
		<!-- ------------------------대내 끝------------------------ -->
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<div style="width:100%;">
			<center>
				<table border="0" cellspacing="0" cellpadding="0">
				<tr><td>
					<acube:buttonGroup align="right">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
				</td></tr></table>
			</center>
			</div>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 선택된 권한 목록 -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<acube:tableFrame class="" width="100%" border="0" cellpadding="1" cellspacing="0" >
						<tr>
							<td>
							<div>
								<table class="table" width="100%" border="0" cellpadding="0" cellspacing="1">
									<thead>
										<tr>
											<TD width="287" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="bind.tab.authority"/></TD>
										</tr>
									</thead>
								</table>
							</div>
							<div id="divPubView" style="height:120px; overflow-x:hidden; overflow-y:auto; background-color:#FFFFFF;">
								<table id="tbAuthorityLines" width="100%" border="0" cellpadding="0" cellspacing="1" style="table-layout:fixed;background-color:#cccccc;">
									<tbody />
								</table>
							</div>
							</td>
						</tr>
					</acube:tableFrame>
				</td>
			</tr>
		</table>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<acube:buttonGroup align="right">
		<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=confirmBtn%>" />
		<acube:space between="button" />
		<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn%>" />
		</acube:buttonGroup>
	</acube:outerFrame>
	<div id="divPop" style="display: none;position: absolute;">
	<table id="sameUser">
		<tbody></tbody>
	</table>
	</div>
</body>
</html>