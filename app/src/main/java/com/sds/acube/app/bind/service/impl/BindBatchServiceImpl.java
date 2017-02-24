/**
 * 
 */
package com.sds.acube.app.bind.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.bind.service.BindBatchService;
import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;


/**
 * Class Name : BindBatchService.java <br> Description : 편철 일괄등록 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.service.BindBatchService.java
 */

@Service("bindBatchService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BindBatchServiceImpl extends BaseService implements BindBatchService, BindConstants {

    private static final long serialVersionUID = 8883554157982270539L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    @SuppressWarnings("unchecked")
    public List<BatchVO> getList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("bind.batch.list", param);
    }


    public int execute(Map<String, String> param, String batchType) throws Exception {
	String queryId = null;
	if (DEFAULT.equals(batchType)) {
	    queryId = "bind.batch.execute";
	} else if (CUSTORM.equals(batchType)) {
	    queryId = "bind.batch.execute2";
	} else {
	    throw new IllegalArgumentException("Unknown batch type found!");
	}

	return this.commonDao.insert(queryId, param);
    }


    public int insert(BatchVO batchVO) throws Exception {
	return this.commonDao.insert("bind.batch.insert", batchVO);
    }


    public BatchVO get(String compId, String year) throws Exception {
	BatchVO batchVO = new BatchVO();
	batchVO.setCompId(compId);
	batchVO.setYear(year);
	return (BatchVO) this.commonDao.get("bind.batch.select", batchVO);
    }


    public int remove(Map<String, String> params) throws Exception {
	return this.commonDao.delete("bind.batch.delete", params);
    }


    public int removeBatch(Map<String, String> params) throws Exception {
	return this.commonDao.delete("bind.batch.delete.log", params);
    }

}
