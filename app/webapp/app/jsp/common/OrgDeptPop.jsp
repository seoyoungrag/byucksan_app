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
	
	String confirmYn = request.getParameter("confirmYn");
	confirmYn = (confirmYn == null ? "N" : confirmYn);
	
	String msgType = request.getParameter("msgType");
	msgType = (msgType == null ? "" : msgType);
	
	String deptId = request.getParameter("deptId");
	
	deptId = (deptId == null ? (String) session.getAttribute("DEPT_ID") : deptId);
	deptId = ("".equals(deptId) == true ? (String) session.getAttribute("DEPT_ID") : deptId);
	pageContext.setAttribute("DEPT_ID", deptId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="common.title.deptlist" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript"><!--
var gNode = null;
var confirmYn = "<%=confirmYn%>";
var msgType = "<%=msgType%>";

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
			ondblclk : function(node, tree_obj){
				dbclickNode(node, tree_obj);
			}
		}
	});

	var errOrg = $('li[orgType="2"][parentId="'+$('li[orgType=0]').attr('orgId')+'"]');
	errOrg.remove(); //부서이면서 그룹을 부모로 하는 것 삭제
	
	$.tree.focused().select_branch('#${userProfile.compId}');
	
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
			}else  if(nodeObj[i].orgType === 2){
				//rel="dept";
				if(nodeObj[i].isProcess){
					rel="proc_dept";
				}else{
					rel="dept";
				}
			}else {
				rel="part";
			}	
			t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, "rel":rel},
				data:{title:nodeObj[i].orgName}}, '#'+nodeObj[i].orgParentID);	
		} catch(e) {}
	}
}


//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	var url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	url += "?deptID="+node.id;
	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});
	addAttrOrg(results, node);
	
	gNode = node;
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

function dbclickNode(node, tree_obj){
	sendOk();
}

//확인버튼 클릭
function sendOk(){
	var obj = new Object();

	if(gNode !== null){
		if(confirmYn === "Y"){
			if(!confirm("<spring:message code='common.msg.adddept' />")){
				return;
			}
		}else{
			if(msgType === "share"){
				msgTmp = "<spring:message code='common.msg.adddept2' />";
				msgTmp = msgTmp.replace(/%s/g, gNode.orgName);
				if(!confirm(msgTmp)){
					return;
				}
			}
			else if(msgType === "transpose"){
				msgTmp = "<spring:message code='common.msg.adddept3' />";
				msgTmp = msgTmp.replace(/%s/g, gNode.orgName);
				if(!confirm(msgTmp)){
					return;
				}
			}
		}
		
		obj.zipCode =  gNode.zipCode;
		obj.telephone =  gNode.telephone;
		obj.roleCodes =  gNode.roleCodes;
		obj.reserved3 =  gNode.reserved3;
		obj.reserved2 =  gNode.reserved2;
		obj.reserved1 =  gNode.reserved1;
		obj.proxyDocHandleDeptCode =  gNode.proxyDocHandleDeptCode;
		obj.outgoingName =  gNode.outgoingName;
		obj.orgType =  gNode.orgType;
		obj.orgParentId =  gNode.orgParentID;
		obj.orgOtherName =  gNode.orgOtherName;
		obj.orgOrder =  gNode.orgOrder;
		obj.orgName =  gNode.orgName;
		obj.orgId =  gNode.orgID;
		obj.orgCode =  gNode.orgCode;
		obj.orgAbbrName =  gNode.orgAbbrName;
		obj.oDCDCode =  gNode.oDCDCode;
		obj.isProxyDocHandleDept =  gNode.isProxyDocHandleDept;
		obj.isProcess =  gNode.isProcess;
		obj.isODCD =  gNode.isODCD;
		obj.isInstitution =  gNode.isInstitution;
		obj.isInspection =  gNode.isInspection;
		obj.isDeleted =  gNode.isDeleted;
		obj.institutionDisplayName =  gNode.institutionDisplayName;
		obj.homepage =  gNode.homepage;
		obj.formBoxInfo =  gNode.formBoxInfo;
		obj.fax =  gNode.fax;
		obj.email =  gNode.email;
		obj.description =  gNode.description;
		obj.companyId =  gNode.companyID;
		obj.chiefPosition =  gNode.chiefPosition;
		obj.addrSymbol =  gNode.addrSymbol;
		obj.addressDetail =  gNode.addressDetail;
		obj.address =  gNode.address;	
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

	}

	if (opener != null && opener.setDeptInfo) {
		var msg = opener.setDeptInfo(obj);
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
	<spring:message code="common.title.deptlist" />
	</span>
</td>
<td></td>
</tr>
	<!-- 여백 시작 -->
	<acube:space between="button_content" table="y"/>
	<!-- 여백 끝 -->
	
	<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
	<tr>
		<td width="48%">
		 <!-- 트리용 테이블  -->
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0"  class="">
		<tr>
			<td height="170" class="basic_box">
			<div id="org_tree" style="height:170px; overflow:auto; background-color : #FFFFFF;">
		    <ul>
<!-- 		    	<li id="ROOT" class="open"><a href='#'><ins></ins>ROOT</a>  -->
<!--		    	<ul> -->
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
					    _class = "class='open'";
					    rel = " rel='comp' ";
					}else  if(result.getOrgType() == 2){
					    _class = "class='closed'";
					   // rel = " rel='dept' ";
					   	if(result.getIsProcess()){
					   	 	rel = " rel='proc_dept' ";
					   	}else{
					   		rel = " rel='dept' ";
					   	}
					}else{
					    _class = "class='closed'";
					    rel = " rel='part' ";
					}
		    		
		    		Li.append("<li id='"+result.getOrgID()+"' orgId='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgType='"+result.getOrgType()+"' ");
		    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
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
<!--		    	</ul> -->
<!--				</li> -->
		    </ul>
			</div>				
			</td>
		</tr>
		</acube:tableFrame><!-- 트리용 테이블 -->
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