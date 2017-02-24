/**
 * 
 */
package com.sds.acube.app.env.util;

import java.util.HashMap;

/** 
 *  Class Name  : OptionUtil.java <br>
 *  Description : 결재옵션 관련 유틸  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 4. <br>
 *  수 정  자 : 신경훈  <br>
 *  수정내용 :  <br>
 * 
 *  @author  신경훈 
 *  @since 2011. 4. 4.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.OptionUtil.java
 */

public class OptionUtil {
    
    /**
     * <pre> 
     *  체크박스 체크 유무를 "Y/N" 값으로 반환
     * </pre>
     * @param obj
     * @return requestParam
     */
    public String checkboxValue(Object obj) {
        String requestParam = (String)obj;
        if (requestParam==null) {
    		requestParam = "N";
        } else {
    		requestParam = "Y";
        }
        return requestParam;
    }
    
    /**
     * <pre> 
     *  request 파라미터가 널이면 설정한 값으로 반환
     * </pre>
     * @param obj
     * @param defaultVal
     * @return requestParam
     */
    public String nullToDefault(Object obj, String defaultVal) {
        String requestParam = (String)obj;
        if (requestParam==null || "".equals(requestParam)) {
            return defaultVal;
        } else {
            return requestParam;
        }
    }
    
    /**
     * <pre> 
     *  2진배열 형태의 문자열을 HashMap으로 반환
     * </pre>
     * @param value
     * @return requestParam
     */
    public HashMap<String, String> arrayToMap(String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] attr = value.split("\\^");
        int attrLength = attr.length;
        for (int i=0; i<attrLength; i++) {
        	String[] attrTemp = attr[i].split(":"); 
        	map.put(attrTemp[0], attrTemp[1]);
        }            
        return map;
    }

}
