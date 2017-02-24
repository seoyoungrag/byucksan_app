package com.sds.acube.app.exchange.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.exchange.vo.ConvertVO;


/**
 * Class Name : IConvertService.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 7. 7. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 7. 7.
 * @version 1.0
 * @see com.sds.acube.app.exchange.service.IConvertService.java
 */

public interface IConvertService {

    List<ConvertVO> getDocList(Map<String, String> param) throws Exception;


    List<ConvertVO> getDocMonthList(Map<String, String> param) throws Exception;


    List<ConvertVO> getErrorList(Map<String, String> param) throws Exception;


    List<ConvertVO> getTargetList(Map<String, String> param) throws Exception;


    int inputError(ConvertVO vo, String message) throws Exception;


    int removeError(ConvertVO vo) throws Exception;

}
