<% request.setCharacterEncoding("utf-8"); %>  
<%@ page import="java.net.*" contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ page import="java.io.*"%>
<%

  if (request.getParameter("save_string") != null && !request.getParameter("save_string").equals("")){

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=untitled.html");

		/*OutputStream outs = response.getOutputStream();
		outs.write( new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF} );
		outs.write(request.getParameter("save_string").getBytes());
		outs.flush();
		outs.close();*/
		
		String sHTML = request.getParameter("save_string");
		out.println(sHTML);

		return;

  }else{

		return;
  }

%>