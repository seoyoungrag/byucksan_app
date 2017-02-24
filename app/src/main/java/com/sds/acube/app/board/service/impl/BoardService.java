package com.sds.acube.app.board.service.impl;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.anyframe.pagination.Page;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.board.service.IBoardService;
import com.sds.acube.app.board.vo.AppBoardReplyVO;
import com.sds.acube.app.board.vo.AppBoardVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : BoardService.java
 *  Description : 설명  
 *  Modification Information 
 *  
 *   수 정  일 : 2012. 6. 14.
 *   수 정  자 : 곽경종
 *   수정내용 : 
 *   
 * @author   jth8172 
 * @since  2012. 3. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.board.service.impl.BoardService.java
 */

@SuppressWarnings("serial")
@Service("boardService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BoardService extends BaseService implements IBoardService {


    /**
	 */
    @Autowired 
    private ICommonDAO commonDAO;
    
    @Inject
    @Named("bindService")
    private BindService bindService;
    
    // 게시목록조회
    public Page listBull(SearchVO searchVO, int page) throws Exception{
    	BindVO bindVO = bindService.getMinor(searchVO.getCompId(), searchVO.getDeptId(), searchVO.getBindingId());
    	if(bindVO!=null){
	    	searchVO.setOrgBindId(bindVO.getOrgBindId());
	    	searchVO.setBindSendType(bindVO.getSendType());
    	}
  	   	return (Page) this.commonDAO.getPagingList("board.listBoard", searchVO, page);
    }

    public Page listBull(SearchVO searchVO, int page, int pageSize) throws Exception{
    	BindVO bindVO = bindService.getMinor(searchVO.getCompId(), searchVO.getDeptId(), searchVO.getBindingId());
    	if(bindVO!=null){
	    	searchVO.setOrgBindId(bindVO.getOrgBindId());
	    	searchVO.setBindSendType(bindVO.getSendType());
    	}
    	return (Page) this.commonDAO.getPagingList("board.listBoard", searchVO, page, pageSize);
    }
    
    // 게시등록
    public int insertBull(AppBoardVO boardVO) throws Exception {
    	return this.commonDAO.insert("board.insertBull", boardVO);
    }
    
    // 수정 시 등록
    public int upInsertBull(AppBoardVO boardVO) throws Exception {
        return this.commonDAO.insert("board.upInsertBull", boardVO);
    }
    // 게시조회
    public AppBoardVO viewBull(AppBoardVO boardVO) throws Exception{
    	BindVO bindVO = bindService.getMinor(boardVO.getCompId(), boardVO.getDeptId(), boardVO.getBindingId());
    	if(bindVO!=null)
    		boardVO.setBindSendType(bindVO.getSendType());
    	return (AppBoardVO) this.commonDAO.get("board.viewBoard", boardVO);
    }

    // 게시조회
    public AppBoardVO viewBullShare(AppBoardVO boardVO) throws Exception{
    	BindVO bindVO = bindService.getMinor(boardVO.getCompId(), boardVO.getDeptId(), boardVO.getBindingId());
    	if(bindVO!=null)
    		boardVO.setBindSendType(bindVO.getSendType());
    	return (AppBoardVO) this.commonDAO.get("board.viewBoardShare", boardVO);
    }
    // 이전이력 조회
    public AppBoardVO versionView(AppBoardVO boardVO) throws Exception{
    	BindVO bindVO = bindService.getMinor(boardVO.getCompId(), boardVO.getDeptId(), boardVO.getBindingId());
    	if(bindVO!=null)
    		boardVO.setBindSendType(bindVO.getSendType());
        return (AppBoardVO) this.commonDAO.get("board.viewVersionBoard", boardVO);
    }
    
    // 다른버전에 수정
    public int modifyBull(AppBoardVO boardVO) throws Exception{
    	return this.commonDAO.modify("board.updateBull", boardVO);
    }
    
    // 기존버전에 수정
    public int modifyUpdateBull(AppBoardVO boardVO) throws Exception{
        return this.commonDAO.modify("board.modifyUpdateBull", boardVO);
    }
    
    // 첨부파일 등록
    public int modifyAttachBull(AppBoardVO boardVO) throws Exception{
    	return this.commonDAO.modify("board.updateAttach", boardVO);
    }
    
    //게시물 삭제
    public int deleteBull(AppBoardVO boardVO) throws Exception{
    	return  this.commonDAO.delete("board.deleteBull", boardVO);
    }
    
    //게시물삭제시 포함 댓글 삭제
    public int alldeleteBull(AppBoardVO boardVO) throws Exception{
    	return  this.commonDAO.delete("board.alldeleteBull", boardVO);
    }
    
    //조회수
    
    public int hitBull(AppBoardVO boardVO) throws Exception {
    	return this.commonDAO.modify("board.updateHitno", boardVO);
    }
    
    // 첨부파일조회
    @SuppressWarnings("unchecked")
	public List<FileVO> viewAttach(AppBoardVO boardVO) throws Exception{
    	return this.commonDAO.getList("board.viewAttach", boardVO);
    }
    
    // 첨부파일조회
    @SuppressWarnings("unchecked")
    public List<FileVO> viewAttachVersion(AppBoardVO boardVO) throws Exception{
        return this.commonDAO.getList("board.viewAttachVersion", boardVO);
    }
    
    // 첨부파일수정
    // 파일 Select
    @SuppressWarnings("unchecked")
    public List<FileVO> listFile(Map<String, String> map) throws Exception {
	return commonDAO.getListMap("board.listFile", map);
    }

    
    // 파일 Delete
    public int deleteFile(Map<String, String> map) throws Exception {
	return commonDAO.deleteMap("board.deleteFileMap", map);
    }
    
    //댓글 입력
    
    public int insertReply (AppBoardVO boardVO) throws Exception {
    	return this.commonDAO.insert("board.insertReply", boardVO);
    }

    // 댓글조회
    @SuppressWarnings("unchecked")
    public List<AppBoardReplyVO> viewReply(AppBoardReplyVO boardReplyVO) throws Exception{
    	return this.commonDAO.getList("board.viewReply", boardReplyVO);
    }

    //게시물 리플 삭제
    public int deleteReply(AppBoardReplyVO boardReplyVO) throws Exception{
    	return  this.commonDAO.delete("boardReply.delete",boardReplyVO);
    }
    
    //관리자 게시물  삭제
    public int adminDeleteBull(AppBoardVO boardVO) throws Exception{
    	return  this.commonDAO.delete("board.adminDelete",boardVO);
    }
    
    //관리자 리플  삭제
    public int adminDeleteReply(AppBoardVO boardVO) throws Exception{
    	return  this.commonDAO.delete("board.adminDeleteReply",boardVO);
    }
    
    // 버전조회
    @SuppressWarnings("unchecked")
    public List<AppBoardVO> viewBullv(AppBoardVO boardVO) throws Exception{
    	BindVO bindVO = bindService.getMinor(boardVO.getCompId(), boardVO.getDeptId(), boardVO.getBindingId());
    	if(bindVO!=null)
    		boardVO.setBindSendType(bindVO.getSendType());
        return this.commonDAO.getList("board.viewBoardv", boardVO);
    }
    
    @SuppressWarnings("unchecked")
    public List<AppBoardVO> viewBullvShare(AppBoardVO boardVO) throws Exception{
    	BindVO bindVO = bindService.getMinorShare(boardVO.getCompId(), boardVO.getDeptId(), boardVO.getBindingId());
    	if(bindVO!=null)
    		boardVO.setBindSendType(bindVO.getSendType());
        return this.commonDAO.getList("board.viewBoardv", boardVO);
    }
    
    // Title 조회
    @SuppressWarnings("unchecked")
    public List<AppBoardVO> viewBulltitle(AppBoardVO boardVO) throws Exception{
        return this.commonDAO.getList("board.viewBoardTitle", boardVO);
    }
    
    //권한조회
    public AppBoardVO selectBind(AppBoardVO boardVO) throws Exception{
        return (AppBoardVO) this.commonDAO.get("board.selectBind", boardVO);
    }
	

    // 게시목록조회
    public Page listBullShare(SearchVO searchVO, int page) throws Exception{
    	BindVO bindVO = bindService.getMinorShare(searchVO.getCompId(), searchVO.getDeptId(), searchVO.getBindingId());
    	if(bindVO!=null){
	    	searchVO.setOrgBindId(bindVO.getOrgBindId());
	    	searchVO.setBindSendType(bindVO.getSendType());
    	}
  	   	return (Page) this.commonDAO.getPagingList("board.listBoardShare", searchVO, page);
    }

    public Page listBullShare(SearchVO searchVO, int page, int pageSize) throws Exception{
    	BindVO bindVO = bindService.getMinorShare(searchVO.getCompId(), searchVO.getDeptId(), searchVO.getBindingId());
    	if(bindVO!=null){
	    	searchVO.setOrgBindId(bindVO.getOrgBindId());
	    	searchVO.setBindSendType(bindVO.getSendType());
    	}else{
    		searchVO.setOrgBindId("ROOT");
	    	searchVO.setBindSendType("DST001");
    	}
    	return (Page) this.commonDAO.getPagingList("board.listBoardShare", searchVO, page, pageSize);
    }
}
