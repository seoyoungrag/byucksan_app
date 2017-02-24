/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AppMenuVO.java <br> Description : 결재함 리스트 조회 결과 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 5. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppMenuVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "AppMenuVO", propOrder = {

	"menuname"       ,
	"menuid"         ,
	"parent_menuid"  ,
	"doc_count"      ,
	"is_leaf"        ,
	"has_list"       
})


public class AppMenuVO {
    

    /**
	 */
    protected String menuname       ;//결재함 이름           
    /**
	 */
    protected String menuid         ;//결재함ID              
    /**
	 */
    protected String parent_menuid  ;//상위 결재함ID         
    /**
	 */
    protected int doc_count      ;//결재문서 개수         
    /**
	 */
    protected String is_leaf        ;//최하위 결재함 여부    
    /**
	 */
    protected String has_list       ;//결재문서 보유가능 여부

    
    

    /**
	 * @return  the menuname
	 */
    public String getMenuname() {
        return menuname;
    }
    /**
	 * @return  the menuid
	 */
    public String getMenuid() {
        return menuid;
    }
    /**
	 * @return  the parent_menuid
	 */
    public String getParent_menuid() {
        return parent_menuid;
    }
    /**
	 * @return  the doc_count
	 */
    public int getDoc_count() {
        return doc_count;
    }
    /**
	 * @return  the is_leaf
	 */
    public String getIs_leaf() {
        return is_leaf;
    }
    /**
	 * @return  the has_list
	 */
    public String getHas_list() {
        return has_list;
    }

    /**
	 * @param menuname  the menuname to set
	 */
    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }
    /**
	 * @param menuid  the menuid to set
	 */
    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }
    /**
	 * @param parentMenuid  the parent_menuid to set
	 */
    public void setParent_menuid(String parentMenuid) {
        parent_menuid = parentMenuid;
    }
    /**
	 * @param docCount  the doc_count to set
	 */
    public void setDoc_count(int docCount) {
        doc_count = docCount;
    }
    /**
	 * @param isLeaf  the is_leaf to set
	 */
    public void setIs_leaf(String isLeaf) {
        is_leaf = isLeaf;
    }
    /**
	 * @param hasList  the has_list to set
	 */
    public void setHas_list(String hasList) {
        has_list = hasList;
    }

    
}
