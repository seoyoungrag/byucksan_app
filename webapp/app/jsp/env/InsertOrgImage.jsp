<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.idir.org.orginfo.OrgImage" %>
<%@ page import="com.sds.acube.app.common.vo.OrganizationVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : InsertOrgImage.jsp 
 *  Description : 직인/서명인 등록,수정 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.25 
 *   수 정 자 : redcomet 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  redcomet
 *  @since 2011. 6. 25 
 *  @version 1.0 
 */ 
%>

<%
	//결과데이타

	String permission  = (String)request.getAttribute("permission");
	if (!"A".equals(permission) && !"I".equals(permission) && !"D".equals(permission)) {
		response.sendRedirect(webUri + "/app/jsp/error/ErrorLimited.jsp");
	}

    OrgImage orgImage = (OrgImage)request.getAttribute("orgImage");
	OrganizationVO organizationVO = (OrganizationVO)request.getAttribute("organizationVO");
	String orgId = organizationVO.getOrgID();
	boolean isInstitution = organizationVO.getIsInstitution();

	String menuTitle = null; 

	String imageId = orgImage.getImageID();
	if(imageId == null) imageId = "";

	int imageType = orgImage.getImageType();
	
	if("".equals(imageId)) {
	    imageId = "";
	    menuTitle = messageSource.getMessage("env.option.subtitle.userseal.regist" , null, langType);
	    if(imageType == 1) {
		    menuTitle = messageSource.getMessage("env.option.subtitle.orgseal.regist" , null, langType); 
		}
	} else {
	    menuTitle = messageSource.getMessage("env.option.subtitle.userseal.update" , null, langType);
	    if(imageType == 1) {
		    menuTitle = messageSource.getMessage("env.option.subtitle.orgseal.update" , null, langType); 
		}
	}
	
	
	String imageTypeStr = messageSource.getMessage("env.seal.userseal" , null, langType); 
	if(imageType == 1) {
	    imageTypeStr = messageSource.getMessage("env.seal.orgseal" , null, langType); 
	}

	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType); 
	String buttonCancel = messageSource.getMessage("env.option.button.cancel" , null, langType); 

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title><%= menuTitle %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
		
<script language="javascript">
//이미지 미리 보기기능...
function changeImage(path){
	var imgObj = document.getElementById("img_picture");
	imgObj.setAttribute("src",path);
	//imgObj.show();
}

function modify(){
	var modifyForm = document.getElementById("modifyForm");	
	var imagePath = document.getElementById("image").value;
	if(checkBlank()){				
		if(checkUploadType(imagePath)){					
			modifyForm.action = "<%=webUri%>/app/env/updateOrgImageInfo.do";
			modifyForm.target = "_self";
			modifyForm.submit();
			alert("<spring:message code='env.option.msg.success.save'/>");
			if(opener !== null){
				opener.selectOrgImageList("<%= orgId %>",<%= imageType %>);	
			}
		}else{
			alert("<spring:message code='env.seal.msg.no.image.file'/>");
			return ;
		}							
	}else{
		return;
	}
}

//특정 input 객체의 값이 비어 있는 지 여부 판별	
function checkBlank() {
	var form = document.getElementById("modifyForm");	
<%
	if("".equals(imageId)) {
%>
		if (form.image.value == "") {
			alert("<spring:message code='env.seal.msg.no.image.file'/>");
			return false;
		}
<%
	}
%>
	if (form.imageName.value == "") {
		alert("<spring:message code='env.seal.msg.no.image.name'/>");
		form.imageName.focus();
		return false;
	}	
	
	if(!IsInt(form.sizeWidth.value)){
		alert("<spring:message code='env.seal.msg.image.size.insert.onlynumber'/>");
		form.sizeWidth.value = "";
		form.sizeWidth.focus();
		return false;
	}

	if(!IsInt(form.sizeHeight.value)){
		alert("<spring:message code='env.seal.msg.image.size.insert.onlynumber'/>");
		form.sizeHeight.value = "";
		form.sizeHeight.focus();
		return false;
	}

	if (form.sizeWidth.value < 10 || form.sizeWidth.value > 54) {
		alert("<spring:message code='env.seal.msg.image.size.insert.wrong'/>");
		form.sizeWidth.focus();
		return false;
	}
	
	if (form.sizeHeight.value < 10 || form.sizeHeight.value > 54) {
		alert("<spring:message code='env.seal.msg.image.size.insert.wrong'/>");
		form.sizeHeight.focus();
		return false;
	}				

	if(!IsInt(form.imageOrder.value) || form.imageOrder.value==""){
		alert("<spring:message code='env.seal.msg.imageorder.insert.onlynumber'/>");
		form.imageOrder.value = "0";
		form.imageOrder.focus();
		return false;
	}
	
	return true;
}

//이미지 파일 여부 판별 
extArray = new Array(".gif", ".jpg", ".bmp");	
function checkUploadType(file) {
	var allowUpload = false; 		
	if (file == "" || file == null) return true;
	
	while(file.indexOf("\\") != -1){
		file = file.slice(file.indexOf("\\") + 1);
		ext	= file.slice(file.indexOf(".")).toLowerCase();
		for (var i = 0; i < extArray.length; i++) {
			if (extArray[i] == ext){
			 	allowUpload = true;
			 	break;
			}
		}
	}
	return allowUpload;
}		


//폐기 여부 체크 박스 체크 
function checkDisuseYN(){
	var disuseChkbox = document.getElementById("disuseYN");
	if(disuseChkbox.checked){
		disuseChkbox.value = "1";
		document.getElementById("disuseReasonTR").style.display = "";
		document.getElementById("disuseRemarksTR").style.display = "";
	}else{
		disuseChkbox.value = "0";
		document.getElementById("disuseReasonTR").style.display = "none";
		document.getElementById("disuseRemarksTR").style.display = "none";
		document.getElementById("disuseReason").value ="";
		document.getElementById("disuseRemarks").value ="";
	}	
}

//숫자인가를 체크하는 함수
function IsInt(value) {
    var   j;
    var    _intValue   = '0123456789';
    for (j=0; j<value.length; j++){
    	if (_intValue.indexOf(value.charAt(j))<0) return false;
    }
    return true;
}

function _close(){
	self.close();	
}

</script>
		
	</head>

	<body class="no_margin">
	
	<form id="modifyForm" name="modifyForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
		<acube:outerFrame>			
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<acube:titleBar><%= menuTitle %></acube:titleBar>
					</td>
				</tr>
				<tr>
					<acube:space between="title_button" />
				</tr>
				<tr>
					<td>
						
					</td>
				</tr>
				<tr>
					<acube:space between="menu_list" />
				</tr>
			</table>

			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1">
				<input type="hidden" name="orgID" id="orgID" value="<%= orgId %>">
				<input type="hidden" name="imageID" id="imageID" value="<%= imageId %>">
				<input type="hidden" name="registrationDate" id="registrationDate" value="<%= orgImage.getRegistrationDate() %>">
				<input type="hidden" name="checkDisuse" id="checkDisuse" value="<%=orgImage.getDisuseYN()%>"> 				
				<tr>
					<td class="tb_left" rowspan="11" style="width:30%;Text-align:center;">
						<% if (orgImage.getImageFileType() != null && !"".equals(orgImage.getImageFileType())) { 
							int imageWidth = orgImage.getSizeWidth();
							int imageHeight = orgImage.getSizeHeight();
							int imageMaxSize = Math.max(imageWidth,imageHeight);
							if(imageMaxSize>42) {
								imageWidth = imageWidth * 3;
								imageHeight = imageHeight * 3;
							} else if(imageMaxSize>34) {
								imageWidth = imageWidth * 4;
								imageHeight = imageHeight * 4;
							} else if(imageMaxSize>28) {
								imageWidth = imageWidth * 5;
								imageHeight = imageHeight * 5;
							} else {
								imageWidth = imageWidth * 6;
								imageHeight = imageHeight * 6;
							}
						%>
							<img id="img_picture" src="<%=webUri%>/app/approval/selectOrgSeal.do?sealId=<%=imageId%>" width="<%=imageWidth%>" height="<%=imageHeight%>" >			
						<% } else { %>
							<img id="img_picture" src="<%=webUri%>/app/ref/image/common/n_no_img.gif" border="0" />
						<% } %>
					</td>
					<td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.file'/><%if("".equals(imageId)) {%><spring:message code='common.title.essentiality'/><%}%></nobr></td>
                    <td class="tb_left_bg" colspan="2">                     	                 
                         <input type="file" name="image" id="image" class="input" style="width:100%;" onchange="javascript:changeImage(this.value);" accept="image/jpg">                         
                    </td>
                 </tr>			
                 <tr>               
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.name'/><spring:message code='common.title.essentiality'/></nobr></td>
                     <td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
		                     <tr>
			                     <td nowrap>
			                     	<input type="text" name="imageName" id="imageName" value="<%=orgImage.getImageName()%>" class="input" style="width:100%;" onkeyup="checkInputMaxLength(this,'',100)">
			                     </td>
		                     </tr>
		                 </table>
		              </td>                                
                 </tr>
                  <tr>             
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.type'/></nobr></td>
                     <td class="tb_left_bg" colspan="2">
<%
/*
%>                     
						<%if(isInstitution){%>
                         <input type="radio" name="imageType"  value="1" <%if(imageType==1) out.print("checked");%>><spring:message code='env.seal.orgseal'/>
                         <input type="radio" name="imageType"  value="0" <%if(imageType==0) out.print("checked");%>><spring:message code='env.seal.userseal'/>
						<!-- 
                         <input type="radio" name="imageType"  value="2" <%if(imageType==2) out.print("checked");%>><spring:message code='env.seal.userseal.omit'/>
                         <input type="radio" name="imageType"  value="3" <%if(imageType==3) out.print("checked");%>><spring:message code='env.seal.orgseal.omit'/>
                         <input type="radio" name="imageType"  value="4" <%if(imageType==4) out.print("checked");%>><spring:message code='env.seal.enforce.omit'/>
						-->
						<%}else{ %>
                         <input type="hidden" name="imageType"  value="0"><spring:message code='env.seal.userseal'/>
						<%} %>
<%
*/
%>
                         <%= imageTypeStr %>
                         <input type="hidden" name="imageType"  value="<%= imageType %>">
                         
                     </td>                     
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.issue.reason'/></nobr></td>
	                 <td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">    
		                     <tr>
			                     <td nowrap>
			                        <input type="text" name="issueReason" id="issueReason" value="<%=orgImage.getIssueReason()%>" class="input" style="width:100%;" onkeyup="checkInputMaxLength(this,'',100)">
			                     </td>
		                     </tr>
		                  </table>
		              </td>
                 </tr>
                 <tr>                 	 
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.managed.org'/></nobr></td>
	                 <td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">    
		                     <tr>
		                     <td nowrap>
		                         <input type="text" name="managedOrg" id="managedOrg" value="<%=orgImage.getManagedOrg()%>"  class="input" style="width:100%;" onkeyup="checkInputMaxLength(this,'',200)">
		                     </td>
		                     </tr>
		                 </table>
		             </td>
                 </tr>
                 <tr>                 	 
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.size'/><spring:message code='common.title.essentiality'/></nobr></td>
                     <td style="background-color:#FFFFFF;" colspan="2">
                     	<table cellpadding="0" cellspacing="0" border="0">
                     		<tr>
                     			<td class="tb_left_bg"><input type="text" name="sizeWidth" id="sizeWidth" value="<%=orgImage.getSizeWidth()%>" class="input" style="Text-align:right;width:50;"></td>
                     			<td class="tb_left_bg">X</td>
                     			<td class="tb_left_bg"><input type="text" name="sizeHeight" id="sizeHeight" value="<%=orgImage.getSizeHeight()%>" class="input" style="Text-align:right;width:50;"></td>                   		  
                    		</tr>
						</table>
                     </td>
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.order'/></nobr></td>
                     <td class="tb_left_bg" colspan="2">
                         <input type="text" name="imageOrder" id="imageOrder" value="<%=orgImage.getImageOrder()%>" class="input" style="Text-align:right;width:123;">
                     </td>
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.regist.remarks'/></nobr></td>
	                 <td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">    
	                     	<tr>
		                     	<td nowrap>
		                        	 <input type="text" name="registrationRemarks" id="registrationRemarks" value="<%=orgImage.getRegistrationRemarks()%>" class="input" style="width:100%;" onkeyup="checkInputMaxLength(this,'',100)">
		                     	</td>
	                     	</tr>
	                     </table>
	                 </td>
                 </tr>                 			
				
			</acube:tableFrame>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr height="5px">
					<td></td>
				</tr>
			</table>
			
			<acube:tableFrame>
				<col width="30%">
				<tr>
					<td class="tb_tit"><nobr><spring:message code='env.seal.disuse.yn'/></nobr></td>
					<td class="tb_left_bg" colspan="2">
						<%if(orgImage.getDisuseYN()){%>
							<input type="checkbox" id="disuseYN" name="disuseYN" onclick="checkDisuseYN()" value="1" checked><spring:message code='env.seal.status.disuse'/>
						<%}else{ %>
							<input type="checkbox" id="disuseYN" name="disuseYN" onclick="checkDisuseYN()" value="0"><spring:message code='env.seal.status.disuse'/>
						<%} %>	
					</td>
				</tr>
				<tr id="disuseReasonTR" <%if(!orgImage.getDisuseYN()){ %>style="display:none"<%}%> >
					<td class="tb_tit"><spring:message code='env.seal.disuse.reason'/></td>
					<td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">	
							<tr>
							<td nowrap>
								<input type="text" id="disuseReason" name="disuseReason" value="<%=orgImage.getDisuseReason()%>" class="input" style="width:100%" onkeyup="checkInputMaxLength(this,'',100)">
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="disuseRemarksTR" <%if(!orgImage.getDisuseYN()){ %>style="display:none"<%}%> >
					<td class="tb_tit"><spring:message code='env.seal.disuse.remarks'/></td>
					<td class="tb_left_bg" colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">	
							<tr>
							<td nowrap>
								<input type="text" id="disuseRemarks" name="disuseRemarks" value="<%=orgImage.getDisuseRemarks()%>" class="input" style="width:100%" onkeyup="checkInputMaxLength(this,'',100)">
							</td>
							</tr>
						</table>
					</td>
				</tr>			
			</acube:tableFrame>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
			  		<tr>
			  			<td height="10px">
			  			</td>			  			
			  		</tr> 
			  		<tr>
			  			<td>                               
                     	<acube:buttonGroup align="right">
                     		<acube:button id="btnModify" href="#" onclick="modify();" value="<%= buttonSave %>" />
							<acube:space between="button" />							
							<acube:button id="btnClose" href="#" onclick="_close();" value="<%= buttonCancel %>" />
						</acube:buttonGroup>
						</td>
					</tr>    
			</table>
		</acube:outerFrame>
	</form>
	</body>
</html>