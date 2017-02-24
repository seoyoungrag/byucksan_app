package com.sds.acube.app.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sds.acube.app.common.util.XssConfig;

/** 
 *  Class Name  : XssFilter.java <br>
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
 *  @see  com.sds.acube.app.common.filter.XssFilter.java
 */

public class XssFilter implements Filter {

	static Logger logger = Logger.getLogger(XssFilter.class.getName());
	protected FilterConfig fc;
	private String xsspage;

	public void init(FilterConfig fc) {
		if (logger.isDebugEnabled()) 
			logger.debug("# init XssSBIS ############################################ #");
		xsspage = fc.getInitParameter("xss-page"); 

		this.fc = fc;
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		boolean xss = false;
		if (logger.isDebugEnabled())
			logger.debug("# Page : " + request.getRequestURI());
		
		String uri = request.getRequestURI();
		uri = uri.substring(0, uri.indexOf(".do"));
		String[] arrUri = uri.split("/");
				
		String strModule = null;
		String strCmd = null;

		if (arrUri.length > 1) {
			strModule = arrUri[arrUri.length - 2];
			strCmd = arrUri[arrUri.length - 1];
		}
		strModule = "app";
		strCmd = "*";

		// Cross Site Scripting 적용
        	try {
        		xss = XssConfig.existScripting(request, strModule, strCmd);
    			if (xss) {
    				if (logger.isDebugEnabled()) {
    					logger.debug("# search XSS ############################################ #");
    					logger.debug("# Cross Site Scripting Code is found. It is going to redirect a Error page : [" + xsspage + "]");
    				}
    				RequestDispatcher rd = request.getRequestDispatcher(xsspage);
    				rd.forward(req, res);
    			} else {
    			    chain.doFilter(req, res);
    			}
    		} catch (Exception e) {
    			logger.debug("Ignore!!! XssFilter.doFilter() - Cross Site Scripting.");
    			logger.debug(e + " : " + e.getStackTrace()[0].getClassName() + "." 
            			+ e.getStackTrace()[0].getMethodName() 
            			+ "[" + e.getStackTrace()[0].getLineNumber() + "]");
    		}
    	}
	
	public void destroy() {
		if (logger.isDebugEnabled()) 
			logger.debug("# destroy FilterSBIS ############################################ #");
		
		this.fc = null;
	}

}
