<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.idir.org.hierarchy.Classification"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : OrgClassificationPop.jsp
 *  Description : 분류체계  트리목록
 *  Modification Information 
 * 
 *   수 정 일 : 2011.08.05 
 *   수 정 자 : 장진홍
 *   수정내용 : 신규개발 
 * 
 *  @author  장진홍
 *  @since 2011.08.05 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId = CommonUtil.nullTrim((String) session.getAttribute("COMP_ID")); // 사용자 소속 회사 아이디
	
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인	
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기	

	List<Classification> results = (List<Classification>) request.getAttribute("result");		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="common.title.classification.hierarchy" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript"><!--
var gNode = null;

$(document).ready(function() { 
		$("#org_tree").tree({
			rules : {
			valid_children : [ "root" ]
			},
			types : {
				"default" : {
					deletable : false,
					renameable : false,
					draggable : false
				},
				"root" : {
					clickable : false,
					icon : { 
						image : "<%=webUri%>/app/ref/js/jsTree/demo/drive.png"
					}
				},
				"folder" : {
					clickable : true //folder도 클릭 되도록 변경, jd.park, 20120508
				},
				"file" : {
					clickable : true,
					icon : { 
						image : "<%=webUri%>/app/ref/js/jsTree/demo/file.png"
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
});

//------------------------트리 이벤트 시작--------------------------------------------------------
//노트가 열렸을때 발생하는 이벤트를 처리하는 함수
function openNode(node, tree_obj){
	if(node.id != "ROOT"){
		var nSize = tree_obj.children(node).length; //하위노드의 수 
		if(nSize < 1){
			var results = null;
			results = getSubNode(node.id);
			drawSubTree(node, results);
			$('#'+node.id+' li').removeClass('leaf');
			$('#'+node.id+' li').addClass('closed');
		}		
		//$.tree.focused().select_branch("#"+node.id);
	}
}

//하위 노드를 가져온다.
function getSubNode(pId){
	var results = null;
	var url = "<%=webUri%>/app/common/selectClassification.do?method=2";
	url += "&classificationId="+pId;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	

	return results;
}

//하위 노드를 그리는 함수
function drawSubTree(node, nodeObj){
	var t = $.tree.focused();
	var depth = new Number(node.getAttribute("depth")) + 1;

	for(var i=0; i < nodeObj.length; i++) {
		try {
			var childs = getSubNode(nodeObj[i].classificationID);
			var id = nodeObj[i].classificationID;
			var name = nodeObj[i].classificationName;
			var rel = "folder";

			if(childs.length === 0){
				rel = "file"
			}
			var group = node.getAttribute("group");
			var classificationCode = nodeObj[i].classificationCode;
			var viewTitle = "";
			if(classificationCode != ""){
				viewTitle = "["+classificationCode+"] "+nodeObj[i].classificationName;
			}else{
				viewTitle = nodeObj[i].classificationName;
			}				
			t.create({attributes:{'id':id, 'group':group, "classificationCode":classificationCode, "name":name ,"depth":depth, "rel": rel},
				data:{title:viewTitle}}, '#'+node.id);
				
		} catch(e) {}
	}
}


//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	gNode = node
}

//노드 더블 클릭하였을때 발생하는 이벤트 처리 함수, jd.park, 20120508
function dbclickNode(node, tree_obj){
	//if(node.getAttribute("rel") === "file"){
		gNode = node
		sendOk();
	//}
}

function getBindInfo(unitId){
	var results = null;
	var url = "<%=webUri%>/app/common/selectClassification.do?method=3";
	url += "&unitId="+unitId;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	

	return results;
}

// 확인버튼 클릭
// jd.park. 20120508 편철과 문서분류 분리
function sendOk(){
	
	if(gNode == null){
		alert('<spring:message code="common.msg.noclassification" />');
		 return; 
	}

	var unitId = gNode.getAttribute("group");
	var result = "";

	//편철과 문서분류 같이 사용할 경우(옵션처리)
	//result = getBindInfo(unitId);
	
	if(result != null){
		var bind = {};
		bind['bindingId'] = result.bindId;
		bind['bindingName'] = result.bindName;
		bind['unitId'] = gNode.getAttribute("id");
		bind['unitName'] = gNode.getAttribute("name");
		bind['retentionPeriod'] = result.retentionPeriod;
		bind['classificationCode'] = gNode.getAttribute("classificationCode");	
		if(opener && opener.setDocKind) {
			//문서번호 다시 가져온후 부모창에 넘겨주기
			var classNumber = "classNumber="+gNode.getAttribute("classificationCode");
			var deptCategory = "";
			$.post("<%=webUri%>/app/env/getEnvDocNumber.do", classNumber, function(data){
				opener.setDocKind(bind);
				if (opener != null && opener.setDeptCategory != null) {
					opener.setDeptCategory(data.result);
				}
				window.close();
			}, 'json').error(function(data) {
				alert("<spring:message code='approval.msg.fail.delete.doc'/>");
			});
		}		
		//편철정보에 넣기(옵션처리)
		/*
		if(opener && opener.setBind) {
			opener.setBind(bind);
			window.close();
		}
		*/
	}else{
		alert('<spring:message code="common.msg.nobindinfo" />');
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<acube:outerFrame>
	<acube:titleBar><spring:message code="common.title.classification.hierarchy" /></acube:titleBar>
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
			<div id="org_tree" style="max-width:390px;height:170px; overflow:auto; background-color : #FFFFFF;">
		    <ul>
		    	<li id="ROOT" class="open" rel="root"><a href='#'><ins style='vertical-align:top'></ins><spring:message code="common.title.classification.hierarchy" /></a> 
		    		<ul>
		    	<%
		    	int nSize = results.size();
		    	String rootOrgId = "";
		    	String rootOrgNm = "";
		    	for(int i = 0; i < nSize; i++){
		    		Classification result = results.get(i);
		    		String rel = "";
		    		StringBuffer Li = new StringBuffer();

		    		out.println("<li id=\""+result.getClassificationID()+"\" group=\""+result.getClassificationID()+"\" classificationCode=\""+result.getClassificationCode()+"\" name=\""+result.getClassificationName()+"\" depth=\"1\" rel=\"folder\" class=\"closed\">");
		    		out.println("<a href=\"#\"><ins style='vertical-align:top'></ins>");		    		
		    		out.println("["+result.getClassificationCode()+"] "+result.getClassificationName());		    		
		    		out.println("</a></li>");
		    	}
		    	%>
		    		</ul>
				</li>
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