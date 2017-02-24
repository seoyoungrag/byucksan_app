package com.sds.acube.app.bind.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindUnitService;
import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : BindUnitController.java <br> Description : 단위업무 관련 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 7. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 4. 7.
 * @version  1.0
 * @see  com.sds.acube.app.bind.controller.BindUnitController.java
 */

@Controller("bindUnitController")
@RequestMapping("/app/bind/unit/*.do")
public class BindUnitController extends BindBaseController {
    private static final long serialVersionUID = 4223423984011850303L;

    Log logger = LogFactory.getLog(BindUnitController.class);

    /**
	 */
    @Inject
    @Named("bindUnitService")
    private BindUnitService bindUnitService;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;


    /**
     * <pre> 
     *  분리체계관리 목록 
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/list.do")
    public ModelAndView simpleList(HttpServletRequest request, HttpServletResponse response,
    	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    	    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    	    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    ) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
    	SearchVO searchVO = new SearchVO();
    	searchVO.setCompId(compId);
    	searchVO.setDeptId(deptId);
    	searchVO.setAskType2(unitType);
    	
    	Page page = null;
    	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
    	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
    	
    	// 다국어 추가
    	searchVO.setLangType(AppConfig.getCurrentLangType());
    	//page = bindUnitService.listBindUnitResource(searchVO, cPage, Integer.parseInt(page_size_max));
    	page = bindUnitService.listBindUnit(searchVO, cPage, Integer.parseInt(page_size_max));

    	ModelAndView mav = new ModelAndView("BindUnitController.list");
    	
    	mav.addObject("ListVo", page.getList());
    	mav.addObject("totalCount", page.getTotalCount());
    	mav.addObject(COMP_ID, compId);
    	mav.addObject(DEPT_ID, deptId);
    	mav.addObject(DEPT_NAME, deptName);
    	mav.addObject(UNIT_TYPE, unitType);
    	mav.addObject("adminSelectDept", adminSelectDept);
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
    	return mav;
    }
    
    

    /**
     * <pre> 
     *  단위업무 수정 페이지 보기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/edit.do")
    public ModelAndView viewEditPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String unitId = request.getParameter(UNIT_ID);
    	String unitType = request.getParameter(UNIT_TYPE);
    	String deptId = request.getParameter(DEPT_ID);
    	String allDocOrForm = request.getParameter("allDocOrForm");

    	BindUnitVO vo = new BindUnitVO();
    	vo.setCompId(super.getCompId(request));
    	vo.setUnitId(unitId);
    	vo.setUnitType(unitType);
    	vo.setDeptId(deptId);

    	// 다국어 추가
    	vo.setLangType(AppConfig.getCurrentLangType());
    	//BindUnitVO row = bindUnitService.getResource(unitId, vo);
    	BindUnitVO row = bindUnitService.get(unitId, vo);
    	
    	ModelAndView mav = new ModelAndView("BindUnitController.edit");
    	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
    	mav.addObject(ROW, row);
    	mav.addObject("allDocOrForm", allDocOrForm);
    	return mav;
    }
    
    /**
     * <pre> 
     *  단위업무 상세 페이지 보기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/info.do")
    public ModelAndView viewInfoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String unitId = request.getParameter(UNIT_ID);
    	String unitType = request.getParameter(UNIT_TYPE);
    	String deptId = request.getParameter(DEPT_ID);
    	String allDocOrForm = request.getParameter("allDocOrForm");
    	
    	BindUnitVO vo = new BindUnitVO();
    	vo.setCompId(super.getCompId(request));
    	vo.setUnitId(unitId);
    	vo.setUnitType(unitType);
    	vo.setDeptId(deptId);
    	
    	// 다국어 추가
    	vo.setLangType(AppConfig.getCurrentLangType());
    	//BindUnitVO row = bindUnitService.getResource(unitId, vo);
    	BindUnitVO row = bindUnitService.get(unitId, vo);
    	
    	ModelAndView mav = new ModelAndView("BindUnitController.info");
    	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
    	mav.addObject(ROW, row);
    	mav.addObject("allDocOrForm", allDocOrForm);
    	return mav;
    }


    /**
     * <pre> 
     *  단위업무 링크
     * </pre>
     * @param bindUnitId
     * @param locale
     * @return
     * @throws Exception
     * @see  
     *
     */
    private String getBindUnitPath(String bindUnitId, Locale locale) throws Exception {
	String basePath = "<a href='/bind/unit/list.do?parentUnitId=root'>" + m.getMessage("bind.obj.unit", null, locale) + "</a>";
	if (ROOT_ID.equals(bindUnitId)) {
	    return basePath;
	} else {
	    return basePath + getPath(bindUnitId, "");
	}
    }


    private String getPath(String unitId, String path) throws Exception {
		BindUnitVO vo = new BindUnitVO();
		vo.setUnitId(unitId);
	
		// 다국어 추가
		vo.setLangType(AppConfig.getCurrentLangType());
		//BindUnitVO bindUnitVO = bindUnitService.getResource(unitId, vo);
		BindUnitVO bindUnitVO = bindUnitService.get(unitId, vo);
	
		path = " > <a href='/bind/unit/list.do?parentUnitId=" + bindUnitVO.getUnitId() + "'>" + bindUnitVO.getUnitName() + "</a>" + path;
		if (ROOT_ID.equals(bindUnitVO.getParentUnitId())) {
		    return path;
		} else {
		    return getPath(bindUnitVO.getParentUnitId(), path);
		}
    }

    
    /**
     * <pre> 
     *  단위업무 생성 
     * </pre>
     * @param req
     * @param resp
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/create.do")
    public void createSimpleUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	JSONObject result = new JSONObject();
    	
    	try {
    		String unitId = createSimpleUnit(req);
    		result.put(SUCCESS, true);
    		result.put("unitId", unitId);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		result.put(SUCCESS, false);
    		result.put(MESSAGE, e.getMessage());
    		result.put("unitId", "");
    	}
    	
    	resp.getWriter().write(result.toString());
    }

    
    /**
     * <pre> 
     *  단위업무 생성
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    private String createSimpleUnit(HttpServletRequest req) throws Exception {
    	String currentTime = super.getCurrentTime();
    	String unitId = GuidUtil.getGUID("UTT");
    	String unitName = URLDecoder.decode(req.getParameter(UNIT_NAME), UTF8);
    	String unitType = req.getParameter(UNIT_TYPE);
    	String retentionPeriod = req.getParameter(RETENTION_PERIOD);
    	String applied = StringUtils.replace(req.getParameter(APPLIED), "/", "");
    	String serialNumber = req.getParameter(SERIAL_NUMBER);
    	String description = req.getParameter(DESCRIPTION);
    	String parentUnitId =  req.getParameter(PARENT_UNIT_ID);
    	long unitDepth = UtilRequest.getInt(req, UNIT_DEPTH ,0);
    	long unitOrder = UtilRequest.getInt(req, UNIT_ORDER ,0);
    	
    	BindUnitVO vo = new BindUnitVO();
    	vo.setCompId(super.getCompId(req));
    	String deptId = super.getDeptId(req);
    	if (unitType.equals("UTT001")) deptId = super.getCompId(req);
    	else if (unitType.equals("UTT002")) deptId = req.getParameter(DEPT_ID);
    	vo.setDeptId(deptId);
    	vo.setUnitId(unitId);
    	vo.setUnitName(unitName);
    	vo.setUnitType(unitType);
    	vo.setRetentionPeriod(retentionPeriod);
    	vo.setApplied(applied + "000000");
    	vo.setCreated(currentTime);
    	vo.setModified(currentTime);
    	vo.setSerialNumber(serialNumber);
    	vo.setDescription(description);
    	vo.setParentUnitId(parentUnitId);
    	vo.setUnitDepth(unitDepth);
    	vo.setUnitOrder(unitOrder);
    	
    	bindUnitService.simpleInsert(vo);
    	
    	return unitId;
    }


    /**
     * <pre> 
     *  단위업무 수정 
     * </pre>
     * @param req
     * @param resp
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/update.do")
    public void simpleUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	JSONObject result = new JSONObject();
    	
    	try {
    		String compId = req.getParameter(COMP_ID);
    		String deptId = req.getParameter(DEPT_ID);
    		String unitId = req.getParameter(UNIT_ID);
    		String unitName = req.getParameter(UNIT_NAME);
    		String retentionPeriod = req.getParameter(RETENTION_PERIOD);
    		String applied = req.getParameter(APPLIED);
    		String serialNumber = req.getParameter(SERIAL_NUMBER);
    		String description = req.getParameter(DESCRIPTION);
    		applied = StringUtils.replace(req.getParameter(APPLIED), "/", "");
    		long unitOrder = UtilRequest.getInt(req, UNIT_ORDER ,0);
    		
    		BindUnitVO vo = new BindUnitVO();
    		vo.setCompId(compId);
    		vo.setDeptId(deptId);
    		vo.setUnitId(unitId);
    		vo.setUnitName(unitName);
    		vo.setRetentionPeriod(retentionPeriod);
    		vo.setApplied(applied + "000000");
    		vo.setModified(super.getCurrentTime());
    		vo.setSerialNumber(serialNumber);
    		vo.setDescription(description);
    		vo.setUnitOrder(unitOrder);
    		
    		bindUnitService.simpleUpdate(vo);
    		
    		result.put(SUCCESS, true);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		result.put(SUCCESS, false);
    		result.put(MESSAGE, e.getMessage());
    	}
    	
    	resp.getWriter().write(result.toString());
    }

    /**
     * <pre> 
     *  분류체계 명 변경
     * </pre>
     * @param req
     * @param resp
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/rename.do")
    public void rename(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	JSONObject result = new JSONObject();

	try {
	    String unitId = req.getParameter(UNIT_ID);
	    String unitName = URLDecoder.decode(req.getParameter(UNIT_NAME), UTF8);

	    BindUnitVO vo = new BindUnitVO();
	    vo.setCompId(super.getCompId(req));
	    vo.setUnitId(unitId);
	    vo.setUnitName(unitName);
	    vo.setModified(super.getCurrentTime());

	    bindUnitService.rename(vo);

	    result.put(SUCCESS, true);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    result.put(SUCCESS, false);
	    result.put(MESSAGE, e.getMessage());
	}

	resp.getWriter().write(result.toString());
    }


    /**
     * <pre> 
     *  단위업무 삭제 
     * </pre>
     * @param req
     * @param resp
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/remove.do")
    public void simpleRemove(HttpServletRequest req, HttpServletResponse resp) throws Exception 
    {
    	// 편철사용중 여부는 클라이언트 사이드에서 제어.
    	JSONObject result = new JSONObject();
    	try {
    		String compId = req.getParameter(COMP_ID);
    		String deptId = req.getParameter(DEPT_ID);
    		String unitId = req.getParameter(UNIT_ID);
    		
    		BindUnitVO vo = new BindUnitVO();
    		vo.setCompId(compId);
    		vo.setDeptId(deptId);
    		vo.setUnitId(unitId);
    		
			bindUnitService.simpleDelete(vo);
			result.put(SUCCESS, true);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		result.put(SUCCESS, false);
    		result.put(MESSAGE, e.getMessage());
    	}
    	
    	resp.setContentType("application/x-json; charset=utf-8");
    	resp.getWriter().write(result.toString());
    }

    
    /**
     * <pre> 
     *  단위 업무 존재여부 
     * </pre>
     * @param req
     * @param resp
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/exist.do")
    public void simpleExist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	JSONObject result = new JSONObject();
    	
    	String unitId = req.getParameter(UNIT_ID);
    	
    	BindUnitVO vo = new BindUnitVO();
    	vo.setCompId(super.getCompId(req));
    	vo.setUnitId(unitId);
    	
    	// 다국어 추가
    	vo.setLangType(AppConfig.getCurrentLangType());
    	//BindUnitVO bindUnitVO = bindUnitService.simpleGetResource(vo);
    	BindUnitVO bindUnitVO = bindUnitService.simpleGet(vo);
    	
    	result.put(SUCCESS, bindUnitVO == null);
    	
    	resp.getWriter().write(result.toString());
    }
    
    /**
     * <pre> 
     *  단위업무 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/add.do")
    public ModelAndView addPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String mode = request.getParameter("mode");
    	String deptId = request.getParameter("deptId");
    	String unitId = request.getParameter("unitId");
    	long unitDepth = UtilRequest.getInt(request, "unitDepth" ,0);
    	
    	ModelAndView mav = new ModelAndView("BindUnitController.add");
    	
		String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
    	mav.addObject("mode", mode);
    	mav.addObject("deptId", deptId);
    	mav.addObject("unitId", unitId);
    	mav.addObject("unitDepth", unitDepth);
		mav.addObject(DEFAULT, defaultRetenionPeriod);
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
    	
		return mav;
    }

    /**
     * <pre> 
     *  단위업무 조회 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/info.do")
    public ModelAndView infoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String unitId = request.getParameter(UNIT_ID);
	String unitType = request.getParameter(UNIT_TYPE);

	BindUnitVO vo = new BindUnitVO();
	vo.setCompId(super.getCompId(request));
	vo.setUnitId(unitId);
	vo.setUnitType(unitType);

	// 다국어 추가
	vo.setLangType(AppConfig.getCurrentLangType());
	//BindUnitVO row = bindUnitService.getResource(unitId, vo);
	BindUnitVO row = bindUnitService.get(unitId, vo);

	ModelAndView mav = new ModelAndView("BindUnitController.info");
	mav.addObject(ROW, row);
	return mav;
    }
    
    /**
     * <pre> 
     *  분리체계관리 - tree 목록 
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/unitList.do")
    public ModelAndView unitList(HttpServletRequest request, HttpServletResponse response,
    	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    	    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    	    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    ) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
		BindUnitVO bindUnitVO = new BindUnitVO();
		bindUnitVO.setCompId(compId);
		bindUnitVO.setDeptId(deptId);
		bindUnitVO.setUnitType(unitType);

    	// 다국어 추가
		bindUnitVO.setLangType(AppConfig.getCurrentLangType());
		
		BindUnitVO[] unitTreeArray = bindUnitService.listTreeBindUnit(bindUnitVO);

    	ModelAndView mav = new ModelAndView("BindUnitController.unitList");
    	
    	mav.addObject("tree", unitTreeArray);   	
    	return mav;
    }
    
    /**
     * <pre> 
     *  분리체계관리 목록 
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/bind/unit/simple/unitManager.do")
    public ModelAndView unitManager(HttpServletRequest request, HttpServletResponse response,
    	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    	    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    	    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    ) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
    	SearchVO searchVO = new SearchVO();
    	searchVO.setCompId(compId);
    	searchVO.setDeptId(deptId);
    	searchVO.setAskType2(unitType);

    	ModelAndView mav = new ModelAndView("BindUnitController.unitManager");

    	mav.addObject(COMP_ID, compId);
    	mav.addObject(DEPT_ID, deptId);
    	mav.addObject(DEPT_NAME, deptName);
    	mav.addObject(UNIT_TYPE, unitType);
    	mav.addObject("adminSelectDept", adminSelectDept);
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
    	return mav;
    }
    
	// 트리 자식노드 오픈 시 AJAX로 로딩
	/*@RequestMapping(params = "method=getTreeChildAjax") 
	public String getTreeChildAjax(Model model, HttpServletRequest request) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
		BindUnitVO bindUnitVO = new BindUnitVO();
		bindUnitVO.setCompId(compId);
		bindUnitVO.setDeptId(deptId);
		bindUnitVO.setUnitType(unitType);

    	// 다국어 추가
		bindUnitVO.setLangType(AppConfig.getCurrentLangType());
		bindUnitVO.setIsSelectType(BindUnitVO.TREE_CHILD);
		bindUnitVO.setUnitId("ROOT");
		
		BindUnitVO[] unitTreeArray = bindUnitService.listTreeBindUnitResource(bindUnitVO);
        
		model.addAttribute("trees", unitTreeArray);
		return "jsonView";
	}*/
    
    @RequestMapping("/app/bind/unit/simple/unitListTree.do")
    public ModelAndView unitListTree(HttpServletRequest request, HttpServletResponse response,
    	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    	    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    	    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    ) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
		BindUnitVO bindUnitVO = new BindUnitVO();
		bindUnitVO.setCompId(compId);
		bindUnitVO.setDeptId(deptId);
		bindUnitVO.setUnitType(unitType);

    	// 다국어 추가
		bindUnitVO.setLangType(AppConfig.getCurrentLangType());
		
		//BindUnitVO[] unitTreeArray = bindUnitService.listTreeBindUnitResource(bindUnitVO);
		BindUnitVO[] unitTreeArray = bindUnitService.listTreeBindUnit(bindUnitVO);
		
    	ModelAndView mav = new ModelAndView("BindUnitController.unitListTree");
    	
    	mav.addObject("tree", unitTreeArray);   	
    	return mav;
    }
    
    @RequestMapping("/app/bind/unit/simple/shareUnitListTree.do")
    public ModelAndView shareUnitListTree(HttpServletRequest request, HttpServletResponse response,
    	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    	    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    	    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    ) throws Exception {
    	String compId = super.getCompId(request);
    	String deptId = request.getParameter(DEPT_ID);
    	String adminSelectDept = request.getParameter("adminSelectDept");
    	
    	if (StringUtils.isEmpty(deptId)) {
    	    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
    	}
    	
    	String deptName = super.getDeptName(orgService, deptId);
    	
    	String roleCodes = getRoleCode(request);
		String systemAdminCode = AppConfig.getProperty("role_admin", "", "role"); // 통합그룹웨어 총괄 관리자
		String appSystemAdminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 전자결재 시스템 관리자
		String unitType = "UTT001"; // 회사공통
		
		if ( roleCodes.indexOf(systemAdminCode) == -1 && roleCodes.indexOf(appSystemAdminCode) == -1 )
		{	//관리자가 아닌 경우.
			unitType = "UTT002"; // 부서고유
		}
		else
		{
			if (StringUtils.isEmpty(adminSelectDept) || adminSelectDept.equals("N") )
			{ // 관리자가 부서선택 하지 않은 경우 부서아이디에 회사아이디가 사용된다.
				deptId = compId; 
				deptName = super.getDeptName(orgService, deptId);
				adminSelectDept = "N";
			}
			else
			{ 
				// 관리자가 부서선택한 경우 선택된 부서로 작업을 하게한다.
				unitType = "UTT002"; // 부서고유
			}
		}
    	
		BindUnitVO bindUnitVO = new BindUnitVO();
		bindUnitVO.setCompId(compId);
		bindUnitVO.setDeptId(deptId);
		bindUnitVO.setUnitType(unitType);

    	// 다국어 추가
		bindUnitVO.setLangType(AppConfig.getCurrentLangType());
		
		//BindUnitVO[] unitTreeArray = bindUnitService.listTreeBindUnitResource(bindUnitVO);
		BindUnitVO[] unitTreeArray = bindUnitService.listTreeShareBindUnit(bindUnitVO);
		
    	ModelAndView mav = new ModelAndView("BindUnitController.unitListTree");
    	
    	mav.addObject("tree", unitTreeArray);   	
    	return mav;
    }
}