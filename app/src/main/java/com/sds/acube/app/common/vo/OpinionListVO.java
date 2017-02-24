/**
 * 
 */
package com.sds.acube.app.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;


/**
 * Class Name : AuditListVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 19. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 5. 19.
 * @version  1.0
 * @see  com.sds.acube.app.common.vo.AuditListVO.java
 */

public class OpinionListVO {
    /**
	 * 문서ID
	 */
    private String docId;
    /**
	 * 회사ID
	 */
    private String compId;
    /**
	 * 제목
	 */
    private String title;
    /**
	 * 문서번호
	 */
    private String docNumber;
    /**
	 * 조치일자
	 */
    private String actionDate = "9999-12-31 23:59:59";
    /**
	 * 감사번호
	 */
    private int auditNumber = 0;
    /**
	 * 담당부서ID
	 */
    private String chargeDeptId;
    /**
	 * 담당부서명
	 */
    private String chargeDeptName;
    /**
	 * 전자문서여부
	 */
    private String electronDocYn;
    /**
	 * 접수일자
	 */
    private String receiveDate = "9999-12-31 23:59:59";
    /**
	 * 결재자유형
	 */
    private String approverType;
    /**
	 * 결재유형
	 */
    private String approveType;
    /**
	 * 결재자ID
	 */
    private String approverId;
    /**
	 * 결재자명
	 */
    private String approverName;
    /**
	 * 결재자직위
	 */
    private String approverPos;
    /**
	 * 결재자부서ID
	 */
    private String approverDeptId;
    /**
	 * 결재자부서명
	 */
    private String approverDeptName;
    /**
	 * 요청유형
	 */
    private String askType;
    /**
	 * 처리유형
	 */
    private String procType;
    /**
	 * 등록자ID
	 */
    private String registerId;
    /**
	 * 등록자명
	 */
    private String registerName;
    /**
	 * 등록자부서ID
	 */
    private String registerDeptId;
    /**
	 * 등록자부서명
	 */
    private String registerDeptName;
    /**
	 * 등록일자
	 */
    private String registDate = "9999-12-31 23:59:59";
    /**
	 * 삭제여부
	 */
    private String deleteYn;
    /**
	 * 삭제일자
	 */
    private String deleteDate = "9999-12-31 23:59:59";
    /**
	 * 비고
	 */
    private String remark;
    /**
	 * 문서상태
	 */
    private String docState;

    /** 파일정보 */
    private List<FileVO> fileInfos = new ArrayList<FileVO>();


    /**
	 * @return  the docId
	 */
    public String getDocId() {
	return docId;
    }


    /**
	 * @param docId  the docId to set
	 */
    public void setDocId(String docId) {
	this.docId = docId;
    }


    /**
	 * @return  the compId
	 */
    public String getCompId() {
	return compId;
    }


    /**
	 * @param compId  the compId to set
	 */
    public void setCompId(String compId) {
	this.compId = compId;
    }


    /**
	 * @return  the title
	 */
    public String getTitle() {
	return title;
    }


    /**
	 * @param title  the title to set
	 */
    public void setTitle(String title) {
	this.title = title;
    }


    /**
	 * @return  the auditNumber
	 */
    public int getAuditNumber() {
	return auditNumber;
    }


    /**
	 * @param auditNumber  the auditNumber to set
	 */
    public void setAuditNumber(int auditNumber) {
	this.auditNumber = auditNumber;
    }


    /**
	 * @return  the chargeDeptId
	 */
    public String getChargeDeptId() {
	return chargeDeptId;
    }


    /**
	 * @param chargeDeptId  the chargeDeptId to set
	 */
    public void setChargeDeptId(String chargeDeptId) {
	this.chargeDeptId = chargeDeptId;
    }


    /**
	 * @return  the chargeDeptName
	 */
    public String getChargeDeptName() {
	return chargeDeptName;
    }


    /**
	 * @param chargeDeptName  the chargeDeptName to set
	 */
    public void setChargeDeptName(String chargeDeptName) {
	this.chargeDeptName = chargeDeptName;
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


    /**
	 * @return  the receiveDate
	 */
    public String getReceiveDate() {
	return receiveDate;
    }


    /**
	 * @param receiveDate  the receiveDate to set
	 */
    public void setReceiveDate(String receiveDate) {
	this.receiveDate = receiveDate;
    }


    /**
	 * @return  the approverType
	 */
    public String getApproverType() {
	return approverType;
    }


    /**
	 * @param approverType  the approverType to set
	 */
    public void setApproverType(String approverType) {
	this.approverType = approverType;
    }


    /**
	 * @return  the approveType
	 */
    public String getApproveType() {
	return approveType;
    }


    /**
	 * @param approveType  the approveType to set
	 */
    public void setApproveType(String approveType) {
	this.approveType = approveType;
    }


    /**
	 * @return  the approverId
	 */
    public String getApproverId() {
	return approverId;
    }


    /**
	 * @param approverId  the approverId to set
	 */
    public void setApproverId(String approverId) {
	this.approverId = approverId;
    }


    /**
	 * @return  the approverName
	 */
    public String getApproverName() {
	return approverName;
    }


    /**
	 * @param approverName  the approverName to set
	 */
    public void setApproverName(String approverName) {
	this.approverName = approverName;
    }


    /**
	 * @return  the approverPos
	 */
    public String getApproverPos() {
	return approverPos;
    }


    /**
	 * @param approverPos  the approverPos to set
	 */
    public void setApproverPos(String approverPos) {
	this.approverPos = approverPos;
    }


    /**
	 * @return  the approverDeptId
	 */
    public String getApproverDeptId() {
	return approverDeptId;
    }


    /**
	 * @param approverDeptId  the approverDeptId to set
	 */
    public void setApproverDeptId(String approverDeptId) {
	this.approverDeptId = approverDeptId;
    }


    /**
	 * @return  the approverDeptName
	 */
    public String getApproverDeptName() {
	return approverDeptName;
    }


    /**
	 * @param approverDeptName  the approverDeptName to set
	 */
    public void setApproverDeptName(String approverDeptName) {
	this.approverDeptName = approverDeptName;
    }


    /**
	 * @return  the askType
	 */
    public String getAskType() {
	return askType;
    }


    /**
	 * @param askType  the askType to set
	 */
    public void setAskType(String askType) {
	this.askType = askType;
    }


    /**
	 * @return  the procType
	 */
    public String getProcType() {
	return procType;
    }


    /**
	 * @param procType  the procType to set
	 */
    public void setProcType(String procType) {
	this.procType = procType;
    }


    /**
	 * @return  the registerId
	 */
    public String getRegisterId() {
	return registerId;
    }


    /**
	 * @param registerId  the registerId to set
	 */
    public void setRegisterId(String registerId) {
	this.registerId = registerId;
    }


    /**
	 * @return  the registerName
	 */
    public String getRegisterName() {
	return registerName;
    }


    /**
	 * @param registerName  the registerName to set
	 */
    public void setRegisterName(String registerName) {
	this.registerName = registerName;
    }


    /**
	 * @return  the registerDeptId
	 */
    public String getRegisterDeptId() {
	return registerDeptId;
    }


    /**
	 * @param registerDeptId  the registerDeptId to set
	 */
    public void setRegisterDeptId(String registerDeptId) {
	this.registerDeptId = registerDeptId;
    }


    /**
	 * @return  the registerDeptName
	 */
    public String getRegisterDeptName() {
	return registerDeptName;
    }


    /**
	 * @param registerDeptName  the registerDeptName to set
	 */
    public void setRegisterDeptName(String registerDeptName) {
	this.registerDeptName = registerDeptName;
    }


    /**
	 * @return  the registDate
	 */
    public String getRegistDate() {
	return registDate;
    }


    /**
	 * @param registDate  the registDate to set
	 */
    public void setRegistDate(String registDate) {
	this.registDate = registDate;
    }


    /**
	 * @return  the deleteYn
	 */
    public String getDeleteYn() {
	return deleteYn;
    }


    /**
	 * @param deleteYn  the deleteYn to set
	 */
    public void setDeleteYn(String deleteYn) {
	this.deleteYn = deleteYn;
    }


    /**
	 * @return  the deleteDate
	 */
    public String getDeleteDate() {
	return deleteDate;
    }


    /**
	 * @param deleteDate  the deleteDate to set
	 */
    public void setDeleteDate(String deleteDate) {
	this.deleteDate = deleteDate;
    }


    /**
	 * @return  the remark
	 */
    public String getRemark() {
	return remark;
    }


    /**
	 * @param remark  the remark to set
	 */
    public void setRemark(String remark) {
	this.remark = remark;
    }


    /**
     * @return the fileInfo
     */
    public List<FileVO> getFileInfo() {
	return fileInfos;
    }


    /**
     * @param fileInfo
     *            the fileInfo to set
     */
    public void setFileInfo(List<FileVO> fileInfos) {
	this.fileInfos = fileInfos;
    }


    /**
	 * @param docState  the docState to set
	 */
    public void setDocState(String docState) {
	this.docState = docState;
    }


    /**
	 * @return  the docState
	 */
    public String getDocState() {
	return docState;
    }


    /**
	 * @return  the docNumber
	 */
    public String getDocNumber() {
        return docNumber;
    }


    /**
	 * @param docNumber  the docNumber to set
	 */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }


    /**
	 * @return  the actionDate
	 */
    public String getActionDate() {
        return actionDate;
    }


    /**
	 * @param actionDate  the actionDate to set
	 */
    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }


}
