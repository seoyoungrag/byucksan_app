package com.sds.acube.app.enforce.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.pagination.Page;
import org.anyframe.datatype.SearchVO;

import com.sds.acube.app.enforce.service.IEnfLineHisService;
import com.sds.acube.app.enforce.service.impl.EnfLineHisService;
import com.sds.acube.app.enforce.vo.EnfLineHisVO;


/**
 * Class Name : EnfLineHisController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.controller.EnfLineHisController.java
 */
@Controller("enfLineHisController")
@RequestMapping("/app/enforce/*.do")
public class EnfLineHisController {

    /**
	 */
    @Inject
    @Named("enfLineHisService")
    private IEnfLineHisService enfLineHisService;


    @RequestMapping("/app/enforce/enflineHis/getLineHisList.do")
    public ModelAndView getLineHisList(HttpServletRequest req) throws Exception {
	
	ModelAndView mav = new ModelAndView("EnfLineHisController.getLineHisList");
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	EnfLineHisVO enfLineHisVO = new EnfLineHisVO();
	enfLineHisVO.setLineHisId((String) req.getParameter("lineHisId"));
	enfLineHisVO.setDocId((String) req.getParameter("docId"));
	enfLineHisVO.setCompId(compId);
	List hisList = enfLineHisService.getList(enfLineHisVO);
	
	mav.addObject("lineHisList",hisList);
	
	return mav;
    }

}
