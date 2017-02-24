package com.sds.acube.app.notice.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.notice.service.INoticeService;
import com.sds.acube.app.notice.vo.NoticeSendOrgVO;
import com.sds.acube.app.notice.vo.NoticeVO;


/**
 * Class Name : BindController.java <br> Description : 공지사항 관련 컨트롤러 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.notice.controller.NoticeController.java
 */

@Controller("noticeController")
@RequestMapping("/app/notice/*.do")
public class NoticeController extends BindBaseController {

    private static final long serialVersionUID = -6436078092721813865L;

    Log logger = LogFactory.getLog(NoticeController.class);
    
    @Inject
    @Named("orgService")
    private OrgService orgService;
    
    @Inject
    @Named("noticeService")
    private INoticeService noticeService;
    
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
    *  공지사항 좌측 메뉴
    * </pre>
    * @param request
    * @param response
    * @return
    * @throws Exception
    * @see  
    *
    **/
    @RequestMapping("/app/notice/menu.do")
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
		param.put(SERIAL_NUMBER, N);
		
		param.put(CREATE_YEAR, transYear);
		param.put(SEND_TYPE, SEND_TYPES.DST003.name());
		param.put(SERIAL_NUMBER, null);
		
		Map<String, String> yearOption = getSearchYear(compId, locale);
		
		ModelAndView mav = new ModelAndView("NoticeController.menu");
		mav.addObject(SEARCH_YEAR, yearOption);
		mav.addObject("etcYear", yearOption);
		mav.addObject(TRANS_YEAR, yearOption);
		mav.addObject(YEAR, searchYear);
		mav.addObject("tyear", transYear);
		mav.addObject("eyear", etcYear);
		mav.addObject(SEARCH_TYPE, searchType);
		return mav;
    }
    
    /**
     * <pre> 
     *  공지사항 목록 가져오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/list.do")
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
	
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setSearchValue(searchValue);
		// 조직코드 컬럼을 조합해서 like쿼리를 돌리는데 추후 문제가 될 수 있다. 상위 기관 코드와 하위 기관 코드와 유사한 형태 즉, 상위 기관 코드 + 한자리정도 숫자??? 이런 조합이면
		// 하위기관 공지사항이라고 하더라도 상위 기관에서 볼 수 있게되는 문제??? 조직도 연계관련 진행이 너무 더뎌 지금 확인이 안된다...
		noticeVO.setReceiveOrgs(super.getDeptId(request));
		Page noticePage = this.noticeService.getList(noticeVO, Integer.parseInt(pageNo));
		
		ModelAndView mav = new ModelAndView("NoticeController.list");
		
		mav.addObject(ROWS, noticePage.getList());
		mav.addObject(YEAR, searchYear);
		mav.addObject(TYPE, searchType);
		mav.addObject(VALUE, searchValue);
		mav.addObject(DTYPE, docType);
		mav.addObject(DOC_TYPE, super.getDocumentType(locale));
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		mav.addObject(SEARCH_VALUE, searchValue);
		
		String allDocOrForm = envOptionAPIService.selectOptionValue(compId, "OPT358");
		
		mav.addObject("allDocOrForm", allDocOrForm); // 채번사용여부(1: 모든문서, 2: 양식에서 선택)    	
		return mav;
    }
    
    /**
     * <pre> 
     *  공지사항 등록 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/add.do")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("NoticeController.add");
		return mav;
    }
    
    /**
     * <pre> 
     *  공지사항 등록
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/create.do")
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	try {
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		
		String noticeId = GuidUtil.getGUID("NTC");
		List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
		
	    String subjectTitle = request.getParameter("subjectTitle");
	    String contentsEtc = request.getParameter("contentsEtc");

	    NoticeVO vo = new NoticeVO(); 
	    vo.setReportNo(noticeId);
		vo.setSubjectTitle(subjectTitle);
		vo.setContentsEtc(contentsEtc);
		vo.setClassCode("04");
		vo.setInputerNo(super.getUserId(request));
		vo.setInputerName(super.getUserName(request));

		int suc = noticeService.insert(vo);
		if(suc >0) {
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
				    fileVO.setDocId(noticeId);
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
				
				// 파일 이력
				String lineHisId = GuidUtil.getGUID();
				List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
				
				if (fileVOs.size() > 0) {
				    if (appComService.insertFile(fileVOs) > 0) {
						appComService.insertFileHis(fileHisVOs);
				    }
				}
		    } 
		    
		    String[] orgCodes = new String(CommonUtil.nullTrim(request.getParameter("orgCodes"))).split(",");
			String[] orgNames = new String(CommonUtil.nullTrim(request.getParameter("orgNames"))).split(",");
			
			for(int i=0;i<orgCodes.length;i++){
				NoticeSendOrgVO noticesoVO = new NoticeSendOrgVO();
				noticesoVO.setReportNo(noticeId);
				noticesoVO.setOrgId(orgCodes[i]);
				noticesoVO.setOrgName(orgNames[i]);
				
				this.noticeService.insertso(noticesoVO);
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
     *  공지사항 수정
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/modify.do")
    public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();

    	try {
    		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
    		HttpSession session = request.getSession();
    		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
    		
    		String reportNo = CommonUtil.nullTrim(request.getParameter("reportNo"));
        	String subjectTitle = CommonUtil.nullTrim(request.getParameter("subjectTitle"));
    	    String contentsEtc = CommonUtil.nullTrim(request.getParameter("contentsEtc"));
    	    List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("attachFile"), uploadTemp + "/" + compId);

    	    NoticeVO vo = new NoticeVO(); 
    	    vo.setReportNo(reportNo);
    		vo.setSubjectTitle(subjectTitle);
    		vo.setContentsEtc(contentsEtc);
    		
    		int suc = this.noticeService.update(vo);
    		
    		if(suc >0) {
    			FileVO delfileVO = new FileVO();
    			delfileVO.setDocId(reportNo);
    			delfileVO.setCompId(compId);
    			appComService.deleteFile(delfileVO);
    			
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
    				    fileVO.setDocId(reportNo);
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
    				
    				// 파일 이력
    				String lineHisId = GuidUtil.getGUID();
    				List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
    				
    				if (fileVOs.size() > 0) {
						if (appComService.insertFile(fileVOs) > 0) {
    						appComService.insertFileHis(fileHisVOs);
    				    }
    				}
    		    } 
    		    
    		    String[] orgCodes = new String(CommonUtil.nullTrim(request.getParameter("orgCodes"))).split(",");
    			String[] orgNames = new String(CommonUtil.nullTrim(request.getParameter("orgNames"))).split(",");
    			
    			// 수정할때의 공지사항은 하나니까 그냥 넘어가자....
    			if(this.noticeService.deleteso("'"+reportNo+"'")>0){
    				for(int i=0;i<orgCodes.length;i++){
        				NoticeSendOrgVO noticesoVO = new NoticeSendOrgVO();
        				noticesoVO.setReportNo(reportNo);
        				noticesoVO.setOrgId(orgCodes[i]);
        				noticesoVO.setOrgName(orgNames[i]);
        				
        				this.noticeService.insertso(noticesoVO);
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
     *  공지사항 수정 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/edit.do")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String reportNo = CommonUtil.nullTrim(request.getParameter("reportNo"));
    	
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setReportNo(reportNo);
		noticeVO = this.noticeService.getNotice(noticeVO);
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", noticeVO.getReportNo());
	    map.put("compId", super.getCompId(request));
	    List<FileVO> fileVOs =  appComService.listFile(map);
		
	    ModelAndView mav = new ModelAndView("NoticeController.edit");
		mav.addObject("reportNo", noticeVO.getReportNo());
		mav.addObject("subjectTitle", noticeVO.getSubjectTitle());
		mav.addObject("contentsEtc", noticeVO.getContentsEtc());
		mav.addObject("classCode", noticeVO.getClassCode());
		mav.addObject("receiveOrgs", noticeVO.getReceiveOrgs());
		mav.addObject("receiveOrgNames", noticeVO.getReceiveOrgNames());
		mav.addObject("fileVOs", fileVOs);
		return mav;
    }
    
    /**
     * <pre> 
     *  공지사항 수정 페이지
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/view.do")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String reportNo = CommonUtil.nullTrim(request.getParameter("reportNo"));
    	
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setReportNo(reportNo);
		noticeVO.setReceiveOrgs(super.getDeptId(request));
		noticeVO = this.noticeService.getNotice(noticeVO);
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", noticeVO.getReportNo());
	    map.put("compId", super.getCompId(request));
	    List<FileVO> fileVOs =  appComService.listFile(map);
		
		ModelAndView mav = new ModelAndView("NoticeController.view");
		mav.addObject("reportNo", noticeVO.getReportNo());
		mav.addObject("subjectTitle", noticeVO.getSubjectTitle());
		mav.addObject("contentsEtc", noticeVO.getContentsEtc());
		mav.addObject("classCode", noticeVO.getClassCode());
		mav.addObject("receiveOrgs", noticeVO.getReceiveOrgs());
		mav.addObject("receiveOrgNames", noticeVO.getReceiveOrgNames());
		mav.addObject("fileVOs", fileVOs);
		return mav;
    }
    
    /**
     * <pre> 
     *  공지사항 삭제
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();

    	try {
    		String reportNos = CommonUtil.nullTrim(request.getParameter("reportNos"));
    		
    		int suc = this.noticeService.delete(reportNos);

    		if(suc >0) {
    			suc = this.noticeService.deleteso(reportNos);
    			if(suc >0){
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
     *  편철 목록 프린트
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/notice/print.do")
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
		param.put(PAGE_NO, "1");
		param.put(LIST_NUM, "1000");
		param.put(SCREEN_COUNT, "1");
		
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setSearchValue(searchValue);
		Page noticePage = this.noticeService.getList(noticeVO, Integer.parseInt(pageNo));
		
		ModelAndView mav = new ModelAndView("NoticeController.print");
		mav.addObject(ROWS, noticePage.getList());
		mav.addObject(VALUE, searchValue);
		mav.addObject(DEPT_ID, deptId);
		mav.addObject(DEPT_NAME, StringUtils.defaultIfEmpty(deptName, m.getMessage("bind.obj.company", null, super.getLocale(request))));
		mav.addObject(PAGE_NO, pageNo);
		return mav;
    }
}
