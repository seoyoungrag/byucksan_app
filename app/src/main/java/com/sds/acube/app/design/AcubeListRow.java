package com.sds.acube.app.design;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.design.style.AcubeStyle;


/**
 * Class Name  : AcubeListRow.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.AcubeListRow.java
 */
public class AcubeListRow
{
    /**
	 */
    private AcubeListCell[] cells;
    private HashMap<String, String> attributes;
    /**
	 */
    private AcubeStyle acubeStyle;

    private boolean bApplyOddEven;
    private boolean bPrintRowHeadSymbol;

    private String portletType;

    public AcubeListRow(int cellCount)
    {
        this(cellCount, null);
    }

    public AcubeListRow(int cellCount, AcubeStyle style)
    {
        this.acubeStyle = style;

        cells = new AcubeListCell[cellCount];
        for (int i=0; i < cellCount; i++) {
            cells[i] = new AcubeListCell(style);
        }

        attributes = new HashMap<String, String>();
        bApplyOddEven = true;
    }
    
    /**
     * <pre>
     *  nobr 을 없애기 위해 bSingleLine 추가하여 선언
     * </pre>
     * 
     * @param cellCount
     * @param style
     * @param bSingleLine
     */
    public AcubeListRow(int cellCount, AcubeStyle style, boolean bSingleLine)
    {
        this.acubeStyle = style;

        cells = new AcubeListCell[cellCount];
        for (int i=0; i < cellCount; i++) {
            cells[i] = new AcubeListCell(style, bSingleLine);
        }

        attributes = new HashMap<String, String>();
        bApplyOddEven = true;
    }


    public void setAttribute(String name, int value) { setAttribute(name, ""+value); }
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

    public boolean applyOddEven() { return bApplyOddEven; }
    public void applyOddEven(boolean value) { bApplyOddEven = value; }

    public boolean printRowHeadSymbol() { return bPrintRowHeadSymbol; }
    public void printRowHeadSymbol(boolean value) { bPrintRowHeadSymbol = value; }

    /**
	 * <pre>  설명 </pre>
	 * @param type
	 * @see   
	 */
    public void setPortletType(String type) {this.portletType = type;}
     
    // cell 관리
    public void setData(int nCell, int data) {
        cells[nCell].setData(""+data);
    }
    public void setData(int nCell, String data) {
        cells[nCell].setData(data);
    }

    public void addAttribute(int nCell, String name, String value) {
        cells[nCell].addAttribute(name, value);
    }
    public void setAttribute(int nCell, String name, int value) {
        setAttribute(nCell, name, ""+value);
    }
    public void setAttribute(int nCell, String name, String value) {
        cells[nCell].setAttribute(name, value);
    }
    public String getAttribute(int nCell, String name) {
        return cells[nCell].getAttribute(name);
    }

    public void setAllCellAttribute(String name, String value)
    {
        for (int i=0; i < cells.length; i++) {
            cells[i].setAttribute(name, value);
        }
    }

    public void setSingleLine(int nCell, boolean value) {
        cells[nCell].setSingleLine(value);
    }




    /**
    *   1행 출력 */
    public void generate(PrintWriter out)
            throws Exception
    {
        out.print("<tr bgcolor='#ffffff'");

        String attrValue = null;
        Set total = attributes.entrySet();
        Iterator iter = total.iterator();
        while (iter.hasNext())
        {
            Map.Entry e = (Map.Entry) iter.next();
            attrValue = (String) e.getValue();
            if (attrValue != null && attrValue.length() > 0) {
                out.print(" " + e.getKey() + "=\"" + e.getValue() + "\"");
            }
        }
        out.println(">");

        int cellCount = cells.length;
        for (int i=0; i < cellCount; i++)
        {
            if (i == 0) {
                cells[i].generate(out, bPrintRowHeadSymbol);
            }
            else {
                cells[i].generate(out);
            }
        }

        out.println("</tr>");
        
        if (portletType != null) {
            if (portletType.equalsIgnoreCase("simple") || portletType.equalsIgnoreCase("normal") ) {
                out.println("<tr><td height='1' colspan='"+cellCount+"' background='"+AppConfig.getProperty("web_uri", "", "path") + getStyle().getString("image.home")+"/dot_search2.gif'></td></tr>");
            }
        }       
    }
}
