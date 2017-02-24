<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List,java.lang.String,java.util.Map"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil,
                 org.anyframe.util.StringUtil"%>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%
/** 
 *  Class Name  : UpdateEnvForm.jsp
 *  Description : 서식 관리 수정
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.03 
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
    
	String compId = (String) session.getAttribute("COMP_ID");
	String rolecode = (String) session.getAttribute("ROLE_CODES");
    String categoryName = StringUtil.null2str((String)request.getAttribute("categoryName"));
    
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
  
    String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과
    String role12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과
    String relay_use = AppConfig.getProperty("relay_use", "", "relay"); // 문서유통 사용유무 

    // 기관코드   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = compId;
	}	
   
    // jth8172 2012 신결재 TF
	String chargeType="";  //담당자 유형
	if(rolecode.contains(role12) ){
	    chargeType = "D";  //문서과담당자
	    if(rolecode.contains(role11)){
		      chargeType = "W";  //문서과 및 처리과 담당자
	    }
	} else if(rolecode.contains(role11)){
	    chargeType = "T";  // 처리과 담당자
    }
    
    FormVO formVO = (FormVO)request.getAttribute("formVO");
    String checked="";
    
    // 권한map
    Map roleType = (Map)request.getAttribute("type"); 
    
   IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
   String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
   opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
   

   // 현재 선택한 파일의 확장자를 구한다.
   String strBodyType = CommonUtil.getFileExtentsion(formVO.getFormFileName());   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.form.title.formupd" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />

<script type="text/javascript">


    var gstrFormId="";

    $(document).ready(function() { initialize(); });

    //초기화
	function initialize() {
		initializeFileManager();
	}

    
    //파일첨부
	function attachFormFile() {

		var filelist = FileManager.uploadfile("<%= strBodyType %>");
    
        if(filelist.length > 0){
    		$("#filePath").val(filelist[0].localpath);
    		$("#formFileName").val(filelist[0].filename);
    	}
    }

    //양식수정
    function fnc_updateForm(){

        if( validateMandatory(document.frmForm)){
            //submit
            $.post("<%=webUri%>/app/env/admincharge/updateEnvForm.do", $("#frmForm").serialize(), function(data){
                if("OK" == data.result ) {  
                    alert("<spring:message code='env.form.msg.confirm.formupdok'/>");
                    
                    opener.fnc_searchFromList();
                    window.close();
                    
                } else {
                    alert("<spring:message code='env.form.msg.fail'/>["+data.result+"]");
                }   
            }, 'json');
        }
    }
    
    //연계시스템조회
    function openBizSystem(){
        
        //팝업
        var top = (screen.availHeight - 250) / 2;
        var left = (screen.availWidth - 400) / 2;
        sysPopWin = window.open("<%=webUri%>/app/env/admincharge/listEnvBizSystemPop.do", "sysWin",
                "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=550,height=390," +
                "scrollbars=no,resizable=no"); 
    }  
    
    //연계시스템 set
    function setSystem(systemCode,systemName,bizTypeCode, bizTypeName){

        frmForm.bizSystemCode.value=systemCode;
        frmForm.bizSystemName.value=systemName;
        frmForm.bizTypeCode.value=bizTypeCode;
        frmForm.bizTypeName.value=bizTypeName;
        return;
    } 

    //연계시스템삭제
    function deleteBizSystem(){
    	frmForm.bizSystemCode.value='';
        frmForm.bizSystemName.value='';
        frmForm.bizTypeCode.value='';
        frmForm.bizTypeName.value='';
    } 
    
    //담당자 호출
    function getbizinfolist(){
//alert($("#chkdoubleYn").val());
    	if(frmForm.doubleYn.value != 'Y'){
            alert("<spring:message code='env.form.msg.form.doubleselyn'/>");
            return;
    	} 
        
         var appDoc = openWindow("appDoc","<%=webUri%>/app/common/OrgTree.do?type=4&treetype=3",600,320 );
        
        //return;
    }

    
    //담당자삭제
    function deleteCharge(){
         $("#chargerId").val("");
         $("#chargerName").val("");
         $("#chargerPos").val("");
         $("#chargerDeptId").val("");
         $("#chargerDeptName").val("");
         //alert(obj.deptId);
         $("#formbizname").val("");
    }  

    function setUserInfo(obj){
        $("#chargerId").val(obj.userId);
        $("#chargerName").val(obj.userName);
        $("#chargerPos").val(obj.positionName);
        $("#chargerDeptId").val(obj.orgId+obj.deptId);
        $("#chargerDeptName").val(obj.orgName + obj.deptName);
        $("#formbizname").val(obj.orgName + obj.userName);
    }

    
    //set checked
    function setChecked(obj,setObj){
        if(obj.checked==true){
        	setObj.value="Y";
        }
        else{
        	setObj.value="N";
        }
    }
 
</script>
</head>

<body scrolling="auto" leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
    <tr>
        <td><span class="pop_title77"><spring:message code='env.form.title.formupd'/></span></td>
    </tr>
</table>
<center>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>

<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="table_grow">
    <form name="frmForm" id="frmForm"  onsubmit="return false;">
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.category", null, langType)%></td>
        <td class="tb_left_bg" colspan="3"><%=categoryName%></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formrange", null, langType)%></td>
        <td class="td_bg" width="75%" colspan="3" >
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
                <tr>
                <% // 회사 관리자는 회사양식, 문서과담당자는 기관양식, 처리과 담당자는 부서양식을 관리함   // jth8172 2012 신결재 TF
                   if((rolecode.indexOf(roleId10) >= 0 && "ADMIN".equals((String)roleType.get("roleType"))) ) {   // 관리자(회사) %>
                    <td class="td_bg" width="20">
                    <input type="radio"  name="groupType" value="<%=appCode.getProperty("GUT001", "GUT001", "GUT")%>" <%= ("GUT001".equals(formVO.getGroupType())) ? "checked" :"" %>></input></td>
                    <td class="tb_left" width="70"><%=messageSource.getMessage("env.form.formcorp", null, langType)%></td>
				<% } 
				  if(("CHARGE".equals((String)roleType.get("roleType")) && ("W".equals(chargeType) || "D".equals(chargeType)))){  // 문서과 문서책임자(부서) %>
                    <td class="td_bg" width="20">
                       <input type="radio"  name="groupType" value="<%=appCode.getProperty("GUT002", "GUT002", "GUT")%>"   <%= ("GUT002".equals(formVO.getGroupType())) ? "checked" :"" %> ></input></td>
                    <td class="tb_left"><%=messageSource.getMessage("env.form.forminstitution", null, langType)%></td>
				<% } 
				  if(("CHARGE".equals((String)roleType.get("roleType")) && ("W".equals(chargeType) || "T".equals(chargeType)))){  // 처리과문서책임자(부서) %>
                    <td class="td_bg" width="20">
                       <input type="radio"  name="groupType" value="<%=appCode.getProperty("GUT003", "GUT003", "GUT")%>"   <%= ("GUT003".equals(formVO.getGroupType())) ? "checked" :"" %> ></input></td>
                    <td class="tb_left"><%=messageSource.getMessage("env.form.formdept", null, langType)%></td>
                <%  } %>
 					<td class="tb_left"></td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formname",null,langType)%><spring:message code='common.title.essentiality'/></td>
        <td class="tb_left_bg" colspan="3">
        <%
        //문서유통기능 사용시, 공문서변환양식 기능 사용
        String sWidth = "";
        if("Y".equals(relay_use)){
             sWidth = "70%";
            if("Y".equals(opt380)){
                sWidth = "45%";
             }
        %>
           <input type="text" class="input" name="formName" onkeyup="checkInputMaxLength(this,'',128)" value="<%=formVO.getFormName() %>" style="width:<%=sWidth%>;ime-mode:active;" />
           &nbsp;&nbsp;<input type="checkbox"  name="pubdocformYn" id="pubdocformYn" value="Y" onclick="javascript:setChecked(this,frmForm.pubdocformYn)" <%=("Y".equals(formVO.getPubdocformYn())) ? "checked" : "" %>></input>&nbsp;&nbsp;<spring:message code='env.form.pubdoc'/>
        <%}else{
        	sWidth = "89%";
    		if("Y".equals(opt380)){
                sWidth = "73%";
            }
        %>
           <input type="text" class="input" name="formName" onkeyup="checkInputMaxLength(this,'',128)" value="<%=formVO.getFormName() %>" style="width:<%=sWidth%>;ime-mode:active;" />
          &nbsp;&nbsp;
        <%} %>
        <% 
        if("Y".equals(opt380)){
         %>
            <input type="checkbox"  name="auditFormYn" id="auditFormYn" value="Y" onclick="javascript:setChecked(this,frmForm.auditFormYn)" <%=("Y".equals(formVO.getAuditformYn())) ? "checked" : "" %>></input>&nbsp;&nbsp;<spring:message code='env.form.auditdoc' />
        <% 
        }
        %>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formfile", null, langType)%><spring:message code='common.title.essentiality'/></td>
        <td  width="80%" colspan="3">
            <table border="0" cellspacing="0" cellpadding="0" width="100%" >
            <tr>
            <td width="90%" class="tb_left_bg">
                <input type="text" name="filePath"  mandatory="Y" msg="<%=messageSource.getMessage("env.form.formfile", null, langType)%>" style="width: 100%;" id="filePath" value="<%= formVO.getFormName() + "." + strBodyType %>" class="input" readonly="true" /> 
            </td>
            <td class="tb_left_bg">
                    <acube:buttonGroup>
                     <acube:button  type="4" onclick="javascript:attachFormFile();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
                    </acube:buttonGroup>
            </td>
            </tr>
            </table>                
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formorder",null,langType)%><spring:message code='common.title.essentiality'/></td>
        <td class="tb_left_bg" colspan="3"><input type="text" class="input" name="formOrder" onkeyup="checkInputMaxLength(this,'',3)" id="formOrder" value="<%=formVO.getFormOrder() %>" style="width: 100%;" maxlength="3" style="ime-mode: disabled;" onkeypress="return onlyNumber(event, false);"></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formnumberingyn", null, langType)%></td>
        <td class="tb_left_bg" colspan="3" width="27%">
        <input type="checkbox"  name="chknumberingYn" value="Y" onclick="javascript:setChecked(this,frmForm.numberingYn)" <%= ("Y".equals(formVO.getNumberingYn())) ? "checked" :"" %>></input>
        <input type="hidden"   name="numberingYn" value="<%=formVO.getNumberingYn() %>" ></input>
        </td>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.doubleyn", null, langType)%></td>
        <td class="tb_left_bg" width="18%">
        <input type="checkbox"  id="chkdoubleYn" name="chkdoubleYn" value="Y" onclick="javascript:setChecked(this,frmForm.doubleYn)" <%= ("Y".equals(formVO.getDoubleYn())) ? "checked" :"" %>></input>
        <input type="hidden"  name="doubleYn" value="<%=formVO.getDoubleYn() %>" ></input>
        </td>
        <td class="tb_tit_left" width="15%"><%=messageSource.getMessage("env.form.formbizinfo", null, langType)%></td>
        <td width="100%">
        <table border="0" cellspacing="0" cellpadding="0" width="100%" >
            <tr>
                <td width="65%" class="tb_left_bg">
                    <input type="text" name="formbizname" id="formbizname" value="<%= formVO.getChargerName() %><%= "".equals(formVO.getChargerName()) ? formVO.getChargerDeptName():""%>" style="width: 100%;" class="input" readonly="true" />
                    <input type="hidden" name="chargerId" id="chargerId" value="<%=formVO.getChargerId() %>" />
                    <input type="hidden" name="chargerName" id="chargerName" value="<%=formVO.getChargerName() %>" />
                    <input type="hidden" name="chargerPos" id="chargerPos" value="<%=formVO.getChargerPos() %>" />
                    <input type="hidden" name="chargerDeptId" id="chargerDeptId" value="<%=formVO.getChargerDeptId() %>" />
                    <input type="hidden" name="chargerDeptName" id="chargerDeptName" value="<%=formVO.getChargerDeptName() %>" />
                </td>
                <td class="tb_left_bg">
                        <acube:buttonGroup>
                        <acube:button  type="4" onclick="javascript:getbizinfolist();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
                        <acube:space between="button" />
                        <acube:button type="4" onclick="javascript:deleteCharge();return(false);" value='<%=messageSource.getMessage("appcom.button.delete",null,langType)%>' />                    
                        </acube:buttonGroup>
                </td>
            </tr>
        </table>       
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.editbodyyn", null, langType)%></td>
        <td class="tb_left_bg" width="15%">
        <input type="checkbox"  name="chkeditbodyYn" value="Y"  onclick="javascript:setChecked(this,frmForm.editbodyYn)"<%= ("Y".equals(formVO.getEditbodyYn())) ? "checked" :"" %>></input>
        <input type="hidden"  name="editbodyYn" value="<%=formVO.getEditbodyYn() %>" checked="true"></input>
        </td>
        <td class="tb_tit_left" width="15%"><%=messageSource.getMessage("env.form.editfileyn", null, langType)%></td>
        <td class="tb_left_bg" width="*">
        <input type="checkbox"  name="chkeditfileYn" value="Y" onclick="javascript:setChecked(this,frmForm.editfileYn)" <%= ("Y".equals(formVO.getEditfileYn())) ? "checked" :"" %>></input>
        <input type="hidden"  name="editfileYn" value="<%=formVO.getEditfileYn() %>" ></input>
        </td>
    </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<table cellspacing="1" cellpadding="0" width="100%" class="table_grow" >
    <tr>
        <td class="tb_tit_left" width="23%"><%=messageSource.getMessage("env.form.bizsystemid", null, langType)%></td>
        <td >
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                <td width="76%" class="tb_left_bg">
                    <input type="text" class="input_read" id="bizSystemName" name="bizSystemName" value="<%=formVO.getBizSystemName() %>" style="width: 98%" readonly="true" />
                </td>
                <td class="tb_left_bg" width="30%" >
                    <acube:buttonGroup>
                    <acube:button type="4" onclick="javascript:openBizSystem();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
                    <acube:space between="button" />
                    <acube:button type="4" onclick="javascript:deleteBizSystem();return(false);" value='<%=messageSource.getMessage("appcom.button.delete",null,langType)%>' />
                    </acube:buttonGroup>
               </td>
               </tr>
           </table>
       </td>
    <tr>
        <td class="tb_tit_left" width="23%" ><%=messageSource.getMessage("env.form.bizsystemtype", null, langType)%></td>
        <td class="tb_left_bg" colspan="2">
        <input type="text" class="input_read" id="bizTypeName" name="bizTypeName" value="<%=formVO.getBizTypeName() %>" style="width: 100%" readonly="true" /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="23%" ><%=messageSource.getMessage("env.form.remark", null, langType)%></td>
        <td class="tb_left_bg" width="80%" colspan="2"><textarea name="remark" rows="5" class="input" style="width: 100%;height:50;" onkeyup="checkInputMaxLength(this,'',2000)"><%=formVO.getRemark() %></textarea></td>
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
                <td background="./image/2.0/bu2_bg.gif" class="text_left">
                <acube:buttonGroup>
                <acube:button  onclick="javascript:fnc_updateForm();return(false);" value='<%=messageSource.getMessage("env.form.ok",null,langType)%>' />
                <acube:space between="button" />
                <acube:button  onclick="javascript:window.close();return(false);" value='<%=messageSource.getMessage("env.form.cancel",null,langType)%>' />
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
<input type="hidden"  id="bizSystemCode" name="bizSystemCode" value="<%=formVO.getBizSystemCode()%>" style="width: 100%"  readonly="true" />
<input type="hidden"  id="bizTypeCode" name="bizTypeCode" value="<%=formVO.getBizTypeCode() %>" style="width: 100%"  readonly="true" />
<input type="hidden" class="input_read" name="formFileName" id="formFileName" value="<%=formVO.getFormFileName() %>"  readOnly="true" /> 
<input type="hidden" class="input_read" name="formFileId" id="formFileId" value="<%=formVO.getFormFileId() %>"  readOnly="true" /> 
<input type="hidden"  name="institutionId" id="institutionId" value="<%=institutionId%>" /> 
<input type="hidden"  name="formType" id="formType" value="<%=formVO.getFormType()%>" />

</form>
</center>
</body>
</html>