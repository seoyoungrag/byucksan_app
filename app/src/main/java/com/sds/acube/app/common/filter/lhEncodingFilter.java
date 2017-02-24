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
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sds.acube.app.common.util.XssConfig;

/** 
 *  Class Name  : lhEncodingFilter.java <br>
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
 *  @see  com.sds.acube.app.common.filter.lhEncodingFilter.java
 */

public class lhEncodingFilter implements Filter {

	protected FilterConfig fc;

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		servletRequest.setCharacterEncoding(fc.getInitParameter("lhEncoding"));
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	public void destroy() {
		
	}

}
