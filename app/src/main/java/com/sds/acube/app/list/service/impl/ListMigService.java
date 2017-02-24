/**
 * 
 */
package com.sds.acube.app.list.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.list.service.IListMigService;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : ListMisgService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2016. 1. 23. <br>
 *  수 정  자 : 서영락  <br>
 *  수정내용 :  <br>
 * 
 *  @author  서영락 
 *  @since 2016. 1. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.impl.ListMigService.java
 */

@Service("listMigService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListMigService implements IListMigService {

    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

	@Override
	public List<AppDocVO> listMigDocRegist(SearchVO searchVO) throws Exception {
		return (List<AppDocVO>) this.commonDAO.getList("list.migDocRegist", searchVO);
	    
	}
	@Override
	public Page listMigDocRegist(SearchVO searchVO, int pageIndex) throws Exception {
		return this.commonDAO.getPagingList("list.migDocRegist", searchVO, pageIndex);
	    
	}
	
	@Override
	public Page listMigDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
		return this.commonDAO.getPagingList("list.migDocRegist", searchVO, pageIndex, pageSize);
	    
	}

	@Override
	public int listMigDocRegistCount(SearchVO searchVO) throws Exception {
		Map map = (Map) this.commonDAO.get("list.migDocRegistCount", searchVO);   
		Integer num = new Integer("" + map.get("CNT"));
		return num.intValue();
		
	}

	@Override
	public List<AppDocVO> listMigDocDist(SearchVO searchVO) throws Exception {
		return (List<AppDocVO>) this.commonDAO.getList("list.migDocDist", searchVO);
	}
	
	@Override
	public Page listMigDocDist(SearchVO searchVO, int pageIndex) throws Exception {
		return this.commonDAO.getPagingList("list.migDocDist", searchVO, pageIndex);
	}

	@Override
	public Page listMigDocDist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
		return this.commonDAO.getPagingList("list.migDocDist", searchVO, pageIndex, pageSize);
	}


	@Override
	public int listMigDocDistCount(SearchVO searchVO) throws Exception {
		Map map = (Map) this.commonDAO.get("list.migDocDistCount", searchVO);   
		Integer num = new Integer("" + map.get("CNT"));
		return num.intValue();
	}

}
