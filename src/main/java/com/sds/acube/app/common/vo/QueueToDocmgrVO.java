package com.sds.acube.app.common.vo;

/**
 * Class Name  : QueueToDocMgrVO.java <br> Description : 문서관리연계큐 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  QueueToDocMgrVO
 */ 
public class QueueToDocmgrVO {
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 상태순서
	 */
	private int stateOrder = 0;
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 변경사유
	 */
	private String changeReason;
	/**
	 * 처리상태
	 */
	private String procState;
	/**
	 * 처리일자
	 */
	private String procDate = "9999-12-31 23:59:59";
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	/**
	 * 사용대상유형
	 */
	private String usingType;
	/**
	 * 처리메세지
	 */
	private String procMessage;
	
	/**
	 * 조회 갯수 설정
	 */
	private int startNumber = 0;
	/**
	 */
	private int endNumber = 0;
	
	/**
	 * 전송이 완료된 큐데이터 삭제일자 설정 일수
	 */
	private String subDays = "0";
	
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
	 * @return  the stateOrder
	 */
        public int getStateOrder() {
            return stateOrder;
        }
	/**
	 * @param stateOrder  the stateOrder to set
	 */
        public void setStateOrder(int stateOrder) {
            this.stateOrder = stateOrder;
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
	 * @return  the changeReason
	 */
        public String getChangeReason() {
            return changeReason;
        }
	/**
	 * @param changeReason  the changeReason to set
	 */
        public void setChangeReason(String changeReason) {
            this.changeReason = changeReason;
        }
	/**
	 * @return  the procState
	 */
        public String getProcState() {
            return procState;
        }
	/**
	 * @param procState  the procState to set
	 */
        public void setProcState(String procState) {
            this.procState = procState;
        }
	/**
	 * @return  the procDate
	 */
        public String getProcDate() {
            return procDate;
        }
	/**
	 * @param procDate  the procDate to set
	 */
        public void setProcDate(String procDate) {
            this.procDate = procDate;
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
		 * <pre>  설명 </pre>
		 * @return
		 * @see   
		 */
        public int getStartNumber(){
            return startNumber;
        }
        
        /**
		 * <pre>  설명 </pre>
		 * @param startNumber
		 * @see   
		 */
        public void setStartNumber(int startNumber){
            this.startNumber = startNumber;
        }
        
        /**
		 * <pre>  설명 </pre>
		 * @return
		 * @see   
		 */
        public int getEndNumber(){
            return endNumber;
        }
        
        /**
		 * <pre>  설명 </pre>
		 * @param endNumber
		 * @see   
		 */
        public void setEndNumber(int endNumber){
            this.endNumber = endNumber;
        }
	/**
	 * @return  the procMessage
	 */
        public String getProcMessage() {
            return procMessage;
        }
	/**
	 * @param procMessage  the procMessage to set
	 */
        public void setProcMessage(String procMessage) {
            this.procMessage = procMessage;
        }
        
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getSubDays(){
	    return subDays;
	}
	
	/**
	 * <pre>  설명 </pre>
	 * @param subDays
	 * @see   
	 */
	public void setSubDays(String subDays){
	    this.subDays = subDays;
	}
}
