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
 * Class Name  : TableFrame.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.TableFrame.java
 */
public class TableFrame extends TagSupport
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


    private String styleFile = "/app/properties/acube_design.properties";

    //private int _margin = 10;
    private String _width;
    private String _border = "0";
    private String _cellspacing = "0";
    private String _cellpadding = "0";
    private String _type = null;

    private String _class;

    public String getClass(String value) { return _class; }
    public void setClass(String value) { _class = value; }
    public void setCss(String value) { _class = value; }
    public String getclass() { return this._class; }

    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setStyleFile(String value) { this.styleFile = value; }
    //public void setMargin(String value) { this._margin = Integer.parseInt(value); }
    public void setWidth(String value) { this._width = value; }
    public void setBorder(String value) { this._border = value; }
    public void setCellspacing(String value) { this._cellspacing = value; }
    public void setCellpadding(String value) { this._cellpadding = value; }
    public void setType(String value) { this._type = value; }

    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();
            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle(styleFile);

            String sWidth = (_width == null) ? style.getString("table_frame.width") : _width;
            //String sClass = style.getString("table_frame.class");
            String sClass = (_class == null) ? style.getString("table_frame.class") : _class;

            String imgHeader = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            if ( _type == null ) {
                out.print("<table width=\"" + sWidth + "\"");
                out.print(" class='" + sClass + "'");
                out.print(" border='"+_border+"'");
                out.println(" cellspacing='"+_cellspacing+"' cellpadding='"+_cellpadding+"'>");
            } else if ( _type.equalsIgnoreCase("search") ) {
                out.println("<table width=\"" + sWidth + "\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" > ");
                out.println("<tr>");
                out.println("<td height='3' background='"+imgHeader+"/dot_search1.gif'></td>");
                out.println("</tr>");
                out.println("<tr class='search'>");
                out.println("<td height='7'></td>");
                out.println("</tr>");
                out.println("<tr class='search'>");
                out.println("<td>");

                out.println("<table width=\"" + sWidth + "\"");
                out.print(" border='"+_border+"'");
                out.print(" bordercolor='pink'");
                out.println(" cellspacing='"+_cellspacing+"' cellpadding='"+_cellpadding+"'>");
            }
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
            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle(styleFile);

            String imgHeader = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            if ( _type == null ) {
                out.println("</table>");
            } else if ( _type.equalsIgnoreCase("search") ) {
                out.println("</table>");

                out.println("</td>");
                out.println("</tr>");
                out.println("<tr class='search'>");
                out.println("<td height='7'></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td height='1' background='"+imgHeader+"/dot_search2.gif'></td>");
                out.println("</tr>");
                out.println("</table>");
            } 
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }
}
