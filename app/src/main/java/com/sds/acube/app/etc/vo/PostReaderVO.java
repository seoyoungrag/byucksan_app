package com.sds.acube.app.etc.vo;

/**
 * Class Name  : PostReaderVO.java <br> Description : 열람자 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PostReaderVO
 */ 
public class PostReaderVO {

	/**
	 * 게시물ID
	 */
	private String publishId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 열람자ID
	 */
	private String readerId;
	/**
	 * 열람자명
	 */
	private String readerName;
	/**
	 * 열람자직위
	 */
	private String readerPos;
	/**
	 * 열람자부서ID
	 */
	private String readerDeptId;
	/**
	 * 열람자부서명
	 */
	private String readerDeptName;
	/**
	 * 열람일자
	 */
	private String readDate = "9999-12-31 23:59:59";
	
	
	/**
	 * @return  the publishId
	 */
	public String getPublishId() {
		return publishId;
	}
	/**
	 * @param publishId  the publishId to set
	 */
	public void setPublishId(String publishId) {
		this.publishId = publishId;
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
	 * @return  the readerId
	 */
	public String getReaderId() {
		return readerId;
	}
	/**
	 * @param readerId  the readerId to set
	 */
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	/**
	 * @return  the readerName
	 */
	public String getReaderName() {
		return readerName;
	}
	/**
	 * @param readerName  the readerName to set
	 */
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	/**
	 * @return  the readerPos
	 */
	public String getReaderPos() {
		return readerPos;
	}
	/**
	 * @param readerPos  the readerPos to set
	 */
	public void setReaderPos(String readerPos) {
		this.readerPos = readerPos;
	}
	/**
	 * @return  the readerDeptId
	 */
	public String getReaderDeptId() {
		return readerDeptId;
	}
	/**
	 * @param readerDeptId  the readerDeptId to set
	 */
	public void setReaderDeptId(String readerDeptId) {
		this.readerDeptId = readerDeptId;
	}
	/**
	 * @return  the readerDeptName
	 */
	public String getReaderDeptName() {
		return readerDeptName;
	}
	/**
	 * @param readerDeptName  the readerDeptName to set
	 */
	public void setReaderDeptName(String readerDeptName) {
		this.readerDeptName = readerDeptName;
	}
	/**
	 * @return  the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate  the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

}
