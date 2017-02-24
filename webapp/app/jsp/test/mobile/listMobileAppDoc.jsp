<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : listMobileAppDoc.jsp 
 *  Description : 모바일 결재 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  jd.park
 *  @since 2012. 06. 18
 *  @version 1.0 
 *  @see
 */ 
%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.env.vo.OptionVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppListVO"%>
<%
	String itemCode = (String) request.getAttribute("itemCode");
	String totalCnt = (String) request.getAttribute("totalCnt");
	
	List<OptionVO> optionList =(List<OptionVO>) request.getAttribute("optList");
	AppListVOs appListVOs = (AppListVOs) request.getAttribute("mobileAppList");		
	List<AppListVO> appList = appListVOs.getAppListVOs();		
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
		<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
		<jsp:include page="/app/jsp/common/common.jsp" />
		<jsp:include page="/app/jsp/common/filemanager.jsp" />
		<script type="text/javascript">
			function getList(){
				var form = document.forms[0];				
				form.action ="listMobileAppDoc.do";
				form.submit();
			}

			function selectDocInfo(docId,itemCode) {
				var form = document.forms[0];					
				form.action = "selectDocInfoMobile.do?docId="+docId+"&itemCode="+itemCode+"&listType=<%=itemCode%>";
				form.submit();
				
			}
		</script>
	</head>
	<body  leftmargin="0" topmargin="20" marginwidth="0" marginheight="0">
		<form name="form" method="post" action="">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >								
				<tr valign="top">
					<td width="50%" align="left">
						<select id="itemCode" name="itemCode" onchange="getList()">
						<%if(optionList.size()>0){
							String selectText ="";
							for(int i =0; i<optionList.size(); i++){
								OptionVO optionVo = optionList.get(i);
								if (appCode.getProperty("OPT103", "OPT103", "OPT").equals(optionVo.getOptionId())
								        || appCode.getProperty("OPT104", "OPT104", "OPT").equals(optionVo.getOptionId())
								        || appCode.getProperty("OPT112", "OPT112", "OPT").equals(optionVo.getOptionId())) {
									selectText ="";
									if(itemCode.equals(optionVo.getOptionId())){
										selectText ="selected";
									}
						%>
							<option value="<%=optionVo.getOptionId()%>" <%=selectText%>><%=optionVo.getOptionValue()%></option>
						<%
								}
							}
							selectText ="";
							if(itemCode.equals("OPT201")){
								selectText ="selected";
							}
						%>
							<option value="OPT201" <%=selectText%>>문서등록대장</option>
						<%
						}
						%>							
							
						</select>
					</td>
					<td width="50%" align="right" style="font-size: 12px">
						목록건수 : <input type="text" name="totalCnt" value="<%=totalCnt%>" size="3">
						<a href="javascript:getList()">선택</a>
					</td>
				</tr>
				<%if(itemCode.equals("OPT103")){%>
				
				<tr><td colspan="2" height="10"></td></tr>	
				<tr>
					<td colspan="2" align="left" style="font-size: 12px"> ※ 모바일의 결재대기함은 기안대기, 검토대기, 협조대기, 부서협조대기(검토), 부서협조대기(결재), 합의대기, 부서합의대기(검토), 부서합의대기(결재), 감사대기, 부서감사대기(검토), 부서감사대기(결재), 결재대기의 목록으로 구성됩니다.</td>
				</tr>
				<%}else if(itemCode.equals("OPT201")){%>
				<tr><td colspan="2" height="10"></td></tr>	
				<tr>
					<td colspan="2" align="left" style="font-size: 12px"> ※ 모바일의 문서등록대장은 생산문서의 결재완료, 발송대기, 반려후 대장등록완료, 심사반려, 심사대기(서명인), 심사대기(직인), 발송완료, 발송보류문서, 발송대기(발송취소문서), 발송대기(반송문서), 부분발송(부분발송문서), 발송문서(재발송요청문서)와 접수문서의 선람 및 담당 지정 대기, 선람 및 담당 지정 대기(반송), 선람대기, 담당대기, 담당대기의 목록으로 구성됩니다.</td>
				</tr>
				<%}%>
				<TR height="10"><td colspan="2"></td></TR>
				<tr valign="top"">
					<td colspan="2">						
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table" align="center">
							<tr bgcolor='#ffffff' height="30">							
								<td width="50%" class="ltb_center"><B>제목</B></td>																
								<td width="20%" class="ltb_center"><B>등록일</B></td>
								<td width="10%" class="ltb_center"><B>문서상태</B></td>								
							</tr>
							<%if(appListVOs.getResposeCode().equals("success")){
								if(appList.size()>0){
									for(int i =0; i<appList.size(); i++){
										AppListVO listVo = appList.get(i);
							%>
							<tr bgcolor='#ffffff' height="20">	
								<td width="50%" class="ltb_left"><a href="javascript:selectDocInfo('<%=listVo.getDocid()%>','<%=listVo.getAppstatus()%>');"><%=listVo.getTitle() %></a></td>
								<td width="20%" class="ltb_center"><%=listVo.getAppdate()%></td>								
								<td width="10%" class="ltb_center">
									<%
									if(listVo.getAppstatus() != null){
										out.print(messageSource.getMessage("list.list.msg."+listVo.getAppstatus().toLowerCase() , null, langType));
									}
									%>																		
								</td>								
							</tr>
							<%
									}
								}else{
							%>
							<tr bgcolor='#ffffff' height="30">	
								<td colspan="3" class="ltb_center">목록이 존재하지 않음...........................</td>
							</tr>
							<%		
								}
							%>
							<%}else{%>
							<tr bgcolor='#ffffff' height="30">	
								<td colspan="3" class="ltb_center">정보를 가져오는데 실패하였습니다(<%=appListVOs.getErrorMessage()%>).................................</td>
							</tr>
							<%}%>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>