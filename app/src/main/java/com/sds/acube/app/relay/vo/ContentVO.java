/**
 * 
 */
package com.sds.acube.app.relay.vo;

/**
 * Class Name  : ContentVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 26. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 3. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.ContentVO.java
 */

public class ContentVO {
	// 본문
	/**
	 */
	private String content;
	
	// 저장서버에 저장될 파일이름
	/**
	 */
	private String contentStorFileName = "";
	
	// 본문 종류
	/**
	 */
	private String contentContentRole = "";
	
	// 본문 암호화
	/**
	 */
	private String contentContentTransperEncoding = "";
	
	// 본문 파일명
	/**
	 */
	private String contentFilename = "";
	
	// 본문 형식(MIME)
	/**
	 */
	private String contentContentType = "";
	
	// 언어셋
	/**
	 */
	private String contentCharset = "";

	/**
	 * @return  the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content  the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return  the contentStorFileName
	 */
	public String getContentStorFileName() {
		return contentStorFileName;
	}

	/**
	 * @param contentStorFileName  the contentStorFileName to set
	 */
	public void setContentStorFileName(String contentStorFileName) {
		this.contentStorFileName = contentStorFileName;
	}

	/**
	 * @return  the contentContentRole
	 */
	public String getContentContentRole() {
		return contentContentRole;
	}

	/**
	 * @param contentContentRole  the contentContentRole to set
	 */
	public void setContentContentRole(String contentContentRole) {
		this.contentContentRole = contentContentRole;
	}

	/**
	 * @return  the contentContentTransperEncoding
	 */
	public String getContentContentTransperEncoding() {
		return contentContentTransperEncoding;
	}

	/**
	 * @param contentContentTransperEncoding  the contentContentTransperEncoding to set
	 */
	public void setContentContentTransperEncoding(
			String contentContentTransperEncoding) {
		this.contentContentTransperEncoding = contentContentTransperEncoding;
	}

	/**
	 * @return  the contentFilename
	 */
	public String getContentFilename() {
		return contentFilename;
	}

	/**
	 * @param contentFilename  the contentFilename to set
	 */
	public void setContentFilename(String contentFilename) {
		this.contentFilename = contentFilename;
	}

	/**
	 * @return  the contentContentType
	 */
	public String getContentContentType() {
		return contentContentType;
	}

	/**
	 * @param contentContentType  the contentContentType to set
	 */
	public void setContentContentType(String contentContentType) {
		this.contentContentType = contentContentType;
	}

	/**
	 * @return  the contentCharset
	 */
	public String getContentCharset() {
		return contentCharset;
	}

	/**
	 * @param contentCharset  the contentCharset to set
	 */
	public void setContentCharset(String contentCharset) {
		this.contentCharset = contentCharset;
	}
}
