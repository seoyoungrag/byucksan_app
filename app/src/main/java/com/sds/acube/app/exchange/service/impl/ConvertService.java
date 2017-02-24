package com.sds.acube.app.exchange.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.exchange.service.IConvertService;
import com.sds.acube.app.exchange.vo.ConvertVO;


/**
 * Class Name : ConvertService.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 7. 7. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 7. 7.
 * @version  1.0
 * @see  com.sds.acube.app.exchange.service.impl.ConvertService.java
 */

@Service("convertService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ConvertService extends BaseService implements IConvertService {

    private static final long serialVersionUID = 4753473370440052964L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    @SuppressWarnings("unchecked")
    public List<ConvertVO> getDocList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("convert.doc.list", param);
    }


    @SuppressWarnings("unchecked")
    public List<ConvertVO> getDocMonthList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("convert.doc.month.list", param);
    }


    @SuppressWarnings("unchecked")
    public List<ConvertVO> getErrorList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("convert.error.list", param);
    }


    @SuppressWarnings("unchecked")
    public List<ConvertVO> getTargetList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("convert.doc.target", param);
    }


    public int inputError(ConvertVO vo, String message) throws Exception {
	vo.setMessage(message);
	return this.commonDao.insert("convert.input.error", vo);
    }


    public int removeError(ConvertVO vo) throws Exception {
	return this.commonDao.delete("convert.error.remove", vo);
    }

}
