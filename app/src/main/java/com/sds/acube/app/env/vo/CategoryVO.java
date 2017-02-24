package com.sds.acube.app.env.vo;

/**
 * Class Name  : CategoryVO.java <br> Description : 카테고리 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  CategoryVO
 */ 
public class CategoryVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 카테고리ID
	 */
	private String categoryId;
	/**
	 * 카테고리명
	 */
	private String categoryName;
	/**
	 * 카테고리순서
	 */
	private int categoryOrder = 0;
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록자명
	 */
	private String registerName;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	
	/**
	 */
	private String deleteYn="N";
	/**
	 * 비고
	 */
	private String remark;
	
	/**
	 * 리소스 ID
	 */
	private String resourceId;
	
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
	 * @return  the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId  the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return  the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName  the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return  the categoryOrder
	 */
        public int getCategoryOrder() {
            return categoryOrder;
        }
	/**
	 * @param categoryOrder  the categoryOrder to set
	 */
        public void setCategoryOrder(int categoryOrder) {
            this.categoryOrder = categoryOrder;
        }
	/**
	 * @return  the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * @param registerId  the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * @return  the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}
	/**
	 * @param registerName  the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	/**
	 * @return  the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}
	/**
	 * @param registDate  the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark  the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the deleteYn
	 */
        public String getDeleteYn() {
            return deleteYn;
        }
	/**
	 * @param deleteYn  the deleteYn to set
	 */
        public void setDeleteYn(String deleteYn) {
            this.deleteYn = deleteYn;
        }
	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CategoryVO [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", categoryOrder=" + categoryOrder
				+ ", compId=" + compId + ", deleteYn=" + deleteYn
				+ ", registDate=" + registDate + ", registerId=" + registerId
				+ ", registerName=" + registerName + ", remark=" + remark
				+ ", resourceId=" + resourceId + "]";
	}
        
        
}
