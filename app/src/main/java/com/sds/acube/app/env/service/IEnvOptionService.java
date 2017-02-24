/**
 * 
 */
package com.sds.acube.app.env.service;

import java.util.List;

import com.sds.acube.app.env.vo.OptionVO;

/** 
 *  Class Name  : IEnvOptionService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 22. <br>
 *  수 정  자 : skh0204  <br>
 *  수정내용 :  <br>
 * 
 *  @author  skh0204 
 *  @since 2011. 3. 22.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvOptionService.java
 */

public interface IEnvOptionService {
   
   void updateOptionList(List optionVOList) throws Exception;
   void updateOptionUseYn(OptionVO optionVO) throws Exception;
   void updateOptionValue(OptionVO optionVO) throws Exception;
   void updateOption(OptionVO optionVO) throws Exception;
   void updateOptionUseYn(String compId, String optionId, String useYn) throws Exception;
   void updateOptionValue(String compId, String optionId, String optionValue) throws Exception;
   void updateOption(String compId, String optionId, String useYn, String optionValue) throws Exception;
}
