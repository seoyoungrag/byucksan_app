<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : EnvAppPreReader.jsp 
 *  Description : 결재경로그룹 관리 - 담당자지정(접수)
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.27
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 27 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType);//추가
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType);//삭제
	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("results");	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));
	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.applines" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#757575;}
	.range {display:inline-block;width:98px;height:25px; !important;}
</style>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--

function getEnfList(appenf) {
	var enflist = new Array();
	var enfinfo = appenf.split(String.fromCharCode(4));
	var enflength = enfinfo.length;
	for (var loop = 0; loop < enflength; loop++) {
		if (enfinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = enfinfo[loop].split(String.fromCharCode(2));
			var enf = new Object();
			
			enf.processorId             = info[0];
			enf.processorName           = info[1];
			enf.processorPos            = info[2];
			enf.processorDeptId         = info[3];
			enf.processorDeptName       = info[4];
			enf.representativeId       	= info[5];
			enf.representativeName     	= info[6];
			enf.representativePos      	= info[7];
			enf.representativeDeptId   	= info[8];
			enf.representativeDeptName 	= info[9];
			enf.askType                 = info[10];
			enf.procType                = info[11];
			enf.processDate             = info[12];
			enf.readDate                = info[13];
			enf.editLineYn              = info[14];
			enf.mobileYn                = info[15];
			enf.procOpinion             = info[16];
			enf.signFileName            = info[17];
			enf.lineHisId            	= info[18];
			enf.fileHisId            	= info[19];
			enf.absentReason            = info[20];
			enf.changeYn	            = info[21];
			enf.lineOrder               = info[22];
	
			enflist[loop] = enf;
		}
	}

	return enflist;
}

//결제요청코드   
var ART060 = '<%=appCode.getProperty("ART060","ART060","APP") %>'; //선람       
var ART070 = '<%=appCode.getProperty("ART070","ART070","APP") %>'; //담당 

var g_Arts = new Array();

var isCtrl = false, isShift = false; //keyChecked

var gSaveObject = g_selector();
//결재라인 tr 객체를 담는 Valuable
var gSaveLineObject = g_selector();
var sColor = "#F2F2F4";
var tbColor = "#E3E3E3";

$(document).ready(function() { 
	makeArtNames();
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

	if (opener != null && opener.getLineGroupName && opener.getMode() == "U") {
		$('#groupName').val(opener.getLineGroupName());
	}

	//결재경로를 호출한다.
	if (opener != null && opener.getMode() && opener.getMode() == "U") {
		lineInit(setAppLine());
	} 

	var radio = $('input:radio[name=options1]');
	if(radio.length > 0){
		radio[0].setAttribute("checked",true);
	}		
	//lineInit(setAppLine());
});
//$(document).ready 종료
//----------------Shift Key  Ctrl Key--------------------------------
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
//----------------Tree 이벤트--------------------------------

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
	//$.tree.focused().select_branch("#"+node.id);
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
			t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, 'rel':rel},
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

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	if(results.length > 0){
		for(var i = 0; i < results.length; i++){
			var user = results[i];
			var row = "<tr bgcolor='#ffffff' id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' rowOrd='"+ i;
			row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";
			var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
			row += "<td class='ltb_center' width='110'>"+userPosition+"</td>";
			row += "<td class='ltb_center'>"+user.userName+"</td></tr>";
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
	
function makeArtNames(){
	g_Arts[ 0] = new Object();
	g_Arts[ 1] = new Object();


	g_Arts[ 0].Val = ART060;
	g_Arts[ 1].Val = ART070;


	g_Arts[ 0].Nm = '${options["OPT019"].optionValue}';
	g_Arts[ 1].Nm = '${options["OPT020"].optionValue}';	
}



//호출 페이지에서 넘져준 경로를 받아온다.
var g_lines = ""; 
function setAppLine(){
	
	if (opener != null && opener.getTxtAppLines) {
		g_lines = opener.getTxtAppLines();

		var arrLines = getEnfList(g_lines);

		if(arrLines.length == 0){
			return false;
		}
		
		for(var i = 0; i < arrLines.length; i++){
			var lines = $('#tbApprovalLines tbody'); //결재경로
			var appLines = lines.children();
			
			var enfObj = arrLines[i];
			for(var idx = 0; idx < g_Arts.length; idx++){
				if(g_Arts[idx].Val === enfObj.askType){
					enfObj.opt_nm = g_Arts[idx].Nm;
					break;
				}
			}	

			var row = approveMakeRow(enfObj);
			lines.append(row);
		}

		return true;
	}
	
	return false;
}
//----------------Tree 이벤트 끝--------------------------------

//로그인 사용자 정보 초기화 시킨다.
function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
}
//사원 선택시 발생하는 이벤트 
// parameter obj : 선택된 tr 의 jquery 객체이다.
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
		if(isCtrl){
			$('document').empty();
			gSaveObject.add(obj, sColor);
		}

		if(isShift){
			
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
		case 13:
		{
//			obj.trigger('dblclick');
//			obj.children()[0].focus();
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
	addApprovalLine();
}


function addApprovalLine(){

	var lines = $('#tbApprovalLines tbody'); //결재경로
	var appLines = lines.children();
	var appColl = gSaveObject.collection();

	if(appColl.length === 0){
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.cknochoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.cknochoice" />';
		return;
	}
	
	for(var i = 0; i < appColl.length; i++){
		var appItem = appColl[i];
		var enfObj = new Object();
		
		var ck_app = true;
		for(var j = 0; j < appLines.length; j++){
			var appLine = appLines[j];
			if(appLine.id == ("tbApprovalLines_" + appItem.attr("id"))){
				ck_app = false;
				if(!bPop)
					alert('<spring:message code="approval.msg.applines.ckchoice" />');
				else
					popMsg = '<spring:message code="approval.msg.applines.ckchoice" />';
				break;
			}
		}
		
		if(ck_app){
			var opt_code = "";
			var opt_nm = "";

			var radio = $('input:radio[name="options1"]:checked');

			opt_code = radio.val();
			opt_nm = radio.attr('codeNm');
			
			enfObj.lineOrder = "";             
			enfObj.processorId = "";            
			enfObj.processorName = "";          
			enfObj.processorPos  = "";          
			enfObj.processorDeptId = "";        
			enfObj.processorDeptName = "";      
			enfObj.originprocessorId = "";      
			enfObj.originprocessorName = "";    
			enfObj.originprocessorPos = "";     
			enfObj.originprocessorDeptId = "";  
			enfObj.originprocessorDeptName = "";
			enfObj.askType = "";                
			enfObj.procType = "";               
			enfObj.processDate = "";            
			enfObj.readDate = "";               
			enfObj.editLineYn = "";             
			enfObj.mobileYn = "";               
			enfObj.procOpinion = "";            
			enfObj.signFileName = "";   
			enfObj.changeYn = "";        
					
			enfObj.processorId = appItem.attr("id");
			enfObj.processorName = appItem.attr("userName");
   		 	enfObj.processorPos = appItem.attr("positionName"); //appItem.attr("positionCode");
   		 	enfObj.processorDeptId = appItem.attr("deptID");
   			enfObj.processorDeptName = appItem.attr("deptName");
   			enfObj.askType = opt_code;
   			enfObj.opt_nm = opt_nm;
   			   			
           	var row = approveMakeRow(enfObj);

           lines.append(row);
		}
	}

	gSaveLineObject.restore();	
}

function approveMakeRow(enfObj){
	
	var tbId = "tbApprovalLines";

	var tbgcolor = "#FFFFFF";

	if(enfObj.procType !== ""){
		tbgcolor = "#EEEEEE"; 
	}

	if (enfObj.changeYn == "Y") {
		tbgcolor = "#ffefef";
	}
	
	var row = "<tr bgcolor='#ffffff'";
	row += "id='"+ tbId + "_" + enfObj.processorId + "' lineOrder='" + enfObj.lineOrder + "' "; 
	row += "processorId='" + enfObj.processorId + "' processorName='" + enfObj.processorName + "' processorPos='" + enfObj.processorPos + "' ";
	row += "processorDeptId='" + enfObj.processorDeptId + "' processorDeptName='" +  enfObj.processorDeptName + "' originprocessorId='" + enfObj.originprocessorId + "' "; 
	row += "originprocessorName='" + enfObj.originprocessorName + "' originprocessorPos='" + enfObj.originprocessorPos + "' originprocessorDeptId='" + enfObj.originprocessorDeptId + "' "; 
	row += "originprocessorDeptName='" + enfObj.originprocessorDeptName + "' askType='" + enfObj.askType + "' procType='" + enfObj.procType + "' ";  
	row += "readDate='"+ enfObj.signFileName +"' processDate='"+ enfObj.processDate +"' ";
	row += "editLineYn='" + enfObj.editLineYn + "' mobileYn='" + enfObj.mobileYn + "' procOpinion='" + enfObj.procOpinion + "' signFileName='" + enfObj.signFileName + "' " ;
	row += " onclick='onLineClick($(\"#"+ tbId + "_" + enfObj.processorId+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+ tbId + "_" + enfObj.processorId +"\"));' ";
	row += " style='background-color:"+tbgcolor+";' >";
	row += "<td width='210' class='ltb_center'>" + enfObj.processorDeptName + "</td>";
	row += "<td width='120' class='ltb_center'>" + enfObj.processorPos + "</td>";
	row += "<td width='143' class='ltb_center'>" + enfObj.processorName + "</td>"
	row += "<td class='ltb_center'>" + enfObj.opt_nm + "</td>";
	row += "</tr>";
	
	return row;
}

//--------------------------결재선 처리------------------------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
	click_Appline(obj);
}
function click_Appline(obj){
	$('input:radio[value="'+obj.attr('askType')+'"]').attr('checked', true);
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
//gSaveLineObject
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
			var objs = $('#tbApprovalLines tbody').children();
			
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

//추가(+) 버튼 클릭시
function onAddLine(){
	addApprovalLine();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	
	for(var i = 0; i < lines.length; i++){
		if(lines[i].attr("procType") !== ""){
			alert('<spring:message code="approval.msg.applines.cannotline" />');
			return;
		}
		lines[i].remove();
	}
	
}


//위로올리기 버튼
function onMoveUp(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.prev();

		if(obj.attr('procType') !== "" || prev.attr('procType') !== "" ){
			return;
		}
		
		prev.before($('#'+obj.attr('id')));
	}
}

//아래로 내리기 버튼
function onMoveDown(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.next();

		if(obj.attr('procType') !== ""){
			return;
		}
		
		prev.after($('#'+obj.attr('id')));		
	}
}

//확인버튼 클릭
function sendOk(){

	if ($.trim($("#groupName").val()) == "") {
		alert('<spring:message code="env.option.msg.validate.groupname"/>');
		$('#groupName').focus();
		return;
	}

	if (opener != null && opener.duplicationCheck) {
		if (opener.duplicationCheck($.trim($("#groupName").val()))) {
			alert('<spring:message code="env.group.msg.groupname.duplication"/>');
			$('#groupName').focus();
			return;
		}
	}
	
	var appline = "";
		
	//결제라인 롤을 체크한다.
	if(! checkLineRole()){
		return;
	}

	appline = makeApprovalLine();

	if (opener != null && opener.setLineGroupInfo && opener.setLineGroupName && opener.getMode) {
		opener.setLineGroupInfo(appline);
		opener.setLineGroupName($('#groupName').val());

		if (opener.getMode() == "I" ) {
			opener.insertLineGroup();
		} else if (opener.getMode() == "U") {
			opener.updateLineGroup();
		}		
		window.close();
	}

	//if (opener != null && opener.setEnfLine) {
	//	opener.setEnfLine(appline);
	//	window.close();
	//}
}

function closePopup(){
	window.close();
}

//결재라인 생성하기
function makeApprovalLine(){
	var lines = $('#tbApprovalLines tbody').children();
	return getApprovalLines(lines);
}

//결재라인 작성하기
function getApprovalLines(lines){
	
	var max = lines.length;
	var strRtn = "";		
	for( var i = 0; i< max ; i++){
		var line = lines[i];
		line.setAttribute('lineOrder',i+1);

		strRtn += line.getAttribute('lineOrder') 			+ String.fromCharCode(2);
		strRtn += '1' + String.fromCharCode(2); // lineSubOrder
		strRtn += line.getAttribute('processorId') 			+ String.fromCharCode(2);
		strRtn += line.getAttribute('processorName') 		+ String.fromCharCode(2);
		strRtn += line.getAttribute('processorPos') 		+ String.fromCharCode(2);
		strRtn += line.getAttribute('processorDeptId') 		+ String.fromCharCode(2);
		strRtn += line.getAttribute('processorDeptName') 	+ String.fromCharCode(2);
		strRtn += line.getAttribute('askType') 				+ String.fromCharCode(4);

		/*
		strRtn += (line.getAttribute('originprocessorId') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('originprocessorName') 	+ String.fromCharCode(2));
		strRtn += (line.getAttribute('originprocessorPos') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('originprocessorDeptId') 	+ String.fromCharCode(2));
		strRtn += (line.getAttribute('originprocessorDeptName') + String.fromCharCode(2));		
		strRtn += (line.getAttribute('procType') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processDate') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('readDate') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('editLineYn') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('mobileYn') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('procOpinion') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('signFileName') 			+ String.fromCharCode(4));
		*/
		
	}

	return strRtn;
}

//결재라인 롤 체크하기
function checkLineRole(){
	var lines = $('#tbApprovalLines tbody').children();
	var max = lines.length;

	var tmpAsk = 0;//ART060

	var lenART070 = $('#tbApprovalLines tbody tr[askType="'+ART070+'"]').length;

	if(lenART070 == 0){ //담당은 최소한 하나 존재해야함 		
		alert('<spring:message code="approval.msg.applines.after.noenf" />');
		return;
	}
	
	for(var i = 0; i < max; i++){
		var enfObj = lines[i];

		var nAskType = 0;
		if(enfObj.getAttribute('askType') === ART060){//선람
			nAskType = 0;
		}

		if(enfObj.getAttribute('askType') === ART070){//담당
			nAskType = 1;
		}

		if(i > 0){
			if(nAskType < tmpAsk){
				alert('<spring:message code="approval.msg.applines.after.enf" />');
				return false;
			}
		}
		/*	
		}else{
			if(nAskType < tmpAsk){
				alert("!21212");				
				alert('<spring:message code="approval.msg.applines.after.enf" />');
				return false;
			}

		}
			*/

		tmpAsk = nAskType;
	}
	
	return true;
}

function optClick(obj){
	var lines = gSaveLineObject.collection();

	for(var i = 0; i < lines.length; i++){
		var line = lines[i];

		line.attr('askType',obj.getAttribute('value'));
		line.children().last().text(obj.getAttribute('codeNm'));		
	}
	
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
	row += "' >";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+";'>"+user.userName+"</td></tr>";

	tbl.append(row);

	var tRow = $('#sameUser tbody tr[id='+user.userUID+']');

	gSaveObject.restore();
	gSaveObject.add(tRow, sColor);

	addApprovalLine();

	$('#userName').val("");
}

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
<acube:outerFrame>
	<acube:titleBar><spring:message code="approval.title.applines" /></acube:titleBar>
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
<div>
	<!-- 검색  -->	
	<acube:tableFrame border="0" cellspacing="0" cellpadding="0" class="">
	<tr>
    	<td height="3" background="<%=imagePath%>/dot_search1.gif"></td>
    </tr>
	<tr>		
		<td width="50%">		
			<acube:tableFrame cellspacing="0" class="">
				<tr class="search" height="36">
					<td width="65" align="center" class="search_t">
						<spring:message code="env.option.form.03"/>
						<spring:message code='common.title.essentiality'/>
					</td>
					<td width="*">
						<input type="text" name="groupName" id="groupName" onkeyup="checkInputMaxLength(this,'',128)" class="input" style="width:90%;height:15px;" />
					</td>
				</tr>
			</acube:tableFrame>						
		</td>
		<td width="50%">
			<acube:tableFrame cellspacing="0" class="">
			<tr class="search" height="36">
				<td class="search_t" width="70">
				<select id="searchType" name="searchType">
					<option value="1"><spring:message code="env.search.user.title" /></option>
					<option value="2"><spring:message code="bind.obj.dept.name" /></option>
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
	
	<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
	<tr>
		<td width="48%">
		 <!-- 트리용 테이블  -->
		<acube:tableFrame class="basic_box">
		<tr>
			<td height="215">
			<div id="org_tree" style="height:200px; overflow:auto; background-color : #FFFFFF;">
		    <ul>
<!-- 		    	<li id="ROOT" class="open"><a href='#'><ins></ins>ROOT</a>  -->
<!--		    	<ul> -->
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
					    rel = " rel='comp' ";
					}else if(result.getOrgType() == 2){
					    rel = " rel='dept' ";
					}else{
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
<!--		    	</ul> -->
<!--				</li> -->
		    </ul>
			</div>				
			</td>
		</tr>
		</acube:tableFrame><!-- 트리용 테이블 -->
		</td>
		<td width="10" style="background-color : #FFFFFF;"></td>
		<td>
		<acube:tableFrame class="basic_box">
		<tr>
			<td style="padding:3 3 3 3;">
				<acube:tableFrame class="table">
					<thead>
						<tr>
							<td width="117" class="ltb_head">
								<spring:message code="approval.table.title.gikwei" />
							</td>
							<td class="ltb_head">
								<spring:message code="approval.table.title.name" />
							</td>
						</tr>
					</thead>
				</acube:tableFrame>
				<div style="height:190px; overflow-x:hidden; overflow-y:scroll; background-color : #FFFFFF;">
				<table id="tbUsers" width="100%" bgcolor="#bbbbbb" border="0" cellpadding="0" cellspacing="1" style="table-layout:fixed;">
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
 
 <!-- 결재 옵션 -->
	<acube:tableFrame width="100%" border="0" cellpadding="1" cellspacing="0" class="">
		<tr>
			<td width="100%" height="100%" class="g_box" style="padding:10px 10px 10px 10px;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="1" colspan="3"></td>
					</tr>
					<tr><td width="5" height="30"></td>
						<td>
							<c:if test='${options["OPT019"].useYn == "Y"}'>
							<span class="range"><!-- 선람 -->							
							<input checked id=ART060 name="options1" type="radio"  value='<%=appCode.getProperty("ART060", "ART060", "APP") %>' codeNm='${options["OPT019"].optionValue}' onclick="optClick(this)" />${options["OPT019"].optionValue}
							</span>							
							</c:if>
							<c:if test='${options["OPT020"].useYn == "Y"}'>
							<span class="range"><!-- 담당 -->							 
							<input id='ART070' name="options1" type="radio"  value='<%=appCode.getProperty("ART070", "ART070", "APP") %>' codeNm='${options["OPT020"].optionValue}' onclick="optClick(this)" />${options["OPT020"].optionValue}
							</span>
							</c:if>
						</td>
						<td width="5"></td>
					</tr>
					<tr>
						<td height="1" colspan="3"></td>
					</tr>
				</table>
			</td>
		</tr>
	</acube:tableFrame>
	
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	<table border='0' cellpadding='0' cellspacing='0' width="100%">
		<tr>
		<td width="100%" height="23" align="right" valign="bottom">
			<div id="spanApprLineButtons">
			<table border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td>
						<acube:buttonGroup align="right">
						<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />
						<acube:space between="button" />
						<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
						</acube:buttonGroup>
					</td>
				</tr>
			</table>
			</div>
		</td>
		</tr>
	</table>
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	<!-- 결재 경로 -->
	<table  width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" height="100%">
							<table width="100%" bgcolor="#e3e3e3" border="0" cellpadding="0" cellspacing="1" class="table">
								<thead>
									<tr>
										<td width="217" class="ltb_head"">
											<spring:message code="approval.table.title.sosok" />
										</td>
										<td width="127"  class="ltb_head">
											<spring:message code="approval.table.title.gikwei" />
										</td>
										<td width="150"  class="ltb_head">
											<spring:message code="approval.table.title.name" />
										</td>
										<td class="ltb_head">
											<spring:message code="approval.table.title.gubun" />
										</td>
									</tr>
								</thead>
							</table>
							<div style="height:121px; overflow-x:hidden; overflow-y:scroll; background-color : #FFFFFF;">
							<table id="tbApprovalLines" width="100%" bgcolor="#bbbbbb" border="0" cellpadding="0" cellspacing="1" style="table-layout:fixed;">
								<tbody />
							</table>
							</div>
						</td>
					</tr>
				</acube:tableFrame>
			</td>
			<td width="5"></td>
			<td width="2%">
				<TABLE cellpadding="0" cellspacing="0" width="2%">
					<TR>
						<TD style="background:#ffffff" align="center" valign="middle">
							<IMG src="<%=webUri%>/app/ref/image/bu_up1.gif" onclick="javascript:onMoveUp();return(false);"><BR /><BR />
							<IMG src="<%=webUri%>/app/ref/image/bu_down1.gif" onclick="javascript:onMoveDown();return(false);">
						</TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
</div>
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	
	<acube:buttonGroup align="right">
	<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="확인" />
	<acube:space between="button" />
	<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="취소" />
	</acube:buttonGroup>		
</acube:outerFrame>
<div id="divPop" style="display: none;position: absolute;">
<table id="sameUser">
	<tbody></tbody>
</table>
</div>

</body>	
</html>