package com.sds.acube.app.design;

import java.io.*;
import com.sds.acube.app.design.style.*;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;

import javax.servlet.jsp.JspWriter;

public class AcubeTitleBar
{
    public static void generateStart(JspWriter out, boolean bPopup)
    {
    try {
        generateStart(new PrintWriter(out), bPopup, "main");
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }

    public static void generateStart(JspWriter out, boolean bPopup, String sType)
    {
    try {
        generateStart(new PrintWriter(out), bPopup, sType);
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }

    public static void generateStart(PrintWriter out, boolean bPopup, String sType)
    {
    try
    {
        AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
        String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

        if (bPopup)
        {
        out.println("<table class='pop_table03' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        out.println("  <tr>");
        out.println("    <td width='10' rowspan='3'></td>");
        out.println("    <td height='10'></td>");
        out.println("    <td width='10' rowspan='3'></td>");
        out.println("  </tr>");
        out.println("  <tr>");
        out.println("    <td>");
        out.println("<table class='pop_table04' width='100%' border='0' cellspacing='0' cellpadding='0'>");
        out.println("<tr>");
        out.println("<td width='21'><img src='" + imageHome + "/title1.jpg'></td>");
        out.println("<td background='" + imageHome + "/titlebg.gif'><table border='0' cellspacing='0' cellpadding='0'>");
        
        out.println("    <tr>");
        out.println("      <td class='title'>");
        }
        else
        {
        if (sType.equalsIgnoreCase("sub"))
        {
            out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
            out.println("<tr>");
            out.println("<td width='16'><img src='" + imageHome + "/title3.gif' width='16' height='16'></td>");
            out.println("<td height='25'><table border='0' cellspacing='0' cellpadding='0'>");
            out.println("    <tr>");
            out.println("      <td class='title'>");
        } else {
            out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
            out.println("<tr>");
            out.println("<td width='21'><img src='" + imageHome + "/title1.jpg'></td>");
            out.println("<td background='" + imageHome + "/titlebg.gif'><table border='0' cellspacing='0' cellpadding='0'>");
            out.println("    <tr>");
            out.println("      <td class='title'>");
        }
        }
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }

    // JspWriter 계열...
    public static void generateEnd(JspWriter out, boolean bPopup)
    {
    try {
        generateEnd(new PrintWriter(out), bPopup);
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }
    public static void generateEnd(JspWriter out, boolean bPopup, String exitFunction)
    {
    try {
        generateEnd(new PrintWriter(out), bPopup, exitFunction, null, "main");
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }
    public static void generateEnd(JspWriter out, boolean bPopup,
        String exitFunction, String helpFunction, String sType)
    {
    try {
        generateEnd(new PrintWriter(out), bPopup, exitFunction, helpFunction, sType);
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }
    // PrintWriter 계열...
    public static void generateEnd(PrintWriter out, boolean bPopup) {
    generateEnd(out, bPopup, null, null, "main");
    }
    public static void generateEnd(PrintWriter out, boolean bPopup, String exitFunction) {
    generateEnd(out, bPopup, exitFunction, null, "main");
    }
    public static void generateEnd(PrintWriter out, boolean bPopup,
        String exitFunction, String helpFunction, String sType)
    {
    try
    {
        AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
        String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

        if (bPopup)
        {
        if (exitFunction == null || exitFunction.length() == 0) {
            exitFunction = "javascript:top.close();";
        }

        out.println("</td>");
        out.println("    </tr>");
        out.println("  </table></td>");
        if (helpFunction != null && helpFunction.length() > 0)
        {
            out.println("<td width='19' background='" + imageHome + "/titlebg.gif' ><a href='"+ helpFunction+ "' onFocus='this.blur()'><img src='" + imageHome + "/bu5_close.gif' width='19' height='19' border='0'></a></td>");
        }
        out.println("<td width='13'><img src='" + imageHome + "/title2.gif' width='13' height='29'></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("</table>");

        }
        else
        {
        if (sType.equalsIgnoreCase("sub"))
        {
            out.println("      </td>");
            out.println("      <td class='ltb_left'>&nbsp;</td>");
            out.println("    </tr>");
            out.println("  </table></td>");
            out.println("</tr>");
            out.println("</table>");
        } else {
            out.println("</td>");
            out.println("    </tr>");
            out.println("  </table></td>");
            out.println("<td width='13'><img src='" + imageHome + "/title2.gif' width='13' height='29'></td>");
            out.println("</tr>");
            out.println("</table>");
        }
        }
    }
    catch (Exception e) {
        LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
    }
    }
}
