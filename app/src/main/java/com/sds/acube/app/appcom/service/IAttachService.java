package com.sds.acube.app.appcom.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.common.vo.DrmParamVO;

/** 
 *  Class Name  : IAttachService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 24. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 24.
 *  @version 1.0 
 *  @see  com.sds.acube.app.appcom.service.IAttachService.java
 */

public interface IAttachService {
    boolean downloadAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception;
    boolean downloadAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception;

    boolean downloadAttach(String docId, FileVO fileVO, DrmParamVO drmParamVO) throws Exception;
    boolean downloadAttach(String docId, List<FileVO> list, DrmParamVO drmParamVO) throws Exception;
    boolean downloadAttach(String docId, List<FileVO> list,String path, DrmParamVO drmParamVO) throws Exception;
    
    StorFileVO uploadAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception;
    List<StorFileVO> uploadAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception;

    FileVO uploadAttach(String docId, FileVO fileVO, DrmParamVO drmParamVO) throws Exception;
    List<FileVO> uploadAttach(String docId, List<FileVO> list, DrmParamVO drmParamVO) throws Exception;

    boolean updateAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception;
    boolean updateAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception;
    
    List<FileVO> listAttach(Map<String, String> map) throws Exception;
    List<FileHisVO> listAttachHis(Map<String, String> map) throws Exception;
    FileVO selectBody(Map<String, String> map) throws Exception;
    FileHisVO selectBodyHis(Map<String, String> map) throws Exception;
}
