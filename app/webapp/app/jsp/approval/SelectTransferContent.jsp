<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectTransferContent.jsp 
 * 
 *  @author  redcomet
 *  @since 2011. 04. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	try
	{
		String fileName = request.getParameter("fileName");
		String filePath = AppConfig.getProperty("upload_temp", "", "attach") + "/" + (String)session.getAttribute("COMP_ID") + "/" + fileName;

		if(filePath != null && !"".equals(filePath)) {
		    out.clear();
		    //out = pageContext.pushBody();
		    
			BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			//response.setContentType("multipart/related");
			response.setHeader("Content-Type", "message/rfc822; charset=euc-kr");
			response.setHeader("Content-Disposition", "inline;filename=" + fileName + ";");

			File inFile = new File(filePath);
			InputStream inputStream = new FileInputStream(inFile);
			byte bytes[]= new byte[(int)inFile.length()];
			int len = 0;
			while ((len = inputStream.read(bytes)) > 0)
			{
			    outputStream.write(bytes);
			}
			inputStream.close();
			outputStream.close();
		}
		else
		{
			response.sendError(response.SC_NOT_FOUND);
		}
	}
	catch(Exception e)
	{
	    logger.error(e.getMessage());
	}
%>