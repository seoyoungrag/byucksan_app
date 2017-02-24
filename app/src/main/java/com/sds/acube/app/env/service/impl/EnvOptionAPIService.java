package com.sds.acube.app.env.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IMemoryService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppEnvOption;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.JStor;
import com.sds.acube.app.common.util.MemoryUtil;
import com.sds.acube.app.common.vo.AppCodeVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.util.OptionUtil;
import com.sds.acube.app.env.vo.FormEnvVO;
import com.sds.acube.app.env.vo.LineGroupVO;
import com.sds.acube.app.env.vo.LineUserVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.PeriodVO;
import com.sds.acube.app.env.vo.PubViewGroupVO;
import com.sds.acube.app.env.vo.PubViewUserVO;
import com.sds.acube.app.env.vo.RecvGroupVO;
import com.sds.acube.app.env.vo.RecvUserVO;
import com.sds.acube.app.env.vo.SenderTitleVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.resource.vo.ResourceVO;

/**
 * Class Name  : EnvOptionAPIService.java <br> Description : 옵션 API  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 25. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   신경훈
 * @since  2011. 3. 25.
 * @version  1.0 
 * @see  com.sds.acube.app.env.service.impl.EnvOptionAPIService.java
 */

@SuppressWarnings("serial")
@Service("envOptionAPIService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnvOptionAPIService extends BaseService implements IEnvOptionAPIService { 
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO; 
    
    /**
	 */
    @Autowired
    private IOrgService orgService;
    
    /**
	 */
    @Autowired
    private IMemoryService memoryService;
    
    /** 
     * 
     * <pre> 
     *  결재옵션을 메모리에 reload 한다.
     * </pre>
     * @param compId
     * @throws Exception
     * @see  
     *
     */
    public void optionReload(String compId) throws Exception {
	// 현재 서버옵션값 재설정
	memoryService.reloadOption(compId);

	// 이중화된 서버가 있을 경우 재설정 호출
	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	appEnvOption.reloadOption(compId);
    }
    
    /** 
     * 
     * <pre> 
     *  결재옵션 전체를 리스트로 가져온다
     * </pre>
     * @param OptionVO
     *  OptionVO를 파라미터로 받는다.
     * @return List<OptionVO>
     *  결재옵션 정보를 OptionVO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionList(OptionVO optionVO) throws Exception {
	return (List<OptionVO>) commonDAO.getList("env.selectOptionList", optionVO);
    }
    
    /**
     *  
     * <pre> 
     *  결재옵션 전체를 해쉬맵(옵션ID, 옵션VO)으로 가져온다.
     * </pre>
     * @param optionVO
     *  OptionVO를 파라미터로 받는다.
     * @return HashMap<optionId, OptionVO>
     *  결재옵션 정보를 해쉬맵에 <옵션ID, 옵션VO>형대로 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, OptionVO> selectOptionMap(OptionVO optionVO) throws Exception {
	
	List<OptionVO> voList = new ArrayList<OptionVO>();
	voList = (List<OptionVO>) commonDAO.getList("env.selectOptionList", optionVO);
	int voListSize = voList.size();
	HashMap<String, OptionVO> resultMap = new HashMap<String, OptionVO>();
	
	for (int i=0; i<voListSize; i++) {
	    resultMap.put((voList.get(i)).getOptionId(), voList.get(i));
	}
	
	return resultMap;
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션 그룹별 리스트를 가져온다.
     *  그룹(optionType) : OPTG000=처리유형, OPTG100=함, OPTG200=대장, OPTG300=결재옵션)
     * </pre>
     * @param OptionVO
     *  OptionVO를 파라미터로 받는다.
     * @return List<OptionVO>
     *  그룹별 결재옵션 정보를 OptionVO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionGroupList(OptionVO optionVO) throws Exception {
	return (List<OptionVO>) commonDAO.getList("env.selectOptionGroupList", optionVO);
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션 그룹별 해쉬맵(옵션ID, 옵션VO)을 가져온다.
     * </pre>
     * @param optionVO
     *  OptionVO를 파라미터로 받는다.
     * @return HashMap<optionId, OptionVO>
     *  그룹별 결재옵션 정보를  해쉬맵에 <옵션ID, 옵션VO>형대로 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, OptionVO> selectOptionGroupMap(OptionVO optionVO) throws Exception {
	List<OptionVO> voList = new ArrayList<OptionVO>();
	voList = (List<OptionVO>) commonDAO.getList("env.selectOptionGroupMap", optionVO);
	int voListSize = voList.size();
	HashMap<String, OptionVO> resultMap = new HashMap<String, OptionVO>();
	
	for (int i=0; i<voListSize; i++) {
	    resultMap.put((voList.get(i)).getOptionId(), voList.get(i));
	}
	
	return resultMap;
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션별 옵션VO를 가져온다.
     * </pre>
     * @param optionVO
     *  OptionVO를 파라미터로 받는다.
     * @return optionVO
     *  옵션ID에 대한 결재옵션 정보를 optionVO에 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public OptionVO selectOption(OptionVO optionVO) throws Exception {

	//return (OptionVO) commonDAO.get("env.selectOption", optionVO);

	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	return appEnvOption.getOption(optionVO.getCompId(), optionVO.getOptionId());
    }
    
    /**
     * 
     * <pre> 
     *  옵션 TYPE에 해당하는 결재옵션들을 리스트로 가져온다.
     * </pre>
     * @param compId
     * @param optionType
     *  회사ID, 옵션TYPE을 파라미터로 받는다.
     * @return List<OptionVO>
     *  옵션TYPE에 해당하는 결재옵션을 옵션VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionGroupList(String compId, String optionType) throws Exception {
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionType(optionType);
		
		return (List<OptionVO>) commonDAO.getList("env.selectOptionGroupList", optionVO);
    }
    
    /**
     * 
     * <pre> 
     *  다국어를 위해서 언어 타입을 추가함
     * </pre>
     * @param compId
     * @param optionType
     * @param langType
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionGroupListResource(String compId, String optionType, String langType) throws Exception {
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionType(optionType);
		optionVO.setLangType(langType);
		
		return (List<OptionVO>) commonDAO.getList("env.selectOptionGroupListResource", optionVO);
    }
    
    /**
     * 
     * <pre> 
     *  다국어를 위해서 언어 타입을 추가함
     * </pre>
     * @param compId
     * @param optionType
     * @param langType
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionGroupListResourceOrderBySequence(String compId, String optionType, String langType) throws Exception {
    	OptionVO optionVO = new OptionVO();
    	optionVO.setCompId(compId);
    	optionVO.setOptionType(optionType);
    	optionVO.setLangType(langType);
    	
    	return (List<OptionVO>) commonDAO.getList("env.selectOptionGroupListResourceOrderBySequence", optionVO);
    }
    
    @SuppressWarnings("unchecked")
    public List<OptionVO> selectOptionGroupListOrderBySequence(String compId, String optionType) throws Exception {
    	OptionVO optionVO = new OptionVO();
    	optionVO.setCompId(compId);
    	optionVO.setOptionType(optionType);
    	
    	return (List<OptionVO>) commonDAO.getList("env.selectOptionGroupListOrderBySequence", optionVO);
    }
    
    /**
     * 
     * <pre> 
     *  옵션 TYPE에 해당하는 결재옵션들을 해쉬맵(옵션ID, 옵션VO)으로 가져온다.
     * </pre>
     * @param compId
     * @param optionType
     *  회사ID, 옵션TYPE을 파라미터로 받는다.
     * @return HashMap<optionId, OptionVO>
     *  옵션TYPE에 해당하는 결재옵션을  해쉬맵에 <옵션ID, 옵션VO>형대로 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, OptionVO> selectOptionGroupMap(String compId, String optionType) throws Exception {
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionType(optionType);
		
		List<OptionVO> voList = new ArrayList<OptionVO>();
		voList = (List<OptionVO>) commonDAO.getList("env.selectOptionGroupMap", optionVO);
		int voListSize = voList.size();
		HashMap<String, OptionVO> resultMap = new HashMap<String, OptionVO>();
		
		for (int i=0; i<voListSize; i++) {
		    resultMap.put((voList.get(i)).getOptionId(), voList.get(i));
		}
		
		return resultMap;
    }
    
    /**
     * 
     * <pre> 
     *  다국어를 위해서 언어 타입을 추가함
     * </pre>
     * @param compId
     * @param optionType
     * @param langType
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, OptionVO> selectOptionGroupMapResource(String compId, String optionType, String langType) throws Exception {
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionType(optionType);
		optionVO.setLangType(langType);
		
		List<OptionVO> voList = new ArrayList<OptionVO>();
		voList = (List<OptionVO>) commonDAO.getList("env.selectOptionGroupMapResource", optionVO);
		int voListSize = voList.size();
		HashMap<String, OptionVO> resultMap = new HashMap<String, OptionVO>();
		
		for (int i=0; i<voListSize; i++) {
		    resultMap.put((voList.get(i)).getOptionId(), voList.get(i));
		}
		
		return resultMap;
    }    
    
    /**
     * 
     * <pre> 
     *  결재옵션별 옵션VO를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     *  회사ID, 옵션ID를 파라미터로 받는다.
     * @return OptionVO
     *  해당 옵션ID에 대한 결재옵션 정보를 옵션VO에 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public OptionVO selectOption(String compId, String optionId) throws Exception {
	/*
	OptionVO optionVO = new OptionVO();
	optionVO.setCompId(compId);
	optionVO.setOptionId(optionId);
	
	return (OptionVO) commonDAO.get("env.selectOption", optionVO);
	 */
	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	return appEnvOption.getOption(compId, optionId);
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션별 사용여부를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     *  회사ID, 옵션ID를 파라미터로 받는다.
     * @return useYn
     *  해당 결재옵션의 사용여부를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public String selectOptionValue(String compId, String optionId) throws Exception {
	/*
	OptionVO optionVO = new OptionVO();
	optionVO.setCompId(compId);
	optionVO.setOptionId(optionId);
	optionVO = (OptionVO) commonDAO.get("env.selectOption", optionVO);
	return optionVO.getUseYn();
	 */	
	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	return appEnvOption.getOptionValue(compId, optionId);
    }
    
    /**
     *  
     * <pre> 
     *  결재옵션별 옵션Value를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     *  회사ID, 옵션ID를 파라미터로 받는다.
     * @return optionValue
     *  해당 결재옵션의 옵션값을 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public String selectOptionText(String compId, String optionId) throws Exception {
		/*	
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionId(optionId);
		optionVO = (OptionVO) commonDAO.get("env.selectOption", optionVO);
		if (optionVO == null) {
		    return "";
		} else {
		    return optionVO.getOptionValue();
		}
		 */
    	
		AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
		
		// 다국어 때문에 RES로 시작하면 다국어이므로 tgw_app_resouce 테이블에서 가져와야한다.
		String optionValue = appEnvOption.getOptionText(compId, optionId);
		if (optionValue.indexOf("RES") == 0) {	
			ResourceVO resourceVO = new ResourceVO();
			resourceVO.setCompId(compId);
			resourceVO.setResourceId(optionValue);
			resourceVO.setLangType(AppConfig.getCurrentLangType());
			
			resourceVO = (ResourceVO)commonDAO.get("resource.selectResourceByLangType", resourceVO);
			if (resourceVO == null) {
				optionValue = "";
			} else {
				optionValue = resourceVO.getResourceName();
			}
		}
		return optionValue;
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션 옵션Value를 구분자로 나눠 해쉬맵으로 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     *  회사ID, 옵션ID를 파라미터로 받는다.
     * @return HashMap<String, String>
     *  해당 결재옵션의 옵션값을 해쉬맵으로 넘긴다.
     *  예) I1:Y^I2:N^..... 형태의 옵션값을 구분자로 나눠 해쉬맵형태로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public HashMap<String, String> selectOptionTextMap(String compId, String optionId) throws Exception {
	OptionUtil optionUtil = new OptionUtil();
	/*
	OptionVO optionVO = new OptionVO();
	optionVO.setCompId(compId);
	optionVO.setOptionId(optionId);
	optionVO = (OptionVO) commonDAO.get("env.selectOption", optionVO);
	 */
	AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	OptionVO optionVO = appEnvOption.getOption(compId, optionId);
	return optionUtil.arrayToMap(optionVO.getOptionValue());
    } 
    
    /** 
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param voList
     *  옵션VO를 리스트로 받는다.
     *  해당옵션의 사용여부를 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void updateOptionUseYnList(List voList) throws Exception {
	commonDAO.modifyList("env.updateOptionUseYn", voList);
	/*
	memoryService.reloadOption(((OptionVO) voList.get(0)).getCompId());
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(((OptionVO) voList.get(0)).getCompId());
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param voList
     *  옵션VO를 리스트로 받는다.
     *  해당옵션의 사용여부와 옵션값을 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void updateOptionList(List voList) throws Exception {
    	// 다국어 때문에 'env.updateOption'를 사용하지 않고 'env.updateOptionUseYn'를 사용함.
    	commonDAO.modifyList("env.updateOptionUseYn", voList);
    	// commonDAO.modifyList("env.updateOption", voList);
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param vo
     *  옵션VO를 파라미터로 받는다.
     *  해당옵션의 사용여부를 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOptionUseYn(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOptionUseYn", vo);
	/*
	memoryService.reloadOption(vo.getCompId());
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(vo.getCompId());
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션값을 수정한다.
     * </pre>
     * @param vo
     *  옵션VO를 파라미터로 받는다.
     *  해당옵션의 옵션값을 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOptionValue(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOptionValue", vo);
	/*
	memoryService.reloadOption(vo.getCompId());
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(vo.getCompId());
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param vo
     *  옵션VO를 파라미터로 받는다.
     *  해당옵션의 사용여부와 옵션값을 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOption(OptionVO vo) throws Exception {
	commonDAO.modify("env.updateOption", vo);
	/*
	memoryService.reloadOption(vo.getCompId());
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(vo.getCompId());
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param useYn
     *  회사ID, 옵션ID, 사용여부를 파라미터로 받아서 해당 결재옵션의 사용여부를 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOptionUseYn(String compId, String optionId, String useYn) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setUseYn(useYn);
	commonDAO.modify("env.updateOptionUseYn", vo);
	/*
	memoryService.reloadOption(compId);
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(compId);
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션값을 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param optionValue
     *  회사ID, 옵션ID, 옵션값을 파라미터로 받아서 해당 결재옵션의 옵션값을 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOptionValue(String compId, String optionId, String optionValue) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setOptionValue(optionValue);
	commonDAO.modify("env.updateOptionValue", vo);
	/*
	memoryService.reloadOption(compId);
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(compId);
	*/
    }
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param useYn
     * @param optionValue
     *  회사ID, 옵션ID, 사용여부, 옵션값을 파라미터로 받아서 해당 결재옵션의 사용여부와 옵션값을 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateOption(String compId, String optionId, String useYn, String optionValue) throws Exception {
	OptionVO vo = new OptionVO();
	vo.setCompId(compId);
	vo.setOptionId(optionId);
	vo.setUseYn(useYn);
	vo.setOptionValue(optionValue);
	commonDAO.modify("env.updateOption", vo);
	/*
	memoryService.reloadOption(compId);
	//AppEnvOption appEnvOption = MemoryUtil.getEnvOptionInstance();
	//appEnvOption.reloadOption(compId);
	*/
    }
    
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼별 리스트를 가져온다.
     *  envType : FET001=로고, FET002=심볼, FET003=상부캠페인, FET004=하부캠페인)
     * </pre>
     * @param compId
     * @param envType
     *  회사ID, 양식환경타입을 파라미터로 받는다.
     * @return List<FormEnvVO>
     *  해당 양식환경타입의 값을 FormEnvVO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<FormEnvVO> selectFormEnvList(String compId, String registerDeptId, String envType) throws Exception {
		FormEnvVO vo = new FormEnvVO();
		vo.setCompId(compId);
		vo.setRegisterDeptId(ListUtil.TransString(registerDeptId)); 
		vo.setEnvType(envType);
		
		return (List<FormEnvVO>) commonDAO.getList("env.selectFormEnvList", vo);
    }
    
    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<FormEnvVO> selectFormEnvListResource(String compId, String registerDeptId, String envType, String langType) throws Exception {
		FormEnvVO vo = new FormEnvVO();
		vo.setCompId(compId);
		vo.setRegisterDeptId(ListUtil.TransString(registerDeptId)); 
		vo.setEnvType(envType);
		vo.setLangType(langType);
		
		return (List<FormEnvVO>) commonDAO.getList("env.selectFormEnvListResource", vo);
    }
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼별 기본설정을 VO로 가져온다. 소속기관에 기본설정이 없을 경우 최상위기관의 기본설정 VO를 가져온다.
     * </pre>
     * @param compId
     * @param envType
     *  회사ID, 양식환경타입을 파라미터로 받는다.
     * @return FormEnvVO
     *  해당 양식환경타입의 기본설정된 값을 FormEnvVO로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public FormEnvVO selectDefaultFormEnvVO(String compId, String registerDeptId, String envType) throws Exception {
		FormEnvVO vo = null;
		while (true) {
		    vo = new FormEnvVO();
		    vo.setCompId(compId); 
		    vo.setRegisterDeptId(ListUtil.TransString(registerDeptId)); 
		    vo.setEnvType(envType);
		    vo.setDefaultYn("Y");
		    
		    // 다국어 추가
		    vo.setLangType(AppConfig.getCurrentLangType());
		    vo = (FormEnvVO) commonDAO.get("env.selectFormEnvListResource", vo);
		    // vo = (FormEnvVO) commonDAO.get("env.selectFormEnvList", vo);
		    
		    if ((vo != null) || (compId.equals(registerDeptId))) {
		    	break;
		    }
		    else {
	//		registerDeptId = orgService.selectHeadOrganizationByRoleCode(compId,
	//									orgService.selectOrganization(registerDeptId).getOrgParentID(),
	//									AppConfig.getProperty("role_institution", "O002", "role")).getOrgID();
		    	registerDeptId = compId;
		    }
		}
		return vo;
    }
    
    /**
     *  
     * <pre> 
     *  다운로드할 로고/심볼 파일의 VO를 가져온다.
     * </pre>
     * @param compId
     * @param formEnvId
     *  회사ID, 양식환경ID를 파라미터로 받는다.
     * @return FormEnvVO
     *  해당 양식환경ID의 값을 FormEnvVO로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public FormEnvVO selectFormEnvVO(String compId, String formEnvId) throws Exception {
	FormEnvVO vo = new FormEnvVO();
	vo.setCompId(compId);
	vo.setFormEnvId(formEnvId);
	return (FormEnvVO) commonDAO.get("env.selectFormEnvVO", vo);
    }    
    
    /**
     * 
     * <pre> 
     *  기본설정된 캠페인 문구를 가져온다.
     *  상위캠페인:envType=FET003 , 하위캠페인:envType=FET004
     * </pre>
     * @param compId
     * @param envType
     *  회사ID, 양식환경타입을 파라미터로 받는다.
     * @return envInfo
     *  기본설정된 캠페인을 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public String selectDefaultCampaign(String compId, String registerDeptId, String envType) throws Exception {
		FormEnvVO vo = selectDefaultFormEnvVO(compId, registerDeptId, envType);
		if (vo == null) {
		    return "";
		} else {
		    return vo.getEnvInfo();
		}
    }
    
    /**
     *  
     * <pre> 
     *  회사에 등록된 모든 로고/심볼 파일을 STOR서버에서 WAS로 다운로드 한다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return true/false
     *  해당 회사에 등록된 로고/심볼 파일을 STOR서버에서 WAS로 다운로드 하고, 그 성공여부를 반환한다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public boolean DonwnloadFormEnvFile(String compId) throws Exception {
	
	List<FormEnvVO> formEnvVoList = new ArrayList<FormEnvVO>();
	FormEnvVO formEnvVO = new FormEnvVO();
	formEnvVO.setCompId(compId);
	formEnvVoList = (List<FormEnvVO>) commonDAO.getList("env.selectFormEnvFileList", formEnvVO);
	int formEnvVoListSize = formEnvVoList.size();
	boolean result = false;
	
	if (formEnvVoListSize>0) {
        	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");        	
        	String[] fileIds = new String[formEnvVoListSize];
        	String[] filePaths = new String[formEnvVoListSize];
        	JStor jStor = new JStor();
        	try {
        	    for (int i=0; i<formEnvVoListSize; i++) {
        		FormEnvVO vo = new FormEnvVO();
        		vo = formEnvVoList.get(i);
        		fileIds[i] = vo.getEnvInfo();
        		filePaths[i] = uploadTemp + "/" + compId + "/" + vo.getRemark();
        	    }
        	    jStor.getFiles(fileIds, filePaths);
        	    result = true;	    
        	} catch (Exception e) {
        	    String message = "failed File Download";
               	    logger.error(message);
               	    throw e;	    
        	}
	}

	return result;
    }
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본여부 전체를 'N'으로 설정한다.
     * </pre>
     * @param compId
     * @param envType
     *  회사ID, 양식환경타입을 파라미터로 받아서 해당 양식환경타입의 기본여부를 사용안함으로 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateFormEnvDefaultYnAllN(String compId, String registerDeptId, String envType) throws Exception {
	FormEnvVO vo = new FormEnvVO();
	vo.setCompId(compId);
	vo.setEnvType(envType);
	commonDAO.modify("env.updateFormEnvDefaultYnAllN", vo);
    }
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본여부를 수정한다.
     * </pre>
     * @param compId
     * @param formEnvId
     *  회사ID, 양식환경ID를 파라미터로 받아서 해당 양식환경 사용여부를 사용으로 업데이트 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateFormEnvDefault(String compId, String registerDeptId, String formEnvId) throws Exception {
	FormEnvVO vo = new FormEnvVO();
	vo.setCompId(compId);
	vo.setFormEnvId(formEnvId);
	commonDAO.modify("env.updateFormEnvDefault", vo);	
    }
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본설정을 수정한다.
     *  기본 설정된 값을 결재옵션값에 등록.
     * </pre>
     * @param compId
     * @param envType
     * @param formEnvId
     * @param optionValue
     *  회사ID, 양식환경타입, 양식환경ID, 옵션값을 파라미터로 받는다.
     *  해당 양식환경타입의 사용여부를 모두 사용안함으로 업데이트 한다.
     *  해당 양식환경ID의 사용여부를 사용으로 업데이트 한다.
     *  기본설정된 값을 해당 옵션값에 업데이트 한다.
     *  캠페인은 기본설정된 문구를 옵션값에 넣고, 로고/심볼은 기본설정된 로고/심볼의 이미지 파일ID를 옵션값에 넣는다.
     * @throws Exception
     * @see  
     *
     */
    public void updateFormEnvDefaultSet(String compId, String registerDeptId, String envType, String formEnvId, String optionValue) throws Exception {
	String optionId = "";
	if ("FET001".equals(envType)) {		// 로고
	    optionId = "OPT328";
	} else if ("FET002".equals(envType)) {	// 심볼
	    optionId = "OPT329";
	} else if ("FET003".equals(envType)) {	// 상부캠페인
	    optionId = "OPT323";
	} else if ("FET004".equals(envType)) {	// 하부캠페인
	    optionId = "OPT324";
	}
	this.updateFormEnvDefaultYnAllN(compId, registerDeptId, envType);
	this.updateFormEnvDefault(compId, registerDeptId, formEnvId);
	this.updateOptionValue(compId, optionId, optionValue);
    }    
    
    /**
     * 
     * <pre> 
     *  캠페인을 등록한다.
     * </pre>
     * @param vo
     *  양식환경VO를 파라미터로 받아서 insert 한다.
     * @throws Exception
     * @see  
     *
     */
    public void insertFormEnvCamp(FormEnvVO vo) throws Exception {
	if ("Y".equals(vo.getDefaultYn())) {
	    this.updateFormEnvDefaultYnAllN(vo.getCompId(), vo.getRegisterDeptId(), vo.getEnvType());
	    commonDAO.insert("env.insertFormEnv", vo);
	    if ("FET003".equals(vo.getEnvType())) {
		this.updateOptionValue(vo.getCompId(), "OPT323", vo.getEnvInfo()); // 상부캠페인
	    } else if ("FET004".equals(vo.getEnvType())) {
		this.updateOptionValue(vo.getCompId(), "OPT324", vo.getEnvInfo()); // 하부캠페인
	    }
	} else {
	    vo.setDefaultYn("N");
	    commonDAO.insert("env.insertFormEnv", vo);
	}
    }
    
    /**
     * 
     * <pre> 
     *  로고/심볼을 등록한다.
     * </pre>
     * @param vo
     *  양식환경VO를 파라미터로 받아서 insert 한다.
     * @throws Exception
     * @see  
     *
     */
    public void insertFormEnvLS(FormEnvVO vo) throws Exception {
	String fileId = "";
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");	
	String filePath = uploadTemp + "/" + vo.getCompId() + "/" + vo.getRemark();
	JStor jStor = new JStor();
	
	try {
	    fileId = jStor.regFile(filePath);
	} catch (Exception e) {
       	    String message = "failed UploadAttach : error jStor.regFile";
       	    logger.error(message);
       	    throw e;
	}
	if (fileId != null) {
	    vo.setEnvInfo(fileId);
	} else {
	    String message = "failed UploadAttach : fileId is null";
	    logger.error(message);
	    throw new Exception();
	}
	
	if ("Y".equals(vo.getDefaultYn())) {
	    this.updateFormEnvDefaultYnAllN(vo.getCompId(), vo.getRegisterDeptId(), vo.getEnvType());
	    commonDAO.insert("env.insertFormEnv", vo);
	    if ("FET001".equals(vo.getEnvType())) {
		this.updateOptionValue(vo.getCompId(), "OPT328", vo.getEnvInfo());
	    } else if ("FET002".equals(vo.getEnvType())) {
		this.updateOptionValue(vo.getCompId(), "OPT329", vo.getEnvInfo());
	    }
	} else {
	    vo.setDefaultYn("N");
	    commonDAO.insert("env.insertFormEnv", vo);
	}
    }
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼을 삭제한다.
     * </pre>
     * @param compId
     * @param formEnvId
     *  회사ID, 양식환경ID를 파라미터로 받아서 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deleteFormEnv(String compId, String formEnvId) throws Exception {
	FormEnvVO vo = new FormEnvVO();
	vo.setCompId(compId);
	vo.setFormEnvId(formEnvId);
	commonDAO.delete("env.deleteFormEnv", vo);
    }

    /** 
     * 
     * <pre> 
     *  소속된 부서의 발신명의 리스트를 가져온다.
     * </pre>
     * @param compId
     * @param deptId
     *  회사ID, 부서ID를 파라미터로 받는다.
     * @return List<SenderTitleVO>
     *  소속된 부서의 발신명의를 발신명의VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<SenderTitleVO> selectSenderTitleList(String compId, String deptId, String groupType) throws Exception {
		SenderTitleVO vo = new SenderTitleVO();
		
		vo.setCompId(compId);
		vo.setDeptId(deptId);
		vo.setGroupType(groupType);
		
		return (List<SenderTitleVO>) commonDAO.getList("env.selectSenderTitleList", vo);
    }
    
    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<SenderTitleVO> selectSenderTitleListResource(String compId, String deptId, String groupType, String langType) throws Exception {
		SenderTitleVO vo = new SenderTitleVO();
		
		vo.setCompId(compId);
		vo.setDeptId(deptId);
		vo.setGroupType(groupType);
		vo.setLangType(langType);
		
		return (List<SenderTitleVO>) commonDAO.getList("env.selectSenderTitleListResource", vo);
    }    
    
    /**
     * 
     * <pre> 
     *  소속된 부서와 상위부서의 발신명의 리스트를 가져온다.
     * </pre>
     * @param session
     *  세션값을 파라미터로 받는다.
     * @return List<SenderTitleVO>
     *  소속된 부서와 상위부서의 발신명의를 발신명의VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<SenderTitleVO> selectSenderTitleAllList(String compId, String[] deptList, String langType) throws Exception {
	SenderTitleVO vo = new SenderTitleVO();
	int deptListSize = deptList.length;
	StringBuffer buff = new StringBuffer();
	
        for (int i=0; i<deptListSize; i++) {
            if (i!=(deptListSize-1)) {
        	buff.append(deptList[i]+",");
            } else {
        	buff.append(deptList[i]);
            }
        }
        String deptIds = ListUtil.TransString(buff.toString());
	vo.setCompId(compId);
	vo.setDeptIds(deptIds);
	vo.setLangType(langType);
	return (List<SenderTitleVO>) commonDAO.getList("env.selectSenderTitleAllList", vo);
    }
    
    /**
     * 
     * <pre> 
     *  소속된 부서의 기본설정된 발신명의를 가져온다.
     * </pre>
     * @param compId
     * @param deptId
     *  회사ID, 부서ID를 파라미터로 받는다.
     * @return senderTitle
     *  소속된 부서의 기본 발신명의를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public String selectDefaultSenderTitle(String compId, String deptId, String langType) throws Exception {
	SenderTitleVO vo = new SenderTitleVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	vo.setLangType(langType);
	vo = (SenderTitleVO) commonDAO.get("env.selectDefaultSenderTitleVO", vo);
	if (vo == null)
	    return "";
	else
	    return vo.getSenderTitle();	
    }
    
    /**
     * 
     * <pre> 
     *  발신명의 기본여부 전체를 'N'으로 설정한다.
     * </pre>
     * @param compId
     * @param deptId
     *  회사ID, 부서ID를 파라미터로 받아서 부서 발심녕의 기본여부를 모두 'N'으로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateSenderTitleDefaultYnAllN(String compId, String deptId, String groupType) throws Exception {
	SenderTitleVO vo = new SenderTitleVO();
	vo.setCompId(compId);
	vo.setDeptId(deptId);
	vo.setGroupType(groupType);
	commonDAO.modify("env.updateSenderTitleDefaultYnAllN", vo);
    }
    
    /**
     * 
     * <pre> 
     *  발신명의 기본여부를 수정한다.
     * </pre>
     * @param compId
     * @param senderTitleId
     *  회사ID, 발신명의ID를 파라미터로 받아서 해당 발신명의 기본여부를 'Y'로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public int updateSenderTitleDefaultYn(String compId, String senderTitleId) throws Exception {
	SenderTitleVO vo = new SenderTitleVO();
	vo.setCompId(compId);
	//vo.setDeptId(deptId);
	vo.setSenderTitleId(senderTitleId);
	//vo.setGroupType(groupType);
	return commonDAO.modify("env.updateSenderTitleDefaultYn", vo);
    }
    
    /**
     * 
     * <pre> 
     *  발신명의 기본값을 수정한다.
     * </pre>
     * @param compId
     * @param deptId
     * @param senderTitle
     * @param groupType
     *  회사ID, 부서ID, 발신명의, 그룹사용구분을 파라미터로 받는다.
     *  부서 발신명의 기본여부를 모두 'N'으로 update 한다.
     *  해당 발신명의 기본여부를 'Y'로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public boolean updateSenderTitleDefaultSet(String compId, String deptId, String senderTitleId, String groupType) throws Exception {
	boolean result = true;
	int intResult = 0;
	this.updateSenderTitleDefaultYnAllN(compId, deptId, groupType);
	intResult = this.updateSenderTitleDefaultYn(compId, senderTitleId);
	if (intResult == 0) {
	    result = false;
	    throw new Exception("["+this.getClass().getName()+"] Error : update fail!");
	}
	return result;
    }
    
    /**
     * 
     * <pre> 
     *  발신명의를 등록한다.
     * </pre>
     * @param vo
     *  발신명의 vo를 파라미터로 받는다.
     * @return result
     *  insert 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public int insertSenderTitle(SenderTitleVO vo) throws Exception {
	int result;
	if ("Y".equals(vo.getDefaultYn())) {
	    this.updateSenderTitleDefaultYnAllN(vo.getCompId(), vo.getDeptId(), vo.getGroupType());
	    result = commonDAO.insert("env.insertSenderTitle", vo);
	} else {
	    vo.setDefaultYn("N");
	    result = commonDAO.insert("env.insertSenderTitle", vo);
	}
	return result;
    }
    
    /**
     * 
     * <pre> 
     *  발신명의를 삭제한다.
     * </pre>
     * @param compId
     * @param senderTitleID
     *  회사ID, 발신명의ID를 파라미터로 받아서 해당 발신명의를 삭제한다.
     * @return result
     *  delete 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public int deleteSenderTitle(String compId, String senderTitleId) throws Exception {
	int result;
	SenderTitleVO vo = new SenderTitleVO();
	vo.setCompId(compId);
	vo.setSenderTitleId(senderTitleId);
	result = commonDAO.delete("env.deleteSenderTitle", vo);
	return result;
    }
    
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     *  회사ID, 등록자ID, 등록자부서ID, 그룹사용구분을 파라미터로 받는다.
     * @return List<LineGroupVO>
     *  그룹사용구분에 따른 결재경로그룹을 결재경로그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<LineGroupVO> listAppLineGroup(String compId, String registerId, String registerDeptId, String groupType) throws Exception {
	String gut003 = appCode.getProperty("GUT003", "GUT003", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "GUT004", "GUT"); // 개인
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setGroupType(groupType);
	if (gut004.equals(groupType)) {
	    vo.setRegisterId(registerId);
	} else if (gut003.equals(groupType)) {
	    vo.setRegisterDeptId(registerDeptId);
	}
	return (List<LineGroupVO>) commonDAO.getList("env.listAppLineGroup", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     *  회사ID, 등록자ID, 등록자부서ID, 그룹사용구분을 파라미터로 받는다.
     * @return List<LineGroupVO>
     *  그룹사용구분에 따른 결재경로그룹을 결재경로그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<LineGroupVO> listAppLineGroup(String compId, String registerId, String registerDeptId, String groupType, String usingType) throws Exception {
	String gut003 = appCode.getProperty("GUT003", "GUT003", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "GUT004", "GUT"); // 개인
	
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setGroupType(groupType);
	vo.setUsingType(usingType);
	if (gut004.equals(groupType)) {
	    vo.setRegisterId(registerId);
	} else if (gut003.equals(groupType)) {
	    vo.setRegisterDeptId(registerDeptId);
	}
	return (List<LineGroupVO>) commonDAO.getList("env.listAppLineGroup", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.(결재경로그룹 선택 팝업)
     *  usingType - DPI001:생산, DPI002:접수
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param usingType
     *  회사ID, 등록자ID, 등록자부서ID, 사용대상유형을 파라미터로 받는다.
     * @return List<LineGroupVO>
     *  개인 결재경로그룹과 소속된 부서의 부서 결재경로그룹을 결재경로그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<LineGroupVO> listAppLineGroupP(String compId, String registerId, String registerDeptId, String usingType) throws Exception {
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setRegisterId(registerId);
	vo.setRegisterDeptId(registerDeptId);
	vo.setUsingType(usingType);
	return (List<LineGroupVO>) commonDAO.getList("env.listAppLineGroupP", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param lineGroupId
     *  회사ID, 결재경로그룹ID를 파라미터로 받는다.
     * @return List<LineUserVO>
     *  해당 결재경로그룹에 등록된 결재경로를 결재자VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<LineUserVO> listAppLine(String compId, String lineGroupId) throws Exception {
	LineUserVO vo = new LineUserVO();
	vo.setCompId(compId);
	vo.setLineGroupId(lineGroupId);
	return (List<LineUserVO>) commonDAO.getList("env.listAppLine", vo);
    }
    
    /**
     * 
     * <pre> 
     *  기본결재경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param usingType
     * @param registerId
     * @param registerDeptId
     *  회사ID, 사용대상유형, 등록자ID, 등록자부서ID를 파라미터로 받는다.
     * @return List<LineUserVO>
     *  기본결재경로를 결재자VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<LineUserVO> listDefaultAppLine(String compId, String usingType, String registerId, String registerDeptId) throws Exception {
	String gut003 = appCode.getProperty("GUT003", "", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "", "GUT"); // 개인
	LineGroupVO vo = new LineGroupVO();	
	List<LineUserVO> voList = new ArrayList();
	vo.setCompId(compId);
	vo.setUsingType(usingType);
	vo.setGroupType(gut004);
	vo.setRegisterId(registerId);	
	voList = commonDAO.getList("env.listDefaultAppLine", vo);
	if (voList.size() == 0) {
	    LineGroupVO vo2 = new LineGroupVO();
	    vo2.setCompId(compId);
	    vo2.setUsingType(usingType);
	    vo2.setGroupType(gut003);
	    vo2.setRegisterDeptId(registerDeptId);
	    voList = commonDAO.getList("env.listDefaultAppLine", vo2);
	}
	return voList;
    }
    
    /**
     * 
     * <pre> 
     *  기본결재경로를 설정한다.
     * </pre>
     * @param compId
     * @param usingType
     * @param registerId
     * @param lineGroupId
     * @param groupType
     *  회사ID, 사용대상유형, 등록자ID, 결재경로그룹ID, 그룹사용구분을 파라미터로 받아서 해당결재경로그룹을 기본설정으로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateDefaultAppLineSet(String compId, String usingType, String registerId, String lineGroupId, String groupType) throws Exception {
	this.updateAppLineDefaultYnAllN(compId, usingType, registerId, groupType);
	this.updateAppLineDefaultYn(compId, lineGroupId);
    }
    
    /**
     * 
     * <pre> 
     *  결재경로그룹 기본여부 전체를 'N'으로 설정한다.
     * </pre>
     * @param compId
     * @param usingType
     * @param registerId
     * @param groupType
     *  회사ID, 사용대상유형, 등록자ID, 그룹사용구분을 파라미터로 받아서 결재경로그룹 기본여부를 모두 'N'으로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateAppLineDefaultYnAllN(String compId, String usingType, String registerId, String groupType) throws Exception {
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setUsingType(usingType);
	vo.setRegisterId(registerId);
	vo.setGroupType(groupType);
	commonDAO.modify("env.updateDefaultAppLineAllN", vo);
    }
    
    /**
     * 
     * <pre> 
     *  기본  결재경로그룹을 설정한다.
     * </pre>
     * @param compId
     * @param lineGroupId
     *  회사ID, 결재경로그룹ID를 파라미터로 받아서 해당 결재경로그룹을 기본여부를 'Y'로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public void updateAppLineDefaultYn(String compId, String lineGroupId) throws Exception {
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setLineGroupId(lineGroupId);
	commonDAO.modify("env.updateDefaultAppLineGroup", vo);
    }
    
    /**
     * 
     * <pre> 
     *  기본  결재경로그룹을 해제한다.
     * </pre>
     * @param compId
     * @param lineGroupId
     *  회사ID, 결재경로그룹ID를 파라미터로 받아서 해당 결재경로그룹을 기본여부를 'N'으로 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public void cancelAppLineDefaultYn(String compId, String lineGroupId) throws Exception {
	LineGroupVO vo = new LineGroupVO();
	vo.setCompId(compId);
	vo.setLineGroupId(lineGroupId);
	commonDAO.modify("env.cancelDefaultAppLineGroup", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로 목록을 삭제한다.
     * </pre>
     * @param compId
     * @param lineGroupId
     *  회사ID, 결재경로그룹ID를 파라미터로 받아서 해당 결재경로그룹을 삭제한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void deleteAppLineGroup(String compId, String lineGroupId) throws Exception {
	LineUserVO vo = new LineUserVO();
	vo.setCompId(compId);
	vo.setLineGroupId(lineGroupId);
	commonDAO.delete("env.deleteAppLine", vo);
	commonDAO.delete("env.deleteAppLineGroup", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  결재경로그룹VO와 결재자 리스트를 파라미티로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void insertAppLineGroup(LineGroupVO gvo, List uvoList) throws Exception {
	commonDAO.insert("env.insertAppLineGroup", gvo);
	commonDAO.insertList("env.insertAppLineGroupUser", uvoList);
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  결재경로그룹VO, 결재자 리스트를 파라미터로 받는다.
     *  결재경로그룹 정보를 update 한다.
     *  기존 결재경로그룹의 결재자 정보를 삭제한다.
     *  수정된 결재경로그룹의 결재자 정보를 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void updateAppLineGroup(LineGroupVO gvo, List uvoList) throws Exception {
	commonDAO.modify("env.updateAppLineGroup", gvo);
	commonDAO.delete("env.deleteAppLine", uvoList.get(0));
	commonDAO.insertList("env.insertAppLineGroupUser", uvoList);
    }
    
    
    /** 
     * 
     * <pre> 
     *  수신자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  결재경로그룹VO, 결재자 리스트를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void insertRecvGroup(RecvGroupVO gvo, List uvoList) throws Exception {
	//commonDAO.insert("env.insertRecvGroup", gvo);
	//commonDAO.insertList("env.insertRecvGroupUser", uvoList);
	this.insertRecvGroup(gvo);
	this.insertRecvGroupUser(uvoList);
    }
    
    

    /** 
     * 
     * <pre> 
     *  수신자그룹을 등록한다.(그룹 등록)
     * </pre>
     * @param gvo
     *  결재경로그룹VO를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void insertRecvGroup(RecvGroupVO gvo) throws Exception {
	commonDAO.insert("env.insertRecvGroup", gvo);
    }
    

    /** 
     * 
     * <pre> 
     *  수신자를 등록한다.(수신자 등록)
     * </pre>
     * @param uvoList
     *  결재자 리스트를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void insertRecvGroupUser(List uvoList) throws Exception {
	commonDAO.insertList("env.insertRecvGroupUser", uvoList);
    }
    

    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹관리, 문서책임자)
     * </pre>
     * @param compId
     * @param registerId
     * @param groupType
     *  회사ID, 등록자ID, 그룹사용구분을 파라미터로 받는다.
     * @return List<RecvGroupVO>
     *  수신자그룹을 수신자그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvGroupVO> listRecvGroup(String compId, String registerDeptId, String groupType) throws Exception {
	String gut003 = appCode.getProperty("GUT003", "", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "", "GUT"); // 개인
	RecvGroupVO vo = new RecvGroupVO();
	vo.setCompId(compId);	
	vo.setGroupType(groupType);
	if (gut003.equals(groupType)) {
	    vo.setRegisterDeptId(registerDeptId);
	} else if (gut004.equals(groupType)) {
	    vo.setRegisterId(registerDeptId);
	}	
	return (List<RecvGroupVO>) commonDAO.getList("env.listRecvGroup", vo);
    }

    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹관리, 회사관리자)
     * </pre>
     * @param compId
     * @param groupType
     *  회사ID, 그룹사용구분을 파라미터로 받는다.
     * @return List<RecvGroupVO>
     *  수신자그룹을 수신자그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvGroupVO> listRecvGroup(String compId, String groupType) throws Exception {
	RecvGroupVO vo = new RecvGroupVO();
	vo.setCompId(compId);
	vo.setGroupType(groupType);
	return (List<RecvGroupVO>) commonDAO.getList("env.listRecvGroup", vo);
    }
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹선택 팝업)
     * </pre>
     * @param compId
     * @param registerDeptId
     * @param registerId
     *  회사ID, 등록자부서ID를 파라미터로 받는다.
     * @return List<RecvGroupVO>
     *  개인 수신자그룹, 부서 수신자그룹, 회사 수신자그룹을 수신자그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvGroupVO> listRecvGroupP(String compId, String registerDeptId, String registerId) throws Exception {
	RecvGroupVO vo = new RecvGroupVO();
	vo.setCompId(compId);
	vo.setRegisterDeptId(registerDeptId);
	vo.setRegisterId(registerId);
	return (List<RecvGroupVO>) commonDAO.getList("env.listRecvGroupP", vo);
    }
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹선택 팝업)
     * </pre>
     * @param compId
     * @param registerDeptId
     * @param registerId
     * @param searchDept
     *  회사ID, 등록자부서ID, 검색부서명을 파라미터로 받는다.
     * @return List<RecvGroupVO>
     *  개인 수신자그룹, 부서 수신자그룹, 회사 수신자그룹을 수신자그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvGroupVO> searchRecvGroupP(String compId, String registerDeptId, String registerId, String searchDept) throws Exception {
	RecvGroupVO vo = new RecvGroupVO();
	vo.setCompId(compId);
	vo.setRegisterDeptId(registerDeptId);
	vo.setRegisterId(registerId);
	vo.setSearchDept(searchDept);
	
	return (List<RecvGroupVO>) commonDAO.getList("env.searchRecvGroupP", vo);
    }
    
    /**
     * 
     * <pre> 
     *  수신자경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param recvGroupId
     *  회사ID, 수신자그룹ID를 파라미터로 받는다.
     * @return List<RecvUserVO>
     *  해당 수신자그룹의 수신자 목록을 수신자VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvUserVO> listRecvLine(String compId, String recvGroupId) throws Exception {
	RecvUserVO vo = new RecvUserVO();
	vo.setCompId(compId);
	vo.setRecvGroupId(recvGroupId);
	return (List<RecvUserVO>) commonDAO.getList("env.listRecvLine", vo);
    }
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 삭제한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     *  회사ID, 수신자그룹ID를 파라미터로 받아서 해당 수신자그룹과 수신자 목록을 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deleteRecvGroup(String compId, String recvGroupId) throws Exception {
	RecvUserVO vo = new RecvUserVO();
	vo.setCompId(compId);
	vo.setRecvGroupId(recvGroupId);
	commonDAO.delete("env.deleteRecvLine", vo);
	commonDAO.delete("env.deleteRecvGroup", vo);
    }
    
    /**
     * 
     * <pre> 
     *  수신자를 삭제한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     *  회사ID, 수신자그룹ID를 파라미터로 받아서 해당 수신자그룹을 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deleteRecvGroupUser(String compId, String recvGroupId) throws Exception {
	RecvUserVO vo = new RecvUserVO();
	vo.setCompId(compId);
	vo.setRecvGroupId(recvGroupId);
	commonDAO.delete("env.deleteRecvLine", vo);
    }

    /**
     * 
     * <pre> 
     *  수신자그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  결재경로그룹VO, 결재자 리스트를 파라미터로 받는다.
     *  결재경로그룹을 update 한다.
     *  해당 결재경로그룹의 결재자 목록을 삭제한다.
     *  수정된 결재자 목록을 insert 한다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void updateRecvGroup(RecvGroupVO gvo, List uvoList) throws Exception {
	commonDAO.modify("env.updateRecvGroup", gvo);
	commonDAO.delete("env.deleteRecvLine", uvoList.get(0));
	commonDAO.insertList("env.insertRecvGroupUser", uvoList);
    }
    
    /**
     * 
     * <pre> 
     *  수신자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     *  수신자 목록을 리스트로 받는다.
     * @return List<RecvUserVO>
     *  등록된 수신자 정보가 현재 조직정보와 일치하는지 비교한다.
     *  정보 변경여부를 넘겨받은 리스트 각 항목에 changYn에 'Y/N'으로 표시하고 다시 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public List<RecvUserVO> checkOrgRecv(List<RecvUserVO> voList) throws Exception {
	
		String det002 = appCode.getProperty("DET002", "", "DET"); // 대내
		String det003 = appCode.getProperty("DET003", "", "DET"); // 대외
		String det005 = appCode.getProperty("DET005", "", "DET"); // 수기 행정기관
		String det006 = appCode.getProperty("DET006", "", "DET"); // 수기 일반기관
		String det007 = appCode.getProperty("DET007", "", "DET"); // 수기 민원인
		String det011 = appCode.getProperty("DET011", "", "DET"); // 행정기관
		String dru001 = appCode.getProperty("DRU001", "", "DRU"); // 부서
		String dru002 = appCode.getProperty("DRU002", "", "DRU"); // 사람
		
		RecvUserVO recvVO;
		OrganizationVO orgVO1;
		OrganizationVO orgVO2;
		UserVO userVO = new UserVO();
		List<RecvUserVO> result = new ArrayList<RecvUserVO>();
		List<String> oldLdapList = new ArrayList<String>();
		
		int voListSize = voList.size();
		for (int i=0; i<voListSize; i++) {
		    recvVO = voList.get(i);
		    String changeYn = "N";
		    
		    if (det002.equals(recvVO.getEnfType()) || det003.equals(recvVO.getEnfType()))  // 대내&대외
		    {
				orgVO1 = orgService.selectOrganization(recvVO.getRecvDeptId());
				
				if (orgVO1 == null) {
				    changeYn = "Y";
				} else {
					String oldDeptName = recvVO.getRecvDeptName();
					String curDeptName = orgVO1.getOrgName();
					String outgoingName = orgVO1.getOutgoingName();
					if (!oldDeptName.equals(curDeptName)) {
						changeYn = "Y";
						if (det003.equals(recvVO.getEnfType()) && oldDeptName.equals(outgoingName)) {
							changeYn = "N";
						}
					}
				}
				
				if (dru001.equals(recvVO.getReceiverType()) && recvVO.getRefDeptId() != null && !"".equals(recvVO.getRefDeptId())) { // 부서		    
				    orgVO2 = orgService.selectOrganization(recvVO.getRefDeptId());	    
				    if (orgVO2 == null) {
				    	changeYn = "Y";
				    } else {
						String oldDeptName = recvVO.getRefDeptName();
						String curDeptName = orgVO2.getOrgName();
						if (!oldDeptName.equals(curDeptName)) {
							changeYn = "Y";
						}
				    }
				} else if (dru002.equals(recvVO.getReceiverType())) { // 사람
				    userVO = orgService.selectUserByUserId(recvVO.getRecvUserId());
				    
				    if(userVO == null || "".equals(StringUtil.null2str(userVO.getDeptID(), ""))){
						// 수신자 삭제
						deleteRecvGroupUser(recvVO);
						continue;
				    }
				    
				    if (userVO == null || !(recvVO.getRecvDeptId()).equals(userVO.getDeptID())) {
				    	changeYn = "Y";
				    }
				}
				recvVO.setChangeYn(changeYn);
			    result.add(recvVO);
		    } 
		    
			if (det005.equals(recvVO.getEnfType()) || det006.equals(recvVO.getEnfType()) || det007.equals(recvVO.getEnfType()))  
			{
				result.add(recvVO);
			}
		    
		    if (det011.equals(recvVO.getEnfType()))  // 행정기관
		    {
		    	String refDeptId = recvVO.getRefDeptId();
		    	String refDeptName = recvVO.getRefDeptName();		    	
		    	if ( refDeptId == null || refDeptId.equals("") ) refDeptId = "nullString";
		    	if ( refDeptName == null || refDeptName.equals("") ) refDeptName = "nullString";
		    	oldLdapList.add(recvVO.getRecvDeptId() + String.valueOf((char)2) + recvVO.getRecvDeptName() + String.valueOf((char)2) + refDeptId + String.valueOf((char)2) + refDeptName);
		    }
		}
		
		int ldapReqSize = oldLdapList.size();
		
		if ( ldapReqSize > 0 )
		{
			List<String> ldapRes = orgService.getLDAPSyncResultByOldList(oldLdapList);
			
	    	String changeYn = "N";
	    	String resInfo[] = null;
			
			for (int i=0; i<voListSize; i++) 
			{
			    recvVO 		= voList.get(i);			    
	    		resInfo 	= ldapRes.get(i).split(String.valueOf((char)2));
	    		changeYn 	= resInfo[2];
	    		
	    		if ( changeYn.equals("Y") )
	    		{
	    			recvVO.setChangeYn(changeYn);
	    		}
			    
			    recvVO.setChangeYn(changeYn);
			    result.add(recvVO);
			}
		}
		
		return result;
    }
    
    
    
    
    /**
     * 
     * <pre> 
     *  수신자 그룹의 사용자를 수정한다.
     * </pre>
     * @param recvVO
     * @throws Exception
     * @see  
     *
     */
    public int updateRecvGroupUser(RecvUserVO recvVO) throws Exception {
	return commonDAO.modify("env.updateRecvGroupUser", recvVO);
    }
    
   
    
    /**
     * 
     * <pre> 
     *  수신자 그룹의 사용자를 삭제한다.
     * </pre>
     * @param recvVO
     * @throws Exception
     * @see  
     *
     */
    public int deleteRecvGroupUser(RecvUserVO recvVO) throws Exception {
	return commonDAO.delete("env.deleteRecvGroupUser", recvVO);
    }
    
    /**
     * 
     * <pre> 
     *  결재자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     *  결재자 목록을 리스트로 받는다.
     * @return List<LineUserVO>
     *  등록된 결재자 정보가 현재 조직정보와 일치하는지 비교한다.
     *  정보 변경여부를 넘겨받은 리스트 각 항목에 changYn에 'Y/N'으로 표시하고 다시 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public List<LineUserVO> checkOrgAppLine(List<LineUserVO> voList) throws Exception {
	
	LineUserVO appLineVO;
	UserVO userVO;
	OrganizationVO orgVO;
	List<LineUserVO> result = new ArrayList<LineUserVO>();
	
	String art032 = appCode.getProperty("ART032", "", "ART"); // 부서협조
	String art041 = appCode.getProperty("ART041", "", "ART"); // 부서감사
	
	String roleId21 = AppConfig.getProperty("role_auditor", "", "role"); // 감사자
	String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
	String roleId29 = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 일상감사 대상자
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String roleId32 = AppConfig.getProperty("role_officer", "", "role"); // 임원
	
	int voListSize = voList.size();
	for (int i=0; i<voListSize; i++) {
	    appLineVO = voList.get(i);
	    String changeYn = "N";
	    String roleCodes = "";
	    
	    if (art032.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
		orgVO = orgService.selectOrganization(appLineVO.getApproverDeptId());	    
		if (orgVO == null || !(appLineVO.getApproverDeptName()).equals(orgVO.getOrgName())) {
		    changeYn = "Y";
		}
	    } else {
		userVO = orgService.selectUserByUserId(appLineVO.getApproverId());
		if (userVO == null || !(appLineVO.getApproverDeptId()).equals(userVO.getDeptID()) || !(appLineVO.getApproverDeptName()).equals(userVO.getDeptName())) {
		    changeYn = "Y";
		}
		if (userVO != null && userVO.getRoleCodes() != null && !"".equals(userVO.getRoleCodes())) {
		    if (userVO.getRoleCodes().indexOf(roleId21) >= 0) {
			roleCodes += roleId21 + "^";
		    }
		    if (userVO.getRoleCodes().indexOf(roleId25) >= 0) {
			roleCodes += roleId25 + "^";
		    }
		    if (userVO.getRoleCodes().indexOf(roleId32) >= 0) {
			roleCodes += roleId32 + "^";
		    }
		    if (userVO.getRoleCodes().indexOf(roleId31) >= 0) {
			roleCodes += roleId31 + "^";
		    }
		    if (userVO.getRoleCodes().indexOf(roleId29) >= 0) {
			roleCodes += roleId29 + "^";
		    }
		}
		appLineVO.setApproverPos(userVO.getDisplayPosition());
	    }
	    if (!"".equals(roleCodes)) {
		roleCodes = roleCodes.substring(0, roleCodes.lastIndexOf("^"));
	    }
	    
	    appLineVO.setRoleCodes(roleCodes);
	    appLineVO.setChangeYn(changeYn);
	    result.add(appLineVO);	    
	}
	
	return voList;
    }
    
    /** 
     * 
     * <pre> 
     *  코드 목록을 가져온다.
     * </pre>
     * @param parentId
     *  상위ID를 파라미터로 받는다.
     * @return List<AppCodeVO>
     *  상위ID를 가지는 코드목록을 코드VO에 담아서 리스트로 넘긴다. 
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<AppCodeVO> listAppCode(String parentId) throws Exception {
	AppCodeVO vo = new AppCodeVO();
	vo.setParentId(parentId);
	return (List<AppCodeVO>) commonDAO.getList("env.listCode", vo);	
    }
    
    /**
     * 
     * <pre> 
     *  코드를 설명을 수정한다.
     * </pre>
     * @param codeId
     * @param description
     *  코드ID, 설명을 받아서 update 한다.
     * @throws Exception
     * @see  
     *
     */
    public int updateAppCode(String codeId, String description) throws Exception {
	AppCodeVO vo = new AppCodeVO();
	int result = 0;
	vo.setCodeId(codeId);
	vo.setDescription(description);
	result = commonDAO.modify("env.updateCode", vo);
	return result;
    }  
    
    /** 
     * 
     * <pre> 
     *  공람자그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     *  회사ID, 등록자ID, 등록자부서ID, 그룹사용구분을 파라미터로 받는다.
     * @return List<PubViewGroupVO>
     *  공람자그룹VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<PubViewGroupVO> listPubViewGroup(String compId, String registerId, String registerDeptId, String groupType) throws Exception {
	String gut003 = appCode.getProperty("GUT003", "", "GUT"); // 부서
	String gut004 = appCode.getProperty("GUT004", "", "GUT"); // 개인
	PubViewGroupVO vo = new PubViewGroupVO();
	vo.setCompId(compId);
	vo.setGroupType(groupType);
	if (gut004.equals(groupType)) {
	    vo.setRegisterId(registerId);
	} else if (gut003.equals(groupType)) {
	    vo.setRegisterDeptId(registerDeptId);
	}
	return (List<PubViewGroupVO>) commonDAO.getList("env.listPubViewGroup", vo);
    }
    
    
    /** 
     * 
     * <pre> 
     *  공람자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  공람자그룹VO, 공람자목록 리스트를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void insertPubViewGroup(PubViewGroupVO gvo, List uvoList) throws Exception {
	this.insertPubViewGroup(gvo);
	this.insertPubViewGroupUser(uvoList);
    }
    
    

    /** 
     * 
     * <pre> 
     *  공람자그룹을 등록한다.
     * </pre>
     * @param gvo
     *  공람자그룹VO를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void insertPubViewGroup(PubViewGroupVO gvo) throws Exception {
	commonDAO.insert("env.insertPubViewGroup", gvo);
    }
    

    /** 
     * 
     * <pre> 
     *  공람자를 등록한다.
     * </pre>
     * @param uvoList
     *  공람자목록 리스트를 파라미터로 받아서 insert 한다.
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void insertPubViewGroupUser(List uvoList) throws Exception {
	commonDAO.insertList("env.insertPubViewGroupUser", uvoList);
    }
    
    /**
     * 
     * <pre> 
     *  공람자 목록을 가져온다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     *  회사ID, 공람자그룹ID를 파라미터로 받는다.
     * @return List<PubViewUserVO>
     *  해당 공람자그룹의 공람자 목록을 공람자VO에 담아서 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<PubViewUserVO> listPubViewLine(String compId, String pubviewGroupId) throws Exception {
	PubViewUserVO vo = new PubViewUserVO();
	vo.setCompId(compId);
	vo.setPubviewGroupId(pubviewGroupId);
	return (List<PubViewUserVO>) commonDAO.getList("env.listPubView", vo);
    }
    
    /**
     * 
     * <pre> 
     * 공람자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     *  공람자 모곡 리스트를 파라미터로 받는다.
     * @return List<PubViewUserVO>
     *  등록된 공람자 정보가 현재 조직정보와 일치하는지 비교한다.
     *  정보 변경여부를 넘겨받은 리스트 각 항목에 changYn에 'Y/N'으로 표시하고 다시 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public List<PubViewUserVO> checkOrgPubView(List<PubViewUserVO> voList) throws Exception {
	PubViewUserVO pubViewUserVO;
	UserVO userVO = new UserVO();
	List<PubViewUserVO> result = new ArrayList<PubViewUserVO>();
	
	int voListSize = voList.size();
	for (int i=0; i<voListSize; i++) {
	    pubViewUserVO = voList.get(i);
	    String changeYn = "N";
	    
		
	    userVO = orgService.selectUserByUserId(pubViewUserVO.getPubReaderId());
	    if(userVO == null || "".equals(StringUtil.null2str(userVO.getDeptID(), ""))){
		// 공람자 삭제
		deletePubViewGroupByReaderId(pubViewUserVO);
		continue;
	    }
	    
	    if (!(pubViewUserVO.getPubReaderDeptId()).equals(userVO.getDeptID()) || !(pubViewUserVO.getPubReaderDeptName()).equals(userVO.getDeptName())) {
		changeYn = "Y";
	    }
	    
	    
	    pubViewUserVO.setPubReaderPos(userVO.getDisplayPosition());
	    pubViewUserVO.setChangeYn(changeYn);
	    result.add(pubViewUserVO);	    
	}
	
	return voList;
    }
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 삭제한다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     *  회사ID, 공람자그룹ID를 파라미터로 받는다.
     *  해당 공람자그룹의 공람자 목록을 삭제한다.
     *  공람자그룹을 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deletePubViewGroup(String compId, String pubviewGroupId) throws Exception {
	PubViewUserVO vo = new PubViewUserVO();
	vo.setCompId(compId);
	vo.setPubviewGroupId(pubviewGroupId);
	commonDAO.delete("env.deletePubView", vo);
	commonDAO.delete("env.deletePubViewGroup", vo);
    }
    
    /**
     * 
     * <pre> 
     *  공람자를 삭제한다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     *  회사ID, 공람자그룹ID를 파라미터로 받아서 해당 공람자그룹의 공람자 목록을 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deletePubViewGroupUser(String compId, String pubviewGroupId) throws Exception {
	PubViewUserVO vo = new PubViewUserVO();
	vo.setCompId(compId);
	vo.setPubviewGroupId(pubviewGroupId);
	commonDAO.delete("env.deletePubView", vo);
    }
    
    /**
     * 
     * <pre> 
     *  해당 공람자를 삭제한다.
     * </pre>
     * @param pubViewUserVO
     * pubViewUserVO를 파라미터로 받아서 해당 공람자그룹의 해당 공람자를 삭제한다.
     * @throws Exception
     * @see  
     *
     */
    public void deletePubViewGroupByReaderId(PubViewUserVO pubViewUserVO) throws Exception {
	commonDAO.delete("env.deletePubViewGroupByReaderId", pubViewUserVO);
    }
    
    
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     *  공람자그룹VO, 공람자 리스트를 파라미터로 받는다.
     *  공람자그룹을 update 한다.
     *  해당 공람자그룹의 공람자 목록을 삭제한다.
     *  수정된 공람자 목록을 insert 한다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public void updatePubViewGroup(PubViewGroupVO gvo, List uvoList) throws Exception {
	commonDAO.modify("env.updatePubViewGroup", gvo);
	commonDAO.delete("env.deletePubView", uvoList.get(0));
	commonDAO.insertList("env.insertPubViewGroupUser", uvoList);
    }
    
    /**
     * 
     * <pre> 
     *  공람자그룹 목록을 가져온다.(공람자그룹선택 팝업)
     * </pre>
     * @param compId
     * @param registerDeptId
     *  회사ID, 등록자부서ID를 파라미터로 받는다.
     * @return List<PubViewGroupVO>
     *  개인 공람자그룹, 부서 공람자그룹 목록을 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<PubViewGroupVO> listPubViewGroupP(String compId, String registerDeptId, String registerId) throws Exception {
	PubViewGroupVO vo = new PubViewGroupVO();
	vo.setCompId(compId);
	vo.setRegisterId(registerId);
	vo.setRegisterDeptId(registerDeptId);
	return (List<PubViewGroupVO>) commonDAO.getList("env.listPubViewGroupP", vo);
    }
    
    
    /** 
     * 
     * <pre> 
     *  등록된 회기 전체 리스트를 가져온다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return list
     *  회기 정보를 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<PeriodVO> listAllPeriod(String compId) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	return (List<PeriodVO>) commonDAO.getList("env.listAllPeriod", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  유효한 회기 리스트를 가져온다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return list
     *  지난 회기부터 현재 회기까지의 정보를 리스트로 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<PeriodVO> listPeriod(String compId) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	return (List<PeriodVO>) commonDAO.getList("env.listPeriod", vo);
    }
    
    /** 
     * 
     * <pre> 
     *  현재 회기를 가져온다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return vo
     *  현재 날짜를 기준으로 현재회기 정보를 회기VO에 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public PeriodVO getCurrentPeriod(String compId) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	vo = (PeriodVO) commonDAO.get("env.currentPeriod", vo);
	
	if (vo == null) {
	    String periodType = AppConfig.getProperty("periodType", "Y", "etc");
	    int periodCount = 0;
	    while(vo == null && periodCount < 10) {
		this.insertPeriod(compId, periodType);
		periodCount++;
	    }
	}
	return vo;
    }
    
    /** 
     * 
     * <pre> 
     *  현재 회기를 가져온다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return periodId
     *  현재회기를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public String getCurrentPeriodStr(String compId) throws Exception {
    String periodId = "";
	PeriodVO vo = this.getCurrentPeriod(compId);
	
	if(vo != null) {
		periodId = vo.getPeriodId();
	}
	if(periodId.isEmpty()||periodId.length()==0){ //연도 못가져 올때 현재 연도 셋업
		Calendar oCalendar = Calendar.getInstance( );
		periodId = String.valueOf(oCalendar.get(Calendar.YEAR));
	}

	return periodId;
    }
    
    /** 
     * 
     * <pre> 
     *  최신 회기를 가져온다.
     * </pre>
     * @param compId
     *  회사ID를 파라미터로 받는다.
     * @return vo
     *  등록된 회기중 가장 나중에 등록된 회기 정보를 회기VO에 남아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public PeriodVO getLatestPeriod(String compId, String periodType) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	vo.setPeriodType(periodType);
	return (PeriodVO) commonDAO.get("env.latestPeriod", vo);
    }
    
    
    /** 
     * 
     * <pre> 
     *  해당 회기에 대한 정보를 가져온다.
     * </pre>
     * @param compId
     * @param periodId
     *  회사ID, 회기ID를 파라미터로 받는다.
     * @return vo
     *  해당회기에 대한 정보를 회기VO에 담아서 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public PeriodVO getPeriod(String compId, String periodId) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);	
	vo.setPeriodId(periodId);
	return (PeriodVO) commonDAO.get("env.selectPeriod", vo);
    }
    
    /**
     * 
     * <pre> 
     *  현재회기를 기준으로 다음회기가 없을 경우 다음회기를 추가한다.(편철에서 사용)
     * </pre>
     * @param compId
     * @throws Exception
     * @see  
     *
     */
    public int insertPeriodAuto(String compId) throws Exception { 
	return commonDAO.insert("env.insertPeriodAuto", this.getCurrentPeriod(compId));
    }
    
    /**
     * 
     * <pre> 
     *  넘겨받은 회기를 기준으로 다음회기가 없을 경우 다음회기를 추가한다.(편철에서 사용)
     * </pre>
     * @param compId, periodId
     * @throws Exception
     * @see  
     *
     */
    public int insertPeriodAuto(String compId, String periodId) throws Exception { 
	return commonDAO.insert("env.insertPeriodAuto", this.getPeriod(compId, periodId));
    }
    
    /**
     * 
     * <pre> 
     *  회기를 추가한다.
     * </pre>
     * @param compId, periodType
     *  회사ID, 기간유형 (Y:연도, P:회기)을 파라미터로 받는다.
     * @return
     *  성공여부(성공:1/실패:0)를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public int insertPeriod(String compId, String periodType) throws Exception { 
	
	String dateFormat = AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date");
	PeriodVO vo = this.getLatestPeriod(compId, periodType);
	int periodId = Integer.parseInt(vo.getPeriodId()) + 1;
	String startDate = DateUtil.getPreNextDate(vo.getEndDate(), 0, 0, 0, 0, 0, 1, dateFormat);
	String endDate = DateUtil.getPreNextDate(startDate, 1, 0, 0, 0, 0, -1, dateFormat);
	vo.setPeriodId(Integer.toString(periodId));
	vo.setLastUpdateDate(vo.getCurrentDate());
	vo.setStartDate(startDate);
	vo.setEndDate(endDate);
	
	//return commonDAO.insert("env.insertPeriodAuto", this.getLatestPeriod(compId, periodType));
	return commonDAO.insert("env.insertPeriod", vo);
    }
    
    /**
     * 
     * <pre> 
     *  회기를 삭제한다.
     * </pre>
     * @param compId, periodId
     *  회사ID, 회기ID를 파라미터로 받아서 해당회기 정보를 삭제한다.
     * @return int
     *  성공여부(성공:1/실패:0)를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public int deletePeriod(String compId, String periodId) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	vo.setPeriodId(periodId);
	return commonDAO.delete("env.deletePeriod", vo);
    }
    
    /**
     * 
     * <pre> 
     *  해당회기의 기간을 수정한다.(현재회기 다음부터 수정 가능, 현재회기는 종료일만 수정 가능)
     * </pre>
     * @param compId, periodId, startDate, endDate
     *  회사ID, 회기ID, 시작일, 종료일을 파라미터로 받아서 update 한다.
     * @return int
     *  성공여부(성공:1/실패:0)를 넘긴다.
     * @throws Exception
     * @see  
     *
     */
    public int updatePeriod(String compId, String periodId, String startDate, String endDate) throws Exception {
	PeriodVO vo = new PeriodVO();
	vo.setCompId(compId);
	vo.setPeriodId(periodId);
	if (!"".equals(startDate)) {
	    vo.setStartDate(startDate);
	}
	vo.setEndDate(endDate);
	vo.setLastUpdateDate(vo.getCurrentDate());
	return commonDAO.modify("env.updatePeriod", vo);
    }
    
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 조회한다.
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<RecvGroupVO> selectRecvGroup() throws Exception {
	return commonDAO.getList("env.selectRecvGroup", "");
    }
    
    /**
     * 
     * <pre> 
     *  문서공유부서 목록을 조회한다.
     * </pre>
     * @param shareDocDeptVO.
     * @return List<ShareDocDeptVO>
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<ShareDocDeptVO> selectShareDeptList(ShareDocDeptVO shareDocDeptVO) throws Exception {
	return commonDAO.getList("env.selectShareDept", shareDocDeptVO);
    }
    
    /**
     * 
     * <pre> 
     *  문서공유부서를 등록한다.
     * </pre>
     * @param shareDocDeptVO.
     * @return int
     *  insert 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public int insertShareDept(ShareDocDeptVO shareDocDeptVO) throws Exception {
	return commonDAO.insert("env.insertShareDept", shareDocDeptVO);
    }
    
    /**
     * 
     * <pre> 
     *  문서공유부서를 삭제한다.
     * </pre>
     * @param shareDocDeptVO.
     * @return int
     *  insert 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    public int deleteShareDept(ShareDocDeptVO shareDocDeptVO) throws Exception {
	return commonDAO.delete("env.deleteShareDept", shareDocDeptVO);
    }
}
