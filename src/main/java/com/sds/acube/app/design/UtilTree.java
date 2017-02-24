package com.sds.acube.app.design;

import java.util.*;


/**
 * <pre> Tree에 Node를 추가한다. </pre>
 */
public class UtilTree
{
    private HashMap nodeBag;
    /**
	 */
    private CMTree root;

    public UtilTree(CMTree tree) {
        nodeBag = new HashMap();
        root = tree;
        if (root.getId() == null || root.getId().equals("")) {
            root.setId("ROOT");
        }
        root.setParentid("");
        root.setDepth(0);
        root.setIsopen(true);
        nodeBag.put(root.getId(), root);
    }

    public UtilTree(int depth, String parentId) {
        nodeBag = new HashMap();
        root = new CMTree();
        root.setParentid(parentId);
        root.setId("");
        root.setText("");
        root.setDepth(depth);
        root.setIsopen(true);
        root.setIsVirtual(true);
        nodeBag.put(parentId, root);
    }

    /**
     * 전체 트리객체를 생성한다.
     * @param strName 생성할 목록의 Root Node 이름
     */
    public UtilTree(String strName) {
        nodeBag = new HashMap();
        root = new CMTree();
        root.setId("ROOT");
        root.setParentid("");
        root.setText(strName);
        root.setDepth(0);
        root.setIsopen(true);
        nodeBag.put(root.getId(), root);
    }

    /**
     * 전체 트리객체를 생성한다.
     * @param strName 생성할 목록의 Root Node 이름
     */
    public UtilTree(String strName, String strId) {
        nodeBag = new HashMap();
        root = new CMTree();
        root.setId(strId);
        root.setParentid("");
        root.setText(strName);
        root.setDepth(0);
        root.setIsopen(true);
        nodeBag.put(strId, root);
    }

    /**
     *   <pre>
     *   Tree에 Node를 추가한다.
     *   </pre>
     * @param newNode     추가할 Node
     */

     public void add(CMTree newNode) {
         CMTree parentNode = (CMTree) nodeBag.get(newNode.getParentid());
         if (parentNode == null) {
             newNode.setDepth(root.getDepth() + 1);
             newNode.setParentid(root.getId());
             root.add(newNode);
         } else {
             newNode.setDepth(parentNode.getDepth() + 1);
             parentNode.add(newNode);
         }
         nodeBag.put(newNode.getId(), newNode);
     }

    /**
     *   <pre>
     *   Tree 객체를 얻는다.
     *   </pre>
     * @return Tree
     */
    public CMTree getTree() {
        return this.root;
    }

    /**
     * 화면출력시 Open할 Node를 설정한다.
     * @param strId
     */
    public void setOpenNode(String strId) {
        CMTree node = (CMTree) nodeBag.get(strId);
        if (node != null) {
            CMTree parentNode = (CMTree)nodeBag.get(node.getParentid());
            if (parentNode != null) {
                parentNode.setIsopen(true);
                setOpenNode(parentNode.getId());
            }
        }
    }

}
