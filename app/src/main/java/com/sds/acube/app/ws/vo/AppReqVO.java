/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AppReqVO.java <br> Description : 결재함 리스트조회 요청 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 5. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppReqVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "AppReqVO", propOrder = {

    	"reqtype"      ,
    	"reqdate"      ,
    	"orgcode"      ,
    	"userid"       ,
    	"userpassword" ,
    	"itemid"       ,
    	"deptid"       ,
    	"reqcount"     ,
    	"fromymd"      ,
    	"toymd"        ,
    	"fileId"
})


public class AppReqVO {
    

    
    /**
	 */
    protected String reqtype        ;
    /**
	 */
    protected String reqdate        ;
    /**
	 */
    protected String orgcode        ;
    /**
	 */
    protected String userid         ;
    /**
	 */
    protected String userpassword   ;
    /**
	 */
    protected String itemid         ;
    /**
	 */
    protected String deptid         ;
    /**
	 */
    protected int    reqcount;
    
    /**
	 */
    protected String fromymd;
    /**
	 */
    protected String toymd;
    /**
	 */
    protected String fileId;
    
    
    
    
    
    
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
	 * @return  the itemid
	 */
    public String getItemid() {
        return itemid;
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
	 * @param itemid  the itemid to set
	 */
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
    /**
	 * @return  the deptid
	 */
    public String getDeptid() {
        return deptid;
    }
    /**
	 * @param deptid  the deptid to set
	 */
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    /**
	 * @return  the reqcount
	 */
    public int getReqcount() {
        return reqcount;
    }
    /**
	 * @param reqcount  the reqcount to set
	 */
    public void setReqcount(int reqcount) {
        this.reqcount = reqcount;
    }
    /**
	 * @return  the fromymd
	 */
    public String getFromymd() {
        return fromymd;
    }
    /**
	 * @return  the toymd
	 */
    public String getToymd() {
        return toymd;
    }
    /**
	 * @param fromymd  the fromymd to set
	 */
    public void setFromymd(String fromymd) {
        this.fromymd = fromymd;
    }
    /**
	 * @param toymd  the toymd to set
	 */
    public void setToymd(String toymd) {
        this.toymd = toymd;
    }
    /**
	 * @return  the fileId
	 */
    public String getFileId() {
        return fileId;
    }
    /**
	 * @param fileId  the fileId to set
	 */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    
}
