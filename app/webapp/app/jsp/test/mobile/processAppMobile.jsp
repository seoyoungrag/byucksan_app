<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : processAppMobile.jsp 
 *  Description : 모바일 결재 처리 
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
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
	
	AppResultVO resultVo = (AppResultVO) request.getAttribute("result");	
	AppDetailVO detailVo = (AppDetailVO) request.getAttribute("detail");

	//화면 설정을 위한 변수 : insert 처리, 나머지 처리후
	String viewType = resultVo.getResposeCode();		
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String actionCode = CommonUtil.nullTrim(request.getParameter("actionCode"));
	
	String actionBtnName ="";
	
	if(actionCode.equals("A1")){
		actionBtnName ="승인";
	}else if(actionCode.equals("A2")){
		actionBtnName ="반려";
	}else if(actionCode.equals("A3")){
		actionBtnName ="찬성";
	}else if(actionCode.equals("A4")){
		actionBtnName ="반대";	
	}
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
		<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
		<jsp:include page="/app/jsp/common/common.jsp" />
		<jsp:include page="/app/jsp/common/filemanager.jsp" />
		<script type="text/javascript">
			function processApp(){
				var form = document.forms[0];
				if(form.password.value ==""){
					alert("패스워드 입력 필수");
					return;
				}else{	
					form.section.value ="result";			
					form.action ="processAppMobile.do";
					form.submit();
				}
			}
		</script>
	</head>
	<body  leftmargin="0" topmargin="20" marginwidth="0" marginheight="0">
		<form name="form" method="post" action="">
			<input type="hidden" name="section" id="section"/>
			<input type="hidden" name="docId" id="docId" value="<%=docId%>"/>
			<input type="hidden" name="actionCode" id="actionCode" value="<%=actionCode%>"/>			
			<%if(viewType.equals("insert")){%>
			<p align="center" style="font-size:17px;">문서처리</p>	
			<table width="300" border="0" cellpadding="0" cellspacing="1" class="table" align="center">
				<tr bgcolor='#ffffff' height="38">
					<td width="100" class="ltb_center">문서 제목</td>
					<TD width="200" class="ltb_left">					
						<%=detailVo.getTitle()%>						
					</td>
				</tr>
				
				<tr bgcolor='#ffffff' height="38">
					<td width="100" class="ltb_center">비밀번호</td>
					<TD width="200" class="ltb_left">					
						<input type="password" name="password" id="password" size="15" style="ime-mode:disabled" >						
					</td>
				</tr>
				
				<tr bgcolor='#ffffff' height="60">
					<td width="100" class="ltb_center">결재(반려)의견</td>
					<TD width="200" class="ltb_left">					
						<textarea rows="3" cols="25" name="appOpinion" id="appOpinion"></textarea>						
					</td>
				</tr>
				
				<tr bgcolor='#ffffff'>					
					<TD class="ltb_right" colspan="2">					
						<a href="javascript:processApp()"><%=actionBtnName%></a>
						<a href="javascript:history.back()">취소</a>						
					</td>
				</tr>				
								
			</table>
			<%}else{%>
			<p align="center" style="font-size:17px;">문서처리결과</p>
			<table width="300" border="0" cellpadding="0" cellspacing="1" class="table" align="center">
				<tr bgcolor='#ffffff' height="50">
					<td class="ltb_center">
					<%
					if(viewType.equals("success")){
						out.print(detailVo.getTitle() + "문서에 대한 " + actionBtnName + "에 성공하였습니다.");
					}else if(viewType.equals("fail")){
						out.print(detailVo.getTitle() + "문서에 대한 " + actionBtnName + "에 실패하였습니다.(" + resultVo.getErrorMessage() + ")");
					}
					%>											
					</td>
				</tr>
				
				<tr bgcolor='#ffffff'>					
					<TD class="ltb_right">					
						<a href="listMobileBox.do" target="_parent">목록</a>							
					</td>
				</tr>				
								
			</table>
			<%}%>
		</form>
	</body>
</html>