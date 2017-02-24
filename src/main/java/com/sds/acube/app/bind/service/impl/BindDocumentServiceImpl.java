/**
 * 
 */
package com.sds.acube.app.bind.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.bind.service.BindDocumentService;
import com.sds.acube.app.bind.vo.BindDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;


/**
 * Class Name : BindServiceImpl.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.service.impl.BindServiceImpl.java
 */

@Service("bindDocumentService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BindDocumentServiceImpl extends BaseService implements BindDocumentService, BindConstants {

    private static final long serialVersionUID = 1211245176110782181L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    @SuppressWarnings("unchecked")
    public List<BindDocVO> getDocumentList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("bind.document.rows", param);
    }


    public BindDocVO getDocument(String compId, String docId) throws Exception {
	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DOC_ID, docId);
	return (BindDocVO) this.commonDao.get("bind.document.selectRow", param);
    }


    @SuppressWarnings("unchecked")
    public List<BindDocVO> getNonBindList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("bind.document.non.bind", param);
    }


    @SuppressWarnings("unchecked")
    public List<BindDocVO> getTransferList(Map<String, String> param) throws Exception {
	return this.commonDao.getList("bind.document.transfer", param);
    }


    public int move(BindDocVO bindDocVO) throws Exception {
	int result = this.commonDao.modify("bind.document.move.app_doc", bindDocVO);
	if (result == 0) {
	    return this.commonDao.modify("bind.document.move.enf_doc", bindDocVO);
	}
	return result;
    }
}
