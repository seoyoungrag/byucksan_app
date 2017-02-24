package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import com.sds.acube.app.design.AcubeSpace;

/**
 * Class Name  : Space.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.Space.java
 */
public class Space extends TagSupport
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


    private String between = "button";

    private String table = "N";

    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setBetween(String value) { between = value; }

    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setTable(String value) { table = value; }


    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            if (table.equalsIgnoreCase("Y")) {
                out.println("<table width='100%' border='0' cellpadding='0' cellspacing='0'>");
    		    out.println("<tr>");
            }

            if (between.equalsIgnoreCase("button")) {
                out.println("<td width='"+AcubeSpace.BETWEEN_BUTTON+"'></td>");
            }
            else if (between.equalsIgnoreCase("menu")) {
                out.println("<td width='"+AcubeSpace.BETWEEN_MENU+"'></td>");
            }
            else if (between.equalsIgnoreCase("menu_list")) {
                out.println("<td height='"+AcubeSpace.MENU_LIST+"'></td>");
            }
            else if (between.equalsIgnoreCase("button_content")) {
                out.println("<td height='"+AcubeSpace.BUTTON_CONTENT+"'></td>");
            }
            else if (between.equalsIgnoreCase("title_button")) {
                out.println("<td height='"+AcubeSpace.TOP_BUTTON+"'></td>");
            }
            else if (between.equalsIgnoreCase("tab")) {
                out.println("<td width='"+AcubeSpace.TAB+"'></td>");
            }
            else if (between.equalsIgnoreCase("button_search")) {
                out.println("<td height='"+AcubeSpace.BUTTON_SEARCH+"'></td>");
            }
            else if (between.equalsIgnoreCase("search")) {
                out.println("<td width='"+AcubeSpace.SEARCH+"'></td>");
            }

            if (table.equalsIgnoreCase("Y")) {
                out.println("</tr>");
                out.println("</table>");
            }

        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

	    return EVAL_BODY_INCLUDE;
    }
}
