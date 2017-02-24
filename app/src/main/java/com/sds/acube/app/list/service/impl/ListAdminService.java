package com.sds.acube.app.list.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;


import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.list.service.IListAdminService;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.relay.vo.RelayAckHisVO;
import com.sds.acube.app.relay.vo.RelayExceptionVO;

/**
 * Class Name  : ListAdminService.java <br> Description : (관리자)문서전체, 서명인날인목록, 직인날인목록, 일상감사일지, 연계처리결과, 문서관리연계 관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListDraftService.java
 */

@SuppressWarnings("serial")
@Service("listAdminService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListAdminService extends BaseService implements IListAdminService{
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    public Page listAdminAll(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAll", searchVO, pageIndex);
	    
    }
    
    public Page listAdminAll(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAll", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAdminStampSeal(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminStampSeal", searchVO, pageIndex);
	    
    }
    
    public Page listAdminStampSeal(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminStampSeal", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAdminSeal(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminSeal", searchVO, pageIndex);
	    
    }
    
    public Page listAdminSeal(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminSeal", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAdminAudit(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAudit", searchVO, pageIndex);
	    
    }
    
    public Page listAdminAudit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAudit", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listBizResult(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listBizResult", searchVO, pageIndex);
	    
    }
    
    public Page listBizResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listBizResult", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<BizProcVO> listBizResultDoc(SearchVO searchVO) throws Exception {
	    return (List<BizProcVO>)this.commonDAO.getList("list.listBizResultDoc", searchVO);
	    
    }
    
    public BizProcVO bizResultXmlDoc(BizProcVO bizProcVO) throws Exception {
	return (BizProcVO)this.commonDAO.get("list.bizResultXmlDoc", bizProcVO);
    }
    
    public Page listAdminDocmgrResult(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminDocmgrResult", searchVO, pageIndex);
	    
    }
    
    public Page listAdminDocmgrResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminDocmgrResult", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<QueueToDocmgrVO> listAdminDocmgrResultDoc(SearchVO searchVO) throws Exception {
	return (List<QueueToDocmgrVO>)this.commonDAO.getList("list.listAdminDocmgrResultDoc", searchVO);
    }
    
    public Page listAdminAccHis(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAccHis", searchVO, pageIndex);
	    
    }
    
    public Page listAdminAccHis(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAdminAccHis", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAdminReceiveWait(SearchVO searchVO, int pageIndex) throws Exception {
	return this.commonDAO.getPagingList("list.listAdminReceiveWait", searchVO, pageIndex);

    }

    public Page listAdminReceiveWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	return this.commonDAO.getPagingList("list.listAdminReceiveWait", searchVO, pageIndex, pageSize);

    }
    
    public Page listAdminRelayResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
    	return this.commonDAO.getPagingList("list.listAdminRelayResult", searchVO, pageIndex);
    }
    
    public RelayExceptionVO getAdminRelayResultDetail(RelayExceptionVO relayExceptionVO) throws Exception {
    	return (RelayExceptionVO) this.commonDAO.get("list.getAdminRelayResultDetail", relayExceptionVO);
    }
    
    public Page listAdminRelayAckResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
    	return this.commonDAO.getPagingList("list.listAdminRelayAckResult", searchVO, pageIndex);
    }
    
    @SuppressWarnings("unchecked")
	public List<RelayAckHisVO> getAdminRelayAckResultDetail(RelayAckHisVO relayAckHisVO) throws Exception {
    	return this.commonDAO.getList("list.getAdminRelayAckResultDetail", relayAckHisVO);
    }
    
    @SuppressWarnings("unchecked")
	public List<FileVO> getRelayAttachFile(RelayExceptionVO relayExceptionVO) throws Exception {
    	return this.commonDAO.getList("list.getAdminRelayAttachFile", relayExceptionVO);
    }
    
    public RelayAckHisVO relayResultXmlDoc(RelayAckHisVO relayAckHisVO) throws Exception {
	return (RelayAckHisVO) this.commonDAO.get("list.relayResultXmlDoc", relayAckHisVO);
    }
    
    public Page listAdminDistributionWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
    	return this.commonDAO.getPagingList("list.listAdminDistributionWait", searchVO, pageIndex, pageSize);
    }
}
