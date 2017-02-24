package com.sds.acube.app.env.vo;

/**
 * Class Name  : PostVO.java <br> Description : 우편번호 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PostVO
 */ 
public class PostVO {

	/**
	 * 우편번호
	 */
	private String postNumber;
	/**
	 * 일련번호
	 */
	private int series;
	/**
	 * 주소
	 */
	private String address;
	
	
	/**
	 * @return  the postNumber
	 */
	public String getPostNumber() {
		return postNumber;
	}
	/**
	 * @param postNumber  the postNumber to set
	 */
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	/**
	 * @return  the series
	 */
	public int getSeries() {
		return series;
	}
	/**
	 * @param series  the series to set
	 */
	public void setSeries(int series) {
		this.series = series;
	}
	/**
	 * @return  the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address  the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
