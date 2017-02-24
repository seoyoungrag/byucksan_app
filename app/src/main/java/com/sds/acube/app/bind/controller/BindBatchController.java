package com.sds.acube.app.bind.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindBatchService;
import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;


/**
 * Class Name : BindBatchController.java <br> Description : 편철 일괄생성 관련 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 6. 27. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 6. 27.
 * @version  1.0
 * @see  com.sds.acube.app.bind.controller.BindBatchController.java
 */

@Controller("bindBatchController")
@RequestMapping("/app/bind/batch/*.do")
public class BindBatchController extends BindBaseController {

    private static final long serialVersionUID = -3765779518986749894L;

    Log logger = LogFactory.getLog(BindBatchController.class);

    /**
	 */
    @Inject
    @Named("bindBatchService")
    private BindBatchService bindBatchService;


    /**
     * <pre> 
     *  편철 일괄생성 목록
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/batch/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String option = YEAR_SESSION.equals(this.getSearchOption(compId)) ? YEAR : PERIOD;

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);

	List<BatchVO> rows = bindBatchService.getList(param);

	int target = 0;

	if (rows.size() > 0) {
	    BatchVO batchVO = rows.get(0);
	    target = Integer.parseInt(batchVO.getYear());
	} else {
	    // 등록되지 않았을 경우 (미리 default를 등록 했음.)
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.YEAR, 1);
	    target = cal.get(Calendar.YEAR);
	}

	int period = Integer.parseInt(envOptionAPIService.getCurrentPeriodStr(compId));
	int year = period;
	if (target >= period) {
	    year++;
	}

	ModelAndView mav = new ModelAndView("BindBatchController.list");
	mav.addObject(ROWS, rows);
	mav.addObject(OPTION, option);
	mav.addObject(YEAR, String.valueOf(year));
	mav.addObject(PERIOD, envOptionAPIService.getCurrentPeriodStr(compId));
	mav.addObject(TARGET, target);
	return mav;
    }


    /**
     * <pre> 
     *  편철 일괄생성 - 생성
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/batch/execute.do")
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String year = request.getParameter(YEAR);

	    String batchType = AppConfig.getProperty("bindBatchType", DEFAULT, "etc");

	    Map<String, String> params = new HashMap<String, String>();
	    params.put(COMP_ID, compId);
	    params.put(CREATE_YEAR, year);
	    params.put(EXPIRE_YEAR, year);
	    params.put(CREATED, DateUtil.getCurrentDate(DEFAULT_DATE_FORMAT));
	    params.put(CREATED_ID, super.getUserId(request));
	    params.put(BEFORE_YEAR, String.valueOf(Integer.parseInt(year) - 1));

	    long start = System.currentTimeMillis();

	    String period = envOptionAPIService.getCurrentPeriodStr(compId);
	    envOptionAPIService.insertPeriodAuto(compId, period);

	    int returnValue = bindBatchService.execute(params, batchType);

	    long end = System.currentTimeMillis();

	    if (returnValue > 0) {
		insertBatch(compId, year, start, end);
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 일괄생성 - 배치로그 테이블에 등록
     * </pre>
     * 
     * @param compId
     * @param year
     * @param start
     * @param end
     * @throws Exception
     * @see
     */
    private void insertBatch(String compId, String year, long start, long end) throws Exception {
	String startDate = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT).format(start);
	String endDate = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT).format(end);

	BatchVO vo = new BatchVO();
	vo.setCompId(compId);
	vo.setStartDate(startDate);
	vo.setEndDate(endDate);
	vo.setYear(year);
	vo.setExecuteTime(end - start);

	bindBatchService.insert(vo);
    }


    /**
     * <pre> 
     *  편철 일괄생성 - 삭제
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/batch/remove.do")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String year = request.getParameter(YEAR);

	    Map<String, String> params = new HashMap<String, String>();
	    params.put(COMP_ID, compId);
	    params.put(CREATE_YEAR, year);
	    params.put(YEAR, year);

	    // TGW_APP_BIND 삭제
	    bindBatchService.remove(params);
	    // TGW_APP_BIND_BATCH 삭제
	    bindBatchService.removeBatch(params);

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.getWriter().write(result.toString());
    }
}
