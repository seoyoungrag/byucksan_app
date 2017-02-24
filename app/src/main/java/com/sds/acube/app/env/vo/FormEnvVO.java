/**
 * 
 */
package com.sds.acube.app.env.vo;

import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.AppConfig;
import org.jconfig.Configuration;
/**
 * Class Name  : FormEnvVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 6. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 4. 6.
 * @version  1.0 
 * @see  com.sds.acube.app.env.vo.FormEnvVO.java
 */

public class FormEnvVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 양식환경ID
	 */
	private String formEnvId;
	/**
	 * 양식환경타입
	 */
	private String envType;
	/**
	 * 양식환경정보
	 */
	private String envInfo;
	/**
	 * 기본여부
	 */
	private String defaultYn;
	/**
	 * 비고
	 */
	private String remark;
	/**
	 * 양식환경 이름
	 */
	private String envName;
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록부서ID
	 */
	private String registerDeptId; //등록부서(회사또는 기관)
	/**
	 * 등록자명
	 */
	private String registerName;	
	/**
	 * 등록일자
	 */
	private String registDate = "9999/12/31 23:59:59";
	/**
	 * 부서변경여부
	 */
	private String changeYn;
	

	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 리소스 Id
	 */
	private String resourceId;    
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");	
	
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
	 * @return  the formEnvId
	 */
        public String getFormEnvId() {
            return formEnvId;
        }
	/**
	 * @param formEnvId  the formEnvId to set
	 */
        public void setFormEnvId(String formEnvId) {
            this.formEnvId = formEnvId;
        }
	/**
	 * @return  the envType
	 */
        public String getEnvType() {
            return envType;
        }
	/**
	 * @param envType  the envType to set
	 */
        public void setEnvType(String envType) {
            this.envType = envType;
        }
	/**
	 * @return  the envInfo
	 */
        public String getEnvInfo() {
            return (envInfo == null) ? "" : envInfo;
        }
	/**
	 * @param envInfo  the envInfo to set
	 */
        public void setEnvInfo(String envInfo) {
            this.envInfo = (envInfo == null) ? "" : envInfo;
        }
	/**
	 * @return  the defaultYn
	 */
        public String getDefaultYn() {
            return defaultYn;
        }
	/**
	 * @param defaultYn  the defaultYn to set
	 */
        public void setDefaultYn(String defaultYn) {
            this.defaultYn = defaultYn;
        }
	/**
	 * @return  the remark
	 */
        public String getRemark() {
            return (remark == null) ? "" : remark;
        }
	/**
	 * @param remark  the remark to set
	 */
        public void setRemark(String remark) {
            this.remark = (remark == null) ? "" : remark;
        }
	/**
	 * @return  the envName
	 */
        public String getEnvName() {
            return (envName == null) ? "" : envName;
        }
	/**
	 * @param envName  the envName to set
	 */
        public void setEnvName(String envName) {
            this.envName = (envName == null) ? "" : envName;
        }
	/**
	 * @return  the registerId
	 */
        public String getRegisterId() {
            return registerId;
        }
	/**
	 * @param registerId  the registerId to set
	 */
        public void setRegisterId(String registerId) {
            this.registerId = registerId;
        }
        
        
        
	/**
	 * @return  the registerDeptId
	 */
	public String getRegisterDeptId() {
		return registerDeptId;
	}
	/**
	 * @param registerDeptId  the registerDeptId to set
	 */
	public void setRegisterDeptId(String registerDeptId) {
		this.registerDeptId = registerDeptId;
	}
	/**
	 * @return  the registerName
	 */
        public String getRegisterName() {
            return registerName;
        }
	/**
	 * @param registerName  the registerName to set
	 */
        public void setRegisterName(String registerName) {
            this.registerName = registerName;
        }
	/**
	 * @return  the registDate
	 */
        public String getRegistDate() {
            return DateUtil.getFormattedDate(registDate, dateFormat);
        }
	/**
	 * @param registDate  the registDate to set
	 */
        public void setRegistDate(String registDate) {
            this.registDate = registDate;
        }
	/**
	 * @return  the changeYn
	 */
        public String getChangeYn() {
            return changeYn;
        }
	/**
	 * @param changeYn  the changeYn to set
	 */
        public void setChangeYn(String changeYn) {
            this.changeYn = changeYn;
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
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormEnvVO [changeYn=" + changeYn + ", compId=" + compId
				+ ", dateFormat=" + dateFormat + ", defaultYn=" + defaultYn
				+ ", envInfo=" + envInfo + ", envName=" + envName
				+ ", envType=" + envType + ", formEnvId=" + formEnvId
				+ ", langType=" + langType + ", registDate=" + registDate
				+ ", registerDeptId=" + registerDeptId + ", registerId="
				+ registerId + ", registerName=" + registerName + ", remark="
				+ remark + ", resourceId=" + resourceId + "]";
	}
         	
}
