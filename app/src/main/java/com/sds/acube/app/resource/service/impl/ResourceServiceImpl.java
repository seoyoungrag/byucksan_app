package com.sds.acube.app.resource.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.resource.service.IResourceService;
import com.sds.acube.app.resource.vo.ResourceVO;
import com.sds.acube.app.resource.vo.UpdateTableVO;


/**
 * 
 *  Class Name  : ResourceService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2013. 8. 5. <br>
 *  수 정  자 : ahnjava  <br>
 *  수정내용 :  <br>
 * 
 *  @author  ahnjava 
 *  @since 2013. 8. 5.
 *  @version 1.0 
 *  @see  com.sds.acube.app.resource.service.impl.ResourceService.java
 */

@Service("resourceService")
public class ResourceServiceImpl extends BaseService implements IResourceService {
	
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
	
    /**
     * 
     * <pre> 
     *  다국어를 조회한다.
     * </pre>
     * @param resourceVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<ResourceVO> selectResourceList(ResourceVO resourceVO) throws Exception {
    	return (List<ResourceVO>) commonDAO.getList("resource.selectResourceList", resourceVO);
    }  
    
    /**
     * 
     * <pre> 
     *  다국어를 등록한다.
     * </pre>
     * @param resourceVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int insertResource(ResourceVO resourceVO) throws Exception { 
    	return commonDAO.insert("resource.insertResource", resourceVO);
    }   
    
    /**
     * 
     * <pre> 
     *  다국어를 수정한다.
     * </pre>
     * @param resourceVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int updateResource(ResourceVO resourceVO) throws Exception { 
    	return commonDAO.modify("resource.updateResource", resourceVO);
    }  
    
    /**
     * 
     * <pre> 
     *  다국어를 삭제한다.
     * </pre>
     * @param resourceVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int deleteResource(ResourceVO resourceVO) throws Exception {
    	return commonDAO.delete("resource.deleteResource", resourceVO);
    }    
    
    /**
     * 
     * <pre> 
     *  다국어의 키인 resourceId 값을 대상 테이블의 필드에 업데이트한다.
     * </pre>
     * @param tableVO
     * @return
     * @throws Exception
     * @see  
     *
     */   
    public int updateTableByResourceId(UpdateTableVO tableVO) throws Exception {
    	return commonDAO.modify("resource.updateTableByResourceId", tableVO);
    }
    
    /**
     * 
     * <pre> 
     *  대상테이블의 컬럼(OPTION_VALUE)에 resourceID로 변경한다.
     * </pre>
     * @param tableVO
     * @return
     * @throws Exception
     * @see  
     *
     */  
    public int updateTableOptionValue(UpdateTableVO tableVO) throws Exception { 
    	return commonDAO.modify("resource.updateTableOptionValue", tableVO);
    }  
    
    
    
    
    
	
}