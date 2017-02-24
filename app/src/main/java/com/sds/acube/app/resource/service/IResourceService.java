package com.sds.acube.app.resource.service;

import java.util.List;

import com.sds.acube.app.resource.vo.ResourceVO;
import com.sds.acube.app.resource.vo.UpdateTableVO;

/** 
 *  Class Name  : IResourceService.java <br>
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
 *  @see  com.sds.acube.app.resource.service.IResourceService.java
 */

public interface IResourceService {
	
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
	List<ResourceVO> selectResourceList(ResourceVO resourceVO) throws Exception;
	
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
	int insertResource(ResourceVO resourceVO) throws Exception;
	
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
    int updateResource(ResourceVO resourceVO) throws Exception;	
    
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
    
    int deleteResource(ResourceVO resourceVO) throws Exception;
    
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
    int updateTableByResourceId(UpdateTableVO tableVO) throws Exception;
    
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
    int updateTableOptionValue(UpdateTableVO tableVO) throws Exception;    

}
