package com.sds.acube.app.env.vo;

/**
 * Class Name  : SenderTitleVO.java <br> Description : 발신명의 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  SenderTitleVO
 */ 
public class SenderTitleVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 부서ID
	 */
	private String deptId;
	/**
	 * 발신명의
	 */
	private String senderTitle;
	/**
	 * 발신명의순서
	 */
	private int senderTitleOrder = 0;
	/**
	 * 부서IDs
	 */
	private String deptIds;
	/**
	 * 그룹사용구분
	 */
	private String groupType;
	/**
	 * 기본여부
	 */
	private String defaultYn;
	/**
	 * 발신명의ID
	 */
	private String senderTitleId;
	
	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 리소스 Id
	 */
	private String resourceId;
	
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
	 * @return  the senderTitle
	 */
	public String getSenderTitle() {
		return (senderTitle == null) ? "" : senderTitle;
	}
	/**
	 * @param senderTitle  the senderTitle to set
	 */
	public void setSenderTitle(String senderTitle) {
	    this.senderTitle = (senderTitle == null) ? "" : senderTitle;
	}
	/**
	 * @return  the senderTitleOrder
	 */
	public int getSenderTitleOrder() {
		return senderTitleOrder;
	}
	/**
	 * @param senderTitleOrder  the senderTitleOrder to set
	 */
	public void setSenderTitleOrder(int senderTitleOrder) {
		this.senderTitleOrder = senderTitleOrder;
	}
	/**
	 * @return  the defaultYn
	 */
	public String getDefaultYn() {
		return defaultYn;
	}
	/**
	 * @param defaultYn  the defaultYn to set
	 */
	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}
	/**
	 * @return  the deptIds
	 */
        public String getDeptIds() {
            return deptIds;
        }
	/**
	 * @param deptIds  the deptIds to set
	 */
        public void setDeptIds(String deptIds) {
            this.deptIds = deptIds;
        }
	/**
	 * @return  the groupType
	 */
        public String getGroupType() {
            return groupType;
        }
	/**
	 * @param groupType  the groupType to set
	 */
        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }
	/**
	 * @return  the senderTitleId
	 */
        public String getSenderTitleId() {
            return senderTitleId;
        }
	/**
	 * @param senderTitleId  the senderTitleId to set
	 */
        public void setSenderTitleId(String senderTitleId) {
            this.senderTitleId = senderTitleId;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SenderTitleVO [compId=" + compId + ", defaultYn=" + defaultYn
				+ ", deptId=" + deptId + ", deptIds=" + deptIds
				+ ", groupType=" + groupType + ", langType=" + langType
				+ ", resourceId=" + resourceId + ", senderTitle=" + senderTitle
				+ ", senderTitleId=" + senderTitleId + ", senderTitleOrder="
				+ senderTitleOrder + "]";
	}
	
}
