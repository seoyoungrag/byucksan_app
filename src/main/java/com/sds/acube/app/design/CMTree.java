package com.sds.acube.app.design;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tree 형태의 목록을 처리하기 위한 객체
 * @author   Machinelee
 * @version  $Revision: 1.2 $ $Date: 2009/03/25 05:41:18 $
 */
public class CMTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
	 */
    private String id;
    /**
	 */
    private String parentid;
    /**
	 */
    private String beforetext;
    /**
	 */
    private String text;
    /**
	 */
    private String aftertext;
    /**
	 */
    private String href = "";
    /**
	 */
    private String cssClass;
    /**
	 */
    private String title;
    /**
	 */
    private String mouseover;
    /**
	 */
    private String params = "";
    /**
	 */
    private String plusImage = "";
    /**
	 */
    private String minusImage = "";
    /**
	 */
    private String dotImage = "";
    /**
	 */
    private int depth;
    /**
	 */
    private boolean isVirtual;
    /**
	 */
    private boolean isopen;
    /**
	 */
    private boolean haschild;
    /**
	 */
    private String isFolder;
    /**
	 */
    private int sorder;
    /**
	 */
    private int sequence;

    private ArrayList subNodeList;


    public CMTree() {
        //css_class = Application.getXmlString("TREE.CLASS","");
    }

    /**
	 * <pre>  설명 </pre>
	 * @param plusImage
	 * @see   
	 */
    public void setPlusImage(String plusImage)
    {
        this.plusImage = plusImage;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPlusImage()
    {
        return plusImage;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param minusImage
	 * @see   
	 */
    public void setMinusImage(String minusImage)
    {
        this.minusImage = minusImage;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMinusImage()
    {
        return minusImage;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param dotImage
	 * @see   
	 */
    public void setDotImage(String dotImage)
    {
        this.dotImage = dotImage;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDotImage()
    {
        return dotImage;
    }

    /**
	 * 화면출력 내용을 입력한다.
	 * @param  text
	 */
    public void setText(String text) {
        this.text = text;
    }

    /**
	 * 화면에 출력할 Node내용을 얻는다.
	 * @return  Node Text
	 */
    public String getText() {
        return this.text;
    }

    /**
	 * 화면출력 내용을 입력한다.
	 * @param  text
	 */
    public void setBeforetext(String beforetext) {
        this.beforetext = beforetext;
    }

    /**
	 * 화면에 출력할 Node내용을 얻는다.
	 * @return  Node Text
	 */
    public String getBeforetext() {
        return this.beforetext;
    }

    /**
	 * 화면출력 내용을 입력한다.
	 * @param  text
	 */
    public void setAftertext(String aftertext) {
        this.aftertext = aftertext;
    }

    /**
	 * 화면에 출력할 Node내용을 얻는다.
	 * @return  Node Text
	 */
    public String getAftertext() {
        return this.aftertext;
    }

    /**
	 * ID를 입력한다.
	 * @param id  NOde의 ID
	 */
    public void setId(String id) {
        this.id = id;
    }

    /**
	 * Node ID를 얻는다.
	 * @return  Node ID
	 */
    public String getId() {
        return this.id;
    }

    /**
	 * 상위 Node의 ID를 입력한다.
	 * @param  parentid
	 */
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    /**
	 * 현재 Node의 상위 ID를 얻는다.
	 * @return
	 */
    public String getParentid() {
        return this.parentid;
    }

    /**
	 * 클릭이벤트를 입력한다.
	 * @param  href
	 */
    public void setHref(String href) {
        this.href = href;
    }

    /**
	 * 클릭이벤트를 얻는다.
	 * @return  href
	 */
    public String getHref() {
        return this.href;
    }

    /**
	 * CSS Class를 입력한다.
	 * @param  css_class
	 */
    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    /**
	 * CSS Class를 얻는다.
	 * @return  Css Class
	 */
    public String getCssClass() {
        return this.cssClass;
    }

    /**
	 * 풍선도움말을 입력한다.
	 * @param  title
	 */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
	 * 풍선도움말을 얻는다.
	 * @return  Title
	 */
    public String getTitle() {
        return this.title;
    }

    /**
	 * Mouseover 이벤트를 입력한다.
	 * @param  mouseover
	 */
    public void setMouseover(String mouseover) {
        this.mouseover = mouseover;
    }

    /**
	 * Mouseover 이벤트를 얻는다.
	 * @return  Mouseover
	 */
    public String getMouseover() {
        return this.mouseover;
    }

    /**
	 * 현재 Node의 Depth를 입력한다.
	 * @param  depth
	 */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
	 * 현재 Node의 Depth를 얻는다.
	 * @return
	 */
    public int getDepth() {
        return this.depth;
    }

    /**
	 * 하위 Node의 화면출력 여부를 입력한다.
	 * @param  isopen
	 */
    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    /**
	 * 하위 Node의 화면출력 여부를 얻는다.
	 * @return
	 */
    public boolean getIsopen() {
        return this.isopen;
    }

    /**
     * 하위 노드의 존재 여부를 입력한다.
     * @param isopen
     */
    public void setIsVirtual(boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    /**
	 * 하위 노드의 존재 여부를 얻는다.
	 * @return
	 */
    public boolean getIsVirtual() {
        return this.isVirtual;
    }

    /**
	 * 하위 노드의 존재 여부를 입력한다.
	 * @param  isopen
	 */
    public void setSorder(int sorder) {
        this.sorder = sorder;
    }

    /**
	 * 하위 노드의 존재 여부를 얻는다.
	 * @return
	 */
    public int getSorder() {
        return this.sorder;
    }

    /**
	 * 폴더(Node)인지 Leaf인지 구별
	 * @param flag  (F:folder, L:leaf)
	 */
    public void setIsFolder(String flag)
    {
        this.isFolder = flag;
    }

    /**
	 * 폴더(Node)인지 Leaf인지 구별
	 * @return  isFolder (F:folder, L:leaf)
	 */
    public String getIsFolder()
    {
        return this.isFolder;
    }

    /**
	 * 하위 노드의 존재 여부를 입력한다.
	 * @param  isopen
	 */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
	 * 하위 노드의 존재 여부를 얻는다.
	 * @return
	 */
    public int getSequence() {
        return this.sequence;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public boolean getHaschild() {
        return haschild;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param haschild
	 * @see   
	 */
    public void setHaschild(boolean haschild) {
        this.haschild = haschild;
    }

    /**
     * 하위 Node를 추가한다.
     * @param subNode
     */
    public void add(CMTree subNode) {
        if (subNodeList == null) {
            subNodeList = new ArrayList();
        }
        subNodeList.add(subNode);
    }

    /**
     * 하위 Node 목록을 얻는다.
     * @return
     */
    public ArrayList getsubNodeList() {
        return this.subNodeList;
    }

    /**
     * Tree객체를 ArrayList를 정렬한다.(화면출력 순서)
     * @param allNode
     * @param isLast
     * @param isOpen
     * @param nOpenDepth
     */
    public void compileNode(ArrayList allNode, boolean isOpen, int nOpenDepth) {
        if (this.id != null) {
            allNode.add(this);
        }
        if (subNodeList != null) {
            this.isopen = isOpen;
            if (this.depth < nOpenDepth) {
                this.isopen = true;
            }
            if (!this.haschild) {
                this.haschild = true;
            }
            for (int i = 0; i < subNodeList.size(); i++) {
                CMTree subNode = (CMTree) subNodeList.get(i);
                subNode.setSequence(i+1);
                subNode.compileNode(allNode, isOpen, nOpenDepth);
            }
        }
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getParams() {
        return params;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param params
	 * @see   
	 */
    public void setParams(String params) {
        this.params = params;
    }
}
