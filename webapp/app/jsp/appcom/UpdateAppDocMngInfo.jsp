<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.Map"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : UpdateAppDocMngInfo.jsp 
 *  Description : 생산문서관리정보수정
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.14 
 *   수 정 자 : 윤동원 
 *   수정내용 :  
 * 
 *  @author  윤동원
 *  @since 2011. 4. 14 
 *  @version 1.0 
 */ 
%>
<%
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType); 
	String msgConfirm = messageSource.getMessage("env.option.msg.confirm.save" , null, langType);  
    
    
    logger.debug("#################################"+((Map)request.getAttribute("map")).get("readRange"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="appcom.docmnginfo.title.docmnginfo"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<style type="text/css">
	.range {display:inline-block;width:110px;height:30px; !important;}
	.h25 {display:inline-block;height:30px; valign:middle; !important;}
</style>
<script type="text/javascript">

	function updateMngInfo() {
		if (confirm("<%=msgConfirm%>")) {
			$.ajaxSetup({async:false});
			$.post("<%=webUri%>/app/appcom/enf/updateDocMngInfo.do", $("#frmMngInfo").serialize(), function(data){
					if("OK" == data.result){
						window.close();
					}
			}, 'json');
	    }

        
	}

</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="appcom.docmnginfo.title.docmnginfo"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="frmMngInfo" id="frmMngInfo">
		<c:set var="map" value="${map}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="appcom.docmnginfo.title.docmnginfo"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" cellspacing="1" class="td_table">
					<tr>
						<td   class="tb_tit">
                            <spring:message code="appcom.docmnginfo.subtitle.readrange"/>
                        </td>
                        <td width="80%"   class="tb_left">
                            <input  type="radio" value="DRS001" name="readRange" id="readRange" <c:if test="${map.readRange eq 'DRS001'}">checked</c:if> />
                            <spring:message code="approval.form.drs001"/>                            
                            <input  type="radio" value="DRS002" name="readRange" id="readRange" <c:if test="${map.readRange eq 'DRS002'}">checked</c:if> />
                            <spring:message code="approval.form.drs002"/>  
                            <input  type="radio" value="DRS003" name="readRange" id="readRange" <c:if test="${map.readRange eq 'DRS003'}">checked</c:if> />
                            <spring:message code="approval.form.drs003"/>   
                            <input  type="radio" value="DRS004" name="readRange" id="readRange" <c:if test="${map.readRange eq 'DRS004'}">checked</c:if> />
                            <spring:message code="approval.form.drs004"/> 
                            <input  type="radio" value="DRS005" name="readRange" id="readRange" <c:if test="${map.readRange eq 'DRS005'}">checked</c:if> />
                            <spring:message code="approval.form.drs005"/> 
                        </td>
                    </tr>
                    <tr>
                        <td class="tb_tit" >
							<spring:message code="appcom.docmnginfo.subtitle.pubboardyn"/>
                        </td>
                        <td width="80%"   class="tb_left">
                            <input type="radio"  value="Y" name="publicPost" id="publicPost" <c:if test="${map.publicPost eq 'Y'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.apply"/>
                            <input type="radio"  value="N" name="publicPost" id="publicPost" <c:if test="${map.publicPost eq ''}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.disapply"/>
                        </td>
                    </tr>
                    <!-- 
                    <tr>
                        <td  class="tb_tit" >
                            <spring:message code="appcom.docmnginfo.subtitle.audityn"/>
                        </td>
                        <td width="80%"   class="tb_left">
                            <input type="radio"  value="Y" name="auditYn" id="auditYn" <c:if test="${map.auditYn eq 'Y'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.apply"/>
                            <input type="radio"  value="N" name=auditYn id="auditYn" <c:if test="${map.auditYn eq 'N'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.disapply"/>
                        </td>
                    </tr>
                    --->
                    <tr>
                        <td class="tb_tit"  > 
                            <spring:message code="appcom.docmnginfo.subtitle.conservetype"/>
                        </td>
                        <td width="80%"    class="tb_left">
                            <input type="radio"  value="DRY001" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY001'}">checked</c:if> />
                             <spring:message code="appcom.docmnginfo.form.conserveType.dry001"/>
                            <input type="radio"  value="DRY002" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY002'}">checked</c:if> />
                             <spring:message code="appcom.docmnginfo.form.conserveType.dry002"/>
                            <input type="radio"  value="DRY003" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY003'}">checked</c:if> />
                             <spring:message code="appcom.docmnginfo.form.conserveType.dry003"/>
                            <input type="radio"  value="DRY004" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY004'}">checked</c:if> />
                             <spring:message code="appcom.docmnginfo.form.conserveType.dry004"/>
                            <input type="radio"  value="DRY005" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY005'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.conserveType.dry005"/>
                            <input type="radio"  value="DRY006" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY006'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.conserveType.dry006"/> 
                            <input type="radio"  value="DRY007" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY007'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.conserveType.dry007"/> 
                            <input type="radio"  value="DRY008" name="conserveType" id="conservetype" <c:if test="${map.conserveType eq 'DRY008'}">checked</c:if> />
                            <spring:message code="appcom.docmnginfo.form.conserveType.dry008"/> 
                        </td>
                    </tr>
				</table> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="javascript:updateMngInfo();" value="<%=buttonSave%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
        <input type="hidden" name="docId" id="docId" value="${map.docId}"/>
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>