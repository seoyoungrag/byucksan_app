package com.sds.acube.app.memo.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganizations;
import com.sds.acube.app.memo.service.IMemoService;
import com.sds.acube.app.memo.vo.MemoVO;


/**
 * Class Name : BindController.java <br> Description : 쪽지 관련 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.notice.controller.MemoController.java
 */

@Controller("memoController")
@RequestMapping("/app/memo/*.do")
public class MemoController extends BindBaseController {

    private static final long serialVersionUID = -6436078092721813865L;

    Log logger = LogFactory.getLog(MemoController.class);
    
    @Inject
    @Named("orgService")
    private OrgService orgService;
    
    @Inject
    @Named("memoService")
    private IMemoService memoService;
    
    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;
    
    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /***
    * <pre> 
    *  쪽지 좌측 메뉴
    * </pre>
    * @param request
    * @param response
    * @return
    * @throws Exception
    * @see  
    *
    **/
    @RequestMapping("/app/memo/menu.do")
    public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("MemoController.menu");
		return mav;
    }
    
    /**
     * <pre> 
     *  쪽지 목록 가져오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		
		String searchType = request.getParameter(SEARCH_TYPE);
		param.put("searchType", searchType);
		
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue)) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		    param.put("searchValue", searchValue);
		}
		
		String userId = super.getUserId(request);
		String pageType = request.getParameter("pageType")==null?"receive":request.getParameter("pageType");
		
		if(pageType.equals("receive")){
			param.put("receiverId", userId);
		}else if(pageType.equals("send")){
			param.put("senderId", userId);
		}
	
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		    param.put("deptId", deptId);
		}
		
		String isMain = request.getParameter("isMain");
		if (StringUtils.isEmpty(deptId)) {
		    param.put("isMain", isMain);
		}
		
		String deptName = super.getDeptName(orgService, deptId);
		String pageNo = StringUtils.defaultIfEmpty(CommonUtil.nullTrim(request.getParameter(PAGE_NO)), "1");
		Page memoPage = this.memoService.list(param, Integer.parseInt(pageNo));
		
		ModelAndView mav = new ModelAndView("MemoController.list");
		
		mav.addObject(ROWS, memoPage.getList());
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		
		Map<String, String> searchMap = new HashMap<String, String>();
		if(pageType.equals("receive")){
			searchMap.put("senderName", "보낸사람명");
		}else{
			searchMap.put("receiverName", "받는사람명");
		}
		searchMap.put("title", "제목");
		
		mav.addObject(SEARCH_TYPE, searchMap);
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		mav.addObject(SEARCH_VALUE, searchValue);
		mav.addObject("pageType", pageType);
		
		return mav;
    }
    
    /**
     * <pre> 
     *  쪽지 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/write.do")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("MemoController.write");
				
		mav.addObject("isMainpage", request.getParameter("isMainpage")==null?"":request.getParameter("isMainpage"));
		mav.addObject("senderId", request.getParameter("senderId")==null?"":request.getParameter("senderId"));
		mav.addObject("senderName", request.getParameter("senderName")==null?"":request.getParameter("senderName"));
		return mav;
    }
    
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
    @RequestMapping("/app/memo/selectReceiver.do")
    public ModelAndView getApprovalLine(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	String treeType = req.getParameter("treetype");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");

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

	ModelAndView mav = new ModelAndView("MemoController.popup.selectReceiver");
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

	    /*if ("1".equals(procEmptyType)) {

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
	    }*/

	    mav.addObject("emptyInfos", emptyInfoList);
	}

	return mav;
    }
    
    /**
     * <pre> 
     *  쪽지 등록
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/create.do")
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		
		String[] receiverId = new String(CommonUtil.nullTrim(request.getParameter("receiverId"))).split(",");
		String[] receiverName = new String(CommonUtil.nullTrim(request.getParameter("receiverName"))).split(",");
				
		for(int count = 0; count < receiverId.length; count++){
		
			List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
			String memoId = GuidUtil.getGUID("MEM");
	
			MemoVO vo = new MemoVO();
			vo.setMemoId(memoId);
			vo.setCompId(compId);			
			vo.setContents(CommonUtil.nullTrim(request.getParameter("contents")));
			vo.setReceiverId(receiverId[count]);
			vo.setReceiverName(receiverName[count]);
			vo.setSenderId(super.getUserId(request));
			vo.setSenderName(super.getUserName(request));
			vo.setTitle(CommonUtil.nullTrim(request.getParameter("title")));
			
			// 파일정보
			String currentDate = DateUtil.getCurrentDate();
			// 파일저장(WAS->STOR)
			DrmParamVO drmParamVO = new DrmParamVO();
			drmParamVO.setCompId(compId);
			drmParamVO.setUserId(super.getUserId(request));
					
			List<FileVO> uploadFileVOs = new ArrayList<FileVO>();
			
			int fileListCount = fileVOsList.size();
		    if (fileListCount > 0) {
				List<FileVO> fileVOs = fileVOsList.get(0);
				int filesize = fileVOs.size();
				for (int pos = 0; pos < filesize; pos++) {
				    FileVO fileVO = (FileVO) fileVOs.get(pos);
				    fileVO.setDocId(memoId);
				    fileVO.setCompId(compId);
				    fileVO.setProcessorId(super.getUserId(request));
				    fileVO.setTempYn("N");
				    fileVO.setRegisterId(super.getUserId(request));
				    fileVO.setRegisterName(super.getUserName(request));
				    fileVO.setRegistDate(currentDate);
				}
				
				int fileCount = fileVOs.size();
				for (int loop = 0; loop < fileCount; loop++) {
				    FileVO fileVO = fileVOs.get(loop);
				    if (!"".equals(fileVO.getFileId())) {
					uploadFileVOs.add(fileVO);
				    }
				}
				
				// 저장서버에 없는 파일만 업로드
				attachService.downloadAttach("", uploadFileVOs, drmParamVO);
				fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
				vo.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));
				
				// 파일 이력
				String lineHisId = GuidUtil.getGUID();
				List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
				
				if (fileVOs.size() > 0) {
				    if (appComService.insertFile(fileVOs) > 0) {
						appComService.insertFileHis(fileHisVOs);
				    }
				}
		    } 
		    
			int suc = memoService.insert(vo);
			if(suc >0) {
				result.put(SUCCESS, true);
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
     *  쪽지 수정 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/view.do")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String memoId = CommonUtil.nullTrim(request.getParameter("memoId"));
    	String isMainpage = CommonUtil.nullTrim(request.getParameter("isMainpage"));
    	
    	String compId = super.getCompId(request); // 사용자 소속 회사 아이디
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("memoId", memoId);
		MemoVO memoVO = this.memoService.get(param);
		
		String userId = super.getUserId(request);
		
		if(memoVO != null && memoVO.getReceiverId().equals(userId) && memoVO.getReadDate().equals("")){
			this.memoService.updateReadDate(memoVO);
		}
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", memoVO.getMemoId());
	    map.put("compId", compId);
	    List<FileVO> fileVOs =  appComService.listFile(map);
		
		ModelAndView mav = new ModelAndView("MemoController.view");
		mav.addObject("memoVO", memoVO);
		mav.addObject("userId", userId);
		mav.addObject("fileVOs", fileVOs);
		mav.addObject("isMainpage",isMainpage);
		return mav;
    }
    
    /**
     * <pre> 
     *  쪽지 삭제
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();

    	try {
    		String memoIds = CommonUtil.nullTrim(request.getParameter("memoIds"));
    		
    		int suc = this.memoService.delete(memoIds);

    		if(suc >0) {
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
     *  편철 목록 프린트
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/print.do")
    public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		}
	
		String pageNo = StringUtils.defaultIfEmpty(request.getParameter(PAGE_NO), "1");
		String deptId = request.getParameter(DEPT_ID);
		if (StringUtils.isEmpty(deptId)) {
		    deptId = StringUtils.defaultIfEmpty(super.setProxyDeptId(request), super.getDeptId(request));
		}
		String deptName = super.getDeptName(orgService, deptId);
		
		Map<String, String> param = new HashMap<String, String>();
		
		
		String searchType = request.getParameter(SEARCH_TYPE);
		param.put("searchType", searchType);
		
		if (StringUtils.isNotEmpty(searchValue)) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		    param.put("searchValue", searchValue);
		}
		
		String userId = super.getUserId(request);
		String pageType = request.getParameter("pageType")==null?"receive":request.getParameter("pageType");
		
		if(pageType.equals("receive")){
			param.put("receiverId", userId);
		}else if(pageType.equals("send")){
			param.put("senderId", userId);
		}
	
		Page memoPage = this.memoService.list(param, Integer.parseInt(pageNo));
		
		ModelAndView mav = new ModelAndView("MemoController.print");
		mav.addObject(ROWS, memoPage.getList());
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		Map<String, String> searchMap = new HashMap<String, String>();
		if(pageType.equals("receive")){
			searchMap.put("senderName", "보낸사람명");
		}else{
			searchMap.put("receiverName", "받는사람명");
		}
		searchMap.put("title", "제목");
		mav.addObject(SEARCH_TYPE, searchMap);
		mav.addObject(DEPT_ID, deptId);
		mav.addObject("pageType", pageType);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		return mav;
    }
}
