/**
 * 
 */
package com.sds.acube.app.mobile.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : MobileAppResultVO.java <br> Description : 결재처리요청에 대한  응답 VO
 * @author   redcomet 
 * @since  2012. 7. 27.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppResultVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "MobileAppResultVO", propOrder = {

	"reqtype",
	"reqdate",
	"orgcode",
	"userid",
	"respose_code",
	"error_message"

})


public class MobileAppResultVO {
    
    
    /**
	 */
    protected String reqtype        ; //요청구분    
    /**
	 */
    protected String reqdate        ; //요청일시    
    /**
	 */
    protected String orgcode        ; //회사코드    
    /**
	 */
    protected String userid         ; //사용자ID    
    /**
	 */
    protected String respose_code   ; //처리상태코드
    /**
	 */
    protected String error_message  ; //오류내용    
    
    /**
	 * @return  the reqtype
	 */
    public String getReqtype() {
        return reqtype;
    }
    /**
	 * @param reqtype  the reqtype to set
	 */
    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }
    /**
	 * @return  the reqdate
	 */
    public String getReqdate() {
        return reqdate;
    }
    /**
	 * @param reqdate  the reqdate to set
	 */
    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }
    /**
	 * @return  the orgcode
	 */
    public String getOrgcode() {
        return orgcode;
    }
    /**
	 * @param orgcode  the orgcode to set
	 */
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
    /**
	 * @return  the userid
	 */
    public String getUserid() {
        return userid;
    }
    /**
	 * @param userid  the userid to set
	 */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
	 * @return  the respose_code
	 */
    public String getRespose_code() {
        return respose_code;
    }
    /**
	 * @param resposeCode  the respose_code to set
	 */
    public void setRespose_code(String resposeCode) {
        respose_code = resposeCode;
    }
    /**
	 * @return  the error_message
	 */
    public String getError_message() {
        return error_message;
    }
    /**
	 * @param errorMessage  the error_message to set
	 */
    public void setError_message(String errorMessage) {
        error_message = errorMessage;
    }
  
 }
