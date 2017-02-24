package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import com.sds.acube.app.design.AcubeSpace;
import com.sds.acube.app.design.AcubeTitleBar;

/**
 * Class Name  : TabGroup.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.TabGroup.java
 */
public class TabGroup extends TagSupport
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



    private String title;
    private String align = "left";

    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setTitle(String value) { title = value; }
    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setAlign(String value) { align = value; }


    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");

            if (title != null && title.length() > 0)
            {
                out.println("<tr>");
                out.println("<td>");
                AcubeTitleBar.generateStart(out, false);
                out.println(title);
                AcubeTitleBar.generateEnd(out, false, null);
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("  <td height='"+AcubeSpace.TOP_BUTTON+"'></td>");
                out.println("</tr>");
            }

            out.println("<tr>");
            out.println("<td>");

            out.println("<table border='0' align='"+align+"' cellpadding='0' cellspacing='0'>");
            out.println("<tr>");
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

            out.println("</tr>");
            out.println("</table>");

            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height='2' style='background-color:#6891CB'></td>");
            out.println("</tr>");
            out.println("</table>");
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }
}
