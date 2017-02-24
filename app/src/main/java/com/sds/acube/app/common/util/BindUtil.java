/**
 * 
 */
package com.sds.acube.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.FastDateFormat;

import com.sds.acube.app.bind.BindConstants;


/**
 * Class Name : BindUtil.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 16. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 16.
 * @version 1.0
 * @see com.sds.acube.app.common.util.BindUtil.java
 */

public class BindUtil implements BindConstants {

    /**
     * <pre> 
     *  yyyyMMddHHmmss 날짜 형식을  dateFormat 으로 변환한다.
     * </pre>
     * 
     * @param dateString
     * @param dateFormat
     * @return
     * @throws ParseException
     * @see
     */
    public static String getDateFormat(String dateString, String dateFormat) throws ParseException {
	Date date = new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(dateString);
	return FastDateFormat.getInstance(dateFormat).format(date);
    }


    public static int substring2int(String obj, int i) {
	return Integer.parseInt(obj.substring(i));
    }


    public static int substring2int(String obj, int i, int j) {
	return Integer.parseInt(obj.substring(i, j));
    }


    /**
     * <pre> 
     *  설명 standardDate : 2002-01-01 00:00:00
     * </pre>
     * 
     * @param standardDate
     * @param yearString
     * @param compId
     * @return
     * @throws Exception
     * @see
     */
    public static String getStartDate(String standardDate, String yearString, String compId) throws Exception {
	int year = Integer.parseInt(yearString);
	int month = BindUtil.substring2int(standardDate, 5, 7) - 1;
	int day = BindUtil.substring2int(standardDate, 8, 10);

	Calendar cal = Calendar.getInstance();
	cal.set(year, month, day, 00, 00, 00);

	return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(cal.getTime());
    }


    /**
     * <pre> 
     *  설명 standardDate : 2002-01-01 00:00:00
     * </pre>
     * 
     * @param standardDate
     * @param yearString
     * @param compId
     * @return
     * @throws Exception
     * @see
     */
    public static String getEndDate(String standardDate, String yearString, String compId) throws Exception {
	int year = Integer.parseInt(yearString) + 1;
	int month = BindUtil.substring2int(standardDate, 5, 7) - 1;
	int day = BindUtil.substring2int(standardDate, 8, 10);

	Calendar cal = Calendar.getInstance();
	cal.set(year, month, day, 23, 59, 59);
	cal.add(Calendar.DATE, -1);

	return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(cal.getTime());
    }
}
