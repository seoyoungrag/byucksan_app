package com.sds.acube.app.enforce.service;

import java.util.List;

import org.anyframe.pagination.Page;
import org.anyframe.datatype.SearchVO;

import com.sds.acube.app.enforce.vo.EnfLineHisVO;

/**
 * 
 *  Class Name  : IEnfLineHisService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : Mar 18, 2011 <br>
 *  수 정  자 : 윤동원  <br>
 *  수정내용 :  <br>
 * 
 *  @author  윤동원 
 *  @since Mar 18, 2011
 *  @version 1.0 
 *  @see  com.kdb.portal.enforce.service.IEnfLineHisService.java
 */
public interface IEnfLineHisService{

  	void insert(EnfLineHisVO enfLineHisVO) throws Exception;
	
	void delete(EnfLineHisVO enfLineHisVO) throws Exception;
		
	void update(EnfLineHisVO enfLineHisVO) throws Exception;
	
  	EnfLineHisVO get(String processorId) throws Exception;
	
	Page getPagingList(SearchVO searchVO) throws Exception;  
	
	List getList(EnfLineHisVO enfLineHisVO) throws Exception;
}