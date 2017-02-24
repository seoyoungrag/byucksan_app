/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name  : SendMail.java <br> Description : 메일 알림 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 6. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 6.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.SendMailVO.java
 */

public class SendMailVO {
    
    /**
	 */
    private String title;	// 메일 제목
    /**
	 */
    private String content;	// 메일 내용
    /**
	 */
    private String linkURL;	// 링크 URL
    
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
	 * @return  the linkURL
	 */
    public String getLinkURL() {
        return linkURL;
    }
    /**
	 * @param linkURL  the linkURL to set
	 */
    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }    
    
}
