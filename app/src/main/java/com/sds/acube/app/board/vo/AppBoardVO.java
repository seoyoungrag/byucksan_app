package com.sds.acube.app.board.vo;

import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;



/**
 * Class Name  : BoardVO.java 
 * Description : 설명  
 *  Modification Information 
 *  
 *  수 정  일 : 2012. 5. 29. 
 *  수 정  자 : 곽경종  
 *  수정내용 :  
 *  
 * @author   jth8172 
 * @since  2012. 3. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.board.vo.BoardVO.java
 */

public class AppBoardVO {
	
  
	/**
	 * 현재 페이지
	 */
	private int currentPage = 1;
	/**
	 * 전체리스트 수
	 */
	private int totalcount = 0;

	/**
	 */
	private String regNo;	  	 	//등록번호
	/**
	 */
	private String compId;	  	 	//회사ID
    /**
	 */
    private String boardId;	  	//게시판ID
    /**
	 */
    private String boardName;	  	//게시판명
    /**
	 */
    private String bullId;	  		//게시물ID
    /**
	 */
    private String bullTitle;	  	//게시제목
    /**
	 */
    private int 	hitno;	 		//조회수
    /**
	 */
    private String attfiles;	  	//첨부
    /**
	 */
    private String 	officialflag;	//공지여부
    /**
	 */
    private String contents;	  	//내용
    /**
	 */
    private String regId;	  		//등록자ID
    /**
	 */
    private String regName;	  	//등록자명
    /**
	 */
    private String regDeptId;	  	//등록자부서ID
    /**
	 */
    private String regDeptName; 	//등록자부서명
    /**
	 */
    private String regDate;	  	//등록일시

    /**
	 */
    private String expirationDate;	  	//도래일시
	/**
	 */
    private String modId;	  		//수정자ID
    /**
	 */
    private String modName;	  	//수정자명
    /**
	 */
    private String modDeptId;	  	//수정자부서ID
    /**
	 */
    private String modDeptName;	//수정자부서명
    /**
	 */
    private String modDate;	  	//수정일시
    
    /**
	 */
    private String recontents;      //댓글내용
    /**
	 */
    private String replyId;         //댓글 ID
    
    private String replyNo;          //댓글 갯수
    
	/** 파일정보 */
	private List<FileVO> fileInfos = new ArrayList<FileVO>();
	
	/**
	 * 첨부개수
	 */ 
	private int attachCount = 0;
   
	private String docOwnerId;          //소유자ID
	
	private String docOwnerName;          //소유자이름
    
	private String docVersion;          //문서버전
    
	private String isActive;          //사용여부
    
	private String docKeyword;          //키워드
	
	private String bindingId;		//캐비닛(편철) ID
	
	private String bindingName;		//캐비닛(편철) 명

	private String retentionPeriod; //보존년한
	
	private String retentionPeriodName; //보존년한 명

	private String securityType; //보안 체계
	
	private String tmpYn; //현재버전선택 여부

	private String isManager; //관리자 인지
	
	private String bindAuthority; //캐비닛 권한
	
	private String isOwner; //소유권자 인지, 작성자 인지

	private String userId; // 사용자 ID
	
	private String deptId; // 사용자 소속 부서 ID 
	
	private String bindSendType; // bind 종류(공유, 결재 등)
	/**
	 * @return the bindSendType
	 */
	public String getBindSendType() {
		return bindSendType;
	}

	/**
	 * @param bindSendType the bindSendType to set
	 */
	public void setBindSendType(String bindSendType) {
		this.bindSendType = bindSendType;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * @return the isManager
	 */
	public String getIsManager() {
		return isManager;
	}

	/**
	 * @param isManager the isManager to set
	 */
	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	/**
	 * @return the bindAuthority
	 */
	public String getBindAuthority() {
		return bindAuthority;
	}

	/**
	 * @param bindAuthority the bindAuthority to set
	 */
	public void setBindAuthority(String bindAuthority) {
		this.bindAuthority = bindAuthority;
	}

	/**
	 * @return the isOwner
	 */
	public String getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner the isOwner to set
	 */
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}

    /**
     * @return the tmpYn
     */
	public String gettmpYn() {
    return tmpYn;
    }
	
	/**
     * @param tmpYn the tmpYn to set
     */
    public void settmpYn(String tmpYn) {
      this.tmpYn = tmpYn;
    }
    /**
	 * @return the securityType
	 */
	public String getSecurityType() {
		return securityType;
	}

	/**
	 * @param securityType the securityType to set
	 */
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	/**
	 * @return the retentionPeriodName
	 */
	public String getRetentionPeriodName() {
		return retentionPeriodName;
	}

	/**
	 * @param retentionPeriodName the retentionPeriodName to set
	 */
	public void setRetentionPeriodName(String retentionPeriodName) {
		this.retentionPeriodName = retentionPeriodName;
	}
	
	/**
	 * @return the retentionPeriod
	 */
	public String getRetentionPeriod() {
		return retentionPeriod;
	}

	/**
	 * @param retentionPeriod the retentionPeriod to set
	 */
	public void setRetentionPeriod(String retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	/**
	 * @return the bindingId
	 */
	public String getBindingId() {
		return bindingId;
	}

	/**
	 * @param bindingId
	 *            the bindingId to set
	 */
	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}

	/**
	 * @return the bindingName
	 */
	public String getBindingName() {
		return bindingName;
	}

	/**
	 * @param bindingName
	 *            the bindingName to set
	 */
	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}
	
	public String getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(String docKeyword) {
		this.docKeyword = docKeyword;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getDocOwnerId() {
		return docOwnerId;
	}

	public void setDocOwnerId(String docOwnerId) {
		this.docOwnerId = docOwnerId;
	}

	public String getDocOwnerName() {
		return docOwnerName;
	}

	public void setDocOwnerName(String docOwnerName) {
		this.docOwnerName = docOwnerName;
	}

	/**
	 * @return the attachCount
	 */
	public int getAttachCount() {
		return attachCount;
	}

	/**
	 * @param attachCount
	 *            the attachCount to set
	 */
	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}

	/**
	 * @return the fileInfos
	 */
	public List<FileVO> getFileInfos() {
		return fileInfos;
	}

	/**
	 * @param fileInfos
	 *            the fileInfos to set
	 */
	public void setFileInfos(List<FileVO> fileInfos) {
		this.fileInfos = fileInfos;
	}

	/**
	 * @return the relplyNo
	 */
	public String getRelplyNo() {
		return replyNo;
	}

	/**
	 * @param relplyNo
	 *            the relplyNo to set
	 */
	public void setRelplyNo(String relplyNo) {
		this.replyNo = relplyNo;
	}

	/**
	 * @return the replyId
	 */
	public String getReplyId() {
		return replyId;
	}

	/**
	 * @param replyId
	 *            the replyId to set
	 */
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	/**
	 * @return the recontents
	 */
	public String getRecontents() {
		return recontents;
	}

	/**
	 * @param recontents
	 *            the recontents to set
	 */
	public void setRecontents(String recontents) {
		this.recontents = recontents;
	}

	/**
	 * @return the rowNum
	 */
	public String getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum
	 *            the rowNum to set
	 */
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 */
	private String rowNum; // 게시물순서

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the totalcount
	 */
	public int getTotalcount() {
		return totalcount;
	}

	/**
	 * @param totalcount
	 *            the totalcount to set
	 */
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	/**
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}

	/**
	 * @param compId
	 *            the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}

	/**
	 * @return the boardId
	 */
	public String getBoardId() {
		return boardId;
	}

	/**
	 * @param boardId
	 *            the boardId to set
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * @return the boardName
	 */
	public String getBoardName() {
		return boardName;
	}

	/**
	 * @param boardName
	 *            the boardName to set
	 */
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	/**
	 * @return the bullId
	 */
	public String getBullId() {
		return bullId;
	}

	/**
	 * @param bullId
	 *            the bullId to set
	 */
	public void setBullId(String bullId) {
		this.bullId = bullId;
	}

	/**
	 * @return the bullTitle
	 */
	public String getBullTitle() {
		return bullTitle;
	}

	/**
	 * @param bullTitle
	 *            the bullTitle to set
	 */
	public void setBullTitle(String bullTitle) {
		this.bullTitle = bullTitle;
	}

	/**
	 * @return the hitno
	 */
	public int getHitno() {
		return hitno;
	}

	/**
	 * @param hitno
	 *            the hitno to set
	 */
	public void setHitno(int hitno) {
		this.hitno = hitno;
	}

	/**
	 * @return the attfiles
	 */
	public String getAttfiles() {
		return attfiles;
	}

	/**
	 * @param attfiles
	 *            the attfiles to set
	 */
	public void setAttfiles(String attfiles) {
		this.attfiles = attfiles;
	}

	/**
	 * @return the officialflag
	 */
	public String getOfficialflag() {
		return officialflag;
	}

	/**
	 * @param officialflag
	 *            the officialflag to set
	 */
	public void setOfficialflag(String officialflag) {
		this.officialflag = officialflag;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the regName
	 */
	public String getRegName() {
		return regName;
	}

	/**
	 * @param regName
	 *            the regName to set
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}

	/**
	 * @return the regDeptId
	 */
	public String getRegDeptId() {
		return regDeptId;
	}

	/**
	 * @param regDeptId
	 *            the regDeptId to set
	 */
	public void setRegDeptId(String regDeptId) {
		this.regDeptId = regDeptId;
	}

	/**
	 * @return the regDeptName
	 */
	public String getRegDeptName() {
		return regDeptName;
	}

	/**
	 * @param regDeptName
	 *            the regDeptName to set
	 */
	public void setRegDeptName(String regDeptName) {
		this.regDeptName = regDeptName;
	}

   /**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
		
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId
	 *            the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the modName
	 */
	public String getModName() {
		return modName;
	}

	/**
	 * @param modName
	 *            the modName to set
	 */
	public void setModName(String modName) {
		this.modName = modName;
	}

	/**
	 * @return the modDeptId
	 */
	public String getModDeptId() {
		return modDeptId;
	}

	/**
	 * @param modDeptId
	 *            the modDeptId to set
	 */
	public void setModDeptId(String modDeptId) {
		this.modDeptId = modDeptId;
	}

	/**
	 * @return the modDeptName
	 */
	public String getModDeptName() {
		return modDeptName;
	}

	/**
	 * @param modDeptName
	 *            the modDeptName to set
	 */
	public void setModDeptName(String modDeptName) {
		this.modDeptName = modDeptName;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate
	 *            the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(" , ").append(currentPage).append(super.toString());
    builder.append("AppBoardVO [currentPage=").append(currentPage).append(", totalcount=")
        .append(totalcount).append(", regNo=").append(regNo).append(", compId=").append(compId)
        .append(", boardId=").append(boardId).append(", boardName=").append(boardName)
        .append(", bullId=").append(bullId).append(", bullTitle=").append(bullTitle)
        .append(", hitno=").append(hitno).append(", attfiles=").append(attfiles)
        .append(", officialflag=").append(officialflag).append(", contents=").append(contents)
        .append(", regId=").append(regId).append(", regName=").append(regName)
        .append(", regDeptId=").append(regDeptId).append(", regDeptName=").append(regDeptName)
        .append(", regDate=").append(regDate).append(", modId=").append(modId).append(", modName=")
        .append(modName).append(", modDeptId=").append(modDeptId).append(", modDeptName=")
        .append(modDeptName).append(", modDate=").append(modDate).append(", recontents=")
        .append(recontents).append(", replyId=").append(replyId).append(", replyNo=")
        .append(replyNo).append(", fileInfos=").append(fileInfos).append(", attachCount=")
        .append(attachCount).append(", docOwnerId=").append(docOwnerId).append(", docOwnerName=")
        .append(docOwnerName).append(", docVersion=").append(docVersion).append(", isActive=")
        .append(isActive).append(", docKeyword=").append(docKeyword).append(bindingId).append(bindingName)
        .append(", rowNum=").append(rowNum).append("]");
    return builder.toString();
  }
}
