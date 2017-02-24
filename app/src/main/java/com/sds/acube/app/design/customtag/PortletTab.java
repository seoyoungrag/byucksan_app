package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.design.style.AcubeStyle;
import com.sds.acube.app.design.style.AcubeStyleFactory;

/**
 * Class Name  : PortletTab.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.PortletTab.java
 */
public class PortletTab extends TagSupport
{
    private static final long serialVersionUID = 1L;
    
	protected BodyContent bodyOut;
    protected PageContext pageContext;
    /**
	 */
    protected Tag parent;

    /**
	 * <pre>  설명 </pre>
	 * @param parent
	 * @see   
	 */
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public void setBodyContent(BodyContent bodyOut) {
        this.bodyOut = bodyOut;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param pageContext
	 * @see   
	 */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public Tag getParent() {
        return this.parent;
    }

    public void release() {
        bodyOut = null;
        pageContext = null;
        parent = null;
    }


    private String _class;
    private String _href;
    private String _onclick;
    private boolean bSelected;
    private int _index;

    //public String getClassName(String value) { return _class; }
    
    public void setClass(String value) { _class = value; }
    public void setCss(String value) { _class = value; }
    public void setHref(String value) { _href = value; }
    public void setOnClick(String value) { _onclick = value; }
    public void setSelected(String value)
    {
        bSelected = value.equalsIgnoreCase("true") ||
                    value.equalsIgnoreCase("y");
    }
    public void setIndex(String value)
    {
        try {
            _index = Integer.parseInt(value);
        }
        catch (Exception ignore) {}
    }

    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            if (_href == null) {
                _href = "#none";
            }
            if (_href.equals("#")) {
                _href = "#none";
            }

            if (_class == null) {
                _class = "text_left";
            }

            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
            String imgHeader = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            if (bSelected) {
                out.print  ("<td><img id='id_tab_left_"+_index+"'");
                out.println(    " src='"+imgHeader+"/tab1.gif' width='16' height='24'></td>");
                out.print  ("<td id='id_tab_bg_"+_index+"'");
                out.println(    " background='"+imgHeader+"/tabbg.gif' class='tab'>");
            }
            else {
                out.print  ("<td><img id='id_tab_left_"+_index+"'");
                out.println(    " src='"+imgHeader+"/tab1_off.gif' width='16' height='24'></td>");
                out.print  ("<td id='id_tab_bg_"+_index+"'");
                out.println(    " background='"+imgHeader+"/tabbg_off.gif' class='tab_off'>");
            }

            out.print("    <a href=\""+_href+"\"");
            if (_onclick != null) {
                out.print(" onclick=\""+_onclick+"\"");
            }

            out.print(">");
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

	    return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();
            String imgHeader = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            out.println("</a></td>");
            out.println("<td><img id='id_tab_right_"+_index+"'");

            if (bSelected) {
                out.println(    " src='"+imgHeader+"/tab2.gif' width='16' height='24'></td>");
            }
            else {
                out.println(    " src='"+imgHeader+"/tab2_off.gif' width='16' height='24'></td>");
            }

        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }
}
