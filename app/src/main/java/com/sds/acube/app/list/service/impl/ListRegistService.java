package com.sds.acube.app.list.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;


import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.PeriodVO;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListRegistService.java <br> Description :문서등록대장, 문서배부대장, 미등록문서대장, 서명인날인대장, 직인날인대장, 일일감사대장, 일상감사일지, 감사직인날인대장  관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListRegistService.java
 */

@Service("listRegistService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListRegistService extends BaseService implements IListRegistService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    
    @SuppressWarnings("unchecked")
    public List<CategoryVO> listCategory(SearchVO searchVO) throws Exception {
	    return (List<CategoryVO>) this.commonDAO.getList("list.listCategory", searchVO);
	    
    }
    
    public Page listDocRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDocRegist", searchVO, pageIndex);
	    
    }
    
    public Page listDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDocRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listDocRegist(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listDocRegistToList", searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listDocRegistCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.docRegistCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    } 
    
    public Page listDistributionRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionRegist", searchVO, pageIndex);
	    
    }
    
    public Page listDistributionRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDistributionRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listRowRankDocRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listRowRankDocRegist", searchVO, pageIndex);
	    
    }
    
    public Page listRelationDocRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listRelationDocRegist", searchVO, pageIndex);
	    
    }
    
    public Page listNoDocRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listNoDocRegist", searchVO, pageIndex);
	    
    }
    
    public Page listNoDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listNoDocRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listStampSealRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listStampSealRegist", searchVO, pageIndex);
	    
    }
    
    public Page listStampSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listStampSealRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listSealRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listSealRegist", searchVO, pageIndex);
	    
    }
    
    public Page listSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listSealRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listDailyAuditRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDailyAuditRegist", searchVO, pageIndex);
	    
    }
    
    public Page listDailyAuditRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDailyAuditRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listDailyAudit(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDailyAudit", searchVO, pageIndex);
	    
    }
    
    public Page listDailyAudit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDailyAudit", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAuditSealRegist(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAuditSealRegist", searchVO, pageIndex);
	    
    }
    
    public Page listAuditSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listAuditSealRegist", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listAuditSealRegistDetailSearch(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAuditSealRegistDetailSearch", searchVO, pageIndex);
	    
    }
    
    /**
     * 년도/회기 내용을 가져온다.
     */
    public HashMap<String, Object> returnRegistDate(String compId, String searchCurRange, String startDate, String endDate) throws Exception {
	 HashMap<String, Object> map	= new HashMap<String, Object>();
	 List<SearchVO> registList	= new ArrayList<SearchVO>();
	 
	 // 선택된 년도/회기가 있을때만 해당 년도/회기의 내용을 가져온다.
	 if(!"".equals(searchCurRange)) {
	     PeriodVO priodVO = envOptionAPIService.getPeriod(compId, searchCurRange);
	     
	     startDate 	= priodVO.getStartDate();
	     endDate 	= priodVO.getEndDate();
	 }
	 
	 //유효한 년도/회기 정보를 가져온다.
	 List<PeriodVO> priodVOs = (List<PeriodVO>)envOptionAPIService.listPeriod(compId);
	 int loopSize = priodVOs.size();
	 
	 for(int i=0; i<loopSize; i++){
	     PeriodVO priodVO = priodVOs.get(i);
	     
	     SearchVO registVO = new SearchVO();

	     registVO.setRegistRange(priodVO.getPeriodId());
	     registList.add(registVO);
	 }
	 
	 map.put("startDate", startDate);
	 map.put("endDate", endDate);
	 map.put("registList", registList);
	 
	 return map;
   }
  
}
