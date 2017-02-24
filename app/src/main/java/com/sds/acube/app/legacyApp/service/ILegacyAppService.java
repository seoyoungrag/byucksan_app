package com.sds.acube.app.legacyApp.service;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.legacyApp.vo.LegacyAppVO;
import com.sds.acube.app.legacyApp.vo.LegacyEaiVO;


 
/** 
 *  Class Name  : ILegacyAppServicee.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 :  <br>
 * 
 *  @author  jth8172 
 *  @since 2012. 5. 21.
 *  @version 1.0 
 *  @see  com.sds.acube.app.legacyApp.service.ILegacyMngService.java
 */

public interface ILegacyAppService {

    // 연계목록조회
    public Page listLegacyApp(LegacyAppVO legacyAppVO,int page) throws Exception;
    
    // 연계등록
    public int insertLegacyApp(LegacyAppVO legacyAppVO) throws Exception;

    // 연계삭제
    public int deleteLegacyApp(LegacyAppVO legacyAppVO) throws Exception;

    // 연계조회
    public LegacyAppVO viewLegacyApp(LegacyAppVO legacyAppVO) throws Exception;
    
    // 연계Ack반영
    public int updateLegacyAckApp(LegacyAppVO legacyAppVO) throws Exception;
    
    // 연계 EAI 헤더 연계대상 목록조회
    public List<LegacyEaiVO> listLegacyEaiProc(Map<String, String> map) throws Exception;
    
    // 연계 EAI 헤더 연계대상 목록조회
    public List<LegacyEaiVO> listLegacyEaiSncNo(Map<String, String> map) throws Exception;
    
    // 연계 EAI 첨부 연계대상 목록조회
    public List<LegacyEaiVO> listLegacyEaiFileInfo(Map<String, String> map) throws Exception;
    
    // 연계 EAI 상태값 업데이트
    public int updateEaiIfStatus(Map<String, String> map) throws Exception;
    
    // 연계 EAI 상태값 업데이트
    public int updateEaiAttIfStatus(Map<String, String> map) throws Exception;
    
    // 연계 EAI Ack 등록
    public int insertLegacyEaiAck(LegacyEaiVO legacyEaiVO) throws Exception;
    
    // 연계조회
    public LegacyEaiVO viewLegacyEaiCot(Map<String, String> map) throws Exception;
    
    // 연계 COTIS EAI Ack 등록
    public int insertLegacyCotEaiAck(LegacyEaiVO legacyEaiVO) throws Exception;
    
    // 전자조달 JD 테이블 정보 조회
    public LegacyEaiVO viewLegacyEaiSup(Map<String, String> map) throws Exception;
    
}
