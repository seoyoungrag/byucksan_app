<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;

    String imagePath = webUri + "/app/ref/image";
        
    String sessionCompId = (String) session.getAttribute("COMP_ID");	// 회사 ID
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<script language="javascript">

	var menus = $('a[group=leftMenuGroup]');
	var curMenuId = "";
	
	function init() {
		
		$('[group=leftMenuImg]').hide();
		
		var f = document.listForm;
		f.searchYear.value = '${year}';
		//f.etcYear.value = '${eyear}';
		//f.transYear.value = '${tyear}';
		
		

		if('bind' == '${searchType}') {
			toggleMenu('BindBox');
			
			var bindRow = document.getElementById('bind_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#bind_1');
				var menus = $('[group=leftMenu]');

				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/list.do?bindId=' + bindId + '&searchYear=${year}';
		} else if('etc' == '${searchType}') {
			toggleMenu('EtcBindBox');
			
			var bindRow = document.getElementById('etc_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#etc_1');
				var menus = $('[group=leftMenu]');
				
				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/list.do?bindId=' + bindId + '&searchYear=${tyear}';
		} else if('trans' == '${searchType}') {
			toggleMenu('TransBindBox');
			
			var bindRow = document.getElementById('trans_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#trans_1');
				var menus = $('[group=leftMenu]');
				
				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","bold");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/transfer.do?bindId=' + bindId + '&searchYear=${tyear}';
		}
	}

	function toggleMenu(menuId) {
		var imgId = 'Img' + menuId;
		var subId = menuId + '_Sub';
		var tdId = menuId + '_Td';
		var leftIconId = 'leftIcon' + menuId;
		var yearId = menuId + '_Year';

		var menus = $('a[group=leftMenuGroup]');
		var menusTd = $('[group=leftMenuTd]');
		var menusSub = $('[group=leftMenuSub]');
		var menusImg = $('[group=leftMenuImg]');
		var menusLink	= $('[group=leftMenu]');

		menusTd.css("color","#666666");
		menusTd.css("font-weight","");

		menusLink.css("color","#777777");
		
		if (document.getElementById(menuId).style.display == 'block') {
			$('#'+menuId).hide();
			$('#'+imgId).hide();
			$('#'+subId).css("backgroundColor","#FFFFFF");	
			$('#'+tdId).css("color","#666666");
			$('#'+tdId).removeClass("menu_open");
			$('#'+tdId).addClass("menu_end");
			$('#'+leftIconId).show();
			$('#'+tdId).css("padding-left","5px");
			$('#'+yearId).css("display","none");
			$('#'+menuId).css("display","none");
		} else {
			$('#'+menuId).show();
			$('#'+imgId).show();
			$('#'+imgId).src = "<%=imagePath%>/left_menu/left_icon.gif";
			$('#'+subId).css("backgroundColor","#E0E0E0");
			//if(curMenuId != ""){
				$('#'+tdId).css("color","#7997df");
			//}
			$('#'+tdId).removeClass("menu_end");
			$('#'+tdId).addClass("menu_open");
			$('#'+leftIconId).hide();
			$('#'+tdId).css("padding-left","14px");
			$('#'+yearId).css("display","block");
			$('#'+menuId).css("display","block");
		}

		curMenuId = menuId;
	}
	
	function goBindPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/list.do?bindId=" + bindId + '&searchYear=${year}';
	}
	
	function goEtcPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/list.do?bindId=" + bindId + '&searchYear=${year}';
	}

	function goNonBindBoxPage(id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/nonBind.do?prev=menu";
	}

	function goTransPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");
		
		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/transfer.do?bindId=" + bindId + '&searchYear=${tyear}';
	}

	function changeSearchYear(year, type) {
		var f = document.listForm;
		f.searchType.value = type;
		f.searchYear.value = year;
		//f.transYear.value = year;
		//f.etcYear.value = year;
		f.submit();
	}
	
	var webUri = '<%=webUri%>';
	
	window.onload = init;

	
	function goBindMain(){
		parent.location.href = "<%=webUri%>/app/index.do?type=bind";
	}
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"  style="border-right:solid 1px #dfdfdf">

<form name="listForm" method='post' action='<%=webUri%>/app/bind/menu.do'>
	<input type="hidden" name="searchType" value="${searchType}" />
	
	<tr>
		<td height="1" colspan="3" bgcolor="e3e3e3"></td>
	</tr>
	<tr onClick="javascript:goBindMain();" style="cursor:pointer;">
   		<td height="50" valign="top" background="<%=imagePath%>/left_menu/title_approval_03.jpg"></td>
	</tr>
	
	<tr valign="top">
		<td align="center">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td>
              		<table cellspacing="0" cellpadding="0" border="0" width="100%">
                		<tr>
                  			<td group="leftMenuTd" onclick="javascript:toggleMenu('BindBox');" id="BindBox_Td" class="menu_end">
                  				<img id="leftIconBindBox" src="<%=webUri%>/app/ref/image/left_menu/icon_01.gif"> <spring:message code='bind.title.box' />
                  			</td>
                  			<td group="leftMenuSub" id="BindBox_Year" style="background-color: #E0E0E0;" style="display: none" align="right">
                  				<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value, 'bind');" items="${searchYear}"/>
                  			</td>
                  			<td width="30" style="background-color:#FFFFFF" group="leftMenuSub" id="BindBox_Sub" align="center" style="background-color:#FFFFFF"><img id="ImgBindBox" group="leftMenuImg" src="<%=webUri%>/app/ref/image/left_menu/left_icon.gif"></td>
                		</tr>
              		</table>
              	</td>
			</tr>
			<tr>
				<td class="menu_bg_01">
					<div style="display: none;" id="BindBox">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
	               
				        <c:forEach items="${rows}" var="row" varStatus="seq">
						<tr>
		                  <td align="right" width="22">
		                  	<img src="<%=webUri%>/app/ref/image/left_menu/left_body_icon.gif">
		                  </td>
		                  <c:choose>
		                  	<c:when test="${seq.count == 1}">
		                  		<td class="menu_body" id="bind_${seq.count}" bindId="${row.bindId}" onclick="javascript:goBindPage('${row.bindId}', 'bind_${seq.count}');" group="leftMenu">
			                  		<c:if test="${row.sendType eq 'DST004'}">
			                  			<spring:message code='bind.obj.share.prefix' />
			                  		</c:if>
		                  	 		${row.bindName}
		                  	 	</td>
		                  	</c:when>
		                  	<c:otherwise>
		                  	<td class="menu_body" id="bind_${seq.count}" onclick="javascript:goBindPage('${row.bindId}', 'bind_${seq.count}');" group="leftMenu">
		                  		<c:if test="${row.sendType eq 'DST004'}">
		                  			<spring:message code='bind.obj.share.prefix' />
		                  		</c:if>
		                  	 	${row.bindName}
		                  	</td>
		                  	</c:otherwise>
		                  </c:choose>
		                  
		                </tr>
				        </c:forEach>
	              
	              	</table>
	              	</div>	
				</td>
			</tr>
	       <%--  <tr><td class="menu_line_01"></td></tr>
			<tr>
				<td>
              		<table cellspacing="0" cellpadding="0" border="0" width="100%">
                		<tr>
                  			<td group="leftMenuTd" onclick="javascript:toggleMenu('EtcBindBox');" id="EtcBindBox_Td" class="menu_end">
                  				<img id="leftIconEtcBindBox" src="<%=webUri%>/app/ref/image/left_menu/icon_01.gif"> <spring:message code='bind.title.etc.box' />
                  			</td>
                  			<td group="leftMenuSub" id="EtcBindBox_Year" style="background-color: #E0E0E0;" style="display: none" align="right">
                  				<form:select id="etcYear" name="etcYear" path="etcYear" onchange="javascript:changeSearchYear(this.value, 'etc');" items="${etcYear}"/>
                  			</td>
                  			<td width="30" style="background-color:#FFFFFF" group="leftMenuSub" id="EtcBindBox_Sub" align="center"><img group="leftMenuImg" id="ImgEtcBindBox" src="<%=webUri%>/app/ref/image/left_menu/left_icon.gif"></td>
                		</tr>
              		</table>
              	</td>
			</tr>
			<tr>
				<td class="menu_bg_01">
					<div style="display: none" id="EtcBindBox">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
	               
				        <c:forEach items="${etcRows}" var="row" varStatus="seq">
						<tr>
		                  <td align="right" width="22">
		                  	<img src="<%=webUri%>/app/ref/image/left_menu/left_body_icon.gif">
		                  </td>
		                  <c:choose>
		                  	<c:when  test="${seq.count == 1}">
		                  	<td class="menu_body" id="etc_${seq.count}" bindId="${row.bindId}" onclick="javascript:goEtcPage('${row.bindId}', 'etc_${seq.count}');" group="leftMenu"> ${row.bindName}</td>
		                  	</c:when>
		                  	<c:otherwise>
		                  	<td class="menu_body" id="etc_${seq.count}" onclick="javascript:goEtcPage('${row.bindId}', 'etc_${seq.count}');" group="leftMenu"> ${row.bindName}</td>
		                  	</c:otherwise>
		                  </c:choose>
		                  
		                </tr>
				        </c:forEach>
	              
	              	</table>
	              	</div>	
				</td>
			</tr>
			<tr><td class="menu_line_01"></td></tr>
			<tr>
				<td>
              		<table cellspacing="0" cellpadding="0" border="0" width="100%">
                		<tr>
                  			<td group="leftMenuTd" onclick="javascript:toggleMenu('TransBindBox');" id="TransBindBox_Td" class="menu_end">
                  				<img id="leftIconTransBindBox" src="<%=webUri%>/app/ref/image/left_menu/icon_01.gif"> <spring:message code='bind.obj.trans.box' />
                  			</td>
                  			<td group="leftMenuSub" id="TransBindBox_Year" style="background-color: #E0E0E0;" style="display: none" align="right">
                  				<form:select id="transYear" name="transYear" path="transYear" onchange="javascript:changeSearchYear(this.value, 'trans');" items="${transYear}"/>
                  			</td>
                  			<td width="30" style="background-color:#FFFFFF" group="leftMenuSub" id="TransBindBox_Sub" align="center"><img group="leftMenuImg" id="ImgTransBindBox" src="<%=webUri%>/app/ref/image/left_menu/left_icon.gif"></td>
                		</tr>
              		</table>
              	</td>
			</tr>
			<tr>
				<td class="menu_bg_01">
					<div style="display: none" id="TransBindBox">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
	               
				        <c:forEach items="${tranRows}" var="row" varStatus="seq">
						<tr>
		                  <td align="right" width="22">
		                  	<img src="<%=webUri%>/app/ref/image/left_menu/left_body_icon.gif">
		                  </td>
		                  <c:choose>
		                  	<c:when  test="${seq.count == 1}">
		                  	<td class="menu_body" id="trans_${seq.count}" bindId="${row.bindId}" onclick="javascript:goTransPage('${row.bindId}', 'trans_${seq.count}');" group="leftMenu"> ${row.bindName}</td>
		                  	</c:when>
		                  	<c:otherwise>
		                  	<td class="menu_body" id="trans_${seq.count}" onclick="javascript:goTransPage('${row.bindId}', 'trans_${seq.count}');" group="leftMenu"> ${row.bindName}</td>
		                  	</c:otherwise>
		                  </c:choose>
		                  
		                </tr>
				        </c:forEach>
	              
	              	</table>
	              	</div>	
				</td>
			</tr>
			<tr><td class="menu_line_01"></td></tr> --%>
		</table>
		</td>
	</tr>
</form>
</table>

</Body>
</Html>
