package com.sds.acube.app.appcom.vo;


/**
 * Class Name  : AppBindVO.java <br> Description : 편철용 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AppBindVO
 */ 
public class AppBindVO {
	
	/**
	 * 문서ID
	 */ 
	private String docId;
	/**
	 * 회사ID
	 */ 
	private String compId;
	/**
	 * 편철ID
	 */ 
	private String bindingId;
	/**
	 * 편철명
	 */ 
	private String bindingName;
	/**
	 * 사용대상유형
	 */
	private String usingType;
	/**
	 * 원본부서ID
	 */
	private String sourceDeptId;
	/**
	 * 원본부서명
	 */
	private String sourceDeptName;
	/**
	 * 대상부서ID
	 */
	private String targetDeptId;
	/**
	 * 대상부서명
	 */
	private String targetDeptName;
	
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
	 * @return  the bindingId
	 */
        public String getBindingId() {
            return bindingId;
        }
	/**
	 * @param bindingId  the bindingId to set
	 */
        public void setBindingId(String bindingId) {
            this.bindingId = bindingId;
        }
	/**
	 * @return  the bindingName
	 */
        public String getBindingName() {
            return bindingName;
        }
	/**
	 * @param bindingName  the bindingName to set
	 */
        public void setBindingName(String bindingName) {
            this.bindingName = bindingName;
        }
	/**
	 * @return  the usingType
	 */
        public String getUsingType() {
            return usingType;
        }
	/**
	 * @param usingType  the usingType to set
	 */
        public void setUsingType(String usingType) {
            this.usingType = usingType;
        }
	/**
	 * @return  the sourceDeptId
	 */
        public String getSourceDeptId() {
            return sourceDeptId;
        }
	/**
	 * @param sourceDeptId  the sourceDeptId to set
	 */
        public void setSourceDeptId(String sourceDeptId) {
            this.sourceDeptId = sourceDeptId;
        }
	/**
	 * @return  the sourceDeptName
	 */
        public String getSourceDeptName() {
            return sourceDeptName;
        }
	/**
	 * @param sourceDeptName  the sourceDeptName to set
	 */
        public void setSourceDeptName(String sourceDeptName) {
            this.sourceDeptName = sourceDeptName;
        }
	/**
	 * @return  the targetDeptId
	 */
        public String getTargetDeptId() {
            return targetDeptId;
        }
	/**
	 * @param targetDeptId  the targetDeptId to set
	 */
        public void setTargetDeptId(String targetDeptId) {
            this.targetDeptId = targetDeptId;
        }
	/**
	 * @return  the targetDeptName
	 */
        public String getTargetDeptName() {
            return targetDeptName;
        }
	/**
	 * @param targetDeptName  the targetDeptName to set
	 */
        public void setTargetDeptName(String targetDeptName) {
            this.targetDeptName = targetDeptName;
        }
}
