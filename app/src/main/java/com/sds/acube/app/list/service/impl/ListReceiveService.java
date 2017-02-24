package com.sds.acube.app.list.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;


import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListSendService.java <br> Description : 접수대기함, 배부대기함 관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 4. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListSendService.java
 */

@Service("listReceiveService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListReceiveService extends BaseService implements IListReceiveService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    public Page listDistributionWait(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionWait", searchVO, pageIndex);
	    
    }
    
    public Page listDistributionWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionWait", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<EnfDocVO>listDistributionWait(SearchVO searchVO) throws Exception {
	return this.commonDAO.getList("list.listDistributionWaitToList", searchVO);
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO>listDistributionWaitPortlet(SearchVO searchVO) throws Exception {
	return this.commonDAO.getList("list.listDistributionWaitToListPortlet", searchVO);
    }
    
    @SuppressWarnings("unchecked")
    public int listDistributionWaitCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.listDistributionWaitCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listDistributionRemind(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionRemind", searchVO, pageIndex);
	    
    }
    
    public Page listDistributionRemind(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionRemind", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listReceiveWait(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listReceiveWait", searchVO, pageIndex);
	    
    }
    
    public Page listReceiveWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listReceiveWait", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO>listReceiveWait(SearchVO searchVO) throws Exception {
	return this.commonDAO.getList("list.listReceiveWaitToList", searchVO);
    }
    
    
    @SuppressWarnings("unchecked")
    public int listReceiveWaitCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.receiveWaitCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
  
}
