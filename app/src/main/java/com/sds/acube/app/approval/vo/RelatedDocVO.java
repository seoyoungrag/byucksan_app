package com.sds.acube.app.approval.vo;

/**
 * Class Name  : RelatedDocVO.java <br> Description : 관련문서정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  RelatedDocVO
 */ 
public class RelatedDocVO {

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
	 * 원문서ID
	 */
	private String originDocId;
	/**
	 * 문서제목
	 */
	private String title;
	/**
	 * 사용대상유형
	 */
	private String usingType;
	/**
	 * 전자문서여부
	 */
	private String electronDocYn;	
	/** 
	 * 관련문서 순서
	 */
	private int docOrder =0;
	
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
	 * @return  the originDocId
	 */
	public String getOriginDocId() {
		return originDocId;
	}
	/**
	 * @param originDocId  the originDocId to set
	 */
	public void setOriginDocId(String originDocId) {
		this.originDocId = originDocId;
	}
	/**
	 * @return  the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title  the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @param electronDocYn  the electronDocYn to set
	 */
        public void setElectronDocYn(String electronDocYn) {
	    this.electronDocYn = electronDocYn;
        }
	/**
	 * @return  the electronDocYn
	 */
        public String getElectronDocYn() {
	    return electronDocYn;
        }
	/**
	 * @return the docOrder
	 */
	public int getDocOrder() {
		return docOrder;
	}
	/**
	 * @param docOrder the docOrder to set
	 */
	public void setDocOrder(int docOrder) {
		this.docOrder = docOrder;
	}        
}
