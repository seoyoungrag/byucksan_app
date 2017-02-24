package com.sds.acube.app.common.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;

/**
 * Class Name  : CommonService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 25. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.common.service.impl.CommonService.java
 */

@Service("commonService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class CommonService implements ICommonService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    public int insertQueueToDocmgr(QueueToDocmgrVO queueToDocmgrVO) throws Exception {

	return commonDAO.insert("common.insertQueueToDocmgr", queueToDocmgrVO);
    }
    
    public int insertQueue(QueueVO queueVO) throws Exception {

	return commonDAO.insert("common.insertQueue", queueVO);
    }
}
