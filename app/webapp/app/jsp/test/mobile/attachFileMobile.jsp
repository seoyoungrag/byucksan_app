<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : selectMobileDocInfo.jsp 
 *  Description : 모바일 결재상세내용 조회 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  kj.yang
 *  @since 2012. 06. 18
 *  @version 1.0 
 *  @see
 */  
%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="javax.activation.DataHandler" %>
<%@ page import="java.io.*" %>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.Files"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.File"%>
<%
	AppAttachVO attachVO = (AppAttachVO)request.getAttribute("AttachList");
	File attfileVO = attachVO.getFile();
	DataHandler dh = attfileVO.getContent();
	
	FileOutputStream fos = new FileOutputStream("D:\\"+attfileVO.getFileName());
	dh.writeTo(fos);
	
	fos.flush();
	fos.close();
	
	String sFileName = "D:\\"+attfileVO.getFileName()+"로 저장되었습니다.";
%>
<Script>
	setTimeout(
			function(){ 
				alert("<%=sFileName%>");
				history.back();
			}, 100);
</Script>