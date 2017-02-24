package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : PubReaderVO.java <br> Description : 공람자정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PubReaderVO
 */ 
public class PubReaderVO {

	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
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
	 * 열람일자
	 */
	private String readDate = "9999-12-31 23:59:59";
	/**
	 * 공람처리일자
	 */
	private String pubReadDate = "9999-12-31 23:59:59";
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
	 * 사용대상유형
	 */
	private String usingType;
	/**
	 * 삭제여부
	 */
	private String deleteYn;
	/**
	 * 공람자순서
	 */
	private int pubReaderOrder = 0;
	
	
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
	 * @return  the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate  the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	/**
	 * @return  the pubReadDate
	 */
	public String getPubReadDate() {
		return pubReadDate;
	}
	/**
	 * @param pubReadDate  the pubReadDate to set
	 */
	public void setPubReadDate(String pubReadDate) {
		this.pubReadDate = pubReadDate;
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
	 * @return  the usingType
	 */
	public String getUsingType() {
		return usingType;
	}
	/**
	 * @param usingType  the usingType to set
	 */
	public void setUsingType(String usingType) {
		this.usingType = usingType;
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
