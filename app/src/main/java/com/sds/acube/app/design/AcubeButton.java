package com.sds.acube.app.design;

import java.io.*;
import com.sds.acube.app.design.style.*;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;

public class AcubeButton
{
    // ID 없는 메서드들...
    public static String getButton(
                    String _href, String _value)
    {
        return getIdButton(null, null, _href, _value, null, null, "2");
    }

    public static String getButton(
                    String _href, String _value, String _align)
    {
        return getIdButton(null, null, _href, _value, _align, null, "2");
    }

    public static String getButton(
                    String _href, String _value, String _class, String _type)
    {
        return getIdButton(null, null, _href, _value, null, _class, _type);
    }

    public static String getButton(
                    String _href, String _value, String _align, String _class, String _type)
    {
        return getIdButton(null, null, _href, _value, _align, _class, _type);
    }

    public static String getButton(
                    String _href, String _value, String _onclick, String _align, String _class, String _type)
    {
        return getIdButton(null, null, _href, _value, _align, _class, _type, false, _onclick, null, null);
    }

    // ID 있는 메서드들...
    public static String getIdButton(
                    String _id, String _href, String _value)
    {
        return getIdButton(_id, null, _href, _value, null, null, "2");
    }

    public static String getIdButton(
                    String _id, String _href, String _value, String _class, String _type)
    {
        return getIdButton(_id, null, _href, _value, null, _class, _type);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value)
    {
        return getIdButton(_id, _disabledID, _href, _value, null, null, "2");
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value, String _class, String _type)
    {
        return getIdButton(_id, _disabledID, _href, _value, null, _class, _type);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type)
    {
        return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type, boolean initiallyDisabled)
    {
        return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false, null);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type,
                    boolean initiallyDisabled, String _onclick)
    {
        return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false, null, null, null);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type,
                    boolean initiallyDisabled, String _onclick, String _width)
    {
        return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false, _onclick, _width, null);
    }


    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type,
                    boolean initiallyDisabled, String _onclick, String _width, String _headImg)
    {
    	return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false, _onclick, _width, null, null);
    }

    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type,
                    boolean initiallyDisabled, String _onclick, String _width, String _headImg, String _title)
    {
    	return getIdButton(_id, _disabledID, _href, _value, _align, _class, _type, false, _onclick, _width, null, null, null);
    }   
    
    // Button generate
    public static String getIdButton(
                    String _id, String _disabledID, String _href, String _value,
                    String _align, String _class, String _type,
                    boolean initiallyDisabled, String _onclick, String _width, String _headImg, String _title, String _tabindex)
    {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);

        try
        {
            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
            String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            String imgLeft = "";
            String imgBg = "";
            String imgRight = "";

            if (_type.equalsIgnoreCase("right"))
            {
                imgLeft = imageHome+"/bu4_add.gif";
                imgBg = imageHome+"/bu4_bg.gif";
                imgRight = imageHome+"/bu4_right.gif";
            }
            else if (_type.equalsIgnoreCase("left"))
            {
                imgLeft = imageHome+"/bu4_del.gif";
                imgBg = imageHome+"/bu4_bg.gif";
                imgRight = imageHome+"/bu4_right.gif";
            }
            else if (_type.equalsIgnoreCase("up"))
            {
                imgLeft = imageHome+"/bu_del4.gif";
                imgBg = imageHome+"/bu4_bg.gif";
                imgRight = imageHome+"/bu4_right.gif";
            }
            else if (_type.equalsIgnoreCase("down"))
            {
                imgLeft = imageHome+"/bu_add4.gif";
                imgBg = imageHome+"/bu4_bg.gif";
                imgRight = imageHome+"/bu4_right.gif";
            }
            else if (_type.equalsIgnoreCase("search"))
            {
                imgLeft = imageHome+"/bu3_search.gif";
                imgBg = imageHome+"/bu2_bg.gif";
                imgRight = imageHome+"/bu2_right.gif";
            }
            else if (_type.equalsIgnoreCase("reset"))
            {
                imgLeft = imageHome+"/btn_reset.gif";
                imgBg = imageHome+"/bu2_bg.gif";
                imgRight = imageHome+"/bu2_right.gif";
            }
            else
            {
            	if(_headImg!=null) {
	                imgLeft = imageHome+"/bu"+_type+"_"+_headImg+".gif";
	                imgBg = imageHome+"/bu"+_type+"_bg.gif";
	                imgRight = imageHome+"/bu"+_type+"_right.gif";
            	} else {
	                imgLeft = imageHome+"/bu"+_type+"_left.gif";
	                imgBg = imageHome+"/bu"+_type+"_bg.gif";
	                imgRight = imageHome+"/bu"+_type+"_right.gif";
            	}
            }

            if (_type.equalsIgnoreCase("list"))
            {
                out.print("<table ondragstart='return(false);' border='0'");
                if (_align != null) {
                    out.print(" align='"+_align+"'");
                }
                out.println(" cellpadding='0' cellspacing='0'>");
                out.println("<tr>");
                out.print  ("<td class='btn_text'>");

                _href = (_href == null) ? "#" : _href;
                
                out.print("<a href=\""+_href+"\" ");
                
                if (_onclick != null) {
                    out.print(" onClick=\"" + _onclick + "\"");
                }
                
                out.println("><nobr>");
                out.println(_value+"</nobr></a>");
    
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
            } else {
                out.print("<table ondragstart='return(false);' border='0'");
                                
                if (_align != null) {
                    out.print(" align='"+_align+"'");
                }

                if (_id != null) {
                    out.print(" id='"+_id+"'");
                }
                if (initiallyDisabled)
                {
                    if (_disabledID == null) {
                        out.print(" disabled='disabled' ");
                    }
                    else {
                        out.print(" style='display:none;'");
                    }
                }
                if (_onclick != null) {
                    out.print(" onClick=\" " + _onclick + "\" style=\"cursor:pointer\"  ");                    
                }

                
                out.println(" cellpadding='0' cellspacing='0'>");
    
                out.println("<tr>");
                out.println("<td><img src='"+imgLeft+"' alt=''></td>");
                out.print  ("<td");
                if (_width != null) {
                    out.print  (" width='" + _width + "'");
                }
    
                out.println(" background='"+imgBg+"' class='text_center' style='white-space:nowrap'>");
    
                _href = (_href == null) ? "#" : _href;

                
                if (_tabindex != null) {
                	out.print("<a href=\""+_href+"\"" + " tabindex='"+_tabindex+"'");
                }else{
                	out.print("<a href=\""+_href+"\"");	
                }
                
    
                /*    
                if (_id != null) {
                    out.print(" id='"+_id+"'");
                }
                if (initiallyDisabled)
                {
                    if (_disabledID == null) {
                        out.print(" disabled='disabled' ");
                    }
                    else {
                        out.print(" style='display:none;width:100%;'");
                    }
                }
                else {
                    out.print(" style='width:100%;'");
                }
                if (_onclick != null) {
                    out.print(" onClick=\"" + _onclick + "\"");
                }
                */
                
                if (_class != null) {
                    out.print(" class='" + _class + "'");
                }
                
                // 타이틀이 없을 경우 _value를 사용
                if(_title==null) {
                	_title = _value;
                }
                
                out.print(" title='"+_title+"'>");
                out.println(_value+"</a>");
    
                /*
                if (_disabledID != null)
                {
                    out.print("<a id='"+_disabledID+"' disabled");
                    if (initiallyDisabled) {
                        out.print(" style='color:#808080;cursor:default;'");
                    }
                    else {
                        out.print(" style='color:#808080;display:none;cursor:default;'");
                    }
                    out.print(" title='"+_value+"'><nobr>"+_value);
                    out.print("</nobr></a>");
                }
                */
                
                out.println("</td>");
                out.println("<td><img src='"+imgRight+"' alt='' width='8' height='20'></td>");
                out.println("</tr>");
                out.println("</table>");
                
                if (_disabledID != null)
                {
                    out.print("<table ondragstart='return(false);' border='0'");
                    if (_align != null) {
                        out.print(" align='"+_align+"'");
                    }
                    out.print(" id='"+_disabledID+"' disabled");
                    if (!initiallyDisabled) {
                        out.print(" style='display:none;'");
                    }

                    out.println(" cellpadding='0' cellspacing='0'>");
                    
                    out.println("<tr>");
                    out.println("<td><img src='"+imgLeft+"' alt=''></td>");
                    out.print  ("<td");
                    if (_width != null) {
                        out.print  (" width='" + _width + "'");
                    }
        
                    out.println(" background='"+imgBg+"' class='text_center' style='white-space:nowrap'>");
                    
                    out.print("<a disabled");
                    if (_class != null) {
                        out.print(" class='" + _class + "'");
                    }
                    out.print(" style='color:#808080;cursor:default;'");
                    out.print(" title='"+_title+"'><nobr>"+_value);
                    out.println("</nobr></a>");
                    
                    out.println("</td>");
                    out.println("<td><img src='"+imgRight+"' alt='' width='8' height='20'></td>");
                    out.println("</tr>");
                    out.println("</table>");
                    
                }

            }
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
}
