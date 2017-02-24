<%@ page import="com.sds.acube.app.enforce.vo.EnfRecvVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/**  
 *  Class Name  : PopupEnfRecvList.jsp 
 *  Description : 미접수부서 목록 
 *  Modification Information 
 * 
 *   수 정 일 : 2012.03.23
 *   수 정 자 : 김경훈
 *   수정내용 : 요구사항 반영 
 * 
 *  @author  김경훈
 *  @since 2012.03.23 
 *  @version 1.0 
 */ 
%> 				 
<% 
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");	
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String popTitle =  messageSource.getMessage("enforce.title.enfRecvList" , null, langType);
	// 검색 결과 값
	List<EnfRecvVO> results = (List<EnfRecvVO>) request.getAttribute("enfRecvList");
	int totalCount = results.size();
	int curPage = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	
	String msgHeaderNotEnforceDeptInfo 	= messageSource.getMessage("enforce.enfRecvPop.header.notEnforceDeptInfo" , null, langType);
	String msgHeaderNumber			 	= messageSource.getMessage("list.list.title.headerCustomNum" , null, langType);
	String msgNoData 					= messageSource.getMessage("enforce.enfRecvPop.msg.noEnforceDeptInfo" , null, langType);
	
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<title><%=popTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"> 
function closePopup(){
	window.close();
}
</script>
</head>
<body onunload="closePopup();" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<acube:titleBar><%=popTitle%></acube:titleBar>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 2);
							acubeList.setColumnWidth("36,*");
							acubeList.setColumnAlign("center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;							
							
							titleRow.setData(rowIndex,msgHeaderNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderNotEnforceDeptInfo);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
							int num = 1;
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < totalCount; i++) {
							    
							    EnfRecvVO result = (EnfRecvVO) results.get(i);
							    
					            String rsRecvDeptName 	= EscapeUtil.escapeHtmlDisp(result.getRecvDeptName());
					            String rsRefDeptName	= EscapeUtil.escapeHtmlDisp(result.getRefDeptName());
					            String recvDeptName		= rsRecvDeptName;
					            
					            if(!"".equals(rsRefDeptName)){
					        		recvDeptName = rsRefDeptName;
					            }
					            
								row = acubeList.createRow();
					
								rowIndex = 0;
								
								row.setData(rowIndex, num);
								
								row.setData(++rowIndex, recvDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",recvDeptName);

								num++;
						    } // for(~)
			
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(false); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	    
						    
			%>
					</td>
						</tr>					
					</table>
					</form>
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>

<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->

<acube:buttonGroup align="right">
<% String msg1 = messageSource.getMessage("approval.enforce.button.close" , null, langType);  %>
<acube:button onclick="closePopup();" value="<%=msg1%>" />
</acube:buttonGroup>
</acube:outerFrame>

</body>
</html>