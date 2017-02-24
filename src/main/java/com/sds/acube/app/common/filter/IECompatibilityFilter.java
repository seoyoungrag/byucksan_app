package com.sds.acube.app.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/** 
 *  Class Name  : IECompatibilityFilter.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 * 
 *  @author  jin 
 *  @since 2013. 4. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.filter.IECompatibilityFilter.java
 */

public class IECompatibilityFilter implements Filter {
	
	public static final String REQUEST_HEADER_USER_AGENT = "User-Agent";
	public static final String MSIE_10_MATCH = "MSIE 10";

	static Logger logger = Logger.getLogger(IECompatibilityFilter.class.getName());
	protected FilterConfig fc;

	public void init(FilterConfig fc) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("IECompatibilityFilter initialized");
		} 
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
			String userAgent = httpServletRequest.getHeader(REQUEST_HEADER_USER_AGENT);
			// MS IE 10 브라우저인지 확인
			if (userAgent.indexOf(MSIE_10_MATCH) > 0) {
				if (servletResponse instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
					// 호환성 모드 및 데스크탑 모드 헤더 추가
					//httpServletResponse.addHeader("X-UA-Compatible", "IE=EmulateIE9");
					httpServletResponse.addHeader("X-UA-Compatible", "requiresActiveX=true,IE=EmulateIE9");
				}
			}
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("IECompatibilityFilter destroyed");
		}	
	}

}
