<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectOptionComImg.jsp 
 *  Description : 이미지 - 공통
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.13 
 *   수 정 자 : 신경훈
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 04. 13 
 *  @version 1.0 
 *  @see
 */ 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	try {

	    FileVO fileVO = new FileVO();
		fileVO = (FileVO)request.getAttribute("fileVO");
		String displayName = "";
		String filePath = fileVO.getFilePath();
		String fileType = fileVO.getFileType();
		if ("jpg".equals(fileType)) {
		    fileType = "jpeg";		    
		}
		fileType = "image/"+fileType;

		if(filePath != null && !"".equals(filePath)) {
		    out.clear();
		    //out = pageContext.pushBody();
		    
			BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType(fileType);
			response.setHeader("Content-Disposition", "inline;filename=" + displayName + ";");

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
		} else {
			response.sendError(response.SC_NOT_FOUND);
		}
	} catch(Exception e) {
	    logger.error(e.getMessage());
	}
%>