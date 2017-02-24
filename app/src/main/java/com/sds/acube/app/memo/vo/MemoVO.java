/**
 * 
 */
package com.sds.acube.app.memo.vo;

/**
 * Class Name  : ContentVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 26. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 3. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.ContentVO.java
 */

public class MemoVO {
	private String memoId                 ;
	private String compId                 ;
	private String title                   ;
	private String contents                ;
	private String senderId               ;
	private String senderName             ;
	private String senderPos              ;
	private String senderDeptId          ;
	private String senderDeptName        ;
	private String createDate             ;
	private String receiverId             ;
	private String receiverName           ;
	private String receiverPos            ;
	private String receiverDeptId        ;
	private String receiverDeptName      ;
	private String readDate               ;
	private String deleteYn               ;
	private int    attachCount            ;
	private int    totalCount            ;
	
	
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the memoId
	 */
	public String getMemoId() {
		return memoId;
	}
	/**
	 * @param memoId the memoId to set
	 */
	public void setMemoId(String memoId) {
		this.memoId = memoId;
	}
	/**
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}
	/**
	 * @param compId the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the senderPos
	 */
	public String getSenderPos() {
		return senderPos;
	}
	/**
	 * @param senderPos the senderPos to set
	 */
	public void setSenderPos(String senderPos) {
		this.senderPos = senderPos;
	}
	/**
	 * @return the senderDeptId
	 */
	public String getSenderDeptId() {
		return senderDeptId;
	}
	/**
	 * @param senderDeptId the senderDeptId to set
	 */
	public void setSenderDeptId(String senderDeptId) {
		this.senderDeptId = senderDeptId;
	}
	/**
	 * @return the senderDeptName
	 */
	public String getSenderDeptName() {
		return senderDeptName;
	}
	/**
	 * @param senderDeptName the senderDeptName to set
	 */
	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the receiverId
	 */
	public String getReceiverId() {
		return receiverId;
	}
	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**
	 * @return the receiverPos
	 */
	public String getReceiverPos() {
		return receiverPos;
	}
	/**
	 * @param receiverPos the receiverPos to set
	 */
	public void setReceiverPos(String receiverPos) {
		this.receiverPos = receiverPos;
	}
	/**
	 * @return the receiverDeptId
	 */
	public String getReceiverDeptId() {
		return receiverDeptId;
	}
	/**
	 * @param receiverDeptId the receiverDeptId to set
	 */
	public void setReceiverDeptId(String receiverDeptId) {
		this.receiverDeptId = receiverDeptId;
	}
	/**
	 * @return the receiverDeptName
	 */
	public String getReceiverDeptName() {
		return receiverDeptName;
	}
	/**
	 * @param receiverDeptName the receiverDeptName to set
	 */
	public void setReceiverDeptName(String receiverDeptName) {
		this.receiverDeptName = receiverDeptName;
	}
	/**
	 * @return the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	/**
	 * @return the deleteYn
	 */
	public String getDeleteYn() {
		return deleteYn;
	}
	/**
	 * @param deleteYn the deleteYn to set
	 */
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	/**
	 * @return the attachCount
	 */
	public int getAttachCount() {
		return attachCount;
	}
	/**
	 * @param attachCount the attachCount to set
	 */
	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}
	
	
}
