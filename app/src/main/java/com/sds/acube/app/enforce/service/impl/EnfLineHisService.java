package com.sds.acube.app.enforce.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;
import org.anyframe.datatype.SearchVO;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.enforce.service.IEnfLineHisService;
import com.sds.acube.app.enforce.vo.EnfLineHisVO;


/**
 * Class Name : EnfLineHisService.java <br> Description : 접수경로에 대한 이력을 관리하는 서비스 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.impl.EnfLineHisService.java
 */
@Service("enfLineHisService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnfLineHisService implements IEnfLineHisService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;


    public void update(EnfLineHisVO enfLineHisVO) throws Exception {
	this.commonDAO.modify("",enfLineHisVO);
    }


    public EnfLineHisVO get(String processorId) throws Exception {
//	return this.commonDAO.get(processorId);
	return null;
    }


    public void delete(EnfLineHisVO enfLineHisVO) throws Exception {
	this.commonDAO.delete("", enfLineHisVO);
    }


    public void insert(EnfLineHisVO enfLineHisVO) throws Exception {
	this.commonDAO.insert("",enfLineHisVO);
    }


    public Page getPagingList(SearchVO searchVO) throws Exception {
	return this.commonDAO.getPagingList("",searchVO,1,1);
    }
    
    /**
     * 결재라인 이력정보 조회
     */
    @SuppressWarnings("unchecked")
    public List getList(EnfLineHisVO enfLineHisVO) throws Exception{

	return commonDAO.getList("enforce.enfline.selectEnfLineHisList",enfLineHisVO);
    }
}