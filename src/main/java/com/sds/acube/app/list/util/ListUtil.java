/**
 * 
 */
package com.sds.acube.app.list.util;



import java.util.HashMap;

import org.jconfig.Configuration;

import com.sds.acube.app.common.util.AppCode;
import com.sds.acube.app.common.util.MemoryUtil;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;

/**
 * Class Name : ListUtil.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : <br>
 * 수 정 자 : <br>
 * 수정내용 : <br>
 * 
 * @author 김경훈
 * @since 2011. 4. 6.
 * @version 1.0
 * @see com.sds.acube.app.list.util.ListUtil.java
 */

public class ListUtil {
    
    /**
     * 
     * <pre> 
     *  내용을 query in 절에 사용하기 위해 변환함.
     * </pre>
     * @param value
     * @return
     * @throws Exception
     * @see  
     *
     */
    public static String TransString(String value) throws Exception {
	StringBuffer buff = new StringBuffer();

	String[] attr = value.split(",");
	int attrLength = attr.length;
	
	for(int i=0; i<attrLength; i++) {

	    if(i != (attrLength -1)) {
		buff.append("'" + attr[i].trim() + "',");
	    }else{
		buff.append("'" + attr[i].trim() + "'");
	    }

	}
	
	return buff.toString();

    }
    
    /**
     * 
     * <pre> 
     *  내용을 query in 절에 사용하기 위해 변환함.(복수컬럼용) 20140721 kj.yang 추가
     * </pre>
     * @param value
     * @return
     * @throws Exception
     * @see  
     *
     */
    public static String TransMutlString(String value) throws Exception {
	StringBuffer buff = new StringBuffer();

	String[] attr = value.split(",");
	int attrLength = attr.length;
	
	for(int i=0; i<attrLength; i++) {

	    if(i != (attrLength -1)) {
		buff.append("(0,'" + attr[i].trim() + "'),");
	    }else{
		buff.append("(0,'" + attr[i].trim() + "')");
	    }

	}
	
	return buff.toString();

    }
    
    /**
     * 
     * <pre> 
     *  appCode의 내용을 query in 절에 사용하기 위해 변환함.
     * </pre>
     * @param value
     * @param Type
     * @return
     * @throws Exception
     * @see  
     *
     */
    public static String TransString(String value, String Type) throws Exception {
	AppCode appCode = MemoryUtil.getCodeInstance();
	StringBuffer buff = new StringBuffer();

	String[] attr = value.split(",");
	int attrLength = attr.length;
	
	for(int i=0; i<attrLength; i++) {
	    String Code = appCode.getProperty(attr[i].trim(), attr[i].trim(), Type);

	    if(i != (attrLength -1)) {
		buff.append("'" + Code + "',");
	    }else{
		buff.append("'" + Code + "'");
	    }

	}

	return buff.toString();

    }
    
    /**
     * 
     * <pre> 
     *  replace
     * </pre>
     * @param str
     * @param Type
     * @param replaceType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public static String TransReplace(String str, String Type, String replaceType) throws Exception {
	
	if(str != null || !"".equals(str)) {
	    str = str.replaceAll(Type, replaceType);
	}

	return str;

    }
    
    /**
     * 
     * <pre> 
     *  기본 조건에 따른 시작일, 종료일 반환
     * </pre>
     * @param searchBasicPeriod
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     * @see  
     *
     */
    public HashMap<String, String> returnDate(String searchBasicPeriod, String startDate, String endDate) throws Exception {
	HashMap<String, String> map = new HashMap<String, String>();
	
	String standard 	= AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date");
	String conDateFormat	= AppConfig.getProperty("std_date_format", "yyyy-MM-dd", "date");
	
	if ("".equals(endDate)) {
	    
	    if ("1".equals(searchBasicPeriod)) {
		startDate = DateUtil.getPreNextDate(-7,conDateFormat);
	    } else if ("2".equals(searchBasicPeriod)) {
		startDate = DateUtil.getPreNextDate(-1, 0,conDateFormat);
	    } else if ("3".equals(searchBasicPeriod)) {
		startDate = DateUtil.getPreNextDate(-3, 0,conDateFormat);
	    } else if ("4".equals(searchBasicPeriod)) {
		startDate = DateUtil.getPreNextDate(-6, 0,conDateFormat);
	    }
	    endDate = DateUtil.getCurrentDay(standard); // 현재 날짜 얻어오기
	    
	    startDate	= DateUtil.getConvertedDate(startDate + " 00:00:00", standard);
	    endDate	= DateUtil.getConvertedDate(endDate + " 23:59:59", standard);
	    
	} else {
	    
	    startDate =  DateUtil.getConvertedDate(startDate + " 00:00:00", standard);
	    endDate = DateUtil.getConvertedDate(endDate + " 23:59:59" ,standard);

	}
	
	map.put("startDate", startDate);
	map.put("endDate", endDate);	
	
	return map;
    }

}
