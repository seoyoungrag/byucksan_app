package com.sds.acube.app.approval.vo;

/**
 * Class Name  : GwAccgvVO.java <br> Description : 투자조합 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author    허주 
 * @since   2011. 3. 18 
 * @version   1.0 
 * @see  GwAccgvVO
 */ 
public class GwAccgvVO {
	
	/**
	 * 투자조합ID
	 */
	private String cdecde;
	/**
	 * 투자조합명
	 */
	private String cdenam;

	/**
         * @param investUnionId the investUnionId to set
         */
	/**
	 * @param investUnionId  the investUnionId to set
	 */
        public void setCdecde(String cdecde) {
	    this.cdecde = cdecde;
        }
	/**
	 * @return  the cdecde
	 */
        public String getCdecde() {
	    return cdecde;
        }
	/**
	 * @param cdenam  the cdenam to set
	 */
        public void setCdenam(String cdenam) {
	    this.cdenam = cdenam;
        }
	/**
	 * @return  the cdenam
	 */
        public String getCdenam() {
	    return cdenam;
        }
	
}
