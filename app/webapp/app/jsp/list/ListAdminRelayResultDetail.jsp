<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.relay.vo.RelayExceptionVO" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ page import="com.sds.acube.app.common.vo.OrganizationVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminRelayAckResultDoc.jsp 
 *  Description : 문서유통 응답이력 상세 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김상태
 *  @since 2012. 05. 04 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	IOrgService orgService = (IOrgService)ctx.getBean("orgService");
	RelayExceptionVO results = (RelayExceptionVO) request.getAttribute("relayExceptionVO");
	
	@SuppressWarnings("unchecked")
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("fileVOs");
	fileVOs.get(0).setFileType("AFT004");
	
	String relayKind = "";
	OrganizationVO orgInfo = orgService.selectOrganization(results.getSendDeptId());

	if(orgInfo != null) {
		relayKind = messageSource.getMessage("list.relay.title.headerKind.send" , null, langType);
	} else {
		relayKind = messageSource.getMessage("list.relay.title.headerKind.receive" , null, langType);
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
		<title><spring:message code="list.list.title.listAdminRelayResultDetail"/></title>
		<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
		<jsp:include page="/app/jsp/common/filemanager.jsp" />
		<jsp:include page="/app/jsp/common/common.jsp" />
		<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
		<script type="text/javascript">
		
			$(document).ready(function() { loadAtt(); });
		
			//첨부 파일 불러오기
			function loadAtt(){
				initializeFileManager();
				loadAttach($("#attachFile").val(),false);
			}

			function onloadResize(){
				if ( navigator.appName=="Netscape" ) {
					winW = window.innerWidth;
					winH = window.innerHeight;
				}
				if ( navigator.appName.indexOf("Microsoft") != -1 ) {
					winW = document.body.scrollWidth;
					winH = document.body.scrollHeight;
				}
				sizeToW = 0;
				sizeToH = 0;
				
				if ( winW > 1024 ) { //1024은 제한하고자 하는 가로크기
				     sizeToW = 1024 - document.body.clientWidth;
				} else if ( Math.abs(document.body.clientWidth - winW ) > 3 ) {
				     sizeToW = winW - document.body.clientWidth;
				}
				
				if ( winH > 768 ) {  //768은 제한하고자 하는 세로크기
				     szeToH = 768 - document.body.clientHeight;
				} else if ( Math.abs(document.body.clientHeight - winH) > 4 ) {
				     sizeToH = winH - document.body.clientHeight;
				}
				
				if ( sizeToW != 0 || sizeToH != 0 ) {
				     window.resizeBy(sizeToW, sizeToH);
				}
			}
		</script>
	</head>
	<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="onloadResize();">
		<acube:outerFrame>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			        <td><acube:titleBar><spring:message code="list.list.title.listAdminRelayResultDetail"/></acube:titleBar></td>
			        <td>
				        <table align="right" border="0" cellpadding="0" cellspacing="0">
				            <tr>
				                <td>
				                    <acube:buttonGroup>
				                    	<acube:button  onclick="javascript:window.close();return(false);" value='<%=messageSource.getMessage("list.list.button.close",null,langType)%>' />
				                    </acube:buttonGroup>
				                </td>
				            </tr>
				        </table>
			        </td>
			    </tr>
			</table>
			<center>
				<form name="relayForm" id="relayForm" method="post">
					<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
					<acube:tableFrame class="table_grow">
					    <tr>
					        <!-- 문서 제목 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerTitle"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.getTitle()%></td>
					        <!-- 오류 유형 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerErrorType"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.getErrorType()%></td>
					    </tr>
					    <tr>
					    	<!-- 발송부서 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerSendDeptName"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.getSendDeptName()%></td>
					        <!-- 발신자 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerSendName"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.getSendName()%></td>
					    </tr>
					    <tr>
					    	<!-- 유통 타입 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerKind"/></td>
					        <td class="tb_left_bg" width="25%"><%=relayKind%></td>
					        <!-- 등록 일자 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerRegistDate"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.getRegistDate()%></td>
					    </tr>
					    <tr>
					        <!-- 오류 상세내용 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relay.title.headerDescription"/></td>
					        <td class="tb_left_bg" colspan="3"><%=EscapeUtil.escapeHtmlDisp(results.getDescription())%></td>
					    </tr>
					</acube:tableFrame>
					<acube:tableFrame>
					    <tr>
					    	<!-- 수신파일 -->
							<td width="50%" class="approval_box" colspan="4">
						    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
						      		<tr>
									    <td width="13%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
						        		<td width="80%;" height="15px">
											<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
						        		</td>
						        		<td width="10">&nbsp;</td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
													<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=messageSource.getMessage("approval.button.save", null, langType)%></a></td>
													<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
												</tr>
											</table>
						        		</td>
						        	</tr>
								</table>
							</td>
						</tr>
					</acube:tableFrame>
				</form>
			</center>
		</acube:outerFrame>
	</body>
</html>