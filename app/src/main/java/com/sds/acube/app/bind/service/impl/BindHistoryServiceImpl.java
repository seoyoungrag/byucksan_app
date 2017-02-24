package com.sds.acube.app.bind.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.service.BindHistoryService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;


/**
 * Class Name : BindHistoryServiceImpl.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.service.impl.BindHistoryServiceImpl.java
 */

@Service("bindHistoryService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BindHistoryServiceImpl extends BaseService implements BindHistoryService {

    private static final long serialVersionUID = 1211245176110782181L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    @SuppressWarnings("unchecked")
    public List<BindVO> getList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("bind.history.list", param);
    }


    public int insert(BindVO bindVO) throws Exception {
	return this.commonDao.insert("bind.history.insert", bindVO);
    }


    public BindVO get(BindVO bindVO) throws Exception {
	return (BindVO) commonDao.get("bind.history.select", bindVO);
    }

}
