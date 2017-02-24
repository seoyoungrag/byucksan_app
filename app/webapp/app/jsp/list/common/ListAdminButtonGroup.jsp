<%@ page import="com.sds.acube.app.list.vo.ListVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListAdminButtonGroup.jsp 
 *  Description : 리스트 공통 버튼(관리자)  
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 05 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
String compId = (String) session.getAttribute("COMP_ID");	// 회사 ID
 
ListVO listVO = (ListVO) request.getAttribute("ButtonVO");
SearchVO searchVO = (SearchVO) request.getAttribute("SearchVO");


String searchButtonAuthYn 			= CommonUtil.nullTrim(listVO.getSearchButtonAuthYn());
String printButtonAuthYn 			= CommonUtil.nullTrim(listVO.getPrintButtonAuthYn());
String saveButtonAuthYn 			= CommonUtil.nullTrim(listVO.getSaveButtonAuthYn());
String displayAppointButtonAuthYn 	= CommonUtil.nullTrim(listVO.getDisplayAppointButtonAuthYn());
String displayProgressButtonAuthYn	= CommonUtil.nullTrim(listVO.getDisplayProgressButtonAuthYn());
String rearProgressButtonAuthYn 	= CommonUtil.nullTrim(listVO.getRearProgressButtonAuthYn());
String noElecDocButtonAuthYn 		= CommonUtil.nullTrim(listVO.getNoElecDocButtonAuthYn());
String stampRegistButtonAuthYn		= CommonUtil.nullTrim(listVO.getStampRegistButtonAuthYn());
String sealRegistButtonAuthYn		= CommonUtil.nullTrim(listVO.getSealRegistButtonAuthYn());
String returnButtonAuthYn 			= CommonUtil.nullTrim(listVO.getReturnButtonAuthYn());
String deleteButtonAuthYn 			= CommonUtil.nullTrim(listVO.getDeleteButtonAuthYn());
String closeButtonAuthYn			= CommonUtil.nullTrim(listVO.getCloseButtonAuthYn());
String selectButtonAuthYn			= CommonUtil.nullTrim(listVO.getSelectButtonAuthYn());
String publishEndAuthYn				= CommonUtil.nullTrim(listVO.getPublishEndAuthYn());
String registButtonAuthYn			= CommonUtil.nullTrim(listVO.getRegistButtonAuthYn());
String modifyButtonAuthYn			= CommonUtil.nullTrim(listVO.getModifyButtonAuthYn());
String unRegistButtonAuthYn			= CommonUtil.nullTrim(listVO.getUnRegistButtonAuthYn());

String listType 					= CommonUtil.nullTrim(searchVO.getLobCode());
String resultSearchDeptName			= CommonUtil.nullTrim(searchVO.getDeptName());

String msgButtonSearch 			= messageSource.getMessage("list.list.button.detailSearch" , null, langType);
String msgButtonPrint 			= messageSource.getMessage("list.list.button.print" , null, langType);
String msgButtonSave 			= messageSource.getMessage("list.list.button.excelSave" , null, langType);
String msgButtonDisplayAppoint 	= messageSource.getMessage("list.list.button.displayAppoint" , null, langType);
String msgButtonDisplayProgress	= messageSource.getMessage("list.list.button.displayProgress" , null, langType);
String msgButtonRearProgress 	= messageSource.getMessage("list.list.button.rearProgress" , null, langType);
String msgButtonStampRegist		= messageSource.getMessage("list.list.button.stampRegist" , null, langType);
String msgButtonSealRegist		= messageSource.getMessage("list.list.button.sealRegist" , null, langType);
String msgButtonNoElecDoc 		= messageSource.getMessage("list.list.button.noElecDoc" , null, langType);
String msgButtonReturn 			= messageSource.getMessage("list.list.button.return" , null, langType);
String msgButtonDelete 			= messageSource.getMessage("list.list.button.delete" , null, langType);
String msgButtonClose 			= messageSource.getMessage("list.list.button.close" , null, langType);
String msgButtonSelect 			= messageSource.getMessage("list.list.button.select" , null, langType);
String msgButtonPublishEnd		= messageSource.getMessage("list.list.button.publishEnd" , null, langType);
String msgButtonRegist			= messageSource.getMessage("list.list.button.add" , null, langType);
String msgButtonSelDept			= messageSource.getMessage("list.list.button.searchTypeSelDept" , null, langType);
String msgButtonDeleteHistory	= messageSource.getMessage("list.list.button.deleteHistory" , null, langType);
String msgButtonUnregist		= messageSource.getMessage("list.list.button.unRegistDoc" , null, langType);
String msgButtonReregist		= messageSource.getMessage("list.list.button.reRegistDoc" , null, langType);
String msgButtonModify			= "";
String resutnButtonScript		= "";
String msgButtonSelInstitution  =messageSource.getMessage("list.list.button.searchTypeSelInstitution" , null , langType);

String lob091 = appCode.getProperty("LOB091","LOB091","LOB");
String lob092 = appCode.getProperty("LOB092","LOB092","LOB");
String lob095 = appCode.getProperty("LOB095","LOB095","LOB");
String lob096 = appCode.getProperty("LOB096","LOB096","LOB");
String lob097 = appCode.getProperty("LOB097","LOB097","LOB");
String lob099 = appCode.getProperty("LOB099","LOB099","LOB");
String lol007 = appCode.getProperty("LOL007","LOL007","LOL");
String lol094 = appCode.getProperty("LOL094","LOL094","LOL");
String lol095 = appCode.getProperty("LOL095","LOL095","LOL");

if(lol094.equals(listType)){
    msgButtonModify = messageSource.getMessage("list.list.button.modifyStampSealDoc" , null, langType);
}else{
    msgButtonModify = messageSource.getMessage("list.list.button.modifyAuditSealDoc" , null, langType);
}

if(lob091.equals(listType)){
    msgButtonReturn = messageSource.getMessage("list.list.button.sendImpossible" , null, langType);
    resutnButtonScript = "javascript:sendImpossibleDoc();";
}else{
    resutnButtonScript = "javascript:returnBizDoc();";
}
%>

		<tr>
			<acube:space between="title_button" />
		</tr>		
		<tr>			
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<%if(lob099.equals(listType) || lol094.equals(listType) || lob095.equals(listType) || lob091.equals(listType)|| lob092.equals(listType)) { %>
							<td class="ltb_left"><img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b><% if("".equals(resultSearchDeptName)) {%><spring:message code='list.list.msg.allSearchDeptName'/><%}else{%><%=resultSearchDeptName%><%} %></b></td>
						<% }  %>
						<td>			
							<acube:buttonGroup align="right">			
							<%if(lob099.equals(listType) || lol094.equals(listType) || lob095.equals(listType) || lob091.equals(listType)) { %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:selectDept();" value='<%=msgButtonSelDept %>' />
							<acube:space between="button" />
							<% } %>
							<%if(lob092.equals(listType)) { %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:selectInstitution();" value='<%=msgButtonSelInstitution %>' />
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(searchButtonAuthYn)){ %>
							<acube:menuButton id="searchDoc" disabledid="" onclick="javascript:detailSearch();" value='<%=msgButtonSearch%>' />
							<input type="hidden" name="detailSearchYn" id="detailSearchYn" value="N">
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(displayAppointButtonAuthYn)){ %>
							<acube:menuButton id="displayAppoint" disabledid="" onclick="javascript:displayAppoint();" value='<%=msgButtonDisplayAppoint %>' />
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(displayProgressButtonAuthYn)){ %>
							<acube:menuButton id="displayProgress" disabledid="" onclick="javascript:displayProgress();" value='<%=msgButtonDisplayProgress %>' />
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(rearProgressButtonAuthYn)){ %>
							<acube:menuButton id="rearProgress" disabledid="" onclick="javascript:rearProgress();" value='<%=msgButtonRearProgress %>' />
							<acube:space between="button" />
							<% } %>
							<% if(!isExtWeb){  %>
								<% if("Y".equals(stampRegistButtonAuthYn)){ %>
								<acube:menuButton id="stampDoc" disabledid="" onclick="javascript:stampRegist();" value='<%=msgButtonStampRegist %>' />
								<acube:space between="button" />
								<% } %>							
								<% if("Y".equals(sealRegistButtonAuthYn)){ %>
								<acube:menuButton id="sealStampDoc" disabledid="" onclick="javascript:sealRegist();" value='<%=msgButtonSealRegist %>' />
								<acube:space between="button" />
								<% } %>							
								<% if("Y".equals(noElecDocButtonAuthYn)){ %>
									<% if("LOL001".equals(listType)) { %>
										<acube:menuButton id="noElecDoc" disabledid="" onclick="javascript:showNoElecDocRegist();" value='<%=msgButtonNoElecDoc %>' />
									<% }else{ %>
										<acube:menuButton id="noElecEnfDoc" disabledid="" onclick="javascript:noElecEnfDocRegist('LOL002');" value='<%=msgButtonNoElecDoc %>' />
									<% } %>
									<acube:space between="button" />
								<% } %>							
							<% } %>
							<% if("Y".equals(returnButtonAuthYn)){ %>								
							<acube:menuButton id="returnDoc" disabledid="" onclick="<%=resutnButtonScript %>" value='<%=msgButtonReturn %>' />								
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(closeButtonAuthYn)){ %>
							<acube:menuButton id="closeWin" disabledid="" onclick="javascript:closeWindow();" value='<%=msgButtonClose %>' />
							<acube:space between="button" />
							<% } %>
							<% if(!isExtWeb){  %>
								<% if("Y".equals(selectButtonAuthYn)){ %>
								<acube:menuButton id="relationDoc" disabledid="" onclick="javascript:selRelationDocRegist();" value='<%=msgButtonSelect %>' />
								<acube:space between="button" />
								<% } %>
								<% if("Y".equals(registButtonAuthYn)){ %>
									<%if(!lob099.equals(listType)){ %>										
										<acube:menuButton id="dailyAuditDoc" disabledid="" onclick="javascript:registDailyAuditDoc();" value='<%=msgButtonRegist %>' />
										<acube:space between="button" />
									<% }  %>
								<% } %>
							<% }  %>
							<% if("Y".equals(publishEndAuthYn)) { %>
							<acube:menuButton id="publishEnd" disabledid="" onclick="javascript:publishEnd();" value='<%=msgButtonPublishEnd %>' />
							<acube:space between="button" />
							<% }  %>
							<% if("Y".equals(modifyButtonAuthYn)) { %>
									<% if(lol094.equals(listType) || lol095.equals(listType)){ %>
										<acube:menuButton id="modifySeal" disabledid="" onclick="javascript:modifySealDoc();" value='<%=msgButtonModify %>' />									
										<acube:space between="button" />
									<% } %>
							<% }  %>							
							<% if("Y".equals(deleteButtonAuthYn)){ %>
								<%if(lol007.equals(listType)) { %>
									<acube:menuButton id="deleteDailyAuditDoc" disabledid="" onclick="javascript:deleteDailyAuditDoc();" value='<%=msgButtonDelete %>' />
								<%}else if(lob095.equals(listType) || lob096.equals(listType) || lob097.equals(listType)) { %>
									<acube:menuButton id="deleteHistory" disabledid="" onclick="javascript:deleteHistory();" value='<%=msgButtonDeleteHistory %>' />								
								<%}else if(lol094.equals(listType) || lol095.equals(listType)){ %>
									<acube:menuButton id="deleteSealDoc" disabledid="" onclick="javascript:deleteSealDoc();" value='<%=msgButtonDelete %>' />
								<%}else{ %>
									<acube:menuButton id="deleteAppDoc" disabledid="" onclick="javascript:deleteAppDoc();" value='<%=msgButtonDelete %>' />
								<% } %>
								<acube:space between="button" />
							<% } %>								
							<%if(lob099.equals(listType) && "Y".equals(unRegistButtonAuthYn)){ %>
								<acube:menuButton id="unRegistDoc" disabledid="" onclick="javascript:unRegistDoc();" value='<%=msgButtonUnregist %>' />
								<acube:space between="button" />
							<% }  %>
							<% if("Y".equals(registButtonAuthYn)){ %>
								<%if(lob099.equals(listType) && "Y".equals(unRegistButtonAuthYn)){ %>
									<acube:menuButton id="reRegistDoc" disabledid="" onclick="javascript:reRegistDoc();" value='<%=msgButtonReregist %>' />
									<acube:space between="button" />
								<%} %>
							<% } %>
							<% if(!isExtWeb){  %>
								<% if("Y".equals(printButtonAuthYn)){ %>
									<acube:menuButton id="printDoc" disabledid="" onclick="javascript:printBox();" value='<%=msgButtonPrint %>' />
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(saveButtonAuthYn)){ %>
									<acube:menuButton id="excelSaveDoc" disabledid="" onclick="javascript:excelSaveBox();" value='<%=msgButtonSave %>' />
									<acube:space between="button" />	
								<% } %>
							<% } %>																		
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>