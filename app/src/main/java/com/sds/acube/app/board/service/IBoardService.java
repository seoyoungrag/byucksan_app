package com.sds.acube.app.board.service;

import java.util.List;
import java.util.Map;
import org.anyframe.pagination.Page;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.board.vo.AppBoardReplyVO;
import com.sds.acube.app.board.vo.AppBoardVO;
import com.sds.acube.app.list.vo.SearchVO;

 
/** 
 *  Class Name  : IBoardService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 14. <br>
 *  수 정  자 : 곽경종  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jth8172 
 *  @since 2012. 3. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.board.service.IBoardService.java
 */

public interface IBoardService {

    // 게시목록조회
    public Page listBull(SearchVO searchVO, int page) throws Exception;
    
    public Page listBull(SearchVO searchVO, int page, int pageSize) throws Exception;
    
    // 게시등록
    public int insertBull(AppBoardVO boardVO) throws Exception;
    
   // 수정 시 등록
    public int upInsertBull(AppBoardVO boardVO) throws Exception;
    
    // 게시조회
    public AppBoardVO viewBull(AppBoardVO boardVO) throws Exception;
    
    // 이전이력 조회
    public AppBoardVO versionView(AppBoardVO boardVO) throws Exception;
    
    // 다른버전에 수정
    public int modifyBull(AppBoardVO boardVO) throws Exception;
    
    // 기존버전에 수정
    public int modifyUpdateBull(AppBoardVO boardVO) throws Exception;
    
	// 게시 첨부 수정
    public int modifyAttachBull(AppBoardVO boardVO) throws Exception;
    
    //게시물 삭제
    public int deleteBull(AppBoardVO boardVO) throws Exception;
    
    //게시물삭제시 포함 댓글 삭제
    public int alldeleteBull(AppBoardVO boardVO) throws Exception;
    
    //게시물 조회수
    public int hitBull (AppBoardVO boardVO) throws Exception;
    
    // 첨부파일게시조회
    public List<FileVO> viewAttach(AppBoardVO boardVO) throws Exception;
    
    // 이전 첨부 조회
    public List<FileVO> viewAttachVersion(AppBoardVO boardVO) throws Exception;
    
    // 첨부파일 불러오기
    public List<FileVO> listFile(Map<String, String> map) throws Exception;
   
    //첨부파일 삭제
    public int deleteFile(Map<String, String> map) throws Exception;
    
    //댓글입력
    public int insertReply(AppBoardVO boardVO) throws Exception;
    
    //댓글조회
    public List<AppBoardReplyVO> viewReply(AppBoardReplyVO boardReplyVO) throws Exception;
    
    //댓글삭제
    public int deleteReply(AppBoardReplyVO boardReplyVO) throws Exception;
    
    //관리자 삭제
    public int adminDeleteBull(AppBoardVO boardVO) throws Exception;
    
    //관리자 리플 삭제
    public int adminDeleteReply(AppBoardVO boardVO) throws Exception;
    
    //버전 조회
    public List<AppBoardVO> viewBullv(AppBoardVO boardVO) throws Exception;
    public List<AppBoardVO> viewBullvShare(AppBoardVO boardVO) throws Exception;
    
    
    
    //Title 조회
    public List<AppBoardVO> viewBulltitle(AppBoardVO boardVO) throws Exception;
    
    //권한조회
    public AppBoardVO selectBind(AppBoardVO boardVO) throws Exception;
    
	public Page listBullShare(SearchVO searchVO, int parseInt, int i) throws Exception;
	
	public AppBoardVO viewBullShare(AppBoardVO boardVO) throws Exception;
}
