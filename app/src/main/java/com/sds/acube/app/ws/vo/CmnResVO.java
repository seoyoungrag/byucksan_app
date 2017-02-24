/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : CmmResVO.java <br> Description : 결재처리결과 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 4. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 4. 4.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.CmnResVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "CmnResVO", propOrder = {

    "response_date",
    "response_code",
    "error_message",
    "sendCompanyCd",
    "messageId",
    "businessDivCd",
    "businessDivDetailCd",
    "requestDateTime",
    "sendSystemCd",
    "dealDateTime",
    "dealStatusCd",
    "errorCd",
    "errorContent"
})


public class CmnResVO {
    

    /*
     * 처리일시
     */
    /**
	 */
    protected String response_date;
    
    /*
     * 처리상태코드
     */
    /**
	 */
    protected String response_code;
    
    /*
     * 오류내용
     */
    /**
	 */
    protected String error_message;
    
    
    /**
	 */
    private String sendCompanyCd;
    /**
	 */
    private String messageId;
    /**
	 */
    private String businessDivCd;
    /**
	 */
    private String businessDivDetailCd;
    /**
	 */
    private String requestDateTime;
    /**
	 */
    private String sendSystemCd;
    /**
	 */
    private String dealDateTime;
    /**
	 */
    private String dealStatusCd;
    /**
	 */
    private String errorCd;
    /**
	 */
    private String errorContent;

    
    /**
	 * @return  the response_date
	 */
    public String getResponse_date() {
        return response_date;
    }

    /**
	 * @return  the response_code
	 */
    public String getResponse_code() {
        return response_code;
    }

    /**
	 * @return  the error_message
	 */
    public String getError_message() {
        return error_message;
    }

    
    /**
	 * @param responseDate  the response_date to set
	 */
    public void setResponse_date(String responseDate) {
        response_date = responseDate;
    }

    /**
	 * @param responseCode  the response_code to set
	 */
    public void setResponse_code(String responseCode) {
        response_code = responseCode;
    }

    /**
	 * @param errorMessage  the error_message to set
	 */
    public void setError_message(String errorMessage) {
        error_message = errorMessage;
    }

    /**
	 * @return  the sendCompanyCd
	 */
    public String getSendCompanyCd() {
        return sendCompanyCd;
    }

    /**
	 * @param sendCompanyCd  the sendCompanyCd to set
	 */
    public void setSendCompanyCd(String sendCompanyCd) {
        this.sendCompanyCd = sendCompanyCd;
    }

    /**
	 * @return  the messageId
	 */
    public String getMessageId() {
        return messageId;
    }

    /**
	 * @param messageId  the messageId to set
	 */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
	 * @return  the businessDivCd
	 */
    public String getBusinessDivCd() {
        return businessDivCd;
    }

    /**
	 * @param businessDivCd  the businessDivCd to set
	 */
    public void setBusinessDivCd(String businessDivCd) {
        this.businessDivCd = businessDivCd;
    }

    /**
	 * @return  the businessDivDetailCd
	 */
    public String getBusinessDivDetailCd() {
        return businessDivDetailCd;
    }

    /**
	 * @param businessDivDetailCd  the businessDivDetailCd to set
	 */
    public void setBusinessDivDetailCd(String businessDivDetailCd) {
        this.businessDivDetailCd = businessDivDetailCd;
    }

    /**
	 * @return  the requestDateTime
	 */
    public String getRequestDateTime() {
        return requestDateTime;
    }

    /**
	 * @param requestDateTime  the requestDateTime to set
	 */
    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    /**
	 * @return  the sendSystemCd
	 */
    public String getSendSystemCd() {
        return sendSystemCd;
    }

    /**
	 * @param sendSystemCd  the sendSystemCd to set
	 */
    public void setSendSystemCd(String sendSystemCd) {
        this.sendSystemCd = sendSystemCd;
    }

    /**
	 * @return  the dealDateTime
	 */
    public String getDealDateTime() {
        return dealDateTime;
    }

    /**
	 * @param dealDateTime  the dealDateTime to set
	 */
    public void setDealDateTime(String dealDateTime) {
        this.dealDateTime = dealDateTime;
    }

    /**
	 * @return  the dealStatusCd
	 */
    public String getDealStatusCd() {
        return dealStatusCd;
    }

    /**
	 * @param dealStatusCd  the dealStatusCd to set
	 */
    public void setDealStatusCd(String dealStatusCd) {
        this.dealStatusCd = dealStatusCd;
    }

    /**
	 * @return  the errorCd
	 */
    public String getErrorCd() {
        return errorCd;
    }

    /**
	 * @param errorCd  the errorCd to set
	 */
    public void setErrorCd(String errorCd) {
        this.errorCd = errorCd;
    }

    /**
	 * @return  the errorContent
	 */
    public String getErrorContent() {
        return errorContent;
    }

    /**
	 * @param errorContent  the errorContent to set
	 */
    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }
    
}
