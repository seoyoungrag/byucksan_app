package com.sds.acube.app.approval.vo;

/**
 * Class Name  : AppOptionVO.java <br> Description : 생산문서 부가정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AppOptionVO
 */ 

public class AppOptionVO {
	
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
	 * 신청서코드
	 */
	private String appFormId;
	/**
	 * 업무구분코드
	 */
	private String bizCode;
	/**
	 * 요청구분코드
	 */
	private String reqCode;
	/**
	 * 업무유형
	 */
	private String workType;
	/**
	 * 신청분류
	 */
	private String requestType;
	/**
	 * 신청시간
	 */
	private String requestTime;
	/**
	 * 반출처
	 */
	private String takeoutTarget;
	/**
	 * 반출담당자
	 */
	private String takeoutCharger;
	/**
	 * 반출연락처
	 */
	private String takeoutContact;
	/**
	 * 반출내역
	 */
	private String takeoutContent;
	/**
	 * 신청사유
	 */
	private String requestReason;
	/**
	 * 기안자에게 알림여부
	 */
	private String alarmYn;
	/**
	 * 다음결재자에게 알림여부
	 */
	private String alarmNextYn;
	
	
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
	 * @return  the appFormId
	 */
	public String getAppFormId() {
		return appFormId;
	}
	/**
	 * @param appFormId  the appFormId to set
	 */
	public void setAppFormId(String appFormId) {
		this.appFormId = appFormId;
	}
	/**
	 * @return  the bizCode
	 */
	public String getBizCode() {
		return bizCode;
	}
	/**
	 * @param bizCode  the bizCode to set
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	/**
	 * @return  the reqCode
	 */
	public String getReqCode() {
		return reqCode;
	}
	/**
	 * @param reqCode  the reqCode to set
	 */
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
	/**
	 * @return  the workType
	 */
        public String getWorkType() {
            return workType;
        }
	/**
	 * @param workType  the workType to set
	 */
        public void setWorkType(String workType) {
            this.workType = workType;
        }
	/**
	 * @return  the requestType
	 */
        public String getRequestType() {
            return requestType;
        }
	/**
	 * @param requestType  the requestType to set
	 */
        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }
	/**
	 * @return  the requestTime
	 */
        public String getRequestTime() {
            return requestTime;
        }
	/**
	 * @param requestTime  the requestTime to set
	 */
        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }
	/**
	 * @return  the takeoutTarget
	 */
        public String getTakeoutTarget() {
            return takeoutTarget;
        }
	/**
	 * @param takeoutTarget  the takeoutTarget to set
	 */
        public void setTakeoutTarget(String takeoutTarget) {
            this.takeoutTarget = takeoutTarget;
        }
	/**
	 * @return  the takeoutCharger
	 */
        public String getTakeoutCharger() {
            return takeoutCharger;
        }
	/**
	 * @param takeoutCharger  the takeoutCharger to set
	 */
        public void setTakeoutCharger(String takeoutCharger) {
            this.takeoutCharger = takeoutCharger;
        }
	/**
	 * @return  the takeoutContact
	 */
        public String getTakeoutContact() {
            return takeoutContact;
        }
	/**
	 * @param takeoutContact  the takeoutContact to set
	 */
        public void setTakeoutContact(String takeoutContact) {
            this.takeoutContact = takeoutContact;
        }
	/**
	 * @return  the takeoutContent
	 */
        public String getTakeoutContent() {
            return takeoutContent;
        }
	/**
	 * @param takeoutContent  the takeoutContent to set
	 */
        public void setTakeoutContent(String takeoutContent) {
            this.takeoutContent = takeoutContent;
        }
	/**
	 * @return  the requestReason
	 */
        public String getRequestReason() {
            return requestReason;
        }
	/**
	 * @param requestReason  the requestReason to set
	 */
        public void setRequestReason(String requestReason) {
            this.requestReason = requestReason;
        }
	/**
	 * @param alarmYn  the alarmYn to set
	 */
        public void setAlarmYn(String alarmYn) {
	    this.alarmYn = alarmYn;
        }
	/**
	 * @return  the alarmYn
	 */
        public String getAlarmYn() {
	    return alarmYn;
        }
	/**
	 * @param alarmNextYn  the alarmNextYn to set
	 */
        public void setAlarmNextYn(String alarmNextYn) {
	    this.alarmNextYn = alarmNextYn;
        }
	/**
	 * @return  the alarmNextYn
	 */
        public String getAlarmNextYn() {
	    return alarmNextYn;
        }

}
