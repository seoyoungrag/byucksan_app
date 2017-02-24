package com.sds.acube.app.env.vo;

/**
 * Class Name  : PubViewUserVO.java <br> Description : 공람자그룹 사용자 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PubViewUserVO
 */ 
public class PubViewUserVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 공람자그룹ID
	 */
	private String pubviewGroupId;
	/**
	 * 공람자ID
	 */
	private String pubReaderId;
	/**
	 * 공람자명
	 */
	private String pubReaderName;
	/**
	 * 공람자직위
	 */
	private String pubReaderPos;
	/**
	 * 공람자부서ID
	 */
	private String pubReaderDeptId;
	/**
	 * 공람자부서명
	 */
	private String pubReaderDeptName;
	/**
	 * 공람자역할
	 */
	private String pubReaderRole;
	/**
	 * 공람자정보 변경여부
	 */
	private String changeYn;
	/**
	 * 공람자순서
	 */
	private int pubReaderOrder = 0;	
	
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
	 * @return  the pubviewGroupId
	 */
	public String getPubviewGroupId() {
		return pubviewGroupId;
	}
	/**
	 * @param pubviewGroupId  the pubviewGroupId to set
	 */
	public void setPubviewGroupId(String pubviewGroupId) {
		this.pubviewGroupId = pubviewGroupId;
	}
	/**
	 * @return  the pubReaderId
	 */
	public String getPubReaderId() {
		return pubReaderId;
	}
	/**
	 * @param pubReaderId  the pubReaderId to set
	 */
	public void setPubReaderId(String pubReaderId) {
		this.pubReaderId = pubReaderId;
	}
	/**
	 * @return  the pubReaderName
	 */
	public String getPubReaderName() {
		return pubReaderName;
	}
	/**
	 * @param pubReaderName  the pubReaderName to set
	 */
	public void setPubReaderName(String pubReaderName) {
		this.pubReaderName = pubReaderName;
	}
	/**
	 * @return  the pubReaderPos
	 */
	public String getPubReaderPos() {
		return pubReaderPos;
	}
	/**
	 * @param pubReaderPos  the pubReaderPos to set
	 */
	public void setPubReaderPos(String pubReaderPos) {
		this.pubReaderPos = pubReaderPos;
	}
	/**
	 * @return  the pubReaderDeptId
	 */
	public String getPubReaderDeptId() {
		return pubReaderDeptId;
	}
	/**
	 * @param pubReaderDeptId  the pubReaderDeptId to set
	 */
	public void setPubReaderDeptId(String pubReaderDeptId) {
		this.pubReaderDeptId = pubReaderDeptId;
	}
	/**
	 * @return  the pubReaderDeptName
	 */
	public String getPubReaderDeptName() {
		return pubReaderDeptName;
	}
	/**
	 * @param pubReaderDeptName  the pubReaderDeptName to set
	 */
	public void setPubReaderDeptName(String pubReaderDeptName) {
		this.pubReaderDeptName = pubReaderDeptName;
	}
	/**
	 * @return  the pubReaderRole
	 */
        public String getPubReaderRole() {
            return pubReaderRole;
        }
	/**
	 * @param pubReaderRole  the pubReaderRole to set
	 */
        public void setPubReaderRole(String pubReaderRole) {
            this.pubReaderRole = pubReaderRole;
        }
        
	/**
	 * @return  the changeYn
	 */
        public String getChangeYn() {
            return changeYn;
        }
	/**
	 * @param changeYn  the changeYn to set
	 */
        public void setChangeYn(String changeYn) {
            this.changeYn = changeYn;
        }
	/**
	 * @param pubReaderOrder  the pubReaderOrder to set
	 */
        public void setPubReaderOrder(int pubReaderOrder) {
	    this.pubReaderOrder = pubReaderOrder;
        }
	/**
	 * @return  the pubReaderOrder
	 */
        public int getPubReaderOrder() {
	    return pubReaderOrder;
        }
        

}
