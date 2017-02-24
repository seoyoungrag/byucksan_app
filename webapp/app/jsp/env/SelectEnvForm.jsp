<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List,java.lang.String"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil,
                 org.anyframe.util.StringUtil"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectEnvForm.jsp
 *  Description : 서식 관리조회
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.04 
 *   수 정 자 : 윤동원 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  윤동원
 *  @since 2011. 5. 03 
 *  @version 1.0 
 */ 
%>

<%
    String formname = messageSource.getMessage("env.form.formname" , null, langType);//양식명
    String registdate = messageSource.getMessage("env.form.registdate" , null, langType);//등록일자
   // String formtype = messageSource.getMessage("env.form.formtype" , null, langType);//양식구분
    
    String rolecode = (String) session.getAttribute("ROLE_CODES");
   	String compId = (String) session.getAttribute("COMP_ID"); 
    String categoryName = StringUtil.null2str((String)request.getAttribute("categoryName"));
    
    FormVO formVO = (FormVO)request.getAttribute("formVO");
    String checked="";   
    String corp = messageSource.getMessage("env.form.formcorp", null, langType);
    String inst = messageSource.getMessage("env.form.forminstitution", null, langType);   // jth8172 2012 신결재 TF
    String dept = messageSource.getMessage("env.form.formdept", null, langType);
 
    // jth8172 2012 신결재 TF
    String groupType = corp;
    if("GUT002".equals(formVO.getGroupType())) {
    	 groupType = inst;
    } else if("GUT003".equals(formVO.getGroupType())) {
    	 groupType = dept;
    }

    // 현재 선택한 파일의 확장자를 구한다.
    String strBodyType = CommonUtil.getFileExtentsion(formVO.getFormFileName());   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.form.title.formsel" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
</script>
</head>

<body  scrolling="auto" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" TitleChange="javascript:change()">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<tr>
        <td><span class="pop_title77"><spring:message code='env.form.title.formsel'/></span>
		</td>
    </tr>
</table>


<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="td_table">
    <form name="frmForm" id="frmForm" >
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.category", null, langType)%></td>
        <td class="tb_left_bg" colspan="3"><%=categoryName%></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.formrange", null, langType)%></td>
        <td class="tb_left_bg" width="75%" colspan="3" >
        <%= groupType %>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="20%"><%=messageSource.getMessage("env.form.formname",null,langType)%></td>
        <td class="tb_left_bg" colspan="3"><%= formVO.getFormName() %></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.formfile", null, langType)%></td>
        <td  width="80%" colspan="3" class="tb_left_bg" ><%=formVO.getFormName()+"."+strBodyType%></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.formorder",null,langType)%></td>
        <td class="tb_left_bg" colspan="3"><%=formVO.getFormOrder() %> </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.formnumberingyn", null, langType)%></td>
        <td class="tb_left_bg" width="27%"><input type="checkbox"  name="numberingYn" value="Y" <%= ("Y".equals(formVO.getNumberingYn())) ? "checked" :"" %>  disabled></input></td>
        <td class="tb_tit_left" ><%=messageSource.getMessage("env.form.doubleyn", null, langType)%></td>
        <td class="tb_left_bg"><input type="checkbox"  name="doubleYn" value="Y" <%= ("Y".equals(formVO.getDoubleYn())) ? "checked" :"" %> disabled></input></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.formbizinfo", null, langType)%></td>
        <td class="tb_left_bg" colspan="3" width="100%">
            <%= formVO.getChargerName()%> <%= "".equals(formVO.getChargerName()) ? formVO.getChargerDeptName():""  %>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.editbodyyn", null, langType)%></td>
        <td class="tb_left_bg" width="27"><input type="checkbox"  name="editbodyYn" value="Y" <%= ("Y".equals(formVO.getEditbodyYn())) ? "checked" :"" %> disabled></input></td>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.editfileyn", null, langType)%></td>
        <td class="tb_left_bg" width="27"><input type="checkbox"  name="editfileYn" value="Y" <%= ("Y".equals(formVO.getEditfileYn())) ? "checked" :"" %> disabled></input></td>
    </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<table cellspacing="1" cellpadding="0" width="100%" class="td_table" >
   
   <% if(formVO.getBizSystemName() != null && !"".equals(formVO.getBizSystemName())){ %>
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.bizsystemid", null, langType)%></td>
        <td width="76%" class="tb_left_bg">
            <%=formVO.getBizSystemName() %>
        </td>
    <tr>
        <td class="tb_tit_left" width="23%" ><%=messageSource.getMessage("env.form.bizsystemtype", null, langType)%></td>
        <td class="tb_left_bg" colspan="2">
        <%=formVO.getBizTypeName() %></td>
    </tr>
    <%}%>
    <tr>
        <td class="tb_tit_left" width="23%" ><%=messageSource.getMessage("env.form.remark", null, langType)%></td>
        <td class="tb_left_bg" width="80%" colspan="2"><textarea  name="remark" rows="5" style="width: 100%;" onkeyup="checkInputMaxLength(this,'',2000)" disabled><%=formVO.getRemark() %></textarea></td>
    </tr>
</table>



<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td height="10"></td>
    </tr>
    <tr>
        <td>
        <table align="right" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td width="4" nowrap="1"></td>
                <acube:buttonGroup>
                <acube:button  onclick="javascript:window.close();return(false);" value='<%=messageSource.getMessage("env.form.close",null,langType)%>' />
                </acube:buttonGroup>
                </td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td height="2"></td>
    </tr>
</table>
</acube:outerFrame>
<input type="hidden" name="categoryId" value="<%=formVO.getCategoryId()%>"> 
<input type="hidden" name="formId" value="<%=formVO.getFormId()%>"> 
</form>
</center>
<!--	<script src="./htdocs/js/object/cn_filetransferctrl.js"></script> -->

</body>

</html>