package com.sds.acube.app.resource.vo;

/**
 *  Class Name  : ResourceVO.java <br>
 *  Description : 다국어 데이터 모델  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2013. 8. 3. <br>
 *  수 정  자 : ahnjava  <br>
 *  수정내용 :  <br>
 * 
 *  @author  ahnjava 
 *  @since 2013. 8. 3.
 *  @version 1.0 
 *  @see  com.sds.acube.app.language.vo.ResourceVO.java
 */
public class ResourceVO {
	
	/**
	 * 회사 ID
	 */
	private String compId;	
	
	/**
	 * 리소스 ID
	 */
	private String resourceId;
	
	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 리소스 명칭
	 */
	private String resourceName;
	
	/**
	 * 리소스 타입
	 */
	private String resourceType;
	
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
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceVO [compId=" + compId + ", langType=" + langType
				+ ", resourceId=" + resourceId + ", resourceName="
				+ resourceName + ", resourceType=" + resourceType + "]";
	}
	
}