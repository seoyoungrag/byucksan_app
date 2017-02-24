<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/approval/button/button.jsp"%>
<% if (lob099.equals(lobCode) && adminstratorFlag) { %>
							<div id="beforeprocess">
								<acube:buttonGroup align="right">
									<td>
										<table id="editbody1" border='0' cellpadding='0' cellspacing='0'>
											<tr>
<% 	if ("N".equals(transferYn) && "N".equals(deleteYn) && !isExtWeb) { %>
												<acube:button onclick="editBodyByAdmin();return(false);" value="<%=adminEdityBodyBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="changeBodyByAdmin();return(false);" value="<%=adminChangeBodyBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="editAttachByAdmin();return(false);" value="<%=adminEdityAttachBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="editDocInfoByAdmin();return(false);" value="<%=adminEditDocInfoBtn%>" type="2" />
												<acube:space between="button" />	
<%		if ((app200.compareTo(docState) <= 0) && (app600.compareTo(docState) > 0)) { %>
												<acube:button onclick="selectAppLineByAdmin();return(false);" value="<%=adminEditApplineBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="withdrawByAdmin();return(false);" value="<%=withdrawBtn%>" type="2" />
												<acube:space between="button" />
												<%-- <acube:button onclick="deleteAppDocByAdmin();return(false);" value="<%=deleteBtn%>" type="2" />
												<acube:space between="button" /> --%>
<% /* %>												
<%			if (receiverFlag) { %>						
												<acube:button onclick="selectAppRecvByAdmin();return(false);" value="<%=receiverBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>
<% */ %>												
<%		} %>	
												<acube:button onclick="deleteAppDocByAdmin();return(false);" value="<%=deleteBtn%>" type="2" />
												<acube:space between="button" />
<% 	} %>
<% 	if ((app600.compareTo(docState) <= 0) && !isExtWeb) { %>										
												<acube:button onclick="sendToDocByAdmin();return(false);" value="<%=adminSendToDocBtn%>" type="2" />
												<acube:space between="button" />	
<%	} %>												
												<acube:space between="button" />
												<acube:space between="button" />
												<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
<%	if ("Y".equals(opt334)) { %>							
												<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%	} %>									
												<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
												<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
												<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
												<acube:space between="button" />
<%	} %>									
												<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
										<table id="editbody2" border='0' cellpadding='0' cellspacing='0' style="display:none;">
											<tr>
<% 	if ("N".equals(transferYn) && !isExtWeb) { %>
												<acube:button onclick="confirmEditBodyByAdmin();return(false);" value="<%=adminConfirmEdityBodyBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="cancelEditBodyByAdmin();return(false);" value="<%=adminCancelEdityBodyBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=adminChangeBodyBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=adminEdityAttachBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=adminEditDocInfoBtn%>" type="2" />
												<acube:space between="button" />
<%		if ((app200.compareTo(docState) <= 0) && (app600.compareTo(docState) > 0)) { %>										
												<acube:button value="<%=withdrawBtn%>" type="2" />
												<acube:space between="button" />
<%		} %>									
<% 	} %>												
<%	if (app600.compareTo(docState) <= 0) { %>										
												<acube:button value="<%=adminSendToDocBtn%>" type="2" />
												<acube:space between="button" />	
<%	} %>												
												<acube:space between="button" />
												<acube:space between="button" />
												<acube:button value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
<%	if ("Y".equals(opt334)) { %>							
												<acube:button value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%	} %>									
												<acube:button value="<%=saveHwpBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=saveAllBtn%>" type="2" />
												<acube:space between="button" />
<%	if (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322)) { %>																
												<acube:button value="<%=savePdfBtn%>" type="2" />
												<acube:space between="button" />
<%	} %>									
												<acube:button value="<%=printBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>	
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
							<div id="waiting" style="display:none;">
								<acube:buttonGroup align="right">
<% 	if ("N".equals(transferYn) && !isExtWeb) { %>
									<acube:button value="<%=adminEdityBodyBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=adminChangeBodyBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=adminEdityAttachBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=adminEditDocInfoBtn%>" type="2" />
									<acube:space between="button" />	
<%		if ((app200.compareTo(docState) <= 0) && (app600.compareTo(docState) > 0)) { %>										
									<acube:button value="<%=adminEditApplineBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=withdrawBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=deleteBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%	} %>									
<%	if (app600.compareTo(docState) <= 0) { %>										
									<acube:button value="<%=adminSendToDocBtn%>" type="2" />
									<acube:space between="button" />	
<%	} %>									
									<acube:space between="button" />
									<acube:space between="button" />
									<acube:button value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
									<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
									<acube:space between="button" />
<%	} %>									
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
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
