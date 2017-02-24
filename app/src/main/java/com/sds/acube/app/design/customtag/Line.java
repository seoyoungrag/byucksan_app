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
 * Class Name  : Line.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.Line.java
 */
public class Line extends TagSupport
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

    private String color;

    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setColor(String value) { this.color = value; }

    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();

            //String imgHeader = style.getString("image.home");

            if (color == null) {
                color = style.getString("line.bgcolor");
            }
            
            out.print("<table width='"+style.getString("line.width") + "'");
            out.println(" border='0' cellpadding='0' cellspacing='0'>");
            out.println("<tr>");
            out.print("<td style=\"height:" + style.getString("line.height") + ";");
            out.println("width:100%;background-color:" + color + ";\"");
            out.println("</tr>");
            out.println("</table>");
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

	    return SKIP_BODY;
    }
}
