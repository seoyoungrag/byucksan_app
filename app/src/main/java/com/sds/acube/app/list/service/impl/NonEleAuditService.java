/**
 * 
 */
package com.sds.acube.app.list.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.INonEleAuditService;

/**
 * Class Name : NonEleAuditService.java <br> Description : 일상감사일지 등록 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 20. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 20.
 * @version  1.0
 * @see  com.sds.acube.app.list.service.impl.NonEleAuditService.java
 */

@SuppressWarnings("serial")
@Service("nonEleAuditService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class NonEleAuditService extends BaseService implements INonEleAuditService {

	/**
	 */
	@Autowired
	ICommonDAO commonDAO;

	/**
	 */
	@Autowired
	IAppComService appComService;

	/**
	 */
	@Autowired
	private IAttachService attachService;

	/**
	 */
	@Autowired
	private IEnvOptionAPIService envOptionAPIService;

	/**
	 */
	@Autowired
	private IOrgService orgService;

	/*
	 * <pre> 일상감사일지 등록처리한다. </pre>
	 * 
	 * @param auditListVO 등록할 감사일지 목록 정보 (AuditListVO)
	 * 
	 * @param currentDate 현재 일시
	 * 
	 * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleAuditService#insertNonEleAudit(com
	 * .sds.acube.app.common.vo.AuditListVO)
	 */
	public ResultVO insertNonEleAudit(AuditListVO auditListVO, String currentDate) throws Exception {
		String lineHisId = GuidUtil.getGUID();
		String insp = appCode.getProperty("INSP", "INSP", "NCT");

		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(auditListVO.getCompId());
		drmParamVO.setUserId(auditListVO.getRegisterId());

		List<FileVO> fileVOs = auditListVO.getFileInfo();
		fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
		// 파일 이력
		List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);

		// 파일/파일이력
		if (fileVOs.size() > 0) {
			if (appComService.insertFile(fileVOs) > 0) {
				appComService.insertFileHis(fileHisVOs);
			}
		}

		DocNumVO docNumVO = new DocNumVO();
		docNumVO.setCompId(auditListVO.getCompId());
		String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 관인
		// 타입
		OrganizationVO orgVO = orgService.selectHeadOrganizationByRoleCode(auditListVO.getCompId(), auditListVO.getRegisterDeptId(), orgType);
		docNumVO.setDeptId(orgVO.getOrgID());
		/*
		 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
		 * String numberingType =
		 * envOptionAPIService.selectOptionValue(auditListVO.getCompId(),
		 * opt318); String day = "1900/01/01"; if ("2".equals(numberingType)) {
		 * day = envOptionAPIService.selectOptionText(auditListVO.getCompId(),
		 * opt318); } String year = currentDate.substring(0, 4); String baseDate
		 * = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
		 * String basicFormat =
		 * AppConfig.getProperty("basic_format",
		 * "yyyyMMddHHmmss", "date"); if
		 * (baseDate.compareTo(DateUtil.getFormattedDate(currentDate,
		 * basicFormat)) > 0) { year = "" + (Integer.parseInt(year) - 1); }
		 * docNumVO.setNumYear(year);
		 */
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(auditListVO.getCompId()));
		docNumVO.setNumType(insp);
		int num = appComService.selectListNum(docNumVO);
		auditListVO.setAuditNumber(num);

		int nRs = appComService.insertAuditList(auditListVO);

		if (nRs == 1) {
			appComService.updateListNum(docNumVO);
		}

		ResultVO resultVO = new ResultVO();

		resultVO.setResultCode("success");
		return resultVO;
	}

	/*
	 * (non-Javadoc) <pre> 일상감사일지 등록처리한다. </pre>
	 * 
	 * @param map 감사일지 조회조건 (회사 ID, 문서ID ,감사일지 ID)
	 * 
	 * @return AuditListVO : 조회된 감사일지 정보
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleAuditService#selectAuditList(java.
	 * util.Map)
	 */
	public AuditListVO selectAuditList(Map<String, String> map) throws Exception {
		List<FileVO> fileInfos = appComService.listFile(map);
		AuditListVO auditListVO = (AuditListVO) commonDAO.getMap("list.slectAuditList", map);
		auditListVO.setFileInfo(fileInfos);
		return auditListVO;
	}

	/**
	 * 
	 * <pre> 
	 *  감사일지를 수정한다.
	 * </pre>
	 * @param auditListVO
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	public ResultVO updateNonEleAudit(AuditListVO auditListVO) throws Exception {
		int nRs = commonDAO.modify("list.updateAuditList", auditListVO);
		ResultVO resultVO = new ResultVO();

		if (nRs > 0) {
			resultVO.setResultCode("success");
		} else {
			resultVO.setResultCode("fail");
		}
		return resultVO;
	}
}
