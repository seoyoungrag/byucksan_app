package com.sds.acube.app.legacyTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.legacyTest.service.ILegacyTestService;
import com.sds.acube.app.legacyTest.vo.LegacyTestVO;

/**
 * Class Name  : LegacyTestService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 :<br> 수 정  자 :  <br> 수정내용 :  <br>
 * @author   jth8172 
 * @since  2012. 5. 21.
 * @version  1.0 
 * @see  com.sds.acube.app.legacyTest.service.impl.LegacyTestService.java
 */
 
@SuppressWarnings("serial")
@Service("LegacyTestService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class LegacyTestService extends BaseService implements ILegacyTestService {
 
    /**
	 */
    @Autowired 
    private ICommonDAO commonDAO;
    
    // 연계목록조회
    public Page listLegacyTest(LegacyTestVO legacyTestVO, int page) throws Exception{
  	   	return (Page) this.commonDAO.getPagingList("legacy.test.tripList", legacyTestVO, page);
    }

    // 연계등록
    public int insertLegacyTest(LegacyTestVO legacyTestVO) throws Exception {
    	return this.commonDAO.insert("legacy.test.insertTrip", legacyTestVO);
    }

    // 연계삭제
    public int deleteLegacyTest(LegacyTestVO legacyTestVO) throws Exception {
    	return this.commonDAO.insert("legacy.test.deleteTrip", legacyTestVO);
    }

    // 연계조회
    public LegacyTestVO viewLegacyTest(LegacyTestVO legacyTestVO) throws Exception{
    	return (LegacyTestVO) this.commonDAO.get("legacy.test.selectTrip", legacyTestVO);
    }
    
    
    // 연계Ack 반영
    public int updateLegacyAckTest(LegacyTestVO legacyTestVO) throws Exception{
    	return this.commonDAO.modify("legacy.test.updateTrip", legacyTestVO);
    }

 
}
