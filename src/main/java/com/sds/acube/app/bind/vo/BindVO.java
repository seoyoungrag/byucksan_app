package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;

/**
 * Class Name : BindVO.java <br> Description : 편철 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 18
 * @version  1.0
 * @see  BindVO
 */
public class BindVO implements BindConstants {

    /**
	 */
    private String compId; // 회사ID
    /**
	 */
    private String deptId; // 부서ID
    /**
	 */
    private String deptName; // 부서명
    /**
	 */
    private String bindId; // 편철ID
    /**
	 */
    private String bindName; // 편철명
    /**
	 */
    private String unitId; // 업무코드
    /**
	 */
    private String unitName; // 업무명
    /**
	 */
    private String unitType; // 업무단위(UTT001: 회사공통, UTT002: 부서고유)
    /**
	 */
    private String modifiedUnitType; // 업무단위(UTT001: 회사공통, UTT002: 부서고유)
    /**
	 */
    private String createYear; // 생산년도
    /**
	 */
    private String expireYear; // 종료년도
    /**
	 */
    private String docType; // 문서유형
    /**
	 */
    private String retentionPeriod; // 보존기간
    /**
	 */
    private int ordered; // 편철순서
    /**
	 */
    private String created; // 생성
    /**
	 */
    private String createdId; // 생성자ID
    /**
	 */
    private String createdName; // 생성자명
    /**
	 */
    private String modified; // 수정일
    /**
	 */
    private String modifiedId; // 수정자ID
    /**
	 */
    private String modifiedName; // 수정자명
    /**
	 */
    private String arrange; // 편철확인
    /**
	 */
    private String binding; // 편철확정
    /**
	 */
    private String sendType; // 인수/인계 코드 (DST001 : 사용, DST002 : 인수, DST003 : 인계)
    /**
	 */
    private String sendDeptId; // 인수/인계 부서ID
    /**
	 */
    private String sendDeptName;
    /**
	 */
    private String sendUnitId; // 인수/인계 업무코드
    /**
	 */
    private String sendUnitName;
    /**
	 */
    private String sendCreateYear; // 인수/인계 생산년도
    /**
	 */
    private String sended; // 인수/인계일
    /**
	 */
    private String isActive; // 사용/삭제 여부
    /**
	 */
    private String prefix; // 연계코드

    /**
	 */
    private int docCount;
    /**
	 */
    private int shareCount;
    /**
	 */
    private int totalCount;

    /**
	 */
    private String targetId; // target DeptId
    /**
	 */
    private String currentTime;

    /**
	 */
    private String orgBindId;
    /**
	 */
    private String nextBindId;
    
    /**
	 */
    private String docMgrPath; // 문서관리 path
    /**
	 */
    private String docMgrUuid; // 문서관리 uuid;
    
	/**
	 * 리소스 편철 ID
	 */
	private String resourceId;
	
	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 편철의 부모 Id
	 */	
	private String parentBindId;

	/**
	 * 편철 순번
	 */	
	private long bindOrder; 
	
	/**
	 * 편철 깊이
	 */	
	private long bindDepth;
	
	/**
	 * 자식이 있는지
	 */	
	private String isChildBind;
	
	/**
	 * 설명
	 */	
	private String description;
    
	/**
	 * 자식을 찾을 건지("C"), 형제를 찾을 건지
	 */	
	private String isSelectType="";
	
	/**
	 * 관리자 정보
	 */	
	private String adminInfo;
	
	/**
	 * 관리자 수정 전 정보
	 */	
	private String origAdminInfo;
	
	/**
	 * 권한 정보
	 */	
	private String authInfo;
	
	/**
	 * 권한 수정 전 정보
	 */	
	private String origAuthInfo;

	private String bindType; //bind type(공유 : SHARE, 공용 문서함 : DOC_BOARD)

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
	 * @param compId
	 * @see   
	 */
    public void setCompId(String compId) {
	this.compId = compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDeptId() {
	return deptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param deptId
	 * @see   
	 */
    public void setDeptId(String deptId) {
	this.deptId = deptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getBindId() {
	return bindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param bindId
	 * @see   
	 */
    public void setBindId(String bindId) {
	this.bindId = bindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getBindName() {
	return bindName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param bindName
	 * @see   
	 */
    public void setBindName(String bindName) {
	this.bindName = bindName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUnitId() {
	return unitId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param unitId
	 * @see   
	 */
    public void setUnitId(String unitId) {
	this.unitId = unitId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUnitName() {
	return unitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param unitName
	 * @see   
	 */
    public void setUnitName(String unitName) {
	this.unitName = unitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUnitType() {
		return unitType;
	}


	/**
	 * <pre>  설명 </pre>
	 * @param unitType
	 * @see   
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}


	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getModifiedUnitType() {
		return modifiedUnitType;
	}


	/**
	 * <pre>  설명 </pre>
	 * @param modifiedUnitType
	 * @see   
	 */
	public void setModifiedUnitType(String modifiedUnitType) {
		this.modifiedUnitType = modifiedUnitType;
	}


	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getCreateYear() {
	return createYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param createYear
	 * @see   
	 */
    public void setCreateYear(String createYear) {
	this.createYear = createYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getExpireYear() {
	return expireYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param expireYear
	 * @see   
	 */
    public void setExpireYear(String expireYear) {
	this.expireYear = expireYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDocType() {
	return docType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param docType
	 * @see   
	 */
    public void setDocType(String docType) {
	this.docType = docType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRetentionPeriod() {
	return retentionPeriod;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param retentionPeriod
	 * @see   
	 */
    public void setRetentionPeriod(String retentionPeriod) {
	this.retentionPeriod = retentionPeriod;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getOrdered() {
	return ordered;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param ordered
	 * @see   
	 */
    public void setOrdered(int ordered) {
	this.ordered = ordered;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCreated() {
	return created;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param created
	 * @see   
	 */
    public void setCreated(String created) {
	this.created = created;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCreatedId() {
	return createdId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param createdId
	 * @see   
	 */
    public void setCreatedId(String createdId) {
	this.createdId = createdId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCreatedName() {
	return createdName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param createdName
	 * @see   
	 */
    public void setCreatedName(String createdName) {
	this.createdName = createdName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getModified() {
	return modified;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param modified
	 * @see   
	 */
    public void setModified(String modified) {
	this.modified = modified;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getModifiedId() {
	return modifiedId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param modifiedId
	 * @see   
	 */
    public void setModifiedId(String modifiedId) {
	this.modifiedId = modifiedId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getModifiedName() {
	return modifiedName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param modifiedName
	 * @see   
	 */
    public void setModifiedName(String modifiedName) {
	this.modifiedName = modifiedName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getArrange() {
	return arrange;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param arrange
	 * @see   
	 */
    public void setArrange(String arrange) {
	this.arrange = arrange;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getBinding() {
	return binding;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param binding
	 * @see   
	 */
    public void setBinding(String binding) {
	this.binding = binding;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendType() {
	return sendType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendType
	 * @see   
	 */
    public void setSendType(String sendType) {
	this.sendType = sendType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendDeptId() {
	return sendDeptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendDeptId
	 * @see   
	 */
    public void setSendDeptId(String sendDeptId) {
	this.sendDeptId = sendDeptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendDeptName() {
	return sendDeptName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendDeptName
	 * @see   
	 */
    public void setSendDeptName(String sendDeptName) {
	this.sendDeptName = sendDeptName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendUnitId() {
	return sendUnitId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendUnitId
	 * @see   
	 */
    public void setSendUnitId(String sendUnitId) {
	this.sendUnitId = sendUnitId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendUnitName() {
	return sendUnitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendUnitName
	 * @see   
	 */
    public void setSendUnitName(String sendUnitName) {
	this.sendUnitName = sendUnitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSendCreateYear() {
	return sendCreateYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sendCreateYear
	 * @see   
	 */
    public void setSendCreateYear(String sendCreateYear) {
	this.sendCreateYear = sendCreateYear;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSended() {
	return sended;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param sended
	 * @see   
	 */
    public void setSended(String sended) {
	this.sended = sended;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIsActive() {
	return isActive;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param isActive
	 * @see   
	 */
    public void setIsActive(String isActive) {
	this.isActive = isActive;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getTotalCount() {
	return totalCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param totalCount
	 * @see   
	 */
    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getTargetId() {
	return targetId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param targetId
	 * @see   
	 */
    public void setTargetId(String targetId) {
	this.targetId = targetId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCurrentTime() {
	return currentTime;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param currentTime
	 * @see   
	 */
    public void setCurrentTime(String currentTime) {
	this.currentTime = currentTime;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param docCount
	 * @see   
	 */
    public void setDocCount(int docCount) {
	this.docCount = docCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getDocCount() {
	return docCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param deptName
	 * @see   
	 */
    public void setDeptName(String deptName) {
	this.deptName = deptName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDeptName() {
	return deptName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param orgBindId
	 * @see   
	 */
    public void setOrgBindId(String orgBindId) {
	this.orgBindId = orgBindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOrgBindId() {
	return orgBindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param shareCount
	 * @see   
	 */
    public void setShareCount(int shareCount) {
	this.shareCount = shareCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getShareCount() {
	return shareCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param nextBindId
	 * @see   
	 */
    public void setNextBindId(String nextBindId) {
	this.nextBindId = nextBindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getNextBindId() {
	return nextBindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param prefix
	 * @see   
	 */
    public void setPrefix(String prefix) {
	this.prefix = prefix;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPrefix() {
	return prefix;
    }


    /**
	 * @param docMgrPath  the docMgrPath to set
	 */
    public void setDocMgrPath(String docMgrPath) {
	this.docMgrPath = docMgrPath;
    }


    /**
	 * @return  the docMgrPath
	 */
    public String getDocMgrPath() {
	return docMgrPath;
    }


    /**
	 * @param docMgrUuid  the docMgrUuid to set
	 */
    public void setDocMgrUuid(String docMgrUuid) {
	this.docMgrUuid = docMgrUuid;
    }


    /**
	 * @return  the docMgrUuid
	 */
    public String getDocMgrUuid() {
	return docMgrUuid;
    }


	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}


	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}


	/**
	 * @return the langType
	 */
	public String getLangType() {
		return langType;
	}


	/**
	 * @param langType the langType to set
	 */
	public void setLangType(String langType) {
		this.langType = langType;
	}
	
	/**
	 * @return the parentBindId
	 */
	public String getParentBindId() {
		return parentBindId;
	}


	/**
	 * @param parentBindId the parentBindId to set
	 */
	public void setParentBindId(String parentBindId) {
		this.parentBindId = parentBindId;
	}


	/**
	 * @return the bindOrder
	 */
	public long getBindOrder() {
		return bindOrder;
	}


	/**
	 * @param bindOrder the bindOrder to set
	 */
	public void setBindOrder(long bindOrder) {
		this.bindOrder = bindOrder;
	}


	/**
	 * @return the bindDepth
	 */
	public long getBindDepth() {
		return bindDepth;
	}


	/**
	 * @param bindDepth the bindDepth to set
	 */
	public void setBindDepth(long bindDepth) {
		this.bindDepth = bindDepth;
	}

    /**
	 * @return the isChildUnit
	 */
	public String getIsChildBind() {
		return isChildBind;
	}


	/**
	 * @param isChildUnit the isChildUnit to set
	 */
	public void setIsChildBind(String isChildBind) {
		this.isChildBind = isChildBind;
	}
	

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
    /**
	 * @return the isSelectType
	 */
	public String getIsSelectType() {
		return isSelectType;
	}


	/**
	 * @param isSelectType the isSelectType to set
	 */
	public void setIsSelectType(String isSelectType) {
		this.isSelectType = isSelectType;
	}
	
   /**
	 * @return the adminInfo
	 */
	public String getAdminInfo() {
		return adminInfo;
	}


	/**
	 * @param adminInfo the adminInfo to set
	 */
	public void setAdminInfo(String adminInfo) {
		this.adminInfo = adminInfo;
	}
	
   /**
	 * @return the origAdminInfo
	 */
	public String getOrigAdminInfo() {
		return origAdminInfo;
	}

	/**
	 * @param origAdminInfo the origAdminInfo to set
	 */
	public void setOrigAdminInfo(String origAdminInfo) {
		this.origAdminInfo = origAdminInfo;
	}
	
	/**
	 * @return the authInfo
	 */
	public String getAuthInfo() {
		return authInfo;
	}

	/**
	 * @param authInfo the authInfo to set
	 */
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}

	/**
	 * @return the origAuthInfo
	 */
	public String getOrigAuthInfo() {
		return origAuthInfo;
	}

	/**
	 * @param origAuthInfo the origAuthInfo to set
	 */
	public void setOrigAuthInfo(String origAuthInfo) {
		this.origAuthInfo = origAuthInfo;
	}
	
	/**
	 * @return the bindType
	 */
	public String getBindType() {
		return bindType;
	}


	/**
	 * @param bindType the bindType to set
	 */
	public void setBindType(String bindType) {
		this.bindType = bindType;
	}
}
