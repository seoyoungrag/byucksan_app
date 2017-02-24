package com.sds.acube.app.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.query.QueryService;
import org.anyframe.query.impl.QueryServiceImpl;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IMemoryService;
import com.sds.acube.app.common.util.AppCode;
import com.sds.acube.app.common.util.AppEnvOption;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.util.MemoryUtil;
import com.sds.acube.app.common.vo.AppCodeVO;
import com.sds.acube.app.env.vo.OptionVO;

/**
 * Class Name  : MemoryService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 25. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.common.service.impl.MemoryService.java
 */

@Service("memoryService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class MemoryService implements IMemoryService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    protected LogWrapper logger = LogWrapper.getLogger("com.sds.acube.app");

    @SuppressWarnings("unchecked")
    public void initializeCode() {
	AppCode appCode = MemoryUtil.getCodeInstance();
	synchronized (appCode) {
	    if (!appCode.getIsLoad()) {			
		try {
		    appCode.clear();

		    if (logger.isDebugEnabled()) {
			logger.debug("# start : Code initialize ############################################ #");
		    }

		    Map<String, String> map = new HashMap<String, String>();
		    map.put("parentId", "ROOT");
		    QueryService queryService = new QueryServiceImpl();
		    if (queryService == null) {
			logger.debug("# commonDAO is Null ############################################ #");
		    } else {		
			List<AppCodeVO> rootCodeVOs = (List<AppCodeVO>) commonDAO.getListMap("common.listAppCode", map);
			int rootCount = rootCodeVOs.size();
			logger.debug("# rootCount : " + rootCount);
			for (int loop = 0; loop < rootCount; loop++) {
			    AppCodeVO rootCodeVO = rootCodeVOs.get(loop);
			    map.put("parentId", rootCodeVO.getCodeId());
			    List<AppCodeVO> appCodeVOs = (List<AppCodeVO>) commonDAO.getListMap("common.listAppCode", map);

			    Map<String, String> codeMap = new HashMap<String, String>();
			    int codeCount = appCodeVOs.size();
			    for (int pos = 0; pos < codeCount; pos++) {
				AppCodeVO appCodeVO = appCodeVOs.get(pos);
				codeMap.put(appCodeVO.getCodeId(), appCodeVO.getCodeValue());
				logger.debug("# CATEGORY : " + rootCodeVO.getCodeId() + ", KEY : " + appCodeVO.getCodeId() + ", VALUE : " + appCodeVO.getCodeValue());
				appCode.setProperty(appCodeVO.getCodeId(), appCodeVO.getCodeValue(), rootCodeVO.getCodeId());
			    }
			}

			appCode.setIsLoad(true);
		    }

		    if (logger.isDebugEnabled()) {
			logger.debug("# end : Code initialize ############################################ #");
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
	    }
	}
    }


    /**
     * appCode를 reload 한다
     */
    public void reloadCode() {
	try {
	    AppCode appCode = MemoryUtil.getCodeInstance();
	    appCode.setIsLoad(false);
	    initializeCode();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
    }
    
    
    
    @SuppressWarnings("unchecked")
    public void initializeOption() {
	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	synchronized (appEnvOption) {
	    if (!appEnvOption.getIsLoad()) {			
		try {
		    appEnvOption.clear();

		    if (logger.isDebugEnabled()) {
			logger.debug("# start : EnvOption initialize ############################################ #");
		    }

		    String compId = "";
		    QueryService queryService = new QueryServiceImpl();
		    if (queryService == null) {
			logger.debug("# commonDAO is Null ############################################ #");
		    } else {
			Map<String, String> map = new HashMap<String, String>();
			List<OptionVO> optionVOs = (List<OptionVO>) commonDAO.getListMap("common.listAppEnvOption", map);
			int optionCount = optionVOs.size();
			logger.debug("# optionCount : " + optionCount);
			for (int loop = 0; loop < optionCount; loop++) {
			    OptionVO optionVO = optionVOs.get(loop);

			    if ("".equals(compId) || !compId.equals(optionVO.getCompId())) {
				compId = optionVO.getCompId();
				Map<String, OptionVO> compMap = new HashMap<String, OptionVO>();
				appEnvOption.setOptions(compId, compMap);
			    }
			    (appEnvOption.getOptions(compId)).put(optionVO.getOptionId(), optionVO);			    
			    logger.debug("# COMP_ID : " + optionVO.getCompId() + ", OPTION_ID : " + optionVO.getOptionId() + ", USE_YN : " + optionVO.getUseYn() + ", OPTION_VALUE : " + optionVO.getOptionValue());
			}

			appEnvOption.setIsLoad(true);
		    }

		    if (logger.isDebugEnabled()) {
			logger.debug("# end : EnvOption initialize ############################################ #");
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
	    }
	}
    }


    /**
     * appCode를 reload 한다
     */
    public void reloadOption() {
	try {
	    AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	    appEnvOption.setIsLoad(false);
	    initializeOption();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
    }
    

    /**
     * appCode를 reload 한다
     */
    @SuppressWarnings("unchecked")
    public void reloadOption(String compId) {
	try {
	    if (logger.isDebugEnabled()) {
		logger.debug("# start : EnvOption reload[" + compId + "] ############################################ #");
	    }

	    AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	    synchronized (appEnvOption) {
		appEnvOption.setIsLoad(false);

		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		List<OptionVO> optionVOs = (List<OptionVO>) commonDAO.getListMap("common.listAppEnvOption", map);
		
		Map envMap = appEnvOption.getOptions(compId);
		envMap.clear();
		int optionCount = optionVOs.size();
		logger.debug("# optionCount : " + optionCount);
		for (int loop = 0; loop < optionCount; loop++) {
		    OptionVO optionVO = optionVOs.get(loop);

		    envMap.put(optionVO.getOptionId(), optionVO);		    
		    logger.debug("# COMP_ID : " + optionVO.getCompId() + ", OPTION_ID : " + optionVO.getOptionId() + ", USE_YN : " + optionVO.getUseYn() + ", OPTION_VALUE : " + optionVO.getOptionValue());
		}
		appEnvOption.setOptions(compId, envMap);
		appEnvOption.setIsLoad(true);
	    }
	    if (logger.isDebugEnabled()) {
		logger.debug("# end : EnvOption reload ############################################ #");
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
    }
    
}
