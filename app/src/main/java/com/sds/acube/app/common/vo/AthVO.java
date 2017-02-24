/**
 * 
 */
package com.sds.acube.app.common.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class Name  : AthVO.java <br> Description : ATH 파일 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 13. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 13.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.SendMailVO.java
 */

public class AthVO {
    
    public AthVO() {
        contentType = "html";
        attachedFiles = new ArrayList();
        attrributes = new HashMap();
    }  

    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String content;
    private String contentType;
    private String fileName;
    private String fullPath;
    private HashMap attrributes;
    /**
	 */
    private FileInfoVO contentFile;
    private List attachedFiles;
}
