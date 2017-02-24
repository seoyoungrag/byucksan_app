/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AppActionVO.java <br> Description : 결재 처리 요청 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 5. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppActionVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "AppActionVO", propOrder = {

	"reqtype",
	"reqdate",
	"orgcode",
	"userid",
	"userpassword",
	"docid",
	"actioncode",
	"appopinion"
})


public class AppActionVO {
    
    
    
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
    protected String userid         ; //사용자 ID      
    /**
	 */
    protected String userpassword   ; //사용자 패스워드
    /**
	 */
    protected String docid          ; //결재문서ID     
    /**
	 */
    protected String actioncode     ; //처리코드       
    /**
	 */
    protected String appopinion     ; //아이템 ID    
    
    
    
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
	 * @return  the userpassword
	 */
    public String getUserpassword() {
        return userpassword;
    }
    /**
	 * @return  the docid
	 */
    public String getDocid() {
        return docid;
    }
    /**
	 * @return  the actioncode
	 */
    public String getActioncode() {
        return actioncode;
    }
    /**
	 * @return  the appopinion
	 */
    public String getAppopinion() {
        return appopinion;
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
	 * @param userpassword  the userpassword to set
	 */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
    /**
	 * @param docid  the docid to set
	 */
    public void setDocid(String docid) {
        this.docid = docid;
    }
    /**
	 * @param actioncode  the actioncode to set
	 */
    public void setActioncode(String actioncode) {
        this.actioncode = actioncode;
    }
    /**
	 * @param appopinion  the appopinion to set
	 */
    public void setAppopinion(String appopinion) {
        this.appopinion = appopinion;
    }

    
    
     
  
 }
