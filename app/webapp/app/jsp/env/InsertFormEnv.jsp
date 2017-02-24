<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : InsertFormEnv.jsp 
 *  Description : 관리자 환경설정 - 캠페인/로고/심볼 등록 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.14 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 14 
 *  @version 1.0 
 */ 
%>
<%
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
	String buttonClose = messageSource.getMessage("env.option.button.close" , null, langType);
	String msgConfirm = messageSource.getMessage("env.option.msg.confirm.register" , null, langType);
	String buttonFile = messageSource.getMessage("env.option.button.file" , null, langType);
	String imagePath = webUri + "/app/ref/image";
	String compId = (String) session.getAttribute("COMP_ID");
	int limitSize = 32*1024;

	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	String registerDeptId = institutionId;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<style type="text/css">
	.range {display:inline-block;width:140px;height:25px; !important;}
</style>
<script type="text/javascript">	
$(document).ready(function() { initialize(); });
	function initialize() {
		initializeFileManager();
	}
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(4) %>

	function appendAttachEnv(envType) {
		var filelist = FileManager.uploadfile("image");

		if (filelist.length > 0) {

			if (<%=limitSize%> > filelist[0].size) {
				$('#'+envType+'LocalFilePath').val(filelist[0].localpath);
				$('#fileName').val(filelist[0].filename);
				$('#fileDisplayName').val(filelist[0].displayname);
				if (envType=="FET001") {
					preViewLogo.innerHTML = "<img src='<%=webUri%>/app/env/imgPreView.do?fileName="+filelist[0].filename+"' width='100' height='100' border='1' />";
				} else if (envType=="FET002") {
					preViewSymbol.innerHTML = "<img src='<%=webUri%>/app/env/imgPreView.do?fileName="+filelist[0].filename+"' width='100' height='100' border='1' />";
				}				
			} else {	
				alert('<spring:message code="env.option.msg.validate.limitsize"/>');
				return false;			
			}
		}
	}

	function callMethod(flag) {
		$('input:checkbox').attr('checked', false);	
		$('input:hidden').val('');	
		$('div[group="cls"]').hide();
		$('#'+flag).show();
	}

	function setDefaultYn(obj, flag) {
		if (obj.checked == true) {
			$('#'+flag).val('Y');
		} else {
			$('#'+flag).val('N');
		}	
	}

	function checkValidate(envType) {
		if (envType == "FET001" && $('#FET001LocalFilePath').val() == "") {
			alert('<spring:message code="env.option.msg.validate.logosymbol"/>');
			return false;
		} else if (envType == "FET002" && $('#FET002LocalFilePath').val() == "") {
			alert('<spring:message code="env.option.msg.validate.logosymbol"/>');
			return false;
		} else if (envType == "FET003" && $('#FET003EnvInfo').val() == "") {
			alert('<spring:message code="env.option.msg.validate.campaign"/>');
			$('#FET003EnvInfo').focus();
			return false;
		} else if (envType == "FET004" && $('#FET004EnvInfo').val() == "") {
			alert('<spring:message code="env.option.msg.validate.campaign"/>');
			$('#FET004EnvInfo').focus();
			return false;
		} else {
			return true;
		}
	}

	function insertFormEnv(envType) {
		if (checkValidate(envType)) {
			var procUrl = "";
			var result = null;
			$('#envType').val(envType);
			if (envType == "FET001" || envType == "FET002") { // 로고,심볼
				procUrl = "<%=webUri%>/app/env/admincharge/insertLogoSymbol.do";
			} else if (envType == "FET003" || envType == "FET004") { // 캠페인
				procUrl = "<%=webUri%>/app/env/admincharge/insertCampaign.do";
			}
			if (confirm("<%=msgConfirm%>")) {
				$.ajaxSetup({async:false});
				$.post(procUrl, $("#insertCLSForm").serialize(), function(data) {
					if (data.success) {
						if (envType == "FET003" || envType == "FET004") { // 캠페인
							// 다국어 추가
							registResource(data.formEnvId);
						}

						alert("<spring:message code='env.option.msg.success.register'/>");
					} else {
						alert("<spring:message code='env.option.msg.error'/>");
					}
				}, 'json');

				opener.location.href = "<%=webUri%>/app/env/admincharge/selectOptionCLS.do";
				window.close();
		    }
		}
	}
</script>
</head>
<body scrolling="auto" leftmargin="10" topmargin="0" marginwidth="0" marginheight="0" style="margin-left:0;">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
    <tr>
        <td><span class="pop_title77"><spring:message code="env.option.subtitle.cls.register"/></span></td>


		<form:form modelAttribute="" method="post" name="insertCLSForm" id="insertCLSForm">
		<tr>
			<acube:space between="title_button" />
		</tr>

         <tr>
            <td>
               <acube:tabGroup>
                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('compaignUp');" selected="true"><spring:message code="env.option.subtitle.opt323"/></acube:tab>
				  <acube:space between="tab"/>
				  <acube:tab index="2" onClick="JavaScript:selectTab(2);callMethod('compaignDown');"><spring:message code="env.option.subtitle.opt324"/></acube:tab>
				  <acube:space between="tab"/>
                  <acube:tab index="3" onClick="JavaScript:selectTab(3);callMethod('logo');"><spring:message code="env.option.subtitle.opt328"/></acube:tab>
                  <acube:tab index="4" onClick="JavaScript:selectTab(4);callMethod('symbol');"><spring:message code="env.option.subtitle.opt329"/></acube:tab>
               </acube:tabGroup>
            </td>
         </tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				
				<!----- 상부캠패인 ----->
				<div id="compaignUp" group="cls" style="display:block;">
					<table width="100%" class='td_table' border='0' cellpadding='0' cellspacing='0'>
						<tr bgcolor="#ffffff">
							<td width="90" class="tb_tit b_td">
								<spring:message code="env.option.subtitle.opt323"/>
								<spring:message code='common.title.essentiality'/>
							</td>
							<td width="*" class="tb_left_bg">
								<input type="text" name="FET003EnvInfo" id="FET003EnvInfo" onclick="showResource(this, 'FORM_ENV', 'ENV_INFO', '', '')" readOnly class="input" style="width:100%;" />
							</td>
						</tr>				
					</table>
					<div style="width:100%;padding:5px 0 10px 0;font-size:13px;">
						<input type="checkbox" name="FET003defaultYn" id="FET003defaultYn" onClick="setDefaultYn(this, 'campDefaultYn')" />
						<font style="font-size:12px;"><spring:message code="env.option.txt.02"/></font>
					</div>
					<div style="width:100%;">
						<acube:buttonGroup>
							<acube:button onclick="javascript:insertFormEnv('FET003')" value='<%=buttonSave%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="javascript:window.close();" value='<%=buttonClose%>' type="2" class="gr" />
						</acube:buttonGroup>
					</div>
				</div> 
				<!----- 상부캠페인 ----->
				
				<!----- 하부캠패인 ----->
				<div id="compaignDown" group="cls" style="display:none;">
					<table width="100%" class='td_table' border='0' cellpadding='0' cellspacing='0'>
						<tr bgcolor="#ffffff">
							<td width="90" class="tb_tit b_td">
								<spring:message code="env.option.subtitle.opt324"/>
								<spring:message code='common.title.essentiality'/>
							</td>
							<td width="*" class="tb_left_bg">								
								<input type="text" name="FET004EnvInfo" id="FET004EnvInfo" onclick="showResource(this, 'FORM_ENV', 'ENV_INFO', '', '')" readOnly class="input" style="width:100%;" />
							</td>
						</tr>				
					</table>
					<div style="width:100%;padding:5px 0 10px 0;font-size:13px;">
						<input type="checkbox" name="FET004defaultYn" id="FET004defaultYn" onClick="javascript:setDefaultYn(this, 'campDefaultYn')" />
						<font style="font-size:12px;"><spring:message code="env.option.txt.03"/></font>
					</div>
					<div style="width:100%;">
						<acube:buttonGroup>
							<acube:button onclick="javascript:insertFormEnv('FET004')" value='<%=buttonSave%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="javascript:window.close();" value='<%=buttonClose%>' type="2" class="gr" />
						</acube:buttonGroup>
					</div>
				</div>
				<!----- 하부캠페인 ----->
				
				<!----- 로고 ----->
				<div id="logo" group="cls" style="display:none;">
					<table width="100%" class='td_table' border='0' cellpadding='0' cellspacing='0'>
						<tr bgcolor="#ffffff">
							<td width="85" class="tb_tit b_td">
								<spring:message code="env.option.form.19"/><spring:message code='common.title.essentiality'/>
							</td>
							<td width="*" class="tb_left_bg">
								<input type="text" name="FET001LocalFilePath" id="FET001LocalFilePath" class="input" style="width:100%;" readOnly />
							</td>
							<td width="80" align="center" class="b_td r_td">
								<acube:button onclick="appendAttachEnv('FET001');return(false);" value="<%=buttonFile%>" type="4" class="gr" />
							</td>
						</tr>				
					</table>
					<div align="right" style="width:100%;text-align:center;padding:5px 0 10px 0;font-size:12px;color:blue;">
						<spring:message code='common.title.essentiality'/>
						<font color="#999999"><spring:message code="env.option.txt.06"/></font>
					</div>
					<div style="height:100px;border:solid 1px #e3e2e3;background-color:#f1f1f2;padding:10px 10px 10px 10px;">
						<span id="preViewLogo" style="width:100px;height:100px;border:solid 1px #e7e7e7;">
							<img src="<%=imagePath%>/common/n_no_img.gif" border="0" />						
						</span>
						<span style="width:250px;height:100px;padding-left:10px;">
							<input type="checkbox" name="FET001defaultYn" id="FET001defaultYn" onClick="javascript:setDefaultYn(this, 'LSDefaultYn')" />
							<font style="font-size:12px;"><spring:message code="env.option.txt.04"/></font>			
						</span>
					</div>
					
					<div style="width:100%;padding-top:5px;">
						<acube:buttonGroup>
							<acube:button onclick="javascript:insertFormEnv('FET001')" value='<%=buttonSave%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="javascript:window.close();" value='<%=buttonClose%>' type="2" class="gr" />
						</acube:buttonGroup>
					</div>
				</div>
				<!----- 로고 ----->
				
				<!----- 심볼 ----->
				<div id="symbol" group="cls" style="display:none;">
					<table width="100%" class='td_table' border='0' cellpadding='0' cellspacing='0'>
						<tr bgcolor="#ffffff">
							<td width="85" class="tb_tit b_td">
								<spring:message code="env.option.form.19"/>
								<spring:message code='common.title.essentiality'/>
							</td>
							<td width="*" class="tb_left_bg">
								<input type="text" name="FET002LocalFilePath" id="FET002LocalFilePath" class="input" style="width:100%;" readOnly />
								<!-- input type="file" name="FET002EnvInfo" id="FET002EnvInfo" style="width:100%;" /-->
							</td>
							<td width="80" align="center" class="b_td r_td">
								<acube:button onclick="appendAttachEnv('FET002');return(false);" value="<%=buttonFile%>" type="4" class="gr" />
							</td>
						</tr>				
					</table>
					
					<div align="right" style="width:100%;text-align:center;padding:5px 0 10px 0;font-size:12px;color:blue;">
						<spring:message code='common.title.essentiality'/>
						<font color="#999999"><spring:message code="env.option.txt.06"/></font>
					</div>
					<div style="height:100px;border:solid 1px #e3e2e3;background-color:#f1f1f2;padding:10px 10px 10px 10px;">
						<span id="preViewSymbol" style="width:100px;height:100px;border:solid 1px #e7e7e7;">
							<img src="<%=imagePath%>/common/n_no_img.gif" border="0" />						
						</span>
						<span style="width:250px;height:100px;padding-left:10px;">
							<input type="checkbox" name="FET002defaultYn" id="FET002defaultYn" onClick="javascript:setDefaultYn(this, 'LSDefaultYn')" />
							<font style="font-size:12px;"><spring:message code="env.option.txt.05"/></font>			
						</span>
					</div>
					
					<div style="width:100%;padding-top:5px;">
						<acube:buttonGroup>
							<acube:button onclick="javascript:insertFormEnv('FET002')" value='<%=buttonSave%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="javascript:window.close();" value='<%=buttonClose%>' type="2" class="gr" />
						</acube:buttonGroup>
					</div>
				</div>
				<!----- 심볼 ----->
				
			</td>
		</tr>
		<input type="hidden" name="envType" id="envType" value="" />
		<input type="hidden" name="campDefaultYn" id="campDefaultYn" value="" />
		<input type="hidden" name="LSDefaultYn" id="LSDefaultYn" value="" />
		<input type="hidden" name="registerDeptId" id="registerDeptId" value="<%=registerDeptId%>" />
		<input type="hidden" name="fileName" id="fileName" value="" />
		<input type="hidden" name="fileDisplayName" id="fileDisplayName" value="" />
		</form:form>
	</table>

</acube:outerFrame>

</body>
</html>