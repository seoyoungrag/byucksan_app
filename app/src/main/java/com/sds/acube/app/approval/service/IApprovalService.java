/**
 * 
 */
package com.sds.acube.app.approval.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.ProxyDeptVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.GwAccgvVO;
import com.sds.acube.app.approval.vo.GwIfcuvVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppDetailVO;
import com.sds.acube.app.ws.vo.AppResultVO;

/** 
 *  Class Name  : IApprovalService.java <br>
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
 *  @see  com.sds.acube.app.approval.service.IApprovalService.java
 */

public interface IApprovalService {
    // 기안, 1인전결
    ResultVO insertAppDoc(AppDocVO appDocVO, String currentDate, String draftType, ProxyDeptVO proxyDeptVO, String userId, Locale langType) throws Exception;
    ResultVO insertAppDoc(List<AppDocVO> appDocVOs, String currentDate, String draftType, ProxyDeptVO proxyDeptVO, String userId, Locale langType) throws Exception;
    
    // 임시저장
    ResultVO insertTemporary(AppDocVO appDocVO, String currentDate) throws Exception;
    ResultVO insertTemporary(List<AppDocVO> appDocVOs, String currentDate) throws Exception;
    
    // 임시저장
    ResultVO updateTemporary(AppDocVO appDocVO, String currentDate) throws Exception;
    ResultVO updateTemporary(List<AppDocVO> appDocVOs, String currentDate) throws Exception;
    
    // 문서처리
    ResultVO processAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String currentDate, String loginId, Locale langType) throws Exception;
    ResultVO processAppDoc(List<AppDocVO> appDocVOs, UserProfileVO userProfileVO, String currentDate, String loginId, Locale langType) throws Exception;

    // 모바일
    AppResultVO processMobile(AppActionVO appActionVO) throws Exception;
    ResultVO recoverBody(String compId, String docId, String docState, String userId, String userName, List<StorFileVO> storFileVOs, List<FileVO> fileVOs, String reason) throws Exception;
    AppDetailVO selectMobile(AppActionVO appActionVO) throws Exception;
    MobileAppResultVO processMobileApp(MobileAppActionVO mobileAppActionVO) throws Exception;
    
    // 보류처리
    ResultVO holdoffAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String deptId, String currentDate) throws Exception;
    ResultVO holdoffAppDoc(List<AppDocVO> appDocVOs, UserProfileVO userProfileVO, String deptId, String currentDate) throws Exception;
    // 후열처리
    ResultVO readafterAppDoc(String compId, String docId, String userId, String currentDate) throws Exception;
    
    // 통보처리  // jth8172 2012 신결재 TF
    ResultVO informAppDoc(String compId, String docId, String userId, String currentDate) throws Exception;

    // 문서반려
    ResultVO returnAppDoc(AppDocVO appDocVO, String userId, String deptId, String currentDate, String procOpinion, DocHisVO docHisVO) throws Exception;
    ResultVO returnAppDoc(List<AppDocVO> appDocVOs, String userId, String deptId, String currentDate, String procOpinion, DocHisVO docHisVO) throws Exception;
    
    // 문서회수
    ResultVO withdrawAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String currentDate) throws Exception;
    ResultVO withdrawAppDoc(List<AppDocVO> appDocVOs, ArrayList<List<StorFileVO>> storFileVOsList, UserProfileVO userProfileVO, String currentDate) throws Exception;
    
    // 문서조회
    AppDocVO selectAppDoc(String compId, String docId) throws Exception;
    List<AppDocVO> listAppDoc(String compId, String docId, String userId, String deptId, String lobCode) throws Exception;
    List<AppDocVO> searchAppDoc(String compId, String docNum) throws Exception;
    
    // 임시문서 조회
    AppDocVO selectTemporary(String compId, String docId, String userId) throws Exception;
    List<AppDocVO> listTemporary(String compId, String docId, String userId) throws Exception;
    
    // 연계문서 조회
    AppDocVO selectBizAppDoc(String compId, String docId, String userId) throws Exception;
    List<AppDocVO> listBizAppDoc(String compId, String docId, String userId) throws Exception;
    
    // 첨부개수 수정
    int updateAttachCount(Map<String, String> map) throws Exception;
    
    // 보고경로 수정
    ResultVO updateAppLine(String compId, String docId, String userId, String askType, String currentDate) throws Exception;
    int updateAppLine(AppLineVO appLineVO, String deptId) throws Exception;
    // 보고경로 조회
    List<AppLineVO> listAppLine(String compId, String docId) throws Exception;
    // 보고경로이력 조회
    List<AppLineHisVO> listAppLineHis(String compId, String docId, String userId) throws Exception;

    //반려문서대장등록
    ResultVO regRejectAppDoc(List<AppDocVO> appDocVOs, List<List<AppLineVO>> appLineVOsList, String currentDate) throws Exception ;    
    ResultVO regRejectAppDoc(AppDocVO appDocVO, List<AppLineVO> appLineVO, String currentDate) throws Exception;
    
    // 반려문서삭제
    ResultVO deleteAppDoc(DocHisVO docHisVO) throws Exception;
    
    // 등록문서 등록취소
    ResultVO unregistAppDoc(DocHisVO docHisVO) throws Exception;
    ResultVO unregistAppDoc(DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgrVO, QueueVO queueVO) throws Exception;
    // 등록취소문서 재등록
    ResultVO registAppDoc(DocHisVO docHisVO) throws Exception;
    ResultVO registAppDoc(DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgrVO, QueueVO queueVO) throws Exception;
    
    // 임시문서삭제
    ResultVO deleteTemporary(String compId, String docId, String userId) throws Exception;
    void deleteTemporary(String compId, String docId, String userId, int code) throws Exception;
    //deleteNonEleDoc
    //비전자 생산문서 삭제처리
    ResultVO deleteNonEleDoc(DocHisVO docHisVO) throws Exception;
    
    //비전자 접수문서 삭제처리
    ResultVO deleteNonEleEnfDoc(DocHisVO docHisVO) throws Exception;

    // 사용자직인 이미지 select
    FileVO selectUserSeal(String compId, String userId) throws Exception;
    
    // 사용자직인 이미지 select
    FileVO selectUserSeal(String compId, String userId, String fileName) throws Exception;

    // 조직직인 이미지 select
    FileVO selectOrgSeal(String compId, String sealId) throws Exception;

    // 거래처정보 Select
    List<GwIfcuvVO> listGwIfcuv(String cusnam, int pageIndex) throws Exception;
    //public Page listGwPibsv(GwIfcuvVO searchVO, int pageIndex) throws Exception;
    
    // 거래처정보 총count
    int listGwIfcuvCount(String cusnam) throws Exception;
    
    // 거래처정보 Select
    List<CustomerVO> listCustomer(String compId, String docIds) throws Exception;
    
    // 투자조합 Select
    List<GwAccgvVO> listGwAccgv() throws Exception;
    
    // 문서정보 수정
    ResultVO modifyDocInfo(Map<String, String> map, PubPostVO pubPostVO, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception;

    // 연계기안본문 업데이트
    List<FileVO> updateBizBody(List<FileVO> fileVOs, DrmParamVO drmParamVO, String docState) throws Exception;
    
    // 첨부날인 업로드
    boolean stampAttachUpload(List<FileVO> fileVOs, DrmParamVO drmParamVO, DocHisVO docHisVO) throws Exception;
    
    public int completelyDeleteAppDoc(String ids,String compId) throws Exception;
    
}
