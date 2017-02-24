<%@ page import="com.sds.acube.app.statistics.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="java.util.HashMap,
				 java.util.Map" 
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.list.util.ListUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : Search.jsp 
 *  Description : 검색  
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
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
String opt188 = AppConfig.getProperty("OPT188", "OPT188", "OPT");
String opt189 = AppConfig.getProperty("OPT189", "OPT189", "OPT");
String opt190 = AppConfig.getProperty("OPT190", "OPT190", "OPT");

//==============================================================================
// 검색 결과 값
SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");

String cPageStr = request.getParameter("cPage");

int CPAGE = 1;
if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);

String resultStartDate		= CommonUtil.nullTrim(resultSearchVO.getStartDate());
String resultEndDate		= CommonUtil.nullTrim(resultSearchVO.getEndDate());
String deptYn				= CommonUtil.nullTrim(resultSearchVO.getDeptYn());
String resultDeptId			= ListUtil.TransReplace(CommonUtil.nullTrim(resultSearchVO.getSearchDeptId()),"'","");
String resultDeptName		= CommonUtil.nullTrim(resultSearchVO.getSearchDeptName());
String resultUserId			= ListUtil.TransReplace(CommonUtil.nullTrim(resultSearchVO.getSearchUserId()),"'","");
String resultUserName		= CommonUtil.nullTrim(resultSearchVO.getSearchUserName());
String resultListType		= CommonUtil.nullTrim(resultSearchVO.getListType());
String resultDocType		= CommonUtil.nullTrim(resultSearchVO.getSearchDocType());
String resultSearchWord		= CommonUtil.nullTrim(resultSearchVO.getSearchWord());

String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);

String startDateId = DateUtil.getFormattedDate(beforeStartDate+" 00:00:00", "yyyyMMdd");
String endDateId = DateUtil.getFormattedDate(beforeEndDate+" 00:00:00", "yyyyMMdd");

String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);


%>
		
		
		<tr>
			<acube:space between="button_search" />
		</tr>
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			<td>
								<form name="listSearch" id="listSearch" method="post" style="margin:0px">
				<acube:tableFrame type="search">
					<tr class="search">
						<td width="10"></td>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="455" height="24">
													<table width="100%"  border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="100%">
<%			              
	if (opt188.equals(resultListType) || opt189.equals(resultListType) || opt190.equals(resultListType)) {
%> 
																<table width="100%" border="0" cellspacing="0" cellpadding="0">
																	<tr>
<%			              
		if (opt189.equals(resultListType)) {																				
%> 
																		<td width="150" class="search_title">
																			<select name="searchDocType" class="select_9pt" style="width:100%;">
																				<option value="ALL" <%="ALL".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeAll"/></option>
																				<option value="RECV" <%="RECV".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeRecv"/></option>
																				<option value="LINE" <%="LINE".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeLine"/></option>
																			</select>
																		</td>
														                <td width="5"></td>
<%			              
		} if (opt190.equals(resultListType)) {																				
%> 
																		<td width="120" class="search_title">
																			<select name="searchDocType" class="select_9pt" style="width:100%;">
																				<option value="ALL" <%="ALL".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeAll"/></option>
																				<option value="APP" <%="APP".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeApp"/></option>
																				<option value="ENF" <%="ENF".equals(resultDocType) ? " selected" : ""%>><spring:message code="statistics.header.searchTypeEnf"/></option>
																			</select>
																		</td>
														                <td width="5"></td>
<%			              
		}																				
%> 
																		<td width="120" class="search_title">
																			<select name="searchType" class="select_9pt" style="width:100%;">
																				<option value="searchDeptName" <%="".equals(CommonUtil.nullTrim(resultUserName)) ? " selected" : ""%>><spring:message code="statistics.header.deptName"/></option>
<%			              
		if (opt190.equals(resultListType)) {																				
%> 
																				<option value="searchUserName" <%=!"".equals(CommonUtil.nullTrim(resultUserName)) ? " selected" : ""%>><spring:message code="statistics.header.userName"/></option>
<%			              
		}																				
%> 
																			</select>
																		</td>
														                <td width="5"></td>
																		<td width="300" class="search">
															                <div id="searchWordTd" style="display:block">
															                	<input name="searchWord" type="text" class="input" style="width:100%" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearch();}" >
															                </div>
																		</td>
			                											<td width="15">&nbsp;</td>
																		<td width="58" align="right"><acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /></td>
																	</tr>
																</table>
<%
	} else if (!"".equals(resultDocType)) {
%>
																<table border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td width="50" class="search_title"><spring:message code="statistics.header.title"/></td>
																		<td width="10"></td>
																		<td width="400" id="searchWordTd">
																			<input type="text" class="input" name="searchWord" id="searchWord" maxlength="50" style="width: 100%;" value="<%=resultSearchWord%>" onkeydown="if(event.keyCode==13){goSearch();}" >
																		</td>	
																		<td width="20">
																	        <input name="searchDocType" id="searchDocType"  type="hidden" value="<%=resultDocType%>">
																		</td>
																		<td>
																			<acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" />
																		</td>
																	</tr>					
																</table>
<%
	} else {
%>
																<table width="100%" border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<td width="60" class="search_title"><spring:message code="list.list.button.period"/></td>
																		<td width="300">
																			<table border="0" cellspacing="0" cellpadding="0">
																				<tr class="search">
																					<td>
																						<input type="text" class="input_read" name="startDate" id="startDate" readonly size="11" value="<%= resultStartDate %>">
																						<img id="calendarBTN1" name="calendarBTN1" 
																							src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
																							align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
																							onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 
																							'<%= dateFormat %>');">
																						<input type="hidden" name="startDateId" id="startDateId" value="<%= startDateId %>">
																							&nbsp;~&nbsp;
																						<input type="text" class="input_read" name="endDate" id="endDate" readonly size="11" value="<%= resultEndDate %>">
																						<img id="calendarBTN2" name="calendarBTN2" 
																							src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
																							align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
																							onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN2', 
																							'<%= dateFormat %>');">
																						<input type="hidden" name="endDateId" id="endDateId" value="<%= endDateId %>">
																					</td>
																				</tr>
																			</table>
																		</td>
			                											<td width="15">&nbsp;</td>
																		<td width="58" align="right"><acube:button onclick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /></td>
																	</tr>
																</table>
<%			              
	}
%>  
															</td>
														</tr>
													</table>
												</td>
											</tr>     
										</table>
									</td>
								</tr>     
			    			</table>    
			    		</td>
			    		<td width="10"></td>
			  		</tr>
  				</acube:tableFrame>
	  			<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">
				<input type="hidden" name="excelExportYn" id="excelExportYn" value="N">		
				<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N">
				<input type="hidden" name="deptYn" id="deptYn" value="<%=deptYn%>">
				<input type="hidden" name="searchDeptId" id="ListFormDeptId" value="<%=resultDeptId %>">
				<input type="hidden" name="searchDeptName" id="ListFormDeptName" value="<%=resultDeptName %>">
				<input type="hidden" name="searchUserId" id="ListFormUserId" value="<%=resultUserId %>">
				<input type="hidden" name="searchUserName" id="ListFormUserName" value="<%=resultUserName %>">
				</form>
			</td>
		</tr>