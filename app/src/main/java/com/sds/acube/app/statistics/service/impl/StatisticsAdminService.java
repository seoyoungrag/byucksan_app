package com.sds.acube.app.statistics.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.statistics.vo.SearchVO;
import com.sds.acube.app.statistics.vo.StatisticsVO;
import com.sds.acube.app.statistics.service.IStatisticsAdminService;
import org.anyframe.pagination.Page;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;

/**
 * Class Name  : StatisticsAdminService.java <br> Description : (관리자)통계 관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 7. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.StatisticsAdminService.java
 */

@Service("statisticsAdminService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class StatisticsAdminService extends BaseService implements IStatisticsAdminService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> approvalRoleStatistics(SearchVO searchVO) throws Exception {
	
	String callXmlUrl = "";
	
	if("Y".equals(searchVO.getDeptYn())) {
	    callXmlUrl = "statistics.approvalRoleStatisticsDept";
	}else{
	    callXmlUrl = "statistics.approvalRoleStatisticsPerson";
	}
	    return (List<StatisticsVO>)commonDAO.getList(callXmlUrl, searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> docKindStatistics(SearchVO searchVO) throws Exception {
	
	String callXmlUrl = "";
	
	if("Y".equals(searchVO.getDeptYn())) {
	    callXmlUrl = "statistics.docKindStatisticsDept";
	}else{
	    callXmlUrl = "statistics.docKindStatisticsPerson";
	}
	    return (List<StatisticsVO>)commonDAO.getList(callXmlUrl, searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> approvalCntStatistics(SearchVO searchVO) throws Exception {
	 return (List<StatisticsVO>)commonDAO.getList("statistics.approvalCntStatistics", searchVO);	
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> sendCntStatistics(SearchVO searchVO) throws Exception {
	 return (List<StatisticsVO>)commonDAO.getList("statistics.sendCntStatistics", searchVO);	
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> receiveCntStatistics(SearchVO searchVO) throws Exception {
	 return (List<StatisticsVO>)commonDAO.getList("statistics.receiveCntStatistics", searchVO);	
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> registCntStatistics(SearchVO searchVO) throws Exception {
	 return (List<StatisticsVO>)commonDAO.getList("statistics.registCntStatistics", searchVO);	
    }
    
    /** added at 2012.05.29 by emptyColor */
    @SuppressWarnings("unchecked")
	    public List<StatisticsVO> receiptStatusStatistics(SearchVO searchVO) throws Exception {
		return (List<StatisticsVO>)commonDAO.getList("statistics.receiptStatusStatistics", searchVO);
    }

    public Page receiptStatusStatistics(SearchVO searchVO, int cPage) throws Exception {
    	return commonDAO.getPagingList("statistics.receiptStatusStatistics", searchVO, cPage);
    }

    public Page receiptStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception {
    	return commonDAO.getPagingList("statistics.receiptStatusStatistics", searchVO, cPage, pageSize);
    }

    @SuppressWarnings("unchecked")
    public List<EnfDocVO> listReceiptStatus(SearchVO searchVO) throws Exception {
		if ("RECV".equals(searchVO.getSearchDocType())) {
		    return (List<EnfDocVO>)commonDAO.getList("statistics.listReceiptStatusRecv", searchVO);
		} else {
		    return (List<EnfDocVO>)commonDAO.getList("statistics.listReceiptStatusLine", searchVO);
		}
    }

    public Page listReceiptStatus(SearchVO searchVO, int cPage) throws Exception {
		if ("RECV".equals(searchVO.getSearchDocType())) {
		    return commonDAO.getPagingList("statistics.listReceiptStatusRecv", searchVO, cPage);
		} else {
		    return commonDAO.getPagingList("statistics.listReceiptStatusLine", searchVO, cPage);
		}
    }

    public Page listReceiptStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception {
		if ("RECV".equals(searchVO.getSearchDocType())) {
		    return commonDAO.getPagingList("statistics.listReceiptStatusRecv", searchVO, cPage, pageSize);
		} else {
		    return commonDAO.getPagingList("statistics.listReceiptStatusLine", searchVO, cPage, pageSize);
		}
    }

    @SuppressWarnings("unchecked")
    public List<StatisticsVO> processStatusStatistics(SearchVO searchVO) throws Exception {
    	return (List<StatisticsVO>)commonDAO.getList("statistics.processStatusStatistics", searchVO);
    }
  
    public Page processStatusStatistics(SearchVO searchVO, int cPage) throws Exception {
    	return commonDAO.getPagingList("statistics.processStatusStatistics", searchVO, cPage);
    }
  
    public Page processStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception {
    	return commonDAO.getPagingList("statistics.processStatusStatistics", searchVO, cPage, pageSize);
    }
  
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listProcessStatus(SearchVO searchVO) throws Exception {
    	return (List<AppDocVO>)commonDAO.getList("statistics.listProcessStatus", searchVO);
    }

    public Page listProcessStatus(SearchVO searchVO, int cPage) throws Exception {
    	return commonDAO.getPagingList("statistics.listProcessStatus", searchVO, cPage);
    }

    public Page listProcessStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception {
    	return commonDAO.getPagingList("statistics.listProcessStatus", searchVO, cPage, pageSize);
    }
    
    @SuppressWarnings("unchecked")
    public List<StatisticsVO> sendStatusStatistics(SearchVO searchVO) throws Exception {
	return (List<StatisticsVO>)commonDAO.getList("statistics.sendStatusStatistics", searchVO);

    }

    public Page sendStatusStatistics(SearchVO searchVO, int cPage) throws Exception {
	return commonDAO.getPagingList("statistics.sendStatusStatistics", searchVO, cPage);

    }

    public Page sendStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception {
	return commonDAO.getPagingList("statistics.sendStatusStatistics", searchVO, cPage, pageSize);

    }

    @SuppressWarnings("unchecked")
    public List<EnfDocVO> listSendStatus(SearchVO searchVO) throws Exception {
	 return (List<EnfDocVO>)commonDAO.getList("statistics.listSendStatus", searchVO);

    }

    public Page listSendStatus(SearchVO searchVO, int cPage) throws Exception {
	return commonDAO.getPagingList("statistics.listSendStatus", searchVO, cPage);

    }

    public Page listSendStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception {
	return commonDAO.getPagingList("statistics.listSendStatus", searchVO, cPage, pageSize);

    }
}
