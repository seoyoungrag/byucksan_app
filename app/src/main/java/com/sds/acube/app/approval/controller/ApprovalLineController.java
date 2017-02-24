package com.sds.acube.app.approval.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.LineGroupVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganizations;

/**
 * Class Name  : ApprovalLineController.java <br> Description : 결재경로관련 Controller  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 31. <br> 수 정  자 : 장진홍  <br> 수정내용 :  <br>
 * @author   Administrator 
 * @since  2011. 3. 31.
 * @version  1.0 
 * @see  com.sds.acube.app.approval.controller.ApprovalLineController.java
 */

@SuppressWarnings("serial")
@Controller
@RequestMapping("/app/approval/*.do")
public class ApprovalLineController extends BaseController{

    /**
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Autowired
    private IApprovalService approvalService;
    
    /**
	 */
    @Autowired
    private IEnvUserService envUserService;
    
    @Autowired
    private IEnfLineService enfLineService;

    /**
     * 
     * <pre> 
     *  결재라인 초기 요청 Controller
     * </pre>
     * @param req treeType=3 기본 세팅 : 사용자 회사 + 사용자 부서 (다른 계열사 제외)
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/ApprovalLine.do")
    public ModelAndView getApprovalLine(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	String treeType = req.getParameter("treetype");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String formName = req.getParameter("formName");

	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}

	treeType = (treeType == null ? "3" : treeType);

	//Departments departments =  orgService.selectOrgTree(userId, Integer.valueOf(treeType).intValue());
	//List<Department> results = departments.getDepartmentList();
	List<DepartmentVO> results = orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));

	//옵션값을 옵션 코드(ID)값을 키로 하여 맵을 생성한다. (다국어 추가) 
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMapResource(compId, "OPTG000", langType);
	// HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMap(compId, "OPTG000");

	mapOptions.put("OPT325", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT325","OPT325", "OPT"))); //이중결재 사용여부
	mapOptions.put("OPT320", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT320","OPT320", "OPT"))); //CEO 결재문서 감사필수 여부
	mapOptions.put("OPT336", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT336","OPT336", "OPT"))); //결재 후 협조 사용여부
	mapOptions.put("OPT337", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT337","OPT337", "OPT"))); //결재 후 부서협조 사용여부
	mapOptions.put("OPT360", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT360","OPT360", "OPT"))); //결재 후 병렬협조 사용

	ModelAndView mav = new ModelAndView("ApprovalLineController.getApprovalLine");
	mav.addObject("formName", formName);
	
	mav.addObject("gList", envOptionAPIService.listAppLineGroupP(compId, registerId, registerDeptId, "DPI001")); //결재경로그룹
	mav.addObject("results", results); 
	mav.addObject("options", mapOptions);
	
	// 양식파일 종류 (hwp, doc, html 문자열) 
	String formBodyType = (String)req.getParameter("formBodyType"); 
	mav.addObject("formBodyType", formBodyType);
	
	String adminFlag = CommonUtil.nullTrim(req.getParameter("adminFlag")); // 관리자여부
	if ("Y".equals(adminFlag)) {
	    String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

	    String opt313 = appCode.getProperty("OPT313", "OPT313", "OPT"); // 부재처리
	    String procEmptyType = envOptionAPIService.selectOptionValue(compId, opt313);

	    ArrayList<EmptyInfoVO> emptyInfoList = new ArrayList<EmptyInfoVO>();

	    if ("1".equals(procEmptyType)) {

		String docId = CommonUtil.nullTrim(req.getParameter("docId")); // 문서ID
		List<AppLineVO> appLineVOs = approvalService.listAppLine(compId, docId);
		int linesize = appLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (apt003.equals(appLineVO.getProcType())) {
			String approverId = appLineVO.getApproverId();
			if (!"".equals(approverId)) {
			    EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(approverId);
			    if (emptyInfoVO != null && emptyInfoVO.getIsEmpty() && emptyInfoVO.getIsSubstitute()) {
				emptyInfoList.add(emptyInfoVO);
			    }
			}
		    }
		}
	    }

	    mav.addObject("emptyInfos", emptyInfoList);
	}

	return mav;
    }

    /**
     * 
     * <pre> 
     * 수신자 팝업 초기 요청 Controller
     * </pre>
     * @param req treeType=1 기본세팅은 3 호출시 1 호출 필수 
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/ApprovalRecip.do")
    public ModelAndView getApprovalRecip(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");

	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}

	String registerDeptId = (String) session.getAttribute("DEPT_ID");
	String registerId = (String) session.getAttribute("USER_ID");

	ModelAndView mav = new ModelAndView("ApprovalLineController.getApprovalRecip");
	List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);
	List<DepartmentVO> result2 = orgService.selectOrgTreeList(userId, 1);
	
	HashMap<String, String> map410 = envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT410");
	Iterator keySetIter = map410.keySet().iterator();
	String tabI1 = "", tabI2 = "", tabI3 = "", tabI4 = "";
	
	while(keySetIter.hasNext())
	{
		String key = (String)keySetIter.next();
		if ( key.equals("I1") ) tabI1 = map410.get(key);
		if ( key.equals("I2") ) tabI2 = map410.get(key);
		if ( key.equals("I3") ) tabI3 = map410.get(key);
		if ( key.equals("I4") ) tabI4 = map410.get(key);
	}
	
	DepartmentVO companyVO = result.get(0);
	String orgId = companyVO.getOrgID();
	
	List<OrganizationVO> resultSymbol = null;
	LDAPOrganizations LDAPOrgs = null;
	
	if ( tabI3.equals("Y") ) {
		int prefixLength = Integer.parseInt(AppConfig.getProperty("prefixLength", "1", "recvSymbol"));
		resultSymbol = orgService.selectRootIndexByAddrSymPrefix(orgId, prefixLength);
	}
	
	if ( tabI4.equals("Y") ) {
		LDAPOrgs = orgService.getSubLDAPOrg("ROOT");
	}
	
	String opt333 = envOptionAPIService.selectOptionValue(compId, "OPT333"); //수신자 부서 원사용여부 1:부서 2 : 부서원 사용
	mav.addObject("result",result); //대내 수신처
	mav.addObject("result2",result2); //대외수신처
	mav.addObject("resultSymbol",resultSymbol); //수신자기호
	mav.addObject("LDAPOrgs",LDAPOrgs); //행정기관
	mav.addObject("opt333", opt333);
	mav.addObject("gList",envOptionAPIService.listRecvGroupP(compId, registerDeptId, registerId)); // 수신자그룹
	return mav;
    }

    /**
     * 
     * <pre> 
     * 공람 팝업 초기 요청 Controller
     * </pre>
     * @param req treeType=1 기본세팅은 3 호출시 1 호출 필수 
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/ApprovalPubReader.do")
    public ModelAndView getApprovalPubReader(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String registerDeptId = (String) session.getAttribute("DEPT_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");

	String usingType = req.getParameter("usingType");
	usingType = (usingType == null?appCode.getProperty("DPI001","DPI001","DPI"):usingType);

	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}

	ModelAndView mav = new ModelAndView("ApprovalLineController.getApprovalPubReader");
	//Departments departments =  orgService.selectOrgTree(userId, 3);
	//List<Department> result = departments.getDepartmentList();
	List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);

	mav.addObject("result",result); //대내 수신처
	mav.addObject("usingType", usingType);
	mav.addObject("gList",envOptionAPIService.listPubViewGroupP(compId, registerDeptId, registerId)); // 공람자그룹
	return mav;
    }

    /**
     * 
     * <pre> 
     * 선람 팝업 초기 요청 Controller
     * </pre>
     * @param req treeType=1 기본세팅은 3 호출시 1 호출 필수 
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/ApprovalPreReader.do")
    public ModelAndView getApprovalPreReader(HttpServletRequest req) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalLineController.getApprovalPreReader");

	String gut003 = appCode.getProperty("GUT003", "GUT003", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "GUT004", "GUT"); // 개인
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수

	HttpSession session = req.getSession();
	String userId = CommonUtil.nullTrim(req.getParameter("userid"));
	String compId = CommonUtil.nullTrim(req.getParameter("compid"));
	String treeType = CommonUtil.nullTrim(req.getParameter("treetype"));

	String registerId = (String) session.getAttribute("USER_ID");
	String registerDeptId = (String) session.getAttribute("DEPT_ID");

	if ("".equals(compId)) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}

	String ownDeptId = registerDeptId;
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (!"".equals(proxyDeptId)) {
	    ownDeptId = proxyDeptId;
	}

	treeType = ("".equals(treeType) ? "3" : treeType);

	List<DepartmentVO> results = orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));	
	List<UserVO> userVOs = orgService.selectUserApprovalLine(ownDeptId, userId);

	//옵션값을 옵션 코드(ID)값을 키로 하여 맵을 생성한다. 
	// 다국어 추가
	HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMapResource(compId, "OPTG000", AppConfig.getCurrentLangType());
	// HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMap(compId, "OPTG000");
	
	mapOptions.put("OPT362", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT362","OPT362", "OPT"))); //기본결재경로 사용여부

	List<LineGroupVO> appLineGroups = (List<LineGroupVO>) envOptionAPIService.listAppLineGroup(compId, registerId, ownDeptId, gut004, dpi002);
	appLineGroups.addAll((List<LineGroupVO>) envOptionAPIService.listAppLineGroup(compId, registerId, ownDeptId, gut003, dpi002));
	if (!registerDeptId.equals(ownDeptId)) {
	    appLineGroups.addAll((List<LineGroupVO>) envOptionAPIService.listAppLineGroup(compId, registerId, registerDeptId, gut003, dpi002));
	}
	mav.addObject("gList", appLineGroups); //결재경로그룹
	mav.addObject("results", results);
	mav.addObject("options", mapOptions);

	if(userVOs.size() > 0){
	    mav.addObject("DEPTHEAD", userVOs.get(0)); //부서장
	}
	

	String adminFlag = CommonUtil.nullTrim(req.getParameter("adminFlag")); // 관리자여부
	if ("Y".equals(adminFlag)) {
	    String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

	    String opt313 = appCode.getProperty("OPT313", "OPT313", "OPT"); // 부재처리
	    String procEmptyType = envOptionAPIService.selectOptionValue(compId, opt313);

	    ArrayList<EmptyInfoVO> emptyInfoList = new ArrayList<EmptyInfoVO>();

	    if ("1".equals(procEmptyType)) {

		String docId = CommonUtil.nullTrim(req.getParameter("docId")); // 문서ID
		EnfLineVO getEnfLineVO = new EnfLineVO();
		getEnfLineVO.setCompId(compId);
		getEnfLineVO.setDocId(docId);
		
		List<EnfLineVO> enfLineVOs = enfLineService.getList(getEnfLineVO);
		int linesize = enfLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    EnfLineVO enfLineVO = enfLineVOs.get(loop);
		    if (apt003.equals(enfLineVO.getProcType())) {
			String processorId = enfLineVO.getProcessorId();
			if (!"".equals(processorId)) {
			    EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(processorId);
			    if (emptyInfoVO != null && emptyInfoVO.getIsEmpty() && emptyInfoVO.getIsSubstitute()) {
				emptyInfoList.add(emptyInfoVO);
			    }
			}
		    }
		}
	    }

	    mav.addObject("emptyInfos", emptyInfoList);
	}	

	return mav;
    }
}
