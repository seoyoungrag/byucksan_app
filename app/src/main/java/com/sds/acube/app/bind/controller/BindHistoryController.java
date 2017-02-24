/**
 * 
 */
package com.sds.acube.app.bind.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindHistoryService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.BindUtil;
import com.sds.acube.app.common.vo.UserVO;


/**
 * Class Name : BindHistoryController.java <br> Description : 편철 이력과 관련된 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 25. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 4. 25.
 * @version  1.0
 * @see  com.sds.acube.app.bind.controller.BindHistoryController.java
 */

@Controller("bindHistoryController")
@RequestMapping("/app/bind/history/*.do")
public class BindHistoryController extends BindBaseController {

    private static final long serialVersionUID = -7470525133073402458L;

    /**
	 */
    @Inject
    @Named("bindHistoryService")
    private BindHistoryService bindHistoryService;

    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;

    /**
     * <pre> 
     *  편철 이력 조회 - 이력 목록
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/history/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String deptId = StringUtils.defaultIfEmpty(request.getParameter(DEPT_ID), super.getDeptId(request));
	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, super.getCompId(request));
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, bindId);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, DEFAULT_COUNT);
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	List<BindVO> rows = bindHistoryService.getList(param);

	for (BindVO vo : rows) {
	    UserVO user = orgService.selectUserByUserId(vo.getModifiedId());
	    vo.setModifiedName(user.getUserName());
	}

	ModelAndView mav = new ModelAndView("BindHistoryController.list");
	mav.addObject(ROWS, rows);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(PAGE_NO, pageNo);
	return mav;
    }


    /**
     * <pre> 
     *  편철 이력 조회 - 선택한 편철 정보
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/history/view.do")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String modified = request.getParameter(MODIFIED);
	String deptId = StringUtils.defaultIfEmpty(request.getParameter(DEPT_ID), super.getDeptId(request));
	String pageNo = request.getParameter(PAGE_NO);
	String compId = super.getCompId(request);

	BindVO vo = new BindVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	vo.setBindId(bindId);
	vo.setModified(modified);

	BindVO bindVO = bindHistoryService.get(vo);

	String option = null;
	if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
	    option = YEAR;
	} else {
	    option = PERIOD;
	}

	String modifiedName = orgService.selectUserByUserId(bindVO.getModifiedId()).getUserName();
	bindVO.setModifiedName(modifiedName);

	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");

	bindVO.setModified(BindUtil.getDateFormat(bindVO.getModified(), dateFormat));

	String sendDeptId = bindVO.getSendDeptId();
	if (StringUtils.isNotEmpty(sendDeptId)) {
	    bindVO.setSendDeptName(orgService.selectOrganization(sendDeptId).getOrgName());
	}

	ModelAndView mav = new ModelAndView("BindHistoryController.view");
	mav.addObject(ROW, bindVO);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(PAGE_NO, pageNo);
	mav.addObject(OPTION, option);
	return mav;
    }
}