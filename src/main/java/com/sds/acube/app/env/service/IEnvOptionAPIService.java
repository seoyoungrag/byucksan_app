/**
 * 
 */
package com.sds.acube.app.env.service;

import java.util.HashMap;
import java.util.List;

import com.sds.acube.app.common.vo.AppCodeVO;
import com.sds.acube.app.env.vo.FormEnvVO;
import com.sds.acube.app.env.vo.LineGroupVO;
import com.sds.acube.app.env.vo.LineUserVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.PeriodVO;
import com.sds.acube.app.env.vo.PubViewGroupVO;
import com.sds.acube.app.env.vo.PubViewUserVO;
import com.sds.acube.app.env.vo.RecvGroupVO;
import com.sds.acube.app.env.vo.RecvUserVO;
import com.sds.acube.app.env.vo.SenderTitleVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;

/** 
 *  Class Name  : IEnvOptionAPIService.java <br>
 *  Description : 결재옵션 API <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 25. <br>
 *  수 정  자 : 신경훈  <br>
 *  수정내용 :  <br>
 * 
 *  @author  신경훈 
 *  @since 2011. 3. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvOptionAPIService.java
 */

public interface IEnvOptionAPIService {
    
    /** 
     * 
     * <pre> 
     *  결재옵션을 메모리에 reload 한다.
     * </pre>
     * @param compId
     * @throws Exception
     * @see  
     *
     */
    void optionReload(String compId) throws Exception;
   
    /** 
     * 
     * <pre> 
     *  결재옵션 전체를 리스트로 가져온다
     * </pre>
     * @param OptionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OptionVO> selectOptionList(OptionVO OptionVO) throws Exception;
    
    /**
     *  
     * <pre> 
     *  결재옵션 전체를 해쉬맵(옵션ID, 옵션VO)으로 가져온다.
     * </pre>
     * @param optionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    HashMap<String, OptionVO> selectOptionMap(OptionVO optionVO) throws Exception;
    
    /**
     * 
     * <pre> 
     * 결재옵션 그룹별 리스트를 가져온다.
     * 그룹(optionType) : OPTG000=처리유형, OPTG100=함, OPTG200=대장, OPTG300=결재옵션)
     * </pre>
     * @param OptionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OptionVO> selectOptionGroupList(OptionVO OptionVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션 그룹별 해쉬맵(옵션ID, 옵션VO)을 가져온다.
     * </pre>
     * @param optionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    HashMap<String, OptionVO> selectOptionGroupMap(OptionVO optionVO) throws Exception;
    HashMap<String, OptionVO> selectOptionGroupMapResource(String compId, String optionType, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션별 옵션VO를 가져온다.
     * </pre>
     * @param optionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    OptionVO selectOption(OptionVO optionVO) throws Exception;   
       

    /**
     * 
     * <pre> 
     *  결재옵션 전체를 리스트로 가져온다.
     * </pre>
     * @param compId
     * @param optionType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OptionVO> selectOptionGroupList(String compId, String optionType) throws Exception;
    List<OptionVO> selectOptionGroupListResource(String compId, String optionType, String langType) throws Exception;
    
    List<OptionVO> selectOptionGroupListOrderBySequence(String compId, String optionType) throws Exception;
    List<OptionVO> selectOptionGroupListResourceOrderBySequence(String compId, String optionType, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션 전체를 해쉬맵(옵션ID, 옵션VO)으로 가져온다.
     * </pre>
     * @param compId
     * @param optionType
     * @return
     * @throws Exception
     * @see  
     *
     */    
    HashMap<String, OptionVO> selectOptionGroupMap(String compId, String optionType) throws Exception; 
    
    /**
     * 
     * <pre> 
     *  결재옵션 옵션Value를 구분자로 나눠 해쉬맵으로 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     * @return
     * @throws Exception
     * @see  
     *
     */
    HashMap<String, String> selectOptionTextMap(String compId, String optionId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션별 옵션VO를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     * @return
     * @throws Exception
     * @see  
     *
     */
    OptionVO selectOption(String compId, String optionId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션별 사용여부를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     * @return
     * @throws Exception
     * @see  
     *
     */
    String selectOptionValue(String compId, String optionId) throws Exception;
    
    /**
     *  
     * <pre> 
     *  결재옵션별 옵션Value를 가져온다.
     * </pre>
     * @param compId
     * @param optionId
     * @return
     * @throws Exception
     * @see  
     *
     */
    String selectOptionText(String compId, String optionId) throws Exception;
       
    /** 
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param optionVOList
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    void updateOptionUseYnList(List optionVOList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param optionVOList
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    void updateOptionList(List optionVOList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param optionVO
     * @throws Exception
     * @see  
     *
     */
    void updateOptionUseYn(OptionVO optionVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션값을 수정한다.
     * </pre>
     * @param optionVO
     * @throws Exception
     * @see  
     *
     */
    void updateOptionValue(OptionVO optionVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param optionVO
     * @throws Exception
     * @see  
     *
     */
    void updateOption(OptionVO optionVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션 사용여부를 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param useYn
     * @throws Exception
     * @see  
     *
     */
    void updateOptionUseYn(String compId, String optionId, String useYn) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션값을 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param optionValue
     * @throws Exception
     * @see  
     *
     */
    void updateOptionValue(String compId, String optionId, String optionValue) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재옵션을 수정한다.
     * </pre>
     * @param compId
     * @param optionId
     * @param useYn
     * @param optionValue
     * @throws Exception
     * @see  
     *
     */
    void updateOption(String compId, String optionId, String useYn, String optionValue) throws Exception;
       

    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼별 리스트를 가져온다.
     *  envType : FET001=로고, FET002=심볼, FET003=상부캠페인, FET004=하부캠페인)
     * </pre>
     * @param compId
     * @param envType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<FormEnvVO> selectFormEnvList(String compId, String registerDeptId, String envType) throws Exception;
    List<FormEnvVO> selectFormEnvListResource(String compId, String registerDeptId, String envType, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/신볼별 기본설정을 VO로 가져온다.
     * </pre>
     * @param compId
     * @param envType
     * @return
     * @throws Exception
     * @see  
     *
     */
    FormEnvVO selectDefaultFormEnvVO(String compId, String registerDeptId, String envType) throws Exception;
    
    /**
     *  
     * <pre> 
     *  다운로드할 로고/심볼 파일의 VO를 가져온다.
     * </pre>
     * @param compId
     * @param formEnvId
     * @return
     * @throws Exception
     * @see  
     *
     */
    FormEnvVO selectFormEnvVO(String compId, String formEnvId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기본설정된 캠페인 문구를 가져온다.
     *  상위캠페인:envType=FET003 , 하위캠페인:envType=FET004
     * </pre>
     * @param compId
     * @param envType
     * @return
     * @throws Exception
     * @see  
     *
     */
    String selectDefaultCampaign(String compId, String registerDeptId, String envType) throws Exception;
    
    /**
     *  
     * <pre> 
     *  회사에 등록된 모든 로고/심볼 파일을 STOR서버에서 WAS로 다운로드 한다.
     * </pre>
     * @param compId
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean DonwnloadFormEnvFile(String compId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본여부 전체를 'N'으로 설정한다.
     * </pre>
     * @param compId
     * @param envType
     * @throws Exception
     * @see  
     *
     */
    void updateFormEnvDefaultYnAllN(String compId, String registerDeptId, String envType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본여부를 수정한다.
     * </pre>
     * @param compId
     * @param formEnvId
     * @throws Exception
     * @see  
     *
     */
    void updateFormEnvDefault(String compId, String registerDeptId, String formEnvId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본설정을 수정한다.
     *  기본 설정된 값을 결재옵션값에 등록.
     * </pre>
     * @param compId
     * @param envType
     * @param formEnvId
     * @param optionValue
     * @throws Exception
     * @see  
     *
     */
    void updateFormEnvDefaultSet(String compId, String registerDeptId, String envType, String formEnvId, String optionValue) throws Exception;
    
    /**
     * 
     * <pre> 
     *  캠페인을 등록한다.
     * </pre>
     * @param vo
     * @throws Exception
     * @see  
     *
     */
    void insertFormEnvCamp(FormEnvVO vo) throws Exception;
    
    /**
     * 
     * <pre> 
     *  로고/심볼을 등록한다.
     * </pre>
     * @param vo
     * @throws Exception
     * @see  
     *
     */
    void insertFormEnvLS(FormEnvVO vo) throws Exception;
    
    /**
     * 
     * <pre> 
     *  캠페인/로고/심볼을 삭제한다.
     * </pre>
     * @param compId
     * @param formEnvId
     * @throws Exception
     * @see  
     *
     */
    void deleteFormEnv(String compId, String formEnvId) throws Exception;
    
       
    /** 
     * 
     * <pre> 
     *  소속된 부서의 발신명의 리스트를 가져온다.
     * </pre>
     * @param compId
     * @param deptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<SenderTitleVO> selectSenderTitleList(String compId, String deptId, String groupType) throws Exception;
    List<SenderTitleVO> selectSenderTitleListResource(String compId, String deptId, String groupType, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  소속된 부서와 상위부서의 발신명의 리스트를 가져온다.
     * </pre>
     * @param session
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<SenderTitleVO> selectSenderTitleAllList(String compId, String[] deptList, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  소속된 부서의 기본설정된 발신명의를 가져온다.
     * </pre>
     * @param compId
     * @param deptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    String selectDefaultSenderTitle(String compId, String deptId, String langType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발신명의 기본여부 전체를 'N'으로 설정한다.
     * </pre>
     * @param compId
     * @param deptId
     * @throws Exception
     * @see  
     *
     */
    void updateSenderTitleDefaultYnAllN(String compId, String deptId, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발신명의 기본여부를 수정한다.
     * </pre>
     * @param compId
     * @param senderTitleId
     * @throws Exception
     * @see  
     *
     */
    int updateSenderTitleDefaultYn(String compId, String senderTitleId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발신여부 기본값을 수정한다.
     * </pre>
     * @param compId
     * @param deptId
     * @param senderTitle
     * @throws Exception
     * @see  
     *
     */
    boolean updateSenderTitleDefaultSet(String compId, String deptId, String senderTitle, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발신명의를 등록한다.
     * </pre>
     * @param vo
     * @throws Exception
     * @see  
     *
     */
    int insertSenderTitle(SenderTitleVO vo) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발신명의를 삭제한다.
     * </pre>
     * @param compId
     * @param senderTitleId
     * @throws Exception
     * @see  
     *
     */
    int deleteSenderTitle(String compId, String senderTitleId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineGroupVO> listAppLineGroup(String compId, String registerId, String registerDeptId, String groupType) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineGroupVO> listAppLineGroup(String compId, String registerId, String registerDeptId, String groupType, String usingType) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.(결재경로그룹 선택팝업)
     *  usingType - DPI001:생산, DPI002:접수
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param usingType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineGroupVO> listAppLineGroupP(String compId, String registerId, String registerDeptId, String usingType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param lineGroupId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineUserVO> listAppLine(String compId, String lineGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기본결재경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param usingType
     * @param registerId
     * @param registerDeptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineUserVO> listDefaultAppLine(String compId, String usingType, String registerId, String registerDeptId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기본결재경로를 설정한다.
     * </pre>
     * @param compId
     * @param usingType
     * @param registerId
     * @param lineGroupId
     * @param groupType
     * @throws Exception
     * @see  
     *
     */
    void updateDefaultAppLineSet(String compId, String usingType, String registerId, String lineGroupId, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기본  결재경로그룹을 해제한다.
     * </pre>
     * @param compId
     * @param lineGroupId
     * @throws Exception
     * @see  
     *
     */
    void cancelAppLineDefaultYn(String compId, String lineGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재경로그룹을 삭제한다.
     * </pre>
     * @param compId
     * @param lineGroupId
     * @throws Exception
     * @see  
     *
     */
    void deleteAppLineGroup(String compId, String lineGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재경로그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvo
     * @throws Exception
     * @see  
     *
     */
    void insertAppLineGroup(LineGroupVO gvo, List<LineUserVO> uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재경로그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    void updateAppLineGroup(LineGroupVO gvo, List uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    void insertRecvGroup(RecvGroupVO gvo, List<RecvUserVO> uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @throws Exception
     * @see  
     *
     */
    void insertRecvGroup(RecvGroupVO gvo) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자를 등록한다.
     * </pre>
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    void insertRecvGroupUser(List<RecvUserVO> uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹관리, 문서책임자)
     * </pre>
     * @param compId
     * @param registerId
     * @param groupType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvGroupVO> listRecvGroup(String compId, String registerDeptId, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹관리, 회사관리자)
     * </pre>
     * @param compId
     * @param groupType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvGroupVO> listRecvGroup(String compId, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(수신자그룹선택 팝업)
     * </pre>
     * @param compId
     * @param registerDeptId
     * @param registerId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvGroupVO> listRecvGroupP(String compId, String registerDeptId, String registerId) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  수신자 그룹 검색한다.
     * </pre>
     * @param compId
     * @param registerDeptId
     * @param registerId
     * @param searchDept
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvGroupVO> searchRecvGroupP(String compId, String registerDeptId, String registerId, String searchDept) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param recvGroupId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvUserVO> listRecvLine(String compId, String recvGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 삭제한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     * @throws Exception
     * @see  
     *
     */
    void deleteRecvGroup(String compId, String recvGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자를 삭제한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     * @throws Exception
     * @see  
     *
     */
    void deleteRecvGroupUser(String compId, String recvGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자를 수정한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     * @throws Exception
     * @see  
     *
     */
     int updateRecvGroupUser(RecvUserVO recvVO) throws Exception;
	
    /**
     * 
     * <pre> 
     *  수신자를 삭제한다.
     * </pre>
     * @param compId
     * @param recvGroupId
     * @throws Exception
     * @see  
     *
     */
    int deleteRecvGroupUser(RecvUserVO recvGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    void updateRecvGroup(RecvGroupVO gvo, List uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<RecvUserVO> checkOrgRecv(List<RecvUserVO> voList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<LineUserVO> checkOrgAppLine(List<LineUserVO> voList) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  코드 목록을 가져온다.
     * </pre>
     * @param parentId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<AppCodeVO> listAppCode(String parentId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  코드를 수정한다.
     * </pre>
     * @param codeId
     * @param description
     * @throws Exception
     * @see  
     *
     */
    int updateAppCode(String codeId, String description) throws Exception;     
    
    
    /** 
     * 
     * <pre> 
     *  공람자그룹 목록을 가져온다.
     * </pre>
     * @param compId
     * @param registerId
     * @param registerDeptId
     * @param groupType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<PubViewGroupVO> listPubViewGroup(String compId, String registerId, String registerDeptId, String groupType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    void insertPubViewGroup(PubViewGroupVO gvo, List<PubViewUserVO> uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 등록한다.
     * </pre>
     * @param gvo
     * @throws Exception
     * @see  
     *
     */
    void insertPubViewGroup(PubViewGroupVO gvo) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자를 등록한다.
     * </pre>
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    void insertPubViewGroupUser(List<PubViewUserVO> uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자경로 목록을 가져온다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<PubViewUserVO> listPubViewLine(String compId, String pubviewGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     * 공람자정보 변경여부를 확인한다.
     * </pre>
     * @param voList
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<PubViewUserVO> checkOrgPubView(List<PubViewUserVO> voList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 삭제한다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     * @throws Exception
     * @see  
     *
     */
    void deletePubViewGroup(String compId, String pubviewGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자를 삭제한다.
     * </pre>
     * @param compId
     * @param pubviewGroupId
     * @throws Exception
     * @see  
     *
     */
    void deletePubViewGroupUser(String compId, String pubviewGroupId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 수정한다.
     * </pre>
     * @param gvo
     * @param uvoList
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    void updatePubViewGroup(PubViewGroupVO gvo, List uvoList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람자그룹 목록을 가져온다.(공람자그룹선택 팝업)
     * </pre>
     * @param compId
     * @param registerDeptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<PubViewGroupVO> listPubViewGroupP(String compId, String registerDeptId, String registerId) throws Exception;
    
    
    /** 
     * 
     * <pre> 
     *  등록된 회기 전체 리스트를 가져온다.
     * </pre>
     * @param compId
     * @return list
     * @throws Exception
     * @see  
     *
     */
    List<PeriodVO> listAllPeriod(String compId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  유효한 회기 리스트를 가져온다.
     * </pre>
     * @param compId
     * @return list
     * @throws Exception
     * @see  
     *
     */
    List<PeriodVO> listPeriod(String compId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  현재 회기를 가져온다.
     * </pre>
     * @param compId
     * @return vo
     * @throws Exception
     * @see  
     *
     */
    PeriodVO getCurrentPeriod(String compId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  현재 회기를 가져온다.
     * </pre>
     * @param compId
     * @return string
     * @throws Exception
     * @see  
     *
     */
    String getCurrentPeriodStr(String compId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  해당 회기에 대한 정보를 가져온다.
     * </pre>
     * @param compId, periodId
     * @return vo
     * @throws Exception
     * @see  
     *
     */
    PeriodVO getPeriod(String compId, String periodId) throws Exception;
    
    /** 
     * 
     * <pre> 
     *  최신 회기를 가져온다.
     * </pre>
     * @param compId, periodType
     * @return vo
     * @throws Exception
     * @see  
     *
     */
    PeriodVO getLatestPeriod(String compId, String periodType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  현재회기를 기준으로 다음회기가 없을 경우 다음회기를 추가한다.(편철에서 사용)
     * </pre>
     * @param compId
     * @return int
     * @throws Exception
     * @see  
     *
     */
    int insertPeriodAuto(String compId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  넘겨받은 회기를 기준으로 다음회기가 없을 경우 다음회기를 추가한다.(편철에서 사용)
     * </pre>
     * @param compId, periodId
     * @return int
     * @throws Exception
     * @see  
     *
     */
    int insertPeriodAuto(String compId, String periodId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  회기를 추가한다.
     * </pre>
     * @param compId, periodType
     * @return int
     * @throws Exception
     * @see  
     *
     */
    int insertPeriod(String compId, String periodType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  회기를 삭제한다.
     * </pre>
     * @param compId, periodId
     * @return int
     * @throws Exception
     * @see  
     *
     */
    int deletePeriod(String compId, String periodId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  회기 기간을 수정한다.
     * </pre>
     * @param compId, periodId, startDate, endDate
     * @return int
     * @throws Exception
     * @see  
     *
     */
    int updatePeriod(String compId, String periodId, String startDate, String endDate) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  수신자그룹 목록을 조회한다.
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    List<RecvGroupVO> selectRecvGroup() throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서공유부서 목록을 조회한다.
     * </pre>
     * @param shareDocDeptVO
     * @return List
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    List<ShareDocDeptVO> selectShareDeptList(ShareDocDeptVO shareDocDeptVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서공유부서를 등록한다.
     * </pre>
     * @param shareDocDeptVO.
     * @return int
     *  insert 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    int insertShareDept(ShareDocDeptVO shareDocDeptVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서공유부서를 삭제한다.
     * </pre>
     * @param shareDocDeptVO.
     * @return int
     *  insert 결과를 0(실패)/1(성공)로 반환한다.
     * @throws Exception
     * @see  
     *
     */
    int deleteShareDept(ShareDocDeptVO shareDocDeptVO) throws Exception;
    
}
