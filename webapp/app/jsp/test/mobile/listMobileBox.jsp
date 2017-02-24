<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : listMobileBox.jsp 
 *  Description : 모바일 목록 
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

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVO"%>

<%
	AppMenuVOs menuVos = (AppMenuVOs)request.getAttribute("AppMenuCnt");
	List<AppMenuVO> menuList = menuVos.getAppMenuVOs();	
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

		</script>
	</head>
	<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form" method="post" action="">
			<input type="hidden" name="itemCode" id="itemCode"/>
			<input type="hidden" name="totalCnt" id="totalCnt"/>			
		</form>
		<table width="600" height="100" border="0" cellpadding="0" cellspacing="0" align="center">			
			<tr>
				<%if(menuList.size()>0){
					for(int i =0; i<menuList.size(); i++){
						AppMenuVO menuVo = menuList.get(i);					
				%>
				<td w>				
					<table width="150" border="0" cellpadding="0" cellspacing="1" class="table" align="center">
						<tr bgcolor='#ffffff' height="30">
							<td class="ltb_center"><%=menuVo.getMenuname()%></td>
						</tr>
						<tr bgcolor='#ffffff' height="60">
							<td class="ltb_center"><%=menuVo.getDocCount()%></td>
						</tr>						
					</table>
				</td>
				<%
					}
				}
				%>			
			</tr>
			<tr>
				<td colspan="<%=menuList.size()%>"><iframe id="listIF" src="<%=webUri%>/app/mobile/listMobileAppDoc.do" width="600" height="500" frameborder="0" scrolling="auto"></iframe></td>
			</tr>
		</table>
	</body>
</html>