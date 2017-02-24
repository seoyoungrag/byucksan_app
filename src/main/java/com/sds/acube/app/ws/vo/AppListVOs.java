/**
 * 
 */
package com.sds.acube.app.ws.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AppListVOs.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : chamchi  <br> 수정내용 :  <br>
 * @author   chamchi 
 * @since  2011. 5. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppListVOs_.java
 */



@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "AppListVOs", propOrder = {

	"reqtype",
	"reqdate",
	"orgcode",
	"userid",
	"respose_code",
	"error_message",
	"appListVOs"

})

public class AppListVOs {
    

    /**
	 */
    protected String reqtype       ;//요청구분    
    /**
	 */
    protected String reqdate       ;//요청일시    
    /**
	 */
    protected String orgcode       ;//회사코드    
    /**
	 */
    protected String userid        ;//사용자ID    
    /**
	 */
    protected String respose_code  ;//처리상태코드
    /**
	 */
    protected String error_message ;//오류내용    

    
    /*
     * 조회리스트
     */
    /**
	 */
    @XmlElement(nillable = true)
    protected List<AppListVO> appListVOs;

    /**
	 * @return  the appListVOs
	 */
    public List<AppListVO> getAppListVOs() {
        return appListVOs;
    }

    /**
	 * @param appListVOs  the appListVOs to set
	 */
    public void setAppListVOs(List<AppListVO> appListVOs) {
        this.appListVOs = appListVOs;
    }

    /**
	 * @return  the reqtype
	 */
    public String getReqtype() {
        return reqtype;
    }

    /**
	 * @return  the reqdate
	 */
    public String getReqdate() {
        return reqdate;
    }

    /**
	 * @return  the orgcode
	 */
    public String getOrgcode() {
        return orgcode;
    }

    /**
	 * @return  the userid
	 */
    public String getUserid() {
        return userid;
    }

    /**
	 * @return  the respose_code
	 */
    public String getRespose_code() {
        return respose_code;
    }

    /**
	 * @return  the error_message
	 */
    public String getError_message() {
        return error_message;
    }

    /**
	 * @param reqtype  the reqtype to set
	 */
    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    /**
	 * @param reqdate  the reqdate to set
	 */
    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    /**
	 * @param orgcode  the orgcode to set
	 */
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    /**
	 * @param userid  the userid to set
	 */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
	 * @param resposeCode  the respose_code to set
	 */
    public void setRespose_code(String resposeCode) {
        respose_code = resposeCode;
    }

    /**
	 * @param errorMessage  the error_message to set
	 */
    public void setError_message(String errorMessage) {
        error_message = errorMessage;
    }
    
    
 }
