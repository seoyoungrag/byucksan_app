package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;


/**
 * Class Name : BindUnitTempVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2014. 10. 1. <br> 수 정 자 : Dony <br> 수정내용 : <br>
 * @author  Dony
 * @since  2014. 10. 1.
 * @version  1.0
 * @see  com.sds.acube.app.bind.vo.BindUnitTempVO.java
 */

public class BindUnitTempVO implements BindConstants {
    
    /**
     * 회사코드
     */
    private String tunitCompId;
    /**
     * 처리아이디
     */
    private String tunitProcId;
    /**
     * 처리일시
     */
    private String tunitProcDate;
    /**
     * 유효기준일
     */
    private String tunitEfctDate;
    /**
     * 부서아이디
     */
    private String tunitDptId;
    /**
     * 부서명
     */
    private String tunitDptName;
    /**
     * 이전아이디
     */
    private String tunitPreId;
    /**
     * 대분류코드
     */
    private String tunit1stCode;
    /**
     * 대분류명
     */
    private String tunit1stName;
    /**
     * 중분류코드
     */
    private String tunit2ndCode;
    /**
     * 중분류명
     */
    private String tunit2ndName;
    /**
     * 소분류코드
     */
    private String tunit3rdCode;
    /**
     * 소분류명
     */
    private String tunit3rdName;
    /**
     * 단위업무아이디
     */
    private String tunitId;
    /**
     * 단위업무명
     */
    private String tunitName;
    /**
     * 단위업무설명
     */
    private String tunitDesc;
    /**
     * 보존기간
     */
    private String tunitSavId;
    /**
     * 보존기간책정사유
     */
    private String tunitNote;
    /**
     * 보존방법
     */
    private String tunitSavType;
    /**
     * 보존장소
     */
    private String tunitSavPlace;
    /**
     * 비치기록물여부
     */
    private String tunitProdBool;
    /**
     * 비치기록물이관시기
     */
    private String tunitProdTrans;
    /**
     * 열람빈도
     */
    private String tunitViewFreq;
    /**
     * 열람용도
     */
    private String tunitViewUse;
    /**
     * 추가타입
     */
    private String tunitAddType;
    /**
     * 추가제목
     */
    private String tunitAddTtl;
    /**
     * 수정일시
     */
    private String tunitModDate;
    /**
     * 처리여부
     */
    private String tunitProcBool;
    /**
     * 파일아이디
     */
    private String tunitFileId;
    /**
     * 처리유형
     */
    private String tunitProcType;
    /**
     * 이관부서아이디
     */
    private String tunitTrnDptId;
    /**
     * 이관부서명
     */
    private String tunitTrnDptName;
    /**
     */
    private int totalCount;
    
    
    /**
     * @return the tunitCompId
     */
    public String getTunitCompId() {
        return tunitCompId;
    }
    /**
     * @param tunitCompId the tunitCompId to set
     */
    public void setTunitCompId(String tunitCompId) {
        this.tunitCompId = tunitCompId;
    }
    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }
    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    /**
     * @return the tunitProcId
     */
    public String getTunitProcId() {
        return tunitProcId;
    }
    /**
     * @param tunitProcId the tunitProcId to set
     */
    public void setTunitProcId(String tunitProcId) {
        this.tunitProcId = tunitProcId;
    }
    /**
     * @return the tunitProcDate
     */
    public String getTunitProcDate() {
        return tunitProcDate;
    }
    /**
     * @param tunitProcDate the tunitProcDate to set
     */
    public void setTunitProcDate(String tunitProcDate) {
        this.tunitProcDate = tunitProcDate;
    }
    /**
     * @return the tunitEfctDate
     */
    public String getTunitEfctDate() {
        return tunitEfctDate;
    }
    /**
     * @param tunitEfctDate the tunitEfctDate to set
     */
    public void setTunitEfctDate(String tunitEfctDate) {
        this.tunitEfctDate = tunitEfctDate;
    }
    /**
     * @return the tunitDptId
     */
    public String getTunitDptId() {
        return tunitDptId;
    }
    /**
     * @param tunitDptId the tunitDptId to set
     */
    public void setTunitDptId(String tunitDptId) {
        this.tunitDptId = tunitDptId;
    }
    /**
     * @return the tunitDptName
     */
    public String getTunitDptName() {
        return tunitDptName;
    }
    /**
     * @param tunitDptName the tunitDptName to set
     */
    public void setTunitDptName(String tunitDptName) {
        this.tunitDptName = tunitDptName;
    }
    /**
     * @return the tunitPreId
     */
    public String getTunitPreId() {
        return tunitPreId;
    }
    /**
     * @param tunitPreId the tunitPreId to set
     */
    public void setTunitPreId(String tunitPreId) {
        this.tunitPreId = tunitPreId;
    }
    /**
     * @return the tunit1stCode
     */
    public String getTunit1stCode() {
        return tunit1stCode;
    }
    /**
     * @param tunit1stCode the tunit1stCode to set
     */
    public void setTunit1stCode(String tunit1stCode) {
        this.tunit1stCode = tunit1stCode;
    }
    /**
     * @return the tunit1stName
     */
    public String getTunit1stName() {
        return tunit1stName;
    }
    /**
     * @param tunit1stName the tunit1stName to set
     */
    public void setTunit1stName(String tunit1stName) {
        this.tunit1stName = tunit1stName;
    }
    /**
     * @return the tunit2ndCode
     */
    public String getTunit2ndCode() {
        return tunit2ndCode;
    }
    /**
     * @param tunit2ndCode the tunit2ndCode to set
     */
    public void setTunit2ndCode(String tunit2ndCode) {
        this.tunit2ndCode = tunit2ndCode;
    }
    /**
     * @return the tunit2ndName
     */
    public String getTunit2ndName() {
        return tunit2ndName;
    }
    /**
     * @param tunit2ndName the tunit2ndName to set
     */
    public void setTunit2ndName(String tunit2ndName) {
        this.tunit2ndName = tunit2ndName;
    }
    /**
     * @return the tunit3rdCode
     */
    public String getTunit3rdCode() {
        return tunit3rdCode;
    }
    /**
     * @param tunit3rdCode the tunit3rdCode to set
     */
    public void setTunit3rdCode(String tunit3rdCode) {
        this.tunit3rdCode = tunit3rdCode;
    }
    /**
     * @return the tunit3rdName
     */
    public String getTunit3rdName() {
        return tunit3rdName;
    }
    /**
     * @param tunit3rdName the tunit3rdName to set
     */
    public void setTunit3rdName(String tunit3rdName) {
        this.tunit3rdName = tunit3rdName;
    }
    /**
     * @return the tunitId
     */
    public String getTunitId() {
        return tunitId;
    }
    /**
     * @param tunitId the tunitId to set
     */
    public void setTunitId(String tunitId) {
        this.tunitId = tunitId;
    }
    /**
     * @return the tunitName
     */
    public String getTunitName() {
        return tunitName;
    }
    /**
     * @param tunitName the tunitName to set
     */
    public void setTunitName(String tunitName) {
        this.tunitName = tunitName;
    }
    /**
     * @return the tunitDesc
     */
    public String getTunitDesc() {
        return tunitDesc;
    }
    /**
     * @param tunitDesc the tunitDesc to set
     */
    public void setTunitDesc(String tunitDesc) {
        this.tunitDesc = tunitDesc;
    }
    /**
     * @return the tunitSavId
     */
    public String getTunitSavId() {
        return tunitSavId;
    }
    /**
     * @param tunitSavId the tunitSavId to set
     */
    public void setTunitSavId(String tunitSavId) {
        this.tunitSavId = tunitSavId;
    }
    /**
     * @return the tunitNote
     */
    public String getTunitNote() {
        return tunitNote;
    }
    /**
     * @param tunitNote the tunitNote to set
     */
    public void setTunitNote(String tunitNote) {
        this.tunitNote = tunitNote;
    }
    /**
     * @return the tunitSavType
     */
    public String getTunitSavType() {
        return tunitSavType;
    }
    /**
     * @param tunitSavType the tunitSavType to set
     */
    public void setTunitSavType(String tunitSavType) {
        this.tunitSavType = tunitSavType;
    }
    /**
     * @return the tunitSavPlace
     */
    public String getTunitSavPlace() {
        return tunitSavPlace;
    }
    /**
     * @param tunitSavPlace the tunitSavPlace to set
     */
    public void setTunitSavPlace(String tunitSavPlace) {
        this.tunitSavPlace = tunitSavPlace;
    }
    /**
     * @return the tunitProdbool
     */
    public String getTunitProdBool() {
        return tunitProdBool;
    }
    /**
     * @param tunitProdbool the tunitProdbool to set
     */
    public void setTunitProdBool(String tunitProdbool) {
        this.tunitProdBool = tunitProdbool;
    }
    /**
     * @return the tunitProdTrans
     */
    public String getTunitProdTrans() {
        return tunitProdTrans;
    }
    /**
     * @param tunitProdTrans the tunitProdTrans to set
     */
    public void setTunitProdTrans(String tunitProdTrans) {
        this.tunitProdTrans = tunitProdTrans;
    }
    /**
     * @return the tunitViewFreq
     */
    public String getTunitViewFreq() {
        return tunitViewFreq;
    }
    /**
     * @param tunitViewFreq the tunitViewFreq to set
     */
    public void setTunitViewFreq(String tunitViewFreq) {
        this.tunitViewFreq = tunitViewFreq;
    }
    /**
     * @return the tunitViewUse
     */
    public String getTunitViewUse() {
        return tunitViewUse;
    }
    /**
     * @param tunitViewUse the tunitViewUse to set
     */
    public void setTunitViewUse(String tunitViewUse) {
        this.tunitViewUse = tunitViewUse;
    }
    /**
     * @return the tunitAddType
     */
    public String getTunitAddType() {
        return tunitAddType;
    }
    /**
     * @param tunitAddType the tunitAddType to set
     */
    public void setTunitAddType(String tunitAddType) {
        this.tunitAddType = tunitAddType;
    }
    /**
     * @return the tunitAddTtl
     */
    public String getTunitAddTtl() {
        return tunitAddTtl;
    }
    /**
     * @param tunitAddTtl the tunitAddTtl to set
     */
    public void setTunitAddTtl(String tunitAddTtl) {
        this.tunitAddTtl = tunitAddTtl;
    }
    /**
     * @return the tunitModDate
     */
    public String getTunitModDate() {
        return tunitModDate;
    }
    /**
     * @param tunitModDate the tunitModDate to set
     */
    public void setTunitModDate(String tunitModDate) {
        this.tunitModDate = tunitModDate;
    }
    /**
     * @return the tunitProcBool
     */
    public String getTunitProcBool() {
        return tunitProcBool;
    }
    /**
     * @param tunitProcBool the tunitProcBool to set
     */
    public void setTunitProcBool(String tunitProcBool) {
        this.tunitProcBool = tunitProcBool;
    }
    /**
     * @return the tunitFileId
     */
    public String getTunitFileId() {
        return tunitFileId;
    }
    /**
     * @param tunitFileId the tunitFileId to set
     */
    public void setTunitFileId(String tunitFileId) {
        this.tunitFileId = tunitFileId;
    }
    /**
     * @return the tunitProcType
     */
    public String getTunitProcType() {
        return tunitProcType;
    }
    /**
     * @param tunitProcType the tunitProcType to set
     */
    public void setTunitProcType(String tunitProcType) {
        this.tunitProcType = tunitProcType;
    }
    /**
     * @return the tunitTrnDptId
     */
    public String getTunitTrnDptId() {
        return tunitTrnDptId;
    }
    /**
     * @param tunitTrnDptId the tunitTrnDptId to set
     */
    public void setTunitTrnDptId(String tunitTrnDptId) {
        this.tunitTrnDptId = tunitTrnDptId;
    }
    /**
     * @return the tunitTrnDptName
     */
    public String getTunitTrnDptName() {
        return tunitTrnDptName;
    }
    
    /**
     * @param tunitTrnDptName the tunitTrnDptName to set
     */
    public void setTunitTrnDptName(String tunitTrnDptName) {
        this.tunitTrnDptName = tunitTrnDptName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BindUnitTempVO [tunitProcId=" + tunitProcId + ", tunitProcDate=" + tunitProcDate + ", tunitEfctDate=" + tunitEfctDate + ", tunitDptId=" + tunitDptId
                + ", tunitDptName=" + tunitDptName + ", tunitPreId=" + tunitPreId + ", tunit1stCode=" + tunit1stCode + ", tunit1stName=" + tunit1stName + ", tunit2ndCode="
                + tunit2ndCode + ", tunit2ndName=" + tunit2ndName + ", tunit3rdCode=" + tunit3rdCode + ", tunit3rdName=" + tunit3rdName + ", tunitId=" + tunitId + ", tunitName="
                + tunitName + ", tunitDesc=" + tunitDesc + ", tunitSavId=" + tunitSavId + ", tunitNote=" + tunitNote + ", tunitSavType=" + tunitSavType + ", tunitSavPlace="
                + tunitSavPlace + ", tunitProdbool=" + tunitProdBool + ", tunitProdTrans=" + tunitProdTrans + ", tunitViewFreq=" + tunitViewFreq + ", tunitViewUse=" + tunitViewUse
                + ", tunitAddType=" + tunitAddType + ", tunitAddTtl=" + tunitAddTtl + ", tunitModDate=" + tunitModDate + ", tunitProcBool=" + tunitProcBool + ", tunitFileId="
                + tunitFileId + ", tunitProcType=" + tunitProcType + ", tunitTrnDptId=" + tunitTrnDptId + ", tunitTrnDptName=" + tunitTrnDptName + "]";
    }
    
}


