package com.sds.acube.app.relay.vo;

/**
 * Class Name  : ApprovalInfoVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 28. <br> 수 정  자 : 김상태  <br> 수정내용 :  <br>
 * @author   김상태 
 * @since  2012. 3. 28.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.ApprovalInfoVO.java
 */

public class LineInfoVO {
	
	// 경로구분
	/**
	 */
	private String part;

	// 경로순서
	/**
	 */
	private String lineOrder;
	
	// 결재, 대리 직위
	/**
	 */
	private String signposition = "";
	
	// 결제 유형
	/**
	 */
	private String type = "";
	
	// 문자서명
	/**
	 */
	private String name = "";
	
	// 서명이미지명
	/**
	 */
	private String signimage = "";
	
	// 처리일자
	/**
	 */
	private String pdate = "";
	
	// 처리시간
	/**
	 */
	private String ptime = "";
	
	/**
	 * @return  the order
	 */
	public String getPart() {
		return part;
	}

	/**
	 * @param order  the order to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
	
	/**
	 * @return  the order
	 */
	public String getLineOrder() {
		return lineOrder;
	}

	/**
	 * @param order  the order to set
	 */
	public void setLineOrder(String lineOrder) {
		this.lineOrder = lineOrder;
	}

	/**
	 * @return  the signposition
	 */
	public String getSignposition() {
		return signposition;
	}

	/**
	 * @param signposition  the signposition to set
	 */
	public void setSignposition(String signposition) {
		this.signposition = signposition;
	}

	/**
	 * @return  the typ
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param typ  the typ to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return  the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name  the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return  the signimage
	 */
	public String getSignimage() {
		return signimage;
	}

	/**
	 * @param signimage  the signimage to set
	 */
	public void setSignimage(String signimage) {
		this.signimage = signimage;
	}

	/**
	 * @return  the date
	 */
	public String getPdate() {
		return pdate;
	}

	/**
	 * @param date  the date to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	/**
	 * @return  the time
	 */
	public String getPtime() {
		return ptime;
	}

	/**
	 * @param time  the time to set
	 */
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
}
