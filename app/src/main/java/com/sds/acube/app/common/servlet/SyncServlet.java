package com.sds.acube.app.common.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sds.acube.app.common.service.IMemoryService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;

/** 
 *  Class Name  : SyncServlet.java <br>
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
public class SyncServlet extends HttpServlet {
	protected static Logger logger = Logger.getLogger(SyncServlet.class);

	/**
	 * Controller를 초기화 한다.
	 * 
	 * @param ServletConfig 
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * Http요청에 따라 Command를 수행하고, 해당 페이지로 포워딩한다.
	 * 
	 * @param req
	 * @param res
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	    try {
		String syncType = CommonUtil.nullTrim(request.getParameter("syncType"));
		String compId = CommonUtil.nullTrim(request.getParameter("compId"));
		if ("option".equals(syncType)) {
		    if (!"".equals(compId)) {
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			IMemoryService memoryService = (IMemoryService) ctx.getBean("memoryService");
			memoryService.reloadOption(compId);
		    }
		} else if ("config".equals(syncType)) {
		    AppConfig.reload();
		}
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	} //end of service()
}
