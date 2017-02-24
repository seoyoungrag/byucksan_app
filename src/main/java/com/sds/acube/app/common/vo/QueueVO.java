package com.sds.acube.app.common.vo;

/**
 * Class Name  : QueueVO.java <br> Description : 검색엔진연계큐 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  QueueVO
 */ 
public class QueueVO {
	/**
	 * 순번
	 */
	private String seqNo;
	/**
	 * 테이블명
	 */
	private String tableName;
	/**
	 * 검색키
	 */
	private String srchKey;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 처리형태
	 */
	private String action;
	
	/**
	 * @return  the seqNo
	 */
        public String getSeqNo() {
            return seqNo;
        }
	/**
	 * @param seqNo  the seqNo to set
	 */
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
	/**
	 * @return  the tableName
	 */
        public String getTableName() {
            return tableName;
        }
	/**
	 * @param tableName  the tableName to set
	 */
        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
	/**
	 * @return  the srchKey
	 */
        public String getSrchKey() {
            return srchKey;
        }
	/**
	 * @param srchKey  the srchKey to set
	 */
        public void setSrchKey(String srchKey) {
            this.srchKey = srchKey;
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
	 * @return  the action
	 */
        public String getAction() {
            return action;
        }
	/**
	 * @param action  the action to set
	 */
        public void setAction(String action) {
            this.action = action;
        }
	
}
