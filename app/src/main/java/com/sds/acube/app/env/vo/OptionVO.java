package com.sds.acube.app.env.vo;

/**
 * Class Name  : OptionVO.java <br> Description : 옵션 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18
 * @version  1.0 
 * @see  OptionVO
 */ 

public class OptionVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 옵션ID
	 */
	private String optionId;
	/**
	 * 사용여부
	 */
	private String useYn;
	/**
	 * 옵션값
	 */
	private String optionValue;
	/**
	 * 옵션TYPE
	 */
	private String optionType;
	/**
	 * 설명
	 */
	private String description;	
	
	/** 
	 * 테이블 이름
	 * */
	private String tableName;
	
	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 리소스 Id
	 */
	private String resourceId;
	
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
	 * @return  the optionId
	 */
	public String getOptionId() {
		return optionId;
	}
	
	/**
	 * @param optionId  the optionId to set
	 */
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	
	/**
	 * @return  the useYn
	 */
	public String getUseYn() {
		return useYn;
	}
	
	/**
	 * @param useYn  the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	/**
	 * @return  the optionValue
	 */
	public String getOptionValue() {
	   	return (optionValue == null) ? "" : optionValue;
	}
	
	/**
	 * @param optionValue  the optionValue to set
	 */
	public void setOptionValue(String optionValue) {
		this.optionValue = (optionValue == null) ? "" : optionValue;
	}
	
	/**
	 * @return  the optionType
	 */
    public String getOptionType() {
        return optionType;
    }
    
	/**
	 * @param optionType  the optionType to set
	 */
    public void setOptionType(String optionType) {
        this.optionType = optionType;
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
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the langType
	 */
	public String getLangType() {
		return langType;
	}
	
	/**
	 * @param langType the langType to set
	 */
	public void setLangType(String langType) {
		this.langType = langType;
	}
	
	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OptionVO [compId=" + compId + ", description=" + description
				+ ", langType=" + langType + ", optionId=" + optionId
				+ ", optionType=" + optionType + ", optionValue=" + optionValue
				+ ", resourceId=" + resourceId + ", tableName=" + tableName
				+ ", useYn=" + useYn + "]";
	}

}
