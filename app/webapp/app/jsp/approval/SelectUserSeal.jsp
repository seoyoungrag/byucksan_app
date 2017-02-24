<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectUserSeal.jsp 
 *  Description : 사용자직인
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.11 
 *   수 정 자 : redcomet
 *   수정내용 : 수정 
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
	    FileVO fileVO = (FileVO)request.getAttribute("fileVO");
		String fileName = fileVO.getFileName();
		String filePath = fileVO.getFilePath();
		String fileType = fileVO.getFileType();

		if(filePath != null && !"".equals(filePath)) {
		    out.clear();
		    //out = pageContext.pushBody();
		    
			BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType(fileType);
			response.setHeader("Content-Disposition", "inline;filename=" + fileName + ";");

			File inFile = new File(filePath);
			InputStream inputStream = new FileInputStream(inFile);
			byte bytes[]= new byte[1024];
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