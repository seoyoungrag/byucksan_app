/**
 * 
 */
package com.sds.acube.app.env.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.query.QueryServiceException;
import org.springframework.stereotype.Service;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.env.service.IEnvBizSystemService;
import com.sds.acube.app.env.vo.BizSystemVO;


/**
 * Class Name : EnvBizSystemService.java <br> Description : 업무시스템관리 서비스 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 13. <br> 수 정 자 : chamchi <br> 수정내용 : <br>
 * @author  chamchi
 * @since  2011. 5. 13.
 * @version  1.0
 * @see  com.sds.acube.app.env.service.impl.EnvBizSystemService.java
 */

@Service("envBizSystemService")
public class EnvBizSystemService extends BaseService implements IEnvBizSystemService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;



    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#insertEvnBizSystem(java
     * .util.Map)
     */
    public void insertEvnBizSystem(Map inputMap) throws QueryServiceException, Exception {

	inputMap.put("registDate",DateUtil.getCurrentDate());
	commonDAO.insertMap("env.system.insertBizSystem", inputMap);

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#listEvnBizSystem(java
     * .util.Map)
     */
    public List listEvnBizSystem(BizSystemVO bizSystemVO) throws Exception {

	List list = commonDAO.getList("env.system.listBizSystem", bizSystemVO);

	return list;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#selectEvnBizSystem(java
     * .util.Map)
     */
    public BizSystemVO selectEvnBizSystem(Map inputMap) throws Exception {
	BizSystemVO bizVO = new BizSystemVO();
	
	bizVO.setCompId((String)inputMap.get("compId"));
	bizVO.setBizSystemCode((String)inputMap.get("bizSystemCode"));
	bizVO.setBizTypeCode((String)inputMap.get("bizTypeCode"));
	
	return selectEvnBizSystem(bizVO);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#selectEvnBizSystem(java
     * .util.Map)
     */
    public BizSystemVO selectEvnBizSystem(BizSystemVO bizSystemVO) throws Exception {
	
	return (BizSystemVO) commonDAO.get("env.selectBizSystemName", bizSystemVO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#updateEvnBizSystem(java
     * .util.Map)
     */
    public void updateEvnBizSystem(Map inputMap) throws Exception {
	commonDAO.modifyMap("env.system.updateBizSystem", inputMap);

    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.env.service.IEnvBizSystemService#updateEvnBizSystem(java
     * .util.Map)
     */
    public void deleteEvnBizSystem(Map inputMap) throws Exception {
	commonDAO.deleteMap("env.system.deleteBizSystem", inputMap);

    }
}
