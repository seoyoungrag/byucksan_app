package com.sds.acube.app.etc.vo;


/**
 * Class Name  : PubPostVO.java <br> Description : 공람게시 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PubPostVO
 */ 
public class PubPostVO {

	/**
	 * 게시물ID
	 */
	private String publishId;
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 문서보안여부
	 */
	private String securityYn;
	/**
	 * 문서보안 비밀번호
	 */
	private String securityPass;
	/**
	 * 문서보안 시작일
	 */
	private String securityStartDate = "9999-12-31 23:59:59";
	/**
	 * 문서보안 종료일
	 */
	private String securityEndDate = "9999-12-31 23:59:59";
	/**
	 * 게시자ID
	 */
	private String publisherId;
	/**
	 * 게시자명
	 */
	private String publisherName;
	/**
	 * 게시자직위
	 */
	private String publisherPos;
	/**
	 * 게시부서ID
	 */
	private String publishDeptId;
	/**
	 * 게시부서명
	 */
	private String publishDeptName;
	/**
	 * 게시일자
	 */
	private String publishDate = "9999-12-31 23:59:59";
	/**
	 * 게시만료일자
	 */
	private String publishEndDate = "9999-12-31 23:59:59";
	/**
	 * 조회수
	 */
	private int readCount = 0;
	/**
	 * 첨부개수
	 */
	private int attachCount = 0;
	/**
	 * 열람범위
	 */
	private String readRange;
	/**
	 * 전자문서여부
	 */
	private String electronDocYn;	
	/**
	 * 원게시물 ID
	 */
	private String orgPublishId = "";

	/**
	 * 조회일자
	 */
	private String readDate;
	
	
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
	 * @return  the publisherId
	 */
	public String getPublisherId() {
		return publisherId;
	}
	/**
	 * @param publisherId  the publisherId to set
	 */
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	/**
	 * @return  the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	/**
	 * @param publisherName  the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/**
	 * @return  the publisherPos
	 */
	public String getPublisherPos() {
		return publisherPos;
	}
	/**
	 * @param publisherPos  the publisherPos to set
	 */
	public void setPublisherPos(String publisherPos) {
		this.publisherPos = publisherPos;
	}
	/**
	 * @return  the publishDeptId
	 */
	public String getPublishDeptId() {
		return publishDeptId;
	}
	/**
	 * @param publishDeptId  the publishDeptId to set
	 */
	public void setPublishDeptId(String publishDeptId) {
		this.publishDeptId = publishDeptId;
	}
	/**
	 * @return  the publishDeptName
	 */
	public String getPublishDeptName() {
		return publishDeptName;
	}
	/**
	 * @param publishDeptName  the publishDeptName to set
	 */
	public void setPublishDeptName(String publishDeptName) {
		this.publishDeptName = publishDeptName;
	}
	/**
	 * @return  the publishDate
	 */
	public String getPublishDate() {
		return publishDate;
	}
	/**
	 * @param publishDate  the publishDate to set
	 */
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	/**
	 * @return  the publishEndDate
	 */
	public String getPublishEndDate() {
		return publishEndDate;
	}
	/**
	 * @param publishEndDate  the publishEndDate to set
	 */
	public void setPublishEndDate(String publishEndDate) {
		this.publishEndDate = publishEndDate;
	}
	/**
	 * @return  the readCount
	 */
	public int getReadCount() {
		return readCount;
	}
	/**
	 * @param readCount  the readCount to set
	 */
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	/**
	 * @return  the attachCount
	 */
	public int getAttachCount() {
		return attachCount;
	}
	/**
	 * @param attachCount  the attachCount to set
	 */
	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}
	/**
	 * @return  the readRange
	 */
        public String getReadRange() {
            return readRange;
        }
	/**
	 * @param readRange  the readRange to set
	 */
        public void setReadRange(String readRange) {
            this.readRange = readRange;
        }
	/**
	 * @param electronDocYn  the electronDocYn to set
	 */
        public void setElectronDocYn(String electronDocYn) {
	    this.electronDocYn = electronDocYn;
        }
	/**
	 * @return  the electronDocYn
	 */
        public String getElectronDocYn() {
	    return electronDocYn;
        }
	/**
	 * @param orgPublishId  the orgPublishId to set
	 */
        public void setOrgPublishId(String orgPublishId) {
	    this.orgPublishId = orgPublishId;
        }
	/**
	 * @return  the orgPublishId
	 */
        public String getOrgPublishId() {
	    return orgPublishId;
        }
	/**
	 * @return  the securityYn
	 */
        public String getSecurityYn() {
            return securityYn;
        }
	/**
	 * @param securityYn  the securityYn to set
	 */
        public void setSecurityYn(String securityYn) {
            this.securityYn = securityYn;
        }
	/**
	 * @return  the securityPass
	 */
        public String getSecurityPass() {
            return securityPass;
        }
	/**
	 * @param securityPass  the securityPass to set
	 */
        public void setSecurityPass(String securityPass) {
            this.securityPass = securityPass;
        }
	/**
	 * @return  the securityStartDate
	 */
        public String getSecurityStartDate() {
            return securityStartDate;
        }
	/**
	 * @param securityStartDate  the securityStartDate to set
	 */
        public void setSecurityStartDate(String securityStartDate) {
            this.securityStartDate = securityStartDate;
        }
	/**
	 * @return  the securityEndDate
	 */
        public String getSecurityEndDate() {
            return securityEndDate;
        }
	/**
	 * @param securityEndDate  the securityEndDate to set
	 */
        public void setSecurityEndDate(String securityEndDate) {
            this.securityEndDate = securityEndDate;
        }

}
