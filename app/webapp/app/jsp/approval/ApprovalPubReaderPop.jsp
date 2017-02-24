<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ApprovalPubInspectPop.jsp 
 *  Description : 공람경로
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.18 
 *   수 정 자 : 장진홍
 *   수정내용 : 수정내용 : 2011.07.25 신경훈  - 공람자그룹 탭 추가 
 * 
 *  @author  장진홍
 *  @since 2011. 04. 18 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType);//부서추가
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType);//추가
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType);//변경
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType);//삭제
	
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

	String COMP_ID  = (String) session.getAttribute("COMP_ID");
	
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));
	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
			
	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("result");
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	String usingType = (String)request.getAttribute("usingType");
	String tbColor = "#E3E3E3";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.apppublic" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
var gSaveObject = g_selector(); //사용자 목록
var gSaveLineObject = g_selector(); //공람목록
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";

var usingType = '<%=usingType%>'; //사용대상유형
var isCtrl = false, isShift = false; //keyChecked

var role_ceo = '<%=AppConfig.getProperty("role_ceo","","role")%>'; //CEO 코드
var role_officer = '<%=AppConfig.getProperty("role_officer","","role")%>'; //임원 코드 

var initPubCnt = 0;



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

	lineInit(true);
	setPubReader();
});

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
		goSearch();
	}
});

function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
	if(! bUse){
		usrRow.trigger('dblclick');
		usrRow.children()[0].focus();
	}
	
}

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
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	url += ("?orgID="+node.id+"&orgType="+node.getAttribute('orgType'));
	
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
	gSaveObject.restore();
}

var teamIds = "";
var teamNames = "";
//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	teamIds = new Array();
	teamNames = new Array();
	
	tbl.empty();
	if(results.length > 0){
		for(var i = 0; i < results.length; i++){
			var user = results[i];
			var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' roleCodes='"+ user.roleCodes + "' rowOrd='"+ i + "' userOrder='"+ user.userOrder;
			row += "' reserved1='"+ user.reserved1 + "' reserved3='"+ user.reserved3;
			row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' class='body' >";
			var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
			row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='143'>"+userPosition+"</td>";
			row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+";'>"+user.userName+"</td></tr>";
			
			tbl.append(row);
			
		}
	}else{
		var row = "<tr>";
		row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;'colsapn='2'><spring:message code='app.alert.msg.19' /></td>";
		row += "</tr>";
		tbl.append(row);
	}

	var strTeam = "&nbsp;";
	for(var i = 0; i < teamIds.length; i++){
		if(i == 0) strTeam = "";
		strTeam += "<a href='javascript:addTeam(\""+ teamIds[i] +"\");' class='btn_text'>"+teamNames[i]+"</a>";
		if(i < (teamIds.length- 1)){
			strTeam += "&nbsp;";
		}
	}
	$('#divReserved').html(strTeam);
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
	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveObject.restore();
		gSaveObject.add(obj, sColor);
	}else{
		if(isCtrl){//CTRL
			$('document').empty();
			gSaveObject.add(obj, sColor);
		}

		if(isShift){//SHIFT
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;

			num1 = new Number(gSaveObject.first().attr("rowOrd"));			
			num2 = new Number(obj.attr("rowOrd"));
			
			if(num1 >= num2){
				ktype = 1;
				num1 = new Number(gSaveObject.last().attr("rowOrd"));
			}

			sNum = (num2 > num1? num1 : num2);
			eNum = (num2 > num1? num2 : num1);
			
			var objs = $('#tbUsers tbody').children();
			gSaveObject.restore();
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveObject.add(nextObj, sColor);
			}	

			gSaveObject.setType(ktype);	

			if(ktype == 0){	
				gSaveObject.last().children()[0].focus();
			}else{
				gSaveObject.first().children()[0].focus();
			}
		}
	}
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

//목록 추가 부분
function AddList(){
	document.selection.empty();
	var pubObj = new Object();
	var treeNode = $.tree.focused().selected;
	var now=new Date();
	 	
	if(!treeNode){
		return;
	}
	
	var tbRecvLines = $('#tbRecvLines tbody');

	var selPubCnt = 0;

	if(gSaveObject.count() === 0){
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.cknochoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.cknochoice" />';
		return;
	}
	
	if(gSaveObject.count() > 0){
		for(var i = 0; i < gSaveObject.count(); i++){
			var user = gSaveObject.collection()[i];	

			//if('${userProfile.userUid}' !== user.attr('id')){						
			pubObj.pubReaderId 			= user.attr('id');
			pubObj.pubReaderName 		= user.attr('userName');
			pubObj.pubReaderPos 		= user.attr('positionName');
			pubObj.pubReaderDeptId 		= user.attr('deptID');
			pubObj.pubReaderDeptName 	= user.attr('deptName');
			pubObj.pubReaderRole 		= "";
			pubObj.readDate				= "";
			pubObj.pubReadDate			= "";
			pubObj.registerId			= "";
			pubObj.usingType			= usingType;
			pubObj.pubReaderOrder       = user.attr('userOrder');

			var id = pubObj.pubReaderId;

           if(user.attr("roleCodes").indexOf(role_ceo) !== -1){
 				if(pubObj.pubReaderRole != ""){
 					pubObj.pubReaderRole += "^";
  				}
 				pubObj.pubReaderRole += role_ceo; //CEO
           }

           if(user.attr("roleCodes").indexOf(role_officer) !== -1){
  				if(pubObj.pubReaderRole != ""){
  					pubObj.pubReaderRole += "^";
  				}
  				pubObj.pubReaderRole += role_officer; //임원
          }

 			var row = MakePubReader(id, pubObj);

			var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');

			if(tmpTr.length === 0){	
				tbRecvLines.append(row);
				selPubCnt++;
			}else{				
				if(!bPop)
					alert('<spring:message code="approval.msg.applines.ckchoice" />');
				else
					popMsg = '<spring:message code="approval.msg.applines.ckchoice" />';
			}
			/*
			}else{
				if(!bPop)
					alert('<spring:message code="approval.msg.applines.no.pub" />');
				else
					popMsg = '<spring:message code="approval.msg.applines.no.pub" />';
			}*/
		}

		
	}
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;

	// 공람자 추가할때 총 합을 셋팅
	var pubReaderCnt = parseInt($("#pubReaderCntForm").val()) + selPubCnt;
	$("#pubReaderCnt").text(pubReaderCnt);
	$("#pubReaderCntForm").val(pubReaderCnt);
}

// 공람자 정보에 대한 Row 정보를 생성한다.
function MakePubReader(idn, recvObj){
	//alert(recvObj.pubReaderOrder);
	var id = "tbRecvLines_" +idn;
	var row = "<tr ";
	row += "id='"					+ id						+ "' ";
	row += "pubReaderId='"			+ recvObj.pubReaderId 		+ "' ";
	row += "pubReaderName='" 		+ recvObj.pubReaderName 	+ "' "; 
	row += "pubReaderPos='" 		+ recvObj.pubReaderPos    	+ "' ";
	row += "pubReaderDeptId='" 		+ recvObj.pubReaderDeptId  	+ "' ";
	row += "pubReaderDeptName='" 	+ recvObj.pubReaderDeptName + "' ";
	row += "pubReaderRole='" 		+ recvObj.pubReaderRole 	+ "' ";
	row += "readDate='" 			+ recvObj.readDate   		+ "' ";
	row += "pubReadDate='" 			+ recvObj.pubReadDate    	+ "' ";
	row += "registerId='" 			+ recvObj.registerId    	+ "' ";
	row += "usingType='" 			+ recvObj.usingType  		+ "' ";
	row += "pubReaderOrder='" 		+ recvObj.pubReaderOrder	+ "' ";
	row += " onclick='onLineClick($(\"#"+id+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+id +"\"));' >";
	var pubReaderDeptName = "&nbsp;";
	if(recvObj.pubReaderDeptName !== "" ) pubReaderDeptName = recvObj.pubReaderDeptName;
	row += "<td width='163' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid'>" +pubReaderDeptName + "</td>"; 
	var pubReaderPos = "&nbsp;";
	if(recvObj.pubReaderPos !== "" ) pubReaderPos = recvObj.pubReaderPos;
	row += "<td width='163' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+"'>" +pubReaderPos + "</td>"; 
	var pubReaderName = "&nbsp;";
	if(recvObj.pubReaderName !== "" ) pubReaderName = recvObj.pubReaderName;
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-right:1pt solid "+tbColor+"'>" +pubReaderName+ "</td>"; 
	row += "</tr>";

	return row;
}

//----------------------------사용자 테이블 이벤트 끝 -------------------------------------------//
//----------------------------공람자 테이블 이벤트 시작 -----------------------------------------//
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
			var objs = $('#tbRecvLines tbody').children();
			
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

//마우스 이동시 발생하는 이벤트
function onLineMouseMove(){
	document.selection.empty();
}
//----------------------------수신자 테이블 이벤트 끝 -----------------------------------------//
//----------------------------추가삭제위 아래 이미지 버튼 이벤트 끝 ------------------------------//
//추가(+) 버튼 클릭시
function onAddLine(){
	AddList();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	var removePubCnt = 0; 
	
	for(var i = 0; i < lines.length; i++){
		lines[i].remove();
		removePubCnt++;
	}

	gSaveLineObject.restore();

	// 공람자 삭제할때 총 합을 셋팅
	var pubReaderCnt = parseInt($("#pubReaderCntForm").val()) - removePubCnt;
	if(pubReaderCnt < 0){
		pubReaderCnt = 0;
	}  
	$("#pubReaderCnt").text(pubReaderCnt);
	$("#pubReaderCntForm").val(pubReaderCnt);

	
}

//----------------------------추가삭제위 아래이미지 버튼 이벤트 끝--------------------------------------//
//----------------------------최 하위 버튼 이벤트 시작 --------------------------------------------//
function sendOk(){
	var appline = "";
	var lines = $('#tbRecvLines tbody');
	var recvMark = $('#chkDisplayAs');
	var txtMark = $('#txtDisplayAs'); 
	
	if(lines.length === 0){
		alert('<spring:message code="approval.msg.applines.norecvs" />');
		return;
	}
	
	appline = makeRecvLine(lines.children());

	//alert(appline);
	if (opener != null && opener.setPubReader) {
		var msg = opener.setPubReader(appline);

		if(typeof(msg) !== "undefined"){
			if(msg !== ""){
				alert(msg);
			}else{
				window.close();
			}
		}else{
			window.close();
		}
	}
}

function closePopup(){
	window.close();
}

function makeRecvLine(lines){
	
	var strRt = "";	
	for(var i = 0; i < lines.length; i++){
		var line = lines[i];
		strRt += (line.getAttribute('pubReaderId') 		+ String.fromCharCode(2) + line.getAttribute('pubReaderName') 		+ String.fromCharCode(2) + line.getAttribute('pubReaderPos') 		+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('pubReaderDeptId') 	+ String.fromCharCode(2) + line.getAttribute('pubReaderDeptName') 	+ String.fromCharCode(2) + line.getAttribute('pubReaderRole') 		+ String.fromCharCode(2) + line.getAttribute('pubReaderOrder') 		+ String.fromCharCode(2));
		strRt += (line.getAttribute('readDate') 		+ String.fromCharCode(2) + line.getAttribute('pubReadDate') 		+ String.fromCharCode(2) + line.getAttribute('registerId')	 		+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('usingType') 		+ String.fromCharCode(4));
	}
	return strRt;
}

//넘어온 경로를 세팅한다.
function setPubReader(){
	var tbRecvLines = $('#tbRecvLines tbody');
	
	if (opener != null && opener.getPubReader) {
		var readersinfo = opener.getPubReader();

		var objRecvs = getPubReaderList(readersinfo)
			
		for(var i = 0; i < objRecvs.length; i++){
			var objRecv = objRecvs[i];
			var id = objRecv.pubReaderId;
			var row = MakePubReader(id, objRecv);
			tbRecvLines.append(row);

			//초기 공람자수 셋팅
			initPubCnt++;
		}
		
	}

	$("#pubReaderCnt").text(initPubCnt);
	$("#pubReaderCntForm").val(initPubCnt);
	
}
//----------------------------최 하위 버튼 이벤트 끝 --------------------------------------------//


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
var bPop = false;
var popMsg = "";
function setSameUsers(user){
	bPop = true;
	makeSameUser(user);
	bPop = false;
	return popMsg;
}

function makeSameUser(user){
	var tbl = $('#sameUser tbody');
	tbl.empty();
	
	var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
	row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
	row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
	row += "' roleCodes='"+ user.roleCodes + "' userOrder='"+ user.userOrder;
	row += "' >";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+";'>"+user.userName+"</td></tr>";
	tbl.append(row);

	var tRow = $('#sameUser tbody tr[id='+user.userUID+']');

	gSaveObject.restore();
	gSaveObject.add(tRow, sColor);

	AddList();

	$('#userName').val("");
}

//------------- 공람자그룹 시작 ------------- 2011.07.25 신경훈 //

// 탭 메뉴 시작
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>
function callMethod(flag) {
	if (flag == "divPubViewGroup") {
		var pubviewGroup = $('#tblPubviewGroup tbody').children();
		if(pubviewGroup.length > 0){
			onRecvGroupClick($('#'+pubviewGroup[0].id));
		}
		$('#divBtnPubviewLine').hide();	
		$('#divBtnPubviewGroup').show();
		
		$('#div_dept_search').hide();
		$('#div_dept_search_no').show();	
	} else {
		$('#divBtnPubviewGroup').hide();
		$('#divBtnPubviewLine').show();

		$('#div_dept_search').show();
		$('#div_dept_search_no').hide();			
	}
	$('div[group="pubview"]').hide();
	$('#'+flag).show();
}
// 탭 메뉴 끝

var recvList = null;

function onRecvGroupClick(obj){
	selectOneElementGp(obj);//2011.09.03 ctrl, atl, shift 키관련
	getPubViwerLine(obj);
}

function selectOneElementGp(obj){
		$('document').empty();
		gSaveObject.restore();
		gSaveObject.add(obj, sColor);
}

function onRecvGroupDblClick() {
	pubviewGroupAdd();
}

function pubviewGroupAdd() {
	var tbRecvLines = $('#tbRecvLines tbody');
	var changeCnt = 0;
	recvLineLength = recvList.length;
	var selPubCnt = 0;
	
	for (var i=0; i<recvLineLength; i++) {
		
		recvObj = recvList[i];
		var id = recvObj.pubReaderId;
		
		recvObj.newFlg = "Y";
		recvObj.usingType = "<%=usingType%>";
		recvObj.readDate = "9999-12-31 23:59:59";
		recvObj.pubReadDate = "9999-12-31 23:59:59";
		

		var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
		
		var row = MakePubReader(id, recvObj);

		var rex = /undefined/g;
		row = row.replace(rex,"");//
		
		if(tmpTr.length === 0 && recvObj.changeYn == "N"){			
			tbRecvLines.append(row);
			selPubCnt++;
		}
		if(recvObj.changeYn == "Y"){
			changeCnt++;			
		}
	}
	if (changeCnt > 0) {
		alert(changeCnt+"<spring:message code='env.group.msg.notice.pubviewline2' />");
	}
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;

	// 공람자 추가할때 총 합을 셋팅
	var pubReaderCnt = parseInt($("#pubReaderCntForm").val()) + selPubCnt;
	$("#pubReaderCnt").text(pubReaderCnt);
	$("#pubReaderCntForm").val(pubReaderCnt);
}

function getPubViwerLine(obj){
	var pubviewGroupId = obj.attr('id');
	var procUrl = "<%=webUri%>/app/env/listEnvPubView.do?pubviewGroupId="+pubviewGroupId;
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, function(data){
		results = data;
	});
	recvList = results;
	drawRecvLine(results);			
}

function drawRecvLine(recvLineList) {
	var tbl = $('#tblPubviewerLine');
	var txtRecvLines = "";
	var recvlineLength = recvLineList.length;
	//var notice = "N";
	tbl.empty();

	for (var i=0; i<recvlineLength; i++) {
		var recvLine = recvLineList[i];
		//var enfName = codeToName(recvLine.enfType);
		var row = "";
		var bgcolor = "ffffff";
		if (recvLine.changeYn == "Y") {
			bgcolor = "ffefef";
			notice = "Y";
		}
		row = "<tr style='background-color:#"+bgcolor+";'>";
		row += "<td width='45%' class='ltb_center'>"+recvLine.pubReaderDeptName+"</td>";
		row += "<td width='25%' class='ltb_center'>"+recvLine.pubReaderPos+"</td>";
		row += "<td width='30%' class='ltb_center'>"+recvLine.pubReaderName+"</td>";
		row += "</tr>";
		tbl.append(row);
	}

	/*
	if (notice == "Y") {
		alert("<spring:message code='env.group.msg.notice.recvline' />");
	}
	*/
}
//------------- 공람자그룹 끝 ------------- 2011.07.25 신경훈 //

//팀별로 공람자 추가하기
function addTeam(reserved1){
	var pubObj = new Object();

	var tbl = $('#tbUsers tbody tr[reserved1='+reserved1+']');
	var tbRecvLines = $('#tbRecvLines tbody');
	
	for(var i = 0; i < tbl.length; i++){
		var user = tbl[i];	

		pubObj.pubReaderId 			= user.getAttribute('id');
		pubObj.pubReaderName 		= user.getAttribute('userName');
		pubObj.pubReaderPos 		= user.getAttribute('positionName');
		pubObj.pubReaderDeptId 		= user.getAttribute('deptID');
		pubObj.pubReaderDeptName 	= user.getAttribute('deptName');
		pubObj.pubReaderRole 		= "";
		pubObj.readDate				= "";
		pubObj.pubReadDate			= "";
		pubObj.registerId			= "";
		pubObj.usingType			= usingType;
		pubObj.pubReaderOrder       = user.getAttribute('pubReaderOrder');

		var id = pubObj.pubReaderId;

       if(user.getAttribute("roleCodes").indexOf(role_ceo) !== -1){
				if(pubObj.pubReaderRole != ""){
					pubObj.pubReaderRole += "^";
				}
				pubObj.pubReaderRole += role_ceo; //CEO
       }

       if(user.getAttribute("roleCodes").indexOf(role_officer) !== -1){
				if(pubObj.pubReaderRole != ""){
					pubObj.pubReaderRole += "^";
				}
				pubObj.pubReaderRole += role_officer; //임원
      }

		var row = MakePubReader(id, pubObj);

		var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');

		if(tmpTr.length === 0){	
			tbRecvLines.append(row);			
		}
	}	
}//E8841A  8793C2

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

	setTimeout(function(){
		if(ownDeptId != null){
			jQuery.tree.reference("#org_tree").scroll_into_view(ownDeptId);
		}
		
	},10);
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
		goSameNameUser();
	}else{
		goSearchDept();
	}
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
			<td>
			<span class="pop_title77"><spring:message code="approval.title.apppublic" /></span>
	        <!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<!-- 탭 시작 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
		            <td>
		               <acube:tabGroup>
		                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('divPubView');" selected="true"><spring:message code="env.tapmenu.pubviewer" /></acube:tab>
						  <acube:space between="tab"/>
						  <acube:tab index="2" onClick="JavaScript:selectTab(2);callMethod('divPubViewGroup');"><spring:message code="env.tapmenu.pubviewergroup" /></acube:tab>
		               </acube:tabGroup>
		           </td>
		        </tr>
	        </table>
	        <!-- 탭 끝 -->
	        <!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
	         <div id="div_dept_search">
			<!-- 검색  -->	
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="table_line">
			<tr>
				<td class="tb_tit" width="70">
				<select id="searchType" name="searchType">
					<option value="1"><spring:message code="env.search.user.title" /></option>
					<option value="2"><spring:message code="bind.obj.dept.name" /></option>
				</select>
				</td>				
				<td class="tb_tit" width="25%"><input id="userName" name="userName" type="text" style="width: 100%;ime-mode:active;FONT-SIZE:9pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#777777; background-color:#FFFFFF; height:16px; padding-left:3px; padding-right:3px; border:1px #C3C2C2 solid;" />
				<input id="searchDept" name="searchDept" type="hidden" />
				</td>
				<td class="tb_tit" width="10%"><a href="javascript:goSearch();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
				<td class="tb_tit">&nbsp;</td>
			</tr>
			</acube:tableFrame>
			</div>
			<div id="div_dept_search_no" style="display:none">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td class="tb_tit">&nbsp;</td>
			</tr>
			</acube:tableFrame>
			</div>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		<div id="divPubView" group="pubview" style="display:block;">
		<!-- 대내 -->
		<div id="divInternal" style="height:200px;">
			<!-- 부서 트리 시작 -->
			<div  id="divDept"  style="float:left; width:49%; ">
			 <!-- 트리용 테이블  -->
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="162" class="basic_box">
				<div id="org_tree" style="height:155px; overflow:auto; background-color : #FFFFFF;">
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
				</td>
			</tr>
			</acube:tableFrame><!-- 트리용 테이블 -->
			</div><!-- 부서 트리 끝-->
			<!-- 부서별 부서원 목록 시작 -->
			<div id="divUsers" style="float:right; width:49%; ">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="177" class="basic_box" style="padding:0px; margin:0px;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0" >
				<tr><td class="table">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<TR>
							<TD width="143" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
							<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
						</TR>
					</thead>
				</table>
				<div style="height:150px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
				<table id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody />
				</table>
				</div>
				</td>
				</tr>
				</table>
			</td>
			</tr>
			</acube:tableFrame>
			</div><!-- 부서별 부서원 목록 시작 -->
		</div>
		</div>
		<!-- ////////// 공람자그룹 시작 ////////// 2011.07.25 신경훈 -->
		<div id="divPubViewGroup" group="pubview" style="display:none;height:200px;">
		
			<div id="divRecvGroupList" style="float:left; width:49%;">
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
					<tr>
						<td height="175" class="basic_box" valign="top" style="padding:1px; margin:1px;">
							<!-- 공람자그룹 목록 -->							
							<table cellpadding="0" cellspacing="0" width="300" class="table" style="table-layout:fixed;">
								<tr>
									<td width="60%" class="ltb_head"><spring:message code="env.option.form.03"/></td>
									<td width="20%" class="ltb_head"><spring:message code="env.option.form.24"/></td>
									<td width="20%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
								</tr>
							</table>
							<div style="height:150px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">								
							<table id=tblPubviewGroup cellpadding="0" cellspacing="0" width="300" class="table_body table_under" style="table-layout:fixed;">
								<tbody>
								
								<c:forEach var="vo" items="${gList}">
									<tr id="${vo.pubviewGroupId}" name="${vo.pubviewGroupId}" onDblClick='onRecvGroupDblClick($("#${vo.pubviewGroupId}"));' onClick='onRecvGroupClick($("#${vo.pubviewGroupId}")); return false;' bgcolor="#ffffff" style="cursor:pointer;">
										<td width="60%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.pubviewGroupName}"><nobr>${vo.pubviewGroupName}</nobr></td>
										<td width="20%" class="ltb_center">${vo.pubviewCount}</td>
										<td width="20%" class="ltb_center"><spring:message code="env.grouptype.${vo.groupType}"/></td>
									</tr>
								</c:forEach>
								
								</tbody>
							</table>
							</div>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			
			<div id="divRecvAppLine" style="float:right; width:49%;">
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
					<tr>
						<td height="175" valign="top" class="basic_box" style="padding:1px; margin:1px;">
							<!-- 공람자 목록 -->
							<table cellpadding="0" cellspacing="0" width="300" class="table" style="table-layout:fixed;">
								<tr>
									<td width="45%" class="ltb_head"><spring:message code="env.option.form.07"/></td>
									<td width="25%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
									<td width="30%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
								</tr>
							</table>
							<div style="height:150px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
							<table id="tblPubviewerLine" cellpadding="0" cellspacing="0" width="300" class="table_body" style="table-layout:fixed;">
								<tbody />
							</table>
							</div>
						</td>
					</tr>
				</acube:tableFrame>
			</div>	
			
		</div>
		<!-- ////////// 공람자그룹 끝 ////////// 2011.07.25 신경훈 -->		
		
		<!-- ------------------------대내 끝------------------------ -->
		<!-- 여백 시작 -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td align="right"><div id="divReserved">&nbsp;</div></td>
			</tr>
		</table>
		<!-- 여백 끝 -->
		<div style="width:100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td width="30%"><strong><font color="#3333ff"><span id="pubReaderCnt"></span></font></strong> <spring:message code="env.option.form.25"/> 
				</td>
				<td width="40%">
					<div id ="divBtnPubviewLine" style="display:block;">
					<acube:buttonGroup align="center">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
					</div>
					<div id ="divBtnPubviewGroup" style="display:none;">
					<acube:buttonGroup align="center">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="pubviewGroupAdd();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
					</div>
				</td>
				<td width="30%">&nbsp;</td>
				</tr></table>
			</div>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 결재 경로 -->
		<table  width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="td_table table_border">
						<tr>
							<td>
							<div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<thead>
										<tr><TD width="170" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.sosok" /></TD>
											<TD width="170"  class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gikwei" /></TD>
											<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
										</tr>
									</thead>
								</table>
							</div>
							<div id="divRecvLines" style="height:120px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
								<table id="tbRecvLines" width="100%" border="0" cellpadding="0" cellspacing="0">
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
	<form>
	<input type="hidden" name="pubReaderCntForm" id="pubReaderCntForm" value="0"></input>
	</form>
</body>
</html>