/**
 * 
 */
package com.sds.acube.app.list.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.INonEleStampSealService;

/**
 * Class Name : NonEleStampSeal.java <br> Description : 서명인/직인날인기록 등록 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 18. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 18.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.NonEleStampSealController.java
 */

@SuppressWarnings("serial")
@Controller
@RequestMapping("/app/list/seal/*.do")
public class NonEleStampSealController extends BaseController {

	/**
	 */
	@Autowired
	INonEleStampSealService stampSealService;
	/**
	 */
	@Autowired
	IOrgService orgService;
	/**
	 */
	@Autowired
	private IEnvOptionAPIService envOptionAPIService;

	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * <pre>
	 *  서명인/직인 날인 기록을 등록한다.
	 * </pre>
	 * 
	 * @param req
	 *            request 객체
	 * @param res
	 *            response 객체
	 * @return ModelAndView 조회시 (직인/날인 정보), 저장시 (결과정보)
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/list/seal/insertNonEleStampSeal.do")
	public ModelAndView insertNonEleStampSeal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String deptId = (String) session.getAttribute("DEPT_ID");
		String deptNm = (String) session.getAttribute("DEPT_NAME");
		String userId = (String) session.getAttribute("USER_ID");
		String userName = (String) session.getAttribute("USER_NAME");
		// String userPosition = (String)
		// session.getAttribute("DISPLAY_POSITION");
		String proxyDeptId = StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"),""); // 대리처리부서
		String proxyDeptName = StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME"),""); // 대리처리부서명

		String method = req.getParameter("method");

		String stampId = req.getParameter("stampId");
		stampId = (stampId == null ? "" : stampId);
		stampId = ("".equals(stampId) ? "" : stampId);
		
		String SPT001 = appCode.getProperty("SPT001", "SPT001", "SPT");// 날인유형(직인)
		String opt373 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 직인날인신청 사용여부
		opt373 = envOptionAPIService.selectOptionValue(compId, opt373);

		// method = 0 : 초기호출 , 1: insert 처리 ajax
		method = (method == null ? "0" : method);

		if ("0".equals(method)) { // 최초호출
			ModelAndView mav = new ModelAndView("NonEleStampSealController.insertNonEleStampSeal");
			if (!"".equals(stampId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("stampId", stampId);
				map.put("compId", compId);
				StampListVO stampListVO = stampSealService.selectStampSeal(map);
				mav.addObject("stampList", stampListVO);
			}

			String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

			opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
			mav.addObject("OPT301", opt301);

			return mav;
		} else {
			try {
				String title = req.getParameter("title");
				String docNumber = req.getParameter("docNumber");
				String enforceDate = req.getParameter("enforceDate");
				enforceDate = ("".equals(enforceDate) ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

				String sealerName = req.getParameter("sealerName");
				String sealerId = req.getParameter("sealerId");
				String sealerPos = req.getParameter("sealerPos");
				String sealDeptId = req.getParameter("sealDeptId");
				String sealDeptName = req.getParameter("sealDeptName");
				String requesterName = CommonUtil.nullTrim(req.getParameter("requesterName"));
				String requesterId = CommonUtil.nullTrim(req.getParameter("requesterId"));
				String requesterPos = CommonUtil.nullTrim(req.getParameter("requesterPos"));
				String requesterDeptId = CommonUtil.nullTrim(req.getParameter("requesterDeptId"));
				String requesterDeptName = CommonUtil.nullTrim(req.getParameter("requesterDeptName"));
				String sealDate = req.getParameter("sealDate");
				sealDate = ("".equals(sealDate) ? "9999-12-31 23:59:59" : sealDate.replaceAll("/", "-"));

				String requestDate = req.getParameter("requestDate");
				requestDate = ("".equals(requestDate) ? "9999-12-31 23:59:59" : requestDate.replaceAll("/", "-"));

				String sender = req.getParameter("sender");
				String receiver = req.getParameter("receiver");
				String docId = req.getParameter("docId");
				String electronDocYn = req.getParameter("electronDocYn");
				String sealType = req.getParameter("sealType");
				String remark = req.getParameter("remark");
				String currentDate = DateUtil.getCurrentDate();
				String deleteYn = "N";

				StampListVO stampListVO = new StampListVO();
				stampListVO.setCompId(compId);
				stampListVO.setDocId(docId);
				stampListVO.setDocNumber(docNumber);
				stampListVO.setElectronDocYn(electronDocYn);
				stampListVO.setEnforceDate(enforceDate);
				stampListVO.setReceiver(receiver);
				stampListVO.setRemark(remark);
				stampListVO.setRequestDate(requestDate);
				stampListVO.setRequesterDeptId(requesterDeptId);
				stampListVO.setRequesterDeptName(requesterDeptName);
				stampListVO.setRequesterId(requesterId);
				stampListVO.setRequesterName(requesterName);
				stampListVO.setRequesterPos(requesterPos);
				stampListVO.setSealDate(sealDate);
				stampListVO.setSealDeptId(sealDeptId);
				stampListVO.setSealDeptName(sealDeptName);
				stampListVO.setSealerId(sealerId);
				stampListVO.setSealerName(sealerName);
				stampListVO.setSealerPos(sealerPos);
				stampListVO.setSealType(sealType);
				stampListVO.setSender(sender);
				stampListVO.setTitle(title);
				stampListVO.setRegistDate(currentDate);
				stampListVO.setRegisterId(userId);
				stampListVO.setRegisterName(userName);
				stampListVO.setRegisterDeptId(deptId);
				stampListVO.setRegisterDeptName(deptNm);
				stampListVO.setDeleteYn(deleteYn);
				
				if(!"".equals(proxyDeptId)){
				    stampListVO.setOwnDeptId(proxyDeptId);
				    stampListVO.setOwnDeptName(proxyDeptName);
				}else{
				    stampListVO.setOwnDeptId(deptId);
				    stampListVO.setOwnDeptName(deptNm);
				}
				
				if(SPT001.equals(sealType) && "Y".equals(opt373)){
				    if("".equals(deptId) || "".equals(deptNm)){
					stampListVO.setRequesterDeptId(deptId);
					stampListVO.setRequesterDeptName(deptNm);
				    }
				}

				String stempId = GuidUtil.getGUID();
				stampListVO.setStampId(stempId);

				/*
				 * if("".equals(docId)){ docId = GuidUtil.getGUID("APP");
				 * stempListVO.setDocId(docId); }
				 */

				ResultVO result = stampSealService.insertNonEleStampSeal(stampListVO, deptId, proxyDeptId, currentDate);
				mapper.writeValue(res.getOutputStream(), result);
			} catch (Exception e) {
				ResultVO result = new ResultVO();
				result.setErrorMessage(e.getMessage());
				result.setResultCode("fail");
				result.setResultMessageKey(Integer.toString(e.hashCode()));
				logger.debug(e);
				mapper.writeValue(res.getOutputStream(), result);
			}
		}

		return null;
	}

	/**
	 * <pre>
	 *  서명인/직인 날인 기록을 수정한다.
	 * </pre>
	 * 
	 * @param req
	 *            request 객체
	 * @param res
	 *            response 객체
	 * @return ModelAndView 조회시 (직인/날인 정보), 저장시 (결과정보)
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/list/seal/updateNonEleStampSeal.do")
	public ModelAndView updateNonEleStampSeal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		
		String method = req.getParameter("method");

		String stampId = req.getParameter("stampId");
		stampId = (stampId == null ? "" : stampId);
		stampId = ("".equals(stampId) ? "" : stampId);

		// method = 0 : 초기호출 , 1: insert 처리 ajax
		method = (method == null ? "0" : method);

		if ("0".equals(method)) { // 최초호출
			ModelAndView mav = new ModelAndView("NonEleStampSealController.updateNonEleStampSeal");
			if (!"".equals(stampId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("stampId", stampId);
				map.put("compId", compId);
				StampListVO stampListVO = stampSealService.selectStampSeal(map);
				mav.addObject("stampList", stampListVO);
			}

			String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

			opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
			mav.addObject("OPT301", opt301);

			return mav;
		} else {
			try {
				String title = req.getParameter("title");
				String docNumber = req.getParameter("docNumber");
				String enforceDate = req.getParameter("enforceDate");
				enforceDate = ("".equals(enforceDate) == true ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

				String sealerName = req.getParameter("sealerName");
				String sealerId = req.getParameter("sealerId");
				String sealerPos = req.getParameter("sealerPos");
				String sealDeptId = req.getParameter("sealDeptId");
				String sealDeptName = req.getParameter("sealDeptName");
				String requesterName = CommonUtil.nullTrim(req.getParameter("requesterName"));
				String requesterId = CommonUtil.nullTrim(req.getParameter("requesterId"));
				String requesterPos = CommonUtil.nullTrim(req.getParameter("requesterPos"));
				String requesterDeptId = CommonUtil.nullTrim(req.getParameter("requesterDeptId"));
				String requesterDeptName = CommonUtil.nullTrim(req.getParameter("requesterDeptName"));
				String sealDate = req.getParameter("sealDate");
				sealDate = ("".equals(sealDate) == true ? "9999-12-31 23:59:59" : sealDate.replaceAll("/", "-"));

				String requestDate = req.getParameter("requestDate");
				requestDate = ("".equals(requestDate) == true ? "9999-12-31 23:59:59" : requestDate.replaceAll("/", "-"));

				String sender = req.getParameter("sender");
				String receiver = req.getParameter("receiver");
				String remark = req.getParameter("remark");

				StampListVO stempListVO = new StampListVO();
				stempListVO.setCompId(compId);
				stempListVO.setStampId(stampId);
				stempListVO.setDocNumber(docNumber);
				stempListVO.setEnforceDate(enforceDate);
				stempListVO.setReceiver(receiver);
				stempListVO.setRemark(remark);
				stempListVO.setRequestDate(requestDate);
				stempListVO.setRequesterDeptId(requesterDeptId);
				stempListVO.setRequesterDeptName(requesterDeptName);
				stempListVO.setRequesterId(requesterId);
				stempListVO.setRequesterName(requesterName);
				stempListVO.setRequesterPos(requesterPos);
				stempListVO.setSealDate(sealDate);
				stempListVO.setSealDeptId(sealDeptId);
				stempListVO.setSealDeptName(sealDeptName);
				stempListVO.setSealerId(sealerId);
				stempListVO.setSealerName(sealerName);
				stempListVO.setSealerPos(sealerPos);
				stempListVO.setSender(sender);
				stempListVO.setTitle(title);
				
				ResultVO result = stampSealService.updateStampList(stempListVO);
				mapper.writeValue(res.getOutputStream(), result);
			} catch (Exception e) {
				ResultVO result = new ResultVO();
				result.setErrorMessage(e.getMessage());
				result.setResultCode("fail");
				result.setResultMessageKey(Integer.toString(e.hashCode()));
				logger.debug(e);
				mapper.writeValue(res.getOutputStream(), result);
			}
		}

		return null;
	}

	/**
	 * <pre>
	 *  감사직인날인대장 기록을 등록한다.
	 * </pre>
	 * 
	 * @param req
	 *            request 객체
	 * @param res
	 *            response 객체
	 * @return ModelAndView 조회시 (감사날인정보), 저장시(처리결과 정보)
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/list/seal/insertAuditSeal.do")
	public ModelAndView insertAuditSeal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String deptId = (String) session.getAttribute("DEPT_ID");
		String deptNm = (String) session.getAttribute("DEPT_NAME");
		String userId = (String) session.getAttribute("USER_ID");
		String userName = (String) session.getAttribute("USER_NAME");
		// String userPosition = (String)
		// session.getAttribute("DISPLAY_POSITION");
		String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); // 대리처리부서
		// 대리처리부서 처리
		proxyDeptId = (proxyDeptId == null ? "" : proxyDeptId);

		String method = req.getParameter("method");

		String stampId = req.getParameter("stampId");
		stampId = (stampId == null ? "" : stampId);
		stampId = ("".equals(stampId) == true ? "" : stampId);

		// method = 0 : 초기호출 , 1: insert 처리 ajax
		method = (method == null ? "0" : method);

		if ("0".equals(method)) { // 최초호출
			ModelAndView mav = new ModelAndView("NonEleStampSealController.insertAuditSeal");
			List<OrganizationVO> orgs = orgService.selectSubAllOrganizationList(compId, 1, false);

			for (int i = 0; i < orgs.size(); i++) {
				OrganizationVO org = orgs.get(i);

				if (org.getIsInspection() && org.getOrgType() != 1) {
					mav.addObject("sender", org);
					break;
				}
			}

			if (!"".equals(stampId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("stampId", stampId);
				map.put("compId", compId);
				StampListVO stampListVO = stampSealService.selectStampSeal(map);
				mav.addObject("stampList", stampListVO);
			}

			String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

			opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
			mav.addObject("OPT301", opt301);

			return mav;
		} else {
			try {
				String title = req.getParameter("title");
				String docNumber = req.getParameter("docNumber");
				String enforceDate = req.getParameter("enforceDate");
				enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));
				enforceDate = ("".equals(enforceDate) == true ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

				String sealerName = req.getParameter("sealerName");
				String sealerId = req.getParameter("sealerId");
				String sealerPos = req.getParameter("sealerPos");
				String sealDeptId = req.getParameter("sealDeptId");
				String sealDeptName = req.getParameter("sealDeptName");
				String requesterName = CommonUtil.nullTrim(req.getParameter("requesterName"));
				String requesterId = CommonUtil.nullTrim(req.getParameter("requesterId"));
				String requesterPos = CommonUtil.nullTrim(req.getParameter("requesterPos"));
				String requesterDeptId = CommonUtil.nullTrim(req.getParameter("requesterDeptId"));
				String requesterDeptName = CommonUtil.nullTrim(req.getParameter("requesterDeptName"));
				String sealDate = req.getParameter("sealDate");
				sealDate = (sealDate == null ? "9999-12-31 23:59:59" : sealDate.replaceAll("/", "-"));
				sealDate = ("".equals(sealDate) == true ? "9999-12-31 23:59:59" : sealDate.replaceAll("/", "-"));

				String requestDate = req.getParameter("requestDate");
				requestDate = (requestDate == null ? "9999-12-31 23:59:59" : requestDate.replaceAll("/", "-"));
				requestDate = ("".equals(requestDate) == true ? "9999-12-31 23:59:59" : requestDate.replaceAll("/", "-"));

				String sender = req.getParameter("sender");
				String receiver = req.getParameter("receiver");
				String docId = req.getParameter("docId");
				String electronDocYn = req.getParameter("electronDocYn");
				String sealType = req.getParameter("sealType");
				String remark = req.getParameter("remark");
				String currentDate = DateUtil.getCurrentDate();
				String deleteYn = "N";

				String modYn = req.getParameter("modYn");
				modYn = (modYn == null ? "N" : modYn);
				modYn = ("".equals(modYn) == true ? "N" : modYn);

				String SPT005 = appCode.getProperty("SPT005", "SPT005", "SPT");
				if (SPT005.equals(sealType) && "9999-12-31 23:59:59".equals(enforceDate)) {
					enforceDate = currentDate; // 시행일자
				}

				StampListVO stempListVO = new StampListVO();
				stempListVO.setCompId(compId);
				stempListVO.setDocId(docId);
				stempListVO.setDocNumber(docNumber);
				stempListVO.setElectronDocYn(electronDocYn);
				stempListVO.setEnforceDate(enforceDate);
				stempListVO.setReceiver(receiver);
				stempListVO.setRemark(remark);
				stempListVO.setRequestDate(requestDate);
				stempListVO.setRequesterDeptId(requesterDeptId);
				stempListVO.setRequesterDeptName(requesterDeptName);
				stempListVO.setRequesterId(requesterId);
				stempListVO.setRequesterName(requesterName);
				stempListVO.setRequesterPos(requesterPos);
				stempListVO.setSealDate(sealDate);
				stempListVO.setSealDeptId(sealDeptId);
				stempListVO.setSealDeptName(sealDeptName);
				stempListVO.setSealerId(sealerId);
				stempListVO.setSealerName(sealerName);
				stempListVO.setSealerPos(sealerPos);
				stempListVO.setSealType(sealType);
				stempListVO.setSender(sender);
				stempListVO.setTitle(title);
				stempListVO.setRegistDate(currentDate);
				stempListVO.setRegisterId(userId);
				stempListVO.setRegisterName(userName);
				stempListVO.setRegisterDeptId(deptId);
				stempListVO.setRegisterDeptName(deptNm);
				stempListVO.setDeleteYn(deleteYn);

				if ("N".equals(modYn)) {
					stampId = GuidUtil.getGUID();

				}

				stempListVO.setStampId(stampId);

				/*
				 * if("".equals(docId)){ docId = GuidUtil.getGUID("APP");
				 * stempListVO.setDocId(docId); }
				 */
				ResultVO result = null;
				if ("N".equals(modYn)) {
					result = stampSealService.insertNonEleStampSeal(stempListVO, deptId, proxyDeptId, currentDate);
				} else {
					result = stampSealService.updateStampList(stempListVO);
				}

				mapper.writeValue(res.getOutputStream(), result);
			} catch (Exception e) {
				ResultVO result = new ResultVO();
				result.setErrorMessage(e.getMessage());
				result.setResultCode("fail");
				result.setResultMessageKey(Integer.toString(e.hashCode()));
				logger.debug(e);
				mapper.writeValue(res.getOutputStream(), result);
			}
		}

		return null;
	}

	/**
	 * 
	 * <pre>
	 *  직인  정보를 삭제한다.
	 * </pre>
	 * 
	 * @param request
	 *            request 객체
	 * @param response
	 *            response 객체
	 * @return ModelAndView 안에 처리 결과 정보를 등록하여 리턴한다.
	 * @throws Exception
	 * @see
	 * 
	 */
	@RequestMapping("/app/list/seal/deleteStampList.do")
	public ModelAndView deleteStampList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("AppComController.deleteAuditList");
		HttpSession session = request.getSession();
		String[] stampIds = request.getParameterValues("stampId");
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
		// 아이디
		String currentDate = DateUtil.getCurrentDate();

		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < stampIds.length; i++) {
			StampListVO param = new StampListVO();
			param.setCompId(compId);
			param.setStampId(stampIds[i]);
			param.setRegistDate(currentDate);
			params.add(param);
		}

		ResultVO result = stampSealService.deleteStampList(params);

		if ("success".equals(result.getResultCode())) {
			mav.addObject("result", "success");
			mav.addObject("message", "appcom.msg.deleted.seal.audit.doc");
		} else {
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.notexist.document");
		}

		return mav;
	}
	
	/**
	 * 
	 * <pre> 
	 *  서명인날인 승인
	 * </pre>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/list/seal/confirmStampSealList.do")
	public ModelAndView confirmStampSealList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    String[] stampIds = request.getParameterValues("stampId");
	    String lobCode = StringUtil.null2str(request.getParameter("lobCode"), "");
	    String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사
	    String deptId =  StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서
	    String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	    if(!"".equals(proxyDeptId)){
		deptId = proxyDeptId;  
	    }
	    
	    String lol004 = appCode.getProperty("LOL004", "LOL004", "LOL");// 서명인날인대장
	    String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL");// 직인날인대장
	    
	    String numType = "";
	    
	    if(lol004.equals(lobCode)){
		numType = appCode.getProperty("SIGN", "SIGN", "NCT");// 서명인날인대장
	    }else if(lol005.equals(lobCode)){
		numType = appCode.getProperty("SEAL", "SEAL", "NCT");// 관인날인대장
		String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 관인
		OrganizationVO orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
		deptId = orgVO.getOrgID();
	    }

	    int stampCount = stampIds.length;
	    int loopSize = stampCount - 1;
	    int confirmCount = 0;
	    for (int loop = loopSize; loop >= 0; loop--) {
		String stampId = CommonUtil.nullTrim(stampIds[loop]);
		if (!"".equals(stampId)) {
		    int sealNumber = stampSealService.selectStampSealNumber(compId, stampId);
		    if (sealNumber == 999999) {
			DocNumVO docNumVO = new DocNumVO();
			docNumVO.setCompId(compId);
        		docNumVO.setNumType(numType);
        		docNumVO.setDeptId(deptId);
        
        		ResultVO resultVO = (ResultVO) stampSealService.confirmStampSealList(docNumVO, stampId);
        		if ("success".equals(resultVO.getResultCode())) {
        		    confirmCount++;
        		}
		    }
		}
	    }
		
	    
	    ModelAndView mav = new ModelAndView("AppComController.confirmStampSealList");
	    
	    if (confirmCount == 0) {
		mav.addObject("result", "fail");	    
		mav.addObject("message", "list.msg.fail.confirmStampSeal");	    
	    } else {
		mav.addObject("result", "success");	    
		mav.addObject("message", "list.msg.success.confirmStampSeal");
		mav.addObject("count", String.valueOf(confirmCount));
	    }

	    return mav;
	}
	
	/**
	 * 
	 * <pre> 
	 *  서명인 날인 삭제
	 * </pre>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/list/seal/deleteStampSealList.do")
	public ModelAndView deleteStampSealList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String[] stampIds = request.getParameterValues("stampId");
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

		int stampCount = stampIds.length;
		int deleteCount = 0;
		for(int loop = 0; loop < stampCount; loop++){
		    String stampId = CommonUtil.nullTrim(stampIds[loop]);
		    if (!"".equals(stampId)) {
			int sealNumber = stampSealService.selectStampSealNumber(compId, stampId);

			StampListVO stampListVO = new StampListVO();
			stampListVO.setCompId(compId);
			stampListVO.setStampId(stampId);
			stampListVO.setSealNumber(sealNumber);

			ResultVO resultVO = (ResultVO) stampSealService.deleteStampSeal(stampListVO);
			if ("success".equals(resultVO.getResultCode())) {
			    deleteCount++;
			}

		    }

		    
		}
		

		ModelAndView mav = new ModelAndView("AppComController.deleteStampSealList");

		if (deleteCount == 0) {
		    mav.addObject("result", "fail");	    
		    mav.addObject("message", "list.msg.fail.deleteStampSealList");	    
		} else {
		    mav.addObject("result", "success");	    
		    mav.addObject("message", "list.msg.success.deleteStampSealList");
		    mav.addObject("count", String.valueOf(deleteCount));
		}

		return mav;
	}

}
