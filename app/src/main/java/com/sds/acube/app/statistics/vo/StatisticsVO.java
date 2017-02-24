package com.sds.acube.app.statistics.vo;

/**
 * Class Name  : StatisticsVO.java <br> Description : 통계관련 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  StatisticsVO
 */ 
public class StatisticsVO {
	
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
	 * 기안코드
	 */
	private String art010;
	/**
	 * 검토코드
	 */
	private String art020;
	/**
	 * 협조코드
	 */
	private String art030;
	/**
	 * 합의코드
	 */
	private String art130;
	/**
	 * 검토코드
	 */
	private String art040;
	/**
	 * 결재코드
	 */
	private String art050;
	/**
	 * 선람코드
	 */
	private String art060;
	/**
	 * 담당코드
	 */
	private String art070;
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
	 * 결재진행중
	 */
	private String appIng;
	/**
	 * 결재완료
	 */
	private String appComplete;
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
	 * @return  the appIng
	 */
        public String getAppIng() {
            return appIng;
        }
	/**
	 * @param appIng  the appIng to set
	 */
        public void setAppIng(String appIng) {
            this.appIng = appIng;
        }
	/**
	 * @return  the appComplete
	 */
        public String getAppComplete() {
            return appComplete;
        }
	/**
	 * @param appComplete  the appComplete to set
	 */
        public void setAppComplete(String appComplete) {
            this.appComplete = appComplete;
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
        

}
