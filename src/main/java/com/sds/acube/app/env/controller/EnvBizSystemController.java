/**
 * 
 */
package com.sds.acube.app.env.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.anyframe.query.QueryServiceException;
import org.anyframe.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.env.service.IEnvBizSystemService;
import com.sds.acube.app.env.vo.BizSystemVO;


/**
 * Class Name : EnvBizSystemController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 13. <br> 수 정 자 : chamchi <br> 수정내용 : <br>
 * @author  chamchi
 * @since  2011. 5. 13.
 * @version  1.0
 * @see  com.sds.acube.app.env.controller.EnvBizSystemController.java
 */

@SuppressWarnings("serial")
@Controller("envBizSystemController")
@RequestMapping("/app/env/*.do")
public class EnvBizSystemController extends BaseController {

    @Inject
    @Named("envBizSystemService")
    private IEnvBizSystemService envBizSystemService;


    /**
     * <pre> 
     *  연계시스템 목록조회
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/listEnvBizSystem.do")
    public ModelAndView listEnvBizSystem(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.listEnvBizSystem");

	try {

	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);

	    int pageIndex = Integer.parseInt(StringUtil.null2str(req.getParameter("cPage"), "1"));

	    BizSystemVO bizSystemVO = new BizSystemVO();
	    bizSystemVO.setCompId(compId);
	    // List srcList = envBizSystemService.listEvnBizSystem(inputMap);

	    List list = (List) envBizSystemService.listEvnBizSystem(bizSystemVO);

	    mav.addObject("list", list);

	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;

    }


    /**
     * <pre> 
     *  연계기관조회 조회 
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/listEnvBizSystemPop.do")
    public ModelAndView listEnvBizSystemPop(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	Map inputMap = new HashMap();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);

	int pageIndex = Integer.parseInt(StringUtil.null2str(req.getParameter("cPage"), "1"));

	BizSystemVO bizSystemVO = new BizSystemVO();
	bizSystemVO.setCompId(compId);

	List list = (List) envBizSystemService.listEvnBizSystem(bizSystemVO);


	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.listEnvBizSystemPop");

	mav.addObject("systemList",list);


	return mav;
    }


    /**
     * <pre> 
     *  연계기관조회 조회 
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/selectEnvBizSystem.do")
    public ModelAndView selectEnvBizSystem(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	Map inputMap = new HashMap();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);

	BizSystemVO bizSysVO = (BizSystemVO) envBizSystemService.selectEvnBizSystem(inputMap);

	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.selectEnvBizSystem");

	mav.addObject("bizSysVO", bizSysVO);

	return mav;
    }


    /**
     * <pre> 
     *  연계시스템 등록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/env/admincharge/insertEnvBizSystem.do")
    public ModelAndView insertEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.result");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String deptId = (String) session.getAttribute("DEPT_ID");
	    String deptName = (String) session.getAttribute("DEPT_NAME");

	    String unitId = CommonUtil.nullTrim(req.getParameter("unitId"));
	    String unitName = CommonUtil.nullTrim(req.getParameter("unitName"));

	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("registerId", userId);
	    inputMap.put("registerName", userName);
	    inputMap.put("registerDeptId", deptId);
	    inputMap.put("registerDeptName", deptName);
	    inputMap.put("unitId", unitId);
	    inputMap.put("unitName", unitName);
	    envBizSystemService.insertEvnBizSystem(inputMap);

	    mav.addObject("result", "OK");
	    return mav;

	}catch (QueryServiceException se){
		mav.addObject("result",se.getSqlErrorMessage());
	}
	catch (Exception e) {
	    mav.addObject("result", "FAIL:" + e.getMessage().toString());
	}
		return mav;
	
	
    }


    /**
     * <pre> 
     *  연계시스템 수정
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/env/admincharge/updateEnvBizSystem.do")
    public ModelAndView updateEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.result");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    
	    String unitId = CommonUtil.nullTrim(req.getParameter("unitId"));
	    String unitName = CommonUtil.nullTrim(req.getParameter("unitName"));

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("unitId", unitId);
	    inputMap.put("unitName", unitName);
	    
	    envBizSystemService.updateEvnBizSystem(inputMap);

	    mav.addObject("result", "OK");
	    return mav;

	} catch (Exception e) {
	    return mav.addObject("result", "FAIL:" + e.getMessage().toString());
	}
    }

    /**
     * <pre> 
     *  연계시스템 삭제
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/deleteEnvBizSystem.do")
    public ModelAndView deleteEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.result");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");

	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    envBizSystemService.deleteEvnBizSystem(inputMap);

	    mav.addObject("result", "OK");
	    return mav;

	} catch (Exception e) {
	    return mav.addObject("result", "FAIL:" + e.getMessage().toString());
	}
    }
    
    
    /**
     * <pre> 
     *  웹서비스호출
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/selectEnvBizSystemWeb.do")
    public ModelAndView selectEnvBizSystemWeb(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	Map inputMap = new HashMap();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);

	BizSystemVO bizSysVO = (BizSystemVO) envBizSystemService.selectEvnBizSystem(inputMap);

	ModelAndView mav = new ModelAndView("EnvBizSystemController.admincharge.selectEnvBizSystem");

	mav.addObject("bizSysVO", bizSysVO);

	return mav;
    }
    
}
