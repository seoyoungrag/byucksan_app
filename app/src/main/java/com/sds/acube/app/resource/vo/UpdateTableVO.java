package com.sds.acube.app.resource.vo;

/**
 * 
 *  Class Name  : UpdateTableVO.java <br>
 *  Description : 다국어 추가시 변경할 테이블 정보  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2013. 8. 8. <br>
 *  수 정  자 : ahnjava  <br>
 *  수정내용 :  <br>
 * 
 *  @author  ahnjava 
 *  @since 2013. 8. 8.
 *  @version 1.0 
 *  @see  com.sds.acube.app.resource.vo.UpdateTableVO.java
 */
public class UpdateTableVO {
	
	/**
	 * 회사 ID
	 */
	private String compId;
	
	/**
	 * 테이블 이름
	 */
	private String tableName;
	
	/**
	 * 변경할 테이블 필드 이름
	 */
	private String updateField;
	
	/**
	 * 변경할 테이블 필드에 대한 값
	 */
	private String updateValue;
	
	/**
	 * 변경할 테이블 필드에 대한 조건 필드 이름
	 */
	private String conditionField;
	
	/**
	 * 변경할 테이블 필드에 대한 조건 필드 값
	 */
	private String conditionValue;

	/**
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
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
	 * @return the updateField
	 */
	public String getUpdateField() {
		return updateField;
	}

	/**
	 * @param updateField the updateField to set
	 */
	public void setUpdateField(String updateField) {
		this.updateField = updateField;
	}

	/**
	 * @return the updateValue
	 */
	public String getUpdateValue() {
		return updateValue;
	}

	/**
	 * @param updateValue the updateValue to set
	 */
	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}

	/**
	 * @return the conditionField
	 */
	public String getConditionField() {
		return conditionField;
	}

	/**
	 * @param conditionField the conditionField to set
	 */
	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	/**
	 * @return the conditionValue
	 */
	public String getConditionValue() {
		return conditionValue;
	}

	/**
	 * @param conditionValue the conditionValue to set
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateTableVO [compId=" + compId + ", conditionField="
				+ conditionField + ", conditionValue=" + conditionValue
				+ ", tableName=" + tableName + ", updateField=" + updateField
				+ ", updateValue=" + updateValue + "]";
	}
		
}
