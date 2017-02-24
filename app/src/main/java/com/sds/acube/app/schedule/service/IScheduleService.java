package com.sds.acube.app.schedule.service;

/**
 * Class Name : IScheduleService.java <br>
 * Description : 스케줄러서비스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 25. <br>
 * 수 정 자 : Timothy <br>
 * 수정내용 : <br>
 * 
 * @author Timothy
 * @since 2011. 3. 25.
 * @version 1.0
 * @see com.sds.acube.app.ws.server.service.IEsbAppService.java
 */

public interface IScheduleService {
    
    // 각종그룹 생성(산업은행:공람자그룹, 대우증권/산은캐피탈:수신자그룹)
    public void manageAnyGroup() throws Exception;
    
    // 문서기간/편철 일괄생성 배치
    public void makeBindBatch() throws Exception;
    
    // 전자결재 접근이력 삭제
    public void removeAccessHistory() throws Exception;
    
    // 전자결재 연계이력 삭제
    public void removeExchangeHistory() throws Exception;
    
    // 전자결재 임시폴더 삭제
    public void deleteAPPTempFolder() throws Exception;

    // 포털 임시폴더 삭제
//    public void deleteEPTempFolder() throws Exception;

    // NDISC 임시폴더 삭제
//    public void deleteNDISCTempFolder() throws Exception;
    
    // 수신자그룹 동기화
    public void syncAnyGroup() throws Exception;
//    public void syncAnyGroup(String executeSchedule) throws Exception;

	// 문서유통 Queue 발송 처리
    public void relaySend() throws Exception;
    
    // 문서유통 수신 처리
    public void relayRecv() throws Exception;
    
	// 문서유통 임시폴더 삭제
    public void workingDelete() throws Exception;
    
    // Legacy 연계 ack 발송 처리
    public void sendLegacy() throws Exception;
    
    // Legacy 연계 수신 처리
    public void receiveLegacy() throws Exception;
    
    // Legacy 연계 폴더 삭제 처리
    public void legacyDelete() throws Exception;
    
    // Mobile 결재
    public void processMobileApp() throws Exception;
    
}
