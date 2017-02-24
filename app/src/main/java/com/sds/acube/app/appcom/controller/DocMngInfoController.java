package com.sds.acube.app.appcom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IDocMngInfoService;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;


/**
 * Class Name : DocMngInfoController.java <br> Description : 문서관리정보 controller <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 28. <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  2011. 4. 28.
 * @version  1.0
 * @see  com.sds.acube.app.appcom.controller.DocMngInfoController.java
 */
@Controller("docMngInfoController")
@RequestMapping("/app/enforce/*.do")
public class DocMngInfoController extends BaseController {

    /**
	 */
    @Inject
    @Named("docMngInfoService")
    private IDocMngInfoService docMngInfoService;

    private ModelAndView mav;


    /**
     * <pre> 
     *  관리정보 수정(생산)
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/appcom/app/updateDocMngInfo.do")
    public ModelAndView updateDocMngInfo(HttpServletRequest req) throws Exception {

	HttpSession se = req.getSession();

	try {
	    String compId = (String) se.getAttribute("COMP_ID");

	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("type", "APP");
	    /*
	     * 서비스 호출
	     */
	    docMngInfoService.updateDocMngInfo(inputMap);

	    mav = new ModelAndView("DocMngInfoController.updateDocMngInfo");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    mav.addObject("result", "FAIL"+e.getMessage().toString());
	}
	return mav;
    }


    /**
     * <pre> 
     *  관리정보 조회(생산)
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/appcom/app/selectDocMngInfo.do")
    public ModelAndView selectDocMngInfo(HttpServletRequest req) throws Exception {

	HttpSession se = req.getSession();

	String compId = (String) se.getAttribute("COMP_ID");

	//
	String docId = UtilRequest.getString(req, "docId");

	Map inputMap = new HashMap();
	inputMap.put("docId", docId);
	inputMap.put("compId", compId);
	inputMap.put("type", "APP");

	/*
	 * 서비스 호출
	 */
	Map map = docMngInfoService.selectDocMngInfo(inputMap);

	mav = new ModelAndView("DocMngInfoController.app.selectDocMngInfo");
	mav.addObject("map",map);

	return mav;
    }


    /**
     * <pre> 
     *  관리정보 수정(접수)
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/appcom/enf/updateDocMngInfo.do")
    public ModelAndView updateEnfDocMngInfo(HttpServletRequest req) throws Exception {

	HttpSession se = req.getSession();

	try {

	    String compId = (String) se.getAttribute("COMP_ID");


	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("type", "ENF");
	    /*
	     * 서비스 호출
	     */
	    docMngInfoService.updateDocMngInfo(inputMap);

	    mav = new ModelAndView("DocMngInfoController.updateDocMngInfo");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    mav.addObject("result", "FAIL"+e.getMessage().toString());
	}
	
	return mav;
    }


    /**
     * <pre> 
     *  관리정보 조회(접수)
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/appcom/enf/selectDocMngInfo.do")
    public ModelAndView selectEnfDocMngInfo(HttpServletRequest req) throws Exception {

	HttpSession se = req.getSession();

	String compId = (String) se.getAttribute("COMP_ID");

	//
	String docId = UtilRequest.getString(req, "docId");

	Map inputMap = new HashMap();
	inputMap.put("docId", docId);
	inputMap.put("compId", compId);
	inputMap.put("type", "ENF");

	/*
	 * 서비스 호출
	 */
	Map map = docMngInfoService.selectDocMngInfo(inputMap);

	mav = new ModelAndView("DocMngInfoController.enf.selectDocMngInfo");
	mav.addObject("map",map);

	return mav;
    }
}
