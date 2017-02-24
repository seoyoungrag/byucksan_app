package com.sds.acube.app.design.customtag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.tagext.TagSupport;

import com.sds.acube.app.design.AcubeTree;
import com.sds.acube.app.common.util.LogWrapper;

/**
 * Class Name  : TreeTag.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.customtag.TreeTag.java
 */
public class TreeTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    /**
	 */
    private com.sds.acube.app.design.UtilTree tree;
    private boolean all = true;
    private boolean usecheckbox = true;
    private boolean open = true;
    private String openid;
    private int opendepth;
    private boolean displayRoot = true;;

    /**
	 * <pre>  설명 </pre>
	 * @param tree
	 * @see   
	 */
    public void setTree(com.sds.acube.app.design.UtilTree tree) {
        this.tree = tree;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param all
	 * @see   
	 */
    public void setAll(boolean all) {
        this.all = all;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param usecheckbox
	 * @see   
	 */
    public void setUsecheckbox(boolean usecheckbox) {
        this.usecheckbox = usecheckbox;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param open
	 * @see   
	 */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param openid
	 * @see   
	 */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param opendepth
	 * @see   
	 */
    public void setOpendepth(int opendepth) {
        this.opendepth = opendepth;
    }

    /**
	 * @param displayRoot  The displayRoot to set.
	 */
    public void setDisplayRoot(boolean displayRoot) {
        this.displayRoot = displayRoot;
    }

    public int doStartTag() {
        try {
            pageContext.getOut().print(this.createHtml());
        } catch(IOException e) {
            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
        }

        return TreeTag.SKIP_BODY;
    }

    private String createHtml() {
        ArrayList allNodes;
//        if (this.all) {
            allNodes = new ArrayList();
            this.tree.getTree().compileNode(allNodes, open, this.opendepth);
            if (!this.open) {
                tree.setOpenNode(this.openid);
            }

            if (all) {
                return AcubeTree.getTreeAll(allNodes, this.usecheckbox, this.openid, this.displayRoot);
            } else {
                return AcubeTree.getTree(allNodes, this.usecheckbox, this.openid, this.displayRoot);
            }
//        } else {
//            if (this.root) {
//                return TagUtil.getTree(this.tree.getTree());
//            } else {
//                return TagUtil.getSubTree(this.tree.getTree(), this.image, this.depth);
//            }
//        }
    }

}
