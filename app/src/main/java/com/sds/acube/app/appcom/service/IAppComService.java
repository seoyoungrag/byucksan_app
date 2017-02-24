package com.sds.acube.app.appcom.service;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.appcom.vo.AppBindVO;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.appcom.vo.SubNumVO;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;

/** 
 *  Class Name  : IAppComService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 29. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 29.
 *  @version 1.0 
 *  @see  com.sds.acube.app.appcom.service.IAppComService.java
 */

public interface IAppComService {
    // 파일
    int insertFile(List<FileVO> fileVOs) throws Exception;
    int insertFile(FileVO fileVO) throws Exception;
    int insertFileHis(List<FileHisVO> fileHisVOs) throws Exception;
    int insertFileHis(FileHisVO fileHisVO) throws Exception;
    int updateBody(List<FileVO> fileVOs) throws Exception;
    int updateBody(FileVO fileVO) throws Exception;
    int updateBody(String compId, List<String> fileIds, List<StorFileVO> storFileVOs) throws Exception;
    int updateBody(String compId, String fileId, StorFileVO storFileVO) throws Exception;
    int deleteFile(FileVO fileVO) throws Exception;
    int deleteFile(Map<String, String> map) throws Exception;
    int deleteFile(List<FileVO> fileVOs) throws Exception;
    List<FileVO> listFile(Map<String, String> map) throws Exception;
    List<FileVO> listFileByGroupAdmin(Map<String, String> map) throws Exception;
    
    // 최종본문이력정보
    List<FileHisVO> selectLastBodyInfo(Map<String, String> map) throws Exception;
   
    // 발송정보
    int insertSendInfo(SendInfoVO sendInfoVO) throws Exception;
    SendInfoVO selectSendInfo(Map<String, String> map) throws Exception;
    
    // 발송처리
    int insertSendProc(SendProcVO sendProcVO) throws Exception;
    
    // 문서소유부서
    int insertOwnDept(OwnDeptVO ownDeptVO) throws Exception;
    List<OwnDeptVO> listOwnDept(Map<String, String> map) throws Exception;
    
    // 공람자
    int insertPubReader(List<PubReaderVO> pubReaderVOs) throws Exception; 
    int updatePubReader(List<PubReaderVO> deleteList, List<PubReaderVO> insertList) throws Exception;
    int updatePubReader(List<PubReaderVO> deleteList, List<PubReaderVO> insertList, List<PubReaderVO> updateList) throws Exception;
    PubReaderVO  selectPubReader(PubReaderVO pubReaderVO) throws Exception;
    List<PubReaderVO> listPubReader(Map<String, String> map) throws Exception;

    // 공람게시 열람자
    List<PostReaderVO> listPostReader(String compId, String publishId, String docId) throws Exception;
    
    // 공람게시 열람자
    public Page listPostReader(PubPostVO searchVO, int pageIndex) throws Exception;

    
    // 채번
    int selectDocNum(DocNumVO docNumVO) throws Exception;
    int updateDocNum(DocNumVO docNumVO) throws Exception;
    int selectSubNum(SubNumVO subNumVO) throws Exception;
    int updateSubNum(SubNumVO subNumVO) throws Exception;
    int selectListNum(DocNumVO docNumVO) throws Exception;
    int updateListNum(DocNumVO docNumVO) throws Exception;
    
    // 일상감사일지
    int insertAuditList(AuditListVO auditListVO) throws Exception;
    int deleteAuditList(String compId, String docId, String currentDate) throws Exception;

    // 편철 수정
    int updateBindInfo(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception;
    int updateBindInfo(List<AppBindVO> appBindVOs, DocHisVO docHisVO) throws Exception;
    // 편철 부서복사
    int copyBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception;
    // 편철 부서회수
    int withdrawBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception;
    // 편철 부서이동
    int moveBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception;
    //문서정보
    public Object selectDocInfo(Map<String, String> map) throws Exception;
    //보고경로
    Object listLines(Map<String, String> map) throws Exception;
    
    //문서비밀버호 비교
    boolean compareDocPassword(String compId, String docId,  String encryptedPwd, String queryString) throws Exception;
}
