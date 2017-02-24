/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AcknowledgeVO.java <br> Description : 연계를 통한 전자결재 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 4. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 4. 4.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.EsbAppDocVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "acknowledge", propOrder = {

    "docId",
    "title",
    "docNum",
    "systemCode",
    "businessCode",
    "businessdocid",
    "compId",
    "approverId",
    "approverName",
    "approverPos",
    "approverDeptName",
    "approverDeptCode",
    "orgName",
    "orgCode",
    "telephone",
    "email",
    "processType",
    "opinion",
    "processDate",
    "docType",
    "docstate"
    
})


public class AcknowledgeVO {
    /*
     * 문서id
     */
    protected String docId ;
    
    /*
     * 회사id
     */
    protected String compId;
    
    /*
     * 결재자 아이디
     */
    protected String approverId;
    
    /*
     * 결재자 명
     */
    protected String approverName;
    
    /*
     * 결재자 직위
     */
    protected String approverPos;
    
    /*
     * 결재자부서명
     */
    protected String approverDeptName;
    
    /*
     * 결재자부서코드
     */
    protected String approverDeptCode;
    
    /*
     * 조직명
     */
    protected String orgName;
    
    /*
     * 조직코드
     */
    protected String orgCode;
    
    /*
     * 전화번호
     */
    protected String telephone;
    
    /*
     * 이메일
     */
    protected String email;
    
    /*
     * 처리유형
     */
    protected String processType;
     
    /*
     * 의견
     */
    protected String opinion;
    
    /*
     * 처리일자
     */
    protected String processDate;
    
    /*
     * 문서제목
     */
    protected String title;
    
    /*
     * 문서번호
     */
    protected String docNum;
    
    /*
     * 송신시스템코드
     */
    protected String systemCode;
    
    /*
     * 송신시스템업무코드
     */
    protected String businessCode;
    
    /*
     * 송신시스템문서id
     */
    protected String businessdocid;
    
    
    /*
     * 문서상태
     */
    protected String docstate;
    
    
    /*
     * 문서형태
     */
    protected String docType;
    
    /**
	 * @return  the docId
	 */
    public String getDocId() {
        return docId;
    }

    /**
	 * @return  the compId
	 */
    public String getCompId() {
        return compId;
    }

    /**
	 * @return  the approverId
	 */
    public String getApproverId() {
        return approverId;
    }

    /**
	 * @return  the approverName
	 */
    public String getApproverName() {
        return approverName;
    }

    /**
	 * @return  the approverPos
	 */
    public String getApproverPos() {
        return approverPos;
    }

    /**
	 * @return  the approverDeptName
	 */
    public String getApproverDeptName() {
        return approverDeptName;
    }

    /**
	 * @return  the approverDeptCode
	 */
    public String getApproverDeptCode() {
        return approverDeptCode;
    }

    /**
	 * @return  the orgName
	 */
    public String getOrgName() {
        return orgName;
    }

    /**
	 * @return  the telephone
	 */
    public String getTelephone() {
        return telephone;
    }

    /**
	 * @return  the email
	 */
    public String getEmail() {
        return email;
    }

    /**
	 * @return  the processType
	 */
    public String getProcessType() {
        return processType;
    }

    /**
	 * @return  the opinion
	 */
    public String getOpinion() {
        return opinion;
    }

    /**
	 * @return  the processDate
	 */
    public String getProcessDate() {
        return processDate;
    }

    /**
	 * @param docId  the docId to set
	 */
    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
	 * @param compId  the compId to set
	 */
    public void setCompId(String compId) {
        this.compId = compId;
    }

    /**
	 * @param approverId  the approverId to set
	 */
    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    /**
	 * @param approverName  the approverName to set
	 */
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    /**
	 * @param approverPos  the approverPos to set
	 */
    public void setApproverPos(String approverPos) {
        this.approverPos = approverPos;
    }

    /**
	 * @param approverDeptName  the approverDeptName to set
	 */
    public void setApproverDeptName(String approverDeptName) {
        this.approverDeptName = approverDeptName;
    }

    /**
	 * @param approverDeptCode  the approverDeptCode to set
	 */
    public void setApproverDeptCode(String approverDeptCode) {
        this.approverDeptCode = approverDeptCode;
    }

    /**
	 * @param orgName  the orgName to set
	 */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
	 * @param telephone  the telephone to set
	 */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
	 * @param email  the email to set
	 */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
	 * @param processType  the processType to set
	 */
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    /**
	 * @param opinion  the opinion to set
	 */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
	 * @param processDate  the processDate to set
	 */
    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    /**
	 * @return  the orgCode
	 */
    public String getOrgCode() {
        return orgCode;
    }

    /**
	 * @param orgCode  the orgCode to set
	 */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
	 * @return  the title
	 */
    public String getTitle() {
        return title;
    }

    /**
	 * @return  the docNum
	 */
    public String getDocNum() {
        return docNum;
    }

    /**
	 * @return  the systemCode
	 */
    public String getSystemCode() {
        return systemCode;
    }

    /**
	 * @return  the businessCode
	 */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
	 * @return  the businessdocid
	 */
    public String getBusinessdocid() {
        return businessdocid;
    }

    /**
	 * @param title  the title to set
	 */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
	 * @param docNum  the docNum to set
	 */
    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    /**
	 * @param systemCode  the systemCode to set
	 */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
	 * @param businessCode  the businessCode to set
	 */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
	 * @param businessdocid  the businessdocid to set
	 */
    public void setBusinessdocid(String businessdocid) {
        this.businessdocid = businessdocid;
    }

    /**
	 * @return  the docType
	 */
    public String getDocType() {
        return docType;
    }

    /**
	 * @param docType  the docType to set
	 */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
	 * @return  the docstate
	 */
    public String getDocstate() {
        return docstate;
    }

    /**
	 * @param docstate  the docstate to set
	 */
    public void setDocstate(String docstate) {
        this.docstate = docstate;
    }

}
