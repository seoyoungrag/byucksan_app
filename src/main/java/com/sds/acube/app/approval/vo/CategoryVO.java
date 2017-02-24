/**
 * 
 */
package com.sds.acube.app.approval.vo;

/**
 * Class Name : CategoryVO.java <br> Description : TGW_CATEGORY 테이블의 Category 목록 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 26. <br> 수 정 자 : 장진홍 <br> 수정내용 : 최초작성<br>
 * @author  jumbohero
 * @since  2011. 4. 26.
 * @version  1.0
 * @see  com.sds.acube.app.approval.vo.CategoryVO.java
 */

public class CategoryVO {
    /**
	 * 회사ID
	 */
    private String compId;

    /**
	 * 카테고리 ID
	 */
    private String categoryId;
    
    /**
	 * 카테고리 명
	 */
    private String categoryName;


    /**
	 * @return  the compId
	 */
    public String getCompId() {
	return compId;
    }


    /**
	 * @param compId  the compId to set
	 */
    public void setCompId(String compId) {
	this.compId = compId;
    }

    /**
	 * @return  the compId
	 */
    public String getCategoryId() {
	return categoryId;
    }


    /**
	 * @param compId  the compId to set
	 */
    public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
    }
    
    /**
	 * @return  the compId
	 */
    public String getCategoryName() {
	return categoryName;
    }


    /**
	 * @param compId  the compId to set
	 */
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }
}
