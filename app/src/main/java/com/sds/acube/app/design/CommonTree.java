/**
 * Author : Junghyun Kim
 * File : CommonTree.java
 * Date : 11/22/06
 * Last Updated : 11/28/06
 * Description : Generating Tree module
 */

package com.sds.acube.app.design;

import java.io.PrintWriter;
import javax.servlet.jsp.JspWriter;

import com.sds.acube.app.common.util.LogWrapper;

import common.cm_ref.CM_TreeVO;
import sutil.NameFactory;
import sutil.UtilCommon;

/**
 * Class Name  : CommonTree.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.CommonTree.java
 */
public class CommonTree
{
    /**
     * Hold Tree Information
     */
    private CM_TreeVO[] folderInfo;
    private int fLength;
    /**
	 */
    private String rootName;
    private boolean isSetFolderName;
    private boolean isCloseAll;
    private String compid;
    private String lang;

    /**
     * Image Information
     */
    private String closedFolderImage;
    private String openedFolderImage;
    private String lineLastNode;
    private String lineConnectedNode;
    private String connectionPlus;
    private String connectionMinus;
    private String lastPlus;
    private String lastMinus;
    private String verticalLine;
    private String blankImage;
    private boolean isImageSet;

    /**
     * Style Information
     */
    private String textStyle;
    private boolean isStyleSet;

    /**
     * Constructor
     */
    public CommonTree(CM_TreeVO[] folderInfo)
    {
        this.folderInfo = folderInfo;
        fLength = folderInfo.length;
        rootName = "";
        isImageSet = false;
        isStyleSet = false;
        isSetFolderName = false;
        isCloseAll = false;
    }

    /**
     * Set Tree Images
     * All parameters are the abbreviation of global variables
     */
    public void setImage(String cfi, String ofi, String lln, String lcn, String cp, String cm, String lp, String lm, String vl, String bi)
    {
        isImageSet = true;
        closedFolderImage = cfi;
        openedFolderImage = ofi;
        lineLastNode = lln;
        lineConnectedNode = lcn;
        connectionPlus = cp;
        connectionMinus = cm;
        lastPlus = lp;
        lastMinus = lm;
        verticalLine = vl;
        blankImage = bi;
    }

    /**
     * Getting image
     * return javascript function string 'setImage'
     */
    private String getImage()
    {
        String result = "";
        result = "setImage('"+closedFolderImage+"','"+openedFolderImage+"','"+lineLastNode+"','"+lineConnectedNode;
        result += "','"+connectionPlus+"','"+connectionMinus+"','"+lastPlus+"','"+lastMinus+"','"+verticalLine+"','"+blankImage;
        result += "');";
        return result;
    }

    /**
     * Set Tree style
     * All parameters are the abbreviation of global variables
     */
    public void setStyle(String textStyle)
    {
        isStyleSet = true;
        this.textStyle = textStyle;
    }

    /**
     * Getting style
     * return javascript function string 'setStyle'
     */
    private String getStyle()
    {
        String result = "";
        result = "setStyle(\""+textStyle+"\");";
        return result;
    }

    /**
	 * Setting the root node name
	 */
    public void setRootName(String rootName)
    {
        this.rootName = rootName;
    }

    /**
	 * Getting the root node name
	 */
    private String getRootName()
    {
        return (rootName.length() > 0) ? rootName : "Root";
    }

    /**
     * @param compid(Comapny ID), lang(Language)
     * @return void
     *
     * Set necessary variables to bring folder name from NameFactory
     * if the folder name that brought from DB is some kind of ID
     */
    public void setFolderName(String compid, String lang)
    {
        this.isSetFolderName = true;
        this.compid = compid;
        this.lang = lang;
    }

    /**
     * @param boolean
     * @return void
     *
     * Set initial state of tree whether expand all nodes or not
     * Close whole tree if bool is true, otherwise, expand whole tree
     *
     */
    public void setCloseAll()
    {
        this.isCloseAll = true;
    }

    /**
     * @return Javascript function that expand whole tree
     */
    private String getCloseAll()
    {
        return "closeAll();";
    }

    public void generate(JspWriter out)
    {
        try
        {
            generate(new PrintWriter(out));
        }
        catch(Exception e)
        {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }
    }

    /**
     * Generate Tree out
     */
    public void generate(PrintWriter out)
    {
        String folderName = "";
        try
        {
            out.println("<HTML>");
            out.println("<HEAD>");
            out.println("<SCRIPT Language='JavaScript' src='/EP/htdocs/CM_Tree.js'></SCRIPT>");
            //out.println("<SCRIPT Language='JavaScript' src='/EP/htdocs/CM_TreeMenu.js'></SCRIPT>");
            out.println("<SCRIPT Language='JavaScript'>");
            /* Set Tree Image */
            if(isImageSet)
            {
                out.println(getImage());
            }
            /* Set Tree Style */
            if(isStyleSet)
            {
                out.println(getStyle());
            }
            /* Set Initial Tree State */
            if(isCloseAll)
            {
                out.println(getCloseAll());
            }

            out.println("setRootName('"+getRootName()+"');");
            out.println("nodes = new Array;");

            // JAVA에서 가지고 있는 모든 트리 정보를 Javascript로 넘겨줌
            for(int i=0; i < fLength; i++)
            {
                // Bring folder Name from NameFactory
                if(isSetFolderName)
                {
                    folderName = UtilCommon.convert(NameFactory.getInstance().getName(this.compid, folderInfo[i].getFolderName(), this.lang));
                }
                else
                {
                    folderName = folderInfo[i].getFolderName();
                }
                out.println("nodes["+i+"] = new Array;");
                out.println("nodes["+i+"]['folderId'] = '"+folderInfo[i].getFolderID()+"';");
                out.println("nodes["+i+"]['folderName'] = '"+folderName+"';");
                out.println("nodes["+i+"]['pFolderId'] = '"+folderInfo[i].getPFolderID()+"';");
                out.println("nodes["+i+"]['depth'] = "+folderInfo[i].getDepth()+";");
                out.println("nodes["+i+"]['order'] = "+folderInfo[i].getOrder()+";");
            }

            // Generate 함수 호출
            out.println("generate(nodes);");
            out.println("initializeDocument();");
            //out.println("setFolderInfo(nodes);");
            //out.println("showMenu();");
            out.println("</SCRIPT>");
            out.println("</HEAD>");

            out.println("<BODY>");
            out.println("<BR /><BR />");
            //out.println("<div id='debugConsole'></div>");
            out.println("</BODY>");
            out.println("</HTML>");
        }
        catch(Exception e)
        {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }
    }
}