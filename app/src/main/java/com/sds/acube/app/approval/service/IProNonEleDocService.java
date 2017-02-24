/**
 * 
 */
package com.sds.acube.app.approval.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.ResultVO;

/** 
 *  Class Name  : IProNonEleDocService.java <br>
 *  Description : 생산용 비전자 문서 등록  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 22. <br>
 *  수 정  자 : 장진홍  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jumbohero 
 *  @since 2011. 4. 22.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.IProNonEleDocService.java
 */
public interface IProNonEleDocService {
    //생산용 비전자문서 등록
    ResultVO insertProNonElecDoc(AppDocVO appDocVO, String currentDate ,String proxyDeptId)throws Exception;
    
    //생상용 비전자 문서를 수정
    ResultVO updateProNonElecDoc(AppDocVO appDocVO, DocHisVO docHisVO, String currentDate)throws Exception;
    
    //생산용 비전자 문서 조회
    AppDocVO selectProNonElecDoc(String compId, String docId)throws Exception;
    
    //카테고리 목록
    List<CategoryVO> selectCategoryList(CategoryVO categoryVo) throws Exception;
    
    //특정카테고리 정보를 가져옴
    CategoryVO selectCate (Map<String, String> map) throws Exception;
}
