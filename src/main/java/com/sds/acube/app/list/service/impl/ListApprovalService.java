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
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListApprovalService.java <br> Description : 결재대기함, 진행문서함  관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListApprovalService.java
 */

@Service("listApprovalService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListApprovalService extends BaseService implements IListApprovalService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    public Page listApprovalWait(SearchVO searchVO, int pageIndex) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalWait", searchVO, pageIndex);
	    
    }
    
    public Page listApprovalWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalWait", searchVO, pageIndex, pageSize);
	    
    }   

    public Page listApprovalReject(SearchVO searchVO, int pageIndex) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalReject", searchVO, pageIndex);
	    
    }
    
    public Page listApprovalReject(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalReject", searchVO, pageIndex, pageSize);
    }
    
    public Page listApprovalDelete(SearchVO searchVO, int pageIndex) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalDelete", searchVO, pageIndex);
	    
    }
    
    public Page listApprovalDelete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return commonDAO.getPagingList("list.listApprovalDelete", searchVO, pageIndex, pageSize);
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listApprovalWait(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listApprovalWaitToList", searchVO);
	    
    }
    
    public Page listProgressDoc(SearchVO searchVO, int pageIndex) throws Exception {
	    return commonDAO.getPagingList("list.listProgressDoc", searchVO, pageIndex);
	    
    }
    
    public Page listProgressDoc(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return commonDAO.getPagingList("list.listProgressDoc", searchVO, pageIndex, pageSize);
    }
    
    // 결재 경로상의 사용자 중 결재 전 사용자가 결재진행함에서 결재문서를 조회할 수 없도록 수정
    public Page listProgressDocOption383(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return commonDAO.getPagingList("list.listProgressDocOption383", searchVO, pageIndex, pageSize);
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listProgressDoc(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listProgressDocToList", searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listApprovalWaitCount(SearchVO searchVO) throws Exception {
	Map map = (Map) commonDAO.get("list.approvalWaitDocCount", searchVO);
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    @SuppressWarnings("unchecked")
    public int listProgressDocCount(SearchVO searchVO) throws Exception {
	Map map = (Map) commonDAO.get("list.progressDocCount", searchVO);
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listAssistantDoc(SearchVO searchVO, int pageIndex) throws Exception {
	return commonDAO.getPagingList("list.listAssistantDoc", searchVO, pageIndex);

    }

    public Page listAssistantDoc(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	return commonDAO.getPagingList("list.listAssistantDoc", searchVO, pageIndex, pageSize);

    } 
}
