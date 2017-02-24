/**
 * 
 */
package com.sds.acube.app.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.avalon.framework.logger.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Class Name : LogWrapper.java <br>
 * Description : 로그처리 wrapper클래스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 5. 25. <br>
 * 수 정 자 : 윤동원 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since 2011. 5. 25.
 * @version 1.0
 * @see com.sds.acube.app.common.util.LogWrapper.java
 */

public class LogWrapper implements Logger {

    public static Log log = null;

    public static final String D_MARK = "▶▶▶";
    public static final String W_MARK = "●●●";
    public static final String I_MARK = "▷▷▷";
    public static final String E_MARK = "★★★";


    public LogWrapper(String str) {

	log = LogFactory.getLog(str);

    }


    public static LogWrapper getLogger(String str) {

	return new LogWrapper(str);

    }


    public static LogWrapper getLogger() {

	return new LogWrapper("com.sds.acube.app");
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.acom.sds.acube.appamework.logger.Logger#debug(java.lang.String)
     */
    public void debug(String arg0) {

	if(log.isDebugEnabled()){
	    log.debug(D_MARK + arg0);
	}

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#debug(java.lang.String,
     * java.lang.Throwable)
     */
    public void debug(String arg0, Throwable arg1) {
	log.debug(D_MARK + arg0+"<<>>"+arg1.getMessage().toString());

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#debug(java.lang.String,
     * java.lang.Throwable)
     */
    public void debug(Throwable arg1) {

	log.debug(arg1.getMessage().toString());

    }


    public void debug(Object arg) {

	log.debug(D_MARK + arg);

    }


    public void debug(int arg) {

	log.debug(D_MARK + arg);

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#error(java.lang.String)
     */
    public void error(String arg0) {
	
	log.error(E_MARK+arg0);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#error(java.lang.String)
     */
    public void error(Exception e) {
	
	// TODO Auto-generated method stub
	log.error(E_MARK+e.getMessage().toString());

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#error(java.lang.String,
     * java.lang.Throwable)
     */
    public void error(String arg0, Throwable arg1) {
	// TODO Auto-generated method stub
	log.error(E_MARK+ "["+arg0+"]"+ arg1.getMessage().toString());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.avalon.framework.logger.Logger#fatalError(java.lang.String)
     */
    public void fatalError(String arg0) {
	// TODO Auto-generated method stub
	log.error(E_MARK+arg0);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.avalon.framework.logger.Logger#fatalError(java.lang.String,
     * java.lang.Throwable)
     */
    public void fatalError(String arg0, Throwable arg1) {
	// TODO Auto-generated method stub
	log.error(E_MARK+arg0);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.avalon.framework.logger.Logger#getChildLogger(java.lang.String
     * )
     */
    public Logger getChildLogger(String arg0) {
	// TODO Auto-generated method stub
	return null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#info(java.lang.String)
     */
    public void info(String arg0) {

	log.info(I_MARK + arg0);

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#info(java.lang.String,
     * java.lang.Throwable)
     */
    public void info(String arg0, Throwable arg1) {
	// TODO Auto-generated method stub

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#isDebugEnabled()
     */
    public boolean isDebugEnabled() {
	// TODO Auto-generated method stub
	return log.isDebugEnabled();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#isErrorEnabled()
     */
    public boolean isErrorEnabled() {
	// TODO Auto-generated method stub
	return log.isErrorEnabled();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#isFatalErrorEnabled()
     */
    public boolean isFatalErrorEnabled() {
	// TODO Auto-generated method stub
	return false;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#isInfoEnabled()
     */
    public boolean isInfoEnabled() {
	// TODO Auto-generated method stub
	return log.isInfoEnabled();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#isWarnEnabled()
     */
    public boolean isWarnEnabled() {
	// TODO Auto-generated method stub
	return log.isWarnEnabled();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#warn(java.lang.String)
     */
    public void warn(String arg0) {

	log.error(E_MARK + arg0);

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.apache.avalon.framework.logger.Logger#warn(java.lang.String,
     * java.lang.Throwable)
     */
    public void warn(String arg0, Throwable arg1) {
	// TODO Auto-generated method stub

    }


    /**
     * <pre> 
     *  VO 클래스 출력
     * </pre>
     * 
     * @param obj
     * @see
     */
    public void printVO(Object obj) {

	if (obj == null)
	    return;
	if (log.isDebugEnabled()) {

	    try {
		Map map = BeanUtils.describe(obj);
		// int count = 0;
		StringBuffer dbList = new StringBuffer();
		StringBuffer nonDbList = new StringBuffer();

		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
		    Map.Entry entry = (Map.Entry) it.next();
		    String engKey = (String) entry.getKey();
		    String hanKey = "";
		    String value = (String) entry.getValue();
		    // buff.append(obj.getClass().getName() + "[" + count++ +
		    // "] ");
		    if (hanKey != null)
			dbList.append("* NAME <" + engKey + "> " + hanKey + " =" + value + "\n");
		    else
			nonDbList.append("* NAME <" + engKey + "> =" + value + "\n");

		    if ("null".equals(value))
			log.debug("@" + engKey + " 는 문자열 null 이 입력되었습니다.");
		}
		this.debug("\n" + dbList.toString() + nonDbList.toString());
	    } catch (InvocationTargetException e) {
		log.debug("printVO"+e.getMessage().toString());
	    } catch (NoSuchMethodException e) {
		log.debug("printVO"+e.getMessage().toString());
	    } catch (IllegalAccessException e) {
		log.debug("printVO"+e.getMessage().toString());
	    }
	}
    }

}
