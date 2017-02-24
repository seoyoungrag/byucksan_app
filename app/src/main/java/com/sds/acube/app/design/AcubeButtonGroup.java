package com.sds.acube.app.design;

import javax.servlet.jsp.JspWriter;

import com.sds.acube.app.common.util.LogWrapper;

public class AcubeButtonGroup
{
    public static void generateStart(JspWriter out, String _align)
    {
        try
        {
            out.println("<table width='100%' border='0' align='"+_align+"' cellpadding='0' cellspacing='0'>");
		    out.println("<tr><td>");
            out.println("<table border='0' align='"+_align+"' cellpadding='0' cellspacing='0'>");
		    out.println("<tr>");
        }
        catch (Exception e) {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }
    }

    public static void generateEnd(JspWriter out)
    {
        try
        {
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
        }
        catch (Exception e) {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }
    }
}
