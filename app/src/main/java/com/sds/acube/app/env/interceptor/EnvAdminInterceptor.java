/**
 * 
 */
package com.sds.acube.app.env.interceptor;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jconfig.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;


/**
 * Class Name : EnvAdminInterceptor.java <br>
 * Description : 전자결재 관리자환경설정 권한체크 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 4. 14. <br>
 * 수 정 자 : 신경훈 <br>
 * 수정내용 : <br>
 * 
 * @author 신경훈
 * @since 2011. 4. 14.
 * @version 1.0
 * @see com.sds.acube.app.env.interceptor.EnvAdminInterceptor.java
 */

public class EnvAdminInterceptor extends HandlerInterceptorAdapter {

    @Inject
    @Named("messageSource")
    MessageSource messageSource;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception {

	HttpSession session = request.getSession();

	String uri = request.getRequestURI();
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	
	String roleId00 = AppConfig.getProperty("role_admin", "", "role");	
	String roleId01 = AppConfig.getProperty("role_iam", "", "role");	
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	
	if (uri.contains("/admin/")) { //관리자
	    if ( ! ( roleCode.indexOf(roleId00) >= 0 || roleCode.indexOf(roleId10) >= 0 || roleCode.indexOf(roleId01) >= 0 ) ) {
		String sessionMsg = messageSource.getMessage("env.option.msg.not.previlige", null, (Locale) session
		        .getAttribute("LANG_TYPE"));
		CommonUtil.script(response, "alert('" + sessionMsg + "');window.close();");
		return false;
	    }
	} else if (uri.contains("/admincharge/")) {//문서관리자, 관리자
	    if (roleCode.indexOf(roleId10) < 0 && roleCode.indexOf(roleId11) < 0 && roleCode.indexOf(roleId12) < 0) {
		String sessionMsg = messageSource.getMessage("env.option.msg.not.previlige", null, (Locale) session
		        .getAttribute("LANG_TYPE"));
		CommonUtil.script(response, "alert('" + sessionMsg + "');window.close();");
		return false;
	    }
	}
	return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object hadler, ModelAndView mav) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object hadler, Exception exception)
	    throws Exception {

    }

}
