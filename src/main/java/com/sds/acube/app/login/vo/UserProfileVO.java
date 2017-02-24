/*
 * @(#) UserProfileVO.java 2010. 5. 28.
 * Copyright (c) 2010 Samsung SDS Co., Ltd. All Rights Reserved.
 */
package com.sds.acube.app.login.vo;

import java.util.Arrays;
import java.util.HashMap;



/** * @author   Alex, Eum * @version  $Revision: 1.1.4.1.4.1.2.2 $ $Date: 2010/12/29 01:47:43 $ */
public class UserProfileVO {
    /**	 */    protected String compId; // 회사코드
    /**	 */    protected String compName; // 회사명
    /**	 */    protected String deptId; // 부서코드    /**	 */    protected String deptName; // 부서명        /**	 */    protected String institution; // 기관코드    /**	 */    protected String headOffice; // 본부코드
    /**	 */    protected String dutyCode; // 직무코드
    /**	 */    protected String dutyName; // 직무명
    /**	 */    protected String gradeCode; // 직급코드
    /**	 */    protected String gradeName; // 직급명
    /**	 */    protected String groupId; // 
    /**	 */    protected String groupName;
    /**	 */    protected int loginResult; // 로그인 결과
    /**	 */    protected String partId; 
    /**	 */    protected String partName;
    /**	 */    protected String positionCode; // 직위 코드
    /**	 */    protected String positionName; // 직위 명
    /**	 */    protected boolean proxy; 
    /**	 */    protected String roleCodes;
    /**	 */    protected int securityLevel; // 보안 등급
    /**	 */    protected String titleCode; // 직책코드
    /**	 */    protected String titleName; // 직책명
    /**	 */    protected String loginId; // 로그인 아이디 
    /**	 */    protected String userName; // 사용자명
    /**	 */    protected String userStatus; // 
    /**	 */    protected String userUid; // 유져 내부 ID
    /**	 */    protected String password;        /**	 */    protected String employeeId;	// 사번
    
    /**	 * @return the employeeId	 */	public String getEmployeeId() {		return employeeId;	}	/**	 * @param employeeId the employeeId to set	 */	public void setEmployeeId(String employeeId) {		this.employeeId = employeeId;	}	/**	 */    protected String officeTel;
    /**	 */    protected String officeTel2;    /**	 */    protected String officeFax;
    /**	 */    protected String homeTel;
    /**	 */    protected String mobile;
    /**	 */    protected String sysMail;
    /**	 */    protected String email;
    /**	 */    protected String changedPasswordDate;    /**	 */    protected String[] departmentList;
    /**	 */    protected HashMap<String, Object> attrs;
    
    /**	 */    protected String defaultPortalId;
    /**	 */    protected String initialPage; // 개인화 초기화면
    /**	 */    protected String language;        /**	 */    protected String optionalGTPName;	// 통합 직함    /**	 */    protected String displayPosition;	// 표시용 직함    /**	 */    protected String userRid;	// 겸직시 원래의 ID    /**	 */    protected String proxyDocHandleDeptCode;	// 대리문서처리과 코드    /**	 */    protected String proxyDocHandleDeptName;	// 대리문서처리과 이름    /**
     * 
     */
    public UserProfileVO() {
		super();
		this.compId = "";
		this.compName = "";
		this.deptId = "";
		this.deptName = "";
		this.institution = "";		this.headOffice = "";		this.dutyCode = "";
		this.dutyName = "";
		this.gradeCode = "";
		this.gradeName = "";
		this.groupId = "";
		this.groupName = "";
		this.loginResult = -1;
		this.partId = "";
		this.partName = "";
		this.positionCode = "";
		this.positionName = "";
		this.proxy = false;
		this.roleCodes = "";
		this.securityLevel = -1;
		this.titleCode = "";
		this.titleName = "";
		this.loginId = "";
		this.userName = "";
		this.userStatus = "";
		this.userUid = "";
		this.password = "";
		this.departmentList = null;
		this.attrs = new HashMap<String, Object>();
		
		this.officeTel = "";
		this.officeFax = "";
	    this.homeTel = "";
	    this.mobile = "";
	    this.sysMail = "";
	    this.email = "";
	    this.changedPasswordDate = "";
	    this.initialPage = "";	    this.language = "";	    	    this.optionalGTPName = "";
	    this.displayPosition = "";	    	    this.userRid = "";	    	}
    
	/**
	 * @param compId
	 * @param compName
	 * @param deptId	 * @param institution	 * @param headOffice	 * @param deptName
	 * @param dutyCode
	 * @param dutyName
	 * @param gradeCode
	 * @param gradeName
	 * @param groupId
	 * @param groupName
	 * @param loginResult
	 * @param partId
	 * @param partName
	 * @param positionCode
	 * @param positionName
	 * @param proxy
	 * @param roleCodes
	 * @param securityLevel
	 * @param titleCode
	 * @param titleName
	 * @param loginId
	 * @param userName
	 * @param userStatus
	 * @param userUid
	 * @param departmentList
	 * @param attrs
	 */
	public UserProfileVO(String compId, String compName, String deptId, String deptName, String institution, String headOffice, String dutyCode, String dutyName, String gradeCode, String gradeName, String groupId, String groupName,
			int loginResult, String partId, String partName, String positionCode, String positionName, boolean proxy, String roleCodes, int securityLevel, String titleCode, String titleName,
			String loginId, String userName, String userStatus, String userUid, String[] departmentList, HashMap<String, Object> attrs, String officeTel, String homeTel, String mobile, String sysMail,
			String email, String changedPasswordDate) {
		super();
		this.compId = compId;
		this.compName = compName;
		this.deptId = deptId;
		this.deptName = deptName;		this.institution = institution;		this.headOffice = headOffice;		this.dutyCode = dutyCode;
		this.dutyName = dutyName;
		this.gradeCode = gradeCode;
		this.gradeName = gradeName;
		this.groupId = groupId;
		this.groupName = groupName;
		this.loginResult = loginResult;
		this.partId = partId;
		this.partName = partName;
		this.positionCode = positionCode;
		this.positionName = positionName;
		this.proxy = proxy;
		this.roleCodes = roleCodes;
		this.securityLevel = securityLevel;
		this.titleCode = titleCode;
		this.titleName = titleName;
		this.loginId = loginId;
		this.userName = userName;
		this.userStatus = userStatus;
		this.userUid = userUid;
		this.departmentList = departmentList;
		this.attrs = attrs;
		
		this.officeTel = officeTel;
	    this.homeTel = homeTel;
	    this.mobile = mobile;
	    this.sysMail = sysMail;
	    this.email = email;
	    this.changedPasswordDate = changedPasswordDate;
	}
	
	

	/**	 * @return  Returns the defaultPortalId.	 */
	public String getDefaultPortalId() {
	    return defaultPortalId;
	}


	/**	 * @param defaultPortalId  The defaultPortalId to set.	 */
	public void setDefaultPortalId(String defaultPortalId) {
	    this.defaultPortalId = defaultPortalId;
	}

	/**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */	public String getPassword() {
	    return password;
	}

	/**	 * <pre>  설명 </pre>	 * @param password	 * @see   	 */	public void setPassword(String password) {
	    this.password = password;
	}

	/**	 * @return  Returns the compId.	 */
	public String getCompId() {
	    return compId;
	}

	/**	 * @param compId  The compId to set.	 */
	public void setCompId(String compId) {
	    this.compId = compId;
	}

	/**	 * @return  Returns the compName.	 */
	public String getCompName() {
	    return compName;
	}

	/**	 * @param compName  The compName to set.	 */
	public void setCompName(String compName) {
	    this.compName = compName;
	}

	/**	 * @return  Returns the deptId.	 */
	public String getDeptId() {
	    return deptId;
	}

	/**	 * @param deptId  The deptId to set.	 */
	public void setDeptId(String deptId) {
	    this.deptId = deptId;
	}

	/**	 * @return  Returns the deptName.	 */
	public String getDeptName() {
	    return deptName;
	}

	/**	 * @param deptName  The deptName to set.	 */
	public void setDeptName(String deptName) {
	    this.deptName = deptName;
	}
			
	/**	 * @return  the institution	 */	public String getInstitution() {		return institution;	}	/**	 * @param institution  the institution to set	 */	public void setInstitution(String institution) {		this.institution = institution;	}	/**	 * @return  the headOffice	 */	public String getHeadOffice() {		return headOffice;	}	/**	 * @param headOffice  the headOffice to set	 */	public void setHeadOffice(String headOffice) {		this.headOffice = headOffice;	}	/**	 * @return  Returns the dutyCode.	 */
	public String getDutyCode() {
	    return dutyCode;
	}

	/**	 * @param dutyCode  The dutyCode to set.	 */
	public void setDutyCode(String dutyCode) {
	    this.dutyCode = dutyCode;
	}

	/**	 * @return  Returns the dutyName.	 */
	public String getDutyName() {
	    return dutyName;
	}

	/**	 * @param dutyName  The dutyName to set.	 */
	public void setDutyName(String dutyName) {
	    this.dutyName = dutyName;
	}

	/**	 * @return  Returns the gradeCode.	 */
	public String getGradeCode() {
	    return gradeCode;
	}

	/**	 * @param gradeCode  The gradeCode to set.	 */
	public void setGradeCode(String gradeCode) {
	    this.gradeCode = gradeCode;
	}

	/**	 * @return  Returns the gradeName.	 */
	public String getGradeName() {
	    return gradeName;
	}

	/**	 * @param gradeName  The gradeName to set.	 */
	public void setGradeName(String gradeName) {
	    this.gradeName = gradeName;
	}

	/**	 * @return  Returns the groupId.	 */
	public String getGroupId() {
	    return groupId;
	}

	/**	 * @param groupId  The groupId to set.	 */
	public void setGroupId(String groupId) {
	    this.groupId = groupId;
	}

	/**	 * @return  Returns the groupName.	 */
	public String getGroupName() {
	    return groupName;
	}

	/**	 * @param groupName  The groupName to set.	 */
	public void setGroupName(String groupName) {
	    this.groupName = groupName;
	}

	/**	 * @return  Returns the loginResult.	 */
	public int getLoginResult() {
	    return loginResult;
	}

	/**	 * @param loginResult  The loginResult to set.	 */
	public void setLoginResult(int loginResult) {
	    this.loginResult = loginResult;
	}

	/**	 * @return  Returns the partId.	 */
	public String getPartId() {
	    return partId;
	}

	/**	 * @param partId  The partId to set.	 */
	public void setPartId(String partId) {
	    this.partId = partId;
	}

	/**	 * @return  Returns the partName.	 */
	public String getPartName() {
	    return partName;
	}

	/**	 * @param partName  The partName to set.	 */
	public void setPartName(String partName) {
	    this.partName = partName;
	}

	/**	 * @return  Returns the positionCode.	 */
	public String getPositionCode() {
	    return positionCode;
	}

	/**	 * @param positionCode  The positionCode to set.	 */
	public void setPositionCode(String positionCode) {
	    this.positionCode = positionCode;
	}

	/**	 * @return  Returns the positionName.	 */
	public String getPositionName() {
	    return positionName;
	}

	/**	 * @param positionName  The positionName to set.	 */
	public void setPositionName(String positionName) {
	    this.positionName = positionName;
	}

	/**	 * @return  Returns the proxy.	 */
	public boolean isProxy() {
	    return proxy;
	}

	/**	 * @param proxy  The proxy to set.	 */
	public void setProxy(boolean proxy) {
	    this.proxy = proxy;
	}

	/**	 * @return  Returns the roleCodes.	 */
	public String getRoleCodes() {
	    return roleCodes;
	}

	/**	 * @param roleCodes  The roleCodes to set.	 */
	public void setRoleCodes(String roleCodes) {
	    this.roleCodes = roleCodes;
	}

	/**	 * @return  Returns the securityLevel.	 */
	public int getSecurityLevel() {
	    return securityLevel;
	}

	/**	 * @param securityLevel  The securityLevel to set.	 */
	public void setSecurityLevel(int securityLevel) {
	    this.securityLevel = securityLevel;
	}

	/**	 * @return  Returns the titleCode.	 */
	public String getTitleCode() {
	    return titleCode;
	}

	/**	 * @param titleCode  The titleCode to set.	 */
	public void setTitleCode(String titleCode) {
	    this.titleCode = titleCode;
	}

	/**	 * @return  Returns the titleName.	 */
	public String getTitleName() {
	    return titleName;
	}

	/**	 * @param titleName  The titleName to set.	 */
	public void setTitleName(String titleName) {
	    this.titleName = titleName;
	}

	/**	 * @return  Returns the loginId.	 */
	public String getLoginId() {
	    return loginId;
	}

	/**	 * @param loginId  The loginId to set.	 */
	public void setLoginId(String loginId) {
	    this.loginId = loginId;
	}

	/**	 * @return  Returns the userName.	 */
	public String getUserName() {
	    return userName;
	}

	/**	 * @param userName  The userName to set.	 */
	public void setUserName(String userName) {
	    this.userName = userName;
	}

	/**	 * @return  Returns the userStatus.	 */
	public String getUserStatus() {
	    return userStatus;
	}

	/**	 * @param userStatus  The userStatus to set.	 */
	public void setUserStatus(String userStatus) {
	    this.userStatus = userStatus;
	}

	/**	 * @return  Returns the userUid.	 */
	public String getUserUid() {
	    return userUid;
	}

	/**	 * @param userUid  The userUid to set.	 */
	public void setUserUid(String userUid) {
	    this.userUid = userUid;
	}

	/**	 * @return  Returns the departmentList.	 */
	public String[] getDepartmentList() {
	    return departmentList;
	}

	/**	 * @param departmentList  The departmentList to set.	 */
	public void setDepartmentList(String[] departmentList) {
	    this.departmentList = departmentList;
	}

	/**	 * @return  Returns the attrs.	 */
	public HashMap<String, Object> getAttrs() {
	    return attrs;
	}

	/**	 * @param attrs  The attrs to set.	 */
	public void setAttrs(HashMap<String, Object> attrs) {
	    this.attrs = attrs;
	}


	/**	 * @return  Returns the officeTel.	 */
	public String getOfficeTel() {
	    return officeTel;
	}


	/**	 * @param officeTel  The officeTel to set.	 */
	public void setOfficeTel(String officeTel) {
	    this.officeTel = officeTel;
	}

	/**	 * @return  Returns the officeTel2.	 */	public String getOfficeTel2() {	    return officeTel2;	}	/**	 * @param officeTel2  The officeTel2 to set.	 */	public void setOfficeTel2(String officeTel2) {	    this.officeTel2 = officeTel2;	}		
	/**	 * @return  Returns the homeTel.	 */
	public String getHomeTel() {
	    return homeTel;
	}


	/**	 * @param homeTel  The homeTel to set.	 */
	public void setHomeTel(String homeTel) {
	    this.homeTel = homeTel;
	}


	/**	 * @return  Returns the mobile.	 */
	public String getMobile() {
	    return mobile;
	}


	/**	 * @param mobile  The mobile to set.	 */
	public void setMobile(String mobile) {
	    this.mobile = mobile;
	}


	/**	 * @return  Returns the sysMail.	 */
	public String getSysMail() {
	    return sysMail;
	}


	/**	 * @param sysMail  The sysMail to set.	 */
	public void setSysMail(String sysMail) {
	    this.sysMail = sysMail;
	}


	/**	 * @return  Returns the email.	 */
	public String getEmail() {
	    return email;
	}


	/**	 * @param email  The email to set.	 */
	public void setEmail(String email) {
	    this.email = email;
	}


	/**	 * @return  Returns the changedPasswordDate.	 */
	public String getChangedPasswordDate() {
	    return changedPasswordDate;
	}


	/**	 * @param changedPasswordDate  The changedPasswordDate to set.	 */
	public void setChangedPasswordDate(String changedPasswordDate) {
	    this.changedPasswordDate = changedPasswordDate;
	}


	/**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */	public String getOfficeFax() {
	    return officeFax;
	}

	/**	 * <pre>  설명 </pre>	 * @param officeFax	 * @see   	 */	public void setOfficeFax(String officeFax) {
	    this.officeFax = officeFax;
	}

	/**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */	public String getInitialPage() {
	    return initialPage;
	}

	/**	 * <pre>  설명 </pre>	 * @param initialPage	 * @see   	 */	public void setInitialPage(String initialPage) {
	    this.initialPage = initialPage;
	}
	/**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */	public String getLanguage() {	    return language;	}	/**	 * <pre>  설명 </pre>	 * @param language	 * @see   	 */	public void setLanguage(String language) {	    this.language = language;	}
	/**	 * @return  the optionalGTPName	 */        public String getOptionalGTPName() {            return optionalGTPName;        }	/**	 * @param optionalGTPName  the optionalGTPName to set	 */        public void setOptionalGTPName(String optionalGTPName) {            this.optionalGTPName = optionalGTPName;        }	/**	 * @return  the displayPosition	 */        public String getDisplayPosition() {            return displayPosition;        }	/**	 * @param displayPosition  the displayPosition to set	 */        public void setDisplayPosition(String displayPosition) {            this.displayPosition = displayPosition;        }	/**	 * @return  the userRid	 */        public String getUserRid() {            return userRid;        }	/**	 * @param userRid  the userRid to set	 */        public void setUserRid(String userRid) {            this.userRid = userRid;        }	/**	 * @return  the proxyDocHandleDeptCode	 */        public String getProxyDocHandleDeptCode() {            return proxyDocHandleDeptCode;        }	/**	 * @param proxyDocHandleDeptCode  the proxyDocHandleDeptCode to set	 */        public void setProxyDocHandleDeptCode(String proxyDocHandleDeptCode) {            this.proxyDocHandleDeptCode = proxyDocHandleDeptCode;        }	/**	 * @return  the proxyDocHandleDeptName	 */        public String getProxyDocHandleDeptName() {            return proxyDocHandleDeptName;        }	/**	 * @param proxyDocHandleDeptName  the proxyDocHandleDeptName to set	 */        public void setProxyDocHandleDeptName(String proxyDocHandleDeptName) {            this.proxyDocHandleDeptName = proxyDocHandleDeptName;        }	@Override
    public String toString() {
	    return super.toString() + ", UserProfileVO [attrs=" + attrs + ", changedPasswordDate=" + changedPasswordDate 	    	+ ", compId=" + compId + ", compName=" + compName + ", departmentList=" + Arrays.toString(departmentList) 	    	+ ", deptId=" + deptId + ", deptName=" + deptName+ ", institution=" + institution + ", headOffice=" + headOffice + ", displayPosition=" + displayPosition + ", dutyCode=" + dutyCode + ", dutyName=" + dutyName 	    	+ ", email=" + email + ", gradeCode=" + gradeCode + ", gradeName=" + gradeName + ", groupId=" + groupId 	    	+ ", groupName=" + groupName + ", homeTel=" + homeTel + ", initialPage=" + initialPage + ", language=" + language 	    	+ ", loginId=" + loginId + ", loginResult=" + loginResult + ", mobile=" + mobile + ", officeTel=" + officeTel + ", officeTel2=" + officeTel2 + ", optionalGTPName=" + optionalGTPName + ", partId=" + partId 	    	+ ", partName=" + partName + ", positionCode=" + positionCode + ", positionName=" + positionName 	    	+ ", proxy=" + proxy + ", proxyDocHandleDeptCode=" + proxyDocHandleDeptCode + ", roleCodes=" + roleCodes + ", securityLevel=" + securityLevel + ", sysMail=" + sysMail 	    	+ ", titleCode=" + titleCode + ", titleName=" + titleName + ", userName=" + userName + ", userStatus=" + userStatus + ", userUid=" + userUid + "]";
    }
    
}
