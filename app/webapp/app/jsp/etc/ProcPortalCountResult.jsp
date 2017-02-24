<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%
JSONObject json = (JSONObject)request.getAttribute("jObject");
%><%=json.toString()%>
