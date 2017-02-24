package com.sds.acube.app.env.vo;

/**
 * Class Name  : LineUserVO.java <br> Description : 결재자 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  LineUserVO
 */ 
public class LineUserVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 보고경로그룹ID
	 */
	private String lineGroupId;
	/**
	 * 경로순서
	 */
	private int lineOrder = 0;
	/**
	 * 경로하위순서
	 */
	private int lineSubOrder = 0;
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
	 * 결재라인정보 변경여부
	 */
	private String changeYn;
	/**
	 * 권한
	 */
	private String roleCodes;
	
	
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
	 * @return  the lineGroupId
	 */
	public String getLineGroupId() {
		return lineGroupId;
	}
	/**
	 * @param lineGroupId  the lineGroupId to set
	 */
	public void setLineGroupId(String lineGroupId) {
		this.lineGroupId = lineGroupId;
	}
	/**
	 * @return  the lineOrder
	 */
	public int getLineOrder() {
		return lineOrder;
	}
	/**
	 * @param lineOrder  the lineOrder to set
	 */
	public void setLineOrder(int lineOrder) {
		this.lineOrder = lineOrder;
	}
	/**
	 * @return  the lineSubOrder
	 */
	public int getLineSubOrder() {
		return lineSubOrder;
	}
	/**
	 * @param lineSubOrder  the lineSubOrder to set
	 */
	public void setLineSubOrder(int lineSubOrder) {
		this.lineSubOrder = lineSubOrder;
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
	 * @return  the changeYn
	 */
        public String getChangeYn() {
            return changeYn;
        }
	/**
	 * @param changeYn  the changeYn to set
	 */
        public void setChangeYn(String changeYn) {
            this.changeYn = changeYn;
        }
	/**
	 * @return  the roleCodes
	 */
        public String getRoleCodes() {
            return roleCodes;
        }
	/**
	 * @param roleCodes  the roleCodes to set
	 */
        public void setRoleCodes(String roleCodes) {
            this.roleCodes = roleCodes;
        }
	

}
