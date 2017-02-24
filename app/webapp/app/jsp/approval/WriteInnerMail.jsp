<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.GuidUtil"%>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.BufferedOutputStream"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.FileWriter"%>
<%@ page import="java.io.BufferedWriter"%>
<%@ page import="java.io.File"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
/** 
 *  Class Name  : WriteInnerMail.jsp 
 *  Description : 사내메일
 *  Modification Information 
 * 
 *   수 정 일 : 2012.01.02 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2012.01.02  
 *  @version 1.0 
 *  @see
 */ 
%>
<%!
    public String makeATHFile(String title, String dirPath, String[] filenames, Log logger) {
	
		try {
		    File dir = new File(dirPath);
		    if (!dir.isDirectory()) {
				dir.mkdirs();
		    }
		    String athFileName = dirPath + GuidUtil.getGUID() + ".ath";
		    String delim = CommonUtil.ascTochar(29);
		    int filecount = filenames.length;
	
		    FileWriter athFile;
		    BufferedWriter buf;
		    String str;	
		    
		    athFile = new FileWriter(athFileName);
		    buf =  new BufferedWriter(athFile);
		    str = "[MSG]";
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    str = "TITLE=" + title;
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    buf.newLine();
		    str = "[CONTENT]";
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    str = "COUNT=0";
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    buf.newLine();
		    str = "[ATTACH]";
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    str = "COUNT=" + filecount;
		    buf.write(str, 0, str.length());
		    buf.newLine();
		    for (int loop = 0; loop < filecount; loop++) {
				File file = new File(dirPath + "/" + filenames[loop]);
			    str = (loop + 1) + "=FILE"+(new StringBuilder(String.valueOf(delim))).append("A").toString()
			    		+(new StringBuilder(String.valueOf(delim))).append("한글첨부명.hwp").toString()
			    		+(new StringBuilder(String.valueOf(delim))).append(dirPath + "/" + filenames[loop]).toString()
			    		+(new StringBuilder(String.valueOf(delim))).append(filenames[loop]).toString()
			    		+(new StringBuilder(String.valueOf(delim))).append(filenames[loop]).toString()
			    		+(new StringBuilder(String.valueOf(delim))).append(file.length()).toString()
			    		+(new StringBuilder(String.valueOf(delim))).append("unknown/unknown").toString()
			    		+(new StringBuilder(String.valueOf(delim))).append("\n").toString();
			    buf.write(str, 0, str.length());
		    }
		    buf.close();
		    athFile.close();
		    
		    return athFileName;
		} catch (Exception e) {
		    logger.debug(e.getMessage());
		    return "";
		}
    }

    public void MoveFile(String sourcePath, String targetPath, String fileName, Log logger) {
		try {
		    File dir = new File(targetPath);
		    if (!dir.isDirectory()) {
				dir.mkdirs();
		    }
		    File inf = new File(sourcePath + fileName);
		    File outf = new File(targetPath + fileName);
		    byte b[] = new byte[1024];

		    BufferedInputStream fin = new BufferedInputStream(new FileInputStream(inf));
		    BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(outf));
	
		    for (int i; (i = fin.read(b)) != -1;) {
				fout.write(b, 0, i);
				fout.flush();
		    }

		    fin.close();
		    fout.close();
		} catch (Exception e) {
		    logger.debug(e.getMessage());
		}
    }
%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID

	String actionUrl = AppConfig.getProperty("callUrl", "", "sendMail");
	String pType = AppConfig.getProperty("ptype", "", "sendMail");
	String pAction = "composeMail";
	String iMode = "1";    // 1 : 수신자 표시, 2 : 수신자 숨김
	String iRcptAddr = ""; // #부서코드, K2222222|D3333333
	
	String pname3 = AppConfig.getProperty("pname3", "", "sendMail");
	String pval3 = (String) request.getAttribute("athfilepath");
	String pname4 = AppConfig.getProperty("pname4", "", "sendMail");
	String pval4 = AppConfig.getProperty("pval4", "", "sendMail");
	String pname5 = AppConfig.getProperty("pname5", "", "sendMail");
	String pval5 = AppConfig.getProperty("pval5", "", "sendMail");
	String role = ""; // 부서메일일 경우 "dept"
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.approval' /></title>
<link rel="stylesheet" type="text/css"
	href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script language="javascript">
	$(document).ready(function() {
		//initialize();
	});
	$.ajaxSetup( {
		async : false
	});

	function initialize() {
		document.frmSendMail.submit();
	}
</script>
</head>
<body>
<form name="frmSendMail" method="post" action="<%=actionUrl%>">
	<input type="hidden" name="ptype" value="<%=pType%>">
	<input type="hidden" name="paction" value="<%=pAction%>">
	<input type="hidden" name="i_Mode" value="<%=iMode%>">
	<input type="hidden" name="userID" value="<%=userId%>"> 
	<input type="hidden" name="i_RcptAddr" value="<%=iRcptAddr%>"> 
	<input type="hidden" name="pname3" value="<%=pname3%>"> 
	<input type="hidden" name="pval3" value="<%=pval3%>"> 
	<input type="hidden" name="pname4" value="<%=pname4%>"> 
	<input type="hidden" name="pval4" value="<%=pval4%>"> 
	<input type="hidden" name="pname5" value="<%=pname5%>"> 
	<input type="hidden" name="pval5" value="<%=pval5%>"> 
	<!-- 
    <input type="hidden" name="role" value="<%=role %>">
    -->
</form>
</body>
</html>