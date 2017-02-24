package com.sds.acube.app.relay.service;

import java.io.File;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.relay.vo.ContentVO;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;

/** 
 *  Class Name  : IRelaySendService.java <br>
 *  Description : 문서유통(송신) Interface <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 4. 19. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 4. 19.
 *  @version 1.0 
 *  @see  com.sds.acube.app.relay.service.IRelaySendService.java
 */

public interface IRelaySendService {
	
	// 스케줄러 Relay Queue 발송문서 리스트 가져오기
	public List<RelayVO> getSendListInfo(RelayVO relayQueueVO) throws Exception;
	
	// 문서유통(발송문서) Pack을 구성하기위한 정보 가져오기
	public List<PackInfoVO> getSendPackListInfo(RelayVO relayQueueVO) throws Exception;
	
	// 문서유통(발송문서) Ack Pack을 구성하기위한 정보 가져오기
	public List<PackInfoVO> getSendAckPackListInfo(RelayVO relayQueueVO) throws Exception;

	// 문서유통을 위한 Pubdoc 정보를 생성해서 xml 완성 후 File형태로 반환
	public File getMakePubdocXml(PackInfoVO packInfoVO) throws Exception;
	
	// 문서유통을 위한(Pack - Content : pubdoc) 본문부분 생성
	public void getContentPubdoc(PackInfoVO packInfoVO) throws Exception;
	
	// 문서유통을 위한(Pack - Content : attach, seal, sign, logo, symbol) 본문부분 생성
	public ContentVO getContentEtc(FileVO fileVO) throws Exception;
	
	// 발송문서 첨부파일 전체 STOR -> WAS 가져와서 Base64Encoding해 FileVO.imageData에 bytes형태로 집어넣는다.
	public List<FileVO> getAttach(PackInfoVO packInfoVO) throws  NullPointerException, IllegalArgumentException, Exception;
	
	// DTD 검사를 위한 첨부 내용을 제외한 PackVO를 Xml형태로 변환하여 File로 저장
	public File makePackXmlDTD(PackInfoVO packInfoVO) throws Exception;
		
	// PackVO를 Xml형태로 변환하여 File로 저장
	public File makePackXmlFile(PackInfoVO packInfoVO, File tmpPackFile) throws Exception;
	
	// 문서발송 큐 삭제
	public int deleteRelayQueue(RelayVO relayQueueVO) throws Exception;
}
