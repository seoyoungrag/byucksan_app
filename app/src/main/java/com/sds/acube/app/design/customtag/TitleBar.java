package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import com.sds.acube.app.design.AcubeTitleBar;

/**
 * Class Name  : TitleBar.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.TitleBar.java
 */
public class TitleBar extends TagSupport
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
    private String exitFunction;
    private String helpFunction;

    private String sType;

    public TitleBar()
    {
        sType = "main";
    }


    public void setPopup(String value)
    {
        bPopup = value.equalsIgnoreCase("true") ||
                 value.equalsIgnoreCase("y");
    }

    public void setExit(String value) { exitFunction = value; }
    public void setHelp(String value) { helpFunction = value; }
    public void setType(String value) { sType = value; }

    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            AcubeTitleBar.generateStart(out, bPopup, sType);
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

			AcubeTitleBar.generateEnd(out, bPopup, exitFunction, helpFunction, sType);
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }
}
