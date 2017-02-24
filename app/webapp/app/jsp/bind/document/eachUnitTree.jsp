<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="java.util.List"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	List<BindUnitVO> unitTree = (List<BindUnitVO>) request.getAttribute("bindUnitList");
	
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디
	
	String userName = (String) session.getAttribute("USER_NAME"); 
	String userUid = (String)session.getAttribute("USER_ID");
		
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='bind.title.add' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">

	function selectNode(node, tree_obj, treeNum)
	{
		var selectItem = $(node);
		
		// 노드 선택시 트리 Open/Close Toggle
		tree_obj.toggle_branch(selectItem);
		
		if (selectItem.attr('bindUnitId')) {
			var unitId = selectItem.attr('bindUnitId');
			var bindId = selectItem.attr('id');
			var bindName = selectItem.attr('bindName');
			var sendType = selectItem.attr('sendType');
			var arrange = selectItem.attr('arrange');
			var binding = selectItem.attr('binding');
			
			// 편철확인/확정이 아니고, 공유기록물철이 아닌 경우 기록물 이동 가능.
			if (arrange == "N" && binding == "N" && (sendType == "DST001" || sendType == "DST002")) {
				$('#all_move', parent.document).show();
				$('#all_move_disabled', parent.document).hide();
				$('#movement', parent.document).show();
				$('#movement_disabled', parent.document).hide();
			} else {
				$('#all_move', parent.document).hide();
				$('#all_move_disabled', parent.document).show();
				$('#movement', parent.document).hide();
				$('#movement_disabled', parent.document).show();
			}
			
			parent.goList(bindId);
		}
		
	}

	//----------------------------초기 OnLoad 이벤트 시작-------------------------------------------//
	$(document).ready(function() { 
		
		// 단위업무 하위 노드에 기록물철 추가
		$("li[type='unit']").each(function() {
			var item = $(this);
			var unitId = item.attr('unitId');
			var subItem = $("[bindUnitId='" + unitId + "']");
			
			item.children('ul').append(subItem);
		});
		
		$('sub').addClass('sub');
		
		$("#unit_tree").tree({
			rules : {
				valid_children : [ "box" ]		
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
				"box" : {				
					icon: {
						image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
					}
				},
				"unit" : {				
					icon: {
						image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
					}
				},
				"bind" : {
					icon: {
						image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"	
					}
				}	
			},
			callback : {
				//노드가 OPEN 될때
				onopen: function(node, tree_obj) {
				},
				//노드를 선택했을 때
				onselect : function(node, tree_obj){
					selectNode(node, tree_obj, 1);
				},
				//노드 생성시 호출됨
				oncreate : function(node, parent, type, tree_obj, rollback) {
				},
				ondblclk : function(node, tree_obj){
				},
				oninit   : function(TREE_OBJ) { 
					//close_all(TREE_OBJ);	
				}
			}
		});
				
		<c:forEach items="${bindUnitList}" var="unit">
			var unitId = '${unit.unitId}';
			var t = $.tree.focused();
			$.tree.focused().close_branch($("li[unitId='"+unitId+"']"));
		</c:forEach>
		
	});
	
	

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_name = "<spring:message code='bind.obj.name'/>";

	var rootText = "<spring:message code='bind.obj.document.division'/>";
	var select_unit = "<spring:message code='bind.msg.select.unit'/>";
	
	var webUri = '<%=webUri%>';
	var year = ${year};
	
	//window.onload = init;

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------컨텐츠  S --------->

	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/bind/create.do">
					
	<div id="unit_tree" style="height:100%; overflow:auto; background-color : #FFFFFF;">
	<% 
		BindUnitVO unitItem = null;
		
		StringBuffer Li = new StringBuffer();
		String _class = "class='open'";
		String rel = "";
		
		String strUnitType = "";
		String strUnitId = "";
		String strUnitName = "";
		String strUnitTempType = "";
		String strBindCount = "";
		String strUnitPre = "";
		int bindCount = 0;
		String isExistUTT002 = "N";
		
		int nSize = unitTree.size();
		for(int i = 0; i < nSize; i++)
		{
			unitItem = unitTree.get(i);
			strUnitType = unitItem.getUnitType();
			
			if ( strUnitType.equals("UTT002" ) ) {
				isExistUTT002 = "Y";
				break;
			}
			
    	}
		
		
   		_class = "class='open'";
   		rel = " rel='box' ";
		Li.append("<ul>");
		Li.append("<li id='UTT001' ");
   		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:middle'></ins><b>" + m.getMessage("bind.code.UTT001", null, langType) + "</b></a>");
   		out.println(Li.toString());
   		
   		out.println("<ul>");

		for(int i = 0; i < nSize; i++)
		{
			unitItem = unitTree.get(i);
			strUnitType = unitItem.getUnitType();
			strUnitId = unitItem.getUnitId();
			strUnitName = unitItem.getUnitName();
			strUnitTempType = unitItem.getUnitTempType();
			bindCount = unitItem.getBindCount();
			strBindCount = "(" + bindCount + ")";
			
			if (strUnitTempType.equals("Y")) {
				strUnitPre = "[" + m.getMessage("bind.obj.temp", null, langType)+"]";
			} else {
			    strUnitPre = "";
			}
			
			if ( strUnitType.equals("UTT001" ) ) {
	    		_class = "class='open'";
	    		rel = " rel='unit' ";
	    		
	    		Li = new StringBuffer();
	    		Li.append("<li id='UTT001"+strUnitId+"' type='unit' unitId='"+strUnitId+"' unitName='"+strUnitName+"' unitType='"+strUnitType+"' ");
	    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:middle'></ins>"+ strUnitPre + strUnitName + strBindCount + "</a>");
	    		out.println(Li.toString());
	    		out.println("<ul></ul>");
	    		out.println("</li>");
			}
			
    	}
		out.println("\n</ul>\n</li>");
		
		if ( isExistUTT002.equals("Y") )
		{
			_class = "class='open'";
    		rel = " rel='box' ";
			Li = new StringBuffer();
			Li.append("<li id='UTT002' ");
    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:middle'></ins><b>" + m.getMessage("bind.code.UTT002", null, langType) + "</b></a>");
    		out.println(Li.toString());
    		
    		out.println("<ul>");

			for(int i = 0; i < nSize; i++)
			{
				unitItem = unitTree.get(i);
				strUnitType = unitItem.getUnitType();
				strUnitId = unitItem.getUnitId();
				strUnitName = unitItem.getUnitName();
				strUnitTempType = unitItem.getUnitTempType();
				bindCount = unitItem.getBindCount();
				strBindCount = "(" + bindCount + ")";
				
				if (strUnitTempType.equals("Y")) {
					strUnitPre = "[" + m.getMessage("bind.obj.temp", null, langType)+"]";
				} else {
				    strUnitPre = "";
				}
				
				if ( strUnitType.equals("UTT002" ) ) {
		    		_class = "class='open'";
		    		rel = " rel='unit' ";
		    		
		    		Li = new StringBuffer();
		    		Li.append("<li id='UTT002"+strUnitId+"' type='unit' unitId='"+strUnitId+"' unitName='"+strUnitName+"' unitType='"+strUnitType+"' ");
		    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:middle'></ins>"+ strUnitPre + strUnitName + strBindCount + "</a>");
		    		out.println(Li.toString());
		    		out.println("<ul></ul>");
		    		out.println("</li>");
				}
				
	    	}
			
			out.println("\n</ul>\n</li>");
		}
		
		// 기록물철 노드.
		rel = " rel='item' ";
		String strBindId = "";
		String strBindName = "";
		String strSendType = "";
		String strArrange = "";
		String strBinding = "";
		String strBindPre = "";
		List<BindVO> rows = (List<BindVO>) request.getAttribute("rows");
		int bindCnt = rows.size();
		
		BindVO bindItem = null;
		for (int i=0; i< bindCnt; i++) {
		    bindItem = rows.get(i);
		    
			strUnitId = bindItem.getUnitId();
			strBindId = bindItem.getBindId();
			strBindName = bindItem.getBindName();
			strSendType = bindItem.getSendType();
			strArrange = bindItem.getArrange();
			strBinding = bindItem.getBinding();
			
			if (strSendType.equals("DST004")) {
			    strBindPre = m.getMessage("bind.obj.share.prefix", null, langType);
			}
		    
		    Li = new StringBuffer();
    		Li.append("<li class='leaf' id='"+strBindId+"' type='bind' bindUnitId='"+strUnitId+"' bindName='"+strBindName+"' sendType='"+strSendType+"' arrange='"+strArrange+"' binding='"+strBinding+"'");
    		Li.append(rel + "><a href='#'><ins class='sub' style='vertical-align:middle'></ins>"+ strBindPre + strBindName + "</a>");
    		out.println(Li.toString());
    		out.println("</li>");
		}
		
		out.println("\n</ul>");
	%>

	
	</div>	

	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->

<!-------컨텐츠 Table S --------->

</body>
</html>