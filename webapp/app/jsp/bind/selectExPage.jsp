<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
	//새마을금고전자결재고도화 금고에서 명칭 변경위해서 부서코드가져옴 2014.12.05 HJ
	String compId = (String)session.getAttribute("COMP_ID");
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
	List<BindUnitVO> unitTree = (List<BindUnitVO>) request.getAttribute("bindUnitList");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.select' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<style>
	.scrollbox {width:100%; height:100%; overflow:auto; padding:2px; border:1px; border-style:solid; border-color:#DDDDDD;
		scrollbar-face-color: #EEEEEE;
		scrollbar-highlight-color: #EEEEEE;
		scrollbar-3dlight-color: #AAAAAA;
		scrollbar-shadow-color: #AAAAAA;
		scrollbar-darkshadow-color: #EEEEEE;
		scrollbar-track-color: #DDDDDD;
		scrollbar-arrow-color: #666666;
	}
	.scrollboxNB {width:100%; height:100%; overflow:auto; padding:2px; border:0px; border-style:solid; border-color:gray}
</style>

<script language="javascript">

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
					var selectItem = $(node);
					if (selectItem.attr('bindUnitId')) {
						selectTarget();
					}
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
	
	function selectNode(node, tree_obj, treeNum)
	{
		var selectItem = $(node);		
		
		// 노드 선택시 트리 Open/Close Toggle
		tree_obj.toggle_branch(selectItem);
		
		if (selectItem.attr('bindUnitId')) {
			var unitId = selectItem.attr('bindUnitId');
			var unitName = selectItem.attr('bindUnitName');
			var bindId = selectItem.attr('id');
			var bindName = selectItem.attr('bindName');	
			var retentionPeriod = "";
			var bindingResourceId = "";
			
			select(bindId, bindName, unitId, unitName, retentionPeriod, bindingResourceId);
		}
		
	}

	//function init() {
		// document.listForm.searchYear.value = '${year}';
	//}

	function selectTarget() {
		var form = document.listForm;
		var bindId = form.bindId.value;

		if(bindId == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else {
			var bind = {};
			bind['bindingId'] = form.bindId.value;
			bind['bindingName'] = form.bindName.value;
			bind['unitId'] = form.unitId.value;
			bind['unitName'] = form.unitName.value;
			bind['retentionPeriod'] = form.retentionPeriod.value;

			// 편철 다국어 추가
			bind['bindingResourceId'] = form.bindingResourceId.value;
			
			if(opener && opener.setBind) {
				opener.focus();
				opener.setBind(bind);
				window.close();
			}
		}
	}

	function changeSearchYear(searchYear) {
		var f = document.listForm;
		f.searchYear.value = searchYear;
		document.listForm.submit();
	}

	function select(bindId, bindName, unitId, unitName, retentionPeriod, bindingResourceId) {
		var f = document.listForm;
		f.bindId.value = bindId;
		f.bindName.value = bindName;
		f.unitId.value = unitId;
		f.unitName.value = unitName;
		f.retentionPeriod.value = retentionPeriod;

		// 편철 다국어 추가
		f.bindingResourceId.value = bindingResourceId;
	} 
	
	function selectDbl(bindId, bindName, unitId, unitName, retentionPeriod, bindingResourceId) {
		var f = document.listForm;
		f.bindId.value = bindId;
		f.bindName.value = bindName;
		f.unitId.value = unitId;
		f.unitName.value = unitName;
		f.retentionPeriod.value = retentionPeriod;

		// 편철 다국어 추가
		f.bindingResourceId.value = bindingResourceId;

		selectTarget();
	}
	
	var webUri = '<%=webUri%>';
	
	//window.onload = init;
	
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/selectEx.do'>
	<input type="hidden" name="bindId" />
	<input type="hidden" name="unitId" />
	<input type="hidden" name="unitName" />
	<input type="hidden" name="retentionPeriod" />
	<input type="hidden" name="serialNumber" value="${serialNumber}" />
	<input type="hidden" name="bindingResourceId" />
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<acube:titleBar><spring:message code="bind.title.select" /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<!--
		<tr>
			<td>
			<table border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td class="basic_text">
	                  <c:choose>
	                  	<c:when test="${searchOption eq 'Y'}">
	                  	<spring:message code='bind.obj.year.point' />
	                  	</c:when>
	                  	<c:otherwise>
	                  	<spring:message code='bind.obj.period' />
	                  	</c:otherwise>
	                  </c:choose>
					</td>
					<td width="10" align="center">:</td>
					<td><form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		-->
		<tr>
			<td colspan="2">
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%;height:350;">
			
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
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
						List<BindVO> rows = (List<BindVO>) request.getAttribute("rows");
						int bindCnt = rows.size();
						
						BindVO bindItem = null;
						for (int i=0; i< bindCnt; i++) {
						    bindItem = rows.get(i);
						    
							strUnitId = bindItem.getUnitId();
							strUnitName = bindItem.getUnitName();
							strBindId = bindItem.getBindId();
							strBindName = bindItem.getBindName();
						    
						    Li = new StringBuffer();
				    		Li.append("<li class='leaf' id='"+strBindId+"' type='bind' bindUnitId='"+strUnitId+"' bindUnitName='"+strUnitName+"' bindName='"+strBindName+"' ");
				    		Li.append(rel + "><a href='#'><ins class='sub' style='vertical-align:middle'></ins>"+ strBindName + "</a>");
				    		out.println(Li.toString());
				    		out.println("</li>");
						}
						
						out.println("\n</ul>");
					%>
				
					
					</div>
					</td>
				</tr>
			</table>
			</DIV>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td class="basic_text" nowrap="nowrap"><spring:message code='bind.obj.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="bindName" size="42" class="input_read" readonly /></td>
				<td width="3"></td>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:selectTarget();"
							value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
						<acube:space between="button" />
						<acube:button onclick="javascript:window.close();"
							value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
					</acube:buttonGroup>
				</td>
			</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</Body>
</Html>
