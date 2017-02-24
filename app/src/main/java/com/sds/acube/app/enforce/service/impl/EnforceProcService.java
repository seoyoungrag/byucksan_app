package com.sds.acube.app.enforce.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;


/**
 * Class Name : EnfLineService.java <br> Description : 접수, 반송, 이송 등의 프로세스 개별 처리 <br> Modification Information <br> <br> 수 정 일 : 2011 4. 18 <br> 수 정 자 : jth8172 <br> 수정내용 : <br>
 * @author  jth8172
 * @since  2011 4. 18
 * @version  1.0
 * @see  com.kdb.portal.enforce.service.EnfLineService.java
 */
@SuppressWarnings("serial")
@Service("EnforceProcService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnforceProcService extends BaseService implements IEnforceProcService {

	/**
	 */
	@Autowired 
	private ICommonDAO commonDAO;

	//접수문서정보
	public EnfDocVO selectEnfDoc(Map<String, String> searchMap) throws Exception {
		return (EnfDocVO) this.commonDAO.getMap("enforceAccept.selectEnfDoc", searchMap);
	}

	//접수문서 수신자목록정보
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnfRecv(Map<String, String> searchMap) throws Exception {
		return (List<EnfRecvVO>) this.commonDAO.getListMap("enforceAccept.selectEnfRecv", searchMap);
	}

	//접수문서 수신자정보(사용자)
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnfRecvUser(Map<String, String> searchMap) throws Exception {
		return (List<EnfRecvVO>) this.commonDAO.getListMap("enforceAccept.selectEnfRecvUser", searchMap);
	}

	//접수문서 수신자정보(부서)
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnfRecvDeptRecv(Map<String, String> searchMap) throws Exception {
		return (List<EnfRecvVO>) this.commonDAO.getListMap("enforceAccept.selectEnfRecvDeptRecv", searchMap);
	}
	//접수문서 참조자정보(부서)
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnfRecvDeptRef(Map<String, String> searchMap) throws Exception {
		return (List<EnfRecvVO>) this.commonDAO.getListMap("enforceAccept.selectEnfRecvDeptRef", searchMap);
	}

	//접수문서 수신자정보(수신자순서(key))
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnfRecvOrder(Map<String, String> searchMap) throws Exception {
		return (List<EnfRecvVO>) this.commonDAO.getListMap("enforceAccept.selectEnfRecvOrder", searchMap);
	}

	//접수문서 최초 배부부서정보(주 배부부서)
	public EnfRecvVO selectEnfRecvMinReceiverOrder(EnfRecvVO enfRecvVO) throws Exception {
		return (EnfRecvVO) this.commonDAO.get("enforceAccept.selectEnfRecvMinReceiverOrder", enfRecvVO);
	}

	//접수문서 마지막 배부부서정보
	public EnfRecvVO selectEnfRecvMaxReceiverOrder(EnfRecvVO enfRecvVO) throws Exception {
		return (EnfRecvVO) this.commonDAO.get("enforceAccept.selectEnfRecvMaxReceiverOrder", enfRecvVO);
	}

	//접수처리이력입력
	public int setEnfProc(EnfProcVO enfProcVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfProc", enfProcVO);
	}

	//접수처리이력변경
	public int modiEnfProc(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfProc", enfDocVO);
	}

	//접수문서복제
	public int setEnfDocDup(EnfDocVO enfDocVO) throws Exception{
		return this.commonDAO.insert("enforceAccept.insertEnfDocDup", enfDocVO);
	}
	//배부문서복제
	public int setEnfDocDist(EnfDocVO enfDocVO) throws Exception{
		return this.commonDAO.insert("enforceAccept.insertEnfDocDist", enfDocVO);
	}
	//생산문서 수신자정보 열람일자 업데이트
	public int updateAppRecvReader(AppRecvVO appRecvVO) throws Exception{
		return this.commonDAO.modify("enforceAccept.updateAppRecvOnRead", appRecvVO);
	}
	//접수문서 열람시 수신일자 업데이트
	public int updateEnfDocReader(Map<String, String> modifyMap) throws Exception{
		return this.commonDAO.modifyMap("enforceAccept.updateEnfDocOnRead", modifyMap);
	}
	//배부,접수문서 열람시 조회일자 업데이트
	public int updateEnfRecvReader(EnfRecvVO enfRecvVO) throws Exception{
		return this.commonDAO.modify("enforceAccept.updateEnfRecvOnRead", enfRecvVO);
	}

	//생산문서 수신자정보 업데이트(접수)
	public int setAppRecvAccept(AppRecvVO appRecvVO) throws Exception{
		return this.commonDAO.modify("enforceAccept.updateAppRecvOnAccept", appRecvVO);
	}

	//생산문서 수신자 정보 업데이트(배부)
	public int setAppRecvDist(AppRecvVO appRecvVO) throws Exception{
		return this.commonDAO.modify("enforceAccept.updateAppRecvOnDist", appRecvVO);
	}
	//접수문서 신규 수신자정보생성(접수)
	public int setEnfRecvAddAccept(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfRecvOnAccept", enfRecvVO);
	}   	
	//접수문서 신규 수신자정보생성(배부)
	public int setEnfRecvAddDist(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfRecvOnDist", enfRecvVO);
	}   	
	//접수문서수신자정보삭제
	public int setEnfRecvDelAccept(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfRecvOnAccept", enfRecvVO);
	}
	//배부후 접수한 문서 원본 삭제
	public int setEnfDelDistributeAccept(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfDelDistributeAccept", enfDocVO);
	}
	//배부후 접수한 파일 정보 삭제
	public int setEnfDelDistributeFileInfo(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfDistributeFileInfo", enfDocVO);
	}
	//파일정보복제
	public int setFileInfoDupAccept(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfFileDupAccept", enfDocVO);
	}
	//파일정보복제
	public int setFileInfoDupDist(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfFileDupDistribute", enfDocVO);
	}
	//하위기관으로 배부시 배부파일정보복제
	public int setFileInfoDistToSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfFileDistToSub", enfDocVO);
	}
	//접수문서정보 모두접수시 삭제
	public int setEnfDocAfterAccept(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfDocAfterAccept", enfDocVO);
	}	
	//하위기관 배부시에 배부를 위한  배부문서정보 추가복제
	public int setEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfDocDistToSub", enfDocVO);
	}	
	//하위기관 배부시에 배부를 위한  문서수신자정보 추가생성
	public int setEnfRecvAddDistToSub(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfRecvOnDistToSub", enfRecvVO);
	}   	
	//하위기관에서 배부시에 상위기관에 접수문서정보 복제
	public int setEnfDocDupDistOnSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertEnfDocDupDistOnSub", enfDocVO);
	}
	
	//하위기관에서 배부시에 상위기관의 문서수신정보 변경
	public int updateEnfRecvDistOnSub(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfRecvDistOnSub", enfRecvVO);
	}

	//하위기관에서 배부시에 상위기관 문서ID변경에 따라 하위기관 원본문서ID 변경
	public int updateEnfDocDistOnSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfDocDistOnSub", enfDocVO);
	}
	
	//하위기관에서 배부시에 상위기관 배부의 마지막 문서일 경우 상위기관 파일정보 삭제
	public int setFileInfoDelDistOnSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteFileInfoDistOnSub", enfDocVO);
	}
	
	//파일정보 모두접수시 삭제
	public int setFileInfoAfterAccept(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteFileInfoAfterAccept", enfDocVO);
	}
	//소유부서정보생성
	public int setOwnDept(OwnDeptVO ownDeptVO) throws Exception {
		return this.commonDAO.insert("enforceAccept.insertOwnDept", ownDeptVO);
	}

	//생산문서수신자정보변경(이송)
	public int setAppRecvMove(AppRecvVO appRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateAppRecvMove", appRecvVO);
	}

	//하위기관으로 이송시 접수문서 정보 업데이트
	public int setEnfDocMove(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfDocMove", enfDocVO);
	}

	
	//생산문서수신자정보변경(반송)
	public int setAppRecvReturn(AppRecvVO appRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateAppRecvReturn", appRecvVO);
	}

	//접수문서수신자정보변경(이송)
	public int setEnfRecvMove(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfRecvMove", enfRecvVO);
	}

	//접수문서수신자정보삭제
	public int setEnfRecvDel(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfRecv", enfRecvVO);
	}

	//접수문서정보삭제(반송)
	public int setEnfDocReturn(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfDocReturn", enfDocVO);
	}

	//파일정보삭제(반송)
	public int setFileInfoReturn(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteFileInfoReturn", enfDocVO);
	}

	//생산문서정보변경(반송)
	public int updateAppDocSendReturn(AppDocVO appDocVO) throws Exception {
		return this.commonDAO.modify("enforceSend.updateAppDocState", appDocVO);
	}    

	//본문파일정보 변경
	public int updateBodyFileInfo( FileVO fileVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateBodyFileInfo", fileVO);
	}

	//최종발송의견
	public SendProcVO selectLastSendOpinion(SendProcVO sendProcVO) throws Exception {
		return (SendProcVO) this.commonDAO.get("enforceAccept.selectLastSendProcOpinion", sendProcVO);
	}
	//최종 접수처리의견
	public EnfProcVO selectLastEnfOpinion(EnfProcVO enfProcVO) throws Exception {
		return (EnfProcVO) this.commonDAO.get("enforceAccept.selectLastEnfProcOpinion", enfProcVO);
	}


	//생산문서수신자정보변경(재발송요청)
	public int setAppRecvReSendRequest(AppRecvVO appRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateAppRecvReSendRequest", appRecvVO);
	}

	//접수문서정보변경(재배부요청,재배부)
	public int setEnfDocState(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfDocState", enfDocVO);
	}

	//접수문서수신정보변경(재배부요청,재배부)
	public int setEnfRecvState(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfRecvState", enfRecvVO);
	}

	//접수처리이력삭제(하위기관에 배부된 문서를 재배부요청)
	public int setEnfProcDel(EnfProcVO enfProcVO) throws Exception {
		return this.commonDAO.delete("enforceAccept.deleteEnfProc", enfProcVO);
	}

	//배부회수문서를 반송이나 재배부요청시 삭제처리(배부대장)
	public int setEnfDocDelete(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfDocDelete", enfDocVO);
	}

	//접수문서수신정보추가(재배부시 추가부서)
	public int setEnfRecvAddReDist(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.insertEnfRecvAddReDist", enfRecvVO);
	}

	//접수문서첨부파일 갯수정보변경
	public int initEnfDocAttachCount(Map<String, String> modifyMap) throws Exception {
		return this.commonDAO.modifyMap("enforceAccept.updateAttachCnt", modifyMap);
	}

	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> getRecvList(Map<String, String> searchMap) throws Exception {
		return this.commonDAO.getListMap("enforceAccept.selectEnfRecv", searchMap);
	}

	//배부회수가능목록조회(배부대장에서 배부회수)
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnableDistributeWithdraw(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.getList("enforceAccept.selectEnableDistributeWithdraw", enfRecvVO);
	}

	//하위기관에 배부한 배부문서 정보 받기
	@SuppressWarnings("unchecked")
    public List<EnfDocVO> selectEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.getList("enforceAccept.selectEnfDocDistToSub", enfDocVO);
	}

	//접수자 정보변경(하위기관으로의 배부 및 재배부요청시 상위기관의 접수자 정보 변경)
	public int updateEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception {
		return this.commonDAO.modify("enforceAccept.updateEnfDocDistToSub", enfDocVO);
	}

	//배부대장에서 반송이나 재배부가능목록조회
	@SuppressWarnings("unchecked")
	public List<EnfRecvVO> selectEnableReturnOnDist(EnfRecvVO enfRecvVO) throws Exception {
		return this.commonDAO.getList("enforceAccept.selectEnableReturnOnDist", enfRecvVO);
	}

}