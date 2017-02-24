package com.sds.acube.app.design;

import java.io.PrintWriter;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.design.style.AcubeStyle;
import com.sds.acube.app.design.style.AcubeStyleFactory;


/**
 * Class Name  : AcubePageNavigator.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.AcubePageNavigator.java
 */
public class AcubePageNavigator
{
    /**
	 */
    private AcubeStyle style;

    private int nUpperSpace;
    private int nCurrentPage = 1;
    private int nMaxPage;
    private int nMaxRow = 10;
    private int nTotalCount = 0;
    private String pageFunction;
    private String navigationType;
    private boolean bPageDisplay = true;
    private boolean bTotalCountDisplay = true;


    public AcubePageNavigator() {
        this("/app/properties/acube_design.properties");
    }

    public AcubePageNavigator(String styleFile)
    {
        style = AcubeStyleFactory.getInstance().getStyle(styleFile);
    }


    public void setUpperSpace(int value) { this.nUpperSpace = value; }
    public void setCurrentPage(int value) { this.nCurrentPage = value; }
    public void setMaxPage(int value) { this.nMaxPage = value; }
    public void setMaxRow(int value) { this.nMaxRow = value; }
    public void setTotalCount(int value) { this.nTotalCount = value; }
    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setPageFunction(String value) { this.pageFunction = value; }
    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setNavigationType(String value) { this.navigationType = value; }
    public void setPageDisplay(boolean bDisplay) { this.bPageDisplay = bDisplay; }
    public void setTotalCountDisplay(boolean bDisplay) { this.bTotalCountDisplay = bDisplay; }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public AcubeStyle getStyle() { return style; }
    /**
	 * <pre>  설명 </pre>
	 * @param value
	 * @see   
	 */
    public void setStyle(AcubeStyle value) { style = value; }


    public void generate(PrintWriter out)
            throws Exception
    {
        nUpperSpace = (nUpperSpace != 0) ? nUpperSpace : style.getInt("page.upper_space");
        nMaxPage = (nMaxPage != 0) ? nMaxPage : style.getInt("page.max_page_count");
        pageFunction = (pageFunction != null) ? pageFunction : style.getString("page.change_function");
        navigationType = (navigationType != null) ? navigationType : style.getString("page.navigator.type");

        if (navigationType.equalsIgnoreCase("simple")) {
            generateSimple(out);
        }
        else {
            generateNormal(out);
        }
    }

    public void generateSimple(PrintWriter out)
            throws Exception
    {
        if (nTotalCount <= nMaxRow) {
            return;
        }

		String imgPriv    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv");
		String imgNext    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next");

        out.println("<table border='0' width='" + style.getString("list.width") + "'>");
        out.println("<tr align='center'>");
        out.println("<td>");

        // [>]
        if (nCurrentPage == 1)
        {
            out.print("<img src='"+imgNext+"' alt='"+(nCurrentPage+1)+"' style='cursor:pointer'");
            out.println(" onClick=\""+pageFunction+"('"+(nCurrentPage+1)+"');\">");
        }
        // [<]
        else if ((nCurrentPage * nMaxRow ) >= nTotalCount)
        {
            out.print("<img src='"+imgPriv+"' alt='"+(nCurrentPage-1)+"' style='cursor:pointer'");
            out.println(" onClick=\""+pageFunction+"('"+(nCurrentPage-1)+"');\">");
        }
        // [<  >]
        else
        {
            out.print("<img src='"+imgPriv+"' alt='"+(nCurrentPage-1)+"' style='cursor:pointer'");
            out.println(" onClick=\""+pageFunction+"('"+(nCurrentPage-1)+"');\">");
            out.println("&nbsp;&nbsp;&nbsp;");
            out.print("<img src='"+imgNext+"' alt='"+(nCurrentPage+1)+"' style='cursor:pointer'");
            out.println(" onClick=\""+pageFunction+"('"+(nCurrentPage+1)+"');\">");
        }

        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
    }


    public void generateNormal(PrintWriter out)
            throws Exception
    {
		//	calculate...
		int nTotalPage = (nTotalCount-1) / nMaxRow + 1;
		int nPageS = ((nCurrentPage-1) / nMaxPage) * nMaxPage + 1;
		int nPageE = nPageS + nMaxPage - 1;
		if (nPageE > nTotalPage) {
			nPageE = nTotalPage;
		}

		String imgPath = AppConfig.getProperty("web_uri", "", "path") + style.getString("image.home");

		String imgPrivEnd = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_end");
		String imgPrivMid = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_mid");
		String imgPriv    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv");

		String imgNext    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next");
		String imgNextMid = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_mid");
		String imgNextEnd = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_end");

		String imgLine   = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.line");

		/*
		String imgPrivEnd_on = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_end.on", style.getString("page.img.priv_end"));
		String imgPrivMid_on = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_end.on", style.getString("page.img.priv_mid"));
		String imgPriv_on    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv.on", style.getString("page.img.priv"));

		String imgNext_on    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next.on", style.getString("page.img.next"));
		String imgNextMid_on = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_end.on", style.getString("page.img.next_mid"));
		String imgNextEnd_on = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_end.on", style.getString("page.img.next_end"));
		*/

		String imgPrivEnd_off = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_end.off", style.getString("page.img.priv_end"));
		String imgPrivMid_off = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv_end.off", style.getString("page.img.priv_mid"));
		String imgPriv_off    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.priv.off", style.getString("page.img.priv"));

		String imgNext_off    = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next.off", style.getString("page.img.next"));
		String imgNextMid_off = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_end.off", style.getString("page.img.next_mid"));
		String imgNextEnd_off = AppConfig.getProperty("web_uri", "", "path") + style.getString("page.img.next_end.off", style.getString("page.img.next_end"));

		String strPageDisplayWidth = "100%";
		if (bPageDisplay || bTotalCountDisplay) strPageDisplayWidth = "50%";

        out.println("<table width='100%'  border='0' cellspacing='0' cellpadding='0'>");
        out.println("  <tr>");
        if (bPageDisplay || bTotalCountDisplay) {
            out.println("    <td width='25%'>");
            if (bPageDisplay) {
                out.println("      <table border='0' cellpadding='0' cellspacing='0'>");
                out.println("        <tr>");
                out.println("          <td width='5'></td>");
                out.println("          <td class='paging_result'>Page:"+nCurrentPage+"/"+nTotalPage+" </td>");
                out.println("          <td width='5'></td>");
                out.println("          <td style='padding-bottom:2px;'><input name='userinputpage' id='userinputpage' type='text' class='inputpage' size='3' maxlength='6' style='IME-MODE:inactive;' onkeyup='javascript:if(isNaN(this.value)){ this.value=\"\"; this.focus(); };if(isNaN(this.value)){ this.value=\"\"; this.focus(); }' onkeydown='javascript:if(event.keyCode==13 || event.which==13) { document.getElementById(\"userinputbutton\").onclick();return(false);}'></td>");
                out.println("          <td><a id='userinputbutton' href='#' onClick=\"javascript:if(isNaN(document.getElementById('userinputpage').value) || document.getElementById('userinputpage').value<1) document.getElementById('userinputpage').value=1; if(document.getElementById('userinputpage').value>"+nTotalPage+") {document.getElementById('userinputpage').value="+nTotalPage+";}"+pageFunction+"(document.getElementById('userinputpage').value);\" ><img src='"+imgPath+"/btn_pagego.gif' border='0' style='cursor:pointer' alt='go'></a></td>");
                out.println("        </tr>");
                out.println("      </table>");
            } else {
                out.println("&nbsp;");
            }
            out.println("    </td>");
        }

        out.println("    <td width='"+strPageDisplayWidth+"' class='paging_bg' align='center'>");

        out.println("<table height='21' border='0'  cellspacing='0' cellpadding='0' width='" + style.getString("list.width") + "'>");
        out.println(" <tr>");
        out.println("   <td style='padding-top:3px;'>");

        out.println("     <table align='center' border='0' cellspacing='0' cellpadding='0'>");
        out.println("       <tr class='paging_bg'>");

		// [<<<]
		if (nCurrentPage > 1)
		{
            out.println("         <td><img border='0' alt='First' src='"+imgPrivEnd+"' style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('1');}\" onClick=\""+pageFunction+"('1');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='First' src='"+imgPrivEnd_off+"'>");
            out.println("         </td>");
		}

		// [<<]
		if (nCurrentPage > nMaxPage)
		{
            out.println("         <td><img border='0' alt='Prev 10' src='"+imgPrivMid+"'  style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('"+(nPageS-nMaxPage)+"');}\" onClick=\""+pageFunction+"('"+(nPageS-nMaxPage)+"');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='Prev 10' src='"+imgPrivMid_off+"'>");
            out.println("         </td>");
		}

		// [<]
		if (nCurrentPage > 1)
		{
            out.println("         <td><img border='0' alt='Prev' src='"+imgPriv+"'  style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('"+(nCurrentPage-1)+"');}\" onClick=\""+pageFunction+"('"+(nCurrentPage-1)+"');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='Prev' src='"+imgPriv_off+"'>");
            out.println("         </td>");
		}

        out.println("         <td>");
        out.println("           <table border='0' cellpadding='0' cellspacing='0'>");
        out.println("             <tr class='paging_bg'>");
        out.println("               <td class='paging'>");

		// [1][2][3]...
		for (int i=nPageS; i<=nPageE; i++)
		{
			if (i == nCurrentPage)
			{
			      out.print("<span class='paging_select'>"+i+"</span>");
			}
			else
			{
			      out.print("<a href=\"javascript:"+pageFunction+"("+i+");\">"+i+"</a>");			      
			}
			if (i < nPageE)
            {
                out.print("<img src='"+imgLine+"' width='5' height='10'>");
            }
		}

        out.println("</td>");
        out.println("             </tr>");
        out.println("           </table></td>");

        // [>]
		if (nCurrentPage < nTotalPage)
		{
            out.println("         <td><img border='0' alt='Next' src='"+imgNext+"'  style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('"+(nCurrentPage+1)+"');}\" onClick=\""+pageFunction+"('"+(nCurrentPage+1)+"');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='Next' src='"+imgNext_off+"'>");
            out.println("         </td>");
		}

        // [>>]
		if (nPageE < nTotalPage)
		{
            out.println("         <td><img border='0' alt='Next 10' src='"+imgNextMid+"'  style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('"+(nPageE+1)+"');}\" onClick=\""+pageFunction+"('"+(nPageE+1)+"');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='Next 10' src='"+imgNextMid_off+"'>");
            out.println("         </td>");
		}

		// [>>>]
		if (nTotalPage > 1)
		{
            out.println("         <td><img border='0' alt='Last' src='"+imgNextEnd+"' style='cursor:pointer'");
            out.println("		       tabindex=\"0\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {"+pageFunction+"('"+nTotalPage+"');}\" onClick=\""+pageFunction+"('"+nTotalPage+"');\"");
            out.println("              >");
            out.println("         </td>");
		}
		else
		{
            out.println("         <td><img border='0' alt='Last' src='"+imgNextEnd_off+"'>");
            out.println("         </td>");
		}

        out.println("       </tr>");
        out.println("     </table>");

        out.println("    </td>");
        out.println("  </tr>");
        out.println("</table>");

        out.println("    </td>");
        if (bPageDisplay || bTotalCountDisplay) {
            out.println("    <td width='25%' align='right'>");
            if (bTotalCountDisplay) {
                out.println("      <table border='0' cellpadding='0' cellspacing='0'>");
                out.println("        <tr>");
                out.println("          <td class='paging_result'>Total:"+nTotalCount+"</td>");
                out.println("          <td width='5'></td>");
                out.println("        </tr>");
                out.println("      </table> ");
            } else {
                out.println("&nbsp;");
            }
            out.println("    </td>");
        }
        out.println("  </tr>");
        out.println("</table>");

        out.println("");
    }
}
