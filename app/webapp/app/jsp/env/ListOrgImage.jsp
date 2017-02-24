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
 *  Class Name  : ListOrgImage.jsp 
 *  Description : 직인/서명인 관리 
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

	String institutionId = "";
	String institutionName = "";

	String orgId = (String) session.getAttribute("DEPT_ID");
	String orgName = (String) session.getAttribute("DEPT_NAME");

	String menuTitle = messageSource.getMessage("env.option.subtitle.userseal" , null, langType); 
	String msgChoiceSeal = messageSource.getMessage("env.seal.msg.choice.userseal" , null, langType); 
	if("A".equals(permission) || "I".equals(permission)) {
		OrganizationVO institution  = (OrganizationVO)request.getAttribute("institution");
		
	    institutionId = institution.getOrgID();
	    institutionName = institution.getOrgName();
	    
	    menuTitle = messageSource.getMessage("env.option.subtitle.seal" , null, langType); 
	    msgChoiceSeal = messageSource.getMessage("env.seal.msg.choice.seal" , null, langType); 
	}
	
	String buttonDeptChoice = messageSource.getMessage("env.seal.dept.choice" , null, langType); 
	String buttonInstitutionChoice = messageSource.getMessage("env.seal.institution.choice" , null, langType); 
	String buttonRegist = messageSource.getMessage("env.option.button.register" , null, langType); 
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= menuTitle %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">

	var orgTreeWin; //부서선택창
	var orgImageWin; //이미지정보창

	var selectedImageType;
	
    //등록
	function insertImage(imageType){
		var url = "<%=webUri%>/app/env/insertOrgImageInfo.do";
		url += "?orgId=" + document.getElementById("orgId"+imageType).value + "&imageType=" + imageType;

		orgImageWin = openWindow("orgImageWin", url, 650, 400);
    }		

        
    //수정
	function updateImage(imageType){
		var checkboxes = document.getElementsByName("chkbox"+imageType);
		var length = checkboxes.length;
		var id = "";
		for (var loop = 0; loop < length; loop++) {
			if(checkboxes[loop].checked) {
				id = checkboxes[loop].id;
				break;
			}
		}
		if(id == "") {
			if(imageType==0) alert("<spring:message code='env.seal.msg.choice.userseal'/>");
			else alert("<spring:message code='env.seal.msg.choice.orgseal'/>");
			return false;
		}
		var url = "<%=webUri%>/app/env/insertOrgImageInfo.do";
		url += "?imageId=" + id;

		orgImageWin = openWindow("orgImageWin", url, 650, 400);

	}

    //조회
	function viewOrgImage(imageId){
		var url = "<%=webUri%>/app/env/selectOrgImageInfo.do";
		url += "?imageId=" + imageId;

		orgImageWin = openWindow("orgImageWin", url, 650, 400);
	}

	function popOrgTree(type, imageType) {
		selectedImageType = imageType;
		var url = "<%=webUri%>/app/common/OrgTree.do?type=" + type + "&treetype=3";
		orgTreeWin = openWindow("orgTreeWin", url, 600, 330);
	}	

	function setDeptInfo(obj) {
		$('#orgId'+selectedImageType).val(obj.orgId);
		$('#orgName'+selectedImageType).val(obj.orgName);
		selectOrgImageList(obj.orgId, selectedImageType);
	}

	function selectOrgImageList(orgId, imageType){
		if(orgId == "") return;
		var procUrl = "<%=webUri%>/app/env/selectOrgImageList.do?orgId=" + orgId + "&imageType=" + imageType;
		var results = null;

		$.ajaxSetup({async:false});
		$.getJSON(procUrl, $('form').serialize(), function(data){
			results = data;
		});
		drawOrgImageList(results, imageType);			
	}
	
	function drawOrgImageList(orgImageList, imageType) {
		var tbl;
		tbl = $('#tblOrgImage'+imageType);
		var orgImageListLength = orgImageList.length;
		tbl.empty();

		if(orgImageListLength > 0) {
			for (var i=0; i<orgImageListLength; i++) {
				var orgImage = orgImageList[i];
				var orgImageId = orgImage.imageID;
				var orgImageName = orgImage.imageName;
				var regDate = orgImage.registrationDate;
				var disuseYN = orgImage.disuseYN;
				var disuseStr =  "<spring:message code='env.seal.status.use'/>";
				if(disuseYN) disuseStr =  "<spring:message code='env.seal.status.disuse'/>";

				var imgType = orgImage.imageType;
				var imgTypeStr =  "<spring:message code='env.seal.userseal'/>";
                if(imgType == 1)  imgTypeStr = "<spring:message code='env.seal.orgseal'/>";
                else if(imgType == 2)  imgTypeStr = "<spring:message code='env.seal.userseal.omit'/>";
                else if(imgType == 3)  imgTypeStr = "<spring:message code='env.seal.orgseal.omit'/>";
                else if(imgType == 4)  imgTypeStr = "<spring:message code='env.seal.enforce.omit'/>";
				
				var row = "";
				row = "<tr  style='background-color:#ffffff'>";
				row += "<td class='ltb_center' width='5%'><input name='chkbox"+imageType+"' type='checkbox' id='"+orgImageId+"' onClick='chkCheckBox(\""+orgImageId+"\","+imageType+")'/></td>";
				row += "<td class='ltb_center' width='15%'>"+imgTypeStr+"</td>";
				row += "<td class='ltb_center' onClick='viewOrgImage(\""+orgImageId+"\");' style='cursor:pointer'>"+orgImageName+"</td>";
				row += "<td class='ltb_center' width='15%'>"+regDate+"</td>";
				row += "<td class='ltb_center' width='15%'>"+disuseStr+"</td>";
				row += "</tr>";
				tbl.append(row);	
			}
		}
		else {
			var row = "";
			row = "<tr style='background-color:#ffffff'>";
			if(imageType==0) row += "<td height='23' align='center'><spring:message code='env.seal.msg.no.userseal'/></td>";
			else row += "<td height='23' align='center'><spring:message code='env.seal.msg.no.orgseal'/></td>";
			row += "</tr>";
			tbl.append(row);	
		}

	}
	

	function chkCheckBox(id, imageType) {
		var cbox = document.getElementById(id);
		var checked = cbox.checked;
		var checkboxes = document.getElementsByName("chkbox"+imageType);
		var length = checkboxes.length;
		for (var loop = 0; loop < length; loop++) {
			checkboxes[loop].checked = false;
		}
		cbox.checked = checked;
	}

	function clearPopup() {
		if (orgTreeWin != null && !orgTreeWin.closed) {
			orgTreeWin.close();
		}
		if (orgImageWin != null && !orgImageWin.closed) {
			orgImageWin.close();
		}
	}
	
</script>
</head>
<body onLoad="selectOrgImageList('<%= institutionId %>',1);selectOrgImageList('<%= orgId %>',0)"; onunload="clearPopup();return(false);">
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2"><acube:titleBar><%= menuTitle %></acube:titleBar></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
<%
	if(!"".equals(institutionId)) {
%>        
        <tr>
            <td colspan="2">
				<acube:buttonGroup>
               		<acube:menuButton  onclick="insertImage(1);" value="<%= buttonRegist %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="updateImage(1);" value="<%= buttonModify %>" />
				</acube:buttonGroup>
            </td>
        </tr>
        <tr>
            <acube:space between="button_content" />
        </tr>
        <tr>
        	<td>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1">	
			     <tr style="border:1px"><!-- image type -->
                     <td class="tb_tit_left" style="width:10%;">
                    	<spring:message code='env.user.table.title.institutionname'/>
                     </td>
                     <td class="tb_left_bg">
                     	<table cellpadding="0" cellspacing="0">
                     		<tr>
                     			<td width="300">
                     				<input type="text" id="orgName1" name="orgName1" class="input_read" readonly style="width:90%;" value="<%= institutionName %>"/>
                     			</td>
                     			<!--
                     			<td width="100">
                     			<% if("A".equals(permission) || "I".equals(permission)) { %>
									<acube:button onclick="popOrgTree(2,1);" value="<%= buttonInstitutionChoice %>" type="4"/>
								<% } %>
                     			</td>
                     			-->
                     		</tr>
                     	</table>
                     	<input id="orgId1" name="orgId1" type="hidden" value="<%= institutionId %>"></input>
                     </td>
                 </tr>
            </acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
			<acube:tableFrame class="" width="100%" border="0" cellpadding="0" cellspacing="1">	
                 <tr>
                     <td colspan="2"> <!-- image list -->
						<table cellpadding="2" cellspacing="1" width="100%" class="table" >
							<tr>
								<td class="ltb_head" width="5%"></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.image.type'/></td>
								<td class="ltb_head"><spring:message code='env.seal.image.name'/></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.regist.date'/></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.disuse.status'/></td>
							</tr>
						</table>
						<table id="tblOrgImage1" cellpadding="2" cellspacing="1" width="100%" class="table_body" >
							<tbody />
						</table>
                     </td>
                 </tr>
            </acube:tableFrame>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
<%
	}
%>        
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td colspan="2">
				<acube:buttonGroup>
               		<acube:menuButton  onclick="insertImage(0);" value="<%= buttonRegist %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="updateImage(0);" value="<%= buttonModify %>" />
				</acube:buttonGroup>
            </td>
        </tr>
        <tr>
            <acube:space between="button_content" />
        </tr>
        <tr>
        	<td>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1">	
			     <tr style="border:1px"><!-- image type -->
                     <td class="tb_tit_left" style="width:10%;">
                    	<spring:message code='env.user.table.title.deptname'/>
                     </td>
                     <td class="tb_left_bg">
                     	<table cellpadding="0" cellspacing="0">
                     		<tr>
                     			<td width="300">
                     				<input type="text" id="orgName0" name="orgName0" class="input_read" style="width:90%;" readonly value="<%= orgName %>"/>
                     			</td>
                     			<td width="100">
                     			<% if("A".equals(permission) || "I".equals(permission)) { %>
									<acube:button onclick="popOrgTree(2,0);" value="<%= buttonDeptChoice %>" type="4"/>
								<% } %>
                     			</td>
                     		</tr>
                     	</table>
                     	<input id="orgId0" name="orgId0" type="hidden" value="<%= orgId %>"></input>
                     </td>
                 </tr>
            </acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
			<acube:tableFrame class="" width="100%" border="0" cellpadding="0" cellspacing="1">	
                 <tr>
                     <td colspan="2"> <!-- image list -->
						<table cellpadding="2" cellspacing="1" width="100%" class="table" >
							<tr>
								<td class="ltb_head" width="5%"></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.image.type'/></td>
								<td class="ltb_head"><spring:message code='env.seal.image.name'/></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.regist.date'/></td>
								<td class="ltb_head" width="15%"><spring:message code='env.seal.disuse.status'/></td>
							</tr>
						</table>
						<table id="tblOrgImage0" cellpadding="2" cellspacing="1" width="100%" class="table_body" >
							<tbody />
						</table>
                     </td>
                 </tr>
            </acube:tableFrame>
        </tr>
    </table>
</acube:outerFrame>
</body>
</html>