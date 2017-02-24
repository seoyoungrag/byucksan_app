package com.sds.acube.app.appcom.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IDocMngInfoService;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;


/**
 * Class Name  : DocMngInfoService.java <br> Description : 문서관리정보 서비스  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 28. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 4. 28.
 * @version  1.0 
 * @see  com.sds.acube.app.appcom.service.impl.DocMngInfoService.java
 */
@Service("docMngInfoService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class DocMngInfoService extends BaseService implements IDocMngInfoService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    

    /**
     * 
     * <pre> 
     *  문서관러정보 수정
     * </pre>
     * @param appDocVO
     * @throws Exception
     * @see  
     *
     */
    public void updateDocMngInfo(Map inputMap) throws Exception{
	
	 String type = (String)inputMap.get("type");
	if("APP".equals(type)){
	    commonDAO.modifyMap("approval.updateDocMngInfo", inputMap);
	}
	else{
	    commonDAO.modifyMap("enforce.updateDocMngInfo", inputMap);
	}
    }


    /**
     * 
     * <pre> 
     *  문서관리 정보 조회
     * </pre>
     * @param inputMap
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Map  selectDocMngInfo(Map inputMap) throws Exception{
	
	String type = (String)inputMap.get("type");
	
	Map rsltMap = new HashMap();
	
	if("APP".equals(type)){
	    rsltMap =  (Map)commonDAO.getMap("approval.selectDocMngInfo", inputMap);
	}
	else{
	    rsltMap= (Map)commonDAO.getMap("enforce.selectDocMngInfo", inputMap);
	}
	return rsltMap;
    }
}