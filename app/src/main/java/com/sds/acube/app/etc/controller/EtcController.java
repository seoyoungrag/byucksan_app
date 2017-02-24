/**
 * 
 */
package com.sds.acube.app.etc.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.etc.service.IEtcService;

/**
 * Class Name  : EtcController.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 18. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 5. 18.
 * @version  1.0 
 * @see  com.sds.acube.app.etc.controller.EtcController.java
 */

@Controller("etcController")
@RequestMapping("/app/etc/*.do")
public class EtcController {
    
    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("etcService")
    private IEtcService etcService;

    /**
     * <pre> 
     * 공람게시 종료처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/etc/closePublicPost.do")
    public ModelAndView closePublicPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("EtcController.closePublicPost");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String[] publishId = request.getParameterValues("publishId");

	int result = etcService.closePublicPost(publishId, compId, currentDate);
	if (result > 0) {
	    mav.addObject("result", "success");
	    mav.addObject("message", "approval.msg.success.closepublicpost");
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.fail.closepublicpost");
	}
	mav.addObject("count", result);
	
	return mav;
    }
}
