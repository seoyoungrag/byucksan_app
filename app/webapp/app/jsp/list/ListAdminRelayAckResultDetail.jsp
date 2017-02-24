<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.relay.vo.RelayAckHisVO" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ page import="com.sds.acube.app.common.vo.OrganizationVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListAdminRelayResultDoc.jsp 
 *  Description : 문서유통 연계이력 상세 목록 
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
	List<RelayAckHisVO> results = (List<RelayAckHisVO>) request.getAttribute("relayAckHisVOs");
	int nSize = results.size();
	
	String relayKind = "";
	OrganizationVO orgInfo = orgService.selectOrganization(results.get(0).getCompId());
	
	String msgHeaderKind 			= messageSource.getMessage("list.relayAck.title.headerKind" , null, langType);
	String msgHeaderDocId 			= messageSource.getMessage("list.relayAck.title.headerDocId" , null, langType);
	String msgHeaderCompId			= messageSource.getMessage("list.relayAck.title.headerCompId" , null, langType);
	String msgHeaderReceiveId		= messageSource.getMessage("list.relayAck.title.headerReceiveId" , null, langType);
	String msgHeaderAckType			= messageSource.getMessage("list.relayAck.title.headerAckType" , null, langType);
	String msgheaderAckDate			= messageSource.getMessage("list.relayAck.title.headerAckDate" , null, langType);
	String msgheaderAckXml			= messageSource.getMessage("list.relayAck.title.headerAckXml" , null, langType);
	String msgheaderAckDept			= messageSource.getMessage("list.relayAck.title.headerAckDept" , null, langType);
	String msgheaderAckName			= messageSource.getMessage("list.relayAck.title.headerAckName" , null, langType);
	String msgheaderRegistDate		= messageSource.getMessage("list.relayAck.title.headerRegistDate" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);

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
		<jsp:include page="/app/jsp/common/common.jsp" />
		<script type="text/javascript">
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

			var relayXmlDoc = null;

			function viewXml(hisId){
				var url = "<%=webUri%>/app/list/admin/ListAdminRelayResultXmlDoc.do";
			    url += "?hisId="+hisId;
			    
			    var width = 600;
			    if (screen.availWidth < 600) {
			        width = screen.availWidth;
			    }

			    var height = 600;
			    if (screen.availHeight < 600) {
			        height = screen.availHeight;
			    }

			    relayXmlDoc = openWindow("relayXmlDoc", url , width, height, "yes");
			}
		</script>
	</head>
	<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="onloadResize();">
		<acube:outerFrame>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			        <td><acube:titleBar><spring:message code="list.list.title.listAdminRelayAckResultDetail"/></acube:titleBar></td>
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
					<acube:tableFrame class="table_grow">
						<tr>
					    	<!-- 수발신유형 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relayAck.title.headerKind"/></td>
					        <td class="tb_left_bg" width="75%" colspan="3"><%=relayKind%></td>
					    </tr>
					    <tr>
					        <!-- 응답 번호 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relayAck.title.headerHisId"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.get(0).getHisId()%></td>
					        <!-- 문서 번호 -->
					        <td class="tb_tit_left" width="25%"><spring:message code="list.relayAck.title.headerDocId"/></td>
					        <td class="tb_left_bg" width="25%"><%=results.get(0).getDocId()%></td>
					    </tr>
					    <tr>
					    	<acube:tableFrame class="table_grow">
					    		<%
					    			AcubeList acubeList = null;
									acubeList = new AcubeList(10, 7);
									acubeList.setColumnWidth("150, 100, 100, *, 100, 130, 100");
									acubeList.setColumnAlign("center, center, center, center, center, center, center");	 
				
									AcubeListRow titleRow = acubeList.createTitleRow();
									int rowIndex=0;
									
									titleRow.setData(rowIndex,msgHeaderAckType);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									
									titleRow.setData(++rowIndex,msgHeaderCompId);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									
									titleRow.setData(++rowIndex,msgHeaderReceiveId);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

									titleRow.setData(++rowIndex,msgheaderAckDept);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
	
									titleRow.setData(++rowIndex,msgheaderAckName);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									
									titleRow.setData(++rowIndex,msgheaderRegistDate);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									
									titleRow.setData(++rowIndex,msgheaderAckXml);
									titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
									AcubeListRow row = null;
					    			for(int i = 0; i < nSize; i++) {
					    				RelayAckHisVO result = (RelayAckHisVO) results.get(i);
					    				
					    				String hisId 		= CommonUtil.nullTrim(result.getHisId());
										String docId 		= CommonUtil.nullTrim(result.getDocId());
										String sendId		= CommonUtil.nullTrim(result.getCompId());
							            String receiveId 	= CommonUtil.nullTrim(result.getReceiveId());
										String ackType		= CommonUtil.nullTrim(result.getAckType());
							            String ackDate		= CommonUtil.nullTrim(result.getAckDate());
							            String ackDept		= CommonUtil.nullTrim(result.getAckDept());
							            String ackName		= CommonUtil.nullTrim(result.getAckName());					            
							            String ackXml		= CommonUtil.nullTrim(result.getAckXml());
							            String registDate	= CommonUtil.nullTrim(result.getRegistDate());
							            
							            row = acubeList.createRow();
							            rowIndex = 0;
							            
							            row.setData(rowIndex, ackType);
										row.setAttribute(rowIndex, "title", ackType);
										
										row.setData(++rowIndex, sendId);
										row.setAttribute(rowIndex, "title", sendId);
										
										row.setData(++rowIndex, receiveId);
										row.setAttribute(rowIndex, "title", receiveId);
										
										row.setData(++rowIndex, ackDept);
										row.setAttribute(rowIndex, "title", ackDept);
										
										row.setData(++rowIndex, ackName);
										row.setAttribute(rowIndex, "title",ackName);

										row.setData(++rowIndex, registDate);
										row.setAttribute(rowIndex, "title",registDate);
										
										row.setData(++rowIndex, "<a href=\"#\"  onclick=\"javascript:viewXml('" + hisId + "');return(false);\">" + messageSource.getMessage("list.list.button.viewXml",null,langType) +"</A>");
										row.setAttribute(rowIndex, "title",hisId);
					    			}
					    			if(nSize == 0){
							            row = acubeList.createDataNotFoundRow();
										row.setData(0, msgNoData);
							        }
							
							        acubeList.setNavigationType("normal");
									acubeList.generatePageNavigator(false); 
									acubeList.setTotalCount(nSize);
									acubeList.setCurrentPage(0);
									acubeList.generate(out);	 
					    		%>
					    	</acube:tableFrame>
					    </tr>
					</acube:tableFrame>
				</form>
			</center>
		</acube:outerFrame>
	</body>
</html>