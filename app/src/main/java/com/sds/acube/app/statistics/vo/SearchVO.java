package com.sds.acube.app.statistics.vo;


/**
 * Class Name  : SearchVO <br> Description : 검색 VO 클래스 <br> Modification Information <br> <br> 수 정 일 :   <br> 수 정 자 :   <br> 수정내용 :  <br>
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
	 * 회사ID
	 */
	private String compId;
	/**
	 * 부서구분 여부
	 */
	private String deptYn;
	/**
	 * 기안코드
	 */
	private String art010;
	/**
	 * 검토코드
	 */
	private String art020;
	/**
	 * 검토(주관부서)코드
	 */
	private String art021;
	/**
	 * 협조 코드
	 */
	private String art030;
	/**
	 * 병렬협조 코드
	 */
	private String art031;
	/**
	 * 부서협조 코드
	 */
	private String art032;
	/**
	 * 협조(기안) 코드
	 */
	private String art033;
	/**
	 * 협조(검토) 코드
	 */
	private String art034;
	/**
	 * 협조(결재) 코드
	 */
	private String art035;
	
	 // jth8172 2012 신결재 TF
	/**
	 * 협조 코드
	 */
	private String art130;
	/**
	 * 병렬협조 코드
	 */
	private String art131;
	/**
	 * 부서협조 코드
	 */
	private String art132;
	/**
	 * 협조(기안) 코드
	 */
	private String art133;
	/**
	 * 협조(검토) 코드
	 */
	private String art134;
	/**
	 * 협조(결재) 코드
	 */
	private String art135;

	/**
	 * 감사 코드
	 */
	private String art040;
	/**
	 * 부서감사 코드
	 */
	private String art041;
	/**
	 * 감사(기안) 코드
	 */
	private String art042;
	/**
	 * 감사(검토) 코드
	 */
	private String art043;
	/**
	 * 감사(결재) 코드
	 */
	private String art044;
	/**
	 * 일상감사 코드
	 */
	private String art045;
	/**
	 * 준법감시 코드
	 */
	private String art046;
	/**
	 * 감사위원 코드
	 */
	private String art047;
	/**
	 * 결재 코드
	 */
	private String art050;
	/**
	 * 전결 코드
	 */
	private String art051;
	/**
	 * 대결 코드
	 */
	private String art052;
	/**
	 * 1인전결 코드
	 */
	private String art053;
	/**
	 * 후열 코드
	 */
	private String art054;
	/**
	 * 통보 코드 // jth8172 2012 신결재 TF
	 */
	private String art055;
	/**
	 * 선람 코드
	 */
	private String art060;
	/**
	 * 담당 코드
	 */
	private String art070;
	/**
	 * 처리유형
	 */
	private String procType;
	/**
	 * 부서없음 처리 메세지
	 */
	private String replaceDbDept;
	/**
	 * 사용자없음 처리 메세지
	 */
	private String replaceDbPerson;
	/**
	 * 검색 부서아이디
	 */
	private String searchDeptId;
	/**
	 * 검색 부서명
	 */
	private String searchDeptName;
	/**
	 * 시행유형 - 내부 코드
	 */
	private String det001;
	/**
	 * 시행유형 - 대내 코드
	 */
	private String det002;
	/**
	 * 시행유형 - 대외 코드
	 */
	private String det003;
	/**
	 * 시행유형 - 대내외 코드
	 */
	private String det004;
	/**
	 * 생산문서 문서상태 코드
	 */
	private String docAppState;
	/**
	 * 생산문서 문서상태 코드  추가
	 */
	private String docAppStateAdd;
	/**
	 * 접수문서 문서상태 코드
	 */
	private String docEnfState;
	/**
	 * 접수문서 문서상태 코드  추가
	 */
	private String docEnfStateAdd;
	/**
	 * 날인코드 ALL
	 */
	private String sptAll;
	/**
	 * 직인 코드
	 */
	private String spt001;
	/**
	 * 서명인코드
	 */
	private String spt002;
	/**
	 * 감사직인코드
	 */
	private String spt005;
	/**
	 * 문서대장코드
	 */
	private String lol001;
	/**
	 * 배부대장코드
	 */
	private String lol002;
	/**
	 * 미등록대장코드
	 */
	private String lol003;
	/**
	 * 서명인날인대장코드
	 */
	private String lol004;
	/**
	 * 직인날인대장코드
	 */
	private String lol005;
	/**
	 * 감사직인날인대장코드
	 */
	private String lol008;	
	/**
	 * 협조관련 코드 사용여부
	 */
	private String opt003GYn;
	/**
	 * 감사관련 코드 사용여부
	 */
	private String opt009GYn;
	/**
	 * 선람 코드 사용여부
	 */
	private String opt019GYn;
	/**
	 * 등록대장 사용여부
	 */
	private String opt201Yn;
	/**
	 * 배부대장 사용여부
	 */
	private String opt202Yn;
	/**
	 * 미등록대장 사용여부
	 */
	private String opt203Yn;
	/**
	 * 서명인 날인 대장 사용여부
	 */
	private String opt204Yn;
	/**
	 * 직인날인대장 사용여부
	 */
	private String opt205Yn;
	/**
	 * 감사직인날인대장 사용여부
	 */
	private String opt208Yn;
	/**
	 * 합의관련 코드 사용여부
	 */
	private String opt053GYn;
	/** 
	 * 리스트 타입 
	 **/
	private String listType;	
	/** 
	 * 문서 타입(전체/생산/접수) */
	private String searchDocType;
	/** 
	 * 검색어 */
	private String searchWord;
	/** 
	 * 생산문서 처리자 처리코드 */
	private String processorProcType;
	/** 
	 * 접수문서 경로지정상태코드 */
	private String docEnfDisplayWaitState;
	/** 
	 * 처리요청유형 */
	private String askType;
	/** 
	 * 검색 사용자아이디 */
	private String searchUserId;
	/** 
	 * 검색 사용자명 */
	private String searchUserName;
	/** 
	 * 검색 사용자직위명 */
	private String searchUserPos;

	
	
	
	
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
	 * @return  the deptYn
	 */
        public String getDeptYn() {
            return deptYn;
        }
	/**
	 * @param deptYn  the deptYn to set
	 */
        public void setDeptYn(String deptYn) {
            this.deptYn = deptYn;
        }
	/**
	 * @return  the art010
	 */
        public String getArt010() {
            return art010;
        }
	/**
	 * @param art010  the art010 to set
	 */
        public void setArt010(String art010) {
            this.art010 = art010;
        }
	/**
	 * @return  the art020
	 */
        public String getArt020() {
            return art020;
        }
	/**
	 * @param art020  the art020 to set
	 */
        public void setArt020(String art020) {
            this.art020 = art020;
        }
	/**
	 * @return  the art021
	 */
        public String getArt021() {
            return art021;
        }
	/**
	 * @param art021  the art021 to set
	 */
        public void setArt021(String art021) {
            this.art021 = art021;
        }
	/**
	 * @return  the art030
	 */
        public String getArt030() {
            return art030;
        }
	/**
	 * @param art030  the art030 to set
	 */
        public void setArt030(String art030) {
            this.art030 = art030;
        }
	/**
	 * @return  the art031
	 */
        public String getArt031() {
            return art031;
        }
	/**
	 * @param art031  the art031 to set
	 */
        public void setArt031(String art031) {
            this.art031 = art031;
        }
	/**
	 * @return  the art032
	 */
        public String getArt032() {
            return art032;
        }
	/**
	 * @param art032  the art032 to set
	 */
        public void setArt032(String art032) {
            this.art032 = art032;
        }
	/**
	 * @return  the art033
	 */
        public String getArt033() {
            return art033;
        }
	/**
	 * @param art033  the art033 to set
	 */
        public void setArt033(String art033) {
            this.art033 = art033;
        }
	/**
	 * @return  the art034
	 */
        public String getArt034() {
            return art034;
        }
	/**
	 * @param art034  the art034 to set
	 */
        public void setArt034(String art034) {
            this.art034 = art034;
        }
	/**
	 * @return  the art035
	 */
        public String getArt035() {
            return art035;
        }
	/**
	 * @param art035  the art035 to set
	 */
        public void setArt035(String art035) {
            this.art035 = art035;
        }
	/**
	 * @return  the art040
	 */
        public String getArt040() {
            return art040;
        }
	/**
	 * @param art040  the art040 to set
	 */
        public void setArt040(String art040) {
            this.art040 = art040;
        }
	/**
	 * @return  the art041
	 */
        public String getArt041() {
            return art041;
        }
	/**
	 * @param art041  the art041 to set
	 */
        public void setArt041(String art041) {
            this.art041 = art041;
        }
	/**
	 * @return  the art042
	 */
        public String getArt042() {
            return art042;
        }
	/**
	 * @param art042  the art042 to set
	 */
        public void setArt042(String art042) {
            this.art042 = art042;
        }
	/**
	 * @return  the art043
	 */
        public String getArt043() {
            return art043;
        }
	/**
	 * @param art043  the art043 to set
	 */
        public void setArt043(String art043) {
            this.art043 = art043;
        }
	/**
	 * @return  the art044
	 */
        public String getArt044() {
            return art044;
        }
	/**
	 * @param art044  the art044 to set
	 */
        public void setArt044(String art044) {
            this.art044 = art044;
        }        
	/**
	 * @return  the art045
	 */
        public String getArt045() {
            return art045;
        }
	/**
	 * @param art045  the art045 to set
	 */
        public void setArt045(String art045) {
            this.art045 = art045;
        }
	/**
	 * @return  the art046
	 */
        public String getArt046() {
            return art046;
        }
	/**
	 * @param art046  the art046 to set
	 */
        public void setArt046(String art046) {
            this.art046 = art046;
        }
	/**
	 * @return  the art047
	 */
        public String getArt047() {
            return art047;
        }
	/**
	 * @param art047  the art047 to set
	 */
        public void setArt047(String art047) {
            this.art047 = art047;
        }
	/**
	 * @return  the art050
	 */
        public String getArt050() {
            return art050;
        }
	/**
	 * @param art050  the art050 to set
	 */
        public void setArt050(String art050) {
            this.art050 = art050;
        }
	/**
	 * @return  the art051
	 */
        public String getArt051() {
            return art051;
        }
	/**
	 * @param art051  the art051 to set
	 */
        public void setArt051(String art051) {
            this.art051 = art051;
        }
	/**
	 * @return  the art052
	 */
        public String getArt052() {
            return art052;
        }
	/**
	 * @param art052  the art052 to set
	 */
        public void setArt052(String art052) {
            this.art052 = art052;
        }
	/**
	 * @return  the art053
	 */
        public String getArt053() {
            return art053;
        }
	/**
	 * @param art053  the art053 to set
	 */
        public void setArt053(String art053) {
            this.art053 = art053;
        }
	/**
	 * @return  the art054
	 */
        public String getArt054() {
            return art054;
        }
	/**
	 * @param art054  the art054 to set
	 */
        public void setArt054(String art054) {
            this.art054 = art054;
        }
	/**
	 * @return  the art060
	 */
        public String getArt060() {
            return art060;
        }
	/**
	 * @param art060  the art060 to set
	 */
        public void setArt060(String art060) {
            this.art060 = art060;
        }
	/**
	 * @return  the art070
	 */
        public String getArt070() {
            return art070;
        }
	/**
	 * @param art070  the art070 to set
	 */
        public void setArt070(String art070) {
            this.art070 = art070;
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
	 * @return  the replaceDbDept
	 */
        public String getReplaceDbDept() {
            return replaceDbDept;
        }
	/**
	 * @param replaceDbDept  the replaceDbDept to set
	 */
        public void setReplaceDbDept(String replaceDbDept) {
            this.replaceDbDept = replaceDbDept;
        }
	/**
	 * @return  the replaceDbPerson
	 */
        public String getReplaceDbPerson() {
            return replaceDbPerson;
        }
	/**
	 * @param replaceDbPerson  the replaceDbPerson to set
	 */
        public void setReplaceDbPerson(String replaceDbPerson) {
            this.replaceDbPerson = replaceDbPerson;
        }
	/**
	 * @return  the searchDeptId
	 */
        public String getSearchDeptId() {
            return searchDeptId;
        }
	/**
	 * @param searchDeptId  the searchDeptId to set
	 */
        public void setSearchDeptId(String searchDeptId) {
            this.searchDeptId = searchDeptId;
        }
	/**
	 * @return  the searchDeptName
	 */
        public String getSearchDeptName() {
            return searchDeptName;
        }
	/**
	 * @param searchDeptName  the searchDeptName to set
	 */
        public void setSearchDeptName(String searchDeptName) {
            this.searchDeptName = searchDeptName;
        }
	/**
	 * @return  the det001
	 */
        public String getDet001() {
            return det001;
        }
	/**
	 * @param det001  the det001 to set
	 */
        public void setDet001(String det001) {
            this.det001 = det001;
        }
	/**
	 * @return  the det002
	 */
        public String getDet002() {
            return det002;
        }
	/**
	 * @param det002  the det002 to set
	 */
        public void setDet002(String det002) {
            this.det002 = det002;
        }
	/**
	 * @return  the det003
	 */
        public String getDet003() {
            return det003;
        }
	/**
	 * @param det003  the det003 to set
	 */
        public void setDet003(String det003) {
            this.det003 = det003;
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
	 * @return  the docAppStateAdd
	 */
        public String getDocAppStateAdd() {
            return docAppStateAdd;
        }
	/**
	 * @param docAppStateAdd  the docAppStateAdd to set
	 */
        public void setDocAppStateAdd(String docAppStateAdd) {
            this.docAppStateAdd = docAppStateAdd;
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
	 * @return  the docEnfStateAdd
	 */
        public String getDocEnfStateAdd() {
            return docEnfStateAdd;
        }
	/**
	 * @param docEnfStateAdd  the docEnfStateAdd to set
	 */
        public void setDocEnfStateAdd(String docEnfStateAdd) {
            this.docEnfStateAdd = docEnfStateAdd;
        }
	/**
	 * @param det004  the det004 to set
	 */
        public void setDet004(String det004) {
	    this.det004 = det004;
        }
	/**
	 * @return  the det004
	 */
        public String getDet004() {
	    return det004;
        }
	/**
	 * @return  the sptAll
	 */
        public String getSptAll() {
            return sptAll;
        }
	/**
	 * @param sptAll  the sptAll to set
	 */
        public void setSptAll(String sptAll) {
            this.sptAll = sptAll;
        }
	/**
	 * @return  the spt001
	 */
        public String getSpt001() {
            return spt001;
        }
	/**
	 * @param spt001  the spt001 to set
	 */
        public void setSpt001(String spt001) {
            this.spt001 = spt001;
        }
	/**
	 * @return  the spt002
	 */
        public String getSpt002() {
            return spt002;
        }
	/**
	 * @param spt002  the spt002 to set
	 */
        public void setSpt002(String spt002) {
            this.spt002 = spt002;
        }
	/**
	 * @return  the spt005
	 */
        public String getSpt005() {
            return spt005;
        }
	/**
	 * @param spt005  the spt005 to set
	 */
        public void setSpt005(String spt005) {
            this.spt005 = spt005;
        }
	/**
	 * @return  the lol001
	 */
        public String getLol001() {
            return lol001;
        }
	/**
	 * @param lol001  the lol001 to set
	 */
        public void setLol001(String lol001) {
            this.lol001 = lol001;
        }
	/**
	 * @return  the lol002
	 */
        public String getLol002() {
            return lol002;
        }
	/**
	 * @param lol002  the lol002 to set
	 */
        public void setLol002(String lol002) {
            this.lol002 = lol002;
        }
	/**
	 * @return  the lol003
	 */
        public String getLol003() {
            return lol003;
        }
	/**
	 * @param lol003  the lol003 to set
	 */
        public void setLol003(String lol003) {
            this.lol003 = lol003;
        }
	/**
	 * @return  the lol004
	 */
        public String getLol004() {
            return lol004;
        }
	/**
	 * @param lol004  the lol004 to set
	 */
        public void setLol004(String lol004) {
            this.lol004 = lol004;
        }
	/**
	 * @return  the lol005
	 */
        public String getLol005() {
            return lol005;
        }
	/**
	 * @param lol005  the lol005 to set
	 */
        public void setLol005(String lol005) {
            this.lol005 = lol005;
        }
	/**
	 * @return  the lol008
	 */
        public String getLol008() {
            return lol008;
        }
	/**
	 * @param lol008  the lol008 to set
	 */
        public void setLol008(String lol008) {
            this.lol008 = lol008;
        }
	/**
	 * @param opt003GYn  the opt003GYn to set
	 */
        public void setOpt003GYn(String opt003GYn) {
	    this.opt003GYn = opt003GYn;
        }
	/**
	 * @return  the opt003GYn
	 */
        public String getOpt003GYn() {
	    return opt003GYn;
        }
        /**
		 * @return  the opt009GYn
		 */
        public String getOpt009GYn() {
            return opt009GYn;
        }
	/**
	 * @param opt009gYn  the opt009GYn to set
	 */
        public void setOpt009GYn(String opt009gYn) {
            opt009GYn = opt009gYn;
        }
	/**
	 * @return  the opt019GYn
	 */
        public String getOpt019GYn() {
            return opt019GYn;
        }
	/**
	 * @param opt019gYn  the opt019GYn to set
	 */
        public void setOpt019GYn(String opt019gYn) {
            opt019GYn = opt019gYn;
        }
	/**
	 * @return  the opt201Yn
	 */
        public String getOpt201Yn() {
            return opt201Yn;
        }
	/**
	 * @param opt201Yn  the opt201Yn to set
	 */
        public void setOpt201Yn(String opt201Yn) {
            this.opt201Yn = opt201Yn;
        }
	/**
	 * @return  the opt202Yn
	 */
        public String getOpt202Yn() {
            return opt202Yn;
        }
	/**
	 * @param opt202Yn  the opt202Yn to set
	 */
        public void setOpt202Yn(String opt202Yn) {
            this.opt202Yn = opt202Yn;
        }
	/**
	 * @return  the opt203Yn
	 */
        public String getOpt203Yn() {
            return opt203Yn;
        }
	/**
	 * @param opt203Yn  the opt203Yn to set
	 */
        public void setOpt203Yn(String opt203Yn) {
            this.opt203Yn = opt203Yn;
        }
	/**
	 * @return  the opt204Yn
	 */
        public String getOpt204Yn() {
            return opt204Yn;
        }
	/**
	 * @param opt204Yn  the opt204Yn to set
	 */
        public void setOpt204Yn(String opt204Yn) {
            this.opt204Yn = opt204Yn;
        }
	/**
	 * @return  the opt205Yn
	 */
        public String getOpt205Yn() {
            return opt205Yn;
        }
	/**
	 * @param opt205Yn  the opt205Yn to set
	 */
        public void setOpt205Yn(String opt205Yn) {
            this.opt205Yn = opt205Yn;
        }
	/**
	 * @return  the opt208Yn
	 */
        public String getOpt208Yn() {
            return opt208Yn;
        }
	/**
	 * @param opt208Yn  the opt208Yn to set
	 */
        public void setOpt208Yn(String opt208Yn) {
            this.opt208Yn = opt208Yn;
        }
	/**
	 * @return  the art130
	 */
	public String getArt130() {
		return art130;
	}
	/**
	 * @param art130  the art130 to set
	 */
	public void setArt130(String art130) {
		this.art130 = art130;
	}
	/**
	 * @return  the art131
	 */
	public String getArt131() {
		return art131;
	}
	/**
	 * @param art131  the art131 to set
	 */
	public void setArt131(String art131) {
		this.art131 = art131;
	}
	/**
	 * @return  the art132
	 */
	public String getArt132() {
		return art132;
	}
	/**
	 * @param art132  the art132 to set
	 */
	public void setArt132(String art132) {
		this.art132 = art132;
	}
	/**
	 * @return  the art133
	 */
	public String getArt133() {
		return art133;
	}
	/**
	 * @param art133  the art133 to set
	 */
	public void setArt133(String art133) {
		this.art133 = art133;
	}
	/**
	 * @return  the art134
	 */
	public String getArt134() {
		return art134;
	}
	/**
	 * @param art134  the art134 to set
	 */
	public void setArt134(String art134) {
		this.art134 = art134;
	}
	/**
	 * @return  the art135
	 */
	public String getArt135() {
		return art135;
	}
	/**
	 * @param art135  the art135 to set
	 */
	public void setArt135(String art135) {
		this.art135 = art135;
	}
	/**
	 * @return  the art055
	 */
	public String getArt055() {
		return art055;
	}
	/**
	 * @param art055  the art055 to set
	 */
	public void setArt055(String art055) {
		this.art055 = art055;
	}
	/**
	 * @return  the opt053GYn
	 */
	public String getOpt053GYn() {
		return opt053GYn;
	}
	/**
	 * @param opt053gYn  the opt053GYn to set
	 */
	public void setOpt053GYn(String opt053gYn) {
		opt053GYn = opt053gYn;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	/**
	 * @return the searchDocType
	 */
	public String getSearchDocType() {
		return searchDocType;
	}
	/**
	 * @param searchDocType the searchDocType to set
	 */
	public void setSearchDocType(String searchDocType) {
		this.searchDocType = searchDocType;
	}
	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}
	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	/**
	 * @return the processorProcType
	 */
	public String getProcessorProcType() {
		return processorProcType;
	}
	/**
	 * @param processorProcType the processorProcType to set
	 */
	public void setProcessorProcType(String processorProcType) {
		this.processorProcType = processorProcType;
	}
	/**
	 * @return the docEnfDisplayWaitState
	 */
	public String getDocEnfDisplayWaitState() {
		return docEnfDisplayWaitState;
	}
	/**
	 * @param docEnfDisplayWaitState the docEnfDisplayWaitState to set
	 */
	public void setDocEnfDisplayWaitState(String docEnfDisplayWaitState) {
		this.docEnfDisplayWaitState = docEnfDisplayWaitState;
	}
	/**
	 * @return the askType
	 */
	public String getAskType() {
		return askType;
	}
	/**
	 * @param askType the askType to set
	 */
	public void setAskType(String askType) {
		this.askType = askType;
	}
	/**
	 * @return the searchUserId
	 */
	public String getSearchUserId() {
		return searchUserId;
	}
	/**
	 * @param searchUserId the searchUserId to set
	 */
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}
	/**
	 * @return the searchUserName
	 */
	public String getSearchUserName() {
		return searchUserName;
	}
	/**
	 * @param searchUserName the searchUserName to set
	 */
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}
	/**
	 * @return the searchUserPos
	 */
	public String getSearchUserPos() {
		return searchUserPos;
	}
	/**
	 * @param searchUserPos the searchUserPos to set
	 */
	public void setSearchUserPos(String searchUserPos) {
		this.searchUserPos = searchUserPos;
	}
	
	
}

