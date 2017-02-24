package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : OwnDeptVO.java <br> Description : 문서소유부서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  OwnDeptVO
 */ 
public class OwnDeptVO {
	
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 소유부서ID
	 */
	private String ownDeptId;
	/**
	 * 소유부서명
	 */
	private String ownDeptName;
	/**
	 * 소유여부
	 */
	private String ownYn;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	
	/**
	 * @return  the docId
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @param docId  the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}
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
	 * @return  the ownDeptId
	 */
	public String getOwnDeptId() {
		return ownDeptId;
	}
	/**
	 * @param ownDeptId  the ownDeptId to set
	 */
	public void setOwnDeptId(String ownDeptId) {
		this.ownDeptId = ownDeptId;
	}
	/**
	 * @return  the ownDeptName
	 */
        public String getOwnDeptName() {
            return ownDeptName;
        }
	/**
	 * @param ownDeptName  the ownDeptName to set
	 */
        public void setOwnDeptName(String ownDeptName) {
            this.ownDeptName = ownDeptName;
        }
	/**
	 * @return  the ownYn
	 */
        public String getOwnYn() {
            return ownYn;
        }
	/**
	 * @param ownYn  the ownYn to set
	 */
        public void setOwnYn(String ownYn) {
            this.ownYn = ownYn;
        }
	/**
	 * @param registDate  the registDate to set
	 */
        public void setRegistDate(String registDate) {
	    this.registDate = registDate;
        }
	/**
	 * @return  the registDate
	 */
        public String getRegistDate() {
	    return registDate;
        }

}
