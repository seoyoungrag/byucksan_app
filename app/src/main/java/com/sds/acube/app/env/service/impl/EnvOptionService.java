package com.sds.acube.app.env.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.env.service.IEnvOptionService;
import com.sds.acube.app.env.vo.OptionVO;

/**
 * Class Name  : EnvOptionService.java <br> Description : 결재옵션 서비스 <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 25. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   신경훈  
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.env.service.impl.EnvOptionService.java
 */

@Service("envOptionService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnvOptionService extends BaseService implements IEnvOptionService { 
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    public void updateOptionList(List voList) throws Exception {
	commonDAO.modifyList("env.updateOptionUseYn", voList);
    }
    
    public void updateOptionUseYn(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOptionUseYn", vo);
    }
    
    public void updateOptionValue(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOptionValue", vo);
    }
    
    public void updateOption(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOption", vo);
    }
    
    
    public void updateOptionUseYn(String compId, String optionId, String useYn) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setUseYn(useYn);
	commonDAO.modify("env.updateOptionUseYn", vo);
    }
    
    public void updateOptionValue(String compId, String optionId, String optionValue) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setOptionValue(optionValue);
	commonDAO.modify("env.updateOptionValue", vo);
    }
    
    public void updateOption(String compId, String optionId, String useYn, String optionValue) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setUseYn(useYn);
	vo.setOptionValue(optionValue);
	commonDAO.modify("env.updateOption", vo);
    }

    
}
