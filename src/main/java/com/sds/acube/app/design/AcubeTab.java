package com.sds.acube.app.design;

import java.io.*;
import javax.servlet.jsp.*;
import com.sds.acube.app.design.style.*;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;

public class AcubeTab
{
    // tab script generate
    public static String getScriptFunction(int tabCount)
    {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);

        try
        {
            generateScriptFunction(out, tabCount);
            return sw.toString();
        }
        catch (Exception e)
        {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
            return "";
        }
        finally
        {
            try
            {
                out.close();
                sw.close();
            }
            catch (Exception e) {}
        }
    }

    public static void generateScriptFunction(JspWriter out, int tabCount)
    {
        try {
            generateScriptFunction(new PrintWriter(out), tabCount);
        }
        catch (Exception ignore) {}
    }

    public static void generateScriptFunction(PrintWriter out, int tabCount)
    {
        try
        {
            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
            String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            out.println();
            out.println("function selectTab(n)");
            out.println("{");
            out.println("   for (var i=1; i <= " + tabCount + "; i++) ");
            out.println("   {");
            out.println("       if (i == n)");
            out.println("       {");
            out.println("           document.getElementById('id_tab_left_'+i).src = '"+imageHome+"/tab1.gif';");
            out.println("           document.getElementById('id_tab_bg_'+i).style.background = 'url("+imageHome+"/tabbg.gif)';");
            out.println("           document.getElementById('id_tab_bg_'+i).className = 'tab';");
            out.println("           document.getElementById('id_tab_right_'+i).src = '"+imageHome+"/tab2.gif';");
            out.println("       }");
            out.println("       else");
            out.println("       {");
            out.println("           document.getElementById('id_tab_left_'+i).src = '"+imageHome+"/tab1_off.gif';");
            out.println("           document.getElementById('id_tab_bg_'+i).style.background = 'url("+imageHome+"/tabbg_off.gif)';");
            out.println("           document.getElementById('id_tab_bg_'+i).className = 'tab_off';");
            out.println("           document.getElementById('id_tab_right_'+i).src = '"+imageHome+"/tab2_off.gif';");
            out.println("       }");
            out.println("   }");
            out.println("}");
            out.println();
        }
        catch (Exception e) {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }
    }
}
