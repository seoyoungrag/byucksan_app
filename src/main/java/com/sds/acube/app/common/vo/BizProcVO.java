package com.sds.acube.app.common.vo;

/**
 * Class Name  : BizProcVO.java <br> Description : 업무시스템처리이력 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  BizProcVO
 */ 
public class BizProcVO {
	
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 처리순서
	 */
	private int procOrder = 0;
	/**
	 * 연계처리방향
	 */
	private String exProcDirection;
	/**
	 * 연계처리유형
	 */
	private String exProcType;
	/**
	 * 처리자ID
	 */
	private String processorId;
	/**
	 * 처리자명
	 */
	private String processorName;
	/**
	 * 처리자직위
	 */
	private String processorPos;
	/**
	 * 처리부서ID
	 */
	private String processorDeptId;
	/**
	 * 처리부서명
	 */
	private String processorDeptName;
	/**
	 * 처리일자
	 */
	private String processDate = "9999-12-31 23:59:59";
	/**
	 * 처리의견
	 */
	private String procOpinion;
	/**
	 * 처리유형
	 */
	private String procType;
	/**
	 * 업무시스템코드
	 */
	private String bizSystemCode;
	/**
	 * 업무유형코드
	 */
	private String bizTypeCode;
	/**
	 * 문서상태
	 */
	private String docState;
	/**
	 * 업무처리상태
	 */
	private String exProcState;
	/**
	 * 업무처리일자
	 */
	private String exProcDate = "9999-12-31 23:59:59";
	/**
	 * 업무처리회수
	 */
	private int exProcCount = 0;
	
	/**
	 * 연계처리 ID
	 */
	private String exProcId;
	
	/**
	 * 문서제목
	 */
	private String title;
	/**
	 * 업무시스템명
	 */
	private String bizSystemName;
	/**
	 * 업무유형명
	 */
	private String bizTypeName;
	
	/**
	 * 연계XML
	 */
	private String exchangeXml;
	/**
	 * 연계문서ID
	 */
	private String originDocId;
	
	/**
	 * @return  the docId
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @param docId  the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}
	/**
	 * @return  the compId
	 */
	public String getCompId() {
		return compId;
	}
	/**
	 * @param compId  the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
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
	/**
	 * @return  the exProcDirection
	 */
	public String getExProcDirection() {
		return exProcDirection;
	}
	/**
	 * @param exProcDirection  the exProcDirection to set
	 */
	public void setExProcDirection(String exProcDirection) {
		this.exProcDirection = exProcDirection;
	}
	/**
	 * @return  the exProcType
	 */
	public String getExProcType() {
		return exProcType;
	}
	/**
	 * @param exProcType  the exProcType to set
	 */
	public void setExProcType(String exProcType) {
		this.exProcType = exProcType;
	}
	/**
	 * @return  the processorId
	 */
	public String getProcessorId() {
		return processorId;
	}
	/**
	 * @param processorId  the processorId to set
	 */
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}
	/**
	 * @return  the processorName
	 */
	public String getProcessorName() {
		return processorName;
	}
	/**
	 * @param processorName  the processorName to set
	 */
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	/**
	 * @return  the processorPos
	 */
	public String getProcessorPos() {
		return processorPos;
	}
	/**
	 * @param processorPos  the processorPos to set
	 */
	public void setProcessorPos(String processorPos) {
		this.processorPos = processorPos;
	}
	/**
	 * @return  the processDate
	 */
	public String getProcessDate() {
		return processDate;
	}
	/**
	 * @param processDate  the processDate to set
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	/**
	 * @return  the bizSystemCode
	 */
	public String getBizSystemCode() {
		return bizSystemCode;
	}
	/**
	 * @param bizSystemCode  the bizSystemCode to set
	 */
	public void setBizSystemCode(String bizSystemCode) {
		this.bizSystemCode = bizSystemCode;
	}
	/**
	 * @return  the bizTypeCode
	 */
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	/**
	 * @param bizTypeCode  the bizTypeCode to set
	 */
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	/**
	 * @return  the docState
	 */
        public String getDocState() {
            return docState;
        }
	/**
	 * @param docState  the docState to set
	 */
        public void setDocState(String docState) {
            this.docState = docState;
        }
	/**
	 * @return  the exProcState
	 */
        public String getExProcState() {
            return exProcState;
        }
	/**
	 * @param exProcState  the exProcState to set
	 */
        public void setExProcState(String exProcState) {
            this.exProcState = exProcState;
        }
	/**
	 * @return  the exProcDate
	 */
        public String getExProcDate() {
            return exProcDate;
        }
	/**
	 * @param exProcDate  the exProcDate to set
	 */
        public void setExProcDate(String exProcDate) {
            this.exProcDate = exProcDate;
        }
	/**
	 * @return  the exProcCount
	 */
        public int getExProcCount() {
            return exProcCount;
        }
	/**
	 * @param exProcCount  the exProcCount to set
	 */
        public void setExProcCount(int exProcCount) {
            this.exProcCount = exProcCount;
        }
	/**
	 * @return  the processorDeptId
	 */
        public String getProcessorDeptId() {
            return processorDeptId;
        }
	/**
	 * @param processorDeptId  the processorDeptId to set
	 */
        public void setProcessorDeptId(String processorDeptId) {
            this.processorDeptId = processorDeptId;
        }
	/**
	 * @return  the processorDeptName
	 */
        public String getProcessorDeptName() {
            return processorDeptName;
        }
	/**
	 * @param processorDeptName  the processorDeptName to set
	 */
        public void setProcessorDeptName(String processorDeptName) {
            this.processorDeptName = processorDeptName;
        }
	/**
	 * @return  the procOpinion
	 */
        public String getProcOpinion() {
            return procOpinion;
        }
	/**
	 * @param procOpinion  the procOpinion to set
	 */
        public void setProcOpinion(String procOpinion) {
            this.procOpinion = procOpinion;
        }
	/**
	 * @return  the procType
	 */
        public String getProcType() {
            return procType;
        }
	/**
	 * @param procType  the procType to set
	 */
        public void setProcType(String procType) {
            this.procType = procType;
        }
	/**
	 * @return  the exProcId
	 */
        public String getExProcId() {
            return exProcId;
        }
	/**
	 * @param exProcId  the exProcId to set
	 */
        public void setExProcId(String exProcId) {
            this.exProcId = exProcId;
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
	 * @return  the bizSystemName
	 */
        public String getBizSystemName() {
            return bizSystemName;
        }
	/**
	 * @param bizSystemName  the bizSystemName to set
	 */
        public void setBizSystemName(String bizSystemName) {
            this.bizSystemName = bizSystemName;
        }
	/**
	 * @return  the bizTypeName
	 */
        public String getBizTypeName() {
            return bizTypeName;
        }
	/**
	 * @param bizTypeName  the bizTypeName to set
	 */
        public void setBizTypeName(String bizTypeName) {
            this.bizTypeName = bizTypeName;
        }
	/**
	 * @param exchangeXml  the exchangeXml to set
	 */
        public void setExchangeXml(String exchangeXml) {
	    this.exchangeXml = exchangeXml;
        }
	/**
	 * @return  the exchangeXml
	 */
        public String getExchangeXml() {
	    return exchangeXml;
        }
	/**
	 * @param originDocId  the originDocId to set
	 */
        public void setOriginDocId(String originDocId) {
	    this.originDocId = originDocId;
        }
	/**
	 * @return  the originDocId
	 */
        public String getOriginDocId() {
	    return originDocId;
        }
        
}
