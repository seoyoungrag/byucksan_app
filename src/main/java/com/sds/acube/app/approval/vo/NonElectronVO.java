/**
 * 
 */
package com.sds.acube.app.approval.vo;

/**
 * Class Name : NonElectronVO.java <br> Description : 회사별 생산되는 비전자문서의 정보를 관리한다. <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 28. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 4. 28.
 * @version  1.0
 * @see  com.sds.acube.app.approval.vo.NonElectronVO.java
 */

public class NonElectronVO {

    /**
	 * 문서ID
	 */
    private String docId = "";
    /**
	 * 회사ID
	 */
    private String compId = "";
    /**
	 * 시행대상
	 */
    private String enfTarget = "";
    /**
	 * 수신자
	 */
    private String receivers = "";
    /**
	 * 시행일자
	 */
    private String enforceDate = "";
    /**
	 * 쪽수
	 */
    private String pageCount = "";
    /**
	 * 문서형태
	 */
    private String apDty = "";
    /**
	 * 특수기록물
	 */
    private String specialRec = "";
    /**
	 * 기록물형태
	 */
    private String recType = "";
    /**
	 * 내용요약
	 */
    private String recSummary = "";
    /**
	 * 요약
	 */
    private String summary = "";

    /**
	 */
    private boolean noSerialYn = false;
    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDocId() {
	return docId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCompId() {
	return compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getEnfTarget() {
	return enfTarget;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getReceivers() {
	return receivers;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getEnforceDate() {
	return enforceDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPageCount() {
	return pageCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getApDty() {
	return apDty;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSpecialRec() {
	return specialRec;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRecType() {
	return recType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRecSummary() {
	return recSummary;
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public boolean getNoSerialYn(){
	return noSerialYn;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param docId
	 * @see   
	 */
    public void setDocId(String docId) {
	this.docId = docId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param compId
	 * @see   
	 */
    public void setCompId(String compId) {
	this.compId = compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param enfTarget
	 * @see   
	 */
    public void setEnfTarget(String enfTarget) {
	this.enfTarget = enfTarget;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param receivers
	 * @see   
	 */
    public void setReceivers(String receivers) {
	this.receivers = receivers;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param enforceDate
	 * @see   
	 */
    public void setEnforceDate(String enforceDate) {
	this.enforceDate = enforceDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param pageCount
	 * @see   
	 */
    public void setPageCount(String pageCount) {
	this.pageCount = pageCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param apDty
	 * @see   
	 */
    public void setApDty(String apDty) {
	this.apDty = apDty;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param specialRec
	 * @see   
	 */
    public void setSpecialRec(String specialRec) {
	this.specialRec = specialRec;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param recType
	 * @see   
	 */
    public void setRecType(String recType) {
	this.recType = recType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param recSummary
	 * @see   
	 */
    public void setRecSummary(String recSummary) {
	this.recSummary = recSummary;
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @param noSerialYn
	 * @see   
	 */
    public void setNoSerialYn(boolean noSerialYn){
	this.noSerialYn = noSerialYn;
    }


    /**
	 * @return  the summary
	 */
    public String getSummary() {
        return summary;
    }


    /**
	 * @param summary  the summary to set
	 */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
