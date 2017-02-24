<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.idir.org.orginfo.OrgImage" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectOrgImage.jsp 
 *  Description : 직인/서명인 조회 
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
	String orgId = (String) session.getAttribute("DEPT_ID");
	String imageId = orgImage.getImageID();
	if(imageId == null) imageId = "";
	
	int imageType = orgImage.getImageType();

	String menuTitle = messageSource.getMessage("env.option.subtitle.userseal.select" , null, langType); 
    if(imageType == 1) {
	    menuTitle = messageSource.getMessage("env.option.subtitle.orgseal.select" , null, langType); 
	}
	
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType); 
	String buttonClose = messageSource.getMessage("env.option.button.close" , null, langType); 
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title><%= menuTitle %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
		
		<script language="javascript">
<!--	
	function modify(){
		var url = "<%=webUri%>/app/env/insertOrgImageInfo.do";
		url += "?imageId=" + "<%=imageId%>";
		document.location.href = url;				
	}
	
	function _close(){
		self.close();	
	}
	
	

//-->
</script>
		
	</head>

	<body class="no_margin">	
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
				<input type="hidden" name="orgId" id="orgId">
				<input type="hidden" name="imageType" id="imageType">				
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
							<img src="<%=webUri%>/app/approval/selectOrgSeal.do?sealId=<%=imageId%>" width="<%=imageWidth%>" height="<%=imageHeight%>" >			
						<% } else { %>
							<img src="<%=webUri%>/app/ref/image/common/n_no_img.gif" border="0" />
						<% } %>					
					</td>
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.name'/></nobr></td>
                     <td colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	                     	<td class="tb_left" nowrap>
	                         	<nobr><%=orgImage.getImageName()%></nobr>
	                     	</td>
	                     </table>
                     </td>                                
                 </tr>
                  <tr>             
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.type'/></nobr></td>
                     <td class="tb_left_bg" colspan="2">
<%                     
						String imageTypeStr = messageSource.getMessage("env.seal.userseal" , null, langType);
		                if(imageType == 1) imageTypeStr = messageSource.getMessage("env.seal.orgseal" , null, langType);
		                else if(imageType == 2) imageTypeStr = messageSource.getMessage("env.seal.userseal.omit" , null, langType);
		                else if(imageType == 3) imageTypeStr = messageSource.getMessage("env.seal.orgseal.omit" , null, langType);
		                else if(imageType == 4) imageTypeStr = messageSource.getMessage("env.seal.enforce.omit" , null, langType);
%>                    
                     	<%= imageTypeStr %>
                     </td>                     
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.regist.date'/></nobr></td>
                     <td class="tb_left" style="width:50%" colspan="2">
                     	 <%=orgImage.getRegistrationDate()%>
                     </td>
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.issue.reason'/></nobr></td>
                     <td colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
		                     <td class="tb_left" nowrap>
		                        <%=orgImage.getIssueReason()%>
		                     </td>
		                  </table>
                     </td>
                 </tr>
                 <tr>                 	 
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.managed.org'/></nobr></td>
                     <td colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
		                     <td class="tb_left" nowrap>
		                         <%=orgImage.getManagedOrg()%>
		                     </td>
		                  </table>
		              </td>
                 </tr>
                 <tr>                 	 
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.size'/></nobr></td>
                     <td class="tb_left" colspan="2" nowrap>
                     	<nobr>
                    		<%=orgImage.getSizeWidth()%> X <%=orgImage.getSizeHeight()%>
						</nobr>
                     </td>
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.image.order'/></nobr></td>
                     <td class="tb_left" colspan="2" nowrap>
                         <%=orgImage.getImageOrder()%>
                     </td>
                 </tr>
                 <tr>                 	
                     <td class="tb_tit" style="width:20%"><nobr><spring:message code='env.seal.regist.remarks'/></nobr></td>
                     <td colspan="2">
	                     <table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
		                     <td class="tb_left" nowrap>
		                         <%=orgImage.getRegistrationRemarks()%>
		                     </td>
		                 </table>
		              </td>
                 </tr> 			
				
			</acube:tableFrame>
			
			<table width"100%" border="0" cellpadding="0" cellspacing="0">
				<tr height="5px">
					<td></td>
				</tr>
			</table>

<%if(orgImage.getDisuseYN()){%>
			
			<acube:tableFrame>
				<col width="30%">
				<tr>
					<td class="tb_tit"><nobr><spring:message code='env.seal.disuse.yn'/></nobr></td>
					<td class="tb_left">
						<%if(orgImage.getDisuseYN()){%>
							<input type="checkbox" id="disuseYN" name="disuseYN" checked disabled><spring:message code='env.seal.status.disuse'/>
						<%}else{ %>
							<input type="checkbox" id="disuseYN" name="disuseYN" disabled><spring:message code='env.seal.status.disuse'/>
						<%} %>	
					</td>
				</tr>
				<tr>
					<td class="tb_tit"><nobr><spring:message code='env.seal.disuse.date'/></nobr></td>
					<td class="tb_left">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
		                     <tr>
			                     <td class="tb_left" nowrap>
									<%if(orgImage.getDisuseYN()){%>
										<%=orgImage.getDisuseDate()%>
									<%}%>
								 </td>
							</tr>
						</table>
					</td>				
				</tr>
				<tr>
					<td class="tb_tit"><nobr><spring:message code='env.seal.disuse.reason'/></nobr></td>
					<td class="tb_left">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
							<tr>
			                     <td class="tb_left" nowrap>
									<%=orgImage.getDisuseReason()%>
								 </td>
							 </tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tb_tit"><nobr><spring:message code='env.seal.disuse.remarks'/></nobr></td>
					<td class="tb_left">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
							<tr>
			                     <td class="tb_left" nowrap>
									<%=orgImage.getDisuseRemarks()%>
								</td>
							</tr>
						</table>
					</td>
				</tr>			
			</acube:tableFrame>
<%} %>			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
			  		<tr>
			  			<td height="10px">
			  			</td>			  			
			  		</tr>  
			  		<tr>
			  			<td>                               
                     	<acube:buttonGroup align="right">
                     		<acube:button id="btnModify" href="#" onclick="modify();" value="<%= buttonModify %>" type="2"/>
							<acube:space between="button" />							
							<acube:button id="btnClose" href="#" onclick="_close();" value="<%= buttonClose %>" type="2" />
						</acube:buttonGroup>
						</td>
					</tr>    
			</table>	

		</acube:outerFrame>
	</body>
</html>