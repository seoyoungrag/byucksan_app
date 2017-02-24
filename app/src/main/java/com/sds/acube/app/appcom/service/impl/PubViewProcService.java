package com.sds.acube.app.appcom.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IPubViewProcService;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.DateUtil;


/**
 * Class Name : EnfLineService.java <br> Description : 생산/접수문서의 공람을  관리하는 서비스 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.service.EnfLineService.java
 */
@SuppressWarnings("serial")
@Service("pubViewService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class PubViewProcService extends BaseService implements IPubViewProcService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;

    /**
     * 공람처리
     */
    public void processPubView(PubReaderVO pubReaderVO) throws Exception {

	/*
	 * 1.해당 문서의 공람처리일자수정
	 */

	// 날자포맷
	pubReaderVO.setPubReadDate(DateUtil.getCurrentDate());// 처리일자
	this.commonDao.modify("appcom.updatePubView", pubReaderVO);

    }

}