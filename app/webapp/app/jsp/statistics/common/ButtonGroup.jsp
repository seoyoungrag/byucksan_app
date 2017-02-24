<%@ page import="com.sds.acube.app.list.vo.ListVO" %>
<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ButtonGroup.jsp 
 *  Description : 공통 버튼  
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
ListVO buttonVO = (ListVO) request.getAttribute("ButtonVO");
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String saveButtonAuthYn = CommonUtil.nullTrim(buttonVO.getSaveButtonAuthYn());
String printButtonAuthYn = CommonUtil.nullTrim(buttonVO.getPrintButtonAuthYn());
String msgButtonSelDept	= messageSource.getMessage("list.list.button.searchTypeSelDept" , null, langType);
String msgButtonPrint = messageSource.getMessage("list.list.button.print" , null, langType);
String msgButtonSave 	= messageSource.getMessage("list.list.button.excelSave" , null, langType);
String deptYn 			= CommonUtil.nullTrim(resultSearchVO.getDeptYn());

%>

		<tr>
			<acube:space between="title_button" />
		</tr>		
		<tr>			
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<acube:buttonGroup align="right">
							<% if(!"Y".equals(deptYn)){  %>
								<acube:menuButton id="selDept" disabledid="" onclick="javascript:selectDept();" value='<%=msgButtonSelDept %>' />
								<acube:space between="button" />
							<% } %>
							<% if(!isExtWeb){  %>
								<% if("Y".equals(printButtonAuthYn)){  %>
									<acube:menuButton id="printDoc" disabledid="" onclick="javascript:printBox();" value='<%=msgButtonPrint %>' />
									<acube:space between="button" />
								<% } %>
								<% if("Y".equals(saveButtonAuthYn)){ %>
									<acube:menuButton id="excelSaveDoc" disabledid="" onclick="javascript:excelSave();" value='<%=msgButtonSave %>' />
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