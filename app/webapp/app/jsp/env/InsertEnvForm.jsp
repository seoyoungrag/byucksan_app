<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>

<%
    /** 
     *  Class Name  : InsertEnvForm.jsp
     *  Description : 서식 관리 등록
     *  Modification Information 
     * 
     *   수 정 일 : 2011.04.25 
     *   수 정 자 : 윤동원 
     *   수정내용 :  
     * 
     *  @author  윤동원
     *  @since 2011. 4. 25 
     *  @version 1.0 
     */
%>

<%
    String formname = messageSource.getMessage("env.form.formname", null, langType);//양식명
    String registdate = messageSource.getMessage("env.form.registdate", null, langType);//등록일자

    String categoryId = (String) request.getParameter("categoryId");		//양식구분
    String categoryName = (String) request.getParameter("categoryName");	//양식명

    String compId = (String) session.getAttribute("COMP_ID");
    String rolecode = (String) session.getAttribute("ROLE_CODES");
	// 기관코드   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = compId;
	}	
    String role11 = AppConfig.getProperty("role_doccharger", "", "role"); 		// 처리과
    String role12 = AppConfig.getProperty("role_cordoccharger", "", "role"); 	// 문서과
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); 		// 시스템관리자
	String relay_use = AppConfig.getProperty("relay_use", "", "relay"); 		// 문서유통 사용유무 
	
	String chargeType="";  //담당자 유형
	if(rolecode.contains(role12) ){
	    chargeType = "D";  //문서과담당자
	    if(rolecode.contains(role11)){
		      chargeType = "W";  //문서과 및 처리과 담당자
	    }
	} else if(rolecode.contains(role11)){
	    chargeType = "T";  // 처리과 담당자
    }
    
    String checked="";	//사용범위 체크
    String numberingYn="";
    
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt325 = appCode.getProperty("OPT325", "OPT325", "OPT"); // 이중결재사용여부
    String opt358 = appCode.getProperty("OPT358", "OPT358", "OPT"); // 채번사용여부
	opt325 = envOptionAPIService.selectOptionValue(compId, opt325);
    opt358 = envOptionAPIService.selectOptionValue(compId, opt358);
    String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
    
	// 권한map
	Map roleType = (Map)request.getAttribute("type"); 
    
    // 문서편집기 사용 값 조회
    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
    
    String editorTypeValues = envOptionAPIService.selectOptionText(compId, opt428);
    String[] editorType = editorTypeValues.split("\\^");
    int editorTypeCount = editorType.length;
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='env.form.title.formreg'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript">

    var gstrFormId="";
    var ext="";
    var doubleYn = "<%=opt325%>";

    $(document).ready(function() { initialize(); });
    $(window).unload(function() { closeChildWindow(); });
    
	function initialize() {

		initializeFileManager();

		$("#groupType:first").attr("checked","checked");  //첫번째 라디오버튼 체크표시   // jth8172 2012 신결재 TF
 
        //이중결재 사용여부체크
       // if(doubleYn =='N'){
       //     $("#chkdoubleYn").attr("disabled",true);
       //     $("#doubleYn").val("N");            
       // }else{
       //     $("#chkdoubleYn").attr("disabled",false);
        //}
        //공문서변환양식,감사문서양식 중 하나만 선택하도록 함. 
        //added by jkkim 20120725
        //$("#pubdocFormYn").click(function(){
        //		$("#auditFormYn").attr("checked","");
        //	}
        //);
        //$("#auditFormYn").click(function(){
    	//		$("#pubdocFormYn").attr("checked","");
    	//	}
   		 //);
  		 //end
	}
    
    // 찾기 (파일첨부)
	function attachFormFile() {
		// 파일양식 종류를 조회한다.
		var formType = $('input[name=formType]:checked').val();
		var fileFormType = "hwp";

		if (formType == "2") {
			fileFormType = "doc";
		} else if (formType == "3") {	
			fileFormType = "html";
		}
		
		var filelist = FileManager.uploadfile(fileFormType);

        if(filelist.length > 0) {
    		$("#filePath").val(filelist[0].localpath);
    		$("#formFileName").val(filelist[0].filename);
        }		
	}

    function formDuplicationCheck(){
       
    	if( validateMandatory()){
    		fnc_insertForm();
    	}
    }
  // 양식등록
    function fnc_insertForm(){

	   //submit
            $.post("<%=webUri%>/app/env/admincharge/insertEnvForm.do", $("#frmForm").serialize(), function(data){
                if("OK" == data.result ) {  
                    alert("<spring:message code='env.form.msg.formregok'/>");
                    opener.fnc_searchFromList();
                    window.close();
                } else {
                    alert("<spring:message code='env.form.msg.fail'/>["+data.result+"]");
                }   
            }, 'json');
        
    }

    //연계시스템조회
    var sysPopWin = null; 
    function openBizSystem(){
        
        //팝업
        sysPopWin = openWindow("sysPopWin", "<%=webUri%>/app/env/admincharge/listEnvBizSystemPop.do", 550, 400);
    }   

    //연계시스템삭제
    function deleteBizSystem(){
    	frmForm.bizSystemCode.value='';
        frmForm.bizSystemName.value='';
        frmForm.bizTypeCode.value='';
        frmForm.bizTypeName.value='';
    }   


    
    //연계시스템 set
    function setSystem(systemCode,systemName,bizTypeCode, bizTypeName){

    	frmForm.bizSystemCode.value=systemCode;
        frmForm.bizSystemName.value=systemName;
        frmForm.bizTypeCode.value=bizTypeCode;
        frmForm.bizTypeName.value=bizTypeName;
        return;
    } 

    //담당자 호출
    var treeDoc = null;
    function getbizinfolist(){

    	if(frmForm.doubleYn.value != 'Y'){
            alert("<spring:message code='env.form.msg.form.doubleselyn'/>");
            return;
    	} 
    	treeDoc = openWindow("appDoc","<%=webUri%>/app/common/OrgTree.do?type=4&treetype=3",650,320 );

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
        $("#chargerDeptName").val(obj.orgName+obj.deptName);
        //alert(obj.deptId);
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

    function closeChildWindow(){
        if(treeDoc != null){
        	treeDoc.close();
        }

        if(sysPopWin != null){
        	sysPopWin.close();
        }
    }
    
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<center>
    <form name="frmForm" id="frmForm"  onsubmit="return false;">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
    <tr>
        <td><span class="pop_title77"><spring:message code='env.form.title.formreg'/></span></td>
    </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>

<table border="0" cellspacing="0" cellpadding="0"  width="100%" class="td_table borderRB">
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.category", null, langType)%></td>
        <td class="tb_left_bg" width="78%" colspan="3"><%=categoryName%></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formrange", null, langType)%></td>
        <td class="tb_left_bg" width="78%" colspan="3" >
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <% // 회사 관리자는 회사양식, 문서과담당자는 기관양식, 처리과 담당자는 부서양식을 관리함   // jth8172 2012 신결재 TF
                   if((rolecode.indexOf(roleId10) >= 0 && "ADMIN".equals((String)roleType.get("roleType"))) ) {   // 관리자(회사) %>
                    <td class="td_left_bg tb_last" width="20">
                    <input type="radio"  name="groupType" id="groupType" value="<%=appCode.getProperty("GUT001", "GUT001", "GUT")%>" checked="true"></input></td>
                    <td class="tb_left" width="70"><%=messageSource.getMessage("env.form.formcorp", null, langType)%></td>
				<% } 
				  if(("CHARGE".equals((String)roleType.get("roleType")) && ("W".equals(chargeType) || "D".equals(chargeType)))){  // 문서과 문서책임자(부서) %>
                    <td class="td_left_bg tb_last" width="20">
                       <input type="radio"  name="groupType" id="groupType" value="<%=appCode.getProperty("GUT002", "GUT002", "GUT")%>"   <%=checked%>></input></td>
                    <td class="tb_left tb_last tr_last" width="70"><%=messageSource.getMessage("env.form.forminstitution", null, langType)%></td>
				<% } 
				  if(("CHARGE".equals((String)roleType.get("roleType")) && ("W".equals(chargeType) || "T".equals(chargeType)))){  // 처리과문서책임자(부서) %>
                    <td class="td_left_bg tb_last" width="20">
                       <input type="radio"  name="groupType" id="groupType" value="<%=appCode.getProperty("GUT003", "GUT003", "GUT")%>"   <%=checked%>></input></td>
                    <td class="tb_left tb_last tr_last" width="70"><%=messageSource.getMessage("env.form.formdept", null, langType)%></td>
                <%  } %>
                    <td class="tb_left tb_last tr_last"></td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formname", null, langType)%><spring:message code='common.title.essentiality'/></td>
        <td class="tb_left_bg" width="78%" colspan="3">

        <%
        //문서유통기능 사용시, 공문서변환양식 기능 사용
         String sWidth = "";
        if("Y".equals(relay_use)){
            sWidth = "70%";
            if("Y".equals(opt380)){
                sWidth = "45%";
             }
        %>
        <input type="text" mandatory="Y" msg="<%=messageSource.getMessage("env.form.formname", null, langType)%>" class="input" name="formName" id="formName" onkeyup="checkInputMaxLength(this,'',128)" value="" style="width:<%=sWidth%>;ime-mode:active;"  />
        &nbsp;&nbsp;<input type="checkbox"  name="pubdocFormYn" id="pubdocFormYn" value="Y" onclick="javascript:setChecked(this,frmForm.pubdocFormYn)"></input>&nbsp;&nbsp;<spring:message code='env.form.pubdoc'/>
    	<%}else{
    	    sWidth = "89%";
    		if("Y".equals(opt380)){
                sWidth = "73%";
            }
    	%>
    	<input type="text" mandatory="Y" msg="<%=messageSource.getMessage("env.form.formname", null, langType)%>" class="input" name="formName" id="formName" onkeyup="checkInputMaxLength(this,'',128)" value="" style="width:<%=sWidth%>;ime-mode:active;"  />
        &nbsp;&nbsp;
    	<%} %>
    	
       <% 
        if("Y".equals(opt380)){
         %>
            <input type="checkbox"  name="auditFormYn" id="auditFormYn" value="Y" onclick="javascript:setChecked(this,frmForm.auditFormYn)"></input>&nbsp;&nbsp;<spring:message code='env.form.auditdoc' />
        <% 
        }
        %>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formfile", null, langType)%><spring:message code='common.title.essentiality'/></td>
        <td width="78%" colspan="3">
            <table border="0" cellspacing="0" cellpadding="0" width="100%" >
            <tr>
	            <td width="90%" class="tb_left_bg tr_last">
	                <input type="text" name="filePath"  mandatory="Y" msg="<%=messageSource.getMessage("env.form.formfile", null, langType)%>" style="width: 100%;" id="filePath" value="" class="input" readonly="true" /> 
	            </td>
	            <td class="tb_left_bg">
	                    <acube:buttonGroup>
	                    <acube:button  type="4" onclick="javascript:attachFormFile();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
	                    </acube:buttonGroup>
	            </td>
            </tr>
            
            <!-- 양식파일에 HWP, DOC, HTML 선택 조건 추가 -->
            <tr>
             	<td class="tb_left" width="78%" colspan="2" >
<%
	String editorChecked = "";
	for (int i = 0; i < editorTypeCount; i++) {
		String editorTypeValue = editorType[i];
		
		if ("".equals(editorChecked)) {
			if (editorTypeValue.indexOf("Y") >= 0) {
				editorChecked = "checked";	
			}
		}
		
		// HTML일경우 양식기안에서는 사용하지 못하도록 수정.
		if ("I3:Y".equals(editorTypeValue)) {
			editorTypeValue = "I3:N";	
		}
%>             	
 
 <%
 		if ("I1:Y".equals(editorTypeValue)) {
 %>					<input type="radio" name="formType" id="formType" value="1" <%= editorChecked %> /><spring:message code="env.option.subtitle.OPT428_1"/>
 <%
 		} else if ("I2:Y".equals(editorTypeValue)) {
 %>
					<input type="radio" name="formType" id="formType" value="2" <%= editorChecked %> /><spring:message code="env.option.subtitle.OPT428_2"/>
<%
 		} else if ("I3:Y".equals(editorTypeValue)) {
%>					
					<input type="radio" name="formType" id="formType" value="3" <%= editorChecked %> /><spring:message code="env.option.subtitle.OPT428_3"/>
<%
 		}
 
 		if ("checked".equals(editorChecked)) {
 			editorChecked = " ";
 		}
	}
%>					
           		</td>
            </tr>            
            
            </table>                
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formorder", null, langType)%><spring:message code='common.title.essentiality'/></td>
        <td class="tb_left_bg" width="78%" colspan="3"><input type="text" mandatory="Y" msg="<%=messageSource.getMessage("env.form.formorder", null, langType)%>" class="input" name="formOrder" id="formdporder" onkeyup="checkInputMaxLength(this,'',3)" value="" style="width: 100%;" maxlength="3" style="ime-mode: disabled;" onkeypress="return onlyNumber(event, false);"></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.formnumberingyn", null, langType)%></td>
        <td class="tb_left_bg" width="78%" colspan="3">
       
        <% 
        if("1".equals(opt358)){ 
            numberingYn = "disabled";
        }
        %>
        
        <input type="checkbox"   name="chknumberingYn" value="Y" onclick="javascript:setChecked(this,frmForm.numberingYn)" checked="true" <%=numberingYn %>></input>
        <input type="hidden"   name="numberingYn" value="Y" ></input>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.doubleyn", null, langType)%></td>
        <td class="tb_left_bg" width="15%">
        <input type="checkbox"  name="chkdoubleYn" id="chkdoubleYn" value="Y" onclick="javascript:setChecked(this,frmForm.doubleYn)"></input>
        <input type="hidden"  name="doubleYn" id="doubleYn" value="N" ></input>
        </td>
        <td class="tb_tit_left" width="15%"><%=messageSource.getMessage("env.form.formbizinfo", null, langType)%></td>
        <td width="100%">
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" >
	            <tr>
    	            <td width="65%" class="tb_left_bg tr_last">
    	                <input type="text" name="formbizname" id="formbizname" value="" style="width: 100%;" class="input" readonly="true" />
    	                <input type="hidden" name="chargerId" id="chargerId" value="" />
    	                <input type="hidden" name="chargerName" id="chargerName" value="" />
    	                <input type="hidden" name="chargerPos" id="chargerPos" value="" />
    	                <input type="hidden" name="chargerDeptId" id="chargerDeptId" value="" />
    	                <input type="hidden" name="chargerDeptName" id="chargerDeptName" value="" />
    	            </td>
    	            <td class="tb_left_bg">
    	            <% //if(opt325.equals("Y")){ %>
    	                    <acube:buttonGroup>
        	                    <acube:button type="4" onclick="javascript:getbizinfolist();return(false);" value='<%=messageSource.getMessage("env.form.formsearch",null,langType)%>' />
                                <acube:space between="button" />
                                <acube:button type="4" onclick="javascript:deleteCharge();return(false);" value='<%=messageSource.getMessage("appcom.button.delete",null,langType)%>' />
                            </acube:buttonGroup>
    	            <%//}%>                    
    	            </td>
	            </tr>
	        </table>        
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.editbodyyn", null, langType)%></td>
        <td class="tb_left_bg" width="15%">
        <input type="checkbox"  name="chkeditbodyYn" value="Y" onclick="javascript:setChecked(this,frmForm.editbodyYn)" checked="true"></input>
        <input type="hidden"  name="editbodyYn" value="Y" checked="true"></input>
        </td>
        <td class="tb_tit_left" width="15%"><%=messageSource.getMessage("env.form.editfileyn", null, langType)%></td>
        <td class="tb_left_bg" width="*">
        <input type="checkbox"  name="chkeditfileYn" value="Y" onclick="javascript:setChecked(this,frmForm.editfileYn)" checked="true"></input>
        <input type="hidden"  name="editfileYn" value="Y" checked="true"></input>
        </td>
    </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%" class="td_table borderRB" >
    <tr>
        <td class="tb_tit_left" width="22%"><%=messageSource.getMessage("env.form.bizsystemid", null, langType)%></td>
        <td >
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                <td width="76%" class="tb_left_bg tr_last">
                    <input type="text" class="input_read" id="bizSystemName" name="bizSystemName" value="" style="width: 98%" readonly="true" />
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
	</tr>
    <tr>
        <td class="tb_tit_left" width="22%" ><%=messageSource.getMessage("env.form.bizsystemtype", null, langType)%></td>
        <td class="tb_left_bg" colspan="2">
        <input type="text" class="input_read" id="bizTypeName" name="bizTypeName" value="" style="width: 100%" readonly="true" /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%" ><%=messageSource.getMessage("env.form.remark", null, langType)%></td>
        <td class="tb_left_bg" width="78%" colspan="2"><textarea name="remark" rows="5" style="width: 100%;" onkeyup="checkInputMaxLength(this,'',2000)"></textarea></td>
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
                <td >
                    <acube:buttonGroup>
                    <acube:button  onclick="javascript:formDuplicationCheck();return(false);" value='<%=messageSource.getMessage("env.form.ok",null,langType)%>' />
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

<input type="hidden" name="categoryId" value="<%=categoryId%>"> 
<input type="hidden"  id="bizSystemCode" name="bizSystemCode" value="" style="width: 100%"  readonly="true" />
<input type="hidden"  id="bizTypeCode" name="bizTypeCode" value="" style="width: 100%"  readonly="true" />
<input type="hidden"  name="formFileName" id="formFileName" value="" /> 
<input type="hidden"  name="institutionId" id="institutionId" value="<%=institutionId%>" /> 

</form>
</center>
<!--	<script src="./htdocs/js/object/cn_filetransferctrl.js"></script> -->

</body>
<iframe name="insertformaction" src="" width="100%" height="0"></iframe>
<iframe id="hidframe" name="hidframe" src="" width="100%" height="0"></iframe>
</html>