package com.sds.acube.app.idir.org.bpm;


public class NavigationUtil {
    
    private int total_count = 0;
    private int count_per_page = 1;
    private int current_page = 1;
    private int total_page = 1;
    
    private int total_group = 1;
    private int current_group = 1;
    private int start_page = 1;
    private int end_page = 1;
    
    public NavigationUtil(int total_count, int count_per_page, int current_page) {
        this.total_count = total_count;
        this.count_per_page = count_per_page;
        this.current_page = current_page;
        setConfig();
    }
    
    private void setConfig() {
        this.total_page = ((this.total_count - 1) / this.count_per_page) + 1;
        this.total_group = (this.total_page - 1) / 10;
        this.current_group = (this.current_page - 1) / 10;
        this.start_page = (this.current_group * 10) + 1;
        if (this.current_group < this.total_group) {
            this.end_page = this.start_page + 9;
        } else {
            this.end_page = this.total_page;
        }
    }

    public String createHtml() {
        StringBuffer result = new StringBuffer();

        result.append("\n");
        result.append("        <!---------- 페이지이동정보 S ---------->\n");
        result.append("        <tr>\n");
        result.append("          <td>\n");
        result.append("            <!---------- 페이지이동정보 Table S ---------->\n");
        result.append("            <table height=16 border=0 cellpadding=0 cellspacing=0 align=center>\n");
        result.append("              <tr>\n");
        result.append("                <td height=16>\n");
        if (this.total_group > 1) {
            result.append("                <a href=\"javascript:movePage('" + (((this.current_group - 1) * 10) + 1) + "');\"><img src=\"" 
                    + "/acubeidir/image/2.0/icon_prevend.gif\" width=13 height=13 border=0></a>\n");
        } else {
            result.append("                <img src=\"/acubeidir/image/2.0/icon_prevend_invalid.gif\" width=13 height=13 border=0>\n");
        }
        result.append("                </td>\n");
        result.append("                <td width=3></td>\n");
        result.append("                <td>\n");
        if (this.current_page > 1) {
            result.append("                <a href=\"javascript:movePage('" + (this.current_page - 1) + "');\"><img src=\"" 
                    + "/acubeidir/image/2.0/icon_prev.gif\" width=13 height=13 border=0></a>\n");
        } else {
            result.append("                <img src=\"/acubeidir/image/2.0/icon_prev_invalid.gif\" width=13 height=13 border=0>\n");
        }
        result.append("                </td>\n");
        result.append("                <td class=text_center>\n");
        
        for (int i = this.start_page; i <= this.end_page; i++) {
            if (i == this.current_page) {
                result.append(" | <b>" + i + "</b>");
            } else {
                result.append(" | <a href=\"javascript:movePage('" + i + "')\">" + i + "<a>");
            }
        }
        result.append(" | ");
        result.append("                </td>\n");
        result.append("                <td height=16>\n");
        
        if (this.current_page < this.total_page) {
            result.append("                <a href=\"javascript:movePage('" + (this.current_page + 1) + "');\"><img src=\"" 
                    + "/acubeidir/image/2.0/icon_next.gif\" width=13 height=13 border=0></a>\n");
        } else {
            result.append("                <img src=\"/acubeidir/image/2.0/icon_next_invalid.gif\" width=13 height=13 border=0>\n");
        }
        result.append("                </td>\n");
        result.append("                <td width=3></td>\n");
        result.append("                <td>\n");
        
        if (this.current_group < this.total_group) {
            result.append("                <a href=\"javascript:movePage('" + (((this.current_group + 1) * 10) + 1) + "');\"><img src=\"" 
                    + "/acubeidir/image/2.0/icon_nextend.gif\" width=13 height=13 border=0></a>\n");
        } else {
            result.append("                <img src=\"/acubeidir/image/2.0/icon_nextend_invalid.gif\" width=13 height=13 border=0>\n");
        }
        result.append("                </td>\n");
        result.append("              </tr>\n");
        result.append("            </table>\n");
        result.append("            <!---------- 페이지이동정보 Table E ---------->\n");
        result.append("          </td>\n");
        result.append("        </tr>\n");
        result.append("        <!------ 페이지이동정보 E --------->\n");

        return result.toString();
    }
    

}
