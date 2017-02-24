package com.sds.acube.app.common.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sds.acube.app.common.util.XssConfig;

/** 
 *  Class Name  : XssServlet.java <br>
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
 *  @see  com.sds.acube.app.common.servlet.XssServlet.java
 */
@SuppressWarnings("serial")
public class XssServlet extends HttpServlet {
	protected static Logger logger = Logger.getLogger(XssServlet.class);

	/**
	 * Controller를 초기화 한다.
	 * 
	 * @param ServletConfig 
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
			XssConfig.loadScripting();
 		} catch(Exception e) {
			logger.error(e.getMessage());
			new ServletException("Error BmsScriptingServlet.init()");
		}
	}

	/**
	 * Http요청에 따라 Command를 수행하고, 해당 페이지로 포워딩한다.
	 * 
	 * @param req
	 * @param res
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		// empty
	} //end of service()
}
