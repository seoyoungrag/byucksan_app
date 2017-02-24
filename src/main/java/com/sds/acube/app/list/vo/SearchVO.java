package com.sds.acube.app.list.vo;


/**
 * Class Name  : SearchVO <br> Description : 검색 VO 클래스 <br> Modification Information <br> <br> 수 정 일 :  2011.03.25 <br> 수 정 자 :  김경훈 <br> 수정내용 :  userUID, 회사ID, 부서ID 추가 <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  SearchVO
 */ 
public class SearchVO {

	/**
	 * 검색시작일
	 */
	private String startDate;
	/**
	 * 검색종료일
	 */
	private String endDate;
	/**
	 * 문서제목
	 */
	private String title;
	/**
	 * 문서유형
	 */
	private String docType;
	/**
	 * 기안자
	 */
	private String drafterName;
	/**
	 * 결재자
	 */
	private String approverName;
	/**
	 * userID
	 */
	private String userId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 부서ID
	 */
	private String deptId;
	/**
	 * 부서명
	 */
	private String deptName;
	/**
	 * 하위부서ID
	 */
	private String rowDeptId;
	/**
	 * 확장부서ID
	 */
	private String exDeptId;
	/**
	 * 검색구분
	 */
	private String searchType;
	/**
	 * 검색 키워드
	 */
	private String searchWord;
	/**
	 * 리스트 타입
	 */
	private String listType;
	/**
	 * 생산문서 문서상태
	 */
	private String docAppState;
	/**
	 * 생산문서 문서상태(부서관리자)
	 */
	private String docAppStateDept;
	/**
	 * 생산문서 문서상태(반려문서)
	 */
	private String docReturnAppState;
	/**
	 * 생산문서 문서상태(완료문서)
	 */
	private String docAppCompleteState;
	/**
	 * 접수문서 문서상태
	 */
	private String docEnfState;
	/**
	 * 접수문서 문서상태(부서관리자)
	 */
	private String docEnfStateDept;
	/**
	 * 접수문서 문서상태(접수 대기)
	 */
	private String docEnfReciveState;
	/**
	 * 접수문서 문서상태(접수 대기 - 부서)
	 */
	private String docEnfReciveStateDept;
	/**
	 * 접수문서 문서상태(선람, 담당 지정 대기)
	 */
	private String docEnfDisplyWaitState;
	/**
	 * 접수문서 문서상태(접수이후)
	 */
	private String docEnfCompleteState;
	/**
	 * 결재 요청 유형
	 */
	private String askType;
	/**
	 * 결재 처리 유형  (기본)
	 */
	private String procType;
	/**
	 * 결재 처리 유형(결재자,대결자)
	 */
	private String apprProcType;
	/**
	 * 결재 처리 유형(처리자)
	 */
	private String processorProcType;
	/**
	 * 시행유형
	 */
	private String enfType;
	/**
	 * 시행유형(추가 필요한 경우)
	 */
	private String enfTypeAdd;
	/**
	 * 부서관리자여부
	 */
	private String deptAdminYn;
	/**
	 * 문서함 코드
	 */
	private String lobCode;
	/**
	 * 공람게시 범위
	 */
	private String displayRange;
	/**
	 * 생산문서 공람사용여부
	 */
	private String optAppDocDisplayYn;
	/**
	 * 접수문서 공람사용여부
	 */
	private String optEnfDocDisplayYn;
	/**
	 * 생산문서 공람사용정보
	 */
	private String optAppDocDisplayInfo;
	/**
	 * 접수문서 공람사용정보
	 */
	private String optEnfDocDisplayInfo;
	/**
	 * 생산문서 사용대상 유형 코드
	 */
	private String optAppDocDpiCode;
	/**
	 * 접수문서 사용대상 유형 코드
	 */
	private String optEnfDocDpiCode;
	/**
	 * 열람 범위
	 */
	private String readingRange;
	/**
	 * 확장 열람 범위
	 */
	private String exReadingRange;
	/**
	 * 문서대장 총 년도/회기
	 */
	private String registRange;
	/**
	 * 문서대장 선택한 년도/회기
	 */
	private String registCurRange;
	/**
	 * 문서대장 년도/회기 사용 여부
	 */
	private String registSearchTypeYn;
	/**
	 * 문서대장 년도/회기 구분
	 */
	private String registSearchTypeValue;
	/**
	 * 공람게시기준일
	 */
	private String pubPostStdDate;
	/**
	 * 결재자역할
	 */
	private String approverRole;
	/**
	 * 결재자역할
	 */
	private String approverAddRole;
	/**
	 * 등록번호(시작)
	 */
	private String startDocNum;
	/**
	 * 등록번호(끝)
	 */
	private String endDocNum;
	/**
	 * 편철 ID
	 */
	private String bindingId;
	/**
	 * 편철명
	 */
	private String bindingName;
	/**
	 * 보존년한
	 */
	private String retentionPeriod="";
	/**
	 * 전송 종류
	 */
	private String bindSendType="";

	/**
	 * 전송한 BindId
	 */
	private String orgBindId="";	
	/**
	 * 문서구분(전자)
	 */
	private String searchElecYn;
	/**
	 * 문서구분(비전자)
	 */
	private String searchNonElecYn;
	/**
	 * 시행구분
	 */
	private String searchDetType;
	/**
	 * 결재자명 - 상세검색
	 */
	private String searchApprovalName;
	/**
	 * 결재타입(결재)
	 */
	private String searchApprTypeApproval;
	/**
	 * 결재타입(검토)
	 */
	private String searchApprTypeExam;
	/**
	 * 결재타입(협조)
	 */
	private String searchApprTypeCoop;
	/**
	 * 결재타입(합의)
	 */
	private String searchApprTypeAgree;
	/**
	 * 결재타입(기안)
	 */
	private String searchApprTypeDraft;
	/**
	 * 결재타입(선람)
	 */
	private String searchApprTypePreDis;
	/**
	 * 결재타입(담당)
	 */
	private String searchApprTypeResponse;
	/**
	 * 결재타입(감사)
	 */
	private String searchApprTypeAudit;
	/**
	 * 결재타입(기타)
	 */
	private String searchApprTypeEtc;
	/**
	 * 결재타입(조건절)
	 */
	private String searchApprTypeList;
	/**
	 * 상세검색 여부
	 */
	private String detailSearchYn;
	/**
	 * 모바일 여부
	 */
	private String mobileYn;	
	/**
	 * 업무유형
	 */
	private String workType;
	/**
	 * 생산문서 여부
	 */
	private String appDocYn;
	/**
	 * 접수문서 여부
	 */
	private String enfDocYn;
	/**
	 * 날인유형
	 */
	private String sealType;
	/**
	 * 문서번호
	 */
	private String docId;
	/**
	 * 페이지 사이즈
	 */
	private String pageSize;
	/**
	 * 업무 처리 상태
	 */
	private String procState;
	/**
	 * 본부하위부서
	 */
	private String searchHeadOffice;
	/**
	 * 기관 하위부서
	 */
	private String searchInstitution;
	/**
	 * 열람범위(본부)
	 */
	private String headOfficeReadingRange;
	/**
	 * 열람범위(회사)
	 */
	private String institutionReadingRange;
	/**
	 * 웹서비스 여부
	 */
	private String webServiceYn;
	/**
	 * 감사,부서감사 코드(검색조건)
	 */
	private String searchAuditOpt009;
	/**
	 * 일상감사 코드(검색조건)
	 */
	private String searchAuditOpt021;
	/**
	 * 준법감시 코드(검색조건)
	 */
	private String searchAuditOpt022;
	/**
	 * 감사위원 코드(검색조건)
	 */
	private String searchAuditOpt023;
	/**
	 * 공람여부
	 */
	private String displayYn;
	/**
	 * 심사 검색 조건 코드
	 */
	private String docJudgeState;
	/**
	 * 심사 검색 조건 코드(부서)
	 */
	private String docJudgeDeptState;
	/**
	 * 심사 변환 코드
	 */
	private String docReplaceJudgeState;
	/**
	 * 요청코드(확장2)
	 */
	private String askType2;
	/**
	 * 요청코드(확장3)
	 */
	private String askType3;
	/**
	 * 요청코드(확장4)
	 */
	private String askType4;
	/**
	 * 요청코드(확장5)
	 */
	private String askType5;
	/**
	 * 요청코드(확장6)
	 */
	private String askType6;
	/**
	 * 요청코드(확장7)
	 */
	private String askType7;
	/**
	 * 요청코드(확장8)
	 */
	private String askType8;
	/**
	 * 요청코드리스트
	 */
	private String askTypeList;
	/**
	 * 검색부서명(검색 조건)
	 */
	private String searchWordDeptName;
	/**
	 * 검사부열람함 공개 여부(공개)
	 */
	private String searchAuditReadY;
	/**
	 * 검사부열람함 공개 여부(비공개)
	 */
	private String searchAuditReadN;
	/**
	 * 문서수신대상
	 */
	private String recvObject;
	/**
	 * 생산문서 간편 조회 여부
	 */
	private String easyApprSearch;
	/**
	 * 접수문서 간편 조회 여부
	 */
	private String easyEnfSearch;
	/**
	 * 문서코드 조회값
	 */
	private String searchLobCode;
	/**
	 * 감사자유형(tgw_audit_dept의 auditor_type)
	 */
	private String auditorType;
	/**
	 * 직인날인대장 확장 여부
	 */
	private String sealRegistExdYn;
	/**
	 * 문서부서분류
	 */
	private String searchDeptCategory;
	/**
	 * 문서일련번호
	 */
	private String searchSerialNumber;
	/**
	 * 부서장 아이디
	 */
	private String chiefId;
	/**
	 * 일일감사대장 사전감사 결재라인 체크 유무
	 */
	private String dailyAuditLineChkYn;
	/**
	 * 문서 열람부서ID
	 */
	private String searchAuthDeptId;
	/**
	 * 문서 열람부서명
	 */
	private String searchAuthDeptName;
	
	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
     * 라디오 타입
     */
    private String radioType;
	
    /**
     * 소유자 ID
     */
    private String docOwnerId;
    
    /**
     * 검색일자 사용여부
     */
    private String searchYn;
    
    private String docDisplayType;
    
    private String sortType;
    private String sortBy;
    
    
    /**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}
	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	/**
	 * @return the docDisplayType
	 */
	public String getDocDisplayType() {
		return docDisplayType;
	}
	/**
	 * @param docDisplayType the docDisplayType to set
	 */
	public void setDocDisplayType(String docDisplayType) {
		this.docDisplayType = docDisplayType;
	}
	/**
     * @return the searchYn
     */
    public String getSearchYn() {
    return searchYn;
    }
    /**
     * @param searchYn the searchYn to set
     */
    public void setSearchYn(String searchYn) {
      this.searchYn = searchYn;
    }
    
    /**
     * @return  the radioType
     */
    public String getRadioType() {
      return radioType;
    }
    /**
     * @param radioType  the radioType to set
     */
    public void setRadioType(String radioType) {
      this.radioType = radioType;
    }
    /**
     * @return  the docOwnerId
     */
    public String getDocOwnerId() {
      return docOwnerId;
    }
    /**
     * @param docOwnerId  the docOwnerId to set
     */
    public void setDocOwnerId(String docOwnerId) {
      this.docOwnerId = docOwnerId;
    }
    /**
	 * @return  the docJudgeState
	 */
        public String getDocJudgeState() {
            return docJudgeState;
        }
	/**
	 * @param docJudgeState  the docJudgeState to set
	 */
        public void setDocJudgeState(String docJudgeState) {
            this.docJudgeState = docJudgeState;
        }
	/**
	 * @return  the docJudgeDeptState
	 */
        public String getDocJudgeDeptState() {
            return docJudgeDeptState;
        }
	/**
	 * @param docJudgeDeptState  the docJudgeDeptState to set
	 */
        public void setDocJudgeDeptState(String docJudgeDeptState) {
            this.docJudgeDeptState = docJudgeDeptState;
        }
	/**
	 * @return  the docReplaceJudgeState
	 */
        public String getDocReplaceJudgeState() {
            return docReplaceJudgeState;
        }
	/**
	 * @param docReplaceJudgeState  the docReplaceJudgeState to set
	 */
        public void setDocReplaceJudgeState(String docReplaceJudgeState) {
            this.docReplaceJudgeState = docReplaceJudgeState;
        }
	/**
	 * @return  the searchHeadOffice
	 */
        public String getSearchHeadOffice() {
            return searchHeadOffice;
        }
	/**
	 * @param searchHeadOffice  the searchHeadOffice to set
	 */
        public void setSearchHeadOffice(String searchHeadOffice) {
            this.searchHeadOffice = searchHeadOffice;
        }
	/**
	 * @return  the searchInstitution
	 */
        public String getSearchInstitution() {
            return searchInstitution;
        }
	/**
	 * @param searchInstitution  the searchInstitution to set
	 */
        public void setSearchInstitution(String searchInstitution) {
            this.searchInstitution = searchInstitution;
        }
	/**
	 * @return  the headOfficeReadingRange
	 */
        public String getHeadOfficeReadingRange() {
            return headOfficeReadingRange;
        }
	/**
	 * @param headOfficeReadingRange  the headOfficeReadingRange to set
	 */
        public void setHeadOfficeReadingRange(String headOfficeReadingRange) {
            this.headOfficeReadingRange = headOfficeReadingRange;
        }
	/**
	 * @return  the institutionReadingRange
	 */
        public String getInstitutionReadingRange() {
            return institutionReadingRange;
        }
	/**
	 * @param institutionReadingRange  the institutionReadingRange to set
	 */
        public void setInstitutionReadingRange(String institutionReadingRange) {
            this.institutionReadingRange = institutionReadingRange;
        }
	/**
	 * @return  the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate  the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return  the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate  the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	 * @return  the userUid
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userUid  the userUid to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return  the deptId
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId  the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * @return  the deptName
	 */
        public String getDeptName() {
            return deptName;
        }
	/**
	 * @param deptName  the deptName to set
	 */
        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }
	/**
	 * @return  the rowDeptId
	 */
        public String getRowDeptId() {
            return rowDeptId;
        }
	/**
	 * @param rowDeptId  the rowDeptId to set
	 */
        public void setRowDeptId(String rowDeptId) {
            this.rowDeptId = rowDeptId;
        }
        
	/**
	 * @return  the exDeptId
	 */
        public String getExDeptId() {
            return exDeptId;
        }
	/**
	 * @param exDeptId  the exDeptId to set
	 */
        public void setExDeptId(String exDeptId) {
            this.exDeptId = exDeptId;
        }
	/**
	 * @return  the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType  the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return  the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}
	/**
	 * @param searchWord  the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	/**
	 * @return  the listType
	 */
	public String getListType() {
		return listType;
	}
	/**
	 * @param listType  the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}
	/**
	 * @return  the docAppState
	 */
	public String getDocAppState() {
		return docAppState;
	}
	/**
	 * @param docAppState  the docAppState to set
	 */
	public void setDocAppState(String docAppState) {
		this.docAppState = docAppState;
	}	
	/**
	 * @return  the docAppStateDept
	 */
        public String getDocAppStateDept() {
            return docAppStateDept;
        }
	/**
	 * @param docAppStateDept  the docAppStateDept to set
	 */
        public void setDocAppStateDept(String docAppStateDept) {
            this.docAppStateDept = docAppStateDept;
        }
        
	/**
	 * @return  the docReturnAppState
	 */
        public String getDocReturnAppState() {
            return docReturnAppState;
        }
	/**
	 * @param docReturnAppState  the docReturnAppState to set
	 */
        public void setDocReturnAppState(String docReturnAppState) {
            this.docReturnAppState = docReturnAppState;
        }
	/**
	 * @return  the docEnfState
	 */
	public String getDocEnfState() {
		return docEnfState;
	}
	/**
	 * @param docEnfState  the docEnfState to set
	 */
	public void setDocEnfState(String docEnfState) {
		this.docEnfState = docEnfState;
	}	
	/**
	 * @return  the docEnfStateDept
	 */
        public String getDocEnfStateDept() {
            return docEnfStateDept;
        }
	/**
	 * @param docEnfStateDept  the docEnfStateDept to set
	 */
        public void setDocEnfStateDept(String docEnfStateDept) {
            this.docEnfStateDept = docEnfStateDept;
        }
        
	/**
	 * @return  the docEnfReciveState
	 */
        public String getDocEnfReciveState() {
            return docEnfReciveState;
        }
	/**
	 * @param docEnfReciveState  the docEnfReciveState to set
	 */
        public void setDocEnfReciveState(String docEnfReciveState) {
            this.docEnfReciveState = docEnfReciveState;
        }
        
	/**
	 * @return  the docEnfReciveStateDept
	 */
        public String getDocEnfReciveStateDept() {
            return docEnfReciveStateDept;
        }
	/**
	 * @param docEnfReciveStateDept  the docEnfReciveStateDept to set
	 */
        public void setDocEnfReciveStateDept(String docEnfReciveStateDept) {
            this.docEnfReciveStateDept = docEnfReciveStateDept;
        }
	/**
	 * @return  the docEnfDisplyWaitState
	 */
        public String getDocEnfDisplyWaitState() {
            return docEnfDisplyWaitState;
        }
	/**
	 * @param docEnfDisplyWaitState  the docEnfDisplyWaitState to set
	 */
        public void setDocEnfDisplyWaitState(String docEnfDisplyWaitState) {
            this.docEnfDisplyWaitState = docEnfDisplyWaitState;
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
	 * @return  the apprProcType
	 */
        public String getApprProcType() {
            return apprProcType;
        }
	/**
	 * @param apprProcType  the apprProcType to set
	 */
        public void setApprProcType(String apprProcType) {
            this.apprProcType = apprProcType;
        }
	/**
	 * @return  the processorProcType
	 */
        public String getProcessorProcType() {
            return processorProcType;
        }
	/**
	 * @param processorProcType  the processorProcType to set
	 */
        public void setProcessorProcType(String processorProcType) {
            this.processorProcType = processorProcType;
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
	 * @return  the enfTypeAdd
	 */
        public String getEnfTypeAdd() {
            return enfTypeAdd;
        }
	/**
	 * @param enfTypeAdd  the enfTypeAdd to set
	 */
        public void setEnfTypeAdd(String enfTypeAdd) {
            this.enfTypeAdd = enfTypeAdd;
        }
	/**
	 * @return  the deptAdminYn
	 */
        public String getDeptAdminYn() {
            return deptAdminYn;
        }
	/**
	 * @param deptAdminYn  the deptAdminYn to set
	 */
        public void setDeptAdminYn(String deptAdminYn) {
            this.deptAdminYn = deptAdminYn;
        }
	/**
	 * @return  the lobCode
	 */
        public String getLobCode() {
            return lobCode;
        }
	/**
	 * @param lobCode  the lobCode to set
	 */
        public void setLobCode(String lobCode) {
            this.lobCode = lobCode;
        }
	
	/**
	 * @return  the displayRange
	 */
        public String getDisplayRange() {
            return displayRange;
        }
	/**
	 * @param displayRange  the displayRange to set
	 */
        public void setDisplayRange(String displayRange) {
            this.displayRange = displayRange;
        }
	/**
	 * @return  the optAppDocDisplayYn
	 */
        public String getOptAppDocDisplayYn() {
            return optAppDocDisplayYn;
        }
	/**
	 * @param optAppDocDisplayYn  the optAppDocDisplayYn to set
	 */
        public void setOptAppDocDisplayYn(String optAppDocDisplayYn) {
            this.optAppDocDisplayYn = optAppDocDisplayYn;
        }
	/**
	 * @return  the optEnfDocDisplayYn
	 */
        public String getOptEnfDocDisplayYn() {
            return optEnfDocDisplayYn;
        }
	/**
	 * @param optEnfDocDisplayYn  the optEnfDocDisplayYn to set
	 */
        public void setOptEnfDocDisplayYn(String optEnfDocDisplayYn) {
            this.optEnfDocDisplayYn = optEnfDocDisplayYn;
        }
	/**
	 * @return  the optAppDocDisplayInfo
	 */
        public String getOptAppDocDisplayInfo() {
            return optAppDocDisplayInfo;
        }
	/**
	 * @param optAppDocDisplayInfo  the optAppDocDisplayInfo to set
	 */
        public void setOptAppDocDisplayInfo(String optAppDocDisplayInfo) {
            this.optAppDocDisplayInfo = optAppDocDisplayInfo;
        }
	/**
	 * @return  the optEnfDocDisplayInfo
	 */
        public String getOptEnfDocDisplayInfo() {
            return optEnfDocDisplayInfo;
        }
	/**
	 * @param optEnfDocDisplayInfo  the optEnfDocDisplayInfo to set
	 */
        public void setOptEnfDocDisplayInfo(String optEnfDocDisplayInfo) {
            this.optEnfDocDisplayInfo = optEnfDocDisplayInfo;
        }
	/**
	 * @return  the optAppDocDpiCode
	 */
        public String getOptAppDocDpiCode() {
            return optAppDocDpiCode;
        }
	/**
	 * @param optAppDocDpiCode  the optAppDocDpiCode to set
	 */
        public void setOptAppDocDpiCode(String optAppDocDpiCode) {
            this.optAppDocDpiCode = optAppDocDpiCode;
        }
	/**
	 * @return  the optEnfDocDpiCode
	 */
        public String getOptEnfDocDpiCode() {
            return optEnfDocDpiCode;
        }
	/**
	 * @param optEnfDocDpiCode  the optEnfDocDpiCode to set
	 */
        public void setOptEnfDocDpiCode(String optEnfDocDpiCode) {
            this.optEnfDocDpiCode = optEnfDocDpiCode;
        }
	/**
	 * @return  the readingRange
	 */
        public String getReadingRange() {
            return readingRange;
        }
	/**
	 * @param readingRange  the readingRange to set
	 */
        public void setReadingRange(String readingRange) {
            this.readingRange = readingRange;
        }
        
	/**
	 * @return  the exReadingRange
	 */
        public String getExReadingRange() {
            return exReadingRange;
        }
	/**
	 * @param exReadingRange  the exReadingRange to set
	 */
        public void setExReadingRange(String exReadingRange) {
            this.exReadingRange = exReadingRange;
        }
	/**
	 * @return  the registRange
	 */
        public String getRegistRange() {
            return registRange;
        }
	/**
	 * @param registRange  the registRange to set
	 */
        public void setRegistRange(String registRange) {
            this.registRange = registRange;
        }
	/**
	 * @return  the registCurRange
	 */
        public String getRegistCurRange() {
            return registCurRange;
        }
	/**
	 * @param registCurRange  the registCurRange to set
	 */
        public void setRegistCurRange(String registCurRange) {
            this.registCurRange = registCurRange;
        }
	/**
	 * @return  the registSearchTypeYn
	 */
        public String getRegistSearchTypeYn() {
            return registSearchTypeYn;
        }
	/**
	 * @param registSearchTypeYn  the registSearchTypeYn to set
	 */
        public void setRegistSearchTypeYn(String registSearchTypeYn) {
            this.registSearchTypeYn = registSearchTypeYn;
        }
	/**
	 * @return  the registSearchTypeValue
	 */
        public String getRegistSearchTypeValue() {
            return registSearchTypeValue;
        }
	/**
	 * @param registSearchTypeValue  the registSearchTypeValue to set
	 */
        public void setRegistSearchTypeValue(String registSearchTypeValue) {
            this.registSearchTypeValue = registSearchTypeValue;
        }
	/**
	 * @return  the pubPostStdDate
	 */
        public String getPubPostStdDate() {
            return pubPostStdDate;
        }
	/**
	 * @param pubPostStdDate  the pubPostStdDate to set
	 */
        public void setPubPostStdDate(String pubPostStdDate) {
            this.pubPostStdDate = pubPostStdDate;
        }
	/**
	 * @return  the approverRole
	 */
        public String getApproverRole() {
            return approverRole;
        }
	/**
	 * @param approverRole  the approverRole to set
	 */
        public void setApproverRole(String approverRole) {
            this.approverRole = approverRole;
        }
        
	/**
	 * @return  the approverAddRole
	 */
        public String getApproverAddRole() {
            return approverAddRole;
        }
	/**
	 * @param approverAddRole  the approverAddRole to set
	 */
        public void setApproverAddRole(String approverAddRole) {
            this.approverAddRole = approverAddRole;
        }
	/**
	 * @return  the startDocNum
	 */
        public String getStartDocNum() {
            return startDocNum;
        }
	/**
	 * @param startDocNum  the startDocNum to set
	 */
        public void setStartDocNum(String startDocNum) {
            this.startDocNum = startDocNum;
        }
	/**
	 * @return  the endDocNum
	 */
        public String getEndDocNum() {
            return endDocNum;
        }
	/**
	 * @param endDocNum  the endDocNum to set
	 */
        public void setEndDocNum(String endDocNum) {
            this.endDocNum = endDocNum;
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
    	 * @return the bindSendType
    	 */
    	public String getBindSendType() {
    		return bindSendType;
    	}
    	/**
    	 * @param bindSendType the bindSendType to set
    	 */
    	public void setBindSendType(String bindSendType) {
    		this.bindSendType = bindSendType;
    	}
    	/**
    	 * @return the orgBindId
    	 */
    	public String getOrgBindId() {
    		return orgBindId;
    	}
    	/**
    	 * @param orgBindId the orgBindId to set
    	 */
    	public void setOrgBindId(String orgBindId) {
    		this.orgBindId = orgBindId;
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
	 * @return the retentionPeriod
	 */
    	public String getRetentionPeriod() {
    		return retentionPeriod;
    	}
	/**
	 * @param retentionPeriod the retentionPeriod to set
	 */
    	public void setRetentionPeriod(String retentionPeriod) {
    		this.retentionPeriod = retentionPeriod;
    	}
	/**
	 * @return  the searchElecYn
	 */
        public String getSearchElecYn() {
            return searchElecYn;
        }
	/**
	 * @param searchElecYn  the searchElecYn to set
	 */
        public void setSearchElecYn(String searchElecYn) {
            this.searchElecYn = searchElecYn;
        }
	/**
	 * @return  the searchNonElecYn
	 */
        public String getSearchNonElecYn() {
            return searchNonElecYn;
        }
	/**
	 * @param searchNonElecYn  the searchNonElecYn to set
	 */
        public void setSearchNonElecYn(String searchNonElecYn) {
            this.searchNonElecYn = searchNonElecYn;
        }
	/**
	 * @return  the searchDetType
	 */
        public String getSearchDetType() {
            return searchDetType;
        }
	/**
	 * @param searchDetType  the searchDetType to set
	 */
        public void setSearchDetType(String searchDetType) {
            this.searchDetType = searchDetType;
        }
	/**
	 * @return  the searchApprovalName
	 */
        public String getSearchApprovalName() {
            return searchApprovalName;
        }
	/**
	 * @param searchApprovalName  the searchApprovalName to set
	 */
        public void setSearchApprovalName(String searchApprovalName) {
            this.searchApprovalName = searchApprovalName;
        }
	/**
	 * @return  the searchApprTypeApproval
	 */
        public String getSearchApprTypeApproval() {
            return searchApprTypeApproval;
        }
	/**
	 * @param searchApprTypeApproval  the searchApprTypeApproval to set
	 */
        public void setSearchApprTypeApproval(String searchApprTypeApproval) {
            this.searchApprTypeApproval = searchApprTypeApproval;
        }	
	/**
	 * @return  the searchApprTypeExam
	 */
        public String getSearchApprTypeExam() {
            return searchApprTypeExam;
        }
	/**
	 * @param searchApprTypeExam  the searchApprTypeExam to set
	 */
        public void setSearchApprTypeExam(String searchApprTypeExam) {
            this.searchApprTypeExam = searchApprTypeExam;
        }
	/**
	 * @return  the searchApprTypeCoop
	 */
        public String getSearchApprTypeCoop() {
            return searchApprTypeCoop;
        }
	/**
	 * @param searchApprTypeCoop  the searchApprTypeCoop to set
	 */
        public void setSearchApprTypeCoop(String searchApprTypeCoop) {
            this.searchApprTypeCoop = searchApprTypeCoop;
        }
	/**
	 * @return  the searchApprTypeDraft
	 */
        public String getSearchApprTypeDraft() {
            return searchApprTypeDraft;
        }
	/**
	 * @param searchApprTypeDraft  the searchApprTypeDraft to set
	 */
        public void setSearchApprTypeDraft(String searchApprTypeDraft) {
            this.searchApprTypeDraft = searchApprTypeDraft;
        }
	/**
	 * @return  the searchApprTypePreDis
	 */
        public String getSearchApprTypePreDis() {
            return searchApprTypePreDis;
        }
	/**
	 * @param searchApprTypePreDis  the searchApprTypePreDis to set
	 */
        public void setSearchApprTypePreDis(String searchApprTypePreDis) {
            this.searchApprTypePreDis = searchApprTypePreDis;
        }
	/**
	 * @return  the searchApprTypeResponse
	 */
        public String getSearchApprTypeResponse() {
            return searchApprTypeResponse;
        }
	/**
	 * @param searchApprTypeResponse  the searchApprTypeResponse to set
	 */
        public void setSearchApprTypeResponse(String searchApprTypeResponse) {
            this.searchApprTypeResponse = searchApprTypeResponse;
        }
	/**
	 * @return  the searchApprTypeList
	 */
        public String getSearchApprTypeList() {
            return searchApprTypeList;
        }
	/**
	 * @param searchApprTypeList  the searchApprTypeList to set
	 */
        public void setSearchApprTypeList(String searchApprTypeList) {
            this.searchApprTypeList = searchApprTypeList;
        }
	/**
	 * @param detailSearchYn  the detailSearchYn to set
	 */
        public void setDetailSearchYn(String detailSearchYn) {
	    this.detailSearchYn = detailSearchYn;
        }
	/**
	 * @return  the detailSearchYn
	 */
        public String getDetailSearchYn() {
	    return detailSearchYn;
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
	 * @return  the workType
	 */
        public String getWorkType() {
            return workType;
        }
	/**
	 * @param workType  the workType to set
	 */
        public void setWorkType(String workType) {
            this.workType = workType;
        }
	/**
	 * @return  the appDocYn
	 */
        public String getAppDocYn() {
            return appDocYn;
        }
	/**
	 * @param appDocYn  the appDocYn to set
	 */
        public void setAppDocYn(String appDocYn) {
            this.appDocYn = appDocYn;
        }
	/**
	 * @return  the enfDocYn
	 */
        public String getEnfDocYn() {
            return enfDocYn;
        }
	/**
	 * @param enfDocYn  the enfDocYn to set
	 */
        public void setEnfDocYn(String enfDocYn) {
            this.enfDocYn = enfDocYn;
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
	 * @param pageSize  the pageSize to set
	 */
        public void setPageSize(String pageSize) {
	    this.pageSize = pageSize;
        }
	/**
	 * @return  the pageSize
	 */
        public String getPageSize() {
	    return pageSize;
        }
	/**
	 * @param procState  the procState to set
	 */
        public void setProcState(String procState) {
	    this.procState = procState;
        }
	/**
	 * @return  the procState
	 */
        public String getProcState() {
	    return procState;
        }
	/**
	 * @param searchApprTypeAudit  the searchApprTypeAudit to set
	 */
        public void setSearchApprTypeAudit(String searchApprTypeAudit) {
	    this.searchApprTypeAudit = searchApprTypeAudit;
        }
	/**
	 * @return  the searchApprTypeAudit
	 */
        public String getSearchApprTypeAudit() {
	    return searchApprTypeAudit;
        }
	/**
	 * @param searchApprTypeEtc  the searchApprTypeEtc to set
	 */
        public void setSearchApprTypeEtc(String searchApprTypeEtc) {
	    this.searchApprTypeEtc = searchApprTypeEtc;
        }
	/**
	 * @return  the searchApprTypeEtc
	 */
        public String getSearchApprTypeEtc() {
	    return searchApprTypeEtc;
        }
	/**
	 * @param webServiceYn  the webServiceYn to set
	 */
        public void setWebServiceYn(String webServiceYn) {
	    this.webServiceYn = webServiceYn;
        }
	/**
	 * @return  the webServiceYn
	 */
        public String getWebServiceYn() {
	    return webServiceYn;
        }
	/**
	 * @return  the searchAuditOpt009
	 */
        public String getSearchAuditOpt009() {
            return searchAuditOpt009;
        }
	/**
	 * @param searchAuditOpt009  the searchAuditOpt009 to set
	 */
        public void setSearchAuditOpt009(String searchAuditOpt009) {
            this.searchAuditOpt009 = searchAuditOpt009;
        }
	/**
	 * @return  the searchAuditOpt021
	 */
        public String getSearchAuditOpt021() {
            return searchAuditOpt021;
        }
	/**
	 * @param searchAuditOpt021  the searchAuditOpt021 to set
	 */
        public void setSearchAuditOpt021(String searchAuditOpt021) {
            this.searchAuditOpt021 = searchAuditOpt021;
        }
	/**
	 * @return  the searchAuditOpt022
	 */
        public String getSearchAuditOpt022() {
            return searchAuditOpt022;
        }
	/**
	 * @param searchAuditOpt022  the searchAuditOpt022 to set
	 */
        public void setSearchAuditOpt022(String searchAuditOpt022) {
            this.searchAuditOpt022 = searchAuditOpt022;
        }
	/**
	 * @return  the searchAuditOpt023
	 */
        public String getSearchAuditOpt023() {
            return searchAuditOpt023;
        }
	/**
	 * @param searchAuditOpt023  the searchAuditOpt023 to set
	 */
        public void setSearchAuditOpt023(String searchAuditOpt023) {
            this.searchAuditOpt023 = searchAuditOpt023;
        }
	/**
	 * @param docAppCompleteState  the docAppCompleteState to set
	 */
        public void setDocAppCompleteState(String docAppCompleteState) {
	    this.docAppCompleteState = docAppCompleteState;
        }
	/**
	 * @return  the docAppCompleteState
	 */
        public String getDocAppCompleteState() {
	    return docAppCompleteState;
        }
	/**
	 * @param docEnfCompleteState  the docEnfCompleteState to set
	 */
        public void setDocEnfCompleteState(String docEnfCompleteState) {
	    this.docEnfCompleteState = docEnfCompleteState;
        }
	/**
	 * @return  the docEnfCompleteState
	 */
        public String getDocEnfCompleteState() {
	    return docEnfCompleteState;
        }
	/**
	 * @param displayYn  the displayYn to set
	 */
        public void setDisplayYn(String displayYn) {
	    this.displayYn = displayYn;
        }
	/**
	 * @return  the displayYn
	 */
        public String getDisplayYn() {
	    return displayYn;
        }
	/**
	 * @return  the askType2
	 */
        public String getAskType2() {
            return askType2;
        }
	/**
	 * @param askType2  the askType2 to set
	 */
        public void setAskType2(String askType2) {
            this.askType2 = askType2;
        }
	/**
	 * @return  the askType3
	 */
        public String getAskType3() {
            return askType3;
        }
	/**
	 * @param askType3  the askType3 to set
	 */
        public void setAskType3(String askType3) {
            this.askType3 = askType3;
        }
	/**
	 * @return  the askType4
	 */
        public String getAskType4() {
            return askType4;
        }
	/**
	 * @param askType4  the askType4 to set
	 */
        public void setAskType4(String askType4) {
            this.askType4 = askType4;
        }
	/**
	 * @return  the askType5
	 */
        public String getAskType5() {
            return askType5;
        }
	/**
	 * @param askType5  the askType5 to set
	 */
        public void setAskType5(String askType5) {
            this.askType5 = askType5;
        }
	/**
	 * @return  the askType6
	 */
        public String getAskType6() {
            return askType6;
        }
	/**
	 * @param askType6  the askType6 to set
	 */
        public void setAskType6(String askType6) {
            this.askType6 = askType6;
        }
	/**
	 * @return  the askType7
	 */
        public String getAskType7() {
            return askType7;
        }
	/**
	 * @param askType7  the askType7 to set
	 */
        public void setAskType7(String askType7) {
            this.askType7 = askType7;
        }
	/**
	 * @return  the askType8
	 */
        public String getAskType8() {
            return askType8;
        }
	/**
	 * @param askType8  the askType8 to set
	 */
        public void setAskType8(String askType8) {
            this.askType8 = askType8;
        }
	/**
	 * @param askTypeList  the askTypeList to set
	 */
        public void setAskTypeList(String askTypeList) {
	    this.askTypeList = askTypeList;
        }
	/**
	 * @return  the askTypeList
	 */
        public String getAskTypeList() {
	    return askTypeList;
        }
	/**
	 * @param searchWordDeptName  the searchWordDeptName to set
	 */
        public void setSearchWordDeptName(String searchWordDeptName) {
	    this.searchWordDeptName = searchWordDeptName;
        }
	/**
	 * @return  the searchWordDeptName
	 */
        public String getSearchWordDeptName() {
	    return searchWordDeptName;
        }
	/**
	 * @param searchAuditReadY  the searchAuditReadY to set
	 */
        public void setSearchAuditReadY(String searchAuditReadY) {
	    this.searchAuditReadY = searchAuditReadY;
        }
	/**
	 * @return  the searchAuditReadY
	 */
        public String getSearchAuditReadY() {
	    return searchAuditReadY;
        }
	/**
	 * @param searchAuditReadN  the searchAuditReadN to set
	 */
        public void setSearchAuditReadN(String searchAuditReadN) {
	    this.searchAuditReadN = searchAuditReadN;
        }
	/**
	 * @return  the searchAuditReadN
	 */
        public String getSearchAuditReadN() {
	    return searchAuditReadN;
        }
	/**
	 * @param recvObject  the recvObject to set
	 */
        public void setRecvObject(String recvObject) {
	    this.recvObject = recvObject;
        }
	/**
	 * @return  the recvObject
	 */
        public String getRecvObject() {
	    return recvObject;
        }
	/**
	 * @param easyApprSearch  the easyApprSearch to set
	 */
        public void setEasyApprSearch(String easyApprSearch) {
	    this.easyApprSearch = easyApprSearch;
        }
	/**
	 * @return  the easyApprSearch
	 */
        public String getEasyApprSearch() {
	    return easyApprSearch;
        }
	/**
	 * @param easyEnfSearch  the easyEnfSearch to set
	 */
        public void setEasyEnfSearch(String easyEnfSearch) {
	    this.easyEnfSearch = easyEnfSearch;
        }
	/**
	 * @return  the easyEnfSearch
	 */
        public String getEasyEnfSearch() {
	    return easyEnfSearch;
        }
	/**
	 * @param searchLobCode  the searchLobCode to set
	 */
        public void setSearchLobCode(String searchLobCode) {
	    this.searchLobCode = searchLobCode;
        }
	/**
	 * @return  the searchLobCode
	 */
        public String getSearchLobCode() {
	    return searchLobCode;
        }
	/**
	 * @param auditorType  the auditorType to set
	 */
        public void setAuditorType(String auditorType) {
	    this.auditorType = auditorType;
        }
	/**
	 * @return  the auditorType
	 */
        public String getAuditorType() {
	    return auditorType;
        }
	/**
	 * @param sealRegistExdYn  the sealRegistExdYn to set
	 */
        public void setSealRegistExdYn(String sealRegistExdYn) {
	    this.sealRegistExdYn = sealRegistExdYn;
        }
	/**
	 * @return  the sealRegistExdYn
	 */
        public String getSealRegistExdYn() {
	    return sealRegistExdYn;
        }
	/**
	 * @param searchDeptCategory  the searchDeptCategory to set
	 */
        public void setSearchDeptCategory(String searchDeptCategory) {
	    this.searchDeptCategory = searchDeptCategory;
        }
	/**
	 * @return  the searchDeptCategory
	 */
        public String getSearchDeptCategory() {
	    return searchDeptCategory;
        }
	/**
	 * @param searchSerialNumber  the searchSerialNumber to set
	 */
        public void setSearchSerialNumber(String searchSerialNumber) {
	    this.searchSerialNumber = searchSerialNumber;
        }
	/**
	 * @return  the searchSerialNumber
	 */
        public String getSearchSerialNumber() {
	    return searchSerialNumber;
        }
	/**
	 * @param chiefId  the chiefId to set
	 */
        public void setChiefId(String chiefId) {
	    this.chiefId = chiefId;
        }
	/**
	 * @return  the chiefId
	 */
        public String getChiefId() {
	    return chiefId;
        }
	/**
	 * @param dailyAuditLineChkYn  the dailyAuditLineChkYn to set
	 */
        public void setDailyAuditLineChkYn(String dailyAuditLineChkYn) {
	    this.dailyAuditLineChkYn = dailyAuditLineChkYn;
        }
	/**
	 * @return  the dailyAuditLineChkYn
	 */
        public String getDailyAuditLineChkYn() {
	    return dailyAuditLineChkYn;
        }
	/**
	 * @param searchApprTypeAgree  the searchApprTypeAgree to set
	 */
	public void setSearchApprTypeAgree(String searchApprTypeAgree) {
		this.searchApprTypeAgree = searchApprTypeAgree;
	}
	/**
	 * @return  the searchApprTypeAgree
	 */
	public String getSearchApprTypeAgree() {
		return searchApprTypeAgree;
	}
	/**
	 * @return the searchAuthDeptId
	 */
	public String getSearchAuthDeptId() {
		return searchAuthDeptId;
	}
	/**
	 * @param searchAuthDeptId the searchAuthDeptId to set
	 */
	public void setSearchAuthDeptId(String searchAuthDeptId) {
		this.searchAuthDeptId = searchAuthDeptId;
	}
	/**
	 * @return the searchAuthDeptName
	 */
	public String getSearchAuthDeptName() {
		return searchAuthDeptName;
	}
	/**
	 * @param searchAuthDeptName the searchAuthDeptName to set
	 */
	public void setSearchAuthDeptName(String searchAuthDeptName) {
		this.searchAuthDeptName = searchAuthDeptName;
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
}

