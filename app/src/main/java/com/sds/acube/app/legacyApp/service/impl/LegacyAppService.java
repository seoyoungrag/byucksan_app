package com.sds.acube.app.legacyApp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.anyframe.pagination.Page;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.legacyApp.service.ILegacyAppService;
import com.sds.acube.app.legacyApp.vo.LegacyAppVO;
import com.sds.acube.app.legacyApp.vo.LegacyEaiVO;

/**
 * Class Name  : LegacyAppService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 :<br> 수 정  자 :  <br> 수정내용 :  <br>
 * @author   jth8172 
 * @since  2012. 5. 21.
 * @version  1.0 
 * @see  com.sds.acube.app.legacyApp.service.impl.LegacyAppService.java
 */
 
@SuppressWarnings("serial")
@Service("LegacyAppService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class LegacyAppService extends BaseService implements ILegacyAppService {
 
    /**
	 */
    @Autowired 
    private ICommonDAO commonDAO;
    
    // 연계목록조회
    public Page listLegacyApp(LegacyAppVO legacyAppVO, int page) throws Exception{
  	   	return (Page) this.commonDAO.getPagingList("legacy.test.tripList", legacyAppVO, page);
    }

    // 연계등록
    public int insertLegacyApp(LegacyAppVO legacyAppVO) throws Exception {
    	return this.commonDAO.insert("legacy.test.insertTrip", legacyAppVO);
    }

    // 연계삭제
    public int deleteLegacyApp(LegacyAppVO legacyAppVO) throws Exception {
    	return this.commonDAO.insert("legacy.test.deleteTrip", legacyAppVO);
    }

    // 연계조회
    public LegacyAppVO viewLegacyApp(LegacyAppVO legacyAppVO) throws Exception{
    	return (LegacyAppVO) this.commonDAO.get("legacy.test.selectTrip", legacyAppVO);
    }
    
    // 연계Ack 반영
    public int updateLegacyAckApp(LegacyAppVO legacyAppVO) throws Exception{
    	return this.commonDAO.modify("legacy.test.updateTrip", legacyAppVO);
    }
    
    // 연계목록조회
    public List<LegacyEaiVO> listLegacyEaiProc(Map<String, String> map) throws Exception{
    	LegacyEaiVO legacyEaiVO = new LegacyEaiVO();
    	legacyEaiVO.setEaiCnntDsCd(map.get("eaiCnntDsCd"));
    	legacyEaiVO.setEaiCnntSsCd(map.get("eaiCnntSsCd"));
    	//SNC_NO 코티스가 아닌 경우, 타지 않도록 함.. by jkkim
    	if(map.get("businessCode").equals("COT")){
	    	if(!CommonUtil.isNullOrEmpty(map.get("sncNo"))){
	    		legacyEaiVO.setSncNo(Integer.parseInt(map.get("sncNo")));
	    	}
    	}
    	legacyEaiVO.setBusinessCode(map.get("businessCode"));
    	return this.commonDAO.getList("legacy.eai.procList", legacyEaiVO);
    }
    
    // 연계 처리해야할 결재일련번호 리스트
    public List<LegacyEaiVO> listLegacyEaiSncNo(Map<String, String> map) throws Exception{
    	return this.commonDAO.getListMap("legacy.eai.SncNolist",map);
    }
    
    // 연계 파일 목로 조회
    public List<LegacyEaiVO> listLegacyEaiFileInfo(Map<String, String> map) throws Exception{
  	   	return this.commonDAO.getListMap("legacy.eai.procFileInfoList", map);
    }
    // EAI 상태값 업데이트
    public int updateEaiIfStatus(Map<String, String> map) throws Exception{
    	LegacyEaiVO legacyEaiVO = new LegacyEaiVO();
    	legacyEaiVO.setEaiCnntNo(map.get("eaiCnntNo"));
    	legacyEaiVO.setEaiCnntSsCd(map.get("eaiCnntSsCd"));
    	legacyEaiVO.setBusinessCode(map.get("businessCode"));
    	return this.commonDAO.modify("legacy.eai.updateEaiIfStatus", legacyEaiVO);
    }
    // EAI 첨부 상태값 업데이트
    public int updateEaiAttIfStatus(Map<String, String> map) throws Exception{
    	LegacyEaiVO legacyEaiVO = new LegacyEaiVO();
    	logger.debug("eaiNo : " + map.get("eaiNo"));
    	logger.debug("eaiCnntSsCd : " + map.get("eaiCnntSsCd"));
    	logger.debug("businessCode : " + map.get("businessCode"));
    	legacyEaiVO.setEaiNo(map.get("eaiNo"));
    	legacyEaiVO.setEaiCnntSsCd(map.get("eaiCnntSsCd"));
    	legacyEaiVO.setBusinessCode(map.get("businessCode"));
    	return this.commonDAO.modify("legacy.eai.updateEaiAttIfStatus", legacyEaiVO);
    }
    
    // 연계 EAI Ack 등록
    public int insertLegacyEaiAck(LegacyEaiVO legacyEaiVO) throws Exception {
    	return this.commonDAO.insert("legacy.eai.insertLegacyEaiAck", legacyEaiVO);
    }
    
    // 연계조회
    public LegacyEaiVO viewLegacyEaiCot(Map<String, String> map) throws Exception{
    	LegacyEaiVO legacyEaiVO = new LegacyEaiVO();
    	legacyEaiVO.setEaiCnntNo(map.get("eaiCnntNo"));
    	return (LegacyEaiVO)this.commonDAO.get("legacy.eai.selectEaiCot", legacyEaiVO);
    }
    
    // 연계 COTIS EAI Ack 등록
    public int insertLegacyCotEaiAck(LegacyEaiVO legacyEaiVO) throws Exception {
    	return this.commonDAO.insert("legacy.eai.insertLegacyCotEaiAck", legacyEaiVO);
    }
    
    // 전자조달 JD 칼럼 정보 조회
    public LegacyEaiVO viewLegacyEaiSup(Map<String, String> map) throws Exception{
    	LegacyEaiVO legacyEaiVO = new LegacyEaiVO();
    	legacyEaiVO.setEaiNo(map.get("eaiNo"));
    	return (LegacyEaiVO)this.commonDAO.get("legacy.eai.selectEaiSup", legacyEaiVO);
    }
    
 
}
