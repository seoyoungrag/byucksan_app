package com.sds.acube.app.list.vo;

/**
 * Class Name  : ListVO.java <br> Description : 함/대장 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  ListVO
 */ 
public class ListVO {
	
	/**
	 * 현재 페이지
	 */
	private int currentPage = 1;
	/**
	 * 전체리스트 수
	 */
	private int totalcount = 0;
	/**
	 * 문서제목
	 */
	private String title;
	/**
	 * 삭제버튼 권한여부
	 */
	private String deleteButtonAuthYn = "N";
	/**
	 * 반송버튼 권한여부
	 */
	private String returnButtonAuthYn = "N";
	/**
	 * 인쇄버튼 권한여부
	 */
	private String printButtonAuthYn = "N";
	/**
	 * 저장버튼 권한여부
	 */
	private String saveButtonAuthYn = "N";
	/**
	 * 검색버튼 권한여부
	 */
	private String searchButtonAuthYn = "N";
	/**
	 * 공람지정버튼 권한여부
	 */
	private String displayAppointButtonAuthYn = "N";
	/**
	 * 공람처리버튼 권한여부
	 */
	private String displayProgressButtonAuthYn = "N";
	/**
	 * 후열처리버튼 권한여부
	 */
	private String rearProgressButtonAuthYn = "N";
	/**
	 * 통보처리버튼 권한여부
	 */
	private String informButtonAuthYn = "N";
	/**
	 * 비전자문서등록버튼 권한여부
	 */
	private String noElecDocButtonAuthYn = "N";
	/**
	 * 서명인날인등록버튼 권한여부
	 */
	private String stampRegistButtonAuthYn = "N";
	/**
	 * 직인날인등록버튼 권한여부
	 */
	private String sealRegistButtonAuthYn = "N";
	/**
	 * 닫기버튼 권한여부
	 */
	private String closeButtonAuthYn = "N";
	/**
	 * 선택버튼 권한여부
	 */
	private String selectButtonAuthYn = "N";
	/**
	 * 게시 종료 권한여부
	 */
	private String publishEndAuthYn = "N";
	/**
	 * 등록 버튼
	 */
	private String registButtonAuthYn = "N";
	/**
	 * 취소 버튼
	 */
	private String cancelButtonAuthYn = "N";
	/**
	 * 수정 버튼
	 */
	private String modifyButtonAuthYn = "N";
	/**
	 * 등록 취소 버튼
	 */
	private String unRegistButtonAuthYn = "N";
	/**
	 * 승인 버튼 권한 여부
	 */
	private String confirmButtonAuthYn = "N";
	/**
	 * 생산문서 간편조회 버튼 권한 여부
	 */
	private String easyApprSearchButtonAuthYn = "N";
	/**
	 * 접수문서 간편조회 버튼 권한 여부
	 */
	private String easyEnfSearchButtonAuthYn = "N";
	
	/**
	 * 문서등록대장 열람조회 버튼 권한 여부(부서 대 부서 열람)
	 */
	private String shareDeptButtonAuthYn = "N";
	
	/**
	 * 문서등록대장 열람조회 버튼 권한 여부(하위부서 열람)
	 */
	private String subDeptButtonAuthYn = "N";

	/**
	 * @return  the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage  the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return  the totalcount
	 */
	public int getTotalcount() {
		return totalcount;
	}

	/**
	 * @param totalcount  the totalcount to set
	 */
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	/**
	 * @return  the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title  the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return  the deleteButtonAuthYn
	 */
        public String getDeleteButtonAuthYn() {
            return deleteButtonAuthYn;
        }
	/**
	 * @param deleteButtonAuthYn  the deleteButtonAuthYn to set
	 */
        public void setDeleteButtonAuthYn(String deleteButtonAuthYn) {
            this.deleteButtonAuthYn = deleteButtonAuthYn;
        }
	/**
	 * @return  the returnButtonAuthYn
	 */
        public String getReturnButtonAuthYn() {
            return returnButtonAuthYn;
        }
	/**
	 * @param returnButtonAuthYn  the returnButtonAuthYn to set
	 */
        public void setReturnButtonAuthYn(String returnButtonAuthYn) {
            this.returnButtonAuthYn = returnButtonAuthYn;
        }
	/**
	 * @return  the printButtonAuthYn
	 */
        public String getPrintButtonAuthYn() {
            return printButtonAuthYn;
        }
	/**
	 * @param printButtonAuthYn  the printButtonAuthYn to set
	 */
        public void setPrintButtonAuthYn(String printButtonAuthYn) {
            this.printButtonAuthYn = printButtonAuthYn;
        }
	/**
	 * @return  the saveButtonAuthYn
	 */
        public String getSaveButtonAuthYn() {
            return saveButtonAuthYn;
        }
	/**
	 * @param saveButtonAuthYn  the saveButtonAuthYn to set
	 */
        public void setSaveButtonAuthYn(String saveButtonAuthYn) {
            this.saveButtonAuthYn = saveButtonAuthYn;
        }
	/**
	 * @return  the searchButtonAuthYn
	 */
        public String getSearchButtonAuthYn() {
            return searchButtonAuthYn;
        }
	/**
	 * @param searchButtonAuthYn  the searchButtonAuthYn to set
	 */
        public void setSearchButtonAuthYn(String searchButtonAuthYn) {
            this.searchButtonAuthYn = searchButtonAuthYn;
        }
	/**
	 * @return  the displayAppointButtonAuthYn
	 */
        public String getDisplayAppointButtonAuthYn() {
            return displayAppointButtonAuthYn;
        }
	/**
	 * @param displayAppointButtonAuthYn  the displayAppointButtonAuthYn to set
	 */
        public void setDisplayAppointButtonAuthYn(String displayAppointButtonAuthYn) {
            this.displayAppointButtonAuthYn = displayAppointButtonAuthYn;
        }
	/**
	 * @return  the displayProgressButtonAuthYn
	 */
        public String getDisplayProgressButtonAuthYn() {
            return displayProgressButtonAuthYn;
        }
	/**
	 * @param displayProgressButtonAuthYn  the displayProgressButtonAuthYn to set
	 */
        public void setDisplayProgressButtonAuthYn(String displayProgressButtonAuthYn) {
            this.displayProgressButtonAuthYn = displayProgressButtonAuthYn;
        }
	/**
	 * @return  the rearProgressButtonAuthYn
	 */
        public String getRearProgressButtonAuthYn() {
            return rearProgressButtonAuthYn;
        }
	/**
	 * @param rearProgressButtonAuthYn  the rearProgressButtonAuthYn to set
	 */
        public void setRearProgressButtonAuthYn(String rearProgressButtonAuthYn) {
            this.rearProgressButtonAuthYn = rearProgressButtonAuthYn;
        }
        
        
        
	/**
	 * @return  the informButtonAuthYn
	 */
	public String getInformButtonAuthYn() {
		return informButtonAuthYn;
	}

	/**
	 * @param informButtonAuthYn  the informButtonAuthYn to set
	 */
	public void setInformButtonAuthYn(String informButtonAuthYn) {
		this.informButtonAuthYn = informButtonAuthYn;
	}

	/**
	 * @return  the noElecDocButtonAuthYn
	 */
        public String getNoElecDocButtonAuthYn() {
            return noElecDocButtonAuthYn;
        }
	/**
	 * @param noElecDocButtonAuthYn  the noElecDocButtonAuthYn to set
	 */
        public void setNoElecDocButtonAuthYn(String noElecDocButtonAuthYn) {
            this.noElecDocButtonAuthYn = noElecDocButtonAuthYn;
        }
	
	/**
	 * @return  the stampRegistButtonAuthYn
	 */
        public String getStampRegistButtonAuthYn() {
            return stampRegistButtonAuthYn;
        }

	/**
	 * @param stampRegistButtonAuthYn  the stampRegistButtonAuthYn to set
	 */
        public void setStampRegistButtonAuthYn(String stampRegistButtonAuthYn) {
            this.stampRegistButtonAuthYn = stampRegistButtonAuthYn;
        }

	/**
	 * @return  the sealRegistButtonAuthYn
	 */
        public String getSealRegistButtonAuthYn() {
            return sealRegistButtonAuthYn;
        }

	/**
	 * @param sealRegistButtonAuthYn  the sealRegistButtonAuthYn to set
	 */
        public void setSealRegistButtonAuthYn(String sealRegistButtonAuthYn) {
            this.sealRegistButtonAuthYn = sealRegistButtonAuthYn;
        }

	/**
	 * @return  the closeButtonAuthYn
	 */
        public String getCloseButtonAuthYn() {
            return closeButtonAuthYn;
        }

	/**
	 * @param closeButtonAuthYn  the closeButtonAuthYn to set
	 */
        public void setCloseButtonAuthYn(String closeButtonAuthYn) {
            this.closeButtonAuthYn = closeButtonAuthYn;
        }
	/**
	 * @return  the selectButtonAuthYn
	 */
        public String getSelectButtonAuthYn() {
            return selectButtonAuthYn;
        }

	/**
	 * @param selectButtonAuthYn  the selectButtonAuthYn to set
	 */
        public void setSelectButtonAuthYn(String selectButtonAuthYn) {
            this.selectButtonAuthYn = selectButtonAuthYn;
        }

	/**
	 * @return  the publishEndAuthYn
	 */
        public String getPublishEndAuthYn() {
            return publishEndAuthYn;
        }

	/**
	 * @param publishEndAuthYn  the publishEndAuthYn to set
	 */
        public void setPublishEndAuthYn(String publishEndAuthYn) {
            this.publishEndAuthYn = publishEndAuthYn;
        }

	/**
	 * @return  the registButtonAuthYn
	 */
        public String getRegistButtonAuthYn() {
            return registButtonAuthYn;
        }

	/**
	 * @param registButtonAuthYn  the registButtonAuthYn to set
	 */
        public void setRegistButtonAuthYn(String registButtonAuthYn) {
            this.registButtonAuthYn = registButtonAuthYn;
        }

	/**
	 * @return  the cancelButtonAuthYn
	 */
        public String getCancelButtonAuthYn() {
            return cancelButtonAuthYn;
        }

	/**
	 * @param cancelButtonAuthYn  the cancelButtonAuthYn to set
	 */
        public void setCancelButtonAuthYn(String cancelButtonAuthYn) {
            this.cancelButtonAuthYn = cancelButtonAuthYn;
        }

	/**
	 * @return  the modifyButtonAuthYn
	 */
        public String getModifyButtonAuthYn() {
            return modifyButtonAuthYn;
        }

	/**
	 * @param modifyButtonAuthYn  the modifyButtonAuthYn to set
	 */
        public void setModifyButtonAuthYn(String modifyButtonAuthYn) {
            this.modifyButtonAuthYn = modifyButtonAuthYn;
        }

	/**
	 * @param unRegistButtonAuthYn  the unRegistButtonAuthYn to set
	 */
        public void setUnRegistButtonAuthYn(String unRegistButtonAuthYn) {
	    this.unRegistButtonAuthYn = unRegistButtonAuthYn;
        }

	/**
	 * @return  the unRegistButtonAuthYn
	 */
        public String getUnRegistButtonAuthYn() {
	    return unRegistButtonAuthYn;
        }

	/**
	 * @param confirmButtonAuthYn  the confirmButtonAuthYn to set
	 */
        public void setConfirmButtonAuthYn(String confirmButtonAuthYn) {
	    this.confirmButtonAuthYn = confirmButtonAuthYn;
        }

	/**
	 * @return  the confirmButtonAuthYn
	 */
        public String getConfirmButtonAuthYn() {
	    return confirmButtonAuthYn;
        }

	/**
	 * @param easyApprSearchButtonAuthYn  the easyApprSearchButtonAuthYn to set
	 */
        public void setEasyApprSearchButtonAuthYn(String easyApprSearchButtonAuthYn) {
	    this.easyApprSearchButtonAuthYn = easyApprSearchButtonAuthYn;
        }

	/**
	 * @return  the easyApprSearchButtonAuthYn
	 */
        public String getEasyApprSearchButtonAuthYn() {
	    return easyApprSearchButtonAuthYn;
        }

	/**
	 * @param easyEnfSearchButtonAuthYn  the easyEnfSearchButtonAuthYn to set
	 */
        public void setEasyEnfSearchButtonAuthYn(String easyEnfSearchButtonAuthYn) {
	    this.easyEnfSearchButtonAuthYn = easyEnfSearchButtonAuthYn;
        }

	/**
	 * @return  the easyEnfSearchButtonAuthYn
	 */
        public String getEasyEnfSearchButtonAuthYn() {
	    return easyEnfSearchButtonAuthYn;
        }

	/**
	 * @return the shareDeptButtonAuthYn
	 */
	public String getShareDeptButtonAuthYn() {
		return shareDeptButtonAuthYn;
	}

	/**
	 * @param shareDeptButtonAuthYn the shareDeptButtonAuthYn to set
	 */
	public void setShareDeptButtonAuthYn(String shareDeptButtonAuthYn) {
		this.shareDeptButtonAuthYn = shareDeptButtonAuthYn;
	}

	/**
	 * @return the subDeptButtonAuthYn
	 */
	public String getSubDeptButtonAuthYn() {
		return subDeptButtonAuthYn;
	}

	/**
	 * @param subDeptButtonAuthYn the subDeptButtonAuthYn to set
	 */
	public void setSubDeptButtonAuthYn(String subDeptButtonAuthYn) {
		this.subDeptButtonAuthYn = subDeptButtonAuthYn;
	}

	
        
        
	
}
