<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="java.io.*" %>

<%!
	//---------------------------------------------------------
	// HTML 파일을 생성한다.
	//---------------------------------------------------------
	public boolean writeHTMLFile(String htmlFileName, String htmlContent, String appLineHTML) {
		boolean isSaveSuccess = true;
		Writer writer = null;
	
		try {
			File fp = new File(htmlFileName);
			
			writer = new OutputStreamWriter(new FileOutputStream(fp));
			
			if (appLineHTML != null && ! "".equals(appLineHTML)) {
				writer.write(appLineHTML);	
			}
			
			writer.write(htmlContent);
		} catch (Exception e) {
			System.out.println("writeHTMLFile Error -> " + htmlFileName);
			isSaveSuccess = false;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e) { }
		}
		return isSaveSuccess;
	}

%>

<%
	String htmlFilePath = (String)request.getParameter("htmlFilePath");
	String htmlContent  = (String)request.getParameter("htmlContent");
	String appLineHTML  = (String)request.getParameter("appLineHTML");
	
	String saveResult = "" + writeHTMLFile(htmlFilePath, htmlContent, appLineHTML);
	response.getWriter().print(saveResult);
%>
