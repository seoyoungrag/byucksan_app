<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@page import="com.sds.acube.app.common.vo.OrganizationVO"%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.HashMap" %>
<%@page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.idir.ldaporg.client.*" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ApprovalRecipientPop.jsp
 *  Description : 수신경로팝업
 *  Modification Information 
 * 
 *   수 정 일 : 2012.04.17 
 *   수 정 자 : 최봉곤
 *   수정내용 : 2014.04.17  - 수신자기호 및 행정기관 탭 추가 / 옵션 적용.
 * 
 *  @author  장진홍
 *  @since 2011. 04. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType); // 부서추가
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType); // 추가
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType); // 변경
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType); // 삭제
	String includeChildrenBtn = messageSource.getMessage("approval.button.applines.include.children.dept", null, langType);// 하위부서 포함

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

	String rgd001Btn = messageSource.getMessage("approval.button.rgt.rgd001", null, langType); // 회사
	String rgd002Btn = messageSource.getMessage("approval.button.rgt.rgd002", null, langType); // 본사
	String rgd003Btn = messageSource.getMessage("approval.button.rgt.rgd003", null, langType); // 지점
	String rgc001Btn = messageSource.getMessage("approval.button.rgt.rgc001", null, langType); // 전 부서
	String rgc002Btn = messageSource.getMessage("approval.button.rgt.rgc002", null, langType); // 전 영업
	String allDelBtn = messageSource.getMessage("approval.button.rgt.alldel", null, langType); // 전체 삭제
	
	String buttonZipcode = messageSource.getMessage("env.option.button.zipcode" , null, langType);
	
	String COMP_ID = (String) session.getAttribute("COMP_ID");
	String DEPT_ID = (String) session.getAttribute("DEPT_ID");
	String PART_ID = (String) session.getAttribute("PART_ID");
	
	PART_ID = (PART_ID == null? "" : PART_ID );

	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("result");
	List<DepartmentVO> results2 = (List<DepartmentVO>) request.getAttribute("result2");
	List<OrganizationVO> resultsSymbol = (List<OrganizationVO>) request.getAttribute("resultSymbol");
	LDAPOrganizations LDAPOrgs = (LDAPOrganizations) request.getAttribute("LDAPOrgs");
	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");	
	HashMap<String, String> map410 = envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT410");
	Iterator keySetIter = map410.keySet().iterator();
	Iterator valuesIter = map410.values().iterator();
	int tabCnt = 2;	// 대내 기본.
	int tabIndex = 1;
	String tabI1 = "", tabI2 = "", tabI3 = "", tabI4 = "";
	
	while(keySetIter.hasNext())
	{
		String key = (String)keySetIter.next();
		if ( key.equals("I1") ) tabI1 = map410.get(key);
		if ( key.equals("I2") ) tabI2 = map410.get(key);
		if ( key.equals("I3") ) tabI3 = map410.get(key);
		if ( key.equals("I4") ) tabI4 = map410.get(key);
	}
	
	while(valuesIter.hasNext())
	{
		if (((String)valuesIter.next()).equals("Y"))
		{
			tabCnt++;
		}
	}
	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	
	pageContext.setAttribute("COMP_ID", COMP_ID);
	pageContext.setAttribute("DEPT_ID", DEPT_ID);
	pageContext.setAttribute("PART_ID", PART_ID);
	
	String Opt333 = (String)request.getAttribute("opt333");
	String tbColor = "#E3E3E3";
	
	String owndept = request.getParameter("owndept");
	owndept = (owndept == null ? "" :owndept);
	owndept = ("".equals(owndept) == true ? "" :owndept);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.apprecip" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
var gSaveObject = g_selector(); //사용자 목록
var gSaveLineObject = g_selector(); //수신목록
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";

var opt333 = '${opt333}';
var DRU001 = '<%=appCode.getProperty("DRU001", "DRU001", "DRU") %>'; //부서
var DRU002 = '<%=appCode.getProperty("DRU002", "DRU002", "DRU") %>'; //사람

var DET002 = '<%=appCode.getProperty("DET002", "DET002", "DET") %>'; //대내
var DET003 = '<%=appCode.getProperty("DET003", "DET003", "DET") %>'; //대외
var DET005 = '<%=appCode.getProperty("DET005", "DET005", "DET") %>'; //외부 행정기관 
var DET006 = '<%=appCode.getProperty("DET006", "DET006", "DET") %>'; //외부 민간기관
var DET007 = '<%=appCode.getProperty("DET007", "DET007", "DET") %>'; //민원인
var DET011 = '<%=appCode.getProperty("DET011", "DET011", "DET") %>'; //행정기관
var DET = DET002;

var RGD001 = '<%=appCode.getProperty("RGD001", "RGD001", "RGD") %>'; // 회사
var RGD002 = '<%=appCode.getProperty("RGD002", "RGD002", "RGD") %>'; // 본사
var RGD003 = '<%=appCode.getProperty("RGD003", "RGD003", "RGD") %>'; // 지점
var RGC001 = '<%=appCode.getProperty("RGC001", "RGC001", "RGC") %>'; // 전 부서
var RGC002 = '<%=appCode.getProperty("RGC002", "RGC002", "RGC") %>'; // 전 영업
var rgd001Btn = "<%=rgd001Btn%>";
var rgd002Btn = "<%=rgd002Btn%>";
var rgd003Btn = "<%=rgd003Btn%>";

var enterpriseYn = "N";

var curTreeId1 = '#${DEPT_ID}';
var curTreeId2 = '#org_${COMP_ID}';

var curTabMark = "";
var curRadioNum = 0;

var isCtrl = false, isShift = false; //keyChecked

var editYn = "Y";

var owndept = "<%=owndept%>"; //기안자 부서 및 대리부서 (수신자 목록에 추가 되면 안된다.) opener.getAppRecv() 에서  리턴 받는다. return.owndept; 

var initRecvCnt = 0;
//----------------------------초기 OnLoad 이벤트 시작-------------------------------------------//
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
			"proc_dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/proc_dept.gif"	
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
				openNode(node, tree_obj, 1);
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				selectNode(node, tree_obj, 1);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
			},
			ondblclk : function(node, tree_obj){
				AddList();
			}
		}
	});

	$.tree.focused().select_branch(curTreeId1);

	$("#org_tree2").tree({
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
			"proc_dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/proc_dept.gif"	
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
				openNode(node, tree_obj,2);
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				selectNode(node, tree_obj, 2);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
				//alert("aaaaaaa");
			},
			ondblclk : function(node, tree_obj){
				AddList();
			}
		}
	});

	$("#org_tree3").tree({
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
					image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
				}
			},
	
			"comp" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"	
				}
			},
	
			"dept" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/dept.gif"	
				}
			},
			"proc_dept" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/proc_dept.gif"	
				}
			},
			"part" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/part.gif"	
				}
			}			
		},
		callback : {
			//노드가 OPEN 될때
			onopen: function(node, tree_obj) {
				openSymbolNode(node, tree_obj, 3);				
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				//selectNode(node, tree_obj, 3);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
			},
			ondblclk : function(node, tree_obj){
				AddSymbolListByJSTree();
			}
		}
	});

	$("#org_ldap").tree({
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
					image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
				}
			},
			"comp" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"	
				}
			},	
			"dept" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/dept.gif"	
				}
			},
			"proc_dept" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/proc_dept.gif"	
				}
			},
			"gray_dept" : {
				icon: {
					image : "<%=webUri%>/app/ref/js/jsTree/demo/gray_dept.gif"	
						
				},
				clickable: false
			}
		},
		callback : {
			//노드가 OPEN 될때
			onopen: function(node, tree_obj) {
				openLDAPNode(node, tree_obj);				
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				//selectNode(node, tree_obj, 4);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
			},
			ondblclk : function(node, tree_obj){
				AddLDAPList();
			}
		}
	});
	
	$('#org_${COMP_ID}').remove(); //로그인 사용자 회사 삭제하기
	var errOrg = $('li[orgType=2][parentId='+$('li[orgType=0]').attr('orgId')+']');
	errOrg.remove(); //부서이면서 그룹을 부모로 하는 것 삭제
	onClickTab('I0');	

	if('<%=tabI4%>' == 'Y')
	{
		setRecvType($('#divRecvType0')); // 행정기관 탭이 활성화되어 있는 경우 행정기관 라디오 버튼 선택.
	}
	else
	{
		setRecvType($('#divRecvType1')); // 그렇지 않은 경우 기관 선택.
	}
	
	var divDept = $('#divDept');
	var divUsers = $('#divUsers');
	
	if(opt333 === '2'){
		divUsers.show();
	}else{
		divDept.css("width","98%");
		divUsers.hide();
	}

	//수신목록을 받아 세팅한다.	
	setAppRecv();
	
});
<%
	out.println(com.sds.acube.app.design.AcubeTab.getScriptFunction(tabCnt));
%>
/*
function callMethod(flag) {
	$('div[group="recv"]').hide();
	$('#'+flag).show();
}
*/
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
//----------------------------초기 OnLoad 이벤트 끝-------------------------------------------//

//노트가 열렸을때 발생하는 이벤트를 처리하는 함수
var openNodeId = "";
function openNode(node, tree_obj, treeNum){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var org_id = node.getAttribute('orgId');

	if ( org_id.indexOf("___") > -1 )
	{
		org_id = org_id.substr(0, org_id.indexOf("___"));
	}
	
	var url = "<%=webUri%>/app/common/OrgTreeAjax.do";
	url += ("?orgID="+org_id+"&treeType=0");
	
	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});	

		drawSubTree(node, results, treeNum);
		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');		
	}
}

function openSymbolNode(node, tree_obj, treeNum){ // :::
	
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var org_id = node.getAttribute('orgId');
	var indexName = node.getAttribute('indexName');

	if ( org_id.indexOf("___") > -1 )
	{
		org_id = org_id.substr(0, org_id.indexOf("___"));
	}

	indexName =encodeURI(indexName);
	
	var url = "<%=webUri%>/app/common/OrgSymbolTreeAjax.do";
	url += ("?indexName="+indexName);

	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});
		drawSymbolSubTree(node, results);

		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');		
	}
}

function openLDAPNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var dn = node.getAttribute('DN');
	dn =encodeURI(dn);

	var url = "<%=webUri%>/app/common/OrgLDAPTreeAjax.do";
	url += ("?DN="+dn);
	//alert('::url: ' + url + ' / nSize: ' + nSize + ' / openNodeId: ' + openNodeId + ' / node.id: ' + node.id);

	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});

		if ( results == null ) 
		{
			//alert('<spring:message code="approval.msg.child.node.empty" />'); return;
		}
		
		drawLDAPSubTree(node, results);

		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');		
	}
}

//하위 노드를 그리는 함수
function drawSubTree(node, nodeObj, treeNum){
	var t = $.tree.focused();
	
	for(var i=0; i < nodeObj.length; i++) {
		try {
			var id = "";
			var pId = "";
			var rel = "";
			if(treeNum == 1){
				id = nodeObj[i].orgID;
				pId = nodeObj[i].orgParentID;
			}else{
				id = "org_" + nodeObj[i].orgID;
				pId = "org_" +  nodeObj[i].orgParentID;
			}

			
			if(nodeObj[i].orgType === 0){
				rel="root";
			}else if(nodeObj[i].orgType === 1){
				rel="comp";
			}else if(nodeObj[i].orgType === 2){
				//rel="dept";
				if(nodeObj[i].isProcess){
					rel="proc_dept";
				}else{
					rel="dept";
				}
			}else{				
				rel="part";
			}
			// jth8172 2012 신결재 TF 수신자기호,부서장 직위 추가
			t.create({attributes:{'id':id,"orgId": nodeObj[i].orgID,'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild
				, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, 'outgoingName':nodeObj[i].outgoingName, 'rootOrgId':node.getAttribute('rootOrgId')
				, 'rootOrgName': node.getAttribute('rootOrgName'), 'rootOutgoingName' : node.getAttribute('rootOutgoingName'), 'addrSymbol':nodeObj[i].addrSymbol, 'chiefName':nodeObj[i].chiefPosition, 'orgCode':'', 'isDeleted':'', 'isInspection':''
				, 'isODCD':'', 'isProxyDocHandleDept':'', 'orgAbbrName':'', 'isInstitution':'', 'isProcess':'', 'rel':rel},
				data:{title:nodeObj[i].orgName}}, '#'+pId);	
			
		} catch(e) {}
	}
}

//하위 노드를 그리는 함수
function drawSymbolSubTree(node, nodeObj){
	var t = $.tree.focused();
	//alert('::drawLDAPSubTree: nodeObj: ' + JSON.stringify(nodeObj));

	for(var i=0; i < nodeObj.length; i++) {
		try {
			var id = "";
			var pId = "";
			var rel = "";
			var _title = "";

			id = nodeObj[i].orgID + "___";
			pId = node.id;

			if(nodeObj[i].orgType === 0){
				rel="root";
			}else if(nodeObj[i].orgType === 1){
				rel="comp";
			}else if(nodeObj[i].orgType === 2){
				//rel="dept";
				if(nodeObj[i].isProcess){
					rel="proc_dept";
				}else{
					rel="dept";
				}
			}else{
				rel="part";
			}

			_title = nodeObj[i].addrSymbol + " [" + nodeObj[i].orgName + "]";
			
			t.create({attributes:{'id':id,'orgId':nodeObj[i].orgID, 'companyID':nodeObj[i].companyID, 'parentId':nodeObj[i].orgParentID
				, 'orgName':nodeObj[i].orgName, 'orgType':nodeObj[i].orgType, 'addrSymbol':nodeObj[i].addrSymbol
				, 'isinstitution':nodeObj[i].isInstitution, 'isProcess':nodeObj[i].isProcess, 'chiefName':nodeObj[i].chiefPosition, 'rel':rel},
				data:{title:_title}}, '#'+pId);

		} catch(e) {}
	}
}

//하위 노드를 그리는 함수
function drawLDAPSubTree(node, nodeObj){
	var t = $.tree.focused();
	//alert('::drawLDAPSubTree: nodeObj: ' + JSON.stringify(nodeObj));
	
	if (nodeObj != null )
	{
		for(var i=0; i < nodeObj.length; i++) {
			try {
				var id = "";
				var pId = "";
				var rel = "";
				var isInstitution = "N";
				var isProcess = "N";
				var _title = "";

				rel="company";

				if ( nodeObj[i].isInstitution == true )
				{
					isInstitution = "Y";
					rel="dept";
				}
				
				if ( nodeObj[i].isProcess == true )
				{
					isProcess = "Y";
					rel="proc_dept";
					
					if ( nodeObj[i].isInstitution == true )
					{
						rel="dept";
					}
				}

				if ( nodeObj[i].isInstitution == false && nodeObj[i].isProcess == false )
				{
					rel="gray_dept";
				}
				
				id = nodeObj[i].orgID;
				pId = nodeObj[i].orgParentID;

				_title = nodeObj[i].orgName;
				//_title = nodeObj[i].orgName + "(isI: " + isInstitution + "/isP: " + isProcess + ")";
				
				t.create({attributes:{'id':id,"orgId": nodeObj[i].orgID,'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].depth
					, 'orgName':nodeObj[i].orgName, 'DN':nodeObj[i].orgOtherName, 'addrSymbol':nodeObj[i].addrSymbol
					, 'isinstitution':isInstitution, 'isProcess':isProcess, 'chiefName':nodeObj[i].chiefPosition, 'rel':rel},
					data:{title:_title}}, '#'+pId);
				
			} catch(e) {}
		}
	}
}

//----------------------------부서 트리 관련 이벤트 시작-------------------------------------------//
//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj, treeNum){

	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var org_id = node.getAttribute('orgId');

	if( node.id.indexOf("___") > -1 ){
		org_id = org_id.substring(0, org_id.indexOf("___"));
		node.id = node.id.substring(0, node.id.indexOf("___"));
	}
	
	if(node.id.indexOf("org_") === -1){
		curTreeId1="#"+node.id
	}else{
		curTreeId2="#"+node.id;
	}
	
	
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	url += ("?orgID="+org_id+"&orgType="+node.getAttribute('orgType'));

	var outgoingName = "";
	outgoingName = node.getAttribute('outgoingName');
	var results = null;
	$.ajaxSetup({async:false});
	if(treeNum === 1){
		$.getJSON(url,function(data){
			results = data;
		});	
		results.outgoingName = outgoingName;
		insertUser(results);

		gSaveObject.restore();
	}
	
	url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	url += "?deptID="+org_id;
	$.getJSON(url,function(data){
		results = data;
	});
	addAttrOrg(results, node);
	//gSaveObject.restore();
}

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	
	tbl.empty();
	if(results.length > 0){
		for(var i = 0; i < results.length; i++){
			var user = results[i];
			if (results.outgoingName != "") {
				user.deptName = results.outgoingName;
			}
			var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' rowOrd='"+ i;
			row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";
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
}

//부서 정보에 해당 조직정보를 추가 한다.
function addAttrOrg(result, node){
	
	var tmpValue = "0";
	if(result!==null){
		node.setAttribute('orgCode' 				, result.orgCode); 	    //조직 코드
		if(result.isDeleted === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		node.setAttribute('isDeleted'				, tmpValue); //삭제여부

		if(result.isInspection === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		
		node.setAttribute('isInspection'			, tmpValue); //감사과 여부

		if(result.isInstitution === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		
		node.setAttribute('isInstitution'			, tmpValue); //기관 여부

		if(result.isODCD === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		
		node.setAttribute('isODCD'					, tmpValue);	//문서과 여부
		if(result.isProxyDocHandleDept === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		node.setAttribute('isProxyDocHandleDept'	, tmpValue); //대리 문서 처리과 여부
		node.setAttribute('orgAbbrName'				, result.orgAbbrName); //조직 약어명
		
		if(result.isProcess === true)
		{
			tmpValue = "1";
		}else{
			tmpValue = "0"
		}
		node.setAttribute('isProcess'				, tmpValue); //처리과 여부
		
		node.setAttribute('outGoingName', result.outgoingName);
	}
}
//----------------------------부서 트리 관련 이벤트 끝-------------------------------------------//
//----------------------------사용자 테이블 이벤트 시작 -----------------------------------------//
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
	try { document.selection.empty(); } catch (e) {}
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
	if(curTabMark === 'I3'){ // 수신자기호
		AddSymbolListByJSTree();
	}
	else if(curTabMark === 'I4'){ // 행정기관
		AddLDAPList();
	}
	else		
		AddList();
}

function includeChildrenDept()
{
	try { document.selection.empty(); } catch (e) {}
	var userCheck = false; //부서원인지 부서인지 체크한다.
	var recvObj = new Object();
	var selRecvCnt = 0;
	
	var treeNode = $.tree.reference("#org_tree").selected;
	
	var isODCd = "";
	var isProcess = "";
	var isInstitution = "0"; //기관여부(대외만 사용)
	var tbRecvLines = $('#tbRecvLines tbody');
	
	if(treeNode){
		 isODCd = treeNode.attr('isODCD'); //문서과 여부
		 isProcess = treeNode.attr('isProcess'); //처리과
		 isInstitution = treeNode.attr('isInstitution');//기관여부
	}

	if(!treeNode){
		return;
	}

	if (isInstitution == "1") {
		DET = DET003;	
		if (isProcess == "1") {
			DET = DET002;		
		}	
	}
	else DET = DET002;	

	recvObj.receiverType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
		,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
		,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId = "", recvObj.outgoingName = "" ,recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF
	recvObj.enfType = DET;
	
	if(isProcess !== "1"){
		if(isInstitution !== "1"){
			// alert('<spring:message code="approval.msg.applines.no.dept" />');
			var url = "<%=webUri%>/app/common/AllDepthOrgTreeAjax.do";
			url += ("?orgID=" + treeNode.attr('orgID') + "&treeType=0");
		
			var results = null;
			
			$.ajaxSetup({async:false});
			$.getJSON(url,function(data){
				results = data;
			});
		
			makeChildrenDeptList(results);
			return;
		}
	}

	recvObj.receiverType = DRU001;
	recvObj.recvCompId = treeNode.attr('rootOrgId');
	recvObj.recvDeptId = treeNode.attr('orgID');
	recvObj.recvDeptName = treeNode.attr('orgName');
	recvObj.recvOutgoingName = treeNode.attr('outgoingName');
	recvObj.recvChiefName = treeNode.attr('chiefName'); // jth8172 2012 신결재 TF
	
	if('${userProfile.deptId}' === recvObj.recvDeptId){
		alert('<spring:message code="approval.msg.applines.draft.dept" />');
		return;
	}
				
	recvObj.recvUserId = "";
	recvObj.recvUserName = "";
	if (recvObj.recvOutgoingName == "") {
		recvObj.sName = recvObj.recvDeptName;
	} else {
		recvObj.sName = recvObj.recvOutgoingName;
	}
	recvObj.sRef = recvObj.refDeptName;
	
	var symbol1 = $('#' + treeNode.attr('rootOrgId')).attr('addrSymbol');
	var symbol2 = treeNode.attr('addrSymbol');

	if(symbol2 !== ""){
		recvObj.sSymbol = symbol2;
		recvObj.recvSymbol = symbol2;
	}
	else
	{
		if(symbol1 !== ""){
			recvObj.sSymbol = symbol1;
			recvObj.recvSymbol = symbol1;
		}else{
			recvObj.sSymbol = "&nbsp;";
		}
	}
	
	var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

	recvObj.newFlg = 'Y';
	
	var row = MakeReciver(id, recvObj);

	var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	
	if(tmpTr.length === 0){	
		tbRecvLines.append(row);
		selRecvCnt++;
	}else{				
		alert('<spring:message code="approval.msg.applines.addeddept" />');
		return;
	}

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);	
	
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;

	var url = "<%=webUri%>/app/common/AllDepthOrgTreeAjax.do";
	url += ("?orgID=" + recvObj.recvDeptId + "&treeType=0");

	var results = null;
	
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});

	makeChildrenDeptList(results);

}

function makeChildrenDeptList(results)
{
	var selRecvCnt = 0;
	
	for(var i=0; i < results.length; i++) {
		var recvObj = new Object();
		
		var isODCd = "";
		var isProcess = "";
		var isInstitution = "0"; //기관여부(대외만 사용)
		var tbRecvLines = $('#tbRecvLines tbody');
		
		 isODCd = results[i].isODCD; //문서과 여부
		 isProcess = results[i].isProcess; //처리과
		 isInstitution = results[i].isInstitution; //기관여부

		if (isInstitution == "1") {
			DET = DET003;	
			if (isProcess == "1") {
				DET = DET002;		
			}	
		}
		else DET = DET002;	

		recvObj.receiverType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
			,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
			,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId = "", recvObj.outgoingName = "" ,recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF
		recvObj.enfType = DET;
		
		recvObj.receiverType = DRU001;
		recvObj.recvCompId = results[i].companyId;
		recvObj.recvDeptId = results[i].orgID;
		recvObj.recvDeptName = results[i].orgName;
		recvObj.recvOutgoingName = results[i].outgoingName;
		recvObj.recvChiefName = results[i].chiefName; // jth8172 2012 신결재 TF
		
		if('${userProfile.deptId}' === recvObj.recvDeptId){
			continue;
		}
					
		recvObj.recvUserId = "";
		recvObj.recvUserName = "";
		if (recvObj.recvOutgoingName == "") {
			recvObj.sName = recvObj.recvDeptName;
		} else {
			recvObj.sName = recvObj.recvOutgoingName;
		}
		recvObj.sRef = recvObj.refDeptName;
		
		var symbol1 = results[i].addrSymbol;
		var symbol2 = results[i].addrSymbol;

		if(symbol2 !== ""){
			recvObj.sSymbol = symbol2;
			recvObj.recvSymbol = symbol2;
		}
		else
		{
			if(symbol1 !== ""){
				recvObj.sSymbol = symbol1;
				recvObj.recvSymbol = symbol1;
			}else{
				recvObj.sSymbol = "&nbsp;";
			}
		}
		
		var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

		recvObj.newFlg = 'Y';
		
		var row = MakeReciver(id, recvObj);

		var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
		
		if(tmpTr.length === 0){	
			tbRecvLines.append(row);
			selRecvCnt++;
		}else{				
			// alert('<spring:message code="approval.msg.applines.addeddept" />');
			continue;
		}
	}

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);	
}

//수신자 추가시
function AddList()
{
	try { document.selection.empty(); } catch (e) {}
	var userCheck = false; //부서원인지 부서인지 체크한다.
	var recvObj = new Object();
	
	var treeNode = null;
	
	if(curTabMark === 'I0'){
		treeNode = $.tree.reference("#org_tree").selected;
	} else if (curTabMark === 'I1') {
		treeNode = $.tree.reference("#org_tree2").selected;
	} else
		treeNode = $.tree.focused().selected;
	
	var isODCd = "";
	var isProcess = "";
	var isInstitution = "0"; //기관여부(대외만 사용)
	var tbRecvLines = $('#tbRecvLines tbody');
	var selRecvCnt = 0;
	
	if(treeNode){
		 isODCd = treeNode.attr('isODCD'); //문서과 여부
		 isProcess = treeNode.attr('isProcess'); //처리과
		 isInstitution = treeNode.attr('isInstitution');//기관여부
	}
	
	if(curTabMark === 'I0'){ //대내 선택시
		if(!treeNode){
			return;
		}

		if (isInstitution == "1") {
			DET = DET003;	
			if (isProcess == "1") {
				DET = DET002;		
			}	
		}
		else DET = DET002;	
		
		recvObj.receiverType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
			,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
			,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId = "", recvObj.outgoingName = "", recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF


		 // 사용자가 수신자일 경우
		if(gSaveObject.count() > 0) 
		{ 
			if(owndept === gSaveObject.first().attr('deptId')){
				alert('<spring:message code="approval.msg.applines.draft.dept" />');
				return;
			}
			
			//if(isODCd !== "1" && isProcess !== "1"){	
			if(isProcess !== "1"){	
				alert('<spring:message code="approval.msg.applines.no.dept" />');
				return;
			}
	
			recvObj.receiverType = DRU002;
			recvObj.enfType = DET;
	
			for(var i = 0; i < gSaveObject.count(); i++) {
				var user = gSaveObject.collection()[i];	
					
				recvObj.recvDeptId = user.attr('deptID');
				recvObj.recvDeptName = user.attr('deptName');
				recvObj.recvOutgoingName = user.attr('outgoingName');
				recvObj.recvUserId = user.attr('id');
				recvObj.recvUserName = user.attr('userName');
				recvObj.sName = recvObj.recvDeptName;
				recvObj.sRef = recvObj.recvUserName;
				recvObj.recvCompId = user.attr('compID');

				var symbol = $('#' + user.attr('deptID')).attr('addrSymbol');
				recvObj.recvSymbol = symbol;
				
				if(symbol === ""){
					recvObj.sSymbol = "&nbsp;";
				}else{
					recvObj.sSymbol = symbol
				}
				
				//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
				var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);
				recvObj.newFlg = 'Y';
				
				var row = MakeReciver(id, recvObj);
	
				var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	
				if(tmpTr.length === 0){	
					tbRecvLines.append(row);
					selRecvCnt ++;
				}else{				
					alert('<spring:message code="approval.msg.applines.ckchoice" />');
				}
				
			}  // for
			
			userCheck = true;

			// 수신자 추가할때 총 합을 셋팅
			var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
			$("#recvCnt").text(recvCnt);
			$("#recvCntForm").val(recvCnt);		
		} // 사용자가 수신자 이면
	
		if(isProcess !== "1"){
			if(isInstitution !== "1"){
				alert('<spring:message code="approval.msg.applines.no.dept" />');
				return;
			}
		}
	
		if(!userCheck) { //대내 부서를 선택한 경우(사용자 말고)
			recvObj.receiverType = DRU001;
			var rootOrgId = treeNode.attr('rootOrgId')
			recvObj.recvCompId = rootOrgId;
			
			if(DET === DET002 || DET === DET003) {
				if ( isInstitution == "1" )
				{
					// 추가하고자 하는 부서가 기관이면..
					recvObj.recvDeptId = treeNode.attr('orgID');
					recvObj.recvDeptName = treeNode.attr('orgName');
				}
				else
				{
					// 사용자 소속기관 가져오기.
					var loginUserInstitutionDeptId = getLoginUserInstitutionInfo();
					// 인접 상위기관 가져오기.
					var institutionInfo = getAdjacentInstitutionInfo(treeNode);
					var adjacentInstitutionDeptId = institutionInfo.id;
					var adjacentInstitutionDeptName = institutionInfo.name;

					if ( loginUserInstitutionDeptId == adjacentInstitutionDeptId )
					{
						// 로그인사용자 소속기관이 인접 상위기관과 같은 경우.
						recvObj.recvDeptId = treeNode.attr('orgID');
						recvObj.recvDeptName = treeNode.attr('orgName');
						DET = DET002;
					}
					else 
					{
						// 로그인사용자 소속기관이 인접 상위기관과 다른 경우.
						recvObj.recvDeptId = adjacentInstitutionDeptId;
						recvObj.recvDeptName = adjacentInstitutionDeptName;
						recvObj.refDeptId = treeNode.attr('orgID');
						recvObj.refDeptName = treeNode.attr('orgName');
						DET = DET003;
					}
				}
			} else {
				recvObj.recvDeptId = treeNode.attr('rootOrgId');
				recvObj.recvDeptName = treeNode.attr('rootOrgName');
				recvObj.refDeptId = treeNode.attr('orgID');
				recvObj.refDeptName = treeNode.attr('orgName');
			}
			recvObj.recvOutgoingName = treeNode.attr('outgoingName');
			recvObj.recvChiefName = treeNode.attr('chiefName'); // jth8172 2012 신결재 TF
			
			if(owndept === recvObj.recvDeptId){
				alert('<spring:message code="approval.msg.applines.draft.dept" />');
				return;
			}
						
			recvObj.recvUserId = "";
			recvObj.recvUserName = "";
			if (recvObj.recvOutgoingName == "") {
				recvObj.sName = recvObj.recvDeptName;
			} else {
				recvObj.sName = recvObj.recvOutgoingName;
			}
			recvObj.sRef = recvObj.refDeptName;
			//recvObj.sRef = (recvObj.refDeptName == "" ?"&nbsp;":recvObj.refDeptName);
			//recvObj.sSymbol = treeNode.attr('addrSymbol');
			
			var symbol1 = $('#' + treeNode.attr('rootOrgId')).attr('addrSymbol');

			var symbol2 = treeNode.attr('addrSymbol');

			if(symbol2 !== ""){
				recvObj.sSymbol = symbol2;
				recvObj.recvSymbol = symbol2;
			}else{
				if(symbol1 !== ""){
					recvObj.sSymbol = symbol1;
					recvObj.recvSymbol = symbol1;
				}else{
					recvObj.sSymbol = "&nbsp;";
				}
			}
			
			//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
			var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

			recvObj.newFlg = 'Y';
			recvObj.enfType = DET;

			var row = MakeReciver(id, recvObj);

			var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
			
			if(tmpTr.length === 0){	
				tbRecvLines.append(row);

				selRecvCnt ++;
			}else{				
				alert('<spring:message code="approval.msg.applines.addeddept" />');
			}

			// 수신자 추가할때 총 합을 셋팅
			var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
			$("#recvCnt").text(recvCnt);
			$("#recvCntForm").val(recvCnt);
			
		} //대내 부서 선택 끝

		
	} else if(curTabMark === 'I1'){//그룹 선택시
		recvObj.receiverType = "" ,recvObj.enfType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
			,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
			,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId="", recvObj.recvOutgoingName="",recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF

		if(!treeNode){
			return;
		}

		DET = DET003;	
		
		recvObj.enfType = DET;

		if("0" === treeNode.attr('orgType')){
			return;
		}
		
		recvObj.receiverType = DRU001;//부서
		recvObj.recvCompId = treeNode.attr('rootOrgId')
		
		if("1" === treeNode.attr('orgType')){ //그룹선정
			if('${userProfile.compId}' === treeNode.attr('orgId')){
				alert('<spring:message code="approval.msg.applines.no.recv2" />');
				return;
			}
			recvObj.receiverType = DRU001;
			recvObj.recvDeptId = treeNode.attr('orgID');
			recvObj.recvDeptName = treeNode.attr('orgName');
			recvObj.recvOutgoingName = treeNode.attr('outgoingName');
			recvObj.refDeptId = "";
			recvObj.refDeptName = "";
			recvObj.recvUserId = "";
			recvObj.recvUserName = "";
			recvObj.recvChiefName =  $('#org_' + treeNode.attr('rootOrgId')).attr('chiefName'); // jth8172 2012 신결재 TF
			//recvObj.sRef = "";
			var symbol = treeNode.attr('addrSymbol');

			if(recvObj.recvOutgoingName == "") {
				recvObj.sName = recvObj.recvDeptName;
			} else {
				recvObj.sName = recvObj.recvOutgoingName;
			}

			recvObj.recvSymbol = symbol;
				
			if(symbol === ""){
				recvObj.sSymbol = "&nbsp;";
			}else{
				recvObj.sSymbol = symbol;
			}

			//기관여부 체크
			if(isInstitution !== "1"){
				alert('<spring:message code="approval.msg.applines.noinstitution" />');
				return;
			}

			//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
			var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

			recvObj.newFlg = 'Y';
			var row = MakeReciver(id, recvObj);
			
			var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	
			
			if(tmpTr.length === 0){	
				tbRecvLines.append(row);

				selRecvCnt++;
			}else{				
				alert('<spring:message code="approval.msg.applines.addeddept" />');
			}

			// 수신자 추가할때 총 합을 셋팅
			var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
			$("#recvCnt").text(recvCnt);
			$("#recvCntForm").val(recvCnt);
			
		} else { //부서면
			if('${userProfile.compId}' === treeNode.attr('rootOrgId')){
				alert('<spring:message code="approval.msg.applines.no.recv2" />');
				return;
			}

			if(isProcess !== "1"){
				if(isInstitution !== "1"){
					alert('<spring:message code="approval.msg.applines.no.dept" />');
					return;
				}
			}

			recvObj.receiverType = DRU001;

			if(isInstitution == "1") {
				recvObj.recvDeptId = treeNode.attr('orgID');
				recvObj.recvDeptName = treeNode.attr('orgName');
			} 
			else {
				var institutionInfo = getAdjacentInstitutionInfo(treeNode);
				var adjacentInstitutionDeptId = institutionInfo.id;
				var adjacentInstitutionDeptName = institutionInfo.name;

				recvObj.recvDeptId = adjacentInstitutionDeptId;
				recvObj.recvDeptName = adjacentInstitutionDeptName;
				
				recvObj.refDeptId = treeNode.attr('orgID');
				recvObj.refDeptName = treeNode.attr('orgName');
			}
			
			recvObj.recvRootOutgoingName = treeNode.attr('rootOutgoingName');
			recvObj.recvOutgoingName = treeNode.attr('outgoingName');
			recvObj.recvUserId = "";
			recvObj.recvUserName = "";
			recvObj.refSymbol = treeNode.attr('addrSymbol'); // jth8172 2012 신결재 TF
			recvObj.recvChiefName =  $('#org_' + treeNode.attr('rootOrgId')).attr('chiefName'); // jth8172 2012 신결재 TF
			recvObj.refChiefName = treeNode.attr('chiefName'); // jth8172 2012 신결재 TF

			if (recvObj.recvRootOutgoingName == "") {
				recvObj.sName = recvObj.recvDeptName;
			} else {
				recvObj.sName = recvObj.recvRootOutgoingName;
			}

			if (recvObj.recvOutgoingName == "") {
				recvObj.sRef = recvObj.refDeptName;
			} else {
				recvObj.sRef = recvObj.recvOutgoingName;
			}


			
			var symbol1 = $('#org_' + treeNode.attr('rootOrgId')).attr('addrSymbol');
			var cheif1 = $('#org_' + treeNode.attr('rootOrgId')).attr('chiefName'); // jth8172 2012 신결재 TF
			var symbol2 = treeNode.attr('addrSymbol');
			
/*
 *			그룹사 선택시 수신자기호는 회사, 부서 별도로 지정하도록 변경함 jth8172 	
 
 			if(symbol2 !== ""){
				recvObj.sSymbol = symbol2;
				recvObj.recvSymbol = symbol2;
			}else{
				if(symbol1 !== ""){
					recvObj.sSymbol = symbol1;
					recvObj.recvSymbol = symbol1;
				}else{
					recvObj.sSymbol = "&nbsp;";
				}
			}

*/
			recvObj.sSymbol = symbol2; // jth8172 2012 신결재 TF
			recvObj.recvSymbol = symbol1; // jth8172 2012 신결재 TF
			recvObj.recvChiefName = cheif1; // jth8172 2012 신결재 TF

			//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
			var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

			recvObj.newFlg = 'Y';
			var row = MakeReciver(id, recvObj);
			
			var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	
			
			if(tmpTr.length === 0){	
				tbRecvLines.append(row);

				selRecvCnt++;
			}else{				
				alert('<spring:message code="approval.msg.applines.addeddept" />');
			}

			// 수신자 추가할때 총 합을 셋팅
			var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
			$("#recvCnt").text(recvCnt);
			$("#recvCntForm").val(recvCnt);
			
		}
		
	} else { //외부기관 선택시
		recvObj.receiverType = "" ,recvObj.enfType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
			,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
			,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId="",recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF

		if(curRadioNum === 1){
			recvObj.receiverType = DRU001;
			DET = DET005;
			
			recvObj.recvDeptName 	= escapeJavaScript($('#txtRecvAgen0').val());
			recvObj.refDeptName 	= escapeJavaScript($('#txtRefAgen0').val());
			recvObj.sSymbol 		= escapeJavaScript($('#txtRecvSymbol0').val());
			recvObj.recvSymbol 		= recvObj.sSymbol;
			
			if(recvObj.recvDeptName === ""){
				alert('<spring:message code="approval.msg.applines.field01" />');
				return;
			}
			
			recvObj.sName = recvObj.recvDeptName;
			recvObj.sRef  = recvObj.refDeptName;
			
			//recvObj.sRef   = (recvObj.sRef === ""? "&nbsp;": recvObj.sRef);
			recvObj.sSymbol = (recvObj.sSymbol === ""? "&nbsp;": recvObj.sSymbol);
			
		}else if(curRadioNum === 2){
			recvObj.receiverType = DRU001;
			DET = DET006;
		
			recvObj.recvDeptName = escapeJavaScript($('#txtRecvAgen1').val());
			recvObj.refDeptName = escapeJavaScript($('#txtRefAgen1').val());
			recvObj.fax = $('#txtFax1').val();
			if(recvObj.recvDeptName === ""){
				alert('<spring:message code="approval.msg.applines.field01" />');
				return;
			}
			
			recvObj.sName = recvObj.recvDeptName;
			recvObj.sRef  = recvObj.refDeptName;

			//recvObj.sRef   = (recvObj.sRef === ""? "&nbsp;": recvObj.sRef);
			recvObj.sSymbol = "&nbsp;";
			
		}else{
			recvObj.receiverType = DRU002;
			DET = DET007;
			
			recvObj.recvDeptName = escapeJavaScript($('#txtRecv2').val());
			recvObj.postNumber = escapeJavaScript($('#txtZipCode2').val());
			recvObj.address = escapeJavaScript($('#txtAddr21').val()) + " " + escapeJavaScript($('#txtAddr22').val()); 
			recvObj.telephone = $('#txtPhone2').val();
			recvObj.fax = $('#txtFax2').val();

			if(recvObj.recvDeptName === ""){
				alert('<spring:message code="approval.msg.applines.field02" />');
				return;
			}
			
			recvObj.sName = recvObj.recvDeptName;
			recvObj.sRef  = recvObj.refDeptName;

			//recvObj.sRef   = (recvObj.sRef === ""? "&nbsp;": recvObj.sRef);
			recvObj.sSymbol = "&nbsp;";
		}

		recvObj.enfType = DET;

		var idDate = new Date();
		var id = recvObj.enfType
			 + new String(idDate.getYear())
		     + new String(idDate.getMonth())
			 + new String(idDate.getDate()) 
			 + new String(idDate.getDay()) 
			 + new String(idDate.getHours()) 
			 + new String(idDate.getMinutes()) 
			 + new String(idDate.getSeconds()) 
			 + new String(idDate.getMinutes());

		//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
		recvObj.newFlg = 'Y';
		var row = MakeReciver(id, recvObj);

		
		//var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');

		var tmpTr = $('#tbRecvLines tbody tr[recvDeptName="'+recvObj.recvDeptName+'"][refDeptName="'+recvObj.refDeptName+'"]');
		
		if(tmpTr.length === 0){	
			tbRecvLines.append(row);
			clearInputs();

			selRecvCnt++;
		}else{				
			alert('<spring:message code="approval.msg.applines.addeddept" />');
		}

		// 수신자 추가할때 총 합을 셋팅
		var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
		$("#recvCnt").text(recvCnt);
		$("#recvCntForm").val(recvCnt);
	}
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;
}

function getParentInstitutionInfo(curNode)
{
	var parentInfo = new Object();
	parentInfo.id = '';
	parentInfo.name = '';
	parentInfo.isInstitution = '';
	
	var parentNode = $.tree.reference('#org_tree2').parent(curNode);

	if ( parentNode == -1 ) return;

	var url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	var results = '';
	url += "?deptID=" + parentNode.attr('orgID');
	
	$.getJSON(url,function(data){
		results = data;
	});
	
	addAttrOrg(results, parentNode.get(0)); // 두번째 인자는 DOM 객체로 변환하여 호출.

	parentInfo.id = parentNode.attr('orgID');
	parentInfo.name = parentNode.attr('orgName');
	parentInfo.isInstitution = parentNode.attr('isInstitution');
	
	return parentInfo;
}

function getAdjacentInstitutionInfo(curNode)
{
	var parentInfo = new Object();
	parentInfo.id = '';
	parentInfo.name = '';
	parentInfo.isInstitution = '';
	parentInfo.orgType = '';
	
	var parentNode = $.tree.reference('#org_tree2').parent(curNode);
	
	if(parentNode == -1){ //왜 root정보는 오브젝트가 아니라 인트값이냐... 당췌 모르겠다.
		/* parentInfo.id = '1000000';
		parentInfo.name = '벽산건재계열';
		parentInfo.isInstitution = '0';
		parentInfo.orgType = parentNode.attr('orgType'); */
		alert('선택한 부서의 상위기관을 찾을 수 없습니다.');
	}else{
		var url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
		var results = '';
		
		url += "?deptID=" + parentNode.attr('orgID');
		
		$.getJSON(url,function(data){
			results = data;
		});
		
		addAttrOrg(results, parentNode.get(0)); // 두번째 인자는 DOM 객체로 변환하여 호출.
		
		parentInfo.id = parentNode.attr('orgID');
		parentInfo.name = parentNode.attr('orgName');
		parentInfo.isInstitution = parentNode.attr('isInstitution');
		parentInfo.orgType = parentNode.attr('orgType');

		if ( parentInfo.isInstitution != '1' )
		{	
			parentInfo = getAdjacentInstitutionInfo(parentNode);
		}
	}
	
	return parentInfo;
}

function getLoginUserInstitutionInfo()
{
	var loginUserInstitutionDeptId = '';
	
	var url = "<%=webUri%>/app/common/LoginUserInstitutionInfoAjax.do";
	var result = '';
	
	$.getJSON(url,function(data){
		result = data;
	});

	if(result!==null){
		loginUserInstitutionDeptId = result.orgID; 	 
	}
	
	return loginUserInstitutionDeptId;
}

function AddSymbolListByAjax(ajaxObj)
{
	try { document.selection.empty(); } catch (e) {}
	var recvObj = new Object();
	
	var isProcess = "";
	var isInstitution = "";
	var tbRecvLines = $('#tbRecvLines tbody');

	isInstitution = eval(ajaxObj.isInstitution)==true?"1":"0"; //기관
	isProcess = eval(ajaxObj.isProcess)==true?"1":"0"; //처리과

	if (isInstitution == "1") {
		DET = DET003;	
		if (isProcess == "1") {
			DET = DET002;		
		}	
	}
	else DET = DET002;	

	recvObj.receiverType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
		,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
		,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId = "", recvObj.outgoingName = "" ,recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF
	recvObj.enfType = DET;
	
	
	if(isProcess !== "1"){
		if(isInstitution !== "1"){
			alert('<spring:message code="approval.msg.applines.no.dept" />');
			return;
		}
	}

	recvObj.receiverType = DRU001;
	recvObj.recvCompId = ajaxObj.companyID;	
	recvObj.recvDeptId = ajaxObj.orgID;
	recvObj.recvDeptName = ajaxObj.orgName;
	recvObj.recvOutgoingName = ajaxObj.outgoingName;
	recvObj.recvChiefName = ajaxObj.chiefPosition; // jth8172 2012 신결재 TF
	
	if('${userProfile.deptId}' === recvObj.recvDeptId){
		alert('<spring:message code="approval.msg.applines.draft.dept" />');
		return;
	}
				
	recvObj.sName = ajaxObj.orgName;
	recvObj.recvSymbol = ajaxObj.addrSymbol;
	recvObj.sSymbol = ajaxObj.addrSymbol;

	$.tree.reference("#org_tree3").select_branch("#" + ajaxObj.orgID + "___"); 
	
	var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

	recvObj.newFlg = 'Y';
	
	var row = MakeReciver(id, recvObj);

	var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	var selRecvCnt = 0;
	
	if(tmpTr.length === 0){	
		tbRecvLines.append(row);
		selRecvCnt ++;
	}else{				
		alert('<spring:message code="approval.msg.applines.addeddept" />');
	}

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);	
		
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;
}

function AddSymbolListByJSTree()
{
	try { document.selection.empty(); } catch (e) {}
	var recvObj = new Object();
	
	var treeNode = $.tree.reference("#org_tree3").selected;
	var isProcess = false;
	var isInstitution = false;
	var tbRecvLines = $('#tbRecvLines tbody');
	
	if(treeNode){
		 isProcess = treeNode.attr('isProcess'); //처리과
		 isInstitution = treeNode.attr('isInstitution');//기관여부
	}

	if(!treeNode){
		return;
	}		

	if (eval(isInstitution) == true) {
		DET = DET003;	
		if (eval(isProcess) == true) {
			DET = DET002;		
		}	
	}
	else DET = DET002;	

	recvObj.receiverType = "" ,recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
		,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
		,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvCompId = "", recvObj.outgoingName = "" ,recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF
	recvObj.enfType = DET;

	if(eval(isProcess) != true){
		if(eval(isInstitution) != true){
			alert('<spring:message code="approval.msg.applines.no.dept" />');
			return;
		}
	}

	var _recvDeptId = treeNode.attr('orgID');

	if ( _recvDeptId.indexOf("___") > -1 )
	{
		_recvDeptId = _recvDeptId.substr(0, _recvDeptId.indexOf("___"));
	}

	recvObj.receiverType 	= DRU001;
	recvObj.recvCompId 		= treeNode.attr('companyID');
	recvObj.recvDeptId 		= _recvDeptId
	recvObj.recvDeptName 	= treeNode.attr('orgName');
	recvObj.sName 			= treeNode.attr('orgName');
	recvObj.recvOutgoingName = treeNode.attr('outgoingName');
	recvObj.recvChiefName 	= treeNode.attr('chiefName'); // jth8172 2012 신결재 TF
	
	if('${userProfile.deptId}' === recvObj.recvDeptId){
		alert('<spring:message code="approval.msg.applines.draft.dept" />');
		return;
	}
				
	recvObj.recvSymbol = treeNode.attr('addrSymbol');
	recvObj.sSymbol = treeNode.attr('addrSymbol');
	
	var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

	recvObj.newFlg = 'Y';
	
	var row = MakeReciver(id, recvObj);

	var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	var selRecvCnt = 0;
	
	if(tmpTr.length === 0){	
		tbRecvLines.append(row);
		selRecvCnt ++;
	}else{				
		alert('<spring:message code="approval.msg.applines.addeddept" />');
	}

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);	

	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;
}


function getLDAPInstitutionNode(curNode)
{
	var parentNode = $.tree.focused().parent(curNode);
	return parentNode;
}

function AddLDAPList(expressInsertNode, fromPop)
{
	var isProcess = "N";
	var isInstitution = "N"; //기관여부
	var parentNode = "";

	var recvObj = new Object(); 
	recvObj.recvDeptId = "" ,recvObj.recvDeptName = "" ,recvObj.refDeptId = "" ,recvObj.refDeptName = "" ,recvObj.recvUserId = "" 
		,recvObj.recvUserName = "" ,recvObj.postNumber = "" ,recvObj.address = "" ,recvObj.telephone = "" ,recvObj.fax = "" ,recvObj.recvSymbol = "" ,recvObj.receiverOrder = ""
		,recvObj.sName = "", recvObj.sRef = "", recvObj.sSymbol = "", recvObj.recvOutgoingName="",recvObj.refSymbol = "", recvObj.recvChiefName = "", recvObj.refChiefName = ""; // jth8172 2012 신결재 TF

	recvObj.enfType = DET;
	recvObj.receiverType = DRU001; //부서
	recvObj.recvCompId = '0000000';	// 대한민국 기관코드
	
	if ( typeof expressInsertNode != 'undefined' ) // 키워드 검색 진입.
	{
		var institutionNode = new Object();

		if ( fromPop == 'Y' ) //  키워드 검색 다수 결과 목록 팝업창에서 접근. expressInserNode는 DOM 객체로 넘어온다.
		{
			if ( eval(expressInsertNode.attr('isInstitution')) == false ) {
				// 인접 상위 기관을 가져와서 [수신]으로 셋팅.

				parentNode = expressInsertNode.attr('orgParentID');
				var url = "<%=webUri%>/app/common/OrgLDAPInstitutionAjax.do";
				url += ("?orgID="+parentNode);

				var results = null;
				$.ajaxSetup({async:false});
				$.getJSON(url,function(data){
					results = data;
				});	

				/*
				 	ajax 결과 = 객체명.속성명
				 	DOM 객체 = 객체명.attr("속성명")
				*/

				institutionNode.orgID = results.oucode;
				institutionNode.orgName = results.organizationalUnitName;
				institutionNode.addrSymbol = results.oudocumentRecipientSymbol;
				institutionNode.chiefName = results.ucChiefTitle;
				
				recvObj.recvDeptId = institutionNode.orgID;
				recvObj.sName = institutionNode.orgName;
				recvObj.recvSymbol = institutionNode.addrSymbol;
				recvObj.recvChiefName = institutionNode.chiefName;
				recvObj.refDeptId = expressInsertNode.attr('orgID');
				recvObj.sRef = expressInsertNode.attr('orgName');
				recvObj.refSymbol = expressInsertNode.attr('addrSymbol');
				recvObj.refChiefName = expressInsertNode.attr('chiefPosition');
			}
			else // 수신처가 기관인 경우
			{ 
				recvObj.recvDeptId = expressInsertNode.attr('orgID');
				recvObj.sName = expressInsertNode.attr('orgName');
				recvObj.recvSymbol = expressInsertNode.attr('addrSymbol');
				recvObj.recvChiefName = expressInsertNode.attr('chiefPosition');
				recvObj.refDeptId = "";
				recvObj.sRef = "";
				recvObj.refSymbol = "";
				recvObj.refChiefName = "";
			}
		}
		else // 키워드 exact matching 시 진입. expressInserNode는 일반 자바스크립트 객체로 넘어온다.
		{
			if ( expressInsertNode.isInstitution == false ) {
				// 인접 상위 기관을 가져와서 [수신]으로 셋팅.
				parentNode = expressInsertNode.orgParentID;
				var url = "<%=webUri%>/app/common/OrgLDAPInstitutionAjax.do";
				url += ("?orgID="+parentNode);

				var results = null;
				$.ajaxSetup({async:false});
				$.getJSON(url,function(data){
					results = data;
				});	

				institutionNode.orgID = results.oucode;
				institutionNode.orgName = results.organizationalUnitName;
				institutionNode.addrSymbol = results.oudocumentRecipientSymbol;
				institutionNode.chiefName = results.ucChiefTitle;
				
				recvObj.recvDeptId = institutionNode.orgID;
				recvObj.sName = institutionNode.orgName;
				recvObj.recvSymbol = institutionNode.addrSymbol;
				recvObj.recvChiefName = institutionNode.chiefName;
				recvObj.refDeptId = expressInsertNode.orgID;
				recvObj.sRef = expressInsertNode.orgName;
				recvObj.refSymbol = expressInsertNode.addrSymbol;
				recvObj.refChiefName = expressInsertNode.chiefPosition;
			}
			else // 수신처가 기관인 경우
			{ 
				recvObj.recvDeptId = expressInsertNode.orgID;
				recvObj.sName = expressInsertNode.orgName;
				recvObj.recvSymbol = expressInsertNode.addrSymbol;
				recvObj.recvChiefName = expressInsertNode.chiefPosition;
				recvObj.refDeptId = "";
				recvObj.sRef = "";
				recvObj.refSymbol = "";
				recvObj.refChiefName = "";
			}
		}
	}
	else // jsTree 로부터 진입.
	{
		try { document.selection.empty(); } catch (e) {}
		var treeNode = $.tree.focused().selected;
		var institutionNode = null;
		
		if(treeNode){
			 isProcess = treeNode.attr('isProcess'); 		//처리과
			 isInstitution = treeNode.attr('isInstitution');//기관여부
		}
			
		if(!treeNode){
			return;
		}
		
		if ( isProcess == 'N' ){
			alert('<spring:message code="approval.msg.applines.no.executieve.acency.recv" />');
			return;
		}

		if ( isInstitution == 'N' ) {
			// 인접 상위 기관을 가져와서 [수신]으로 셋팅.
			var isFlag = true;

			institutionNode = treeNode;

			do
			{
				institutionNode = getLDAPInstitutionNode(institutionNode);
				var _isInstitution = institutionNode.attr("isInstitution");
				var _depth = institutionNode.attr("depth");
		
				if ( _isInstitution == 'Y' || _depth == '1' )
				{
					isFlag = false;
				}
			}
			while(isFlag == true)
			
			recvObj.recvDeptId = institutionNode.attr('orgID');
			recvObj.sName = institutionNode.attr('orgName');
			recvObj.recvSymbol = institutionNode.attr('addrSymbol');
			recvObj.recvChiefName = institutionNode.attr('chiefName');
			recvObj.refDeptId = treeNode.attr('orgID');
			recvObj.sRef = treeNode.attr('orgName');
			recvObj.refSymbol = treeNode.attr('addrSymbol');
			recvObj.refChiefName = treeNode.attr('chiefName');
			
		}
		else { // 수신처가 기관인 경우
			recvObj.recvDeptId = treeNode.attr('orgID');
			recvObj.sName = treeNode.attr('orgName');
			recvObj.recvSymbol = treeNode.attr('addrSymbol');
			recvObj.recvChiefName = treeNode.attr('chiefName');
			recvObj.refDeptId = "";
			recvObj.sRef = "";
			recvObj.refSymbol = "";
			recvObj.refChiefName = "";
		}
	}
	
	var tbRecvLines = $('#tbRecvLines tbody');

	var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);
	
	recvObj.newFlg = 'Y';
	var row = MakeReciver(id, recvObj);
	
	var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
	var selRecvCnt = 0;
	
	if(tmpTr.length === 0){	
		tbRecvLines.append(row);
		selRecvCnt ++;
	}else{				
		alert('<spring:message code="approval.msg.applines.addeddept" />');
	}

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);	
			
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;
}

function clearInputs(){
	$('#txtRecvAgen0').val("");  
    $('#txtRefAgen0').val("");   
    $('#txtRecvSymbol0').val("");  
    
    $('#txtRecvAgen1').val("");
    $('#txtRefAgen1').val(""); 
    $('#txtFax1').val("");       
    
    
    $('#txtRecv2').val("");   
    $('#txtZipCode2').val("");
    $('#txtAddr21').val(""); 
    $('#txtAddr22').val("");
    $('#txtPhone2').val("");   
    $('#txtFax2').val("");
}
//수신자 정보에 대한 Row 정보를 생성한다.
function MakeReciver(idn, recvObj)
{
	if(recvObj.enfType === DET006){
		if(recvObj.fax !== "") 
			recvObj.sSymbol = recvObj.fax;
		else
			recvObj.sSymbol = "&nbsp;";
	}
	var id = "tbRecvLines_" +idn;

	var _recvDeptId = recvObj.recvDeptId;

	if ( _recvDeptId.indexOf("___") > -1 )
	{
		_recvDeptId = _recvDeptId.substr(0, _recvDeptId.indexOf("___"));
	}

	var sRef = recvObj.sRef;
	var sSymbol = recvObj.sSymbol;

	if ( sRef == '' || sRef == null ) {
		sRef = '&nbsp;';
	}
	if ( sSymbol == '' || sSymbol == null ) {
		sSymbol = '&nbsp;';
	}
		
	//id = replaceSpace(id);
	var row = "<tr ";
	row += "id='"+id+"' ";
	row += "receiverType='"+recvObj.receiverType + "' ";
	row += "enfType='" + recvObj.enfType       +"' "; 
	row += "recvCompId='" + recvObj.recvCompId       +"' "; 
	row += "recvDeptId='" + _recvDeptId    +"' ";
	row += "recvDeptName='" + recvObj.recvDeptName  +"' ";
	row += "refDeptId='" + recvObj.refDeptId     +"' ";
	row += "refDeptName='" + recvObj.refDeptName   +"' ";
	row += "recvUserId='" + recvObj.recvUserId    +"' ";
	row += "recvUserName='" + recvObj.recvUserName  +"' ";
	row += "postNumber='" + recvObj.postNumber    +"' ";
	row += "address='" + recvObj.address       +"' ";
	row += "telephone='" + recvObj.telephone     +"' ";
	row += "fax= '" + recvObj.fax           +"' ";
	row += "recvSymbol='" + recvObj.recvSymbol    +"' ";
	row += "newFlg='" + recvObj.newFlg +"' ";
	row += "sName='" + recvObj.sName +"' ";
	row += "sRef='" + recvObj.sRef +"' ";
	row += "refSymbol='" + recvObj.refSymbol    +"' "; // jth8172 2012 신결재 TF
	row += "recvChiefName='" + recvObj.recvChiefName    +"' "; // jth8172 2012 신결재 TF
	row += "refChiefName='" + recvObj.refChiefName    +"' "; // jth8172 2012 신결재 TF
	row += " onclick='onLineClick($(\"#"+id+"\"));' onDblClick='onDeleteLine();' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+id +"\"));' >";
	row += "<td width='200' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid'>" +recvObj.sName + "</td>"; 
	row += "<td width='200' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+"'>" + sRef + "</td>"; 
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-right:1pt solid "+tbColor+"'>" + sSymbol + "</td>"; 
	row += "</tr>";
	return row;
}

//----------------------------사용자 테이블 이벤트 끝 -------------------------------------------//
//----------------------------수신자 테이블 이벤트 시작 -----------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
	//viewLineInfo(obj);
}

function viewLineInfo(obj){
	if(obj.attr("enfType") === DET002){
		selectTab(1);
		onClickTab('I0');
	}else if(obj.attr("enfType") === DET003){
		selectTab(2);
		onClickTab('I1');
	}else if(obj.attr("enfType") === DET005 || obj.attr("enfType") === DET006 ||obj.attr("enfType") === DET007){
		selectTab(3);
		onClickTab('I2');
	} 
	
	if(obj.attr("enfType") === DET005){
		var recvType0 =  $('#recvType0');
		recvType0.trigger('click');
		onRecvType(recvType0[0]);

		$('#txtRecvAgen0').val(obj.attr('recvDeptName'))  
		$('#txtRefAgen0').val(obj.attr('refDeptName'))   
		$('#txtRecvSymbol0').val(obj.attr('recvSymbol'))
				
	}else if(obj.attr("enfType") === DET006){
		var recvType1 =  $('#recvType1');
		recvType1.trigger('click');
		onRecvType(recvType1[0]);
		
		$('#txtRecvAgen1').val(obj.attr("recvDeptName"));
		$('#txtRefAgen1').val(obj.attr("refDeptName"));
		$('#txtFax1').val(obj.attr("fax"));
	}else if(obj.attr("enfType") === DET007){
		var recvType2 =  $('#recvType2');
		recvType2.trigger('click');
		onRecvType(recvType2[0]);

		$('#txtRecv2').val(obj.attr('recvDeptName'));
		$('#txtZipCode2').val(obj.attr('postNumber'));			
		$('#txtAddr21').val(obj.attr('address'));
		$('#txtPhone2').val(obj.attr('telephone'));
		$('#txtFax2').val(obj.attr('fax'));
	}
	
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

//라인 선택시 처리하는 함수
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
	try { document.selection.empty(); } catch (e) {}
}
//----------------------------수신자 테이블 이벤트 끝 -----------------------------------------//
//----------------------------텝 클릭 이벤트 시작 --------------------------------------------//
function onClickTab(tMark){
	var tab01 = $('#divInternal');
	var tab02 = $('#divOuter');
	var tab03 = $('#divOutPlace');
	var tab04 = $('#divRecvSymbol');
	var tab05 = $('#divLDAP');
	var tab06 = $('#divRecvGroup');

	var btnLine = $('#divBtnRecvLine');
	var btnGroup = $('#divBtnRecvGroup');
	curTabMark = tMark;
	
	switch(tMark){
	case 'I0':
		tab01.show();
		tab02.hide();
		tab03.hide();
		tab04.hide();
		tab05.hide();
		tab06.hide();
		btnLine.show();
		btnGroup.hide();
		DET = DET002;
		$.tree.focused().select_branch(curTreeId1);
		$.tree.focused().deselect_branch(curTreeId2);
		$('#searchDept').val('');
		$('#div_dept_search').show();
		$('#keyword_label').html('<spring:message code="bind.obj.dept.name" />'); 
		$('#search_label').html('<spring:message code="approval.msg.recvPopInfo.head" />'); 
		$('#includeChildrenBtn').show();
		break;
	case 'I1':
		tab02.show();
		tab01.hide();
		tab03.hide();
		tab04.hide();
		tab05.hide();
		tab06.hide();
		btnLine.show();
		btnGroup.hide();
		DET = DET003;
		$.tree.focused().deselect_branch(curTreeId2);
		$.tree.focused().deselect_branch(curTreeId1);
		$('#searchDept').val('');
		$('#div_dept_search').show();
		$('#keyword_label').html('<spring:message code="bind.obj.dept.name" />');
		$('#search_label').html('<spring:message code="approval.msg.recvPopInfo.head" />'); 
		$('#includeChildrenBtn').hide();
		break;
	case 'I2':
		tab03.show();
		tab01.hide();
		tab02.hide();
		tab04.hide();
		tab05.hide();
		tab06.hide();
		btnLine.show();
		btnGroup.hide();

		if ( curRadioNum === 1 )
		{
			DET = DET005;
		}
		else if ( curRadioNum === 2 )
		{
			DET = DET006;
		}
		else if ( curRadioNum === 3 )
		{
			DET = DET007;
		}

		$('#searchDept').val('');
		$('#div_dept_search').hide();
		$('#includeChildrenBtn').hide();
		break;
	case 'I3':
		tab04.show();
		tab01.hide();
		tab02.hide();
		tab03.hide();
		tab05.hide();
		tab06.hide();
		btnLine.show();
		btnGroup.hide();
		DET = DET002; // 대내와 동일.
		$('#searchDept').val('');
		$('#div_dept_search').show();
		$.tree.reference("#org_tree3").select_branch(curTreeId1 + '___');
		$('#keyword_label').html('<spring:message code="bind.obj.symbol.name" />'); 
		$('#search_label').html('<spring:message code="approval.msg.recvPopInfo.symbol.head" />'); 
		$('#includeChildrenBtn').hide();
		break;	
	case 'I4':
		tab05.show();
		tab01.hide();
		tab02.hide();
		tab03.hide();
		tab04.hide();
		tab06.hide();
		DET = DET011;
		$('#searchDept').val('');
		$('#div_dept_search').show();
		$('#keyword_label').html('<spring:message code="bind.obj.executive.agency.name" />'); 
		$('#search_label').html('<spring:message code="approval.msg.recvPopInfo.agency.head" />'); 
		$('#includeChildrenBtn').hide();
		break;			
	case 'I5':		
		var recvGroup = $('#tblRecvGroup tbody').children();		
		if(recvGroup.length > 0){
			onRecvGroupClick($('#'+recvGroup[0].id));
		}
		tab06.show();
		tab01.hide();
		tab02.hide();
		tab03.hide();
		tab04.hide();
		tab05.hide();	

		btnLine.hide();		
		btnGroup.show();

		$('#searchDept').val('');
		$('#div_dept_search').hide();
		$('#includeChildrenBtn').hide();
		break;
	}
}

function checkDisplayAs(checked){
	var txtDisplayAs = $('#txtDisplayAs');
	if(checked){
		txtDisplayAs.show();
	}else{
		txtDisplayAs.hide();
	}
}
//----------------------------텝 클릭 이벤트 끝 --------------------------------------------//
//----------------------------텝3 라디오 버튼 이벤트 시작 ----------------------------------//

function setRecvType(obj){

	var id = obj.attr('id');

	var divRecvType0 = $('#divRecvType0');
	var divRecvType1 = $('#divRecvType1');
	var divRecvType2 = $('#divRecvType2');
	
	if( id == "divRecvType0" ){
		divRecvType0.show();
		divRecvType1.hide();
		divRecvType2.hide();
		DET = DET005;
		curRadioNum = 1;
		$('input:radio[name=recvType]:input[value=0]').attr("checked", true);
	}

	if( id == "divRecvType1" ){
		divRecvType1.show();
		divRecvType0.hide();
		divRecvType2.hide();
		DET = DET006;
		curRadioNum = 2;
		$('input:radio[name=recvType]:input[value=1]').attr("checked", true);
	}

	if( id == "divRecvType2" ){
		divRecvType2.show();
		divRecvType1.hide();
		divRecvType0.hide();
		DET = DET007;
		curRadioNum = 3;
		$('input:radio[name=recvType]:input[value=3]').attr("checked", true);
	}
}

function onRecvType(obj){
	var divRecvType0 = $('#divRecvType0');
	var divRecvType1 = $('#divRecvType1');
	var divRecvType2 = $('#divRecvType2');
	inputInit();

	
	if(obj.value == "0" && obj.checked){
		divRecvType0.show();
		divRecvType1.hide();
		divRecvType2.hide();
		DET = DET005;
		curRadioNum = 1;
	}

	if(obj.value == "1" && obj.checked){
		divRecvType1.show();
		divRecvType0.hide();
		divRecvType2.hide();
		DET = DET006;
		curRadioNum = 2;
	}


	if(obj.value == "2" && obj.checked){
		divRecvType2.show();
		divRecvType1.hide();
		divRecvType0.hide();
		DET = DET007;
		curRadioNum = 3;
	}
}

function inputInit(){
	$('#txtRecvAgen0').val("")  
	$('#txtRefAgen0').val("")   
	$('#txtRecvSymbol0').val("")

	$('#txtRecvAgen1').val("");
	$('#txtRefAgen1').val("");
	$('#txtFax1').val("");

	$('#txtRecv2').val("");
	$('#txtZipCode2').val("");			
	$('#txtAddr21').val("");
	$('#txtAddr22').val("");
	$('#txtPhone2').val("");
	$('#txtFax2').val("");
}
//----------------------------텝3 라디오 버튼 이벤트 끝 ----------------------------------//
//----------------------------추가삭제위 아래 이미지 버튼 이벤트 끝 ------------------------------//
//하위부서포함 버튼 클릭시
function onIncludeChildren(){	
	includeChildrenDept();
}

//추가(+) 버튼 클릭시
function onAddLine(){ // :::
	if(curTabMark === 'I3'){ // 수신자기호
		AddSymbolListByJSTree();
	}
	else if(curTabMark === 'I4'){ // 행정기관
		AddLDAPList();
	}
	else		
		AddList();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	var removeRecvCnt = 0;
	
	for(var i = 0; i < lines.length; i++){
		if(editYn === "N"){
			if('N' === lines[i].attr('newFlg')){
				alert('<spring:message code="approval.msg.applines.after.nodelrecv" />');
				return;
			}
		}
				
		lines[i].remove();
		removeRecvCnt++;
	}

	gSaveLineObject.restore();

	// 수신자 삭제할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) - removeRecvCnt;
	if(recvCnt < 0){
		recvCnt = 0;
	}  
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);
}

//위로올리기 버튼
function onMoveUp(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.prev();

		if(editYn === "N"){
			if(obj.attr('newFlg') === "N" || prev.attr('newFlg') === "N" ){
				return;
			}
		}
		
		prev.before($('#'+obj.attr('id')));
	}

}

//아래로 내리기 버튼
function onMoveDown(){

	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.next();
		if(editYn === "N"){
			if(obj.attr('newFlg') === "N" || prev.attr('newFlg') === "N" ){
				return;
			}
		}
		
		prev.after($('#'+obj.attr('id')));
	}

}
//----------------------------추가삭제위 아래이미지 버튼 이벤트 끝--------------------------------------//
//----------------------------최 하위 버튼 이벤트 시작 --------------------------------------------//
function sendOk(){
	var apprecv = "";
	var lines = $('#tbRecvLines tbody');
	var recvMark = $('#chkDisplayAs');
	var txtMark = $('#txtDisplayAs'); 
	
	if(lines.length === 0){
		alert('<spring:message code="approval.msg.applines.norecvs" />');
		return;
	}

	var recvlines = lines.children();
	var recvlinecount = recvlines.length; 
	apprecv = makeRecvLine(recvlines);

	//$('#divtest').text(apprecv);
	//return;
	
	if (opener != null && opener.setAppRecv) {
		var ckMark = (recvMark.attr('checked')===true?'Y':'N');
		var strMark = "";
		if(ckMark === "Y"){
			txtMark.val($.trim(txtMark.val()));
			strMark = txtMark.val();
			if (strMark == "") {
				alert('<spring:message code="approval.msg.applines.nodisplayrecvs" />');
				txtMark.focus();
				return false;
			}
		}
		
		if(recvlinecount === 0) enterpriseYn = "N";
		//alert(lines.children().length + ":::"+ enterpriseYn);
		 
		//필요시사용, 수신처가 사람인 경우 경고 메시지 호출 , kj.yang, 20120425 S
<%--
		for (var loop = 0; loop < recvlinecount; loop++) {	//
			if (recvlines[loop].getAttribute('receiverType') == DRU002) {
				if (confirm("<spring:message code='approval.msg.confirm.apprecv.include.dru002' />")) {
					break;
				} else {
					return false;
				}			
			}
		}
--%>
		//필요시사용, 수신처가 사람인 경우 메시지 호출 , kj.yang, 20120425 E
	
		var msg = opener.setAppRecv(apprecv, ckMark, strMark, enterpriseYn);

		if(typeof(msg) !== "undefined"){
			if(msg !== ""){
				alert(msg);
			} else {
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
		line.setAttribute('receiverOrder',i+1);
		strRt += (line.getAttribute('receiverType') 	+ String.fromCharCode(2) + line.getAttribute('enfType') 		+ String.fromCharCode(2) + line.getAttribute('recvCompId') 		+ String.fromCharCode(2) + line.getAttribute('recvDeptId') 		+ String.fromCharCode(2)); 
		//strRt += (line.getAttribute('recvDeptName') 	+ String.fromCharCode(2) + line.getAttribute('refDeptId') 		+ String.fromCharCode(2) + line.getAttribute('refDeptName') 	+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('sName') 			+ String.fromCharCode(2) + line.getAttribute('refDeptId') 		+ String.fromCharCode(2) + line.getAttribute('sRef') 			+ String.fromCharCode(2));
		strRt += (line.getAttribute('recvUserId') 		+ String.fromCharCode(2) + line.getAttribute('recvUserName') 	+ String.fromCharCode(2) + line.getAttribute('postNumber') 		+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('address') 			+ String.fromCharCode(2) + line.getAttribute('telephone') 		+ String.fromCharCode(2) + line.getAttribute('fax') 			+ String.fromCharCode(2)); 
		strRt += (line.getAttribute('recvSymbol') 		+ String.fromCharCode(2) + line.getAttribute('newFlg') 			+ String.fromCharCode(2) + line.getAttribute('refSymbol') 		+ String.fromCharCode(2)); // jth8172 2012 신결재 TF
		strRt += (line.getAttribute('recvChiefName') 	+ String.fromCharCode(2) + line.getAttribute('refChiefName') 	+ String.fromCharCode(2) + line.getAttribute('receiverOrder') 	+ String.fromCharCode(4)); // jth8172 2012 신결재 TF
	}
 
	return strRt;
}

//설정된 수신 목록을 받아 설정한다.
function setAppRecv(){
	var tbRecvLines = $('#tbRecvLines tbody');
	var objRecv = null;

	
	if (opener != null && opener.getAppRecv) {
		var objRecv = opener.getAppRecv();
		
		//owndept = objRecv.owndept; //기안자 소유부서(대리처리과)
		
		//$('#divtest').text(objRecv.appRecv);
		var arrLines = getReceiverList(objRecv.appRecv);

		if(typeof(objRecv.editYn)!== "undefined"){
			editYn = objRecv.editYn;
		}
				
		if(arrLines.length == 0){

			$("#recvCnt").text(0);
			$("#recvCntForm").val(0);
			
			return false;
		}
		
		if(typeof(objRecv.enterpriseYn)!== "undefined"){
			objRecv.enterpriseYn = enterpriseYn;
			enterpriseYn = (enterpriseYn==""?"N":enterpriseYn); 
		}

		for (var i = 0;i  < arrLines.length; i++){
			var recvObj = arrLines[i];
			
			recvObj.sName = recvObj.recvDeptName;
			recvObj.sRef  = recvObj.refDeptName;
			
			if(recvObj.sRef === ""){
				recvObj.sRef = recvObj.recvUserName
			}
			

			if(recvObj.recvSymbol === ""){
				recvObj.sSymbol = "&nbsp;";
			}else{
				recvObj.sSymbol = recvObj.recvSymbol;
			}

			var id = "";
			if(recvObj.enfType == DET005 || recvObj.enfType == DET006 || recvObj.enfType == DET007){
				var idDate = new Date();
				id = recvObj.enfType
					 + new String(idDate.getYear())
				     + new String(idDate.getMonth())
					 + new String(idDate.getDate()) 
					 + new String(idDate.getDay()) 
					 + new String(idDate.getHours()) 
					 + new String(idDate.getMinutes()) 
					 + new String(idDate.getSeconds()) 
					 + new String(idDate.getMinutes());
				
			}else{
				id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);
			}

			
			var row = MakeReciver(id, recvObj);
			tbRecvLines.append(row);

			//초기 수신자수 셋팅
			initRecvCnt++;			
		}

		$("#recvCnt").text(initRecvCnt);
		$("#recvCntForm").val(initRecvCnt);

		var recvMark = $('#chkDisplayAs');
		var txtMark = $('#txtDisplayAs'); 
		recvMark.attr('checked',(objRecv.displayNameYn === 'Y'?true:false));
		
		if(objRecv.displayNameYn === 'Y'){
			txtMark.val(objRecv.receivers);
		}else{
			txtMark.val("");
		}
		checkDisplayAs(recvMark.attr('checked'));
		
	}
	
}
//----------------------------최 하위 버튼 이벤트 끝 --------------------------------------------//
//-------------정규식----------------------------------//
function validValue(obj,method){
	if(typeof(method) === "method"){
		method = 1;
	}
	
	if(0 === method || 1 === method){
		if(obj.value === "" ) return;
		if(!telNumber(obj.value)){
			var msg = '<spring:message code="approval.msg.format.telephone" />';
			if(obj.id === "txtFax1" || obj.id === "txtFax2"){
			
				msg = msg.replace(/%S/g,'<spring:message code="approval.form.fax" />');
			}

			if(obj.id === "txtPhone2"){
				msg = msg.replace(/%S/g,'<spring:message code="approval.form.phone" />');
			}
			
			alert(msg);
			//obj.value = "";
			$('#'+obj.id).focus();
			return;
		}	
	}

	if(0 === method || 2 === method){ //keyup, keydown에서 사용할 것 특수문자 체크
		if(!checkTags(obj.value)){
			if(0 === method){
				obj.value = "";
				return;
			}else{
				var length = obj.value.length;			
				obj.value = obj.value.substr(0,length-1);
			}
			alert("<spring:message code='env.recv.msg.noSpecialChar' />");
			return;
		}
	}
}

//전화번호 팩스번호 체크
function telNumber(source){
	var regxp = /^0\d{1,2}-[1-9]\d{2,4}-\d{4}$/;
	return regxp.test(source);
}

//특수문자 여부를 체크한다.
function checkTags(source){
	var regxp = /[~`!@#$%^&*=+|:;?"<,.>'{}()\[\]\/\\]/;
	return !regxp.test(source);
}

//빈값을 _로 변환한다.
function replaceSpace(source){
	var regxp = / /g;
	source = source.replace(regxp,"_");
	regxp = /[~`!@#$%^&*=+|:;?"<,.>'{}()\[\]\/\\]/g;
	return source.replace(regxp,"");
}

function winZipCode() {
	var top = (screen.availHeight - 450) / 2;
	var left = (screen.availWidth - 500) / 2;
	var url = "<%=webUri%>/app/env/selectZipcodePop.do";
	var option = "width=500,height=450,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
	zipcode = window.open(url, "zipcode", option);
}
//----------------------------최 하위 버튼 이벤트 끝 --------------------------------------------//

function setZipCode(obj) {
	$('#txtZipCode2').val(obj.first().children().first().text());
	//$('#txtAddr21').val(obj.first().children().last().text());
	$('#txtAddr21').val(obj.first().attr('displayAddr'));
	$('#txtAddr22').val(obj.first().attr('bungi'));
}

//------------- 수신자그룹 시작 ------------- 2011.05.30 신경훈 //
var recvList = null;

function codeToName(cd) {
	var DET001 = "<spring:message code='env.code.name.DET001'/>";
	var DET002 = "<spring:message code='env.code.name.DET002'/>";
	var DET003 = "<spring:message code='env.code.name.DET003'/>";
	var DET004 = "<spring:message code='env.code.name.DET004'/>";
	var DET005 = "<spring:message code='env.code.name.DET005'/>";
	var DET006 = "<spring:message code='env.code.name.DET006'/>";
	var DET007 = "<spring:message code='env.code.name.DET007'/>";
	var DET011 = "<spring:message code='env.code.name.DET011'/>";
	return eval(cd);
}

function onRecvGroupClick(obj){
	selectOneElementGp(obj);//2011.09.03 ctrl, atl, shift 키관련
	getRecvLine(obj);
}

function selectOneElementGp(obj){
	$('document').empty();
	gSaveObject.restore();
	gSaveObject.add(obj, sColor);
}

function onRecvGroupDblClick() {
	recvGroupAdd();
}

function recvGroupAdd() {
	var tbRecvLines = $('#tbRecvLines tbody');
	var changeCnt = 0;
	var insideCnt = 0;
	var outsideCnt = 0;
	var selRecvCnt = 0;
	recvLineLength = recvList.length;
	for (var i=0; i<recvLineLength; i++) {
		
		recvObj = recvList[i];
		if (recvObj.enfType == DET005 || recvObj.enfType == DET006 || recvObj.enfType == DET007) { // 외부
			recvObj.recvDeptId = "";
		}

		var id = "";
		if(recvObj.enfType == DET005 || recvObj.enfType == DET006 || recvObj.enfType == DET007){
			var idDate = new Date();
			id = recvObj.enfType
				 + new String(idDate.getYear())
			     + new String(idDate.getMonth())
				 + new String(idDate.getDate()) 
				 + new String(idDate.getDay()) 
				 + new String(idDate.getHours()) 
				 + new String(idDate.getMinutes()) 
				 + new String(idDate.getSeconds()) 
				 + new String(idDate.getMinutes());
			
		}else{
			id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
		}
		
		recvObj.newFlg = "Y";
		recvObj.sName = recvObj.recvDeptName;
		recvObj.sRef  = (recvObj.refDeptName == "" ? recvObj.recvUserName : recvObj.refDeptName);
		recvObj.sSymbol = "&nbsp;";
		
		if(recvObj.recvSymbol === ""){
			recvObj.sSymbol = "&nbsp;";
		}else{
			recvObj.sSymbol = recvObj.recvSymbol;
		}

		var tmpTr = "";
		if(recvObj.enfType == DET005 || recvObj.enfType == DET006 || recvObj.enfType == DET007){
			var tmpTr = $('#tbRecvLines tbody tr[recvDeptName="'+recvObj.recvDeptName+'"][refDeptName="'+recvObj.refDeptName+'"]');
		}else{
			tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
		}
		
		var row = MakeReciver(id, recvObj);
		
		if(tmpTr.length === 0 && recvObj.changeYn == "N"){	
			if(owndept == ""){
				if (recvObj.enfType == DET003) {
					insideCnt++;
				} else if (recvObj.enfType == DET005 || recvObj.enfType == DET006 || recvObj.enfType == DET007) {
					outsideCnt++;
				} else {
					tbRecvLines.append(row);

					selRecvCnt++;
				}
			} else {
				if (owndept != recvObj.recvDeptId) {
					tbRecvLines.append(row);
					
					selRecvCnt++;
				}
			}
		}
		
		if(recvObj.changeYn == "Y"){
			changeCnt++;
		}
	}
	if (changeCnt>0) {
		alert(changeCnt+"<spring:message code='env.group.msg.notice.recvline2' />");
	}
	if (insideCnt>0) {
		alert("<spring:message code='env.group.msg.notice.recvline3' />");
	}
	if (outsideCnt>0) {
		alert("<spring:message code='env.group.msg.notice.recvline4' />");
	}
	document.getElementById("divRecvLines").scrollTop = document.getElementById("divRecvLines").scrollHeight;

	// 수신자 추가할때 총 합을 셋팅
	var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
	$("#recvCnt").text(recvCnt);
	$("#recvCntForm").val(recvCnt);
}

function getRecvLine(obj){
	var recvGroupId = obj.attr('id');
	var procUrl = "<%=webUri%>/app/env/listEnvRecvLine.do?recvGroupId="+recvGroupId;
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, function(data){
		results = data;
	});
	recvList = results;
	drawRecvLine(results);			
}

function getRecvGroupAdd(recvGroupId){
	var procUrl = "<%=webUri%>/app/env/listEnvRecvLine.do?recvGroupId="+recvGroupId;
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, function(data){
		results = data;
	});
	recvList = results;
	recvGroupAdd();	

}

function drawRecvLine(recvLineList) {
	var tbl = $('#tblRecvLine');
	var txtRecvLines = "";
	var recvlineLength = recvLineList.length;
	//var notice = "N";
	tbl.empty();

	for (var i=0; i<recvlineLength; i++) {
		var recvLine = recvLineList[i];
		var enfName = codeToName(recvLine.enfType);
		var row = "";
		var bgcolor = "ffffff";
		if (recvLine.changeYn == "Y") {
			bgcolor = "ffefef";
			notice = "Y";
		}
		row = "<tr style='background-color:#"+bgcolor+";'>";
		row += "<td width='40%' class='ltb_center'>"+recvLine.recvDeptName+"</td>";
		if (recvLine.refDeptName != null && recvLine.refDeptName != "") {
			row += "<td width='35%' class='ltb_center'>"+recvLine.refDeptName+"</td>";
		} else {
			row += "<td width='35%' class='ltb_center'>"+recvLine.recvUserName+"</td>";
		}
		row += "<td width='25%' class='ltb_center'>"+enfName+"</td>";
		row += "</tr>";
		tbl.append(row);
	}

	/*
	if (notice == "Y") {
		alert("<spring:message code='env.group.msg.notice.recvline' />");
	}
	*/
}

//회사, 본사, 지점 버튼 클릭시
function goRgt(nType){
	var url = "<%=webUri%>/app/env/listEnvRecvLine.do?recvGroupId=";
	
	if(nType === 1){
		url  += RGD001;
		enterpriseYn = "Y";
	}else if(nType === 2){
		url  += RGD002;
	}else if(nType == 3){
		url  += RGD003;
	}else if(nType === 4){
		url  += RGC001;
	}else{
		url  += RGC002;
	}

	$.ajaxSetup({async:false});
	$.getJSON(url, function(data){
		delLineAllNoMsg("N");
		makeRgt(data, nType);
	});
}
//회사, 본사, 지점 처리 
function makeRgt(results, nType){
	var tbRecvLines = $('#tbRecvLines tbody');
	var recvMark = $('#chkDisplayAs');
	var txtMark = $('#txtDisplayAs'); 
	var selRecvCnt = 0;
	
	if(typeof(results) != "undefined"){
		var nSize = results.length;
		var overlap = 0;
		var overlapMsg = "<spring:message code='approval.msg.recipient.overlap' />";

		for(var i = 0; i < nSize; i++){
			var recvObj = new Object();
			var result = results[i];
			recvObj.receiverOrder 	= result.receiverOrder;
			recvObj.receiverType 	= result.receiverType;
			recvObj.enfType 		= result.enfType;
			recvObj.recvDeptId 		= result.recvDeptId;
			recvObj.recvDeptName 	= result.recvDeptName;
			recvObj.refDeptId		= result.refDeptId;
			recvObj.refDeptName 	= result.refDeptName;
			recvObj.recvUserId 		= result.recvUserId;
			recvObj.recvUserName 	= result.recvUserName;
			recvObj.postNumber 		= result.postNumber;
			recvObj.address 		= result.address;
			recvObj.telephone 		= result.telephone;
			recvObj.fax 			= result.fax;
			recvObj.recvSymbol 		= result.recvSymbol;
			recvObj.recvCompId		= result.recvCompId;
			recvObj.refSymbol 		= result.refSymbol; // jth8172 2012 신결재 TF
			recvObj.recvChiefName 	= result.recvChiefName; // jth8172 2012 신결재 TF
			recvObj.refChiefName 	= result.refChiefName; // jth8172 2012 신결재 TF
			
			recvObj.sName = recvObj.recvDeptName;
			recvObj.sRef  = recvObj.refDeptName;
			
			if(recvObj.sRef === ""){
				recvObj.sRef = recvObj.recvUserName
			}
			
			//recvObj.sRef   = (recvObj.sRef === ""? "&nbsp;": recvObj.sRef);

			if(recvObj.recvSymbol === ""){
				recvObj.sSymbol = "&nbsp;";
			}else{
				recvObj.sSymbol = recvObj.recvSymbol;
			}
						
			//var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.recvDeptName + recvObj.refDeptName);
			var id = replaceSpace(recvObj.recvDeptId + recvObj.refDeptId + recvObj.recvUserId + recvObj.sName + recvObj.sRef);

			var row = MakeReciver(id, recvObj);
			
			var tmpTr = $('#tbRecvLines tbody tr[id="tbRecvLines_'+id+'"]');
			
			if(tmpTr.length === 0){	
				tbRecvLines.append(row);
				selRecvCnt++;
			}else{	
				overlap++;
			}
		}

		if(nSize > 0){
			if(nType <= 3 ){
				var strMark = "";
				if(nType === 1) strMark = rgd001Btn; 
				if(nType === 2) strMark = rgd002Btn; 
				if(nType === 3) strMark = rgd003Btn; 

				txtMark.val(strMark);
				recvMark.attr('checked',true);
				checkDisplayAs(recvMark.attr('checked'));
			}
		}
		
		if(nSize == 0){
			enterpriseYn = "N";
		}
		
		if(overlap > 0){
			var regxp = /%S/g;
			overlapMsg = overlapMsg.replace(regxp,overlap);
			alert(overlapMsg);
		}else{
			if(nSize < 1){
				var MsgTitle = "";
				if(nType === 1){
					MsgTitle = "<%=rgd001Btn%> ";
				}else if(nType === 2){
					MsgTitle = "<%=rgd002Btn%> ";
				}else{
					MsgTitle = "<%=rgd003Btn%> ";
				}
				alert(MsgTitle + "<spring:message code='common.msg.nodata' />");
			}
		}

		// 수신자 추가할때 총 합을 셋팅
		var recvCnt = parseInt($("#recvCntForm").val()) + selRecvCnt;
		$("#recvCnt").text(recvCnt);
		$("#recvCntForm").val(recvCnt);
	}
}

function delLineAll(){
	delLineAllNoMsg("Y");
	$('#chkDisplayAs').attr('checked', false);
	checkDisplayAs($('#chkDisplayAs').attr('checked'));
}

function delLineAllNoMsg(MsgYn){
	var tbRecvLines = $('#tbRecvLines tbody');
	if(tbRecvLines.children().length > 0){
		if(MsgYn === "Y"){
			if(confirm("<spring:message code='approval.msg.delete.allitem' />")){			
				tbRecvLines.empty();
				$('#txtDisplayAs').val("");
			}
		}else{
			tbRecvLines.empty();
			$('#txtDisplayAs').val("");
		}
	}

	// 수신자 추가할때 총 합을 셋팅
	$("#recvCnt").text(0);
	$("#recvCntForm").val(0);
}

// ------------- 수신자그룹 끝 ------------- 2011.05.30 신경훈 //
var searchDepts = "";
var searchRecvGroups = "";

function goSearchDept(){
	var objDept = $('#searchDept');

	if(objDept.val() === "")
	{
		if ( curTabMark == 'I3' )
		{
			alertMessage = "<spring:message code='common.msg.symbolnm.no' />";
		}
		else if ( curTabMark == 'I4' )
		{
			alertMessage = "<spring:message code='common.msg.executive.agency.nm.no' />";
		}
		else
		{
			alertMessage = "<spring:message code='common.msg.deptnm.no' />";
		}
		alert(alertMessage); 
		return;
	}

	var _method = "1";

	if ( curTabMark == 'I3' )
	{
		_method = "searchSymbol";
	}
	if ( curTabMark == 'I4' )
	{
		_method = "searchAgency";
	}

	var url = "<%=webUri%>/app/common/searchDept.do?method=" + _method + "&partYn=N";
	
	if(curTabMark === 'I0' || curTabMark === 'I3'){
		//자기회사
		url += "&searchType=1";
		
	}else{
		//전체회사
		url += "&searchType=2";
	}

	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,objDept.serialize() ,function(data){
		results = data;
	});

	var url2 = "<%=webUri%>/app/env/searchEnvAppLineGroup.do";
	var results2 = "";

	$.ajaxSetup({async:false});
	$.getJSON(url2,objDept.serialize() ,function(data2){
		results2 = data2;
	});
			
	var totResults = 0;

	var resultsCnt = 0;
	var results2Cnt = 0;

	if ( results != null )
	{
		resultsCnt = results.length;
	}
	
	if ( results != null )
	{
		results2Cnt = results2.length;
	}

	totResults = resultsCnt + results2Cnt;

	if( (results != "" && results != null) || (results2 != null && results2 != "") )
	{
		if(totResults === 1)
		{
			if(curTabMark == 'I0')
			{
				if(results.length > 0){
					selectOwnDept(results[0]);
					AddList();						
				} else if(results2.length > 0) {
					getRecvGroupAdd(results2[0].recvGroupId);
				}
			}
			else if(curTabMark == 'I3') // 수신처기호
			{
				if(results.length > 0){
					AddSymbolListByAjax(results[0]);
					return;
				} else if(results2.length > 0) {
					getRecvGroupAdd(results2[0].recvGroupId);
				}
			}
			else if(curTabMark == 'I4') // 행정기관
			{
				if(results.length > 0){
					AddLDAPList(results[0]);
					return;
				} else if(results2.length > 0) {
					getRecvGroupAdd(results2[0].recvGroupId);
				}
			}
			else
			{
				if(results.length > 0){
					selectOtherDept(results[0]);

					AddList();
				}else if(results2.length > 0){
					getRecvGroupAdd(results2[0].recvGroupId);
				}
			}

			return;
		}else{
			searchDepts = results;
			searchRecvGroups = results2;

			if(curTabMark == 'I3')
				goSearchListForI3();
			else
				goSearchList();
			
			return;
		}
	}else{
		alert("<spring:message code='app.alert.msg.56'/>");
		objDept.val("");
		return;
	}

}

function setDirectOwnDept(){	
	setTimeout(AddList,100);
}

function getSearchDept(){
	return searchDepts;
}

function getSearchRecvGroup(){
	return searchRecvGroups;
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
			openNode(node[0], $.tree.focused(), 1);
			
			if(i === (rsCnt -1)){
				ownDeptId = '#' + results[i].orgID;
				$.tree.focused().scroll_into_view(ownDeptId);
				$.tree.reference("#org_tree").select_branch(ownDeptId);			
			}
		}
	}

	setTimeout(function(){
		if(ownDeptId != null){
			jQuery.tree.reference("#org_tree").scroll_into_view(ownDeptId);
		}
		
	},10);
}

function selectSymbolDept(ownDeptId)
{
	$.tree.reference("#org_tree3").select_branch(ownDeptId);	
	AddSymbolListByJSTree();
}

var otherDept = "";
function selectOtherDept(objDept){
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
			var node = $('#org_'+results[i].orgID);
			openNode(node[0], jQuery.tree.reference("#org_tree2"), 2);
			
			if(i === (rsCnt -1)){
				otherDept = '#org_' + results[i].orgID;
				jQuery.tree.reference("#org_tree2").select_branch(otherDept);
				jQuery.tree.reference("#org_tree2").scroll_into_view(otherDept);
			}
		}
	}

	setTimeout(function(){
		if(ownDeptId != null){
			jQuery.tree.reference("#org_tree2").scroll_into_view(otherDept);
		}
		
	},10);
}

function goSearchList(){
	var _method = "3";
	var _winName = "searchDeptName";
	if ( curTabMark == 'I3' )
	{
		_method = "searchSymbolPop";
		_winName = "searchSymbolName";
	}
	if ( curTabMark == 'I4' )
	{
		_method = "searchAgencyPop";
		_winName = "searchAgencyName";
	}
	
	var width = 400;
	var height = 330;
	var url = "<%=webUri%>/app/common/searchDept.do?method=" + _method;
	if(curTabMark === 'I0'){
		//자기회사
		url += "&searchType=1";
		
	}else{
		//전체회사
		url += "&searchType=2";
	}

	url += "&listType=All";
		
	var appDoc = null;
	appDoc = openWindow(_winName, url, width, height); 
}

function goSearchListForI3(){
	var _method = "3";
	var _winName = "searchDeptName";
	
	var width = 400;
	var height = 330;
	var url = "<%=webUri%>/app/common/searchDept.do?method=" + _method;
	if(curTabMark === 'I0'){
		//자기회사
		url += "&searchType=1";
		
	}else{
		//전체회사
		url += "&searchType=2";
	}

	url += "&listType=All";
		
	var appDoc = null;
	appDoc = openWindow("searchDeptName", url, width, height); 
}



function searchKeyup(){

	if(event.keyCode === 13){
		goSearchDept();
	}
	return;		
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
			<span class="pop_title77">
			<spring:message code="approval.title.apprecip" /></span>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- --------탭시작------ -->
			<table cellspacing='0' cellpadding='0' border='0' width='100%' height='38'>
				<tr>
					<td valign='bottom'>
		               <acube:tabGroup>
		                  <acube:tab index="1" onClick="JavaScript:selectTab(1);onClickTab('I0');" selected="true">
		                  		<spring:message code="approval.tab.title.recv.00" />
		                  </acube:tab>
						<% if (tabI1.equals("Y")) { %>
						<%
								String selectTab = "JavaScript:selectTab(" + (++tabIndex) + ");onClickTab('I1');";
								String tagIndex = "" + tabIndex;
						%>
						  <acube:space between="tab"/>
						  <acube:tab index="<%=tagIndex%>" onClick="<%=selectTab%>">
						  	<spring:message code="approval.tab.title.recv.01" />
						  </acube:tab>
						<% } %>
						<% if (tabI2.equals("Y")) { %>
						<%
								String selectTab = "JavaScript:selectTab(" + (++tabIndex) + ");onClickTab('I2');";
								String tagIndex = "" + tabIndex;								
						%>					
						  <acube:space between="tab"/>						 
		                  <acube:tab index="<%=tagIndex%>" onClick="<%=selectTab%>">
		                  	<spring:message code="approval.tab.title.recv.02" />
		                  </acube:tab>
		                <% } %>
						<% if (tabI3.equals("Y")) { %>
						<%
								String selectTab = "JavaScript:selectTab(" + (++tabIndex) + ");onClickTab('I3');";
								String tagIndex = "" + tabIndex;								
						%>
		                  <acube:space between="tab"/>
		                  <acube:tab index="<%=tagIndex%>" onClick="<%=selectTab%>">
		                  	<spring:message code="env.code.name.DET010" />
		                  </acube:tab> 
		                <% } %>
						<% if (tabI4.equals("Y")) { %>
						<%
								String selectTab = "JavaScript:selectTab(" + (++tabIndex) + ");onClickTab('I4');";
								String tagIndex = "" + tabIndex;								
						%>
		                  <acube:space between="tab"/>
		                  <acube:tab index="<%=tagIndex%>" onClick="<%=selectTab%>">
		                  	<spring:message code="env.code.name.DET011" />
		                  </acube:tab>
		                <% } %>
						<%
							String selectTab = "JavaScript:selectTab(" + (++tabIndex) + ");onClickTab('I5');";
							String tagIndex = "" + tabIndex;								
						%>
		                  <acube:tab index="<%=tagIndex%>" onClick="<%=selectTab%>">
		                  	<spring:message code="env.tapmenu.recvgroup" />
		                  </acube:tab> 
		               </acube:tabGroup>
					</td>
					<td valign='bottom'>
						<table border='0' width='100%' cellpadding='0' cellspacing='0'>
							<tr>
								<td align='right'>
									<select id='idProposalSel' style='display:none;' onchange='javascript:onChangeProposal(this.value);return(false);'></select></td>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height='2' colspan='2' class='tab_line'></td></tr>
			</table>
			<!-- --------탭끝------ -->
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<div id="div_dept_search">
			<!-- 검색  -->	
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td class="tb_tit" width="40" id="keyword_label"><spring:message code="bind.obj.dept.name" /></td>				
				<td class="tb_tit" width="25%"><input id="searchDept" name="searchDept" type="text" style="width: 100%;ime-mode:active;FONT-SIZE:9pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#777777; background-color:#FFFFFF; height:16px; padding-left:3px; padding-right:3px; border:1px #C3C2C2 solid;" onkeypress="searchKeyup();"/></td>
				<td class="tb_tit" width="20"><a href="javascript:goSearchDept();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
				<td class="tb_tit" id="search_label"><spring:message code="approval.msg.recvPopInfo.head" /></td>
			</tr>
			</acube:tableFrame>			
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			</div>
			<!-- 대내 -->
			<div id="divInternal" group="recv" style="height:200px;width:100%;margin:0px;padding:0px; ">
				<!-- 부서 트리 시작 -->
				<div  id="divDept"  style="float:left;width:<c:choose><c:when test='${opt333 == "2"}'>49%;</c:when><c:otherwise>100%</c:otherwise></c:choose> ">
				 <!-- 트리용 테이블  -->
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td height="150" class="basic_box">
					<div id="org_tree" style="height:178px; overflow:auto; background-color : #FFFFFF;">
				    <ul>
				    	<%
				    	int nSize = results.size();
				    	String rootOrgId = "";
				    	String rootOrgNm = "";
				    	String rootOutgoingName = "";
				    	for(int i = 0; i < nSize; i++){
				    		DepartmentVO result = results.get(i);
				    		String _class = "class='closed'";
				    		String rel = "";
				    		StringBuffer Li = new StringBuffer();
				    		
				    		boolean hasChild = false;
				    		if(result.getOrgType() == 0){
				    			rel = " rel='root' ";
				    		
				    		}else if(result.getOrgType() == 1){
				    		    if(rootOrgId != result.getOrgID()){
						    		rootOrgId = result.getOrgID();
						    		rootOrgNm = result.getOrgName();
						    		rootOutgoingName = result.getOutgoingName();
				    		    }
				    			rel = " rel='comp' ";
				    		}else if(result.getOrgType() == 2){
				    			//rel = " rel='dept' ";
							   	if(result.getIsProcess()){
							   	 	rel = " rel='proc_dept' ";
							   	}else{
							   		rel = " rel='dept' ";
							   	}
				    		}else{
				    			rel = " rel='part' ";
				    		}
				    		
				    		if(i < (nSize-1)){
				    		    //자식존재여부
					    		hasChild = result.getOrgID().equals(results.get(i+1).getOrgParentID());	    		
							}
				    		
				    		if(result.getOrgType()== 0 ) {
								_class = "class='open'";
							}else{
							    _class = "class='closed'";
							}
				    		
				    		Li.append("<li id='"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID());
				    		Li.append("' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() );
				    		Li.append("' rootOrgId='"+rootOrgId+"' rootOrgName='"+rootOrgNm+"' outgoingName='"+result.getOutgoingName()+"' rootOutgoingName='"+rootOutgoingName+"' addrSymbol='"+result.getAddrSymbol() +"' chiefName='"+result.getChiefPosition()  ); // jth8172 2012 신결재 TF
				    		Li.append("' orgCode='' isDeleted='' isInspection='' isInstitution='' isODCD='' isProxyDocHandleDept='' orgAbbrName='' isProcess='' ");
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
<c:if test='${opt333 == "2"}'>
				<!-- 부서별 부서원 목록 시작 -->
				<div id="divUsers" style="float:right; width:49%; ">
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td height="180" class="basic_box" style="padding:0px; margin:0px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
						<thead>
							<TR><TD width="150" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
								<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
							</TR>
						</thead>
					</table>
					<div style="height:175px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
					<table id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody />
					</table>
					</div>
				</td>
				</tr>
				</acube:tableFrame>
				</div><!-- 부서별 부서원 목록 시작 -->
</c:if>
			</div>
			<!-- ------------------------대내 끝------------------------ -->
			<!-- 대외 -->
			<div id="divOuter" group="recv" style="height:200px;display:none;width:98%">
				<acube:tableFrame width="100%"  border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td height="150" class="basic_box">
					<div id="org_tree2" style="height:180px; overflow:auto; background-color : #FFFFFF;">
				    <ul>
				    	<%
				    	int nSize = results2.size();
				    	String rootOrgId = "";
				    	String rootOrgNm = "";
				    	String rootOutgoingName = "";
				    	for(int i = 0; i < nSize; i++){
				    		DepartmentVO result = results2.get(i);
				    		String _class = "class='closed'";
				    		String rel = "";
				    		StringBuffer Li = new StringBuffer();
				    		boolean hasChild = false;
				    		
				    		if(result.getOrgType() == 0){
				    			rel = " rel='root' ";
				    		
				    		}else if(result.getOrgType() == 1){
				    		    if(rootOrgId != result.getOrgID()){
						    		rootOrgId = result.getOrgID();
						    		rootOrgNm = result.getOrgName();
						    		rootOutgoingName = result.getOutgoingName();
				    		    }
				    			rel = " rel='comp' ";
				    		}else if(result.getOrgType() == 2){
				    			//rel = " rel='dept' ";
							   	if(result.getIsProcess()){
							   	 	rel = " rel='proc_dept' ";
							   	}else{
							   		rel = " rel='dept' ";
							   	}
				    		}else{
				    			rel = " rel='part' ";
				    		}
				    		
				    		if(i < (nSize-1)){
				    		    //자식존재여부
					    		hasChild = result.getOrgID().equals(results2.get(i+1).getOrgParentID());	    		
							}
				    		
				    		if(result.getOrgType()== 0 ) {
								_class = "class='open'";
							}else{
							    _class = "class='closed'";
							}
				    		
				    		Li.append("<li id='org_"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID());
				    		Li.append("' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() );
				    		Li.append("' rootOrgId='"+rootOrgId+"' rootOrgName='"+rootOrgNm+"' outgoingName='"+result.getOutgoingName()+"' rootOutgoingName='"+rootOutgoingName+"' addrSymbol='"+result.getAddrSymbol() +"' chiefName='"+result.getChiefPosition()  );// jth8172 2012 신결재 TF
				    		Li.append("' orgCode='' isDeleted='' isInspection='' isODCD='' isProxyDocHandleDept='' orgAbbrName='' isProcess='' ");
				    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
				    		
				    		//out.println("<li id='org_"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() +"' addrSymbol='"+result.getAddrSymbol()+"' "+_class+"><a href='#'><ins></ins>"+result.getOrgName()+"</a>");
				    		out.println(Li.toString());
				    		
				    		if(hasChild){//자식이있으면
				    		    out.println("<ul>");
				    		}
				    		
				    		
				    		if(i <= (nSize -2)){
				    		   
				    		   if(!hasChild && !result.getOrgParentID().equals(results2.get(i+1).getOrgParentID())){
				    			  // out.println("</li>\n</ul>\n</li>");
				    			   int deptSize = results2.get(i+1).getDepth() - result.getDepth();
				    			   out.println("</li>");
				    			   for(int nLi = 0; nLi < deptSize; nLi++){
				    				   out.println("\n</ul>\n</li>");
				    			   }
				    		   }
				    		   
				    		   if(!hasChild && result.getOrgParentID().equals(results2.get(i+1).getOrgParentID())){
				    		   		out.println("</li>");
				    		   }
				    		}
				    		
				    		if(i == (nSize-1)){
					    		if(!results2.get(0).getOrgParentID().equals(results2.get(i).getOrgParentID())){
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
				</acube:tableFrame>
			</div>
			<!-- 외부 -->
			<div id="divOutPlace" group="recv" style="height:231px;display:none;">
				<acube:tableFrame class="ltb_table">
				<tr>
					<td align="right" style="font-size: 9pt">
						<input id='recvType0' name="recvType" type="radio"  value='0' onclick='onRecvType(this);'/><spring:message code="approval.form.admin.agency" />&nbsp;
						<input id='recvType1' name="recvType" type="radio"  value='1' onclick='onRecvType(this);'/><spring:message code="approval.form.receive.organization" />&nbsp;
						<input id='recvType2' name="recvType" type="radio"  value='2' onclick='onRecvType(this);'/><spring:message code="approval.form.receive.individual" />&nbsp;
					</td>
				</tr>
				</acube:tableFrame>
				<!-- 여백 시작 -->
				<acube:space between="button_content" table="y"/>
				<!-- 여백 끝 -->
				<div id="divRecvType0" style="height:170px;display:none;font-size: 9pt;">
					<acube:tableFrame>
					<tr>
						<td class="tb_tit b_td" width="18%"><spring:message code="approval.form.recv.agency" /><spring:message code='common.title.essentiality'/></td>
						<td class="tb_left_bg"><input type="text" name="txtRecvAgen0" id="txtRecvAgen0" size="30" class="input" style="width:99%;" onkeyup="checkInputMaxLength(this,'',128);"></td>
					</tr>
					<tr>
						<td class="tb_tit b_td"><spring:message code="approval.form.ref.agency" /></td>
						<td class="tb_left_bg"><input type="text" name="txtRefAgen0" id="txtRefAgen0" size="30" class="input" style="width:99%;" onkeyup="checkInputMaxLength(this,'',128);"></td>
					</tr>
					<tr>
						<td class="tb_tit b_td"><spring:message code="approval.table.title.recvsymbol" /></td>
						<td class="tb_left_bg"><input type="text" name="txtRecvSymbol0" id="txtRecvSymbol0" size="30" class="input" style="width:99%;"  onkeyup="checkInputMaxLength(this,'',64)"></td>
					</tr>
					</acube:tableFrame>
				</div>
				<div id="divRecvType1" style="height:170px;  ">
					<acube:tableFrame>
					<tr>
						<td class="tb_tit b_td" width="18%"><spring:message code="approval.form.recv.agency" /><spring:message code='common.title.essentiality'/></td>
						<td class="tb_left_bg"><input type="text" name="txtRecvAgen1" id="txtRecvAgen1" size="30" class="input" style="width:99%;" onkeyup="checkInputMaxLength(this,'',128);"></td>
					</tr>
					<tr>
						<td class="tb_tit b_td"><spring:message code="approval.form.ref.agency" /></td>
						<td class="tb_left_bg"><input type="text" name="txtRefAgen1" id="txtRefAgen1" size="30" class="input" style="width:99%;"  onkeyup="checkInputMaxLength(this,'',128);"></td>
					</tr>
					<tr>
						<td class="tb_tit b_td"><spring:message code="approval.form.fax" /></td>
						<td class="tb_left_bg"><input type="text" name="txtFax1" id="txtFax1" size="30" class="input" style="width:99%;"  onkeyup="checkInputMaxLength(this,'',20)" onblur="validValue(this, 1)" value=""></td>
					</tr>
					</acube:tableFrame>
					<br/>
					<spring:message code="approval.msg.fax.sample" />
				</div>
				<div id="divRecvType2" style="height:170px; display:none;font-size: 9pt; " >
					<acube:tableFrame>
					<thead>
						<th width="18%"></th><th width="32%"></th><th width="18%"></th><th></th>
					</thead>
					<tr>
						<td class="tb_tit b_td" width="20%"><spring:message code="approval.title.apprecip" /><spring:message code='common.title.essentiality'/></td>
						<td class="tb_left_bg" colspan="3"><input type="text" name="txtRecv2" id="txtRecv2" size="30" class="input" style="width:99%;"  onkeyup="checkInputMaxLength(this,'',128);"></td>
					</tr>
					<tr>
						<td class="tb_tit b_td"><spring:message code="approval.form.phone" /></td>
						<td class="tb_left_bg"><input type="text" name="txtPhone2" id="txtPhone2" size="30" class="input" style="width:98%;" onkeyup="checkInputMaxLength(this,'',20)" onblur="validValue(this, 1)"></td>
						<td class="tb_tit b_td" width="18%"><spring:message code="approval.form.fax" /></td>
						<td class="tb_left_bg"><input type="text" name="txtFax2" id="txtFax2" size="30" class="input" style="width:98%;" onkeyup="checkInputMaxLength(this,'',20)" onblur="validValue(this, 1)"></td>
					</tr>
					<tr>
						<td  class="tb_tit b_td"><spring:message code="approval.form.zipcode" /></td>
						<td class="tb_left_bg" colspan="3">
							<table width="200" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="100">
										<input type="text" name="txtZipCode2" id="txtZipCode2" class="input_read" style="width:85%;" readOnly>
									</td>
									<td width="120">
										<acube:buttonGroup align="left">
											<acube:button id="sendOk" disabledid="" onclick="winZipCode();" value="<%=buttonZipcode%>" type="4" class="gr" />
										</acube:buttonGroup>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="tb_tit b_td" rowspan="2"><spring:message code="approval.form.addr" /></td>
						<td class="tb_left_bg" colspan="3"><input type="text" name="txtAddr21" id="txtAddr21" size="30" class="input_read" style="width:99%;" readOnly></td>
					</tr>
					<tr>
						<td class="tb_left_bg" colspan="3"><input type="text" name="txtAddr22" id="txtAddr22" size="30" class="input" style="width:99%;" onkeyup="checkInputMaxLength(this,'',156)"></td>
					</tr>
					</acube:tableFrame>
					<br/>
					<spring:message code="approval.msg.fax.sample" />
				</div>
			</div >			
			
	<% if ( tabI3.equals("Y") ) { %>		
	<!-- 수신자기호 시작 -->
	<div id="divRecvSymbol" group="recv" style="height:210px;display:none;">
		<!-- 부서 트리 시작 -->
		<!-- <div id="divDept"  style="float:left; width:48%; "> -->
		<div  id="divDept2"  style="float:left;width:<c:choose><c:when test='${opt333 == "2"}'>48%;</c:when><c:otherwise>100%</c:otherwise></c:choose> ">
		 <!-- 트리용 테이블  -->
		<acube:tableFrame class="basic_box">
		<tr>
			<td height="185">
			<div id="org_tree3" style="height:186px; overflow:auto; background-color:#FFFFFF;">
		    <ul>
		    	<%
		    	int nSize = resultsSymbol.size();
		    	String itemId = "";
	    		String itemName = "";
	    		String companyID = "";
	    		
	    		out.println("<ul>");
		    	for(int i = 0; i < nSize; i++){
		    		OrganizationVO result = resultsSymbol.get(i);
		    		String _class = "class='closed'";
		    		String rel = " rel='comp' ";
		    		StringBuffer Li = new StringBuffer();
		    		
		    		itemId = "ROOT" + Integer.toString(i);
		    		itemName = result.getAddrSymbol();
		    		companyID = result.getCompanyID();
		    		
		    		Li.append("<li id='"+itemId+"' orgId='"+itemId+"' indexName='" +itemName + "' companyID='"+companyID+"' ");
		    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+ itemName + "</a></li>");
		    		out.println(Li.toString());
		    	}
		    	out.println("</ul>");
		    		
		    	%>
		    </ul>
			</div>				
			</td>
		</tr>
		</acube:tableFrame><!-- 트리용 테이블 -->
		</div><!-- 부서 트리 끝-->
<c:if test='${opt333 == "2"}'>
		<!-- 부서별 부서원 목록 시작 -->
		<div id="divUsers2" style="float:right; width:50%; ">
		<acube:tableFrame class="basic_box">
		<tr>
			<td height="185" style="padding:0 0 0 0;">
			<acube:tableFrame class="table">
				<thead>
					<tr>
						<td width="101" class="ltb_head""><spring:message code="approval.table.title.gikwei" /></td>
						<td class="ltb_head"><spring:message code="approval.table.title.name" /></td>
					</tr>
				</thead>
			</acube:tableFrame>
			<div style="height:182px; overflow-x:hidden; overflow-y:auto; background-color:#FFFFFF;">
			<table bgcolor="#e3e3e3" id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<tbody />
			</table>
			</div>
		</td>
		</tr>
		</acube:tableFrame>
		</div><!-- 부서별 부서원 목록 시작 -->
</c:if>
	</div>
	<!-- ------------------------수신자기호 끝------------------------ -->
	<% } %>
	<% if ( tabI4.equals("Y") ) { %>
	<!-- LDAP 시작 -->
	<div id="divLDAP" group="recv" style="height:210px;display:none;">
		<acube:tableFrame class="">
		<tr>
			<td height="190" class="basic_box">
			<div id="org_ldap" style="height:190px; overflow:auto; background-color : #FFFFFF;">
			<% 
				LDAPOrganization 	ldapOrg = null;
				
				StringBuffer Li = new StringBuffer();
				String _class = "class='open'";
				String rel = "";
	    		
	    		rel = " rel='root' ";
				
				String strDN = "";
				String strID = "";
				String strDisplayName = "";
				String strChiefName = "";
				String strSymbol = "";
				String strDepth = "";
				String strParentID = "";
				String strIsInstitution = "";
				String strIsProcess = "";
				String strSelectYN = "";
				
				Li.append("<ul>");
				Li.append("<li id='0000000' orgId='0000000' parentId='0");
	    		Li.append("' orgName='Government of Korea' DN='o=Government of Korea,c=kr' isinstitution='N' isProcess='N' chiefName='President' depth='0' addrSymbol='' ");
	    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>Government of Korea</a>");
	    		out.println(Li.toString());
	    		
	    		out.println("<ul>");
				if(LDAPOrgs != null){
				int nSize = LDAPOrgs.size();
					for(int i = 0; i < nSize; i++)
					{
						ldapOrg = LDAPOrgs.get(i);
						strDN = ldapOrg.getDN();
						strID = ldapOrg.getOUCode();
						strDisplayName = ldapOrg.getOrganizationalUnitName();
						strChiefName = ldapOrg.getUcChiefTitle();
						strSymbol = ldapOrg.getOUDocumentRecipientSymbol();
						strDepth = ldapOrg.getOULevel();
						strParentID = ldapOrg.getParentOUCode();
						strIsInstitution = ldapOrg.getOUSendOutDocumentYN();					
						strIsProcess = ldapOrg.getOUReceiveDocumentYN();	
						
						if (strIsInstitution.equalsIgnoreCase("Y")) strIsInstitution = "Y"; else strIsInstitution = "N";
						if (strIsProcess.equalsIgnoreCase("Y")) strIsProcess = "Y"; else strIsProcess = "N";
			    		
			    		_class = "class='closed'";
			    		rel = " rel='comp' ";
			    		
			    		Li = new StringBuffer();
			    		Li.append("<li id='"+strID+"' orgId='"+strID+"' parentId='"+strParentID);
			    		Li.append("' orgName='"+strDisplayName+"' DN='"+strDN+"' isinstitution='"+strIsInstitution+"' depth='"+strDepth + "' addrSymbol='"+strSymbol+"' ");
			    		Li.append("' isProcess='"+strIsProcess+"' chiefName='"+strChiefName+"' ");
			    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+ strDisplayName + "</a>");
			    		out.println(Li.toString());
			    		out.println("</li>");
			    	}
				}
				
				out.println("\n</ul>\n</li>\n</ul>");
			%>

			
			</div>				
			</td>
		</tr>
		</acube:tableFrame>
	</div>
	<!-- ------------------------ LDAP 끝------------------------ -->
	<% } %>
	
	<!-- ////////// 수신자그룹 시작 ////////// 2011.05.30 신경훈 -->
			<div id="divRecvGroup" group="recv" style="height:231px;display:none;">
				<div id="divRecvGroupList" style="float:left; width:49%;">
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
						<tr>
							<td height="175" class="basic_box" valign="top" style="padding:1px; margin:1px;">
									
								<!-- 수신자그룹 목록 -->							
								<table cellpadding="0" cellspacing="0" width="300" class="table" style="table-layout:fixed;">
									<tr>
										<td width="55%" class="ltb_head"><spring:message code="env.option.form.03"/></td>
										<td width="20%" class="ltb_head"><spring:message code="env.option.form.21"/></td>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:170px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">								
								<table id="tblRecvGroup" cellpadding="0" cellspacing="0" width="300" class="table_body table_under" style="table-layout:fixed;">
									<tbody>
									
									<c:forEach var="vo" items="${gList}">
										<tr id="${vo.recvGroupId}" name="${vo.recvGroupId}" onDblClick='onRecvGroupDblClick($("#${vo.recvGroupId}"));' onClick='onRecvGroupClick($("#${vo.recvGroupId}")); return false;' bgcolor="#ffffff" style="cursor:pointer;">
											<td width="55%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.recvGroupName}"><nobr>${vo.recvGroupName}</nobr></td>
											<td width="20%" class="ltb_center">${vo.recvCount}</td>
											<td width="25%" class="ltb_center"><spring:message code="env.grouptype.${vo.groupType}"/></td>
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
							
								<!-- 수신경로 -->
								<table cellpadding="0" cellspacing="0" width="300" class="table" style="table-layout:fixed;">
									<tr>
										<td width="40%" class="ltb_head"><spring:message code="env.option.form.16"/></td>
										<td width="35%" class="ltb_head"><spring:message code="env.option.form.17"/></td>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:170px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
								<table id="tblRecvLine" cellpadding="0" cellspacing="0" width="300" class="table_body table_under" style="table-layout:fixed;">
									<tbody />
								</table>
								</div>
								
							</td>
						</tr>
					</acube:tableFrame>
				</div>
			</div>
			<!-- ////////// 수신자그룹 끝 ////////// 2011.05.30 신경훈 -->
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<div style="width:100%;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td width="30%"><spring:message code="env.option.form.21"/> : <strong><font color="#3333ff"><span id="recvCnt"></span></font></strong> 
				</td>
				<td width="40%">				
					<div id ="divBtnRecvLine" style="display:block;">
					<acube:buttonGroup align="center">	
					<acube:button id="includeChildrenBtn" disabledid="" type="right" onclick="onIncludeChildren();" value="<%=includeChildrenBtn%>" />
					<acube:space between="button" />				
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />					
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
					</div>
					<div id ="divBtnRecvGroup" style="display:none;">
					<acube:buttonGroup align="center">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="recvGroupAdd();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
					</div>
				</td>
				<td align="right">
					<acube:buttonGroup align="right">
					<acube:button id="allDelBtn" disabledid="" type="4" onclick="delLineAll();" value="<%=allDelBtn%>" />					
					</acube:buttonGroup>
				</td>
				</tr></table>
			</div>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<!-- 결재 경로 -->
			<table  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<acube:tableFrame width="100%" border="0" cellpadding="1" cellspacing="0" class="td_table table_border">
							<tr>
								<td width="100%" height="100%">
									<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<thead>
											<tr><TD width="200" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.recieve" /></TD>
												<TD width="200"  class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.ref" /></TD>
												<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.recvsymbol" /></TD>
											</tr>
										</thead>
									</table>
									</div>
									<div id="divRecvLines" style="height:115px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
									<table id="tbRecvLines" width="100%" border="0" cellpadding="0" cellspacing="0">
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
							<tr>
								<TD style="background:#ffffff" align="center" valign="middle">
									<IMG src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:onMoveUp();return(false);"><BR /><BR />
									<IMG src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:onMoveDown();return(false);">
								</TD>
							</tr>
						</TABLE>
					</td>
				</tr>
			</table>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<table width='100%' height="30" cellpadding="0" cellspacing="0" border="0" class="td_table">
			<tr>
				
				<td width="24%" class="tb_tit b_td">
				<table width="100%" border="0"><tr>
				<td width="20"><input type="checkbox" name="chkDisplayAs" id="chkDisplayAs" onclick="checkDisplayAs(this.checked);"/></td>
				<td><spring:message code="approval.form.markreciver" /></td></tr></table>
				</td>
				<td width="76%" class="tb_left_bg"><input type="text" name="txtDisplayAs" id="txtDisplayAs" size="30" class="input" style="width:98%;display:none;ime-mode:active;"></td>
			</tr></table>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			
			
			<table width='100%' height="30" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="87%"><spring:message code="approval.msg.recvPopInfo.bottom" /></td>
				<td align="right"><acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=confirmBtn%>" /></td>
				<td><acube:space between="button" /></td>
				<td align="right"><acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn%>" /></td>
			</tr>
			</table>
	</acube:outerFrame>
		<div id="divtest"></div>
		
	<form>
		<input type="hidden" name="recvCntForm" id="recvCntForm" value="0"></input>
	</form>
</body>
</html>