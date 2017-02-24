<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/approval/button/button.jsp"%>

<%
	// 문서편집기 사용 값 조회
	String opt428Data = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT428", "OPT428", "OPT"));

	FileVO formBodyVO = (FileVO) request.getAttribute("bodyfile");
	String strBodyType = CommonUtil.getFileExtentsion(formBodyVO.getFileName());

	// 현재 환경설정에서 문서편집기를 선택한 문서일겨우만 참조기안 버튼을 보일수 있도록 설정한다.
	boolean isReferDraftUse = false;
	if ("hwp".equals(strBodyType)) {
		if (opt428Data != null && (opt428Data.indexOf("I1:Y") >= 0)) {
			isReferDraftUse = true;	
		}		
	} else if ("doc".equals(strBodyType)) {
		if (opt428Data != null && (opt428Data.indexOf("I2:Y") >= 0)) {
			isReferDraftUse = true;	
		}		
	} else if ("html".equals(strBodyType)) {
		if (opt428Data != null && (opt428Data.indexOf("I3:Y") >= 0)) {
			isReferDraftUse = true;	
		}
	}
%>

<%
String erpId = (String) request.getAttribute("erpId");
%>

<% if (lob009.equals(lobCode) || lob010.equals(lobCode)) { %>
							<div id="beforeprocess">
								<acube:buttonGroup align="right">
<% 	if (!isExtWeb) { %>									
									<td>
										<table id="before_beforereferdraft" border='0' cellpadding='0' cellspacing='0'>
											<tr>		
<% if(erpId!=null && erpId!=""){ %>
	<acube:button onclick="insertStatement();return(false);" value="전표" type="2" />
	<acube:space between="button" />
<%} %>						
<%		if ("N".equals(transferYn)) { %>
			<% 			if ("".equals(bizSystemCode) && isReferDraftUse) { //업무시스템코드가 없는 경우 참조기안 활성화%>
												<acube:button onclick="referdraftAppDoc();return(false);" value="<%=referdraftBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>												
<%		} %>												
												<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
<%		if ("Y".equals(opt334)) { %>							
												<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%		} %>
											</tr>
										</table>
										<table id="before_afterreferdraft" border='0' cellpadding='0' cellspacing='0' style="display:none;">
											<tr>
												<acube:button onclick="insertAppDoc();return(false);" value="<%=submitBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="holdoffAppDoc();return(false);" value="<%=savetempBtn%>" type="2" />
												<acube:space between="button" />
												<acube:button onclick="createOpinion();return(false);" value="의견작성" type="2" />
												<acube:space between="button" />		
												<acube:button onclick="insertDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
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
												<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%		} %>									
												<acube:button onclick="insertSummary();return(false);" value="<%=summaryBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
									<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
									<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if (false && lob009.equals(lobCode)) { %>
									<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>
									<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%	} else { %>									
									<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
									<acube:space between="button" />
<%	} %>									
									<acube:button onclick="exitAppDoc();return(false);" value="<%=closeBtn%>" type="2" />
								</acube:buttonGroup>
							</div>
							<div id="waiting" style="display:none;">
								<acube:buttonGroup align="right">
									<td>
<% 	if (!isExtWeb) { %>									
										<table id="after_beforereferdraft" border='0' cellpadding='0' cellspacing='0'>
											<tr>
<%		if ("N".equals(transferYn)) { %>
			<% 			if ("".equals(bizSystemCode)) { ////업무시스템코드가 없는 경우 참조기안 활성화%>
												<acube:button value="<%=referdraftBtn%>" type="2" />
												<acube:space between="button" />
<%			} %>									
<%		} %>									
												<acube:button value="<%=docinfoBtn%>" type="2" />
												<acube:space between="button" />
<%		if ("Y".equals(opt334)) { %>							
												<acube:button value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%		} %>									
											</tr>
										</table>
										<table id="after_afterreferdraft" border='0' cellpadding='0' cellspacing='0' style="display:none;">
											<tr>
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
												<acube:button onclick="listPubReader();return(false);" value="<%=pubreaderBtn%>"  type="2" />
												<acube:space between="button" />
<%		} %>									
												<acube:button value="<%=summaryBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
									<acube:button value="<%=saveHwpBtn%>" type="2" />
									<acube:space between="button" />
									<acube:button value="<%=saveAllBtn%>" type="2" />
									<acube:space between="button" />
<%		if (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322)) { %>																
									<acube:button value="<%=savePdfBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if (lob009.equals(lobCode)) { %>
									<acube:button value="<%=sendMailBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>
									<acube:button value="<%=printBtn%>" type="2" />
									<acube:space between="button" />
<%	} else { %>									
									<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" />
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
<%		if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
									<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>									
<%		if (false && lob009.equals(lobCode)) { %>
									<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
									<acube:space between="button" />
<%		} %>
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
