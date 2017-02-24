package com.sds.acube.app.common.vo;

/**
 * Class Name  : DrmParamVO.java <br> Description : DRM Parameter VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 7. 8 
 * @version  1.0 
 * @see  DrmParamVO
 */ 
public class DrmParamVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 사용자ID
	 */
	private String userId;
	/**
	 * DRM적용여부
	 */
	private String applyYN;
	
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
	 * @return  the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId  the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return  the applyYN
	 */
        public String getApplyYN() {
            return applyYN;
        }
	/**
	 * @param applyYN  the applyYN to set
	 */
        public void setApplyYN(String applyYN) {
            this.applyYN = applyYN;
        }
}
