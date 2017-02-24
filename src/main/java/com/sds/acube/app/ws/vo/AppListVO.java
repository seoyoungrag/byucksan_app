/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AppListVO.java <br> Description : 결재리스트 조회 결과 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author    윤동원 
 * @since   2011. 5. 23.
 * @version   1.0 
 * @see  com.sds.acube.app.ws.vo.AppListVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "appListVO", propOrder = {

	"title",
	"docid",
	"username",
	"orgname",
	"appdate",
	"appstatus",
	"elecDocYn",
	"urgencyYn",
	"auditDivision"
	
})


public class AppListVO {
    

    /**
	 */
    protected String title         ;//문서제목    
    /**
	 */
    protected String docid         ;//문서ID      
    /**
	 */
    protected String username      ;//기안자이름  
    /**
	 */
    protected String orgname       ;//기안부서이름
    /**
	 */
    protected String appdate       ;//기안일자    
    /**
	 */
    protected String appstatus     ;//결재상태    
    /**
	 */
    protected String elecDocYn	   ;//전자/비전자구분
    /**
	 */
    protected String urgencyYn	   ;//긴급여부
    /**
	 */
    protected String auditDivision ;//감사구분
    
    
    

    
    /**
	 * @return  the title
	 */
    public String getTitle() {
        return title;
    }
    /**
	 * @return  the docid
	 */
    public String getDocid() {
        return docid;
    }
    /**
	 * @return  the username
	 */
    public String getUsername() {
        return username;
    }
    /**
	 * @return  the orgname
	 */
    public String getOrgname() {
        return orgname;
    }
    /**
	 * @return  the appdate
	 */
    public String getAppdate() {
        return appdate;
    }
    /**
	 * @return  the appstatus
	 */
    public String getAppstatus() {
        return appstatus;
    }
    /**
     * @param reqtype the reqtype to set
     */

    /**
	 * @param reqtype  the reqtype to set
	 */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
	 * @param docid  the docid to set
	 */
    public void setDocid(String docid) {
        this.docid = docid;
    }
    /**
	 * @param username  the username to set
	 */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * @param orgname  the orgname to set
	 */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    /**
	 * @param appdate  the appdate to set
	 */
    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }
    /**
	 * @param appstatus  the appstatus to set
	 */
    public void setAppstatus(String appstatus) {
        this.appstatus = appstatus;
    }
    /**
	 * @return  the elecYn
	 */
    public String getElecDocYn() {
        return elecDocYn;
    }
    /**
	 * @param elecYn  the elecYn to set
	 */
    public void setElecDocYn(String elecDocYn) {
        this.elecDocYn = elecDocYn;
    }
    /**
	 * @return  the urgencyYn
	 */
    public String getUrgencyYn() {
        return urgencyYn;
    }
    /**
	 * @param urgencyYn  the urgencyYn to set
	 */
    public void setUrgencyYn(String urgencyYn) {
        this.urgencyYn = urgencyYn;
    }
    /**
	 * @return  the auditDivision
	 */
    public String getAuditDivision() {
        return auditDivision;
    }
    /**
	 * @param auditDivision  the auditDivision to set
	 */
    public void setAuditDivision(String auditDivision) {
        this.auditDivision = auditDivision;
    }
    
   
}
