/**
 * 
 */
package com.sds.acube.app.env.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.env.vo.CategoryVO;
import com.sds.acube.app.env.vo.FormVO;

/** 
 * 
 *  Class Name  : IEnvFormService.java <br>
 *  Description : 서식관리 서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 26. <br>
 *  수 정  자 : 윤동원  <br>
 *  수정내용 :  <br>
 * 
 *  @author  윤동원 
 *  @since 2011. 4. 26.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvFormService.java
 */

public interface IEnvFormService {
   
    /**
     * 
     * <pre> 
     *  서식관리 목록조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List listEvnForm(Map inputMap) throws Exception;

    
    /**
     * 
     * <pre> 
     *  서식관리 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public FormVO selectEvnForm(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식관리 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public FormVO selectEvnFormById(Map inputMap) throws Exception;
    
    
     /**
     * 
     * <pre> 
     *  공문서양식 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public FormVO selectEvnPubdocForm(Map inputMap) throws Exception;
    
         /**
     * 
     * <pre> 
     *  감사양식 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public FormVO selectEvnAuditForm(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     * 서식관리 등록
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void insertEvnForm(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식관리 수정
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void updateEvnForm(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식관리 삭제
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void deleteEvnForm(Map inputMap) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  서식함 목록조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List listEvnCategory(Map inputMap) throws Exception;
    public List listEvnCategoryResource(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식함 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public CategoryVO selectEvnCategory(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     * 서식함 등록
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void insertEvnCategory(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식함 수정
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void updateEvnCategory(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서식함 삭제
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void deleteEvnCategory(Map inputMap) throws Exception;
    

    
    
    /**
     * 
     * <pre> 
     *  시스템코드와 관련된 양식조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public FormVO getFormBySystem(FormVO formVO) throws Exception;
    
    
    
    /**
     * 
     * <pre> 
     *  양식이름 (같은 양식함 안에서 중복 이름 조회)
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int selectFormName(Map inputMap) throws Exception;
    
    
    
}
