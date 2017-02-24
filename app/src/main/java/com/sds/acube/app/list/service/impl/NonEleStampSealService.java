/**
 * 
 */
package com.sds.acube.app.list.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.INonEleStampSealService;

/**
 * Class Name : NonEleStampSealService.java <br> Description : 서명인/직인날인기록 등록 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 18. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 18.
 * @version  1.0
 * @see  com.sds.acube.app.list.service.impl.NonEleStampSealService.java
 */

@SuppressWarnings("serial")
@Service
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class NonEleStampSealService extends BaseService implements INonEleStampSealService {

	/**
	 */
	@Autowired
	ICommonDAO commonDAO;
	/**
	 */
	@Autowired
	IAppComService appcomService;

	/**
	 */
	@Autowired
	private IEnvOptionAPIService envOptionAPIService;

	/**
	 */
	@Autowired
	private IOrgService orgService;

	/*
	 * 
	 * <pre> 서명인/직인날인기록 등록 </pre>
	 * 
	 * @param stempListVO 등록할 서명/직인 날인 정보 (StampListVO)
	 * 
	 * @param deptId 부서ID
	 * 
	 * @param proxyDeptId 대리처리부서ID
	 * 
	 * @param currentDate 현재일시
	 * 
	 * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과 (Ajax 처리)
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleStampSealService#insertNonEleStampSeal
	 * (com.sds.acube.app.common.vo.StampListVO)
	 */
	public ResultVO insertNonEleStampSeal(StampListVO stampListVO, String deptId, String proxyDeptId, String currentDate) throws Exception {

		DocNumVO docNumVO = new DocNumVO();
		docNumVO.setCompId(stampListVO.getCompId());

		String SIGN = appCode.getProperty("SIGN", "SIGN", "NCT");// 서명인날인대장
		String SEAL = appCode.getProperty("SEAL", "SEAL", "NCT");// 관인날인대장
		String INSL = appCode.getProperty("INSL", "INSL", "NCT");// 감사직인날인대장

		String SPT001 = appCode.getProperty("SPT001", "SPT001", "SPT");// 날인유형
		// 관인/직인
		String SPT002 = appCode.getProperty("SPT002", "SPT002", "SPT");// 날인유형
		// 서명인/서명인
		String SPT005 = appCode.getProperty("SPT005", "SPT005", "SPT");// 감사직인

		String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 관인
		// 타입
		OrganizationVO orgVO = orgService.selectHeadOrganizationByRoleCode(stampListVO.getCompId(), deptId, orgType);

		if (SPT001.equals(stampListVO.getSealType())) {
			docNumVO.setNumType(SEAL);
			docNumVO.setDeptId(orgVO.getOrgID());
		} else if (SPT002.equals(stampListVO.getSealType())) {
			docNumVO.setNumType(SIGN);
			if (!"".equals(proxyDeptId)) {
				docNumVO.setDeptId(proxyDeptId);
			} else {
				docNumVO.setDeptId(deptId);
			}
		} else if (SPT005.equals(stampListVO.getSealType())) {
			docNumVO.setNumType(INSL);
			docNumVO.setDeptId(orgVO.getOrgID());
		}
		/*
		 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
		 * String day =
		 * envOptionAPIService.selectOptionText(stempListVO.getCompId(),
		 * opt318); String year = currentDate.substring(0, 4); String baseDate =
		 * year + day.substring(5, 7) + day.substring(8, 10) + "000000"; String
		 * basicFormat =
		 * AppConfig.getProperty("basic_format",
		 * "yyyyMMddHHmmss", "date"); if
		 * (baseDate.compareTo(DateUtil.getFormattedDate(currentDate,
		 * basicFormat)) > 0) { year = "" + (Integer.parseInt(year) - 1); }
		 * 
		 * docNumVO.setNumYear(year);
		 */
		String opt367 = appCode.getProperty("OPT367", "OPT367", "OPT"); // 서명인 날인 승인 사용 여부
		opt367 = StringUtil.null2str(envOptionAPIService.selectOptionValue(stampListVO.getCompId(),opt367),"N");
		
		String opt373 = appCode.getProperty("OPT373", "OPT373", "OPT"); // 직인 날인 승인 사용 여부
		opt373 = StringUtil.null2str(envOptionAPIService.selectOptionValue(stampListVO.getCompId(),opt373),"N");
		
		int sealNumber = 0;
		if (SPT002.equals(stampListVO.getSealType()) || SPT001.equals(stampListVO.getSealType())) {
			if( ( "Y".equals(opt367) && SPT002.equals(stampListVO.getSealType()))
					||  ( "Y".equals(opt373) && SPT001.equals(stampListVO.getSealType())) ){
				sealNumber = 999999;
			}else {
				docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(stampListVO.getCompId()));
		
				sealNumber = appcomService.selectListNum(docNumVO);
				stampListVO.setSealProcDate(stampListVO.getRegistDate());
			}
		}else if(SPT005.equals(stampListVO.getSealType()) ){
		
			docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(stampListVO.getCompId()));
		
			sealNumber = appcomService.selectListNum(docNumVO);
			stampListVO.setSealProcDate(stampListVO.getRegistDate());
		
		}

		stampListVO.setSealNumber(sealNumber);
 		int nRs = commonDAO.insert("list.insertStampSeal", stampListVO);
		if (nRs > 0) {
 		    if( ( !"Y".equals(opt367) && SPT002.equals(stampListVO.getSealType()))
			    ||  ( !"Y".equals(opt373) && SPT001.equals(stampListVO.getSealType())) ){					    
 		    	docNumVO.setNum(sealNumber);
 		    	appcomService.updateListNum(docNumVO);

		    }else if(SPT005.equals(stampListVO.getSealType()) ){
 			docNumVO.setNum(sealNumber);
			appcomService.updateListNum(docNumVO);
 
		    }
 		}
		

		ResultVO result = new ResultVO();
		result.setResultCode("sucess");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleStampSealService#selectStampSeal(java
	 * .util.Map)
	 */
	public StampListVO selectStampSeal(Map<String, String> map) throws Exception {
		return (StampListVO) commonDAO.getMap("list.slectStampList", map);
	}

	/*
	 * <pre> 서명인/직인날인기록 삭제 </pre>
	 * 
	 * @param params 삭제 목록
	 * 
	 * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과 (Ajax 처리)
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleStampSealService#deleteStampList(java
	 * .util.Map)
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public ResultVO deleteStampList(List<Object> params) throws Exception {		
    
	    int rs = commonDAO.modifyList("list.deleteStampSeal", params);

		ResultVO result = new ResultVO();

		if (rs > 0) {
			result.setResultCode("success");
		} else {
			result.setResultCode("fail");
		}
		return result;
	}

	/*
	 * <pre> 서명인/직인날인기록 수정 </pre>
	 * 
	 * @param stempListVO 서명날인 수정 정보 (StempListVO)
	 * 
	 * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과 (Ajax 처리)
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.sds.acube.app.list.service.INonEleStampSealService#updateStampList(com
	 * .sds.acube.app.common.vo.StampListVO)
	 */
	public ResultVO updateStampList(StampListVO stempListVO) throws Exception {
		int rs = commonDAO.modify("list.updateStampSeal", stempListVO);

		ResultVO result = new ResultVO();

		if (rs > 0) {
			result.setResultCode("success");
		} else {
			result.setResultCode("fail");
		}
		return result;
	}
	
	/**
	 *  서명인 날인 승인
	 */
	public ResultVO confirmStampSealList(DocNumVO docNumVO, String stampId) throws Exception {
	    ResultVO resultVO = new ResultVO();
	    resultVO.setResultCode("fail");
	    
	    docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(docNumVO.getCompId()));

	    int sealNumber = appcomService.selectListNum(docNumVO);
	    
	    StampListVO stampListVO = new StampListVO();
	    stampListVO.setCompId(docNumVO.getCompId());
	    stampListVO.setStampId(stampId);
	    stampListVO.setSealNumber(sealNumber);
	    stampListVO.setSealProcDate(DateUtil.getCurrentDate());
	    
	    int nRs = commonDAO.modify("list.updateStampSealNumber", stampListVO);
	    
	    if(nRs > 0){
		docNumVO.setNum(sealNumber);
		int docNumCnt =  appcomService.updateListNum(docNumVO);
		
		if(docNumCnt > 0){
		    resultVO.setResultCode("success");
		}
	    }
	    
	    return resultVO;
	}
	
	/**
	 * 날인번호 확인
	 */
	@SuppressWarnings("unchecked")
        public int selectStampSealNumber(String compId, String stampId) throws Exception {
	    int sealNumber = 0;
	    
	    if(!"".equals(stampId)){
		
		 Map<String, String> setMap = new HashMap<String, String>();
		 setMap.put("compId", compId);
		 setMap.put("stampId", stampId);
			
		Map map = (Map) commonDAO.get("list.selectStampSealNumber", setMap);
		
		if (map == null || map.isEmpty()) {
		    return 0;
		} else {
		    Integer num = new Integer("" + map.get("sealnumber"));
		    return num.intValue();
		}
	    }else{
		
		return sealNumber;
	    }
	}
	
	/**
	 * 서명인 날인 대장 삭제
	 * 
	 * sealnumber 가 999999 이면 완전 삭제 
	 * 아니면 delete_yn 의 값을 'Y'로 변경
	 */
	public ResultVO deleteStampSeal(StampListVO stampListVO) throws Exception {
	    ResultVO resultVO = new ResultVO();
	    resultVO.setResultCode("fail");
	    
	    int cnt = 0;
	    
	    if(stampListVO.getSealNumber() == 999999){
		cnt = commonDAO.delete("list.deleteStampSealComplete", stampListVO);
	    }else{
		cnt = commonDAO.modify("list.deleteStampSeal", stampListVO);
	    }
	    
	    if(cnt > 0){
		resultVO.setResultCode("success");
	    }
	    
	    return resultVO;
	    
	}
}
