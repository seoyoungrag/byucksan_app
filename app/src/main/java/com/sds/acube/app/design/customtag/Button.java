package com.sds.acube.app.design.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import com.sds.acube.app.design.AcubeButton;


/**
 * Class Name  : Button.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.Button.java
 */
public class Button extends TagSupport
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
        _value = null;
        _title = null;
    }


    private String _id;
    private String _disabledid;
    private String _type = "2";
    private String _href;
    private String _onclick;
    private String _tabindex;
    private String _align;
    private String _class;
    private String _value;
    private String _width;
    private String _headImg;
    private String _title;
    private boolean _initiallyDisabled;

    public String getClass(String value) { return _class; }
    public void setClass(String value) { _class = value; }

    public void setCss(String value) { _class = value; }
    public void setId(String value) { this._id = value; }
    public void setDisabledid(String value) { this._disabledid = value; }
    public void setType(String value) { this._type = value; }
    public void setHref(String value) { this._href = value; }
    public void setOnclick(String value) { this._onclick = value; }
    public void setOnClick(String value) { this._onclick = value; }
    public void setTabindex(String value) { this._tabindex= value; }
    public void setAlign(String value) { this._align = value; }
    public String getclass() { return this._class; }
    public void setValue(String value) { this._value = value; }
    public void setWidth(String value) { this._width = value; }
    public void setHeadImg(String value) { this._headImg = value; }
    public void setTitle(String value) { this._title = value; }
    public void setDisable(String value)
    {
        this._initiallyDisabled = value.equalsIgnoreCase("true") ||
                                   value.equalsIgnoreCase("y");
    }

    public int doStartTag()
            throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();

            // buttonGroup 태그 안에 존재하는 경우에는 td 태그로 감싼다.
            ButtonGroup buttonGroup = (ButtonGroup) findAncestorWithClass(this, ButtonGroup.class);
            if (buttonGroup != null) {
                out.println("<td>");
            }

            out.println(AcubeButton.getIdButton(_id, _disabledid, _href,
                        _value, _align, _class, _type,
                        _initiallyDisabled, _onclick, _width, _headImg, _title, _tabindex));

            if (buttonGroup != null) {
                out.println("</td>");
            }
        }
        catch (Exception e) {
            throw new JspTagException(e.toString());
        }

	    return SKIP_BODY;
    }
}
