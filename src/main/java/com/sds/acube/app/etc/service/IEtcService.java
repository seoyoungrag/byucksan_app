package com.sds.acube.app.etc.service;

import java.util.List;

import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;

/** 
 *  Class Name  : EtcController.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 18. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 5. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.etc.service.IEtcService.java
 */

public interface IEtcService {
    // 공람게시 등록
    int insertPublicPost(PubPostVO publicPostVO) throws Exception;
    // 공람게시 삭제
    int deletePublicPost(PubPostVO publicPostVO) throws Exception;
    // 공람게시 열람자 삭제
    int deletePostReader(PubPostVO publicPostVO) throws Exception;
    // 공람게시 종료
    int closePublicPost(String[] publishIds, String compId, String currentDate) throws Exception;
    // 공람게시정보 조회
    public PubPostVO selectPublicPost(String compId, String docId) throws Exception;

    // 공람게시 열람자 추가
    int insertPostReader(PostReaderVO postReaderVO) throws Exception;
    int insertPostReader(List<PostReaderVO> postReaderVOs) throws Exception;

    boolean sendFSP(String userId, String appId, AppOptionVO appOptionVO, String currentDate) throws Exception;
}
