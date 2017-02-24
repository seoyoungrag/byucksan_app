package com.sds.acube.app.approval.vo;

import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;

/**
 * Class Name  : AppDocVO.java <br> Description : 생산문서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AppDocVO
 */ 
public class AppDocVO {
	
	/**
	 * 문서ID
	 */ 
	private String docId;
	/**
	 * 회사ID
	 */ 
	private String compId;
	/**
	 * 문서제목
	 */ 
	private String title;
	/**
	 * 문서보안여부
	 */
	private String securityYn;
	/**
	 * 문서보안 비밀번호
	 */
	private String securityPass;
	/**
	 * 문서보안 시작일
	 */
	private String securityStartDate = "9999-12-31 23:59:59";
	/**
	 * 문서보안 종료일
	 */
	private String securityEndDate = "9999-12-31 23:59:59";
	/**
	 * 문서유형
	 */ 
	private String docType;
	/**
	 * 시행유형
	 */ 
	private String enfType;
	/**
	 * 기안자ID
	 */ 
	private String drafterId;
	/**
	 * 기안자명
	 */ 
	private String drafterName;
	/**
	 * 기안자직위
	 */ 
	private String drafterPos;
	/**
	 * 기안자부서ID
	 */ 
	private String drafterDeptId;
	/**
	 * 기안자부서명
	 */ 
	private String drafterDeptName;
	/**
	 * 기안일자
	 */ 
	private String draftDate = "9999-12-31 23:59:59";
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
	 * 결재일자
	 */ 
	private String approvalDate = "9999-12-31 23:59:59";
	/**
	 * 처리자ID
	 */ 
	private String processorId;
	/**
	 * 처리자명
	 */ 
	private String processorName;
	/**
	 * 처리자직위
	 */ 
	private String processorPos;
	/**
	 * 처리자부서ID
	 */ 
	private String processorDeptId;
	/**
	 * 처리자부서명
	 */ 
	private String processorDeptName;
	/**
	 * 최종수정일
	 */ 
	private String lastUpdateDate = "9999-12-31 23:59:59";
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록자명
	 */
	private String registerName;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	/**
	 * 문서상태
	 */ 
	private String docState;
	/**
	 * 문서부서분류
	 */ 
	private String deptCategory;
	/**
	 * 문서일련번호
	 */ 
	private int serialNumber = 0;
	/**
	 * 문서하위번호
	 */ 
	private int subserialNumber = 0;
	/**
	 * 열람범위
	 */ 
	private String readRange;
	/**
	 * 정보공개
	 */
	private String openLevel;
	/**
	 * 정보공개사유
	 */
	private String openReason;
	/**
	 * 보존년한
	 */ 
	private String conserveType;
	/**
	 * 삭제여부
	 */ 
	private String deleteYn;
	/**
	 * 임시여부
	 */ 
	private String tempYn;
	/**
	 * 문서출처
	 */ 
	private String docSource;
	/**
	 * 원문서ID
	 */ 
	private String originDocId;
	/**
	 * 원문서번호
	 */ 
	private String originDocNumber;
	/**
	 * 일괄기안여부
	 */ 
	private String batchDraftYn;
	/**
	 * 일괄기안일련번호
	 */ 
	private int batchDraftNumber = 1;
	/**
	 * 전자문서여부
	 */ 
	private String electronDocYn;
	/**
	 * 첨부개수
	 */ 
	private int attachCount = 0;
	/**
	 * 긴급여부
	 */ 
	private String urgencyYn;
	/**
	 * 공람게시여부
	 */ 
	private String publicPost;
	/**
	 * 감사열람여부
	 */ 
	private String auditReadYn;
	/**
	 * 감사열람사유
	 */ 
	private String auditReadReason;
	/**
	 * 감사여부
	 */ 
	private String auditYn;
	/**
	 * 편철ID
	 */ 
	private String bindingId;
	/**
	 * 편철명
	 */ 
	private String bindingName;
	/**
	 * 인계여부
	 */ 
	private String handoverYn;
	/**
	 * 자동발송여부
	 */ 
	private String autoSendYn;
	/**
	 * 업무시스템코드
	 */ 
	private String bizSystemCode;
	/**
	 * 업무시스템명
	 */ 
	private String bizSystemName;
	/**
	 * 업무유형코드
	 */ 
	private String bizTypeCode;
	/**
	 * 모바일결재여부
	 */ 
	private String mobileYn;
	/**
	 * 문서이관여부
	 */ 
	private String transferYn;
	/**
	 * 이중결재여부
	 */ 
	private String doubleYn;
	/**
	 * 본문수정가능여부
	 */ 
	private String editbodyYn;
	/**
	 * 첨부수정가능여부
	 */ 
	private String editfileYn;
	/**
	 * 주관부서ID
	 */ 
	private String execDeptId;
	/**
	 * 주관부서명
	 */ 
	private String execDeptName;
	/**
	 * 협조라인유형
	 */ 
	private String assistantLineType;
	/**
	 * 감사라인유형
	 */ 
	private String auditLineType;
	/**
	 * 요약
	 */ 
	private String summary;
	/**
	 * 발송기관 부서ID
	 */
	private String senderDeptId;
	/**
	 * 발송기관 부서명
	 */
	private String senderDeptName;
	/**
	 * 발송기관 회사ID
	 */
	private String senderCompId;
	/**
	 * 발송기관 회사명
	 */
	private String senderCompName;
	/**
	 * 발송자ID
	 */
	private String senderId;
	/**
	 * 발송자명
	 */
	private String senderName;
	/**
	 * 반려여부
	 */
	private String returnDocYn;
	/**
	 * 수신부서명
	 */
	private String recvDeptNames;
	/**
	 * 수신부서 갯수
	 */
	private int recvDeptCnt = 0;
	/**
	 * 날인번호
	 */
	private int sealNumber;
	/**
	 * 날인일자
	 */
	private String sealDate = "9999-12-31 23:59:59";
	/**
	 * 요청부서ID
	 */
	private String requesterDeptId;
	/**
	 * 요청부서명
	 */
	private String requesterDeptName;
	/**
	 * 요청자ID
	 */
	private String requesterId;
	/**
	 * 요청자명
	 */
	private String requesterName;
	/**
	 * 공람처리일자
	 */
	private String pubReadDate = "9999-12-31 23:59:59";
	/**
	 * 등록취소여부
	 */
	private String unregistYn;
	
	/**
	 */
	private String categoryName;
	
	/**
	 * 발신명의
	 */
	private String senderTitle;
	
	/**
	 * 요청유형
	 */
	private String procType;
	/**
	 * 감사구분
	 */
	private String auditDivision;
	
	/**
	 * 문서상태 완료 여부
	 */
	private String docCompleteYn;
	
	/**
	 * 분류번호
	 */
	private String classNumber;
	/**
	 * 분류번호명
	 */
	private String docnumName;
	
	/**
	 * 원회사 ID
	 */
	private String originCompId;
	
	/**
	 * 생산문서부가정보
	 */
	private AppOptionVO appOptionVO = null;
	/**
	 * 발송정보
	 */
	private SendInfoVO sendInfoVO = null;
	/** 보고경로 */
	private List<AppLineVO> appLines = new ArrayList<AppLineVO>();
	/** 파일정보 */
	private List<FileVO> fileInfos = new ArrayList<FileVO>();
	/** 수신자정보 */
	private List<AppRecvVO> receiverInfos = new ArrayList<AppRecvVO>();
	/** 관련문서 */
	private List<RelatedDocVO> relatedDocs = new ArrayList<RelatedDocVO>();
	/** 관련규정 */
	private List<RelatedRuleVO> relatedRules = new ArrayList<RelatedRuleVO>();
	/** 거래처 */
	private List<CustomerVO> customers = new ArrayList<CustomerVO>();

	/** 소유부서정보 */
	private List<OwnDeptVO> ownDepts = new ArrayList<OwnDeptVO>();
	/** 공람자 */
	private List<PubReaderVO> pubReaders = new ArrayList<PubReaderVO>();
	
	/**
	 * 비전자 문서 정보
	 */
	private NonElectronVO nonElectronVO = new NonElectronVO();
	
	/**
	 * 재기안 원본문서ID
	 */
	private String redraftDocId;

	/**
	 * 반려문서대장등록여부
	 */
	private String redraftRegYn;

	/**
	 * 날인유형
	 */
	private String sealType;
	
	
	/**
	 * 수신자순서 20120613 add
	 */
	private int receiverOrder;
	
	
	/**
	 * 수신일자 20120613 add
	 */
	private String receiveDate = "9999-12-31 23:59:59";	
	
	/**
	 * 편철 다국어 ID
	 */
	private String bindingResourceId;
	
	/**
	 * 대결자 이름
	 */
	private String representativeName;
	
	/**
	 * 대결자 아이디
	 */
	private String representativeId;
	
	/**
	 * 완결자명
	 */
	private String enderName;
	
	
	/**
	 * 완결일자
	 */ 
	private String endDate = "9999-12-31 23:59:59";
	
	/**
	 * 반려자 아이디
	 */
	private String rejectorId;
	

	/**
	 * 조회 여부
	 */
	private String readDate;
	
	
	/**
	 * @return the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	/**
	 * @return the rejectorId
	 */
	public String getRejectorId() {
		return rejectorId;
	}
	/**
	 * @param rejectorId the rejectorId to set
	 */
	public void setRejectorId(String rejectorId) {
		this.rejectorId = rejectorId;
	}
	/**
	 * 반려자 이름
	 */
	private String rejectorName;
	
	/**
	 * 반려일자
	 */
	private String rejectDate = "9999-12-31 23:59:59";
	
	/**
	 * 삭제일
	 */
	private String deleteDate = "9999-12-31 23:59:59";
	
	
	/**
	 * @return the deleteDate
	 */
	public String getDeleteDate() {
		return deleteDate;
	}
	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	/**
	 * @return the rejectorName
	 */
	public String getRejectorName() {
		return rejectorName;
	}
	/**
	 * @param rejectorName the rejectorName to set
	 */
	public void setRejectorName(String rejectorName) {
		this.rejectorName = rejectorName;
	}
	/**
	 * @return the rejectDate
	 */
	public String getRejectDate() {
		return rejectDate;
	}
	/**
	 * @param rejectDate the rejectDate to set
	 */
	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	/**
	 * @return the enderName
	 */
	public String getEnderName() {
		return enderName;
	}
	/**
	 * @param enderName the enderName to set
	 */
	public void setEnderName(String enderName) {
		this.enderName = enderName;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the representativeId
	 */
	public String getRepresentativeId() {
		return representativeId;
	}
	/**
	 * @param representativeId the representativeId to set
	 */
	public void setRepresentativeId(String representativeId) {
		this.representativeId = representativeId;
	}
	/**
	 * @return the representativeName
	 */
	public String getRepresentativeName() {
		return representativeName;
	}
	/**
	 * @param representativeName the representativeName to set
	 */
	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}
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
	 * @return  the enfType
	 */
	public String getEnfType() {
		return enfType;
	}
	/**
	 * @param enfType  the enfType to set
	 */
	public void setEnfType(String enfType) {
		this.enfType = enfType;
	}
	/**
	 * @return  the drafterId
	 */
	public String getDrafterId() {
		return drafterId;
	}
	/**
	 * @param drafterId  the drafterId to set
	 */
	public void setDrafterId(String drafterId) {
		this.drafterId = drafterId;
	}
	/**
	 * @return  the drafterName
	 */
	public String getDrafterName() {
		return drafterName;
	}
	/**
	 * @param drafterName  the drafterName to set
	 */
	public void setDrafterName(String drafterName) {
		this.drafterName = drafterName;
	}
	/**
	 * @return  the drafterPos
	 */
	public String getDrafterPos() {
		return drafterPos;
	}
	/**
	 * @param drafterPos  the drafterPos to set
	 */
	public void setDrafterPos(String drafterPos) {
		this.drafterPos = drafterPos;
	}
	/**
	 * @return  the drafterDeptId
	 */
	public String getDrafterDeptId() {
		return drafterDeptId;
	}
	/**
	 * @param drafterDeptId  the drafterDeptId to set
	 */
	public void setDrafterDeptId(String drafterDeptId) {
		this.drafterDeptId = drafterDeptId;
	}
	/**
	 * @return  the drafterDeptName
	 */
	public String getDrafterDeptName() {
		return drafterDeptName;
	}
	/**
	 * @param drafterDeptName  the drafterDeptName to set
	 */
	public void setDrafterDeptName(String drafterDeptName) {
		this.drafterDeptName = drafterDeptName;
	}
	/**
	 * @return  the draftDate
	 */
	public String getDraftDate() {
		return draftDate;
	}
	/**
	 * @param draftDate  the draftDate to set
	 */
	public void setDraftDate(String draftDate) {
		this.draftDate = draftDate;
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
	 * @return  the approvalDate
	 */
	public String getApprovalDate() {
		return approvalDate;
	}
	/**
	 * @param approvalDate  the approvalDate to set
	 */
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	/**
	 * @return  the processorId
	 */
	public String getProcessorId() {
		return processorId;
	}
	/**
	 * @param processorId  the processorId to set
	 */
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}
	/**
	 * @return  the processorName
	 */
	public String getProcessorName() {
		return processorName;
	}
	/**
	 * @param processorName  the processorName to set
	 */
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	/**
	 * @return  the processorPos
	 */
	public String getProcessorPos() {
		return processorPos;
	}
	/**
	 * @param processorPos  the processorPos to set
	 */
	public void setProcessorPos(String processorPos) {
		this.processorPos = processorPos;
	}
	/**
	 * @return  the processorDeptId
	 */
	public String getProcessorDeptId() {
		return processorDeptId;
	}
	/**
	 * @param processorDeptId  the processorDeptId to set
	 */
	public void setProcessorDeptId(String processorDeptId) {
		this.processorDeptId = processorDeptId;
	}
	/**
	 * @return  the processorDeptName
	 */
	public String getProcessorDeptName() {
		return processorDeptName;
	}
	/**
	 * @param processorDeptName  the processorDeptName to set
	 */
	public void setProcessorDeptName(String processorDeptName) {
		this.processorDeptName = processorDeptName;
	}
	/**
	 * @return  the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate  the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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
	 * @return  the docState
	 */
	public String getDocState() {
		return docState;
	}
	/**
	 * @param docState  the docState to set
	 */
	public void setDocState(String docState) {
		this.docState = docState;
	}
	/**
	 * @return  the deptCategory
	 */
	public String getDeptCategory() {
		return deptCategory;
	}
	/**
	 * @param deptCategory  the deptCategory to set
	 */
	public void setDeptCategory(String deptCategory) {
		this.deptCategory = deptCategory;
	}
	/**
	 * @return  the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber  the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return  the subserialNumber
	 */
	public int getSubserialNumber() {
		return subserialNumber;
	}
	/**
	 * @param subserialNumber  the subserialNumber to set
	 */
	public void setSubserialNumber(int subserialNumber) {
		this.subserialNumber = subserialNumber;
	}
	/**
	 * @return  the readRange
	 */
	public String getReadRange() {
		return readRange;
	}
	/**
	 * @param readRange  the readRange to set
	 */
	public void setReadRange(String readRange) {
		this.readRange = readRange;
	}
	/**
	 * @return  the openLevel
	 */
        public String getOpenLevel() {
            return openLevel;
        }
	/**
	 * @param openLevel  the openLevel to set
	 */
        public void setOpenLevel(String openLevel) {
            this.openLevel = openLevel;
        }
	/**
	 * @return  the openReason
	 */
        public String getOpenReason() {
            return openReason;
        }
	/**
	 * @param openReason  the openReason to set
	 */
        public void setOpenReason(String openReason) {
            this.openReason = openReason;
        }
	/**
	 * @return  the conserveType
	 */
	public String getConserveType() {
		return conserveType;
	}
	/**
	 * @param conserveType  the conserveType to set
	 */
	public void setConserveType(String conserveType) {
		this.conserveType = conserveType;
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
	 * @return  the tempYn
	 */
	public String getTempYn() {
		return tempYn;
	}
	/**
	 * @param tempYn  the tempYn to set
	 */
	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}
	/**
	 * @return  the docSource
	 */
	public String getDocSource() {
		return docSource;
	}
	/**
	 * @param docSource  the docSource to set
	 */
	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}
	/**
	 * @return  the originDocId
	 */
	public String getOriginDocId() {
		return originDocId;
	}
	/**
	 * @param originDocId  the originDocId to set
	 */
	public void setOriginDocId(String originDocId) {
		this.originDocId = originDocId;
	}
	/**
	 * @return  the originDocNumber
	 */
	public String getOriginDocNumber() {
		return originDocNumber;
	}
	/**
	 * @param originDocNumber  the originDocNumber to set
	 */
	public void setOriginDocNumber(String originDocNumber) {
		this.originDocNumber = originDocNumber;
	}
	/**
	 * @return  the batchDraftYn
	 */
	public String getBatchDraftYn() {
		return batchDraftYn;
	}
	/**
	 * @param batchDraftYn  the batchDraftYn to set
	 */
	public void setBatchDraftYn(String batchDraftYn) {
		this.batchDraftYn = batchDraftYn;
	}
	/**
	 * @return  the batchDraftNumber
	 */
	public int getBatchDraftNumber() {
		return batchDraftNumber;
	}
	/**
	 * @param batchDraftNumber  the batchDraftNumber to set
	 */
	public void setBatchDraftNumber(int batchDraftNumber) {
		this.batchDraftNumber = batchDraftNumber;
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
	 * @return  the attachCount
	 */
	public int getAttachCount() {
		return attachCount;
	}
	/**
	 * @param attachCount  the attachCount to set
	 */
	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
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
	 * @return  the publicPost
	 */
	public String getPublicPost() {
		return publicPost;
	}
	/**
	 * @param publicPost  the publicPost to set
	 */
	public void setPublicPost(String publicPost) {
		this.publicPost = publicPost;
	}
	/**
	 * @return  the auditYn
	 */
	public String getAuditYn() {
		return auditYn;
	}
	/**
	 * @param auditYn  the auditYn to set
	 */
	public void setAuditYn(String auditYn) {
		this.auditYn = auditYn;
	}
	/**
	 * @return  the bindingId
	 */
	public String getBindingId() {
		return bindingId;
	}
	/**
	 * @param bindingId  the bindingId to set
	 */
	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}
	/**
	 * @return  the bindingName
	 */
	public String getBindingName() {
		return bindingName;
	}
	/**
	 * @param bindingName  the bindingName to set
	 */
	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}
	/**
	 * @return  the handoverYn
	 */
	public String getHandoverYn() {
		return handoverYn;
	}
	/**
	 * @param handoverYn  the handoverYn to set
	 */
	public void setHandoverYn(String handoverYn) {
		this.handoverYn = handoverYn;
	}
	/**
	 * @return  the autoSendYn
	 */
	public String getAutoSendYn() {
		return autoSendYn;
	}
	/**
	 * @param autoSendYn  the autoSendYn to set
	 */
	public void setAutoSendYn(String autoSendYn) {
		this.autoSendYn = autoSendYn;
	}
	/**
	 * @return  the bizSystemCode
	 */
	public String getBizSystemCode() {
		return bizSystemCode;
	}
	/**
	 * @param bizSystemCode  the bizSystemCode to set
	 */
	public void setBizSystemCode(String bizSystemCode) {
		this.bizSystemCode = bizSystemCode;
	}	
	/**
	 * @return  the bizSystemName
	 */
        public String getBizSystemName() {
            return bizSystemName;
        }
	/**
	 * @param bizSystemName  the bizSystemName to set
	 */
        public void setBizSystemName(String bizSystemName) {
            this.bizSystemName = bizSystemName;
        }
	/**
	 * @return  the bizTypeCode
	 */
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	/**
	 * @param bizTypeCode  the bizTypeCode to set
	 */
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	/**
	 * @return  the mobileYn
	 */
	public String getMobileYn() {
		return mobileYn;
	}
	/**
	 * @param mobileYn  the mobileYn to set
	 */
	public void setMobileYn(String mobileYn) {
		this.mobileYn = mobileYn;
	}
	/**
	 * @return  the transferYn
	 */
	public String getTransferYn() {
		return transferYn;
	}
	/**
	 * @param transferYn  the transferYn to set
	 */
	public void setTransferYn(String transferYn) {
		this.transferYn = transferYn;
	}
	/**
	 * @return  the doubleYn
	 */
	public String getDoubleYn() {
		return doubleYn;
	}
	/**
	 * @param doubleYn  the doubleYn to set
	 */
	public void setDoubleYn(String doubleYn) {
		this.doubleYn = doubleYn;
	}
	/**
	 * @return  the execDeptId
	 */
	public String getExecDeptId() {
		return execDeptId;
	}
	/**
	 * @param execDeptId  the execDeptId to set
	 */
	public void setExecDeptId(String execDeptId) {
		this.execDeptId = execDeptId;
	}
	/**
	 * @return  the execDeptName
	 */
	public String getExecDeptName() {
		return execDeptName;
	}
	/**
	 * @param execDeptName  the execDeptName to set
	 */
	public void setExecDeptName(String execDeptName) {
		this.execDeptName = execDeptName;
	}
	/**
	 * @return  the summary
	 */
	public String getSummary() {
		return (summary == null) ? "" : summary;
	}
	/**
	 * @param summary  the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = (summary == null) ? "" : summary;
	}	
	/**
	 * @return  the senderDeptId
	 */
        public String getSenderDeptId() {
            return senderDeptId;
        }
	/**
	 * @param senderDeptId  the senderDeptId to set
	 */
        public void setSenderDeptId(String senderDeptId) {
            this.senderDeptId = senderDeptId;
        }
	/**
	 * @return  the senderDeptName
	 */
        public String getSenderDeptName() {
            return senderDeptName;
        }
	/**
	 * @param senderDeptName  the senderDeptName to set
	 */
        public void setSenderDeptName(String senderDeptName) {
            this.senderDeptName = senderDeptName;
        }
	/**
	 * @return  the senderCompId
	 */
        public String getSenderCompId() {
            return senderCompId;
        }
	/**
	 * @param senderCompId  the senderCompId to set
	 */
        public void setSenderCompId(String senderCompId) {
            this.senderCompId = senderCompId;
        }
	/**
	 * @return  the senderCompName
	 */
        public String getSenderCompName() {
            return senderCompName;
        }
	/**
	 * @param senderCompName  the senderCompName to set
	 */
        public void setSenderCompName(String senderCompName) {
            this.senderCompName = senderCompName;
        }        
        /**
		 * @return  the senderId
		 */
        public String getSenderId() {
            return senderId;
        }
	/**
	 * @param senderId  the senderId to set
	 */
        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }
	/**
	 * @return  the senderName
	 */
        public String getSenderName() {
            return senderName;
        }
	/**
	 * @param senderName  the senderName to set
	 */
        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }
	/**
	 * @return  the returnDocYn
	 */
        public String getReturnDocYn() {
            return returnDocYn;
        }        
	/**
	 * @param returnDocYn  the returnDocYn to set
	 */
        public void setReturnDocYn(String returnDocYn) {
            this.returnDocYn = returnDocYn;
        }
 	/**
	 * @return  the recvDeptNames
	 */
        public String getRecvDeptNames() {
            return recvDeptNames;
        }
	/**
	 * @param recvDeptNames  the recvDeptNames to set
	 */
        public void setRecvDeptNames(String recvDeptNames) {
            this.recvDeptNames = recvDeptNames;
        }
	/**
	 * @return  the recvDeptCnt
	 */
        public int getRecvDeptCnt() {
            return recvDeptCnt;
        }
	/**
	 * @param recvDeptCnt  the recvDeptCnt to set
	 */
        public void setRecvDeptCnt(int recvDeptCnt) {
            this.recvDeptCnt = recvDeptCnt;
        }
        
	
	/**
	 * @return  the sealNumber
	 */
        public int getSealNumber() {
            return sealNumber;
        }
	/**
	 * @param sealNumber  the sealNumber to set
	 */
        public void setSealNumber(int sealNumber) {
            this.sealNumber = sealNumber;
        }
	/**
	 * @return  the sealDate
	 */
        public String getSealDate() {
            return sealDate;
        }
	/**
	 * @param sealDate  the sealDate to set
	 */
        public void setSealDate(String sealDate) {
            this.sealDate = sealDate;
        }
        
	/**
	 * @return  the requesterDeptId
	 */
        public String getRequesterDeptId() {
            return requesterDeptId;
        }
	/**
	 * @param requesterDeptId  the requesterDeptId to set
	 */
        public void setRequesterDeptId(String requesterDeptId) {
            this.requesterDeptId = requesterDeptId;
        }
	/**
	 * @return  the requesterDeptName
	 */
        public String getRequesterDeptName() {
            return requesterDeptName;
        }
	/**
	 * @param requesterDeptName  the requesterDeptName to set
	 */
        public void setRequesterDeptName(String requesterDeptName) {
            this.requesterDeptName = requesterDeptName;
        }
	/**
	 * @return  the requesterId
	 */
        public String getRequesterId() {
            return requesterId;
        }
	/**
	 * @param requesterId  the requesterId to set
	 */
        public void setRequesterId(String requesterId) {
            this.requesterId = requesterId;
        }
	/**
	 * @return  the requesterName
	 */
        public String getRequesterName() {
            return requesterName;
        }
	/**
	 * @param requesterName  the requesterName to set
	 */
        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }
        
        
	/**
	 * @return  the pubReadDate
	 */
        public String getPubReadDate() {
            return pubReadDate;
        }
	/**
	 * @param pubReadDate  the pubReadDate to set
	 */
        public void setPubReadDate(String pubReadDate) {
            this.pubReadDate = pubReadDate;
        }
	/**
	 * @return  the categoryName
	 */
        public String getCategoryName() {
            return categoryName;
        }
	/**
	 * @param categoryName  the categoryName to set
	 */
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
	/**
	 * @return  the appOptionVO
	 */
	public AppOptionVO getAppOptionVO() {
		return appOptionVO;
	}
	/**
	 * @param appOptionVO  the appOptionVO to set
	 */
	public void setAppOptionVO(AppOptionVO appOptionVO) {
	    if (appOptionVO != null)
		this.appOptionVO = appOptionVO;
	}
	/**
	 * @return  the sendInfoVO
	 */
	public SendInfoVO getSendInfoVO() {
		return sendInfoVO;
	}
	/**
	 * @param sendInfoVO  the sendInfoVO to set
	 */
	public void setSendInfoVO(SendInfoVO sendInfoVO) {
		this.sendInfoVO = sendInfoVO;
	}
	/**
	 * @return the appLine
	 */
	public List<AppLineVO> getAppLine() {
		return appLines;
	}
	/**
	 * @param appLine the appLine to set
	 */
	public void setAppLine(List<AppLineVO> appLines) {
		this.appLines = appLines;
	}
	/**
	 * @return the fileInfo
	 */
	public List<FileVO> getFileInfo() {
		return fileInfos;
	}
	/**
	 * @param fileInfo the fileInfo to set
	 */
	public void setFileInfo(List<FileVO> fileInfos) {
		this.fileInfos = fileInfos;
	}
	/**
	 * @return the receiverInfo
	 */
	public List<AppRecvVO> getReceiverInfo() {
		return receiverInfos;
	}
	/**
	 * @param receiverInfo the receiverInfo to set
	 */
	public void setReceiverInfo(List<AppRecvVO> receiverInfos) {
		this.receiverInfos = receiverInfos;
	}
	/**
	 * @return the relatedDoc
	 */
	public List<RelatedDocVO> getRelatedDoc() {
		return relatedDocs;
	}
	/**
	 * @param relatedDoc the relatedDoc to set
	 */
	public void setRelatedDoc(List<RelatedDocVO> relatedDocs) {
		this.relatedDocs = relatedDocs;
	}
	/**
	 * @return the relatedRule
	 */
	public List<RelatedRuleVO> getRelatedRule() {
		return relatedRules;
	}
	/**
	 * @param relatedRule the relatedRule to set
	 */
	public void setRelatedRule(List<RelatedRuleVO> relatedRules) {
		this.relatedRules = relatedRules;
	}
	/**
	 * @return the customer
	 */
	public List<CustomerVO> getCustomer() {
		return customers;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(List<CustomerVO> customers) {
		this.customers = customers;
	}
	/**
         * @return the ownDeptVOs
         */
        public List<OwnDeptVO> getOwnDept() {
            return ownDepts;
        }
	/**
         * @param ownDepts the ownDepts to set
         */
        public void setOwnDept(List<OwnDeptVO> ownDepts) {
            this.ownDepts = ownDepts;
        }
	/**
         * @return the pubReaders
         */
        public List<PubReaderVO> getPubReader() {
            return pubReaders;
        }
	/**
         * @param pubReaders the pubReaders to set
         */
        public void setPubReader(List<PubReaderVO> pubReaders) {
            this.pubReaders = pubReaders;
        }
	/**
	 * @return  the editbodyYn
	 */
        public String getEditbodyYn() {
            return editbodyYn;
        }
	/**
	 * @param editbodyYn  the editbodyYn to set
	 */
        public void setEditbodyYn(String editbodyYn) {
            this.editbodyYn = editbodyYn;
        }
	/**
	 * @return  the editfileYn
	 */
        public String getEditfileYn() {
            return editfileYn;
        }
	/**
	 * @param editfileYn  the editfileYn to set
	 */
        public void setEditfileYn(String editfileYn) {
            this.editfileYn = editfileYn;
        }
        
        /**
		 * <pre>  설명 </pre>
		 * @return
		 * @see   
		 */
        public NonElectronVO getNonElectronVO(){
            
            return nonElectronVO;
        }
        
        /**
		 * <pre>  설명 </pre>
		 * @param nonElectronVO
		 * @see   
		 */
        public void setNonElectronVO(NonElectronVO nonElectronVO){
            this.nonElectronVO = nonElectronVO;
        }
	/**
	 * @return  the auditReadYn
	 */
        public String getAuditReadYn() {
            return auditReadYn;
        }
	/**
	 * @param auditReadYn  the auditReadYn to set
	 */
        public void setAuditReadYn(String auditReadYn) {
            this.auditReadYn = auditReadYn;
        }
	/**
	 * @return  the auditReadReason
	 */
        public String getAuditReadReason() {
            return auditReadReason;
        }
	/**
	 * @param auditReadReason  the auditReadReason to set
	 */
        public void setAuditReadReason(String auditReadReason) {
            this.auditReadReason = auditReadReason;
        }
	/**
	 * @return  the senderTitle
	 */
        public String getSenderTitle() {
            return senderTitle;
        }
	/**
	 * @param senderTitle  the senderTitle to set
	 */
        public void setSenderTitle(String senderTitle) {
            this.senderTitle = senderTitle;
        }
	/**
	 * @param procType  the procType to set
	 */
        public void setProcType(String procType) {
	    this.procType = procType;
        }
	/**
	 * @return  the procType
	 */
        public String getProcType() {
	    return procType;
        }
	/**
	 * @param redraftDocId  the redraftDocId to set
	 */
        public void setRedraftDocId(String redraftDocId) {
	    this.redraftDocId = redraftDocId;
        }
	/**
	 * @return  the redraftDocId
	 */
        public String getRedraftDocId() {
	    return redraftDocId;
        }
	/**
	 * @param auditDivision  the auditDivision to set
	 */
        public void setAuditDivision(String auditDivision) {
	    this.auditDivision = auditDivision;
        }
	/**
	 * @return  the auditDivision
	 */
        public String getAuditDivision() {
	    return auditDivision;
        }
	/**
	 * @param unregistYn  the unregistYn to set
	 */
        public void setUnregistYn(String unregistYn) {
	    this.unregistYn = unregistYn;
        }
	/**
	 * @return  the unregistYn
	 */
        public String getUnregistYn() {
	    return unregistYn;
        }
	/**
	 * @param docCompleteYn  the docCompleteYn to set
	 */
        public void setDocCompleteYn(String docCompleteYn) {
	    this.docCompleteYn = docCompleteYn;
        }
	/**
	 * @return  the docCompleteYn
	 */
        public String getDocCompleteYn() {
	    return docCompleteYn;
        }
	/**
	 * @param classNumber  the classNumber to set
	 */
        public void setClassNumber(String classNumber) {
	    this.classNumber = classNumber;
        }
	/**
	 * @return  the classNumber
	 */
        public String getClassNumber() {
	    return classNumber;
        }
	/**
	 * @param docnumName  the docnumName to set
	 */
        public void setDocnumName(String docnumName) {
	    this.docnumName = docnumName;
        }
	/**
	 * @return  the docnumName
	 */
        public String getDocnumName() {
	    return docnumName;
        }
	/**
	 * @param assistantLineType  the assistantLineType to set
	 */
        public void setAssistantLineType(String assistantLineType) {
	    this.assistantLineType = assistantLineType;
        }
	/**
	 * @return  the assistantLineType
	 */
        public String getAssistantLineType() {
	    return assistantLineType;
        }
	/**
	 * @param auditLineType  the auditLineType to set
	 */
        public void setAuditLineType(String auditLineType) {
	    this.auditLineType = auditLineType;
        }
	/**
	 * @return  the auditLineType
	 */
        public String getAuditLineType() {
	    return auditLineType;
        }
	/**
	 * @param originCompId  the originCompId to set
	 */
        public void setOriginCompId(String originCompId) {
	    this.originCompId = originCompId;
        }
	/**
	 * @return  the originCompId
	 */
        public String getOriginCompId() {
	    return originCompId;
        }
	/**
	 * @return  the securityYn
	 */
        public String getSecurityYn() {
            return securityYn;
        }
	/**
	 * @param securityYn  the securityYn to set
	 */
        public void setSecurityYn(String securityYn) {
            this.securityYn = securityYn;
        }
	/**
	 * @return  the securityPass
	 */
        public String getSecurityPass() {
            return securityPass;
        }
	/**
	 * @param securityPass  the securityPass to set
	 */
        public void setSecurityPass(String securityPass) {
            this.securityPass = securityPass;
        }
	/**
	 * @return  the securityStartDate
	 */
        public String getSecurityStartDate() {
            return securityStartDate;
        }
	/**
	 * @param securityStartDate  the securityStartDate to set
	 */
        public void setSecurityStartDate(String securityStartDate) {
            this.securityStartDate = securityStartDate;
        }
	/**
	 * @return  the securityEndDate
	 */
        public String getSecurityEndDate() {
            return securityEndDate;
        }
	/**
	 * @param securityEndDate  the securityEndDate to set
	 */
        public void setSecurityEndDate(String securityEndDate) {
            this.securityEndDate = securityEndDate;
        }
	/**
	 * @return  the redraftRegYn
	 */
	public String getRedraftRegYn() {
		return redraftRegYn;
	}
	/**
	 * @param redraftRegYn  the redraftRegYn to set
	 */
	public void setRedraftRegYn(String redraftRegYn) {
		this.redraftRegYn = redraftRegYn;
	}
	/**
	 * @return  the sealType
	 */
	public String getSealType() {
		return sealType;
	}
	/**
	 * @param sealType  the sealType to set
	 */
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
	
	
	/**
         * @return the receiverOrder
         */
        public int getReceiverOrder() {
            return receiverOrder;
        }
	/**
         * @param receiverOrder the receiverOrder to set
         */
        public void setReceiverOrder(int receiverOrder) {
            this.receiverOrder = receiverOrder;
        }
	/**
         * @return the receiveDate
         */
        public String getReceiveDate() {
            return receiveDate;
        }
	/**
         * @param receiveDate the receiveDate to set
         */
        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }
	/**
	 * @return the appLines
	 */
	public List<AppLineVO> getAppLines() {
		return appLines;
	}
	/**
	 * @param appLines the appLines to set
	 */
	public void setAppLines(List<AppLineVO> appLines) {
		this.appLines = appLines;
	}
	/**
	 * @return the fileInfos
	 */
	public List<FileVO> getFileInfos() {
		return fileInfos;
	}
	/**
	 * @param fileInfos the fileInfos to set
	 */
	public void setFileInfos(List<FileVO> fileInfos) {
		this.fileInfos = fileInfos;
	}
	/**
	 * @return the receiverInfos
	 */
	public List<AppRecvVO> getReceiverInfos() {
		return receiverInfos;
	}
	/**
	 * @param receiverInfos the receiverInfos to set
	 */
	public void setReceiverInfos(List<AppRecvVO> receiverInfos) {
		this.receiverInfos = receiverInfos;
	}
	/**
	 * @return the relatedDocs
	 */
	public List<RelatedDocVO> getRelatedDocs() {
		return relatedDocs;
	}
	/**
	 * @param relatedDocs the relatedDocs to set
	 */
	public void setRelatedDocs(List<RelatedDocVO> relatedDocs) {
		this.relatedDocs = relatedDocs;
	}
	/**
	 * @return the relatedRules
	 */
	public List<RelatedRuleVO> getRelatedRules() {
		return relatedRules;
	}
	/**
	 * @param relatedRules the relatedRules to set
	 */
	public void setRelatedRules(List<RelatedRuleVO> relatedRules) {
		this.relatedRules = relatedRules;
	}
	/**
	 * @return the customers
	 */
	public List<CustomerVO> getCustomers() {
		return customers;
	}
	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<CustomerVO> customers) {
		this.customers = customers;
	}
	/**
	 * @return the ownDepts
	 */
	public List<OwnDeptVO> getOwnDepts() {
		return ownDepts;
	}
	/**
	 * @param ownDepts the ownDepts to set
	 */
	public void setOwnDepts(List<OwnDeptVO> ownDepts) {
		this.ownDepts = ownDepts;
	}
	/**
	 * @return the pubReaders
	 */
	public List<PubReaderVO> getPubReaders() {
		return pubReaders;
	}
	/**
	 * @param pubReaders the pubReaders to set
	 */
	public void setPubReaders(List<PubReaderVO> pubReaders) {
		this.pubReaders = pubReaders;
	}
	/**
	 * @return the bindingResourceId
	 */
	public String getBindingResourceId() {
		return bindingResourceId;
	}
	/**
	 * @param bindingResourceId the bindingResourceId to set
	 */
	public void setBindingResourceId(String bindingResourceId) {
		this.bindingResourceId = bindingResourceId;
	}


}
