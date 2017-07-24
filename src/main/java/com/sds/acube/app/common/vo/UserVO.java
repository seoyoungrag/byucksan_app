/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name  : UserVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 29. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 4. 29.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.UserVO.java
 */

public class UserVO {
    /**
	 */
    private String userID;
    /**
	 */
    private String userName;
    /**
	 */
    private String userOtherName;
    /**
	 */
    private String userUID;
    /**
	 */
    private String groupID;
    /**
	 */
    private String groupName;
    /**
	 */
    private String groupOtherName;
    /**
	 */
    private String compID;
    /**
	 */
    private String compName;
    /**
	 */
    private String compOtherName;
    /**
	 */
    private String deptID;
    /**
	 */
    private String deptName;
    /**
	 */
    private String deptOtherName;
    /**
	 */
    private String partID;
    /**
	 */
    private String partName;
    /**
	 */
    private String partOtherName;
    /**
	 */
    private String orgDisplayName;
    /**
	 */
    private String orgDisplayOtherName;
    /**
	 */
    private int userOrder;
    /**
	 */
    private int securityLevel;
    /**
	 */
    private String roleCodes;
    /**
	 */
    private String residentNo;
    /**
	 */
    private String employeeID;
    /**
	 */
    private String sysMail;
    /**
	 */
    private boolean concurrent;
    /**
	 */
    private boolean proxy;
    /**
	 */
    private boolean delegate;
    /**
	 */
    private boolean existence;
    /**
	 */
    private String userRID;
    /**
	 */
    private boolean deleted;
    /**
	 */
    private int isDeleted;
    /**
	 */
    private String description;
    /**
	 * @return the isDeleted
	 */
	public int getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 */
    private String reserved1;
    /**
	 */
    private String reserved2;
    /**
	 */
    private String reserved3;
    /**
	 */
    private String gradeCode;
    /**
	 */
    private String gradeName;
    /**
	 */
    private String gradeOtherName;
    /**
	 */
    private String gradeAbbrName;
    /**
	 */
    private int gradeOrder;
    /**
	 */
    private String positionCode;
    /**
	 */
    private String positionName;
    /**
	 */
    private String positionOtherName;
    /**
	 */
    private String positionAbbrName;
    /**
	 */
    private int positionOrder;
    /**
	 */
    private String titleCode;
    /**
	 */
    private String titleName;
    /**
	 */
    private String titleOtherName;
    /**
	 */
    private int titleOrder;
    /**
	 */
    private String email;
    /**
	 */
    private String duty;
    /**
	 */
    private String pcOnlineID;
    /**
	 */
    private String homePage;
    /**
	 */
    private String officeTel;
    /**
	 */
    private String officeTel2;
    /**
	 */
    private String officeAddr;
    /**
	 */
    private String officeDetailAddr;
    /**
	 */
    private String officeZipCode;
    /**
	 */
    private String officeFax;
    /**
	 */
    private String mobile;
    /**
	 */
    private String mobile2;
    /**
	 */
    private String pager;
    /**
	 */
    private String homeAddr;
    /**
	 */
    private String homeDetailAddr;
    /**
	 */
    private String homeZipCode;
    /**
	 */
    private String homeTel;
    /**
	 */
    private String homeTel2;
    /**
	 */
    private String homeFax;
    /**
	 */
    private String userStatus;
    /**
	 */
    private String changedPWDDate;
    /**
	 */
    private String mailServer;
    /**
	 */
    private String certificationID;
    /**
	 */
    private String dutyCode;
    /**
	 */
    private String dutyName;
    /**
	 */
    private String dutyOtherName;
    /**
	 */
    private int dutyOrder;
    /**
	 */
    private String optionalGTPName;
    /**
	 */
    private String displayPosition;
    /**
	 */
    private int loginResult;
    // 확장된 함수(OrgUtil)에 의해 호출될 경우에만 사용 가능
    /**
	 */
    private boolean isEmpty = false;
    /**
	 */
    private String emptyReason = "";
    /**
	 */
    private String emptyStartDate = "";
    /**
	 */
    private String emptyEndDate = "";
    
    private int approvalWaitCount = 0;
    
    
    /**
	 * @return the approvalWaitCount
	 */
	public int getApprovalWaitCount() {
		return approvalWaitCount;
	}
	/**
	 * @param approvalWaitCount the approvalWaitCount to set
	 */
	public void setApprovalWaitCount(int approvalWaitCount) {
		this.approvalWaitCount = approvalWaitCount;
	}
	/**
	 * @return  the userID
	 */
    public String getUserID() {
        return userID;
    }
    /**
	 * @param userID  the userID to set
	 */
    public void setUserID(String userID) {
        this.userID = userID;
    }
    /**
	 * @return  the userName
	 */
    public String getUserName() {
        return userName;
    }
    /**
	 * @param userName  the userName to set
	 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
	 * @return  the userOtherName
	 */
    public String getUserOtherName() {
        return userOtherName;
    }
    /**
	 * @param userOtherName  the userOtherName to set
	 */
    public void setUserOtherName(String userOtherName) {
        this.userOtherName = userOtherName;
    }
    /**
	 * @return  the userUID
	 */
    public String getUserUID() {
        return userUID;
    }
    /**
	 * @param userUID  the userUID to set
	 */
    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
    /**
	 * @return  the groupID
	 */
    public String getGroupID() {
        return groupID;
    }
    /**
	 * @param groupID  the groupID to set
	 */
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    /**
	 * @return  the groupName
	 */
    public String getGroupName() {
        return groupName;
    }
    /**
	 * @param groupName  the groupName to set
	 */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
	 * @return  the groupOtherName
	 */
    public String getGroupOtherName() {
        return groupOtherName;
    }
    /**
	 * @param groupOtherName  the groupOtherName to set
	 */
    public void setGroupOtherName(String groupOtherName) {
        this.groupOtherName = groupOtherName;
    }
    /**
	 * @return  the compID
	 */
    public String getCompID() {
        return compID;
    }
    /**
	 * @param compID  the compID to set
	 */
    public void setCompID(String compID) {
        this.compID = compID;
    }
    /**
	 * @return  the compName
	 */
    public String getCompName() {
        return compName;
    }
    /**
	 * @param compName  the compName to set
	 */
    public void setCompName(String compName) {
        this.compName = compName;
    }
    /**
	 * @return  the compOtherName
	 */
    public String getCompOtherName() {
        return compOtherName;
    }
    /**
	 * @param compOtherName  the compOtherName to set
	 */
    public void setCompOtherName(String compOtherName) {
        this.compOtherName = compOtherName;
    }
    /**
	 * @return  the deptID
	 */
    public String getDeptID() {
        return deptID;
    }
    /**
	 * @param deptID  the deptID to set
	 */
    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }
    /**
	 * @return  the deptName
	 */
    public String getDeptName() {
        return deptName;
    }
    /**
	 * @param deptName  the deptName to set
	 */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    /**
	 * @return  the deptOtherName
	 */
    public String getDeptOtherName() {
        return deptOtherName;
    }
    /**
	 * @param deptOtherName  the deptOtherName to set
	 */
    public void setDeptOtherName(String deptOtherName) {
        this.deptOtherName = deptOtherName;
    }
    /**
	 * @return  the partID
	 */
    public String getPartID() {
        return partID;
    }
    /**
	 * @param partID  the partID to set
	 */
    public void setPartID(String partID) {
        this.partID = partID;
    }
    /**
	 * @return  the partName
	 */
    public String getPartName() {
        return partName;
    }
    /**
	 * @param partName  the partName to set
	 */
    public void setPartName(String partName) {
        this.partName = partName;
    }
    /**
	 * @return  the partOtherName
	 */
    public String getPartOtherName() {
        return partOtherName;
    }
    /**
	 * @param partOtherName  the partOtherName to set
	 */
    public void setPartOtherName(String partOtherName) {
        this.partOtherName = partOtherName;
    }
    /**
	 * @return  the orgDisplayName
	 */
    public String getOrgDisplayName() {
        return orgDisplayName;
    }
    /**
	 * @param orgDisplayName  the orgDisplayName to set
	 */
    public void setOrgDisplayName(String orgDisplayName) {
        this.orgDisplayName = orgDisplayName;
    }
    /**
	 * @return  the orgDisplayOtherName
	 */
    public String getOrgDisplayOtherName() {
        return orgDisplayOtherName;
    }
    /**
	 * @param orgDisplayOtherName  the orgDisplayOtherName to set
	 */
    public void setOrgDisplayOtherName(String orgDisplayOtherName) {
        this.orgDisplayOtherName = orgDisplayOtherName;
    }
    /**
	 * @return  the userOrder
	 */
    public int getUserOrder() {
        return userOrder;
    }
    /**
	 * @param userOrder  the userOrder to set
	 */
    public void setUserOrder(int userOrder) {
        this.userOrder = userOrder;
    }
    /**
	 * @return  the securityLevel
	 */
    public int getSecurityLevel() {
        return securityLevel;
    }
    /**
	 * @param securityLevel  the securityLevel to set
	 */
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
    /**
	 * @return  the roleCodes
	 */
    public String getRoleCodes() {
        return roleCodes;
    }
    /**
	 * @param roleCodes  the roleCodes to set
	 */
    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }
    /**
	 * @return  the residentNo
	 */
    public String getResidentNo() {
        return residentNo;
    }
    /**
	 * @param residentNo  the residentNo to set
	 */
    public void setResidentNo(String residentNo) {
        this.residentNo = residentNo;
    }
    /**
	 * @return  the employeeID
	 */
    public String getEmployeeID() {
        return employeeID;
    }
    /**
	 * @param employeeID  the employeeID to set
	 */
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    /**
	 * @return  the sysMail
	 */
    public String getSysMail() {
        return sysMail;
    }
    /**
	 * @param sysMail  the sysMail to set
	 */
    public void setSysMail(String sysMail) {
        this.sysMail = sysMail;
    }
    /**
	 * @return  the concurrent
	 */
    public boolean isConcurrent() {
        return concurrent;
    }
    /**
	 * @param concurrent  the concurrent to set
	 */
    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }
    /**
	 * @return  the proxy
	 */
    public boolean isProxy() {
        return proxy;
    }
    /**
	 * @param proxy  the proxy to set
	 */
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }
    /**
	 * @return  the delegate
	 */
    public boolean isDelegate() {
        return delegate;
    }
    /**
	 * @param delegate  the delegate to set
	 */
    public void setDelegate(boolean delegate) {
        this.delegate = delegate;
    }
    /**
	 * @return  the existence
	 */
    public boolean isExistence() {
        return existence;
    }
    /**
	 * @param existence  the existence to set
	 */
    public void setExistence(boolean existence) {
        this.existence = existence;
    }
    /**
	 * @return  the userRID
	 */
    public String getUserRID() {
        return userRID;
    }
    /**
	 * @param userRID  the userRID to set
	 */
    public void setUserRID(String userRID) {
        this.userRID = userRID;
    }
    /**
	 * @return  the deleted
	 */
    public boolean isDeleted() {
        return deleted;
    }
    /**
	 * @param deleted  the deleted to set
	 */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
	 * @return  the reserved1
	 */
    public String getReserved1() {
        return reserved1;
    }
    /**
	 * @param reserved1  the reserved1 to set
	 */
    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }
    /**
	 * @return  the reserved2
	 */
    public String getReserved2() {
        return reserved2;
    }
    /**
	 * @param reserved2  the reserved2 to set
	 */
    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }
    /**
	 * @return  the reserved3
	 */
    public String getReserved3() {
        return reserved3;
    }
    /**
	 * @param reserved3  the reserved3 to set
	 */
    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }
    /**
	 * @return  the gradeCode
	 */
    public String getGradeCode() {
        return gradeCode;
    }
    /**
	 * @param gradeCode  the gradeCode to set
	 */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }
    /**
	 * @return  the gradeName
	 */
    public String getGradeName() {
        return gradeName;
    }
    /**
	 * @param gradeName  the gradeName to set
	 */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
    /**
	 * @return  the gradeOtherName
	 */
    public String getGradeOtherName() {
        return gradeOtherName;
    }
    /**
	 * @param gradeOtherName  the gradeOtherName to set
	 */
    public void setGradeOtherName(String gradeOtherName) {
        this.gradeOtherName = gradeOtherName;
    }
    /**
	 * @return  the gradeAbbrName
	 */
    public String getGradeAbbrName() {
        return gradeAbbrName;
    }
    /**
	 * @param gradeAbbrName  the gradeAbbrName to set
	 */
    public void setGradeAbbrName(String gradeAbbrName) {
        this.gradeAbbrName = gradeAbbrName;
    }
    /**
	 * @return  the gradeOrder
	 */
    public int getGradeOrder() {
        return gradeOrder;
    }
    /**
	 * @param gradeOrder  the gradeOrder to set
	 */
    public void setGradeOrder(int gradeOrder) {
        this.gradeOrder = gradeOrder;
    }
    /**
	 * @return  the positionCode
	 */
    public String getPositionCode() {
        return positionCode;
    }
    /**
	 * @param positionCode  the positionCode to set
	 */
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    /**
	 * @return  the positionName
	 */
    public String getPositionName() {
        return positionName;
    }
    /**
	 * @param positionName  the positionName to set
	 */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    /**
	 * @return  the positionOtherName
	 */
    public String getPositionOtherName() {
        return positionOtherName;
    }
    /**
	 * @param positionOtherName  the positionOtherName to set
	 */
    public void setPositionOtherName(String positionOtherName) {
        this.positionOtherName = positionOtherName;
    }
    /**
	 * @return  the positionAbbrName
	 */
    public String getPositionAbbrName() {
        return positionAbbrName;
    }
    /**
	 * @param positionAbbrName  the positionAbbrName to set
	 */
    public void setPositionAbbrName(String positionAbbrName) {
        this.positionAbbrName = positionAbbrName;
    }
    /**
	 * @return  the positionOrder
	 */
    public int getPositionOrder() {
        return positionOrder;
    }
    /**
	 * @param positionOrder  the positionOrder to set
	 */
    public void setPositionOrder(int positionOrder) {
        this.positionOrder = positionOrder;
    }
    /**
	 * @return  the titleCode
	 */
    public String getTitleCode() {
        return titleCode;
    }
    /**
	 * @param titleCode  the titleCode to set
	 */
    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }
    /**
	 * @return  the titleName
	 */
    public String getTitleName() {
        return titleName;
    }
    /**
	 * @param titleName  the titleName to set
	 */
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
    /**
	 * @return  the titleOtherName
	 */
    public String getTitleOtherName() {
        return titleOtherName;
    }
    /**
	 * @param titleOtherName  the titleOtherName to set
	 */
    public void setTitleOtherName(String titleOtherName) {
        this.titleOtherName = titleOtherName;
    }
    /**
	 * @return  the titleOrder
	 */
    public int getTitleOrder() {
        return titleOrder;
    }
    /**
	 * @param titleOrder  the titleOrder to set
	 */
    public void setTitleOrder(int titleOrder) {
        this.titleOrder = titleOrder;
    }
    /**
	 * @return  the email
	 */
    public String getEmail() {
        return email;
    }
    /**
	 * @param email  the email to set
	 */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
	 * @return  the duty
	 */
    public String getDuty() {
        return duty;
    }
    /**
	 * @param duty  the duty to set
	 */
    public void setDuty(String duty) {
        this.duty = duty;
    }
    /**
	 * @return  the pcOnlineID
	 */
    public String getPcOnlineID() {
        return pcOnlineID;
    }
    /**
	 * @param pcOnlineID  the pcOnlineID to set
	 */
    public void setPcOnlineID(String pcOnlineID) {
        this.pcOnlineID = pcOnlineID;
    }
    /**
	 * @return  the homePage
	 */
    public String getHomePage() {
        return homePage;
    }
    /**
	 * @param homePage  the homePage to set
	 */
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
    /**
	 * @return  the officeTel
	 */
    public String getOfficeTel() {
        return officeTel;
    }
    /**
	 * @param officeTel  the officeTel to set
	 */
    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }
    /**
	 * @return  the officeTel2
	 */
    public String getOfficeTel2() {
        return officeTel2;
    }
    /**
	 * @param officeTel2  the officeTel2 to set
	 */
    public void setOfficeTel2(String officeTel2) {
        this.officeTel2 = officeTel2;
    }
    /**
	 * @return  the officeAddr
	 */
    public String getOfficeAddr() {
        return officeAddr;
    }
    /**
	 * @param officeAddr  the officeAddr to set
	 */
    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }
    /**
	 * @return  the officeDetailAddr
	 */
    public String getOfficeDetailAddr() {
        return officeDetailAddr;
    }
    /**
	 * @param officeDetailAddr  the officeDetailAddr to set
	 */
    public void setOfficeDetailAddr(String officeDetailAddr) {
        this.officeDetailAddr = officeDetailAddr;
    }
    /**
	 * @return  the officeZipCode
	 */
    public String getOfficeZipCode() {
        return officeZipCode;
    }
    /**
	 * @param officeZipCode  the officeZipCode to set
	 */
    public void setOfficeZipCode(String officeZipCode) {
        this.officeZipCode = officeZipCode;
    }
    /**
	 * @return  the officeFax
	 */
    public String getOfficeFax() {
        return officeFax;
    }
    /**
	 * @param officeFax  the officeFax to set
	 */
    public void setOfficeFax(String officeFax) {
        this.officeFax = officeFax;
    }
    /**
	 * @return  the mobile
	 */
    public String getMobile() {
        return mobile;
    }
    /**
	 * @param mobile  the mobile to set
	 */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
	 * @return  the mobile2
	 */
    public String getMobile2() {
        return mobile2;
    }
    /**
	 * @param mobile2  the mobile2 to set
	 */
    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }
    /**
	 * @return  the pager
	 */
    public String getPager() {
        return pager;
    }
    /**
	 * @param pager  the pager to set
	 */
    public void setPager(String pager) {
        this.pager = pager;
    }
    /**
	 * @return  the homeAddr
	 */
    public String getHomeAddr() {
        return homeAddr;
    }
    /**
	 * @param homeAddr  the homeAddr to set
	 */
    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }
    /**
	 * @return  the homeDetailAddr
	 */
    public String getHomeDetailAddr() {
        return homeDetailAddr;
    }
    /**
	 * @param homeDetailAddr  the homeDetailAddr to set
	 */
    public void setHomeDetailAddr(String homeDetailAddr) {
        this.homeDetailAddr = homeDetailAddr;
    }
    /**
	 * @return  the homeZipCode
	 */
    public String getHomeZipCode() {
        return homeZipCode;
    }
    /**
	 * @param homeZipCode  the homeZipCode to set
	 */
    public void setHomeZipCode(String homeZipCode) {
        this.homeZipCode = homeZipCode;
    }
    /**
	 * @return  the homeTel
	 */
    public String getHomeTel() {
        return homeTel;
    }
    /**
	 * @param homeTel  the homeTel to set
	 */
    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }
    /**
	 * @return  the homeTel2
	 */
    public String getHomeTel2() {
        return homeTel2;
    }
    /**
	 * @param homeTel2  the homeTel2 to set
	 */
    public void setHomeTel2(String homeTel2) {
        this.homeTel2 = homeTel2;
    }
    /**
	 * @return  the homeFax
	 */
    public String getHomeFax() {
        return homeFax;
    }
    /**
	 * @param homeFax  the homeFax to set
	 */
    public void setHomeFax(String homeFax) {
        this.homeFax = homeFax;
    }
    /**
	 * @return  the userStatus
	 */
    public String getUserStatus() {
        return userStatus;
    }
    /**
	 * @param userStatus  the userStatus to set
	 */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    /**
	 * @return  the changedPWDDate
	 */
    public String getChangedPWDDate() {
        return changedPWDDate;
    }
    /**
	 * @param changedPWDDate  the changedPWDDate to set
	 */
    public void setChangedPWDDate(String changedPWDDate) {
        this.changedPWDDate = changedPWDDate;
    }
    /**
	 * @return  the mailServer
	 */
    public String getMailServer() {
        return mailServer;
    }
    /**
	 * @param mailServer  the mailServer to set
	 */
    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }
    /**
	 * @return  the certificationID
	 */
    public String getCertificationID() {
        return certificationID;
    }
    /**
	 * @param certificationID  the certificationID to set
	 */
    public void setCertificationID(String certificationID) {
        this.certificationID = certificationID;
    }
    /**
	 * @return  the dutyCode
	 */
    public String getDutyCode() {
        return dutyCode;
    }
    /**
	 * @param dutyCode  the dutyCode to set
	 */
    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }
    /**
	 * @return  the dutyName
	 */
    public String getDutyName() {
        return dutyName;
    }
    /**
	 * @param dutyName  the dutyName to set
	 */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
    /**
	 * @return  the dutyOtherName
	 */
    public String getDutyOtherName() {
        return dutyOtherName;
    }
    /**
	 * @param dutyOtherName  the dutyOtherName to set
	 */
    public void setDutyOtherName(String dutyOtherName) {
        this.dutyOtherName = dutyOtherName;
    }
    /**
	 * @return  the dutyOrder
	 */
    public int getDutyOrder() {
        return dutyOrder;
    }
    /**
	 * @param dutyOrder  the dutyOrder to set
	 */
    public void setDutyOrder(int dutyOrder) {
        this.dutyOrder = dutyOrder;
    }
    /**
	 * @return  the optionalGTPName
	 */
    public String getOptionalGTPName() {
        return optionalGTPName;
    }
    /**
	 * @param optionalGTPName  the optionalGTPName to set
	 */
    public void setOptionalGTPName(String optionalGTPName) {
        this.optionalGTPName = optionalGTPName;
    }
    /**
	 * @return  the displayPosition
	 */
    public String getDisplayPosition() {
        return displayPosition;
    }
    /**
	 * @param displayPosition  the displayPosition to set
	 */
    public void setDisplayPosition(String displayPosition) {
        this.displayPosition = displayPosition;
    }
    /**
	 * @return  the loginResult
	 */
    public int getLoginResult() {
        return loginResult;
    }
    /**
	 * @param loginResult  the loginResult to set
	 */
    public void setLoginResult(int loginResult) {
        this.loginResult = loginResult;
    }

	/**
	 * @return  the isEmpty
	 */
    public boolean getIsEmpty() {
        return isEmpty;
    }
	/**
     * @param isEmpty the isEmpty to set
     */
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    /**
	 * @return  the emptyReason
	 */
    public String getEmptyReason() {
        return emptyReason;
    }
    /**
	 * @param emptyReason  the emptyReason to set
	 */
    public void setEmptyReason(String emptyReason) {
        this.emptyReason = emptyReason;
    }
    /**
	 * @return  the emptyStartDate
	 */
    public String getEmptyStartDate() {
        return emptyStartDate;
    }
    /**
	 * @param emptyStartDate  the emptyStartDate to set
	 */
    public void setEmptyStartDate(String emptyStartDate) {
        this.emptyStartDate = emptyStartDate;
    }
    /**
	 * @return  the emptyEndDate
	 */
    public String getEmptyEndDate() {
        return emptyEndDate;
    }
    /**
	 * @param emptyEndDate  the emptyEndDate to set
	 */
    public void setEmptyEndDate(String emptyEndDate) {
        this.emptyEndDate = emptyEndDate;
    }
    
}
