package com.sds.acube.app.env.vo;

/**
 * Class Name  : RecvUserVO.java <br> Description : 수신자사용자 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  RecvUserVO
 */ 
public class RecvUserVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 수신자그룹ID
	 */
	private String recvGroupId;
	/**
	 * 수신자ID
	 */
	private String recvId;
	/**
	 * 수신자순서
	 */
	private int receiverOrder = 0;
	/**
	 * 수신자유형
	 */
	private String receiverType;
	/**
	 * 시행유형
	 */
	private String enfType;
	/**
	 * 수신회사ID
	 */
	private String recvCompId;
	/**
	 * 수신부서ID
	 */
	private String recvDeptId;
	/**
	 * 수신부서명
	 */
	private String recvDeptName;
	/**
	 * 참조부서ID
	 */
	private String refDeptId;
	/**
	 * 참조부서명
	 */
	private String refDeptName;
	/**
	 * 수신인ID
	 */
	private String recvUserId;
	/**
	 * 수신인명
	 */
	private String recvUserName;
	/**
	 * 우편번호
	 */
	private String postNumber;
	/**
	 * 주소
	 */
	private String address;
	/**
	 * 전화번호
	 */
	private String telephone;
	/**
	 * FAX
	 */
	private String fax;
	/**
	 * 수신자기호
	 */
	private String recvSymbol;
	/**
	 * 수신자정보 변경여부
	 */
	private String changeYn;
	

	/**
	 * 참조자기호
	 */
	private String refSymbol;
	/**
	 * 수신자부서장직위
	 */
	private String recvChiefName;
	/**
	 * 참조자부서장직위
	 */
	private String refChiefName;
	
	
		
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
	 * @return  the recvGroupId
	 */
	public String getRecvGroupId() {
		return recvGroupId;
	}
	/**
	 * @param recvGroupId  the recvGroupId to set
	 */
	public void setRecvGroupId(String recvGroupId) {
		this.recvGroupId = recvGroupId;
	}
	/**
	 * @return  the recvId
	 */
	public String getRecvId() {
		return recvId;
	}
	/**
	 * @param recvId  the recvId to set
	 */
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	/**
	 * @return  the receiverOrder
	 */
	public int getReceiverOrder() {
		return receiverOrder;
	}
	/**
	 * @param receiverOrder  the receiverOrder to set
	 */
	public void setReceiverOrder(int receiverOrder) {
		this.receiverOrder = receiverOrder;
	}
	/**
	 * @return  the receiverType
	 */
	public String getReceiverType() {
		return receiverType;
	}
	/**
	 * @param receiverType  the receiverType to set
	 */
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	/**
	 * @return  the enfType
	 */
	public String getEnfType() {
		return enfType;
	}
	/**
	 * @param enfType  the enfType to set
	 */
	public void setEnfType(String enfType) {
		this.enfType = enfType;
	}
	/**
	 * @return  the recvCompId
	 */
	public String getRecvCompId() {
		return recvCompId;
	}
	/**
	 * @param recvCompId  the recvCompId to set
	 */
	public void setRecvCompId(String recvCompId) {
		this.recvCompId = recvCompId;
	}
	/**
	 * @return  the recvDeptId
	 */
	public String getRecvDeptId() {
		return recvDeptId;
	}
	/**
	 * @param recvDeptId  the recvDeptId to set
	 */
	public void setRecvDeptId(String recvDeptId) {
		this.recvDeptId = recvDeptId;
	}
	/**
	 * @return  the recvDeptName
	 */
	public String getRecvDeptName() {
		return recvDeptName;
	}
	/**
	 * @param recvDeptName  the recvDeptName to set
	 */
	public void setRecvDeptName(String recvDeptName) {
		this.recvDeptName = recvDeptName;
	}
	/**
	 * @return  the refDeptId
	 */
	public String getRefDeptId() {
		return refDeptId;
	}
	/**
	 * @param refDeptId  the refDeptId to set
	 */
	public void setRefDeptId(String refDeptId) {
		this.refDeptId = refDeptId;
	}
	/**
	 * @return  the refDeptName
	 */
	public String getRefDeptName() {
		return refDeptName;
	}
	/**
	 * @param refDeptName  the refDeptName to set
	 */
	public void setRefDeptName(String refDeptName) {
		this.refDeptName = refDeptName;
	}
	/**
	 * @return  the recvUserId
	 */
	public String getRecvUserId() {
		return recvUserId;
	}
	/**
	 * @param recvUserId  the recvUserId to set
	 */
	public void setRecvUserId(String recvUserId) {
		this.recvUserId = recvUserId;
	}
	/**
	 * @return  the recvUserName
	 */
	public String getRecvUserName() {
		return recvUserName;
	}
	/**
	 * @param recvUserName  the recvUserName to set
	 */
	public void setRecvUserName(String recvUserName) {
		this.recvUserName = recvUserName;
	}
	/**
	 * @return  the postNumber
	 */
	public String getPostNumber() {
		return postNumber;
	}
	/**
	 * @param postNumber  the postNumber to set
	 */
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	/**
	 * @return  the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address  the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return  the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone  the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return  the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax  the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return  the recvSymbol
	 */
	public String getRecvSymbol() {
		return recvSymbol;
	}
	/**
	 * @param recvSymbol  the recvSymbol to set
	 */
	public void setRecvSymbol(String recvSymbol) {
		this.recvSymbol = recvSymbol;
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
	 * @return  the refSymbol
	 */
	public String getRefSymbol() {
		return refSymbol;
	}
	/**
	 * @param refSymbol  the refSymbol to set
	 */
	public void setRefSymbol(String refSymbol) {
		this.refSymbol = refSymbol;
	}
	/**
	 * @return  the recvChiefName
	 */
	public String getRecvChiefName() {
		return recvChiefName;
	}
	/**
	 * @param recvChiefName  the recvChiefName to set
	 */
	public void setRecvChiefName(String recvChiefName) {
		this.recvChiefName = recvChiefName;
	}
	/**
	 * @return  the refChiefName
	 */
	public String getRefChiefName() {
		return refChiefName;
	}
	/**
	 * @param refChiefName  the refChiefName to set
	 */
	public void setRefChiefName(String refChiefName) {
		this.refChiefName = refChiefName;
	}
	

}
