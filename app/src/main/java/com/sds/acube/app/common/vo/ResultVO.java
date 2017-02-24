package com.sds.acube.app.common.vo;

/**
 * Class Name : ResultVO.java <br> Description : 처리결과 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : redcomet <br> 수정내용 : <br>
 * @author  redcomet
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.common.vo.ResultVO.java
 */

public class ResultVO {

    public ResultVO() { }
    
    public ResultVO(String code, String messageKey) {
	resultCode = code;
	resultMessageKey = messageKey;
	errorMessage = "";
    }


    public ResultVO(String code, String messageKey, String message) {
	resultCode = code;
	resultMessageKey = messageKey;
	errorMessage = message;
    }

    /**
	 * 결과코드
	 */
    private String resultCode;

    /**
	 * 결과메세지키
	 */
    private String resultMessageKey;

    /**
	 * 에러메세지
	 */
    private String errorMessage;


    /**
	 * @return  the resultCode
	 */
    public String getResultCode() {
	return resultCode;
    }


    /**
	 * @param resultCode  the resultCode to set
	 */
    public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
    }


    /**
	 * @return  the resultMessageKey
	 */
    public String getResultMessageKey() {
	return resultMessageKey;
    }


    /**
	 * @param resultMessageKey  the resultMessageKey to set
	 */
    public void setResultMessageKey(String resultMessageKey) {
	this.resultMessageKey = resultMessageKey;
    }


    /**
	 * @return  the errorMessage
	 */
    public String getErrorMessage() {
	return errorMessage;
    }


    /**
	 * @param errorMessage  the errorMessage to set
	 */
    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

}
