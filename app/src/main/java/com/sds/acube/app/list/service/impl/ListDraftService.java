package com.sds.acube.app.list.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;


import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.list.service.IListDraftService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListDraftService.java <br> Description : 임시저장함, 연계기안함 관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListDraftService.java
 */

@Service("listDraftService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListDraftService extends BaseService implements IListDraftService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    public Page listTempApproval(SearchVO searchVO, int pageIndex) throws Exception {
	return this.commonDAO.getPagingList("list.listTempApproval", searchVO, pageIndex);

    }

    public Page listTempApproval(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	return this.commonDAO.getPagingList("list.listTempApproval", searchVO, pageIndex, pageSize);

    }

    public Page listBizDraft(SearchVO searchVO, int pageIndex) throws Exception {
	return this.commonDAO.getPagingList("list.listBizDraft", searchVO, pageIndex);

    }

    public Page listBizDraft(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	return this.commonDAO.getPagingList("list.listBizDraft", searchVO, pageIndex, pageSize);

    }
  
}
