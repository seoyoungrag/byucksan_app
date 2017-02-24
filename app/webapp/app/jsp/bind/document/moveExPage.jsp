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
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
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
		if (selectItem.attr('bindUnitId')) {
			var unitId = selectItem.attr('bindUnitId');
			var unitName = selectItem.attr('bindUnitName');
			var bindId = selectItem.attr('id');
			var bindName = selectItem.attr('bindName');	
			
			select(bindId, bindName);
		}
		
	}

	//function init() {
		//document.listForm.searchYear.value = '${year}';
	//}

	function move() {
		var form = document.listForm;
		var targetId = form.targetId.value;

		if(targetId == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else if (targetId == '${bindId}') {
			alert("<spring:message code='bind.msg.move.not.same' />");
		} else {
			if(confirm("<spring:message code='bind.msg.confirm.bind.move.document' />")) {
				var f = opener.document.listForm;

				$.ajax({
					url : '<%=webUri%>/app/bind/document/movement.do',
					type : 'POST',
					dataType : 'json',
					data : {
						orgBindId : '${bindId}',
						bindId : targetId,
						docId : '${docId}',
						deptId : '${deptId}',
						searchType : f.searchType.value,
						searchValue : f.searchValue.value
					},
					success : function(data) {
						if(data.success) {
							alert("<spring:message code='bind.msg.completed'/>");
							
							// 부모창 리로드 함수 호출.
							$(opener.location).attr("href", "javascript:goList('DEL','${moveCount}');");

							//f.action = '<%=webUri%>/app/bind/document/listEachUnit.do';
							//f.target = '_self';
							//f.submit();

							if(opener.opener) {
								// 부모창 리로드 함수 호출.
								$(opener.opener.location).attr("href", "javascript:goList('DEL','${moveCount}');");
								//var _f = opener.opener.document.listForm;
								//f.action = '<%=webUri%>/app/bind/document/listEachUnit.do';
								//f.target = '_self';
								//_f.submit();
							}
							
							window.close();
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					},
					error : function(data) {
						alert("<spring:message code='bind.msg.error'/>");
					}
				});
			}
		}
	}

	function changeSearchYear(searchYear) {
		var f = document.listForm;
		f.searchYear.value = searchYear;
		document.listForm.submit();
	}

	function select(bindId, bindName) {
		var f = document.listForm;
		f.targetId.value = bindId;
		f.targetName.value = bindName;
	}
	
	var webUri = '<%=webUri%>';
	
	//window.onload = init;
	
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/move.do'>
	<input type="hidden" name="bindId" value="${bindId}"/>
	<input type="hidden" name="docId" value="${docId}" />
	<input type="hidden" name="targetId" />	
	
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
		<tr>
			<acube:space between="menu_list" />
		</tr>
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
		<tr>
			<td colspan="2">
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%;height:325;">
			
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
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td class="basic_text"><spring:message code='bind.obj.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="targetName" size="43" class="input_read" readonly /></td>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:move();"
							value='<%= m.getMessage("bind.button.movement", null, langType) %>' type="2" class="gr" />
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
