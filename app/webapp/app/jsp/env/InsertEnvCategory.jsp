<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>


<%
    /** 
	 *  Class Name  : InsertEnvCategory.jsp 
	 *  Description : 양식함 등록
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

    String btnOk = messageSource.getMessage("appcom.button.ok" , null, langType);
    String btnCancel = messageSource.getMessage("appcom.button.cancel" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='env.form.title.categoryreg'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">

//함등록
	function fnc_categoryRegister() {

		 if( validateMandatory()){
            $.post("<%=webUri%>/app/env/admin/insertCategory.do", $("#frmCategory").serialize(), function(data){
            	if("OK" == data.result ) {
					// 다국어 저장 
            		registResource($("#categoryId").val());                  	  
    				alert("<spring:message code='env.form.msg.confirm.categoryinsok'/>");
                    opener.fnc_searchList();
                    window.close();
    			}else if(data.result != "OK" && data.result == "") {
    				 alert("<spring:message code='env.form.msg.fail'/>"); 
    				
    			}else{
    				alert(data.result);
    				
    			}	
            }, 'json');
		 }
	}
    
    //취소
    function fnc_cancel() {
    	window.close();
    }

</script>
</head>
<body>

<acube:outerFrame>
    <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td ><acube:titleBar><spring:message code='env.category.button.register'/></acube:titleBar></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td>
            <table height="100%" width="100%" style='' border='0' cellspacing='1'cellpadding='0' class="table_grow">
               <form name="frmCategory" id="frmCategory" onsubmit="return false;">
                <tr>
                    <td width="100" class="tb_tit_left"><spring:message code='env.form.categoryid'/><spring:message code='common.title.essentiality'/></td>
                    <td class="tb_left_bg">
                    <input class="input" type="text"  mandatory="Y" msg="<spring:message code='env.form.categoryid'/>" style="width:100%"  name="categoryId" id="categoryId" onkeyup="checkInputMaxLength(this,'',32)" value=""></input>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tb_tit_left"><spring:message code='env.form.categoryname'/><spring:message code='common.title.essentiality'/></td>
                    <td class="tb_left_bg">
                    <input class="input" type="text"  mandatory="Y" msg="<spring:message code='env.form.categoryname'/>" style="width:100%;ime-mode:active;"  name="categoryName" id="categoryName" onclick="showResource(this, 'CATEGORY', 'CATEGORY_NAME', '', '')" value="" readOnly></input>
                    </td>
                </tr>
                </form>
            </table>
            </td>
            <tr>
                <acube:space between="title_button" />
            </tr>
            <tr>
            <td>
            <acube:buttonGroup>
                <acube:button onclick="javascript:fnc_categoryRegister();" value="<%=btnOk%>" 
                    />
                <acube:space between="button" />
                <acube:button onclick="javascript:fnc_cancel();" value="<%=btnCancel%>" 
                     />
            </acube:buttonGroup>
            </td>
        </tr>
    </table>
 </acube:outerFrame>
</body>
</html>