package com.sds.acube.app.enforce.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;


/**
 * Class Name : EnfDocVO.java <br> Description : 접수문서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  허주
 * @since  2011. 3. 18
 * @version  1.0
 * @see  EnfDocVO
 */
public class EnfDocVO {

    /**
	 * 문서ID
	 */
    private String docId;
    /**
	 * 회사ID
	 */
    private String compId;
    /**
	 * 원문서ID
	 */
    private String originDocId;
    /**
	 * 원문서 회사ID
	 */
    private String originCompId;
    /**
	 * 문서유형
	 */
    private String docType;
    /**
	 * 시행유형
	 */
    private String enfType;
    /**
	 * 문서제목
	 */
    private String title;
    /**
	 * 문서상태
	 */
    private String docState;
    /**
	 * 생산문서번호
	 */
    private String docNumber;
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
	 * 접수자ID
	 */
    private String accepterId;
    /**
	 * 접수자명
	 */
    private String accepterName;
    /**
	 * 접수자직위
	 */
    private String accepterPos;
    /**
	 * 접수부서ID
	 */
    private String acceptDeptId;
    /**
	 * 접수부서명
	 */
    private String acceptDeptName;
    /**
	 * 접수일자
	 */
    private String acceptDate = "9999-12-31 23:59:59";
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
	 * 수신일자
	 */
    private String receiveDate = "9999-12-31 23:59:59";
    /**
	 * 배부여부
	 */
    private String distributeYn;
    /**
	 * 배부번호
	 */
    private int distributeNumber = 0;
    /**
	 * 배부자ID
	 */
    private String distributorId;
    /**
	 * 배부자명
	 */
    private String distributorName;
    /**
	 * 배부자직위
	 */
    private String distributorPos;
    /**
	 * 배부부서ID
	 */
    private String distributorDeptId;
    /**
	 * 배부부서명
	 */
    private String distributorDeptName;
    /**
	 * 배부일자
	 */
    private String distributeDate = "9999-12-31 23:59:59";
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
	 * 전자문서여부
	 */
    private String electronDocYn;
    /**
	 * 첨부개수
	 */
    private int attachCount = 0;
    /**
	 * 삭제여부
	 */
    private String deleteYn;
    /**
	 * 긴급여부
	 */
    private String urgencyYn;
    /**
	 * 공람게시여부
	 */
    private String publicPost;
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
	 * 모바일결재여부
	 */
    private String mobileYn;
    /**
	 * 문서이관여부
	 */
    private String transferYn;
    /**
	 * 협조라인유형
	 */ 
    private String assistantLineType;
    /**
	 * 감사라인유형
	 */ 
    private String auditLineType;
    /**
	 * 발송자ID
	 */
    private String senderId;
    /**
	 * 발송자명
	 */
    private String senderName;
    /**
	 * 발송일자
	 */
    private String sendDate = "9999-12-31 23:59:59";
    /**
	 * 발송의견
	 */
    private String sendOpinion;
    /**
	 * 발송 회사ID
	 */
    private String senderCompId;
    /**
	 * 발송 부서ID
	 */
    private String senderDeptId;
    /**
	 * 발송 회사명
	 */
    private String senderCompName;
    /**
	 * 발송 부서명
	 */
    private String senderDeptName;
    /**
	 * 발송자직위
	 */
    private String senderPos;
    /**
	 * 등록취소여부
	 */
    private String unregistYn;
    /**
	 * DOC_TYPE 명칭
	 */
    private String categoryName;
    /**
	 * 채번사용안함여부
	 */
    private boolean noSerialYn;
    /**
	 * 분류번호
	 */
    private String classNumber;
    /**
	 * 분류번호명
	 */
    private String docnumName;
    
	/**
	 * 편철 다국어 ID
	 */
	private String bindingResourceId;

    /**
	 * 발송정보
	 */
    private SendInfoVO sendInfoVO = new SendInfoVO();
    /**
	 * 접수문서보고경로
	 */
    private List<EnfLineVO> enfLines = new ArrayList<EnfLineVO>();
    /**
	 * 현재 보고 경로를 가져온다.
	 */
    private EnfLineVO enfLine = new EnfLineVO();
    /**
	 * 파일정보
	 */
    private List<FileVO> fileInfos = new ArrayList<FileVO>();
    /**
	 * 접수문서 수신자정보
	 */
    private List<EnfRecvVO> receiverInfos = new ArrayList<EnfRecvVO>();
    /**
	 * 비전자 문서 정보
	 */
    private NonElectronVO nonElectronVO = new NonElectronVO();

    /**
	 * 배부된문서ID
	 */
    private String enfDocId;

    /**
	 * 수신자순서
	 */
    private int receiverOrder;

    /**
	 */
    private EnfProcVO enfProcVO = new EnfProcVO();

    /**
	 * 관련 문서 정보
	 */
    List<RelatedDocVO> relatedDoc = new ArrayList<RelatedDocVO>();

    /**
	 * 소유 부서
	 */
    OwnDeptVO ownDeptVO = new OwnDeptVO();

    /**
	 * 소유부서정보
	 */
    private List<OwnDeptVO> ownDepts = new ArrayList<OwnDeptVO>();

    /** 관련규정 */
    private List<RelatedRuleVO> relatedRules = new ArrayList<RelatedRuleVO>();

    /** 공람자 */
    private List<PubReaderVO> pubReaders = new ArrayList<PubReaderVO>();

    
    /**
	 */
    private Locale locale;
    
    
    /**
     * 조회여부(일자)
	 */
    private String readDate;
    
    

    /**
	 * @return the nonElectronVO
	 */
	public NonElectronVO getNonElectronVO() {
		return nonElectronVO;
	}


	/**
	 * @param nonElectronVO the nonElectronVO to set
	 */
	public void setNonElectronVO(NonElectronVO nonElectronVO) {
		this.nonElectronVO = nonElectronVO;
	}


	/**
	 * @return the enfProcVO
	 */
	public EnfProcVO getEnfProcVO() {
		return enfProcVO;
	}


	/**
	 * @param enfProcVO the enfProcVO to set
	 */
	public void setEnfProcVO(EnfProcVO enfProcVO) {
		this.enfProcVO = enfProcVO;
	}


	/**
	 * @return the ownDeptVO
	 */
	public OwnDeptVO getOwnDeptVO() {
		return ownDeptVO;
	}


	/**
	 * @param ownDeptVO the ownDeptVO to set
	 */
	public void setOwnDeptVO(OwnDeptVO ownDeptVO) {
		this.ownDeptVO = ownDeptVO;
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
	 * @return  the originCompId
	 */
    public String getOriginCompId() {
	return originCompId;
    }


    /**
	 * @param originCompId  the originCompId to set
	 */
    public void setOriginCompId(String originCompId) {
	this.originCompId = originCompId;
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
	 * @return  the accepterId
	 */
    public String getAccepterId() {
	return accepterId;
    }


    /**
	 * @param accepterId  the accepterId to set
	 */
    public void setAccepterId(String accepterId) {
	this.accepterId = accepterId;
    }


    /**
	 * @return  the accepterName
	 */
    public String getAccepterName() {
	return accepterName;
    }


    /**
	 * @param accepterName  the accepterName to set
	 */
    public void setAccepterName(String accepterName) {
	this.accepterName = accepterName;
    }


    /**
	 * @return  the accepterPos
	 */
    public String getAccepterPos() {
	return accepterPos;
    }


    /**
	 * @param accepterPos  the accepterPos to set
	 */
    public void setAccepterPos(String accepterPos) {
	this.accepterPos = accepterPos;
    }


    /**
	 * @return  the acceptDeptId
	 */
    public String getAcceptDeptId() {
	return acceptDeptId;
    }


    /**
	 * @param acceptDeptId  the acceptDeptId to set
	 */
    public void setAcceptDeptId(String acceptDeptId) {
	this.acceptDeptId = acceptDeptId;
    }


    /**
	 * @return  the acceptDeptName
	 */
    public String getAcceptDeptName() {
	return acceptDeptName;
    }


    /**
	 * @param acceptDeptName  the acceptDeptName to set
	 */
    public void setAcceptDeptName(String acceptDeptName) {
	this.acceptDeptName = acceptDeptName;
    }


    /**
	 * @return  the acceptDate
	 */
    public String getAcceptDate() {
	return acceptDate;
    }


    /**
	 * @param acceptDate  the acceptDate to set
	 */
    public void setAcceptDate(String acceptDate) {
	this.acceptDate = acceptDate;
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
	 * @return  the distributeYn
	 */
    public String getDistributeYn() {
	return distributeYn;
    }


    /**
	 * @param distributeYn  the distributeYn to set
	 */
    public void setDistributeYn(String distributeYn) {
	this.distributeYn = distributeYn;
    }


    /**
	 * @return  the distributeNumber
	 */
    public int getDistributeNumber() {
	return distributeNumber;
    }


    /**
	 * @param distributeNumber  the distributeNumber to set
	 */
    public void setDistributeNumber(int distributeNumber) {
	this.distributeNumber = distributeNumber;
    }


    /**
	 * @return  the distributorId
	 */
    public String getDistributorId() {
	return distributorId;
    }


    /**
	 * @param distributorId  the distributorId to set
	 */
    public void setDistributorId(String distributorId) {
	this.distributorId = distributorId;
    }


    /**
	 * @return  the distributorName
	 */
    public String getDistributorName() {
	return distributorName;
    }


    /**
	 * @param distributorName  the distributorName to set
	 */
    public void setDistributorName(String distributorName) {
	this.distributorName = distributorName;
    }


    /**
	 * @return  the distributorPos
	 */
    public String getDistributorPos() {
	return distributorPos;
    }


    /**
	 * @param distributorPos  the distributorPos to set
	 */
    public void setDistributorPos(String distributorPos) {
	this.distributorPos = distributorPos;
    }


    /**
	 * @return  the distributorDeptId
	 */
    public String getDistributorDeptId() {
	return distributorDeptId;
    }


    /**
	 * @param distributorDeptId  the distributorDeptId to set
	 */
    public void setDistributorDeptId(String distributorDeptId) {
	this.distributorDeptId = distributorDeptId;
    }


    /**
	 * @return  the distributorDeptName
	 */
    public String getDistributorDeptName() {
	return distributorDeptName;
    }


    /**
	 * @param distributorDeptName  the distributorDeptName to set
	 */
    public void setDistributorDeptName(String distributorDeptName) {
	this.distributorDeptName = distributorDeptName;
    }


    /**
	 * @return  the distributeDate
	 */
    public String getDistributeDate() {
	return distributeDate;
    }


    /**
	 * @param distributeDate  the distributeDate to set
	 */
    public void setDistributeDate(String distributeDate) {
	this.distributeDate = distributeDate;
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
	 * @return  the sendDate
	 */
    public String getSendDate() {
	return sendDate;
    }


    /**
	 * @param sendDate  the sendDate to set
	 */
    public void setSendDate(String sendDate) {
	this.sendDate = sendDate;
    }


    /**
	 * @return  the sendOpinion
	 */
    public String getSendOpinion() {
	return sendOpinion;
    }


    /**
	 * @param sendOpinion  the sendOpinion to set
	 */
    public void setSendOpinion(String sendOpinion) {
	this.sendOpinion = sendOpinion;
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
	 * @return  the enfLines
	 */
    public List<EnfLineVO> getEnfLines() {
	return enfLines;
    }


    /**
	 * @param enfLines  the enfLines to set
	 */
    public void setEnfLines(List<EnfLineVO> enfLines) {
	this.enfLines = enfLines;
    }


    /**
	 * @return  the enfLines
	 */
    public EnfLineVO getEnfLine() {
	return enfLine;
    }


    /**
	 * @param enfLines  the enfLines to set
	 */
    public void setEnfLine(EnfLineVO enfLine) {
	this.enfLine = enfLine;
    }


    /**
	 * @return  the fileInfos
	 */
    public List<FileVO> getFileInfos() {
	return fileInfos;
    }


    /**
	 * @param fileInfos  the fileInfos to set
	 */
    public void setFileInfos(List<FileVO> fileInfos) {
	this.fileInfos = fileInfos;
    }


    /**
	 * @return  the receiverInfos
	 */
    public List<EnfRecvVO> getReceiverInfos() {
	return receiverInfos;
    }


    /**
	 * @param receiverInfos  the receiverInfos to set
	 */
    public void setReceiverInfos(List<EnfRecvVO> receiverInfos) {
	this.receiverInfos = receiverInfos;
    }


    /**
	 * @param senderCompId  the senderCompId to set
	 */
    public void setSenderCompId(String senderCompId) {
	this.senderCompId = senderCompId;
    }


    /**
	 * @return  the senderCompId
	 */
    public String getSenderCompId() {
	return senderCompId;
    }


    /**
	 * @param senderDeptId  the senderDeptId to set
	 */
    public void setSenderDeptId(String senderDeptId) {
	this.senderDeptId = senderDeptId;
    }


    /**
	 * @return  the senderDeptId
	 */
    public String getSenderDeptId() {
	return senderDeptId;
    }


    /**
	 * @param senderPos  the senderPos to set
	 */
    public void setSenderPos(String senderPos) {
	this.senderPos = senderPos;
    }


    /**
	 * @return  the senderPos
	 */
    public String getSenderPos() {
	return senderPos;
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
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public boolean getNoSerialYn() {
	return noSerialYn;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param noSerialYn
	 * @see   
	 */
    public void setNoSerialYn(boolean noSerialYn) {

	this.noSerialYn = noSerialYn;
    }


    public NonElectronVO getNonElectron() {

	return nonElectronVO;
    }


    public void setNonElectron(NonElectronVO nonElectronVO) {
	this.nonElectronVO = nonElectronVO;
    }


    public EnfProcVO getEnfProc() {

	return enfProcVO;
    }


    public void setEnfProc(EnfProcVO enfProcVO) {

	this.enfProcVO = enfProcVO;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public List<RelatedDocVO> getRelatedDoc() {
	return relatedDoc;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param relatedDoc
	 * @see   
	 */
    public void setRelatedDoc(List<RelatedDocVO> relatedDoc) {
	this.relatedDoc = relatedDoc;
    }


    public OwnDeptVO getOwnDept() {
	return ownDeptVO;
    }


    public void setOwnDept(OwnDeptVO ownDeptVO) {
	this.ownDeptVO = ownDeptVO;
    }


    /**
	 * @param enfDocId  the enfDocId to set
	 */
    public void setEnfDocId(String enfDocId) {
	this.enfDocId = enfDocId;
    }


    /**
	 * @return  the enfDocId
	 */
    public String getEnfDocId() {
	return enfDocId;
    }


    /**
	 * @return  the ownDeptVOs
	 */
    public List<OwnDeptVO> getOwnDepts() {
	return ownDepts;
    }


    /**
	 * @param ownDepts  the ownDepts to set
	 */
    public void setOwnDepts(List<OwnDeptVO> ownDepts) {
	this.ownDepts = ownDepts;
    }


    /**
     * @return the relatedRule
     */
    public List<RelatedRuleVO> getRelatedRule() {
	return relatedRules;
    }


    /**
     * @param relatedRule
     *            the relatedRule to set
     */
    public void setRelatedRule(List<RelatedRuleVO> relatedRules) {
	this.relatedRules = relatedRules;
    }


    /**
	 * @param receiverOrder  the receiverOrder to set
	 */
    public void setReceiverOrder(int receiverOrder) {
	this.receiverOrder = receiverOrder;
    }


    /**
	 * @return  the receiverOrder
	 */
    public int getReceiverOrder() {
	return receiverOrder;
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
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public Locale getLocale(){
	return this.locale;
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @param locale
	 * @see   
	 */
    public void setLocale(Locale locale){
	this.locale = locale;
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
