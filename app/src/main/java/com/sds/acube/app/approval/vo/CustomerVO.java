package com.sds.acube.app.approval.vo;

/**
 * Class Name  : CustomerVO.java <br> Description : 거래처정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  CustomerVO
 */ 
public class CustomerVO {
	
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 처리자ID
	 */
	private String processorId;
	/**
	 * 임시여부
	 */
	private String tempYn;
	/**
	 * 거래처ID
	 */
	private String customerId;
	/**
	 * 거래처명
	 */
	private String customerName;
	
	
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
	 * @return  the processorId
	 */
	public String getProcessorId() {
		return processorId;
	}
	/**
	 * @param processorId  the processorId to set
	 */
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}
	/**
	 * @return  the tempYn
	 */
	public String getTempYn() {
		return tempYn;
	}
	/**
	 * @param tempYn  the tempYn to set
	 */
	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}
	/**
	 * @return  the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId  the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName  the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
