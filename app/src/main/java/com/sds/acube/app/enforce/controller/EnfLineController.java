package com.sds.acube.app.enforce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.pagination.Page;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.vo.EnfLineHisVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;


/**
 * Class Name : EnfLineController.java <br> Description : 접수결재경로를 처리하는 컨트롤러 클래스 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.web.EnfLineController.java
 */
@SuppressWarnings("serial")
@Controller("enfLineController")
@RequestMapping("/app/enforce/*.do")
public class EnfLineController extends BaseController {

    /**
	 */
    @Inject
    @Named("enfLineService")
    private IEnfLineService enfLineService;


    public void setEnfLineVOService(IEnfLineService enfLineService) {
	this.enfLineService = enfLineService;
    }


    /**
     * <pre> 
     *  접수경로 등록
     * </pre>
     * 
     * @param enfLineVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/insertEnfLine.do")
    public ModelAndView insert(@RequestParam("docId") String docId, @RequestParam("compId") String compId, HttpServletRequest req)
	    throws Exception {

	List<EnfLineVO> insertList = new ArrayList<EnfLineVO>();

	EnfLineVO inputVO = null;
	String[] processorId = UtilRequest.getStringArray(req, "processorId");
	String[] processorName = UtilRequest.getStringArray(req, "processorName");
	String[] processorPos = UtilRequest.getStringArray(req, "processorPos");
	String[] processorDeptId = UtilRequest.getStringArray(req, "processorDeptId");
	String[] askType = UtilRequest.getStringArray(req, "askType");
	String[] processorDeptName = UtilRequest.getStringArray(req, "processorDeptName");

	// 사이즈
	int size = processorId.length;

	for (int i = 0; i < size; i++) {
	    inputVO = new EnfLineVO();
	    inputVO.setDocId(docId);
	    inputVO.setCompId(compId);
	    inputVO.setLineOrder(i + 1);
	    inputVO.setProcessorId(processorId[i]);
	    inputVO.setProcessorName(processorName[i]);
	    inputVO.setProcessorPos(processorPos[i]);
	    inputVO.setProcessorDeptId(processorDeptId[i]);
	    inputVO.setProcessorDeptName(processorDeptName[i]);
	    inputVO.setAskType(askType[i]);
	    insertList.add(inputVO);
	}

	/*
	 * 서비스 호출
	 */
	enfLineService.insert(insertList);

	ModelAndView mav = new ModelAndView("EnfLineController.selectEnfLine");


	return mav;
    }


    /**
     * <pre> 
     *  접수경로 상세조회를 한다.
     * </pre>
     * 
     * @param enfLineVO
     * @param model
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping(params = "method=get")
    public ModelAndView get(@RequestParam("docId") String docId, Model model) throws Exception {
	
	EnfLineVO enfLineVO = new EnfLineVO();
	enfLineVO.setDocId(docId);
//	String line = enfLineService.get(enfLineVO);
	enfLineVO.setLineOrder(3);

	// 접수라인 이력 vo
	EnfLineHisVO enfLineHisVO = new EnfLineHisVO();

	// vo변환로직
	Transform.transformVO(enfLineVO, enfLineHisVO);

	logger.debug("#######################" + enfLineHisVO.getDocId());
	logger.debug("#######################" + enfLineHisVO.getCompId());
	logger.debug("#######################" + enfLineHisVO.getLineOrder());

	ModelAndView mav = new ModelAndView("EnfLineController.select");
	model.addAttribute(enfLineVO);

	return mav;
    }



    /**
     * <pre> 
     *  접수경로를 삭제한다. 
     * </pre>
     * 
     * @param enfLineVO
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/deleteEnfLine.do")
    public ModelAndView delete(EnfLineVO enfLineVO) throws Exception {

	enfLineService.delete(enfLineVO);

	ModelAndView mav = new ModelAndView("EnfLineController.delete");
	return mav;
    }
}
