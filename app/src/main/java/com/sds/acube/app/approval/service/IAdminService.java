/**
 * 
 */
package com.sds.acube.app.approval.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;

/** 
 *  Class Name  : IAdminService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 23. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.IAdminService.java
 */

public interface IAdminService {
    // 문서정보 수정
    ResultVO modifyDocInfo(Map<String, String> map, Map<String, String> optionMap, AppDocVO appDocVO, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception;
    ResultVO modifyDocInfo(Map<String, String> map, Map<String, String> optionMap, AppDocVO appDocVO, List<StorFileVO> storFileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception;
    // 결재경로 수정
    ResultVO modifyAppLine(List<AppDocVO> appDocVOs, List<List<AppLineVO>> appLineVOsList, List<List<FileVO>> fileVOsList, List<List<StorFileVO>> storFileVOsList, DocHisVO docHisVO) throws Exception;
    boolean modifyAppLine(AppDocVO appDocVO, List<AppLineVO> appLineVO, DocHisVO docHisVO) throws Exception;
    // 본문수정
    ResultVO modifyBody(List<FileVO> fileVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception;
    ResultVO modifyBody(List<FileVO> fileVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception;
    // 첨부수정
    ResultVO modifyAttach(List<FileVO> prevFileVOs, List<FileVO> fileVOs, DocHisVO docHisVO) throws Exception;
    ResultVO modifyAttach(List<FileVO> prevFileVOs, List<FileVO> fileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception;
    // 관리자회수
    ResultVO withdrawAppDoc(List<AppDocVO> appDocVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception;
    // 문서관리로 보내기
    ResultVO sendToDoc(QueueToDocmgrVO queueToDocmgrVO) throws Exception;
    // 관리자 문서삭제
    ResultVO deleteAppDoc(List<AppDocVO> appDocVOs,  DocHisVO docHisVO) throws Exception;

   
    /**
     * 
     * <pre> 
     *  접수문서 결재경로 수정(관리자)
     * </pre>
     * @param map
     * @param enfLineVOs
     * @param docHisVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO modifyEnfLineByAdmin(Map<String, String> map, List<EnfLineVO> enfLineVOs, DocHisVO docHisVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  관리자 접수문서 문서회수
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public ResultVO processAdminRetrieve(EnfLineVO enfLineVO, DocHisVO docHisVO) throws Exception;
}
