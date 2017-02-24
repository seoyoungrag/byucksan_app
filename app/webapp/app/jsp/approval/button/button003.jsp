<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/approval/button/button.jsp"%>
<%
String erpId = (String) request.getAttribute("erpId");
%>
<% if (lob003.equals(lobCode)) { %>	    
							<div id="beforeprocess">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>			
<% if(erpId!=null && erpId!=""){ %>
	<td>
	<table id="statement_select"  border='0' cellpadding='0' cellspacing='0'">
	<tr>
	<acube:button onclick="selectStatement();return(false);" value="전표" type="2" />
	<acube:space between="button" />
	</tr>
	</table>
	</td>
		<%		if (app100.equals(docState) && userId.equals(appDocVO.getDrafterId())) { %>
		<td>
		<table id="statement_modify"  border='0' cellpadding='0' cellspacing='0'>
		<tr>
		<acube:button onclick="insertStatement();return(false);" value="전표수정" type="2" />
		<acube:space between="button" />
		</tr>
		</table>
		</td>
		<% 			}else{ %>	
		<td>
		<table id="statement_modify"  border='0' cellpadding='0' cellspacing='0' style="display:none;">
		<tr>
		<acube:button onclick="insertStatement();return(false);" value="전표수정" type="2" />
		<acube:space between="button" />
		</tr>
		</table>
		</td>
		<%} %>
<%} %>		
					
<%		if (app110.equals(docState) && userId.equals(appDocVO.getDrafterId())) { %>
									<td>
										<table id="before_beforeredraft" border='0' cellpadding='0' cellspacing='0'>
											<tr>
<% 			if ("".equals(bizSystemCode)) { //업무시스템코드가 없는 경우 재기안 버튼 활성화 %>
												<acube:button onclick="redraftAppDoc();return(false);" value="<%=redraftBtn%>" type="2" />
												<acube:space between="button" />
<% 			} %>												
<% 	if("Y".equals(opt412) ) { //반려문서 대장등록   // jth8172 2012 신결재 TF %>
												<acube:button onclick="regReject();return(false);" value="<%=regRejectBtn%>" type="2" />
												<acube:space between="button" />
<% } %>
												<acube:button onclick="deleteAppDoc();return(false);" value="<%=deleteBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
												<% if("Y".equals(opt380)&&(docState.indexOf(app401) > -1 || docState.indexOf(app402) > -1 || docState.indexOf(app405) > -1)){ %>
												<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>"  type="2" />
												<acube:space between="button" />
												<%}%>
											</tr>
										</table>
										<table id="before_afterredraft" border='0' cellpadding='0' cellspacing='0' style="display:none;">
											<tr>
												<acube:button onclick="insertAppDoc();return(false);" value="<%=submitBtn%>" type="2" />
												<acube:space between="button" />
<% /* %>
												<acube:button onclick="holdoffAppDoc();return(false);" value="<%=savetempBtn%>" type="2" />
												<acube:space between="button" />
<% */ %>										<acube:button onclick="createOpinion();return(false);" value="의견작성" type="2" />
												<acube:space between="button" />
												<acube:button onclick="insertDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="selectAppLine();return(false);" value="<%=applineBtn%>" type="2" />
												<acube:space between="button" />
<%			if (receiverFlag) { %>						
												<acube:button onclick="selectAppRecv();return(false);" value="<%=receiverBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>												
<%			if ("Y".equals(opt321)) { %>							
												<acube:button onclick="selectRelatedDoc();return(false);" value="<%=relateddocBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt344)) { %>							
												<acube:button onclick="selectRelatedRule();return(false);" value="<%=relatedruleBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt348)) { %>							
												<acube:button onclick="selectCustomer();return(false);" value="<%=customerBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt334)) { %>							
												<acube:button onclick="selectPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%			} %>									
												<acube:button onclick="insertSummary();return(false);" value="<%=summaryBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
<%		} else { %>
<%			if (currentUserFlag  ) { %>
<%				//합의,부서합의시 찬성,반대로 결재진행 기능 추가 // jth8172 2012 신결재 TF
				// app100(기안대기)일경우도 결재 버튼이 안보여야한다.
				if(! (app100.equals(docState) || app360.equals(docState) || app361.equals(docState) || app362.equals(docState) || app365.equals(docState) || app370.equals(docState))) { %>
									<acube:button onclick="processAppDoc();return(false);" value="<%=approvalBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if (!app100.equals(docState) && !(app360.equals(docState) || app361.equals(docState) || app362.equals(docState) || app365.equals(docState) || app370.equals(docState))) { %>									
									<acube:button onclick="returnAppDoc();return(false);" value="<%=returnBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if (app360.equals(docState) || app361.equals(docState) || app362.equals(docState) || app365.equals(docState)) { %>									
									<acube:button onclick="agreeAppDoc();return(false);" value="결재" type="2" />
									<acube:space between="button" />
									<acube:button onclick="disagreeAppDoc();return(false);" value="<%=disagreeBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				// app100(기안대기)일경우 상신 버튼이 보여야한다.
				if (app100.equals(docState)) { %>
												<acube:button onclick="insertAppDoc();return(false);" value="<%=submitBtn%>" type="2" />
												<acube:space between="button" />
<%				} %>									
									<acube:button onclick="holdoffAppDoc();return(false);" value="<%=holdoffBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="createOpinion();return(false);" value="의견작성" type="2" />
									<acube:space between="button" />
									<acube:button onclick="insertDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
										<!-- added by jkkim 원문보기 기능 추가 -->
									<% if("Y".equals(opt380)&&(docState.indexOf(app401) > -1 || docState.indexOf(app402) > -1 || docState.indexOf(app405) > -1)){ %>
										<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>"  type="2"/>
										<acube:space between="button" />
									<%}%>
									<acube:button onclick="selectAppLine();return(false);" value="<%=applineBtn%>" type="2" />
									<acube:space between="button" />
<%				if (receiverFlag) { %>						
									<acube:button onclick="selectAppRecv();return(false);" value="<%=receiverBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt321)) { %>							
									<acube:button onclick="selectRelatedDoc();return(false);" value="<%=relateddocBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt344)) { %>							
									<acube:button onclick="selectRelatedRule();return(false);" value="<%=relatedruleBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt348)) { %>							
									<acube:button onclick="selectCustomer();return(false);" value="<%=customerBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt334)) { %>							
									<acube:button onclick="selectPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%				} %>									
									<acube:button onclick="insertSummary();return(false);" value="<%=summaryBtn%>" type="2" />
									<acube:space between="button" />
<%			} %>									
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
									<% if("Y".equals(opt380)&&(docState.indexOf(app401) > -1 || docState.indexOf(app402) > -1 || docState.indexOf(app405) > -1)){ %>
									<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>"  type="2" />
									<acube:space between="button" />
									<%}%>
<%	} %>									
<%	if (currentUserFlag) { %>
									<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
<%	} else { %>									
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
<%	} %>									
								</acube:buttonGroup>
							</div>
							<div id="waiting" style="display:none;">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
<%		if (app110.equals(docState)) { %>
									<td>
										<table id="after_beforeredraft" border='0' cellpadding='0' cellspacing='0'>
											<tr>
<% 			if ("".equals(bizSystemCode)) { //업무시스템코드가 없는 경우 재기안 버튼 활성화%>
												<acube:button value="<%=redraftBtn%>" type="2" />
												<acube:space between="button" />
<% 			} %>												
												<acube:button value="<%=deleteBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
										<table id="after_afterredraft" border='0' cellpadding='0' cellspacing='0' style="display:none;">
											<tr>
												<acube:button value="<%=submitBtn%>" type="2" />
												<acube:space between="button" />
<% /* %>
												<acube:button value="<%=savetempBtn%>" type="2" />
												<acube:space between="button" />
<% */ %>
												<acube:button value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button value="<%=applineBtn%>" type="2" />
												<acube:space between="button" />
<%			if (receiverFlag) { %>																		
												<acube:button value="<%=receiverBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>												
<%			if ("Y".equals(opt321)) { %>							
												<acube:button value="<%=relateddocBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt344)) { %>							
												<acube:button value="<%=relatedruleBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt348)) { %>							
												<acube:button value="<%=customerBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%			if ("Y".equals(opt334)) { %>							
												<acube:button value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%			} %>									
												<acube:button value="<%=summaryBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
<%		} else { %>
<%			if (currentUserFlag) { %>
									<acube:button value="<%=approvalBtn%>" type="2" />
									<acube:space between="button" />
<%				if (!app100.equals(docState)) { %>									
									<acube:button value="<%=returnBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if (app360.equals(docState)) { %>									
									<acube:button onclick="agreeAppDoc();return(false);" value="<%=agreeBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="disagreeAppDoc();return(false);" value="<%=disagreeBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
									<acube:button value="<%=holdoffBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=applineBtn%>" type="2" />
									<acube:space between="button" />
<%				if (receiverFlag) { %>															
									<acube:button value="<%=receiverBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>
<%				if ("Y".equals(opt321)) { %>							
									<acube:button value="<%=relateddocBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt344)) { %>							
									<acube:button value="<%=relatedruleBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt348)) { %>							
									<acube:button value="<%=customerBtn%>" type="2" />
									<acube:space between="button" />
<%				} %>									
<%				if ("Y".equals(opt334)) { %>							
									<acube:button value="<%=pubreaderBtn%>"  type="2" />
									<acube:space between="button" />
<%				} %>									
									<acube:button value="<%=summaryBtn%>" type="2" />
									<acube:space between="button" />
<%			} %>									
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
								<acube:buttonGroup>
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
									<%  if("Y".equals(opt380)&&(docState.indexOf(app401) > -1 || docState.indexOf(app402) > -1 || docState.indexOf(app405) > -1)){%>
									<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>"  type="2" />
									<acube:space between="button" />
									<%}%>
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
