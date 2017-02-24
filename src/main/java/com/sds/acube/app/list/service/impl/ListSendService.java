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
import com.sds.acube.app.list.service.IListSendService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListSendService.java <br> Description : 발송대기함, 발송심사함 관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 4. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListSendService.java
 */

@Service("listSendService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListSendService extends BaseService implements IListSendService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    public Page listSendWait(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listSendWait", searchVO, pageIndex);
	    
    }
    
    public Page listSendWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listSendWait", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listSendWait(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listSendWaitToList", searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listSendWaitCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.sendWaitCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listSendJudge(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listSendJudge", searchVO, pageIndex);
	    
    }
    
    public Page listSendJudge(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listSendJudge", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listSendJudgeCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.sendJudgeCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
  
}
