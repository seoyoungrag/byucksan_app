package com.sds.acube.app.bind.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.AppBindVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindHistoryService;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.bind.vo.BindBatchVO;
import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.AppCodeVO;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.list.service.IListEtcService;


/**
 * Class Name : BindController.java <br> Description : 편철 관련 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.controller.BindController.java
 */

@Controller("bindController")
@RequestMapping("/app/bind/*.do")
public class BindController extends BindBaseController {

    private static final long serialVersionUID = -6436078092721813865L;

    Log logger = LogFactory.getLog(BindController.class);

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

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

    /*
    @Inject
    @Named("folderService")
    private IFolderService folderService;
    */

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;


    /**
     * <pre> 
     *  편철 목록 가져오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	}

	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}

	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}
	String deptName = super.getDeptName(orgService, deptId);
	Locale locale = super.getLocale(request);
	String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, DEFAULT_COUNT);
	param.put(SCREEN_COUNT, DEFAULT_COUNT);
	
	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	BatchVO batchVO = bindService.getBindSearchYear(compId);

	// 다국어 추가
	param.put("langType", AppConfig.getCurrentLangType());
	//List<BindVO> binds = bindService.getListResource(param);
	List<BindVO> binds = bindService.getList(param);
	
	ModelAndView mav = new ModelAndView("BindController.list");
	
	mav.addObject(ROWS, binds);
	mav.addObject(YEAR, searchYear);
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(DTYPE, docType);
	mav.addObject(SEARCH_YEAR, getSearchYear(compId, batchVO.getYear(), locale));
	mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
	mav.addObject(DOC_TYPE, super.getDocumentType(locale));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
	mav.addObject(PAGE_NO, pageNo);
	
	String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
	
	mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
	return mav;
    }


    /**
     * <pre> 
     *  편철 목록 프린트
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/print.do")
    public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	}

	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}
	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String deptName = super.getDeptName(orgService, deptId);
	Locale locale = super.getLocale(request);
	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, "1");
	param.put(LIST_NUM, "1000");
	param.put(SCREEN_COUNT, "1");

	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}
	// 다국어 추가
	param.put("langType", AppConfig.getCurrentLangType());
	//List<BindVO> binds = bindService.getListResource(param);
	List<BindVO> binds = bindService.getList(param);

	ModelAndView mav = new ModelAndView("BindController.print");
	mav.addObject(ROWS, binds);
	mav.addObject(YEAR, searchYear);
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(DTYPE, docType);
	mav.addObject(SEARCH_YEAR, getSearchYear(compId, locale));
	mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
	mav.addObject(DOC_TYPE, super.getDocumentType(locale));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
	mav.addObject(PAGE_NO, pageNo);
	return mav;
    }


    /**
     * <pre> 
     *  편철 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/add.do")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	long bindDepth = UtilRequest.getInt(request, "bindDepth" ,0);

	String year = null;
	String option = null;
	if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
	    year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	    option = YEAR;
	} else {
	    year = envOptionAPIService.getCurrentPeriodStr(compId);
	    option = PERIOD;
	}

	String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
	
	String deptId = request.getParameter(TARGET_ID);
	String bindId = request.getParameter(BIND_ID);
	
	OrganizationVO deptVO = orgService.selectOrganization(deptId);
	String deptName = "";
	if (deptVO != null) {
	    deptName = deptVO.getOrgName();
	}
	
	BindUnitVO vo = new BindUnitVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	
	// 다국어 추가
	vo.setLangType(AppConfig.getCurrentLangType());
	//List<BindUnitVO> bindUnitList = bindService.getBindUnitListResource(vo);
	List<BindUnitVO> bindUnitList = bindService.getBindUnitList(vo);
	
	BindVO bindVO = new BindVO();
	bindService.setInfoManagerAndAuth(bindVO, bindId, compId, deptId);

	ModelAndView mav = new ModelAndView("BindController.add");
	
	mav.addObject("adminInfo", bindVO.getAdminInfo());
	mav.addObject("authInfo", bindVO.getAuthInfo());
	mav.addObject("cabAuthList", envOptionAPIService.listAppCode(BindVO.CABAUTH));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_NAME, deptName);
	mav.addObject("bindDepth", bindDepth);
	mav.addObject(COMP_ID, compId);
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	mav.addObject(DEFAULT, defaultRetenionPeriod);
	mav.addObject(OPTION, option);
	mav.addObject(YEAR, year);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DOC_TYPE, super.getDocumentType(super.getLocale(request)));
	//mav.addObject("bindUnitList", bindUnitList);
	return mav;
    }
    
    /**
     * <pre> 
     *  편철 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/addShare.do")
    public ModelAndView addShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	long bindDepth = UtilRequest.getInt(request, "bindDepth" ,0);

	String year = null;
	String option = null;
	if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
	    year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	    option = YEAR;
	} else {
	    year = envOptionAPIService.getCurrentPeriodStr(compId);
	    option = PERIOD;
	}

	String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
	
	String deptId = request.getParameter(TARGET_ID);
	String bindId = request.getParameter(BIND_ID);
	
	OrganizationVO deptVO = orgService.selectOrganization(deptId);
	String deptName = "";
	if (deptVO != null) {
	    deptName = deptVO.getOrgName();
	}
	
	BindUnitVO vo = new BindUnitVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	
	// 다국어 추가
	vo.setLangType(AppConfig.getCurrentLangType());
	//List<BindUnitVO> bindUnitList = bindService.getBindUnitListResource(vo);
	List<BindUnitVO> bindUnitList = bindService.getBindUnitList(vo);
	
	BindVO bindVO = new BindVO();
	bindService.setInfoManagerAndAuth(bindVO, bindId, compId, deptId);

	ModelAndView mav = new ModelAndView("BindController.addShare");
	
	mav.addObject("adminInfo", bindVO.getAdminInfo());
	mav.addObject("authInfo", bindVO.getAuthInfo());
	mav.addObject("cabAuthList", envOptionAPIService.listAppCode(BindVO.CABAUTH));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_NAME, deptName);
	mav.addObject("bindDepth", bindDepth);
	mav.addObject(COMP_ID, compId);
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	mav.addObject(DEFAULT, defaultRetenionPeriod);
	mav.addObject(OPTION, option);
	mav.addObject(YEAR, year);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DOC_TYPE, super.getDocumentType(super.getLocale(request)));
	//mav.addObject("bindUnitList", bindUnitList);
	return mav;
    }


    /**
     * <pre> 
     *  편철 수정 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/edit.do")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bindId = request.getParameter(BIND_ID);
		String deptId = request.getParameter(DEPT_ID);
		String unitType = request.getParameter(UNIT_TYPE);
		String compId = super.getCompId(request);
	
		String year = null;
		String option = null;
		if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		    year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		    option = YEAR;
		} else {
		    year = envOptionAPIService.getCurrentPeriodStr(compId);
		    option = PERIOD;
		}
	
		// 다국어 추가
		//BindVO bindVO = bindService.getResource(compId, deptId, bindId, unitType, AppConfig.getCurrentLangType());
		BindVO bindVO = bindService.get(compId, deptId, bindId, unitType);
	
		ModelAndView mav = new ModelAndView("BindController.edit");
		
		OrganizationVO deptVO = orgService.selectOrganization(deptId);
		
		String deptName = "";
		if (deptVO != null) {
		    deptName = deptVO.getOrgName();
		}
		 
		List<AppCodeVO> cabAuthList = envOptionAPIService.listAppCode(BindVO.CABAUTH);
		mav.addObject("cabAuthList", cabAuthList);
		mav.addObject(DEPT_NAME, deptName);
		
		mav.addObject(ROW, bindVO);
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
		mav.addObject(OPTION, option);
		mav.addObject(YEAR, year);
		mav.addObject(DOC_TYPE, super.getDocumentType(super.getLocale(request)));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject("selectSendType", request.getParameter("selectSendType"));
		
		/*BindUnitVO vo = new BindUnitVO();
		vo.setCompId(compId);
		vo.setDeptId(deptId);
		
		// 다국어 추가
		vo.setLangType(AppConfig.getCurrentLangType());
		List<BindUnitVO> bindUnitList = bindService.getBindUnitListResource(vo);
		// List<BindUnitVO> bindUnitList = bindService.getBindUnitList(vo);
		mav.addObject("bindUnitList", bindUnitList);*/
		
		return mav;
    }
    
    /**
     * <pre> 
     *  캐비닛 수정 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/editShare.do")
    public ModelAndView editShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bindId = request.getParameter(BIND_ID);
		String deptId = request.getParameter(DEPT_ID);
		String unitType = request.getParameter(UNIT_TYPE);
		String compId = super.getCompId(request);
	
		String year = null;
		String option = null;
		if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		    year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		    option = YEAR;
		} else {
		    year = envOptionAPIService.getCurrentPeriodStr(compId);
		    option = PERIOD;
		}
	
		// 다국어 추가
		//BindVO bindVO = bindService.getResource(compId, deptId, bindId, unitType, AppConfig.getCurrentLangType());
		BindVO bindVO = bindService.getShare(compId, deptId, bindId, unitType);
	
		ModelAndView mav = new ModelAndView("BindController.editShare");
		
		OrganizationVO deptVO = orgService.selectOrganization(deptId);
		
		String deptName = "";
		if (deptVO != null) {
		    deptName = deptVO.getOrgName();
		}
		 
		List<AppCodeVO> cabAuthList = envOptionAPIService.listAppCode(BindVO.CABAUTH);
		mav.addObject("cabAuthList", cabAuthList);
		mav.addObject(DEPT_NAME, deptName);
		
		mav.addObject(ROW, bindVO);
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
		mav.addObject(OPTION, option);
		mav.addObject(YEAR, year);
		mav.addObject(DOC_TYPE, super.getDocumentType(super.getLocale(request)));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject("selectSendType", request.getParameter("selectSendType"));
		
		/*BindUnitVO vo = new BindUnitVO();
		vo.setCompId(compId);
		vo.setDeptId(deptId);
		
		// 다국어 추가
		vo.setLangType(AppConfig.getCurrentLangType());
		List<BindUnitVO> bindUnitList = bindService.getBindUnitListResource(vo);
		// List<BindUnitVO> bindUnitList = bindService.getBindUnitList(vo);
		mav.addObject("bindUnitList", bindUnitList);*/
		
		return mav;
    }


    /**
     * <pre> 
     *  전자결재에서 편철 선택하는 화면 호출
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/select.do")
    public ModelAndView select(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		searchYear = String.valueOf(super.getCurrentYear());
	    } else {
		searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	    }
	}

	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}
	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
	String serialNumber = request.getParameter(SERIAL_NUMBER);
	if (StringUtils.isEmpty(serialNumber)) {
	    serialNumber = null;
	} else {
	    if ("0".equals(serialNumber)) {
		serialNumber = Y;
	    } else if ("-1".equals(serialNumber)) {
		serialNumber = N;
	    }
	}

	Locale locale = super.getLocale(request);

	/*Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, "10000");
	param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put(BINDING, N);
	param.put(ARRANGE, N);
	param.put(SERIAL_NUMBER, serialNumber);
	// 다국어 추가
	param.put("langType", AppConfig.getCurrentLangType());*/
	
	BindVO bindVO = new BindVO();
	
	bindVO.setCompId(compId);
	bindVO.setDeptId(deptId);
	bindVO.setCreateYear(searchYear);
	bindVO.setBinding(N);
	bindVO.setArrange(N);
	bindVO.setLangType(AppConfig.getCurrentLangType());

	//List<BindVO> rows = bindService.getSelectTargetListResource(param);
	//List<BindVO> rows = bindService.getSelectTargetList(param);
	BindVO[] bindTreeArray = bindService.getSelectTargetTreeList(bindVO);

	ModelAndView mav = new ModelAndView("BindController.select");
	mav.addObject(SEARCH_YEAR, super.getSearchYear(compId, locale));
	mav.addObject(YEAR, searchYear);
	mav.addObject(DOC_ID, request.getParameter(DOC_ID));
	mav.addObject(BIND_ID, request.getParameter(BIND_ID));
	mav.addObject(SEARCH_OPTION, super.getSearchOption(compId));
	mav.addObject(SERIAL_NUMBER, serialNumber);
	//mav.addObject(ROWS, rows);
	mav.addObject("tree", bindTreeArray); 
	return mav;
    }


    /**
     * <pre> 
     *  전자결재에서 편철 선택하는 화면 호출
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/selectShare.do")
    public ModelAndView selectShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//TODO
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		searchYear = String.valueOf(super.getCurrentYear());
	    } else {
		searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	    }
	}

	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}
	String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
	String serialNumber = request.getParameter(SERIAL_NUMBER);
	if (StringUtils.isEmpty(serialNumber)) {
	    serialNumber = null;
	} else {
	    if ("0".equals(serialNumber)) {
		serialNumber = Y;
	    } else if ("-1".equals(serialNumber)) {
		serialNumber = N;
	    }
	}

	Locale locale = super.getLocale(request);

	/*Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, "10000");
	param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put(BINDING, N);
	param.put(ARRANGE, N);
	param.put(SERIAL_NUMBER, serialNumber);
	// 다국어 추가
	param.put("langType", AppConfig.getCurrentLangType());*/
	
	BindVO bindVO = new BindVO();
	
	bindVO.setCompId(compId);
	bindVO.setDeptId(deptId);
	bindVO.setCreateYear(searchYear);
	bindVO.setBinding(N);
	bindVO.setArrange(N);
	bindVO.setLangType(AppConfig.getCurrentLangType());

	//List<BindVO> rows = bindService.getSelectTargetListResource(param);
	//List<BindVO> rows = bindService.getSelectTargetList(param);

	bindVO.setBindType(CommonUtil.nullTrim(request.getParameter("bindType")));
	BindVO[] bindTreeArray = bindService.getSelectTreeBindShare(bindVO);

	ModelAndView mav = new ModelAndView("BindController.select");
	mav.addObject(SEARCH_YEAR, super.getSearchYear(compId, locale));
	mav.addObject(YEAR, searchYear);
	mav.addObject(DOC_ID, request.getParameter(DOC_ID));
	mav.addObject(BIND_ID, request.getParameter(BIND_ID));
	mav.addObject(SEARCH_OPTION, super.getSearchOption(compId));
	mav.addObject(SERIAL_NUMBER, serialNumber);
	//mav.addObject(ROWS, rows);
	mav.addObject("tree", bindTreeArray); 
	return mav;
    }
    /**
     * <pre> 
     *  편철 지정 목록에 나타나는 트리
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/selectList.do")
    public void selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List<JSONObject> result = new ArrayList<JSONObject>();
	String compId = super.getCompId(request);
	String createYear = request.getParameter(ID);

	if (ROOT_ID.equals(createYear)) {
	    List<String> years = getSearchList(compId);

	    for (int i = years.size(); 0 < i; i--) {
		String text = years.get(i - 1);

		JSONObject obj = new JSONObject();
		obj.put(TEXT, text);
		obj.put(ID, text);
		obj.put(ORDERED, i);
		obj.put(ICON_CLS, FOLDER);
		obj.put(LEAF, false);
		if (i == 1) {
		    obj.put(EXPANDED, true);
		}
		result.add(obj);
	    }
	} else {
	    String deptId = request.getParameter(DEPT_ID);
	    if (StringUtils.isEmpty(deptId)) {
		deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	    }

	    Map<String, String> param = new HashMap<String, String>();
	    param.put(COMP_ID, compId);
	    param.put(DEPT_ID, deptId);
	    param.put(CREATE_YEAR, createYear);

	    // 다국어 추가
	    param.put("langType", AppConfig.getCurrentLangType());
	    //List<BindVO> rows = bindService.getSelectListResource(param);
	    List<BindVO> rows = bindService.getSelectList(param);

	    for (BindVO vo : rows) {
		JSONObject obj = new JSONObject();
		obj.put(TEXT, vo.getBindName());
		obj.put(ORIGINAL_TEXT, CommonUtil.decodeEscapedSpecialChar(vo.getBindName()));
		obj.put(ID, vo.getBindId());
		obj.put(UNIT_ID, vo.getUnitId());
		obj.put(UNIT_NAME, vo.getUnitName());
		obj.put(RETENTION_PERIOD, vo.getRetentionPeriod());
		obj.put(DISPLAY_NAME, m.getMessage("bind.code." + vo.getRetentionPeriod(), null, super.getLocale(request)));
		obj.put(ORDERED, vo.getOrdered());
		obj.put(ICON_CLS, "icon-book");
		obj.put(LEAF, true);
		result.add(obj);
	    }
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 생성
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/create.do")
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);
	    String unitId = request.getParameter(UNIT_ID);
	    String unitType = request.getParameter(UNIT_TYPE);
	    String bindName = request.getParameter(BIND_NAME);
	    String docType = request.getParameter(DOC_TYPE);
	    String createYear = request.getParameter(CREATE_YEAR);
	    String expireYear = request.getParameter(EXPIRE_YEAR);
	    String retentionPeriod = request.getParameter(RETENTION_PERIOD);
	    String prefix = request.getParameter(PREFIX);
	    String description  = request.getParameter(DESCRIPTION);
	    String adminInfo = request.getParameter("adminInfo");
	    String authInfo = request.getParameter("authInfo");
	    String parentBindId = request.getParameter("parentBindId");
    	long bindDepth = UtilRequest.getInt(request, "bindDepth" ,0);
    	long bindOrder = UtilRequest.getInt(request, "bindOrder" ,0);

	    // 다국어 추가
	    //boolean isExist = bindService.existResource(compId, deptId, bindName, createYear, null, AppConfig.getCurrentLangType());
	    boolean isExist = bindService.exist(compId, deptId, bindName, createYear, null);

	    if (isExist) {
			result.put(SUCCESS, false);
			result.put(MESSAGE_ID, "bind_create");
			result.put(MESSAGE, m.getMessage("bind.msg.not.bind.create.samename", null, getLocale(request)));
	    } else {
			String currentTime = super.getCurrentTime();
	
			String bindId = bindService.getNextBindId();
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setDeptId(deptId);
			vo.setBindId(bindId);
			vo.setUnitId(unitId);
			vo.setUnitType(unitType);
			vo.setBindName(CommonUtil.escapeSpecialChar(bindName));
			vo.setDocType(docType);
			vo.setCreateYear(createYear);
			vo.setExpireYear(expireYear);
			vo.setRetentionPeriod(retentionPeriod);
			vo.setCreated(currentTime);
			vo.setCreatedId(super.getUserId(request));
			vo.setPrefix(prefix);
			vo.setDescription(description);
			vo.setAdminInfo(adminInfo);
			vo.setAuthInfo(authInfo);
			vo.setParentBindId(parentBindId);
			vo.setBindDepth(bindDepth);
			vo.setBindOrder(bindOrder);
			
		    //문서관리서비스 분리 jth8172 20120404
			int suc = bindService.insert(vo);
	/*
		    boolean isSuccess = folderService.create(vo);
			if (isSuccess) {
			    bindService.insert(vo);
			}
	*/		if(suc >0) {
				result.put(SUCCESS, true);
				result.put("bindId", bindId);
			}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     * 캬비닛 생성
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/createShare.do")
    public void createShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);
	    String unitId = request.getParameter(UNIT_ID);
	    String unitType = request.getParameter(UNIT_TYPE);
	    String bindName = request.getParameter(BIND_NAME);
	    String docType = request.getParameter(DOC_TYPE);
	    String createYear = request.getParameter(CREATE_YEAR);
	    String expireYear = request.getParameter(EXPIRE_YEAR);
	    String retentionPeriod = request.getParameter(RETENTION_PERIOD);
	    String prefix = request.getParameter(PREFIX);
	    String description  = request.getParameter(DESCRIPTION);
	    String adminInfo = request.getParameter("adminInfo");
	    String authInfo = request.getParameter("authInfo");
	    String parentBindId = request.getParameter("parentBindId");
    	long bindDepth = UtilRequest.getInt(request, "bindDepth" ,0);
    	long bindOrder = UtilRequest.getInt(request, "bindOrder" ,0);

	    // 다국어 추가
	    //boolean isExist = bindService.existResource(compId, deptId, bindName, createYear, null, AppConfig.getCurrentLangType());
	    boolean isExist = bindService.existShare(compId, deptId, bindName, createYear, null);

	    if (isExist) {
			result.put(SUCCESS, false);
			result.put(MESSAGE_ID, "bind_create");
			result.put(MESSAGE, m.getMessage("bind.msg.not.bind.create.samename", null, getLocale(request)));
	    } else {
			String currentTime = super.getCurrentTime();
	
			String bindId = bindService.getNextBindId();
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setDeptId(deptId);
			vo.setBindId(bindId);
			vo.setUnitId(unitId);
			vo.setUnitType(unitType);
			vo.setBindName(CommonUtil.escapeSpecialChar(bindName));
			vo.setDocType(docType);
			vo.setCreateYear(createYear);
			vo.setExpireYear(expireYear);
			vo.setRetentionPeriod(retentionPeriod);
			vo.setCreated(currentTime);
			vo.setCreatedId(super.getUserId(request));
			vo.setPrefix(prefix);
			vo.setDescription(description);
			vo.setAdminInfo(adminInfo);
			vo.setAuthInfo(authInfo);
			vo.setParentBindId(parentBindId);
			vo.setBindDepth(bindDepth);
			vo.setBindOrder(bindOrder);
			
		    //문서관리서비스 분리 jth8172 20120404
			int suc = bindService.insertShare(vo);
	/*
		    boolean isSuccess = folderService.create(vo);
			if (isSuccess) {
			    bindService.insert(vo);
			}
	*/		if(suc >0) {
				result.put(SUCCESS, true);
				result.put("bindId", bindId);
			}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 수정
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/update.do")
    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);
	    String unitId = request.getParameter(UNIT_ID);
	    String originalUnitType = request.getParameter("originalUnitType");
	    String modifiedUnitType = request.getParameter("modifiedUnitType");
	    String bindId = request.getParameter(BIND_ID);
	    String bindName = request.getParameter(BIND_NAME);
	    String docType = request.getParameter(DOC_TYPE);
	    String createYear = request.getParameter(CREATE_YEAR);
	    String expireYear = request.getParameter(EXPIRE_YEAR);
	    String retentionPeriod = request.getParameter(RETENTION_PERIOD);
	    String prefix = request.getParameter(PREFIX);
	    String description = request.getParameter("description");
	    String adminInfo = request.getParameter("adminInfo");
	    String origAdminInfo = request.getParameter("origAdminInfo");
	    String authInfo = request.getParameter("authInfo");
	    String origAuthInfo = request.getParameter("origAuthInfo");
	    long bindOrder = UtilRequest.getInt(request, "bindOrder" ,0);
	    
	    // 다국어 추가
	    //boolean isExist = bindService.existResource(compId, deptId, bindName, createYear, bindId, AppConfig.getCurrentLangType());
	    boolean isExist = bindService.exist(compId, deptId, bindName, createYear, bindId);

	    if (isExist) {
			result.put(SUCCESS, false);
			result.put(MESSAGE_ID, "bind_update");
			result.put(MESSAGE, m.getMessage("bind.msg.not.bind.create.samename", null, getLocale(request)));
	    } else {
	    	// 다국어 추가
	    	//BindVO orgVO = bindService.getResource(compId, deptId, bindId, originalUnitType, AppConfig.getCurrentLangType());
			BindVO orgVO = bindService.get(compId, deptId, bindId, originalUnitType);
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setModifiedId(super.getUserId(request));
			vo.setModified(super.getCurrentTime());
			vo.setDeptId(deptId);
			vo.setUnitId(unitId);
			vo.setUnitType(originalUnitType);
			vo.setModifiedUnitType(modifiedUnitType);
			vo.setBindId(bindId);
			vo.setBindName(CommonUtil.escapeSpecialChar(bindName));
			vo.setDocType(docType);
			vo.setCreateYear(createYear);
			vo.setExpireYear(expireYear);
			vo.setRetentionPeriod(retentionPeriod);
			vo.setCurrentTime(super.getCurrentTime());
			vo.setPrefix(prefix);
		    vo.setDescription(description);
			vo.setOrdered(orgVO.getOrdered());
			vo.setAdminInfo(adminInfo);
			vo.setOrigAdminInfo(origAdminInfo);
			vo.setAuthInfo(authInfo);
			vo.setOrigAuthInfo(origAuthInfo);
			vo.setBindOrder(bindOrder);
			
			int rtn = bindHistoryService.insert(vo);
			if (rtn > 0) {			
				//편철 수정 webservice와 분리  jd.park 20120412
				int suc = bindService.update(vo);
	
				if(suc>0){
					result.put(SUCCESS, true);	
				}
				
				//편철 수정 webservice 주석 jdpark 20120412 
				/*
			    boolean isSuccess = folderService.update(vo);
	
			    if (isSuccess) {
				bindService.update(vo);
			    }
				 */
		
			} else {
			    result.put(SUCCESS, false);
			    result.put(MESSAGE, m.getMessage("bind.msg.error", null, super.getLocale(request)));
			}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     * 문서관리 수정
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/updateShare.do")
    public void updateShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);
	    String unitId = request.getParameter(UNIT_ID);
	    String originalUnitType = request.getParameter("originalUnitType");
	    String modifiedUnitType = request.getParameter("modifiedUnitType");
	    String bindId = request.getParameter(BIND_ID);
	    String bindName = request.getParameter(BIND_NAME);
	    String docType = request.getParameter(DOC_TYPE);
	    String createYear = request.getParameter(CREATE_YEAR);
	    String expireYear = request.getParameter(EXPIRE_YEAR);
	    String retentionPeriod = request.getParameter(RETENTION_PERIOD);
	    String prefix = request.getParameter(PREFIX);
	    String description = request.getParameter("description");
	    String adminInfo = request.getParameter("adminInfo");
	    String origAdminInfo = request.getParameter("origAdminInfo");
	    String authInfo = request.getParameter("authInfo");
	    String origAuthInfo = request.getParameter("origAuthInfo");
	    long bindOrder = UtilRequest.getInt(request, "bindOrder" ,0);
	    
	    // 다국어 추가
	    //boolean isExist = bindService.existResource(compId, deptId, bindName, createYear, bindId, AppConfig.getCurrentLangType());
	    boolean isExist = bindService.exist(compId, deptId, bindName, createYear, bindId);

	    if (isExist) {
			result.put(SUCCESS, false);
			result.put(MESSAGE_ID, "bind_update");
			result.put(MESSAGE, m.getMessage("bind.msg.not.bind.create.samename", null, getLocale(request)));
	    } else {
	    	// 다국어 추가
	    	//BindVO orgVO = bindService.getResource(compId, deptId, bindId, originalUnitType, AppConfig.getCurrentLangType());
			BindVO orgVO = bindService.getShare(compId, deptId, bindId, originalUnitType);
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setModifiedId(super.getUserId(request));
			vo.setModified(super.getCurrentTime());
			vo.setDeptId(deptId);
			vo.setUnitId(unitId);
			vo.setUnitType(originalUnitType);
			vo.setModifiedUnitType(modifiedUnitType);
			vo.setBindId(bindId);
			vo.setBindName(CommonUtil.escapeSpecialChar(bindName));
			vo.setDocType(docType);
			vo.setCreateYear(createYear);
			vo.setExpireYear(expireYear);
			vo.setRetentionPeriod(retentionPeriod);
			vo.setCurrentTime(super.getCurrentTime());
			vo.setPrefix(prefix);
		    vo.setDescription(description);
			vo.setOrdered(orgVO.getOrdered());
			vo.setAdminInfo(adminInfo);
			vo.setOrigAdminInfo(origAdminInfo);
			vo.setAuthInfo(authInfo);
			vo.setOrigAuthInfo(origAuthInfo);
			vo.setBindOrder(bindOrder);
			
			int rtn = 1;//bindHistoryService.insert(vo);
			if (rtn > 0) {			
				//편철 수정 webservice와 분리  jd.park 20120412
				int suc = bindService.updateShare(vo);
	
				if(suc>0){
					result.put(SUCCESS, true);	
				}
				
				//편철 수정 webservice 주석 jdpark 20120412 
				/*
			    boolean isSuccess = folderService.update(vo);
	
			    if (isSuccess) {
				bindService.update(vo);
			    }
				 */
		
			} else {
			    result.put(SUCCESS, false);
			    result.put(MESSAGE, m.getMessage("bind.msg.error", null, super.getLocale(request)));
			}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 조회
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/view.do")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String searchYear = request.getParameter(SEARCH_YEAR);
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(TARGET_ID);
	String unitType = request.getParameter(UNIT_TYPE);
	String compId = super.getCompId(request);

	String option = null;
	if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
	    option = YEAR;
	} else {
	    option = PERIOD;
	}

	// 다국어 추가
	//BindVO bindVO = bindService.getResource(compId, deptId, bindId, unitType, AppConfig.getCurrentLangType());	
	BindVO bindVO = bindService.get(compId, deptId, bindId, unitType);

	// 공유 편철일 경우
	String _deptId = null;
	if (SEND_TYPES.DST004.name().equals(bindVO.getSendType())) {
	    _deptId = bindVO.getSendDeptId();
	} else {
	    _deptId = bindVO.getDeptId();
	}

	OrganizationVO deptVO = orgService.selectOrganization(_deptId);
	if (deptVO != null) {
	    String deptName = deptVO.getOrgName();
	    bindVO.setDeptName(deptName);
	}

	String sendDeptId = bindVO.getSendDeptId();

	if (StringUtils.isNotEmpty(sendDeptId)) {
	    OrganizationVO orgVO = orgService.selectOrganization(sendDeptId);
	    if (orgVO != null && StringUtil.isNotEmpty(orgVO.getOrgName())) {
		bindVO.setSendDeptName(orgVO.getOrgName());
	    }
	}

	ModelAndView mav = new ModelAndView("BindController.view");
	mav.addObject(ROW, bindVO);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(SEARCH_YEAR, searchYear);
	mav.addObject(SEARCH_TYPE, searchType);
	mav.addObject(SEARCH_VALUE, searchValue);
	mav.addObject(DOC_TYPE, docType);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(OPTION, option);
	return mav;
    }


    /**
     * <pre> 
     *  편철 인계 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/transfer.do")
    public ModelAndView transfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String deptId = request.getParameter(TARGET_ID);
	String unitType = request.getParameter(UNIT_TYPE);
	String compId = super.getCompId(request);

	// 다국어 추가
	//BindVO bindVO = bindService.getResource(compId, deptId, bindId, unitType, AppConfig.getCurrentLangType());	
	BindVO bindVO = bindService.get(compId, deptId, bindId, unitType);

	ModelAndView mav = new ModelAndView("BindController.transfer");
	mav.addObject(ROW, bindVO);
	mav.addObject(COMP_NAME, super.getCompName(request));
	return mav;
    }

    /**
     * <pre> 
     *  편철 인계 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindTransfer.do")
    public ModelAndView bindTransfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String deptId = request.getParameter(TARGET_ID);
	String unitType = request.getParameter(UNIT_TYPE);
	String compId = super.getCompId(request);

	// 다국어 추가
	//BindVO bindVO = bindService.getResource(compId, deptId, bindId, unitType, AppConfig.getCurrentLangType());	
	BindVO bindVO = bindService.get(compId, deptId, bindId, unitType);

	ModelAndView mav = new ModelAndView("BindController.transfer");
	mav.addObject(ROW, bindVO);
	mav.addObject(COMP_NAME, super.getCompName(request));
	return mav;
    }
    
    /**
     * <pre> 
     *  편철 인계 트리
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/getTransferTree.do")
    public void getTransferTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List<JSONObject> result = new ArrayList<JSONObject>();

	String orgId = request.getParameter(ID);
	if (ROOT_ID.equals(orgId)) {
	    orgId = super.getCompId(request);
	}

	List<DepartmentVO> departmentVOList = orgService.selectOrgSubTreeList(orgId, 0);

	for (int i = 0; i < departmentVOList.size(); i++) {
	    DepartmentVO dept = departmentVOList.get(i);

	    // OrganizationVO org =
	    // orgService.selectOrganization(dept.getOrgID());

	    JSONObject obj = new JSONObject();
	    obj.put(TEXT, dept.getOrgName());
	    obj.put(ID, dept.getOrgID());

	    // FIXME 하위 부서 존재 여부
	    obj.put(LEAF, false);
	    // obj.put(LEAF, "U".equals(dept.getHasChild()));

	    // obj.put(ICON_CLS, org.getIsProcess() ? FOLDER : FOLDER_GRAY);
	    // if (!org.getIsProcess()) {
	    // obj.put(QTIP, m.getMessage("bind.msg.bind.hasno.dept", null,
	    // super.getLocale(request)));
	    // }
	    // // 편철함 유무
	    // obj.put(BIND, org.getIsProcess());

	    obj.put(ICON_CLS, FOLDER);
	    obj.put(BIND, true);

	    result.add(obj);

	    // logger.debug(dept.getOrgName() + " : " + dept.getOrgID() + " : "
	    // + dept.getHasChild() + " : " + org.getIsProcess());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 인계
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/transpose.do")
    public void transpose(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String bindId = request.getParameter(BIND_ID);
	    String deptId = request.getParameter(DEPT_ID);
	    String targetId = request.getParameter(TARGET_ID);
	    String compId = super.getCompId(request);
	    String deptName = super.getDeptName(orgService, deptId);

	    // 다국어 추가
	    //BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType()); 
	    BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 
	    
	    bindVO.setTargetId(targetId);
	    bindVO.setModifiedId(super.getUserId(request));
	    bindVO.setCurrentTime(super.getCurrentTime());

	    int rtnval = bindService.transfer(bindVO);

	    if (rtnval == 1) {
		AppBindVO appBindVO = new AppBindVO();
		appBindVO.setCompId(compId);
		if(StringUtils.isEmpty(bindVO.getOrgBindId())){
		    appBindVO.setBindingId(bindId);
		}else{
		    appBindVO.setBindingId(bindVO.getOrgBindId());
		}
		appBindVO.setBindingName(bindVO.getBindName());
		appBindVO.setSourceDeptId(deptId);
		appBindVO.setSourceDeptName(deptName);
		appBindVO.setTargetDeptId(targetId);
		appBindVO.setTargetDeptName(super.getDeptName(orgService, targetId));

		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setCompId(compId);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUserId(super.getUserId(request));
		docHisVO.setUserName(super.getUserName(request));
		docHisVO.setUserIp(super.getIPAddress(request));

		appComService.moveBind(appBindVO, docHisVO);
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }

    /**
     * <pre> 
     *  편철 인계
     * </pre>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/bind/bindTranspose.do")
    public void bindTranspose(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String targetId = request.getParameter(DEPT_ID);
	    String bindId = request.getParameter(TARGET_IDS);
	    String deptId = request.getParameter(DEPT_IDS);
	    
	    String compId = super.getCompId(request);
	    String deptName = super.getDeptName(orgService, deptId);

	    // 다국어 추가
	    //BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType()); 
	    BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 
	    List<BindVO> bindList = new ArrayList<BindVO>();
	    String returnValue = bindService.getTransposeTreeList(bindVO, bindList);
		if(!SUCCESS.equals(returnValue)){
		    result.put(SUCCESS, false);
		    if(TRANSPOSE_BIND_ERROR_DST3.equals(returnValue))
		    	result.put(MESSAGE, TRANSPOSE_BIND_MESSAGE_DST3);
		    else if(TRANSPOSE_BIND_ERROR_DST4.equals(returnValue))
		    	result.put(MESSAGE, TRANSPOSE_BIND_MESSAGE_DST4);
		    else if(TRANSPOSE_BIND_ERROR_BINDING.equals(returnValue))
		    	result.put(MESSAGE, TRANSPOSE_BIND_MESSAGE_BINDING);
		    else if(TRANSPOSE_BIND_ERROR_SHARED.equals(returnValue))
		    	result.put(MESSAGE, TRANSPOSE_BIND_MESSAGE_SHARED);
		    else if(TRANSPOSE_BIND_ERROR_DOC_PROCESS.equals(returnValue))
		    	result.put(MESSAGE, TRANSPOSE_BIND_MESSAGE_DOC_PROCESS);
		}
		else {
			for(BindVO bind: bindList){
				bind.setTargetId(targetId);
				bind.setModifiedId(super.getUserId(request));
				bind.setCurrentTime(super.getCurrentTime());
		
			    int rtnval = bindService.bindTransfer(bind);
		
			    if (rtnval == 1) {
					AppBindVO appBindVO = new AppBindVO();
					appBindVO.setCompId(compId);
					if(StringUtils.isEmpty(bind.getOrgBindId())){
					    appBindVO.setBindingId(bindId);
					}else{
					    appBindVO.setBindingId(bind.getOrgBindId());
					}
					appBindVO.setBindingName(bind.getBindName());
					appBindVO.setSourceDeptId(deptId);
					appBindVO.setSourceDeptName(deptName);
					appBindVO.setTargetDeptId(targetId);
					appBindVO.setTargetDeptName(super.getDeptName(orgService, targetId));
			
					DocHisVO docHisVO = new DocHisVO();
					docHisVO.setCompId(compId);
					docHisVO.setDeptId(deptId);
					docHisVO.setDeptName(deptName);
					docHisVO.setUserId(super.getUserId(request));
					docHisVO.setUserName(super.getUserName(request));
					docHisVO.setUserIp(super.getIPAddress(request));
			
					appComService.moveBind(appBindVO, docHisVO);
			    }
			}
		    result.put(SUCCESS, true);
		}
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     *  편철업무 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/job.do")
    public ModelAndView job(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String deptId = request.getParameter(DEPT_ID);
	String searchYear = request.getParameter(SEARCH_YEAR);
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	String docType = request.getParameter(DOC_TYPE);
	String pageNo = request.getParameter(PAGE_NO);

	String compId = super.getCompId(request);

	// 다국어 추가
	//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
	BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	ModelAndView mav = new ModelAndView("BindController.job");
	mav.addObject(SEARCH_YEAR, searchYear);
	mav.addObject(SEARCH_TYPE, searchType);
	mav.addObject(SEARCH_VALUE, searchValue);
	mav.addObject(DOC_TYPE, docType);
	mav.addObject(PAGE_NO, pageNo);
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(ARRANGE, bindVO.getArrange());
	mav.addObject(BINDING, bindVO.getBinding());
	return mav;
    }
    
    @RequestMapping("/app/bind/bindJob.do")
    public ModelAndView bindJob(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String deptId = request.getParameter(DEPT_ID);

	String compId = super.getCompId(request);

	// 다국어 추가
	//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
	BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	ModelAndView mav = new ModelAndView("BindController.bindJob");;
	mav.addObject(BIND_ID, bindId);
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(ARRANGE, bindVO.getArrange());
	mav.addObject(BINDING, bindVO.getBinding());
	return mav;
    }


    /**
     * <pre> 
     *  편철업무 설정
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/actionJob.do")
    public void actionJob(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String bindId = request.getParameter(BIND_ID);
	    String deptId = request.getParameter(DEPT_ID);
	    String arrange = request.getParameter(ARRANGE);
	    String binding = request.getParameter(BINDING);
	    String isAll = request.getParameter("isAll");
	    String compId = super.getCompId(request);

	    if (Y.equals(binding)) {
		arrange = N;
	    } else if (Y.equals(arrange)) {
		binding = N;
	    }

	    BindVO vo = new BindVO();
	    vo.setCompId(super.getCompId(request));
	    vo.setDeptId(deptId);
	    vo.setBindId(bindId);
	    vo.setArrange(arrange);
	    vo.setBinding(binding);
	    vo.setCurrentTime(super.getCurrentTime());

	    // 다국어 추가
	    //BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
	    /*BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	    bindVO.setModifiedId(super.getUserId(request));
	    bindVO.setCurrentTime(super.getCurrentTime());
	    bindVO.setArrange(arrange);
	    bindVO.setBinding(binding);*/
	    if("Y".equals(isAll)){
	    	bindService.updateAll(vo);
	    }
	    else{
	    	bindService.update(vo);// emptyColor
	    }

	    // 문서관리 연계 제거
	    // boolean isSuccess = folderService.update(bindVO);
	    // if (isSuccess) {
	    // bindService.update(vo);
	    // }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 좌측 메뉴
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/menu.do")
    public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String defaultYear = envOptionAPIService.getCurrentPeriodStr(compId);
	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), defaultYear);
	String etcYear = StringUtils.defaultIfEmpty(request.getParameter("etcYear"), defaultYear);
	String transYear = StringUtils.defaultIfEmpty(request.getParameter(TRANS_YEAR), defaultYear);
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), BIND);

	Locale locale = super.getLocale(request);

	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(SEND_TYPE, SEND_TYPES.DST001.name());
	param.put(SERIAL_NUMBER, Y);

    // 다국어 추가
    param.put("langType", AppConfig.getCurrentLangType());
    //List<BindVO> bindRows = bindService.getSelectListResource(param);
	List<BindVO> bindRows = bindService.getSelectList(param);

	param.put(SERIAL_NUMBER, N);

	// 다국어 추가
	//List<BindVO> etcRows = bindService.getSelectListResource(param);
	List<BindVO> etcRows = bindService.getSelectList(param);

	param.put(CREATE_YEAR, transYear);
	param.put(SEND_TYPE, SEND_TYPES.DST003.name());
	param.put(SERIAL_NUMBER, null);

	// 다국어 추가
	//List<BindVO> tranRows = bindService.getSelectListResource(param);
	List<BindVO> tranRows = bindService.getSelectList(param);

	Map<String, String> yearOption = getSearchYear(compId, locale);

	ModelAndView mav = new ModelAndView("BindController.menu");
	mav.addObject(SEARCH_YEAR, yearOption);
	mav.addObject("etcYear", yearOption);
	mav.addObject(TRANS_YEAR, yearOption);
	mav.addObject(ROWS, bindRows);
	mav.addObject("etcRows", etcRows);
	mav.addObject("tranRows", tranRows);
	mav.addObject(YEAR, searchYear);
	mav.addObject("tyear", transYear);
	mav.addObject("eyear", etcYear);
	mav.addObject(SEARCH_TYPE, searchType);
	return mav;
    }

    /**
     * <pre> 
     *  편철 폐기
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/obsolete.do")
    public void obsolete(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String[] bindIds = request.getParameter(BIND_IDS).split(",");
	    String[] unitTypes = request.getParameter("unitTypes").split(",");

	    for (int i = 0; i < bindIds.length; i++) {
			String bindId = bindIds[i];
			String deptId = deptIds[i];
			String unitType = unitTypes[i];
			if (StringUtils.isEmpty(bindId)) {
			    continue;
			}
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setDeptId(deptId);
			vo.setBindId(bindId);
			vo.setUnitType(unitType);
	
			//편철 폐기 webservice와 분리  jd.park 20120412
			int suc =  bindService.remove(vo);
	
			if(suc>0){
				result.put(SUCCESS, true);	
			}
			
			//편철 폐기 webservice 주석 jdpark 20120412 
			/*
		   	boolean isSuccess = folderService.remove(compId, deptId, bindId);
			if (isSuccess) {
			    bindService.remove(vo);
				}
		    }
		    */
	    }		 
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }

    /**
     * <pre> 
     *  편철 폐기
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindObsolete.do")
    public void bindObsolete(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String[] bindIds = request.getParameter(BIND_IDS).split(",");
	    String[] unitTypes = request.getParameter("unitTypes").split(",");

	    for (int i = 0; i < bindIds.length; i++) {
			String bindId = bindIds[i];
			String deptId = deptIds[i];
			String unitType = unitTypes[i];
			if (StringUtils.isEmpty(bindId)) {
			    continue;
			}
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setDeptId(deptId);
			vo.setBindId(bindId);
			vo.setUnitType(unitType);
	
			//편철 폐기 webservice와 분리  jd.park 20120412
			String returnValue = bindService.removeBind(vo);
			if(!SUCCESS.equals(returnValue)){
			    result.put(SUCCESS, false);
			    if(REMOVE_BIND_ERROR_DST3.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_DST3);
			    else if(REMOVE_BIND_ERROR_DOC.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_DOC);
			    else if(REMOVE_BIND_ERROR_SHARED.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_SHARED);
			    break;
			}
			result.put(SUCCESS, true);	
			
			//편철 폐기 webservice 주석 jdpark 20120412 
			/*
		   	boolean isSuccess = folderService.remove(compId, deptId, bindId);
			if (isSuccess) {
			    bindService.remove(vo);
				}
		    }
		    */
	    }		 
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     *  캐비닛 폐기
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindShareObsolete.do")
    public void bindShareObsolete(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String[] bindIds = request.getParameter(BIND_IDS).split(",");
	    String[] unitTypes = request.getParameter("unitTypes").split(",");

	    for (int i = 0; i < bindIds.length; i++) {
			String bindId = bindIds[i];
			String deptId = deptIds[i];
			String unitType = unitTypes[i];
			if (StringUtils.isEmpty(bindId)) {
			    continue;
			}
	
			BindVO vo = new BindVO();
			vo.setCompId(compId);
			vo.setDeptId(deptId);
			vo.setBindId(bindId);
			vo.setUnitType(unitType);
	
			//편철 폐기 webservice와 분리  jd.park 20120412
			String returnValue = bindService.removeBindShare(vo);
			if(!SUCCESS.equals(returnValue)){
			    result.put(SUCCESS, false);
			    if(REMOVE_BIND_ERROR_DST3.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_DST3);
			    else if(REMOVE_BIND_ERROR_DOC.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_DOC);
			    else if(REMOVE_BIND_ERROR_SHARED.equals(returnValue))
			    	result.put(MESSAGE, REMOVE_BIND_MESSAGE_SHARED);
			    break;
			}
			result.put(SUCCESS, true);	
			
			//편철 폐기 webservice 주석 jdpark 20120412 
			/*
		   	boolean isSuccess = folderService.remove(compId, deptId, bindId);
			if (isSuccess) {
			    bindService.remove(vo);
				}
		    }
		    */
	    }		 
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }

    /**
     * <pre> 
     *  편철 공유
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/share.do")
    public void share(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String targetId = request.getParameter(DEPT_ID);
	    String[] bindIds = request.getParameter(TARGET_IDS).split(",");
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String currentTime = super.getCurrentTime();

	    for (int i = 0; i < bindIds.length; i++) {
		String bindId = bindIds[i];
		String deptId = deptIds[i];

		String nextBindId = bindService.getNextBindId();

		// 다국어 추가
		//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
		BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 

		bindVO.setNextBindId(nextBindId);
		bindVO.setCurrentTime(currentTime);
		bindVO.setTargetId(targetId);

		int share = bindService.share(bindVO);

		// if (share > 0) {
		// try {
		// String deptName = super.getDeptName(orgService, deptId);
		//
		// AppBindVO appBindVO = new AppBindVO();
		// appBindVO.setCompId(compId);
		// appBindVO.setBindingId(bindId);
		// appBindVO.setBindingName(bindVO.getBindName());
		// appBindVO.setSourceDeptId(deptId);
		// appBindVO.setSourceDeptName(deptName);
		// appBindVO.setTargetDeptId(targetId);
		// appBindVO.setTargetDeptName(super.getDeptName(orgService,
		// targetId));
		//
		// DocHisVO docHisVO = new DocHisVO();
		// docHisVO.setCompId(compId);
		// docHisVO.setDeptId(deptId);
		// docHisVO.setDeptName(deptName);
		// docHisVO.setUserId(super.getUserId(request));
		// docHisVO.setUserName(super.getUserName(request));
		// docHisVO.setUserIp(super.getIPAddress(request));
		//
		// appComService.copyBind(appBindVO, docHisVO);
		// } catch (Exception e) {
		// logger.error(e.getMessage());
		// }
		// }
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }

    /**
     * <pre> 
     *  편철 공유
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindShare.do")
    public void bindShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String targetId = request.getParameter(DEPT_ID);
	    String[] bindIds = request.getParameter(TARGET_IDS).split(",");
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String currentTime = super.getCurrentTime();

	    for (int i = 0; i < bindIds.length; i++) {
			String bindId = bindIds[i];
			String deptId = deptIds[i];
	
			//String nextBindId = bindService.getNextBindId();
	
			// 다국어 추가
			//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType());
			BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 
	
			//bindVO.setNextBindId(nextBindId);
			bindVO.setCurrentTime(currentTime);
			bindVO.setTargetId(targetId);
	
			String returnValue = bindService.shareBind(bindVO);
	
			if(!SUCCESS.equals(returnValue)){
			    result.put(SUCCESS, false);
			    if(SHARE_BIND_ERROR_DST3.equals(returnValue))
			    	result.put(MESSAGE, SHARE_BIND_MESSAGE_DST3);
			    else if(SHARE_BIND_ERROR_DST4.equals(returnValue))
			    	result.put(MESSAGE, SHARE_BIND_MESSAGE_DST4);
			    else if(SHARE_BIND_ERROR_UTT002.equals(returnValue))
			    	result.put(MESSAGE, SHARE_BIND_MESSAGE_UTT002);
			    break;
			}
	    }
	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     *  편철 공유 목록
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/sharedList.do")
    public ModelAndView sharedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		searchYear = String.valueOf(super.getCurrentYear());
	    } else {
		searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	    }
	}

	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}
	String deptName = super.getDeptName(orgService, deptId);
	Locale locale = super.getLocale(request);
	String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}

	String docType = request.getParameter(DOC_TYPE);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, DEFAULT_COUNT);
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	List<BindVO> rows = bindService.getSharedList(param);
	for (BindVO vo : rows) {
	    String _deptName = super.getDeptName(orgService, vo.getDeptId());
	    vo.setDeptName(_deptName);
	}

	ModelAndView mav = new ModelAndView("BindController.sharedList");
	mav.addObject(ROWS, rows);
	mav.addObject(YEAR, searchYear);
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(DTYPE, docType);
	mav.addObject(SEARCH_YEAR, getSearchYear(compId, locale));
	mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
	mav.addObject(DOC_TYPE, super.getDocumentType(locale));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
	mav.addObject(PAGE_NO, pageNo);
	return mav;
    }


    /**
     * <pre> 
     *  편철 공유 목록 프린트
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/sharedPrint.do")
    public ModelAndView sharedPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    if (YEAR_SESSION.equals(this.getSearchOption(compId))) {
		searchYear = String.valueOf(super.getCurrentYear());
	    } else {
		searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	    }
	}

	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}

	String deptName = super.getDeptName(orgService, deptId);
	Locale locale = super.getLocale(request);
	String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}

	String docType = request.getParameter(DOC_TYPE);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, pageNo);
	param.put(LIST_NUM, DEFAULT_COUNT);
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	List<BindVO> rows = bindService.getSharedList(param);
	for (BindVO vo : rows) {
	    String _deptName = super.getDeptName(orgService, vo.getDeptId());
	    vo.setDeptName(_deptName);
	}

	ModelAndView mav = new ModelAndView("BindController.sharedPrint");
	mav.addObject(ROWS, rows);
	mav.addObject(YEAR, searchYear);
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(DTYPE, docType);
	mav.addObject(SEARCH_YEAR, getSearchYear(compId, locale));
	mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
	mav.addObject(DOC_TYPE, super.getDocumentType(locale));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
	mav.addObject(PAGE_NO, pageNo);
	return mav;
    }

    /**
     * <pre> 
     *  편철 공유 해지
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/revocate.do")
    public void revocate(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String[] bindIds = request.getParameter(BIND_IDS).split(",");

	    for (int i = 0; i < bindIds.length; i++) {
		String bindId = bindIds[i];
		String deptId = deptIds[i];

		// 다국어 추가
		//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType()); 
		BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 

		int delCnt = bindService.removeShare(bindVO);

		// if (delCnt > 0) {
		// String deptName = super.getDeptName(orgService, deptId);
		//
		// AppBindVO appBindVO = new AppBindVO();
		// appBindVO.setCompId(compId);
		// appBindVO.setBindingId(bindId);
		// appBindVO.setBindingName(bindVO.getBindName());
		// appBindVO.setSourceDeptId(deptId);
		// appBindVO.setSourceDeptName(deptName);
		//
		// DocHisVO docHisVO = new DocHisVO();
		// docHisVO.setCompId(compId);
		// docHisVO.setDeptId(deptId);
		// docHisVO.setDeptName(deptName);
		// docHisVO.setUserId(super.getUserId(request));
		// docHisVO.setUserName(super.getUserName(request));
		// docHisVO.setUserIp(super.getIPAddress(request));
		//
		// appComService.withdrawBind(appBindVO, docHisVO);
		// }
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     *  편철 공유 해지
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindRevocate.do")
    public void revocateBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] deptIds = request.getParameter(DEPT_IDS).split(",");
	    String[] bindIds = request.getParameter(BIND_IDS).split(",");

	    for (int i = 0; i < bindIds.length; i++) {
		String bindId = bindIds[i];
		String deptId = deptIds[i];

		// 다국어 추가
		//BindVO bindVO = bindService.getMinorResource(compId, deptId, bindId, AppConfig.getCurrentLangType()); 
		BindVO bindVO = bindService.getMinor(compId, deptId, bindId); 

		bindService.removeShareBind(bindVO);

		// if (delCnt > 0) {
		// String deptName = super.getDeptName(orgService, deptId);
		//
		// AppBindVO appBindVO = new AppBindVO();
		// appBindVO.setCompId(compId);
		// appBindVO.setBindingId(bindId);
		// appBindVO.setBindingName(bindVO.getBindName());
		// appBindVO.setSourceDeptId(deptId);
		// appBindVO.setSourceDeptName(deptName);
		//
		// DocHisVO docHisVO = new DocHisVO();
		// docHisVO.setCompId(compId);
		// docHisVO.setDeptId(deptId);
		// docHisVO.setDeptName(deptName);
		// docHisVO.setUserId(super.getUserId(request));
		// docHisVO.setUserName(super.getUserName(request));
		// docHisVO.setUserIp(super.getIPAddress(request));
		//
		// appComService.withdrawBind(appBindVO, docHisVO);
		// }
	    }

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 순서 변경 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/order.do")
    public ModelAndView ordered(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String searchYear = request.getParameter(SEARCH_YEAR);
	if (StringUtils.isEmpty(searchYear)) {
	    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
	}

	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}

	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(DEPT_ID);
	if (StringUtils.isEmpty(deptId)) {
	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	}
	String deptName = super.getDeptName(orgService, deptId);
	Locale locale = super.getLocale(request);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, "1");
	param.put(LIST_NUM, "10000");
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	BatchVO batchVO = bindService.getBindSearchYear(compId);

	// 다국어 추가
	param.put("langType", AppConfig.getCurrentLangType());
	//List<BindVO> binds = bindService.getListResource(param);	
	List<BindVO> binds = bindService.getList(param);

	ModelAndView mav = new ModelAndView("BindController.order");
	mav.addObject(ROWS, binds);
	mav.addObject(YEAR, searchYear);
	mav.addObject(TYPE, searchType);
	mav.addObject(VALUE, searchValue);
	mav.addObject(DTYPE, docType);
	mav.addObject(SEARCH_YEAR, getSearchYear(compId, batchVO.getYear(), locale));
	mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
	mav.addObject(DOC_TYPE, super.getDocumentType(locale));
	mav.addObject(DEPT_ID, deptId);
	mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
	return mav;
    }


    /**
     * <pre> 
     *  편철 순서 변경
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/actionOrder.do")
    public void actionOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);
	    if (StringUtils.isEmpty(deptId)) {
		deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
	    }

	    String[] bindIds = request.getParameter(BIND_IDS).split(",");

	    for (int i = 0; i < bindIds.length; i++) {
		String bindId = bindIds[i];

		bindService.bindOrder(compId, deptId, bindId, i + 1);

		//list.add(bindService.get(compId, deptId, bindId)); // emptyColor.2012.05.18 unitType
	    }
	    //편철 순서 변경 webservice 주석 jd.park 20120412
	    /*
	    try {	    	
	    	folderService.order(compId, deptId, list.toArray(new BindVO[0]));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		*/
	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철에 속한 문서 중 진행중인 문서 건수 알아오기
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/getBindCount.do")
    public void getBindCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String bindId = request.getParameter(BIND_ID);

	    int bindCount = listEtcService.getAppIngBindCount(compId, bindId);

	    result.put(SUCCESS, true);
	    result.put("bindCount", bindCount);

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 자동 생성
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/autoCreate.do")
    public void autoCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String deptId = request.getParameter(DEPT_ID);

	    String period = envOptionAPIService.getCurrentPeriodStr(compId);

	    Map<String, String> params = new HashMap<String, String>();
	    params.put(COMP_ID, compId);
	    params.put(DEPT_ID, deptId);
	    params.put(PERIOD, period);
	    params.put(CREATED, DateUtil.getCurrentDate(DEFAULT_DATE_FORMAT));
	    params.put(CREATED_ID, super.getUserId(request));

	    bindService.autoCreate(params);

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 일괄 생성 관리 - 목록
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindBatchList.do")
    public ModelAndView bindBatchList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(COMP_ID, super.getCompId(request));

	List<BindBatchVO> rows = bindService.getBindBatchTargetList(params);

	ModelAndView mav = new ModelAndView("BindController.bindBatchList");
	mav.addObject(ROWS, rows);
	return mav;
    }


    /**
     * <pre> 
     *  편철 일괄 생성 관리 - 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindBatchAdd.do")
    public ModelAndView bindBatchAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);

	String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
	
	String deptId = super.getDeptId(request);
	
	BindUnitVO vo = new BindUnitVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	
	// 다국어 추가
	vo.setLangType(AppConfig.getCurrentLangType());
	//List<BindUnitVO> bindUnitList = bindService.getBindUnitListResource(vo);
	List<BindUnitVO> bindUnitList = bindService.getBindUnitList(vo);

	ModelAndView mav = new ModelAndView("BindController.bindBatchAdd");
	mav.addObject(COMP_ID, compId);
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	mav.addObject(DEFAULT, defaultRetenionPeriod);
	mav.addObject("bindUnitList", bindUnitList);
	return mav;
    }

    /**
     * <pre> 
     *  편철 일괄생성 관리 - 수정 (키값이 없으므로 수정페이지 사용안함)
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindBatchEdit.do")
    public ModelAndView bindBatchEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String compId = super.getCompId(request);
	String unitId = request.getParameter(UNIT_ID);
	String bindName = request.getParameter(BIND_NAME);

	BindBatchVO row = bindService.getBindBatchTarget(compId, unitId, bindName);

	ModelAndView mav = new ModelAndView("BindController.bindBatchEdit");
	mav.addObject(COMP_ID, compId);
	mav.addObject(ROW, row);
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	return mav;
    }

    /**
     * <pre> 
     *  편철 일괄생성 관리 - 등록
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindBatchCreate.do")
    public void bindBatchCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String unitId = request.getParameter(UNIT_ID);
	    String bindName = request.getParameter(BIND_NAME);
	    String retentionPeriod = request.getParameter(RETENTION_PERIOD);
	    String prefix = request.getParameter(PREFIX);
	    String unitType = request.getParameter(UNIT_TYPE);

	    Map<String, String> params = new HashMap<String, String>();
	    params.put(COMP_ID, compId);
	    params.put(UNIT_ID, unitId);
	    params.put(DISPLAY_NAME, bindName);
	    params.put(RETENTION_PERIOD, retentionPeriod);
	    params.put(PREFIX, prefix);
	    params.put(UNIT_TYPE, unitType);

	    if (bindService.bindBatchExist(params)) {
		result.put(SUCCESS, false);
		result.put(MESSAGE, m.getMessage("bind.msg.not.bind.create.samename", null, super.getLocale(request)));
		result.put(MESSAGE_ID, "duplicate");
	    } else {
		bindService.bindBatchCreate(params);
		result.put(SUCCESS, true);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    String msg = e.getMessage();
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, msg);
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  편철 일괄생성 관리 - 삭제
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/bindBatchDelete.do")
    public void bindBatchDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String compId = super.getCompId(request);
	    String[] bindNames = request.getParameter(BIND_NAME).split(",");
	    String[] unitTypes = request.getParameter(UNIT_TYPE).split(",");

	    for (int i = 0; i < unitTypes.length; i++) {
			String bindName = bindNames[i];
			String unitType = unitTypes[i];	
			Map<String, String> params = new HashMap<String, String>();
			params.put(COMP_ID, compId);
			params.put(UNIT_TYPE, unitType);
			params.put(DISPLAY_NAME, bindName);
	
			bindService.bindBatchRemove(params);
	    }

	    result.put(SUCCESS, true);

	} catch (Exception e) {
	    e.printStackTrace();
	    String msg = e.getMessage();
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, msg);
	}

	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    
    @RequestMapping("/app/bind/bindEtc.do")
    public ModelAndView bindEtc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String compId = super.getCompId(request);
	
		String searchYear = request.getParameter(SEARCH_YEAR);
		if (StringUtils.isEmpty(searchYear)) {
		    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
		}
	
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String docType = request.getParameter(DOC_TYPE);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		Locale locale = super.getLocale(request);
		String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
	
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(CREATE_YEAR, searchYear);
		param.put(PAGE_NO, pageNo);
		param.put(LIST_NUM, DEFAULT_COUNT);
		param.put(SCREEN_COUNT, DEFAULT_COUNT);
		
		if (DOC_TYPE.equals(searchType)) {
		    param.put(DOC_TYPE, docType);
		} else {
		    param.put(searchType, searchValue);
		}
	
		BatchVO batchVO = bindService.getBindSearchYear(compId);
	
		// 다국어 추가
		param.put("langType", AppConfig.getCurrentLangType());
		//List<BindVO> binds = bindService.getListResource(param);
		List<BindVO> binds = bindService.getList2(param);
		
		ModelAndView mav = new ModelAndView("BindController.bindEtc");
		
		mav.addObject(ROWS, binds);
		mav.addObject(YEAR, searchYear);
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		mav.addObject(DTYPE, docType);
		mav.addObject(SEARCH_YEAR, getSearchYear(compId, batchVO.getYear(), locale));
		mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
		mav.addObject(DOC_TYPE, super.getDocumentType(locale));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
		return mav;
	}
    
    /**
     * <pre> 
     *  편철 목록 가져오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     */
    @RequestMapping("/app/bind/bindManager.do")
    public ModelAndView bindManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String compId = super.getCompId(request);
	
		String searchYear = request.getParameter(SEARCH_YEAR);
		if (StringUtils.isEmpty(searchYear)) {
		    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
		}
	
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String docType = request.getParameter(DOC_TYPE);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		Locale locale = super.getLocale(request);
		String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
	
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(CREATE_YEAR, searchYear);
		param.put(PAGE_NO, pageNo);
		param.put(LIST_NUM, DEFAULT_COUNT);
		param.put(SCREEN_COUNT, DEFAULT_COUNT);
		
		if (DOC_TYPE.equals(searchType)) {
		    param.put(DOC_TYPE, docType);
		} else {
		    param.put(searchType, searchValue);
		}
	
		BatchVO batchVO = bindService.getBindSearchYear(compId);
	
		// 다국어 추가
		param.put("langType", AppConfig.getCurrentLangType());
		//List<BindVO> binds = bindService.getListResource(param);
		// List<BindVO> binds = bindService.getList(param);
		
		ModelAndView mav = new ModelAndView("BindController.bindManager");
		
		//mav.addObject(ROWS, binds);
		mav.addObject(YEAR, searchYear);
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		mav.addObject(DTYPE, docType);
		mav.addObject(SEARCH_YEAR, getSearchYear(compId, batchVO.getYear(), locale));
		mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
		mav.addObject(DOC_TYPE, super.getDocumentType(locale));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		mav.addObject("orgDeptId", request.getParameter("orgDeptId"));
		
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
		return mav;
    }
    
    /**
     * <pre> 
     *  편철 목록 가져오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     */
    @RequestMapping("/app/bind/bindShareManager.do")
    public ModelAndView bindShareManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String compId = super.getCompId(request);
	
		String searchYear = request.getParameter(SEARCH_YEAR);
		if (StringUtils.isEmpty(searchYear)) {
		    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
		}
	
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String docType = request.getParameter(DOC_TYPE);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		Locale locale = super.getLocale(request);
		String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
	
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(CREATE_YEAR, searchYear);
		param.put(PAGE_NO, pageNo);
		param.put(LIST_NUM, DEFAULT_COUNT);
		param.put(SCREEN_COUNT, DEFAULT_COUNT);
		
		if (DOC_TYPE.equals(searchType)) {
		    param.put(DOC_TYPE, docType);
		} else {
		    param.put(searchType, searchValue);
		}
	
		BatchVO batchVO = bindService.getBindSearchYear(compId);
	
		// 다국어 추가
		param.put("langType", AppConfig.getCurrentLangType());
		//List<BindVO> binds = bindService.getListResource(param);
		// List<BindVO> binds = bindService.getList(param);
		
		ModelAndView mav = new ModelAndView("BindController.bindShareManager");
		
		//mav.addObject(ROWS, binds);
		mav.addObject(YEAR, searchYear);
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		mav.addObject(DTYPE, docType);
		mav.addObject(SEARCH_YEAR, getSearchYear(compId, batchVO.getYear(), locale));
		mav.addObject(SEARCH_TYPE, getSearchOptions(locale));
		mav.addObject(DOC_TYPE, super.getDocumentType(locale));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		mav.addObject("orgDeptId", request.getParameter("orgDeptId"));
		
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
		return mav;
    }
    
    @RequestMapping("/app/bind/bindList.do")
    public ModelAndView bindList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String compId = super.getCompId(request);
	
		String searchYear = request.getParameter(SEARCH_YEAR);
		if (StringUtils.isEmpty(searchYear)) {
		    searchYear = envOptionAPIService.getCurrentPeriodStr(compId);
		}
	
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String docType = request.getParameter(DOC_TYPE);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		Locale locale = super.getLocale(request);
/*
		BindVO bindVO = new BindVO();
		
		bindVO.setCreateYear(searchYear);

		if (searchType!=null&&DOC_TYPE.equals(searchType)) {
			bindVO.setDocType(docType);
		}
		
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);

		// 다국어 추가
		bindVO.setLangType(AppConfig.getCurrentLangType());
		
		bindVO.setBindType(CommonUtil.nullTrim(request.getParameter("bindType")));
		*/
		String isManager = CommonUtil.nullTrim(request.getParameter("isManager"));
		BindVO bindVO = new BindVO();
		
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);
		bindVO.setCreateYear(searchYear);
		if(isManager.equals("Y")){
		}else{
			bindVO.setBinding(N);
			bindVO.setArrange(N);
		}
		bindVO.setLangType(AppConfig.getCurrentLangType());

		
		//BindVO[] bindTreeArray = bindService.getSelectTreeBindResource(bindVO);
		//BindVO[] bindTreeArray = bindService.getSelectTreeBind(bindVO);
		BindVO[] bindTreeArray = bindService.getSelectTargetTreeList(bindVO);
		
		BindVO[] bindTreeShareArray = bindService.getSelectTreeShareBind(bindVO);
		ModelAndView mav = new ModelAndView("BindController.bindList");
		
		mav.addObject("tree", bindTreeArray);
		mav.addObject("ShareTree", bindTreeShareArray);
	
		return mav;
    }
    
    

    @RequestMapping("/app/bind/bindListShare.do")
    public ModelAndView bindListShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String compId = super.getCompId(request);
	
		String searchYear = request.getParameter(SEARCH_YEAR);
		if (StringUtils.isEmpty(searchYear)) {
		    searchYear = "2016";//envOptionAPIService.getCurrentPeriodStr(compId);
		}
	
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String docType = request.getParameter(DOC_TYPE);
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		Locale locale = super.getLocale(request);

		BindVO bindVO = new BindVO();
		
		bindVO.setCreateYear(searchYear);

		if (searchType!=null&&DOC_TYPE.equals(searchType)) {
			bindVO.setDocType(docType);
		}
		
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);

		// 다국어 추가
		bindVO.setLangType(AppConfig.getCurrentLangType());
		
		bindVO.setBindType(CommonUtil.nullTrim(request.getParameter("bindType")));
		
		
		//BindVO[] bindTreeArray = bindService.getSelectTreeBindResource(bindVO);
		BindVO[] bindTreeArray = bindService.getSelectTreeBindShare(bindVO);
		BindVO[] bindTreeShareArray = bindService.getSelectTreeShareBind(bindVO);
		ModelAndView mav = new ModelAndView("BindController.bindListShare");
		
		mav.addObject("tree", bindTreeArray);
		mav.addObject("ShareTree", bindTreeShareArray);
	
		return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  관리자 등록화면을 호출한다.(팝업화면)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/selectOrgPop.do")
    public ModelAndView selectOrgPop(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String userId = req.getParameter("userid");
		String compId = req.getParameter("compid");
		
		String usingType = req.getParameter("usingType");
		usingType = (usingType == null?appCode.getProperty("DPI001","DPI001","DPI"):usingType);
		
		if (compId == null) {
		    userId = (String) session.getAttribute("USER_ID");
		    compId = (String) session.getAttribute("COMP_ID");
		}
		
		ModelAndView mav = new ModelAndView("BindController.selectOrgPop");
		//Departments departments =  orgService.selectOrgTree(userId, 3);
		//List<Department> result = departments.getDepartmentList();
		List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);
		
		mav.addObject("result",result); //대내 수신처
		mav.addObject("usingType", usingType);
		return mav;
    }
    
	@RequestMapping("/app/bind/selectAuthorityPop.do")
	public ModelAndView selectAuthorityPop(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String userId = req.getParameter("userid");
		String compId = req.getParameter("compid");
		
		String usingType = req.getParameter("usingType");
		usingType = (usingType == null?appCode.getProperty("DPI001","DPI001","DPI"):usingType);
		
		if (compId == null) {
		    userId = (String) session.getAttribute("USER_ID");
		    compId = (String) session.getAttribute("COMP_ID");
		}
		ModelAndView mav = new ModelAndView("BindController.selectAuthorityPop");
		//Departments departments =  orgService.selectOrgTree(userId, 3);
		//List<Department> result = departments.getDepartmentList();
		List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);
		
		mav.addObject("result",result); //대내 수신처
		mav.addObject("usingType", usingType);
		return mav;
	}
}
