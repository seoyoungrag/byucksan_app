package com.sds.acube.app.common.vo;

/**
 * Class Name  : AppCodeVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 31. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 5. 31.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.AppCodeVO.java
 */

public class AppCodeVO {
    /**
	 * 코드ID
	 */
    private String codeId;
    /**
	 * 코드값
	 */
    private String codeValue;
    /**
	 * 상위ID
	 */
    private String parentId;
    /**
	 * 코드명
	 */
    private String codeName;
    /**
	 * 설명
	 */
    private String description;
    
    /**
	 * @return  the codeId
	 */
    public String getCodeId() {
        return codeId;
    }
    /**
	 * @param codeId  the codeId to set
	 */
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
    /**
	 * @return  the codeValue
	 */
    public String getCodeValue() {
        return codeValue;
    }
    /**
	 * @param codeValue  the codeValue to set
	 */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    /**
	 * @return  the parentId
	 */
    public String getParentId() {
        return parentId;
    }
    /**
	 * @param parentId  the parentId to set
	 */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    /**
	 * @return  the description
	 */
    public String getDescription() {
        return description;
    }
    /**
	 * @param description  the description to set
	 */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
	 * @return  the codeName
	 */
    public String getCodeName() {
        return codeName;
    }
    /**
	 * @param codeName  the codeName to set
	 */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
