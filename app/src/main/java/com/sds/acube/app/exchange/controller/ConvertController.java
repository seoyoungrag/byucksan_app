package com.sds.acube.app.exchange.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.exchange.convert.ConvertThread;
import com.sds.acube.app.exchange.service.IConvertService;
import com.sds.acube.app.exchange.service.IDocumentService;
import com.sds.acube.app.exchange.vo.ConvertVO;


/**
 * Class Name : ConvertController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 7. 7. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 7. 7.
 * @version  1.0
 * @see  com.sds.acube.app.exchange.controller.ConvertController.java
 */
@Controller("convertController")
@RequestMapping("/app/convert/*.do")
public class ConvertController extends BindBaseController {

    private static final long serialVersionUID = -4305867876402379041L;

    /**
	 */
    @Inject
    @Named("convertService")
    private IConvertService convertService;

    /**
	 */
    @Inject
    @Named("documentService")
    private IDocumentService documentService;


    @RequestMapping("/app/convert/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = StringUtils.defaultIfEmpty(request.getParameter(COMPANY), super.getCompId(request));
	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), DateUtil.getCurrentYear());
	Locale locale = super.getLocale(request);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(YEAR, searchYear);

	List<ConvertVO> rows = convertService.getDocList(param);

	ModelAndView mav = new ModelAndView("ConvertController.list");
	mav.addObject(ROWS, rows);
	mav.addObject(YEAR, searchYear);
	mav.addObject(COMP_ID, compId);
	mav.addObject(COMPANY, super.getCompList());
	mav.addObject(SEARCH_YEAR, super.getSearchYear(compId, locale));
	return mav;
    }


    @RequestMapping("/app/convert/error.do")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = StringUtils.defaultIfEmpty(request.getParameter(COMPANY), super.getCompId(request));

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);

	List<ConvertVO> rows = convertService.getErrorList(param);

	ModelAndView mav = new ModelAndView("ConvertController.error");
	mav.addObject(ROWS, rows);
	mav.addObject(COMP_ID, compId);
	mav.addObject(COMPANY, super.getCompList());
	return mav;
    }


    @RequestMapping("/app/convert/detail.do")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = StringUtils.defaultIfEmpty(request.getParameter(COMPANY), super.getCompId(request));
	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), DateUtil.getCurrentYear());
	String searchMonth = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_MONTH), "01");
	Locale locale = super.getLocale(request);

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Integer.parseInt(searchYear));
	cal.set(Calendar.MONTH, Integer.parseInt(searchMonth) - 1);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(START_DAY, searchYear + searchMonth + "01");
	param.put(END_DAY, searchYear + searchMonth + cal.getActualMaximum(Calendar.DAY_OF_MONTH));

	List<ConvertVO> rows = convertService.getDocMonthList(param);

	ModelAndView mav = new ModelAndView("ConvertController.detail");
	mav.addObject(ROWS, rows);
	mav.addObject(YEAR, searchYear);
	mav.addObject(MONTH, searchMonth);
	mav.addObject(COMP_ID, compId);
	mav.addObject(COMPANY, super.getCompList());
	mav.addObject(SEARCH_YEAR, super.getSearchYear(compId, locale));
	mav.addObject(SEARCH_MONTH, super.getSearchMonth(locale));
	return mav;
    }


    @RequestMapping("/app/convert/execute.do")
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = request.getParameter(COMP_ID);
	    String year = request.getParameter(YEAR);
	    String month = request.getParameter(MONTH);
	    String day = request.getParameter(DAY);

	    String startDay = null;
	    String endDay = null;

	    if (StringUtils.isNotEmpty(day)) {
		startDay = year + month + day;
		endDay = year + month + day;
	    } else {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		if (StringUtils.isNotEmpty(month)) {
		    cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);

		    startDay = year + month + "01";
		    endDay = year + month + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		} else {
		    startDay = year + "0101";
		    endDay = year + "1231";
		}
	    }

	    ConvertThread ct = new ConvertThread(documentService, convertService, compId, startDay, endDay);
	    new Thread(ct).start();

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.getWriter().write(result.toString());
    }


    @RequestMapping("/app/convert/document.do")
    public void document(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = request.getParameter(COMP_ID);
	    String docId = request.getParameter(DOC_ID);
	    String usingType = request.getParameter("usingType");

	    ConvertVO vo = new ConvertVO();
	    vo.setCompId(compId);
	    vo.setDocId(docId);
	    vo.setUsingType(usingType);

	    boolean convert = documentService.convert(vo, true);

	    if (convert) {
		convertService.removeError(vo);
		result.put(SUCCESS, true);
	    } else {
		result.put(SUCCESS, false);
		result.put(MESSAGE, vo.getMessage());
	    }
	} catch (Exception e) {
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.getWriter().write(result.toString());
    }


    @RequestMapping("/app/convert/documentAll.do")
    public void documentAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = request.getParameter(COMP_ID);

	    Map<String, String> param = new HashMap<String, String>();
	    param.put(COMP_ID, compId);

	    List<ConvertVO> rows = convertService.getErrorList(param);

	    for (ConvertVO vo : rows) {
		try {
		    if (documentService.convert(vo, true)) {
			convertService.removeError(vo);
		    }
		} catch (Exception e) {
		}
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.getWriter().write(result.toString());
    }
}
