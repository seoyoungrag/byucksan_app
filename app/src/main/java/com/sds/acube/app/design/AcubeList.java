package com.sds.acube.app.design;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.jsp.JspWriter;
import com.sds.acube.app.design.style.AcubeStyle;
import com.sds.acube.app.design.style.AcubeStyleFactory;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;


/**
 * Class Name  : AcubeList.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.AcubeList.java
 */
public class AcubeList
{
    /**
	 */
    private AcubeListRow titleRow;
    private ArrayList<AcubeListRow> rows;
    private int nRow;
    private int nCol;
    private HashMap<String, String> attributes;

    private String[] arrColumnWidth;
    private String[] arrColumnAlign;

    private String styleFile;
    /**
	 */
    private AcubeStyle style;
    private boolean bPrintRowHeadSymbol;
    private boolean bGeneratePageNavigator;
    /**
	 */
    private AcubePageNavigator pageNavigator;

    private boolean bNoData = false;

    private String portletType = null;
    private String moreFuction = null;

    private boolean bListWithScroll = false;
    private boolean bTableBorder = false;
    private int nScrollHeight;

    /** 생성자 */
    public AcubeList(int nRow, int nCol)
    {
	this.nRow = nRow;
	this.nCol = nCol;

	rows = new ArrayList<AcubeListRow>();
	arrColumnWidth = new String[nCol];
	arrColumnAlign = new String[nCol];

	styleFile = "/app/properties/acube_design.properties";
	bGeneratePageNavigator = true;

	attributes = new HashMap<String, String>();
	attributes.put("width", getStyle().getString("list.width"));
	attributes.put("border", "0");
	attributes.put("cellspacing", "0");
	attributes.put("cellpadding", "0");
    }

    public void setAttribute(String name, int value) { setAttribute(name, ""+value); }
    public void setAttribute(String name, String value) { attributes.put(name, value); }
    public String getAttribute(String name) { return (String) attributes.get(name); }

    public void printRowHeadSymbol(boolean value) { bPrintRowHeadSymbol = value; }

    /**
	 * <pre>  설명 </pre>
	 * @param styleFile
	 * @see   
	 */
    public void setStyleFile(String styleFile)
    {
	this.styleFile = styleFile;
	attributes.put("width", getStyle(true).getString("list.width"));
	attributes.put("style", getStyle().getString("list.style_table"));
	getPageNavigator().setStyle(getStyle());
    }

    public void applyPortletStyle()
    {
	applyPortletStyle("normal");
    }

    public void applyPortletStyle(String type)
    {

	setPortletType(type);

	if (type.equalsIgnoreCase("simple")) {
	    bPrintRowHeadSymbol = true;
	    setStyleFile("/app/properties/acube_portlet_design.properties");
	} else {
	    setStyleFile("/app/properties/acube_design.properties");
	    attributes.put("style", "table-layout:fixed;");
	}

	attributes.put("cellspacing", "0");

    }

    /**
	 * <pre>  설명 </pre>
	 * @param type
	 * @see   
	 */
    public void setPortletType(String type)
    {
	this.portletType = type;
    }

    public void setPortletMore(String value)
    {
	this.moreFuction = value;
	generatePageNavigator(false);
    }

    public void generatePageNavigator(boolean bGenerate) {
	bGeneratePageNavigator = bGenerate;
    }

    public void setCurrentPage(int currPage) {
	getPageNavigator().setCurrentPage(currPage);
    }

    public void setTotalCount(int totalCount) {
	getPageNavigator().setTotalCount(totalCount);
    }

    public void setNavigationType(String value) {
	getPageNavigator().setNavigationType(value);
    }

    public void setPageDisplay(boolean bDisplay) {
	getPageNavigator().setPageDisplay(bDisplay);
    }

    public void setTotalCountDisplay(boolean bDisplay) {
	getPageNavigator().setTotalCountDisplay(bDisplay);
    }

    public void setColumnWidth(String widthInfo)
    {
	StringTokenizer token = new StringTokenizer(widthInfo, ", ");
	for (int i=0; token.hasMoreTokens(); i++) {
	    arrColumnWidth[i] = token.nextToken();
	}
    }

    public void setColumnAlign(String alignInfo)
    {
	StringTokenizer token = new StringTokenizer(alignInfo, ", ");
	for (int i=0; token.hasMoreTokens(); i++) {
	    arrColumnAlign[i] = token.nextToken();
	}
    }

    public void setListWithScroll()
    {
	setListWithScroll(265);
    }

    public void setListWithScroll(int nScrollHeight)
    {
	bListWithScroll = true;
	this.nScrollHeight = nScrollHeight;
    }

    public void setTableBorder(boolean btableborder)
    {
	bTableBorder = btableborder;
    }

    public AcubeListRow createTitleRow()
    {
	this.titleRow = createStyleClassRow(getStyle().getString("list.class.cell_title"));

	return titleRow;
    }


    public AcubeListRow createDataNotFoundRow()
    {
	bNoData = true;

	AcubeListRow row = new AcubeListRow(nCol, getStyle());
	row.printRowHeadSymbol(false);
	row.setAttribute(0, "colspan", nCol);
	row.setAllCellAttribute("height", "30");

	if (portletType == null)
	{
	    row.setAllCellAttribute("class", "communi_text");
	} else {
	    row.setAllCellAttribute("class", "portlet_text_c");
	}

	row.setAllCellAttribute("align", "center");
	rows.add(row);
	return row;
    }

    public AcubeListRow createStyleClassRow(String styleClass)
    {
	AcubeListRow row = new AcubeListRow(nCol, getStyle());
	row.printRowHeadSymbol(bPrintRowHeadSymbol);
	row.setPortletType(this.portletType);
	row.setAllCellAttribute("class", styleClass);

	return row;
    }


    public AcubeListRow createRow()
    {
	AcubeListRow row = new AcubeListRow(nCol, getStyle());
	row.printRowHeadSymbol(bPrintRowHeadSymbol);
	row.setPortletType(this.portletType);

	for (int i=0; i < nCol; i++)
	{
	    try {
		row.setAttribute(i, "class", getStyle().getString("list.class.cell_"+arrColumnAlign[i]));
	    }
	    catch (Exception e) {
		LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
	    }
	}
	rows.add(row);
	
	return row;
    }
    
    /**
     * 
     * <pre> 
     *  nobr 을 없애기 위해 bSingleLine 추가하여 선언
     * </pre>
     * 
     * @param bSingleLine
     * @return
     * @see  
     *
     */
    public AcubeListRow createRow(boolean bSingleLine)
    {
	AcubeListRow row = new AcubeListRow(nCol, getStyle(), bSingleLine);
	row.printRowHeadSymbol(bPrintRowHeadSymbol);
	row.setPortletType(this.portletType);

	for (int i=0; i < nCol; i++)
	{
	    try {
		row.setAttribute(i, "class", getStyle().getString("list.class.cell_"+arrColumnAlign[i]));
	    }
	    catch (Exception e) {
		LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
	    }
	}
	rows.add(row);
	
	return row;
    }

    /**
     *   페이지 출력 */

    public void pageGenerate(JspWriter out)
    {
	try
	{
	    getPageNavigator().generate(new PrintWriter(out));
	}
	catch (Exception e) {
	    LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
	}
    }


    /**
     *   목록 출력 */
    public void generate(JspWriter out)
    {
	try
	{
	    generate(new PrintWriter(out));
	}
	catch (Exception e) {
	    LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
	}
    }

    /**
     *   목록 출력 */
    public void generate(PrintWriter out)
    {
	try
	{
	    StringBuffer buff = new StringBuffer();

	    buff.append("<table");

	    String attrValue = null;
	    Set total = attributes.entrySet();
	    Iterator iter = total.iterator();
	    while (iter.hasNext())
	    {
		Map.Entry e = (Map.Entry) iter.next();
		attrValue = (String) e.getValue();

		if (attrValue != null && attrValue.length() > 0) {
		    buff.append(" " + e.getKey() + "=\"" + e.getValue() + "\"");
		}
	    }

	    if (bListWithScroll) {
		//                out.println("<div style='height:23px;overflow-y:scroll;scrollbar-base-color:#FFFFFF;scrollbar-face-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF;scrollbar-3dlight-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF;'>");
		out.println("<div style='height:"+nScrollHeight+"px;overflow-y:auto' onscroll='javascript:this.firstChild.style.top = this.scrollTop;' >");
	    }

	    if (portletType == null)
	    {
		if (bTableBorder) {
		    out.println(buff.toString() + " class=\"table_border\" ");
		} else {
		    out.println(buff.toString() + " class=\"table\" ");
		}
		if (bListWithScroll) {
		    out.println(" style='position:absolute;left:0px;top:0px;z-index:10;'");
		}
		out.println(" >");
	    } else {
		out.println(buff.toString() + " >");
	    }

	    if (titleRow != null)
	    {
		setFirstRowAttribute(titleRow);
		out.println("<thead>");
		titleRow.generate(out);
		out.println("</thead>");
	    }

	    out.println("<tbody>");

	    if (bNoData)
	    {
		if (portletType == null)
		{
		    out.println("</table>");
		    out.println("<table width='100%' border='0' cellpadding='0' cellspacing='0'>");
		    out.println("<tr>");
		    out.println("<td height='10'></td>");
		    out.println("</tr>");
		    out.println("</table>");
		    out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='table_border'>");
		} else {
		    out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		}
	    }

	    if (bListWithScroll) {
		out.println("</table>");
		//                    out.println("</div>");
		//                    out.println("<div style='height:"+nScrollHeight+"px;overflow-y:scroll'>");
		out.println(buff.toString() + " class=\"table_body\" ");
		if (bListWithScroll) {
		    out.println(" style='position:absolute;left:0px;top:30px;z-index:1;'");
		}
		out.println(" >");
	    }

	    int rowCount = rows.size();
	    AcubeListRow row = null;
	    String classValue = null;
	    String previousClassValue = null;
	    String rowMouseOverBgColor = null;
	    int nOddEven = 1;
	    for (int i=0; i < rowCount; i++)
	    {
		row = (AcubeListRow) rows.get(i);

		if (i == 0 && (titleRow == null || bListWithScroll)) {
		    setFirstRowAttribute(row);
		}

		// row의 class 설정
		classValue = row.getAttribute("class");
		if (classValue == null)
		{
		    if (i == (rowCount-1)) {
			classValue = getStyle().getString("list.class.row_last");
		    }

		    if (classValue == null || classValue.length() == 0)
		    {
			if (row.applyOddEven())
			{
			    classValue = (nOddEven%2 == 0) ? getStyle().getString("list.class.row_even") :
				getStyle().getString("list.class.row_odd");
			    nOddEven++;
			}
			else {
			    classValue = previousClassValue;
			}
		    }

		    row.setAttribute("class", classValue);
		}
		previousClassValue = classValue;


		// row에 mouse over시 background 색상.
		rowMouseOverBgColor = getStyle().getString("list.row_mouse_over_bgcolor");
		if (rowMouseOverBgColor != null && rowMouseOverBgColor.length() > 0)
		{
		    row.setAttribute("onMouseOver", "this.style.backgroundColor='"+rowMouseOverBgColor+"'");
		    row.setAttribute("onMouseOut", "this.style.backgroundColor=''");
		}

		row.generate(out);
	    }

	    if (moreFuction != null && !bNoData) {
		out.println("<tr>");
		out.print("<td colspan='"+nCol+"' class='portlet_text' align='right'><a href='#none' onclick='"+moreFuction+"'>");
		out.println("<img src='"+AppConfig.getProperty("web_uri", "", "path") + getStyle().getString("image.home")+"/more.gif' width='39' height='16' border='0'></a>");
		if ((portletType != null) && (portletType.equalsIgnoreCase("normal"))) {
		    out.print("&nbsp;");
		}
		out.println("</td></tr>");
	    }

	    out.println("</tbody>");

	    if (bGeneratePageNavigator && !bNoData && !bListWithScroll) {
		out.println("<tfoot>");

		out.print("<tr align='center'> ");
		out.println(" <td colspan='"+nCol+"' class='paging_bg'>");
		getPageNavigator().generate(out);
		out.print("</td> ");
		out.print("</tr> ");

		out.println("</tfoot>");
	    }

	    out.println("</table>");

	    if (bListWithScroll) {
		out.println("</div>");
	    }
	}
	catch (Exception e) {
	    LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
	}
    }



    ///////////////////////////////////////////////////////////////////////////
    // private methods...

    private void setFirstRowAttribute(AcubeListRow row)
    throws Exception
    {
	for (int i=0; i < nCol; i++)
	{
	    if (arrColumnWidth[i] != null) {
		row.setAttribute(i, "width", arrColumnWidth[i]);
	    }
	}
    }


    ///////////////////////////////////////////////////////////////////////////
    // external ojbect...

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public AcubeStyle getStyle()
    {
	return getStyle(false);
    }

    public AcubeStyle getStyle(boolean bNewStyle)
    {
	if (style == null || bNewStyle) {
	    style = AcubeStyleFactory.getInstance().getStyle(styleFile);
	}
	return style;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public AcubePageNavigator getPageNavigator()
    {
	if (pageNavigator == null)
	{
	    pageNavigator = new AcubePageNavigator(styleFile);
	    pageNavigator.setMaxRow(nRow);
	}
	return pageNavigator;
    }
}
