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
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;

/**
 * Class Name  : MenuButton.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.MenuButton.java
 */
public class MenuButton extends TagSupport
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
    private String _href;
    private String _value;
    private String _onclick;
    private String _title;

    public void setId(String value) { this._id = value; }
    public void setDisabledid(String value) { this._disabledid = value; }
    public void setHref(String value) { this._href = value; }
    public void setValue(String value) { this._value = value; }
    public void setOnclick(String value) { this._onclick = value; }
    public void setOnClick(String value) { this._onclick = value; }
    public void setTitle(String value) { this._title = value; }

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

            AcubeStyle style = AcubeStyleFactory.getInstance().getStyle();

            String imageHome = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

            out.println("<table ondragstart='return(false);' border='0' cellspacing='0' cellpadding='0'");
            if (_id != null) {
                out.print(" id='"+_id+"' ");
            }
            if (_onclick != null) {
                out.print(" onClick=\"" + _onclick + "\" style=\"cursor:pointer\" ");
            }
            out.println(">");
            out.println(" <tr>");
            out.println("   <td width='6'><img src='"+imageHome+"/btn_top1.gif' alt='' width='6' height='20'></td>");
            out.print("   <td background='"+imageHome+"/btn_topbg.gif' class='text_left'>");

            /*
            out.print("<button ");

            if (_id != null) {
                out.print(" id='"+_id+"' ");
            }

            _href = _href == null ? "" : _href;
            if (_onclick != null) {
                _href += ";" + _onclick;
            }
            out.print(" onClick=\"" + _href + "\"");
            out.print(" style='cursor:pointer;font-size:11px;background:none;border:none;'");
            out.print(">"+_value+"</button>");

            if (_id != null) {
                if (_disabledid == null) {
                    _disabledid = _id+"_disabled";
                }
                else if (_disabledid.startsWith(_id) == false) {
                    _disabledid = _id+"_disabled";
                }

                out.print("<input type='button' id='"+_disabledid+"' disabled='disabled' value='"+_value+"' style='color:#808080;display:none'>");
            }
            */

            out.print("<a");
            _href = _href == null ? "#" : _href;
            out.print(" href=\"" + _href + "\"");

            /*
            if (_id != null) {
                out.print(" id='"+_id+"' ");
            }

            if (_onclick != null) {
                out.print(" onClick=\"" + _onclick + "\"");
            }
            */

            // 타이틀이 없을 경우 _value를 사용
         //   if(_title==null) {
            	_title = _value;
         //   }

            out.print(" title='"+_title+"'>");
            out.print(    _value+"</a>");

            /*
            if (_id != null)
            {
                if (_disabledid == null) {
                    _disabledid = _id+"_disabled";
                }
                else if (_disabledid.startsWith(_id) == false) {
                    _disabledid = _id+"_disabled";
                }

                out.print("<nobr><a id='"+_disabledid+"' href='#' disabled style='color:#808080;display:none' title='"+_value+"'>"+_value);
                out.println("</a></nobr>");
            }
            */

            out.println("   </td>");
            out.println("   <td width='6'><img src='"+imageHome+"/btn_top2.gif' alt='' width='6' height='20'></td>");
            out.println(" </tr>");
            out.println("</table>");

            if (_id != null)
            {
                out.println("<table ondragstart='return(false);' border='0' cellspacing='0' cellpadding='0'");
                if (_disabledid == null) {
                    _disabledid = _id+"_disabled";
                }
                else if (!_disabledid.startsWith(_id)) {
                    _disabledid = _id+"_disabled";
                }
                out.print(" id='"+_disabledid+"' style='display:none;'");
                out.println(">");
                out.println(" <tr>");
                out.println("   <td width='6'><img src='"+imageHome+"/btn_top1.gif' alt='' width='6' height='20'></td>");
                out.print("   <td background='"+imageHome+"/btn_topbg.gif' class='text_left'>");
        	

                out.print("<nobr><a href='#' disabled style='color:#808080;cursor:default;' title='"+_title+"'>"+_value);
                out.print("</a></nobr>");

                out.println("   </td>");
                out.println("   <td width='6'><img src='"+imageHome+"/btn_top2.gif' alt='' width='6' height='20'></td>");
                out.println(" </tr>");
                out.println("</table>");
            }
            
            if (buttonGroup != null) {
                out.println("</td>");
            }
        }
        catch (Exception e) {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
            throw new JspTagException(e.toString());
        }

	    return SKIP_BODY;
    }
}
