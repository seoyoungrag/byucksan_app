/**
 * 
 */
package com.sds.acube.app.list.controller;

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

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.INonEleAuditService;

/**
 * Class Name : NonEleAuditController.java <br> Description : 일상감사일지 등록 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 19. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 19.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.NonEleAuditController.java
 */

@SuppressWarnings("serial")
@Controller("nonEleAuditController")
@RequestMapping("/app/list/audit/*.do")
public class NonEleAuditController extends BaseController {

	/**
	 */
	@Autowired
	INonEleAuditService nonEleAuditService;

	/**
	 */
	@Autowired
	private IEnvOptionAPIService envOptionAPIService;

	private final ObjectMapper mapper = new ObjectMapper();

	private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	/**
	 * 
	 * <pre>
	 *  감사일지 정보를 가져오거나 등록한다.
	 * </pre>
	 * 
	 * @param req
	 *            request 객체
	 * @param res
	 *            response 객체
	 * @return ModelAndView 조회시 (감사일지 정보), 등록시 (처리겨로가 정보)
	 * @throws Exception
	 * @see
	 * 
	 */
	@RequestMapping("/app/list/audit/insertNonEleAudit.do")
	public ModelAndView insertNonEleAudit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String deptId = (String) session.getAttribute("DEPT_ID");
		String deptNm = (String) session.getAttribute("DEPT_NAME");
		String userId = (String) session.getAttribute("USER_ID");
		String userName = (String) session.getAttribute("USER_NAME");
		// String userPosition = (String)
		// session.getAttribute("DISPLAY_POSITION");

		String docId = req.getParameter("docId");
		docId = (docId == null ? "" : docId);
		docId = ("".equals(docId) ? "" : docId);

		String method = req.getParameter("method");

		// method = 0 : 초기호출 , 1: insert 처리 ajax
		method = (method == null ? "0" : method);

		if ("0".equals(method)) { // 최초호출
			ModelAndView mav = new ModelAndView("NonEleAuditController.insertNonEleAudit");
			if (!"".equals(docId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("docId", docId);
				map.put("compId", compId);
				AuditListVO auditListVO = nonEleAuditService.selectAuditList(map);
				mav.addObject("auditList", auditListVO);
			}

			String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
			// -
			// 0
			// :
			// 인증안함,
			// 1
			// :
			// 결재패스워드,
			// 2
			// :
			// 인증서
			opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
			mav.addObject("OPT301", opt301);

			return mav;
		} else {
			String title = req.getParameter("title");
			String chargeDeptId = req.getParameter("chargeDeptId");
			String chargeDeptName = req.getParameter("chargeDeptName");
			String electronDocYn = req.getParameter("electronDocYn");
			String receiveDate = req.getParameter("receiveDate");
			receiveDate = ("".equals(receiveDate) ? "9999-12-31 23:59:59" : receiveDate.replaceAll("/", "-"));

			String approverType = req.getParameter("approverType");
			String approveType = req.getParameter("approveType");
			String approverId = req.getParameter("approverId");
			String approverName = req.getParameter("approverName");
			String approverPos = req.getParameter("approverPos");
			String approverDeptId = req.getParameter("approverDeptId");
			String approverDeptName = req.getParameter("approverDeptName");

			String askType = appCode.getProperty("ART044", "ART044", "ART");
			String procType = appCode.getProperty("APT001", "APT001", "APT");

			String remark = req.getParameter("remark");
			remark = (remark == null ? "" : remark);
			remark = ("".equals(remark) ? "" : remark);

			try {
				docId = GuidUtil.getGUID("ADT");
				String currentDate = DateUtil.getCurrentDate();

				AuditListVO auditListVO = new AuditListVO();
				auditListVO.setDocId(docId);
				auditListVO.setApproverDeptId(approverDeptId);
				auditListVO.setApproverDeptName(approverDeptName);
				auditListVO.setApproverId(approverId);
				auditListVO.setApproverName(approverName);
				auditListVO.setApproverPos(approverPos);
				auditListVO.setApproverType(approverType);
				auditListVO.setApproveType(approveType);
				auditListVO.setAskType(askType);
				auditListVO.setChargeDeptId(chargeDeptId);
				auditListVO.setChargeDeptName(chargeDeptName);
				auditListVO.setCompId(compId);
				auditListVO.setElectronDocYn(electronDocYn);
				auditListVO.setProcType(procType);
				auditListVO.setReceiveDate(receiveDate);
				auditListVO.setRegistDate(currentDate);
				auditListVO.setRegisterDeptId(deptId);
				auditListVO.setRegisterDeptName(deptNm);
				auditListVO.setRegisterId(userId);
				auditListVO.setRegisterName(userName);
				auditListVO.setRemark(remark);
				auditListVO.setTitle(title);
				auditListVO.setDeleteYn("N");

				// 첨부파일--------------------------------------------------------------------------------------------------
				List<FileVO> fileInfos = AppTransUtil.transferFile(req.getParameter("attachFile"), uploadTemp + "/" + compId);
				// AppDocVO appDocVO = new AppDocVO();
				for (int i = 0; i < fileInfos.size(); i++) {
					FileVO fileVO = fileInfos.get(i);
					fileVO.setCompId(compId);
					fileVO.setDocId(docId);
					fileVO.setProcessorId(userId);
					fileVO.setTempYn("N");
					fileVO.setRegisterId(userId);
					fileVO.setRegisterName(userName);
					fileVO.setRegistDate(currentDate);
				}
				// 청부파일 파일정보 저장
				auditListVO.setFileInfo(fileInfos);

				ResultVO result = nonEleAuditService.insertNonEleAudit(auditListVO, currentDate);
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
	 *  감사일지 정보를 가져오거나 수정한다.
	 * </pre>
	 * 
	 * @param req
	 *            request 객체
	 * @param res
	 *            response 객체
	 * @return ModelAndView 조회시 (감사일지 정보), 등록시 (처리겨로가 정보)
	 * @throws Exception
	 * @see
	 * 
	 */
	@RequestMapping("/app/list/audit/updateNonEleAudit.do")
	public ModelAndView updateNonEleAudit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");

		String docId = req.getParameter("docId");
		docId = (docId == null ? "" : docId);
		docId = ("".equals(docId) ? "" : docId);

		String method = req.getParameter("method");

		// method = 0 : 초기호출 , 1: insert 처리 ajax
		method = (method == null ? "0" : method);

		if ("0".equals(method)) { // 최초호출
			ModelAndView mav = new ModelAndView("NonEleAuditController.updateNonEleAudit");
			if (!"".equals(docId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("docId", docId);
				map.put("compId", compId);
				AuditListVO auditListVO = nonEleAuditService.selectAuditList(map);
				mav.addObject("auditList", auditListVO);
			}

			String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
																			
			opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
			mav.addObject("OPT301", opt301);

			return mav;
		} else {
			String approverId = req.getParameter("approverId");
			String approverName = req.getParameter("approverName");
			String approverPos = req.getParameter("approverPos");
			String approverDeptId = req.getParameter("approverDeptId");
			String approverDeptName = req.getParameter("approverDeptName");

			String remark = req.getParameter("remark");
			remark = (remark == null ? "" : remark);
			remark = ("".equals(remark) ? "" : remark);

			try {
				AuditListVO auditListVO = new AuditListVO();
				auditListVO.setDocId(docId);
				auditListVO.setCompId(compId);
				
				auditListVO.setApproverDeptId(approverDeptId);
				auditListVO.setApproverDeptName(approverDeptName);
				auditListVO.setApproverId(approverId);
				auditListVO.setApproverName(approverName);
				auditListVO.setApproverPos(approverPos);
				auditListVO.setRemark(remark);

				ResultVO result = nonEleAuditService.updateNonEleAudit(auditListVO);
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
}
