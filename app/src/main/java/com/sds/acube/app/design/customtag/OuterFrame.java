package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import com.sds.acube.app.design.style.AcubeStyle;
import com.sds.acube.app.design.style.AcubeStyleFactory;

/**
 * Class Name  : OuterFrame.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.OuterFrame.java
 */
public class OuterFrame extends TagSupport
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


    private boolean bPopup;
    private boolean bGenerateTop = true;
    private String styleFile = "/app/properties/acube_design.properties";

    private String _margin = "10";
    private String _width;

    public void setPopup(String value)
    {
        this.bPopup = value.equalsIgnoreCase("true") ||
                      value.equalsIgnoreCase("y");
    }
    public void setGenerateTop(String value)
    {
        this.bGenerateTop = value.equalsIgnoreCase("true") ||
                            value.equalsIgnoreCase("y");
    }
    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setStyleFile(String value) { this.styleFile = value; }
    public void setMargin(String value) { this._margin = value; }
    public void setWidth(String value) { this._width = value; }


    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();
            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle(styleFile);

            String sWidth = null;
            if (bPopup) {
                sWidth = "100%";
            }
            else
            {
                if (_width == null) {
                    sWidth = style.getString("outer_frame.width");
                }
                else {
                    sWidth = _width;
                }
            }

            String sStyle = style.getString("outer_frame.style");
            out.print("<table width=\"" + sWidth + "\"");
            out.print(" style='" + sStyle + "'");
            out.println(" cellspacing='0' cellpadding='0'>");

            if (_margin.equals("0")) {
                out.println("<tr><td>");
            }
            else
            {
                if (bGenerateTop)
                {
                    out.println("<tr>");
                    out.println("<td height='"+_margin+"' colspan='3'></td>");
                    out.println("</tr>");
                }

                out.println("<tr>");
                out.println("<td width='"+_margin+"'></td>");
                out.println("<td>");
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

            if (_margin.equals("0")) {
                out.println("</td></tr>");
            }
            else
            {
                out.println("</td>");
                out.println("<td width='"+_margin+"'></td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }
}
