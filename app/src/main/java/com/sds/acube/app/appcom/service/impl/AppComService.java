package com.sds.acube.app.appcom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
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
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;


/**
 * Class Name : AppComService.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.appcom.service.impl.AppComService.java
 */

@SuppressWarnings("serial")
@Service("appComService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class AppComService extends BaseService implements IAppComService {

    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    
    // 파일 Insert
    @SuppressWarnings("unchecked")
    public int insertFile(List fileVOs) throws Exception {
	return commonDAO.insertList("appcom.insertFile", fileVOs);
    }


    // 파일 Insert
    public int insertFile(FileVO fileVO) throws Exception {
	return commonDAO.insert("appcom.insertFile", fileVO);
    }


    // 파일이력 Insert
    @SuppressWarnings("unchecked")
    public int insertFileHis(List fileHisVOs) throws Exception {
	return commonDAO.insertList("appcom.insertFileHis", fileHisVOs);
    }


    // 파일이력 Insert
    public int insertFileHis(FileHisVO fileHisVO) throws Exception {
	return commonDAO.insert("appcom.insertFileHis", fileHisVO);
    }

    
    // 파일 Update
    @SuppressWarnings("unchecked")
    public int updateBody(List fileVOs) throws Exception {
	int result = 0;
	int fileCount = fileVOs.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    result += updateBody((FileVO) fileVOs.get(loop));
	}
	
	return result;
    }


    // 파일 Update
    public int updateBody(FileVO fileVO) throws Exception {
	commonDAO.delete("appcom.deleteFile", fileVO);
	return commonDAO.modify("appcom.insertFile", fileVO);
    }

    
    public int updateBody(String compId, List<String> fileIds, List<StorFileVO> storFileVOs) throws Exception {
	int result = 0;
	int bodyCount = fileIds.size();
	for (int loop = 0; loop < bodyCount; loop++) {
	    result += updateBody(compId, fileIds.get(loop), storFileVOs.get(loop));
	}
	
	return result;
    }

    
    public int updateBody(String compId, String fileId, StorFileVO storFileVO) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", storFileVO.getDocid());
	map.put("tempYn", "N");
	map.put("sourceFileId", fileId);
	map.put("targetFileId", storFileVO.getFileid());
	map.put("fileName", storFileVO.getFilename());

	return commonDAO.modifyMap("appcom.updateFile", map);
    }

    
    // 파일 Delete
    public int deleteFile(FileVO fileVO) throws Exception {
	return commonDAO.delete("appcom.deleteFile", fileVO);
    }


    // 파일 Delete
    @SuppressWarnings("unchecked")
    public int deleteFile(List fileVOs) throws Exception {
	return commonDAO.deleteList("appcom.deleteFile", fileVOs);
    }


    // 파일 Delete
    public int deleteFile(Map<String, String> map) throws Exception {
	return commonDAO.deleteMap("appcom.deleteFileMap", map);
    }


    // 파일 Select
    @SuppressWarnings("unchecked")
    public List<FileVO> listFile(Map<String, String> map) throws Exception {
	return commonDAO.getListMap("appcom.listFile", map);
    }

    
    // 최종본문이력정보 
    @SuppressWarnings("unchecked")
    public List<FileHisVO> selectLastBodyInfo(Map<String, String> map) throws Exception {
        return commonDAO.getListMap("appcom.listLastBodyHis", map);
     }

    
    // 그룹 관리자용 파일 Select
    @SuppressWarnings("unchecked")
    public List<FileVO> listFileByGroupAdmin(Map<String, String> map) throws Exception {
	return commonDAO.getListMap("appcom.listFileByGroupAdmin", map);
    }

    
    // 발송정보 Insert
    public int insertSendInfo(SendInfoVO sendInfoVO) throws Exception {
	return commonDAO.insert("appcom.insertSendInfo", sendInfoVO);
    }


    // 발송정보 Select
    public SendInfoVO selectSendInfo(Map<String, String> map) throws Exception {
	return (SendInfoVO) commonDAO.getMap("appcom.selectSendInfo", map);
    }


    // 발송처리이력 Insert
    public int insertSendProc(SendProcVO sendProcVO) throws Exception {
	return commonDAO.insert("appcom.insertSendProc", sendProcVO);
    }


    // 소유부서 Insert
    public int insertOwnDept(OwnDeptVO ownDeptVO) throws Exception {
	return commonDAO.insert("appcom.insertOwnDept", ownDeptVO);
    }

    // 소유부서 Select
    @SuppressWarnings("unchecked")
    public List<OwnDeptVO> listOwnDept(Map<String, String> map) throws Exception {
	return commonDAO.getListMap("appcom.listOwnDept", map);
    }
    

    // 공람자 Insert
    @SuppressWarnings("unchecked")
    public int insertPubReader(List pubReaderVOs) throws Exception {
	//return commonDAO.insertList("appcom.insertPubReader", pubReaderVOs);
	int pubReaderVOsSize = pubReaderVOs.size();
	List<Object> pubReaderVOList = new ArrayList<Object>();
	for(int i=0; i<pubReaderVOsSize; i++) {
	    PubReaderVO pubReaderVO = (PubReaderVO)pubReaderVOs.get(i);
	    pubReaderVO.setPubReaderOrder(i);
	    pubReaderVOList.add(pubReaderVO);
	}
	return commonDAO.insertList("appcom.insertPubReader", pubReaderVOList);
    }


    // 공람자 Update
    @SuppressWarnings("unchecked")
    public int updatePubReader(List deleteList, List insertList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("appcom.deletePubReader", deleteList);
	result += commonDAO.insertList("appcom.insertPubReader", insertList);
	return result;
    }


    // 공람자 Update
    @SuppressWarnings("unchecked")
    public int updatePubReader(List deleteList, List insertList, List updateList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("appcom.deletePubReader", deleteList);
	result += commonDAO.insertList("appcom.insertPubReader", insertList);
	result += commonDAO.modifyList("appcom.updatePubReaderOrder", updateList);
	return result;
    }

    
    // 공람자 조회
    public PubReaderVO selectPubReader(PubReaderVO pubReaderVO) throws Exception {
	return (PubReaderVO) commonDAO.get("appcom.selectPubReader", pubReaderVO);
    }


    // 공람자목록 Select
    @SuppressWarnings("unchecked")
    public List<PubReaderVO> listPubReader(Map<String, String> map) throws Exception {
	return (List<PubReaderVO>) commonDAO.getListMap("appcom.listPubReader", map);
    }


    // 공람게시 조회자 목록
    @SuppressWarnings("unchecked")
    public List<PostReaderVO> listPostReader(String compId, String publishId, String docId) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("publishId", publishId);
	map.put("docId", docId);
	return (List<PostReaderVO>) commonDAO.getListMap("etc.listPostReader", map);
    }


    // 공람게시 조회자 목록
    public Page listPostReader(PubPostVO searchVO, int pageIndex) throws Exception {
	return this.commonDAO.getPagingList("etc.listPostReader", searchVO, pageIndex);

    }


    // 문서채번(REGI/DIST)
    @SuppressWarnings("unchecked")
    public int selectDocNum(DocNumVO docNumVO) throws Exception {
	String regi = appCode.getProperty("REGI", "REGI", "NCT"); // 등록대장(생산/접수)
	Map map = (Map) commonDAO.get("appcom.selectDocNum", docNumVO);
	if (map == null || map.isEmpty()) {
	    if (regi.equals(docNumVO.getNumType())) {
		return AppConfig.getIntProperty("regi_numbering", 1, "etc"); // 등록대장 채번
	    } else {
		return AppConfig.getIntProperty("numbering", 1, "etc"); // 채번
	    }
	} else {
	    Integer num = new Integer("" + map.get("num"));
	    return num.intValue();
	}
    }


    // 문서채번(REGI/DIST)
    public int updateDocNum(DocNumVO docNumVO) throws Exception {
	String regi = appCode.getProperty("REGI", "REGI", "NCT"); // 등록대장(생산/접수)
	if (regi.equals(docNumVO.getNumType())) {
	    docNumVO.setNum(AppConfig.getIntProperty("regi_numbering", 1, "etc")); // 등록대장 채번
	} else {
	    docNumVO.setNum(AppConfig.getIntProperty("numbering", 1, "etc"));
	}
	return commonDAO.modify("appcom.updateDocNum", docNumVO);
    }


    // 하위문서채번(REGI/DIST)
    @SuppressWarnings("unchecked")
    public int selectSubNum(SubNumVO subNumVO) throws Exception {
	Map map = (Map) commonDAO.get("appcom.selectSubNum", subNumVO);
	if (map == null || map.isEmpty()) {
	    return 1;
	} else {
	    Integer num = new Integer("" + map.get("subnum"));
	    return num.intValue();
	}
    }


    // 하위문서채번(REGI/DIST)
    public int updateSubNum(SubNumVO subNumVO) throws Exception {
	return commonDAO.modify("appcom.updateSubNum", subNumVO);
    }


    // 직인채번(SEAL/SIGN)
    @SuppressWarnings("unchecked")
    public int selectListNum(DocNumVO docNumVO) throws Exception {
	Map map = (Map) commonDAO.get("appcom.selectDocNum", docNumVO);
	if (map == null || map.isEmpty()) {
	    return 1;
	} else {
	    Integer num = new Integer("" + map.get("num"));
	    return num.intValue();
	}
    }


    // 직인채번(SEAL/SIGN)
    public int updateListNum(DocNumVO docNumVO) throws Exception {
	docNumVO.setNum(1);
	return commonDAO.modify("appcom.updateDocNum", docNumVO);
    }


    // 일상감사일지 등록
    public int insertAuditList(AuditListVO auditListVO) throws Exception {
	int result = 0;
	AuditListVO auditVO = (AuditListVO) commonDAO.get("appcom.selectAuditList", auditListVO);
	if (auditVO == null) {
	    if (commonDAO.insert("appcom.insertAuditList", auditListVO) > 0) {
		result = 1;
	    }
	} else {
	    if (commonDAO.modify("appcom.updateAuditList", auditListVO) > 0) {
		result = 2;
	    }
	}
	return result;
    }


    // 일상감사일지 삭제
    public int deleteAuditList(String compId, String docId, String currentDate) throws Exception {
	AuditListVO auditVO = new AuditListVO();

	auditVO.setCompId(compId);
	auditVO.setDocId(docId);
	auditVO.setDeleteDate(currentDate);

	return commonDAO.modify("appcom.deleteAuditList", auditVO);
    }


    /**
     * <pre> 
     * 편철정보 수정
     * </pre>
     * 
     * @param appBindVOs
     *            - 편철정보 리스트
     * @return 처리결과
     * @exception Exception
     * @see
     */
    public int updateBindInfo(List<AppBindVO> appBindVOs, DocHisVO docHisVO) throws Exception {
	int result = 0;
	int docCount = appBindVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppBindVO appBindVO = appBindVOs.get(loop);
	    result += updateBindInfo(appBindVO, docHisVO);
	}

	return result;
    }


    /**
     * <pre> 
     * 편철정보 수정
     * </pre>
     * 
     * @param appBindVO
     *            - 편철정보
     * @return 처리결과
     * @exception Exception
     * @see
     */
    public int updateBindInfo(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception {
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String dhu018 = appCode.getProperty("DHU018", "DHU018", "DHU"); // 편철변경

	// 편철수정이력
	docHisVO.setDocId(appBindVO.getDocId());
	docHisVO.setHisId(GuidUtil.getGUID());
	docHisVO.setUseDate(DateUtil.getCurrentDate());
	docHisVO.setUsedType(dhu018);
	logService.insertDocHis(docHisVO);
	// 편철정보 수정
	if (dpi001.equals(appBindVO.getUsingType())) {
	    return commonDAO.modify("appcom.updateAppBind", appBindVO);
	} else if (dpi002.equals(appBindVO.getUsingType())) {
	    return commonDAO.modify("appcom.updateEnfBind", appBindVO);
	} else {
	    String docId = appBindVO.getDocId();
	    if ("APP".equals(docId.substring(0, 3))) {
		return commonDAO.modify("appcom.updateAppBind", appBindVO);
	    } else {
		return commonDAO.modify("appcom.updateEnfBind", appBindVO);
	    }
	}
    }


    /**
     * <pre> 
     * 편철 부서복사
     * </pre>
     * 
     * @param appBindVO
     *            - 편철정보
     * @return 처리결과
     * @exception Exception
     * @see
     */
    public int copyBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception {
	commonDAO.delete("appcom.withdrawBind", appBindVO);
	return commonDAO.insert("appcom.copyBind", appBindVO);
    }


    /**
     * <pre> 
     * 편철 부서회수
     * </pre>
     * 
     * @param appBindVO
     *            - 편철정보
     * @return 처리결과
     * @exception Exception
     * @see
     */
    public int withdrawBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception {
	return commonDAO.delete("appcom.withdrawBind", appBindVO);
    }


    /**
     * <pre> 
     * 편철 부서이동
     * </pre>
     * 
     * @param appBindVO
     *            - 편철정보
     * @return 처리결과
     * @exception Exception
     * @see
     */
    public int moveBind(AppBindVO appBindVO, DocHisVO docHisVO) throws Exception {
	commonDAO.delete("appcom.deleteOwnBind", appBindVO);
	return commonDAO.modify("appcom.moveBind", appBindVO);
    }

    /**
     * <pre> 
     *  문서정보
     * </pre>
     * 
     * @param map
     * @return
     * @throws Exception
     * @see
     */
    public Object selectDocInfo(Map<String, String> map) throws Exception {
	String docId = map.get("docId");
	Object rtnObj = null;
	if (docId.indexOf("APP") != -1) {
	    rtnObj = commonDAO.getMap("approval.selectAppDoc", map);
	}else{
	    rtnObj = commonDAO.getMap("enforce.selectEnfDoc", map);
	}
	
	return rtnObj;
    }

    /**
     * <pre> 
     *  보고경로
     * </pre>
     * 
     * @param map
     * @return
     * @throws Exception
     * @see
     */
    public Object listLines(Map<String, String> map) throws Exception {
	String docId = map.get("docId");
	Object rtnObj = null;
	if (docId.indexOf("APP") != -1) {
	    rtnObj = commonDAO.getListMap("approval.listAppLine", map);
	} else {
	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setDocId(map.get("docId"));
	    enfLineVO.setCompId(map.get("compId"));
	    rtnObj = commonDAO.getList("enforce.selectEnfLineList", enfLineVO);
	}

	return rtnObj;
    }
    
     public boolean compareDocPassword (String compId, String docId , String encryptedPwd, String queryString) throws Exception{
	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setCompId(compId);
	appDocVO.setDocId(docId);
	Map map = (Map)commonDAO.get(queryString, appDocVO);
	if (encryptedPwd.equals(map.get("docpass"))) {
	    return true;
	} else {
	    return false;
	}
	
    }
}
