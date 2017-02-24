package com.sds.acube.app.exchange.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.exchange.service.IExchangeService;


/**
 * Class Name : ExchangeController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 18. <br> 수 정 자 : chamchi <br> 수정내용 : <br>
 * @author  chamchi
 * @since  2011. 4. 18.
 * @version  1.0
 * @see  com.sds.acube.app.exchange.controller.ExchangeController.java
 */
@Controller("exchangeController")
@RequestMapping("/app/exchnage/*.do")
public class ExchangeController extends BaseController {

    /**
	 */
    @Inject
    @Named("exchangeService")
    private IExchangeService exchangeService;
    private ModelAndView mav;


    public void seExchangeService(IExchangeService exchangeService) {
	this.exchangeService = exchangeService;
    }


    /**
     * <pre> 
     *  연계를 통해 들어온 거래를 반송처리 함.
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */

    @RequestMapping("/app/exchange/processReject.do")
    public ModelAndView processReject(HttpServletRequest req) throws Exception {

	List<AppDocVO> insertList = new ArrayList();

	AppDocVO inputVO = null;

	HttpSession se = req.getSession();
	String userId = (String) se.getAttribute("USER_ID");
	String compId = (String) se.getAttribute("COMP_ID");
	String[] docIds = UtilRequest.getStringArray(req, "docId");

	// 사이즈
	int size = docIds.length;

	for (int i = 0; i < size; i++) {
	    inputVO = new AppDocVO();
	    inputVO.setDocId(docIds[i]);
	    inputVO.setProcessorId(userId);
	    inputVO.setCompId(compId);
	    insertList.add(inputVO);
	}
	try {
	    /*
	     * 서비스 호출
	     */
	    exchangeService.processDocReject(insertList);

	    mav = new ModelAndView("ExchangeController.returndoc");

	    // todo : 리턴페이지 정의 페이지 정의가 없음
	    //mav.setViewName("ExchangeController.returndoc");

	    mav.addObject("message", "exchange.msg.returnok");

	} catch (Exception e) {
	    mav.addObject("message", e.getMessage().toString());
	}
	return mav;
    }
}
