package com.sds.acube.app.bind.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.AppBindVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindDocumentService;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindDocVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : BindDocumentController.java <br> Description : 편철에 등록된 문서정보 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 16. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 5. 16.
 * @version  1.0
 * @see  com.sds.acube.app.bind.controller.BindDocumentController.java
 */

@Controller("bindDocumentController")
@RequestMapping("/app/bind/document/*.do")
public class BindDocumentController extends BindBaseController {

    private static final long serialVersionUID = 4133122445086950164L;

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

    /**
	 */
    @Inject
    @Named("bindDocumentService")
    private BindDocumentService bindDocumentService;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;

    /**
	 */
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;

    /**
	 */
    @Autowired
    private ICommonService commonService;


    /**
     * <pre> 
     *  편철에 등록된 문서 목록
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String compId = super.getCompId(request);
	String deptId = request.getParameter(TARGET_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String searchYear = request.getParameter(SEARCH_YEAR);

	String bindName = null;

	BindVO bindVO = null;
	if (StringUtils.isEmpty(bindId)) {
	    Map<String, String> param = new HashMap<String, String>();
	    param.put(COMP_ID, compId);
	    param.put(DEPT_ID, deptId);
	    param.put(CREATE_YEAR, searchYear);
	    
	    // 다국어 추가
	    param.put("langType", AppConfig.getCurrentLangType());
	    List<BindVO> rows = bindService.getSelectListResource(param);
	    // List<BindVO> rows = bindService.getSelectList(param);
	    
	    if (rows.size() > 0) {
			bindVO = rows.get(0);
			bindId = bindVO.getBindId();
			bindName = bindVO.getBindName();
	    }
	} else {
		// 다국어 추가
		bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
	    // bindVO = bindService.getMinor(compId, deptId, bindId);
		
	    bindName = bindVO.getBindName();
	}

	String orgBindId = null;
	if (bindVO != null) {
	    orgBindId = bindVO.getOrgBindId();
	}

	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_VALUE), EMPTY);
	if (BIND_NAME.equals(searchType)) {
	    searchType = "title";
	    searchValue = "";
	}
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	String pageSize = envOptionAPIService.selectOptionText(compId, OPT424);
	
	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, StringUtils.defaultIfEmpty(orgBindId, bindId));
	param.put(PAGE_NO, pageNo);
	//param.put(LIST_NUM, DEFAULT_COUNT);
	//param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put(LIST_NUM, pageSize);
	param.put(SCREEN_COUNT, pageSize);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> rows = bindDocumentService.getDocumentList(param);

	String lobCode = appCode.getProperty("LOL001", "LOL001", "LOL");

	SearchVO searchVO = new SearchVO();
	searchVO.setLobCode(lobCode);

	ModelAndView mav = new ModelAndView("BindDocumentController.list");
	mav.addObject(ROWS, rows);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, super.getDeptName(orgService, deptId));
	mav.addObject(BIND_ID, bindId);
	mav.addObject(BIND_NAME, bindName);
	mav.addObject(UNIT_NAME, bindVO == null ? "" : bindVO.getUnitName());
	mav.addObject(PAGE_NO, pageNo);
	mav.addObject(SEARCH_TYPE, super.getDocumentOptions(super.getLocale(request)));
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(BINDING, bindVO == null ? N : bindVO.getBinding());
	mav.addObject("SearchVO", searchVO);
	mav.addObject(SEND_TYPE, bindVO == null ? "" : bindVO.getSendType());
	return mav;
    }


    /**
     * <pre> 
     *  미편철함에 등록된 문서목록
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/nonBind.do")
    public ModelAndView nonBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), EMPTY);
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_VALUE), EMPTY);
	// 부서선택 버튼 감추기
	String prev = StringUtils.defaultIfEmpty(request.getParameter("prev"), "manager");
	String startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	String endDate = StringUtil.null2str(request.getParameter("endDate"), "");

	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
	Locale locale = super.getLocale(request);
	Map<String, String> searhYearOption = new LinkedHashMap<String, String>();
	searhYearOption.put("", m.getMessage("bind.obj.select", null, locale));
	searhYearOption.putAll(super.getSearchYear(compId, locale));

	String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));

	// 연도/회기별 조회 조건에 따라 검색한다.
	if ("Y".equals(searchDefaultType) && !"".equals(searchYear)) {
	    // 연도 및 회기의 정보에 따라 검색한다.
	    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, searchYear, startDate, endDate);
	    startDate = (String) returnInfor.get("startDate");
	    endDate = (String) returnInfor.get("endDate");
	} else {

	    // 기본 조건에 의한 날짜 반환
	    ListUtil defaultListUtil = new ListUtil();
	    String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	    HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	    startDate = (String) returnDate.get("startDate");
	    endDate = (String) returnDate.get("endDate");
	}

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	String pageSize = envOptionAPIService.selectOptionText(compId, OPT424);
	
	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(PAGE_NO, pageNo);
	//param.put(LIST_NUM, DEFAULT_COUNT);
	//param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put(LIST_NUM, pageSize);
	param.put(SCREEN_COUNT, pageSize);
	param.put("startdate", startDate);
	param.put("enddate", endDate);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getNonBindList(param);

	String lobCode = appCode.getProperty("LOL001", "LOL001", "LOL");

	SearchVO searchVO = new SearchVO();
	searchVO.setLobCode(lobCode);

	ModelAndView mav = new ModelAndView("BindDocumentController.nonBind");
	mav.addObject(ROWS, binds);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, super.getDeptName(orgService, deptId));
	mav.addObject(SEARCH_YEAR, searhYearOption);
	mav.addObject(SEARCH_TYPE, super.getDocumentOptions(super.getLocale(request)));
	mav.addObject(TYPE, searchType);
	mav.addObject(YEAR, searchYear);
	mav.addObject(VALUE, searchValue);
	mav.addObject("SearchVO", searchVO);
	mav.addObject("prev", prev);
	mav.addObject("startDate", startDate);
	mav.addObject("endDate", endDate);
	return mav;
    }


    /**
     * <pre> 
     *  편철함 문서목록 프린트
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/print.do")
    public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String compId = super.getCompId(request);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(URLDecoder.decode(request.getParameter(SEARCH_VALUE), UTF8), EMPTY);

	BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	String orgBindId = null;
	if (bindVO != null) {
	    orgBindId = bindVO.getOrgBindId();
	}

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, StringUtils.defaultIfEmpty(orgBindId, bindId));
	param.put(PAGE_NO, String.valueOf(1));
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	param.put(LIST_NUM, String.valueOf(page_size_max));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getDocumentList(param);

	ModelAndView mav = new ModelAndView("BindDocumentController.print");
	mav.addObject(ROWS, binds);
	return mav;
    }


    /**
     * <pre> 
     *  미편철함 문서목록 프린트
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/nonBindPrint.do")
    public ModelAndView nonBindPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), DateUtil.getCurrentYear());
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(URLDecoder.decode(request.getParameter(SEARCH_VALUE), UTF8), EMPTY);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(PAGE_NO, String.valueOf(1));
	param.put(LIST_NUM, String.valueOf(1000));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put("startdate", super.getStartDate(searchYear, compId));
	param.put("enddate", super.getEndDate(searchYear, compId));

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getNonBindList(param);

	ModelAndView mav = new ModelAndView("BindDocumentController.nonBindPrint");
	mav.addObject(ROWS, binds);
	return mav;
    }


    /**
     * <pre> 
     *  문서 이동 - 편철 선택 페이지
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/move.do")
    public ModelAndView move(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), String.valueOf(super.getCurrentYear()));
		String compId = super.getCompId(request);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
	
		String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
		Locale locale = super.getLocale(request);
	
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(CREATE_YEAR, searchYear);
		param.put(PAGE_NO, pageNo);
		param.put(LIST_NUM, DEFAULT_COUNT);
		param.put(SCREEN_COUNT, DEFAULT_COUNT);
		param.put(BINDING, N);
		
		// 다국어 추가
		param.put("langType", AppConfig.getCurrentLangType());
		List<BindVO> rows = bindService.getSelectListResource(param);
		// List<BindVO> rows = bindService.getSelectList(param);
	
		ModelAndView mav = new ModelAndView("BindDocumentController.move");
		mav.addObject(SEARCH_YEAR, super.getSearchYear(compId, locale));
		mav.addObject(YEAR, searchYear);
		mav.addObject(DOC_ID, request.getParameter(DOC_ID));
		mav.addObject(BIND_ID, request.getParameter(BIND_ID));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(SEARCH_OPTION, super.getSearchOption(compId));
		mav.addObject(ROWS, rows);
		return mav;
    }


    /**
     * <pre> 
     *  선택한 편철로 문서 이동
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/movement.do")
    public void movement(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String orgBindId = request.getParameter(ORG_BIND_ID);
	    String bindId = request.getParameter(BIND_ID);
	    String deptId = request.getParameter(DEPT_ID);
	    if (StringUtils.isEmpty(deptId)) {
		deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	    }

	    String compId = super.getCompId(request);
	    String deptName = super.getDeptName(request);
	    String userId = super.getUserId(request);
	    String userName = super.getUserName(request);
	    String userIp = super.getIPAddress(request);
	    String pos = super.getPos(request);
	    Locale locale = super.getLocale(request);

	    BindVO orgBindVO = bindService.getMinor(compId, deptId, orgBindId);
	    String orgBindName = orgBindVO == null ? "" : orgBindVO.getBindName();

	    BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	    String bindName = bindVO.getBindName();

	    List<AppBindVO> appBindVOs = new ArrayList<AppBindVO>();

	    boolean isAll = ALL.equals(request.getParameter(DOC_ID));

	    if (isAll) { // 전체이동
		String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
		String searchValue = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_VALUE), EMPTY);

		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(BIND_ID, orgBindId);
		param.put(PAGE_NO, String.valueOf(1));
		param.put(LIST_NUM, String.valueOf(1000000));
		param.put(SCREEN_COUNT, DEFAULT_COUNT);

		if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
		    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
			searchValue = EMPTY;
		    } else {
			param.put(searchType, searchValue);
		    }
		}

		List<BindDocVO> rows = bindDocumentService.getDocumentList(param);

		for (BindDocVO vo : rows) {
		    // 문서관리 연계큐에 추가
		    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
		    queueToDocmgr.setDocId(vo.getDocId());
		    queueToDocmgr.setCompId(compId);
		    queueToDocmgr.setTitle(vo.getTitle());
		    String DHU017 = appCode.getProperty("DHU017", "DHU017", "DHU");
		    queueToDocmgr.setChangeReason(DHU017);
		    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
		    queueToDocmgr.setProcState(bps001);
		    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
		    queueToDocmgr.setRegistDate(DateUtil.getCurrentDate());
		    queueToDocmgr.setUsingType(vo.getDocType());
		    commonService.insertQueueToDocmgr(queueToDocmgr);

		    String docId = vo.getDocId();
		    vo.setCompId(compId);
		    vo.setBindId(bindId);

		    int resultCount = bindDocumentService.move(vo);

		    if (resultCount > 0) {
			AppBindVO appBindVO = new AppBindVO();
			appBindVO.setCompId(compId);
			appBindVO.setBindingId(bindId);
			appBindVO.setBindingName(bindName);
			appBindVO.setDocId(docId);
			appBindVOs.add(appBindVO);
		    }
		}
	    } else { // 개별이동
		String[] docIds = request.getParameter(DOC_ID).split(",");

		for (String docId : docIds) {
		    BindDocVO vo = bindDocumentService.getDocument(compId, docId);

		    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
		    queueToDocmgr.setDocId(vo.getDocId());
		    queueToDocmgr.setCompId(compId);
		    queueToDocmgr.setTitle(vo.getTitle());
		    String DHU017 = appCode.getProperty("DHU017", "DHU017", "DHU");
		    queueToDocmgr.setChangeReason(DHU017);
		    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
		    queueToDocmgr.setProcState(bps001);
		    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
		    queueToDocmgr.setRegistDate(DateUtil.getCurrentDate());
		    queueToDocmgr.setUsingType(vo.getDocType());
		    commonService.insertQueueToDocmgr(queueToDocmgr);

		    vo.setBindId(bindId);

		    int resultCount = bindDocumentService.move(vo);

		    if (resultCount > 0) {
			AppBindVO appBindVO = new AppBindVO();
			appBindVO.setCompId(compId);
			appBindVO.setBindingId(bindId);
			appBindVO.setBindingName(bindName);
			appBindVO.setDocId(docId);
			appBindVOs.add(appBindVO);
		    }
		}
	    }

	    DocHisVO hisVO = new DocHisVO();
	    hisVO.setCompId(compId);
	    hisVO.setDeptId(deptId);
	    hisVO.setDeptName(deptName);
	    hisVO.setUserId(userId);
	    hisVO.setUserName(userName);
	    hisVO.setUserIp(userIp);
	    hisVO.setPos(pos); // 직위
	    // 미편철에서 bindName 으로 이동
	    String remark = orgBindName + m.getMessage("bind.msg.move.from", null, locale) + " " + bindName
		    + m.getMessage("bind.msg.move.to", null, locale);
	    hisVO.setRemark(remark);

	    appComService.updateBindInfo(appBindVOs, hisVO);

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getLocalizedMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  미편철함에서 편철함 선택 후 문서이동
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/selectBind.do")
    public void selectBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String[] docIds = request.getParameter(DOC_IDS).split(",");
	String bindId = request.getParameter(BIND_ID);
	String deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	String deptName = super.getDeptName(request);
	String compId = super.getCompId(request);
	String userId = super.getUserId(request);
	String userName = super.getUserName(request);
	String userIp = super.getIPAddress(request);
	String pos = super.getPos(request);
	Locale locale = super.getLocale(request);

	JSONObject result = new JSONObject();

	try {
	    BindVO row = bindService.getMinor(compId, deptId, bindId);
	    String bindName = row.getBindName();

	    List<AppBindVO> appBindVOs = new ArrayList<AppBindVO>();

	    for (String docId : docIds) {
		BindDocVO vo = bindDocumentService.getDocument(compId, docId);

		QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
		queueToDocmgr.setDocId(vo.getDocId());
		queueToDocmgr.setCompId(compId);
		queueToDocmgr.setTitle(vo.getTitle());
		String DHU017 = appCode.getProperty("DHU017", "DHU017", "DHU");
		queueToDocmgr.setChangeReason(DHU017);
		String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
		queueToDocmgr.setProcState(bps001);
		queueToDocmgr.setProcDate("9999-12-31 23:59:59");
		queueToDocmgr.setRegistDate(DateUtil.getCurrentDate());
		queueToDocmgr.setUsingType(vo.getDocType());
		commonService.insertQueueToDocmgr(queueToDocmgr);

		AppBindVO appBindVO = new AppBindVO();
		appBindVO.setBindingId(bindId);
		appBindVO.setBindingName(bindName);
		appBindVO.setCompId(compId);
		appBindVO.setDocId(docId);

		appBindVOs.add(appBindVO);
	    }

	    DocHisVO hisVO = new DocHisVO();
	    hisVO.setCompId(compId);
	    hisVO.setDeptId(deptId);
	    hisVO.setDeptName(deptName);
	    hisVO.setUserId(userId);
	    hisVO.setUserName(userName);
	    hisVO.setUserIp(userIp);
	    hisVO.setPos(pos); // 직위
	    // 미편철에서 bindName 으로 이동
	    String remark = m.getMessage("bind.msg.move.from.nonbind", null, locale) + " " + bindName
		    + m.getMessage("bind.msg.move.to", null, locale);
	    hisVO.setRemark(remark);

	    appComService.updateBindInfo(appBindVOs, hisVO);

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getLocalizedMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  인계 편철함 - 문서 목록
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/transfer.do")
    public ModelAndView transfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String compId = super.getCompId(request);
	String deptId = request.getParameter(TARGET_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_VALUE), EMPTY);

	List<BindDocVO> rows = null;
	String orgBindId = "";
	String bindName = "";
	String unitName = "";

	if (StringUtils.isNotEmpty(bindId)) {
	    BindVO vo = bindService.getMinor(compId, deptId, bindId);

	    bindName = vo.getBindName();
	    unitName = vo.getUnitName();
	    
	    String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	    String pageSize = envOptionAPIService.selectOptionText(compId, OPT424);

	    Map<String, String> param = new HashMap<String, String>();
	    param.put(COMP_ID, compId);
	    param.put(DEPT_ID, deptId);
	    param.put(BIND_ID, StringUtils.defaultIfEmpty(vo.getOrgBindId(), bindId));
	    param.put(PAGE_NO, pageNo);
	    //param.put(LIST_NUM, DEFAULT_COUNT);
	    //param.put(SCREEN_COUNT, DEFAULT_COUNT);
	    param.put(LIST_NUM, pageSize);
	    param.put(SCREEN_COUNT, pageSize);

	    if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
		param.put(searchType, searchValue);
	    }

	    rows = bindDocumentService.getTransferList(param);
	}

	OrganizationVO orgVO = orgService.selectOrganization(deptId);
	String deptName = orgVO.getOrgName();

	ModelAndView mav = new ModelAndView("BindDocumentController.transfer");
	mav.addObject(ROWS, rows);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, deptName);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(ORG_BIND_ID, orgBindId);
	mav.addObject(BIND_NAME, bindName);
	mav.addObject(UNIT_NAME, unitName);
	mav.addObject(PAGE_NO, pageNo);
	mav.addObject(SEARCH_TYPE, super.getDocumentOptions(super.getLocale(request)));
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	return mav;
    }


    /**
     * <pre> 
     *  인계 편철함 - 문서 목록 프린트
     * </pre>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/document/transferPrint.do")
    public ModelAndView transferPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String compId = super.getCompId(request);
	String deptId = request.getParameter(TARGET_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(URLDecoder.decode(request.getParameter(SEARCH_VALUE), UTF8), EMPTY);

	BindVO vo = bindService.getMinor(compId, deptId, bindId);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, StringUtils.defaultIfEmpty(vo.getOrgBindId(), bindId));
	param.put(PAGE_NO, String.valueOf(1));
	param.put(LIST_NUM, String.valueOf(1000));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    param.put(searchType, searchValue);
	}

	List<BindDocVO> rows = bindDocumentService.getTransferList(param);

	ModelAndView mav = new ModelAndView("BindDocumentController.transferPrint");
	mav.addObject(ROWS, rows);
	return mav;
    }
}
