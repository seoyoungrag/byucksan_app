package com.sds.acube.app.env.vo;

/**
 * Class Name  : EmptyReasonVO.java <br> Description : 부재설정이유 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 6. 15 
 * @version  1.0 
 * @see  EmptyReasonVO
 */ 
public class EmptyReasonVO {

	/**
	 * 부재설정이유ID
	 */
	private String emptyReasonId;
	/**
	 * 부재설정이유
	 */
	private String emptyReason;

	/**
	 * @return  the emptyReasonId
	 */
        public String getEmptyReasonId() {
            return emptyReasonId;
        }
	/**
	 * @param emptyReasonId  the emptyReasonId to set
	 */
        public void setEmptyReasonId(String emptyReasonId) {
            this.emptyReasonId = emptyReasonId;
        }
	/**
	 * @return  the emptyReason
	 */
        public String getEmptyReason() {
            return emptyReason;
        }
	/**
	 * @param emptyReason  the emptyReason to set
	 */
        public void setEmptyReason(String emptyReason) {
            this.emptyReason = emptyReason;
        }

}
