package com.sds.acube.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.time.FastDateFormat;


/**
 * Class Name : DateUtil.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 29. <br>
 * 수 정 자 : Timothy <br>
 * 수정내용 : <br>
 * 
 * @author Timothy
 * @since 2011. 3. 29.
 * @version 1.0
 * @see com.sds.acube.app.common.util.DateUtil.java
 */

public class DateUtil {
    private static String standard = AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date");
    private static String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
    private static String date_format = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

    public static String getCurrentDateWithMilliseconds() {
	GregorianCalendar date = new GregorianCalendar();
	return FastDateFormat.getInstance(standard).format(date) + " " + date.getTimeInMillis();
    }

    public static String getCurrentDate() {
	return FastDateFormat.getInstance(standard).format(new Date());
    }

    public static String getCurrentDate(Date date) {
	return FastDateFormat.getInstance(standard).format(date);
    }

    public static String getCurrentDate(String dateformat) {
	return FastDateFormat.getInstance(dateformat).format(new Date());
    }

    public static String getCurrentYear() {
	return FastDateFormat.getInstance(standard).format(new Date()).substring(0, 4);
    }
    
    public static String getCurrentDay() {
	return FastDateFormat.getInstance(standard).format(new Date()).substring(0, 10);
    }
    
    public static String getCurrentDay(String dateformat) {
	return FastDateFormat.getInstance(dateformat).format(new Date()).substring(0, 10);
    }


    public static String getCurrentTime() {
	return FastDateFormat.getInstance(standard).format(new Date()).substring(11);
    }


    public static String getPreNextDate(int day) {
	return getPreNextDate(0, 0, day);
    }


    public static String getPreNextDate(int day, String dateformat) {
	return getPreNextDate(0, 0, day, dateformat);
    }


    public static String getPreNextDate(int month, int day) {
	return getPreNextDate(0, month, day);
    }


    public static String getPreNextDate(int month, int day, String dateformat) {
	return getPreNextDate(0, month, day, dateformat);
    }


    public static String getPreNextDate(int year, int month, int day) {
	GregorianCalendar date = new GregorianCalendar();
	date.add(Calendar.YEAR, year);
	date.add(Calendar.MONTH, month);
	date.add(Calendar.DATE, day);
	return FastDateFormat.getInstance(standard).format(date);
    }


    public static String getPreNextDate(int year, int month, int day, String dateformat) {
	GregorianCalendar date = new GregorianCalendar();
	date.add(Calendar.YEAR, year);
	date.add(Calendar.MONTH, month);
	date.add(Calendar.DATE, day);
	return FastDateFormat.getInstance(dateformat).format(date);
    }


    public static String getPreNextDate(String currentDate, int year, int month, int day) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    Calendar cal = Calendar.getInstance();      
	    cal.setTime(date) ;

	    //GregorianCalendar calendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
	    GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	    calendar.add(Calendar.YEAR, year);
	    calendar.add(Calendar.MONTH, month);
	    calendar.add(Calendar.DATE, day);
	    return FastDateFormat.getInstance(standard).format(calendar);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }


    public static String getPreNextDate(String currentDate, int year, int month, int day, String dateformat) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    Calendar cal = Calendar.getInstance();      
	    cal.setTime(date) ;

	    //GregorianCalendar calendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
	    GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	    calendar.add(Calendar.YEAR, year);
	    calendar.add(Calendar.MONTH, month);
	    calendar.add(Calendar.DATE, day);
	    return FastDateFormat.getInstance(dateformat).format(calendar);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }


    public static String getPreNextDate(String currentDate, int year, int month, int day, int hour, int minute, int second, String dateformat) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    Calendar cal = Calendar.getInstance();      
	    cal.setTime(date) ;

	    //GregorianCalendar calendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
	    GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 
		    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
	    calendar.add(Calendar.YEAR, year);
	    calendar.add(Calendar.MONTH, month);
	    calendar.add(Calendar.DATE, day);
	    calendar.add(Calendar.HOUR, hour);
	    calendar.add(Calendar.MINUTE, minute);
	    calendar.add(Calendar.SECOND, second);
	    return FastDateFormat.getInstance(dateformat).format(calendar);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }

    
    public static String getFormattedDate(String currentDate) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    return FastDateFormat.getInstance(dateFormat).format(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }


    public static String getFormattedDate(String currentDate, String dateformat) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    return FastDateFormat.getInstance(dateformat).format(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }


    public static String getFormattedShortDate(String currentDate) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    return FastDateFormat.getInstance(date_format).format(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }


    public static String getConvertedDate(String currentDate, String dateformat) {
	try {
	    if (currentDate.indexOf("/") != -1) {
	    	currentDate = currentDate.replaceAll("/", "-");
	    }
	    Date date = (new SimpleDateFormat(standard, Locale.KOREA)).parse(currentDate);
	    return FastDateFormat.getInstance(dateformat).format(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    return "";
	}
    }
    
    public static int inDurationDate(String startDay, String endDay, String dateformat) {
	 int durationDate = 0;
	 
	 try{
	     SimpleDateFormat formatter = new SimpleDateFormat(dateformat, Locale.KOREA);
	     Date beginDate = formatter.parse(startDay);
	     Date endDate = formatter.parse(endDay);
        	     
	     durationDate = beginDate.compareTo(endDate);
        	    
	     return durationDate;
	 
	 } catch(ParseException e) {
	     return durationDate;
	 }
    }
    
    public static boolean isDurationDate(String startDay, String endDay, String dateformat) {
	 boolean durationDate = false;
	 
	 try{
	     SimpleDateFormat formatter = new SimpleDateFormat(dateformat, Locale.KOREA);
	     Date beginDate = formatter.parse(startDay);
	     Date endDate = formatter.parse(endDay);
	    // int beginDate =  Integer.parseInt(FastDateFormat.getInstance(dateformat).format(formatter.parse(startDay)));
	     //int endDate = Integer.parseInt(FastDateFormat.getInstance(dateformat).format(formatter.parse(endDay)));
	     String curDate =  FastDateFormat.getInstance(dateformat).format(new Date());
	    // if((curDate > beginDate || curDate == beginDate) && (curDate < endDate || curDate == endDate) )
	    //	 durationDate = true;
	     
	     //if((curDate.before(endDate)||curDate.equals(endDate)) &&  (curDate.after(beginDate)||curDate.equals(startDay))) 
	     //	 durationDate = true;
	     return durationDate;
	 
	 } catch(ParseException e) {
	     return durationDate;
	 }
   }
    public static long diffOfDate(String begin, String end) throws Exception
    {
      SimpleDateFormat formatter = new SimpleDateFormat(standard);
   
      Date beginDate = formatter.parse(begin);
      Date endDate = formatter.parse(end);
   
      long diff = endDate.getTime() - beginDate.getTime();
      long diffDays = diff / (24 * 60 * 60 * 1000);
   
      return diffDays;
    }
    
}
