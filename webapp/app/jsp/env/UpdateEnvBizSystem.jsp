<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale,java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.BizSystemVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>


<%
    /** 
     *  Class Name  : UpdateEnvBizSystem.jsp
     *  Description : 연계 시스템 수정
     *  Modification Information 
     * 
     *   수 정 일 : 2011.05.13 
     *   수 정 자 : 윤동원 
     *   수정내용 : KDB 요건반영 
     * 
     *  @author  윤동원
     *  @since 2011. 5. 13 
     *  @version 1.0 
     */
%>

<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String rolecode = (String) session.getAttribute("ROLE_CODES");
	String docCategory = messageSource.getMessage("env.bizsystem.doc.category" , null, langType);

    BizSystemVO bizSysVO = (BizSystemVO)request.getAttribute("bizSysVO");

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
    String opt358 = appCode.getProperty("OPT358", "OPT358", "OPT"); // 채번사용여부
    opt358 = envOptionAPIService.selectOptionValue(compId, opt358);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='env.bizsystem.title.upd'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript">


    //등록
    function fnc_updateForm(){

       if( validateMandatory()){
            
            //submit
            $.post("<%=webUri%>/app/env/admincharge/updateEnvBizSystem.do", $("#frmForm").serialize(), function(data){
                if("OK" == data.result ) {  
                    
                    alert("<spring:message code='env.bizsystem.msg.bizsystemupdok'/>");
                    
                    opener.fnc_selectForm();
                    window.close();
                    
                } else {
                    alert("<spring:message code='env.form.msg.fail'/>");
                }   
            }, 'json');
        }
    }

	//set checked
	function setChecked(obj, type){
	    if (obj.checked==true) {
	        if (type == 1) {
	    		frmForm.lastAckYn.value = "Y";
	        } else if (type == 2) {
	            var useNumbering = $("#useNumbering").val();
	            if (useNumbering == "N") {
	                $("#unitId").val("");
	                $("#unitName").val("");
	                $("#useNumbering").val("");
	            }
				frmForm.numberingYn.value = "Y";
	        } else if (type == 3) {
				frmForm.overlapYn.value = "Y";
	        }
	    } else {
	        if (type == 1) {
	        	frmForm.lastAckYn.value = "N";
	        } else if (type == 2) {
	            var useNumbering = $("#useNumbering").val();
	            if (useNumbering == "Y") {
	                $("#unitId").val("");
	                $("#unitName").val("");
	                $("#useNumbering").val("");
	            }
	    		frmForm.numberingYn.value = "N";
	        } else if (type == 3) {
				frmForm.overlapYn.value = "N";
	        }
	    }
	}

	// 단위업무 선택창 띄우기
    function selectUnit() {
    	openWindow('select_unit_win', '<%=webUri%>/app/bind/unit/select.do', 350, 400, 'no');
    }

	 // 단위업무 선택 시 호출 함수
    function setUnit(unit) {
        $("#unitId").val(unit.unitId);
        $("#unitName").val(unit.unitName);
        $("#useNumbering").val(unit.serialNumber);
        if (unit.serialNumber == "Y") {
        	frmForm.chk_numberingYn.checked = true;
        	frmForm.numberingYn.value = "Y";
        } else {
        	frmForm.chk_numberingYn.checked = false;
        	frmForm.numberingYn.value = "N";
        }
    }
</script>
</head>

<body  scrolling="auto" leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td><acube:titleBar><spring:message code='env.bizsystem.title.upd'/></acube:titleBar></td>
    </tr>
</table>
<center>
<form name="frmForm" id="frmForm"  onsubmit="return false;">
<table border="0" cellspacing="1" cellpadding="0" width="100%" class="table_grow">
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.name'/></td>
        <td class="tb_left_bg" colspan="3"><input width="100%" type="text"  mandatory="Y" msg="<spring:message code='env.bizsystem.name'/>" class="input" name="bizSystemName"  value="<%=EscapeUtil.escapeHtmlTag(bizSysVO.getBizSystemName())%>" onkeyup="checkInputMaxLength(this,'',128)" style="width: 100%;" maxlength="128" /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.code'/></td>
        <td class="tb_left_bg" colspan="3"><input type="text"  class="input_read" mandatory="Y" msg="<spring:message code='env.bizsystem.code'/>" name="bizSystemCode"  value="<%=EscapeUtil.escapeHtmlTag(bizSysVO.getBizSystemCode())%>"  style="width: 100%;" maxlength="16" readonly="true" /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.biztype.name'/></td>
        <td class="tb_left_bg" colspan="3"><input type="text"  mandatory="Y" msg="<spring:message code='env.biztype.name'/>" class="input" name="bizTypeName"  value="<%=EscapeUtil.escapeHtmlTag(bizSysVO.getBizTypeName())%>"  onkeyup="checkInputMaxLength(this,'',128)" style="width: 100%;" maxlength="128"  /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.biztype.code'/></td>
        <td class="tb_left_bg" colspan="3"><input type="text"  class="input_read" name="bizTypeCode"  value="<%=EscapeUtil.escapeHtmlTag(bizSysVO.getBizTypeCode())%>"  style="width: 100%;" maxlength="16"  readonly="true" /></td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.useyn'/></td>
        <td class="tb_left_bg"  colspan="3">
            <input type="radio"   name="useYn"  <%= ("Y".equals(bizSysVO.getUseYn())) ? "checked" :"" %>  value="Y" /><spring:message code='env.bizsystem.use'/>
            <input type="radio"   name="useYn"  <%= ("N".equals(bizSysVO.getUseYn())) ? "checked" :"" %>  value="N"  /><spring:message code='env.bizsystem.notuse'/>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.lastackyn'/></td>
        <td class="tb_left_bg">
	        <input type="checkbox"   name="chk_lastAckYn"   onclick="javascript:setChecked(this, 1);" <%= "Y".equals(bizSysVO.getLastAckYn()) ? "checked" : "" %>/>
	        <input type="hidden"     name="lastAckYn"   value="<%=bizSysVO.getLastAckYn() %>" />
        </td>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.numberingyn'/></td>
        <%
        String numberingYn="";
        if("1".equals(opt358)){ 
            numberingYn = "disabled";
        }
        %>
        <td class="tb_left_bg">
	        <input type="checkbox"   name="chk_numberingYn"   onclick="javascript:setChecked(this, 2);" <%= "Y".equals(bizSysVO.getNumberingYn()) ? "checked" : "" %> <%=numberingYn %>/>
	        <input type="hidden"     name="numberingYn"   value="<%=bizSysVO.getNumberingYn() %>" />
        </td>
    </tr>
    
 	<tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.doc.category'/></td>
        <td class="tb_left_bg" colspan="3">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td width="82%">
                    <%
						//<input type="text"  class="input_read" msg="<spring:message code='env.bizsystem.doc.category'/>" id="unitName" name="unitName"  style="width: 100%;" value="<=EscapeUtil.escapeHtmlTag(bizSysVO.getUnitName())" readonly />
					 %>
                    	<input type="text"  class="input" id="unitId" name="unitId" style="width: 100%;" value="<%=bizSysVO.getUnitId() %>"/>
						<input type="hidden"  id="useNumbering" name="useNumbering" value="<%=bizSysVO.getNumberingYn() %>"/>
					</td>
                    <%
					//<td width="18%" align="right">
						//<acube:button onclick="selectUnit();return(false);" value="=docCategory" type="4" class="gr" />
					//</td>
                     %>
				</tr>
			</table>
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%"><spring:message code='env.bizsystem.overlapyn'/></td>
        <td class="tb_left_bg" colspan="3">
	        <input type="checkbox"   name="chk_overlapYn"   onclick="javascript:setChecked(this, 3);" <%= "Y".equals(bizSysVO.getOverlapYn()) ? "checked" : "" %>/><spring:message code='env.bizsystem.permit'/>
	        <input type="hidden"     name="overlapYn"   value="N" />
        </td>
    </tr>
    <tr>
        <td class="tb_tit_left" width="22%" ><%=messageSource.getMessage("env.form.remark", null, langType)%></td>
        <td class="tb_left_bg" width="78%" colspan="3"><textarea name="remark" class="input" rows="5" style="width: 100%;height:50;" maxlength="2000" onkeyup="checkInputMaxLength(this,'',2000)"><%=bizSysVO.getRemark() %></textarea></td>
    </tr>
</table>
</form>
</center>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
<!--	<script src="./htdocs/js/object/cn_filetransferctrl.js"></script> -->

</body>
</html>