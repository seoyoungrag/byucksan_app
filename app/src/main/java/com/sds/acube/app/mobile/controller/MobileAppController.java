/**
 * 
 */
package com.sds.acube.app.mobile.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.mobile.service.IMobileAppService;
import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppActionVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO;

/** 
 *  Class Name  : MobileAppController.java <br>
 *  Description : 모바일 웹서비스 service 호출 테스트를 위한 Controller  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 18. <br>
 *  수 정  자 : jd.park  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jd.park
 *  @since 2012. 6. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.mobile.service.impl.MobileAppController.java
 */

@SuppressWarnings("serial")
@Controller("mobileAppController")
@RequestMapping("/app/mobile/*.do")
public class MobileAppController extends BaseController {
	
	/**
	  * 모바일 웹서비스 service 호출
	 */
    @Inject
    @Named("mobileAppService")
    private IMobileAppService mobileAppService;  
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
     * 
     * <pre> 
     *  모바일 함별 건수
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/mobile/listMobileBox.do") 
    public ModelAndView listMobileBox(HttpServletRequest req) throws Exception {	
		HttpSession session = req.getSession();	
		String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
		String loginId = (String)session.getAttribute("LOGIN_ID");		// 로그인 ID
		
		AppReqVO reqVo = new AppReqVO();
		reqVo.setReqtype("listMobileBox");								
		reqVo.setOrgcode(compId);
		reqVo.setUserid(loginId);
		
		AppMenuVOs menuVo = mobileAppService.getListMobileBox(reqVo);
		
		ModelAndView mav = new ModelAndView("MobileAppController.listMobileBox");
	
		mav.addObject("AppMenuCnt", menuVo);
	
		return mav;
    }
    
    /**
     * 
     * <pre> 
     *  결재 목록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/mobile/listMobileAppDoc.do")
    public ModelAndView listMobileAppDoc(HttpServletRequest req) throws Exception {
    	HttpSession session = req.getSession();	
		String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
		String loginId = (String)session.getAttribute("LOGIN_ID");		// 로그인 ID		
			
		//함 종류 
		String itemCode = CommonUtil.nullTrim(req.getParameter("itemCode"));
		
		//함종류가 없을때는 초기화면
		if(itemCode.equals("")){
			itemCode ="OPT103";
		}
		//목록 건수(보여줄 건수)
		String sTotalCnt = CommonUtil.nullTrim(req.getParameter("totalCnt"));
		if(sTotalCnt.equals("")){
			sTotalCnt = "15";
		}		
		int totalCnt = Integer.parseInt(sTotalCnt);
		
		//시작일
		String startDate = CommonUtil.nullTrim(req.getParameter("start"));
		if(!startDate.equals("")){
			startDate +=" 00:00:00";
		}
		//종료일
		String endDate = CommonUtil.nullTrim(req.getParameter("end"));
		if(!endDate.equals("")){
			endDate +=" 23:59:59";
		}
		
		AppReqVO reqVo = new AppReqVO();
		reqVo.setReqtype("listAppDoc");								
		reqVo.setOrgcode(compId);
		reqVo.setUserid(loginId);
		reqVo.setItemid(itemCode);
		reqVo.setReqcount(totalCnt);	
		reqVo.setFromymd(startDate);
		reqVo.setFromymd(endDate);
		
		AppListVOs listVo = mobileAppService.getListMobileAppDoc(reqVo);	
		
		// 다국어 추가
		List<OptionVO> optList = envOptionAPIService.selectOptionGroupListResource(compId, "OPTG100", AppConfig.getCurrentLangType());
		// List<OptionVO> optList = envOptionAPIService.selectOptionGroupList(compId, "OPTG100");
		ModelAndView mav = new ModelAndView("MobileAppController.listMobileAppDoc");
		
		mav.addObject("mobileAppList", listVo);
		mav.addObject("optList", optList);
		mav.addObject("itemCode", itemCode);
		mav.addObject("totalCnt", sTotalCnt);
		return mav;
    }
    
     /**
     * 
     * <pre> 
     * 모바일 결재상세내용 조회 
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/mobile/selectDocInfoMobile.do")
    public ModelAndView selectDocInfoMobile (HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
	String loginId = (String)session.getAttribute("LOGIN_ID");		// 로그인 ID
	String docId = CommonUtil.nullTrim(req.getParameter("docId"));		// 문서 ID
	
	AppReqVO reqVo = new AppReqVO();
	reqVo.setReqtype("selectDocInfoMobile");
	reqVo.setItemid(docId);
	reqVo.setOrgcode(compId);	
	AppDetailVO detailVo = mobileAppService.getSelectDocInfoMobile(reqVo);	
	
	ModelAndView mav = new ModelAndView("MobileAppController.selectDocInfoMobile");
	mav.addObject("SelectDocInfo", detailVo);
	
	return mav;
       
    }   
    
    /**
     * 
     * <pre> 
     *  결재 처리
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/mobile/processAppMobile.do")
    public ModelAndView processDoc(HttpServletRequest req) throws Exception {
    	HttpSession session = req.getSession();	
		String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
		String loginId = (String)session.getAttribute("LOGIN_ID");		// 로그인 ID		
		
		AppResultVO resultVo = new AppResultVO();
		//화면구분
		String section = CommonUtil.nullTrim(req.getParameter("section"));
		//문서번호
		String docId = CommonUtil.nullTrim(req.getParameter("docId"));
		
		AppReqVO reqVo = new AppReqVO();
		reqVo.setReqtype("selectDocInfoMobile");
		reqVo.setItemid(docId);
		reqVo.setOrgcode(compId);
		
		AppDetailVO detailVo = mobileAppService.getSelectDocInfoMobile(reqVo);
		
		
		if(section.equals("result")){	
			
			//결재암호
			String userPassword = CommonUtil.nullTrim(req.getParameter("password"));
			
			//결재의견
			String appOpinion = CommonUtil.nullTrim(req.getParameter("opinion"));
			
			//처리유형
			String actionCode = CommonUtil.nullTrim(req.getParameter("actionCode"));
						
			AppActionVO actionVo = new AppActionVO();
			actionVo.setReqtype("updateApproval");
			actionVo.setDocid(docId);
			actionVo.setUserid(loginId);
			actionVo.setOrgcode(compId);
			actionVo.setActioncode(actionCode);
			actionVo.setUserpassword(userPassword);
			actionVo.setAppopinion(appOpinion);
			
			resultVo = mobileAppService.processAppMobile(actionVo);
		}else{
			resultVo.setResposeCode("insert");
		}
		
		ModelAndView mav = new ModelAndView("MobileAppController.processDoc");
		
		mav.addObject("result", resultVo);
		mav.addObject("detail", detailVo);
		return mav;
    }
    
   
    @RequestMapping("/app/mobile/attachFileMobile.do")
    public ModelAndView getAttachFile (HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
	String docId = CommonUtil.nullTrim(req.getParameter("docId"));		// 문서 ID
	String fileId = CommonUtil.nullTrim(req.getParameter("fileId"));	//파일ID
	
	AppReqVO reqVo = new AppReqVO();
	reqVo.setReqtype("selectDocInfoMobile");
	reqVo.setItemid(docId);
	reqVo.setOrgcode(compId);
	reqVo.setFileId(fileId);
	
	AppAttachVO attachVO = mobileAppService.getAttachFile(reqVo);
	ModelAndView mav = new ModelAndView("MobileAppController.getAttachFile");
	mav.addObject("AttachList", attachVO);
	
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  모바일 결재처리(QUEUE 생성)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/mobile/processMobileApproval.do")
    public ModelAndView processMobileApproval(HttpServletRequest req) throws Exception {
    	HttpSession session = req.getSession();	
		String compId = (String)session.getAttribute("COMP_ID");		// 회사 ID
		String deptId = (String)session.getAttribute("DEPT_ID");		// 부서 ID
		String loginId = (String)session.getAttribute("LOGIN_ID");		// 로그인 ID		
		
		MobileAppResultVO resultVo = new MobileAppResultVO();
		//화면구분
		String section = CommonUtil.nullTrim(req.getParameter("section"));
		//문서번호
		String docId = CommonUtil.nullTrim(req.getParameter("docId"));
		
		if(section.equals("result")){	
			
			//결재암호
			String userPassword = CommonUtil.nullTrim(req.getParameter("password"));
			
			//결재의견
			String appOpinion = CommonUtil.nullTrim(req.getParameter("opinion"));
			
			//처리유형
			String actionCode = CommonUtil.nullTrim(req.getParameter("actionCode"));
						
			MobileAppActionVO actionVo = new MobileAppActionVO();
			actionVo.setReqtype("updateApproval");
			actionVo.setDocid(docId);
			actionVo.setUserid(loginId);
			actionVo.setOrgcode(compId);
			actionVo.setDeptcode(deptId);
			actionVo.setActioncode(actionCode);
			actionVo.setUserpassword(userPassword);
			actionVo.setAppopinion(appOpinion);
			
			resultVo = mobileAppService.processMobileApproval(actionVo);
		}else{
			resultVo.setRespose_code("insert");
		}
		
		ModelAndView mav = new ModelAndView("ApprovalController.processAppDoc");
		
		mav.addObject("result", resultVo.getRespose_code());
		return mav;
    }
    
}
