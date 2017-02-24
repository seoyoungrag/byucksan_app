<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.Locale,org.anyframe.util.StringUtil"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
    /** 
	 *  Class Name  : InsertEnvCategory.jsp 
	 *  Description : 양식함 수정
	 *  Modification Information 
	 * 
	 *   수 정 일 : 2011.04.25 
	 *   수 정 자 : 윤동원 
	 *   수정내용 : KDB 요건반영 
	 * 
	 *  @author  윤동원
	 *  @since 2011. 4. 25 
	 *  @version 1.0 
	 */
%>

<%
	String categoryName  = URLDecoder.decode((String)request.getParameter("categoryName"), "UTF-8");
    String categoryId    = (String)request.getParameter("categoryId");
    String categoryOrder = (String)request.getParameter("categoryOrder");
    String deleteYn      = StringUtil.null2str((String)request.getParameter("deleteYn"), "N");
    String resourceId    = URLDecoder.decode((String)request.getParameter("resourceId"), "UTF-8");
    
    String btnOk = messageSource.getMessage("appcom.button.ok" , null, langType);
    String btnCancel = messageSource.getMessage("appcom.button.cancel" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='env.form.title.categoryupd'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
    // 함수정
	function fnc_categoryUpdate() {
        $.post("<%=webUri%>/app/env/admin/updateCategory.do", $("#frmCategory").serialize(), function(data){
        	if ("OK" == data.result ) {  
				// 다국어 저장 
        		registResource("<%= categoryId %>"); 
            	
				alert("<spring:message code='env.form.msg.confirm.categoryupdok'/>");
                opener.fnc_searchList();
                window.close();
			} else {
				 alert("<spring:message code='env.form.msg.fail'/>"+"["+data.result+"]");
			}	
        }, 'json');
	}
    
    // 취소
    function fnc_cancel() {
        window.close();
    }
</script>
</head>
<body>

<acube:outerFrame>
<form name="frmCategory" id="frmCategory" onsubmit="return false;">
<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td ><acube:titleBar><spring:message code='env.form.title.categoryupd'/></acube:titleBar></td>
	</tr>
	<tr>
    	<acube:space between="title_button" />
	</tr>
	<tr>
		<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='1'cellpadding='0' class="table">
				<tr>
					<td width="100" class="tb_tit_left"><spring:message code='env.form.categoryid'/></td>
					<td class="tb_left_bg"><%=categoryId %></td>
				</tr>
				<tr>
					<td width="70" class="tb_tit_left"><spring:message code='env.form.categoryname'/><spring:message code='common.title.essentiality'/></td>
					<td class="tb_left_bg">
						<input class="input" type="text" onclick="showResource(this, 'CATEGORY', 'CATEGORY_NAME', '<%= resourceId %>', '<%=EscapeUtil.escapeHtmlTag(categoryName)%>')" style="width:100%"  name="categoryName" id="categoryName" value="<%=EscapeUtil.escapeHtmlTag(categoryName)%>" readOnly></input>
						<input class="input" type="hidden"  mandatory="Y" msg="<spring:message code='env.form.categoryname'/>" style="width:100%;ime-mode:active;"  name="categoryId" id="categoryId" value="<%=categoryId%>"></input>
					</td>
				</tr>
				<tr>
					<td width="70" class="tb_tit_left"><spring:message code='env.form.categoryorder'/><spring:message code='common.title.essentiality'/></td>
					<td class="tb_left_bg">
						<input class="input" type="text"   onkeyup="checkInputMaxLength(this,'',3)" style="width:100%"  name="categoryOrder" id="categoryOrder" value="<%=categoryOrder%>"></input>
					</td>
				</tr>
				<tr>
					<td width="70" class="tb_tit_left"><spring:message code='env.bizsystem.useyn'/><spring:message code='common.title.essentiality'/></td>
					<td class="tb_left_bg">
						<input  type="radio"   name="deleteYn" id="deleteYn" value="N" <%= deleteYn.equals("N") ? "checked" :"" %>/><spring:message code='env.form.use'/>&nbsp;
						<input  type="radio"   name="deleteYn" id="deleteYn" value="T" <%= deleteYn.equals("T") ? "checked" :"" %>/><spring:message code='env.form.viewonly'/>&nbsp;
						<input  type="radio"   name="deleteYn" id="deleteYn" value="Y" <%= deleteYn.equals("Y") ? "checked" :"" %>/><spring:message code='env.form.unuse'/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
    	<acube:space between="title_button" />
	</tr>
	<tr>
    	<td>
			<acube:buttonGroup>
				<acube:button onclick="javascript:fnc_categoryUpdate();" value="<%=btnOk%>"/>
				<acube:space between="button" />
				<acube:button onclick="javascript:fnc_cancel();" value="<%=btnCancel%>"/>
			</acube:buttonGroup>
		</td>
	</tr>
</table>
</form>
 </acube:outerFrame>
</body>
</html>