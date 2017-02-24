package com.sds.acube.app.design;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.design.style.AcubeStyle;
import com.sds.acube.app.design.style.AcubeStyleFactory;


/**
 * Class Name  : AcubeListCell.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.AcubeListCell.java
 */
public class AcubeListCell
{
    private HashMap<String, String> attributes;
    private String cellData;
    /**
	 */
    private AcubeStyle acubeStyle;
    private boolean bSingleLine;

    public AcubeListCell() {
        this(AcubeStyleFactory.getInstance().getStyle());
    }

    public AcubeListCell(AcubeStyle style)
    {
        this.acubeStyle = style;
        attributes = new HashMap<String, String>();
        bSingleLine = true;
    }
    
    /**
     * <pre>
     *  nobr 을 없애기 위해 bSingleLine 추가하여 선언
     * </pre>
     * 
     * @param style
     * @param bSingleLine
     */
    public AcubeListCell(AcubeStyle style, boolean bSingleLine)
    {
        this.acubeStyle = style;
        attributes = new HashMap<String, String>();
        this.bSingleLine = bSingleLine;
    }


    public void setData(String data) { cellData = data; }

    public void setSingleLine(boolean value) { bSingleLine = value; }
    public boolean getSingleLine() { return bSingleLine; }

    public void setAttribute(String name, String value) { attributes.put(name, value); }
    public String getAttribute(String name) { return (String) attributes.get(name); }

    public void addAttribute(String name, String value)
    {
        String previousValue = (String) attributes.get(name);

        if (previousValue != null) {
            value = previousValue + value;
        }

        attributes.put(name, value);
    }

    public AcubeStyle getStyle() { return acubeStyle; }
    public void setStyle(AcubeStyle value) { acubeStyle = value; }



    /**
    *   1셀 출력 */
    public void generate(PrintWriter out)
            throws Exception
    {
        generate(out, false);
    }

    public void generate(PrintWriter out, boolean bPrintRowHeadSymbol)
            throws Exception
    {
        if (cellData == null) {
            return;
        }

        out.print("<td ");

        Set total = attributes.entrySet();
        Iterator i = total.iterator();
        String attrName = null;
        boolean hasStyleAttr = false;
        while (i.hasNext())
        {
            Map.Entry e = (Map.Entry) i.next();
            attrName = (String) e.getKey();
            String tempValue = (String)e.getValue();
            
            // 속성이 style인 경우 overflow:hidden 속성을 추가한다.
            // table-layout:fixed가 FF, Safari에서 제대로 작성하지 않는걸 보정함            
            if("style".equals(attrName)) {
            	if(tempValue.indexOf("overflow") == -1) {
            		tempValue = "overflow:hidden;" + tempValue;
            	}
            	hasStyleAttr = true;
            } 
            out.print(" " + attrName + "=\"" + tempValue + "\"");
        }
        if(!hasStyleAttr) {
        	out.print(" style='overflow:hidden;' ");
        }
        
        out.print(" >");
        if (bSingleLine) {
            out.print("<nobr>");
        }

        if (bPrintRowHeadSymbol)
        {
            out.println("<img src='"+AppConfig.getProperty("web_uri", "", "path") + acubeStyle.getString("list.row_header_icon")+"'>");
        }

        out.print((cellData.length() == 0) ? "&nbsp;" : cellData);
        if (bSingleLine) {
            out.print("</nobr>");
        }
        out.println("</td>");
    }
}
