/**
 * 
 */
package com.sds.acube.app.env.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.anyframe.query.QueryServiceException;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.env.service.IEnvFormService;
import com.sds.acube.app.env.vo.CategoryVO;
import com.sds.acube.app.env.vo.FormVO;


/**
 * Class Name : EnvFormService.java <br> Description : 서식관리 서비스 구현 클래스 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 26. <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  2011. 4. 26.
 * @version  1.0
 * @see  com.sds.acube.app.env.service.impl.EnvFormService.java
 */

@Service("envFormService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnvFormService extends BaseService implements IEnvFormService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;


    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#deleteEvnForm()
     */
    @SuppressWarnings("unchecked")
    public void deleteEvnForm(Map inputMap) throws Exception {

	commonDAO.deleteMap("env.form.deleteEnvForm", inputMap);

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#insertEvnForm()
     */
    @SuppressWarnings("unchecked")
    public void insertEvnForm(Map inputMap) throws Exception {

	FormVO formVO = new FormVO();
	formVO.setCompId((String) inputMap.get("compId"));
	formVO.setBizSystemCode((StringUtil.null2str((String) inputMap.get("bizSystemCode"), "")));
	formVO.setBizTypeCode((StringUtil.null2str((String) inputMap.get("bizTypeCode"), "")));
	formVO.setFormType((StringUtil.null2str((String) inputMap.get("formType"), "")));
	logger.debug(StringUtil.null2str((String) inputMap.get("groupType"), ""));
	formVO = this.getFormBySystem(formVO);

	if (formVO != null) {

	    throw new Exception("This form already exist");
	}

	String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + inputMap.get("compId") + "";// 파일

	// 저장서버 파일 등록
	StorFileVO storVO = new StorFileVO();
	storVO.setDocid("");
	storVO.setFilename((String) inputMap.get("formFileName"));// 파일고유명(GUID채번)
	storVO.setFilepath(filepath + "/" + storVO.getFilename());

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId((String)inputMap.get("compId"));
	drmParamVO.setUserId((String)inputMap.get("registerId"));

	storVO = attachService.uploadAttach(storVO, drmParamVO);

	inputMap.put("formId", GuidUtil.getGUID());
	inputMap.put("formFileId", storVO.getFileid());
	inputMap.put("registDate", DateUtil.getCurrentDate());

	// 정렬순번수정
	commonDAO.modifyMap("env.form.updateFormOrder", inputMap);

	// 서식등록
	commonDAO.insertMap("env.form.insertEnvForm", inputMap);
	
	//양식 업데이트시, 공문서 변환양식을 선택한 경우, 복수의 공문서 변환양식
	//이 존재하지 않도록, 이미 설정한 공문서 변환양식은 'N'으로 처리한다.
	String pubdocformYn = (String)inputMap.get("pubdocformYn");
	if("Y".equals(pubdocformYn)){
	    commonDAO.modifyMap("env.form.updateFormPubDocFormYn", inputMap);
	}
	
	//양식 업데이트시, 감사문서양식을 선택한 경우, 복수의 감사문서양식
	//이 존재하지 않도록, 이미 설정한 감사문서 양식은 'N'으로 처리한다.
	String auditformYn = (String)inputMap.get("auditformYn");
	if("Y".equals(auditformYn)){
	    commonDAO.modifyMap("env.form.updateFormAuditFormYn", inputMap);
	}

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#listEvnForm()
     */
    @SuppressWarnings("unchecked")
    public List listEvnForm(Map inputMap) throws Exception {

	List rsltList = (List) commonDAO.getListMap("env.form.ListEnvForm", inputMap);

	return rsltList;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#selectEvnForm()
     */
    @SuppressWarnings("unchecked")
    public FormVO selectEvnForm(Map inputMap) throws Exception {
	FormVO formVO = (FormVO) commonDAO.getMap("env.form.SelectEnvForm", inputMap);
	return formVO;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#selectEvnFormById()
     */
    @SuppressWarnings("unchecked")
    public FormVO selectEvnFormById(Map inputMap) throws Exception {
	FormVO formVO = (FormVO) commonDAO.getMap("env.form.SelectEnvFormById", inputMap);
	return formVO;
    }
    
    
     /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#selectEvnFormById()
     */
    @SuppressWarnings("unchecked")
    public FormVO selectEvnPubdocForm(Map inputMap) throws Exception {
	FormVO formVO = (FormVO) commonDAO.getMap("env.form.SelectEnvPubdocForm", inputMap);
	return formVO;
    }
    
        
     /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#selectEvnFormById()
     */
    @SuppressWarnings("unchecked")
    public FormVO selectEvnAuditForm(Map inputMap) throws Exception {
	FormVO formVO = (FormVO) commonDAO.getMap("env.form.SelectEnvAuditForm", inputMap);
	return formVO;
    }



    /*
     * (non-Javadoc)
     * 
     * @see com.sds.acube.app.env.service.IEnvFormService#updateEvnForm()
     */
    @SuppressWarnings("unchecked")
	public void updateEvnForm(Map inputMap) throws Exception {

	FormVO formVO = (FormVO) commonDAO.getMap("env.form.SelectEnvForm", inputMap);
	
	//시스템코드, 파일 확장자에 해당하는 서식조회
	FormVO checkFormVO = new FormVO();
	checkFormVO.setCompId((String) inputMap.get("compId"));
	checkFormVO.setBizSystemCode((StringUtil.null2str((String) inputMap.get("bizSystemCode"), "")));
	checkFormVO.setBizTypeCode((StringUtil.null2str((String) inputMap.get("bizTypeCode"), "")));
	checkFormVO.setFormType((StringUtil.null2str((String) inputMap.get("formType"), "")));
	
	checkFormVO = this.getFormBySystem(checkFormVO);

	if (checkFormVO != null) {
		//현재 수정 중인 서식 제외
		String checkFormId = checkFormVO.getFormId();
		
		if(!checkFormId.equals(formVO.getFormId())) {
			throw new Exception("This form already exist");
		}
	}

	String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + inputMap.get("compId") + "";// 파일

	if (!((String) inputMap.get("formFileName")).equals(formVO.getFormFileName())) {
	    // 저장서버 파일 등록
	    StorFileVO storVO = new StorFileVO();
	    storVO.setDocid("");
	    storVO.setFilename((String) inputMap.get("formFileName"));// 파일고유명
	    storVO.setFilepath(filepath + "/" + storVO.getFilename());

	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId((String)inputMap.get("compId"));
	    drmParamVO.setUserId((String)inputMap.get("userId"));
	    
	    storVO = attachService.uploadAttach(storVO, drmParamVO);

	    inputMap.put("formFileId", storVO.getFileid());
	    inputMap.put("registDate", DateUtil.getCurrentDate());
	}

	int order = Integer.parseInt((String) inputMap.get("formOrder"));

	if (formVO.getFormOrder() != order) {
	    // 정렬순번수정
	    commonDAO.modifyMap("env.form.updateFormOrder", inputMap);

	}
	commonDAO.modifyMap("env.form.updateEnvForm", inputMap);
	//양식 업데이트시, 공문서 변환양식을 선택한 경우, 복수의 공문서 변환양식
	//이 존재하지 않도록, 이미 설정한 공문서 변환양식은 'N'으로 처리한다.
	String pubdocformYn = (String)inputMap.get("pubdocformYn");
	if("Y".equals(pubdocformYn)){
	    commonDAO.modifyMap("env.form.updateFormPubDocFormYn", inputMap);
	}
	
        //양식 업데이트시, 감사문서양식을 선택한 경우, 복수의 감사문서양식
	//이 존재하지 않도록, 이미 설정한 감사문서 양식은 'N'으로 처리한다.
	String auditformYn = (String)inputMap.get("auditFormYn");
	if("Y".equals(auditformYn)){
	    commonDAO.modifyMap("env.form.updateFormAuditFormYn", inputMap);
	}

    }


    /**
     * <pre> 
     *  서식함 목록조회
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public List listEvnCategory(Map inputMap) throws Exception {

	List rsltList = commonDAO.getListMap("env.category.listCategory", inputMap);

	return rsltList;
    }

    // 다국어 추가 (langType)
    public List listEvnCategoryResource(Map inputMap) throws Exception {
    	List rsltList = commonDAO.getListMap("env.category.listCategoryResource", inputMap);
    	return rsltList;
    }
    
    /**
     * <pre> 
     *  서식함 조회
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public CategoryVO selectEvnCategory(Map inputMap) throws Exception {

	return null;
    }


    /**
     * <pre> 
     * 서식함 등록
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public void insertEvnCategory(Map inputMap) throws QueryServiceException, Exception {

	CategoryVO categoryVO = new CategoryVO();
	//categoryVO.setCategoryId(GuidUtil.getGUID());
	categoryVO.setCompId((String) inputMap.get("compId"));
	categoryVO.setCategoryName((String) inputMap.get("categoryName"));
	categoryVO.setCategoryId((String) inputMap.get("categoryId"));
	categoryVO.setRegistDate(DateUtil.getCurrentDate());
	categoryVO.setRegisterId((String) inputMap.get("userId"));
	categoryVO.setRegisterName((String) inputMap.get("userName"));
	categoryVO.setRemark((String) inputMap.get("remark"));

	commonDAO.insert("env.category.insertCategory", categoryVO);
    }


    /**
     * <pre> 
     *  서식함 수정 
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public void updateEvnCategory(Map inputMap) throws Exception {

	CategoryVO categoryVO = new CategoryVO();
	categoryVO.setCompId((String) inputMap.get("compId"));
	categoryVO.setCategoryName((String) inputMap.get("categoryName"));
	categoryVO.setCategoryId((String) inputMap.get("categoryId"));
	categoryVO.setDeleteYn((String) inputMap.get("deleteYn"));
	categoryVO.setCategoryOrder(Integer.parseInt((String) inputMap.get("categoryOrder")));
	commonDAO.modify("env.category.updateCategory", categoryVO);
    }


    /**
     * <pre> 
     *  서식함 삭제
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public void deleteEvnCategory(Map inputMap) throws Exception {

	CategoryVO categoryVO = new CategoryVO();
	categoryVO.setCompId((String) inputMap.get("compId"));
	categoryVO.setCategoryId((String) inputMap.get("categoryId"));

	
	inputMap.put("docType",(String) inputMap.get("categoryId"));

	//생산문서 건수 조회

	Map appMap = (Map)commonDAO.getMap("approval.selectCountByDocType", inputMap);

	if(appMap !=null){
	    BigDecimal cnt = (BigDecimal)appMap.get("cnt");
	    int count = Integer.parseInt(cnt.toString());
	    if(count > 0){
		throw new Exception("삭제할 수 없습니다.");
	    }
	}
	
	Map enfMap = (Map)commonDAO.getMap("enforce.selectCountByDocType", inputMap);

	if(enfMap !=null){
	    BigDecimal cnt = (BigDecimal)enfMap.get("cnt");
	    int count = Integer.parseInt(cnt.toString());
	    if(count > 0){
		throw new Exception("삭제할 수 없습니다.");
	    }
	}
	
	commonDAO.modify("env.category.deleteCategory", categoryVO);
	
    }


    /**
     * <pre> 
     *  시스템코드와 관련된 양식조회
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    public FormVO getFormBySystem(FormVO formVO) throws Exception {

	List formList = commonDAO.getList("env.form.SelectEnvFormBySystem", formVO);

	if (formList != null) {
	    if (formList.size() > 0) {
		FormVO form = (FormVO) formList.get(0);
		return form;
	    }
	}
	return null;
    }
    
    
    public int selectFormName(Map inputMap) throws Exception{
    	  	
    	List formList = commonDAO.getListMap("env.form.getByformName", inputMap);
    	if(formList != null){
    		if(formList.size() > 0){
    			return formList.size(); 
    		}
    	}
    	
    	return 0;
    }
    
      
}
