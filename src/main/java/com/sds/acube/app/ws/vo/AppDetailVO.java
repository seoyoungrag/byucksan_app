/**
 * 
 */
package com.sds.acube.app.ws.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;


/**
 * Class Name  : AppDetailVO.java <br> Description : 결재 상세내용 조회 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 23. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 5. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AppDetailVO.java
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "AppDetailVO", propOrder = {

	"reqtype",
	"reqdate",
	"orgcode",
	"userid",
	"respose_code",
	"error_message",
	"title",
	"docid",
	"appline",
	"enfline",
	"appstatus",
	"electronDocYn",
	"docType",
	"content"
})


public class AppDetailVO {
    
    
    
    /**
	 */
    protected String reqtype      ; //요청구분    
    /**
	 */
    protected String reqdate      ; //요청일시    
    /**
	 */
    protected String orgcode      ; //회사코드    
    /**
	 */
    protected String userid       ; //사용자ID    
    /**
	 */
    protected String respose_code ; //처리상태코드
    /**
	 */
    protected String error_message; //오류내용    
    /**
	 */
    protected String title        ; //문서제목    
    /**
	 */
    protected String docid        ; //문서ID      
    /**
	 */
    protected List<AppLineVO>  appline      ; //결재경로    
    /**
	 */
    protected List<EnfLineVO>  enfline      ; //결재경로
    /**
	 */
    protected String appstatus    ; //결재상태       
    /**
	 */
    protected String docType; // 문서유형
    /**
	 */
    protected String electronDocYn; // 전자문서여부
    /**
	 */
    protected AppFileVOs  content       ; //첨부정보(본문포함)   
    
    
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
	 * @return  the appstatus
	 */
    public String getAppstatus() {
        return appstatus;
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
    /**
	 * @param title  the title to set
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
	 * @param appstatus  the appstatus to set
	 */
    public void setAppstatus(String appstatus) {
        this.appstatus = appstatus;
    }

    /**
	 * @return  the appline
	 */
    public List<AppLineVO> getAppline() {
        return appline;
    }

    /**
	 * @param appline  the appline to set
	 */
    public void setAppline(List<AppLineVO> appline) {
        this.appline = appline;
    }
    /**
	 * @return  the content
	 */
    public AppFileVOs getContent() {
        return content;
    }
    /**
	 * @param content  the content to set
	 */
    public void setContent(AppFileVOs content) {
        this.content = content;
    }
    /**
	 * @return  the enfline
	 */
    public List<EnfLineVO> getEnfline() {
        return enfline;
    }
    /**
	 * @param enfline  the enfline to set
	 */
    public void setEnfline(List<EnfLineVO> enfline) {
        this.enfline = enfline;
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
	 * @return  the electronDocYn
	 */
    public String getElectronDocYn() {
        return electronDocYn;
    }
    /**
	 * @param electronDocYn  the electronDocYn to set
	 */
    public void setElectronDocYn(String electronDocYn) {
        this.electronDocYn = electronDocYn;
    }    

 }
