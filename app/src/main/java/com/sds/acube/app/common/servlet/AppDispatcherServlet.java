package com.sds.acube.app.common.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;

import com.sds.acube.app.common.util.MemoryUtil;

/** 
 *  Class Name  : CodeServlet.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 22. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 22.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.servlet.AppDispatcherServlet.java
 */
@SuppressWarnings("serial")
public class AppDispatcherServlet extends DispatcherServlet {

    protected static Logger logger = Logger.getLogger(AppDispatcherServlet.class);

    /**
     * Controller를 초기화 한다.
     * 
     * @param ServletConfig 
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	try {
	    MemoryUtil.initialize(getWebApplicationContext());
	} catch(Exception e) {
	    logger.error(e.getMessage());
	    new ServletException("Error CodeServlet.init()");
	}
    }
}
