<%@ page import="com.sds.acube.app.list.vo.ListVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
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
 *  Class Name  : ListButtonGroup.jsp 
 *  Description : 리스트 공통 버튼  
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
String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드

ListVO listVO = (ListVO) request.getAttribute("ButtonVO");
SearchVO searchVO = (SearchVO) request.getAttribute("SearchVO");

String searchButtonAuthYn 			= CommonUtil.nullTrim(listVO.getSearchButtonAuthYn());
String printButtonAuthYn 			= CommonUtil.nullTrim(listVO.getPrintButtonAuthYn());
String saveButtonAuthYn 			= CommonUtil.nullTrim(listVO.getSaveButtonAuthYn());
String displayAppointButtonAuthYn 	= CommonUtil.nullTrim(listVO.getDisplayAppointButtonAuthYn());
String displayProgressButtonAuthYn	= CommonUtil.nullTrim(listVO.getDisplayProgressButtonAuthYn());
String rearProgressButtonAuthYn 	= CommonUtil.nullTrim(listVO.getRearProgressButtonAuthYn());
String informButtonAuthYn 			= CommonUtil.nullTrim(listVO.getInformButtonAuthYn());   // jth8172 2012 신결재 TF
String noElecDocButtonAuthYn 		= CommonUtil.nullTrim(listVO.getNoElecDocButtonAuthYn());
String stampRegistButtonAuthYn		= CommonUtil.nullTrim(listVO.getStampRegistButtonAuthYn());
String sealRegistButtonAuthYn		= CommonUtil.nullTrim(listVO.getSealRegistButtonAuthYn());
String returnButtonAuthYn 			= CommonUtil.nullTrim(listVO.getReturnButtonAuthYn());
String deleteButtonAuthYn 			= CommonUtil.nullTrim(listVO.getDeleteButtonAuthYn());
String closeButtonAuthYn			= CommonUtil.nullTrim(listVO.getCloseButtonAuthYn());
String selectButtonAuthYn			= CommonUtil.nullTrim(listVO.getSelectButtonAuthYn());
String publishEndAuthYn				= CommonUtil.nullTrim(listVO.getPublishEndAuthYn());
String registButtonAuthYn			= CommonUtil.nullTrim(listVO.getRegistButtonAuthYn());
String cancelButtonAuthYn			= CommonUtil.nullTrim(listVO.getCancelButtonAuthYn());
String modifyButtonAuthYn			= CommonUtil.nullTrim(listVO.getModifyButtonAuthYn());
String unRegistButtonAuthYn			= CommonUtil.nullTrim(listVO.getUnRegistButtonAuthYn());
String confirmButtonAuthYn			= CommonUtil.nullTrim(listVO.getConfirmButtonAuthYn());
String easyApprSearchButtonAuthYn	= CommonUtil.nullTrim(listVO.getEasyApprSearchButtonAuthYn());
String easyEnfSearchButtonAuthYn	= CommonUtil.nullTrim(listVO.getEasyEnfSearchButtonAuthYn());
String subDeptButtonAuthYn			= CommonUtil.nullTrim(listVO.getSubDeptButtonAuthYn());
String shareDeptButtonAuthYn		= CommonUtil.nullTrim(listVO.getShareDeptButtonAuthYn());
    
String listType 					= CommonUtil.nullTrim(searchVO.getLobCode());
String resultSearchDeptName			= CommonUtil.nullTrim(searchVO.getDeptName());

String msgButtonSearch 				= messageSource.getMessage("list.list.button.detailSearch" , null, langType);
String msgButtonPrint 				= messageSource.getMessage("list.list.button.print" , null, langType);
String msgButtonSave 				= messageSource.getMessage("list.list.button.excelSave" , null, langType);
String msgButtonDisplayAppoint 		= messageSource.getMessage("list.list.button.displayAppoint" , null, langType);
String msgButtonDisplayProgress		= messageSource.getMessage("list.list.button.displayProgress" , null, langType);
String msgButtonRearProgress 		= messageSource.getMessage("list.list.button.rearProgress" , null, langType);
String msgButtonInformProgress 		= messageSource.getMessage("list.list.button.informProgress" , null, langType);   // jth8172 2012 신결재 TF
String msgButtonStampRegist			= messageSource.getMessage("list.list.button.stampRegist" , null, langType);
String msgButtonSealRegist			= messageSource.getMessage("list.list.button.sealRegist" , null, langType);
String msgButtonNoElecDoc 			= messageSource.getMessage("list.list.button.noElecDoc" , null, langType);
String msgButtonReturn 				= messageSource.getMessage("list.list.button.return" , null, langType);
String msgButtonDelete 				= messageSource.getMessage("list.list.button.delete" , null, langType);
String msgButtonClose 				= messageSource.getMessage("list.list.button.close" , null, langType);
String msgButtonSelect 				= messageSource.getMessage("list.list.button.select" , null, langType);
String msgButtonPublishEnd			= messageSource.getMessage("list.list.button.publishEnd" , null, langType);
String msgButtonRegist				= messageSource.getMessage("list.list.button.add" , null, langType);
String msgButtonSelDept				= messageSource.getMessage("list.list.button.searchTypeSelDept" , null, langType);
String msgButtonNonEleAppRegist		= messageSource.getMessage("list.list.button.nonEleAppRegist" , null, langType);
String msgButtonNonEleEnfRegist		= messageSource.getMessage("list.list.button.nonEleEnfRegist" , null, langType);
String msgButtonCancel				= messageSource.getMessage("list.list.button.cancelAuditSealDoc" , null, langType);
String msgButtonModify				= "";
String msgButtonRegistAuditSealDoc 	= messageSource.getMessage("list.list.button.registAuditSealDoc" , null, langType);
String msgButtonUnregist			= messageSource.getMessage("list.list.button.unRegistDoc" , null, langType);
String msgButtonConfirm				= messageSource.getMessage("list.list.button.stampSealConfirm" , null, langType);
String msgButtonEasyApprSearch		= messageSource.getMessage("list.list.button.easyApprSearch" , null, langType);
String msgButtonEasyEnfSearch		= messageSource.getMessage("list.list.button.easyEnfSearch" , null, langType);

String lob001 = appCode.getProperty("LOB001","LOB001","LOB");
String lob003 = appCode.getProperty("LOB003","LOB003","LOB");
String lob004 = appCode.getProperty("LOB004","LOB004","LOB");
String lob009 = appCode.getProperty("LOB009","LOB009","LOB");
String lob010 = appCode.getProperty("LOB010","LOB010","LOB");
String lob014 = appCode.getProperty("LOB014","lob014","LOB");
String lob020 = appCode.getProperty("LOB020","LOB020","LOB");
String lob026 = appCode.getProperty("LOB026","LOB026","LOB");
String lob027 = appCode.getProperty("LOB027","LOB027","LOB");
String lob031 = appCode.getProperty("LOB031","LOB031","LOB");
String lol001 = appCode.getProperty("LOL001","LOL001","LOL");
String lol003 = appCode.getProperty("LOL003","LOL003","LOL");
String lol004 = appCode.getProperty("LOL004","LOL004","LOL");
String lol005 = appCode.getProperty("LOL005","LOL005","LOL");
String lol007 = appCode.getProperty("LOL007","LOL007","LOL");
String lol008 = appCode.getProperty("LOL008","LOL008","LOL");

// 작성여부
String draftYn = AppConfig.getProperty("draftYn", "Y", "systemOperation");

if(lol004.equals(listType)){
    msgButtonModify = messageSource.getMessage("list.list.button.modifyStampSealDoc" , null, langType);
}else if(lol007.equals(listType)){
    msgButtonModify = messageSource.getMessage("list.list.button.modify" , null, langType);
}else{
    msgButtonModify = messageSource.getMessage("list.list.button.modifyAuditSealDoc" , null, langType);
}

if(lol005.equals(listType)){
    msgButtonConfirm = messageSource.getMessage("list.list.button.sealRegistConfirm" , null, langType);
}
//검사부 열람함 열람범위
String opt342 = "";
if(request.getAttribute("opt342") != null){
    opt342 = (String) request.getAttribute("opt342");
}

IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String opt363 = appCode.getProperty("OPT363", "OPT363", "OPT"); // 문서책임자 문서 등록 취소 가능 여부 
opt363 = envOptionAPIService.selectOptionValue(compId, opt363);

String roleId11 = AppConfig.getProperty("role_doccharger","","role"); // 처리과 문서 담당자
boolean deptAdmin = (roleCode.indexOf(roleId11) == -1) ? false : true; 
%>

		<tr>
			<acube:space between="title_button" />
		</tr>		
		<tr>			
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>						
						<%if(lob020.equals(listType) || (lob014.equals(listType) && "1".equals(opt342)) ) { %>
							<td class="title_mini"><img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b><% if("".equals(resultSearchDeptName)) {%><spring:message code='list.list.msg.allSearchDeptName'/><%}else{%><%=resultSearchDeptName%><%} %></b></td>
						<% }else if(lob031.equals(listType)) {  %>
							<% if(!"".equals(resultSearchDeptName)) {%><td class="title_mini"><img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b><%=resultSearchDeptName%></b></td><%} %>
						<% } %>
						<% if("Y".equals(easyApprSearchButtonAuthYn) || "Y".equals(easyEnfSearchButtonAuthYn) ){ %>
							<td class="title_mini">
								<acube:buttonGroup align="left">
									<% if("Y".equals(easyApprSearchButtonAuthYn)){ %>
										<acube:menuButton id="searchEasyAppr" disabledid="" onclick="javascript:searchEasyApprDoc();" value='<%=msgButtonEasyApprSearch %>' />
										<acube:space between="button" />
									<% } %>
									<% if("Y".equals(easyEnfSearchButtonAuthYn)){ %>
										<acube:menuButton id="searchEasyEnf" disabledid="" onclick="javascript:searchEasyEnfDoc();" value='<%=msgButtonEasyEnfSearch %>' />
									<% } %>
								</acube:buttonGroup>
							</td>
						<% } %>
						<td>
							<acube:buttonGroup align="right">
							<!-- 양식기안 버튼과 상세정보 버튼표시... LOB003(수신함),LOB004(진행함),  -->
							<%if(lob003.equals(listType) || lob004.equals(listType) || lob010.equals(listType) || lob009.equals(listType)  || lob026.equals(listType) || lob027.equals(listType) ) { %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:lfn_formPop();" value='양식기안' />
							<acube:space between="button" />
							<%-- <acube:menuButton id="selDept" disabledid="" onclick="javascript:viewDocDetail();" value='상세정보' />		//상세정보 data가 나오지 않아 메뉴에서 우선 삭제 160114 한동구 수정
							<acube:space between="button" /> --%>
							<% } %>
							
							<%if(lob027.equals(listType) ) { %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:restoreDoc();" value='복구' />
							<acube:space between="button" />
							<% } %>
							
							<%if(lob020.equals(listType)|| (lob014.equals(listType) && "1".equals(opt342)) ) { %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:selectDept();" value='<%=msgButtonSelDept %>' />
							<acube:space between="button" />
							<% } %>
							<% if("Y".equals(searchButtonAuthYn)){ %>
							<acube:menuButton id="searchDoc" disabledid="" onclick="javascript:detailSearch();" value='<%=msgButtonSearch%>' />
							<input type="hidden" name="detailSearchYn" id="detailSearchYn" value="N">
							<acube:space between="button" />
							<% } %>
							<% if(lol001.equals(listType) && ("Y".equals(subDeptButtonAuthYn) || "Y".equals(shareDeptButtonAuthYn))){ %>
							<acube:menuButton id="selDept" disabledid="" onclick="javascript:selectDeptAuth();" value='<%=msgButtonSelDept %>' />
							<acube:space between="button" />
							<% } %>
							<% if(!isExtWeb){  %>
								<% if("Y".equals(confirmButtonAuthYn)){ %>
									<acube:menuButton id="stampSealConfirmDoc" disabledid="" onclick="javascript:stampSealConfirm();" value='<%=msgButtonConfirm %>' />
									<acube:space between="button" />
								<% }  %>
								<% if("Y".equals(draftYn)) { %>
									<% if("Y".equals(stampRegistButtonAuthYn)){ %>
									<acube:menuButton id="stampDoc" disabledid="" onclick="javascript:stampRegist();" value='<%=msgButtonStampRegist %>' />
									<acube:space between="button" />
									<% } %>
									<% if("Y".equals(sealRegistButtonAuthYn)){ %>
									<acube:menuButton id="sealStampDoc" disabledid="" onclick="javascript:sealRegist();" value='<%=msgButtonSealRegist %>' />
									<acube:space between="button" />
									<% } %>
								<% } %>
								<% if("Y".equals(noElecDocButtonAuthYn) && "Y".equals(draftYn)){ %>
								<% if(lol001.equals(listType)) { %>
									<%-- <acube:menuButton id="nonEleAppRegist" disabledid="" onclick="javascript:noElecAppDocRegist('LOL001');" value='<%=msgButtonNonEleAppRegist %>' />
									<acube:space between="button" />
									<acube:menuButton id="nonEleEnfRegist" disabledid="" onclick="javascript:noElecEnfDocRegist('LOL001');" value='<%=msgButtonNonEleEnfRegist %>' /> --%>
								<% }else{ %>
									<acube:menuButton id="nonEleEnfRegist" disabledid="" onclick="javascript:noElecEnfDocRegist('LOL002');" value='<%=msgButtonNonEleEnfRegist %>' />
								<% } %>
								<acube:space between="button" />
								<% } %>							
								<% if("Y".equals(returnButtonAuthYn)){ %>
								<acube:menuButton id="returnDoc" disabledid="" onclick="javascript:returnBizDoc();" value='<%=msgButtonReturn %>' />
								<acube:space between="button" />
								<% } %>
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
								<% if("Y".equals(registButtonAuthYn) && "Y".equals(draftYn)){ %>
									<% if(lol008.equals(listType)) { %>
										<acube:menuButton id="AuditSealDoc" disabledid="" onclick="javascript:registAuditSealDoc();" value='<%=msgButtonRegistAuditSealDoc %>' />
										<acube:space between="button" />
									<% }else{  %>
										<acube:menuButton id="dailyAuditDoc" disabledid="" onclick="javascript:registDailyAuditDoc('LOL007');" value='<%=msgButtonRegist %>' />
										<acube:space between="button" />
									<% } %>
								<% } %>							
								<% if("Y".equals(publishEndAuthYn)) { %>									
									<acube:menuButton id="publishEnd" disabledid="" onclick="javascript:publishEnd();" value='<%=msgButtonPublishEnd %>' />
									<acube:space between="button" />									
								<% }  %>
								<% if("Y".equals(modifyButtonAuthYn)) { %>
									<% if(lol004.equals(listType) || lol005.equals(listType)){ %>
										<acube:menuButton id="modifySeal" disabledid="" onclick="javascript:modifySealDoc();" value='<%=msgButtonModify %>' />									
									<%}else if(lol007.equals(listType)){ %>
										<acube:menuButton id="modifyDailyAudit" disabledid="" onclick="javascript:modifyDailyAudit();" value='<%=msgButtonModify %>' />
									<%}else{ %>
										<acube:menuButton id="modifyAuditSeal" disabledid="" onclick="javascript:modifyAuditSealDoc();" value='<%=msgButtonModify %>' />										
									<% } %>
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(cancelButtonAuthYn)) { %>
									<acube:menuButton id="cancel" disabledid="" onclick="javascript:cancelDoc();" value='<%=msgButtonCancel %>' />
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(deleteButtonAuthYn)){ %>
									<% if(lob001.equals(listType)) { %>
										<acube:menuButton id="deleteTempDoc" disabledid="" onclick="javascript:deleteTemporary();" value='<%=msgButtonDelete %>' />										
									<%}else if(lol007.equals(listType)) { %>
										<acube:menuButton id="deleteDailyAuditDoc" disabledid="" onclick="javascript:deleteDailyAuditDoc();" value='<%=msgButtonDelete %>' />
									<%}else if(lol004.equals(listType) || lol005.equals(listType)) { %>
										<acube:menuButton id="deleteSealDoc" disabledid="" onclick="javascript:deleteSealDoc();" value='<%=msgButtonDelete %>' />
									<%}else if(lob027.equals(listType)) { %> <!-- 폐기함 이므로 문서를 완전 삭제한다. -->
										<acube:menuButton id="deleteComplate" disabledid="" onclick="javascript:deleteComplate();" value='완전삭제' />
									<%}else{ %>
										<acube:menuButton id="deleteAppDoc" disabledid="" onclick="javascript:deleteAppDoc();" value='<%=msgButtonDelete %>' />
									<% } %>
									<acube:space between="button" />
								<% } %>	
								<% if("Y".equals(unRegistButtonAuthYn)){ %>
									<acube:menuButton id="unRegistDoc" disabledid="" onclick="javascript:unRegistDoc();" value='<%=msgButtonUnregist %>' />
									<acube:space between="button" />
								<% } %>															
								<% if("Y".equals(printButtonAuthYn)){ %>
									<acube:menuButton id="printDoc" disabledid="" onclick="javascript:printBox();" value='<%=msgButtonPrint %>' />
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(saveButtonAuthYn)){ %>
									<acube:menuButton id="excelSaveDoc" disabledid="" onclick="javascript:excelSaveBox();" value='엑셀' />
									<acube:space between="button" />	
								<% } %>							
								<% if("Y".equals(displayAppointButtonAuthYn)){ %>
									<!--<acube:menuButton id="displayAppoint" disabledid="" onclick="javascript:displayAppoint();" value='msgButtonDisplayAppoint' />
									<acube:space between="button" /> -->
								<% } %>
								<% if("Y".equals(displayProgressButtonAuthYn)){ %>
									<acube:menuButton id="displayProgress" disabledid="" onclick="javascript:displayProgress();" value='<%=msgButtonDisplayProgress %>' />
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(rearProgressButtonAuthYn)){ %>
									<acube:menuButton id="rearProgress" disabledid="" onclick="javascript:rearProgress();" value='<%=msgButtonRearProgress %>' />
									<acube:space between="button" />
								<% } %>									
								<% if("Y".equals(informButtonAuthYn)){     // jth8172 2012 신결재 TF %>
									<acube:menuButton id="informProgress" disabledid="" onclick="javascript:informProgress();" value='<%=msgButtonInformProgress %>' />
									<acube:space between="button" />
								<% } %>									
							<% } %>																	
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>