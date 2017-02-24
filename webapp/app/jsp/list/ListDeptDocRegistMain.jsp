<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListDocRegist.jsp 
 *  Description : 문서등록대장 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	
	String listTitle = messageSource.getMessage("list.listBox.title.menuDeptSerch" , null, langType);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery-ui-1.8.4.custom.min.js"></script>

<script LANGUAGE="JavaScript">

	function init() {
		
		$('#btLeft').click(function() {
			$("[name='unitZone']").each(function () {
				$(this).hide();
			});
			$('#leftTd').hide();
			$('#rightTd').show();
		});
		
		$('#btRight').click(function() {
			$("[name='unitZone']").each(function () {
				$(this).show();
			});
			$('#rightTd').hide();
			$('#leftTd').show();
		});
		
		$('#btLeft').css("cursor", "hand");
		$('#btRight').css("cursor", "hand");
		
	}
	
	function goAllMovePage() {
		$("#frmDocList")[0].contentWindow.goAllMovePage();
	}

	function goMovePage() {
		$("#frmDocList")[0].contentWindow.goMovePage();
	}
	
	function showButton(btn) {
	    $(btn).show();
	}

	function hideButton(btn) {
		$(btn).hide();
	}
	
	function goList(deptId, deptName) {
		$("#frmDocList").attr("src","<%=webUri%>/app/list/regist/ListDeptDocRegist.do?searchAuthDeptId="+deptId+"&searchAuthDeptName="+deptName);
	}
	
	window.onload = init;
	
</script>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar><%=listTitle%></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		
		<tr>
            <td height="100%" class="communi_text">
            <table height="580" width="100%"  cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td name="unitZone" class="communi_text"></td>
                    <td name="unitZone" height="20" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text" width="150">
                                 <acube:titleBar type="sub"><spring:message code='env.pubreader.tab2' /></acube:titleBar>
                                 </td>
                            </tr>
                         </table>
                     </td>
                     <td class="communi_text"></td>
                     <td colspan="2" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><spring:message code='list.listBox.title.menuDeptSerch' /></acube:titleBar>
                                 </td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td name="unitZone" width="10" class="communi_text"></td>
                    <td name="unitZone" width="320" height="100%">
                    <!------------------------------------단위업무 트리--------------------------------------------->
                    <iframe id='frmUnit' name='frmUnit' src="<%=webUri%>/app/common/DeptDocRegister.do?type=2&treetype=3&confirmYn=Y" scrolling='yes' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    </td>
                    
                    <td width="30" class="text_center" id='leftTd'><img id="btLeft" src="<%=webUri%>/app/ref/image/bu_left2.gif"/></td>
                    <td width="30" class="text_center" id='rightTd' style="display:none"><img id="btRight" src="<%=webUri%>/app/ref/image/bu_right2.gif"/></td>
                    
                   
                    <!-- --------------------------------문서목록------------------------------------------ -->
                    <td width="*" height="100%">
                    <!------------------------------------단위업무 트리--------------------------------------------->
                    <iframe id='frmDocList' name='frmDocList' src="<%=webUri%>/app/list/regist/ListDeptDocRegist.do" scrolling='no' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    </td>
                    <td width= "10" class="communi_text"></td>
                </tr>
                
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
            </table>
            </td>
        </tr>
	</table>
</acube:outerFrame>
</Body>
</Html>