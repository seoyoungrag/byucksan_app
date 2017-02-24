/**
 * 
 */
package com.sds.acube.app.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.vo.DocHisVO;

/**
 * Class Name  : LogService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 8. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 4. 8.
 * @version  1.0 
 * @see  com.sds.acube.app.common.service.impl.LogService.java
 */


@Service("logService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class LogService extends BaseService implements ILogService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.ILogService#insertDocHis(java.lang.String[][])
     */
    public int insertDocHis(DocHisVO docHisVO) throws Exception {
	return commonDAO.insert("common.insertDocHis", docHisVO);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.ILogService#updateDocHis(java.util.Map[][])
     */
    public int updateDocHis(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("common.updateDocHis", map);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.ILogService#selectDocHis(java.util.Map[][])
     */
    @SuppressWarnings("unchecked")
    public List<DocHisVO> listDocHis(Map<String, String> map) throws Exception {
	return commonDAO.getListMap("common.listDocHis", map);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.ILogService#selectDocHis(java.util.Map[][])
     */
    @SuppressWarnings("unchecked")
    public List<DocHisVO> selectDocHis(DocHisVO docHisVO) throws Exception {
	return commonDAO.getList("common.selectDocHis", docHisVO);
    }

}
