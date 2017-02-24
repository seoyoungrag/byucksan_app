<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/approval/button/button.jsp"%>
<%
FormVO formVO = (FormVO) request.getAttribute("formVO");
%>
<% if (lob000.equals(lobCode) || lob001.equals(lobCode) || lob002.equals(lobCode)) { %>	    
							<div id="beforeprocess">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
<% 		if (lob000.equals(lobCode) || lob001.equals(lobCode)) { %>
<%			if ("Y".equals(opt350)) { %>
									<td>
										<table id="standardform_beforeprocess" border='0' cellpadding='0' cellspacing='0' style='display:none;'>
											<tr>
												<acube:button onclick="appendAppDoc();return(false);" value="<%=appendItemBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="removeAppDoc();return(false);" value="<%=removeItemBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
<%			} %>

<% 			if ("Y".equals(editbodyYn)) { %>
									<acube:button onclick="openLocalForm();return(false);" value="<%=pcdocBtn%>" type="2" />
									<acube:space between="button" />
<% 			} %>
					
<%		} %>									
<%if(formVO!=null && ( formVO.getFormName().equals("CEA-0000-05 (테스트용)출장결과품의서") || formVO.getFormName().equals("CEA-0000-05 (테스트2중)출장결과품의서")  ) ){ //출장경비양식에서만 나오도록 하드코딩 함. 관리할 수 있는 구조로 가자는데 언젠간 갈 수 있겠지...%>
									<acube:button onclick="insertStatement('b');return(false);" value="전표입력" type="2" />
									<acube:space between="button" />
<%} %>
<%if(formVO!=null && ( formVO.getFormName().equals("CEB-0000-05 (테스트용)출장결과및여비지출품의서") || formVO.getFormName().equals("CEB-0000-05 (테스트2중)출장결과및여비지출품의서") ) ){ //출장경비양식에서만 나오도록 하드코딩 함. 관리할 수 있는 구조로 가자는데 언젠간 갈 수 있겠지...%>
									<acube:button onclick="insertStatement('p');return(false);" value="전표입력" type="2" />
									<acube:space between="button" />
<%} %>


									<acube:button onclick="insertAppDoc();return(false);" value="<%=submitBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="holdoffAppDoc();return(false);" value="<%=savetempBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="createOpinion();return(false);" value="의견작성" type="2" />
									<acube:space between="button" />
									<acube:button onclick="insertDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="appendAttach();return(false);" value="첨부파일" type="2" />
									<acube:space between="button" />
									<acube:button onclick="selectAppLine();return(false);" value="<%=applineBtn%>" type="2" />
									<acube:space between="button" />
<%		if (receiverFlag) { %>						
									<acube:button onclick="selectAppRecv();return(false);" value="<%=receiverBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>
<%		if ("Y".equals(opt321)) { %>							
									<acube:button onclick="selectRelatedDoc();return(false);" value="<%=relateddocBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>				
<%		if ("Y".equals(opt344)) { %>							
									<acube:button onclick="selectRelatedRule();return(false);" value="<%=relatedruleBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt348)) { %>							
									<acube:button onclick="selectCustomer();return(false);" value="<%=customerBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt334)) { %>							
									<acube:button onclick="selectPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button onclick="insertSummary();return(false);" value="<%=summaryBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
<%		if (!lob000.equals(lobCode)) { %>
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<% 	} %>
									<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
							<div id="waiting" style="display:none;">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
<% 		if (lob000.equals(lobCode) || lob001.equals(lobCode)) { %>								
<%			if ("Y".equals(opt350)) { %>
									<td>
										<table id="standardform_waiting" border='0' cellpadding='0' cellspacing='0' style='display:none;'>
											<tr>
												<acube:button value="<%=appendItemBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=removeItemBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>	
<% 			} %>								
<% /* %>									
<% 			if ("Y".equals(editbodyYn)) { %>								
									<acube:button value="<%=pcdocBtn%>" type="2" />
									<acube:space between="button" />
<% 			} %>								
<% */ %>						
<%		} %>									
									<acube:button value="<%=submitBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=savetempBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=applineBtn%>" type="2" />
									<acube:space between="button" />
<%		if (receiverFlag) { %>						
									<acube:button value="<%=receiverBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt321)) { %>							
									<acube:button value="<%=relateddocBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt344)) { %>							
									<acube:button value="<%=relatedruleBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt348)) { %>							
									<acube:button value="<%=customerBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if ("Y".equals(opt334)) { %>							
									<acube:button value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button value="<%=summaryBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
<%		if (!lob000.equals(lobCode)) { %>
									<acube:button value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>	
									<acube:button value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<% 	} %>									
									<acube:button value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
							<div id="afterprocess" style="display:none;">
								<acube:buttonGroup>
<% 	if (!isExtWeb) { %>									
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
<%		if (!lob000.equals(lobCode)) { %>
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>	
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<% 	} %>
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup> 
							</div>
<% } else { %>
							<div>
								<acube:buttonGroup align="right">
									<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<% 	if (!isExtWeb) { %>									
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
									<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%		if (false && (lol001.equals(lobCode) || lol003.equals(lobCode))) { %>
									<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>
<%	} %>									
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
<% } %>
