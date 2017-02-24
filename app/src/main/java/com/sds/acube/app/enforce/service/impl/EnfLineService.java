package com.sds.acube.app.enforce.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.vo.EnfLineVO;


/**
 * Class Name : EnfLineService.java <br> Description : 접수된 문서의 결재 경로를 관리하는 서비스 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.service.EnfLineService.java
 */
@SuppressWarnings("serial")
@Service("enfLineService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnfLineService extends BaseService implements IEnfLineService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    public void delete(EnfLineVO lineVO) throws Exception {
	this.commonDao.delete("", lineVO.getProcessorId());
    }


    /**
     * 접수라인 등록처리 라인의 처리자 갯수만큼 등록처리를 함
     */
    @SuppressWarnings("unchecked")
    public void insert(List list) throws Exception {

	this.commonDao.insertList("enforce.insertEnfLine", list);

    }


    /**
     * 결재정보(리스트를 string으로 변환해서 가져옴)
     */
    @SuppressWarnings("unchecked")
    public String get(EnfLineVO lineVO, String docState) throws Exception {

	StringBuffer enfLines = new StringBuffer();
	EnfLineVO enfLineVO = null;
	List list = this.getList(lineVO);

	int size = list.size();
	for (int i = 0; i < size; i++) {
	    enfLineVO = new EnfLineVO();
	    enfLineVO = (EnfLineVO) list.get(i);
	    enfLines.append(enfLineVO.getProcessorId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getProcessorName()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getProcessorPos()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getProcessorDeptId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getProcessorDeptName()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getRepresentativeId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getRepresentativeName()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getRepresentativePos()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getRepresentativeDeptId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getRepresentativeDeptName()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getAskType()).append(ConstantList.COL);

	    // 반려문서일경우 처리상태를 ""
	    if (docState != null && appCode.getProperty("ENF310", "ENF310", "ENF").equals(docState)) {
		enfLines.append("").append(ConstantList.COL);
		enfLines.append("9999-12-31 23:59:59").append(ConstantList.COL);
		enfLines.append("9999-12-31 23:59:59").append(ConstantList.COL);
	    } else {
		enfLines.append(enfLineVO.getProcType()).append(ConstantList.COL);
		enfLines.append(enfLineVO.getProcessDate()).append(ConstantList.COL);
		enfLines.append(enfLineVO.getReadDate()).append(ConstantList.COL);
	    }
	    enfLines.append(enfLineVO.getEditLineYn()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getMobileYn()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getProcOpinion()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getSignFileName()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getLineHisId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getFileHisId()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getAbsentReason()).append(ConstantList.COL);
	    enfLines.append(enfLineVO.getLineOrder()).append(ConstantList.COL);

	    enfLines.append(ConstantList.ROW);
	}

	return enfLines.toString();
    }


    /**
     * 결재정보(리스트를 string으로 변환해서 가져옴)
     */
    public String get(EnfLineVO lineVO) throws Exception {

	return get(lineVO, null);
    }


    /**
     * <pre> 
     *  리스트 조회
     * </pre>
     * 
     * @param lineVO
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<EnfLineVO> getList(EnfLineVO lineVO) throws Exception {

	return (List<EnfLineVO>) commonDao.getList("enforce.selectEnfLineList", lineVO); 

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfLineService#update(com.sds.acube.app.
     * enforce.vo.EnfLineVO)
     */
    public void update(EnfLineVO enfLineVO) throws Exception {
	// TODO Auto-generated method stub

    }

    
    /**
     * 결재처리 의견
     */
    public String[] getCurOpinion(EnfLineVO lineVO) throws Exception { 
	String[] opinion = {"",""};
	EnfLineVO enfLineVO = (EnfLineVO)this.commonDao.get("enforce.selectEnfLineProcOpinion", lineVO);
	
	if (enfLineVO != null && enfLineVO.getProcOpinion() != null) {
	    opinion[0] = enfLineVO.getProcOpinion().trim();
	    opinion[1] = enfLineVO.getAskType().trim();
	}
	
	return opinion;
    }
}