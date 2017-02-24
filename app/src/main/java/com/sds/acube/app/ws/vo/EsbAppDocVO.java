
package com.sds.acube.app.ws.vo;

import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;


/**
 * Class Name  : EsbAppDocVO.java <br> Description : 연계를 통한 전자결재 서비스를 처리함  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 4. <br> 수 정  자 : 윤동원  <br> 수정내용 :  <br>
 * @author   윤동원 
 * @since  2011. 4. 4.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.EsbAppDocVO.java
 */


public class EsbAppDocVO {
    
    /*
     * 전자졀재정보
     * xml형태의 값
     */
    private String approvalInfo;
    
    /*
     * 전자졀재처리정보
     * xml형태의 값
     */
    private String acknowledgeInfo;
    
    /*
     * 처리순서
     */
    private int procOrder = 0;
    
     /*
     * 헤더정보 
     */
    private  HeaderVO headerVO;
    
    /*
     * 연계정보 
     */
    private  LegacyVO legacyVO;
    
    
    /*
     * 첨부파일 정보    
     */
    private  List<FileVO> attachFiles;

    
    /**
	 * @return  the approvalInfo
	 */
    public String getApprovalInfo() {
        return approvalInfo;
    }


    /**
	 * @return  the attachFiles
	 */
    public List<FileVO> getAttachFiles() {
        return attachFiles;
    }

    /**
	 * @param approvalInfo  the approvalInfo to set
	 */
    public void setApprovalInfo(String approvalInfo) {
        this.approvalInfo = approvalInfo;
    }


    /**
	 * @param attachFiles  the attachFiles to set
	 */
    public void setAttachFiles(List<FileVO> attachFiles) {
        this.attachFiles = attachFiles;
    }

    /**
	 * @return  the headerVO
	 */
    public HeaderVO getHeaderVO() {
        return headerVO;
    }


    /**
	 * @param legacyVO  the legacyVO to set
	 */
    public void setHeaderVO(HeaderVO headerVO) {
        this.headerVO = headerVO;
    }

    /**
	 * @return  the legacyVO
	 */
    public LegacyVO getLegacyVO() {
        return legacyVO;
    }

    /**
	 * @param legacyVO  the legacyVO to set
	 */
    public void setLegacyVO(LegacyVO legacyVO) {
        this.legacyVO = legacyVO;
    }

    /**
	 * @return  the acknowledgeInfo
	 */
    public String getAcknowledgeInfo() {
        return acknowledgeInfo;
    }

    /**
	 * @param acknowledgeInfo  the acknowledgeInfo to set
	 */
    public void setAcknowledgeInfo(String acknowledgeInfo) {
        this.acknowledgeInfo = acknowledgeInfo;
    }

     /**
	 * @return  the procOrder
	 */
    public int getProcOrder() {
        return procOrder;
    }

    /**
	 * @param procOrder  the procOrder to set
	 */
    public void setProcOrder(int procOrder) {
        this.procOrder = procOrder;
    }
}
