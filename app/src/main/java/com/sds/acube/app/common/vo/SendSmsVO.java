/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name  : SendSMS.java <br> Description : SMS 알림 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 6. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 6.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.SendSmsVO.java
 */

public class SendSmsVO {
    
    /**
	 * 사번
	 */
    private String sabun;
    /**
	 * 수신자 전화번호
	 */
    private String toMobile;
    /**
	 * 송신자 전화번호
	 */
    private String fromMobile;
    /**
	 * 발송일자 : yyyy-mm-dd
	 */
    private String sendDate;
    /**
	 * 발송시간 : hh.mm.ss
	 */
    private String sendTime;
    /**
	 * errCode (발송시 공백문자)
	 */
    private String errCode;
    /**
	 * 메시지
	 */
    private String message;
    
    
    /**
	 * @return  the sabun
	 */
    public String getSabun() {
        return sabun;
    }
    /**
	 * @param sabun  the sabun to set
	 */
    public void setSabun(String sabun) {
        this.sabun = sabun;
    }
    /**
	 * @return  the toMobile
	 */
    public String getToMobile() {
        return toMobile;
    }
    /**
	 * @param toMobile  the toMobile to set
	 */
    public void setToMobile(String toMobile) {
        this.toMobile = toMobile;
    }
    /**
	 * @return  the fromMobile
	 */
    public String getFromMobile() {
        return fromMobile;
    }
    /**
	 * @param fromMobile  the fromMobile to set
	 */
    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }
    /**
	 * @return  the sendDate
	 */
    public String getSendDate() {
        return sendDate;
    }
    /**
	 * @param sendDate  the sendDate to set
	 */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    /**
	 * @return  the sendTime
	 */
    public String getSendTime() {
        return sendTime;
    }
    /**
	 * @param sendTime  the sendTime to set
	 */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    /**
	 * @return  the errCode
	 */
    public String getErrCode() {
        return errCode;
    }
    /**
	 * @param errCode  the errCode to set
	 */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    /**
	 * @return  the message
	 */
    public String getMessage() {
        return message;
    }
    /**
	 * @param message  the message to set
	 */
    public void setMessage(String message) {
        this.message = message;
    }    

}
