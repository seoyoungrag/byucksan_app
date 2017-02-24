<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/approval/button/button.jsp"%>
<% if (lob024.equals(lobCode)) { %>
							<div>
							<acube:buttonGroup align="right">
	<% 	if (!isExtWeb) { %>									
<%	
			String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보   jth8172 2012 신결재 TF
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			boolean readafterFlag = false;
			if (appLineVOs != null) {
				int lineCount = appLineVOs.size();
				for (int loop = lineCount - 1; loop > 0; loop--) {
				    AppLineVO appLineVO = appLineVOs.get(loop);
				    if (userId.equals(appLineVO.getApproverId()) && art055.equals(appLineVO.getAskType())) {
						if ("9999-12-31 23:59:59".equals(appLineVO.getProcessDate())) {
						    readafterFlag = true;
						}
						break;
				    }
				}
			}
%>
<% 		if (readafterFlag) { %>								
									<td>
										<table id="beforeprocess" border='0' cellpadding='0' cellspacing='0'>
											<tr>
												<acube:button onclick="informAppDoc();return(false);" value="<%=informBtn%>" type="2" />
												<acube:space between="button" />
											</tr>
										</table>
									</td>
<% 		} %>


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
<%		if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
									<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" />
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
