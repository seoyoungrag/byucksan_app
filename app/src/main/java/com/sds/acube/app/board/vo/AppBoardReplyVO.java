/**
 * 
 */
package com.sds.acube.app.board.vo;

/**
 * Class Name  : AppBoardReplyVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 3. <br> 수 정  자 : sdspc2313564  <br> 수정내용 :  <br>
 * @author   곽경종 
 * @since  2012. 5. 3.
 * @version  1.0 
 * @see  com.sds.acube.app.board.vo.AppBoardReplyVO.java
 */

public class AppBoardReplyVO {
	
	/**
	 */
	private String recontents;      //댓글내용
	/**
	 */
	private String compId;	  	 	//회사ID
	/**
	 */
	private String bullId;	  		//게시물ID
	/**
	 */
	private String regId;	  		//등록자ID
    /**
	 */
    private String regName;	  	    //등록자명
    /**
	 */
    private String regDeptId;	  	//등록자부서ID
    /**
	 */
    private String regDeptName; 	//등록자부서명
    /**
	 */
    private String regDate;	  	    //등록일시
    /**
	 */
    private String replyId;         //댓글ID
    
    
    
	/**
	 * @return  the replyId
	 */
	public String getReplyId() {
		return replyId;
	}
	/**
	 * @param replyId  the replyId to set
	 */
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	/**
	 * @return  the recontents
	 */
	public String getRecontents() {
		return recontents;
	}
	/**
	 * @param recontents  the recontents to set
	 */
	public void setRecontents(String recontents) {
		this.recontents = recontents;
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
	 * @return  the bullId
	 */
	public String getBullId() {
		return bullId;
	}
	/**
	 * @param bullId  the bullId to set
	 */
	public void setBullId(String bullId) {
		this.bullId = bullId;
	}
	/**
	 * @return  the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId  the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return  the regName
	 */
	public String getRegName() {
		return regName;
	}
	/**
	 * @param regName  the regName to set
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}
	/**
	 * @return  the regDeptId
	 */
	public String getRegDeptId() {
		return regDeptId;
	}
	/**
	 * @param regDeptId  the regDeptId to set
	 */
	public void setRegDeptId(String regDeptId) {
		this.regDeptId = regDeptId;
	}
	/**
	 * @return  the regDeptName
	 */
	public String getRegDeptName() {
		return regDeptName;
	}
	/**
	 * @param regDeptName  the regDeptName to set
	 */
	public void setRegDeptName(String regDeptName) {
		this.regDeptName = regDeptName;
	}
	/**
	 * @return  the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate  the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

    
    
}
