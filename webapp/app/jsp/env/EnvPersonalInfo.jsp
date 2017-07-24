<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : EnvPersonalInfo.jsp 
 *  Description : 개인정보 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.28 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 28 
 *  @version 1.0 
 */ 
%>
<% 
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String)session.getAttribute("USER_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String buttonOrg = messageSource.getMessage("env.option.subtitle.05", null, langType); // 조직도
	String buttonSearch = messageSource.getMessage("env.form.formsearch", null, langType); // 찾기
	String buttonModify = messageSource.getMessage("env.option.button.modify", null, langType); // 수정
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType); // 삭제
	String buttonInitialize = messageSource.getMessage("env.empty.button.initialize" , null, langType); // 초기화
	String buttonZipcode = messageSource.getMessage("env.option.button.zipcode" , null, langType); // 우편번호
	String confirmBtn = messageSource.getMessage("env.option.button.save", null, langType); // 확인
	String pwUseYn = (String) request.getAttribute("pwUseYn");
	String mode = (String) request.getAttribute("mode");
	String pictureYn = (String) request.getAttribute("pictureYn");
	String signYn = (String) request.getAttribute("signYn");
	String imagePath = webUri + "/app/ref/image";
	int limitSize = 32 * 1024;

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
    String opt419 = appCode.getProperty("OPT419", "OPT419", "OPT"); // 하부캠페인사용여부
    String personalSign = envOptionAPIService.selectOptionValue(compId, opt419);	//AppConfig.getProperty("comp" + compId, "N", "personalSign"); // 개인이미지서명 사용여부
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.menu.sub.25" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">

</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var userId = "<%=userId%>";
var nType = "";

function appendAttachEnv(nType) {
	var filelist = FileManager.uploadfile("image");	

	if (filelist.length > 0) {

		if (<%=limitSize%> > filelist[0].size) {
			$('#fileName'+nType).val(filelist[0].filename);
			if (nType=="0") {
				divPersonalPicture.innerHTML = "<img src='<%=webUri%>/app/env/imgPreView.do?fileName="+filelist[0].filename+"' width='100' height='120' border='0' />";
			} else if (nType=="2") {
				divPersonalSign.innerHTML = "<img src='<%=webUri%>/app/env/imgPreView.do?fileName="+filelist[0].filename+"' width='140' height='120' border='0' />";
			}	
		} else {	
			alert('<spring:message code="env.option.msg.validate.limitsize"/>');
			return false;			
		}
	}
}

function getNType() {
	return $("#nType").val();
}
function setNType(n) {
	$("#nType").val(n);
}

function updateImg(nType) {
	if ($('#fileName'+nType).val()=="") {
		alert("<spring:message code='env.personalinfo.msg.validate.noimage'/>");
		return;
	}

	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		if ($('#userIdImg').val()=="") {
			alert("<spring:message code='env.personalinfo.msg.validate.noUser'/>");
			return;
		}
	<% } %>

	
	setNType(nType);
	var result = null;
	$.ajaxSetup({async:false});
	$.getJSON("<%=webUri%>/app/env/updatePersonalImg.do", $('#personalInfoForm').serialize(), function(data){
		result = data;
	});
	if (result == "success") {
		alert("<spring:message code='env.option.msg.success.modify'/>");
	} else {
		alert("<spring:message code='env.option.msg.error'/>");
	}
}

<% if ("Y".equals(pwUseYn)) { %>   

	function checkPassword() {
		var result = false;
/*
		<% if ("general".equals(mode)) { %>
			if ($.trim($('#beforePassword').val()) == "") {
				alert("<spring:message code='env.personalinfo.msg.validate.nobeforepassword'/>");
				$('#beforePassword').focus();
				return false;
			}
		<% } %>
*/
		if ($.trim($('#newPassword').val()) == "") {
			alert("<spring:message code='env.personalinfo.msg.validate.nonewpassword'/>");
			$('#newPassword').focus();
			return false;
		}
		if ($.trim($('#confirmPassword').val()) == "") {
			alert("<spring:message code='env.personalinfo.msg.validate.noconfirmpassword'/>");
			$('#confirmPassword').focus();
			return false;
		}
		if ($.trim($('#newPassword').val()) == $.trim($('#confirmPassword').val())) {
			result = true;
		} else {
			alert("<spring:message code='env.personalinfo.msg.validate.nomatching'/>");
		}
		return result;
	}
	
	function updatePassWord() {
		if (checkPassword()) {
			prepareSeed();
			<% if ("general".equals(mode)) { %>
			if ($("#beforePassword").val() != "")
				$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#beforePassword").val()));
			<% } %>
			$("#newencryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#newPassword").val()));
			var result = null;
			$.ajaxSetup({async:false});
			$.getJSON("<%=webUri%>/app/env/updateAppPassword.do", $('#pwForm').serialize(), function(data){
				result = data;
			});
			if (result == "success") {
				alert("<spring:message code='env.personalinfo.msg.success.pwmodify'/>");
			} else if (result == "nomatching") {
				alert("<spring:message code='env.personalinfo.msg.validate.password'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}
		$('#newPassword').val("");
		$('#confirmPassword').val("");
		<% //if ("admin".equals(mode)) { %>
			//self.location.href = "<%=webUri%>/app/env/admin/envPersonalInfoAdmin.do";
		<% //} else { %>
			//self.location.href = "<%=webUri%>/app/env/selectEnvPersonalInfo.do";
		<% //} %>
	}
	
	function prepareSeed() {
	    var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();
	    if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
	        var userkey = document.getElementById("seedOCX").GetUserKey();
	        currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
	    }
	    var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);
	    $('#roundkey').val(roundkey_c);
	}

	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>		

		

		function initializePassword() {
			if ($('#userId').val() == "") {
				alert("<spring:message code='env.personalinfo.msg.validate.noUser'/>");
				return;
			}
			var result;
			$.ajaxSetup({async:false});
			$.getJSON("<%=webUri%>/app/env/admin/initializePassword.do?userId="+userId, function(data){
				result = data;
			});
			if (result == "success") {
				alert("<spring:message code='env.personalinfo.msg.success.pwInitailize'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
			$('#newPassword').val("");
			$('#confirmPassword').val("");
			//self.location.href = "<%=webUri%>/app/env/admin/envPersonalInfoAdmin.do";
		}
	<% } %>	

<% } %>

<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>

	function winPIOrgTreePop() {
		var winPIOrgTreePop = openWindow("winPIOrgTreePop", "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3", 600, 310);		
	}

	function winPIOrgTreeApprPop(){
		var winPIOrgTreePop = openWindow("winPIOrgTreePop", "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3&apprParam=Y", 600, 310);
	}

	function setUserInfo(obj) {		
		userId = obj.userId;
		$('#userId').val(obj.userId);
		$('#userIdImg').val(obj.userId);
		$('#userName').text(obj.userName);
		$('#deptName').text(obj.deptName);

		var results = new Object();
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/admin/selectEnvPersonalInfoImg.do?userId="+obj.userId, function(data){
			results = data;
		});

		if (results.pictureYn == "Y") {
			divPersonalPicture.innerHTML = "<img src='<%=webUri%>/app/env/EnvComImage.do?userId="+obj.userId+"&nType=0' width='100' height='120' border='0' />";
		} else {
			divPersonalPicture.innerHTML = "<img src='<%=imagePath%>/common/n_no_img.gif' border='0' />";
		} 

		if (results.signYn == "Y") {
			divPersonalSign.innerHTML = "<img src='<%=webUri%>/app/env/EnvComImage.do?userId="+obj.userId+"&nType=2' width='140' height='120' border='0' />";
		} else {
			divPersonalSign.innerHTML = "<img src='<%=imagePath%>/common/n_no_img.gif' border='0' />";
		}
	}

	function setUserOfficeApprInfo(obj) {
		userId = obj.userId;

		$('#officeInfoUserId').val(obj.userId);
		$('#officeInfoUserName').text(obj.userName);
		$('#officeInfoDeptName').text(obj.deptName);

		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/admin/selectOfficeApprInfoAjax.do?userId="+obj.userId, function(data){
			objEmpty = data;
		});
		getOfficeApprInfo(objEmpty);
	}

	function getOfficeApprInfo(obj){
		var displayZipCode = "";
		if(obj.officeZipCode != null && obj.officeZipCode != ""){
			displayZipCode = (obj.officeZipCode).substring(0, 3) + "-" + (obj.officeZipCode).substring(3, 6);
		}
				
		$('#zipCode').val(obj.officeZipCode);
		$('#displayZipCode').val(displayZipCode);
		$('#officeAddress').val(obj.officeAddr);
		$('#officeAddressDetail').val(obj.officeDetailAddr);
		$('#officeFax').val(obj.officeFax);
	}
	
<% } %>

function initialize() {
	initializeFileManager();

	initZipCode();
}

function initZipCode(){
	var userVO = new Object();
	userVO.zipCode = "${userVO.officeZipCode}";
	if (userVO.zipCode != "") {
		//var displayZipCode = (userVO.zipCode).substring(0,3) + "-" + (userVO.zipCode).substring(3,6);
		$('#displayZipCode').val(userVO.zipCode);
		$('#zipCode').val(userVO.zipCode);
	}	
}

function winZipCode() {
	var envDeptInfoZipCode = openWindow("envDeptInfoZipCode", "<%=webUri%>/app/env/selectZipcodePop.do", 500, 450);
}

function setZipCode(obj) {
	
	var displayZipCode = (obj.first().children().first().text());
	var zipCode = displayZipCode.replace('-','');
	$('#officeZipCode').val(zipCode);
	$('#displayZipCode').val(displayZipCode);
	$('#officeAddress').val(obj.first().attr('displayAddr'));
	$('#officeAddressDetail').val(obj.first().attr('bungi'));
}

function updateOfficeInfo(){
	
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		if($('#officeInfoUserId').val() == ""){
			alert("<spring:message code='env.personalinfo.msg.validate.noUser'/>");
			return;
		}
	<% } %>
	
	if($('#officeAddressDetail').val() != "" && ( $('#officeAddress').val() == "" || $('#officeZipCode').val() == "") ){
		alert("<spring:message code='env.personalinfo.msg.validate.noOfficeApprAddr'/>");		
		return;
	}

	if($('#officeFax').val() == "" && $('#officeAddressDetail').val() == "" && $('#officeAddress').val() == "" && $('#officeZipCode').val() == "" ){
		alert("<spring:message code='env.personalinfo.msg.validate.noOfficeApprInfo'/>");
		return;
	}		
	
	var result = null;
	$.ajaxSetup({async:false});
	$.getJSON("<%=webUri%>/app/env/charge/updateUserAppOfficeInfo.do", $('#officeInfoForm').serialize(), function(data){
		result = data;
	});
	if (result == "success") {
		alert("<spring:message code='env.option.msg.success.modify'/>");
	} else {
		alert("<spring:message code='env.option.msg.error'/>");
	}		
}

function initializeOfficeInfo(){
	
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
		if($('#officeInfoUserId').val() == ""){
			alert("<spring:message code='env.personalinfo.msg.validate.noUser'/>");
			return;
		}
	<% } %>

	if(confirm("<spring:message code='env.personalinfo.msg.init.confirm.officeApprInfo'/>")){
		
		$('#officeFax').val("");
		$('#officeZipCode').val("");
		$('#displayZipCode').val("");
		$('#officeAddress').val("");
		$('#officeAddressDetail').val("");
		
		var result = null;
		$.ajaxSetup({async:false});
		$.getJSON("<%=webUri%>/app/env/charge/updateUserAppOfficeInfo.do", $('#officeInfoForm').serialize(), function(data){
			result = data;
		});
		if (result == "success") {
			alert("<spring:message code='env.personalinfo.msg.success.officeApprInfo'/>");
		} else {
			alert("<spring:message code='env.option.msg.error'/>");
		}
		
	}
}
</script>
</head> 
<body>
<acube:outerFrame>

	<acube:titleBar><spring:message code="env.option.menu.sub.25" /></acube:titleBar>
	<br />	
	<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
	<table width="675" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:buttonGroup>					
					<acube:button onclick="winPIOrgTreePop();" value="<%=buttonOrg%>" type="2" class="gr" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td width="30%" class="tb_tit_left">
				<nobr>
					<strong><spring:message code="env.user.table.title.deptname"/> : </strong>
					<span id="deptName"></span>
				</nobr>
			</td>
			<td width="70%" class="tb_tit_left">
				<nobr>
					<strong><spring:message code="env.search.user.title"/> : </strong>
					<span id="userName"></span>
				</nobr>
			</td>
		</tr>
	</table>
	<% } %>
	<table width="675" border="0" cellpadding="12" cellspacing="0" style="table-layout:fixed;">
		<form:form modelAttribute="" method="post" name="personalInfoForm" id="personalInfoForm">
		<tr class="g_box">
		
			<!-- 사진 -->
			<td width="185" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><acube:titleBar type="sub"><spring:message code="env.info.personal.picture"/></acube:titleBar></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
							<div id="divPersonalPicture" style="text-align:center;height:125px;border:solid 1px #e7e7e7;background-color:#ffffff;">
							<% if ("Y".equals(pictureYn)) { %>
								<img src="<%=webUri%>/app/env/EnvComImage.do?userId=<%=userId%>&nType=0" width="100" height="120" border="0" />
							<% } else { %>
								<img src="<%=imagePath%>/common/n_no_img.gif" border="0" />
							<% } %>					
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding:5px 5px 0 0;">
							
							<acube:buttonGroup>
							<acube:button onclick="appendAttachEnv('0');return(false);" value='<%=buttonSearch%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="updateImg('0')" value='<%=buttonModify%>' type="2" class="gr" />
							</acube:buttonGroup>
							 
						</td>
					</tr>
				</table>
			</td>
			
			<!-- 서명 -->
			<td width="185" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><acube:titleBar type="sub"><spring:message code="env.info.personal.sign"/></acube:titleBar></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
							<div id="divPersonalSign" style="text-align:center;height:125px;border:solid 1px #e7e7e7;background-color:#ffffff;">
							<% if ("Y".equals(signYn)) { %>
								<img src="<%=webUri%>/app/env/EnvComImage.do?userId=<%=userId%>&nType=2" width="140" height="120" border="0" />
							<% } else { %>
								<img src="<%=imagePath%>/common/n_no_img.gif" border="0" />
							<% } %>				
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding:5px 5px 0 0;">
						<% if ("Y".equals(personalSign)) { %>
							<acube:buttonGroup>
							<acube:button onclick="appendAttachEnv('2');return(false);" value='<%=buttonSearch%>' type="2" class="gr" />
							<acube:space between="button" />
							<acube:button onclick="updateImg('2')" value='<%=buttonModify%>' type="2" class="gr" />
							</acube:buttonGroup>
						<% } %>	
						</td>
					</tr>
				</table>
			</td>
			
			<!-- 결재비밀번호 -->			
			<td width="185" valign="top">
				<% if ("Y".equals(pwUseYn)) { %>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><acube:titleBar type="sub"><spring:message code="env.info.personal.password"/></acube:titleBar></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
						<div id="divSettingPassword">
							<acube:tableFrame class="td_table">
							<% if ("general".equals(mode)) { %>
								<tr bgcolor="#ffffff">
									<td width="35%" class="tb_tit_left">
										<nobr><spring:message code="env.info.password.before"/></nobr><!-- 기존 비밀번호 -->
									</td>
									<td width="65%" class="tb_left_bg">
										<nobr><input type="password" id="beforePassword" name="beforePassword" class="input" style="width:100%;" /></nobr>
									</td>
								</tr>
							<% } %>
								<tr bgcolor="#ffffff">
									<td width="35%" class="tb_tit_left">
										<nobr><spring:message code="env.info.password.new"/></nobr><!-- 새 비밀번호 -->
									</td>
									<td width="65%" class="tb_left_bg">
										<nobr><input type="password" id="newPassword" name="newPassword" class="input" style="width:100%;" /></nobr>
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td class="tb_tit_left">
										<nobr><spring:message code="env.info.password.confirm"/></nobr><!-- 비밀번호 확인 -->
									</td>
									<td class="tb_left_bg">
										<nobr><input type="password" id="confirmPassword" name="confirmPassword" class="input" style="width:100%;" /></nobr>
									</td>
								</tr>
								<tr bgcolor="#ffffff" style="padding:3px;color:#3333ff;">
									<td colspan="2" height="40">※ <spring:message code="env.info.password.role"/></td>
								</tr>
							</acube:tableFrame>
							</div>
						</td>
					</tr>
					<tr><td width="*"></td></tr>
					<tr>
						<td style="padding:5px 5px 0 0;">
							<acube:buttonGroup>
							<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
							<acube:button onclick="initializePassword();" value="<%=buttonInitialize%>" type="2" class="gr" />
							<acube:space between="button" />
							<% } %>
							<acube:button onclick="updatePassWord();" value='<%=buttonModify%>' type="2" class="gr" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>				
				<% } %>
			</td>
		</tr>
		<!--input type="hidden" id="LocalFilePath0" name="LocalFilePath0" value="" />
		<input type="hidden" id="LocalFilePath2" name="LocalFilePath2" value="" /-->
		<input type="hidden" id="fileName0" name="fileName0" value="" />
		<input type="hidden" id="fileName2" name="fileName2" value="" />
		<input type="hidden" id="nType" name="nType" value="" />
		<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
			<input type="hidden" id="modeImg" name="modeImg" value="<%=mode%>" />
			<input type="hidden" id="userIdImg" name="userIdImg" value="" />
		<% } %>
		</form:form>
		<% if ("Y".equals(pwUseYn)) { %>		
		<form:form id="pwForm" name="pwForm" method="post" onsubmit="return(false);">
			<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
				<input type="hidden" id="mode" name="mode" value="<%=mode%>" />
				<input type="hidden" id="userId" name="userId" />
			<% } %>
			<input type="hidden" id="roundkey" name="roundkey" />
			<% if ("general".equals(mode)) { %>
				<input type="hidden" id="encryptedpassword" name="encryptedpassword" />
			<% } %>
			<input type="hidden" id="newencryptedpassword" name="newencryptedpassword" />
		</form:form>
		<% } %>
		
		
	</table>
	
	
	<table>
	<tr>
		<td height="24">&nbsp;</td>
	</tr>
	</table>
	
	<table border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;" >
		<tr>
			<td width="675" height="100%">
				<acube:titleBar ><spring:message code="env.option.menu.sub.50" /></acube:titleBar>
				<acube:space between="button_content" table="y"/>
				<acube:buttonGroup>
					<acube:menuButton onclick="initializeOfficeInfo();" value="<%=buttonInitialize%>" />
					<acube:space between="button" />
					<acube:menuButton onclick="updateOfficeInfo();" value="<%=confirmBtn%>" />
				</acube:buttonGroup>
				<acube:space between="button_content"/>
				<acube:space between="button_content" table="y"/>
				
				
				<acube:tableFrame class="td_table" width="675">
				<c:set var="vo" value="${userVO}" />
				<form:form modelAttribute="" method="post" name="officeInfoForm" id="officeInfoForm">	
					<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
					<input type="hidden" id="mode" name="mode" value="<%=mode%>" />
					<input type="hidden" name="officeInfoUserId" id="officeInfoUserId" value="" />
					<tr bgcolor="#ffffff">
						<td width="15%"  class="tb_tit_left">
							<nobr><spring:message code="env.user.table.title.deptname"/><!-- 부서명 --></nobr>
						</td>						
						<td width="35%" class="tb_left_bg">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="70%">
										<nobr><span id="officeInfoDeptName" class="search_text"></span></nobr>
									</td>
									<td width="*" align="right">
										<nobr>
										<acube:buttonGroup>
											<acube:button type="4" onclick="winPIOrgTreeApprPop();" value="<%=buttonOrg%>" />
										</acube:buttonGroup>
										</nobr>
									</td>
								</tr>
							</table>	
						</td>
						
						<td width="15%" class="tb_tit_left">
							<nobr><spring:message code="env.search.user.title"/></nobr><!-- 사용자명 -->				
						</td>
						<td width="35%" class="tb_left_bg">
							<nobr>
							<span id="officeInfoUserName"></span>
							</nobr>
						</td>
					</tr>
					<% } %>
					<tr bgcolor="#ffffff">
						<td width="15%"  class="tb_tit_left">
							<nobr><spring:message code="env.deptinfo.form.08"/></nobr><!-- 팩스번호 -->
						</td>
						<td width="35%" class="tb_left_bg">
							<nobr><input type="text" id="officeFax" name="officeFax" value="${vo.officeFax}" onkeyup="checkInputMaxLength(this,'',20)" class="input" style="width:100%;"/></nobr>
						</td>
						<td width="15%" class="tb_tit_left">
							<nobr><spring:message code="env.deptinfo.form.04"/></nobr><!-- 우편번호 -->				
						</td>
						<td width="35%" class="tb_left_bg">
							<nobr>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="40%">
										<!-- <input type="text" id="displayZipCode" name="displayZipCode" value="${vo.officeZipCode}" group="readOnly" readOnly class="input_read" style="width:95%;"/> -->
										<input type="text" id="officeZipCode" name="officeZipCode" value="${vo.officeZipCode}" style="width:95%;"/>
									</td>
									<td width="*">
									<!-- 
										<acube:buttonGroup align="left">
											<acube:button id="zipCodeButton" disabledid="" onclick="winZipCode();" value="<%=buttonZipcode%>" type="4" class="gr" />
										</acube:buttonGroup>
									 -->
									</td>
								</tr>
							</table>				
							</nobr>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tb_tit_left">
							<nobr><spring:message code="env.deptinfo.form.05"/></nobr><!-- 주소 -->
						</td>
						<td class="tb_left_bg" colspan="3">
							<!-- <nobr><input type="text" id="officeAddress" name="officeAddress" value="${vo.officeAddr}" group="readOnly" readOnly class="input_read" style="width:100%;"/></nobr> -->
							<nobr><input type="text" id="officeAddress" name="officeAddress" value="${vo.officeAddr}" style="width:100%;"/></nobr> 
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tb_tit_left">
							<nobr><spring:message code="env.deptinfo.form.06"/></nobr><!-- 상세주소 -->
						</td>
						<td class="tb_left_bg" colspan="3">
							<nobr><input type="text" id="officeAddressDetail" name="officeAddressDetail" value="${vo.officeDetailAddr}" onkeyup="checkInputMaxLength(this,'',100)" class="input" style="width:100%;"/></nobr>
						</td>
					</tr>
				</form:form>
				</acube:tableFrame>
			</td>
		</tr>
		<tr><td class="basic_text_01" style="padding:15px 0 10px 10px;" colspan="3"><spring:message code="env.option.description.38"/></td></tr>
	</table>
	
	
</acube:outerFrame>



<% if ("Y".equals(pwUseYn)) { %>
<jsp:include page="/app/jsp/common/seed.jsp" />
<% } %>
</body>	
</html>