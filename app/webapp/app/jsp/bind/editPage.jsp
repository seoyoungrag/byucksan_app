<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="java.util.List"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	List<BindUnitVO> unitTree = (List<BindUnitVO>) request.getAttribute("bindUnitList");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.edit' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">

	function selectNode(node, tree_obj, treeNum)
	{
		var unitId = node.getAttribute('unitId');
		var unitName = node.getAttribute('unitName');
		var unitType = node.getAttribute('unitType');
		$("#unitId").val(unitId);
		$("#unitName").val(unitName);
		$("#modifiedUnitType").val(unitType);
	}

	//----------------------------초기 OnLoad 이벤트 시작-------------------------------------------//
	$(document).ready(function() { 
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
				"box" : {				
					icon: {
						image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
					}
				},
				"item" : {
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
				}
			}
		});


		var form = document.getElementById('bindForm');
		form.docType.value = '${row.docType}';
		form.retentionPeriod.value = '${row.retentionPeriod}';
	});

	function edit() {
		var form = document.getElementById('bindForm');
	
		var unitId = form.unitId.value.trim();
		if(unitId == null || unitId == '') {
			alert(bind_unit_code_required);
			form.unitId.focus();
			return;
		}
	
		var unitName = form.unitName.value.trim();
		if(unitName == null || unitName == '') {
			alert(bind_unit_name_required);
			form.unitName.focus();
			return;
		}

		var bindName = form.bindName.value.trim();
		if(bindName == null || bindName == '') {
			alert(bind_name_required);
			form.bindName.focus();
			return;
		}
		
		var createYear = form.createYear.value.trim();
		if(createYear == null || createYear == '') {
			alert(bind_create_year_required);
			form.createYear.focus();
			return;
		}

		var expireYear = form.expireYear.value.trim();
		if(expireYear == null || expireYear == '') {
			alert(bind_expire_year_required);
			form.expireYear.focus();
			return;
		}

		var prefix = form.prefix.value.trim();
		
		if(this.year > createYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.create.greater.than.current' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.create.greater.than.current.period' arguments='${year}'/>");
			}
			form.expireYear.focus();
			return;
		} else if(this.year> expireYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.expire.greater.than.current' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.expire.greater.than.current.period' arguments='${year}'/>");
			}
			form.expireYear.focus();
			return;
		} else if(createYear > expireYear) {
			if('${option}' == 'year') {
				alert("<spring:message code='bind.msg.expire.greater.than.create' arguments='${year}'/>");
			} else {
				alert("<spring:message code='bind.msg.expire.greater.than.create.period' arguments='${year}'/>");
			}
			form.expireYear.focus();
			return;
		}

		var retentionPeriod = form.retentionPeriod.value.trim();
		if(retentionPeriod == null || retentionPeriod == '' || retentionPeriod == '_') {
			alert(bind_retentionPeriod_required);
			form.retentionPeriod.focus();
			return;
		}

		var bindId = form.bindId.value;
		var docType = form.docType.value;
		var deptId = form.deptId.value;
		var originalUnitType = form.originalUnitType.value;
		var modifiedUnitType = form.modifiedUnitType.value;
		var resourceId = '${row.resourceId}';

		$.ajax({
			url : '<%=webUri%>/app/bind/update.do',
			type : 'POST',
			dataType : 'json',
			data : {
				unitId : unitId,
				unitName : unitName,
				originalUnitType : originalUnitType,
				modifiedUnitType : modifiedUnitType,
				bindId : bindId,
				bindName : bindName,
				createYear : createYear,
				expireYear : expireYear,
				retentionPeriod : retentionPeriod,
				docType : docType,
				deptId : deptId,
				prefix : prefix
			},
			success : function(data) {
				if(data.success) {
					// 다국어 추가
					registResource(resourceId);
										
					alert("<spring:message code='bind.msg.edit.completed'/>");
					
					var f = opener.document.listForm;
					f.action = '<%=webUri%>/app/bind/list.do';
					f.target = '_self';
					f.submit();
					
					window.close();
				} else {
					if(data.msgId) {
						alert(data.msg);
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				}
			},
			error : function(data) {
				alert("<spring:message code='bind.msg.error'/>");
			}
		});
	}

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_name = "<spring:message code='bind.obj.name'/>";
	var bind_create_year = '${option}' == 'year' ? "<spring:message code='bind.obj.create.year'/>" : "<spring:message code='bind.obj.create.period'/>";
	var bind_expire_year = '${option}' == 'year' ? "<spring:message code='bind.obj.expire.year'/>" : "<spring:message code='bind.obj.expire.period'/>";
	var bind_retentionPeriod = "<spring:message code='bind.obj.retention.period'/>";
	var rootText = "<spring:message code='bind.obj.document.division'/>";
	var select_unit = "<spring:message code='bind.msg.select.unit'/>";
		
	var bind_unit_code_required = bind_unit_code + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_name_required = bind_unit_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_name_required = bind_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_create_year_required = bind_create_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_expire_year_required = bind_expire_year + " <spring:message code='bind.msg.mandantory'/>";
	var bind_retentionPeriod_required = bind_retentionPeriod + " <spring:message code='bind.msg.mandantory'/>";
	var msg_completed = "<spring:message code='bind.msg.completed'/>";

	var webUri = '<%=webUri%>';

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='bind.title.edit' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="bindForm" method="POST" action="<%=webUri%>/app/bind/update.do" onsubmit="return false;">
	<input type="hidden" name="bindId" value="${row.bindId}"/>
	<input type="hidden" name="deptId" value="${row.deptId}"/>
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit" nowrap="nowrap">
							<spring:message code='bind.obj.unit.code' /> <spring:message code='common.title.essentiality'/>
						</td>
						<td height="200">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr><td colspan="2">
	<div id="divTree">
		<acube:tableFrame class="">
		<tr>
			<td height="170" class="basic_box">
			<div id="unit_tree" style="height:170px; overflow:auto; background-color : #FFFFFF;">
			<% 
				BindUnitVO unitItem = null;
				
				StringBuffer Li = new StringBuffer();
				String _class = "class='open'";
				String rel = "";
	    		
	    		rel = " rel='box' ";
				
				String strUnitType = "";
				String strUnitId = "";
				String strUnitName = "";
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
				
				Li.append("<ul>");
				Li.append("<li id='UTT001' ");
	    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins><b>" + m.getMessage("bind.code.UTT001", null, langType) + "</b></a>");
	    		out.println(Li.toString());
	    		
	    		out.println("<ul>");

				for(int i = 0; i < nSize; i++)
				{
					unitItem = unitTree.get(i);
					strUnitType = unitItem.getUnitType();
					strUnitId = unitItem.getUnitId();
					strUnitName = unitItem.getUnitName();
					
					if ( strUnitType.equals("UTT001" ) ) {
			    		_class = "class='open'";
			    		rel = " rel='item' ";
			    		
			    		Li = new StringBuffer();
			    		Li.append("<li id='UTT001"+strUnitId+"' unitId='"+strUnitId+"' unitName='"+strUnitName+"' unitType='"+strUnitType+"' ");
			    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+ strUnitName + "</a>");
			    		out.println(Li.toString());
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
		    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins><b>" + m.getMessage("bind.code.UTT002", null, langType) + "</b></a>");
		    		out.println(Li.toString());
		    		
		    		out.println("<ul>");
	
					for(int i = 0; i < nSize; i++)
					{
						unitItem = unitTree.get(i);
						strUnitType = unitItem.getUnitType();
						strUnitId = unitItem.getUnitId();
						strUnitName = unitItem.getUnitName();
						
						if ( strUnitType.equals("UTT002" ) ) {
				    		_class = "class='open'";
				    		rel = " rel='item' ";
				    		
				    		Li = new StringBuffer();
				    		Li.append("<li id='UTT002"+strUnitId+"' unitId='"+strUnitId+"' unitName='"+strUnitName+"' unitType='"+strUnitType+"' ");
				    		Li.append(_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+ strUnitName + "</a>");
				    		out.println(Li.toString());
				    		out.println("</li>");
						}
						
			    	}
					
					out.println("\n</ul>\n</li>");
				}
				
				
				
				out.println("\n</ul>");
			%>

			
			</div>	
			</td>
		</tr>
		</acube:tableFrame>
	</div>						
							
							</td></tr>
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input_read" id="unitId" name="unitId" value="${row.unitId}" readonly size="12"></td>
								<td style="padding-left:-1px; width: 100%"><input type="text" class="input_read" id="unitName" name="unitName" value="${row.unitName}" readonly size="35"></td>
								<input type="hidden" id="originalUnitType" value="${row.unitType}" />
								<input type="hidden" id="modifiedUnitType" />
							</tr>
						</table>
						</td>
					</tr>
	
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='bind.obj.name' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="bindName" value="${row.bindName}" onclick="showResource(this, 'APP_BIND', 'BIND_NAME', '${row.resourceId}', '${row.bindName}')" readOnly size="49"></td>
							</tr>
						</table>
						</td>
					</tr>

					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='bind.obj.type' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td><form:select id="docType" name="docType" path="docType" items="${docType}" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='bind.obj.prefix.code' />
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="prefix" value="${row.prefix}" onkeyup="checkInputMaxLength(this, '', 12)" size="29"></td>
							</tr>
						</table>
						</td>
					</tr>
					
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub">
					<spring:message code='bind.title.classification' />
				</acube:titleBar>
			</td>
		</tr>
		<tr>
			<td><acube:tableFrame>
				<tr bgcolor="#ffffff">
					<td width="150" class="tb_tit">
						<c:choose>
						<c:when test="${option != 'year'}">
							<spring:message code='bind.obj.create.period' />
						</c:when>
						<c:otherwise>
							<spring:message code='bind.obj.create.year' />
						</c:otherwise>
						</c:choose>
						<spring:message code='common.title.essentiality'/>
					</td>
					<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td class="communi_text_list">
									<input type="text" class="input_read" name="createYear" id="createYear"			
										style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);"
										maxlength="4" size="6" value="${row.createYear}" readonly>
										
									<c:choose>
									<c:when test="${option != 'year'}">
										<spring:message code='bind.obj.period' />
									</c:when>
									<c:otherwise>
										<spring:message code='bind.obj.year'/>
									</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr bgcolor="#ffffff">
					<td width="150" class="tb_tit">
						<c:choose>
						<c:when test="${option != 'year'}">
							<spring:message code='bind.obj.expire.period' />
						</c:when>
						<c:otherwise>
							<spring:message code='bind.obj.expire.year' />
						</c:otherwise>
						</c:choose>
						<spring:message code='common.title.essentiality'/>
					</td>
					<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td class="communi_text_list">
									<input type="text" class="input" name="expireYear" id="expireYear"
										style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);"
										maxlength="4" size="6" value="${row.expireYear}">
	
									<c:choose>
									<c:when test="${option != 'year'}">
										<spring:message code='bind.obj.period' />
									</c:when>
									<c:otherwise>
										<spring:message code='bind.obj.year'/>
									</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr bgcolor="#ffffff">
					<td width="150" class="tb_tit">
						<spring:message code='bind.obj.retention.period' /> <spring:message code='common.title.essentiality'/>
					</td>
					<td>
					<table border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" /></td>
						</tr>
					</table>
					</td>
				</tr>
			</acube:tableFrame> <!-------정보등록 Table E ---------></td>
		</tr>
		<!-------내용조회  E --------->

		<!-------여백 H5  S --------->
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:edit();"
						value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>' type="2" class="gr" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>