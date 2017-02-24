package com.sds.acube.app.design;

import java.util.ArrayList;
import sutil.Application;

//import sutil.*;

/**
 * @author wonjung jang
 */
public class AcubeTree {
    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTreeAll(ArrayList allNodes, boolean usecheckbox) {
        return getTreeAll(allNodes, usecheckbox, "", true);
    }

    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTreeAll(ArrayList allNodes, boolean usecheckbox, String openid) {
        return getTreeAll(allNodes, usecheckbox, openid, true);
    }

    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTreeAll(ArrayList allNodes, boolean usecheckbox, String openid, boolean displayRoot) {
        StringBuffer strHtml = new StringBuffer();
        int BeforeDepth = 0;
        int startSeed = 0;

        if(!displayRoot) startSeed = 1;

        for (int i = startSeed; i < allNodes.size(); i++) {
            CMTree tree = (CMTree)allNodes.get(i);
            for (int k = tree.getDepth(); k < BeforeDepth; k++) {
                strHtml.append("</div></div>\n");
            }
            BeforeDepth = tree.getDepth();
            strHtml.append("<div id=\"PARENT_").append(tree.getParentid()).append("_").append(tree.getSequence());
            strHtml.append("\" value=\"").append(tree.getId()).append("\" >");
            strHtml.append("<table border='0' cellpadding=0 cellspacing=0 id=\"").append(tree.getId()).append("\" parentid=\"");
            strHtml.append(tree.getParentid()).append("\" depth=\"").append(tree.getDepth()).append("\" sorder=\"");
            strHtml.append(tree.getSorder()).append("\"  isopen=\"").append(tree.getIsopen()).append("\" haschild=\"");
            strHtml.append(tree.getHaschild()).append("\" isFolder=\"").append(tree.getIsFolder());
            strHtml.append("\" sequence=\"").append(tree.getSequence()).append("\" usecheckbox=\"");
            strHtml.append(usecheckbox).append("\">\n");
            strHtml.append("  <tr height=22>\n");
            for (int j = 0; j < tree.getDepth(); j++) {
                strHtml.append("    <td width=16 height=22 nowrap>&nbsp;</td>\n");
            }
            strHtml.append("    <td width=16 height=22>");

            // 이미지 설정
            String minusImg = (tree.getMinusImage().equals("")) ? Application.getXmlString("TREE.IMAGE.MINUS","") : tree.getMinusImage();
            String plusImg = (tree.getPlusImage().equals("")) ? Application.getXmlString("TREE.IMAGE.PLUS","") : tree.getPlusImage();
            String dotImg = (tree.getDotImage().equals("")) ? Application.getXmlString("TREE.IMAGE.DOT","") : tree.getDotImage();
            /*
            String minusImg = tree.getMinusImage();
            String plusImg = tree.getPlusImage();
            String dotImg = tree.getDotImage();
            */

            if (tree.getHaschild()) {
                if (tree.getIsopen()) {
                    strHtml.append("<img src=\"");
                    strHtml.append(minusImg).append("\" ");
                    strHtml.append(" tabIndex=\"0\" id='IMG_OPEN_").append(tree.getId()).append("' onclick=\"javascript:showSubTree('");
                    strHtml.append(tree.getId()).append("');\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {showSubTree('"+tree.getId()+"');}\" style='cursor:pointer;'>");
                } else {
                    strHtml.append("<img src=\"");
                    strHtml.append(plusImg).append("\" ");
                    strHtml.append(" tabIndex=\"0\" id=IMG_OPEN_").append(tree.getId()).append(" onclick=\"javascript:showSubTree('");
                    strHtml.append(tree.getId()).append("');\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {showSubTree('"+tree.getId()+"');}\" style='cursor:pointer;'>");
                }
            } else {
                strHtml.append("<img src=\"");
                strHtml.append(dotImg).append("\" ");
                strHtml.append(" id=IMG_OPEN_").append(tree.getId()).append(" onclick=\"javascript:showSubTree('");
                strHtml.append(tree.getId()).append("');\" onKeyPress=\"javascript:if(event.keyCode==13 || event.which==13) {showSubTree('"+tree.getId()+"');}\">");
            }
            strHtml.append("</td>\n");
            strHtml.append("    <td height=22>");
            if(i != 0 && usecheckbox) {
                strHtml.append("<input type=checkbox name=CHECKBOX_").append(tree.getId());
                strHtml.append(" onclick=\"javascript:checkData('").append(tree.getId()).append("');\">");
            }
            if (tree.getBeforetext() != null && tree.getBeforetext() != "") {
                strHtml.append(tree.getBeforetext()).append("&nbsp;");
            }
            String style = "";
            if(tree.getId().equals(openid)) {
                style = "background-color:#2984be;color:#ffffff;";
            }
            //strHtml.append("<span id=TEXT_").append(tree.getId()).append(" height=22 style=\"cursor:pointer; border:'1px solid #ffffff';" + style + "\" class=").append(tree.getCssClass());
            strHtml.append("<span id=TEXT_").append(tree.getId()).append(" height=22 style=\"cursor:pointer;" + style + "\" class=").append(tree.getCssClass());
            if (tree.getTitle() != null && tree.getTitle() != "") {
                strHtml.append(" title=\"").append(tree.getTitle()).append("\"");
            }
            if (tree.getMouseover() != null && tree.getMouseover() != "") {
                strHtml.append(" onmouseover=\"javascript:").append(tree.getMouseover()).append("\"");
            }
            strHtml.append(" onclick=\"javascript:selectData('").append(tree.getId()).append("');");
            strHtml.append(tree.getHref()).append("\"><a href=# title='"+tree.getText()+"'><nobr>").append(tree.getText()).append("</nobr></a></span>\n");
            if (tree.getAftertext() != null && tree.getAftertext() != "") {
                strHtml.append(tree.getAftertext());
            }

            strHtml.append("</td>\n");
            strHtml.append("  </tr>\n");
            strHtml.append("</table>\n");
            if (tree.getHaschild()) {
                if (tree.getIsopen()) {
                    strHtml.append("<div id=DIV_").append(tree.getId()).append(">\n");
                } else {
                    strHtml.append("<div style=\"display:none;\" id=DIV_").append(tree.getId()).append(">\n");
                }
            } else {
                strHtml.append("<div style=\"display:none;\" id=DIV_").append(tree.getId()).append("></div></div>\n");
            }
            if ((i+1) == allNodes.size()) {
                strHtml.append("</div></div>\n");
            }
        }
        return strHtml.toString();
    }

    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTree(ArrayList allNodes, boolean usecheckbox) {
        return getTree(allNodes, usecheckbox, "", true);
    }

    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTree(ArrayList allNodes, boolean usecheckbox, String openid) {
        return getTree(allNodes, usecheckbox, openid, true);
    }

    /**
     * 화면에 출력할 HTML을 생성한다.
     * @param allNodes
     * @return
     */
    public static String getTree(ArrayList allNodes, boolean usecheckbox, String openid, boolean displayRoot) {

        StringBuffer strHtml = new StringBuffer();
        int BeforeDepth = 0;
        int startSeed = 0;

        if(!displayRoot) startSeed = 1;

        for (int i = startSeed; i < allNodes.size(); i++) {
            CMTree tree = (CMTree)allNodes.get(i);
            if(tree.getIsVirtual()) {
                continue;
            }
            for (int k = tree.getDepth(); k < BeforeDepth; k++) {
                strHtml.append("</div></div>\n");
            }
            BeforeDepth = tree.getDepth();

            strHtml.append("<div id=\"PARENT_").append(tree.getParentid()).append("_").append(tree.getSequence());
            strHtml.append("\" value=\"").append(tree.getId()).append("\" >");
            strHtml.append("<table cellpadding=0 cellspacing=0 id=\"").append(tree.getId()).append("\" parentid=\"");
            strHtml.append(tree.getParentid()).append("\" depth=\"").append(tree.getDepth()).append("\" sorder=\"");
            strHtml.append(tree.getSorder()).append("\" isopen=\"").append(tree.getIsopen()).append("\" opened=\"false\"");
            strHtml.append(" haschild=\"").append(tree.getHaschild()).append("\" sequence=\"").append(tree.getSequence());
            strHtml.append("\" usecheckbox=\"").append(usecheckbox).append("\">\n");
            strHtml.append("  <tr height=22>\n");
            for (int j = 0; j < tree.getDepth(); j++) {
                strHtml.append("    <td width=16 height=22>&nbsp;</td>\n");
            }
            strHtml.append("    <td width=16 height=22>");
            strHtml.append("<img src=\"");
            
            /*
            if (tree.getHaschild()) {
                if (tree.getIsopen()) {
                    strHtml.append(Application.getXmlString("TREE.HAS_CHILD_OPEN","")).append("\" ");
                } else {
                    strHtml.append(Application.getXmlString("TREE.HAS_CHILD_CLOSE","")).append("\" ");
                }
            } else {
                strHtml.append(Application.getXmlString("TREE.NO_CHILD_MIDDLE","")).append("\" ");
            }
            */
            
            strHtml.append(" id=IMG_OPEN_").append(tree.getId()).append(" onclick=\"javascript:showSubTree('");
            strHtml.append(tree.getId()).append("','").append(tree.getDepth()).append("','");
            strHtml.append(tree.getParams()).append("');\" style=cursor:pointer;>");
            strHtml.append("</td>\n");
            strHtml.append("    <td height=22>");

//            strHtml.append("<img src=\"").append(Application.getXmlString("TREE.CHECK_NO","")).append("\" ");
//            strHtml.append(" id=IMG_CHECK_").append(tree.getId());
//            strHtml.append(" onclick=\"javascript:checkData('").append(tree.getId()).append("');\" ");
//            strHtml.append(" style=cursor:pointer;>");
            if(!tree.getId().equals("ROOT") && usecheckbox) {
                strHtml.append("<input type=checkbox name=CHECKBOX_").append(tree.getId());
                strHtml.append(" onclick=\"javascript:checkData('").append(tree.getId()).append("');\">");
            }
            if (tree.getBeforetext() != null && tree.getBeforetext() != "") {
                strHtml.append(tree.getBeforetext()).append("&nbsp;");
            }
            String style = "";
            if(tree.getId().equals(openid)) {
                style = "background-color:#2984be;color:#ffffff;";
            }
            strHtml.append("<span id=TEXT_").append(tree.getId()).append(" height=22 style=\"cursor:pointer;" + style + "\" class=").append(tree.getCssClass());
            if (tree.getTitle() != null && tree.getTitle() != "") {
                strHtml.append(" title=\"").append(tree.getTitle()).append("\"");
            }
            if (tree.getMouseover() != null && tree.getMouseover() != "") {
                strHtml.append(" onmouseover=\"javascript:").append(tree.getMouseover()).append("\"");
            }
            strHtml.append(" onclick=\"javascript:selectData('").append(tree.getId()).append("');");
            strHtml.append(tree.getHref()).append("\">").append(tree.getText()).append("&nbsp;</span>\n");
            if (tree.getAftertext() != null && tree.getAftertext() != "") {
                strHtml.append(tree.getAftertext());
            }

            strHtml.append("</td>\n");
            strHtml.append("  </tr>\n");
            strHtml.append("</table>\n");
            if (tree.getHaschild() && tree.getIsopen()) {
                if (tree.getIsopen()) {
                    strHtml.append("<div id=DIV_").append(tree.getId()).append(">\n");
                } else {
                    strHtml.append("<div style=\"display:none;\" id=DIV_").append(tree.getId()).append(">\n");
                }
            } else {
                strHtml.append("<div style=\"display:none;\" id=DIV_").append(tree.getId()).append("></div></div>\n");
            }
            if(tree.getId().equals("ROOT")) {
                strHtml.append("</div></div>\n");
            }
        }
        return strHtml.toString();
    }

}

