package com.sds.acube.app.relay.service;

import java.io.File;

import org.xml.sax.SAXException;

import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;

/**
 * Class Name  : IRelayService.java <br> Description : 문서유통 Interface <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 20. <br> 수 정  자 : 김상태  <br> 수정내용 :  <br>
 * @author    김상태 
 * @since   2012. 3. 20.
 * @version   1.0 
 * @see com.sds.acube.app.relay.service.IRelayAckService.java
 */

public interface IRelayAckService {
	
	// 발송자정보 저장
	public int insertPubSendInfo(RelayVO relaySenderVO) throws Exception;
	
	// 기관간 문서 큐 저장
	public int insertPubQueueInfo(RelayVO relayQueueVO) throws Exception;
	
	// 기관간 수신정보 저장(발신)
	public int insertPubRecvInfoSend(RelayVO relayRecvVO) throws Exception;
	
	// 기간관 수진정보 저정(수신)
	public int insertPubRecvInfoReceive(RelayVO relayRecvVO) throws Exception;
	
	// 재발송 요청내용 저장
	public int insertOpinionInfo(RelayVO opinionVO) throws Exception;
	
	// 기간관 수신문서 enfDocId 갱신
	public int insertRelayDocInfo(RelayVO updateRelayRecvVO) throws Exception;
	
	// 기간관 수신문서 enfDocId 삭제
	public int deleteRelayDocInfo(RelayVO updateRelayRecvVO) throws Exception;

	// 문서유통처리중 오류가 발생시 오류사항과 파일을 DB와 Stor에 등록한다.
	public void logEx(PackInfoVO packInfoVO, String errCode, String errMsg, File file) throws Exception;
	
	// 유통문서 유효성 검사
	public void isRelayValidate(File recvFile, PackInfoVO packInfoVO) throws SAXException, Exception;

	// 유통문서 Ack 메세지 입력
	public void sendAckMessage(PackInfoVO packInfoVO, String detailMessge, String ackType, String procType) throws Exception;
	
	// 문서유통 수신문서 정보 가져오기(WEB 호출)
	public PackInfoVO getRecvPubdoc(String docId) throws Exception;

}
