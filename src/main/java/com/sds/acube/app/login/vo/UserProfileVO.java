/*
 * @(#) UserProfileVO.java 2010. 5. 28.
 * Copyright (c) 2010 Samsung SDS Co., Ltd. All Rights Reserved.
 */
package com.sds.acube.app.login.vo;

import java.util.Arrays;
import java.util.HashMap;



/**
public class UserProfileVO {
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    /**
    
    /**
    /**
    /**
     * 
     */
    public UserProfileVO() {
		super();
		this.compId = "";
		this.compName = "";
		this.deptId = "";
		this.deptName = "";
		this.institution = "";
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
	    this.initialPage = "";
	    this.displayPosition = "";
    
	/**
	 * @param compId
	 * @param compName
	 * @param deptId
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
		this.deptName = deptName;
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
	
	

	/**
	public String getDefaultPortalId() {
	    return defaultPortalId;
	}


	/**
	public void setDefaultPortalId(String defaultPortalId) {
	    this.defaultPortalId = defaultPortalId;
	}

	/**
	    return password;
	}

	/**
	    this.password = password;
	}

	/**
	public String getCompId() {
	    return compId;
	}

	/**
	public void setCompId(String compId) {
	    this.compId = compId;
	}

	/**
	public String getCompName() {
	    return compName;
	}

	/**
	public void setCompName(String compName) {
	    this.compName = compName;
	}

	/**
	public String getDeptId() {
	    return deptId;
	}

	/**
	public void setDeptId(String deptId) {
	    this.deptId = deptId;
	}

	/**
	public String getDeptName() {
	    return deptName;
	}

	/**
	public void setDeptName(String deptName) {
	    this.deptName = deptName;
	}

	/**
	public String getDutyCode() {
	    return dutyCode;
	}

	/**
	public void setDutyCode(String dutyCode) {
	    this.dutyCode = dutyCode;
	}

	/**
	public String getDutyName() {
	    return dutyName;
	}

	/**
	public void setDutyName(String dutyName) {
	    this.dutyName = dutyName;
	}

	/**
	public String getGradeCode() {
	    return gradeCode;
	}

	/**
	public void setGradeCode(String gradeCode) {
	    this.gradeCode = gradeCode;
	}

	/**
	public String getGradeName() {
	    return gradeName;
	}

	/**
	public void setGradeName(String gradeName) {
	    this.gradeName = gradeName;
	}

	/**
	public String getGroupId() {
	    return groupId;
	}

	/**
	public void setGroupId(String groupId) {
	    this.groupId = groupId;
	}

	/**
	public String getGroupName() {
	    return groupName;
	}

	/**
	public void setGroupName(String groupName) {
	    this.groupName = groupName;
	}

	/**
	public int getLoginResult() {
	    return loginResult;
	}

	/**
	public void setLoginResult(int loginResult) {
	    this.loginResult = loginResult;
	}

	/**
	public String getPartId() {
	    return partId;
	}

	/**
	public void setPartId(String partId) {
	    this.partId = partId;
	}

	/**
	public String getPartName() {
	    return partName;
	}

	/**
	public void setPartName(String partName) {
	    this.partName = partName;
	}

	/**
	public String getPositionCode() {
	    return positionCode;
	}

	/**
	public void setPositionCode(String positionCode) {
	    this.positionCode = positionCode;
	}

	/**
	public String getPositionName() {
	    return positionName;
	}

	/**
	public void setPositionName(String positionName) {
	    this.positionName = positionName;
	}

	/**
	public boolean isProxy() {
	    return proxy;
	}

	/**
	public void setProxy(boolean proxy) {
	    this.proxy = proxy;
	}

	/**
	public String getRoleCodes() {
	    return roleCodes;
	}

	/**
	public void setRoleCodes(String roleCodes) {
	    this.roleCodes = roleCodes;
	}

	/**
	public int getSecurityLevel() {
	    return securityLevel;
	}

	/**
	public void setSecurityLevel(int securityLevel) {
	    this.securityLevel = securityLevel;
	}

	/**
	public String getTitleCode() {
	    return titleCode;
	}

	/**
	public void setTitleCode(String titleCode) {
	    this.titleCode = titleCode;
	}

	/**
	public String getTitleName() {
	    return titleName;
	}

	/**
	public void setTitleName(String titleName) {
	    this.titleName = titleName;
	}

	/**
	public String getLoginId() {
	    return loginId;
	}

	/**
	public void setLoginId(String loginId) {
	    this.loginId = loginId;
	}

	/**
	public String getUserName() {
	    return userName;
	}

	/**
	public void setUserName(String userName) {
	    this.userName = userName;
	}

	/**
	public String getUserStatus() {
	    return userStatus;
	}

	/**
	public void setUserStatus(String userStatus) {
	    this.userStatus = userStatus;
	}

	/**
	public String getUserUid() {
	    return userUid;
	}

	/**
	public void setUserUid(String userUid) {
	    this.userUid = userUid;
	}

	/**
	public String[] getDepartmentList() {
	    return departmentList;
	}

	/**
	public void setDepartmentList(String[] departmentList) {
	    this.departmentList = departmentList;
	}

	/**
	public HashMap<String, Object> getAttrs() {
	    return attrs;
	}

	/**
	public void setAttrs(HashMap<String, Object> attrs) {
	    this.attrs = attrs;
	}


	/**
	public String getOfficeTel() {
	    return officeTel;
	}


	/**
	public void setOfficeTel(String officeTel) {
	    this.officeTel = officeTel;
	}


	/**
	public String getHomeTel() {
	    return homeTel;
	}


	/**
	public void setHomeTel(String homeTel) {
	    this.homeTel = homeTel;
	}


	/**
	public String getMobile() {
	    return mobile;
	}


	/**
	public void setMobile(String mobile) {
	    this.mobile = mobile;
	}


	/**
	public String getSysMail() {
	    return sysMail;
	}


	/**
	public void setSysMail(String sysMail) {
	    this.sysMail = sysMail;
	}


	/**
	public String getEmail() {
	    return email;
	}


	/**
	public void setEmail(String email) {
	    this.email = email;
	}


	/**
	public String getChangedPasswordDate() {
	    return changedPasswordDate;
	}


	/**
	public void setChangedPasswordDate(String changedPasswordDate) {
	    this.changedPasswordDate = changedPasswordDate;
	}


	/**
	    return officeFax;
	}

	/**
	    this.officeFax = officeFax;
	}

	/**
	    return initialPage;
	}

	/**
	    this.initialPage = initialPage;
	}
	/**
	/**
    public String toString() {
	    return super.toString() + ", UserProfileVO [attrs=" + attrs + ", changedPasswordDate=" + changedPasswordDate 
    }
    
}