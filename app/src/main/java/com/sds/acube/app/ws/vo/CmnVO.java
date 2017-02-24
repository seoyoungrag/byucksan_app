/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : CmnVO.java <br> Description : ESB 공통 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 12. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.CmnVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "cmnVO", propOrder = {

    "sendCompanyCd",
    "messageId",
    "businessDivCd",
    "businessDivDetailCd",
    "requestDateTime",
    "sendSystemCd",
    "receiveCompanyCd",
    "receiveSystemCd",
    "requestId"    
})


public class CmnVO {
    
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
    private String receiveCompanyCd;
    /**
	 */
    private String receiveSystemCd;
    /**
	 */
    private String requestId;
    
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
	 * @return  the receiveCompanyCd
	 */
    public String getReceiveCompanyCd() {
        return receiveCompanyCd;
    }
    /**
	 * @param receiveCompanyCd  the receiveCompanyCd to set
	 */
    public void setReceiveCompanyCd(String receiveCompanyCd) {
        this.receiveCompanyCd = receiveCompanyCd;
    }
    
    /**
	 * @return  the receiveSystemCd
	 */
    public String getReceiveSystemCd() {
        return receiveSystemCd;
    }
    /**
	 * @param receiveSystemCd  the receiveSystemCd to set
	 */
    public void setReceiveSystemCd(String receiveSystemCd) {
        this.receiveSystemCd = receiveSystemCd;
    }
    
    /**
	 * @return  the requestId
	 */
    public String getRequestId() {
        return requestId;
    }
    /**
	 * @param requestId  the requestId to set
	 */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
}
