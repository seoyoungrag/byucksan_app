<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/approval/button/button.jsp"%>
<%
String erpId = (String) request.getAttribute("erpId");
%>
<% if (lob004.equals(lobCode)) { %>
							<div id="beforeprocess">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
<%		if (withdrawFlag) { %>								
									<acube:button onclick="withdrawAppDoc();return(false);" value="<%=withdrawBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>	
<% if(erpId!=null && erpId!=""){ %>
	<acube:button onclick="insertStatement();return(false);" value="전표" type="2" />
	<acube:space between="button" />
<%} %>	

									<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<%		if ("Y".equals(opt334)) { %>								
									<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%	} else { %>									
									<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<%		if ("Y".equals(opt334)) { %>							
									<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%		} %>									
<%	} %>									
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
							<div id="waiting" style="display:none;">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
<%		if (withdrawFlag) { %>								
									<acube:button value="<%=withdrawBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<%		if ("Y".equals(opt334)) { %>							
									<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%		} %>									
									<acube:button value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%	} else { %>									
									<acube:button value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<%	} %>									
									<acube:button value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div> 
							<div id="afterprocess" style="display:none;">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%	} %>									
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
