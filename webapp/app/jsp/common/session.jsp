<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.*" %>

<html>
<head><title>APP Session</title></head>
<body style='margin:0;overflow:auto;'>

<%
	int ssCount = 0;

	out.println("app session values<BR><BR>");

	String sessitem="",appItem="";
	out.print("SessionId: " + session.getId() + "<P>");
	String strSesList[]=session.getValueNames();

	out.print("List of " + strSesList.length + " items in Session   contents collection:<HR>");

	for(Enumeration enum_ses=session.getAttributeNames();enum_ses.hasMoreElements();)
	{
    	sessitem = enum_ses.nextElement().toString();
     	if (!sessitem.equals ("PASSWORD") )
     	{     	    if (sessitem.equals("DEPARTMENT_LIST")) {     			String[] list = (String[]) session.getAttribute(sessitem);     			int count = list.length;     			for (int loop = 0; loop < count; loop++) {        			out.print("DEPARTMENT_LIST[" + loop + "] : " + list[loop].toString()+"<BR>");     			}     	    } else {
    			out.print(sessitem + " : " + session.getAttribute(sessitem).toString()+"<BR>");     	    }
    	}
    	ssCount = ssCount + 1;
	}

%>

</body>
</html>