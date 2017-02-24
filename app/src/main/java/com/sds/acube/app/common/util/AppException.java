package com.sds.acube.app.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class Name : AppException.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.common.util.AppException.java
 */

public class AppException extends Exception{

    private static final long serialVersionUID = 1L;

    static final Log logger = LogFactory.getLog(AppException.class);

    /**
	 */
    private String errorCode;
    /**
	 */
    private String message = "";
    /**
	 */
    private String userMsg;


    /**
	 * @return  Returns the errorCode.
	 */
    public String getErrorCode() {
	return errorCode;
    }


    /**
	 * @param errorCode  The errorCode to set.
	 */
    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }


    public AppException() {
	super();
    }


    public AppException(String errorCode) {
	this.errorCode = errorCode;
    }


    public AppException(String errorCode, String message) {
	this.message = message;
	this.errorCode = errorCode;
    }


    public AppException(String errorCode, String message, Exception ex) {
	super(ex);
	this.errorCode = errorCode;

	StackTraceElement[] stackTraceElements = ex.getStackTrace();
	int traceCount = stackTraceElements.length;

	for (int i = 0; i < traceCount; i++) {
	    if (stackTraceElements[i].getClassName().startsWith("gov.bms.") && stackTraceElements[i].getClassName().endsWith("FacadeBean")) {

		this.message = (message != null && message.length() > 0 ? message + " " : "")
		        + "\n--[BmsException]--------------------------------------------" + "\n - class : "
		        + stackTraceElements[i].getClassName() + "\n - method: " + stackTraceElements[i].getMethodName() + "\n - line  : "
		        + stackTraceElements[i].getLineNumber() + "\n - cause : " + getOriginalCauseMessage(ex)
		        + "\n------------------------------------------------------------";

		break;
	    }
	}

	if ("".equals(this.message)) {
	    for (int i = 0; i < traceCount; i++) {
		if (stackTraceElements[i].getClassName().startsWith("gov.bms.")) {

		    this.message = (message != null && message.length() > 0 ? message + " " : "")
			    + "\n--[BmsException]--------------------------------------------" + "\n - class : "
			    + stackTraceElements[i].getClassName() + "\n - method: " + stackTraceElements[i].getMethodName()
			    + "\n - line  : " + stackTraceElements[i].getLineNumber() + "\n - cause : " + getOriginalCauseMessage(ex)
			    + "\n------------------------------------------------------------";

		    break;
		}
	    }
	}
    }


    public static String getOriginalCauseMessage(Throwable ttt) {
	Throwable t = ttt;
	while (t.getCause() != null) {
	    t = t.getCause();
	}

	return t.getMessage();
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMessage() {
	// this.message += (super.getMessage() != null &&
	// super.getMessage().length() > 0 ? "\n" + super.getMessage() : "");

	return this.message;
    }


    /**
	 * @return  the userMsg
	 */
    public String getUserMsg() {
	return userMsg;
    }

}
