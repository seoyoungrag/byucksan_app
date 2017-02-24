/**
 * 
 */
package com.sds.acube.app.mobile.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : MobileAppActionVO.java <br> Description : 결재 처리 요청 VO
 * @author   redcomet 
 * @since  2012. 7. 27.
 * @version  1.0 
 * @see  com.sds.acube.app.mobile.vo.MobileAppActionVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "MobileAppActionVO", propOrder = {

	"reqtype",
	"reqdate",
	"orgcode",
	"deptcode",
	"userid",
	"userpassword",
	"docid",
	"actioncode",
	"appopinion",
	"serialnumber",
	"signYn"
})


public class MobileAppActionVO {
    
    
    
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
    protected String deptcode       ; //부서코드       
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
	 */
    protected int serialnumber = 0;	; //문서일련번호    
    
    protected String signYn			; //서명사용여부
    
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
	 * @return  the deptcode
	 */
    public String getDeptcode() {
        return deptcode;
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
	 * @param deptcode  the deptcode to set
	 */
    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
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
	/**
	 * @return the serialnumber
	 */
	public int getSerialnumber() {
		return serialnumber;
	}
	/**
	 * @param serialnumber the serialnumber to set
	 */
	public void setSerialnumber(int serialnumber) {
		this.serialnumber = serialnumber;
	}
	/**
	 * @return the signYn
	 */
	public String getSignYn() {
		return signYn;
	}
	/**
	 * @param signYn the signYn to set
	 */
	public void setSignYn(String signYn) {
		this.signYn = signYn;
	}
 }
